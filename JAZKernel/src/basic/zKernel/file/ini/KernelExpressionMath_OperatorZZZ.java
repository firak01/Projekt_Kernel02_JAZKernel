package basic.zKernel.file.ini;

import java.util.Vector;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractList.VectorZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zKernel.KernelUseObjectZZZ;
import basic.zKernel.KernelZZZ;
import custom.zKernel.file.ini.FileIniZZZ;

public class KernelExpressionMath_OperatorZZZ  extends KernelUseObjectZZZ{
//	public enum FLAGZ{
//		USEFORMULA_MATH
//	}
	private String sOperator = null;
		
	public KernelExpressionMath_OperatorZZZ() throws ExceptionZZZ{
		String[] saFlag = {"init"};
		KernelExpressionMathSolverNew_(saFlag);
	}
		
	public KernelExpressionMath_OperatorZZZ(String[] saFlag) throws ExceptionZZZ{		
		KernelExpressionMathSolverNew_(saFlag);
	}
	
	public KernelExpressionMath_OperatorZZZ(KernelZZZ objKernel, String[] saFlag) throws ExceptionZZZ{
		super(objKernel);
		KernelExpressionMathSolverNew_(saFlag);
	}
	
	
	private boolean KernelExpressionMathSolverNew_(String[] saFlagControlIn) throws ExceptionZZZ {
	 boolean bReturn = false;
	 String stemp; boolean btemp; 
	 main:{
		 	
	 	//try{	 		
	 			//setzen der übergebenen Flags	
				if(saFlagControlIn != null){
					for(int iCount = 0;iCount<=saFlagControlIn.length-1;iCount++){
						stemp = saFlagControlIn[iCount];
						btemp = setFlag(stemp, true);
						if(btemp==false){
							ExceptionZZZ ez = new ExceptionZZZ( "the flag '" + stemp + "' is not available.", iERROR_FLAG_UNAVAILABLE, this, ReflectCodeZZZ.getMethodCurrentName()); 
							throw ez;		 
						}
					}
					if(this.getFlag("init")==true){
						bReturn = true;
						break main;
					}
				}			
	 	}//end main:
		return bReturn;
	 }//end function KernelExpressionMathSolverNew_
	
	
	public String compute(String sValue01, String sValue02) throws ExceptionZZZ{
		String sReturn = null;
		main:{
			if(StringZZZ.isEmpty(sValue01)) break main;
			if(StringZZZ.isEmpty(sValue02)) break main;
			
			String sOperator = this.getOperator();
			sReturn = this.compute(sValue01, sValue02, sOperator);
									
		}//end main:
		return sReturn;
	}
	
	public String compute(String sValue01, String sValue02, String sOperator) throws ExceptionZZZ{
		String sReturn = null;
		main:{
			if(StringZZZ.isEmpty(sValue01)) break main;
			if(StringZZZ.isEmpty(sValue02)) break main;
			try{
			
			//erst ab Java 1.7 kann auf Strings in einenr Switch - Anweisung gepürft werden.
//			switch(sOperator){
//			case "*":
//				break;
//			case "+":
//				
//			case "-":
//			case "/":
//			case else:
//				//+ als Default
//				
//			}
			
			String sOp = "+";
			if(!StringZZZ.isBlank(sOperator)){
				sOp = sOperator.trim();				
			}
			
			Integer intA = new Integer(sValue01);
			Integer intB = new Integer(sValue02);
			int iReturn = 0;
			if(sOp.equals("+")){
				iReturn = intA.intValue() + intB.intValue();				
			}else if(sOp.equals("*")){
				iReturn = intA.intValue() * intB.intValue();
			}else{
				ExceptionZZZ ez = new ExceptionZZZ( "the operator '" + sOperator + "' is not handled yet.", iERROR_PARAMETER_VALUE, this, ReflectCodeZZZ.getMethodCurrentName()); 
				throw ez;		 
			}
			Integer intReturn = new Integer(iReturn);
			sReturn = intReturn.toString();
			}catch(NumberFormatException e){
				ExceptionZZZ ez = new ExceptionZZZ( "Fehler bei Berechung: '" + sValue01 + "'_'" + sOperator + "'_'" + sValue02 + "'", iERROR_PARAMETER_VALUE, this, ReflectCodeZZZ.getMethodCurrentName()); 
				throw ez;
			}
									
		}//end main:
		return sReturn;
	}
	
	public static boolean isExpression(String sLine){
		boolean bReturn = false;
		main:{
			boolean btemp = StringZZZ.contains(sLine, KernelExpressionMath_OperatorZZZ.getExpressionTagStarting());
			if(btemp==false) break main;
		
			btemp = StringZZZ.contains(sLine, KernelExpressionMath_OperatorZZZ.getExpressionTagClosing());
			if(btemp==false) break main;
			
			bReturn = true;
		}//end main
		return bReturn;
	}
	
	/** Gibt einen Vector zurück, in dem das erste Element der Ausdruck VOR der ersten 'Expression' ist. Das 2. Element ist die Expression. Das 3. Element ist der Ausdruck NACH der ersten Expression.
	* @param sLineWithExpression
	* @return
	* 
	* lindhaueradmin; 06.03.2007 11:20:34
	 * @throws ExceptionZZZ 
	 */
	public Vector computeExpressionFirstVector(String sLineWithExpression) throws ExceptionZZZ{
		Vector vecReturn = new Vector();		
		main:{
			vecReturn = StringZZZ.vecMidFirst(sLineWithExpression, KernelExpressionMath_OperatorZZZ.getExpressionTagStarting(), KernelExpressionMath_OperatorZZZ.getExpressionTagClosing(), false);
		}
		return vecReturn;
	}
	
	
	//###### Getter / Setter
	public static String getExpressionTagName(){
		return "Z:operator";
	}
	public static String getExpressionTagStarting(){
		return "<" + KernelExpressionMath_OperatorZZZ.getExpressionTagName() + ">";
	}
	public static String getExpressionTagClosing(){
		return "</" + KernelExpressionMath_OperatorZZZ.getExpressionTagName() + ">"; 
	}		
	
	//+++++++
	public String getOperator(){
		return this.sOperator;
	}
	public void setOperator(String sOperator){
		this.sOperator = sOperator;
	}
}//End class