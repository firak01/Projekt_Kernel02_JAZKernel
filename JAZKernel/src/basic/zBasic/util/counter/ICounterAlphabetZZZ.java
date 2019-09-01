package basic.zBasic.util.counter;

import basic.zBasic.ExceptionZZZ;

public interface ICounterAlphabetZZZ  <T extends ICounterStrategyAlphabetZZZ>  extends ICounterCaseSensitiveZZZ{	
	public static int iPOSITION_MIN=1; //Merke: Als Alphabetgrundlage wird hier der ASCII Satz gesehen
	public static int iPOSITION_MAX=26;
	
	public static String sREGEX_CHARACTERS="[a-zA-Z]";
	
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

	
}
