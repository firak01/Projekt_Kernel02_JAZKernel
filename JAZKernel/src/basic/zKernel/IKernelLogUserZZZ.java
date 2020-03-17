package basic.zKernel;

import custom.zKernel.LogZZZ;

public interface IKernelLogUserZZZ {
	public abstract LogZZZ getLogObject();
	public abstract void setLogObject(LogZZZ objLog);
	
	public abstract void logLineDate(String sLog);
}
