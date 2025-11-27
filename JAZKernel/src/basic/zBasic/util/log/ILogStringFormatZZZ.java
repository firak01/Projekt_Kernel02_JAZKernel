package basic.zBasic.util.log;

import static basic.zBasic.IReflectCodeZZZ.sPOSITION_METHOD_SEPARATOR;
import static basic.zBasic.IReflectCodeZZZ.sPOSITION_FILE_SEPARATOR;
import static basic.zBasic.IReflectCodeZZZ.sPOSITION_MESSAGE_SEPARATOR;

import java.util.EnumSet;

import basic.zBasic.util.datatype.xml.XmlUtilUnensuredZZZ;
import basic.zBasic.IReflectCodeZZZ;
import basic.zBasic.reflection.position.ITagTypeClassNameZZZ;
import basic.zBasic.reflection.position.ITagTypeDateZZZ;
import basic.zBasic.reflection.position.ITagTypeThreadIdZZZ;
import basic.zBasic.reflection.position.ITagTypeFileNameZZZ;
import basic.zBasic.reflection.position.ITagTypeFilePositionZZZ;
import basic.zBasic.reflection.position.ITagTypeLineNumberZZZ;
import basic.zBasic.reflection.position.ITagTypeMethodZZZ;

public interface ILogStringFormatZZZ extends ITagTypeMethodZZZ, ITagTypeLineNumberZZZ, ITagTypeFileNameZZZ, ITagTypeFilePositionZZZ{ //verwenden von z.B. import static basic.zBasic.reflection.position.ITagTypeFileNameZZZ.*; geht nicht, weil ja die Konstanten alle gleich heissen. 
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
	public static int iARG_CONTROL = 0;
	public static int iARG_STRING = 1;
	public static int iARG_SYSTEM = 2;
	public static int iARG_OBJECT = 3;	
	public static int iARG_STRINGHASHMAP = 5;
	public static int iARG_STRINGXML = 7;
	
	
	
	//Argumente der compute Methode (sofern vorhanden und != null)
	//TODO IDEE: Wenn man eine Zahl angibt, soll die Zusammenstellung des Formats definiert sein.
	//           Darum sind das alles Primzahlen...			
	public static int iFACTOR_STRINGTYPE01_STRING_BY_STRING=1;
	public static int iFACTOR_STRINGTYPE02_STRING_BY_STRING=2;     //4 verschiedene Varianten fuer Argnext (Name entspricht der dahinterstehenden Primzahl)
	public static int iFACTOR_STRINGTYPE03_STRING_BY_STRING=3;
	
	public static int iFACTOR_LINENEXT_STRING_BY_STRING=5;
	
	public static int iFACTOR_CLASSNAME_STRING_BY_STRING=7;
	public static int iFACTOR_CLASSNAMESIMPLE_STRING_BY_STRING=11;
	public static int iFACTOR_CLASSFILENAME_STRING_BY_STRING=13;
	
	public static int iFACTOR_CLASSMETHOD_STRING_BY_HASHMAP=19;
	public static int iFACTOR_CLASSFILELINE_STRING_BY_HASHMAP=23;
	public static int iFACTOR_CLASSFILENAME_STRING_BY_HASHMAP=29;
	public static int iFACTOR_CLASSFILEPOSITION_STRING_BY_HASHMAP=31;  //mit der Zeilenummer dahinter
	
	public static int iFACTOR_CLASSMETHOD_XML_BY_XML=41;
	public static int iFACTOR_CLASSFILELINE_XML_BY_XML=43;
	public static int iFACTOR_CLASSFILENAME_XML_BY_XML=47;
	public static int iFACTOR_CLASSFILEPOSITION_XML_BY_XML=53;  //mit der Zeilenummer dahinter
	public static int iFACTOR_POSITIONCURRENT_XML_BY_XML=59;  //Wird aus ObjectZZZ.getPostionCalling() geholt. Z.B. <method>searchDirectory</method><fileposition> (FileEasyZZZ.java:625) </fileposition>#
	
