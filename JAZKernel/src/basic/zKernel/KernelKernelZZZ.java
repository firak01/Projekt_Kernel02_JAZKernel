package basic.zKernel;

import java.awt.Dimension;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.swing.ImageIcon;

import org.apache.commons.io.FileUtils;

import basic.zBasic.IObjectZZZ;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IResourceHandlingObjectZZZ;
import basic.zBasic.ObjectZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractList.ArrayListExtendedZZZ;
import basic.zBasic.util.abstractList.ArrayListZZZ;
import basic.zBasic.util.abstractList.HashMapCaseInsensitiveZZZ;
import basic.zBasic.util.abstractList.HashMapExtendedZZZ;
import basic.zBasic.util.abstractList.HashMapMultiIndexedZZZ;
import basic.zBasic.util.counter.CounterByCharacterAsciiFactoryZZZ;
import basic.zBasic.util.counter.CounterByCharacterAscii_AlphabetZZZ;
import basic.zBasic.util.counter.CounterByCharacterAscii_AlphanumericZZZ;
import basic.zBasic.util.counter.CounterHandlerSingleton_AlphabetSignificantZZZ;
import basic.zBasic.util.counter.CounterHandlerSingleton_AlphabetZZZ;
import basic.zBasic.util.counter.CounterHandlerSingleton_AlphanumericSignificantZZZ;
import basic.zBasic.util.counter.CounterStrategyAlphabetSerialZZZ;
import basic.zBasic.util.counter.CounterStrategyAlphabetSignificantZZZ;
import basic.zBasic.util.counter.CounterStrategyAlphanumericSignificantZZZ;
import basic.zBasic.util.counter.ICounterAlphabetSignificantZZZ;
import basic.zBasic.util.counter.ICounterAlphabetZZZ;
import basic.zBasic.util.counter.ICounterAlphanumericSignificantZZZ;
import basic.zBasic.util.counter.ICounterStrategyAlphabetSignificantZZZ;
import basic.zBasic.util.counter.ICounterStrategyAlphabetZZZ;
import basic.zBasic.util.counter.ICounterStrategyAlphanumericSignificantZZZ;
import basic.zBasic.util.counter.ICounterStringZZZ;
import basic.zBasic.util.datatype.string.StringArrayZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.file.FileEasyZZZ;
import basic.zBasic.util.file.JarEasyUtilZZZ;
import basic.zBasic.util.file.ini.IniFile;
import basic.zKernel.cache.IKernelCacheZZZ;
import basic.zKernel.cache.ICachableObjectZZZ;
import basic.zKernel.cache.IKernelCacheUserZZZ;
import basic.zKernel.cache.KernelCacheZZZ;
import basic.zKernel.file.ini.KernelExpressionIniConverterZZZ;
import basic.zKernel.file.ini.KernelExpressionIniSolverZZZ;
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
public abstract class KernelKernelZZZ extends ObjectZZZ implements IKernelZZZ, IKernelConfigConstantZZZ, IKernelLogUserZZZ, IKernelContextUserZZZ, IKernelExpressionIniConverterUserZZZ, IKernelCacheUserZZZ, IResourceHandlingObjectZZZ {
	//FLAGZ, die dann zum "Rechnen in der Konfiguations Ini Datei" gesetzt sein müssen.
	public enum FLAGZ{
		USEFORMULA, USEFORMULA_MATH;
	}
	
	private FileFilterModuleZZZ objFileFilterModule=null;
	
	//Merke 20180721: Wichtig ist mir, dass die neue HashMap für Variablen NICHT im Kernel-Objekt gespeichert wird. 
	//                        Sie hängt aussschliesslich am Kernel...IniFileZZZ-Objekt. 
	//                        Eventuelle Variablen-"Zustände" werden dann beim Holen der Parameter als Argument übergeben, sind aber NICHT im Kernel-Objekt gespeichert.
			
	//20210110: Nur noch über FileIniZZZ - Objekt ereichbar...  private IniFile objIniConfig=null;
	//20210110: Nur noch über FileIniZZZ - Objekt ereichbar...  private File objFileKernelConfig=null;
	protected FileIniZZZ objFileIniKernelConfig = null; 
	
	private String sFileConfig="";
	private String sDirectoryConfig="";
	
	private String sSystemNumber="";	
	private String sApplicationKey="";

	private LogZZZ objLog = null;
	protected IKernelConfigZZZ objConfig = null;   //die Werte für den Applikationskey, Systemnummer, etc.
	private IKernelContextZZZ objContext = null;   //die Werte des aufrufenden Programms (bzw. sein Klassenname, etc.)
	
