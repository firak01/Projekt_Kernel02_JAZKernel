package basic.zUtil.io;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IConstantZZZ;
import basic.zBasic.IFlagZZZ;
import basic.zBasic.IObjectZZZ;
import basic.zBasic.ObjectZZZ;
import basic.zBasic.ReflectClassZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.IFlagZZZ.FLAGZ;
import basic.zBasic.util.datatype.string.StringArrayZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.file.FileEasyZZZ;
import basic.zBasic.util.math.MathZZZ;
import basic.zKernel.IKernelUserZZZ;
import basic.zKernel.IKernelZZZ;
import custom.zKernel.LogZZZ;
import custom.zUtil.io.FileExpansionZZZ;
import custom.zUtil.io.FileZZZ;


/**
This class extends File and not ObjectZZZ !!!
==> it inherits the methods of file and can also be used as input for printwriter/reader - objects !!!
==> it implements AssetObjectZZZ for throwing ExcetptionZZZ
==> it doesn�t implement assetKernelZZZ, because this class is used to create the log-file of the kernel.
         I never implement any functionality in an object which is used for this functionality !!!

TODO Einige static Methoden an basic.zBasic.Util.file.FileEasyZZZ abgeben  
 * @author Lindhauer
 */
public class KernelFileZZZ extends File implements IConstantZZZ, IObjectZZZ, IFileExpansionUserZZZ, IFlagZZZ{ //IFunctionZZZ {
	private IFileExpansionZZZ objExpansion=null;
	private ExceptionZZZ objException=null;
	
	private String sDirectoryPath = new String("");
	private String sFileName = new String(""); // Der ganze Dateiname, also: FileNameOnly + "." + Ending
		
	//flags 
	//private boolean bFlagUse_FILE_Expansion; //Zeigt an, ob eine Dateinamens Expansion angehängt werden muss, oder eine bestehende Expansion ersetzt hat.
	public enum FLAGZ{
		USE_FILE_EXPANSION; //Merke: DEBUG und INIT aus ObjectZZZ sollen über IObjectZZZ eingebunden werden, weil von ObjectkZZZ kann man ja nicht erben. Es wird schon von File geerbt.
	}
	
	
	private HashMap<String, Boolean>hmFlag = new HashMap<String, Boolean>(); 
	
//	### Constructor ##########################
	public KernelFileZZZ() throws ExceptionZZZ{
		this("","","init");
	}
	
