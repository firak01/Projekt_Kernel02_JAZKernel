package basic.zKernel.file.ini;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IObjectWithExpressionZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedTestCaseZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedTestFlagsetZZZ;
import basic.zBasic.util.abstractList.HashMapCaseInsensitiveZZZ;
import basic.zBasic.util.abstractList.Vector3ZZZ;
import basic.zBasic.util.abstractList.VectorUtilZZZ;
import basic.zBasic.util.crypt.code.ICharacterPoolEnabledZZZ;
import basic.zBasic.util.crypt.code.IROTUserZZZ;
import basic.zBasic.util.crypt.code.ROT13ZZZ;
import basic.zBasic.util.crypt.code.ROTnnZZZ;
import basic.zBasic.util.crypt.code.ROTnumericZZZ;
import basic.zBasic.util.datatype.calling.ReferenceZZZ;
import basic.zBasic.util.datatype.character.CharacterExtendedZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.datatype.xml.XmlUtilZZZ;
import basic.zBasic.util.file.FileEasyZZZ;
import basic.zBasic.util.stream.IStreamZZZ;
import basic.zBasic.util.stream.StreamZZZ;
import basic.zKernel.IKernelConfigSectionEntryZZZ;
import basic.zKernel.KernelZZZ;
import basic.zKernel.config.KernelConfigSectionEntryUtilZZZ;
import custom.zKernel.file.ini.FileIniZZZ;
import junit.framework.TestCase;

public class KernelZFormulaIniSolverZZZTest extends TestCase {
	protected final static String sEXPRESSION_FORMULA_MATH_SOURCE01_CONTENT = "<Z:formula><Z:math><Z:val>2</Z:val><Z:op>*</Z:op><Z:val>3</Z:val></Z:math></Z:formula>";
	protected final static String sEXPRESSION_FORMULA_MATH_SOURCE01 = "Der dynamische Wert ist '<Z>"+ KernelZFormulaIniSolverZZZTest.sEXPRESSION_FORMULA_MATH_SOURCE01_CONTENT +"</Z>'. FGL rulez.";
	
	protected final static String sEXPRESSION_FORMULA_MATH_SOLVED01_CONTENT = "6";
	protected final static String sEXPRESSION_FORMULA_MATH_SOLVED01 = "Der dynamische Wert ist '<Z>"+ KernelZFormulaIniSolverZZZTest.sEXPRESSION_FORMULA_MATH_SOLVED01_CONTENT +"</Z>'. FGL rulez.";	
	
	private File objFile;
	private FileIniZZZ objFileIniInit;
	private FileIniZZZ objFileIniTest;
	private KernelZZZ objKernel;
	
	/// +++ Die eigentlichen Test-Objekte	
	private KernelZFormulaIniSolverZZZ objFormulaSolver=null;
	private KernelZFormulaIniSolverZZZ objFormulaSolverInit=null;
	
	
	protected void setUp(){
		try {										
			//Kernel + Log - Object dem TestFixture hinzufuegen. Siehe test.zzzKernel.KernelZZZTest
			objKernel = new KernelZZZ("FGL", "01", "test", "ZKernelConfigKernel_test.ini",(String)null);
			
			
			//#### Ein init TestObjekt
			String[] saFlagInit = {"init"};
			objFileIniInit = new FileIniZZZ(saFlagInit);
			
			objFormulaSolverInit = new KernelZFormulaIniSolverZZZ<Object>(saFlagInit);									
			
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
			
			//Für die Variablenersetzung wichtig: Eine HashMap mit den Variablen in der Ini-Datei.
			HashMapCaseInsensitiveZZZ<String,String> hmVariable = new HashMapCaseInsensitiveZZZ<String,String>();
			hmVariable.put("myTestVariableString", "mySolvedTestVariableString");
			objFileIniTest = new FileIniZZZ(objKernel,  objFile, hmVariable,  saFlagFileIni);			 											

			//Wichtig: Wenn hier der Solver definiert ist, muessen bei jedem Parsen/Solven die Internen Werte neu gesetzt werden. Sonst wird ein zweiter Aufruf verfälscht.
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
			assertEquals(9, saFlag.length);
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}
	
	/** void, Test: Reading an entry in a section of the ini-file
	* Lindhauer; 22.04.2006 12:54:32
	 */
	public void testCompute(){
		try {
			boolean btemp;
			String sValue;
			String sSection; String sProperty;
			String sExpression1In; String sExpression1SolvedIn; String sExpression2In; String sExpression2SolvedIn;
			String sExpression; String sExpressionSolved;
			
				
			//+++++++ VORGEZOGENER LETZTER FEHLERTEST START
			
			
		
			//+++++++ VORGEZOGENER LETZTER FEHLERTEST ENDE
			
			
			
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
			
			//+++++++++++++++++++++++++++++++++++++++		
			//###########################################################################						
			//+++++++++++++++++++++++++++++++++++++++
			
			//################################################
			//### mit Solver, direkt
			sExpression1In = "Der dynamische Wert1 ist '<Z>[Section A]Testentry1</Z>'. FGL rulez.";
			sExpression1SolvedIn = "Der dynamische Wert1 ist '<Z>Testvalue1 to be found</Z>'. FGL rulez.";
			
			sExpression2In = "Der dynamische Wert2 ist '<Z>[Section B]Testentry2</Z>'. FGL rulez.";
			sExpression2SolvedIn = "Der dynamische Wert2 ist '<Z>Testvalue2 local to be found</Z>'. FGL rulez.";
			 
			String sTagStartZ = "<Z>";
			String sTagEndZ = "</Z>";
							
			//Wenn man die Expression_PATH-Behandlung ausstellt, dann werden auch beim "Solven" keine Pfade ersetzt
			//Die Z-Tags verschwinden trotzdem.
			sExpression = sExpression2In;
			sExpressionSolved = sExpression;
		    sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ, false);
			
			btemp = objFormulaSolver.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION,true); 
			assertTrue("Flag nicht vorhanden '" + IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
			
			btemp = objFormulaSolver.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH, false); //also nur die Expression aufloesen, aber den Pfad nicht aufloesen.
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
			
			btemp = objFormulaSolver.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER,true); //false: Damit der Pfad NICHT sofort ausgerechnet wird
			assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
			
