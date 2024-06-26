package basic.zKernel.file.ini;

import java.util.Vector;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractList.VectorZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zKernel.IKernelZFormulaIniZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernel.AbstractKernelUseObjectZZZ;
import basic.zKernel.KernelZZZ;
import basic.zKernel.config.KernelConfigEntryUtilZZZ;
import basic.zKernel.flag.IFlagZUserZZZ;
import custom.zKernel.file.ini.FileIniZZZ;

public class KernelZFormulaMathSolverZZZ  extends AbstractKernelIniSolverZZZ {//KernelUseObjectZZZ implements IKernelZFormulaIniZZZ{
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
//	 boolean bReturn = false;
//	 String stemp; boolean btemp; 
//	 main:{
//		 	
//	 	//try{	 		
//	 			//setzen der �bergebenen Flags	
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
	
	
	
	public Vector<String>computeExpressionAllVector(String sLineWithExpression) throws ExceptionZZZ{
		Vector<String>vecReturn = new Vector<String>();
		main:{
			if(StringZZZ.isEmpty(sLineWithExpression)) break main;
			
			
			//Mehrere Ausdruecke. Dann muss der jeweilige "Rest-Bestandteil" des ExpressionFirst-Vectors weiter zerlegt werden.
			vecReturn = this.computeExpressionFirstVector(sLineWithExpression);			
			String sExpression = (String) vecReturn.get(1);
			if(!StringZZZ.isEmpty(sExpression)){
					
				//Nun den z:operator Tag suchen
				KernelZFormulaMath_OperatorZZZ objOperator = new KernelZFormulaMath_OperatorZZZ();
				if(objOperator.isExpression(sExpression)){
					 sExpression = objOperator.compute(sExpression);					
				}else{
					//Da gibt es wohl nix weiter auszurechen....	also die Werte als String nebeneinander setzen....
					//Nun die z:value-of Einträge suchen, Diese werden jeweils zu eine String.
					KernelZFormulaMath_ValueZZZ objValue = new KernelZFormulaMath_ValueZZZ();
					
					String sExpressionOld = sExpression; 
					while(objValue.isExpression(sExpression)){
						sExpression = objValue.compute(sExpression);
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
				String sTagStartZ = "<Z>";
				String sTagEndZ = "</Z>";
				KernelConfigEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(vecReturn, sTagStartZ, sTagEndZ);
			}
		}
		return vecReturn;
	}
	
	//###### Getter / Setter
	public String getExpressionTagName(){
		return "Z:math";
	}

	//### Andere Interfaces
	@Override
	public String convert(String sLine) throws ExceptionZZZ {
		return null;
	}

	@Override
	public boolean isStringForComputeRelevant(String sExpressionToProof) throws ExceptionZZZ {		
		return false;
	}

	@Override
	public boolean isStringForConvertRelevant(String sToProof) throws ExceptionZZZ {
		return false;
	}
}//End class