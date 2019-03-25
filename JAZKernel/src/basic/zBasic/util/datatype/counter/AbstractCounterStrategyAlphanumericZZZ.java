package basic.zBasic.util.datatype.counter;

import basic.zBasic.IConstantZZZ;

public abstract class AbstractCounterStrategyAlphanumericZZZ extends AbstractCounterStrategyZZZ implements ICounterStrategyAlphanumericZZZ{
	private boolean bLowercase = false;
		
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
