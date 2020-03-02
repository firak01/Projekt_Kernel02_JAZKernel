package basic.zBasic.util.abstractList;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import basic.zBasic.ExceptionZZZ;
import junit.framework.TestCase;

public class HashMapZZZTest extends TestCase{
		 private Map<String,Object> hmTest = null;	 
		 
		    protected void setUp(){
		      
			//try {			
				//### Die TestObjekte, normal	
				this.hmTest=new HashMap<String,Object>();
				
				//### Das spezielle Generics Testobjekt
				DummyObjectZZZ objTest01 = new DummyObjectZZZ();
				objTest01.setFlag("init", true);
				objTest01.setValue("1");
				this.hmTest.put("75",objTest01);
											
				DummyObjectZZZ objTest03 = new DummyObjectZZZ();
				objTest03.setFlag("init", true);
				objTest03.setValue("3");
				this.hmTest.put("85",objTest03);
				
				DummyObjectZZZ objTest02 = new DummyObjectZZZ();
				objTest02.setFlag("init", true);
				objTest02.setValue("2");
				this.hmTest.put("80",objTest02);
			
//			} catch (ExceptionZZZ ez) {
//				fail("Method throws an exception." + ez.getMessageLast());
//			} 
			
			
		}//END setup
		    
		    public void testSortByKeyAsInteger() {
		    	try{
		    		HashMapIterableKeyZZZ<String, Object> hmIndexed = HashMapZZZ.sortByKeyAsInteger_StringObject(this.hmTest);					
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
					DummyObjectZZZ objTest02 = (DummyObjectZZZ)hmIndexed.getValue(intKey);
					assertNotNull(objTest02);
					assertTrue(objTest02.getValue().equals("2"));
					
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
		

		
		
}
