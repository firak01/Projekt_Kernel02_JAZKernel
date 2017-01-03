package basic.zKernel;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IObjectZZZ;
import basic.zBasic.ObjectZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;

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
		
		//Das Objekt, das für die Interpretation der Argumente sorgt.Falls Argument werte vorhanden sind "Werden sie automatisch sofort geladen".
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
		String sPattern = this.readPatternString();
		
		//Das Objekt, das für die Interpretation der Argumente sorgt.Falls Argument werte vorhanden sind "Werden sie automatisch sofort geladen".
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
		if(StringZZZ.isEmpty(sReturn)) sReturn = this.getPatternStringDefault();
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
		if(StringZZZ.isEmpty(sReturn)) sReturn = this.getApplicationKeyDefault();
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
		if(StringZZZ.isEmpty(sReturn)) sReturn = this.getSystemNumberDefault();
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
		if(StringZZZ.isEmpty(sReturn)) sReturn = this.getConfigDirectoryNameDefault();
		
//		!!! Falls es ein Leerstring ist, dann wird das aktuelle Verzeichnis verwendet. Hier noch mal sicherheitshalber eingebaut.
		if(StringZZZ.isEmpty(sReturn)) sReturn = ".";
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
		if(StringZZZ.isEmpty(sReturn)) sReturn = this.getConfigFileNameDefault();
		return sReturn;
	}
	
	
	/**Intern wird ein GetOptZZZ-Objekt verwendet, um die Argumente auszuwerten.
	 *  Hier wird das "isLoaded" - Flag dieses Objekts ausgewertet. 
	 *  Falls z.B. keine Argumente übergeben worden sind, oder die Argumente fehlerhaft sind, dann sollte dieses Flag nicht gesetzt sein.
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
	public abstract String getApplicationKeyDefault();
	public abstract String getConfigDirectoryNameDefault();
	public abstract String getConfigFileNameDefault();
	public abstract String getPatternStringDefault();
	public abstract String getSystemNumberDefault();	
}
