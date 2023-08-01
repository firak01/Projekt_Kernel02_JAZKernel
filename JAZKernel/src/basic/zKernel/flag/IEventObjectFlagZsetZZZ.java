package basic.zKernel.flag;

import basic.zBasic.IObjectZZZ;
import basic.zBasic.util.datatype.enums.EnumZZZ;

public interface IEventObjectFlagZsetZZZ extends IObjectZZZ{
	public Enum getFlagEnum();
	public String getFlagText();
	public boolean getFlagValue();
	public int getID();
}
