package basic.zBasic.util.xml.tagsimple;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractList.Vector3ZZZ;

public interface IParseZZZ extends IParseEnabledZZZ{
	boolean isParseRelevant() throws ExceptionZZZ;//ob ein Tag ueberhaupt fuer das parsen relevant ist
	boolean isParseRelevant(String sExpression) throws ExceptionZZZ;//z.B. als Ausdrück für KernelExpressionIni_EmptyZZZ wäre relevant: <z:Empty/>, d.h. true zurück.
	//geht erst ab Java 1.8 oder so... static boolean isParseStatic(String sExpressionToProof) throws ExceptionZZZ;
	
	public String parse(String sExpression) throws ExceptionZZZ;
	public String parse(String sExpression, boolean bKeepSurroundingSeparatorsOnParse) throws ExceptionZZZ;
	public String parse(String sExpression, boolean bKeepSurroundingSeparatorsOnParse, boolean bIgnoreCase) throws ExceptionZZZ;
	
	public Vector3ZZZ<String> parsePost(Vector3ZZZ<String> vecExpression) throws ExceptionZZZ;
	public Vector3ZZZ<String> parsePost(Vector3ZZZ<String> vecExpression, boolean bKeepSurroundingSeparatorsOnParse) throws ExceptionZZZ;
	public Vector3ZZZ<String> parsePost(Vector3ZZZ<String> vecExpression, boolean bKeepSurroundingSeparatorsOnParse, boolean bRemoveOwnTagParts) throws ExceptionZZZ;
	public Vector3ZZZ<String> parsePostCustom(Vector3ZZZ<String> vecExpression) throws ExceptionZZZ;
	public Vector3ZZZ<String> parsePostCustom(Vector3ZZZ<String> vecExpression, boolean bKeepSurroundingSeparatorsOnParse) throws ExceptionZZZ;	

	public Vector3ZZZ<String>parseFirstVector(String sExpression) throws ExceptionZZZ;
	public Vector3ZZZ<String>parseFirstVector(String sExpression, boolean bKeepSurroundingSeparatorsOnParse) throws ExceptionZZZ;
	public Vector3ZZZ<String>parseFirstVector(String sExpression, boolean bKeepSurroundingSeparatorsOnParse, boolean bIgnoreCase) throws ExceptionZZZ;
	
	public Vector3ZZZ<String> parseFirstVectorPost(Vector3ZZZ<String> vecExpression) throws ExceptionZZZ;
	public Vector3ZZZ<String> parseFirstVectorPost(Vector3ZZZ<String> vecExpression, boolean bKeepSurroundingSeparatorsOnParse) throws ExceptionZZZ;
	public Vector3ZZZ<String> parseFirstVectorPost(Vector3ZZZ<String> vecExpression, boolean bKeepSurroundingSeparatorsOnParse, boolean bRemoveOwnTagParts) throws ExceptionZZZ;
	public Vector3ZZZ<String> parseFirstVectorPostCustom(Vector3ZZZ<String> vecExpression) throws ExceptionZZZ;
	public Vector3ZZZ<String> parseFirstVectorPostCustom(Vector3ZZZ<String> vecExpression, boolean bKeepSurroundingSeparatorsOnParse) throws ExceptionZZZ;
}
