package basic.zBasic;

import basic.zBasic.util.log.IEnumSetMappedLogStringFormatZZZ;

public interface IObjectPositionLogZZZ extends IObjectProtocolLogZZZ{
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
	//................
	
	//##################################
	//### C) Die Idee ist, das hier zusätzlich zu dem System.out noch an einer anderen Stelle protokolliert wird.
	//###    (s. IKernelObjectLogZZZ) 
	//###    UND mit Angabe der CodePosition 
	//##################################
	
	//Merke: Wie bei logLineDate... hier auch die Version mit ...WithPosition
	public void logProtocolWithPosition(String sLog) throws ExceptionZZZ; //Intention dahinter: Anders als logLineDate wird ggfs. noch woanders als im System.out protokolliert. In einfachen Klassen normalerweise wie logLineDate.
	public void logProtocolWithPosition(String... sLogs) throws ExceptionZZZ;
		
	public void logProtocolWithPosition(Object obj, String sLog) throws ExceptionZZZ; //Intention dahinter: Anders als logLineDate wird ggfs. noch woanders als im System.out protokolliert. In einfachen Klassen normalerweise wie logLineDate.
	public void logProtocolWithPosition(Object obj, String... sLogs) throws ExceptionZZZ;
	
	public void logProtocolWithPosition(IEnumSetMappedLogStringFormatZZZ ienumMappedLogString, String sLog) throws ExceptionZZZ; //Intention dahinter: Anders als logLineDate wird ggfs. noch woanders als im System.out protokolliert. In einfachen Klassen normalerweise wie logLineDate.
	public void logProtocolWithPosition(IEnumSetMappedLogStringFormatZZZ ienumMappedLogString, String... sLogs) throws ExceptionZZZ;
	public void logProtocolWithPosition(IEnumSetMappedLogStringFormatZZZ[] ienumaMappedLogString, String... sLogs) throws ExceptionZZZ;
	
	public void logProtocolWithPosition(Object obj, IEnumSetMappedLogStringFormatZZZ ienumMappedLogString, String sLog) throws ExceptionZZZ; //Intention dahinter: Anders als logLineDate wird ggfs. noch woanders als im System.out protokolliert. In einfachen Klassen normalerweise wie logLineDate.
	public void logProtocolWithPosition(Object obj, IEnumSetMappedLogStringFormatZZZ ienumMappedLogString, String... sLogs) throws ExceptionZZZ;
	public void logProtocolWithPosition(Object obj, IEnumSetMappedLogStringFormatZZZ[] ienumaMappedLogString, String... sLogs) throws ExceptionZZZ;
}