	public KernelFileZZZ(String sDirectoryPath, String sFileName, String[] saFlagControlIn) throws ExceptionZZZ{
		super(sDirectoryPath + "\\" + sFileName);
		String stemp; boolean btemp;
		main:{
			
		//setzen der übergebenen Flags	
			if(saFlagControlIn != null){
				for(int iCount = 0;iCount<=saFlagControlIn.length-1;iCount++){
					stemp = saFlagControlIn[iCount];
					btemp = setFlag(stemp, true);
					if(btemp==false){ 								   
						   ExceptionZZZ ez = new ExceptionZZZ( sERROR_FLAG_UNAVAILABLE + stemp, iERROR_FLAG_UNAVAILABLE, ReflectCodeZZZ.getMethodCurrentName(), ""); 
						   //doesn�t work. Only works when > JDK 1.4
						   //Exception e = new Exception();
						   //ExceptionZZZ ez = new ExceptionZZZ(stemp,iCode,this, e, "");
						   throw ez;		 
					}
				}
				}

			//+++ Falls das Debug-Flag gesetzt ist, muss nun eine Session �ber das Factory-Objekt erzeugt werden. 
			// Damit kann auf andere Datenbanken zugegriffen werden (z.B. im Eclipse Debugger)
			// Besser jedoch ist es beim Debuggen mit einem anderen Tool eine Notes-ID zu verwenden, die ein leeres Passwort hat.
			btemp = this.getFlag("init");
			if(btemp==true) break main;
		
		this.setPathDirectory(sDirectoryPath);
		this.setName(sFileName);
		if(this.getFlag(KernelFileZZZ.FLAGZ.USE_FILE_EXPANSION.name())) {
			IFileExpansionZZZ objFileExpansion=new FileExpansionZZZ((FileZZZ) this);
			this.setFileExpansionObject(objFileExpansion);
		}
		}//end main		
	}
	
	
	public KernelFileZZZ(String sDirectoryPath, String sFileName, String sFlagControl) throws ExceptionZZZ{
		super(sDirectoryPath + "\\" + sFileName);
		String[] saFlagControl = new String[1];
		saFlagControl[0] = sFlagControl;
		KernelFileNew_(sDirectoryPath, sFileName, -1, saFlagControl);		//-1; der Defaultwert (aus einer Konstante) wird dann genommen 
	}

	
	/** CONSTRUCTOR
	 @date: 04.10.2004
	 @param sPathTarget
	 @param sFileTarget
	 @param iExpansionLength
	 * @throws ExceptionZZZ 
	 */
	public KernelFileZZZ(String sDirectoryPath, String sFileName, int iExpansionLength, String[] saFlagControl) throws ExceptionZZZ {		
		super(sDirectoryPath + "\\" + sFileName);
		KernelFileNew_(sDirectoryPath, sFileName, iExpansionLength, saFlagControl);
	}
	private void KernelFileNew_(String sDirectoryPath, String sFileName, int iExpansionLength, String[] saFlagControl) throws ExceptionZZZ {
			main:{
				if(saFlagControl!=null){
					boolean btemp = false;
					for(int icount=0;icount <= saFlagControl.length-1;icount++){
						String stemp = saFlagControl[icount];
						btemp = this.setFlag(stemp, true);
						
						if(btemp==false){ 								   
							   ExceptionZZZ ez = new ExceptionZZZ( sERROR_FLAG_UNAVAILABLE + stemp, iERROR_FLAG_UNAVAILABLE, ReflectCodeZZZ.getMethodCurrentName(), ""); 
							   throw ez;		 
						}
					}
					if(this.getFlag("init")) break main;
				}
				
				this.setPathDirectory(sDirectoryPath);
				this.setName(sFileName);
				
				if(this.getFlag(KernelFileZZZ.FLAGZ.USE_FILE_EXPANSION.name())) {
					IFileExpansionZZZ objFileExpansion=new FileExpansionZZZ((FileZZZ) this, iExpansionLength);
					this.setFileExpansionObject(objFileExpansion);	
				}
		}//End main:
		
	}

	
	//### Acessor - Methods ###################
	//+++++++++++++++++++++++++++++++++++++++++
	public String getName() {
		return this.sFileName;
	}
	public void setName(String sFileName) {
		this.sFileName = sFileName;
	}
	
	public String getNameEnd(){	
		return FileEasyZZZ.NameEndCompute(this.getName());
	}
	
	public String getNameWithChangedEnd(String sEnd) throws ExceptionZZZ{
		String sReturn = null;
		
		String sFileName = this.getName();
		sReturn = FileEasyZZZ.getNameWithChangedEnd(sFileName, sEnd);	
		
		return sReturn;
	}
	
	
	
	//++++++++++++++++++++++++++++++++++++++++++
	public String getNameOnly() throws ExceptionZZZ{		
		return FileEasyZZZ.NameOnlyCompute(this.getName());	
	}
	
	
	/** Berechnet den nächsten Dateinamen. Dabei wird ggf. eine Zählvariable an den Dateinamen angehängt
	 * 
	 * Merke: Der Rückgabewert enthält nur den Dateinamen nicht den Pfad
	* @return
	* @throws ExceptionZZZ
	* 
	* lindhauer; 25.02.2008 11:58:46
	 */
	public String getNameExpandedNext() throws ExceptionZZZ{
		String sReturn = null;
		main:{
			String stemp = PathNameTotalExpandedNextCompute();
			File objFile = new File(stemp);
			sReturn = objFile.getName();  				
		}
		return sReturn;
	}
	
	public String getNameExpandedFirst() throws ExceptionZZZ{
		String sReturn = null;
		main:{
			String stemp = PathNameTotalExpandedFirstCompute();
			File objFile = new File(stemp);
			sReturn = objFile.getName();  				
		}
		return sReturn;
	}
	
	public String getNameExpandedCurrent() throws ExceptionZZZ{
		String sReturn = null;
		main:{
			String stemp = PathNameTotalExpandedCurrentCompute();
			File objFile = new File(stemp);
			sReturn = objFile.getName();  				
		}
		return sReturn;
	}
	

	
	
