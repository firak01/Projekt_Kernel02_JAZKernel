package basic.zKernel.file.ini;

import java.util.Vector;

import basic.zBasic.ExceptionZZZ;

public class KernelEncryption_KeyNumberZZZ<T>  extends AbstractIniTagSimpleZZZ<T>{
	private static final long serialVersionUID = -3758573003010061733L;
	public static final String sTAG_NAME = "Z:KeyNumber";
	
	public KernelEncryption_KeyNumberZZZ() throws ExceptionZZZ{
		super();
		KernelEncryptionKeyNumberNew_();
	}
		
	private boolean KernelEncryptionKeyNumberNew_() throws ExceptionZZZ {
//		 boolean bReturn = false;
//		 main:{
//			if(this.getFlag("init")==true){
//				bReturn = true;
//				break main;
//			}
//			
//			
//		}//end main:
		return true;
	 }//end function KernelEncryptionKeyNumberNew_
		
	
	//###### Getter / Setter
//	@Override
//	public String getNameDefault() throws ExceptionZZZ{
//		return KernelEncryption_KeyNumberZZZ.sTAG_NAME;
//	}		
	
	//### aus IConvertableZZZ
	@Override
	public boolean isConvertRelevant(String sStringToProof) throws ExceptionZZZ {	
		return false;
	}

}//End class