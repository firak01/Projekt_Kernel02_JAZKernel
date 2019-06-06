package basic.zBasic.util.datatype.counter;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IConstantZZZ;

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
			return this.cCounterFilling;
		}

		@Override
		public void setCounterFilling(char cFilling) {
			this.cCounterFilling=cFilling;
		}
		
		
		

}
