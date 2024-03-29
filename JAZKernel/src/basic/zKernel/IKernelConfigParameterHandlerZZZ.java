package basic.zKernel;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.ImageIcon;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractList.HashMapIndexedZZZ;
import basic.zBasic.util.crypt.code.ICryptZZZ;
import custom.zKernel.file.ini.FileIniZZZ;

public interface IKernelConfigParameterHandlerZZZ extends IKernelConfigFileUserZZZ{
	public ArrayList<String> getModuleAll() throws ExceptionZZZ;
	
	public ArrayList<String> getProgramAliasUsed(FileIniZZZ objFileIniConfig, String sMainSection, String sProgramOrAlias, String sSystemNumber) throws ExceptionZZZ;
	public ArrayList<String> getProgramAliasUsed_DirectLookup(FileIniZZZ objFileIniConfig, String sMainSection, String sProgramOrAlias, String sSystemNumber) throws ExceptionZZZ;

	public IKernelConfigSectionEntryZZZ getParameter(String sParameterName) throws ExceptionZZZ;
	
	public IKernelConfigSectionEntryZZZ getParameterByProgramAlias(File objFileConfig, String sProgramOrSection, String sProperty) throws ExceptionZZZ;
	public IKernelConfigSectionEntryZZZ getParameterByProgramAlias(FileIniZZZ objFileIni, String sModuleAlias, String sProgramAlias, String sParameterName, boolean bUseCache ) throws ExceptionZZZ;
	public IKernelConfigSectionEntryZZZ getParameterByProgramAlias(FileIniZZZ objFileIni, String sModuleAlias, String sProgramAlias, String sParameterName ) throws ExceptionZZZ;
	public IKernelConfigSectionEntryZZZ getParameterByProgramAlias(FileIniZZZ objFileIni,String sProgramAlias,String sParameterName, boolean bUseCache) throws ExceptionZZZ;
	public IKernelConfigSectionEntryZZZ getParameterByProgramAlias(FileIniZZZ objFileIni,String sProgramAlias,String sParameterName) throws ExceptionZZZ;
	public IKernelConfigSectionEntryZZZ getParameterByProgramAlias(String sModuleAlias, String sProgramAlias, String sParameterName, boolean bUseCache) throws ExceptionZZZ;
	public IKernelConfigSectionEntryZZZ getParameterByProgramAlias(String sModuleAlias, String sProgramAlias, String sParameterName) throws ExceptionZZZ;
	public IKernelConfigSectionEntryZZZ getParameterByProgramAlias(String sProgramAlias, String sParameterName, boolean bUseCache) throws ExceptionZZZ;
	public IKernelConfigSectionEntryZZZ getParameterByProgramAlias(String sProgramAlias, String sParameterName) throws ExceptionZZZ;
		
	public IKernelConfigSectionEntryZZZ getParameterByModuleAlias(String sModuleAlias, String sParameterName) throws ExceptionZZZ;
	public IKernelConfigSectionEntryZZZ[] getParameterByModuleAliasAsArray(String sModule, String sProperty) throws ExceptionZZZ;
	
	public IKernelConfigSectionEntryZZZ getParameterByModuleFile(FileIniZZZ objFileIni, String sParameterName) throws ExceptionZZZ;
	public IKernelConfigSectionEntryZZZ getParameterByModuleFile(File objFileConfig, String sParameter) throws ExceptionZZZ;
	public IKernelConfigSectionEntryZZZ getParameterByModuleFile(File objFileConfig, String sModuleAlias, String sParameter) throws ExceptionZZZ;
	public IKernelConfigSectionEntryZZZ getParameterByModuleFile(FileIniZZZ objFileIniConfig, String sModuleAlias, String sParameter) throws ExceptionZZZ;
	
	public IKernelConfigSectionEntryZZZ[] getParameterArrayWithEntryByProgramAlias(String sModuleAlias, String sProgramAlias, String sParameterName) throws ExceptionZZZ;
	public IKernelConfigSectionEntryZZZ[] getParameterArrayWithEntryByProgramAlias(String sProgramAlias, String sParameterName) throws ExceptionZZZ;
	public String[] getParameterArrayWithStringByProgramAlias(String sModuleAlias, String sProgramAlias, String sParameterName) throws ExceptionZZZ;
	public String[] getParameterArrayWithStringByProgramAlias(String sProgramAlias, String sParameterName) throws ExceptionZZZ;
	
	public String[] getParameterArrayStringByModuleAlias(String sModule, String sProperty) throws ExceptionZZZ;
	
	public IKernelConfigSectionEntryZZZ getParameter4System(String sParameter) throws ExceptionZZZ;
		
	public Boolean getParameterBooleanByProgramAlias(String sModuleAlias, String sProgramAlias, String sParameterName) throws ExceptionZZZ;
	
	public HashMapIndexedZZZ<Integer,IKernelConfigSectionEntryZZZ> getParameterHashMapWithEntryByProgramAlias(String sModuleAlias, String sProgramAlias, String sParameterName) throws ExceptionZZZ;
	public HashMapIndexedZZZ<Integer, IKernelConfigSectionEntryZZZ> getParameterHashMapWithEntryByProgramAlias(String sProgramAlias, String sParameterName) throws ExceptionZZZ;	
	public HashMap<String,String> getParameterHashMapWithStringByProgramAlias(String sModuleAlias, String sProgramAlias, String sParameterName) throws ExceptionZZZ;
	public HashMap<String,String> getParameterHashMapWithStringByProgramAlias(String sProgramAlias, String sParameterName) throws ExceptionZZZ;
	
	
	public File getParameterFileByProgramAlias(String sModule, String sSectionOrProgram, String sProperty) throws ExceptionZZZ;
	public File getParameterFileByModuleAlias(String sModule, String sProperty) throws ExceptionZZZ;
	public ImageIcon getParameterImageIconByProgramAlias(String sModule, String sSectionOrProgram, String sProperty) throws ExceptionZZZ;
	public ImageIcon getParameterImageIconByModuleAlias(String sModule, String sProperty) throws ExceptionZZZ;
	
	
	
	public void setParameterByModuleAlias(String sModuleAlias, String sParameter, String sValue, boolean bSaveImmediate) throws ExceptionZZZ;
	
	public void setParameterByProgramAlias(File objFileConfig, String sProgramOrSection, String sProperty, String sValue, boolean bFlagSaveImmidiate) throws ExceptionZZZ;
	public void setParameterByProgramAlias(File objFileConfig, String sProgramOrSection, String sProperty, String sValue) throws ExceptionZZZ;
	public void setParameterByProgramAlias(FileIniZZZ objFileIni, String sSectionOrProgram, String sProperty, String sValue, boolean bSaveImmidiately) throws ExceptionZZZ;
	public void setParameterByProgramAlias(FileIniZZZ objFileIni, String sSectionOrProgram, String sProperty, String sValue) throws ExceptionZZZ;
	public void setParameterByProgramAlias(String sModuleAndProgramAndSection, String sProperty, String sValue) throws ExceptionZZZ;
	public void setParameterByProgramAlias(String sModule, String sSectionOrProgram, String sProperty, String sValue) throws ExceptionZZZ;	
	public void setParameterByProgramAlias(String sModule, String sSectionOrProgram, String sProperty, String sValue, boolean bSaveImmidiately) throws ExceptionZZZ;
	
	public void setParameterByProgramAliasEncrypted(String sSectionOrProgram, String sProperty, String sValue, ICryptZZZ objCrypt) throws ExceptionZZZ;
	
	//+++++++++++++++
	public boolean proofSectionIsConfigured(String sAlias) throws ExceptionZZZ;
	
}
