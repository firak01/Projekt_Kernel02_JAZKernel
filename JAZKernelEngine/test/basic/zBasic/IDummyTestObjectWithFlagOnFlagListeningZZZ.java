package basic.zBasic;

public interface IDummyTestObjectWithFlagOnFlagListeningZZZ extends IDummyTestObjectWithFlagZZZ{		
	public String getValueDummyByFlagEvent();
	public void setValueDummyByFlagEvent(String sValue);
	
	//#############################################################
	//### FLAGZ
	//#############################################################
	public enum FLAGZ{
		DUMMY
	}
		
	boolean getFlag(FLAGZ objEnumFlag) throws ExceptionZZZ;
	boolean setFlag(FLAGZ objEnumFlag, boolean bFlagValue) throws ExceptionZZZ;
	boolean[] setFlag(FLAGZ[] objaEnumFlag, boolean bFlagValue) throws ExceptionZZZ;
	boolean proofFlagExists(FLAGZ objEnumFlag) throws ExceptionZZZ;
	boolean proofFlagSetBefore(FLAGZ objEnumFlag) throws ExceptionZZZ;
	
	//#######################################################################################
	// STATUS	
    //............ hier erst einmal nicht .....................
}
