package basic.zBasic.util.datatype.counter;

public abstract class AbstractCounterByCharacterAsciiAlphabetZZZ extends AbstractCounterByCharacterAsciiZZZ implements ICounterAlphanumericZZZ{
	private ICounterStrategyAlphanumericZZZ objCounterStrategy;
	
	public AbstractCounterByCharacterAsciiAlphabetZZZ(){
		super();
	}
	public AbstractCounterByCharacterAsciiAlphabetZZZ(int iStartingValue){
		super(iStartingValue);
	}
	public AbstractCounterByCharacterAsciiAlphabetZZZ(boolean bLowercase){
		this.isLowercase(bLowercase);
	}
	public AbstractCounterByCharacterAsciiAlphabetZZZ(int iStartingValue, boolean bLowercase){
		super(iStartingValue);
		this.isLowercase(bLowercase);
	}
	
	
	
	// ++++ Aus Interface
	public ICounterStrategyAlphanumericZZZ getCounterStrategyObject(){
		if(this.objCounterStrategy==null){
			ICounterStrategyAlphanumericZZZ objCounterStrategy = new CounterStrategyAlphabetMultipleZZZ();//!!!!!!!!!!!
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
