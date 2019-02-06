package custom.zKernel;

import basic.zBasic.ExceptionZZZ;
import basic.zKernel.KernelConfigZZZ;


/**Klasse enth�lt die Werte, die im Kernel als default angesehen werden.
	 *- ApplicationKey: FGL
	 * - SystemNumber: 01
	 * - Verzeichnis: c:\\fglKernel\\KernelConfig
	 * - Datei:		ZKernelConfigKernel_default.ini
	
	Verwende eine eigene Klasse, die KernelConfigZZZ erweitert, um für eine Spezielles Projekt andere Werte zu verwenden.
 * @author lindhauer
 *
 */
public class ConfigZZZ extends KernelConfigZZZ{
	private static String sDIRECTORY_CONFIG_DEFAULT = "c:\\fglKernel\\KernelConfig";
	private static String sFILE_CONFIG_DEFAULT = "ZKernelConfigKernel_default.ini";
	private static String sKEY_APPLICATION_DEFAULT = "FGL";
	private static String sNUMBER_SYSTEM_DEFAULT= "01";
	private static String sPATTERN_DEFAULT="k:s:f:d:";
	
	public ConfigZZZ() throws ExceptionZZZ{
		super();
	}
	public ConfigZZZ(String[] saArg) throws ExceptionZZZ {
		super(saArg); 
	} 
			
	@Override
	public String getApplicationKeyDefault() {
		return ConfigZZZ.sKEY_APPLICATION_DEFAULT;
	}
	@Override
	public String getConfigDirectoryNameDefault() {
		return ConfigZZZ.sDIRECTORY_CONFIG_DEFAULT;
	}
	@Override
	public String getConfigFileNameDefault() {		
		return ConfigZZZ.sFILE_CONFIG_DEFAULT;
	}
	@Override
	public String getPatternStringDefault() {
		return ConfigZZZ.sPATTERN_DEFAULT;
	}
	@Override
	public String getSystemNumberDefault() {
		return ConfigZZZ.sNUMBER_SYSTEM_DEFAULT;
}


	

	

	

}
