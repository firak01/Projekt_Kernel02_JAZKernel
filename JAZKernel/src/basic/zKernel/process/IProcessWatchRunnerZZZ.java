package basic.zKernel.process;

import basic.zBasic.ExceptionZZZ;
import basic.zKernel.status.IEventBrokerStatusLocalSetUserZZZ;
import basic.zKernel.status.IStatusLocalUserZZZ;


public interface IProcessWatchRunnerZZZ extends IStatusLocalUserZZZ{
	public enum FLAGZ{
		DUMMY, STOPREQUEST
	}

	boolean getFlag(FLAGZ objEnumFlag);
	boolean setFlag(FLAGZ objEnumFlag, boolean bFlagValue) throws ExceptionZZZ;
	boolean[] setFlag(FLAGZ[] objaEnumFlag, boolean bFlagValue) throws ExceptionZZZ;
	boolean proofFlagExists(FLAGZ objEnumFlag) throws ExceptionZZZ;
	boolean proofFlagSetBefore(FLAGZ objEnumFlag) throws ExceptionZZZ;
	
	public Process getProcessObject();
	public void setProcessObject(Process objProcess);
	
	public int getNumber();
	public void setNumber(int iNumber);
	
	public abstract boolean analyseInputLineCustom(String sLine) throws ExceptionZZZ;
	
	
}