	//++++++++++++++++++++++++++++++++++++++++++
	public String getPathDirectory(){
		//Merke: hierfür gibt es keine Berechnung. Dies wird konstant gesetzt.
		return this.sDirectoryPath;			
	}
	
	public void setPathDirectory(String sToSet){
		this.sDirectoryPath = sToSet;
	}
	
	/**Overwritten and using an object of jakarta.commons.lang
	 * to create this string using reflection. 
	 * Remark: this is not yet formated. A style class is available in jakarta.commons.lang. 
	 */
	@Override
	public String toString(){
		String sReturn = "";
		sReturn = ReflectionToStringBuilder.toString(this);
		return sReturn;
	}
	
	//### FlagMethods ##########################	
	@Override
	public Class getClassFlagZ(){
		return FLAGZ.class;
	}
	
	public HashMap<String, Boolean>getHashMapFlagZ(){
		return this.hmFlag;
	} 
	
	/* @see basic.zBasic.IFlagZZZ#getFlagZ(java.lang.String)
	 * 	 Weteire Voraussetzungen:
	 * - Public Default Konstruktor der Klasse, damit die Klasse instanziiert werden kann.
	 * - Innere Klassen müssen auch public deklariert werden.(non-Javadoc)
	 */
	@Override
	public boolean getFlagZ(String sFlagName) {
		boolean bFunction = false;
		main:{
			if(StringZZZ.isEmpty(sFlagName)) break main;
										
			HashMap<String, Boolean> hmFlag = this.getHashMapFlagZ();
			Boolean objBoolean = hmFlag.get(sFlagName.toUpperCase());
			if(objBoolean==null){
				bFunction = false;
			}else{
				bFunction = objBoolean.booleanValue();
			}
							
		}	// end main:
		
		return bFunction;	
	}
	
	
	
	//++++++++++++++++++++++++++++++++++++++++++
//		public boolean getFlag(String sFlagAlias){
//			boolean bFunction = false;
//			
//			main:{
//				String sTemp = sFlagAlias.toLowerCase();
//				if(sTemp.equals("use_file_expansion")){
//						bFunction = this.bFlag_use_file_Expansion;
//				}
//			}
//			end:{
//				return bFunction;	
//			}		
//		} // end function
//		
//		//+++++++++++++++++++++++++++++++++++++++++++
//		public boolean setFlag(String sFlagAlias, boolean bValue){
//			boolean bFunction = false;
//			
//			main:{
//				String sTemp = sFlagAlias.toLowerCase();
//				if(sTemp.equals("use_file_expansion")){
//					this.bFlag_use_file_Expansion = bValue;
//					bFunction = true;
//				}			
//			}
//			end:{
//				return bFunction;	
//			}	
//		}
	
	
	@Override
	public boolean getFlag(String sFlagName) {
//		boolean bFunction = false;
//	main:{
//		if(StringZZZ.isEmpty(sFlagName)) break main;
//		
//		// hier keine Superclass aufrufen, ist ja schon ObjectZZZ
//		// bFunction = super.getFlag(sFlagName);
//		// if(bFunction == true) break main;
//		
//		// Die Flags dieser Klasse setzen
//		String stemp = sFlagName.toLowerCase();
//		if(stemp.equals("debug")){
//			bFunction = this.bFlagDebug;
//			break main;
//		}else if(stemp.equals("init")){
//			bFunction = this.bFlagInit;
//			break main;
//		}else{
//			bFunction = false;
//		}		
//	}	// end main:
//	
//	return bFunction;	
		return this.getFlagZ(sFlagName);
	}
	@Override
	public boolean setFlag(String sFlagName, boolean bFlagValue) {
		//Version Vor Java 1.6
//		boolean bFunction = true;
//		main:{
//			if(StringZZZ.isEmpty(sFlagName)) break main;
//			
//			// hier keine Superclass aufrufen, ist ja schon ObjectZZZ
//			// bFunction = super.setFlag(sFlagName, bFlagValue);
//			// if(bFunction == true) break main;
//			
//			// Die Flags dieser Klasse setzen
//			String stemp = sFlagName.toLowerCase();
//			if(stemp.equals("debug")){
//				this.bFlagDebug = bFlagValue;
//				bFunction = true;                            //durch diesen return wert kann man "reflexiv" ermitteln, ob es in dem ganzen hierarchie-strang das flag �berhaupt gibt !!!
//				break main;
//			}else if(stemp.equals("init")){
//				this.bFlagInit = bFlagValue;
//				bFunction = true;
//				break main;
//			}else{
//				bFunction = false;
//			}	
//			
//		}	// end main:
//		
//		return bFunction;	
		try {
			return this.setFlagZ(sFlagName, bFlagValue);
		} catch (ExceptionZZZ e) {
			System.out.println("ExceptionZZZ (aus compatibilitaetgruenden mit Version vor Java 6 nicht weitergereicht) : " + e.getDetailAllLast());
			return false;
		}
	}
	
	
	
