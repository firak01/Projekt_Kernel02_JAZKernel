package basic.zBasic.util.datatype.counter;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IConstantZZZ;
import basic.zBasic.ObjectReflectableZZZ;
import basic.zBasic.ReflectCodeKernelZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;

public abstract class AbstractCounterStrategyZZZ extends ObjectReflectableZZZ implements IConstantZZZ, ICounterStrategyZZZ{
	private boolean bRightAligned = false;
	private boolean bIncreasableInOtherMethod = true;
	
	public AbstractCounterStrategyZZZ() throws ExceptionZZZ{
		super();
		this.initClassMethodCallingString();		
	}
	
	//++++ Aus Interface
	@Override
	public boolean isRightAligned() {
		return this.bRightAligned;
	}
	@Override
	public void isRightAligned(boolean bValue) {
		this.bRightAligned = bValue;
	}
	
	
	@Override
	public boolean isIncreasableInOtherMethod(){
		return this.bIncreasableInOtherMethod;
	}
	@Override
	public void isIncreasableInOtherMethod(boolean bValue){
		this.bIncreasableInOtherMethod = bValue;
	}
	
	
}
