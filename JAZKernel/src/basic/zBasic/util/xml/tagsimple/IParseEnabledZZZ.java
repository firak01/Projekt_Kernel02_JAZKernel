package basic.zBasic.util.xml.tagsimple;

import java.util.Vector;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractList.Vector3ZZZ;

public interface IParseEnabledZZZ {
	boolean isParserEnabledThis() throws ExceptionZZZ;
	boolean isParseRelevant() throws ExceptionZZZ;//ob ein Tag ueberhaupt fuer das parsen relevant ist
	boolean isParseRelevant(String sExpression) throws ExceptionZZZ;//z.B. als Ausdrück für KernelExpressionIni_EmptyZZZ wäre relevant: <z:Empty/>, d.h. true zurück.
	//geht erst ab Java 1.8 oder so... static boolean isParseStatic(String sExpressionToProof) throws ExceptionZZZ;
	
	public String parse(String sExpression) throws ExceptionZZZ;
	public String parse(String sExpression, boolean bRemoveSurroundingSeparators) throws ExceptionZZZ;
	public Vector3ZZZ<String>parseFirstVector(String sExpression) throws ExceptionZZZ;
	public Vector3ZZZ<String>parseFirstVector(String sExpression, boolean bRemoveSurroundingSeparators) throws ExceptionZZZ;
	
	public Vector3ZZZ<String> parseFirstVectorPost(Vector3ZZZ<String> vecExpression) throws ExceptionZZZ;
	public Vector3ZZZ<String> parseFirstVectorPost(Vector3ZZZ<String> vecExpression, boolean bRemoveSurroundingSeparators) throws ExceptionZZZ;
	public Vector3ZZZ<String> parseFirstVectorPostCustom(Vector3ZZZ<String> vecExpression) throws ExceptionZZZ;
	public Vector3ZZZ<String> parseFirstVectorPostCustom(Vector3ZZZ<String> vecExpression, boolean bRemoveSurroundingSeparators) throws ExceptionZZZ;
	
}
