package basic.zBasic.util.crypt;

import basic.zKernel.IKernelZZZ;
import basic.zKernel.KernelZZZ;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractEnum.EnumSetFactoryZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zKernel.KernelUseObjectZZZ;

public class KernelCryptAlgorithmFactoryZZZ extends KernelUseObjectZZZ{

private static KernelCryptAlgorithmFactoryZZZ objCryptAlgorithmFactory = null;  //muss static sein, wg. getInstance()!!!
	
	/**Konstruktor ist private, wg. Singleton
	 * @param objKernel
	 * @throws ExceptionZZZ
	 */
	private KernelCryptAlgorithmFactoryZZZ(IKernelZZZ objKernel) throws ExceptionZZZ{
		super();
	}
	private KernelCryptAlgorithmFactoryZZZ(){
		super();
	}
	
	public static KernelCryptAlgorithmFactoryZZZ getInstance(){
		if(objCryptAlgorithmFactory==null){
			objCryptAlgorithmFactory = new KernelCryptAlgorithmFactoryZZZ();
		}
		return objCryptAlgorithmFactory;		
	}
	
	public static ICryptZZZ createAlgorithmTypeByCipher(IKernelZZZ objKernel, String sCipher) throws ExceptionZZZ{
		ICryptZZZ objReturn = null;
		main:{			
			check:{
					if(StringZZZ.isEmpty(sCipher)){
						ExceptionZZZ ez = new ExceptionZZZ(sERROR_PARAMETER_MISSING+"Cipher", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), "");
						throw ez;					
					}
			}//end check
		
		if(sCipher.equalsIgnoreCase("ROT13")){
			objReturn = new Rot13ZZZ();
		}else if(sCipher.equalsIgnoreCase("ROTnumeric")){
			objReturn = new ROTnumericZZZ();
		}else if(sCipher.equalsIgnoreCase("ROTnn")) {
			objReturn = new ROTnnZZZ();
		}else{
			ExceptionZZZ ez = new ExceptionZZZ(sERROR_PARAMETER_VALUE+"unhandled cipher type '" + sCipher + "'", iERROR_PARAMETER_VALUE, ReflectCodeZZZ.getMethodCurrentName(), "");
			throw ez;				
		}
		
		}//end main
		return objReturn;
	}
	

	
	
}
