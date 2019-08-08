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

public class ReflectEnvironmentZZZTest extends TestCase{
	
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
//		try{
		String stemp = ReflectEnvironmentZZZ.getJavaVersionCurrent();
		assertNotNull("Kann nicht auf aktuelle Java-Version zugreifen (NULL)",stemp);
		
		stemp = stemp.trim();
		assertFalse("Kann nicht auf aktuelle Java-Version zugreifen (Leerstring))", stemp.equals(""));
		
		boolean btemp = ReflectEnvironmentZZZ.isJavaVersionMainCurrentEqualOrNewerThan(stemp);
		assertTrue("Fehler beim Java-Versionsvergleich",btemp);//Da man die aktuelle Version mit der aktuellen Version selbst vergleicht, muss einfach true herauskommen
//		} catch (ExceptionZZZ ez) {
//			fail("Method throws an exception." + ez.getMessageLast());
//		} 		
	}	
}//END Class
