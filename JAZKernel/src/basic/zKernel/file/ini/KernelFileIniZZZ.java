/*
 * Created on 05.10.2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package basic.zKernel.file.ini;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Set;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractList.ArrayListExtendedZZZ;
import basic.zBasic.util.abstractList.HashMapCaseInsensitiveZZZ;
import basic.zBasic.util.abstractList.HashMapExtendedZZZ;
import basic.zBasic.util.datatype.calling.ReferenceArrayZZZ;
import basic.zBasic.util.datatype.calling.ReferenceHashMapZZZ;
import basic.zBasic.util.datatype.calling.ReferenceZZZ;
import basic.zBasic.util.datatype.string.StringArrayZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.file.FileEasyZZZ;
import basic.zBasic.util.file.ini.IniFile;
import basic.zKernel.IKernelConfigSectionEntryZZZ;
import basic.zKernel.IKernelExpressionIniConverterUserZZZ;
import basic.zKernel.IKernelZFormulaIniZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernel.KernelConfigSectionEntryZZZ;
import basic.zKernel.KernelKernelZZZ;
import basic.zKernel.KernelUseObjectZZZ;
import basic.zKernel.KernelZZZ;
import basic.zKernel.cache.ICachableObjectZZZ;
import basic.zKernel.cache.IKernelCacheZZZ;
import basic.zKernel.config.KernelConfigEntryUtilZZZ;
import custom.zKernel.LogZZZ;
import custom.zKernel.file.ini.FileIniZZZ;


/**

@author 0823 ,date 05.10.2004
*/
public class KernelFileIniZZZ extends KernelUseObjectZZZ implements IKernelFileIniZZZ, IKernelExpressionIniConverterUserZZZ, ICachableObjectZZZ{
//20170123: Diese Flags nun per Reflection aus der Enumeration FLAGZ holen und in eine FlagHashmap (s. ObjectZZZ) verwenden.
//	private boolean bFlagFileUnsaved;
//	private boolean bFlagFileNew; // don�t create a file in the constructor
//	private boolean bFlagFileChanged;
//	private boolean bFlagUseFormula=true;  //Falls true, dann wird ggf. die Formel in der ini-Datei aufgelöst. z.B. <Z>[Section A]Value1</Z>. Siehe KernelExpressionIniSolver.
	
//Flags, die alle Z-Objekte haben
//	private boolean bFlagDebug;
//	private boolean bFlagInit;
		
	private IniFile objFileIni;
	private File objFile;
	private HashMapCaseInsensitiveZZZ<String,String> hmVariable;
	
	//20190718: Durch KernelConfigEntryZZZ - Klasse soll es nicht mehr notwendig sein die Werte in KernelFileIniZZZ zu speichern.
	//20190226: Problem: Wenn ein Wert (z.B. <z:Empty/> konvertiert wurde, weiss das die aufrufenden Methode nicht.
	//                   Um auf solch einen Wert reagieren zu können (also im genannten Beispiel zu merken: Der Wert ist absichtlich leer) das Flag und den 
	//private boolean bValueConverted=false;//Wenn durch einen Converter der Wert verändert wurde, dann wird das hier festgehalten.
	//private String sValueRaw=null;
	
	private boolean bSkipCache=false;

	public KernelFileIniZZZ() throws ExceptionZZZ{
		super("init");//20210402: Die direkte FlagVerarbeitung wird nun im ElternObjekt gemacht
		KernelFileIniNew_(null, null, null, null);
	}
	
	public KernelFileIniZZZ(IKernelZZZ objKernel, String sDirectory, String sFilename, String[] saFlagControl) throws ExceptionZZZ{
		super(objKernel, saFlagControl);//20210402: Die direkte FlagVerarbeitung wird nun im ElternObjekt gemacht
		KernelFileIniNew_(null, sDirectory, sFilename, null);
	}
	
	public KernelFileIniZZZ(IKernelZZZ objKernel, String sDirectory, String sFilename, HashMap<String,Boolean> hmFlag) throws ExceptionZZZ{
		super(objKernel, hmFlag);//20210402: Die direkte FlagVerarbeitung wird nun im ElternObjekt gemacht
		KernelFileIniNew_(null, sDirectory, sFilename, null);
	}
	
	public KernelFileIniZZZ(IKernelZZZ objKernel, File objFile,String[] saFlagControl) throws ExceptionZZZ{
		super(objKernel, saFlagControl);//20210402: Die direkte FlagVerarbeitung wird nun im ElternObjekt gemacht
		KernelFileIniNew_(objFile,null, null, null);
	}
	
	public KernelFileIniZZZ(IKernelZZZ objKernel, File objFile,HashMap<String,Boolean> hmFlag) throws ExceptionZZZ{
		super(objKernel,hmFlag);//20210402: Die direkte FlagVerarbeitung wird nun im ElternObjekt gemacht
		KernelFileIniNew_(objFile,null, null, null);
	}
	
	public KernelFileIniZZZ(IKernelZZZ objKernel, String sDirectory, String sFilename, HashMapCaseInsensitiveZZZ<String,String> hmVariable, String[] saFlagControl) throws ExceptionZZZ{
		super(objKernel, saFlagControl);//20210402: Die direkte FlagVerarbeitung wird nun im ElternObjekt gemacht
		KernelFileIniNew_(null, sDirectory, sFilename, null);
	}
	
	public KernelFileIniZZZ(IKernelZZZ objKernel, String sDirectory, String sFilename,  HashMapCaseInsensitiveZZZ<String,String> hmVariable, HashMap<String,Boolean> hmFlag) throws ExceptionZZZ{
		super(objKernel, hmFlag);//20210402: Die direkte FlagVerarbeitung wird nun im ElternObjekt gemacht
		KernelFileIniNew_(null, sDirectory, sFilename, hmVariable);
	}
	
