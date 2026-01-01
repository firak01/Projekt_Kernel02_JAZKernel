package basic.zBasic.util.log;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractList.ArrayListZZZ;
import basic.zBasic.util.datatype.string.IStringJustifierZZZ;

public interface ILogStringFormatManagerZZZ extends ILogStringFormatManagerComputerZZZ{
	public ArrayListZZZ<IStringJustifierZZZ> getStringJustifierListDefault() throws ExceptionZZZ;
	public ArrayListZZZ<IStringJustifierZZZ> getStringJustifierList() throws ExceptionZZZ;
	public void setStringJustifierList(ArrayListZZZ<IStringJustifierZZZ> listaJustifier) throws ExceptionZZZ;
	
	public boolean hasStringJustifier(int iIndex) throws ExceptionZZZ;
	public IStringJustifierZZZ getStringJustifierDefault() throws ExceptionZZZ;
	
	public IStringJustifierZZZ getStringJustifier(int iIndex) throws ExceptionZZZ;
	public void addStringJustifier(IStringJustifierZZZ objStringJustifier) throws ExceptionZZZ;
	
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
