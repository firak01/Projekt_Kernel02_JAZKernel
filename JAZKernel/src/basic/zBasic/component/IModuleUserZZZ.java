package basic.zBasic.component;

import basic.zBasic.ExceptionZZZ;

public interface IModuleUserZZZ {
	public enum FLAGZ{
		ISMODULEUSER;
	}
	
	public String readModuleName() throws ExceptionZZZ;
	public String getModuleName() throws ExceptionZZZ;
	public void setModuleName(String sModuleName);
	public void resetModuleUsed();
	
	//damit muss man nicht mehr tippen hinter dem enum .name()
	public boolean getFlag(IModuleUserZZZ.FLAGZ objEnumFlag);
	public boolean setFlag(IModuleUserZZZ.FLAGZ objEnumFlag, boolean bFlagValue) throws ExceptionZZZ;
	public boolean[] setFlag(IModuleUserZZZ.FLAGZ[] objaEnumFlag, boolean bFlagValue) throws ExceptionZZZ;
	public boolean proofFlagExists(IModuleUserZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ;
	
	public IModuleZZZ getModule() throws ExceptionZZZ;
	public void setModule(IModuleZZZ objModule);
	
	boolean proofFlagSetBefore(FLAGZ objEnumFlag) throws ExceptionZZZ;	
}
