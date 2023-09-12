package basic.zKernel.process;

import basic.zBasic.ExceptionZZZ;

public interface IProcessWatchRunnerZZZ {
	public Process getProcessObject();
	public void setProcessObject(Process objProcess);
	
	public int getNumber();
	public void setNumber(int iNumber);
	
	public abstract boolean analyseInputLineCustom(String sLine) throws ExceptionZZZ;
}
