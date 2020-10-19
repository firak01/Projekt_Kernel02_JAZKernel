package basic.zKernel;

import custom.zKernel.ConfigZZZ;
import custom.zKernel.file.ini.FileIniZZZ;

import static java.lang.System.out;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IObjectZZZ;
import basic.zBasic.ObjectZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractList.HashMapCaseInsensitiveZZZ;
import basic.zBasic.util.datatype.calling.ReferenceZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.file.FileEasyZZZ;
import basic.zBasic.util.file.JarEasyUtilZZZ;
import basic.zKernel.config.KernelConfigEntryUtilZZZ;

/**Klasse wertet Kommandozeilenparamter aus, hinsichtlich der zu verwendenden Kernel-Konfiguration
 * -k = ApplicationKey
 * -s = SystemKey
 * -d = directory
 * -f = file (.ini)
 * 
 * Mit diesen Informationen kann dann das eigentliche Kernel-Objekt erstellt werden
 * 
 * @author lindhauer
 * 
 */
public abstract class KernelConfigZZZ extends ObjectZZZ implements IObjectZZZ, IKernelConfigZZZ,IKernelExpressionIniConverterUserZZZ{
	//FLAGZ, die dann zum "Rechnen in der Konfiguations Ini Datei" gesetzt sein müssen.
	public enum FLAGZ{
		USEFORMULA, USEFORMULA_MATH;
	}
	
	private GetOptZZZ objOpt = null;
	 
	public KernelConfigZZZ() throws ExceptionZZZ{
		ConfigNew_(null, null);
	}
	public KernelConfigZZZ(String[] saArg) throws ExceptionZZZ{
	 	ConfigNew_(saArg, null);
	}	
	public KernelConfigZZZ(String[] saArg, String[]saFlagControl) throws ExceptionZZZ{
	 	ConfigNew_(saArg, saFlagControl);	 	
	}
	public KernelConfigZZZ(String[] saArg, String sFlagControl) throws ExceptionZZZ{
		String[] saFlagControl = new String[1];
		saFlagControl[0]=sFlagControl;
	 	ConfigNew_(saArg, saFlagControl);	 	
	}
	
	
	private boolean ConfigNew_(String[] saArgIn, String[] saFlagControlIn) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{	
			String stemp=null; boolean btemp=false; String sLog = null;
			System.out.println("Initializing ConfigObject");
			
			//setzen der übergebenen Flags	
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
			
			String[] saArg = null;
			if(saArgIn==null || saArgIn.length==0){
				//Das Argument-Array darf auch leer sein.
//				ExceptionZZZ ez = new ExceptionZZZ("Argument - Array", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
//				throw ez;
				
				saArg = new String[8];
				saArg[0] = "-k";
				saArg[1] = this.getApplicationKeyDefault();
				saArg[2] = "-s";
				saArg[3] = this.getSystemNumberDefault();
				saArg[4] = "-f";
				saArg[5] = this.getConfigFileNameDefault();
				saArg[6] = "-d";
				saArg[7] = this.getConfigDirectoryNameDefault();
			}else {
				saArg = saArgIn;
			}
			
			//Nun den konfigurierten String holen
			String sPattern = this.getPatternStringDefault();
			
			//Das Objekt, das für die Interpretation der Argumente sorgt.Falls Argument werte vorhanden sind "Werden sie automatisch sofort geladen".
			this.objOpt = new GetOptZZZ(sPattern, saArg);
			
			bReturn = true;
		}
		return bReturn;
	}
	

	public String expressionSolveConfigDirectoryNameDefault(String sDirectoryNameReadIn) {
		String sReturn=null;
		main:{		
		boolean bUseFormula = this.getFlagZ("useFormula");
		String sDirectoryNameRead = sDirectoryNameReadIn;//z.B. auch "<z:Null/>"
		//20191031: Dieser Wert muss vom Programm verarbeitet/Übersetzt werden werden - wie ein ini-Datei Eintrag auch übersetzt würde.
		//return "<z:Null/>";//Merke '.' oder Leerstring '' = src Verzeichnis
		ReferenceZZZ<String> objsReturnValueExpressionSolved= new ReferenceZZZ<String>();
		ReferenceZZZ<String> objsReturnValueConverted= new ReferenceZZZ<String>();
		ReferenceZZZ<String> objsReturnValue= new ReferenceZZZ<String>();
		try {
			//TODO: Wenn das klappt eine statische Methode anbieten, bei der alle null-Parameter weggelassen werden können.
			int iConvertionType = KernelConfigEntryUtilZZZ.getValueExpressionSolvedAndConverted((FileIniZZZ) null, sDirectoryNameRead, bUseFormula, (HashMapCaseInsensitiveZZZ<String,String>) null, (String[]) null, objsReturnValueExpressionSolved, objsReturnValueConverted, objsReturnValue);
			sReturn = objsReturnValue.get();
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
	
	public String readConfigDirectoryName(){
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
	
	
	/** Heuristische Lösung. 
	 *  Funktioniert so im Vergleich "Webservice" vs. "Swing Standalone in Eclipse"
	 * @return
	 * @author lindhaueradmin, 07.11.2018, 07:26:27
	 */
	public boolean isOnServer(){
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
	public boolean isInJar() throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			bReturn = JarEasyUtilZZZ.isInJar(this.getClass());
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

	//##########
	// Getter / Setter
	//##########
	public GetOptZZZ getOptObject(){
		return this.objOpt;
	}
	
}
