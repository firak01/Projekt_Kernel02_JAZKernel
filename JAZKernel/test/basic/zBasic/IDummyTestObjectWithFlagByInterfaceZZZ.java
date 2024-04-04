package basic.zBasic;

public interface IDummyTestObjectWithFlagByInterfaceZZZ{
		

	//#############################################################
	//### FLAGZ
	//#############################################################
	public enum FLAGZ{
		DUMMY01INTERFACE,DUMMY02INTERFACE
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
