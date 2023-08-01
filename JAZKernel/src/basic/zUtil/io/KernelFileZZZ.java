package basic.zUtil.io;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IConstantZZZ;
import basic.zBasic.IObjectZZZ;
import basic.zBasic.ObjectZZZ;
import basic.zBasic.ReflectClassZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.datatype.string.StringArrayZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.file.FileEasyZZZ;
import basic.zBasic.util.math.MathZZZ;
import basic.zKernel.IKernelUserZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernel.flag.IFlagZLocalUserZZZ;
import basic.zKernel.flag.IFlagZUserZZZ;
import basic.zKernel.flag.IFlagZUserZZZ.FLAGZ;
import basic.zKernel.flag.json.FlagZHelperZZZ;
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
public class KernelFileZZZ extends File implements IConstantZZZ, IObjectZZZ, IFileExpansionUserZZZ, IFileExpansionProxyZZZ, IFlagZUserZZZ, IFlagZLocalUserZZZ{
	private static final long serialVersionUID = 2355847392852232484L;
	
	private IFileExpansionZZZ objExpansion=null;
	private ExceptionZZZ objException=null;
	
	private String sDirectoryPath = new String("");
	private String sFileName = new String(""); // Der ganze Dateiname, also: FileNameOnly + "." + Ending
		
	//flags 
	//private boolean bFlagUse_FILE_Expansion; //Zeigt an, ob eine Dateinamens Expansion angehängt werden muss, oder eine bestehende Expansion ersetzt hat.
	public enum FLAGZ{
		USE_FILE_EXPANSION; //Merke: DEBUG und INIT über IFlagZZZ eingebunden werden, weil von ObjectkZZZ kann man ja nicht erben. Es wird schon von anderer Objektklasse geerbt.
	}
	private HashMap<String, Boolean>hmFlag = new HashMap<String, Boolean>();
	private HashMap<String, Boolean>hmFlagPassed = new HashMap<String, Boolean>(); 
	private HashMap<String, Boolean>hmFlagLocal = new HashMap<String, Boolean>();
	
//	### Constructor ##########################
	public KernelFileZZZ() throws ExceptionZZZ{
		this("","","init");
	}
	
	public KernelFileZZZ(String sDirectoryPath, String sFileName) throws ExceptionZZZ{
		super(sDirectoryPath + "\\" + sFileName);
		KernelFileNew_(sDirectoryPath, sFileName, null, (String[])null);
	}
	
