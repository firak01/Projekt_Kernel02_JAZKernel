package basic.zBasic.util.log;


import static basic.zBasic.IReflectCodeZZZ.sPACKAGE_SEPERATOR;
import static basic.zBasic.IReflectCodeZZZ.sCLASS_METHOD_SEPERATOR;

import static basic.zBasic.IReflectCodeZZZ.sPOSITION_METHOD_IDENTIFIER;
import static basic.zBasic.IReflectCodeZZZ.sPOSITION_METHOD_SEPARATOR;
import static basic.zBasic.IReflectCodeZZZ.sPOSITION_FILE_IDENTIFIER;
import static basic.zBasic.IReflectCodeZZZ.sPOSITION_FILE_SEPARATOR;
import static basic.zBasic.IReflectCodeZZZ.sPOSITION_MESSAGE_IDENTIFIER;
import static basic.zBasic.IReflectCodeZZZ.sPOSITION_MESSAGE_SEPARATOR;


import java.util.EnumSet;
import java.util.HashMap;
import java.util.LinkedHashMap;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IReflectCodeZZZ;
import basic.zBasic.IDummyTestObjectWithStatusByInterfaceExtendedZZZ.STATUSLOCAL;
import basic.zBasic.util.abstractEnum.IEnumSetMappedStatusZZZ;
import basic.zBasic.util.abstractList.ArrayListUniqueZZZ;
import basic.zBasic.util.abstractList.HashMapIndexedZZZ;
import basic.zBasic.util.abstractList.HashMapMultiIndexedZZZ;
import basic.zBasic.util.datatype.string.StringJustifierZZZ;

public interface ILogStringFormatZZZ{
	public static String sENUMNAME="LOGSTRINGFORMAT";
	public static String sSEPARATOR_PREFIX_DEFAULT="";
	public static String sSEPARATOR_POSTFIX_DEFAULT="";
	public static String sSEPARATOR_MESSAGE_DEFAULT=IReflectCodeZZZ.sPOSITION_MESSAGE_SEPARATOR;
	
	//Merke: Es soll folgendes abgebildet werden, z.B. 
	//       String sLog = ReflectCodeZZZ.getPositionCurrent() + "[Thread: "+lngThreadID + "] Status='"+enumStatus.getName() +"', StatusValue="+bStatusValue+", StatusMessage='" + sStatusMessage +"'";
	
	//Das zu wird das Format als Anweisung in ein Array abgelegt, in dem dann die einzelnen Teilkomponenten ausgerechnet werden.
	
	//Merke: TODO Idee ist es das Custom-Format in einer einzigen Zahl zu definieren.
	//Merke: Jedes dieser Argumente ist als Primzahl zu defnieren.
	//       Es gilt Positionswert = Position * Primzahl
	public static int iARG_NOTHING = 0;
	public static int iARG_STRING = 1;
	public static int iARG_SYSTEM = 2;
	public static int iARG_OBJECT = 3;	
	public static int iARG_STRINGHASHMAP = 5;
	public static int iARG_STRINGXML = 7;
	
	
	
	//Argumente der compute Methode (sofern vorhanden und != null)
	//TODO IDEE: Wenn man eine Zahl angibt, soll die Zusammenstellung des Formats definiert sein.
	//           Darum sind das alles Primzahlen...			
	public static int iFACTOR_STRING_TYPE01=1;
	public static int iFACTOR_STRING_TYPE02=2;     //4 verschiedene Varianten fuer Argnext (Name entspricht der dahinterstehenden Primzahl)
	public static int iFACTOR_STRING_TYPE03=3;
	
	public static int iFACTOR_LINENEXT=5;
	
	public static int iFACTOR_CLASSNAME=7;
	public static int iFACTOR_CLASSNAMESIMPLE=11;
	public static int iFACTOR_CLASSFILENAME=13;
	
	public static int iFACTOR_CLASSMETHOD_STRING=19;
	public static int iFACTOR_CLASSFILELINE_STRING=23;
	public static int iFACTOR_CLASSFILENAME_STRING=29;
	public static int iFACTOR_CLASSFILEPOSITION_STRING=31;  //mit der Zeilenummer dahinter
	
