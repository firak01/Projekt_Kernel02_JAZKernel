package basic.zBasic;

import basic.zBasic.util.datatype.integer.IntegerArrayZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.log.IEnumSetMappedLogStringFormatZZZ;
import basic.zBasic.util.log.ILogStringFormatZZZ;
import basic.zBasic.util.log.LogStringFormaterZZZ;
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
	
	/** Bedenke: Sobald sich die Zeilennummer in diesem Code aendert stimmt der Wert für <linenr> - Tag nicht mehr.
	 *           Darum steht der Test ganz oben...
	 * 
	 */
	public void testGetPositionCurrentXml() {
		try {
			String sValue = ReflectCodeZZZ.getPositionCurrentXml();
			assertEquals("<positioncurrent><method>testGetPositionCurrentXml</method><filename>ReflectCodeZZZTest.java</filename><linenr>32</linenr><fileposition> (ReflectCodeZZZTest.java:32) </fileposition></positioncurrent># ", sValue); 			
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			fail("Method throws an exception." + ez.getMessageLast());
		}	
	}
	
	/** Bedenke: Sobald sich die Zeilennummer in diesem Code aendert stimmt der Wert für nicht mehr.
	 * 
	 */
	public void testGetPositionCurrent() {
		try {
			String sValue = ReflectCodeZZZ.getPositionCurrent();
			assertEquals("testGetPositionCurrent @ReflectCodeZZZTest.java:45 ~ (ReflectCodeZZZTest.java:45) # ", sValue); 			
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			fail("Method throws an exception." + ez.getMessageLast());
		}	
	}
	
	/** Bedenke: Sobald sich die Zeilennummer in diesem Code aendert stimmt der Wert für <linenr> - Tag nicht mehr.
	 *           Darum steht der Test ganz oben...
	 * 
	 */
	public void testGetPositionCurrentXmlFormated() {
		try {
			String sValue = ReflectCodeZZZ.getPositionCurrentXmlFormated();
			assertEquals("<positioncurrent><method>testGetPositionCurrentXml</method><linenr>59</linenr><filename>ReflectCodeZZZTest.java</filename><fileposition> (ReflectCodeZZZTest.java:59) </fileposition></positioncurrent># ", sValue); 			
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			fail("Method throws an exception." + ez.getMessageLast());
		}	
	}
	
	
	
}//end class