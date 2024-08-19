package basic.zKernel.file.ini;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IConvertableZZZ;
import basic.zBasic.component.IModuleZZZ;
import basic.zBasic.util.xml.tagsimple.IParseEnabledZZZ;

public interface IKernelZVariableIniSolverZZZ {
	//Merke: Erst ab Java 8 können static Ausdrücke in ein interface: 
	//getExpressionTagName()
	//getExpressionTagStarting()
	//getExpressionTagClosing()
	//getExpressionTagEmpty()
 
	public enum FLAGZ{
		USEEXPRESSION_VARIABLE
	}	
	
	//damit muss man nicht mehr tippen hinter dem enum .name()
	public boolean getFlag(IKernelZVariableIniSolverZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ;
	public boolean setFlag(IKernelZVariableIniSolverZZZ.FLAGZ objEnumFlag, boolean bFlagValue) throws ExceptionZZZ;
	public boolean[] setFlag(IKernelZVariableIniSolverZZZ.FLAGZ[] objEnumFlag, boolean bFlagValue) throws ExceptionZZZ;
	public abstract boolean proofFlagExists(IKernelZVariableIniSolverZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ;
	public abstract boolean proofFlagSetBefore(IKernelZVariableIniSolverZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ;
}
