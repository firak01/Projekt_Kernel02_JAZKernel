package basic.zKernel.file.ini;

import java.util.EnumSet;

import basic.zBasic.util.abstractEnum.IEnumSetMappedStatusZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedTestCaseZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedTestFlagsetZZZ;

//TODOGOON20250107;//mache Flagset-Kombinationen, sogenannte "Flagsets".
                   //Damit wird dann ein Aufruf reichen um mehrere Flags zu setzen.
public enum EnumSetMappedTestCaseFlagsetTypeZZZ implements IEnumSetMappedTestFlagsetZZZ{//Folgendes geht nicht, da alle Enums schon von einer Java BasisKlasse erben... extends EnumSetMappedBaseZZZ{
	//Ansatz: Weise den Flag-Kombinationsmöglichkeiten, der Testfaelle einen Typ zu.
	//        Dann kann man alle asserts in den Tests in einer assert-Methode buendeln und in meheren Tests nutzen.
	//        	//Das funktioniert nämlich nicht, da es keine finale Konstante ist   case EnumSetMappedTestCaseFlagsetTypeZZZ.UNEXPRESSED.getAbbreviation():

	//Analog zu einem Beispiel aus dem OVPN Projekt.
	//ISSTARTNEW(iSTATUSLOCAL_GROUPID, "isstartnew","SERVER: Noch nicht gestartet.", ""),
	//Die finale Konstanten (z.B. TestUtilAsTestZZZ.sUNEXPRESSED) sind notwendig, um in einer switch...case Abfrage genutzt zu werden.
		
	UNEXPRESSED("unexpressed",TestUtilAsTestZZZ.sFLAGSET_UNEXPRESSED,"Flagset Variante: 'Unexpressed'"),
	UNPARSED("unparsed",TestUtilAsTestZZZ.sFLAGSET_UNPARSED,"Flagset Variante: 'Unparsed'"),
	SOLVED("solved",TestUtilAsTestZZZ.sFLAGSET_SOLVED,"Flagset Variante: 'Solved'"),	
	UNSOLVED("unsolved",TestUtilAsTestZZZ.sFLAGSET_UNSOLVED,"Flagset Variante: 'Unsolved'"),
	UNPARSED_UNSOLVED("unparsed_unsolved",TestUtilAsTestZZZ.sFLAGSET_UNPARSED_UNSOLVED,"Flagset Variante: 'Unparsed' und 'Unsolved'"),
	
	FORMULA_UNSOLVED("formula_unsolved",TestUtilAsTestZZZ.sFLAGSET_FORMULA_UNSOLVED,"Flagset Variante: 'Formula unsolved'"),
	FORMULA_MATH_UNSOLVED("formula_math_unsolved",TestUtilAsTestZZZ.sFLAGSET_MATH_UNSOLVED,"Flagset Variante: 'Formula Math unsolved'"),
	FORMULA_MATH_SOLVED("formula_math_solved",TestUtilAsTestZZZ.sFLAGSET_MATH_SOLVED,"Flagset Variante: 'Formula Math solved'"),
	
	PATH_UNSUBSTITUTED("path_unsubstituted",TestUtilAsTestZZZ.sFLAGSET_PATH_UNSUBSTITUTED,"Flagset Variante: 'Path Unsubstituted'"),
	PATH_SUBSTITUTED("path_substituted",TestUtilAsTestZZZ.sFLAGSET_PATH_SUBSTITUTED,"Flagset Variante: 'Path Substituted'"),
	VARIABLE_UNSUBSTITUTED("variable_unsubstituted",TestUtilAsTestZZZ.sFLAGSET_VARIABLE_UNSUBSTITUTED,"Flagset Variante: 'Variable Unsubstituted'"),
	VARIABLE_SUBSTITUTED("variable_substituted",TestUtilAsTestZZZ.sFLAGSET_VARIABLE_SUBSTITUTED,"Flagset Variante: 'Variable Substituted'"),
	
	CALL_UNSOLVED("call_unsolved",TestUtilAsTestZZZ.sFLAGSET_CALL_UNSOLVED,"Flagset Variante: 'Call unsolved'"),
	CALL_UNSOLVED_JAVACALL_SOLVED("call_unsolved_javacall_solved",TestUtilAsTestZZZ.sFLAGSET_CALL_UNSOLVED_JAVACALL_SOLVED,"Flagset Variante: 'Call unsolved, JavaCall solved'"),
	JAVACALL_UNSOLVED("javacall_unsolved",TestUtilAsTestZZZ.sFLAGSET_JAVACALL_UNSOLVED,"Flagset Variante: 'JavaCall unsolved'"),
	JAVACALL_SOLVED("javacall_solved",TestUtilAsTestZZZ.sFLAGSET_JAVACALL_SOLVED,"Flagset Variante: 'JavaCall solved'"),
	
	JSON_SOLVED("json_solved",TestUtilAsTestZZZ.sFLAGSET_JSON_SOLVED,"Flagset Variante: 'Json solved'"),
	JSON_UNSOLVED("json_unsolved",TestUtilAsTestZZZ.sFLAGSET_JSON_UNSOLVED,"Flagset Variante: 'Json unsolved'"),
			
	JSONARRAY_SOLVED("jsonarray_solved",TestUtilAsTestZZZ.sFLAGSET_JSONARRAY_SOLVED,"Flagset Variante: 'JsonArray solved'"),
	JSONARRAY_UNSOLVED("jsonarray_unsolved",TestUtilAsTestZZZ.sFLAGSET_JSONARRAY_UNSOLVED,"Flagset Variante: 'JsonArray unsolved'"),
		
	JSONMAP_SOLVED("jsonmap_solved",TestUtilAsTestZZZ.sFLAGSET_JSONMAP_SOLVED,"Flagset Variante: 'JsonArray solved'"),
	JSONMAP_UNSOLVED("jsonmap_unsolved",TestUtilAsTestZZZ.sFLAGSET_JSONMAP_UNSOLVED,"Flagset Variante: 'JsonArray unsolved'"),
	
	ENCRYPTION_SOLVED("encryption_solved",TestUtilAsTestZZZ.sFLAGSET_ENCRYPTION_SOLVED,"Flagset Variante: 'Encryption solved'"),
	ENCRYPTION_UNSOLVED("encryption_unsolved",TestUtilAsTestZZZ.sFLAGSET_ENCRYPTION_UNSOLVED,"Flagset Variante: 'Encryption unsolved'"),
	
	;
	
	

	private String name, abbr;
	
	//aus IEnumSetMappedTestFlagsetZZZ:
	private String sTestFlagsetMessage;
	
	//Merke: Enums haben keinen public Konstruktor, können also nicht intiantiiert werden, z.B. durch Java-Reflektion.
	//          In der Util-Klasse habe ich aber einen Workaround gefunden.
	EnumSetMappedTestCaseFlagsetTypeZZZ(){	
	}
	
	//Merke1: Enums haben keinen public Konstruktor, können also nicht intiantiiert werden, z.B. durch Java-Reflektion.
	//Merke2: Analog zu einem Beispiel aus dem OBPN Projekt: STATUSLOCAL(int iStatusGroupId, String sAbbreviation, String sStatusMessage, String sDescription) {
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
	
	
	//TODO: Mal sehen wie man das Kombinieren von 2 EnumSets als Utility - Methode nutzen kann
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
