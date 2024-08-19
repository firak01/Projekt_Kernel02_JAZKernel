package basic.zKernel.file.ini;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractList.HashMapCaseInsensitiveZZZ;
import basic.zBasic.util.crypt.code.ICharacterPoolUserZZZ;
import basic.zBasic.util.crypt.code.IROTUserZZZ;
import basic.zBasic.util.crypt.code.ROT13ZZZ;
import basic.zBasic.util.crypt.code.ROTnnZZZ;
import basic.zBasic.util.crypt.code.ROTnumericZZZ;
import basic.zBasic.util.datatype.character.CharacterExtendedZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.file.FileEasyZZZ;
import basic.zBasic.util.stream.IStreamZZZ;
import basic.zBasic.util.stream.StreamZZZ;
import basic.zKernel.IKernelConfigSectionEntryZZZ;
import basic.zKernel.KernelZZZ;
import custom.zKernel.file.ini.FileIniZZZ;
import junit.framework.TestCase;

public class KernelZFormulaIniSolverZZZTest extends TestCase {
	private final static String strFILE_DIRECTORY_DEFAULT = new String("c:\\fglKernel\\KernelTest");
	private final static String strFILE_NAME_DEFAULT = new String("JUnit_KernelZFormulaIniSolverZZZTest.ini");
	
	
	private File objFile;
	private FileIniZZZ objFileIniInit;
	private FileIniZZZ objFileIniTest;
	private KernelZZZ objKernel;
	
	/// +++ Die eigentlichen Test-Objekte	
	private KernelZFormulaIniSolverZZZ objFormulaSolver;
	private KernelZFormulaIniSolverZZZ objFormulaSolverInit;
	
	

