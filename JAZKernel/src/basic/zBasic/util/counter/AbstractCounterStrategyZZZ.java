package basic.zBasic.util.counter;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IConstantZZZ;
import basic.zBasic.IObjectReflectableImplementerZZZ;
import basic.zBasic.ObjectReflectableZZZ;
import basic.zBasic.ReflectCodeKernelZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;

public abstract class AbstractCounterStrategyZZZ extends ObjectReflectableZZZ implements IObjectReflectableImplementerZZZ, ICounterStrategyZZZ{
	//TODO: Default Werte gehören eingentlich als Konstante ins Interface.
	int iStartDefault = 0;		
	private int iStart = -1;
	
	private boolean bRightAligned = false;
	private boolean bIncreasableInOtherMethod = false;
	
	public AbstractCounterStrategyZZZ() throws ExceptionZZZ{
		super();			
	}
	
	public AbstractCounterStrategyZZZ(int iStartValue) throws ExceptionZZZ{
		super();			
		this.setCounterStart(iStartValue);
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
	
	@Override
	public abstract boolean makeReflectableInitialization() throws ExceptionZZZ;
	
	//Aus Interface ICounterStrategy
	@Override
	public void setCounterStart(int iStart){
		this.iStart = iStart;
	}
				
	@Override
	public int getCounterStart(){
		if(this.iStart<=-1){
			this.iStart = this.iStartDefault;
		}
		return this.iStart;
	}
}
