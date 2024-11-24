package basic.zKernel.file.ini;

import java.util.Set;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IObjectWithExpressionZZZ;
import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.abstractList.HashMapCaseInsensitiveZZZ;
import basic.zBasic.util.abstractList.Vector3ZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zKernel.IKernelConfigSectionEntryZZZ;
import basic.zKernel.config.KernelConfigSectionEntryUtilZZZ;

/**
 * @author fl86kyvo
 *
 * @param <T>
 */

//Merke: Klasse benötigt Flags, aber keinen Kernel...
public class ZTagFormulaIni_VariableZZZ<T>  extends AbstractIniTagWithExpressionBasicZZZ<T> implements IKernelZFormulaIni_VariableZZZ{
	private static final long serialVersionUID = 6370617551800139734L;
	public static String sTAG_NAME = "z:Var";
	private HashMapCaseInsensitiveZZZ<String,String>hmVariable = null;
		
	public ZTagFormulaIni_VariableZZZ() throws ExceptionZZZ{
		super(""); //"init" Flag setzen lassen, da wir ja keine Map mitbekommen.
		KernelExpressionIniVariableNew_(null);
	}
	
	public ZTagFormulaIni_VariableZZZ(HashMapCaseInsensitiveZZZ<String,String> hmVariableValue) throws ExceptionZZZ{
		super(); //kein "init" Flag setzen lassen, da wir ja eine Map mitbekommen.
		KernelExpressionIniVariableNew_(hmVariableValue);
	}
	
	public ZTagFormulaIni_VariableZZZ(HashMapCaseInsensitiveZZZ<String,String> hmVariableValue, String[] saFlagControl) throws ExceptionZZZ{
		super(saFlagControl);
		KernelExpressionIniVariableNew_(hmVariableValue);
	}
			
	private boolean KernelExpressionIniVariableNew_(HashMapCaseInsensitiveZZZ<String,String> hmVariableValue) throws ExceptionZZZ {
	 boolean bReturn = false;
	 	main:{	
		 	if(this.getFlag("init")==true){
				bReturn = true;
				break main;
			}
			this.setHashMapVariable(hmVariableValue);
			
			bReturn = true;
	 	}//end main:
		return bReturn;
	 }//end function KernelExpressionIniVariableNew_
	
	
	/**
	 * Gibt einen Vector zurück, in dem das erste Element der Ausdruck VOR der
	 * ersten 'Expression' ist. Das 2. Element ist die Expression. Das 3. Element
	 * ist der Ausdruck NACH der ersten Expression.
	 *
	 * BESONERHEIT HIER: VARIABLENERSETZUNG!!!
	 * 
	 * @param sLineWithExpression
	 * @throws ExceptionZZZ
	 */
	@Override
	public Vector3ZZZ<String>parseFirstVector(String sLineWithExpression) throws ExceptionZZZ{
		return this.parseFirstVector_(sLineWithExpression, true);
	}
	
	@Override
	public Vector3ZZZ<String>parseFirstVector(String sLineWithExpression, boolean bRemoveSurroundingSeparators) throws ExceptionZZZ{
		return this.parseFirstVector_(sLineWithExpression, bRemoveSurroundingSeparators);
	}
	
	private Vector3ZZZ<String>parseFirstVector_(String sLineWithExpression, boolean bRemoveSurroundingSeparators) throws ExceptionZZZ{
		Vector3ZZZ<String>vecReturn = new Vector3ZZZ<String>();
		String sReturn = sLineWithExpression;
		boolean bUseExpression = false;
		main:{
			if(StringZZZ.isEmpty(sLineWithExpression)) break main;
			
			bUseExpression = this.getFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION);
			if(!bUseExpression) break main;
			
			boolean bUseExpressionPath = this.getFlag(IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE);
			if(!bUseExpressionPath) break main;
			
			//Bei dem einfachen Tag wird die naechste Tag genommen und dann auch das naechste schliessende Tag...
			Vector3ZZZ<String> vecSection = StringZZZ.vecMidFirst(sLineWithExpression, this.getTagStarting(), this.getTagClosing(), false, false);
			
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
			vecReturn.replace(vecSection.get(0), sValue, vecSection.get(2));
			
			// Z-Tags entfernen.
			if(bRemoveSurroundingSeparators) {
				String sTagStartZ = "<Z>";
				String sTagEndZ = "</Z>";
				KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(vecReturn, sTagStartZ, sTagEndZ);
			}
			
			this.setValue((String) vecReturn.get(1));
		} //end main
		return vecReturn;
	}
	
	//###### Getter / Setter	
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
			
			 HashMapCaseInsensitiveZZZ<String,String> hmVariable = this.getHashMapVariable();
			 if(hmVariable==null) break main;
			 
			 sReturn = (String)hmVariable.get(sKey);
		}
		return sReturn;
	}

	@Override
	public String getNameDefault() throws ExceptionZZZ {
		return sTAG_NAME;
	}

	@Override
	public boolean isConvertRelevant(String sStringToProof) throws ExceptionZZZ {
		return false;
	}

	//###################################
	//### FLAG Handling
	
	//### aus IKernelZVariableIniSolverZZZ	
	@Override
	public boolean getFlag(IKernelZFormulaIni_VariableZZZ.FLAGZ objEnumFlag) {
		return this.getFlag(objEnumFlag.name());
	}
	@Override
	public boolean setFlag(IKernelZFormulaIni_VariableZZZ.FLAGZ objEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		return this.setFlag(objEnumFlag.name(), bFlagValue);
	}
	
	@Override
	public boolean[] setFlag(IKernelZFormulaIni_VariableZZZ.FLAGZ[] objaEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		boolean[] baReturn=null;
		main:{
			if(!ArrayUtilZZZ.isNull(objaEnumFlag)) {
				baReturn = new boolean[objaEnumFlag.length];
				int iCounter=-1;
				for(IKernelZFormulaIni_VariableZZZ.FLAGZ objEnumFlag:objaEnumFlag) {
					iCounter++;
					boolean bReturn = this.setFlag(objEnumFlag, bFlagValue);
					baReturn[iCounter]=bReturn;
				}
			}
		}//end main:
		return baReturn;
	}
	
	@Override
	public boolean proofFlagExists(IKernelZFormulaIni_VariableZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
			return this.proofFlagExists(objEnumFlag.name());
	}
	
	@Override
	public boolean proofFlagSetBefore(IKernelZFormulaIni_VariableZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
			return this.proofFlagSetBefore(objEnumFlag.name());
	}
}//End class