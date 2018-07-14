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


/**
This class extends File and not ObjectZZZ !!!
==> it inherits the methods of file and can also be used as input for printwriter/reader - objects !!!
==> it implements AssetObjectZZZ for throwing ExcetptionZZZ
==> it doesn�t implement assetKernelZZZ, because this class is used to create the log-file of the kernel.
         I never implement any functionality in an object which is used for this functionality !!!

TODO Einige static Methoden an basic.zBasic.Util.file.FileEasyZZZ abgeben  
 * @author Lindhauer
 */
public class KernelFileZZZ extends File implements IConstantZZZ, IObjectZZZ, IFlagZZZ{ //IFunctionZZZ {
	private static final char cEXPANSION_FILLING_DEFAULT = '0'; 
	private String sFileNameExpandedNext = new String("");
	private String sFileNameExpandedFirst = new String("");
	private String sFileNameExpandedCurrent = new String("");
	//private String sFileNameExpanded = new String("");
	private ExceptionZZZ objException;
	private int iExpansionLength;
	
	private String sFileEnding = new String("");        // die Endung, z.B. txt
	private String sFileNameOnly = new String("");      // der Dateiname ohne Endung, also FileNamePure + Expansion
	private String sDirectoryPath = new String("");
	private String sFileName = new String(""); // Der ganze Dateiname, also: FileNamePure + Expansion + "." + Ending
	private char cExpansionFilling=cEXPANSION_FILLING_DEFAULT;
	
	//flags 
	//private boolean bFlagExpansionAppend; //Zeigt an, ob eine Dateinamens Expansion angehängt werden muss, oder eine bestehende Expansion ersetzt hat.
	public enum FLAGZ{
		EXPANSIONAPPEND; //Merke: DEBUG und INIT aus ObjectZZZ sollen über IObjectZZZ eingebunden werden, weil von ObjectkZZZ kann man ja nicht erben. Es wird schon von File geerbt.
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
			
		//seten der übergebenen Flags	
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
		
