package basic.zBasic.util.counter;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IConstantZZZ;
import basic.zBasic.IObjectReflectableImplementerZZZ;
import basic.zBasic.ObjectReflectableZZZ;
import basic.zBasic.ReflectCodeKernelZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;

public abstract class AbstractCounterStrategyZZZ extends ObjectReflectableZZZ implements IObjectReflectableImplementerZZZ, ICounterStrategyZZZ{
	//TODO: Default Werte gehören eigentlich als Konstante ins Interface.
	int iStartDefault = 0;		
	private int iStart = -1;
	
	private boolean bLeftAligned = false;//d.h. rechts stehen dann die Werte mit dem niedrigsten DigitValue, wie man es vom Dezimalsystem her kennt.
	private boolean bIncreasableInOtherMethod = false;
	
	public AbstractCounterStrategyZZZ() throws ExceptionZZZ{
		super();			
	}
	
	public AbstractCounterStrategyZZZ(int iStartValue) throws ExceptionZZZ{
		super();			
		this.setCounterStart(iStartValue);
	}
	
	//++++ Aus Interface
	@Override
	public boolean isLeftAligned() {
		return this.bLeftAligned;
	}
	@Override
	public void isLeftAligned(boolean bValue) {
		this.bLeftAligned = bValue;
	}
	
	@Override
	public boolean isRightAligned() {
		return !this.bLeftAligned;
	}
	@Override
	public void isRightAligned(boolean bValue) {
		this.bLeftAligned = !bValue;
	}
	
	//Merke: Das gibt es in der Numerischen Variante (ohne LowerCase) und in der Alphabet Variante (mit LowerCase)
	//TODO GOON 201909014: 
//	@Override
//	public abstract String getCharForPosition(int iPositionInMinMaxRange) throws ExceptionZZZ;
	
	
	@Override
	public boolean isIncreasableInOtherMethod(){
		return this.bIncreasableInOtherMethod;
	}
	@Override
	public void isIncreasableInOtherMethod(boolean bValue){
		this.bIncreasableInOtherMethod = bValue;
	}
	
	@Override
	public abstract boolean makeReflectableInitialization() throws ExceptionZZZ;
	
	
	//Aus Interface ICounterStrategy
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
	
	@Override
	public int getDigitValueMin(){
		return this.getCharacterPositionMin()-1;
	}
	
	@Override
	public int getDigitValueMax(){
		return this.getCharacterPositionMax()-1;
	}
	
	@Override
	public String getCharForDigitValue(int iDigitValueInMinMaxRange) throws ExceptionZZZ{		
		int iPositionValue = this.getPositionValueForDigitValue(iDigitValueInMinMaxRange);
		return this.getCharForPosition(iPositionValue);
	}
	
		
	@Override
	public int getPositionValueForDigitValue(int iDigitValueInMinMaxRange){
		return iDigitValueInMinMaxRange + 1;
	}
	
	@Override
	public int getDigitValueForPositionValue(int iPositionValueInMinMaxRange){		
		return iPositionValueInMinMaxRange - 1;
	}

}