	protected void setUp(){
		try {			
			
			//### Eine Beispieldatei. Merke: Die Einträge werden immer neu geschrieben und nicht etwa angehngt.
			//Merke: Erst wenn es überhaupt einen Test gibt, wird diese Datei erstellt
			String sFilePathTotal = null;		
			if(FileEasyZZZ.exists(strFILE_DIRECTORY_DEFAULT)){
				sFilePathTotal = FileEasyZZZ.joinFilePathName(strFILE_DIRECTORY_DEFAULT, strFILE_NAME_DEFAULT );
			}else{
				//Eclipse Worspace
				File f = new File("");
			    String sPathEclipse = f.getAbsolutePath();
			    System.out.println("Path for Kernel Directory Default does not exist. Using workspace absolut path: " + sPathEclipse);
				
			    sFilePathTotal = FileEasyZZZ.joinFilePathName(sPathEclipse, strFILE_NAME_DEFAULT );				
			}
			
			IStreamZZZ objStreamFile = new StreamZZZ(sFilePathTotal, 1);  //This is not enough, to create the file			
			objStreamFile.println(";This is a temporarily test file for FileIniZZZTest.");      //Now the File is created. This is a comment line
			objStreamFile.println(";This file will be newly created by the setUp()-method of this JUnit Test class, every time before a testMethod will be invoked.");
			objStreamFile.println("#This is another commentline");
			objStreamFile.println("[Section A]");
			objStreamFile.println("Testentry1=Testvalue1 to be found");			
			objStreamFile.println("TestentryDymmy=nothing");
						
			objStreamFile.println("[Section B!01]");
			objStreamFile.println("Testentry2=Testvalue local to be found");
			
			objStreamFile.println("[Section B]");
			objStreamFile.println("Testentry2=Testvalue global. This should not be found");
			
			objStreamFile.println("[Section for testCompute]");
			objStreamFile.println("Formula1=Der dynamische Wert ist '<Z>[Section A]Testentry1</Z>'. FGL rulez.");
			objStreamFile.println("Formula2=Der dynamische Wert2 ist '<Z>[Section B]Testentry2</Z>'. FGL rulez.");
			
			objStreamFile.println("[Section for testComputeMathArguments]");
			objStreamFile.println("WertA=4");
			objStreamFile.println("WertB=5");
			
			objStreamFile.println("[Section for testComputeMathValue]");
			objStreamFile.println("Formula1=Der dynamische Wert ist '<Z><Z:math><Z:val>2</Z:val><Z:val>3</Z:val></Z:math></Z>'. FGL rulez.");
			objStreamFile.println("Formula2=Der dynamische Wert2 ist '<Z><Z:math><Z:val>2</Z:val><Z:op>*</Z:op><Z:val>[Section for testComputeMathArguments]WertA</Z:val></Z:math></Z>'. FGL rulez.");		
			
			objStreamFile.println("[Section for testComputeMath]");
			objStreamFile.println("Formula1=Der dynamische Wert ist '<Z><Z:math><Z:val>2</Z:val><Z:op>*</Z:op><Z:val>3</Z:val></Z:math></Z>'. FGL rulez.");
			objStreamFile.println("Formula2=Der dynamische Wert2 ist '<Z><Z:math><Z:val>2</Z:val><Z:op>*</Z:op><Z:val>[Section for testComputeMathArguments]WertA</Z:val></Z:math></Z>'. FGL rulez.");
			objStreamFile.println("Formula3=Der dynamische Wert3 ist '<Z><Z:math><Z:val>[Section for testComputeMathArguments]WertB</Z:val><Z:op>*</Z:op><Z:val>[Section for testComputeMathArguments]WertA</Z:val></Z:math></Z>'. FGL rulez.");
			 
			
			objStreamFile.println("[Section for testComputePathWithMath]");
			objStreamFile.println("Formula1=<Z>[Section for testComputeMath]Formula1</Z>");
			objStreamFile.println("Formula2=<Z>[Section for testComputeMath]Formula2</Z>");
			objStreamFile.println("Formula3=<Z>[Section for testComputeMath]Formula3</Z>");
			
			objStreamFile.println("[Section for testComputeMath NOT EXACTMATCH]");
			objStreamFile.println("Formula1=Der dynamische Wert ist '<Z><z:Math><Z:VAL>6</Z:val><Z:oP>+</Z:op><Z:val>7</Z:val></Z:math></Z>'. FGL rulez.");
			 
			
			objStreamFile.println("[Section for testComputeMathArguments FLOAT]");
			objStreamFile.println("WertA_float=4.0");
			objStreamFile.println("WertB_float=5.0");
			objStreamFile.println("[Section for testComputeMath FLOAT]");
			objStreamFile.println("Formula1=Der dynamische Wert ist '<Z><z:Math><Z:VAL>[Section for testComputeMathArguments FLOAT]WertA_float</Z:val><Z:oP>*</Z:op><Z:val>[Section for testComputeMathArguments FLOAT]WertB_float</Z:val></Z:math></Z>'. FGL rulez.");
			
			objStreamFile.println("[Section for testComputeMathVariable FLOAT]");
			objStreamFile.println("WertB_float=<Z><z:Var>myTestVariableFloat</z:Var></Z>");
			
			//Beachte Variablen können wie INI-Path auch außßerhalb einer MATH - Anweisung gelten.
			objStreamFile.println("[Section for testPassVariable]");
			objStreamFile.println("Formula1=<Z>Der dynamische Wert ist '<z:Var>myTestVariableString</z:Var>'. FGL rulez.</Z>");
			objStreamFile.println("Formula2=Der dynamische Wert ist '<Z><z:Math><Z:VAL>[Section for testComputeMathArguments FLOAT]WertA_float</Z:val><Z:oP>*</Z:op><Z:val><Z:Var>myTestVariableFloat</z:Var></Z:val></Z:math></Z>'. FGL rulez.");
			objStreamFile.println("Formula3=Der dynamische Wert ist '<Z><z:Math><Z:VAL>[Section for testComputeMathArguments FLOAT]WertA_float</Z:val><Z:oP>*</Z:op><Z:val>[Section for testComputeMathVariable FLOAT]WertB_float</Z:val></Z:math></Z>'. FGL rulez.");
			
			
			//20210707 Tests für die Arbeit mit JSON Strings
			//Merke:			
			//Gib den JSON-Hashmap-Wert so an: {"DEBUGUI_PANELLABEL_ON":true} Merke: Intern hier eine HashMap String, Boolean Das ist aber nur sinnvoll bei der FLAG übergabe, da weiss man, dass der Wert Boolean ist.
			//                           also: NavigatorContentJson=<JSON>{"UIText01":"TESTWERT2DO2JSON01","UIText02":"TESTWERT2DO2JSON02"}</JSON>
			//Gib den JSON-Array-Wert so an: {"wert1","wert2"}
			objStreamFile.println("[Section for testJsonHashmap]");
			objStreamFile.println("Map1="+KernelJsonMapIniSolverZZZTest.sEXPRESSION_JSONMAP01_DEFAULT);
									
			
			//20220926 Tests für die Arbeit mit verschluesselten / encrypted Werten
			String sValue = "abcde"; int iKeyNumber=5; String sCharacterPool="?! abcdefghijklmnopqrstuvwxyz";
			String sFlagNumeric = ICharacterPoolUserZZZ.FLAGZ.USENUMERIC.name();
			String sFlagUppercase = ICharacterPoolUserZZZ.FLAGZ.USEUPPERCASE.name();
			String sFlagBlank = IROTUserZZZ.FLAGZ.USEBLANK.name();
			String sEncrypted = ROT13ZZZ.encryptIt(sValue);
			objStreamFile.println("[Section for testEncrypted]");
			objStreamFile.println("WertA="+sValue);
			objStreamFile.println("WertAencrypted="+KernelEncryptionIniSolverZZZTest.sEXPRESSION_ENCRYPTION01_DEFAULT);
			
			sEncrypted = ROTnumericZZZ.encrypt(sValue, iKeyNumber, true, false);
			objStreamFile.println("WertB="+sValue);
			objStreamFile.println("WertBencrypted="+KernelEncryptionIniSolverZZZTest.sEXPRESSION_ENCRYPTION02_DEFAULT);
			
			CharacterExtendedZZZ objCharacterMissingReplacement = new CharacterExtendedZZZ(ICharacterPoolUserZZZ.cCHARACTER_MISSING_REPLACEMENT_DEFAULT);
			sEncrypted = ROTnnZZZ.encrypt(sValue, sCharacterPool, objCharacterMissingReplacement, iKeyNumber, true, false, false,false);
			objStreamFile.println("WertC="+sValue);
			objStreamFile.println("WertCencrypted="+KernelEncryptionIniSolverZZZTest.sEXPRESSION_ENCRYPTION02_DEFAULT);
			
			
			
			
			objFile = new File(sFilePathTotal);
							
			//Kernel + Log - Object dem TestFixture hinzuf�gen. Siehe test.zzzKernel.KernelZZZTest
			objKernel = new KernelZZZ("FGL", "01", "test", "ZKernelConfigKernel_test.ini",(String)null);
			
			//Für die Variablenersetzung wichtig: Eine HashMap mit den Variablen in der Ini-Datei.
			HashMapCaseInsensitiveZZZ<String,String> hmVariable = new HashMapCaseInsensitiveZZZ<String,String>();
			hmVariable.put("myTestVariableString", "mySolvedTestVariableString");
			objFileIniTest = new FileIniZZZ(objKernel,  objFile, hmVariable, (String[]) null);
			 			
			//### Die TestObjecte
			objFormulaSolverInit = new KernelZFormulaIniSolverZZZ<Object>();
			objFormulaSolver = new KernelZFormulaIniSolverZZZ(objKernel, objFileIniTest, null);
			
			//TestKonfiguration prüfen
//			assertTrue(objExpressionSolverInit.getFlag("init")==true);
//			assertFalse(objExpressionSolver.getFlag("init")==true); //Nun wäre init falsch
			
			//#### Ein init TestObjekt
			String[] saFlag = {"init"};
			objFileIniInit = new FileIniZZZ(objKernel,  objFile, saFlag);
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		} catch (FileNotFoundException e) {			
			e.printStackTrace();
		} catch (IOException e) {			
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}//END setup
	
	public void testFlagHandling(){
		try{
				
			assertTrue(objFormulaSolverInit.getFlag("init")==true);
			assertFalse(objFormulaSolver.getFlag("init")==true); //Nun wäre init falsch
	
			
			String[] saFlag = objFormulaSolver.getFlagZ();
			assertTrue(saFlag.length==4);
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}
	
	/** void, Test: Reading an entry in a section of the ini-file
	* Lindhauer; 22.04.2006 12:54:32
	 */
	public void testCompute(){
		try {
			String sValue; String sExpression; String sExpressionSource;
			boolean btemp;
			sExpressionSource = "Der dynamische Wert ist '<Z>[Section A]Testentry1</Z>'. FGL rulez.";
			
			//+++++++ SPEZIELLER TEST START
			
			//+++++++ SPEZIELLER TEST ENDE
			
			
			//+++ Alle Flags auf false
			//--> Output = Input
			sExpression = objFormulaSolver.solve(sExpressionSource);
			assertEquals(sExpressionSource, sExpression);
			
			//-->Output = Input			
			sExpression = objFormulaSolver.parse(sExpressionSource);
			assertEquals(sExpressionSource, sExpression);
			
			
			
			//+++ Expression behandeln
			btemp = objFormulaSolver.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION,true); //eh keine Formel drin, also sollte das egal sein.);
			assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
 
			//--> Output = Input, weil keine Pfad-expression verarbeitet wird.
			sExpression = objFormulaSolver.solve(sExpressionSource);
			assertEquals(sExpressionSource, sExpression);
			
			//-->Output = Input, aber durch das Parsen und Verareitung der Expression sind die Z-Tags drumrum weg
			sExpression = objFormulaSolver.solve(sExpressionSource);
			assertEquals("Der dynamische Wert ist '[Section A]Testentry1'. FGL rulez.", sExpression);
			
			
			//+++++++++++++++++++++++++++++++++++++++
			btemp = objFormulaSolver.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA,true); //eh keine Formel drin, also sollte das egal sein.);
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA + "'", btemp);
 
			sExpression = objFormulaSolver.solve(sExpressionSource);
			assertEquals(sExpressionSource, sExpression);
			
			//beim "reinen Parsen" bleiben Pfadangaben bestehen, auch Z-Tags müssten bestehen bleiben, 
			//weil IKernelExpressionIniSolverZZZ nicht implementiert ist 
			//aber Weil der Tag fuer Formeln auch <Z> ist, werden diese letztendlich vom Parsen doch entfent!!!
			sExpression = objFormulaSolver.parse(sExpressionSource);
			assertEquals("Der dynamische Wert ist '[Section A]Testentry1'. FGL rulez.", sExpression);
			
			
			
			
			//++++++++++++++++++++++++++++++++++++++++
			btemp = objFormulaSolver.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA,true); //eh keine Formel drin, also sollte das egal sein.);
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA + "'", btemp);
 
			btemp = objFormulaSolver.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH,true); //eh keine Formel drin, also sollte das egal sein.);
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);

			sExpression = objFormulaSolver.solve(sExpressionSource);
			assertEquals("Der dynamische Wert ist '<Z>Testvalue1 to be found</Z>'. FGL rulez.", sExpression);
			
			sExpression = objFormulaSolver.parse(sExpressionSource);
			assertEquals("Der dynamische Wert ist 'Testvalue1 to be found'. FGL rulez.", sExpression);
			
			
			//+++++++++++++++++++++++++++++++++++++++
			
			
			//Nun Pfadersetzung, Merke: Dies ist bestandteil jedes Solvers....
			btemp = objFormulaSolver.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH,true); //eh keine Formel drin, also sollte das egal sein.);
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
			
