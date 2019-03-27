package basic.zBasic.util.datatype.counter;

public interface ICounterStrategyAlphanumericUserZZZ extends ICounterStringZZZ{
	public ICounterStrategyAlphanumericZZZ getCounterStrategyObject();
	public void setCounterStrategyObject(ICounterStrategyAlphanumericZZZ objCounterStrategy);
	
	//Entsprechend in ICounterStrategyAlphanumeric
	public boolean isLowercase();
	public void isLowercase(boolean bValue);
}
