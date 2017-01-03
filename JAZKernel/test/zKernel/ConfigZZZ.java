package zKernel;

import basic.zBasic.ExceptionZZZ;
import basic.zKernel.KernelConfigZZZ;

public class ConfigZZZ extends KernelConfigZZZ {

	public ConfigZZZ(String[] saArg) throws ExceptionZZZ {
		super(saArg);
	}

	public String getApplicationKeyDefault() {
		return "TEST";
	}

	public String getConfigDirectoryNameDefault() {
		return "";
	}

	public String getConfigFileNameDefault() {
		return "ZKernelConfigKernel_test.ini";
	}

	public String getPatternStringDefault() {
		return "k:s:f:d:";
	}

	public String getSystemNumberDefault() {
		return "01";
	}
}
