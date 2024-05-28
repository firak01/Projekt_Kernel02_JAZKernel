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
		//ALIAS("Uniquename","xml String", int Anzahl der Tags ohne umgebende Texte, int Anzahl der Tags mit umgebenden Texten, "Beschreibung, wird nicht genutzt....",)
		public enum TESTVALUE implements IEnumSetMappedTestContainerTypeZZZ{//Folgendes geht nicht, da alle Enums schon von einer Java BasisKlasse erben... extends EnumSetMappedBaseZZZ{
			neg00("neg00","",0,0, "Negativtest, Leerstring"),
			neg01("neg00","Kein Xml Tag da",0,1, "Negativtest, ohne xml tag")
			;
			
			
			
			
			private String sAbbreviation,sXml,sDescription;
			private int iExpectedElementsWithoutText, iExpectedElementsWithText;
		
			//#############################################
			//#### Konstruktoren
			//Merke: Enums haben keinen public Konstruktor, können also nicht intiantiiert werden, z.B. durch Java-Reflektion.
			//In der Util-Klasse habe ich aber einen Workaround gefunden.
			TESTVALUE(String sAbbreviation, String sXml, int iExpectedElementsWithoutText, int iExpectedElementsWithText, String sDescription) {
			    this.sAbbreviation = sAbbreviation;
			    this.sXml = sXml;
			    this.iExpectedElementsWithoutText = iExpectedElementsWithoutText;
			    this.iExpectedElementsWithText = iExpectedElementsWithText;
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
			public String getXml() {
				return this.sXml;
			}
			
			public int getExpectedElementsWithoutText() {
				return this.iExpectedElementsWithoutText;
			}

			public int getExpectedElementsWithText() {
				return this.iExpectedElementsWithText;
			}
			
			
			//+++++++++++++++++++++++++
					
			public EnumSet<?>getEnumSetUsed(){
				return TAGTYPE.getEnumSet();
			}
		
			/* Die in dieser Methode verwendete Klasse für den ...TypeZZZ muss immer angepasst werden. */
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
