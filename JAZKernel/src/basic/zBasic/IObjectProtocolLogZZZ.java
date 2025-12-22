package basic.zBasic;

import basic.zBasic.util.log.IEnumSetMappedLogStringFormatZZZ;

public interface IObjectProtocolLogZZZ extends IObjectLogZZZ{
	//#################################
	//### Drei Wege Logs zu schreiben (A / B / C).
	//### Die Formatierung dieses Strings mit ILogStringZZZ - Methodik ist moeglich.
	//##################################
	
	//##################################
	//### A) Die Idee ist, das hier ein einfaches System.out gemacht wird. (siehe IObjectLogZZZ)
	//##################################
	//................
	
	//##################################
	//### B) Die Idee ist, das hier zusätzlich zu dem System.out noch an einer anderen Stelle protokolliert wird.
	//###    (s. IKernelObjectLogZZZ)
	//##################################
	
	//Merke: 20240130 Weil es in AbstractMainOVPN diese Methode gibt hier aufgenommen. Damit muss man die Codestellen nicht alle durch logLineDate ersetzen, wenn man mal ein Snippet daraus nutzt.
	public void logProtocol(String sLog) throws ExceptionZZZ; //Intention dahinter: Anders als logLineDate wird ggfs. noch woanders als im System.out protokolliert. In einfachen Klassen normalerweise wie logLineDate.
	public void logProtocol(String... sLogs) throws ExceptionZZZ; //Intention dahinter: Anders als logLineDate wird ggfs. noch woanders als im System.out protokolliert. In einfachen Klassen normalerweise wie logLineDate.
	
	//Merke: Ggfs. möchte man fuer ein anderes Objekt einen Log-Eintrag generieren (z.B. ein EventBroker für den gerade verarbeiteten Listener)
	public void logProtocol(Object obj, String sLog) throws ExceptionZZZ; //Intention dahinter: Anders als logLineDate wird ggfs. noch woanders als im System.out protokolliert. In einfachen Klassen normalerweise wie logLineDate.
	public void logProtocol(Object obj, String... sLogs) throws ExceptionZZZ; //Intention dahinter: Anders als logLineDate wird ggfs. noch woanders als im System.out protokolliert. In einfachen Klassen normalerweise wie logLineDate.
	
	//++++++++++++++++++++++++++
	//Merke: 20240427 Nun wird auch mit einer moeglichen Formatanweisung fuer LogStringFormatZZZ geschrieben.
	public void logProtocol(IEnumSetMappedLogStringFormatZZZ ienumMappedLogString, String sLog) throws ExceptionZZZ; //Intention dahinter: Anders als logLineDate wird ggfs. noch woanders als im System.out protokolliert. In einfachen Klassen normalerweise wie logLineDate.
	public void logProtocol(IEnumSetMappedLogStringFormatZZZ ienumMappedLogString, String... sLogs) throws ExceptionZZZ;
	public void logProtocol(IEnumSetMappedLogStringFormatZZZ[] ienumaMappedLogString, String... sLogs) throws ExceptionZZZ;
	
	//Merke: Ggfs. möchte man fuer ein anderes Objekt einen Log-Eintrag generieren (z.B. ein EventBroker für den gerade verarbeiteten Listener)
	public void logProtocol(Object obj, IEnumSetMappedLogStringFormatZZZ ienumMappedLogString, String sLog) throws ExceptionZZZ; //Intention dahinter: Anders als logLineDate wird ggfs. noch woanders als im System.out protokolliert. In einfachen Klassen normalerweise wie logLineDate.
	public void logProtocol(Object obj, IEnumSetMappedLogStringFormatZZZ ienumMappedLogString, String... sLogs) throws ExceptionZZZ;
	public void logProtocol(Object obj, IEnumSetMappedLogStringFormatZZZ[] ienumaMappedLogString, String... sLogs) throws ExceptionZZZ;
	
	//+++++++++++++++++++++++++++++++++++++++++++
	
	//##################################
	//### C) Die Idee ist, das hier zusätzlich zu dem System.out noch an einer anderen Stelle protokolliert wird.
	//###    (s. IKernelObjectLogZZZ) 
	//###    UND mit Angabe der CodePosition 
	//##################################
	//..............

}
