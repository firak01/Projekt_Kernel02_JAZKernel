package basic.zKernel.file.ini;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IObjectWithExpressionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedTestCaseZZZ;
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
		String sValue; String sExpressionSolved; String sExpressionSource; String sExpressionSource2;String sExpessionSourceFormulaMath;
		String sTagStartZ;	String sTagEndZ;
		boolean btemp; IKernelConfigSectionEntryZZZ objEntryTemp;
		sExpressionSource = KernelJsonMapIniSolverZZZTest.sEXPRESSION_JSONMAP01_DEFAULT;;
		
		try {			
			btemp = objExpressionSolver.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION, true); 
			assertTrue("Flag nicht vorhanden '" + IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
			
			btemp = objExpressionSolver.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER,true); 
			assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
			
			btemp = objExpressionSolver.setFlag(IKernelJsonIniSolverZZZ.FLAGZ.USEJSON, false); //Ansonsten wird der Wert sofort ausgerechnet
			assertTrue("Das Flag '"+ IKernelJsonIniSolverZZZ.FLAGZ.USEJSON +" sollte zur Verfügung stehen.", btemp);
			
			btemp = objExpressionSolver.setFlag(IKernelJsonMapIniSolverZZZ.FLAGZ.USEJSON_MAP, false); //Ansonsten wird der Wert sofort ausgerechnet
			assertTrue("Das Flag '"+ IKernelJsonMapIniSolverZZZ.FLAGZ.USEJSON_MAP +" sollte zur Verfügung stehen.", btemp);
			
			
			//### Teilberechnungen durchführen
			Vector<String> vecReturn = objExpressionSolver.parseFirstVector(sExpressionSource);
			assertFalse(StringZZZ.isEmpty(vecReturn.get(0))); //in der 0ten Position ist der String vor der Encryption, in der 3ten Position ist der String nach der Encryption.
			assertFalse(StringZZZ.isEmpty(vecReturn.get(1))); //in der 0ten Position ist der String vor der Encryption, in der 3ten Position ist der String nach der Encryption.
			assertFalse(StringZZZ.isEmpty(vecReturn.get(2))); //in der 0ten Position ist der String vor der Encryption, in der 3ten Position ist der String nach der Encryption.		
			
			//### Gesamtberechnung durchführen
			sTagStartZ = "<Z>";
			sTagEndZ = "</Z>";
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSource, sTagStartZ, sTagEndZ);			
		
			sValue = objExpressionSolver.parse(sExpressionSource);
			assertEquals("Ohne Auflösung soll Ausgabe gleich Eingabe sein",sExpressionSolved, sValue);
		
			//Entry auswerten
			objEntryTemp = objExpressionSolver.getEntry();
			assertTrue(objEntryTemp.isParseCalled());
			assertTrue(objEntryTemp.isJson()); //das hat der Parser alles herausgefunden.
			assertTrue(objEntryTemp.isJsonMap());
			
			//### Anwenden der ersten Formel
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
			sExpressionSolved = ExpressionIniUtilZZZ.makeAsExpression(sExpressionSolved, "JSON");
			sValue = objExpressionSolver.solve(sExpressionSource);			
			System.out.println(ReflectCodeZZZ.getPositionCurrent() + "\tDebugausagabe: '" + sValue + "'\n");
			
			assertEquals(sExpressionSolved, sValue);

			//Entry auswerten
			objEntryTemp = objExpressionSolver.getEntry();
			assertTrue(objEntryTemp.isParseCalled());
			assertTrue(objEntryTemp.isParsedChanged());
			
			assertTrue(objEntryTemp.isJson());
			assertTrue(objEntryTemp.isJsonMap());
			
			assertTrue(objEntryTemp.isSolveCalled());
			assertTrue(objEntryTemp.isSolvedChanged());
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
			
			//######################################
			//Einfacher Test: Leerstring
			sExpressionSource = "";
			sExpression = sExpressionSource;
			sLineWithJson = sExpression;
			hm = objExpressionSolver.computeHashMapFromJson(sLineWithJson);
			assertNotNull(hm);
			assertEquals(0,hm.size());//Leerstring ist kein gültiger JSON String, darum kommt nix raus.
			
			//Einfacher Test: Dummystring
			sExpressionSource = "dummy";
			sExpression = sExpressionSource;
			sLineWithJson = sExpression;
			hm = objExpressionSolver.computeHashMapFromJson(sLineWithJson);
			assertNotNull(hm);
			assertEquals(0,hm.size());//"dummy" ist kein gültiger JSON-Hashmpa String, darum kommt nix raus.
			
						
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
			btemp = objExpressionSolver.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION, true); 
			assertTrue("Flag nicht vorhanden '" + IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
			
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
			assertEquals(2,hm.size());
			
			//+++
			btemp = objExpressionSolver.setFlag(IKernelJsonIniSolverZZZ.FLAGZ.USEJSON, true); 
			assertTrue("Das Flag '"+ IKernelJsonIniSolverZZZ.FLAGZ.USEJSON +" sollte zur Verfügung stehen.", btemp);
			
			vecReturn = objExpressionSolver.parseFirstVector(sExpressionSource);
			assertFalse(StringZZZ.isEmpty(vecReturn.get(1))); //in der 0ten Position ist der String vor der Map, in der 3ten Position ist der String nach der Map.
			
			//ohne gueltigen Json-String wird keine HashMap erzeugt.
			sLineWithJson = vecReturn.get(1); 
			hm = objExpressionSolver.computeHashMapFromJson(sLineWithJson);
			assertNotNull(hm);
			assertEquals(2,hm.size());
									
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
	
	
	//############################################################
	/** void, Test: Reading an entry in a section of the ini-file
	* Lindhauer; 22.04.2006 12:54:32
	 */
	public void testCompute_JsonMap(){
		String sExpressionSource=null;
//		try {			
			sExpressionSource = KernelJsonMapIniSolverZZZTest.sEXPRESSION_JSONMAP01_DEFAULT;
			testCompute_JsonMap_(sExpressionSource);
//		} catch (ExceptionZZZ ez) {
//			fail("Method throws an exception." + ez.getMessageLast());
//		}
	
	}
	
	/**void, Test: Reading an entry in a section of the ini-file
	 * 
	 * @author Fritz Lindhauer, 05.05.2023, 08:54:30
	 */
	private void testCompute_JsonMap_(String sExpressionSourceIn){
		
		boolean btemp; int itemp;
		
		String sSection; String sProperty;
		String sExpressionSource; 
		String sExpressionSolved; String sExpressionSolvedTagless;
		IKernelConfigSectionEntryZZZ objEntry; ReferenceZZZ<IKernelConfigSectionEntryZZZ>objSectionEntryReference;
	
		String sValue;
		String sTagStartZ = "<Z>";
		String sTagEndZ = "</Z>";	
		
		try {		
					
			//+++++++ VORGEZOGENER LETZTER FEHLERTEST START
			
			//+++ Mit JsonMap-Berechnung
			
			//b) Werdem beim reinen Parsen die umgebenden Tags entfernt, dann wird auch das Tag des Solvers entfernt. Das wird naemlich auch durch Parsen "aufgeloest". 
			sExpressionSource = sExpressionSourceIn;			
			sExpressionSolved = "{\"UIText01\":\"TESTWERT2DO2JSON01\",\"UIText02\":\"TESTWERT2DO2JSON02\"}"; //Das ist der Wert von HashMap.toString/(; 
			//der eigene Tag wird ja auch beim Parsen entfernt. Also hier nicht als erwarteteten Wert aufnehmen... sExpressionSolved = objExpressionSolver.makeAsExpression(sExpressionSolved);
			sExpressionSolved = ExpressionIniUtilZZZ.makeAsExpression(sExpressionSolved, "JSON");//Da der JSON-Solver nicht hier getestet wird, bleibt dieser Tag drin.
			//der umgebende Z-Tag soll weg sein...  sExpressionSolved = ExpressionIniUtilZZZ.makeAsExpression(sExpressionSolved);
			
			//Variante, wenn man Tags Wegnehmen wuerde
//			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
//			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSolved, objExpressionSolver.getName(), false);
						
			btemp = testCompute_JsonMap_(sExpressionSource, sExpressionSolved, true, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);

			
			//+++++++ VORGEZOGENER LETZTER FEHLERTEST ENDE
			
			//###########################
		    //### objExpression
			//#########################
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//+++ Ohne jegliche Expression-Berechnung
			//a)
			sExpressionSource = sExpressionSourceIn;
			sExpressionSolved = sExpressionSource;
			btemp = testCompute_JsonMap_Unexpressed_(sExpressionSource, sExpressionSolved, false, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
			
			//b)
			sExpressionSource = sExpressionSourceIn;
			sExpressionSolved = sExpressionSource;			
			btemp = testCompute_JsonMap_Unexpressed_(sExpressionSource, sExpressionSolved, true, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
			
			//c)
			sExpressionSource = sExpressionSourceIn;
			sExpressionSolved = sExpressionSource; 			
			btemp = testCompute_JsonMap_Unexpressed_(sExpressionSource, sExpressionSolved, false, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
				
			//d)
			sExpressionSource = sExpressionSourceIn;
			sExpressionSolved = sExpressionSource;
			btemp = testCompute_JsonMap_Unexpressed_(sExpressionSource, sExpressionSolved, true, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
			
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//+++ Ohne jegliche Solver-Berechnung
			//a)
			sExpressionSource = sExpressionSourceIn;
			sExpressionSolved = sExpressionSource;
			btemp = testCompute_JsonMap_Unsolved_(sExpressionSource, sExpressionSolved, false, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
			
			//b)
			sExpressionSource = sExpressionSourceIn;
			sExpressionSolved = sExpressionSource;	//Beim Parsen werden, wenn wie hier gewuenscht immer der Z-Tag entfernt.
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ, false);
			btemp = testCompute_JsonMap_Unsolved_(sExpressionSource, sExpressionSolved, true, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
			
			//c)
			sExpressionSource = sExpressionSourceIn;
			sExpressionSolved = sExpressionSource; 			
			btemp = testCompute_JsonMap_Unsolved_(sExpressionSource, sExpressionSolved, false, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
				
			//d)
			sExpressionSource = sExpressionSourceIn;
			sExpressionSolved = sExpressionSource;	//Beim Parsen werden, wenn wie hier gewuenscht immer der Z-Tag entfernt.
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ, false);
			btemp = testCompute_JsonMap_Unsolved_(sExpressionSource, sExpressionSolved, true, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
			
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//+++ Ohne Json-Solver-Berechung
			//a)
			sExpressionSource = sExpressionSourceIn;
			sExpressionSolved = sExpressionSource;
			btemp = testCompute_JsonMap_JsonUnsolved_(sExpressionSource, sExpressionSolved, false, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
			
			//b)
			sExpressionSource = sExpressionSourceIn;
			sExpressionSolved = sExpressionSource;
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ, false);
			//Beim Parsen ohne Solver, bleibt sogar das Encryption-Tag drin, auch wenn sonst die Tags entfernt werden.
			btemp = testCompute_JsonMap_JsonUnsolved_(sExpressionSource, sExpressionSolved, true, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);

			//c)
			sExpressionSource = sExpressionSourceIn;
			sExpressionSolved = sExpressionSource;
			//Beim Solven ohne Solver, bleibt alles wie est ist.
			btemp = testCompute_JsonMap_JsonUnsolved_(sExpressionSource, sExpressionSolved, false, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
			
			//d)
			sExpressionSource = sExpressionSourceIn;
			sExpressionSolved = sExpressionSource;
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ, false);
			//Beim Solven ohne Solver, werden nur die äusseren Z-Tags ggfs. entfernt.
			btemp = testCompute_JsonMap_JsonUnsolved_(sExpressionSource, sExpressionSolved, true, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
			
			//+++ Ohne JsonMapSolver-Berechung
			//a)
			sExpressionSource = sExpressionSourceIn;
			sExpressionSolved = sExpressionSource;
			btemp = testCompute_JsonMap_JsonMapUnsolved_(sExpressionSource, sExpressionSolved, false, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
			
			//b)
			sExpressionSource = sExpressionSourceIn;
			sExpressionSolved = sExpressionSource; 
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ, false);
			//Beim Parsen ohne encryption, muss doch dieser encryption - Tag drinbleiben. Hier werden also nur die aeussern Z-Tags entfernt.
			btemp = testCompute_JsonMap_JsonMapUnsolved_(sExpressionSource, sExpressionSolved, true, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
			
			//c)
			sExpressionSource = sExpressionSourceIn;
			sExpressionSolved = sExpressionSource;
			//Beim Solven ohne encryption, bleibt alles an Tags drin.
			btemp = testCompute_JsonMap_JsonMapUnsolved_(sExpressionSource, sExpressionSolved, false, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
		
			//d)
			sExpressionSource = sExpressionSourceIn;
			sExpressionSolved = sExpressionSource;	
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ, false);
			//Beim Solven ohne encryption muss dieser encryption - Tag drinbleiben
			btemp = testCompute_JsonMap_JsonMapUnsolved_(sExpressionSource, sExpressionSolved, true, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
			
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//+++ Mit JsonMap-Berechnung
			//a) nur parsen bringt keinen Unterschied, wenn die Tags drinbleiben sollen
			sExpressionSource = sExpressionSourceIn;
			sExpressionSolved = "{\"UIText01\":\"TESTWERT2DO2JSON01\",\"UIText02\":\"TESTWERT2DO2JSON02\"}";
			sExpressionSolved = objExpressionSolver.makeAsExpression(sExpressionSolved);
			sExpressionSolved = ExpressionIniUtilZZZ.makeAsExpression(sExpressionSolved, "JSON");
			sExpressionSolved = ExpressionIniUtilZZZ.makeAsExpression(sExpressionSolved);
			
			//false: d.h. Tags sollen drin bleiben sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ, false);
			btemp = testCompute_JsonMap_(sExpressionSource, sExpressionSolved, false, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);

			//b) Werdem beim reinen Parsen die umgebenden Tags entfernt, dann wird auch das Encryption-Tag entfernt. Das wird naemlich auch durch Parsen "aufgeloest". Das eigentliche Aufloesen findet aber nicht statt.
			sExpressionSource = sExpressionSourceIn;			
			sExpressionSolved = "{\"UIText01\":\"TESTWERT2DO2JSON01\",\"UIText02\":\"TESTWERT2DO2JSON02\"}"; 
			//der eigene Tag wird ja auch beim Parsen entfernt. Also hier nicht als erwarteteten Wert aufnehmen... sExpressionSolved = objExpressionSolver.makeAsExpression(sExpressionSolved);
			sExpressionSolved = ExpressionIniUtilZZZ.makeAsExpression(sExpressionSolved, "JSON");
			//der umgebende Z-Tag soll weg sein...  sExpressionSolved = ExpressionIniUtilZZZ.makeAsExpression(sExpressionSolved);
			
			//Variante, wenn man Tags Wegnehmen wuerde
//			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
//			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSolved, objExpressionSolver.getName(), false);
						
			btemp = testCompute_JsonMap_(sExpressionSource, sExpressionSolved, true, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
			
			//c)
			sExpressionSource = sExpressionSourceIn;
			sExpressionSolved = "{UIText02=TESTWERT2DO2JSON02, UIText01=TESTWERT2DO2JSON01}"; //Das ist der Wert von HashMap.toString/(; //sExpressionSourceIn;			
			//der eigene Tag wird ja auch beim Parsen entfernt. Also hier nicht als erwarteteten Wert aufnehmen... sExpressionSolved = objExpressionSolver.makeAsExpression(sExpressionSolved);
			sExpressionSolved = ExpressionIniUtilZZZ.makeAsExpression(sExpressionSolved, "JSON");
			sExpressionSolved = ExpressionIniUtilZZZ.makeAsExpression(sExpressionSolved); //Z-Tags bleiben drin
						
			//Wichtig: JSON:MAP soll aus dem Ergebnis weg sein, wg. Aufloesen!!! Auch wenn die umgebenden Z-Tags drin bleiben.  
			btemp = testCompute_JsonMap_(sExpressionSource, sExpressionSolved, false, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
			
			//d)
			sExpressionSource = sExpressionSourceIn;
			sExpressionSolved = "{UIText02=TESTWERT2DO2JSON02, UIText01=TESTWERT2DO2JSON01}"; //Das ist der Wert von HashMap.toString/(; //sExpressionSourceIn;
			//der eigene Tag wird ja auch beim Parsen entfernt. Also hier nicht als erwarteteten Wert aufnehmen... sExpressionSolved = objExpressionSolver.makeAsExpression(sExpressionSolved);
			sExpressionSolved = ExpressionIniUtilZZZ.makeAsExpression(sExpressionSolved, "JSON");
			//Z-Tags sind raus sExpressionSolved = ExpressionIniUtilZZZ.makeAsExpression(sExpressionSolved);
			
			//Wichtig: Bis auf den umgebendn JSON-Tag (der Solver wird hier nicht verwendet) sind alle Tags raus.
			btemp = testCompute_JsonMap_(sExpressionSource, sExpressionSolved, true, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
							
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}		
	}
	
	
	private boolean testCompute_JsonMap_Unexpressed_(String sExpressionSourceIn, String sExpressionSolvedIn, boolean bRemoveSuroundingSeparators, IEnumSetMappedTestCaseZZZ objEnumTestCase) {
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
			
			
			//+++ ... parse ist nicht solve... also wird hier nichts aufgeloest, aussser die Pfade substituiert.
			if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.PARSE)) {
				sExpression = sExpressionSourceIn;
				sExpressionSolved = sExpressionSolvedIn;		
				objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
				sValue = objExpressionSolver.parse(sExpression, objSectionEntryReference, bRemoveSuroundingSeparators);
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
				vecValue = objExpressionSolver.parseFirstVector(sExpressionSolved, bRemoveSuroundingSeparators);					
				sValue = VectorUtilZZZ.implode(vecValue);
				assertEquals(sExpression, sValue); //Beim parse fist Vector wird nie der Z-Tag drum herum entfernt. Das ist Aufgabe von parse().
				
				sValue = (String) vecValue.get(1);//in der 0ten Position ist der String vor der Map, in der 3ten Position ist der String nach der Map.
				assertTrue(StringZZZ.contains(sExpressionSolved,sValue,false)); //da der Wert selbst nicht als Argument in der Methode uebergeben wurde, koennen wir nur auf Existenz im Gesamtergebnis pruefen.
			
				
				
				//+++ Nun die Gesamtberechnung durchführen
				sValue = objExpressionSolver.parse(sExpression, objSectionEntryReference,bRemoveSuroundingSeparators);
				assertEquals(sExpressionSolved, sValue);
							
				objEntry = objSectionEntryReference.get();
				assertNotNull(objEntry);
				
				assertTrue(objEntry.isParseCalled()); //Der Parse-Schritt wurde gemacht.
				assertFalse(objEntry.isParsedChanged()); //es wird ja nix gemacht, also immer "unveraendert"
				
				assertFalse(objEntry.isPathSubstituted());
				assertFalse(objEntry.isVariableSubstituted());
				
									
			
				assertFalse(objEntry.isSolveCalled());
				
				assertFalse(objEntry.isDecrypted());
				assertNull(objEntry.getValueDecrypted()); //Merke: sValue kann unterschiedlich zu dem decrypted Wert sein. Wenn etwas drumherum steht.
				
				assertFalse(objEntry.isCall());
				assertFalse(objEntry.isJavaCall());
				assertNull(objEntry.getCallingClassname());
				assertNull(objEntry.getCallingMethodname());
			}
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
				
			//+++ ... solve verhält sich NICHT wie parse(), bei solve wird aufgeloest...
			if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.SOLVE)) {
				sExpression = sExpressionSourceIn;
				sExpressionSolved = sExpressionSolvedIn;
				objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
				sValue = objExpressionSolver.solve(sExpression, objSectionEntryReference, bRemoveSuroundingSeparators);
				assertEquals(sExpressionSolved, sValue);
				
				objEntry = objSectionEntryReference.get();
				assertNotNull(objEntry);
				
				assertFalse(objEntry.isParseCalled()); //Der Parse-Schritt wurde NICHT gemacht.
				assertFalse(objEntry.isParsedChanged()); //es wird ja nix gemacht, also immer "unveraendert"
				
				assertFalse(objEntry.isPathSubstituted());//es wird ja nix gemacht, also immer "unveraendert"
				assertFalse(objEntry.isVariableSubstituted());//es wird ja nix gemacht, also immer "unveraendert"
									
			
				assertTrue(objEntry.isSolveCalled());//es wird ja nix gemacht, also immer "unveraendert"
				assertFalse(objEntry.isSolvedChanged());		
				
				assertFalse(objEntry.isDecrypted());
				assertNull(objEntry.getValueDecrypted()); //Merke: sValue kann unterschiedlich zu dem decrypted Wert sein. Wenn etwas drumherum steht.
								
				assertFalse(objEntry.isCall());
				assertFalse(objEntry.isJavaCall());
				assertNull(objEntry.getCallingClassname());
				assertNull(objEntry.getCallingMethodname());
			}
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			
			//+++ Variante fuer den AsEntry-Test
			if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.PARSE_AS_ENTRY)) {
				sExpression = sExpressionSourceIn;
				sExpressionSolved = sExpressionSolvedIn;
				objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
				objEntryUsed = objExpressionSolver.parseAsEntry(sExpression, bRemoveSuroundingSeparators);				
				assertNotNull(objEntryUsed);
							
				assertTrue(objEntryUsed.isParseCalled()); //Der Parse-Schritt wurde gemacht.
				assertFalse(objEntryUsed.isPathSubstituted());
				assertFalse(objEntryUsed.isVariableSubstituted());
				
				assertFalse(objEntryUsed.isParsedChanged()); //es wird ja nix gemacht, also immer "unveraendert"						
			
				assertFalse(objEntryUsed.isSolveCalled()); //es ist auch kein Solver involviert
								
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
				sValue = objExpressionSolver.solve(sExpression, objSectionEntryReference, bRemoveSuroundingSeparators);
				assertEquals(sExpressionSolved, sValue);
				
				objEntryUsed = objSectionEntryReference.get();
				assertNotNull(objEntryUsed);
				
				assertTrue(objEntryUsed.isParseCalled()); //Der Parse-Schritt wurde gemacht.
				assertFalse(objEntryUsed.isPathSubstituted());
				assertFalse(objEntryUsed.isVariableSubstituted());

				assertFalse(objEntryUsed.isParsedChanged()); //es wird ja nix gemacht, also immer "unveraendert"						
					
				assertFalse(objEntryUsed.isSolveCalled());
								
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
	
	private boolean testCompute_JsonMap_Unsolved_(String sExpressionSourceIn, String sExpressionSolvedIn, boolean bRemoveSuroundingSeparators, IEnumSetMappedTestCaseZZZ objEnumTestCase) {
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
				vecValue = objExpressionSolver.parseFirstVector(sExpression, bRemoveSuroundingSeparators);					
				sValue = VectorUtilZZZ.implode(vecValue);
				assertEquals(sExpression, sValue); //Beim parse fist Vector wird nie der Z-Tag drum herum entfernt. Das ist Aufgabe von parse().
				
				sValue = (String) vecValue.get(1);//in der 0ten Position ist der String vor der Map, in der 3ten Position ist der String nach der Map.
				assertTrue(StringZZZ.contains(sExpressionSolved,sValue,false)); //da der Wert selbst nicht als Argument in der Methode uebergeben wurde, koennen wir nur auf Existenz im Gesamtergebnis pruefen.
			
				
				//+++ Nun die Gesamtberechnung durchführen
				objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
				sValue = objExpressionSolver.parse(sExpression, objSectionEntryReference, bRemoveSuroundingSeparators);
				assertEquals(sExpressionSolved, sValue);
							
				objEntry = objSectionEntryReference.get();
				assertNotNull(objEntry);
				
				assertTrue(objEntry.isParseCalled()); //Der Parse-Schritt wurde gemacht.
				if(bRemoveSuroundingSeparators) {
					assertTrue(objEntry.isParsedChanged()); //es werden ja die Z-Tags drumherum entfernt also "veraendert"
				}else {
					assertFalse(objEntry.isParsedChanged()); //es werden ja die Z-Tags drumherum NICHT entfernt also "veraendert"
				}
				
				assertFalse(objEntry.isPathSubstituted());
				assertFalse(objEntry.isVariableSubstituted());
													
				assertFalse(objEntry.isSolveCalled());
				
				assertTrue(objEntry.isJson());
				assertFalse(objEntry.isJsonArray());
				assertTrue(objEntry.isJsonMap());
				
				assertFalse(objEntry.isDecrypted());
				assertNull(objEntry.getValueDecrypted()); //Merke: sValue kann unterschiedlich zu dem decrypted Wert sein. Wenn etwas drumherum steht.
				
				assertFalse(objEntry.isCall());
				assertFalse(objEntry.isJavaCall());
				assertNull(objEntry.getCallingClassname());
				assertNull(objEntry.getCallingMethodname());
			}
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
				
			//+++ ... solve verhält sich NICHT wie parse(), bei solve wird aufgeloest...
			if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.SOLVE)) {
				sExpression = sExpressionSourceIn;
				sExpressionSolved = sExpressionSolvedIn;
				objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
				sValue = objExpressionSolver.solve(sExpression, objSectionEntryReference, bRemoveSuroundingSeparators);
				assertEquals(sExpressionSolved, sValue);
				
				objEntry = objSectionEntryReference.get();
				assertNotNull(objEntry);
				
				assertTrue(objEntry.isParseCalled()); //Der Parse-Schritt wurde gemacht.
				if(bRemoveSuroundingSeparators) {
					assertTrue(objEntry.isParsedChanged()); //es werden ja die Z-Tags drumherum entfernt also "veraendert"
				}else {
					assertFalse(objEntry.isParsedChanged()); //es werden ja die Z-Tags drumherum NICHT entfernt also "veraendert"
				}

				
				assertFalse(objEntry.isPathSubstituted());
				assertFalse(objEntry.isVariableSubstituted());
				
				assertTrue(objEntry.isSolveCalled()); //.solve() wird ja ausgefuert, s. .parse()
				assertFalse(objEntry.isSolvedChanged()); //ist ja nix wirklich aufgeloest
				
				assertTrue(objEntry.isJson());
				assertFalse(objEntry.isJsonArray());
				assertTrue(objEntry.isJsonMap());
								
				assertFalse(objEntry.isDecrypted());
				assertNull(objEntry.getValueDecrypted()); //Merke: sValue kann unterschiedlich zu dem decrypted Wert sein. Wenn etwas drumherum steht.
								
				assertFalse(objEntry.isCall());
				assertFalse(objEntry.isJavaCall());
				assertNull(objEntry.getCallingClassname());
				assertNull(objEntry.getCallingMethodname());
			}
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			
			//+++ Variante fuer den AsEntry-Test
			if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.PARSE_AS_ENTRY)) {
				sExpression = sExpressionSourceIn;
				sExpressionSolved = sExpressionSolvedIn;
				objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
				objEntryUsed = objExpressionSolver.parseAsEntry(sExpression, bRemoveSuroundingSeparators);				
				assertNotNull(objEntryUsed);
							
				assertTrue(objEntryUsed.isParseCalled()); //Der Parse-Schritt wurde gemacht.
				if(bRemoveSuroundingSeparators) {
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
				assertFalse(objEntryUsed.isJsonArray());
				assertTrue(objEntryUsed.isJsonMap());
				
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
				sValue = objExpressionSolver.solve(sExpression, objSectionEntryReference, bRemoveSuroundingSeparators);
				assertEquals(sExpressionSolved, sValue);
				
				objEntryUsed = objSectionEntryReference.get();
				assertNotNull(objEntryUsed);
				
				assertTrue(objEntryUsed.isParseCalled()); //Der Parse-Schritt wurde gemacht.
				if(bRemoveSuroundingSeparators) {
					assertTrue(objEntryUsed.isParsedChanged()); //es werden ja die Z-Tags drumherum entfernt also "veraendert"
				}else {
					assertFalse(objEntryUsed.isParsedChanged()); //es werden ja die Z-Tags drumherum NICHT entfernt also "veraendert"
				}

				
				assertFalse(objEntryUsed.isPathSubstituted());
				assertFalse(objEntryUsed.isVariableSubstituted());
	
				assertTrue(objEntryUsed.isSolveCalled()); //.solve() wird ja ausgefuert, s. .parse()
				assertFalse(objEntryUsed.isSolvedChanged()); //ist ja nix wirklich aufgeloest
				
				assertTrue(objEntryUsed.isSolveCalled());
				assertTrue(objEntryUsed.isSolvedChanged());		
				
				assertTrue(objEntryUsed.isJson());
				assertFalse(objEntryUsed.isJsonArray());
				assertTrue(objEntryUsed.isJsonMap());
								
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
	
	private boolean testCompute_JsonMap_JsonUnsolved_(String sExpressionSourceIn, String sExpressionSolvedIn, boolean bRemoveSuroundingSeparators, IEnumSetMappedTestCaseZZZ objEnumTestCase) {
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
				vecValue = objExpressionSolver.parseFirstVector(sExpression, bRemoveSuroundingSeparators);					
				sValue = VectorUtilZZZ.implode(vecValue);
				assertEquals(sExpression, sValue); //Beim parse fist Vector wird nie der Z-Tag drum herum entfernt. Das ist Aufgabe von parse().
				
				sValue = (String) vecValue.get(1);//in der 0ten Position ist der String vor der Map, in der 3ten Position ist der String nach der Map.
				assertTrue(StringZZZ.contains(sExpressionSolved,sValue,false)); //da der Wert selbst nicht als Argument in der Methode uebergeben wurde, koennen wir nur auf Existenz im Gesamtergebnis pruefen.
			
			
				
				
				//+++ Nun die Gesamtberechnung durchführen
				objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
				sValue = objExpressionSolver.parse(sExpression, objSectionEntryReference, bRemoveSuroundingSeparators);
				assertEquals(sExpressionSolved, sValue);
							
				objEntry = objSectionEntryReference.get();
				assertNotNull(objEntry);
				
				assertTrue(objEntry.isParseCalled()); //Der Parse-Schritt wurde gemacht.
				if(bRemoveSuroundingSeparators) {
					assertTrue(objEntry.isParsedChanged()); //es werden ja die Z-Tags drumherum entfernt also "veraendert"
				}else {
					assertFalse(objEntry.isParsedChanged()); //es werden ja die Z-Tags drumherum NICHT entfernt also "veraendert"
				}
				
				assertFalse(objEntry.isPathSubstituted());
				assertFalse(objEntry.isVariableSubstituted());
				
				assertFalse(objEntry.isSolveCalled());
				
				assertFalse(objEntry.isDecrypted());
				assertNull(objEntry.getValueDecrypted()); //Merke: sValue kann unterschiedlich zu dem decrypted Wert sein. Wenn etwas drumherum steht.
				
				assertFalse(objEntry.isCall());
				assertFalse(objEntry.isJavaCall());
				assertNull(objEntry.getCallingClassname());
				assertNull(objEntry.getCallingMethodname());
			}
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
				
			//+++ ... solve verhält sich NICHT wie parse(), bei solve wird aufgeloest...
			if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.SOLVE)) {
				sExpression = sExpressionSourceIn;
				sExpressionSolved = sExpressionSolvedIn;
				objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
				sValue = objExpressionSolver.solve(sExpression, objSectionEntryReference, bRemoveSuroundingSeparators);
				assertEquals(sExpressionSolved, sValue);
				
				objEntry = objSectionEntryReference.get();
				assertNotNull(objEntry);
				
				assertTrue(objEntry.isParseCalled()); //Der Parse-Schritt wurde gemacht.
				if(bRemoveSuroundingSeparators) {
					assertTrue(objEntry.isParsedChanged()); //es werden ja die Z-Tags drumherum entfernt also "veraendert"
				}else {
					assertFalse(objEntry.isParsedChanged()); //es werden ja die Z-Tags drumherum NICHT entfernt also "veraendert"
				}
				
				assertFalse(objEntry.isPathSubstituted());
				assertFalse(objEntry.isVariableSubstituted());
				
				assertTrue(objEntry.isSolveCalled()); //wird ja ausgefürht
				//assertTrue(objEntry.isSolvedChanged());				
				
				assertFalse(objEntry.isDecrypted());
				assertNull(objEntry.getValueDecrypted()); //Merke: sValue kann unterschiedlich zu dem decrypted Wert sein. Wenn etwas drumherum steht.
								
				assertFalse(objEntry.isCall());
				assertFalse(objEntry.isJavaCall());
				assertNull(objEntry.getCallingClassname());
				assertNull(objEntry.getCallingMethodname());
			}
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			
			//+++ Variante fuer den AsEntry-Test
			if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.PARSE_AS_ENTRY)) {
				sExpression = sExpressionSourceIn;
				sExpressionSolved = sExpressionSolvedIn;
				objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
				objEntryUsed = objExpressionSolver.parseAsEntry(sExpression, bRemoveSuroundingSeparators);				
				assertNotNull(objEntryUsed);
							
				assertTrue(objEntryUsed.isParseCalled()); //Der Parse-Schritt wurde gemacht.
				if(bRemoveSuroundingSeparators) {
					assertTrue(objEntryUsed.isParsedChanged()); //es werden ja die Z-Tags drumherum entfernt also "veraendert"
				}else {
					assertFalse(objEntryUsed.isParsedChanged()); //es werden ja die Z-Tags drumherum NICHT entfernt also "veraendert"
				}
				
				assertFalse(objEntryUsed.isPathSubstituted());
				assertFalse(objEntryUsed.isVariableSubstituted());
				
				assertFalse(objEntryUsed.isSolveCalled()); //es ist auch kein Solver involviert
								
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
				objEntryUsed = objExpressionSolver.solveAsEntry(sExpression, bRemoveSuroundingSeparators);
				assertNotNull(objEntryUsed);
				
				
				sValue = objEntryUsed.getValue();
				assertEquals(sExpressionSolved, sValue);
				
				
				
				assertTrue(objEntryUsed.isParseCalled()); //Der Parse-Schritt wurde gemacht.
				if(bRemoveSuroundingSeparators) {
					assertTrue(objEntryUsed.isParsedChanged()); //es werden ja die Z-Tags drumherum entfernt also "veraendert"
				}else {
					assertFalse(objEntryUsed.isParsedChanged()); //es werden ja die Z-Tags drumherum NICHT entfernt also "veraendert"
				}
				
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
	
	
	private boolean testCompute_JsonMap_JsonMapUnsolved_(String sExpressionSourceIn, String sExpressionSolvedIn, boolean bRemoveSuroundingSeparators, IEnumSetMappedTestCaseZZZ objEnumTestCase) {
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
				vecValue = objExpressionSolver.parseFirstVector(sExpression, bRemoveSuroundingSeparators);					
				sValue = VectorUtilZZZ.implode(vecValue);
				assertEquals(sExpression, sValue); //Beim parse fist Vector wird nie der Z-Tag drum herum entfernt. Das ist Aufgabe von parse().
				
				sValue = (String) vecValue.get(1);//in der 0ten Position ist der String vor der Map, in der 3ten Position ist der String nach der Map.
				assertTrue(StringZZZ.contains(sExpressionSolved,sValue,false)); //da der Wert selbst nicht als Argument in der Methode uebergeben wurde, koennen wir nur auf Existenz im Gesamtergebnis pruefen.

				
				//+++ Nun die Gesamtberechnung durchführen
				objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
				sValue = objExpressionSolver.parse(sExpression, objSectionEntryReference, bRemoveSuroundingSeparators);
				assertEquals(sExpressionSolved, sValue);
							
				objEntry = objSectionEntryReference.get();
				assertNotNull(objEntry);
				
				assertTrue(objEntry.isParseCalled()); //Der Parse-Schritt wurde gemacht.
				if(bRemoveSuroundingSeparators) {
					assertTrue(objEntry.isParsedChanged()); //es werden ja die Z-Tags drumherum entfernt also "veraendert"
				}else {
					assertFalse(objEntry.isParsedChanged()); //es werden ja die Z-Tags drumherum NICHT entfernt also "veraendert"
				}
				
				assertFalse(objEntry.isPathSubstituted());
				assertFalse(objEntry.isVariableSubstituted());
			
				assertFalse(objEntry.isSolveCalled());
				
				assertFalse(objEntry.isDecrypted());
				assertNull(objEntry.getValueDecrypted()); //Merke: sValue kann unterschiedlich zu dem decrypted Wert sein. Wenn etwas drumherum steht.
				
				assertFalse(objEntry.isCall());
				assertFalse(objEntry.isJavaCall());
				assertNull(objEntry.getCallingClassname());
				assertNull(objEntry.getCallingMethodname());
			}
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
				
			//+++ ... solve verhält sich NICHT wie parse(), bei solve wird aufgeloest...
			if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.SOLVE)) {
				sExpression = sExpressionSourceIn;
				sExpressionSolved = sExpressionSolvedIn;
				objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
				sValue = objExpressionSolver.solve(sExpression, objSectionEntryReference, bRemoveSuroundingSeparators);
				assertEquals(sExpressionSolved, sValue);
				
				objEntry = objSectionEntryReference.get();
				assertNotNull(objEntry);
				
				assertTrue(objEntry.isParseCalled()); //Der Parse-Schritt wurde gemacht.
				if(bRemoveSuroundingSeparators) {
					assertTrue(objEntry.isParsedChanged()); //es werden ja die Z-Tags drumherum entfernt also "veraendert"
				}else {
					assertFalse(objEntry.isParsedChanged()); //es werden ja die Z-Tags drumherum NICHT entfernt also "veraendert"
				}
				
				assertFalse(objEntry.isPathSubstituted());
				assertFalse(objEntry.isVariableSubstituted());
				
										
			
				assertTrue(objEntry.isSolveCalled());
				//assertTrue(objEntry.isSolvedChanged());
												
				assertFalse(objEntry.isDecrypted());
				assertNull(objEntry.getValueDecrypted()); //Merke: sValue kann unterschiedlich zu dem decrypted Wert sein. Wenn etwas drumherum steht.
								
				assertFalse(objEntry.isCall());
				assertFalse(objEntry.isJavaCall());
				assertNull(objEntry.getCallingClassname());
				assertNull(objEntry.getCallingMethodname());
			}
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			
			//+++ Variante fuer den AsEntry-Test
			if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.PARSE_AS_ENTRY)) {
				sExpression = sExpressionSourceIn;
				sExpressionSolved = sExpressionSolvedIn;
				objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
				objEntryUsed = objExpressionSolver.parseAsEntry(sExpression, bRemoveSuroundingSeparators);				
				assertNotNull(objEntryUsed);
							
				assertTrue(objEntryUsed.isParseCalled()); //Der Parse-Schritt wurde gemacht.
				if(bRemoveSuroundingSeparators) {
					assertTrue(objEntryUsed.isParsedChanged()); //es werden ja die Z-Tags drumherum entfernt also "veraendert"
				}else {
					assertFalse(objEntryUsed.isParsedChanged()); //es werden ja die Z-Tags drumherum NICHT entfernt also "veraendert"
				}
				
				assertFalse(objEntryUsed.isPathSubstituted());
				assertFalse(objEntryUsed.isVariableSubstituted());
				
				assertFalse(objEntryUsed.isSolveCalled()); //es ist auch kein Solver involviert
								
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
				objEntryUsed = objExpressionSolver.solveAsEntry(sExpression, bRemoveSuroundingSeparators);
				assertNotNull(objEntryUsed);
				
				sValue = objEntryUsed.getValue();
				assertEquals(sExpressionSolved, sValue);				
				
				assertTrue(objEntryUsed.isParseCalled()); //Der Parse-Schritt wurde gemacht.
				if(bRemoveSuroundingSeparators) {
					assertTrue(objEntryUsed.isParsedChanged()); //es werden ja die Z-Tags drumherum entfernt also "veraendert"
				}else {
					assertFalse(objEntryUsed.isParsedChanged()); //es werden ja die Z-Tags drumherum NICHT entfernt also "veraendert"
				}
				
				assertFalse(objEntryUsed.isPathSubstituted());
				assertFalse(objEntryUsed.isVariableSubstituted());

					
				assertFalse(objEntryUsed.isSolveCalled());
								
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

	private boolean testCompute_JsonMap_(String sExpressionSourceIn, String sExpressionSolvedIn, boolean bRemoveSuroundingSeparators, IEnumSetMappedTestCaseZZZ objEnumTestCase) {
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
				vecValue = objExpressionSolver.parseFirstVector(sExpression, bRemoveSuroundingSeparators);					
				sValue = VectorUtilZZZ.implode(vecValue);

				String sExpressionSolvedTemp = ExpressionIniUtilZZZ.makeAsExpression(sExpressionSolved);//Beim parse fist Vector wird nie der Z-Tag drum herum entfernt. Das ist Aufgabe von parse().
				//sExpressionSolvedTemp = ExpressionIniUtilZZZ.makeAsExpression(sExpressionSolvedTemp, "JSON");
				//sExpressionSolvedTemp = objExpressionSolver.makeAsExpression(sExpressionSolvedTemp);
				assertEquals(sExpressionSolvedTemp, sValue); 
				
				sValue = (String) vecValue.get(1);//in der 0ten Position ist der String vor der Map, in der 3ten Position ist der String nach der Map.
				assertTrue(StringZZZ.contains(sExpressionSolved,sValue,false)); //da der Wert selbst nicht als Argument in der Methode uebergeben wurde, koennen wir nur auf Existenz im Gesamtergebnis pruefen.
				
				//+++ Nun die Gesamtberechnung durchführen
				objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
				sValue = objExpressionSolver.parse(sExpression, objSectionEntryReference, bRemoveSuroundingSeparators);
				assertEquals(sExpressionSolved, sValue);
							
				objEntry = objSectionEntryReference.get();
				assertNotNull(objEntry);
				
				assertTrue(objEntry.isParseCalled()); //Der Parse-Schritt wurde gemacht.
				if(bRemoveSuroundingSeparators) {
					assertTrue(objEntry.isParsedChanged()); //es werden ja die Z-Tags drumherum entfernt also "veraendert"
				}else {
					assertFalse(objEntry.isParsedChanged()); //es werden ja die Z-Tags drumherum NICHT entfernt also "veraendert"
				}
			
				assertFalse(objEntry.isPathSubstituted());
				assertFalse(objEntry.isVariableSubstituted());
				
				assertFalse(objEntry.isSolveCalled());
				
				assertFalse(objEntry.isDecrypted());
				assertNull(objEntry.getValueDecrypted()); //Merke: sValue kann unterschiedlich zu dem decrypted Wert sein. Wenn etwas drumherum steht.
				
				assertFalse(objEntry.isCall());
				assertFalse(objEntry.isJavaCall());
				assertNull(objEntry.getCallingClassname());
				assertNull(objEntry.getCallingMethodname());
			}
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
				
			//+++ ... solve verhält sich NICHT wie parse(), bei solve wird aufgeloest...
			if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.SOLVE)) {
				sExpression = sExpressionSourceIn;
				sExpressionSolved = sExpressionSolvedIn;
				objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
				sValue = objExpressionSolver.solve(sExpression, objSectionEntryReference, bRemoveSuroundingSeparators);
				assertEquals(sExpressionSolved, sValue);
				
				objEntry = objSectionEntryReference.get();
				assertNotNull(objEntry);
				
				assertTrue(objEntry.isParseCalled()); //Der Parse-Schritt wurde gemacht.
				if(bRemoveSuroundingSeparators) {
					assertTrue(objEntry.isParsedChanged()); //es werden ja die Z-Tags drumherum entfernt also "veraendert"
				}else {
					assertFalse(objEntry.isParsedChanged()); //es werden ja die Z-Tags drumherum NICHT entfernt also "veraendert"
				}
				
				assertFalse(objEntry.isPathSubstituted());
				assertFalse(objEntry.isVariableSubstituted());
				
				assertTrue(objEntry.isSolveCalled());
				//assertTrue(objEntry.isSolvedChanged());
								
				assertFalse(objEntry.isDecrypted());
				assertNull(objEntry.getValueDecrypted()); //Merke: sValue kann unterschiedlich zu dem decrypted Wert sein. Wenn etwas drumherum steht.
								
				assertFalse(objEntry.isCall());
				assertFalse(objEntry.isJavaCall());
				assertNull(objEntry.getCallingClassname());
				assertNull(objEntry.getCallingMethodname());
			}
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			
			//+++ Variante fuer den AsEntry-Test
			if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.PARSE_AS_ENTRY)) {
				sExpression = sExpressionSourceIn;
				sExpressionSolved = sExpressionSolvedIn;
				objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
				objEntryUsed = objExpressionSolver.parseAsEntry(sExpression, bRemoveSuroundingSeparators);				
				assertNotNull(objEntryUsed);
				
				sValue = objEntryUsed.getValue();
				assertEquals(sExpressionSolved, sValue);
							
				assertTrue(objEntryUsed.isParseCalled()); //Der Parse-Schritt wurde gemacht.
				if(bRemoveSuroundingSeparators) {
					assertTrue(objEntryUsed.isParsedChanged()); //es werden ja die Z-Tags drumherum entfernt also "veraendert"
				}else {
					assertFalse(objEntryUsed.isParsedChanged()); //es werden ja die Z-Tags drumherum NICHT entfernt also "veraendert"
				}
				
				assertFalse(objEntryUsed.isPathSubstituted());
				assertFalse(objEntryUsed.isVariableSubstituted());													
			
				assertFalse(objEntryUsed.isSolveCalled()); //es ist auch kein Solver involviert
								
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
				objEntryUsed = objExpressionSolver.solveAsEntry(sExpression, bRemoveSuroundingSeparators);
				assertNotNull(objEntryUsed);
				
				sValue = objEntryUsed.getValue();
				assertEquals(sExpressionSolved, sValue);
				
				assertTrue(objEntryUsed.isParseCalled()); //Der Parse-Schritt wurde gemacht.
				if(bRemoveSuroundingSeparators) {
					assertTrue(objEntryUsed.isParsedChanged()); //es werden ja die Z-Tags drumherum entfernt also "veraendert"
				}else {
					assertFalse(objEntryUsed.isParsedChanged()); //es werden ja die Z-Tags drumherum NICHT entfernt also "veraendert"
				}
				//assertTrue(objEntryUsed.isParsedChanged()); //Beim Aufloesen werden die Z-Tags des Solvers entfernt also "veraendert"
				
				assertFalse(objEntryUsed.isPathSubstituted());
				assertFalse(objEntryUsed.isVariableSubstituted());
					
				assertFalse(objEntryUsed.isSolveCalled());
								
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
	
