package basic.zKernel.file.ini;

import java.util.Vector;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernel.config.KernelConfigSectionEntryUtilZZZ;
import custom.zKernel.file.ini.FileIniZZZ;

public class KernelZFormulaMathSolverZZZ<T>  extends AbstractKernelIniSolverZZZ<T>  implements IKernelZFormulaIniZZZ{
	private static final long serialVersionUID = -6400035649490240580L;
	public static String sTAG_NAME = "Z:math";
	
	public KernelZFormulaMathSolverZZZ() throws ExceptionZZZ{
		super();
	}
		
	public KernelZFormulaMathSolverZZZ(String[] saFlag) throws ExceptionZZZ{
		super(saFlag);
		KernelExpressionMathSolverNew_();
	}
	
	public KernelZFormulaMathSolverZZZ(IKernelZZZ objKernel) throws ExceptionZZZ{
		super(objKernel);
		KernelExpressionMathSolverNew_();
	}
	
	public KernelZFormulaMathSolverZZZ(IKernelZZZ objKernel, String[] saFlag) throws ExceptionZZZ{
		super(objKernel);
		KernelExpressionMathSolverNew_();
	}
	
	public KernelZFormulaMathSolverZZZ(IKernelZZZ objKernel, FileIniZZZ<T> objFileIni, String[] saFlag)  throws ExceptionZZZ{
		super(objKernel, objFileIni, saFlag);
		KernelExpressionMathSolverNew_();
	}
	
	
	private boolean KernelExpressionMathSolverNew_() throws ExceptionZZZ {
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
	
	//Analog zu KernelJsonMapInisolver, KernelEncrytptionIniSolver aufbauen...
	@Override
	public Vector<String>parseFirstVector(String sLineWithExpression, boolean bRemoveSurroundingSeparators) throws ExceptionZZZ{
		Vector<String>vecReturn = new Vector<String>();
		String sReturn = sLineWithExpression;
		main:{
			if(StringZZZ.isEmpty(sLineWithExpression)) break main;
			boolean bUseFormula = this.getFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA);		
			if(!bUseFormula) break main;
			
			boolean bUseFormulaMath = this.getFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH);		
			if(!bUseFormulaMath) break main;
			
			
			//Mehrere Ausdruecke. Dann muss der jeweilige "Rest-Bestandteil" des ExpressionFirst-Vectors weiter zerlegt werden.
			//Im Aufruf der Eltern-Methode findet ggfs. auch eine Aufloesung von Pfaden und eine Ersetzung von Variablen statt.
			//Z:math drumherum entfernen
			vecReturn = super.parseFirstVector(sLineWithExpression, bRemoveSurroundingSeparators);			
			String sExpression = (String) vecReturn.get(1);
			if(StringZZZ.isEmpty(sExpression)) break main;
			
			//++++++++++++++++++++++++++++++
		    //Das Ziel ist, das der Operator nur mit der reinen Formel arbeitet. Da er umgebenden Text nicht herausfiltern kann.
			//Nun den z:operator Tag suchen
			String sFormulaExpression = sExpression;
			ZTagFormulaMath_OperatorZZZ<T> objOperator = new ZTagFormulaMath_OperatorZZZ<T>();
			if(objOperator.isExpression(sFormulaExpression)){
				 sExpression = objOperator.parse(sFormulaExpression);
				 sReturn = sExpression;
			}else{
				//Da gibt es wohl nix weiter auszurechen...	also die Werte als String nebeneinander setzen....
				//Nun die z:value-of Einträge suchen, Diese werden jeweils zu eine String.
				ZTagFormulaMath_ValueZZZ<T> objValue = new ZTagFormulaMath_ValueZZZ<T>();
				
				String sExpressionOld = sFormulaExpression; 
				while(objValue.isExpression(sExpressionOld)){
					sExpression = objValue.parse(sExpressionOld);
//						String sDebug = (String) vecValue.get(1);
//						System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": Value01=" + sDebug);
//						System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": Gesamt-Reststring soweit=" + sExpression);
					
					if(sExpressionOld.equals(sExpression)) break; //Sicherheitsmassnahme gegen Endlosschleife
					sExpressionOld = sExpression;
				}													
				 sReturn = sExpression;
			}//end if operator.isExpression(...)
			
			
		}//end main:
		
		//Den Wert ersetzen, wenn es was zu ersetzen gibt.
		if(sReturn!=null){
			if(vecReturn.size()==0) vecReturn.add(0,"");
			
			if(vecReturn.size()>=2) vecReturn.removeElementAt(1);
			if(!StringZZZ.isEmpty(sReturn)){
				vecReturn.add(1, sReturn);
			}else {
				vecReturn.add(1, "");
			}
			
			if(vecReturn.size()==2) vecReturn.add(2,"");			
		}		
		
		// Z-Tags entfernen.
		if(bRemoveSurroundingSeparators) {
			String sTagStartZ = "<Z>";
			String sTagEndZ = "</Z>";
			KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(vecReturn, sTagStartZ, sTagEndZ);
		}
		return vecReturn;
	}
	
	//##########################
	//### FLAG Handling
	
	//### Aus Interface IKernelZFormulaIniZZZ
	@Override
	public boolean getFlag(IKernelZFormulaIniZZZ.FLAGZ objEnum_IKernelEncryptionIniSolverZZZ) {
		return this.getFlag(objEnum_IKernelEncryptionIniSolverZZZ.name());
	}
	
	@Override
	public boolean setFlag(IKernelZFormulaIniZZZ.FLAGZ objEnum_IKernelEncryptionIniSolverZZZ, boolean bFlagValue) throws ExceptionZZZ {
		return this.setFlag(objEnum_IKernelEncryptionIniSolverZZZ.name(), bFlagValue);
	}
	
	
	@Override
	public boolean[] setFlag(IKernelZFormulaIniZZZ.FLAGZ[] objaEnum_IKernelEncryptionIniSolverZZZ, boolean bFlagValue) throws ExceptionZZZ {
		boolean[] baReturn=null;
		main:{
			if(!ArrayUtilZZZ.isNull(objaEnum_IKernelEncryptionIniSolverZZZ)) {
				baReturn = new boolean[objaEnum_IKernelEncryptionIniSolverZZZ.length];
				int iCounter=-1;
				for(IKernelZFormulaIniZZZ.FLAGZ objEnum_IKernelEncryptionIniSolverZZZ:objaEnum_IKernelEncryptionIniSolverZZZ) {
					iCounter++;
					boolean bReturn = this.setFlag(objEnum_IKernelEncryptionIniSolverZZZ, bFlagValue);
					baReturn[iCounter]=bReturn;
				}
			}
		}//end main:
		return baReturn;
	}

	@Override
	public boolean proofFlagExists(IKernelZFormulaIniZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
		return this.proofFlagExists(objEnumFlag.name());
	}
	
	@Override
	public boolean proofFlagSetBefore(IKernelZFormulaIniZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
			return this.proofFlagSetBefore(objEnumFlag.name());
	}
}//End class