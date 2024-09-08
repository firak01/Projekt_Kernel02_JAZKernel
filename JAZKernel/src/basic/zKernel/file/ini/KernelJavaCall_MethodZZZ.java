package basic.zKernel.file.ini;

import basic.zBasic.ExceptionZZZ;

public class KernelJavaCall_MethodZZZ<T>  extends AbstractIniTagSimpleZZZ<T>{
	private static final long serialVersionUID = 8437140852114172576L;
	public static String sTAG_NAME = "Z:Method";
	
	public KernelJavaCall_MethodZZZ() throws ExceptionZZZ{
		super();
		KernelJavaCallMethodNew_();
	}
	
	private boolean KernelJavaCallMethodNew_() throws ExceptionZZZ {
//		 boolean bReturn = false;
//		 main:{
//				if(this.getFlag("init")==true){
//					bReturn = true;
//					break main;
//				}
//				
//		 	}//end main:
			return true;
	 }//end function KernelJavaCallMethodNew_
		
	
	//###### Getter / Setter
	@Override
	public String getNameDefault() throws ExceptionZZZ{
		return KernelJavaCall_MethodZZZ.sTAG_NAME;
	}

	//### aus IConvertableZZZ
	@Override
	public boolean isConvertRelevant(String sStringToProof) throws ExceptionZZZ {	
		return false;
	}						
}//End class