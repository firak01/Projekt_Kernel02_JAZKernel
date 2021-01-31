package debug.zBasic;

import basic.zBasic.ExceptionZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernel.module.AbstractKernelProgramZZZ;

public class DummyClassImplementingEnumByInterface extends AbstractKernelProgramZZZ{

	public DummyClassImplementingEnumByInterface() {
		super();
	}
	
	public DummyClassImplementingEnumByInterface(IKernelZZZ objKernel) {
		super(objKernel);
	}

	@Override
	public String getModuleName() throws ExceptionZZZ {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getProgramAlias() throws ExceptionZZZ {
		// TODO Auto-generated method stub
		return null;
	}

}