			btemp = objFormulaSolver.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA, true);
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA + "'", btemp);
			
			boolean bUseExpressionGeneral = objFormulaSolver.isExpressionEnabledGeneral();
			boolean bUseSolver = objFormulaSolver.isSolverEnabledGeneral();
			
			sValue = objFormulaSolver.solve(sExpression);
			String sExpressionSolvedTemp = sExpressionSolved;
			if(bUseExpressionGeneral && bUseSolver){
				sExpressionSolvedTemp = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
			}
			assertEquals(sExpressionSolvedTemp, sValue);
			
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//Anstellen der PathSubstitution, die Z-Tags verschwinden.
			sExpression = sExpression2In;
			sExpressionSolved = sExpression2SolvedIn;
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ, false);
						
			btemp = objFormulaSolver.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH, true); //also nur die Expression aufloesen, aber den Pfad nicht aufloesen.
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);

			sValue = objFormulaSolver.solve(sExpression);
			assertEquals(sExpressionSolved, sValue);
			

			//########################################################################################
			//### BEIM 2ten SUCHEN (wohlgemerkt, gleicher JUnit-Test und gleich Testobjekte) 
			//### MUSS MAN EIN NEUES ERGEBNIS BEKOMMEN
			//########################################################################################			
			//### mit objFormulaSolver
			btemp = testCompute_2ndSolve_(sExpression1In, sExpression1SolvedIn);
			
			btemp = testCompute_2ndSolve_(sExpression2In, sExpression2SolvedIn);
						
			
			//### mit objFileIni				
			sSection = "Section for testCompute";
			sProperty = "Formula2";
			sExpressionSolved = "Der dynamische Wert2 ist '<Z>[Section B]Testentry2</Z>'. FGL rulez.";
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ, false);
			
			btemp = testCompute_2ndGetProperty_(sSection, sProperty, sExpressionSolved);
						
			//########################################################################################
			//### Ausrechnen Mathematischer Formeln ##################################################
			//########################################################################################			
			btemp = testCompute_GetPropertyWithFormulaMath_();
			
			btemp = testCompute_GetPropertyWithFormulaMathByPath_();
			
			
					
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}
	
	private boolean testCompute_2ndSolve_(String sExpressionIn, String sExpressionSolvedIn) {
		boolean btemp; 
		String sValue;
		String sExpression; String sExpressionSolved;
		
		
		String sTagStartZ = "<Z>";
		String sTagEndZ = "</Z>";
		
		main:{
			try {
				//++++++++++++++++++++++++++++++++++++++++++++++++++++++
				//Wenn man die PathSubstitution ausstellt, dann werden auch beim "Solven" keine Pfade ersetzt
				//Die Z-Tags sind trotzdem raus.
				sExpression = sExpressionIn;
				sExpressionSolved = sExpression;
				sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ, false);
				
				btemp = objFormulaSolver.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION,true); 
				assertTrue("Flag nicht vorhanden '" + IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
							
				btemp = objFormulaSolver.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH, false); //also nur die Expression aufloesen, aber den Pfad nicht aufloesen.
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
		
				btemp = objFormulaSolver.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER,true); //false: Damit der Pfad NICHT sofort ausgerechnet wird
				assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
							
				btemp = objFormulaSolver.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA, true);
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA + "'", btemp);
				
			
				sValue = objFormulaSolver.solve(sExpression);
				assertEquals(sExpressionSolved, sValue);
								
				//++++++++++++++++++++++++++++++++++++
				//PathSubstitution anstellen
				//Die Z-Tags bleiben raus.
				sExpression = sExpressionIn;
				sExpressionSolved = sExpressionSolvedIn;
				sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ, false);
				
				btemp = objFormulaSolver.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH, true); //also nur die Expression aufloesen, aber den Pfad nicht aufloesen.
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
		
				sValue = objFormulaSolver.solve(sExpression);
				assertEquals(sExpressionSolved, sValue);
			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			}
		}//end main:			
		return true;
	}
	
	private boolean testCompute_2ndGetProperty_(String sSectionIn, String sPropertyIn, String sExpressionSolvedIn) {
		boolean btemp; 
		String sValue; 
		String sSection; String sProperty;
		String sExpression; String sExpressionSolved;
	
		main:{
			sExpressionSolved = sExpressionSolvedIn;
			sSection = sSectionIn; 
			sProperty = sPropertyIn; 

			try {
				//1. Suche
				//Erst einmal soll der Wert nicht sofort ausgerechnet werden.			
				btemp = objFileIniTest.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION,true); 
				assertTrue("Flag nicht vorhanden '" + IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
			
				btemp = objFileIniTest.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH,false); //Pfade werden nicht aufgeloest
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
			
				//Erst wenn der Parser den Solver nutzen soll, kommt auch das vom solve() raus.
				btemp = objFileIniTest.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER,true);
				assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
							
				
				btemp = objFileIniTest.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA, false); //eh keine Formel drin, also sollte das egal sein.
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA + "'", btemp);
														
				//Merke: wg. Parsen sind die Z-Tags verschwunden.
				sExpression = "Der dynamische Wert2 ist '[Section B]Testentry2'. FGL rulez.";
				sValue = objFileIniTest.getPropertyValue(sSection, sProperty).getValue();
				assertEquals(sExpression, sValue);
			
				//####################################################
				//2. Suche: Muss auch funktionieren... es darf halt nix in irgendwelchen Objekten bleiben. Intern wird naemlich gecloned.
				sExpression = "Der dynamische Wert2 ist '<Z>[Section B]Testentry2</Z>'. FGL rulez.";
				sSection = "Section for testCompute";
				sProperty = "Formula1";
				
				//### mit objFileini
				//+++ KEINE Expression oder Formel verwenden dann ist die Aufloesung egal
				btemp = objFileIniTest.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION, false);
				assertTrue("Flag nicht vorhanden '" + IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
				
				btemp = objFileIniTest.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH, true); //muss egal sein, da EXPRESIONSOLVER = FALSE
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
						
				btemp = objFileIniTest.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER,true);
				assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
				 				
				btemp = objFileIniTest.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA, false); //eh keine Formel drin, also sollte das egal sein.
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA + "'", btemp);
							
				//Merke: Ohne Expression Behandlund duefen trotz parsen die Z-Tags nicht verschwinden.
				sExpression = "Der dynamische Wert1 ist '<Z>[Section A]Testentry1</Z>'. FGL rulez.";
				sValue= objFileIniTest.getPropertyValue(sSection, sProperty).getValue();
				assertEquals(sExpression,sValue);
				
			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			}
		}//end main:			
		return true;
	}
	
	private boolean testCompute_GetPropertySystemNrBeforeGlobal_() {
		main:{
			boolean btemp; 
			
			String sValue; String sSection; String sProperty;
			String sExpression; String sExpressionSolved; String sTag; String sTagSolved;			
			ReferenceZZZ<IKernelConfigSectionEntryZZZ> objSectionEntryReference;
			IKernelConfigSectionEntryZZZ objEntry; IKernelConfigSectionEntryZZZ objEntryUsed;
			
			String sTagStartZ = "<Z>";
			String sTagEndZ = "</Z>";	
			IEnumSetMappedTestFlagsetZZZ objEnumFunction = EnumSetMappedTestCaseFlagsetTypeZZZ.UNEXPRESSED;
			
			
			sExpression = "Der dynamische Wert2 ist '<Z>[Section B]Testentry2</Z>'. FGL rulez.";
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
		boolean btemp; String sValue; 
		//String sSection; String sProperty;
		//String sExpressionSource;
		String sSection; String sProperty;
		String sExpressionSolved; //fuer den RAW Vergleich.

		main:{
			//sExpressionSource = "Der dynamische Wert2 ist '<Z>[Section B]Testentry2</Z>'. FGL rulez.";


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
				sSection = "Section for testCompute";
				sProperty = "Formula2";
				sExpressionSolved = "Der dynamische Wert2 ist '[Section B]Testentry2'. FGL rulez.";
				sValue = objFileIniTest.getPropertyValue(sSection, sProperty).getValue();
				assertEquals(sExpressionSolved, sValue);
				
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
				sExpressionSolved = "Der dynamische Wert2 ist '[Section B]Testentry2'. FGL rulez.";
				sValue = objFormulaSolver.parse(sExpressionSolved);
				assertEquals(sExpressionSolved, sValue);
				
				//Nun werden die Pfade in objFileIniTest aufgeloest
				btemp = objFileIniTest.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH, true);
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
				
				sExpressionSolved = "Der dynamische Wert2 ist 'Testvalue2 local to be found'. FGL rulez."; 
				sValue = objFileIniTest.getPropertyValue("Section for testCompute", "Formula2").getValue();
				assertEquals(sExpressionSolved, sValue);
														
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
		String sExpression; String sExpressionSubstituted; String sExpressionSolved; String sTag; String sTagSolved;
//		try {			
			sExpression = KernelZFormulaIniSolverZZZTest.sEXPRESSION_FORMULA_MATH_SOURCE01;	
			sExpressionSubstituted = KernelZFormulaIniSolverZZZTest.sEXPRESSION_FORMULA_MATH_SOURCE01;
			sExpressionSolved = KernelZFormulaIniSolverZZZTest.sEXPRESSION_FORMULA_MATH_SOLVED01;
			sTag = KernelZFormulaIniSolverZZZTest.sEXPRESSION_FORMULA_MATH_SOURCE01_CONTENT;
			sTagSolved = KernelZFormulaIniSolverZZZTest.sEXPRESSION_FORMULA_MATH_SOLVED01_CONTENT;
			testCompute_FORMULA_MATH_(sExpression, sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved);
//		} catch (ExceptionZZZ ez) {
//			fail("Method throws an exception." + ez.getMessageLast());
//		}
	
	}
	
	private boolean testCompute_FORMULA_MATH_(String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, String sTagIn, String sTagSolvedIn) {
		boolean bReturn = false;
		boolean btemp; String stemp; String sValue;
		String sExpression; String sExpressionSubstituted; String sExpressionSolved; String sTag; String sTagSolved;
		sExpression = sExpressionIn;	
		sExpressionSolved = sExpressionSolvedIn;
		sTagSolved = sTagSolvedIn;
		
		String sTagStartZ = "<Z>";
		String sTagEndZ = "</Z>";
		IEnumSetMappedTestFlagsetZZZ objEnumFunction = EnumSetMappedTestCaseFlagsetTypeZZZ.UNSOLVED;
				
		sExpression = sExpressionIn;
		sExpressionSubstituted = sExpressionSubstitutedIn;				
		sExpressionSolved = sExpressionSolvedIn;
		sTag = sTagIn;
		sTagSolved = sTagSolvedIn;
		
		main:{
			try {
				//+++++++ VORGEZOGENER LETZTER FEHLERTEST START
				
				
				//TODOGOON20250212;
				//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
				//+++ Mit FORMULA MATH-Berechnung
				
				//d)
				sExpressionSolved = sExpressionSolvedIn;
				sTagSolved = sTagSolvedIn;

				//Ersetze in dem Ausdruck eine Z-Tag mit der reinen Aufloesung
				stemp = ExpressionIniUtilZZZ.makeAsExpression(sTagSolved, sTagStartZ, sTagEndZ); //Z-Tags bleiben drin
				sExpressionSolved = StringZZZ.replace(sExpressionSolved, stemp, sTagSolved); 
				
				btemp = testCompute_FORMULA_MATH_5Solved_(sExpression, sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved, true, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);		
				//+++++++ VORGEZOGENER LETZTER FEHLERTEST ENDE
				
				//###########################
			    //### objExpression
				//#########################
		
				//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
				//+++ Ohne jegliche Expression-Berechnung
				//a)	
				sExpressionSolved = sExpressionIn;
				sTag=null;//es findet kein Parsen statt
				sTagSolved = null; //es findet kein Parsen statt
				
				btemp = testCompute_FORMULA_MATH_1Unexpressed_(sExpression, sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved, false, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
				
				//b)
				sExpressionSolved = sExpressionIn;
				sTag=null;//es findet kein Parsen statt
				sTagSolved = null; //es findet kein Parsen statt
				btemp = testCompute_FORMULA_MATH_1Unexpressed_(sExpression, sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved, true, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
				
				//c)			
				sExpressionSolved = sExpressionIn; 
				sTag=null;//es findet kein Parsen statt
				sTagSolved = null; //es findet kein Parsen statt
				btemp = testCompute_FORMULA_MATH_1Unexpressed_(sExpression, sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved, false, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
					
				//d)
				sExpressionSolved = sExpressionIn;
				sTag=null;//es findet kein Parsen statt
				sTagSolved = null; //es findet kein Parsen statt
				btemp = testCompute_FORMULA_MATH_1Unexpressed_(sExpression, sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved, true, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
				
				
				//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++				
				//+++ Ohne jegliche FORMULA_MATH Solver-Berechnung, 
				//a) //Z:Formula Tag muss drin bleiben, im Gesamtstring!!!
				sExpressionSolved = sExpressionIn;	
				sTag = sTagIn;
				sTag = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sTag, KernelZFormulaIniSolverZZZ.sTAG_NAME, false);//aber Z_FORMULA wird entfernt fuer den Tag-Wert an sich
				sTagSolved = sTag;
				btemp = testCompute_FORMULA_MATH_2SolverUnsolved_(sExpression, sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved, false, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
				
			
				//b)
				sExpressionSolved = sExpressionIn;	//Beim Parsen werden, wenn wie hier gewuenscht immer der Z-Tag entfernt.
				sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ, false);
				sTag = sTagIn;
				sTag = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sTag, KernelZFormulaIniSolverZZZ.sTAG_NAME, false);//aber Z_FORMULA wird entfernt fuer den Tag-Wert an sich
				sTagSolved = sTag;				
				btemp = testCompute_FORMULA_MATH_2SolverUnsolved_(sExpression, sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved, true, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
				
				//c)
				sExpression = sExpressionIn;
				sExpressionSolved = sExpressionIn; 
				sTag = sTagIn;
				sTag = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sTag, KernelZFormulaIniSolverZZZ.sTAG_NAME, false);//aber Z_FORMULA wird entfernt fuer den Tag-Wert an sich
				sTagSolved = sTag;
				btemp = testCompute_FORMULA_MATH_2SolverUnsolved_(sExpression, sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved, false, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
					
				//d)				
				sExpressionSolved = sExpressionIn;	//Beim Parsen werden, wenn wie hier gewuenscht immer der Z-Tag entfernt.
				sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ, false);
				sTag = sTagIn;
				sTag = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sTag, KernelZFormulaIniSolverZZZ.sTAG_NAME, false);//aber Z_FORMULA wird entfernt fuer den Tag-Wert an sich
				sTagSolved = sTag;
				btemp = testCompute_FORMULA_MATH_2SolverUnsolved_(sExpression, sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved, true, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
				
				
				//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
				//+++ Ohne FORMULA-Berechung
				//a)
				sExpressionSolved = sExpressionIn;
				sTag = sTagIn;
				sTag = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sTag, KernelZFormulaIniSolverZZZ.sTAG_NAME, false);//aber Z_FORMULA wird entfernt fuer den Tag-Wert an sich
				sTagSolved = sTag;
				btemp = testCompute_FORMULA_MATH_3FormulaUnsolved_(sExpression, sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved, false, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
				
				//b)
				sExpressionSolved = sExpressionIn;				
				sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ, false);
				//Beim Parsen ohne Solver, bleibt sogar das Encryption-Tag drin, auch wenn sonst die Tags entfernt werden.
				sTag = sTagIn;
				sTag = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sTag, KernelZFormulaIniSolverZZZ.sTAG_NAME, false);//aber Z_FORMULA wird entfernt fuer den Tag-Wert an sich
				sTagSolved = sTag;
				btemp = testCompute_FORMULA_MATH_3FormulaUnsolved_(sExpression, sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved, true, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
		
				//c)
				sExpressionSolved = sExpressionIn;
				//Beim Solven ohne Solver, bleibt alles wie est ist.
				sTag = sTagIn;
				sTag = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sTag, KernelZFormulaIniSolverZZZ.sTAG_NAME, false);//aber Z_FORMULA wird entfernt fuer den Tag-Wert an sich
				sTagSolved = sTag;
				btemp = testCompute_FORMULA_MATH_3FormulaUnsolved_(sExpression, sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved, false, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
				
				//d)
				sExpressionSolved = sExpressionIn;
				sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ, false);
				//Beim Solven ohne Solver, werden nur die äusseren Z-Tags ggfs. entfernt.
				sTag = sTagIn;
				sTag = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sTag, KernelZFormulaIniSolverZZZ.sTAG_NAME, false);//aber Z_FORMULA wird entfernt fuer den Tag-Wert an sich
				sTagSolved = sTag;
				btemp = testCompute_FORMULA_MATH_3FormulaUnsolved_(sExpression, sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved, true, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
				
				//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
				//+++ Ohne FORMULA_MATH-Berechung
				//a)
				sExpressionSolved = sExpressionIn;
				sTag = sTagIn;
				sTag = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sTag, KernelZFormulaIniSolverZZZ.sTAG_NAME, false);//aber Z_FORMULA wird entfernt fuer den Tag-Wert an sich
				sTagSolved = sTag;
				btemp = testCompute_FORMULA_MATH_4FormulaMathUnsolved_(sExpression, sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved, false, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
				
				//b)
				sExpressionSolved = sExpressionIn; 
				sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ, false);
				//Beim Solven ohne den passenden Unter-Solver, bleibt nur der Tag diese Solvers drin.
				sTag = sTagIn;
				sTag = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sTag, KernelZFormulaIniSolverZZZ.sTAG_NAME, false);//aber Z_FORMULA wird entfernt fuer den Tag-Wert an sich
				sTagSolved = sTag;
				btemp = testCompute_FORMULA_MATH_4FormulaMathUnsolved_(sExpression, sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved, true, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
				
				//c)
				sExpressionSolved = sExpressionIn;
				sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, KernelZFormulaIniSolverZZZ.sTAG_NAME, false);//aber Z_FORMULA wird entfernt fuer den Tag-Wert an sich
				//Beim Solven ohne den passenden Unter-Solver, bleibt nur der Tag diese Solvers drin.
				sTag = sTagIn;
				sTag = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sTag, KernelZFormulaIniSolverZZZ.sTAG_NAME, false);//aber Z_FORMULA wird entfernt fuer den Tag-Wert an sich
				sTagSolved = sTag;
				btemp = testCompute_FORMULA_MATH_4FormulaMathUnsolved_(sExpression, sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved, false, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
							
				//d)
				sExpressionSolved = sExpressionIn;	
				sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ, false);
				sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, KernelZFormulaIniSolverZZZ.sTAG_NAME, false);//aber Z_FORMULA wird entfernt fuer den Tag-Wert an sich
				//Beim Solven ohne encryption muss dieser encryption - Tag drinbleiben
				sTag = sTagIn;
				sTag = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sTag, KernelZFormulaIniSolverZZZ.sTAG_NAME, false);//aber Z_FORMULA wird entfernt fuer den Tag-Wert an sich
				sTagSolved = sTag;
				btemp = testCompute_FORMULA_MATH_4FormulaMathUnsolved_(sExpression, sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved, true, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
					
		
				//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
				//+++ Mit FORMULA MATH-Berechnung
				//a) nur parsen bringt keinen Unterschied, wenn die Tags drinbleiben sollen
				sExpressionSolved = sExpressionIn;
//				sExpressionSolved = objFormulaSolver.makeAsExpression(sExpressionSolved);
//				sExpressionSolved = ExpressionIniUtilZZZ.makeAsExpression(sExpressionSolved);
//				sExpressionSolved = ExpressionIniUtilZZZ.makeAsExpression(sExpressionSolved, "Z");
				
				//false: d.h. Tags sollen drin bleiben sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ, false);
				sTag = sTagIn;
				sTag = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sTagIn, KernelZFormulaIniSolverZZZ.sTAG_NAME, false);//aber Z_FORMULA wird entfernt fuer den Tag-Wert an sich
				sTagSolved = sTag; //Beim Parsen wird nicht aufgeloest;
				btemp = testCompute_FORMULA_MATH_5Solved_(sExpression, sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved, false, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
		
		
				//b) Werdem beim reinen Parsen die umgebenden Tags entfernt, dann wird NICHT das Formula-Math-Tag entfernt. 					
				sExpressionSolved = sExpressionIn; //Beim Parsen wird der Wert nicht berechnet.
				sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
				sTag = sTagIn;
				sTag = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sTagIn, KernelZFormulaIniSolverZZZ.sTAG_NAME, false);//aber Z_FORMULA wird entfernt fuer den Tag-Wert an sich
				sTagSolved = sTag; //Beim Parsen wird nicht aufgeloest;
				btemp = testCompute_FORMULA_MATH_5Solved_(sExpression, sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved, true, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
	
				
				//c) Aufloesen, aber den Z-Tag drinbehalten.
				sExpressionSolved = sExpressionSolvedIn;
				sTag = sTagIn;
				sTag = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sTag, KernelZFormulaIniSolverZZZ.sTAG_NAME, false);//aber Z_FORMULA wird entfernt fuer den Tag-Wert an sich
				sTagSolved = sTagSolvedIn;
				sTagSolved = ExpressionIniUtilZZZ.makeAsExpression(sTagSolved, sTagStartZ, sTagEndZ); //Z-Tags bleiben drin				
				btemp = testCompute_FORMULA_MATH_5Solved_(sExpression, sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved, false, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);

				
				//d)
				sExpressionSolved = sExpressionSolvedIn;
				sTagSolved = sTagSolvedIn;

				stemp = ExpressionIniUtilZZZ.makeAsExpression(sTagSolved, sTagStartZ, sTagEndZ); //Z-Tags bleiben drin
				sExpressionSolved = StringZZZ.replace(sExpressionSolved, stemp, sTagSolved); //Ersetze in dem Ausdruck eine Z-Tag mit der reinen Aufloesung
				
				btemp = testCompute_FORMULA_MATH_5Solved_(sExpression, sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved, true, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			}
			bReturn = true;
		}//end main:
		return bReturn;		
	}
	
	//########################################################################
	
	private boolean testCompute_FORMULA_MATH_1Unexpressed_(String sExpressionSourceIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, String sTagIn, String sTagSolvedIn, boolean bRemoveSurroundingSeparators, IEnumSetMappedTestCaseZZZ objEnumTestCase) {
		boolean bReturn = false;
		try {
			boolean btemp; 
			
			String sValue;
			String sExpression; String sExpressionSubstituted; String sExpressionSolved; String sTag; String sTagSolved;			
			ReferenceZZZ<IKernelConfigSectionEntryZZZ> objSectionEntryReference;
			IKernelConfigSectionEntryZZZ objEntry; IKernelConfigSectionEntryZZZ objEntryUsed;
			
			String sTagStartZ = "<Z>";
			String sTagEndZ = "</Z>";
			IEnumSetMappedTestFlagsetZZZ objEnumFunction = EnumSetMappedTestCaseFlagsetTypeZZZ.UNEXPRESSED;
			
			sExpression = sExpressionSourceIn;
			sExpressionSubstituted = sExpressionSubstitutedIn;
			sExpressionSolved = sExpressionSolvedIn;
			sTag = sTagIn;
			sTagSolved = sTagSolvedIn;
			
			//####################################################################################
			//### EXPRESSION nicht verarbeiten - FORMULA_MATH 
			//####################################################################################
		
			btemp = objFormulaSolver.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION, false); 
			assertTrue("Flag nicht vorhanden '" + IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
			
			btemp = objFormulaSolver.setFlag(IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER, true); //sollte egal sein			
			assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER + "'", btemp);
							
			btemp = objFormulaSolver.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH, true); //sollte egal sein
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
		
			btemp = objFormulaSolver.setFlag(IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE, true); //sollte egal sein			
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE + "'", btemp);
					
			btemp = objFormulaSolver.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER,true); //sollte egal sein 
			assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
			
			btemp = objFormulaSolver.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA,true); //sollte egal sein
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA + "'", btemp);
						
			btemp = objFormulaSolver.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH, true); //sollte egal sein
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH + "'", btemp);
														
			
						
			//+++ ... parse ist nicht solve... also wird hier nichts aufgeloest, aussser die Pfade
			if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.PARSE)) {	
				objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
				sValue = objFormulaSolver.parse(sExpression, objSectionEntryReference, bRemoveSurroundingSeparators);
				assertEquals(sExpressionSolved, sValue);
				
				sExpressionSolved = sExpressionSolvedIn; //der Wert des Tags selbst unterscheidet sich immer vom Wert der Zeile
				
				//Ausser wenn die Expression gar nicht behandelt wird. Dann ist das die ganze Zeile
				//sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ, false);
				//sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSolved, objFormulaSolver.getName(), false);								
				sValue = objFormulaSolver.getValue();
				assertEquals(null, sValue); //ohne jede Expressionbehandlung ist der Wert des Solver NULL
								
				objEntry = objSectionEntryReference.get();
				assertNotNull(objEntry);
				
				//Nutze eine Sammlung von assert Methoden, die ein objEntry als input hat.
				//und in der die verschiedenen stati für den unexpressed, unsubstituted, substituted, unsolved, etc Fall stehen.
				btemp = TestUtilAsTestZZZ.assertFileIniEntry(EnumSetMappedTestCaseSolverTypeZZZ.PARSE, objEnumFunction, objEntry, sExpression, sExpressionSubstituted, sExpressionSolved);
				assertTrue(btemp);
//				
//				
//				sValue = objEntry.getValue();
//				assertEquals(sExpressionSolved, sValue); //ohne jede Expressionbehandlung ist der Wert der Zeile unveraendert.
//				
//				assertTrue(objEntry.isParseCalled());
//				
//				sExpressionSolved = sExpressionSolvedIn;
//				if(sExpression.equals(sExpressionSolved)) {
//					assertFalse(objEntry.isParsedChanged());						
//				}else {
//					assertTrue(objEntry.isParsedChanged());
//				}
//				
//				assertFalse(objEntry.isSolveCalled()); //Der Solve-Schritt wurde gemacht.
//				assertFalse(objEntry.isSolvedChanged()); //es wird ja nix gemacht, also "unveraendert" 
//				
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
				
			//+++ ... solve verhält sich NICHT wie parse(), bei solve wird aufgeloest...
			if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.SOLVE)) {				
				objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
				sValue = objFormulaSolver.solve(sExpression, objSectionEntryReference, bRemoveSurroundingSeparators);
				assertEquals(sExpressionSolved, sValue);

				objEntry = objSectionEntryReference.get();
				assertNotNull(objEntry);
				
				//Nutze eine Sammlung von assert Methoden, die ein objEntry als input hat.
				//und in der die verschiedenen stati für den unexpressed, unsubstituted, substituted, unsolved, etc Fall stehen.
				btemp = TestUtilAsTestZZZ.assertFileIniEntry(EnumSetMappedTestCaseSolverTypeZZZ.SOLVE, objEnumFunction, objEntry, sExpression, sExpressionSubstituted, sExpressionSolved);
				assertTrue(btemp);
				
//				assertFalse(objEntry.isParseCalled()); //Wenn der Solver nicht ausgeführt wird, wird auch kein parser gestartet
//				assertFalse(objEntry.isParsedChanged()); //es wird ja nix gemacht, also "unveraendert"
//				
//				assertTrue(objEntry.isSolveCalled()); //Der Solve-Schritt wurde gemacht.
//				assertFalse(objEntry.isSolvedChanged()); //Ohne Expression Behandlung wird auch nichts geaendert.
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
				objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
				objEntry = objFormulaSolver.parseAsEntry(sExpression, bRemoveSurroundingSeparators);
				assertNotNull(objEntry);
				
				//Nutze eine Sammlung von assert Methoden, die ein objEntry als input hat.
				//und in der die verschiedenen stati für den unexpressed, unsubstituted, substituted, unsolved, etc Fall stehen.
				btemp = TestUtilAsTestZZZ.assertFileIniEntry(EnumSetMappedTestCaseSolverTypeZZZ.PARSE, objEnumFunction, objEntry, sExpression, sExpressionSubstituted, sExpressionSolved);
				assertTrue(btemp);
				
				
//				sValue = objEntryUsed.getValue();
//				assertEquals(sExpressionSolved, sValue);
//				
//				assertFalse(objEntryUsed.isParseCalled()); 
//				assertFalse(objEntryUsed.isParsedChanged());
//				
//				assertFalse(objEntryUsed.isSolveCalled()); //Der Solve-Schritt wurde gemacht.
//				assertFalse(objEntryUsed.isSolvedChanged()); //es wird ja nix gemacht, also "unveraendert" 
//																
//				assertFalse(objEntryUsed.isDecrypted());
//				assertNull(objEntryUsed.getValueDecrypted()); //Merke: sValue kann unterschiedlich zu dem decrypted Wert sein. Wenn etwas drumherum steht.
//				
//				assertFalse(objEntryUsed.isCall());
//				assertFalse(objEntryUsed.isJavaCall());
//				assertNull(objEntryUsed.getCallingClassname());
//				assertNull(objEntryUsed.getCallingMethodname());
			}
			
			//+++ ... solve verhält sich NICHT wie parse(), bei solve wird aufgeloest...
			if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.SOLVE_AS_ENTRY)) {
				objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
				objEntry = objFormulaSolver.solveAsEntry(sExpression, objSectionEntryReference, bRemoveSurroundingSeparators);
				assertNotNull(objEntry);
				
				//Nutze eine Sammlung von assert Methoden, die ein objEntry als input hat.
				//und in der die verschiedenen stati für den unexpressed, unsubstituted, substituted, unsolved, etc Fall stehen.
				btemp = TestUtilAsTestZZZ.assertFileIniEntry(EnumSetMappedTestCaseSolverTypeZZZ.SOLVE, objEnumFunction, objEntry, sExpression, sExpressionSubstituted,sExpressionSolved);
				assertTrue(btemp);
				
//				sValue = objEntry.getValue();
//				assertEquals(sExpressionSolved, sValue);
//				
//				assertFalse(objEntry.isParseCalled());
//				assertFalse(objEntry.isParsedChanged());
//								
//				assertTrue(objEntry.isSolveCalled()); //Der Solve-Schritt wurde gemacht.
//				assertFalse(objEntry.isSolvedChanged()); //es wird ja nix gemacht, also "unveraendert" 
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

			
			bReturn = true;
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}
		return bReturn;
	}


	//#############################################################################
	
	private boolean testCompute_FORMULA_MATH_2SolverUnsolved_(String sExpressionSourceIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, String sTagIn, String sTagSolvedIn, boolean bRemoveSurroundingSeparators, IEnumSetMappedTestCaseZZZ objEnumTestCase) {
		boolean bReturn = false;
		try {
			boolean btemp; 
			
			String sExpression; String sExpressionSubstituted; String sExpressionSolved; String sTag; String sTagSolved;
			String sValue; String sValueUsed;				
			ReferenceZZZ<IKernelConfigSectionEntryZZZ> objSectionEntryReference;
			IKernelConfigSectionEntryZZZ objEntry; IKernelConfigSectionEntryZZZ objEntryUsed;
			
			String sTagStartZ = "<Z>";
			String sTagEndZ = "</Z>";	
			IEnumSetMappedTestFlagsetZZZ objEnumFunction = EnumSetMappedTestCaseFlagsetTypeZZZ.UNSOLVED;
			
			sExpression = sExpressionSourceIn;
			sExpressionSubstituted = sExpressionSubstitutedIn;
			sExpressionSolved = sExpressionSolvedIn;
			sTag = sTagIn;
			sTagSolved = sTagSolvedIn;
			
			//####################################################################################
			//### EXPRESSION nicht verarbeiten - FORMULA_MATH 
			//####################################################################################
		
			btemp = objFormulaSolver.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION, true); 
			assertTrue("Flag nicht vorhanden '" + IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
			
			btemp = objFormulaSolver.setFlag(IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER, true);			
			assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER + "'", btemp);
							
			btemp = objFormulaSolver.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH, true);			
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
		
			btemp = objFormulaSolver.setFlag(IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE, true);			
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE + "'", btemp);
							
			btemp = objFormulaSolver.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER, false); 
			assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
			
			btemp = objFormulaSolver.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA,true); //sollte egal sein
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA + "'", btemp);
						
			btemp = objFormulaSolver.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH, true); //sollte egal sein
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH + "'", btemp);
														
			
						
			//+++ ... parse ist nicht solve... also wird hier nichts aufgeloest, aussser die Pfade
			if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.PARSE)) {
				objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
				sValue = objFormulaSolver.parse(sExpression, objSectionEntryReference, bRemoveSurroundingSeparators);
				assertEquals(sExpressionSolved, sValue);
				
//				sExpressionSolved = sExpressionSolvedIn; //der Wert des Tags selbst unterscheidet sich immer vom Wert der Zeile
//				sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ, false);
//				sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSolved, objFormulaSolver.getName(), false);								
				sValue = objFormulaSolver.getValue();
				assertEquals(sTag, sValue); //nur parsen, nicht aufloesen.
				
				
				objEntry = objSectionEntryReference.get();
				assertNotNull(objEntry);

				//Nutze eine Sammlung von assert Methoden, die ein objEntry als input hat.
				//und in der die verschiedenen stati für den unexpressed, unsubstituted, substituted, unsolved, etc Fall stehen.
				btemp = TestUtilAsTestZZZ.assertFileIniEntry(EnumSetMappedTestCaseSolverTypeZZZ.PARSE, objEnumFunction, objEntry, sExpression, sExpressionSubstituted,sExpressionSolved);
				assertTrue(btemp);
				
			}
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
				
			//+++ ... solve verhält sich NICHT wie parse(), bei solve wird aufgeloest...
			if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.SOLVE)) {				
				objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
				sValue = objFormulaSolver.solve(sExpression, objSectionEntryReference, bRemoveSurroundingSeparators);
				assertEquals(sExpressionSolved, sValue);
				
				sValue = objFormulaSolver.getValue();
				assertEquals(sTagSolved, sValue);
				
				objEntry = objSectionEntryReference.get();
				assertNotNull(objEntry);
				
				//Nutze eine Sammlung von assert Methoden, die ein objEntry als input hat.
				//und in der die verschiedenen stati für den unexpressed, unsubstituted, substituted, unsolved, etc Fall stehen.
				btemp = TestUtilAsTestZZZ.assertFileIniEntry(EnumSetMappedTestCaseSolverTypeZZZ.SOLVE, objEnumFunction, objEntry, sExpression, sExpressionSubstituted,sExpressionSolved);
				assertTrue(btemp);
				
			}
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			
			//+++ Variante fuer den AsEntry-Test
			if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.PARSE_AS_ENTRY)) {			
				objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
				objEntry = objFormulaSolver.parseAsEntry(sExpression, bRemoveSurroundingSeparators);
				assertNotNull(objEntry);
				
				sValue = objFormulaSolver.getValue();
				assertEquals(sTag, sValue); //nur parsen, nicht aufloesen
				
				sValue = objEntry.getValue();
				assertEquals(sExpressionSolved, sValue);
			
				//Nutze eine Sammlung von assert Methoden, die ein objEntry als input hat.
				//und in der die verschiedenen stati für den unexpressed, unsubstituted, substituted, unsolved, etc Fall stehen.
				btemp = TestUtilAsTestZZZ.assertFileIniEntry(EnumSetMappedTestCaseSolverTypeZZZ.PARSE, objEnumFunction, objEntry, sExpression, sExpressionSubstituted,sExpressionSolved);
				assertTrue(btemp);
				
			}
			
			//+++ ... solve verhält sich NICHT wie parse(), bei solve wird aufgeloest...
			if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.SOLVE_AS_ENTRY)) {
				objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
				objEntry = objFormulaSolver.solveAsEntry(sExpression, objSectionEntryReference, bRemoveSurroundingSeparators);
				assertNotNull(objEntry);
				
				sValue = objFormulaSolver.getValue();
				assertEquals(sTagSolved, sValue);
				
				sValue = objEntry.getValue();
				assertEquals(sExpressionSolved, sValue);
				
				//Nutze eine Sammlung von assert Methoden, die ein objEntry als input hat.
				//und in der die verschiedenen stati für den unexpressed, unsubstituted, substituted, unsolved, etc Fall stehen.
				btemp = TestUtilAsTestZZZ.assertFileIniEntry(EnumSetMappedTestCaseSolverTypeZZZ.SOLVE, objEnumFunction, objEntry, sExpression, sExpressionSubstituted,sExpressionSolved);
				assertTrue(btemp);
				
			}
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

			
			bReturn = true;
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}
		return bReturn;
	}

	//########################################################################
	
	private boolean testCompute_FORMULA_MATH_3FormulaUnsolved_(String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, String sTagIn, String sTagSolvedIn, boolean bRemoveSurroundingSeparators, IEnumSetMappedTestCaseZZZ objEnumTestCase) {
		boolean bReturn = false;
		try {
			boolean btemp; 
			
			String sExpression; String sExpressionSubstituted; String sExpressionSolved; String sTag; String sTagSolved;
			String sValue; String sValueUsed;				
			ReferenceZZZ<IKernelConfigSectionEntryZZZ> objSectionEntryReference;
			IKernelConfigSectionEntryZZZ objEntry; IKernelConfigSectionEntryZZZ objEntryUsed;
			
			String sTagStartZ = "<Z>";
			String sTagEndZ = "</Z>";	
			IEnumSetMappedTestFlagsetZZZ objEnumFunction = EnumSetMappedTestCaseFlagsetTypeZZZ.UNSOLVED;
						
			sExpression = sExpressionIn;
			sExpressionSubstituted = sExpressionSubstitutedIn;
			sExpressionSolved = sExpressionSolvedIn;
			sTag = sTagIn;
			sTagSolved = sTagSolvedIn;
			
			//####################################################################################
			//### EXPRESSION nicht verarbeiten - FORMULA_MATH 
			//####################################################################################
		
			btemp = objFormulaSolver.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION, true); 
			assertTrue("Flag nicht vorhanden '" + IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
			
			btemp = objFormulaSolver.setFlag(IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER, true);			
			assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER + "'", btemp);
							
			btemp = objFormulaSolver.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH, true);			
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
		
			btemp = objFormulaSolver.setFlag(IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE, true);			
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE + "'", btemp);
				
			btemp = objFormulaSolver.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER, true);  
			assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
			
			btemp = objFormulaSolver.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA, false); 
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA + "'", btemp);
						
			btemp = objFormulaSolver.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH, true); //sollte egal sein
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH + "'", btemp);
														
									
			//+++ ... parse ist nicht solve... also wird hier nichts aufgeloest, aussser die Pfade
			if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.PARSE)) {
				
//				sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ, false);
//				sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSolved, objFormulaSolver.getName(), false);												
				sTagSolved = sTagSolvedIn; //der Wert des Tags selbst unterscheidet sich immer vom Wert der Zeile
				objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
				sValue = objFormulaSolver.parse(sExpression, objSectionEntryReference, bRemoveSurroundingSeparators);
				assertEquals(sExpressionSolved, sValue);
			
				sValue = objFormulaSolver.getValue();
				assertEquals(sTagSolved, sValue);
				
				
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
				assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingClassname() + "'", objEntry.getCallingClassname());
				assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingMethodname() + "'", objEntry.getCallingMethodname());
			}
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
				
			//+++ ... solve verhält sich NICHT wie parse(), bei solve wird aufgeloest...
			if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.SOLVE)) {
				objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
				sValue = objFormulaSolver.solve(sExpression, objSectionEntryReference, bRemoveSurroundingSeparators);
				assertEquals(sExpressionSolved, sValue);

				objEntry = objSectionEntryReference.get();
				assertNotNull(objEntry);
				
				//Nutze eine Sammlung von assert Methoden, die ein objEntry als input hat.
				//und in der die verschiedenen stati für den unexpressed, unsubstituted, substituted, unsolved, etc Fall stehen.
				btemp = TestUtilAsTestZZZ.assertFileIniEntry(EnumSetMappedTestCaseSolverTypeZZZ.SOLVE, objEnumFunction, objEntry, sExpression, sExpressionSubstituted,sExpressionSolved);
				assertTrue(btemp);
				
			}
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			
			//+++ Variante fuer den AsEntry-Test
			if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.PARSE_AS_ENTRY)) {
				objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
				objEntry = objFormulaSolver.parseAsEntry(sExpression, bRemoveSurroundingSeparators);
				assertNotNull(objEntry);
				
				sValue = objEntry.getValue();
				assertEquals(sExpressionSolved, sValue);
				
				
				//Nutze eine Sammlung von assert Methoden, die ein objEntry als input hat.
				//und in der die verschiedenen stati für den unexpressed, unsubstituted, substituted, unsolved, etc Fall stehen.
				btemp = TestUtilAsTestZZZ.assertFileIniEntry(EnumSetMappedTestCaseSolverTypeZZZ.SOLVE, objEnumFunction, objEntry, sExpression, sExpressionSubstituted,sExpressionSolved);
				assertTrue(btemp);
	
			}
			
			//+++ ... solve verhält sich NICHT wie parse(), bei solve wird aufgeloest...
			if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.SOLVE_AS_ENTRY)) {
				objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
				objEntry = objFormulaSolver.solveAsEntry(sExpression, objSectionEntryReference, bRemoveSurroundingSeparators);
				assertNotNull(objEntry);
				
				sValue = objEntry.getValue();
				assertEquals(sExpressionSolved, sValue);
				
				//Nutze eine Sammlung von assert Methoden, die ein objEntry als input hat.
				//und in der die verschiedenen stati für den unexpressed, unsubstituted, substituted, unsolved, etc Fall stehen.
				btemp = TestUtilAsTestZZZ.assertFileIniEntry(EnumSetMappedTestCaseSolverTypeZZZ.SOLVE, objEnumFunction, objEntry, sExpression, sExpressionSubstituted,sExpressionSolved);
				assertTrue(btemp);

			}
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

			
			bReturn = true;
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}
		return bReturn;
	}


	//########################################################################
	
	private boolean testCompute_FORMULA_MATH_4FormulaMathUnsolved_(String sExpressionSourceIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, String sTagIn, String sTagSolvedIn, boolean bRemoveSurroundingSeparators, IEnumSetMappedTestCaseZZZ objEnumTestCase) {
		boolean bReturn = false;
		try {
			boolean btemp; 
			
			String sExpression;	String sExpressionSubstituted; String sExpressionSolved; String sTag; String sTagSolved; 
			String sValue; String sValueUsed;				
			ReferenceZZZ<IKernelConfigSectionEntryZZZ> objSectionEntryReference;
			IKernelConfigSectionEntryZZZ objEntry; IKernelConfigSectionEntryZZZ objEntryUsed;

			String sTagStartZ = "<Z>";
			String sTagEndZ = "</Z>";	
			IEnumSetMappedTestFlagsetZZZ objEnumFunction = EnumSetMappedTestCaseFlagsetTypeZZZ.FORMULA_MATH_UNSOLVED;
			
			sExpression = sExpressionSourceIn;
			sExpressionSubstituted = sExpressionSubstitutedIn;
			sExpressionSolved = sExpressionSolvedIn;
			sTag = sTagIn;
			sTagSolved = sTagSolvedIn;
			
			//####################################################################################
			//### EXPRESSION nicht verarbeiten - FORMULA_MATH 
			//####################################################################################
		
			btemp = objFormulaSolver.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION, true); 
			assertTrue("Flag nicht vorhanden '" + IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
			
			btemp = objFormulaSolver.setFlag(IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER, true);  
			assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER + "'", btemp);
						
			
			btemp = objFormulaSolver.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH, true);			
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
		
			btemp = objFormulaSolver.setFlag(IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE, true);			
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE + "'", btemp);
						
			btemp = objFormulaSolver.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER, true);  
			assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
			
			btemp = objFormulaSolver.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA, true); 
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA + "'", btemp);
						
			btemp = objFormulaSolver.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH, false); 
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH + "'", btemp);
														
									
			//+++ ... parse ist nicht solve... also wird hier nichts aufgeloest, aussser die Pfade
			if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.PARSE)) {				
				objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
				sValue = objFormulaSolver.parse(sExpression, objSectionEntryReference, bRemoveSurroundingSeparators);
				assertEquals(sExpressionSolved, sValue);
			 
				sValue = objFormulaSolver.getValue();
				assertEquals(sTagSolved, sValue);
								
				objEntry = objSectionEntryReference.get();
				assertNotNull(objEntry);
				
				btemp = TestUtilAsTestZZZ.assertFileIniEntry(EnumSetMappedTestCaseSolverTypeZZZ.PARSE, objEnumFunction, objEntry, sExpression, sExpressionSubstituted, sExpressionSolved);
				assertTrue(btemp);
				
			}
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
				
			//+++ ... solve verhält sich NICHT wie parse(), bei solve wird aufgeloest...
			if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.SOLVE)) {
				objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
				sValue = objFormulaSolver.solve(sExpression, objSectionEntryReference, bRemoveSurroundingSeparators);
				assertEquals(sExpressionSolved, sValue);

				objEntry = objSectionEntryReference.get();
				assertNotNull(objEntry);
											
				btemp = TestUtilAsTestZZZ.assertFileIniEntry(EnumSetMappedTestCaseSolverTypeZZZ.SOLVE, objEnumFunction, objEntry, sExpression, sExpressionSubstituted, sExpressionSolved);
				assertTrue(btemp);
				
			}
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			
			//+++ Variante fuer den AsEntry-Test
			if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.PARSE_AS_ENTRY)) {
				objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
				objEntry = objFormulaSolver.parseAsEntry(sExpression, bRemoveSurroundingSeparators);
				assertNotNull(objEntry);

				sValue = objEntry.getValue();
				assertEquals(sExpressionSolved, sValue);
				
				btemp = TestUtilAsTestZZZ.assertFileIniEntry(EnumSetMappedTestCaseSolverTypeZZZ.PARSE, objEnumFunction, objEntry, sExpression, sExpressionSubstituted, sExpressionSolved);				
				assertTrue(btemp);

			}
			
			//+++ ... solve verhält sich NICHT wie parse(), bei solve wird aufgeloest...
			if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.SOLVE_AS_ENTRY)) {
				objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
				objEntry = objFormulaSolver.solveAsEntry(sExpression, objSectionEntryReference, bRemoveSurroundingSeparators);
				assertNotNull(objEntry);

				sValue = objEntry.getValue();
				assertEquals(sExpressionSolved, sValue);
				
				btemp = TestUtilAsTestZZZ.assertFileIniEntry(EnumSetMappedTestCaseSolverTypeZZZ.SOLVE, objEnumFunction, objEntry, sExpression, sExpressionSubstituted, sExpressionSolved);
				assertTrue(btemp);
				
			}
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

			
			bReturn = true;
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}
		return bReturn;
	}

	
	//########################################################################

	private boolean testCompute_FORMULA_MATH_5Solved_(String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, String sTagIn, String sTagSolvedIn, boolean bRemoveSurroundingSeparatorsOnSolve, IEnumSetMappedTestCaseZZZ objEnumTestCase) {
		boolean bReturn = false;
		try {
			boolean btemp; 
			String sValue; String sValueUsed; Vector3ZZZ<String> vecValue;	
			String sExpression; String sExpressionSubstituted; String sExpressionSolved;  String sTag; String sTagSolved;				
			ReferenceZZZ<IKernelConfigSectionEntryZZZ> objSectionEntryReference;
			IKernelConfigSectionEntryZZZ objEntry; IKernelConfigSectionEntryZZZ objEntryUsed;
			
			String sTagStartZ = "<Z>";
			String sTagEndZ = "</Z>";
			IEnumSetMappedTestFlagsetZZZ objEnumFunction = EnumSetMappedTestCaseFlagsetTypeZZZ.SOLVED;
			
			sExpression = sExpressionIn;
			sExpressionSubstituted = sExpressionSubstitutedIn;
			sExpressionSolved = sExpressionSolvedIn;
			sTag = sTagIn;
			sTagSolved = sTagSolvedIn; //Der Wert des Tags selbst unterscheidet sich von dem Wert der Zeile
			
			//##########################################
			//### Expression solved Fall
			//+++ Wert noch nicht ausrechnen: A			
			btemp = objFormulaSolver.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION, true); 
			assertTrue("Flag nicht vorhanden '" + IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
			
			btemp = objFormulaSolver.setFlag(IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER, true);			
			assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER + "'", btemp);
							
			btemp = objFormulaSolver.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH, true);			
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
		
			btemp = objFormulaSolver.setFlag(IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE, true);			
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE + "'", btemp);
					
			btemp = objFormulaSolver.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER,true); 
			assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
			
			btemp = objFormulaSolver.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA,true); //Damit der Wert sofort ausgerechnet wird
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA + "'", btemp);
						
			btemp = objFormulaSolver.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH,true); //Damit der Wert NICHT sofort ausgerechnet wird
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH + "'", btemp);
														
			
			
			//+++ ... parse ist nicht solve... also wird hier nichts aufgeloest, aussser die Pfade substituiert.
			if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.PARSE)) {
				
				
//				//+++ Teilberechnungen durchführen.
//				//    Es werden wg false die Tags nicht entfernt
//				vecValue = objFormulaSolver.parseFirstVector(sExpression, false);					
//				sValue = VectorUtilZZZ.implode(vecValue);
//				assertEquals(sExpression, sValue); //dann sollen auch die Z-Tags drumherum nicht entfernt werden.
//				
//				sValue = (String) vecValue.get(1);//in der 0ten Position ist der String vor der Map, in der 3ten Position ist der String nach der Map.
//				assertTrue(StringZZZ.contains(sExpressionSolved,sValue,false)); //da der Wert selbst nicht als Argument in der Methode uebergeben wurde, koennen wir nur auf Existenz im Gesamtergebnis pruefen.
			
				//+++ Teilberechnungen durchführen, aber es wird nur geparsed... kein solve. 
				vecValue = objFormulaSolver.parseFirstVector(sExpression, bRemoveSurroundingSeparatorsOnSolve);					
				sValue = VectorUtilZZZ.implode(vecValue);
				assertTrue(StringZZZ.contains(sValue,sTag,false)); //da der Wert selbst nicht als Argument in der Methode uebergeben wurde, koennen wir nur auf Existenz im Gesamtergebnis pruefen.				

				//String sExpressionSolvedTemp = ExpressionIniUtilZZZ.makeAsExpression(sExpressionSolved);//Beim parse fist Vector wird nie der Z-Tag drum herum entfernt. Das ist Aufgabe von parse().
				//sExpressionSolvedTemp = ExpressionIniUtilZZZ.makeAsExpression(sExpressionSolvedTemp, "JSON");
				//sExpressionSolvedTemp = objExpressionSolver.makeAsExpression(sExpressionSolvedTemp);
				assertEquals(sExpressionSolved, sValue); 
				
				sValue = (String) vecValue.get(1);//in der 0ten Position ist der String vor der Map, in der 3ten Position ist der String nach der Map.
				assertTrue(StringZZZ.contains(sExpressionSolved,sValue,false)); //da der Wert selbst nicht als Argument in der Methode uebergeben wurde, koennen wir nur auf Existenz im Gesamtergebnis pruefen.
				
				//+++ Nun die Gesamtberechnung durchführen, aber es wird nur geparsed... nicht aufgeloest.
				objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
				sValue = objFormulaSolver.parse(sExpression, objSectionEntryReference, bRemoveSurroundingSeparatorsOnSolve);
				assertEquals(sExpressionSolved, sValue);
					
				sValue = objFormulaSolver.getValue();
				assertEquals(sTag, sValue);
				
				objEntry = objSectionEntryReference.get();
				assertNotNull(objEntry);
				
				//Nutze eine Sammlung von assert Methoden, die ein objEntry als input hat.
				//und in der die verschiedenen stati für den unexpressed, unsubstituted, substituted, unsolved, etc Fall stehen.
				btemp = TestUtilAsTestZZZ.assertFileIniEntry(EnumSetMappedTestCaseSolverTypeZZZ.PARSE, objEnumFunction, objEntry, sExpression, sExpressionSubstituted, sExpressionSolved);
				assertTrue(btemp);
			
			}
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
				
			//+++ ... solve verhält sich NICHT wie parse(), bei solve wird aufgeloest...
			if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.SOLVE)) {				
				//TODOGOON20240114;//Beim solven muss der Wert ohne umgebenden z:formula-Tag zugewiesen werden.
				objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
				sValue = objFormulaSolver.solve(sExpression, objSectionEntryReference, bRemoveSurroundingSeparatorsOnSolve);
				assertEquals(sExpressionSolved, sValue);
				
				sValue = objFormulaSolver.getValue(); //Der geparste Wert des Tags selbst enthaelt keine umgebenden Tags. Also wieder den Tag rausrechnen.
				sTagSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sTagSolved, KernelZFormulaIniSolverZZZ.sTAG_NAME, false);
				sTagSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sTagSolved, sTagStartZ, sTagEndZ, false);				
				assertEquals(sTagSolved, sValue);
				
				objEntry = objSectionEntryReference.get();
				assertNotNull(objEntry);
				
				//Nutze eine Sammlung von assert Methoden, die ein objEntry als input hat.
				//und in der die verschiedenen stati für den unexpressed, unsubstituted, substituted, unsolved, etc Fall stehen.
				btemp = TestUtilAsTestZZZ.assertFileIniEntry(EnumSetMappedTestCaseSolverTypeZZZ.SOLVE, objEnumFunction, objEntry, sExpression, sExpressionSubstituted, sExpressionSolved);
				assertTrue(btemp);
			}
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			
			//+++ Variante fuer den AsEntry-Test
			if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.PARSE_AS_ENTRY)) {			
				objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
				objEntry = objFormulaSolver.parseAsEntry(sExpression, bRemoveSurroundingSeparatorsOnSolve);				
				assertNotNull(objEntry);
				
				//Nutze eine Sammlung von assert Methoden, die ein objEntry als input hat.
				//und in der die verschiedenen stati für den unexpressed, unsubstituted, substituted, unsolved, etc Fall stehen.
				btemp = TestUtilAsTestZZZ.assertFileIniEntry(EnumSetMappedTestCaseSolverTypeZZZ.PARSE, objEnumFunction, objEntry, sExpression, sExpressionSubstituted, sExpressionSolved);
				assertTrue(btemp);
				
			}
			
			
			//+++ ... solve verhält sich NICHT wie parse(), bei solve wird aufgeloest...
			if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.SOLVE_AS_ENTRY)) {
				objEntry = objFormulaSolver.solveAsEntry(sExpression, bRemoveSurroundingSeparatorsOnSolve);
				assertNotNull(objEntry);
				
				//Nutze eine Sammlung von assert Methoden, die ein objEntry als input hat.
				//und in der die verschiedenen stati für den unexpressed, unsubstituted, substituted, unsolved, etc Fall stehen.
				btemp = TestUtilAsTestZZZ.assertFileIniEntry(EnumSetMappedTestCaseSolverTypeZZZ.SOLVE, objEnumFunction, objEntry, sExpression, sExpressionSubstituted, sExpressionSolved);
				assertTrue(btemp);
				
			}
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++	
			
			bReturn = true;
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}
		return bReturn;
	}
	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	//###########################################################################
		/** void, Test: Reading an entry in a section of the ini-file
		* Lindhauer; 22.04.2006 12:54:32
		 */
		public boolean testCompute_FORMULA_PATH(){
			boolean bReturn = false;
			String sExpressionIn; String sExpressionSolvedIn;
			main:{
//				try {
					boolean btemp; 
					
					String sValue; String sSection; String sProperty;
					String sExpression; String sExpressionSolved; String sExpressionSubstituted; String sTag; String sTagSolved;			
					ReferenceZZZ<IKernelConfigSectionEntryZZZ> objSectionEntryReference;
					IKernelConfigSectionEntryZZZ objEntry; IKernelConfigSectionEntryZZZ objEntryUsed;
					
					String sTagStartZ = "<Z>";
					String sTagEndZ = "</Z>";	
					IEnumSetMappedTestFlagsetZZZ objEnumFunction = EnumSetMappedTestCaseFlagsetTypeZZZ.UNEXPRESSED;

					//#########################################################################
					//#### Vorgezogener letzter Fehlertest: START
					
					
					
					
					//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++	
					//3) Fuer [Section B] gibt es in der Testdatei einen "globalen" Eintrag, der nicht gefunden werden soll 
					//Anwenden der Formel, so dass ein localer Wert vor einem globalen Wert gefunden wird.
					/*
					objStreamFile.println("[Section for testCompute]");
					....
					objStreamFile.println("Formula2=Der dynamische Wert2 ist '<Z>[Section B]Testentry2</Z>'. FGL rulez.");
					
					objStreamFile.println("[Section B!01]");
					objStreamFile.println("Testentry2=Testvalue2 local to be found");
					
					objStreamFile.println("[Section B]");
					objStreamFile.println("Testentry2=Testvalue2 global. This should not be found");				
					*/								
					sExpression = "Der dynamische Wert2 ist <Z:formula>'{[Section B]Testentry2}'</Z:formula>. FGL rulez.";
					sExpressionSubstituted = "Der dynamische Wert2 ist <Z:formula>'Testvalue2 local to be found'</Z:formula>. FGL rulez."; 
					sExpressionSolved = "Der dynamische Wert2 ist 'Testvalue2 local to be found'. FGL rulez.";
					
					sTag = "'{[Section B]Testentry2}'";								
					sTagSolved = "'Testvalue2 local to be found'";
					testCompute_FORMULA_PATH_(sExpression, sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved);
					
					
					
					
					
					//#### Vorgezogener letzter Fehlertest: ENDE
					//#########################################################################
					
					
					
					
					
					
				    //### 1) TEST OHNE FORMULA TAG UM DIE SUBSTITUTION		
					//!!! Weil der Tag Z-Formula nicht in dem String enthalten ist, wird hier auch nichts ersetzt.
					sExpression = "Der dynamische Wert1 ist <Z>'{[Section A]Testentry1}'</Z>. FGL rulez.";			
					sExpressionSubstituted = "Der dynamische Wert1 ist <Z>'{[Section A]Testentry1}'</Z>. FGL rulez.";
					sExpressionSolved = sExpressionSubstituted;
					
					sTag = "";								
					sTagSolved = "";
					testCompute_FORMULA_PATH_(sExpression, sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved);
	
					//### 2) TEST MIT FORMULA TAG UM DIE SUBSTITUTION
					//!!! Hier ist der Tag Z-Formula um den String mit der Ersetzung enthalten, daher wird hier durch den KernelZFormulaIniSolver ersetzt.				
					sExpression = "Der dynamische Wert1 ist <Z:formula>'{[Section A]Testentry1}'</Z:formula>. FGL rulez.";
					sExpressionSubstituted = "Der dynamische Wert1 ist <Z:formula>'Testvalue1 to be found'</Z:formula>. FGL rulez.";
					sExpressionSolved = "Der dynamische Wert1 ist 'Testvalue1 to be found'. FGL rulez.";
					
					sTag = "'{[Section A]Testentry1}'";								
					sTagSolved = "'Testvalue1 to be found'";
					testCompute_FORMULA_PATH_(sExpression, sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved);
	
					//###############################
					//Wiederholungstest von 2)... der gleiche Test muss auch wieder funktionieren
					//20250111: Zwei Tests hintereinander. Das gibt einen Fehler.
					//Der dynamische Wert1 ist '<Z><Z>[Section A]Testentry1</Z></Z>'. FGL rulez.
					sExpression = "Der dynamische Wert1 ist <Z:formula>'{[Section A]Testentry1}'</Z:formula>. FGL rulez.";
					sExpressionSubstituted = "Der dynamische Wert1 ist <Z:formula>'Testvalue1 to be found'</Z:formula>. FGL rulez.";
					sExpressionSolved = "Der dynamische Wert1 ist 'Testvalue1 to be found'. FGL rulez.";
					
					sTag = "'{[Section A]Testentry1}'";						
					sTagSolved = "'Testvalue1 to be found'";
					testCompute_FORMULA_PATH_(sExpression, sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved);
				
					//################################
					//Wiederholungstest von 1)... der gleiche Test muss auch wieder funktionieren
					//20250111: Zwei Tests hintereinander. Das gibt einen Fehler.
					//!!! Weil der Tag Z-Formula nicht in dem String enthalten ist, wird hier auch nichts ersetzt.
					sExpression = "Der dynamische Wert1 ist <Z>'{[Section A]Testentry1}'</Z>. FGL rulez.";				
					sExpressionSubstituted = "Der dynamische Wert1 ist <Z>'{[Section A]Testentry1}'</Z>. FGL rulez.";
					sExpressionSolved = sExpressionSubstituted;
					
					sTag = "";
					sTagSolved = "";
					testCompute_FORMULA_PATH_(sExpression, sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved);
			
				
				//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++	
				//3) Fuer [Section B] gibt es in der Testdatei einen "globalen" Eintrag, der nicht gefunden werden soll 
				//Anwenden der Formel, so dass ein localer Wert vor einem globalen Wert gefunden wird.
				/*
				objStreamFile.println("[Section for testCompute]");
				....
				objStreamFile.println("Formula2=Der dynamische Wert2 ist '<Z>[Section B]Testentry2</Z>'. FGL rulez.");
				
				objStreamFile.println("[Section B!01]");
				objStreamFile.println("Testentry2=Testvalue2 local to be found");
				
				objStreamFile.println("[Section B]");
				objStreamFile.println("Testentry2=Testvalue2 global. This should not be found");				
				*/								
				sExpression = "Der dynamische Wert2 ist <Z:formula>'{[Section B]Testentry2}'</Z:formula>. FGL rulez.";
				sExpressionSubstituted = "Der dynamische Wert2 ist <Z:formula>'Testvalue2 local to be found'</Z:formula>. FGL rulez."; 
				sExpressionSolved = "Der dynamische Wert2 ist 'Testvalue2 local to be found'. FGL rulez.";
					
				sTag = "'{[Section B]Testentry2}'";								
				sTagSolved = "'Testvalue2 local to be found'";
				testCompute_FORMULA_PATH_(sExpression, sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved);
				
				
				//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
				//Reihenfolgetest von 1) ... nun muss der erste Test auch wieder funktionieren				
				//!!! Weil der Tag Z-Formula nicht in dem String enthalten ist, wird hier auch nichts ersetzt.
				sExpression = "Der dynamische Wert1 ist <Z>'{[Section A]Testentry1}'</Z>. FGL rulez.";			
				sExpressionSubstituted = "Der dynamische Wert1 ist <Z>'{[Section A]Testentry1}'</Z>. FGL rulez.";
				sExpressionSolved = sExpressionSubstituted;
				
				sTag = "";								
				sTagSolved = "";
				testCompute_FORMULA_PATH_(sExpression, sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved);

				
				bReturn = true;
//			} catch (ExceptionZZZ ez) {
//				fail("Method throws an exception." + ez.getMessageLast());
//			}
				}//end main:
				return bReturn;
		}
		
		private boolean testCompute_FORMULA_PATH_(String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, String sTagIn, String sTagSolvedIn) {
			boolean bReturn = false;
			//String sExpressionIn; String sExpressionSolvedIn;
			main:{
				try {
					boolean btemp; 
					
					String sValue; String sSection; String sProperty;
					String sExpression; String sExpressionSubstituted; String sExpressionSolved; String sTag; String sTagSolved;			
					ReferenceZZZ<IKernelConfigSectionEntryZZZ> objSectionEntryReference;
					IKernelConfigSectionEntryZZZ objEntry; IKernelConfigSectionEntryZZZ objEntryUsed;
					
					String sTagStartZ = "<Z>";
					String sTagEndZ = "</Z>";	
					IEnumSetMappedTestFlagsetZZZ objEnumFunction = null; //Z.B.: EnumSetMappedTestCaseFlagsetTypeZZZ.UNEXPRESSED;

					sExpression = sExpressionIn;
					sExpressionSubstituted = sExpressionSubstitutedIn;
					sExpressionSolved = sExpression;	//Beim Parsen werden, wenn wie hier gewuenscht immer der Z-Tag entfernt.
					sTag = sTagIn;
					sTagSolved = sTagSolvedIn;
					
					
					//Merke: Z:Formula ist als Tag fuer die Pfadsubstitution notwendig.
					String sTagParentStart = XmlUtilZZZ.computeTagPartOpening(KernelZFormulaIniSolverZZZ.sTAG_NAME);
					String sTagParentEnd =  XmlUtilZZZ.computeTagPartClosing(KernelZFormulaIniSolverZZZ.sTAG_NAME); 
					//##############################################################################
					//+++++++ VORGEZOGENER LETZTER FEHLERTEST START
				
					//+++ Ohne Path-Substitution

					
					//b)
					sExpression = sExpressionIn;
					sExpressionSubstituted = sExpressionSubstitutedIn;
					sTag = sTagIn;
					sTagSolved = sTagSolvedIn;
					
					//... spezielles
					sExpressionSolved = sExpressionIn; //Ohne Substitutionsbehandlung
					sTagSolved = sTagIn;
					
					//Nutze eine Methode mit "Wenn der Tag innerhalb eines anderen Tags (name) liegt".
					sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagContainedRemoved(sExpressionSolved, sTagStartZ, sTagEndZ, true, sTagParentStart, sTagParentEnd);
																
					//Beim Parsen wird auch ein Z-Tag entfernt als TAG-Value, wenn gewuenscht.
					sTag = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sTag, sTagStartZ, sTagEndZ, false);					
					sTagSolved = sTag;	
							
					btemp = testCompute_FORMULA_PATH_2Unsubstituted_(sExpression, sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved, true, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
					
					//+++++++ VORGEZOGENER LETZTER FEHLERTEST ENDE
					//##############################################################################
					
					
				//###########################
			    //### objExpression
				//#########################
		
				//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
				//+++ Ohne jegliche Expression-Berechnung
				//a) Ausdruck unbearbeitet und der TagWert selbst auch nicht aufgeloest!!"				
				sExpressionSolved = sExpressionIn; //Keine Expressionverarbeitung				
				sTagSolved = sTagIn;	
				btemp = testCompute_FORMULA_PATH_1Unexpressed_(sExpression, sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved, false, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
				
				//b)
				sExpressionSolved = sExpressionIn; //Keine Expressionverarbeitung				
				sTagSolved = sTagIn;
				btemp = testCompute_FORMULA_PATH_1Unexpressed_(sExpression, sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved, true, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
				
				//c)
				sExpressionSolved = sExpressionIn; //Keine Expressionverarbeitung 
				sTagSolved = sTagIn;
				btemp = testCompute_FORMULA_PATH_1Unexpressed_(sExpression, sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved, false, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
					
				//d)
				sExpressionSolved = sExpressionIn; //Keine Expressionverarbeitung
				sTagSolved = sTagIn;
				btemp = testCompute_FORMULA_PATH_1Unexpressed_(sExpression, sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved, true, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
				
				
				//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
				//+++ Ohne Path-Substitution
				//a)
				sExpression = sExpressionIn;
				sExpressionSubstituted = sExpressionSubstitutedIn;
				sTag = sTagIn;
				sTagSolved = sTagSolvedIn;
				
				//... spezielles
				sExpressionSolved = sExpressionIn; //Ohne Substitutionsbehandlung
				sTagSolved = sTagIn;
				btemp = testCompute_FORMULA_PATH_2Unsubstituted_(sExpression, sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved, false, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
		
				
				//b)
				sExpression = sExpressionIn;
				sExpressionSubstituted = sExpressionSubstitutedIn;
				sTag = sTagIn;
				sTagSolved = sTagSolvedIn;
				
				//... spezielles
				sExpressionSolved = sExpressionIn; //Ohne Substitutionsbehandlung
				sTagSolved = sTagIn;
				
				//Nutze eine Methode mit "Wenn der Tag innerhalb eines anderen Tags (name) liegt".
				sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagContainedRemoved(sExpressionSolved, sTagStartZ, sTagEndZ, true, sTagParentStart, sTagParentEnd);
															
				//Beim Parsen wird auch ein Z-Tag entfernt als TAG-Value, wenn gewuenscht.
				sTag = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sTag, sTagStartZ, sTagEndZ, false);					
				sTagSolved = sTag;	
						
				btemp = testCompute_FORMULA_PATH_2Unsubstituted_(sExpression, sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved, true, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
			
							
				//c)
				sExpression = sExpressionIn;
				sExpressionSubstituted = sExpressionSubstitutedIn;
				sTag = sTagIn;
				sTagSolved = sTagSolvedIn;
				
				//... spezielles
				sExpressionSolved = sExpressionIn; //Ohne Substitutionsbehandlung 
				sTagSolved = sTagIn;
				
				//Der Formula - Tag wird beim Solven aber entfernt, falls vorhanden
				sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagParentStart, sTagParentEnd, false);
				
				//Das gilt aber nicht beim Tag - Wert, wenn kein Pfad substituiert wird, d.h. er wird ja nicht erkannt. Dann ist das interne Z-Tag ein normaler Text.
				//sTagSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sTagSolved, sTagStartZ, sTagEndZ, false);
								
				btemp = testCompute_FORMULA_PATH_2Unsubstituted_(sExpression, sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved, false, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
			
				//d)
				sExpression = sExpressionIn;
				sExpressionSubstituted = sExpressionSubstitutedIn;
				sTag = sTagIn;
				sTagSolved = sTagSolvedIn;
				
				//... spezielles
				sExpressionSolved = sExpressionIn;	//Beim Parsen werden, wenn wie hier gewuenscht immer der Z-Tag entfernt.					
				sTagSolved = sTagIn;
				
				//Nutze eine Methode mit "Wenn der Tag innerhalb eines anderen Tags (name) liegt".
				sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagContainedRemoved(sExpressionSolved, sTagStartZ, sTagEndZ, true, sTagParentStart, sTagParentEnd);				
				//Der Formula - Tag wird beim Parsen aber entfernt, falls vorhanden
				sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagParentStart, sTagParentEnd, false);							
				//Beim Solven wird auch ein Z-Tag entfernt als TAG-Value
				sTag = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sTag, sTagStartZ, sTagEndZ, false);					
				sTagSolved = sTag;	
				
				btemp = testCompute_FORMULA_PATH_2Unsubstituted_(sExpression, sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved, true, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
			
				
				//+++ Mit Path-Substitution
				//a)
				sExpression = sExpressionIn;
				sExpressionSubstituted = sExpressionSubstitutedIn;
				sTag = sTagIn;
				sTagSolved = sTagSolvedIn;
				
				//... spezielles
				sExpressionSolved = sExpressionSubstitutedIn;//Beim Parsen bleibt der Z-Formula Tag drin					
				sTagSolved = sTagSolvedIn;
				btemp = testCompute_FORMULA_PATH_3Substituted_(sExpression, sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved, false, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
								
				//b)
				sExpression = sExpressionIn;
				sExpressionSubstituted = sExpressionSubstitutedIn;
				sTag = sTagIn;
				sTagSolved = sTagSolvedIn;
				
				//... spezielles
				sExpressionSolved = sExpressionSubstitutedIn;//Beim Parsen bleibt der Z-Formula Tag drin	
				sTagSolved = sTagSolvedIn;
				
				//Beim Parsen werden, wenn wie hier gewuenscht immer der Z-Tag entfernt, aber nur wenn ueberhaupt ein Formula-Tag drumherum liegt.
				//Nutze eine Methode mit "Wenn der Tag innerhalb eines anderen Tags (name) liegt".
				sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsContainedRemoved(sExpressionSolved, sTagStartZ, sTagEndZ, sTagParentStart, sTagParentEnd);   
				sTagSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sTagSolved, sTagStartZ, sTagEndZ, false);				
				btemp = testCompute_FORMULA_PATH_3Substituted_(sExpression, sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved, true, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
			
				//c)
				sExpression = sExpressionIn;
				sExpressionSubstituted = sExpressionSubstitutedIn;
				sTag = sTagIn;
				sTagSolved = sTagSolvedIn;
				
				//... spezielles
				sExpressionSolved = sExpressionSolvedIn; 
				sTagSolved = sTagSolvedIn;
				
				//Beim Solve werden immer der Z-Tag entfernt, aber nur wenn ueberhaupt ein Formula-Tag drumherum liegt.
				//Nutze eine Methode mit "Wenn der Tag innerhalb eines anderen Tags (name) liegt".
				//sTagSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sTagSolved, sTagStartZ, sTagEndZ, false);
				sTagSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagContainedRemoved(sTagSolved, sTagStartZ, sTagEndZ, false, sTagParentStart, sTagParentEnd);
				btemp = testCompute_FORMULA_PATH_3Substituted_(sExpression, sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved, false, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
				
				//d)
				sExpression = sExpressionIn;
				sExpressionSubstituted = sExpressionSubstitutedIn;
				sTag = sTagIn;
				sTagSolved = sTagSolvedIn;
				
				//... spezielles
				sExpressionSolved = sExpressionSolvedIn;						
				
				//Nutze eine Methode mit "Wenn der Tag innerhalb eines anderen Tags (name) liegt".
				sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagContainedRemoved(sExpressionSolved, sTagStartZ, sTagEndZ, false, sTagParentStart, sTagParentEnd);
				
				//Beim Solve werden immer der Z-Tag entfernt, aber nur wenn ueberhaupt ein Formula-Tag drumherum liegt.
				//Nutze eine Methode mit "Wenn der Tag innerhalb eines anderen Tags (name) liegt".
				//sTagSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sTagSolved, sTagStartZ, sTagEndZ, false);
				sTagSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagContainedRemoved(sTagSolved, sTagStartZ, sTagEndZ, false, sTagParentStart, sTagParentEnd);				
				btemp = testCompute_FORMULA_PATH_3Substituted_(sExpression, sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved, true, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
							
				bReturn = true;
			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			}	
			bReturn = true;
		}//end main:
			return bReturn;
		}
			
		private boolean testCompute_FORMULA_PATH_1Unexpressed_(String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, String sTagIn, String sTagSolvedIn, boolean bRemoveSurroundingSeparators, IEnumSetMappedTestCaseZZZ objEnumTestCase) {
			boolean bReturn = false;
			//String sExpressionIn; String sExpressionSolvedIn;
			main:{
				try {										
					boolean btemp; 
					
					String sValue; String sSection; String sProperty;
					String sExpression; String sExpressionSubstituted; String sExpressionSolved; String sTag; String sTagSolved;			
					ReferenceZZZ<IKernelConfigSectionEntryZZZ> objSectionEntryReference;
					IKernelConfigSectionEntryZZZ objEntry; IKernelConfigSectionEntryZZZ objEntryUsed;
					
					String sTagStartZ = "<Z>";
					String sTagEndZ = "</Z>";	
					IEnumSetMappedTestFlagsetZZZ objEnumFunction = EnumSetMappedTestCaseFlagsetTypeZZZ.UNEXPRESSED;
								
					sExpression = sExpressionIn;
					sExpressionSubstituted = sExpressionSubstitutedIn;
					sExpressionSolved = sExpressionSolvedIn;
					sTag = sTagIn;
					sExpressionSolved = sExpressionSolvedIn;
					
				//####################################################################################
				//### EXPRESSION nicht verarbeiten - FORMULA_PATH 
				//####################################################################################
			
				btemp = objFormulaSolver.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION, false); 
				assertTrue("Flag nicht vorhanden '" + IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
				
				btemp = objFormulaSolver.setFlag(IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER,true); //sollte egal sein 
				assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER + "'", btemp);
								
				btemp = objFormulaSolver.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH, true); //sollte egal sein
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
						
				btemp = objFormulaSolver.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER,true); //sollte egal sein 
				assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
				
				btemp = objFormulaSolver.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA,true); //sollte egal sein
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA + "'", btemp);
								
				btemp = objFormulaSolver.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH, true); //sollte egal sein
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH + "'", btemp);
															
				
				//+++ ... parse ist nicht solve... also wird hier nichts aufgeloest, aussser die Pfade
				if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.PARSE)) {						
					objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
					sValue = objFormulaSolver.parse(sExpression, objSectionEntryReference, bRemoveSurroundingSeparators);
					assertEquals(sExpressionSolved, sValue);
																				
					//Hier erwarte ich einen NULL Wert, da ohne Expressionbehandlung auch nicht geparsed wird.
					sValue = objFormulaSolver.getValue();					
					assertNull("NULL erwartet. Wert ist aber '" + sValue + "'", sValue);
										
					objEntry = objSectionEntryReference.get();
					assertNotNull(objEntry);
					
					//Nutze eine Sammlung von assert Methoden, die ein objEntry als input hat.
					//und in der die verschiedenen stati für den unexpressed, unsubstituted, substituted, unsolved, etc Fall stehen.
					btemp = TestUtilAsTestZZZ.assertFileIniEntry(EnumSetMappedTestCaseSolverTypeZZZ.PARSE, objEnumFunction, objEntry, sExpression, sExpressionSubstituted, sExpressionSolved);
					assertTrue(btemp);
				}
				//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
					
				//+++ ... solve verhält sich NICHT wie parse(), bei solve wird aufgeloest...
				if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.SOLVE)) {					
					objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
					sValue = objFormulaSolver.solve(sExpression, objSectionEntryReference, bRemoveSurroundingSeparators);
					assertEquals(sExpressionSolved, sValue);

					//Hier erwarte ich einen NULL Wert, das ohne Expression auch nicht geparsed wird.
					sValue = objFormulaSolver.getValue();					
					assertNull("NULL erwartet. Wert ist aber '" + sValue + "'", sValue);
			
					objEntry = objSectionEntryReference.get();
					assertNotNull(objEntry);
										
					//Nutze eine Sammlung von assert Methoden, die ein objEntry als input hat.
					//und in der die verschiedenen stati für den unexpressed, unsubstituted, substituted, unsolved, etc Fall stehen.
					btemp = TestUtilAsTestZZZ.assertFileIniEntry(EnumSetMappedTestCaseSolverTypeZZZ.SOLVE, EnumSetMappedTestCaseFlagsetTypeZZZ.UNEXPRESSED, objEntry, sExpression, sExpressionSubstituted, sExpressionSolved);
					assertTrue(btemp);					
				}
				//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
				
				//+++ Variante fuer den AsEntry-Test
				if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.PARSE_AS_ENTRY)) {					
					objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
					objEntry = objFormulaSolver.parseAsEntry(sExpression, bRemoveSurroundingSeparators);
					assertNotNull(objEntry);
					
					//Nutze eine Sammlung von assert Methoden, die ein objEntry als input hat.
					//und in der die verschiedenen stati für den unexpressed, unsubstituted, substituted, unsolved, etc Fall stehen.
					btemp = TestUtilAsTestZZZ.assertFileIniEntry(EnumSetMappedTestCaseSolverTypeZZZ.PARSE, EnumSetMappedTestCaseFlagsetTypeZZZ.UNEXPRESSED, objEntry, sExpression, sExpressionSubstituted, sExpressionSolved);
					assertTrue(btemp);
					
				}
				
				//+++ ... solve verhält sich NICHT wie parse(), bei solve wird aufgeloest...
				if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.SOLVE_AS_ENTRY)) {					
					objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
					objEntry = objFormulaSolver.solveAsEntry(sExpression, objSectionEntryReference, bRemoveSurroundingSeparators);
					assertNotNull(objEntry);
					
					//Nutze eine Sammlung von assert Methoden, die ein objEntry als input hat.
					//und in der die verschiedenen stati für den unexpressed, unsubstituted, substituted, unsolved, etc Fall stehen.
					btemp = TestUtilAsTestZZZ.assertFileIniEntry(EnumSetMappedTestCaseSolverTypeZZZ.SOLVE, EnumSetMappedTestCaseFlagsetTypeZZZ.UNEXPRESSED, objEntry, sExpression, sExpressionSubstituted, sExpressionSolved);
					assertTrue(btemp);
					
				}
				//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++	

			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			}
				bReturn = true;
			}//end main;
			return bReturn;
		}

		//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		
		private boolean testCompute_FORMULA_PATH_2Unsubstituted_(String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, String sTagIn, String sTagSolvedIn, boolean bRemoveSurroundingSeparators, IEnumSetMappedTestCaseZZZ objEnumTestCase) {
			boolean bReturn = false;
			//String sExpressionIn; String sExpressionSolvedIn;
			main:{
				try {
					boolean btemp; 
					
					String sValue; String sSection; String sProperty;
					String sExpression; String sExpressionSubstituted; String sExpressionSolved; String sTag; String sTagSolved;			
					ReferenceZZZ<IKernelConfigSectionEntryZZZ> objSectionEntryReference;
					IKernelConfigSectionEntryZZZ objEntry; IKernelConfigSectionEntryZZZ objEntryUsed;
					
					String sTagStartZ = "<Z>";
					String sTagEndZ = "</Z>";	
					IEnumSetMappedTestFlagsetZZZ objEnumFunction = EnumSetMappedTestCaseFlagsetTypeZZZ.PATH_UNSUBSTITUTED;
			
					sExpression = sExpressionIn;
					sExpressionSubstituted = sExpressionSubstitutedIn;
					sExpressionSolved = sExpressionSolvedIn;
					sTag = sTagIn;
					sTagSolved = sTagSolvedIn;

							
				//####################################################################################
				//### EXPRESSION nicht verarbeiten - FORMULA_PATH 
				//####################################################################################
			
				btemp = objFormulaSolver.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION, true); 
				assertTrue("Flag nicht vorhanden '" + IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
				
				btemp = objFormulaSolver.setFlag(IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER,true); 
				assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER + "'", btemp);
				
				btemp = objFormulaSolver.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH, false); //OHNE PATHSUBSTITUTION
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
		
				
				btemp = objFormulaSolver.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER,true); 
				assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
				
				btemp = objFormulaSolver.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA,true); 
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA + "'", btemp);
				
				
				btemp = objFormulaSolver.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH, true);
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH + "'", btemp);
															
				
							
				//+++ ... parse ist nicht solve... also wird hier nichts aufgeloest, aussser die Pfade
				if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.PARSE)) {						
					objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
					sValue = objFormulaSolver.parse(sExpression, objSectionEntryReference, bRemoveSurroundingSeparators);
					assertEquals(sExpressionSolved, sValue);
					
					 
					
					//Der Wert des Tags selbst unterscheidet sich immer vom Wert der Zeile					
					//Beim Solver gilt: Ausser wenn die Expression gar nicht behandelt wird. Dann ist das die ganze Zeile
					//sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ, false);
					//sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSolved, objFormulaSolver.getName(), false);								
					sValue = objFormulaSolver.getValue();
					assertNotNull(objFormulaSolver.getValue());//Nur NULL, wenn kein Parser angestellt ist.
					assertEquals(sTag, sValue);
					
					
					objEntry = objSectionEntryReference.get();
					assertNotNull(objEntry);

					//Nutze eine Sammlung von assert Methoden, die ein objEntry als input hat.
					//und in der die verschiedenen stati für den unexpressed, unsubstituted, substituted, unsolved, etc Fall stehen.
					btemp = TestUtilAsTestZZZ.assertFileIniEntry(EnumSetMappedTestCaseSolverTypeZZZ.PARSE, objEnumFunction, objEntry, sExpression, sExpressionSubstituted, sExpressionSolved);
					assertTrue(btemp);
					
				}
				//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
					
				//+++ ... solve verhält sich NICHT wie parse(), bei solve wird aufgeloest...
				if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.SOLVE)) {					
					objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
					sValue = objFormulaSolver.solve(sExpression, objSectionEntryReference, bRemoveSurroundingSeparators);
					assertEquals(sExpressionSolved, sValue);

					//Ausser wenn die Expression gar nicht behandelt wird. Dann ist das die ganze Zeile
					//sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ, false);
					//sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSolved, objFormulaSolver.getName(), false);								
					sValue = objFormulaSolver.getValue();
					assertNotNull(objFormulaSolver.getValue());
					assertEquals(sTagSolved, sValue);
					
					objEntry = objSectionEntryReference.get();
					assertNotNull(objEntry);
					
					//Nutze eine Sammlung von assert Methoden, die ein objEntry als input hat.
					//und in der die verschiedenen stati für den unexpressed, unsubstituted, substituted, unsolved, etc Fall stehen.
					btemp = TestUtilAsTestZZZ.assertFileIniEntry(EnumSetMappedTestCaseSolverTypeZZZ.SOLVE, objEnumFunction, objEntry, sExpression, sExpressionSubstituted, sExpressionSolved);
					assertTrue(btemp);

				}
				//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
				
				//+++ Variante fuer den AsEntry-Test
				if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.PARSE_AS_ENTRY)) {					
					objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
					objEntry = objFormulaSolver.parseAsEntry(sExpression, bRemoveSurroundingSeparators);
					assertNotNull(objEntry);
					
					sValue = objEntry.getValue();
					assertEquals(sExpressionSolved, sValue);
					
					
					//Ausser wenn die Expression gar nicht behandelt wird. Dann ist das die ganze Zeile
					//sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ, false);
					//sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSolved, objFormulaSolver.getName(), false);								
					sValue = objFormulaSolver.getValue();
					assertNotNull(objFormulaSolver.getValue());
					assertEquals(sTag, sValue);
				
					//Nutze eine Sammlung von assert Methoden, die ein objEntry als input hat.
					//und in der die verschiedenen stati für den unexpressed, unsubstituted, substituted, unsolved, etc Fall stehen.
					btemp = TestUtilAsTestZZZ.assertFileIniEntry(EnumSetMappedTestCaseSolverTypeZZZ.PARSE, objEnumFunction, objEntry, sExpression, sExpressionSubstituted, sExpressionSolved);
					assertTrue(btemp);
					
				}
				
				//+++ ... solve verhält sich NICHT wie parse(), bei solve wird aufgeloest...
				if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.SOLVE_AS_ENTRY)) {					
					objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
					objEntry = objFormulaSolver.solveAsEntry(sExpression, objSectionEntryReference, bRemoveSurroundingSeparators);
					assertNotNull(objEntry);
					
					sValue = objEntry.getValue();
					assertEquals(sExpressionSolved, sValue);
					
					//Ausser wenn die Expression gar nicht behandelt wird. Dann ist das die ganze Zeile
					//sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ, false);
					//sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSolved, objFormulaSolver.getName(), false);								
					sValue = objFormulaSolver.getValue();
					assertNotNull(objFormulaSolver.getValue());
					assertEquals(sTagSolved, sValue);
				
					//Nutze eine Sammlung von assert Methoden, die ein objEntry als input hat.
					//und in der die verschiedenen stati für den unexpressed, unsubstituted, substituted, unsolved, etc Fall stehen.
					btemp = TestUtilAsTestZZZ.assertFileIniEntry(EnumSetMappedTestCaseSolverTypeZZZ.SOLVE, objEnumFunction, objEntry, sExpression, sExpressionSubstituted, sExpressionSolved);
					assertTrue(btemp);
			
				}
				//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

			
			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			}
				bReturn = true;
			}//end main:
			return bReturn;
		}

		//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		
		private boolean testCompute_FORMULA_PATH_3Substituted_(String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, String sTagIn, String sTagSolvedIn, boolean bRemoveSurroundingSeparators, IEnumSetMappedTestCaseZZZ objEnumTestCase) {
			boolean bReturn = false;
			//String sExpressionIn; String sExpressionSolvedIn;
			main:{
				try {
					boolean btemp; 
					
					String sValue; String sSection; String sProperty;
					String sExpression; String sExpressionSubstituted; String sExpressionSolved; String sTag; String sTagSolved;			
					ReferenceZZZ<IKernelConfigSectionEntryZZZ> objSectionEntryReference;
					IKernelConfigSectionEntryZZZ objEntry; IKernelConfigSectionEntryZZZ objEntryUsed;
					
					String sTagStartZ = "<Z>";
					String sTagEndZ = "</Z>";	
					IEnumSetMappedTestFlagsetZZZ objEnumFunction = EnumSetMappedTestCaseFlagsetTypeZZZ.PATH_SUBSTITUTED;
			
					sExpression = sExpressionIn;	
					sExpressionSubstituted = sExpressionSubstitutedIn;
					sExpressionSolved = sExpressionSolvedIn;
					sTag = sTagIn;
					sTagSolved = sTagSolvedIn;
					
					
					//####################################################################################
					//### EXPRESSION nicht verarbeiten - FORMULA_PATH 
					//####################################################################################
				
					btemp = objFormulaSolver.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION, true); 
					assertTrue("Flag nicht vorhanden '" + IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
					
					btemp = objFormulaSolver.setFlag(IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER, true);			
					assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER + "'", btemp);
									
					btemp = objFormulaSolver.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH, true);
					assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
			
					//andere Flags auf false stellen, um die reine substitution zu testen
					btemp = objFormulaSolver.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER,true); 
					assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
					
					btemp = objFormulaSolver.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA, true);
					assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA + "'", btemp);
					
					
					btemp = objFormulaSolver.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH, false); //sollte egal sein
					assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH + "'", btemp);
																
					
							
				//+++ ... parse ist nicht solve... also wird hier nichts aufgeloest, aussser die Pfade! D.h. auch der z:Formula Tag bleibt drin!!!
				if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.PARSE)) {					
					objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
					sValue = objFormulaSolver.parse(sExpression, objSectionEntryReference, bRemoveSurroundingSeparators);
					assertEquals(sExpressionSolved, sValue);
					
					//Ausser wenn die Expression gar nicht behandelt wird. Dann ist das die ganze Zeile
					//sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ, false);
					//sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSolved, objFormulaSolver.getName(), false);								
					sValue = objFormulaSolver.getValue();
					assertNotNull(objFormulaSolver.getValue());
					assertEquals(sTagSolved, sValue);
										
					objEntry = objSectionEntryReference.get();
					assertNotNull(objEntry);
					
					//Nutze eine Sammlung von assert Methoden, die ein objEntry als input hat.
					//und in der die verschiedenen stati für den unexpressed, unsubstituted, substituted, unsolved, etc Fall stehen.
					btemp = TestUtilAsTestZZZ.assertFileIniEntry(EnumSetMappedTestCaseSolverTypeZZZ.PARSE, objEnumFunction, objEntry, sExpression, sExpressionSubstituted, sExpressionSolved);
					assertTrue(btemp);
				}
				//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
					
				//+++ ... solve verhält sich NICHT wie parse(), bei solve wird aufgeloest...
				if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.SOLVE)) {					
					objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
					sValue = objFormulaSolver.solve(sExpression, objSectionEntryReference, bRemoveSurroundingSeparators);
					assertEquals(sExpressionSolved, sValue);

					//Ausser wenn die Expression gar nicht behandelt wird. Dann ist das die ganze Zeile
					//sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ, false);
					//sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSolved, objFormulaSolver.getName(), false);								
					sValue = objFormulaSolver.getValue();
					assertNotNull(objFormulaSolver.getValue());
					assertEquals(sTagSolved, sValue);
					
					objEntry = objSectionEntryReference.get();
					assertNotNull(objEntry);
					
					//Nutze eine Sammlung von assert Methoden, die ein objEntry als input hat.
					//und in der die verschiedenen stati für den unexpressed, unsubstituted, substituted, unsolved, etc Fall stehen.
					btemp = TestUtilAsTestZZZ.assertFileIniEntry(EnumSetMappedTestCaseSolverTypeZZZ.SOLVE, objEnumFunction, objEntry, sExpression, sExpressionSubstituted, sExpressionSolved);
					assertTrue(btemp);
					
				}
				//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
				
				//+++ Variante fuer den AsEntry-Test
				if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.PARSE_AS_ENTRY)) {					
					objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
					objEntry = objFormulaSolver.parseAsEntry(sExpression, bRemoveSurroundingSeparators);
					assertNotNull(objEntry);
					
					sValue = objEntry.getValue();
					assertEquals(sExpressionSolved, sValue);
					
					//Ausser wenn die Expression gar nicht behandelt wird. Dann ist das die ganze Zeile
					//sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ, false);
					//sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSolved, objFormulaSolver.getName(), false);								
					sValue = objFormulaSolver.getValue();
					assertNotNull(objFormulaSolver.getValue());
					assertEquals(sTagSolved, sValue);
				
					//Nutze eine Sammlung von assert Methoden, die ein objEntry als input hat.
					//und in der die verschiedenen stati für den unexpressed, unsubstituted, substituted, unsolved, etc Fall stehen.
					btemp = TestUtilAsTestZZZ.assertFileIniEntry(EnumSetMappedTestCaseSolverTypeZZZ.PARSE, objEnumFunction, objEntry, sExpression, sExpressionSubstituted, sExpressionSolved);
					assertTrue(btemp);
					
				}
				
				//+++ ... solve verhält sich NICHT wie parse(), bei solve wird aufgeloest...
				if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.SOLVE_AS_ENTRY)) {
					objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
					objEntry = objFormulaSolver.solveAsEntry(sExpression, objSectionEntryReference, bRemoveSurroundingSeparators);
					assertNotNull(objEntry);
					
					sValue = objEntry.getValue();
					assertEquals(sExpressionSolved, sValue);
					
					//Ausser wenn die Expression gar nicht behandelt wird. Dann ist das die ganze Zeile
					//sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ, false);
					//sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSolved, objFormulaSolver.getName(), false);								
					sValue = objFormulaSolver.getValue();
					assertNotNull(objFormulaSolver.getValue());
					assertEquals(sTagSolved, sValue);
								
					//Nutze eine Sammlung von assert Methoden, die ein objEntry als input hat.
					//und in der die verschiedenen stati für den unexpressed, unsubstituted, substituted, unsolved, etc Fall stehen.
					btemp = TestUtilAsTestZZZ.assertFileIniEntry(EnumSetMappedTestCaseSolverTypeZZZ.SOLVE, objEnumFunction, objEntry, sExpression, sExpressionSubstituted, sExpressionSolved);
					assertTrue(btemp);
					
				}
				//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			}
				bReturn = true;
			}//end main:
			return bReturn;
		}

		
		
	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	
	private boolean testCompute_GetPropertyWithExpression_() {
		//!!! Merke: Anders als beim Kernel-Holen der Property, wird beim KernelIniFile kein Cache verwendet.
		//           Darum reicht es die Flagz zu andern und es muss kein Cache erst geleert werden.
		String sExpressionIn; String sExpressionSolvedIn;
		
		main:{
			try {
				boolean btemp; 
				
				String sValue; String sSection; String sProperty;
				String sExpression; String sExpressionSolved; String sTag; String sTagSolved;			
				ReferenceZZZ<IKernelConfigSectionEntryZZZ> objSectionEntryReference;
				IKernelConfigSectionEntryZZZ objEntry; IKernelConfigSectionEntryZZZ objEntryUsed;
				
				String sTagStartZ = "<Z>";
				String sTagEndZ = "</Z>";	
				IEnumSetMappedTestFlagsetZZZ objEnumFunction = EnumSetMappedTestCaseFlagsetTypeZZZ.UNEXPRESSED;
				
				sSection = "Section for testCompute";
				sProperty = "Formula1";				
				sExpressionIn = "Der dynamische Wert1 ist '<Z>[Section A]Testentry1</Z>'. FGL rulez."; 
				sExpressionSolvedIn = "Der dynamische Wert1 ist 'Testvalue1 to be found'. FGL rulez.";

				
				//+++ KEINE Expression verwenden dann ist die Substitution egal
				sExpression = sExpressionIn;
				sExpressionSolved = sExpression;
								
				btemp = objFileIniTest.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION, false);
				assertTrue("Flag nicht vorhanden '" + IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
				
				btemp = objFileIniTest.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH, true); //muss egal sein, da EXPRESIONSOLVER = FALSE
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
								
				btemp = objFileIniTest.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER,true); //muss egal sein, da EXPRESIONSOLVER = FALSE
				assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
				 			
				btemp = objFileIniTest.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA, false); //eh keine Formel drin, also sollte das egal sein.
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA + "'", btemp);
							
				//Merke: Ohne Expression Behandlund duefen trotz parsen die Z-Tags nicht verschwinden.
				sExpressionSolved = sExpressionSolved; //"Der dynamische Wert ist '<Z>[Section A]Testentry1</Z>'. FGL rulez.";				
				sValue = objFileIniTest.getPropertyValue(sSection, sProperty).getValue();				
				assertEquals(sExpressionSolved, sValue);
				
				//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
				//+++ Expression verwenden UND KEINE Substitution verwenden, trotzdem wird der Z-Tag entfernt.
				sExpression = sExpressionIn;
				sExpressionSolved = sExpression;
				sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ, false);
				//sExpressionSolved = "Der dynamische Wert1 ist '[Section A]Testentry1'. FGL rulez.";
				
				btemp = objFileIniTest.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION,true);
				assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
			
				btemp = objFileIniTest.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH, false); //muss egal sein, da EXPRESIONSOLVER = FALSE
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
			
				
				btemp = objFileIniTest.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER,false);
				assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
				 				 		
				btemp = objFileIniTest.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA, false); 
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA + "'", btemp);
				
		
				sValue = objFileIniTest.getPropertyValue(sSection, sProperty).getValue();
				assertEquals(sExpressionSolved, sValue);
				 
				//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
				//+++ Expression verwenden UND Substitution verwenden, es wird auch der Z-Tag entfernt.
				//    Dabei ist es egal, ob der Solver aus oder an ist.
				sExpression = sExpressionIn;
				sExpressionSolved = sExpressionSolvedIn;
										
				btemp = objFileIniTest.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION,true);
				assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
							
				btemp = objFileIniTest.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH, true); //das ist dann egal, weil USE..._Solver=false
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
								
				btemp = objFileIniTest.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER,false);
				assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
				 			
				btemp = objFileIniTest.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA, true); //eh keine Formel drin, also sollte das egal sein.
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA + "'", btemp);
				
				//sExpression dito
				sValue = objFileIniTest.getPropertyValue(sSection, sProperty).getValue();
				assertEquals(sExpressionSolved, sValue);
				
				//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
				//+++ Expression verwenden UND Substitution verwenden, es wird auch der Z-Tag entfernt.
				//    Dabei ist es egal, ob der Solver aus oder an ist.
				sExpression = sExpressionIn;
				sExpressionSolved = sExpressionSolvedIn;				
				
				btemp = objFileIniTest.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION,true); 
				assertTrue("Flag nicht vorhanden '" + IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);

				//Nun werden die Pfade aufgeloest
				btemp = objFileIniTest.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH,true);
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
				
				//Erst wenn der Parser den Solver nutzen soll, kommt auch das vom solve() raus.
				btemp = objFileIniTest.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER,true);
				assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);				
				
				btemp = objFileIniTest.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA, true); //eh keine Formel drin, also sollte das egal sein.
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA + "'", btemp);
										
				sValue = objFileIniTest.getPropertyValue(sSection, sProperty).getValue();
				assertEquals(sExpressionSolved, sValue);
				
			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			}
		}//end main:			
		return true;
	}

	private boolean testCompute_GetPropertyWithExpressionCombinedWithSolver_() {
		boolean bReturn=false;
		String sExpressionIn; String sExpressionSolvedIn;
			
		main:{
			try {
				boolean btemp; 
				
				String sValue; String sSection; String sProperty;
				String sExpression; String sExpressionSolved; String sTag; String sTagSolved;			
				ReferenceZZZ<IKernelConfigSectionEntryZZZ> objSectionEntryReference;
				IKernelConfigSectionEntryZZZ objEntry; IKernelConfigSectionEntryZZZ objEntryUsed;
				
				String sTagStartZ = "<Z>";
				String sTagEndZ = "</Z>";	
				IEnumSetMappedTestFlagsetZZZ objEnumFunction = EnumSetMappedTestCaseFlagsetTypeZZZ.UNEXPRESSED;
				
				//Variablen, Werte fuer spaeteren Testvergleich aufheben
				String sExpressionForSolverByProperty; String sExpressionForSolverDirect;
				
				
				
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
				sSection = "Section for testCompute";
				sProperty = "Formula2";				
				sExpressionIn = "Der dynamische Wert2 ist '<Z>[Section B]Testentry2</Z>'. FGL rulez."; 
				sExpressionSolvedIn = "Der dynamische Wert2 ist 'Testvalue2 local to be found'. FGL rulez.";
			

				//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
				//+++ OHNE Expression
				//+++ Erst einmal den Wert nur auslesen, nicht parsen oder solven
				sExpression = sExpressionIn;
				sExpressionSolved = sExpression;
				//Merke: Die Z-Tags sind verschwunden.????????????????????
						
				btemp = objFileIniTest.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION,false); 
				assertTrue("Flag nicht vorhanden '" + IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
			
				btemp = objFileIniTest.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH,true); //sollte dann egal sein
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
						
				//Erst wenn der Parser den Solver nutzen soll, kommt auch das vom solve() raus.
				btemp = objFileIniTest.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER,true); //sollte egal sein
				assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
											
				btemp = objFileIniTest.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA, true); //eh keine Formel drin, also sollte das egal sein.
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA + "'", btemp);
				
				sValue = objFileIniTest.getPropertyValue(sSection, sProperty).getValue();
				assertEquals(sExpressionSolved, sValue);
				sExpressionForSolverDirect = sValue; //fuer spaeter aufheben
				
				//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
				//+++ MIT Expression, OHNE Pfadsubstitution
				//Erst einmal soll der Wert nicht sofort ausgerechnet werden.	
				//Merke: Die Z-Tags sind verschwunden.
				sExpression = sExpressionIn;
				sExpressionSolved = sExpression;
				sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ, false);
				//sExpressionSolved = "Der dynamische Wert2 ist '[Section B]Testentry2'. FGL rulez.";
				
				btemp = objFileIniTest.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION,true); 
				assertTrue("Flag nicht vorhanden '" + IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
				
				btemp = objFileIniTest.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH,false); //Pfade werden nicht aufgeloest
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
							
				btemp = objFileIniTest.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER,true);
				assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
					
				btemp = objFileIniTest.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA, true); //eh keine Formel drin, also sollte das egal sein.
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA + "'", btemp);
		
				sValue = objFileIniTest.getPropertyValue(sSection, sProperty).getValue();
				assertEquals(sExpressionSolved, sValue);
				sExpressionForSolverByProperty = sValue; //fuer spaeter aufheben			
				
				//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
				//+++++++++++++++++++ SOLVER TEST +++++++++++++++++++++++++++++++++++++++++++++
				//+++++++++++++++++++ Arbeite mit dem vorherigen Ergebnis im Solver weiter
				//+++++++++++++++++++ Problem: Die Z-Tags sind raus.
				sExpression = sExpressionForSolverByProperty;
				sExpressionSolved = sExpression;
				//sExpressionSolved = "Der dynamische Wert2 ist '[Section B]Testentry2'. FGL rulez."; 				//Weil oben die Z-Tags raus sind, bleiben Sie auch hier weg
				//sExpressionSolved = sExpression;
				//sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ, false);
				
				//Wenn man die Expression_PATH-Behandlung ausstellt, dann werden auch beim "Solven" keine Pfade ersetzt
				btemp = objFormulaSolver.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION,true); 
				assertTrue("Flag nicht vorhanden '" + IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
				
				btemp = objFormulaSolver.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH, false); //also nur die Expression aufloesen, aber den Pfad nicht aufloesen.
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
								
				btemp = objFormulaSolver.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER,true); //false: Damit der Pfad NICHT sofort ausgerechnet wird
				assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
								
				btemp = objFormulaSolver.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA, true);
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA + "'", btemp);
							
				sValue = objFormulaSolver.solve(sExpressionForSolverByProperty);
				assertEquals(sExpressionSolved, sValue);
				
				//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
				//Wenn man die Expression_PATH-Behandlung anstellt, dann findet normalerweise die Ersetzung statt.
				//Weil oben die Z-Tags raus sind, findet keine Ersetzung statt
				btemp = objFormulaSolver.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH, true);
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
				
				//dito sExpression = "Der dynamische Wert2 ist '[Section B]Testentry2'. FGL rulez.";
				sValue = objFormulaSolver.solve(sExpression);
				assertEquals(sExpressionSolved, sValue);
				
				//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++				
				//Zum Vergleich, String mit Z-Tags
				sExpression = sExpressionForSolverDirect;
				sExpressionSolved = sExpressionSolvedIn;
				//sExpressionSolved = "Der dynamische Wert2 ist '<Z>Testvalue2 local to be found</Z>'. FGL rulez.";
				sValue = objFormulaSolver.solve(sExpression);				
				assertEquals(sExpressionSolved, sValue);
				
			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			}
			bReturn = false;
		}//end main:			
		return bReturn;
	}
	
	private boolean testCompute_GetPropertyWithFormulaMath_() {
		boolean bReturn = false;
		String sExpressionIn; String sExpressionSolvedIn;
		
		main:{
			try {
				boolean btemp; 
				
				String sValue; String sSection; String sProperty;
				String sExpression; String sExpressionSolved; String sTag; String sTagSolved;			
				ReferenceZZZ<IKernelConfigSectionEntryZZZ> objSectionEntryReference;
				IKernelConfigSectionEntryZZZ objEntry; IKernelConfigSectionEntryZZZ objEntryUsed;
				
				String sTagStartZ = "<Z>";
				String sTagEndZ = "</Z>";	
				IEnumSetMappedTestFlagsetZZZ objEnumFunction = EnumSetMappedTestCaseFlagsetTypeZZZ.UNEXPRESSED;
					
				
				//####################################################################################
				//### Formel mit MULTIPLIKATIONS Operator
				//####################################################################################
				
				sExpressionIn="Der dynamische Wert ist '<Z><Z:math><Z:val>2</Z:val><Z:op>*</Z:op><Z:val>3</Z:val></Z:math></Z>'. FGL rulez.";
				sSection = "Section for testComputeMath";
				sProperty = "Formula1";
				
				//+++ KEINE Expression oder Formel verwenden dann ist die Aufloesung egal
				btemp = objFileIniTest.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION, false);
				assertTrue("Flag nicht vorhanden '" + IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
				
				btemp = objFileIniTest.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH, true); //muss egal sein, da EXPRESION = FALSE //muss egal sein, wenn EXPRESIONSOLVER = FALSE
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
							
				btemp = objFileIniTest.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER,true); //muss egal sein, da EXPRESION = FALSE
				assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
				 							
				btemp = objFileIniTest.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA, true); //muss egal sein, da EXPRESION = FALSE //muss egal sein, wenn EXPRESIONSOLVER = FALSE
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA + "'", btemp);
											
				btemp = objFileIniTest.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH, true); //muss egal sein, da EXPRESION = FALSE //muss egal sein, wenn EXPRESIONSOLVER = FALSE
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA + "'", btemp);
							
