package basic.zKernel;

import java.io.File;
import java.util.ArrayList;

import basic.zBasic.ExceptionZZZ;
import custom.zKernel.LogZZZ;
import custom.zKernel.file.ini.FileIniZZZ;

public interface IKernelZZZ{
	
	public File getFileConfigKernel() throws ExceptionZZZ;
	public FileIniZZZ getFileConfigIni() throws ExceptionZZZ;
	public FileIniZZZ getFileConfigIniByAlias(String sModule) throws ExceptionZZZ;
	public ArrayList getModuleFileAliasAll(boolean bOnlyConfigured, boolean bOnlyExisting) throws ExceptionZZZ;
	
	public IKernelConfigZZZ getConfigObject();
	
	public LogZZZ getLogObject();
	
	public String getApplicationKey();
	public String getSystemKey();	
	public String getSystemNumber();
	
	public boolean proofModuleFileIsConfigured(String sModule) throws ExceptionZZZ;
	public boolean proofModuleFileExists(String sModule) throws ExceptionZZZ;
	public ArrayList getModuleAll() throws ExceptionZZZ;
	
	public String getParameterByProgramAlias(FileIniZZZ objFileIni, String sModuleAlias, String sProgramAlias, String sParameterName ) throws ExceptionZZZ;
	public String getParameterByProgramAlias(FileIniZZZ objFileIni,String sProgramAlias,String sParameterName) throws ExceptionZZZ;
	public String getParameterByProgramAlias(String sModuleAlias, String sProgramAlias, String sParameterName) throws ExceptionZZZ;
	public String getParameterByProgramAlias(String sProgramAlias, String sParameterName) throws ExceptionZZZ;
	
	public String getParameterByModuleAlias(String sModuleAlias, String sParameterName) throws ExceptionZZZ;
	
	public String getParameterByModuleFile(FileIniZZZ objFileIni, String sParameterName) throws ExceptionZZZ;
	
	public String getParameter(String sParameterName) throws ExceptionZZZ;
	
}
