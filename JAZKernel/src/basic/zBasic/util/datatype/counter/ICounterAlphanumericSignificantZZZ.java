package basic.zBasic.util.datatype.counter;

import basic.zBasic.ExceptionZZZ;

public interface ICounterAlphanumericSignificantZZZ extends ICounterStringZZZ{
//20190521: Der Name war public interface ICounterStringStrategyAlphanumericUserZZZ extends ICounterStringZZZ{
//          Damit ist der Name zwar zutreffend, aber viel zu lang. Da das Interface verwendet wird, um ein entsprechendes Counterobject zu erstellen.... kürzen
	
	//20190606: Merke: Wenn man das in den abstrakten Klassen hält, bekommt man 2 objCounterStrategy - Objekte,
	//darum diese Strategy-Objekte nur in den "Endklassen" verwenden. 	
	public ICounterStrategyAlphanumericSignificantZZZ getCounterStrategyObject();
	public void setCounterStrategyObject(ICounterStrategyAlphanumericSignificantZZZ objCounterStrategy);
	
	//Entsprechend in ICounterStrategyAlphanumeric
	public int getCounterLength();
	public void setCounterLength(int iLength) throws ExceptionZZZ;
	
	public char getCounterFilling();
	public void setCounterFilling(char cFilling);
}
