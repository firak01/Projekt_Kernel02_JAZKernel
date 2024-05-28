package basic.zBasic.util.xml;

import java.util.EnumSet;

import basic.zBasic.reflection.position.TagTypeFileNameZZZ;
import basic.zBasic.reflection.position.TagTypeFilePositionZZZ;
import basic.zBasic.reflection.position.TagTypeLineNumberZZZ;
import basic.zBasic.reflection.position.TagTypeMethodZZZ;
import basic.zBasic.xml.IEnumSetMappedTagTypeZZZ;
import basic.zBasic.xml.TagFactoryZZZ;
import basic.zBasic.xml.TagFactoryZZZ.TAGTYPE;

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
		//ALIAS("Uniquename", int Anzahl der Knoten ohne umgebende Texte, int Anzahl der Knoten mit umgebenden Texten,"xml String","Beschreibung, wird nicht genutzt....",)
	    //Merke: ExpectedElementsWithText muss immer groesser sein, als ExpectedElementsWithoutText.
		public enum TESTVALUE implements IEnumSetMappedTestXmlTypeZZZ{//Folgendes geht nicht, da alle Enums schon von einer Java BasisKlasse erben... extends EnumSetMappedBaseZZZ{
			neg00("neg00",0,0,"","Negativtest, Leerstring"),
			neg01("neg01",0,1,"Kein Xml Tag da","Negativtest, ohne einen XML Tag"),
			
			pos01("pos01",4,9,"Wert vor abc<abc>wert vor b<b>Wert in b</b>wert nach b</abc>wert nach abc","Positivtest, mit XMLTags und Werten vor den Tags"),
			pos02("pos02",16,23,"<DataElements><EmpStatus>2.0</EmpStatus><Expenditure>95465.00</Expenditure><StaffType>11.A</StaffType><Industry>13</Industry></DataElements>               <InteractionElements><TargetCenter>92f4-MPA</TargetCenter><Trace>7.19879</Trace></InteractionElements>","Positivtest, mit XMLTags und Werten vor den Tags"),
			pos03("pos03",6,13,"Wert vor abc<abc>wert vor b<b>Wert vor bc<bc>Wert in bc</bc>Wert nach bc</b>wert nach b</abc>wert nach abc","Positivtest, mit XMLTags und Werten vor den Tags")
			;
						
			private String sAbbreviation,sXml,sDescription;
			private int iExpectedElementsWithoutText, iExpectedElementsWithText;
		
			//#############################################
			//#### Konstruktoren
			//Merke: Enums haben keinen public Konstruktor, können also nicht intiantiiert werden, z.B. durch Java-Reflektion.
			//In der Util-Klasse habe ich aber einen Workaround gefunden.
			TESTVALUE(String sAbbreviation, int iExpectedElementsWithoutText, int iExpectedElementsWithText, String sXml, String sDescription) {
			    this.sAbbreviation = sAbbreviation;
			    this.iExpectedElementsWithoutText = iExpectedElementsWithoutText;
			    this.iExpectedElementsWithText = iExpectedElementsWithText;
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
