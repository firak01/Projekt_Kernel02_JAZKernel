package basic.zBasic.util.counter;

import basic.zBasic.ExceptionZZZ;

public interface ICounterAlphanumericSignificantZZZ <T extends ICounterStrategyAlphanumericSignificantZZZ> extends ICounterCaseSensitiveZZZ, ICounterAlphanumericSignificantConstantsZZZ{
//20190521: Der Name war public interface ICounterStringStrategyAlphanumericUserZZZ extends ICounterStringZZZ{
//          Damit ist der Name zwar zutreffend, aber viel zu lang. Da das Interface verwendet wird, um ein entsprechendes Counterobject zu erstellen.... kürzen
	
	
	
	//20190606: Merke: Wenn man das in den abstrakten Klassen hält, bekommt man 2 objCounterStrategy - Objekte,
	//darum diese Strategy-Objekte nur in den "Endklassen" verwenden.
	//Jetzt durch eine Generic Lösung in ICounterStringZZZ ersetzt
	//public ICounterStrategyAlphanumericSignificantZZZ getCounterStrategyObject();
	//public void setCounterStrategyObject(ICounterStrategyAlphanumericSignificantZZZ objCounterStrategy);

	//Die gleichen Methoden gibt es im ICounterStringZZZ Interface
	public void setCounterStrategyObject(T objCounterStrategy) throws ExceptionZZZ;
	public T getCounterStrategyObject() throws ExceptionZZZ;

	
	
	
	//Entsprechend in ICounterStrategyAlphanumeric
	public int getCounterLength() throws ExceptionZZZ;
	public void setCounterLength(int iLength) throws ExceptionZZZ;
	
	public boolean hasCounterFilling() throws ExceptionZZZ;
	public char getCounterFilling() throws ExceptionZZZ;
	public void setCounterFilling(char cFilling) throws ExceptionZZZ;
	public void setCounterFilling(Character charFilling) throws ExceptionZZZ;
		
}