//				sExpressionSolved = sExpression;
//				sValue = objFileIniTest.getPropertyValue(sSection, sProperty).getValue();
//				assertEquals(sExpressionSolved, sValue);
				
				//+++ Expression oder Formel verwenden, aber keine Aufloesung (auch nicht des Pfades) verwenden, dann wird mit parse() lediglich der Z-Tag entfernt.
				btemp = objFileIniTest.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION,true);
				assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
							
				btemp = objFileIniTest.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER,false);
				assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
				 			
//				sExpressionSolved = "Der dynamische Wert ist '<Z:math><Z:val>2</Z:val><Z:op>*</Z:op><Z:val>3</Z:val></Z:math>'. FGL rulez.";
//				sValue = objFileIniTest.getPropertyValue(sSection, sProperty).getValue();
//				assertEquals(sExpressionSolved, sValue);
				
				
				//+++ Expression oder Formel verwenden UND Aufloesung verwenden, ABER noch keine Aufloesung des Pfades, dann wird mit parse() lediglich der Z-Tag entfernt und der unaufgeloeste Pfadausdruck steht dort.
				//    Die Formel soll noch nicht ausgerechnet werden.
				btemp = objFileIniTest.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH, false); //Jetzt nicht mehr egal, da der Solver ausgefuehrt wird.
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);

				btemp = objFileIniTest.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER,true);
				assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
				 			
				 		
				btemp = objFileIniTest.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA, false); //Jetzt nicht mehr egal, da der Solver ausgefuehrt wird.
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA + "'", btemp);
								
				//... dito zu vorherigem lauf.
