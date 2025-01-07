package basic.zKernel.file.ini;

import java.util.EnumSet;

import basic.zBasic.util.abstractEnum.IEnumSetMappedStatusZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedTestCaseZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedTestFlagsetZZZ;

//TODOGOON20250107;//mache mit sinnvollen Flagset-kombinationen
public enum EnumSetMappedTestCaseFlagsetTypeZZZ implements IEnumSetMappedTestFlagsetZZZ{//Folgendes geht nicht, da alle Enums schon von einer Java BasisKlasse erben... extends EnumSetMappedBaseZZZ{
	//Ansatz: Weise den Flag-Kombinationsmöglichkeiten, der Testfaelle einen Typ zu.
	//        Dann kann man alle asserts in den Tests in einer assert-Methode buendeln und in meheren Tests nutzen.
	//        
	TODOGOON20240106: Nicht nur beim assert nutzen, sondern auch beim Aufbau der Flag Werte.
	
	//Analog zu einem Beispiel aus dem OVPN Projekt.
	//ISSTARTNEW(iSTATUSLOCAL_GROUPID, "isstartnew","SERVER: Noch nicht gestartet.", ""),
	//Die finale Konstanten (z.B. TestUtilAsTestZZZ.sUNEXPRESSED) sind notwendig, um in einer switch...case Abfrage genutzt zu werden.
	//Das funktioniert nämlich nicht, da es keine finale Konstante ist   case EnumSetMappedTestCaseFlagsetTypeZZZ.UNEXPRESSED.getAbbreviation():
		
	UNEXPRESSED("unexpressed",TestUtilAsTestZZZ.sUNEXPRESSED,"Flagset Variante: 'Unexpressed'"),
	SOLVED("solved","s","Flagset Variante: 'Solved'"),
	UNSOLVED("unsolved","us","Flagset Variante: 'Unsolved'"),
	CALL_UNEXPRESSED("call_unexpressed","cuex","Flagset Variante: 'Call unexpressed'"),
	CALL_UNSOLVED("call_unsolved","cus","Flagset Variante: 'Call unsolved'");
	

private String name, abbr;

//aus IEnumSetMappedTestFlagsetZZZ:
private String sTestFlagsetMessage;

//Merke: Enums haben keinen public Konstruktor, können also nicht intiantiiert werden, z.B. durch Java-Reflektion.
//          In der Util-Klasse habe ich aber einen Workaround gefunden.
EnumSetMappedTestCaseFlagsetTypeZZZ(){	
}

//Merke: Enums haben keinen public Konstruktor, können also nicht intiantiiert werden, z.B. durch Java-Reflektion.
//EnumSetMappedStatusTestTypeZZZ(String fullName, String abbr) {
//Analog zu einem Beispiel aus dem OBPN Projekt: STATUSLOCAL(int iStatusGroupId, String sAbbreviation, String sStatusMessage, String sDescription) {
EnumSetMappedTestCaseFlagsetTypeZZZ(String fullName, String abbr, String sTestFlagsetMessage) {
    this.name = fullName;
    this.abbr = abbr;
    this.sTestFlagsetMessage = sTestFlagsetMessage;
}


@Override
public String toString() {
    return this.name;
}

@Override
public int getIndex() {
	return ordinal();
}

// the identifierMethod ---> Going in DB
@Override
public String getAbbreviation() {
    return this.abbr;
}

// the valueOfMethod <--- Translating from DB
public static EnumSetMappedTestCaseFlagsetTypeZZZ fromAbbreviation(String s) {
    for (EnumSetMappedTestCaseFlagsetTypeZZZ state : values()) {
        if (s.equals(state.getAbbreviation()))
            return state;
    }
    throw new IllegalArgumentException("Not a correct abbreviation: " + s);
}

@Override
public String getDescription(){
	return this.name;
}

public EnumSet<?>getEnumSetUsed(){
	return EnumSetMappedTestCaseFlagsetTypeZZZ.getEnumSet();
}

/* Die in dieser Methode verwendete Klasse für den ...TypeZZZ muss immer angepasst werden. */
@SuppressWarnings("rawtypes")
public static <E> EnumSet getEnumSet() {
	
    //Merke: Das wird anders behandelt als FLAGZ Enumeration.
	//String sFilterName = "FLAGZ"; /
	//...
	//ArrayList<Class<?>> listEmbedded = ReflectClassZZZ.getEmbeddedClasses(this.getClass(), sFilterName);
	
	//Erstelle nun ein EnumSet, speziell für diese Klasse, basierend auf  allen Enumrations  dieser Klasse.
	Class<EnumSetMappedTestCaseFlagsetTypeZZZ> enumClass = EnumSetMappedTestCaseFlagsetTypeZZZ.class;
	EnumSet<EnumSetMappedTestCaseFlagsetTypeZZZ> set = EnumSet.noneOf(enumClass);//Erstelle ein leeres EnumSet
	
	 Enum[]objaEnum = (Enum[]) enumClass.getEnumConstants();
	 for(Object obj : objaEnum){
		//System.out.println(obj + "; "+obj.getClass().getName());
		set.add((EnumSetMappedTestCaseFlagsetTypeZZZ) obj);
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

@Override
public int getPosition() {
	return getIndex() + 1;
}

@Override
public String getName() {	
	return name;
}

//##########################################################
//aus IEnumSetMappedTestCaseZZZ
@Override
public String getTestFlagsetMessage() {
	return sTestFlagsetMessage;
}

//@Override
//public String setTestCaseMessage(String sTestCaseMessage) {
//	this.sTestCaseMessage = sTestCaseMessage;
//}




//TODO: Mal sehen wie man das kombineiren von 2 EnumSets als Utility - Methode nutezn kann
/*
Combining Java EnumSets
enum Suit { CLUBS, DIAMONDS, HEARTS, SPADES }
EnumSet<Suit> reds = EnumSet.of(Suit.HEARTS, Suit.DIAMONDS);
EnumSet<Suit> blacks = EnumSet.of(Suit.CLUBS, Suit.SPADES);

EnumSet<Suit> redAndBlack = EnumSet.copyOf(reds);
redAndBlack.addAll(blacks);
*/
	
	
	//#######################################################################################
	/* für enumeration als Klasse:
	 * 
	 * How to use Java reflection when the enum type is a Class?
	 * public enum PropertyEnum {

  SYSTEM_PROPERTY_ONE("property.one.name", "property.one.value"),

  SYSTEM_PROPERTY_TWO("property.two.name", "property.two.value");

  private String name;  

  private String defaultValue;

  PropertyEnum(String name) {
    this.name = name;
  }

  PropertyEnum(String name, String value) {
    this.name = name;
    this.defaultValue = value;
  } 

  public String getName() {
    return name;
  }

  public String getValue() {
    return System.getProperty(name);
  }

  public String getDefaultValue() {
    return defaultValue;
  }  
	 * 
	 * 
	 * hier die Auswertung
	 *  Class<?> clz = Class.forName("test.PropertyEnum");
    Object[] consts = clz.getEnumConstants();
    Class<?> sub = consts[0].getClass();
    Method mth = sub.getDeclaredMethod("getDefaultValue");
    String val = (String) mth.invoke(consts[0]);
    System.out.println("getDefaultValue " + 
      val.equals(PropertyEnum.SYSTEM_PROPERTY_ONE.getDefaultValue()));
	 */

	
}//End class
