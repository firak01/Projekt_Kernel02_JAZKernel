package basic.zBasic.util.datatype.counter;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ObjectZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractEnum.EnumSetFactoryZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetFactoryZZZ;
import basic.zKernel.IKernelZZZ;

public class CounterByCharacterAsciiFactoryZZZ extends ObjectZZZ implements ICounterByCharacterAsciiFactoryZZZ {
	private static CounterByCharacterAsciiFactoryZZZ objCounterFactory = null;  //muss static sein, wg. getInstance()!!!
	
	/**Konstruktor ist private, wg. Singleton
	 * @param objKernel
	 * @throws ExceptionZZZ
	 */
	private CounterByCharacterAsciiFactoryZZZ(IKernelZZZ objKernel) throws ExceptionZZZ{
		super();
	}
	private CounterByCharacterAsciiFactoryZZZ(){
		super();
	}
	
	public static CounterByCharacterAsciiFactoryZZZ getInstance(){
		if(objCounterFactory==null){
			objCounterFactory = new CounterByCharacterAsciiFactoryZZZ();
		}
		return objCounterFactory;		
	}
	
	
	
	
	private ICounterStringZZZ createCounterAlphabet(){
		return null;
	}
	private ICounterStringZZZ createCounterAlphabetStartingWithString(String s){
		return null;
	}
	private ICounterStringZZZ createCounterAlphanumeric(){
		return null;
	}
	private ICounterStringZZZ createCounterAlphanumericStartingWithString(String s){
		return null;
	}
	private ICounterStringZZZ createCounterNumeric(){
		return null;
	}
	private ICounterStringZZZ createCounterNumericStartingWithString(String s){
		return null;
	}
	
	//### AUS Interfaces
	//COUNTER MIT DEFAULTSTRATEGY "MULTIPLE"
	@Override
	public ICounterStringZZZ createCounter(int iCounterType) throws ExceptionZZZ {
		ICounterStringZZZ objReturn = null;
		if(iCounterType==CounterByCharacterAsciiFactoryZZZ.iCOUNTER_TYPE_NUMERIC){
			objReturn = new CounterByCharacterAscii_NumericZZZ();
		}else if(iCounterType==CounterByCharacterAsciiFactoryZZZ.iCOUNTER_TYPE_ALPHABET){
			objReturn = new CounterByCharacterAscii_AlphabetZZZ();		
		}else{
			ExceptionZZZ ez = new ExceptionZZZ("CounterType wird (noch nicht?) behandelt", iERROR_PARAMETER_VALUE, this, ReflectCodeZZZ.getMethodCurrentName());
			throw ez;
		}
		return objReturn;
	}
	@Override
	public ICounterStringZZZ createCounter(int iCounterType, String sStart) throws ExceptionZZZ {
		ICounterStringZZZ objReturn = null;
		if(iCounterType==CounterByCharacterAsciiFactoryZZZ.iCOUNTER_TYPE_NUMERIC){
			objReturn = new CounterByCharacterAscii_NumericZZZ(sStart);
		}else if(iCounterType==CounterByCharacterAsciiFactoryZZZ.iCOUNTER_TYPE_ALPHABET){
			objReturn = new CounterByCharacterAscii_AlphabetZZZ(sStart);		
		}else{
			ExceptionZZZ ez = new ExceptionZZZ("CounterType wird (noch nicht?) behandelt", iERROR_PARAMETER_VALUE, this, ReflectCodeZZZ.getMethodCurrentName());
			throw ez;
		}
		return objReturn;
	}
	@Override
	public ICounterStringZZZ createCounter(int iCounterType, int iStart)	throws ExceptionZZZ {
		ICounterStringZZZ objReturn = null;
		if(iCounterType==CounterByCharacterAsciiFactoryZZZ.iCOUNTER_TYPE_NUMERIC){
			objReturn = new CounterByCharacterAscii_NumericZZZ(iStart);
		}else{
			ExceptionZZZ ez = new ExceptionZZZ("CounterType wird (noch nicht?) behandelt", iERROR_PARAMETER_VALUE, this, ReflectCodeZZZ.getMethodCurrentName());
			throw ez;
		}
		return objReturn;
	}
	
