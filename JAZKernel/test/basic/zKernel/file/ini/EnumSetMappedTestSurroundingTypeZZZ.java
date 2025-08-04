package basic.zKernel.file.ini;

import java.util.EnumSet;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedStatusZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedTestCaseZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedTestSurroundingZZZ;

public enum EnumSetMappedTestSurroundingTypeZZZ implements IEnumSetMappedTestSurroundingZZZ{//Folgendes geht nicht, da alle Enums schon von einer Java BasisKlasse erben... extends EnumSetMappedBaseZZZ{
	//Analog zu einem Beispiel aus dem OVPN Projekt.
	//ISSTARTNEW(iSTATUSLOCAL_GROUPID, "isstartnew","SERVER: Noch nicht gestartet.", ""),
	PARSE_KEEP("parse_keep", "pk", true,"Variante: 'Keep Surrounding on Parse', default ist true"),
	PARSE_REMOVE("parse_remove","pr", false,"Variante: 'Remove Surrounding on Parse', nicht default"),
	SOLVE_KEEP("solve_keep","sk", false, "Variante: 'Keep Surrounding on Solve', nicht default"),
	SOLVE_REMOVE("solve_remove", "sr", true, "Variante: 'Remove Surrounding on Solve', default ist true"),
	;
	

private String name, abbr;

//aus IEnumSetMappedSurroudingZZZ:
private String sSurroundingValueUsedTestMessage;
private boolean bSurroundingValueUsedForMethod;

//Merke: Enums haben keinen public Konstruktor, können also nicht intiantiiert werden, z.B. durch Java-Reflektion.
//          In der Util-Klasse habe ich aber einen Workaround gefunden.
EnumSetMappedTestSurroundingTypeZZZ(){	
}

//Merke: Enums haben keinen public Konstruktor, können also nicht intiantiiert werden, z.B. durch Java-Reflektion.
//EnumSetMappedStatusTestTypeZZZ(String fullName, String abbr) {
//Analog zu einem Beispiel aus dem OBPN Projekt: STATUSLOCAL(int iStatusGroupId, String sAbbreviation, String sStatusMessage, String sDescription) {
EnumSetMappedTestSurroundingTypeZZZ(String fullName, String abbr, boolean bSurroundingValueUsed, String sSurroundingTestMessage) {
    this.name = fullName;
    this.abbr = abbr;
    this.bSurroundingValueUsedForMethod = bSurroundingValueUsed;
    this.sSurroundingValueUsedTestMessage = sSurroundingTestMessage;
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
public static EnumSetMappedTestSurroundingTypeZZZ fromAbbreviation(String s) {
    for (EnumSetMappedTestSurroundingTypeZZZ state : values()) {
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
	return EnumSetMappedTestSurroundingTypeZZZ.getEnumSet();
}

/* Die in dieser Methode verwendete Klasse für den ...TypeZZZ muss immer angepasst werden. */
@SuppressWarnings("rawtypes")
public static <E> EnumSet getEnumSet() {
	
    //Merke: Das wird anders behandelt als FLAGZ Enumeration.
	//String sFilterName = "FLAGZ"; /
	//...
	//ArrayList<Class<?>> listEmbedded = ReflectClassZZZ.getEmbeddedClasses(this.getClass(), sFilterName);
	
	//Erstelle nun ein EnumSet, speziell für diese Klasse, basierend auf  allen Enumrations  dieser Klasse.
	Class<EnumSetMappedTestSurroundingTypeZZZ> enumClass = EnumSetMappedTestSurroundingTypeZZZ.class;
	EnumSet<EnumSetMappedTestSurroundingTypeZZZ> set = EnumSet.noneOf(enumClass);//Erstelle ein leeres EnumSet
	
	 Enum[]objaEnum = (Enum[]) enumClass.getEnumConstants();
	 for(Object obj : objaEnum){
		//System.out.println(obj + "; "+obj.getClass().getName());
		set.add((EnumSetMappedTestSurroundingTypeZZZ) obj);
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
//aus IEnumSetMappedTestSurroundingZZZ
@Override
public String getSurroundingValueUsedTestMessage() {
	return this.sSurroundingValueUsedTestMessage;
}

//@Override
//public String setTestCaseMessage(String sTestCaseMessage) {
//	this.sTestCaseMessage = sTestCaseMessage;
//}

@Override
public boolean getSurroundingValueUsedForMethod() throws ExceptionZZZ {
	//Parse und Solve haben unterschiedlich "gepolte" boolean Parameter, quasi was default ist.
	if(this==EnumSetMappedTestSurroundingTypeZZZ.PARSE_REMOVE) {
		return false; //
	}else if(this==EnumSetMappedTestSurroundingTypeZZZ.PARSE_KEEP) {
		return true;
	}else if(this==EnumSetMappedTestSurroundingTypeZZZ.SOLVE_REMOVE) {
		return true;
	}else if(this==EnumSetMappedTestSurroundingTypeZZZ.SOLVE_KEEP) {
		return false;
	}else {
		ExceptionZZZ ez = new ExceptionZZZ("Type not handled yet '" + this.getAbbreviation());
		throw ez;
	}
}

@Override
public boolean isSurroundingValueToRemove_OnParse() throws ExceptionZZZ {
	return (this==EnumSetMappedTestSurroundingTypeZZZ.PARSE_REMOVE);
}

@Override
public boolean isSurroundingValueToKeep_OnParse() throws ExceptionZZZ {
	return (this==EnumSetMappedTestSurroundingTypeZZZ.PARSE_KEEP);
}

@Override
public boolean isSurroundingValueToRemove_OnSolve() throws ExceptionZZZ {
	return (this==EnumSetMappedTestSurroundingTypeZZZ.SOLVE_REMOVE);
}

@Override
public boolean isSurroundingValueToKeep_OnSolve() throws ExceptionZZZ {
	return (this==EnumSetMappedTestSurroundingTypeZZZ.SOLVE_KEEP);
}




//TODO: Mal sehen wie man das kombinieren von 2 EnumSets als Utility - Methode nutezn kann
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
