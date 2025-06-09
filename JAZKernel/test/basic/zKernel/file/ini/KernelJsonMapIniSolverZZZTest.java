package basic.zKernel.file.ini;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IObjectWithExpressionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedTestCaseZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedTestFlagsetZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedTestSurroundingZZZ;
import basic.zBasic.util.abstractList.ArrayListExtendedZZZ;
import basic.zBasic.util.abstractList.HashMapExtendedZZZ;
import basic.zBasic.util.abstractList.Vector3ZZZ;
import basic.zBasic.util.abstractList.VectorUtilZZZ;
import basic.zBasic.util.datatype.calling.ReferenceZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zKernel.IKernelConfigSectionEntryZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernel.KernelZZZ;
import basic.zKernel.config.KernelConfigSectionEntryUtilZZZ;
import custom.zKernel.file.ini.FileIniZZZ;
import junit.framework.TestCase;

public class KernelJsonMapIniSolverZZZTest extends TestCase {	
	//Problem dabei: Die Reihenfolge der Einträge in der HashMap ist nicht fest vorgegeben.
	protected final static String sEXPRESSION_JSONMAP01_CONTENT_SOLVED = "{UIText02=TESTWERT2DO2JSON02, UIText01=TESTWERT2DO2JSON01}";
	protected final static String sEXPRESSION_JSONMAP01_SOLVED = "<Z>" + KernelJsonMapIniSolverZZZTest.sEXPRESSION_JSONMAP01_CONTENT_SOLVED + "</Z>";
	protected final static String sEXPRESSION_JSONMAP01_CONTENT = "{\"UIText01\":\"TESTWERT2DO2JSON01\",\"UIText02\":\"TESTWERT2DO2JSON02\"}";
	protected final static String sEXPRESSION_JSONMAP01_DEFAULT = "<Z><JSON><JSON:MAP>" + KernelJsonMapIniSolverZZZTest.sEXPRESSION_JSONMAP01_CONTENT +"</JSON:MAP></JSON></Z>";
	
	
	private File objFile;
	private IKernelZZZ objKernel;
	private FileIniZZZ objFileIniTest=null;	
	
	/// +++ Die eigentlichen Test-Objekte	
	private KernelJsonMapIniSolverZZZ objExpressionSolver;
	private KernelJsonMapIniSolverZZZ objExpressionSolverInit;
	
	protected void setUp(){
		try {			
						
			objKernel = new KernelZZZ("FGL", "01", "test", "ZKernelConfigKernel_test.ini",(String)null);
			
			//#### Ein init TestObjekt
			String[] saFlagInit = {"init"};
			objExpressionSolverInit = new KernelJsonMapIniSolverZZZ(objKernel, saFlagInit);
			
			//#### Das konkrete TestObject				
			objFile = TestUtilZZZ.createKernelFileUsed();


			//Merke: Für diesen Test das konkrete Ini-File an das Test-Objekt uebergeben und sich nicht auf den Kernel selbst beziehen.
			String[] saFlagFileIni= {
							IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION.name(),
							IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER.name(),
							IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH.name(),
							IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE.name(),
							IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER.name(),
							IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA.name(),
							IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH.name(),
							IKernelJsonIniSolverZZZ.FLAGZ.USEJSON.name(),
							IKernelJsonArrayIniSolverZZZ.FLAGZ.USEJSON_ARRAY.name(),
							IKernelJsonMapIniSolverZZZ.FLAGZ.USEJSON_MAP.name()
							}; //Merke: In static Utility-Methoden ist auch wichtig, was im Ini-File für Flags angestellt sind.
			                   //       und nicht nur die Flags vom ExpressionIniHandler
			objFileIniTest = new FileIniZZZ(objKernel,  objFile, saFlagFileIni);
			
			String[] saFlag = {""}; //Die Flags werden in den konkreten Tests an-/ausgeschaltet.
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
		boolean btemp;
		try{							
			btemp = objExpressionSolver.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION, true); //Ansonsten wird der Wert sofort ausgerechnet 
			assertTrue("Flag nicht vorhanden '" + IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
			
			btemp = objExpressionSolver.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH, true);			
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
		
			btemp = objExpressionSolver.setFlag(IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE, true);			
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE + "'", btemp);
			
			btemp = objExpressionSolver.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER, true);
			assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
			
			btemp = objExpressionSolver.setFlag(IKernelJsonIniSolverZZZ.FLAGZ.USEJSON, false);
			assertTrue("Flag nicht vorhanden '" + IKernelJsonIniSolverZZZ.FLAGZ.USEJSON + "'", btemp);
			
			btemp = objExpressionSolver.setFlag(IKernelJsonMapIniSolverZZZ.FLAGZ.USEJSON_MAP, true);//Sollte dann egal sein
			assertTrue("Flag nicht vorhanden '" + IKernelJsonMapIniSolverZZZ.FLAGZ.USEJSON_MAP + "'", btemp);
		
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}
	
	/** void, Test: Reading an entry in a section of the ini-file
	* Lindhauer; 22.04.2006 12:54:32
	 */
	public void testCompute01(){
		String sValue; String sExpressionSolved; String sExpression;
		IKernelConfigSectionEntryZZZ objEntry=null;
		String sTagStartZ;	String sTagEndZ;
		boolean btemp; 
		sExpression = KernelJsonMapIniSolverZZZTest.sEXPRESSION_JSONMAP01_DEFAULT;;
		
		try {			
			btemp = objExpressionSolver.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION, true); 
			assertTrue("Flag nicht vorhanden '" + IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
			
			btemp = objExpressionSolver.setFlag(IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER,true); //ohne aktivierten Parser keine SUBSTITUTION  
			assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER + "'", btemp);

			btemp = objExpressionSolver.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER,true); 
			assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
			
			btemp = objExpressionSolver.setFlag(IKernelJsonIniSolverZZZ.FLAGZ.USEJSON, false); //Ansonsten wird der Wert sofort ausgerechnet
			assertTrue("Das Flag '"+ IKernelJsonIniSolverZZZ.FLAGZ.USEJSON +" sollte zur Verfügung stehen.", btemp);
			
			btemp = objExpressionSolver.setFlag(IKernelJsonMapIniSolverZZZ.FLAGZ.USEJSON_MAP, false); //Ansonsten wird der Wert sofort ausgerechnet
			assertTrue("Das Flag '"+ IKernelJsonMapIniSolverZZZ.FLAGZ.USEJSON_MAP +" sollte zur Verfügung stehen.", btemp);
			
			//######################################################
			//### 1.1. Teilberechnungen durchführen
			Vector<String> vecReturn = objExpressionSolver.parseFirstVector(sExpression);
			assertFalse(StringZZZ.isEmpty(vecReturn.get(0))); //in der 0ten Position ist der Tag vor dem gesuchten String ODER wenn nicht geparst wurde ODER wenn der Tag nicht enthalten ist.
			assertTrue(StringZZZ.isEmpty(vecReturn.get(1))); //in der 1ten Position ist der Tag
			assertTrue(StringZZZ.isEmpty(vecReturn.get(2))); //in der 2ten Position ist der Tag nach dem gesuchten String		
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//zu 1.1. Entry auswerten
			objEntry = objExpressionSolver.getEntry();
			assertTrue(objEntry.isParseCalled());
			assertFalse(objEntry.isJson()); //das hat der Parser alles herausgefunden.
			assertFalse(objEntry.isJsonMap());
			
			//#########################################################
			//### 1.2. Gesamtberechnung durchführen
			sTagStartZ = "<Z>";
			sTagEndZ = "</Z>";
			sExpressionSolved = sExpression;
			//sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpression, sTagStartZ, sTagEndZ);			
		
			sValue = objExpressionSolver.parse(sExpression);
			assertEquals("Ohne Auflösung soll Ausgabe gleich Eingabe sein",sExpressionSolved, sValue);
		
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//zu 1.2. Entry auswerten
			objEntry = objExpressionSolver.getEntry();
			assertTrue(objEntry.isParseCalled());
			assertFalse(objEntry.isJson()); //das hat der Parser alles herausgefunden.
			assertFalse(objEntry.isJsonMap());
			
			//#################################################
			//### 1.3. Anwenden der ohne komplettaufloesung
			btemp = objExpressionSolver.setFlag(IKernelJsonIniSolverZZZ.FLAGZ.USEJSON, true); //Ansonsten wird der Wert sofort ausgerechnet
			assertTrue("Das Flag '"+ IKernelJsonIniSolverZZZ.FLAGZ.USEJSON +" sollte zur Verfügung stehen.", btemp);
			
			btemp = objExpressionSolver.setFlag(IKernelJsonMapIniSolverZZZ.FLAGZ.USEJSON_MAP, false); //Ansonsten wird der Wert sofort ausgerechnet
			assertTrue("Das Flag '"+ IKernelJsonMapIniSolverZZZ.FLAGZ.USEJSON_MAP +" sollte zur Verfügung stehen.", btemp);
			
			sTagStartZ = "<Z>";
			sTagEndZ = "</Z>"; //False steht für "von aussen nach innen" statt true = "von innen nach aussen".
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ, false);			
//		
//			sTagStart = objExpressionSolver.getTagStarting();
//			sTagEnd = objExpressionSolver.getTagClosing(); //False steht für "von aussen nach innen" statt true = "von innen nach aussen".
//			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSource, sTagStart, sTagEnd, false);			
		
