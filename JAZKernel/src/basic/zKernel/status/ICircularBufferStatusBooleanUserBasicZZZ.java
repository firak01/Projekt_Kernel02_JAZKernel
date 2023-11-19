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
	public abstract int searchStatusLocalGroupIndexLowerInBuffer() throws ExceptionZZZ;
	public abstract int searchStatusLocalGroupIndexLowerInBuffer(int iGroupId) throws ExceptionZZZ;
	public abstract int searchStatusLocalGroupIndexLowerInBuffer(int iGroupId, boolean bWithInterruption) throws ExceptionZZZ;
	public abstract int searchStatusLocalGroupIndexUpperInBuffer() throws ExceptionZZZ;
	public abstract int searchStatusLocalGroupIndexUpperInBuffer(int iGroupId) throws ExceptionZZZ;
	public abstract int searchStatusLocalGroupIndexUpperInBuffer(int iGroupId, boolean bWithInterruption) throws ExceptionZZZ;
	
	public abstract ArrayList<IStatusBooleanZZZ> searchStatusLocalGroupCurrent() throws ExceptionZZZ;
	public abstract ArrayList<IStatusBooleanZZZ> searchStatusLocalGroupCurrent(boolean bWithInterruption) throws ExceptionZZZ;
	public abstract ArrayList<IStatusBooleanZZZ> searchStatusLocalGroupPrevious() throws ExceptionZZZ;
	public abstract ArrayList<IStatusBooleanZZZ> searchStatusLocalGroupPrevious(boolean bWithInterruption) throws ExceptionZZZ;
	public abstract ArrayList<IStatusBooleanZZZ> searchStatusLocalGroupById(int iStatusLocalGroupId) throws ExceptionZZZ;
	public abstract ArrayList<IStatusBooleanZZZ> searchStatusLocalGroupById(int iStatusLocalGroupId, boolean bWithInterruption) throws ExceptionZZZ;

}
