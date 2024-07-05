package basic.zKernel.file.ini;

import java.util.Vector;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.formula.AbstractIniTagCascadedZZZ;
import basic.zBasic.util.abstractList.VectorZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;

public class ZTagFormulaMath_ValueZZZ<T>  extends AbstractIniTagCascadedZZZ<T>{
	private static final long serialVersionUID = -6014276444711828780L;
	public static String sTAG_NAME = "Z:val";
	public ZTagFormulaMath_ValueZZZ() throws ExceptionZZZ{
		super();
	}
		
	public ZTagFormulaMath_ValueZZZ(String[] saFlag) throws ExceptionZZZ{	
		super(saFlag);
		KernelExpressionMathValueNew_();
	}
	
		
	private boolean KernelExpressionMathValueNew_() throws ExceptionZZZ {
	 boolean bReturn = false;
	 main:{
		if(this.getFlag("init")==true){
			bReturn = true;
			break main;
		}
		
		
		}//end main:
		return bReturn;
	 }//end function KernelExpressionMathSolverNew_
	
	
	public Vector<String> computeExpressionAllVector(String sLineWithExpression) throws ExceptionZZZ{
		Vector<String> vecReturn = new Vector<String>();
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
		return ZTagFormulaMath_ValueZZZ.sTAG_NAME;
	}				
}//End class