package debug.zBasic;

import basic.zBasic.ExceptionZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernel.component.AbstractKernelProgramZZZ;

public class DummyClassImplementingEnumByInterfaceOnly extends AbstractKernelProgramZZZ{

	public DummyClassImplementingEnumByInterfaceOnly() throws ExceptionZZZ {
		super();
	}
	
	public DummyClassImplementingEnumByInterfaceOnly(IKernelZZZ objKernel) throws ExceptionZZZ {
		super(objKernel);
	}

	//### Aus IResettableValues
	@Override
	public boolean reset() throws ExceptionZZZ{
		return super.reset();
	}
	
	@Override
	public boolean resetValues() throws ExceptionZZZ{
		return false;
	}
	
	/* (non-Javadoc)
	 * @see basic.zKernel.component.AbstractKernelProgramZZZ#getModuleName()
	 */
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
