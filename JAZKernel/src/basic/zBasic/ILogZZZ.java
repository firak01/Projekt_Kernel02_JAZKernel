package basic.zBasic;

import basic.zBasic.util.log.IEnumSetMappedLogStringZZZ;

public interface ILogZZZ {
	public void logLineDate(String sLog) throws ExceptionZZZ; //Nutzt intern KernelLogZZZ-statische Methode;
	
	//Merke: 20240130 Weil es in AbstractMainOVPN diese Methode gibt hier aufgenommen. Damit muss man die Codestellen nicht alle durch logLineDate ersetzen, wenn man mal ein Snippet daraus nutzt.
	public void logProtocolString(String sLog) throws ExceptionZZZ; //Intention dahinter: Anders als logLineDate wird ggfs. noch woanders als im System.out protokolliert. In einfachen Klassen normalerweise wie logLineDate.
	public void logProtocolString(String[] saLog) throws ExceptionZZZ;
	
	//Merke: 20240427 Nun wird auch mit einer moeglichen Formatanweisung fuer LogStringFormatZZZ geschrieben.
	public void logProtocolString(String sLog, IEnumSetMappedLogStringZZZ ienumMappedLogString) throws ExceptionZZZ; //Intention dahinter: Anders als logLineDate wird ggfs. noch woanders als im System.out protokolliert. In einfachen Klassen normalerweise wie logLineDate.
	public void logProtocolString(String[] saLog, IEnumSetMappedLogStringZZZ[] ienumaMappedLogString) throws ExceptionZZZ;
	
}
