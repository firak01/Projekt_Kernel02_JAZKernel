package basic.zKernel.status;

import java.util.ArrayList;

import basic.zBasic.ExceptionZZZ;

public interface ICircularBufferStatusBooleanUserBasicZZZ{
	public abstract void debugCircularBufferStatusLocal() throws ExceptionZZZ;
	public abstract void debugCircularBufferStatusLocal(int iStepsMax) throws ExceptionZZZ;
	
	public abstract int getStatusLocalGroupIdFromCurrent();
	public abstract int getStatusLocalGroupIdFromPrevious();
	public abstract int getStatusLocalGroupIdFromPrevious(int iIndexStepsBack);
	
	public abstract int searchStatusLocalGroupIdPreviousDifferentFromCurrent() throws ExceptionZZZ;
	public abstract int searchStatusLocalGroupIndexLowerInBuffer(int iGroupId);
	public abstract int searchStatusLocalGroupIndexUpperInBuffer(int iGroupId);
	
	public abstract ArrayList<IStatusBooleanZZZ> searchStatusLocalGroupCurrent(boolean bWithoutInterruption) throws ExceptionZZZ;
	public abstract ArrayList<IStatusBooleanZZZ> searchStatusLocalGroupPrevious(boolean bWithoutInterruption) throws ExceptionZZZ;
	public abstract ArrayList<IStatusBooleanZZZ> searchStatusLocalGroupById(int iStatusLocalGroupId, boolean bWithoutInterruption) throws ExceptionZZZ;

}