	public static int iFACTOR_DATE_XML_BY_HASHMAP=61;
	public static int iFACTOR_THREADID_XML_BY_HASHMAP=67;
	public static int iFACTOR_CLASSNAME_XML_BY_HASHMAP=71;
	public static int iFACTOR_CLASSNAMESIMPLE_XML_BY_HASHMAP=73;
	public static int iFACTOR_CLASSMETHOD_XML_BY_HASHMAP=79;
	public static int iFACTOR_CLASSFILELINE_XML_BY_HASHMAP=83;
	public static int iFACTOR_CLASSFILENAME_XML_BY_HASHMAP=89;
	public static int iFACTOR_CLASSFILEPOSITION_XML_BY_HASHMAP=97;  //mit der Zeilenummer dahinter
	
	public static int iFACTOR_CLASSMETHOD_STRING_BY_XML=101;
	public static int iFACTOR_CLASSFILELINE_STRING_BY_XML=103;
	public static int iFACTOR_CLASSFILENAME_STRING_BY_XML=107;
	public static int iFACTOR_CLASSFILEPOSITION_STRING_BY_XML=113;  //mit der Zeilenummer dahinter
	public static int iFACTOR_POSITIONCURRENT_STRING_BY_XML=127;   //Wird aus ObjectZZZ.getPostionCalling() geholt. Z.B. <method>searchDirectory</method><fileposition> (FileEasyZZZ.java:625) </fileposition>#	
	                                                               //... die Tags werden daraus entfernt.
	
	public static int iFACTOR_THREADID_STRING_BY_STRING=131;
	public static int iFACTOR_DATE_STRING_BY_STRING=137;
	
	//Weitere Primzahlen sind:
	//11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59,61 ,67 71, 73, 79, 83, 89, 97 "Algorithmus ist 'Das Sieb des Eratosthenes'"
	//101,103,107,109,113,127,131,137,139,149,151,157,163,167,173,179,181,191,193,197,199

	//Statt mehrere Strukturen zu pflegen, lieber eine enum, mit den Konfigurationswerten.
	//Merke: Methoden und Zeilennummern können nicht aus dem aktuellen Objekt ermittelt werden, daher sind sie von einem uebergebenen String abhaengig.
	//       POSITIONCALLING ist das entsprechende Format und kann fuer die Reihenfolge festgelegt werden.

	//Definition des Verhaltens:
	//String Typ  -> 1:1 den Wert uebernehmen mit PRE und POST Anweisung
	//HashMap Typ -> 1:1 den Wert uebernehmen mit PRE und POST Anweisung
	//XML Typ     -> Den Tag per definierten Type auslesen und als XML zurueckgeben.
	