	/** DIESE METHODE MUSS IN ALLEN KLASSEN VORHANDEN SEIN - über Vererbung -, DIE IHRE FLAGS SETZEN WOLLEN
	 * Weteire Voraussetzungen:
	 * - Public Default Konstruktor der Klasse, damit die Klasse instanziiert werden kann.
	 * - Innere Klassen müssen auch public deklariert werden.
	 * @param objClassParent
	 * @param sFlagName
	 * @param bFlagValue
	 * @return
	 * lindhaueradmin, 23.07.2013
	 */
	@Override
	public boolean setFlagZ(String sFlagName, boolean bFlagValue) throws ExceptionZZZ {
		boolean bFunction = false;
		main:{
			if(StringZZZ.isEmpty(sFlagName)) break main;
			

			bFunction = this.proofFlagZExists(sFlagName);												
			if(bFunction == true){
				
				//Setze das Flag nun in die HashMap
				HashMap<String, Boolean> hmFlag = this.getHashMapFlagZ();
				hmFlag.put(sFlagName.toUpperCase(), bFlagValue);
				bFunction = true;								
			}										
		}	// end main:
		
		return bFunction;	
	}
	
	//Aus IFlagZZZ, siehe ObjectZZZ
	/**Gibt alle möglichen FlagZ Werte als Array zurück. 
	 * @return
	 */
	public String[] getFlagZ(){
		String[] saReturn = null;
		main:{				
				Class objClass4Enum = this.getClassFlagZ();	//Aufgrund des Interfaces IFlagZZZ wird vorausgesetzt, dass diese Methode vorhanden ist.
				String sFilterName = objClass4Enum.getSimpleName();
				
				ArrayList<Class<?>> listEmbedded = ReflectClassZZZ.getEmbeddedClasses(this.getClass(), sFilterName);
				if(listEmbedded == null) break main;
				//out.format("%s# ListEmbeddedClasses.size()...%s%n", ReflectCodeZZZ.getPositionCurrent(), listEmbedded.size());
				
				ArrayList <String> listasTemp = new ArrayList<String>();
				for(Class objClass : listEmbedded){
					//out.format("%s# Class...%s%n", ReflectCodeZZZ.getPositionCurrent(), objClass.getName());
					Field[] fields = objClass.getDeclaredFields();
					for(Field field : fields){
						if(!field.isSynthetic()){ //Sonst wird ENUM$VALUES auch zurückgegeben.
							//out.format("%s# Field...%s%n", ReflectCodeZZZ.getPositionCurrent(), field.getName());
							listasTemp.add(field.getName());
						}				
				}//end for
			}//end for
				
			//20170307: Durch das Verschieben von FLAGZ mit den Werten DEBUG und INIT in das IObjectZZZ Interface, muss man explizit auch dort nachsehen.
		   //                Merke: Das Verschieben ist deshlab notwenig, weil nicht alle Klassen direkt von ObjectZZZ erben können, sondern das Interface implementieren müsssen.
		
											
				//+++ Nun die aktuelle Klasse 
				Class<FLAGZ> enumClass = FLAGZ.class;								
				for(Object obj : FLAGZ.class.getEnumConstants()){
					//System.out.println(obj + "; "+obj.getClass().getName());
					listasTemp.add(obj.toString());
				}
				saReturn = listasTemp.toArray(new String[listasTemp.size()]);
		}//end main:
		return saReturn;
	}

