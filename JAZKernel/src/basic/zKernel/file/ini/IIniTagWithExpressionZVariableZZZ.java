package basic.zKernel.file.ini;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IConvertableZZZ;
import basic.zBasic.component.IModuleZZZ;
import basic.zBasic.util.xml.tagsimple.IParseEnabledZZZ;

public interface IIniTagWithExpressionZVariableZZZ {
	//Merke: Erst ab Java 8 können static Ausdrücke in ein interface: 
	//getExpressionTagName()
	//getExpressionTagStarting()
	//getExpressionTagClosing()
	//getExpressionTagEmpty()
 
	public enum FLAGZ{
		USEEXPRESSION_VARIABLE
	}	
	
	//damit muss man nicht mehr tippen hinter dem enum .name()
	public boolean getFlag(IIniTagWithExpressionZVariableZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ;
	public boolean setFlag(IIniTagWithExpressionZVariableZZZ.FLAGZ objEnumFlag, boolean bFlagValue) throws ExceptionZZZ;
	public boolean[] setFlag(IIniTagWithExpressionZVariableZZZ.FLAGZ[] objEnumFlag, boolean bFlagValue) throws ExceptionZZZ;
	public abstract boolean proofFlagExists(IIniTagWithExpressionZVariableZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ;
	public abstract boolean proofFlagSetBefore(IIniTagWithExpressionZVariableZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ;
}
