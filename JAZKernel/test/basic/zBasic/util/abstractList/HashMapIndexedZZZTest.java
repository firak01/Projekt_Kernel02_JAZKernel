package basic.zBasic.util.abstractList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import junit.framework.TestCase;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractList.HashMapIndexedZZZ;

public class HashMapIndexedZZZTest extends TestCase{
	private HashMap hmTest = null; 
	private ArrayList alTest = null;
		 
	    protected void setUp(){
	      
		//try {			
			//### Die TestObjecte	
	    	hmTest = new HashMap();
	    	hmTest.put("A", "Testwert A");  //0
			hmTest.put("B", "Testwert B");  //1
			hmTest.put("C", "Testwert C");  //2
			hmTest.put("D", "Testwert A");  //3
			hmTest.put("E", "Testwert A");    //4
			hmTest.put("F", "Testwert D");   //5
			hmTest.put("G", "Testwert C");   //6
			
			
//			### Die TestObjecte	
	    	alTest = new ArrayList();
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
	    
	    public void testCheckStructure(){
			try{
				boolean btemp;
				HashMapIndexedZZZ hmIndexed = null;
				hmIndexed=new HashMapIndexedZZZ();
				
				Integer inta[] = new Integer[10];
				for(int icount=0; icount < 10; icount++){
					Integer objInt = new Integer(icount);
					inta[icount] = objInt;
				}

				ArrayList alA = new ArrayList();
				alA.add(inta[0]);
				alA.add(inta[3]);
				alA.add(inta[4]);
				
				ArrayList alC = new ArrayList();
				alC.add(inta[2]);
				alC.add(inta[6]);
				
				
				
				hmIndexed.put(inta[0],alA);
				hmIndexed.put(inta[2], alC);
				//+++ PR�FOBJEKT 1)
				
				//Struktur g�ltig
				btemp =HashMapIndexedZZZ.checkStructure(hmIndexed, false);
				assertTrue("Testobjekt aus dem Setup sollte gueltig strukturiert sein.", btemp);
			
			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			} 
		}    
	    
	    public void testGetValueDupsIndexedByMethod(){
	    	try{
	    		
	    		//A) HASHMAP
	    		HashMapIndexedZZZ hmIndexedA = HashMapIndexedZZZ.getValueDupsIndexedByMethod(hmTest, "toString");
	    		int itemp = hmIndexedA.size();
	    		assertEquals("(A) Es wurden 5 doppelte Objekte erwartet.",5, itemp);  //3 mal Testfall A, 2 mal Testfall C
	    		
	    		boolean btemp = hmIndexedA.checkStructure(true);
	    		assertTrue("(A) Die Struktur ist nicht g�ltig", btemp);
	    		
	    		int ipos=-1;
	    		Iterator itIndexed = hmIndexedA.keySet().iterator();
	    		while(itIndexed.hasNext()){
	    			ipos++;
	    			Object objKey = itIndexed.next();
	    			ArrayList alIndexed = (ArrayList) hmIndexedA.get(objKey);
	    			
	    			itemp = alIndexed.size();
	    			assertTrue("(A) Es wurden zwei oder mehr Objektindizes erwartet. Position " + ipos, itemp>=2);  //wg. der Struktur
	    			assertTrue("(A) Es wurden in dem Test maximal drei Objektindizes erwartet.Poition " + ipos, itemp <=3 ); //wg. des Testbeispiels hmTest.
	    			
	    			
	    		}
	    		
	    		//B) ARRAYLIST
	    		HashMapIndexedZZZ hmIndexedB = HashMapIndexedZZZ.getValueDupsIndexedByMethod(alTest, "toString");
	    		itemp = hmIndexedB.size();
	    		assertEquals("(B) Es wurden 5 doppelte Objekte erwartet.",5, itemp);  //3 mal Testfall A, 2 mal Testfall C
	    		
	    		 btemp = hmIndexedB.checkStructure(true);
	    		assertTrue("(B) Die Struktur ist nicht g�ltig", btemp);
	    		
	    		 ipos=-1;
	    		 itIndexed = hmIndexedB.keySet().iterator();
	    		while(itIndexed.hasNext()){
	    			ipos++;
	    			Object objKey = itIndexed.next();
	    			ArrayList alIndexed = (ArrayList) hmIndexedB.get(objKey);
	    			
	    			itemp = alIndexed.size();
	    			assertTrue("(B) Es wurden zwei oder mehr Objektindizes erwartet. Position " + ipos, itemp>=2);  //wg. der Struktur
	    			assertTrue("(B) Es wurden in dem Test maximal drei Objektindizes erwartet.Poition " + ipos, itemp <=3 ); //wg. des Testbeispiels hmTest.
	    			
	    			
	    		}
	    		
	    	} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			} 
	    }
	    
