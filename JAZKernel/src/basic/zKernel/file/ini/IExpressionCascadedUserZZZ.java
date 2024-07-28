package basic.zKernel.file.ini;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.xml.tagsimple.IParseEnabledZZZ;

public interface IExpressionCascadedUserZZZ extends IExpressionUserZZZ{
		
	public String computeAsExpression() throws ExceptionZZZ;
	public String computeAsExpression(String sExpression) throws ExceptionZZZ;
}
