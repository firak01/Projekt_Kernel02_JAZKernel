package basic.zKernel.status;

import basic.zBasic.IObjectZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedStatusZZZ;

public interface IEventObjectStatusBasicZZZ extends IObjectZZZ{
	public Enum getStatusEnum();
	public String getStatusText(); //Das ist normalerweise der Wert des Enum
	public boolean getStatusValue();
}
