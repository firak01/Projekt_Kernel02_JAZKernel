package basic.zBasic.util.abstractEnum;

import java.util.EnumSet;
import java.util.Set;

import junit.framework.TestCase;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractList.HashMapExtendedZZZ;
import basic.zBasic.util.datatype.enums.EnumSetMappedUtilZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.persistence.jdbc.UrlLogicZZZ;

public class EnumSetMappedZZZTest extends TestCase{
	 private HashMapExtendedZZZ<String, EnumSetMappedTestTypeZZZ> hmTestGenerics = null;
	 
	    protected void setUp(){
	      
		//try {			
		
			//### Das spezielle Generics Testobjekt			
			hmTestGenerics = new HashMapExtendedZZZ<String, EnumSetMappedTestTypeZZZ>();
			
			Set<EnumSetMappedTestTypeZZZ> allTypes = EnumSet.allOf(EnumSetMappedTestTypeZZZ.class);
			for(EnumSetMappedTestTypeZZZ myType : allTypes) {
				//String sType = myType.getAbbreviation();
				String sName = myType.name();
				hmTestGenerics.put(sName,myType);
			}
			
		
			
		/*
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		} 
		*/
		
	}//END setup
	    public void testGetEnumSet(){
//			try{
				boolean btemp;
				
				//static zugriff
				//EnumSet soll von der EnuerationZZZ-Klasse schon "generisch/per Reflektion" erzeugt und mitgebracht werden.
				EnumSet setEnumGenerated = EnumSetMappedTestTypeZZZ.getEnumSet();		
				int iSize = setEnumGenerated.size();
				assertTrue("3 Elemente im Set erwartet.", iSize==3);
				
//			} catch (ExceptionZZZ ez) {
//				fail("Method throws an exception." + ez.getMessageLast());
//			} 
	    }
	    
	    public void testStartsWith(){
			try{
				boolean btemp;

				//++++++++++++++++++++++
				//Variante A) Greife auf eine static Methode zurück.
				//Merke: EnumSet wird von der EnumerationZZZ-Klasse schon "generisch/per Reflektion" erzeugt und mitgebracht.
				EnumSet setEnumGenerated = EnumSetMappedTestTypeZZZ.getEnumSet();
				
				//Positivfall
				btemp = EnumSetMappedUtilZZZ.startsWithAnyAlias("ONE", setEnumGenerated);
				assertTrue("A) Prüfstring sollte in der Enumeration vorhanden sein.", btemp);
				
				//Negativfall
				btemp = EnumSetMappedUtilZZZ.startsWithAnyAlias("XXXX", setEnumGenerated);
				assertFalse("A) Prüfstring sollte in der Enumeration NICHT vorhanden sein.", btemp);

				//++++++++++++++++++++++
				//Variante B) Erstelle das EnumSet mit festen Werten
				//Positivfall
				EnumSet<EnumSetMappedTestTypeZZZ> setEnumCurrent = EnumSet.of(EnumSetMappedTestTypeZZZ.ONE, EnumSetMappedTestTypeZZZ.TWO, EnumSetMappedTestTypeZZZ.THREE);
				btemp = EnumSetMappedUtilZZZ.startsWithAnyAlias("ONE", setEnumCurrent);
				assertTrue("B) Prüfstring sollte in der Enumeration vorhanden sein.", btemp);
				
				//Negativfall
				EnumSet<EnumSetMappedTestTypeZZZ> setEnumCurrent02 = EnumSet.of(EnumSetMappedTestTypeZZZ.ONE, EnumSetMappedTestTypeZZZ.TWO, EnumSetMappedTestTypeZZZ.THREE);
				btemp = EnumSetMappedUtilZZZ.startsWithAnyAlias("Nixdaaa",setEnumCurrent02);
				assertFalse("B) Prüfstring sollte in der Enumeration NICHT vorhanden sein .", btemp);
				
				//++++++++++++++++++++++				
				//Varainte C) Hole das benötigte EnumSet aus der Enumeration selbst, unter intensivem Einsatz von Reflection.
				//                Dabei reicht der Name der Klasse aus PLUS der Factory, die ihn dan verarbeitet.
				IEnumSetFactoryZZZ objFactory = EnumSetTestFactoryZZZ.getInstance();	
				EnumSetMappedUtilZZZ objEnumUtilC = new EnumSetMappedUtilZZZ(objFactory, EnumSetMappedTestTypeZZZ.class);
				EnumSet<?> setEnumGeneric = objEnumUtilC.getEnumSetCurrent();
				
				//Positivfall
				btemp = EnumSetMappedUtilZZZ.startsWithAnyAlias("ONE", setEnumGeneric);
				assertTrue("C) Prüfstring sollte in der Enumeration vorhanden sein.", btemp);
				
				//Negativfall
				btemp = EnumSetMappedUtilZZZ.startsWithAnyAlias("Nixdaaa", setEnumGeneric);
				assertFalse("C) Prüfstring sollte in der Enumeration NICHT vorhanden sein .", btemp);
				
				//++++++++++++++++++++++
				//Variante D) wie C), nur noch directer
				IEnumSetFactoryZZZ objFactoryD = EnumSetTestFactoryZZZ.getInstance();	
				EnumSetMappedUtilZZZ objEnumUtilD = new EnumSetMappedUtilZZZ(objFactoryD, EnumSetMappedTestTypeZZZ.class);
				
				//Positivfall
				btemp = objEnumUtilD.startsWithAnyAlias("ONE");
				assertTrue("D) Prüfstring sollte in der Enumeration vorhanden sein.", btemp);
				
				//Negativfall
				btemp = objEnumUtilD.startsWithAnyAlias("Nixdaaa");
				assertFalse("D) Prüfstring sollte in der Enumeration NICHT vorhanden sein .", btemp);
			
			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			} 
		}
	}//end class
	

