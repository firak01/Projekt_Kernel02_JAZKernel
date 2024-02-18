package basic.zBasic.component;

import basic.zBasic.ExceptionZZZ;

public interface IModuleUserZZZ {
	public String readModuleName() throws ExceptionZZZ;
	public String getModuleName() throws ExceptionZZZ;
	public void setModuleName(String sModuleName);
	public void resetModuleUsed();
	
	public IModuleZZZ getModule() throws ExceptionZZZ;
	public void setModule(IModuleZZZ objModule);
	

	//#############################################################
	//### FLAGZ
	//#############################################################	
	public enum FLAGZ{
		ISMODULEUSER;
	}
	
	//damit muss man nicht mehr tippen hinter dem enum .name()
	public boolean getFlag(FLAGZ objEnumFlag);
	public boolean setFlag(FLAGZ objEnumFlag, boolean bFlagValue) throws ExceptionZZZ;
	public boolean[] setFlag(FLAGZ[] objaEnumFlag, boolean bFlagValue) throws ExceptionZZZ;
	public boolean proofFlagExists(FLAGZ objEnumFlag) throws ExceptionZZZ;
	boolean proofFlagSetBefore(FLAGZ objEnumFlag) throws ExceptionZZZ;	
}
