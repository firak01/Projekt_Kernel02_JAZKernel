package basic.zBasic.util.datatype.enums;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Set;

import junit.framework.TestCase;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractEnum.EnumSetTestFactoryZZZ;
import basic.zBasic.util.abstractEnum.EnumSetMappedStatusTestTypeZZZ;
import basic.zBasic.util.abstractEnum.EnumSetMappedTestTypeZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetFactoryZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedStatusZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedZZZ;
import basic.zBasic.util.abstractList.HashMapZZZ;
import basic.zBasic.util.datatype.enums.EnumSetMappedUtilZZZ;

public class EnumSetMappedUtilZZZTest  extends TestCase{
	 private HashMapZZZ<String, EnumSetMappedTestTypeZZZ> hmTestGenerics = null;
	 
	    protected void setUp(){
	      
		//try {			
		
			//### Das spezielle Generics Testobjekt			
			hmTestGenerics = new HashMapZZZ<String, EnumSetMappedTestTypeZZZ>();
			
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
				IEnumSetFactoryZZZ objFactory = EnumSetTestFactoryZZZ.getInstance();				
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
				IEnumSetFactoryZZZ objFactory = EnumSetTestFactoryZZZ.getInstance();	
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
	    	try {
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
	    	
	    	}catch(ExceptionZZZ ez) {
	    		ez.printStackTrace();
	    		fail("Method throws an exception." + ez.getMessageLast());
	    	}
	    }
	    
	    
    public void testToEnumArray() {	    	
		//Merke: Intern wird dann eine Fallunterscheidung nach dem in der Liste gespcicherten Datentyp gemacht.
	    //       Aufgerufen wird dann ...Mapped..., bzw. ...MappedStatus..., etc.
	
    	//###################################
 	   //### 0. Variante: Typ ist nicht vorhanden ...
 	   //####################################
//    	try { 	        	  	   
 	  	   //Eine Dummy-Liste ohne Enum...
 	  	   ArrayList<String>listaDummy = new ArrayList<String>();
 	  	   listaDummy.add("One");
 	  	   listaDummy.add("Two");
 	  	   listaDummy.add("Three");
 	  	    	  	   
 	  	   //Der eigentliche Test, hier soll ein Fehler geworfen werden.
 	  	   try {
 	  		   Enum[] enumaMapped = EnumSetMappedUtilZZZ.toEnumMappedArray(listaDummy);
 	  		   fail("Method should have thrown an exception.");
	 	   } catch (ExceptionZZZ ez) {
	 		   //Ein Fehler soll passieren....
	 		   System.out.println("Expected Exception: " + ez.getDetailAllLast());;	  			
	 	   } 
 	  	   
 	  	   		   
// 	    } catch (ExceptionZZZ ez) {
// 			fail("Method throws an exception." + ez.getMessageLast());
// 		} 		
     	
    	
    	
	   //###################################
	   //### 1. Variante: Ohne Status ...
	   //####################################
    	try {
	       Class<?> objClass = EnumSetMappedTestTypeZZZ.class;
	  	   Object[] obja = objClass.getEnumConstants();
	  	   
	  	   Enum[] objaEnum = (Enum[]) obja;
	  	   
	  	   //Als Vorbereitung: Das Array in eine Listenform bringen.
	  	   ArrayList<IEnumSetMappedZZZ>listaenumMapped = EnumSetMappedUtilZZZ.toEnumMappedArrayList(objaEnum);
	  	   assertNotNull("Fehler in der Vorbereitung - kann keine passende Arraylist erstellen (null-Fall)",listaenumMapped);
	  	   assertFalse("Fehler in der Vorbereitung - kann keine passende Arraylist erstellen (empty-Fall)",listaenumMapped.isEmpty());
	  	   
	  	   //Der eigentliche Test
	  	   Enum[] enumaMapped = EnumSetMappedUtilZZZ.toEnumMappedArray(listaenumMapped);
	  	   assertNotNull(enumaMapped);
	  	   assertTrue(enumaMapped.length==3);
		  	
	  	   assertFalse(enumaMapped instanceof IEnumSetMappedZZZ[]);//weil eben enum
	  	   assertTrue(enumaMapped instanceof Enum[]); //bingo
		   assertFalse(enumaMapped[0] instanceof IEnumSetMappedStatusZZZ);//weil EnumSetMappedTestType das IEnumSetMappdedZZZ implementiert und nicht IEnumSetMappdedStatusZZZ
		   assertTrue(enumaMapped[0] instanceof IEnumSetMappedZZZ);
		   
	    } catch (ExceptionZZZ ez) {
	    	ez.printStackTrace();
			fail("Method throws an exception." + ez.getMessageLast());
		} 		
		   
			   
	   //###################################
	   //### 2. Variante: Mit Status ...
	   //####################################
		try {
			   Class<?> objClassStatus = EnumSetMappedStatusTestTypeZZZ.class;
		  	   Object[] objaStatus = objClassStatus.getEnumConstants();
		  	   
		  	   Enum[] objaEnumStatus = (Enum[]) objaStatus;
		  	   
		  	   //Als Vorbereitung: Das Array in eine Listenform bringen.
		  	   ArrayList<IEnumSetMappedStatusZZZ>listaenumMappedStatus = EnumSetMappedUtilZZZ.toEnumMappedStatusArrayList(objaEnumStatus);
		  	   assertNotNull("Fehler in der Vorbereitung - kann keine passende Arraylist erstellen (null-Fall)",listaenumMappedStatus);
		  	   assertFalse("Fehler in der Vorbereitung - kann keine passende Arraylist erstellen (empty-Fall)",listaenumMappedStatus.isEmpty());
		  	   
		  	   //Der eigentliche Test
		  	   //Enum[] enumaMappedStatus = EnumSetMappedUtilZZZ.toEnumArrayByMappedStatus(listaenumMappedStatus);
		  	   Enum[] enumaMappedStatus = EnumSetMappedUtilZZZ.toEnumMappedArray(listaenumMappedStatus);
		  	   assertNotNull(enumaMappedStatus);
		  	   assertTrue(enumaMappedStatus.length==3);
			  	
		  	   assertFalse(enumaMappedStatus instanceof IEnumSetMappedStatusZZZ[]);//weil eben enum
		  	   assertTrue(enumaMappedStatus instanceof Enum[]); //bingo
			   assertTrue(enumaMappedStatus[0] instanceof IEnumSetMappedZZZ);//weil EnumSetMappedStatusTestType das IEnumSetMappdedStatusZZZ implementiert dies wiederum aus IEnumSetMappdedZZZ erbt.
			   assertTrue(enumaMappedStatus[0] instanceof IEnumSetMappedStatusZZZ);//weil EnumSetMappedStatusTestType das IEnumSetMappdedStatusZZZ implementiert und nicht IEnumSetMappdedZZZ  
		
			   
	    } catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		} 			
	 }
	  
    public void testToEnumArrayByMapped() {
    	//###################################
		//### 1. Variante: Ohne Status ...
		//####################################
	    Class<?> objClass = EnumSetMappedTestTypeZZZ.class;
	  	Object[] obja = objClass.getEnumConstants();
	  	   
	  	Enum[] objaEnum = (Enum[]) obja;
	  	   
	  	//Als Vorbereitung: Das Array in eine Listenform bringen.
	  	ArrayList<IEnumSetMappedZZZ>listaenumMapped = EnumSetMappedUtilZZZ.toEnumMappedArrayList(objaEnum);
	  	assertNotNull("Fehler in der Vorbereitung - kann keine passende Arraylist erstellen (null-Fall)",listaenumMapped);
	  	assertFalse("Fehler in der Vorbereitung - kann keine passende Arraylist erstellen (empty-Fall)",listaenumMapped.isEmpty());
	  	   
	  	//Der eigentliche Test
	  	Enum[] enumaMapped = EnumSetMappedUtilZZZ.toEnumArrayByMapped(listaenumMapped);
	  	assertNotNull(enumaMapped);
	  	assertTrue(enumaMapped.length==3);
		  	
	  	assertFalse(enumaMapped instanceof IEnumSetMappedZZZ[]);//weil eben enum
	  	assertTrue(enumaMapped instanceof Enum[]); //bingo
		assertFalse(enumaMapped[0] instanceof IEnumSetMappedStatusZZZ);//weil EnumSetMappedTestType das IEnumSetMappdedZZZ implementiert und nicht IEnumSetMappdedStatusZZZ  
		assertTrue(enumaMapped[0] instanceof IEnumSetMappedZZZ);		
	}	   
    
    public void testToEnumArrayByMappedStatus() {	    		    	
    	//###################################
		//### 2. Variante: Mit Status ...
		//####################################
		Class<?> objClassStatus = EnumSetMappedStatusTestTypeZZZ.class;
	  	Object[] objaStatus = objClassStatus.getEnumConstants();
	  	  
	  	Enum[] objaEnumStatus = (Enum[]) objaStatus;
	  	   
	  	//Als Vorbereitung: Das Array in eine Listenform bringen.
	  	ArrayList<IEnumSetMappedStatusZZZ>listaenumMappedStatus = EnumSetMappedUtilZZZ.toEnumMappedStatusArrayList(objaEnumStatus);
	  	assertNotNull("Fehler in der Vorbereitung - kann keine passende Arraylist erstellen (null-Fall)",listaenumMappedStatus);
	  	assertFalse("Fehler in der Vorbereitung - kann keine passende Arraylist erstellen (empty-Fall)",listaenumMappedStatus.isEmpty());
	  	
	  	//Der eigentliche Test
	  	Enum[] enumaMappedStatus = EnumSetMappedUtilZZZ.toEnumArrayByMappedStatus(listaenumMappedStatus);
	  	assertNotNull(enumaMappedStatus);
	  	assertTrue(enumaMappedStatus.length==3);
			
	  	assertFalse(enumaMappedStatus instanceof IEnumSetMappedStatusZZZ[]);//weil eben enum
	  	assertTrue(enumaMappedStatus instanceof Enum[]); //bingo
		assertTrue(enumaMappedStatus[0] instanceof IEnumSetMappedZZZ);//weil EnumSetMappedStatusTestType das IEnumSetMappdedStatusZZZ implementiert dies wiederum aus IEnumSetMappdedZZZ erbt.
		assertTrue(enumaMappedStatus[0] instanceof IEnumSetMappedStatusZZZ);//weil EnumSetMappedStatusTestType das IEnumSetMappdedStatusZZZ implementiert und nicht IEnumSetMappdedZZZ  	   
    }
    
