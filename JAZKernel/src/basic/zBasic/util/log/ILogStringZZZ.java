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

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IDummyTestObjectWithStatusByInterfaceExtendedZZZ.STATUSLOCAL;
import basic.zBasic.util.abstractEnum.IEnumSetMappedStatusZZZ;
import basic.zBasic.util.datatype.string.StringJustifierZZZ;

public interface ILogStringZZZ{
	public static String sENUMNAME="LOGSTRING";
	public static String sSEPARATOR_PREFIX_DEFAULT="";
	public static String sSEPARATOR_POSTFIX_DEFAULT="";
	
	//Merke: Es soll folgendes abgebildet werden, z.B. 
	//       String sLog = ReflectCodeZZZ.getPositionCurrent() + "[Thread: "+lngThreadID + "] Status='"+enumStatus.getName() +"', StatusValue="+bStatusValue+", StatusMessage='" + sStatusMessage +"'";
	
	//Das zu wird das Format als Anweisung in ein Array abgelegt, in dem dann die einzelnen Teilkomponenten ausgerechnet werden.
	
	//Merke: TODO Idee ist es das Custom-Format in einer einzigen Zahl zu definieren.
	//Merke: Jedes dieser Argumente ist als Primzahl zu defnieren.
	//       Es gilt Positionswert = Position * Primzahl
	public static int iARG_NOTHING = 0;
	public static int iARG_STRING = 1;
	public static int iARG_OBJECT = 2;
	
	
	
	//Argumente der compute Methode (sofern vorhanden und != null)
	//TODO IDEE: Wenn man eine Zahl angibt, soll die Zusammenstellung des Formats definiert sein.
	//           Darum sind das alles Primzahlen...	
	public static int iFACTOR_ARGNEXT01=1;
	public static int iFACTOR_ARGNEXT02=2;     //3 verschiedene Varianten fuer Argnext
	public static int iFACTOR_ARGNEXT03=3;
	public static int iFACTOR_ARGNEXT05=5;
	public static int iFACTOR_CLASSNAME=7;
	public static int iFACTOR_CLASSNAMESIMPLE=11;
	public static int iFACTOR_CLASSMETHOD=13;
	public static int iFACTOR_CLASSFILENAME=17;
	public static int iFACTOR_CLASSPOSITION=19;       //mit der Zeilenummer dahinter
	public static int iFACTOR_CLASSFILEPOSITION=23;  //mit der Zeilenummer dahinter
	public static int iFACTOR_THREADID=29;
	public static int iFACTOR_DATE=31;
	

	//Merke:
	//Statt mehrere Strukturen zu pflegen, lieber eine enum, mit den Konfigurationswerten.
	//Das hat auch den Vorteil, dass man nicht mehr mit abstrakten int - Zahlen bei der Definition der Struktur des Logformat-Arrays arbeiten muss
	//++++++++++++++++++++++++
	
	//TODOGOON20240503: Eine Sortierung des Arrays, welche als Formatierungsanweisung uebergeben werden ist wünschenswert.
	//                  Zuerst die ohne Argument, dann die mit Objekt als Argument, dann erst die Stringbasierten Argumente.
	//                  Diese NACHTRAEGLICHE Sortierung kann man zumindest bei dem Default-Format extra durchfuehren und so erzwingen. 
	
	//Merke: Methoden und Zeilennummern können nicht aus dem aktuellen Objekt ermittelt werden, daher sind sie von einem uebergebenen String abhaengig.
	
