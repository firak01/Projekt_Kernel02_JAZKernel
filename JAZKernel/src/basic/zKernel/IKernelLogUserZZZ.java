package basic.zKernel;

import basic.zBasic.ExceptionZZZ;
import custom.zKernel.LogZZZ;

public interface IKernelLogUserZZZ {
	public abstract LogZZZ getLogObject() throws ExceptionZZZ;
	public abstract void setLogObject(LogZZZ objLog) throws ExceptionZZZ;
	
	//Analog zu ILogZZZ
	public abstract void logLineDate(String sLog) throws ExceptionZZZ;
	public abstract void logLineDateWithPosition(String sLog) throws ExceptionZZZ;
}
