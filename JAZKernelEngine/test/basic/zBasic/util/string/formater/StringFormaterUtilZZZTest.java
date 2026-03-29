package basic.zBasic.util.string.formater;

import java.util.HashMap;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractList.ArrayListZZZ;
import basic.zBasic.util.string.formater.IEnumSetMappedStringFormatZZZ;
import basic.zBasic.util.string.formater.IStringFormatZZZ;
import basic.zBasic.util.string.formater.StringFormaterUtilZZZ;
import junit.framework.TestCase;

public class StringFormaterUtilZZZTest  extends TestCase{
	
	//private LogStringFormaterZZZ objLogStringTest = null;

	protected void setUp(){
//		try {			
//			objLogStringTest = new LogStringFormaterZZZ();
//								
//		} catch (ExceptionZZZ ez) {
//			fail("Method throws an exception." + ez.getMessageLast());
//		} 	
	}//END setup

	
	
	
	public void testComputeLinePartInLog() {
		try {
			//Hole den Formatierten String des "Kommentartrenners"
			//Das ist einfach, da kein LogString, etc. als zusätzlicher Paramter notwendig ist.
			String sValue=null; String sValueExpected=null;
						
			sValueExpected = "[A00/]# ";
			IEnumSetMappedStringFormatZZZ ienumFormatLogString = IStringFormatZZZ.LOGSTRINGFORMAT.CONTROL_SEPARATORMESSAGE_STRING;
			sValue= StringFormaterUtilZZZ.computeLinePartInLog(ienumFormatLogString);
			assertNotNull(sValue);
			assertEquals(sValueExpected, sValue);
			
			
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			fail("Method throws an exception." + ez.getMessageLast());
		}	
	}
}//end class
