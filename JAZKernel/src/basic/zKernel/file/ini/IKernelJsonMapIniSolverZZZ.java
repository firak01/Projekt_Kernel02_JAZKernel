package basic.zKernel.file.ini;

import basic.zBasic.ExceptionZZZ;

public interface IKernelJsonMapIniSolverZZZ extends IKernelJsonIniSolverZZZ{
	public enum FLAGZ{
		USEJSON_MAP
	}	
	
	//damit muss man nicht mehr tippen hinter dem enum .name()
	public boolean getFlag(IKernelJsonMapIniSolverZZZ.FLAGZ objEnumFlag);
	public boolean setFlag(IKernelJsonMapIniSolverZZZ.FLAGZ objEnumFlag, boolean bFlagValue) throws ExceptionZZZ;
	public boolean[] setFlag(IKernelJsonMapIniSolverZZZ.FLAGZ[] objEnumFlag, boolean bFlagValue) throws ExceptionZZZ;
	public abstract boolean proofFlagExists(IKernelJsonMapIniSolverZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ;
	public abstract boolean proofFlagSetBefore(IKernelJsonMapIniSolverZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ;
}
