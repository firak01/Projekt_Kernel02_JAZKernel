package basic.zBasic.util.counter;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IConstantZZZ;

public abstract class AbstractCounterStrategyAlphabetZZZ extends AbstractCounterStrategyZZZ implements ICounterStrategyAlphabetZZZ{
	private boolean bLowercase = false;
	
	public AbstractCounterStrategyAlphabetZZZ() throws ExceptionZZZ{
		super();	
		this.initClassMethodCallingString();	
	}
	
	public AbstractCounterStrategyAlphabetZZZ(String sStartValue) throws ExceptionZZZ{
		super();				
		this.initClassMethodCallingString();	
		this.setCounterStart(sStartValue);
	}
	
	public AbstractCounterStrategyAlphabetZZZ(boolean bLowercase) throws ExceptionZZZ{
		super();		
		this.initClassMethodCallingString();	
		this.isLowercase(true);
	}
	
	public AbstractCounterStrategyAlphabetZZZ(String sStartValue, boolean bLowercase) throws ExceptionZZZ{
		super();				
		this.initClassMethodCallingString();	
		this.setCounterStart(sStartValue);
		this.isLowercase(bLowercase);
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
		return CounterByCharacterAscii_AlphabetZZZ.iPOSITION_MAX;
	}
	
	@Override
	public int getCharacterPositionMin() {
		return CounterByCharacterAscii_AlphabetZZZ.iPOSITION_MIN;
	}
	
	@Override
	public int getPositionValueForDigitValue(int iDigitValueInMinMaxRange){
		return iDigitValueInMinMaxRange + 1;
	}
	
	@Override
	public int getDigitValueForPositionValue(int iPositionInMinMaxRange){
		return iPositionInMinMaxRange - 1;
	}
	
	@Override
	public String getCharForPosition(int iPositionInMinMaxRange) throws ExceptionZZZ{
		boolean bLowercase = this.isLowercase();
		int iNumber = this.getDigitValueForPositionValue(iPositionInMinMaxRange);
		return CounterByCharacterAscii_AlphabetZZZ.getStringForNumber(iNumber, bLowercase);
	}
		
	//Aus Interface ICounterStrategyAlphabet
	@Override
	public void setCounterStart(String sStart) throws ExceptionZZZ{			
		int iValue = CounterByCharacterAscii_AlphabetZZZ.getNumberForString(sStart, this);
		this.setCounterStart(iValue);
	}		
}
