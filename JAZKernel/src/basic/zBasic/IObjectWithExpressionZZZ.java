package basic.zBasic;

public interface IObjectWithExpressionZZZ{	
	enum FLAGZ{
		USEEXPRESSION
	}
	
	//damit muss man nicht mehr tippen hinter dem enum .name()
	public boolean getFlag(IObjectWithExpressionZZZ.FLAGZ objEnum_IKernelExpressionIniSolverZZZ);
	public boolean setFlag(IObjectWithExpressionZZZ.FLAGZ objEnum_IKernelExpressionIniSolverZZZ, boolean bFlagValue) throws ExceptionZZZ;
	public boolean[] setFlag(IObjectWithExpressionZZZ.FLAGZ[] objEnum_IKernelExpressionIniSolverZZZ, boolean bFlagValue) throws ExceptionZZZ;
	public abstract boolean proofFlagExists(IObjectWithExpressionZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ;
	public abstract boolean proofFlagSetBefore(IObjectWithExpressionZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ;
}
