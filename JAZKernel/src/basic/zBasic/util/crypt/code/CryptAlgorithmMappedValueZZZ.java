package basic.zBasic.util.crypt.code;

import java.io.Serializable;
import java.util.EnumSet;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractEnum.EnumSetMappedTestTypeZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedMaintypeZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetZZZ;
import basic.zBasic.util.persistence.jdbc.JdbcDriverMappedValueZZZ.JdbcDriverClassTypeZZZ;

//#####################################################
//20220927: Um die Enumeration herum eine Klasse bauen.
//          Diese Struktur hat den Vorteil, das solche Werte auch in einer Datenbank per Hibernate persistiert werden können.
//          Verwendet wird solch eine Struktur z.B. in der Defaulttext - Klasse des TileHexMapTHM Projekts
public class CryptAlgorithmMappedValueZZZ  implements Serializable{
	private static final long serialVersionUID = 1340342888046470974L;
	
		//Entsprechend der internen Enumeration
		//Merke: Die Enumeration dient der Festlegung der Defaultwerte. In den Feldern des Entities werden die gespeicherten Werte gehalten.
		private String fullName, abbr;
		
		public CryptAlgorithmMappedValueZZZ(){		
		}
		
		public String getFullname(){
			return this.fullName;
		}
		public void setFullname(String sFullname){
			this.fullName = sFullname;
		}
		
		public String getAbbreviation(){
			return this.abbr;
		}
		public void setAbbreviation(String sAbbr){
			this.abbr = sAbbr;
		}

	//### Statische Methode (um einfacher darauf zugreifen zu können)
    public static Class getEnumClassStatic(){
    	try{
    		System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": Diese Methode muss in den daraus erbenden Klassen überschrieben werden.");
    	}catch(ExceptionZZZ ez){
			String sError = "ExceptionZZZ: " + ez.getMessageLast() + "+\n ThreadID:" + Thread.currentThread().getId() +"\n";			
			System.out.println(sError);
		}
    	return CipherTypeZZZ.class;    	
    }
	
//#######################################################
//### Eingebettete Enum-Klasse mit den Defaultwerten, diese Werte werden auch per Konstruktor übergeben.
//### String fullName, String abbreviation
//#######################################################
			
