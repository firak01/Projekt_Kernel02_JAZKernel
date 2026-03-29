package basic.zKernel.status;

import basic.zBasic.util.abstractList.CircularBufferForStatusBooleanMessageZZZ;

public interface ICircularBufferStatusBooleanUserZZZ extends ICircularBufferStatusBooleanUserBasicZZZ  {
	public abstract CircularBufferForStatusBooleanMessageZZZ<IStatusBooleanMessageZZZ> getCircularBufferStatusLocal();
	public abstract void setCircularBufferStatusLocal(CircularBufferForStatusBooleanMessageZZZ<IStatusBooleanMessageZZZ> cb);
}
