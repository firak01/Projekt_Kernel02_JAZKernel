package basic.zBasic.util.datatype.counter;

import basic.zBasic.ExceptionZZZ;

public interface ICounterCaseSensitiveZZZ extends ICounterStringZZZ{
	
		//Entsprechend in ICounterStrategyAlphanumeric
		public boolean isLowercase() throws ExceptionZZZ;
		public void isLowercase(boolean bValue) throws ExceptionZZZ;
}
