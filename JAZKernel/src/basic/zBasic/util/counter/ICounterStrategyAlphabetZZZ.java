package basic.zBasic.util.counter;

import basic.zBasic.ExceptionZZZ;

public interface ICounterStrategyAlphabetZZZ extends ICounterStrategyZZZ {
	public void setCounterStart(String sStart) throws ExceptionZZZ;
	
	public boolean isLowercase();
	public void isLowercase(boolean bValue);
	
	//Entsprechende Konstante gibt es im jeweiligen Interface ICounter...ConstantsZZZ, z.B. ICounterAlphabetConstantsZZZ
	//In den Klassen, die ICounterStrategyZZZ implementieren, können nun die jeweils passenden Statischen Werte zurückgegeben werden.
	public String getCharForPosition(int iPositionInMinMaxRange, boolean bLowercase) throws ExceptionZZZ;
}
