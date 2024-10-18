package basic.zKernel.file.ini;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractList.Vector3ZZZ;

public interface ISolveEnabledZZZ{
	public String solve(String sExpression) throws ExceptionZZZ;	//ruft parse() auf... und anschliessend solveParsed()....
	public String solve(String sExpression, boolean bRemoveSuroundingSeparators) throws ExceptionZZZ;

	public String solveParsed(String sExpression, boolean bRemoveSuroundingSeparators) throws ExceptionZZZ;
	public String solveParsed(String sExpression) throws ExceptionZZZ;
	public String solveParsed(Vector3ZZZ<String> vecExpression) throws ExceptionZZZ;
	public String solveParsed(Vector3ZZZ<String> vecExpression, boolean bRemoveSuroundingSeparators) throws ExceptionZZZ;
	
	boolean isSolveRelevant(String sExpressionToProof) throws ExceptionZZZ;//z.B. als Ausdrück für KernelExpressionIni_EmptyZZZ wäre relevant: <z:Empty/>, d.h. true zurück.
	
	public Vector3ZZZ<String> parseFirstVectorSolverCustomPost(Vector3ZZZ<String> vecExpression) throws ExceptionZZZ;
	public Vector3ZZZ<String> parseFirstVectorSolverCustomPost(Vector3ZZZ<String> vecExpression, boolean bRemoveSuroundingSeparators) throws ExceptionZZZ;
}