//			sExpressionSolved = KernelJsonMapIniSolverZZZTest.sEXPRESSION_JSONMAP01_CONTENT;
//			sExpressionSolved = ExpressionIniUtilZZZ.makeAsExpression(sExpressionSolved, KernelJsonMapIniSolverZZZ.sTAG_NAME);
//			sExpressionSolved = ExpressionIniUtilZZZ.makeAsExpression(sExpressionSolved, KernelJsonIniSolverZZZ.sTAG_NAME);
//			sExpressionSolved = ExpressionIniUtilZZZ.makeAsExpression(sExpressionSolved);
			sValue = objExpressionSolver.solve(sExpression);			
			System.out.println(ReflectCodeZZZ.getPositionCurrent() + "\tDebugausagabe: '" + sValue + "'\n");			
			assertEquals(sExpressionSolved, sValue);

			//++++++++++++++++++++++++++++++++++++++++++++++++++++
			//zu 1.3. Entry auswerten
			objEntry = objExpressionSolver.getEntry();
			assertTrue(objEntry.isParseCalled());
			assertTrue(objEntry.isJson()); //das hat der Parser alles herausgefunden.
			assertFalse(objEntry.isJsonMap());
			
			
			//################################################################
			//### 1.4. Anwenden der Komplettaufloesung
			btemp = objExpressionSolver.setFlag(IKernelJsonIniSolverZZZ.FLAGZ.USEJSON, true); //Ansonsten wird der Wert sofort ausgerechnet
			assertTrue("Das Flag '"+ IKernelJsonIniSolverZZZ.FLAGZ.USEJSON +" sollte zur Verfügung stehen.", btemp);
			
			btemp = objExpressionSolver.setFlag(IKernelJsonMapIniSolverZZZ.FLAGZ.USEJSON_MAP, true); //Ansonsten wird der Wert sofort ausgerechnet
			assertTrue("Das Flag '"+ IKernelJsonMapIniSolverZZZ.FLAGZ.USEJSON_MAP +" sollte zur Verfügung stehen.", btemp);
			
//			sTagStartZ = "<Z>";
//			sTagEndZ = "</Z>";
//			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSource, sTagStartZ, sTagEndZ);			
//		
//			sTagStartZ = objExpressionSolver.getTagStarting();
//			sTagEndZ = objExpressionSolver.getTagClosing();
//			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSource, sTagStartZ, sTagEndZ);			
		
			sExpressionSolved = KernelJsonMapIniSolverZZZTest.sEXPRESSION_JSONMAP01_CONTENT_SOLVED;
