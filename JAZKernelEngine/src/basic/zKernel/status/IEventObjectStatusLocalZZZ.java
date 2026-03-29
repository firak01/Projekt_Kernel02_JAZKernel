package basic.zKernel.status;

import basic.zBasic.util.abstractEnum.IEnumSetMappedStatusLocalZZZ;

public interface IEventObjectStatusLocalZZZ extends IEventObjectStatusBasicZZZ{
	public IEnumSetMappedStatusLocalZZZ getStatusLocal();
	public void setStatusLocal(IEnumSetMappedStatusLocalZZZ objEnumSet);
	public void setStatusLocal(Enum objEnum);
	
	public String getStatusMessage();
	public void setStatusMessage(String sStatusMessage);
}
