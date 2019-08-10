package basic.zBasic;

import junit.framework.TestCase;

/**
 *  Merke: Das THE im Klassenanemn ist Absicht, weil nämlich die  Reflection - Klassen sellbst 
 *            aus dem  errechneten Stacktrace, etc. herausgerechnet werden.
 *            Darum weichen die Testklassen vom Schema ab.
 * 
 * @author Fritz Lindhauer, 08.08.2019, 23:15:52
 * 
 */
public class ReflectTHECodeKernelZZZTest  extends TestCase{
	
	private ReflectTHECodeKernelObject4TestingZZZ objObjectTest = null;

	protected void setUp(){
		//try {			
			
			//The main object used for testing
			objObjectTest = new ReflectTHECodeKernelObject4TestingZZZ();
			
		
//		} catch (ExceptionZZZ e) {
//			fail("Method throws an exception." + e.getMessageLast());
//		} 
	}//END setup
	 
	// TODO: ALLE METHODNEN VON REFLECTCODEZZZ auch in einer KernelVariante anbieten, die sich nur auf die Kernel-Klassen bezieht.
	public void testGetCallingStack(){		
		try{
			//Verbereitende initialisierung:
			String scallingMethod = ReflectCodeZZZ.getClassCurrentName() + "." + ReflectCodeZZZ.getMethodCurrentName();			
			String[]saCalling = ReflectCodeZZZ.getCallingStack();
						
			//+++++++++++++++++++++++++++++++++++++++++++++
			//Ermittle aus dem Stacktrace den Klassennamen.Methodennamen. 
			//Dies kann in diversen Möglichkeiten als Schlüssel für eine HashMap verwendet werden.
			String[]saCallingIntern01 = ReflectCodeKernelZZZ.getCallingStack();
			
			//Weil nur die aufrufende  Test-Klasse darin auftauchen soll gilt:
			assertTrue("Es sollte nur die aufrufende Testmethode im Ergebnis sein.", saCallingIntern01.length==1);
			String sCallingIntern01 = saCallingIntern01[0];			
			assertTrue("Es sollte die aufrufende Testmethode im Ergebnis sein", sCallingIntern01.equals(scallingMethod));
			
			
			//### Nun die Methode innerhalb eines erzeugten Objekts aufrufen ##########################
			boolean btemp = objObjectTest.startAsMain();
			String[] saCallingIntern02 = objObjectTest.getCallingStackComputed();			
			assertTrue("Es sollte die Größe des Arrays höher sein als bei lokalem Funktionsaufruf", saCallingIntern01.length<saCallingIntern02.length);
			
			String sCallingIntern02 = saCallingIntern01[saCallingIntern01.length-1];
			assertTrue("Es sollte die aufrufende Testmethode im Ergebnis sein, als Wert mit dem höchsten Index", sCallingIntern02.equals(scallingMethod));
			
			boolean btemp2 = objObjectTest.startAsSubInitialiseObject();
			
			
			
	} catch (ExceptionZZZ ez) {
		fail("Method throws an exception." + ez.getMessageLast());
	} 
	}
	
	public void testGetMethodCurrentName(){
		try{
			String sFunction = "testGetMethodCurrentName";
			String  stemp = ReflectCodeZZZ.getMethodCurrentName();
			assertNotNull("Fehler beim Ermitteln des aktuellen Methodennamens (NULL)", stemp);
			assertTrue("Fehler beim Ermitteln des aktuellen Methodennamens: '" + stemp + "' wurde nicht erwartet.", stemp.equals(sFunction));
			
			stemp = ReflectCodeZZZ.getMethodCurrentNameLined(0);
			assertNotNull("Fehler beim Ermitteln des aktuellen Methodennamens mit Zeilennummer (NULL)", stemp);
			assertTrue("Fehler beim Ermitteln des aktuellen Methodennamens: '" + stemp + "' wurde nicht erwartet (Zeilennummer).", stemp.startsWith(sFunction) && stemp.length() > sFunction.length());
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		} 
	}
	
	public void testGetClassCurrentName(){
		try{
			String sClass = this.getClass().getName();
			String  stemp = ReflectCodeZZZ.getClassCurrentName();
			assertNotNull("Fehler beim Ermitteln des aktuellen Klassennamens (NULL)", stemp);
			assertTrue("Fehler beim Ermitteln des aktuellen Klassennamens: '" + stemp + "' wurde nicht erwartet.", stemp.equals(sClass));
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		} 
	}
	
	public void testGetClassCallingName(){
		try{
			String sClassCurrent = this.getClass().getName(); 
			String  stemp = ReflectCodeZZZ.getClassCallingName(); //Das ist dann keine meiner Klassen, sondern von sun, etc.
			assertNotNull("Fehler beim Ermitteln des aufrufenden Klassennamens (NULL)", stemp);
			assertFalse("Fehler beim Ermitteln des aufrufenden Klassennamens: '" + stemp + "' wurde nicht erwartet.", stemp.equals(sClassCurrent));
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		} 
	}
	
	public void testGetPositionCurrent(){
		try{
			String sClassCurrent = this.getClass().getName(); 
			String sMethodCurrentLined = ReflectCodeZZZ.getMethodCurrentNameLined(2);//2 ist das Offset, weil die Zeilennummer erste f�r 2 Zeilen sp�ter berechnet werden soll. 
			
			String  stemp = ReflectCodeZZZ.getPositionCurrent(); //Das ist dann keine meiner Klassen, sondern von sun, etc.
			assertNotNull("Fehler beim Ermitteln der aktuellen CodePosition (NULL)", stemp);
			
			assertTrue("Fehler beim Ermitteln der aktuellen CodePosition: '" + stemp + "' wurde nicht erwartet (Klassenname).", stemp.startsWith(sClassCurrent) && stemp.length() > sClassCurrent.length());
			assertTrue("Fehler beim Ermitteln der aktuellen CodePosition: '" + stemp + "' wurde nicht erwartet (Zeilennummer).", stemp.endsWith(sMethodCurrentLined) && stemp.length() > sMethodCurrentLined.length());
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		} 
	}
		
}//END Class