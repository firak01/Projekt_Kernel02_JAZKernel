package basic.zKernel.file.ini;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Vector;

import junit.framework.TestCase;
import basic.javagently.Stream;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractList.HashMapCaseInsensitiveZZZ;
import basic.zBasic.util.abstractList.HashMapExtendedZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.file.FileEasyZZZ;
import basic.zKernel.IKernelConfigSectionEntryZZZ;
import basic.zKernel.KernelZZZ;
import basic.zKernel.file.ini.KernelZFormulaIniSolverZZZ;
import custom.zKernel.LogZZZ;
import custom.zKernel.file.ini.FileIniZZZ;

public class KernelJsonMapIniSolverZZZTest extends TestCase {	
	protected final static String sEXPRESSION_JSONMAP01_DEFAULT = "<JSON><JSON:MAP>{\"UIText01\":\"TESTWERT2DO2JSON01\",\"UIText02\":\"TESTWERT2DO2JSON02\"}</JSON:MAP></JSON>";
	
	
	private KernelZZZ objKernel;
	
	/// +++ Die eigentlichen Test-Objekte	
	private KernelJsonMapIniSolverZZZ objExpressionSolver;
	private KernelJsonMapIniSolverZZZ objExpressionSolverInit;
	
	

	protected void setUp(){
		try {			
						
			objKernel = new KernelZZZ("FGL", "01", "test", "ZKernelConfigKernel_test.ini",(String)null);
			
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
		try{							
		assertTrue(objExpressionSolverInit.getFlag("init")==true);
		assertFalse(objExpressionSolver.getFlag("init")==true); //Nun wäre init falsch
		
		boolean bFlagAvailable = objExpressionSolver.setFlag("usejson", false); //Ansonsten wird der Wert sofort ausgerechnet
		assertTrue("Das Flag 'usejson' sollte zur Verfügung stehen.", bFlagAvailable);
		
		bFlagAvailable = objExpressionSolver.setFlag("usejson_map", false); //Ansonsten wird der Wert sofort ausgerechnet
		assertTrue("Das Flag 'usejson_map' sollte zur Verfügung stehen.", bFlagAvailable);
		

		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}
	
	/** void, Test: Reading an entry in a section of the ini-file
	* Lindhauer; 22.04.2006 12:54:32
	 */
	public void testCompute(){
		try {					
			boolean bFlagAvailable = objExpressionSolver.setFlag("usejson", false); //Ansonsten wird der Wert sofort ausgerechnet
			assertTrue("Das Flag 'usejson' sollte zur Verfügung stehen.", bFlagAvailable);
			
			String sLineWithExpression = sEXPRESSION_JSONMAP01_DEFAULT;
			
			//### Teilberechnungen durchführen
			Vector<String> vecReturn = objExpressionSolver.computeExpressionFirstVector(sLineWithExpression);
			assertFalse(StringZZZ.isEmpty(vecReturn.get(1))); //in der 0ten Position ist der String vor der Map, in der 3ten Position ist der String nach der Map.
			
			
			//### Nun die Gesamtberechnung durchführen
			String sValue = objExpressionSolver.compute(sLineWithExpression);
			assertEquals("Ohne Auflösung soll Ausgabe gleich Eingabe sein",sLineWithExpression, sValue);
		
			//Anwenden der ersten Formel
			//TODO GOON: compute soll also einen String zurückgeben, das wird dann die HashMap.toString sein.
			//           Der eigentliche Wert wird aber durch .computeHashMap() zurückgegeben.			
			objExpressionSolver.setFlag("usejson", true); //Damit der Wert sofort ausgerechnet wird
			objExpressionSolver.setFlag("usejson_map", true); //Damit der Wert sofort ausgerechnet wird
			sValue = objExpressionSolver.compute(sLineWithExpression);			
			assertFalse("Mit Auflösung soll Ausgabe anders als Eingabe sein.",sLineWithExpression.equals(sValue));
			System.out.println(ReflectCodeZZZ.getPositionCurrent() + "\tDebugausagabe\n" + sValue);
			
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}
	
	/** void, Test: Reading an entry in a section of the ini-file
	* Lindhauer; 22.04.2006 12:54:32
	 */
	public void testComputeHashMap(){
		try {					
			boolean bFlagAvailable = objExpressionSolver.setFlag("usejson", false); //Ansonsten wird der Wert sofort ausgerechnet
			assertTrue("Das Flag 'usejson' sollte zur Verfügung stehen.", bFlagAvailable);
			
			String sLineWithExpression = sEXPRESSION_JSONMAP01_DEFAULT;
			
			//### Teilberechnungen durchführen
			Vector<String> vecReturn = objExpressionSolver.computeExpressionFirstVector(sLineWithExpression);
			assertFalse(StringZZZ.isEmpty(vecReturn.get(1))); //in der 0ten Position ist der String vor der Map, in der 3ten Position ist der String nach der Map.
			
			
			//### Nun die Gesamtberechnung durchführen
			HashMap<String,String>hm1 = objExpressionSolver.computeHashMap(sLineWithExpression);
			assertTrue("Ohne Auflösung soll es keine HashMap geben",hm1.size()==0);
				
			objExpressionSolver.setFlag(IKernelJsonIniSolverZZZ.FLAGZ.USEJSON, true); //Damit der Wert sofort ausgerechnet wird
			//Aber ohne Map-Flag wird noch nix gefunden...
			HashMap<String,String>hmDummy = objExpressionSolver.computeHashMap(sLineWithExpression);
			assertTrue("Ohne Auflösung soll es keine HashMap geben",hmDummy.size()==0);
						
			objExpressionSolver.setFlag(IKernelJsonIniSolverZZZ.FLAGZ.USEJSON_MAP, true); //Damit der Wert sofort ausgerechnet wird			
			HashMap<String,String>hm2 = objExpressionSolver.computeHashMap(sLineWithExpression);
			assertTrue("Mit Auflösung des String soll die HashMap entsprechende Größe haben. ",hm2.size()==2);

			String sValue = HashMapExtendedZZZ.debugString(hm2);	
			System.out.println(ReflectCodeZZZ.getPositionCurrent() + "\tDebugausagabe\n" + sValue);
			
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}
	
	public void testJson() {
		try {			
			String sExpression = "<JSON>kljkljlkjklj</JSON>";
			boolean bValue = objExpressionSolver.isExpression(sExpression);
			assertFalse(bValue);
			
			sExpression = sEXPRESSION_JSONMAP01_DEFAULT;
			bValue = objExpressionSolver.isExpression(sExpression);
			assertTrue(bValue);
			
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}
	
}//END class
	
