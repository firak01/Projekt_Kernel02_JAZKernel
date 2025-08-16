package basic.zKernel.flag.event;

import java.util.EventListener;

import basic.zBasic.ExceptionZZZ;
import basic.zKernel.flag.IFlagZEnabledZZZ;
import basic.zKernel.status.IListenerObjectStatusLocalZZZ.FLAGZ;

public interface IListenerObjectFlagZsetZZZ extends EventListener, IFlagZEnabledZZZ{
	public boolean flagChanged(IEventObjectFlagZsetZZZ eventFlagZset) throws ExceptionZZZ;

	//#############################################################
	//### FLAGZ
	//#############################################################
	//Merke: REGISTER_ON_SELF_FOR_EVENT bewirkt, dass der geworfenen Event vom Objekt selbst empfangen wird. 
	//                                           Dadurch ist die Reaktion ggfs. schneller und VOR allen anderen registrierten Objekten. 
	public enum FLAGZ{
		DUMMY,REGISTER_SELF_FOR_EVENT
	}
		
	boolean getFlag(FLAGZ objEnumFlag);
	boolean setFlag(FLAGZ objEnumFlag, boolean bFlagValue) throws ExceptionZZZ;
	boolean[] setFlag(FLAGZ[] objaEnumFlag, boolean bFlagValue) throws ExceptionZZZ;
	boolean proofFlagExists(FLAGZ objEnumFlag) throws ExceptionZZZ;
	boolean proofFlagSetBefore(FLAGZ objEnumFlag) throws ExceptionZZZ;
}
