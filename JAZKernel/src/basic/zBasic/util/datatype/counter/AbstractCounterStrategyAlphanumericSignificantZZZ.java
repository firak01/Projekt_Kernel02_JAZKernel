package basic.zBasic.util.datatype.counter;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IConstantZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.datatype.character.CharZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;

public abstract class AbstractCounterStrategyAlphanumericSignificantZZZ extends AbstractCounterStrategyAlphanumericZZZ implements ICounterStrategyAlphanumericSignificantZZZ{		
	int iStartDefault = 0;
	int iCounterLengthDefault = 4;
	char cCounterFillingDefault = "0".toCharArray()[0]; //das sollte dann auch "0" sein, oder?;
	
	private int iStart = -1;
	private int iCounterLength;
	private char cCounterFilling;
	
	public AbstractCounterStrategyAlphanumericSignificantZZZ(){
		super();
	}
	public AbstractCounterStrategyAlphanumericSignificantZZZ(int iLength, char cCounterFilling) throws ExceptionZZZ{
		this();
		this.setCounterLength(iLength);
		this.setCounterFilling(cCounterFilling);
	}
	public AbstractCounterStrategyAlphanumericSignificantZZZ(int iLength, String sCounterFilling) throws ExceptionZZZ{
		this();
		this.setCounterLength(iLength);
		this.setCounterFilling(sCounterFilling);		
	}
	public AbstractCounterStrategyAlphanumericSignificantZZZ(int iLength, String sCounterFilling, int iCounterStart) throws ExceptionZZZ{
		this();
		this.setCounterLength(iLength);
		this.setCounterFilling(sCounterFilling);
		this.setCounterStart(iCounterStart);
	}
	public AbstractCounterStrategyAlphanumericSignificantZZZ(int iLength, String sCounterFilling, String sCounterStart) throws ExceptionZZZ{
		this();
		this.setCounterLength(iLength);
		this.setCounterFilling(sCounterFilling);
		this.setCounterStart(sCounterStart);
	}
	public AbstractCounterStrategyAlphanumericSignificantZZZ(boolean bLowercase){
		super();
		this.isLowercase(true);
	}

	//+++ Aus Interfaces
		@Override
		public int getCounterLength() {
			if(this.iCounterLength==0){
				this.iCounterLength= this.iCounterLengthDefault;
			}
			return this.iCounterLength;
		}

		@Override
		public void setCounterLength(int iLength) throws ExceptionZZZ {
			if(iLength<=0){				
				ExceptionZZZ ez = new ExceptionZZZ("Ungültiger Wert für CounterLength (= "+iLength+")",iERROR_PARAMETER_VALUE, this,  ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			this.iCounterLength=iLength;
		}

		@Override
		public char getCounterFilling() {		
			if(CharZZZ.isEmpty(cCounterFilling)){
				this.cCounterFilling = this.cCounterFillingDefault;
			}
			return this.cCounterFilling;			
		}

		@Override
		public void setCounterFilling(char cFilling) {
			this.cCounterFilling=cFilling;
		}
		
		@Override
		public void setCounterFilling(String sCounterFilling)throws ExceptionZZZ{
			if(!StringZZZ.isEmpty(sCounterFilling)){
				if(sCounterFilling.length()>=2){
					ExceptionZZZ ez = new ExceptionZZZ("Ungültiger Wert für CounterFilling (= "+ sCounterFilling +"). Nur ein Buchstabe erlaubt.",iERROR_PARAMETER_VALUE, this,  ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
				char cCounterFilling = sCounterFilling.toCharArray()[0];
				this.setCounterFilling(cCounterFilling);
			}else{
				this.setCounterFilling(CharZZZ.getEmpty());
			}
		}
		
		@Override
		public void setCounterStart(String sStart) throws ExceptionZZZ{			
			int iValue = CounterByCharacterAscii_AlphanumericZZZ.getNumberForString(sStart, this);
			this.setCounterStart(iValue);
		}
		
		@Override
		public void setCounterStart(int iStart){
			this.iStart = iStart;
		}
					
		@Override
		public int getCounterStart(){
			if(this.iStart<=-1){
				this.iStart = this.iStartDefault;
			}
			return this.iStart;
		}
		
		
		

}
