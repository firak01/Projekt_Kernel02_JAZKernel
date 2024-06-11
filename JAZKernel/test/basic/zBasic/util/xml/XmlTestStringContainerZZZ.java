package basic.zBasic.util.xml;

import java.util.EnumSet;

import basic.zBasic.xml.tagtype.TagByTypeFactoryZZZ.TAGTYPE;

public class XmlTestStringContainerZZZ {
	public static String sENUMNAME="TEST"; //Der Name des Enums, das ggfs. von aussen mal gesucht wird
			
	private String sTest=null;
	
	public XmlTestStringContainerZZZ(String sTest) {
		this.setTest(sTest);
	}
	
	public XmlTestStringContainerZZZ() {		
	}
	
	public String getTest() {
		return this.sTest; 
	}
	
	public void setTest(String sTest) {
		this.sTest = sTest;
	}

	public enum TESTVALUE implements IEnumSetMappedTestValueStringTypeZZZ{
		s0("s0","",""),
		s1("s1","Kein Xml Tag da",""),
		s2("s2","Wert vor b<b>Wert in b</b>Wert hinter b",""),
		s3("s3","Wert vor abc<abc>Wert vor b<b>Wert in b</b>Wert hinter b</abc>Wert hinter abc",""),
		s4("s4","<DataElements><EmpStatus>2.0</EmpStatus><Expenditure>95465.00</Expenditure><StaffType>11.A</StaffType><Industry>13</Industry></DataElements>               <InteractionElements><TargetCenter>92f4-MPA</TargetCenter><Trace>7.19879</Trace></InteractionElements>",""),
		s5("s5","Wert vor abc<abc>Wert vor b<b>Wert vor bc<bc>Wert in bc</bc>Wert hinter bc</b>Wert hinter b</abc>Wert hinter abc",""),
		s6("s6","Wert vor dem 1. abc<abc><a>1. Wert in a</a>Wert vor b<b>Wert in b</b>Wert hinter b</abc>Wert hinter dem 1. abc<abc><a>2. Wert in a</a></abc>",""),
		s7("s7","Wert vor dem 1. abc<abc><a>1. Wert in a</a>Wert vor b<b>Wert vor bc<bc>Wert in bc</bc></b>Wert hinter b</abc>Wert hinter dem 1. abc<abc><a>2. Wert in a</a></abc>",""),
		s8("s8","Wert vor abc<abc>Wert vor b<b>Wert vor dem 1. bc<bc>1. Wert in bc</bc>Wert hinter dem 1. bc/vor dem 2. bc<bc>2. Wert in bc</bc></b>Wert hinter b</abc>Wert hinter abc","");
		
		private String sAbbreviation,sXml,sDescription;
		
		//#############################################
		//#### Konstruktoren
		//Merke: Enums haben keinen public Konstruktor, können also nicht intiantiiert werden, z.B. durch Java-Reflektion.
		//In der Util-Klasse habe ich aber einen Workaround gefunden.
		TESTVALUE(String sAbbreviation, String sXml, String sDescription) {
		    this.sAbbreviation = sAbbreviation;
		    this.sXml = sXml;
		    this.sDescription = sDescription;
		}
		
		@Override
		public String getAbbreviation() {
		 return this.sAbbreviation;
		}						
		
		@Override
		public String getDescription() {
			return this.sDescription;
		}		
		
		//++++++++++++++++++++++++++
		@Override
		public String getString() {
			return this.sXml;
		}
		
		
		//+++++++++++++++++++++++++	
		@Override
		public EnumSet<?>getEnumSetUsed(){
			return TAGTYPE.getEnumSet();
		}
	
		/* Die in dieser Methode verwendete Klasse für den ...TypeZZZ muss immer angepasst werden. */
		//static Methoden koennen nicht ueberschrieben werden @Override
		@SuppressWarnings("rawtypes")			
		public static <E> EnumSet getEnumSet() {
			
		 //Merke: Das wird anders behandelt als FLAGZ Enumeration.
			//String sFilterName = "FLAGZ"; /
			//...
			//ArrayList<Class<?>> listEmbedded = ReflectClassZZZ.getEmbeddedClasses(this.getClass(), sFilterName);
			
			//Erstelle nun ein EnumSet, speziell für diese Klasse, basierend auf  allen Enumrations  dieser Klasse.
			Class<TESTVALUE> enumClass = XmlTestStringContainerZZZ.TESTVALUE.class;
			EnumSet<TESTVALUE> set = EnumSet.noneOf(enumClass);//Erstelle ein leeres EnumSet
			
			Enum[]objaEnum = (Enum[]) enumClass.getEnumConstants();
			for(Object obj : objaEnum){
				//System.out.println(obj + "; "+obj.getClass().getName());
				set.add((TESTVALUE) obj);
			}
			return set;
			
		}
	
