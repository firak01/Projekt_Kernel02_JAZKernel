package basic.zKernel.file.ini;

import java.util.EnumSet;

import basic.zBasic.util.abstractEnum.IEnumSetMappedStatusZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedTestCaseZZZ;

public enum EnumSetMappedTestCaseSolverTypeZZZ implements IEnumSetMappedTestCaseZZZ{//Folgendes geht nicht, da alle Enums schon von einer Java BasisKlasse erben... extends EnumSetMappedBaseZZZ{
	//Analog zu einem Beispiel aus dem OVPN Projekt.
	//ISSTARTNEW(iSTATUSLOCAL_GROUPID, "isstartnew","SERVER: Noch nicht gestartet.", ""),
	PARSE("parse","p","Variante: 'Parse'"),
	SOLVE("solve","s","Variante: 'Solve'"),
	PARSE_AS_EXPRESSION("parse_as_expression","pae","Variante: 'Parse as experssion'");
	

private String name, abbr;

//aus IEnumSetMappedTestCaseZZZ:
private String sTestCaseMessage;

//Merke: Enums haben keinen public Konstruktor, können also nicht intiantiiert werden, z.B. durch Java-Reflektion.
//          In der Util-Klasse habe ich aber einen Workaround gefunden.
EnumSetMappedTestCaseSolverTypeZZZ(){	
}

//Merke: Enums haben keinen public Konstruktor, können also nicht intiantiiert werden, z.B. durch Java-Reflektion.
//EnumSetMappedStatusTestTypeZZZ(String fullName, String abbr) {
//Analog zu einem Beispiel aus dem OBPN Projekt: STATUSLOCAL(int iStatusGroupId, String sAbbreviation, String sStatusMessage, String sDescription) {
EnumSetMappedTestCaseSolverTypeZZZ(String fullName, String abbr, String sTestCaseMessage) {
    this.name = fullName;
    this.abbr = abbr;
    this.sTestCaseMessage = sTestCaseMessage;
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
public static EnumSetMappedTestCaseSolverTypeZZZ fromAbbreviation(String s) {
    for (EnumSetMappedTestCaseSolverTypeZZZ state : values()) {
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
	return EnumSetMappedTestCaseSolverTypeZZZ.getEnumSet();
}

/* Die in dieser Methode verwendete Klasse für den ...TypeZZZ muss immer angepasst werden. */
@SuppressWarnings("rawtypes")
public static <E> EnumSet getEnumSet() {
	
    //Merke: Das wird anders behandelt als FLAGZ Enumeration.
	//String sFilterName = "FLAGZ"; /
	//...
	//ArrayList<Class<?>> listEmbedded = ReflectClassZZZ.getEmbeddedClasses(this.getClass(), sFilterName);
	
	//Erstelle nun ein EnumSet, speziell für diese Klasse, basierend auf  allen Enumrations  dieser Klasse.
	Class<EnumSetMappedTestCaseSolverTypeZZZ> enumClass = EnumSetMappedTestCaseSolverTypeZZZ.class;
	EnumSet<EnumSetMappedTestCaseSolverTypeZZZ> set = EnumSet.noneOf(enumClass);//Erstelle ein leeres EnumSet
	
	 Enum[]objaEnum = (Enum[]) enumClass.getEnumConstants();
	 for(Object obj : objaEnum){
		//System.out.println(obj + "; "+obj.getClass().getName());
		set.add((EnumSetMappedTestCaseSolverTypeZZZ) obj);
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
public String getTestCaseMessage() {
	return sTestCaseMessage;
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
