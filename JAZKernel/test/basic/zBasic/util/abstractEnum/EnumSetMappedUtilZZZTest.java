package basic.zBasic.util.abstractEnum;

import java.util.EnumSet;
import java.util.Set;

import junit.framework.TestCase;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractList.HashMapExtendedZZZ;

public class EnumSetMappedUtilZZZTest  extends TestCase{
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
			try{
				boolean btemp;
				
				//Static Zugriff
				//EnumSet soll von der EnumerationZZZ-Klasse schon "generisch/per Reflektion" erzeugt und mitgebracht werden.
				EnumSet setEnumGenerated = EnumSetMappedTestTypeZZZ.getEnumSet();		
				int iSize = setEnumGenerated.size();
				assertTrue("3 Elemente im Set erwartet.", iSize==3);
				
				//Generischer Zugriff, ist  nicht ganz möglich, darum Verwendung der Factory Klasse
				IEnumSetFactoryZZZ objFactory = EnumSetMappedTestFactoryZZZ.getInstance();				
				Class objClass = EnumSetMappedTestTypeZZZ.class;
				EnumSetMappedUtilZZZ enumSetUtil = new EnumSetMappedUtilZZZ(objFactory, objClass);
				
				//Beim Wiederholten Zugriff über die Util-Klasse soll das einmal erstellte EnumSet wiederverwendet werden.	
				EnumSet setEnumReused = enumSetUtil.getEnumSetCurrent();
				assertNotNull(setEnumReused);
				
				int iSizeReused = setEnumReused.size();
				assertTrue("3 Elemente im SetReused erwartet.", iSizeReused==3);				
			
			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			} 
	    }
	    
	    public void testStartsWith(){
			try{
				boolean btemp;
				
				//Merke: Varaussetzung ist: EnumSet wird von der EnumerationZZZ-Klasse schon "generisch/per Reflektion" erzeugt und mitgebracht werden. Alles basisert auf einer static Methode getEnumSet().

				//Variante A) EnumSet selbst erzeugen
				//Positivfall
				EnumSet<EnumSetMappedTestTypeZZZ> setEnumCurrent = EnumSet.of(EnumSetMappedTestTypeZZZ.ONE, EnumSetMappedTestTypeZZZ.TWO, EnumSetMappedTestTypeZZZ.THREE);
				assertNotNull(setEnumCurrent);
				btemp = EnumSetMappedUtilZZZ.startsWithAnyAlias("ONE", setEnumCurrent);
				assertTrue("A) Prüfstring sollte in der Enumeration vorhanden sein.", btemp);
				
				//Negativfall
				EnumSet<EnumSetMappedTestTypeZZZ> setEnumCurrent02 = EnumSet.of(EnumSetMappedTestTypeZZZ.ONE, EnumSetMappedTestTypeZZZ.TWO, EnumSetMappedTestTypeZZZ.THREE);
				assertNotNull(setEnumCurrent02);
				btemp = EnumSetMappedUtilZZZ.startsWithAnyAlias("Nixdaaa", setEnumCurrent02);
				assertFalse("A) Prüfstring sollte in der Enumeration NICHT vorhanden sein .", btemp);
				
				//Variante B) EnumSet per statischer Methode holen
				//Positivfall
				EnumSet<?> setEnumGenerated = EnumSetMappedTestTypeZZZ.getEnumSet();
				assertNotNull(setEnumGenerated);
				btemp = EnumSetMappedUtilZZZ.startsWithAnyAlias("TWO", setEnumGenerated);
				assertTrue("B) Prüfstring sollte in der Enumeration vorhanden sein.", btemp);
				
				//Negativvall
				EnumSet<?> setEnumGenerated02 = EnumSetMappedTestTypeZZZ.getEnumSet();
				assertNotNull(setEnumGenerated02);
				btemp = EnumSetMappedUtilZZZ.startsWithAnyAlias("NIXDA", setEnumGenerated02);
				assertFalse("B) Prüfstring sollte in der Enumeration NICHT vorhanden sein.", btemp);
				
				//Variante C) direkter
				//Generischer Zugriff, ist  nicht ganz möglich, darum Verwendung der Factory Klasse
				IEnumSetFactoryZZZ objFactory = EnumSetMappedTestFactoryZZZ.getInstance();	
				Class objClass = EnumSetMappedTestTypeZZZ.class;
				EnumSetMappedUtilZZZ enumSetUtil = new EnumSetMappedUtilZZZ(objFactory, objClass);
				
				//Positivfall
				btemp = enumSetUtil.startsWithAnyAbbreviation("3");
				assertTrue("C) Prüfstring sollte in der Enumeration vorhanden sein.", btemp);
												
				//Negativfall
				btemp = enumSetUtil.startsWithAnyAbbreviation("XXXX");
				assertFalse("C) Prüfstring sollte in der Enumeration NICHT vorhanden sein.", btemp);
				
			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			} 
		}    
	    
	    public void testGetEnumConstant(){
	    	Class<?> objClass = EnumSetMappedTestTypeZZZ.class;
	    	String sName = EnumSetMappedUtilZZZ.getEnumConstant_NameValue(objClass, "ONE");
	    	assertTrue("Prüfstring solllte als Ergebnis 'ONE' sein", "ONE".equals(sName));
	    	
	    	String sString = EnumSetMappedUtilZZZ.getEnumConstant_StringValue(objClass, "ONE");
	    	assertTrue("Prüfstring solllte als Ergebnis 'eins' sein", "eins".equals(sString));
	    	
	    	Integer intOrdinal = EnumSetMappedUtilZZZ.getEnumConstant_OrdinalValue(objClass, "ONE");
	    	assertEquals("Prüfinteger solllte als Ergebnis 0 sein", intOrdinal.intValue(),0);
	    	
	     	@SuppressWarnings("unchecked")
			String sDescription = EnumSetMappedUtilZZZ.getEnumConstant_DescriptionValue((Class<IEnumSetMappedZZZ>) objClass, "ONE");
	    	assertTrue("Prüfstring solllte als Ergebnis 'eins' sein", "eins".equals(sDescription));
	    	assertEquals("StringValue sollte gleich DescriptionValue sein", sString, sDescription);
	    	
	    	@SuppressWarnings("unchecked")
			String sAbbreviation = EnumSetMappedUtilZZZ.getEnumConstant_AbbreviationValue((Class<IEnumSetMappedZZZ>) objClass, "ONE");
	    	assertTrue("Prüfstring solllte als Ergebnis '1' sein", "1".equals(sAbbreviation));
	    	
	    	@SuppressWarnings("unchecked")
			Integer intPosition = EnumSetMappedUtilZZZ.getEnumConstant_PositionValue((Class<IEnumSetMappedZZZ>) objClass, "ONE");
	    	assertEquals("Prüfinteger solllte als Ergebnis 1 sein", intPosition.intValue(),1);

	    	
	    	@SuppressWarnings("unchecked")
			Integer intIndex = EnumSetMappedUtilZZZ.getEnumConstant_IndexValue((Class<IEnumSetMappedZZZ>) objClass, "ONE");
	    	assertEquals("Prüfinteger solllte als Ergebnis 0 sein", intIndex.intValue(),0);
	    	assertEquals("Prüfinteger solllte als Ergebnis dem ordinal - Wert entsprechen", intIndex.intValue(), intOrdinal.intValue());
	    	assertEquals("Prüfinteger solllte als Ergebnis um 1 höher als der Index sein", intPosition.intValue(),intIndex.intValue()+1);	    	
	    }
	    
	
	}//end class
	
