package basic.zKernel.file.ini;

import basic.zBasic.ExceptionZZZ;

public class KernelEncryption_CharacterPoolAdditionalZZZ<T>  extends AbstractIniTagSimpleZZZ<T>{
	private static final long serialVersionUID = 956884729101672279L;
	public static String sTAG_NAME = "Z:CharacterPoolAdditional";
	
	public KernelEncryption_CharacterPoolAdditionalZZZ() throws ExceptionZZZ{
		super("init");
	}
		
	public KernelEncryption_CharacterPoolAdditionalZZZ(String[] saFlag) throws ExceptionZZZ{
		super(saFlag);
		KernelEncryptionKeyNumberNew_();
	}
	
	private boolean KernelEncryptionKeyNumberNew_() throws ExceptionZZZ {
		 boolean bReturn = false;
		 main:{
			if(this.getFlag("init")==true){
				bReturn = true;
				break main;
			}
			
			
		}//end main:
		return bReturn;
	 }//end function KernelExpressionMathSolverNew_
		
	
	//###### Getter / Setter
	public String getExpressionTagName(){
		return KernelEncryption_CharacterPoolAdditionalZZZ.sTAG_NAME;
	}			
}//End class