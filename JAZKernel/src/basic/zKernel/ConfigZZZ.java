package basic.zKernel;

import basic.zBasic.ExceptionZZZ;
import basic.zKernel.AbstractKernelConfigZZZ;

/**
 *  Default Configuration of this project
 * 
 * @author lindhaueradmin, 20.10.2018, 06:32:14
 * 
 */
@SuppressWarnings("unchecked")
public class ConfigZZZ extends AbstractKernelConfigZZZ{
	private static String sPROJECT_NAME = "JAZKernel";
	private static String sPROJECT_PATH = "Project_Kernel02_JAZKernel";
	
	private static final long serialVersionUID = 1L;
	private static String sDIRECTORY_CONFIG_DEFAULT = "";
	private static String sFILE_CONFIG_DEFAULT = "ZKernelConfigKernel_default.ini";
	private static String sKEY_APPLICATION_DEFAULT = "ZZZ";
	private static String sNUMBER_SYSTEM_DEFAULT= "01";
	
	public ConfigZZZ() throws ExceptionZZZ{
		super();
	}
	public ConfigZZZ(String[] saArg) throws ExceptionZZZ{
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
	public String getSystemNumberDefault() {		
		return ConfigZZZ.sNUMBER_SYSTEM_DEFAULT;
	}
	
	@Override
	public String getPatternStringDefault() {
		return "k:s:f:d:";
	}
	@Override
	public String getProjectName() {
		return ConfigZZZ.sPROJECT_NAME;
	}
	@Override
	public String getProjectDirectory() {
		return ConfigZZZ.sPROJECT_PATH;
	}
}