	//#### COUNTER UNTER ANGABE DER STRATEGY. DABEI GIBT ES VERSCHIEDENEN STRATEGIEN. Die haben auch ggfs. andere Methoden.
	@Override
	public ICounterNumericZZZ createCounter(ICounterStrategyNumericZZZ objCounterStrategy) throws ExceptionZZZ {
		ICounterNumericZZZ objReturn = null;
		if(objCounterStrategy==null){
			ExceptionZZZ ez = new ExceptionZZZ("CounterStrategyObject", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
			throw ez;
		}
		
		//if(iCounterType==CounterByCharacterAsciiFactoryZZZ.iCOUNTER_TYPE_NUMERIC){
			objReturn = new CounterByCharacterAscii_NumericZZZ();
			objReturn.setCounterStrategyObject(objCounterStrategy);			
		//}else{
//			ExceptionZZZ ez = new ExceptionZZZ("CounterType wird (noch nicht?) behandelt", iERROR_PARAMETER_VALUE, this, ReflectCodeZZZ.getMethodCurrentName());
//			throw ez;
		//}
		return objReturn;
	}
	@Override
	public ICounterNumericZZZ createCounter(ICounterStrategyNumericZZZ objCounterStrategy, int iStart) throws ExceptionZZZ {
		ICounterNumericZZZ objReturn = this.createCounter(objCounterStrategy);
		objReturn.setValueCurrent(iStart);
		return objReturn;	
	}
	@Override
	public ICounterNumericZZZ createCounter(ICounterStrategyNumericZZZ objCounterStrategy,String sStart) throws ExceptionZZZ {
		ICounterNumericZZZ objReturn = this.createCounter(objCounterStrategy);
		objReturn.setValueCurrent(sStart);
		return objReturn;	
	}
	@Override
	public ICounterAlphanumericZZZ createCounter(ICounterStrategyAlphanumericZZZ objCounterStrategy) throws ExceptionZZZ {
		ICounterAlphanumericZZZ objReturn = null;
		if(objCounterStrategy==null){
			ExceptionZZZ ez = new ExceptionZZZ("CounterStrategyObject", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
			throw ez;
		}
		
		
		if(objCounterStrategy instanceof CounterStrategyAlphabetSerialZZZ || objCounterStrategy instanceof CounterStrategyAlphabetMultipleZZZ){
			objReturn = new CounterByCharacterAscii_AlphabetZZZ();
			objReturn.setCounterStrategyObject(objCounterStrategy);			
		}else if(objCounterStrategy instanceof CounterStrategyAlphanumericMultipleZZZ || objCounterStrategy instanceof CounterStrategyAlphanumericSerialZZZ){				 
			objReturn = new CounterByCharacterAscii_AlphanumericZZZ();
			objReturn.setCounterStrategyObject(objCounterStrategy);			
		}else{		
			ExceptionZZZ ez = new ExceptionZZZ("CounterStategyObject-Type wird (noch nicht?) behandelt", iERROR_PARAMETER_VALUE, this, ReflectCodeZZZ.getMethodCurrentName());
			throw ez;
		}
		return objReturn;
	}
	@Override
	public ICounterAlphanumericZZZ createCounter(ICounterStrategyAlphanumericZZZ objCounterStrategy, int iStart) throws ExceptionZZZ {
		ICounterAlphanumericZZZ objReturn = this.createCounter(objCounterStrategy);
		objReturn.setValueCurrent(iStart);
		return objReturn;
	}
	@Override
	public ICounterAlphanumericZZZ createCounter(ICounterStrategyAlphanumericZZZ objCounterStrategy, String sStart) throws ExceptionZZZ {
		ICounterAlphanumericZZZ objReturn = this.createCounter(objCounterStrategy);
		objReturn.setValueCurrent(sStart);
		return objReturn;
	}
}
