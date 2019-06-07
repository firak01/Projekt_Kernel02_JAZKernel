package basic.zBasic.util.datatype.counter;

public interface ICounterAlphanumericZZZ extends ICounterStringZZZ{
//20190521: Der Name war public interface ICounterStringStrategyAlphanumericUserZZZ extends ICounterStringZZZ{
//          Damit ist der Name zwar zutreffend, aber viel zu lang. Da das Interface verwendet wird, um ein entsprechendes Counterobject zu erstellen.... kürzen
	public ICounterStrategyAlphanumericZZZ getCounterStrategyObject();
	public void setCounterStrategyObject(ICounterStrategyAlphanumericZZZ objCounterStrategy);		
}
