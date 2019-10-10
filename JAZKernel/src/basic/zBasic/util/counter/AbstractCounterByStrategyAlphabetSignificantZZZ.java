package basic.zBasic.util.counter;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IConstantZZZ;

//public abstract class AbstractCounterByStrategyAlphanumericSignificantZZZ extends CounterByCharacterAscii_AlphanumericZZZ implements ICounterStrategyAlphanumericSignificantZZZ{
public abstract class AbstractCounterByStrategyAlphabetSignificantZZZ <T extends ICounterStrategyAlphabetSignificantZZZ> extends AbstractCounterByCharacterAsciiZZZ implements ICounterAlphabetSignificantZZZ, ICounterCaseSensitiveZZZ{ 
	//20190606: Merke: Wenn man das in den abstrakten Klassen hält, bekommt man 2 objCounterStrategy - Objekte,
	//darum diese Strategy-Objekte nur in den "Endklassen" verwenden. 
	//T objCounterStrategy;
	
	public AbstractCounterByStrategyAlphabetSignificantZZZ(boolean bLowercase) throws ExceptionZZZ{
		super();
		this.isLowercase(true);
	}	
	public AbstractCounterByStrategyAlphabetSignificantZZZ(String sStartingValue) throws ExceptionZZZ{
		super(sStartingValue);		
	}	
	public AbstractCounterByStrategyAlphabetSignificantZZZ(){
		super();
		this.setValueCurrent(CounterByCharacterAscii_AlphabetZZZ.iPOSITION_MIN);
	}
	public AbstractCounterByStrategyAlphabetSignificantZZZ(int iStartingValue){
		super(iStartingValue);		
	}
	public AbstractCounterByStrategyAlphabetSignificantZZZ(int iStartingValue, ICounterStrategyAlphabetSignificantZZZ objCounterStrategy){
		super(iStartingValue);
		this.setCounterStrategyObject(objCounterStrategy);
	}
	public AbstractCounterByStrategyAlphabetSignificantZZZ(ICounterStrategyAlphabetSignificantZZZ objCounterStrategy){
		super(CounterByCharacterAscii_AlphabetZZZ.iPOSITION_MIN);		
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
				Fehlermarker: Für 1 Kommt C heraus, muss aber A sein!!! Merke: Es geht hier um die Zeichensatzposition und nicht um den Digitvalue
				T objCounterStrategy = (T) this.getCounterStrategyObject();
				sReturn = CounterByCharacterAscii_AlphabetZZZ.getStringForNumber(iValue, objCounterStrategy);	
				
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
				int iValue = CounterByCharacterAscii_AlphabetZZZ.getNumberForString(sValue, objCounterStrategy);
				this.setValueCurrent(iValue);
			}//end main:
		}	
	
//		@Override
//		public int getCharacterPositionMax() {
//			return CounterByCharacterAscii_AlphabetZZZ.iPOSITION_MAX;
//		}
//		
//		@Override
//		public int getCharacterPositionMin() {
//			return CounterByCharacterAscii_AlphabetZZZ.iPOSITION_MIN;
//		}
		
		// ++++ Aus Interface						
		@Override
		public abstract void setCounterStrategyObject(ICounterStrategyAlphabetSignificantZZZ objCounterStrategy);
		
		@Override	
		public abstract T getCounterStrategyObject() throws ExceptionZZZ;
		
	
}
