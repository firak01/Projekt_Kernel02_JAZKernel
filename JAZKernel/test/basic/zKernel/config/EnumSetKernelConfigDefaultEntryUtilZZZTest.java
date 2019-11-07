package basic.zKernel.config;
import java.util.EnumSet;
import java.util.Set;

import junit.framework.TestCase;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractEnum.EnumSetTestFactoryZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetFactoryZZZ;
import basic.zBasic.util.abstractList.HashMapExtendedZZZ;
import basic.zBasic.util.datatype.enums.EnumSetMappedUtilZZZ;
import basic.zKernel.config.KernelConfigDefaultEntryZZZ.EnumConfigDefaultEntryZZZ;

public class EnumSetKernelConfigDefaultEntryUtilZZZTest  extends TestCase{
	 private HashMapExtendedZZZ<String, EnumConfigDefaultEntryZZZ> hmTestGenerics = null;
	 
	    protected void setUp(){
	      
		//try {			
		
			//### Das spezielle Generics Testobjekt			
			hmTestGenerics = new HashMapExtendedZZZ<String, EnumConfigDefaultEntryZZZ>();
			
			Set<EnumConfigDefaultEntryZZZ> allTypes = EnumSet.allOf(EnumConfigDefaultEntryZZZ.class);
			for(EnumConfigDefaultEntryZZZ myType : allTypes) {
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
				EnumSet setEnumGenerated = EnumConfigDefaultEntryZZZ.getEnumSet();		
				int iSize = setEnumGenerated.size();
				assertTrue("2 Elemente im Set erwartet.", iSize==2);
				
				//Generischer Zugriff, ist  nicht ganz möglich, darum Verwendung der Factory Klasse
				IEnumSetFactoryZZZ objFactory = EnumSetTestFactoryZZZ.getInstance();				
				Class objClass = EnumConfigDefaultEntryZZZ.class;
				EnumSetConfigDefaultEntryUtilZZZ enumSetUtil = new EnumSetConfigDefaultEntryUtilZZZ(objFactory, objClass);
				
				//Beim Wiederholten Zugriff über die Util-Klasse soll das einmal erstellte EnumSet wiederverwendet werden.	
				EnumSet setEnumReused = enumSetUtil.getEnumSetCurrent();
				assertNotNull(setEnumReused);
				
				int iSizeReused = setEnumReused.size();
				assertTrue("2 Elemente im SetReused erwartet.", iSizeReused==2);				
			
			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			} 
	    }
	    
//	    public void testStartsWith(){
//			try{
//				boolean btemp;
//				
//				//Merke: Varaussetzung ist: EnumSet wird von der EnumerationZZZ-Klasse schon "generisch/per Reflektion" erzeugt und mitgebracht werden. Alles basisert auf einer static Methode getEnumSet().
//
//				//Variante A) EnumSet selbst erzeugen
//				//Positivfall
//
//				EnumSet<EnumConfigDefaultEntryZZZ> setEnumCurrent = EnumSet.of(EnumConfigDefaultEntryZZZ.T01, EnumConfigDefaultEntryZZZ.T02);
//				assertNotNull(setEnumCurrent);
//				btemp = EnumSetConfigDefaultEntryUtilZZZ.startsWithAnyAlias("TESTVALUE01", setEnumCurrent);
//				assertTrue("A) Prüfstring sollte in der Enumeration vorhanden sein.", btemp);
//				
//				//Negativfall
//				EnumSet<EnumConfigDefaultEntryZZZ> setEnumCurrent02 = EnumSet.of(EnumConfigDefaultEntryZZZ.T01, numConfigDefaultEntryZZZ.T02);
//				assertNotNull(setEnumCurrent02);
//				btemp = EnumSetConfigDefaultEntryUtilZZZ.startsWithAnyAlias("Nixdaaa", setEnumCurrent02);
//				assertFalse("A) Prüfstring sollte in der Enumeration NICHT vorhanden sein .", btemp);
//				
//				//Variante B) EnumSet per statischer Methode holen
//				//Positivfall
//				EnumSet<?> setEnumGenerated = EnumConfigDefaultEntryZZZ.getEnumSet();
//				assertNotNull(setEnumGenerated);
//				btemp = EnumSetConfigDefaultEntryUtilZZZ.startsWithAnyAlias("TESTVALUE02", setEnumGenerated);
//				assertTrue("B) Prüfstring sollte in der Enumeration vorhanden sein.", btemp);
//				
//				//Negativvall
//				EnumSet<?> setEnumGenerated02 = EnumConfigDefaultEntryZZZ.getEnumSet();
//				assertNotNull(setEnumGenerated02);
//				btemp = EnumSetConfigDefaultEntryUtilZZZ.startsWithAnyAlias("NIXDA", setEnumGenerated02);
//				assertFalse("B) Prüfstring sollte in der Enumeration NICHT vorhanden sein.", btemp);
//				
//				//Variante C) direkter
//				//Generischer Zugriff, ist  nicht ganz möglich, darum Verwendung der Factory Klasse
//				IEnumSetFactoryZZZ objFactory = EnumSetTestFactoryZZZ.getInstance();	
//				//Class objClass = EnumSetDefaulttextUtilZZZ.class;  //TODO
//				Class objClass = EnumConfigDefaultEntryZZZ.class;
//				EnumSetConfigDefaultEntryUtilZZZ enumSetUtil = new EnumSetConfigDefaultEntryUtilZZZ(objFactory, objClass);
//				
//				
//				///FGL########## dann hören hier ggfs. die Geminsamkeiten mit der mapped... Struktur auf, heir haben wir Defaulttexte.
//				
//				//Positivfall
////				btemp = enumSetUtil.startsWithAnyShorttext("Test03");
////				assertTrue("C) Prüfstring sollte in der Enumeration vorhanden sein.", btemp);
////												
////				//Negativfall
////				btemp = enumSetUtil.startsWithAnyShorttext("XXXX");
////				assertFalse("C) Prüfstring sollte in der Enumeration NICHT vorhanden sein.", btemp);
//				
//			} catch (ExceptionZZZ ez) {
//				fail("Method throws an exception." + ez.getMessageLast());
//			} 
//		}    
//	    
//	    public void testGetEnumConstant(){
//	    	//Merke: Die Spezielle Enum Struktur unterscheidet sich dann ab einem bestimmten Punkt von der normalen Enum Struktur.
//	    	//           Das bedeutet, dass auch die Utility-Klasse ganz andere Methoden braucht.
//	    	
//	    	Class<?> objClass = EnumSetDefaulttextTestTypeTHM.class;
//	    	String sName = EnumSetConfigDefaultEntryUtilZZZ.readEnumConstant_NameValue(objClass, "TESTVALUE01");
//	    	assertTrue("Prüfstring solllte als Ergebnis 'TESTVALUE01' sein", "TESTVALUE01".equals(sName));
//	    	
//	    	String sString = EnumSetConfigDefaultEntryUtilZZZ.readEnumConstant_StringValue(objClass, "TESTVALUE01");
//	    	assertTrue("Prüfstring solllte als Ergebnis 'Test01' sein", "Test01".equals(sString));
//	    	
//	    	Integer intOrdinal = EnumSetConfigDefaultEntryUtilZZZ.readEnumConstant_OrdinalValue(objClass, "TESTVALUE01");
//	    	assertEquals("Prüfinteger solllte als Ergebnis 0 sein", intOrdinal.intValue(),0);
//	    	
//	    	//#### Dann hören hier ggfs. die Gemeinsamkeiten mit der mapped... Struktur auf, hier haben wir spezieller ausgeprägte Objekte.				    	
//	     	@SuppressWarnings("unchecked")
//			String sDescription = EnumSetConfigDefaultEntryUtilZZZ.readEnumConstant_DescriptionValue((Class<IEnumSetKernelConfigDefaultEntryZZZ>) objClass, "TESTVALUE01");
//	     	//String sDescription = EnumSetDefaulttextUtilZZZ.getEnumConstant_DescriptionValue((Class<E>)objClass, "TESTVALUE01");
//	    	assertTrue("Prüfstring solllte als Ergebnis 'A Test01 value description' sein", "A Test01 value description".equals(sDescription));
//	    		    	
//	    	@SuppressWarnings("unchecked")
//			Integer intPosition = EnumSetConfigDefaultEntryUtilZZZ.readEnumConstant_PositionValue((Class<IEnumSetKernelConfigDefaultEntryZZZ>) objClass, "TESTVALUE01");
//	    	assertEquals("Prüfinteger solllte als Ergebnis 1 sein", intPosition.intValue(),1);
//
//	    	
//	    	@SuppressWarnings("unchecked")
//			Integer intIndex = EnumSetConfigDefaultEntryUtilZZZ.readEnumConstant_IndexValue((Class<IEnumSetKernelConfigDefaultEntryZZZ>) objClass, "TESTVALUE01");
//	    	assertEquals("Prüfinteger solllte als Ergebnis 0 sein", intIndex.intValue(),0);
//	    	assertEquals("Prüfinteger solllte als Ergebnis dem ordinal - Wert entsprechen", intIndex.intValue(), intOrdinal.intValue());
//	    	assertEquals("Prüfinteger solllte als Ergebnis um 1 höher als der Index sein", intPosition.intValue(),intIndex.intValue()+1);	    	
//	    }
	    
	
	}//end class