	public KernelFileIniZZZ(IKernelZZZ objKernel, File objFile, HashMapCaseInsensitiveZZZ<String,String> hmVariable, String[] saFlagControl) throws ExceptionZZZ{
		super(objKernel, saFlagControl);//20210402: Die direkte FlagVerarbeitung wird nun im ElternObjekt gemacht
		KernelFileIniNew_(objFile, null, null, hmVariable);
	}
	
	public KernelFileIniZZZ(IKernelZZZ objKernel, File objFile, HashMapCaseInsensitiveZZZ<String,String> hmVariable, HashMap<String,Boolean> hmFlag) throws ExceptionZZZ{
		super(objKernel,hmFlag);//20210402: Die direkte FlagVerarbeitung wird nun im ElternObjekt gemacht
		KernelFileIniNew_(objFile,null, null, hmVariable);
	}
	
	// DESTRUCTOR
	protected void finalize(){
		if(this.objFileIni!=null) this.objFileIni = null;
		if(this.objFile!=null) this.objFile = null;
	}
	
	/** ++++++++++++++++++++++++++++++++++++++++++
	
	 @author 0823 , date: 05.10.2004
	 @param objKernel
	 @param objLog
	 @param sFileName
	 @param saFlagControl
	 @return
	 */
	private boolean KernelFileIniNew_(File objFileIn, String sDirectoryIn, String sFileIn, HashMapCaseInsensitiveZZZ<String,String> hmVariable) throws ExceptionZZZ {
	 boolean bReturn = false;
	 String stemp; boolean btemp; String sLog;
	 main:{
		 	
	 	try{
			if(this.getFlag("init")==true){
				bReturn = true;
				break main;
			}

			if (this.getFlag("filenew")==false){		
				if(objFileIn==null & (sDirectoryIn==null | sFileIn == null)){
					ExceptionZZZ ez = new ExceptionZZZ("Missing Parameter File-Object or File-Path information", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
					throw ez; 
				}
		
				File objFile = null;			 
				if(objFileIn !=null){
					if(objFileIn.exists()==false){
						ExceptionZZZ ez = new ExceptionZZZ("File not found '" + objFileIn.getPath() + "'", iERROR_PARAMETER_VALUE, this, ReflectCodeZZZ.getMethodCurrentName());
						throw ez; 
					}
					if(objFileIn.isDirectory()){
						ExceptionZZZ ez = new ExceptionZZZ("Parameter File is a directory: '" + objFileIn.getPath() + "'", iERROR_PARAMETER_VALUE, this, ReflectCodeZZZ.getMethodCurrentName());
						throw ez; 
					}
					objFile = objFileIn;
						
				}else if(sFileIn.equals("")){
						ExceptionZZZ ez = new ExceptionZZZ( "Parameter Filename is empty: '" + objFile.getPath() + "'", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
						throw ez; 
				}else{
					String sDirectory = FileEasyZZZ.getFileUsedPath(sDirectoryIn);
					if(sDirectory.equals("")){
						objFile = new File(sFileIn);
					}else{
						String sFilePath = FileEasyZZZ.joinFilePathName(sDirectory, sFileIn);
						objFile = new File(sFilePath);
					}				
				}
				
				if(objFile==null){
					sLog = "Configuration File does not exist (=null, für Directory='" + sDirectoryIn +"', File='" + sFileIn +"')'.";
					this.logLineDate(ReflectCodeZZZ.getMethodCurrentName() + ": " + sLog);
					ExceptionZZZ ez = new ExceptionZZZ(sLog, iERROR_PARAMETER_VALUE, this, ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}else if(!FileEasyZZZ.exists(objFile)){
					sLog = "Configuration File does not exist '" + objFile.getAbsolutePath() + "'.";
					this.logLineDate(ReflectCodeZZZ.getMethodCurrentName() + ": " + sLog);
					ExceptionZZZ ez = new ExceptionZZZ(sLog, iERROR_PROPERTY_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
								
				//create the ini-file-object from file-object
				this.setFileObject(objFile);
	
				IniFile objFileIni = new IniFile(objFile.getPath(), false); //the target is not to save the file, everytime an entry is made, performance !!!
				this.objFileIni = objFileIni;		
			}else{
				IniFile objFileIni = new IniFile(); //the target is not to save the file, everytime an entry is made, performance !!!
				this.objFileIni = objFileIni;	
			}
	 	}catch (IOException e){
	 		System.out.println(e.getMessage());	
	 		bReturn = false;
	 		break main;
		 }
	 	
	 	this.setHashMapVariable(hmVariable);
	 	}//end main:
		return bReturn;
	 }//end function
	
	
	/** Gibt true zur�ck, wenn in dem Section String (ab dem 2. Buchstaben) ein Ausrufezeichen steht und ggf. ein Folgebuchstabe.
	 * Also der minimalste Section-Name ist z.b. "a!a"
	* @param sSection
	* @return
	* 
	* lindhauer; 09.01.2008 07:29:59
	 */
	public static boolean isSectionWithSystemNrAny(String sSection){
		boolean bReturn = false;
		main:{
			if(StringZZZ.isEmpty(sSection)) break main;
			if(sSection.length()<=2) break main;
			if(sSection.startsWith("!")) break main;
			if(sSection.endsWith("!")) break main;
			
			bReturn =  StringZZZ.contains(sSection, "!");
		}
		return bReturn;
	}
	
	/** TODO String[], All properties of all sections of the .ini-file. The Array is contains only unique values.
	* Lindhauer; 23.04.2006 10:16:44
	 * @return String[]
	 */
	public String[] getPropertyAll(){
		return this.objFileIni.getVariables();
	}
	
	/** String[], All Properties of a section. The Array is contains only unique values.
	* Lindhauer; 23.04.2006 09:57:53
	 * @return
	 * @throws ExceptionZZZ 
	 */
	public String[] getPropertyAll(String sSectionIn) throws ExceptionZZZ {
		return this.getPropertyAll_(sSectionIn, true);
	}
	

	/** String[], All Properties of a section. The Array is contains only unique values.
	 * @param sSectionIn
	 * @param bSearchApplicationKey: false=restrict this only to the Section, do not search for the applicationKey.
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 02.11.2021, 09:09:48
	 */
	public String[] getPropertyAll(String sSectionIn, boolean bSearchApplicationKey) throws ExceptionZZZ {
		return this.getPropertyAll_(sSectionIn, bSearchApplicationKey);
	}
	
	private String[] getPropertyAll_(String sSectionIn, boolean bSearchApplicationKey) throws ExceptionZZZ {
		String[] saReturn = null;
		main:{
			String sSection;String sApplication; String sSystemNumber;
			check:{
				if(this.objFileIni==null){				
					ExceptionZZZ ez = new ExceptionZZZ( "missing property 'FileIniObject'", iERROR_PROPERTY_MISSING, this, ReflectCodeZZZ.getMethodCurrentName()); 
					throw ez;		
				}
				if(StringZZZ.isEmpty(sSectionIn)){
					ExceptionZZZ ez = new ExceptionZZZ( "missing parameter 'Section'", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName()); 
					throw ez;		
				}else{
					sSection = sSectionIn;
				}			
				
				
			}//end check:
			
			if(bSearchApplicationKey) {
				sApplication = StringZZZ.left(sSection, "!");
				sSystemNumber = StringZZZ.right(sSection, "!");
					
				boolean bFoundSystemNumber=false;String[]saSystemNumber=null;
				if(!StringZZZ.isEmpty(sSystemNumber)) {
					bFoundSystemNumber=true;
					saSystemNumber = this.objFileIni.getVariables(sSection);
					
				}
				
				boolean bFoundApplication=false;String[]saApplication=null;
				if(!StringZZZ.isEmpty(sApplication)) {
					bFoundApplication=true;
					saApplication = this.objFileIni.getVariables(sApplication);
					
				}
				
				if(bFoundSystemNumber && !bFoundApplication) {
					saReturn = saSystemNumber;
					break main;	
				}else if(!bFoundSystemNumber && bFoundApplication) {
					saReturn = saApplication;
					break main;	
				}else if(!bFoundSystemNumber && !bFoundApplication) {
					saReturn = this.objFileIni.getVariables(sSection);
					break main;
				}else {
					String[] saFlag = {"SKIPNULL","ADDMISSING"};
					saReturn = StringArrayZZZ.append(saSystemNumber, saApplication, saFlag);
					break main;	
				}
			}else {
				saReturn = this.objFileIni.getVariables(sSection);
			}		
		}//end main:
		return saReturn;
	}
	
	
	
	/**  String, the value of a property in a section	
	 @author 0823 , date: 18.10.2004
	 @param sSectionIn
	 @param sPropertyIn
	 @return the string entry at the section for the specified property
	 @throws ExceptionZZZ
	 */
	public IKernelConfigSectionEntryZZZ getPropertyValue(String sSectionIn, String sPropertyIn) throws ExceptionZZZ{
		IKernelConfigSectionEntryZZZ objReturn = new KernelConfigSectionEntryZZZ(); //Hier schon die Rückgabe vorbereiten, falls eine weitere Verarbeitung nicht konfiguriert ist.
		main:{
			String sSection; String sProperty;
			check:{
				if(this.objFileIni==null){				
					ExceptionZZZ ez = new ExceptionZZZ( "missing property 'FileIniObject'", iERROR_PROPERTY_MISSING, this, ReflectCodeZZZ.getMethodCurrentName()); 
					throw ez;		
				}
				if(StringZZZ.isEmpty(sSectionIn)){
					ExceptionZZZ ez = new ExceptionZZZ( "missing parameter 'Section'", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName()); 
					throw ez;		
				}else{
					sSection = sSectionIn;
				}			
				if(StringZZZ.isEmpty(sPropertyIn)){
					ExceptionZZZ ez = new ExceptionZZZ("missing parameter 'Property'", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName()); 
					throw ez;		
				}else{
					sProperty = sPropertyIn;
				}					
			}//end check:
			
			System.out.println(ReflectCodeZZZ.getPositionCurrent()+ ": Hole Wert für Section= '" + sSection + "' und Property = '" + sProperty +"'");
			String sReturnRaw = this.objFileIni.getValue(sSection, sProperty);
			if(sReturnRaw==null) break main;
			objReturn.setSection(sSection);
			objReturn.setProperty(sProperty);
			objReturn.setRaw(sReturnRaw);
			objReturn.setValue(sReturnRaw);												
			
			//+++ 20191126: Auslagern der Formelausrechung in einen Utility Klasse. Ziel: Diese Routine von mehreren Stellen aus aufrufen können. 
			boolean bUseExpression = this.getFlag(IKernelExpressionIniConverterUserZZZ.FLAGZ.USEEXPRESSION.name());
			if(bUseExpression) {
				
				//20210725: Noch eine Ebene dazwischen engebaut, da zusätzlich/alternativ zu den einfachen ZFormeln nun auch JSONArray / JSONMap konfigurierbar sind.
				KernelExpressionIniSolverZZZ exDummy = new KernelExpressionIniSolverZZZ();
				String[] saFlagZpassed = this.getFlagZ_passable(true, exDummy);
				
				HashMapCaseInsensitiveZZZ<String,String>hmVariable = this.getHashMapVariable();
				
				KernelExpressionIniSolverZZZ objExpressionSolver = new KernelExpressionIniSolverZZZ((FileIniZZZ) this, hmVariable, saFlagZpassed);
				int iReturnValue = objExpressionSolver.compute(sReturnRaw,objReturn);	
				System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": Ergebnis der Expression ist vom Typ '" + iReturnValue + "'");
			}
		}//end main:
		return objReturn;
	}//end function
	
	/** Returns a Value by SystemNr. If this doesn´t exists the global value will be returned (value without a SystemNr).
	* @param sSectionIn
	* @param sPropertyIn
	* @param sSystemNrIn
	* @return The value of a string in the section. Start with the section + "!" + SystemNr
	* 
	* lindhauer; 09.01.2008 07:15:18
	 * @throws ExceptionZZZ 
	 */
	public IKernelConfigSectionEntryZZZ getPropertyValueSystemNrSearched(String sSectionIn, String sPropertyIn, String sSystemNrIn) throws ExceptionZZZ{
		IKernelConfigSectionEntryZZZ objReturn = new KernelConfigSectionEntryZZZ(); //Hier schon die Rückgabe vorbereiten, falls eine weitere Verarbeitung nicht konfiguriert ist.
		
		main:{
			String sSection; String sProperty; String sSystemNr;
				if(this.objFileIni==null){				
					ExceptionZZZ ez = new ExceptionZZZ( "missing property 'FileIniObject'", iERROR_PROPERTY_MISSING, this, ReflectCodeZZZ.getMethodCurrentName()); 
					throw ez;		
				}
				if(StringZZZ.isEmpty(sSectionIn)){
					ExceptionZZZ ez = new ExceptionZZZ( "missing parameter 'Section'", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName()); 
					throw ez;		
				}else{
					sSection = sSectionIn;
				}			
				if(StringZZZ.isEmpty(sPropertyIn)){
					ExceptionZZZ ez = new ExceptionZZZ("missing parameter 'Property'", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName()); 
					throw ez;		
				}else{
					sProperty = sPropertyIn;
				}
				
				//!!! Falls es schon in der Property ein Ausrufezeichen gibt, so entf�llt der 1. Versuch. Es wird sofort nach der konkreten Section gesucht
				if(! KernelFileIniZZZ.isSectionWithSystemNrAny(sSection)){
					if(StringZZZ.isEmpty(sSystemNrIn)){
						sSystemNr = this.getKernelObject().getSystemNumber();
					}else{
						sSystemNr = sSystemNrIn;
					}
					
					//1. Versuch: Suche die spezielle Nr.
					String stemp = sSection + "!" + sSystemNr;
					objReturn = this.getPropertyValue(stemp, sProperty);
					
					//2. Versuch: Suche die globale Nr, d.h. wie angegeben
					if(!objReturn.hasAnyValue()){
						objReturn = this.getPropertyValue(sSection, sProperty);
					}
				}else{
					objReturn = this.getPropertyValue(sSection, sProperty);
				}// .isSectionWithSystemNrAny(...)				
		}//end main:
		return objReturn;
	}
	
	/** String[], All values of a section.
	* Lindhauer; 24.04.2006 07:34:11
	 * @param sSectionIn
	 * @return
	 * @throws ExceptionZZZ
	 */
	public String[] getPropertyValueAll(String sSectionIn) throws ExceptionZZZ{
		String[] saReturn=null;
		main:{
			String sSection;
			check:{
				if(this.objFileIni==null){				
					ExceptionZZZ ez = new ExceptionZZZ( "missing property 'FileIniObject'", iERROR_PROPERTY_MISSING, this, ReflectCodeZZZ.getMethodCurrentName()); 
					throw ez;		
				}
				if(StringZZZ.isEmpty(sSectionIn)){
					ExceptionZZZ ez = new ExceptionZZZ( "missing parameter 'Section'", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName()); 
					//doesn�t work. Only works when > JDK 1.4
					//Exception e = new Exception();
					//ExceptionZZZ ez = new ExceptionZZZ(stemp,iCode,this, e, "");
					throw ez;						
				}else{
					sSection = sSectionIn;
				}			
			}//END check:
			
			//Nun alle Properties der Section auslesen
			String[] saPropertyAll = this.getPropertyAll(sSection);
			if(saPropertyAll==null) break main;
			
			ArrayList listaString = new ArrayList(saPropertyAll.length);
			for(int icount=0; icount < saPropertyAll.length; icount++){
				 IKernelConfigSectionEntryZZZ objEntry = this.getPropertyValue(sSection, saPropertyAll[icount]);
				 String stemp = objEntry.getValue();
				listaString.add(icount, stemp);
			}
			
			//durch den neuen Parameter wird der Datentyp des zur�ckzugebenden Arrays definiert.
			//Trotzdem braucht man den Typcast.
			saReturn =  (String[]) listaString.toArray(new String[0]);
			
		}//END main:
		return saReturn;
	}
	
	/** 
	 this will add an entry to the ini-file
	 if bFlagSaveImmidiate= false, the change will be kept  in memory only.
	 Then you have to call the save() method to write this to the filesystem.
	 if bFlagSaveImmidiate = true, the change will be written to the file immidiately
	 	 
	 @author 0823 , date: 18.10.2004
	 @param sSectionIn
	 @param sPropertyIn
	 @param sValueIn
	 @param bFlagSaveImmediate
	 @return boolean, true = everything was fine
	 @throws ExceptionZZZ
	 */
	public boolean setPropertyValue(String sSectionIn, String sPropertyIn, String sValueIn, boolean bFlagSaveImmidiate) throws ExceptionZZZ{
		boolean bReturn = false;
			main:{
				try {
				String sSection; String sProperty;String sValue;
				check:{
					if(this.objFileIni==null){
						ExceptionZZZ ez = new ExceptionZZZ("missing property 'FileIniObject'", iERROR_PROPERTY_MISSING, this, ReflectCodeZZZ.getMethodCurrentName()); 
						throw ez;		
					}
					if(StringZZZ.isEmpty(sSectionIn)){
						ExceptionZZZ ez = new ExceptionZZZ( "missing parameter 'Section'", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName()); 
						throw ez;			
					}else{
						sSection = sSectionIn;
					}			
					if(StringZZZ.isEmpty(sPropertyIn)){
						ExceptionZZZ ez = new ExceptionZZZ("missing parameter 'Property'",iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName()); 
						throw ez;						
					}else{
						sProperty = sPropertyIn;
					}				
					
					if(sValueIn==null){
						ExceptionZZZ ez = new ExceptionZZZ("missing parameter 'Value'", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName()); 
						throw ez;	
					}else{
						//Remark: An empty String may be allowed !!! 
						
						//Hole den passenden Converter
						IKernelZFormulaIniZZZ objExpression = KernelZFormulaIniConverterZZZ.getAsObject(sValueIn);
						
						//20190123: Hier den Stringwert in ein ini-Tag wandeln, falls er z.B. Leerstring ist => KernelExpressionIni_Empty Klasse.
						if(objExpression!=null){
							sValue = objExpression.convert(sValueIn);
							if(!sValueIn.equals(sValue)){
								System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0)+ ": Value durch ExpressionIniConverter verändert von '" + sValueIn + "' nach '" + sValue +"'");
							}
						}else{						
							sValue = sValueIn;
						}
					}
				}//end check:
			
				
				//Merke: 20211130: Beim Einlesen in den "Eigenschafts Editor" DLGBox4Ini gab es das Problem, dass <z:Null> zum Leerstring wird " "!!!
				       //          Das darf nicht sein, denn beim Zurückspeichern wird korrekterweise ein Leerstring " " zu <z:Empty>!!!
				       //Lösung: USEEXPRESSION wird nun über den -z Paramter als false übergeben an das neu zu erstellende Kernel-Objekt.
				       //        Damit wird dieser Wert in dem FileIniZZZ Objekt nicht gesetzt => Es findet keine Übersetzung statt. 
				       //        Korrekterweise bleibt dann z.B. <z:Null> bestehen!!!
				
				
				//20211130: Ziel muss es sein den Wert in die passende Section zu füllen, also dort wo schon etwas drin steht.
				TODOGOON; //20211130 DAS IN EINE EIGENE METHODE PACKEN... objFileIniZZZ.useSystemSectionForProperty(sSection, sProperty);
				
				//0. Erst einmal feststellen ob wir in der Haupt- bzw. Eltersection starten oder in der SystemSection.
				boolean bUseSystemKey=false;
				String sValueOld=null;
				String sSectionUsed = null;
				boolean bSystemKey = KernelKernelZZZ.isSystemSection(sSection);
				if(bSystemKey) {	
					sSectionUsed = sSection;
					
					//A) Hole den Wert in der SystemKeySection
					sValueOld = this.objFileIni.getValue(sSectionUsed, sProperty);
					if(StringZZZ.isEmpty(sValueOld)) {
						
						//Wurde in der PARENTSECTION der Wert überhaupt gesetzt?
						sSectionUsed = KernelKernelZZZ.computeSectionFromSystemSection(sSection);
						sValueOld = this.objFileIni.getValue(sSectionUsed, sProperty);
						if(StringZZZ.isEmpty(sValueOld)) {
							bUseSystemKey=true; //also dort auch nicht gesetzt, dann nimm doch den Systemkey
						}else {
							bUseSystemKey=false;
						}
					}else {
						
						bUseSystemKey=true;												
					}
					
				}else {
					//B) Fange mit dem SystemKey an
					String sSystemNumber = this.getKernelObject().getSystemNumber();
					sSectionUsed = KernelKernelZZZ.computeSystemSectionNameForSection(sSection, sSystemNumber);
							
					sValueOld = this.objFileIni.getValue(sSectionUsed, sProperty);
					if(StringZZZ.isEmpty(sValueOld)) {
						bUseSystemKey=false;
					}else {
						
						//Wurde dort der Wert gesetzt?
						sSectionUsed = sSection;
						sValueOld = this.objFileIni.getValue(sSectionUsed, sProperty);
						if(StringZZZ.isEmpty(sValueOld)) {
							bUseSystemKey=true; //also dort auch nicht gesetzt, dann nimm doch den Systemkey
						}else {
							bUseSystemKey=false;
						}
					}
				}
				
				//+++ Nun festlegen, welche konkrete Section für diesen Wert verwendet werden soll
				sSectionUsed=null;
				if(bUseSystemKey) {
					String sSystemNumber = this.getKernelObject().getSystemNumber();
					sSectionUsed = KernelKernelZZZ.computeSystemSectionNameForSection(sSection, sSystemNumber);					
				}else {
					sSectionUsed = KernelKernelZZZ.computeSectionFromSystemSection(sSection);
				}
								
				this.objFileIni.setValue(sSectionUsed, sProperty, sValue);
				if(bFlagSaveImmidiate==true){
					this.objFileIni.saveFile();
				}else{
					this.setFlag("FileUnsaved", true);
				    this.setFlag("FileChanged", true);				 	
				}
				
				 //if this works, everything is fine
				 bReturn = true;
				 				
				} catch (IOException e) {									
									ExceptionZZZ ez =	new ExceptionZZZ(	"IOException: '" + e.getMessage(),iERROR_RUNTIME,this,ReflectCodeZZZ.getMethodCurrentName());
									throw ez;
								}
			}//end main:
			return bReturn;
	}//end function
	
	
	/** boolean, set all property=value entries of a section. 
	* Lindhauer; 25.04.2006 08:39:25
	 * @param sSectionIn
	 * @param objHtValue, the property-value-pairs which should be set.
	 * @param bFlagClearbefore, true: The section will be removed and then created again. False: Only add or replace the values. Value-pairs not contained by the hashtable will not be affected.
	 * @return
	 * @throws ExceptionZZZ 
	 */
	public boolean setSection(String sSectionIn, Hashtable objHtValue, boolean bFlagClearBefore) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			String sSection;
			check:{
				if(this.objFileIni==null){
					ExceptionZZZ ez = new ExceptionZZZ("missing property 'FileIniObject'", iERROR_PROPERTY_MISSING, this, ReflectCodeZZZ.getMethodCurrentName()); 
					throw ez;		
				}
				if(StringZZZ.isEmpty(sSectionIn)){
					ExceptionZZZ ez = new ExceptionZZZ( "missing parameter 'Section'", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName()); 
					throw ez;					
				}else{
					sSection = sSectionIn;
				}			
				if(objHtValue==null){
					ExceptionZZZ ez = new ExceptionZZZ("missing parameter 'Hashtable Object'",iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName()); 
					throw ez;		
				}else if(objHtValue.size() <= 0){
					ExceptionZZZ ez = new ExceptionZZZ( "empty parameter Hashtable Object'", iERROR_PARAMETER_VALUE, this, ReflectCodeZZZ.getMethodCurrentName()); 
					throw ez;		
				}else{
					//Es wird hier nicht mit einer Kopie des Objekts gearbeitet, da auch nur aus dem Objekt gelesen werden soll.
				}				
				
			}//end check:
			
			//##############################
			//Falls gewünscht die Section erst entfernen
			if(bFlagClearBefore == true){
				this.deleteSection(sSection);
			}
			
			//Section erstellen oder auffüllen. Alle Elemente der Hashtable durchgehen
			Enumeration objEnum = objHtValue.keys();
			while(objEnum.hasMoreElements()){
				String sProperty = (String) objEnum.nextElement();
				if(!sProperty.trim().equals("")){
					String sValue = (String) objHtValue.get(sProperty);
					
					//Auch falls der Wert ein Leerstring ist, diesen setzen.
					this.setPropertyValue(sSection, sProperty, sValue, false);
				}
			} //end while
			bReturn = true;
			
			
		}//end main:
		return bReturn;
	}
	
