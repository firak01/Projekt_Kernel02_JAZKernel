package basic.zKernel.file.ini;

import java.util.Vector;

import basic.zBasic.ExceptionZZZ;

public class ZTagEncryption_KeyStringZZZ<T>  extends AbstractIniTagSimpleZZZ<T>{
	private static final long serialVersionUID = -79235762837292434L;
	public static final String sTAG_NAME = "Z:KeyString";
	
	public ZTagEncryption_KeyStringZZZ() throws ExceptionZZZ{
		super();
		KernelEncryptionKeyStringNew_();
	}
			
	private boolean KernelEncryptionKeyStringNew_() throws ExceptionZZZ {
//	 boolean bReturn = false;
//	 main:{
//			if(this.getFlag("init")==true){
//				bReturn = true;
//				break main;
//			}
//	 	}//end main:
		return true;
	 }//end function KernelEncryptionKeyStringNew_
		
	
	//###### Getter / Setter
//	@Override
//	public String getNameDefault() throws ExceptionZZZ {
//		return ZTagEncryption_KeyStringZZZ.sTAG_NAME;
//	}

	//### aus IConvertableZZZ
	@Override
	public boolean isConvertRelevant(String sStringToProof) throws ExceptionZZZ {	
		return false;
	}				
}//End class