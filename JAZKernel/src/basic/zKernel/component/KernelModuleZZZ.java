package basic.zKernel.component;

import basic.zBasic.ExceptionZZZ;
import basic.zKernel.IKernelZZZ;

public class KernelModuleZZZ extends AbstractKernelModuleZZZ{

	public KernelModuleZZZ(IKernelZZZ objKernel, String sModule) throws ExceptionZZZ {
		super(objKernel, sModule);		
	}
	
	@Override
	public boolean reset() {
		this.resetModuleUsed();
		return true;
	}

}
