package basic.zKernel.file.ini;

import java.util.HashMap;
import java.util.Vector;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractList.HashMapExtendedZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zKernel.IKernelConfigSectionEntryZZZ;
import basic.zKernel.KernelZZZ;
import basic.zKernel.config.KernelConfigSectionEntryUtilZZZ;
import junit.framework.TestCase;

public class KernelJsonMapIniSolverZZZTest extends TestCase {	
	protected final static String sEXPRESSION_JSONMAP01_CONTENT = "{\"UIText01\":\"TESTWERT2DO2JSON01\",\"UIText02\":\"TESTWERT2DO2JSON02\"}";
	protected final static String sEXPRESSION_JSONMAP01_DEFAULT = "<Z><JSON><JSON:MAP>" + KernelJsonMapIniSolverZZZTest.sEXPRESSION_JSONMAP01_CONTENT +"</JSON:MAP></JSON></Z>";
	
	
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
		String sValue; String sExpression; String sExpressionSource; String sExpressionSource2;String sExpessionSourceFormulaMath;
		String sTagStartZ;	String sTagEndZ;
		boolean btemp; IKernelConfigSectionEntryZZZ objEntryTemp;
		sExpressionSource = KernelJsonMapIniSolverZZZTest.sEXPRESSION_JSONMAP01_DEFAULT;;
		
		try {			
			btemp = objExpressionSolver.setFlag(IIniTagWithExpressionZZZ.FLAGZ.USEEXPRESSION, true); 
			assertTrue("Flag nicht vorhanden '" + IIniTagWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
			
			btemp = objExpressionSolver.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER,true); 
			assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
			
			btemp = objExpressionSolver.setFlag(IKernelJsonIniSolverZZZ.FLAGZ.USEJSON, false); //Ansonsten wird der Wert sofort ausgerechnet
			assertTrue("Das Flag '"+ IKernelJsonIniSolverZZZ.FLAGZ.USEJSON +" sollte zur Verfügung stehen.", btemp);
			
			btemp = objExpressionSolver.setFlag(IKernelJsonMapIniSolverZZZ.FLAGZ.USEJSON_MAP, false); //Ansonsten wird der Wert sofort ausgerechnet
			assertTrue("Das Flag '"+ IKernelJsonMapIniSolverZZZ.FLAGZ.USEJSON_MAP +" sollte zur Verfügung stehen.", btemp);
			
			
			//### Teilberechnungen durchführen
			Vector<String> vecReturn = objExpressionSolver.parseFirstVector(sExpressionSource);
			assertTrue(StringZZZ.isEmpty(vecReturn.get(0))); //in der 0ten Position ist der String vor der Encryption, in der 3ten Position ist der String nach der Encryption.
			assertFalse(StringZZZ.isEmpty(vecReturn.get(1))); //in der 0ten Position ist der String vor der Encryption, in der 3ten Position ist der String nach der Encryption.
			assertTrue(StringZZZ.isEmpty(vecReturn.get(2))); //in der 0ten Position ist der String vor der Encryption, in der 3ten Position ist der String nach der Encryption.		
			
			//### Gesamtberechnung durchführen
			sTagStartZ = "<Z>";
			sTagEndZ = "</Z>";
			sExpression = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSource, sTagStartZ, sTagEndZ);			
		
			sValue = objExpressionSolver.parse(sExpressionSource);
			assertEquals("Ohne Auflösung soll Ausgabe gleich Eingabe sein",sExpression, sValue);
		
			//Entry auswerten
			objEntryTemp = objExpressionSolver.getEntry();
			assertFalse(objEntryTemp.isJson());
			assertFalse(objEntryTemp.isJsonMap());
			
			//### Anwenden der ersten Formel
			btemp = objExpressionSolver.setFlag(IKernelJsonIniSolverZZZ.FLAGZ.USEJSON, true); //Ansonsten wird der Wert sofort ausgerechnet
			assertTrue("Das Flag '"+ IKernelJsonIniSolverZZZ.FLAGZ.USEJSON +" sollte zur Verfügung stehen.", btemp);
			
			btemp = objExpressionSolver.setFlag(IKernelJsonMapIniSolverZZZ.FLAGZ.USEJSON_MAP, true); //Ansonsten wird der Wert sofort ausgerechnet
			assertTrue("Das Flag '"+ IKernelJsonMapIniSolverZZZ.FLAGZ.USEJSON_MAP +" sollte zur Verfügung stehen.", btemp);
			
			sValue = objExpressionSolver.parse(sExpressionSource);			
			System.out.println(ReflectCodeZZZ.getPositionCurrent() + "\tDebugausagabe: '" + sValue + "'\n");
			assertEquals(sExpression, sValue);

