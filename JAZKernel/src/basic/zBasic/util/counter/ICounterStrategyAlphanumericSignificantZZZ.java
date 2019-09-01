package basic.zBasic.util.counter;

import basic.zBasic.ExceptionZZZ;

public interface ICounterStrategyAlphanumericSignificantZZZ extends ICounterStrategyAlphanumericZZZ {		
	public int getCounterLength();
	public void setCounterLength(int iLength) throws ExceptionZZZ;
	
	public boolean hasCounterFilling();
	public char getCounterFilling();
	public void setCounterFilling(char cFilling);
	public void setCounterFilling(Character charFilling);
	public void setCounterFilling(String sFilling) throws ExceptionZZZ;
}
