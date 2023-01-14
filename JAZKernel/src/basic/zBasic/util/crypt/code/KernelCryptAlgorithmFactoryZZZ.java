package basic.zBasic.util.crypt.code;

import basic.zKernel.IKernelZZZ;
import basic.zKernel.KernelZZZ;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractEnum.EnumSetFactoryZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zKernel.KernelUseObjectZZZ;

/** Idee: Die Kernel Version wird dann später ganze Konfigurationsdateien verschlüsseln, oder so....
 *  
 * 
 * @author Fritz Lindhauer, 02.10.2022, 08:50:03
 * 
 */
public class KernelCryptAlgorithmFactoryZZZ extends KernelUseObjectZZZ implements ICryptUserZZZ{

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
	
	public static KernelCryptAlgorithmFactoryZZZ getInstance() throws ExceptionZZZ{
		if(objCryptAlgorithmFactory==null){
			objCryptAlgorithmFactory = new KernelCryptAlgorithmFactoryZZZ();
		}
		return objCryptAlgorithmFactory;		
	}
	public static KernelCryptAlgorithmFactoryZZZ getInstance(IKernelZZZ objKernel) throws ExceptionZZZ{
		if(objCryptAlgorithmFactory==null){
			objCryptAlgorithmFactory = new KernelCryptAlgorithmFactoryZZZ(objKernel);
		}
		return objCryptAlgorithmFactory;		
	}
	
	@Override
	public ICryptZZZ createAlgorithmType(String sCipher) throws ExceptionZZZ {	
		return CryptAlgorithmFactoryZZZ.createAlgorithmTypeByCipher(sCipher);
	}
	
	

	
	
}
