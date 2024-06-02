package basic.zBasic.util.xml;

import java.util.EnumSet;

import basic.zBasic.reflection.position.TagTypeFileNameZZZ;
import basic.zBasic.reflection.position.TagTypeFilePositionZZZ;
import basic.zBasic.reflection.position.TagTypeLineNumberZZZ;
import basic.zBasic.reflection.position.TagTypeMethodZZZ;
import basic.zBasic.xml.tagtype.IEnumSetMappedTagTypeZZZ;
import basic.zBasic.xml.tagtype.TagByTypeFactoryZZZ;
import basic.zBasic.xml.tagtype.TagByTypeFactoryZZZ.TAGTYPE;

public class XmlTestStringContainerZZZ {
	public static String sENUMNAME="TESTVALUE";
			
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


	//################### ENUM ALLER "EINFACHEN" KERNEL-TAG-TYPEN ##############
		//ALIAS("Uniquename", int Anzahl der Tags (aufmachend, schliessend) ohne umgebende Texte, int Anzahl der Tags (aufmachend, schliessend) mit umgebenden Texten,{Beispieltags},{{Beispielwerte der Tags}}, "xml String","Beschreibung, wird nicht genutzt....",)
	    //Merke01: Es kann pro Tag ggfs. mehrere Werte geben. Darum sind die ExpectedValues "Array von Array"	
	    //Merke02: ExpectedElementsWithText muss immer groesser sein, als ExpectedElementsWithoutText.
	    
		public enum TESTVALUE implements IEnumSetMappedTestXmlTypeZZZ{//Folgendes geht nicht, da alle Enums schon von einer Java BasisKlasse erben... extends EnumSetMappedBaseZZZ{
			neg00("neg00",null,null,0,0,"","Negativtest, Leerstring"),
			neg01("neg01",null,null,0,1,"Kein Xml Tag da","Negativtest, ohne einen XML Tag"),
			
			pos01("pos01",new String[]{"b"},new String[][]{{"Wert in b"}},4,9,"Wert vor abc<abc>wert vor b<b>Wert in b</b>wert hinter b</abc>wert hinter abc","Positivtest, mit XMLTags und Werten vor den Tags"),
			pos02("pos02",new String[]{"EmpStatus","StaffType"},new String[][]{{"2.0"},{"11.A"}},16,23,"<DataElements><EmpStatus>2.0</EmpStatus><Expenditure>95465.00</Expenditure><StaffType>11.A</StaffType><Industry>13</Industry></DataElements>               <InteractionElements><TargetCenter>92f4-MPA</TargetCenter><Trace>7.19879</Trace></InteractionElements>","Positivtest, mit XMLTags und Werten vor den Tags"),
			pos03("pos03",new String[]{"bc"},new String[][]{{"Wert in bc"}},6,13,"Wert vor abc<abc>wert vor b<b>Wert vor bc<bc>Wert in bc</bc>Wert hinter bc</b>wert hinter b</abc>wert hinter abc","Positivtest, mit XMLTags und Werten vor den Tags"),
			pos04("pos04",new String[]{"bc","a"},new String[][]{{null},{"1. Wert in a","2. Wert in a"}},10,17,"Wert vor dem 1. abc<abc><a>1. Wert in a</a>wert vor b<b>wert in b</b>wert hinter b</abc>wert hinter dem 1. abc<abc><a>2. Wert in a</a></abc>","Positivtest, mit XMLTags und Werten vor den Tags, +++ Tag kommt mehrmals vor, ABER UNTERSCHIEDE IM ZWEIG WERDEN NICHT BERUECKSICHTIGT"),
			pos05("pos05",new String[]{"bc","a"},new String[][]{{"Wert in bc"},{"1. Wert in a","2. Wert in a"}},12,20,"Wert vor dem 1. abc<abc><a>1. Wert in a</a>wert vor b<b>Wert vor bc<bc>Wert in bc</bc></b>wert hinter b</abc>wert hinter dem 1. abc<abc><a>2. Wert in a</a></abc>","Positivtest, mit XMLTags und Werten vor den Tags, +++ Tag kommt mehrmals vor, ABER UNTERSCHIEDE IM ZWEIG WERDEN NICHT BERUECKSICHTIGT - tiefer verschachtelt")	
			;
						
			private String sAbbreviation,sXml,sDescription;
			private int iExpectedElementsWithoutText, iExpectedElementsWithText;
			private String[]saExpectedTagExample; String[][]saExpectedValueExample;
		
			//#############################################
			//#### Konstruktoren
			//Merke: Enums haben keinen public Konstruktor, können also nicht intiantiiert werden, z.B. durch Java-Reflektion.
			//In der Util-Klasse habe ich aber einen Workaround gefunden.
			TESTVALUE(String sAbbreviation, String[] saExpectedTagExample, String[][] saExpectedValueExample, int iExpectedElementsWithoutText, int iExpectedElementsWithText, String sXml, String sDescription) {
			    this.sAbbreviation = sAbbreviation;
			    this.iExpectedElementsWithoutText = iExpectedElementsWithoutText;
			    this.iExpectedElementsWithText = iExpectedElementsWithText;			    
			    this.sXml = sXml;
			    this.saExpectedTagExample = saExpectedTagExample;
			    this.saExpectedValueExample = saExpectedValueExample;
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
				return this.sXml;
			}
			
			@Override
			public String[] getTagsForExpectedValue() {
				return this.saExpectedTagExample;
			}

			@Override
			public String[][] getExpectedValues() {
				return this.saExpectedValueExample;
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
}
