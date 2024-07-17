package basic.zKernel.file.ini;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IComputableZZZ;

public interface IComputableExpressionZZZ extends IComputableZZZ{
	public String computeAsExpression() throws ExceptionZZZ;
	public String computeAsExpression(String sExpression) throws ExceptionZZZ;
	
	public boolean isExpression(String sExpression) throws ExceptionZZZ;
}
