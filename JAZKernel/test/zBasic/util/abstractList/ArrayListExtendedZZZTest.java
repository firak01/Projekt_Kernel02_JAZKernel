package zBasic.util.abstractList;

import java.util.ArrayList;
import java.util.Iterator;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractList.ArrayListExtendedZZZ;
import basic.zBasic.util.abstractList.HashMapIndexedZZZ;

import junit.framework.TestCase;

public class ArrayListExtendedZZZTest extends TestCase{
	private ArrayListExtendedZZZ alTest = null; 
	 
    protected void setUp(){
      
	//try {			
		//### Die TestObjecte	
    	alTest = new ArrayListExtendedZZZ();
    	alTest.add("Testwert A");  //0
		alTest.add("Testwert B");  //1
		alTest.add("Testwert C");  //2
		alTest.add("Testwert A");  //3
		alTest.add("Testwert A");    //4
		alTest.add("Testwert D");   //5
		alTest.add("Testwert C");   //6
    				
	/*
	} catch (ExceptionZZZ ez) {
		fail("Method throws an exception." + ez.getMessageLast());
	} 
	*/
	
}//END setup
    
   
    
    public void testGetValueDupsIndexedByMethod(){
    	try{
    		
    		HashMapIndexedZZZ hmIndexed = alTest.getValueDupsIndexedByMethod("toString");
    		int itemp = hmIndexed.size();
    		assertEquals("Es wurden 5 doppelte Objekte erwartet.",5, itemp);  //3 mal Testfall A, 2 mal Testfall C
    		
    		boolean btemp = hmIndexed.checkStructure(true);
    		assertTrue("Die Struktur ist nicht g�ltig", btemp);
    		
    		int ipos=-1;
    		Iterator itIndexed = hmIndexed.keySet().iterator();
    		while(itIndexed.hasNext()){
    			ipos++;
    			Object objKey = itIndexed.next();
    			ArrayList alIndexed = (ArrayList) hmIndexed.get(objKey);
    			
    			itemp = alIndexed.size();
    			assertTrue("Es wurden zwei oder mehr Objektindizes erwartet. Position " + ipos, itemp>=2);  //wg. der Struktur
    			assertTrue("Es wurden in dem Test maximal drei Objektindizes erwartet.Poition " + ipos, itemp <=3 ); //wg. des Testbeispiels hmTest.
    		
    		}
    		
    	} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		} 
    }
    
    public void testRemoveFromDups(){
		try{
			int itemp;
		
			//+++ PR�FOBJEKT 1)
			ArrayListExtendedZZZ alTest1 = (ArrayListExtendedZZZ) alTest.clone();
			
			//hmIndexed  jedesmal neu errechnen, da die Indexreihenfolge einem Internen Algorithmus entspricht und nicht so ist, wie notiert.
			HashMapIndexedZZZ hmIndexed1 = alTest1.getValueDupsIndexedByMethod("toString"); //Eintr�ge, wichtig ist nur, dass die Werte trotz unterschiedlicher Keys an den Stellen (s. hmTest) identisch sind.
			assertEquals("Es wurde erwartet, das 5 doppelte Objekte existieren.",5, hmIndexed1.size());
			int iStartSize = alTest1.size();   
			
			/* TODO GOON
			//TESTFALL: Behalte die letzten Eintr�ge
			itemp =HashMapIndexedZZZ.removeDupsFromByIndex(hmTest1, hmIndexed1, "KEEPLAST");
			assertEquals("Es wurde erwartet, das 3 Objekte entfernt wurden.",3, itemp);
			assertEquals("Es wurde erwartet, das die Hashmap nun " + itemp + "  Objekte weniger enth�lt.",iStartSize-itemp, hmTest1.size());
		    Iterator it = hmTest1.keySet().iterator();
		    System.out.println("Ergebnis nach dem Entfernen der Doppelten..., es sollten die letzten �brig bleiben");
		    while(it.hasNext()){
		    	Object obj = it.next();
		    	System.out.println("Key: " + obj.toString() + " | Wert: " + (Object)( hmTest1.get(obj)).toString());
		    }
		    
		    //Jetzt soll es keine doppelten Objekte mehr geben.
			HashMapIndexedZZZ hmIndexed = HashMapIndexedZZZ.getValueDupsIndexedByMethod(hmTest1, "toString"); //Eintr�ge, wichtig ist nur, dass die Werte trotz unterschiedlicher Keys an den Stellen (s. hmTest) identisch sind.
			assertEquals("Es wurde erwartet, das keine doppelte Objekte existieren.",0, hmIndexed.size());
		    
		    //++++ PR�FOBJEKT 2)
			HashMap hmTest2 = (HashMap) hmTest.clone();
			
//			hmIndexed  jede mal neu errechnen, da die Indexreihenfolge einem Internen Algorithmus entspricht und nicht so ist, wie notiert.
			HashMapIndexedZZZ hmIndexed2 = HashMapIndexedZZZ.getValueDupsIndexedByMethod(hmTest2, "toString"); //Eintr�ge, wichtig ist nur, dass die Werte trotz unterschiedlicher Keys an den Stellen (s. hmTest) identisch sind.
			assertEquals("Es wurde erwartet, das 5 doppelte Objekte existieren.",5, hmIndexed2.size());
			iStartSize = hmTest2.size();   
		    
//		  TESTFALL: Behalte die ersten Eintr�ge
			itemp =HashMapIndexedZZZ.removeDupsFromByIndex(hmTest2, hmIndexed2, "KEEPFIRST");
			assertEquals("Es wurde erwartet, das 3 Objekte entfernt wurden.",3, itemp);
			assertEquals("Es wurde erwartet, das die Hashmap nun " + itemp + "  Objekte weniger enth�lt.",iStartSize-itemp, hmTest2.size());
		    it = hmTest2.keySet().iterator();
		    System.out.println("\nErgebnis nach dem Entfernen der Doppelten..., es sollten die ersten �brig bleiben");
		    while(it.hasNext()){
		    	Object obj = it.next(); 
		    	System.out.println("Key: " + obj.toString() + " | Wert: " + (Object)( hmTest2.get(obj)).toString());
		    }
		    
		    //Jetzt soll es keine doppelten Objekte mehr geben.
			hmIndexed = HashMapIndexedZZZ.getValueDupsIndexedByMethod(hmTest2, "toString"); //Eintr�ge, wichtig ist nur, dass die Werte trotz unterschiedlicher Keys an den Stellen (s. hmTest) identisch sind.
			assertEquals("Es wurde erwartet, das keine doppelte Objekte existieren.",0, hmIndexed.size());
		    */
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		} 
	}    
}
