package basic.zKernel.file.ini;

public interface IKernelZFormulaIniSolverZZZ {
	public enum FLAGZ{
		USEFORMULA,USEFORMULA_MATH
	}	
	
	//damit muss man nicht mehr tippen hinter dem enum .name()
	public boolean getFlag(IKernelZFormulaIniSolverZZZ.FLAGZ objEnum_IKernelZFormulaIniSolverZZZ);
	public boolean setFlag(IKernelZFormulaIniSolverZZZ.FLAGZ objEnum_IKernelZFormulaIniSolverZZZ, boolean bFlagValue);
	public boolean[] setFlag(IKernelZFormulaIniSolverZZZ.FLAGZ[] objEnum_IKernelZFormulaIniSolverZZZ, boolean bFlagValue);		
}
