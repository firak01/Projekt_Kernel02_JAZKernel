package basic.zBasic.util.crypt;

import java.io.Serializable;
import java.util.EnumSet;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractEnum.EnumSetMappedTestTypeZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetZZZ;
import basic.zBasic.util.persistence.jdbc.JdbcDriverMappedValueZZZ.JdbcDriverClassTypeZZZ;

//#####################################################
//20220927: Um die Enumeration herum eine Klasse bauen.
//          Diese Struktur hat den Vorteil, das solche Werte auch in einer Datenbank per Hibernate persistiert werden können.
//          Verwendet wird solch eine Struktur z.B. in der Defaulttext - Klasse des TileHexMapTHM Projekts
public class CryptCipherAlgorithmMappedValueZZZ  implements IEnumSetZZZ, Serializable{
	private static final long serialVersionUID = 1340342888046470974L;
	
		//Entsprechend der internen Enumeration
		//Merke: Die Enumeration dient der Festlegung der Defaultwerte. In den Feldern des Entities werden die gespeicherten Werte gehalten.
		private String fullName, abbr;
		
		public CryptCipherAlgorithmMappedValueZZZ(){		
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
    	return CryptCipherTypeZZZ.class;    	
    }
	
//#######################################################
//### Eingebettete Enum-Klasse mit den Defaultwerten, diese Werte werden auch per Konstruktor übergeben.
//### String fullName, String abbreviation
//#######################################################
			
//Merke: Obwohl fullName und abbr nicht direkt abgefragt werden, müssen Sie im Konstruktor sein, um die Enumeration so zu definieren.
//ALIAS("Beschreibung, wird nicht genutzt....","Abkürzung, also das, was im URL String steht. Meist gefolgt von einem  Doppelpunkt, der hinzugerchnet wird, wenn die Abkürzung nicht leer ist.")
public enum CryptCipherTypeZZZ implements IEnumSetMappedZZZ{//Folgendes geht nicht, da alle Enums schon von einer Java BasisKlasse erben... extends EnumSetMappedBaseZZZ{
	ROT13("ROT13","simple encrytption methode for the characters a-z, using a fix number to rotate."),
	ROTnumeric("ROTnumeric","simple encrytption methode for the characters a-z PLUS 0-9, using a parameter for the number to rotate."),
	ROTnn("ROTnn","simple encrytption methode for the characters provided by a character list, using a parameter for the number to rotate.");
	
private String fullName, abbr;

//#############################################
//#### Konstruktoren
//Merke: Enums haben keinen public Konstruktor, können also nicht intiantiiert werden, z.B. durch Java-Reflektion.
//In der Util-Klasse habe ich aber einen Workaround gefunden.
CryptCipherTypeZZZ(String fullName, String abbr) {
    this.fullName = fullName;
    this.abbr = abbr;
}


//the identifierMethod ---> Going in DB
public String getAbbreviation() {
 return this.abbr;
}

public String getFullname(){
	return this.fullName;
}



public EnumSet<?>getEnumSetUsed(){
	return CryptCipherTypeZZZ.getEnumSet();
}

/* Die in dieser Methode verwendete Klasse für den ...TypeZZZ muss immer angepasst werden. */
@SuppressWarnings("rawtypes")
public static <E> EnumSet getEnumSet() {
	
 //Merke: Das wird anders behandelt als FLAGZ Enumeration.
	//String sFilterName = "FLAGZ"; /
	//...
	//ArrayList<Class<?>> listEmbedded = ReflectClassZZZ.getEmbeddedClasses(this.getClass(), sFilterName);
	
	//Erstelle nun ein EnumSet, speziell für diese Klasse, basierend auf  allen Enumrations  dieser Klasse.
	Class<CryptCipherTypeZZZ> enumClass = CryptCipherTypeZZZ.class;
	EnumSet<CryptCipherTypeZZZ> set = EnumSet.noneOf(enumClass);//Erstelle ein leeres EnumSet
	
	for(Object obj : CryptCipherTypeZZZ.class.getEnumConstants()){
		//System.out.println(obj + "; "+obj.getClass().getName());
		set.add((CryptCipherTypeZZZ) obj);
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
public static CryptCipherTypeZZZ fromAbbreviation(String s) {
for (CryptCipherTypeZZZ state : values()) {
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
    return this.fullName+"="+this.abbr+"#";
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
	return this.fullName;
}
//+++++++++++++++++++++++++
}//End internal Class

@Override
public String name() {
	// TODO Auto-generated method stub
	return null;
}

@Override
public String getName() {
	// TODO Auto-generated method stub
	return null;
}

@Override
public int getIndex() {
	// TODO Auto-generated method stub
	return 0;
}

@Override
public int getPosition() {
	// TODO Auto-generated method stub
	return 0;
}

@Override
public EnumSet<?> getEnumSetUsed() {
	// TODO Auto-generated method stub
	return null;
}
	
}//End Class
