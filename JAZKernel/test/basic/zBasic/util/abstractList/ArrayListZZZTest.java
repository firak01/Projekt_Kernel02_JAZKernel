package basic.zBasic.util.abstractList;

import java.util.ArrayList;
import java.util.Iterator;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractEnum.EnumSetMappedTestTypeZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedZZZ;
import basic.zBasic.util.abstractList.ArrayListExtendedZZZ;
import basic.zBasic.util.abstractList.HashMapExtendedIndexedZZZ;

import junit.framework.TestCase;

public class ArrayListZZZTest extends TestCase{
	private ArrayList alTest = null; 
	 
    protected void setUp(){
      
	//try {			
		//### Die TestObjekte	
    	alTest = new ArrayList();	
    	
    	
	/*
	} catch (ExceptionZZZ ez) {
		fail("Method throws an exception." + ez.getMessageLast());
	} 
	*/
	
}//END setup
    
   public void testToArray() {
	   //try {
	   Class<?> objClass = EnumSetMappedTestTypeZZZ.class;
	   Object[] objaEnum = objClass.getEnumConstants();
	   
	   //Erst einmal aus dem Enum eine ArrayList machen.
	   ArrayListExtendedZZZ listaEnumExtended = new ArrayListExtendedZZZ(objaEnum);
	   ArrayList listaEnum = listaEnumExtended.toArrayList();
	   
	   //Test: Aus der ArrayList ein Enum machen
	   Object[] objaResultToArray = ArrayListZZZ.toArray(listaEnum);
	   assertNotNull(objaResultToArray);
	   assertTrue(objaResultToArray.length==3);
	   assertTrue(objaResultToArray[0] instanceof IEnumSetMappedZZZ);
	   assertTrue(objaResultToArray[0] instanceof EnumSetMappedTestTypeZZZ);
	   
	   //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	   
	   Enum[]  objaResultToEnumArray = ArrayListZZZ.toEnumArray(listaEnum);
	   assertNotNull(objaResultToArray);
	   assertTrue(objaResultToArray.length==3);
	   assertTrue(objaResultToArray[0] instanceof IEnumSetMappedZZZ);
	   assertTrue(objaResultToArray[0] instanceof EnumSetMappedTestTypeZZZ);
	   
	   
	   //EnumSetMappedTestTypeZZZ
	   System.out.println("test");
//   		} catch (ExceptionZZZ ez) {
//			fail("Method throws an exception." + ez.getMessageLast());
//		}    
   }
}
