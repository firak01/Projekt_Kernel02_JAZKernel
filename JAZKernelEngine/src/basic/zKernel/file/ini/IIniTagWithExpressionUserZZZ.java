package basic.zKernel.file.ini;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractList.VectorDifferenceZZZ;

public interface IIniTagWithExpressionUserZZZ {
	public String getRawAsExpression() throws ExceptionZZZ;
	
	public VectorDifferenceZZZ<String> getValueAsExpressionVector();
	public String getValueAsExpression();
	public void setValueAsExpression(String sValueAsExpression) throws ExceptionZZZ;                   //20251007: Wird vor dem Entfernen der Z-Tags im PostSolve / ...Wrapup... verwendet
	public void setValueAsExpression(String sValueAsExpression, boolean bEnforce) throws ExceptionZZZ; //20251007: Wird vor dem Entfernen der Z-Tags im PostSolve / ...Wrapup... verwendet
}
