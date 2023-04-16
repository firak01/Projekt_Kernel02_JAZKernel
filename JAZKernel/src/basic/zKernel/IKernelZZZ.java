package basic.zKernel;

import java.io.File;
import java.util.ArrayList;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractList.HashMapIndexedZZZ;
import basic.zBasic.util.crypt.code.ICryptZZZ;
import basic.zBasic.util.file.ini.IniFile;
import basic.zKernel.cache.IKernelCacheUserZZZ;
import basic.zKernel.file.ini.IKernelExpressionIniSolverZZZ;
import basic.zKernel.file.ini.IKernelFileIniZZZ;
import basic.zKernel.flag.IEventBrokerFlagZsetUserZZZ;
import basic.zKernel.flag.IFlagZUserZZZ;
import custom.zKernel.LogZZZ;
import custom.zKernel.file.ini.FileIniZZZ;

public interface IKernelZZZ extends IKernelConfigSectionEntryUserZZZ,IKernelCacheUserZZZ,IFlagZUserZZZ, IKernelExpressionIniSolverZZZ{
	//FLAGZ, die dann zum "Rechnen in der Konfigurations Ini Datei" gesetzt sein müssen.
	public enum FLAGZ{
		USEFORMULA, USEFORMULA_MATH;
	}
	//damit muss man nicht mehr tippen hinter dem enum .name()
	public boolean getFlag(IKernelZZZ.FLAGZ objEnumFlag);
	public boolean setFlag(IKernelZZZ.FLAGZ objEnumFlag, boolean bFlagValue) throws ExceptionZZZ;
	public boolean[] setFlag(IKernelZZZ.FLAGZ[] objEnumFlag, boolean bFlagValue) throws ExceptionZZZ;	
	public abstract boolean proofFlagExists(IKernelZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ;
	
	
	public IKernelConfigZZZ getConfigObject() throws ExceptionZZZ;	
	public LogZZZ getLogObject();
	
	public String getApplicationKey() throws ExceptionZZZ;
	public String getSystemKey() throws ExceptionZZZ;	
	public String getSystemNumber() throws ExceptionZZZ;
			
	public boolean isOnServer() throws ExceptionZZZ;
	public boolean isInJar() throws ExceptionZZZ;	
	
	//### häufig verwendete Methoden, z.B. auch in den JUnit Tests:

	public ArrayList<String> computeSystemSectionNamesForProgram(String sProgramName) throws ExceptionZZZ;
	
	public IniFile getFileConfigKernelAsIni() throws ExceptionZZZ;
	public File[]getFileConfigModuleAllByDir(String sDir) throws ExceptionZZZ;
	
	public FileIniZZZ searchModuleFileWithProgramAlias(String sModule, String sProgramOrSection) throws ExceptionZZZ;
	
	HashMapIndexedZZZ<Integer,IKernelConfigSectionEntryZZZ>getParameterHashMapEntryByProgramAlias(String sModule, String sKeyInHashMapForProgramOrSection, String sValueInHashMapForsProperty) throws ExceptionZZZ;
	
	public IKernelConfigSectionEntryZZZ getProgramAliasFor(String sModuleOrProgram) throws ExceptionZZZ;
	public IKernelConfigSectionEntryZZZ getSectionAliasFor(String sModuleOrProgramAsSection) throws ExceptionZZZ;
	
	public String searchAliasForModule(String sModule) throws ExceptionZZZ;
	
	public String searchAliasForProgram(String sProgramName) throws ExceptionZZZ;
	public String searchAliasForProgram(String sModule, String sProgramName) throws ExceptionZZZ;
	
	
	//Schreibe einen Wert mit weiteren Argumenten als <Z> - Expression. Der uebergebene Wert wird in der Methode erst noch verschluesselt. 
	public void setParameterByProgramAlias(String sSectionOrProgram, String sProperty,
			String sEncrypted, ICryptZZZ objCrypt) throws ExceptionZZZ;
	
	//Schreibe einen Wert mit weiteren Argumenten als <Z> - Expression. Der uebergebene Wert wird in der Methode erst noch verschluesselt. 
	public void setParameterByProgramAlias(String sModule, String sSectionOrProgram, String sProperty,
			String sEncrypted, ICryptZZZ objCrypt) throws ExceptionZZZ;
	
	//Schreibe einen Wert mit weiteren Argumenten als <Z> - Expression. Der übergebene Wert ist schon verschluesselt.
	public void setParameterByProgramAliasEncrypted(String sModule, String sSectionOrProgram, String sProperty,
			String sEncrypted, ICryptZZZ objCrypt) throws ExceptionZZZ;
	
	
}