//Es darf kein Array mit Interface zurueckgegeben werden, s. ChatGPT 20260110	
//    public void testToEnumMappedArray() {	    		    	
//       Class<?> objClass = EnumSetMappedTestTypeZZZ.class;
//  	   Object[] obja = objClass.getEnumConstants();
//  	   
//  	   Enum[] objaEnum = (Enum[]) obja;
//  	   
//  	   IEnumSetMappedZZZ[] enumaMapped = EnumSetMappedUtilZZZ.toEnumMappedArray(objaEnum);
//  	   assertNotNull(enumaMapped);
//  	   assertTrue(enumaMapped.length==3);
//	  		  	   
//	   assertFalse(enumaMapped[0] instanceof IEnumSetMappedStatusZZZ);
//	   assertTrue(enumaMapped[0] instanceof IEnumSetMappedZZZ);	  	  
//	}
    
    public void testToEnumMappedArrayLists() {	    		    	
       Class<?> objClass = EnumSetMappedTestTypeZZZ.class;
  	   Object[] obja = objClass.getEnumConstants();
  	   
  	   Enum[] objaEnum = (Enum[]) obja;
  	   
  	   ArrayList<IEnumSetMappedZZZ> listae = EnumSetMappedUtilZZZ.toEnumMappedArrayList(objaEnum);
  	   assertNotNull(listae);
  	   assertTrue(listae.size()==3);
	   			   
	   assertFalse(listae.get(0) instanceof IEnumSetMappedStatusZZZ);
	   assertTrue(listae.get(0) instanceof IEnumSetMappedZZZ);		  
	}
    
	    
	    
	    
    public void testToEnumMappedStatusArrayLists() {	    	    	
       Class<?> objClass = EnumSetMappedTestTypeZZZ.class;
  	   Object[] obja = objClass.getEnumConstants();
  	   
  	   Enum[] objaEnum = (Enum[]) obja;
  	   
  	   ArrayList<IEnumSetMappedStatusZZZ> listae = EnumSetMappedUtilZZZ.toEnumMappedStatusArrayList(objaEnum);
  	   assertNull(listae); //Da der Datentyp IEnumSetMappedZZZ /aus dem ...TestTypeZZZ-Objekt) nicht mit IEnumSetMappedStatusZZZ castfaehig ist.		  	   
	}
}//end class
	
