package basic.zBasic.util.counter;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IConstantZZZ;

public abstract class AbstractCounterStrategyNumbersOnlyZZZ extends AbstractCounterStrategyZZZ implements ICounterStrategyNumericZZZ{
		public AbstractCounterStrategyNumbersOnlyZZZ() throws ExceptionZZZ{
			super();
		}
		
		@Override
		public int getCharacterPositionMax() throws ExceptionZZZ {
			return CounterByCharacterAscii_NumericZZZ.iPOSITION_MAX;
		}
		
		@Override
		public int getCharacterPositionMin() throws ExceptionZZZ {
			return CounterByCharacterAscii_NumericZZZ.iPOSITION_MIN;
		}
		
		@Override
		public String getCharForPosition(int iPositionInMinMaxRange) throws ExceptionZZZ{			
			return CounterByCharacterAscii_NumericZZZ.getCharForPosition(iPositionInMinMaxRange);
		}
}
