package basic.zKernel.file.ini;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IConvertableZZZ;
import basic.zBasic.util.xml.tagsimple.IParseEnabledZZZ;

public interface IExpressionUserZZZ extends IParseEnabledZZZ, IConvertableZZZ{		
	public boolean isExpression(String sExpression) throws ExceptionZZZ;
	
	public String computeAsExpression() throws ExceptionZZZ;
	public String computeAsExpression(String sExpression) throws ExceptionZZZ;
	
}
