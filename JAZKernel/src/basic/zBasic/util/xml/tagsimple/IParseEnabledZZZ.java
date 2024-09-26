package basic.zBasic.util.xml.tagsimple;

import java.util.Vector;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractList.Vector3ZZZ;

public interface IParseEnabledZZZ {
	public String parse(String sExpression) throws ExceptionZZZ;
	public String parse(String sExpression, boolean bRemoveSuroundingSeparators) throws ExceptionZZZ;
	public Vector3ZZZ<String>parseFirstVector(String sLineWithExpression) throws ExceptionZZZ;
	public Vector3ZZZ<String>parseFirstVector(String sLineWithExpression, boolean bRemoveSuroundingSeparators) throws ExceptionZZZ;
		
	boolean isParseRelevant(String sExpressionToProof) throws ExceptionZZZ;//z.B. als Ausdrück für KernelExpressionIni_EmptyZZZ wäre relevant: <z:Empty/>, d.h. true zurück.
}
