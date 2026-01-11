package basic.zBasic.util.abstractList;

import java.util.ArrayList;
import java.util.Iterator;

import basic.zBasic.DummyTestObjecWithDefaultValuesZZZ;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractEnum.EnumSetMappedStatusTestTypeZZZ;
import basic.zBasic.util.abstractEnum.EnumSetMappedTestTypeZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedStatusZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedZZZ;
import basic.zBasic.util.abstractList.ArrayListZZZ;
import basic.zBasic.util.abstractList.HashMapIndexedZZZ;

import junit.framework.TestCase;

/**Merksatz (wichtig!)(von ChatGPT, 20260110)
*
* Ein Enum-Array kann niemals direkt zu einem Interface-Array gecastet werden,
* auch wenn das Enum dieses Interface implementiert.
* 
* @author Fritz Lindhauer, 10.01.2026, 08:27:05
*/	
public class ArrayListUtilZZZTest extends TestCase{
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
    
    public void testGetInstanceOfList() {
 	   try {
	 	   DummyTestObjecWithDefaultValuesZZZ obj = new DummyTestObjecWithDefaultValuesZZZ();	 	  
	 	   ArrayList<Class<?>>listaInstance = ArrayListUtilZZZ.getInstanceOfList(obj);
	 	   assertNull(listaInstance); //Weil es keine Liste ist...
	 	   

	 	   System.out.println("test");
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
 		}    
    }
    
