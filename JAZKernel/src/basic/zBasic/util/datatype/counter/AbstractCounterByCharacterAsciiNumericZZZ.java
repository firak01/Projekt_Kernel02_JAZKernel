package basic.zBasic.util.datatype.counter;

import basic.zBasic.ExceptionZZZ;

public abstract class AbstractCounterByCharacterAsciiNumericZZZ extends AbstractCounterByCharacterAsciiZZZ implements ICounterStrategyNumericUserZZZ{
	private ICounterStrategyZZZ objCounterStrategy;
	
	public AbstractCounterByCharacterAsciiNumericZZZ(){
		super();
	}
	public AbstractCounterByCharacterAsciiNumericZZZ(int iStartingValue){
		super(iStartingValue);
	}
	public AbstractCounterByCharacterAsciiNumericZZZ(int iStartingValue, ICounterStrategyAlphanumericZZZ objCounterStrategy){
		super(iStartingValue);
		this.setCounterStrategyObject(objCounterStrategy);
	}
	public AbstractCounterByCharacterAsciiNumericZZZ(ICounterStrategyAlphanumericZZZ objCounterStrategy){
		super(CounterByCharacterAscii_AlphanumericZZZ.iALPHANUMERIC_POSITION_MIN);
		this.setCounterStrategyObject(objCounterStrategy);
	}
	
	
	
	// ++++ Aus Interface
	public ICounterStrategyZZZ getCounterStrategyObject(){
		if(this.objCounterStrategy==null){
			ICounterStrategyZZZ objCounterStrategy = new CounterStrategyNumericMultipleZZZ();
			this.objCounterStrategy = objCounterStrategy;
		}
		return this.objCounterStrategy;
	}
	public void setCounterStrategyObject(ICounterStrategyZZZ objCounterStrategy){
		this.objCounterStrategy = objCounterStrategy;
	}
}
