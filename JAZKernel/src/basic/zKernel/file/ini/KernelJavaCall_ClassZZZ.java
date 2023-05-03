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

public class KernelJavaCall_ClassZZZ  extends AbstractKernelIniTagZZZ{
	public static String sTAG_NAME = "Z:Class";
	
	public KernelJavaCall_ClassZZZ() throws ExceptionZZZ{
		super();
	}
		
	public KernelJavaCall_ClassZZZ(String[] saFlag) throws ExceptionZZZ{
		super(saFlag);
		KernelJavaCallMethodNew_(saFlag);
	}
	
	public KernelJavaCall_ClassZZZ(IKernelZZZ objKernel, String[] saFlag) throws ExceptionZZZ{
		super(objKernel,saFlag);
		KernelJavaCallMethodNew_(saFlag);
	}
	
	
	private boolean KernelJavaCallMethodNew_(String[] saFlagControlIn) throws ExceptionZZZ {
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
		return KernelJavaCall_ClassZZZ.sTAG_NAME;
	}
			
	//### Aus Interface IKernelExpressionIniZZZ
	@Override
	public String convert(String sLine) throws ExceptionZZZ {		
		return null;
	}
	
	@Override
	public boolean isStringForConvertRelevant(String sStringToProof) throws ExceptionZZZ {		
		return false;
	}

	@Override
	public boolean isStringForComputeRelevant(String sExpressionToProof) throws ExceptionZZZ {		
		return false;
	}						
}//End class