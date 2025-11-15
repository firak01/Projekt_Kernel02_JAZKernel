package basic.zBasic.util.log;

import java.util.LinkedHashMap;

import basic.zBasic.ExceptionZZZ;

public interface ILogStringFormatManagerComputerZZZ {
	//Da der Manager verschiedenen FormatString-Erzeuger bedienen muss, 
	//gibt es hier die Methoden diese speziell als Objekt zu uebergeben.
	//Also wie ILogStringFormatComputerZZZ, nur mit ILogStringFormaterObjekt vorangestellt.
		
	//ohne ein intern gespeichertes Format oder einen LogString zu verwenden.
	public String compute(ILogStringFormaterZZZ objFormater, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ;
	public String compute(ILogStringFormaterZZZ objFormater,Object obj, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ;
	
	//ohne ein intern gespeichertes Format zu verwenden
	public String compute(ILogStringFormaterZZZ objFormater, String sMessage, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ;
	public String compute(ILogStringFormaterZZZ objFormater, String sMessage, IEnumSetMappedLogStringFormatZZZ[] ienumaFormatLogString) throws ExceptionZZZ;
	public String compute(ILogStringFormaterZZZ objFormater, String[] saMessage, IEnumSetMappedLogStringFormatZZZ[] ienumaFormatLogString) throws ExceptionZZZ;
	public String compute(ILogStringFormaterZZZ objFormater, Object obj, String sMessage, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ;
	public String compute(ILogStringFormaterZZZ objFormater, Object obj, String sMessage, IEnumSetMappedLogStringFormatZZZ[] ienumaFormatLogString) throws ExceptionZZZ;
	public String compute(ILogStringFormaterZZZ objFormater, Object obj, String[] saLog, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ;
	public String compute(ILogStringFormaterZZZ objFormater, Object obj, String[] saLog, IEnumSetMappedLogStringFormatZZZ[] ienumFormatLogString) throws ExceptionZZZ;

	public String compute(ILogStringFormaterZZZ objFormater, Object obj, String sMessage01, String sMessage02, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ; //Zwei Log String sind normal log01 wäre z.B. ReflectCodeZZZ.getPositionCurrent();
	public String compute(ILogStringFormaterZZZ objFormater, Object obj, String sMessage01, String sMessage02, IEnumSetMappedLogStringFormatZZZ[] ienumaFormatLogString) throws ExceptionZZZ; //Zwei Log String sind normal log01 wäre z.B. ReflectCodeZZZ.getPositionCurrent()

	//Das alles noch in einer Variante in der die Klasse uebergeben wird.
	public String compute(ILogStringFormaterZZZ objFormater, Class classObj, String sMessage, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ;
	public String compute(ILogStringFormaterZZZ objFormater, Class classObj, String sMessage, IEnumSetMappedLogStringFormatZZZ[] ienumaFormatLogString) throws ExceptionZZZ;
	public String compute(ILogStringFormaterZZZ objFormater, Class classObj, String[] saLog, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ;
	public String compute(ILogStringFormaterZZZ objFormater, Class classObj, String[] saLog, IEnumSetMappedLogStringFormatZZZ[] ienumFormatLogString) throws ExceptionZZZ;
	public String compute(ILogStringFormaterZZZ objFormater, Class classObj, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ;
	
	
	//Mit expliziter Angabe zu ILogStringZZZ.iFACTOR_CLASSMETHOD und darin ggfs. der komplette String, aber ohne konkrete Formatsangabe
	public String compute(ILogStringFormaterZZZ objFormater, LinkedHashMap<IEnumSetMappedLogStringFormatZZZ, String> hm)throws ExceptionZZZ;
	public String compute(ILogStringFormaterZZZ objFormater, Object obj, LinkedHashMap<IEnumSetMappedLogStringFormatZZZ, String> hm)throws ExceptionZZZ;
	public String compute(ILogStringFormaterZZZ objFormater, Class classObj, LinkedHashMap<IEnumSetMappedLogStringFormatZZZ, String> hm)throws ExceptionZZZ;
	
	
	//das ganze auch mit dem intern gespeicherten Format
	//Ohne Objekt / Klasse macht das aber keinen Sinn.
	
	//... mit Objekt
	public String compute(ILogStringFormaterZZZ objFormater, Object obj, String sMessage) throws ExceptionZZZ;
	public String compute(ILogStringFormaterZZZ objFormater, Object obj, String[] saMessage) throws ExceptionZZZ; //ACHTUNG: Hier werden beide Strings in einer Zeile zusammengefasst. Der 2. sollte dann mit dem zweiten argnext formatiert sein
		
	public String compute(ILogStringFormaterZZZ objFormater, Object obj, String sMessage01, String sMessage02) throws ExceptionZZZ; //ACHTUNG: Hier werden beide Strings in einer Zeile zusammengefasst. Der 2. sollte dann mit dem zweiten argnext formatiert sein
	
	//... mit Klasse
	public String compute(ILogStringFormaterZZZ objFormater, Class classObj, String sMessage) throws ExceptionZZZ;
	public String compute(ILogStringFormaterZZZ objFormater, Class classObj, String sMessage01, String sMessage02) throws ExceptionZZZ;
	public String compute(ILogStringFormaterZZZ objFormater, Class classObj, String[] saMessage) throws ExceptionZZZ;
	
}
