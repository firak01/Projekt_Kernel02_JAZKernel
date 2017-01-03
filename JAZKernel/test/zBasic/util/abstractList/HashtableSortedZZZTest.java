package zBasic.util.abstractList;

import java.util.Hashtable;
import java.util.TreeMap;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractList.HashtableSortedZZZ;
import junit.framework.TestCase;

public class HashtableSortedZZZTest extends TestCase{
    private Hashtable objHt = null;
	private HashtableSortedZZZ objHtSortedInit;
	private HashtableSortedZZZ objHtSortedTest;
	private HashtableSortedZZZ objHtSortedReverseTest;
    
    protected void setUp(){
    
	try {			
		objHt=new Hashtable();
		objHt.put("y","valuey");
		objHt.put("c","valuec");
		objHt.put("z","a valuez");   //d.h. die Werte sind nicht alphabetisch sortiert
		objHt.put("w", "valuew");
		objHt.put("b", "valueb");
		objHt.put("x", "a valuex");
		objHt.put("a", "valuea");
		
		//### Die TestObjecte		
		//An object just initialized, only for writing
		objHtSortedInit = new HashtableSortedZZZ();
		
		//The main objects used for testing
		objHtSortedReverseTest = new HashtableSortedZZZ(objHt, "-");
		objHtSortedTest = new HashtableSortedZZZ(objHt);
	
	} catch (ExceptionZZZ ez) {
		fail("Method throws an exception." + ez.getMessageLast());
	} 
	
}//END setup

    /** void, Test: .getKeyFirst(), .getKeyLast(), .getValueFirst(), .getValueLast()
    * Lindhauer; 27.04.2006 11:45:07
     */
    public void testGetMarginAll(){
    	String stemp = (String)objHtSortedReverseTest.getKeyFirst();
    	assertNotNull("There should be a string in this test fixture-object", stemp);
    	assertEquals("z", stemp);
    	    	
    	String stemp2 = (String)objHtSortedReverseTest.getKeyLast();
    	assertNotNull("There should be a string in this test fixture-object", stemp2);
    	assertEquals("a", stemp2);
    	
    	String stemp3 = (String)objHtSortedReverseTest.getValueFirst();
    	assertNotNull("There should be a string in this test fixture-object", stemp3);
    	assertEquals("a valuez", stemp3);
    	
    	String stemp4 = (String)objHtSortedReverseTest.getValueLast();
    	assertNotNull("There should be a string in this test fixture-object", stemp4);
    	assertEquals("valuea", stemp4);    	
    	
    	
    	
    	//+++ Nun die aufsteigend sortierten
    	String stemp5 = (String)objHtSortedTest.getKeyFirst();
    	assertNotNull("There should be a string in this test fixture-object", stemp5);
    	assertEquals("a", stemp5);
    	    	
    	String stemp6 = (String)objHtSortedTest.getKeyLast();
    	assertNotNull("There should be a string in this test fixture-object", stemp6);
    	assertEquals("z", stemp6);
    	
    	String stemp7 = (String)objHtSortedTest.getValueFirst();
    	assertNotNull("There should be a string in this test fixture-object", stemp7);
    	assertEquals("valuea", stemp7);
    	
    	String stemp8 = (String)objHtSortedTest.getValueLast();
    	assertNotNull("There should be a string in this test fixture-object", stemp8);
    	assertEquals("a valuez", stemp8);   
    }

/** void, Test> .getKeyNext(), getValueNext()
* Lindhauer; 27.04.2006 14:10:51
 */
public void testGetNext(){
	//MERKE: JUnit baut das Test-Objekt f�r jeden Test neu auf
	String stemp = (String)objHtSortedReverseTest.getKeyNext();
	assertNotNull("There should be a string in this test fixture-object", stemp);
	assertEquals("z", stemp);
	
	String stemp2 = (String)objHtSortedReverseTest.getKeyNext();
	assertNotNull("There should be a string in this test fixture-object", stemp2);
	assertEquals("y", stemp2);
	
	
	String stemp3 = (String)objHtSortedReverseTest.getKeyNext();
	assertNotNull("There should be a string in this test fixture-object", stemp3);
	assertEquals("x", stemp3);
	
	String stemp4 = (String) objHtSortedReverseTest.getValueNext();
	assertNotNull("There should be a string in this test fixture-object", stemp4);
	assertEquals("valuew", stemp4);
	
}

public void testGetValue(){
	String stemp = (String) objHtSortedTest.getValue("a");
	assertNotNull("There should be a string in this test fixture-object", stemp);
	assertEquals("valuea", stemp);
	
	String stemp2 = (String) objHtSortedTest.getValue("z");
	assertNotNull("There should be a string in this test fixture-object", stemp2);
	assertEquals("a valuez", stemp2);

}

public void testGetTreeMap(){
	TreeMap objTm = objHtSortedTest.getTreeMap();
	
	Object objTest1 = objHtSortedTest.getKeyFirst();
	assertNotNull("There should be a string in this test fixture-object", objTest1);
	
	Object objTest2 = objTm.firstKey();
	assertNotNull("There should be an object in the treemap", objTest2);
	assertEquals(objTest1, objTest2);
	
	
}
}//END Class
