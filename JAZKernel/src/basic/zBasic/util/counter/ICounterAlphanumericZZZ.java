package basic.zBasic.util.counter;

import basic.zBasic.ExceptionZZZ;

public interface ICounterAlphanumericZZZ extends ICounterCaseSensitiveZZZ, ICounterAlphanumericConstantsZZZ{
//20190521: Der Name war public interface ICounterStringStrategyAlphanumericUserZZZ extends ICounterStringZZZ{
//          Damit ist der Name zwar zutreffend, aber viel zu lang. Da das Interface verwendet wird, um ein entsprechendes Counterobject zu erstellen.... kürzen

	
	//20190606: Merke: Wenn man das in den abstrakten Klassen hält, bekommt man 2 objCounterStrategy - Objekte,
	//darum diese Strategy-Objekte nur in den "Endklassen" verwenden. 
	public ICounterStrategyAlphanumericZZZ getCounterStrategyObject() throws ExceptionZZZ;
	public void setCounterStrategyObject(ICounterStrategyAlphanumericZZZ objCounterStrategy);		
}