	//ALIAS("Uniquename",Faktor, "Format... Merke %s für den String wert muss für String.format() sein",Kennzeichen des Argumenttyps,"PostfixSeparatorString", "Beschreibung, wird nicht genutzt....")
	public enum LOGSTRING implements IEnumSetMappedLogStringFormatZZZ{//Folgendes geht nicht, da alle Enums schon von einer Java BasisKlasse erben... extends EnumSetMappedBaseZZZ{		
		ARGNEXT01("argnext01",ILogStringZZZ.iFACTOR_ARGNEXT01, "[A01]" + ILogStringZZZ.sSEPARATOR_PREFIX_DEFAULT + sPOSITION_MESSAGE_SEPARATOR, "%s",ILogStringZZZ.iARG_STRING, ILogStringZZZ.sSEPARATOR_POSTFIX_DEFAULT + "[/A01]", "Gib den naechsten Log String - sofern vorhanden - in diesem Format aus."),
		ARGNEXT02("argnext02",ILogStringZZZ.iFACTOR_ARGNEXT02, "[A02]" + ILogStringZZZ.sSEPARATOR_PREFIX_DEFAULT, "%s",ILogStringZZZ.iARG_STRING, ILogStringZZZ.sSEPARATOR_POSTFIX_DEFAULT + "[/A02]", "Gib den naechsten Log String - sofern vorhanden - in diesem Format aus."),
		ARGNEXT03("argnext03",ILogStringZZZ.iFACTOR_ARGNEXT03, "[A03]" + ILogStringZZZ.sSEPARATOR_PREFIX_DEFAULT, "%s",ILogStringZZZ.iARG_STRING, sPOSITION_MESSAGE_SEPARATOR + ILogStringZZZ.sSEPARATOR_POSTFIX_DEFAULT + "[/A03]" , "Gib den naechsten Log String - sofern vorhanden - in diesem Format aus."),
		ARGNEXT05("argnext05",ILogStringZZZ.iFACTOR_ARGNEXT05, ILogStringZZZ.sSEPARATOR_PREFIX_DEFAULT + "[A05/]", "%s",ILogStringZZZ.iARG_STRING, ILogStringZZZ.sSEPARATOR_POSTFIX_DEFAULT + "", "Scheibe zum naechsten ARGUMENT String, ohne Ausgabe des aktuellen LogString. Damit kann gesteuert werden, dass auch ohne ARGNEXT weitergegangen wird. Z.B. wenn die Filepostion (ist immer String, aber nicht argnext) vorher uebergeben worden ist."),
		
		CLASSNAME("classname",ILogStringZZZ.iFACTOR_CLASSNAME, ILogStringZZZ.sSEPARATOR_PREFIX_DEFAULT + "[C]", "%s:",ILogStringZZZ.iARG_OBJECT, ILogStringZZZ.sSEPARATOR_POSTFIX_DEFAULT +  "[/C]", "Gib den Klassennamen mit Package in diesem Format aus."),
		CLASSNAMESIMPLE("classnamesimple",ILogStringZZZ.iFACTOR_CLASSNAMESIMPLE, ILogStringZZZ.sSEPARATOR_PREFIX_DEFAULT + "[CS]", "%s:",ILogStringZZZ.iARG_OBJECT, ":" + ILogStringZZZ.sSEPARATOR_POSTFIX_DEFAULT + "[/CS]", "Gib den einfachen Klassennamen in diesem Format aus."),
		CLASSMETHOD("classmethod",ILogStringZZZ.iFACTOR_CLASSMETHOD, ILogStringZZZ.sSEPARATOR_PREFIX_DEFAULT + "[CM]", "%s",ILogStringZZZ.iARG_STRING, sPOSITION_METHOD_SEPARATOR + ILogStringZZZ.sSEPARATOR_POSTFIX_DEFAULT + "[/CM]", "Gib den Methodennamen in diesem Format aus."),
		CLASSPOSITION("classposition",ILogStringZZZ.iFACTOR_CLASSPOSITION, ILogStringZZZ.sSEPARATOR_PREFIX_DEFAULT + "[CP]", "[Class: %s]",ILogStringZZZ.iARG_STRING, ILogStringZZZ.sSEPARATOR_POSTFIX_DEFAULT + "[/CP]", "Gib die errechnete Position in der Java-Klasse in diesem Format aus."),
		
		CLASSFILENAME("classfilename",ILogStringZZZ.iFACTOR_CLASSFILENAME, ILogStringZZZ.sSEPARATOR_PREFIX_DEFAULT + "[CF]", "[File:%s]",ILogStringZZZ.iARG_OBJECT, sPOSITION_FILE_SEPARATOR + ILogStringZZZ.sSEPARATOR_POSTFIX_DEFAULT + "[/CF]", "Gib den Dateinamen der Java-Klasse in diesem Format aus."),
		CLASSFILEPOSITION("classfileposition",ILogStringZZZ.iFACTOR_CLASSFILEPOSITION, ILogStringZZZ.sSEPARATOR_PREFIX_DEFAULT + "[CFP]", "[File:%s]",ILogStringZZZ.iARG_STRING, sPOSITION_FILE_SEPARATOR + ILogStringZZZ.sSEPARATOR_POSTFIX_DEFAULT + "[/CFP]", "Gib die errechnete Position in der Java-Datei in diesem Format aus."),
		
