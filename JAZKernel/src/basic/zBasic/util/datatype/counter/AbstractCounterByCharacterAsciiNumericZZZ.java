package basic.zBasic.util.datatype.counter;

import basic.zBasic.ExceptionZZZ;

public abstract class AbstractCounterByCharacterAsciiNumericZZZ <T extends ICounterStrategyNumericZZZ>   extends AbstractCounterByCharacterAsciiZZZ implements ICounterNumericZZZ{
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
		super(CounterByCharacterAscii_AlphanumericZZZ.iPOSITION_MIN);
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
	
	// ++++ Aus Interface
//		public ICounterStrategyAlphanumericZZZ getCounterStrategyObject(){
//			if(this.objCounterStrategy==null){
//				ICounterStrategyAlphanumericZZZ objCounterStrategy = new CounterStrategyNumericZZZ();//!!!!!!!!!!!
//				this.objCounterStrategy = objCounterStrategy;
//			}
//			return this.objCounterStrategy;
//		}
//		public void setCounterStrategyObject(ICounterStrategyNumericZZZ objCounterStrategy){
//			this.objCounterStrategy = objCounterStrategy;
//		}
		
		@Override
		public abstract void setCounterStrategyObject(ICounterStrategyNumericZZZ objCounterStrategy);
		
		//@Override
		//public abstract void setCounterStrategyObject(T objCounterStrategy);
		
		@Override	
		public abstract T getCounterStrategyObject() throws ExceptionZZZ;
}
