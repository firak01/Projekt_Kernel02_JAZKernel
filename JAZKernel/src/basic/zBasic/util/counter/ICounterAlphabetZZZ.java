package basic.zBasic.util.counter;

import basic.zBasic.ExceptionZZZ;

public interface ICounterAlphabetZZZ  <T extends ICounterStrategyAlphabetZZZ>  extends ICounterCaseSensitiveZZZ,ICounterAlphabetConstantsZZZ{	
		
	//20190606: Merke: Wenn man das in den abstrakten Klassen hält, bekommt man 2 objCounterStrategy - Objekte,
	//darum diese Strategy-Objekte nur in den "Endklassen" verwenden. 
	//public ICounterStrategyAlphabetZZZ getCounterStrategyObject() throws ExceptionZZZ;
	//public void setCounterStrategyObject(ICounterStrategyAlphabetZZZ objCounterStrategy);	
	
	
	//20190606: Merke: Wenn man das in den abstrakten Klassen hält, bekommt man 2 objCounterStrategy - Objekte,
	//darum diese Strategy-Objekte nur in den "Endklassen" verwenden.
	//Jetzt durch eine Generic Lösung in ICounterStringZZZ ersetzt
	//public ICounterStrategyAlphanumericSignificantZZZ getCounterStrategyObject();
	//public void setCounterStrategyObject(ICounterStrategyAlphanumericSignificantZZZ objCounterStrategy);

	//Die gleichen Methoden gibt es im ICounterStringZZZ Interface
	public void setCounterStrategyObject(T objCounterStrategy);
	public T getCounterStrategyObject() throws ExceptionZZZ;

	public int getDigitValueMin();
	public int getDigitValueMax();
}
