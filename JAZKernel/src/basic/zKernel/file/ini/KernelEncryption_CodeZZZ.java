package basic.zKernel.file.ini;

import java.util.Vector;

import basic.zBasic.ExceptionZZZ;

public class KernelEncryption_CodeZZZ<T>  extends AbstractIniTagSimpleZZZ<T>{
	private static final long serialVersionUID = 5159343273872632561L;
	public static String sTAG_NAME = "Z:Code";
	
	public KernelEncryption_CodeZZZ() throws ExceptionZZZ{
		super();
		KernelEncryptionCodeNew_();
	}
		
	private boolean KernelEncryptionCodeNew_() throws ExceptionZZZ {
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
		return KernelEncryption_CodeZZZ.sTAG_NAME;
	}		
	
	//### aus IConvertableZZZ
	@Override
	public boolean isStringForConvertRelevant(String sStringToProof) throws ExceptionZZZ {	
		return false;
	}

}//End class