	public static int iFACTOR_CLASSMETHOD_XML=41;
	public static int iFACTOR_CLASSFILELINE_XML=43;
	public static int iFACTOR_CLASSFILENAME_XML=47;
	public static int iFACTOR_CLASSFILEPOSITION_XML=53;  //mit der Zeilenummer dahinter
	public static int iFACTOR_POSITIONCURRENT_XML=59;  //Wird aus ObjectZZZ.getPostionCalling() geholt. Z.B. <method>searchDirectory</method><fileposition> (FileEasyZZZ.java:625) </fileposition>#
	
	public static int iFACTOR_CLASSMETHOD_HASHMAP=61;
	public static int iFACTOR_CLASSFILELINE_HASHMAP=67;
	public static int iFACTOR_CLASSFILENAME_HASHMAP=71;
	public static int iFACTOR_CLASSFILEPOSITION_HASHMAP=73;  //mit der Zeilenummer dahinter
	
	public static int iFACTOR_THREADID=79;
	public static int iFACTOR_DATE=83;
	
	//Weitere Primzahlen sind:
	//11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59,61 ,67 71, 73, 79, 83, 89, 97 "Algorithmus ist 'Das Sieb des Eratosthenes'"
	

	//Merke:
	//Statt mehrere Strukturen zu pflegen, lieber eine enum, mit den Konfigurationswerten.
	//Das hat auch den Vorteil, dass man nicht mehr mit abstrakten int - Zahlen bei der Definition der Struktur des Logformat-Arrays arbeiten muss
	//++++++++++++++++++++++++
	
	//TODOGOON20240503: Eine Sortierung des Arrays, welche als Formatierungsanweisung uebergeben werden ist wünschenswert.
	//                  Zuerst die ohne Argument, dann die mit Objekt als Argument, dann erst die Stringbasierten Argumente.
	//                  Diese NACHTRAEGLICHE Sortierung kann man zumindest bei dem Default-Format extra durchfuehren und so erzwingen. 
	
	//Merke: Methoden und Zeilennummern können nicht aus dem aktuellen Objekt ermittelt werden, daher sind sie von einem uebergebenen String abhaengig.
	//       POSITIONCALLING ist das entsprechende Format und kann fuer die Reihenfolge festgelegt werden.
	
	//ALIAS("Uniquename bzw. Tag",Faktor, "Format... Merke %s für den String wert muss für String.format() sein",Kennzeichen des Argumenttyps,"PostfixSeparatorString", "Beschreibung, wird nicht genutzt....")
	
	
	//RAUS
	//	
	
