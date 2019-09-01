package basic.zBasic.util.counter;

import basic.zBasic.ExceptionZZZ;

public interface ICounterStrategyAlphabetZZZ extends ICounterStrategyZZZ {
	public void setCounterStart(String sStart) throws ExceptionZZZ;
	
	public boolean isLowercase();
	public void isLowercase(boolean bValue);
}