	//Aufbau des Enum:
	//ALIAS("Uniquename bzw. Tag",Faktor, "Format... Merke %s für den String wert muss für String.format() sein",Kennzeichen des Argumenttyps,"PostfixSeparatorString", "Beschreibung, wird nicht genutzt....")	
		public enum LOGSTRINGFORMAT implements IEnumSetMappedLogStringFormatZZZ{//Folgendes geht nicht, da alle Enums schon von einer Java BasisKlasse erben... extends EnumSetMappedBaseZZZ{		
		STRINGTYPE01_STRING_BY_STRING("stringtype01",ILogStringFormatZZZ.iFACTOR_STRINGTYPE01_STRING_BY_STRING, "[A01]" + ILogStringFormatZZZ.sSEPARATOR_PREFIX_DEFAULT + sPOSITION_MESSAGE_SEPARATOR, "%s",ILogStringFormatZZZ.iARG_STRING,  "[/A01]" + ILogStringFormatZZZ.sSEPARATOR_POSTFIX_DEFAULT, "Gib den naechsten Log String - sofern vorhanden - in diesem Format aus."),
		STRINGTYPE02_STRING_BY_STRING("stringtype02",ILogStringFormatZZZ.iFACTOR_STRINGTYPE02_STRING_BY_STRING, "[A02]" + ILogStringFormatZZZ.sSEPARATOR_PREFIX_DEFAULT + sPOSITION_MESSAGE_SEPARATOR, "%s",ILogStringFormatZZZ.iARG_STRING, "[/A02]" + ILogStringFormatZZZ.sSEPARATOR_POSTFIX_DEFAULT, "Gib den naechsten Log String - sofern vorhanden - in diesem Format aus."),
		STRINGTYPE03_STRING_BY_STRING("stringtype03",ILogStringFormatZZZ.iFACTOR_STRINGTYPE03_STRING_BY_STRING, "[A03]" + ILogStringFormatZZZ.sSEPARATOR_PREFIX_DEFAULT, "%s",ILogStringFormatZZZ.iARG_STRING, sPOSITION_MESSAGE_SEPARATOR + "[/A03]" + ILogStringFormatZZZ.sSEPARATOR_POSTFIX_DEFAULT, "Gib den naechsten Log String - sofern vorhanden - in diesem Format aus."),
		LINENEXT("linenext",ILogStringFormatZZZ.iFACTOR_LINENEXT_STRING_BY_STRING, ILogStringFormatZZZ.sSEPARATOR_PREFIX_DEFAULT + "[A05/]", "%s",ILogStringFormatZZZ.iARG_CONTROL, ILogStringFormatZZZ.sSEPARATOR_POSTFIX_DEFAULT + "", "Scheibe zum naechsten ARGUMENT String, ohne Ausgabe des aktuellen LogString. Damit kann gesteuert werden, dass auch ohne ARGNEXT weitergegangen wird. Z.B. wenn die Filepostion (ist immer String, aber nicht argnext) vorher uebergeben worden ist."),				
		
		CLASSNAME_STRING_BY_STRING("classname",ILogStringFormatZZZ.iFACTOR_CLASSNAME_STRING_BY_STRING, ILogStringFormatZZZ.sSEPARATOR_PREFIX_DEFAULT + "[C]", "%s" + sPOSITION_METHOD_SEPARATOR,ILogStringFormatZZZ.iARG_OBJECT, ILogStringFormatZZZ.sSEPARATOR_POSTFIX_DEFAULT +  "[/C]", "Gib den Klassennamen mit Package in diesem Format aus."),
		CLASSNAMESIMPLE_STRING_BY_STRING("classnamesimple",ILogStringFormatZZZ.iFACTOR_CLASSNAMESIMPLE_STRING_BY_STRING, ILogStringFormatZZZ.sSEPARATOR_PREFIX_DEFAULT + "[CS]", "%s"  + sPOSITION_METHOD_SEPARATOR, ILogStringFormatZZZ.iARG_OBJECT, "[/CS]" + ILogStringFormatZZZ.sSEPARATOR_POSTFIX_DEFAULT, "Gib den einfachen Klassennamen in diesem Format aus."),				
		CLASSFILENAME_STRING_BY_STRING("classfilename",ILogStringFormatZZZ.iFACTOR_CLASSFILENAME_STRING_BY_STRING, ILogStringFormatZZZ.sSEPARATOR_PREFIX_DEFAULT + "[CF]", "[File:%s]" + sPOSITION_FILE_SEPARATOR, ILogStringFormatZZZ.iARG_OBJECT, "[/CF]" + ILogStringFormatZZZ.sSEPARATOR_POSTFIX_DEFAULT , "Gib den Dateinamen der Java-Klasse in diesem Format aus."),
		
