package basic.zKernel.file.ini;

import java.util.Vector;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.datatype.calling.ReferenceZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zKernel.IKernelConfigSectionEntryZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernel.config.KernelConfigSectionEntryUtilZZZ;

public class KernelZFormulaMathSolverZZZ<T>  extends AbstractKernelIniSolverZZZ<T> {
	private static final long serialVersionUID = -6400035649490240580L;
	public static String sTAG_NAME = "Z:math";
	
	public KernelZFormulaMathSolverZZZ() throws ExceptionZZZ{
		super();
	}
		
	public KernelZFormulaMathSolverZZZ(String[] saFlag) throws ExceptionZZZ{
		super(saFlag);
		KernelExpressionMathSolverNew_(saFlag);
	}
		
	public KernelZFormulaMathSolverZZZ(IKernelZZZ objKernel, String[] saFlag) throws ExceptionZZZ{
		super(objKernel);
		KernelExpressionMathSolverNew_(saFlag);
	}
	
	
	private boolean KernelExpressionMathSolverNew_(String[] saFlagControlIn) throws ExceptionZZZ {
		 boolean bReturn = false; 
		 main:{			 																
				if(this.getFlag("init")==true){
					bReturn = true;
					break main;
				}										
					
				bReturn = true;
		 	}//end main:
			return bReturn;
	 }//end function KernelExpressionMathSolverNew_
	
	
	//###### Getter / Setter
	@Override
	public String getNameDefault() throws ExceptionZZZ {
		return KernelZFormulaMathSolverZZZ.sTAG_NAME;
	}

	//### Andere Interfaces	
	@Override
	public boolean isStringForConvertRelevant(String sToProof) throws ExceptionZZZ {
		return false;
	}
	
	//### aus IParseEnabled
	@Override
	public Vector<String>parseFirstVector(String sLineWithExpression) throws ExceptionZZZ{
		return this.parseFirstVector(sLineWithExpression, true);
	}
	
	@Override
	public Vector<String>parseFirstVector(String sLineWithExpression, boolean bRemoveSurroundingSeparators) throws ExceptionZZZ{
		Vector<String>vecReturn = new Vector<String>();
		main:{
			if(StringZZZ.isEmpty(sLineWithExpression)) break main;
			
			
			//Mehrere Ausdruecke. Dann muss der jeweilige "Rest-Bestandteil" des ExpressionFirst-Vectors weiter zerlegt werden.
			vecReturn = this.parseFirstVector(sLineWithExpression);			
			String sExpression = (String) vecReturn.get(1);
			if(!StringZZZ.isEmpty(sExpression)){
					
				//Nun den z:operator Tag suchen
				ZTagFormulaMath_OperatorZZZ<T> objOperator = new ZTagFormulaMath_OperatorZZZ<T>();
				if(objOperator.isExpression(sExpression)){
					 sExpression = objOperator.parse(sExpression);					
				}else{
					//Da gibt es wohl nix weiter auszurechen...	also die Werte als String nebeneinander setzen....
					//Nun die z:value-of Einträge suchen, Diese werden jeweils zu eine String.
					ZTagFormulaMath_ValueZZZ<T> objValue = new ZTagFormulaMath_ValueZZZ<T>();
					
					String sExpressionOld = sExpression; 
					while(objValue.isExpression(sExpression)){
						sExpression = objValue.parse(sExpression);
//						String sDebug = (String) vecValue.get(1);
//						System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": Value01=" + sDebug);
//						System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": Gesamt-Reststring soweit=" + sExpression);
						
						if(sExpressionOld.equals(sExpression)) break; //Sicherheitsmassnahme gegen Endlosschleife
						sExpressionOld = sExpression;
					}					
				}
								
				String sValue = sExpression;
				
				//Den Wert ersetzen, wenn es was zu ersetzen gibt.
				if(sValue!=null){
					if(vecReturn.size()>=2) vecReturn.removeElementAt(1);
					vecReturn.add(1, sValue);
				}		
				
				// Z-Tags entfernen.
				if(bRemoveSurroundingSeparators) {
					String sTagStartZ = "<Z>";
					String sTagEndZ = "</Z>";
					KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(vecReturn, sTagStartZ, sTagEndZ);
				}
			}
		}
		return vecReturn;
	}
}//End class