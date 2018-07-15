package basic.zKernel.file.ini;

import java.util.Vector;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractList.VectorZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zKernel.KernelUseObjectZZZ;
import basic.zKernel.KernelZZZ;
import custom.zKernel.file.ini.FileIniZZZ;

public class KernelExpressionMath_ValueZZZ  extends KernelUseObjectZZZ{
//	public enum FLAGZ{
//		USEFORMULA_MATH
//	}
		
	public KernelExpressionMath_ValueZZZ() throws ExceptionZZZ{
		String[] saFlag = {"init"};
		KernelExpressionMathValueNew_(saFlag);
	}
		
	public KernelExpressionMath_ValueZZZ(String[] saFlag) throws ExceptionZZZ{		
		KernelExpressionMathValueNew_(saFlag);
	}
	
	public KernelExpressionMath_ValueZZZ(KernelZZZ objKernel, String[] saFlag) throws ExceptionZZZ{
		super(objKernel);
		KernelExpressionMathValueNew_(saFlag);
	}
	
	
	private boolean KernelExpressionMathValueNew_(String[] saFlagControlIn) throws ExceptionZZZ {
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
			
			
			//TODO: Mehrere Ausdr�cke. Dann muss der jeweilige "Rest-Bestandteil" des ExpressionFirst-Vectors weiter zerlegt werden.
			vecReturn = this.computeExpressionFirstVector(sLineWithExpression);			
//			String sExpression = (String) vecReturn.get(1);
//			if(!StringZZZ.isEmpty(sExpression)){
//					
//				//Nun den z:operator suchen
//				
//				//Nun die z:value-of suchen
//				//links vom Operator
//				
//				//rechts vom Operator
//				
//				
//
//				
//				//Den Wert ersetzen
//				vecReturn.removeElementAt(1);
//				vecReturn.add(1, sValue);
//				
////				TODO: Verschachtelung der Ausdrücke. Dann muss das jeweilige "Vector Element" des ExpressionFirst-Vectors erneut mit this.computeExpressionFirstVector(...) zerlegt werden.
//				
//				//Nun den Vector der "übersetzten" Werte zusammensetzen
//				String sLineWithExpression2Check = VectorZZZ.implode(vecReturn);
//								
//				
//				//TODO GOON: Hole Ausdrücke mit <z:math>...</z:math>							
//				//1. Ist das Flag gesetzt
//				
//				//Ja: Dann erzeuge neues KernelExpressionMathSolverZZZ - Objekt.
//				KernelExpressionMath_ValueZZZ objMathSolver = new KernelExpressionMath_ValueZZZ(); //Dummy, um die Flags zu bekommen
//				
//				
//				
//				
//				//2. Ist in dem String math?
//				//aus dem vecReturn wieder einen String machen, in dem ein Wert ersetzt ist.
//				//Danach den Math-Teil herausholen und in einen neuen vec packen.
//				
//				
//				
//				
//			}
			
			
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
			vecReturn = StringZZZ.vecMidFirst(sLineWithExpression, KernelExpressionMath_ValueZZZ.getExpressionTagStarting(), KernelExpressionMath_ValueZZZ.getExpressionTagClosing(), false);
		}
		return vecReturn;
	}
	
	
	public static boolean isExpression(String sLine){
		boolean bReturn = false;
		main:{
			boolean btemp = StringZZZ.contains(sLine, KernelExpressionMath_ValueZZZ.getExpressionTagStarting());
			if(btemp==false) break main;
		
			btemp = StringZZZ.contains(sLine, KernelExpressionMath_ValueZZZ.getExpressionTagClosing());
			if(btemp==false) break main;
			
			bReturn = true;
		}//end main
		return bReturn;
	}
	
	
	//###### Getter / Setter
	public static String getExpressionTagName(){
		return "Z:value-of";
	}
	public static String getExpressionTagStarting(){
		return "<" + KernelExpressionMath_ValueZZZ.getExpressionTagName() + ">";
	}
	public static String getExpressionTagClosing(){
		return "</" + KernelExpressionMath_ValueZZZ.getExpressionTagName() + ">"; 
	}		
}//End class