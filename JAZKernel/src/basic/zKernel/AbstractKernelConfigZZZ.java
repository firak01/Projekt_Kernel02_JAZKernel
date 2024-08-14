package basic.zKernel;

import custom.zKernel.ConfigFGL;
import custom.zKernel.ConfigZZZ;
import custom.zKernel.file.ini.FileIniZZZ;

import static java.lang.System.out;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IObjectZZZ;
import basic.zBasic.AbstractObjectWithFlagZZZ;
import basic.zBasic.AbstractObjectZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.ReflectWorkspaceZZZ;
import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.abstractList.HashMapCaseInsensitiveZZZ;
import basic.zBasic.util.crypt.code.ICryptZZZ;
import basic.zBasic.util.datatype.calling.ReferenceZZZ;
import basic.zBasic.util.datatype.json.JsonEasyZZZ;
import basic.zBasic.util.datatype.string.StringArrayZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.file.FileEasyZZZ;
import basic.zBasic.util.file.JarEasyUtilZZZ;
import basic.zKernel.config.KernelConfigSectionEntryUtilZZZ;
import basic.zKernel.file.ini.IKernelCallIniSolverZZZ;
import basic.zKernel.file.ini.IKernelEncryptionIniSolverZZZ;
import basic.zKernel.file.ini.IKernelExpressionIniSolverZZZ;
import basic.zKernel.file.ini.IKernelJavaCallIniSolverZZZ;
import basic.zKernel.file.ini.IKernelJsonArrayIniSolverZZZ;
import basic.zKernel.file.ini.IKernelJsonIniSolverZZZ;
import basic.zKernel.file.ini.IKernelJsonMapIniSolverZZZ;
import basic.zKernel.file.ini.IKernelZFormulaIniSolverZZZ;
import basic.zKernel.file.ini.IKernelZFormulaIniZZZ;
import basic.zKernel.file.ini.IKernelZFormulaIni_PathZZZ;
import basic.zKernel.flag.IFlagZUserZZZ;

/**Klasse wertet Kommandozeilenparamter aus, hinsichtlich der zu verwendenden Kernel-Konfiguration
 * -k = ApplicationKey
 * -s = SystemKey
 * -d = directory
 * -f = file (.ini)
 * -z = flagz, JSON String mit dem beliebige Flags von aussen gesetzt werden. Sie werden in einer extra HashMap-verwaltet.
 * Mit diesen Informationen kann dann das eigentliche Kernel-Objekt erstellt werden
 * Z.B.:  -z {"DEBUGUI_PANELLABEL_ON":true,"DEBUGUI_PANELLIST_STRATEGIE_ENTRYFIRST":true,"DEBUGUI_PANELLIST_STRATEGIE_ENTRYDUMMY":true,"DEBUGUI_PANELLIST_STRATEGIE_ENTRYLAST":true}
 * 
 * 			//20210331: Jetzt sind aber Optionsparameter mit mehr als 1 Zeichen gewünscht.
			//          Das ist gescheitert, da zuviel zu ändern ist.
 * 
 * @author lindhauer
 * 
 */
public abstract class AbstractKernelConfigZZZ<T> extends AbstractObjectWithFlagZZZ<T> implements IKernelConfigZZZ,IKernelExpressionIniSolverZZZ{
	//FLAGZ, die dann zum "Rechnen in der Konfiguations Ini Datei" gesetzt sein müssen.
//	public enum FLAGZ{
//		USEFORMULA, USEFORMULA_MATH;
//	}
	
	private static final long serialVersionUID = -8991085241039836506L;
	private IKernelConfigSectionEntryZZZ objEntry = null;
	private GetOptZZZ objOpt = null;
	private ICryptZZZ objCrypt = null;
	private String sCallingProjectPathTotal = null;//Zum Ausrechnen des Pfads diese Projekts, wenn es aus einem anderen Projekt aus aufgerufen wird.
	
