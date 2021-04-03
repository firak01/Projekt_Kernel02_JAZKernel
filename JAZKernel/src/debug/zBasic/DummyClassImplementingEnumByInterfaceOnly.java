package debug.zBasic;

import basic.zBasic.ExceptionZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernel.component.AbstractKernelProgramZZZ;

public class DummyClassImplementingEnumByInterfaceOnly extends AbstractKernelProgramZZZ{

	public DummyClassImplementingEnumByInterfaceOnly() {
		super();
	}
	
	public DummyClassImplementingEnumByInterfaceOnly(IKernelZZZ objKernel) throws ExceptionZZZ {
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

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
	}

}
