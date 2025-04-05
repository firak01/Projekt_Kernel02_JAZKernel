package basic.zKernel.file.ini;

import java.io.File;
import java.util.ArrayList;
import java.util.Vector;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IObjectWithExpressionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedTestCaseZZZ;
import basic.zBasic.util.abstractList.ArrayListExtendedZZZ;
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

public class KernelJsonArrayIniSolverZZZTest extends TestCase {	
	public final static String sEXPRESSION_JSONARRAY01_DEFAULT = "<Z><JSON><JSON:ARRAY>[\"TESTWERT2DO2JSON01\",\"TESTWERT2DO2JSON02\"]</JSON:ARRAY></JSON></Z>";
	
	private File objFile;
	private IKernelZZZ objKernel;
	private FileIniZZZ objFileIniTest=null;
	
	/// +++ Die eigentlichen Test-Objekte	
	private KernelJsonArrayIniSolverZZZ objExpressionSolver;
	private KernelJsonArrayIniSolverZZZ objExpressionSolverInit;
	
	

	protected void setUp(){
		try {			
						
			objKernel = new KernelZZZ("FGL", "01", "test", "ZKernelConfigKernel_test.ini",(String)null);
			
			//#### Ein init TestObjekt
			String[] saFlagInit = {"init"};
			objExpressionSolverInit = new KernelJsonArrayIniSolverZZZ(objKernel, saFlagInit);
			
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
		boolean btemp;
		
		try{							
		assertTrue(objExpressionSolverInit.getFlag("init")==true);
		assertFalse(objExpressionSolver.getFlag("init")==true); //Nun wäre init falsch
		
		btemp = objExpressionSolver.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION, true); //Ansonsten wird der Wert sofort ausgerechnet 
		assertTrue("Flag nicht vorhanden '" + IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
		
		btemp = objExpressionSolver.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH, true);			
		assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
	
		btemp = objExpressionSolver.setFlag(IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE, true);			
		assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE + "'", btemp);
		
		btemp = objExpressionSolver.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER, false);
		assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
		
		btemp = objExpressionSolver.setFlag(IKernelJsonIniSolverZZZ.FLAGZ.USEJSON, true);//Sollte dann egal sein
		assertTrue("Flag nicht vorhanden '" + IKernelJsonIniSolverZZZ.FLAGZ.USEJSON + "'", btemp);
		