	/** String[], All sections of the ini-File
	* Lindhauer; 23.04.2006 10:01:12
	 * @return
	 * @throws ExceptionZZZ 
	 */
	public String[] getSectionAll() throws ExceptionZZZ{
		String[] saReturn = null;
		main:{
			if(this.objFileIni==null){
				String stemp =  "missing property 'FileIniObject'";
				System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": "+ stemp);
				ExceptionZZZ ez = new ExceptionZZZ(stemp,iERROR_PROPERTY_MISSING, this, ReflectCodeZZZ.getMethodCurrentName()); 
				throw ez;		
			}
						
			//#####
			saReturn = this.objFileIni.getSubjects();
						
		}//end main
		return saReturn;
	}
	
	/** 
	 * removes as section and all of the property-entries of the section 
	
	 @author 0823 , date: 15.11.2004
	 @param sSectionIn, the Name of the section to remove
	 @return boolean, true, if everything was fine.
	 */
	public boolean deleteSection(String sSectionIn) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			try{
			String sSection = new String("");
			if(StringZZZ.isEmpty(sSectionIn)){
				ExceptionZZZ ez = new ExceptionZZZ("missing parameter 'SectionName'", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName()); 
				throw ez;		
			}else{
				sSection = sSectionIn;
			}
			
			
			if(this.objFileIni==null){
				ExceptionZZZ ez = new ExceptionZZZ( "missing property 'FileIniObject'", iERROR_PROPERTY_MISSING, this, ReflectCodeZZZ.getMethodCurrentName()); 
				throw ez;		
			}
			
			
			//#####
			this.objFileIni.deleteSubject(sSection);
			bReturn = true;
			
			//#####			
	} catch (IOException e) {
		ExceptionZZZ ez =	new ExceptionZZZ(	"IOException: '" + e.getMessage(),iERROR_RUNTIME,this,ReflectCodeZZZ.getMethodCurrentName());
		throw ez;
	}
		
		}//end main
		return bReturn;
	}// end function
		
	/** boolean, removes a property in a section. If this is the last property of a section, the section entry will be removed too.
	* Lindhauer; 22.04.2006 14:15:56
	 * @param sSectionIn
	 * @param sPropertyIn
	 * @param bFlagSaveImmediate, true = save this change immediately on disk.
	 * @return true, this property does not exist any more (it might has never existed there).
	 * @throws ExceptionZZZ
	 */
	public boolean deleteProperty(String sSectionIn, String sPropertyIn, boolean bFlagSaveImmediate) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			try{
						
			String sSection = new String("");			
			if(StringZZZ.isEmpty(sSectionIn)){				
				ExceptionZZZ ez = new ExceptionZZZ("missing parameter 'SectionName'",iERROR_PARAMETER_MISSING, this,ReflectCodeZZZ.getMethodCurrentName()); 
				throw ez;				
			}else{
				sSection = sSectionIn;
			}
			String sProperty=new String("");
			if(StringZZZ.isEmpty(sPropertyIn)){
							ExceptionZZZ ez = new ExceptionZZZ("'PropertyName'", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName()); 
							throw ez;		
			}else{
				sProperty = sPropertyIn;
			}
			
			
			if(this.objFileIni==null){
				ExceptionZZZ ez = new ExceptionZZZ("'File Object'", iERROR_PROPERTY_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;		
			}
			
			this.objFileIni.deleteValue(sSection, sProperty);
			if(bFlagSaveImmediate==true){
				this.objFileIni.saveFile();
			}else{
				this.setFlag("FileChanged", true);
				this.setFlag("FileUnsaved", true);
			}
			bReturn = true;    //Wenn das klappt, dann true setzen
			
			
			
			}catch(IOException e){
				ExceptionZZZ ez = new ExceptionZZZ(e.getMessage(),iERROR_RUNTIME,ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}					
		}//end main:
		return bReturn;
	}
	
	
	/* (non-Javadoc)
	@see zzzKernel.basic.KernelObjectZZZ#getFlag(java.lang.String)
	Flags used:<CR>
	- FileChanged
	- FileUnsaved
	- FileNew
	 */
	/*20170123: Soll herausgenommen werden k�nnen wg. ENUMERATION FlagZ Nutzung
	public boolean getFlag(String sFlagName){
		boolean bFunction = false;
		main:{
			if(StringZZZ.isEmpty(sFlagName)) break main;
			bFunction = super.getFlag(sFlagName);
			if(bFunction==true) break main;
	
			//getting the flags of this object
			String stemp = sFlagName.toLowerCase();
			if(stemp.equals("filechanged")){
				bFunction = bFlagFileChanged;
				break main;
			}else if(stemp.equals("fileunsaved")){
				bFunction = bFlagFileUnsaved;
				break main;
			}else if(stemp.equals("filenew")){
				bFunction = bFlagFileNew;
				break main;
			}else if(stemp.equals("useformula")){
				bFunction = bFlagUseFormula;
				break main;
			}
		}//end main:
		return bFunction;
	}
	*/

	/**
	 * @see zzzKernel.basic.KernelUseObjectZZZ#setFlag(java.lang.String, boolean)
	 * @param sFlagName
	 * Flags used:<CR>
	 * - FileChanged, if a value is written to the file the flag is changed to true,
	 * - FileUnsaved, if a value is written to the file the flag is changed to true, if the save() method is used it is reset to false.
	 * 			  source_remove: after copying the source_files will be removed.
	 */
	/*20170123: Soll herausgenommen werden k�nnen wg. ENUMERATION FlagZ Nutzung
	public boolean setFlag(String sFlagName, boolean bFlagValue){
		boolean bFunction = false;
		main:{
			if(StringZZZ.isEmpty(sFlagName)) break main;
			bFunction = super.setFlag(sFlagName, bFlagValue);
			if(bFunction==true) break main;
	
		//setting the flags of this object
		String stemp = sFlagName.toLowerCase();
		if(stemp.equals("fileunchanged")){
			bFlagFileChanged = bFlagValue;
			bFunction = true;
			break main;
		}else if(stemp.equals("fileunsaved")){
			bFlagFileUnsaved = bFlagValue;
			bFunction = true;
			break main;
		}else if(stemp.equals("filenew")){
			bFlagFileNew = bFlagValue;
			bFunction = true;
			break main;
		}else if(stemp.equals("useformula")){
			bFlagUseFormula = bFlagValue;
			bFunction = true;
			break main;
		}
		}//end main:
		return bFunction;
	}
	*/
	
	public boolean save() throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			try {
			
			boolean btemp;
			check:{
				if(this.objFileIni==null){
					ExceptionZZZ ez = new ExceptionZZZ( "'internal fileIni-Object'", iERROR_PROPERTY_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
					throw ez;	
				}						 
			}//end check:
			
			//to avoid unnecessary write access to disk
			btemp = this.getFlag("FileUnsaved");
			if(btemp==true){
				
					this.objFileIni.saveFile();			
							bReturn = true;
							btemp = this.setFlag("FileUnsaved", false);				
			}

			} catch (IOException e) {
							ExceptionZZZ ez = new ExceptionZZZ( "IOException: '" + e.getMessage(),iERROR_RUNTIME, this, ReflectCodeZZZ.getMethodCurrentName());						
							throw ez;
						}
		}//end main:
		
		return bReturn;
	}//end function
	
	public boolean save(String sFileNameIn) throws ExceptionZZZ{
			boolean bReturn = false;
			main:{
				try {
			
			   String sFileName;
				check:{
					if(StringZZZ.isEmpty(sFileNameIn)){
						ExceptionZZZ ez = new ExceptionZZZ("'FileName'", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
						throw ez;					
					}else{
						sFileName = sFileNameIn;
					}

				
					if(this.objFileIni==null){
						ExceptionZZZ ez = new ExceptionZZZ("'internal fileIni-Object'", iERROR_PROPERTY_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
						throw ez;	
					}						 
				}//end check:
			
				//if a filename is passed, no check of the flag is done, because it can be any filename
				this.objFileIni.saveFile(sFileName);			
				bReturn = true;
				

				} catch (IOException e) {
					ExceptionZZZ ez = new ExceptionZZZ(e.getMessage(),iERROR_RUNTIME,ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
							}
			}//end main:
		
			return bReturn;
		}//end function
	
	/** boolean, searches all sections of the ini-file for sSection. Returns true if there is a match.
	* Lindhauer; 19.05.2006 09:11:34
	 * @param sSection
	 * @return
	 * @throws ExceptionZZZ
	 */
	public boolean proofSectionExists(String sSection) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			if(StringZZZ.isEmpty(sSection)){
				String stemp = "'Section'";
				System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": "+ stemp);
				ExceptionZZZ ez = new ExceptionZZZ(stemp, iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;	
			}
		
		//Das Array aller Sections nach der einen Section durchsuchen
		String[] saSectionAll = this.getSectionAll();
		StringArrayZZZ saZZZ = new StringArrayZZZ(saSectionAll);
		bReturn = saZZZ.contains(sSection);
				
		}//END main
		return bReturn;
	}
	
	public boolean proofSectionExistsSearched(String sSection) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			if(StringZZZ.isEmpty(sSection)){
				String stemp = "'Section'";
				System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": "+ stemp);
				ExceptionZZZ ez = new ExceptionZZZ(stemp, iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;	
			}
		
			//1. Suche nach sSection PLUS Systemnumber
			String sSystemNumber = this.getKernelObject().getSystemNumber();
			String sSectionSearch  = KernelKernelZZZ.computeSystemSectionNameForSection(sSection, sSystemNumber);
			bReturn = this.proofSectionExists(sSectionSearch);
			if(bReturn)break main;
			
			//2. Suche nach sSection pur.
			sSectionSearch  = sSection;
			bReturn = this.proofSectionExists(sSectionSearch);
				
		}//END main
		return bReturn;				
	}
	
	/** boolean, searches all sections of the ini-file for sSection and Value. Returns true if there is a match.
	* Lindhauer; 12.02.2019 8:43
	 * @param sSection
	 * @return
	 * @throws ExceptionZZZ
	 */
	public boolean proofValueExists(String sSection, String sValue) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			check:{
				if(StringZZZ.isEmpty(sSection)){
					String stemp = "'Section'";
					System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": "+ stemp);
					ExceptionZZZ ez = new ExceptionZZZ(stemp, iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
					throw ez;	
				}
				if(this.objFileIni==null){
					String stemp =  "missing property 'FileIniObject'";
					System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": "+ stemp);
					ExceptionZZZ ez = new ExceptionZZZ(stemp,iERROR_PROPERTY_MISSING, this, ReflectCodeZZZ.getMethodCurrentName()); 
					throw ez;		
				}
			}//END check:
		
		String stemp = this.objFileIni.getValue(sSection, sValue);
		if(stemp!=null){
			bReturn = true;
		}
		}//END main
		return bReturn;
	}
	
	
	//### Getter / Setter
	public File getFileObject() {
		return objFile;
	}

	/** Merke: Private, damit die Erstellung über den Konstruktor nicht umgangen wird.
	 @param file
	 */
	private void setFileObject(File file) {
		objFile = file;
	}
	

	/** 
	 @date: 26.10.2004
	 @return FileObject
	 */
	public IniFile getFileIniObject() {
		return objFileIni;
	}

	/** Merke: Private, damit die Erstellung über den Konstruktor nicht umgangen wird.
	 @param file
	 */
	private void setFileIniObject(File file) {
		objFile = file;
	}

	public void setHashMapVariable(HashMapCaseInsensitiveZZZ<String,String> hmVariable){
		this.hmVariable = hmVariable;
	}
	public HashMapCaseInsensitiveZZZ<String,String> getHashMapVariable(){
		if(this.hmVariable==null){
			HashMapCaseInsensitiveZZZ<String,String> hmVariable = new HashMapCaseInsensitiveZZZ<String,String>();
			this.setHashMapVariable(hmVariable);		
		}
		return this.hmVariable;
	}
	
	/**Setze die Variable. 
	 *   Besonderheit: Man kann steueren ob Formeln, die diese Variable enthalten kurfristig aus dem Cache genommen werden (skip).
	 *                        Dadurch würden Sie erneut berechnet. 
	 *                        Merke: Das ist Z.B. im TileHexMap Projekt beim Herein-/Herauszoomen und der Berechnung der Seitenlänge des Hexfelds notwendig.
	 * @param sVariable
	 * @param sValue
	 * @param bUncacheFormula
	 */
	public void setVariable(String sVariable, String sValue, boolean bUncacheFormula){
			this.getHashMapVariable().put(sVariable, sValue);		
		
			if(bUncacheFormula){
				//20190817: Mit der Einführung des Cache für Ini - Einträge hat sich gezeigt, dass es notwendig ist, berechnete Ausdrücke nach Änderung der Variablen neu zu holen.
				//                Darum wird für die Ausdrücke, die diese Variablen enthalten, der Cache "geskipped".
				IKernelCacheZZZ objCache = this.getKernelObject().getCacheObject();		
				objCache.isCacheSkippedContainingVariable(true, sVariable);
			}
	}
	
	public void setVariable(String sVariable, String sValue){
		this.setVariable(sVariable, sValue, false);
	}	
	public String getVariable(String sVariable){
		return (String) this.getHashMapVariable().get(sVariable);
	}

	//###### AUS INTERFACE ICachableObjectZZZ
	@Override
	public boolean isCacheSkipped() {
		return this.bSkipCache;
	}

	@Override
	public void isCacheSkipped(boolean bValue) {
		this.bSkipCache=bValue;
	}

	@Override
	public boolean wasValueComputed() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getValueForFilter() {
		// TODO Auto-generated method stub
		return null;
	}
	
	//### Interface
	//aus IKernelExpressionIniConverterUserZZZ
	//Damit eine aufrufende Methode mitbekommt, ob ein Converter den Wert verändert hat.
//	public boolean isValueConverted(){
//		return this.bValueConverted;
//	}
//	public void isValueConverted(boolean bStatus){
//		this.bValueConverted=bStatus;
//	}
//	public String getValueRaw(){
//		return this.sValueRaw;
//	}
//	public void setValueRaw(String sValueRaw){
//		this.sValueRaw = sValueRaw;
//		if(this.sValueRaw!=null){
//			this.isValueConverted(true);
//		}else{
//			this.isValueConverted(false);
//		}
//	}
	
}
