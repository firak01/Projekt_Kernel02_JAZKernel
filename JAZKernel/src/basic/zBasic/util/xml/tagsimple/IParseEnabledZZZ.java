package basic.zBasic.util.xml.tagsimple;

import java.util.Vector;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractList.Vector3ZZZ;

public interface IParseEnabledZZZ {
	boolean isParseRelevant();
	boolean isParse(String sExpressionToProof) throws ExceptionZZZ;//z.B. als Ausdrück für KernelExpressionIni_EmptyZZZ wäre relevant: <z:Empty/>, d.h. true zurück.
	//geht erst ab Java 1.8 oder so... static boolean isParseStatic(String sExpressionToProof) throws ExceptionZZZ;
	
	public String parse(String sExpression) throws ExceptionZZZ;
	public String parse(String sExpression, boolean bRemoveSuroundingSeparators) throws ExceptionZZZ;
	public Vector3ZZZ<String>parseFirstVector(String sLineWithExpression) throws ExceptionZZZ;
	public Vector3ZZZ<String>parseFirstVector(String sLineWithExpression, boolean bRemoveSuroundingSeparators) throws ExceptionZZZ;
	
	public Vector3ZZZ<String> parseFirstVectorPostCustom(Vector3ZZZ<String> vecExpression) throws ExceptionZZZ;
	public Vector3ZZZ<String> parseFirstVectorPostCustom(Vector3ZZZ<String> sExpression, boolean bRemoveSuroundingSeparators) throws ExceptionZZZ;
	
}
