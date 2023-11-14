package basic.zKernel.status;

import basic.zBasic.IObjectZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedStatusZZZ;

public interface IEventObjectStatusLocalSetZZZ extends IObjectZZZ{
	public IEnumSetMappedStatusZZZ getStatusEnum();
	public String getStatusText();
	public boolean getStatusValue();
	public int getProcessID();
}
