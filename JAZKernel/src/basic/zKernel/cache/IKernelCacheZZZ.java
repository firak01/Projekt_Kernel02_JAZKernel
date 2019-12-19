package basic.zKernel.cache;

import java.util.ArrayList;
import java.util.regex.Pattern;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractList.HashMapMultiZZZ;

public interface IKernelCacheZZZ {
	public HashMapMultiZZZ<String,String,ICachableObjectZZZ> getHashMapCache();
	public void setHashMapCache(HashMapMultiZZZ<String,String,ICachableObjectZZZ> hmMulti);
	
	public ICachableObjectZZZ getCacheEntry(String sSection, String sProperty) throws ExceptionZZZ; //Merke: In dem Cache wird wie in der Konfiguratins-Ini-Datei von Section und Property als Schlüsselwerten gesprochen.
	public void setCacheEntry(String sSection, String sProperty, ICachableObjectZZZ objEntry)throws ExceptionZZZ;
	
	//20190817: Mit der Einführung des Cache für Ini - Einträge hat sich gezeigt, dass es notwendig ist, berechnete Ausdrücke nach Änderung der Variablen neu zu holen.
	//Darum wird für die Ausdrücke, die diese Variablen enthalten, der Cache "geskipped".
	//Aufgerufen wird diese Methode beim Setzen einer KernelFileIniZZZ-Variablen.
	public int isCacheSkippedContainingVariable(boolean bValue, String sVariableName);
	public ArrayList<ICachableObjectZZZ>getCacheEntriesWithPropertiesByRegEx(String sRegEx);//Durchsuche alle CacheObjekte und prüfe, ob der a) "RAW"-Eintrag b) der Eintrag selbst dem Regex-Ausdruck entspricht.
	
	//Damit bestimmte Objekte herausgefiltert werden können
	public ICacheFilterZZZ getFilterObject(String sRexex); //ICacheFilterZZZ objFilter); //Der eigentliche Filterprozess beginnt dann in dem Filterobjekt, z.B. per Regex
}
