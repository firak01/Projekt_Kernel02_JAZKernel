package basic.zBasic.util.data;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import basic.zBasic.util.datatype.string.StringZZZ;

public class DataFieldZZZ implements IDataConstZZZ {	
	//!!! Diese Konstanten werden gesetzt damit per Reflection API folgende Abfrage möglich ist: DataFieldZZZ. isValidMetadataType(String sMetadataType){
	public static final String FIELDNAME = "Fieldname";
	public static final String FIELDCLASS = "Fieldclass";
	public static final String FIELDMETHOD = "Fieldmethod";
	public static final String ZCLASS = "Zclass";
	public static final String ZMETHOD = "Zmethod";
	
	//+++20080127
	public static final String TARGETVALUEHANDLING = "Targetvaluehandling";
	
	
	public static final String FORMAT = "Format";
	public static final String DATATYPE = "Datatype";
	
	public static final String ALIAS = "Alias";
	public static final String CUSTOM_CLASS_POST_STORE_READ = "CustomClassPostStoreRead";
	public static final String CUSTOM_METHOD_POST_STORE_READ = "CustomMethodPostStoreRead";
	public static final String CUSTOM_CLASS_POST_TARGET_INSERT = "CustomClassPostTargetInsert";
	public static final String CUSTOM_METHOD_POST_TARGET_INSERT = "CustomMethodPostTargetInsert";
	
	
	//!!! Diese Klasse darf nur Strings als Property enthalten.
	//!!! Diese Properties werden dynamisch ausgelesen in DataStoreZZZ.setField(..)
	public String Alias=null;
	public String Fieldname=null;
	public String Fieldmethod=null; //name der Methode, mit der die Werte aus der Quelle ausgelesen werden sollen
	public String Fieldclass=null; //name der Klasse, dieser Komponente (z.B: JComboBox) 
	
	public String Datatype=null;
	public String Format=null; //Wird z.B. benötigt, wenn man einen Sting übergeben will, als Datum !!! . Siehe SimpleDateFormat-Klasse
								  //z.B. für den 28.11.2006 als String ist das Format dd.MM.yyyy   während das Format dd.mm.yyyy den 28.01.2006 00:11 ausgibt, also mm sind die Minuten !!!
	public String isKey=null;
	
	
	//+++ 20080127 Folgende Properties dienen der Bearbeitung von Werten
	public String Targetvaluehandling=null; //Hiermit wird festgelegt, wie die Wert beim Einfügen behandelt werden, wenn es schon Werte gibt: Ersetze bestehende, hänge an, hänge vorne an, behalte alten wert
	
	//+++ 20080201 Folgende Properties dienen der Berechnung von Werten aus anderen Werten. Z.B. vor dem Versenden von Maskeninhalten an ein Servlet.
	public String Zclass = null; //hier steht eine @Z Formel drin, bzw. nur @Z
	public String Zmethod = null;//hier steht dann die eigentliche Formel drin
	
	//+++ Folgende Properties dienen dem dynamischen Aufruf bestimmter Custom-Routinen.  
	public String CustomClassPostStoreRead=null;    //Diese Klasse wird nach dem Auslesen des Werts aus dem DataStore ausgeführt.
	public String CustomMethodPostStoreRead=null; // ... eben diese Methode wird ausgeführt. Merke: Merke: Es können hier keine Parameter übergeben werden. Die Methode muss sich quasi alles "zusammensuchen"
	public String CustomClassPostTargetInsert=null; //Diese Klasse wird nach dem Auslesen und dem Einfügen des Wertes in das Ziel (z.B. dem Notesdokument) gesucht, zur "Nachverarbeitung"
	public String CustomMethodPostTargetInsert=null;  //... eben diese Methode wird ausgeführt. Merke: Es können hier keine Parameter übergeben werden. Die Methode muss sich quasi alles "zusammensuchen"
	
	
	//IDEE; Die Felder, die Pflichtfelder sind, sollten schon im Konstruktor mitgegeben werden.
	public DataFieldZZZ(String sAlias){
		this.Alias = sAlias;
	} 
	
	public static boolean isValidMetadataType(String sMetadataType){
		boolean bReturn = false;
		main:{
			if(StringZZZ.isEmpty(sMetadataType)) break main;
			
			/* Besser, per Reflection lösen
			if(! sMetadataType.equalsIgnoreCase(FIELDNAME) &&
			! sMetadataType.equalsIgnoreCase(FIELDMETHOD) &&
			! sMetadataType.equalsIgnoreCase(FIELDCLASS) &&
			! sMetadataType.equalsIgnoreCase(DATATYPE) &&
			! sMetadataType.equalsIgnoreCase(FORMAT) &&
			! */
			
			Class cl = DataFieldZZZ.class;
			Field[] fielda = cl.getDeclaredFields();
			for(int icount = 0; icount < fielda.length-1; icount++){
				Field field = fielda[icount];
				
				//Dies nur auf static - Felder beschränken
				if(Modifier.isStatic(field.getModifiers())){
					String stemp = field.getName();
					if(stemp.equalsIgnoreCase(sMetadataType)){
						bReturn = true;
						break main;
					}
				}
			}
			
		}//end main
		return bReturn;
	}
}
