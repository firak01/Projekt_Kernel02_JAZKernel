package basic.zBasic.util.datatype.counter;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ObjectZZZ;
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
	@Override
	public ICounterStringZZZ createCounter(int iCounterType) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public ICounterStringZZZ createCounter(int iCounterType, String sStart) {
		// TODO Auto-generated method stub
		return null;
	}
}
