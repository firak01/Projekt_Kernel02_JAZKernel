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
	public static int iPOSITION_MIN=1; //Merke: Als Alphabetgrundlage wird hier der ASCII Satz gesehen
	public static int iPOSITION_MAX=26;
	
	public static String sREGEX_CHARACTERS="[a-zA-Z]";

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
	public void setCounterLength(int iLength) {
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
		}//end main:
		return sReturn;
	}
	
	@Override
	public void setValueCurrent(String sValue) throws ExceptionZZZ{			
		main:{
			ICounterStrategyAlphanumericZZZ objCounterStrategy = this.getCounterStrategyObject();
			int iValue = CounterByCharacterAscii_AlphanumericZZZ.getNumberForString(sValue, objCounterStrategy);
			this.setValueCurrent(iValue);
		}//end main:
	}	
	
}
