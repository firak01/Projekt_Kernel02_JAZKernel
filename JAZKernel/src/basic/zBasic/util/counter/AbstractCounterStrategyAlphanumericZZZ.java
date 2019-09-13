package basic.zBasic.util.counter;

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
	
	@Override
	public int getCharacterPositionMax() {
		return CounterByCharacterAscii_AlphanumericZZZ.iPOSITION_MAX;
	}
	
	@Override
	public int getCharacterPositionMin() {
		return CounterByCharacterAscii_AlphanumericZZZ.iPOSITION_MIN;
	}
	
	@Override
	public String getCharForPosition(int iPositionInMinMaxRange, boolean bLowercase) throws ExceptionZZZ{
		return CounterByCharacterAscii_AlphanumericZZZ.getStringForNumber(iPositionInMinMaxRange, bLowercase);
	}
	
	//Aus Interface ICounterStrategyAlphabet
	@Override
	public void setCounterStart(String sStart) throws ExceptionZZZ{			
		int iValue = CounterByCharacterAscii_AlphanumericZZZ.getNumberForString(sStart, this);
		this.setCounterStart(iValue);
	}	
}
