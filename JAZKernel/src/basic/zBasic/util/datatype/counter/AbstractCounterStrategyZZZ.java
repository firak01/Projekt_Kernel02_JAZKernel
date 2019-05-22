package basic.zBasic.util.datatype.counter;

import basic.zBasic.IConstantZZZ;

public abstract class AbstractCounterStrategyZZZ implements IConstantZZZ, ICounterStrategyZZZ{
	private boolean bRightAligned = false;

	//++++ Aus Interface
	@Override
	public boolean isRightAligned() {
		return this.bRightAligned;
	}

	@Override
	public void isRightAligned(boolean bValue) {
		this.bRightAligned = bValue;
	}
}
