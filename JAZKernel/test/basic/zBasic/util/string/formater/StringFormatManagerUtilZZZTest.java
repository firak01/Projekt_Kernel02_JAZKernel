package basic.zBasic.util.string.formater;

import java.util.HashMap;
import java.util.Map;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractList.ArrayListZZZ;
import basic.zBasic.util.abstractList.HashMapUtilZZZ;
import basic.zBasic.util.string.formater.IEnumSetMappedStringFormatZZZ;
import basic.zBasic.util.string.formater.IStringFormatZZZ;
import basic.zBasic.util.string.formater.StringFormaterUtilZZZ;
import junit.framework.TestCase;

public class StringFormatManagerUtilZZZTest  extends TestCase{
	
	//private LogStringFormaterZZZ objLogStringTest = null;

	protected void setUp(){
//		try {			
//			objLogStringTest = new LogStringFormaterZZZ();
//								
//		} catch (ExceptionZZZ ez) {
//			fail("Method throws an exception." + ez.getMessageLast());
//		} 	
	}//END setup

	
	
	public void testGetHashMapFormatPositionStringAll() {
		try {
			//Hole den Formatierten String des "Kommentartrenners"
			HashMap<Integer, String> hmValue = StringFormatManagerUtilZZZ.getHashMapFormatPositionStringAll();
			assertNotNull(hmValue);
			
			int iSize = hmValue.size();
			assertEquals(51, iSize);
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			fail("Method throws an exception." + ez.getMessageLast());
		}	
	}
	
	
	public void testFilterSeparatorsAsArrayList() {
		try {
			IEnumSetMappedStringFormatZZZ[] objaFormat = null;
			ArrayListZZZ<IEnumSetMappedStringFormatZZZ> listaValue = null; int iValue = 0;
			StringFormatManagerZZZ objFormatManager = null;
						
			
			objFormatManager = StringFormatManagerZZZ.getNewInstance();			
			objaFormat = objFormatManager.getStringFormatArrayCurrent();
			
			
			listaValue = StringFormatManagerUtilZZZ.filterSeparatorsAsArrayList(objaFormat);
			assertNotNull(listaValue);
			
			iValue = listaValue.size();
			assertEquals(2, iValue);
			
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			fail("Method throws an exception." + ez.getMessageLast());
		}		
	}

	public void testSplitBySeparatorToMap() {
		try {
			StringFormatManagerZZZ objFormatManager = null;
			Map<IEnumSetMappedStringFormatZZZ, IEnumSetMappedStringFormatZZZ[]> mFormatSplitted = null;
			HashMap<IEnumSetMappedStringFormatZZZ, IEnumSetMappedStringFormatZZZ[]> hmFormatSplitted = null;
			
			objFormatManager = StringFormatManagerZZZ.getNewInstance();
			IEnumSetMappedStringFormatZZZ[] objaFormat = objFormatManager.getStringFormatArrayCurrent();
			
			mFormatSplitted = StringFormatManagerUtilZZZ.splitBySeparatorToStringHashMap(objaFormat);
			assertNotNull(mFormatSplitted);
			
			hmFormatSplitted = HashMapUtilZZZ.fromMap(mFormatSplitted);
			assertNotNull(hmFormatSplitted);
			assertEquals(3, hmFormatSplitted.size());												
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			fail("Method throws an exception." + ez.getMessageLast());
		}		
	}
	
}//end class