//ohne JSON TAG drumherum			sExpressionSolved = ExpressionIniUtilZZZ.makeAsExpression(sExpressionSolved, "JSON");
			sValue = objExpressionSolver.solve(sExpression);			
			System.out.println(ReflectCodeZZZ.getPositionCurrent() + "\tDebugausagabe: '" + sValue + "'\n");
			
			assertEquals(sExpressionSolved, sValue);

			//+++++++++++++++++++++++++++++++++++++++++++
			//zu 1.4. Entry auswerten
			objEntry = objExpressionSolver.getEntry();
			assertTrue(objEntry.isParseCalled());
			//Wenn gesolved wurde kann man das nicht mehr erkennen, also ob die Aenderung am Parsen oder am Solven liegt  ...
			//... das gilt fuer die allgemeinen Testmethoden. 
			//Aber hier geben wir ja einen expliziten String ohne Substitution vor. 
			assertFalse(objEntry.isParsedChanged());
			assertTrue(objEntry.isParsed());
			
			assertTrue(objEntry.isJson());
			assertTrue(objEntry.isJsonMap());
			
			assertTrue(objEntry.isSolveCalled());
			assertTrue(objEntry.isSolvedChanged());
			assertTrue(objEntry.isSolved());
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}
	
	/** void, Test: Reading an entry in a section of the ini-file
	* Lindhauer; 22.04.2006 12:54:32
	 */
	public void testComputeHashMapFromJson(){
		String sValue; String sExpression; String sExpressionSource; String sExpressionSource2;String sExpessionSourceFormulaMath;
		IKernelConfigSectionEntryZZZ objEntry=null;
		String sTagStart;	String sTagEnd;

		String sTagStartZ = "<Z>";
		String sTagEndZ = "</Z>";
		Vector<String> vecReturn; HashMap<String,String> hm; String sLineWithJson;
		boolean btemp;
		
		try {
			
			//######################################
			//A) Einfacher Test: Leerstring
			sExpressionSource = "";
			sExpression = sExpressionSource;
			sLineWithJson = sExpression;
			hm = objExpressionSolver.computeHashMapFromJson(sLineWithJson);
			assertNotNull(hm);
			assertEquals(0,hm.size());//Leerstring ist kein gültiger JSON String, darum kommt nix raus.
			
			//######################################
			//B) Einfacher Test: Dummystring
			sExpressionSource = "dummy";
			sExpression = sExpressionSource;
			sLineWithJson = sExpression;
			hm = objExpressionSolver.computeHashMapFromJson(sLineWithJson);
			assertNotNull(hm);
			assertEquals(0,hm.size());//"dummy" ist kein gültiger JSON-Hashmpa String, darum kommt nix raus.
			
					
			//###############################################################
			//C) Einfacher Test: Den JSON-Map source String direkt uebernehmen.
			sExpressionSource = KernelJsonMapIniSolverZZZTest.sEXPRESSION_JSONMAP01_CONTENT;
			sExpression = sExpressionSource;
			sLineWithJson = sExpression;
			hm = objExpressionSolver.computeHashMapFromJson(sLineWithJson);
			assertNotNull(hm);
			assertEquals(2,hm.size());
			
					
			//###############################################################
			//D) Aus dem Source String den reinen Json-Part machen. 
			//False steht für "von aussen nach innen" statt true = "von innen nach aussen".
			sExpressionSource = KernelJsonMapIniSolverZZZTest.sEXPRESSION_JSONMAP01_DEFAULT;;
			sExpression = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSource, sTagStartZ, sTagEndZ, false);
			
			sTagStart = "<JSON>";
			sTagEnd = "</JSON>";
			sExpression = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpression, sTagStart, sTagEnd, false);
			
			sTagStart = "<JSON:MAP>";
			sTagEnd = "</JSON:MAP>";
			sExpression = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpression, sTagStart, sTagEnd, false);
			
			sLineWithJson = sExpression;
			hm = objExpressionSolver.computeHashMapFromJson(sLineWithJson);
			assertNotNull(hm);
			assertEquals(2,hm.size());
			
			//##############################################################################
			//####### Gesamtpaket testen, d.h. mit echt geparsten Werten
			//### 2.1. Teilberechnungen durchführen - JSON unsolved JSON MAP unsolved	
			btemp = objExpressionSolver.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION, true); 
			assertTrue("Flag nicht vorhanden '" + IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
			
			btemp = objExpressionSolver.setFlag(IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER,true); //ohne aktivierten Parser keine SUBSTITUTION  
			assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER + "'", btemp);

			btemp = objExpressionSolver.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER,true); 
			assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
			
			btemp = objExpressionSolver.setFlag(IKernelJsonIniSolverZZZ.FLAGZ.USEJSON, false); //Ansonsten wird der Wert sofort ausgerechnet
			assertTrue("Das Flag '"+ IKernelJsonIniSolverZZZ.FLAGZ.USEJSON +" sollte zur Verfügung stehen.", btemp);
			
			btemp = objExpressionSolver.setFlag(IKernelJsonMapIniSolverZZZ.FLAGZ.USEJSON_MAP, false); //Ansonsten wird der Wert sofort ausgerechnet
			assertTrue("Das Flag '"+ IKernelJsonMapIniSolverZZZ.FLAGZ.USEJSON_MAP +" sollte zur Verfügung stehen.", btemp);
									
			vecReturn = objExpressionSolver.parseFirstVector(sExpressionSource);
			assertFalse(StringZZZ.isEmpty(vecReturn.get(0))); //in der 0ten Position ist der Tag vor dem gesuchten String ODER wenn nicht geparst wurde ODER wenn der Tag nicht enthalten ist.
			assertTrue(StringZZZ.isEmpty(vecReturn.get(1))); //in der 1ten Position ist der Tag
			assertTrue(StringZZZ.isEmpty(vecReturn.get(2))); //in der 2ten Position ist der Tag nach dem gesuchten String		
			
			//ohne gueltigen Json-String wird keine HashMap erzeugt.
			sLineWithJson = vecReturn.get(1); //Leerstring ist kein gültiger JSON String, darum kommt nix raus.
			System.out.println(ReflectCodeZZZ.getPositionCurrent() + "\tDebugausagabe\nsLineWithJson=" + sLineWithJson);
			hm = objExpressionSolver.computeHashMapFromJson(sLineWithJson);
			assertNotNull(hm);
			assertEquals(0,hm.size());
			
			//+++++++++++++++++++++++++++++++++++++++
			//zu 2.1. Entry auswerten
			objEntry = objExpressionSolver.getEntry();
			assertTrue(objEntry.isParseCalled());
			//Wenn gesolved wurde kann man das nicht mehr erkennen, also ob die Aenderung am Parsen oder am Solven liegt  ...
			//... das gilt fuer die allgemeinen Testmethoden. 
			//Aber hier geben wir ja einen expliziten String ohne Substitution vor. 
			assertFalse(objEntry.isParsedChanged());
			assertTrue(objEntry.isParsed());
			
			assertFalse(objEntry.isJson());     //beide Flags sind auf "not use"
			assertFalse(objEntry.isJsonMap());
			
			assertFalse(objEntry.isSolveCalled()); //solve spielt hier keine Rolle
			assertFalse(objEntry.isSolvedChanged());
			assertFalse(objEntry.isSolved());
			
			
			//###################################################################################
			//### 2.2. Teilberechnungen durchführen - JSON solved, JSON MAP unsolved
			btemp = objExpressionSolver.setFlag(IKernelJsonIniSolverZZZ.FLAGZ.USEJSON, true); 
			assertTrue("Das Flag '"+ IKernelJsonIniSolverZZZ.FLAGZ.USEJSON +" sollte zur Verfügung stehen.", btemp);
			
			btemp = objExpressionSolver.setFlag(IKernelJsonMapIniSolverZZZ.FLAGZ.USEJSON_MAP, false); 
			assertTrue("Das Flag '"+ IKernelJsonMapIniSolverZZZ.FLAGZ.USEJSON_MAP +" sollte zur Verfügung stehen.", btemp);
						
			vecReturn = objExpressionSolver.parseFirstVector(sExpressionSource);
			assertFalse(StringZZZ.isEmpty(vecReturn.get(0))); //in der 0ten Position ist der Tag vor dem gesuchten String ODER wenn nicht geparst wurde ODER wenn der Tag nicht enthalten ist.
			assertTrue(StringZZZ.isEmpty(vecReturn.get(1))); //in der 1ten Position ist der Tag
			assertTrue(StringZZZ.isEmpty(vecReturn.get(2))); //in der 2ten Position ist der Tag nach dem gesuchten String		
						
			//ohne gueltigen Json-String wird keine HashMap erzeugt.
			sLineWithJson = vecReturn.get(1); 
			System.out.println(ReflectCodeZZZ.getPositionCurrent() + "\tDebugausagabe\nsLineWithJson=" + sLineWithJson);
			hm = objExpressionSolver.computeHashMapFromJson(sLineWithJson);
			assertNotNull(hm);
			assertEquals(0,hm.size());
			
			//+++++++++++++++++++++++++++++++++++++++
			//zu 2.2. Entry auswerten
			objEntry = objExpressionSolver.getEntry();
			assertTrue(objEntry.isParseCalled());
			//Wenn gesolved wurde kann man das nicht mehr erkennen, also ob die Aenderung am Parsen oder am Solven liegt  ...
			//... das gilt fuer die allgemeinen Testmethoden. 
			//Aber hier geben wir ja einen expliziten String ohne Substitution vor. 
			assertFalse(objEntry.isParsedChanged());
			assertTrue(objEntry.isParsed());
			
			assertTrue(objEntry.isJson());
			assertFalse(objEntry.isJsonMap()); //JSON MAP Flag "not used"
			
			assertFalse(objEntry.isSolveCalled()); //solve spielt hier keine Rolle
			assertFalse(objEntry.isSolvedChanged());
			assertFalse(objEntry.isSolved());
					
			//######################################################################################
			//### 2.3. Nun die Teilberechnung durchführen JSON unsolved, JSON MAP solved
			btemp = objExpressionSolver.setFlag(IKernelJsonIniSolverZZZ.FLAGZ.USEJSON, false); 
			assertTrue("Das Flag '"+ IKernelJsonIniSolverZZZ.FLAGZ.USEJSON +" sollte zur Verfügung stehen.", btemp);
						
			btemp = objExpressionSolver.setFlag(IKernelJsonMapIniSolverZZZ.FLAGZ.USEJSON_MAP, true); 
			assertTrue("Das Flag '"+ IKernelJsonMapIniSolverZZZ.FLAGZ.USEJSON_MAP +" sollte zur Verfügung stehen.", btemp);
			
			vecReturn = objExpressionSolver.parseFirstVector(sExpressionSource);			
			assertFalse(StringZZZ.isEmpty(vecReturn.get(0))); //in der 0ten Position ist der Tag vor dem gesuchten String ODER wenn nicht geparst wurde ODER wenn der Tag nicht enthalten ist.
			assertFalse(StringZZZ.isEmpty(vecReturn.get(1))); //in der 1ten Position ist der Tag
			assertFalse(StringZZZ.isEmpty(vecReturn.get(2))); //in der 2ten Position ist der Tag nach dem gesuchten String		
					
			//2.3.1
			sLineWithJson = vecReturn.get(1); //ist ein gültiger JSON String
			System.out.println(ReflectCodeZZZ.getPositionCurrent() + "\tDebugausagabe\nsLineWithJson=" + sLineWithJson);
			hm = objExpressionSolver.computeHashMapFromJson(sLineWithJson);
			assertNotNull(hm);
			assertEquals(2,hm.size());
			
			//2.3.2
			sLineWithJson = vecReturn.get(0); //ist kein gültiger JSON String, darum kommt nix raus.
			System.out.println(ReflectCodeZZZ.getPositionCurrent() + "\tDebugausagabe\nsLineWithJson=" + sLineWithJson);
			hm = objExpressionSolver.computeHashMapFromJson(sLineWithJson);
			assertNotNull(hm);
			assertEquals(0,hm.size());//Also der String ist auch kein gueltiger
			
			//zu 2.3.2. Wert ermitteln
			sValue = HashMapExtendedZZZ.computeDebugString(hm);	
			System.out.println(ReflectCodeZZZ.getPositionCurrent() + "\tDebugausagabe\n" + sValue);
						
			//+++++++++++++++++++++++++++++++++++++++
			//zu 2.3. Entry auswerten
			objEntry = objExpressionSolver.getEntry();
			assertTrue(objEntry.isParseCalled());
			//Wenn gesolved wurde kann man das nicht mehr erkennen, also ob die Aenderung am Parsen oder am Solven liegt  ...
			//... das gilt fuer die allgemeinen Testmethoden. 
			//Aber hier geben wir ja einen expliziten String ohne Substitution vor. 
			assertFalse(objEntry.isParsedChanged());
			assertTrue(objEntry.isParsed());
			
			assertTrue(objEntry.isJson()); //!!! obwohl JSON "not used"
			assertTrue(objEntry.isJsonMap());
			
			assertFalse(objEntry.isSolveCalled()); //solve spielt hier keine Rolle
			assertFalse(objEntry.isSolvedChanged());
			assertFalse(objEntry.isSolved());
	
			
			
			//######################################################################################
			//### 2.4. Nun die Teileberechnung durchführen JSON solved, JSON MAP solved
			btemp = objExpressionSolver.setFlag(IKernelJsonIniSolverZZZ.FLAGZ.USEJSON, true); 
			assertTrue("Das Flag '"+ IKernelJsonIniSolverZZZ.FLAGZ.USEJSON +" sollte zur Verfügung stehen.", btemp);
						
			btemp = objExpressionSolver.setFlag(IKernelJsonMapIniSolverZZZ.FLAGZ.USEJSON_MAP, true); 
			assertTrue("Das Flag '"+ IKernelJsonMapIniSolverZZZ.FLAGZ.USEJSON_MAP +" sollte zur Verfügung stehen.", btemp);
			
			vecReturn = objExpressionSolver.parseFirstVector(sExpressionSource);
			assertFalse(StringZZZ.isEmpty(vecReturn.get(1))); //in der 0ten Position ist der String vor der Map, in der 3ten Position ist der String nach der Map.
			
			//2.4.1.
			sLineWithJson = vecReturn.get(0); //ist kein gültiger JSON String, darum kommt nix raus.
			hm = objExpressionSolver.computeHashMapFromJson(sLineWithJson);
			assertNotNull(hm);
			assertEquals(0,hm.size());
			
			//2.4.2.
			sLineWithJson = vecReturn.get(1); //ist ein gültiger JSON String
			hm = objExpressionSolver.computeHashMapFromJson(sLineWithJson);
			assertNotNull(hm);
			assertEquals(2,hm.size());
			
			//zu 2.2. Wert ermitteln
			sValue = HashMapExtendedZZZ.computeDebugString(hm);	
			System.out.println(ReflectCodeZZZ.getPositionCurrent() + "\tDebugausagabe\n" + sValue);
						
			//+++++++++++++++++++++++++++++++++++++++
			//zu 2.2. Entry auswerten
			objEntry = objExpressionSolver.getEntry();
			assertTrue(objEntry.isParseCalled());
			//Wenn gesolved wurde kann man das nicht mehr erkennen, also ob die Aenderung am Parsen oder am Solven liegt  ...
			//... das gilt fuer die allgemeinen Testmethoden. 
			//Aber hier geben wir ja einen expliziten String ohne Substitution vor. 
			assertFalse(objEntry.isParsedChanged());
			assertTrue(objEntry.isParsed());
			
			assertTrue(objEntry.isJson());
			assertTrue(objEntry.isJsonMap());
			
			assertFalse(objEntry.isSolveCalled()); //solve spielt hier keine Rolle
			assertFalse(objEntry.isSolvedChanged());
			assertFalse(objEntry.isSolved());
									
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}
	
	
	//############################################################
	/** void, Test: Reading an entry in a section of the ini-file
	* Lindhauer; 22.04.2006 12:54:32
	 */
	public void testCompute_JsonMap(){
		String sExpressionSource=null; String sExpressionSubstituted; String sExpressionSolved;
//		try {			
			sExpressionSource = KernelJsonMapIniSolverZZZTest.sEXPRESSION_JSONMAP01_DEFAULT;
			sExpressionSubstituted = KernelJsonMapIniSolverZZZTest.sEXPRESSION_JSONMAP01_DEFAULT;
			sExpressionSolved = KernelJsonMapIniSolverZZZTest.sEXPRESSION_JSONMAP01_SOLVED;
			testCompute_JsonMap_(sExpressionSource, sExpressionSubstituted, sExpressionSolved);
//		} catch (ExceptionZZZ ez) {
//			fail("Method throws an exception." + ez.getMessageLast());
//		}
	
	}
	
	/**void, Test: Reading an entry in a section of the ini-file
	 * 
	 * @author Fritz Lindhauer, 05.05.2023, 08:54:30
	 */
	private void testCompute_JsonMap_(String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn){
		
		boolean btemp; int itemp;
		
		String sSection; String sProperty;
		String sExpression; String sExpressionSubstituted; String sExpressionSolved;
		ReferenceZZZ<IKernelConfigSectionEntryZZZ>objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
		IKernelConfigSectionEntryZZZ objEntry=null;
		
		String sValue;
		String sTagStartZ = "<Z>";
		String sTagEndZ = "</Z>";	
		
		try {		
					
			//+++++++ VORGEZOGENER LETZTER FEHLERTEST START
			
			//+++ Mit JsonMap-Berechnung
			

			//+++++++ VORGEZOGENER LETZTER FEHLERTEST ENDE
			
			//###########################
		    //### objExpression
			//#########################
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//+++ 1. Ohne jegliche Expression-Berechnung
			//1a)
			sExpression = sExpressionIn;
			sExpressionSubstituted = sExpressionSubstitutedIn;
			sExpressionSolved = sExpression;
			btemp = testCompute_JsonMap_Unexpressed_(sExpression, sExpressionSubstituted, sExpressionSolved, EnumSetMappedTestSurroundingTypeZZZ.PARSE_KEEP,  EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
			
			//1b)
			sExpression = sExpressionIn;
			sExpressionSubstituted = sExpressionSubstitutedIn;
			sExpressionSolved = sExpression;			
			btemp = testCompute_JsonMap_Unexpressed_(sExpression, sExpressionSubstituted, sExpressionSolved, EnumSetMappedTestSurroundingTypeZZZ.PARSE_REMOVE, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
			
			//1c)
			sExpression = sExpressionIn;
			sExpressionSubstituted = sExpressionSubstitutedIn;
			sExpressionSolved = sExpression; 			
			btemp = testCompute_JsonMap_Unexpressed_(sExpression, sExpressionSubstituted, sExpressionSolved, EnumSetMappedTestSurroundingTypeZZZ.SOLVE_KEEP, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
				
			//1d)
			sExpression = sExpressionIn;
			sExpressionSubstituted = sExpressionSubstitutedIn;
			sExpressionSolved = sExpression;
			btemp = testCompute_JsonMap_Unexpressed_(sExpression, sExpressionSubstituted, sExpressionSolved, EnumSetMappedTestSurroundingTypeZZZ.SOLVE_REMOVE, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
			
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//+++ 2. Ohne jegliche Solver-Berechnung
			//2a)
			sExpression = sExpressionIn;
			sExpressionSubstituted = sExpressionSubstitutedIn;
			sExpressionSolved = sExpression;
			btemp = testCompute_JsonMap_Unsolved_(sExpression, sExpressionSubstituted, sExpressionSolved, EnumSetMappedTestSurroundingTypeZZZ.PARSE_KEEP, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
			
			//2b)
			sExpression = sExpressionIn;
			sExpressionSubstituted = sExpressionSubstitutedIn;
			sExpressionSubstituted = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSubstituted, sTagStartZ, sTagEndZ, false);			
			sExpressionSolved = sExpressionSubstituted;	//Beim Parsen werden, wenn wie hier gewuenscht immer der Z-Tag entfernt.
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ, false);
			btemp = testCompute_JsonMap_Unsolved_(sExpression, sExpressionSubstituted, sExpressionSolved, EnumSetMappedTestSurroundingTypeZZZ.PARSE_REMOVE, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
			
			//2c)
			sExpression = sExpressionIn;
			sExpressionSubstituted = sExpressionSubstitutedIn;
			sExpressionSolved = sExpression; 			
			btemp = testCompute_JsonMap_Unsolved_(sExpression, sExpressionSubstituted, sExpressionSolved, EnumSetMappedTestSurroundingTypeZZZ.SOLVE_KEEP, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
				
			//2d)
			sExpression = sExpressionIn;
			sExpressionSubstituted = sExpressionSubstitutedIn;
			sExpressionSolved = sExpressionSubstituted;	//Beim Parsen werden, wenn wie hier gewuenscht immer der Z-Tag entfernt.
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ, false);
			btemp = testCompute_JsonMap_Unsolved_(sExpression, sExpressionSubstituted, sExpressionSolved, EnumSetMappedTestSurroundingTypeZZZ.SOLVE_REMOVE, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
			
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//+++ 3. Ohne Json-Solver-Berechung
			//3a)
			sExpression = sExpressionIn;
			sExpressionSubstituted = sExpressionSubstitutedIn;
			sExpressionSolved = sExpression;
			btemp = testCompute_JsonMap_JsonUnsolved_(sExpression, sExpressionSubstituted, sExpressionSolved, EnumSetMappedTestSurroundingTypeZZZ.PARSE_KEEP, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
			
			//3b)
			sExpression = sExpressionIn;
			sExpressionSubstituted = sExpressionSubstitutedIn;
			sExpressionSolved = sExpression;
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ, false);
			//Beim Parsen ohne Solver, bleibt sogar das Encryption-Tag drin, auch wenn sonst die Tags entfernt werden.
			btemp = testCompute_JsonMap_JsonUnsolved_(sExpression, sExpressionSubstituted, sExpressionSolved, EnumSetMappedTestSurroundingTypeZZZ.PARSE_REMOVE, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);

			//3c)
			sExpression = sExpressionIn;
			sExpressionSubstituted = sExpressionSubstitutedIn;
			sExpressionSolved = sExpression;			
			btemp = testCompute_JsonMap_JsonUnsolved_(sExpression, sExpressionSubstituted, sExpressionSolved, EnumSetMappedTestSurroundingTypeZZZ.SOLVE_KEEP, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
			
			//3d)
			sExpression = sExpressionIn;
			sExpressionSubstituted = sExpressionSubstitutedIn;
			sExpressionSolved = sExpression;
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ, false);		
			btemp = testCompute_JsonMap_JsonUnsolved_(sExpression, sExpressionSubstituted, sExpressionSolved, EnumSetMappedTestSurroundingTypeZZZ.SOLVE_REMOVE, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
			
			//+++ 4. Ohne JsonMapSolver-Berechung
			//4a)
			sExpression = sExpressionIn;
			sExpressionSubstituted = sExpressionSubstitutedIn;
			sExpressionSolved = sExpression;
			btemp = testCompute_JsonMap_JsonMapUnsolved_(sExpression, sExpressionSubstituted, sExpressionSolved, EnumSetMappedTestSurroundingTypeZZZ.PARSE_KEEP, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
			
			//4b)
			sExpression = sExpressionIn;
			sExpressionSubstituted = sExpressionSubstitutedIn;
			sExpressionSolved = sExpression; 
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ, false);			
			btemp = testCompute_JsonMap_JsonMapUnsolved_(sExpression, sExpressionSubstituted, sExpressionSolved, EnumSetMappedTestSurroundingTypeZZZ.PARSE_REMOVE, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
			
			//4c)
			sExpression = sExpressionIn;
			sExpressionSubstituted = sExpressionSubstitutedIn;
			sExpressionSolved = sExpression;
			btemp = testCompute_JsonMap_JsonMapUnsolved_(sExpression, sExpressionSubstituted, sExpressionSolved, EnumSetMappedTestSurroundingTypeZZZ.SOLVE_KEEP, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
		
			//4d)
			sExpression = sExpressionIn;
			sExpressionSubstituted = sExpressionSubstitutedIn;
			sExpressionSolved = sExpressionSubstituted;	
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ, false);
			btemp = testCompute_JsonMap_JsonMapUnsolved_(sExpression, sExpressionSubstituted, sExpressionSolved, EnumSetMappedTestSurroundingTypeZZZ.SOLVE_REMOVE, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
			
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//+++ 5. Mit JsonMap-Berechnung
			//5a) nur parsen bringt keinen Unterschied, wenn die Tags drinbleiben sollen
			sExpression = sExpressionIn;
			sExpressionSubstituted = sExpressionSubstitutedIn;
			sExpressionSolved = sEXPRESSION_JSONMAP01_CONTENT; //"{\"UIText01\":\"TESTWERT2DO2JSON01\",\"UIText02\":\"TESTWERT2DO2JSON02\"}";
			sExpressionSolved = objExpressionSolver.makeAsExpression(sExpressionSolved);
			sExpressionSolved = ExpressionIniUtilZZZ.makeAsExpression(sExpressionSolved, "JSON");
			sExpressionSolved = ExpressionIniUtilZZZ.makeAsExpression(sExpressionSolved);
			
			//false: d.h. Tags sollen drin bleiben sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ, false);
			btemp = testCompute_JsonMap_JsonMapSolved_(sExpression, sExpressionSubstituted, sExpressionSolved, EnumSetMappedTestSurroundingTypeZZZ.PARSE_KEEP, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);

			//5b)
			sExpression = sExpressionIn;	
			sExpressionSubstituted = sExpressionSubstitutedIn;
			sExpressionSolved = sEXPRESSION_JSONMAP01_CONTENT; //"{\"UIText01\":\"TESTWERT2DO2JSON01\",\"UIText02\":\"TESTWERT2DO2JSON02\"}"; 
			sExpressionSolved = objExpressionSolver.makeAsExpression(sExpressionSolved);
			sExpressionSolved = ExpressionIniUtilZZZ.makeAsExpression(sExpressionSolved, "JSON");
			//den Z-Tag haben wir hier entfernt, also nicht im Ergebnis draufrechen... sExpressionSolved = ExpressionIniUtilZZZ.makeAsExpression(sExpressionSolved);						
			btemp = testCompute_JsonMap_JsonMapSolved_(sExpression, sExpressionSubstituted, sExpressionSolved, EnumSetMappedTestSurroundingTypeZZZ.PARSE_REMOVE, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
			
			//5c)
			sExpression = sExpressionIn;
			sExpressionSubstituted = sExpressionSubstitutedIn;
			sExpressionSolved = sExpressionSolvedIn;//"{UIText02=TESTWERT2DO2JSON02, UIText01=TESTWERT2DO2JSON01}"; //Das ist der Wert von HashMap.toString/(; //sExpressionIn;									
			//Wichtig: JSON und JSON:MAP sollen aus dem Ergebnis weg sein, wg. Aufloesen!!! Auch wenn die umgebenden Z-Tags drin bleiben.  
			btemp = testCompute_JsonMap_JsonMapSolved_(sExpression, sExpressionSubstituted, sExpressionSolved, EnumSetMappedTestSurroundingTypeZZZ.SOLVE_KEEP, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
			
			//5d)
			sExpression = sExpressionIn;
			sExpressionSubstituted = sExpressionSubstitutedIn;
			sExpressionSolved = sExpressionSolvedIn;
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ, false);
			//Wichtig: JSON und JSON:MAP sollen aus dem Ergebnis weg sein, wg. Aufloesen!!! Auch wenn die umgebenden Z-Tags drin bleiben.
			btemp = testCompute_JsonMap_JsonMapSolved_(sExpression, sExpressionSubstituted, sExpressionSolved, EnumSetMappedTestSurroundingTypeZZZ.SOLVE_REMOVE, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
							
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}		
	}
	
	
	private boolean testCompute_JsonMap_Unexpressed_(String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, IEnumSetMappedTestSurroundingZZZ objEnumSurrounding, IEnumSetMappedTestCaseZZZ objEnumTestCase) {
		boolean bReturn = false;
		try {
			boolean btemp; 
			
			String sExpression; String sExpressionSubstituted; String sExpressionSolved;  
			String sValue; String sValueUsed; Vector3ZZZ<String> vecValue;		
			ReferenceZZZ<IKernelConfigSectionEntryZZZ> objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
			IKernelConfigSectionEntryZZZ objEntry=null;
			
			String sTagStartZ = "<Z>";
			String sTagEndZ = "</Z>";		
			
			sExpression = sExpressionIn;
			sExpressionSubstituted = sExpressionSubstitutedIn;
			sExpressionSolved = sExpressionSolvedIn;

			IEnumSetMappedTestFlagsetZZZ objEnumFlagset = EnumSetMappedTestCaseFlagsetTypeZZZ.UNEXPRESSED;
						
			//##########################################
			//### Expression unsolved Fall
			btemp = objExpressionSolver.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION, false); //Ansonsten wird der Wert sofort ausgerechnet 
			assertTrue("Flag nicht vorhanden '" + IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
			
			btemp = objExpressionSolver.setFlag(IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER,true); //ohne aktivierten Parser keine SUBSTITUTION  
			assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER + "'", btemp);
			
			btemp = objExpressionSolver.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH, true);			
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
		
			btemp = objExpressionSolver.setFlag(IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE, true);			
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE + "'", btemp);
			
			btemp = objExpressionSolver.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER, true);//Sollte dann egal sein
			assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
			
			btemp = objExpressionSolver.setFlag(IKernelJsonIniSolverZZZ.FLAGZ.USEJSON, true);//Sollte dann egal sein
			assertTrue("Flag nicht vorhanden '" + IKernelJsonIniSolverZZZ.FLAGZ.USEJSON + "'", btemp);
			
			btemp = objExpressionSolver.setFlag(IKernelJsonMapIniSolverZZZ.FLAGZ.USEJSON_MAP, true);//Sollte dann egal sein
			assertTrue("Flag nicht vorhanden '" + IKernelJsonMapIniSolverZZZ.FLAGZ.USEJSON_MAP + "'", btemp);
			
			boolean bUseExpressionGeneral = objExpressionSolver.isExpressionEnabledGeneral();
			boolean bUseSolver = objExpressionSolver.isSolverEnabledGeneral();
			boolean bUseParser = objExpressionSolver.isParserEnabledGeneral() && objExpressionSolver.isParserEnabledThis();
			
			
			//+++ ... parse ist nicht solve... also wird hier nichts aufgeloest, aussser die Pfade substituiert.
			if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.PARSE)) {	
				sValue = objExpressionSolver.parse(sExpression, objSectionEntryReference, objEnumSurrounding.isSurroundingValueToKeep_OnParse());
				assertEquals(sExpressionSolved, sValue);
				
				//+++ Teilberechnungen durchführen
				//    Es werden nomalerweise die Tags entfernt, aber ohne einen angestellten Solver werden sie beim parsen ignoriert 
				vecValue = objExpressionSolver.parseFirstVector(sExpressionSolved, objEnumSurrounding.isSurroundingValueToKeep_OnParse());					
				sValue = VectorUtilZZZ.implode(vecValue);
				assertEquals(sExpression, sValue); //Beim parse fist Vector wird nie der Z-Tag drum herum entfernt. Das ist Aufgabe von parse().
				
				if(bUseParser && bUseExpressionGeneral) {
					sValue = (String) vecValue.get(1);//in der 0ten Position ist der String vor der Map, in der 3ten Position ist der String nach der Map.
				}else {
					sValue = (String) vecValue.get(0);//in der 0ten Position ist der String, entweder wenn der Tag nicht enthalten ist ODER der Parser (ggfs. entsprechend dem Solver) abgestellt ist
				}
				assertTrue(StringZZZ.contains(sExpressionSolved,sValue,false)); //da der Wert selbst nicht als Argument in der Methode uebergeben wurde, koennen wir nur auf Existenz im Gesamtergebnis pruefen.
			
				//+++ Nun die Gesamtberechnung durchführen
				sValue = objExpressionSolver.parse(sExpression, objSectionEntryReference,objEnumSurrounding.isSurroundingValueToKeep_OnParse());
				assertEquals(sExpressionSolved, sValue);
							
				objEntry = objSectionEntryReference.get();
				assertNotNull(objEntry);
			}
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
				
			//+++ ... solve verhält sich NICHT wie parse(), bei solve wird aufgeloest...
			if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.SOLVE)) {
				sValue = objExpressionSolver.solve(sExpression, objSectionEntryReference, objEnumSurrounding.isSurroundingValueToRemove_OnSolve());
				assertEquals(sExpressionSolved, sValue);
				
				objEntry = objSectionEntryReference.get();
				assertNotNull(objEntry);
			}
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			
			//+++ Variante fuer den AsEntry-Test
			if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.PARSE_AS_ENTRY)) {
				objEntry = objExpressionSolver.parseAsEntry(sExpression, objEnumSurrounding.isSurroundingValueToRemove_OnParse());				
				assertNotNull(objEntry);
				
				sValue = objEntry.getValue();
				assertEquals(sExpressionSubstituted, sValue);
			}
			
			
			//+++ ... solve verhält sich NICHT wie parse(), bei solve wird aufgeloest...
			if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.SOLVE_AS_ENTRY)) {
				sValue = objExpressionSolver.solve(sExpression, objSectionEntryReference, objEnumSurrounding.isSurroundingValueToRemove_OnSolve());
				assertEquals(sExpressionSolved, sValue);
				
				objEntry = objSectionEntryReference.get();
				assertNotNull(objEntry);
			}
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++	
			
			//Nutze eine Sammlung von assert Methoden, die ein objEntry als input hat.
			//und in der die verschiedenen stati für den unexpressed, unsubstituted, substituted, unsolved, etc Fall stehen.
			btemp = TestUtilAsTestZZZ.assertFileIniEntry(objEnumFlagset, objEnumSurrounding, objEnumTestCase, objEntry, sExpression, sExpressionSubstituted, sExpressionSolved);
			assertTrue(btemp);
			
			bReturn = true;
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}
		return bReturn;
	}

	//##############################################################################
	
	private boolean testCompute_JsonMap_Unsolved_(String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, IEnumSetMappedTestSurroundingZZZ objEnumSurrounding, IEnumSetMappedTestCaseZZZ objEnumTestCase) {
		boolean bReturn = false;
		try {
			boolean btemp; 
			
			String sExpression; String sExpressionSubstituted; String sExpressionSolved;  
			String sValue; String sValueUsed; Vector3ZZZ<String> vecValue;		
			ReferenceZZZ<IKernelConfigSectionEntryZZZ> objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
			IKernelConfigSectionEntryZZZ objEntry = null;
			
			String sExpressionSubstituted2Compare = null; String sExpressionSurroundedTemp = null;
			
			String sTagStartZ = "<Z>";
			String sTagEndZ = "</Z>";		
			
			sExpression = sExpressionIn;
			sExpressionSubstituted = sExpressionSubstitutedIn;
			sExpressionSolved = sExpressionSolvedIn;

			IEnumSetMappedTestFlagsetZZZ objEnumFlagset = EnumSetMappedTestCaseFlagsetTypeZZZ.UNSOLVED;
			
			//##########################################
			//### Expression unsolved Fall
			btemp = objExpressionSolver.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION, true); //Ansonsten wird der Wert sofort ausgerechnet 
			assertTrue("Flag nicht vorhanden '" + IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
			
			btemp = objExpressionSolver.setFlag(IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER,true); //ohne aktivierten Parser keine SUBSTITUTION  
			assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER + "'", btemp);

			btemp = objExpressionSolver.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH, true);			
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
		
			btemp = objExpressionSolver.setFlag(IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE, true);			
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE + "'", btemp);
			
			btemp = objExpressionSolver.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER, false);
			assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
			
			btemp = objExpressionSolver.setFlag(IKernelJsonIniSolverZZZ.FLAGZ.USEJSON, true);//Sollte dann egal sein
			assertTrue("Flag nicht vorhanden '" + IKernelJsonIniSolverZZZ.FLAGZ.USEJSON + "'", btemp);
			
			btemp = objExpressionSolver.setFlag(IKernelJsonMapIniSolverZZZ.FLAGZ.USEJSON_MAP, true);//Sollte dann egal sein
			assertTrue("Flag nicht vorhanden '" + IKernelJsonMapIniSolverZZZ.FLAGZ.USEJSON_MAP + "'", btemp);
			
			boolean bUseExpressionGeneral = objExpressionSolver.isExpressionEnabledGeneral();
			boolean bUseSolver = objExpressionSolver.isSolverEnabledGeneral();
			boolean bUseParser = objExpressionSolver.isParserEnabledGeneral() && objExpressionSolver.isParserEnabledThis();

			
			//+++ ... parse ist nicht solve... also wird hier nichts aufgeloest, aussser die Pfade substituiert.
			if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.PARSE)) {
				
				//+++ Teilberechnungen durchführen
				//    Es werden nomalerweise die Tags entfernt, aber ohne einen angestellten Solver werden sie beim parsen ignoriert 
				vecValue = objExpressionSolver.parseFirstVector(sExpression, objEnumSurrounding.isSurroundingValueToKeep_OnParse());
				sValue = VectorUtilZZZ.implode(vecValue);
				System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpression='"+sExpression+"'" );
				System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sValue='"+sValue+"'" );
				
				sExpressionSurroundedTemp = sExpressionSubstituted;
				if(bUseExpressionGeneral && bUseParser && objEnumSurrounding.isSurroundingValueToRemove_OnParse()) {
					sExpressionSurroundedTemp = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSurroundedTemp, sTagStartZ, sTagEndZ);
				}
				sExpressionSubstituted = sExpressionSurroundedTemp;
				assertEquals(sExpressionSubstituted, sValue);
				
							
				if(bUseParser && bUseExpressionGeneral) {
					sValue = (String) vecValue.get(1);//in der 0ten Position ist der String vor der Map, in der 3ten Position ist der String nach der Map.
				}else {
					sValue = (String) vecValue.get(0);//in der 0ten Position ist der String, entweder wenn der Tag nicht enthalten ist ODER der Parser (ggfs. entsprechend dem Solver) abgestellt ist
				}								
				sExpressionSubstituted2Compare = sExpressionSubstituted;
				sExpressionSubstituted2Compare = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSubstituted2Compare, sTagStartZ, sTagEndZ, false);
				sExpressionSubstituted2Compare = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSubstituted2Compare, KernelJsonMapIniSolverZZZ.sTAG_NAME, false);
				sExpressionSubstituted2Compare = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSubstituted2Compare, KernelJsonIniSolverZZZ.sTAG_NAME, false);				
				System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpressionSubstituted2Compare=" + sExpressionSubstituted2Compare);
				System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sValue=" + sValue);
				assertEquals(sExpressionSubstituted2Compare, sValue); //Beim parse fist Vector wird nie der Z-Tag drum herum entfernt. Das ist Aufgabe von parse().
							
				
				//+++ Nun die Gesamtberechnung durchführen
				sValue = objExpressionSolver.parse(sExpression, objSectionEntryReference, objEnumSurrounding.isSurroundingValueToKeep_OnParse());
				
				sExpressionSurroundedTemp = sExpressionSubstituted;
				if(bUseExpressionGeneral && bUseParser && objEnumSurrounding.isSurroundingValueToRemove_OnParse()) {
					sExpressionSurroundedTemp = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSurroundedTemp, sTagStartZ, sTagEndZ);
				}
				sExpressionSubstituted = sExpressionSurroundedTemp;
				assertEquals(sExpressionSubstituted, sValue);
				
							
				objEntry = objSectionEntryReference.get();
				assertNotNull(objEntry);

			}
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
				
			//+++ ... solve verhält sich NICHT wie parse(), bei solve wird aufgeloest...
			if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.SOLVE)) {
				sValue = objExpressionSolver.solve(sExpression, objSectionEntryReference, objEnumSurrounding.isSurroundingValueToRemove_OnSolve());
				System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpressionSolved='"+sExpressionSolved+"'" );
				System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sValue='"+sValue+"'" );
				
				sExpressionSurroundedTemp = sExpressionSolved;
				if(bUseExpressionGeneral && bUseSolver && objEnumSurrounding.isSurroundingValueToRemove_OnSolve()) {
					sExpressionSurroundedTemp = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSurroundedTemp, sTagStartZ, sTagEndZ);
				}
				sExpressionSolved = sExpressionSurroundedTemp;				
				assertEquals(sExpressionSolved, sValue);
				
				objEntry = objSectionEntryReference.get();
				assertNotNull(objEntry);
			}
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			
			//+++ Variante fuer den AsEntry-Test
			if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.PARSE_AS_ENTRY)) {
				objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
				objEntry = objExpressionSolver.parseAsEntry(sExpression, objEnumSurrounding.isSurroundingValueToKeep_OnParse());				
				assertNotNull(objEntry);
				
				sValue = objEntry.getValue();
				assertEquals(sExpressionSubstituted, sValue);
			}
			
			
			//+++ ... solve verhält sich NICHT wie parse(), bei solve wird aufgeloest...
			if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.SOLVE_AS_ENTRY)) {
				sValue = objExpressionSolver.solve(sExpression, objSectionEntryReference, objEnumSurrounding.isSurroundingValueToRemove_OnSolve());
				assertEquals(sExpressionSolved, sValue);
				
				objEntry = objSectionEntryReference.get();
				assertNotNull(objEntry);

			}
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++	
			
			//Nutze eine Sammlung von assert Methoden, die ein objEntry als input hat.
			//und in der die verschiedenen stati für den unexpressed, unsubstituted, substituted, unsolved, etc Fall stehen.
			btemp = TestUtilAsTestZZZ.assertFileIniEntry(objEnumFlagset, objEnumSurrounding, objEnumTestCase, objEntry, sExpression, sExpressionSubstituted, sExpressionSolved);
			assertTrue(btemp);
			
			bReturn = true;
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}
		return bReturn;
	}
	
	
	
	//########################################################################
	
	private boolean testCompute_JsonMap_JsonUnsolved_(String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, IEnumSetMappedTestSurroundingZZZ objEnumSurrounding, IEnumSetMappedTestCaseZZZ objEnumTestCase) {
		boolean bReturn = false;
		try {
			boolean btemp; 
			
			String sExpression; String sExpressionSubstituted; String sExpressionSolved;  
			String sValue; String sValueUsed; Vector3ZZZ<String> vecValue;		
			ReferenceZZZ<IKernelConfigSectionEntryZZZ> objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
			IKernelConfigSectionEntryZZZ objEntry=null;
			
			String sExpressionSurroundedTemp = null;
			
			String sTagStartZ = "<Z>";
			String sTagEndZ = "</Z>";		
			
			sExpression = sExpressionIn;
			sExpressionSubstituted = sExpressionSubstitutedIn;
			sExpressionSolved = sExpressionSolvedIn;

			IEnumSetMappedTestFlagsetZZZ objEnumFlagset = EnumSetMappedTestCaseFlagsetTypeZZZ.UNSOLVED;
			
			//##########################################
			//### Expression unsolved Fall
			btemp = objExpressionSolver.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION, true); //Ansonsten wird der Wert sofort ausgerechnet 
			assertTrue("Flag nicht vorhanden '" + IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
			
			btemp = objExpressionSolver.setFlag(IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER,true); //ohne aktivierten Parser keine SUBSTITUTION  
			assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER + "'", btemp);

			btemp = objExpressionSolver.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH, true);			
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
		
			btemp = objExpressionSolver.setFlag(IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE, true);			
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE + "'", btemp);
			
			btemp = objExpressionSolver.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER, true);
			assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
			
			btemp = objExpressionSolver.setFlag(IKernelJsonIniSolverZZZ.FLAGZ.USEJSON, false);
			assertTrue("Flag nicht vorhanden '" + IKernelJsonIniSolverZZZ.FLAGZ.USEJSON + "'", btemp);
			
			btemp = objExpressionSolver.setFlag(IKernelJsonMapIniSolverZZZ.FLAGZ.USEJSON_MAP, true);//Sollte dann egal sein
			assertTrue("Flag nicht vorhanden '" + IKernelJsonMapIniSolverZZZ.FLAGZ.USEJSON_MAP + "'", btemp);
			
			boolean bUseExpressionGeneral = objExpressionSolver.isExpressionEnabledGeneral();
			boolean bUseSolver = objExpressionSolver.isSolverEnabledGeneral();
			boolean bUseParser = objExpressionSolver.isParserEnabledGeneral() && objExpressionSolver.isParserEnabledThis();
			
			//+++ ... parse ist nicht solve... also wird hier nichts aufgeloest, aussser die Pfade substituiert.
			if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.PARSE)) {
				
				//+++ Teilberechnungen durchführen.
				//    Es werden wg false die Tags nicht entfernt
				vecValue = objExpressionSolver.parseFirstVector(sExpression, objEnumSurrounding.isSurroundingValueToKeep_OnParse());					
				sValue = VectorUtilZZZ.implode(vecValue);
				
				sExpressionSurroundedTemp = sExpressionSubstituted;
				if(bUseExpressionGeneral && bUseParser && objEnumSurrounding.isSurroundingValueToRemove_OnParse()) {
					sExpressionSurroundedTemp = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSurroundedTemp, sTagStartZ, sTagEndZ);
				}
				sExpressionSubstituted = sExpressionSurroundedTemp;
				assertEquals(sExpressionSubstituted, sValue);
				
				if(bUseParser && bUseExpressionGeneral) {
					sValue = (String) vecValue.get(1);//in der 0ten Position ist der String vor der Map, in der 3ten Position ist der String nach der Map.
				}else {
					sValue = (String) vecValue.get(0);//in der 0ten Position ist der String, entweder wenn der Tag nicht enthalten ist ODER der Parser (ggfs. entsprechend dem Solver) abgestellt ist
				}
				assertTrue(StringZZZ.contains(sExpressionSolved,sValue,false)); //da der Wert selbst nicht als Argument in der Methode uebergeben wurde, koennen wir nur auf Existenz im Gesamtergebnis pruefen.
			
					
				//+++ Nun die Gesamtberechnung durchführen				
				sValue = objExpressionSolver.parse(sExpression, objSectionEntryReference, objEnumSurrounding.isSurroundingValueToKeep_OnParse());
				sExpressionSurroundedTemp = sExpressionSubstituted;
				if(bUseExpressionGeneral && bUseParser && objEnumSurrounding.isSurroundingValueToRemove_OnParse()) {
					sExpressionSurroundedTemp = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSurroundedTemp, sTagStartZ, sTagEndZ);
				}
				sExpressionSubstituted = sExpressionSurroundedTemp;
				assertEquals(sExpressionSubstituted, sValue);
							
				objEntry = objSectionEntryReference.get();
				assertNotNull(objEntry);
			}
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
				
			//+++ ... solve verhält sich NICHT wie parse(), bei solve wird aufgeloest...
			if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.SOLVE)) {
				sValue = objExpressionSolver.solve(sExpression, objSectionEntryReference, objEnumSurrounding.isSurroundingValueToRemove_OnSolve());
				assertEquals(sExpressionSolved, sValue);
				
				objEntry = objSectionEntryReference.get();
				assertNotNull(objEntry);
			}
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			
			//+++ Variante fuer den AsEntry-Test
			if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.PARSE_AS_ENTRY)) {
				objEntry = objExpressionSolver.parseAsEntry(sExpression, objEnumSurrounding.isSurroundingValueToKeep_OnParse());				
				assertNotNull(objEntry);
				
				sValue = objEntry.getValue();
				assertEquals(sExpressionSubstituted, sValue);
			}
			
			
			//+++ ... solve verhält sich NICHT wie parse(), bei solve wird aufgeloest...
			if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.SOLVE_AS_ENTRY)) {
				objEntry = objExpressionSolver.solveAsEntry(sExpression, objEnumSurrounding.isSurroundingValueToRemove_OnSolve());
				assertNotNull(objEntry);
				
				
				sValue = objEntry.getValue();
				assertEquals(sExpressionSolved, sValue);
			}
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++	
			
			bReturn = true;
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}
		return bReturn;
	}
	
	
	
	//########################################################################
	
	
	private boolean testCompute_JsonMap_JsonMapUnsolved_(String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, IEnumSetMappedTestSurroundingZZZ objEnumSurrounding, IEnumSetMappedTestCaseZZZ objEnumTestCase) {
		boolean bReturn = false;
		try {
			boolean btemp; 
			
			String sExpression; String sExpressionSubstituted; String sExpressionSolved;  
			String sValue; String sValueUsed; Vector3ZZZ<String> vecValue;		
			ReferenceZZZ<IKernelConfigSectionEntryZZZ> objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
			IKernelConfigSectionEntryZZZ objEntry=null;
			
			String sTagStartZ = "<Z>";
			String sTagEndZ = "</Z>";		
			
			sExpression = sExpressionIn;
			sExpressionSubstituted = sExpressionSubstitutedIn;
			sExpressionSolved = sExpressionSolvedIn;

			IEnumSetMappedTestFlagsetZZZ objEnumFlagset = EnumSetMappedTestCaseFlagsetTypeZZZ.JSONMAP_UNSOLVED;
			
			//##########################################
			//### Expression unsolved Fall
			btemp = objExpressionSolver.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION, true); //Ansonsten wird der Wert sofort ausgerechnet 
			assertTrue("Flag nicht vorhanden '" + IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
			
			btemp = objExpressionSolver.setFlag(IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER,true); //ohne aktivierten Parser keine SUBSTITUTION  
			assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER + "'", btemp);

			btemp = objExpressionSolver.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH, true);			
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
		
			btemp = objExpressionSolver.setFlag(IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE, true);			
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE + "'", btemp);
			
			btemp = objExpressionSolver.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER, true);
			assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
			
			btemp = objExpressionSolver.setFlag(IKernelJsonIniSolverZZZ.FLAGZ.USEJSON, true);
			assertTrue("Flag nicht vorhanden '" + IKernelJsonIniSolverZZZ.FLAGZ.USEJSON + "'", btemp);
			
			btemp = objExpressionSolver.setFlag(IKernelJsonMapIniSolverZZZ.FLAGZ.USEJSON_MAP, false);
			assertTrue("Flag nicht vorhanden '" + IKernelJsonMapIniSolverZZZ.FLAGZ.USEJSON_MAP + "'", btemp);
			
			boolean bUseExpressionGeneral = objExpressionSolver.isExpressionEnabledGeneral();
			boolean bUseSolver = objExpressionSolver.isSolverEnabledGeneral();
			boolean bUseParser = objExpressionSolver.isParserEnabledGeneral() && objExpressionSolver.isParserEnabledThis();

			
			//+++ ... parse ist nicht solve... also wird hier nichts aufgeloest, aussser die Pfade substituiert.
			if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.PARSE)) {
													
				//+++ Teilberechnungen durchführen
				//    Es werden nomalerweise die Tags entfernt, aber ohne einen angestellten Solver werden sie beim parsen ignoriert 
				vecValue = objExpressionSolver.parseFirstVector(sExpression, objEnumSurrounding.isSurroundingValueToKeep_OnParse());					
				sValue = VectorUtilZZZ.implode(vecValue);
				
				String sExpressionSurroundedTemp = sExpressionSubstituted;
				if(bUseExpressionGeneral && bUseParser && objEnumSurrounding.isSurroundingValueToRemove_OnParse()) {
					sExpressionSurroundedTemp = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSurroundedTemp, sTagStartZ, sTagEndZ);
				}
				sExpressionSubstituted = sExpressionSurroundedTemp;
				assertEquals(sExpressionSubstituted, sValue);
								
				
				if(bUseParser && bUseExpressionGeneral) {
					sValue = (String) vecValue.get(1);//in der 0ten Position ist der String vor der Map, in der 3ten Position ist der String nach der Map.
				}else {
					sValue = (String) vecValue.get(0);//in der 0ten Position ist der String, entweder wenn der Tag nicht enthalten ist ODER der Parser (ggfs. entsprechend dem Solver) abgestellt ist
				}
				assertTrue(StringZZZ.contains(sExpressionSolved,sValue,false)); //da der Wert selbst nicht als Argument in der Methode uebergeben wurde, koennen wir nur auf Existenz im Gesamtergebnis pruefen.

				
				//+++ Nun die Gesamtberechnung durchführen
				sValue = objExpressionSolver.parse(sExpression, objSectionEntryReference, objEnumSurrounding.isSurroundingValueToKeep_OnParse());
				assertEquals(sExpressionSolved, sValue);
							
				objEntry = objSectionEntryReference.get();
				assertNotNull(objEntry);								
			}
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
				
			//+++ ... solve verhält sich NICHT wie parse(), bei solve wird aufgeloest...
			if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.SOLVE)) {
				sValue = objExpressionSolver.solve(sExpression, objSectionEntryReference, objEnumSurrounding.isSurroundingValueToRemove_OnSolve());
				assertEquals(sExpressionSolved, sValue);
				
				objEntry = objSectionEntryReference.get();
				assertNotNull(objEntry);
//				
//				assertTrue(objEntry.isParseCalled()); //Der Parse-Schritt wurde gemacht.
//				if(objEnumSurrounding.isSurroundingValueToRemove_OnSolve()) {
//					assertTrue(objEntry.isParsedChanged()); //es werden ja die Z-Tags drumherum entfernt also "veraendert"
//				}else {
//					assertFalse(objEntry.isParsedChanged()); //es werden ja die Z-Tags drumherum NICHT entfernt also "veraendert"
//				}
//				
//				assertFalse(objEntry.isPathSubstituted());
//				assertFalse(objEntry.isVariableSubstituted());
//				
//										
//			
//				assertTrue(objEntry.isSolveCalled());
//				//assertTrue(objEntry.isSolvedChanged());
//												
//				assertFalse(objEntry.isDecrypted());
//				assertNull(objEntry.getValueDecrypted()); //Merke: sValue kann unterschiedlich zu dem decrypted Wert sein. Wenn etwas drumherum steht.
//								
//				assertFalse(objEntry.isCall());
//				assertFalse(objEntry.isJavaCall());
//				assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingClassname() + "'", objEntry.getCallingClassname());
//				assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingMethodname() + "'", objEntry.getCallingMethodname());
			}
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			
			//+++ Variante fuer den AsEntry-Test
			if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.PARSE_AS_ENTRY)) {
				objEntry = objExpressionSolver.parseAsEntry(sExpression, objEnumSurrounding.isSurroundingValueToKeep_OnParse());				
				assertNotNull(objEntry);

				sValue = objEntry.getValue();
				assertEquals(sExpressionSolved, sValue);				

			}
			
			
			//+++ ... solve verhält sich NICHT wie parse(), bei solve wird aufgeloest...
			if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.SOLVE_AS_ENTRY)) {
				objEntry = objExpressionSolver.solveAsEntry(sExpression, objEnumSurrounding.isSurroundingValueToRemove_OnSolve());
				assertNotNull(objEntry);
				
				sValue = objEntry.getValue();
				assertEquals(sExpressionSolved, sValue);				

			}
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++	
			
			//Nutze eine Sammlung von assert Methoden, die ein objEntry als input hat.
			//und in der die verschiedenen stati für den unexpressed, unsubstituted, substituted, unsolved, etc Fall stehen.
			btemp = TestUtilAsTestZZZ.assertFileIniEntry(objEnumFlagset, objEnumSurrounding, objEnumTestCase, objEntry, sExpression, sExpressionSubstituted, sExpressionSolved);
			assertTrue(btemp);	
			
			bReturn = true;
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}
		return bReturn;
	}
	
	
	
	//########################################################################

	private boolean testCompute_JsonMap_JsonMapSolved_(String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, IEnumSetMappedTestSurroundingZZZ objEnumSurrounding, IEnumSetMappedTestCaseZZZ objEnumTestCase) {
		boolean bReturn = false;
		try {
			boolean btemp; 
			
			String sExpression; String sExpressionSubstituted; String sExpressionSolved;  
			String sValue; String sValueUsed; Vector3ZZZ<String> vecValue;		
			ReferenceZZZ<IKernelConfigSectionEntryZZZ> objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
			IKernelConfigSectionEntryZZZ objEntry=null;
			
			String sExpressionSubstituted2Compare = null; String sExpressionSurroundedTemp = null;
			
			String sTagStartZ = "<Z>";
			String sTagEndZ = "</Z>";		
			
			sExpression = sExpressionIn;
			sExpressionSubstituted = sExpressionSubstitutedIn;
			sExpressionSolved = sExpressionSolvedIn;

			IEnumSetMappedTestFlagsetZZZ objEnumFlagset = EnumSetMappedTestCaseFlagsetTypeZZZ.JSONMAP_SOLVED;
			
			//##########################################
			//### Expression solved Fall
			btemp = objExpressionSolver.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION, true); //Ansonsten wird der Wert sofort ausgerechnet 
			assertTrue("Flag nicht vorhanden '" + IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
			
			btemp = objExpressionSolver.setFlag(IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER,true); //ohne aktivierten Parser keine SUBSTITUTION  
			assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER + "'", btemp);

			btemp = objExpressionSolver.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH, true);			
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
		
			btemp = objExpressionSolver.setFlag(IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE, true);			
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE + "'", btemp);
			
			btemp = objExpressionSolver.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER, true);
			assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
			
			btemp = objExpressionSolver.setFlag(IKernelJsonIniSolverZZZ.FLAGZ.USEJSON, true);
			assertTrue("Flag nicht vorhanden '" + IKernelJsonIniSolverZZZ.FLAGZ.USEJSON + "'", btemp);
			
			btemp = objExpressionSolver.setFlag(IKernelJsonMapIniSolverZZZ.FLAGZ.USEJSON_MAP, true);//Sollte dann egal sein
			assertTrue("Flag nicht vorhanden '" + IKernelJsonMapIniSolverZZZ.FLAGZ.USEJSON_MAP + "'", btemp);
			
			boolean bUseExpressionGeneral = objExpressionSolver.isExpressionEnabledGeneral();
			boolean bUseSolver = objExpressionSolver.isSolverEnabledGeneral();
			boolean bUseParser = objExpressionSolver.isParserEnabledGeneral() && objExpressionSolver.isParserEnabledThis();
			
			//+++ ... parse ist nicht solve... also wird hier nichts aufgeloest, aussser die Pfade substituiert.
			if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.PARSE)) {
				
				//+++ Teilberechnungen durchführen.
				//    Es werden wg false die Tags nicht entfernt
				vecValue = objExpressionSolver.parseFirstVector(sExpression, objEnumSurrounding.isSurroundingValueToKeep_OnParse());					
				sValue = VectorUtilZZZ.implode(vecValue);
				System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpression='"+sExpression+"'" );
				System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sValue='"+sValue+"'" );
				
				sExpressionSurroundedTemp = sExpressionSubstituted;
				if(bUseExpressionGeneral && bUseParser && objEnumSurrounding.isSurroundingValueToRemove_OnParse()) {
					sExpressionSurroundedTemp = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSurroundedTemp, sTagStartZ, sTagEndZ);
				}
				sExpressionSubstituted = sExpressionSurroundedTemp;
				assertEquals(sExpressionSubstituted, sValue);
				
				if(bUseParser && bUseExpressionGeneral) {
					sValue = (String) vecValue.get(1);//in der 0ten Position ist der String vor der Map, in der 3ten Position ist der String nach der Map.
				}else {
					sValue = (String) vecValue.get(0);//in der 0ten Position ist der String, entweder wenn der Tag nicht enthalten ist ODER der Parser (ggfs. entsprechend dem Solver) abgestellt ist
				}
				assertTrue(StringZZZ.contains(sExpressionSolved,sValue,false)); //da der Wert selbst nicht als Argument in der Methode uebergeben wurde, koennen wir nur auf Existenz im Gesamtergebnis pruefen.
					
				
				sExpressionSubstituted2Compare = sExpressionSubstituted;
				sExpressionSubstituted2Compare = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSubstituted2Compare, sTagStartZ, sTagEndZ, false);
				sExpressionSubstituted2Compare = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSubstituted2Compare, KernelJsonMapIniSolverZZZ.sTAG_NAME, false);
				sExpressionSubstituted2Compare = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSubstituted2Compare, KernelJsonIniSolverZZZ.sTAG_NAME, false);				
				System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpressionSubstituted2Compare=" + sExpressionSubstituted2Compare);
				System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sValue=" + sValue);
				assertEquals(sExpressionSubstituted2Compare, sValue); //Beim parse fist Vector wird nie der Z-Tag drum herum entfernt. Das ist Aufgabe von parse().
				
				//+++ Nun die Gesamtberechnung durchführen
				sValue = VectorUtilZZZ.implode(vecValue);
				assertEquals(sExpressionSolved, sValue); 
				
				
				//+++ Nun die Gesamtberechnung durchführen				
				sValue = objExpressionSolver.parse(sExpression, objSectionEntryReference, objEnumSurrounding.isSurroundingValueToKeep_OnParse());
				sExpressionSurroundedTemp = sExpressionSubstituted;
				if(bUseExpressionGeneral && bUseParser && objEnumSurrounding.isSurroundingValueToRemove_OnParse()) {
					sExpressionSurroundedTemp = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSurroundedTemp, sTagStartZ, sTagEndZ);
				}
				sExpressionSubstituted = sExpressionSurroundedTemp;
				assertEquals(sExpressionSubstituted, sValue);
							
				objEntry = objSectionEntryReference.get();
				assertNotNull(objEntry);
			}
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
				
			//+++ ... solve verhält sich NICHT wie parse(), bei solve wird aufgeloest...
			if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.SOLVE)) {
				sValue = objExpressionSolver.solve(sExpression, objSectionEntryReference, objEnumSurrounding.isSurroundingValueToRemove_OnSolve());
				String sExpressionSolvedTemp = sExpressionSolved;
				if(bUseExpressionGeneral && bUseSolver && objEnumSurrounding.isSurroundingValueToRemove_OnSolve()) {
					sExpressionSolvedTemp = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
				}
				sExpressionSolved = sExpressionSolvedTemp;
				assertEquals(sExpressionSolved, sValue);
				
				objEntry = objSectionEntryReference.get();
				assertNotNull(objEntry);	
			}
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			
			//+++ Variante fuer den AsEntry-Test
			if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.PARSE_AS_ENTRY)) {
				objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
				objEntry = objExpressionSolver.parseAsEntry(sExpression, objEnumSurrounding.isSurroundingValueToKeep_OnParse());				
				assertNotNull(objEntry);
				
				sValue = objEntry.getValue();
				assertEquals(sExpressionSolved, sValue);
			}
			
			
			//+++ ... solve verhält sich NICHT wie parse(), bei solve wird aufgeloest...
			if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.SOLVE_AS_ENTRY)) {			
				objEntry = objExpressionSolver.solveAsEntry(sExpression, objEnumSurrounding.isSurroundingValueToRemove_OnSolve());
				assertNotNull(objEntry);
				
				sValue = objEntry.getValue();
				String sExpressionSolvedTemp = sExpressionSolved;
				if(bUseExpressionGeneral && bUseSolver && objEnumSurrounding.isSurroundingValueToRemove_OnSolve()) {
					sExpressionSolvedTemp = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
				}
				sExpressionSolved = sExpressionSolvedTemp;
				assertEquals(sExpressionSolved, sValue);
			}
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++	
			
			//Nutze eine Sammlung von assert Methoden, die ein objEntry als input hat.
			//und in der die verschiedenen stati für den unexpressed, unsubstituted, substituted, unsolved, etc Fall stehen.
			btemp = TestUtilAsTestZZZ.assertFileIniEntry(objEnumFlagset, objEnumSurrounding, objEnumTestCase, objEntry, sExpression, sExpressionSubstituted, sExpressionSolved);
			assertTrue(btemp);

			bReturn = true;
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}
		return bReturn;
	}
	
	
	
	//########################################################################
	
	//###################################
	
	/** void, Test: Reading an entry in a section of the ini-file
	* Lindhauer; 22.04.2006 12:54:32
	 */
	public void testCompute01Map(){
		boolean btemp;
		String sExpression; String sExpressionSolved;
		String sValue; Vector<String> vecValue;
		HashMap<String,String>hm;
		try {					
			btemp = objExpressionSolver.setFlag(IKernelJsonIniSolverZZZ.FLAGZ.USEJSON, false);
			assertTrue("Flag nicht vorhanden '" + IKernelJsonIniSolverZZZ.FLAGZ.USEJSON + "'", btemp);
			
		
			//### Teilberechnungen durchführen
			sExpression = sEXPRESSION_JSONMAP01_DEFAULT;
			vecValue = objExpressionSolver.parseFirstVector(sExpression);
			assertFalse(StringZZZ.isEmpty(vecValue.get(1))); //in der 0ten Position ist der String vor der Map, in der 3ten Position ist der String nach der Map.
			
			
			//### Nun die Gesamtberechnung durchführen
			sExpression = sEXPRESSION_JSONMAP01_DEFAULT;
			hm = objExpressionSolver.computeHashMapFromJson(sExpression);
			assertTrue("Ohne Auflösung soll es keine HashMap geben",hm.size()==0);
				
			
			
			//##############################################################################
			//### Expression solved Fall
			btemp = objExpressionSolver.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION, true); //Ansonsten wird der Wert sofort ausgerechnet 
			assertTrue("Flag nicht vorhanden '" + IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
			
			btemp = objExpressionSolver.setFlag(IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER,true); //ohne aktivierten Parser keine SUBSTITUTION  
			assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER + "'", btemp);

			btemp = objExpressionSolver.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH, true);			
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
		
			btemp = objExpressionSolver.setFlag(IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE, true);			
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE + "'", btemp);
			
			btemp = objExpressionSolver.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER, true);
			assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
			
			btemp = objExpressionSolver.setFlag(IKernelJsonIniSolverZZZ.FLAGZ.USEJSON, true);
			assertTrue("Flag nicht vorhanden '" + IKernelJsonIniSolverZZZ.FLAGZ.USEJSON + "'", btemp);
			
			btemp = objExpressionSolver.setFlag(IKernelJsonMapIniSolverZZZ.FLAGZ.USEJSON_MAP, true);//Sollte dann egal sein
			assertTrue("Flag nicht vorhanden '" + IKernelJsonMapIniSolverZZZ.FLAGZ.USEJSON_MAP + "'", btemp);
		
			sExpression = sEXPRESSION_JSONMAP01_CONTENT;
			hm = objExpressionSolver.computeHashMapFromJson(sExpression);
			assertNotNull(hm);
			assertTrue("Mit Auflösung des String soll die HashMap entsprechende Größe haben. ",hm.size()==2);

			sValue = HashMapExtendedZZZ.computeDebugString(hm);	
			System.out.println(ReflectCodeZZZ.getPositionCurrent() + "\tDebugausagabe\n" + sValue);
			
			
			//++++++++++++++++++++++++++++++
			sExpression = sEXPRESSION_JSONMAP01_DEFAULT;
			sExpressionSolved = objExpressionSolver.solve(sExpression);
			hm =objExpressionSolver.getValueHashMap();
			assertNotNull(hm);
			assertTrue("Mit Auflösung des String soll die HashMap entsprechende Größe haben. ",hm.size()==2);

			sValue = HashMapExtendedZZZ.computeDebugString(hm);	
			System.out.println(ReflectCodeZZZ.getPositionCurrent() + "\tDebugausagabe\n" + sValue);
			
			
			//#############################################################
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
	
