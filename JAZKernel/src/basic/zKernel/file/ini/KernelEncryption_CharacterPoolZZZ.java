package basic.zKernel.file.ini;

import java.util.Vector;

import basic.zBasic.ExceptionZZZ;

public class KernelEncryption_CharacterPoolZZZ<T>  extends AbstractIniTagSimpleZZZ<T>{
	private static final long serialVersionUID = -3249222443434359620L;
	public static String sTAG_NAME = "Z:CharacterPool";
	
	public KernelEncryption_CharacterPoolZZZ() throws ExceptionZZZ{
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
	 }//end function KernelExpressionMathSolverNew_
		
	
	//###### Getter / Setter
	@Override
	public String getNameDefault() throws ExceptionZZZ{
		return KernelEncryption_CharacterPoolZZZ.sTAG_NAME;
	}


	//### aus IConvertableZZZ
	@Override
	public boolean isStringForConvertRelevant(String sStringToProof) throws ExceptionZZZ {	
		return false;
	}
				
}//End class