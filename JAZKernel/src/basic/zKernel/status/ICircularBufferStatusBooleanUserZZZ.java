package basic.zKernel.status;

import basic.zBasic.util.abstractList.CircularBufferZZZ;

public interface ICircularBufferStatusBooleanUserZZZ {
	public abstract CircularBufferZZZ<IStatusBooleanZZZ> getCircularBufferStatusLocal();
	public abstract void setCircularBufferStatusLocal(CircularBufferZZZ<IStatusBooleanZZZ> cb);
	
	public abstract void debugCircularBufferStatusLocal(int iStepsMax);
}