		CLASSMETHOD_STRING_BY_HASHMAP("methodbystring",ILogStringFormatZZZ.iFACTOR_CLASSMETHOD_STRING_BY_HASHMAP, ILogStringFormatZZZ.sSEPARATOR_PREFIX_DEFAULT + "", "%s",ILogStringFormatZZZ.iARG_STRING,"" + ILogStringFormatZZZ.sSEPARATOR_POSTFIX_DEFAULT, "Gib den Methodennamen in diesem  in diesem String Format aus, ermittelt in ReflectCodeZZZ.getPositionCurrent()."),		
		CLASSFILELINE_STRING_BY_HASHMAP("linenrbystring",ILogStringFormatZZZ.iFACTOR_CLASSFILELINE_STRING_BY_HASHMAP, ILogStringFormatZZZ.sSEPARATOR_PREFIX_DEFAULT + "", "%s",ILogStringFormatZZZ.iARG_STRING, "" + ILogStringFormatZZZ.sSEPARATOR_POSTFIX_DEFAULT, "Gib die errechnete Codezeil in der Java-Datei in diesem String Format aus, ermittelt in ReflectCodeZZZ.getPositionCurrent()."),
		CLASSFILENAME_STRING_BY_HASHMAP("filenamebystring",ILogStringFormatZZZ.iFACTOR_CLASSFILENAME_STRING_BY_HASHMAP, ILogStringFormatZZZ.sSEPARATOR_PREFIX_DEFAULT + "", "%s",ILogStringFormatZZZ.iARG_STRING, "" + ILogStringFormatZZZ.sSEPARATOR_POSTFIX_DEFAULT, "Gib die errechnete Java-Datei in diesem String Format aus, ermittelt in ReflectCodeZZZ.getPositionCurrent()."),
		CLASSFILEPOSITION_STRING_BY_HASHMAP("filepositionbystring",ILogStringFormatZZZ.iFACTOR_CLASSFILEPOSITION_STRING_BY_HASHMAP, ILogStringFormatZZZ.sSEPARATOR_PREFIX_DEFAULT + "", "%s",ILogStringFormatZZZ.iARG_STRING, "" + ILogStringFormatZZZ.sSEPARATOR_POSTFIX_DEFAULT, "Gib die errechnete Position in der Java-Datei in diesem String Format aus, ermittelt in ReflectCodeZZZ.getPositionCurrent()."),		
		
		
		CLASSMETHOD_XML_BY_XML("method",ILogStringFormatZZZ.iFACTOR_CLASSMETHOD_XML_BY_XML, ILogStringFormatZZZ.sSEPARATOR_PREFIX_DEFAULT + "", "%s",ILogStringFormatZZZ.iARG_STRINGXML, "" + ILogStringFormatZZZ.sSEPARATOR_POSTFIX_DEFAULT, "Gib den Methodennamen in diesem XML-String in diesem XML-Tag Format aus, ermittelt in ReflectCodeZZZ.getPositionCurrentXml()."),
		CLASSFILELINE_XML_BY_XML("linenr",ILogStringFormatZZZ.iFACTOR_CLASSFILELINE_XML_BY_XML, ILogStringFormatZZZ.sSEPARATOR_PREFIX_DEFAULT + "", "%s",ILogStringFormatZZZ.iARG_STRINGXML, "" + ILogStringFormatZZZ.sSEPARATOR_POSTFIX_DEFAULT, "Gib die errechnete Codezeil in der Java-Datei aus diesem XML-String in diesem XML-Tag Format aus, ermittelt in ReflectCodeZZZ.getPositionCurrentXml()."),
		CLASSFILENAME_XML_BY_XML("filename",ILogStringFormatZZZ.iFACTOR_CLASSFILENAME_XML_BY_XML, ILogStringFormatZZZ.sSEPARATOR_PREFIX_DEFAULT + "", "%s",ILogStringFormatZZZ.iARG_STRINGXML, "" + ILogStringFormatZZZ.sSEPARATOR_POSTFIX_DEFAULT, "Gib die errechnete Java-Datei aus diesem XML-String in diesem XML-Tag Format aus, ermittelt in ReflectCodeZZZ.getPositionCurrentXml()."),
		CLASSFILEPOSITION_XML_BY_XML("fileposition",ILogStringFormatZZZ.iFACTOR_CLASSFILEPOSITION_XML_BY_XML, ILogStringFormatZZZ.sSEPARATOR_PREFIX_DEFAULT + "", "%s",ILogStringFormatZZZ.iARG_STRINGXML, "" + ILogStringFormatZZZ.sSEPARATOR_POSTFIX_DEFAULT, "Gib die errechnete Position in der Java-Datei aus diesem XML-String in diesem XML-Tag Format aus, ermittelt in ReflectCodeZZZ.getPositionCurrentXml()."),		
		POSITIONCURRENT_XML_BY_XML("positioncurrent", ILogStringFormatZZZ.iFACTOR_POSITIONCURRENT_XML_BY_XML, ILogStringFormatZZZ.sSEPARATOR_PREFIX_DEFAULT + "", "%s", ILogStringFormatZZZ.iARG_STRINGXML, "" + ILogStringFormatZZZ.sSEPARATOR_POSTFIX_DEFAULT, "getPositionCurrent - Kann nur von aussen als String uebergeben werden. Wird geholt ueber ObjectZZZ.getPostitionCallingXml(). . Hat kuenstliches Tag <positioncurrent> und entaelt z.B. '<method>searchDirectory</method><fileposition> (FileEasyZZZ.java:625) </fileposition># '."),
		
