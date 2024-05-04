package basic.zBasic.util.log;

import basic.zBasic.util.abstractEnum.IEnumSetMappedZZZ;

public interface IEnumSetMappedLogStringFormatZZZ extends IEnumSetMappedZZZ{
		
	//weitere Erweiterungen, speziell für LogString
	public int getFactor();
	public String getPrefixSeparator();
	public String getFormat();
	public int getArgumentType();
	public String getPostfixSeparator();
}
