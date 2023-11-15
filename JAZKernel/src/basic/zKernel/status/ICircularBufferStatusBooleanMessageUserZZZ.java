package basic.zKernel.status;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractList.CircularBufferZZZ;


public interface ICircularBufferStatusBooleanMessageUserZZZ{
	//Merke: Wg. des IStatusBooleanMessageZZZ Parameters erbt diese Klasse nicht von ICircularBufferStatusBooleanZZZ
	public abstract CircularBufferZZZ<IStatusBooleanMessageZZZ> getCircularBufferStatusLocal();
	public abstract void setCircularBufferStatusLocal(CircularBufferZZZ<IStatusBooleanMessageZZZ> cb);
	
	public abstract CircularBufferZZZ<String> getCircularBufferStatusLocalMessage();
	public abstract void setCircularBufferStatusLocalMessage(CircularBufferZZZ<String> cb);	
	
	public abstract void debugCircularBufferStatusLocalMessage(int iStepsMax) throws ExceptionZZZ;
}
