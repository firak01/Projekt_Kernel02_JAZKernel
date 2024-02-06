package basic.zKernel.status;

import basic.zBasic.IObjectZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedStatusZZZ;

public interface IEventObjectStatusLocalReactZZZ extends IEventObjectStatusBasicZZZ{
	public IEnumSetMappedStatusZZZ getStatusEnum();
	public String getStatusText();
	public boolean getStatusValue();
	public int getProcessID();
}
