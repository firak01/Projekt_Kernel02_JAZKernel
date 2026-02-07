package basic.zBasic.util.log;

import java.util.HashMap;

import basic.zBasic.DummyTestObjectZZZ;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IReflectCodeZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedZZZ;
import basic.zBasic.util.datatype.integer.IntegerArrayZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.math.PrimeFactorizationZZZ;
import basic.zBasic.util.math.PrimeNumberZZZ;
import basic.zBasic.util.string.formater.IEnumSetMappedLogStringFormatZZZ;
import basic.zBasic.util.string.formater.ILogStringFormatZZZ;
import basic.zBasic.util.string.formater.LogStringFormaterUtilZZZ;
import junit.framework.TestCase;

public class LogStringFormaterUtilZZZTest  extends TestCase{
	
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
			HashMap<Integer, String> hmValue = LogStringFormaterUtilZZZ.getHashMapFormatPositionStringAll();
			assertNotNull(hmValue);
			
			int iSize = hmValue.size();
			assertEquals(38, iSize);
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			fail("Method throws an exception." + ez.getMessageLast());
		}	
	}
	
	public void testComputeLinePartInLog() {
		try {
			//Hole den Formatierten String des "Kommentartrenners"
			//Das ist einfach, da kein LogString, etc. als zusätzlicher Paramter notwendig ist.
			String sValue=null; String sValueExpected=null;
						
			sValueExpected = "[A00/]# ";
			IEnumSetMappedLogStringFormatZZZ ienumFormatLogString = ILogStringFormatZZZ.LOGSTRINGFORMAT.CONTROL_SEPARATORMESSAGE_STRING;
			sValue= LogStringFormaterUtilZZZ.computeLinePartInLog(ienumFormatLogString);
			assertNotNull(sValue);
			assertEquals(sValueExpected, sValue);
			
			
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			fail("Method throws an exception." + ez.getMessageLast());
		}	
	}
	
	
}//end class
