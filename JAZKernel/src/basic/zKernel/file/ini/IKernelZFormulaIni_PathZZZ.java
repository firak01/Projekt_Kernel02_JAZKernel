package basic.zKernel.file.ini;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IConvertEnabledZZZ;
import basic.zBasic.component.IModuleZZZ;
import basic.zBasic.util.xml.tagsimple.IParseEnabledZZZ;

public interface IKernelZFormulaIni_PathZZZ {
	//Merke: Erst ab Java 8 können static Ausdrücke in ein interface: 
	//getExpressionTagName()
	//getExpressionTagStarting()
	//getExpressionTagClosing()
	//getExpressionTagEmpty()
 
	public enum FLAGZ{
		USEEXPRESSION_PATH
	}	
	
	//damit muss man nicht mehr tippen hinter dem enum .name()
	public boolean getFlag(IKernelZFormulaIni_PathZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ;
	public boolean setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ objEnumFlag, boolean bFlagValue) throws ExceptionZZZ;
	public boolean[] setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ[] objEnumFlag, boolean bFlagValue) throws ExceptionZZZ;
	public abstract boolean proofFlagExists(IKernelZFormulaIni_PathZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ;
	public abstract boolean proofFlagSetBefore(IKernelZFormulaIni_PathZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ;
}
