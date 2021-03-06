package basic.zBasic.util.counter;

import basic.zBasic.ExceptionZZZ;

public abstract class AbstractCounterByCharacterAsciiAlphanumericZZZ <T extends ICounterStrategyAlphanumericZZZ>  extends AbstractCounterByCharacterAsciiZZZ implements ICounterAlphanumericZZZ, ICounterCaseSensitiveZZZ{
	
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
		super(CounterByCharacterAscii_AlphanumericZZZ.iPOSITION_MIN);
		this.setCounterStrategyObject(objCounterStrategy);
	}
	public AbstractCounterByCharacterAsciiAlphanumericZZZ(String sStartingValue) throws ExceptionZZZ{
		//Das Problem ist, dass der Integer Wert nur über eine CounterStrategy errechnet wird.
		//Die Counter Strategy ist aber auf der hierüber liegenden "Vererbungsebene" der Abstrakten Klassen nicht vorhanden!!!
		//Darum kann man nicht einfach den Konstruktor der Elternklasse mit super(...) aufrufen. 
		boolean bSuccess = this.AbstractCounterByCharacterAsciiAlphanumericZZZ_(sStartingValue, null);		
	}
	public AbstractCounterByCharacterAsciiAlphanumericZZZ(String sStartingValue, ICounterStrategyAlphanumericZZZ objCounterStrategy) throws ExceptionZZZ{
		//Das Problem ist, dass der Integer Wert nur über eine CounterStrategy errechnet wird.
		//Die Counter Strategy ist aber auf der hierüber liegenden "Vererbungsebene" der Abstrakten Klassen nicht vorhanden!!!
		//Darum kann man nicht einfach den Konstruktor der Elternklasse mit super(...) aufrufen. 
		boolean bSuccess = this.AbstractCounterByCharacterAsciiAlphanumericZZZ_(sStartingValue, objCounterStrategy);				
	}
	
	private boolean AbstractCounterByCharacterAsciiAlphanumericZZZ_(String sStartingValue, ICounterStrategyAlphanumericZZZ objCounterStrategy) throws ExceptionZZZ{
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
	
	
	
	// ++++ Aus Interface		
	@Override
	public boolean isLowercase() throws ExceptionZZZ {
		return this.getCounterStrategyObject().isLowercase();
	}

	@Override
	public void isLowercase(boolean bValue) throws ExceptionZZZ {
		this.getCounterStrategyObject().isLowercase(bValue);
	}
	
	@Override
	public String preValueSetting(String sValue) throws ExceptionZZZ {
		//Da das normalerweise nicht verwendet wird einfach den Eingabewert zurückgeben. Wenn es gebraucht wird, die Methode überschreiben.
		return sValue;
	}
	@Override
	public String postValueSetting(String sValue) throws ExceptionZZZ {
		//Da das normalerweise nicht verwendet wird einfach den Eingabewert zurückgeben. Wenn es gebraucht wird, die Methode überschreiben.
		return sValue;
	}
	
	@Override
	public abstract void setValueCurrent(String sValue) throws ExceptionZZZ ;
	@Override
	public abstract String peekChange(int iValue) throws ExceptionZZZ;
	
//	@Override
//	public int getCharacterPositionMax() {
//		return CounterByCharacterAscii_AlphanumericZZZ.iPOSITION_MAX;
//	}
//	
//	@Override
//	public int getCharacterPositionMin() {
//		return CounterByCharacterAscii_AlphanumericZZZ.iPOSITION_MIN;
//	}
	
	@Override
	public int getDigitValueMin(){
		return ICounterAlphanumericConstantsZZZ.iPOSITION_MIN-1;
	}
	
	@Override
	public int getDigitValueMax(){
		return ICounterAlphanumericConstantsZZZ.iPOSITION_MAX-1;
	}
	
	
	// ++++ Aus Interface	
	@Override
	public abstract void setCounterStrategyObject(ICounterStrategyAlphanumericZZZ objCounterStrategy);
	
	@Override	
	public abstract T getCounterStrategyObject() throws ExceptionZZZ;
	
}

