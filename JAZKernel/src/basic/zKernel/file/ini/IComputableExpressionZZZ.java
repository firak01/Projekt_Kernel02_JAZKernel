package basic.zKernel.file.ini;

import basic.zBasic.ExceptionZZZ;

public interface IComputableExpressionZZZ {
	public String compute() throws ExceptionZZZ;
	public String compute(String sExpression) throws ExceptionZZZ;
	
	public String computeAsExpression() throws ExceptionZZZ;
	public String computeAsExpression(String sExpression) throws ExceptionZZZ;
	
	public boolean isExpression(String sExpression) throws ExceptionZZZ;
}
