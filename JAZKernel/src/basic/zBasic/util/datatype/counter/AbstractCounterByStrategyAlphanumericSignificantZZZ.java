package basic.zBasic.util.datatype.counter;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IConstantZZZ;

//public abstract class AbstractCounterByStrategyAlphanumericSignificantZZZ extends CounterByCharacterAscii_AlphanumericZZZ implements ICounterStrategyAlphanumericSignificantZZZ{
public abstract class AbstractCounterByStrategyAlphanumericSignificantZZZ extends AbstractCounterByCharacterAsciiZZZ implements ICounterAlphanumericSignificantZZZ, ICounterCaseSensitiveZZZ{ 
	//Merke: Wenn man das in den jeweilgen abstrakten Klassen behält, bekommt man 2 objCounterStrategy - Objekte
	//private ICounterStrategyAlphanumericSignificantZZZ objCounterStrategy;	
		 
	public AbstractCounterByStrategyAlphanumericSignificantZZZ(boolean bLowercase){
		super();
		this.isLowercase(true);
	}	
	public AbstractCounterByStrategyAlphanumericSignificantZZZ(String sStartingValue) throws ExceptionZZZ{
		super(sStartingValue);		
	}	
	public AbstractCounterByStrategyAlphanumericSignificantZZZ(){
		super();
		this.setValueCurrent(CounterByCharacterAscii_AlphanumericZZZ.iPOSITION_MIN);
	}
	public AbstractCounterByStrategyAlphanumericSignificantZZZ(int iStartingValue){
		super(iStartingValue);		
	}
	public AbstractCounterByStrategyAlphanumericSignificantZZZ(int iStartingValue, ICounterStrategyAlphanumericSignificantZZZ objCounterStrategy){
		super(iStartingValue);
		this.setCounterStrategyObject(objCounterStrategy);
	}
	public AbstractCounterByStrategyAlphanumericSignificantZZZ(ICounterStrategyAlphanumericSignificantZZZ objCounterStrategy){
		super(CounterByCharacterAscii_AlphanumericZZZ.iPOSITION_MIN);
		this.setCounterStrategyObject(objCounterStrategy);
	}	
	
	// ++++ Aus Interface		
	@Override
	public boolean isLowercase() {
		return this.getCounterStrategyObject().isLowercase();
	}

	@Override
	public void isLowercase(boolean bValue) {
		this.getCounterStrategyObject().isLowercase(bValue);
	}
	
	@Override
	public abstract void setValueCurrent(String sValue) throws ExceptionZZZ ;
	@Override
	public abstract String peekChange(int iValue) throws ExceptionZZZ;
	

}
