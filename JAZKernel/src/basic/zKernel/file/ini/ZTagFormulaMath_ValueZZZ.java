package basic.zKernel.file.ini;

import basic.zBasic.ExceptionZZZ;

public class ZTagFormulaMath_ValueZZZ<T>  extends AbstractIniTagSimpleZZZ<T>{
	private static final long serialVersionUID = -6014276444711828780L;
	public static String sTAG_NAME = "Z:val";
	public ZTagFormulaMath_ValueZZZ() throws ExceptionZZZ{
		super();
		KernelExpressionMathValueNew_();
	}
		
	private boolean KernelExpressionMathValueNew_() throws ExceptionZZZ {
//	 boolean bReturn = false;
//	 main:{
////		if(this.getFlag("init")==true){
////			bReturn = true;
////			break main;
////		}
//		
//		}//end main:
		return true;
	 }//end function KernelExpressionMathSolverNew_
	
	
	//###### Getter / Setter
	@Override
	public String getName(){
		return ZTagFormulaMath_ValueZZZ.sTAG_NAME;
	}
}//End class