//				sValue = objFileIniTest.getPropertyValue(sSection, sProperty).getValue();
//				assertEquals(sExpressionSolved, sValue);
				
				
				//+++ Expression oder Formel verwenden UND Aufloesung verwenden UND Aufloesung des Pfades, dann wird mit parse() lediglich der Z-Tag entfernt und der aufgeloeste Pfadausdruck steht dort.
				//    Die Formel soll noch nicht ausgerechnet werden.
				btemp = objFileIniTest.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH, true);
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
								
//				sExpressionSolved = "Der dynamische Wert ist '<Z:math><Z:val>2</Z:val><Z:op>*</Z:op><Z:val>3</Z:val></Z:math>'. FGL rulez.";
//				sValue = objFileIniTest.getPropertyValue(sSection, sProperty).getValue();
//				assertEquals(sExpressionSolved, sValue);
								
				
				//+++ ausrechnen
				//    aber immer noch nicht die Formel
				btemp = objFileIniTest.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA, true); //Jetzt nicht mehr egal, da der Solver ausgefuehrt wird.
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA + "'", btemp);
				
				btemp = objFileIniTest.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH, false);
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH + "'", btemp);
				
				//... dito zu vorherigem lauf.
//				sValue = objFileIniTest.getPropertyValue("Section for testComputeMath", "Formula1").getValue();
//				assertEquals(sExpressionSolved, sValue);
				
				
				//+++ Formel ausrechnen
				btemp = objFileIniTest.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH, true);
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH + "'", btemp);
				
