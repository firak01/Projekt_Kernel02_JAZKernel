package basic.zKernel.file.ini;

import java.util.Vector;

import basic.zBasic.ExceptionZZZ;

public class KernelEncryption_CipherZZZ<T>  extends AbstractIniTagSimpleZZZ<T>{
	private static final long serialVersionUID = -7986470340481307896L;
	public static final String sTAG_NAME = "Z:Cipher";
	
	public KernelEncryption_CipherZZZ() throws ExceptionZZZ{
		super();
		KernelExpressionMathValueNew_();
	}
		
	private boolean KernelExpressionMathValueNew_() throws ExceptionZZZ {
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
	
	public String getCipher() {
		return this.getValue();
	}
	public void setCipher(String sCipher) {
		this.setValue(sCipher);
	}
		
	//###### Getter / Setter
//	@Override
//	public String getNameDefault() throws ExceptionZZZ{
//		return KernelEncryption_CipherZZZ.sTAG_NAME;
//	}

	//### aus IConvertableZZZ
	@Override
	public boolean isConvertRelevant(String sStringToProof) throws ExceptionZZZ {	
		return false;
	}
			
}//End class