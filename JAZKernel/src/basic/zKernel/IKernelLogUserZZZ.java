package basic.zKernel;

import basic.zBasic.ExceptionZZZ;
import custom.zKernel.LogZZZ;

public interface IKernelLogUserZZZ {
	public abstract LogZZZ getLogObject() throws ExceptionZZZ;
	public abstract void setLogObject(LogZZZ objLog) throws ExceptionZZZ;
	
	public abstract void logLineDate(String sLog) throws ExceptionZZZ;
}
