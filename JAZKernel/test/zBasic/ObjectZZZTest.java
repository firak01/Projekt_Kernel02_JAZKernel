package zBasic;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.regexp.RE;
import org.apache.regexp.RESyntaxException;

import junit.framework.TestCase;
import basic.javagently.Stream;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ObjectZZZ;
import basic.zBasic.util.file.FileEasyZZZ;
import basic.zBasic.util.file.FileTextParserZZZ;

public class ObjectZZZTest extends TestCase{
	
	private ObjectZZZ objObjectTest = null;

	protected void setUp(){
		//try {			
			
			//The main object used for testing
			objObjectTest = new ObjectZZZ();
			
		
//		} catch (ExceptionZZZ e) {
//			fail("Method throws an exception." + e.getMessageLast());
//		} 
	}//END setup
	 
	
	public void testConstructor(){
		//try{
			//Init - Object
			String[] saFlag = {"init"};
			ObjectZZZ objObjectInit = new ObjectZZZ(saFlag);
			assertTrue(objObjectInit.getFlag("init")==true); 
			
			
			//TestKonfiguration prüfen
			assertFalse(objObjectTest.getFlag("init")==true); //Nun wäre init falsch
//		}catch(ExceptionZZZ ez){
//			fail("An exception happend testing: " + ez.getDetailAllLast());
//		}
	}
	

}//END Class
