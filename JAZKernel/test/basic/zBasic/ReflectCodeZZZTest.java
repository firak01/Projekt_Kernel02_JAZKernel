package basic.zBasic;

import basic.zBasic.util.datatype.integer.IntegerArrayZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.log.IEnumSetMappedLogStringFormatZZZ;
import basic.zBasic.util.log.ILogStringZZZ;
import basic.zBasic.util.log.LogStringZZZ;
import basic.zBasic.util.math.PrimeFactorizationZZZ;
import basic.zBasic.util.math.PrimeNumberZZZ;
import junit.framework.TestCase;

public class ReflectCodeZZZTest   extends TestCase{
	
	

	protected void setUp(){
//		try {			
//			
//					
//			
//		} catch (ExceptionZZZ ez) {
//			fail("Method throws an exception." + ez.getMessageLast());
//		} 	
	}//END setup
	
	public void testGetPositionCurrent() {
		try {
			String sValue = ReflectCodeZZZ.getPositionCurrent();
			assertEquals("<method>testGetPositionCurrent</method><fileposition> (ReflectCodeZZZTest.java:28) </fileposition># ", sValue);
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			fail("Method throws an exception." + ez.getMessageLast());
		}	
	}
	
	
	
}//end class