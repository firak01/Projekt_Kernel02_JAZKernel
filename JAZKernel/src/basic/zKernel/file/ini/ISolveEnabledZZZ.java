package basic.zKernel.file.ini;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractList.Vector3ZZZ;
import basic.zBasic.util.datatype.calling.ReferenceZZZ;
import basic.zKernel.IKernelConfigSectionEntryZZZ;

public interface ISolveEnabledZZZ{
	boolean isSolverEnabledGeneral() throws ExceptionZZZ;////prueft intern ab, ob das Flag Solver zu nutzen gesetzt ist.
	boolean isSolverEnabledThis() throws ExceptionZZZ; //prueft intern ab, ob das Flag fuer den konkreten Solver gesetzt ist.
	boolean isSolverEnabledEveryRelevant() throws ExceptionZZZ; //prueft intern ab ab solverEnabledGeneral und solverEnabledThis auf true sind.
	boolean isSolveRelevant(String sExpression) throws ExceptionZZZ;//z.B. als Ausdrück für KernelExpressionIni_EmptyZZZ wäre relevant: <z:Empty/>, d.h. true zurück.
	//geht erst ab Java 1.8 oder so... static boolean isParseStatic(String sExpressionToProof) throws ExceptionZZZ;
	
	public String solve(String sExpression) throws ExceptionZZZ;	//ruft parse() auf... und anschliessend solveParsed()....
	public String solve(String sExpression, boolean bRemoveSurroundingSeparators) throws ExceptionZZZ;

	public String solveParsed(String sExpression, boolean bRemoveSuroundingSeparators) throws ExceptionZZZ;
	public String solveParsed(String sExpression) throws ExceptionZZZ;
	public String solveParsed(Vector3ZZZ<String> vecExpression) throws ExceptionZZZ;
	public String solveParsed(Vector3ZZZ<String> vecExpression, boolean bRemoveSurroundingSeparators) throws ExceptionZZZ;
	
	public Vector3ZZZ<String> solvePost(Vector3ZZZ<String> vecExpression) throws ExceptionZZZ;
	public Vector3ZZZ<String> solvePost(Vector3ZZZ<String> vecExpression, boolean bRemoveSurroundingSeparators) throws ExceptionZZZ;
	
	public Vector3ZZZ<String> solvePostCustom(Vector3ZZZ<String> vecExpression) throws ExceptionZZZ;
	public Vector3ZZZ<String> solvePostCustom(Vector3ZZZ<String> vecExpression, boolean bRemoveSurroundingSeparators) throws ExceptionZZZ;
	
	//Merke: Das steht fuer parse in IIniTagBasicZZZ
	public IKernelConfigSectionEntryZZZ solveAsEntryNew(String sExpression) throws ExceptionZZZ;
	public IKernelConfigSectionEntryZZZ solveAsEntry(String sExpression) throws ExceptionZZZ;
	public IKernelConfigSectionEntryZZZ solveAsEntry(String sExpression, boolean bRemoveSurroundingSeparators) throws ExceptionZZZ;
	public IKernelConfigSectionEntryZZZ solveAsEntry(String sExpression, ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReferenceIn) throws ExceptionZZZ;
	public IKernelConfigSectionEntryZZZ solveAsEntry(String sExpression, ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReferenceIn, boolean bRemoveSurroundingSeparators) throws ExceptionZZZ;
	
	//angedacht...
	//public String[] solveAsArray(String sExpression) throws ExceptionZZZ;
	//public String[] solveAsArray(String sExpression, String sDelimiter) throws ExceptionZZZ;
}
