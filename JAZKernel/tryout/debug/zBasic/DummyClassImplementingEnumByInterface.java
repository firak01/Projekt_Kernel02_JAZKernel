package debug.zBasic;

import basic.zBasic.ExceptionZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernel.component.AbstractKernelProgramZZZ;

public class DummyClassImplementingEnumByInterface extends AbstractKernelProgramZZZ{
	public enum FLAGZ{
		CLASSZZZ_DUMMYENUM01, CLASSZZZ_DUMMYENUM02; //Merke: DEBUG und INIT über IFlagZZZ eingebunden werden, weil von ObjectkZZZ kann man ja nicht erben. Es wird schon von anderer Objektklasse geerbt.
	}
	public DummyClassImplementingEnumByInterface() throws ExceptionZZZ {
		super();
	}
	
	public DummyClassImplementingEnumByInterface(IKernelZZZ objKernel) throws ExceptionZZZ {
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
