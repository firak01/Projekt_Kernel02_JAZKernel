package basic.zBasic.util.datatype.counter;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IConstantZZZ;

public abstract class AbstractCounterStrategyAlphanumericZZZ extends AbstractCounterStrategyZZZ implements ICounterStrategyAlphanumericZZZ{
	private boolean bLowercase = false;
	public AbstractCounterStrategyAlphanumericZZZ() throws ExceptionZZZ{
		super();				
	}
	public AbstractCounterStrategyAlphanumericZZZ(boolean bLowercase) throws ExceptionZZZ{
		super();		
		this.initClassMethodCallingString();	
		this.isLowercase(true);
	}
	
	//++++ Aus Interface
	@Override
	public boolean isLowercase() {
		return this.bLowercase;
	}

	@Override
	public void isLowercase(boolean bValue) {
		this.bLowercase = bValue;
	}
}
