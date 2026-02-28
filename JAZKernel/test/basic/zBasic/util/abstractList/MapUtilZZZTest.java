package basic.zBasic.util.abstractList;

import java.util.HashMap;
import java.util.Map;

import basic.zBasic.DummyTestObjecWithDefaultValuesZZZ;
import basic.zBasic.ExceptionZZZ;
import junit.framework.TestCase;

public class MapUtilZZZTest extends TestCase{
	 private Map<String,String[]> hmTest_StringArray01 = null;	 
	 private Map<String,String[]> hmTest_StringArray02 = null;
	 
	 private Map<Integer,Object> hmTest_IntegerObject = null;	
	 
	    protected void setUp(){
	      
		//try {			
			//### Die TestObjekte, String, String-Array	
			this.hmTest_StringArray01= setUp_fillTest_StringArray01();	
			this.hmTest_StringArray02= setUp_fillTest_StringArray02();
			
//		} catch (ExceptionZZZ ez) {
//			fail("Method throws an exception." + ez.getMessageLast());
//		} 
			
		
		
	}//END setup
	    
	    public void testMergeMapsAndJoinArrayValues() {
	    	try{
	    		Map<String,String[]> hmValue = MapUtilZZZ.mergeMapsAndJoinArrayValues(hmTest_StringArray01,hmTest_StringArray02);					
				assertNotNull(hmValue);
						
			} catch (ExceptionZZZ ez) {
				ez.printStackTrace();
				fail("Method throws an exception." + ez.getMessageLast());
			} 
	    }
	    
	    
	    private Map<String,String[]>setUp_fillTest_StringArray01(){
			Map<String,String[]> hmTest = new HashMap<String,String[]>();
			main:{
//				try {
					//### StringArray als Testobjekt
					String[] sa01 = {"a","b"};
					String[] sa02 = {"c","d","e"};
					
					hmTest.put("01", sa01);
					hmTest.put("02", sa02);
														
//				} catch (ExceptionZZZ ez) {
//					ez.printStackTrace();
//					fail("Method throws an exception." + ez.getMessageLast());
//				} 
				
			}//end main:
		return hmTest;		
	}
	    
    private Map<String,String[]>setUp_fillTest_StringArray02(){
		Map<String,String[]> hmTest = new HashMap<String,String[]>();
		main:{
//				try {
				//### StringArray als Testobjekt
				String[] sa01 = {"1","2"};
				String[] sa03 = {"3","4","5"};
				
				hmTest.put("01", sa01);
				hmTest.put("03", sa03);
													
//				} catch (ExceptionZZZ ez) {
//					ez.printStackTrace();
//					fail("Method throws an exception." + ez.getMessageLast());
//				} 
			
		}//end main:
	return hmTest;		
}
	    
    //TODOGOON: Die Methoden auch mit Dummy-Objekten testen.
	    private Map<String,Object[]>setUp_fillTest_ObjectArray01(){
			Map<String,Object[]> hmTest = new HashMap<String,Object[]>();
			main:{
				try {
				//### Das spezielle Generics Testobjekt
				DummyTestObjecWithDefaultValuesZZZ objTest01 = new DummyTestObjecWithDefaultValuesZZZ();
				objTest01.setFlag("init", true);
				objTest01.setValue("1");
											
				DummyTestObjecWithDefaultValuesZZZ objTest03 = new DummyTestObjecWithDefaultValuesZZZ();
				objTest03.setFlag("init", true);
				objTest03.setValue("3");
				
				DummyTestObjecWithDefaultValuesZZZ[] objaTest01 = new DummyTestObjecWithDefaultValuesZZZ[2];
				objaTest01[0]=objTest01;
				objaTest01[0]=objTest03;
				
				hmTest.put("01", objaTest01);
				
				//++++++++++++++++++++++++++++++++++++++++++++++++
				DummyTestObjecWithDefaultValuesZZZ objTest02 = new DummyTestObjecWithDefaultValuesZZZ();
				objTest02.setFlag("init", true);
				objTest02.setValue("2");
				
				DummyTestObjecWithDefaultValuesZZZ[] objaTest02 = new DummyTestObjecWithDefaultValuesZZZ[3];
				objaTest02[0]=objTest01;
				objaTest02[0]=objTest02;
				objaTest02[2]=objTest03;
								
				hmTest.put("02", objaTest02);
			} catch (ExceptionZZZ ez) {
				ez.printStackTrace();
				fail("Method throws an exception." + ez.getMessageLast());
			} 
			
		}//end main:
		return hmTest;		
	}
	    
	    
}
