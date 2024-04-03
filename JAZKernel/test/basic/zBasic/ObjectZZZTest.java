package basic.zBasic;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.regexp.RE;
import org.apache.regexp.RESyntaxException;

import junit.framework.TestCase;
import basic.javagently.Stream;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.AbstractObjectWithFlagZZZ;
import basic.zBasic.util.file.FileEasyZZZ;
import basic.zBasic.util.file.FileTextParserZZZ;

public class ObjectZZZTest extends TestCase{
	
	private DummyTestObjectWithFlagZZZ objObjectTest = null;

	protected void setUp(){
		//try {			
			
			//The main object used for testing
			objObjectTest = new DummyTestObjectWithFlagZZZ();
			
		
//		} catch (ExceptionZZZ e) {
//			fail("Method throws an exception." + e.getMessageLast());
//		} 
	}//END setup
	 
	
	public void testConstructor(){
		try{
			//Init - Object
			String[] saFlag = {"init"};
			DummyTestObjectWithFlagZZZ objObjectInit = new DummyTestObjectWithFlagZZZ(saFlag);
			assertTrue(objObjectInit.getFlag("init")==true); 
			
			
			//TestKonfiguration pr�fen
			assertFalse(objObjectTest.getFlag("init")==true); //Nun wäre init falsch
		}catch(ExceptionZZZ ez){
			fail("An exception happend testing: " + ez.getDetailAllLast());
		}
	}
	
	public void testGetFlagZ(){
		try{
		//Init - Object
		String[] saFlag = {"init"};
		DummyTestObjectWithFlagZZZ objObjectInit = new DummyTestObjectWithFlagZZZ(saFlag);
		assertTrue(objObjectInit.getFlag("init")==true); 
		
		
		//TestKonfiguration prüfen.
		//1. Hole alle FlagZ des Objekts
		String[] saTest01 = objObjectInit.getFlagZ();
		assertNotNull(saTest01);		
		assertTrue("Es wurden auf dieser Ebenen der Objekthierarchie 3 FlagZ erwartet: DEBUG und INIT.",saTest01.length==3);
		assertTrue(saTest01[0].equals("DUMMY"));
		assertTrue(saTest01[1].equals("DEBUG"));
		assertTrue(saTest01[2].equals("INIT"));
		
		
		//2. Hole alle FlagZ Einträge, die entsprechend true/false gesetzt sind.
		String[]saTest02 = objObjectInit.getFlagZ(true);
		assertNotNull(saTest02);		
		assertTrue("Es wurden auf dieser Ebenen der Objekthierarrchie nur 1 FlagZ für 'true' erwartet: INIT.",saTest02.length==1);
				
		String[]saTest02b = objObjectInit.getFlagZ(false);
		assertNotNull(saTest02b);		
		assertTrue("Es wurden auf dieser Ebenen der Objekthierarrchie 2 FlagZ für 'false' erwartet: DEBUG.",saTest02b.length==2);
		assertTrue(saTest02b[0].equals("DUMMY"));
		assertTrue(saTest02b[1].equals("DEBUG"));
		
		objObjectInit.setFlag("DEBUG", true);
		String[]saTest02c = objObjectInit.getFlagZ(false);
		assertNotNull(saTest02c);		
		assertTrue("Es wurden auf dieser Ebenen der Objekthierarrchie JETZT EIN FLAG WENIGER für 'false' erwartet.",saTest02c.length==saTest02b.length-1);
				
		objObjectInit.setFlag("DUMMY", true);
		String[]saTest02d = objObjectInit.getFlagZ(false);
		assertNotNull(saTest02d);		
		assertTrue("Es wurden auf dieser Ebenen der Objekthierarrchie JETZT KEIN FLAG MEHR für 'false' erwartet.",saTest02d.length==0);
	}catch(ExceptionZZZ ez){
		fail("An exception happend testing: " + ez.getDetailAllLast());
	}
		
	}
	

}//END Class