	/**Gibt alle "true" gesetzten FlagZ - Werte als Array zurück. 
	 * @return
	 */
	public String[] getFlagZ(boolean bValueToSearchFor){
		String[] saReturn = null;
		main:{
			
			
			ArrayList<String>listasTemp=new ArrayList<String>();
			
			//FALLUNTERSCHEIDUNG: Alle gesetzten FlagZ werden in der HashMap gespeichert. Aber die noch nicht gesetzten FlagZ stehen dort nicht drin.
			//                                  Diese kann man nur durch Einzelprüfung ermitteln.
			if(bValueToSearchFor==true){
				HashMap<String,Boolean>hmFlag=this.getHashMapFlagZ();
				if(hmFlag==null) break main;
				
				Set<String> setKey = hmFlag.keySet();
				for(String sKey : setKey){
					boolean btemp = hmFlag.get(sKey);
					if(btemp==bValueToSearchFor){
						listasTemp.add(sKey);
					}
				}
			}else{
				String[]saFlagZ = this.getFlagZ();
				for(String sFlagZ : saFlagZ){
					boolean btemp = this.getFlagZ(sFlagZ);
					if(btemp==bValueToSearchFor ){ //also 'false'
						listasTemp.add(sFlagZ);
					}
				}
			}
			saReturn = listasTemp.toArray(new String[listasTemp.size()]);
		}//end main:
		return saReturn;
	}
	
	/**Gibt alle "true" gesetzten FlagZ - Werte als Array zurück, die auch als FLAGZ in dem anderen Objekt überhaupt vorhanden sind.
	 *  Merke: Diese Methode ist dazu gedacht FlagZ-Werte von einem Objekt auf ein anderes zu übertragen.	
	 *    
	 * @return
	 * @throws ExceptionZZZ 
	 */
	public String[] getFlagZ_passable(boolean bValueToSearchFor, IFlagZZZ objUsingFlagZ) throws ExceptionZZZ{
		String[] saReturn = null;
		main:{
			
			//1. Hole alle FlagZ, DIESER Klasse, mit dem gewünschten Wert.
			String[] saFlag = this.getFlagZ(bValueToSearchFor);
			
			//2. Hole alle FlagZ der Zielklasse
			String[] saFlagTarget = objUsingFlagZ.getFlagZ();
			
			ArrayList<String>listasFlagPassable=new ArrayList<String>();
			//Nun nur die Schnittmenge der beiden StringÄrrays hiolen.
			
			saReturn = StringArrayZZZ.intersect(saFlag, saFlagTarget);
		}//end main:
		return saReturn;
	}
	
	/**Gibt alle "true" gesetzten FlagZ - Werte als Array zurück, die auch als FLAGZ in dem anderen Objekt überhaupt vorhanden sind.
	 *  Merke: Diese Methode ist dazu gedacht FlagZ-Werte von einem Objekt auf ein anderes zu übertragen.	
	 *    
	 * @return
	 * @throws ExceptionZZZ 
	 */
	public String[] getFlagZ_passable(IFlagZZZ objUsingFlagZ) throws ExceptionZZZ{
		String[] saReturn = null;
		main:{
			
			//1. Hole alle FlagZ, DIESER Klasse, mit dem gewünschten Wert.
			String[] saFlag = this.getFlagZ();
			
			//2. Hole alle FlagZ der Zielklasse
			String[] saFlagTarget = objUsingFlagZ.getFlagZ();
			
			ArrayList<String>listasFlagPassable=new ArrayList<String>();
			//Nun nur die Schnittmenge der beiden StringÄrrays hiolen.
			
			saReturn = StringArrayZZZ.intersect(saFlag, saFlagTarget);
		}//end main:
		return saReturn;
	}
	
	//Aus IObjectZZZ, siehe FileZZZ
		@Override
		public boolean proofFlagZExists(String sFlagName) {
			boolean bReturn = false;
			main:{
				bReturn = ObjectZZZ.proofFlagZExists(this.getClass(), sFlagName);
			
				//Schon die oberste IObjectZZZ nutzende Klasse, darum ist der Aufruf einer Elternklasse mit der Methode nicht möglich. 
				//boolean bReturn = super.proofFlagZExists(sFlagName);
			
				if(!bReturn){			
					Class<FLAGZ> enumClass = FLAGZ.class;				
					for(Object obj : FLAGZ.class.getEnumConstants()){
						//System.out.println(obj + "; "+obj.getClass().getName());
						if(sFlagName.equalsIgnoreCase(obj.toString())) {
							bReturn = true;
							break main;
						}
					}				
				}
			}//end main:
			return bReturn;
		}
	
	
	
