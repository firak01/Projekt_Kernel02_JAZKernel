package basic.zKernel.file.ini;

import java.util.Vector;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractList.VectorZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zKernel.KernelUseObjectZZZ;
import basic.zKernel.KernelZZZ;
import custom.zKernel.file.ini.FileIniZZZ;

public class KernelExpressionMathSolverZZZ  extends KernelUseObjectZZZ{
//	public enum FLAGZ{
//		USEFORMULA_MATH
//	}
		
	public KernelExpressionMathSolverZZZ() throws ExceptionZZZ{
		String[] saFlag = {"init"};
		KernelExpressionMathSolverNew_(saFlag);
	}
		
	public KernelExpressionMathSolverZZZ(String[] saFlag) throws ExceptionZZZ{		
		KernelExpressionMathSolverNew_(saFlag);
	}
	
	public KernelExpressionMathSolverZZZ(KernelZZZ objKernel, String[] saFlag) throws ExceptionZZZ{
		super(objKernel);
		KernelExpressionMathSolverNew_(saFlag);
	}
	
	
	private boolean KernelExpressionMathSolverNew_(String[] saFlagControlIn) throws ExceptionZZZ {
	 boolean bReturn = false;
	 String stemp; boolean btemp; 
	 main:{
		 	
	 	//try{	 		
	 			//setzen der �bergebenen Flags	
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
	
	public String compute(String sLineWithExpression) throws ExceptionZZZ{
		String sReturn = null;
		main:{
			if(StringZZZ.isEmpty(sLineWithExpression)) break main;
			
			Vector vecAll = this.computeExpressionAllVector(sLineWithExpression);
			
			//Der Vector ist schon so aufbereiten, dass hier nur noch "zusammenaddiert" werden muss
			sReturn = VectorZZZ.implode(vecAll);
			
		}//end main:
		return sReturn;
	}
	
	public Vector computeExpressionAllVector(String sLineWithExpression) throws ExceptionZZZ{
		Vector vecReturn = new Vector();
		main:{
			if(StringZZZ.isEmpty(sLineWithExpression)) break main;
			
			
			//Mehrere Ausdr�cke. Dann muss der jeweilige "Rest-Bestandteil" des ExpressionFirst-Vectors weiter zerlegt werden.
			vecReturn = this.computeExpressionFirstVector(sLineWithExpression);			
			String sExpression = (String) vecReturn.get(1);
			if(!StringZZZ.isEmpty(sExpression)){
					
				//Nun den z:operator suchen
				KernelExpressionMath_OperatorZZZ objOperator = new KernelExpressionMath_OperatorZZZ();
				if(objOperator.isExpression(sExpression)){
					//Nun die z:value-of suchen
					String sValue01 = null;
					String sValue02 = null;
					
					//links vom Operator
					KernelExpressionMath_ValueZZZ objValue01 = new KernelExpressionMath_ValueZZZ();
					if(objValue01.isExpression(sExpression)){
						Vector vecValue = objValue01.computeExpressionFirstVector(sExpression);
						sValue01 = (String) vecValue.get(1);
						sExpression = VectorZZZ.implode(vecValue);
					}	
					
					//rechts vom Operator
					KernelExpressionMath_ValueZZZ objValue02 = new KernelExpressionMath_ValueZZZ();
					if(objValue01.isExpression(sExpression)){
						Vector vecValue = objValue01.computeExpressionFirstVector(sExpression);
						sValue02 = (String) vecValue.get(1);
						sExpression = VectorZZZ.implode(vecValue);
					}	
					
					 Vector vecValue = objOperator.computeExpressionFirstVector(sExpression); //TEST: Damit holt man den Operator selbst
					String sOperator = (String) vecValue.get(1);
					 objOperator.setOperator(sOperator);
					 
					//... und dann compute, mit den beiden Werten...
					sExpression = objOperator.compute(sValue01, sValue02);
					
				}else{
					//Da gibt es wohl nix weiter auszurechen....
					//Also die Werte als String nebeneinander setzen....
					//Nun die z:value-of Einträge suchen, Diese werden jeweils zu eine String.
					KernelExpressionMath_ValueZZZ objValue = new KernelExpressionMath_ValueZZZ();
					while(objValue.isExpression(sExpression)){
						Vector vecValue = objValue.computeExpressionFirstVector(sExpression);
//						String sDebug = (String) vecValue.get(1);
//						System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": Value01=" + sDebug);
						sExpression = VectorZZZ.implode(vecValue);
//						System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": Gesamtstring soweit=" + sExpression);
					}					
				}
								
				String sValue = sExpression;
				
				//Den Wert ersetzen, wenn es was zu ersetzen gibt.
				if(sValue!=null){
					vecReturn.removeElementAt(1);
					vecReturn.add(1, sValue);
				}
				
//				TODO: Verschachtelung der Ausdrücke. Dann muss das jeweilige "Vector Element" des ExpressionFirst-Vectors erneut mit this.computeExpressionFirstVector(...) zerlegt werden.
													
			}
			
			
		}
		return vecReturn;
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
			vecReturn = StringZZZ.vecMidFirst(sLineWithExpression, KernelExpressionMathSolverZZZ.getExpressionTagStarting(), KernelExpressionMathSolverZZZ.getExpressionTagClosing(), false);
		}
		return vecReturn;
	}
	
	
	public static boolean isExpression(String sLine){
		boolean bReturn = false;
		main:{
			boolean btemp = StringZZZ.contains(sLine, KernelExpressionMathSolverZZZ.getExpressionTagStarting());
			if(btemp==false) break main;
		
			btemp = StringZZZ.contains(sLine, KernelExpressionMathSolverZZZ.getExpressionTagClosing());
			if(btemp==false) break main;
			
			bReturn = true;
		}//end main
		return bReturn;
	}
	
	
	//###### Getter / Setter
	public static String getExpressionTagName(){
		return "Z:math";
	}
	public static String getExpressionTagStarting(){
		return "<" + KernelExpressionMathSolverZZZ.getExpressionTagName() + ">";
	}
	public static String getExpressionTagClosing(){
		return "</" + KernelExpressionMathSolverZZZ.getExpressionTagName() + ">"; 
	}		
}//End class