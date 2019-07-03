package basic.zBasic.util.datatype.counter;

import java.util.ArrayList;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IConstantZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.datatype.character.CharZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;


/**
 *  
 * 
 * @author Fritz Lindhauer, 03.03.2019, 12:49:22
 * 
 */
public class CounterByCharacterAscii_AlphanumericSignificantZZZ extends AbstractCounterByStrategyAlphanumericSignificantZZZ{
	private ICounterStrategyAlphanumericSignificantZZZ objCounterStrategy;	
	
	public static int iPOSITION_MIN=1;  //Merke: die Sonderzeichen werden übersprungen bei Werten >10  und <=16
	public static int iPOSITION_MAX=36;
	
	public static String sREGEX_CHARACTERS="[a-zA-Z0-9]";

	public CounterByCharacterAscii_AlphanumericSignificantZZZ(){
		super();	
	}
	public CounterByCharacterAscii_AlphanumericSignificantZZZ(int iStartingValue){
		super(iStartingValue);		
	}
	public CounterByCharacterAscii_AlphanumericSignificantZZZ(String sStartingValue) throws ExceptionZZZ{
		super(sStartingValue);		
	}
	public CounterByCharacterAscii_AlphanumericSignificantZZZ(int iStartingValue, ICounterStrategyAlphanumericSignificantZZZ objCounterStrategy){
		super(iStartingValue);
		this.setCounterStrategyObject(objCounterStrategy);
	}
	public CounterByCharacterAscii_AlphanumericSignificantZZZ(ICounterStrategyAlphanumericSignificantZZZ objCounterStrategy){
		super(CounterByCharacterAscii_AlphanumericSignificantZZZ.iPOSITION_MIN);
		this.setCounterStrategyObject(objCounterStrategy);
	}
	
	


	//### Aus Interface
	@Override
	public int getCounterLength() {
		return this.getCounterStrategyObject().getCounterLength();
	}

	@Override
	public void setCounterLength(int iLength) throws ExceptionZZZ {
		this.getCounterStrategyObject().setCounterLength(iLength);
	}

	@Override
	public char getCounterFilling() {		
		return  this.getCounterStrategyObject().getCounterFilling();
	}

	@Override
	public void setCounterFilling(char cFilling) {
		this.getCounterStrategyObject().setCounterFilling(cFilling);
	}
	
	//### Aus Interface
	@Override
	public String peekChange(int iValue) throws ExceptionZZZ {	
		String sReturn = null;
		main:{
			ICounterStrategyAlphanumericZZZ objCounterStrategy = this.getCounterStrategyObject();
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
		
			ICounterStrategyAlphanumericZZZ objCounterStrategy = this.getCounterStrategyObject();
			int iValue = CounterByCharacterAscii_AlphanumericZZZ.getNumberForString(sValue, objCounterStrategy);
			this.setValueCurrent(iValue);
		}//end main:
	}	
	
	@Override
	public String preValueSetting(String sValue) throws ExceptionZZZ {
		String sReturn = null;
		main:{
		if(StringZZZ.isEmpty(sValue)){
			ExceptionZZZ ez = new ExceptionZZZ("AlphanumericCounter: Kein Wert für das PREPROCESSING übergeben.", iERROR_PARAMETER_VALUE, CounterByCharacterAscii_AlphanumericSignificantZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
			throw ez;
		}
		
		if(this.getCounterLength()>= 1){
			//Führendes Füllzeichen entfernen, aber nur wenn es innerhalb der maximalen Zählergröße liegt. Merke: Nur dann schlägt ggfs. eine Validierung fehl. Was gewünscht ist!!!
			int iTimes=this.getCounterLength() - sValue.length();
			if(iTimes>=0){			
				char cToBeStripped = this.getCounterFilling();
				
				String sStringToBeStripped = StringZZZ.char2String(cToBeStripped) ;
				
				
				if(this.getCounterStrategyObject().isRightAligned()){
					sReturn = StringZZZ.stripRight(sValue, sStringToBeStripped);
				}else{
					sReturn = StringZZZ.stripLeft(sValue, sStringToBeStripped);
				}
			}else{
				sReturn = sValue;
			}
		}else{
			sReturn = sValue;
		}
		
		}//end main;
		return sReturn;
	}
	@Override
	public String postValueSetting(String sValue) throws ExceptionZZZ {
		String sReturn = null;
		main:{
		if(StringZZZ.isEmpty(sValue)){
			ExceptionZZZ ez = new ExceptionZZZ("AlphanumericCounter: Kein Wert für das POSTPROCESSING übergeben.", iERROR_PARAMETER_VALUE, CounterByCharacterAscii_AlphanumericSignificantZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
			throw ez;
		}
		
		
		if(this.getCounterLength()>=1){
			
			//Führendes Füllzeichen hinzunehmen, aber nur bis zum Zählermaximum bzgl. der Länge.
			int iTimes=this.getCounterLength() - sValue.length();
			if(iTimes >= 1){			
				char cFilling = this.getCounterFilling();
				String sString = StringZZZ.char2String(cFilling);
				String stemp = StringZZZ.repeat(sString, iTimes);
				
				if(this.getCounterStrategyObject().isRightAligned()){
					sReturn = sValue + stemp;
				}else{
					sReturn = stemp + sValue;
				}			
				 
			}else{
				sReturn = sValue;
			}
		}else{
			sReturn = sValue;
		}
		
		}//end main;
		return sReturn;
	}
	
	//###################
	// ++++ Aus Interface
	@Override
	public ICounterStrategyAlphanumericSignificantZZZ getCounterStrategyObject(){
	if(this.objCounterStrategy==null){
		ICounterStrategyAlphanumericSignificantZZZ objCounterStrategy = new CounterStrategyAlphanumericSignificantZZZ();
		this.objCounterStrategy = objCounterStrategy;
	}
	return this.objCounterStrategy;
	}
	@Override
	public void setCounterStrategyObject(ICounterStrategyAlphanumericSignificantZZZ objCounterStrategy){
		this.objCounterStrategy = objCounterStrategy;	
	}
	
	
}
