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
	
	
	@Override
	public ICounterStringZZZ createCounterAlphabet() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public ICounterStringZZZ createCounterAlphabetStartingWithString(String s) {
		// TODO Auto-generated method stub
		return null;
	}

}
