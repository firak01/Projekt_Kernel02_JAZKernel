package basic.zKernel.file.ini;

import basic.zBasic.ExceptionZZZ;

public interface IKernelJavaCallIniSolverZZZ extends IKernelCallIniSolverZZZ{
	public enum FLAGZ{
		USECALL_JAVA
	}	
	
	//damit muss man nicht mehr tippen hinter dem enum .name()
	public boolean getFlag(IKernelJavaCallIniSolverZZZ.FLAGZ objEnumFlag);
	public boolean setFlag(IKernelJavaCallIniSolverZZZ.FLAGZ objEnumFlag, boolean bFlagValue) throws ExceptionZZZ;
	public boolean[] setFlag(IKernelJavaCallIniSolverZZZ.FLAGZ[] objEnumFlag, boolean bFlagValue) throws ExceptionZZZ;
	public abstract boolean proofFlagExists(IKernelJavaCallIniSolverZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ;
	public abstract boolean proofFlagSetBefore(IKernelJavaCallIniSolverZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ;
}
