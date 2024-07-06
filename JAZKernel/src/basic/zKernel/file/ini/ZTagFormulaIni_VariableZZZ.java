package basic.zKernel.file.ini;

import java.util.Set;
import java.util.Vector;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.formula.AbstractIniTagSimpleZZZ;
import basic.zBasic.util.abstractList.HashMapCaseInsensitiveZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zKernel.flag.IFlagZUserZZZ;

public class ZTagFormulaIni_VariableZZZ<T>  extends AbstractIniTagSimpleZZZ<T>{
	private static final long serialVersionUID = 6370617551800139734L;
	public static String sTAG_NAME = "z:Var"; 
	private HashMapCaseInsensitiveZZZ<String,String>hmVariable = null;
			
	public ZTagFormulaIni_VariableZZZ() throws ExceptionZZZ{
		super("init");
		KernelExpressionIniVariableNew_(null, null);
	}
	
	public ZTagFormulaIni_VariableZZZ(HashMapCaseInsensitiveZZZ<String,String> hmVariableValue) throws ExceptionZZZ{
		super();
		KernelExpressionIniVariableNew_(hmVariableValue, null);
	}
	
	public ZTagFormulaIni_VariableZZZ(HashMapCaseInsensitiveZZZ<String,String> hmVariableValue, String[] saFlagControl) throws ExceptionZZZ{
		super(saFlagControl);
		KernelExpressionIniVariableNew_(hmVariableValue, saFlagControl);
	}
		
	private boolean KernelExpressionIniVariableNew_(HashMapCaseInsensitiveZZZ<String,String> hmVariableValue, String[] saFlagControlIn) throws ExceptionZZZ {
	 boolean bReturn = false;
	 main:{		
			if(this.getFlag("init")==true){
				bReturn = true;
				break main;
			}										
				
			this.setHashMapVariable(hmVariableValue);
	 	}//end main:
		return bReturn;
	 }//end function KernelExpressionIniVariableNew_
		
	
	/* (non-Javadoc)
	 * @see basic.zKernel.file.ini.AbstractKernelIniTagZZZ#computeExpressionAllVector(java.lang.String)
	 * 
	 * BESONERHEIT HIER: VARIABLENERSETZUNG!!!
	 */
	public Vector<String> computeExpressionAllVector(String sLineWithExpression) throws ExceptionZZZ{
		Vector<String> vecReturn = new Vector<String>();		
		main:{
			if(StringZZZ.isEmpty(sLineWithExpression)) break main;
			
			//Nun die Section suchen
			Vector<String> vecSection = this.computeExpressionFirstVector(sLineWithExpression);	
								
			String sVariableName = (String) vecSection.get(1);
		    String sValue = null;
			if(!(StringZZZ.isEmpty(sVariableName))){
				sValue = this.getVariable(sVariableName);									
//				HashMapCaseInsensitiveZZZ<String,String> hmVariableValue = this.getHashMapVariable();
//					if(hmVariableValue==null){
//						ExceptionZZZ ez = new ExceptionZZZ("HashMapCaseInsensitiveZZZ VariableValuei", iERROR_PROPERTY_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
//						throw ez;
//					}
//					
//					//Nicht caseSensitive Variablen
//					sReturn = (String) hmVariableValue.get(sVariableName);
					
			}//end if isempty(sVariableName)
			
			//NUN DEN INNERHALB DER EXPRESSION BERECHUNG ERSTELLTEN WERT in den Return-Vector übernehmen						
			//Den Wert ersetzen, aber nur, wenn es auch etwas zu ersetzen gibt.
			if(sValue==null){
				//Setze den Variablennamen dort ein
				sValue = sVariableName;
			}
				
				//Dann hat man auch den Fall, dass dies Bestandteil einer Formel ist. Also den Wert vorher und den Rest in den Vektor packen
				if(vecReturn.size()>=1) vecReturn.removeElementAt(0);
				vecReturn.add(0,vecSection.get(0));
														
				if(vecReturn.size()>=2) vecReturn.removeElementAt(1);
				vecReturn.add(1, sValue);
				
				if(vecReturn.size()>=3) vecReturn.removeElementAt(2);
				vecReturn.add(2,vecSection.get(2));	
		}//end main:
		return vecReturn;
	}
	
	//###### Getter / Setter
	//Merke: Erst ab Java 8 können static Ausdrücke in ein interface
	public String getExpressionTagName(){
		return ZTagFormulaIni_VariableZZZ.sTAG_NAME;
	}
		
	public void setHashMapVariable(HashMapCaseInsensitiveZZZ<String,String> hmVariable){
		this.hmVariable = hmVariable;
	}
	public HashMapCaseInsensitiveZZZ<String,String> getHashMapVariable(){
		return this.hmVariable;
	}
	
	public void setVariable(HashMapCaseInsensitiveZZZ<String,String> hmVariable){
		if(this.hmVariable==null){
			this.hmVariable = hmVariable;
		}else{
			if(hmVariable==null){
				//nix....
			}else{
				//füge Werte hinzu.
				Set<String> sSet =  this.hmVariable.keySet();
				for(String sKey : sSet){
					this.hmVariable.put(sKey, (String)hmVariable.get(sKey));
				}
			}
		}
	}
	
	public String getVariable(String sKey){
		String sReturn = null;
		main:{
			if(StringZZZ.isEmpty(sKey)) break main;
			
			 HashMapCaseInsensitiveZZZ hmVariable = this.getHashMapVariable();
			 if(hmVariable==null) break main;
			 
			 sReturn = (String)hmVariable.get(sKey);
		}
		return sReturn;
	}

	//### Aus Interface IKernelExpressionIniZZZ
		
		/* (non-Javadoc)
		 * @see basic.zKernel.file.ini.AbstractKernelIniTagZZZ#compute(java.lang.String)
		 * 
		 * Besonderheit: Hier wird der Vector mit implode zusammengefasst.
		 */
//		@Override
//		public String compute(String sLineWithExpression) throws ExceptionZZZ{
//			String sReturn = null;
//			main:{
//				if(StringZZZ.isEmptyTrimmed(sLineWithExpression)) break main;
//				
//				Vector vecAll = this.computeExpressionAllVector(sLineWithExpression);
//				
////				sReturn = (String) vecAll.get(1);
////				this.setValue(sReturn);
//				
//				//Der Vector ist schon so aufbereiten, dass hier nur noch "zusammenaddiert" werden muss
//				sReturn = VectorZZZ.implode(vecAll);
//				
//			}//end main:
//			return sReturn;
//		}
}//End class