   public void testToArray() {
	   //try {
	   Class<?> objClass = EnumSetMappedTestTypeZZZ.class;
	   Object[] objaEnum = objClass.getEnumConstants();
	   
	   //A) Datentyp Enum
	   //Erst einmal aus dem Enum eine ArrayList machen.
	   ArrayListZZZ listaEnumExtended = new ArrayListZZZ(objaEnum);
	   ArrayList<Enum> listaEnum = listaEnumExtended.toArrayList();
	   
	   //Test: Aus der ArrayList ein Enum machen
	   Object[] objaResultToArray = ArrayListUtilZZZ.toArray(listaEnum);
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
	   ArrayListZZZ listaEnumExtended = new ArrayListZZZ(objaEnum);
	   ArrayList<Enum> listaEnum = listaEnumExtended.toArrayList();
	   
	 //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	   //Test: Aus der ArrayList ein Enum-Array machen
	   Enum[]  objaResultToEnumArray = ArrayListUtilZZZ.toEnumArray(listaEnum);
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
	   ArrayListZZZ listaEnumExtended = new ArrayListZZZ(objaEnum);
	   ArrayList<IEnumSetMappedZZZ> listaEnum = listaEnumExtended.toArrayList();
	   
	 //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	   //Test: Aus der ArrayList ein Enum-Array machen
	   Enum[]  objaResultToEnumArray = ArrayListUtilZZZ.toEnumArrayByMapped(listaEnum);
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
   
   
 //Es darf kein Array mit Interface zurueckgegeben werden, s. ChatGPT 20260110
//   public void testToEnumMappedArrayByMapped() {
   public void testToEnumMappedArrayListByMapped() {
	   //try {
	   Class<?> objClass = EnumSetMappedTestTypeZZZ.class;
	   Object[] objaEnum = objClass.getEnumConstants();
	   
	   //A) Datentyp Enum
	   //Erst einmal aus dem Enum eine ArrayList machen.
	   ArrayListZZZ listaEnumExtended = new ArrayListZZZ(objaEnum);
	   ArrayList<IEnumSetMappedZZZ> listaEnum = listaEnumExtended.toArrayList();
	   assertNotNull(listaEnum);
	   
	 //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
//Es darf kein Array mit Interface zurueckgegeben werden, s. ChatGPT 20260110
	   //Test: Aus der ArrayList ein Enum-Array machen
	   //Das gibt eine Class Cast Exception... Enum[]  objaResultToEnumArray = ArrayListZZZ.toEnumMappedArrayByMapped(listaEnum);
//	   IEnumSetMappedZZZ[]  objaResultToEnumMappedArray = ArrayListUtilZZZ.toEnumMappedArrayByMapped(listaEnum);
//	   assertNotNull(objaResultToEnumMappedArray);
//	   assertTrue(objaResultToEnumMappedArray.length==3);
//	   assertTrue(objaResultToEnumMappedArray instanceof IEnumSetMappedZZZ[]);
//	   assertTrue(objaResultToEnumMappedArray[0] instanceof IEnumSetMappedZZZ);
//	   assertTrue(objaResultToEnumMappedArray[0] instanceof EnumSetMappedTestTypeZZZ);
	   
	   
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
	   ArrayListZZZ listaEnumExtended = new ArrayListZZZ(objaEnum);
	   ArrayList<IEnumSetMappedStatusZZZ> listaEnum = listaEnumExtended.toArrayList();
	   
	 //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	   //Test: Aus der ArrayList ein Enum-Array machen
	   //Das gibt eine Class Cast Exception... Enum[]  objaResultToEnumArray = ArrayListZZZ.toEnumMappedArrayByMapped(listaEnum);
	   //Das gibt eine java.lang.ArrayStoreException   
	   Enum[]  objaResultToEnumArray = ArrayListUtilZZZ.toEnumArrayByMappedStatus(listaEnum);
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
   
   
   //Es darf kein Array mit Interface zurueckgegeben werden, s. ChatGPT 20260110
   @SuppressWarnings({ "rawtypes", "unchecked" })
   public void testToEnumMappedStatusdArrayListByMapped() {
//	   try {
	   
	   //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	   //A) Datentyp Enum ohne Status
	   Class<?> objClassWithoutStatusEnum = EnumSetMappedTestTypeZZZ.class;
	   Object[] objaEnumWithoutStatus = objClassWithoutStatusEnum.getEnumConstants();
	 	 
	   //Erst einmal aus dem Enum eine ArrayList machen.

	   ArrayListZZZ listaEnumExtendedWithoutStatus = new ArrayListZZZ(objaEnumWithoutStatus);
	   ArrayList<IEnumSetMappedStatusZZZ> listaEnumWithoutStatus = listaEnumExtendedWithoutStatus.toArrayList();
	   assertNotNull(listaEnumWithoutStatus);
	   
//Es darf kein Array mit Interface zurueckgegeben werden, s. ChatGPT 20260110
	   //Test: Aus der ArrayList ein Enum-Array machen
	   //Das gibt eine Class Cast Exception... Enum[]  objaResultToEnumArray = ArrayListZZZ.toEnumMappedArrayByMapped(listaEnum);
	   //Das gibt eine java.lang.ArrayStoreException   
//	   IEnumSetMappedStatusZZZ[]  objaResultToEnumMappedStatusArray = ArrayListUtilZZZ.toEnumMappedStatusArrayByMapped(listaEnumWithoutStatus);
//	   assertNull(objaResultToEnumMappedStatusArray); //weil EnumSetMappedTestTypeZZZ als Klasse nur ein Enum hat vom Typ IEnumSetMappedZZZ
	   
	   
	  //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	   //A) Datentyp Enum mit Status
	   Class<?> objClassWithStatusEnum = EnumSetMappedStatusTestTypeZZZ.class;
	   Object[] objaEnumWithStatus = objClassWithStatusEnum.getEnumConstants();


	   //Erst einmal aus dem Enum eine ArrayList machen.
	   ArrayListZZZ listaEnumExtendedWithStatus = new ArrayListZZZ(objaEnumWithStatus);
	   ArrayList<IEnumSetMappedStatusZZZ> listaEnumWithStatus = listaEnumExtendedWithStatus.toArrayList();
	   assertNotNull(listaEnumWithStatus);
	   
//Es darf kein Array mit Interface zurueckgegeben werden, s. ChatGPT 20260110
	   //Test: Aus der ArrayList ein Enum-Array machen		  
//	   objaResultToEnumMappedStatusArray = ArrayListUtilZZZ.toEnumMappedStatusArrayByMapped(listaEnumWithStatus);	   
//	   assertNotNull(objaResultToEnumMappedStatusArray);
//	   assertTrue(objaResultToEnumMappedStatusArray.length==3);
//	   
//	   assertTrue(objaResultToEnumMappedStatusArray instanceof IEnumSetMappedZZZ[]); //wohl wg. IEnumSetMappedStatusZZZ extends IEnumSetMappedZZZ. 
//	   assertTrue(objaResultToEnumMappedStatusArray instanceof IEnumSetMappedStatusZZZ[]);
//	   
//	   assertTrue(objaResultToEnumMappedStatusArray[0] instanceof IEnumSetMappedZZZ);//wohl wg. IEnumSetMappedStatusZZZ extends IEnumSetMappedZZZ.
//	   assertTrue(objaResultToEnumMappedStatusArray[0] instanceof IEnumSetMappedStatusZZZ);
	   

//   		} catch (ExceptionZZZ ez) {
//			fail("Method throws an exception." + ez.getMessageLast());
//		}    
   }
   
}
