package basic.zBasic.util.datatype.counter;

public interface ICounterAlphanumericSignificantZZZ extends ICounterStringZZZ{
//20190521: Der Name war public interface ICounterStringStrategyAlphanumericUserZZZ extends ICounterStringZZZ{
//          Damit ist der Name zwar zutreffend, aber viel zu lang. Da das Interface verwendet wird, um ein entsprechendes Counterobject zu erstellen.... kürzen
	
	//20190606: Merke: Wenn man das darinbehält, bekommt man 2 objCounterStrategy - Objekte
	//private ICounterStrategyAlphanumericSignificantZZZ objCounterStrategy;	
	//TODO GOON: Und wenn man es verschiebt klappt es auch so?
//Lösung: Dies hier herausnehmen und die Methoden, welche diese Klasse nutzen mit einem CAST versehene.
	
	
	public ICounterStrategyAlphanumericSignificantZZZ getCounterStrategyObject();
	public void setCounterStrategyObject(ICounterStrategyAlphanumericSignificantZZZ objCounterStrategy);
	
	//Entsprechend in ICounterStrategyAlphanumeric
	public int getCounterLength();
	public void setCounterLength(int iLength);
	
	public char getCounterFilling();
	public void setCounterFilling(char cFilling);
}
