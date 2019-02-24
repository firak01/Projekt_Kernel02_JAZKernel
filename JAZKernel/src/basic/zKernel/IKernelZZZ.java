package basic.zKernel;

import java.io.File;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import basic.zBasic.ExceptionZZZ;
import custom.zKernel.LogZZZ;
import custom.zKernel.file.ini.FileIniZZZ;

public interface IKernelZZZ{
	public File getFileConfigKernel() throws ExceptionZZZ;
	public File getFileConfigByAlias(String sAlias) throws ExceptionZZZ;
	public FileIniZZZ getFileConfigIni() throws ExceptionZZZ;
	public FileIniZZZ getFileConfigIniByAlias(String sModule) throws ExceptionZZZ;
	public ArrayList getModuleFileAliasAll(boolean bOnlyConfigured, boolean bOnlyExisting) throws ExceptionZZZ;
	
	public IKernelConfigZZZ getConfigObject() throws ExceptionZZZ;
	
	public LogZZZ getLogObject();
	
	public String getApplicationKey() throws ExceptionZZZ;
	public String getSystemKey() throws ExceptionZZZ;	
	public String getSystemNumber() throws ExceptionZZZ;
	
	public boolean proofModuleFileIsConfigured(String sModule) throws ExceptionZZZ;
	public boolean proofModuleFileExists(String sModule) throws ExceptionZZZ;
	public ArrayList<String> getModuleAll() throws ExceptionZZZ;
	public ArrayList<String> getProgramAliasUsed(FileIniZZZ objFileIniConfig, String sMainSection, String sProgramOrAlias, String sSystemNumber) throws ExceptionZZZ;
	
	public String getParameterByProgramAlias(FileIniZZZ objFileIni, String sModuleAlias, String sProgramAlias, String sParameterName ) throws ExceptionZZZ;
	public String getParameterByProgramAlias(FileIniZZZ objFileIni,String sProgramAlias,String sParameterName) throws ExceptionZZZ;
	public String getParameterByProgramAlias(String sModuleAlias, String sProgramAlias, String sParameterName) throws ExceptionZZZ;
	public String getParameterByProgramAlias(String sProgramAlias, String sParameterName) throws ExceptionZZZ;
	
	public String getParameterByModuleAlias(String sModuleAlias, String sParameterName) throws ExceptionZZZ;
	
	public String getParameterByModuleFile(FileIniZZZ objFileIni, String sParameterName) throws ExceptionZZZ;
	
	public String getParameter(String sParameterName) throws ExceptionZZZ;
	public File getParameterFileByProgramAlias(String sModule, String sSectionOrProgram, String sProperty) throws ExceptionZZZ;
	public File getParameterFileByModuleAlias(String sModule, String sProperty) throws ExceptionZZZ;
	public ImageIcon getParameterImageIconByProgramAlias(String sModule, String sSectionOrProgram, String sProperty) throws ExceptionZZZ;
	public ImageIcon getParameterImageIconByModuleAlias(String sModule, String sProperty) throws ExceptionZZZ;
	
	public boolean isOnServer() throws ExceptionZZZ;
	public boolean isInJar() throws ExceptionZZZ;
	
	
	public void setParameterByProgramAlias(String sModule, String sSectionOrProgram, String sProperty, String sValue) throws ExceptionZZZ;
}
