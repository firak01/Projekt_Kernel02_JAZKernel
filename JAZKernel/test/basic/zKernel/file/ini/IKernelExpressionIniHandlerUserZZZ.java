package basic.zKernel.file.ini;

import basic.zBasic.ExceptionZZZ;

public interface IKernelExpressionIniHandlerUserZZZ {
	public IKernelExpressionIniHandlerZZZ getExpressionHandlerNew() throws ExceptionZZZ;
	public IKernelExpressionIniHandlerZZZ getExpressionHandler() throws ExceptionZZZ;
	public void setExpressionHandler(IKernelExpressionIniHandlerZZZ objExpressionHandler) throws ExceptionZZZ;
}