	//  CLASSPOSITION("classposition",ILogStringZZZ.iFACTOR_CLASSPOSITION, ILogStringZZZ.sSEPARATOR_PREFIX_DEFAULT + "[CP]", "[Class: %s]",ILogStringZZZ.iARG_STRING, ILogStringZZZ.sSEPARATOR_POSTFIX_DEFAULT + "[/CP]", "Gib die errechnete Position in der Java-Klasse in diesem Format aus."),
	//   CLASSMETHOD("classmethod",ILogStringZZZ.iFACTOR_CLASSMETHOD, ILogStringZZZ.sSEPARATOR_PREFIX_DEFAULT + "[CM]", "[Method: %s]",ILogStringZZZ.iARG_STRING, sPOSITION_METHOD_SEPARATOR + ILogStringZZZ.sSEPARATOR_POSTFIX_DEFAULT + "[/CM]", "Gib den Methodennamen in diesem Format aus."),
	//   CLASSFILEPOSITION("classfileposition",ILogStringZZZ.iFACTOR_CLASSFILEPOSITION, ILogStringZZZ.sSEPARATOR_PREFIX_DEFAULT + "[CFP]", "[File:%s]",ILogStringZZZ.iARG_STRING, sPOSITION_FILE_SEPARATOR + ILogStringZZZ.sSEPARATOR_POSTFIX_DEFAULT + "[/CFP]", "Gib die errechnete Position in der Java-Datei in diesem Format aus."),
	public enum LOGSTRINGFORMAT implements IEnumSetMappedLogStringFormatZZZ{//Folgendes geht nicht, da alle Enums schon von einer Java BasisKlasse erben... extends EnumSetMappedBaseZZZ{		
		STRINGTYPE01("stringtype01",ILogStringFormatZZZ.iFACTOR_STRING_TYPE01, "[A01]" + ILogStringFormatZZZ.sSEPARATOR_PREFIX_DEFAULT + sPOSITION_MESSAGE_SEPARATOR, "%s",ILogStringFormatZZZ.iARG_STRING, ILogStringFormatZZZ.sSEPARATOR_POSTFIX_DEFAULT + "[/A01]", "Gib den naechsten Log String - sofern vorhanden - in diesem Format aus."),
		STRINGTYPE02("stringtype02",ILogStringFormatZZZ.iFACTOR_STRING_TYPE02, "[A02]" + ILogStringFormatZZZ.sSEPARATOR_PREFIX_DEFAULT + sPOSITION_MESSAGE_SEPARATOR, "%s",ILogStringFormatZZZ.iARG_STRING, ILogStringFormatZZZ.sSEPARATOR_POSTFIX_DEFAULT + "[/A02]", "Gib den naechsten Log String - sofern vorhanden - in diesem Format aus."),
		STRINGTYPE03("stringtype03",ILogStringFormatZZZ.iFACTOR_STRING_TYPE03, "[A03]" + ILogStringFormatZZZ.sSEPARATOR_PREFIX_DEFAULT, "%s",ILogStringFormatZZZ.iARG_STRING, sPOSITION_MESSAGE_SEPARATOR + ILogStringFormatZZZ.sSEPARATOR_POSTFIX_DEFAULT + "[/A03]" , "Gib den naechsten Log String - sofern vorhanden - in diesem Format aus."),
		LINENEXT("linenext",ILogStringFormatZZZ.iFACTOR_LINENEXT, ILogStringFormatZZZ.sSEPARATOR_PREFIX_DEFAULT + "[A05/]", "%s",ILogStringFormatZZZ.iARG_NOTHING, ILogStringFormatZZZ.sSEPARATOR_POSTFIX_DEFAULT + "", "Scheibe zum naechsten ARGUMENT String, ohne Ausgabe des aktuellen LogString. Damit kann gesteuert werden, dass auch ohne ARGNEXT weitergegangen wird. Z.B. wenn die Filepostion (ist immer String, aber nicht argnext) vorher uebergeben worden ist."),				
		
		CLASSNAME("classname",ILogStringFormatZZZ.iFACTOR_CLASSNAME, ILogStringFormatZZZ.sSEPARATOR_PREFIX_DEFAULT + "[C]", "%s:",ILogStringFormatZZZ.iARG_OBJECT, ILogStringFormatZZZ.sSEPARATOR_POSTFIX_DEFAULT +  "[/C]", "Gib den Klassennamen mit Package in diesem Format aus."),
		CLASSNAMESIMPLE("classnamesimple",ILogStringFormatZZZ.iFACTOR_CLASSNAMESIMPLE, ILogStringFormatZZZ.sSEPARATOR_PREFIX_DEFAULT + "[CS]", "%s:",ILogStringFormatZZZ.iARG_OBJECT, ":" + ILogStringFormatZZZ.sSEPARATOR_POSTFIX_DEFAULT + "[/CS]", "Gib den einfachen Klassennamen in diesem Format aus."),				
		CLASSFILENAME("classfilename",ILogStringFormatZZZ.iFACTOR_CLASSFILENAME, ILogStringFormatZZZ.sSEPARATOR_PREFIX_DEFAULT + "[CF]", "[File:%s]",ILogStringFormatZZZ.iARG_OBJECT, sPOSITION_FILE_SEPARATOR + ILogStringFormatZZZ.sSEPARATOR_POSTFIX_DEFAULT + "[/CF]", "Gib den Dateinamen der Java-Klasse in diesem Format aus."),
		
