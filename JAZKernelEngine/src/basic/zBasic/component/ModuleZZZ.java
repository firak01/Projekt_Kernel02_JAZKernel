package basic.zBasic.component;

import basic.zBasic.ExceptionZZZ;

public class ModuleZZZ extends AbstractModuleZZZ{

	public ModuleZZZ(String sModule) throws ExceptionZZZ {
		super(sModule);		
	}
	
	@Override
	public void reset() {
		this.resetModuleUsed();
	}

	

}