	private IKernelCacheZZZ objCache = null; //Ein Zwischenspeicher für die aus der Ini-Konfiguration gelesenen Werte.
	
/**  Verwende diesen Konstruktor, wenn die Defaultangaben für das Verzeichnis und für den ini-Dateinamen verwendet werden sollen:
	 * -Verzeichnis: c:\\fglKernel\\KernelConfig
	 * - Datei:		ZKernelConfigKernel_default.ini
	 * Diese Werte stammen aus ConfigZZZ im custom.zKernel - Verzeichnis.
* lindhauer; 14.08.2007 06:40:31
 * @throws ExceptionZZZ
 */
public KernelKernelZZZ() throws ExceptionZZZ{
	//20181005: Die Default - Konfiguration nun auch in den verschiedenen Projekten konfigurierbar machen.  ConfigZZZ objConfig = new ConfigZZZ();
	IKernelConfigZZZ objConfig = this.getConfigObject();
	
	String[] saFlagControl = new String[1];
	saFlagControl[0] = "init";
	KernelNew_(objConfig, null, null, null, null, null, null,saFlagControl);
}

public KernelKernelZZZ(String[] saFlagControl) throws ExceptionZZZ{
	//20181005: Die Default - Konfiguration nun auch in den verschiedenen Projekten konfigurierbar machen.   ConfigZZZ objConfig = new ConfigZZZ();
	IKernelConfigZZZ objConfig = this.getConfigObject();
	KernelNew_(objConfig, null, null, null, null, null, null,saFlagControl);
}
	/**Merke: Damit einzelne Projekte ihr eigenes ConfigZZZ - Objekt verwenden k�nnen, wird in diesem Konstruktor ein Interface eingebaut.
	* lindhauer; 14.08.2007 07:19:55
	 * @param objConfig
	 * @param sFlagControl
	 * @throws ExceptionZZZ
	 */
	public KernelKernelZZZ(IKernelConfigZZZ objConfig, String sFlagControl) throws ExceptionZZZ{
		String[] saFlagControl = new String[1];
		saFlagControl[0] = sFlagControl;
		KernelNew_(objConfig, null, null, null, null, null, null, saFlagControl);
	}
	public KernelKernelZZZ(IKernelConfigZZZ objConfig, String[] saFlagControl) throws ExceptionZZZ{
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
	public KernelKernelZZZ(String sApplicationKey, String sSystemNumber, String sFileConfigPath, String sFileConfigName, String[] saFlagControl ) throws ExceptionZZZ{
		KernelNew_(null, null, sApplicationKey, sSystemNumber, sFileConfigPath, sFileConfigName, null, saFlagControl);
	}
	
	public KernelKernelZZZ(String sApplicationKey, String sSystemNumber, String sFileConfigPath, String sFileConfigName, String sFlagControl) throws ExceptionZZZ{
		String[] saFlagControl = new String[1];
		saFlagControl[0] = sFlagControl;
		KernelNew_(null, null, sApplicationKey, sSystemNumber, sFileConfigPath, sFileConfigName, null, saFlagControl);
	}
	
	public KernelKernelZZZ(String sApplicationKey, String sSystemNumber, String sFileConfigPath, String sFileConfigName, IKernelContextZZZ objContext, String sFlagControl) throws ExceptionZZZ{
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
	public KernelKernelZZZ(String sApplicationKey, String sSystemNumber, String sFlagControl) throws ExceptionZZZ{
		//20181005: Die Default - Konfiguration nun auch in den verschiedenen Projekten konfigurierbar machen.  ConfigZZZ objConfig = new ConfigZZZ();
		IKernelConfigZZZ objConfig = this.getConfigObject();
		
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
	public KernelKernelZZZ(String sApplicationKey, String sSystemNumber, String[] saFlagControl) throws ExceptionZZZ{
		//20181005: Die Default - Konfiguration nun auch in den verschiedenen Projekten konfigurierbar machen.  ConfigZZZ objConfig = new ConfigZZZ();
		IKernelConfigZZZ objConfig = this.getConfigObject();
				
		KernelNew_(objConfig,null, sApplicationKey, sSystemNumber, null, null, null, saFlagControl);
	}
	
	
	
	/** Choose this construktor, if you want to create another Kernel-Object, using the same configuration and log-file, but another Application
	* Lindhauer; 08.05.2006 08:21:25
	 * @param sApplicationKey
	 * @param objKernelNew
	 * @param saFlagControl
	 * @throws ExceptionZZZ 
	 */
	public KernelKernelZZZ(String sApplicationKey, String sSystemNumber, IKernelZZZ objKernelOld, String[] saFlagControl) throws ExceptionZZZ{
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
	
	// DESTRUCTOR
	protected void finalize(){		
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
			String sFileConfig = null;
			check:{
				//Falls schon mal geholt, nicht neu holen
				if(this.objFileIniKernelConfig!=null){
					objReturn = this.objFileIniKernelConfig.getFileObject();
					break main;
				}
				
				sFileConfig = this.getFileConfigKernelName();
				if(StringZZZ.isEmpty(sFileConfig)){
					sLog = "Missing property: 'Configuration File-Name'";
					System.out.println(sLog);
					ExceptionZZZ ez = new ExceptionZZZ(sLog,iERROR_PROPERTY_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
			}//end check
		

			String sDirectoryConfig = this.getFileConfigKernelDirectory();	
			
			//Suche nach der Datei, ggfs. mit relativem Pfad unterhalb des Workspace oder sogar im Classpath (.war / .jar Datei, s. WebService)
			//MErke: Wenn diese Datei nicht existiert, wird im TEMP Verzeichnis eine temporäre Datei erstellt. z.B. C:\DOKUME~1\MYUSER~1\LOKALE~1\Temp\temp9018141784814640806ZZZ
			//       Diese existiert demnach immer...
			objReturn = FileEasyZZZ.searchFile(sDirectoryConfig, sFileConfig);
			if(objReturn==null){
				sLog = "'Configuration File' does not exist (null) in the current directory or in: " + sDirectoryConfig + this.sFileConfig + " or in the classpath.";
				System.out.println(sLog);				
				ExceptionZZZ ez = new ExceptionZZZ(sLog,iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName() );
				throw ez;
			}else if(objReturn.exists()==false){			
				sLog = "'Configuration File' does not exist in the current directory or in: " + sDirectoryConfig + this.sFileConfig + " or in the classpath.";
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
			
			
			//Merke: Das soll dann einen Fehler werfen, wenn es eine temporäre Datei ist
			//TODO GOON 20190128: Eine temporäre Datei mit den übergebenen Daten füllen, so dass Sie als Minimale Kernelkonfiguration durchgeht.
			if(objReturn.isFile()){
				
				//Merke: Die einzige Methode eine neue temporäre Datei zu erkennen ist, dass die Dateigröße 0 ist.
				if(objReturn.length()==0){				
					sLog = "The configuration file '" + objReturn.getPath() + "' is empty. Probabley a temporary file because the original file does not exist ('" + this.sDirectoryConfig + File.separator + this.sFileConfig+"').";
					System.out.println(sLog);
					ExceptionZZZ ez = new ExceptionZZZ(sLog, iERROR_CONFIGURATION_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
					throw ez;	
				}
			}

			
			
		}//end main:		
		return objReturn;
	}
	
	public String getFileConfigKernelName() throws ExceptionZZZ{		
		String sFileConfigKernelName = this.sFileConfig;
		if(StringZZZ.isEmpty(sFileConfigKernelName)){
			IKernelConfigZZZ objConfig = this.getConfigObject();
			sFileConfigKernelName=objConfig.getConfigFileNameDefault();
			this.sFileConfig = sFileConfigKernelName;
		}
		return sFileConfigKernelName;
	}
	protected void setFileConfigKernelName(String sFileConfig){
		this.sFileConfig = sFileConfig;
	}
	
	public String getFileConfigKernelDirectory() throws ExceptionZZZ{		
		String sDirectoryConfig = this.sDirectoryConfig;
		boolean bDirectoryFound = false;
		if(StringZZZ.isEmpty(sDirectoryConfig)){
			IKernelConfigZZZ objConfig = this.getConfigObject();
			sDirectoryConfig=objConfig.getConfigDirectoryNameDefault();
			if(sDirectoryConfig.equals("")){
				String sDirConfigTemp = KernelKernelZZZ.sDIRECTORY_CONFIG_DEFAULT; 
				File objDir = new File(sDirConfigTemp);
				if(objDir.exists()){
					sDirectoryConfig = sDirConfigTemp;
					bDirectoryFound = true;
				}else{
					//Pfad relativ zum Eclipse Workspace
					URL workspaceURL;
					try {
						workspaceURL = new File(sDirectoryConfig).toURI().toURL();
						String sWorkspaceURL = workspaceURL.getPath();					
						sWorkspaceURL = StringZZZ.stripFileSeparatorsRight(sWorkspaceURL);				
						objDir = FileEasyZZZ.searchDirectory(sWorkspaceURL);
						if(objDir.exists()){
							sDirectoryConfig = objDir.getAbsolutePath();
							bDirectoryFound = true;
						}else{
							//Pfad relativ zum src - Ordner
							sDirConfigTemp = FileEasyZZZ.getFileRootPath(); 		
							objDir = new File(sDirConfigTemp);
							if(objDir.exists()){
								sDirectoryConfig = sDirConfigTemp;
								bDirectoryFound = true;
							}
						}
					} catch (MalformedURLException e) {
						ExceptionZZZ ez  = new ExceptionZZZ("MalformedURLException: " + e.getMessage(), iERROR_PARAMETER_VALUE, FileEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
						throw ez;
					}			
				}				
			}			
		}else{
			/* Merke: Das sind die hierin verarbeiteten Suchpfade
			 * if(sDirectoryIn.equals(FileEasyZZZ.sDIRECTORY_CURRENT) | sDirectoryIn.equals(""))==> FileEasyZZZ.getFileRootPath();
		     * if(sDirectoryIn.equals(FileEasyZZZ.sDIRECTORY_CONFIG_SOURCEFOLDER))==> sDirectory = sDirectoryIn; 
		     * if(FileEasyZZZ.isPathRelative(sDirectoryIn))==> FileEasyZZZ.getFileRootPath() + File.separator + sDirectoryIn;
		     * und dann wird auf die Existenz des Verzeichnisses geprüft.
			 */
			File objDir = FileEasyZZZ.searchDirectory(sDirectoryConfig);
			if(objDir!=null){
				//sofort gefunden... dann nimm es.
				sDirectoryConfig = objDir.getAbsolutePath();
				bDirectoryFound = true;
			}else{
				//Jetzt geht die Sucherei los
				String sDirConfigTemp = KernelKernelZZZ.sDIRECTORY_CONFIG_DEFAULT+File.separator+sDirectoryConfig;			
				objDir = new File(sDirConfigTemp);
				if(objDir.exists()){
					sDirectoryConfig = sDirConfigTemp;
					bDirectoryFound = true;
				}else{
					//Merke 20190129: Hier wird keine Fehlermeldung geworfen. Jetzt ist die Idee mit einer temporären Minimalkonfigurationsdatei (selbst erzeugt) weiterzuarbeiten.
					bDirectoryFound = false;
				}
			}								
		}
		if(bDirectoryFound){
			this.setFileConfigKernelDirectory(sDirectoryConfig);
		}
		return sDirectoryConfig;
	}
	protected void setFileConfigKernelDirectory(String sDirectoryConfig){
		this.sDirectoryConfig = sDirectoryConfig;
	}
	
	public IniFile getFileConfigKernelAsIni() throws ExceptionZZZ{
		IniFile objReturn = null;
		if(this.getFileConfigIni()==null){			
			File objFile = this.getFileConfigKernel();
			try {
				objReturn = new IniFile(objFile.getPath());
			} catch (IOException e) {
				String sLog = "Configuration File. Not able to create ini-FileObject.";
				System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": " + sLog);
				ExceptionZZZ ez = new ExceptionZZZ(sLog,iERROR_PARAMETER_VALUE, this,  ReflectCodeZZZ.getMethodCurrentName() );
				throw ez;
			} 
		}else {
			objReturn = this.getFileConfigIni().getFileIniObject();
		}
		return objReturn;	
	}
	
	public IniFile getFileConfigKernelAsIni(String sAlias) throws ExceptionZZZ{
		IniFile objReturn = null;
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
			try{
			//Hole zuerst das "Basis-File"
			IniFile objIni = this.getFileConfigKernelAsIni(); 
			
			
			//1. Hole den Dateinamen			
			String sFileNameUsed = this.searchFileConfigIniNameByAlias(objIni, sAlias);			
			if(StringZZZ.isEmpty(sFileNameUsed)){			
				ExceptionZZZ ez = new ExceptionZZZ("KernelKonfiguration ('"+objIni.getFileName()+"'): FileName not configured, is null or Empty for Modul-Alias '" + sAlias + "'", this.iERROR_CONFIGURATION_MISSING,this,  ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			//2. Hole den Dateipfad						
			String sFilePathUsed = this.searchFileConfigIniPathByAlias(objIni, sAlias);
			//Mache ruhig eine Überprüfung. Bei einem Leerstring wird nämlich normalerweise das Root zurückgegeben.
			if(StringZZZ.isEmpty(sFilePathUsed)){											
				ExceptionZZZ ez = new ExceptionZZZ("FilePath not configured, is null or Empty. PLUS: BaseDirectory not found.", this.iERROR_CONFIGURATION_MISSING,this,  ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			//3. Mache das neue Ini-Objekt
			String sPathTotalToUse = FileEasyZZZ.joinFilePathName(sFilePathUsed, sFileNameUsed);
			String sLog = "Trying to create new IniFile Object for path '" + sPathTotalToUse + "'.";
			System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": " + sLog);
			objReturn = new IniFile(sPathTotalToUse);
			
			//TODO GOON 20190214: Hier das neue Ini File der ArrayList der Dateien hinzufügen. Dann muss man es auch nicht immer wieder neu erstellen....
			
			} catch (IOException e) {
				String sLog = "Configuration File. Not able to create ini-FileObject.";
				System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": " + sLog);
				ExceptionZZZ ez = new ExceptionZZZ(sLog,iERROR_PARAMETER_VALUE, this,  ReflectCodeZZZ.getMethodCurrentName() );
				throw ez;
			}
		}//end main:
		return objReturn;
	}
		
	public String getApplicationKey() throws ExceptionZZZ{		
		String sApplicationKey = this.sApplicationKey;
		if(StringZZZ.isEmpty(sApplicationKey)){
			IKernelConfigZZZ objConfig = this.getConfigObject();
			sApplicationKey = objConfig.getApplicationKeyDefault();
			this.setApplicationKey(sApplicationKey);
		}
		return this.sApplicationKey;
	}
	protected void setApplicationKey(String sApplicationKey){
		this.sApplicationKey = sApplicationKey;
	}
	
	public String getSystemNumber() throws ExceptionZZZ{
		String sSystemNumber = this.sSystemNumber;
		if(StringZZZ.isEmpty(sSystemNumber)){
			IKernelConfigZZZ objConfig = this.getConfigObject();
			sSystemNumber = objConfig.getSystemNumberDefault();
			this.setSystemNumber(sSystemNumber);
		}		
		return this.sSystemNumber;
	}
	protected void setSystemNumber(String sSystemNumber){
		this.sSystemNumber=sSystemNumber;
	}

	/**
	 * @return sApplicationKey + "!" + sSystemNumber, also der Modulname z.B. f�r das Produktivsystem
	 * @throws ExceptionZZZ 
	 */
	public String getSystemKey() throws ExceptionZZZ{
		String stemp = this.getApplicationKey();
		String stemp2 = this.getSystemNumber();
		return KernelKernelZZZ.computeSystemKeyForSection(stemp, stemp2);
	}
	
	public static String computeSystemKeyForSection(String sSection, String sSystemNumber){
		String sReturn = null;
		main:{
			if(StringZZZ.isEmpty(sSection)) break main;
			if(!StringZZZ.isEmpty(sSystemNumber)){
				sReturn = sSection + "!" + sSystemNumber;	
			}else{
				sReturn = sSection;
			}
		}//end main:
		return sReturn;
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
	
	/** Reads in  the Kernel-Configuration-File the,directory and name information. Returns a file object. But: Doesn't proof the file existance !!! <CR>
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
					//IniFile objIni = this.getFileConfigKernelAsIni(); //Hier fehlt der Alias...
					//NEIN, das wäre wohl eine Endlosschleife: FileIniZZZ objKernelIni = this.getFileConfigIniByAlias(sAlias);
					IniFile objIni = this.getFileConfigKernelAsIni(sAlias);
					if(objIni==null){
						String sLog = "FileIni missing for Alias: " + sAlias;
						System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": " + sLog);
						ExceptionZZZ ez = new ExceptionZZZ(sLog,iERROR_PROPERTY_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
						throw ez;
					} //20190705: UND ... WAS WIRD NUN MIT DER GEFUNDENEN INI-DATEI gemacht? Neu: Setze die Datei.
														
					//++++++++++++++++++++++++++++++++++++++++++++++++++++
					//FEHLERMARKER
					//++++++++++++++++++++++++++++++++++++++++++++++++++++
					
					//1. Hole den Dateinamen										
					String sFileName = this.searchFileConfigIniNameByAlias(objIni, sAlias);
					if(StringZZZ.isEmpty(sFileName)){											
						ExceptionZZZ ez = new ExceptionZZZ("FileName not configured, is null or Empty", this.iERROR_CONFIGURATION_MISSING,this,  ReflectCodeZZZ.getMethodCurrentName());
						throw ez;
					}
														
					//2. Hole den Dateipfad	
					String sFilePath = this.searchFileConfigIniPathByAlias(objIni, sAlias);
					if(StringZZZ.isEmpty(sFilePath)){											
						ExceptionZZZ ez = new ExceptionZZZ("FilePath not configured, is null or Empty. PLUS: BaseDirectory not found.", this.iERROR_CONFIGURATION_MISSING,this,  ReflectCodeZZZ.getMethodCurrentName());
						throw ez;
					}
														
					//++++++++++++++++++++++++++++++++++++++++++++++++++++	
					//3. Neues FileIniZZZ Objekt erstellen, ggfs.
					if(this.objFileIniKernelConfig==null){												
						HashMap<String, Boolean> hmFlag = new HashMap<String, Boolean>();					
						FileIniZZZ exDummy = new FileIniZZZ();					
						String[] saFlagZpassed = this.getFlagZ_passable(true, exDummy);						
						objReturn = new FileIniZZZ(this,  sFilePath,sFileName,saFlagZpassed);

					}else{
						//Übernimm die gesetzten FlagZ...
						HashMap<String,Boolean>hmFlagZ = this.getFileConfigIni().getHashMapFlagZ();
						
						//Übernimm die gesetzten Variablen...
						HashMapCaseInsensitiveZZZ<String,String>hmVariable = this.getFileConfigIni().getHashMapVariable();
						objReturn = new FileIniZZZ(this,  sFilePath,sFileName,hmFlagZ);
						objReturn.setHashMapVariable(hmVariable);	
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
		
	public String searchFileConfigIniNameByAlias(IniFile objIni, String sAlias) throws ExceptionZZZ {
		String sReturn="";
		main:{
			check:{
				if(objIni == null) {
					ExceptionZZZ ez = new ExceptionZZZ("Missing parameter: 'inifile'",iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
				if(sAlias == null){							
					ExceptionZZZ ez = new ExceptionZZZ("Missing parameter: 'Alias'",iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
				if(sAlias.equals("")){							
					ExceptionZZZ ez = new ExceptionZZZ("Empty parameter: 'Alias'",iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}						
			}//end check:
		
			IKernelConfigSectionEntryZZZ objEntryFileName = this.searchPropertyByAlias(objIni, sAlias,"KernelConfigFile");					
			if(!objEntryFileName.hasAnyValue()){											
				//20191029: Den Alias ggfs. normieren, d.h. erst als alias mit Systemkey !01 ... Dann also alias ohne System key, d.h. vor dem !.
				//Das Problem ist sonst, dass die Property KernelConfigFileOVPN!01 heissen muss, für das System01.
				//Der PropertyName sollte aber über alle Systeme gleich sein.
				//DAHER DARF KEINE PROPERTY DEN "!SYSTEMNUMBER" IM NAMEN HABEN. 
				//ABER DER APPLICATIONKEY IST ERLAUBT
				
				String sApplicationKey = this.getApplicationKey();
				objEntryFileName = this.searchPropertyByAlias(objIni, sAlias,"KernelConfigFile"+sApplicationKey);
				if(!objEntryFileName.hasAnyValue()){
					System.out.println(ReflectCodeZZZ.getPositionCurrent()+ ": Kein Dateiname konfiguriert für den Alias  '" + sAlias + "' in Datei '" + objIni.getFileName() +"'");	
					break main;
				}
			}
			sReturn = objEntryFileName.getValue();
		}//end main:		
		return sReturn;
	}
	
	public String searchFileConfigIniPathByAlias(IniFile objIni, String sAlias) throws ExceptionZZZ {
		String sReturn="";
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
		
			String sFilePathUsed = null;
			IKernelConfigSectionEntryZZZ objEntryFilePath = this.searchPropertyByAlias(objIni, sAlias,"KernelConfigPath");
			if(!objEntryFilePath.hasAnyValue()){
				//20191029: Den Alias ggfs. normieren, d.h. erst als alias mit Systemkey !01 ... Dann also alias ohne System key, d.h. vor dem !.
				//Das Problem ist sonst, dass die Property KernelConfigFileOVPN!01 heissen muss, für das System01.
				//Der PropertyName sollte aber über alle Systeme gleich sein.
				//DAHER DARF KEINE PROPERTY DEN "!SYSTEMNUMBER" IM NAMEN HABEN. 
				//ABER DER APPLICATIONKEY IST ERLAUBT
								
				String sApplicationKey = this.getApplicationKey();
				objEntryFilePath = this.searchPropertyByAlias(objIni, sAlias,"KernelConfigPath"+sApplicationKey);
				if(!objEntryFilePath.hasAnyValue()){						
					System.out.println(ReflectCodeZZZ.getPositionCurrent()+ ": Kein DateiPfad konfiguriert für den Alias  '" + sAlias + "' in Datei '" + objIni.getFileName() +"'");	
					//break main; Auch mit leerem Dateipfad weitermachen....
				}else if(objEntryFilePath.hasNullValue()){
					System.out.println(ReflectCodeZZZ.getPositionCurrent()+": KernelConfigPath für den Alias '" + sAlias + "' ist ABSICHTLICH NULL. Erwarte die Datei im Projektordner.");
					File objDirProject = FileEasyZZZ.searchDirectory(null);
					sFilePathUsed = objDirProject.getAbsolutePath();
				}else{						
					sFilePathUsed = objEntryFilePath.getValue();
				}
			}else {
				sFilePathUsed = objEntryFilePath.getValue();
			}
			
			//NEIN, Weitermachen if(StringZZZ.isEmpty(sFilePathUsed)) break main;
			File objDirTemp = FileEasyZZZ.searchDirectory(sFilePathUsed);
			if(objDirTemp==null){
				String sLog = "Directory with name '" + sFilePathUsed + "' not found.";
				System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": " + sLog);
				ExceptionZZZ ez  = new ExceptionZZZ(sLog, iERROR_CONFIGURATION_VALUE, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			sReturn = objDirTemp.getAbsolutePath();
		}//end main:
		
		return sReturn;
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
																	
						//1. Hole den Dateinamen
						IKernelConfigSectionEntryZZZ objEntryFileName = this.searchPropertyByAlias(sAlias,"KernelConfigFile");
						if(!objEntryFileName.hasAnyValue()) break main;
						String sFileName = objEntryFileName.getValue();
						
						//2. Hole den Dateipfad
						IKernelConfigSectionEntryZZZ objEntryFilePath = this.searchPropertyByAlias(sAlias,"KernelConfigPath");
						//Auch wenn der Dateipfad nicht gepflegt ist weiterarbeiten. Es wird dann ein Standard genommen. if(!objEntryFilePath.hasAnyValue()) break main;
						String sFilePath = objEntryFilePath.getValue();
						
						/* Achtung: Es ist nicht Aufgabe dieser Funktion die Existenz der Datei zu pruefen*/					
						//FGL 20181008: Relative Fileangaben verarbeitet und Suche auf dem Classpath (z.B. wg. verpackt in .war / .jar Datei, z.B. WebService - Fall.
						objReturn = FileEasyZZZ.searchFile(sFilePath, sFileName); 
						
				}//end main:
			return objReturn;
		}
	
	private String KernelChooseMainSectionUsedForConfigFile_(String sMainSection, String sProgramOrSection) throws ExceptionZZZ{
		String sReturn = null;
		main:{
			if(!StringZZZ.isEmpty(sMainSection)){
				if(sMainSection.equals(sProgramOrSection)){
					sReturn = this.getApplicationKey();					
				}else{
					sReturn = sMainSection;
				}
				break main;				
			}
			
			sReturn = this.getApplicationKey();			
		}
		return sReturn;
	}
	
	private FileIniZZZ KernelSearchConfigFileByProgramAlias_(String sMainSection, String sProgramOrSection) throws ExceptionZZZ{
		FileIniZZZ objReturn=null;
		HashMapMultiIndexedZZZ hmDebug = new HashMapMultiIndexedZZZ();//Speichere hier die Suchwerte ab, um sie später zu Debug-/Analysezwecken auszugeben.
		
		String sSectionCacheUsed = sMainSection;
		String sPropertyCacheUsed = sProgramOrSection;
		main:{
			//############################
			//VORWEG: IM CACHE NACHSEHEN, OB DER EINTRAG VORHANDEN IST			
			ICachableObjectZZZ objFromCache = (ICachableObjectZZZ) this.getCacheObject().getCacheEntry(sSectionCacheUsed, sPropertyCacheUsed);
			if(objFromCache!=null){					
					hmDebug.put("From CacheZZZ: " + sSectionCacheUsed, sPropertyCacheUsed);
					objReturn = (FileIniZZZ) objFromCache;
					break main;				
			}else{
				hmDebug.put("Not in CacheZZZ: " + sSectionCacheUsed, sPropertyCacheUsed);
			}
			
			//1. Konfigurationsfile für das Program oder die Section holen	
			if(!StringZZZ.isEmpty(sProgramOrSection)){
	    		try{
	    			hmDebug.put("ProgramOrSection", sProgramOrSection);
		    		//String stemp = "Suche FileIniZZZ fuer das Programm / das Modul '" + sProgramOrSection + ".";
		    		//System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": "+ stemp);
		    		objReturn = this.getFileConfigIniByAlias(sProgramOrSection);		    		
		    	}catch(ExceptionZZZ ez2){
		    		System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": Versuch über den Programmnamen / das Modul '" + sProgramOrSection + "' schlägt fehl. ...");
		    	}
	    		if(objReturn!=null)break main;
			}
			
    		//2. Konfigurationsfile für die Mainsection holen
			if(!StringZZZ.isEmpty(sMainSection)){
	    		try{
	    			hmDebug.put("Modul", sMainSection);
		    		//String stemp = "Suche FileIniZZZ fuer Modul '" + sMainSection + ".";
		    		//System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": "+ stemp);
		    		objReturn = this.getFileConfigIniByAlias(sMainSection);		    		
		    	}catch(ExceptionZZZ ez2){
		    		System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": Versuch über die MainSection '" + sMainSection + "' schlägt fehl. ...");
		    	}
	    		if(objReturn!=null)break main;
			}
			
			//3. Konfigurationsfile des Systems holen
		    String sSystemkey = this.getSystemKey();
		    try{	
		    	hmDebug.put("System", sSystemkey);
		    	//String stemp = "Suche FileIniZZZ fuer Systemkey'" + sSystemkey + ".";
		    	//System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": "+ stemp);
		    	objReturn = this.getFileConfigIniByAlias(sSystemkey);		    	
		    }catch(ExceptionZZZ ez){
		    	System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": Versuch über den Systemkey '" + sSystemkey + "' schlägt fehl. ...");			    		
	    	}
		    if(objReturn!=null)break main;
		    
		  //4. Konfigurationsfile der Applikation holen
		    String sApplicationkey = this.getApplicationKey();
		    try{	
		    	hmDebug.put("Application", sApplicationkey);
		    	//String stemp = "Suche FileIniZZZ fuer Applicationkey'" + sApplicationkey + ".";
		    	//System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": "+ stemp);
		    	objReturn = this.getFileConfigIniByAlias(sApplicationkey);		    	
		    }catch(ExceptionZZZ ez){
		    	System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": Versuch über den Applicationkey '" + sApplicationkey + "' schlägt fehl. ...");			    		
	    	}
		    if(objReturn!=null)break main;
		    
		    if(objReturn==null){
    			//String stemp = "FileIniZZZ für ApplicationKey '" + this.getApplicationKey() +  "' oder  SystemKey '" + this.getSystemKey() + "', fuer Modul '" + sProgramOrSection + "' oder Modul '" + sMainSection + "' ist NULL.";
		    	String stemp = "ENDE DIESER SUCHE NACH FileIniZZZ OHNE ERFOLG +++ Suchpfad: " + hmDebug.debugString(":", "\t|");
				System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": "+ stemp);
				ExceptionZZZ ez = new ExceptionZZZ(stemp, iERROR_PARAMETER_VALUE, this,  ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
		   		
		}//end main:
		if(objReturn!=null){
			String stemp = "ERFOLGREICHES ENDE DIESER SUCHE NACH FileIniZZZ +++ Suchpfad: " + hmDebug.debugString(":", "\t|");
    		System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": "+ stemp);
    		
    		//Verwende die oben abgespeicherten Eingabewerte und nicht die Werte aus der Debug-Hashmap
			this.getCacheObject().setCacheEntry(sSectionCacheUsed, sPropertyCacheUsed, (ICachableObjectZZZ) objReturn);
		}
		return objReturn;								
	}
	
	private IKernelConfigSectionEntryZZZ searchPropertyByAlias(String sAlias, String sProperty) throws ExceptionZZZ{
		IKernelConfigSectionEntryZZZ objReturn = null;
		main:{
			check:{
			if(StringZZZ.isEmpty(sAlias)){								
				ExceptionZZZ ez = new ExceptionZZZ("Missing parameter: 'Alias'",iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}	
			if(StringZZZ.isEmpty(sProperty)){								
				ExceptionZZZ ez = new ExceptionZZZ("Missing parameter: 'Property'",iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}	
		}//end check:
			
			//first, get the Kernel-Configuration-INI-File
			IniFile objIni = this.getFileConfigKernelAsIni();			
			objReturn = this.searchPropertyByAlias(objIni, sAlias, sProperty);
		}//end main:
		return objReturn;		
	}
		
	
	/** Heuristische Lösung. 
	 *  Funktioniert so im Vergleich "Webservice" vs. "Swing Standalone in Eclipse"
	 * @return
	 * @author lindhaueradmin, 07.11.2018, 07:26:27
	 * @throws ExceptionZZZ 
	 */
	@Override
	public boolean isOnServer() throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			IKernelConfigZZZ objConfig = this.getConfigObject();
			bReturn = objConfig.isOnServer();
		}
		return bReturn;
	}
	
	private IKernelConfigSectionEntryZZZ searchPropertyByAlias(IniFile objIni, String sAlias, String sProperty) throws ExceptionZZZ{
		IKernelConfigSectionEntryZZZ objReturn = new KernelConfigSectionEntryZZZ(); //Hier schon die Rückgabe vorbereiten, falls eine weitere Verarbeitung nicht konfiguriert ist.
		HashMapMultiIndexedZZZ hmDebug = new HashMapMultiIndexedZZZ();//Speichere hier die Suchwerte ab, um sie später zu Debug-/Analysezwecken auszugeben.
		String sDebug = ""; 
		
		main:{
			check:{
			if(objIni==null){
				ExceptionZZZ ez = new ExceptionZZZ("Missing parameter: 'IniFile'",iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			if(StringZZZ.isEmpty(sAlias)){								
				ExceptionZZZ ez = new ExceptionZZZ("Missing parameter: 'Alias'",iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}	
			if(StringZZZ.isEmpty(sProperty)){								
				ExceptionZZZ ez = new ExceptionZZZ("Missing parameter: 'Property'",iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}	
		}//end check:

		System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": Verwende als ini-Datei für die Suche '"+ objIni.getFileName() + "'.");
		
			//Merke 20190119: Wird ein "empty" Tag in der ZFormel Sprache definiert. Dieser muss hier ausgewertet werden und auch den Abbruch bedingen, wenn er gefunden wird.
		    //Merke:               Das bedeutet eine Beschleunigung . Ein Leerstring bedingt nämlich, dass weitergesucht wird.
		    String sValueFound=null;
		   
			//1. Versuch: Speziell für die Komponente/das Modul definiert
			String sSection = sAlias;
			String sPropertyUsed = sProperty;
			//System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0) + ": Verwende als sSection '"+ sSection + "' für die Suche nach der Property '" + sPropertyUsed + "'");
			hmDebug.put(sSection, sPropertyUsed);
			sValueFound=objIni.getValue(sSection,sPropertyUsed);
			if(!StringZZZ.isEmpty(sValueFound)) {		
				//System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0)+ ": Value gefunden für Property '" + sPropertyUsed + "'=''" + sValueFound + "'");
				if(this.getFlag("useFormula")==true){
					String sReturnRaw = sValueFound;
					sValueFound = KernelExpressionIniConverterZZZ.getAsString(sReturnRaw);  //Auch ohne Formelauswertung die gefundenen Werte zumindest übersetzen
					if(!StringZZZ.equals(sValueFound,sReturnRaw)){
						System.out.println(ReflectCodeZZZ.getPositionCurrent()+ ": Value durch ExpressionIniConverter verändert von '" + sReturnRaw + "' nach '" + sValueFound +"'");
						objReturn.setRaw(sReturnRaw);
						objReturn.isExpression(true);
					}else{
						objReturn.setRaw(null);
						objReturn.isExpression(false);
					}
				}
				objReturn.setSection(sSection);
				objReturn.setProperty(sPropertyUsed);				
				objReturn.setValue(sValueFound);
				break main;
			}
				
			//2.1a. Versuch: Speziel für das System konfiguriert, mit dem Modulnamen (der dann ggfs. einer Klasse und deren Package entspricht)
			//                                                                         also z.B. KernelConfigFilebasic.zBasic.util.log.ReportLogZZZ
			sSection = this.getSystemKey();		
			sPropertyUsed = sProperty;
			//System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0) + ": Verwende als sSection '"+ sSection + "' für die Suche nach der Property '" + sPropertyUsed + "'");
			hmDebug.put(sSection, sPropertyUsed);
			sValueFound =objIni.getValue(sSection,sPropertyUsed);
			if(!StringZZZ.isEmpty(sValueFound)) {		
				System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0)+ ": Value gefunden für Property '" + sPropertyUsed + "'=''" + sValueFound + "'");
				if(this.getFlag("useFormula")==true){
					String sReturnRaw = sValueFound;
					sValueFound = KernelExpressionIniConverterZZZ.getAsString(sReturnRaw);  //Auch ohne Formelauswertung die gefundenen Werte zumindest übersetzen
					if(!StringZZZ.equals(sValueFound,sReturnRaw)){
						System.out.println(ReflectCodeZZZ.getPositionCurrent()+ ": Value durch ExpressionIniConverter verändert von '" + sReturnRaw + "' nach '" + sValueFound +"'");
						//this.setValueRaw(sReturnRaw);
						objReturn.setRaw(sReturnRaw);
						objReturn.isExpression(true);
					}else{
						//this.setValueRaw(null);
						objReturn.setRaw(null);
						objReturn.isExpression(false);
					}
				}
				objReturn.setSection(sSection);
				objReturn.setProperty(sPropertyUsed);
				objReturn.setValue(sValueFound);
				break main;
			}
			
			//2.1b. Versuch: Speziel für das System konfiguriert, mit dem Modulnamen (der dann ggfs. einer Klasse und deren Package entspricht)
			//                                                                         also z.B. KernelConfigFilebasic.zBasic.util.log.ReportLogZZZ
			sSection = this.getSystemKey();		
			sPropertyUsed = sProperty + sAlias;
			//System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0) + ": Verwende als sSection '"+ sSection + "' für die Suche nach der Property '" + sPropertyUsed + "'");
			hmDebug.put(sSection, sPropertyUsed);
			sValueFound =objIni.getValue(sSection,sPropertyUsed);
			if(!StringZZZ.isEmpty(sValueFound)) {		
				System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0)+ ": Value gefunden für Property '" + sPropertyUsed + "'=''" + sValueFound + "'");
				if(this.getFlag("useFormula")==true){
					String sReturnRaw = sValueFound;
					sValueFound = KernelExpressionIniConverterZZZ.getAsString(sReturnRaw);  //Auch ohne Formelauswertung die gefundenen Werte zumindest übersetzen
					if(!StringZZZ.equals(sValueFound,sReturnRaw)){
						System.out.println(ReflectCodeZZZ.getPositionCurrent()+ ": Value durch ExpressionIniConverter verändert von '" + sReturnRaw + "' nach '" + sValueFound +"'");
						//this.setValueRaw(sReturnRaw);
						objReturn.setRaw(sReturnRaw);
						objReturn.isExpression(true);
					}else{
						//this.setValueRaw(null);
						objReturn.setRaw(null);
						objReturn.isExpression(false);
					}
				}
				objReturn.setSection(sSection);
				objReturn.setProperty(sPropertyUsed);
				objReturn.setValue(sValueFound);
				break main;
			}
			
			//2.2a. Versuch: Global für die Applikation konfiguriert, mit dem Modulnamen (der dann ggfs. einer Klasse und deren Package entspricht)
			//                                                                         also z.B. KernelConfigFilebasic.zBasic.util.log.ReportLogZZZ
			sSection = this.getApplicationKey();		
			sPropertyUsed = sProperty;
			//System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0) + ": Verwende als sSection '"+ sSection + "' für die Suche nach der Property '" + sPropertyUsed + "'");
			hmDebug.put(sSection, sPropertyUsed);
			sValueFound =objIni.getValue(sSection,sPropertyUsed);	
			if(!StringZZZ.isEmpty(sValueFound)) {		
				System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0)+ ": Value gefunden für Property '" + sPropertyUsed + "'=''" + sValueFound + "'");
				if(this.getFlag("useFormula")==true){
					String sReturnRaw = sValueFound;
					sValueFound = KernelExpressionIniConverterZZZ.getAsString(sReturnRaw);  //Auch ohne Formelauswertung die gefundenen Werte zumindest übersetzen
					if(!StringZZZ.equals(sValueFound,sReturnRaw)){
						System.out.println(ReflectCodeZZZ.getPositionCurrent()+ ": Value durch ExpressionIniConverter verändert von '" + sReturnRaw + "' nach '" + sValueFound +"'");
						objReturn.setRaw(sReturnRaw);
						objReturn.isExpression(true);
					}else{				
						objReturn.setRaw(null);
						objReturn.isExpression(false);
					}
				}
				objReturn.setSection(sSection);
				objReturn.setProperty(sPropertyUsed);
				objReturn.setValue(sValueFound);
				break main;
			}
			
			//2.2b. Versuch: Global für die Applikation konfiguriert, mit dem Modulnamen (der dann ggfs. einer Klasse und deren Package entspricht)
			//                                                                         also z.B. KernelConfigFilebasic.zBasic.util.log.ReportLogZZZ
			sSection = this.getApplicationKey();		
			sPropertyUsed = sProperty + sAlias;
			//System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0) + ": Verwende als sSection '"+ sSection + "' für die Suche nach der Property '" + sPropertyUsed + "'");
			hmDebug.put(sSection, sPropertyUsed);
			sValueFound =objIni.getValue(sSection,sPropertyUsed);	
			if(!StringZZZ.isEmpty(sValueFound)) {		
				System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0)+ ": Value gefunden für Property '" + sPropertyUsed + "'=''" + sValueFound + "'");
				if(this.getFlag("useFormula")==true){
					String sReturnRaw = sValueFound;
					sValueFound = KernelExpressionIniConverterZZZ.getAsString(sReturnRaw);  //Auch ohne Formelauswertung die gefundenen Werte zumindest übersetzen
					if(!StringZZZ.equals(sValueFound,sReturnRaw)){
						System.out.println(ReflectCodeZZZ.getPositionCurrent()+ ": Value durch ExpressionIniConverter verändert von '" + sReturnRaw + "' nach '" + sValueFound +"'");
						objReturn.setRaw(sReturnRaw);
						objReturn.isExpression(true);
					}else{				
						objReturn.setRaw(null);
						objReturn.isExpression(false);
					}
				}
				objReturn.setSection(sSection);
				objReturn.setProperty(sPropertyUsed);
				objReturn.setValue(sValueFound);
				break main;
			}
			
