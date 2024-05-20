package basic.zBasic;

import basic.zBasic.util.log.IEnumSetMappedLogStringFormatZZZ;

public interface ILogZZZ {
	//!!! Merke 20240512: Mache in den (abstrakten) Klassen, die diese Methoden implementieren die Methoden "synchronized"
	public void logLineDate(String sLog) throws ExceptionZZZ; //Nutzt intern KernelLogZZZ-statische Methode;
	
	//Merke: 20240130 Weil es in AbstractMainOVPN diese Methode gibt hier aufgenommen. Damit muss man die Codestellen nicht alle durch logLineDate ersetzen, wenn man mal ein Snippet daraus nutzt.
	public void logProtocolString(String sLog) throws ExceptionZZZ; //Intention dahinter: Anders als logLineDate wird ggfs. noch woanders als im System.out protokolliert. In einfachen Klassen normalerweise wie logLineDate.
	public void logProtocolString(String[] saLog) throws ExceptionZZZ;
	
	//Merke: Ggfs. möchte man fuer ein anderes Objekt einen Log-Eintrag generieren (z.B. ein EventBroker für den gerade verarbeiteten Listener)
	public void logProtocolString(Object obj, String sLog) throws ExceptionZZZ; //Intention dahinter: Anders als logLineDate wird ggfs. noch woanders als im System.out protokolliert. In einfachen Klassen normalerweise wie logLineDate.
	public void logProtocolString(Object obj, String[] saLog) throws ExceptionZZZ;
	
	//++++++++++++++++++++++++++
	//Merke: 20240427 Nun wird auch mit einer moeglichen Formatanweisung fuer LogStringFormatZZZ geschrieben.
	public void logProtocolString(String sLog, IEnumSetMappedLogStringFormatZZZ ienumMappedLogString) throws ExceptionZZZ; //Intention dahinter: Anders als logLineDate wird ggfs. noch woanders als im System.out protokolliert. In einfachen Klassen normalerweise wie logLineDate.
	public void logProtocolString(String[] saLog, IEnumSetMappedLogStringFormatZZZ[] ienumaMappedLogString) throws ExceptionZZZ;
	
	//Merke: Ggfs. möchte man fuer ein anderes Objekt einen Log-Eintrag generieren (z.B. ein EventBroker für den gerade verarbeiteten Listener)
	public void logProtocolString(Object obj, String sLog, IEnumSetMappedLogStringFormatZZZ ienumMappedLogString) throws ExceptionZZZ; //Intention dahinter: Anders als logLineDate wird ggfs. noch woanders als im System.out protokolliert. In einfachen Klassen normalerweise wie logLineDate.
	public void logProtocolString(Object obj, String[] saLog, IEnumSetMappedLogStringFormatZZZ[] ienumaMappedLogString) throws ExceptionZZZ;

}
