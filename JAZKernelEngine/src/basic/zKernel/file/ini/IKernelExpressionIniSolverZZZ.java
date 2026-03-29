package basic.zKernel.file.ini;

import basic.zBasic.ExceptionZZZ;

public interface IKernelExpressionIniSolverZZZ { //extends IKernelZFormulaIniSolverZZZ, IKernelZFormulaIni_PathZZZ,  IKernelJsonMapIniSolverZZZ, IKernelJsonArrayIniSolverZZZ, IKernelEncryptionIniSolverZZZ, IKernelJavaCallIniSolverZZZ{
	public enum FLAGZ{
		USEEXPRESSION_SOLVER
	}
	
	//Das Entry-Objekt wird in den Methoden fortlaufend gefüllt. Damit bekommt man auch Zwischenstände.
	//Z.B. beim entschluesseln eines Werts, kann man so auch den reinen verschluesselten Wert zurueckgreifen.
	//public IKernelConfigSectionEntryZZZ getEntry();  
	//public void setEntry(IKernelConfigSectionEntryZZZ objEntry);
	
	//damit muss man nicht mehr tippen hinter dem enum .name()
	public boolean getFlag(IKernelExpressionIniSolverZZZ.FLAGZ objEnum_IKernelExpressionIniSolverZZZ) throws ExceptionZZZ;
	public boolean setFlag(IKernelExpressionIniSolverZZZ.FLAGZ objEnum_IKernelExpressionIniSolverZZZ, boolean bFlagValue) throws ExceptionZZZ;
	public boolean[] setFlag(IKernelExpressionIniSolverZZZ.FLAGZ[] objEnum_IKernelExpressionIniSolverZZZ, boolean bFlagValue) throws ExceptionZZZ;
	public abstract boolean proofFlagExists(IKernelExpressionIniSolverZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ;
	public abstract boolean proofFlagSetBefore(IKernelExpressionIniSolverZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ;
}
