package basic.zKernel;

import java.util.Vector;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractList.Vector3ZZZ;
import basic.zBasic.util.datatype.calling.ReferenceZZZ;
import basic.zKernel.file.ini.IExpressionUserZZZ;
import basic.zKernel.file.ini.ISolveUserZZZ;

//### Damit bleiben die Werte in der Variablen IKernelConfigSectionEntryZZZ objEntry erhalten 
public interface IKernelEntryReferenceSolveUserZZZ extends IKernelConfigSectionEntryUserZZZ, ISolveUserZZZ{
	public String solve(String sExpression, ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference) throws ExceptionZZZ;
	public String solve(String sExpression, ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference, boolean bRemoveSurroundingSeparators) throws ExceptionZZZ;
	
	public String solveParsed(String sExpression, ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReference) throws ExceptionZZZ;		
	public String solveParsed(String sExpression, ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReference, boolean bRemoveSurroundingSeparators) throws ExceptionZZZ;
	
	public String solveParsed(Vector3ZZZ<String> vecExpression, ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReference, boolean bRemoveSuroundingSeparators) throws ExceptionZZZ;
		
	public Vector3ZZZ<String> parseFirstVectorSolverCustomPost(Vector3ZZZ<String> vecExpression, ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReference) throws ExceptionZZZ;		
	public Vector3ZZZ<String> parseFirstVectorSolverCustomPost(Vector3ZZZ<String> vecExpression, ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReference, boolean bRemoveSurroundingSeparators) throws ExceptionZZZ;	
}
