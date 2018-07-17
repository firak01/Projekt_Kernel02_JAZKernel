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

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.datatype.string.StringArrayZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.file.ini.IniFile;
import basic.zKernel.KernelUseObjectZZZ;
import basic.zKernel.KernelZZZ;
import custom.zKernel.LogZZZ;
import custom.zKernel.file.ini.FileIniZZZ;


/**

@author 0823 ,date 05.10.2004
*/
public class KernelFileIniZZZ extends KernelUseObjectZZZ{
//20170123: Diese Flags nun per Reflection aus der Enumeration FLAGZ holen und in eine FlagHashmap (s. ObjectZZZ) verwenden.
//	private boolean bFlagFileUnsaved;
//	private boolean bFlagFileNew; // don�t create a file in the constructor
//	private boolean bFlagFileChanged;
//	private boolean bFlagUseFormula=true;  //Falls true, dann wird ggf. die Formel in der ini-Datei aufgel�st. z.B. <Z>[Section A]Value1</Z>. Siehe KernelExpressionIniSolver.
	
//Flags, die alle Z-Objekte haben
//	private boolean bFlagDebug;
//	private boolean bFlagInit;
		
	/**20130721: Eweitert um HashMap und die Enum-Flags, Compiler auf 1.6 ge�ndert
	 * 
	 */
	public enum FLAGZ{
		FILEUNSAVED, FILENEW, FILECHANGED, USEFORMULA, USEFORMULA_MATH;
	}
	
	
	private IniFile objFileIni;
	private File objFile;

	public KernelFileIniZZZ(KernelZZZ objKernel, String sDirectory, String sFilename, String[] saFlagControl) throws ExceptionZZZ{
		super(objKernel);
		KernelFileIniNew_(null, sDirectory, sFilename, saFlagControl,null);
	}
	
	public KernelFileIniZZZ(KernelZZZ objKernel, String sDirectory, String sFilename, HashMap<String,Boolean> hmFlag) throws ExceptionZZZ{
		super(objKernel);
		KernelFileIniNew_(null, sDirectory, sFilename, null, hmFlag);
	}
	
	public KernelFileIniZZZ(KernelZZZ objKernel, File objFile,String[] saFlagControl) throws ExceptionZZZ{
		super(objKernel);
		KernelFileIniNew_(objFile,null, null,saFlagControl,null);
	}
	
	public KernelFileIniZZZ(KernelZZZ objKernel, File objFile,HashMap<String,Boolean> hmFlag) throws ExceptionZZZ{
		super(objKernel);
		KernelFileIniNew_(objFile,null, null,null,hmFlag);
	}
	
	
	/** ++++++++++++++++++++++++++++++++++++++++++
	
	 @author 0823 , date: 05.10.2004
	 @param objKernel
	 @param objLog
	 @param sFileName
	 @param saFlagControl
	 @return
	 */
	private boolean KernelFileIniNew_(File objFileIn, String sDirectoryIn, String sFileIn, String[] saFlagControlIn, HashMap<String,Boolean>hmFlag) throws ExceptionZZZ {
	 boolean bReturn = false;
	 String stemp; boolean btemp; 
	 main:{
		 	
	 	try{
	 		//Die ggf. vorhandenen Flags setzen.
			if(hmFlag!=null){
				for(String sKey:hmFlag.keySet()){
					this.setFlagZ(sKey, hmFlag.get(sKey));
				}
			}
	 		
	 		
			//setzen der �bergebenen Flags	
				if(saFlagControlIn != null){
					for(int iCount = 0;iCount<=saFlagControlIn.length-1;iCount++){
						stemp = saFlagControlIn[iCount];
						btemp = setFlag(stemp, true);
						if(btemp==false){
							ExceptionZZZ ez = new ExceptionZZZ( "the flag '" + stemp + "' is not available.", iERROR_FLAG_UNAVAILABLE, this, ReflectCodeZZZ.getMethodCurrentName()); 
							throw ez;		 
						}
					}
				}
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
				if(sDirectoryIn!=null){
					if(sDirectoryIn.equals("")){
						objFile = new File(sFileIn);
					}else{
						objFile = new File(sDirectoryIn + File.separator + File.separator + sFileIn);
					}
				}else{
					objFile = new File(sFileIn);	
				}	
			}
			
			//create the ini-file-object from file-object
			this.objFile = objFile;

			IniFile objFileIni = new IniFile(objFile.getPath(), false); //the target is not to save the file, everytime an entry is made, performance !!!
			this.objFileIni = objFileIni;		
			}else{
				//.getflag("fielnew")==true
				IniFile objFileIni = new IniFile(); //the target is not to save the file, everytime an entry is made, performance !!!
				this.objFileIni = objFileIni;	
			}
	 	}catch (IOException e){
	 		System.out.println(e.getMessage());	
	 		bReturn = false;
	 		break main;
		 }
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
	
