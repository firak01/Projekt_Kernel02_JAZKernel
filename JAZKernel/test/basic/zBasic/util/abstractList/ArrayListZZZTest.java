package basic.zBasic.util.abstractList;

import java.util.ArrayList;
import java.util.Iterator;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractEnum.EnumSetMappedStatusTestTypeZZZ;
import basic.zBasic.util.abstractEnum.EnumSetMappedTestTypeZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedStatusZZZ;
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
	   
	   //A) Datentyp Enum
	   //Erst einmal aus dem Enum eine ArrayList machen.
	   ArrayListExtendedZZZ listaEnumExtended = new ArrayListExtendedZZZ(objaEnum);
	   ArrayList<Enum> listaEnum = listaEnumExtended.toArrayList();
	   
	   //Test: Aus der ArrayList ein Enum machen
	   Object[] objaResultToArray = ArrayListZZZ.toArray(listaEnum);
	   assertNotNull(objaResultToArray);
	   assertTrue(objaResultToArray.length==3);
	   assertTrue(objaResultToArray[0] instanceof IEnumSetMappedZZZ);
	   assertTrue(objaResultToArray[0] instanceof EnumSetMappedTestTypeZZZ);
	   
	   //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	   
	  
	   
	   //EnumSetMappedTestTypeZZZ
	   System.out.println("test");
