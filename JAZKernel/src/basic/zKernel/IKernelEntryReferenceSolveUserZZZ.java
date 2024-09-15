package basic.zKernel;

import java.util.Vector;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.datatype.calling.ReferenceZZZ;
import basic.zKernel.file.ini.IExpressionUserZZZ;
import basic.zKernel.file.ini.ISolveUserZZZ;

//### Damit bleiben die Werte in der Variablen IKernelConfigSectionEntryZZZ objEntry erhalten 
public interface IKernelEntryReferenceSolveUserZZZ extends IKernelConfigSectionEntryUserZZZ, ISolveUserZZZ{
	public int solve(String sLineWithExpression, ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference) throws ExceptionZZZ;
	public int solve(String sLineWithExpression, ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference, boolean bRemoveSurroundingSeparators) throws ExceptionZZZ;
	
	public Vector<String>solveFirstVector(String sLineWithExpression, ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReference) throws ExceptionZZZ;		
	public Vector<String>solveFirstVector(String sLineWithExpression, ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReference, boolean bRemoveSurroundingSeparators) throws ExceptionZZZ;
		
}
