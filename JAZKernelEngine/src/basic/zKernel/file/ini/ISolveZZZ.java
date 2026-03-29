package basic.zKernel.file.ini;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractList.Vector3ZZZ;

public interface ISolveZZZ extends ISolveEnabledZZZ{
	boolean isSolveRelevant(String sExpression) throws ExceptionZZZ;//z.B. als Ausdrück für KernelExpressionIni_EmptyZZZ wäre relevant: <z:Empty/>, d.h. true zurück.
	//geht erst ab Java 1.8 oder so... static boolean isParseStatic(String sExpressionToProof) throws ExceptionZZZ;
	
	public String solve(String sExpression) throws ExceptionZZZ;	//ruft parse() auf... und anschliessend solveParsed()....
	public String solve(String sExpression, boolean bRemoveSurroundingSeparators) throws ExceptionZZZ;

	public String solveParsed(String sExpression) throws ExceptionZZZ;
	public String solveParsed(String sExpression, boolean bRemoveSurroundingSeparatorsOnSolve) throws ExceptionZZZ;	
	public String solveParsed(Vector3ZZZ<String> vecExpression) throws ExceptionZZZ;
	public String solveParsed(Vector3ZZZ<String> vecExpression, boolean bRemoveSurroundingSeparators) throws ExceptionZZZ;
	
	public String solveParsedWrapup(String sExpression) throws ExceptionZZZ; 											  //soll mit dem Ergebnis von solveParsed() weiterarbeiten, darum nur String und nicht Vector als Argument
	public String solveParsedWrapup(String sExpression, boolean bRemoveSurroundingSeparatorsOnSolve) throws ExceptionZZZ; //soll mit dem Ergebnis von solveParsed() weiterarbeiten, darum nur String und nicht Vector als Argument
	public Vector3ZZZ<String> solveParsedWrapup(Vector3ZZZ<String> vecExpression) throws ExceptionZZZ; 											  //soll mit dem Ergebnis von solveParsed() weiterarbeiten, darum nur String und nicht Vector als Argument
	public Vector3ZZZ<String> solveParsedWrapup(Vector3ZZZ<String> vecExpression, boolean bRemoveSurroundingSeparatorsOnSolve) throws ExceptionZZZ; //soll mit dem Ergebnis von solveParsed() weiterarbeiten, darum nur String und nicht Vector als Argument
	

	public Vector3ZZZ<String> solvePost(Vector3ZZZ<String> vecExpression) throws ExceptionZZZ;
	public Vector3ZZZ<String> solvePost(Vector3ZZZ<String> vecExpression, boolean bRemoveSurroundingSeparators) throws ExceptionZZZ;
	
	public Vector3ZZZ<String> solvePostCustom(Vector3ZZZ<String> vecExpression) throws ExceptionZZZ;
	public Vector3ZZZ<String> solvePostCustom(Vector3ZZZ<String> vecExpression, boolean bRemoveSurroundingSeparators) throws ExceptionZZZ;
}