//   		} catch (ExceptionZZZ ez) {
//			fail("Method throws an exception." + ez.getMessageLast());
//		}    
   }
   
   public void testToEnumArray() {
	   //try {
	   Class<?> objClass = EnumSetMappedTestTypeZZZ.class;
	   Object[] objaEnum = objClass.getEnumConstants();
	   
	   //A) Datentyp Enum
	   //Erst einmal aus dem Enum eine ArrayList machen.
	   ArrayListExtendedZZZ listaEnumExtended = new ArrayListExtendedZZZ(objaEnum);
	   ArrayList<Enum> listaEnum = listaEnumExtended.toArrayList();
	   
	 //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	   //Test: Aus der ArrayList ein Enum-Array machen
	   Enum[]  objaResultToEnumArray = ArrayListZZZ.toEnumArray(listaEnum);
	   assertNotNull(objaResultToEnumArray);
	   assertTrue(objaResultToEnumArray.length==3);
	   assertTrue(objaResultToEnumArray instanceof Enum[]);
	   assertTrue(objaResultToEnumArray[0] instanceof IEnumSetMappedZZZ);
	   assertTrue(objaResultToEnumArray[0] instanceof EnumSetMappedTestTypeZZZ);
	   
	   
	   //EnumSetMappedTestTypeZZZ
	   System.out.println("test");
//   		} catch (ExceptionZZZ ez) {
//			fail("Method throws an exception." + ez.getMessageLast());
//		}    
   }
   
   public void testToEnumArrayByMapped() {
	   //try {
	   Class<?> objClass = EnumSetMappedTestTypeZZZ.class;
	   Object[] objaEnum = objClass.getEnumConstants();
	   
	   //A) Datentyp Enum
	   //Erst einmal aus dem Enum eine ArrayList machen.
	   ArrayListExtendedZZZ listaEnumExtended = new ArrayListExtendedZZZ(objaEnum);
	   ArrayList<IEnumSetMappedZZZ> listaEnum = listaEnumExtended.toArrayList();
	   
	 //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	   //Test: Aus der ArrayList ein Enum-Array machen
	   Enum[]  objaResultToEnumArray = ArrayListZZZ.toEnumArrayByMapped(listaEnum);
	   assertNotNull(objaResultToEnumArray);
	   assertTrue(objaResultToEnumArray.length==3);
	   assertTrue(objaResultToEnumArray instanceof Enum[]);
	   assertTrue(objaResultToEnumArray[0] instanceof IEnumSetMappedZZZ);
	   assertTrue(objaResultToEnumArray[0] instanceof EnumSetMappedTestTypeZZZ);
	   
	   
	   //EnumSetMappedTestTypeZZZ
	   System.out.println("test");
//   		} catch (ExceptionZZZ ez) {
//			fail("Method throws an exception." + ez.getMessageLast());
//		}    
   }
   
   
   public void testToEnumMappedArrayByMapped() {
	   //try {
	   Class<?> objClass = EnumSetMappedTestTypeZZZ.class;
	   Object[] objaEnum = objClass.getEnumConstants();
	   
	   //A) Datentyp Enum
	   //Erst einmal aus dem Enum eine ArrayList machen.
	   ArrayListExtendedZZZ listaEnumExtended = new ArrayListExtendedZZZ(objaEnum);
	   ArrayList<IEnumSetMappedZZZ> listaEnum = listaEnumExtended.toArrayList();
	   
	 //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	   //Test: Aus der ArrayList ein Enum-Array machen
	   //Das gibt eine Class Cast Exception... Enum[]  objaResultToEnumArray = ArrayListZZZ.toEnumMappedArrayByMapped(listaEnum);
	   IEnumSetMappedZZZ[]  objaResultToEnumMappedArray = ArrayListZZZ.toEnumMappedArrayByMapped(listaEnum);
	   assertNotNull(objaResultToEnumMappedArray);
	   assertTrue(objaResultToEnumMappedArray.length==3);
	   assertTrue(objaResultToEnumMappedArray instanceof IEnumSetMappedZZZ[]);
	   assertTrue(objaResultToEnumMappedArray[0] instanceof IEnumSetMappedZZZ);
	   assertTrue(objaResultToEnumMappedArray[0] instanceof EnumSetMappedTestTypeZZZ);
	   
	   
	   //EnumSetMappedTestTypeZZZ
	   System.out.println("test");
//   		} catch (ExceptionZZZ ez) {
//			fail("Method throws an exception." + ez.getMessageLast());
//		}    
   }
   
   
   public void testToEnumdArrayByMappedStatus() {
	   //try {
	   Class<?> objClass = EnumSetMappedTestTypeZZZ.class;
	   Object[] objaEnum = objClass.getEnumConstants();
	   
	   //A) Datentyp Enum
	   //Erst einmal aus dem Enum eine ArrayList machen.
	   ArrayListExtendedZZZ listaEnumExtended = new ArrayListExtendedZZZ(objaEnum);
	   ArrayList<IEnumSetMappedStatusZZZ> listaEnum = listaEnumExtended.toArrayList();
	   
	 //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	   //Test: Aus der ArrayList ein Enum-Array machen
	   //Das gibt eine Class Cast Exception... Enum[]  objaResultToEnumArray = ArrayListZZZ.toEnumMappedArrayByMapped(listaEnum);
	   //Das gibt eine java.lang.ArrayStoreException   
	   Enum[]  objaResultToEnumArray = ArrayListZZZ.toEnumArrayByMappedStatus(listaEnum);
	   assertNotNull(objaResultToEnumArray);
	   assertTrue(objaResultToEnumArray.length==3);
	   
	   assertFalse(objaResultToEnumArray instanceof IEnumSetMappedStatusZZZ[]);
	   assertTrue(objaResultToEnumArray instanceof Enum[]);
	   
	   assertTrue(objaResultToEnumArray[0] instanceof IEnumSetMappedZZZ);//wohl weil die Enums im Objekt halt so definiert sind, als IEnumSetMappedZZZ
	   assertFalse(objaResultToEnumArray[0] instanceof IEnumSetMappedStatusZZZ); //trotz IEnumSetMappedStatusZZZ extends IEnumSetMappedZZZ. Aber die Enums im Objekt sind halt so definiert als IEnumSetMappedZZZ
	   
	   assertTrue(objaResultToEnumArray[0] instanceof EnumSetMappedTestTypeZZZ);
	   
	   
	   //EnumSetMappedTestTypeZZZ
	   System.out.println("test");
//   		} catch (ExceptionZZZ ez) {
//			fail("Method throws an exception." + ez.getMessageLast());
//		}    
   }
   
   public void testToEnumMappedStatusdArrayByMapped() {
	   try {
	   
	   //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	   //A) Datentyp Enum ohne Status
	   Class<?> objClassWithoutStatusEnum = EnumSetMappedTestTypeZZZ.class;
	   Object[] objaEnumWithoutStatus = objClassWithoutStatusEnum.getEnumConstants();
	 	 
	   //Erst einmal aus dem Enum eine ArrayList machen.
	   ArrayListExtendedZZZ listaEnumExtendedWithoutStatus = new ArrayListExtendedZZZ(objaEnumWithoutStatus);
	   ArrayList<IEnumSetMappedStatusZZZ> listaEnumWithoutStatus = listaEnumExtendedWithoutStatus.toArrayList();
	   
	 
	   //Test: Aus der ArrayList ein Enum-Array machen
	   //Das gibt eine Class Cast Exception... Enum[]  objaResultToEnumArray = ArrayListZZZ.toEnumMappedArrayByMapped(listaEnum);
	   //Das gibt eine java.lang.ArrayStoreException   
	   IEnumSetMappedStatusZZZ[]  objaResultToEnumMappedStatusArray = ArrayListZZZ.toEnumMappedStatusArrayByMapped(listaEnumWithoutStatus);
	   assertNull(objaResultToEnumMappedStatusArray); //weil EnumSetMappedTestTypeZZZ als Klasse nur ein Enum hat vom Typ IEnumSetMappedZZZ
	   
	   
	  //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	   //A) Datentyp Enum mit Status
	   Class<?> objClassWithStatusEnum = EnumSetMappedStatusTestTypeZZZ.class;
	   Object[] objaEnumWithStatus = objClassWithStatusEnum.getEnumConstants();
	 
	   //Erst einmal aus dem Enum eine ArrayList machen.
	   ArrayListExtendedZZZ listaEnumExtendedWithStatus = new ArrayListExtendedZZZ(objaEnumWithStatus);
	   ArrayList<IEnumSetMappedStatusZZZ> listaEnumWithStatus = listaEnumExtendedWithStatus.toArrayList();
	 
	   //Test: Aus der ArrayList ein Enum-Array machen		  
	   objaResultToEnumMappedStatusArray = ArrayListZZZ.toEnumMappedStatusArrayByMapped(listaEnumWithStatus);	   
	   assertNotNull(objaResultToEnumMappedStatusArray);
	   assertTrue(objaResultToEnumMappedStatusArray.length==3);
	   
	   assertTrue(objaResultToEnumMappedStatusArray instanceof IEnumSetMappedZZZ[]); //wohl wg. IEnumSetMappedStatusZZZ extends IEnumSetMappedZZZ. 
	   assertTrue(objaResultToEnumMappedStatusArray instanceof IEnumSetMappedStatusZZZ[]);
	   
	   assertTrue(objaResultToEnumMappedStatusArray[0] instanceof IEnumSetMappedZZZ);//wohl wg. IEnumSetMappedStatusZZZ extends IEnumSetMappedZZZ.
	   assertTrue(objaResultToEnumMappedStatusArray[0] instanceof IEnumSetMappedStatusZZZ);
	   
	   //assertFalse(objaResultToEnumMappedStatusArray[0] instanceof EnumSetMappedStatusTestTypeZZZ);
	   
	   
	   //EnumSetMappedTestTypeZZZ
	   System.out.println("test");
   		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}    
   }
   
}
