package basic.zKernel;

import java.io.File;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import basic.zBasic.ExceptionZZZ;
import custom.zKernel.file.ini.FileIniZZZ;

public interface IKernelConfigSectionEntryUserZZZ extends IKernelConfigFileUserZZZ{
	public ArrayList<String> getModuleAll() throws ExceptionZZZ;
	
	public ArrayList<String> getProgramAliasUsed(FileIniZZZ objFileIniConfig, String sMainSection, String sProgramOrAlias, String sSystemNumber) throws ExceptionZZZ;
	
	public IKernelConfigSectionEntryZZZ getParameterByProgramAlias(FileIniZZZ objFileIni, String sModuleAlias, String sProgramAlias, String sParameterName ) throws ExceptionZZZ;
	public IKernelConfigSectionEntryZZZ getParameterByProgramAlias(FileIniZZZ objFileIni,String sProgramAlias,String sParameterName) throws ExceptionZZZ;
	public IKernelConfigSectionEntryZZZ getParameterByProgramAlias(String sModuleAlias, String sProgramAlias, String sParameterName) throws ExceptionZZZ;
	public IKernelConfigSectionEntryZZZ getParameterByProgramAlias(String sProgramAlias, String sParameterName) throws ExceptionZZZ;
	
	public IKernelConfigSectionEntryZZZ getParameterByModuleAlias(String sModuleAlias, String sParameterName) throws ExceptionZZZ;	
	public IKernelConfigSectionEntryZZZ getParameterByModuleFile(FileIniZZZ objFileIni, String sParameterName) throws ExceptionZZZ;
	public IKernelConfigSectionEntryZZZ getParameter(String sParameterName) throws ExceptionZZZ;
	
	public String[] getParameterArrayStringByProgramAlias(String sModuleAlias, String sProgramAlias, String sParameterName) throws ExceptionZZZ;
	
	
	public File getParameterFileByProgramAlias(String sModule, String sSectionOrProgram, String sProperty) throws ExceptionZZZ;
	public File getParameterFileByModuleAlias(String sModule, String sProperty) throws ExceptionZZZ;
	public ImageIcon getParameterImageIconByProgramAlias(String sModule, String sSectionOrProgram, String sProperty) throws ExceptionZZZ;
	public ImageIcon getParameterImageIconByModuleAlias(String sModule, String sProperty) throws ExceptionZZZ;
	
	
	
	
	public void setParameterByProgramAlias(String sModule, String sSectionOrProgram, String sProperty, String sValue) throws ExceptionZZZ;
}
