package basic.zKernel.file.ini;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractList.VectorDifferenceZZZ;

public interface IIniTagWithExpressionUserZZZ {
	public String getRawAsExpression() throws ExceptionZZZ;
	
	//TODOGOON20251007; //Verschiebe nach IIniTagWithExpressionUserZZZ	
	public VectorDifferenceZZZ<String> getValueAsExpressionVector();
	public String getValueAsExpression();
	public void setValueAsExpression(String sValueAsExpression) throws ExceptionZZZ;
	public void setValueAsExpression(String sValueAsExpression, boolean bEnforce) throws ExceptionZZZ;
}
