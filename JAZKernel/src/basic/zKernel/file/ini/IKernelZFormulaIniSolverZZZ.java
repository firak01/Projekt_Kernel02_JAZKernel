package basic.zKernel.file.ini;

import basic.zBasic.ExceptionZZZ;
import basic.zKernel.flag.IFlagZUserZZZ.FLAGZ;

public interface IKernelZFormulaIniSolverZZZ {
	public enum FLAGZ{
		USEFORMULA,USEFORMULA_MATH
	}	
	
	//damit muss man nicht mehr tippen hinter dem enum .name()
	public boolean getFlag(IKernelZFormulaIniSolverZZZ.FLAGZ objEnum_IKernelZFormulaIniSolverZZZ);
	public boolean setFlag(IKernelZFormulaIniSolverZZZ.FLAGZ objEnum_IKernelZFormulaIniSolverZZZ, boolean bFlagValue) throws ExceptionZZZ;
	public boolean[] setFlag(IKernelZFormulaIniSolverZZZ.FLAGZ[] objEnum_IKernelZFormulaIniSolverZZZ, boolean bFlagValue) throws ExceptionZZZ;
	public abstract boolean proofFlagExists(IKernelZFormulaIniSolverZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ;
}
