package basic.zKernel.file.ini;

import java.util.Vector;

import basic.zBasic.ExceptionZZZ;

public class KernelEncryption_CharacterPoolAdditionalZZZ<T>  extends AbstractIniTagSimpleZZZ<T>{
	private static final long serialVersionUID = 956884729101672279L;
	public static String sTAG_NAME = "Z:CharacterPoolAdditional";
	
	public KernelEncryption_CharacterPoolAdditionalZZZ() throws ExceptionZZZ{
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
		return KernelEncryption_CharacterPoolAdditionalZZZ.sTAG_NAME;
	}


	//### aus IConvertableZZZ
	@Override
	public boolean isConvertRelevant(String sStringToProof) throws ExceptionZZZ {	
		return false;
	}
		
}//End class