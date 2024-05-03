package basic.zBasic.util.log;

import java.util.EnumSet;
import java.util.HashMap;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IDummyTestObjectWithStatusByInterfaceExtendedZZZ.STATUSLOCAL;
import basic.zBasic.util.abstractEnum.IEnumSetMappedStatusZZZ;

public interface ILogStringZZZ{
	public static String sENUMNAME="LOGSTRING";
	
	
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
	public static int iFACTOR_ARGNEXT02=2;     //2 verschiedene Varianten fuer Argnext 
	public static int iFACTOR_CLASSNAME=3;
	public static int iFACTOR_CLASSSIMPLENAME=5;
	public static int iFACTOR_CLASSMETHOD=7;
	public static int iFACTOR_CLASSFILENAME=11;
	public static int iFACTOR_CLASSPOSITION=13;       //mit der Zeilenummer dahinter
	public static int iFACTOR_CLASSFILEPOSITION=17;  //mit der Zeilenummer dahinter
	public static int iFACTOR_THREADID=19;
	public static int iFACTOR_DATE=23; 

	//Merke:
	//Statt mehrere Strukturen zu pflegen, lieber eine enum, mit den Konfigurationswerten.
	//Das hat auch den Vorteil, dass man nicht mehr mit abstrakten int - Zahlen bei der Definition der Struktur des Logformat-Arrays arbeiten muss
	//++++++++++++++++++++++++
	
	//TODOGOON20240503: Eine Sortierung des Arrays, welche als Formatierungsanweisung uebergeben werden ist wünschenswert.
	//                  Zuerst die ohne Argument, dann die mit Objekt als Argument, dann erst die Stringbasierten Argumente.
	//                  Diese NACHTRAEGLICHE Sortierung kann man zumindest bei dem Default-Format extra durchfuehren und so erzwingen. 
	
	//Merke: Methoden und Zeilennummern können nicht aus dem aktuellen Objekt ermittelt werden, daher sind sie von einem uebergebenen String abhaengig.
	
	//ALIAS("Uniquename",Faktor, "Format... Merke %s für den String wert muss für String.format() sein",Kennzeichen des Argumenttyps,"PostfixSeparatorString", "Beschreibung, wird nicht genutzt....")
	public enum LOGSTRING implements IEnumSetMappedLogStringZZZ{//Folgendes geht nicht, da alle Enums schon von einer Java BasisKlasse erben... extends EnumSetMappedBaseZZZ{
		ARGNEXT01("argnext01",ILogStringZZZ.iFACTOR_ARGNEXT01, "", "%s",ILogStringZZZ.iARG_STRING, " ", "Gib den naechsten Log String - sofern vorhanden - in diesem Format aus."),
		ARGNEXT02("argnext01",ILogStringZZZ.iFACTOR_ARGNEXT02, "", "%s",ILogStringZZZ.iARG_STRING, " ", "Gib den naechsten Log String - sofern vorhanden - in diesem Format aus."),
		
		CLASSNAME("classname",ILogStringZZZ.iFACTOR_CLASSNAME, "", "%s:",ILogStringZZZ.iARG_OBJECT, "", "Gib den Klassennamen mit Package in diesem Format aus."),
		CLASSSIMPLENAME("classsimplename",ILogStringZZZ.iFACTOR_CLASSSIMPLENAME, "", "%s:",ILogStringZZZ.iARG_OBJECT, "", "Gib den einfachen Klassennamen in diesem Format aus."),
		CLASSMETHOD("classmethod",ILogStringZZZ.iFACTOR_CLASSMETHOD, "", "[Method: %s]",ILogStringZZZ.iARG_STRING, "", "Gib den Methodennamen in diesem Format aus."),
		CLASSPOSITION("classposition",ILogStringZZZ.iFACTOR_CLASSPOSITION, "", "[Class: %s]",ILogStringZZZ.iARG_STRING, "", "Gib die errechnete Position in der Java-Klasse in diesem Format aus."),
		