			//Entry auswerten
			objEntryTemp = objExpressionSolver.getEntry();
			assertFalse(objEntryTemp.isJson());
			assertFalse(objEntryTemp.isJsonMap());
			
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}
	
	/** void, Test: Reading an entry in a section of the ini-file
	* Lindhauer; 22.04.2006 12:54:32
	 */
	public void testComputeHashMapFromJson(){
		String sValue; String sExpression; String sExpressionSource; String sExpressionSource2;String sExpessionSourceFormulaMath;
		String sTagStartZ;	String sTagEndZ;
		Vector<String> vecReturn; HashMap<String,String> hm; String sLineWithJson;
		boolean btemp;
		
		try {
			//###############################################################
			//Einfacher Test: Den JSON-Map source String direkt uebernehmen.
			sExpressionSource = KernelJsonMapIniSolverZZZTest.sEXPRESSION_JSONMAP01_CONTENT;
			sExpression = sExpressionSource;
			sLineWithJson = sExpression;
			hm = objExpressionSolver.computeHashMapFromJson(sLineWithJson);
			assertNotNull(hm);
			assertEquals(2,hm.size());
			
			
			//###############################################################
			//Aus dem Source String den reinen Json-Part machen. 
			//False steht für "von aussen nach innen" statt true = "von innen nach aussen".
			sExpressionSource = KernelJsonMapIniSolverZZZTest.sEXPRESSION_JSONMAP01_DEFAULT;;
			
			sTagStartZ = "<Z>";
			sTagEndZ = "</Z>";
			sExpression = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSource, sTagStartZ, sTagEndZ, false);
			
			sTagStartZ = "<JSON>";
			sTagEndZ = "</JSON>";
			sExpression = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpression, sTagStartZ, sTagEndZ, false);
			
			sTagStartZ = "<JSON:MAP>";
			sTagEndZ = "</JSON:MAP>";
			sExpression = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpression, sTagStartZ, sTagEndZ, false);
			
			sLineWithJson = sExpression;
			hm = objExpressionSolver.computeHashMapFromJson(sLineWithJson);
			assertNotNull(hm);
			assertEquals(2,hm.size());
			
			
			//####### Gesamtpaket testen, d.h. mit echt geparsten Werten
			//### Teilberechnungen durchführen			
			btemp = objExpressionSolver.setFlag(IIniTagWithExpressionZZZ.FLAGZ.USEEXPRESSION, true); 
			assertTrue("Flag nicht vorhanden '" + IIniTagWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
			
			btemp = objExpressionSolver.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER,true); 
			assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
			
			btemp = objExpressionSolver.setFlag(IKernelJsonIniSolverZZZ.FLAGZ.USEJSON, false); //Ansonsten wird der Wert sofort ausgerechnet
			assertTrue("Das Flag '"+ IKernelJsonIniSolverZZZ.FLAGZ.USEJSON +" sollte zur Verfügung stehen.", btemp);
			
			btemp = objExpressionSolver.setFlag(IKernelJsonMapIniSolverZZZ.FLAGZ.USEJSON_MAP, false); //Ansonsten wird der Wert sofort ausgerechnet
			assertTrue("Das Flag '"+ IKernelJsonMapIniSolverZZZ.FLAGZ.USEJSON_MAP +" sollte zur Verfügung stehen.", btemp);
									
			vecReturn = objExpressionSolver.parseFirstVector(sExpressionSource);
			assertFalse(StringZZZ.isEmpty(vecReturn.get(1))); //in der 0ten Position ist der String vor der Map, in der 3ten Position ist der String nach der Map.
			
			//ohne gueltigen Json-String wird keine HashMap erzeugt.
			sLineWithJson = vecReturn.get(1); //Leerstring ist kein gültiger JSON String, darum kommt nix raus.
			hm = objExpressionSolver.computeHashMapFromJson(sLineWithJson);
			assertNotNull(hm);
			assertEquals(0,hm.size());
			
			//+++
			btemp = objExpressionSolver.setFlag(IKernelJsonIniSolverZZZ.FLAGZ.USEJSON, true); 
			assertTrue("Das Flag '"+ IKernelJsonIniSolverZZZ.FLAGZ.USEJSON +" sollte zur Verfügung stehen.", btemp);
			
			vecReturn = objExpressionSolver.parseFirstVector(sExpressionSource);
			assertFalse(StringZZZ.isEmpty(vecReturn.get(1))); //in der 0ten Position ist der String vor der Map, in der 3ten Position ist der String nach der Map.
			
			//ohne gueltigen Json-String wird keine HashMap erzeugt.
			sLineWithJson = vecReturn.get(1); //Leerstring ist kein gültiger JSON String, darum kommt nix raus.
			hm = objExpressionSolver.computeHashMapFromJson(sLineWithJson);
			assertNotNull(hm);
			assertEquals(0,hm.size());
									
			//### Nun die Gesamtberechnung durchführen
			btemp = objExpressionSolver.setFlag(IKernelJsonMapIniSolverZZZ.FLAGZ.USEJSON_MAP, true); 
			assertTrue("Das Flag '"+ IKernelJsonMapIniSolverZZZ.FLAGZ.USEJSON_MAP +" sollte zur Verfügung stehen.", btemp);
			
			vecReturn = objExpressionSolver.parseFirstVector(sExpressionSource);
			assertFalse(StringZZZ.isEmpty(vecReturn.get(1))); //in der 0ten Position ist der String vor der Map, in der 3ten Position ist der String nach der Map.
						
			sLineWithJson = vecReturn.get(1); //Leerstring ist kein gültiger JSON String, darum kommt nix raus.
			hm = objExpressionSolver.computeHashMapFromJson(sLineWithJson);
			assertNotNull(hm);
			assertEquals(2,hm.size());
				
			//##### Wert ermitteln
			sValue = HashMapExtendedZZZ.computeDebugString(hm);	
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
	
