package basic.zBasic.util.string.formater;

import java.util.LinkedHashMap;

import basic.zBasic.ExceptionZZZ;

public interface IStringFormatManagerJustifiedZZZ extends IStringFormatManagerZZZ, IStringFormatManagerComputerJustifiedZZZ{
	//Da der Manager verschiedenen FormatString-Erzeuger bedienen muss, 
	//gibt es hier die Methoden diese speziell als Objekt zu uebergeben.
	//Also wie ILogStringFormatComputerZZZ, nur mit ILogStringFormaterObjekt vorangestellt.
		
	//ohne ein intern gespeichertes Format oder einen LogString zu verwenden.
	public String compute(IStringFormaterZZZ objFormater, IEnumSetMappedStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ;
	public String compute(IStringFormaterZZZ objFormater,Object obj, IEnumSetMappedStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ;
	
	//ohne ein intern gespeichertes Format zu verwenden
	public String compute(IStringFormaterZZZ objFormater, IEnumSetMappedStringFormatZZZ[] ienumaFormatLogString, String... sLogs) throws ExceptionZZZ;
	public String compute(IStringFormaterZZZ objFormater, Object obj, IEnumSetMappedStringFormatZZZ[] ienumaFormatLogString, String... sLogs) throws ExceptionZZZ;
	public String compute(IStringFormaterZZZ objFormater, Object obj, IEnumSetMappedStringFormatZZZ ienumFormatLogString, String... sLogs) throws ExceptionZZZ; //Zwei Log String sind normal log01 wäre z.B. ReflectCodeZZZ.getPositionCurrent();
	

	//Das alles noch in einer Variante in der die Klasse uebergeben wird.
	public String compute(IStringFormaterZZZ objFormater, Class classObj, IEnumSetMappedStringFormatZZZ ienumFormatLogString, String... sLogs) throws ExceptionZZZ;
	public String compute(IStringFormaterZZZ objFormater, Class classObj, IEnumSetMappedStringFormatZZZ[] ienumaFormatLogString, String... sLogs) throws ExceptionZZZ;
	//sinn? public String compute(ILogStringFormaterZZZ objFormater, Class classObj, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ;
	
	
	//Mit expliziter Angabe zu ILogStringZZZ.iFACTOR_CLASSMETHOD und darin ggfs. der komplette String, aber ohne konkrete Formatsangabe
	public String compute(IStringFormaterZZZ objFormater, LinkedHashMap<IEnumSetMappedStringFormatZZZ, String> hm)throws ExceptionZZZ;
	public String compute(IStringFormaterZZZ objFormater, Object obj, LinkedHashMap<IEnumSetMappedStringFormatZZZ, String> hm)throws ExceptionZZZ;
	public String compute(IStringFormaterZZZ objFormater, Class classObj, LinkedHashMap<IEnumSetMappedStringFormatZZZ, String> hm)throws ExceptionZZZ;
	
	
	//das ganze auch mit dem intern gespeicherten Format
	//Ohne Objekt / Klasse macht das aber keinen Sinn.
	
	//... mit Objekt
	public String compute(IStringFormaterZZZ objFormater, Object obj, String... sLogs) throws ExceptionZZZ; //ACHTUNG: Hier werden beide Strings in einer Zeile zusammengefasst. Der 2. sollte dann mit dem zweiten argnext formatiert sein
	
	//... mit Klasse
	public String compute(IStringFormaterZZZ objFormater, Class classObj, String... sLogs) throws ExceptionZZZ;
	
}
