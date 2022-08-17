package basic.zKernel;

import java.io.File;
import java.util.ArrayList;

import basic.zBasic.ExceptionZZZ;
import custom.zKernel.file.ini.FileIniZZZ;

public interface IKernelConfigFileUserZZZ{
	public File getFileConfigKernel() throws ExceptionZZZ;
	public File getFileConfigByAlias(String sAlias) throws ExceptionZZZ;
	public FileIniZZZ getFileConfigIni() throws ExceptionZZZ;
	public FileIniZZZ getFileModuleIniByAlias(String sModule) throws ExceptionZZZ;
	public ArrayList<String> getModuleFileAliasAll(boolean bOnlyConfigured, boolean bOnlyExisting) throws ExceptionZZZ;
	
	public boolean proofModuleFileIsConfigured(String sModule) throws ExceptionZZZ;
	public boolean proofModuleFileExists(String sModule) throws ExceptionZZZ;
	
}