		CLASSFILENAME("classfilename",ILogStringZZZ.iFACTOR_CLASSFILENAME, "", "[File: %s]",ILogStringZZZ.iARG_OBJECT, "", "Gib den Dateinamen der Java-Klasse in diesem Format aus."),
		CLASSFILEPOSITION("classfileposition",ILogStringZZZ.iFACTOR_CLASSFILEPOSITION, "", "[File: %s]",ILogStringZZZ.iARG_STRING, "", "Gib die errechnete Position in der Java-Datei in diesem Format aus."),
		
		THREADID("threadid",ILogStringZZZ.iFACTOR_THREADID, "", "[Thread: %s]",ILogStringZZZ.iARG_NOTHING, "", "Gib die ID des Threads in diesem Format aus."),
		DATE("date",ILogStringZZZ.iFACTOR_DATE,"", "[%s]",ILogStringZZZ.iARG_NOTHING, "", "Gib das errechnete Datum in diesem Format aus.")
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
	public String compute(IEnumSetMappedLogStringZZZ ienumFormatLogString) throws ExceptionZZZ;
	public String compute(Object obj, IEnumSetMappedLogStringZZZ ienumFormatLogString) throws ExceptionZZZ;
	
	//ohne ein intern gespeichertes Format zu verwenden
	public String compute(String sLog, IEnumSetMappedLogStringZZZ ienumFormatLogString) throws ExceptionZZZ;
	public String compute(String sLog, IEnumSetMappedLogStringZZZ[] ienumaFormatLogString) throws ExceptionZZZ;
	public String compute(Object obj, String sLog, IEnumSetMappedLogStringZZZ ienumFormatLogString) throws ExceptionZZZ;
	public String compute(Object obj, String sLog, IEnumSetMappedLogStringZZZ[] ienumaFormatLogString) throws ExceptionZZZ;
	public String compute(Object obj, String[] saLog, IEnumSetMappedLogStringZZZ ienumFormatLogString) throws ExceptionZZZ;
	public String compute(Object obj, String[] saLog, IEnumSetMappedLogStringZZZ[] ienumFormatLogString) throws ExceptionZZZ;

	public String compute(Object obj, String sLog01, String sLog02, IEnumSetMappedLogStringZZZ ienumFormatLogString) throws ExceptionZZZ; //Zwei Log String sind normal log01 wäre z.B. ReflectCodeZZZ.getPositionCurrent();
	public String compute(Object obj, String sLog01, String sLog02, IEnumSetMappedLogStringZZZ[] ienumaFormatLogString) throws ExceptionZZZ; //Zwei Log String sind normal log01 wäre z.B. ReflectCodeZZZ.getPositionCurrent()
		
	//das ganze auch mit dem intern gespeicherten Format
	public String compute(String sLog) throws ExceptionZZZ;
	public String compute(Object obj, String sLog) throws ExceptionZZZ;
	public String compute(Object obj, String[] saLog) throws ExceptionZZZ;
	
	public String compute(Object obj, String sLog01, String sLog02) throws ExceptionZZZ; //Zwei Log String sind normal log01 wäre z.B. ReflectCodeZZZ.getPositionCurrent();
	
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
	public void setFormatPositionsMapped(IEnumSetMappedLogStringZZZ[]ienumaMappedFormat);
	public IEnumSetMappedLogStringZZZ[]getFormatPositionsMapped() throws ExceptionZZZ;
	public IEnumSetMappedLogStringZZZ[]getFormatPositionsMappedDefault() throws ExceptionZZZ;
	public IEnumSetMappedLogStringZZZ[]getFormatPositionsMappedCustom() throws ExceptionZZZ;
	
	public void setHashMapFormatPositionString(HashMap<Integer,String>hmFormatPostionString);
	public HashMap<Integer,String>getHashMapFormatPositionString() throws ExceptionZZZ;
	public HashMap<Integer,String>getHashMapFormatPositionStringCustom() throws ExceptionZZZ;
	public HashMap<Integer,String>getHashMapFormatPositionStringDefault() throws ExceptionZZZ;
	
	
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
