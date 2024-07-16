package basic.zKernel.file.ini;

import basic.zBasic.ExceptionZZZ;

public class ZTagEncryption_KeyStringZZZ  extends AbstractIniTagSimpleZZZ{
	private static final long serialVersionUID = -79235762837292434L;
	public static String sTAG_NAME = "Z:KeyString";
	
	public ZTagEncryption_KeyStringZZZ() throws ExceptionZZZ{
		super("init");
	}
		
	public ZTagEncryption_KeyStringZZZ(String[] saFlag) throws ExceptionZZZ{
		super(saFlag);
		KernelEncryptionKeyStringNew_();
	}
	
	private boolean KernelEncryptionKeyStringNew_() throws ExceptionZZZ {
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
		return ZTagEncryption_KeyStringZZZ.sTAG_NAME;
	}					
}//End class