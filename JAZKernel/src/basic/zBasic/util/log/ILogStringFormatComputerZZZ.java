package basic.zBasic.util.log;

import java.util.LinkedHashMap;

import basic.zBasic.ExceptionZZZ;

public interface ILogStringFormatComputerZZZ {
	//Ein wenig Reflexion ueber das Format und den auszugebenend String
		//wird als static Methode angeboten public boolean isFormatUsingLogString(int iFormat);
		//wird als static Methode angeboten public boolean isFormatUsingObject(int iFormat);
		
		
		//ohne ein intern gespeichertes Format oder einen LogString zu verwenden.
		public String compute(IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ;
		public String compute(Object obj, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ;
		
		//ohne ein intern gespeichertes Format zu verwenden
		//public String compute(String sLog) throws ExceptionZZZ;
		//public String compute(String[] saLog) throws ExceptionZZZ;
		public String compute(String... sLogs) throws ExceptionZZZ;
		//public String compute(String sLog, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ;
		//public String compute(String sLog, IEnumSetMappedLogStringFormatZZZ[] ienumaFormatLogString) throws ExceptionZZZ;
		//public String compute(String[] saLog, IEnumSetMappedLogStringFormatZZZ[] ienumaFormatLogString) throws ExceptionZZZ;
		public String compute(IEnumSetMappedLogStringFormatZZZ[] ienumaFormatLogString, String... sLogs) throws ExceptionZZZ;
		//public String compute(Object obj, String sLog, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ;
		//public String compute(Object obj, String sLog, IEnumSetMappedLogStringFormatZZZ[] ienumaFormatLogString) throws ExceptionZZZ;
		//public String compute(Object obj, String[] saLog, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ;
		//public String compute(Object obj, String[] saLog, IEnumSetMappedLogStringFormatZZZ[] ienumFormatLogString) throws ExceptionZZZ;
		public String compute(Object obj, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString, String... sLogs) throws ExceptionZZZ;
		public String compute(Object obj, IEnumSetMappedLogStringFormatZZZ[] ienumFormatLogString, String... sLogs) throws ExceptionZZZ;

		
		//public String compute(Object obj, String sLog01, String sLog02, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ; //Zwei Log String sind normal log01 wäre z.B. ReflectCodeZZZ.getPositionCurrent();
		//public String compute(Object obj, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString, String... sLogs) throws ExceptionZZZ; //Zwei Log String sind normal log01 wäre z.B. ReflectCodeZZZ.getPositionCurrent();
		
		//public String compute(Object obj, String sLog01, String sLog02, IEnumSetMappedLogStringFormatZZZ[] ienumaFormatLogString) throws ExceptionZZZ; //Zwei Log String sind normal log01 wäre z.B. ReflectCodeZZZ.getPositionCurrent()
		//public String compute(Object obj, IEnumSetMappedLogStringFormatZZZ[] ienumaFormatLogString, String... sLog) throws ExceptionZZZ; //Zwei Log String sind normal log01 wäre z.B. ReflectCodeZZZ.getPositionCurrent()

		//Das alles noch in einer Variante in der die Klasse uebergeben wird.
		//public String compute(Class classObj, String sLog, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ;
		//public String compute(Class classObj, String sLog, IEnumSetMappedLogStringFormatZZZ[] ienumaFormatLogString) throws ExceptionZZZ;
		//public String compute(Class classObj, String[] saLog, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ;
		public String compute(Class classObj, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString, String... sLogs) throws ExceptionZZZ;
		//public String compute(Class classObj, String[] saLog, IEnumSetMappedLogStringFormatZZZ[] ienumFormatLogString) throws ExceptionZZZ;
		public String compute(Class classObj, IEnumSetMappedLogStringFormatZZZ[] ienumFormatLogString, String... sLogs) throws ExceptionZZZ;
		public String compute(Class classObj, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ;
		
		
		//Mit expliziter Angabe zu ILogStringZZZ.iFACTOR_CLASSMETHOD und darin ggfs. der komplette String, aber ohne konkrete Formatsangabe
		public String compute(LinkedHashMap<IEnumSetMappedLogStringFormatZZZ, String> hm)throws ExceptionZZZ;
		public String compute(Object obj, LinkedHashMap<IEnumSetMappedLogStringFormatZZZ, String> hm)throws ExceptionZZZ;
		public String compute(Class classObj, LinkedHashMap<IEnumSetMappedLogStringFormatZZZ, String> hm)throws ExceptionZZZ;
		
		
		//das ganze auch mit dem intern gespeicherten Format
		//Ohne Objekt / Klasse macht das aber keinen Sinn.
		
		//... mit Objekt
		//public String compute(Object obj, String sLog) throws ExceptionZZZ;
		//public String compute(Object obj, String[] saLog) throws ExceptionZZZ; //ACHTUNG: Hier werden beide Strings in einer Zeile zusammengefasst. Der 2. sollte dann mit dem zweiten argnext formatiert sein
		public String compute(Object obj, String... sLogs) throws ExceptionZZZ; //ACHTUNG: Hier werden beide Strings in einer Zeile zusammengefasst. Der 2. sollte dann mit dem zweiten argnext formatiert sein
			
//		public String compute(Object obj, String sLog01, String sLog02) throws ExceptionZZZ; //ACHTUNG: Hier werden beide Strings in einer Zeile zusammengefasst. Der 2. sollte dann mit dem zweiten argnext formatiert sein
		//public String compute(Object obj, String... sLogs) throws ExceptionZZZ; //ACHTUNG: Hier werden beide Strings in einer Zeile zusammengefasst. Der 2. sollte dann mit dem zweiten argnext formatiert sein
		
		//... mit Klasse
		//public String compute(Class classObj, String sLog) throws ExceptionZZZ;
		//public String compute(Class classObj, String sLog01, String sLog02) throws ExceptionZZZ;
		//public String compute(Class classObj, String[] saLog) throws ExceptionZZZ;
		public String compute(Class classObj, String... sLogs) throws ExceptionZZZ;
		
}
