package basic.zBasic.util.string.formater;

import basic.zBasic.util.abstractEnum.IEnumSetMappedZZZ;

public interface IEnumSetMappedLogStringFormatZZZ extends IEnumSetMappedZZZ{
	public static String sENUMNAME="LOGSTRINGFORMAT";
	
	//weitere Erweiterungen, speziell für LogString
	public int getFactor();
	public String getPrefixSeparator();
	public String getFormat();
	public int getArgumentType();
	public String getPostfixSeparator();
}
