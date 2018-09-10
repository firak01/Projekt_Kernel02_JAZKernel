package basic.zKernel;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import basic.zBasic.IObjectZZZ;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ObjectZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractList.ArrayListZZZ;
import basic.zBasic.util.abstractList.HashMapCaseInsensitiveZZZ;
import basic.zBasic.util.datatype.string.StringArrayZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.file.FileEasyZZZ;
import basic.zBasic.util.file.ini.IniFile;
import basic.zKernel.file.ini.KernelFileIniZZZ;
import custom.zKernel.ConfigZZZ;
import custom.zKernel.FileFilterModuleZZZ;
import custom.zKernel.LogZZZ;
import custom.zKernel.file.ini.FileIniZZZ;


/**
 * @author 0823
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class KernelZZZ extends ObjectZZZ implements IObjectZZZ,IKernelContextUserZZZ {
	//FLAGZ, die dann zum "Rechnen in der Konfiguations Ini Datei" gesetzt sein müssen.
	public enum FLAGZ{
		USEFORMULA, USEFORMULA_MATH;
	}
	
	public static String sDIRECTORY_CONFIG_DEFAULT="c:\\fglkernel\\kernelconfig";
	private IniFile objIniConfig=null;
	private FileFilterModuleZZZ objFileFilterModule=null;
    //Merke 20180721: Wichtig ist mir, dass die neue HashMap für Variablen NICHT im Kernel-Objekt gespeichert wird. 
	//                        Sie hängt aussschliesslich am Kernel...IniFileZZZ-Objekt. 
	//                        Eventuelle Variablen-"Zustände" werden dann beim Holen der Parameter als Argument übergeben, sind aber NICHT im Kernel-Objekt gespeichert.
	
	private String sSystemNumber="";
	private File objFileKernelConfig=null;
	private FileIniZZZ objFileIniKernelConfig = null;


	private String sFileConfig="";
	private String sApplicationKey="";
	

	private String sDirectoryConfig="";

	private LogZZZ objLog = null;
	private IKernelConfigZZZ objConfig = null;        //die Werte f�r den Applikationskey, Systemnummer, etc.
	private IKernelContextZZZ objContext = null;   //die Werte des aufrufenden PRogramms (bzw. sein Klassenname, etc.)
	
/**  Verwende diesen Konstruktor, wenn die Defaultangaben f�r das Verzeichnis und f�r den ini-Dateinamen verwendet werden sollen:
	 * -Verzeichnis: c:\\fglKernel\\KernelConfig
	 * - Datei:		ZKernelConfigKernel_default.ini
	 * Diese Werte stammen aus ConfigZZZ im custom.zKernel - Verzeichnis.
* lindhauer; 14.08.2007 06:40:31
 * @throws ExceptionZZZ
 */
public KernelZZZ() throws ExceptionZZZ{
	ConfigZZZ objConfig = new ConfigZZZ();
	String[] saFlagControl = new String[1];
	saFlagControl[0] = "init";
	KernelNew_(objConfig, null, null, null, null, null, null,saFlagControl);
}

