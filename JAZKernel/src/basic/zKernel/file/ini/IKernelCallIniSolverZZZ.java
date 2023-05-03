package basic.zKernel.file.ini;

import basic.zBasic.ExceptionZZZ;

public interface IKernelCallIniSolverZZZ {
	public enum FLAGZ{
		USEJSON,USEJSON_MAP,USEJSON_ARRAY
	}	
	
	//damit muss man nicht mehr tippen hinter dem enum .name()
	public boolean getFlag(IKernelCallIniSolverZZZ.FLAGZ objEnumFlag);
	public boolean setFlag(IKernelCallIniSolverZZZ.FLAGZ objEnumFlag, boolean bFlagValue) throws ExceptionZZZ;
	public boolean[] setFlag(IKernelCallIniSolverZZZ.FLAGZ[] objEnumFlag, boolean bFlagValue) throws ExceptionZZZ;
	public abstract boolean proofFlagExists(IKernelCallIniSolverZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ;
}
