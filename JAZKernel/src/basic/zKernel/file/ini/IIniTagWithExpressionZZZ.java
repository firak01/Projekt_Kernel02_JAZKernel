package basic.zKernel.file.ini;

import basic.zBasic.ExceptionZZZ;

public interface IIniTagWithExpressionZZZ{	
	public enum FLAGZ{
		USEEXPRESSION
	}
		
	//damit muss man nicht mehr tippen hinter dem enum .name()
	public boolean getFlag(IIniTagWithExpressionZZZ.FLAGZ objEnum_IKernelExpressionIniSolverZZZ);
	public boolean setFlag(IIniTagWithExpressionZZZ.FLAGZ objEnum_IKernelExpressionIniSolverZZZ, boolean bFlagValue) throws ExceptionZZZ;
	public boolean[] setFlag(IIniTagWithExpressionZZZ.FLAGZ[] objEnum_IKernelExpressionIniSolverZZZ, boolean bFlagValue) throws ExceptionZZZ;
	public abstract boolean proofFlagExists(IIniTagWithExpressionZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ;
	public abstract boolean proofFlagSetBefore(IIniTagWithExpressionZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ;
}
