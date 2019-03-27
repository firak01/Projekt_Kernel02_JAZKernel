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
	//COUNTER MIT DEFAUKTSTRATEGY "MULTIPLE"
	@Override
	public ICounterStringZZZ createCounter(int iCounterType) throws ExceptionZZZ {
		ICounterStringZZZ objReturn = null;
		if(iCounterType==CounterByCharacterAsciiFactoryZZZ.iCOUNTER_TYPE_NUMERIC){
			objReturn = new CounterByCharacterAscii_NumericZZZ();
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
	public ICounterStrategyNumericUserZZZ createCounter(int iCounterType,ICounterStrategyNumericZZZ objCounterStrategy) throws ExceptionZZZ {
		ICounterStrategyNumericUserZZZ objReturn = null;
		if(iCounterType==CounterByCharacterAsciiFactoryZZZ.iCOUNTER_TYPE_NUMERIC){
			objReturn = new CounterByCharacterAscii_NumericZZZ();
			objReturn.setCounterStrategyObject(objCounterStrategy);			
		}else{
			ExceptionZZZ ez = new ExceptionZZZ("CounterType wird (noch nicht?) behandelt", iERROR_PARAMETER_VALUE, this, ReflectCodeZZZ.getMethodCurrentName());
			throw ez;
		}
		return objReturn;
	}
	@Override
	public ICounterStrategyNumericUserZZZ createCounter(int iCounterType, int iStart,ICounterStrategyNumericZZZ objCounterStrategy) throws ExceptionZZZ {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public ICounterStrategyNumericUserZZZ createCounter(int iCounterType, String sStart, ICounterStrategyNumericZZZ objCounterStrategy) throws ExceptionZZZ {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ICounterStrategyAlphanumericUserZZZ createCounter(int iCounterType,
			ICounterStrategyAlphanumericZZZ objCounterStrategy)
			throws ExceptionZZZ {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public ICounterStrategyAlphanumericUserZZZ createCounter(int iCounterType,
			int iStart, ICounterStrategyAlphanumericUserZZZ objCounterStrategy)
			throws ExceptionZZZ {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public ICounterStrategyAlphanumericUserZZZ createCounter(int iCounterType,
			String sStart,
			ICounterStrategyAlphanumericUserZZZ objCounterStrategy)
			throws ExceptionZZZ {
		// TODO Auto-generated method stub
		return null;
	}
}
