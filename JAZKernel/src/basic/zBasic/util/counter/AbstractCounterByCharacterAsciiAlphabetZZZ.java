package basic.zBasic.util.counter;

import basic.zBasic.ExceptionZZZ;

public abstract class AbstractCounterByCharacterAsciiAlphabetZZZ<T extends ICounterStrategyAlphabetZZZ> extends AbstractCounterByCharacterAsciiZZZ implements ICounterAlphabetZZZ, ICounterCaseSensitiveZZZ{
	//20190606: Merke: Wenn man das in den abstrakten Klassen hält, bekommt man 2 objCounterStrategy - Objekte,
	//darum diese Strategy-Objekte nur in den "Endklassen" verwenden. 
	//private ICounterStrategyAlphanumericZZZ objCounterStrategy;
	
	public AbstractCounterByCharacterAsciiAlphabetZZZ(){
		super();
	}
	public AbstractCounterByCharacterAsciiAlphabetZZZ(int iStartingValue){
		super(iStartingValue);
	}
	public AbstractCounterByCharacterAsciiAlphabetZZZ(String sStartingValue) throws ExceptionZZZ{
		//Das Problem ist, dass der Integer Wert nur über eine CounterStrategy errechnet wird.
		//Die Counter Strategy ist aber auf der hierüber liegenden "Vererbungsebene" der Abstrakten Klassen nicht vorhanden!!!
		//Darum kann man nicht einfach den Konstruktor der Elternklasse mit super(...) aufrufen. 
		boolean bSuccess = this.AbstractCounterByCharacterAsciiAlphabetZZZ_(sStartingValue, null);		
	}
	public AbstractCounterByCharacterAsciiAlphabetZZZ(String sStartingValue, ICounterStrategyAlphabetZZZ objCounterStrategy) throws ExceptionZZZ{
		//Das Problem ist, dass der Integer Wert nur über eine CounterStrategy errechnet wird.
		//Die Counter Strategy ist aber auf der hierüber liegenden "Vererbungsebene" der Abstrakten Klassen nicht vorhanden!!!
		//Darum kann man nicht einfach den Konstruktor der Elternklasse mit super(...) aufrufen. 
		boolean bSuccess = this.AbstractCounterByCharacterAsciiAlphabetZZZ_(sStartingValue, objCounterStrategy);				
	}
	public AbstractCounterByCharacterAsciiAlphabetZZZ(boolean bLowercase) throws ExceptionZZZ{
		this.isLowercase(bLowercase);
	}
	public AbstractCounterByCharacterAsciiAlphabetZZZ(int iStartingValue, boolean bLowercase) throws ExceptionZZZ{
		super(iStartingValue);
		this.isLowercase(bLowercase);
	}
	public AbstractCounterByCharacterAsciiAlphabetZZZ(int iStartingValue, ICounterStrategyAlphabetZZZ objCounterStrategy){
		super(iStartingValue);
		this.setCounterStrategyObject(objCounterStrategy);
	}
	public AbstractCounterByCharacterAsciiAlphabetZZZ(ICounterStrategyAlphabetZZZ objCounterStrategy){
		super(CounterByCharacterAscii_AlphabetZZZ.iPOSITION_MIN);
		this.setCounterStrategyObject(objCounterStrategy);
	}
	
	private boolean AbstractCounterByCharacterAsciiAlphabetZZZ_(String sStartingValue, ICounterStrategyAlphabetZZZ objCounterStrategy) throws ExceptionZZZ{
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
//		return CounterByCharacterAscii_AlphabetZZZ.iPOSITION_MAX;
//	}
//	
//	@Override
//	public int getCharacterPositionMin() {
//		return CounterByCharacterAscii_AlphabetZZZ.iPOSITION_MIN;
//	}
	
	// ++++ Aus Interface
	public abstract void setCounterStrategyObject(ICounterStrategyAlphabetZZZ objCounterStrategy);
		
	public abstract T getCounterStrategyObject() throws ExceptionZZZ;
	
	
	@Override
	public boolean isLowercase() throws ExceptionZZZ {
		return this.getCounterStrategyObject().isLowercase();
	}

	@Override
	public void isLowercase(boolean bValue) throws ExceptionZZZ {
		this.getCounterStrategyObject().isLowercase(bValue);
	}

}
