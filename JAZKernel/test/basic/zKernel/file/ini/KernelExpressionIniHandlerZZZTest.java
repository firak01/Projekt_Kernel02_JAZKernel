package basic.zKernel.file.ini;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IObjectWithExpressionZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedTestCaseZZZ;
import basic.zBasic.util.datatype.calling.ReferenceZZZ;
import basic.zBasic.util.datatype.xml.XmlUtilZZZ;
import basic.zBasic.util.machine.EnvironmentZZZ;
import basic.zKernel.IKernelConfigSectionEntryZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernel.KernelZZZ;
import basic.zKernel.config.KernelConfigSectionEntryUtilZZZ;
import custom.zKernel.file.ini.FileIniZZZ;
import junit.framework.TestCase;

public class KernelExpressionIniHandlerZZZTest extends TestCase {	
	protected final static String sEXPRESSION_Expression01_UNEXPRESSED = "Der dynamische Wert1 ist '{[Section A]Testentry1}'. FGL rulez.";
	protected final static String sEXPRESSION_Expression01_DEFAULT = "Der dynamische Wert1 ist '<Z>{[Section A]Testentry1}</Z>'. FGL rulez.";
	protected final static String sEXPRESSION_Expression01_SUBSTITUTED = "Der dynamische Wert1 ist '<Z>Testvalue1 to be found</Z>'. FGL rulez.";
	protected final static String sEXPRESSION_Expression01_SOLVED = "Der dynamische Wert1 ist 'Testvalue1 to be found'. FGL rulez.";
			
	private File objFile;
	private IKernelZZZ objKernel;
	private FileIniZZZ<?> objFileIniTest=null;
	
	/// +++ Die eigentlichen Test-Objekte	
	private KernelExpressionIniHandlerZZZ objExpressionHandler;
	private KernelExpressionIniHandlerZZZ objExpressionHandlerInit;
	
