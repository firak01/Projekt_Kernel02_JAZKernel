package basic.zKernel.file.ini;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.formula.AbstractIniTagSimpleZZZ;

public class KernelEncryption_CipherZZZ<T>  extends AbstractIniTagSimpleZZZ<T>{
	private static final long serialVersionUID = -7986470340481307896L;
	public static String sTAG_NAME = "Z:Cipher";
	
	public KernelEncryption_CipherZZZ() throws ExceptionZZZ{
		super("init");
	}
		
	public KernelEncryption_CipherZZZ(String[] saFlag) throws ExceptionZZZ{	
		super(saFlag);
		KernelExpressionMathValueNew_();
	}
	
	private boolean KernelExpressionMathValueNew_() throws ExceptionZZZ {
		 boolean bReturn = false;
		 main:{
			if(this.getFlag("init")==true){
				bReturn = true;
				break main;
			}
			
			
		}//end main:
		return bReturn;
	 }//end function KernelExpressionMathSolverNew_
	
	public String getCipher() {
		return this.getValue();
	}
	public void setCipher(String sCipher) {
		this.setValue(sCipher);
	}
		
	//###### Getter / Setter
	public String getExpressionTagName(){
		return KernelEncryption_CipherZZZ.sTAG_NAME;
	}				
}//End class