			//3a. Versuch: Speziell für ein System konfiguriert.
			sSection =  this.getSystemKey();
			sPropertyUsed = sProperty;
			//System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0) + ": Verwende als sSection '"+ sSection + "' für die Suche nach der Property '" + sPropertyUsed + "'");
			hmDebug.put(sSection, sPropertyUsed);
			sValueFound =objIni.getValue(sSection,sPropertyUsed );	
			if(!StringZZZ.isEmpty(sValueFound)) {		
				System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0)+ ": Value gefunden für Property '" + sPropertyUsed + "'=''" + sValueFound + "'");
				if(this.getFlag("useFormula")==true){
					String sReturnRaw = sValueFound;
					sValueFound = KernelExpressionIniConverterZZZ.getAsString(sReturnRaw);  //Auch ohne Formelauswertung die gefundenen Werte zumindest übersetzen
					if(!StringZZZ.equals(sValueFound,sReturnRaw)){
						System.out.println(ReflectCodeZZZ.getPositionCurrent()+ ": Value durch ExpressionIniConverter verändert von '" + sReturnRaw + "' nach '" + sValueFound +"'");
						objReturn.setRaw(sReturnRaw);
						objReturn.isExpression(true);
					}else{
						objReturn.setSection(sSection);
						objReturn.setRaw(null);
						objReturn.isExpression(false);
					}
				}
				objReturn.setProperty(sPropertyUsed);
				objReturn.setValue(sValueFound);
				break main;
			}
					
			//3b. Versuch: Global für die Applikation konfiguriert
			sSection = this.getApplicationKey();
			sPropertyUsed = sProperty;
			//System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0) + ": Verwende als sSection '"+ sSection + "' für die Suche nach der Property '" + sPropertyUsed + "'");
			hmDebug.put(sSection, sPropertyUsed);
			sValueFound =objIni.getValue(sSection,sPropertyUsed );
			if(!StringZZZ.isEmpty(sValueFound)) {		
				System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0)+ ": Value gefunden für Property '" + sPropertyUsed + "'=''" + sValueFound + "'");
				if(this.getFlag("useFormula")==true){
					String sReturnRaw = sValueFound;
					sValueFound = KernelExpressionIniConverterZZZ.getAsString(sReturnRaw);  //Auch ohne Formelauswertung die gefundenen Werte zumindest übersetzen
					if(!StringZZZ.equals(sValueFound,sReturnRaw)){
						System.out.println(ReflectCodeZZZ.getPositionCurrent()+ ": Value durch ExpressionIniConverter verändert von '" + sReturnRaw + "' nach '" + sValueFound +"'");
						objReturn.setRaw(sReturnRaw);
						objReturn.isExpression(true);
					}else{
						objReturn.setRaw(null);
						objReturn.isExpression(false);
					}
				}
				objReturn.setSection(sSection);
				objReturn.setProperty(sPropertyUsed);
				objReturn.setValue(sValueFound);
				break main;
			}
					
						
			//4a. Versuch: Global für die Applikation konfiguriert, mit dem SystemKey
			sSection = this.getApplicationKey();		
			sPropertyUsed = sProperty + this.getSystemKey();
			//System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0) + ": Verwende als sSection '"+ sSection + "' für die Suche nach der Property '" + sPropertyUsed + "'");
			hmDebug.put(sSection, sPropertyUsed);
			sValueFound =objIni.getValue(sSection,sPropertyUsed);
			if(!StringZZZ.isEmpty(sValueFound)) {		
				System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0)+ ": Value gefunden für Property '" + sPropertyUsed + "'=''" + sValueFound + "'");
				if(this.getFlag("useFormula")==true){
					String sReturnRaw = sValueFound;
					sValueFound = KernelExpressionIniConverterZZZ.getAsString(sReturnRaw);  //Auch ohne Formelauswertung die gefundenen Werte zumindest übersetzen
					if(!StringZZZ.equals(sValueFound,sReturnRaw)){
						System.out.println(ReflectCodeZZZ.getPositionCurrent()+ ": Value durch ExpressionIniConverter verändert von '" + sReturnRaw + "' nach '" + sValueFound +"'");
						objReturn.setRaw(sReturnRaw);
						objReturn.isExpression(true);
					}else{
						objReturn.setRaw(null);
						objReturn.isExpression(false);
					}
				}
				objReturn.setSection(sSection);
				objReturn.setProperty(sPropertyUsed);
				objReturn.setValue(sValueFound);
				break main;
			}
							
			//4b. Versuch: Global für die Applikation konfiguriert, mit dem Applikationskey
			sSection = this.getApplicationKey();		
			sPropertyUsed = sProperty +this.getApplicationKey();
			//System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0) + ": Verwende als sSection '"+ sSection + "' für die Suche nach der Property '" + sPropertyUsed + "'");
			hmDebug.put(sSection, sPropertyUsed);
			sValueFound =objIni.getValue(sSection,sPropertyUsed);	
			if(!StringZZZ.isEmpty(sValueFound)) {		
				System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0)+ ": Value gefunden für Property '" + sPropertyUsed + "'=''" + sValueFound + "'");
				if(this.getFlag("useFormula")==true){
					String sReturnRaw = sValueFound;
					sValueFound = KernelExpressionIniConverterZZZ.getAsString(sReturnRaw);  //Auch ohne Formelauswertung die gefundenen Werte zumindest übersetzen
					if(!StringZZZ.equals(sValueFound,sReturnRaw)){
						System.out.println(ReflectCodeZZZ.getPositionCurrent()+ ": Value durch ExpressionIniConverter verändert von '" + sReturnRaw + "' nach '" + sValueFound +"'");
						objReturn.setRaw(sReturnRaw);
						objReturn.isExpression(true);
					}else{
						objReturn.setRaw(null);
						objReturn.isExpression(false);
					}
				}
				objReturn.setSection(sSection);
				objReturn.setProperty(sPropertyUsed);
				objReturn.setValue(sValueFound);
				break main;
			}
			
			//Falls irgendwann einmal ein Leerstring (oder ähnliches) gefunden wurde diesen zurückliefern und nicht NULL
			//TODO 20190804: Braucht man diesen Zweig noch, oder kann er weg?
			if(objReturn.hasAnyValue()){
				System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0)+ ": Zu guter Letzt. Erstmals gefundener Value (section='" + objReturn.getSection() + "') anwenden für Property '" + objReturn.getProperty() + "'=''" + objReturn.getValue() + "'");
				if(this.getFlag("useFormula")==true){
					String sReturnRaw = sValueFound;
					sValueFound = KernelExpressionIniConverterZZZ.getAsString(sReturnRaw);  //Auch ohne Formelauswertung die gefundenen Werte zumindest übersetzen
					if(!StringZZZ.equals(sValueFound,sReturnRaw)){
						System.out.println(ReflectCodeZZZ.getPositionCurrent()+ ": Value durch ExpressionIniConverter verändert von '" + sReturnRaw + "' nach '" + sValueFound +"'");
						objReturn.setRaw(sReturnRaw);
						objReturn.isExpression(true);
					}else{
						objReturn.setRaw(null);
						objReturn.isExpression(false);
					}
				}
				objReturn.setSection(sSection);
				objReturn.setProperty(sPropertyUsed);
				objReturn.setValue(sValueFound);
			}
		}//end main:
		
		sDebug = hmDebug.debugString(":"," | ");
		if(objReturn.hasAnyValue()){
			System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0) + ": ERFOLGREICHES ENDE DIESER SUCHE +++ Suchreihenfolge (Section:Property): " + sDebug); 
		}else{
			System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0) + ": ENDE DIESER SUCHE OHNE ERFOLG +++ Suchreihenfolge (Section:Property): " + sDebug);
		}
		return objReturn;
	}
	
	
	/** Parameter für ein ganzes Modul holen, aus der Modul-.ini-Datei, die erst noch hier ermittelt werden muss.
	 * Ein Modul ist der Teil, der zwischen dem KernelConfigPath und dem Gleichheitszeichen steht
	 * KernelConfigPath...=,
	 *  bzw. auch KernelConfigFile...=
	 *  Die drei Punkte ... stehen nun für den Modulnamen.
	 *        
	 * Dies ist z.B. der Aliasname für ein 'Program'-Teil
	 * Um für das Program einen Parameter zu holen muss verwendet werden "getParameterByProgramAlias(,,,)
	 * @param objFileConfig, Modul-Konfigurationsfile. Wird z.B. durch  objKernel.getFileConfigByAlias(sModuleAlias); ermittelt.
	 * @param sModuleAlias
	 * @param sParameter
	 * @return String. Wert hinter dem Gleichheitszeichen
	 * @throws ExceptionZZZ
	 */
	public IKernelConfigSectionEntryZZZ getParameterByModuleAlias(String sModuleAlias, String sParameter)  throws ExceptionZZZ{
		IKernelConfigSectionEntryZZZ objReturn = null; //new KernelConfigSectionEntryZZZ(); //Hier schon die Rückgabe vorbereiten, falls eine weitere Verarbeitung nicht konfiguriert ist.
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
						if(objFileModule.length()==0){
							ExceptionZZZ ez = new ExceptionZZZ("Configuration file '" + objFileModule.getAbsolutePath() + "' not filled for the module'" + sModuleAlias + "'",iERROR_CONFIGURATION_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
							throw ez;
						}
																														
						//+++ Now use another method
						objReturn = this.getParameterByModuleFile(objFileModule, sParameter);
			
					}//end main:
	return objReturn;		
}//end function getParameterByProgramAlias(..)
	
	public IKernelConfigSectionEntryZZZ[] getParameterByModuleAliasAsArray(String sModule, String sProperty) throws ExceptionZZZ{
		IKernelConfigSectionEntryZZZ[] objaReturn = null; //new KernelConfigSectionEntryZZZ(); //Hier schon die Rückgabe vorbereiten, falls eine weitere Verarbeitung nicht konfiguriert ist.		
		String sDebug = "";
		main:{
			check:{
				if(StringZZZ.isEmpty(sModule)){
					String stemp = "'String Module'";
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
				  
			IKernelConfigSectionEntryZZZ objReturn = this.getParameterByModuleAlias(sModule, sProperty);
			
			//Aber das wäre nur ein Stringwert, diesen versuchen zu splitten.
			try {
				objaReturn = KernelConfigSectionEntryZZZ.explode(objReturn, "");
			} catch (CloneNotSupportedException e) {			
				e.printStackTrace();
			}
		}//END main:
		return objaReturn;
	}
	
	
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
	public IKernelConfigSectionEntryZZZ getParameter(String sParameter) throws ExceptionZZZ{
		IKernelConfigSectionEntryZZZ objReturn = null; 
		main:{
			String sModulAlias = this.getApplicationKey();
			objReturn = this.getParameterByModuleAlias(sModulAlias, sParameter);
		}//END main:
		return objReturn;
	}
	
	/**Wie get Parameter, aber jetzt wird in den Suchstring auch noch der SystemKey eingebunden.
	 * @param sParameter
	 * @return
	 * @throws ExceptionZZZ
	 * lindhaueradmin, 07.07.2013
	 */
	public IKernelConfigSectionEntryZZZ getParameter4System(String sParameter) throws ExceptionZZZ{
		IKernelConfigSectionEntryZZZ objReturn = null; //new KernelConfigSectionEntryZZZ(); //Hier schon die Rückgabe vorbereiten, falls eine weitere Verarbeitung nicht konfiguriert ist.
		main:{
			String sModul = this.getApplicationKey();
			String sModulWithKey = this.getSystemKey(); //this.getApplicationKey();
			objReturn = this.getParameterByProgramAlias(sModul, sModulWithKey, sParameter);
		}//END main:
		return objReturn;
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
	public IKernelConfigSectionEntryZZZ getParameterByModuleFile(File objFileConfig, String sParameter) throws ExceptionZZZ{
		IKernelConfigSectionEntryZZZ objReturn = null; //new KernelConfigSectionEntryZZZ(); //Hier schon die Rückgabe vorbereiten, falls eine weitere Verarbeitung nicht konfiguriert ist.
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
				saFlagZpassed = StringArrayZZZ.remove(saFlagZpassed, "INIT", true);
				FileIniZZZ objFileIniConfig = new FileIniZZZ(this,  objFileConfig, saFlagZpassed);						
				
				objReturn = this.getParameterByModuleFile(objFileIniConfig,  sParameter);						
						}//end main:
		return objReturn;		
	}//end function getParameterByProgramAlias(..)

	public IKernelConfigSectionEntryZZZ getParameterByModuleFile(FileIniZZZ objFileIniConfig, String sParameter) throws ExceptionZZZ{
		IKernelConfigSectionEntryZZZ objReturn = null; //new KernelConfigSectionEntryZZZ(); //Hier schon die Rückgabe vorbereiten, falls eine weitere Verarbeitung nicht konfiguriert ist.
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
					String sModuleAlias = this.getApplicationKey(); //Merke: Bei der Suche wird erst nach einem Wert mit dem SystemKey gesucht, der ja ggfs. den ApplicationKey übersteuern kann. //this.getSystemKey();
					if(StringZZZ.isEmpty(sModuleAlias)){
						ExceptionZZZ ez = new ExceptionZZZ("Missing parameter: 'Module Alias'",iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
						throw ez;
					}								
					objReturn = KernelGetParameterByModuleFile_(objFileIniConfig, sModuleAlias, sParameter);

				}//end main:
			return objReturn;		
		}//end function getParameterByModuleAlias(..)
		
	
	private IKernelConfigSectionEntryZZZ KernelGetParameterByModuleFile_(FileIniZZZ objFileIniConfigIn, String sModuleAlias, String sProperty) throws ExceptionZZZ{
		IKernelConfigSectionEntryZZZ objReturn = null; //new KernelConfigSectionEntryZZZ(); //Hier schon die Rückgabe vorbereiten, falls eine weitere Verarbeitung nicht konfiguriert ist.
		main:{			
			objReturn = KernelGetParameterByProgramAlias_(objFileIniConfigIn, sModuleAlias, null, sProperty);
		}//END main:
		return objReturn;
	}
	
	/** String; Parameter basierend auf dem Systemkey holen und dem ALIAS eines Programms, innerhalb der Modul-.ini-Datei.
	 *
	* Der Aliasname des Programms ist dabei eine weitere Section in der .ini-Datei, mit folgendem Aufbau:
	 * [SYSTEMKEY!PROGRAMALIAS]
	 * 
	 * z.B. für das Programm mit dem Alias copy1 lautet die Section [FGL#01!copy1]
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
	public IKernelConfigSectionEntryZZZ getParameterByProgramAlias(File objFileConfig, String sProgramOrSection, String sProperty) throws ExceptionZZZ{
		IKernelConfigSectionEntryZZZ objReturn = null;//new KernelConfigSectionEntryZZZ(); //Hier schon die Rückgabe vorbereiten, falls eine weitere Verarbeitung nicht konfiguriert ist.
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
			objReturn = this.getParameterByProgramAlias(objIni, sProgramOrSection, sProperty);
		}//end main:
	return objReturn;		
}//end function getParameterByProgramAlias(..)
	
	/**Gibt den Konfigurierten Wert eines Programms wieder. Dabei werden globale Werte durch "Speziell" für die SystemNumber definierte Werte �berschrieben
	 * @param objFileIniConfig
	 * @param sAliasProgramOrSection  
	 * @param sProperty
	 * @return Null, wenn die Property nicht gefunden werden kann, sonst String
	 * @throws ExceptionZZZ, wenn z.B. keine Sektion aus sProgramOrSection in der Konfigurationsdatei existiert.
	 *
	 * javadoc created by: 0823, 20.10.2006 - 09:03:54
	 */
	public IKernelConfigSectionEntryZZZ getParameterByProgramAlias(FileIniZZZ objFileIniConfig, String sAliasProgramOrSection, String sProperty) throws ExceptionZZZ{
		IKernelConfigSectionEntryZZZ objReturn = null; //new KernelConfigSectionEntryZZZ(); //Hier schon die Rückgabe vorbereiten, falls eine weitere Verarbeitung nicht konfiguriert ist.
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

				String sProgramOrSection = objFileIniConfig.getPropertyValue(this.getSystemKey(), sAliasProgramOrSection).getValue();
				if(StringZZZ.isEmpty(sProgramOrSection)){
					sProgramOrSection = sAliasProgramOrSection;
				}							
				
				objReturn = this.KernelGetParameterByProgramAlias_(objFileIniConfig, sProgramOrSection, sAliasProgramOrSection, sProperty);								
	}//end main:
	return objReturn;		
}//end function getParameterByProgramAlias(..)
		
	
	public IKernelConfigSectionEntryZZZ getParameterByProgramAlias(String sModule, String sProgramOrSection, String sProperty) throws ExceptionZZZ{
		IKernelConfigSectionEntryZZZ objReturn = null; //new KernelConfigSectionEntryZZZ(); //Hier schon die Rückgabe vorbereiten, falls eine weitere Verarbeitung nicht konfiguriert ist.		
		String sDebug = "";
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
				  
			objReturn = this.KernelGetParameterByProgramAlias_(null, sModule, sProgramOrSection, sProperty);
		}//END main:
		return objReturn;
	}
	
	public IKernelConfigSectionEntryZZZ getParameterByProgramAlias(FileIniZZZ objFileIniConfig, String sModule, String sProgramOrSection, String sProperty) throws ExceptionZZZ{
		IKernelConfigSectionEntryZZZ objReturn = null;//new KernelConfigSectionEntryZZZ(); //Hier schon die Rückgabe vorbereiten, falls eine weitere Verarbeitung nicht konfiguriert ist.
		main:{
			check:{
				if(objFileIniConfig==null){
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

			objReturn = this.KernelGetParameterByProgramAlias_(objFileIniConfig, sModule, sProgramOrSection, sProperty);
		}//END main:
		return objReturn;
	}
	
	private IKernelConfigSectionEntryZZZ KernelGetParameterByProgramAlias_AliasSystemLookup_(String sDebugKey, HashMapMultiIndexedZZZ hmDebug, String sSection, String sProperty, FileIniZZZ objFileIniConfig) throws ExceptionZZZ{
		IKernelConfigSectionEntryZZZ objReturn = new KernelConfigSectionEntryZZZ(); //Hier schon die Rückgabe vorbereiten, falls eine weitere Verarbeitung nicht konfiguriert ist.
		main:{
			if(!StringZZZ.isEmpty(sSection)){
				IKernelConfigSectionEntryZZZ objTemp = null;
				
//				CounterHandlerSingleton_AlphanumericSignificantZZZ objHandler = CounterHandlerSingleton_AlphanumericSignificantZZZ.getInstance();
//				ICounterAlphanumericSignificantZZZ objCounter = objHandler.getCounterFor();
				CounterHandlerSingleton_AlphabetZZZ objHandler = CounterHandlerSingleton_AlphabetZZZ.getInstance();
				ICounterAlphabetZZZ objCounter = objHandler.getCounterFor();
				String sSearchCounter = objCounter.getStringNext(); //objCounter.current();
				
				
				//a) mit Systemkey
				//String sDebugKeyUsed = sDebugKey + ".a";
				//System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0)+ ": " + sDebugKeyUsed + " Suche nach einem Alias in '"+ this.getSystemKey() + "' für das Programm oder die Section '" + sSection + "'");
				String sSectionUsed = this.getSystemKey();
				String sPropertyUsed = sSection; //Erst nach einem möglichen Aliaswert hier suchen.
				hmDebug.put(sDebugKey + "(" + sSearchCounter + ")." + sSectionUsed, sPropertyUsed);	
				objTemp = objFileIniConfig.getPropertyValue(sSectionUsed, sPropertyUsed);				
				if(objTemp.hasAnyValue()){									
					sSectionUsed = objTemp.getValue();
					sPropertyUsed = sProperty; //nun erst die nach der übergebenen Property suchen. 
					hmDebug.put(sDebugKey + "(" + sSearchCounter + ")." + sSectionUsed, sPropertyUsed);	
					
					objReturn = KernelGetParameterByProgramAlias_DirectLookup_(sSectionUsed, sPropertyUsed, objFileIniConfig);
					if(objReturn.hasAnyValue()) break main;
				}
				
				//b) mit ApplicationKey
				//sDebugKeyUsed = sDebugKey + ".b";
				//System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0)+ ": " + sDebugKeyUsed + " Suche nach einem Alias in '"+ this.getApplicationKey() + "' für das Programm oder die Section '" + sSection + "'");
				sSectionUsed = this.getApplicationKey();								
				sPropertyUsed = sSection; 
				hmDebug.put(sDebugKey + "(" + sSearchCounter + ")." + sSectionUsed, sPropertyUsed);	
				objTemp = objFileIniConfig.getPropertyValue(sSectionUsed, sPropertyUsed);
				if(objTemp.hasAnyValue()){
					sSectionUsed = objTemp.getValue();
					sPropertyUsed = sProperty;
					hmDebug.put(sDebugKey + "(" + sSearchCounter + ")." + sSectionUsed, sPropertyUsed);	
					objReturn = KernelGetParameterByProgramAlias_DirectLookup_(sSectionUsed, sPropertyUsed, objFileIniConfig);
					if(objReturn.hasAnyValue()) break main;				
				}
			}
		}//End main:
		return objReturn;
	}
	
	private IKernelConfigSectionEntryZZZ KernelGetParameterByProgramAlias_SystemLookup_(String sDebugKey, HashMapMultiIndexedZZZ hmDebug, String sSection, String sProperty, FileIniZZZ objFileIniConfig) throws ExceptionZZZ{
		IKernelConfigSectionEntryZZZ objReturn = new KernelConfigSectionEntryZZZ(); //Hier schon die Rückgabe vorbereiten, falls eine weitere Verarbeitung nicht konfiguriert ist.
		main:{
			if(!StringZZZ.isEmpty(sSection)){				
//				CounterHandlerSingleton_AlphanumericSignificantZZZ objHandler = CounterHandlerSingleton_AlphanumericSignificantZZZ.getInstance();
//				ICounterAlphanumericSignificantZZZ objCounter = objHandler.getCounterFor();
				
//				CounterHandlerSingleton_AlphabetZZZ objHandler = CounterHandlerSingleton_AlphabetZZZ.getInstance();
//				ICounterAlphabetZZZ objCounter = objHandler.getCounterFor();
				
				CounterHandlerSingleton_AlphabetSignificantZZZ objHandler = CounterHandlerSingleton_AlphabetSignificantZZZ.getInstance();
				ICounterAlphabetSignificantZZZ objCounter = objHandler.getCounterFor();
				
				String sSearchCounter = objCounter.getStringNext(); //objCounter.current();
				
				//a) mit Systemkey
				
				
				//String sDebugKeyUsed = sDebugKey + ".a" + sSearchCounter;
				//System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined()+ sDebugKeyUsed);

				String sSectionUsed = this.getSystemKey() + "!" + sSection;	
				hmDebug.put(sDebugKey + "(" + sSearchCounter + ") " + sSectionUsed, sProperty);				
				//System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0)+ ": " + sDebugKeyUsed + "Suche in '"+ sSectionUsed + "' nach dem Wert '" + sProperty + "'");			
				objReturn = KernelGetParameterByProgramAlias_DirectLookup_(sSectionUsed, sProperty, objFileIniConfig);
				if(objReturn.hasAnyValue()) break main;
					
				//b) mit ApplicationKey
				//sDebugKeyUsed = sDebugKey + ".b" + sSearchCounter;
				//System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined()+ sDebugKeyUsed);
				
				sSectionUsed = this.getApplicationKey() + "!" + sSection;	
				hmDebug.put(sDebugKey + "(" + sSearchCounter + ") " + sSectionUsed, sProperty);
				//System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0)+ ": " + sDebugKeyUsed + "Suche in '"+ sSectionUsed + "' nach dem Wert '" + sProperty + "'");			
				objReturn = KernelGetParameterByProgramAlias_DirectLookup_(sSectionUsed, sProperty, objFileIniConfig);
				if(objReturn.hasAnyValue()) break main;	

				}//if(!StringZZZ.isEmpty(sSection)){									
		}//End main:
		return objReturn;
	}
	
	private IKernelConfigSectionEntryZZZ KernelGetParameterByProgramAlias_DirectLookup_(String sSection, String sProperty, FileIniZZZ objFileIniConfig) throws ExceptionZZZ{
		IKernelConfigSectionEntryZZZ objReturn = new KernelConfigSectionEntryZZZ(); //Hier schon die Rückgabe vorbereiten, falls eine weitere Verarbeitung nicht konfiguriert ist.	
		main:{
			if(!StringZZZ.isEmpty(sSection)){
				objReturn.setSection(sSection);
			    boolean bSectionExists = objFileIniConfig.proofSectionExists(sSection);
				if(bSectionExists==true){					
					objReturn.sectionExists(true);
					objReturn = objFileIniConfig.getPropertyValue(sSection, sProperty);
				}else{					
					objReturn.sectionExists(false);
				}
			}
		}//End main:
		return objReturn;
	}
	
	private IKernelConfigSectionEntryZZZ KernelGetParameterByProgramAlias_(FileIniZZZ objFileIniConfigIn, String sMainSection, String sProgramOrSection, String sProperty) throws ExceptionZZZ{
		IKernelConfigSectionEntryZZZ objReturn = new KernelConfigSectionEntryZZZ(); //Hier schon die Rückgabe vorbereiten, falls eine weitere Verarbeitung nicht konfiguriert ist.		
		HashMapMultiIndexedZZZ hmDebug = new HashMapMultiIndexedZZZ();//Speichere hier die Suchwerte ab, um sie später zu Debug-/Analysezwecken auszugeben.
		String sDebug;
		
		//Merke: Die Eingabewerte werden ggfs. als Schlüssel für den Cache verwendet.
		//       Sowohl für die Suche im Cache, als auch für das Speichern im Cache. Dadurch wird Performance erreicht.
		String sSectionCacheUsed = null;					
		if(StringZZZ.isEmpty(sProgramOrSection)){
			sSectionCacheUsed = sMainSection;						
		}else{
			sSectionCacheUsed = sProgramOrSection;
		}
		String sPropertyCacheUsed = sProperty;
		
		
		main:{		
			//############################
			//VORWEG: IM CACHE NACHSEHEN, OB DER EINTRAG VORHANDEN IST
			IKernelConfigSectionEntryZZZ objFromCache = (IKernelConfigSectionEntryZZZ) this.getCacheObject().getCacheEntry(sSectionCacheUsed, sPropertyCacheUsed);
			if(objFromCache!=null){					
					hmDebug.put("From CacheZZZ: " + sSectionCacheUsed, sPropertyCacheUsed);
					objReturn = objFromCache;
					break main;				
			}else{
				hmDebug.put("Not in CacheZZZ: " + sSectionCacheUsed, sPropertyCacheUsed);
			}
			
			//###################################################################################################			
		    //IDEE: Mache eine Factory, über die dann ein 'AsciiCounterZweistellig' Objekt erstellt werden kann
			//      Dementsprechend in solch einem Objekt den Startwert speichern ung ggfs. per Konstruktor erstellen.
			//      Dann die objAsciiCounterZweistellig.next() bzw. die objAsciiCounterZweistellig.increase() Methode 
			//      letztere zum Endgültigen setzen und erhöhen des Werts anbieten.
			
			//int iStartValue=CounterByCharacterAscii_AlphanumericZZZ.iPOSITION_MAX;//Zählvariable (beginne anschliessend mit zweistelligen Strings), um im Log den Suchschritt unterscheidbar zu machen.
						
			//Hier einen SingletonCounter holen!!!		
			//CounterHandlerSingleton_AlphanumericSignificantZZZ objHandler = CounterHandlerSingleton_AlphanumericSignificantZZZ.getInstance();
			//String sStartValue= "A0";
			//ICounterStrategyAlphanumericSignificantZZZ objCounterStrategy = new CounterStrategyAlphanumericSignificantZZZ(4,"0", sStartValue);	
			//objHandler.setCounterStrategy(objCounterStrategy);
			//ICounterAlphanumericSignificantZZZ objCounter = objHandler.getCounterFor();
			
						
			String sStartValue= "";
			//CounterHandlerSingleton_AlphabetZZZ objHandler = CounterHandlerSingleton_AlphabetZZZ.getInstance();
			//ICounterStrategyAlphabetZZZ objCounterStrategy = new CounterStrategyAlphabetSerialZZZ(sStartValue);
			//objHandler.setCounterStrategy(objCounterStrategy);
			//ICounterAlphabetZZZ objCounter = objHandler.getCounterFor();
			
			CounterHandlerSingleton_AlphabetSignificantZZZ objHandler = CounterHandlerSingleton_AlphabetSignificantZZZ.getInstance();
			ICounterStrategyAlphabetSignificantZZZ objCounterStrategy = new CounterStrategyAlphabetSignificantZZZ(4,"0",sStartValue);
			objCounterStrategy.isIncreasableInOtherMethod(false);//Merke: Die erste Suche geht schon für die Suche nach dem Modul drauf, z.B. KernelConfigPathTestModule. So dass hier schon der 2. Zähler verwendet würde.
			objHandler.setCounterStrategy(objCounterStrategy);
			ICounterAlphabetSignificantZZZ objCounter = objHandler.getCounterFor();
			String sSearchCounter = objCounter.getStringNext();  // objCounter.current();
			String sDebugKey=null;
			//#################################################################################################
			//1. Konfigurationsfile des Systems holen
			FileIniZZZ objFileIniConfig = null;
		    if(objFileIniConfigIn==null){
		    	objFileIniConfig = this.KernelSearchConfigFileByProgramAlias_(sMainSection, sProgramOrSection);		    	
		    }else{
		    	String stemp = "Verwende übergebenes FileIniZZZ.";
	    		System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": "+ stemp);
		    	objFileIniConfig = objFileIniConfigIn;
		    }
		   
		    //2. Den Abschnitt holen
			String sMainSectionUsed = this.KernelChooseMainSectionUsedForConfigFile_(sMainSection, sProgramOrSection);
			String sSectionUsed = null;	
			
			//#######################################################################
			//+++ Ggfs. direkt als Program deklarierter Wert									
			if(!StringZZZ.isEmpty(sProgramOrSection)){
				if(sProgramOrSection!=this.getSystemKey() && sProgramOrSection!=this.getApplicationKey()){ //Damit keine doppelte Abfrage gemacht wird.
					sSectionUsed=sProgramOrSection;
					sDebugKey = "(00)";
					hmDebug.put(sDebugKey + "(" + sSearchCounter + ") " + sSectionUsed, sProperty);
					objReturn = KernelGetParameterByProgramAlias_DirectLookup_(sSectionUsed, sProperty, objFileIniConfig);
					if(objReturn.hasAnyValue()) break main;
				}
			}
			
			//###############################################################################
			// Als Program definierte Werte (Merke: Die direkt definierten Werte - d.h. ohne Application/Systemkey - wurden schon eher abgefragt)
			//###############################################################################
			//3a. Für den Fall, dass der Programname direkt angegeben wurde. Suche ihn im System-/Applicationkey
			if(!StringZZZ.isEmpty(sProgramOrSection)){
				objReturn = KernelGetParameterByProgramAlias_SystemLookup_("(2a)", hmDebug, sProgramOrSection, sProperty, objFileIniConfig);
				if(objReturn.hasAnyValue()) break main;	
				
				//3b. Ermittle ggfs. den Aliasnamen eines Programms immer aus der verwendeten "MainSection" des Systems			
				String sSystemNumber= this.getSystemNumber();
				ArrayListExtendedZZZ<String>listasAlias = this.getProgramAliasUsed(objFileIniConfig,sMainSectionUsed, sProgramOrSection, sSystemNumber);
									
				//+++ Die Section als Program mit Alias und vorangestelltem Systemkey definiert:			
				Iterator<String> itAlias = listasAlias.iterator();
				while(itAlias.hasNext()){
					String sSectionTemp = itAlias.next(); //der verwendete Programalias zur Suche nach der Section. Da auch Systemkeys darin sein sollten, anschliessend den DirectLookup machen und nicht den System-Lookup
					
					//Ggfs. wird der Systemkey explizit noch verwendet, gefolgt vom definierten Alias
					sSectionUsed = this.getSystemKey() + "!" + sSectionTemp;					
					sDebugKey = "(2b1)";
					hmDebug.put(sDebugKey + "(" + sSearchCounter + ") " + sSectionUsed, sProperty);
					objReturn = KernelGetParameterByProgramAlias_DirectLookup_(sSectionUsed, sProperty, objFileIniConfig);
					if(objReturn.hasAnyValue()) break main;	
					
					
					//Ggfs. wird der Systemkey explizit noch verwendet, gefolgt vom definierten Alias
					sSectionUsed = this.getApplicationKey() + "!" + sSectionTemp;					
					sDebugKey = "(2b2)";
					hmDebug.put(sDebugKey + "(" + sSearchCounter + ") " + sSectionUsed, sProperty);
					objReturn = KernelGetParameterByProgramAlias_DirectLookup_(sSectionUsed, sProperty, objFileIniConfig);
					if(objReturn.hasAnyValue()) break main;	
					
					
					//Nur den definierten Alias verwenden
					sSectionUsed = sSectionTemp;					
					sDebugKey = "(2b3)";
					hmDebug.put(sDebugKey + "(" + sSearchCounter + ") " + sSectionUsed, sProperty);
					objReturn = KernelGetParameterByProgramAlias_DirectLookup_(sSectionUsed, sProperty, objFileIniConfig);
					if(objReturn.hasAnyValue()) break main;						
				}//end while
				if(listasAlias.size()==0){
					System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0)+ "Keine möglichen Programaliaswerte gefunden. Suche direkter nach der Property.'" + sProperty +"'.");
				}else{
					System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0)+ "Keinen Value gefunden in einem möglichen Programalias. Suche direkter nach der Property.'" + sProperty +"'.");
				}
			}
			
			 //##################################################################################		
			//+++ Die Section als einen ggfs. auf Systemkey definierten Aliasnamen vorhanden. Aber: Die Section hat keine erweiterung mit ! und irgendeiner Systemnumber.			
			if(!StringZZZ.isEmpty(sProgramOrSection)){
				sSectionUsed = sProgramOrSection;	
				sDebugKey = "(3)";
				objReturn = KernelGetParameterByProgramAlias_AliasSystemLookup_(sDebugKey, hmDebug, sSectionUsed, sProperty, objFileIniConfig);
				if(objReturn.hasAnyValue())break main;							
			}//if(!StringZZZ.isEmpty(sProgramOrSection)){
		
			//#######################################################################
			//+++ B1. Ggfs. ohne Program deklarierter Wert
			//########################################################################
			if(sMainSection!=this.getSystemKey() && sMainSection!=this.getApplicationKey()){ //Damit keine doppelte Abfrage gemacht wird.
				sSectionUsed=sMainSection+"!"+this.getSystemNumber();	//Immer zuerst die ggfs. überschreibende Variante
				sDebugKey = "(0a)";
				hmDebug.put(sDebugKey + "(" + sSearchCounter + ") " + sSectionUsed, sProperty);
				objReturn = KernelGetParameterByProgramAlias_DirectLookup_(sSectionUsed, sProperty, objFileIniConfig);
				if(objReturn.hasAnyValue()) break main;	
								
				sSectionUsed=sMainSection;
				sDebugKey = "(0b)";
				hmDebug.put(sDebugKey + "(" + sSearchCounter + ") " + sSectionUsed, sProperty);
				objReturn = KernelGetParameterByProgramAlias_DirectLookup_(sSectionUsed, sProperty, objFileIniConfig);
				if(objReturn.hasAnyValue()) break main;									
			}
			
			//B2.  Suche Werte ohne Programm, nur nach Systemkey				
			sSectionUsed = this.getSystemKey();
			sDebugKey = "(1a)";
			hmDebug.put(sDebugKey + "(" + sSearchCounter + ") " + sSectionUsed, sProperty);
			objReturn = KernelGetParameterByProgramAlias_DirectLookup_(sSectionUsed, sProperty, objFileIniConfig);
			if(objReturn.hasAnyValue()) break main;				
			
			
			//B3.  Suche Werte ohne Programm, nur nach Applicationkey						
			sSectionUsed = this.getApplicationKey();
			sDebugKey = "(1b)";
			hmDebug.put(sDebugKey + "(" + sSearchCounter + ") " + sSectionUsed, sProperty);
			objReturn = KernelGetParameterByProgramAlias_DirectLookup_(sSectionUsed, sProperty, objFileIniConfig);
			if(objReturn.hasAnyValue()) break main;	
			
			

			//###############################################################################################################
			//Falls der Parameter immer noch nicht gefunden wurde, hier eine Exception auswerfen.
	        //Ansonsten droht die Gefahr einer Endlosschleife.
				String sModuleUsed="";
				boolean bModuleConfig = this.proofModuleFileIsConfigured(sMainSection);
				if(bModuleConfig==false){
																		
					//A2. Versuch: Prüfen, ob es als Systemkey konfiguriert ist
					sModuleUsed = this.getSystemKey();
					bModuleConfig = this.proofModuleFileIsConfigured(sModuleUsed);
					if(bModuleConfig==false){
						
						//A3. Versuch: Prüfen, ob es als Applikationskey konfiguriert ist
						sModuleUsed = this.getApplicationKey();
						bModuleConfig = this.proofModuleFileIsConfigured(sModuleUsed);
						if(bModuleConfig==false){
							String stemp = "Wrong parameter: Module '" + sModuleUsed + "' is not configured or property could not be found anywhere in the file in the file '" + objFileIniConfig.getFileObject().getAbsolutePath() + "' for the property: '" + sProperty + "'.";
							System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": "+ stemp);
							ExceptionZZZ ez = new ExceptionZZZ(stemp,iERROR_CONFIGURATION_VALUE, this,  ReflectCodeZZZ.getMethodCurrentName());
							throw ez;
						} //end if Versuch 3
					}else{
						String stemp = "Wrong parameter (2): Module '" + sModuleUsed + "' is not configured in the ini File '" + objFileIniConfig.getFileObject().getAbsolutePath() + "'.";
						System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": "+ stemp);
						ExceptionZZZ ez = new ExceptionZZZ(stemp,iERROR_CONFIGURATION_VALUE, this,  ReflectCodeZZZ.getMethodCurrentName());
						throw ez;
					}//end if Versuch 2
				}else{
					String stemp = "Wrong parameter (1): Module '" + sMainSection + "' is not configured in the ini File '" + objFileIniConfig.getFileObject().getAbsolutePath() + "'.";
					System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": "+ stemp);
					ExceptionZZZ ez = new ExceptionZZZ(stemp,iERROR_CONFIGURATION_VALUE, this,  ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}//end if Versuch 1
				
				//B1. Prüfen, ob das Modul existiert
				boolean bModuleExists = this.proofModuleFileExists(sMainSection);
				if(bModuleExists==false){
					String stemp = "Wrong parameter: Module '" + sModuleUsed + "' does not exist or property could not be found anywhere in the file '" + objFileIniConfig.getFileObject().getAbsolutePath() + "' for the property: '" + sProperty + "'.";
					System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": "+ stemp);
					ExceptionZZZ ez = new ExceptionZZZ(stemp,iERROR_CONFIGURATION_VALUE, this,  ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}

				//Abbruch der Parametersuche. Ohne diesen else-Zweig, gibt es ggfs. eine Endlosschleife.
				sDebug = hmDebug.debugString(":"," | ");
				System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0) + ": ABBRUCH DIESER SUCHE OHNE ERFOLG +++ Suchreihenfolge (Section:Property): " + sDebug + " in der Datei '" + objFileIniConfig.getFileObject().getAbsolutePath() + "'");
				ExceptionZZZ ez = new ExceptionZZZ(sDebug, iERROR_CONFIGURATION_VALUE, this,  ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
				}//END main:
				
				sDebug = hmDebug.debugString(":"," | ");
				if(objReturn.hasAnyValue()) {										
					System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0) + ": ERFOLGREICHES ENDE DIESER SUCHE +++ Suchreihenfolge (Section:Property): " + sDebug);
					
					//################
					//DEBUG: Entsprechen die oben eingegebenen Suchstrings auch dem 1. Suchschritt?
					/*
					Set<Integer> setKeyOuter = hmDebug.keySet();
					Integer[] objaKeyOuter = (Integer[]) setKeyOuter.toArray(new Integer[setKeyOuter.size()]);
					Integer obj = objaKeyOuter[0];
					
					//Nun diesen Wert in den Cache übernehmen.
					HashMap<String,String>hmInner = hmDebug.getInnerHashMap(obj);//Das war der 1. Versuch. Er sollte die Eingabewerte beinhalten. Diese sollen die Keys für den Cache sein. Dann wird der Wert beim 2. Aufruf sofort gefunden.
					Set<String> setKey = hmInner.keySet();
					String[] saKey = (String[]) setKey.toArray(new String[setKey.size()]);				
					String sKey = saKey[0];					
					String sValue = hmInner.get(sKey);
					*/
					//###################
					
					//Verwende die oben abgespeicherten Eingabewerte und nicht die Werte aus der Debug-Hashmap
					this.getCacheObject().setCacheEntry(sSectionCacheUsed, sPropertyCacheUsed, (ICachableObjectZZZ) objReturn);
					
				}else{
					System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0) + ": ENDE DIESER SUCHE OHNE ERFOLG +++ Suchreihenfolge (Section:Property): " + sDebug);
				}				
				return objReturn;
	}
	
	private boolean KernelSetParameterByProgramAlias_DirectLookup_(String sSection, String sProperty, String sValue, boolean bSetNew, boolean bFlagDelete, boolean bFlagSaveImmidiate, FileIniZZZ objFileIniConfig) throws ExceptionZZZ{
		boolean  bReturn = false;
		main:{			
			//System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0)+ ": " + sDebugKey + " Verwende als sSection '"+ sSection + "' für die Suche nach der Property '" + sProperty + "'");			
			if(!StringZZZ.isEmpty(sSection)){
				boolean bSectionExists = objFileIniConfig.proofSectionExists(sSection);
				if(bSectionExists==true){
					System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0)+ ": !!! Section gefunden für '"+ sSection + "'");
					if(!bSetNew || bFlagDelete){				
						boolean bValueExists = objFileIniConfig.proofValueExists(sSection, sProperty);
						if(bValueExists){
							if(bFlagDelete==false){					
								bReturn = objFileIniConfig.setPropertyValue(sSection, sProperty, sValue, bFlagSaveImmidiate);
								if (bReturn) {
									System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0)+ ": Value erfolgreich  (ggfs. konvertiert) gesetzt für Property '" + sProperty + "'=''" + sValue + "'");
									break main;
								}	
							}else{
								bReturn =  objFileIniConfig.deleteProperty(sSection, sProperty, bFlagSaveImmidiate);
								if (bReturn) {
									System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0)+ ": Value erfolgreich gelöscht für Property '" + sProperty + "'=''" + sValue + "'");
									break main;
								}
							}
						}
					}else{
						//Setzen, im 2. Durchlauf
						bReturn = objFileIniConfig.setPropertyValue(sSection, sProperty, sValue, bFlagSaveImmidiate);
						if (bReturn) {
							System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0)+ ": Value erfolgreich  (ggfs. konvertiert) gesetzt für Property '" + sProperty + "'=''" + sValue + "'");
							break main;
						} else {
							System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0)+ ": !!! Value nicht erfolgreich gesetzt für Property '" + sProperty + "'=''" + sValue + "'");
							break main;
						}
					}
				}else{
					System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0)+ ": Keine Section gefunden für '"+ sSection + "'");
				}	
			}//if(!StringZZZ.isEmpty(sSection)){			
		}//end main;
		return bReturn;
	}
	
	private boolean KernelSetParameterByProgramAlias_SystemLookup_(String sDebugKey, HashMapMultiIndexedZZZ hmDebug, String sSection, String sProperty, String sValue, boolean bSetNew, boolean bFlagDelete, boolean bFlagSaveImmidiate, FileIniZZZ objFileIniConfig) throws ExceptionZZZ{
		boolean  bReturn = false;
		main:{
						
			if(!StringZZZ.isEmpty(sSection)){
//				CounterHandlerSingleton_AlphanumericSignificantZZZ objHandler = CounterHandlerSingleton_AlphanumericSignificantZZZ.getInstance();
//				ICounterAlphanumericSignificantZZZ objCounter = objHandler.getCounterFor();
				
//				CounterHandlerSingleton_AlphabetZZZ objHandler = CounterHandlerSingleton_AlphabetZZZ.getInstance();
//				ICounterAlphabetZZZ objCounter = objHandler.getCounterFor();
				
				CounterHandlerSingleton_AlphabetSignificantZZZ objHandler = CounterHandlerSingleton_AlphabetSignificantZZZ.getInstance();
				ICounterAlphabetSignificantZZZ objCounter = objHandler.getCounterFor();
				String sSearchCounter = objCounter.getStringNext();//.getString();
				
				//a) mit Systemkey								
				String sSectionUsed = this.getSystemKey() + "!" + sSection;		
				hmDebug.put(sDebugKey + "." + sSectionUsed, sProperty);									
				bReturn = KernelSetParameterByProgramAlias_DirectLookup_(sSectionUsed, sProperty, sValue, bSetNew, bFlagDelete, bFlagSaveImmidiate, objFileIniConfig);
				if(bReturn) break main;
									
				//b) mit ApplicationKey				
				sSectionUsed = this.getApplicationKey() + "!" + sSection;				
				hmDebug.put(sDebugKey + "." + sSectionUsed, sProperty);						
				bReturn = KernelSetParameterByProgramAlias_DirectLookup_(sSectionUsed, sProperty, sValue, bSetNew, bFlagDelete, bFlagSaveImmidiate, objFileIniConfig);
				if(bReturn) break main;	

				}//if(!StringZZZ.isEmpty(sSection)){									
		}//End main:
		return bReturn;
	}
	
	private boolean KernelSetParameterByProgramAlias_AliasSystemLookup_
	(String sDebugKey, HashMapMultiIndexedZZZ hmDebug, String sProgramOrSection, String sProperty, String sValue, boolean bSetNew, boolean bFlagDelete, boolean bFlagSaveImmidiate, FileIniZZZ objFileIniConfig) throws ExceptionZZZ{
		boolean  bReturn = false;
		main:{
		if(!StringZZZ.isEmpty(sProgramOrSection)){	
//			CounterHandlerSingleton_AlphanumericSignificantZZZ objHandler = CounterHandlerSingleton_AlphanumericSignificantZZZ.getInstance();
//			ICounterAlphanumericSignificantZZZ objCounter = objHandler.getCounterFor();
			
//			CounterHandlerSingleton_AlphabetZZZ objHandler = CounterHandlerSingleton_AlphabetZZZ.getInstance();
//			ICounterAlphabetZZZ objCounter = objHandler.getCounterFor();
			
			CounterHandlerSingleton_AlphabetSignificantZZZ objHandler = CounterHandlerSingleton_AlphabetSignificantZZZ.getInstance();
			ICounterAlphabetSignificantZZZ objCounter = objHandler.getCounterFor();
			String sSearchCounter = objCounter.getStringNext();
			
			//a) mit Systemkey
			String sDebugKeyUsed = sDebugKey + ".a";
			System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0)+ ": " + sDebugKeyUsed + " Suche nach einem Alias in '"+ this.getSystemKey() + "' für das Programm oder die Section '" + sProgramOrSection + "'");			
			String sSectionUsed = objFileIniConfig.getPropertyValue(this.getSystemKey(), sProgramOrSection).getValue();	
			hmDebug.put(sDebugKey + "(" + sSearchCounter + ") " + sSectionUsed, sProperty);
			if(!StringZZZ.isEmpty(sSectionUsed)){
				bReturn = KernelSetParameterByProgramAlias_DirectLookup_(sSectionUsed, sProperty, sValue, bSetNew, bFlagDelete, bFlagSaveImmidiate, objFileIniConfig);
				if(bReturn) break main;		
			}//if(!StringZZZ.isEmpty(sSection)){
				
				
			//b) mit Applicationkey			
			sSectionUsed = objFileIniConfig.getPropertyValue(this.getApplicationKey(), sProgramOrSection).getValue();
			hmDebug.put(sDebugKey + "(" + sSearchCounter + ") " + sSectionUsed, sProperty);
			if(!StringZZZ.isEmpty(sSectionUsed)){
				bReturn = KernelSetParameterByProgramAlias_DirectLookup_(sSectionUsed, sProperty, sValue, bSetNew, bFlagDelete, bFlagSaveImmidiate, objFileIniConfig);
				if(bReturn) break main;				
			}//if(!StringZZZ.isEmpty(sSection)){

			}//if(!StringZZZ.isEmpty(sProgramOrSection)){
		}//end main;
		return bReturn;
	}
	
	private boolean KernelSetParameterByProgramAlias_(FileIniZZZ objFileIniConfigIn, String sMainSection, String sProgramOrSection, String sProperty, String sValueIn, boolean bFlagSaveImmidiate) throws ExceptionZZZ{
		boolean bReturn = false;
		HashMapMultiIndexedZZZ hmDebug = new HashMapMultiIndexedZZZ();//Speichere hier die Suchwerte ab, um sie später zu Debug-/Analysezwecken auszugeben.
		String sDebug; IKernelConfigSectionEntryZZZ objReturn = new KernelConfigSectionEntryZZZ(); //Wird ggfs. verwendet um zu sehen welcher Wert definiert ist. Diesen dann mit dem neuen Wert überschreiben.
		main:{	
			//TODO 20190815: CACHE VERWENDUNG AUCH BEIM SETZEN EINBAUEN
			//
			//			
			//#######################################################################			
			//TODO 20190711: COUNTER AUCH BEIM SETZEN EINBAUEN: sSearchCounter = objCounter.getStringNext();
			//                                                  sDebugKey = "(00)"+ sSearchCounter;
			//###################################################################################################			
		    //IDEE: Mache eine Factory, über die dann ein 'AsciiCounterZweistellig' Objekt erstellt werden kann
			//      Dementsprechend in solch einem Objekt den Startwert speichern ung ggfs. per Konstruktor erstellen.
			//      Dann die objAsciiCounterZweistellig.next() bzw. die objAsciiCounterZweistellig.increase() Methode 
			//      letztere zum Endgültigen setzen und erhöhen des Werts anbieten.
			
			//Hier einen SingletonCounter holen!!!
			//CounterHandlerSingleton_AlphanumericSignificantZZZ objHandler = CounterHandlerSingleton_AlphanumericSignificantZZZ.getInstance();
			//String sStartValue= "A0";
			//ICounterStrategyAlphanumericSignificantZZZ objCounterStrategy = new CounterStrategyAlphanumericSignificantZZZ(4,"0", sStartValue);	
			//objHandler.setCounterStrategy(objCounterStrategy);
			//ICounterAlphanumericSignificantZZZ objCounter = objHandler.getCounterFor();

			String sStartValue= "A";
//			CounterHandlerSingleton_AlphabetZZZ objHandler = CounterHandlerSingleton_AlphabetZZZ.getInstance();
//			ICounterStrategyAlphabetZZZ objCounterStrategy = new CounterStrategyAlphabetSerialZZZ(sStartValue);
//			objHandler.setCounterStrategy(objCounterStrategy);
//			ICounterAlphabetZZZ objCounter = objHandler.getCounterFor();
			
			CounterHandlerSingleton_AlphabetSignificantZZZ objHandler = CounterHandlerSingleton_AlphabetSignificantZZZ.getInstance();
			ICounterStrategyAlphabetSignificantZZZ objCounterStrategy = new CounterStrategyAlphabetSignificantZZZ(4,"0",sStartValue);
			objHandler.setCounterStrategy(objCounterStrategy);
			ICounterAlphabetSignificantZZZ objCounter = objHandler.getCounterFor();
			String sSearchCounter = objCounter.getStringNext();
			String sDebugKey=null;
			//#################################################################################################
			//1. Konfigurationsfile des Systems holen
			FileIniZZZ objFileIniConfig = null;
		    if(objFileIniConfigIn==null){
		    	objFileIniConfig = this.KernelSearchConfigFileByProgramAlias_(sMainSection, sProgramOrSection);		    	
		    }else{
		    	String stemp = "Verwende übergebenes FileIniZZZ.";
	    		System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": "+ stemp);
		    	objFileIniConfig = objFileIniConfigIn;
		    }
		   
		    //2. Den Abschnitt holen
			String sMainSectionUsed = this.KernelChooseMainSectionUsedForConfigFile_(sMainSection, sProgramOrSection);
			String sSectionUsed = null;	
			
			//3. Setzen des Wertes
			String sValue=new String("");
			boolean bFlagDelete = false;
			if(sValueIn==null){
				bFlagDelete=true;
			}else{
				sValue=sValueIn;
			}			
		
		//+++ AUSSERHALB DER SCHLEIFE: AlIASWERTE HOLEN
		String sSystemNumber= this.getSystemNumber();
		ArrayListExtendedZZZ<String>listasAlias = this.getProgramAliasUsed(objFileIniConfig,sMainSectionUsed, sProgramOrSection, sSystemNumber);
							
		//! ZWEI DURCHLÄUFE: 
		//Im ersten Durchlauf, prüfen, ob der Wert an der Stelle existiert.
		//Im zweiten Durchlauf, an der erstbesten Stelle setzen.		
		boolean bSetNew = false;		
		for(int icount = 0; icount <= 1; icount++){
			if(icount==1){
				System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0)+ ": Zweiter Durchlauf. Ersetze jetzt keinen bestehenden Wert, sondern erlaube neuen Eintrag an der ersten passenden Stelle.");
				bSetNew=true;
			}
			
			
			//### B. SUCHE WERTE ####################################################################		
			//#######################################################################					
			//+++ Ggfs. direkt als Program deklarierter Wert		
			if(!StringZZZ.isEmpty(sProgramOrSection)){
				if(sProgramOrSection!=this.getSystemKey() && sProgramOrSection!=this.getApplicationKey()){ //Damit keine doppelte Abfrage gemacht wird.
					sSectionUsed=sProgramOrSection;
					sDebugKey = "(00)";
					hmDebug.put(sDebugKey + "(" + sSearchCounter + ") " + sSectionUsed, sProperty);					
					bReturn = KernelSetParameterByProgramAlias_DirectLookup_(sSectionUsed, sProperty, sValue, bSetNew, bFlagDelete, bFlagSaveImmidiate, objFileIniConfig);
					if(bReturn)break main;
				}
			}
			
			//+++ B1. Ggfs. ohne Program deklarierter Wert
			if(!StringZZZ.isEmpty(sMainSection)){			
				if(sMainSection!=this.getSystemKey() && sMainSection!=this.getApplicationKey()){ //Damit keine doppelte Abfrage gemacht wird.
					//1. Schritt mit Systemnumber
					sSectionUsed=sMainSection+"!"+this.getSystemNumber();	//Immer zuerst die ggfs. überschreibende Variante
					sDebugKey = "(01)";
					hmDebug.put(sDebugKey + "(" + sSearchCounter + ")." + sSectionUsed, sProperty);
					bReturn = KernelSetParameterByProgramAlias_DirectLookup_(sSectionUsed, sProperty, sValue, bSetNew, bFlagDelete, bFlagSaveImmidiate, objFileIniConfig);
					if(bReturn)break main;
					
					//2. Schritt die Variante ohne Systemnumber
					sSectionUsed=sMainSection;
					sDebugKey = "(02)";
					hmDebug.put(sDebugKey + "(" + sSearchCounter + ") " + sSectionUsed, sProperty);
					bReturn = KernelSetParameterByProgramAlias_DirectLookup_(sSectionUsed, sProperty, sValue, bSetNew, bFlagDelete, bFlagSaveImmidiate, objFileIniConfig);
					if(bReturn)break main;
				}//if(sMainSection!=this.getSystemKey() && sMainSection!=this.getApplicationKey()){						
			}//if(!StringZZZ.isEmpty(sMainSection)){	
			
			//B2.  Suche Werte ohne Programm, nur nach Systemkey
			if(sMainSection!=this.getSystemKey()){  //doppelte Abfragen vermeiden
				sSectionUsed = this.getSystemKey(); 
				sDebugKey = "(03)";
				hmDebug.put(sDebugKey + "(" + sSearchCounter + ") " + sSectionUsed, sProperty);
				bReturn = KernelSetParameterByProgramAlias_DirectLookup_(sSectionUsed, sProperty, sValue, bSetNew, bFlagDelete, bFlagSaveImmidiate, objFileIniConfig);
				if(bReturn)break main;			
			}//if(sMainSection!=this.getSystemKey()){
			
			//B3.  Suche Werte ohne Programm, nur nach Applicationkey
			if(sSectionUsed!=this.getApplicationKey()){  //doppelte Abfragen vermeiden
				sSectionUsed = this.getApplicationKey(); //als 2. Schritt dann den Applicationkey
				sDebugKey = "(04)";
				hmDebug.put(sDebugKey + "(" + sSearchCounter + ") " + sSectionUsed, sProperty);
				bReturn = KernelSetParameterByProgramAlias_DirectLookup_(sSectionUsed, sProperty, sValue, bSetNew, bFlagDelete, bFlagSaveImmidiate, objFileIniConfig);
				if(bReturn)break main;
			}//if(sSection!=this.getApplicationKey()){ 
			
			
			//###############################################################################
			// Als Program definierte Werte (Merke: Die direkt definierten Werte - d.h. ohne Application/Systemkey - wurden schon eher abgefragt)
			//###############################################################################
			//3a. Für den Fall, dass der Programname direkt angegeben wurde. Suche ihn im System-/Applicationkey
			sSectionUsed = sProgramOrSection;
			sDebugKey = "(05)";
			//wird übergeben und daher nicht hier gefüllt hmDebug.put(sDebugKey + "(" + sSearchCounter + ")." + sSectionUsed, sProperty);
			bReturn = KernelSetParameterByProgramAlias_SystemLookup_(sDebugKey, hmDebug, sSectionUsed, sProperty, sValue, bSetNew, bFlagDelete, bFlagSaveImmidiate, objFileIniConfig);
			if(bReturn)break main;
		
			//+++ Die Section als Program mit Alias und vorangestelltem Systemkey definiert:
			Iterator<String> itAlias = listasAlias.iterator();
			while(itAlias.hasNext()){
				String sSectionTemp = itAlias.next(); //der verwendete Programalias zur Suche nach der  Section
				
				//Für den Fall, dass die Section des Alias mit dem Systemkey voran definiert wurde
				sSectionUsed = this.getSystemKey() + sSectionTemp; 
				sDebugKey = "(06.a)";							
				hmDebug.put(sDebugKey + "(" + sSearchCounter + ") " + sSectionUsed, sProperty);
				bReturn = KernelSetParameterByProgramAlias_DirectLookup_(sSectionUsed, sProperty, sValue, bSetNew, bFlagDelete, bFlagSaveImmidiate, objFileIniConfig);
				if(bReturn)break main;
				
				//Für den Fall, dass die Section des Alias mit dem Applicationkey voran definiert wurde
				sSectionUsed = this.getApplicationKey() + sSectionTemp;
				sDebugKey = "(06.b)";							
				hmDebug.put(sDebugKey + "(" + sSearchCounter + ") " + sSectionUsed, sProperty);
				bReturn = KernelSetParameterByProgramAlias_DirectLookup_(sSectionUsed, sProperty, sValue, bSetNew, bFlagDelete, bFlagSaveImmidiate, objFileIniConfig);
				if(bReturn)break main;
				
				//Nur den Alias 
				sSectionUsed = sSectionTemp;
				sDebugKey = "(06.c)";							
				hmDebug.put(sDebugKey + "(" + sSearchCounter + ") " + sSectionUsed, sProperty);
				bReturn = KernelSetParameterByProgramAlias_DirectLookup_(sSectionUsed, sProperty, sValue, bSetNew, bFlagDelete, bFlagSaveImmidiate, objFileIniConfig);
				if(bReturn)break main;
				
			}//end while
			
		
		 //##################################################################################		
		//+++ Die Section als einen ggfs. auf Systemkey definierten Aliasnamen vorhanden. Aber: Die Section hat keine erweiterung mit ! und irgendeiner Systemnumber.
		if(!StringZZZ.isEmpty(sProgramOrSection)){
			sSectionUsed =  sProgramOrSection;	
			sDebugKey = "(07)";
			//wird übergeben und daher nicht hier gefüllt hmDebug.put(sDebugKey + "(" + sSearchCounter + ")." + sSectionUsed, sProperty);
			bReturn = KernelSetParameterByProgramAlias_AliasSystemLookup_(sDebugKey, hmDebug, sSectionUsed, sProperty, sValue, bSetNew, bFlagDelete, bFlagSaveImmidiate, objFileIniConfig);
			if(bReturn)break main;						
		}//if(!StringZZZ.isEmpty(sProgramOrSection)){
	
		//+++ Einfach als SystemKey
		sSectionUsed = this.getSystemKey();	
		if(!StringZZZ.isEmpty(sSectionUsed)){
			//System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0)+ ": (k) Verwende als sSection '"+ sSection + "' für die Suche nach der Property '" + sProperty + "'");
			sDebugKey = "(08)";
			hmDebug.put(sDebugKey + "." + sSectionUsed, sProperty);
			boolean bSectionExists = objFileIniConfig.proofSectionExists(sSectionUsed);
			if(bSectionExists==true){
				if(!bSetNew || bFlagDelete){
					boolean bValueExists = objFileIniConfig.proofValueExists(sSectionUsed, sProperty);
					if(bValueExists){
						if(bFlagDelete==false){					
							bReturn = objFileIniConfig.setPropertyValue(sSectionUsed, sProperty, sValue, bFlagSaveImmidiate);
							if (bReturn) {
								System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0)+ ": Value erfolgreich  (ggfs. konvertiert) gesetzt für Property '" + sProperty + "'=''" + sValue + "'");
								break main;
							}	
						}else{
							bReturn =  objFileIniConfig.deleteProperty(sSectionUsed, sProperty, bFlagSaveImmidiate);
							if (bReturn) {
								System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0)+ ": Value erfolgreich gelöscht für Property '" + sProperty + "'=''" + sValue + "'");
								break main;
							}
						}
					}
				}else{
					//Setzen, im 2. Durchlauf
					bReturn = objFileIniConfig.setPropertyValue(sSectionUsed, sProperty, sValue, bFlagSaveImmidiate);
					if (bReturn) {
						System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0)+ ": Value erfolgreich  (ggfs. konvertiert) gesetzt für Property '" + sProperty + "'=''" + sValue + "'");
						break main;
					}						
				}
			}else{
				System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0)+ ": (k.a) Keine Section gefunden für '"+ sSectionUsed  + "'");
			}
		}//if(!StringZZZ.isEmpty(sSection)){
			
								
		//+++ Einfach nur ApplicationKey
		sSectionUsed = this.getApplicationKey();		
		if(!StringZZZ.isEmpty(sSectionUsed)){	
			//System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0)+ ": (l) Verwende als sSection '"+ sSection + "' für die Suche nach der Property '" + sProperty + "'");
			sDebugKey = "(09)";
			hmDebug.put(sDebugKey + "." + sSectionUsed, sProperty);
			boolean bSectionExists = objFileIniConfig.proofSectionExists(sSectionUsed);
			if(bSectionExists==true){
				if(!bSetNew || bFlagDelete){
					boolean bValueExists = objFileIniConfig.proofValueExists(sSectionUsed, sProperty);
					if(bValueExists){
						if(bFlagDelete==false){					
							bReturn = objFileIniConfig.setPropertyValue(sSectionUsed, sProperty, sValue, bFlagSaveImmidiate);
							if (bReturn) {
								System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0)+ ": Value erfolgreich  (ggfs. konvertiert) gesetzt für Property '" + sProperty + "'=''" + sValue + "'");
								break main;
							}	
						}else{
							bReturn =  objFileIniConfig.deleteProperty(sSectionUsed, sProperty, bFlagSaveImmidiate);
							if (bReturn) {
								System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0)+ ": Value erfolgreich gelöscht für Property '" + sProperty + "'=''" + sValue + "'");
								break main;
							}
						}		
					}
				}else{
					//Setzen, im 2. Durchlauf
					bReturn = objFileIniConfig.setPropertyValue(sSectionUsed, sProperty, sValue, bFlagSaveImmidiate);
					if (bReturn) {
						System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0)+ ": Value erfolgreich  (ggfs. konvertiert) gesetzt für Property '" + sProperty + "'=''" + sValue + "'");
						break main;
					}						
				}
			}else{
				System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0)+ ": (l.a) Keine Section gefunden für '"+ sSectionUsed  + "'");
			}
		}//if(!StringZZZ.isEmpty(sSection)){
		}//end for icount
		
		if(bFlagDelete){	
			//Falls der Wert nicht gefunden wurde, hier bReturn = false, aber keine Exception
			System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0)+ ": Zu löschende Property nicht gefunden. Ende, keine Exception.");
			bReturn = false;
			break  main;
		}
		
		