	//### Functions #########################
	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++

	/**
	 @param sDirectoryName
	 @param sFileName
	 @param iExpansionLength
	 @return String, Path with filename. The filename does have the next expansion. 
	 * @throws ExceptionZZZ 
	 */
	public String PathNameTotalExpandedNextCompute(String sDirectoryNameIn, String sFileNameIn) throws ExceptionZZZ{
		String sReturn = new String("");
		main:{					
			String sDirectoryName;
			if(StringZZZ.isEmpty(sDirectoryNameIn)){
				sDirectoryName = this.getPathDirectory();
			}else{
				sDirectoryName = sDirectoryNameIn;
			}
			
			String sFileName;	
			if(sFileNameIn == null){
				sFileName = this.getName();
			}else{
				sFileName = sFileNameIn;
			}
			
			//Leere Dateinamen können nicht "expanidert" werden.
			int iFileLength = sFileName.length();
			if(iFileLength <= 0){
				break main;
			}											 
		
 	 	
 	 		sReturn = PathNameTotalExpandedNextCompute_(sDirectoryName, sFileName);
		
		}//end main
		return sReturn;
	}
	
	public String PathNameTotalExpandedFirstCompute(String sDirectoryNameIn, String sFileNameIn) throws ExceptionZZZ{
	String sReturn = new String("");
	main:{
		String sDirectoryName;
		if(StringZZZ.isEmpty(sDirectoryNameIn)){
			sDirectoryName = this.getPathDirectory();
		}else{
			sDirectoryName = sDirectoryNameIn;
		}
			
		String sFileName;	
		if(sFileNameIn == null){
			sFileName = this.getName();
		}else{
			sFileName = sFileNameIn;
		}
			
		//Leere Dateinamen können nicht "expanidert" werden.
		int iFileLength = sFileName.length();
		if(iFileLength <= 0){
			break main;
		}				

 	 	
		sReturn = PathNameTotalExpandedFirstCompute_(sDirectoryName, sFileName);
		
	}//end main
	return sReturn;
}


public String PathNameTotalExpandedCurrentCompute(String sDirectoryNameIn, String sFileNameIn) throws ExceptionZZZ{
	String sReturn = new String("");
	main:{		
		String sDirectoryName;
		if(StringZZZ.isEmpty(sDirectoryNameIn)) {
			sDirectoryName = this.getPathDirectory();
		}else {
			sDirectoryName = sDirectoryNameIn;
		}
				
		String sFileName;
		if(StringZZZ.isEmpty(sFileNameIn)){
			sFileName = this.getName();
		}else {
			sFileName = sFileNameIn;
		}
				
		//Leere Dateinamen können nicht "expanidert" werden.
		int iFileLength = sFileName.length();
		if(iFileLength <= 0){
			break main;
		}				

		sReturn = PathNameTotalExpandedCurrentCompute_(sDirectoryName, sFileName);
		
	}//end main
	return sReturn;
}

public String PathNameTotalExpandedFirstCompute() throws ExceptionZZZ{
	String sReturn = new String("");
	main:{
		sReturn = PathNameTotalExpandedFirstCompute_(this.getPathDirectory(), this.getName());				
} //end main
return sReturn;
}

public String PathNameTotalExpandedCurrentCompute() throws ExceptionZZZ{
	String sReturn = new String("");
	main:{
		sReturn = PathNameTotalExpandedCurrentCompute_(this.getPathDirectory(), this.getName());				
} //end main
return sReturn;
}


	
	public String PathNameTotalExpandedNextCompute() throws ExceptionZZZ{
		String sReturn = new String("");
		main:{
			sReturn = PathNameTotalExpandedNextCompute_(this.getPathDirectory(), this.getName());				
	} //end main
	return sReturn;
	}
	