		DATE_XML_BY_HASHMAP("datebyhm",ILogStringFormatZZZ.iFACTOR_DATE_XML_BY_HASHMAP, ILogStringFormatZZZ.sSEPARATOR_PREFIX_DEFAULT + XmlUtilUnensuredZZZ.computeTagPartOpening(ITagTypeDateZZZ.sTAGNAME), "%s",ILogStringFormatZZZ.iARG_STRINGHASHMAP, XmlUtilUnensuredZZZ.computeTagPartClosing(ITagTypeDateZZZ.sTAGNAME) + ILogStringFormatZZZ.sSEPARATOR_POSTFIX_DEFAULT, "Gib das Datum in diesem Format gespeichert in einer HashMap aus, ermittelt in ReflectCodeZZZ.getPositionCurrentXmlFormated()."),
		THREADID_XML_BY_HASHMAP("threadidbyhm",ILogStringFormatZZZ.iFACTOR_THREADID_XML_BY_HASHMAP, ILogStringFormatZZZ.sSEPARATOR_PREFIX_DEFAULT + XmlUtilUnensuredZZZ.computeTagPartOpening(ITagTypeThreadIdZZZ.sTAGNAME), "%s",ILogStringFormatZZZ.iARG_STRINGHASHMAP, XmlUtilUnensuredZZZ.computeTagPartClosing(ITagTypeThreadIdZZZ.sTAGNAME) + ILogStringFormatZZZ.sSEPARATOR_POSTFIX_DEFAULT, "Gib das ThreadId in diesem Format gespeichert in einer HashMap aus, ermittelt in ReflectCodeZZZ.getPositionCurrentXmlFormated()."),
		CLASSNAME_XML_BY_HASHMAP("classnamebyhm",ILogStringFormatZZZ.iFACTOR_CLASSNAME_XML_BY_HASHMAP, ILogStringFormatZZZ.sSEPARATOR_PREFIX_DEFAULT + XmlUtilUnensuredZZZ.computeTagPartOpening(ITagTypeClassNameZZZ.sTAGNAME), "%s",ILogStringFormatZZZ.iARG_STRINGHASHMAP, XmlUtilUnensuredZZZ.computeTagPartClosing(ITagTypeClassNameZZZ.sTAGNAME) + ILogStringFormatZZZ.sSEPARATOR_POSTFIX_DEFAULT, "Gib den Klassennamen in diesem Format gespeichert in einer HashMap aus, ermittelt in ReflectCodeZZZ.getPositionCurrentXmlFormated()."),		
		CLASSNAMESIMPLE_XML_BY_HASHMAP("classnamesimplebyhm",ILogStringFormatZZZ.iFACTOR_CLASSNAMESIMPLE_XML_BY_HASHMAP, ILogStringFormatZZZ.sSEPARATOR_PREFIX_DEFAULT + XmlUtilUnensuredZZZ.computeTagPartOpening(ITagTypeClassNameZZZ.sTAGNAME), "%s",ILogStringFormatZZZ.iARG_STRINGHASHMAP, XmlUtilUnensuredZZZ.computeTagPartClosing(ITagTypeClassNameZZZ.sTAGNAME) + ILogStringFormatZZZ.sSEPARATOR_POSTFIX_DEFAULT, "Gib den Klassennamen (ohne Package) in diesem Format gespeichert in einer HashMap aus, ermittelt in ReflectCodeZZZ.getPositionCurrentXmlFormated()."),
		CLASSMETHOD_XML_BY_HASHMAP("methodbyhm",ILogStringFormatZZZ.iFACTOR_CLASSMETHOD_XML_BY_HASHMAP, ILogStringFormatZZZ.sSEPARATOR_PREFIX_DEFAULT + XmlUtilUnensuredZZZ.computeTagPartOpening(ITagTypeMethodZZZ.sTAGNAME), "%s",ILogStringFormatZZZ.iARG_STRINGHASHMAP, XmlUtilUnensuredZZZ.computeTagPartClosing(ITagTypeMethodZZZ.sTAGNAME) + ILogStringFormatZZZ.sSEPARATOR_POSTFIX_DEFAULT, "Gib den Methodennamen in diesem Format gespeichert in einer HashMap aus, ermittelt in ReflectCodeZZZ.getPositionCurrentXmlFormated()."),
		CLASSFILELINE_XML_BY_HASHMAP("linenrbyhm",ILogStringFormatZZZ.iFACTOR_CLASSFILELINE_XML_BY_HASHMAP, ILogStringFormatZZZ.sSEPARATOR_PREFIX_DEFAULT + XmlUtilUnensuredZZZ.computeTagPartOpening(ITagTypeLineNumberZZZ.sTAGNAME), "%s",ILogStringFormatZZZ.iARG_STRINGHASHMAP, XmlUtilUnensuredZZZ.computeTagPartClosing(ITagTypeLineNumberZZZ.sTAGNAME) + ILogStringFormatZZZ.sSEPARATOR_POSTFIX_DEFAULT, "Gib die errechnete Codezeil in der Java-Datei in diesem Format gespeichert in einer HashMap aus, ermittelt in ReflectCodeZZZ.getPositionCurrentXmlFormated()."),
		CLASSFILENAME_XML_BY_HASHMAP("filenamebyhm",ILogStringFormatZZZ.iFACTOR_CLASSFILENAME_XML_BY_HASHMAP, ILogStringFormatZZZ.sSEPARATOR_PREFIX_DEFAULT + XmlUtilUnensuredZZZ.computeTagPartOpening(ITagTypeFileNameZZZ.sTAGNAME), "%s",ILogStringFormatZZZ.iARG_STRINGHASHMAP, XmlUtilUnensuredZZZ.computeTagPartClosing(ITagTypeFileNameZZZ.sTAGNAME) + ILogStringFormatZZZ.sSEPARATOR_POSTFIX_DEFAULT, "Gib die errechnete Java-Datei in diesem Format gespeichert in einer HashMap aus, ermittelt in ReflectCodeZZZ.getPositionCurrentXmlFormated()."),
		CLASSFILEPOSITION_XML_BY_HASHMAP("filepositionbyhm",ILogStringFormatZZZ.iFACTOR_CLASSFILEPOSITION_XML_BY_HASHMAP, ILogStringFormatZZZ.sSEPARATOR_PREFIX_DEFAULT + XmlUtilUnensuredZZZ.computeTagPartOpening(ITagTypeFilePositionZZZ.sTAGNAME), "%s",ILogStringFormatZZZ.iARG_STRINGHASHMAP, XmlUtilUnensuredZZZ.computeTagPartClosing(ITagTypeFilePositionZZZ.sTAGNAME) + ILogStringFormatZZZ.sSEPARATOR_POSTFIX_DEFAULT, "Gib die errechnete Position in der Java-Datei in diesem Format gespeichert in einer HashMap aus, ermittelt in ReflectCodeZZZ.getPositionCurrentXmlFormated()."),		
		
