package basic.zBasic.util.datatype.counter;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IConstantZZZ;
import basic.zBasic.util.datatype.character.CharZZZ;

public abstract class AbstractCounterStrategyAlphanumericSignificantZZZ extends AbstractCounterStrategyAlphanumericZZZ implements ICounterStrategyAlphanumericSignificantZZZ{		
	private int iCounterLength;
	private char cCounterFilling;
	
	public AbstractCounterStrategyAlphanumericSignificantZZZ(){
		super();
	}
	public AbstractCounterStrategyAlphanumericSignificantZZZ(boolean bLowercase){
		super();
		this.isLowercase(true);
	}

	//+++ Aus Interfaces
		@Override
		public int getCounterLength() {
			return this.iCounterLength;
		}

		@Override
		public void setCounterLength(int iLength) {
			this.iCounterLength=iLength;
		}

		@Override
		public char getCounterFilling() {		
			if(CharZZZ.isEmpty(cCounterFilling)){
				return "0".toCharArray()[0]; //das sollte dann auch "0" sein, oder?
			}else{
				return this.cCounterFilling;
			}
		}

		@Override
		public void setCounterFilling(char cFilling) {
			this.cCounterFilling=cFilling;
		}
		
		
		

}
