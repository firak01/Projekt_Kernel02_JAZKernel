package basic.zKernel.file.ini;

import java.util.HashMap;
import java.util.Set;
import java.util.Vector;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractList.HashMapCaseInsensitiveZZZ;
import basic.zBasic.util.abstractList.VectorZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernel.KernelUseObjectZZZ;
import basic.zKernel.KernelZZZ;
import custom.zKernel.file.ini.FileIniZZZ;

public class KernelExpressionIni_VariableZZZ  extends KernelUseObjectZZZ{
//	public enum FLAGZ{
//		USEFORMULA_MATH
//	}
	private HashMapCaseInsensitiveZZZ<String,String>hmVariable = null;
			
	public KernelExpressionIni_VariableZZZ() throws ExceptionZZZ{
		String[] saFlag = {"init"};
		KernelExpressionIniVariableNew_(null, saFlag);
	}
			
	public KernelExpressionIni_VariableZZZ(IKernelZZZ objKernel, HashMapCaseInsensitiveZZZ<String,String> hmVariableValue) throws ExceptionZZZ{
		super(objKernel);
		KernelExpressionIniVariableNew_(hmVariableValue, null);
	}
	
	public KernelExpressionIni_VariableZZZ(IKernelZZZ objKernel, HashMapCaseInsensitiveZZZ<String,String> hmVariableValue, String[] saFlag) throws ExceptionZZZ{
		super(objKernel);
		KernelExpressionIniVariableNew_(hmVariableValue, saFlag);
	}
	
	
	private boolean KernelExpressionIniVariableNew_(HashMapCaseInsensitiveZZZ<String,String> hmVariableValue, String[] saFlagControlIn) throws ExceptionZZZ {
	 boolean bReturn = false;
	 String stemp; boolean btemp; 
	 main:{		
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
				this.setHashMapVariable(hmVariableValue);
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
			
			//Nun die Section suchen
			Vector vecSection = this.computeExpressionFirstVector(sLineWithExpression);	
								
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
//				if(!StringZZZ.isEmpty(sBefore)){
					if(vecReturn.size()>=1) vecReturn.removeElementAt(0);
//					vecReturn.add(0, sBefore);
//				}else{
					vecReturn.add(0,vecSection.get(0));
//				}
														
				if(vecReturn.size()>=2) vecReturn.removeElementAt(1);
				vecReturn.add(1, sValue);
				
//				if(vecReturn.size()>=3) vecReturn.removeElementAt(2); //Immer den Namen der Property löschen....
//				if(!StringZZZ.isEmpty(sRest)){							
//					vecReturn.add(2, sRest); //Fallls vorhanden einen Restwert eintragen.
//				}else{
					vecReturn.add(2,vecSection.get(2));
//				}		
				
	
		}//end main:
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
			vecReturn = StringZZZ.vecMidFirst(sLineWithExpression, KernelExpressionIni_VariableZZZ.getExpressionTagStarting(), KernelExpressionIni_VariableZZZ.getExpressionTagClosing(), false,false);
		}
		return vecReturn;
	}
	
	
	public static boolean isExpression(String sLine){
		boolean bReturn = false;
		main:{
			boolean btemp = StringZZZ.contains(sLine, KernelExpressionIni_VariableZZZ.getExpressionTagStarting(), false);
			if(btemp==false) break main;
		
			btemp = StringZZZ.contains(sLine, KernelExpressionIni_VariableZZZ.getExpressionTagClosing(), false);
			if(btemp==false) break main;
			
			bReturn = true;
		}//end main
		return bReturn;
	}
	
	
	//###### Getter / Setter
	public static String getExpressionTagName(){
		return "z:Var"; 
	}
	public static String getExpressionTagStarting(){
		return "<" + KernelExpressionIni_VariableZZZ.getExpressionTagName() + ">";
	}
	public static String getExpressionTagClosing(){
		return "</" + KernelExpressionIni_VariableZZZ.getExpressionTagName() + ">"; 
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
}//End class