		this.sDirectoryPath = sDirectoryPath;
		this.sFileName = sFileName;
		this.iExpansionLength = 3; //der Defaultwert
		}//end main		
	}
	
	
	public KernelFileZZZ(String sDirectoryPath, String sFileName, String sFlagControl) throws ExceptionZZZ{
		super(sDirectoryPath + "\\" + sFileName);
		String[] saFlagControl = new String[1];
		saFlagControl[0] = sFlagControl;
		KernelFileNew_(sDirectoryPath, sFileName, 3, saFlagControl);		//3; der Defaultwert 
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
				
				this.sDirectoryPath = sDirectoryPath;
				this.sFileName = sFileName;
				this.iExpansionLength = iExpansionLength;			
			
		}//End main:
		
	}

	
	//### Acessor - Methods ###################
	//+++++++++++++++++++++++++++++++++++++++++
	public String getNameEnd(){
		if(this.sFileEnding.equals("")){
			this.sFileEnding =FileEasyZZZ.NameEndCompute(this.sFileName);
		}
		return this.sFileEnding;
	}
	
	public String getNameWithChangedEnd(String sEnd) throws ExceptionZZZ{
		String sReturn = null;
		
		String sFileName = this.sFileName;
		sReturn = FileEasyZZZ.getNameWithChangedEnd(sFileName, sEnd);
		/*
		int iFileOnlyLength = sFileName.lastIndexOf(".");
		if(iFileOnlyLength > -1){
			sReturn = StringZZZ.left(sFileName, iFileOnlyLength + 1);
			if(StringZZZ.isEmpty(sReturn)){
				sReturn = sFileName + File.separator + sEnd;
			}else{
				sReturn = sReturn + sEnd;
			}
		}else{
			sReturn = sFileName + File.separator + sEnd;
		}*/
		return sReturn;
	}
	
	
	
	//++++++++++++++++++++++++++++++++++++++++++
	public String getNameOnly() throws ExceptionZZZ{
		if(this.sFileNameOnly.equals("")){
			this.sFileNameOnly =FileEasyZZZ.NameOnlyCompute(this.sFileName);	
		}
		return this.sFileNameOnly;
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
		if(this.sFileNameExpandedNext.equals("")){
			String stemp = PathNameTotalExpandedNextCompute();
			File objFile = new File(stemp);
			this.sFileNameExpandedNext = objFile.getName();  				
		}
		return this.sFileNameExpandedNext;
	}
	
	public String getNameExpandedFirst() throws ExceptionZZZ{
		if(this.sFileNameExpandedFirst.equals("")){
					String stemp = PathNameTotalExpandedFirstCompute();
					File objFile = new File(stemp);
					this.sFileNameExpandedFirst = objFile.getName();  				
				}
				return this.sFileNameExpandedFirst;
	}
	
	public String getNameExpandedCurrent() throws ExceptionZZZ{
		if(this.sFileNameExpandedCurrent.equals("")){
					String stemp = PathNameTotalExpandedCurrentCompute();
					File objFile = new File(stemp);
					this.sFileNameExpandedCurrent = objFile.getName();  				
				}
				return this.sFileNameExpandedCurrent;
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
//				if(sTemp.equals("expansionappend")){
//						bFunction = this.bFlagExpansionAppend;
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
//				if(sTemp.equals("expansionappend")){
//					this.bFlagExpansionAppend = bValue;
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
	public String PathNameTotalExpandedNextCompute(String sDirectoryNameIn, String sFileNameIn,int iExpandLengthIn) throws ExceptionZZZ{
		String sReturn = new String("");
		main:{
			int iFileLength;
			int iExpandLength;
			String sFileName = new String("");
			String sDirectoryName = new String("");
			
			//////
			paramcheck:
			{
				if(sDirectoryNameIn == null){
					sDirectoryName = this.sDirectoryPath;
				}else if(sDirectoryNameIn.equals("")){
					sDirectoryName = this.sDirectoryPath;
				}
				
				
				if(sFileNameIn == null){
					sFileName = this.sFileName;
				}else if(sFileNameIn.equals("")){
					sFileName = this.sFileName;
				}
				
				//Leere Dateinamen können nicht "expanidert" werden.
				iFileLength = sFileName.length();
				if(iFileLength <= 0){
					break main;
				}				
			
				if(iExpandLengthIn <= 0 || iExpandLengthIn >= 6){
					iExpandLength = 3; //Default
				} else {
					iExpandLength = iExpandLengthIn;				
				}							 
			}//end paramcheck
 	 	
 	 		sReturn = PathNameTotalExpandedNextCompute_(sDirectoryName, sFileName, iExpandLength);
		
		}//end main
		return sReturn;
	}
	
	public String PathNameTotalExpandedFirstCompute(String sDirectoryNameIn, String sFileNameIn,int iExpandLengthIn) throws ExceptionZZZ{
	String sReturn = new String("");
	main:{
		int iFileLength;
		int iExpandLength;
		String sFileName = new String("");
		String sDirectoryName = new String("");
			
		//////
		paramcheck:
		{
			if(sDirectoryNameIn == null){
				sDirectoryName = this.sDirectoryPath;
			}else if(sDirectoryNameIn.equals("")){
				sDirectoryName = this.sDirectoryPath;
			}
				
				
			if(sFileNameIn == null){
				sFileName = this.sFileName;
			}else if(sFileNameIn.equals("")){
				sFileName = this.sFileName;
			}
				
			//Leere Dateinamen können nicht "expanidert" werden.
			iFileLength = sFileName.length();
			if(iFileLength <= 0){
				break main;
			}				
			
			if(iExpandLengthIn <= 0 || iExpandLengthIn >= 6){
				iExpandLength = 3; //Default
			} else {
				iExpandLength = iExpandLengthIn;				
			}							 
		}//end paramcheck
 	 	
		sReturn = PathNameTotalExpandedFirstCompute_(sDirectoryName, sFileName, iExpandLength);
		
	}//end main
	return sReturn;
}


public String PathNameTotalExpandedCurrentCompute(String sDirectoryNameIn, String sFileNameIn,int iExpandLengthIn) throws ExceptionZZZ{
	String sReturn = new String("");
	main:{
		int iFileLength;
		int iExpandLength;
		String sFileName = new String("");
		String sDirectoryName = new String("");
			
		//////
		paramcheck:
		{
			if(sDirectoryNameIn == null){
				sDirectoryName = this.sDirectoryPath;
			}else if(sDirectoryNameIn.equals("")){
				sDirectoryName = this.sDirectoryPath;
			}
				
				
			if(sFileNameIn == null){
				sFileName = this.sFileName;
			}else if(sFileNameIn.equals("")){
				sFileName = this.sFileName;
			}
				
			//Leere Dateinamen k�nnen nicht "expanidert" werden.
			iFileLength = sFileName.length();
			if(iFileLength <= 0){
				break main;
			}				
			
			if(iExpandLengthIn <= 0 || iExpandLengthIn >= 6){
				iExpandLength = 3; //Default
			} else {
				iExpandLength = iExpandLengthIn;				
			}							 
		}//end paramcheck
 	 	
		sReturn = PathNameTotalExpandedCurrentCompute_(sDirectoryName, sFileName, iExpandLength);
		
	}//end main
	return sReturn;
}

public String PathNameTotalExpandedFirstCompute() throws ExceptionZZZ{
	String sReturn = new String("");
	main:{
		sReturn = PathNameTotalExpandedFirstCompute_(this.getPathDirectory(), this.getName(), this.getExpansionLength());				
} //end main
return sReturn;
}

public String PathNameTotalExpandedCurrentCompute() throws ExceptionZZZ{
	String sReturn = new String("");
	main:{
		sReturn = PathNameTotalExpandedCurrentCompute_(this.getPathDirectory(), this.getName(), this.getExpansionLength());				
} //end main
return sReturn;
}


	
	public String PathNameTotalExpandedNextCompute() throws ExceptionZZZ{
		String sReturn = new String("");
		main:{
			sReturn = PathNameTotalExpandedNextCompute_(this.getPathDirectory(), this.getName(), this.getExpansionLength());				
	} //end main
	return sReturn;
	}
	
	private String PathNameTotalExpandedNextCompute_(String sDirectory, String sFile, int iExpansionLength) throws ExceptionZZZ{
		String sReturn = new String("");
		main:{
					String sExpandValue = this.getExpansionNext(iExpansionLength);
					String sFileOnly = this.getNameOnly();
					String sEnding = this.getNameEnd();
					if(sEnding.length() > 0){
						sEnding = "." + sEnding;
					}
		
					if(this.getFlag("ExpansionAppend") == false){
					//if(bFlagExpansionAppend == false){
						sReturn =  this.getPathDirectory() + File.separator + sFileOnly + sEnding;	
						this.sFileNameExpandedNext = sFileOnly + sEnding;
						break main;
					}else{
						sReturn = this.getPathDirectory() + File.separator + sFileOnly + sExpandValue + sEnding;
						this.sFileNameExpandedNext = sFileOnly+sExpandValue+sEnding;
						break main;			
					}			 
		}//end main
		return sReturn;
	}
	
	private String PathNameTotalExpandedFirstCompute_(String sDirectory, String sFile, int iExpansionLength) throws ExceptionZZZ{
	String sReturn = new String("");
	main:{
				String sExpandValue = this.getExpansionFirst(iExpansionLength);
				String sFileOnly = this.getNameOnly();
				String sEnding = this.getNameEnd();
				if(sEnding.length() > 0){
					sEnding = "." + sEnding;
				}
		
				//if(bFlagExpansionAppend == false){
				if(this.getFlag("ExpansionAppend") == false){
					sReturn =  this.getPathDirectory() +File.separator + sFileOnly + sEnding;		
					this.sFileNameExpandedFirst = sFileOnly + sEnding;
					break main;
				}else{
					sReturn = this.getPathDirectory() + File.separator + sFileOnly + sExpandValue + sEnding;
					this.sFileNameExpandedFirst = sFileOnly + sExpandValue + sEnding;
					break main;			
				}			 
	}//end main
	return sReturn;
}

private String PathNameTotalExpandedCurrentCompute_(String sDirectory, String sFile, int iExpansionLength) throws ExceptionZZZ{
	String sReturn = new String("");
	main:{
				String sExpandValue = this.getExpansionCurrent(iExpansionLength);
				String sFileOnly = this.getNameOnly();
				String sEnding = this.getNameEnd();
				if(sEnding.length() > 0){
					sEnding = "." + sEnding;
				}
		
				//if(bFlagExpansionAppend == false){
				if(this.getFlag("ExpansionAppend") == false){
					sReturn =  this.getPathDirectory() + File.separator + sFileOnly + sEnding;		
					this.sFileNameExpandedCurrent = sFileOnly + sEnding;
					break main;
				}else{
					sReturn = this.getPathDirectory() + File.separator + sFileOnly + sExpandValue + sEnding;
					this.sFileNameExpandedCurrent = sFileOnly + sExpandValue + sEnding;
					break main;			
				}			 
	}//end main
	return sReturn;
}	
	
	
	/**
	 @param iExpansionLength
	 @return String, the Expansion which has not been used by any other file, e.g. 000 --> 999
	 * @throws ExceptionZZZ 
	 */
	public String getExpansionNext(int iExpansionLength) throws ExceptionZZZ{
		String sFunction = new String("");
		
		Integer intTemp = new Integer(0);
		String sTemp = new String("");
		boolean bTemp = false;
		
		main:{
			String sExpansionCur = getExpansionCurrent(iExpansionLength);
			//System.out.println("Gefundene letzte Datei-Expansion: '" + sExpansionCur + "'");
			if(sExpansionCur.length() > 0){
				intTemp = new Integer(sExpansionCur);
				sTemp = ExpansionCompute(this.getExpansionFilling(),intTemp.intValue() + 1, iExpansionLength);
				sFunction = sTemp;
			}else{
				bTemp = this.getFlag("ExpansionAppend");
				if(bTemp == true){
					sTemp =  ExpansionCompute(this.getExpansionFilling(), 1, iExpansionLength);
					sFunction = sTemp;
				}
			}		
		}
		
		end:{
			return sFunction;
		}
	} // end function
	
	/** String, An expansion has a fixed length. This character is used to fill the missing charakters of a given expansion-number.
	 * Default is "0" ---> e.g. "0001"
	 * but it is possible to change this to e.g.  "-"  --->  "---1"
	 * 
	* Lindhauer; 22.04.2006 07:15:07
	 * @return String
	 */
	public String getExpansionFilling() {
		String sReturn = String.valueOf(cExpansionFilling);
		if(sReturn.equals("")){
			sReturn = String.valueOf(cEXPANSION_FILLING_DEFAULT);
		}
		return sReturn;
	}
	
	/** void, An expansion has a fixed length. This character is used to fill the missing charakters of a given expansion-number.
	 * Default is '0' ---> e.g. "0001"
	 * but it is possible to change this to e.g.  '-'  --->  "---1"
	 * 
	* Lindhauer; 22.04.2006 07:20:14
	 * @param cExpansionFilling
	 */
	public void setExpansionFilling(char cExpansionFilling){
		//TODO Das Filling-Zeichen muss dahingehend gepr�ft werden, ob es ein Zeichene ist, das in einem Dateinamen enthalten ist
		this.cExpansionFilling = cExpansionFilling;
	}
	
	public void setExpansionFilling(String sExpansionFilling) throws ExceptionZZZ{
		//		TODO Das Filling-Zeichen muss dahingehend gepr�ft werden, ob es ein Zeichene ist, das in einem Dateinamen enthalten ist
		if(sExpansionFilling.length() >= 2) {
			ExceptionZZZ ez = new ExceptionZZZ("An expansion-filling should be one character", iERROR_PARAMETER_VALUE, this,  ReflectCodeZZZ.getMethodCurrentName()); 
			throw ez;		 
		}else if(sExpansionFilling.length() == 0){
			this.cExpansionFilling='0';
		}else{
			this.cExpansionFilling = sExpansionFilling.charAt(0);
		} 
		
		
	}
	

	/**
	 @param iExpansionLength
	 @return String, last used expansion, e.g. 000 ---> 999
	 * @throws ExceptionZZZ 
	 */
	public String getExpansionCurrent(int iExpansionLength) throws ExceptionZZZ{
		String sFunction = new String("");				
		String sExpansionFoundLast = new String("");
		
		boolean bFound = false;
		int iCounter = 0;
		Integer intCounter = new Integer(0);
						
		main:{
			
			//if the current file exists, then a expansion must be appended
			if(this.exists()){
				sFunction = "";
				this.setFlag("ExpansionAppend", true);	
			}
		
		//get file details
		String sPath = this.getPathDirectory();
		if(sPath.length() > 0){
			sPath = sPath + File.separator;
		}
		String sEnding = this.getNameEnd();
		if(sEnding.length() > 0){
			sEnding = "." + sEnding;
		}
		
		//maximale Zahl errechen
		//double dTemp = Math.pow(10, iExpansionLength);
		//iCounter = (int)dTemp;
		intCounter = new Integer(getExpansionMax(iExpansionLength));
		iCounter = intCounter.intValue();
		
			//create new expansions and try their existance.
			do{
				String sExpansion = ExpansionCompute("0", iCounter, iExpansionLength);
				File f = new File(sPath + this.getNameOnly() + sExpansion + sEnding);
				if(f.exists() == true){
					bFound = true;
					sExpansionFoundLast = sExpansion;
					//Remark: Don�t leave this loop, because there can be a gap
				}
				iCounter--;			
			}while(iCounter >= 0 && bFound == false);
		
		//das wird ausserhalb der Schleife gemacht, performance
		if(bFound==true){
			this.setFlag("ExpansionAppend", true);	
		}
			
		}
		
		end:{
			if(this.getFlag("ExpansionAppend") == true){
			sFunction = sExpansionFoundLast;
			}else{
				sFunction = "";
			}
			return sFunction;
		}
	} // end function

	/**
	 @param iExpansionLength
	 @return String, the first found expansion for the file (e.g. the filename itself when there are no files  or  000 --> 999
	 * @throws ExceptionZZZ 
	 */
	public String getExpansionFirst(int iExpansionLength) throws ExceptionZZZ{
		String sFunction = new String("");
		function:{	
			paramCheck:{
				if(iExpansionLength <= 0){
					break function;	
				}
				
				if(this.exists()){
					break function; //falls die Originaldatei existiert, so gilt diese als erste Datei in der Reihenfolge.		
				}
			}//end paramCheck
									
			String sPath = this.getPathDirectory();
			if(sPath.length() > 0){
				sPath = sPath + "\\";
			}
			String sEnding = this.getNameEnd();
			if(sEnding.length() > 0){
				sEnding = "." + sEnding;
			}
			
			//Die h�chste Expansion ermitteln
			Integer intNrOfExpansion = 	new Integer(getExpansionMax(iExpansionLength));
			int iNrOfExpansion = intNrOfExpansion.intValue();
			for (int iCount = 0; iCount <= iNrOfExpansion; iCount++){
					String sExpansion = ExpansionCompute("0", iCount, iExpansionLength);
					File f = new File(sPath + this.getNameOnly() + sExpansion + sEnding);
					if(f.exists() == true){
						this.setFlag("ExpansionAppend",true);
						sFunction = sExpansion;
						break function;
					}
			}
		
			
							
		}//end function
		return sFunction;	
	}
	
	public int getExpansionLength(){		
		return iExpansionLength;
	}

	public void setExpansionLength(int iExpansionLength){
		this.iExpansionLength = iExpansionLength;
	}
	
	
	/** e.g. on an expansion Length of 3. The return value will be '999'
	* @return String
	* @param iExpansionLength
	* 
	* lindhaueradmin; 19.10.2006 09:37:46
	 */
	public static String getExpansionMax(int iExpansionLength){
		String sFunction = null;
		function:{
			int itemp = MathZZZ.pow(10, iExpansionLength);	
			sFunction = Integer.toString(itemp-1); 
		}//end function
		return sFunction;
	}
	
	/** 
	 @param sFilling
	 @param iEndingValue
	 @param iEndingLength
	 @return String Expansion, e.g. '001'
	 */
	public String ExpansionCompute(String sFilling, int iEndingValue, int iEndingLength) {
		String sReturn = new String("");
		String sEndingValue = new String("");
		Integer intEndingValue = new Integer(0);
		main:{
			paramcheck:{
				if(iEndingValue <= 0){
					break main;	
				}	
			}//end paramcheck
		
		intEndingValue = new Integer(iEndingValue);
		sEndingValue = intEndingValue.toString();
				
		if(sEndingValue.length() == iEndingLength){
			//Den Eingangswert zur�ckgeben 
			sReturn = sEndingValue;
		} else if(iEndingLength==0){
			// !!! nix weiter zu tun, es soll keine Endung zur�ckgegeben werden
			sReturn = "";
		}else{
			//F�hrende Nullen setzen
			int iEndingToFill = iEndingLength - sEndingValue.length();
			for(int iCounter = 0; iCounter < iEndingToFill; iCounter++){
				sEndingValue = sFilling + sEndingValue;				
			}
			sReturn = sEndingValue;
		}				
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

	/* (non-Javadoc)
	 * @see zzzKernel.basic.KernelAssetObjectZZZ#setExceptionObject(zzzKernel.custom.ExceptionZZZ)
	 */
	public void setExceptionObject(ExceptionZZZ objException) {
		this.objException = objException;
	}//end function



	
	


	
}//end class
