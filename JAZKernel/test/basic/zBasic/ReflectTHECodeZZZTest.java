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
public class ReflectTHECodeZZZTest  extends TestCase{
	
	private DummyTestObjectWithFlagZZZ objObjectTest = null;

	protected void setUp(){
		//try {			
			
			//The main object used for testing
			objObjectTest = new DummyTestObjectWithFlagZZZ();
			
		
//		} catch (ExceptionZZZ e) {
//			fail("Method throws an exception." + e.getMessageLast());
//		} 
	}//END setup
	 
	
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
			String sClassMethod = ReflectCodeZZZ.getClassMethodString();
			assertTrue(sClassMethod.startsWith(sClassMethod));
			
			String sMethodCurrentLined1 = ReflectCodeZZZ.getMethodCurrentNameLined(1); //also fuer die Folgezeile (!!!) berechnen
			String sMethodCurrentLined = ReflectCodeZZZ.getMethodCurrentNameLined();
			assertTrue(sMethodCurrentLined.equals(sMethodCurrentLined1));
			
			sMethodCurrentLined1 = ReflectCodeZZZ.getMethodCurrentNameLined(1); //also fuer die Folgezeile (!!!) berechnen
			String sMethodCurrentLined0 = ReflectCodeZZZ.getMethodCurrentNameLined(0);
			assertTrue(sMethodCurrentLined0.equals(sMethodCurrentLined1));
			
			int iLineCurrent1 = ReflectCodeZZZ.getMethodCurrentLine(0,1);
			int iLineCurrent0 = ReflectCodeZZZ.getMethodCurrentLine(0,0);
			assertTrue(iLineCurrent0==iLineCurrent1);
			
			//Merke: Von dieser Testfunktion aus, ist keine sinnvolle "calling"-Methode aufrufbar.
			iLineCurrent1 = ReflectCodeZZZ.getMethodCurrentLine(0,1);
			int iLineCallingMinus1 = ReflectCodeZZZ.getMethodCallingLine(-1);
			assertTrue(iLineCallingMinus1==iLineCurrent1);
			
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			String sMethodCurrent = ReflectCodeZZZ.getMethodCurrentName();
			assertTrue("Fehler bei der Ermittlung der Methode", sMethodCurrentLined.startsWith(sMethodCurrent));
			
			String sMethodCurrent0 = ReflectCodeZZZ.getMethodCurrentName(0); //
			assertTrue(sMethodCurrent.equals(sMethodCurrent0));
			
			
			//Merke: Von dieser Testfunktion aus, ist keine sinnvolle "calling"-Methode aufrufbar.
			String sMethodCallingMinus1 = ReflectCodeZZZ.getMethodCallingName(-1);			
			assertTrue(sMethodCallingMinus1.equals(sMethodCurrent0));
			//+++++++++++++++++++++++++++++++++++++
			
			int iLineCurrentInPosition  = ReflectCodeZZZ.getMethodCurrentLine(0,1);
			String  sPositionCurrent = ReflectCodeZZZ.getPositionCurrent(); //Das ist dann keine meiner Klassen, sondern von sun, etc.
			assertNotNull("Fehler beim Ermitteln der aktuellen CodePosition (NULL)", sPositionCurrent);						
			//20240508: Da nun die Logzeilen für das Protokol mit LogStringZZZ errechnet werden, gilt:
			//          - Der String ist formatierbar
			//          - Alles was Objektbezogen ist, wird in das Format gepackt.
			//          - Es wird die Position in der Java-Datei zusätzlich ausgegeben. In der Form, dass diese in der Eclipse Konsole anclickbar ist.
			//FAZIT 20240508: Hier nicht mehr auf den Klassennamen pruefen.
			//assertTrue("Fehler beim Ermitteln der aktuellen CodePosition: '" + stemp + "' wurde nicht erwartet (Klassenname).", stemp.startsWith(sClassCurrent) && stemp.length() > sClassCurrent.length());			
			//assertTrue("Fehler beim Ermitteln der aktuellen CodePosition: '" + stemp + "' wurde nicht erwartet (Zeilennummer).", stemp.endsWith(sMethodCurrentLined) && stemp.length() > sMethodCurrentLined.length());
			
			//wg geaendert ab 20240508:
			assertFalse("Fehler beim Ermitteln der aktuellen CodePosition: '" + sPositionCurrent + "' wurde nicht erwartet (Klassenname sollte jetzt ueber LogStringZZZ protokolliert werden).", sPositionCurrent.startsWith(sClassCurrent) && sPositionCurrent.length() > sClassCurrent.length());
			assertTrue("Fehler beim Ermitteln der aktuellen CodePosition: '" + sPositionCurrent + "' wurde nicht erwartet (Die Zeilennummer "+ iLineCurrentInPosition + "sollte enthalten sein.).", sPositionCurrent.contains(Integer.toString(iLineCurrentInPosition)));
			
			assertTrue("Fehler beim Ermitteln der aktuellen CodePosition: '" + sPositionCurrent + "' wurde nicht erwartet (Der Methodenname sollte jetzt am Anfang stehen).", sPositionCurrent.startsWith(sMethodCurrent) && sPositionCurrent.length() > sMethodCurrent.length());
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		} 
	}
	
	public void testGetCallingStack(){
		try{
			//Ermittle aus dem Stacktrace den Klassennamen.Methodennamen. 
			//Dies kann in diversen Möglichkeiten als Schlüssel für eine HashMap verwendet werden.
			String[]saCalling = ReflectCodeKernelZZZ.getCallingStack();
			
			//Weil nur die aufrufende  Test-Klasse darin auftauchen soll gilt:
			assertTrue("Es sollte nur die aufrufende Testklasse im Ergebnis sein.", saCalling.length==1);
			String sCalling = saCalling[0];
			String scallingMethod = ReflectCodeZZZ.getClassCurrentName() + "." + ReflectCodeZZZ.getMethodCurrentName();
			assertTrue("Es sollte die aufrufende Testklasse im Ergebnis sein", sCalling.equals(scallingMethod));
			
			String[]saCalling02 = ReflectCodeZZZ.getCallingStack();
			assertTrue("Es sollte mehr als nur die aufrufende Testklasse im Ergebnis sein.", saCalling02.length>saCalling.length);
	} catch (ExceptionZZZ ez) {
		fail("Method throws an exception." + ez.getMessageLast());
	} 
	}
	
	
}//END Class