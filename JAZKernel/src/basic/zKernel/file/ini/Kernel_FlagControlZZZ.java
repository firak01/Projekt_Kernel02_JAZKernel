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

public class Kernel_FlagControlZZZ  extends AbstractIniTagSimpleZZZ{
	public static String sTAG_NAME = "Z:FlagControl";
	
	public Kernel_FlagControlZZZ() throws ExceptionZZZ{
		super("init");
	}
		
	public Kernel_FlagControlZZZ(String[] saFlag) throws ExceptionZZZ{
		super(saFlag);
		KernelEncryptionKeyNumberNew_();
	}
	
	private boolean KernelEncryptionKeyNumberNew_() throws ExceptionZZZ {
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
		return Kernel_FlagControlZZZ.sTAG_NAME;
	}				
}//End class