	private String PathNameTotalExpandedNextCompute_(String sDirectoryIn, String sFileIn) throws ExceptionZZZ{
		String sReturn = new String("");
		main:{								
			String sDirectory;
			if(StringZZZ.isEmpty(sDirectoryIn)) {
				sDirectory = this.getPathDirectory();
			}else {
				sDirectory=sDirectoryIn;
			}
			
			String sFile; String sFileOnly;String sEnding;
			if(StringZZZ.isEmpty(sFileIn)) {
				sFile = this.getName();
				sFileOnly = this.getNameOnly();	
				sEnding = this.getNameEnd();
			}else {
				sFile = sFileIn;
				sFileOnly = FileEasyZZZ.NameOnlyCompute(sFile);
				sEnding = FileEasyZZZ.NameEndCompute(this.getName());
			}			 
			if(sEnding.length() > 0){
				sEnding = "." + sEnding;
			}
			
			//Merke: Nur mit dem Flag "use_file_expansion" ist das Expansion Objekt normalerweise gefüllt.			
			if(this.getFileExpansionObject()==null) {
				sReturn =  sDirectory + File.separator + sFileOnly + sEnding;							
				break main;
			}else {
				String sExpandValue = this.getExpansionNext();
				sReturn = sDirectory + File.separator + sFileOnly + sExpandValue + sEnding;						
				break main;			
			}				
		}//end main
		return sReturn;
	}
	
	private String PathNameTotalExpandedFirstCompute_(String sDirectoryIn, String sFileIn) throws ExceptionZZZ{
	String sReturn = new String("");
	main:{
		String sDirectory;
		if(StringZZZ.isEmpty(sDirectoryIn)) {
			sDirectory = this.getPathDirectory();
		}else {
			sDirectory=sDirectoryIn;
		}
		
		String sFile; String sFileOnly;String sEnding;
		if(StringZZZ.isEmpty(sFileIn)) {
			sFile = this.getName();
			sFileOnly = this.getNameOnly();	
			sEnding = this.getNameEnd();
		}else {
			sFile = sFileIn;
			sFileOnly = FileEasyZZZ.NameOnlyCompute(sFile);
			sEnding = FileEasyZZZ.NameEndCompute(this.getName());
		}	
		if(sEnding.length() > 0){
			sEnding = "." + sEnding;
		}

		//Merke: Nur mit dem Flag "use_file_expansion" ist das Expansion Objekt normalerweise gefüllt.			
		if(this.getFileExpansionObject()==null) {
			sReturn =  sDirectory +File.separator + sFileOnly + sEnding;							
			break main;
		}else{
			String sExpandValue = this.getExpansionFirst();						
			sReturn = sDirectory + File.separator + sFileOnly + sExpandValue + sEnding;					
			break main;			
		}			 
	}//end main
	return sReturn;
}

private String PathNameTotalExpandedCurrentCompute_(String sDirectoryIn, String sFileIn) throws ExceptionZZZ{
	String sReturn = new String("");
	main:{
		String sDirectory;
		if(StringZZZ.isEmpty(sDirectoryIn)) {
			sDirectory = this.getPathDirectory();
		}else {
			sDirectory=sDirectoryIn;
		}
		
		String sFile; String sFileOnly;String sEnding;
		if(StringZZZ.isEmpty(sFileIn)) {
			sFile = this.getName();
			sFileOnly = this.getNameOnly();	
			sEnding = this.getNameEnd();
		}else {
			sFile = sFileIn;
			sFileOnly = FileEasyZZZ.NameOnlyCompute(sFile);
			sEnding = FileEasyZZZ.NameEndCompute(this.getName());
		}	
		if(sEnding.length() > 0){
			sEnding = "." + sEnding;
		}

		//Merke: Nur mit dem Flag "use_file_expansion" ist das Expansion Objekt normalerweise gefüllt.			
		if(this.getFileExpansionObject()==null) {
			sReturn =  sDirectory + File.separator + sFileOnly + sEnding;						
			break main;
		}else{
			String sExpandValue = this.getExpansionCurrent();
			sReturn = sDirectory + File.separator + sFileOnly + sExpandValue + sEnding;				
			break main;			
		}			 
	}//end main
	return sReturn;
}	
	
