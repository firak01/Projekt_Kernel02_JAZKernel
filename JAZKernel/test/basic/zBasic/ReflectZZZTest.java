package basic.zBasic;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.regexp.RE;
import org.apache.regexp.RESyntaxException;

import junit.framework.TestCase;
import basic.javagently.Stream;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ObjectZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.ReflectEnvironmentZZZ;
import basic.zBasic.util.file.FileEasyZZZ;
import basic.zBasic.util.file.FileTextParserZZZ;

public class ReflectZZZTest extends TestCase{
	
	private ObjectZZZ objObjectTest = null;

	protected void setUp(){
		//try {			
			
			//The main object used for testing
			objObjectTest = new ObjectZZZ();
			
		
//		} catch (ExceptionZZZ e) {
//			fail("Method throws an exception." + e.getMessageLast());
//		} 
	}//END setup
	 
	
	public void testEnvironment(){
		//try{
			//Init - Object
			String stemp = ReflectEnvironmentZZZ.getJavaVersionCurrent();
			assertNotNull("Kann nicht auf aktuelle Java-Version zugreifen (NULL)",stemp);
			
			stemp = stemp.trim();
			assertFalse("Kann nicht auf aktuelle Java-Version zugreifen (Leerstring))", stemp.equals(""));
						
			System.out.println("Aktuelle Java-Version=" + stemp);
			boolean btemp = ReflectEnvironmentZZZ.isJava6();
			assertTrue("Version ist nicht Java 6", btemp);
		
//		}catch(ExceptionZZZ ez){
//			fail("An exception happend testing: " + ez.getDetailAllLast());
//		}
	}
	
	public void testIsJavaVersionMainCurrentEqualOrNewerThan(){
		String stemp = ReflectEnvironmentZZZ.getJavaVersionCurrent();
		assertNotNull("Kann nicht auf aktuelle Java-Version zugreifen (NULL)",stemp);
		
		stemp = stemp.trim();
		assertFalse("Kann nicht auf aktuelle Java-Version zugreifen (Leerstring))", stemp.equals(""));
		
		boolean btemp = ReflectEnvironmentZZZ.isJavaVersionMainCurrentEqualOrNewerThan(stemp);
		assertTrue("Fehler beim Java-Versionsvergleich",btemp);//Da man die aktuelle Version mit der aktuellen Version selbst vergleicht, muss einfach true herauskommen
		
	}
	
	
	public void testGetMethodCurrentName(){
		String sFunction = "testGetMethodCurrentName";
		String  stemp = ReflectCodeZZZ.getMethodCurrentName();
		assertNotNull("Fehler beim Ermitteln des aktuellen Methodennamens (NULL)", stemp);
		assertTrue("Fehler beim Ermitteln des aktuellen Methodennamens: '" + stemp + "' wurde nicht erwartet.", stemp.equals(sFunction));
		
		stemp = ReflectCodeZZZ.getMethodCurrentNameLined(0);
		assertNotNull("Fehler beim Ermitteln des aktuellen Methodennamens mit Zeilennummer (NULL)", stemp);
		assertTrue("Fehler beim Ermitteln des aktuellen Methodennamens: '" + stemp + "' wurde nicht erwartet (Zeilennummer).", stemp.startsWith(sFunction) && stemp.length() > sFunction.length());
	}
	
	public void testGetClassCurrentName(){
		String sClass = this.getClass().getName();
		String  stemp = ReflectCodeZZZ.getClassCurrentName();
		assertNotNull("Fehler beim Ermitteln des aktuellen Klassennamens (NULL)", stemp);
		assertTrue("Fehler beim Ermitteln des aktuellen Klassennamens: '" + stemp + "' wurde nicht erwartet.", stemp.equals(sClass));
		
	}
	
	public void testGetClassCallingName(){
		String sClassCurrent = this.getClass().getName(); 
		String  stemp = ReflectCodeZZZ.getClassCallingName(); //Das ist dann keine meiner Klassen, sondern von sun, etc.
		assertNotNull("Fehler beim Ermitteln des aufrufenden Klassennamens (NULL)", stemp);
		assertFalse("Fehler beim Ermitteln des aufrufenden Klassennamens: '" + stemp + "' wurde nicht erwartet.", stemp.equals(sClassCurrent));
		
	}
	
	public void testGetPositionCurrent(){
		String sClassCurrent = this.getClass().getName(); 
		String sMethodCurrentLined = ReflectCodeZZZ.getMethodCurrentNameLined(2);//2 ist das Offset, weil die Zeilennummer erste f�r 2 Zeilen sp�ter berechnet werden soll. 
		
		String  stemp = ReflectCodeZZZ.getPositionCurrent(); //Das ist dann keine meiner Klassen, sondern von sun, etc.
		assertNotNull("Fehler beim Ermitteln der aktuellen CodePosition (NULL)", stemp);
		
		assertTrue("Fehler beim Ermitteln der aktuellen CodePosition: '" + stemp + "' wurde nicht erwartet (Klassenname).", stemp.startsWith(sClassCurrent) && stemp.length() > sClassCurrent.length());
		assertTrue("Fehler beim Ermitteln der aktuellen CodePosition: '" + stemp + "' wurde nicht erwartet (Zeilennummer).", stemp.endsWith(sMethodCurrentLined) && stemp.length() > sMethodCurrentLined.length());
	}
	
	
}//END Class