public KernelZZZ(String[] saFlagControl) throws ExceptionZZZ{
	ConfigZZZ objConfig = new ConfigZZZ();
	KernelNew_(objConfig, null, null, null, null, null, null,saFlagControl);
}
	/**Merke: Damit einzelne Projekte ihr eigenes ConfigZZZ - Objekt verwenden k�nnen, wird in diesem Konstruktor ein Interface eingebaut.
	* lindhauer; 14.08.2007 07:19:55
	 * @param objConfig
	 * @param sFlagControl
	 * @throws ExceptionZZZ
	 */
	public KernelZZZ(IKernelConfigZZZ objConfig, String sFlagControl) throws ExceptionZZZ{
		String[] saFlagControl = new String[1];
		saFlagControl[0] = sFlagControl;
		KernelNew_(objConfig, null, null, null, null, null, null, saFlagControl);
	}
	public KernelZZZ(IKernelConfigZZZ objConfig, String[] saFlagControl) throws ExceptionZZZ{
		KernelNew_(objConfig, null, null, null, null, null, null, saFlagControl);
	}
	
	/**
	CONSTRUCTOR

	TODO: Contructor mit File-Object
	TODO: Finden der Notes.ini. In der Notes.ini eine Section [FritZ-Kernel] einbauen und diese mit einer property versehen, welche den Pfad zur Configurationsdatei beinhaltet.
	            Dies als static Methode anbieten, die ein File-Object zur�ckliefert, dass dann Grundlage f�r die Kernel-Konfiguration ist.
	

	
	 @author 0823 , date: 18.10.2004
	 @param sApplicationKey, the namespace main part e.g. FGL
	 @param sSystemNumber, an additional text to specify different system for the application key, e.g. 01.   
	 Together with the 'application key' this makes the 'system number', e.g. FGL#01
	 @param sFileConfigPath, specifies the directory containing the basic configuration file, e.g. 
	 @param sFileConfigName
	 @param saFlagControl
	 @throws ExceptionZZZ
	 */
	public KernelZZZ(String sApplicationKey, String sSystemNumber, String sFileConfigPath, String sFileConfigName, String[] saFlagControl ) throws ExceptionZZZ{
		KernelNew_(null, null, sApplicationKey, sSystemNumber, sFileConfigPath, sFileConfigName, null, saFlagControl);
	}
	
	public KernelZZZ(String sApplicationKey, String sSystemNumber, String sFileConfigPath, String sFileConfigName, String sFlagControl) throws ExceptionZZZ{
		String[] saFlagControl = new String[1];
		saFlagControl[0] = sFlagControl;
		KernelNew_(null, null, sApplicationKey, sSystemNumber, sFileConfigPath, sFileConfigName, null, saFlagControl);
	}
	
	public KernelZZZ(String sApplicationKey, String sSystemNumber, String sFileConfigPath, String sFileConfigName, IKernelContextZZZ objContext, String sFlagControl) throws ExceptionZZZ{
		String[] saFlagControl = new String[1];
		saFlagControl[0] = sFlagControl;
		KernelNew_(null, objContext, sApplicationKey, sSystemNumber, sFileConfigPath, sFileConfigName,null, saFlagControl);
	}
	
	/** Verwende diesen Konstruktor, wenn die Defaultangaben f�r das Verzeichnis und f�r den ini-Dateinamen verwendet werden sollen:
	 * -Verzeichnis: c:\\fglKernel\\KernelConfig
	 * - Datei:		ZKernelConfigKernel_default.ini
	 * Diese Werte stammen aus ConfigZZZ im custom.zKernel - Verzeichnis.
	 * 
	* lindhauer; 14.08.2007 06:34:16
	 * @param sApplicationKey
	 * @param sSystemNumber
	 * @param sFlagControl
	 * @throws ExceptionZZZ
	 */
	public KernelZZZ(String sApplicationKey, String sSystemNumber, String sFlagControl) throws ExceptionZZZ{
		ConfigZZZ objConfig = new ConfigZZZ();
		String[] saFlagControl = new String[1];
		saFlagControl[0] = sFlagControl;
		KernelNew_(objConfig,null, sApplicationKey, sSystemNumber, null, null, null, saFlagControl);
	}
	
	/**  Verwende diesen Konstruktor, wenn die Defaultangaben f�r das Verzeichnis und f�r den ini-Dateinamen verwendet werden sollen:
	 * -Verzeichnis: c:\\fglKernel\\KernelConfig
	 * - Datei:		ZKernelConfigKernel_default.ini
	 * Diese Werte stammen aus ConfigZZZ im custom.zKernel - Verzeichnis.
	 * 
	* lindhauer; 14.08.2007 06:45:43
	 * @param sApplicationKey
	 * @param sSystemNumber
	 * @param saFlagControl
	 * @throws ExceptionZZZ
	 */
	public KernelZZZ(String sApplicationKey, String sSystemNumber, String[] saFlagControl) throws ExceptionZZZ{
		ConfigZZZ objConfig = new ConfigZZZ();
		KernelNew_(objConfig,null, sApplicationKey, sSystemNumber, null, null, null, saFlagControl);
	}
	
	
	
	/** Choose this construktor, if you want to create another Kernel-Object, using the same configuration and log-file, but another Application
	* Lindhauer; 08.05.2006 08:21:25
	 * @param sApplicationKey
	 * @param objKernelNew
	 * @param saFlagControl
	 * @throws ExceptionZZZ 
	 */
	public KernelZZZ(String sApplicationKey, String sSystemNumber, KernelZZZ objKernelOld, String[] saFlagControl) throws ExceptionZZZ{
		main:{
			check:{
				if(objKernelOld==null){
					  ExceptionZZZ ez = new ExceptionZZZ("Missing kernel-object parameter.", iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName()); 
					  //doesn�t work. Only works when > JDK 1.4
					  //Exception e = new Exception();
					  //ExceptionZZZ ez = new ExceptionZZZ(stemp,iCode,this, e, "");
					  throw ez;		 
				}
			}//END check:
	
		File objFile = objKernelOld.getFileConfigKernel();
		String sFileConfigPath = objFile.getParent();
		String sFileConfigName = objFile.getName();
		LogZZZ objLog = objKernelOld.getLogObject();
		IKernelConfigZZZ objConfig = objKernelOld.getConfigObject();
		
		KernelNew_(objConfig, null, sApplicationKey, sSystemNumber, sFileConfigPath, sFileConfigName, objLog, saFlagControl);
		}//END main
	}
	
	// DESTRICTOR
	protected void finalize(){
		if(this.objIniConfig!=null) this.objIniConfig = null;
		if(this.objFileKernelConfig!=null) this.objFileKernelConfig = null;
	}
	
	
	//############################
	public LogZZZ getLogObject(){
		return this.objLog;
	}
	
	/** 
	The configuration file specifies where other configuration files are.
	The default filename of this file is: 'ZKernelConfigKernel_default.ini'.
	<CR>### Example:
		
	[FGL#01]
#Productive system
KernelLogPath=c:\tempfgl\KernelLog
KernelLogFile=ZKernelLog_default.txt
KernelConfigPathKernel=c:\tempfgl\KernelConfig
KernelConfigFileKernel=ZKernelConfigKernel_default.ini
KernelConfigPathImport=c:\tempfgl\KernelConfig
KernelConfigFileImport=ZKernelConfigImport_default.ini


[FGL#02]
#Test system
KernelLogPath=c:\tempfgl\KernelLog
KernelLogFile=ZKernelLog_default.txt
KernelConfigPathKernel=c:\tempfgl\KernelConfig
KernelConfigFileKernel=ZKernelConfigKernel_default.ini
KernelConfigPathImport=c:\tempfgl\KernelConfig
KernelConfigFileImport=ZKernelConfigImport_default.ini


[FGL#03]
#Developing system
KernelLogPath=c:\tempfgl\KernelLog
KernelLogFile=ZKernelLog_default.txt
KernelConfigPathKernel=c:\tempfgl\KernelConfig
KernelConfigFileKernel=ZKernelConfigKernel_default.ini
KernelConfigPathImport=c:\tempfgl\KernelConfig
KernelConfigFileImport=ZKernelConfigImport_default.ini

### End Example
	
	 @author 0823 , date: 18.10.2004
	 @return File-Object.
	 @throws ExceptionZZZ
	 */
	public File getFileConfigKernel() throws ExceptionZZZ{
		File objReturn = null;
		main:{
			String sLog = null;
			check:{
				//Falls schon mal geholt, nicht neu holen
				if(this.objFileKernelConfig!=null){
					objReturn = this.objFileKernelConfig;
					break main;
				}
				
				String sFileConfig = this.getFileConfigKernelName();
				if(StringZZZ.isEmpty(sFileConfig)){
					sLog = "Missing property: 'Configuration File-Name'";
					System.out.println(sLog);
					ExceptionZZZ ez = new ExceptionZZZ(sLog,iERROR_PROPERTY_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
			}//end check
		
			//1. Versuch
			String sDirectoryConfig = this.getFileConfigKernelDirectory();		
		    objReturn = FileEasyZZZ.getFile(this.sDirectoryConfig, this.sFileConfig);
		    if(objReturn.exists()) {
		    	if(objReturn.isDirectory()){		
		    		sLog = "'Configuration Filename' is a directory: " + this.sDirectoryConfig + File.separator + this.sFileConfig;
		    		System.out.println(sLog);
					ExceptionZZZ ez = new ExceptionZZZ(sLog, iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
		    	break main;
		    }
		    
		    //2. Versuch: Nimm das Default Verzeichnis
			this.sDirectoryConfig = KernelZZZ.sDIRECTORY_CONFIG_DEFAULT;
			objReturn = new File(this.sDirectoryConfig + File.separator + this.sFileConfig);
			
			//Falls der 2. Versuch auch gescheitert ist, wirf Fehler
			if(objReturn.exists()==false){			
				sLog = "'Configuration File' does not exist in the current directory or in: " + this.sDirectoryConfig + File.separator + this.sFileConfig;
				System.out.println(sLog);				
				ExceptionZZZ ez = new ExceptionZZZ(sLog,iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName() );
				throw ez;
			}				
			if(objReturn.isDirectory()){	
				sLog = "'Configuration Filename' is a directory: " + this.sDirectoryConfig + File.separator + this.sFileConfig;
				System.out.println(sLog);
				ExceptionZZZ ez = new ExceptionZZZ(sLog, iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
		}//end main:
		this.objFileKernelConfig = objReturn; 
		return objReturn;
	}
	
	public String getFileConfigKernelName(){
		return this.sFileConfig;
	}
	protected void setFileConfigKernelName(String sFileConfig){
		this.sFileConfig = sFileConfig;
	}
	
	public String getFileConfigKernelDirectory(){
		if(this.sDirectoryConfig.equals("")){
			
				File objDir = new File(KernelZZZ.sDIRECTORY_CONFIG_DEFAULT);
				if(objDir.exists()){
					this.sDIRECTORY_CONFIG_DEFAULT = KernelZZZ.sDIRECTORY_CONFIG_DEFAULT;
				}else{
					this.sDirectoryConfig = ".";
				}
		}
		return this.sDirectoryConfig;
	}
	protected void setFileConfigKernelDirectory(String sDirectoryConfig){
		this.sDirectoryConfig = sDirectoryConfig;
	}
	
	public IniFile getFileConfigKernelAsIni() throws ExceptionZZZ{
		if(this.objIniConfig==null){
			
			File objFile = this.getFileConfigKernel();
			IniFile objIni;
			try {
				objIni = new IniFile(objFile.getPath());
			} catch (IOException e) {
				String sLog = "Configuration File. Not able to create ini-FileObject.";
				System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": " + sLog);
				ExceptionZZZ ez = new ExceptionZZZ(sLog,iERROR_PARAMETER_VALUE, this,  ReflectCodeZZZ.getMethodCurrentName() );
				throw ez;
			}
						
			this.objIniConfig = objIni; 
		}
		return this.objIniConfig;	
	}
	
	public void setFileIniConfigKernel(IniFile objIni){
		//TODO: Müsste dann nicht FileIniZZZ für diesen Kernel mit diesem ggfs. neuen File neu gemacht werden.
		this.objIniConfig = objIni;
	}
	
	public String getApplicationKey(){
		return this.sApplicationKey;	
	}
	protected void setApplicationKey(String sApplicationKey){
		this.sApplicationKey = sApplicationKey;
	}
	
	public String getSystemNumber(){
		if(StringZZZ.isEmpty(this.sSystemNumber)){
			this.setSystemNumber("01");
		}
		return this.sSystemNumber;
	}
	protected void setSystemNumber(String sSystemNumber){
		this.sSystemNumber=sSystemNumber;
	}

	/**
	 * @return sApplicationKey + "!" + sSystemNumber, also der Modulname z.B. f�r das Produktivsystem
	 */
	public String getSystemKey(){
		String stemp = this.getApplicationKey();
		String stemp2 = this.getSystemNumber();
		if(!StringZZZ.isEmpty(stemp2)){
			return stemp + "!" + stemp2;	
		}else{
			return stemp;
		}
	}
	
	public String getKernelKey(){
		return "ZZZ";
	}
	
	/** FileFilterModuleZZZ, also einen FileFilter, der beim Auflisten der Dateien file.list(objFileFilter) eingestzt werden kann. Dieser FileFilter soll nur Modul-Ini Dateien erlauben.
	* Lindhauer; 12.05.2006 08:55:47
	 * @return
	 */
	public FileFilterModuleZZZ getFileFilterModule(){
		if(this.objFileFilterModule==null){
			this.objFileFilterModule = new FileFilterModuleZZZ();
		}
		return this.objFileFilterModule;
	}
	public void setFileFilterModule(FileFilter objFileFilter){
		this.objFileFilterModule = (FileFilterModuleZZZ) objFileFilter;
	}
	
	public FileIniZZZ getFileConfigIni() throws ExceptionZZZ{
		FileIniZZZ objReturn = this.objFileIniKernelConfig;
		if(objReturn==null){
			String sKey = this.getApplicationKey();
			objReturn = this.getFileConfigIniByAlias(sKey);
			this.objFileIniKernelConfig = objReturn;
		}
		return objReturn;	
	}
	public void setFileConfigIni(FileIniZZZ objFileIniKernelConfig){
		this.objFileIniKernelConfig = objFileIniKernelConfig;
	}
	
	/** Reads in  the Kernel-Configuration-File the,directory and name information. Returns a file object. But: Doesn�t proof the file existance !!! <CR>
		Gets the Entries starting with 'KernelConfigPath...', 'KernelConfigFile...', where ... is the Alias.
		<CR><CR>
		e.g. for the alias 'Export' the following entries may be found<CR>
		KernelConfigPathExport=c:\tempfgl\KernelConfig<CR>
		KernelConfigFileExport=ZKernelConfigExport_default.ini<CR>
		
	 @date: 26.10.2004
	 @param sAlias
	 @return FileIniZZZ, like getFileConfigByAlias.
	 @throws ExceptionZZZ
	 */
	public FileIniZZZ getFileConfigIniByAlias(String sAlias) throws ExceptionZZZ{
		FileIniZZZ objReturn = null;		
				main:{
					check:{
						if(sAlias == null){							
							ExceptionZZZ ez = new ExceptionZZZ("Missing parameter: 'Alias'",iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
							throw ez;
						}
						if(sAlias.equals("")){							
							ExceptionZZZ ez = new ExceptionZZZ("Empty parameter: 'Alias'",iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
							throw ez;
						}						
					}//end check:
					
					//first, get the Kernel-Configuration-INI-File
					IniFile objIni = this.getFileConfigKernelAsIni();
					if(objIni==null){
						String sLog = "FileIni";
						System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": " + sLog);
						ExceptionZZZ ez = new ExceptionZZZ(sLog,iERROR_PROPERTY_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
						throw ez;
					}
										
					//1. Versuch: SystemKey
					String sKeyUsed = this.getSystemKey();
					String sFileName =objIni.getValue(sKeyUsed,"KernelConfigFile" +sAlias );
					if(StringZZZ.isEmpty(sFileName)){
						
						//2. Versuch: ApplicationKey
						sKeyUsed = this.getApplicationKey();
						sFileName = objIni.getValue(sKeyUsed, "KernelConfigFile" + sAlias );
						
						if(StringZZZ.isEmpty(sFileName)){
							//3. Versuch: KernelKey
							sKeyUsed = this.getKernelKey();
							sFileName = objIni.getValue(sKeyUsed, "KernelConfigFile" + sAlias );
						
							if(StringZZZ.isEmpty(sFileName)) break main;							
						}												
					}
					
					//Neu: Nun kann die Datei auch im . - Verzeichnis liegen
					String sFilePath = objIni.getValue(sKeyUsed,"KernelConfigPath" +sAlias );					
					if(StringZZZ.isEmpty(sFilePath)) sFilePath = ".";
										
					if(this.objFileIniKernelConfig==null){
						HashMap<String, Boolean> hmFlag = new HashMap<String, Boolean>();					
						FileIniZZZ exDummy = new FileIniZZZ();					
						String[] saFlagZpassed = this.getFlagZ_passable(true, exDummy);						
						objReturn = new FileIniZZZ(this,  sFilePath,sFileName,saFlagZpassed);	
					}else{
						//Übernimm die gesetzten FlagZ...
						HashMap<String,Boolean>hmFlagZ = this.objFileIniKernelConfig.getHashMapFlagZ();
						
						//Übernimm die gesetzten Variablen...
						HashMapCaseInsensitiveZZZ<String,String>hmVariable = this.objFileIniKernelConfig.getHashMapVariable();
						objReturn = new FileIniZZZ(this,  sFilePath,sFileName,hmFlagZ);
						objReturn.setHashMapVariable(hmVariable);	
						
						//Übernimm das ggfs. veränderte ini-File Objekt
						File objFile = this.objFileIniKernelConfig.getFileObject();
						objReturn.setFileObject(objFile);
					}
					
									
					/* Achtung: Es ist nicht Aufgabe dieser Funktion die Existenz der Datei zu pr�fen
					if(objReturn.exists()==false){
						sMethod = this.getMethodCurrentName();
						ExceptionZZZ ez = new ExceptionZZZ("File not found '" + sFilePath + "\\" + sFileName + "'",101, this, sMethod, null );
						throw ez;		
					}
					*/
					this.setFileConfigIni(objReturn);
					
					
				}//end main:
		return objReturn;
	}
	
	/** File[], returns all files of a directory, which matches the Kernel File Filter
	* Lindhauer; 12.05.2006 09:16:05
	 * @param sDir
	 * @return
	 * @throws ExceptionZZZ
	 */
	public File[] getFileConfigAllByDir(String sDirIn) throws ExceptionZZZ{
		File[] objaReturn=null;
		main:{
			String sDir;
			check:{
				if(sDirIn==null)break main;				
				if(sDirIn.equals("")){
					sDir=".";
				}else{
					sDir = sDirIn;
				}			
				//Das ist nun m�glich. Konfigurationsdatein werden dirkt in den Projektordner abgelegt.   if(sDir.equals("")) break main;
			}//END check:
			
//			Das Verzeichnis holen
		File objDir = new File(sDir);
			if(objDir.isDirectory()==false){
				ExceptionZZZ ez = new ExceptionZZZ("The provided string is no directory",iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
		//Den Modul-FileFilter holen
		FileFilterModuleZZZ objFilter = this.getFileFilterModule();
		if(objFilter==null){
			ExceptionZZZ ez = new ExceptionZZZ("Unable to receive the Kernel File Filter for Module files",iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
			throw ez;
		}
				
		//Den Modul-FileFilter einsetzen		
		objaReturn  = objDir.listFiles(objFilter);
		
		}//END main:
		return objaReturn;
	}
	
	/** Reads in  the Kernel-Configuration-File the,directory and name information. Returns a file object. But: Doesn�t proof the file existance !!! <CR>
		Gets the Entries starting with 'KernelConfigPath...', 'KernelConfigFile...', where ... is the Alias.
		<CR><CR>
		e.g. for the alias 'Export' the following entries may be found<CR>
		KernelConfigPathExport=c:\tempfgl\KernelConfig<CR>
		KernelConfigFileExport=ZKernelConfigExport_default.ini<CR>

		 @author 0823 , date: 18.10.2004
		 @param sAlias
		 @return File-Object, Existance is not proofed, null: if not configured.
		 @throws ExceptionZZZ
		 */
	public File getFileConfigByAlias(String sAlias) throws ExceptionZZZ{
			File objReturn = null;
			main:{
						check:{
							if(StringZZZ.isEmpty(sAlias)){								
								ExceptionZZZ ez = new ExceptionZZZ("Missing parameter: 'Alias'",iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
								throw ez;
							}						
						}//end check:
					
						//first, get the Kernel-Configuration-INI-File
						//TODO write ini-file-class for zzz-kernel
						IniFile objIni = this.getFileConfigKernelAsIni();
						
						//1. Versuch: Speziell f�r die Komponente definiert
						String sFileName =objIni.getValue(sAlias,"KernelConfigFile" +sAlias );	
						String sFilePath = null;
						if(StringZZZ.isEmpty(sFileName)) {
																				
							//2. Versuch: Speziell f�r ein System konfiguriert.
							String stemp = this.getSystemKey();													
							sFileName =objIni.getValue(stemp,"KernelConfigFile" +sAlias );		 			
							if(StringZZZ.isEmpty(sFileName)) {
								
								//3. Versuch: Global f�r die Applikation konfiguriert
								stemp = this.getApplicationKey();													
								sFileName =objIni.getValue(stemp,"KernelConfigFile" +sAlias );		 			
								if(StringZZZ.isEmpty(sFileName)) {
								
									
									//4. Versuch: Global f�r die Applikation konfiguriert, mit dem SystemKey
									stemp = this.getApplicationKey();		
									String sAliasTemp = this.getSystemKey();
									sFileName =objIni.getValue(stemp,"KernelConfigFile" +sAliasTemp );		
									if(StringZZZ.isEmpty(sFileName)) {
										
										//5. Versuch: Global f�r die Applikation konfiguriert, mit dem Applikationskey
										stemp = this.getApplicationKey();		
										sAliasTemp = this.getApplicationKey();
										sFileName =objIni.getValue(stemp,"KernelConfigFile" +sAliasTemp );		
										if(StringZZZ.isEmpty(sFileName)) {
										
											//TODO: 20121106 Eigentlich sollte dann die eigene / gleiche Datei zur�ckgegeben werden, oder?
											
											
											//Merke: Das soll keine Exception ausl�sen, sondern es soll null zur�ckgegeben werden.
											//ExceptionZZZ ez = new ExceptionZZZ("no filename for the module '"+ sAlias + "' available in the kernelconfiguration-file. Property: KernelConfigFile"+sAlias+ " was empty.", iERROR_CONFIGURATION_MISSING, this, ReflectionZZZ.getMethodCurrentName());
											//throw ez;
											//Also:
											break main;
										}//end if Versuch 5
									}//end if Versuch 4
								}//end if Versuch 3
							}//end if Versuch 2
							sFilePath = objIni.getValue(stemp,"KernelConfigPath" +sAlias );
							if (StringZZZ.isEmpty(sFilePath)) sFilePath = ".";
							
						}else{
							sFilePath = objIni.getValue(sAlias,"KernelConfigPath" +sAlias );
							if (StringZZZ.isEmpty(sFilePath)) sFilePath = ".";
						}//end if Versuch 1
						
						/* Achtung: Es ist nicht Aufgabe dieser Funktion die Existenz der Datei zu pr�fen*/
						//FGL 20121106: Relative Fileangaben verarbeiten
						objReturn = FileEasyZZZ.getFile(sFilePath, sFileName);
						//objReturn = new File(sFilePath + File.separator + sFileName);
				}//end main:
			return objReturn;
		}
	
	
	/** Parameter f�r ein ganzes Modul holen, aus der Modul-.ini-Datei, die erst noch hier ermittelt werden muss.
	 * Ein Modul ist der Teil, der zwischen dem KernelConfigPath und dem Gleichheitszeichen steht
	 * KernelConfigPath...=,
	 *  bzw. auch KernelConfigFile...=
	 *  Die drei Punkte ... stehen nun f�r den Modulnamen.
	 *        
	 * Dies ist z.B. der Aliasname f�r ein 'Program'-Teil
	 * Um f�r das Program einen Parameter zu holen muss verwendet werden "getParameterByProgramAlias(,,,)
	 * @param objFileConfig, Modul-Konfigurationsfile. Wird z.B. durch  objKernel.getFileConfigByAlias(sModuleAlias); ermittelt.
	 * @param sModuleAlias
	 * @param sParameter
	 * @return String. Wert hinter dem Gleichheitszeichen
	 * @throws ExceptionZZZ
	 */
	public String getParameterByModuleAlias(String sModuleAlias, String sParameter)  throws ExceptionZZZ{
		String sReturn = new String("");
		main:{			
				check:{
							if(sModuleAlias == null){
								ExceptionZZZ ez = new ExceptionZZZ("Missing parameter: 'Module alias'",iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
								throw ez;
							}else if(sModuleAlias.equals("")){
								ExceptionZZZ ez = new ExceptionZZZ("Empty parameter: Module alias",iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
								throw ez;						
							}															
							
							if(sParameter==null){
								ExceptionZZZ ez = new ExceptionZZZ("Missing parameter: 'Parameter'",iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
								throw ez;
							}else if(sParameter.equals("")){
								ExceptionZZZ ez = new ExceptionZZZ("Empty parameter: 'Parameter'",iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
								throw ez;
							}
						}//end check:
				
						//+++ First get the Module-Ini-File
						File objFileModule = this.getFileConfigByAlias(sModuleAlias);
						if(objFileModule==null){
							ExceptionZZZ ez = new ExceptionZZZ("Configuration file not configured for the module'" + sModuleAlias + "'",iERROR_CONFIGURATION_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
							throw ez;	
						};
						if(objFileModule.exists()==false){
							ExceptionZZZ ez = new ExceptionZZZ("Configuration file '" + objFileModule.getAbsolutePath() + "' not exists for the module'" + sModuleAlias + "'",iERROR_CONFIGURATION_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
							throw ez;		
						}
												
						//+++ Now use another method
						sReturn = this.getParameterByModuleFile(objFileModule, sParameter);
			
					}//end main:
	return sReturn;		
}//end function getParameterByProgramAlias(..)
	
	
	public synchronized void setParameterByModuleAlias(String sModuleAlias, String sParameter, String sValue, boolean bSaveImmediate) throws ExceptionZZZ{
		main:{
		check:{
			if(sModuleAlias == null){
				ExceptionZZZ ez = new ExceptionZZZ("Missing parameter: 'Module alias'",iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}else if(sModuleAlias.equals("")){
				ExceptionZZZ ez = new ExceptionZZZ("Empty parameter: Module alias",iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
				throw ez;						
			}															
			
			if(sParameter==null){
				ExceptionZZZ ez = new ExceptionZZZ("Missing parameter: 'Parameter'",iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}else if(sParameter.equals("")){
				ExceptionZZZ ez = new ExceptionZZZ("Empty parameter: 'Parameter'",iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
		}//end check:

		this.KernelSetParameterByModuleAlias_(null, sModuleAlias, null, sParameter, sValue, bSaveImmediate);
		
		/*
		//+++ First get the Module-Ini-File
		File objFileModule = this.getFileConfigByAlias(sModuleAlias);
		if(objFileModule==null) break main;
		if(objFileModule.exists()==false){
			ExceptionZZZ ez = new ExceptionZZZ("Configuration file not found for the module'" + sModuleAlias + "'",iERROR_CONFIGURATION_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
			throw ez;		
		}
		
		//first, get the Ini-file-object
		HashMap<String, Boolean> hmFlag = new HashMap<String, Boolean>();					
		hmFlag.put(FileIniZZZ.FLAGZ.USEFORMULA.name(), true);
		FileIniZZZ objIni = new FileIniZZZ(this,  objFileModule, hmFlag);
		String sSection = this.getSystemKey();
		objIni.setPropertyValue(sSection, sParameter, sValue, bSaveImmediate);
*/
	
		}//END main
		
	}
	
	private boolean KernelSetParameterByModuleAlias_(FileIniZZZ objFileIniConfigIn, String sModule, String sProgramOrSection, String sProperty, String sValue, boolean bFlagSaveImmidiate) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{		
		
			bReturn = KernelSetParameterByProgramAlias_(objFileIniConfigIn, sModule, sProgramOrSection, sProperty, sValue, bFlagSaveImmidiate);
						
			}//END main:
		return bReturn;
	}
	
	
	
	
		
	/** Wie .getParameterByModuleAlias(...), hier wird aber der ApplikationKey als Modul-Alias implizit angenommen.
	 * Das kann dann verwendet werden, wenn das Kernel-Konfigurationsfile die Modulkonfiguration auf sich selbst verweist.
	 * 
	 * Beispiel:
	 * Es gibt eine Datei ZKernelConfig_OVPNClient.ini, die als Parameter bei der Erstellung des Kernel-Objekts �bergeben wurde.
	 * Diese Datei hat folgenden Inhalt: 
	 * 
	 * 
[OVPN#01]
#Produktivsystem
KernelLogPath=c:\fglKernel\KernelLog
KernelLogFile=ZKernelLog_OVPNt.txt

######## Modulkonfiguration ##############
;OpenVPN, weist auf das gleiche File
KernelConfigPathOVPN=
KernelConfigFileOVPN=ZKernelConfig_OVPNClient.ini

MeinTestParameter=blablaErgebnis


	 * 
	 * Nun wird die Methode .getParameter("OVPN", "MeinTestParameter") aufgerufen und das ergibt das gleiche "blablaErgebnis"
	 * wie .getParameter("MeinTestParameter") 
	 * 
	* @return String
	* @param sParameter
	* @return 
	* 
	* lindhaueradmin; 13.07.2006 10:45:57
	 * @throws ExceptionZZZ 
	 */
	public String getParameter(String sParameter) throws ExceptionZZZ{
		String sReturn = null;
		main:{
			String sModulAlias = this.getApplicationKey();
			sReturn = this.getParameterByModuleAlias(sModulAlias, sParameter);
		}//END main:
		return sReturn;
	}
	
	/**Wie get Parameter, aber jetzt wird in den Suchstring auch noch der SystemKey eingebunden.
	 * @param sParameter
	 * @return
	 * @throws ExceptionZZZ
	 * lindhaueradmin, 07.07.2013
	 */
	public String getParameter4System(String sParameter) throws ExceptionZZZ{
		String sReturn = null;
		main:{
			String sModul = this.getApplicationKey();
			String sModulWithKey = this.getSystemKey(); //this.getApplicationKey();
			sReturn = this.getParameterByProgramAlias(sModul, sModulWithKey, sParameter);
		}//END main:
		return sReturn;
	}

	/** Parameter f�r ein ganzes Modul holen, aus der Modul-.ini-Datei, die �bergeben wird.
	 * Dies ist z.B. der Aliasname f�r ein 'Program'-Teil
	 * Um f�r das Program einen Parameter zu holen muss verwendet werden "getParameterByProgramAlias(,,,)
	 * @param objFileConfig, Modul-Konfigurationsfile. Wird z.B. durch  objKernel.getFileConfigByAlias(sModuleAlias); ermittelt.
	 * @param sModuleAlias
	 * @param sParameter
	 * @return String. Wert hinter dem Gleichheitszeichen
	 * @throws ExceptionZZZ
	 */
	public String getParameterByModuleFile(File objFileConfig, String sParameter) throws ExceptionZZZ{
		String sReturn = null;
		main:{
				check:{
								if(objFileConfig == null){
									ExceptionZZZ ez = new ExceptionZZZ("Missing parameter: 'Configuration file-object'",iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
									throw ez;
								}else if(objFileConfig.exists()==false){
									ExceptionZZZ ez = new ExceptionZZZ("Wrong parameter: 'Configuration file-object' does not exist.",iERROR_PARAMETER_VALUE, this,  ReflectCodeZZZ.getMethodCurrentName());
									throw ez;
								}else if(objFileConfig.isDirectory()==true){
									ExceptionZZZ ez = new ExceptionZZZ("Wrong parameter: 'Configuration file-object' is as directory.", iERROR_PARAMETER_VALUE, this,  ReflectCodeZZZ.getMethodCurrentName());
									throw ez;
								}															
								
								if(StringZZZ.isEmpty(sParameter)){
									ExceptionZZZ ez = new ExceptionZZZ("Missing parameter: 'Parameter'",iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
									throw ez;								
								}
							}//end check:
					
							//first, get the Ini-file-object			                 	
							String[] saFlagZpassed = this.getFlagZ_passable(true, this);
							FileIniZZZ objFileIniConfig = new FileIniZZZ(this,  objFileConfig, saFlagZpassed);						
							
							sReturn = this.getParameterByModuleFile(objFileIniConfig,  sParameter);
							//1. Versuch: get the value by SystemKey
//							String sModuleAlias = this.getSystemKey();
//							if(StringZZZ.isEmpty(sModuleAlias)){
//								ExceptionZZZ ez = new ExceptionZZZ("Missing parameter: 'Program Alias'",iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
//								throw ez;
//							}
//							sReturn = objIni.getPropertyValue(sModuleAlias, sParameter);
//							if(StringZZZ.isEmptyNull(sReturn)){
//								//2. Versuch: Get the Value by ApplicationKey
//								sModuleAlias = this.getApplicationKey();
//								if(StringZZZ.isEmpty(sModuleAlias)){
//									ExceptionZZZ ez = new ExceptionZZZ("Missing parameter: 'Program Alias'",iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
//									throw ez;
//								}
//								sReturn = objIni.getPropertyValue(sModuleAlias, sParameter);
//							}
						}//end main:
		return sReturn;		
	}//end function getParameterByProgramAlias(..)

	public String getParameterByModuleFile(FileIniZZZ objFileIniConfig, String sParameter) throws ExceptionZZZ{
			String sReturn = null;
			main:{
					check:{
									if(objFileIniConfig == null){
										ExceptionZZZ ez = new ExceptionZZZ("Missing parameter: 'Configuration file-object'",iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
										throw ez;
									}else if(objFileIniConfig.getFileObject().exists()==false){
										ExceptionZZZ ez = new ExceptionZZZ("Wrong parameter: 'Configuration file-object' does not exist.",iERROR_PARAMETER_VALUE, this,  ReflectCodeZZZ.getMethodCurrentName());
										throw ez;
									}else if(objFileIniConfig.getFileObject().isDirectory()==true){
										ExceptionZZZ ez = new ExceptionZZZ("Wrong parameter: 'Configuration file-object' is as directory.", iERROR_PARAMETER_VALUE, this,  ReflectCodeZZZ.getMethodCurrentName());
										throw ez;
									}
																							
									if(StringZZZ.isEmpty(sParameter)){
										ExceptionZZZ ez = new ExceptionZZZ("Missing parameter: 'Parameter'",iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
										throw ez;
									}
								}//end check:
					
								//get the value
								String sModuleAlias = this.getSystemKey();
								if(StringZZZ.isEmpty(sModuleAlias)){
									ExceptionZZZ ez = new ExceptionZZZ("Missing parameter: 'Program Alias'",iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
									throw ez;
								}
								//sReturn = objFileIniConfig.getPropertyValue(sModuleAlias, sParameter);
								sReturn = KernelGetParameterByModuleFile_(objFileIniConfig, sModuleAlias, sParameter);

							}//end main:
			return sReturn;		
		}//end function getParameterByModuleAlias(..)
		
	
	private String KernelGetParameterByModuleFile_(FileIniZZZ objFileIniConfigIn, String sModuleAlias, String sProperty) throws ExceptionZZZ{
		String sReturn = new String("");
		
		main:{		
			
					sReturn = KernelGetParameterByProgramAlias_(objFileIniConfigIn, sModuleAlias, null, sProperty);
				}//END main:

				return sReturn;
	}
	
	/** String; Parameter basierend auf dem Systemkey holen und dem ALIAS eines Programms, innerhalb der Modul-.ini-Datei.
	 *
	* Der Aliasname des Programms ist dabei eine weitere Section in der .ini-Datei, mit folgendem Aufbau:
	 * [SYSTEMKEY!PROGRAMALIAS]
	 * 
	 * z.B. f�r das Programm mit dem Alias copy1 lautet die Section [FGL#01!copy1]
	 * 
	 * 
	 * Hintergrund:
	 * Ein Programm, z.B. ein Notes-Agent sollte seinen eigenen Namen kennen und das Modul, zu dem es geh�rt.
	 * Mit diesem Namen wird es in der Lage sein aus der Modulkonfiguration in einem ersten Schritt den Aliasnamen zu ermitteln.
	 * und in einem n�chsten Schritt kann dann diese Methode eingesetzt werden.
	 * 
	* Lindhauer; 20.04.2006 07:53:05
	 * @param objFileConfig
	 * @param sProgramAlias
	 * @param sParameter
	 * @return
	 * @throws ExceptionZZZ
	 */
	public String getParameterByProgramAlias(File objFileConfig, String sProgramOrSection, String sProperty) throws ExceptionZZZ{
	String sReturn = null;
	main:{
			check:{
							if(objFileConfig == null){
								ExceptionZZZ ez = new ExceptionZZZ("Missing parameter: 'Configuration file-object'",iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
								throw ez;
							}else if(objFileConfig.exists()==false){
								ExceptionZZZ ez = new ExceptionZZZ("Wrong parameter: 'Configuration file-object' does not exist.",iERROR_PARAMETER_VALUE, this,  ReflectCodeZZZ.getMethodCurrentName());
								throw ez;
							}else if(objFileConfig.isDirectory()==true){
								ExceptionZZZ ez = new ExceptionZZZ("Wrong parameter: 'Configuration file-object' is as directory.", iERROR_PARAMETER_VALUE, this,  ReflectCodeZZZ.getMethodCurrentName());
								throw ez;
							}
								
							if(StringZZZ.isEmpty(sProgramOrSection)){
								ExceptionZZZ ez = new ExceptionZZZ("Missing parameter: 'ProgramOrSection'",iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
								throw ez;
							}
								
							if(StringZZZ.isEmpty(sProperty)){
								ExceptionZZZ ez = new ExceptionZZZ("Missing parameter: 'Property'",iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
								throw ez;
							}
						}//end check:
					
						//first, get the Ini-file-object
						HashMap<String, Boolean> hmFlag = new HashMap<String, Boolean>();					
						hmFlag.put(FileIniZZZ.FLAGZ.USEFORMULA.name(), true);
	
						FileIniZZZ objIni = new FileIniZZZ(this, objFileConfig, hmFlag);
	
						//now call another method
						sReturn = this.getParameterByProgramAlias(objIni, sProgramOrSection, sProperty);
					}//end main:
	return sReturn;		
}//end function getParameterByProgramAlias(..)
	
	/**Gibt den Konfigurierten Wert eines Programms wieder. Dabei werden globale Werte durch "Speziell" f�r die SystemNumber definierte Werte �berschrieben
	 * @param objFileIniConfig
	 * @param sAliasProgramOrSection  
	 * @param sProperty
	 * @return Null, wenn die Property nicht gefunden werden kann, sonst String
	 * @throws ExceptionZZZ, wenn z.B. keine Sektion aus sProgramOrSection in der Konfigurationsdatei existiert.
	 *
	 * javadoc created by: 0823, 20.10.2006 - 09:03:54
	 */
	public String getParameterByProgramAlias(FileIniZZZ objFileIniConfig, String sAliasProgramOrSection, String sProperty) throws ExceptionZZZ{
	String sReturn = null;
	main:{
				check:{
							if(objFileIniConfig == null){
								String stemp = "Missing parameter: 'Configuration file-object'";
								System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": "+ stemp);
								ExceptionZZZ ez = new ExceptionZZZ(stemp,iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
								throw ez;
							}else if(objFileIniConfig.getFileObject().exists()==false){
								String stemp = "Wrong parameter: 'Configuration file-object' does not exist.";
								System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": "+ stemp);
								ExceptionZZZ ez = new ExceptionZZZ(stemp,iERROR_PARAMETER_VALUE, this,  ReflectCodeZZZ.getMethodCurrentName());
								throw ez;
							}else if(objFileIniConfig.getFileObject().isDirectory()==true){
								String stemp = "Wrong parameter: 'Configuration file-object' is as directory.";
								System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": "+ stemp);
								ExceptionZZZ ez = new ExceptionZZZ(stemp,iERROR_PARAMETER_VALUE, this,  ReflectCodeZZZ.getMethodCurrentName());
								throw ez;
							}
									
							if(StringZZZ.isEmpty(sAliasProgramOrSection)){
								String stemp = "Missing parameter: 'ProgramOrSection'";
								System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": "+ stemp);
								ExceptionZZZ ez = new ExceptionZZZ(stemp,iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
								throw ez;
							}
							
							if(StringZZZ.isEmpty(sProperty)){
								String stemp = "Missing parameter: 'Property'";
								System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": "+ stemp);
								ExceptionZZZ ez = new ExceptionZZZ(stemp,iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
								throw ez;
							}
						}//end check:

				String sProgramOrSection = objFileIniConfig.getPropertyValue(this.getSystemKey(), sAliasProgramOrSection);
				if(StringZZZ.isEmpty(sProgramOrSection)){
					sProgramOrSection = sAliasProgramOrSection;
				}
				sReturn = this.KernelGetParameterByProgramAlias_(objFileIniConfig, sProgramOrSection, sAliasProgramOrSection, sProperty);								
	}//end main:
	return sReturn;		
}//end function getParameterByProgramAlias(..)
		
	
	public String getParameterByProgramAlias(String sModule, String sProgramOrSection, String sProperty) throws ExceptionZZZ{
		String sReturn = null;
		main:{
			check:{
				if(StringZZZ.isEmpty(sModule)){
					String stemp = "'String Module'";
					System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": "+ stemp);
					ExceptionZZZ ez = new ExceptionZZZ(stemp,iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}	
				if(StringZZZ.isEmpty(sProgramOrSection)){
					String stemp = "'String ProgramOrSection'";
					System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": "+ stemp);					
					ExceptionZZZ ez = new ExceptionZZZ(stemp,iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}	
				if(StringZZZ.isEmpty(sProperty)){
					String stemp = "'String Property'";
					System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": "+ stemp);
					ExceptionZZZ ez = new ExceptionZZZ(stemp,iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}	
			}//END check:
		
		sReturn = this.KernelGetParameterByProgramAlias_(null, sModule, sProgramOrSection, sProperty);

		}//END main:
		return sReturn;
	}
	
	public String getParameterByProgramAlias(FileIniZZZ objFileIniconfig, String sModule, String sProgramOrSection, String sProperty) throws ExceptionZZZ{
		String sReturn = null;
		main:{
			check:{
				if(objFileIniconfig==null){
					String stemp = "'FileIniZZZ'";
					System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": "+ stemp);
					ExceptionZZZ ez = new ExceptionZZZ(stemp,iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
				if(StringZZZ.isEmpty(sModule)){
					String stemp = "'String Module'";
					System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": "+ stemp);
					ExceptionZZZ ez = new ExceptionZZZ(stemp,iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}	
				if(StringZZZ.isEmpty(sProgramOrSection)){
					String stemp = "'String ProgramOrSection'";
					System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": "+ stemp);
					ExceptionZZZ ez = new ExceptionZZZ(stemp,iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}	
				if(StringZZZ.isEmpty(sProperty)){
					String stemp = "'String Property'";
					System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": "+ stemp);
					ExceptionZZZ ez = new ExceptionZZZ(stemp,iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}	
			}//END check:
		
			sReturn = this.KernelGetParameterByProgramAlias_(objFileIniconfig, sModule, sProgramOrSection, sProperty);
		}//END main:
		return sReturn;
	}
	
	private String KernelGetParameterByProgramAlias_(FileIniZZZ objFileIniConfigIn, String sModule, String sProgramOrSection, String sProperty) throws ExceptionZZZ{
		String sReturn = new String("");
		
		main:{		
			
			//A1. Prüfen, ob das Modul überhaupt konfiguriert ist
			String sModuleUsed;
			if(StringZZZ.isEmpty(sModule)){
				sModuleUsed = this.getApplicationKey();
			}else{
				if(sModule.equals(sProgramOrSection)){
					sModuleUsed = this.getApplicationKey();
				}else{
					sModuleUsed = sModule;
				}
			}
			
			//3. Konfigurationsfile des Moduls holen
			FileIniZZZ objFileIniConfig = null;
		    if(objFileIniConfigIn==null){
		    	objFileIniConfig = this.getFileConfigIniByAlias(sModuleUsed);				
		    }else{
		    	objFileIniConfig = objFileIniConfigIn;
		    }
		    
		    //als Programm...
		    if(objFileIniConfig==null){
		    	objFileIniConfig = this.getFileConfigIniByAlias(sProgramOrSection);
		    }
		    		    
		    if(objFileIniConfig==null){
				String stemp = "FileIniZZZ fuer Modul '" + sModuleUsed + "' ist NULL.";
				System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": "+ stemp);
				ExceptionZZZ ez = new ExceptionZZZ(stemp, iERROR_PARAMETER_VALUE, this,  ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
		    

		    
		    		   	   
		    //+++ Ggfs. als Program deklarierte Section
		    String  sSection = this.getSystemKey() + "!" + sModuleUsed;
			System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0)+ ": Verwende als sSection '"+ sSection + "' für die Suche nach der Property '" + sProperty + "'");			
			if(!StringZZZ.isEmpty(sSection)){
			    boolean bSectionExists = objFileIniConfig.proofSectionExists(sSection);
				if(bSectionExists==true){
					sReturn = objFileIniConfig.getPropertyValue(sSection, sProperty);
					if(sReturn != null) break main;
				}
			}
			
			//+++ First Try: Use SystemKey ! Parameter as section
			sSection  = this.getSystemKey() + "!" + sProgramOrSection;
			System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0)+ ": Verwende als sSection '"+ sSection + "' für die Suche nach der Property '" + sProperty + "'");
			if(!StringZZZ.isEmpty(sSection)){
				boolean bSectionExists = objFileIniConfig.proofSectionExists(sSection);
				if(bSectionExists==true){				
					sReturn = objFileIniConfig.getPropertyValue(sSection, sProperty);
					if(sReturn != null) break main;
				}			
			}
			
			//+++ 
			sSection = sModuleUsed;
			System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0)+ ": Verwende als sSection '"+ sSection + "' für die Suche nach der Property '" + sProperty + "'");
			if(!StringZZZ.isEmpty(sSection)){
				boolean bSectionExists = objFileIniConfig.proofSectionExists(sSection);
				if(bSectionExists==true){
					sReturn = objFileIniConfig.getPropertyValue(sSection, sProperty);
					if(sReturn != null) break main;
				}
			}
			
			//+++ 
			sSection =  sProgramOrSection;
			System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0)+ ": Verwende als sSection '"+ sSection + "' für die Suche nach der Property '" + sProperty + "'");
			if(!StringZZZ.isEmpty(sSection)){
				boolean bSectionExists = objFileIniConfig.proofSectionExists(sSection);
				if(bSectionExists==true){
					sReturn = objFileIniConfig.getPropertyValue(sSection, sProperty);
					if(sReturn != null) break main;
				}
			}
			
			//+++ Einen ggfs. definierten Aliasnamen		
			sSection = objFileIniConfig.getPropertyValue(this.getSystemKey(), sProgramOrSection);
			System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0)+ ": Verwende als sSection '"+ sSection + "' für die Suche nach der Property '" + sProperty + "'");
			if(!StringZZZ.isEmpty(sSection)){
				boolean bSectionExists = objFileIniConfig.proofSectionExists(sSection);
				if(bSectionExists==true){
					sReturn = objFileIniConfig.getPropertyValue(sSection, sProperty);
					if(sReturn != null) break main;
				}
			}
			
			
			//+++ Einen ggfs. definierten Aliasnamen PLUS Systemnumber
			sSection = objFileIniConfig.getPropertyValue(this.getSystemKey(), sProgramOrSection);
			sSection = sSection + "!" + this.getSystemNumber();
			System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0)+ ": Verwende als sSection '"+ sSection + "' für die Suche nach der Property '" + sProperty + "'");
			if(!StringZZZ.isEmpty(sSection)){
				boolean bSectionExists = objFileIniConfig.proofSectionExists(sSection);
				if(bSectionExists==true){
					sReturn = objFileIniConfig.getPropertyValue(sSection, sProperty);
					if(sReturn != null) break main;
				}
			}
			
			
			//+++ Den Systemkey PLUS den ggfs. defnierten Aliasnamen
			sSection = objFileIniConfig.getPropertyValue(this.getSystemKey(), sProgramOrSection);
			sSection = this.getSystemKey() + "!" + sSection;
			System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0)+ ": Verwende als sSection '"+ sSection + "' für die Suche nach der Property '" + sProperty + "'");
			if(!StringZZZ.isEmpty(sSection)){
				boolean bSectionExists = objFileIniConfig.proofSectionExists(sSection);
				if(bSectionExists==true){
					sReturn = objFileIniConfig.getPropertyValue(sSection, sProperty);
					if(sReturn != null) break main;
				}
			}
			
			
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			sSection = objFileIniConfig.getPropertyValue(this.getSystemKey(), sModuleUsed);
			System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0)+ ": Verwende als sSection '"+ sSection + "' für die Suche nach der Property '" + sProperty + "'");
			if(!StringZZZ.isEmpty(sSection)){
				boolean bSectionExists = objFileIniConfig.proofSectionExists(sSection);
				if(bSectionExists==true){
					sReturn = objFileIniConfig.getPropertyValue(sSection, sProperty);
					if(sReturn != null) break main;
				}
			}
			
			sSection = objFileIniConfig.getPropertyValue(this.getApplicationKey(), sProgramOrSection);
			System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0)+ ": Verwende als sSection '"+ sSection + "' für die Suche nach der Property '" + sProperty + "'");
			if(!StringZZZ.isEmpty(sSection)){
				boolean bSectionExists = objFileIniConfig.proofSectionExists(sSection);
				if(bSectionExists==true){
					sReturn = objFileIniConfig.getPropertyValue(sSection, sProperty);
					if(sReturn != null) break main;
				}
			}
			
			sSection = objFileIniConfig.getPropertyValue(this.getApplicationKey(), sModuleUsed);
			System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0)+ ": Verwende als sSection '"+ sSection + "' für die Suche nach der Property '" + sProperty + "'");
			if(!StringZZZ.isEmpty(sSection)){
				boolean bSectionExists = objFileIniConfig.proofSectionExists(sSection);
				if(bSectionExists==true){
					sReturn = objFileIniConfig.getPropertyValue(sSection, sProperty);
					if(sReturn != null) break main;
				}
			}
			
			//+++ Einfach als SystemKey
			sSection = this.getSystemKey();
			System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0)+ ": Verwende als sSection '"+ sSection + "' für die Suche nach der Property '" + sProperty + "'");
			if(!StringZZZ.isEmpty(sSection)){
				boolean bSectionExists = objFileIniConfig.proofSectionExists(sSection);
				if(bSectionExists==true){
					sReturn = objFileIniConfig.getPropertyValue(sSection, sProperty);
					if(sReturn != null) break main;
				}
			}
									
			//+++ Einfach nur ApplicationKey
			sSection = this.getApplicationKey();
			System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0)+ ": Verwende als sSection '"+ sSection + "' für die Suche nach der Property '" + sProperty + "'");
			if(!StringZZZ.isEmpty(sSection)){				
				boolean bSectionExists = objFileIniConfig.proofSectionExists(sSection);
				if(bSectionExists==true){
					sReturn = objFileIniConfig.getPropertyValue(sSection, sProperty);
					if(sReturn != null) break main;
				}
			}

			//Falls der Parameter immer noch nicht gefunden wurde, hier eine Exception auswerfen.
	        //Ansonsten droht die Gefahr einer Endlosschleife.
				boolean bModuleConfig = this.proofModuleFileIsConfigured(sModuleUsed);
				if(bModuleConfig==false){
																		
					//A2. Versuch: Prüfen, ob es als Systemkey konfiguriert ist
					sModuleUsed = this.getSystemKey();
					bModuleConfig = this.proofModuleFileIsConfigured(sModuleUsed);
					if(bModuleConfig==false){
						
						//A3. Versuch: Prüfen, ob es als Applikationskey konfiguriert ist
						sModuleUsed = this.getApplicationKey();
						bModuleConfig = this.proofModuleFileIsConfigured(sModuleUsed);
						if(bModuleConfig==false){
							String stemp = "Wrong parameter: Module '" + sModuleUsed + "' is not configured.";
							System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": "+ stemp);
							ExceptionZZZ ez = new ExceptionZZZ(stemp,iERROR_PARAMETER_VALUE, this,  ReflectCodeZZZ.getMethodCurrentName());
							throw ez;
						} //end if Versuch 3
					} //end if Versuch 2
				}//end if Versuch 1
				
				//B1. Prüfen, ob das Modul existiert
				boolean bModuleExists = this.proofModuleFileExists(sModuleUsed);
				if(bModuleExists==false){
					String stemp = "Wrong parameter: Module '" + sModuleUsed + "' does not exist.";
					System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": "+ stemp);
					ExceptionZZZ ez = new ExceptionZZZ(stemp,iERROR_PARAMETER_VALUE, this,  ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}

				//Abbruch der Parametersuche. Ohne diesen else-Zweig, gibt es ggfs. eine Endlosschleife.
				String stemp = "Parameter nicht in der ini-Datei definiert (Modul/Program or Section) '(" + sModule + "/" + sProgramOrSection + ") " + sProperty + "'";
				System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": "+ stemp);
				ExceptionZZZ ez = new ExceptionZZZ(stemp, iERROR_CONFIGURATION_VALUE, this,  ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
				}//END main:

				return sReturn;
	}
	
	private boolean KernelSetParameterByProgramAlias_(FileIniZZZ objFileIniConfigIn, String sModule, String sProgramOrSection, String sProperty, String sValue, boolean bFlagSaveImmidiate) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{		
		
		//A1. Prüfen, ob das Modul überhaupt konfiguriert ist
		String sModuleUsed;
		if(StringZZZ.isEmpty(sModule)){
			sModuleUsed = this.getApplicationKey();
		}else{
			sModuleUsed = sModule;
		}
		
		//3. Konfigurationsfile des Moduls holen
		FileIniZZZ objFileIniConfig = null;
	    if(objFileIniConfigIn==null){
	    	objFileIniConfig = this.getFileConfigIniByAlias(sModuleUsed);			
	    }else{
	    	objFileIniConfig = objFileIniConfigIn;
	    }
	    
	    //als Programm...
	    if(objFileIniConfig==null){
	    	objFileIniConfig = this.getFileConfigIniByAlias(sProgramOrSection);
	    }
	    
	    
	    if(objFileIniConfig==null){
			String stemp = "FileIniZZZ fuer Modul '" + sModuleUsed + "' ist NULL.";
			System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": "+ stemp);
			ExceptionZZZ ez = new ExceptionZZZ(stemp, iERROR_PARAMETER_VALUE, this,  ReflectCodeZZZ.getMethodCurrentName());
			throw ez;
		}
	    
	    //+++ Ggfs. als Program deklarierte Section
	    String sSection = this.getSystemKey() + "!" + sModuleUsed;
		System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0)+ ": Verwende als sSection '"+ sSection + "' für die Suche nach der Property '" + sProperty + "'");
		if(!StringZZZ.isEmpty(sSection)){
		    boolean bSectionExists = objFileIniConfig.proofSectionExists(sSection);
			if(bSectionExists==true){
				bReturn = objFileIniConfig.setPropertyValue(sSection, sProperty, sValue, bFlagSaveImmidiate);
				if (bReturn) break main;
			}
		}
		
		sSection = sModuleUsed;
		System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0)+ ": Verwende als sSection '"+ sSection + "' für die Suche nach der Property '" + sProperty + "'");
		if(!StringZZZ.isEmpty(sSection)){
		boolean bSectionExists = objFileIniConfig.proofSectionExists(sSection);
		if(bSectionExists==true){
			bReturn = objFileIniConfig.setPropertyValue(sSection, sProperty, sValue, bFlagSaveImmidiate);
			if(bReturn) break main;
		}
		}
		
		//+++ First Try: Use SystemKey ! Parameter as section
		sSection  = this.getSystemKey() + "!" + sProgramOrSection;
		System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0)+ ": Verwende als sSection '"+ sSection + "' für die Suche nach der Property '" + sProperty + "'");
		if(!StringZZZ.isEmpty(sSection)){
			boolean bSectionExists = objFileIniConfig.proofSectionExists(sSection);
			if(bSectionExists==true){				
				bReturn = objFileIniConfig.setPropertyValue(sSection, sProperty, sValue, bFlagSaveImmidiate);
				if(bReturn) break main;
			}		
		}
		
		//+++ Second Try
		sSection =  sProgramOrSection;
		System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0)+ ": Verwende als sSection '"+ sSection + "' für die Suche nach der Property '" + sProperty + "'");
		if(!StringZZZ.isEmpty(sSection)){
		    boolean bSectionExists = objFileIniConfig.proofSectionExists(sSection);
			if(bSectionExists==true){
				bReturn = objFileIniConfig.setPropertyValue(sSection, sProperty, sValue, bFlagSaveImmidiate);
				if(bReturn) break main;
			}
		}
				
		//+++++++++++++++++++
			boolean bModuleConfig = this.proofModuleFileIsConfigured(sModuleUsed);
			if(bModuleConfig==false){
																	
				//A2. Versuch: Prüfen, ob es als Systemkey konfiguriert ist
				sModuleUsed = this.getSystemKey();
				bModuleConfig = this.proofModuleFileIsConfigured(sModuleUsed);
				if(bModuleConfig==false){
					
					//A3. Versuch: Prüfen, ob es als Applikationskey konfiguriert ist
					sModuleUsed = this.getApplicationKey();
					bModuleConfig = this.proofModuleFileIsConfigured(sModuleUsed);
					if(bModuleConfig==false){
						String stemp = "Wrong parameter: Module '" + sModuleUsed + "' is not configured.";
						System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": "+ stemp);
						ExceptionZZZ ez = new ExceptionZZZ(stemp,iERROR_PARAMETER_VALUE, this,  ReflectCodeZZZ.getMethodCurrentName());
						throw ez;
					} //end if Versuch 3
				} //end if Versuch 2
			}//end if Versuch 1
			
			//B1. Prüfen, ob das Modul existiert
			boolean bModuleExists = this.proofModuleFileExists(sModuleUsed);
			if(bModuleExists==false){
				String stemp = "Wrong parameter: Module '" + sModule + "' does not exist.";
				System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": "+ stemp);
				ExceptionZZZ ez = new ExceptionZZZ(stemp,iERROR_PARAMETER_VALUE, this,  ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			

			//Setzen des Wertes
			boolean bFlagDelete = false;
			if(sValue==null){
				bFlagDelete=true;
			}else{
				sValue=sValue;
			}
			
			if(bFlagDelete==false){
				bReturn = objFileIniConfig.setPropertyValue(sSection, sProperty, sValue, bFlagSaveImmidiate);				
			}else{
				bReturn =  objFileIniConfig.deleteProperty(sSection, sProperty, bFlagSaveImmidiate);
			}
						
			}//END main:
		return bReturn;
	}
	
	/**Merke: Hier wird davon ausgegangen, das Modul - und Programmname identisch sind !!!
	 * FGL 20180909: Müsste nicht eigentlich der Kernel-ApplicationKey als ModulAlias verwendet werden??? Statt Gleichheit???
	* @param sModuleAndProgramAndSection
	* @param sProperty
	* @return
	* @throws ExceptionZZZ
	* 
	* lindhaueradmin; 12.01.2007 09:10:41
	 */
	public String getParameterByProgramAlias(String sModuleAndProgramAndSection, String sProperty) throws ExceptionZZZ{
		String sReturn = null;
		main:{
			check:{
				if(StringZZZ.isEmpty(sModuleAndProgramAndSection)){
					ExceptionZZZ ez = new ExceptionZZZ("Missing 'String Module/Program/Section'",iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}			
			}//END check:
		
			//FGL TODO GOON 20180909: Müsste nicht eigentlich der ApplicationKey als ModulAlias verwendet werden?		
			//sReturn = this.getParameterByProgramAlias(sModuleAndProgramAndSection, sModuleAndProgramAndSection, sProperty); 
		sReturn = this.getParameterByProgramAlias(this.getApplicationKey(), sModuleAndProgramAndSection, sProperty);
		}//END main:
		return sReturn;
	}
	
	
	/** set the parameter of a program to the Module - Configuration - File
	 * Remark: The file will be immediately saved !!! 
	 * @param objFileConfig,       the Module-Configuration-File
	 * @param sProgramAlias,     the Alias-Name of the Program, e.g. this is the Line ProgIP=FGL#01_IP; where ProgIP is the AliasName which leads to the technical section FGL#101_IP
	 * @param sParameter,         the ParameterName in the technical section
	 * @param sValue,                 the Value to be set, if the parameter does not exist, the entry will be created.	if the value is 'null', the property will be deleted from the file.
	 * @throws ExceptionZZZ
	 */
	public synchronized void setParameterByProgramAlias(File objFileConfig, String sProgramOrSection, String sProperty, String sValue, boolean bFlagSaveImmidiate) throws ExceptionZZZ{
		main:{
			check:{
							if(objFileConfig == null){
								ExceptionZZZ ez = new ExceptionZZZ("Missing parameter: 'Configuration file-object'",iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
								throw ez;
							}else if(objFileConfig.exists()==false){
								ExceptionZZZ ez = new ExceptionZZZ("Wrong parameter: 'Configuration file-object' does not exist.",iERROR_PARAMETER_VALUE, this,  ReflectCodeZZZ.getMethodCurrentName());
								throw ez;
							}else if(objFileConfig.isDirectory()==true){
								ExceptionZZZ ez = new ExceptionZZZ("Wrong parameter: 'Configuration file-object' is as directory.", iERROR_PARAMETER_VALUE, this,  ReflectCodeZZZ.getMethodCurrentName());
								throw ez;
							}
											
						}//end check:
	
						//1. Erstellen des FileIni-Objects
						HashMap<String, Boolean> hmFlag = new HashMap<String, Boolean>();					
						hmFlag.put(FileIniZZZ.FLAGZ.USEFORMULA.name(), true);
					
						FileIniZZZ objFileIni = new FileIniZZZ(this, objFileConfig,hmFlag);
						this.KernelSetParameterByProgramAlias_(objFileIni, null, sProgramOrSection, sProperty, sValue, bFlagSaveImmidiate);

		}//end main:
	}//end function setParameterByProgramAlias
		
	/**
	 * @param objFileIniConfig, 			FileIniZZZ, Module-Konfiguration-File
	 * @param sProgramAlias
	 * @param sParameter
	 * @param sValueIn
	 * @param bFlagSaveImmediate,   true if the change should be saved immediately, otherwise the change will be kept im memory and stored in objFileConfig
	 * @throws ExceptionZZZ
	 */
	public synchronized void setParameterByProgramAlias(FileIniZZZ objFileIniConfig, String sProgramOrSection, String sProperty, String sValue, boolean bFlagSaveImmidiate) throws ExceptionZZZ{
	main:{
		try{
		check:{
						if(objFileIniConfig == null){
							ExceptionZZZ ez = new ExceptionZZZ("'Configuration file-object'",iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
							throw ez;
						}
					
						if(StringZZZ.isEmpty(sProgramOrSection)){
							ExceptionZZZ ez = new ExceptionZZZ("SectionOrProgram",iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
							throw ez;
						}
					
						if(StringZZZ.isEmpty(sProperty)){
							ExceptionZZZ ez = new ExceptionZZZ("Property",iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
							throw ez;
						}												
					}//end check:
									
					this.KernelSetParameterByProgramAlias_(objFileIniConfig, null, sProgramOrSection, sProperty, sValue, bFlagSaveImmidiate);

		}catch(ExceptionZZZ ez){
			this.setExceptionObject(ez);
			throw ez;		
		}
									
	}//end main:
}//end function setParameterByProgramAlias	
	
	/** void, set a value to a property in a section. The section is either provided directly or will be the value of another property in the systemkey-section (this i call then a 'program'). 
	 * Remark: The change wil be saved immidiately, because you have no chance to get a handle on the FileIniZZZ-Object which is used internally.
	* Lindhauer; 20.05.2006 09:40:36
	 * @param sModule
	 * @param sSectionOrProgram
	 * @param sProperty
	 * @param sValue
	 * @throws ExceptionZZZ
	 */
	public synchronized void setParameterByProgramAlias(String sModule, String sSectionOrProgram, String sProperty, String sValue) throws ExceptionZZZ{			
			main:{
						check:{
							if(StringZZZ.isEmpty(sModule)){
								ExceptionZZZ ez = new ExceptionZZZ("Module",iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
								throw ez;
							}
																			
							//Merke: Die Werte werden nicht gecheckt. Ein NULL-Wert bedeutet entfernen der Property aus dem ini-File
						}//end check:
	
						if(sModule.equals(sSectionOrProgram)){
							this.setParameterByModuleAlias(sModule, sProperty, sValue, true); //Es muss sofort gespeichert werden, da man keine chance hat auf das noch nicht gespeicherte FiniIni-Objekt von aussen zuzugreifen und dieses anschliessend zu speichern.
						}else{					
							this.KernelSetParameterByProgramAlias_(null, sModule, sSectionOrProgram, sProperty, sValue, true);
						}
		}//end main:
	}
	
	/** Use this method only when Modulename, Sectionname and Programname are equal.
	 *   will call .setParameterByProgramAlias(sModuleAndSectionAndProgram, sModuleAndSectionAndProgram, sProperty, sValue);
	 *   
	 *   Remark: The File will be saved immediate.
	 *   
	* @param sModuleAndSectionAndProgram
	* @param sProperty
	* @param sValue
	* @throws ExceptionZZZ
	* 
	* lindhaueradmin; 16.01.2007 11:08:36
	 */
	public synchronized void setParameterByProgramAlias(String sModuleAndSectionAndProgram, String sProperty, String sValue) throws ExceptionZZZ{
		main:{
				check:{
					if(StringZZZ.isEmpty(sModuleAndSectionAndProgram)){
						ExceptionZZZ ez = new ExceptionZZZ("Module/Section/Program",iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
						throw ez;
					}
																	
					//Merke: Die Werte werden nicht gecheckt. Ein NULL-Wert bedeutet entfernen der Property aus dem ini-File
				}//end check:
	
	            this.KernelSetParameterByProgramAlias_(null, null, sModuleAndSectionAndProgram, sProperty, sValue, true);
		}
	}
	
	
	/** Return a file-object on a configured file name. 
	 * Because it is used very often, this comfortabel method is available.
	 * Remark: If the configured file name is not absolute, the path of the module-configuration-file will be used.
	 *  
	* @param sModule
	* @param sSectionOrProgram
	* @param sProperty
	* @param sValue
	* @return
	* @throws ExceptionZZZ
	* 
	* lindhaueradmin; 09.02.2007 10:39:55
	 */
	public File getParameterFileByProgramAlias(String sModule, String sSectionOrProgram, String sProperty) throws ExceptionZZZ{
		File fileReturn = null;
		main:{
			String sCatalogSerieTitleFileName = this.getParameterByProgramAlias(sModule, sSectionOrProgram, sProperty);
			if(StringZZZ.isEmpty(sCatalogSerieTitleFileName)==false){
				File filetemp = new File(sCatalogSerieTitleFileName);				
				if( !filetemp.isAbsolute()){
					//Nun wird angenommen, dass die Datei im selben Verzeichnis wie die ModulKonfigurationsdatei liegt.
					File fileModuleConfig = this.getFileConfigByAlias(sModule);
					filetemp = fileModuleConfig.getParentFile(); //Das Verzeichnis
					String stemp = FileEasyZZZ.joinFilePathName(filetemp.getPath(), sCatalogSerieTitleFileName);
					fileReturn = new File(stemp);
				}else{
					fileReturn = filetemp;
				}				
			}else{
				ExceptionZZZ ez = new ExceptionZZZ("No parameter configured '" + sProperty + "'", iERROR_CONFIGURATION_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());				
				throw ez;
			}
		}
		return fileReturn;
	}
	
	/** String; Ausgehend vom Dateinamen werden die "�berfl�ssigen" Namensteile abgeschnitten und der Modulalias bleibt bestehen 
	* Lindhauer; 12.05.2006 09:33:01
	 * @param sIn
	 * @return
	 */
	public static String computeModuleAliasBy(File objFile){
		String sReturn =new String("");
		main:{
			 String sIn;
			check:{
				if(objFile==null) break main;
			   if(objFile.isDirectory())break main;
			}
			sIn = objFile.getName();
			sReturn = computeModuleAliasByFilename(sIn);	
		}		
		return sReturn;
	}
	
	/** String, computes the module alias from a filename, provided as as string
	* Lindhauer; 15.05.2006 08:35:30
	 * @param sIn
	 * @return
	 */
	public static String computeModuleAliasByFilename(String sIn){
		String sReturn =new String("");
		main:{
			check:{
				if(StringZZZ.isEmpty(sIn)) break main;
				if(!sIn.toLowerCase().startsWith("zkernelconfig")) break main;	
			}
	
		//in der lowercase-variante die "Ausschneid-Indices ermitteln
		int iLen = new String("zkernelconfig").length();
		String stemp = sIn.substring(iLen);
			
		String[] sa2Find = {"_", "."};
		int iEnd = StringZZZ.firstIndexOf(stemp,sa2Find);
		if(iEnd<=-1) break main;
		
		sReturn = stemp.substring(0, iEnd);
		}
		return sReturn;
	}
	
	/**Bereche den ModulAlias, ausgehend von einem Subject (d.h. normalerweise einer Property der Konfigurationsdatei in der der Pfad zur Moduldatei hinterlegt ist.)
	 * z.B. der String KernelConfigFiletest.zKernelUI.KernelUIZZZTest  ==> ergibt als ModuleAlias: test.zKernelUI.KernelUIZZZTest 
	* @param sSubjectIn
	* @return
	* 
	* lindhaueradmin; 04.03.2007 10:15:54
	 */
	public static String computeModuleAliasBySubject(String sSubjectIn) throws ExceptionZZZ{
		String sReturn = new String("");
		main:{
			if(StringZZZ.isEmpty(sSubjectIn)){
				ExceptionZZZ ez = new ExceptionZZZ("SubjectIn", iERROR_PARAMETER_MISSING, KernelZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			//a) kernelconfigfile
			String sSubject = sSubjectIn.toLowerCase();
			String stemp = StringZZZ.left(sSubject, "kernelconfigfile");
			
			//Falls es links daneben etwas gibt: Kein Fehler, aber nix zur�ckgeben.
			if(!StringZZZ.isEmpty(stemp)) break main;
			
			sReturn = StringZZZ.right(sSubject, "kernelconfigfile");
			if(!StringZZZ.isEmpty(sReturn)){
				//Da der Returnwert nun allerdings nur kleingeschrieben w�re vom Original ausgehen
				sReturn = sSubjectIn.substring(16);  //16 ist die L�nge von 'kernelConfigfile'
				break main;
			}
			
			
			//b) kernelconfigpath
			stemp = StringZZZ.left(sSubject, "kernelconfigpath");
			//Falls es links daneben etwas gibt: Kein Fehler, aber nix zur�ckgeben.
			if(!StringZZZ.isEmpty(stemp)) break main;
			
			sReturn = StringZZZ.right(sSubject, "kernelconfigpath");
			if(!StringZZZ.isEmpty(sReturn)) {
//				Da der Returnwert nun allerdings nur kleingeschrieben w�re vom Original ausgehen
				sReturn = sSubjectIn.substring(16);  //16 ist die L�nge von 'kernelConfigpath'
				break main;
			}
		}
		return sReturn;
	}
	
	/** String, computes the KernelConfigFile or KernelConfigPath - Property for a module in the ini-File 
	* Lindhauer; 15.05.2006 08:51:41
	 * @param sModuleAlias
	 * @param sPropertyIn
	 * @return
	 * @throws ExceptionZZZ 
	 */
	public static String computePropertyForModuleAlias(String sPropertyIn, String sModuleAlias) throws ExceptionZZZ{
		String sReturn = new String("");
		main:{
			String sProperty=null;
			check:{
				if(sModuleAlias==null)break main;
				if(sModuleAlias.equals("")) break main;
				if(sPropertyIn==null){
					sProperty = new String("File");
				}else if(sPropertyIn.equals("")){
					sProperty = new String("File");
				}else if(sPropertyIn.toLowerCase()!="file" && sPropertyIn.toLowerCase()!="path"){
					ExceptionZZZ ez = new ExceptionZZZ("Wrong parameter for sProperty='" + sPropertyIn + "', but expected 'File' or 'Path'.'", iERROR_PROPERTY_VALUE, KernelZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());				
					throw ez;
				}
			}
		sReturn= new String("KernelConfig"+sProperty+sModuleAlias);
		}
		return sReturn;
	}
	
			/** ArrayList
			 * -- true, true => Von den konfigurierten Modulen nur diejenige, die auch existieren.
			 * -- false, true => Von den konfigurierten Modulen diejenigen, deren Konfigurationsdatei fehlt
			 * -- true, false => Von allen Moduldateien ausgehend diejenigen, deren Konfiguration fehlt (!!! hierbei wird das Kernel-Konfigurations-Verzeichnis nach Moduldateien durchsucht !!!)
			* Lindhauer; 30.04.2006 09:41:15
			 * @param bOnlyConfigured
			 * @param bOnlyExisting
			 * @return
			 * @throws ExceptionZZZ 
			 */
	public ArrayList getModuleFileAliasAll(boolean bOnlyConfigured, boolean bOnlyExisting) throws ExceptionZZZ{
				ArrayList listaReturn = new ArrayList();
				main:{
					int iCase=0;
					check:{
						if(bOnlyConfigured==true && bOnlyExisting==true){
							iCase = 1;
						}else if(bOnlyConfigured == true && bOnlyExisting == false){
							iCase = 2;							
						}else if(bOnlyConfigured == false && bOnlyExisting == true){
							iCase = 3;
						}else if(bOnlyConfigured == false && bOnlyExisting == false){
							iCase = 4;
						}
					}//END Check:
					 
					
					//+++ Die KernelKonfigurations-Datei
				File objFile = this.getFileConfigKernel();
				
				HashMap<String, Boolean> hmFlag = new HashMap<String, Boolean>();					
				hmFlag.put(FileIniZZZ.FLAGZ.USEFORMULA.name(), true);
				
				FileIniZZZ objFileIni = new FileIniZZZ( this, objFile, hmFlag);
					
				String[] saProperty = null; String sSearch = null; int iIndex;
				File objFileTemp=null; File[] objaFileTemp=null; String sDir=null; ArrayList listaConfigured=null; ArrayList listaFile = null;
					switch(iCase){
					case 1:
						//+++ Von den konfigurierten Modulen nur diejenige, die auch existieren.
						//Aus der KernelKonfigurationsdatei alle Werte des aktuellen Systems holen. 
						saProperty = objFileIni.getPropertyAll(this.getSystemKey());
						if (saProperty==null) break main;
						
						sSearch = new String("kernelconfigfile");
						iIndex = sSearch.length();	
						
						//Aus dem Array alle ausfiltern, die mit "KernelConfigFile..." anfangen. Der Modulname steht unmittelbar dahinter.
						listaConfigured = new ArrayList();
						for(int icount=0; icount < saProperty.length; icount++){
							String stemp = saProperty[icount].toLowerCase();
							if(stemp.startsWith(sSearch)){						
								listaConfigured.add(saProperty[icount].substring(iIndex));
							}
						}
						
						//Aus der liste nun diejenigen wieder entfernen, die nicht existieren
						//!!! Von hinten nach vorne die liste durchsehen, ansonsten werden die Indizes durcheinandergeworfen: for(int icount=0; icount < listaConfigured.size(); icount++){
						for(int icount=listaConfigured.size()-1; icount >= 0 ; icount--){		
							boolean btemp = this.proofModuleFileExists((String) listaConfigured.get(icount));
							if(btemp == false){
								listaConfigured.remove(icount);
							}
						}
						
						//Alle ggf. doppelt vorkommenden Eintr�ge entfernen
						listaReturn = ArrayListZZZ.unique(listaConfigured);
						break;
					case 2:
						//+++ Von allen Moduldateien ausgehend diejenigen, deren Konfiguration fehlt
						//(!!! hierbei wird das Kernel-Konfigurations-Verzeichnis nach Moduldateien durchsucht !!!)
						objFileTemp = this.getFileConfigKernel();
						sDir = objFileTemp.getParent();
						if(sDir==null) sDir = "";     //Das ist nun m�glich     if(sDir.equals("")) break main;
						
						//Alle Moduldateien dieses Verzeichnisses holen
						objaFileTemp = this.getFileConfigAllByDir(sDir);
						if(objaFileTemp==null) break main;
						if(objaFileTemp.length==0)break main;
						
						// Aus der KernelKonfigurationsdatei alle Werte des aktuellen Systems holen. 
						saProperty = objFileIni.getPropertyValueAll(this.getSystemKey());
						
						
						listaConfigured = new ArrayList();
						if(saProperty!=null){
							//Von den Konfigurierten Dateien das Modul ermitteln
							for(int icount = 0; icount < saProperty.length; icount ++){
								String stemp = computeModuleAliasByFilename(saProperty[icount]);
								if(!stemp.equals("")) listaConfigured.add(stemp);	
							}							
						}
						
						//Die Module aus allen Dateinamen herausfiltern. D.h. ggf. gibt es ja mehrere Dateien zu einem Modul.
						listaFile = new ArrayList();
						for(int icount=0; icount < objaFileTemp.length; icount ++){
							String stemp = computeModuleAliasBy(objaFileTemp[icount]);
							if(!stemp.equals("")) listaFile.add(stemp);	
						}
						
						//Von der gesamtliste aller module nun diejenigen abziehen, die konfiguriert sind
//						!!! Von hinten nach vorne die liste durchsehen, ansonsten werden die Indizes durcheinandergeworfen:	for(int icount = 0; icount < listaConfigured.size(); icount++){							
						for(int icount=listaConfigured.size()-1; icount >= 0 ; icount--){		
							listaFile.remove(listaConfigured.get(icount));  //Falls es in der Liste vorhanden ist, wird es entfernt												
						}
						
						
						//Nun doppelte Modulnamen entfernen
						listaReturn = ArrayListZZZ.unique(listaFile);
						break;
					case 3:
						//+++ Von den konfigurierten Modulen diejenigen, deren Konfigurationsdatei fehlt
//						Aus der KernelKonfigurationsdatei alle Werte des aktuellen Systems holen. 
						saProperty = objFileIni.getPropertyAll(this.getSystemKey());
						if (saProperty==null) break main;
						
						sSearch = new String("kernelconfigfile");
						 iIndex = sSearch.length();	
						
						//Aus dem Array alle ausfiltern, die mit "KernelConfigFile..." anfangen. Der Modulname steht unmittelbar dahinter.
						 listaConfigured = new ArrayList();
						for(int icount=0; icount < saProperty.length; icount++){
							String stemp = saProperty[icount].toLowerCase();
							if(stemp.startsWith(sSearch)){						
								listaConfigured.add(saProperty[icount].substring(iIndex));
							}
						}
						
						//Nun die Module trimmen
						listaFile = ArrayListZZZ.unique(listaConfigured);
						
						//Aus der liste nun diejenigen wieder entfernen, die  existieren, bzw: Diejenigen, die FEHLEN HINZUF�GEN
						listaReturn = new ArrayList();
						for(int icount=0; icount < listaFile.size(); icount++){
							boolean btemp = this.proofModuleFileExists((String) listaFile.get(icount));
							if(btemp == false){
								listaReturn.add(listaFile.get(icount));  //Ein einfaches Entfernen an dieser Stelle �ndert die Size der Liste und die Indexpositionen
							}
						}
												
						break;						
					case 4:
						//+++ Fall: Alle Module, sowohl die Konfigurierten als auch die als Datei vorhandenen
//						(!!! hierbei wird das Kernel-Konfigurations-Verzeichnis nach Moduldateien durchsucht !!!)
						objFileTemp = this.getFileConfigKernel();
						sDir = objFileTemp.getParent();
						if(sDir==null) sDir = "";     //Das ist nun m�glich     if(sDir.equals("")) break main;
						
						
						//Alle Moduldateien dieses Verzeichnisses holen
						objaFileTemp = this.getFileConfigAllByDir(sDir);
						if(objaFileTemp==null) break main;
						if(objaFileTemp.length==0)break main;
						
						// Aus der KernelKonfigurationsdatei alle Werte des aktuellen Systems holen. ! DIE PROPERTIES, nicht die Values !!! 
						saProperty = objFileIni.getPropertyAll(this.getSystemKey());
							
						/* falsch, das funktioniert nur mit den Values
						listaConfigured = new ArrayList();
						if(saProperty!=null){
							//Von den Konfigurierten Dateien das Modul ermitteln
							for(int icount = 0; icount < saProperty.length; icount ++){
								
								//Nun den Aliasnamen aus der Property auslesen
								String stemp = getModuleAliasFrom(saProperty[icount]);
								if(!stemp.equals("")) listaConfigured.add(stemp);	
							}							
						}
						*/
																	
						sSearch = new String("kernelconfigfile");
						iIndex = sSearch.length();	
						
						//Aus dem Array alle ausfiltern, die mit "KernelConfigFile..." anfangen. Der Modulname steht unmittelbar dahinter.
						listaConfigured = new ArrayList();
						for(int icount=0; icount < saProperty.length; icount++){
							String stemp = saProperty[icount].toLowerCase();
							if(stemp.startsWith(sSearch)){						
								listaConfigured.add(saProperty[icount].substring(iIndex));
							}
						}
						
						
						
						//###########
						//Die Module aus allen Dateinamen herausfiltern. D.h. ggf. gibt es ja mehrere Dateien zu einem Modul.
						listaFile = new ArrayList();
						for(int icount=0; icount < objaFileTemp.length; icount ++){
							String stemp = computeModuleAliasBy(objaFileTemp[icount]);
							if(!stemp.equals("")) listaFile.add(stemp);	
						}
						
						listaReturn = ArrayListZZZ.join(listaConfigured, listaFile, true);
															
						break;						
					default:
					ExceptionZZZ ez = new ExceptionZZZ("Unexpected case'",iERROR_RUNTIME, this,  ReflectCodeZZZ.getMethodCurrentName());
					throw ez;	
					}
										
				}//END main:
				return listaReturn;
			}
	
	
	
	/**List aus der KernelKonfiguration alle Konfigurierten Modulenamen aus. Irgenwelche File - Eigenschaften (z.B. die Existenz) werden hier nicht ber�cksichtig
	* @return
	* 
	* lindhaueradmin; 04.03.2007 10:00:37
	 * @throws ExceptionZZZ 
	 */
	public ArrayList getModuleAll() throws ExceptionZZZ{
		ArrayList listaModuleString = new ArrayList();
		main:{
			IniFile objFileIni = this.getFileConfigKernelAsIni();
			if(objFileIni==null){
				ExceptionZZZ ez = new ExceptionZZZ("KernelConfigurationIni-File", iERROR_PROPERTY_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;				
			}
			
			String sSystemKey = this.getSystemKey(); //Also z.b. "MyApplication#01"
			String[] saVar = objFileIni.getVariables(sSystemKey);
			for(int icount = 0; icount <= saVar.length-1; icount ++){
				String sModule = KernelZZZ.computeModuleAliasBySubject(saVar[icount]);
				if(!StringZZZ.isEmpty(sModule)){
					if(!listaModuleString.contains(sModule)){
						listaModuleString.add(sModule);
					}
				}
			}
		}
		return listaModuleString;
	}
	
	
	
	/** boolean, proofs the existance of the module configuration lines in the kernel configuration .ini-file.
	 *   A  line has to look like: 	KernelConfigPath + sAlias = c:\.......
	 *   Another line:                 KernelConfigFile + sAlias = .... .ini
	 *   
	 *   This method does not check if a file really exists there.
	 *   
	 * @param sAlias
	 * @return 
	 * @throws ExceptionZZZ 
	 */
	public boolean proofModuleFileIsConfigured(String sAlias) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			check:{
				if(StringZZZ.isEmpty(sAlias)){
					String stemp = "Missing parameter: 'Alias'";
					System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": "+ stemp);
					ExceptionZZZ ez = new ExceptionZZZ(stemp,iERROR_PARAMETER_MISSING,  this,  ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}							
			}//end check:
			
			//first, get the Kernel-Configuration-INI-File
			//TODO write ini-file-class for zzz-kernel
			IniFile objIni = this.getFileConfigKernelAsIni();
			
			//PRÜFUNG: LIES EINEN .ini - DATEINAMEN AUS.
			//Merke: Der Pfad darf leer sein. Dann wird "." als aktuelles Verzeichnis angenommen    String sFilePath = objIni.getValue(stemp,"KernelConfigPath" +sAlias );
			
			//1. Versuch: Systemebene
			String sKeyUsed = this.getSystemKey();			
			String sFileName =objIni.getValue(sKeyUsed, "KernelConfigFile" +sAlias );
 			
			//2. Versuch: Applilkationsebene
			if(StringZZZ.isEmptyNull(sFileName)){
				sFileName =objIni.getValue(sAlias, "KernelConfigFile"+sAlias );
			}

			if(StringZZZ.isEmpty(sFileName)) break main;
			bReturn = true;			
		}//end main:
		return bReturn;
	}
		
	/** boolean, proofs the existance of the module configuration lines in the Kernel configuration .ini-file
	 *  AND checks if the configured file really exists.
	 * 
	* Lindhauer; 21.04.2006 09:39:09
	 * @param sAlias
	 * @return
	 * @throws ExceptionZZZ
	 */
	public boolean proofModuleFileExists(String sAlias) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			check:{
				if(StringZZZ.isEmpty(sAlias)){
					ExceptionZZZ ez = new ExceptionZZZ("Missing parameter: 'Alias'",iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}								
			}//end check:
			
			//first, get the Kernel-Configuration-INI-File
			//TODO write ini-file-class for zzz-kernel
			IniFile objIni = this.getFileConfigKernelAsIni();
			
			
			//1. Versuch: Auf Systemebene
			String sKeyUsed = this.getSystemKey();
			String sFileName =objIni.getValue(sKeyUsed,"KernelConfigFile" +sAlias ); 			
			if(StringZZZ.isEmpty(sFileName)){
				
				//2. Versuch auf Applikationsebene
				sKeyUsed = this.getApplicationKey();
				sFileName =objIni.getValue(sKeyUsed,"KernelConfigFile" +sAlias ); 
				
				if(sFileName.equals(""))break main;
								
			}
			
			String sFilePath = objIni.getValue(sKeyUsed,"KernelConfigPath" +sAlias );
			if(StringZZZ.isEmpty(sFilePath)) sFilePath = "."; //Nun kann die Datei auch im gleichen Verzeichnis liegen
			
			//Proof the existance of the file
			String sFileTotal = FileEasyZZZ.joinFilePathName(sFilePath, sFileName);
			if(this.getLogObject()!=null) this.getLogObject().WriteLineDate(ReflectCodeZZZ.getMethodCurrentName() + "#sFileTotal = " +  sFileTotal);
			File objFile = new File(sFileTotal);
			bReturn = objFile.exists();
			if(this.getLogObject()!=null) this.getLogObject().WriteLineDate(ReflectCodeZZZ.getMethodCurrentName() + "#FileExists = " + bReturn);
		}//end main:
		return bReturn;
	}
	

	public void setLogObject(LogZZZ objLog){
		this.objLog = objLog;
	}
	
	private boolean KernelNew_(IKernelConfigZZZ objConfig, IKernelContextZZZ objContext, String sApplicationKeyIn,String sSystemNumberIn, String sDirectoryConfigIn, String sFileConfigIn, LogZZZ objLogIn, String[] saFlagControlIn) throws ExceptionZZZ{
		boolean bReturn = false;
		String stemp; boolean btemp;
		main:{
			try{
				System.out.println("Initializing KernelObject");
				
				String sDirectoryLog = null;
				String sFileLog = null;
				String sApplicationKey = null;
				String sSystemNumber = null;
				String sFileConfig = null;
				String sDirectoryConfig = null;
				String sLog = null;
				
				 //setzen der �bergebenen Flags	
				  if(saFlagControlIn != null){
					  for(int iCount = 0;iCount<=saFlagControlIn.length-1;iCount++){
						  stemp = saFlagControlIn[iCount];
						  if(!StringZZZ.isEmpty(stemp)){
							  btemp = setFlag(stemp, true);
							  if(btemp==false){
								  sLog = "the flag '" + stemp + "' is not available.";
								  System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": " + sLog);
								  ExceptionZZZ ez = new ExceptionZZZ(sLog, iERROR_PARAMETER_VALUE, this,  ReflectCodeZZZ.getMethodCurrentName()); 
								  throw ez;		 
							  }
						  }
					  }
						if(this.getFlag("INIT")==true){
							bReturn = true;
							break main; 
						}		
				  }
			
				//ggf. Config Object setzen
				this.objConfig = objConfig;
				this.objContext = objContext;
				  
				  
				 btemp = StringZZZ.isEmpty(sApplicationKeyIn);
				if(!btemp){
					sApplicationKey = sApplicationKeyIn;
				}else if(objConfig==null){
					sLog = "ApplicationKey not passed, Config-Object not available";
					System.out.println(sLog);	
					ExceptionZZZ ez = new ExceptionZZZ(sLog, iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}else if(objConfig.isOptionObjectLoaded()==false){
					//Fall: Das objConfig - Objekt existiert, aber es "lebt" von den dort vorhandenenen DEFAULT-Einträgen
					//      und nicht von irgendwelchen übergebenen Startparametern, sei es per Batch Kommandozeile oder per INI-Datei.
					sLog = "Config-Object not loaded, using DEFAULTS.";
					System.out.println(sLog);
					sApplicationKey = objConfig.getApplicationKeyDefault();
					if(StringZZZ.isEmpty(sApplicationKey)){
						sLog = "ApplicationKey DEFAULT not receivable from Config-Object, Config-Object  not loaded.";
						System.out.println(sLog);
						ExceptionZZZ ez = new ExceptionZZZ(sLog, iERROR_PROPERTY_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
						throw ez;
					}
				}else{
					sApplicationKey = objConfig.readApplicationKey();
					if(StringZZZ.isEmpty(sApplicationKey)){
						sLog = "ApplicationKey not receivable from Config-Object";
						System.out.println(sLog);
						ExceptionZZZ ez = new ExceptionZZZ(sLog, iERROR_PROPERTY_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
						throw ez;
					}
				}
				this.setApplicationKey(sApplicationKey);
				
				btemp = StringZZZ.isEmpty(sSystemNumberIn);
				if(!btemp){
					sSystemNumber = sSystemNumberIn;
				}else if(objConfig==null){
					sLog = "SystemNumber not passed, Config-Object not available";
					System.out.println(sLog);
					ExceptionZZZ ez = new ExceptionZZZ(sLog, iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}else if(objConfig.isOptionObjectLoaded()==false){
					sLog = "SystemNumber unavailable, Config-Object not loaded, USING DEFAULTS";
					System.out.println(sLog);
					
					//Fall: Das objConfig - Objekt existiert, aber es "lebt" von den dort vorhandenenen DEFAULT-Einträgen
					//      und nicht von irgendwelchen übergebenen Startparametern, sei es per Batch Kommandozeile oder per INI-Datei.
					sSystemNumber = objConfig.getSystemNumberDefault();
					if(StringZZZ.isEmpty(sApplicationKey)){
						sLog = "SystemNumber DEFAULT not receivable from Config-Object, Config-Object  not loaded.";
						System.out.println(sLog);
						ExceptionZZZ ez = new ExceptionZZZ(sLog, iERROR_PROPERTY_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
						throw ez;
					}
				}else{
					sSystemNumber = objConfig.readSystemNumber();
					if(StringZZZ.isEmpty(sSystemNumber)){
						sLog = "SystemNumber not receivable from Config-Object";
						System.out.println(sLog);
						ExceptionZZZ ez = new ExceptionZZZ(sLog, iERROR_PROPERTY_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
						throw ez;
					}
				}
				this.setSystemNumber(sSystemNumber);
				
				//get the Application-Configuration-File
				//A) Directory
				//     Hier kann auf das Config Objekt verzichtet werden. wenn nix gefunden wird, wird "." als aktuelles Verzeichnis genommen
				btemp = StringZZZ.isEmpty(sDirectoryConfigIn);
				if(!btemp){
					sDirectoryConfig = sDirectoryConfigIn;					
				}else if(objConfig==null){
					//Damit ist das Konfigurationsverzeichnis entsprechend dem aktuellen Verzeichnis
					
				}else if(objConfig.isOptionObjectLoaded()==false){
					sLog = "Directory for configuration unavailable, Config-Object not loaded, USING DEFAULTS";
					System.out.println(sLog);
					
					//Fall: Das objConfig - Objekt existiert, aber es "lebt" von den dort vorhandenenen DEFAULT-Einträgen
					//      und nicht von irgendwelchen übergebenen Startparametern, sei es per Batch Kommandozeile oder per INI-Datei.
					sDirectoryConfig = objConfig.getConfigDirectoryNameDefault();
					if(StringZZZ.isEmpty(sDirectoryConfig)){
						sLog = "Directory for configuration DEFAULT not receivable from Config-Object, Config-Object  not loaded.";
						System.out.println(sLog);
						ExceptionZZZ ez = new ExceptionZZZ(sLog, iERROR_PROPERTY_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
						throw ez;
					}
				}else{
					sDirectoryConfig = objConfig.readConfigDirectoryName();
				}
				if(StringZZZ.isEmpty(sDirectoryConfig)){
					this.setFileConfigKernelDirectory(".");//"c:\\fglkernel\\KernelConfig";				
				}else{
					this.setFileConfigKernelDirectory(sDirectoryConfig);
				}						
				
				//B) FileName
				btemp = StringZZZ.isEmpty(sFileConfigIn);
				if(!btemp){
					sFileConfig = sFileConfigIn;
				}else if(objConfig==null){
					sLog = "ConfigurationFilename not passed, Config-Object not available";
					System.out.println(sLog);
					ExceptionZZZ ez = new ExceptionZZZ(sLog , iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}else if(objConfig.isOptionObjectLoaded()==false){
					sLog = "ConfigurationFilename unavailable, Config-Object not loaded, USING DEFAULTS";
					System.out.println(sLog);
					
					//Fall: Das objConfig - Objekt existiert, aber es "lebt" von den dort vorhandenenen DEFAULT-Einträgen
					//      und nicht von irgendwelchen übergebenen Startparametern, sei es per Batch Kommandozeile oder per INI-Datei.
					sFileConfig = objConfig.getConfigFileNameDefault();
					if(StringZZZ.isEmpty(sFileConfig)){
						sLog = "Filename for configuration DEFAULT not receivable from Config-Object, Config-Object  not loaded.";
						System.out.println(sLog);
						ExceptionZZZ ez = new ExceptionZZZ(sLog, iERROR_PROPERTY_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
						throw ez;
					}										
				}else{
					sFileConfig = objConfig.readConfigFileName();
					if(StringZZZ.isEmpty(sFileConfig)){
						sLog = "ConfigurationFilename not receivable from Config-Object";
						System.out.println(sLog);
						ExceptionZZZ ez = new ExceptionZZZ(sLog, iERROR_PROPERTY_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
						throw ez;
					}
				}
				this.sFileConfig = sFileConfig;
				if(this.getFlag("DEBUG")){
					System.out.println("SystemNr: '" + sSystemNumber + "'");
					System.out.println("Configurationfile: '" + sFileConfig + "'");
					System.out.println("Configurationpath: '" + sDirectoryConfig + "'");
				}
				
				//read the ini-content: 
				//Merke: Falls die Datei nicht existiert, wird ein Fehler geworfen
				File objFile = this.getFileConfigKernel();
				IniFile objIni = new IniFile(objFile.getPath());
				if(this.getFlag ("DEBUG")) System.out.println("Konfigurationsdatei gefunden: '" + objFile.getPath() +"'");
				
				
//				TODO replace the direct use of the IniFile - Klass by a Z-Kernel-Class
				this.setFileIniConfigKernel(objIni);
				
				
				//Throw an Exception if the Application/SystemKey does not exist
				String[] saSection = objIni.getSubjects();
				StringArrayZZZ objA = new StringArrayZZZ(saSection);
				boolean bProof = objA.contains(this.getSystemKey());
				if(bProof==false){
					sLog = "In the configuration file '" + objFile.getPath() + "' does the the section for the SystemKey '" + this.getSystemKey() + "' not exist or the section is empty.";
					System.out.println(sLog);
					ExceptionZZZ ez = new ExceptionZZZ(sLog, iERROR_CONFIGURATION_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
				
								
				//create the log using the configured path/file
				LogZZZ objLog = null;
				if(objLogIn==null){
					System.out.println("Erstelle neues Log Object");
//					1. Versuch: �ber die Programm-Konfiguration. 
					//Merke: Dies geht nur, wenn ein Context-Objekt �bergeben worden ist. An dieser Stelle kommt man nicht anders an den Namen der Aufrufenden - Klasse (d.h. den Programnamen) dran.
					if(this.getContextUsed()!=null){
						
						//!!! Falls da nix konfiguriert ist, darf kein Fehler geworfen werden !!!
						try{
							sDirectoryLog = this.getParameterByProgramAlias(this.getContextUsed().getModuleName(), this.getContextUsed().getProgramName(), "KernelLogPath");
							sFileLog = this.getParameterByProgramAlias(this.getContextUsed().getModuleName(), this.getContextUsed().getProgramName(), "KernelLogFile");
						}catch (ExceptionZZZ ez){
							//nix tun, Ausgabe nur zum Test/Debug
							System.out.println(ez.getDetailAllLast());
						}
					}	
					
					//2. Versuch: �ber die Globale konfiguration der Applikation
					if(StringZZZ.isEmpty(sDirectoryLog) && StringZZZ.isEmpty(sFileLog)){
						sDirectoryLog = objIni.getValue(this.getSystemKey(), "KernelLogPath");
						sFileLog = objIni.getValue(this.getSystemKey(), "KernelLogFile");										
					}
					
					//3. Fehlermeldung, wenn kein Log definiert
					if(StringZZZ.isEmpty(sDirectoryLog) && StringZZZ.isEmpty(sFileLog)){
						sLog = "In the configuration file '" + objFile.getPath() + "' is no log configured (Properties 'KernelLogPath', 'KernelLogFile'   ";
						System.out.println(sLog);
						ExceptionZZZ ez = new ExceptionZZZ(sLog, iERROR_CONFIGURATION_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
						throw ez;
					}
					
					if(this.getFlag ("DEBUG")) System.out.println("Initialisiere KernelLog mit folgendem Pfad, Dateinamen: '" + sDirectoryLog +"', '" + sFileLog + "'");
					objLog = new LogZZZ(sDirectoryLog, sFileLog);
				}else{
					System.out.println("Verwende LogObject erneut");
					objLog = objLogIn;
				}
				this.setLogObject(objLog);
			}catch(IOException e){
				System.out.println(e.getMessage());
				bReturn = false;
				break main;
			}
		}//end main
		return bReturn;
	}

	public IKernelConfigZZZ  getConfigObject(){
		return this.objConfig;
	}
	
	//##### Interfaces
	public IKernelContextZZZ getContextUsed() {
		return this.objContext;
	}
	public void setContextUsed(IKernelContextZZZ objContext) {
		this.objContext = objContext;
	}
}//end class// end class
