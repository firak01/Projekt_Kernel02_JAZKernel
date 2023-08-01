package basic.zKernel.status;

import basic.zBasic.IObjectZZZ;
import basic.zBasic.util.datatype.enums.EnumZZZ;

public interface IEventObjectStatusLocalSetZZZ extends IObjectZZZ{
	public Enum getStatusEnum();
	public String getStatusText();
	public boolean getStatusValue();
	public int getID();
}