//#####################################################################		
			//Falls der Parameter immer noch nicht gefunden wurde, hier eine Exception auswerfen.
	        //Ansonsten droht die Gefahr einer Endlosschleife.
				String sModuleUsed="";
				boolean bModuleConfig = this.proofModuleFileIsConfigured(sMainSection);
				if(bModuleConfig==false){
																		
					//A2. Versuch: Prüfen, ob es als Systemkey konfiguriert ist
					sModuleUsed = this.getSystemKey();
					bModuleConfig = this.proofModuleFileIsConfigured(sModuleUsed);
					if(bModuleConfig==false){
						
						//A3. Versuch: Prüfen, ob es als Applikationskey konfiguriert ist
						sModuleUsed = this.getApplicationKey();
						bModuleConfig = this.proofModuleFileIsConfigured(sModuleUsed);
						if(bModuleConfig==false){
							String stemp = "Wrong parameter: Module '" + sModuleUsed + "' is not configured or property could not be found anywhere in the file in the file '" + objFileIniConfig.getFileObject().getAbsolutePath() + "' for the property: '" + sProperty + "'.";
							System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": "+ stemp);
							ExceptionZZZ ez = new ExceptionZZZ(stemp,iERROR_PARAMETER_VALUE, this,  ReflectCodeZZZ.getMethodCurrentName());
							throw ez;
						} //end if Versuch 3
					} //end if Versuch 2
				}//end if Versuch 1
				
				//B1. Prüfen, ob das Modul existiert
				boolean bModuleExists = this.proofModuleFileExists(sMainSection);
				if(bModuleExists==false){
					String stemp = "Wrong parameter: Module '" + sModuleUsed + "' does not exist or property could not be found anywhere in the file in the file '" + objFileIniConfig.getFileObject().getAbsolutePath() + "' for the property: '" + sProperty + "'.";
					System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": "+ stemp);
					ExceptionZZZ ez = new ExceptionZZZ(stemp,iERROR_PARAMETER_VALUE, this,  ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}

				//Abbruch der Parametersuche. Ohne diesen else-Zweig, gibt es ggfs. eine Endlosschleife.
				String stemp = "Parameter nicht in der ini-Datei definiert (Modul/Program or Section) '(" + sMainSection + "/" + sProgramOrSection + ")  in the file '" + objFileIniConfig.getFileObject().getAbsolutePath() + "' for the property: '" + sProperty + "'.";
				System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": "+ stemp);
				ExceptionZZZ ez = new ExceptionZZZ(stemp, iERROR_CONFIGURATION_VALUE, this,  ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
		}//END main:
		sDebug = hmDebug.debugString(":"," | ");
		if(bReturn) {
			System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0) + ": ERFOLGREICHES ENDE DIESER SUCHE +++ Suchreihenfolge (Section:Property): " + sDebug); 
		}else{
			System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0) + ": ENDE DIESER SUCHE OHNE ERFOLG +++ Suchreihenfolge (Section:Property): " + sDebug);
		}			
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
	public IKernelConfigSectionEntryZZZ getParameterByProgramAlias(String sModuleAndProgramAndSection, String sProperty) throws ExceptionZZZ{
		IKernelConfigSectionEntryZZZ objReturn = new KernelConfigSectionEntryZZZ(); //Hier schon die Rückgabe vorbereiten, falls eine weitere Verarbeitung nicht konfiguriert ist.
		main:{
			check:{
				if(StringZZZ.isEmpty(sModuleAndProgramAndSection)){
					ExceptionZZZ ez = new ExceptionZZZ("Missing 'String Module/Program/Section'",iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}			
			}//END check:
		
			//FGL TODO GOON 20180909: Müsste nicht eigentlich der ApplicationKey als ModulAlias verwendet werden?		
			//sReturn = this.getParameterByProgramAlias(sModuleAndProgramAndSection, sModuleAndProgramAndSection, sProperty); 
			objReturn = this.getParameterByProgramAlias(this.getApplicationKey(), sModuleAndProgramAndSection, sProperty);
		}//END main:
		return objReturn;
	}
	
	
	//############################################################################
	public IKernelConfigSectionEntryZZZ[] getParameterByProgramAliasAsArray(String sModule, String sProgramOrSection, String sProperty) throws ExceptionZZZ{
		IKernelConfigSectionEntryZZZ[] objaReturn = null; //new KernelConfigSectionEntryZZZ(); //Hier schon die Rückgabe vorbereiten, falls eine weitere Verarbeitung nicht konfiguriert ist.		
		String sDebug = "";
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
				  
			objaReturn = this.KernelGetParameterByProgramAliasAsArray_(null, sModule, sProgramOrSection, sProperty);
		}//END main:
		return objaReturn;
	}
	
	
	private IKernelConfigSectionEntryZZZ[] KernelGetParameterByProgramAliasAsArray_(FileIniZZZ objFileIniConfigIn, String sMainSection, String sProgramOrSection, String sProperty) throws ExceptionZZZ{
		IKernelConfigSectionEntryZZZ[] objaReturn = null;
		ArrayList<IKernelConfigSectionEntryZZZ>listaReturn= new ArrayList<IKernelConfigSectionEntryZZZ>();				
		HashMapMultiIndexedZZZ hmDebug = new HashMapMultiIndexedZZZ();//Speichere hier die Suchwerte ab, um sie später zu Debug-/Analysezwecken auszugeben.
		String sDebug;
		
		main:{				
		//Merke: Die Eingabewerte werden ggfs. als Schlüssel für den Cache verwendet.
		//       Sowohl für die Suche im Cache, als auch für das Speichern im Cache. Dadurch wird Performance erreicht.
		//Aber: Es wird im Cache kein Array gespeichert, sondern der zu zerlegende String !!!
			//  Darum findet hier nicht das Nachsehen im Cache statt. 
					
		//############################
		hmDebug.put("+++ ArrayMethod calling StringMethod for search. Input: " + sMainSection + "," + sProgramOrSection ,sProperty);
		
		IKernelConfigSectionEntryZZZ objReturn = null;
		objReturn = this.KernelGetParameterByProgramAlias_(objFileIniConfigIn, sMainSection, sProgramOrSection, sProperty);
		
		sDebug = hmDebug.debugString(":"," | ");
		if(objReturn.hasAnyValue()) {										
			System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0) + "+++ ArrayMethod calling StringMethod for search. Zerlege gefundenen Wert nun in Array" + sDebug);			
			try {
				objaReturn = KernelConfigSectionEntryZZZ.explode(objReturn, null);
			} catch (CloneNotSupportedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
				
				//################
				//DEBUG: Entsprechen die oben eingegebenen Suchstrings auch dem 1. Suchschritt?
				/*
				Set<Integer> setKeyOuter = hmDebug.keySet();
				Integer[] objaKeyOuter = (Integer[]) setKeyOuter.toArray(new Integer[setKeyOuter.size()]);
				Integer obj = objaKeyOuter[0];
				
				//Nun diesen Wert in den Cache übernehmen.
				HashMap<String,String>hmInner = hmDebug.getInnerHashMap(obj);//Das war der 1. Versuch. Er sollte die Eingabewerte beinhalten. Diese sollen die Keys für den Cache sein. Dann wird der Wert beim 2. Aufruf sofort gefunden.
				Set<String> setKey = hmInner.keySet();
				String[] saKey = (String[]) setKey.toArray(new String[setKey.size()]);				
				String sKey = saKey[0];					
				String sValue = hmInner.get(sKey);
				*/
				//###################
				
				
			}else{
				System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0) + "+++ ArrayMethod calling StringMethod for search. Kein Wert zum Zerlegen gefunden." + sDebug);
			}			
		}//END main:
		return objaReturn;
	}
	
	
	
	//################################################################################################
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
	
	/**Returns an array-object of a configured ini value. 
	 * Because it is used very often, this comfortabel method is available.
	 * Remark: If the Separator is not provided a default one will be used.
	 *  
	 * @param sModule
	 * @param sProperty
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 07.04.2020, 08:06:58
	 */
	public String[] getParameterArrayStringByModuleAlias(String sModule, String sProperty) throws ExceptionZZZ{
		String[] saReturn = null;
		main:{
			//String sFileName = this.getParameterByModuleAlias(sModule, sProperty).getValue();			
			IKernelConfigSectionEntryZZZ[] objaEntry = this.getParameterByModuleAliasAsArray(sModule, sProperty);
			if(objaEntry!=null){
				
				//Hole getValue aus jedem entry und packe es in eine ArrayList, die dann als StringArray zurückgegeben wird.
				ArrayList<String> listasEntry = new ArrayList<String>();
				for(int iCounter=0; iCounter <= objaEntry.length-1;iCounter++) {
					IKernelConfigSectionEntryZZZ objEntry = objaEntry[iCounter];
					String sEntry = objEntry.getValue();					
					listasEntry.add(sEntry);
				}
				saReturn = ArrayListZZZ.toStringArray(listasEntry);			
			}else{
				ExceptionZZZ ez = new ExceptionZZZ("No parameter configured '" + sProperty + "'", iERROR_CONFIGURATION_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());				
				throw ez;
			}
		}
		return saReturn;
	}
	
	/**Returns an array-object on a configured value. 
	 * Because it is used very often, this comfortabel method is available.
	 * Remark: If the Separator is not provided a default one will be used.
	 *  
	 * @param sModule
	 * @param sSectionOrProgram
	 * @param sProperty
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 07.04.2020, 08:10:43
	 */
	public String[] getParameterArrayStringByProgramAlias(String sModule, String sSectionOrProgram, String sProperty) throws ExceptionZZZ{
		String[] saReturn = null;
		main:{
			//String sFileName = this.getParameterByProgramAlias(sModule, sSectionOrProgram, sProperty).getValue();
			IKernelConfigSectionEntryZZZ[] objaEntry = this.getParameterByProgramAliasAsArray(sModule, sSectionOrProgram, sProperty);
			if(objaEntry!=null){
				
				//Hole getValue aus jedem entry und packe es in eine ArrayList, die dann als StringArray zurückgegeben wird.
				ArrayList<String> listasEntry = new ArrayList<String>();
				for(int iCounter=0; iCounter <= objaEntry.length-1;iCounter++) {
					IKernelConfigSectionEntryZZZ objEntry = objaEntry[iCounter];
					String sEntry = objEntry.getValue();					
					listasEntry.add(sEntry);
				}
				saReturn = ArrayListZZZ.toStringArray(listasEntry);			
			}else{
				ExceptionZZZ ez = new ExceptionZZZ("No parameter configured '" + sProperty + "'", iERROR_CONFIGURATION_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());				
				throw ez;
			}
		}
		return saReturn;
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
	public File getParameterFileByModuleAlias(String sModule, String sProperty) throws ExceptionZZZ{
		File fileReturn = null;
		main:{
			String sFileName = this.getParameterByModuleAlias(sModule, sProperty).getValue();
			if(!StringZZZ.isEmpty(sFileName)){
				
				//20190220: Da die Datei im Classpath vermutet wird, ist der absolute Pfad ein anderer. Diesen suchen.
				fileReturn = FileEasyZZZ.searchFile(sFileName);
								
			}else{
				ExceptionZZZ ez = new ExceptionZZZ("No parameter configured '" + sProperty + "'", iERROR_CONFIGURATION_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());				
				throw ez;
			}
		}
		return fileReturn;
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
			String sFileName = this.getParameterByProgramAlias(sModule, sSectionOrProgram, sProperty).getValue(); 
			if(!StringZZZ.isEmpty(sFileName)){
				
				//20190220: Da die Datei im Classpath vermutet wird, ist der absolute Pfad ein anderer. Diesen suchen.
				fileReturn = FileEasyZZZ.searchFile(sFileName);
							
			}else{
				ExceptionZZZ ez = new ExceptionZZZ("No parameter configured '" + sProperty + "'", iERROR_CONFIGURATION_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());				
				throw ez;
			}
		}
		return fileReturn;
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
	public ImageIcon getParameterImageIconByModuleAlias(String sModule, String sProperty) throws ExceptionZZZ{
		ImageIcon iconReturn = null;
		main:{
			File objFile = this.getParameterFileByModuleAlias(sModule, sProperty);
			String sFilePath=null;
			if(objFile!=null){
				sFilePath = objFile.getAbsolutePath();
				if(objFile.length()>0){					
					this.getLogObject().WriteLineDate("Absoluter Pfad für das ImageIcon: " + sFilePath);
					iconReturn = new ImageIcon(sFilePath);					
				}else{
					String sLog = "Parameter wrong configured. ImageSize = 0 '" + sProperty + "' (File may not exist: '" + sFilePath + "')";
					System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": " + sLog);
					this.getLogObject().WriteLineDate(sLog);
				}						
			}else{
				ExceptionZZZ ez = new ExceptionZZZ("No parameter configured '" + sProperty + "'", iERROR_CONFIGURATION_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());				
				throw ez;
			}
		}
		return iconReturn;
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
	public ImageIcon getParameterImageIconByProgramAlias(String sModule, String sSectionOrProgram, String sProperty) throws ExceptionZZZ{
		ImageIcon iconReturn = null;
		main:{
			File objFile = this.getParameterFileByProgramAlias(sModule, sSectionOrProgram, sProperty);
			String sFilePath=null;
			if(objFile!=null){
				sFilePath = objFile.getAbsolutePath();
				if(objFile.length()>0){					
					this.getLogObject().WriteLineDate("Absoluter Pfad für das ImageIcon: " + sFilePath);
					iconReturn = new ImageIcon(sFilePath);					
				}else{					
					String sLog = "Parameter wrong configured. ImageSize = 0 '" + sProperty + "' (File may not exist: '" + sFilePath + "')";
					System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": " + sLog);
					this.getLogObject().WriteLineDate(sLog);					
				}		
			}else{
				ExceptionZZZ ez = new ExceptionZZZ("No parameter configured '" + sProperty + "'", iERROR_CONFIGURATION_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());				
				throw ez;
			}
		}
		return iconReturn;
	}
	
	/**
	 * @param objFileIniConfig
	 * @param sMainSection
	 * @param sProgramOrAlias
	 * @param sSystemNumber
	 * @return
	 * @throws ExceptionZZZ
	 */
	public ArrayListExtendedZZZ<String> getProgramAliasUsed(FileIniZZZ objFileIniConfig, String sMainSection, String sProgramOrAlias, String sSystemNumber) throws ExceptionZZZ{
		ArrayListExtendedZZZ<String> listasReturn = new ArrayListExtendedZZZ<String>();
		HashMapMultiIndexedZZZ hmDebug = new HashMapMultiIndexedZZZ(); //Für die durchgeführten Suchen. Statt so vieler system.out Anweisungen.
		//CounterHandlerSingleton_AlphanumericSignificantZZZ objHandler = CounterHandlerSingleton_AlphanumericSignificantZZZ.getInstance();
		//CounterHandlerSingleton_AlphabetZZZ objHandler = CounterHandlerSingleton_AlphabetZZZ.getInstance();
		CounterHandlerSingleton_AlphabetSignificantZZZ objHandler = CounterHandlerSingleton_AlphabetSignificantZZZ.getInstance();
		ICounterAlphabetSignificantZZZ objCounter = objHandler.getCounterFor();
		String sSearchCounter = objCounter.getStringNext(); // objCounter.current();
		main:{
			if(StringZZZ.isEmpty(sMainSection)){
				ExceptionZZZ ez = new ExceptionZZZ("Module/Section",iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			if(StringZZZ.isEmpty(sProgramOrAlias)){
				ExceptionZZZ ez = new ExceptionZZZ("Program",iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
				
			IKernelConfigSectionEntryZZZ objEntry = null;
			String sProgramAliasUsed=null; boolean bSectionExists= false;
			String sSectionUsed=null; String sPropertyUsed=null;String sDebugKey = null;
			
			String sProgramNameUsed=sProgramOrAlias;					
			if(!StringZZZ.isEmpty(sProgramNameUsed)){
				//Suche nach einer überschreibenden Version mit Systemkey				
				sSectionUsed = sMainSection+"!"+sSystemNumber;
				sPropertyUsed = sProgramNameUsed;
				//System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0)+ ": Programaliasermittlung (a.a) - Verwende als sSection '"+ sSection + "' für die Suche nach dem Programm als der Property '" + sProgramNameUsed + "'");				
				bSectionExists = objFileIniConfig.proofSectionExists(sSectionUsed);
				if(bSectionExists==true){
					//System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0)+ ": Programaliasermittlung (a.a) - Section als Grundlage gefunden '" + sSection + "'.");
					//Nun in der Section nach dem Wert für das Programm suchen.
					objEntry = objFileIniConfig.getPropertyValue(sSectionUsed, sPropertyUsed); 
					if(objEntry.hasAnyValue()){
						sProgramAliasUsed = objEntry.getValue();
						//System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0)+ ": Programaliasermittlung (a.a) - in der Section als Grundlage '" + sSection + "' Wert für '" + sProgramNameUsed + "' gefunden = '" + sProgramAliasUsed +"'.");
						listasReturn.add(sProgramAliasUsed+"!"+sSystemNumber);//Den gefundenen Programalias zuerst um die SystemNumber erweitern.					
						listasReturn.add(sProgramAliasUsed);
						sDebugKey = "(a.a)";
						hmDebug.put(sDebugKey + "(" + sSearchCounter + ")." + sSectionUsed, sPropertyUsed);
					}else{
						//System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0)+ ": Programaliasermittlung (a.a) - in der Section als Grundlage '" + sSection + "' KEINEN Wert für '" + sProgramNameUsed + "' gefunden, der Alias sein könnte.");
					}
				}else{
					//System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0)+ ": Programaliasermittlung (a.a) - Keine Section als Grundlage gefunden '" + sSection + "'.");
				}
				
				//Suche nach der Version ohne SectionKey
			   sSectionUsed = sMainSection;
			   //System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0)+ ": Programaliasermittlung (b.b) - Verwende als sSection '"+ sSection + "' für die Suche nach dem Programm als der Property '" + sProgramNameUsed + "'");			  
			   bSectionExists = objFileIniConfig.proofSectionExists(sSectionUsed);
				if(bSectionExists==true){
					//System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0)+ ": Programaliasermittlung (b.b) - Section als Grundlage gefunden '" + sSection + "'.");

					//Nun in der Section nach dem Wert für das Programm suchen.
					objEntry = objFileIniConfig.getPropertyValue(sSectionUsed, sPropertyUsed); 
					if(objEntry.hasAnyValue()){
						sProgramAliasUsed = objEntry.getValue();					
						//System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0)+ ": Programaliasermittlung (b.b) - in der Section als Grundlage '" + sSection + "' Wert für '" + sProgramNameUsed + "' gefunden = '" + sProgramAliasUsed +"'.");
						listasReturn.add(sProgramAliasUsed+"!"+sSystemNumber);//Den gefundenen Programalias zuerst um die SystemNumber erweitern.					
						listasReturn.add(sProgramAliasUsed);
						sDebugKey = "(b.b)";
						 hmDebug.put(sDebugKey + "(" + sSearchCounter + ")." + sSectionUsed, sPropertyUsed);
					}else{
						//System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0)+ ": Programaliasermittlung (b.b) - in der Section als Grundlage '" + sSection + "' keinen Wert für '" + sProgramNameUsed + "' gefunden, der Alias sein könnte.");
					}																	
				}else{
					//System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0)+ ": Programaliasermittlung (b.b) - Keine Section als Grundlage gefunden '" + sSection + "'.");
				}
				
				//####################################################################################################
				//Programmaliasermittlung, verwende als Section den Systemkey (ApplicationKey + "!" + Systemnumber)						
				sSectionUsed = this.getSystemKey();
				//System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0)+ ": Programaliasermittlung (c.c) - Verwende als sSection '"+ sSection + "' für die Suche nach dem Programm als der Property '" + sProgramNameUsed + "'");				 				
				bSectionExists = objFileIniConfig.proofSectionExists(sSectionUsed);
				if(bSectionExists==true){
					//System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0)+ ": Programaliasermittlung (c.c) - Section als Grundlage gefunden '" + sSection + "'.");

					//Nun in der Section nach dem Wert für das Programm suchen.
					objEntry = objFileIniConfig.getPropertyValue(sSectionUsed, sPropertyUsed); 
					if(objEntry.hasAnyValue()){
						sProgramAliasUsed = objEntry.getValue();
						//System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0)+ ": Programaliasermittlung (c.c) - Value gefunden für Property '" + sProgramNameUsed + "'='" + sProgramAliasUsed + "'");
						
						//Den gefundenen Programalias zuerst um die SystemNumber erweitern.
						listasReturn.add(sProgramAliasUsed+"!"+sSystemNumber);
						listasReturn.add(sProgramAliasUsed);	
						sDebugKey = "(c.c)";
						hmDebug.put(sDebugKey + "(" + sSearchCounter + ")." + sSectionUsed, sPropertyUsed);
					}else{
						//System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0)+ ": Programaliasermittlung (c.c) - in der Section als Grundlage '" + sSection + "' keinen Wert für '" + sProgramNameUsed + "' gefunden, der Alias sein könnte.");
					}
				}else{
					//System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0)+ ": Programaliasermittlung (c.c) - Keine Section als Grundlage gefunden '" + sSection + "'.");
				}
					
				
				//###################################################################################################
				//Programaliasermittlung, verwende als Section den ApplicationKey selbst
				if(!StringZZZ.isEmpty(sProgramNameUsed)){						
					sSectionUsed = this.getApplicationKey();
					//System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0)+ ": Programaliasermittlung (d.d) - Verwende als sSection '"+ sSection + "' für die Suche nach dem Programm als der Property '" + sProgramNameUsed + "'");					
					bSectionExists = objFileIniConfig.proofSectionExists(sSectionUsed);
					if(bSectionExists==true){
						//System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0)+ ": Programaliasermittlung (d.d) - Section als Grundlage gefunden '" + sSection + "'.");
					
						//Nun in der Section nach dem Wert für das Programm suchen.
						objEntry = objFileIniConfig.getPropertyValue(sSectionUsed, sPropertyUsed); 
						if(objEntry.hasAnyValue()){
							sProgramAliasUsed = objEntry.getValue();
							//System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0)+ ": Programaliasermittlung (d.d) - Value gefunden für Property '" + sProgramNameUsed + "'='" + sProgramAliasUsed + "'");
							
							//Den gefundenen Programalias zuerst um die SystemNumber erweitern.
							listasReturn.add(sProgramAliasUsed+"!"+sSystemNumber);					
							listasReturn.add(sProgramAliasUsed);
							sDebugKey = "(d.d)";
							hmDebug.put(sDebugKey + "(" + sSearchCounter + ")." + sSectionUsed, sPropertyUsed);
						}else{
							//System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0)+ ": Programaliasermittlung (d.d) - in der Section als Grundlage '" + sSection + "' keinen Wert für '" + sProgramNameUsed + "' gefunden, der Alias sein könnte.");
						}
					}else{
						//System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0)+ ": Programaliasermittlung (d.d) - Keine Section als Grundlage gefunden '" + sSection + "'.");
					}
				}
			}//end if(!StringZZZ.isEmpty(sProgramNameUsed)){
		}//end main:
		
		
		if(listasReturn.size()>=1){
			String sDebug = hmDebug.debugString(":"," | ");
			System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0) + ": ERFOLGREICH PROGRAMALIASSE GEFUNDEN +++ Suchreihenfolge (Section:Property): " + sDebug);
		}else{
			System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0) + ": KEINE PROGRAMALIASSE GEFUNDEN");
		}
		return listasReturn;
	}
	
	/** String; Ausgehend vom Dateinamen werden die "überfl�ssigen" Namensteile abgeschnitten und der Modulalias bleibt bestehen 
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
				ExceptionZZZ ez = new ExceptionZZZ("SubjectIn", iERROR_PARAMETER_MISSING, KernelKernelZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
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
					ExceptionZZZ ez = new ExceptionZZZ("Wrong parameter for sProperty='" + sPropertyIn + "', but expected 'File' or 'Path'.'", iERROR_PROPERTY_VALUE, KernelKernelZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());				
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
				File objFileTemp=null; File[] objaFileTemp=null; String sDir=null; ArrayList listaConfigured=null; ArrayList listaExisting = null; ArrayList listaFile = null;  
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
						//######## Von allen gefundenen Moduldateien ausgehend diejenigen, deren Konfiguration fehlt
						//(!!! hierbei wird das Kernel-Konfigurations-Verzeichnis nach Moduldateien durchsucht !!!)
																	
						//+++ 1. Die konfigurierten Module holen
						//Aus der KernelKonfigurationsdatei alle Werte des aktuellen Systems holen. 
						saProperty = objFileIni.getPropertyAll(this.getSystemKey()); 
						
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
												
						//+++2. Hole alle Konfigurationsdateien im aktuellen Verzeichnis
						objFileTemp = this.getFileConfigKernel();
						sDir = objFileTemp.getParent();
						if(sDir==null) sDir = "";     //Das ist nun m�glich     if(sDir.equals("")) break main;
						
						//Alle Moduldateien dieses Verzeichnisses holen
						objaFileTemp = this.getFileConfigAllByDir(sDir);
						if(objaFileTemp==null) break main;
						if(objaFileTemp.length==0)break main;
						
						
						
						//+++3. Berechne aus den vorhandenen Konfigurationsdateien wie der Modulname sein sollte
						listaExisting = new ArrayList();
						if(saProperty!=null){
							//Von den vorhandenen Dateien das Modul ermitteln
							//for(int icount = 0; icount < saProperty.length; icount ++){
							for(int icount=0;icount < objaFileTemp.length; icount++){
								String sFilename = FileEasyZZZ.getNameOnly(objaFileTemp[icount].getAbsolutePath());
								String stemp = computeModuleAliasByFilename(sFilename);
								if(!stemp.equals("")) listaExisting.add(stemp);	
							}							
						}
						
						//+++ 4. Die Module aus allen Dateinamen herausfiltern. D.h. ggf. gibt es ja mehrere Dateien zu einem Modul.
						listaFile = new ArrayList();
												
						Iterator it = listaExisting.iterator();
						while(it.hasNext()){
							String sExisting = (String)it.next();
							if(!listaConfigured.contains(sExisting)){
								listaFile.add(sExisting);
							}
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
								listaReturn.add(listaFile.get(icount));  //Ein einfaches Entfernen an dieser Stelle ändert die Size der Liste und die Indexpositionen
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
	
	
	
	/**List aus der KernelKonfiguration alle Konfigurierten Modulenamen aus. Irgenwelche File - Eigenschaften (z.B. die Existenz) werden hier nicht berücksichtig
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
			
			String sSystemKey = this.getSystemKey(); //Also z.b. "MyApplication!01"
			String[] saVar = objFileIni.getVariables(sSystemKey);
			for(int icount = 0; icount <= saVar.length-1; icount ++){
				String sModule = KernelKernelZZZ.computeModuleAliasBySubject(saVar[icount]);
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
	 * @param sModule
	 * @return 
	 * @throws ExceptionZZZ 
	 */
	public boolean proofModuleFileIsConfigured(String sModule) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			check:{
				if(StringZZZ.isEmpty(sModule)){
					String stemp = "Missing parameter: 'Alias'";
					System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": "+ stemp);
					ExceptionZZZ ez = new ExceptionZZZ(stemp,iERROR_PARAMETER_MISSING,  this,  ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}							
			}//end check:
			
			//first, get the Kernel-Configuration-INI-File
			//TODO write ini-file-class for zzz-kernel
			IniFile objIni = this.getFileConfigKernelAsIni();
			System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0) + ": Verwende als ini-Datei für die Prüfung '"+ objIni.getFileName() + "'.");
			
			//PRÜFUNG: LIES EINEN .ini - DATEINAMEN AUS.
			//Merke: Der Pfad darf leer sein. Dann wird "." als aktuelles Verzeichnis angenommen    String sFilePath = objIni.getValue(stemp,"KernelConfigPath" +sAlias );
			
			//1. Versuch: Systemebene
			String sKeyUsed = this.getSystemKey();			
			String sFileName =objIni.getValue(sKeyUsed, "KernelConfigFile" +sModule );
			String sFileNameUsed = KernelExpressionIniConverterZZZ.getAsString(sFileName);
			if(!StringZZZ.equals(sFileName,sFileNameUsed)){
				System.out.println(ReflectCodeZZZ.getPositionCurrent()+ ": Value durch ExpressionIniConverter verändert von '" + sFileName + "' nach '" + sFileNameUsed +"'");
				//this.setValueRaw(sFileName);
			}else{
				//this.setValueRaw(null);
			}
			if(!StringZZZ.isEmpty(sFileNameUsed)) {
				bReturn = true;
				break main;
			}
			
			//2. Versuch: Applikationsebene
			if(StringZZZ.isEmptyNull(sFileNameUsed)){
				sKeyUsed = this.getApplicationKey();
				sFileName =objIni.getValue(sKeyUsed, "KernelConfigFile"+sModule );
				sFileNameUsed = KernelExpressionIniConverterZZZ.getAsString(sFileName);
				if(!StringZZZ.equals(sFileName,sFileNameUsed)){
					System.out.println(ReflectCodeZZZ.getPositionCurrent()+ ": Value durch ExpressionIniConverter verändert von '" + sFileName + "' nach '" + sFileNameUsed +"'");
					//this.setValueRaw(sFileName);
				}else{
					//this.setValueRaw(null);
				}
			}
			if(!StringZZZ.isEmpty(sFileNameUsed)) {
				bReturn = true;	
				break main;
			}						
		}//end main:
		return bReturn;
	}
		
	/** boolean, proofs the existance of the module configuration lines in the Kernel configuration .ini-file
	 *  AND checks if the configured file really exists.
	 * 
	* Lindhauer; 21.04.2006 09:39:09
	 * @param sModule
	 * @return
	 * @throws ExceptionZZZ
	 */
	public boolean proofModuleFileExists(String sModule) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			check:{
				if(StringZZZ.isEmpty(sModule)){
					ExceptionZZZ ez = new ExceptionZZZ("Missing parameter: 'Alias'",iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}								
			}//end check:
			
			//first, get the Kernel-Configuration-INI-File
			IniFile objIni = this.getFileConfigKernelAsIni();
			System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0) + ": Verwende als ini-Datei für die Prüfung '"+ objIni.getFileName() + "'.");
			
			//1. Versuch: Auf Systemebene
			String sKeyUsed = this.getSystemKey();
			String sFileName =objIni.getValue(sKeyUsed,"KernelConfigFile" +sModule ); 	
			String sFileNameUsed = KernelExpressionIniConverterZZZ.getAsString(sFileName);
			if(!StringZZZ.equals(sFileName,sFileNameUsed)){
				System.out.println(ReflectCodeZZZ.getPositionCurrent()+ ": Value durch ExpressionIniConverter verändert von '" + sFileName + "' nach '" + sFileNameUsed +"'");
				//this.setValueRaw(sFileName);
			}else{
				//this.setValueRaw(null);
			}
			
			//2. Versuch auf Applikationsebene
			if(StringZZZ.isEmpty(sFileNameUsed)) {								
				sKeyUsed = this.getApplicationKey();
				sFileName =objIni.getValue(sKeyUsed,"KernelConfigFile" +sModule ); 
				sFileNameUsed = KernelExpressionIniConverterZZZ.getAsString(sFileName);
				if(!StringZZZ.equals(sFileName,sFileNameUsed)){
					System.out.println(ReflectCodeZZZ.getPositionCurrent()+ ": Value durch ExpressionIniConverter verändert von '" + sFileName + "' nach '" + sFileNameUsed +"'");
					//this.setValueRaw(sFileName);
				}else{
					//this.setValueRaw(null);
				}
			}
			if(StringZZZ.isEmpty(sFileNameUsed)) break main;
			if(sFileNameUsed.equals(""))break main;								
			
			//###############
			String sFilePath = objIni.getValue(sKeyUsed,"KernelConfigPath" +sModule );
			String sFilePathUsed = KernelExpressionIniConverterZZZ.getAsString(sFilePath);
			if(!StringZZZ.equals(sFilePath,sFilePathUsed)){
				System.out.println(ReflectCodeZZZ.getPositionCurrent()+ ": Value durch ExpressionIniConverter verändert von '" + sFilePath + "' nach '" + sFilePathUsed +"'");
				//this.setValueRaw(sFilePath);
			}else{
				//this.setValueRaw(null);
			}						
			//if(StringZZZ.isEmpty(sFilePathUsed)) sFilePathUsed = "."; //Nun kann die Datei auch im gleichen Verzeichnis liegen
			
			//Proof the existance of the file
			String sFileTotal = FileEasyZZZ.joinFilePathName(sFilePathUsed, sFileNameUsed);
			if(this.getLogObject()!=null) this.getLogObject().WriteLineDate(ReflectCodeZZZ.getMethodCurrentName() + "#sFileTotal = " +  sFileTotal);
			File objFile = new File(sFileTotal);
			bReturn = FileEasyZZZ.exists(objFile);
			if(this.getLogObject()!=null) this.getLogObject().WriteLineDate(ReflectCodeZZZ.getMethodCurrentName() + "#FileExists = " + bReturn);
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
	public boolean proofSectionIsConfigured(String sAlias) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{			
			if(StringZZZ.isEmpty(sAlias)){
				ExceptionZZZ ez = new ExceptionZZZ("Missing parameter: 'Alias'",iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}								
					
			//first, get the Kernel-Configuration-INI-File
			FileIniZZZ objIni = this.getFileConfigIni();
			if(objIni==null) {
				ExceptionZZZ ez = new ExceptionZZZ("Not configured KernelIniFile'",iERROR_PROPERTY_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			File objFile = objIni.getFileObject();
			if(objFile==null) {
				ExceptionZZZ ez = new ExceptionZZZ("Configured KernelIniFile-Object has not internal File used.'",iERROR_PROPERTY_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			String sLog = ReflectCodeZZZ.getMethodCurrentNameLined(0) + ": Verwende als ini-Datei für die Prüfung '"+ objFile.getAbsolutePath() + "'.";
			this.logLineDate(sLog);
			
			bReturn = objIni.proofSectionExistsSearched(sAlias);						
		}//end main:
		return bReturn;
	}
	

	public void setLogObject(LogZZZ objLog){
		this.objLog = objLog;
	}
	
	private boolean KernelNew_(IKernelConfigZZZ objConfig, IKernelContextZZZ objContext, String sApplicationKeyIn,String sSystemNumberIn, String sDirectoryConfigIn, String sFileConfigIn, LogZZZ objLogIn, String[] saFlagControlIn) throws ExceptionZZZ{
		boolean bReturn = false;		
		main:{			
				String stemp=null; boolean btemp=false; String sLog = null;
				this.logLineDate(ReflectCodeZZZ.getMethodCurrentName() + ": Initializing KernelObject");
				
				//ggf. Config Object setzen
				this.setConfigObject(objConfig);
				
				//20191204: Umstrukturierung:
				//Zusätzlich zu übergebenen Flags müssen auch die Flags vom Config-Objekt übernommen werden, wenn sie vorhanden sind als Flags im KernelObjekt.
				//siehe als schon realisiertes Beispiel: Die Erstellung eines FileIniZZZ Objekts in: KernelKernelZZZ.getFileConfigIniByAlias(String sAlias) throws ExceptionZZZ{
							
				//Es geht dabei darum die Flags aus dem Configuration-Objekt zu überhnehmen, also z.B. "useFormula" zu übernehmen.												
				String[] saFlag = null;				
				if(objConfig!=null) {
					//Übernimm die gesetzten FlagZ...
					Map<String,Boolean>hmFlagZ = objConfig.getHashMapFlagZ();
					saFlag = (String[]) HashMapExtendedZZZ.getKeysAsStringFromValue(hmFlagZ, Boolean.TRUE);
				}																		
				String[]sa = StringArrayZZZ.append(saFlag, saFlagControlIn, "SKIPNULL");						
				String[]saFlagUsed = StringArrayZZZ.unique(sa);
												 
				if(saFlagUsed!=null) {
					//setzen der übergebenen Flags
					for(int iCount = 0;iCount<=saFlagUsed.length-1;iCount++){
					  stemp = saFlagUsed[iCount];
					  if(!StringZZZ.isEmpty(stemp)){
						  btemp = setFlag(stemp, true);
						  if(btemp==false){
							  sLog = "the flag '" + stemp + "' is not available.";
							  this.logLineDate(ReflectCodeZZZ.getMethodCurrentName() + ": " + sLog);
							  ExceptionZZZ ez = new ExceptionZZZ(sLog, iERROR_PARAMETER_VALUE, this,  ReflectCodeZZZ.getMethodCurrentName()); 
							  throw ez;		 
						  }
					  }
					}
					if(this.getFlag("INIT")==true){
						bReturn = true;
						break main; 
					}	
				}//end if saFlagUsed!=null
				 
				//+++++++++++++++++++++++++++++++
				//ggfs Context Object setzen
				this.setContextUsed(objContext);
				  
				//++++++++++++++++++++++++++++++++
				String sDirectoryLog = null;
				String sFileLog = null;
				String sApplicationKey = null;
				String sSystemNumber = null;
				String sFileConfig = null;
				String sDirectoryConfig = null;
				if(!StringZZZ.isEmpty(sApplicationKeyIn)){
					sApplicationKey = sApplicationKeyIn;
				}else if(this.getConfigObject()==null){
					sLog = "ApplicationKey not passed, Config-Object not available";
					this.logLineDate(ReflectCodeZZZ.getMethodCurrentName() + ": " + sLog);
					ExceptionZZZ ez = new ExceptionZZZ(sLog, iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}else if(this.getConfigObject().isOptionObjectLoaded()==false){
					//Fall: Das objConfig - Objekt existiert, aber es "lebt" von den dort vorhandenenen DEFAULT-Einträgen
					//      und nicht von irgendwelchen übergebenen Startparametern, sei es per Batch Kommandozeile oder per INI-Datei.
					sLog = "Config-Object not loaded, using DEFAULTS.";
					this.logLineDate(ReflectCodeZZZ.getMethodCurrentName() + ": " + sLog);
					sApplicationKey = this.getConfigObject().getApplicationKeyDefault();
					if(StringZZZ.isEmpty(sApplicationKey)){
						sLog = "ApplicationKey DEFAULT not receivable from Config-Object, Config-Object  not loaded.";
						this.logLineDate(ReflectCodeZZZ.getMethodCurrentName() + ": " + sLog);
						ExceptionZZZ ez = new ExceptionZZZ(sLog, iERROR_PROPERTY_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
						throw ez;
					}
				}else{
					sApplicationKey = this.getConfigObject().readApplicationKey();
					if(StringZZZ.isEmpty(sApplicationKey)){
						sLog = "ApplicationKey not receivable from Config-Object";
						this.logLineDate(ReflectCodeZZZ.getMethodCurrentName() + ": " + sLog);
						ExceptionZZZ ez = new ExceptionZZZ(sLog, iERROR_PROPERTY_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
						throw ez;
					}
				}
				this.setApplicationKey(sApplicationKey);
				
				btemp = StringZZZ.isEmpty(sSystemNumberIn);
				if(!btemp){
					sSystemNumber = sSystemNumberIn;
				}else if(this.getConfigObject()==null){
					sLog = "SystemNumber not passed, Config-Object not available";
					this.logLineDate(ReflectCodeZZZ.getMethodCurrentName() + ": " + sLog);
					ExceptionZZZ ez = new ExceptionZZZ(sLog, iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}else if(this.getConfigObject().isOptionObjectLoaded()==false){
					sLog = "SystemNumber unavailable, Config-Object not loaded, USING DEFAULTS";
					this.logLineDate(ReflectCodeZZZ.getMethodCurrentName() + ": " + sLog);
					
					//Fall: Das objConfig - Objekt existiert, aber es "lebt" von den dort vorhandenenen DEFAULT-Einträgen
					//      und nicht von irgendwelchen übergebenen Startparametern, sei es per Batch Kommandozeile oder per INI-Datei.
					sSystemNumber = this.getConfigObject().getSystemNumberDefault();
					if(StringZZZ.isEmpty(sApplicationKey)){
						sLog = "SystemNumber DEFAULT not receivable from Config-Object, Config-Object  not loaded.";
						this.logLineDate(ReflectCodeZZZ.getMethodCurrentName() + ": " + sLog);
						ExceptionZZZ ez = new ExceptionZZZ(sLog, iERROR_PROPERTY_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
						throw ez;
					}
				}else{
					sSystemNumber = this.getConfigObject().readSystemNumber();
					if(StringZZZ.isEmpty(sSystemNumber)){
						sLog = "SystemNumber not receivable from Config-Object";
						this.logLineDate(ReflectCodeZZZ.getMethodCurrentName() + ": " + sLog);
						ExceptionZZZ ez = new ExceptionZZZ(sLog, iERROR_PROPERTY_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
						throw ez;
					}
				}
				this.setSystemNumber(sSystemNumber);
				
				//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
				//get the Application-Configuration-File
				//A) Directory
				//     Hier kann auf das Config Objekt verzichtet werden. wenn nix gefunden wird, wird "." als aktuelles Verzeichnis genommen				
				if(!StringZZZ.isEmpty(sDirectoryConfigIn)){
					sDirectoryConfig = sDirectoryConfigIn;	
				}else if(this.getConfigObject()==null){
					sLog = "Configuration Directory not passed, Config-Object not available";
					this.logLineDate(ReflectCodeZZZ.getMethodCurrentName() + ": " + sLog);	
					ExceptionZZZ ez = new ExceptionZZZ(sLog, iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}else if(this.getConfigObject()!=null){
					if(this.getConfigObject().isOptionObjectLoaded()){
						sDirectoryConfig = this.getConfigObject().readConfigDirectoryName();
						sLog = "Directory from Configuration-Object passed: " + sDirectoryConfig;
						this.logLineDate(ReflectCodeZZZ.getMethodCurrentName() + ": " + sLog);
					}else{
						sLog = "Directory for configuration unavailable, Config-Object not (yet) loaded, USING DEFAULTS";
						this.logLineDate(ReflectCodeZZZ.getMethodCurrentName() + ": " + sLog);
						
						//Fall: Das objConfig - Objekt existiert, aber es "lebt" von den dort vorhandenenen DEFAULT-Einträgen
						//      und nicht von irgendwelchen übergebenen Startparametern, sei es per Batch Kommandozeile oder per INI-Datei.
						sDirectoryConfig = this.getConfigObject().getConfigDirectoryNameDefault();
						
						//20191204: NULL Werte sind als Verzeichnis erlaubt. <z:Null/> würde in der Konfiguration als NULL-Wert übersetzt.
						if(sDirectoryConfig==null){							
							sLog = "Directory is null in Configuration-Object passed for first load. Using Project - directory as default later.";
							this.logLineDate(ReflectCodeZZZ.getMethodCurrentName() + ": " + sLog);												
						}
					}										
				}else{
					sLog = "Directory is empty and no Configuration-Object passed. Using ROOT - directory.";
					System.out.println(sLog);					
					sDirectoryConfig = FileEasyZZZ.getFileRootPath();
				}
				
				//Prüfe nun auf Vorhandensein und korregiere ggfs. auf das aktuelle Verzeichnis
				File objDirectoryProof = FileEasyZZZ.searchDirectory(sDirectoryConfig, true);
				if(objDirectoryProof==null){					
					sLog = "Directory does not exists (='"+sDirectoryConfig+"'). Using CURRENT - directory.";
					this.logLineDate(ReflectCodeZZZ.getMethodCurrentName() + ": " + sLog);
					sDirectoryConfig = FileEasyZZZ.sDIRECTORY_CURRENT;//Falls das Verzeichnis nicht existiert, verwende das aktuelle Verzeichnis.
				}else{
					sDirectoryConfig=objDirectoryProof.getAbsolutePath();						
				}
				this.setFileConfigKernelDirectory(sDirectoryConfig);
				
				
				
				//B) FileName
				if(! StringZZZ.isEmpty(sFileConfigIn)){
					sFileConfig = sFileConfigIn;
				}else if(this.getConfigObject()==null){
					sLog = "Configuration Filename not passed, Config-Object not available";
					this.logLineDate(ReflectCodeZZZ.getMethodCurrentName() + ": " + sLog);	
					ExceptionZZZ ez = new ExceptionZZZ(sLog, iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}else if(this.getConfigObject()!=null){
					if(this.getConfigObject().isOptionObjectLoaded()){
						sFileConfig=this.getConfigObject().readConfigFileName();
						if(sFileConfig==null){
							sLog = "Filename for configuration DEFAULT not receivable from Config-Object, although Config-Object was loaded.";
							this.logLineDate(ReflectCodeZZZ.getMethodCurrentName() + ": " + sLog);
							ExceptionZZZ ez = new ExceptionZZZ(sLog, iERROR_PROPERTY_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
							throw ez;
						}	
					}else{
						sLog = "ConfigurationFilename unavailable, Config-Object not (yet) loaded, USING DEFAULTS";
						this.logLineDate(ReflectCodeZZZ.getMethodCurrentName() + ": " + sLog);
					
						//Fall: Das objConfig - Objekt existiert, aber es "lebt" von den dort vorhandenenen DEFAULT-Einträgen
						//      und nicht von irgendwelchen übergebenen Startparametern, sei es per Batch Kommandozeile oder per INI-Datei.
						sFileConfig = this.getConfigObject().getConfigFileNameDefault();
						if(sFileConfig==null){
							sLog = "Filename for configuration DEFAULT not receivable from Config-Object, Config-Object not loaded.";
							this.logLineDate(ReflectCodeZZZ.getMethodCurrentName() + ": " + sLog);
							ExceptionZZZ ez = new ExceptionZZZ(sLog, iERROR_PROPERTY_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
							throw ez;
						}						
					}
				}
				if(StringZZZ.isEmpty(sFileConfig)){
					sLog = "Filename for configuration is empty. Not passed and not readable from Config-Object.";
					this.logLineDate(ReflectCodeZZZ.getMethodCurrentName() + ": " + sLog);
					ExceptionZZZ ez = new ExceptionZZZ(sLog, iERROR_PROPERTY_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
				this.setFileConfigKernelName(sFileConfig);
				if(this.getFlag("DEBUG")){
					this.logLineDate(ReflectCodeZZZ.getMethodCurrentName() + " - SystemNr: '" + sSystemNumber + "'");
					this.logLineDate(ReflectCodeZZZ.getMethodCurrentName() + " - Configurationfile: '" + sFileConfig + "'");
						this.logLineDate(ReflectCodeZZZ.getMethodCurrentName() + " - Configurationpath: '" + sDirectoryConfig + "'");
				}
				
				//#############################################################
				//read the ini-content: 
				//Merke: Falls die Datei nicht existiert, wird ein Fehler geworfen
				String[] saFlagFileIniZZZ = {"USEFORMULA", "USEFORMULA_MATH"};
				FileIniZZZ objFileIniZZZ = new FileIniZZZ(this, this.getFileConfigKernelDirectory(), this.getFileConfigKernelName(), saFlagFileIniZZZ);
				this.setFileConfigIni(objFileIniZZZ);
				
				//Wirf eine Exception wenn weder der Application noch der SystemKey in der Konfigurationsdatei existieren
				String sKeyToProof = this.getApplicationKey();
				boolean bProofConfigMain = this.proofSectionIsConfigured(sKeyToProof);
				if(!bProofConfigMain) {
					String sFilePath = FileEasyZZZ.joinFilePathName(this.getFileConfigKernelDirectory(), this.getFileConfigKernelName());
					sLog = "In the configuration file '" + sFilePath + "' does the the section for the ApplicationKey '" + this.getApplicationKey() + "' and the section for the SystemKey '" + this.getSystemKey() + "' not exist or the section is empty.";
					this.logLineDate(ReflectCodeZZZ.getMethodCurrentName() + ": " + sLog);
					ExceptionZZZ ez = new ExceptionZZZ(sLog, iERROR_CONFIGURATION_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
						
				//##################################################################################
				//create the log using the configured path/file
				IniFile objIni = this.getFileConfigKernelAsIni();
				
				LogZZZ objLog = null;
				if(objLogIn==null){
					this.logLineDate(ReflectCodeZZZ.getMethodCurrentName() + ": " + "Erstelle neues Log Object");
//					1. Versuch: über die Programm-Konfiguration. 
					//Merke: Dies geht nur, wenn ein Context-Objekt �bergeben worden ist. An dieser Stelle kommt man nicht anders an den Namen der Aufrufenden - Klasse (d.h. den Programnamen) dran.
					if(this.getContextUsed()!=null){
						
						//!!! Falls da nix konfiguriert ist, darf kein Fehler geworfen werden !!!
						try{
							sDirectoryLog = this.getParameterByProgramAlias(this.getContextUsed().getModuleName(), this.getContextUsed().getProgramName(), "KernelLogPath").getValue(); 
							sFileLog = this.getParameterByProgramAlias(this.getContextUsed().getModuleName(), this.getContextUsed().getProgramName(), "KernelLogFile").getValue(); 
						}catch (ExceptionZZZ ez){
							//nix tun, Ausgabe nur zum Test/Debug
							System.out.println(ez.getDetailAllLast());
						}
					}	
					
					//2. Versuch: Über die Globale Konfiguration der Applikation (Systemkey)
					if(StringZZZ.isEmpty(sDirectoryLog) && StringZZZ.isEmpty(sFileLog)){
						sDirectoryLog = objIni.getValue(this.getSystemKey(), "KernelLogPath");
						sFileLog = objIni.getValue(this.getSystemKey(), "KernelLogFile");										
					}
					
					//3. Versuch: Über die Globale Konfiguration der Applikation (ApplicationKey)
					if(StringZZZ.isEmpty(sDirectoryLog) && StringZZZ.isEmpty(sFileLog)){
						sDirectoryLog = objIni.getValue(this.getApplicationKey(), "KernelLogPath");
						sFileLog = objIni.getValue(this.getApplicationKey(), "KernelLogFile");	
					}
					
					
					//4. Fehlermeldung, wenn kein Log definiert
					if(StringZZZ.isEmpty(sDirectoryLog) && StringZZZ.isEmpty(sFileLog)){
						String sFilePath = FileEasyZZZ.joinFilePathName(this.getFileConfigKernelDirectory(), this.getFileConfigKernelName());
						sLog = "In the configuration file '" + sFilePath + "' is no log configured (Properties 'KernelLogPath', 'KernelLogFile'   ";
						System.out.println(sLog);
						ExceptionZZZ ez = new ExceptionZZZ(sLog, iERROR_CONFIGURATION_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
						throw ez;
					}
					
					if(this.getFlag ("DEBUG")) System.out.println("Initialisiere KernelLog mit folgendem Pfad, Dateinamen: '" + sDirectoryLog +"', '" + sFileLog + "'");
					objLog = new LogZZZ(sDirectoryLog, sFileLog,KernelLogZZZ.FLAGZ.USE_FILE_EXPANSION.name());
				}else{
					System.out.println("Verwende LogObject erneut");
					objLog = objLogIn;
				}
				this.setLogObject(objLog);
		}//end main
		return bReturn;
	}

	
	//##### Interfaces	
	public IKernelConfigZZZ getConfigObject() throws ExceptionZZZ{
		if(this.objConfig==null){
			this.objConfig = new ConfigZZZ(null);			
		}
		return this.objConfig;
	}
	public void setConfigObject(IKernelConfigZZZ objConfig){
		this.objConfig = objConfig;
	}
	
	public IKernelContextZZZ getContextUsed() {
		return this.objContext;
	}
	public void setContextUsed(IKernelContextZZZ objContext) {
		this.objContext = objContext;
	}	
	

	public IKernelCacheZZZ getCacheObject(){
		if(this.objCache==null){
			this.objCache = new KernelCacheZZZ();
		}
		return this.objCache;		
	}
	public void setCacheObject(IKernelCacheZZZ objCache){
		this.objCache = objCache;
	}
	
	
	//aus IRessourceHandlingObjectZZZ
	/** Das Problem ist, das ein Zugriff auf Ressourcen anders gestaltet werden muss, wenn die Applikation in einer JAR-Datei läuft.
	 *   Merke: Static Klassen müssen diese Methode selbst implementieren.
	 * @return
	 * @author lindhaueradmin, 21.02.2019
	 * @throws ExceptionZZZ 
	 */
	@Override
	public boolean isInJar() throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			bReturn = JarEasyUtilZZZ.isInJar(this.getClass());
		}
		return bReturn;
	}
	
	//aus IKernelLogObjectUserZZZ, analog zu KernelUseObjectZZZ
	@Override
	public void logLineDate(String sLog) {
		LogZZZ objLog = this.getLogObject();
		if(objLog==null) {
			String sTemp = KernelLogZZZ.computeLineDate(sLog);
			System.out.println(sTemp);
		}else {
			objLog.WriteLineDate(sLog);
		}			
	}	
}//end class// end class
