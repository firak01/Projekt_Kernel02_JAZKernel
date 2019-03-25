package basic.zBasic.util.datatype.counter;

public abstract class AbstractCounterByCharacterAsciiAlphanumericZZZ extends AbstractCounterByCharacterAsciiZZZ implements ICounterStrategyAlphanumericUserZZZ{
	private ICounterStrategyAlphanumericZZZ objCounterStrategy;
	
	public AbstractCounterByCharacterAsciiAlphanumericZZZ(){
		super();
	}
	public AbstractCounterByCharacterAsciiAlphanumericZZZ(int iStartingValue){
		super(iStartingValue);
	}
	public AbstractCounterByCharacterAsciiAlphanumericZZZ(int iStartingValue, ICounterStrategyAlphanumericZZZ objCounterStrategy){
		super(iStartingValue);
		this.setCounterStrategyObject(objCounterStrategy);
	}
	public AbstractCounterByCharacterAsciiAlphanumericZZZ(ICounterStrategyAlphanumericZZZ objCounterStrategy){
		super(CounterByCharacterAscii_AlphanumericZZZ.iALPHANUMERIC_POSITION_MIN);
		this.setCounterStrategyObject(objCounterStrategy);
	}
	
	
	
	// ++++ Aus Interface
	public ICounterStrategyAlphanumericZZZ getCounterStrategyObject(){
		if(this.objCounterStrategy==null){
			ICounterStrategyAlphanumericZZZ objCounterStrategy = new CounterStrategyAlphanumericMultipleZZZ();
			this.objCounterStrategy = objCounterStrategy;
		}
		return this.objCounterStrategy;
	}
	public void setCounterStrategyObject(ICounterStrategyAlphanumericZZZ objCounterStrategy){
		this.objCounterStrategy = objCounterStrategy;
	}
	
	@Override
	public boolean isLowercase() {
		return this.getCounterStrategyObject().isLowercase();
	}

	@Override
	public void isLowercase(boolean bValue) {
		this.getCounterStrategyObject().isLowercase(bValue);
	}

}