		THREADID("threadid",ILogStringZZZ.iFACTOR_THREADID, ILogStringZZZ.sSEPARATOR_PREFIX_DEFAULT + "[T]", "[Thread: %s]",ILogStringZZZ.iARG_NOTHING, "", "Gib die ID des Threads in diesem Format aus."),
		DATE("date",ILogStringZZZ.iFACTOR_DATE,ILogStringZZZ.sSEPARATOR_PREFIX_DEFAULT + "[D]", "[%s]",ILogStringZZZ.iARG_NOTHING, ILogStringZZZ.sSEPARATOR_POSTFIX_DEFAULT + "[/D]", "Gib das errechnete Datum in diesem Format aus.")
		;		
		
		int iFactor, iArgumentType;
		private String sAbbreviation,sPrefixSeparator, sFormat,sPostfixSeparator, sDescription;
	
		//#############################################
		//#### Konstruktoren
		//Merke: Enums haben keinen public Konstruktor, können also nicht intiantiiert werden, z.B. durch Java-Reflektion.
		//In der Util-Klasse habe ich aber einen Workaround gefunden.
		LOGSTRING(String sAbbreviation, int iFactor, String sPrefixSeparator, String sFormat, int iArgumentType, String sPostfixSeparator, String sDescription) {
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
			return LOGSTRING.getEnumSet();
		}
	
