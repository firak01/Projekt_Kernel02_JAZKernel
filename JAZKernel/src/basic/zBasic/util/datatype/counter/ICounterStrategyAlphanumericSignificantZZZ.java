package basic.zBasic.util.datatype.counter;

import basic.zBasic.ExceptionZZZ;

public interface ICounterStrategyAlphanumericSignificantZZZ extends ICounterStrategyAlphanumericZZZ {	
	public int getCounterStart();
	public void setCounterStart(int iStart);
	public void setCounterStart(String sStart) throws ExceptionZZZ;
	
	public int getCounterLength();
	public void setCounterLength(int iLength) throws ExceptionZZZ;
	
	public boolean hasCounterFilling();
	public char getCounterFilling();
	public void setCounterFilling(char cFilling);
	public void setCounterFilling(Character charFilling);
	public void setCounterFilling(String sFilling) throws ExceptionZZZ;
}
