package basic.zKernel.file.ini;

public interface IKernelZFormulaIniSolverZZZ {
	public enum FLAGZ{
		USEFORMULA,USEFORMULA_MATH
	}	
	
	//damit muss man nicht mehr tippen hinter dem enum .name()
	public boolean getFlag(IKernelZFormulaIniSolverZZZ.FLAGZ objEnumFlag);
	public boolean setFlag(IKernelZFormulaIniSolverZZZ.FLAGZ objEnumFlag, boolean bFlagValue);
	public boolean[] setFlag(IKernelZFormulaIniSolverZZZ.FLAGZ[] objEnumFlag, boolean bFlagValue);		
}