		CLASSMETHOD_STRING_BY_XML("methodbyxml",ILogStringFormatZZZ.iFACTOR_CLASSMETHOD_STRING_BY_XML, ILogStringFormatZZZ.sSEPARATOR_PREFIX_DEFAULT + "", "%s",ILogStringFormatZZZ.iARG_STRINGXML, "" + ILogStringFormatZZZ.sSEPARATOR_POSTFIX_DEFAULT, "Gib den Methodennamen in diesem XML-String in diesem STRING Format aus, ermittelt in ReflectCodeZZZ.getPositionCurrentXml()."),
		CLASSFILELINE_STRING_BY_XML("linenrbyxml",ILogStringFormatZZZ.iFACTOR_CLASSFILELINE_STRING_BY_XML, ILogStringFormatZZZ.sSEPARATOR_PREFIX_DEFAULT + "", "%s",ILogStringFormatZZZ.iARG_STRINGXML, "" + ILogStringFormatZZZ.sSEPARATOR_POSTFIX_DEFAULT, "Gib die errechnete Codezeil in der Java-Datei aus diesem XML-String in diesem STRING Format aus, ermittelt in ReflectCodeZZZ.getPositionCurrentXml()."),
		CLASSFILENAME_STRING_BY_XML("filenamebyxml",ILogStringFormatZZZ.iFACTOR_CLASSFILENAME_STRING_BY_XML, ILogStringFormatZZZ.sSEPARATOR_PREFIX_DEFAULT + "", "%s",ILogStringFormatZZZ.iARG_STRINGXML, "" + ILogStringFormatZZZ.sSEPARATOR_POSTFIX_DEFAULT, "Gib die errechnete Java-Datei aus diesem XML-String in diesem STRING Format aus, ermittelt in ReflectCodeZZZ.getPositionCurrentXml()."),
		CLASSFILEPOSITION_STRING_BY_XML("filepositionbyxml",ILogStringFormatZZZ.iFACTOR_CLASSFILEPOSITION_STRING_BY_XML, ILogStringFormatZZZ.sSEPARATOR_PREFIX_DEFAULT + "", "%s",ILogStringFormatZZZ.iARG_STRINGXML, "" + ILogStringFormatZZZ.sSEPARATOR_POSTFIX_DEFAULT, "Gib die errechnete Position in der Java-Datei aus diesem XML-String in diesem STRING Format aus, ermittelt in ReflectCodeZZZ.getPositionCurrentXml()."),		
		POSITIONCURRENT_STRING_BY_XML("positioncurrentbyxml", ILogStringFormatZZZ.iFACTOR_POSITIONCURRENT_STRING_BY_XML, ILogStringFormatZZZ.sSEPARATOR_PREFIX_DEFAULT + "", "%s", ILogStringFormatZZZ.iARG_STRINGXML, "" + ILogStringFormatZZZ.sSEPARATOR_POSTFIX_DEFAULT, "getPositionCurrent - Kann nur von aussen als String uebergeben werden. Wird geholt ueber ObjectZZZ.getPostitionCallingXml(). Hat kuenstliches Tag <positioncurrent> und entaelt z.B. '<method>searchDirectory</method><fileposition> (FileEasyZZZ.java:625) </fileposition># '. Als Rueckgabe-String werden diese Tags entfernt."),
		
		THREADID_STRING_BY_STRING("threadid",ILogStringFormatZZZ.iFACTOR_THREADID_STRING_BY_STRING, ILogStringFormatZZZ.sSEPARATOR_PREFIX_DEFAULT + "[T]", "[Thread: %s]",ILogStringFormatZZZ.iARG_SYSTEM, "[/T]" + ILogStringFormatZZZ.sSEPARATOR_POSTFIX_DEFAULT, "Gib die ID des Threads in diesem Format aus."),
		DATE_STRING_BY_STRING("date",ILogStringFormatZZZ.iFACTOR_DATE_STRING_BY_STRING,ILogStringFormatZZZ.sSEPARATOR_PREFIX_DEFAULT + "[D]", "[%s]",ILogStringFormatZZZ.iARG_SYSTEM, "[/D]" + ILogStringFormatZZZ.sSEPARATOR_POSTFIX_DEFAULT, "Gib das errechnete Datum in diesem Format aus.")
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
