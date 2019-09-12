package basic.zBasic.util.counter;

import basic.zBasic.ExceptionZZZ;

public interface ICounterNumericZZZ extends ICounterStringZZZ, ICounterNumericConstantsZZZ {
//20190521: Der Name war public interface ICounterStringStrategyNumericUserZZZ extends ICounterStringZZZ{
//          Damit ist der Name zwar zutreffend, aber viel zu lang. Da das Interface verwendet wird, um ein entsprechendes Counterobject zu erstellen.... kürzen
	
	public ICounterStrategyNumericZZZ getCounterStrategyObject() throws ExceptionZZZ;
	public void setCounterStrategyObject(ICounterStrategyNumericZZZ objCounterStrategy);
}
