package basic.zBasic.util.xml.tagsimple;

import java.util.Vector;

import basic.zBasic.ExceptionZZZ;

public interface IParseEnabledZZZ {
	public String parse(String sExpression) throws ExceptionZZZ;
	public Vector<String>parseFirstVector(String sLineWithExpression) throws ExceptionZZZ;
	
	boolean isParseRelevant(String sExpressionToProof) throws ExceptionZZZ;//z.B. als Ausdrück für KernelExpressionIni_EmptyZZZ wäre relevant: <z:Empty/>, d.h. true zurück.
}
