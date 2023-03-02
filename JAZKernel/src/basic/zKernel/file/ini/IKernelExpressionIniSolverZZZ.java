package basic.zKernel.file.ini;

public interface IKernelExpressionIniSolverZZZ extends IKernelZFormulaIniSolverZZZ, IKernelJsonIniSolverZZZ, IKernelEncryptionIniSolverZZZ{
	public enum FLAGZ{
		USEEXPRESSION
	}
	//damit muss man nicht mehr tippen hinter dem enum .name()
	public boolean getFlag(IKernelExpressionIniSolverZZZ.FLAGZ objEnumFlag);
	public boolean setFlag(IKernelExpressionIniSolverZZZ.FLAGZ objEnumFlag, boolean bFlagValue);
	public boolean[] setFlag(IKernelExpressionIniSolverZZZ.FLAGZ[] objEnumFlag, boolean bFlagValue);
}
