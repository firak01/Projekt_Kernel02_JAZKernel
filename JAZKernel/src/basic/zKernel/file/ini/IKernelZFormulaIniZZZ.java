package basic.zKernel.file.ini;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IConvertableZZZ;
import basic.zBasic.util.xml.tagsimple.IParseEnabledZZZ;

public interface IKernelZFormulaIniZZZ extends IConvertableZZZ, IParseEnabledZZZ{
	//TODOGOON 20240814; //Wenn sonstige compilerfehler bereinigt, umbennen in IKernelZFormulaIniSolverZZZ 
	
	//Merke: Erst ab Java 8 können static Ausdrücke in ein interface: 
	//getExpressionTagName()
	//getExpressionTagStarting()
	//getExpressionTagClosing()
	//getExpressionTagEmpty()
	
	public enum FLAGZ{
		USEFORMULA,USEFORMULA_MATH
	}	
	
	//damit muss man nicht mehr tippen hinter dem enum .name()
	public boolean getFlag(IKernelZFormulaIniZZZ.FLAGZ objEnum_IKernelZFormulaIniZZZ) throws ExceptionZZZ;
	public boolean setFlag(IKernelZFormulaIniZZZ.FLAGZ objEnum_IKernelZFormulaIniZZZ, boolean bFlagValue) throws ExceptionZZZ;
	public boolean[] setFlag(IKernelZFormulaIniZZZ.FLAGZ[] objEnum_IKernelZFormulaIniZZZ, boolean bFlagValue) throws ExceptionZZZ;
	public abstract boolean proofFlagExists(IKernelZFormulaIniZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ;  
	public abstract boolean proofFlagSetBefore(IKernelZFormulaIniZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ;
}