//				sExpressionSolved = "Der dynamische Wert ist '6'. FGL rulez.";;
//				sValue = objFileIniTest.getPropertyValue(sSection, sProperty).getValue();											
//				assertEquals(sExpressionSolved, sValue);
//				assertTrue("Im Ergebnis wurde eine ausgerechnete '6' erwartet.", StringZZZ.contains(sValue, "6"));
				
				
				//####################################################################################
				//### Formel mit PLUS Operator - Tags in unterschiedlichsten Schreibweisen.
				//####################################################################################
				
				sExpressionIn="Der dynamische Wert ist '<Z><z:Math><Z:VAL>6</Z:val><Z:oP>+</Z:op><Z:val>7</Z:val></Z:math></Z>'. FGL rulez.";
				sSection = "Section for testComputeMath NOT EXACTMATCH";
				sProperty = "Formula1";
				
				//+++ KEINE Expression oder Formel verwenden dann ist die Aufloesung egal
				btemp = objFileIniTest.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION, false);
				assertTrue("Flag nicht vorhanden '" + IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
			
				btemp = objFileIniTest.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH, true); //muss egal sein, da EXPRESION = FALSE //muss egal sein, wenn EXPRESIONSOLVER = FALSE
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);			
				
				btemp = objFileIniTest.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER,true); //muss egal sein, da EXPRESION = FALSE
				assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
				 						
				btemp = objFileIniTest.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA, true); //muss egal sein, da EXPRESION = FALSE //muss egal sein, wenn EXPRESIONSOLVER = FALSE
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA + "'", btemp);
											
				btemp = objFileIniTest.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH, true); //muss egal sein, da EXPRESION = FALSE //muss egal sein, wenn EXPRESIONSOLVER = FALSE
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA + "'", btemp);
				
				sExpression = sExpressionIn;
				sExpressionSolved = sExpression;
