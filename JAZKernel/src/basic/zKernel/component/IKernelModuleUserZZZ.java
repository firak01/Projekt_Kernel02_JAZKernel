package basic.zKernel.component;

import basic.zBasic.ExceptionZZZ;

public interface IKernelModuleUserZZZ {
	public enum FLAGZ{
		ISKERNELMODULEUSER;
	}
	public IKernelModuleZZZ getModule() throws ExceptionZZZ;
	public void setModule(IKernelModuleZZZ objModule);	
}
