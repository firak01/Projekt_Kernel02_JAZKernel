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
import basic.zKernel.flag.IFlagUserZZZ;
import custom.zKernel.file.ini.FileIniZZZ;

public class KernelEncryption_CodeZZZ  extends AbstractKernelIniTagZZZ{
	public KernelEncryption_CodeZZZ() throws ExceptionZZZ{
		String[] saFlag = {"init"};
		KernelExpressionMathValueNew_(saFlag);
	}
		
	public KernelEncryption_CodeZZZ(String[] saFlag) throws ExceptionZZZ{		
		KernelExpressionMathValueNew_(saFlag);
	}
	
	public KernelEncryption_CodeZZZ(IKernelZZZ objKernel, String[] saFlag) throws ExceptionZZZ{
		super(objKernel,saFlag);
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
	
	
//	public Vector computeExpressionAllVector(String sLineWithExpression) throws ExceptionZZZ{
//		Vector vecReturn = new Vector();
//		main:{
//			if(StringZZZ.isEmpty(sLineWithExpression)) break main;
//						
//			//TODO: Mehrere Ausdrücke ineinander verschachtelt. Dann muss der jeweilige "Rest-Bestandteil" des ExpressionFirst-Vectors weiter zerlegt werden.
//			vecReturn = this.computeExpressionFirstVector(sLineWithExpression);			
//			
//			//... und vielleicht erneut den Math-Solver auf den Rest ansetzen.
//			
//		}
//		return vecReturn;
//	}
	
	/** Gibt einen Vector zurück, in dem das erste Element der Ausdruck VOR der ersten 'Expression' ist. Das 2. Element ist die Expression. Das 3. Element ist der Ausdruck NACH der ersten Expression.
	* @param sLineWithExpression
	* @return
	* 
	* lindhaueradmin; 06.03.2007 11:20:34
	 * @throws ExceptionZZZ 
	 */
//	public Vector computeExpressionFirstVector(String sLineWithExpression) throws ExceptionZZZ{
//		Vector vecReturn = new Vector();		
//		main:{
//			vecReturn = StringZZZ.vecMidFirst(sLineWithExpression, KernelEncryption_CodeZZZ.getExpressionTagStarting(), KernelEncryption_CodeZZZ.getExpressionTagClosing(), false, false);
//		}
//		return vecReturn;
//	}
	
	
//	public static boolean isExpression(String sLine){
//		boolean bReturn = false;
//		main:{
//			boolean btemp = StringZZZ.contains(sLine, KernelEncryption_CodeZZZ.getExpressionTagStarting(),false);
//			if(btemp==false) break main;
//		
//			btemp = StringZZZ.contains(sLine, KernelEncryption_CodeZZZ.getExpressionTagClosing(),false);
//			if(btemp==false) break main;
//			
//			bReturn = true;
//		}//end main
//		return bReturn;
//	}
	
	
	//###### Getter / Setter
	public String getExpressionTagName(){
		return "Z:code";
	}
	
//			@Override
//			public boolean isStringForConvertRelevant(String sToProof) throws ExceptionZZZ {
//				boolean bReturn=false;
//				
//				//Hier noch was Relevantes für die KernelExpressionIniConverter-Klasse finden.
////						if(StringZZZ.isEmpty(sToProof)){
////							bReturn = true;
////						}
//				return bReturn;
//			}
//			
//			@Override
//			public boolean isStringForComputeRelevant(String sExpressionToProof)
//					throws ExceptionZZZ {
//				// TODO Auto-generated method stub
//				return false;
//			}
//			
//			@Override
//			public String compute(String sLineWithExpression) throws ExceptionZZZ{
//				String sReturn = null;
//				main:{
//					if(StringZZZ.isEmptyTrimmed(sLineWithExpression)) break main;
//					
//					Vector vecAll = this.computeExpressionAllVector(sLineWithExpression);
//					
//					//Der Vector ist schon so aufbereiten, dass hier nur noch "zusammenaddiert" werden muss
//					sReturn = VectorZZZ.implode(vecAll);
//					
//				}//end main:
//				return sReturn;
//			}

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