	public AbstractKernelConfigZZZ() throws ExceptionZZZ{
		super();//20210403: Das direkte Setzen der Flags wird nun in ObjectZZZ komplett erledigt
		ConfigNew_(null);
	}
	public AbstractKernelConfigZZZ(String[] saArg) throws ExceptionZZZ{
		super((String[])null);//20210403: Das direkte Setzen der Flags wird nun in ObjectZZZ komplett erledigt
		ConfigNew_(saArg);
	}	
	public AbstractKernelConfigZZZ(String[] saArg, String[]saFlagControl) throws ExceptionZZZ{
		super(saFlagControl); //20210403: Das direkte Setzen der Flags wird nun in ObjectZZZ komplett erledigt 	
		ConfigNew_(saArg);
	}
	
	public AbstractKernelConfigZZZ(String[] saArg, String sFlagControl) throws ExceptionZZZ{
		super(sFlagControl); //20210403: Das direkte Setzen der Flags wird nun in ObjectZZZ komplett erledigt
		ConfigNew_(saArg);
	}
	
	
	private boolean ConfigNew_(String[] saArgIn) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{				
			System.out.println("Initializing ConfigObject");
			if(this.getFlag("INIT")==true){
				bReturn = true;
				break main; 
			}	
						
			String[] saArg = null;
			if(saArgIn==null || StringArrayZZZ.isEmpty(saArgIn)){
				//Das Argument-Array darf auch leer sein.
//				ExceptionZZZ ez = new ExceptionZZZ("Argument - Array", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
//				throw ez;
				
				saArg = new String[10];
				saArg[0] = "-k";
				saArg[1] = this.getApplicationKeyDefault();
				saArg[2] = "-s";
				saArg[3] = this.getSystemNumberDefault();
				saArg[4] = "-f";
				saArg[5] = this.getConfigFileNameDefault();
				saArg[6] = "-d";
				saArg[7] = this.getConfigDirectoryNameDefault();
				saArg[8] = "-z";
				saArg[9] = this.getConfigFlagzJsonDefault();
			}else {
				saArg = saArgIn;
			}
			
			//Nun den konfigurierten String holen
			String sPattern = this.getPatternStringDefault();
			
			//Das Objekt, das für die Interpretation der Argumente sorgt.Falls Argument werte vorhanden sind "Werden sie automatisch sofort geladen".
			this.objOpt = new GetOptZZZ(sPattern, saArg);
			
			//20210331: Nun die HashMap für die weiterzureichenden FlagZ Werte füllen
			String sJson = this.objOpt.readValue("z");
			HashMap<String, Boolean> hmFlagZpassed = AbstractKernelConfigZZZ.computeHashMapFlagFromJSON(sJson);
			this.setHashMapFlagPassed(hmFlagZpassed);
			
			bReturn = true;
		}
		return bReturn;
	}
	

	public String expressionSolveConfigDirectoryNameDefault(String sDirectoryNameReadIn) throws ExceptionZZZ{
		String sReturn=null;
		main:{		
		boolean bUseExpression = this.getFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION);
		boolean bUseFormula = this.getFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA);
		String sDirectoryNameRead = sDirectoryNameReadIn;//z.B. auch "<z:Null/>"
		//20191031: Dieser Wert muss vom Programm verarbeitet/Übersetzt werden werden - wie ein ini-Datei Eintrag auch übersetzt würde.
		//return "<z:Null/>";//Merke '.' oder Leerstring '' = src Verzeichnis
		
		ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference= new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
		try {
			if(bUseExpression && bUseFormula) {	
				FileIniZZZ objFileIniConfig = new FileIniZZZ();
				int iConvertionType = KernelConfigSectionEntryUtilZZZ.getValueExpressionSolvedAndConverted(objFileIniConfig, sDirectoryNameRead, bUseFormula, (HashMapCaseInsensitiveZZZ<String,String>) null, (String[]) null, objReturnReference);
				sReturn = objReturnReference.get().getValue();
			}else {
				sReturn  = sDirectoryNameRead;
			}
		}catch(ExceptionZZZ ez){
			String sError = ez.getMessageLast();
			try {
				out.format("%s# Error thrown: %s%n", ReflectCodeZZZ.getPositionCurrent(),sError);
			} catch (ExceptionZZZ e) {
				String sErrorInt = ez.getMessageLast();
				out.format("%s# Error thrown: %s%n", "Holen der akutellen Position",sErrorInt);
				e.printStackTrace();
			}
		}
		}//end main
		return sReturn;
	}
	
	/**
	 * @param sJSON, Beispiel für ein Array, das in eine HashMap gepackt werden soll:
	 *               {'k1':'apple','k2':'orange'}"
	 *               Das funktioniert hier aber mit dem 2ten Wert als Boolean, was im intern verwendeten TypeToken fest angegeben wird.
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 02.04.2021, 08:48:35
	 */
	public static HashMap<String, Boolean> computeHashMapFlagFromJSON(String sJSON) throws ExceptionZZZ {
		HashMap<String, Boolean> hmReturn = null;
		main:{
			if(StringZZZ.isEmpty(sJSON)) break main;
			if(!JsonEasyZZZ.isJsonValid(sJSON)) break main;
						
			TypeToken<HashMap<String, Boolean>> typeToken = new TypeToken<HashMap<String, Boolean>>(){};
			hmReturn = (HashMap<String, Boolean>) JsonEasyZZZ.toHashMap(sJSON);												
		}//end main
		return hmReturn;
	}
	
	public String readPatternString(){
		String sReturn = null;
		main:{
			GetOptZZZ objOpt = this.getOptObject();
			if(objOpt==null) break main;
			if(objOpt.getFlag("isLoaded")==false) break main;
			
			sReturn = objOpt.getPattern();
			if(sReturn==null){
				sReturn = this.getPatternStringDefault();
			}
		}		
		return sReturn;
	}
	
	public String readApplicationKey(){
		String sReturn = null;
		main:{
			GetOptZZZ objOpt = this.getOptObject();
			if(objOpt==null) break main;
			if(objOpt.getFlag("isLoaded")==false) break main;
			
			sReturn = objOpt.readValue("k");
			if(sReturn==null){
				sReturn = this.getApplicationKeyDefault();
			}
		}//end main:		
		return sReturn;
	}
	
	public String readSystemNumber(){
		String sReturn = null;
		main:{
			GetOptZZZ objOpt = this.getOptObject();
			if(objOpt==null) break main;
			if(objOpt.getFlag("isLoaded")==false) break main;
			
			sReturn = objOpt.readValue("s");
			if(sReturn==null){
				sReturn = this.getSystemNumberDefault();
			}
		}//end main:		
		return sReturn;
	}
	
	public String readConfigDirectoryName() throws ExceptionZZZ{
		String sReturn = null;
		main:{
			GetOptZZZ objOpt = this.getOptObject();
			if(objOpt==null) break main;
			if(objOpt.getFlag("isLoaded")==false) break main;
			
			sReturn = objOpt.readValue("d");			
			if(sReturn==null){
				sReturn = this.getConfigDirectoryNameDefault();				
			}
			sReturn = this.expressionSolveConfigDirectoryNameDefault(sReturn);			
		}//end main:						
		return sReturn;
	} 
	  
	public String readConfigFileName(){
		String sReturn = null;
		main:{
			GetOptZZZ objOpt = this.getOptObject();
			if(objOpt==null) break main;
			if(objOpt.getFlag("isLoaded")==false) break main;
			 
			sReturn = objOpt.readValue("f");
			if(sReturn==null){
				sReturn = this.getConfigFileNameDefault();
			}
		}//end main:		
		return sReturn;
	}
	
	public String readFlagzJson(){
		String sReturn = null;
		main:{
			GetOptZZZ objOpt = this.getOptObject();
			if(objOpt==null) break main;
			if(objOpt.getFlag("isLoaded")==false) break main;
			
			sReturn = objOpt.readValue("z"); //20210331: Eigentlich sollte das ein längerer String werden flagz, aber die Umstellung auf Steuerungsangaben mit mehr als 1 Zeichen länge wäre zu aufwändig.
			if(sReturn==null){
				sReturn = this.getConfigFlagzJsonDefault();
			}
		}		
		return sReturn;
	}
	
	
	/** Heuristische Lösung. 
	 *  Funktioniert so im Vergleich "Webservice" vs. "Swing Standalone in Eclipse"
	 * @return
	 * @author lindhaueradmin, 07.11.2018, 07:26:27
	 */
	@Override
	public boolean isOnServer() throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			bReturn = FileEasyZZZ.isOnServer();
		}
		return bReturn;
	}
		
		
	/** Ist notwendig, da der Zugriff auf Ressource anders ist,  wenn die Applikation in einer .jar Datei liegt.
	 * @return
	 * @author lindhaueradmin, 07.11.2018, 07:26:27
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
	
	/** Ist notwendig, da der Zugriff auf Ressource anders ist,  wenn die Applikation in einer .jar Datei liegt.
	 * @return
	 * @author lindhaueradmin, 07.11.2018, 07:26:27
	 * @throws ExceptionZZZ 
	 */
	@Override
	public boolean isInIDE() throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			bReturn = FileEasyZZZ.isInIDE(this.getClass());
		}
		return bReturn;
	}
	
	/**Intern wird ein GetOptZZZ-Objekt verwendet, um die Argumente auszuwerten.
	 *  Hier wird das "isLoaded" - Flag dieses Objekts ausgewertet. 
	 *  Falls z.B. keine Argumente �bergeben worden sind, oder die Argumente fehlerhaft sind, dann sollte dieses Flag nicht gesetzt sein.
	* @return
	* 
	* lindhauer; 12.08.2007 06:50:56
	 */
	public boolean isOptionObjectLoaded(){
		boolean bReturn =false;
		main:{
			if(this.objOpt==null) break main;
			bReturn = this.objOpt.getFlag("isloaded");
		}
		return bReturn;
	}
	
	@Override
	public boolean isApplicationKeyDefault(String sValue) {
		boolean bReturn = false;{
			main:{
				if(StringZZZ.isEmpty(sValue))break main;
				if(sValue.equals(this.getApplicationKeyDefault())) {
					bReturn = true;
				}
			}
		}
		return bReturn;
	}
	
	@Override
	public boolean isSystemNumberDefault(String sValue) {
		boolean bReturn = false;{
			main:{
				if(StringZZZ.isEmpty(sValue))break main;
				if(sValue.equals(this.getSystemNumberDefault())) {
					bReturn = true;
				}
			}
		}
		return bReturn;
	}
	
	@Override
	public boolean isConfigDirectoryNameDefault(String sValue) {
		boolean bReturn = false;{
			main:{
				if(StringZZZ.isEmpty(sValue))break main;
				if(sValue.equals(this.getConfigDirectoryNameDefault())) {
					bReturn = true;
				}
			}
		}
		return bReturn;
	}
	
	@Override
	public boolean isConfigFileNameDefault(String sValue) {
		boolean bReturn = false;{
			main:{
				if(StringZZZ.isEmpty(sValue))break main;
				if(sValue.equals(this.getConfigFileNameDefault())) {
					bReturn = true;
				}
			}
		}
		return bReturn;
	}
	
	@Override
	public boolean isConfigFlagzJsonDefault(String sValue) {
		boolean bReturn = false;{
			main:{
				if(StringZZZ.isEmpty(sValue))break main;
				if(sValue.equals(this.getConfigFlagzJsonDefault())) {
					bReturn = true;
				}
			}
		}
		return bReturn;
	}
	
	@Override
	public boolean isPatternStringDefault(String sValue) {
		boolean bReturn = false;{
			main:{
				if(StringZZZ.isEmpty(sValue))break main;
				if(sValue.equals(this.getPatternStringDefault())) {
					bReturn = true;
				}
			}
		}
		return bReturn;
	}
	
	//### Interface aus IKernelExpressionIniSolver
	public IKernelConfigSectionEntryZZZ getEntry() throws ExceptionZZZ {
		if(this.objEntry==null) {
			this.objEntry = new KernelConfigSectionEntryZZZ<T>();			
		}
		return this.objEntry;
	}
	public void setEntry(IKernelConfigSectionEntryZZZ objEntry) {
		this.objEntry = objEntry;
	}
	

	//### Interface ###########
	public String getConfigFlagzJsonDefault() {
		return IKernelConfigZZZ.sFLAGZ_DEFAULT;
	}
	
	public String getPatternStringDefault() {
		return IKernelConfigZZZ.sPATTERN_DEFAULT;
	}
	
	//### Aus Interface ICryptUserZZZ
		@Override
		public ICryptZZZ getCryptAlgorithmType() throws ExceptionZZZ {
			return this.objCrypt;
		}
		@Override
		public void setCryptAlgorithmType(ICryptZZZ objCrypt) {
			this.objCrypt=objCrypt;
		}
			
		
		//### Aus Interface IKernelConfigProjectHelperZZZ
		@Override
		public String getProjectPath() throws ExceptionZZZ{
			return this.computeProjectPath();
		}
		@Override
		public String computeProjectPath() throws ExceptionZZZ {
			return AbstractKernelConfigZZZ.computeProjectPath(this.getProjectDirectory(), this.getProjectName());
		}
		
		public static String computeProjectPath(String sProjektDirectory, String sProjectName) throws ExceptionZZZ {
			String sReturn = FileEasyZZZ.joinFilePathNameForUrl(sProjektDirectory, sProjectName);
			sReturn = JarEasyUtilZZZ.toFilePath(sReturn); //Slash in Backslash umwandeln, etc.
			return sReturn;
		}
		
		@Override 
		public String computeProjectPathTotal() throws ExceptionZZZ {
			String sReturn = null;
			String sWorkspacePath = ReflectWorkspaceZZZ.computeWorkspaceRunningProjectPath(); //Hole den aktuellen Pfad des gesamten "aktuellen" Projekts
			
			//Davon einen ggfs. vorhandenen Aufrufenden Projektpfad abziehen.
			String sProjectCallingPath = this.getCallingProjectPath();
			String sWorkspacePathPure = StringZZZ.leftback(sWorkspacePath, sProjectCallingPath);
			sWorkspacePathPure = StringZZZ.stripFileSeparators(sWorkspacePathPure);
			
			String sProjectPath = AbstractKernelConfigZZZ.computeProjectPath(this.getProjectDirectory(), this.getProjectName());
			
			sReturn = FileEasyZZZ.joinFilePathNameForUrl(sWorkspacePathPure, sProjectPath);
			sReturn = JarEasyUtilZZZ.toFilePath(sReturn); //Slash in Backslash umwandeln, etc.
			return sReturn;
		}
		
		@Override
		public String getProjectPathTotal() throws ExceptionZZZ {
			return this.computeProjectPathTotal();
		}
		
		@Override
		public String getCallingProjectPath() {
			return this.sCallingProjectPathTotal;
		}
		
		@Override
		public void setCallingProjectPath(String sCallingProjectPathTotal) {
			this.sCallingProjectPathTotal = sCallingProjectPathTotal;
		}
		
		//##########
		// Getter / Setter
		//##########
		public GetOptZZZ getOptObject(){
			return this.objOpt;
		}		
	
	
	//##################################################
	//### FLAG Handling
	
	//### aus IKernelZFormulaIni_PathZZZ
	@Override
	public boolean getFlag(IKernelZFormulaIni_PathZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ{
		return this.getFlag(objEnumFlag.name());
	}
	
	@Override
	public boolean setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ objEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		return this.setFlag(objEnumFlag.name(), bFlagValue);
	}
	
	@Override
	public boolean[] setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ[] objaEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		boolean[] baReturn=null;
		main:{
			if(!ArrayUtilZZZ.isNull(objaEnumFlag)) {
				baReturn = new boolean[objaEnumFlag.length];
				int iCounter=-1;
				for(IKernelZFormulaIni_PathZZZ.FLAGZ objEnumFlag:objaEnumFlag) {
					iCounter++;
					boolean bReturn = this.setFlag(objEnumFlag, bFlagValue);
					baReturn[iCounter]=bReturn;
				}
			}
		}//end main:
		return baReturn;
	}
	
	@Override
	public boolean proofFlagExists(IKernelZFormulaIni_PathZZZ.FLAGZ objaEnumFlag) throws ExceptionZZZ {
		return this.proofFlagExists(objaEnumFlag.name());
	}
	
	@Override
	public boolean proofFlagSetBefore(IKernelZFormulaIni_PathZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
			return this.proofFlagSetBefore(objEnumFlag.name());
	}
	
	//###### aus IKernelExpressionIniSolverZZZ
	@Override
	public boolean getFlag(IKernelExpressionIniSolverZZZ.FLAGZ objEnumFlag) {
		return this.getFlag(objEnumFlag.name());
	}
	
	@Override
	public boolean setFlag(IKernelExpressionIniSolverZZZ.FLAGZ objEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		return this.setFlag(objEnumFlag.name(), bFlagValue);
	}

	@Override
	public boolean[] setFlag(IKernelExpressionIniSolverZZZ.FLAGZ[] objaEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		boolean[] baReturn=null;
		main:{
			if(!ArrayUtilZZZ.isNull(objaEnumFlag)) {
				baReturn = new boolean[objaEnumFlag.length];
				int iCounter=-1;
				for(IKernelExpressionIniSolverZZZ.FLAGZ objEnumFlag:objaEnumFlag) {
					iCounter++;
					boolean bReturn = this.setFlag(objEnumFlag, bFlagValue);
					baReturn[iCounter]=bReturn;
				}
			}
		}//end main:
		return baReturn;
	}
	
	@Override
	public boolean proofFlagExists(IKernelExpressionIniSolverZZZ.FLAGZ objaEnumFlag) throws ExceptionZZZ {
		return this.proofFlagExists(objaEnumFlag.name());
	}
	
	@Override
	public boolean proofFlagSetBefore(IKernelExpressionIniSolverZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
			return this.proofFlagSetBefore(objEnumFlag.name());
	}
	
	//### aus IKernelEncryptionIniSolverZZZ
	@Override
	public boolean getFlag(IKernelEncryptionIniSolverZZZ.FLAGZ objEnumFlag) {
		return this.getFlag(objEnumFlag.name());
	}
	
	@Override
	public boolean setFlag(IKernelEncryptionIniSolverZZZ.FLAGZ objEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		return this.setFlag(objEnumFlag.name(), bFlagValue);
	}
	
	@Override
	public boolean[] setFlag(IKernelEncryptionIniSolverZZZ.FLAGZ[] objaEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		boolean[] baReturn=null;
		main:{
			if(!ArrayUtilZZZ.isNull(objaEnumFlag)) {
				baReturn = new boolean[objaEnumFlag.length];
				int iCounter=-1;
				for(IKernelEncryptionIniSolverZZZ.FLAGZ objEnumFlag:objaEnumFlag) {
					iCounter++;
					boolean bReturn = this.setFlag(objEnumFlag, bFlagValue);
					baReturn[iCounter]=bReturn;
				}
			}
		}//end main:
		return baReturn;
	}
	
	@Override
	public boolean proofFlagExists(IKernelEncryptionIniSolverZZZ.FLAGZ objaEnumFlag) throws ExceptionZZZ {
		return this.proofFlagExists(objaEnumFlag.name());
	}
	
	@Override
	public boolean proofFlagSetBefore(IKernelEncryptionIniSolverZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
			return this.proofFlagSetBefore(objEnumFlag.name());
	}
	
	//### aus IKernelJsonIniSolverZZZ
	@Override
	public boolean getFlag(IKernelJsonIniSolverZZZ.FLAGZ objEnumFlag) {
		return this.getFlag(objEnumFlag.name());
	}
	
	@Override
	public boolean setFlag(IKernelJsonIniSolverZZZ.FLAGZ objEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		return this.setFlag(objEnumFlag.name(), bFlagValue);
	}
	
	@Override
	public boolean[] setFlag(IKernelJsonIniSolverZZZ.FLAGZ[] objaEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		boolean[] baReturn=null;
		main:{
			if(!ArrayUtilZZZ.isNull(objaEnumFlag)) {
				baReturn = new boolean[objaEnumFlag.length];
				int iCounter=-1;
				for(IKernelJsonIniSolverZZZ.FLAGZ objEnumFlag:objaEnumFlag) {
					iCounter++;
					boolean bReturn = this.setFlag(objEnumFlag, bFlagValue);
					baReturn[iCounter]=bReturn;
				}
			}
		}//end main:
		return baReturn;
	}
	
	@Override
	public boolean proofFlagExists(IKernelJsonIniSolverZZZ.FLAGZ objaEnumFlag) throws ExceptionZZZ {
		return this.proofFlagExists(objaEnumFlag.name());
	}
	
	@Override
	public boolean proofFlagSetBefore(IKernelJsonIniSolverZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
			return this.proofFlagSetBefore(objEnumFlag.name());
	}
	
	//Aus IKernelJsonMapIniSolverZZZ
	@Override
	public boolean getFlag(IKernelJsonMapIniSolverZZZ.FLAGZ objEnumFlag) {
		return this.getFlag(objEnumFlag.name());
	}
	
	@Override
	public boolean setFlag(IKernelJsonMapIniSolverZZZ.FLAGZ objEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		return this.setFlag(objEnumFlag.name(), bFlagValue);
	}

	@Override
		public boolean[] setFlag(IKernelJsonMapIniSolverZZZ.FLAGZ[] objaEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
			boolean[] baReturn=null;
			main:{
				if(!ArrayUtilZZZ.isNull(objaEnumFlag)) {
					baReturn = new boolean[objaEnumFlag.length];
					int iCounter=-1;
					for(IKernelJsonMapIniSolverZZZ.FLAGZ objEnumFlag:objaEnumFlag) {
						iCounter++;
						boolean bReturn = this.setFlag(objEnumFlag, bFlagValue);
						baReturn[iCounter]=bReturn;
					}
				}
			}//end main:
			return baReturn;
		}

	@Override
	public boolean proofFlagExists(IKernelJsonMapIniSolverZZZ.FLAGZ objaEnumFlag) throws ExceptionZZZ {
		return this.proofFlagExists(objaEnumFlag.name());
	}
	
	@Override
	public boolean proofFlagSetBefore(IKernelJsonMapIniSolverZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
			return this.proofFlagSetBefore(objEnumFlag.name());
	}

	//### aus IKernelJsonArrayIniSolverZZZ
		@Override
		public boolean getFlag(IKernelJsonArrayIniSolverZZZ.FLAGZ objEnumFlag) {
			return this.getFlag(objEnumFlag.name());
		}
		
		@Override
		public boolean setFlag(IKernelJsonArrayIniSolverZZZ.FLAGZ objEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
			return this.setFlag(objEnumFlag.name(), bFlagValue);
		}

		@Override
			public boolean[] setFlag(IKernelJsonArrayIniSolverZZZ.FLAGZ[] objaEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
				boolean[] baReturn=null;
				main:{
					if(!ArrayUtilZZZ.isNull(objaEnumFlag)) {
						baReturn = new boolean[objaEnumFlag.length];
						int iCounter=-1;
						for(IKernelJsonArrayIniSolverZZZ.FLAGZ objEnumFlag:objaEnumFlag) {
							iCounter++;
							boolean bReturn = this.setFlag(objEnumFlag, bFlagValue);
							baReturn[iCounter]=bReturn;
						}
					}
				}//end main:
				return baReturn;
			}
		
		@Override
		public boolean proofFlagExists(IKernelJsonArrayIniSolverZZZ.FLAGZ objaEnumFlag) throws ExceptionZZZ {
			return this.proofFlagExists(objaEnumFlag.name());
		}
		
		@Override
		public boolean proofFlagSetBefore(IKernelJsonArrayIniSolverZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
				return this.proofFlagSetBefore(objEnumFlag.name());
		}
		
		//### aus IKernelCallIniSolverZZZ
		@Override
		public boolean getFlag(IKernelCallIniSolverZZZ.FLAGZ objEnumFlag) {
			return this.getFlag(objEnumFlag.name());
		}
		
		@Override
		public boolean setFlag(IKernelCallIniSolverZZZ.FLAGZ objEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
			return this.setFlag(objEnumFlag.name(), bFlagValue);
		}

		@Override
			public boolean[] setFlag(IKernelCallIniSolverZZZ.FLAGZ[] objaEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
				boolean[] baReturn=null;
				main:{
					if(!ArrayUtilZZZ.isNull(objaEnumFlag)) {
						baReturn = new boolean[objaEnumFlag.length];
						int iCounter=-1;
						for(IKernelCallIniSolverZZZ.FLAGZ objEnumFlag:objaEnumFlag) {
							iCounter++;
							boolean bReturn = this.setFlag(objEnumFlag, bFlagValue);
							baReturn[iCounter]=bReturn;
						}
					}
				}//end main:
				return baReturn;
			}
		
		@Override
		public boolean proofFlagExists(IKernelCallIniSolverZZZ.FLAGZ objaEnumFlag) throws ExceptionZZZ {
			return this.proofFlagExists(objaEnumFlag.name());
		}
		
		@Override
		public boolean proofFlagSetBefore(IKernelCallIniSolverZZZ.FLAGZ objaEnumFlag) throws ExceptionZZZ {
			return this.proofFlagExists(objaEnumFlag.name());
		}
		
		//### Aus Interface IKernelJavaCallIniSolverZZZ
		@Override
		public boolean getFlag(IKernelJavaCallIniSolverZZZ.FLAGZ objEnum_IKernelJavaCallIniSolverZZZ) {
			return this.getFlag(objEnum_IKernelJavaCallIniSolverZZZ.name());
		}
		
		@Override
		public boolean setFlag(IKernelJavaCallIniSolverZZZ.FLAGZ objEnum_IKernelJavaCallIniSolverZZZ, boolean bFlagValue) throws ExceptionZZZ {
			return this.setFlag(objEnum_IKernelJavaCallIniSolverZZZ.name(), bFlagValue);
		}
				
		@Override
		public boolean[] setFlag(IKernelJavaCallIniSolverZZZ.FLAGZ[] objaEnum_IKernelJavaCallIniSolverZZZ, boolean bFlagValue) throws ExceptionZZZ {
			boolean[] baReturn=null;
			main:{
				if(!ArrayUtilZZZ.isNull(objaEnum_IKernelJavaCallIniSolverZZZ)) {
					baReturn = new boolean[objaEnum_IKernelJavaCallIniSolverZZZ.length];
					int iCounter=-1;
					for(IKernelJavaCallIniSolverZZZ.FLAGZ objEnum_IKernelJavaCallIniSolverZZZ:objaEnum_IKernelJavaCallIniSolverZZZ) {
						iCounter++;
						boolean bReturn = this.setFlag(objEnum_IKernelJavaCallIniSolverZZZ, bFlagValue);
						baReturn[iCounter]=bReturn;
					}
				}
			}//end main:
			return baReturn;
		}

		@Override
		public boolean proofFlagExists(IKernelJavaCallIniSolverZZZ.FLAGZ objEnum_IKernelJavaCallIniSolverZZZ) throws ExceptionZZZ {
			return this.proofFlagExists(objEnum_IKernelJavaCallIniSolverZZZ.name());
		}
		
		@Override
		public boolean proofFlagSetBefore(IKernelJavaCallIniSolverZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
				return this.proofFlagSetBefore(objEnumFlag.name());
		}
}
