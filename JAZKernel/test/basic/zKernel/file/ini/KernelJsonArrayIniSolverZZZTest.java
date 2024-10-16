package basic.zKernel.file.ini;

import java.util.ArrayList;
import java.util.Vector;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractList.ArrayListExtendedZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernel.KernelZZZ;
import junit.framework.TestCase;

public class KernelJsonArrayIniSolverZZZTest extends TestCase {	
	public final static String sEXPRESSION_JSONARRAY01_DEFAULT = "<Z><JSON><JSON:ARRAY>[\"TESTWERT2DO2JSON01\",\"TESTWERT2DO2JSON02\"]</JSON:ARRAY></JSON></Z>";
	
	private IKernelZZZ objKernel;
	
	/// +++ Die eigentlichen Test-Objekte	
	private KernelJsonArrayIniSolverZZZ objExpressionSolver;
	private KernelJsonArrayIniSolverZZZ objExpressionSolverInit;
	
	

	protected void setUp(){
		try {			
						
			objKernel = new KernelZZZ("FGL", "01", "test", "ZKernelConfigKernel_test.ini",(String)null);
			
			//#### Ein init TestObjekt
			String[] saFlagInit = {"init"};
			objExpressionSolverInit = new KernelJsonArrayIniSolverZZZ(objKernel, saFlagInit);
			
			String[] saFlag = {""};
			objExpressionSolver = new KernelJsonArrayIniSolverZZZ(objKernel, saFlag);
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
		
		bFlagAvailable = objExpressionSolver.setFlag("usejson_array", false); //Ansonsten wird der Wert sofort ausgerechnet
		assertTrue("Das Flag 'usejson_array' sollte zur Verfügung stehen.", bFlagAvailable);
		

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
			
			String sLineWithExpression = sEXPRESSION_JSONARRAY01_DEFAULT;
			
			//### Teilberechnungen durchführen
			Vector<String> vecReturn = objExpressionSolver.parseFirstVector(sLineWithExpression);
			assertFalse(StringZZZ.isEmpty(vecReturn.get(1))); //in der 0ten Position ist der String vor der Map, in der 3ten Position ist der String nach der Map.
			
			
			//### Nun die Gesamtberechnung durchführen
			String sValue = objExpressionSolver.parse(sLineWithExpression);
			assertEquals("Ohne Auflösung soll Ausgabe gleich Eingabe sein",sLineWithExpression, sValue);
		
			//Anwenden der ersten Formel
			//TODO GOON: compute soll also einen String zurückgeben, das wird dann die HashMap.toString sein.
			//           Der eigentliche Wert wird aber durch .computeHashMap() zurückgegeben.			
			objExpressionSolver.setFlag("usejson", true); //Damit der Wert sofort ausgerechnet wird
			objExpressionSolver.setFlag("usejson_array", true); //Damit der Wert sofort ausgerechnet wird
			sValue = objExpressionSolver.parse(sLineWithExpression);			
			assertFalse("Mit Auflösung soll Ausgabe anders als Eingabe sein.",sLineWithExpression.equals(sValue));
			System.out.println(ReflectCodeZZZ.getPositionCurrent() + "\tDebugausagabe\n" + sValue);
			
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}
	
	/** void, Test: Reading an entry in a section of the ini-file
	* Lindhauer; 22.04.2006 12:54:32
	 */
	public void testComputeArray(){
		try {					
			boolean bFlagAvailable = objExpressionSolver.setFlag("usejson", false); //Ansonsten wird der Wert sofort ausgerechnet
			assertTrue("Das Flag 'usejson' sollte zur Verfügung stehen.", bFlagAvailable);
			
			String sLineWithExpression = sEXPRESSION_JSONARRAY01_DEFAULT;
			
			//### Teilberechnungen durchführen
			Vector<String> vecReturn = objExpressionSolver.parseFirstVector(sLineWithExpression);
			assertFalse(StringZZZ.isEmpty(vecReturn.get(1))); //in der 0ten Position ist der String vor der Map, in der 3ten Position ist der String nach der Map.
			
			
			//### Nun die Gesamtberechnung durchführen
			ArrayList<String>ls1 = objExpressionSolver.computeArrayList(sLineWithExpression);
			assertTrue("Ohne Auflösung soll es keine HashMap geben",ls1.size()==0);
				
			objExpressionSolver.setFlag("usejson", true); //Damit der Wert sofort ausgerechnet wird
			objExpressionSolver.setFlag("usejson_array", true); //Damit der Wert sofort ausgerechnet wird
			ArrayList<String>ls2 = objExpressionSolver.computeArrayList(sLineWithExpression);
			assertTrue("Mit Auflösung des String soll die HashMap entsprechende Größe haben. ",ls2.size()==2);

			String sValue = ArrayListExtendedZZZ.debugString(ls2);	
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
			
			sExpression = sEXPRESSION_JSONARRAY01_DEFAULT;
			bValue = objExpressionSolver.isExpression(sExpression);
			assertTrue(bValue);
			
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}
	
}//END class
	
