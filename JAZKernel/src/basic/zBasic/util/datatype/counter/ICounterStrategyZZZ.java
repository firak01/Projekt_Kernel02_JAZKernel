package basic.zBasic.util.datatype.counter;

import basic.zBasic.ExceptionZZZ;

public interface ICounterStrategyZZZ {
	public boolean checkSyntax(String sTotal) throws ExceptionZZZ;
	public int computeNumberForString(String sTotal);
	public String computeStringForNumber(int iNumber, boolean bLowercase); 
}
