package basic.zBasic.util.datatype.counter;

import basic.zBasic.ExceptionZZZ;

public abstract class AbstractCounterByCharacterAsciiNumericZZZ extends AbstractCounterByCharacterAsciiZZZ implements ICounterStringStrategyNumericUserZZZ{
	private ICounterStrategyNumericZZZ objCounterStrategy;
	
	public AbstractCounterByCharacterAsciiNumericZZZ(){
		super();
	}
	public AbstractCounterByCharacterAsciiNumericZZZ(int iStartingValue){
		super(iStartingValue);
	}
	public AbstractCounterByCharacterAsciiNumericZZZ(int iStartingValue, ICounterStrategyNumericZZZ objCounterStrategy){
		super(iStartingValue);
		this.setCounterStrategyObject(objCounterStrategy);
	}
	public AbstractCounterByCharacterAsciiNumericZZZ(ICounterStrategyNumericZZZ objCounterStrategy){
		super(CounterByCharacterAscii_AlphanumericZZZ.iALPHANUMERIC_POSITION_MIN);
		this.setCounterStrategyObject(objCounterStrategy);
	}
	public AbstractCounterByCharacterAsciiNumericZZZ(String sStartingValue){
		super(sStartingValue);
	}
	
	
	// ++++ Aus Interface
	public ICounterStrategyNumericZZZ getCounterStrategyObject(){
		if(this.objCounterStrategy==null){
			ICounterStrategyNumericZZZ objCounterStrategy = new CounterStrategyNumericMultipleZZZ();
			this.objCounterStrategy = objCounterStrategy;
		}
		return this.objCounterStrategy;
	}
	public void setCounterStrategyObject(ICounterStrategyNumericZZZ objCounterStrategy){
		this.objCounterStrategy = objCounterStrategy;
	}
}
