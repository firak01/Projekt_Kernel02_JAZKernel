package basic.zBasic.util.data;

public interface IDataConstZZZ {
	public final static String sSTRING = "STRING";
	public final static String sINTEGER = "INTEGER";
	public final static String sLONG = "LONG";
	public final static String sDOUBLE = "DOUBLE";	
	public final static String sDATE = "DATE";
	public final static String sOBJECT = "OBJECT"; //zum Speichern von Objekten (auch in Notes)
		
	public final static String sNOTESRICHTEXT = "NOTESRICHTEXT";
	public final static String sNOTESAUTHOR = "NOTESAUTHOR";
	public final static String sNOTESREADER = "NOTESREADER";
	public final static String sNOTESNAME = "NOTESNAME";
	
//	+++ 20080127
	//+++ Was mit dem TargetValueHandling alles gemacht werden kann, hier als Konstante anbieten
	public static final String sTARGET_VALUE_APPEND = "a";
	public static final String sTARGET_VALUE_PREPEND = "p";
	public static final String sTARGET_VALUE_REPLACE = "r"; //ersetze alten wert
	public static final String sTARGET_VALUE_KEEP = "k";  //behalte alten wert
	
	//+++ 20080513 Das "unique machen" hinzugefügt
	public static final String sTARGET_VALUE_APPEND_UNIQUE = "au";
	public static final String sTARGET_VALUE_PREPEND_UNIQUE = "pu";
	public static final String sTARGET_VALUE_REPLACE_UNIQUE = "ru";
	public static final String sTARGET_VALUE_KEEP_UNIQUE = "ku";
	
}
