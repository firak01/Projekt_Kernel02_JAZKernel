package basic.zKernel.file.ini;

import java.util.Vector;

import basic.zBasic.ExceptionZZZ;

public interface IIniTagWithExpressionZZZ extends IExpressionUserZZZ, IIniTagBasicZZZ {
	public Vector<String> computeAsExpressionFirstVector(String sLineWithExpression) throws ExceptionZZZ;
	public Vector<String> computeAsExpressionAllVector(String sLineWithExpression) throws ExceptionZZZ;
}