//			TODOGOON: Unabhängig von einer Formel bei jedem Parsen den Pfad ggfs. auflösen.
//			          Dazu super.solve(sExpression) darin aufrufen.
			sValue = objFormulaSolver.parse(sExpression);
			assertEquals("Der dynamische Wert ist 'Testvalue1 to be found'. FGL rulez.", sValue);
			
			
			//################################################
			//### mit objFileini
			
			btemp = objFileIniTest.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION,false); //false: Damit der Pfad NICHT sofort ausgerechnet wird
			assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
			 			
			btemp = objFileIniTest.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH, true); //eh keine Formel drin, also sollte das egal sein.
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
			 
			objFileIniTest.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA, true); //eh keine Formel drin, also sollte das egal sein.
			
			sExpression = objFileIniTest.getPropertyValue("Section for testCompute", "Formula1").getValue();
			assertEquals(sExpressionSource, sExpression);
			
			//++++++++++++++++++++++++++++++++++
			
			
			//+++++++++++++++++++++++++++++++++++++++
			objFileIniTest.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION,true); //false: Damit der Pfad NICHT sofort ausgerechnet wird
			objFileIniTest.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH, false); //also nur die Expression aufloesen, aber den Pfad nicht aufloesen.			
			objFormulaSolver.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA,true); //eh keine Formel drin, also sollte das egal sein.);
			
			//TODOGOON20240813; 
			//beim "reinen Parsen" bleiben Pfadangaben bestehen, aber die Z-Tags müssen weg.
			sValue = objFormulaSolver.parse(sExpressionSource);
			assertEquals("Der dynamische Wert ist '[Section A]Testentry1'. FGL rulez.", sValue);
						
			//beim "Solven" werden dann Pfade ersetzt
			sValue = objFormulaSolver.solve(sExpressionSource);
			assertEquals("Der dynamische Wert ist 'Testvalue1 to be found'. FGL rulez.", sValue);
							
			objFileIniTest.setFlag("useformula", true); //Damit der Wert sofort ausgerechnet wird
			sValue = objFileIniTest.getPropertyValue("Section for testCompute", "Formula1").getValue();
			assertEquals("Der dynamische Wert ist 'Testvalue1 to be found'. FGL rulez.", sValue);
			//++++++++++++++++++++++++++++++++++++++++
			
			
			//Anwenden der Formel, so dass ein localer Wert vor einem globalen Wert gefunden wird.
			/*objStreamFile.println("[Section B!01]");
			objStreamFile.println("Testentry2=Testvalue local to be found");
			
			objStreamFile.println("[Section B]");
			objStreamFile.println("Testentry2=Testvalue global. This should not be found");
			*/
			objFileIniTest.setFlag("useformula", false);//Ansonsten wird der Wert sofort ausgerechnet
			sExpression = objFileIniTest.getPropertyValue("Section for testCompute", "Formula2").getValue();
			sValue = objFormulaSolver.parse(sExpression);
			assertEquals("Der dynamische Wert2 ist 'Testvalue local to be found'. FGL rulez.", sValue);
			
			objFileIniTest.setFlag("useformula", true); //Damit der Wert sofort ausgerechnet wird
			sExpression = objFileIniTest.getPropertyValue("Section for testCompute", "Formula2").getValue();
			assertEquals(sValue, sExpression);
			
			objFileIniTest.setFlag("useformula", true); //Damit der Wert sofort ausgerechnet wird
			objFileIniTest.setFlag("useformula_math", false); //Damit noch KEINE MATH FORMEL ausgerechnet wird
			sExpression = objFileIniTest.getPropertyValue("Section for testComputeMath", "Formula1").getValue();
			//Merke: Das waere der String mit umgebenden <Z> - Tag. Der wird aber aufgeloest:
			//     "Der dynamische Wert ist '<Z><Z:math><Z:val>2</Z:val><Z:op>*</Z:op><Z:val>3</Z:val></Z:math></Z>'. FGL rulez.";
			sValue="Der dynamische Wert ist '<Z:math><Z:val>2</Z:val><Z:op>*</Z:op><Z:val>3</Z:val></Z:math>'. FGL rulez."; //Also Z-Tag weg aber der Wert ohne die z:math-Tags auszurechnen.
			assertEquals(sValue, sExpression);
			
			objFileIniTest.setFlag("useformula", true); //Damit der Wert sofort ausgerechnet wird
			objFileIniTest.setFlag("useformula_math", true); //Damit jetzt die MATH FORMEL ausgerechnet wird
			sExpression = objFileIniTest.getPropertyValue("Section for testComputeMathValue", "Formula1").getValue();
			sValue="Der dynamische Wert ist '23'. FGL rulez."; //Also der Wert ohne einen Operator auszurechnen.
			assertTrue("Im Ergebnis '" + sExpression + "' wurde eine zusammengesetzte '23' erwartet.", StringZZZ.contains(sExpression, "23"));
			assertEquals(sValue, sExpression);
			
			
			objFileIniTest.setFlag("useformula", true); //Damit der Wert sofort ausgerechnet wird
			objFileIniTest.setFlag("useformula_math", true); //Damit jetzt die MATH FORMEL ausgerechnet wird
			sExpression = objFileIniTest.getPropertyValue("Section for testComputeMath", "Formula1").getValue();
			sValue="Der dynamische Wert ist '6'. FGL rulez."; //Also der Wert ohne die Math auszurechnen.
			assertTrue("Im Ergebnis wurde eine ausgerechnete '6' erwartet.", StringZZZ.contains(sExpression, "6"));
			assertEquals(sValue, sExpression);
			
			//+++ Die Tags brauchen nicht exact zu sein hinsichtlich der Groß-/Kleinescheibung. Außerdem wird der '+' Operator getestet.
			objFileIniTest.setFlag("useformula", true); //Damit der Wert sofort ausgerechnet wird
			objFileIniTest.setFlag("useformula_math", true); //Damit jetzt die MATH FORMEL ausgerechnet wird
			sExpression = objFileIniTest.getPropertyValue("Section for testComputeMath NOT EXACTMATCH", "Formula1").getValue();
			sValue="Der dynamische Wert ist '13'. FGL rulez."; //Also der Wert ohne die Math auszurechnen.
			assertTrue("Im Ergebnis wurde eine ausgerechnete '13' erwartet.", StringZZZ.contains(sExpression, "13"));
			assertEquals(sValue, sExpression);
			
			//+++ Teste das Rechnen mit Float-Werten
			objFileIniTest.setFlag("useformula", true); //Damit der Wert sofort ausgerechnet wird
			objFileIniTest.setFlag("useformula_math", true); //Damit jetzt die MATH FORMEL ausgerechnet wird
			sExpression = objFileIniTest.getPropertyValue("Section for testComputeMath FLOAT", "Formula1").getValue();
			sValue="Der dynamische Wert ist '20.0'. FGL rulez."; //Also der Wert ohne die Math auszurechnen.
			assertTrue("Im Ergebnis wurde eine ausgerechnete '20.0' erwartet.", StringZZZ.contains(sExpression, "20.0"));
			assertEquals(sValue, sExpression);
			
			
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}
	
	public void testCompute_Variables() {
		try {
			objFileIniTest.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION.name(),true);
			
			//+++ Teste Uebergabe von Variablen
			objFileIniTest.setFlag("useformula", true); //Damit der Wert sofort ausgerechnet wird
			objFileIniTest.setFlag("useformula_math", false); //Math ist keine Voraussetzung für Variablen
			String sExpression = objFileIniTest.getPropertyValue("Section for testPassVariable", "Formula1").getValue(); //Wenn noch keine Formelvariable gesetzt ist...
			String sValue="Der dynamische Wert ist 'mySolvedTestVariableString'. FGL rulez."; //Also der Wert mit einer uebergebenen Variablen ausgerechnet.
			assertTrue("Im Ergebnis wurde eine ausgerechnete 'mySolvedTestVariableString' erwartet.", StringZZZ.contains(sExpression, "mySolvedTestVariableString"));
			assertEquals(sValue, sExpression);
			 
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}
	
	public void testCompute_VariablesCascaded() {
		try {
			objFileIniTest.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION.name(),true);
						
			//Einbinden der Variablen in Math-Ausdrücke
			objFileIniTest.setFlag("useformula", true); //Damit der Wert sofort ausgerechnet wird
			objFileIniTest.setFlag("useformula_math", true); //Math ist keine Voraussetzung für Variablennersetzung, aber zum Rechnen.
			
			HashMapCaseInsensitiveZZZ<String,String> hmVariable = new HashMapCaseInsensitiveZZZ<String,String>();
			hmVariable.put("myTestVariableString","Test erfolgreich");
			hmVariable.put("myTestVariableFloat","2.5");
			objFileIniTest.setHashMapVariable(hmVariable);
			String sExpression = objFileIniTest.getPropertyValue("Section for testPassVariable", "Formula2").getValue(); //Wenn noch keine Formelvariable gesetzt ist...
			String sValue="Der dynamische Wert ist '10.0'. FGL rulez."; //Also der Wert ohne die Math auszurechnen.
			assertTrue("Im Ergebnis wurde eine ausgerechnete '10.0' erwartet.", StringZZZ.contains(sExpression, "10.0"));
			assertEquals(sValue, sExpression);
			

		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}
	
	
	public void testCompute_VariablesCascaded_DifferentPath() {
		try {
			objFileIniTest.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION.name(),true);
						
			//Einbinden der Variablen in Math-Ausdrücke
			objFileIniTest.setFlag("useformula", true); //Damit der Wert sofort ausgerechnet wird
			objFileIniTest.setFlag("useformula_math", true); //Math ist keine Voraussetzung für Variablennersetzung, aber zum Rechnen.
						
			//Verschachtelung. D.h Formel arbeitet mit Variablen, die in einem anderen INI-Pfad liegt.
			HashMapCaseInsensitiveZZZ<String,String> hmVariable02 = new HashMapCaseInsensitiveZZZ<String,String>();
			hmVariable02.put("myTestVariableString","Test erfolgreich");
			hmVariable02.put("myTestVariableFloat","3.0");
			objFileIniTest.setHashMapVariable(hmVariable02);
			String sExpression = objFileIniTest.getPropertyValue("Section for testPassVariable", "Formula3").getValue(); //Wenn noch keine Formelvariable gesetzt ist...
			String sValue="Der dynamische Wert ist '12.0'. FGL rulez."; //Also der Wert ohne die Math auszurechnen.
			assertTrue("Im Ergebnis wurde eine ausgerechnete '12.0' erwartet.", StringZZZ.contains(sExpression, "12.0"));
			assertEquals(sValue, sExpression);

		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}
	
	public void testCompute_Math_Cascaded() {
		try {
			objFileIniTest.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION.name(),true);
				
			//+++ Verschachtelt, hier wird auf eine Section Verwiesen, die einen Math-Ausdruck enthält.
			objFileIniTest.setFlag("useformula", true); //Damit der Wert sofort ausgerechnet wird
			objFileIniTest.setFlag("useformula_math", true); //Damit jetzt die MATH FORMEL ausgerechnet wird
			String sExpression = objFileIniTest.getPropertyValue("Section for testComputePathWithMath", "Formula1").getValue();
			String sValue="Der dynamische Wert ist '6'. FGL rulez."; //Also der Wert ohne die Math auszurechnen.
			assertTrue("Im Ergebnis wurde eine ausgerechnete '6' erwartet.", StringZZZ.contains(sExpression, "6"));
			assertEquals(sValue, sExpression);
			
		
			//+++ Verschachtelt, hier wird auf eine Section Verwiesen, die einen Math-Ausdruck enthält, der wiederum eine IniPath-Anweisung hat.
			//TODOGOON;//20230514
			objFileIniTest.setFlag("useformula", true); //Damit der Wert sofort ausgerechnet wird
			objFileIniTest.setFlag("useformula_math", true); //Damit jetzt die MATH FORMEL ausgerechnet wird
			sExpression = objFileIniTest.getPropertyValue("Section for testComputePathWithMath", "Formula2").getValue();
			sValue="Der dynamische Wert2 ist '8'. FGL rulez."; //Also der Wert ohne die Math auszurechnen.
			assertEquals(sValue, sExpression);
			assertTrue("Im Ergebnis wurde eine ausgerechnete '8' erwartet.", StringZZZ.contains(sExpression, "8"));
			
			//+++ Verschachtelt, hier wird auf eine Section Verwiesen, die einen Math-Ausdruck enthält, der wiederum eine IniPath-Anweisung hat.... diesmal 2 Path Anweisungen!!!
			objFileIniTest.setFlag("useformula", true); //Damit der Wert sofort ausgerechnet wird
			objFileIniTest.setFlag("useformula_math", true); //Damit jetzt die MATH FORMEL ausgerechnet wird
			sExpression = objFileIniTest.getPropertyValue("Section for testComputePathWithMath", "Formula3").getValue();
			sValue="Der dynamische Wert3 ist '20'. FGL rulez."; //Also der Wert ohne die Math auszurechnen.
			assertTrue("Im Ergebnis wurde eine ausgerechnete '20' erwartet.", StringZZZ.contains(sExpression, "20"));
			assertEquals(sValue, sExpression);
			
			
		}catch(ExceptionZZZ ez){
			fail("An exception happend testing: " + ez.getDetailAllLast());
		}
			
	}

	
	public void testComputeEncryption() {		
		try {
			objFileIniTest.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION.name(),true);
			
			//Auch wenn die ZExpression-Ausdrücke gesetzt sind, muss es funktionieren.
			objFileIniTest.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA.name(), true);
			
			//Anwenden der ersten Formel
			objFileIniTest.setFlag(IKernelEncryptionIniSolverZZZ.FLAGZ.USEENCRYPTION.name(), false); //Ansonsten wird der Wert sofort ausgerechnet
			String sExpression = objFileIniTest.getPropertyValue("Section for testEncrypted", "WertAencrypted").getValue();
			assertNotNull(sExpression);
			
			IKernelConfigSectionEntryZZZ objEntry = objFileIniTest.getPropertyValue("Section for testEncrypted", "WertAencrypted");
			boolean bDecrypted = objEntry.isDecrypted();
			assertFalse(bDecrypted);//Wenn das Flag auf false gesetzt ist, wird das nicht behandelt
			
			boolean bIsRawEncrypted = objEntry.isRawEncrypted();
			assertFalse(bIsRawEncrypted);//Wenn das Flag auf false gesetzt ist, wird das nicht behandelt
						
			objFileIniTest.setFlag(IKernelEncryptionIniSolverZZZ.FLAGZ.USEENCRYPTION.name(), true);			
			objEntry = objFileIniTest.getPropertyValue("Section for testEncrypted", "WertAencrypted");
			assertNotNull(objEntry);
			bDecrypted = objEntry.isDecrypted();
			assertTrue(bDecrypted);//Erst wenn das Flag auf true gesetzt ist, wird es überhaupt behandelt und ggfs. als JSON erkannt.
			
			//Prüfe nun, ob der hinterlegte Wert gleich ist.
			String sValueDecrypted = objEntry.getValue();
			
			IKernelConfigSectionEntryZZZ objEntryProof = objFileIniTest.getPropertyValue("Section for testEncrypted", "WertA");
			String sProof = objEntryProof.getValue();
			assertEquals(sProof,sValueDecrypted);
			
			
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}
	
	public void testComputeJson() {		
		try {
			objFileIniTest.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION,true);
			
			//Auch wenn die ZExpression-Ausdrücke gesetzt sind, muss es funktionieren.
			objFileIniTest.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA, true);
			objFileIniTest.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH,true);
			
			//Anwenden der ersten Formel
			objFileIniTest.setFlag(IKernelJsonIniSolverZZZ.FLAGZ.USEJSON, false); //Ansonsten wird der Wert sofort ausgerechnet
			String sExpression = objFileIniTest.getPropertyValue("Section for testJsonHashmap", "Map1").getValue();
			assertNotNull(sExpression);
			
			IKernelConfigSectionEntryZZZ objEntry = objFileIniTest.getPropertyValue("Section for testJsonHashmap", "Map1");
			boolean bIsJson = objEntry.isJson();
			assertFalse(bIsJson);//Wenn das Flag auf false gesetzt ist, wird das nicht behandelt
			
			objFileIniTest.setFlag(IKernelJsonIniSolverZZZ.FLAGZ.USEJSON, true);
			objFileIniTest.setFlag(IKernelJsonArrayIniSolverZZZ.FLAGZ.USEJSON_ARRAY, true);
			objFileIniTest.setFlag(IKernelJsonMapIniSolverZZZ.FLAGZ.USEJSON_MAP, true);
			objEntry = objFileIniTest.getPropertyValue("Section for testJsonHashmap", "Map1");
			bIsJson = objEntry.isJson();
			assertTrue(bIsJson);//Erst wenn das Flag auf true gesetzt ist, wird es überhaupt behandelt und ggfs. als JSON erkannt.
			
			
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
	
