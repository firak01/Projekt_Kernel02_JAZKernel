package basic.zKernel;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IObjectZZZ;
import basic.zBasic.ObjectZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.file.FileEasyZZZ;

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
public abstract class KernelConfigZZZ extends ObjectZZZ implements IObjectZZZ, IKernelConfigZZZ{
	private GetOptZZZ objOpt = null;
	private String sDirectory = null;
	private String sFile = null;
	private String sApplicationKey = null;
	private String sSystemNumber= null;
	private String sPatternString = null;
	 
	public KernelConfigZZZ() throws ExceptionZZZ{
		String sPattern = this.getPatternStringDefault();
		String[] saArg = new String[8];
		saArg[0] = "-k";
		saArg[1] = this.getApplicationKeyDefault();
		saArg[2] = "-s";
		saArg[3] = this.getSystemNumberDefault();
		saArg[4] = "-f";
		saArg[5] = this.getConfigFileNameDefault();
		saArg[6] = "-d";
		saArg[7] = this.getConfigDirectoryNameDefault();
		
		//Das Objekt, das f�r die Interpretation der Argumente sorgt.Falls Argument werte vorhanden sind "Werden sie automatisch sofort geladen".
		this.objOpt = new GetOptZZZ(sPattern, saArg);
	}
	public KernelConfigZZZ(String[] saArg) throws ExceptionZZZ{
	 
		
		//Das Argument-Array darf auch leer sein.
		/*
		if(saArg==null){
			ExceptionZZZ ez = new ExceptionZZZ("Argument - Array", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
			throw ez;
		}
		 */
		
		//Nun den konfigurierten String holen
		String sPattern = this.getPatternStringDefault();
		
		//Das Objekt, das f�r die Interpretation der Argumente sorgt.Falls Argument werte vorhanden sind "Werden sie automatisch sofort geladen".
		this.objOpt = new GetOptZZZ(sPattern, saArg);
	}
	

	public String readPatternString(){
		String sReturn = null;
		main:{
			GetOptZZZ objOpt = this.getOptObject();
			if(objOpt==null) break main;
			if(objOpt.getFlag("isLoaded")==false) break main;
			
			sReturn = objOpt.getPattern();
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
		}//end main:
		
//		!!! Falls es ein Leerstring ist, dann wird das aktuelle Verzeichnis verwendet.
//		if(StringZZZ.isEmpty(sReturn)) sReturn = "."; //Merke: Bei der Suche nach der Datei dann geändert in: KernelKernelZZZ.sDIRECTORY_CONFIG_SOURCEFOLDER; //Merke: Damit ist diese Datei auch auf dem WebServer findbar.
		return sReturn;
	} 
	  
	public String readConfigFileName(){
		String sReturn = null;
		main:{
			GetOptZZZ objOpt = this.getOptObject();
			if(objOpt==null) break main;
			if(objOpt.getFlag("isLoaded")==false) break main;
			 
			sReturn = objOpt.readValue("f");
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
	
	//#########
	//Aus Interface
	//##########
	public String getApplicationKeyDefault(){
		if(this.sApplicationKey==null){
			this.sApplicationKey = this.readApplicationKey();			
		}
		return this.sApplicationKey;
	};
	
	public String getConfigDirectoryNameDefault(){
		if(this.sDirectory==null){
			this.sDirectory = this.readConfigDirectoryName();
		}
		return this.sDirectory;
	}
	
	
	public String getConfigFileNameDefault(){
		if(this.sFile==null){
			this.sFile=this.readConfigFileName();
		}
		return this.sFile;
	}

	public String getSystemNumberDefault(){
		if(this.sSystemNumber==null){
			this.sSystemNumber=this.readSystemNumber();
		}
		return this.sSystemNumber;
	}
	
	public String getPatternStringDefault(){
		if(this.sPatternString==null){
			this.sPatternString=this.readPatternString();
		}
		return this.sPatternString;
	}
	
}