		/* Die in dieser Methode verwendete Klasse für den ...TypeZZZ muss immer angepasst werden. */
		@SuppressWarnings("rawtypes")
		public static <E> EnumSet getEnumSet() {
			
		 //Merke: Das wird anders behandelt als FLAGZ Enumeration.
			//String sFilterName = "FLAGZ"; /
			//...
			//ArrayList<Class<?>> listEmbedded = ReflectClassZZZ.getEmbeddedClasses(this.getClass(), sFilterName);
			
			//Erstelle nun ein EnumSet, speziell für diese Klasse, basierend auf  allen Enumrations  dieser Klasse.
			Class<LOGSTRING> enumClass = LOGSTRING.class;
			EnumSet<LOGSTRING> set = EnumSet.noneOf(enumClass);//Erstelle ein leeres EnumSet
					
			Enum[]objaEnum = (Enum[]) enumClass.getEnumConstants();
			for(Object obj : objaEnum){
				//System.out.println(obj + "; "+obj.getClass().getName());
				set.add((LOGSTRING) obj);
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
		public static LOGSTRING fromAbbreviation(String s) {
		for (LOGSTRING state : values()) {
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

	
	//Ein wenig Reflexion ueber das Format und den auszugebenend String
	//wird als static Methode angeboten public boolean isFormatUsingLogString(int iFormat);
	//wird als static Methode angeboten public boolean isFormatUsingObject(int iFormat);
	
	
	//ohne ein intern gespeichertes Format oder einen LogString zu verwenden.
	public String compute(IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ;
	public String compute(Object obj, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ;
	
	//ohne ein intern gespeichertes Format zu verwenden
	public String compute(String sMessage, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ;
	public String compute(String sMessage, IEnumSetMappedLogStringFormatZZZ[] ienumaFormatLogString) throws ExceptionZZZ;
	public String compute(Object obj, String sMessage, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ;
	public String compute(Object obj, String sMessage, IEnumSetMappedLogStringFormatZZZ[] ienumaFormatLogString) throws ExceptionZZZ;
	public String compute(Object obj, String[] saLog, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ;
	public String compute(Object obj, String[] saLog, IEnumSetMappedLogStringFormatZZZ[] ienumFormatLogString) throws ExceptionZZZ;

	public String compute(Object obj, String sMessage01, String sMessage02, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ; //Zwei Log String sind normal log01 wäre z.B. ReflectCodeZZZ.getPositionCurrent();
	public String compute(Object obj, String sMessage01, String sMessage02, IEnumSetMappedLogStringFormatZZZ[] ienumaFormatLogString) throws ExceptionZZZ; //Zwei Log String sind normal log01 wäre z.B. ReflectCodeZZZ.getPositionCurrent()
		
	
	//das ganze auch mit dem intern gespeicherten Format
	public String compute(String sMessage) throws ExceptionZZZ;
	public String compute(String sMessage01, String sMessage02) throws ExceptionZZZ;
	public String compute(String[] saMessage) throws ExceptionZZZ;
	
	//... mit Objekt
	public String compute(Object obj, String sMessage) throws ExceptionZZZ;
	public String compute(Object obj, String[] saMessage) throws ExceptionZZZ; //ACHTUNG: Hier werden beide Strings in einer Zeile zusammengefasst. Der 2. sollte dann mit dem zweiten argnext formatiert sein
		
	public String compute(Object obj, String sMessage01, String sMessage02) throws ExceptionZZZ; //ACHTUNG: Hier werden beide Strings in einer Zeile zusammengefasst. Der 2. sollte dann mit dem zweiten argnext formatiert sein
	
	public int computeFormatPositionsNumber() throws ExceptionZZZ;
	
	
	/* Beispiel für solch ein Array mit den Angaben wo was stehen soll ist:
	            int[] iaFormat = {
				ILogStringZZZ.iTHREAD,
				ILogStringZZZ.iARGNEXT,
				ILogStringZZZ.iCLASSNAME,
				ILogStringZZZ.iARGNEXT,
			};
			
			Also zuerst die Threadangabe, danach der Wert des 1. Arguments der compute-Methode, dann der Klassenname, danach der Wert des 2. Arguments der compute-Methode
	 */
	public void setFormatPositionsMapped(IEnumSetMappedLogStringFormatZZZ[]ienumaMappedFormat);
	public IEnumSetMappedLogStringFormatZZZ[]getFormatPositionsMapped() throws ExceptionZZZ;
	public IEnumSetMappedLogStringFormatZZZ[]getFormatPositionsMappedDefault() throws ExceptionZZZ;
	public IEnumSetMappedLogStringFormatZZZ[]getFormatPositionsMappedCustom() throws ExceptionZZZ;
	
	public void setHashMapFormatPositionString(HashMap<Integer,String>hmFormatPostionString);
	public HashMap<Integer,String>getHashMapFormatPositionString() throws ExceptionZZZ;
	public HashMap<Integer,String>getHashMapFormatPositionStringCustom() throws ExceptionZZZ;
	public HashMap<Integer,String>getHashMapFormatPositionStringDefault() throws ExceptionZZZ;
	
	//###########################
	//Methoden, mit denen versucht wird die Uebersichtlichkeit der Ausgaben noch weiter zu erhöhen.
	//Nach jeder Logausgabe wird zwischen dem Positionsteil und dem Informationsteil unterscheiden.
	//Mit Leerzeichen wird dann gearbeitet um die Ausgaben des Informationsteils möglichst buendig untereinander zu bekommen.
    public StringJustifierZZZ getStringJustifier() throws ExceptionZZZ;
    public void setStringJustifier(StringJustifierZZZ objStringJustifier);
	
	
	//#############################################################
	//### FLAGZ
	//#############################################################
	public enum FLAGZ{
		DUMMY,EXCLUDE_THREAD, EXCLUDE_CLASSNAME
	}
		
	boolean getFlag(FLAGZ objEnumFlag);
	boolean setFlag(FLAGZ objEnumFlag, boolean bFlagValue) throws ExceptionZZZ;
	boolean[] setFlag(FLAGZ[] objaEnumFlag, boolean bFlagValue) throws ExceptionZZZ;
	boolean proofFlagExists(FLAGZ objEnumFlag) throws ExceptionZZZ;
	boolean proofFlagSetBefore(FLAGZ objEnumFlag) throws ExceptionZZZ;
	
	
	//#######################################################################################
	// STATUS	
    //............ hier erst einmal nicht .....................
}