//				sValue = objFileIniTest.getPropertyValue(sSection, sProperty).getValue();
//				assertEquals(sExpressionSolved, sValue);
				
				//+++ Expression oder Formel verwenden, aber keine Aufloesung (auch nicht des Pfades) verwenden, dann wird mit parse() lediglich der Z-Tag entfernt.
				btemp = objFileIniTest.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION,true);
				assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
							
				btemp = objFileIniTest.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER,false);
				assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
				
				sExpressionSolved = "Der dynamische Wert ist '<z:Math><Z:VAL>6</Z:val><Z:oP>+</Z:op><Z:val>7</Z:val></Z:math>'. FGL rulez.";
				sValue = objFileIniTest.getPropertyValue(sSection, sProperty).getValue();
				assertEquals(sExpressionSolved, sValue);
				
				
				//+++ Expression oder Formel verwenden UND Aufloesung verwenden, ABER noch keine Aufloesung des Pfades, dann wird mit parse() lediglich der Z-Tag entfernt und der unaufgeloeste Pfadausdruck steht dort.
				//    Die Formel soll noch nicht ausgerechnet werden.
				btemp = objFileIniTest.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH, false); //Jetzt nicht mehr egal, da der Solver ausgefuehrt wird.
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
		
				btemp = objFileIniTest.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER,true);
				assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
				 					 		
				btemp = objFileIniTest.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA, false); //Jetzt nicht mehr egal, da der Solver ausgefuehrt wird.
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA + "'", btemp);
								
				//... dito zu vorherigem lauf.
				sValue = objFileIniTest.getPropertyValue(sSection, sProperty).getValue();
				assertEquals(sExpressionSolved, sValue);
				
				
				//+++ Expression oder Formel verwenden UND Aufloesung verwenden UND Aufloesung des Pfades, dann wird mit parse() lediglich der Z-Tag entfernt und der aufgeloeste Pfadausdruck steht dort.
				//    Die Formel soll noch nicht ausgerechnet werden.
				btemp = objFileIniTest.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH, true);
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
								
				//... dito zu vorherigem lauf.				
				sValue = objFileIniTest.getPropertyValue(sSection, sProperty).getValue();
				assertEquals(sExpressionSolved, sValue);
								
				
				//+++ ausrechnen
				//    aber immer noch nicht die Formel
				btemp = objFileIniTest.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA, true); //Jetzt nicht mehr egal, da der Solver ausgefuehrt wird.
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA + "'", btemp);
				
				btemp = objFileIniTest.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH, false);
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH + "'", btemp);
				
				//... dito zu vorherigem lauf.
				sValue = objFileIniTest.getPropertyValue(sSection, sProperty).getValue();
				assertEquals(sExpressionSolved, sValue);
				
				
				//+++ Formel ausrechnen
				btemp = objFileIniTest.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH, true);
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH + "'", btemp);
				
				sExpressionSolved = "Der dynamische Wert ist '13'. FGL rulez.";
				sValue = objFileIniTest.getPropertyValue(sSection, sProperty).getValue();
				assertTrue("Im Ergebnis wurde eine ausgerechnete '13' erwartet.", StringZZZ.contains(sExpressionSolved, "13"));							
				assertEquals(sExpressionSolved, sValue);
				
				
				//####################################################################################
				//### Formel ohne Operator - ist einfache Stringzusammenfassung
				//####################################################################################
				
				sExpressionIn="Der dynamische Wert ist '<Z><Z:math><Z:val>2</Z:val><Z:val>3</Z:val></Z:math></Z>'. FGL rulez.";
				sSection = "Section for testComputeMathValue";
				sProperty = "Formula1";
				
				//+++ KEINE Expression oder Formel verwenden dann ist die Aufloesung egal
				btemp = objFileIniTest.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION, false);
				assertTrue("Flag nicht vorhanden '" + IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
				
				btemp = objFileIniTest.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH, true); //muss egal sein, da EXPRESION = FALSE //muss egal sein, wenn EXPRESIONSOLVER = FALSE
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
			
				btemp = objFileIniTest.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER,true); //muss egal sein, da EXPRESION = FALSE
				assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
				 				
				btemp = objFileIniTest.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA, true); //muss egal sein, da EXPRESION = FALSE //muss egal sein, wenn EXPRESIONSOLVER = FALSE
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA + "'", btemp);
											
				btemp = objFileIniTest.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH, true); //muss egal sein, da EXPRESION = FALSE //muss egal sein, wenn EXPRESIONSOLVER = FALSE
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA + "'", btemp);
				
				sExpression = sExpressionIn;
				sExpressionSolved = sExpression;
				sValue = objFileIniTest.getPropertyValue(sSection, sProperty).getValue();
				assertEquals(sExpressionSolved, sValue);
				
				//+++ Expression oder Formel verwenden, aber keine Aufloesung (auch nicht des Pfades) verwenden, dann wird mit parse() lediglich der Z-Tag entfernt.
				btemp = objFileIniTest.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION,true);
				assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
							
				btemp = objFileIniTest.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER,false);
				assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
												
				sExpressionSolved = "Der dynamische Wert ist '<Z:math><Z:val>2</Z:val><Z:val>3</Z:val></Z:math>'. FGL rulez.";
				sValue = objFileIniTest.getPropertyValue(sSection, sProperty).getValue();
				assertEquals(sExpressionSolved, sValue);
				
				
				//+++ Expression oder Formel verwenden UND Aufloesung verwenden, ABER noch keine Aufloesung des Pfades, dann wird mit parse() lediglich der Z-Tag entfernt und der unaufgeloeste Pfadausdruck steht dort.
				//    Die Formel soll noch nicht ausgerechnet werden.
				btemp = objFileIniTest.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH, false); //Jetzt nicht mehr egal, da der Solver ausgefuehrt wird.
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
			
				btemp = objFileIniTest.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER,true);
				assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
				 							 	
				btemp = objFileIniTest.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA, false); //Jetzt nicht mehr egal, da der Solver ausgefuehrt wird.
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA + "'", btemp);
								
				//... dito zu vorherigem lauf.
				sValue = objFileIniTest.getPropertyValue(sSection, sProperty).getValue();
				assertEquals(sExpressionSolved, sValue);
				
				
				//+++ Expression oder Formel verwenden UND Aufloesung verwenden UND Aufloesung des Pfades, dann wird mit parse() lediglich der Z-Tag entfernt und der aufgeloeste Pfadausdruck steht dort.
				//    Die Formel soll noch nicht ausgerechnet werden.
				btemp = objFileIniTest.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH, true);
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
								
				//... dito zu vorherigem lauf.				
				sValue = objFileIniTest.getPropertyValue(sSection, sProperty).getValue();
				assertEquals(sExpressionSolved, sValue);
								
				
				//+++ ausrechnen
				//    aber immer noch nicht die Formel
				btemp = objFileIniTest.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA, true); //Jetzt nicht mehr egal, da der Solver ausgefuehrt wird.
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA + "'", btemp);
				
				btemp = objFileIniTest.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH, false);
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH + "'", btemp);
				
				//... dito zu vorherigem lauf.
				sValue = objFileIniTest.getPropertyValue(sSection, sProperty).getValue();
				assertEquals(sExpressionSolved, sValue);
				
				
				//+++ Formel ausrechnen
				btemp = objFileIniTest.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH, true);
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH + "'", btemp);
				
				sExpressionSolved = "Der dynamische Wert ist '23'. FGL rulez.";;
				sValue = objFileIniTest.getPropertyValue(sSection, sProperty).getValue();
				assertTrue("Im Ergebnis '" + sExpressionSolved + "' wurde eine zusammengesetzte '23' erwartet.", StringZZZ.contains(sExpressionSolved, "23"));							
				assertEquals(sExpressionSolved, sValue);			
				
			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			}
			bReturn = true;
		}//end main:			
		return bReturn;
	}
	
	private boolean testCompute_GetPropertyWithFormulaMathByPath_() {
		boolean bReturn = false;
		String sExpressionIn; String sExpressionSolvedIn;
		main:{
			try {
				boolean btemp; 
				
				String sValue; String sSection; String sProperty;
				String sExpression; String sExpressionSolved; String sTag; String sTagSolved;			
				ReferenceZZZ<IKernelConfigSectionEntryZZZ> objSectionEntryReference;
				IKernelConfigSectionEntryZZZ objEntry; IKernelConfigSectionEntryZZZ objEntryUsed;
				
				String sTagStartZ = "<Z>";
				String sTagEndZ = "</Z>";	
				IEnumSetMappedTestFlagsetZZZ objEnumFunction = EnumSetMappedTestCaseFlagsetTypeZZZ.UNEXPRESSED;
				
				
				//####################################################################################
				//### Formel zur Berechnung mit FLOAT Werten
				//####################################################################################
							
				sExpressionIn="Der dynamische Wert ist '<Z><z:Math><Z:VAL>[Section for testComputeMathArguments FLOAT]WertA_float</Z:val><Z:oP>*</Z:op><Z:val>[Section for testComputeMathArguments FLOAT]WertB_float</Z:val></Z:math></Z>'. FGL rulez.";
				sSection = "Section for testComputeMath FLOAT";
				sProperty = "Formula1";
				
				//+++ KEINE Expression oder Formel verwenden dann ist die Aufloesung egal
				btemp = objFileIniTest.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION, false);
				assertTrue("Flag nicht vorhanden '" + IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
			
				btemp = objFileIniTest.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH, true); //muss egal sein, da EXPRESION = FALSE //muss egal sein, wenn EXPRESIONSOLVER = FALSE
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
			
				btemp = objFileIniTest.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER,true); //muss egal sein, da EXPRESION = FALSE
				assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
				 				
				btemp = objFileIniTest.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA, true); //muss egal sein, da EXPRESION = FALSE //muss egal sein, wenn EXPRESIONSOLVER = FALSE
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA + "'", btemp);
											
				btemp = objFileIniTest.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH, true); //muss egal sein, da EXPRESION = FALSE //muss egal sein, wenn EXPRESIONSOLVER = FALSE
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA + "'", btemp);
							
				sExpression = sExpressionIn;
				sValue = objFileIniTest.getPropertyValue(sSection, sProperty).getValue();
				assertEquals(sExpression, sValue);
				
				//+++ Expression oder Formel verwenden, aber keine Aufloesung (auch nicht des Pfades) verwenden, dann wird mit parse() lediglich der Z-Tag entfernt.
				btemp = objFileIniTest.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION,true);
				assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
							
				btemp = objFileIniTest.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH, false); //Jetzt nicht mehr egal, da der Solver ausgefuehrt wird.
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
				
				btemp = objFileIniTest.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER,false);
				assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
				 			
				sExpression = "Der dynamische Wert ist '<z:Math><Z:VAL>[Section for testComputeMathArguments FLOAT]WertA_float</Z:val><Z:oP>*</Z:op><Z:val>[Section for testComputeMathArguments FLOAT]WertB_float</Z:val></Z:math>'. FGL rulez.";
				sValue = objFileIniTest.getPropertyValue(sSection, sProperty).getValue();
				assertEquals(sExpression, sValue);
				
				
				//+++ Expression oder Formel verwenden UND Aufloesung verwenden, ABER noch keine Aufloesung des Pfades, dann wird mit parse() lediglich der Z-Tag entfernt und der unaufgeloeste Pfadausdruck steht dort.
				//    Die Formel soll noch nicht ausgerechnet werden.
				btemp = objFileIniTest.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH, false); //Jetzt nicht mehr egal, da der Solver ausgefuehrt wird.
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
				
				btemp = objFileIniTest.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER,true);
				assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
				 			 		
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

				sExpressionIn = "<Z>[Section for testComputeMath]Formula1</Z>";//String sExpressionSource="Der dynamische Wert ist '<Z><Z:math><Z:val>2</Z:val><Z:op>*</Z:op><Z:val>3</Z:val></Z:math></Z>'. FGL rulez.";
				sSection = "Section for testComputePathWithMath";
				sProperty = "Formula1";
				
				//+++ KEINE Expression oder Formel verwenden dann ist die Aufloesung egal
				btemp = objFileIniTest.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION, false);
				assertTrue("Flag nicht vorhanden '" + IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
				
				btemp = objFileIniTest.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH, true); //muss egal sein, da EXPRESION = FALSE //muss egal sein, wenn EXPRESIONSOLVER = FALSE
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
			
				btemp = objFileIniTest.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER,true); //muss egal sein, da EXPRESION = FALSE
				assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
				 			
				btemp = objFileIniTest.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA, true); //muss egal sein, da EXPRESION = FALSE //muss egal sein, wenn EXPRESIONSOLVER = FALSE
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA + "'", btemp);
											
				btemp = objFileIniTest.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH, true); //muss egal sein, da EXPRESION = FALSE //muss egal sein, wenn EXPRESIONSOLVER = FALSE
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA + "'", btemp);
							
				sExpression = sExpressionIn;
				sValue = objFileIniTest.getPropertyValue(sSection, sProperty).getValue();
				assertEquals(sExpression, sValue);
				
				//+++ Expression oder Formel verwenden, aber keine Aufloesung (auch nicht des Pfades) verwenden, dann wird mit parse() lediglich der Z-Tag entfernt.
				btemp = objFileIniTest.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION,true);
				assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
							
				btemp = objFileIniTest.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH, false); //Jetzt nicht mehr egal, da der Solver ausgefuehrt wird.
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
			
				btemp = objFileIniTest.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER,false);
				assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
												
				sExpression = "[Section for testComputeMath]Formula1";
				sValue = objFileIniTest.getPropertyValue(sSection, sProperty).getValue();
				assertEquals(sExpression, sValue);
				
				
				//+++ Expression oder Formel verwenden UND Aufloesung verwenden, ABER noch keine Aufloesung des Pfades, dann wird mit parse() lediglich der Z-Tag entfernt und der unaufgeloeste Pfadausdruck steht dort.
				//    Die Formel soll noch nicht ausgerechnet werden.
				btemp = objFileIniTest.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH, false); //Jetzt nicht mehr egal, da der Solver ausgefuehrt wird.
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
			
				btemp = objFileIniTest.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER,true);
				assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
				 				 		
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
							
	
	public boolean testCompute_GetPropertyWithVariables() {
		boolean bReturn = false;
		String sExpressionIn; String sExpressionSolvedIn;
		main:{
			try {
				boolean btemp; 
				
				String sValue; String sSection; String sProperty;
				String sExpression; String sExpressionSolved; String sTag; String sTagSolved;			
				ReferenceZZZ<IKernelConfigSectionEntryZZZ> objSectionEntryReference;
				IKernelConfigSectionEntryZZZ objEntry; IKernelConfigSectionEntryZZZ objEntryUsed;
				
				String sTagStartZ = "<Z>";
				String sTagEndZ = "</Z>";	
				IEnumSetMappedTestFlagsetZZZ objEnumFunction = EnumSetMappedTestCaseFlagsetTypeZZZ.UNEXPRESSED;
				
		
			//####################################################################################
			//### Expression mit Variablen
			//####################################################################################
			
			sExpressionIn="<Z>Der dynamische Wert ist '<z:Var>myTestVariableString</z:Var>'. FGL rulez.</Z>";
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
						
			//Variablenaufloesung unterbinden per Flag
			btemp = objFileIniTest.setFlag(IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE,false);
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE + "'", btemp);
			
			sExpression = sExpressionIn;
			sExpressionSolved = sExpression;
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ, false);
			sValue = objFileIniTest.getPropertyValue(sSection, sProperty).getValue(); //Wenn noch keine Formelvariable gesetzt ist...			
			assertEquals(sExpressionSolved, sValue);
			
			//Variablenaufloesung anstellen
			btemp = objFileIniTest.setFlag(IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE,true);
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE + "'", btemp);
			
			sExpressionSolved = "Der dynamische Wert ist 'mySolvedTestVariableString'. FGL rulez.";
			sValue = objFileIniTest.getPropertyValue(sSection, sProperty).getValue(); //Wenn noch keine Formelvariable gesetzt ist...			
			assertEquals(sExpressionSolved, sValue);
			
			 
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
			sExpressionSolved="Der dynamische Wert ist 'mySolvedTestVariableString'. FGL rulez."; //Also der Wert mit einer uebergebenen Variablen ausgerechnet.
			sValue = objFileIniTest.getPropertyValue(sSection, sProperty).getValue(); //Wenn noch keine Formelvariable gesetzt ist...
			assertTrue("Im Ergebnis wurde eine ausgerechnete 'mySolvedTestVariableString' erwartet.", StringZZZ.contains(sExpressionSolved, "mySolvedTestVariableString"));
			assertEquals(sExpressionSolved, sValue);
			 						
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}
			bReturn = true;
		}//end main
		return bReturn;
	}
	
	
	
		
	
	public boolean testCompute_VariablesCascaded_DifferentPath() {
		boolean bReturn = false;
		String sExpressionIn; String sExpressionSolvedIn;
		main:{
			try {
				boolean btemp; 
				
				String sValue; String sSection; String sProperty;
				String sExpression; String sExpressionSolved; String sTag; String sTagSolved;			
				ReferenceZZZ<IKernelConfigSectionEntryZZZ> objSectionEntryReference;
				IKernelConfigSectionEntryZZZ objEntry; IKernelConfigSectionEntryZZZ objEntryUsed;
				
				String sTagStartZ = "<Z>";
				String sTagEndZ = "</Z>";	
				IEnumSetMappedTestFlagsetZZZ objEnumFunction = EnumSetMappedTestCaseFlagsetTypeZZZ.UNEXPRESSED;
				
			//###############################################	
			//### Einbinden der Variablen und Ini-Pfade in Math-Ausdruecke
			//###############################################

			sExpressionIn = "Der dynamische Wert ist '<Z><z:Math><Z:VAL>[Section for testComputeMathArguments FLOAT]WertA_float</Z:val><Z:oP>*</Z:op><Z:val>[Section for testComputeMathVariable FLOAT]WertB_float</Z:val></Z:math></Z>'. FGL rulez.";
			
			
			
			//############################
			//### Per INI-File-Objekt
			sSection = "Section for testPassVariable";
			sProperty = "Formula3";
			
			
			btemp = objFileIniTest.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION, true); 
			assertTrue("Flag nicht vorhanden '" + IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
			
			//Nun erst werden Pfade ersetzt
			btemp = objFileIniTest.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH, true); //muss egal sein, da EXPRESION = FALSE //muss egal sein, wenn EXPRESIONSOLVER = FALSE
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
						
			//Nun erst werden Variablen ersetzt			
			btemp = objFileIniTest.setFlag(IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE,true);
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE + "'", btemp);
						
			btemp = objFileIniTest.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER,true); 
			assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
						
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
			sExpressionSolved="Der dynamische Wert ist '12.0'. FGL rulez."; //Also der Wert ohne die Math auszurechnen.
			assertTrue("Im Ergebnis wurde eine ausgerechnete '12.0' erwartet.", StringZZZ.contains(sValue, "12.0"));
			assertEquals(sExpressionSolved, sValue);

		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}
			bReturn = true;
		}//end main:
		return bReturn;
	}
	
	public boolean testCompute_VariablesCascaded_PathAndVariable() {
		boolean bReturn = false;
		String sExpressionIn; String sExpressionSolvedIn;
		main:{
			try {
				boolean btemp; 
				
				String sValue; String sSection; String sProperty;
				String sExpression; String sExpressionSolved; String sTag; String sTagSolved;			
				ReferenceZZZ<IKernelConfigSectionEntryZZZ> objSectionEntryReference;
				IKernelConfigSectionEntryZZZ objEntry; IKernelConfigSectionEntryZZZ objEntryUsed;
				
				String sTagStartZ = "<Z>";
				String sTagEndZ = "</Z>";	
				IEnumSetMappedTestFlagsetZZZ objEnumFunction = EnumSetMappedTestCaseFlagsetTypeZZZ.UNEXPRESSED;
				
			
			
			//###############################################	
			//### Einbinden der Variablen UND Ini-Pfade in Math-Ausdruecke
			//###############################################
			sExpressionIn = "Der dynamische Wert ist '<Z><z:Math><Z:VAL>[Section for testComputeMathArguments FLOAT]WertA_float</Z:val><Z:oP>*</Z:op><Z:val><Z:Var>myTestVariableFloat</z:Var></Z:val></Z:math></Z>'. FGL rulez.";
			
			
			
			//############################
			//### Per INI-File-Objekt
			sSection = "Section for testPassVariable";
			sProperty = "Formula2";
			
			btemp = objFileIniTest.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION, true); 
			assertTrue("Flag nicht vorhanden '" + IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
			
			//Nun erst werden Ini-Pfade substituiert 
			btemp = objFileIniTest.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH, true); //muss egal sein, da EXPRESION = FALSE //muss egal sein, wenn EXPRESIONSOLVER = FALSE
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
			
			//Nun erst werden Variablen ersetzt			
			btemp = objFileIniTest.setFlag(IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE,true);
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE + "'", btemp);
						
			btemp = objFileIniTest.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER,true); 
			assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
									
			
			btemp = objFileIniTest.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA,true); //Damit der Wert sofort ausgerechnet wird
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA + "'", btemp);
			
			
			btemp = objFileIniTest.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH, true); //Damit der Wert NICHT sofort ausgerechnet wird
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH + "'", btemp);
					
			HashMapCaseInsensitiveZZZ<String,String> hmVariable = new HashMapCaseInsensitiveZZZ<String,String>();
			hmVariable.put("myTestVariableString","Test erfolgreich");
			hmVariable.put("myTestVariableFloat","2.5");
			objFileIniTest.setHashMapVariable(hmVariable);
			sValue = objFileIniTest.getPropertyValue(sSection, sProperty).getValue(); //Wenn noch keine Formelvariable gesetzt ist...
			sExpressionSolved="Der dynamische Wert ist '10.0'. FGL rulez."; //Also der Wert ohne die Math auszurechnen.			
			assertEquals(sExpressionSolved, sValue);		
			assertTrue("Im Ergebnis wurde eine ausgerechnete '10.0' erwartet.", StringZZZ.contains(sValue, "10.0"));
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}
		bReturn = true;
		}
		return bReturn;
	}

	public boolean testCompute_VariablesCascaded_VariableAndPath() {
		boolean bReturn = false;
		String sExpressionIn; String sExpressionSolvedIn;
		main:{
			try {
				boolean btemp; 
				
				String sValue; String sSection; String sProperty;
				String sExpression; String sExpressionSolved; String sTag; String sTagSolved;			
				ReferenceZZZ<IKernelConfigSectionEntryZZZ> objSectionEntryReference;
				IKernelConfigSectionEntryZZZ objEntry; IKernelConfigSectionEntryZZZ objEntryUsed;
				
				String sTagStartZ = "<Z>";
				String sTagEndZ = "</Z>";	
				IEnumSetMappedTestFlagsetZZZ objEnumFunction = EnumSetMappedTestCaseFlagsetTypeZZZ.UNEXPRESSED;
				
		
			//###############################################	
			//### Einbinden der Variablen UND Ini-Pfade in Math-Ausdruecke
			//###############################################
			sExpressionIn = "Der dynamische Wert ist '<Z><z:Math><Z:val><Z:Var>myTestVariableFloat</z:Var></Z:val><Z:oP>*</Z:op><Z:VAL>[Section for testComputeMathArguments FLOAT]WertA_float</Z:val></Z:math></Z>'. FGL rulez.";
			
			
			
			//############################
			//### Per INI-File-Objekt
			sSection = "Section for testPassVariable";
			sProperty = "Formula4";
			
			btemp = objFileIniTest.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION, true); 
			assertTrue("Flag nicht vorhanden '" + IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
			
			//Nun erst werden Ini-Pfade substituiert 
			btemp = objFileIniTest.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH, true); //muss egal sein, da EXPRESION = FALSE //muss egal sein, wenn EXPRESIONSOLVER = FALSE
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
			
			//Nun erst werden Variablen ersetzt			
			btemp = objFileIniTest.setFlag(IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE,true);
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE + "'", btemp);
						
			btemp = objFileIniTest.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER,true); 
			assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
												
			btemp = objFileIniTest.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA,true); //Damit der Wert sofort ausgerechnet wird
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA + "'", btemp);
						
			btemp = objFileIniTest.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH, true); //Damit der Wert NICHT sofort ausgerechnet wird
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH + "'", btemp);
					
			HashMapCaseInsensitiveZZZ<String,String> hmVariable = new HashMapCaseInsensitiveZZZ<String,String>();
			hmVariable.put("myTestVariableString","Test erfolgreich");
			hmVariable.put("myTestVariableFloat","2.5");
			objFileIniTest.setHashMapVariable(hmVariable);
			sValue = objFileIniTest.getPropertyValue(sSection, sProperty).getValue(); //Wenn noch keine Formelvariable gesetzt ist...
			sExpressionSolved="Der dynamische Wert ist '10.0'. FGL rulez."; //Also der Wert ohne die Math auszurechnen.			
			assertEquals(sExpressionSolved, sValue);		
			assertTrue("Im Ergebnis wurde eine ausgerechnete '10.0' erwartet.", StringZZZ.contains(sValue, "10.0"));
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}
		bReturn = true;
		}//end main:
		return bReturn;
	}

	
	
	public boolean testCompute_FORMULA_MATHxCascaded() {
		boolean bReturn = false;
		String sExpressionIn; String sExpressionSolvedIn;
		main:{
			try {
				boolean btemp; 
				
				String sValue; String sSection; String sProperty;
				String sExpression; String sExpressionSolved; String sTag; String sTagSolved;			
				ReferenceZZZ<IKernelConfigSectionEntryZZZ> objSectionEntryReference;
				IKernelConfigSectionEntryZZZ objEntry; IKernelConfigSectionEntryZZZ objEntryUsed;
				
				String sTagStartZ = "<Z>";
				String sTagEndZ = "</Z>";	
				IEnumSetMappedTestFlagsetZZZ objEnumFunction = EnumSetMappedTestCaseFlagsetTypeZZZ.UNEXPRESSED;
				
		
			
			
			//###############################################	
			//### Einbinden der Variablen UND Pade in Math-Ausdrücke
			//###############################################
			sSection = "Section for testComputePathWithMath";
			sProperty = "Formula1";
			sExpression = "<Z>[Section for testComputeMath]Formula1</Z>";
			
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
			sExpression = "<Z>[Section for testComputeMath]Formula2</Z>";
			sSection = "Section for testComputePathWithMath";
			sProperty = "Formula2";
			
			sExpression="Der dynamische Wert2 ist '8'. FGL rulez."; //Also der Wert ohne die Math auszurechnen.
			sValue = objFileIniTest.getPropertyValue(sSection, sProperty).getValue();			
			assertEquals(sExpression, sValue);
			assertTrue("Im Ergebnis wurde eine ausgerechnete '8' erwartet.", StringZZZ.contains(sExpression, "8"));
			
			//+++ Verschachtelt, hier wird auf eine Section Verwiesen, die einen Math-Ausdruck enthält, der wiederum eine IniPath-Anweisung hat.... diesmal 2 Path Anweisungen!!!
			sExpression = "<Z>[Section for testComputeMath]Formula3</Z>";
			sSection = "Section for testComputePathWithMath";
			sProperty = "Formula3";
			
			sExpression="Der dynamische Wert3 ist '20'. FGL rulez."; //Also der Wert ohne die Math auszurechnen.
			sValue = objFileIniTest.getPropertyValue(sSection, sProperty).getValue();
			assertEquals(sExpression, sValue);
			assertTrue("Im Ergebnis wurde eine ausgerechnete '20' erwartet.", StringZZZ.contains(sExpression, "20"));
			
			
		}catch(ExceptionZZZ ez){
			fail("An exception happend testing: " + ez.getDetailAllLast());
		}
		bReturn = true;	
		}
		return bReturn;
	}

	
	public boolean testComputeEncryption() {	
		boolean bReturn = false;
		String sExpressionIn; String sExpressionSolvedIn;
		main:{
			try {
				boolean btemp; 
				
				String sValue; String sSection; String sProperty;
				String sExpression; String sExpressionSolved; String sTag; String sTagSolved;			
				ReferenceZZZ<IKernelConfigSectionEntryZZZ> objSectionEntryReference;
				IKernelConfigSectionEntryZZZ objEntry; IKernelConfigSectionEntryZZZ objEntryUsed;
				
				String sTagStartZ = "<Z>";
				String sTagEndZ = "</Z>";	
				IEnumSetMappedTestFlagsetZZZ objEnumFunction = EnumSetMappedTestCaseFlagsetTypeZZZ.UNEXPRESSED;
		
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
			sExpressionIn = KernelEncryptionIniSolverZZZTest.sEXPRESSION_ENCRYPTION01_DEFAULT;
			sSection = "Section for testEncrypted";
			sProperty = "WertAforDecrypt";
			
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionIn, sTagStartZ, sTagEndZ);
					
			sValue = objFileIniTest.getPropertyValue(sSection, sProperty).getValue();
			assertNotNull(sValue);
			assertEquals(sExpressionSolved, sValue);
			
			//+++ Weiterer Lauf mit objEntry, aber immer noch keine Entschluesselung verwenden			
			objEntry = objFileIniTest.getPropertyValue(sSection, sProperty);
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
			sExpressionSolved = objEntry.getValue();
			
			//+++ Vergleichswert holen
			sSection = "Section for testEncrypted";
			sProperty = "WertA";
			sExpressionSolved = "abcde";
			IKernelConfigSectionEntryZZZ objEntryProof = objFileIniTest.getPropertyValue(sSection, sProperty);
			sValue = objEntryProof.getValue();
			assertEquals(sExpressionSolved, sValue);
			
			
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}
			bReturn = true;
		}//end main:
		return bReturn;
	}
	
	public boolean testComputeJson() {	
		boolean bReturn = false;
		String sExpressionIn; String sExpressionSolvedIn;
		main:{
			try {
				boolean btemp; 
				
				String sValue; String sSection; String sProperty;
				String sExpression; String sExpressionSolved; String sTag; String sTagSolved;			
				ReferenceZZZ<IKernelConfigSectionEntryZZZ> objSectionEntryReference;
				IKernelConfigSectionEntryZZZ objEntry; IKernelConfigSectionEntryZZZ objEntryUsed;
				
				String sTagStartZ = "<Z>";
				String sTagEndZ = "</Z>";	
				IEnumSetMappedTestFlagsetZZZ objEnumFunction = EnumSetMappedTestCaseFlagsetTypeZZZ.UNEXPRESSED;
		
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
			sExpressionIn = KernelJsonMapIniSolverZZZTest.sEXPRESSION_JSONMAP01_DEFAULT;

			sExpression = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionIn, sTagStartZ, sTagEndZ);
										
			objEntry = objFileIniTest.getPropertyValue(sSection, sProperty);
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
			bReturn = true;
		}//end main:
		return bReturn;
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
	