	    public void testRemoveFromDups(){
			try{
				int itemp;
				//########################################################################
                //VERION A) HASHMAP			
				//+++ PRUEFOBJEKT 1)
				HashMap hmTest1 = (HashMap) hmTest.clone();
				
				//hmIndexed  jedesmal neu errechnen, da die Indexreihenfolge einem Internen Algorithmus entspricht und nicht so ist, wie notiert.
				HashMapIndexedZZZ hmIndexed1 = HashMapIndexedZZZ.getValueDupsIndexedByMethod(hmTest1, "toString"); //Eintr�ge, wichtig ist nur, dass die Werte trotz unterschiedlicher Keys an den Stellen (s. hmTest) identisch sind.
				assertEquals("Es wurde erwartet, das 5 doppelte Objekte existieren.",5, hmIndexed1.size());
				int iStartSize = hmTest1.size();   
				
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
				
//				hmIndexed  jede mal neu errechnen, da die Indexreihenfolge einem Internen Algorithmus entspricht und nicht so ist, wie notiert.
				HashMapIndexedZZZ hmIndexed2 = HashMapIndexedZZZ.getValueDupsIndexedByMethod(hmTest2, "toString"); //Eintr�ge, wichtig ist nur, dass die Werte trotz unterschiedlicher Keys an den Stellen (s. hmTest) identisch sind.
				assertEquals("Es wurde erwartet, das 5 doppelte Objekte existieren.",5, hmIndexed2.size());
				iStartSize = hmTest2.size();   
			    
//			  TESTFALL: Behalte die ersten Eintr�ge
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
				
				
				//####################################################################
				//VERSION B) ARRAYLIST
				
				//+++ PR�FOBJEKT 1)
				ArrayList alTest1 = (ArrayList) alTest.clone();
				
				//hmIndexed  jedesmal neu errechnen, da die Indexreihenfolge einem Internen Algorithmus entspricht und nicht so ist, wie notiert.
				HashMapIndexedZZZ hmIndexed1B = HashMapIndexedZZZ.getValueDupsIndexedByMethod(alTest1, "toString"); //Eintr�ge, wichtig ist nur, dass die Werte trotz unterschiedlicher Keys an den Stellen (s. hmTest) identisch sind.
				assertEquals("Es wurde erwartet, das 5 doppelte Objekte existieren.",5, hmIndexed1B.size());
				iStartSize = alTest1.size();   
				
				//TESTFALL: Behalte die letzten Eintr�ge
				itemp =HashMapIndexedZZZ.removeDupsFromByIndex(alTest1, hmIndexed1B, "KEEPLAST");
				assertEquals("Es wurde erwartet, das 3 Objekte entfernt wurden.",3, itemp);
				assertEquals("Es wurde erwartet, das die Hashmap nun " + itemp + "  Objekte weniger enth�lt.",iStartSize-itemp, alTest1.size());
			    it = alTest1.iterator();
			    System.out.println("Ergebnis nach dem Entfernen der Doppelten..., es sollten die letzten �brig bleiben");
			    while(it.hasNext()){
			    	Object obj = it.next();
			    	System.out.println(" Wert: " + obj.toString());
			    }
			    
			    //Jetzt soll es keine doppelten Objekte mehr geben.
				hmIndexed = HashMapIndexedZZZ.getValueDupsIndexedByMethod(alTest1, "toString"); //Eintr�ge, wichtig ist nur, dass die Werte trotz unterschiedlicher Keys an den Stellen (s. hmTest) identisch sind.
				assertEquals("Es wurde erwartet, das keine doppelte Objekte existieren.",0, hmIndexed.size());
			    
				
				//+++ PR�FOBJEKT 2)
				ArrayList alTest2 = (ArrayList) alTest.clone();
				
				//hmIndexed  jedesmal neu errechnen, da die Indexreihenfolge einem Internen Algorithmus entspricht und nicht so ist, wie notiert.
				HashMapIndexedZZZ hmIndexed2B = HashMapIndexedZZZ.getValueDupsIndexedByMethod(alTest2, "toString"); //Eintr�ge, wichtig ist nur, dass die Werte trotz unterschiedlicher Keys an den Stellen (s. hmTest) identisch sind.
				assertEquals("Es wurde erwartet, das 5 doppelte Objekte existieren.",5, hmIndexed2B.size());
				iStartSize = alTest2.size();   
				
				//TESTFALL: Behalte die ersten Eintr�ge
				itemp =HashMapIndexedZZZ.removeDupsFromByIndex(alTest2, hmIndexed2B, "KEEPFIRST");
				assertEquals("Es wurde erwartet, das 3 Objekte entfernt wurden.",3, itemp);
				assertEquals("Es wurde erwartet, das die Hashmap nun " + itemp + "  Objekte weniger enth�lt.",iStartSize-itemp, alTest2.size());
			    it = alTest2.iterator();
			    System.out.println("Ergebnis nach dem Entfernen der Doppelten..., es sollten die letzten �brig bleiben");
			    while(it.hasNext()){
			    	Object obj = it.next();
			    	System.out.println(" Wert: " + obj.toString());
			    }
			    
			    //Jetzt soll es keine doppelten Objekte mehr geben.
				hmIndexed = HashMapIndexedZZZ.getValueDupsIndexedByMethod(alTest2, "toString"); //Eintr�ge, wichtig ist nur, dass die Werte trotz unterschiedlicher Keys an den Stellen (s. hmTest) identisch sind.
				assertEquals("Es wurde erwartet, das keine doppelte Objekte existieren.",0, hmIndexed.size());
			    
			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			} 
		}    
}
