package basic.zKernel.file.ini;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Hashtable;

import junit.framework.TestCase;
import basic.javagently.Stream;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractList.HashMapCaseInsensitiveZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.file.FileEasyZZZ;
import basic.zKernel.IKernelConfigSectionEntryZZZ;
import basic.zKernel.KernelZZZ;
import basic.zKernel.file.ini.KernelExpressionIniSolverZZZ;
import custom.zKernel.LogZZZ;
import custom.zKernel.file.ini.FileIniZZZ;

public class KernelJsonMapIniSolverZZZTest extends TestCase {	
	private final static String sEXPRESSION01_DEFAULT = "<JSON><JSON:MAP>{\"UIText01\":\"TESTWERT2DO2JSON01\",\"UIText02\":\"TESTWERT2DO2JSON02\"}<JSON:MAP></JSON>";
	
	
	private KernelZZZ objKernel;
	
	/// +++ Die eigentlichen Test-Objekte	
	private KernelJsonMapIniSolverZZZ objExpressionSolver;
	private KernelJsonMapIniSolverZZZ objExpressionSolverInit;
	
	

	protected void setUp(){
		try {			
						
			//#### Ein init TestObjekt
			String[] saFlagInit = {"init"};
			objExpressionSolverInit = new KernelJsonMapIniSolverZZZ(objKernel, saFlagInit);
			
			String[] saFlag = {""};
			objExpressionSolver = new KernelJsonMapIniSolverZZZ(objKernel, saFlag);
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		} 
//		catch (FileNotFoundException e) {			
//			e.printStackTrace();
//		} catch (IOException e) {			
//			e.printStackTrace();
//		}		
	}//END setup
	
	public void testFlagHandling(){
		//try{
				
		assertTrue(objExpressionSolverInit.getFlag("init")==true);
		assertFalse(objExpressionSolver.getFlag("init")==true); //Nun wäre init falsch

//		} catch (ExceptionZZZ ez) {
//			fail("Method throws an exception." + ez.getMessageLast());
//		}
	}
	
	/** void, Test: Reading an entry in a section of the ini-file
	* Lindhauer; 22.04.2006 12:54:32
	 */
	public void testCompute(){
		try {					
			objExpressionSolver.setFlag("usejson", false); //Ansonsten wird der Wert sofort ausgerechnet
			String sExpression = sEXPRESSION01_DEFAULT;
			String sValue = objExpressionSolver.compute(sExpression);
			assertEquals("Ohne Auflösung soll Ausgabe gleich Eingabe sein",sExpression, sValue);
		
			//Anwenden der ersten Formel
			objExpressionSolver.setFlag("usejson", true); //Damit der Wert sofort ausgerechnet wird
			sValue = objExpressionSolver.compute(sExpression);			
			assertFalse("Mit Auflösung soll Ausgabe anders als Eingabe sein.",sExpression.equals(sValue));
			
			//TODO GOON: compute soll also einen String zurückgeben, das wird dann die HashMap.toString sein.
			//           Der eigentliche Wert wird aber durch .computeHashMap() zurückgegeben.
			
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}
	
	public void testJson() {
//		try {			
			String sExpression = "<JSON>kljkljlkjklj</JSON>";
			boolean bValue = objExpressionSolver.isExpression(sExpression);
			assertFalse(bValue);
			
			sExpression = sEXPRESSION01_DEFAULT;
			bValue = objExpressionSolver.isExpression(sExpression);
			assertTrue(bValue);
			
//		} catch (ExceptionZZZ ez) {
//			fail("Method throws an exception." + ez.getMessageLast());
//		}
	}
	
}//END class
	