		CLASSMETHOD_STRING("methodbystring",ILogStringFormatZZZ.iFACTOR_CLASSMETHOD_STRING, ILogStringFormatZZZ.sSEPARATOR_PREFIX_DEFAULT + "", "%s",ILogStringFormatZZZ.iARG_STRING, ILogStringFormatZZZ.sSEPARATOR_POSTFIX_DEFAULT + "", "Gib den Methodennamen in diesem  in diesem String Format aus, ermittelt in ReflectCodeZZZ.getPositionCurrent()."),		
		CLASSFILELINE_STRING("linenrbystring",ILogStringFormatZZZ.iFACTOR_CLASSFILELINE_STRING, ILogStringFormatZZZ.sSEPARATOR_PREFIX_DEFAULT + "", "%s",ILogStringFormatZZZ.iARG_STRING, ILogStringFormatZZZ.sSEPARATOR_POSTFIX_DEFAULT + "", "Gib die errechnete Codezeil in der Java-Datei in diesem String Format aus, ermittelt in ReflectCodeZZZ.getPositionCurrent()."),
		CLASSFILENAME_STRING("filenamebystring",ILogStringFormatZZZ.iFACTOR_CLASSFILENAME_STRING, ILogStringFormatZZZ.sSEPARATOR_PREFIX_DEFAULT + "", "%s",ILogStringFormatZZZ.iARG_STRINGXML, ILogStringFormatZZZ.sSEPARATOR_POSTFIX_DEFAULT + "", "Gib die errechnete Java-Datei in diesem String Format aus, ermittelt in ReflectCodeZZZ.getPositionCurrent()."),
		CLASSFILEPOSITION_STRING("filepositionbystring",ILogStringFormatZZZ.iFACTOR_CLASSFILEPOSITION_STRING, ILogStringFormatZZZ.sSEPARATOR_PREFIX_DEFAULT + "", "%s",ILogStringFormatZZZ.iARG_STRINGXML, ILogStringFormatZZZ.sSEPARATOR_POSTFIX_DEFAULT + "", "Gib die errechnete Position in der Java-Datei in diesem String Format aus, ermittelt in ReflectCodeZZZ.getPositionCurrent()."),		
		
		
		CLASSMETHOD_XML("method",ILogStringFormatZZZ.iFACTOR_CLASSMETHOD_XML, ILogStringFormatZZZ.sSEPARATOR_PREFIX_DEFAULT + "", "%s",ILogStringFormatZZZ.iARG_STRINGXML, ILogStringFormatZZZ.sSEPARATOR_POSTFIX_DEFAULT + "", "Gib den Methodennamen in diesem  in diesem XML-Tag Format aus, ermittelt in ReflectCodeZZZ.getPositionCurrentXml()."),
		CLASSFILELINE_XML("linenr",ILogStringFormatZZZ.iFACTOR_CLASSFILELINE_XML, ILogStringFormatZZZ.sSEPARATOR_PREFIX_DEFAULT + "", "%s",ILogStringFormatZZZ.iARG_STRINGXML, ILogStringFormatZZZ.sSEPARATOR_POSTFIX_DEFAULT + "", "Gib die errechnete Codezeil in der Java-Datei in diesem XML-Tag Format aus, ermittelt in ReflectCodeZZZ.getPositionCurrentXml()."),
		CLASSFILENAME_XML("filename",ILogStringFormatZZZ.iFACTOR_CLASSFILENAME_XML, ILogStringFormatZZZ.sSEPARATOR_PREFIX_DEFAULT + "", "%s",ILogStringFormatZZZ.iARG_STRINGXML, ILogStringFormatZZZ.sSEPARATOR_POSTFIX_DEFAULT + "", "Gib die errechnete Java-Datei in diesem XML-Tag Format aus, ermittelt in ReflectCodeZZZ.getPositionCurrentXml()."),
		CLASSFILEPOSITION_XML("fileposition",ILogStringFormatZZZ.iFACTOR_CLASSFILEPOSITION_XML, ILogStringFormatZZZ.sSEPARATOR_PREFIX_DEFAULT + "", "%s",ILogStringFormatZZZ.iARG_STRINGXML, ILogStringFormatZZZ.sSEPARATOR_POSTFIX_DEFAULT + "", "Gib die errechnete Position in der Java-Datei in diesem XML-Tag Format aus, ermittelt in ReflectCodeZZZ.getPositionCurrentXml()."),		
		POSITIONCURRENT_XML("positioncurrent", ILogStringFormatZZZ.iFACTOR_POSITIONCURRENT_XML, ILogStringFormatZZZ.sSEPARATOR_PREFIX_DEFAULT + "", "%s", ILogStringFormatZZZ.iARG_STRINGXML, ILogStringFormatZZZ.sSEPARATOR_POSTFIX_DEFAULT + "", "getPositionCurrent - Kann nur von aussen als String uebergeben werden. Wird geholt ueber ObjectZZZ.getPostitionCallingXml(). . Hat kuenstliches Tag <positioncurrent> und entaelt z.B. '<method>searchDirectory</method><fileposition> (FileEasyZZZ.java:625) </fileposition># '."),
		
