package basic.zBasic.util.abstractList;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import basic.zBasic.ExceptionZZZ;
import junit.framework.TestCase;

public class HashMapZZZTest extends TestCase{
		 private Map<String,Object> hmTest_StringObject = null;	 
		 private Map<Integer,Object> hmTest_IntegerObject = null;	
		 
		    protected void setUp(){
		      
			//try {			
				//### Die TestObjekte, String, Object	
				this.hmTest_StringObject= setUp_fillTest_StringObject();							
//			} catch (ExceptionZZZ ez) {
//				fail("Method throws an exception." + ez.getMessageLast());
//			} 
				
			//try {		
				//### Die TestObjekte, Integer, Object	
				this.hmTest_IntegerObject=setUp_fillTest_IntegerObject();												
//				} catch (ExceptionZZZ ez) {
//					fail("Method throws an exception." + ez.getMessageLast());
//				} 
			
		}//END setup
		    
		    public void testSortByKeyAsInteger_StringObject() {
		    	try{
		    		HashMapIterableKeyZZZ<String, Object> hmIndexed = HashMapZZZ.sortByKeyAsInteger_StringObject(this.hmTest_StringObject, HashMapIterableKeyZZZ.iSORT_DIRECTION_ASCENDING );					
					assertNotNull(hmIndexed);
					
					DummyObjectZZZ  objTest01 = (DummyObjectZZZ) hmIndexed.getValueFirst();
					assertNotNull(objTest01);
					assertTrue(objTest01.getValue().equals("1"));
					
					DummyObjectZZZ objTest03 = (DummyObjectZZZ) hmIndexed.getValueLast();
					assertNotNull(objTest03);
					assertTrue(objTest03.getValue().equals("3"));
					
					//#############################
					DummyObjectZZZ objTestNULL = (DummyObjectZZZ) hmIndexed.getValueNext();
					assertNull(objTestNULL);
					//#############################
					
					Integer intKey = new Integer(1);//Merke: Index beginnt mit 0, also ist das der zweite Wert.
					DummyObjectZZZ objTest02 = (DummyObjectZZZ)hmIndexed.getValueByIndex(intKey);
					assertNotNull(objTest02);
					assertTrue(objTest02.getValue().equals("2"));
					
					DummyObjectZZZ objTestX = (DummyObjectZZZ) hmIndexed.getValueNext();
					assertNotNull(objTestX);
					assertTrue(objTestX.equals(objTest03));
					
					DummyObjectZZZ objTestY = (DummyObjectZZZ) hmIndexed.getValueNext();
					assertNull(objTestY);
					
					DummyObjectZZZ objTestZ = (DummyObjectZZZ) hmIndexed.getValueNext();
					assertNull(objTestZ);
					
					DummyObjectZZZ objTestZ2 = (DummyObjectZZZ) hmIndexed.getValue("75");
					assertNotNull(objTestZ2);
					
					DummyObjectZZZ objTestZ3 = (DummyObjectZZZ) hmIndexed.getValue("NichtDa");
					assertNull(objTestZ3);
					
				} catch (ExceptionZZZ ez) {
					fail("Method throws an exception." + ez.getMessageLast());
				} 
		    }
		    
		    public void testSortByKeyInteger() {
		    	try{
		    		HashMapIterableKeyZZZ<Integer, Object> hmIndexed = HashMapZZZ.sortByKeyInteger(this.hmTest_IntegerObject);					
					assertNotNull(hmIndexed);
					
					DummyObjectZZZ  objTest01 = (DummyObjectZZZ) hmIndexed.getValueFirst();
					assertNotNull(objTest01);
					assertTrue(objTest01.getValue().equals("1"));
					
					DummyObjectZZZ objTest03 = (DummyObjectZZZ) hmIndexed.getValueLast();
					assertNotNull(objTest03);
					assertTrue(objTest03.getValue().equals("3"));
					
					//#############################
					DummyObjectZZZ objTestNULL = (DummyObjectZZZ) hmIndexed.getValueNext();
					assertNull(objTestNULL);
					//#############################
					
					Integer intKey = new Integer(1);//Merke: Index beginnt mit 0, also ist das der zweite Wert.
					DummyObjectZZZ objTest02 = (DummyObjectZZZ)hmIndexed.getValueByIndex(intKey);
					assertNotNull(objTest02);
					assertTrue(objTest02.getValue().equals("2"));
					
					
					DummyObjectZZZ objTest02b = (DummyObjectZZZ)hmIndexed.getValueByIndex(1);
					assertNotNull(objTest02b);
					assertTrue(objTest02b.getValue().equals("2"));
					
					DummyObjectZZZ objTestX = (DummyObjectZZZ) hmIndexed.getValueNext();
					assertNotNull(objTestX);
					assertTrue(objTestX.equals(objTest03));
					
					DummyObjectZZZ objTestY = (DummyObjectZZZ) hmIndexed.getValueNext();
					assertNull(objTestY);
					
					DummyObjectZZZ objTestZ = (DummyObjectZZZ) hmIndexed.getValueNext();
					assertNull(objTestZ);
				} catch (ExceptionZZZ ez) {
					fail("Method throws an exception." + ez.getMessageLast());
				} 
		    }
		

		private Map<String,Object>setUp_fillTest_StringObject(){
			Map<String,Object> hmTest = new HashMap<String,Object>();
			main:{
				try {
				//### Das spezielle Generics Testobjekt
				DummyObjectZZZ objTest01 = new DummyObjectZZZ();
				objTest01.setFlag("init", true);
				objTest01.setValue("1");
				hmTest.put("75",objTest01);
											
				DummyObjectZZZ objTest03 = new DummyObjectZZZ();
				objTest03.setFlag("init", true);
				objTest03.setValue("3");
				hmTest.put("85",objTest03);
				
				DummyObjectZZZ objTest02 = new DummyObjectZZZ();
				objTest02.setFlag("init", true);
				objTest02.setValue("2");
				hmTest.put("80",objTest02);
				} catch (ExceptionZZZ ez) {
					fail("Method throws an exception." + ez.getMessageLast());
				} 
				
			}//end main:
		return hmTest;		
	}
		
		private Map<Integer,Object>setUp_fillTest_IntegerObject(){
			Map<Integer,Object> hmTest = new HashMap<Integer,Object>();
			main:{	
				try {
				//### Das spezielle Generics Testobjekt
				DummyObjectZZZ objTest01 = new DummyObjectZZZ();
				objTest01.setFlag("init", true);
				objTest01.setValue("1");
				Integer intKey1 = new Integer(75);
				hmTest.put(intKey1,objTest01);
											
				DummyObjectZZZ objTest03 = new DummyObjectZZZ();
				objTest03.setFlag("init", true);
				objTest03.setValue("3");
				Integer intKey2 = new Integer(85);
				hmTest.put(intKey2,objTest03);
				
				DummyObjectZZZ objTest02 = new DummyObjectZZZ();
				objTest02.setFlag("init", true);
				objTest02.setValue("2");
				Integer intKey3 = new Integer(80);
				hmTest.put(intKey3,objTest02);
				} catch (ExceptionZZZ ez) {
					fail("Method throws an exception." + ez.getMessageLast());
				} 
				
			}//end main:
		return hmTest;		
	}
		
}
