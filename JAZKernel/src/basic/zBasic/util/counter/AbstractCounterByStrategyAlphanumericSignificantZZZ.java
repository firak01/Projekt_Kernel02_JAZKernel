package basic.zBasic.util.counter;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IConstantZZZ;

//public abstract class AbstractCounterByStrategyAlphanumericSignificantZZZ extends CounterByCharacterAscii_AlphanumericZZZ implements ICounterStrategyAlphanumericSignificantZZZ{
public abstract class AbstractCounterByStrategyAlphanumericSignificantZZZ <T extends ICounterStrategyAlphanumericSignificantZZZ> extends AbstractCounterByCharacterAsciiZZZ implements ICounterAlphanumericSignificantZZZ, ICounterCaseSensitiveZZZ{ 
	//20190606: Merke: Wenn man das in den abstrakten Klassen hält, bekommt man 2 objCounterStrategy - Objekte,
	//darum diese Strategy-Objekte nur in den "Endklassen" verwenden. 
	//T objCounterStrategy;
	
	public AbstractCounterByStrategyAlphanumericSignificantZZZ(boolean bLowercase) throws ExceptionZZZ{
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
		
	@Override
	public boolean isLowercase() throws ExceptionZZZ {
		return this.getCounterStrategyObject().isLowercase();
	}

	@Override
	public void isLowercase(boolean bValue) throws ExceptionZZZ {
		this.getCounterStrategyObject().isLowercase(bValue);
	}

	
	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	//Normalerweise ist Eingabe = Ausgabe, aber bei den Signifikant-Counter ist eine führende 0 erlaubt, wenn eine Zählerlänge vorgegeben wurde.
	//Dann kann man beispielsweise diese führende 0 entfernen (vor der Verarbeitung) und wieder hinzufügen (bis zur Zählerlänge, nach der Verarbeitung)
		@Override
		public abstract String preValueSetting(String sValue) throws ExceptionZZZ;
			
		@Override
		public abstract String postValueSetting(String sValue) throws ExceptionZZZ;
		
		@Override
		public String peekChange(int iValue) throws ExceptionZZZ {	
			String sReturn = null;
			main:{
				T objCounterStrategy = (T) this.getCounterStrategyObject();
				sReturn = CounterByCharacterAscii_AlphanumericZZZ.getStringForNumber(iValue, objCounterStrategy);	
				
				//...Nachbearbeitung durchführen
				sReturn = this.postValueSetting(sReturn);
			}//end main:
			return sReturn;
		}
		
		@Override
		public void setValueCurrent(String sValue) throws ExceptionZZZ{			
			main:{
				//...Vorverarbeitug durchführen
				sValue = this.preValueSetting(sValue);
			
				T objCounterStrategy = (T) this.getCounterStrategyObject();
				int iValue = CounterByCharacterAscii_AlphanumericZZZ.getNumberForString(sValue, objCounterStrategy);
				this.setValueCurrent(iValue);
			}//end main:
		}	
		
		// ++++ Aus Interface						
		@Override
		public abstract void setCounterStrategyObject(ICounterStrategyAlphanumericSignificantZZZ objCounterStrategy);
		
		@Override	
		public abstract T getCounterStrategyObject() throws ExceptionZZZ;
}