	protected void setUp(){
		try {						
			objFile = TestUtilZZZ.createKernelFileUsed();
			
			//#################
			objKernel = new KernelZZZ("FGL", "01", "test", "ZKernelConfigKernel_test.ini",(String)null);
			
			//#### Ein init TestObjekt
			String[] saFlagInit = {"init"};
			objExpressionHandlerInit = new KernelExpressionIniHandlerZZZ(objKernel, objFileIniTest, saFlagInit);
			
			//#### Das konkrete TestObject
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
							IKernelJsonMapIniSolverZZZ.FLAGZ.USEJSON_MAP.name(),
							IKernelEncryptionIniSolverZZZ.FLAGZ.USEENCRYPTION.name()
							}; //Merke: In static Utility-Methoden ist auch wichtig, was im Ini-File für Flags angestellt sind.
			                   //       und nicht nur die Flags vom ExpressionIniHandler
			objFileIniTest = new FileIniZZZ(objKernel,  objFile, saFlagFileIni);
			
			String[] saFlag = {""}; //Merke: Die Flags des Testobjekts selbst werden in den einzelnen Tests explizit gesetzt.
			objExpressionHandler = new KernelExpressionIniHandlerZZZ(objKernel, objFileIniTest, saFlag);
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}	
	}//END setup
	
	public void testFlagHandling(){
		boolean btemp;
		try{							
			assertTrue(objExpressionHandlerInit.getFlag("init")==true);
			assertFalse(objExpressionHandler.getFlag("init")==true); //Nun wäre init falsch
				
			btemp = objExpressionHandler.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION, true); 
			assertTrue("Flag nicht vorhanden '" + IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
			
			btemp = objExpressionHandler.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH,true);  
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
			
			btemp = objExpressionHandler.setFlag(IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE,true);  
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE + "'", btemp);
							
			btemp = objExpressionHandler.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER, true); 
			assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
			
			//+++++++++++++++++++++++++++++++++
			//... mit Berechnung MATH			
			btemp = objExpressionHandler.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA,true);  
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA + "'", btemp);
			
			btemp = objExpressionHandler.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH,true);
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH + "'", btemp);
			
			
			
			//+++++++++++++++++++++++++++++++++
			//... mit Berechnung JSON
			btemp = objExpressionHandler.setFlag(IKernelJsonIniSolverZZZ.FLAGZ.USEJSON, true); 
			assertTrue("Das Flag '" + IKernelJsonIniSolverZZZ.FLAGZ.USEJSON +"' sollte zur Verfügung stehen.", btemp);
							
			//... mit Berechnung JSON-MAP
			btemp = objExpressionHandler.setFlag(IKernelJsonMapIniSolverZZZ.FLAGZ.USEJSON_MAP, true);//Ansonsten wird der Wert sofort ausgerechnet 
			assertTrue("Das Flag '" + IKernelJsonMapIniSolverZZZ.FLAGZ.USEJSON_MAP +"' sollte zur Verfügung stehen.", btemp);
			
			//++++++++++++++++
			
			//... mit Berechnung JSON-ARRAY
			btemp = objExpressionHandler.setFlag(IKernelJsonArrayIniSolverZZZ.FLAGZ.USEJSON_ARRAY, true);//Ansonsten wird der Wert sofort ausgerechnet 
			assertTrue("Das Flag '" + IKernelJsonArrayIniSolverZZZ.FLAGZ.USEJSON_ARRAY +"' sollte zur Verfügung stehen.", btemp);
			
			//+++++++++++++++++
			
			//... mit Berechnung CALL
			btemp = objExpressionHandler.setFlag(IKernelCallIniSolverZZZ.FLAGZ.USECALL, true); //soll dann egal sein
			assertTrue("Flag nicht vorhanden '" + IKernelCallIniSolverZZZ.FLAGZ.USECALL + "'", btemp);
			
			btemp = objExpressionHandler.setFlag(IKernelJavaCallIniSolverZZZ.FLAGZ.USECALL_JAVA, true);
			assertTrue("Flag nicht vorhanden '" + IKernelJavaCallIniSolverZZZ.FLAGZ.USECALL_JAVA + "'", btemp);
		
			//++++++++++++++++++++++++++++++++++
			String[] saFlag = objExpressionHandler.getFlagZ();
			assertEquals(15,saFlag.length);
		
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}
	
	
	public void testCompute_PATH() {
		String sExpression; String sExpressionSubstituted;String sExpressionSolved;
//		try {	
		
			//+++++++ VORGEZOGENER LETZTER FEHLERTEST START
			
		//B) Test mit Z-Tag drumherum. Erst jetzt ist es eine Expression und wird ersetzt
		sExpression = "<Z>" + KernelExpressionIniHandlerZZZTest.sEXPRESSION_Expression01_UNEXPRESSED + "</Z>";
		sExpressionSubstituted = "<Z>Der dynamische Wert1 ist 'Testvalue1 to be found'. FGL rulez.</Z>";
		sExpressionSolved = KernelExpressionIniHandlerZZZTest.sEXPRESSION_Expression01_SOLVED;
		testCompute_PATH_(sExpression,sExpressionSubstituted, sExpressionSolved);
		
			
			//+++++++ VORGEZOGENER LETZTER FEHLERTEST ENDE
		
            //################################################################
			//A) Test ohne notwendige Pfadersetzung, weil zwar ein PATH-AUSDRUCK aber keine Expression vorliegt
			sExpression = KernelExpressionIniHandlerZZZTest.sEXPRESSION_Expression01_UNEXPRESSED;
			sExpressionSubstituted = sExpression;
			sExpressionSolved = sExpression;
			
			testCompute_PATH_(sExpression,sExpressionSubstituted, sExpressionSolved);
			
			//B) Test mit Z-Tag drumherum. Erst jetzt ist es eine Expression und wird ersetzt
			sExpression = "<Z>" + KernelExpressionIniHandlerZZZTest.sEXPRESSION_Expression01_UNEXPRESSED + "</Z>";
			sExpressionSubstituted = "<Z>Der dynamische Wert1 ist 'Testvalue1 to be found'. FGL rulez.</Z>";
			sExpressionSolved = KernelExpressionIniHandlerZZZTest.sEXPRESSION_Expression01_SOLVED;
			testCompute_PATH_(sExpression,sExpressionSubstituted, sExpressionSolved);
			
			//C) Test mit Z-Tag unmittelbar um den PATH herum. Erst jetzt ist es eine Expression und wird ersetzt
			sExpression = KernelExpressionIniHandlerZZZTest.sEXPRESSION_Expression01_DEFAULT;
			sExpressionSubstituted = KernelExpressionIniHandlerZZZTest.sEXPRESSION_Expression01_SUBSTITUTED;
			sExpressionSolved = KernelExpressionIniHandlerZZZTest.sEXPRESSION_Expression01_SOLVED;
			testCompute_PATH_(sExpression,sExpressionSubstituted, sExpressionSolved);
			
			//TODO diesen String testen:  String sExpressionSource2 = "Der dynamische Wert2 ist '<Z>[Section B]Testentry2</Z>'. FGL rulez.";		
			
//		} catch (ExceptionZZZ ez) {
//			fail("Method throws an exception." + ez.getMessageLast());
//		}
	}
	
	private void testCompute_PATH_(String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn) {
		try {		
			boolean btemp;	
			String sExpression; String sExpressionSubstituted; String sExpressionSolved;
			
			
			String sTagStartZ = "<Z>";
			String sTagEndZ = "</Z>";					
			
			//Default Uebernahme, aber wg. der speziellen Flags wird dies manchmal uebersteuert
			sExpression = sExpressionIn;
			sExpressionSubstituted = sExpressionSubstitutedIn;
			sExpressionSolved = sExpressionSolvedIn;
			
			//+++++++ VORGEZOGENER LETZTER FEHLERTEST START
			
			//+++++++++++++++++++++++++++++++++++++++++++++++++
						
			//+++++++ VORGEZOGENER LETZTER FEHLERTEST ENDE
			
			//##########################################################
			//### PFAD-Ausdruck drin
			//##########################################################
			
			//A) OHNE Aktivierte Expression Berechnungn
			//a)parse
			
			//b) rechne umgebende Z-Tags raus
			
			//c) solve, unexpressed Fall					
			sExpression = sExpressionIn;
			sExpressionSubstituted = sExpression;
			sExpressionSolved = sExpression;
			btemp = testCompute_PATH_unexpressed_(sExpression, sExpressionSubstituted, sExpressionSolved, false, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
	

			//d) solve, unexpressed Fall
			sExpression = sExpressionIn;
			sExpressionSubstituted = sExpression;
			sExpressionSolved = sExpression;						
			btemp = testCompute_PATH_unexpressed_(sExpression, sExpressionSubstituted, sExpressionSolved, true, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);		
			
			
			//+++++++++++++++++++++++++++++++++++++++++++++++
			//B) OHNE Aktivierte PATH Eretzung
			//a)parse
			
			//b) rechne umgebende Z-Tags raus
			
			//c) solve
			
			//d) Rechne fuer das Ergebnis die umgebenden Z-Tags raus
			
			
			//+++++++++++++++++++++++++++++++++++++++++++++++++
			//C) OHNE aktivierte Variablen Ersetung
			//a)parse
			
			//b) rechne umgebende Z-Tags raus
			
			//c) solve
			
			//d) Rechne fuer das Ergebnis die umgebenden Z-Tags raus
			
			//+++++++++++++++++++++++++++++++++++++++++++++++++
			//D) OHNE aktivierte Formel-Berechnung
			//a)parse
			
			//b) rechne umgebende Z-Tags raus
			
			//c) solve
			
			//d) Rechne fuer das Ergebnis die umgebenden Z-Tags raus
			
			
			//+++++++++++++++++++++++++++++++++++++++++++++++++
			//E) OHNE aktivierten Solver
			//a)parse
			
			//b) rechne umgebende Z-Tags raus
			
			//c) solve, unsolved Fall					
			sExpression = sExpressionIn;
			sExpressionSubstituted = sExpressionSubstitutedIn;
			sExpressionSolved = sExpressionSubstituted;
			btemp = testCompute_PATH_unsolved_(sExpression, sExpressionSubstituted, sExpressionSolved, false, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
	

			//d) solve, unsolved Fall - Rechne fuer das Ergebnis die umgebenden Z-Tags raus
			//   Merke: Der deaktivierte Solver laesst die Z-Tags drin.
			sExpression = sExpressionIn;
			sExpressionSubstituted = sExpressionSubstitutedIn;
			sExpressionSolved = sExpressionSubstituted;
			//sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);						
			btemp = testCompute_PATH_unsolved_(sExpression, sExpressionSubstituted, sExpressionSolved, true, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);		
			
			
			
			//++++++++++++++++++++++++++++++++++++++++++++++++
			//F) PATH Berechnung
			//a)parse
			
			//b) rechne umgebende Z-Tags raus
			
			//c) solve						
			sExpression = sExpressionIn;
			sExpressionSubstituted = sExpressionSubstitutedIn;
			sExpressionSolved = sExpressionSolvedIn;
			btemp = testCompute_PATH_(sExpression, sExpressionSubstituted, sExpressionSolved, false, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);	//Z-Tags um PATH-Aufloesung immer rausrechnen!!!		
						
			
			//d) Rechne fuer das Ergebnis die umgebenden Z-Tags raus
			sExpression = sExpressionIn;
			sExpressionSubstituted = sExpressionSubstitutedIn;
			sExpressionSolved = sExpressionSolvedIn;
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
			btemp = testCompute_PATH_(sExpression, sExpressionSubstituted, sExpressionSolved, true, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
			
		
				
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			
			
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}
	
	private boolean testCompute_PATH_unexpressed_(String sExpressionSourceIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, boolean bRemoveSurroundingSeparatorsOnSolve, IEnumSetMappedTestCaseZZZ objEnumTestCase) {
		boolean bReturn = false;
		
		main:{
			try {
				String sExpressionSource; String sExpressionSubstituted; String sExpressionSolved; String sValue;
				boolean btemp; 
												
				sExpressionSource = sExpressionSourceIn;
				sExpressionSubstituted = sExpressionSubstitutedIn;
				sExpressionSolved = sExpressionSolvedIn;
				
				//#########################################################
				//#### SECTION A ########################################## 
				//+++ Anwenden der ersten Formel, ohne Berechnung
				btemp = objExpressionHandler.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION, false); 
				assertTrue("Flag nicht vorhanden '" + IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
					
				btemp = objExpressionHandler.setFlag(IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER,true); //ohne aktivierten Parser keine SUBSTITUTION  
				assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER + "'", btemp);
				
				btemp = objExpressionHandler.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH,true);  //sollte wg. useexpression egal sein
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
				
				btemp = objExpressionHandler.setFlag(IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE,false);  
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE + "'", btemp);

				btemp = objExpressionHandler.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER, true); //sollte wg. useexpression egal sein
				assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
				
				btemp = objExpressionHandler.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA,true);  //sollte wg. useexpression_solver = false egal sein.
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA + "'", btemp);
				
				btemp = objExpressionHandler.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH,true);
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH + "'", btemp);
				
								
				//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++				
				if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.PARSE)) {											
					sValue = objExpressionHandler.parse(sExpressionSource);
					assertEquals(sExpressionSubstituted, sValue);
				}
				//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
				
				if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.SOLVE)) {											
					sValue = objExpressionHandler.parse(sExpressionSource);
					assertEquals(sExpressionSolved, sValue);
				}
				
				bReturn = true;
			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			}
		}//end main:
		return bReturn;
	}
	
	private boolean testCompute_PATH_unsolved_(String sExpressionSourceIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, boolean bRemoveSurroundingSeparatorsOnSolve, IEnumSetMappedTestCaseZZZ objEnumTestCase) {
		boolean bReturn = false;
		
		main:{
			try {
				String sExpressionSource; String sExpressionSubstituted; String sExpressionSolved; String sValue;
				boolean btemp; 
												
				sExpressionSource = sExpressionSourceIn;
				sExpressionSubstituted = sExpressionSubstitutedIn;
				sExpressionSolved = sExpressionSolvedIn;
				
				//#########################################################
				//#### SECTION A ########################################## 
				//+++ Anwenden der ersten Formel, ohne Berechnung
				btemp = objExpressionHandler.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION, true); 
				assertTrue("Flag nicht vorhanden '" + IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
					
				btemp = objExpressionHandler.setFlag(IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER,true);  
				assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER + "'", btemp);
				
				btemp = objExpressionHandler.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH,true); 
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
				
				btemp = objExpressionHandler.setFlag(IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE,true);  
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE + "'", btemp);

				btemp = objExpressionHandler.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER, false); 
				assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
				
				btemp = objExpressionHandler.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA,true);  //sollte wg. useexpression_solver = false egal sein.
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA + "'", btemp);
				
				btemp = objExpressionHandler.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH,true);
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH + "'", btemp);
				
								
				//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++				
				if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.PARSE)) {											
					sValue = objExpressionHandler.parse(sExpressionSource);
					assertEquals(sExpressionSubstituted, sValue);
				}
				//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
				
				if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.SOLVE)) {											
					sValue = objExpressionHandler.parse(sExpressionSource);
					assertEquals(sExpressionSolved, sValue);
				}
				
				bReturn = true;
			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			}
		}//end main:
		return bReturn;
	}

	private boolean testCompute_PATH_PATH_unsubstituted_(String sExpressionSourceIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, boolean bRemoveSurroundingSeparatorsOnSolve, IEnumSetMappedTestCaseZZZ objEnumTestCase) {
		boolean bReturn = false;
		
		main:{
			try {
				String sExpressionSource; String sExpressionSubstituted; String sExpressionSolved; String sValue;
				boolean btemp;
						
				sExpressionSource = sExpressionSourceIn;
				sExpressionSubstituted = sExpressionSubstitutedIn;
				sExpressionSolved = sExpressionSolvedIn;

				//#########################################################
				//#### SECTION A ########################################## 
				btemp = objExpressionHandler.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION, true); //sollte wg. useexpression egal sein
				assertTrue("Flag nicht vorhanden '" + IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
				
				btemp = objExpressionHandler.setFlag(IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER,true); //ohne aktivierten Parser keine SUBSTITUTION  
				assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER + "'", btemp);
								
				btemp = objExpressionHandler.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH,false);  
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
				
				btemp = objExpressionHandler.setFlag(IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE,true);  
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE + "'", btemp);
				
				btemp = objExpressionHandler.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER, true);
				assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
				
				btemp = objExpressionHandler.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA,true); 
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA + "'", btemp);
				
				btemp = objExpressionHandler.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH, true);
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH + "'", btemp);
				
						
				//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++				
				if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.PARSE)) {				
					sValue = objExpressionHandler.parse(sExpressionSource);
					assertEquals(sExpressionSubstituted, sValue);
				}
				//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
				
				if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.SOLVE)) {
					sValue = objExpressionHandler.solve(sExpressionSource, bRemoveSurroundingSeparatorsOnSolve);
					assertEquals(sExpressionSolved, sValue);
				}
								
				bReturn = true;
			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			}
		}//end main:
		return bReturn;
	}
	
	private boolean testCompute_PATH_VARIABLE_unsubstituted_(String sExpressionSourceIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, boolean bRemoveSurroundingSeparatorsOnSolve, IEnumSetMappedTestCaseZZZ objEnumTestCase) {
		boolean bReturn = false;
		
		main:{
			try {
				String sExpressionSource; String sExpressionSubstituted; String sExpressionSolved; String sValue;
				boolean btemp;
				
				sExpressionSource = sExpressionSourceIn;
				sExpressionSubstituted = sExpressionSubstitutedIn;
				sExpressionSolved = sExpressionSolvedIn;
				

				//#########################################################
				//#### SECTION A ########################################## 
				btemp = objExpressionHandler.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION, true); //sollte wg. useexpression egal sein
				assertTrue("Flag nicht vorhanden '" + IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
				
				btemp = objExpressionHandler.setFlag(IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER,true); //ohne aktivierten Parser keine SUBSTITUTION  
				assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER + "'", btemp);
				
				btemp = objExpressionHandler.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH,true);  
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
				
				btemp = objExpressionHandler.setFlag(IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE,false);  
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE + "'", btemp);
				
				btemp = objExpressionHandler.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER, true); //sollte egal sein
				assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
				
				btemp = objExpressionHandler.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA,true); //sollte egal sein
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA + "'", btemp);
					
				btemp = objExpressionHandler.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH,true); //sollte egal sein
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH + "'", btemp);
				
				
				//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
				//+++ ... parse sollte aber IMMER den Z-Tag entfernen...
				if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.PARSE)) {												
					sValue = objExpressionHandler.parse(sExpressionSource);
					assertEquals(sExpressionSubstituted, sValue);
				}
				//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
				
				
				if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.SOLVE)) {
					sValue = objExpressionHandler.solve(sExpressionSource, bRemoveSurroundingSeparatorsOnSolve);
					assertEquals(sExpressionSolved, sValue);
				}
				
				bReturn = true;
			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			}
		}//end main:
		return bReturn;
	}

	
	private boolean testCompute_PATH_SOLVER_unsolve_(String sExpressionSourceIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, boolean bRemoveSurroundingSeparatorsOnSolve, IEnumSetMappedTestCaseZZZ objEnumTestCase) {
		boolean bReturn = false;
		
		main:{
			try {
				String sExpressionSource; String sExpressionSubstituted; String sExpressionSolved; String sValue;
				boolean btemp;
					
				sExpressionSource = sExpressionSourceIn;
				sExpressionSubstituted = sExpressionSubstitutedIn;
				sExpressionSolved = sExpressionSolvedIn;

				//#########################################################
				//#### SECTION A ########################################## 
				btemp = objExpressionHandler.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION, true); //sollte wg. useexpression egal sein
				assertTrue("Flag nicht vorhanden '" + IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
				
				btemp = objExpressionHandler.setFlag(IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER,true); //ohne aktivierten Parser keine SUBSTITUTION  
				assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER + "'", btemp);
				
				btemp = objExpressionHandler.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH, true);  
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
				
				btemp = objExpressionHandler.setFlag(IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE, true);  
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE + "'", btemp);
				
				btemp = objExpressionHandler.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER, false); //sollte egal sein
				assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
				
				btemp = objExpressionHandler.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA, true);  //sollte wg. useexpression_solver = false egal sein.
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA + "'", btemp);
				
				btemp = objExpressionHandler.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH, true); //sollte egal sein
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH + "'", btemp);
				
				//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
				//+++ ... parse sollte aber IMMER den Z-Tag entfernen...
				if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.PARSE)) {															
					sValue = objExpressionHandler.parse(sExpressionSource);
					assertEquals(sExpressionSubstituted, sValue);
				}
				//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
					
				if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.SOLVE)) {
					sValue = objExpressionHandler.solve(sExpressionSource, bRemoveSurroundingSeparatorsOnSolve);
					assertEquals(sExpressionSolved, sValue);
				}
				
				bReturn = true;
			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			}
		}//end main:
		return bReturn;
	}

	private boolean testCompute_PATH_FORMULA_unsolved_(String sExpressionSourceIn, String sExpressionSolvedIn, boolean bRemoveSurroundingSeparatorsOnSolve, IEnumSetMappedTestCaseZZZ objEnumTestCase) {
		boolean bReturn = false;
		
		main:{
			try {
				String sExpressionSource; String sExpressionSolved; String sValue;
				boolean btemp; String stemp;
												

				//#########################################################
				//#### SECTION A ########################################## 
				btemp = objExpressionHandler.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION, true); //sollte wg. useexpression egal sein
				assertTrue("Flag nicht vorhanden '" + IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
				
				btemp = objExpressionHandler.setFlag(IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER,true); //ohne aktivierten Parser keine SUBSTITUTION  
				assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER + "'", btemp);
								
				btemp = objExpressionHandler.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH,true);  
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
				
				btemp = objExpressionHandler.setFlag(IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE,true);  
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE + "'", btemp);
				
				btemp = objExpressionHandler.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER, true); 
				assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
				
				btemp = objExpressionHandler.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA,false);  //Ansonsten wird der Wert sofort ausgerechnet
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA + "'", btemp);
					
				btemp = objExpressionHandler.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH,true); //sollte wg. usefomula = false egal sein.
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH + "'", btemp);
				
				
				//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
				//+++ ... parse sollte aber IMMER den Z-Tag entfernen...
				if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.PARSE)) {
				String sTagStartZ = "<Z>";
				String sTagEndZ = "</Z>";
												
				sExpressionSource = sExpressionSourceIn;		
				sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSource, sTagStartZ, sTagEndZ);				
				sValue = objExpressionHandler.parse(sExpressionSource);
				assertEquals(sExpressionSolved, sValue);
				}
				//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
				
				if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.SOLVE)) {
				sExpressionSource = sExpressionSourceIn;
				sExpressionSolved = sExpressionSolvedIn;
				sValue = objExpressionHandler.solve(sExpressionSource, bRemoveSurroundingSeparatorsOnSolve);
				assertEquals(sExpressionSolved, sValue);

				}
				
				bReturn = true;
			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			}
		}//end main:
		return bReturn;
	}


	
	private boolean testCompute_PATH_(String sExpressionSourceIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, boolean bRemoveSurroundingSeparatorsOnSolve, IEnumSetMappedTestCaseZZZ objEnumTestCase) {
		boolean bReturn = false;
		
		main:{
			try {
				String sExpressionSource; String sExpressionSubstituted; String sExpressionSolved; String sValue;
				boolean btemp; String stemp;
				ReferenceZZZ<IKernelConfigSectionEntryZZZ> objSectionEntryReference;
								
				sExpressionSource = sExpressionSourceIn;
				sExpressionSubstituted = sExpressionSubstitutedIn;
				sExpressionSolved = sExpressionSolvedIn;
								
				//... mit Berechnung PATH
				btemp = objExpressionHandler.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION, true); //sollte wg. useexpression egal sein
				assertTrue("Flag nicht vorhanden '" + IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
				
				btemp = objExpressionHandler.setFlag(IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER,true); //ohne aktivierten Parser keine SUBSTITUTION  
				assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER + "'", btemp);
								
				btemp = objExpressionHandler.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH,true);  
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
				
				btemp = objExpressionHandler.setFlag(IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE,true);  
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE + "'", btemp);
				
				btemp = objExpressionHandler.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER, true); //Ansonsten wird der Wert sofort ausgerechnet
				assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
				
				btemp = objExpressionHandler.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA,true);
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA + "'", btemp);
				
				btemp = objExpressionHandler.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH,true); //ohne Mathematische Berechnung, sollte daher egal sein
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH + "'", btemp);
						
				if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.PARSE)) {

					//sExpressionSolved = "Der dynamische Wert ist '<Z>Testvalue1 to be found</Z>'. FGL rulez.";			
					objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
					sValue = objExpressionHandler.parse(sExpressionSource, bRemoveSurroundingSeparatorsOnSolve); //false ist wichtig, damit die Z-Tags enthalten bleiben, die die Formeln umgeben
					assertEquals(sExpressionSubstituted, sValue);
				}
				
				if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.SOLVE)) {

					//sExpressionSolved = "Der dynamische Wert ist '<Z>Testvalue1 to be found</Z>'. FGL rulez.";			
					objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
					sValue = objExpressionHandler.solve(sExpressionSource, bRemoveSurroundingSeparatorsOnSolve); //false ist wichtig, damit die Z-Tags enthalten bleiben, die die Formeln umgeben
					assertEquals(sExpressionSolved, sValue);
				}
				
				bReturn = true;
			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			}
		}//end main:
		return bReturn;
	}
	
	
	
	//#########################################################
	//### MATH
	//#########################################################
	public void testCompute_MATH() {
		String sExpression=null; String sExpressionSubstituted; String sExpressionSolved=null;
//		try {
			//Test ohne MATH-Expression
			sExpression = KernelExpressionIniHandlerZZZTest.sEXPRESSION_Expression01_DEFAULT;
			sExpressionSubstituted = KernelExpressionIniHandlerZZZTest.sEXPRESSION_Expression01_SUBSTITUTED;
			sExpressionSolved = KernelExpressionIniHandlerZZZTest.sEXPRESSION_Expression01_SOLVED;
			
			testCompute_MATH_(sExpression,sExpressionSubstituted, sExpressionSolved);
			
			//sExpressionSourceFormulaMath="Der dynamische Wert ist '<Z><Z:math><Z:val>2</Z:val><Z:op>*</Z:op><Z:val>3</Z:val></Z:math></Z>'. FGL rulez.";
			//sExpressionFormulaMathSolved = "Der dynamische Wert ist '<Z><Z:math><Z:val>2</Z:val><Z:op>*</Z:op><Z:val>3</Z:val></Z:math></Z>'. FGL rulez.";
			
			
			
//		} catch (ExceptionZZZ ez) {
//			fail("Method throws an exception." + ez.getMessageLast());
//		}
	}

	
	private void testCompute_MATH_(String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn) {
		try {			
			boolean btemp;
			String sTagStartZ = "<Z>";
			String sTagEndZ = "</Z>";						
			String sExpression; String sExpressionSubstituted; String sExpressionSolved;
			String sExpressionSourceFormulaMath; String sExpressionFormulaMathSubstituted; String sExpressionFormulaMathSolved;
			
			sExpression = sExpressionIn;
			sExpressionSubstituted = sExpressionSubstitutedIn;
			sExpressionSolved = sExpressionSolvedIn;
			
			//+++++++ VORGEZOGENER LETZTER FEHLERTEST START
			
			
			
			//+++++++ VORGEZOGENER LETZTER FEHLERTEST ENDE
			
						
			
			//###################################
			//### MATH
			//###################################
			
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//+++ Kein Math-Ausdruck drin, trotzdem den Pfad ausrechen
			//a) parse
			btemp = testCompute_MATH_(sExpression, sExpressionSubstituted, sExpressionSolved, false, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);	

			//b)Rechne fuer das Ergebnis die umgebenden Z-Tags raus
			sExpression = sExpressionIn;
			sExpressionSolved = sExpressionSolvedIn;
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);			
			btemp = testCompute_MATH_(sExpression, sExpressionSubstituted, sExpressionSolved, true, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
			
			//c)) solve
			sExpression = sExpressionIn;
			sExpressionSolved = sExpressionSolvedIn;
			btemp = testCompute_MATH_(sExpression, sExpressionSubstituted, sExpressionSolved, false, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
			
			//d)Rechne fuer das Ergebnis die umgebenden Z-Tags raus
			sExpression = sExpressionIn;
			sExpressionSolved = sExpressionSolvedIn;
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
			btemp = testCompute_MATH_(sExpression, sExpressionSubstituted, sExpressionSolved, true, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			
			
			//##########################################################
			//### Math-Ausdruck drin, 
			//### OHNE einen OPERATOR zu 
			//### - FALL: KEINE AUFLOESUNG
			//##########################################################
			//a) parse
			sExpressionSourceFormulaMath="Der dynamische Wert ist '<Z><Z:math><Z:val>2</Z:val><Z:val>3</Z:val></Z:math></Z>'. FGL rulez.";
			sExpressionFormulaMathSubstituted = sExpressionSourceFormulaMath;
			sExpressionFormulaMathSolved = sExpressionSourceFormulaMath;
			
			btemp = testCompute_MATH_unsolved_(sExpressionSourceFormulaMath, sExpressionFormulaMathSubstituted, sExpressionFormulaMathSolved, false, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);

			//b) ohne Z-Tags
			sExpressionSourceFormulaMath="Der dynamische Wert ist '<Z><Z:math><Z:val>2</Z:val><Z:val>3</Z:val></Z:math></Z>'. FGL rulez.";
			sExpressionFormulaMathSolved = sExpressionSourceFormulaMath;
			sExpressionSolved= KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionFormulaMathSolved, sTagStartZ, sTagEndZ);
			btemp = testCompute_MATH_unsolved_(sExpressionSourceFormulaMath, sExpressionFormulaMathSubstituted, sExpressionSolved, true, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
				
			//c) solve
			sExpressionSourceFormulaMath="Der dynamische Wert ist '<Z><Z:math><Z:val>2</Z:val><Z:val>3</Z:val></Z:math></Z>'. FGL rulez.";
			sExpressionFormulaMathSolved = sExpressionSourceFormulaMath;
			btemp = testCompute_MATH_unsolved_(sExpressionSourceFormulaMath, sExpressionFormulaMathSubstituted, sExpressionFormulaMathSolved, false, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);	

			//d) ohne Z-Tags
			sExpressionSourceFormulaMath="Der dynamische Wert ist '<Z><Z:math><Z:val>2</Z:val><Z:val>3</Z:val></Z:math></Z>'. FGL rulez.";
			sExpressionFormulaMathSubstituted = sExpressionSourceFormulaMath;
			sExpressionFormulaMathSolved= KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionFormulaMathSubstituted, sTagStartZ, sTagEndZ);
					
			sExpression = sExpressionSourceFormulaMath;
			sExpressionSubstituted = sExpressionFormulaMathSubstituted;
			sExpressionSolved = sExpressionFormulaMathSolved;			 			
			btemp = testCompute_MATH_unsolved_(sExpression, sExpressionSubstituted, sExpressionSolved, true, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
			
			//##########################################################
			//### Math-Ausdruck drin, OHNE einen OPERATOR zu verwenden
			//##########################################################
			//a) parse
			sExpressionSourceFormulaMath="Der dynamische Wert ist '<Z><Z:math><Z:val>2</Z:val><Z:val>3</Z:val></Z:math></Z>'. FGL rulez.";
			sExpression = sExpressionSourceFormulaMath;
			sExpressionSubstituted = sExpression;
			sExpressionSolved= KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSubstituted, sTagStartZ, sTagEndZ);
			btemp = testCompute_MATH_(sExpression, sExpressionSubstituted, sExpressionSolved, false, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);

			//b) ohne Z-Tags
			sExpressionSourceFormulaMath="Der dynamische Wert ist '<Z><Z:math><Z:val>2</Z:val><Z:val>3</Z:val></Z:math></Z>'. FGL rulez.";
			sExpression = sExpressionSourceFormulaMath;
			sExpressionSubstituted = sExpression;
			sExpressionSolved= KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSourceFormulaMath, sTagStartZ, sTagEndZ);
			btemp = testCompute_MATH_(sExpression, sExpressionSubstituted, sExpressionSolved, true, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
				
			//c) solve
			sExpressionSourceFormulaMath="Der dynamische Wert ist '<Z><Z:math><Z:val>2</Z:val><Z:val>3</Z:val></Z:math></Z>'. FGL rulez.";
			sExpression = sExpressionSourceFormulaMath;
			sExpressionSubstituted = sExpression;
			sExpressionSolved = "Der dynamische Wert ist '<Z>23</Z>'. FGL rulez.";
			btemp = testCompute_MATH_(sExpression, sExpressionSubstituted, sExpressionSolved, false, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);	

			//d) ohne Z-Tags
			sExpressionSourceFormulaMath="Der dynamische Wert ist '<Z><Z:math><Z:val>2</Z:val><Z:val>3</Z:val></Z:math></Z>'. FGL rulez.";
			sExpression = sExpressionSourceFormulaMath;
			sExpressionSubstituted = sExpression;
			sExpressionSolved = "Der dynamische Wert ist '<Z>23</Z>'. FGL rulez.";
			sExpressionSolved= KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);			
			btemp = testCompute_MATH_(sExpression, sExpressionSubstituted, sExpressionSolved, true, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
						
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			
			
			
			//#####################################################
			//### Math-Ausdruck drin, der einen Operator enthaelt
			//#####################################################
			//a) parse
			sExpressionSourceFormulaMath="Der dynamische Wert ist '<Z><Z:math><Z:val>2</Z:val><Z:op>*</Z:op><Z:val>3</Z:val></Z:math></Z>'. FGL rulez.";
			sExpressionFormulaMathSubstituted = sExpressionSourceFormulaMath;
			sExpressionFormulaMathSolved = sExpressionFormulaMathSubstituted;
			btemp = testCompute_MATH_(sExpressionSourceFormulaMath, sExpressionFormulaMathSubstituted, sExpressionSourceFormulaMath, false, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);

			//b) ohne Z-Tags
			sExpressionSourceFormulaMath="Der dynamische Wert ist '<Z><Z:math><Z:val>2</Z:val><Z:op>*</Z:op><Z:val>3</Z:val></Z:math></Z>'. FGL rulez.";
			sExpressionFormulaMathSubstituted = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSourceFormulaMath, sTagStartZ, sTagEndZ);
			sExpressionFormulaMathSolved = sExpressionFormulaMathSubstituted;
			
			sExpression = sExpressionSourceFormulaMath;
			sExpressionSubstituted = sExpressionFormulaMathSubstituted;
			sExpressionSolved = sExpressionFormulaMathSolved;
			btemp = testCompute_MATH_(sExpression, sExpressionSubstituted, sExpressionSolved, true, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
						
			//c) solve
			sExpressionSourceFormulaMath="Der dynamische Wert ist '<Z><Z:math><Z:val>2</Z:val><Z:op>*</Z:op><Z:val>3</Z:val></Z:math></Z>'. FGL rulez.";
			sExpressionFormulaMathSubstituted = sExpressionSourceFormulaMath;
			sExpressionFormulaMathSolved = "Der dynamische Wert ist '<Z>6</Z>'. FGL rulez.";
			btemp = testCompute_MATH_(sExpressionSourceFormulaMath, sExpressionFormulaMathSubstituted, sExpressionFormulaMathSolved, false, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);	

			//d) ohne Z-Tags
			sExpressionSourceFormulaMath="Der dynamische Wert ist '<Z><Z:math><Z:val>2</Z:val><Z:op>*</Z:op><Z:val>3</Z:val></Z:math></Z>'. FGL rulez.";
			sExpressionFormulaMathSubstituted = sExpressionSourceFormulaMath;
			sExpressionFormulaMathSolved = "Der dynamische Wert ist '<Z>6</Z>'. FGL rulez.";
			
			sExpression = sExpressionSourceFormulaMath;
			sExpressionSubstituted = sExpressionFormulaMathSubstituted;			
			sExpressionSolved= sExpressionFormulaMathSolved;			
			btemp = testCompute_MATH_(sExpression, sExpressionSubstituted, sExpressionSolved, true, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
						
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			
			
						
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}


	private boolean testCompute_MATH_unsolved_(String sExpressionSourceIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, boolean bRemoveSurroundingSeparatorsOnSolve, IEnumSetMappedTestCaseZZZ objEnumTestCase) {
		boolean bReturn = false;
		
		main:{
			try {
				String sExpressionSource; String sExpressionSubstituted; String sExpressionSolved; String sValue;
				boolean btemp;
				ReferenceZZZ<IKernelConfigSectionEntryZZZ> objSectionEntryReference;
				
				sExpressionSource = sExpressionSourceIn;
				sExpressionSubstituted = sExpressionSubstitutedIn;
				sExpressionSolved = sExpressionSolvedIn;
				
				//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
				//... ohne Berechnung (math)
				btemp = objExpressionHandler.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION, true); //sollte wg. useexpression egal sein
				assertTrue("Flag nicht vorhanden '" + IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
				
				btemp = objExpressionHandler.setFlag(IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER,true); //ohne aktivierten Parser keine SUBSTITUTION  
				assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER + "'", btemp);
								
				btemp = objExpressionHandler.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH,true);  
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
				
				btemp = objExpressionHandler.setFlag(IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE,true);  
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE + "'", btemp);
				
				btemp = objExpressionHandler.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER, true); //Ansonsten wird der Wert sofort ausgerechnet
				assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
				
				btemp = objExpressionHandler.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA,true);  
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA + "'", btemp);
				
				btemp = objExpressionHandler.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH,false);
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH + "'", btemp);
																
				//+++ ... parse ist nicht solve... also wird hier nichts aufgeloest, aussser die Pfade
				if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.PARSE)) {		
					objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
					sValue = objExpressionHandler.parse(sExpressionSource, objSectionEntryReference, bRemoveSurroundingSeparatorsOnSolve);
					assertEquals(sExpressionSubstituted, sValue);
				}
				//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
					
				//+++ ... solve verhält sich NICHT wie parse(), bei solve wird aufgeloest...
				if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.SOLVE)) {
					objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
					sValue = objExpressionHandler.solve(sExpressionSource, objSectionEntryReference, bRemoveSurroundingSeparatorsOnSolve);
					assertEquals(sExpressionSolved, sValue);
				}
				//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
				
				
				bReturn = true;
			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			}
		}//end main:
		return bReturn;
	}
	
	private boolean testCompute_MATH_(String sExpressionSourceIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, boolean bRemoveSurroundingSeparatorsOnSolve, IEnumSetMappedTestCaseZZZ objEnumTestCase) {
		boolean bReturn = false;
		
		main:{
			try {
				String sExpressionSource; String sExpressionSubstituted; String sExpressionSolved; String sValue;
				boolean btemp; String stemp;
				ReferenceZZZ<IKernelConfigSectionEntryZZZ> objSectionEntryReference;
				String sTagStartZ = "<Z>";
				String sTagEndZ = "</Z>";
					
				sExpressionSource = sExpressionSourceIn;
				sExpressionSubstituted = sExpressionSubstitutedIn;
				sExpressionSolved = sExpressionSolvedIn;
				
				//#########################################################
				//### Mit Berechnung MATH 
				//#########################################################
				
				//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
				//... mit Berechnung (math)
				btemp = objExpressionHandler.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION, true); //sollte wg. useexpression egal sein
				assertTrue("Flag nicht vorhanden '" + IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
				
				btemp = objExpressionHandler.setFlag(IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER,true); //ohne aktivierten Parser keine SUBSTITUTION  
				assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER + "'", btemp);
				
				btemp = objExpressionHandler.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH,true);  
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
				
				btemp = objExpressionHandler.setFlag(IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE,true);  
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE + "'", btemp);
				
				btemp = objExpressionHandler.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER, true); //Ansonsten wird der Wert sofort ausgerechnet
				assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
				
				btemp = objExpressionHandler.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA,true);  
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA + "'", btemp);
				
				btemp = objExpressionHandler.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH,true);
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH + "'", btemp);
													
				//+++ ... parse ist nicht solve... also wird hier nichts aufgeloest, aussser die Pfade
				if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.PARSE)){		
					objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
					sValue = objExpressionHandler.parse(sExpressionSource, objSectionEntryReference, bRemoveSurroundingSeparatorsOnSolve);
					assertEquals(sExpressionSubstituted, sValue);
				}
				//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
					
				//+++ ... solve verhält sich NICHT wie parse(), bei solve wird aufgeloest...
				if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.SOLVE)) {
					objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
					sValue = objExpressionHandler.solve(sExpressionSource, objSectionEntryReference, bRemoveSurroundingSeparatorsOnSolve);
					assertEquals(sExpressionSolved, sValue);
				}
				//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
				
				
				bReturn = true;
			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			}
		}//end main:
		return bReturn;
	}
	
	public void testCompute_PATH_IniUsed() {
		boolean btemp;
		String sSection; String sProperty;	
		String sExpressionSolved; String sTagSolved;
		
		String sTagStartZ = "<Z>";
		String sTagEndZ = "</Z>";					
		
		try {			
			
			//+++++++ VORGEZOGENER LETZTER FEHLERTEST START
			
			
			
			//+++++++ VORGEZOGENER LETZTER FEHLERTEST ENDE
		
			
			
			//################################################################################
			//### Verwende den Expression-Sover ueber das FileIni-Objekt
			//################################################################################
			
			//
			//Merke: Beim Holen der ini-Property, werden die Z-Tags normalerweise entfernt.
			//       Ausser ueber Flag ist etwas deaktivert.... 
			
			
			//TODO diesen String testen:  String sExpressionSource2 = "Der dynamische Wert2 ist '<Z>[Section B]Testentry2</Z>'. FGL rulez.";		
			
			
			//#########################################################
			//#### SECTION A ##########################################
			//#### HIER LIEGT DER WERT IN EINER SECTION MIT EINER SYSTEMNUMBER.
			//#### In der Section Property-Kombination liegt ein Pfad mit einem anderen Wert
			//#########################################################
			
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//#### Hier das Substitute ausschalten, wg. Solve wird der Z-Tag aber trotzdem entfernt.
			sSection = "Section for testCompute";
			sProperty = "Formula1";						
			sExpressionSolved = KernelExpressionIniHandlerZZZTest.sEXPRESSION_Expression01_DEFAULT;
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
			sTagSolved = "{[Section A]Testentry1}";
			btemp = testCompute_PATH_IniUsed_Unsubstituted_(sSection, sProperty, sExpressionSolved, sTagSolved);	
			
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//#### Hier das Solve ausschalten, ohne SOLVE bleibt das Z-Tag drin, auch wenn sutbstituiert wird
			sSection = "Section for testCompute";
			sProperty = "Formula1";
			sExpressionSolved = KernelExpressionIniHandlerZZZTest.sEXPRESSION_Expression01_SUBSTITUTED;//ohne Formelberechnung bleibt der umgebende Z-Tag drin			
			sTagSolved = "Testvalue1 to be found";
			btemp = testCompute_PATH_IniUsed_Unsolved_(sSection, sProperty, sExpressionSolved, sTagSolved);	
			

			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//#### Hier komplett aufloesen
			sSection = "Section for testCompute";
			sProperty = "Formula1";
			sExpressionSolved = KernelExpressionIniHandlerZZZTest.sEXPRESSION_Expression01_SOLVED;
			sTagSolved = "Testvalue1 to be found";
			btemp = testCompute_PATH_IniUsed_(sSection, sProperty, sExpressionSolved, sTagSolved);	
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			
			
			
			//#########################################################
			//#### SECTION B ##########################################
			//#### In der Section Property-Kombination liegt ein Pfad mit einem anderen Wert
			//#### HIER LIEGT DER WERT IN EINER SECTION MIT EINER SYSTEMNUMBER.
			//#########################################################
		
			//#### Hier das Substitute ausschalten, wg. Solve wird der Z-Tag aber trotzdem entfernt.	 
			sSection = "Section for testCompute";
			sProperty = "Formula2";	//Merke: Nur der Parser laesst den Z-Tag drin. Damit der Solver ihn verarbeiten koennte.
			                        //       Aber ohne Solver, bleibt es dann auch so.
			sExpressionSolved = "Der dynamische Wert2 ist '<Z>{[Section B]Testentry2}</Z>'. FGL rulez.";
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
			sTagSolved = "{[Section B]Testentry2}";
			btemp = testCompute_PATH_IniUsed_Unsubstituted_(sSection, sProperty, sExpressionSolved, sTagSolved);	
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//#### Hier das Solve ausschalten, es wird also nur substituted		
			sSection = "Section for testCompute";
			sProperty = "Formula2";						
			sExpressionSolved = "Der dynamische Wert2 ist '<Z>Testvalue2 local to be found</Z>'. FGL rulez.";//ohne Formelberechnung bleibt der umgebende Z-Tag drin
			//sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
			sTagSolved = "Testvalue2 local to be found";
			btemp = testCompute_PATH_IniUsed_Unsolved_(sSection, sProperty, sExpressionSolved, sTagSolved);	

			
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//#### Hier das Solve EIN-schalten
			sSection = "Section for testCompute";
			sProperty = "Formula2";						
			sExpressionSolved = "Der dynamische Wert2 ist '<Z>Testvalue2 local to be found</Z>'. FGL rulez.";
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
			sTagSolved = "Testvalue2 local to be found";
			btemp = testCompute_PATH_IniUsed_(sSection, sProperty, sExpressionSolved, sTagSolved);	
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			
			//#########################################################
			//#### SECTION C ##########################################
			//#### In der Section Property-Kombination liegt ein Pfad mit einem anderen Wert
			//#### HIER LIEGT DER WERT IN EINER SECTION MIT EINER SYSTEMNUMBER, die direkt hinter dem ApplicationKey steht
			
			//### Substitute ausschalten, solve bleibt eingeschaltet
			sSection = "Section for testCompute";
			sProperty = "Formula3";	
			sExpressionSolved = "Der dynamische Wert3 ist '<Z>{[Section C]Testentry3}</Z>'. FGL rulez.";
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
			sTagSolved = "{[Section C]Testentry3}";
			btemp = testCompute_PATH_IniUsed_Unsubstituted_(sSection, sProperty, sExpressionSolved, sTagSolved);	
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//### Solve ausschalten, substitute wird durchgeführt			
			sSection = "Section for testCompute";
			sProperty = "Formula3";	
			sExpressionSolved = "Der dynamische Wert3 ist '<Z>Testvalue3 local to be found</Z>'. FGL rulez.";
			//sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
			sTagSolved = "Testvalue3 local to be found";
			btemp = testCompute_PATH_IniUsed_Unsolved_(sSection, sProperty, sExpressionSolved, sTagSolved);	
			
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//### Solve einschalten
			//a) Direkt
			sSection = "Section C";
			sProperty = "Testentry3";						
			sExpressionSolved = "Testvalue3 local to be found";
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
			sTagSolved = ""; //Merke: Im direkten Wert steckt kein Z-Tag, darum ist der Wert hier leer 
			btemp = testCompute_PATH_IniUsed_(sSection, sProperty, sExpressionSolved, sTagSolved);	
		
			//b) Ueber den Path
			sSection = "Section for testCompute";
			sProperty = "Formula3";	
			sExpressionSolved = "Der dynamische Wert3 ist '<Z>Testvalue3 local to be found</Z>'. FGL rulez.";
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
			sTagSolved = "Testvalue3 local to be found";
			btemp = testCompute_PATH_IniUsed_(sSection, sProperty, sExpressionSolved, sTagSolved);	
				
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}
	
	private boolean testCompute_PATH_IniUsed_(String sSectionIn, String sPropertyIn, String sExpressionSolvedIn, String sTagSolvedIn) {
		boolean bReturn = false;
		String sSection; String sProperty; String sExpressionSolved; String sTagSolved; String sValue;
		boolean btemp; String stemp;
		main:{
			try {				
				ReferenceZZZ<IKernelConfigSectionEntryZZZ> objSectionEntryReference;
				
				sSection = sSectionIn;
				sProperty = sPropertyIn;
				sExpressionSolved = sExpressionSolvedIn;	
				sTagSolved = sTagSolvedIn;
				
				//... mit Berechnung PATH
				btemp = objFileIniTest.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION, true); 
				assertTrue("Flag nicht vorhanden '" + IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
				
				btemp = objFileIniTest.setFlag(IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER,true); //ohne aktivierten Parser keine SUBSTITUTION  
				assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER + "'", btemp);
								
				btemp = objFileIniTest.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH,true);  
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
				
				btemp = objFileIniTest.setFlag(IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE,true);  
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE + "'", btemp);
				
				btemp = objFileIniTest.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER, true); 
				assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
								
				btemp = objFileIniTest.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA,true);
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA + "'", btemp);
				
				btemp = objFileIniTest.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH, true); 
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH + "'", btemp);
									
				objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
				IKernelConfigSectionEntryZZZ objEntry = objFileIniTest.getPropertyValue(sSection, sProperty);
				assertNotNull(objEntry);
				 
				sValue = objEntry.getValue();
				assertEquals(sExpressionSolved, sValue);
				
				sValue = objEntry.getValueFromTag();
				assertEquals(sTagSolved, sValue);
				
				bReturn = true;
			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			}
		}//end main:
		return bReturn;
	}
	
	
	private boolean testCompute_PATH_IniUsed_Unsubstituted_(String sSectionIn, String sPropertyIn, String sExpressionSolvedIn, String sTagSolvedIn) {
		boolean bReturn = false;
		
		main:{
			try {
				String sSection; String sProperty; String sExpressionSolved; String sTagSolved; String sValue;
				boolean btemp; String stemp;
				ReferenceZZZ<IKernelConfigSectionEntryZZZ> objSectionEntryReference;

				sSection = sSectionIn;
				sProperty = sPropertyIn;				
				sExpressionSolved = sExpressionSolvedIn;	
				sTagSolved = sTagSolvedIn;
				
				//... mit Berechnung PATH
				btemp = objFileIniTest.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION, true); 
				assertTrue("Flag nicht vorhanden '" + IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
			
				btemp = objFileIniTest.setFlag(IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER,true); //ohne aktivierten Parser keine SUBSTITUTION  
				assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER + "'", btemp);
				
				btemp = objFileIniTest.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH,false);   //Ansonsten wird der Wert sofort ausgerechnet
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
								
				btemp = objFileIniTest.setFlag(IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE,true);  
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE + "'", btemp);
				
				btemp = objFileIniTest.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER, true); //sollte egal sein
				assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
				
				//... ohne solve-Flag ist die eine Berechnung egal
				btemp = objFileIniTest.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA,true);                     //sollte wg. USEEXPRESSION_SOLVER = false egal sein.
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA + "'", btemp);
				
				//... ohne solve-Flag ist die Mathematische Berechnung egal
				btemp = objFileIniTest.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH,true); 
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH + "'", btemp);
				
				objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
				IKernelConfigSectionEntryZZZ objEntry = objFileIniTest.getPropertyValue(sSection, sProperty);
				assertNotNull(objEntry);
				 
				sValue = objEntry.getValue();
				assertEquals(sExpressionSolved, sValue);
				
				sValue = objEntry.getValueFromTag();
				assertEquals(sTagSolved, sValue);
												
				bReturn = true;
			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			}
		}//end main:
		return bReturn;
	}
	
	
	private boolean testCompute_PATH_IniUsed_Unsolved_(String sSectionIn, String sPropertyIn, String sExpressionSolvedIn, String sTagSolvedIn) {
		boolean bReturn = false;
		
		main:{
			try {
				String sSection; String sProperty; String sExpressionSolved; String sTagSolved; String sValue;
				boolean btemp; String stemp;
				ReferenceZZZ<IKernelConfigSectionEntryZZZ> objSectionEntryReference;
				
				sSection = sSectionIn;
				sProperty = sPropertyIn;
				sExpressionSolved = sExpressionSolvedIn;
				sTagSolved = sTagSolvedIn;
				
				//... mit Berechnung PATH
				btemp = objFileIniTest.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION, true); 
				assertTrue("Flag nicht vorhanden '" + IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
			
				btemp = objFileIniTest.setFlag(IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER,true); //ohne aktivierten Parser keine SUBSTITUTION  
				assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER + "'", btemp);
				
				btemp = objFileIniTest.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH,true);  
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
								
				btemp = objFileIniTest.setFlag(IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE,true);  
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE + "'", btemp);
				
				btemp = objFileIniTest.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER, false); //Ansonsten wird der Wert sofort ausgerechnet
				assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
				
				//... ohne solve-Flag ist die eine Berechnung egal
				btemp = objFileIniTest.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA,true);                     //sollte wg. USEEXPRESSION_SOLVER = false egal sein.
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA + "'", btemp);
				
				//... ohne solve-Flag ist die Mathematische Berechnung egal
				btemp = objFileIniTest.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH,true); 
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH + "'", btemp);
									
				objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
				IKernelConfigSectionEntryZZZ objEntry = objFileIniTest.getPropertyValue(sSection, sProperty);
				assertNotNull(objEntry);
				 
				sValue = objEntry.getValue();
				assertEquals(sExpressionSolved, sValue);
				
				sValue = objEntry.getValueFromTag();
				assertEquals(sTagSolved, sValue);
				
				bReturn = true;
			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			}
		}//end main:
		return bReturn;
	}
	
	public void testCompute_MATH_IniUsed() {
		boolean btemp;
		String sSection; String sProperty;
		String sExpressionSolved; String sTagSolved;
		
		String sTagStartZ = "<Z>";
		String sTagEndZ = "</Z>";						
	
		main:{
			try {
				//################################################################################
				//### Verwende den Expression-Sover ueber das FileIni-Objekt
				//################################################################################
				//TODO diesen String testen:  String sExpressionSource2 = "Der dynamische Wert2 ist '<Z>[Section B]Testentry2</Z>'. FGL rulez.";		
					
				//##############################################################
				//#### SECTION 1 MATH ##########################################
				//#### Einfache MATH Formel ohne Operator, ohne PATH, etc.
				//a) ohne jedliche Aufloesung
				sSection = "Section for testComputeMathValue";
				sProperty = "Formula1";	
				sExpressionSolved = "Der dynamische Wert ist '<Z><Z:math><Z:val>2</Z:val><Z:val>3</Z:val></Z:math></Z>'. FGL rulez.";
				sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
				sTagSolved = "<Z:math><Z:val>2</Z:val><Z:val>3</Z:val></Z:math>";
				btemp = testCompute_PATH_IniUsed_Unsolved_(sSection, sProperty, sExpressionSolved, sTagSolved);	
				
				//b) ohne FORMULA Aufloesung
				sSection = "Section for testComputeMathValue";
				sProperty = "Formula1";							
				sExpressionSolved = "Der dynamische Wert ist '<Z><Z:math><Z:val>2</Z:val><Z:val>3</Z:val></Z:math></Z>'. FGL rulez.";
				sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
				sTagSolved = "<Z:math><Z:val>2</Z:val><Z:val>3</Z:val></Z:math>";
				btemp = testCompute_PATH_IniUsed_(sSection, sProperty, sExpressionSolved, sTagSolved);
				
				//c) ohne MATH Aufloesung
				sSection = "Section for testComputeMathValue";
				sProperty = "Formula1";	
				sExpressionSolved = "Der dynamische Wert ist '<Z><Z:math><Z:val>2</Z:val><Z:val>3</Z:val></Z:math></Z>'. FGL rulez.";
				sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
				sTagSolved = "<Z:math><Z:val>2</Z:val><Z:val>3</Z:val></Z:math>";
				btemp = testCompute_MATH_IniUsed_unsolved_(sSection, sProperty, sExpressionSolved, sTagSolved);	
				
				
				//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
				//#### Einfache MATH Formel ohne Operator, ohne PATH, etc.
				//d) Aufloesen
				sSection = "Section for testComputeMathValue";
				sProperty = "Formula1";							
				sExpressionSolved = "Der dynamische Wert ist '<Z>23</Z>'. FGL rulez.";
				sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
				sTagSolved = "23";
				btemp = testCompute_MATH_IniUsed_(sSection, sProperty, sExpressionSolved, sTagSolved);	

			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			}
		}//end main:
	}
	
	private boolean testCompute_MATH_IniUsed_(String sSectionIn, String sPropertyIn, String sExpressionSolvedIn, String sTagSolvedIn) {
		boolean bReturn = false;
		
		main:{
			try {
				String sSection; String sProperty; String sExpressionSolved; String sTagSolved; String sValue;
				boolean btemp;
				ReferenceZZZ<IKernelConfigSectionEntryZZZ> objSectionEntryReference;
				
				sSection = sSectionIn;
				sProperty = sPropertyIn;
				sExpressionSolved = sExpressionSolvedIn;	
				sTagSolved = sTagSolvedIn;
				
				//... mit Berechnung PATH
				btemp = objFileIniTest.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION, true); 
				assertTrue("Flag nicht vorhanden '" + IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
				
				btemp = objFileIniTest.setFlag(IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER,true); //ohne aktivierten Parser keine SUBSTITUTION  
				assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER + "'", btemp);
				
				btemp = objFileIniTest.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH,true);  
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
				
				btemp = objFileIniTest.setFlag(IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE,false);  
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE + "'", btemp);
								
				btemp = objFileIniTest.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER, true); 
				assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
				
				btemp = objFileIniTest.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA,true);
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA + "'", btemp);
			
				//... mit Berechnung MATH
				btemp = objFileIniTest.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH, true); 
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH + "'", btemp);
									
				objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
				IKernelConfigSectionEntryZZZ objEntry = objFileIniTest.getPropertyValue(sSection, sProperty);
				assertNotNull(objEntry);
				 
				sValue = objEntry.getValue();
				assertEquals(sExpressionSolved, sValue);
				
				sValue = objEntry.getValueFromTag();
				assertEquals(sTagSolved, sValue);
				
				bReturn = true;
			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			}
		}//end main:
		return bReturn;
	}
	
	private boolean testCompute_MATH_IniUsed_unsolved_(String sSectionIn, String sPropertyIn, String sExpressionSolvedIn, String sTagSolvedIn) {
		boolean bReturn = false;
		
		main:{
			try {
				String sSection; String sProperty; String sExpressionSolved; String sTagSolved; String sValue;
				boolean btemp; 
				ReferenceZZZ<IKernelConfigSectionEntryZZZ> objSectionEntryReference;
				
				sSection = sSectionIn;
				sProperty = sPropertyIn;
				sExpressionSolved = sExpressionSolvedIn;
				sTagSolved = sTagSolvedIn;
				
				//... mit Berechnung PATH
				btemp = objFileIniTest.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION, true); 
				assertTrue("Flag nicht vorhanden '" + IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
			
				btemp = objFileIniTest.setFlag(IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER,true); //ohne aktivierten Parser keine SUBSTITUTION  
				assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER + "'", btemp);
								
				btemp = objFileIniTest.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH,true);  
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
				
				btemp = objFileIniTest.setFlag(IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE,true);  
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE + "'", btemp);
				
				btemp = objFileIniTest.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER, false); //Ansonsten wird der Wert sofort ausgerechnet
				assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
										
				btemp = objFileIniTest.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA,true);                     //sollte wg. USEEXPRESSION_SOLVER = false egal sein.
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA + "'", btemp);
				
				btemp = objFileIniTest.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH,true); //ohne Mathematische Berechnung, sollte daher egal sein
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH + "'", btemp);
									
				objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
				sValue = objFileIniTest.getPropertyValue(sSection, sProperty).getValue();
				assertEquals(sExpressionSolved, sValue);
				
				
				bReturn = true;
			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			}
		}//end main:
		return bReturn;
	}

	
	/** void, Test: Reading an entry in a section of the ini-file
	* Lindhauer; 22.04.2006 12:54:32
	 */
	public void testCompute_JsonMap(){

		try {	
			boolean btemp; String stemp;
			
			String sTagStartZ = "<Z>";
			String sTagEndZ = "</Z>";						
			String sExpressionSource; String sExpressionSolved; HashMap hmExpressionSolved;
					
			
			//+++++++ VORGEZOGENER LETZTER FEHLERTEST START
								
			
			
			//+++++++ VORGEZOGENER LETZTER FEHLERTEST ENDE
			
			//################################################################
			//### Varianten JSON nicht aufzuloesen
			//#################################################################
			//a)
			sExpressionSource = KernelJsonMapIniSolverZZZTest.sEXPRESSION_JSONMAP01_DEFAULT;
			sExpressionSolved = sExpressionSource;			
			btemp = testCompute_JsonMap_JsonUnsolved_(sExpressionSource, sExpressionSolved, false, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
			
			//b)
			sExpressionSource = KernelJsonMapIniSolverZZZTest.sEXPRESSION_JSONMAP01_DEFAULT;
			sExpressionSolved = sExpressionSource;
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
			btemp = testCompute_JsonMap_JsonUnsolved_(sExpressionSource, sExpressionSolved, true, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
			
			//c)
			sExpressionSource = KernelJsonMapIniSolverZZZTest.sEXPRESSION_JSONMAP01_DEFAULT;
			sExpressionSolved = sExpressionSource;
			btemp = testCompute_JsonMap_JsonUnsolved_(sExpressionSource, sExpressionSolved, false, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
			
			//d)
			sExpressionSource = KernelJsonMapIniSolverZZZTest.sEXPRESSION_JSONMAP01_DEFAULT;
			sExpressionSolved = sExpressionSource;
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
			btemp = testCompute_JsonMap_JsonUnsolved_(sExpressionSource, sExpressionSolved, true, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
			
			
			//################################################################
			//### Varianten JSON-HashMap nicht aufzuloesen
			//#################################################################
			
			//a)
			sExpressionSource = KernelJsonMapIniSolverZZZTest.sEXPRESSION_JSONMAP01_DEFAULT;
			sExpressionSolved = sExpressionSource;			
			btemp = testCompute_JsonMap_Unsolved_(sExpressionSource, sExpressionSolved, false, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
			
			//b)
			sExpressionSource = KernelJsonMapIniSolverZZZTest.sEXPRESSION_JSONMAP01_DEFAULT;
			sExpressionSolved = sExpressionSource;
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
			btemp = testCompute_JsonMap_Unsolved_(sExpressionSource, sExpressionSolved, true, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
			
			//c)
			sExpressionSource = KernelJsonMapIniSolverZZZTest.sEXPRESSION_JSONMAP01_DEFAULT;
			sExpressionSolved = sExpressionSource;
			btemp = testCompute_JsonMap_Unsolved_(sExpressionSource, sExpressionSolved, false, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);

			//d)
			sExpressionSource = KernelJsonMapIniSolverZZZTest.sEXPRESSION_JSONMAP01_DEFAULT;
			sExpressionSolved = sExpressionSource;
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
			btemp = testCompute_JsonMap_Unsolved_(sExpressionSource, sExpressionSolved, true, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
			
			//################################################################
			//### Varianten JSON-HashMap aufzuloesen
			//#################################################################
			
			//a)
			sExpressionSource = KernelJsonMapIniSolverZZZTest.sEXPRESSION_JSONMAP01_DEFAULT;
			hmExpressionSolved = new HashMap();
			sExpressionSolved = sExpressionSource;		
			btemp = testCompute_JsonMap_(sExpressionSource, sExpressionSolved, hmExpressionSolved, false, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
			
			//b)
			sExpressionSource = KernelJsonMapIniSolverZZZTest.sEXPRESSION_JSONMAP01_DEFAULT;
			hmExpressionSolved = new HashMap();
			sExpressionSolved = sExpressionSource;
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
			btemp = testCompute_JsonMap_(sExpressionSource, sExpressionSolved, hmExpressionSolved, true, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
			
			//c)
			sExpressionSource = KernelJsonMapIniSolverZZZTest.sEXPRESSION_JSONMAP01_DEFAULT;
			hmExpressionSolved = new HashMap();
			sExpressionSolved = "<Z>{UIText01=TESTWERT2DO2JSON01, UIText02=TESTWERT2DO2JSON02}</Z>";
			btemp = testCompute_JsonMap_(sExpressionSource, sExpressionSolved, hmExpressionSolved, false, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
			
			//d)
			sExpressionSource = KernelJsonMapIniSolverZZZTest.sEXPRESSION_JSONMAP01_DEFAULT;
			hmExpressionSolved = new HashMap();
			sExpressionSolved = "<Z>{UIText01=TESTWERT2DO2JSON01, UIText02=TESTWERT2DO2JSON02}</Z>";
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
			btemp = testCompute_JsonMap_(sExpressionSource, sExpressionSolved, hmExpressionSolved, true, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
			
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}		
	}
	
	private boolean testCompute_JsonMap_JsonUnsolved_(String sExpressionSourceIn, String sExpressionSolvedIn, boolean bRemoveSurroundingSeparatorsOnSolve, IEnumSetMappedTestCaseZZZ objEnumTestCase) {
		boolean bReturn = false;
		
		main:{
			try {
				boolean btemp; String stemp;
				
				String sExpressionSource; HashMap hmExpressionSolved; String sExpressionSolved; String sValue;				
				ReferenceZZZ<IKernelConfigSectionEntryZZZ> objSectionEntryReference;
				
				btemp = objExpressionHandler.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION, true); 
				assertTrue("Flag nicht vorhanden '" + IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
				
				btemp = objExpressionHandler.setFlag(IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER,true); //ohne aktivierten Parser keine SUBSTITUTION  
				assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER + "'", btemp);
				
				btemp = objExpressionHandler.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH,true);  
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
				
				btemp = objExpressionHandler.setFlag(IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE,true);  
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE + "'", btemp);
								
				btemp = objExpressionHandler.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER, true); 
				assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
				
				//... ohne Formelberechnung
				btemp = objExpressionHandler.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA,false);
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA + "'", btemp);
			
				//... ohne Berechnung MATH
				btemp = objExpressionHandler.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH, false); 
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH + "'", btemp);
				
				//... ohne Berechnung JSON
				btemp = objExpressionHandler.setFlag(IKernelJsonIniSolverZZZ.FLAGZ.USEJSON, false); //Ansonsten wird der Wert sofort ausgerechnet
				assertTrue("Das Flag '" + IKernelJsonIniSolverZZZ.FLAGZ.USEJSON +"' sollte zur Verfügung stehen.", btemp);
								
				btemp = objExpressionHandler.setFlag(IKernelJsonMapIniSolverZZZ.FLAGZ.USEJSON_MAP, true); //sollte egal sein
				assertTrue("Das Flag '" + IKernelJsonMapIniSolverZZZ.FLAGZ.USEJSON_MAP +"' sollte zur Verfügung stehen.", btemp);
								
				//+++ ... solve verhält sich NICHT wie parse(), bei solve wird aufgeloest...
				if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.PARSE)) {
					sExpressionSource = sExpressionSourceIn;					
					sExpressionSolved = sExpressionSolvedIn;
					objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
					sValue = objExpressionHandler.solve(sExpressionSource, objSectionEntryReference, bRemoveSurroundingSeparatorsOnSolve);
					assertEquals(sExpressionSolved, sValue);
				}
				//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

				//+++ ... parse ist nicht solve... also wird hier nichts aufgeloest, aussser die Pfade
				if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.SOLVE)) {
					sExpressionSource = sExpressionSourceIn;							
					sExpressionSolved = sExpressionSolvedIn;
					objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
					sValue = objExpressionHandler.parse(sExpressionSource, objSectionEntryReference, bRemoveSurroundingSeparatorsOnSolve);
					assertEquals(sExpressionSolved, sValue);
				}
				//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
					
				
				bReturn = true;
			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			}
		}//end main:
		return bReturn;
	}
	
	
	private boolean testCompute_JsonMap_Unsolved_(String sExpressionSourceIn, String sExpressionSolvedIn, boolean bRemoveSurroundingSeparatorsOnSolve, IEnumSetMappedTestCaseZZZ objEnumTestCase) {
		boolean bReturn = false;
		
		main:{
			try {
				boolean btemp; String stemp;
				
				String sExpressionSource; HashMap hmExpressionSolved; String sExpressionSolved; String sValue;				
				ReferenceZZZ<IKernelConfigSectionEntryZZZ> objSectionEntryReference;
				
				btemp = objExpressionHandler.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION, true); 
				assertTrue("Flag nicht vorhanden '" + IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
				
				btemp = objExpressionHandler.setFlag(IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER,true); //ohne aktivierten Parser keine SUBSTITUTION  
				assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER + "'", btemp);
								
				btemp = objExpressionHandler.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH,true);  
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
				
				btemp = objExpressionHandler.setFlag(IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE,true);  
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE + "'", btemp);
								
				btemp = objExpressionHandler.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER, true); 
				assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
				
				//... ohne Formelberechnung
				btemp = objExpressionHandler.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA,false);
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA + "'", btemp);
			
				//... ohne Berechnung MATH
				btemp = objExpressionHandler.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH, false); 
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH + "'", btemp);
				
				//... mit Berechnung JSON
				btemp = objExpressionHandler.setFlag(IKernelJsonIniSolverZZZ.FLAGZ.USEJSON, true); 
				assertTrue("Das Flag '" + IKernelJsonIniSolverZZZ.FLAGZ.USEJSON +"' sollte zur Verfügung stehen.", btemp);
								
				//... ohne Berechnung JSON-MAP
				btemp = objExpressionHandler.setFlag(IKernelJsonMapIniSolverZZZ.FLAGZ.USEJSON_MAP, false);//Ansonsten wird der Wert sofort ausgerechnet 
				assertTrue("Das Flag '" + IKernelJsonMapIniSolverZZZ.FLAGZ.USEJSON_MAP +"' sollte zur Verfügung stehen.", btemp);
								
				//+++ ... solve verhält sich NICHT wie parse(), bei solve wird aufgeloest...
				if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.PARSE)) {
					sExpressionSource = sExpressionSourceIn;					
					sExpressionSolved = sExpressionSolvedIn;
					objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
					sValue = objExpressionHandler.solve(sExpressionSource, objSectionEntryReference, bRemoveSurroundingSeparatorsOnSolve);
					assertEquals(sExpressionSolved, sValue);
				}
				//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

				//+++ ... parse ist nicht solve... also wird hier nichts aufgeloest, aussser die Pfade
				if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.SOLVE)) {
					sExpressionSource = sExpressionSourceIn;							
					sExpressionSolved = sExpressionSolvedIn;
					objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
					sValue = objExpressionHandler.parse(sExpressionSource, objSectionEntryReference, bRemoveSurroundingSeparatorsOnSolve);
					assertEquals(sExpressionSolved, sValue);
				}
				//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
					
				
				bReturn = true;
			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			}
		}//end main:
		return bReturn;
	}
	
	private boolean testCompute_JsonMap_(String sExpressionSourceIn, String sExpressionSolvedIn, HashMap hmExpressionSolvedIn, boolean bRemoveSurroundingSeparatorsOnSolve, IEnumSetMappedTestCaseZZZ objEnumTestCase) {
		boolean bReturn = false;
		
		main:{
			try {
				boolean btemp; String stemp;
				
				String sExpressionSource; String sExpressionSolved; HashMap hmExpressionSolved; String sValue;				
				ReferenceZZZ<IKernelConfigSectionEntryZZZ> objSectionEntryReference;
				
				//... mit Berechnung PATH
				btemp = objExpressionHandler.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION, true); 
				assertTrue("Flag nicht vorhanden '" + IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
				
				btemp = objExpressionHandler.setFlag(IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER,true); //ohne aktivierten Parser keine SUBSTITUTION  
				assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER + "'", btemp);
				
				btemp = objExpressionHandler.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH,true);  
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
				
				btemp = objExpressionHandler.setFlag(IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE,true);  
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE + "'", btemp);
								
				btemp = objExpressionHandler.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER, true); 
				assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
				
				btemp = objExpressionHandler.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA,true);
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA + "'", btemp);
			
				//... mit Berechnung MATH
				btemp = objExpressionHandler.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH, true); 
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH + "'", btemp);
					
				//... mit Berechnung JSON
				btemp = objExpressionHandler.setFlag(IKernelJsonIniSolverZZZ.FLAGZ.USEJSON, true); 
				assertTrue("Das Flag '" + IKernelJsonIniSolverZZZ.FLAGZ.USEJSON +"' sollte zur Verfügung stehen.", btemp);
								
				//... mit Berechnung JSON-MAP
				btemp = objExpressionHandler.setFlag(IKernelJsonMapIniSolverZZZ.FLAGZ.USEJSON_MAP, true);//Ansonsten wird der Wert sofort ausgerechnet 
				assertTrue("Das Flag '" + IKernelJsonMapIniSolverZZZ.FLAGZ.USEJSON_MAP +"' sollte zur Verfügung stehen.", btemp);
				
				
				//+++ ... parse ist nicht solve... also wird hier nichts aufgeloest, aussser die Pfade
				if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.PARSE)) {
					sExpressionSource = sExpressionSourceIn;
					hmExpressionSolved = hmExpressionSolvedIn;		
					sExpressionSolved = sExpressionSolvedIn; //hmExpressionSolved.toString();
					objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
					sValue = objExpressionHandler.parse(sExpressionSource, objSectionEntryReference, bRemoveSurroundingSeparatorsOnSolve);
					assertEquals(sExpressionSolved, sValue);
				}
				//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
					
				//+++ ... solve verhält sich NICHT wie parse(), bei solve wird aufgeloest...
				if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.SOLVE)) {
					sExpressionSource = sExpressionSourceIn;
					hmExpressionSolved = hmExpressionSolvedIn;
					sExpressionSolved = sExpressionSolvedIn; //hmExpressionSolved.toString();
					objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
					sValue = objExpressionHandler.solve(sExpressionSource, objSectionEntryReference, bRemoveSurroundingSeparatorsOnSolve);
					assertEquals(sExpressionSolved, sValue);
				}
				//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

				bReturn = true;
			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			}
		}//end main:
		return bReturn;
	}
	
	
	/**void, Test: Reading an entry in a section of the ini-file
	 * 
	 * @author Fritz Lindhauer, 05.05.2023, 08:54:30
	 */
	public void testCompute_CallJava(){
		
		boolean btemp; int itemp;
		
		String sSection; String sProperty;
		String sExpressionSource; String sExpressionSubstituted; String sExpressionSolved; 
		IKernelConfigSectionEntryZZZ objEntry; ReferenceZZZ<IKernelConfigSectionEntryZZZ>objSectionEntryReference;
	
		String sValue;
		String sTagStartZ = "<Z>";
		String sTagEndZ = "</Z>";	
		
		try {		
			
			String sHostName = EnvironmentZZZ.getHostName();
			assertNotNull(sHostName);
			
			//+ Voraussetzungen fuer Aufruf in .ini pruefen
			String sClassName = null;
			String sMethodName = null;
			
			String sSectionParameter = "ArgumentSection for testCallComputed";
			String sProperty_className = "JavaClass";
			String sExpressionSolved_className = "basic.zBasic.util.machine.EnvironmentZZZ";
			objEntry = objFileIniTest.getPropertyValueDirectLookup(sSectionParameter, sProperty_className);
			sValue = objEntry.getValue();
			assertEquals(sExpressionSolved_className, sValue);
			sClassName = sValue;
			
			String sProperty_methodName = "JavaMethod";
			String sExpressionSolved_methodName = "getHostName";
			objEntry = objFileIniTest.getPropertyValueDirectLookup(sSectionParameter, sProperty_methodName);
			sValue = objEntry.getValue();
			assertEquals(sExpressionSolved_methodName, sValue);
			sMethodName = sValue;
			
			
			//+ Wichtige Hilfsmethode pruefen, wichtig ist false am Ende, damit wird von aussen nach innen das Tag entfernt.
			sExpressionSource = "<Z><Z:Call><Z:Java><Z:Class><Z>xyz</Z></Z:Class><Z:Method><Z>abcde</Z></Z:Method></Z:Java></Z:Call></Z>"; //INI-Pfade werden trotzdem ersetzt
			sExpressionSolved = "<Z:Call><Z:Java><Z:Class><Z>xyz</Z></Z:Class><Z:Method><Z>abcde</Z></Z:Method></Z:Java></Z:Call>"; //INI-Pfade werden trotzdem ersetzt //INI-Pfade werden trotzdem ersetzt
			sValue = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSource, sTagStartZ, sTagEndZ, false);
			assertEquals(sExpressionSolved, sValue);
			
		
		
			//+++++++ VORGEZOGENER LETZTER FEHLERTEST START
			
			
			
			
			
			//+++++++ VORGEZOGENER LETZTER FEHLERTEST ENDE
			
			//###########################
		    //### objExpression
			//#########################
			
				
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//+++ A) Ohne jegliche Expression-Berechnung
			//a)
			sExpressionSource = KernelJavaCallIniSolverZZZTest.sEXPRESSION_CALL01_DEFAULT;
			sExpressionSolved = sExpressionSource;
			btemp = testCompute_CallJava_unexpressed_(sExpressionSource, sExpressionSolved, false, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
			
			//b)
			sExpressionSource = KernelJavaCallIniSolverZZZTest.sEXPRESSION_CALL01_DEFAULT;
			sExpressionSolved = sExpressionSource;			
			btemp = testCompute_CallJava_unexpressed_(sExpressionSource, sExpressionSolved, true, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
			
			//c)
			sExpressionSource = KernelJavaCallIniSolverZZZTest.sEXPRESSION_CALL01_DEFAULT;
			sExpressionSolved = sExpressionSource; 			
			btemp = testCompute_CallJava_unexpressed_(sExpressionSource, sExpressionSolved, false, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
				
			//d)
			sExpressionSource = KernelJavaCallIniSolverZZZTest.sEXPRESSION_CALL01_DEFAULT;
			sExpressionSolved = sExpressionSource;
			btemp = testCompute_CallJava_unexpressed_(sExpressionSource, sExpressionSolved, true, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//+++ B) Ohne Solver-Berechung
			//a)
			sExpressionSource = KernelJavaCallIniSolverZZZTest.sEXPRESSION_CALL01_DEFAULT;
			sExpressionSolved = "<Z><Z:Call><Z:Java><Z:Class>" + sClassName + "</Z:Class><Z:Method>" + sMethodName +"</Z:Method></Z:Java></Z:Call></Z>"; //INI-Pfade werden trotzdem ersetzt
			btemp = testCompute_CallJava_Solver_unsolved_(sExpressionSource, sExpressionSolved, false, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
			
			//b) Merke: Ohne Solver-Berechnung bleibt der Z-Tag immer drin.
			sExpressionSource = KernelJavaCallIniSolverZZZTest.sEXPRESSION_CALL01_DEFAULT;
			sExpressionSubstituted = KernelJavaCallIniSolverZZZTest.sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT;
			sExpressionSolved = sExpressionSubstituted;
			//sExpressionSolved = "<Z><Z:Call><Z:Java><Z:Class>" + sClassName + "</Z:Class><Z:Method>" + sMethodName +"</Z:Method></Z:Java></Z:Call></Z>"; //INI-Pfade werden trotzdem ersetzt
			//sExpressionSolvedTagless = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ, false);
			btemp = testCompute_CallJava_Solver_unsolved_(sExpressionSource, sExpressionSolved, true, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
				
			//c) Merke: Ohne Solver-Berechnung bleibt der Z-Tag immer drin. 
			sExpressionSource = KernelJavaCallIniSolverZZZTest.sEXPRESSION_CALL01_DEFAULT;
			sExpressionSubstituted = KernelJavaCallIniSolverZZZTest.sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT;
			//sExpressionSolved = "<Z><Z:Call><Z:Java><Z:Class>" + sClassName + "</Z:Class><Z:Method>" + sMethodName +"</Z:Method></Z:Java></Z:Call></Z>"; //INI-Pfade werden trotzdem ersetzt
			sExpressionSolved = sExpressionSubstituted;
			btemp = testCompute_CallJava_Solver_unsolved_(sExpressionSource, sExpressionSolved, false, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
				
			//d)
			sExpressionSource = KernelJavaCallIniSolverZZZTest.sEXPRESSION_CALL01_DEFAULT;
			sExpressionSubstituted = KernelJavaCallIniSolverZZZTest.sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT;
			sExpressionSolved = sExpressionSubstituted;
			//sExpressionSolved = "<Z><Z:Call><Z:Java><Z:Class>" + sClassName + "</Z:Class><Z:Method>" + sMethodName +"</Z:Method></Z:Java></Z:Call></Z>"; //INI-Pfade werden trotzdem ersetzt
			//sExpressionSolvedTagless = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ, false);
			btemp = testCompute_CallJava_Solver_unsolved_(sExpressionSource, sExpressionSolved, true, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
			
			//+++ C) Ohne jegliche CallJava-Berechnung, aber der generelle Solver ist aktiviert
			//a)
			sExpressionSource = KernelJavaCallIniSolverZZZTest.sEXPRESSION_CALL01_DEFAULT;
			sExpressionSubstituted = KernelJavaCallIniSolverZZZTest.sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT;
			sExpressionSolved = sExpressionSubstituted;			
			btemp = testCompute_CallJava_CallJava_unsolved_(sExpressionSource, sExpressionSolved, false, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
			
			//b)
			sExpressionSource = KernelJavaCallIniSolverZZZTest.sEXPRESSION_CALL01_DEFAULT;
			sExpressionSubstituted = KernelJavaCallIniSolverZZZTest.sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT;
			sExpressionSolved = sExpressionSubstituted;				
			btemp = testCompute_CallJava_CallJava_unsolved_(sExpressionSource, sExpressionSolved, true, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
			
			//c) Merke: Es wird aber trotzdem eine CALL-Berechnung gemacht, darum ist der Tag dann im Enderegebnis raus.
			sExpressionSource = KernelJavaCallIniSolverZZZTest.sEXPRESSION_CALL01_DEFAULT;
			sExpressionSubstituted = KernelJavaCallIniSolverZZZTest.sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT;
			sExpressionSolved = sExpressionSubstituted;
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, KernelCallIniSolverZZZ.sTAG_NAME);
			btemp = testCompute_CallJava_CallJava_unsolved_(sExpressionSource, sExpressionSolved, false, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);

			//d) Merke: Es wird aber trotzdem eine CALL-Berechnung gemacht, darum ist der Tag dann im Enderegebnis raus.
			sExpressionSource = KernelJavaCallIniSolverZZZTest.sEXPRESSION_CALL01_DEFAULT;
			sExpressionSubstituted = KernelJavaCallIniSolverZZZTest.sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT;
			sExpressionSolved = sExpressionSubstituted;
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, KernelCallIniSolverZZZ.sTAG_NAME);
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ, false);	
			btemp = testCompute_CallJava_CallJava_unsolved_(sExpressionSource, sExpressionSolved, true, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
			
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//+++ E) Mit CALL-Berechnung
			//a)
			sExpressionSource = KernelJavaCallIniSolverZZZTest.sEXPRESSION_CALL01_DEFAULT;
			sExpressionSubstituted = KernelJavaCallIniSolverZZZTest.sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT;
			sExpressionSolved = sExpressionSubstituted; // "<Z><Z:Call><Z:Java><Z:Class><Z>basic.zBasic.util.machine.EnvironmentZZZ</Z></Z:Class><Z:Method><Z>getHostName</Z></Z:Method></Z:Java></Z:Call></Z>";						
			btemp = testCompute_CallJava_(sExpressionSource, sExpressionSubstituted, false, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
				
			//b)
			sExpressionSource = KernelJavaCallIniSolverZZZTest.sEXPRESSION_CALL01_DEFAULT;
			sExpressionSubstituted = KernelJavaCallIniSolverZZZTest.sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT;
			sExpressionSolved = sExpressionSubstituted; //sExpressionSolved = "<Z:Call><Z:Java><Z:Class>basic.zBasic.util.machine.EnvironmentZZZ</Z:Class><Z:Method>getHostName</Z:Method></Z:Java></Z:Call>";
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
			btemp = testCompute_CallJava_(sExpressionSource, sExpressionSubstituted, true, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
			
			//c)
			sExpressionSource = KernelJavaCallIniSolverZZZTest.sEXPRESSION_CALL01_DEFAULT;
			sExpressionSolved = "<Z>" + sHostName + "</Z>";			
			btemp = testCompute_CallJava_(sExpressionSource, sExpressionSolved, false, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
			
			//d)		
			sExpressionSource = KernelJavaCallIniSolverZZZTest.sEXPRESSION_CALL01_DEFAULT;
			sExpressionSolved = sHostName;
			btemp = testCompute_CallJava_(sExpressionSource, sExpressionSolved, true, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
			
			
			//#####################################
			//### INI FILE
			//### Merke: Auf dieser obersten Ebene kann man NICHT steuern "mit oder ohne umgebende Tags".
			//#####################################
						
			//a) ohne EXPRESSION - Berechnung
			sSection = "Section for testCall";
			sProperty = "WertCalled";
			sExpressionSolved = KernelJavaCallIniSolverZZZTest.sEXPRESSION_CALL01_DEFAULT; // "<Z><Z:Call><Z:Java><Z:Class>{[ArgumentSection for testCallComputed]JavaClass}</Z:Class><Z:Method>{[ArgumentSection for testCallComputed]JavaMethod}</Z:Method></Z:Java></Z:Call></Z>";
			btemp = testCompute_CallJava_IniUsed_unexpressed_(sSection, sProperty, sExpressionSolved);
			
				
			//b ) ohne SOLVER - Berechnung
			sSection = "Section for testCall";
			sProperty = "WertCalled";
			sExpressionSolved = KernelJavaCallIniSolverZZZTest.sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT;    //"<Z:Call><Z:Java><Z:Class>basic.zBasic.util.machine.EnvironmentZZZ</Z:Class><Z:Method>getHostName</Z:Method></Z:Java></Z:Call>";
			btemp = testCompute_CallJava_IniUsed_unsolved_(sSection, sProperty, sExpressionSolved);	
	
			//c) mit CALL-Berechnung
			sSection = "Section for testCall";
			sProperty = "WertCalled";
			sExpressionSolved=EnvironmentZZZ.getHostName();
			btemp = testCompute_CallJava_IniUsed_(sSection, sProperty, sExpressionSolved);
							
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}		
	}
	
	
	private boolean testCompute_CallJava_unexpressed_(String sExpressionSourceIn, String sExpressionSolvedIn, boolean bRemoveSurroundingSeparatorsOnSolve, IEnumSetMappedTestCaseZZZ objEnumTestCase) {
		boolean bReturn = false;
		try {
			boolean btemp; 
			
			String sExpressionSource; 
			String sExpressionSolved; String sValue;				
			ReferenceZZZ<IKernelConfigSectionEntryZZZ> objSectionEntryReference;
			IKernelConfigSectionEntryZZZ objEntry;
			
			
			//####################################################################################
			//### EXPRESSION - NICHT EXPRESSION BEHANDLUNG .solve
			//####################################################################################
			
			//Anwenden der ersten Formel, ohne Berechnung oder Formelersetzung						
			sExpressionSource = sExpressionSourceIn;
			sExpressionSolved = sExpressionSolvedIn;
			
			btemp = objExpressionHandler.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION, false); 
			assertTrue("Flag nicht vorhanden '" + IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);

			btemp = objExpressionHandler.setFlag(IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER,true); //ohne aktivierten Parser keine SUBSTITUTION  
			assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER + "'", btemp);
			
			btemp = objExpressionHandler.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH, true);			
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
		
			btemp = objExpressionHandler.setFlag(IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE, true);			
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE + "'", btemp);
						
			btemp = objExpressionHandler.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER, true); //soll dann egal sein
			assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
			
			btemp = objExpressionHandler.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA, true); //soll dann egal sein
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA + "'", btemp);
			
			btemp = objExpressionHandler.setFlag(IKernelCallIniSolverZZZ.FLAGZ.USECALL, true); //soll dann egal sein
			assertTrue("Flag nicht vorhanden '" + IKernelCallIniSolverZZZ.FLAGZ.USECALL + "'", btemp);
			
			btemp = objExpressionHandler.setFlag(IKernelJavaCallIniSolverZZZ.FLAGZ.USECALL_JAVA, true);
			assertTrue("Flag nicht vorhanden '" + IKernelJavaCallIniSolverZZZ.FLAGZ.USECALL_JAVA + "'", btemp);
			
			
			//+++ ... parse ist nicht solve... also wird hier nichts aufgeloest, aussser die Pfade
			if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.PARSE)){
				sExpressionSource = sExpressionSourceIn;
				sExpressionSolved = sExpressionSolvedIn;		
				objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
				sValue = objExpressionHandler.parse(sExpressionSource, objSectionEntryReference, bRemoveSurroundingSeparatorsOnSolve);
				assertEquals(sExpressionSolved, sValue);
				
				objEntry = objSectionEntryReference.get();
				assertNotNull(objEntry);
				assertFalse(objEntry.isCall()); 		//Beim Solven erst wuerde der CallIniSolver erst aufgerufen, mit seinem parse(). Wert kommt aus parse()
				assertFalse(objEntry.isJavaCall());	    //Beim Solven erst wuerde der JavaCallIniSolver erst aufgerufen, mit seinem parse(). Wert kommt aus parse()
				assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingClassname() + "'", objEntry.getCallingClassname());
				assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingMethodname() + "'", objEntry.getCallingMethodname());
			}
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
				
			//+++ ... solve verhält sich NICHT wie parse(), bei solve wird aufgeloest...
			if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.SOLVE)){
				sExpressionSource = sExpressionSourceIn;
				sExpressionSolved = sExpressionSolvedIn;
				objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
				sValue = objExpressionHandler.solve(sExpressionSource, objSectionEntryReference, bRemoveSurroundingSeparatorsOnSolve);
				assertEquals(sExpressionSolved, sValue);
				
				objEntry = objSectionEntryReference.get();
				assertNotNull(objEntry);
				assertFalse(objEntry.isCall()); 		//Beim Solven erst wuerde der CallIniSolver erst aufgerufen, mit seinem parse(). Wert kommt aus parse()
				assertFalse(objEntry.isJavaCall());	    //Beim Solven erst wuerde der JavaCallIniSolver erst aufgerufen, mit seinem parse(). Wert kommt aus parse()
				assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingClassname() + "'", objEntry.getCallingClassname());
				assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingMethodname() + "'", objEntry.getCallingMethodname());
			}
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			
			
			
			bReturn = true;
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}
		return bReturn;
	}

	
	private boolean testCompute_CallJava_CallJava_unsolved_(String sExpressionSourceIn, String sExpressionSolvedIn, boolean bRemoveSurroundingSeparatorsOnSolve, IEnumSetMappedTestCaseZZZ objEnumTestCase) {
		boolean bReturn = false;
		try {
			boolean btemp; 
			
			String sExpressionSource; 
			String sExpressionSolved; String sValue;				
			ReferenceZZZ<IKernelConfigSectionEntryZZZ> objSectionEntryReference;
			IKernelConfigSectionEntryZZZ objEntry;
			
			
			//####################################################################################
			//### EXPRESSION - NICHT EXPRESSION BEHANDLUNG .solve
			//####################################################################################
			
			//Anwenden der ersten Formel, ohne Berechnung oder Formelersetzung						
			sExpressionSource = sExpressionSourceIn;
			sExpressionSolved = sExpressionSolvedIn;
			
			btemp = objExpressionHandler.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION, true); 
			assertTrue("Flag nicht vorhanden '" + IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);

			btemp = objExpressionHandler.setFlag(IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER,true); //ohne aktivierten Parser keine SUBSTITUTION  
			assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER + "'", btemp);
			
			btemp = objExpressionHandler.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH, true);			
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
		
			btemp = objExpressionHandler.setFlag(IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE, true);			
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE + "'", btemp);
						
			btemp = objExpressionHandler.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER, true); //soll dann egal sein
			assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
			
			btemp = objExpressionHandler.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA, true); //soll dann egal sein
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA + "'", btemp);
			
			btemp = objExpressionHandler.setFlag(IKernelCallIniSolverZZZ.FLAGZ.USECALL, true); //soll dann egal sein
			assertTrue("Flag nicht vorhanden '" + IKernelCallIniSolverZZZ.FLAGZ.USECALL + "'", btemp);
			
			btemp = objExpressionHandler.setFlag(IKernelJavaCallIniSolverZZZ.FLAGZ.USECALL_JAVA, false);
			assertTrue("Flag nicht vorhanden '" + IKernelJavaCallIniSolverZZZ.FLAGZ.USECALL_JAVA + "'", btemp);
			
			
			//+++ ... parse ist nicht solve... also wird hier nichts aufgeloest, aussser die Pfade
			if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.PARSE)){
				sExpressionSource = sExpressionSourceIn;
				sExpressionSolved = sExpressionSolvedIn;		
				objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
				sValue = objExpressionHandler.parse(sExpressionSource, objSectionEntryReference, bRemoveSurroundingSeparatorsOnSolve);
				assertEquals(sExpressionSolved, sValue);
				
				objEntry = objSectionEntryReference.get();
				assertNotNull(objEntry);
				assertFalse(objEntry.isCall()); 		//Beim Solven erst wuerde der CallIniSolver erst aufgerufen, mit seinem parse(). Wert kommt aus parse()
				assertFalse(objEntry.isJavaCall());	    //Beim Solven erst wuerde der JavaCallIniSolver erst aufgerufen, mit seinem parse(). Wert kommt aus parse()
				assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingClassname() + "'", objEntry.getCallingClassname());
				assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingMethodname() + "'", objEntry.getCallingMethodname());
			}
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
				
			//+++ ... solve verhält sich NICHT wie parse(), bei solve wird aufgeloest...
			if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.SOLVE)){
				sExpressionSource = sExpressionSourceIn;
				sExpressionSolved = sExpressionSolvedIn;
				objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
				sValue = objExpressionHandler.solve(sExpressionSource, objSectionEntryReference, bRemoveSurroundingSeparatorsOnSolve);
				assertEquals(sExpressionSolved, sValue);
				
				objEntry = objSectionEntryReference.get();
				assertNotNull(objEntry);
				assertTrue(objEntry.isCall());		//Wert kommt aus parse()
				assertTrue(objEntry.isJavaCall());	//Wert kommt aus parse()
				assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingClassname() + "'", objEntry.getCallingClassname());
				assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingMethodname() + "'", objEntry.getCallingMethodname());
			}
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			
			
			
			bReturn = true;
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}
		return bReturn;
	}
	
	private boolean testCompute_CallJava_Solver_unsolved_(String sExpressionSourceIn, String sExpressionSolvedIn, boolean bRemoveSurroundingSeparatorsOnSolve, IEnumSetMappedTestCaseZZZ objEnumTestCase) {
		boolean bReturn = false;
		try {
			boolean btemp; 
			
			String sExpressionSource; 
			String sExpressionSolved; String sValue;				
			ReferenceZZZ<IKernelConfigSectionEntryZZZ> objSectionEntryReference;
			IKernelConfigSectionEntryZZZ objEntry;
			
			
			//####################################################################################
			//### EXPRESSION - CALL NICHT .solve
			//####################################################################################
						
			//Anwenden des Ausdrucks ohne Solver - Aufruf
			btemp = objExpressionHandler.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION, true); 
			assertTrue("Flag nicht vorhanden '" + IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
			
			btemp = objExpressionHandler.setFlag(IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER,true); //ohne aktivierten Parser keine SUBSTITUTION  
			assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER + "'", btemp);
			
			btemp = objExpressionHandler.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH, true);			
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
		
			btemp = objExpressionHandler.setFlag(IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE, true);			
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE + "'", btemp);
			
			btemp = objExpressionHandler.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER, false); 
			assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
			
			btemp = objExpressionHandler.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA, true); //soll dann egal sein
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA + "'", btemp);
			
			btemp = objExpressionHandler.setFlag(IKernelCallIniSolverZZZ.FLAGZ.USECALL, true); //soll dann egal sein
			assertTrue("Flag nicht vorhanden '" + IKernelCallIniSolverZZZ.FLAGZ.USECALL + "'", btemp);
			
			btemp = objExpressionHandler.setFlag(IKernelJavaCallIniSolverZZZ.FLAGZ.USECALL_JAVA, true);
			assertTrue("Flag nicht vorhanden '" + IKernelJavaCallIniSolverZZZ.FLAGZ.USECALL_JAVA + "'", btemp);
						
			//+++ ... parse ist nicht solve... also wird hier nichts aufgeloest, aussser die Pfade
			if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.PARSE)) {
				sExpressionSource = sExpressionSourceIn;
				sExpressionSolved = sExpressionSolvedIn;		
				objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
				sValue = objExpressionHandler.parse(sExpressionSource, objSectionEntryReference, bRemoveSurroundingSeparatorsOnSolve);
				assertEquals(sExpressionSolved, sValue);
			}
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
				
			//+++ ... solve verhält sich NICHT wie parse(), bei solve wird aufgeloest...
			if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.SOLVE)) {
				sExpressionSource = sExpressionSourceIn;
				sExpressionSolved = sExpressionSolvedIn;
				objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
				sValue = objExpressionHandler.solve(sExpressionSource, objSectionEntryReference, bRemoveSurroundingSeparatorsOnSolve);
				assertEquals(sExpressionSolved, sValue);
			}
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			
			bReturn = true;
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}
		return bReturn;
	}
	
	private boolean testCompute_CallJava_(String sExpressionSourceIn, String sExpressionSolvedIn, boolean bRemoveSurroundingSeparatorsOnSolve, IEnumSetMappedTestCaseZZZ objEnumTestCase) {
		boolean bReturn = false;
		try {
			boolean btemp; 
			
			String sExpressionSource; 
			String sExpressionSolved; String sValue;				
			ReferenceZZZ<IKernelConfigSectionEntryZZZ> objSectionEntryReference;
			IKernelConfigSectionEntryZZZ objEntry;
			
			
			//####################################################################################
			//### EXPRESSION .solve
			//####################################################################################
			
			//Anwenden der ersten Formel, ohne Berechnung oder Formelersetzung						
			sExpressionSource = sExpressionSourceIn;
			sExpressionSolved = sExpressionSolvedIn;
			
			btemp = objExpressionHandler.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION, true); 
			assertTrue("Flag nicht vorhanden '" + IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);

			btemp = objExpressionHandler.setFlag(IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER,true); //ohne aktivierten Parser keine SUBSTITUTION  
			assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER + "'", btemp);
			
			btemp = objExpressionHandler.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH, true);			
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
		
			btemp = objExpressionHandler.setFlag(IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE, true);			
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE + "'", btemp);
									
			btemp = objExpressionHandler.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER, true); //soll dann egal sein
			assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
			
			btemp = objExpressionHandler.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA, true); //soll dann egal sein
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA + "'", btemp);
			
			btemp = objExpressionHandler.setFlag(IKernelCallIniSolverZZZ.FLAGZ.USECALL, true); //soll dann egal sein
			assertTrue("Flag nicht vorhanden '" + IKernelCallIniSolverZZZ.FLAGZ.USECALL + "'", btemp);
			
			btemp = objExpressionHandler.setFlag(IKernelJavaCallIniSolverZZZ.FLAGZ.USECALL_JAVA, true);
			assertTrue("Flag nicht vorhanden '" + IKernelJavaCallIniSolverZZZ.FLAGZ.USECALL_JAVA + "'", btemp);
			
			
			//+++ ... parse ist nicht solve... also wird hier nichts aufgeloest, aussser die Pfade substituiert.
			if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.PARSE)) {			
				sExpressionSource = sExpressionSourceIn;
				sExpressionSolved = sExpressionSolvedIn;		
				objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
				sValue = objExpressionHandler.parse(sExpressionSource, objSectionEntryReference, bRemoveSurroundingSeparatorsOnSolve);
				assertEquals(sExpressionSolved, sValue);
				
				objEntry = objSectionEntryReference.get();
				assertNotNull(objEntry);
				assertFalse(objEntry.isCall()); 		//Beim Solven erst wuerde der CallIniSolver erst aufgerufen, mit seinem parse(). Wert kommt aus parse()
				assertFalse(objEntry.isJavaCall());	    //Beim Solven erst wuerde der JavaCallIniSolver erst aufgerufen, mit seinem parse(). Wert kommt aus parse()
				assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingClassname() + "'", objEntry.getCallingClassname());
				assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingMethodname() + "'", objEntry.getCallingMethodname());
			}
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
				
			//+++ ... solve verhält sich NICHT wie parse(), bei solve wird aufgeloest...
			//+++ ... parse ist nicht solve... also wird hier nichts aufgeloest, aussser die Pfade substituiert.
			if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.SOLVE)) {
				sExpressionSource = sExpressionSourceIn;
				sExpressionSolved = sExpressionSolvedIn;
				objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
				sValue = objExpressionHandler.solve(sExpressionSource, objSectionEntryReference, bRemoveSurroundingSeparatorsOnSolve);
				assertEquals(sExpressionSolved, sValue);
				
				objEntry = objSectionEntryReference.get();
				assertNotNull(objEntry);
				assertTrue(objEntry.isCall());
				assertTrue(objEntry.isJavaCall());
				assertNotNull(objEntry.getCallingClassname());
				assertNotNull(objEntry.getCallingMethodname());
			}
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		
			bReturn = true;
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}
		return bReturn;
	}
		
	private boolean testCompute_CallJava_IniUsed_unexpressed_(String sSectionIn, String sPropertyIn, String sExpressionSolvedIn) {
		boolean bReturn = false;
		try {
			boolean btemp; String stemp;
			
			String sSection; String sProperty; 
			String sExpressionSolved; String sValue;				
			ReferenceZZZ<IKernelConfigSectionEntryZZZ> objSectionEntryReference;
			IKernelConfigSectionEntryZZZ objEntry;
			
			
			//####################################################################################
			//### EXPRESSION - INI Handler .solve
			//####################################################################################
			
			//Anwenden der ersten Formel, ohne Berechnung oder Formelersetzung						
			sSection = sSectionIn;
			sProperty = sPropertyIn;
			sExpressionSolved = sExpressionSolvedIn;
			
			//Anwenden der ersten Formel, ohne Berechnung oder Formelersetzung
			btemp = objFileIniTest.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION, false); 
			assertTrue("Flag nicht vorhanden '" + IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
			
			btemp = objFileIniTest.setFlag(IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER,true); //ohne aktivierten Parser keine SUBSTITUTION  
			assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER + "'", btemp);
						
			btemp = objFileIniTest.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER, true); //sollte dann egal sein
			assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
			
			btemp = objFileIniTest.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA, true); 
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA + "'", btemp);
			
			btemp = objFileIniTest.setFlag(IKernelCallIniSolverZZZ.FLAGZ.USECALL, true); //soll dann egal sein
			assertTrue("Flag nicht vorhanden '" + IKernelCallIniSolverZZZ.FLAGZ.USECALL + "'", btemp);
			
			btemp = objFileIniTest.setFlag(IKernelJavaCallIniSolverZZZ.FLAGZ.USECALL_JAVA, true);
			assertTrue("Flag nicht vorhanden '" + IKernelJavaCallIniSolverZZZ.FLAGZ.USECALL_JAVA + "'", btemp);
			
			btemp = objFileIniTest.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH, true);			
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
		
			btemp = objFileIniTest.setFlag(IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE, true);			
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE + "'", btemp);
	
			sValue = objFileIniTest.getPropertyValue(sSection, sProperty).getValue();
			assertEquals(sExpressionSolved, sValue);
			
			objEntry = objFileIniTest.getEntry();
			assertNotNull(objEntry);
			assertNotNull(objEntry);
			assertFalse(objEntry.isCall()); 		//Beim Solven erst wuerde der CallIniSolver erst aufgerufen, mit seinem parse(). Wert kommt aus parse()
			assertFalse(objEntry.isJavaCall());	    //Beim Solven erst wuerde der JavaCallIniSolver erst aufgerufen, mit seinem parse(). Wert kommt aus parse()
						
			//###############################################################
						
			bReturn = true;
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}
		return bReturn;
	}
	
	private boolean testCompute_CallJava_IniUsed_unsolved_(String sSectionIn, String sPropertyIn, String sExpressionSolvedIn) {
		boolean bReturn = false;
		try {
			boolean btemp; String stemp;
			
			String sSection; String sProperty; 
			String sExpressionSolved; String sValue;				
			ReferenceZZZ<IKernelConfigSectionEntryZZZ> objSectionEntryReference;
			IKernelConfigSectionEntryZZZ objEntry;
			
			
			//####################################################################################
			//### EXPRESSION - INI Handler .solve
			//####################################################################################
		
			//########################################################################################
			//Anwenden der ersten Formel, ohne Berechnung oder Formelersetzung						
			sSection = sSectionIn;
			sProperty = sPropertyIn;
			sExpressionSolved = sExpressionSolvedIn;
			
			//Anwenden der ersten Formel, Mit Berechnung oder Formelersetzung
			btemp = objFileIniTest.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION, true); 
			assertTrue("Flag nicht vorhanden '" + IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
			
			btemp = objFileIniTest.setFlag(IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER,true); //ohne aktivierten Parser keine SUBSTITUTION  
			assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER + "'", btemp);
			
			btemp = objFileIniTest.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH, true);			
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
		
			btemp = objFileIniTest.setFlag(IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE, true);			
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE + "'", btemp);
			
			btemp = objFileIniTest.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER, false); 
			assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
			
			btemp = objFileIniTest.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA, true); 
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA + "'", btemp);
			
			btemp = objFileIniTest.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH,true); 
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH + "'", btemp);
			
			btemp = objFileIniTest.setFlag(IKernelCallIniSolverZZZ.FLAGZ.USECALL, true); //soll dann egal sein
			assertTrue("Flag nicht vorhanden '" + IKernelCallIniSolverZZZ.FLAGZ.USECALL + "'", btemp);
			
			btemp = objFileIniTest.setFlag(IKernelJavaCallIniSolverZZZ.FLAGZ.USECALL_JAVA, true);
			assertTrue("Flag nicht vorhanden '" + IKernelJavaCallIniSolverZZZ.FLAGZ.USECALL_JAVA + "'", btemp);
			
			sValue = objFileIniTest.getPropertyValue(sSection, sProperty).getValue();
			assertEquals(sExpressionSolved, sValue);
			
			objEntry = objFileIniTest.getEntry();
			assertNotNull(objEntry);
			assertFalse(objEntry.isCall()); 		//Beim Solven erst wuerde der CallIniSolver erst aufgerufen, mit seinem parse(). Wert kommt aus parse()
			assertFalse(objEntry.isJavaCall());	    //Beim Solven erst wuerde der JavaCallIniSolver erst aufgerufen, mit seinem parse(). Wert kommt aus parse()
					
			//###############################################################
						
			bReturn = true;
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}
		return bReturn;
	}

	
	
	private boolean testCompute_CallJava_IniUsed_(String sSectionIn, String sPropertyIn, String sExpressionSolvedIn) {
		boolean bReturn = false;
		try {
			boolean btemp; String stemp;
			
			String sSection; String sProperty; String sExpressionSolved; String sValue;				
			ReferenceZZZ<IKernelConfigSectionEntryZZZ> objSectionEntryReference;
			IKernelConfigSectionEntryZZZ objEntry;
			
			
			//####################################################################################
			//### EXPRESSION - INI Handler
			//####################################################################################
			
			//Anwenden der ersten Formel, MIT BERECHNUNG				
			sSection = sSectionIn;
			sProperty = sPropertyIn;
			sExpressionSolved = sExpressionSolvedIn;
			
			//Anwenden der ersten Formel, Mit Berechnung oder Formelersetzung
			btemp = objFileIniTest.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION, true); 
			assertTrue("Flag nicht vorhanden '" + IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
			
			btemp = objFileIniTest.setFlag(IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER,true); //ohne aktivierten Parser keine SUBSTITUTION  
			assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER + "'", btemp);
						
			btemp = objFileIniTest.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH, true);			
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
		
			btemp = objFileIniTest.setFlag(IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE, true);			
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE + "'", btemp);
			
			btemp = objFileIniTest.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER, true); 
			assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
			
			btemp = objFileIniTest.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA, true); 
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA + "'", btemp);
			
			btemp = objFileIniTest.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH,false); //ohne Mathematische Berechnung, sollte daher egal sein
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH + "'", btemp);
			
			btemp = objFileIniTest.setFlag(IKernelCallIniSolverZZZ.FLAGZ.USECALL, true); //soll dann egal sein
			assertTrue("Flag nicht vorhanden '" + IKernelCallIniSolverZZZ.FLAGZ.USECALL + "'", btemp);
			
			btemp = objFileIniTest.setFlag(IKernelJavaCallIniSolverZZZ.FLAGZ.USECALL_JAVA, true);
			assertTrue("Flag nicht vorhanden '" + IKernelJavaCallIniSolverZZZ.FLAGZ.USECALL_JAVA + "'", btemp);
			
			sValue = objFileIniTest.getPropertyValue(sSection, sProperty).getValue();
			assertEquals(sExpressionSolved, sValue);
			
			IKernelConfigSectionEntryZZZ objSectionEntry = objFileIniTest.getEntry();
			assertNotNull(objSectionEntry);
			assertTrue(objSectionEntry.isCall());
			assertTrue(objSectionEntry.isJavaCall());
					
			bReturn = true;
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}
		return bReturn;
	}
	
	/**void, Test: Reading an entry in a section of the ini-file
	 * 
	 * @author Fritz Lindhauer, 05.05.2023, 08:54:30
	 */
	public void testCompute_CallJavaEntry_Detail(){

//		try {
			boolean btemp; 
			
			String sExpressionSource; 
			String sExpressionSolved; String sValue;				
			ReferenceZZZ<IKernelConfigSectionEntryZZZ> objSectionEntryReference;
			IKernelConfigSectionEntryZZZ objEntry;
		
			String sFormulaSolvedAndConverted; String sFormulaSolvedAndConvertedAsExpression;
			//TODO 20241011: Source und Solved und solvedTagless etc. an die Methoden von aussen uebergeben, so dass die verschiednesten String sgetestet werden koennen.
			
			//+++++++ VORGEZOGENER LETZTER FEHLERTEST START
			
			btemp = testCompute_CallJavaEntry_Detail_SolverUnsolved_();
			
			//+++++++ VORGEZOGENER LETZTER FEHLERTEST ENDE
			
			
			
			btemp = testCompute_CallJavaEntry_Detail_SolverUnsolved_();
			
			btemp = testCompute_CallJavaEntry_Detail_CallUnsolved_();
			
			btemp = testCompute_CallJavaEntry_Detail_();
		
//		} catch (ExceptionZZZ ez) {
//			fail("Method throws an exception." + ez.getMessageLast());
//		}		
	}
	
	private boolean testCompute_CallJavaEntry_Detail_SolverUnsolved_() {
		boolean bReturn = false;
		
		try {
			boolean btemp; 
			
			String sExpressionSource; 
			String sExpressionSolved; String sValue;				
			ReferenceZZZ<IKernelConfigSectionEntryZZZ> objSectionEntryReference;
			IKernelConfigSectionEntryZZZ objEntry;
		
			String sFormulaSolvedAndConverted; String sFormulaSolvedAndConvertedAsExpression;
			
			
			
			//###########################################################
			//Anwenden der ersten Formel, ohne Berechnung
			btemp = objFileIniTest.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION, true); 
			assertTrue("Flag nicht vorhanden '" + IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);

			btemp = objExpressionHandler.setFlag(IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER,true); //ohne aktivierten Parser keine SUBSTITUTION  
			assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER + "'", btemp);
			
			btemp = objFileIniTest.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH, true);			
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
		
			btemp = objFileIniTest.setFlag(IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE, true);			
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE + "'", btemp);
			
			btemp = objFileIniTest.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER, false);
			assertTrue("Das Flag 'useexpression' sollte zur Verfügung stehen.", btemp);
				
			btemp = objFileIniTest.setFlag(IKernelCallIniSolverZZZ.FLAGZ.USECALL.name(), true); //Ansonsten wird der Wert sofort ausgerechnet
			assertTrue("Das Flag 'usecall' sollte zur Verfügung stehen.", btemp);
		
			
			sValue = objFileIniTest.getPropertyValue("Section for testCall", "WertCalled").getValue();
			sExpressionSolved = "<Z:Call><Z:Java><Z:Class>basic.zBasic.util.machine.EnvironmentZZZ</Z:Class><Z:Method>getHostName</Z:Method></Z:Java></Z:Call>";
			assertEquals(sExpressionSolved, sValue);			
			
			//Wert mit Entry-Wert vergleichen
			objEntry = objFileIniTest.getEntry();
			assertNotNull(objEntry);
			assertEquals(sValue, objEntry.getValue());			
			assertTrue(objEntry.isExpression()); //Zumindest die PATH Anweisungen wurden ersetzt
			
			assertFalse(objEntry.isFormula());
			
			assertTrue(objEntry.isSolveCalled()); //Solver gestartet, aber nicht er wird nicht ausgeführt.
			assertFalse(objEntry.isSolvedChanged()); //Merke: PATH Anweisungen werden auch ohne Solver ersetzt.
			
			assertFalse(objEntry.isCall()); 		//Beim Solven erst wuerde der CallIniSolver erst aufgerufen, mit seinem parse(). Wert kommt aus parse()
			assertFalse(objEntry.isJavaCall());	    //Beim Solven erst wuerde der JavaCallIniSolver erst aufgerufen, mit seinem parse(). Wert kommt aus parse()
			assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingClassname() + "'", objEntry.getCallingClassname());
			assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingMethodname() + "'", objEntry.getCallingMethodname());
			
			//Wert mit speziellen Entry-Formelwert vergleichen
			sFormulaSolvedAndConverted = objEntry.getValueFormulaSolvedAndConverted();
			assertNull("NULL erwartet. Wert ist aber '" + sFormulaSolvedAndConverted + "'", sFormulaSolvedAndConverted); //Da keine Formel enthalten ist
						
			sFormulaSolvedAndConvertedAsExpression = objEntry.getValueFormulaSolvedAndConvertedAsExpression();					
			assertEquals(XmlUtilZZZ.computeTagNull(), sFormulaSolvedAndConvertedAsExpression);//Da keine Formel enthalten ist.

			bReturn = true;
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}
		
		return bReturn;
	}
	
	private boolean testCompute_CallJavaEntry_Detail_CallUnsolved_() {
		boolean bReturn = false;
		
		try {
			boolean btemp; 
			
			String sExpressionSource; 
			String sExpressionSolved; String sValue;				
			ReferenceZZZ<IKernelConfigSectionEntryZZZ> objSectionEntryReference;
			IKernelConfigSectionEntryZZZ objEntry;
		
			String sFormulaSolvedAndConverted; String sFormulaSolvedAndConvertedAsExpression;
			
			
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++		
			//Nur Expression ausrechnen, ist aber unverändert vom reinen Ergebnis her.		
			btemp = objFileIniTest.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION, true); 
			assertTrue("Flag nicht vorhanden '" + IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);

			btemp = objExpressionHandler.setFlag(IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER,true); //ohne aktivierten Parser keine SUBSTITUTION  
			assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER + "'", btemp);
			
			btemp = objFileIniTest.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH, true);			
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
		
			btemp = objFileIniTest.setFlag(IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE, true);			
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE + "'", btemp);
							
			btemp = objFileIniTest.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER, true);
			assertTrue("Das Flag 'useexpression' sollte zur Verfügung stehen.", btemp);
			
			btemp = objFileIniTest.setFlag(IKernelCallIniSolverZZZ.FLAGZ.USECALL.name(), false); //Ansonsten wird der Wert sofort ausgerechnet
			assertTrue("Das Flag 'usecall' sollte zur Verfügung stehen.", btemp);
		
			
			sValue = objFileIniTest.getPropertyValue("Section for testCall", "WertCalled").getValue();
			sExpressionSolved = "<Z:Call><Z:Java><Z:Class>basic.zBasic.util.machine.EnvironmentZZZ</Z:Class><Z:Method>getHostName</Z:Method></Z:Java></Z:Call>";
			assertEquals(sExpressionSolved,sValue);
			
			//Wert mit Entry-Wert vergleichen
			objEntry = objFileIniTest.getEntry();
			assertNotNull(objEntry);
			assertEquals(sValue, objEntry.getValue());
			assertTrue(objEntry.isExpression()); //Zumindest die PATH Anweisungen wurden ersetzt
			
			assertFalse(objEntry.isFormula());
			
			//Merke: Der Solver wird zwar ausgefuehrt. INI-Pfade werden auch ausgetauscht
			//       (was kein solve ist, sondern ein substitute!). Aber der JAVACall wird nicht gemacht und daher kein Wert veraendert!
			assertTrue(objEntry.isSolveCalled()); //der Solver wirg immer gestartet, wenn auch nicht immer ausgefuehrt
			assertFalse(objEntry.isSolvedChanged());
			
			assertFalse(objEntry.isCall()); 		//Beim Solven erst wuerde der CallIniSolver erst aufgerufen, mit seinem parse(). Wert kommt aus parse()
			assertFalse(objEntry.isJavaCall());	    //Beim Solven erst wuerde der JavaCallIniSolver erst aufgerufen, mit seinem parse(). Wert kommt aus parse()
			assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingClassname() + "'", objEntry.getCallingClassname());
			assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingMethodname() + "'", objEntry.getCallingMethodname());
						
			//Wert mit speziellen Entry-Formelwert vergleichen
			sFormulaSolvedAndConverted = objEntry.getValueFormulaSolvedAndConverted();
			assertNull("NULL erwartet. Wert ist aber '" + sFormulaSolvedAndConverted + "'", sFormulaSolvedAndConverted); //Da keine Formel enthalten ist
						
			sFormulaSolvedAndConvertedAsExpression = objEntry.getValueFormulaSolvedAndConvertedAsExpression();					
			assertEquals(XmlUtilZZZ.computeTagNull(), sFormulaSolvedAndConvertedAsExpression);//Da keine Formel enthalten ist.

			bReturn = true;
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}
		
		return bReturn;
	}
	
	private boolean testCompute_CallJavaEntry_Detail_() {
		boolean bReturn = false;
		
		try {
			boolean btemp; 
			
			String sExpressionSource; 
			String sExpressionSolved; String sValue;				
			ReferenceZZZ<IKernelConfigSectionEntryZZZ> objSectionEntryReference;
			IKernelConfigSectionEntryZZZ objEntry;
		
			String sFormulaSolvedAndConverted; String sFormulaSolvedAndConvertedAsExpression;
			
			
			
			//###################################################		
			//Berechne die erste Formel, DIRECT			
			String sHostName = EnvironmentZZZ.getHostName();
			
			btemp = objExpressionHandler.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION, true); 
			assertTrue("Flag nicht vorhanden '" + IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);

			btemp = objExpressionHandler.setFlag(IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER,true); //ohne aktivierten Parser keine SUBSTITUTION  
			assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER + "'", btemp);
			
			btemp = objExpressionHandler.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH, true);			
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
		
			btemp = objExpressionHandler.setFlag(IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE, true);			
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE + "'", btemp);
			
			
			btemp = objExpressionHandler.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER, true);
			assertTrue("Das Flag '"+IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER +"' sollte zur Verfügung stehen.", btemp);
			
			btemp = objExpressionHandler.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA,true);
			assertTrue("Das Flag '"+IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA+"' sollte zur Verfügung stehen.", btemp);
			
			btemp = objExpressionHandler.setFlag(IKernelCallIniSolverZZZ.FLAGZ.USECALL, true);
			assertTrue("Das Flag '"+IKernelCallIniSolverZZZ.FLAGZ.USECALL+"' sollte zur Verfügung stehen.", btemp);			
			
			btemp = objExpressionHandler.setFlag(IKernelJavaCallIniSolverZZZ.FLAGZ.USECALL_JAVA, true);
			assertTrue("Das Flag '"+IKernelJavaCallIniSolverZZZ.FLAGZ.USECALL_JAVA+"' sollte zur Verfügung stehen.", btemp);
			
			String sExpression = KernelCallIniSolverZZZTest.sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT; 
			objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
			sValue = objExpressionHandler.solve(sExpression, objSectionEntryReference);			
			assertEquals(sHostName,sValue);
			
			//Diese Berechnung im Entry Objekt nachvollziehen
			objEntry = objSectionEntryReference.get();
			assertNotNull(objEntry);
			assertEquals(sValue,objEntry.getValue());
			assertTrue(objEntry.isExpression());
			assertFalse(objEntry.isFormula());
			assertTrue(objEntry.isSolveCalled());
			assertTrue(objEntry.isCall());
			assertTrue(objEntry.isJavaCall());
			assertEquals("basic.zBasic.util.machine.EnvironmentZZZ",objEntry.getCallingClassname());
			assertEquals("getHostName",objEntry.getCallingMethodname());
			
			//Wert mit speziellen Entry-Formelwert vergleichen
			sFormulaSolvedAndConverted = objEntry.getValueFormulaSolvedAndConverted();
			assertNull("NULL erwartet. Wert ist aber '" + sFormulaSolvedAndConverted + "'", sFormulaSolvedAndConverted); //Da keine Formel enthalten ist
						
			sFormulaSolvedAndConvertedAsExpression = objEntry.getValueFormulaSolvedAndConvertedAsExpression();					
			assertEquals(XmlUtilZZZ.computeTagNull(), sFormulaSolvedAndConvertedAsExpression);//Da keine Formel enthalten ist.

		
		bReturn = true;
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}
		
		return bReturn;
	}
	
	
	//#####################################################################################
	//### ENCRYPTED
	//#####################################################################################
	/** void, Test: Reading an entry in a section of the ini-file
	* Lindhauer; 22.04.2006 12:54:32
	 */
	public void testCompute_Encrypted(){	
		String sExpressionSource=null;
		String sSection; String sProperty; String sExpressionSolved;
		boolean btemp;
		
		String sTagStartZ = "<Z>";
		String sTagEndZ = "</Z>";		
//		try {
			sExpressionSource = KernelEncryptionIniSolverZZZTest.sEXPRESSION_ENCRYPTION01_DEFAULT;
			testCompute_Encrypted_(sExpressionSource);
			
			sExpressionSource = KernelEncryptionIniSolverZZZTest.sEXPRESSION_ENCRYPTION02_DEFAULT;
			testCompute_Encrypted_(sExpressionSource);
			
			sExpressionSource = KernelEncryptionIniSolverZZZTest.sEXPRESSION_ENCRYPTION03_DEFAULT;
			testCompute_Encrypted_(sExpressionSource);
			
			
//		} catch (ExceptionZZZ ez) {
//			fail("Method throws an exception." + ez.getMessageLast());
//		}
//			
	}
	
	/** void, Test: Reading an entry in a section of the ini-file
	* Lindhauer; 22.04.2006 12:54:32
	 */
	private void testCompute_Encrypted_(String sExpressionIn){

		try {	
			boolean btemp; String stemp;
			
			String sTagStartZ = "<Z>";
			String sTagEndZ = "</Z>";						
			String sExpressionSource; String sExpressionSolved; ArrayList alExpressionSolved;
			String sSection; String sProperty;	
			
			//+++++++ VORGEZOGENER LETZTER FEHLERTEST START
								
			//################################################################
			//### Varianten JSON-ArrayList aufzuloesen
			//#################################################################
			
			//a)
			sExpressionSource = sExpressionIn;
			alExpressionSolved = new ArrayList();
			sExpressionSolved = sExpressionSource;			
			btemp = testCompute_Encrypted_(sExpressionSource, sExpressionSolved, alExpressionSolved, false, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
			
			
			//+++++++ VORGEZOGENER LETZTER FEHLERTEST ENDE
			
			//################################################################
			//### Varianten Encryption nicht aufzuloesen
			//#################################################################
			//a)
			sExpressionSource = sExpressionIn;
			sExpressionSolved = sExpressionSource;
			btemp = testCompute_Encrypted_EncryptionUnsolved_(sExpressionSource, sExpressionSolved, false, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
			
			//b)
			sExpressionSource = sExpressionIn;
			sExpressionSolved = sExpressionSource;
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
			btemp = testCompute_Encrypted_EncryptionUnsolved_(sExpressionSource, sExpressionSolved, true, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
			
			
			//c)
			sExpressionSource = sExpressionIn;
			sExpressionSolved = sExpressionSource;			
			btemp = testCompute_Encrypted_EncryptionUnsolved_(sExpressionSource, sExpressionSolved, false, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
			
			
			//d)
			sExpressionSource = sExpressionIn;
			sExpressionSolved = sExpressionSource;
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
			btemp = testCompute_Encrypted_EncryptionUnsolved_(sExpressionSource, sExpressionSolved, true, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
			
			
		
			//################################################################
			//### Varianten Encryption aufzuloesen
			//#################################################################
			
			//a)			
			sExpressionSource = sExpressionIn;
			alExpressionSolved = new ArrayList();
			sExpressionSolved = sExpressionSource;		
			btemp = testCompute_Encrypted_(sExpressionSource, sExpressionSolved, alExpressionSolved, false, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
			
			//b)
			sExpressionSource = sExpressionIn;	
			alExpressionSolved = new ArrayList();
			sExpressionSolved = sExpressionSource;
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
			btemp = testCompute_Encrypted_(sExpressionSource, sExpressionSolved, alExpressionSolved, true, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
			
			
			//c)
			sExpressionSource = sExpressionIn;					
			sExpressionSolved = "<Z>abcde</Z>";			
			btemp = testCompute_Encrypted_(sExpressionSource, sExpressionSolved, alExpressionSolved, false, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
						
			
			//d)
			sExpressionSource = sExpressionIn;			
			sExpressionSolved = "<Z>abcde</Z>";	
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
			btemp = testCompute_Encrypted_(sExpressionSource, sExpressionSolved, alExpressionSolved, true, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
			
									
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}		
		
		
	}
	
	private boolean testCompute_Encrypted_(String sExpressionSourceIn, String sExpressionSolvedIn, ArrayList alExpressionSolvedIn, boolean bRemoveSurroundingSeparatorsOnSolve, IEnumSetMappedTestCaseZZZ objEnumTestCase) {
		boolean bReturn = false;
		
		main:{
			try {
				boolean btemp; String stemp;
				
				String sExpressionSource; String sExpressionSolved; ArrayList alExpressionSolved; String sValue;				
				ReferenceZZZ<IKernelConfigSectionEntryZZZ> objSectionEntryReference;
				
				//... mit Berechnung PATH
				btemp = objExpressionHandler.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION, true); 
				assertTrue("Flag nicht vorhanden '" + IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
				
				btemp = objExpressionHandler.setFlag(IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER,true); //ohne aktivierten Parser keine SUBSTITUTION  
				assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER + "'", btemp);
				
				btemp = objExpressionHandler.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH,true);  
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
				
				btemp = objExpressionHandler.setFlag(IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE,true);  
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE + "'", btemp);
								
				btemp = objExpressionHandler.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER, true); 
				assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
				
				btemp = objExpressionHandler.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA,true);
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA + "'", btemp);
			
				//... mit Berechnung MATH
				btemp = objExpressionHandler.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH, true); 
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH + "'", btemp);
					
				//... mit Berechnung Encryption
				btemp = objExpressionHandler.setFlag(IKernelEncryptionIniSolverZZZ.FLAGZ.USEENCRYPTION, true); //soll dann egal sein
				assertTrue("Flag nicht vorhanden '" + IKernelEncryptionIniSolverZZZ.FLAGZ.USEENCRYPTION + "'", btemp);	
				
				//+++ ... parse ist nicht solve... also wird hier nichts aufgeloest, aussser die Pfade
				if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.PARSE)) {
					sExpressionSource = sExpressionSourceIn;
					alExpressionSolved = alExpressionSolvedIn;		
					sExpressionSolved = sExpressionSolvedIn; //hmExpressionSolved.toString();
					objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
					sValue = objExpressionHandler.parse(sExpressionSource, objSectionEntryReference, bRemoveSurroundingSeparatorsOnSolve);
					assertEquals(sExpressionSolved, sValue);
					
					IKernelConfigSectionEntryZZZ objEntry = objExpressionHandler.parseAsEntry(sExpressionSource, objSectionEntryReference, bRemoveSurroundingSeparatorsOnSolve);
					assertNotNull(objEntry);
					sValue = objEntry.getValue();	
					
					assertEquals(sExpressionSolved, sValue);
					assertFalse(objEntry.isJson());
					assertFalse(objEntry.isJsonArray());
					
					assertFalse(objEntry.isDecrypted()); //Nach dem Parsen ist das nicht entschluesselt
					assertFalse(objEntry.isRawEncrypted()); //Erst im Expression-Solver wird der konkrete Solver aufgerufen (Hier Encryption). Nur der konkrete Solver kennt dann solche speziellen Flags, die nur fuer ihn da sind. 
				}
				//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
					
				//+++ ... solve verhält sich NICHT wie parse(), bei solve wird aufgeloest...
				if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.SOLVE)) {
					sExpressionSource = sExpressionSourceIn;
					alExpressionSolved = alExpressionSolvedIn;
					sExpressionSolved = sExpressionSolvedIn; //hmExpressionSolved.toString();
					objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
					sValue = objExpressionHandler.solve(sExpressionSource, objSectionEntryReference, bRemoveSurroundingSeparatorsOnSolve);
					assertEquals(sExpressionSolved, sValue);
					
					IKernelConfigSectionEntryZZZ objEntry = objExpressionHandler.solveAsEntry(sExpressionSource, objSectionEntryReference, bRemoveSurroundingSeparatorsOnSolve);
					assertNotNull(objEntry);
					sValue = objEntry.getValue();	
					
					assertEquals(sExpressionSolved, sValue);
					assertFalse(objEntry.isJson());
					assertFalse(objEntry.isJsonArray());
					
					assertTrue(objEntry.isDecrypted());  //Nach dem Solven ist das entschluesselt
					assertTrue(objEntry.isRawEncrypted()); //Im Expression-Solver wird der konkrete Solver aufgerufen (Hier Encryption). Nur der konkrete Solver kennt dann solche speziellen Flags, die nur fuer ihn da sind.
				}
				//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

				bReturn = true;
			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			}
		}//end main:
		return bReturn;
	}
	
	private boolean testCompute_Encrypted_Unsolved_(String sExpressionSourceIn, String sExpressionSolvedIn, boolean bRemoveSurroundingSeparatorsOnSolve, IEnumSetMappedTestCaseZZZ objEnumTestCase) {
		boolean bReturn = false;
		
		main:{
			try {
				boolean btemp; String stemp;
				
				String sExpressionSource; ArrayList alExpressionSolved; String sExpressionSolved; String sValue;				
				ReferenceZZZ<IKernelConfigSectionEntryZZZ> objSectionEntryReference;
				
				btemp = objExpressionHandler.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION, true); 
				assertTrue("Flag nicht vorhanden '" + IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
				
				btemp = objExpressionHandler.setFlag(IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER,true); //ohne aktivierten Parser keine SUBSTITUTION  
				assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER + "'", btemp);
				
				btemp = objExpressionHandler.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH,true);  
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
				
				btemp = objExpressionHandler.setFlag(IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE,true);  
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE + "'", btemp);

				//... OHNE SOLVER Berechnung
				btemp = objExpressionHandler.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER, false); 
				assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
				
				//... mit Formelberechnung
				btemp = objExpressionHandler.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA,true);
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA + "'", btemp);
			
				//... mit Berechnung MATH
				btemp = objExpressionHandler.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH, true); 
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH + "'", btemp);
				
				//... mit Encryption Berechnung
				btemp = objExpressionHandler.setFlag(IKernelEncryptionIniSolverZZZ.FLAGZ.USEENCRYPTION, true); //soll dann egal sein
				assertTrue("Flag nicht vorhanden '" + IKernelEncryptionIniSolverZZZ.FLAGZ.USEENCRYPTION + "'", btemp);	
								
				//+++ ... solve verhält sich NICHT wie parse(), bei solve wird aufgeloest...
				if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.PARSE)) {
					sExpressionSource = sExpressionSourceIn;					
					sExpressionSolved = sExpressionSolvedIn;
					objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
					sValue = objExpressionHandler.parse(sExpressionSource, objSectionEntryReference, bRemoveSurroundingSeparatorsOnSolve);
					assertEquals(sExpressionSolved, sValue);
					
					IKernelConfigSectionEntryZZZ objEntry = objExpressionHandler.parseAsEntry(sExpressionSource, objSectionEntryReference, bRemoveSurroundingSeparatorsOnSolve);
					assertNotNull(objEntry);
					sValue = objEntry.getValue();	
					
					assertEquals(sExpressionSolved, sValue);
					assertFalse(objEntry.isJson());
					assertFalse(objEntry.isJsonArray());
					
					assertFalse(objEntry.isDecrypted());
					assertFalse(objEntry.isRawEncrypted());
					
				}
				//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

				//+++ ... parse ist nicht solve... also wird hier nichts aufgeloest, aussser die Pfade
				if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.SOLVE)) {
					sExpressionSource = sExpressionSourceIn;							
					sExpressionSolved = sExpressionSolvedIn;
					objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
					sValue = objExpressionHandler.solve(sExpressionSource, objSectionEntryReference, bRemoveSurroundingSeparatorsOnSolve);
					assertEquals(sExpressionSolved, sValue);
					
					IKernelConfigSectionEntryZZZ objEntry = objExpressionHandler.solveAsEntry(sExpressionSource, objSectionEntryReference, bRemoveSurroundingSeparatorsOnSolve);
					assertNotNull(objEntry);
					sValue = objEntry.getValue();	
					
					assertEquals(sExpressionSolved, sValue);
					assertFalse(objEntry.isJson());
					assertFalse(objEntry.isJsonArray());
					
					assertFalse(objEntry.isDecrypted());
					assertFalse(objEntry.isRawEncrypted());
				}
				//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
					
				
				bReturn = true;
			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			}
		}//end main:
		return bReturn;
	}
	
	private boolean testCompute_Encrypted_EncryptionUnsolved_(String sExpressionSourceIn, String sExpressionSolvedIn, boolean bRemoveSurroundingSeparatorsOnSolve, IEnumSetMappedTestCaseZZZ objEnumTestCase) {
		boolean bReturn = false;
		
		main:{
			try {
				boolean btemp; String stemp;
				
				String sExpressionSource; ArrayList alExpressionSolved; String sExpressionSolved; String sValue;				
				ReferenceZZZ<IKernelConfigSectionEntryZZZ> objSectionEntryReference;
				
				btemp = objExpressionHandler.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION, true); 
				assertTrue("Flag nicht vorhanden '" + IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
				
				btemp = objExpressionHandler.setFlag(IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER,true); //ohne aktivierten Parser keine SUBSTITUTION  
				assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER + "'", btemp);
				
				btemp = objExpressionHandler.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH,true);  
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
				
				btemp = objExpressionHandler.setFlag(IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE,true);  
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE + "'", btemp);
								
				btemp = objExpressionHandler.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER, true); 
				assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
				
				//... mit Formelberechnung
				btemp = objExpressionHandler.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA,true);
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA + "'", btemp);
			
				//... mit Berechnung MATH
				btemp = objExpressionHandler.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH, true); 
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH + "'", btemp);
				
				//... OHNE Berechnung Encryption
				btemp = objExpressionHandler.setFlag(IKernelEncryptionIniSolverZZZ.FLAGZ.USEENCRYPTION, false); 
				assertTrue("Flag nicht vorhanden '" + IKernelEncryptionIniSolverZZZ.FLAGZ.USEENCRYPTION + "'", btemp);	
							
				//+++ ... solve verhält sich NICHT wie parse(), bei solve wird aufgeloest...
				if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.PARSE)) {
					sExpressionSource = sExpressionSourceIn;					
					sExpressionSolved = sExpressionSolvedIn;
					objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
					sValue = objExpressionHandler.parse(sExpressionSource, objSectionEntryReference, bRemoveSurroundingSeparatorsOnSolve);
					assertEquals(sExpressionSolved, sValue);
					
					IKernelConfigSectionEntryZZZ objEntry = objExpressionHandler.parseAsEntry(sExpressionSource, objSectionEntryReference, bRemoveSurroundingSeparatorsOnSolve);
					assertNotNull(objEntry);
					sValue = objEntry.getValue();	
					
					assertEquals(sExpressionSolved, sValue);
					assertFalse(objEntry.isJson());
					assertFalse(objEntry.isJsonArray());
					
					assertFalse(objEntry.isDecrypted());
					assertFalse(objEntry.isRawEncrypted());
				}
				//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

				//+++ ... parse ist nicht solve... also wird hier nichts aufgeloest, aussser die Pfade
				if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.SOLVE)) {
					sExpressionSource = sExpressionSourceIn;							
					sExpressionSolved = sExpressionSolvedIn;
					objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
					sValue = objExpressionHandler.solve(sExpressionSource, objSectionEntryReference, bRemoveSurroundingSeparatorsOnSolve);
					assertEquals(sExpressionSolved, sValue);
					
					IKernelConfigSectionEntryZZZ objEntry = objExpressionHandler.solveAsEntry(sExpressionSource, objSectionEntryReference, bRemoveSurroundingSeparatorsOnSolve);
					assertNotNull(objEntry);
					sValue = objEntry.getValue();	
					
					assertEquals(sExpressionSolved, sValue);
					assertFalse(objEntry.isJson());
					assertFalse(objEntry.isJsonArray());
					
					assertFalse(objEntry.isDecrypted());
					assertFalse(objEntry.isRawEncrypted());
				}
				//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
					
				
				bReturn = true;
			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			}
		}//end main:
		return bReturn;
	}
	

	/** void, Test: Reading an entry in a section of the ini-file
	* Lindhauer; 22.04.2006 12:54:32
	 */
	public void testCompute_Encrypted_IniUsed(){	
		String sExpressionSource=null;
		String sSection; String sProperty; String sExpressionSolved;
		boolean btemp;
		
		String sTagStartZ = "<Z>";
		String sTagEndZ = "</Z>";		
		main:{
			try {
					
				//+++++++ VORGEZOGENER LETZTER FEHLERTEST START
					
				//b)
				sSection = "Section for testEncrypted";
				sProperty = "WertAforDecrypt";			
				sExpressionSolved = KernelEncryptionIniSolverZZZTest.sEXPRESSION_ENCRYPTION01_DEFAULT;
				sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
				btemp = testCompute_Encrypted_IniUsed_EncryptionUnsolved_(sSection, sProperty, sExpressionSolved);
				
				
				//+++++++ VORGEZOGENER LETZTER FEHLERTEST ENDE
			
			
				//################################################################
				//### Varianten ENCRRYPTION per Section/Property in Ini-File aufloesen
				//#################################################################
				
				//a)
				sSection = "Section for testEncrypted";
				sProperty = "WertAforDecrypt";			
				sExpressionSolved = KernelEncryptionIniSolverZZZTest.sEXPRESSION_ENCRYPTION01_DEFAULT;
				sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
				btemp = testCompute_Encrypted_IniUsed_Unsolved_(sSection, sProperty, sExpressionSolved);
				
				//b)
				sSection = "Section for testEncrypted";
				sProperty = "WertAforDecrypt";			
				sExpressionSolved = KernelEncryptionIniSolverZZZTest.sEXPRESSION_ENCRYPTION01_DEFAULT;
				sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
				btemp = testCompute_Encrypted_IniUsed_EncryptionUnsolved_(sSection, sProperty, sExpressionSolved);
					
				//c)
				sSection = "Section for testEncrypted";
				sProperty = "WertAforDecrypt";
				sExpressionSolved = "abcde";		
				btemp = testCompute_Encrypted_IniUsed_Detail_(sSection, sProperty, sExpressionSolved);
					
			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			}
		}//end main:
	}

	
	private boolean testCompute_Encrypted_IniUsed_Unsolved_(String sSectionIn, String sPropertyIn, String sExpressionSolvedIn) {
		boolean bReturn = false;
		
		main:{
			try {
				String sSection; String sProperty; String sExpressionSolved; String sValue;
				boolean btemp; String stemp;
				ReferenceZZZ<IKernelConfigSectionEntryZZZ> objSectionEntryReference;
				
				//... mit Berechnung PATH
				btemp = objFileIniTest.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION, true); 
				assertTrue("Flag nicht vorhanden '" + IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
			
				btemp = objFileIniTest.setFlag(IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER,true); //ohne aktivierten Parser keine SUBSTITUTION  
				assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER + "'", btemp);
				
				btemp = objFileIniTest.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH,true);  
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
								
				btemp = objFileIniTest.setFlag(IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE,true);  
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE + "'", btemp);
				
				//... OHNE solve-Flag ist die eine Berechnung egal
				btemp = objFileIniTest.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER, false); //Ansonsten wird der Wert sofort ausgerechnet
				assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
				
				btemp = objFileIniTest.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA,true);                     //sollte wg. USEEXPRESSION_SOLVER = false egal sein.
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA + "'", btemp);
				
				//... ohne solve-Flag ist die Mathematische Berechnung egal
				btemp = objFileIniTest.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH,true); 
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH + "'", btemp);
							
				//... ohne solve-Flag ist die Encryption Berechnung egal
				btemp = objFileIniTest.setFlag(IKernelEncryptionIniSolverZZZ.FLAGZ.USEENCRYPTION, true); //soll dann egal sein
				assertTrue("Flag nicht vorhanden '" + IKernelEncryptionIniSolverZZZ.FLAGZ.USEENCRYPTION + "'", btemp);	
											
				sSection = sSectionIn;
				sProperty = sPropertyIn;
				sExpressionSolved = sExpressionSolvedIn;						
				objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
				
				IKernelConfigSectionEntryZZZ objEntry = objFileIniTest.getPropertyValue(sSection, sProperty);
				assertNotNull(objEntry);
				sValue = objEntry.getValue();	
				
				assertEquals(sExpressionSolved, sValue);
				assertFalse(objEntry.isJson());
				assertFalse(objEntry.isJsonArray());
				
				assertFalse(objEntry.isDecrypted());
				assertFalse(objEntry.isRawEncrypted());
				
				bReturn = true;
			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			}
		}//end main:
		return bReturn;
	}
	
	
	private boolean testCompute_Encrypted_IniUsed_EncryptionUnsolved_(String sSectionIn, String sPropertyIn, String sExpressionSolvedIn) {
		boolean bReturn = false;
		
		main:{
			try {
				String sSection; String sProperty; String sExpressionSolved; String sValue;
				boolean btemp; String stemp;
				ReferenceZZZ<IKernelConfigSectionEntryZZZ> objSectionEntryReference;
				
				//... mit Berechnung PATH
				btemp = objFileIniTest.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION, true); 
				assertTrue("Flag nicht vorhanden '" + IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
			
				btemp = objFileIniTest.setFlag(IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER,true); //ohne aktivierten Parser keine SUBSTITUTION  
				assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER + "'", btemp);
				
				btemp = objFileIniTest.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH,true);  
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
								
				btemp = objFileIniTest.setFlag(IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE,true);  
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE + "'", btemp);
				
				//... OHNE solve-Flag ist die eine Berechnung egal
				btemp = objFileIniTest.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER, true); 
				assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
				
				btemp = objFileIniTest.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA,true);                     //sollte wg. USEEXPRESSION_SOLVER = false egal sein.
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA + "'", btemp);
				
				//... ohne solve-Flag ist die Mathematische Berechnung egal
				btemp = objFileIniTest.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH,true); 
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH + "'", btemp);
							
				//... ohne solve-Flag ist die Encryption Berechnung egal
				btemp = objFileIniTest.setFlag(IKernelEncryptionIniSolverZZZ.FLAGZ.USEENCRYPTION, false); //Ansonsten wird der Wert sofort ausgerechnet
				assertTrue("Flag nicht vorhanden '" + IKernelEncryptionIniSolverZZZ.FLAGZ.USEENCRYPTION + "'", btemp);	
											
				sSection = sSectionIn;
				sProperty = sPropertyIn;
				sExpressionSolved = sExpressionSolvedIn;						
				objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
				
				IKernelConfigSectionEntryZZZ objEntry = objFileIniTest.getPropertyValue(sSection, sProperty);
				assertNotNull(objEntry);
				sValue = objEntry.getValue();	
				
				assertEquals(sExpressionSolved, sValue);
				assertFalse(objEntry.isJson());
				assertFalse(objEntry.isJsonArray());
				
				assertFalse(objEntry.isDecrypted());
				assertFalse(objEntry.isRawEncrypted());
				
				
				
				bReturn = true;
			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			}
		}//end main:
		return bReturn;
	}
	
	
	
	private boolean testCompute_Encrypted_IniUsed_Detail_(String sSectionIn, String sPropertyIn, String sExpressionSolvedIn) {
		boolean bReturn = false;
		
		main:{
			try {
				String sSection; String sProperty; String sExpressionSolved; String sValue;
				boolean btemp; String stemp;
				ReferenceZZZ<IKernelConfigSectionEntryZZZ> objSectionEntryReference;
				
				//... mit Berechnung PATH
				btemp = objFileIniTest.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION, true); 
				assertTrue("Flag nicht vorhanden '" + IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
			
				btemp = objFileIniTest.setFlag(IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER,true); //ohne aktivierten Parser keine SUBSTITUTION  
				assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER + "'", btemp);
				
				btemp = objFileIniTest.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH,true);  
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
								
				btemp = objFileIniTest.setFlag(IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE,true);  
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE + "'", btemp);
				
				btemp = objFileIniTest.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER, true); 
				assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
				
				btemp = objFileIniTest.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA,true);                     //sollte wg. USEEXPRESSION_SOLVER = false egal sein.
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA + "'", btemp);
				
				btemp = objFileIniTest.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH,true); 
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH + "'", btemp);
							
				//... mit Berechnung Encryption
				btemp = objFileIniTest.setFlag(IKernelEncryptionIniSolverZZZ.FLAGZ.USEENCRYPTION, true); //soll dann egal sein
				assertTrue("Flag nicht vorhanden '" + IKernelEncryptionIniSolverZZZ.FLAGZ.USEENCRYPTION + "'", btemp);	
											
				sSection = sSectionIn;
				sProperty = sPropertyIn;							
				sExpressionSolved = sExpressionSolvedIn;
				
				IKernelConfigSectionEntryZZZ objEntry = objFileIniTest.getPropertyValue(sSection, sProperty);
				assertNotNull(objEntry);
				sValue = objEntry.getValue();				
				assertEquals(sExpressionSolved, sValue);
				assertFalse(objEntry.isJson());
				assertFalse(objEntry.isJsonArray());
				
				assertTrue(objEntry.isDecrypted());
				assertTrue(objEntry.isRawEncrypted());
				
				ArrayList<String> als = objEntry.getValueArrayList();
				assertNull(als);
								
				bReturn = true;
			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			}
		}//end main:
		return bReturn;
	}

	
	
	
	//#################################################################################
	//### JSON ARRAY
	//##################################################################################
	/** void, Test: Reading an entry in a section of the ini-file
	* Lindhauer; 22.04.2006 12:54:32
	 */
	public void testCompute_JsonArray(){	
		boolean btemp;
		String sExpression=null; String sExpressionSolved; ArrayList alExpressionSolved;
		String sSection; String sProperty; 
		
		String sTagStartZ = "<Z>";
		String sTagEndZ = "</Z>";		
//		try {
			sExpression = KernelJsonArrayIniSolverZZZTest.sEXPRESSION_JSONARRAY01_DEFAULT;
			testCompute_JsonArray_(sExpression);
			
//			sExpressionSource = KernelEncryptionIniSolverZZZTest.sEXPRESSION_ENCRYPTION02_DEFAULT;
//			testCompute_Encrypted_(sExpressionSource);
//			
//			sExpressionSource = KernelEncryptionIniSolverZZZTest.sEXPRESSION_ENCRYPTION03_DEFAULT;
//			testCompute_Encrypted_(sExpressionSource);
			
//		} catch (ExceptionZZZ ez) {
//			fail("Method throws an exception." + ez.getMessageLast());
//		}
	}
		
	/** void, Test: Reading an entry in a section of the ini-file
	* Lindhauer; 22.04.2006 12:54:32
	 */
	private void testCompute_JsonArray_(String sExpressionIn){

		try {	
			boolean btemp; String stemp;
			
			String sTagStartZ = "<Z>";
			String sTagEndZ = "</Z>";						
			String sExpression; String sExpressionSolved; ArrayList alExpressionSolved;
			//String sSection; String sProperty;	
			
			//+++++++ VORGEZOGENER LETZTER FEHLERTEST START
								
			//################################################################
			//### Varianten JSON-ArrayList aufzuloesen
			//#################################################################
			
			//a)
			sExpression = sExpressionIn;
			alExpressionSolved = new ArrayList();
			sExpressionSolved = sExpression;			
			btemp = testCompute_JsonArray_(sExpression, sExpressionSolved, alExpressionSolved, false, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
			
			
			//+++++++ VORGEZOGENER LETZTER FEHLERTEST ENDE
			
			//################################################################
			//### Varianten JSON nicht aufzuloesen
			//#################################################################
			//a)
			sExpression = KernelJsonArrayIniSolverZZZTest.sEXPRESSION_JSONARRAY01_DEFAULT;
			sExpressionSolved = sExpression;
			btemp = testCompute_JsonArray_JsonUnsolved_(sExpression, sExpressionSolved, false, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
			
			//b)
			sExpression = KernelJsonArrayIniSolverZZZTest.sEXPRESSION_JSONARRAY01_DEFAULT;
			sExpressionSolved = sExpression;
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
			btemp = testCompute_JsonArray_JsonUnsolved_(sExpression, sExpressionSolved, true, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
			
			
			//c)
			sExpression = KernelJsonArrayIniSolverZZZTest.sEXPRESSION_JSONARRAY01_DEFAULT;
			sExpressionSolved = sExpression;
			//sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
			btemp = testCompute_JsonArray_JsonUnsolved_(sExpression, sExpressionSolved, false, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
			
			
			//d)
			sExpression = KernelJsonArrayIniSolverZZZTest.sEXPRESSION_JSONARRAY01_DEFAULT;
			sExpressionSolved = sExpression;
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
			btemp = testCompute_JsonArray_JsonUnsolved_(sExpression, sExpressionSolved, true, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
			
			
			//################################################################
			//### Varianten JSON-ArrayList nicht aufzuloesen
			//#################################################################
			//a)
			sExpression = KernelJsonArrayIniSolverZZZTest.sEXPRESSION_JSONARRAY01_DEFAULT;
			sExpressionSolved = sExpression;
			//sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
			btemp = testCompute_JsonArray_Unsolved_(sExpression, sExpressionSolved, false, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
			
			//b)
			sExpression = KernelJsonArrayIniSolverZZZTest.sEXPRESSION_JSONARRAY01_DEFAULT;
			sExpressionSolved = sExpression;
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
			btemp = testCompute_JsonArray_Unsolved_(sExpression, sExpressionSolved, true, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
			
			//c)
			sExpression = KernelJsonArrayIniSolverZZZTest.sEXPRESSION_JSONARRAY01_DEFAULT;
			sExpressionSolved = sExpression;
			//sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
			btemp = testCompute_JsonArray_Unsolved_(sExpression, sExpressionSolved, false, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
			
			//d)
			sExpression = KernelJsonArrayIniSolverZZZTest.sEXPRESSION_JSONARRAY01_DEFAULT;
			sExpressionSolved = sExpression;
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
			btemp = testCompute_JsonArray_Unsolved_(sExpression, sExpressionSolved, true, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
			
			//################################################################
			//### Varianten JSON-ArrayList aufzuloesen
			//#################################################################
			
			//a)			
			sExpression = KernelJsonArrayIniSolverZZZTest.sEXPRESSION_JSONARRAY01_DEFAULT;
			alExpressionSolved = new ArrayList();
			sExpressionSolved = sExpression;		
			btemp = testCompute_JsonArray_(sExpression, sExpressionSolved, alExpressionSolved, false, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
			
			//b)
			sExpression = KernelJsonArrayIniSolverZZZTest.sEXPRESSION_JSONARRAY01_DEFAULT;	
			alExpressionSolved = new ArrayList();
			sExpressionSolved = sExpression;
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
			btemp = testCompute_JsonArray_(sExpression, sExpressionSolved, alExpressionSolved, true, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
			
			
			//c)
			sExpression = KernelJsonArrayIniSolverZZZTest.sEXPRESSION_JSONARRAY01_DEFAULT;
			alExpressionSolved = new ArrayList();
			alExpressionSolved.add("TESTWERT2DO2JSON01");
			alExpressionSolved.add("TESTWERT2DO2JSON02");			
			sExpressionSolved = "<Z>" + alExpressionSolved.toString() + "</Z>";			
			btemp = testCompute_JsonArray_(sExpression, sExpressionSolved, alExpressionSolved, false, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
						
			
			//d)
			sExpression = KernelJsonArrayIniSolverZZZTest.sEXPRESSION_JSONARRAY01_DEFAULT;
			alExpressionSolved = new ArrayList();
			alExpressionSolved.add("TESTWERT2DO2JSON01");
			alExpressionSolved.add("TESTWERT2DO2JSON02");
			sExpressionSolved = alExpressionSolved.toString();
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
			btemp = testCompute_JsonArray_(sExpression, sExpressionSolved, alExpressionSolved, true, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
			
									
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}		
		
		//TODOGOON20241014;//mache Methode wie testCompute_IniUsed_   bzw. testCompute_IniUsedUnsolved_
		
		/*
		try {	
					
			boolean bFlagAvailable = objExpressionSolver.setFlag("usejson", false); //Ansonsten wird der Wert sofort ausgerechnet
			assertTrue("Das Flag 'usejson' sollte zur Verfügung stehen.", bFlagAvailable);
			
			//Anwenden der ersten Formel, ohne Berechnung			
			String sExpression = objFileIniTest.getPropertyValue("Section for testJsonArraylist", "Array1").getValue();
			assertEquals(KernelJsonArrayIniSolverZZZTest.sEXPRESSION_JSONARRAY01_DEFAULT,sExpression);
			
			//Berechne die erste Formel
			objExpressionSolver.setFlag("usejson", true);
			objExpressionSolver.setFlag("usejson_array", true);
			String sValue = objExpressionSolver.compute(sExpression);//compute gibt nur einen DebugString zurück
			assertNotNull(sValue);
			assertFalse(sValue.equals(KernelJsonArrayIniSolverZZZTest.sEXPRESSION_JSONARRAY01_DEFAULT));//Auch wenn es nur ein Debug-String ist, so ist er immer verändert.
			
			bFlagAvailable = objFileIniTest.setFlag("usejson", true); //Ansonsten wird der Wert sofort ausgerechnet
			assertTrue("Das Flag 'usejson' sollte zur Verfügung stehen.", bFlagAvailable);
			bFlagAvailable = objFileIniTest.setFlag("usejson_array", true); //Ansonsten wird der Wert sofort ausgerechnet
			assertTrue("Das Flag 'usejson_array' sollte zur Verfügung stehen.", bFlagAvailable);
			
			IKernelConfigSectionEntryZZZ objEntry = objFileIniTest.getPropertyValue("Section for testJsonArraylist", "Array1");
			assertNotNull(objEntry);
			assertTrue(objEntry.isJson());
			assertTrue(objEntry.isJsonArray());
			
			ArrayList<String> als = objEntry.getValueArrayList();
			assertNotNull(als);
			String sValue01 = als.get(0);
			assertTrue(sValue01.equals("TESTWERT2DO2JSON01"));
			
			
			
			
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}
		*/
	}
	
	private boolean testCompute_JsonArray_(String sExpressionSourceIn, String sExpressionSolvedIn, ArrayList alExpressionSolvedIn, boolean bRemoveSurroundingSeparatorsOnSolve, IEnumSetMappedTestCaseZZZ objEnumTestCase) {
		boolean bReturn = false;
		
		main:{
			try {
				boolean btemp; String stemp;
				
				String sExpressionSource; String sExpressionSolved; ArrayList alExpressionSolved; String sValue;				
				ReferenceZZZ<IKernelConfigSectionEntryZZZ> objSectionEntryReference;
				
				//... mit Berechnung PATH
				btemp = objExpressionHandler.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION, true); 
				assertTrue("Flag nicht vorhanden '" + IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
				
				btemp = objExpressionHandler.setFlag(IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER,true); //ohne aktivierten Parser keine SUBSTITUTION  
				assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER + "'", btemp);
				
				btemp = objExpressionHandler.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH,true);  
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
				
				btemp = objExpressionHandler.setFlag(IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE,true);  
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE + "'", btemp);
								
				btemp = objExpressionHandler.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER, true); 
				assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
				
				btemp = objExpressionHandler.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA,true);
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA + "'", btemp);
			
				//... mit Berechnung MATH
				btemp = objExpressionHandler.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH, true); 
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH + "'", btemp);
					
				//... mit Berechnung JSON
				btemp = objExpressionHandler.setFlag(IKernelJsonIniSolverZZZ.FLAGZ.USEJSON, true); 
				assertTrue("Das Flag '" + IKernelJsonIniSolverZZZ.FLAGZ.USEJSON +"' sollte zur Verfügung stehen.", btemp);
								
				//... mit Berechnung JSON-ARRAY
				btemp = objExpressionHandler.setFlag(IKernelJsonArrayIniSolverZZZ.FLAGZ.USEJSON_ARRAY, true);//Ansonsten wird der Wert sofort ausgerechnet 
				assertTrue("Das Flag '" + IKernelJsonArrayIniSolverZZZ.FLAGZ.USEJSON_ARRAY +"' sollte zur Verfügung stehen.", btemp);
				
				
				//+++ ... parse ist nicht solve... also wird hier nichts aufgeloest, aussser die Pfade
				if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.PARSE)) {
					sExpressionSource = sExpressionSourceIn;
					alExpressionSolved = alExpressionSolvedIn;		
					sExpressionSolved = sExpressionSolvedIn; //hmExpressionSolved.toString();
					objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
					sValue = objExpressionHandler.parse(sExpressionSource, objSectionEntryReference, bRemoveSurroundingSeparatorsOnSolve);
					assertEquals(sExpressionSolved, sValue);
				}
				//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
					
				//+++ ... solve verhält sich NICHT wie parse(), bei solve wird aufgeloest...
				if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.SOLVE)) {
					sExpressionSource = sExpressionSourceIn;
					alExpressionSolved = alExpressionSolvedIn;
					sExpressionSolved = sExpressionSolvedIn; //hmExpressionSolved.toString();
					objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
					sValue = objExpressionHandler.solve(sExpressionSource, objSectionEntryReference, bRemoveSurroundingSeparatorsOnSolve);
					assertEquals(sExpressionSolved, sValue);
				}
				//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

				bReturn = true;
			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			}
		}//end main:
		return bReturn;
	}
	
	private boolean testCompute_JsonArray_Unsolved_(String sExpressionSourceIn, String sExpressionSolvedIn, boolean bRemoveSurroundingSeparatorsOnSolve, IEnumSetMappedTestCaseZZZ objEnumTestCase) {
		boolean bReturn = false;
		
		main:{
			try {
				boolean btemp; String stemp;
				
				String sExpressionSource; ArrayList alExpressionSolved; String sExpressionSolved; String sValue;				
				ReferenceZZZ<IKernelConfigSectionEntryZZZ> objSectionEntryReference;
				
				btemp = objExpressionHandler.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION, true); 
				assertTrue("Flag nicht vorhanden '" + IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
				
				btemp = objExpressionHandler.setFlag(IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER,true); //ohne aktivierten Parser keine SUBSTITUTION  
				assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER + "'", btemp);
				
				btemp = objExpressionHandler.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH,true);  
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
				
				btemp = objExpressionHandler.setFlag(IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE,true);  
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE + "'", btemp);
								
				btemp = objExpressionHandler.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER, true); 
				assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
				
				//... mit Formelberechnung
				btemp = objExpressionHandler.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA,true);
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA + "'", btemp);
			
				//... mit Berechnung MATH
				btemp = objExpressionHandler.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH, true); 
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH + "'", btemp);
				
				//... mit Berechnung JSON
				btemp = objExpressionHandler.setFlag(IKernelJsonIniSolverZZZ.FLAGZ.USEJSON, true); 
				assertTrue("Das Flag '" + IKernelJsonIniSolverZZZ.FLAGZ.USEJSON +"' sollte zur Verfügung stehen.", btemp);
								
				//... ohne Berechnung JSON-ARRAY
				btemp = objExpressionHandler.setFlag(IKernelJsonArrayIniSolverZZZ.FLAGZ.USEJSON_ARRAY, false);//Ansonsten wird der Wert sofort ausgerechnet 
				assertTrue("Das Flag '" + IKernelJsonMapIniSolverZZZ.FLAGZ.USEJSON_MAP +"' sollte zur Verfügung stehen.", btemp);
								
				//+++ ... solve verhält sich NICHT wie parse(), bei solve wird aufgeloest...
				if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.PARSE)) {
					sExpressionSource = sExpressionSourceIn;					
					sExpressionSolved = sExpressionSolvedIn;
					objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
					sValue = objExpressionHandler.parse(sExpressionSource, objSectionEntryReference, bRemoveSurroundingSeparatorsOnSolve);
					assertEquals(sExpressionSolved, sValue);
				}
				//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

				//+++ ... parse ist nicht solve... also wird hier nichts aufgeloest, aussser die Pfade
				if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.SOLVE)) {
					sExpressionSource = sExpressionSourceIn;							
					sExpressionSolved = sExpressionSolvedIn;
					objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
					sValue = objExpressionHandler.solve(sExpressionSource, objSectionEntryReference, bRemoveSurroundingSeparatorsOnSolve);
					assertEquals(sExpressionSolved, sValue);
				}
				//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
					
				
				bReturn = true;
			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			}
		}//end main:
		return bReturn;
	}
	
	private boolean testCompute_JsonArray_JsonUnsolved_(String sExpressionSourceIn, String sExpressionSolvedIn, boolean bRemoveSurroundingSeparatorsOnSolve, IEnumSetMappedTestCaseZZZ objEnumTestCase) {
		boolean bReturn = false;
		
		main:{
			try {
				boolean btemp; String stemp;
				
				String sExpressionSource; ArrayList alExpressionSolved; String sExpressionSolved; String sValue;				
				ReferenceZZZ<IKernelConfigSectionEntryZZZ> objSectionEntryReference;
				
				btemp = objExpressionHandler.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION, true); 
				assertTrue("Flag nicht vorhanden '" + IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
				
				btemp = objExpressionHandler.setFlag(IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER,true); //ohne aktivierten Parser keine SUBSTITUTION  
				assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER + "'", btemp);
								
				btemp = objExpressionHandler.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH,true);  
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
				
				btemp = objExpressionHandler.setFlag(IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE,true);  
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE + "'", btemp);
								
				btemp = objExpressionHandler.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER, true); 
				assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
				
				//... mit Formelberechnung
				btemp = objExpressionHandler.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA,true);
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA + "'", btemp);
			
				//... mit Berechnung MATH
				btemp = objExpressionHandler.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH, true); 
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH + "'", btemp);
				
				//... ohne Berechnung JSON
				btemp = objExpressionHandler.setFlag(IKernelJsonIniSolverZZZ.FLAGZ.USEJSON, false); //Ansonsten wird der Wert sofort ausgerechnet
				assertTrue("Das Flag '" + IKernelJsonIniSolverZZZ.FLAGZ.USEJSON +"' sollte zur Verfügung stehen.", btemp);
								
				btemp = objExpressionHandler.setFlag(IKernelJsonArrayIniSolverZZZ.FLAGZ.USEJSON_ARRAY, true); //sollte egal sein
				assertTrue("Das Flag '" + IKernelJsonArrayIniSolverZZZ.FLAGZ.USEJSON_ARRAY +"' sollte zur Verfügung stehen.", btemp);
								
				//+++ ... solve verhält sich NICHT wie parse(), bei solve wird aufgeloest...
				if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.PARSE)) {
					sExpressionSource = sExpressionSourceIn;					
					sExpressionSolved = sExpressionSolvedIn;
					objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
					sValue = objExpressionHandler.parse(sExpressionSource, objSectionEntryReference, bRemoveSurroundingSeparatorsOnSolve);
					assertEquals(sExpressionSolved, sValue);
				}
				//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

				//+++ ... parse ist nicht solve... also wird hier nichts aufgeloest, aussser die Pfade
				if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.SOLVE)) {
					sExpressionSource = sExpressionSourceIn;							
					sExpressionSolved = sExpressionSolvedIn;
					objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
					sValue = objExpressionHandler.solve(sExpressionSource, objSectionEntryReference, bRemoveSurroundingSeparatorsOnSolve);
					assertEquals(sExpressionSolved, sValue);
				}
				//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
					
				
				bReturn = true;
			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			}
		}//end main:
		return bReturn;
	}

	
	/** void, Test: Reading an entry in a section of the ini-file
	* Lindhauer; 22.04.2006 12:54:32
	 */
	public void testCompute_JsonArray_IniUsed(){	
		boolean btemp;
		String sExpression=null; String sExpressionSolved; ArrayList alExpressionSolved;
		String sSection; String sProperty; 
		
		String sTagStartZ = "<Z>";
		String sTagEndZ = "</Z>";		
		try {
				
			//################################################################
			//### Varianten JSON-ArrayList per Section/Property in Ini-File aufloesen
			//#################################################################
			
			sSection = "Section for testJsonArraylist";
			sProperty = "Array1";			
			sExpressionSolved = KernelJsonArrayIniSolverZZZTest.sEXPRESSION_JSONARRAY01_DEFAULT;
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
			btemp = testCompute_JsonArray_IniUsedUnsolved_(sSection, sProperty, sExpressionSolved);
			
			sSection = "Section for testJsonArraylist";
			sProperty = "Array1";
			alExpressionSolved = new ArrayList();
			alExpressionSolved.add("TESTWERT2DO2JSON01");
			alExpressionSolved.add("TESTWERT2DO2JSON02");			
			btemp = testCompute_JsonArray_IniUsed_Detail_(sSection, sProperty, alExpressionSolved);

			

		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}

	
	private boolean testCompute_JsonArray_IniUsedUnsolved_(String sSectionIn, String sPropertyIn, String sExpressionSolvedIn) {
		boolean bReturn = false;
		
		main:{
			try {
				String sSection; String sProperty; String sExpressionSolved; String sValue;
				boolean btemp; String stemp;
				ReferenceZZZ<IKernelConfigSectionEntryZZZ> objSectionEntryReference;
				
				//... mit Berechnung PATH
				btemp = objFileIniTest.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION, true); 
				assertTrue("Flag nicht vorhanden '" + IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
			
				btemp = objFileIniTest.setFlag(IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER,true); //ohne aktivierten Parser keine SUBSTITUTION  
				assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER + "'", btemp);
				
				btemp = objFileIniTest.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH,true);  
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
								
				btemp = objFileIniTest.setFlag(IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE,true);  
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE + "'", btemp);
				
				btemp = objFileIniTest.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER, false); //Ansonsten wird der Wert sofort ausgerechnet
				assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
				
				//... ohne solve-Flag ist die eine Berechnung egal
				btemp = objFileIniTest.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA,true);                     //sollte wg. USEEXPRESSION_SOLVER = false egal sein.
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA + "'", btemp);
				
				//... ohne solve-Flag ist die Mathematische Berechnung egal
				btemp = objFileIniTest.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH,true); 
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH + "'", btemp);
							
				//... mit Berechnung JSON
				btemp = objFileIniTest.setFlag(IKernelJsonIniSolverZZZ.FLAGZ.USEJSON, true); 
				assertTrue("Das Flag '" + IKernelJsonIniSolverZZZ.FLAGZ.USEJSON +"' sollte zur Verfügung stehen.", btemp);
								
				//... ohne Berechnung JSON-ARRAY
				btemp = objFileIniTest.setFlag(IKernelJsonArrayIniSolverZZZ.FLAGZ.USEJSON_ARRAY, true);//Ansonsten wird der Wert sofort ausgerechnet 
				assertTrue("Das Flag '" + IKernelJsonMapIniSolverZZZ.FLAGZ.USEJSON_MAP +"' sollte zur Verfügung stehen.", btemp);
												
				sSection = sSectionIn;
				sProperty = sPropertyIn;
				sExpressionSolved = sExpressionSolvedIn;						
				objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
				sValue = objFileIniTest.getPropertyValue(sSection, sProperty).getValue();
				assertEquals(sExpressionSolved, sValue);
				
				
				bReturn = true;
			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			}
		}//end main:
		return bReturn;
	}
	
	private boolean testCompute_JsonArray_IniUsed_Detail_(String sSectionIn, String sPropertyIn, ArrayList<String> alExpressionSolvedIn) {
		boolean bReturn = false;
		
		main:{
			try {
				String sSection; String sProperty; String sExpressionSolved; String sValue;
				boolean btemp; String stemp;
				ReferenceZZZ<IKernelConfigSectionEntryZZZ> objSectionEntryReference;
				
				//... mit Berechnung PATH
				btemp = objFileIniTest.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION, true); 
				assertTrue("Flag nicht vorhanden '" + IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
			
				btemp = objFileIniTest.setFlag(IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER,true); //ohne aktivierten Parser keine SUBSTITUTION  
				assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER + "'", btemp);
				
				btemp = objFileIniTest.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH,true);  
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
								
				btemp = objFileIniTest.setFlag(IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE,true);  
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE + "'", btemp);
				
				btemp = objFileIniTest.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER, true); 
				assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
				
				btemp = objFileIniTest.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA,true);                     //sollte wg. USEEXPRESSION_SOLVER = false egal sein.
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA + "'", btemp);
				
				btemp = objFileIniTest.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH,true); 
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH + "'", btemp);
							
				//... mit Berechnung JSON
				btemp = objFileIniTest.setFlag(IKernelJsonIniSolverZZZ.FLAGZ.USEJSON, true); 
				assertTrue("Das Flag '" + IKernelJsonIniSolverZZZ.FLAGZ.USEJSON +"' sollte zur Verfügung stehen.", btemp);
								
				//... ohne Berechnung JSON-ARRAY
				btemp = objFileIniTest.setFlag(IKernelJsonArrayIniSolverZZZ.FLAGZ.USEJSON_ARRAY, true);//Ansonsten wird der Wert sofort ausgerechnet 
				assertTrue("Das Flag '" + IKernelJsonMapIniSolverZZZ.FLAGZ.USEJSON_MAP +"' sollte zur Verfügung stehen.", btemp);
												
				sSection = sSectionIn;
				sProperty = sPropertyIn;			
				ArrayList<String>alExpressionSolved = alExpressionSolvedIn;
				sExpressionSolved = alExpressionSolved.toString();
				
				IKernelConfigSectionEntryZZZ objEntry = objFileIniTest.getPropertyValue(sSection, sProperty);
				assertNotNull(objEntry);
				sValue = objEntry.getValue();				
				assertEquals(sExpressionSolved, sValue);
				assertTrue(objEntry.isJson());
				assertTrue(objEntry.isJsonArray());
				
				ArrayList<String> als = objEntry.getValueArrayList();
				assertNotNull(als);
				String sValue00 = als.get(0);
				String sValue00expected = alExpressionSolved.get(0);
				assertEquals(sValue00expected, sValue00);
				
				String sValue01 = als.get(1);
				String sValue01expected = alExpressionSolved.get(1);
				assertEquals(sValue01expected, sValue01);
				
				bReturn = true;
			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			}
		}//end main:
		return bReturn;
	}
	
		
	public void testIsExpression() {
		fail("JUnit Test not developed");
		/* TODOGOON
//		try {			
			String sExpression = "<Z>bin kein JSON</Z>";
			boolean bValue = objExpressionSolver.isExpression(sExpression);
			assertFalse(bValue);
		
		
			sExpression = "<JSON>kljkljlkjklj</JSON>";
			bValue = objExpressionSolver.isExpression(sExpression);
			assertTrue(bValue);
			
			//TODOGOON: DIE BERECHUNG ERFOLGT ANHAND DER KONKRETEN ERSTELLENT DATEI
//			sExpression = sEXPRESSION01_DEFAULT;
//			bValue = objExpressionSolver.isExpression(sExpression);
//			assertTrue(bValue);
			
//		} catch (ExceptionZZZ ez) {
//			fail("Method throws an exception." + ez.getMessageLast());
//		}
 * */ 
	}
	
}//END class
	
