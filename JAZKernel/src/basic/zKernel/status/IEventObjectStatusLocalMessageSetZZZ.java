package basic.zKernel.status;

import basic.zBasic.IObjectZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedStatusZZZ;

public interface IEventObjectStatusLocalMessageSetZZZ extends IEventObjectStatusLocalSetZZZ{
	public String getStatusMessage();
	public void setStatusMessage(String sStatusMessage);
}