//Merke: Obwohl fullName und abbr nicht direkt abgefragt werden, müssen Sie im Konstruktor sein, um die Enumeration so zu definieren.
//ALIAS("Beschreibung, wird nicht genutzt....","Abkürzung, also das, was im URL String steht. Meist gefolgt von einem  Doppelpunkt, der hinzugerchnet wird, wenn die Abkürzung nicht leer ist.")
public enum CipherTypeZZZ implements IEnumSetMappedMaintypeZZZ, IEnumSetZZZ {//Folgendes geht nicht, da alle Enums schon von einer Java BasisKlasse erben... extends EnumSetMappedBaseZZZ{
	//FGL: EnumSet in EnumSet zu verschachteln ist zu kompliziert
//	ROT13("ROT13","simple encrytption method for the characters a-z, using a fix number to rotate.",CryptCipherAlgorithmSubtypeZZZ.CryptCipherSubtypeZZZ.ROT),
//	ROTascii("ROTascii","simple encrytption method for the ASCII characters.",CryptCipherAlgorithmSubtypeZZZ.CryptCipherSubtypeZZZ.ROT),
//	ROTnumeric("ROTnumeric","simple encrytption methodefor the characters a-z PLUS 0-9, using a parameter for the number to rotate.",CryptCipherAlgorithmSubtypeZZZ.CryptCipherSubtypeZZZ.ROT),
//	ROTnn("ROTnn","simple encrytption method for the characters provided by a character list, using a parameter for the number to rotate.",CryptCipherAlgorithmSubtypeZZZ.CryptCipherSubtypeZZZ.ROT),
//	VIGENERE("Vigenere","simple encryption method for the characters, using a keyword to rotate", CryptCipherAlgorithmSubtypeZZZ.CryptCipherSubtypeZZZ.VIGENERE);
	
	
	ROT13("ROT13","simple encrytption method for the characters a-z, using a fix number to rotate.",CryptAlgorithmMaintypeZZZ.TypeZZZ.ROT.ordinal()),
	ROTascii("ROTascii","simple encrytption method for the ASCII characters.",CryptAlgorithmMaintypeZZZ.TypeZZZ.ROT.ordinal()),
	ROTnumeric("ROTnumeric","simple encrytption methodefor the characters a-z PLUS 0-9, using a parameter for the number to rotate.",CryptAlgorithmMaintypeZZZ.TypeZZZ.ROT.ordinal()),
	ROTnn("ROTnn","simple encrytption method for the characters provided by a character pool, using a parameter for the number to rotate.",CryptAlgorithmMaintypeZZZ.TypeZZZ.ROT.ordinal()),
	VIGENERE26("Vigenere256","simple encryption method for the characters, using a keyword to rotate (96 Characters)", CryptAlgorithmMaintypeZZZ.TypeZZZ.VIGENERE.ordinal()),
	VIGENERE96("Vigenere256","simple encryption method for the characters, using a keyword to rotate (96 Characters)", CryptAlgorithmMaintypeZZZ.TypeZZZ.VIGENERE.ordinal()),
	VIGENERE256("Vigenere256","simple encryption method for the characters, using a keyword to rotate (256 Characters)", CryptAlgorithmMaintypeZZZ.TypeZZZ.VIGENERE.ordinal()),
	VIGENEREnn("VigenereNn","simple encryption method for the charcters provided by a character pool, using a keyword to rotate (nn number of characters in pool)", CryptAlgorithmMaintypeZZZ.TypeZZZ.VIGENERE.ordinal());
	
	
private String sAbbr, sDescr;
private int iMaintype;

//FGL: EnumSet in EnumSet zu verschachteln ist zu kompliziert
//private EnumSet enumSubtype;

//#############################################
//#### Konstruktoren
//Merke: Enums haben keinen public Konstruktor, können also nicht intiantiiert werden, z.B. durch Java-Reflektion.
//In der Util-Klasse habe ich aber einen Workaround gefunden.
CipherTypeZZZ(String sAbbr, String sDescr,int iMaintype) {
    this.sAbbr = sAbbr;
    this.sDescr = sDescr;
    this.iMaintype = iMaintype;
}

//FGL: EnumSet in EnumSet zu verschachteln ist zu kompliziert
//CryptCipherTypeZZZ(String sAbbr, String sDescr,EnumSet enumSubtype) {
//    this.sAbbr = sAbbr;
//    this.sDescr = sDescr;
//    this.enumSubtype = enumSubtype;
//}



//the identifierMethod ---> Going in DB
public String getAbbreviation() {
 return this.sAbbr;
}

public EnumSet<?>getEnumSetUsed(){
	return CipherTypeZZZ.getEnumSet();
}

/* Die in dieser Methode verwendete Klasse für den ...TypeZZZ muss immer angepasst werden. */
@SuppressWarnings("rawtypes")
public static <E> EnumSet getEnumSet() {
	
 //Merke: Das wird anders behandelt als FLAGZ Enumeration.
	//String sFilterName = "FLAGZ"; /
	//...
	//ArrayList<Class<?>> listEmbedded = ReflectClassZZZ.getEmbeddedClasses(this.getClass(), sFilterName);
	
	//Erstelle nun ein EnumSet, speziell für diese Klasse, basierend auf  allen Enumrations  dieser Klasse.
	Class<CipherTypeZZZ> enumClass = CipherTypeZZZ.class;
	EnumSet<CipherTypeZZZ> set = EnumSet.noneOf(enumClass);//Erstelle ein leeres EnumSet
	
	for(Object obj : CipherTypeZZZ.class.getEnumConstants()){
		//System.out.println(obj + "; "+obj.getClass().getName());
		set.add((CipherTypeZZZ) obj);
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
public static CipherTypeZZZ fromAbbreviation(String s) {
for (CipherTypeZZZ state : values()) {
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
public String toString() {
    return this.sAbbr+"#"+this.sDescr;
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
	return this.sDescr;
}
//+++++++++++++++++++++++++

@Override
public int getMaintype() {
	return this.iMaintype;
}
}//End internal Class	
}//End Class
