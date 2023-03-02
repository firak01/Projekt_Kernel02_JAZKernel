package basic.zKernel.component;

import basic.zBasic.ExceptionZZZ;

public interface IKernelModuleUserZZZ {
	public enum FLAGZ{
		ISKERNELMODULEUSER;
	}
	//damit muss man nicht mehr tippen hinter dem enum .name()
	public boolean getFlag(IKernelModuleUserZZZ.FLAGZ objEnumFlag);
	public boolean setFlag(IKernelModuleUserZZZ.FLAGZ objEnumFlag, boolean bFlagValue);
	public boolean[] setFlag(IKernelModuleUserZZZ.FLAGZ[] objEnumFlag, boolean bFlagValue);
	
	
	public IKernelModuleZZZ getModule() throws ExceptionZZZ;
	public void setModule(IKernelModuleZZZ objModule);	
}
