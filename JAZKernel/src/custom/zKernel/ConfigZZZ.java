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
	public ConfigZZZ() throws ExceptionZZZ{
		super();
	}
	public ConfigZZZ(String[] saArg) throws ExceptionZZZ {
		super(saArg); 
	} 
	
	public String getApplicationKeyDefault() {
		return "FGL"; 
	}

	public String getConfigDirectoryNameDefault() {
		return "c:\\fglKernel\\KernelConfig";
	}

	public String getConfigFileNameDefault() {
		return "ZKernelConfigKernel_default.ini";
	}

	public String getPatternStringDefault() {
		return "k:s:d:f:";
	}

	public String getSystemNumberDefault() {
		return "01";
	}


	

	

	

}