	/** void, An expansion has a fixed length. This character is used to fill the missing charakters of a given expansion-number.
	 * Default is '0' ---> e.g. "0001"
	 * but it is possible to change this to e.g.  '-'  --->  "---1"
	 * 
	* Lindhauer; 22.04.2006 07:20:14
	 * @param cExpansionFilling
	 */
	public void setExpansionFilling(char cExpansionFilling) throws ExceptionZZZ{
		if(this.getFileExpansionObject()!=null) {
			this.getFileExpansionObject().setExpansionFilling(cExpansionFilling);
		}
	}
	
	public void setExpansionFilling(String sExpansionFilling) throws ExceptionZZZ{
		if(this.getFileExpansionObject()!=null) {
			 this.getFileExpansionObject().setExpansionFilling(sExpansionFilling);
		}
	}
	
	public String getExpansionFilling()  throws ExceptionZZZ{
		String sReturn = new String("");
		main:{
			if(this.getFileExpansionObject()!=null) {
				sReturn = this.getFileExpansionObject().getExpansionFilling();
			}
		}//end main:
		return sReturn;
	}
	

	


	
	public int getExpansionLength(){	
		if(this.getFileExpansionObject()!=null) {
			return this.getFileExpansionObject().getExpansionLength();
		}else {
			return 0;
		}
	}

	public void setExpansionLength(int iExpansionLength)throws ExceptionZZZ{
		if(this.getFileExpansionObject()!=null) {
			this.getFileExpansionObject().setExpansionLength(iExpansionLength);
		}else {
			  ExceptionZZZ ez = new ExceptionZZZ( iERROR_PROPERTY_MISSING + " kein ExpansionObjekt vorhanden ", iERROR_PROPERTY_MISSING, ReflectCodeZZZ.getMethodCurrentName(), ""); 
			   //doesn�t work. Only works when > JDK 1.4
			   //Exception e = new Exception();
			   //ExceptionZZZ ez = new ExceptionZZZ(stemp,iCode,this, e, "");
			   throw ez;		
		}
	}
	
	
	
	
	/** 
	 @param sFilling
	 @param iEndingValue
	 @param iEndingLength
	 @return String Expansion, e.g. '001'
	 */
	public String ExpansionCompute(String sFilling, int iExpansionValue) {
		String sReturn = new String("");		
		main:{
			IFileExpansionZZZ objFileExpansion = this.getFileExpansionObject();
			if(objFileExpansion==null)break main;
				
			sReturn = objFileExpansion.computeExpansion(sFilling, iExpansionValue);			
		}//end main
		return sReturn;
	} //end function
	
	
	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	

	/* (non-Javadoc)
	 * @see zzzKernel.basic.KernelAssetObjectZZZ#getExceptionObject()
	 */
	public ExceptionZZZ getExceptionObject() {
		return objException;
	}

	//++++++++++++++++++++++++++++++++++++++
	/* (non-Javadoc)
	 * @see zzzKernel.basic.KernelAssetObjectZZZ#setExceptionObject(zzzKernel.custom.ExceptionZZZ)
	 */
	public void setExceptionObject(ExceptionZZZ objException) {
		this.objException = objException;
	}//end function

	@Override
	public IFileExpansionZZZ getFileExpansionObject() {
		return this.objExpansion;
	}

	@Override
	public void setFileExpansionObject(IFileExpansionZZZ objFileExpansion) {
		this.objExpansion = objFileExpansion;
	}

	@Override
	public String getExpansionNext() throws ExceptionZZZ {
		String sReturn = new String("");		
		main:{
			IFileExpansionZZZ objFileExpansion = this.getFileExpansionObject();
			if(objFileExpansion==null)break main;
			
			sReturn = objFileExpansion.getExpansionNext();
		}
		return sReturn;
	}

	@Override
	public String getExpansionCurrent() throws ExceptionZZZ {
		String sReturn = new String("");		
		main:{
			IFileExpansionZZZ objFileExpansion = this.getFileExpansionObject();
			if(objFileExpansion==null)break main;
			
			sReturn = objFileExpansion.getExpansionCurrent();
		}
		return sReturn;
	}

	@Override
	public String getExpansionFirst() throws ExceptionZZZ {
		String sReturn = new String("");		
		main:{
			IFileExpansionZZZ objFileExpansion = this.getFileExpansionObject();
			if(objFileExpansion==null)break main;
			
			sReturn = objFileExpansion.getExpansionFirst();
		}
		return sReturn;
	}
}//end class