	public KernelFileZZZ(String sDirectoryPath, String sFileName, String[] saFlagControlIn) throws ExceptionZZZ{
		super(sDirectoryPath + "\\" + sFileName);
		KernelFileNew_(sDirectoryPath, sFileName, null, saFlagControlIn);
//		String stemp; boolean btemp;
//		main:{
//			
//		//setzen der übergebenen Flags	
//			if(saFlagControlIn != null){
//				for(int iCount = 0;iCount<=saFlagControlIn.length-1;iCount++){
//					stemp = saFlagControlIn[iCount];
//					btemp = setFlag(stemp, true);
//					if(btemp==false){ 								   
//						   ExceptionZZZ ez = new ExceptionZZZ( sERROR_FLAG_UNAVAILABLE + stemp, iERROR_FLAG_UNAVAILABLE, ReflectCodeZZZ.getMethodCurrentName(), ""); 
//						   //doesn�t work. Only works when > JDK 1.4
//						   //Exception e = new Exception();
//						   //ExceptionZZZ ez = new ExceptionZZZ(stemp,iCode,this, e, "");
//						   throw ez;		 
//					}
//				}
//				}
//
//			//+++ Falls das Debug-Flag gesetzt ist, muss nun eine Session �ber das Factory-Objekt erzeugt werden. 
//			// Damit kann auf andere Datenbanken zugegriffen werden (z.B. im Eclipse Debugger)
//			// Besser jedoch ist es beim Debuggen mit einem anderen Tool eine Notes-ID zu verwenden, die ein leeres Passwort hat.
//			btemp = this.getFlag("init");
//			if(btemp==true) break main;
//		
//		this.setPathDirectory(sDirectoryPath);
//		this.setName(sFileName);
//		if(this.getFlag(KernelFileZZZ.FLAGZ.USE_FILE_EXPANSION.name())) {
//			IFileExpansionZZZ objFileExpansion=new FileExpansionZZZ((FileZZZ) this);
//			this.setFileExpansionObject(objFileExpansion);
//		}
//		}//end main		
	}
	
	
	public KernelFileZZZ(String sDirectoryPath, String sFileName, String sFlagControl) throws ExceptionZZZ{
		super(sDirectoryPath + "\\" + sFileName);
		String[] saFlagControl = new String[1];
		saFlagControl[0] = sFlagControl;
		KernelFileNew_(sDirectoryPath, sFileName, null, saFlagControl);		 
	}

	
	/** CONSTRUCTOR
	 @date: 04.10.2004
	 @param sPathTarget
	 @param sFileTarget
	 @param iExpansionLength
	 * @throws ExceptionZZZ 
	 */
	public KernelFileZZZ(String sDirectoryPath, String sFileName, IFileExpansionZZZ objFileExpansion, String[] saFlagControl) throws ExceptionZZZ {		
		super(sDirectoryPath + "\\" + sFileName);
		KernelFileNew_(sDirectoryPath, sFileName, objFileExpansion, saFlagControl);
	}
	private void KernelFileNew_(String sDirectoryPath, String sFileName, IFileExpansionZZZ objFileExpansionIn, String[] saFlagControl) throws ExceptionZZZ {
			main:{
				if(saFlagControl!=null){
					boolean btemp = false;
					for(int icount=0;icount <= saFlagControl.length-1;icount++){
						String stemp = saFlagControl[icount];
						btemp = this.setFlag(stemp, true);
						
						if(btemp==false){ 								   
							   ExceptionZZZ ez = new ExceptionZZZ( IFlagZUserZZZ.sERROR_FLAG_UNAVAILABLE + stemp, IFlagZUserZZZ.iERROR_FLAG_UNAVAILABLE, ReflectCodeZZZ.getMethodCurrentName(), ""); 
							   throw ez;		 
						}
					}
					if(this.getFlag("init")) break main;
				}
				
				this.setPathDirectory(sDirectoryPath);
				this.setName(sFileName);
				
				IFileExpansionZZZ objFileExpansion = null;
				if(objFileExpansionIn!=null) {
					objFileExpansion = objFileExpansionIn;
					this.setFlag(KernelFileZZZ.FLAGZ.USE_FILE_EXPANSION.name(),true);
				}else{
					if(this.getFlag(KernelFileZZZ.FLAGZ.USE_FILE_EXPANSION.name())) {
						objFileExpansion = new FileExpansionZZZ((FileZZZ) this);							
					}
				}
				this.setFileExpansionObject(objFileExpansion);
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
		String sFileName = this.getName();
		return FileEasyZZZ.getNameWithChangedEnd(sFileName, sEnd);	
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

	//### Aus IFlagUserZZZ
	@Override
	public boolean getFlag(IFlagZUserZZZ.FLAGZ objEnumFlag) {
		return this.getFlag(objEnumFlag.name());
	}
	
	@Override
	public boolean setFlag(IFlagZUserZZZ.FLAGZ objEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		return this.setFlag(objEnumFlag.name(), bFlagValue);
	}	
	
	@Override
	public boolean[] setFlag(IFlagZUserZZZ.FLAGZ[] objaEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		boolean[] baReturn=null;
		main:{
			if(!ArrayUtilZZZ.isEmpty(objaEnumFlag)) {
				baReturn = new boolean[objaEnumFlag.length];
				int iCounter=-1;
				for(IFlagZUserZZZ.FLAGZ objEnumFlag:objaEnumFlag) {
					iCounter++;
					boolean bReturn = this.setFlag(objEnumFlag, bFlagValue);
					baReturn[iCounter]=bReturn;
				}
			}
		}//end main:
		return baReturn;
	}
	
	@Override
	public boolean proofFlagExists(IFlagZUserZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
		return this.proofFlagExists(objEnumFlag.name());
	}
	
	//##################################
//		@Override
//		public boolean getFlag(String sFlagName) {
			//Version Vor Java 1.6
//			boolean bFunction = false;
//		main:{
//			if(StringZZZ.isEmpty(sFlagName)) break main;
//			
//			// hier keine Superclass aufrufen, ist ja schon ObjectZZZ
//			// bFunction = super.getFlag(sFlagName);
//			// if(bFunction == true) break main;
//			
//			// Die Flags dieser Klasse setzen
//			String stemp = sFlagName.toLowerCase();
//			if(stemp.equals("debug")){
//				bFunction = this.bFlagDebug;
//				break main;
//			}else if(stemp.equals("init")){
//				bFunction = this.bFlagInit;
//				break main;
//			}else{
//				bFunction = false;
//			}		
//		}	// end main:
	//	
//		return bFunction;	
//			return this.getFlagZ(sFlagName);
//		}
		
//		@Override
//		public boolean setFlag(String sFlagName, boolean bFlagValue) {
//			Version Vor Java 1.6
//			boolean bFunction = true;
//			main:{
//				if(StringZZZ.isEmpty(sFlagName)) break main;
//				
//				// hier keine Superclass aufrufen, ist ja schon ObjectZZZ
//				// bFunction = super.setFlag(sFlagName, bFlagValue);
//				// if(bFunction == true) break main;
//				
//				// Die Flags dieser Klasse setzen
//				String stemp = sFlagName.toLowerCase();
//				if(stemp.equals("debug")){
//					this.bFlagDebug = bFlagValue;
//					bFunction = true;                            //durch diesen return wert kann man "reflexiv" ermitteln, ob es in dem ganzen hierarchie-strang das flag �berhaupt gibt !!!
//					break main;
//				}else if(stemp.equals("init")){
//					this.bFlagInit = bFlagValue;
//					bFunction = true;
//					break main;
//				}else{
//					bFunction = false;
//				}	
//				
//			}	// end main:
//			
//			return bFunction;	
//			try {
//				return this.setFlag(sFlagName, bFlagValue);
//			} catch (ExceptionZZZ e) {
//				System.out.println("ExceptionZZZ (aus compatibilitaetgruenden mit Version vor Java 6 nicht weitergereicht) : " + e.getDetailAllLast());
//				return false;
//			}
//		}
		
		@Override
		public boolean[] setFlag(String[] saFlagName, boolean bFlagValue) throws ExceptionZZZ {
			boolean[] baReturn=null;
			main:{
				if(!StringArrayZZZ.isEmptyTrimmed(saFlagName)) {
					baReturn = new boolean[saFlagName.length];
					int iCounter=-1;
					for(String sFlagName:saFlagName) {
						iCounter++;
						boolean bReturn = this.setFlag(sFlagName, bFlagValue);
						baReturn[iCounter]=bReturn;
					}
				}
			}//end main:
			return baReturn;
		}
		
		/* @see basic.zBasic.IFlagZZZ#getFlagZ(java.lang.String)
		 * 	 Weitere Voraussetzungen:
		 * - Public Default Konstruktor der Klasse, damit die Klasse instanziiert werden kann.
		 * - Innere Klassen muessen auch public deklariert werden.(non-Javadoc)
		 */
		public boolean getFlag(String sFlagName) {
			boolean bFunction = false;
			main:{
				if(StringZZZ.isEmpty(sFlagName)) break main;
											
				HashMap<String, Boolean> hmFlag = this.getHashMapFlag();
				Boolean objBoolean = hmFlag.get(sFlagName.toUpperCase());
				if(objBoolean==null){
					bFunction = false;
				}else{
					bFunction = objBoolean.booleanValue();
				}
								
			}	// end main:
			
			return bFunction;	
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
	public boolean setFlag(String sFlagName, boolean bFlagValue) throws ExceptionZZZ {
		boolean bFunction = false;
		main:{
			if(StringZZZ.isEmpty(sFlagName)) {
				bFunction = true;
				break main;
			}
						
			bFunction = this.proofFlagExists(sFlagName);															
			if(bFunction == true){
				
				//Setze das Flag nun in die HashMap
				HashMap<String, Boolean> hmFlag = this.getHashMapFlag();
				hmFlag.put(sFlagName.toUpperCase(), bFlagValue);
				bFunction = true;								
			}										
		}	// end main:
		
		return bFunction;	
	}
		
	@Override
	public HashMap<String, Boolean>getHashMapFlag(){
		return this.hmFlag;
	}
		
	@Override
	public HashMap<String, Boolean> getHashMapFlagPassed() {
		return this.hmFlagPassed;
	}
	@Override
	public void setHashMapFlagPassed(HashMap<String, Boolean> hmFlagPassed) {
		this.hmFlagPassed = hmFlagPassed;
	}
	
		/**Gibt alle möglichen FlagZ Werte als Array zurück. 
		 * @return
		 * @throws ExceptionZZZ 
		 */
		public String[] getFlagZ() throws ExceptionZZZ{
			String[] saReturn = null;
			main:{	
				saReturn = FlagZHelperZZZ.getFlagsZ(this.getClass());				
			}//end main:
			return saReturn;
		}
	
		/**Gibt alle "true" gesetzten FlagZ - Werte als Array zurück. 
		 * @return
		 * @throws ExceptionZZZ 
		 */
		public String[] getFlagZ(boolean bValueToSearchFor) throws ExceptionZZZ{
			return this.getFlagZ_(bValueToSearchFor, false);
		}
		
		public String[] getFlagZ(boolean bValueToSearchFor, boolean bLookupExplizitInHashMap) throws ExceptionZZZ{
			return this.getFlagZ_(bValueToSearchFor, bLookupExplizitInHashMap);
		}
		
		private String[]getFlagZ_(boolean bValueToSearchFor, boolean bLookupExplizitInHashMap) throws ExceptionZZZ{
			String[] saReturn = null;
			main:{
				ArrayList<String>listasTemp=new ArrayList<String>();
				
				//FALLUNTERSCHEIDUNG: Alle gesetzten FlagZ werden in der HashMap gespeichert. Aber die noch nicht gesetzten FlagZ stehen dort nicht drin.
				//                                  Diese kann man nur durch Einzelprüfung ermitteln.
				if(bLookupExplizitInHashMap) {
					HashMap<String,Boolean>hmFlag=this.getHashMapFlag();
					if(hmFlag==null) break main;
					
					Set<String> setKey = hmFlag.keySet();
					for(String sKey : setKey){
						boolean btemp = hmFlag.get(sKey);
						if(btemp==bValueToSearchFor){
							listasTemp.add(sKey);
						}
					}
				}else {
					//So bekommt man alle Flags zurück, also auch die, die nicht explizit true oder false gesetzt wurden.						
					String[]saFlagZ = this.getFlagZ();
					
					//20211201:
					//Problem: Bei der Suche nach true ist das egal... aber bei der Suche nach false bekommt man jedes der Flags zurück,
					//         auch wenn sie garnicht gesetzt wurden.
					//Lösung:  Statt dessen explitzit über die HashMap der gesetzten Werte gehen....						
					for(String sFlagZ : saFlagZ){
						boolean btemp = this.getFlag(sFlagZ);
						if(btemp==bValueToSearchFor ){ //also 'true'
							listasTemp.add(sFlagZ);
						}
					}
				}
				saReturn = listasTemp.toArray(new String[listasTemp.size()]);
			}//end main:
			return saReturn;
		}
		
		//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
				//++++++++++++++++++++++++
				/* @see basic.zBasic.IFlagZZZ#getFlagZ(java.lang.String)
				 * 	 Weteire Voraussetzungen:
				 * - Public Default Konstruktor der Klasse, damit die Klasse instanziiert werden kann.
				 * - Innere Klassen m�ssen auch public deklariert werden.(non-Javadoc)
				 */
				public boolean getFlagLocal(String sFlagName) {
					boolean bFunction = false;
					main:{
						if(StringZZZ.isEmpty(sFlagName)) break main;
													
						HashMap<String, Boolean> hmFlag = this.getHashMapFlagLocal();
						Boolean objBoolean = hmFlag.get(sFlagName.toUpperCase());
						if(objBoolean==null){
							bFunction = false;
						}else{
							bFunction = objBoolean.booleanValue();
						}
										
					}	// end main:
					
					return bFunction;	
				}
				
				/** DIESE METHODEN MUSS IN ALLEN KLASSEN VORHANDEN SEIN - über Vererbung -, DIE IHRE FLAGS SETZEN WOLLEN
				 * Weitere Voraussetzungen:
				 * - Public Default Konstruktor der Klasse, damit die Klasse instanziiert werden kann.
				 * - Innere Klassen müssen auch public deklariert werden.
				 * @param objClassParent
				 * @param sFlagName
				 * @param bFlagValue
				 * @return
				 * lindhaueradmin, 23.07.2013
				 */
				public boolean setFlagLocal(String sFlagName, boolean bFlagValue) throws ExceptionZZZ {
					boolean bFunction = false;
					main:{
						if(StringZZZ.isEmpty(sFlagName)) {
							bFunction = true;
							break main;
						}
									
						bFunction = this.proofFlagLocalExists(sFlagName);															
						if(bFunction == true){
							
							//Setze das Flag nun in die HashMap
							HashMap<String, Boolean> hmFlag = this.getHashMapFlagLocal();
							hmFlag.put(sFlagName.toUpperCase(), bFlagValue);
							bFunction = true;								
						}										
					}	// end main:
					
					return bFunction;	
				}
				
				@Override
				public boolean[] setFlagLocal(String[] saFlag, boolean bValue) throws ExceptionZZZ {
					boolean[] baReturn=null;
					main:{
						if(!StringArrayZZZ.isEmptyTrimmed(saFlag)) {
							baReturn = new boolean[saFlag.length];
							int iCounter=-1;
							for(String sFlagName:saFlag) {
								iCounter++;
								boolean bReturn = this.setFlagLocal(sFlagName, bValue);
								baReturn[iCounter]=bReturn;
							}
						}
					}//end main:
					return baReturn;
				}
					
				@Override
				public HashMap<String, Boolean>getHashMapFlagLocal(){
					return this.hmFlagLocal;
				}
				
				@Override
				public void setHashMapFlagLocal(HashMap<String, Boolean> hmFlagLocal) {
					this.hmFlagLocal = hmFlagLocal;
				}
				
				/**Gibt alle möglichen FlagZ Werte als Array zurück. 
				 * @return
				 * @throws ExceptionZZZ 
				 */
				public String[] getFlagZLocal() throws ExceptionZZZ{
					String[] saReturn = null;
					main:{	
						saReturn = FlagZHelperZZZ.getFlagsZDirectAvailable(this.getClass());				
					}//end main:
					return saReturn;
				}
			
				/**Gibt alle "true" gesetzten FlagZ - Werte als Array zurück. 
				 * @return
				 * @throws ExceptionZZZ 
				 */
				public String[] getFlagZLocal(boolean bValueToSearchFor) throws ExceptionZZZ{
					return this.getFlagZLocal_(bValueToSearchFor, false);
				}
				
				public String[] getFlagZLocal(boolean bValueToSearchFor, boolean bLookupExplizitInHashMap) throws ExceptionZZZ{
					return this.getFlagZLocal_(bValueToSearchFor, bLookupExplizitInHashMap);
				}
				
				private String[]getFlagZLocal_(boolean bValueToSearchFor, boolean bLookupExplizitInHashMap) throws ExceptionZZZ{
					String[] saReturn = null;
					main:{
						ArrayList<String>listasTemp=new ArrayList<String>();
						
						//FALLUNTERSCHEIDUNG: Alle gesetzten FlagZ werden in der HashMap gespeichert. Aber die noch nicht gesetzten FlagZ stehen dort nicht drin.
						//                                  Diese kann man nur durch Einzelprüfung ermitteln.
						if(bLookupExplizitInHashMap) {
							HashMap<String,Boolean>hmFlag=this.getHashMapFlagLocal();
							if(hmFlag==null) break main;
							
							Set<String> setKey = hmFlag.keySet();
							for(String sKey : setKey){
								boolean btemp = hmFlag.get(sKey);
								if(btemp==bValueToSearchFor){
									listasTemp.add(sKey);
								}
							}
						}else {
							//So bekommt man alle Flags zurück, also auch die, die nicht explizit true oder false gesetzt wurden.						
							String[]saFlagZ = this.getFlagZLocal();
							
							//20211201:
							//Problem: Bei der Suche nach true ist das egal... aber bei der Suche nach false bekommt man jedes der Flags zurück,
							//         auch wenn sie garnicht gesetzt wurden.
							//Lösung:  Statt dessen explitzit über die HashMap der gesetzten Werte gehen....						
							for(String sFlagZ : saFlagZ){
								boolean btemp = this.getFlagLocal(sFlagZ);
								if(btemp==bValueToSearchFor ){ //also 'true'
									listasTemp.add(sFlagZ);
								}
							}
						}
						saReturn = listasTemp.toArray(new String[listasTemp.size()]);
					}//end main:
					return saReturn;
				}
				
				/** DIESE METHODE MUSS IN ALLEN KLASSEN VORHANDEN SEIN - über Vererbung ODER Interface Implementierung -, DIE IHRE FLAGS SETZEN WOLLEN
				 *  SIE WIRD PER METHOD.INVOKE(....) AUFGERUFEN.
				 * @param name 
				 * @param sFlagName
				 * @return
				 * lindhaueradmin, 23.07.2013
				 * @throws ExceptionZZZ 
				 */
				public boolean proofFlagLocalExists(String sFlagName) throws ExceptionZZZ{
					boolean bReturn = false;
					main:{
						if(StringZZZ.isEmpty(sFlagName))break main;
						bReturn = FlagZHelperZZZ.proofFlagZDirectExists(this.getClass(), sFlagName);				
					}//end main:
					return bReturn;
				}
					
				//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++
				//++++++++++++++++++++++++
			
		
		/**Gibt alle "true" gesetzten FlagZ - Werte als Array zurück, die auch als FLAGZ in dem anderen Objekt überhaupt vorhanden sind.
		 *  Merke: Diese Methode ist dazu gedacht FlagZ-Werte von einem Objekt auf ein anderes zu übertragen.	
		 *    
		 * @return
		 * @throws ExceptionZZZ 
		 */
		public String[] getFlagZ_passable(boolean bValueToSearchFor, IFlagZUserZZZ objUsingFlagZ) throws ExceptionZZZ{
			return this.getFlagZ_passable_(bValueToSearchFor, false, objUsingFlagZ);
		}
		
		public String[] getFlagZ_passable(boolean bValueToSearchFor, boolean bLookupExplizitInHashMap, IFlagZUserZZZ objUsingFlagZ) throws ExceptionZZZ{
			return this.getFlagZ_passable_(bValueToSearchFor, bLookupExplizitInHashMap, objUsingFlagZ);
		}
		
		private String[] getFlagZ_passable_(boolean bValueToSearchFor, boolean bLookupExplizitInHashMap, IFlagZUserZZZ objUsingFlagZ) throws ExceptionZZZ{
			String[] saReturn = null;
			main:{
				
				//1. Hole alle FlagZ, DIESER Klasse, mit dem gewünschten Wert.
				String[] saFlag = this.getFlagZ(bValueToSearchFor,bLookupExplizitInHashMap);
				
				//2. Hole alle FlagZ der Zielklasse
				String[] saFlagTarget = objUsingFlagZ.getFlagZ();
				
				//Nun nur die Schnittmenge der beiden StringÄrrays holen.				
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
		public String[] getFlagZ_passable(IFlagZUserZZZ objUsingFlagZ) throws ExceptionZZZ{
			return this.getFlagZ_passable_(objUsingFlagZ);
		}
		
		private String[] getFlagZ_passable_(IFlagZUserZZZ objUsingFlagZ) throws ExceptionZZZ{
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
		
	/** DIESE METHODE MUSS IN ALLEN KLASSEN VORHANDEN SEIN - über Vererbung ODER Interface Implementierung -, DIE IHRE FLAGS SETZEN WOLLEN
	 *  SIE WIRD PER METHOD.INVOKE(....) AUFGERUFEN.
	 * @param name 
	 * @param sFlagName
	 * @return
	 * lindhaueradmin, 23.07.2013
	 * @throws ExceptionZZZ 
	 */
	public boolean proofFlagExists(String sFlagName) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			if(StringZZZ.isEmpty(sFlagName))break main;
			bReturn = FlagZHelperZZZ.proofFlagZExists(this.getClass(), sFlagName);				
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
				String sExpandValue = this.searchExpansionFreeNext();
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
			String sExpandValue = this.searchExpansionFreeLowest();						
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
			String sExpandValue = this.searchExpansionCurrent();
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
	public String searchExpansionFreeNext() throws ExceptionZZZ {
		String sReturn = new String("");		
		main:{
			IFileExpansionZZZ objFileExpansion = this.getFileExpansionObject();
			if(objFileExpansion==null)break main;
			
			sReturn = objFileExpansion.searchExpansionFreeNext();
		}
		return sReturn;
	}

	@Override
	public String searchExpansionCurrent() throws ExceptionZZZ {
		String sReturn = new String("");		
		main:{
			IFileExpansionZZZ objFileExpansion = this.getFileExpansionObject();
			if(objFileExpansion==null)break main;
			
			sReturn = objFileExpansion.searchExpansionCurrent();
		}
		return sReturn;
	}

	@Override
	public String searchExpansionFreeLowest() throws ExceptionZZZ {
		String sReturn = new String("");		
		main:{
			IFileExpansionZZZ objFileExpansion = this.getFileExpansionObject();
			if(objFileExpansion==null)break main;
			
			sReturn = objFileExpansion.searchExpansionFreeLowest();
		}
		return sReturn;
	}

	

	
}//end class
