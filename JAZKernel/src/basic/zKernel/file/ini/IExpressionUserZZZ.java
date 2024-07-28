package basic.zKernel.file.ini;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.xml.tagsimple.IParseEnabledZZZ;

public interface IExpressionUserZZZ extends IParseEnabledZZZ{		
	public boolean isExpression(String sExpression) throws ExceptionZZZ;
	
}
