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

public class KernelEncryption_CodeZZZ  extends AbstractIniTagSimpleZZZ{
	public static String sTAG_NAME = "Z:Code";
	
	public KernelEncryption_CodeZZZ() throws ExceptionZZZ{
		super("init");
	}
		
	public KernelEncryption_CodeZZZ(String[] saFlag) throws ExceptionZZZ{
		super(saFlag);
		KernelEncryptionCodeNew_();
	}
	
	private boolean KernelEncryptionCodeNew_() throws ExceptionZZZ {
		 boolean bReturn = false;
		 main:{
//			 	    String stemp; boolean btemp;  		
//		 			//setzen der uebergebenen Flags	
//					if(saFlagControlIn != null){
//						for(int iCount = 0;iCount<=saFlagControlIn.length-1;iCount++){
//							stemp = saFlagControlIn[iCount];
//							btemp = setFlag(stemp, true);
//							if(btemp==false){
//								ExceptionZZZ ez = new ExceptionZZZ( "the flag '" + stemp + "' is not available.", IFlagUserZZZ.iERROR_FLAG_UNAVAILABLE, this, ReflectCodeZZZ.getMethodCurrentName()); 
//								throw ez;		 
//							}
//						}			
//					}	
			
			if(this.getFlag("init")==true){
				bReturn = true;
				break main;
			}
		}//end main:
		return bReturn;
	 }//end function KernelExpressionMathSolverNew_
		
	
	//###### Getter / Setter
	public String getExpressionTagName(){
		return KernelEncryption_CodeZZZ.sTAG_NAME;
	}					
}//End class