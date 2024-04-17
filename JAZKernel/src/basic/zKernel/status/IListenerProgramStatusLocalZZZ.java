package basic.zKernel.status;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.component.IModuleUserZZZ;
import basic.zBasic.component.IProgramZZZ;

public interface IListenerProgramStatusLocalZZZ extends IProgramZZZ{ //IListenerObjectStatusLocalZZZ, IProgramZZZ, IModuleUserZZZ{
	
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
}
