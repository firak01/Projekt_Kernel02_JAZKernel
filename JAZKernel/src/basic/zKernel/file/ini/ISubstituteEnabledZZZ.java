package basic.zKernel.file.ini;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractList.Vector3ZZZ;

public interface ISubstituteEnabledZZZ  extends IValueVariableUserZZZ, IKernelZFormulaIni_VariableZZZ, IKernelZFormulaIni_PathZZZ{
	boolean isSubstituteEnabledThis() throws ExceptionZZZ;
	boolean isSubstitutePathEnabledThis() throws ExceptionZZZ;
	boolean isSubstituteVariableEnabledThis() throws ExceptionZZZ;
	
	boolean isSubstituteRelevant() throws ExceptionZZZ;
	boolean isSubstitute(String sExpression) throws ExceptionZZZ;
	boolean isSubstitutePath(String sExpression) throws ExceptionZZZ;     //wenn der PathTag in der Expression zutrifft
	boolean isSubstituteVariable(String sExpression) throws ExceptionZZZ; //wenn der VariableTag in der Expression zutrifft
		
	public String substitute(String sExpression) throws ExceptionZZZ;
	public String substitute(String sExpression, boolean bRemoveSurroundingSeparators) throws ExceptionZZZ;

	public String substituteParsed(String sExpression) throws ExceptionZZZ;
	public String substituteParsed(String sExpression, boolean bRemoveSurroundingSeparators) throws ExceptionZZZ;
	
	public String substituteParsed(Vector3ZZZ<String> vecExpression) throws ExceptionZZZ;
	public String substituteParsed(Vector3ZZZ<String> vecExpression, boolean bRemoveSurroundingSeparators) throws ExceptionZZZ;
}
