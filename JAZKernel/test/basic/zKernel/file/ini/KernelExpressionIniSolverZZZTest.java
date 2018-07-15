package basic.zKernel.file.ini;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Hashtable;

import junit.framework.TestCase;
import basic.javagently.Stream;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.file.FileEasyZZZ;
import basic.zKernel.KernelZZZ;
import basic.zKernel.file.ini.KernelExpressionIniSolverZZZ;
import custom.zKernel.LogZZZ;
import custom.zKernel.file.ini.FileIniZZZ;

public class KernelExpressionIniSolverZZZTest extends TestCase {
	private final static String strFILE_DIRECTORY_DEFAULT = new String("c:\\fglKernel\\KernelTest");
	private final static String strFILE_NAME_DEFAULT = new String("JUnitTest.ini");
	
	
	private File objFile;
	private FileIniZZZ objFileIniInit;
	private FileIniZZZ objFileIniTest;
	private KernelZZZ objKernel;
	
	/// +++ Die eigentlichen Test-Objekte	
	private KernelExpressionIniSolverZZZ objExpressionSolver;
	private KernelExpressionIniSolverZZZ objExpressionSolverInit;
	
	

	protected void setUp(){
		try {			
			
			//### Eine Beispieldatei. Merke: Die Einträge werden immer neu geschrieben und nicht etwa angehngt.
			//Merke: Erst wenn es �berhaupt einen Test gibt, wird diese DAtei erstellt
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
			
			Stream objStreamFile = new Stream(sFilePathTotal, 1);  //This is not enough, to create the file			
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
			objStreamFile.println("Formula1=Der dynamische Wert ist '<Z><Z:math><Z:value-of>2</Z:value-of><Z:value-of>3</Z:value-of></Z:math></Z>'. FGL rulez.");
			objStreamFile.println("Formula2=Der dynamische Wert2 ist '<Z><Z:math><Z:value-of>2</Z:value-of><Z:operator>*</Z:operator><Z:value-of>[Section for testComputeMathArguments]WertA</Z:value-of></Z:math></Z>'. FGL rulez.");		
			
			objStreamFile.println("[Section for testComputeMath]");
			objStreamFile.println("Formula1=Der dynamische Wert ist '<Z><Z:math><Z:value-of>2</Z:value-of><Z:operator>*</Z:operator><Z:value-of>3</Z:value-of></Z:math></Z>'. FGL rulez.");
			objStreamFile.println("Formula2=Der dynamische Wert2 ist '<Z><Z:math><Z:value-of>2</Z:value-of><Z:operator>*</Z:operator><Z:value-of>[Section for testComputeMathArguments]WertA</Z:value-of></Z:math></Z>'. FGL rulez.");
			objStreamFile.println("Formula3=Der dynamische Wert3 ist '<Z><Z:math><Z:value-of>[Section for testComputeMathArguments]WertB</Z:value-of><Z:operator>*</Z:operator><Z:value-of>[Section for testComputeMathArguments]WertA</Z:value-of></Z:math></Z>'. FGL rulez.");
			 
			
			objStreamFile.println("[Section for testComputePathWithMath]");
			objStreamFile.println("Formula1=<Z>[Section for testComputeMath]Formula1</Z>");
			objStreamFile.println("Formula2=<Z>[Section for testComputeMath]Formula2</Z>");
			objStreamFile.println("Formula3=<Z>[Section for testComputeMath]Formula3</Z>");
			
			
			objFile = new File(sFilePathTotal);
			
		
			
			//Kernel + Log - Object dem TestFixture hinzuf�gen. Siehe test.zzzKernel.KernelZZZTest
			objKernel = new KernelZZZ("FGL", "01", "", "ZKernelConfigKernel_test.ini",(String)null);	
			objFileIniTest = new FileIniZZZ(objKernel,  objFile, (String[]) null);
			 			
			//### Die TestObjecte
			objExpressionSolverInit = new KernelExpressionIniSolverZZZ();
			objExpressionSolver = new KernelExpressionIniSolverZZZ(objKernel, objFileIniTest, null);
			
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
		}		
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
			//Anwenden der ersten Formel
			objFileIniTest.setFlag("useformula", false); //Ansonsten wird der Wert sofort ausgerechnet
			String sExpression = objFileIniTest.getPropertyValue("Section for testCompute", "Formula1");
			String sValue = objExpressionSolver.compute(sExpression);
			assertEquals("Der dynamische Wert ist 'Testvalue1 to be found'. FGL rulez.", sValue);
			
			objFileIniTest.setFlag("useformula", true); //Damit der Wert sofort ausgerechnet wird
			sExpression = objFileIniTest.getPropertyValue("Section for testCompute", "Formula1");
			assertEquals(sValue, sExpression);
			
			//Anwenden der Formel, so da� ein localer Wert vor einem globalen Wert gefunden wird.
			/*objStreamFile.println("[Section B!01]");
			objStreamFile.println("Testentry2=Testvalue local to be found");
			
			objStreamFile.println("[Section B]");
			objStreamFile.println("Testentry2=Testvalue global. This should not be found");
			*/
			objFileIniTest.setFlag("useformula", false);//Ansonsten wird der Wert sofort ausgerechnet
			sExpression = objFileIniTest.getPropertyValue("Section for testCompute", "Formula2");
			sValue = objExpressionSolver.compute(sExpression);
			assertEquals("Der dynamische Wert2 ist 'Testvalue local to be found'. FGL rulez.", sValue);
			
			objFileIniTest.setFlag("useformula", true); //Damit der Wert sofort ausgerechnet wird
			sExpression = objFileIniTest.getPropertyValue("Section for testCompute", "Formula2");
			assertEquals(sValue, sExpression);
			
			objFileIniTest.setFlag("useformula", true); //Damit der Wert sofort ausgerechnet wird
			objFileIniTest.setFlag("useformula_math", false); //Damit noch KEINE MATH FORMEL ausgerechnet wird
			sExpression = objFileIniTest.getPropertyValue("Section for testComputeMath", "Formula1");
			sValue="Der dynamische Wert ist '<Z:math><Z:value-of>2</Z:value-of><Z:operator>*</Z:operator><Z:value-of>3</Z:value-of></Z:math>'. FGL rulez."; //Also der Wert ohne die Math auszurechnen.
			assertEquals(sValue, sExpression);
			
			objFileIniTest.setFlag("useformula", true); //Damit der Wert sofort ausgerechnet wird
			objFileIniTest.setFlag("useformula_math", true); //Damit jetzt die MATH FORMEL ausgerechnet wird
			sExpression = objFileIniTest.getPropertyValue("Section for testComputeMathValue", "Formula1");
			sValue="Der dynamische Wert ist '23'. FGL rulez."; //Also der Wert ohne einen Operator auszurechnen.
			assertTrue("Im Ergebnis wurde eine zusammengesetzte '23' erwartet.", StringZZZ.contains(sExpression, "23"));
			assertEquals(sValue, sExpression);
			
			
			objFileIniTest.setFlag("useformula", true); //Damit der Wert sofort ausgerechnet wird
			objFileIniTest.setFlag("useformula_math", true); //Damit jetzt die MATH FORMEL ausgerechnet wird
			sExpression = objFileIniTest.getPropertyValue("Section for testComputeMath", "Formula1");
			sValue="Der dynamische Wert ist '6'. FGL rulez."; //Also der Wert ohne die Math auszurechnen.
			assertTrue("Im Ergebnis wurde eine ausgerechnete '6' erwartet.", StringZZZ.contains(sExpression, "6"));
			assertEquals(sValue, sExpression);
			
			//+++ Verschachtelt, hier wird auf eine Section Verwiesen, die einen Math-Ausdruck enthält.
			objFileIniTest.setFlag("useformula", true); //Damit der Wert sofort ausgerechnet wird
			objFileIniTest.setFlag("useformula_math", true); //Damit jetzt die MATH FORMEL ausgerechnet wird
			sExpression = objFileIniTest.getPropertyValue("Section for testComputePathWithMath", "Formula1");
			sValue="Der dynamische Wert ist '6'. FGL rulez."; //Also der Wert ohne die Math auszurechnen.
			assertTrue("Im Ergebnis wurde eine ausgerechnete '6' erwartet.", StringZZZ.contains(sExpression, "6"));
			assertEquals(sValue, sExpression);
			
			//+++ Verschachtelt, hier wird auf eine Section Verwiesen, die einen Math-Ausdruck enthält, der wiederum eine IniPath-Anweisung hat.
			objFileIniTest.setFlag("useformula", true); //Damit der Wert sofort ausgerechnet wird
			objFileIniTest.setFlag("useformula_math", true); //Damit jetzt die MATH FORMEL ausgerechnet wird
			sExpression = objFileIniTest.getPropertyValue("Section for testComputePathWithMath", "Formula2");
			sValue="Der dynamische Wert2 ist '8'. FGL rulez."; //Also der Wert ohne die Math auszurechnen.
			assertTrue("Im Ergebnis wurde eine ausgerechnete '8' erwartet.", StringZZZ.contains(sExpression, "8"));
			assertEquals(sValue, sExpression);
			
			//+++ Verschachtelt, hier wird auf eine Section Verwiesen, die einen Math-Ausdruck enthält, der wiederum eine IniPath-Anweisung hat.... diesmal 2 Path Anweisungen!!!
			objFileIniTest.setFlag("useformula", true); //Damit der Wert sofort ausgerechnet wird
			objFileIniTest.setFlag("useformula_math", true); //Damit jetzt die MATH FORMEL ausgerechnet wird
			sExpression = objFileIniTest.getPropertyValue("Section for testComputePathWithMath", "Formula3");
			sValue="Der dynamische Wert3 ist '20'. FGL rulez."; //Also der Wert ohne die Math auszurechnen.
			assertTrue("Im Ergebnis wurde eine ausgerechnete '20' erwartet.", StringZZZ.contains(sExpression, "20"));
			assertEquals(sValue, sExpression);
			
			
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
		KernelExpressionIniSolverZZZ objSolverInit = new KernelExpressionIniSolverZZZ();
		
		//B01) Teste mal welche FlagZ es gemeinsam gibt.
		String[] saTestB01 = objFileIniInit.getFlagZ_passable(objSolverInit);
		assertTrue("Es wurden auf dieser Ebenen der Objekthierarrchie 3 FlagZ (oder mehr) als Gemeinsamkeit erwartet: INIT, DEBUG, USEFORMULA_MATH, ... .",saTestB01.length>=3);
		
		//B02) Teste welche von den gemeinsamen FlagZ hier True gesetzt sind.
		//objFileInit.getFlagZ_passable(true, sTargetClassnameForThePass);
		String[] saTestB02 = objFileIniInit.getFlagZ_passable(true, objSolverInit);
		assertTrue("Es wurden auf dieser Ebenen der Objekthierarrchie JETZT EIN FLAG WENIGER für 'true' erwartet.",saTestB02.length==saTestB01.length-1);
		
	}catch(ExceptionZZZ ez){
		fail("An exception happend testing: " + ez.getDetailAllLast());
	}
		
	}

	
}//END class
	