		CLASSMETHOD_HASHMAP("methodbyhm",ILogStringFormatZZZ.iFACTOR_CLASSMETHOD_HASHMAP, ILogStringFormatZZZ.sSEPARATOR_PREFIX_DEFAULT + "", "%s",ILogStringFormatZZZ.iARG_STRINGHASHMAP, ILogStringFormatZZZ.sSEPARATOR_POSTFIX_DEFAULT + "", "Gib den Methodennamen in diesem  in diesem Format gespeichert in einer HashMap aus, ermittelt in ReflectCodeZZZ.getPositionCurrentXmlFormated()."),
		CLASSFILELINE_HASHMAP("linenrbyhm",ILogStringFormatZZZ.iFACTOR_CLASSFILELINE_HASHMAP, ILogStringFormatZZZ.sSEPARATOR_PREFIX_DEFAULT + "", "%s",ILogStringFormatZZZ.iARG_STRINGHASHMAP, ILogStringFormatZZZ.sSEPARATOR_POSTFIX_DEFAULT + "", "Gib die errechnete Codezeil in der Java-Datei in diesem Format gespeichert in einer HashMap aus, ermittelt in ReflectCodeZZZ.getPositionCurrentXmlFormated()."),
		CLASSFILENAME_HASHMAP("filenamebyhm",ILogStringFormatZZZ.iFACTOR_CLASSFILENAME_HASHMAP, ILogStringFormatZZZ.sSEPARATOR_PREFIX_DEFAULT + "", "%s",ILogStringFormatZZZ.iARG_STRINGHASHMAP, ILogStringFormatZZZ.sSEPARATOR_POSTFIX_DEFAULT + "", "Gib die errechnete Java-Datei in diesem Format gespeichert in einer HashMap aus, ermittelt in ReflectCodeZZZ.getPositionCurrentXmlFormated()."),
		CLASSFILEPOSITION_HASHMAP("filepositionbyhm",ILogStringFormatZZZ.iFACTOR_CLASSFILEPOSITION_HASHMAP, ILogStringFormatZZZ.sSEPARATOR_PREFIX_DEFAULT + "", "%s",ILogStringFormatZZZ.iARG_STRINGHASHMAP, ILogStringFormatZZZ.sSEPARATOR_POSTFIX_DEFAULT + "", "Gib die errechnete Position in der Java-Datei in diesem Format gespeichert in einer HashMap aus, ermittelt in ReflectCodeZZZ.getPositionCurrentXmlFormated()."),		
		
		THREADID("threadid",ILogStringFormatZZZ.iFACTOR_THREADID, ILogStringFormatZZZ.sSEPARATOR_PREFIX_DEFAULT + "[T]", "[Thread: %s]",ILogStringFormatZZZ.iARG_SYSTEM, "", "Gib die ID des Threads in diesem Format aus."),
		DATE("date",ILogStringFormatZZZ.iFACTOR_DATE,ILogStringFormatZZZ.sSEPARATOR_PREFIX_DEFAULT + "[D]", "[%s]",ILogStringFormatZZZ.iARG_SYSTEM, ILogStringFormatZZZ.sSEPARATOR_POSTFIX_DEFAULT + "[/D]", "Gib das errechnete Datum in diesem Format aus.")
		;		
		
		int iFactor, iArgumentType;
		private String sAbbreviation,sPrefixSeparator, sFormat,sPostfixSeparator, sDescription;
	
