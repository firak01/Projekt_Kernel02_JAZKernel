package basic.zKernel.file.ini;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IObjectWithExpressionZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedTestCaseZZZ;
import basic.zBasic.util.abstractList.HashMapCaseInsensitiveZZZ;
import basic.zBasic.util.abstractList.Vector3ZZZ;
import basic.zBasic.util.abstractList.VectorUtilZZZ;
import basic.zBasic.util.crypt.code.ICharacterPoolUserZZZ;
import basic.zBasic.util.crypt.code.IROTUserZZZ;
import basic.zBasic.util.crypt.code.ROT13ZZZ;
import basic.zBasic.util.crypt.code.ROTnnZZZ;
import basic.zBasic.util.crypt.code.ROTnumericZZZ;
import basic.zBasic.util.datatype.calling.ReferenceZZZ;
import basic.zBasic.util.datatype.character.CharacterExtendedZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.file.FileEasyZZZ;
import basic.zBasic.util.stream.IStreamZZZ;
import basic.zBasic.util.stream.StreamZZZ;
import basic.zKernel.IKernelConfigSectionEntryZZZ;
import basic.zKernel.KernelZZZ;
import basic.zKernel.config.KernelConfigSectionEntryUtilZZZ;
import custom.zKernel.file.ini.FileIniZZZ;
import junit.framework.TestCase;

public class KernelZFormulaIniSolverZZZTest extends TestCase {
	protected final static String sEXPRESSION_FORMULA_MATH_SOURCE01_CONTENT = "<Z:math><Z:val>2</Z:val><Z:op>*</Z:op><Z:val>3</Z:val></Z:math>";
	protected final static String sEXPRESSION_FORMULA_MATH_SOURCE01 = "Der dynamische Wert ist '<Z>"+ KernelZFormulaIniSolverZZZTest.sEXPRESSION_FORMULA_MATH_SOURCE01_CONTENT +"</Z>'. FGL rulez.";
	
	protected final static String sEXPRESSION_FORMULA_MATH_SOLVED01_CONTENT = "6";
	protected final static String sEXPRESSION_FORMULA_MATH_SOLVED01 = "Der dynamische Wert ist '"+ KernelZFormulaIniSolverZZZTest.sEXPRESSION_FORMULA_MATH_SOLVED01_CONTENT +"'. FGL rulez.";	
	
	private File objFile;
	private FileIniZZZ objFileIniInit;
	private FileIniZZZ objFileIniTest;
	private KernelZZZ objKernel;
	
	/// +++ Die eigentlichen Test-Objekte	
	private KernelZFormulaIniSolverZZZ objFormulaSolver;
	private KernelZFormulaIniSolverZZZ objFormulaSolverInit;
	
	
	protected void setUp(){
		try {			
			
			
			
			
//			//### Eine Beispieldatei. Merke: Die Einträge werden immer neu geschrieben und nicht etwa angehngt.
//			//Merke: Erst wenn es überhaupt einen Test gibt, wird diese Datei erstellt
//			String sFilePathTotal = null;		
//			if(FileEasyZZZ.exists(strFILE_DIRECTORY_DEFAULT)){
//				sFilePathTotal = FileEasyZZZ.joinFilePathName(strFILE_DIRECTORY_DEFAULT, strFILE_NAME_DEFAULT );
//			}else{
//				//Eclipse Worspace
//				File f = new File("");
//			    String sPathEclipse = f.getAbsolutePath();
//			    System.out.println("Path for Kernel Directory Default does not exist. Using workspace absolut path: " + sPathEclipse);
//				
//			    sFilePathTotal = FileEasyZZZ.joinFilePathName(sPathEclipse, strFILE_NAME_DEFAULT );				
//			}
//			
//			IStreamZZZ objStreamFile = new StreamZZZ(sFilePathTotal, 1);  //This is not enough, to create the file			
//			objStreamFile.println(";This is a temporarily test file for FileIniZZZTest.");      //Now the File is created. This is a comment line
//			objStreamFile.println(";This file will be newly created by the setUp()-method of this JUnit Test class, every time before a testMethod will be invoked.");
//			objStreamFile.println("#This is another commentline");
//			objStreamFile.println("[Section A]");
//			objStreamFile.println("Testentry1=Testvalue1 to be found");			
//			objStreamFile.println("TestentryDymmy=nothing");
//						
//			objStreamFile.println("[Section B!01]");
//			objStreamFile.println("Testentry2=Testvalue2 local to be found");
//			
//			objStreamFile.println("[Section B]");
//			objStreamFile.println("Testentry2=Testvalue2 global. This should not be found");
//			
//			objStreamFile.println("[Section for testCompute]");
//			objStreamFile.println("Formula1=Der dynamische Wert ist '<Z>[Section A]Testentry1</Z>'. FGL rulez.");
//			objStreamFile.println("Formula2=Der dynamische Wert2 ist '<Z>[Section B]Testentry2</Z>'. FGL rulez.");
//			
//			objStreamFile.println("[Section for testComputeMathArguments]");
//			objStreamFile.println("WertA=4");
//			objStreamFile.println("WertB=5");
//			
//			objStreamFile.println("[Section for testComputeMathValue]");
//			objStreamFile.println("Formula1=Der dynamische Wert ist '<Z><Z:math><Z:val>2</Z:val><Z:val>3</Z:val></Z:math></Z>'. FGL rulez.");
//			objStreamFile.println("Formula2=Der dynamische Wert2 ist '<Z><Z:math><Z:val>2</Z:val><Z:op>*</Z:op><Z:val>[Section for testComputeMathArguments]WertA</Z:val></Z:math></Z>'. FGL rulez.");		
//			
//			objStreamFile.println("[Section for testComputeMath]");
//			objStreamFile.println("Formula1=Der dynamische Wert ist '<Z><Z:math><Z:val>2</Z:val><Z:op>*</Z:op><Z:val>3</Z:val></Z:math></Z>'. FGL rulez.");
//			objStreamFile.println("Formula2=Der dynamische Wert2 ist '<Z><Z:math><Z:val>2</Z:val><Z:op>*</Z:op><Z:val>[Section for testComputeMathArguments]WertA</Z:val></Z:math></Z>'. FGL rulez.");
//			objStreamFile.println("Formula3=Der dynamische Wert3 ist '<Z><Z:math><Z:val>[Section for testComputeMathArguments]WertB</Z:val><Z:op>*</Z:op><Z:val>[Section for testComputeMathArguments]WertA</Z:val></Z:math></Z>'. FGL rulez.");
//			 
//			
//			objStreamFile.println("[Section for testComputePathWithMath]");
//			objStreamFile.println("Formula1=<Z>[Section for testComputeMath]Formula1</Z>");
//			objStreamFile.println("Formula2=<Z>[Section for testComputeMath]Formula2</Z>");
//			objStreamFile.println("Formula3=<Z>[Section for testComputeMath]Formula3</Z>");
//			
//			objStreamFile.println("[Section for testComputeMath NOT EXACTMATCH]");
//			objStreamFile.println("Formula1=Der dynamische Wert ist '<Z><z:Math><Z:VAL>6</Z:val><Z:oP>+</Z:op><Z:val>7</Z:val></Z:math></Z>'. FGL rulez.");
//			 
//			
//			objStreamFile.println("[Section for testComputeMathArguments FLOAT]");
//			objStreamFile.println("WertA_float=4.0");
//			objStreamFile.println("WertB_float=5.0");
//			objStreamFile.println("[Section for testComputeMath FLOAT]");
//			objStreamFile.println("Formula1=Der dynamische Wert ist '<Z><z:Math><Z:VAL>[Section for testComputeMathArguments FLOAT]WertA_float</Z:val><Z:oP>*</Z:op><Z:val>[Section for testComputeMathArguments FLOAT]WertB_float</Z:val></Z:math></Z>'. FGL rulez.");
//			
//			objStreamFile.println("[Section for testComputeMathVariable FLOAT]");
//			objStreamFile.println("WertB_float=<Z><z:Var>myTestVariableFloat</z:Var></Z>");
//			
//			//Beachte Variablen können wie INI-Path auch außßerhalb einer MATH - Anweisung gelten.
//			objStreamFile.println("[Section for testPassVariable]");
//			objStreamFile.println("Formula1=<Z>Der dynamische Wert ist '<z:Var>myTestVariableString</z:Var>'. FGL rulez.</Z>");
//			objStreamFile.println("Formula2=Der dynamische Wert ist '<Z><z:Math><Z:VAL>[Section for testComputeMathArguments FLOAT]WertA_float</Z:val><Z:oP>*</Z:op><Z:val><Z:Var>myTestVariableFloat</z:Var></Z:val></Z:math></Z>'. FGL rulez.");
//			objStreamFile.println("Formula3=Der dynamische Wert ist '<Z><z:Math><Z:VAL>[Section for testComputeMathArguments FLOAT]WertA_float</Z:val><Z:oP>*</Z:op><Z:val>[Section for testComputeMathVariable FLOAT]WertB_float</Z:val></Z:math></Z>'. FGL rulez.");
//			
//			
//			//20210707 Tests für die Arbeit mit JSON Strings
//			//Merke:			
//			//Gib den JSON-Hashmap-Wert so an: {"DEBUGUI_PANELLABEL_ON":true} Merke: Intern hier eine HashMap String, Boolean Das ist aber nur sinnvoll bei der FLAG übergabe, da weiss man, dass der Wert Boolean ist.
//			//                           also: NavigatorContentJson=<JSON>{"UIText01":"TESTWERT2DO2JSON01","UIText02":"TESTWERT2DO2JSON02"}</JSON>
//			//Gib den JSON-Array-Wert so an: {"wert1","wert2"}
//			objStreamFile.println("[Section for testJsonHashmap]");
//			objStreamFile.println("Map1="+KernelJsonMapIniSolverZZZTest.sEXPRESSION_JSONMAP01_DEFAULT);
//									
//			
//			//20220926 Tests für die Arbeit mit verschluesselten / encrypted Werten
//			String sValue = "abcde"; int iKeyNumber=5; String sCharacterPool="?! abcdefghijklmnopqrstuvwxyz";
//			String sFlagNumeric = ICharacterPoolUserZZZ.FLAGZ.USENUMERIC.name();
//			String sFlagUppercase = ICharacterPoolUserZZZ.FLAGZ.USEUPPERCASE.name();
//			String sFlagBlank = IROTUserZZZ.FLAGZ.USEBLANK.name();
//			String sEncrypted = ROT13ZZZ.encryptIt(sValue); //"nopqr";
//			objStreamFile.println("[Section for testEncrypted]");
//			objStreamFile.println("WertA="+sValue);
//			objStreamFile.println("WertAencrypted="+sEncrypted);
//			objStreamFile.println("WertAforDecrypt="+KernelEncryptionIniSolverZZZTest.sEXPRESSION_ENCRYPTION01_DEFAULT);
//			
//			sEncrypted = ROTnumericZZZ.encrypt(sValue, iKeyNumber, true, false);
//			objStreamFile.println("WertB="+sValue);
//			objStreamFile.println("WertBencrypted="+KernelEncryptionIniSolverZZZTest.sEXPRESSION_ENCRYPTION02_DEFAULT);
//			
//			CharacterExtendedZZZ objCharacterMissingReplacement = new CharacterExtendedZZZ(ICharacterPoolUserZZZ.cCHARACTER_MISSING_REPLACEMENT_DEFAULT);
//			sEncrypted = ROTnnZZZ.encrypt(sValue, sCharacterPool, objCharacterMissingReplacement, iKeyNumber, true, false, false,false);
//			objStreamFile.println("WertC="+sValue);
//			objStreamFile.println("WertCencrypted="+KernelEncryptionIniSolverZZZTest.sEXPRESSION_ENCRYPTION02_DEFAULT);
//			
//			
//			
//			
//			objFile = new File(sFilePathTotal);
							
			//Kernel + Log - Object dem TestFixture hinzuf�gen. Siehe test.zzzKernel.KernelZZZTest
			objKernel = new KernelZZZ("FGL", "01", "test", "ZKernelConfigKernel_test.ini",(String)null);
			
			
			//#### Ein init TestObjekt
			String[] saFlagInit = {"init"};		
			objFormulaSolverInit = new KernelZFormulaIniSolverZZZ<Object>(saFlagInit);
						
			objFileIniInit = new FileIniZZZ(saFlagInit);
			
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
			
			//Für die Variablenersetzung wichtig: Eine HashMap mit den Variablen in der Ini-Datei.
			HashMapCaseInsensitiveZZZ<String,String> hmVariable = new HashMapCaseInsensitiveZZZ<String,String>();
			hmVariable.put("myTestVariableString", "mySolvedTestVariableString");
			objFileIniTest = new FileIniZZZ(objKernel,  objFile, hmVariable,  saFlagFileIni);
			 					
			objFormulaSolver = new KernelZFormulaIniSolverZZZ(objKernel, objFileIniTest, null);			

		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}//END setup
	
	public void testFlagHandling(){
		try{
				
			assertTrue(objFormulaSolverInit.getFlag("init")==true);
			assertFalse(objFormulaSolver.getFlag("init")==true); //Nun wäre init falsch
			
			String[] saFlag = objFormulaSolver.getFlagZ();
			assertEquals(8, saFlag.length);
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}
	
	/** void, Test: Reading an entry in a section of the ini-file
	* Lindhauer; 22.04.2006 12:54:32
	 */
	public void testCompute(){
		try {
			String sValue; String sExpression; String sExpressionSource; String sExpressionSource2;String sExpessionSourceFormulaMath;
			boolean btemp;
			sExpressionSource = "Der dynamische Wert ist '<Z>[Section A]Testentry1</Z>'. FGL rulez.";
			sExpressionSource2 = "Der dynamische Wert2 ist '<Z>[Section B]Testentry2</Z>'. FGL rulez.";	
			
			//+++++++ VORGEZOGENER LETZTER FEHLERTEST START
			
		
			//+++++++ VORGEZOGENER LETZTER FEHLERTEST ENDE
			
			//################################################
			//### mit objFormulaSolver
			
			btemp = testCompute_SolveWithExpressionA_(sExpressionSource);		
			
			btemp = testCompute_SolveWithExpressionB_(sExpressionSource);		
			
			
			//################################################
			//### mit objFileini
			btemp = testCompute_GetPropertyWithExpression_();
			
			
			//++++++++++++++++++++++++++++++++++			
			//#############################################################
			//+++++++++++++++++++++++++++++++++++++++
			
			//### Kombination, erst Property Holen und dann diesen Wert mit einem Solver verarbeiten.
			btemp = testCompute_GetPropertyWithExpressionCombinedWithSolver_();
			
		
			//+++++++++++++++++++			
			//###########################################################################
			//+++++++++++++++++++++++++++++++++++++++
			
			
			btemp = testCompute_GetPropertySystemNrBeforeGlobal_();
			
			btemp = testCompute_2ndSolverUsesGetPropertyResult_();
			
			//+++++++++++++++++++			
			//###########################################################################
			//+++++++++++++++++++++++++++++++++++++++									
			//Wenn man die Expression_PATH-Behandlung ausstellt, dann werden auch beim "Solven" keine Pfade ersetzt
			btemp = objFormulaSolver.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION,true); 
			assertTrue("Flag nicht vorhanden '" + IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
			
			btemp = objFormulaSolver.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER,true); //false: Damit der Pfad NICHT sofort ausgerechnet wird
			assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
			
			btemp = objFormulaSolver.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH, false); //also nur die Expression aufloesen, aber den Pfad nicht aufloesen.
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
			
			btemp = objFormulaSolver.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA, true);
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA + "'", btemp);
											