		btemp = objExpressionSolver.setFlag(IKernelJsonArrayIniSolverZZZ.FLAGZ.USEJSON_ARRAY, true);//Sollte dann egal sein
		assertTrue("Flag nicht vorhanden '" + IKernelJsonArrayIniSolverZZZ.FLAGZ.USEJSON_ARRAY + "'", btemp);
		
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}
	
	/** void, Test: Reading an entry in a section of the ini-file
	* Lindhauer; 22.04.2006 12:54:32
	 */
	public void testCompute_JsonArray(){
		String sExpressionSource=null;
//		try {			
			sExpressionSource = KernelJsonArrayIniSolverZZZTest.sEXPRESSION_JSONARRAY01_DEFAULT;
			testCompute_JsonArray_(sExpressionSource);
//		} catch (ExceptionZZZ ez) {
//			fail("Method throws an exception." + ez.getMessageLast());
//		}
	
	}
	
	/**void, Test: Reading an entry in a section of the ini-file
	 * 
	 * @author Fritz Lindhauer, 05.05.2023, 08:54:30
	 */
	private void testCompute_JsonArray_(String sExpressionSourceIn){
		
		boolean btemp; int itemp;
		
		String sSection; String sProperty;
		String sExpressionSource; 
		String sExpressionSolved; String sExpressionSolvedTemp;
		IKernelConfigSectionEntryZZZ objEntry; ReferenceZZZ<IKernelConfigSectionEntryZZZ>objSectionEntryReference;
	
		String sValue;
		String sTagStartZ = "<Z>";
		String sTagEndZ = "</Z>";	
		
		try {		
					
			//+++++++ VORGEZOGENER LETZTER FEHLERTEST START
			
			//+++ Mit JsonArray-Berechnung
			
			//d)
			sExpressionSource = sExpressionSourceIn;
			sExpressionSolved = "[TESTWERT2DO2JSON01, TESTWERT2DO2JSON02]"; //Der ArrayList.toString() Ausdruck

			sExpressionSolvedTemp = sExpressionSolved;
			//Den Tag des konketen Solver nicht aufnehmen.... sExpressionSolvedTemp = objExpressionSolver.makeAsExpression(sExpressionSolvedTemp);//den Tag des konkreten Solvers drumpacken.
			sExpressionSolvedTemp = ExpressionIniUtilZZZ.makeAsExpression(sExpressionSolvedTemp, "JSON");//einen Tag eines nicht genutzten Solvers drumpacken
			//der Z-Tag ist auch weg sExpressionSolvedTemp = ExpressionIniUtilZZZ.makeAsExpression(sExpressionSolvedTemp);//Beim parse fist Vector wird nie der Z-Tag drum herum entfernt. Das ist Aufgabe von parse().
			btemp = testCompute_JsonArray_(sExpressionSource, sExpressionSolvedTemp, true, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
		
			
			//+++++++ VORGEZOGENER LETZTER FEHLERTEST ENDE
			
			//###########################
		    //### objExpression
			//#########################
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//+++ Ohne jegliche Expression-Berechnung
			//a)
			sExpressionSource = sExpressionSourceIn;
			sExpressionSolved = sExpressionSource;
			btemp = testCompute_JsonArray_Unexpressed_(sExpressionSource, sExpressionSolved, false, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
			
			//b)
			sExpressionSource = sExpressionSourceIn;
			sExpressionSolved = sExpressionSource;			
			btemp = testCompute_JsonArray_Unexpressed_(sExpressionSource, sExpressionSolved, true, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
			
			//c)
			sExpressionSource = sExpressionSourceIn;
			sExpressionSolved = sExpressionSource; 			
			btemp = testCompute_JsonArray_Unexpressed_(sExpressionSource, sExpressionSolved, false, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
				
			//d)
			sExpressionSource = sExpressionSourceIn;
			sExpressionSolved = sExpressionSource;
			btemp = testCompute_JsonArray_Unexpressed_(sExpressionSource, sExpressionSolved, true, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
			
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//+++ Ohne jegliche Solver-Berechnung
			//a)
			sExpressionSource = sExpressionSourceIn;
			sExpressionSolved = sExpressionSource;
			btemp = testCompute_JsonArray_Unsolved_(sExpressionSource, sExpressionSolved, false, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
			
			//b)
			sExpressionSource = sExpressionSourceIn;
			sExpressionSolved = sExpressionSource;	//Beim Parsen werden, wenn wie hier gewuenscht immer der Z-Tag entfernt.
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ, false);
			btemp = testCompute_JsonArray_Unsolved_(sExpressionSource, sExpressionSolved, true, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
			
			//c)
			sExpressionSource = sExpressionSourceIn;
			sExpressionSolved = sExpressionSource; 			
			btemp = testCompute_JsonArray_Unsolved_(sExpressionSource, sExpressionSolved, false, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
				
			//d)
			sExpressionSource = sExpressionSourceIn;
			sExpressionSolved = sExpressionSource;	//Beim Parsen werden, wenn wie hier gewuenscht immer der Z-Tag entfernt.
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ, false);
			btemp = testCompute_JsonArray_Unsolved_(sExpressionSource, sExpressionSolved, true, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
			
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//+++ Ohne Json-Solver-Berechung
			//a)
			sExpressionSource = sExpressionSourceIn;
			sExpressionSolved = sExpressionSource;
			btemp = testCompute_JsonArray_JsonUnsolved_(sExpressionSource, sExpressionSolved, false, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
			
			//b)
			sExpressionSource = sExpressionSourceIn;
			sExpressionSolved = sExpressionSource;
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ, false);
			//Beim Parsen ohne Solver, bleibt sogar das Encryption-Tag drin, auch wenn sonst die Tags entfernt werden.
			btemp = testCompute_JsonArray_JsonUnsolved_(sExpressionSource, sExpressionSolved, true, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);

			//c)
			sExpressionSource = sExpressionSourceIn;
			sExpressionSolved = sExpressionSource;
			//Beim Solven ohne Solver, bleibt alles wie est ist.
			btemp = testCompute_JsonArray_JsonUnsolved_(sExpressionSource, sExpressionSolved, false, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
			
			//d)
			sExpressionSource = sExpressionSourceIn;
			sExpressionSolved = sExpressionSource;
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ, false);
			//Beim Solven ohne Solver, werden nur die äusseren Z-Tags ggfs. entfernt.
			btemp = testCompute_JsonArray_JsonUnsolved_(sExpressionSource, sExpressionSolved, true, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
			
			//+++ Ohne JsonArraySolver-Berechung
			//a)
			sExpressionSource = sExpressionSourceIn;
			sExpressionSolved = sExpressionSource;
			btemp = testCompute_JsonArray_JsonArrayUnsolved_(sExpressionSource, sExpressionSolved, false, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
			
			//b)
			sExpressionSource = sExpressionSourceIn;
			sExpressionSolved = sExpressionSource; 
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ, false);
			//Beim Parsen ohne encryption, muss doch dieser encryption - Tag drinbleiben. Hier werden also nur die aeussern Z-Tags entfernt.
			btemp = testCompute_JsonArray_JsonArrayUnsolved_(sExpressionSource, sExpressionSolved, true, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
			
			//c)
			sExpressionSource = sExpressionSourceIn;
			sExpressionSolved = sExpressionSource;
			//Beim Solven ohne encryption, bleibt alles an Tags drin.
			btemp = testCompute_JsonArray_JsonArrayUnsolved_(sExpressionSource, sExpressionSolved, false, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
		
			//d)
			sExpressionSource = sExpressionSourceIn;
			sExpressionSolved = sExpressionSource;	
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ, false);
			//Beim Solven ohne encryption muss dieser encryption - Tag drinbleiben
			btemp = testCompute_JsonArray_JsonArrayUnsolved_(sExpressionSource, sExpressionSolved, true, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
			
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//+++ Mit JsonArray-Berechnung
			//a) nur parsen bringt keinen Unterschied, wenn die Tags drinbleiben sollen
			sExpressionSource = sExpressionSourceIn;
			sExpressionSolved = sExpressionSourceIn; 
			//false: d.h. Tags sollen drin bleiben sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ, false);
			btemp = testCompute_JsonArray_(sExpressionSource, sExpressionSolved, false, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);

			//b) Werdem beim reinen Parsen die umgebenden Tags entfernt, dann wird auch das Encryption-Tag entfernt. Das wird naemlich auch durch Parsen "aufgeloest". Das eigentliche Aufloesen findet aber nicht statt.
			sExpressionSource = sExpressionSourceIn;
			sExpressionSolved = sExpressionSource;			
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, objExpressionSolver.getName(), false);
			btemp = testCompute_JsonArray_(sExpressionSource, sExpressionSolved, true, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
			
			//c)
			sExpressionSource = sExpressionSourceIn;
			sExpressionSolved = "[TESTWERT2DO2JSON01, TESTWERT2DO2JSON02]"; //Der ArrayList.toString() Ausdruck
			
			//Wichtig: JSON:ARRAY soll aus dem Ergebnis weg sein, wg. Aufloesen!!! Auch wenn die umgebenden Z-Tags drin bleiben.
			sExpressionSolvedTemp = sExpressionSolved;
			//Den Tag des konketen Solver nicht aufnehmen.... sExpressionSolvedTemp = objExpressionSolver.makeAsExpression(sExpressionSolvedTemp);//den Tag des konkreten Solvers drumpacken.
			sExpressionSolvedTemp = ExpressionIniUtilZZZ.makeAsExpression(sExpressionSolvedTemp, "JSON");//einen Tag eines nicht genutzten Solvers drumpacken
			sExpressionSolvedTemp = ExpressionIniUtilZZZ.makeAsExpression(sExpressionSolvedTemp);//Beim parse fist Vector wird nie der Z-Tag drum herum entfernt. Das ist Aufgabe von parse().  
			btemp = testCompute_JsonArray_(sExpressionSource, sExpressionSolvedTemp, false, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);

			//d)
			sExpressionSource = sExpressionSourceIn;
			sExpressionSolved = "[TESTWERT2DO2JSON01, TESTWERT2DO2JSON02]"; //Der ArrayList.toString() Ausdruck

			sExpressionSolvedTemp = sExpressionSolved;
			//Den Tag des konketen Solver nicht aufnehmen.... sExpressionSolvedTemp = objExpressionSolver.makeAsExpression(sExpressionSolvedTemp);//den Tag des konkreten Solvers drumpacken.
			sExpressionSolvedTemp = ExpressionIniUtilZZZ.makeAsExpression(sExpressionSolvedTemp, "JSON");//einen Tag eines nicht genutzten Solvers drumpacken
			//der Z-Tag ist auch weg sExpressionSolvedTemp = ExpressionIniUtilZZZ.makeAsExpression(sExpressionSolvedTemp);//Beim parse fist Vector wird nie der Z-Tag drum herum entfernt. Das ist Aufgabe von parse().
			btemp = testCompute_JsonArray_(sExpressionSource, sExpressionSolvedTemp, true, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
							
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}		
	}
	
	
	private boolean testCompute_JsonArray_Unexpressed_(String sExpressionSourceIn, String sExpressionSolvedIn, boolean bRemoveSurroundingSeparatorsOnSolve, IEnumSetMappedTestCaseZZZ objEnumTestCase) {
		boolean bReturn = false;
		try {
			boolean btemp; 
			
			String sExpression; String sExpressionSolved;  
			String sValue; String sValueUsed; Vector3ZZZ<String> vecValue;		
			ReferenceZZZ<IKernelConfigSectionEntryZZZ> objSectionEntryReference;
			IKernelConfigSectionEntryZZZ objEntry; IKernelConfigSectionEntryZZZ objEntryUsed;
			
						
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
			
			btemp = objExpressionSolver.setFlag(IKernelJsonArrayIniSolverZZZ.FLAGZ.USEJSON_ARRAY, true);//Sollte dann egal sein
			assertTrue("Flag nicht vorhanden '" + IKernelJsonArrayIniSolverZZZ.FLAGZ.USEJSON_ARRAY + "'", btemp);
			
			
			
			//+++ ... parse ist nicht solve... also wird hier nichts aufgeloest, aussser die Pfade substituiert.
			if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.PARSE)) {
				sExpression = sExpressionSourceIn;
				sExpressionSolved = sExpressionSolvedIn;		
				objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
				sValue = objExpressionSolver.parse(sExpression, objSectionEntryReference, bRemoveSurroundingSeparatorsOnSolve);
				assertEquals(sExpressionSolved, sValue);
				
				
//				//+++ Teilberechnungen durchführen
//				vecValue = objExpressionSolver.parseFirstVector(sExpression);
//				sValue = (String) vecValue.get(1);//in der 0ten Position ist der String vor der Map, in der 3ten Position ist der String nach der Map.
//				assertEquals(sExpressionSolved, sValue);
				
				//+++ Teilberechnungen durchführen.
				//    Es werden wg false die Tags nicht entfernt
				vecValue = objExpressionSolver.parseFirstVector(sExpression, false);					
				sValue = VectorUtilZZZ.implode(vecValue);
				assertEquals(sExpression, sValue); //dann sollen auch die Z-Tags drumherum nicht entfernt werden.
				
				sValue = (String) vecValue.get(1);//in der 0ten Position ist der String vor der Map, in der 3ten Position ist der String nach der Map.
				assertTrue(StringZZZ.contains(sExpressionSolved,sValue,false)); //da der Wert selbst nicht als Argument in der Methode uebergeben wurde, koennen wir nur auf Existenz im Gesamtergebnis pruefen.
			
				//+++ Teilberechnungen durchführen
				//    Es werden nomalerweise die Tags entfernt, aber ohne einen angestellten Solver werden sie beim parsen ignoriert 
				vecValue = objExpressionSolver.parseFirstVector(sExpressionSolved, bRemoveSurroundingSeparatorsOnSolve);					
				sValue = VectorUtilZZZ.implode(vecValue);
				assertEquals(sExpression, sValue); //Beim parse fist Vector wird nie der Z-Tag drum herum entfernt. Das ist Aufgabe von parse().
				
				sValue = (String) vecValue.get(1);//in der 0ten Position ist der String vor der Map, in der 3ten Position ist der String nach der Map.
				assertTrue(StringZZZ.contains(sExpressionSolved,sValue,false)); //da der Wert selbst nicht als Argument in der Methode uebergeben wurde, koennen wir nur auf Existenz im Gesamtergebnis pruefen.
			
				
				
				//+++ Nun die Gesamtberechnung durchführen
				sValue = objExpressionSolver.parse(sExpression, objSectionEntryReference,bRemoveSurroundingSeparatorsOnSolve);
				assertEquals(sExpressionSolved, sValue);
							
				objEntry = objSectionEntryReference.get();
				assertNotNull(objEntry);
				
				assertTrue(objEntry.isParseCalled()); //Der Parse-Schritt wurde gemacht.

				assertFalse(objEntry.isParsedChanged()); //es wird ja nix gemacht, also immer "unveraendert"
				
				assertFalse(objEntry.isPathSubstituted()); //ueberhaupt keine Expression verarbeitung
				assertFalse(objEntry.isVariableSubstituted()); //ueberhaupt keine Expression verarbeitung
				
				assertFalse(objEntry.isJson()); //ueberhaupt keine Expression verarbeitung 
				assertFalse(objEntry.isJsonArray());
				assertFalse(objEntry.isJsonMap());		
			
				assertFalse(objEntry.isSolveCalled());
				assertFalse(objEntry.isSolvedChanged());
				
				assertFalse(objEntry.isDecrypted());
				assertNull(objEntry.getValueDecrypted()); //Merke: sValue kann unterschiedlich zu dem decrypted Wert sein. Wenn etwas drumherum steht.
				
				assertFalse(objEntry.isCall());
				assertFalse(objEntry.isJavaCall());
				assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingClassname() + "'", objEntry.getCallingClassname());
				assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingMethodname() + "'", objEntry.getCallingMethodname());
			}
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
				
			//+++ ... solve verhält sich NICHT wie parse(), bei solve wird aufgeloest...
			if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.SOLVE)) {
				sExpression = sExpressionSourceIn;
				sExpressionSolved = sExpressionSolvedIn;
				objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
				sValue = objExpressionSolver.solve(sExpression, objSectionEntryReference, bRemoveSurroundingSeparatorsOnSolve);
				assertEquals(sExpressionSolved, sValue);
				
				objEntry = objSectionEntryReference.get();
				assertNotNull(objEntry);
				
				assertFalse(objEntry.isParseCalled()); //Der Parse-Schritt wurde NICHT gemacht.
				assertFalse(objEntry.isParsedChanged()); //es wird ja nix gemacht, also immer "unveraendert"
				
				assertFalse(objEntry.isPathSubstituted()); //ueberhaupt keine Expression verarbeitung
				assertFalse(objEntry.isVariableSubstituted()); //ueberhaupt keine Expression verarbeitung
				
				assertFalse(objEntry.isJson()); //ueberhaupt keine Expression verarbeitung 
				assertFalse(objEntry.isJsonArray());
				assertFalse(objEntry.isJsonMap());						
			
				assertTrue(objEntry.isSolveCalled());
				assertFalse(objEntry.isSolvedChanged());	
				
				assertFalse(objEntry.isDecrypted());
				assertNull(objEntry.getValueDecrypted()); //Merke: sValue kann unterschiedlich zu dem decrypted Wert sein. Wenn etwas drumherum steht.
								
				assertFalse(objEntry.isCall());
				assertFalse(objEntry.isJavaCall());
				assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingClassname() + "'", objEntry.getCallingClassname());
				assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingMethodname() + "'", objEntry.getCallingMethodname());
			}
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			
			//+++ Variante fuer den AsEntry-Test
			if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.PARSE_AS_ENTRY)) {
				sExpression = sExpressionSourceIn;
				sExpressionSolved = sExpressionSolvedIn;
				objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
				objEntryUsed = objExpressionSolver.parseAsEntry(sExpression, bRemoveSurroundingSeparatorsOnSolve);				
				assertNotNull(objEntryUsed);
							
				assertTrue(objEntryUsed.isParseCalled()); //Der Parse-Schritt wurde gemacht
				assertFalse(objEntryUsed.isParsedChanged()); //es wird ja nix gemacht, also immer "unveraendert"
				
				assertFalse(objEntryUsed.isPathSubstituted()); //ueberhaupt keine Expression verarbeitung
				assertFalse(objEntryUsed.isVariableSubstituted()); //ueberhaupt keine Expression verarbeitung
				
				assertFalse(objEntryUsed.isJson()); //ueberhaupt keine Expression verarbeitung 
				assertFalse(objEntryUsed.isJsonArray());
				assertFalse(objEntryUsed.isJsonMap());	
				
				assertFalse(objEntryUsed.isSolveCalled()); //es ist auch kein Solver involviert
				assertFalse(objEntryUsed.isSolvedChanged());	
				
				sValueUsed = objEntryUsed.getValue();
				assertEquals(sExpressionSolved, sValueUsed);
				
				assertFalse(objEntryUsed.isDecrypted());
				assertNull(objEntryUsed.getValueDecrypted()); //Merke: sValue kann unterschiedlich zu dem decrypted Wert sein. Wenn etwas drumherum steht.
									
				assertFalse(objEntryUsed.isCall());
				assertFalse(objEntryUsed.isJavaCall());
				assertNull(objEntryUsed.getCallingClassname());
				assertNull(objEntryUsed.getCallingMethodname());
			}
			
			
			//+++ ... solve verhält sich NICHT wie parse(), bei solve wird aufgeloest...
			if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.SOLVE_AS_ENTRY)) {
				sExpression = sExpressionSourceIn;
				sExpressionSolved = sExpressionSolvedIn;
				objEntryUsed = objExpressionSolver.solveAsEntry(sExpression, bRemoveSurroundingSeparatorsOnSolve);
				assertNotNull(objEntryUsed);
				
				sValue = objEntryUsed.getValue();
				assertEquals(sExpressionSolved, sValue);
				
				assertTrue(objEntryUsed.isParseCalled()); //Der Parse-Schritt wurde gemacht
				assertFalse(objEntryUsed.isParsedChanged()); //es wird ja nix gemacht, also immer "unveraendert"
				
				assertFalse(objEntryUsed.isPathSubstituted()); //ueberhaupt keine Expression verarbeitung
				assertFalse(objEntryUsed.isVariableSubstituted()); //ueberhaupt keine Expression verarbeitung
				
				assertFalse(objEntryUsed.isJson()); //ueberhaupt keine Expression verarbeitung 
				assertFalse(objEntryUsed.isJsonArray());
				assertFalse(objEntryUsed.isJsonMap());
				
				assertTrue(objEntryUsed.isSolveCalled());
				assertFalse(objEntryUsed.isSolvedChanged());	
				
				assertFalse(objEntryUsed.isDecrypted());
				assertNull(objEntryUsed.getValueDecrypted()); //Merke: sValue kann unterschiedlich zu dem decrypted Wert sein. Wenn etwas drumherum steht.
								
				assertFalse(objEntryUsed.isCall());
				assertFalse(objEntryUsed.isJavaCall());
				assertNull(objEntryUsed.getCallingClassname());
				assertNull(objEntryUsed.getCallingMethodname());
			}
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++	
			
			bReturn = true;
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}
		return bReturn;
	}

	//##############################################################################
	
	private boolean testCompute_JsonArray_Unsolved_(String sExpressionSourceIn, String sExpressionSolvedIn, boolean bRemoveSurroundingSeparatorsOnSolve, IEnumSetMappedTestCaseZZZ objEnumTestCase) {
		boolean bReturn = false;
		try {
			boolean btemp; 
			
			String sExpression; String sExpressionSolved;  
			String sValue; String sValueUsed; Vector3ZZZ<String> vecValue;		
			ReferenceZZZ<IKernelConfigSectionEntryZZZ> objSectionEntryReference;
			IKernelConfigSectionEntryZZZ objEntry; IKernelConfigSectionEntryZZZ objEntryUsed;
			
			
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
			
			btemp = objExpressionSolver.setFlag(IKernelJsonArrayIniSolverZZZ.FLAGZ.USEJSON_ARRAY, true);//Sollte dann egal sein
			assertTrue("Flag nicht vorhanden '" + IKernelJsonArrayIniSolverZZZ.FLAGZ.USEJSON_ARRAY + "'", btemp);
			
			
			
			//+++ ... parse ist nicht solve... also wird hier nichts aufgeloest, aussser die Pfade substituiert.
			if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.PARSE)) {
				sExpression = sExpressionSourceIn;
				sExpressionSolved = sExpressionSolvedIn;		
				
				//+++ Teilberechnungen durchführen.
				//    Es werden wg false die Tags nicht entfernt
				vecValue = objExpressionSolver.parseFirstVector(sExpression, false);					
				sValue = VectorUtilZZZ.implode(vecValue);
				assertEquals(sExpression, sValue); //dann sollen auch die Z-Tags drumherum nicht entfernt werden.
				
				sValue = (String) vecValue.get(1);//in der 0ten Position ist der String vor der Map, in der 3ten Position ist der String nach der Map.
				assertTrue(StringZZZ.contains(sExpressionSolved,sValue,false)); //da der Wert selbst nicht als Argument in der Methode uebergeben wurde, koennen wir nur auf Existenz im Gesamtergebnis pruefen.
			
				//+++ Teilberechnungen durchführen
				//    Es werden nomalerweise die Tags entfernt, aber ohne einen angestellten Solver werden sie beim parsen ignoriert 
				vecValue = objExpressionSolver.parseFirstVector(sExpression, bRemoveSurroundingSeparatorsOnSolve);					
				sValue = VectorUtilZZZ.implode(vecValue);
				assertEquals(sExpression, sValue); //Beim parse fist Vector wird nie der Z-Tag drum herum entfernt. Das ist Aufgabe von parse().
				
				sValue = (String) vecValue.get(1);//in der 0ten Position ist der String vor der Map, in der 3ten Position ist der String nach der Map.
				assertTrue(StringZZZ.contains(sExpressionSolved,sValue,false)); //da der Wert selbst nicht als Argument in der Methode uebergeben wurde, koennen wir nur auf Existenz im Gesamtergebnis pruefen.
			
				
				//+++ Nun die Gesamtberechnung durchführen
				objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
				sValue = objExpressionSolver.parse(sExpression, objSectionEntryReference, bRemoveSurroundingSeparatorsOnSolve);
				assertEquals(sExpressionSolved, sValue);
							
				objEntry = objSectionEntryReference.get();
				assertNotNull(objEntry);
				
				assertTrue(objEntry.isParseCalled()); //Der Parse-Schritt wurde gemacht.
				if(bRemoveSurroundingSeparatorsOnSolve) {
					assertTrue(objEntry.isParsedChanged()); //es werden ja die Z-Tags drumherum entfernt also "veraendert"
				}else {
					assertFalse(objEntry.isParsedChanged()); //es werden ja die Z-Tags drumherum NICHT entfernt also "veraendert"
				}
				
				assertFalse(objEntry.isPathSubstituted());
				assertFalse(objEntry.isVariableSubstituted());
													
				assertFalse(objEntry.isSolveCalled());
				assertFalse(objEntry.isSolvedChanged());
				
				assertTrue(objEntry.isJson());
				assertTrue(objEntry.isJsonArray());
				
				assertFalse(objEntry.isDecrypted());
				assertNull(objEntry.getValueDecrypted()); //Merke: sValue kann unterschiedlich zu dem decrypted Wert sein. Wenn etwas drumherum steht.
				
				assertFalse(objEntry.isCall());
				assertFalse(objEntry.isJavaCall());
				assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingClassname() + "'", objEntry.getCallingClassname());
				assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingMethodname() + "'", objEntry.getCallingMethodname());
			}
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
				
			//+++ ... solve verhält sich NICHT wie parse(), bei solve wird aufgeloest...
			if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.SOLVE)) {
				sExpression = sExpressionSourceIn;
				sExpressionSolved = sExpressionSolvedIn;
				objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
				sValue = objExpressionSolver.solve(sExpression, objSectionEntryReference, bRemoveSurroundingSeparatorsOnSolve);
				assertEquals(sExpressionSolved, sValue);
				
				objEntry = objSectionEntryReference.get();
				assertNotNull(objEntry);
				
				assertTrue(objEntry.isParseCalled()); //Der Parse-Schritt wurde gemacht.
				if(bRemoveSurroundingSeparatorsOnSolve) {
					assertTrue(objEntry.isParsedChanged()); //es werden ja die Z-Tags drumherum entfernt also "veraendert"
				}else {
					assertFalse(objEntry.isParsedChanged()); //es werden ja die Z-Tags drumherum NICHT entfernt also "veraendert"
				}

				
				assertFalse(objEntry.isPathSubstituted());
				assertFalse(objEntry.isVariableSubstituted());
				
				assertTrue(objEntry.isSolveCalled());
				assertFalse(objEntry.isSolvedChanged());
				
				assertTrue(objEntry.isJson());
				assertTrue(objEntry.isJsonArray());
								
				assertFalse(objEntry.isDecrypted());
				assertNull(objEntry.getValueDecrypted()); //Merke: sValue kann unterschiedlich zu dem decrypted Wert sein. Wenn etwas drumherum steht.
								
				assertFalse(objEntry.isCall());
				assertFalse(objEntry.isJavaCall());
				assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingClassname() + "'", objEntry.getCallingClassname());
				assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingMethodname() + "'", objEntry.getCallingMethodname());
			}
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			
			//+++ Variante fuer den AsEntry-Test
			if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.PARSE_AS_ENTRY)) {
				sExpression = sExpressionSourceIn;
				sExpressionSolved = sExpressionSolvedIn;
				objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
				objEntryUsed = objExpressionSolver.parseAsEntry(sExpression, bRemoveSurroundingSeparatorsOnSolve);				
				assertNotNull(objEntryUsed);
							
				assertTrue(objEntryUsed.isParseCalled()); //Der Parse-Schritt wurde gemacht.
				if(bRemoveSurroundingSeparatorsOnSolve) {
					assertTrue(objEntryUsed.isParsedChanged()); //es werden ja die Z-Tags drumherum entfernt also "veraendert"
				}else {
					assertFalse(objEntryUsed.isParsedChanged()); //es werden ja die Z-Tags drumherum NICHT entfernt also "veraendert"
				}

				
				assertFalse(objEntryUsed.isPathSubstituted());
				assertFalse(objEntryUsed.isVariableSubstituted());														
			
				assertFalse(objEntryUsed.isSolveCalled()); //es ist auch kein Solver involviert
				assertFalse(objEntryUsed.isSolvedChanged());
				
				sValueUsed = objEntryUsed.getValue();
				assertEquals(sExpressionSolved, sValueUsed);
				
				assertTrue(objEntryUsed.isJson());
				assertTrue(objEntryUsed.isJsonArray());
				
				assertFalse(objEntryUsed.isDecrypted());
				assertNull(objEntryUsed.getValueDecrypted()); //Merke: sValue kann unterschiedlich zu dem decrypted Wert sein. Wenn etwas drumherum steht.
									
				assertFalse(objEntryUsed.isCall());
				assertFalse(objEntryUsed.isJavaCall());
				assertNull(objEntryUsed.getCallingClassname());
				assertNull(objEntryUsed.getCallingMethodname());
			}
			
			
			//+++ ... solve verhält sich NICHT wie parse(), bei solve wird aufgeloest...
			if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.SOLVE_AS_ENTRY)) {
				sExpression = sExpressionSourceIn;
				sExpressionSolved = sExpressionSolvedIn;
				objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
				objEntryUsed = objExpressionSolver.solveAsEntry(sExpression, objSectionEntryReference, bRemoveSurroundingSeparatorsOnSolve);
				assertNotNull(objEntryUsed);
				
				sValue = objEntryUsed.getValue();
				assertEquals(sExpressionSolved, sValue);
				
				assertTrue(objEntryUsed.isParseCalled()); //Der Parse-Schritt wurde gemacht.
				if(bRemoveSurroundingSeparatorsOnSolve) {
					assertTrue(objEntryUsed.isParsedChanged()); //es werden ja die Z-Tags drumherum entfernt also "veraendert"
				}else {
					assertFalse(objEntryUsed.isParsedChanged()); //es werden ja die Z-Tags drumherum NICHT entfernt also "veraendert"
				}

				
				assertFalse(objEntryUsed.isPathSubstituted());
				assertFalse(objEntryUsed.isVariableSubstituted());
	
				assertTrue(objEntryUsed.isSolveCalled());
				assertFalse(objEntryUsed.isSolvedChanged());		
				
				assertTrue(objEntryUsed.isJson());
				assertTrue(objEntryUsed.isJsonArray());
								
				assertFalse(objEntryUsed.isDecrypted());
				assertNull(objEntryUsed.getValueDecrypted()); //Merke: sValue kann unterschiedlich zu dem decrypted Wert sein. Wenn etwas drumherum steht.
								
				assertFalse(objEntryUsed.isCall());
				assertFalse(objEntryUsed.isJavaCall());
				assertNull(objEntryUsed.getCallingClassname());
				assertNull(objEntryUsed.getCallingMethodname());
			}
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++	
			
			bReturn = true;
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}
		return bReturn;
	}
	
	
	
	//########################################################################
	
	private boolean testCompute_JsonArray_JsonUnsolved_(String sExpressionSourceIn, String sExpressionSolvedIn, boolean bRemoveSurroundingSeparatorsOnSolve, IEnumSetMappedTestCaseZZZ objEnumTestCase) {
		boolean bReturn = false;
		try {
			boolean btemp; 
			
			String sExpression; String sExpressionSolved;  
			String sValue; String sValueUsed; Vector3ZZZ<String> vecValue;		
			ReferenceZZZ<IKernelConfigSectionEntryZZZ> objSectionEntryReference;
			IKernelConfigSectionEntryZZZ objEntry; IKernelConfigSectionEntryZZZ objEntryUsed;
			
			
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
			
			btemp = objExpressionSolver.setFlag(IKernelJsonArrayIniSolverZZZ.FLAGZ.USEJSON_ARRAY, true);//Sollte dann egal sein
			assertTrue("Flag nicht vorhanden '" + IKernelJsonArrayIniSolverZZZ.FLAGZ.USEJSON_ARRAY + "'", btemp);
			
			
			
			//+++ ... parse ist nicht solve... also wird hier nichts aufgeloest, aussser die Pfade substituiert.
			if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.PARSE)) {
				sExpression = sExpressionSourceIn;
				sExpressionSolved = sExpressionSolvedIn;		
				
				//+++ Teilberechnungen durchführen.
				//    Es werden wg false die Tags nicht entfernt
				vecValue = objExpressionSolver.parseFirstVector(sExpression, false);					
				sValue = VectorUtilZZZ.implode(vecValue);
				assertEquals(sExpression, sValue); //dann sollen auch die Z-Tags drumherum nicht entfernt werden.
				
				sValue = (String) vecValue.get(1);//in der 0ten Position ist der String vor der Map, in der 3ten Position ist der String nach der Map.
				assertTrue(StringZZZ.contains(sExpressionSolved,sValue,false)); //da der Wert selbst nicht als Argument in der Methode uebergeben wurde, koennen wir nur auf Existenz im Gesamtergebnis pruefen.
			
				//+++ Teilberechnungen durchführen
				//    Es werden nomalerweise die Tags entfernt, aber ohne einen angestellten Solver werden sie beim parsen ignoriert 
				vecValue = objExpressionSolver.parseFirstVector(sExpression, bRemoveSurroundingSeparatorsOnSolve);					
				sValue = VectorUtilZZZ.implode(vecValue);
				assertEquals(sExpression, sValue); //Beim parse fist Vector wird nie der Z-Tag drum herum entfernt. Das ist Aufgabe von parse().
				
				sValue = (String) vecValue.get(1);//in der 0ten Position ist der String vor der Map, in der 3ten Position ist der String nach der Map.
				assertTrue(StringZZZ.contains(sExpressionSolved,sValue,false)); //da der Wert selbst nicht als Argument in der Methode uebergeben wurde, koennen wir nur auf Existenz im Gesamtergebnis pruefen.
			
			
				
				
				//+++ Nun die Gesamtberechnung durchführen
				objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
				sValue = objExpressionSolver.parse(sExpression, objSectionEntryReference, bRemoveSurroundingSeparatorsOnSolve);
				assertEquals(sExpressionSolved, sValue);
							
				objEntry = objSectionEntryReference.get();
				assertNotNull(objEntry);
				
				assertTrue(objEntry.isParseCalled()); //Der Parse-Schritt wurde gemacht.
				if(bRemoveSurroundingSeparatorsOnSolve) {
					assertTrue(objEntry.isParsedChanged()); //es werden ja die Z-Tags drumherum entfernt also "veraendert"
				}else {
					assertFalse(objEntry.isParsedChanged()); //es werden ja die Z-Tags drumherum NICHT entfernt also "veraendert"
				}
				
				assertFalse(objEntry.isPathSubstituted());
				assertFalse(objEntry.isVariableSubstituted());
				
				assertFalse(objEntry.isSolveCalled());
				assertFalse(objEntry.isSolvedChanged());
				
				assertFalse(objEntry.isDecrypted());
				assertNull(objEntry.getValueDecrypted()); //Merke: sValue kann unterschiedlich zu dem decrypted Wert sein. Wenn etwas drumherum steht.
				
				assertFalse(objEntry.isCall());
				assertFalse(objEntry.isJavaCall());
				assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingClassname() + "'", objEntry.getCallingClassname());
				assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingMethodname() + "'", objEntry.getCallingMethodname());
			}
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
				
			//+++ ... solve verhält sich NICHT wie parse(), bei solve wird aufgeloest...
			if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.SOLVE)) {
				sExpression = sExpressionSourceIn;
				sExpressionSolved = sExpressionSolvedIn;
				objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
				sValue = objExpressionSolver.solve(sExpression, objSectionEntryReference, bRemoveSurroundingSeparatorsOnSolve);
				assertEquals(sExpressionSolved, sValue);
				
				objEntry = objSectionEntryReference.get();
				assertNotNull(objEntry);
				
				assertTrue(objEntry.isParseCalled()); //Der Parse-Schritt wurde gemacht.
				if(bRemoveSurroundingSeparatorsOnSolve) {
					assertTrue(objEntry.isParsedChanged()); //es werden ja die Z-Tags drumherum entfernt also "veraendert"
				}else {
					assertFalse(objEntry.isParsedChanged()); //es werden ja die Z-Tags drumherum NICHT entfernt also "veraendert"
				}
				
				assertFalse(objEntry.isPathSubstituted());
				assertFalse(objEntry.isVariableSubstituted());
				
				assertTrue(objEntry.isSolveCalled());
				assertFalse(objEntry.isSolvedChanged());
								
				assertFalse(objEntry.isDecrypted());
				assertNull(objEntry.getValueDecrypted()); //Merke: sValue kann unterschiedlich zu dem decrypted Wert sein. Wenn etwas drumherum steht.
								
				assertFalse(objEntry.isCall());
				assertFalse(objEntry.isJavaCall());
				assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingClassname() + "'", objEntry.getCallingClassname());
				assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingMethodname() + "'", objEntry.getCallingMethodname());
			}
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			
			//+++ Variante fuer den AsEntry-Test
			if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.PARSE_AS_ENTRY)) {
				sExpression = sExpressionSourceIn;
				sExpressionSolved = sExpressionSolvedIn;
				objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
				objEntryUsed = objExpressionSolver.parseAsEntry(sExpression, bRemoveSurroundingSeparatorsOnSolve);				
				assertNotNull(objEntryUsed);
							
				assertTrue(objEntryUsed.isParseCalled()); //Der Parse-Schritt wurde gemacht.
				if(bRemoveSurroundingSeparatorsOnSolve) {
					assertTrue(objEntryUsed.isParsedChanged()); //es werden ja die Z-Tags drumherum entfernt also "veraendert"
				}else {
					assertFalse(objEntryUsed.isParsedChanged()); //es werden ja die Z-Tags drumherum NICHT entfernt also "veraendert"
				}
				
				assertFalse(objEntryUsed.isPathSubstituted());
				assertFalse(objEntryUsed.isVariableSubstituted());
				
				assertFalse(objEntryUsed.isSolveCalled()); //es ist auch kein Solver involviert
				assertFalse(objEntryUsed.isSolvedChanged());
				
				sValueUsed = objEntryUsed.getValue();
				assertEquals(sExpressionSolved, sValueUsed);
				
				assertFalse(objEntryUsed.isDecrypted());
				assertNull(objEntryUsed.getValueDecrypted()); //Merke: sValue kann unterschiedlich zu dem decrypted Wert sein. Wenn etwas drumherum steht.
									
				assertFalse(objEntryUsed.isCall());
				assertFalse(objEntryUsed.isJavaCall());
				assertNull(objEntryUsed.getCallingClassname());
				assertNull(objEntryUsed.getCallingMethodname());
			}
			
			
			//+++ ... solve verhält sich NICHT wie parse(), bei solve wird aufgeloest...
			if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.SOLVE_AS_ENTRY)) {
				sExpression = sExpressionSourceIn;
				sExpressionSolved = sExpressionSolvedIn;

				objEntryUsed = objExpressionSolver.solveAsEntry(sExpression, bRemoveSurroundingSeparatorsOnSolve);
				assertNotNull(objEntryUsed);
				
				sValue = objEntryUsed.getValue();
				assertEquals(sExpressionSolved, sValue);
				
				assertTrue(objEntryUsed.isParseCalled()); //Der Parse-Schritt wurde gemacht.
				if(bRemoveSurroundingSeparatorsOnSolve) {
					assertTrue(objEntryUsed.isParsedChanged()); //es werden ja die Z-Tags drumherum entfernt also "veraendert"
				}else {
					assertFalse(objEntryUsed.isParsedChanged()); //es werden ja die Z-Tags drumherum NICHT entfernt also "veraendert"
				}
				
				assertFalse(objEntryUsed.isPathSubstituted());
				assertFalse(objEntryUsed.isVariableSubstituted());
					
				assertTrue(objEntryUsed.isSolveCalled());
				assertFalse(objEntryUsed.isSolvedChanged());
								
				assertFalse(objEntryUsed.isDecrypted());
				assertNull(objEntryUsed.getValueDecrypted()); //Merke: sValue kann unterschiedlich zu dem decrypted Wert sein. Wenn etwas drumherum steht.
								
				assertFalse(objEntryUsed.isCall());
				assertFalse(objEntryUsed.isJavaCall());
				assertNull(objEntryUsed.getCallingClassname());
				assertNull(objEntryUsed.getCallingMethodname());
			}
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++	
			
			bReturn = true;
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}
		return bReturn;
	}
	
	
	
	//########################################################################
	
	
	private boolean testCompute_JsonArray_JsonArrayUnsolved_(String sExpressionSourceIn, String sExpressionSolvedIn, boolean bRemoveSurroundingSeparatorsOnSolve, IEnumSetMappedTestCaseZZZ objEnumTestCase) {
		boolean bReturn = false;
		try {
			boolean btemp; 
			
			String sExpression; String sExpressionSolved;  
			String sValue; String sValueUsed; Vector3ZZZ<String> vecValue;		
			ReferenceZZZ<IKernelConfigSectionEntryZZZ> objSectionEntryReference;
			IKernelConfigSectionEntryZZZ objEntry; IKernelConfigSectionEntryZZZ objEntryUsed;
			
			
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
			
			btemp = objExpressionSolver.setFlag(IKernelJsonArrayIniSolverZZZ.FLAGZ.USEJSON_ARRAY, true);//Sollte dann egal sein
			assertTrue("Flag nicht vorhanden '" + IKernelJsonArrayIniSolverZZZ.FLAGZ.USEJSON_ARRAY + "'", btemp);
			
			
			
			//+++ ... parse ist nicht solve... also wird hier nichts aufgeloest, aussser die Pfade substituiert.
			if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.PARSE)) {
				sExpression = sExpressionSourceIn;
				sExpressionSolved = sExpressionSolvedIn;		
								
				//+++ Teilberechnungen durchführen.
				//    Es werden wg false die Tags nicht entfernt
				vecValue = objExpressionSolver.parseFirstVector(sExpression, false);					
				sValue = VectorUtilZZZ.implode(vecValue);
				assertEquals(sExpression, sValue); //dann sollen auch die Z-Tags drumherum nicht entfernt werden.
				
				sValue = (String) vecValue.get(1);//in der 0ten Position ist der String vor der Map, in der 3ten Position ist der String nach der Map.
				assertTrue(StringZZZ.contains(sExpressionSolved,sValue,false)); //da der Wert selbst nicht als Argument in der Methode uebergeben wurde, koennen wir nur auf Existenz im Gesamtergebnis pruefen.
			
				//+++ Teilberechnungen durchführen
				//    Es werden nomalerweise die Tags entfernt, aber ohne einen angestellten Solver werden sie beim parsen ignoriert 
				vecValue = objExpressionSolver.parseFirstVector(sExpression, bRemoveSurroundingSeparatorsOnSolve);					
				sValue = VectorUtilZZZ.implode(vecValue);
				assertEquals(sExpression, sValue); //Beim parse fist Vector wird nie der Z-Tag drum herum entfernt. Das ist Aufgabe von parse().
				
				sValue = (String) vecValue.get(1);//in der 0ten Position ist der String vor der Map, in der 3ten Position ist der String nach der Map.
				assertTrue(StringZZZ.contains(sExpressionSolved,sValue,false)); //da der Wert selbst nicht als Argument in der Methode uebergeben wurde, koennen wir nur auf Existenz im Gesamtergebnis pruefen.

				
				//+++ Nun die Gesamtberechnung durchführen
				objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
				sValue = objExpressionSolver.parse(sExpression, objSectionEntryReference, bRemoveSurroundingSeparatorsOnSolve);
				assertEquals(sExpressionSolved, sValue);
							
				objEntry = objSectionEntryReference.get();
				assertNotNull(objEntry);
				
				assertTrue(objEntry.isParseCalled()); //Der Parse-Schritt wurde gemacht.
				if(bRemoveSurroundingSeparatorsOnSolve) {
					assertTrue(objEntry.isParsedChanged()); //es werden ja die Z-Tags drumherum entfernt also "veraendert"
				}else {
					assertFalse(objEntry.isParsedChanged()); //es werden ja die Z-Tags drumherum NICHT entfernt also "veraendert"
				}
				
				assertFalse(objEntry.isPathSubstituted());
				assertFalse(objEntry.isVariableSubstituted());
			
				assertFalse(objEntry.isSolveCalled());
				assertFalse(objEntry.isSolvedChanged());
				
				assertFalse(objEntry.isDecrypted());
				assertNull(objEntry.getValueDecrypted()); //Merke: sValue kann unterschiedlich zu dem decrypted Wert sein. Wenn etwas drumherum steht.
				
				assertFalse(objEntry.isCall());
				assertFalse(objEntry.isJavaCall());
				assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingClassname() + "'", objEntry.getCallingClassname());
				assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingMethodname() + "'", objEntry.getCallingMethodname());
			}
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
				
			//+++ ... solve verhält sich NICHT wie parse(), bei solve wird aufgeloest...
			if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.SOLVE)) {
				sExpression = sExpressionSourceIn;
				sExpressionSolved = sExpressionSolvedIn;
				objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
				sValue = objExpressionSolver.solve(sExpression, objSectionEntryReference, bRemoveSurroundingSeparatorsOnSolve);
				assertEquals(sExpressionSolved, sValue);
				
				objEntry = objSectionEntryReference.get();
				assertNotNull(objEntry);
				
				assertTrue(objEntry.isParseCalled()); //Der Parse-Schritt wurde gemacht.
				if(bRemoveSurroundingSeparatorsOnSolve) {
					assertTrue(objEntry.isParsedChanged()); //es werden ja die Z-Tags drumherum entfernt also "veraendert"
				}else {
					assertFalse(objEntry.isParsedChanged()); //es werden ja die Z-Tags drumherum NICHT entfernt also "veraendert"
				}
				
				assertFalse(objEntry.isPathSubstituted());
				assertFalse(objEntry.isVariableSubstituted());
				
										
			
				assertTrue(objEntry.isSolveCalled());
				assertFalse(objEntry.isSolvedChanged());
								
				assertFalse(objEntry.isDecrypted());
				assertNull(objEntry.getValueDecrypted()); //Merke: sValue kann unterschiedlich zu dem decrypted Wert sein. Wenn etwas drumherum steht.
								
				assertFalse(objEntry.isCall());
				assertFalse(objEntry.isJavaCall());
				assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingClassname() + "'", objEntry.getCallingClassname());
				assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingMethodname() + "'", objEntry.getCallingMethodname());
			}
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			
			//+++ Variante fuer den AsEntry-Test
			if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.PARSE_AS_ENTRY)) {
				sExpression = sExpressionSourceIn;
				sExpressionSolved = sExpressionSolvedIn;
				objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
				objEntryUsed = objExpressionSolver.parseAsEntry(sExpression, bRemoveSurroundingSeparatorsOnSolve);				
				assertNotNull(objEntryUsed);
							
				assertTrue(objEntryUsed.isParseCalled()); //Der Parse-Schritt wurde gemacht.
				if(bRemoveSurroundingSeparatorsOnSolve) {
					assertTrue(objEntryUsed.isParsedChanged()); //es werden ja die Z-Tags drumherum entfernt also "veraendert"
				}else {
					assertFalse(objEntryUsed.isParsedChanged()); //es werden ja die Z-Tags drumherum NICHT entfernt also "veraendert"
				}
				
				assertFalse(objEntryUsed.isPathSubstituted());
				assertFalse(objEntryUsed.isVariableSubstituted());
				
				assertFalse(objEntryUsed.isSolveCalled()); //es ist auch kein Solver involviert
				assertFalse(objEntryUsed.isSolvedChanged());
				
				sValueUsed = objEntryUsed.getValue();
				assertEquals(sExpressionSolved, sValueUsed);
				
				assertFalse(objEntryUsed.isDecrypted());
				assertNull(objEntryUsed.getValueDecrypted()); //Merke: sValue kann unterschiedlich zu dem decrypted Wert sein. Wenn etwas drumherum steht.
									
				assertFalse(objEntryUsed.isCall());
				assertFalse(objEntryUsed.isJavaCall());
				assertNull(objEntryUsed.getCallingClassname());
				assertNull(objEntryUsed.getCallingMethodname());
			}
			
			
			//+++ ... solve verhält sich NICHT wie parse(), bei solve wird aufgeloest...
			if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.SOLVE_AS_ENTRY)) {
				sExpression = sExpressionSourceIn;
				sExpressionSolved = sExpressionSolvedIn;
				objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
				sValue = objExpressionSolver.solve(sExpression, objSectionEntryReference, bRemoveSurroundingSeparatorsOnSolve);
				assertEquals(sExpressionSolved, sValue);
				
				objEntryUsed = objSectionEntryReference.get();
				assertNotNull(objEntryUsed);
				
				assertTrue(objEntryUsed.isParseCalled()); //Der Parse-Schritt wurde gemacht.
				if(bRemoveSurroundingSeparatorsOnSolve) {
					assertTrue(objEntryUsed.isParsedChanged()); //es werden ja die Z-Tags drumherum entfernt also "veraendert"
				}else {
					assertFalse(objEntryUsed.isParsedChanged()); //es werden ja die Z-Tags drumherum NICHT entfernt also "veraendert"
				}
				
				assertFalse(objEntryUsed.isPathSubstituted());
				assertFalse(objEntryUsed.isVariableSubstituted());

					
				assertTrue(objEntryUsed.isSolveCalled());
				assertFalse(objEntryUsed.isSolvedChanged());		
				
				assertFalse(objEntryUsed.isDecrypted());
				assertNull(objEntryUsed.getValueDecrypted()); //Merke: sValue kann unterschiedlich zu dem decrypted Wert sein. Wenn etwas drumherum steht.
								
				assertFalse(objEntryUsed.isCall());
				assertFalse(objEntryUsed.isJavaCall());
				assertNull(objEntryUsed.getCallingClassname());
				assertNull(objEntryUsed.getCallingMethodname());
			}
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++	
			
			bReturn = true;
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}
		return bReturn;
	}
	
	
	
	//########################################################################

	private boolean testCompute_JsonArray_(String sExpressionSourceIn, String sExpressionSolvedIn, boolean bRemoveSurroundingSeparatorsOnSolve, IEnumSetMappedTestCaseZZZ objEnumTestCase) {
		boolean bReturn = false;
		try {
			boolean btemp; 
			
			String sExpression; String sExpressionSolved;  
			String sValue; String sValueUsed; Vector3ZZZ<String> vecValue;		
			ReferenceZZZ<IKernelConfigSectionEntryZZZ> objSectionEntryReference;
			IKernelConfigSectionEntryZZZ objEntry; IKernelConfigSectionEntryZZZ objEntryUsed;
			
			
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
			
			btemp = objExpressionSolver.setFlag(IKernelJsonArrayIniSolverZZZ.FLAGZ.USEJSON_ARRAY, true);//Sollte dann egal sein
			assertTrue("Flag nicht vorhanden '" + IKernelJsonArrayIniSolverZZZ.FLAGZ.USEJSON_ARRAY + "'", btemp);
			
			
			//+++ ... parse ist nicht solve... also wird hier nichts aufgeloest, aussser die Pfade substituiert.
			if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.PARSE)) {
				sExpression = sExpressionSourceIn;
				sExpressionSolved = sExpressionSolvedIn;		
				
				//+++ Teilberechnungen durchführen.
				//    Es werden wg false die Tags nicht entfernt
				vecValue = objExpressionSolver.parseFirstVector(sExpression, false);					
				sValue = VectorUtilZZZ.implode(vecValue);
				assertEquals(sExpression, sValue); //dann sollen auch die Z-Tags drumherum nicht entfernt werden.
				
				sValue = (String) vecValue.get(1);//in der 0ten Position ist der String vor der Map, in der 3ten Position ist der String nach der Map.
				assertTrue(StringZZZ.contains(sExpressionSolved,sValue,false)); //da der Wert selbst nicht als Argument in der Methode uebergeben wurde, koennen wir nur auf Existenz im Gesamtergebnis pruefen.
			
				//+++ Teilberechnungen durchführen
				//    Es werden nomalerweise die Tags entfernt, bei einem angestellten Solver werden sie beim parsen ignoriert 
				vecValue = objExpressionSolver.parseFirstVector(sExpression, bRemoveSurroundingSeparatorsOnSolve);					
				sValue = VectorUtilZZZ.implode(vecValue);
				String sExpressionSolvedTemp = ExpressionIniUtilZZZ.makeAsExpression(sExpressionSolved);
				assertEquals(sExpressionSolvedTemp, sValue); //Beim parse fist Vector wird nie der Z-Tag drum herum entfernt. Das ist Aufgabe von parse().
				
				sValue = (String) vecValue.get(1);//in der 0ten Position ist der String vor der Map, in der 3ten Position ist der String nach der Map.
				assertTrue(StringZZZ.contains(sExpressionSolved,sValue,false)); //da der Wert selbst nicht als Argument in der Methode uebergeben wurde, koennen wir nur auf Existenz im Gesamtergebnis pruefen.

				
				//+++ Nun die Gesamtberechnung durchführen
				objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
				sValue = objExpressionSolver.parse(sExpression, objSectionEntryReference, bRemoveSurroundingSeparatorsOnSolve);
				assertEquals(sExpressionSolved, sValue);
							
				objEntry = objSectionEntryReference.get();
				assertNotNull(objEntry);
				
				assertTrue(objEntry.isParseCalled()); //Der Parse-Schritt wurde gemacht.
				if(bRemoveSurroundingSeparatorsOnSolve) {
					assertTrue(objEntry.isParsedChanged()); //es werden ja die Z-Tags drumherum entfernt also "veraendert"
				}else {
					assertFalse(objEntry.isParsedChanged()); //es werden ja die Z-Tags drumherum NICHT entfernt also "veraendert"
				}
			
				assertFalse(objEntry.isPathSubstituted());
				assertFalse(objEntry.isVariableSubstituted());
				
				assertFalse(objEntry.isSolveCalled());
				assertFalse(objEntry.isSolvedChanged());
				
				assertFalse(objEntry.isDecrypted());
				assertNull(objEntry.getValueDecrypted()); //Merke: sValue kann unterschiedlich zu dem decrypted Wert sein. Wenn etwas drumherum steht.
				
				assertFalse(objEntry.isCall());
				assertFalse(objEntry.isJavaCall());
				assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingClassname() + "'", objEntry.getCallingClassname());
				assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingMethodname() + "'", objEntry.getCallingMethodname());
			}
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
				
			//+++ ... solve verhält sich NICHT wie parse(), bei solve wird aufgeloest...
			if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.SOLVE)) {
				sExpression = sExpressionSourceIn;
				sExpressionSolved = sExpressionSolvedIn;
				objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
				sValue = objExpressionSolver.solve(sExpression, objSectionEntryReference, bRemoveSurroundingSeparatorsOnSolve);
				assertEquals(sExpressionSolved, sValue);
				
				objEntry = objSectionEntryReference.get();
				assertNotNull(objEntry);
				
				assertTrue(objEntry.isParseCalled()); //Der Parse-Schritt wurde gemacht.
				if(bRemoveSurroundingSeparatorsOnSolve) {
					assertTrue(objEntry.isParsedChanged()); //es werden ja die Z-Tags drumherum entfernt also "veraendert"
				}else {
					assertFalse(objEntry.isParsedChanged()); //es werden ja die Z-Tags drumherum NICHT entfernt also "veraendert"
				}
//				assertTrue(objEntry.isParsedChanged()); //Beim Aufloesen werden die Z-Tags des Solvers entfernt also "veraendert"
				
				
				assertFalse(objEntry.isPathSubstituted());
				assertFalse(objEntry.isVariableSubstituted());
				
				assertTrue(objEntry.isSolveCalled());
				assertTrue(objEntry.isSolvedChanged());
				
				assertFalse(objEntry.isDecrypted());
				assertNull(objEntry.getValueDecrypted()); //Merke: sValue kann unterschiedlich zu dem decrypted Wert sein. Wenn etwas drumherum steht.
								
				assertFalse(objEntry.isCall());
				assertFalse(objEntry.isJavaCall());
				assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingClassname() + "'", objEntry.getCallingClassname());
				assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingMethodname() + "'", objEntry.getCallingMethodname());
			}
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			
			//+++ Variante fuer den AsEntry-Test
			if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.PARSE_AS_ENTRY)) {
				sExpression = sExpressionSourceIn;
				sExpressionSolved = sExpressionSolvedIn;
				objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
				objEntryUsed = objExpressionSolver.parseAsEntry(sExpression, bRemoveSurroundingSeparatorsOnSolve);				
				assertNotNull(objEntryUsed);
							
				assertTrue(objEntryUsed.isParseCalled()); //Der Parse-Schritt wurde gemacht.
				if(bRemoveSurroundingSeparatorsOnSolve) {
					assertTrue(objEntryUsed.isParsedChanged()); //es werden ja die Z-Tags drumherum entfernt also "veraendert"
				}else {
					assertFalse(objEntryUsed.isParsedChanged()); //es werden ja die Z-Tags drumherum NICHT entfernt also "veraendert"
				}
				
				assertFalse(objEntryUsed.isPathSubstituted());
				assertFalse(objEntryUsed.isVariableSubstituted());													
			
				assertFalse(objEntryUsed.isSolveCalled()); //es ist auch kein Solver involviert
				assertFalse(objEntryUsed.isSolvedChanged());				
				
				sValueUsed = objEntryUsed.getValue();
				assertEquals(sExpressionSolved, sValueUsed);
				
				assertFalse(objEntryUsed.isDecrypted());
				assertNull(objEntryUsed.getValueDecrypted()); //Merke: sValue kann unterschiedlich zu dem decrypted Wert sein. Wenn etwas drumherum steht.
									
				assertFalse(objEntryUsed.isCall());
				assertFalse(objEntryUsed.isJavaCall());
				assertNull(objEntryUsed.getCallingClassname());
				assertNull(objEntryUsed.getCallingMethodname());
			}
			
			
			//+++ ... solve verhält sich NICHT wie parse(), bei solve wird aufgeloest...
			if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.SOLVE_AS_ENTRY)) {
				sExpression = sExpressionSourceIn;
				sExpressionSolved = sExpressionSolvedIn;
				objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
				objEntryUsed = objExpressionSolver.solveAsEntry(sExpression, bRemoveSurroundingSeparatorsOnSolve);
				assertNotNull(objEntryUsed);
				
				sValue = objEntryUsed.getValue();
				assertEquals(sExpressionSolved, sValue);
				
				objEntryUsed = objSectionEntryReference.get();
				
				
				assertTrue(objEntryUsed.isParseCalled()); //Der Parse-Schritt wurde gemacht.
				if(bRemoveSurroundingSeparatorsOnSolve) {
					assertTrue(objEntryUsed.isParsedChanged()); //es werden ja die Z-Tags drumherum entfernt also "veraendert"
				}else {
					assertFalse(objEntryUsed.isParsedChanged()); //es werden ja die Z-Tags drumherum NICHT entfernt also "veraendert"
				}
//				assertTrue(objEntryUsed.isParsedChanged()); //Beim Aufloesen werden die Z-Tags des Solvers entfernt also "veraendert"
				
				assertFalse(objEntryUsed.isPathSubstituted());
				assertFalse(objEntryUsed.isVariableSubstituted());
					
				assertTrue(objEntryUsed.isSolveCalled());
				assertTrue(objEntryUsed.isSolvedChanged());		
				
				assertFalse(objEntryUsed.isDecrypted());
				assertNull(objEntryUsed.getValueDecrypted()); //Merke: sValue kann unterschiedlich zu dem decrypted Wert sein. Wenn etwas drumherum steht.
								
				assertFalse(objEntryUsed.isCall());
				assertFalse(objEntryUsed.isJavaCall());
				assertNull(objEntryUsed.getCallingClassname());
				assertNull(objEntryUsed.getCallingMethodname());
			}
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++	
			
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
	public void testComputeArray(){
		boolean btemp;
		String sExpression = sEXPRESSION_JSONARRAY01_DEFAULT;
		try {					
			btemp = objExpressionSolver.setFlag(IKernelJsonIniSolverZZZ.FLAGZ.USEJSON, false);
			assertTrue("Flag nicht vorhanden '" + IKernelJsonIniSolverZZZ.FLAGZ.USEJSON + "'", btemp);
			
		
			//### Teilberechnungen durchführen
			Vector<String> vecReturn = objExpressionSolver.parseFirstVector(sExpression);
			assertFalse(StringZZZ.isEmpty(vecReturn.get(1))); //in der 0ten Position ist der String vor der Map, in der 3ten Position ist der String nach der Map.
			
			
			//### Nun die Gesamtberechnung durchführen
			ArrayList<String>ls1 = objExpressionSolver.computeArrayList(sExpression);
			assertTrue("Ohne Auflösung soll es keine HashMap geben",ls1.size()==0);
				
			
			
			//##############################################################################
			//### Expression solved Fall
			btemp = objExpressionSolver.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION, true); //Ansonsten wird der Wert sofort ausgerechnet 
			assertTrue("Flag nicht vorhanden '" + IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
			
			btemp = objExpressionSolver.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH, true);			
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
		
			btemp = objExpressionSolver.setFlag(IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE, true);			
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE + "'", btemp);
			
			btemp = objExpressionSolver.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER, true);
			assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
			
			btemp = objExpressionSolver.setFlag(IKernelJsonIniSolverZZZ.FLAGZ.USEJSON, true);
			assertTrue("Flag nicht vorhanden '" + IKernelJsonIniSolverZZZ.FLAGZ.USEJSON + "'", btemp);
			
			btemp = objExpressionSolver.setFlag(IKernelJsonArrayIniSolverZZZ.FLAGZ.USEJSON_ARRAY, true);//Sollte dann egal sein
			assertTrue("Flag nicht vorhanden '" + IKernelJsonArrayIniSolverZZZ.FLAGZ.USEJSON_ARRAY + "'", btemp);
		
			ArrayList<String>ls2 = objExpressionSolver.computeArrayList(sExpression);
			assertTrue("Mit Auflösung des String soll die ArrayList entsprechende Größe haben. ",ls2.size()==2);

			String sValue = ArrayListExtendedZZZ.computeDebugString(ls2);	
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
	
