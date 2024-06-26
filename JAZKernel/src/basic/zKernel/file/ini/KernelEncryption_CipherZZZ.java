package basic.zKernel.file.ini;

import java.util.Vector;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.formula.AbstractIniTagSimpleZZZ;
import basic.zBasic.util.abstractList.VectorZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zKernel.IKernelZFormulaIniZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernel.AbstractKernelUseObjectZZZ;
import basic.zKernel.KernelZZZ;
import basic.zKernel.flag.IFlagZUserZZZ;
import custom.zKernel.file.ini.FileIniZZZ;

public class KernelEncryption_CipherZZZ  extends AbstractIniTagSimpleZZZ{
	public static String sTAG_NAME = "Z:Cipher";
	
	public KernelEncryption_CipherZZZ() throws ExceptionZZZ{
		super();
	}
		
	public KernelEncryption_CipherZZZ(String[] saFlag) throws ExceptionZZZ{	
		super(saFlag);
		KernelExpressionMathValueNew_(saFlag);
	}
	
	private boolean KernelExpressionMathValueNew_(String[] saFlagControlIn) throws ExceptionZZZ {
//	 boolean bReturn = false;
//	 String stemp; boolean btemp; 
//	 main:{
//		 	
//	 	//try{	 		
//	 			//setzen der uebergebenen Flags	
//				if(saFlagControlIn != null){
//					for(int iCount = 0;iCount<=saFlagControlIn.length-1;iCount++){
//						stemp = saFlagControlIn[iCount];
//						btemp = setFlag(stemp, true);
//						if(btemp==false){
//							ExceptionZZZ ez = new ExceptionZZZ( "the flag '" + stemp + "' is not available.", IFlagUserZZZ.iERROR_FLAG_UNAVAILABLE, this, ReflectCodeZZZ.getMethodCurrentName()); 
//							throw ez;		 
//						}
//					}
//					if(this.getFlag("init")==true){
//						bReturn = true;
//						break main;
//					}
//				}			
//	 	}//end main:
//		return bReturn;
		
		return true;
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