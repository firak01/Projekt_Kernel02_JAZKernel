package basic.zBasic.util.log;

import java.util.LinkedHashMap;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractList.ArrayListZZZ;

public interface ILogStringFormatManagerComputerZZZ extends ILogStringFormatComputerZZZ, ILogStringFormatComputerJaggedZZZ{
	
	//##############################
	//### Da der Manager verschiedenen FormatString-Erzeuger bedienen muss, 
	//### gibt es hier die Methoden diese speziell als Objekt zu uebergeben (objFormater).
	//### Also wie ILogStringFormatComputerZZZ, nur mit ILogStringFormaterObjekt vorangestellt.
		
	//ohne ein intern gespeichertes Format oder einen LogString zu verwenden.
	public String compute(ILogStringFormaterZZZ objFormater, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ;
	public String compute(ILogStringFormaterZZZ objFormater,Object obj, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ;
	
	//ohne ein intern gespeichertes Format zu verwenden
	public String compute(ILogStringFormaterZZZ objFormater, IEnumSetMappedLogStringFormatZZZ[] ienumaFormatLogString, String... sLogs) throws ExceptionZZZ;
	public String compute(ILogStringFormaterZZZ objFormater, Object obj, IEnumSetMappedLogStringFormatZZZ[] ienumaFormatLogString, String... sLogs) throws ExceptionZZZ;
	public String compute(ILogStringFormaterZZZ objFormater, Object obj, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString, String... sLogs) throws ExceptionZZZ; //Zwei Log String sind normal log01 wäre z.B. ReflectCodeZZZ.getPositionCurrent();
	

	//Das alles noch in einer Variante in der die Klasse uebergeben wird.
	public String compute(ILogStringFormaterZZZ objFormater, Class classObj, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString, String... sLogs) throws ExceptionZZZ;
	public String compute(ILogStringFormaterZZZ objFormater, Class classObj, IEnumSetMappedLogStringFormatZZZ[] ienumaFormatLogString, String... sLogs) throws ExceptionZZZ;
	public String compute(ILogStringFormaterZZZ objFormater, Class classObj, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ;
	
	
	//Mit expliziter Angabe zu ILogStringZZZ.iFACTOR_CLASSMETHOD und darin ggfs. der komplette String, aber ohne konkrete Formatsangabe
	public String compute(ILogStringFormaterZZZ objFormater, LinkedHashMap<IEnumSetMappedLogStringFormatZZZ, String> hm)throws ExceptionZZZ;
	public String compute(ILogStringFormaterZZZ objFormater, Object obj, LinkedHashMap<IEnumSetMappedLogStringFormatZZZ, String> hm)throws ExceptionZZZ;
	public String compute(ILogStringFormaterZZZ objFormater, Class classObj, LinkedHashMap<IEnumSetMappedLogStringFormatZZZ, String> hm)throws ExceptionZZZ;
	
	
	//das ganze auch mit dem intern gespeicherten Format
	//Ohne Objekt / Klasse macht das aber keinen Sinn.
	
	//... mit Objekt
	public String compute(ILogStringFormaterZZZ objFormater, Object obj, String... sLogs) throws ExceptionZZZ; //ACHTUNG: Hier werden beide Strings in einer Zeile zusammengefasst. Der 2. sollte dann mit dem zweiten argnext formatiert sein
	
	//... mit Klasse
	public String compute(ILogStringFormaterZZZ objFormater, Class classObj, String... sLogs) throws ExceptionZZZ;
	
	
	
	//######################################
	//### JAGGED VARIANTE
	//######################################
	
	public String computeJagged(ILogStringFormaterZZZ objFormater, Object obj, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ;
	public String computeJagged(ILogStringFormaterZZZ objFormater, IEnumSetMappedLogStringFormatZZZ[] ienumaFormatLogString, String... sLogs) throws ExceptionZZZ;
	public String computeJagged(ILogStringFormaterZZZ objFormater, Object obj, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString, String... sLogs) throws ExceptionZZZ;	
	public String computeJagged(ILogStringFormaterZZZ objFormater, Object obj, IEnumSetMappedLogStringFormatZZZ[] ienumaFormatLogString, String... sLogs) throws ExceptionZZZ;
	public String computeJagged(ILogStringFormaterZZZ objFormater, Class classObj, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString, String... sLogs) throws ExceptionZZZ;
	public String computeJagged(ILogStringFormaterZZZ objFormater, Class classObj, IEnumSetMappedLogStringFormatZZZ[] ienumaFormatLogString, String... sLogs) throws ExceptionZZZ;
	public String computeJagged(ILogStringFormaterZZZ objFormater, Class classObj, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ;
	public String computeJagged(ILogStringFormaterZZZ objFormater, LinkedHashMap<IEnumSetMappedLogStringFormatZZZ, String> hm) throws ExceptionZZZ;
	public String computeJagged(ILogStringFormaterZZZ objFormater, Object obj, LinkedHashMap<IEnumSetMappedLogStringFormatZZZ, String> hm) throws ExceptionZZZ;
	public String computeJagged(ILogStringFormaterZZZ objFormater, Class classObj, LinkedHashMap<IEnumSetMappedLogStringFormatZZZ, String> hm) throws ExceptionZZZ;
	public String computeJagged(ILogStringFormaterZZZ objFormater, Object obj, String... sLogs) throws ExceptionZZZ;
	public String computeJagged(ILogStringFormaterZZZ objFormater, Class classObj, String... sLogs) throws ExceptionZZZ;		

	public String computeJagged(ILogStringFormaterZZZ objFormater, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ;
	
	//##### jede der Varianten auch als ArrayList
	public ArrayListZZZ<String> computeJaggedArrayList(ILogStringFormaterZZZ objFormater, Object obj, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ;
	public ArrayListZZZ<String> computeJaggedArrayList(ILogStringFormaterZZZ objFormater, IEnumSetMappedLogStringFormatZZZ[] ienumaFormatLogString, String... sLogs) throws ExceptionZZZ;
	public ArrayListZZZ<String> computeJaggedArrayList(ILogStringFormaterZZZ objFormater, Object obj, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString, String... sLogs) throws ExceptionZZZ;	
	public ArrayListZZZ<String> computeJaggedArrayList(ILogStringFormaterZZZ objFormater, Object obj, IEnumSetMappedLogStringFormatZZZ[] ienumaFormatLogString, String... sLogs) throws ExceptionZZZ;
	public ArrayListZZZ<String> computeJaggedArrayList(ILogStringFormaterZZZ objFormater, Class classObj, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString, String... sLogs) throws ExceptionZZZ;
	public ArrayListZZZ<String> computeJaggedArrayList(ILogStringFormaterZZZ objFormater, Class classObj, IEnumSetMappedLogStringFormatZZZ[] ienumaFormatLogString, String... sLogs) throws ExceptionZZZ;
	public ArrayListZZZ<String> computeJaggedArrayList(ILogStringFormaterZZZ objFormater, Class classObj, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ;
	public ArrayListZZZ<String> computeJaggedArrayList(ILogStringFormaterZZZ objFormater, LinkedHashMap<IEnumSetMappedLogStringFormatZZZ, String> hm) throws ExceptionZZZ;
	public ArrayListZZZ<String> computeJaggedArrayList(ILogStringFormaterZZZ objFormater, Object obj, LinkedHashMap<IEnumSetMappedLogStringFormatZZZ, String> hm) throws ExceptionZZZ;
	public ArrayListZZZ<String> computeJaggedArrayList(ILogStringFormaterZZZ objFormater, Class classObj, LinkedHashMap<IEnumSetMappedLogStringFormatZZZ, String> hm) throws ExceptionZZZ;
	public ArrayListZZZ<String> computeJaggedArrayList(ILogStringFormaterZZZ objFormater, Object obj, String... sLogs) throws ExceptionZZZ;
	public ArrayListZZZ<String> computeJaggedArrayList(ILogStringFormaterZZZ objFormater, Class classObj, String... sLogs) throws ExceptionZZZ;		

	public ArrayListZZZ<String> computeJaggedArrayList(ILogStringFormaterZZZ objFormater, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ;

	
}
