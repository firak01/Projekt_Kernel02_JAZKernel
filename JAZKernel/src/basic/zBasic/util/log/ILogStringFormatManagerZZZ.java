package basic.zBasic.util.log;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.datatype.string.IStringJustifierZZZ;

public interface ILogStringFormatManagerZZZ extends ILogStringFormatManagerComputerZZZ, ILogStringFormatComputerZZZ{
	public boolean hasStringJustifierPrivate() throws ExceptionZZZ;
	public IStringJustifierZZZ getStringJustifier() throws ExceptionZZZ;
	public void setStringJustifier(IStringJustifierZZZ objStringJustifier) throws ExceptionZZZ;
	
	public boolean reset() throws ExceptionZZZ;
	
	//#############################################################
	//### FLAGZ
	//#############################################################
	public enum FLAGZ{
		DUMMY
	}
		
	boolean getFlag(FLAGZ objEnumFlag);
	boolean setFlag(FLAGZ objEnumFlag, boolean bFlagValue) throws ExceptionZZZ;
	boolean[] setFlag(FLAGZ[] objaEnumFlag, boolean bFlagValue) throws ExceptionZZZ;
	boolean proofFlagExists(FLAGZ objEnumFlag) throws ExceptionZZZ;
	boolean proofFlagSetBefore(FLAGZ objEnumFlag) throws ExceptionZZZ;
	
	
	
	//#######################################################################################
	// STATUS	
    //............ hier erst einmal nicht .....................
}
