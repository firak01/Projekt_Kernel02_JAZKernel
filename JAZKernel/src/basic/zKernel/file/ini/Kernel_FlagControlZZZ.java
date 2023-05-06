package basic.zKernel.file.ini;

import java.util.Vector;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractList.VectorZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zKernel.IKernelZFormulaIniZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernel.KernelUseObjectZZZ;
import basic.zKernel.KernelZZZ;
import basic.zKernel.flag.IFlagZUserZZZ;
import custom.zKernel.file.ini.FileIniZZZ;

public class Kernel_FlagControlZZZ  extends AbstractKernelIniTagSimpleZZZ{
	public static String sTAG_NAME = "Z:FlagControl";
	
	public Kernel_FlagControlZZZ() throws ExceptionZZZ{
		super();
	}
		
	public Kernel_FlagControlZZZ(String[] saFlag) throws ExceptionZZZ{
		super(saFlag);
		KernelEncryptionKeyNumberNew_(saFlag);
	}
	
	public Kernel_FlagControlZZZ(IKernelZZZ objKernel, String[] saFlag) throws ExceptionZZZ{
		super(objKernel,saFlag);
		KernelEncryptionKeyNumberNew_(saFlag);
	}
	
	
	private boolean KernelEncryptionKeyNumberNew_(String[] saFlagControlIn) throws ExceptionZZZ {
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
		
	
	//###### Getter / Setter
	public String getExpressionTagName(){
		return Kernel_FlagControlZZZ.sTAG_NAME;
	}
			
	//### Aus Interface IKernelExpressionIniZZZ
	@Override
	public String convert(String sLine) throws ExceptionZZZ {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public boolean isStringForConvertRelevant(String sStringToProof) throws ExceptionZZZ {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isStringForComputeRelevant(String sExpressionToProof) throws ExceptionZZZ {
		// TODO Auto-generated method stub
		return false;
	}						
}//End class