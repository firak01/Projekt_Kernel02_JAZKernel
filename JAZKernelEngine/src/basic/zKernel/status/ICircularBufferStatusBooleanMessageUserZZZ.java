package basic.zKernel.status;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractList.CircularBufferForStatusBooleanMessageZZZ;


public interface ICircularBufferStatusBooleanMessageUserZZZ extends ICircularBufferStatusBooleanUserBasicZZZ{	
	
	//Merke: Wg. des IStatusBooleanMessageZZZ Parameters erbt diese Klasse nicht von ICircularBufferStatusBooleanZZZ	
	public abstract CircularBufferForStatusBooleanMessageZZZ<IStatusBooleanMessageZZZ> getCircularBufferStatusLocal();
	public abstract void setCircularBufferStatusLocal(CircularBufferForStatusBooleanMessageZZZ<IStatusBooleanMessageZZZ> cb);
	
}
