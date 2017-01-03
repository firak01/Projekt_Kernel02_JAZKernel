package basic.zKernel;

import basic.zBasic.IFunctionZZZ;

/** Interface, welches von KernelConfigZZZ etc. eingebunden wird, um die Default Werte festzulegen.
 * @author lindhauer
 *
 */
public interface IKernelConfigZZZ extends IFunctionZZZ{
	/**Falls Kein entsprechender Parameter in der Kommandozeile �bergeben worden ist, so wird der hier definierte Wert verwendet f�r den Initialisierung des Kernels
	* @return
	* 
	* lindhauer; 25.07.2007 07:20:25
	 */
	public String readApplicationKey();
	public String getApplicationKeyDefault();
	public String readSystemNumber();
	public String getSystemNumberDefault();
	public String readConfigDirectoryName();
	public String getConfigDirectoryNameDefault();
	public String readConfigFileName();
	public String getConfigFileNameDefault();
	
	/** Die Argumente, die f�r diese Konfiguration erlaubt sind. Siehe dazu GetOptZZZ()
	* @return (z.B. "a:b:cde:", mit dem Doppelpunkt als Anzeichen daf�r, das ein Parameter diesem Steuerungsargument folgt.)
	* 
	* lindhauer; 31.07.2007 06:24:53
	 */
	public String readPatternString();
	public String getPatternStringDefault();
	
	public boolean isOptionObjectLoaded();
	
}
