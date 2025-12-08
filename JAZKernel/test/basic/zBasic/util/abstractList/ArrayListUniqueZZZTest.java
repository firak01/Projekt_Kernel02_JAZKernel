package basic.zBasic.util.abstractList;

import java.util.ArrayList;
import java.util.Iterator;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractList.ArrayListZZZ;
import basic.zBasic.util.abstractList.HashMapIndexedZZZ;

import junit.framework.TestCase;

public class ArrayListUniqueZZZTest extends TestCase{
	private ArrayListUniqueZZZ alTest = null; 
	 
    protected void setUp(){
      
	//try {			
		//### Die TestObjekte	
    	alTest = new ArrayListUniqueZZZ();			
	/*
	} catch (ExceptionZZZ ez) {
		fail("Method throws an exception." + ez.getMessageLast());
	} 
	*/
	
}//END setup
    
   public void testAdd() {
	   //try {
		   //Beim Unique Hinzufuegen duerfen keine Werte doppelt gespeichert werden.
		   alTest.add("Testwert A");  //0
		   alTest.add("Testwert B");  //1
		   alTest.add("Testwert C");  //2
		   alTest.add("Testwert A");  //3
		   alTest.add("Testwert A");  //4
		   alTest.add("Testwert D");  //5
		   alTest.add("Testwert C");  //6
		   
		   int iSize = alTest.size();
		   assertEquals("Beim Unique Hinzufuegen duerfen keine Werte doppelt gespeichert worden sein.",iSize, 4);

		   String sTest = alTest.debugString("\n");
		   System.out.println(sTest);
//   		} catch (ExceptionZZZ ez) {
//			fail("Method throws an exception." + ez.getMessageLast());
//		}    
   }
}
