package basic.zBasic.util.string.formater;

import java.util.LinkedHashMap;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractList.ArrayListZZZ;

public interface ILogStringFormatComputerZZZ {

		//auf ein intern gespeichertes Format angewiesen
		public String compute(String... sLogs) throws ExceptionZZZ;
	
		//ohne ein intern gespeichertes Format oder einen LogString zu verwenden.
		public String compute(IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ;
		public String compute(IEnumSetMappedLogStringFormatZZZ[] ienumaFormatLogString, String... sLogs) throws ExceptionZZZ;
		
		public String compute(Object obj, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ;
		public String compute(Object obj, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString, String... sLogs) throws ExceptionZZZ;
		public String compute(Object obj, IEnumSetMappedLogStringFormatZZZ[] ienumFormatLogString, String... sLogs) throws ExceptionZZZ;

		//Das alles noch in einer Variante in der die Klasse uebergeben wird.
		public String compute(Class classObj, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString, String... sLogs) throws ExceptionZZZ;		
		public String compute(Class classObj, IEnumSetMappedLogStringFormatZZZ[] ienumFormatLogString, String... sLogs) throws ExceptionZZZ;
		public String compute(Class classObj, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ;
		
		//Mit expliziter Angabe zu ILogStringZZZ.iFACTOR_CLASSMETHOD und darin ggfs. der komplette String, aber ohne konkrete Formatsangabe
		public String compute(LinkedHashMap<IEnumSetMappedLogStringFormatZZZ, String> hm)throws ExceptionZZZ;
		public String compute(Object obj, LinkedHashMap<IEnumSetMappedLogStringFormatZZZ, String> hm)throws ExceptionZZZ;
		public String compute(Class classObj, LinkedHashMap<IEnumSetMappedLogStringFormatZZZ, String> hm)throws ExceptionZZZ;
		
		
		//das ganze auch mit dem intern gespeicherten Format
		//Ohne Objekt / Klasse macht das aber keinen Sinn.
		
		//... mit Objekt		//
		public String compute(Object obj, String... sLogs) throws ExceptionZZZ; //ACHTUNG: Hier werden beide Strings in einer Zeile zusammengefasst. Der 2. sollte dann mit dem zweiten argnext formatiert sein
		
		//... mit Klasse
		public String compute(Class classObj, String... sLogs) throws ExceptionZZZ;	
		
		//############################
		//Jede Methode auch als ArrayListZZZ
		public ArrayListZZZ<String> computeJaggedArrayList(String... sLogs) throws ExceptionZZZ;
		
		public ArrayListZZZ<String> computeJaggedArrayList(LinkedHashMap<IEnumSetMappedLogStringFormatZZZ, String> hm) throws ExceptionZZZ;
		public ArrayListZZZ<String> computeJaggedArrayList(Object obj, LinkedHashMap<IEnumSetMappedLogStringFormatZZZ, String> hm) throws ExceptionZZZ;
		public ArrayListZZZ<String> computeJaggedArrayList(Class classObj, LinkedHashMap<IEnumSetMappedLogStringFormatZZZ, String> hmLog) throws ExceptionZZZ;
		
		public ArrayListZZZ<String> computeJaggedArrayList(Object obj, String... sLogs) throws ExceptionZZZ;
		public ArrayListZZZ<String> computeJaggedArrayList(Class classObj, String... sLogs) throws ExceptionZZZ;	
		
		public ArrayListZZZ<String> computeJaggedArrayList(IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ;
		public ArrayListZZZ<String> computeJaggedArrayList(IEnumSetMappedLogStringFormatZZZ[] ienumaFormatLogString, String... sLogs) throws ExceptionZZZ;
		
		public ArrayListZZZ<String> computeJaggedArrayList(Object obj, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ;
		public ArrayListZZZ<String> computeJaggedArrayList(Class classObj, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ;
		public ArrayListZZZ<String> computeJaggedArrayList(Object obj, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString, String... sLogs) throws ExceptionZZZ;		
		public ArrayListZZZ<String> computeJaggedArrayList(Class classObj, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString, String... sLogs) throws ExceptionZZZ;
		public ArrayListZZZ<String> computeJaggedArrayList(Object obj, IEnumSetMappedLogStringFormatZZZ[] ienumaFormatLogString, String... sLogs) throws ExceptionZZZ;
		public ArrayListZZZ<String> computeJaggedArrayList(Class classObj, IEnumSetMappedLogStringFormatZZZ[] ienumaFormatLogString, String... sLogs) throws ExceptionZZZ;

}