			sValue = objFormulaSolver.solve(sExpressionSource2);
			assertEquals("Der dynamische Wert2 ist '<Z>[Section B]Testentry2</Z>'. FGL rulez.", sValue);
			
			//Anstellen
			btemp = objFormulaSolver.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH, true); //also nur die Expression aufloesen, aber den Pfad nicht aufloesen.
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);

			sValue = objFormulaSolver.solve(sExpressionSource2);
			assertEquals("Der dynamische Wert2 ist '<Z>Testvalue2 local to be found</Z>'. FGL rulez.", sValue);
			

			//########################################################################################
			//###BEIM 2ten SUCHEN BEKOMMT MUSS MAN EIN NEUES ERGEBNIS BEKOMMEN
			//########################################################################################			
			//### mit objFormulaSolver
			btemp = testCompute_2ndSolveA_(sExpressionSource);
			
			btemp = testCompute_2ndSolveB_(sExpressionSource2);
												
			//### mit objFileIni			
			btemp = testCompute_2ndGetProperty_();
						
			//########################################################################################
			//### Ausrechnen Mathematischer Formeln ##################################################
			//########################################################################################			
			btemp = testCompute_GetPropertyWithFormulaMath_();
			
			btemp = testCompute_GetPropertyWithFormulaMathByPath_();
			
			
					
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}
	
	private boolean testCompute_2ndSolveA_(String sExpressionSource) {
		boolean btemp; String sValue;
		main:{
			try {
				btemp = objFormulaSolver.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION,true); 
				assertTrue("Flag nicht vorhanden '" + IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
									
				btemp = objFormulaSolver.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER,true); //false: Damit der Pfad NICHT sofort ausgerechnet wird
				assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
							
				btemp = objFormulaSolver.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH, false); //also nur die Expression aufloesen, aber den Pfad nicht aufloesen.
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
		
				btemp = objFormulaSolver.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA, true);
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA + "'", btemp);
				
				//Wenn man die Expression-Behandlung ausstellt, dann werden auch beim "Solven" keine Pfade ersetzt
				sValue = objFormulaSolver.solve(sExpressionSource);
				assertEquals("Der dynamische Wert ist '<Z>[Section A]Testentry1</Z>'. FGL rulez.", sValue);
								
				//Anstellen
				btemp = objFormulaSolver.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH, true); //also nur die Expression aufloesen, aber den Pfad nicht aufloesen.
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
		
				sValue = objFormulaSolver.solve(sExpressionSource);
				assertEquals("Der dynamische Wert ist '<Z>Testvalue1 to be found</Z>'. FGL rulez.", sValue);
			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			}
		}//end main:			
		return true;
	}
	private boolean testCompute_2ndSolveB_(String sExpressionSource) {
		boolean btemp; String sValue;
		main:{
			try {
				btemp = objFormulaSolver.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION,true); 
				assertTrue("Flag nicht vorhanden '" + IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
									
				btemp = objFormulaSolver.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER,true); //false: Damit der Pfad NICHT sofort ausgerechnet wird
				assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
							
				btemp = objFormulaSolver.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH, false); //also nur die Expression aufloesen, aber den Pfad nicht aufloesen.
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
		
				btemp = objFormulaSolver.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA, true);
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA + "'", btemp);
				
				//Wenn man die Expression-Behandlung ausstellt, dann werden auch beim "Solven" keine Pfade ersetzt
				sValue = objFormulaSolver.solve(sExpressionSource);
				assertEquals("Der dynamische Wert2 ist '<Z>[Section B]Testentry2</Z>'. FGL rulez.", sValue);
								
				//Anstellen
				btemp = objFormulaSolver.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH, true); //also nur die Expression aufloesen, aber den Pfad nicht aufloesen.
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
		
				sValue = objFormulaSolver.solve(sExpressionSource);
				assertEquals("Der dynamische Wert2 ist '<Z>Testvalue2 local to be found</Z>'. FGL rulez.", sValue);
			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			}
		}//end main:			
		return true;
	}
	
	private boolean testCompute_2ndGetProperty_() {
		boolean btemp; String sValue; String sSection; String sProperty;
		String sExpressionSource;
		String sExpression; //fuer den RAW Vergleich.

		main:{
			sExpressionSource = "Der dynamische Wert2 ist '<Z>[Section B]Testentry2</Z>'. FGL rulez.";
			sSection = "Section for testCompute";
			sProperty = "Formula2";

			try {
				//1. Suche
				//Erst einmal soll der Wert nicht sofort ausgerechnet werden.			
				btemp = objFileIniTest.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION,true); 
				assertTrue("Flag nicht vorhanden '" + IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
				
				//Erst wenn der Parser den Solver nutzen soll, kommt auch das vom solve() raus.
				btemp = objFileIniTest.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER,true);
				assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
							
				btemp = objFileIniTest.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH,false); //Pfade werden nicht aufgeloest
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
				
				btemp = objFileIniTest.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA, false); //eh keine Formel drin, also sollte das egal sein.
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA + "'", btemp);
														
				//Merke: wg. Parsen sind die Z-Tags verschwunden.
				sExpression = "Der dynamische Wert2 ist '[Section B]Testentry2'. FGL rulez.";
				sValue = objFileIniTest.getPropertyValue(sSection, sProperty).getValue();
				assertEquals(sExpression, sValue);
			
				//####################################################
				//2. Suche: Muss auch funktionieren... es darf halt nix in irgendwelchen Objekten bleiben. Intern wird naemlich gecloned.
				sExpressionSource = "Der dynamische Wert2 ist '<Z>[Section B]Testentry2</Z>'. FGL rulez.";
				sSection = "Section for testCompute";
				sProperty = "Formula1";
				
				//### mit objFileini
				//+++ KEINE Expression oder Formel verwenden dann ist die Aufloesung egal
				btemp = objFileIniTest.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION, false);
				assertTrue("Flag nicht vorhanden '" + IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
				
				btemp = objFileIniTest.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER,true);
				assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
				 			
				btemp = objFileIniTest.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH, true); //muss egal sein, da EXPRESIONSOLVER = FALSE
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
				
				btemp = objFileIniTest.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA, false); //eh keine Formel drin, also sollte das egal sein.
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA + "'", btemp);
							
				//Merke: Ohne Expression Behandlund duefen trotz parsen die Z-Tags nicht verschwinden.
				sExpression = "Der dynamische Wert ist '<Z>[Section A]Testentry1</Z>'. FGL rulez.";
				sValue= objFileIniTest.getPropertyValue(sSection, sProperty).getValue();
				assertEquals(sExpression,sValue);
				
			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			}
		}//end main:			
		return true;
	}
	
	private boolean testCompute_GetPropertySystemNrBeforeGlobal_() {
		boolean btemp; String sValue; String sSection; String sProperty;
		String sExpressionSource;
		String sExpression; //fuer den RAW Vergleich.
		main:{
			sExpressionSource = "Der dynamische Wert2 ist '<Z>[Section B]Testentry2</Z>'. FGL rulez.";
			sSection = "Section for testCompute";
			sProperty = "Formula2";
			try {
				//Test: Wert mit Systemnr soll vor dem globalen Wert gefunden werden!!!
				btemp = objFileIniTest.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION,true); 
				assertTrue("Flag nicht vorhanden '" + IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
						
				//Erst wenn der Parser den Solver nutzen soll, kommt auch das vom solve() raus.
				btemp = objFileIniTest.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER,true);
				assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
				
				//Nun werden die Pfade aufgeloest
				btemp = objFileIniTest.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH,true);
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
				
				btemp = objFileIniTest.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA, false); //eh keine Formel drin, also sollte das egal sein.
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA + "'", btemp);
				
				sExpression = "Der dynamische Wert2 ist 'Testvalue2 local to be found'. FGL rulez.";
				sValue = objFileIniTest.getPropertyValue(sSection, sProperty).getValue();
				assertEquals(sExpression, sValue);
			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			}
		}//end main:			
		return true;
	}
	
	private boolean testCompute_2ndSolverUsesGetPropertyResult_() {
		boolean btemp; String sValue; String sSection; String sProperty;
		String sExpressionSource;
		String sExpression; //fuer den RAW Vergleich.

		main:{
			sExpressionSource = "Der dynamische Wert2 ist '<Z>[Section B]Testentry2</Z>'. FGL rulez.";
			sSection = "Section for testCompute";
			sProperty = "Formula2";

			try {
		
				//Erst einmal soll der Wert nicht sofort ausgerechnet werden.
				//Dann soll damit der Solver arbeiten
				btemp = objFileIniTest.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION,true); 
				assertTrue("Flag nicht vorhanden '" + IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
					
				//Erst wenn der Parser den Solver nutzen soll, kommt auch das vom solve() raus.
				btemp = objFileIniTest.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER,true);
				assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
							
				//KEINE AUfloesung der Pfade
				btemp = objFileIniTest.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH, false);
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
				
				btemp = objFileIniTest.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA, false); //eh keine Formel drin, also sollte das egal sein.
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA + "'", btemp);
						
				//intern wird geparsed -> Z-Tag aussen rum verschwindet
				sExpression = "Der dynamische Wert2 ist '[Section B]Testentry2'. FGL rulez.";
				sValue = objFileIniTest.getPropertyValue(sSection, sProperty).getValue();
				assertEquals(sExpression, sValue);
				
				//+++ objFormula arbeitet mit dem Wert weiter				
				btemp = objFormulaSolver.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION,true); 
				assertTrue("Flag nicht vorhanden '" + IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
				
				btemp = objFormulaSolver.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER,true); //false: Damit der Pfad NICHT sofort ausgerechnet wird
				assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
				
				btemp = objFormulaSolver.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH, true); //also nur die Expression aufloesen, aber den Pfad nicht aufloesen.
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
				
				btemp = objFormulaSolver.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA, true);
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA + "'", btemp);

				//Ohne Z-Tags im Ausdruck wird nichts aufgeloest, obwohl alle Flags auf true sind
				sExpression = "Der dynamische Wert2 ist '[Section B]Testentry2'. FGL rulez.";
				sValue = objFormulaSolver.parse(sExpression);
				assertEquals(sExpression, sValue);
				
				//Nun werden die Pfade in objFileIniTest aufgeloest
				btemp = objFileIniTest.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH, true);
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
				
				sExpression = "Der dynamische Wert2 ist 'Testvalue2 local to be found'. FGL rulez."; 
				sValue = objFileIniTest.getPropertyValue("Section for testCompute", "Formula2").getValue();
				assertEquals(sExpression, sValue);
														
			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			}
		}//end main:			
		return true;
	}
	
	private boolean testCompute_SolveWithExpressionA_(String sExpressionSource) {
		boolean btemp; String sExpression; String sValue;
		main:{
			try {				
				//+++ Alle Flags auf false
				btemp = objFormulaSolver.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION,false); 
				assertTrue("Flag nicht vorhanden '" + IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
							
				//Nun werden die Pfade aufgeloest
				btemp = objFormulaSolver.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH,false);
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
				
				//Erst wenn der Parser den Solver nutzen soll, kommt auch das vom solve() raus.
				btemp = objFormulaSolver.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER,false);
				assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);

				btemp = objFormulaSolver.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA, false); //eh keine Formel drin, also sollte das egal sein.
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA + "'", btemp);
				
				
				//--> Output = Input
				sExpression = objFormulaSolver.solve(sExpressionSource);
				assertEquals(sExpressionSource, sExpression);
				
				//-->Output = Input			
				sExpression = objFormulaSolver.parse(sExpressionSource);
				assertEquals(sExpressionSource, sExpression);
				
				//+++++++++++++++++++++++++++++++++++++++++++++++++		
				//+++ Expression behandeln
				btemp = objFormulaSolver.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER,true); //eh keine Formel drin, also sollte das egal sein.);
				assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
	 
				//--> Output = Input, weil keine Pfad-expression verarbeitet wird.
				sExpression = objFormulaSolver.solve(sExpressionSource);
				assertEquals(sExpressionSource, sExpression);
				
				//-->Output = Input, aber durch das Parsen und Verareitung der Expression sind die Z-Tags drumrum weg
				btemp = objFormulaSolver.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION,true); 
				assertTrue("Flag nicht vorhanden '" + IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
	 
				//beim "reinen Parsen" bleiben Pfadangaben bestehen, 
				//auch Z-Tags müssten bestehen bleiben, weil IKernelExpressionIniSolverZZZ nicht implementiert ist 
				//Aber Weil der Tag fuer Formeln auch <Z> ist, werden diese letztendlich vom Parsen doch entfent!!!
				sExpression = objFormulaSolver.parse(sExpressionSource);
				assertEquals("Der dynamische Wert ist '[Section A]Testentry1'. FGL rulez.", sExpression);
				
				
				//+++++++++++++++++++++++++++++++++++++++	
				//Behandlung von expression ueberhaupt
				btemp = objFormulaSolver.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION,true); 
				assertTrue("Flag nicht vorhanden '" + IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
	 
				//Nun werden die Pfade aufgeloest
				btemp = objFormulaSolver.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH,true);
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);

				sExpression = objFormulaSolver.solve(sExpressionSource);
				assertEquals("Der dynamische Wert ist '<Z>Testvalue1 to be found</Z>'. FGL rulez.", sExpression);
							
				//Wenn der parser den Solver nicht nutzen soll, kommt nicht das vom solve() raus.
				//Umgebende Z-Tags werden aber trotzdem enfernt.
				btemp = objFormulaSolver.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER,false);
				assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
				
				sExpression = objFormulaSolver.parse(sExpressionSource);
				assertEquals("Der dynamische Wert ist '[Section A]Testentry1'. FGL rulez.", sExpression);
				
				
				//Erst wenn der Parser den Solver nutzen soll, kommt auch das vom solve() raus.
				btemp = objFormulaSolver.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER,true);
				assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
				 
				sExpression = objFormulaSolver.parse(sExpressionSource);
				assertEquals("Der dynamische Wert ist 'Testvalue1 to be found'. FGL rulez.", sExpression);
				//+++++++++++++++++++++++++++++++++++++++

			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			}
		}//end main:			
		return true;
	}
	
	private boolean testCompute_SolveWithExpressionB_(String sExpressionSource) {
		boolean btemp; String sExpression; String sValue;
		main:{
			try {				
				btemp = objFormulaSolver.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER,true); //false: Damit der Pfad NICHT sofort ausgerechnet wird
				assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
							
				btemp = objFormulaSolver.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH, false); //also nur die Expression aufloesen, aber den Pfad nicht aufloesen.
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
				
				btemp = objFormulaSolver.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA,true); //eh keine Formel drin, also sollte das egal sein.);
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA + "'", btemp);
							
				//beim "reinen Solven" bleiben die Z-Tags drin
				sValue = objFormulaSolver.solve(sExpressionSource);
				assertEquals("Der dynamische Wert ist '<Z>[Section A]Testentry1</Z>'. FGL rulez.", sValue);
							
				//beim "Parsen" bleiben immer noch die Pfadangaben bestehen, aber die Z-Tags müssen weg.
				sValue = objFormulaSolver.parse(sExpressionSource);
				assertEquals("Der dynamische Wert ist '[Section A]Testentry1'. FGL rulez.", sValue);
				
				//++++++++++++++++++++++++++++++++++			
				//#############################################################
				//+++++++++++++++++++++++++++++++++++++++
				btemp = objFormulaSolver.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION,false); 
				assertTrue("Flag nicht vorhanden '" + IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
									
				btemp = objFormulaSolver.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER,true); //false: Damit der Pfad NICHT sofort ausgerechnet wird
				assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
							
				btemp = objFormulaSolver.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH, true); //also nur die Expression aufloesen, aber den Pfad nicht aufloesen.
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);

				btemp = objFormulaSolver.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA, true);
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA + "'", btemp);
				
				//Wenn man die Expression-Behandlung ausstellt, dann werden auch beim "Solven" keine Pfade ersetzt
				sValue = objFormulaSolver.solve(sExpressionSource);
				assertEquals("Der dynamische Wert ist '<Z>[Section A]Testentry1</Z>'. FGL rulez.", sValue);
												
				//dito beim parsen... aber dann sind die Z-Tags auch nicht weg.
				sValue = objFormulaSolver.parse(sExpressionSource);
				assertEquals("Der dynamische Wert ist '<Z>[Section A]Testentry1</Z>'. FGL rulez.", sValue);
				
				//nun einfach oben das Flag setzen... alles wird maximal uebersetzt
				btemp = objFormulaSolver.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION,true); 
				assertTrue("Flag nicht vorhanden '" + IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
				
				//... beim solven bleiben die Z-Tags bestehen
				sValue = objFormulaSolver.solve(sExpressionSource);
				assertEquals("Der dynamische Wert ist '<Z>Testvalue1 to be found</Z>'. FGL rulez.", sValue);
				
				//... beim parsen kommen die Z-Tags raus.
				sValue = objFormulaSolver.parse(sExpressionSource);
				assertEquals("Der dynamische Wert ist 'Testvalue1 to be found'. FGL rulez.", sValue);
				
			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			}
		}//end main:			
		return true;
	}
	
	
	//###########################################################################
	/** void, Test: Reading an entry in a section of the ini-file
	* Lindhauer; 22.04.2006 12:54:32
	 */
	public void testCompute_FORMULA_MATH(){
		String sExpression; String sExpressionSolved;;
//		try {			
			sExpression = KernelZFormulaIniSolverZZZTest.sEXPRESSION_FORMULA_MATH_SOURCE01;
			sExpressionSolved = KernelZFormulaIniSolverZZZTest.sEXPRESSION_FORMULA_MATH_SOLVED01;
			testCompute_FORMULA_MATH_(sExpression, sExpressionSolved);
//		} catch (ExceptionZZZ ez) {
//			fail("Method throws an exception." + ez.getMessageLast());
//		}
	
	}
	
	private boolean testCompute_FORMULA_MATH_(String sExpressionIn, String sExpressionSolvedIn) {
		boolean btemp; String sValue;
		String sExpression; String sExpressionSolved; String sTagSolved;
		sExpression = sExpressionIn;	
		sExpressionSolved = sExpressionSolvedIn;
		
		String sTagStartZ = "<Z>";
		String sTagEndZ = "</Z>";
				
		main:{
			try {
				//+++++++ VORGEZOGENER LETZTER FEHLERTEST START
				
				//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
				//+++ Ohne jegliche FORMULA_MATH Solver-Berechnung
				
				
				//aktuelles Problem .parseChanged wird gesetzt wenn der Tag entfernt wird... soll das so sein???
				//b)
				sExpression = sExpressionIn;
				sExpressionSolved = sExpression;	//Beim Parsen werden, wenn wie hier gewuenscht immer der Z-Tag entfernt.
				sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ, false);
				sTagSolved = KernelZFormulaIniSolverZZZTest.sEXPRESSION_FORMULA_MATH_SOURCE01_CONTENT;				
				btemp = testCompute_FORMULA_MATH_SolverUnsolved_(sExpression, sExpressionSolved, sTagSolved, true, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
			
				
				//+++++++ VORGEZOGENER LETZTER FEHLERTEST ENDE
				
				//###########################
			    //### objExpression
				//#########################
		
				//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
				//+++ Ohne jegliche Expression-Berechnung
				//a)
				sExpression = sExpressionIn;
				sExpressionSolved = sExpression;
				sTagSolved = KernelZFormulaIniSolverZZZTest.sEXPRESSION_FORMULA_MATH_SOURCE01_CONTENT;				
				btemp = testCompute_FORMULA_MATH_Unexpressed_(sExpression, sExpressionSolved, sTagSolved, false, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
				
				//b)
				sExpression = sExpressionIn;
				sExpressionSolved = sExpression;			
				sTagSolved = KernelZFormulaIniSolverZZZTest.sEXPRESSION_FORMULA_MATH_SOURCE01_CONTENT;
				btemp = testCompute_FORMULA_MATH_Unexpressed_(sExpression, sExpressionSolved, sTagSolved, true, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
				
				//c)
				sExpression = sExpressionIn;
				sExpressionSolved = sExpression; 			
				sTagSolved = KernelZFormulaIniSolverZZZTest.sEXPRESSION_FORMULA_MATH_SOURCE01_CONTENT;
				btemp = testCompute_FORMULA_MATH_Unexpressed_(sExpression, sExpressionSolved, sTagSolved, false, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
					
				//d)
				sExpression = sExpressionIn;
				sExpressionSolved = sExpression;
				sTagSolved = KernelZFormulaIniSolverZZZTest.sEXPRESSION_FORMULA_MATH_SOURCE01_CONTENT;
				btemp = testCompute_FORMULA_MATH_Unexpressed_(sExpression, sExpressionSolved, sTagSolved, true, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
				
				
				//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
				//+++ Ohne jegliche FORMULA_MATH Solver-Berechnung
				//a)
				sExpression = sExpressionIn;
				sExpressionSolved = sExpression;				
				sTagSolved = KernelZFormulaIniSolverZZZTest.sEXPRESSION_FORMULA_MATH_SOURCE01_CONTENT;
				btemp = testCompute_FORMULA_MATH_SolverUnsolved_(sExpression, sExpressionSolved, sTagSolved, false, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
				
				//b)
				sExpression = sExpressionIn;
				sExpressionSolved = sExpression;	//Beim Parsen werden, wenn wie hier gewuenscht immer der Z-Tag entfernt.
				sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ, false);
				sTagSolved = KernelZFormulaIniSolverZZZTest.sEXPRESSION_FORMULA_MATH_SOURCE01_CONTENT;				
				btemp = testCompute_FORMULA_MATH_SolverUnsolved_(sExpression, sExpressionSolved, sTagSolved, true, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
				
				//c)
				sExpression = sExpressionIn;
				sExpressionSolved = sExpression; 			
				sTagSolved = KernelZFormulaIniSolverZZZTest.sEXPRESSION_FORMULA_MATH_SOURCE01_CONTENT;
				btemp = testCompute_FORMULA_MATH_SolverUnsolved_(sExpression, sExpressionSolved, sTagSolved, false, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
					
				//d)
				sExpression = sExpressionIn;
				sExpressionSolved = sExpression;	//Beim Parsen werden, wenn wie hier gewuenscht immer der Z-Tag entfernt.
				sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ, false);
				sTagSolved = KernelZFormulaIniSolverZZZTest.sEXPRESSION_FORMULA_MATH_SOURCE01_CONTENT;
				btemp = testCompute_FORMULA_MATH_SolverUnsolved_(sExpression, sExpressionSolved, sTagSolved, true, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
				
				
				//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
				//+++ Ohne FORMULA-Berechung
				//a)
				sExpression = sExpressionIn;
				sExpressionSolved = sExpression;
				sTagSolved = KernelZFormulaIniSolverZZZTest.sEXPRESSION_FORMULA_MATH_SOURCE01_CONTENT;
				btemp = testCompute_FORMULA_MATH_FormulaUnsolved_(sExpression, sExpressionSolved, sTagSolved, false, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
				
				//b)
				sExpression = sExpressionIn;
				sExpressionSolved = sExpression;
				sTagSolved = KernelZFormulaIniSolverZZZTest.sEXPRESSION_FORMULA_MATH_SOURCE01_CONTENT;
				sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ, false);
				//Beim Parsen ohne Solver, bleibt sogar das Encryption-Tag drin, auch wenn sonst die Tags entfernt werden.
				btemp = testCompute_FORMULA_MATH_FormulaUnsolved_(sExpression, sExpressionSolved, sTagSolved, true, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
		
				//c)
				sExpression = sExpressionIn;
				sExpressionSolved = sExpression;
				sTagSolved = KernelZFormulaIniSolverZZZTest.sEXPRESSION_FORMULA_MATH_SOURCE01_CONTENT;
				//Beim Solven ohne Solver, bleibt alles wie est ist.
				btemp = testCompute_FORMULA_MATH_FormulaUnsolved_(sExpression, sExpressionSolved, sTagSolved, false, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
				
				//d)
				sExpression = sExpressionIn;
				sExpressionSolved = sExpression;
				sTagSolved = KernelZFormulaIniSolverZZZTest.sEXPRESSION_FORMULA_MATH_SOURCE01_CONTENT;
				sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ, false);
				//Beim Solven ohne Solver, werden nur die äusseren Z-Tags ggfs. entfernt.
				btemp = testCompute_FORMULA_MATH_FormulaUnsolved_(sExpression, sExpressionSolved, sTagSolved, true, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
				
				//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
				//+++ Ohne FORMULA_MATH-Berechung
				//a)
				sExpression = sExpressionIn;
				sExpressionSolved = sExpression;
				btemp = testCompute_FORMULA_MATH_FormulaMathUnsolved_(sExpression, sExpressionSolved, sTagSolved, false, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
				
				//b)
				sExpression = sExpressionIn;
				sExpressionSolved = sExpression; 
				sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ, false);
				//Beim Parsen ohne encryption, muss doch dieser encryption - Tag drinbleiben. Hier werden also nur die aeussern Z-Tags entfernt.
				sTagSolved = KernelZFormulaIniSolverZZZTest.sEXPRESSION_FORMULA_MATH_SOURCE01_CONTENT;
				btemp = testCompute_FORMULA_MATH_FormulaMathUnsolved_(sExpression, sExpressionSolved, sTagSolved, true, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
				
				//c)
				sExpression = sExpressionIn;
				sExpressionSolved = sExpression;
				//Beim Solven ohne encryption, bleibt alles an Tags drin.
				sTagSolved = KernelZFormulaIniSolverZZZTest.sEXPRESSION_FORMULA_MATH_SOURCE01_CONTENT;
				btemp = testCompute_FORMULA_MATH_FormulaMathUnsolved_(sExpression, sExpressionSolved, sTagSolved, false, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
			
				//d)
				sExpression = sExpressionIn;
				sExpressionSolved = sExpression;	
				sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ, false);
				//Beim Solven ohne encryption muss dieser encryption - Tag drinbleiben
				sTagSolved = KernelZFormulaIniSolverZZZTest.sEXPRESSION_FORMULA_MATH_SOURCE01_CONTENT;
				btemp = testCompute_FORMULA_MATH_FormulaMathUnsolved_(sExpression, sExpressionSolved, sTagSolved, true, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
				
		
				//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
				//+++ Mit FORMULA MATH-Berechnung
				//a) nur parsen bringt keinen Unterschied, wenn die Tags drinbleiben sollen
				sExpression = sExpressionIn;
				sExpressionSolved = sExpressionSolvedIn;
				sExpressionSolved = objFormulaSolver.makeAsExpression(sExpressionSolved);
				sExpressionSolved = ExpressionIniUtilZZZ.makeAsExpression(sExpressionSolved, "JSON");
				sExpressionSolved = ExpressionIniUtilZZZ.makeAsExpression(sExpressionSolved);
				
				//false: d.h. Tags sollen drin bleiben sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ, false);
				sTagSolved = KernelZFormulaIniSolverZZZTest.sEXPRESSION_FORMULA_MATH_SOURCE01_CONTENT;
				btemp = testCompute_FORMULA_MATH_(sExpression, sExpressionSolved, sTagSolved, false, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
		
				//b) Werdem beim reinen Parsen die umgebenden Tags entfernt, dann wird auch das Encryption-Tag entfernt. Das wird naemlich auch durch Parsen "aufgeloest". Das eigentliche Aufloesen findet aber nicht statt.
				sExpression = sExpressionIn;			
				sExpressionSolved = sExpressionSolvedIn; 
				//der eigene Tag wird ja auch beim Parsen entfernt. Also hier nicht als erwarteteten Wert aufnehmen... sExpressionSolved = objExpressionSolver.makeAsExpression(sExpressionSolved);
				sExpressionSolved = ExpressionIniUtilZZZ.makeAsExpression(sExpressionSolved, "JSON");
				//der umgebende Z-Tag soll weg sein...  sExpressionSolved = ExpressionIniUtilZZZ.makeAsExpression(sExpressionSolved);
				
				//Variante, wenn man Tags Wegnehmen wuerde
		//		sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
		//		sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSolved, objExpressionSolver.getName(), false);
							
				sTagSolved = KernelZFormulaIniSolverZZZTest.sEXPRESSION_FORMULA_MATH_SOURCE01_CONTENT;
				btemp = testCompute_FORMULA_MATH_(sExpression, sExpressionSolved, sTagSolved, true, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
				
				//c)
				sExpression = sExpressionIn;
				sExpressionSolved = sExpressionSolvedIn;			
				//der eigene Tag wird ja auch beim Parsen entfernt. Also hier nicht als erwarteteten Wert aufnehmen... sExpressionSolved = objExpressionSolver.makeAsExpression(sExpressionSolved);
				sExpressionSolved = ExpressionIniUtilZZZ.makeAsExpression(sExpressionSolved, "JSON");
				sExpressionSolved = ExpressionIniUtilZZZ.makeAsExpression(sExpressionSolved); //Z-Tags bleiben drin
							
				//Wichtig: JSON:MAP soll aus dem Ergebnis weg sein, wg. Aufloesen!!! Auch wenn die umgebenden Z-Tags drin bleiben.
				
				sTagSolved = KernelZFormulaIniSolverZZZTest.sEXPRESSION_FORMULA_MATH_SOURCE01_CONTENT;
				btemp = testCompute_FORMULA_MATH_(sExpression, sExpressionSolved, sTagSolved, false, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
				
				//d)
				sExpression = sExpressionIn;
				sExpressionSolved = sExpressionSolvedIn;
				//der eigene Tag wird ja auch beim Parsen entfernt. Also hier nicht als erwarteteten Wert aufnehmen... sExpressionSolved = objExpressionSolver.makeAsExpression(sExpressionSolved);
				sExpressionSolved = ExpressionIniUtilZZZ.makeAsExpression(sExpressionSolved, "JSON");
				//Z-Tags sind raus sExpressionSolved = ExpressionIniUtilZZZ.makeAsExpression(sExpressionSolved);
				
				//Wichtig: Bis auf den umgebendn JSON-Tag (der Solver wird hier nicht verwendet) sind alle Tags raus.
				
				sTagSolved = KernelZFormulaIniSolverZZZTest.sEXPRESSION_FORMULA_MATH_SOURCE01_CONTENT;
				btemp = testCompute_FORMULA_MATH_(sExpression, sExpressionSolved, sTagSolved, true, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
								
			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			}		
		}//end main:			
		return true;
	}
	
	//########################################################################
	
	private boolean testCompute_FORMULA_MATH_Unexpressed_(String sExpressionSourceIn, String sExpressionSolvedIn, String sTagSolvedIn, boolean bRemoveSurroundingSeparators, IEnumSetMappedTestCaseZZZ objEnumTestCase) {
		boolean bReturn = false;
		try {
			boolean btemp; 
			
			String sExpression; 
			String sExpressionSolved; String sValue; String sValueUsed;				
			ReferenceZZZ<IKernelConfigSectionEntryZZZ> objSectionEntryReference;
			IKernelConfigSectionEntryZZZ objEntry; IKernelConfigSectionEntryZZZ objEntryUsed;
			
			String sTagStartZ = "<Z>";
			String sTagEndZ = "</Z>";	
						
			//####################################################################################
			//### EXPRESSION nicht verarbeiten - FORMULA_MATH 
			//####################################################################################
		
			btemp = objFormulaSolver.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION, false); 
			assertTrue("Flag nicht vorhanden '" + IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
			
			btemp = objFormulaSolver.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER,true); //sollte egal sein 
			assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
			
			btemp = objFormulaSolver.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA,true); //sollte egal sein
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA + "'", btemp);
			
			
			btemp = objFormulaSolver.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH, true); //sollte egal sein
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH + "'", btemp);
														
			
						
			//+++ ... parse ist nicht solve... also wird hier nichts aufgeloest, aussser die Pfade
			if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.PARSE)) {
				sExpression = sExpressionSourceIn;
				sExpressionSolved = sExpressionSolvedIn;		
				objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
				sValue = objFormulaSolver.parse(sExpression, objSectionEntryReference, bRemoveSurroundingSeparators);
				assertEquals(sExpressionSolved, sValue);
				
				sExpressionSolved = sExpressionSolvedIn; //der Wert des Tags selbst unterscheidet sich immer vom Wert der Zeile
				
				//Ausser wenn die Expression gar nicht behandelt wird. Dann ist das die ganze Zeile
				//sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ, false);
				//sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSolved, objFormulaSolver.getName(), false);								
				sValue = objFormulaSolver.getValue();
				assertEquals(sExpressionSolved, sValue);
				
				
				objEntry = objSectionEntryReference.get();
				assertNotNull(objEntry);

				assertTrue(objEntry.isParseCalled());
				
				sExpressionSolved = sExpressionSolvedIn;
				if(sExpression.equals(sExpressionSolved)) {
					assertFalse(objEntry.isParsedChanged());						
				}else {
					assertTrue(objEntry.isParsedChanged());
				}
				
				assertFalse(objEntry.isSolveCalled()); //Der Solve-Schritt wurde gemacht.
				assertFalse(objEntry.isSolvedChanged()); //es wird ja nix gemacht, also "unveraendert" 
				
				
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
				sValue = objFormulaSolver.solve(sExpression, objSectionEntryReference, bRemoveSurroundingSeparators);
				assertEquals(sExpressionSolved, sValue);

				objEntry = objSectionEntryReference.get();
				assertNotNull(objEntry);
				
				assertFalse(objEntry.isParseCalled()); //Wenn der Solver nicht ausgeführt wird, wird auch kein parser gestartet
				assertFalse(objEntry.isParsedChanged()); //es wird ja nix gemacht, also "unveraendert"
				
				assertTrue(objEntry.isSolveCalled()); //Der Solve-Schritt wurde gemacht.
				assertFalse(objEntry.isSolvedChanged()); //Ohne Expression Behandlung wird auch nichts geaendert.
			
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
				objEntryUsed = objFormulaSolver.parseAsEntry(sExpression, bRemoveSurroundingSeparators);
				assertNotNull(objEntryUsed);
				
				sValue = objEntryUsed.getValue();
				assertEquals(sExpressionSolved, sValue);
				
				assertFalse(objEntryUsed.isParseCalled()); 
				assertFalse(objEntryUsed.isParsedChanged());
				
				assertFalse(objEntryUsed.isSolveCalled()); //Der Solve-Schritt wurde gemacht.
				assertFalse(objEntryUsed.isSolvedChanged()); //es wird ja nix gemacht, also "unveraendert" 
																
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
				objEntry = objFormulaSolver.solveAsEntry(sExpression, objSectionEntryReference, bRemoveSurroundingSeparators);
				assertNotNull(objEntry);
				
				sValue = objEntry.getValue();
				assertEquals(sExpressionSolved, sValue);
				
				assertFalse(objEntry.isParseCalled());
				assertFalse(objEntry.isParsedChanged());
								
				assertTrue(objEntry.isSolveCalled()); //Der Solve-Schritt wurde gemacht.
				assertFalse(objEntry.isSolvedChanged()); //es wird ja nix gemacht, also "unveraendert" 
								
				assertFalse(objEntry.isDecrypted());
				assertNull(objEntry.getValueDecrypted()); //Merke: sValue kann unterschiedlich zu dem decrypted Wert sein. Wenn etwas drumherum steht.
				
				assertFalse(objEntry.isCall());
				assertFalse(objEntry.isJavaCall());
				assertNull(objEntry.getCallingClassname());
				assertNull(objEntry.getCallingMethodname());
			}
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

			
			bReturn = true;
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}
		return bReturn;
	}


	//#############################################################################
	
	private boolean testCompute_FORMULA_MATH_SolverUnsolved_(String sExpressionSourceIn, String sExpressionSolvedIn, String sTagSolvedIn, boolean bRemoveSurroundingSeparators, IEnumSetMappedTestCaseZZZ objEnumTestCase) {
		boolean bReturn = false;
		try {
			boolean btemp; 
			
			String sExpression; String sExpressionSolved; String sTagSolved;
			String sValue; String sValueUsed;				
			ReferenceZZZ<IKernelConfigSectionEntryZZZ> objSectionEntryReference;
			IKernelConfigSectionEntryZZZ objEntry; IKernelConfigSectionEntryZZZ objEntryUsed;
			
			String sTagStartZ = "<Z>";
			String sTagEndZ = "</Z>";	
						
			//####################################################################################
			//### EXPRESSION nicht verarbeiten - FORMULA_MATH 
			//####################################################################################
		
			btemp = objFormulaSolver.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION, true); 
			assertTrue("Flag nicht vorhanden '" + IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
			
			btemp = objFormulaSolver.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER, false); 
			assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
			
			btemp = objFormulaSolver.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA,true); //sollte egal sein
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA + "'", btemp);
			
			
			btemp = objFormulaSolver.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH, true); //sollte egal sein
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH + "'", btemp);
														
			
						
			//+++ ... parse ist nicht solve... also wird hier nichts aufgeloest, aussser die Pfade
			if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.PARSE)) {
				sExpression = sExpressionSourceIn;
				sExpressionSolved = sExpressionSolvedIn;
				sTagSolved = sTagSolvedIn;
				objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
				sValue = objFormulaSolver.parse(sExpression, objSectionEntryReference, bRemoveSurroundingSeparators);
				assertEquals(sExpressionSolved, sValue);
				
//				sExpressionSolved = sExpressionSolvedIn; //der Wert des Tags selbst unterscheidet sich immer vom Wert der Zeile
//				sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ, false);
//				sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSolved, objFormulaSolver.getName(), false);								
				sValue = objFormulaSolver.getValue();
				assertEquals(sTagSolved, sValue);
				
				
				objEntry = objSectionEntryReference.get();
				assertNotNull(objEntry);

				assertTrue(objEntry.isParseCalled());
				assertFalse(objEntry.isParsedChanged());						

				assertFalse(objEntry.isSolveCalled()); //Der Solve-Schritt wurde gemacht.
				assertFalse(objEntry.isSolvedChanged()); //es wird ja nix gemacht, also "unveraendert" 
								
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
				sTagSolved = sTagSolvedIn;
				objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
				sValue = objFormulaSolver.solve(sExpression, objSectionEntryReference, bRemoveSurroundingSeparators);
				assertEquals(sExpressionSolved, sValue);
				
				sValue = objFormulaSolver.getValue();
				assertEquals(sTagSolved, sValue);
				
				objEntry = objSectionEntryReference.get();
				assertNotNull(objEntry);
				
				assertTrue(objEntry.isParseCalled());
				assertFalse(objEntry.isParsedChanged());						
				
				assertTrue(objEntry.isSolveCalled()); //Der Solve-Schritt wurde gemacht.
				assertFalse(objEntry.isSolvedChanged()); //Ohne Expression Behandlung wird auch nichts geaendert.
								
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
				sTagSolved = sTagSolvedIn;
				objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
				objEntryUsed = objFormulaSolver.parseAsEntry(sExpression, bRemoveSurroundingSeparators);
				assertNotNull(objEntryUsed);
				
				sValue = objFormulaSolver.getValue();
				assertEquals(sTagSolved, sValue);
				
				sValue = objEntryUsed.getValue();
				assertEquals(sExpressionSolved, sValue);
								
				assertTrue(objEntryUsed.isParseCalled());
				assertFalse(objEntryUsed.isParsedChanged());						
				
				assertFalse(objEntryUsed.isSolveCalled()); //Der Solve-Schritt wurde gemacht.
				assertFalse(objEntryUsed.isSolvedChanged()); //es wird ja nix gemacht, also "unveraendert" 
																
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
				sTagSolved = sTagSolvedIn;
				objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
				objEntry = objFormulaSolver.solveAsEntry(sExpression, objSectionEntryReference, bRemoveSurroundingSeparators);
				assertNotNull(objEntry);
				
				sValue = objFormulaSolver.getValue();
				assertEquals(sTagSolved, sValue);
				
				sValue = objEntry.getValue();
				assertEquals(sExpressionSolved, sValue);
				
				assertTrue(objEntry.isParseCalled());
				
				sExpressionSolved = sExpressionSolvedIn;
				if(sExpression.equals(sExpressionSolved)) {
					assertFalse(objEntry.isParsedChanged());						
				}else {
					assertTrue(objEntry.isParsedChanged());
				}
								
				assertTrue(objEntry.isSolveCalled()); //Der Solve-Schritt wurde gemacht.
				assertFalse(objEntry.isSolvedChanged()); //es wird ja nix gemacht, also "unveraendert" 
								
				assertFalse(objEntry.isDecrypted());
				assertNull(objEntry.getValueDecrypted()); //Merke: sValue kann unterschiedlich zu dem decrypted Wert sein. Wenn etwas drumherum steht.
				
				assertFalse(objEntry.isCall());
				assertFalse(objEntry.isJavaCall());
				assertNull(objEntry.getCallingClassname());
				assertNull(objEntry.getCallingMethodname());
			}
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

			
			bReturn = true;
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}
		return bReturn;
	}

	//########################################################################
	
	private boolean testCompute_FORMULA_MATH_FormulaUnsolved_(String sExpressionSourceIn, String sExpressionSolvedIn, String sTagSolvedIn, boolean bRemoveSurroundingSeparators, IEnumSetMappedTestCaseZZZ objEnumTestCase) {
		boolean bReturn = false;
		try {
			boolean btemp; 
			
			String sExpression; 
			String sExpressionSolved; String sValue; String sValueUsed;				
			ReferenceZZZ<IKernelConfigSectionEntryZZZ> objSectionEntryReference;
			IKernelConfigSectionEntryZZZ objEntry; IKernelConfigSectionEntryZZZ objEntryUsed;
			
			String sTagStartZ = "<Z>";
			String sTagEndZ = "</Z>";	
						
			//####################################################################################
			//### EXPRESSION nicht verarbeiten - FORMULA_MATH 
			//####################################################################################
		
			btemp = objFormulaSolver.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION, true); 
			assertTrue("Flag nicht vorhanden '" + IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
			
			btemp = objFormulaSolver.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER, true);  
			assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
			
			btemp = objFormulaSolver.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA, false); //sollte egal sein
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA + "'", btemp);
			
			
			btemp = objFormulaSolver.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH, true); //sollte egal sein
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH + "'", btemp);
														
									
			//+++ ... parse ist nicht solve... also wird hier nichts aufgeloest, aussser die Pfade
			if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.PARSE)) {
				sExpression = sExpressionSourceIn;
				sExpressionSolved = sExpressionSolvedIn;		
				objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
				sValue = objFormulaSolver.parse(sExpression, objSectionEntryReference, bRemoveSurroundingSeparators);
				assertEquals(sExpressionSolved, sValue);
				
				sExpressionSolved = sExpressionSolvedIn; //der Wert des Tags selbst unterscheidet sich immer vom Wert der Zeile
				sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ, false);
				sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSolved, objFormulaSolver.getName(), false);								
				sValue = objFormulaSolver.getValue();
				assertEquals(sExpressionSolved, sValue);
				
				
				objEntry = objSectionEntryReference.get();
				assertNotNull(objEntry);

				assertTrue(objEntry.isParseCalled());
				
				sExpressionSolved = sExpressionSolvedIn;
				if(sExpression.equals(sExpressionSolved)) {
					assertFalse(objEntry.isParsedChanged());						
				}else {
					assertTrue(objEntry.isParsedChanged());
				}
				
				assertFalse(objEntry.isSolveCalled()); //Der Solve-Schritt wurde gemacht.
				assertFalse(objEntry.isSolvedChanged()); //es wird ja nix gemacht, also "unveraendert" 
				
				
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
				sValue = objFormulaSolver.solve(sExpression, objSectionEntryReference, bRemoveSurroundingSeparators);
				assertEquals(sExpressionSolved, sValue);

				objEntry = objSectionEntryReference.get();
				assertNotNull(objEntry);
				
				assertTrue(objEntry.isParseCalled());
				
				sExpressionSolved = sExpressionSolvedIn;
				if(sExpression.equals(sExpressionSolved)) {
					assertFalse(objEntry.isParsedChanged());						
				}else {
					assertTrue(objEntry.isParsedChanged());
				}
				assertTrue(objEntry.isSolveCalled()); //Der Solve-Schritt wurde gemacht.
				assertFalse(objEntry.isSolvedChanged()); //Ohne Expression Behandlung wird auch nichts geaendert.
				
				
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
				objEntryUsed = objFormulaSolver.parseAsEntry(sExpression, bRemoveSurroundingSeparators);
				assertNotNull(objEntryUsed);
				
				sValue = objEntryUsed.getValue();
				assertEquals(sExpressionSolved, sValue);
				
				assertTrue(objEntryUsed.isParseCalled());
				
				sExpressionSolved = sExpressionSolvedIn;
				if(sExpression.equals(sExpressionSolved)) {
					assertFalse(objEntryUsed.isParsedChanged());						
				}else {
					assertTrue(objEntryUsed.isParsedChanged());
				}
				
				assertFalse(objEntryUsed.isSolveCalled()); //Der Solve-Schritt wurde gemacht.
				assertFalse(objEntryUsed.isSolvedChanged()); //es wird ja nix gemacht, also "unveraendert" 
																
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
				objEntry = objFormulaSolver.solveAsEntry(sExpression, objSectionEntryReference, bRemoveSurroundingSeparators);
				assertNotNull(objEntry);
				
				sValue = objEntry.getValue();
				assertEquals(sExpressionSolved, sValue);
				
				assertTrue(objEntry.isParseCalled());
				
				sExpressionSolved = sExpressionSolvedIn;
				if(sExpression.equals(sExpressionSolved)) {
					assertFalse(objEntry.isParsedChanged());						
				}else {
					assertTrue(objEntry.isParsedChanged());
				}
								
				assertTrue(objEntry.isSolveCalled()); //Der Solve-Schritt wurde gemacht.
				assertFalse(objEntry.isSolvedChanged()); //es wird ja nix gemacht, also "unveraendert" 
								
				assertFalse(objEntry.isDecrypted());
				assertNull(objEntry.getValueDecrypted()); //Merke: sValue kann unterschiedlich zu dem decrypted Wert sein. Wenn etwas drumherum steht.
				
				assertFalse(objEntry.isCall());
				assertFalse(objEntry.isJavaCall());
				assertNull(objEntry.getCallingClassname());
				assertNull(objEntry.getCallingMethodname());
			}
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

			
			bReturn = true;
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}
		return bReturn;
	}


	//########################################################################
	
	private boolean testCompute_FORMULA_MATH_FormulaMathUnsolved_(String sExpressionSourceIn, String sExpressionSolvedIn, String sTagSolvedIn, boolean bRemoveSurroundingSeparators, IEnumSetMappedTestCaseZZZ objEnumTestCase) {
		boolean bReturn = false;
		try {
			boolean btemp; 
			
			String sExpression; 
			String sExpressionSolved; String sValue; String sValueUsed;				
			ReferenceZZZ<IKernelConfigSectionEntryZZZ> objSectionEntryReference;
			IKernelConfigSectionEntryZZZ objEntry; IKernelConfigSectionEntryZZZ objEntryUsed;
			
			String sTagStartZ = "<Z>";
			String sTagEndZ = "</Z>";	
						
			//####################################################################################
			//### EXPRESSION nicht verarbeiten - FORMULA_MATH 
			//####################################################################################
		
			btemp = objFormulaSolver.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION, true); 
			assertTrue("Flag nicht vorhanden '" + IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
			
			btemp = objFormulaSolver.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER, true);  
			assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
			
			btemp = objFormulaSolver.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA, true); //sollte egal sein
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA + "'", btemp);
			
			
			btemp = objFormulaSolver.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH, false); //sollte egal sein
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH + "'", btemp);
														
									
			//+++ ... parse ist nicht solve... also wird hier nichts aufgeloest, aussser die Pfade
			if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.PARSE)) {
				sExpression = sExpressionSourceIn;
				sExpressionSolved = sExpressionSolvedIn;		
				objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
				sValue = objFormulaSolver.parse(sExpression, objSectionEntryReference, bRemoveSurroundingSeparators);
				assertEquals(sExpressionSolved, sValue);
				
				sExpressionSolved = sExpressionSolvedIn; //der Wert des Tags selbst unterscheidet sich immer vom Wert der Zeile
				sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ, false);
				sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSolved, objFormulaSolver.getName(), false);								
				sValue = objFormulaSolver.getValue();
				assertEquals(sExpressionSolved, sValue);
				
				
				objEntry = objSectionEntryReference.get();
				assertNotNull(objEntry);

				assertTrue(objEntry.isParseCalled());
				
				sExpressionSolved = sExpressionSolvedIn;
				if(sExpression.equals(sExpressionSolved)) {
					assertFalse(objEntry.isParsedChanged());						
				}else {
					assertTrue(objEntry.isParsedChanged());
				}
				
				assertFalse(objEntry.isSolveCalled()); //Der Solve-Schritt wurde gemacht.
				assertFalse(objEntry.isSolvedChanged()); //es wird ja nix gemacht, also "unveraendert" 
				
				
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
				sValue = objFormulaSolver.solve(sExpression, objSectionEntryReference, bRemoveSurroundingSeparators);
				assertEquals(sExpressionSolved, sValue);

				objEntry = objSectionEntryReference.get();
				assertNotNull(objEntry);
				
				assertTrue(objEntry.isParseCalled());
				
				sExpressionSolved = sExpressionSolvedIn;
				if(sExpression.equals(sExpressionSolved)) {
					assertFalse(objEntry.isParsedChanged());						
				}else {
					assertTrue(objEntry.isParsedChanged());
				}
				assertTrue(objEntry.isSolveCalled()); //Der Solve-Schritt wurde gemacht.
				assertFalse(objEntry.isSolvedChanged()); //Ohne Expression Behandlung wird auch nichts geaendert.
				
				
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
				objEntryUsed = objFormulaSolver.parseAsEntry(sExpression, bRemoveSurroundingSeparators);
				assertNotNull(objEntryUsed);
				
				sValue = objEntryUsed.getValue();
				assertEquals(sExpressionSolved, sValue);
				
				assertTrue(objEntryUsed.isParseCalled());
				
				sExpressionSolved = sExpressionSolvedIn;
				if(sExpression.equals(sExpressionSolved)) {
					assertFalse(objEntryUsed.isParsedChanged());						
				}else {
					assertTrue(objEntryUsed.isParsedChanged());
				}
				
				assertFalse(objEntryUsed.isSolveCalled()); //Der Solve-Schritt wurde gemacht.
				assertFalse(objEntryUsed.isSolvedChanged()); //es wird ja nix gemacht, also "unveraendert" 
																
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
				objEntry = objFormulaSolver.solveAsEntry(sExpression, objSectionEntryReference, bRemoveSurroundingSeparators);
				assertNotNull(objEntry);
				
				sValue = objEntry.getValue();
				assertEquals(sExpressionSolved, sValue);
				
				assertTrue(objEntry.isParseCalled());
				
				sExpressionSolved = sExpressionSolvedIn;
				if(sExpression.equals(sExpressionSolved)) {
					assertFalse(objEntry.isParsedChanged());						
				}else {
					assertTrue(objEntry.isParsedChanged());
				}
								
				assertTrue(objEntry.isSolveCalled()); //Der Solve-Schritt wurde gemacht.
				assertFalse(objEntry.isSolvedChanged()); //es wird ja nix gemacht, also "unveraendert" 
								
				assertFalse(objEntry.isDecrypted());
				assertNull(objEntry.getValueDecrypted()); //Merke: sValue kann unterschiedlich zu dem decrypted Wert sein. Wenn etwas drumherum steht.
				
				assertFalse(objEntry.isCall());
				assertFalse(objEntry.isJavaCall());
				assertNull(objEntry.getCallingClassname());
				assertNull(objEntry.getCallingMethodname());
			}
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

			
			bReturn = true;
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}
		return bReturn;
	}

	
	//########################################################################

	private boolean testCompute_FORMULA_MATH_(String sExpressionIn, String sExpressionSolvedIn, String sTagSolvedIn, boolean bRemoveSuroundingSeparators, IEnumSetMappedTestCaseZZZ objEnumTestCase) {
		boolean bReturn = false;
		try {
			boolean btemp; 
			
			String sExpression; String sExpressionSolved;  
			String sValue; String sValueUsed; Vector3ZZZ<String> vecValue;		
			ReferenceZZZ<IKernelConfigSectionEntryZZZ> objSectionEntryReference;
			IKernelConfigSectionEntryZZZ objEntry; IKernelConfigSectionEntryZZZ objEntryUsed;
			
			
			//##########################################
			//### Expression solved Fall
			//+++ Wert noch nicht ausrechnen: A			
			btemp = objFormulaSolver.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION, true); 
			assertTrue("Flag nicht vorhanden '" + IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
			
			btemp = objFormulaSolver.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER,true); 
			assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
			
			btemp = objFormulaSolver.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA,true); //Damit der Wert sofort ausgerechnet wird
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA + "'", btemp);
			
			
			btemp = objFormulaSolver.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH,true); //Damit der Wert NICHT sofort ausgerechnet wird
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH + "'", btemp);
														
			sValue = objFormulaSolver.solve(sExpressionIn);
			assertEquals(sExpressionIn, sValue);
			
			
			//+++ ... parse ist nicht solve... also wird hier nichts aufgeloest, aussser die Pfade substituiert.
			if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.PARSE)) {
				sExpression = sExpressionIn;
				sExpressionSolved = sExpressionSolvedIn;		
				
				//+++ Teilberechnungen durchführen.
				//    Es werden wg false die Tags nicht entfernt
				vecValue = objFormulaSolver.parseFirstVector(sExpression, false);					
				sValue = VectorUtilZZZ.implode(vecValue);
				assertEquals(sExpression, sValue); //dann sollen auch die Z-Tags drumherum nicht entfernt werden.
				
				sValue = (String) vecValue.get(1);//in der 0ten Position ist der String vor der Map, in der 3ten Position ist der String nach der Map.
				assertTrue(StringZZZ.contains(sExpressionSolved,sValue,false)); //da der Wert selbst nicht als Argument in der Methode uebergeben wurde, koennen wir nur auf Existenz im Gesamtergebnis pruefen.
			
				//+++ Teilberechnungen durchführen
				//    Es werden nomalerweise die Tags entfernt, bei einem angestellten Solver werden sie beim parsen ignoriert 
				vecValue = objFormulaSolver.parseFirstVector(sExpression, bRemoveSuroundingSeparators);					
				sValue = VectorUtilZZZ.implode(vecValue);

				String sExpressionSolvedTemp = ExpressionIniUtilZZZ.makeAsExpression(sExpressionSolved);//Beim parse fist Vector wird nie der Z-Tag drum herum entfernt. Das ist Aufgabe von parse().
				//sExpressionSolvedTemp = ExpressionIniUtilZZZ.makeAsExpression(sExpressionSolvedTemp, "JSON");
				//sExpressionSolvedTemp = objExpressionSolver.makeAsExpression(sExpressionSolvedTemp);
				assertEquals(sExpressionSolvedTemp, sValue); 
				
				sValue = (String) vecValue.get(1);//in der 0ten Position ist der String vor der Map, in der 3ten Position ist der String nach der Map.
				assertTrue(StringZZZ.contains(sExpressionSolved,sValue,false)); //da der Wert selbst nicht als Argument in der Methode uebergeben wurde, koennen wir nur auf Existenz im Gesamtergebnis pruefen.
				
				//+++ Nun die Gesamtberechnung durchführen
				objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
				sValue = objFormulaSolver.parse(sExpression, objSectionEntryReference, bRemoveSuroundingSeparators);
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
				sExpression = sExpressionIn;
				sExpressionSolved = sExpressionSolvedIn;
				objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
				sValue = objFormulaSolver.solve(sExpression, objSectionEntryReference, bRemoveSuroundingSeparators);
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
				sExpression = sExpressionIn;
				sExpressionSolved = sExpressionSolvedIn;
				objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
				objEntryUsed = objFormulaSolver.parseAsEntry(sExpression, bRemoveSuroundingSeparators);				
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
				sExpression = sExpressionIn;
				sExpressionSolved = sExpressionSolvedIn;				
				objEntryUsed = objFormulaSolver.solveAsEntry(sExpression, bRemoveSuroundingSeparators);
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
	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	
	private boolean testCompute_GetPropertyWithExpression_() {
		boolean btemp; String sValue; String sSection; String sProperty;
		String sExpressionSource; 
		String sExpression; //fuer den RAW Vergleich.
		main:{
			try {				
				sExpressionSource = "Der dynamische Wert ist '<Z>[Section A]Testentry1</Z>'. FGL rulez.";
				sSection = "Section for testCompute";
				sProperty = "Formula1";
				
				//+++ KEINE Expression oder Formel verwenden dann ist die Aufloesung egal
				btemp = objFileIniTest.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION, false);
				assertTrue("Flag nicht vorhanden '" + IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
				
				btemp = objFileIniTest.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER,true);
				assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
				 			
				btemp = objFileIniTest.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH, true); //muss egal sein, da EXPRESIONSOLVER = FALSE
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
				
				btemp = objFileIniTest.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA, false); //eh keine Formel drin, also sollte das egal sein.
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA + "'", btemp);
							
				//Merke: Ohne Expression Behandlund duefen trotz parsen die Z-Tags nicht verschwinden.
				sExpression = sExpressionSource; //"Der dynamische Wert ist '<Z>[Section A]Testentry1</Z>'. FGL rulez.";				
				sValue = objFileIniTest.getPropertyValue(sSection, sProperty).getValue();				
				assertEquals(sExpression, sValue);
				
				
				//+++ Expression oder Formel verwenden, aber keine Aufloesung verwenden, dann wird mit parse() der Z-Tag entfernt.
				btemp = objFileIniTest.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION,true);
				assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
							
				btemp = objFileIniTest.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER,false);
				assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
				 			
				btemp = objFileIniTest.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH, true); //muss egal sein, da EXPRESIONSOLVER = FALSE
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
				 		
				btemp = objFileIniTest.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA, false); 
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA + "'", btemp);
				
				sExpression = "Der dynamische Wert ist '[Section A]Testentry1'. FGL rulez.";
				sValue = objFileIniTest.getPropertyValue(sSection, sProperty).getValue();
				assertEquals(sExpression, sValue);
				 
				
				//+++ Expression verwenden, aber keine Aufloesung verwenden.
				btemp = objFileIniTest.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION,true);
				assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
							
				btemp = objFileIniTest.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER,false);
				assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
				 			
				btemp = objFileIniTest.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH, true); //das ist dann egal, weil USE..._Solver=false
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
				
				btemp = objFileIniTest.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA, true); //eh keine Formel drin, also sollte das egal sein.
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA + "'", btemp);
				
				//sExpression dito
				sValue = objFileIniTest.getPropertyValue(sSection, sProperty).getValue();
				assertEquals(sExpression, sValue);
				
				//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
				//+++ Erst wenn Expression aufgeloest werden sollen, klappts
				btemp = objFileIniTest.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION,true); 
				assertTrue("Flag nicht vorhanden '" + IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);

				//Erst wenn der Parser den Solver nutzen soll, kommt auch das vom solve() raus.
				btemp = objFileIniTest.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER,true);
				assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
				
				//Nun werden die Pfade aufgeloest
				btemp = objFileIniTest.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH,true);
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
				
				btemp = objFileIniTest.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA, false); //eh keine Formel drin, also sollte das egal sein.
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA + "'", btemp);
							
				//!!! Merke: Anders als beim Kernel-Holen der Property, wird beim KernelIniFile kein Cache verwendet.
				//           Darum reicht es die Flagz zu andern und es muss kein Cache erst geleert werden.
				sExpression = "Der dynamische Wert ist 'Testvalue1 to be found'. FGL rulez.";
				sValue = objFileIniTest.getPropertyValue(sSection, sProperty).getValue();
				assertEquals(sExpression, sValue);
				
			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			}
		}//end main:			
		return true;
	}

	private boolean testCompute_GetPropertyWithExpressionCombinedWithSolver_() {
		boolean btemp; String sValue;  String sSection; String sProperty;
		String sExpressionSource; String sExpressionForSolverByProperty; String sExpressionForSolverDirect;
		String sExpression; //fuer den RAW Vergleich.
		main:{
			try {
				//Anwenden der Formel, so dass ein localer Wert vor einem globalen Wert gefunden wird.
				/*
				 * 
				objStreamFile.println("[Section for testCompute]");
				....
				objStreamFile.println("Formula2=Der dynamische Wert2 ist '<Z>[Section B]Testentry2</Z>'. FGL rulez.");
				
				
				
				objStreamFile.println("[Section B!01]");
				objStreamFile.println("Testentry2=Testvalue2 local to be found");
				
				objStreamFile.println("[Section B]");
				objStreamFile.println("Testentry2=Testvalue2 global. This should not be found");
				
				*/
				
				sExpressionSource = "Der dynamische Wert2 ist '<Z>[Section B]Testentry2</Z>'. FGL rulez.";
				sSection = "Section for testCompute";
				sProperty = "Formula2";
				
				//+++ Erst einmal den Wert nur auslesen, nicht parsen oder solven
				btemp = objFileIniTest.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION,false); 
				assertTrue("Flag nicht vorhanden '" + IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
					
				//Erst wenn der Parser den Solver nutzen soll, kommt auch das vom solve() raus.
				btemp = objFileIniTest.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER,true);
				assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
							
				btemp = objFileIniTest.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH,false); //Pfade werden nicht aufgeloest
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
				
				btemp = objFileIniTest.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA, false); //eh keine Formel drin, also sollte das egal sein.
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA + "'", btemp);
				
				//Merke: wg. Parsen sind die Z-Tags verschwunden.
				sExpression = sExpressionSource;
				sValue = objFileIniTest.getPropertyValue(sSection, sProperty).getValue();
				assertEquals(sExpression, sValue);
				sExpressionForSolverDirect = sValue; //fuer spaeter aufheben
				
				//++++++++++++++++++++++++++++
				//Erst einmal soll der Wert nicht sofort ausgerechnet werden.			
				btemp = objFileIniTest.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION,true); 
				assertTrue("Flag nicht vorhanden '" + IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
				
				btemp = objFileIniTest.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER,true);
				assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
									
				//Merke: wg. Parsen sind die Z-Tags verschwunden.
				sExpression = "Der dynamische Wert2 ist '[Section B]Testentry2'. FGL rulez.";
				sValue = objFileIniTest.getPropertyValue(sSection, sProperty).getValue();
				assertEquals(sExpression, sValue);
				sExpressionForSolverByProperty = sValue; //fuer spaeter aufheben			
				
				//+++++++++++++++++++ Arbeite mit dem Ergebnis im Solver weiter
				//Wenn man die Expression_PATH-Behandlung ausstellt, dann werden auch beim "Solven" keine Pfade ersetzt
				btemp = objFormulaSolver.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION,true); 
				assertTrue("Flag nicht vorhanden '" + IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
				
				btemp = objFormulaSolver.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER,true); //false: Damit der Pfad NICHT sofort ausgerechnet wird
				assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
				
				btemp = objFormulaSolver.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH, false); //also nur die Expression aufloesen, aber den Pfad nicht aufloesen.
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
				
				btemp = objFormulaSolver.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA, true);
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA + "'", btemp);
				
				sExpression = "Der dynamische Wert2 ist '[Section B]Testentry2'. FGL rulez."; 				//Weil oben die Z-Tags raus sind, bleiben Sie auch hier weg
				sValue = objFormulaSolver.solve(sExpressionForSolverByProperty);
				assertEquals(sExpression, sValue);
				
				//Wenn man die Expression_PATH-Behandlung anstellt, dann findet die Ersetzung statt.
				btemp = objFormulaSolver.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH, true);
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
				
				//Test: Wert mit Systemnr soll vor dem globalen Wert gefunden werden!!!
				//Weil oben die Z-Tags raus sind, findet keine Ersetzung statt
				//dito sExpression = "Der dynamische Wert2 ist '[Section B]Testentry2'. FGL rulez.";
				sValue = objFormulaSolver.solve(sExpressionForSolverByProperty);
				assertEquals(sExpression, sValue);
				
				//Zum Vergleich, String mit Z-Tags
				sExpression = "Der dynamische Wert2 ist '<Z>Testvalue2 local to be found</Z>'. FGL rulez.";
				sValue = objFormulaSolver.solve(sExpressionForSolverDirect);				
				assertEquals(sExpression, sValue);
				
			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			}
		}//end main:			
		return true;
	}
	
	private boolean testCompute_GetPropertyWithFormulaMath_() {
		boolean btemp; String sValue; String sSection; String sProperty;
		String sExpressionForSolverByProperty; String sExpressionForSolverDirect;		
		String sExpression;
		String sExpressionSource;
		main:{
			try {
																		
				//####################################################################################
				//### Formel mit MULTIPLIKATIONS Operator
				//####################################################################################
				
				sExpressionSource="Der dynamische Wert ist '<Z><Z:math><Z:val>2</Z:val><Z:op>*</Z:op><Z:val>3</Z:val></Z:math></Z>'. FGL rulez.";
				sSection = "Section for testComputeMath";
				sProperty = "Formula1";
				
				//+++ KEINE Expression oder Formel verwenden dann ist die Aufloesung egal
				btemp = objFileIniTest.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION, false);
				assertTrue("Flag nicht vorhanden '" + IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
				
				btemp = objFileIniTest.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER,true); //muss egal sein, da EXPRESION = FALSE
				assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
				 			
				btemp = objFileIniTest.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH, true); //muss egal sein, da EXPRESION = FALSE //muss egal sein, wenn EXPRESIONSOLVER = FALSE
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
				
				btemp = objFileIniTest.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA, true); //muss egal sein, da EXPRESION = FALSE //muss egal sein, wenn EXPRESIONSOLVER = FALSE
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA + "'", btemp);
											
				btemp = objFileIniTest.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH, true); //muss egal sein, da EXPRESION = FALSE //muss egal sein, wenn EXPRESIONSOLVER = FALSE
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA + "'", btemp);
							
				sExpression = sExpressionSource;
				sValue = objFileIniTest.getPropertyValue(sSection, sProperty).getValue();
				assertEquals(sExpression, sValue);
				
				//+++ Expression oder Formel verwenden, aber keine Aufloesung (auch nicht des Pfades) verwenden, dann wird mit parse() lediglich der Z-Tag entfernt.
				btemp = objFileIniTest.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION,true);
				assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
							
				btemp = objFileIniTest.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER,false);
				assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
				 			
				sExpression = "Der dynamische Wert ist '<Z:math><Z:val>2</Z:val><Z:op>*</Z:op><Z:val>3</Z:val></Z:math>'. FGL rulez.";
				sValue = objFileIniTest.getPropertyValue(sSection, sProperty).getValue();
				assertEquals(sExpression, sValue);
				
				
				//+++ Expression oder Formel verwenden UND Aufloesung verwenden, ABER noch keine Aufloesung des Pfades, dann wird mit parse() lediglich der Z-Tag entfernt und der unaufgeloeste Pfadausdruck steht dort.
				//    Die Formel soll noch nicht ausgerechnet werden.
				btemp = objFileIniTest.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER,true);
				assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
				 			
				btemp = objFileIniTest.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH, false); //Jetzt nicht mehr egal, da der Solver ausgefuehrt wird.
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
				 		
				btemp = objFileIniTest.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA, false); //Jetzt nicht mehr egal, da der Solver ausgefuehrt wird.
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA + "'", btemp);
								
				//... dito zu vorherigem lauf.
				sValue = objFileIniTest.getPropertyValue(sSection, sProperty).getValue();
				assertEquals(sExpression, sValue);
				
				
				//+++ Expression oder Formel verwenden UND Aufloesung verwenden UND Aufloesung des Pfades, dann wird mit parse() lediglich der Z-Tag entfernt und der aufgeloeste Pfadausdruck steht dort.
				//    Die Formel soll noch nicht ausgerechnet werden.
				btemp = objFileIniTest.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH, true);
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
								
				sExpression = "Der dynamische Wert ist '<Z:math><Z:val>2</Z:val><Z:op>*</Z:op><Z:val>3</Z:val></Z:math>'. FGL rulez.";
				sValue = objFileIniTest.getPropertyValue(sSection, sProperty).getValue();
				assertEquals(sExpression, sValue);
								
				
				//+++ ausrechnen
				//    aber immer noch nicht die Formel
				btemp = objFileIniTest.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA, true); //Jetzt nicht mehr egal, da der Solver ausgefuehrt wird.
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA + "'", btemp);
				
				btemp = objFileIniTest.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH, false);
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH + "'", btemp);
				
				//... dito zu vorherigem lauf.
				sValue = objFileIniTest.getPropertyValue("Section for testComputeMath", "Formula1").getValue();
				assertEquals(sExpression, sValue);
				
				
				//+++ Formel ausrechnen
				btemp = objFileIniTest.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH, true);
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH + "'", btemp);
				
				sExpression = "Der dynamische Wert ist '6'. FGL rulez.";;
				sValue = objFileIniTest.getPropertyValue(sSection, sProperty).getValue();
				assertTrue("Im Ergebnis wurde eine ausgerechnete '6' erwartet.", StringZZZ.contains(sValue, "6"));							
				assertEquals(sExpression, sValue);
				
				
				//####################################################################################
				//### Formel mit PLUS Operator - Tags in unterschiedlichsten Schreibweisen.
				//####################################################################################
				
				sExpressionSource="Der dynamische Wert ist '<Z><z:Math><Z:VAL>6</Z:val><Z:oP>+</Z:op><Z:val>7</Z:val></Z:math></Z>'. FGL rulez.";
				sSection = "Section for testComputeMath NOT EXACTMATCH";
				sProperty = "Formula1";
				
				//+++ KEINE Expression oder Formel verwenden dann ist die Aufloesung egal
				btemp = objFileIniTest.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION, false);
				assertTrue("Flag nicht vorhanden '" + IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
				
				btemp = objFileIniTest.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER,true); //muss egal sein, da EXPRESION = FALSE
				assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
				 			
				btemp = objFileIniTest.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH, true); //muss egal sein, da EXPRESION = FALSE //muss egal sein, wenn EXPRESIONSOLVER = FALSE
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
				
				btemp = objFileIniTest.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA, true); //muss egal sein, da EXPRESION = FALSE //muss egal sein, wenn EXPRESIONSOLVER = FALSE
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA + "'", btemp);
											
				btemp = objFileIniTest.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH, true); //muss egal sein, da EXPRESION = FALSE //muss egal sein, wenn EXPRESIONSOLVER = FALSE
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA + "'", btemp);
							
				sExpression = sExpressionSource;
				sValue = objFileIniTest.getPropertyValue(sSection, sProperty).getValue();
				assertEquals(sExpression, sValue);
				
				//+++ Expression oder Formel verwenden, aber keine Aufloesung (auch nicht des Pfades) verwenden, dann wird mit parse() lediglich der Z-Tag entfernt.
				btemp = objFileIniTest.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION,true);
				assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
							
				btemp = objFileIniTest.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER,false);
				assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
				
				sExpression = "Der dynamische Wert ist '<z:Math><Z:VAL>6</Z:val><Z:oP>+</Z:op><Z:val>7</Z:val></Z:math>'. FGL rulez.";
				sValue = objFileIniTest.getPropertyValue(sSection, sProperty).getValue();
				assertEquals(sExpression, sValue);
				
				
				//+++ Expression oder Formel verwenden UND Aufloesung verwenden, ABER noch keine Aufloesung des Pfades, dann wird mit parse() lediglich der Z-Tag entfernt und der unaufgeloeste Pfadausdruck steht dort.
				//    Die Formel soll noch nicht ausgerechnet werden.
				btemp = objFileIniTest.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER,true);
				assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
				 			
				btemp = objFileIniTest.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH, false); //Jetzt nicht mehr egal, da der Solver ausgefuehrt wird.
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
				 		
				btemp = objFileIniTest.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA, false); //Jetzt nicht mehr egal, da der Solver ausgefuehrt wird.
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA + "'", btemp);
								
				//... dito zu vorherigem lauf.
				sValue = objFileIniTest.getPropertyValue(sSection, sProperty).getValue();
				assertEquals(sExpression, sValue);
				
				
				//+++ Expression oder Formel verwenden UND Aufloesung verwenden UND Aufloesung des Pfades, dann wird mit parse() lediglich der Z-Tag entfernt und der aufgeloeste Pfadausdruck steht dort.
				//    Die Formel soll noch nicht ausgerechnet werden.
				btemp = objFileIniTest.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH, true);
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
								
				//... dito zu vorherigem lauf.				
				sValue = objFileIniTest.getPropertyValue(sSection, sProperty).getValue();
				assertEquals(sExpression, sValue);
								
				
				//+++ ausrechnen
				//    aber immer noch nicht die Formel
				btemp = objFileIniTest.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA, true); //Jetzt nicht mehr egal, da der Solver ausgefuehrt wird.
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA + "'", btemp);
				
				btemp = objFileIniTest.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH, false);
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH + "'", btemp);
				
				//... dito zu vorherigem lauf.
				sValue = objFileIniTest.getPropertyValue(sSection, sProperty).getValue();
				assertEquals(sExpression, sValue);
				
				
				//+++ Formel ausrechnen
				btemp = objFileIniTest.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH, true);
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH + "'", btemp);
				
				sExpression = "Der dynamische Wert ist '13'. FGL rulez.";
				sValue = objFileIniTest.getPropertyValue(sSection, sProperty).getValue();
				assertTrue("Im Ergebnis wurde eine ausgerechnete '13' erwartet.", StringZZZ.contains(sExpression, "13"));							
				assertEquals(sExpression, sValue);
				
				
				//####################################################################################
				//### Formel ohne Operator - ist einfache Stringzusammenfassung
				//####################################################################################
				
				sExpressionSource="Der dynamische Wert ist '<Z><Z:math><Z:val>2</Z:val><Z:val>3</Z:val></Z:math></Z>'. FGL rulez.";
				sSection = "Section for testComputeMathValue";
				sProperty = "Formula1";
				
				//+++ KEINE Expression oder Formel verwenden dann ist die Aufloesung egal
				btemp = objFileIniTest.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION, false);
				assertTrue("Flag nicht vorhanden '" + IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
				
				btemp = objFileIniTest.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER,true); //muss egal sein, da EXPRESION = FALSE
				assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
				 			
				btemp = objFileIniTest.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH, true); //muss egal sein, da EXPRESION = FALSE //muss egal sein, wenn EXPRESIONSOLVER = FALSE
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
				
				btemp = objFileIniTest.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA, true); //muss egal sein, da EXPRESION = FALSE //muss egal sein, wenn EXPRESIONSOLVER = FALSE
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA + "'", btemp);
											
				btemp = objFileIniTest.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH, true); //muss egal sein, da EXPRESION = FALSE //muss egal sein, wenn EXPRESIONSOLVER = FALSE
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA + "'", btemp);
							
				sExpression = sExpressionSource;
				sValue = objFileIniTest.getPropertyValue(sSection, sProperty).getValue();
				assertEquals(sExpression, sValue);
				
				//+++ Expression oder Formel verwenden, aber keine Aufloesung (auch nicht des Pfades) verwenden, dann wird mit parse() lediglich der Z-Tag entfernt.
				btemp = objFileIniTest.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION,true);
				assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
							
				btemp = objFileIniTest.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER,false);
				assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
												
				sExpression = "Der dynamische Wert ist '<Z:math><Z:val>2</Z:val><Z:val>3</Z:val></Z:math>'. FGL rulez.";
				sValue = objFileIniTest.getPropertyValue(sSection, sProperty).getValue();
				assertEquals(sExpression, sValue);
				
				
				//+++ Expression oder Formel verwenden UND Aufloesung verwenden, ABER noch keine Aufloesung des Pfades, dann wird mit parse() lediglich der Z-Tag entfernt und der unaufgeloeste Pfadausdruck steht dort.
				//    Die Formel soll noch nicht ausgerechnet werden.
				btemp = objFileIniTest.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER,true);
				assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
				 			
				btemp = objFileIniTest.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH, false); //Jetzt nicht mehr egal, da der Solver ausgefuehrt wird.
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
				 		
				btemp = objFileIniTest.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA, false); //Jetzt nicht mehr egal, da der Solver ausgefuehrt wird.
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA + "'", btemp);
								
				//... dito zu vorherigem lauf.
				sValue = objFileIniTest.getPropertyValue(sSection, sProperty).getValue();
				assertEquals(sExpression, sValue);
				
				
				//+++ Expression oder Formel verwenden UND Aufloesung verwenden UND Aufloesung des Pfades, dann wird mit parse() lediglich der Z-Tag entfernt und der aufgeloeste Pfadausdruck steht dort.
				//    Die Formel soll noch nicht ausgerechnet werden.
				btemp = objFileIniTest.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH, true);
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
								
				//... dito zu vorherigem lauf.				
				sValue = objFileIniTest.getPropertyValue(sSection, sProperty).getValue();
				assertEquals(sExpression, sValue);
								
				
				//+++ ausrechnen
				//    aber immer noch nicht die Formel
				btemp = objFileIniTest.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA, true); //Jetzt nicht mehr egal, da der Solver ausgefuehrt wird.
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA + "'", btemp);
				
				btemp = objFileIniTest.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH, false);
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH + "'", btemp);
				
				//... dito zu vorherigem lauf.
				sValue = objFileIniTest.getPropertyValue(sSection, sProperty).getValue();
				assertEquals(sExpression, sValue);
				
				
				//+++ Formel ausrechnen
				btemp = objFileIniTest.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH, true);
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH + "'", btemp);
				
				sExpression = "Der dynamische Wert ist '23'. FGL rulez.";;
				sValue = objFileIniTest.getPropertyValue(sSection, sProperty).getValue();
				assertTrue("Im Ergebnis '" + sExpression + "' wurde eine zusammengesetzte '23' erwartet.", StringZZZ.contains(sExpression, "23"));							
				assertEquals(sExpression, sValue);			
				
			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			}
		}//end main:			
		return true;
	}
	
	private boolean testCompute_GetPropertyWithFormulaMathByPath_() {
		boolean btemp; String sValue; String sExpression; String sExpressionSource; String sSection; String sProperty;
		main:{
			try {

				//####################################################################################
				//### Formel zur Berechnung mit FLOAT Werten
				//####################################################################################
							
				sExpressionSource="Der dynamische Wert ist '<Z><z:Math><Z:VAL>[Section for testComputeMathArguments FLOAT]WertA_float</Z:val><Z:oP>*</Z:op><Z:val>[Section for testComputeMathArguments FLOAT]WertB_float</Z:val></Z:math></Z>'. FGL rulez.";
				sSection = "Section for testComputeMath FLOAT";
				sProperty = "Formula1";
				
				//+++ KEINE Expression oder Formel verwenden dann ist die Aufloesung egal
				btemp = objFileIniTest.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION, false);
				assertTrue("Flag nicht vorhanden '" + IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
				
				btemp = objFileIniTest.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER,true); //muss egal sein, da EXPRESION = FALSE
				assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
				 			
				btemp = objFileIniTest.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH, true); //muss egal sein, da EXPRESION = FALSE //muss egal sein, wenn EXPRESIONSOLVER = FALSE
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
				
				btemp = objFileIniTest.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA, true); //muss egal sein, da EXPRESION = FALSE //muss egal sein, wenn EXPRESIONSOLVER = FALSE
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA + "'", btemp);
											
				btemp = objFileIniTest.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH, true); //muss egal sein, da EXPRESION = FALSE //muss egal sein, wenn EXPRESIONSOLVER = FALSE
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA + "'", btemp);
							
				sExpression = sExpressionSource;
				sValue = objFileIniTest.getPropertyValue(sSection, sProperty).getValue();
				assertEquals(sExpression, sValue);
				
				//+++ Expression oder Formel verwenden, aber keine Aufloesung (auch nicht des Pfades) verwenden, dann wird mit parse() lediglich der Z-Tag entfernt.
				btemp = objFileIniTest.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION,true);
				assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
							
				btemp = objFileIniTest.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER,false);
				assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
				 			
				sExpression = "Der dynamische Wert ist '<z:Math><Z:VAL>[Section for testComputeMathArguments FLOAT]WertA_float</Z:val><Z:oP>*</Z:op><Z:val>[Section for testComputeMathArguments FLOAT]WertB_float</Z:val></Z:math>'. FGL rulez.";
				sValue = objFileIniTest.getPropertyValue(sSection, sProperty).getValue();
				assertEquals(sExpression, sValue);
				
				
				//+++ Expression oder Formel verwenden UND Aufloesung verwenden, ABER noch keine Aufloesung des Pfades, dann wird mit parse() lediglich der Z-Tag entfernt und der unaufgeloeste Pfadausdruck steht dort.
				//    Die Formel soll noch nicht ausgerechnet werden.
				btemp = objFileIniTest.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER,true);
				assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
				 			
				btemp = objFileIniTest.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH, false); //Jetzt nicht mehr egal, da der Solver ausgefuehrt wird.
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
				 		
				btemp = objFileIniTest.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA, false); //Jetzt nicht mehr egal, da der Solver ausgefuehrt wird.
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA + "'", btemp);
								
				//... dito zu vorherigem lauf.
				sValue = objFileIniTest.getPropertyValue(sSection, sProperty).getValue();
				assertEquals(sExpression, sValue);
				
				
				//+++ Expression oder Formel verwenden UND Aufloesung verwenden UND Aufloesung des Pfades, dann wird mit parse() lediglich der Z-Tag entfernt und der aufgeloeste Pfadausdruck steht dort.
				//    Die Formel soll noch nicht ausgerechnet werden.
				btemp = objFileIniTest.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH, true);
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
								
				sExpression = "Der dynamische Wert ist '<z:Math><Z:VAL>4.0</Z:val><Z:oP>*</Z:op><Z:val>5.0</Z:val></Z:math>'. FGL rulez.";
				sValue = objFileIniTest.getPropertyValue(sSection, sProperty).getValue();
				assertEquals(sExpression, sValue);
								
				
				//+++ ausrechnen
				//    aber immer noch nicht die Formel
				btemp = objFileIniTest.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA, true); //Jetzt nicht mehr egal, da der Solver ausgefuehrt wird.
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA + "'", btemp);
				
				btemp = objFileIniTest.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH, false);
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH + "'", btemp);
				
				//... dito zu vorherigem lauf.
				sValue = objFileIniTest.getPropertyValue(sSection, sProperty).getValue();
				assertEquals(sExpression, sValue);
				
				
				//+++ Formel ausrechnen
				btemp = objFileIniTest.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH, true);
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH + "'", btemp);
				
				sExpression = "Der dynamische Wert ist '20.0'. FGL rulez.";;
				sValue = objFileIniTest.getPropertyValue(sSection, sProperty).getValue();
				assertTrue("Im Ergebnis wurde eine ausgerechnete '20.0' erwartet.", StringZZZ.contains(sExpression, "20.0"));							
				assertEquals(sExpression, sValue);
				
	
				
				//###############################################################################
				//### Formeln ueber Pfadaufloesung holen
				//###############################################################################

				sExpressionSource = "<Z>[Section for testComputeMath]Formula1</Z>";//String sExpressionSource="Der dynamische Wert ist '<Z><Z:math><Z:val>2</Z:val><Z:op>*</Z:op><Z:val>3</Z:val></Z:math></Z>'. FGL rulez.";
				sSection = "Section for testComputePathWithMath";
				sProperty = "Formula1";
				
				//+++ KEINE Expression oder Formel verwenden dann ist die Aufloesung egal
				btemp = objFileIniTest.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION, false);
				assertTrue("Flag nicht vorhanden '" + IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
				
				btemp = objFileIniTest.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER,true); //muss egal sein, da EXPRESION = FALSE
				assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
				 			
				btemp = objFileIniTest.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH, true); //muss egal sein, da EXPRESION = FALSE //muss egal sein, wenn EXPRESIONSOLVER = FALSE
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
				
				btemp = objFileIniTest.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA, true); //muss egal sein, da EXPRESION = FALSE //muss egal sein, wenn EXPRESIONSOLVER = FALSE
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA + "'", btemp);
											
				btemp = objFileIniTest.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH, true); //muss egal sein, da EXPRESION = FALSE //muss egal sein, wenn EXPRESIONSOLVER = FALSE
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA + "'", btemp);
							
				sExpression = sExpressionSource;
				sValue = objFileIniTest.getPropertyValue(sSection, sProperty).getValue();
				assertEquals(sExpression, sValue);
				
				//+++ Expression oder Formel verwenden, aber keine Aufloesung (auch nicht des Pfades) verwenden, dann wird mit parse() lediglich der Z-Tag entfernt.
				btemp = objFileIniTest.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION,true);
				assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
							
				btemp = objFileIniTest.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER,false);
				assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
												
				sExpression = "[Section for testComputeMath]Formula1";
				sValue = objFileIniTest.getPropertyValue(sSection, sProperty).getValue();
				assertEquals(sExpression, sValue);
				
				
				//+++ Expression oder Formel verwenden UND Aufloesung verwenden, ABER noch keine Aufloesung des Pfades, dann wird mit parse() lediglich der Z-Tag entfernt und der unaufgeloeste Pfadausdruck steht dort.
				//    Die Formel soll noch nicht ausgerechnet werden.
				btemp = objFileIniTest.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER,true);
				assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
				 			
				btemp = objFileIniTest.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH, false); //Jetzt nicht mehr egal, da der Solver ausgefuehrt wird.
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
				 		
				btemp = objFileIniTest.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA, false); //Jetzt nicht mehr egal, da der Solver ausgefuehrt wird.
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA + "'", btemp);
								
				//... dito zu vorherigem lauf.
				sValue = objFileIniTest.getPropertyValue(sSection, sProperty).getValue();
				assertEquals(sExpression, sValue);
				
				
				//+++ Expression oder Formel verwenden UND Aufloesung verwenden UND Aufloesung des Pfades, dann wird mit parse() lediglich der Z-Tag entfernt und der aufgeloeste Pfadausdruck steht dort.
				//    Die Formel soll noch nicht ausgerechnet werden.
				btemp = objFileIniTest.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH, true);
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
								
				sExpression = "Der dynamische Wert ist '<Z:math><Z:val>2</Z:val><Z:op>*</Z:op><Z:val>3</Z:val></Z:math>'. FGL rulez.";				
				sValue = objFileIniTest.getPropertyValue(sSection, sProperty).getValue();
				assertEquals(sExpression, sValue);
								
				
				//+++ ausrechnen
				//    aber immer noch nicht die Formel
				btemp = objFileIniTest.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA, true); //Jetzt nicht mehr egal, da der Solver ausgefuehrt wird.
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA + "'", btemp);
				
				btemp = objFileIniTest.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH, false);
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH + "'", btemp);
				
				//... dito zu vorherigem lauf.
				sValue = objFileIniTest.getPropertyValue(sSection, sProperty).getValue();
				assertEquals(sExpression, sValue);
				
				
				//+++ Formel ausrechnen
				btemp = objFileIniTest.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH, true);
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH + "'", btemp);
				
				sExpression = "Der dynamische Wert ist '6'. FGL rulez.";;
				sValue = objFileIniTest.getPropertyValue(sSection, sProperty).getValue();
				assertTrue("Im Ergebnis wurde eine ausgerechnete '6' erwartet.", StringZZZ.contains(sValue, "6"));							
				assertEquals(sExpression, sValue);
				
			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			}
		}//end main:			
		return true;
	}
							
	
	public void testCompute_GetPropertyWithVariables() {
		boolean btemp; String sValue; String sSection; String sProperty;
		String sExpressionForSolverByProperty; String sExpressionForSolverDirect;		
		String sExpression;
		String sExpressionSource;
		
		try {
			//####################################################################################
			//### Expression mit Variablen
			//####################################################################################
			
			sExpressionSource="<Z>Der dynamische Wert ist '<z:Var>myTestVariableString</z:Var>'. FGL rulez.</Z>";
			sSection = "Section for testPassVariable";
			sProperty = "Formula1";
			
			//+++ Mit Einstellung diese nicht aufzuloesen
			btemp = objFormulaSolver.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION,true); 
			assertTrue("Flag nicht vorhanden '" + IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
						
			//Nun werden die Pfade aufgeloest
			btemp = objFormulaSolver.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH,false);
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
			
			//Erst wenn der Parser den Solver nutzen soll, kommt auch das vom solve() raus.
			btemp = objFormulaSolver.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER,false);
			assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);

			btemp = objFormulaSolver.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA, true); //eh keine Formel drin, also sollte das egal sein.
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA + "'", btemp);
						
			sExpression = sExpressionSource;
			sValue = objFileIniTest.getPropertyValue(sSection, sProperty).getValue(); //Wenn noch keine Formelvariable gesetzt ist...			
			assertEquals(sExpression, sValue);
			 
			//+++ Mit Einstellung diese aufzuloesen
			btemp = objFileIniTest.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION,true); 
			assertTrue("Flag nicht vorhanden '" + IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
									
			//Nun werden die Pfade aufgeloest
			btemp = objFileIniTest.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH,false); //ist eh kein Pfad drin
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
			
			//Nun werden Variablen ersetzt			
			btemp = objFileIniTest.setFlag(IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE,true);
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE + "'", btemp);
			
			//Erst wenn der Parser den Solver nutzen soll, kommt auch das vom solve() raus.
			btemp = objFileIniTest.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER,true);
			assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);

			btemp = objFileIniTest.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA, true); //eh keine Formel drin, also sollte das egal sein.
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA + "'", btemp);
						
			
			//TODOGOON20240830;//z-Var Tag muss raus
			sExpression="Der dynamische Wert ist 'mySolvedTestVariableString'. FGL rulez."; //Also der Wert mit einer uebergebenen Variablen ausgerechnet.
			sValue = objFileIniTest.getPropertyValue(sSection, sProperty).getValue(); //Wenn noch keine Formelvariable gesetzt ist...
			assertTrue("Im Ergebnis wurde eine ausgerechnete 'mySolvedTestVariableString' erwartet.", StringZZZ.contains(sExpression, "mySolvedTestVariableString"));
			assertEquals(sExpression, sValue);
			 						
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}
	
	
	
	public void testCompute_VariablesCascaded() {
		boolean btemp; String sSection; String sProperty; String sValue;
		String sExpressionSource; String sExpression;
		try {
			//###############################################	
			//### Einbinden der Variablen UND Pade in Math-Ausdrücke
			//###############################################
			sSection = "Section for testPassVariable";
			sProperty = "Formula2";
			sExpressionSource = "Der dynamische Wert ist '<Z><z:Math><Z:VAL>[Section for testComputeMathArguments FLOAT]WertA_float</Z:val><Z:oP>*</Z:op><Z:val><Z:Var>myTestVariableFloat</z:Var></Z:val></Z:math></Z>'. FGL rulez.";
			
			btemp = objFileIniTest.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION, true); 
			assertTrue("Flag nicht vorhanden '" + IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
			
			btemp = objFileIniTest.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER,true); 
			assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
			
			//Nun erst werden Pfade ersetzt
			btemp = objFileIniTest.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH, true); //muss egal sein, da EXPRESION = FALSE //muss egal sein, wenn EXPRESIONSOLVER = FALSE
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
						
			//Nun erst werden Variablen ersetzt			
			btemp = objFileIniTest.setFlag(IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE,true);
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE + "'", btemp);
			
			btemp = objFileIniTest.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA,true); //Damit der Wert sofort ausgerechnet wird
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA + "'", btemp);
			
			
			btemp = objFileIniTest.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH, true); //Damit der Wert NICHT sofort ausgerechnet wird
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH + "'", btemp);
			
			//TODOGOON20240926;//JUnit schlaegt fehl
			HashMapCaseInsensitiveZZZ<String,String> hmVariable = new HashMapCaseInsensitiveZZZ<String,String>();
			hmVariable.put("myTestVariableString","Test erfolgreich");
			hmVariable.put("myTestVariableFloat","2.5");
			objFileIniTest.setHashMapVariable(hmVariable);
			sValue = objFileIniTest.getPropertyValue(sSection, sProperty).getValue(); //Wenn noch keine Formelvariable gesetzt ist...
			sExpression="Der dynamische Wert ist '10.0'. FGL rulez."; //Also der Wert ohne die Math auszurechnen.
			assertTrue("Im Ergebnis wurde eine ausgerechnete '10.0' erwartet.", StringZZZ.contains(sValue, "10.0"));
			assertEquals(sExpression, sValue);
			

		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}
	
	
	public void testCompute_VariablesCascaded_DifferentPath() {
		boolean btemp; String sSection; String sProperty; String sValue;
		String sExpressionSource; String sExpression;
		try {
			//###############################################	
			//### Einbinden der Variablen in Math-Ausdrücke
			//###############################################
			sSection = "Section for testPassVariable";
			sProperty = "Formula3";
			sExpressionSource = "Der dynamische Wert ist '<Z><z:Math><Z:VAL>[Section for testComputeMathArguments FLOAT]WertA_float</Z:val><Z:oP>*</Z:op><Z:val>[Section for testComputeMathVariable FLOAT]WertB_float</Z:val></Z:math></Z>'. FGL rulez.";
			
			btemp = objFileIniTest.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION, true); 
			assertTrue("Flag nicht vorhanden '" + IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
			
			btemp = objFileIniTest.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER,true); 
			assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
			
			//Nun erst werden Pfade ersetzt
			btemp = objFileIniTest.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH, true); //muss egal sein, da EXPRESION = FALSE //muss egal sein, wenn EXPRESIONSOLVER = FALSE
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
						
			//Nun erst werden Variablen ersetzt			
			btemp = objFileIniTest.setFlag(IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE,true);
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE + "'", btemp);
			
			btemp = objFileIniTest.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA,true); //Damit der Wert sofort ausgerechnet wird
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA + "'", btemp);
			
			
			btemp = objFileIniTest.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH, true); //Damit der Wert NICHT sofort ausgerechnet wird
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH + "'", btemp);
						
			//Verschachtelung. D.h Formel arbeitet mit Variablen, die in einem anderen INI-Pfad liegt.
			HashMapCaseInsensitiveZZZ<String,String> hmVariable02 = new HashMapCaseInsensitiveZZZ<String,String>();
			hmVariable02.put("myTestVariableString","Test erfolgreich");
			hmVariable02.put("myTestVariableFloat","3.0");
			objFileIniTest.setHashMapVariable(hmVariable02);
			sValue = objFileIniTest.getPropertyValue(sSection, sProperty).getValue(); //Wenn noch keine Formelvariable gesetzt ist...
			sExpression="Der dynamische Wert ist '12.0'. FGL rulez."; //Also der Wert ohne die Math auszurechnen.
			assertTrue("Im Ergebnis wurde eine ausgerechnete '12.0' erwartet.", StringZZZ.contains(sValue, "12.0"));
			assertEquals(sExpression, sValue);

		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}
	
	public void testCompute_Math_Cascaded() {
		boolean btemp; String sSection; String sProperty; String sValue;
		String sExpressionSource; String sExpression;
		try {
			//###############################################	
			//### Einbinden der Variablen UND Pade in Math-Ausdrücke
			//###############################################
			sSection = "Section for testComputePathWithMath";
			sProperty = "Formula1";
			sExpressionSource = "<Z>[Section for testComputeMath]Formula1</Z>";
			
			btemp = objFileIniTest.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION, true); 
			assertTrue("Flag nicht vorhanden '" + IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
			
			btemp = objFileIniTest.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER,true); 
			assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
			
			//Nun erst werden Pfade ersetzt
			btemp = objFileIniTest.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH, true); //muss egal sein, da EXPRESION = FALSE //muss egal sein, wenn EXPRESIONSOLVER = FALSE
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
						
			//Nun erst werden Variablen ersetzt			
			btemp = objFileIniTest.setFlag(IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE,true);
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE + "'", btemp);
			
			btemp = objFileIniTest.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA,true); //Damit der Wert sofort ausgerechnet wird
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA + "'", btemp);
			
			
			btemp = objFileIniTest.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH, true); //Damit der Wert NICHT sofort ausgerechnet wird
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH + "'", btemp);
			

			sExpression="Der dynamische Wert ist '6'. FGL rulez."; //Also der Wert ohne die Math auszurechnen.
			sValue = objFileIniTest.getPropertyValue(sSection, sProperty).getValue();
			assertEquals(sExpression, sValue);
			assertTrue("Im Ergebnis wurde eine ausgerechnete '6' erwartet.", StringZZZ.contains(sValue, "6"));
			
		
			//+++ Verschachtelt, hier wird auf eine Section Verwiesen, die einen Math-Ausdruck enthält, der wiederum eine IniPath-Anweisung hat.
			sExpressionSource = "<Z>[Section for testComputeMath]Formula2</Z>";
			sSection = "Section for testComputePathWithMath";
			sProperty = "Formula2";
			
			sExpression="Der dynamische Wert2 ist '8'. FGL rulez."; //Also der Wert ohne die Math auszurechnen.
			sValue = objFileIniTest.getPropertyValue(sSection, sProperty).getValue();			
			assertEquals(sExpression, sValue);
			assertTrue("Im Ergebnis wurde eine ausgerechnete '8' erwartet.", StringZZZ.contains(sExpression, "8"));
			
			//+++ Verschachtelt, hier wird auf eine Section Verwiesen, die einen Math-Ausdruck enthält, der wiederum eine IniPath-Anweisung hat.... diesmal 2 Path Anweisungen!!!
			sExpressionSource = "<Z>[Section for testComputeMath]Formula3</Z>";
			sSection = "Section for testComputePathWithMath";
			sProperty = "Formula3";
			
			sExpression="Der dynamische Wert3 ist '20'. FGL rulez."; //Also der Wert ohne die Math auszurechnen.
			sValue = objFileIniTest.getPropertyValue(sSection, sProperty).getValue();
			assertEquals(sExpression, sValue);
			assertTrue("Im Ergebnis wurde eine ausgerechnete '20' erwartet.", StringZZZ.contains(sExpression, "20"));
			
			
		}catch(ExceptionZZZ ez){
			fail("An exception happend testing: " + ez.getDetailAllLast());
		}
			
	}

	
	public void testComputeEncryption() {	
		boolean btemp; String sSection; String sProperty; String sValue;
		String sExpressionSource; String sExpression;
		
		try {
			//###############################################	
			//### Verschluesselten Ausdruck aus INI-Datei lesen
			//###############################################			
			btemp = objFileIniTest.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION, true); 
			assertTrue("Flag nicht vorhanden '" + IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
			
			btemp = objFileIniTest.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER,true); 
			assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
			
			//Nun erst werden Pfade ersetzt
			btemp = objFileIniTest.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH, true); //muss egal sein, da EXPRESION = FALSE //muss egal sein, wenn EXPRESIONSOLVER = FALSE
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
						
			//Nun erst werden Variablen ersetzt			
			btemp = objFileIniTest.setFlag(IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE,true);
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE + "'", btemp);
			
			btemp = objFileIniTest.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA,true); //Damit der Wert sofort ausgerechnet wird
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA + "'", btemp);
			
			
			btemp = objFileIniTest.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH, true); //Damit der Wert NICHT sofort ausgerechnet wird
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH + "'", btemp);
			
			//+++ Im 1. Lauf Entschluesselung noch nicht verwenden
			btemp = objFileIniTest.setFlag(IKernelEncryptionIniSolverZZZ.FLAGZ.USEENCRYPTION, false); //Ansonsten wird der Wert sofort ausgerechnet
			assertTrue("Flag nicht vorhanden '" + IKernelEncryptionIniSolverZZZ.FLAGZ.USEENCRYPTION + "'", btemp);
			
			//Anwenden der ersten Formel						
			sExpressionSource = KernelEncryptionIniSolverZZZTest.sEXPRESSION_ENCRYPTION01_DEFAULT;
			sSection = "Section for testEncrypted";
			sProperty = "WertAforDecrypt";
			
			String sTagStartZ = "<Z>";
			String sTagEndZ = "</Z>";
			sExpression = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSource, sTagStartZ, sTagEndZ);
					
			sValue = objFileIniTest.getPropertyValue(sSection, sProperty).getValue();
			assertNotNull(sValue);
			assertEquals(sExpression, sValue);
			
			//+++ Weiterer Lauf mit objEntry, aber immer noch keine Entschluesselung verwenden			
			IKernelConfigSectionEntryZZZ objEntry = objFileIniTest.getPropertyValue(sSection, sProperty);
			boolean bDecrypted = objEntry.isDecrypted();
			assertFalse(bDecrypted);//Wenn das Flag auf false gesetzt ist, wird das nicht behandelt
						
			boolean bIsRawEncrypted = objEntry.isRawEncrypted();
			assertFalse(bIsRawEncrypted);//Wenn das Flag auf false gesetzt ist, wird das nicht behandelt
						
			//+++ Nun Entschluesselung verwenden
			btemp = objFileIniTest.setFlag(IKernelEncryptionIniSolverZZZ.FLAGZ.USEENCRYPTION, true);
			assertTrue("Flag nicht vorhanden '" + IKernelEncryptionIniSolverZZZ.FLAGZ.USEENCRYPTION + "'", btemp);
			
			objEntry = objFileIniTest.getPropertyValue(sSection, sProperty);
			assertNotNull(objEntry);
			bDecrypted = objEntry.isDecrypted();
			assertTrue(bDecrypted);//Erst wenn das Flag auf true gesetzt ist, wird es überhaupt behandelt und ggfs. als JSON erkannt.
			
			//Prüfe nun, ob der hinterlegte Wert gleich ist.
			sExpression = objEntry.getValue();
			
			//+++ Vergleichswert holen
			sSection = "Section for testEncrypted";
			sProperty = "WertA";
			sExpression = "abcde";
			IKernelConfigSectionEntryZZZ objEntryProof = objFileIniTest.getPropertyValue(sSection, sProperty);
			sValue = objEntryProof.getValue();
			assertEquals(sExpression, sValue);
			
			
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}
	
	public void testComputeJson() {	
		boolean btemp; String sSection; String sProperty; String sValue;
		String sExpressionSource; String sExpression;
		
		try {
			//###############################################	
			//### JSON Ausdruck aus INI-Datei lesen
			//###############################################
			
			btemp = objFileIniTest.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION, true); 
			assertTrue("Flag nicht vorhanden '" + IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
			
			btemp = objFileIniTest.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER,true); 
			assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
			
			//Nun erst werden Pfade ersetzt
			btemp = objFileIniTest.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH, true); //muss egal sein, da EXPRESION = FALSE //muss egal sein, wenn EXPRESIONSOLVER = FALSE
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
						
			//Nun erst werden Variablen ersetzt			
			btemp = objFileIniTest.setFlag(IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE,true);
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE + "'", btemp);
			
			btemp = objFileIniTest.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA,true); //Damit der Wert sofort ausgerechnet wird
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA + "'", btemp);
			
			
			btemp = objFileIniTest.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH, true); //Damit der Wert NICHT sofort ausgerechnet wird
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH + "'", btemp);
			
			//+++ Im 1. Lauf JSON noch nicht verwenden
			btemp = objFileIniTest.setFlag(IKernelJsonIniSolverZZZ.FLAGZ.USEJSON, false); //Ansonsten wird der Wert sofort ausgerechnet
			assertTrue("Flag nicht vorhanden '" + IKernelJsonIniSolverZZZ.FLAGZ.USEJSON + "'", btemp);
					
			//Anwenden der ersten Formel mit objEntry als Ergebnis, keine JSON verwenden
			sSection = "Section for testJsonHashmap";
			sProperty = "Map1";
			sExpressionSource = KernelJsonMapIniSolverZZZTest.sEXPRESSION_JSONMAP01_DEFAULT;
			
			String sTagStartZ = "<Z>";
			String sTagEndZ = "</Z>";
			sExpression = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSource, sTagStartZ, sTagEndZ);
										
			IKernelConfigSectionEntryZZZ objEntry = objFileIniTest.getPropertyValue(sSection, sProperty);
			boolean bIsJson = objEntry.isJson();
			assertFalse(bIsJson);//Wenn das Flag auf false gesetzt ist, wird das nicht behandelt
			sValue = objEntry.getValue();
			assertEquals(sExpression, sValue);
			
			//+++ Nun JSONMAP und JSON verwenden			
			btemp = objFileIniTest.setFlag(IKernelJsonIniSolverZZZ.FLAGZ.USEJSON, true);
			assertTrue("Flag nicht vorhanden '" + IKernelJsonIniSolverZZZ.FLAGZ.USEJSON + "'", btemp);
			
			btemp = objFileIniTest.setFlag(IKernelJsonMapIniSolverZZZ.FLAGZ.USEJSON_MAP, true);
			assertTrue("Flag nicht vorhanden '" + IKernelJsonMapIniSolverZZZ.FLAGZ.USEJSON_MAP + "'", btemp);
						
			objEntry = objFileIniTest.getPropertyValue(sSection, sProperty);
			bIsJson = objEntry.isJson();
			assertTrue(bIsJson);
			
			HashMap<String,String> hm = objEntry.getValueHashMap();
			assertEquals(2,hm.size());
			
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}


	/** Erweitertes Flag Handling. 
	 *   Das Ziel ist es gesetzte Flags an andere Objekte "Weiterzureichen"
	 * 
	 */
	public void testFlagPassHandling(){
		//A) Teste an dier Stelle die Funktionalitäten aus ObjectZZZ
		
		try{
				
			//TestKonfiguration prüfen.
			//1. Hole alle FlagZ des Objekts
			String[] saTest01 = objFileIniInit.getFlagZ();
			assertNotNull(saTest01);		
			assertTrue("Es wurden auf dieser Ebenen der Objekthierarrchie mehr als 2 FlagZ erwartet: Also nicht nur DEBUG und INIT.",saTest01.length>=3);
			
			//2. Hole alle FlagZ Einträge, die entsprechend true/false gesetzt sind.
			//Init - Object
			assertTrue(objFileIniInit.getFlag("init")==true);
			
			String[]saTest02 = objFileIniInit.getFlagZ(true);
			assertNotNull(saTest02);		
			assertTrue("Es wurden auf dieser Ebenen der Objekthierarrchie nur 1 FlagZ für 'true' erwartet: INIT.",saTest02.length==1);
					
			String[]saTest02b = objFileIniInit.getFlagZ(false);
			assertNotNull(saTest02b);		
			assertTrue("Es wurden auf dieser Ebenen der Objekthierarrchie mehr als 1 FlagZ für 'false' erwartet: Also nicht nur DEBUG.",saTest02b.length>=2);
			
			objFileIniInit.setFlag("DEBUG", true);
			String[]saTest02c = objFileIniInit.getFlagZ(false);
			assertNotNull(saTest02c);		
			assertTrue("Es wurden auf dieser Ebenen der Objekthierarrchie JETZT EIN FLAG WENIGER für 'false' erwartet.",saTest02c.length==saTest02b.length-1);
			
			
			//B) TESTE DIE FUNKTIONALITÄT DER FLAG - ÜBERGABE.
			KernelZFormulaIniSolverZZZ objSolverInit = new KernelZFormulaIniSolverZZZ();
			
			//B01) Teste mal welche FlagZ es gemeinsam gibt.
			String[] saTestB01 = objFileIniInit.getFlagZ_passable(objSolverInit);
			assertTrue("Es wurden auf dieser Ebenen der Objekthierarrchie 3 FlagZ (oder mehr) als Gemeinsamkeit erwartet: INIT, DEBUG, USEFORMULA, USEFORMULA_MATH, ... .",saTestB01.length>=4);
			
			//B02) Teste welche von den gemeinsamen FlagZ hier True gesetzt sind.
			//objFileInit.getFlagZ_passable(true, sTargetClassnameForThePass);
			String[] saTestB02 = objFileIniInit.getFlagZ_passable(true, objSolverInit);
			assertTrue("Es wurden auf dieser Ebenen der Objekthierarrchie JETZT ZWEI FLAGS für 'true' erwartet.",saTestB02.length==2);
			
			//B03) Teste welche von den gemeinsamen FlagZ hier True gesetzt sind.
			//objFileInit.getFlagZ_passable(true, sTargetClassnameForThePass);
			String[] saTestB03 = objFileIniInit.getFlagZ_passable(false, objSolverInit);
			assertTrue("Es wurden auf dieser Ebenen der Objekthierarrchie JETZT für 'false' der Rest an Flags erwartet.",saTestB03.length==saTestB01.length-saTestB02.length);
					
		}catch(ExceptionZZZ ez){
			fail("An exception happend testing: " + ez.getDetailAllLast());
		}
		
	}
	
	
	
}//END class
	