		//TODO: Mal ausprobieren was das bringt
		//Convert Enumeration to a Set/List
		//static Methoden koennen nicht ueberschrieben werden @Override
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
		//static Methoden koennen nicht ueberschrieben werden @Override
		public static TESTVALUE fromAbbreviation(String s) {
		for (TESTVALUE state : values()) {
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
		
	}//End internal Class
	

		//################### ENUM ALLER "EINFACHEN" KERNEL-TAG-TYPEN ##############
		//ALIAS("Uniquename", "xml String",
	    //                    int[] {indexposition des Tags im Vector ohne umgebende Texte},
	    //                    int[] {indexposition des Tags im Vector mit umgebende Texte},
	    //                    String[] {Beispieltags},
	    //					  String[][] {{"Beispielwerte des Tags01","Beispielwerte des Tags01"}{"Beispielwerte des Tags02","Beispielwerte des Tags02"},
		//					  int Anzahl der Tags (aufmachend, schliessend) ohne umgebende Texte,
		//                    int Anzahl der Tags (aufmachend, schliessend) mit umgebenden Texten,
		//                   
	    //                    "Beschreibung, wird nicht genutzt....",)
	    //Merke01: Es kann pro Tag ggfs. mehrere Werte geben. Darum sind die ExpectedValues "Array von Array"	
	    //Merke02: ExpectedElementsWithText >= ExpectedElementsWithoutText.
	    
		public enum TEST implements IEnumSetMappedTestXmlTypeZZZ{//Folgendes geht nicht, da alle Enums schon von einer Java BasisKlasse erben... extends EnumSetMappedBaseZZZ{
			neg00("neg00",TESTVALUE.s0, new int[] {-1}, new int[] {-1}, null,null,0,0,"Negativtest, Leerstring"),
			neg01("neg01",TESTVALUE.s1, new int[] {-1}, new int[] {-1}, new String[] {"text"},new String[][] {{"Kein Xml Tag da"}},0,1,"Negativtest, ohne einen XML Tag"),
			
			pos01("pos01",TESTVALUE.s2, new int[] {0}, new int[] {1}, new String[]{"b"},new String[][]{{"Wert in b"}},2,5,"Positivtest, mit XMLTags und Werten vor den Tags"),
			pos02("pos02",TESTVALUE.s3, new int[] {0}, new int[] {1}, new String[]{"abc"},new String[][]{{"Wert vor b<b>Wert in b</b>Wert hinter b"}},4,9,"Positivtest, mit XMLTags und Werten vor den Tags, nur 1 Ebene, keine weiteren Tags auf einer Ebene"),
			pos03("pos03",TESTVALUE.s4, new int[] {0}, new int[] {1}, new String[]{"EmpStatus","StaffType"},new String[][]{{"2.0"},{"11.A"}},16,23,"Positivtest, mit XMLTags und Werten vor den Tags"),			
			pos04("pos04",TESTVALUE.s5, new int[] {0}, new int[] {1}, new String[]{"bc"},new String[][]{{"Wert in bc"}},6,13,"Positivtest, mit XMLTags und Werten vor den Tags"),
			pos05("pos05",TESTVALUE.s6, new int[] {0}, new int[] {1}, new String[]{"bc","a"},new String[][]{{null},{"1. Wert in a","2. Wert in a"}},10,17,"Positivtest, mit XMLTags und Werten vor den Tags, +++ Tag kommt mehrmals vor, ABER UNTERSCHIEDE IM ZWEIG WERDEN NICHT BERUECKSICHTIGT"),
			pos06("pos06",TESTVALUE.s7, new int[] {0}, new int[] {1}, new String[]{"bc","a"},new String[][]{{"Wert in bc"},{"1. Wert in a","2. Wert in a"}},12,20,"Positivtest, mit XMLTags und Werten vor den Tags, +++ Tag kommt mehrmals vor, ABER UNTERSCHIEDE IM ZWEIG WERDEN NICHT BERUECKSICHTIGT - tiefer verschachtelt"),	
			pos07("pos07",TESTVALUE.s8, new int[] {0}, new int[] {1}, new String[]{"abc","bc"},new String[][]{{"Wert vor b<b>Wert vor dem 1. bc<bc>1. Wert in bc</bc>Wert hinter dem 1. bc/vor dem 2. bc<bc>2. Wert in bc</bc></b>Wert hinter b"},{"1. Wert in bc", "2. Wert in bc"}},8,16,"Positivtest, mit XMLTags und Werten vor den Tags, +++ Tag kommt mehrmals vor, ABER UNTERSCHIEDE IM ZWEIG WERDEN NICHT BERUECKSICHTIGT - tiefer verschachtelt")
			;
						
			private String sAbbreviation,sDescription;			
			private int iExpectedElementsWithoutText, iExpectedElementsWithText;
			private int[]iaExpectedExampleIndexInVectorWithoutText; private int[]iaExpectedExampleIndexInVectorWithText;
			
			private String[]saExpectedExampleTag; String[][]saExpectedExampleValue;
			private IEnumSetMappedTestValueStringTypeZZZ enumMappedString;
			
			//#############################################
			//#### Konstruktoren
			//Merke: Enums haben keinen public Konstruktor, können also nicht intiantiiert werden, z.B. durch Java-Reflektion.
			//In der Util-Klasse habe ich aber einen Workaround gefunden.
			TEST(String sAbbreviation, IEnumSetMappedTestValueStringTypeZZZ enumMappedString, int[] iaExpectedExampleIndexWithoutText, int[] iaExpectedExampleIndexWithText, String[] saExpectedExampleTag, String[][] saExpectedExampleValue, int iExpectedElementsWithoutText, int iExpectedElementsWithText, String sDescription) {
			    this.sAbbreviation = sAbbreviation;
			    this.enumMappedString = enumMappedString;
			    this.iExpectedElementsWithoutText = iExpectedElementsWithoutText;
			    this.iExpectedElementsWithText = iExpectedElementsWithText;			    
			    //Merke: Das wird nun aus dem String des enum TESTVALUE geholt   this.sXml = sXml;
			    this.iaExpectedExampleIndexInVectorWithoutText = iaExpectedExampleIndexWithoutText;
			    this.iaExpectedExampleIndexInVectorWithText = iaExpectedExampleIndexWithText;
			    this.saExpectedExampleTag = saExpectedExampleTag;
			    this.saExpectedExampleValue = saExpectedExampleValue;
			    this.sDescription = sDescription;
			}
			
			@Override
			public String getAbbreviation() {
			 return this.sAbbreviation;
			}						
			
			@Override
			public String getDescription() {
				return this.sDescription;
			}			
			
			//++++++++++++++++++++++++++++++++++++++++	
			@Override
			public int getExpectedElementsWithoutText() {
				return this.iExpectedElementsWithoutText;
			}

			@Override
			public int getExpectedElementsWithText() {
				return this.iExpectedElementsWithText;
			}
			
			@Override
			public String getXml() {
				return this.enumMappedString.getString();
			}
			
			@Override
			public int[] getIndexInVectorOfExpectedTagsWithoutText() {
				return this.iaExpectedExampleIndexInVectorWithoutText;
			}
			
			@Override
			public int[] getIndexInVectorOfExpectedTagsWithText() {
				return this.iaExpectedExampleIndexInVectorWithText;
			}
			
			@Override
			public String[] getTagsForExpectedValue() {
				return this.saExpectedExampleTag;
			}

			@Override
			public String[][] getExpectedValues() {
				return this.saExpectedExampleValue;
			}	
			
			//+++++++++++++++++++++++++	
			@Override
			public EnumSet<?>getEnumSetUsed(){
				return TAGTYPE.getEnumSet();
			}
		
			/* Die in dieser Methode verwendete Klasse für den ...TypeZZZ muss immer angepasst werden. */
			//static Methoden koennen nicht ueberschrieben werden @Override
			@SuppressWarnings("rawtypes")			
			public static <E> EnumSet getEnumSet() {
				
			 //Merke: Das wird anders behandelt als FLAGZ Enumeration.
				//String sFilterName = "FLAGZ"; /
				//...
				//ArrayList<Class<?>> listEmbedded = ReflectClassZZZ.getEmbeddedClasses(this.getClass(), sFilterName);
				
				//Erstelle nun ein EnumSet, speziell für diese Klasse, basierend auf  allen Enumrations  dieser Klasse.
				Class<TEST> enumClass = XmlTestStringContainerZZZ.TEST.class;
				EnumSet<TEST> set = EnumSet.noneOf(enumClass);//Erstelle ein leeres EnumSet
				
				Enum[]objaEnum = (Enum[]) enumClass.getEnumConstants();
				for(Object obj : objaEnum){
					//System.out.println(obj + "; "+obj.getClass().getName());
					set.add((TEST) obj);
				}
				return set;
				
			}
		
			//TODO: Mal ausprobieren was das bringt
			//Convert Enumeration to a Set/List
			//static Methoden koennen nicht ueberschrieben werden @Override
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
			//static Methoden koennen nicht ueberschrieben werden @Override
			public static TEST fromAbbreviation(String s) {
			for (TEST state : values()) {
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
			
		}//End internal Class
}
