package basic.zKernel;

import java.io.IOException;

import junit.framework.TestCase;
import basic.zBasic.ExceptionZZZ;
import basic.zKernel.KernelPropertyZZZ;

public class KernelPropertyZZZTest extends TestCase{
	/** wird in der TestSuite AllTest zusammengefasst.
	 * Darum gibt es hier keine Main - Methode.
	 * Die Main Methode von AllTest kann ausgef�hrt werden.
	 */
	private KernelPropertyZZZ objPropertyTest;
	
	protected void setUp(){
		try {
			
			//TODO: Diese Datei zuvor per Programm erstellen
			//objPropertyTest = KernelPropertyZZZ.getInstance("c:\\fglKernel\\KernelTest\\JUnitTest.property");
			objPropertyTest = KernelPropertyZZZ.getInstance(".\\JUnitTest.property");
			
		//} catch (ExceptionZZZ ez) {
		//	fail("Method throws an exception." + ez.getMessageLast());
		} catch (IOException e){
			fail("Method throws an IOException." + e.getMessage());
		}
	}
	
	protected void tearDown(){
		//NULL SETZEN REICHT NICHT, DAS OBJEKT MUSS WOHL KOMPLETT ZERST�RT WERDEN   objPropertyTest;  //Sonst wird immer das gleiche Objekt wiiederverwendet, das hat f�r die Tests die negativen Auswirkungen, das keine Datei erneut eingelsen wird, weil ja schon mal vorher eingelesen wurde.
		objPropertyTest.finalize();
		//objPropertyTest=null;
		//scheint nicht mehr notwendig. System.gc();  //Danach explizit den Garbage Collector aufrufen		
	}
	
	
	public void testContructor(){
		try{
		assertFalse(objPropertyTest.getFlag("init")==true);
		 
		int iFileLoaded = objPropertyTest.getFileLoadedCounter();
		assertEquals(1, iFileLoaded);
		
		//Nun das testen, was den "Singleton ausmacht"
		//A) die gleiche Datei erneut einlesen
		//KernelPropertyZZZ objProperty = KernelPropertyZZZ.getInstance("c:\\fglKernel\\KernelTest\\JUnitTest.property");
		KernelPropertyZZZ objProperty = KernelPropertyZZZ.getInstance(".\\JUnitTest.property");
		assertNotNull(objProperty);
		assertEquals("The new object is not equal to the former instance. This seems to be no SINGLETON.", objProperty, objPropertyTest);
		
		iFileLoaded = objPropertyTest.getFileLoadedCounter();
		assertEquals(1, iFileLoaded); //Nun soll bei der gleichen Datei nat�rlich nicht die anzahl der verarbeiteten Properties erh�ht werden.
		
		
		//B) Eine andere Datei einlesen
		//KernelPropertyZZZ objPropertySecond = KernelPropertyZZZ.getInstance("c:\\fglKernel\\KernelTest\\JUnitTestSecond.property");
		KernelPropertyZZZ objPropertySecond = KernelPropertyZZZ.getInstance(".\\JUnitTestSecond.property");
		assertNotNull(objPropertySecond);
		assertEquals("The new object is not equal to the former instance. This seems to be no SINGLETON.", objPropertySecond, objPropertyTest);
		
		iFileLoaded = objPropertyTest.getFileLoadedCounter();
		assertEquals(2, iFileLoaded); //Nun wurde die Anzahl der eingelesenen Property Dateien um 1 erh�ht.
		
		
		//C) Keine Datei einlesen, einfach so eine Instanz bekommen
		KernelPropertyZZZ objPropertyThird = KernelPropertyZZZ.getInstance();
		assertNotNull(objPropertyThird);
		assertEquals("The new object is not equal to the former instance. This seems to be no SINGLETON.", objPropertyThird, objPropertyTest);
		
		iFileLoaded = objPropertyTest.getFileLoadedCounter();
		assertEquals(2, iFileLoaded); //Nun soll bei der gleichen Datei nat�rlich nicht die anzahl der verarbeiteten Properties erh�ht werden.
		
		

		}catch (ExceptionZZZ ez){
			fail("Method throws an ExceptionZZZ: '"+ ez.getDetailAllLast() + "'");
		}catch(IOException e){
			fail("Method throws an IOException: '" + e.getMessage() + "'");
		}
		
}//END Function
	
	public void testGetProperty(){
		try{
			int iFileLoaded = 0 ;
			
		//Dieses File wurde im Constructor eingelesen
		//String stemp = objPropertyTest.getProperty("c:\\fglKernel\\KernelTest\\JUnitTest.property", "Property1");
		String stemp = objPropertyTest.getProperty(".\\JUnitTest.property", "Property1");
		assertEquals("das ist ein TEST", stemp);
		
		iFileLoaded = objPropertyTest.getFileLoadedCounter();
		assertEquals(1, iFileLoaded); //Nun wurde die Anzahl der eingelesenen Property Dateien um 1 erh�ht.
				
		//Dieses File ist neu und wird nun eingelesen
		//String stemp2 = objPropertyTest.getProperty("c:\\fglKernel\\KernelTest\\JUnitTestSecond.property", "Property1");
		String stemp2 = objPropertyTest.getProperty(".\\JUnitTestSecond.property", "Property1");
		assertEquals("das ist ein ZWEITER TEST", stemp2);
		
		iFileLoaded = objPropertyTest.getFileLoadedCounter();
		assertEquals(2, iFileLoaded); //Nun wurde die Anzahl der eingelesenen Property Dateien um 1 erh�ht.
		
		//DIESE FILE EXISTIERT NIcht
		try{
			//objPropertyTest.getProperty("c:\\fglKernel\\KernelTest\\dat git es nicht", "Property1");
			objPropertyTest.getProperty(".\\dat git es nicht", "Property1");
			fail("File 'c:\\fglKernel\\KernelTest\\dat git es nicht' was expected not to exist. Method should have thrown an IOException: '");
		}catch(IOException e){
			//Hier ist der Fehlerfall gew�nscht. Der Test schl�gt Alarm, wenn kein Fehler ausgel�st wird !!!
		}
		
	}catch(IOException e){
		fail("Method throws an IOException: '" + e.getMessage() + "'");
	}
	}
	
}
