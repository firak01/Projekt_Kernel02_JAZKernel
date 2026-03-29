package basic.zBasic.util.counter;

import basic.zBasic.ExceptionZZZ;

public abstract class AbstractCounterByCharacterAsciiNumericZZZ <T extends ICounterStrategyNumericZZZ>   extends AbstractCounterByCharacterAsciiZZZ implements ICounterNumericZZZ{
	//20190606: Merke: Wenn man das in den abstrakten Klassen hält, bekommt man 2 objCounterStrategy - Objekte,
	//darum diese Strategy-Objekte nur in den "Endklassen" verwenden. 
	//private ICounterStrategyNumericZZZ objCounterStrategy;
	
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
		super(CounterByCharacterAscii_NumericZZZ.iPOSITION_MIN-1);
		this.setCounterStrategyObject(objCounterStrategy);
	}
	public AbstractCounterByCharacterAsciiNumericZZZ(String sStartingValue) throws ExceptionZZZ{
		//Das Problem ist, dass der Integer Wert nur über eine CounterStrategy errechnet wird.
				//Die Counter Strategy ist aber auf der hierüber liegenden "Vererbungsebene" der Abstrakten Klassen nicht vorhanden!!!
				//Darum kann man nicht einfach den Konstruktor der Elternklasse mit super(...) aufrufen. 
				boolean bSuccess = this.AbstractCounterByCharacterAsciiNumericZZZ_(sStartingValue, null);
	}
	public AbstractCounterByCharacterAsciiNumericZZZ(String sStartingValue,ICounterStrategyNumericZZZ objCounterStrategy) throws ExceptionZZZ{
		//Das Problem ist, dass der Integer Wert nur über eine CounterStrategy errechnet wird.
				//Die Counter Strategy ist aber auf der hierüber liegenden "Vererbungsebene" der Abstrakten Klassen nicht vorhanden!!!
				//Darum kann man nicht einfach den Konstruktor der Elternklasse mit super(...) aufrufen. 
				boolean bSuccess = this.AbstractCounterByCharacterAsciiNumericZZZ_(sStartingValue, objCounterStrategy);
	}
		
	private boolean AbstractCounterByCharacterAsciiNumericZZZ_(String sStartingValue, ICounterStrategyNumericZZZ objCounterStrategy) throws ExceptionZZZ{
		boolean bReturn=false;
		main:{
			if(objCounterStrategy==null){
				objCounterStrategy = this.getCounterStrategyObject();	
			}
			this.setCounterStrategyObject(objCounterStrategy);			
			this.setValueCurrent(sStartingValue);
			bReturn = true;
		}//end main:
		return bReturn;
	}
	
//	@Override
//	public int getCharacterPositionMax() {
//		return CounterByCharacterAscii_NumericZZZ.iPOSITION_MAX;
//	}
//	
//	@Override
//	public int getCharacterPositionMin() {
//		return CounterByCharacterAscii_NumericZZZ.iPOSITION_MIN;
//	}
	
	// ++++ Aus Interface		
		@Override
		public abstract void setCounterStrategyObject(ICounterStrategyNumericZZZ objCounterStrategy);
		
		@Override	
		public abstract T getCounterStrategyObject() throws ExceptionZZZ;
		
		@Override
		public int getDigitValueMax(){
			return CounterByCharacterAscii_NumericZZZ.iPOSITION_MAX - 1;
		}
		
		@Override 
		public int getDigitValueMin(){
			return CounterByCharacterAscii_NumericZZZ.iPOSITION_MIN - 1;
		}
}
