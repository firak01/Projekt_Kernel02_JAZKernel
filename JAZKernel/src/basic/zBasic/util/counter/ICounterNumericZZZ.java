package basic.zBasic.util.counter;

import basic.zBasic.ExceptionZZZ;

public interface ICounterNumericZZZ extends ICounterStringZZZ {
//20190521: Der Name war public interface ICounterStringStrategyNumericUserZZZ extends ICounterStringZZZ{
//          Damit ist der Name zwar zutreffend, aber viel zu lang. Da das Interface verwendet wird, um ein entsprechendes Counterobject zu erstellen.... kürzen
	
	public static int iPOSITION_MIN=1;  //Das Ziel sollte sein, dass iALPHABET_POSTION_MIN = 
	public static int iPOSITION_MAX=10;//die 10 Ziffern 0-9 dazu.
	
	public static String sREGEX_CHARACTERS="[0-9]";
	
	public ICounterStrategyNumericZZZ getCounterStrategyObject() throws ExceptionZZZ;
	public void setCounterStrategyObject(ICounterStrategyNumericZZZ objCounterStrategy);
}
