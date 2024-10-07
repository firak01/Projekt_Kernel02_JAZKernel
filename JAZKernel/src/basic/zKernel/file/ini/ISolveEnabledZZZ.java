package basic.zKernel.file.ini;

import java.util.Vector;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IObjectWithExpressionZZZ;
import basic.zBasic.IValueComputedBufferedUserZZZ;
import basic.zBasic.util.abstractList.Vector3ZZZ;
import basic.zBasic.util.datatype.calling.ReferenceZZZ;
import basic.zKernel.IKernelConfigSectionEntryUserZZZ;
import basic.zKernel.IKernelConfigSectionEntryZZZ;

public interface ISolveEnabledZZZ{
	public String solve(String sExpression) throws ExceptionZZZ;	//ruft parse() auf... und anschliessend solveParsed()....
	public String solve(String sExpression, boolean bRemoveSuroundingSeparators) throws ExceptionZZZ;

	public String solveParsed(String sExpression, boolean bRemoveSuroundingSeparators) throws ExceptionZZZ;
	public String solveParsed(String sExpression) throws ExceptionZZZ;
	public String solveParsed(Vector3ZZZ<String> vecExpression) throws ExceptionZZZ;
	public String solveParsed(Vector3ZZZ<String> vecExpression, boolean bRemoveSuroundingSeparators) throws ExceptionZZZ;
		
	boolean isSolveRelevant(String sExpressionToProof) throws ExceptionZZZ;//z.B. als Ausdrück für KernelExpressionIni_EmptyZZZ wäre relevant: <z:Empty/>, d.h. true zurück.
	
}
