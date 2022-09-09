package basic.zKernel;

import java.io.File;
import java.util.ArrayList;

import basic.zBasic.ExceptionZZZ;
import custom.zKernel.file.ini.FileIniZZZ;

public interface IKernelConfigFileUserZZZ{
	public File getFileConfigKernel() throws ExceptionZZZ;
	public File getFileConfigModuleOLDDIRECT(String sAlias) throws ExceptionZZZ;
	public FileIniZZZ getFileConfigKernelIni() throws ExceptionZZZ;
	public FileIniZZZ getFileConfigModuleIni(String sModule) throws ExceptionZZZ;
	public ArrayList<String> getFileConfigModuleAliasAll(boolean bOnlyConfigured, boolean bOnlyExisting) throws ExceptionZZZ;
	
	public boolean proofFileConfigModuleIsConfigured(String sModule) throws ExceptionZZZ;
	public boolean proofFileConfigModuleExists(String sModule) throws ExceptionZZZ;
	
}
