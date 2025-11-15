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
		public String compute(String sMessage, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ;
		public String compute(String sMessage, IEnumSetMappedLogStringFormatZZZ[] ienumaFormatLogString) throws ExceptionZZZ;
		public String compute(String[] saMessage, IEnumSetMappedLogStringFormatZZZ[] ienumaFormatLogString) throws ExceptionZZZ;
		public String compute(Object obj, String sMessage, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ;
		public String compute(Object obj, String sMessage, IEnumSetMappedLogStringFormatZZZ[] ienumaFormatLogString) throws ExceptionZZZ;
		public String compute(Object obj, String[] saLog, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ;
		public String compute(Object obj, String[] saLog, IEnumSetMappedLogStringFormatZZZ[] ienumFormatLogString) throws ExceptionZZZ;

		public String compute(Object obj, String sMessage01, String sMessage02, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ; //Zwei Log String sind normal log01 wäre z.B. ReflectCodeZZZ.getPositionCurrent();
		public String compute(Object obj, String sMessage01, String sMessage02, IEnumSetMappedLogStringFormatZZZ[] ienumaFormatLogString) throws ExceptionZZZ; //Zwei Log String sind normal log01 wäre z.B. ReflectCodeZZZ.getPositionCurrent()

		//Das alles noch in einer Variante in der die Klasse uebergeben wird.
		public String compute(Class classObj, String sMessage, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ;
		public String compute(Class classObj, String sMessage, IEnumSetMappedLogStringFormatZZZ[] ienumaFormatLogString) throws ExceptionZZZ;
		public String compute(Class classObj, String[] saLog, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ;
		public String compute(Class classObj, String[] saLog, IEnumSetMappedLogStringFormatZZZ[] ienumFormatLogString) throws ExceptionZZZ;
		public String compute(Class classObj, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ;
		
		
		//Mit expliziter Angabe zu ILogStringZZZ.iFACTOR_CLASSMETHOD und darin ggfs. der komplette String, aber ohne konkrete Formatsangabe
		public String compute(LinkedHashMap<IEnumSetMappedLogStringFormatZZZ, String> hm)throws ExceptionZZZ;
		public String compute(Object obj, LinkedHashMap<IEnumSetMappedLogStringFormatZZZ, String> hm)throws ExceptionZZZ;
		public String compute(Class classObj, LinkedHashMap<IEnumSetMappedLogStringFormatZZZ, String> hm)throws ExceptionZZZ;
		
		
		//das ganze auch mit dem intern gespeicherten Format
		//Ohne Objekt / Klasse macht das aber keinen Sinn.
		
		//... mit Objekt
		public String compute(Object obj, String sMessage) throws ExceptionZZZ;
		public String compute(Object obj, String[] saMessage) throws ExceptionZZZ; //ACHTUNG: Hier werden beide Strings in einer Zeile zusammengefasst. Der 2. sollte dann mit dem zweiten argnext formatiert sein
			
		public String compute(Object obj, String sMessage01, String sMessage02) throws ExceptionZZZ; //ACHTUNG: Hier werden beide Strings in einer Zeile zusammengefasst. Der 2. sollte dann mit dem zweiten argnext formatiert sein
		
		//... mit Klasse
		public String compute(Class classObj, String sMessage) throws ExceptionZZZ;
		public String compute(Class classObj, String sMessage01, String sMessage02) throws ExceptionZZZ;
		public String compute(Class classObj, String[] saMessage) throws ExceptionZZZ;
		
}