	/** String[], All Properties of a section. The Array is contains only unique values.
	* Lindhauer; 23.04.2006 09:57:53
	 * @return
	 * @throws ExceptionZZZ 
	 */
	public String[] getPropertyAll(String sSectionIn) throws ExceptionZZZ {
		String[] saReturn = null;
		main:{
			String sSection;
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
			
			saReturn = this.objFileIni.getVariables(sSection);
			
			
		}//end main:
		return saReturn;
	}
	
	/** TODO String[], All properties of all sections of the .ini-file. The Array is contains only unique values.
	* Lindhauer; 23.04.2006 10:16:44
	 * @return String[]
	 */
	public String[] getPropertyAll(){
		return null;
	}
	
	/**  String, the value of a property in a section	
	 @author 0823 , date: 18.10.2004
	 @param sSectionIn
	 @param sPropertyIn
	 @return the string entry at the section for the specified property
	 @throws ExceptionZZZ
	 */
	public String getPropertyValue(String sSectionIn, String sPropertyIn) throws ExceptionZZZ{
		String sReturn = new String("");
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
			
			sReturn = this.objFileIni.getValue(sSection, sProperty);
			
			
			//20070306 dieser Wert kann ggf. eine Formel sein, die sich auf eine andere Section bezieht. Darum:
			if(this.getFlag("useFormula")==true){
				while(KernelExpressionIniSolverZZZ.isExpression(sReturn)){
					
					//20180711: Die Flags an das neue Objekt der Klasse vererben
					KernelExpressionIniSolverZZZ exDummy = new KernelExpressionIniSolverZZZ();
					String[] saFlagZpassed = this.getFlagZ_passable(true, exDummy);
					KernelExpressionIniSolverZZZ ex = new KernelExpressionIniSolverZZZ((FileIniZZZ)this, saFlagZpassed);				
					sReturn = ex.compute(sReturn);
				}
			}
			
			
		}//end main:
		return sReturn;
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
	public String getPropertyValueSystemNrSearched(String sSectionIn, String sPropertyIn, String sSystemNrIn) throws ExceptionZZZ{
		String sReturn = new String("");
		
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
					sReturn = this.getPropertyValue(stemp, sProperty);
			}// .isSectionWithSystemNrAny(...)
	
			//2. Versuch: Suche die globale Nr, d.h. wie angegeben
			if(StringZZZ.isEmpty(sReturn)){
				sReturn = this.getPropertyValue(sSection, sProperty);
			}
		}//end main:
		return sReturn;
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
				String stemp = this.getPropertyValue(sSection, saPropertyAll[icount]);
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
						sValue = sValueIn;		
					}
				}//end check:
			

				this.objFileIni.setValue(sSection, sProperty, sValue);
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
				ExceptionZZZ ez = new ExceptionZZZ( "missing property 'FileIniObject'",iERROR_PROPERTY_MISSING, this, ReflectCodeZZZ.getMethodCurrentName()); 
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



	/** 
	 @date: 26.10.2004
	 @return FileObject
	 */
	public File getFileObject() {
		return objFile;
	}

	/** 
	 @date: 26.10.2004
	 @param file
	 */
	public void setFileObject(File file) {
		objFile = file;
	}

	
	/** boolean, searches all sections of the ini-file for sSection. Returns true if there is a match.
	* Lindhauer; 19.05.2006 09:11:34
	 * @param sSection
	 * @return
	 * @throws ExceptionZZZ
	 */
	public boolean proofSectionExists(String sSection) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			check:{
				if(StringZZZ.isEmpty(sSection)){
					ExceptionZZZ ez = new ExceptionZZZ("'Section'", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
					throw ez;	
				}
			}//END check:
		
		//Das Array aller Sections nach der einen Section durchsuchen
		String[] saSectionAll = this.getSectionAll();
		StringArrayZZZ saZZZ = new StringArrayZZZ(saSectionAll);
		bReturn = saZZZ.contains(sSection);
				
		}//END main
		return bReturn;
	}
}
