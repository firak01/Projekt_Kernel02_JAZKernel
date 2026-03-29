package basic.zKernel.file.ini;

import basic.zBasic.ExceptionZZZ;

public interface IKernelJsonArrayIniSolverZZZ extends IKernelJsonIniSolverZZZ{
	public enum FLAGZ{
		USEJSON_ARRAY
	}	
	
	//damit muss man nicht mehr tippen hinter dem enum .name()
	public boolean getFlag(IKernelJsonArrayIniSolverZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ;
	public boolean setFlag(IKernelJsonArrayIniSolverZZZ.FLAGZ objEnumFlag, boolean bFlagValue) throws ExceptionZZZ;
	public boolean[] setFlag(IKernelJsonArrayIniSolverZZZ.FLAGZ[] objEnumFlag, boolean bFlagValue) throws ExceptionZZZ;
	public abstract boolean proofFlagExists(IKernelJsonArrayIniSolverZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ;
	public abstract boolean proofFlagSetBefore(IKernelJsonArrayIniSolverZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ;
}
