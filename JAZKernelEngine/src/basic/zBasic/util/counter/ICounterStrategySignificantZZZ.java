package basic.zBasic.util.counter;

import basic.zBasic.ExceptionZZZ;

public interface ICounterStrategySignificantZZZ {		
	public int getCounterLength() throws ExceptionZZZ;
	public void setCounterLength(int iLength) throws ExceptionZZZ;
	
	public boolean hasCounterFilling() throws ExceptionZZZ;
	public char getCounterFilling() throws ExceptionZZZ;
	public void setCounterFilling(char cFilling) throws ExceptionZZZ;
	public void setCounterFilling(Character charFilling) throws ExceptionZZZ;
	public void setCounterFilling(String sFilling) throws ExceptionZZZ;
	
	public String getCounterStringNormed(String sCounterString) throws ExceptionZZZ;//Entferne führende Füllzeichen. Das kann man nur wg. des Stellenwerts (significant) machen.
}
