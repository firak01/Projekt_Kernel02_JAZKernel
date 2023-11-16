package basic.zKernel.status;

import basic.zBasic.util.abstractList.CircularBufferZZZ;

public interface ICircularBufferStatusBooleanUserZZZ extends ICircularBufferStatusBooleanUserBasicZZZ  {
	public abstract CircularBufferZZZ<IStatusBooleanZZZ> getCircularBufferStatusLocal();
	public abstract void setCircularBufferStatusLocal(CircularBufferZZZ<IStatusBooleanZZZ> cb);
}
