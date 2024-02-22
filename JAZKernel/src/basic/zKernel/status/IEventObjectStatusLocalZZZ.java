package basic.zKernel.status;

import basic.zBasic.util.abstractEnum.IEnumSetMappedStatusZZZ;

public interface IEventObjectStatusLocalZZZ extends IEventObjectStatusBasicZZZ{
	public IEnumSetMappedStatusZZZ getStatusLocal();
	public void setStatusLocal(IEnumSetMappedStatusZZZ objEnumSet);
	public void setStatusLocal(Enum objEnum);
	
	public String getStatusMessage();
	public void setStatusMessage(String sStatusMessage);
}
