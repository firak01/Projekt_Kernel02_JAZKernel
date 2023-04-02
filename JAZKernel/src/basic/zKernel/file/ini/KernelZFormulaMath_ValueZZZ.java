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

public class KernelZFormulaMath_ValueZZZ  extends AbstractKernelIniTagZZZ{ //KernelUseObjectZZZ implements IKernelZFormulaIniZZZ{
	public static String sTAG_NAME = "Z:val";
	public KernelZFormulaMath_ValueZZZ() throws ExceptionZZZ{
		super();
	}
		
	public KernelZFormulaMath_ValueZZZ(String[] saFlag) throws ExceptionZZZ{	
		super(saFlag);
		KernelExpressionMathValueNew_(saFlag);
	}
	
	public KernelZFormulaMath_ValueZZZ(IKernelZZZ objKernel, String[] saFlag) throws ExceptionZZZ{		
		super(objKernel, saFlag);
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
	
	
	public Vector computeExpressionAllVector(String sLineWithExpression) throws ExceptionZZZ{
		Vector vecReturn = new Vector();
		main:{
			if(StringZZZ.isEmpty(sLineWithExpression)) break main;
						
			//TODO: Mehrere Ausdrücke ineinander verschachtelt. Dann muss der jeweilige "Rest-Bestandteil" des ExpressionFirst-Vectors weiter zerlegt werden.
			vecReturn = this.computeExpressionFirstVector(sLineWithExpression);			
			
			//... und vielleicht erneut den Math-Solver auf den Rest ansetzen.
			
		}
		return vecReturn;
	}
	
	//###### Getter / Setter
	public String getExpressionTagName(){
		return KernelZFormulaMath_ValueZZZ.sTAG_NAME;
	}
	
	//### Aus Interface IKernelExpressionIniZZZ	
			@Override
			public String compute(String sLineWithExpression) throws ExceptionZZZ{
				String sReturn = null;
				main:{
					if(StringZZZ.isEmptyTrimmed(sLineWithExpression)) break main;
					
					Vector vecAll = this.computeExpressionAllVector(sLineWithExpression);
					
					//Der Vector ist schon so aufbereiten, dass hier nur noch "zusammenaddiert" werden muss
					sReturn = VectorZZZ.implode(vecAll);

					//sReturn = (String) vecAll.get(1);
					//this.setValue(sReturn);
				}//end main:
				return sReturn;			
			}

			@Override
			public String convert(String sLine) throws ExceptionZZZ {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public boolean isStringForConvertRelevant(String sToProof) throws ExceptionZZZ {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean isStringForComputeRelevant(String sExpressionToProof) throws ExceptionZZZ {
				// TODO Auto-generated method stub
				return false;
			}						
}//End class