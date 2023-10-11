package basic.zBasic.util.crypt.code;

import basic.zKernel.IKernelZZZ;
import basic.zKernel.KernelZZZ;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractEnum.EnumSetFactoryZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zKernel.AbstractKernelUseObjectZZZ;

/** Idee: Die Kernel Version wird dann später ganze Konfigurationsdateien verschlüsseln, oder so....
 *  
 * 
 * @author Fritz Lindhauer, 02.10.2022, 08:50:03
 * 
 */
public class KernelCryptAlgorithmFactoryZZZ extends AbstractKernelUseObjectZZZ implements ICryptUserZZZ{

private static KernelCryptAlgorithmFactoryZZZ objCryptAlgorithmFactory = null;  //muss static sein, wg. getInstance()!!!
private ICryptZZZ objCryptAlgorithmCreatedLast = null;	
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
		
	public ICryptZZZ createAlgorithmType(String sCipher) throws ExceptionZZZ {	
		ICryptZZZ objCrypt = CryptAlgorithmFactoryZZZ.createAlgorithmTypeByCipher(sCipher);
		this.setCryptAlgorithmType(objCrypt);
		return objCrypt;
	}
	
	//### Aus Interface ICryptZZZ
	@Override
	public ICryptZZZ getCryptAlgorithmType() throws ExceptionZZZ {
		return this.objCryptAlgorithmCreatedLast;
	}
	@Override
	public void setCryptAlgorithmType(ICryptZZZ objCrypt) {
		this.objCryptAlgorithmCreatedLast = objCrypt;
	}
	
	

	
	
}
