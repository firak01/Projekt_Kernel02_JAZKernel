package basic.zKernel;

import static basic.zKernel.IKernelConfigConstantZZZ.sPATTERN_DEFAULT;
import static basic.zKernel.IKernelConfigConstantZZZ.sFLAGZ_DEFAULT;

import basic.zBasic.ExceptionZZZ;
import basic.zKernel.flag.IFlagZEnabledZZZ;

/** Interface, welches von KernelConfigZZZ etc. eingebunden wird, um die Default Werte festzulegen.
 * @author lindhauer
 *
 */
public interface IKernelConfigZZZ extends IFlagZEnabledZZZ,IKernelConfigProjectHelperZZZ, IKernelConfigConstantZZZ{
	
	/**Falls Kein entsprechender Parameter in der Kommandozeile übergeben worden ist, so wird der hier definierte Wert verwendet für den Initialisierung des Kernels
	* @return
	* 
	* lindhauer; 25.07.2007 07:20:25
	 */
	public String readApplicationKey();
	public String getApplicationKeyDefault();
	public String readSystemNumber();
	public String getSystemNumberDefault();
	public String readConfigDirectoryName() throws ExceptionZZZ;
	public String getConfigDirectoryNameDefault();
	public String readConfigFileName();
	public String getConfigFileNameDefault();
	
	public String getLogFileName() throws ExceptionZZZ;
	public String getLogFileNameDefault();
	public String readLogFileName() throws ExceptionZZZ;
	public boolean isLogFileNameDefault(String sValue);
	
	public String getLogDirectoryName() throws ExceptionZZZ;
	public String getLogDirectoryNameDefault();
	public String readLogDirectoryName() throws ExceptionZZZ;
	public boolean isLogDirectoryNameDefault(String sValue);
	
	
	//20210331: Flagz, default String, als leerer JSON-Wert
	public String getConfigFlagzJsonDefault();
		
	/** Die Argumente, die für diese Konfiguration erlaubt sind. Siehe dazu GetOptZZZ()
	* @return (z.B. "a:b:cde:", mit dem Doppelpunkt als Anzeichen dafür, das ein Parameter diesem Steuerungsargument folgt.)
	* 
	* lindhauer; 31.07.2007 06:24:53
	 */
	public String readPatternString();
	public String getPatternStringDefault();
	
	public boolean isOnServer() throws ExceptionZZZ;
	public boolean isInJar() throws ExceptionZZZ;
	public boolean isInIDE() throws ExceptionZZZ;
	public boolean isOptionObjectLoaded();
	
	public boolean isApplicationKeyDefault(String sApplicationKey);
	public boolean isSystemNumberDefault(String sSystemNumber);
	public boolean isPatternStringDefault(String sValue);
	public boolean isConfigFlagzJsonDefault(String sValue);
	public boolean isConfigFileNameDefault(String sValue);
	public boolean isConfigDirectoryNameDefault(String sValue);
	
}
