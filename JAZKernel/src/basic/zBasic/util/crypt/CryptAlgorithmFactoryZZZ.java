package basic.zBasic.util.crypt;

import basic.zKernel.IKernelZZZ;
import basic.zKernel.KernelZZZ;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ObjectZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractEnum.EnumSetFactoryZZZ;
import basic.zBasic.util.crypt.encode.Vigenere256ZZZ;
import basic.zBasic.util.crypt.encode.Vigenere26ZZZ;
import basic.zBasic.util.crypt.encode.Vigenere96ZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zKernel.KernelUseObjectZZZ;

public class CryptAlgorithmFactoryZZZ extends ObjectZZZ implements ICryptUserZZZ{

private static CryptAlgorithmFactoryZZZ objCryptAlgorithmFactory = null;  //muss static sein, wg. getInstance()!!!
	
	/**Konstruktor ist private, wg. Singleton
	 * @param objKernel
	 * @throws ExceptionZZZ
	 */
	private CryptAlgorithmFactoryZZZ(IKernelZZZ objKernel) throws ExceptionZZZ{
		super();
	}
	private CryptAlgorithmFactoryZZZ(){
		super();
	}
	
	public static CryptAlgorithmFactoryZZZ getInstance() throws ExceptionZZZ{
		if(objCryptAlgorithmFactory==null){
			objCryptAlgorithmFactory = new CryptAlgorithmFactoryZZZ();
		}
		return objCryptAlgorithmFactory;		
	}
		
	@Override
	public ICryptZZZ createAlgorithmType(String sCipher) throws ExceptionZZZ {	
		return CryptAlgorithmFactoryZZZ.createAlgorithmTypeByCipher(sCipher);
	}
	
	public static ICryptZZZ createAlgorithmTypeByCipher(String sCipher) throws ExceptionZZZ{
		ICryptZZZ objReturn = null;
		main:{			
			check:{
					if(StringZZZ.isEmpty(sCipher)){
						ExceptionZZZ ez = new ExceptionZZZ(sERROR_PARAMETER_MISSING+"Cipher", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), "");
						throw ez;					
					}
			}//end check
		
		if(sCipher.equalsIgnoreCase(CryptAlgorithmMappedValueZZZ.CipherTypeZZZ.ROT13.getAbbreviation())){
			objReturn = new ROT13ZZZ();
		}else if(sCipher.equalsIgnoreCase(CryptAlgorithmMappedValueZZZ.CipherTypeZZZ.ROTnumeric.getAbbreviation())){
			objReturn = new ROTnumericZZZ();
		}else if(sCipher.equalsIgnoreCase(CryptAlgorithmMappedValueZZZ.CipherTypeZZZ.ROTascii.getAbbreviation())) {
			objReturn = new ROTasciiZZZ();
		}else if(sCipher.equalsIgnoreCase(CryptAlgorithmMappedValueZZZ.CipherTypeZZZ.ROTnn.getAbbreviation())) {
			objReturn = new ROTnnZZZ();
		}else if(sCipher.equalsIgnoreCase(CryptAlgorithmMappedValueZZZ.CipherTypeZZZ.VIGENERE26.getAbbreviation())) {
			objReturn = new Vigenere26ZZZ();
		}else if(sCipher.equalsIgnoreCase(CryptAlgorithmMappedValueZZZ.CipherTypeZZZ.VIGENERE96.getAbbreviation())) {
			objReturn = new Vigenere96ZZZ();
		}else if(sCipher.equalsIgnoreCase(CryptAlgorithmMappedValueZZZ.CipherTypeZZZ.VIGENERE256.getAbbreviation())) {
			objReturn = new Vigenere256ZZZ();
		}else{
			ExceptionZZZ ez = new ExceptionZZZ(sERROR_PARAMETER_VALUE+"unhandled cipher type '" + sCipher + "'", iERROR_PARAMETER_VALUE, ReflectCodeZZZ.getMethodCurrentName(), "");
			throw ez;				
		}
		
		}//end main
		return objReturn;
	}
	
	

	
	
}
