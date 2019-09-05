package basic.zBasic.util.counter;

import basic.zBasic.ExceptionZZZ;

public interface ICounterStrategySignificantZZZ {		
	public int getCounterLength();
	public void setCounterLength(int iLength) throws ExceptionZZZ;
	
	public boolean hasCounterFilling();
	public char getCounterFilling();
	public void setCounterFilling(char cFilling);
	public void setCounterFilling(Character charFilling);
	public void setCounterFilling(String sFilling) throws ExceptionZZZ;
	
	public String getCounterStringNormed(String sCounterString);//Entferne führende Füllzeichen. Das kann man nur wg. des Stellenwerts (significant) machen.
}
