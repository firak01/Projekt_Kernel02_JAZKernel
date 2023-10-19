package basic.zKernel.status;

import basic.zBasic.IObjectZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedZZZ;

public interface IEventObjectStatusLocalSetZZZ extends IObjectZZZ{
	public IEnumSetMappedZZZ getStatusEnum();
	public String getStatusText();
	public boolean getStatusValue();
	public int getID();
}
