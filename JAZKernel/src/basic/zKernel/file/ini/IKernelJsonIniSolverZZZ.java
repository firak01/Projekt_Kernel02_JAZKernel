package basic.zKernel.file.ini;

import basic.zBasic.ExceptionZZZ;

public interface IKernelJsonIniSolverZZZ {
	public enum FLAGZ{
		USEJSON,USEJSON_MAP,USEJSON_ARRAY
	}	
	
	//damit muss man nicht mehr tippen hinter dem enum .name()
	public boolean getFlag(IKernelJsonIniSolverZZZ.FLAGZ objEnumFlag);
	public boolean setFlag(IKernelJsonIniSolverZZZ.FLAGZ objEnumFlag, boolean bFlagValue) throws ExceptionZZZ;
	public boolean[] setFlag(IKernelJsonIniSolverZZZ.FLAGZ[] objEnumFlag, boolean bFlagValue) throws ExceptionZZZ;
	public abstract boolean proofFlagExists(IKernelJsonIniSolverZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ;
}
