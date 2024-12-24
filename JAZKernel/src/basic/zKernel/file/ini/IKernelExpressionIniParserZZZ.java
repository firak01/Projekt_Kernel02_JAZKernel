package basic.zKernel.file.ini;

import basic.zBasic.ExceptionZZZ;

//Merke: Das Interface wurde notwendig um zwischen solver() und parse() unterscheiden zu koennen.
//       Ein Solver kann nun abgeschaltet sein, trotzdem wird der Tag geparsed (und ggfs. Pfade substituiert), nur halt nicht aufgeloest.
public interface IKernelExpressionIniParserZZZ { //extends IKernelZFormulaIniSolverZZZ, IKernelZFormulaIni_PathZZZ,  IKernelJsonMapIniSolverZZZ, IKernelJsonArrayIniSolverZZZ, IKernelEncryptionIniSolverZZZ, IKernelJavaCallIniSolverZZZ{
	public enum FLAGZ{
		USEEXPRESSION_PARSER
	}
	
	//damit muss man nicht mehr tippen hinter dem enum .name()
	public boolean getFlag(IKernelExpressionIniParserZZZ.FLAGZ objEnum_IKernelExpressionIniParserZZZ);
	public boolean setFlag(IKernelExpressionIniParserZZZ.FLAGZ objEnum_IKernelExpressionIniParserZZZ, boolean bFlagValue) throws ExceptionZZZ;
	public boolean[] setFlag(IKernelExpressionIniParserZZZ.FLAGZ[] objEnum_IKernelExpressionIniParserZZZ, boolean bFlagValue) throws ExceptionZZZ;
	public abstract boolean proofFlagExists(IKernelExpressionIniParserZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ;
	public abstract boolean proofFlagSetBefore(IKernelExpressionIniParserZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ;
}