		//#############################################
		//#### Konstruktoren
		//Merke: Enums haben keinen public Konstruktor, können also nicht intiantiiert werden, z.B. durch Java-Reflektion.
		//In der Util-Klasse habe ich aber einen Workaround gefunden.
		LOGSTRINGFORMAT(String sAbbreviation, int iFactor, String sPrefixSeparator, String sFormat, int iArgumentType, String sPostfixSeparator, String sDescription) {
		    this.sAbbreviation = sAbbreviation;
		    this.iFactor = iFactor;
		    this.sPrefixSeparator = sPrefixSeparator;
		    this.sFormat = sFormat;
		    this.iArgumentType = iArgumentType;
		    this.sPostfixSeparator = sPostfixSeparator;
		    this.sDescription = sDescription;
		}
	
		public String getAbbreviation() {
			 return this.sAbbreviation;
			}
		
		public int getFactor() {
			return this.iFactor;
		}
		
		public String getPrefixSeparator() {
			return this.sPrefixSeparator;
		}
		
		public String getFormat() {
			 return this.sFormat;
		}
		
		public int getArgumentType() {
			return this.iArgumentType;
		}
		
		public String getPostfixSeparator() {
			return this.sPostfixSeparator;
		}
				
		public EnumSet<?>getEnumSetUsed(){
			return LOGSTRINGFORMAT.getEnumSet();
		}
	
		/* Die in dieser Methode verwendete Klasse für den ...TypeZZZ muss immer angepasst werden. */
		@SuppressWarnings("rawtypes")
		public static <E> EnumSet getEnumSet() {
			
		 //Merke: Das wird anders behandelt als FLAGZ Enumeration.
			//String sFilterName = "FLAGZ"; /
			//...
			//ArrayList<Class<?>> listEmbedded = ReflectClassZZZ.getEmbeddedClasses(this.getClass(), sFilterName);
			
			//Erstelle nun ein EnumSet, speziell für diese Klasse, basierend auf  allen Enumrations  dieser Klasse.
			Class<LOGSTRINGFORMAT> enumClass = LOGSTRINGFORMAT.class;
			EnumSet<LOGSTRINGFORMAT> set = EnumSet.noneOf(enumClass);//Erstelle ein leeres EnumSet
					
			Enum[]objaEnum = (Enum[]) enumClass.getEnumConstants();
			for(Object obj : objaEnum){
				//System.out.println(obj + "; "+obj.getClass().getName());
				set.add((LOGSTRINGFORMAT) obj);
			}
			return set;
			
		}
	
		//TODO: Mal ausprobieren was das bringt
		//Convert Enumeration to a Set/List
		private static <E extends Enum<E>>EnumSet<E> toEnumSet(Class<E> enumClass,long vector){
			  EnumSet<E> set=EnumSet.noneOf(enumClass);
			  long mask=1;
			  for (  E e : enumClass.getEnumConstants()) {
			    if ((mask & vector) == mask) {
			      set.add(e);
			    }
			    mask<<=1;
			  }
			  return set;
			}
	
		//+++ Das könnte auch in einer Utility-Klasse sein.
		//the valueOfMethod <--- Translating from DB
		public static LOGSTRINGFORMAT fromAbbreviation(String s) {
		for (LOGSTRINGFORMAT state : values()) {
		   if (s.equals(state.getAbbreviation()))
		       return state;
		}
		throw new IllegalArgumentException("Not a correct abbreviation: " + s);
		}
	
		//##################################################
		//#### Folgende Methoden bring Enumeration von Hause aus mit. 
				//Merke: Diese Methoden können aber nicht in eine abstrakte Klasse verschoben werden, zum daraus Erben. Grund: Enum erweitert schon eine Klasse.
		@Override
		public String getName() {	
			return super.name();
		}
	
		@Override
		public String toString() {//Mehrere Werte mit # abtennen
		    return this.sAbbreviation+"="+this.sDescription;
		}
	
		@Override
		public int getIndex() {
			return ordinal();
		}
	
		//### Folgende Methoden sind zum komfortablen Arbeiten gedacht.
		@Override
		public int getPosition() {
			return getIndex()+1; 
		}
	
		@Override
		public String getDescription() {
			return this.sDescription;
		}
		//+++++++++++++++++++++++++
	}//End internal Class
}
