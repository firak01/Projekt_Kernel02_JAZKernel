package basic.zBasic.util.abstractArray;

import java.util.ArrayList;
import java.util.Map;

import basic.zBasic.DummyTestObjecWithDefaultValuesZZZ;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractEnum.EnumSetMappedTestTypeZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedZZZ;
import basic.zBasic.util.abstractList.ArrayListZZZ;
import basic.zBasic.util.datatype.string.StringArrayZZZ;
import basic.zBasic.util.abstractList.ArrayListUtilZZZ;
import junit.framework.TestCase;

public class ArrayUtilZZZTest extends TestCase{
	private ArrayList alTest = null; 
	 
    protected void setUp(){
      
	//try {			
		//### Die TestObjekte. Die Idee ist, eine ArrayList zu verwenden.
    	//    Diese ist viel dynamischer um Eintraege zu erweitern.
    	//    Vor den eigentlichen Array-Test muss dann daraus immer ein Array gemacht werden.
    	alTest = new ArrayList();	
    	
    	
	/*
	} catch (ExceptionZZZ ez) {
		fail("Method throws an exception." + ez.getMessageLast());
	} 
	*/
	
    }//END setup
    
    public void testTrim() {
 	   try {
 		  int iArrayLengthOnStart=-1;  int iArrayLengthOnEnd=-1;
 		  int iListLengthOnStart=-1;  int iListLengthOnEnd=-1;
 		  String[]saTestReturn=null;
 		  
 				  
	 	   alTest.add("eins");
	 	   alTest.add("zwei");
	 	   alTest.add(null);
	 	   alTest.add("drei");
	 	   alTest.add("vier");
	 	   iListLengthOnStart = alTest.size();
	 	   
	 	   
	 	   //### der eigentliche Test
	 	   String[]saTest = ArrayListUtilZZZ.toStringArray(alTest);
	 	   assertNotNull(saTest);
	 	   
	 	   iArrayLengthOnStart = saTest.length;
	 	   saTestReturn = ArrayUtilZZZ.trim(saTest);
	 	   
	 	   iArrayLengthOnEnd = saTestReturn.length;	 	   
	 	   assertTrue(iArrayLengthOnStart>=iArrayLengthOnEnd);
	 	   
	 	   //mache mal einen dynamischen Vergleich
	 	   ArrayList<String>alTestReturn = (ArrayList<String>) ArrayListUtilZZZ.trim(alTest);
	 	   iListLengthOnEnd = alTestReturn.size();
	 	   
	 	   assertEquals(iArrayLengthOnEnd, iListLengthOnEnd);
	 	
	 	   
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			fail("Method throws an exception." + ez.getMessageLast());
 		}    
    }
    
    public void testSplityKeysToMap() {
    	try {
    		String[] sa01 = {"a","b","#","c","d","e","f","g","*","h","i","j","~","x","y"}; 
			String[] saKey = {"#","~","!","*"};
			
			Map<String, String[]> hmValue = ArrayUtilZZZ.splitByKeysToMap(sa01, saKey,String.class);
			assertNotNull(hmValue);
			assertEquals(4,hmValue.size());
			
		}catch(ExceptionZZZ ez){
			ez.printStackTrace();
			fail("Method throws an exception." + ez.getMessageLast());
		}
    }
}
