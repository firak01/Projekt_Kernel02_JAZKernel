package basic.zBasic.util.datatype.counter;

import basic.zBasic.ExceptionZZZ;

public abstract class AbstractCounterByCharacterAsciiAlphanumericZZZ extends AbstractCounterByCharacterAsciiZZZ implements ICounterAlphanumericZZZ{
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
	
	@Override
	public void setValueCurrent(String sValue) throws ExceptionZZZ {
		// TODO Auto-generated method stub
		
	}
	@Override
	public String peekChange(int iValue) throws ExceptionZZZ {
		// TODO Auto-generated method stub
		return null;
	}	
	

}
