package basic.zBasic;

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
			int iLineNr = 25;
			assertEquals("<positioncurrent><method>testGetPositionCurrentXml</method><filename>ReflectCodeZZZTest.java</filename><linenr>"+ iLineNr + "</linenr><fileposition> (ReflectCodeZZZTest.java:"+ iLineNr + ") </fileposition></positioncurrent># ", sValue); 			
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
			int iLineNr=39;
			assertEquals("testGetPositionCurrent @ReflectCodeZZZTest.java:"+ iLineNr + " ~ (ReflectCodeZZZTest.java:"+ iLineNr + ") # ", sValue); 			
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
			int iLineNr = 54;
			assertEquals("<positioncurrent><method>testGetPositionCurrentXmlFormated</method><linenr>"+ iLineNr + "</linenr><filename>ReflectCodeZZZTest.java</filename><fileposition> (ReflectCodeZZZTest.java:"+ iLineNr + ") </fileposition></positioncurrent># ", sValue); 			
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			fail("Method throws an exception." + ez.getMessageLast());
		}	
	}
	
	
	
}//end class