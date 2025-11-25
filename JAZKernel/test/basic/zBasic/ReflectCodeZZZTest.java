package basic.zBasic;

import basic.zBasic.util.datatype.string.StringZZZ;
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
			int iLineNr = 26;
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
			int iLineNr=40;
			assertEquals("testGetPositionCurrent @ReflectCodeZZZTest.java:"+ iLineNr + " ~ (ReflectCodeZZZTest.java:"+ iLineNr + ") # ", sValue); 			
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			fail("Method throws an exception." + ez.getMessageLast());
		}	
	}
	
	public void testGetPositionCurrentSimple() {
		try {
			String sValue = ReflectCodeZZZ.getPositionCurrentSimple();
			int iLineNr=51; 
			boolean bStartsWith = StringZZZ.startsWith(sValue, "basic.zBasic.ReflectCodeZZZTest.testGetPositionCurrentSimple - Line "+ iLineNr);
			assertTrue(bStartsWith);
			
			boolean bEndsWith = StringZZZ.endsWith(sValue, IReflectCodeZZZ.sPOSITION_MESSAGE_SEPARATOR);
			assertTrue(bEndsWith);
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
			int iLineNr = 71;
			assertEquals("<positioncurrent><method>testGetPositionCurrentXmlFormated</method><linenr>"+ iLineNr + "</linenr><filename>ReflectCodeZZZTest.java</filename><fileposition> (ReflectCodeZZZTest.java:"+ iLineNr + ") </fileposition></positioncurrent># ", sValue); 			
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			fail("Method throws an exception." + ez.getMessageLast());
		}	
	}
	
	//######################################################################################################
	public void testGetPositionCalling() {
		try {
			//Weil es calling Tests sind, eine Untermethode aufrufen, von der aus "gerechnet wird".
			String sValue = testGetPositionCallingSimple_();			
			int iLineNr = 84;
			assertEquals("basic.zBasic.ReflectCodeZZZTest.testGetPositionCalling - Line " + iLineNr + "# ", sValue); 			
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			fail("Method throws an exception." + ez.getMessageLast());
		}	
	}
	
	private String testGetPositionCallingSimple_() throws ExceptionZZZ {
		String sReturn = null;
		main:{
			sReturn = ReflectCodeZZZ.getPositionCallingSimple();
		}//end main:
		return sReturn;
	}
	
	
	//#####################################################################################################
	
	public void testGetMethodCurrentNameLined() {
		try {
			//Weil es calling Tests sind, eine Untermethode aufrufen, von der aus "gerechnet wird".
			String sValue = ReflectCodeZZZ.getMethodCurrentNameLined();			
			int iLineNr = 107;
			boolean bStartsWith = StringZZZ.startsWith(sValue, "testGetMethodCurrentNameLined - Line " + iLineNr );
			assertTrue(bStartsWith);
			
			boolean bEndsWith = StringZZZ.endsWith(sValue, IReflectCodeZZZ.sPOSITION_MESSAGE_SEPARATOR);
			assertTrue(bEndsWith);
						
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			fail("Method throws an exception." + ez.getMessageLast());
		}	
	}
	
	
}//end class