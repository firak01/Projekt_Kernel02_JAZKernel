package basic.zBasic.util.abstractList;

import java.util.Set;

import junit.framework.TestCase;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractList.HashMapMultiZZZ;

public class HashMapMultiIndexedZZZTest extends TestCase{
    private HashMapMultiIndexedZZZ hmTest = null;
	
    protected void setUp(){
      
	//try {			
		//### Die TestObjecte		
		hmTest=new HashMapMultiIndexedZZZ();
	
	/*
	} catch (ExceptionZZZ ez) {
		fail("Method throws an exception." + ez.getMessageLast());
	} 
	*/
	
}//END setup
    
    
public void testValueSetGet(){
	try{
		hmTest.clear();
		
		//Index prüfen
		int iIndexLast = hmTest.getIndexLast();
		assertEquals(-1,iIndexLast);
		int iIndexHigh = hmTest.getIndexHigh();
		assertEquals(-1,iIndexHigh);
		
		Integer intTest = new Integer(0);
		hmTest.put(intTest, "Alias1a", "Das ist ein Test");
		
		//Index prüfen
		iIndexLast = hmTest.getIndexLast();
		assertEquals(0,iIndexLast);
		iIndexHigh = hmTest.getIndexHigh();
		assertEquals(0,iIndexHigh);
		
				
		//Nun den Wert wieder auslesen
		String stemp = (String)hmTest.get(intTest, "Alias1a");
		assertEquals("Das ist ein Test", stemp);
		
		//Neue Werte hinzunehmen
		hmTest.put("Alias1b", "2. Zeile");
		hmTest.put( "Alias2a", "3. Zeile");
		hmTest.put("Alias2b", "4. Zeile");
		hmTest.put("Alias2c", "5. Zeile");
		
		//Index prüfen
		iIndexLast = hmTest.getIndexLast();
		assertEquals(4,iIndexLast);
		iIndexHigh = hmTest.getIndexHigh();
		assertEquals(4,iIndexHigh);
		
		//Nun den Wert wieder auslesen
		intTest = new Integer(iIndexHigh);
		stemp = (String)hmTest.get(intTest, "Alias2c");
		assertEquals("5. Zeile", stemp);
	
	} catch (ExceptionZZZ ez) {
		fail("Method throws an exception." + ez.getMessageLast());
	} 
}

public void testDebugString(){
	try{
		hmTest.clear();
		hmTest.put( "Alias1a", "Das ist ein Test");
		hmTest.put("Alias1b", "2. Zeile");
		hmTest.put( "Alias2a", "3. Zeile");
		hmTest.put("Alias2b", "4. Zeile");
		hmTest.put("Alias2c", "5. Zeile");
		
	
		String stemp = hmTest.debugString();
		assertFalse(stemp==null);
		assertFalse(stemp.equals(""));
		
		System.out.println(ReflectCodeZZZ.getPositionCurrent() + " Ausgabe des Debug-Strings. Merke: Sortiert nach 1. Spalte ist erwartet: ");
		System.out.println(stemp);
		
	} catch (ExceptionZZZ ez) {
		fail("Method throws an exception." + ez.getMessageLast());
	} 
}

public void testGetOuterKeySet(){
	try{
		hmTest.clear();
		hmTest.put( "Alias1a", "Das ist ein Test");
		hmTest.put( "Alias1b", "2. Zeile");
		hmTest.put("Alias2a", "3. Zeile");
		hmTest.put( "Alias2b", "4. Zeile");
		hmTest.put( "Alias2c", "5. Zeile");
		
		Set<String> setKeyOuter = (Set<String>) hmTest.getOuterKeySet();
		assertTrue(setKeyOuter.size()==5);//Für jeden Eintrag wird ein neuer Äusserer Key mit dem Integer Wert gemacht.
		
		
	} catch (ExceptionZZZ ez) {
		fail("Method throws an exception." + ez.getMessageLast());
	}
}
	
	public void testGetInnerKeySet(){
		try{
			hmTest.clear();
			hmTest.put("Alias1a", "Das ist ein Test");
			hmTest.put("Alias1b", "2. Zeile");
			hmTest.put("Alias2a", "3. Zeile");
			hmTest.put("Alias2b", "4. Zeile");
			hmTest.put("Alias2c", "5. Zeile");
			
			
			Integer intTest = new Integer(0);
			Set<String> setKeyInner = (Set<String>) hmTest.getInnerKeySet(intTest);
			assertTrue(setKeyInner.size()==1);
			
			//Index prüfen
			int iIndexLast = hmTest.getIndexLast();
			assertEquals(4,iIndexLast);
			int iIndexHigh = hmTest.getIndexHigh();
			assertEquals(4,iIndexHigh);
			
			Integer intTest02 = new Integer(iIndexLast);
			Set<String> setKeyInner02 = (Set<String>) hmTest.getInnerKeySet(intTest02);
			assertTrue(setKeyInner02.size()==1);
			
			
			
			
			
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}
}
  
}//END Class

