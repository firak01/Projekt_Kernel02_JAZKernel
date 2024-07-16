package basic.zKernel.file.ini;

import basic.zBasic.ExceptionZZZ;

public class KernelEncryption_CharacterPoolZZZ<T>  extends AbstractIniTagSimpleZZZ<T>{
	private static final long serialVersionUID = -3249222443434359620L;
	public static String sTAG_NAME = "Z:CharacterPool";
	
	public KernelEncryption_CharacterPoolZZZ() throws ExceptionZZZ{
		super("init");
	}
		
	public KernelEncryption_CharacterPoolZZZ(String[] saFlag) throws ExceptionZZZ{
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
		return KernelEncryption_CharacterPoolZZZ.sTAG_NAME;
	}				
}//End class