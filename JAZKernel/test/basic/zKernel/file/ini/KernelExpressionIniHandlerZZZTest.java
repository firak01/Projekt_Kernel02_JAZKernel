package basic.zKernel.file.ini;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IObjectWithExpressionZZZ;
import basic.zBasic.util.abstractEnum.EnumSetMappedStatusTestTypeZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedTestCaseZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedTestFlagsetZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedTestSurroundingZZZ;
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
	//Variante, um die Z-Tags aussen drumherumgebaut werden koennen
	protected final static String sEXPRESSION_Expression01_PATHSECTION = "Section A";
	protected final static String sEXPRESSION_Expression01_PATHPROPERTY = "Testentry1";
	protected final static String sEXPRESSION_Expression01_CONTENT_SOLVED = "Testvalue1 to be found";
	protected final static String sEXPRESSION_Expression01_CONTENT = "Testvalue1 to be found";
	protected final static String sEXPRESSION_Expression01A = "Der dynamische Wert1 ist '{["
																		+ KernelExpressionIniHandlerZZZTest.sEXPRESSION_Expression01_PATHSECTION +"]"
																		+ KernelExpressionIniHandlerZZZTest.sEXPRESSION_Expression01_PATHPROPERTY +"}'. FGL rulez.";
	protected final static String sEXPRESSION_Expression01A_SUBSTITUTED = "Der dynamische Wert1 ist '"+KernelExpressionIniHandlerZZZTest.sEXPRESSION_Expression01_CONTENT+"'. FGL rulez.";
	protected final static String sEXPRESSION_Expression01A_SOLVED = "Der dynamische Wert1 ist '"+KernelExpressionIniHandlerZZZTest.sEXPRESSION_Expression01_CONTENT_SOLVED+"'. FGL rulez.";
	
	
	//Variante mit Z-Tags innen	
	//... SECTION UND PROPERTY sind identisch, ergo auch der CONTENT
	//protected final static String sEXPRESSION_Expression01_CONTENT_SOLVED = "Testvalue1 to be found";
	//protected final static String sEXPRESSION_Expression01_CONTENT = "Testvalue1 to be found";
	protected final static String sEXPRESSION_Expression01B = "Der dynamische Wert1 ist '<Z>{["
																		+ KernelExpressionIniHandlerZZZTest.sEXPRESSION_Expression01_PATHSECTION +"]"
																		+ KernelExpressionIniHandlerZZZTest.sEXPRESSION_Expression01_PATHPROPERTY +"}</Z>'. FGL rulez.";
	protected final static String sEXPRESSION_Expression01B_SUBSTITUTED = "Der dynamische Wert1 ist '<Z>"+KernelExpressionIniHandlerZZZTest.sEXPRESSION_Expression01_CONTENT+"</Z>'. FGL rulez.";
	protected final static String sEXPRESSION_Expression01B_SOLVED = "Der dynamische Wert1 ist '<Z>"+KernelExpressionIniHandlerZZZTest.sEXPRESSION_Expression01_CONTENT_SOLVED+"</Z>'. FGL rulez.";
			
	private File objFile;
	private IKernelZZZ objKernel;
	private FileIniZZZ<?> objFileIniTest=null;
	
	/// +++ Die eigentlichen Test-Objekte	
	private KernelExpressionIniHandlerZZZ objExpressionHandler;
	private KernelExpressionIniHandlerZZZ objExpressionHandlerInit;
	
	protected void setUp(){
		try {						
			objFile = TestUtilZZZ.createKernelFileUsed4JUnit_KernelExpressionIniHandlerZZZTest();
			
			//#################
			objKernel = new KernelZZZ("FGL", "01", "test", "ZKernelConfigKernel_test.ini",(String)null);
			
			//#### Ein init TestObjekt
			String[] saFlagInit = {"init"};
			objExpressionHandlerInit = new KernelExpressionIniHandlerZZZ(objKernel, objFileIniTest, saFlagInit);
			
			//#### Das konkrete TestObject
			//Merke: Für diesen Test das konkrete Ini-File an das Test-Objekt uebergeben und sich nicht auf den Kernel selbst beziehen.
			//Merke: 20250816 - Jetzt wird das KernelIniFile-Objekt mit .registerForFlagEvents mit den Flags des Solver verknuepft.
			                 // Darum duerfen jetzt nicht erst einmal alle Flags gesetzt werden, sonst sind ggfs. zuviele gesetzt.
			/*
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
							IKernelJsonMapIniSolverZZZ.FLAGZ.USEJSON_MAP.name(),
							IKernelEncryptionIniSolverZZZ.FLAGZ.USEENCRYPTION.name()
							}; //Merke: In static Utility-Methoden ist auch wichtig, was im Ini-File für Flags angestellt sind.
			                   //       und nicht nur die Flags vom ExpressionIniHandler
			objFileIniTest = new FileIniZZZ(objKernel,  objFile, saFlagFileIni); 
			 */
			objFileIniTest = new FileIniZZZ(objKernel,  objFile);
			
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
		String sExpression; String sExpressionSubstituted;String sExpressionSolved; String sTag=null; String sTagSolved=null;
		
		String sTagStartZ = "<Z>";
		String sTagEndZ = "</Z>";	
//		try {	
		
			//+++++++ VORGEZOGENER LETZTER FEHLERTEST START
			
			//B) Test (01) mit Z-Tag AUSSEN drumherum. Erst jetzt ist es eine Expression und wird ersetzt
			sExpression = sTagStartZ + KernelExpressionIniHandlerZZZTest.sEXPRESSION_Expression01A + sTagEndZ;
			sExpressionSubstituted = sTagStartZ + KernelExpressionIniHandlerZZZTest.sEXPRESSION_Expression01A_SUBSTITUTED + sTagEndZ;
			sExpressionSolved = sTagStartZ + KernelExpressionIniHandlerZZZTest.sEXPRESSION_Expression01A_SOLVED + sTagEndZ;
			testCompute_PATH_(sExpression,sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved);
			
					
			//+++++++ VORGEZOGENER LETZTER FEHLERTEST ENDE
		
            //################################################################
			//A) Test (01) ohne notwendige Pfadersetzung, weil zwar ein PATH-AUSDRUCK aber keine Expression vorliegt
			sExpression = KernelExpressionIniHandlerZZZTest.sEXPRESSION_Expression01A;
			sExpressionSubstituted = sExpression;
			sExpressionSolved = sExpression;			
			testCompute_PATH_(sExpression,sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved);
			
			//B) Test (01) mit Z-Tag AUSSEN drumherum. Erst jetzt ist es eine Expression und wird ersetzt
			sExpression = sTagStartZ + KernelExpressionIniHandlerZZZTest.sEXPRESSION_Expression01A + sTagEndZ;
			sExpressionSubstituted = sTagStartZ + KernelExpressionIniHandlerZZZTest.sEXPRESSION_Expression01A_SUBSTITUTED + sTagEndZ;
			sExpressionSolved = sTagStartZ + KernelExpressionIniHandlerZZZTest.sEXPRESSION_Expression01A_SOLVED + sTagEndZ;
			testCompute_PATH_(sExpression,sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved);
			
			//C) Test (02) mit Z-Tag unmittelbar um den PATH herum. Jetzt ist es eine Expression und wird ersetzt
//TODOGOON20250729;// objEntry.getLineFormulaSolvedAndConverted(); ist nicht die ganze Zeile. Da müsste der Wert noch rein.			

			sExpression = KernelExpressionIniHandlerZZZTest.sEXPRESSION_Expression01B;
			sExpressionSubstituted = KernelExpressionIniHandlerZZZTest.sEXPRESSION_Expression01B_SUBSTITUTED;
			sExpressionSolved = KernelExpressionIniHandlerZZZTest.sEXPRESSION_Expression01B_SOLVED;
			testCompute_PATH_(sExpression,sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved);
			
			//TODO diesen String testen:  String sExpressionSource2 = "Der dynamische Wert2 ist '<Z>[Section B]Testentry2</Z>'. FGL rulez.";		
			
//		} catch (ExceptionZZZ ez) {
//			fail("Method throws an exception." + ez.getMessageLast());
//		}
	}
	
	private void testCompute_PATH_(String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, String sTagIn, String sTagSolvedIn) {
//		try {		
			boolean btemp;	
			String sExpression; String sExpressionSubstituted; String sExpressionSolved; String sTag; String sTagSolved;
			
			

			//Default Uebernahme, aber wg. der speziellen Flags wird dies manchmal uebersteuert
			sExpression = sExpressionIn;
			sExpressionSubstituted = sExpressionSubstitutedIn;
			sExpressionSolved = sExpressionSolvedIn;
			sTag=sTagIn;
			sTagSolved = sTagSolvedIn;
			
			//+++++++ VORGEZOGENER LETZTER FEHLERTEST START

			//Fd) Rechne fuer das Ergebnis die umgebenden Z-Tags raus
			sExpression = sExpressionIn;
			sExpressionSubstituted = sExpressionSubstitutedIn;
			sExpressionSolved = sExpressionSolvedIn;		
			sTag=sTagIn;
			sTagSolved = sTagSolvedIn;
			btemp = testCompute_PATH_(sExpression, sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved, EnumSetMappedTestSurroundingTypeZZZ.SOLVE_REMOVE, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
				
		
			
			//+++++++ VORGEZOGENER LETZTER FEHLERTEST ENDE
			
			//##########################################################
			//### PFAD-Ausdruck drin
			//##########################################################
			
				

			//Ad) solve, unexpressed Fall
			//   Merke: Die deaktivierte Expression laesst die Z-Tags drin.
			sExpression = sExpressionIn;
			sExpressionSubstituted = sExpression;
			sExpressionSolved = sExpression;
			//sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
			sTag=sTagIn;
			sTagSolved = sTagSolvedIn;
			btemp = testCompute_PATH_unexpressed_(sExpression, sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved, EnumSetMappedTestSurroundingTypeZZZ.SOLVE_REMOVE, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);		
			
			
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
			
			//Ec) solve, unsolved Fall					
			sExpression = sExpressionIn;
			sExpressionSubstituted = sExpressionSubstitutedIn;
			sExpressionSolved = sExpressionSubstituted;
			sTag=sTagIn;
			sTagSolved = sTagSolvedIn;
			btemp = testCompute_PATH_unsolved_(sExpression, sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved, EnumSetMappedTestSurroundingTypeZZZ.SOLVE_KEEP, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
		

			//d) solve, unsolved Fall - Rechne fuer das Ergebnis die umgebenden Z-Tags raus
			//   Merke: Der deaktivierte Solver laesst die Z-Tags drin.
			sExpression = sExpressionIn;
			sExpressionSubstituted = sExpressionSubstitutedIn;
			sExpressionSolved = sExpressionSubstituted;
			//sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
			sTag=sTagIn;
			sTagSolved = sTagSolvedIn;
			btemp = testCompute_PATH_unsolved_(sExpression, sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved, EnumSetMappedTestSurroundingTypeZZZ.SOLVE_REMOVE, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);		
			
			
			
			//++++++++++++++++++++++++++++++++++++++++++++++++
			//F) PATH Berechnung
			//a)parse
			
			//b) rechne umgebende Z-Tags raus
			
			//c) solve und behalte umgebenden Z-Tags						
			sExpression = sExpressionIn;
			sExpressionSubstituted = sExpressionSubstitutedIn;
			sExpressionSolved = sExpressionSubstituted;
			//sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ, false, false); //also sicherheitshalber noch Tags, die aussen sind (also nicht ueberall) entfernen
			sTag=sTagIn;
			sTagSolved = sTagSolvedIn;
			btemp = testCompute_PATH_(sExpression, sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved, EnumSetMappedTestSurroundingTypeZZZ.SOLVE_KEEP, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);	//Z-Tags um PATH-Aufloesung immer rausrechnen!!!		
						
			
			//Fd) Rechne fuer das Ergebnis die umgebenden Z-Tags raus
			sExpression = sExpressionIn;
			sExpressionSubstituted = sExpressionSubstitutedIn;
			sExpressionSolved = sExpressionSolvedIn;			
			sTag=sTagIn;
			sTagSolved = sTagSolvedIn;
			btemp = testCompute_PATH_(sExpression, sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved, EnumSetMappedTestSurroundingTypeZZZ.SOLVE_REMOVE, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
				
		
				
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			
			
//		} catch (ExceptionZZZ ez) {
//			fail("Method throws an exception." + ez.getMessageLast());
//		}
	}
	
	private boolean testCompute_PATH_unexpressed_(String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, String sTagIn, String sTagSolvedIn, IEnumSetMappedTestSurroundingZZZ objEnumSurrounding, IEnumSetMappedTestCaseZZZ objEnumTestCase) {
		boolean bReturn = false;
		
		main:{
			try {				
				boolean btemp; 
				
				String sExpression;  String sExpressionSubstituted; String sExpressionSolved; String sTag; String sTagSolved; 
				String sValue; 		
				ReferenceZZZ<IKernelConfigSectionEntryZZZ> objSectionEntryReference;
				IKernelConfigSectionEntryZZZ objEntry=null;
				
				IEnumSetMappedTestFlagsetZZZ objEnumFlagset = EnumSetMappedTestCaseFlagsetTypeZZZ.UNEXPRESSED;
				
				String sTagStartZ = "<Z>";
				String sTagEndZ = "</Z>";
				
				sExpression = sExpressionIn;
				sExpressionSubstituted = sExpressionSubstitutedIn;
				sExpressionSolved = sExpressionSolvedIn;
				sTag = sTagIn;
				sTagSolved = sTagSolvedIn;
				
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
				
				boolean bUseExpressionGeneral = objExpressionHandler.isExpressionEnabledGeneral();
				boolean bUseSolver = objExpressionHandler.isSolverEnabledGeneral();
				
				//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++				
				if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.PARSE)) {	
					objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();				
					sValue = objExpressionHandler.parse(sExpression, objSectionEntryReference, objEnumSurrounding.isSurroundingValueToRemove_OnParse());
					assertEquals(sExpressionSubstituted, sValue);
					
					objEntry = objSectionEntryReference.get();
					assertNotNull(objEntry);
				}
				
				if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.SOLVE)) {
					objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
					sValue = objExpressionHandler.solve(sExpression, objSectionEntryReference, objEnumSurrounding.isSurroundingValueToRemove_OnSolve());

					String sExpressionSolvedTemp = sExpressionSolved;
					if(bUseExpressionGeneral && bUseSolver && objEnumSurrounding.isSurroundingValueToRemove_OnSolve()) {
						sExpressionSolvedTemp = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
					}
					sExpressionSolved = sExpressionSolvedTemp;
					assertEquals(sExpressionSolved, sValue);
					
					objEntry = objSectionEntryReference.get();
					assertNotNull(objEntry);
				}
				
				//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
				
				//Nutze eine Sammlung von assert Methoden, die ein objEntry als input hat.
				//und in der die verschiedenen stati für den unexpressed, unsubstituted, substituted, unsolved, etc Fall stehen.
				btemp = TestUtilAsTestZZZ.assertFileIniEntry(objExpressionHandler, objEnumFlagset, objEnumSurrounding, objEnumTestCase, objEntry, sExpression, sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved);
				
				bReturn = true;
			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			}
		}//end main:
		return bReturn;
	}
	
	private boolean testCompute_PATH_unsolved_(String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, String sTagIn, String sTagSolvedIn, IEnumSetMappedTestSurroundingZZZ objEnumSurrounding, IEnumSetMappedTestCaseZZZ objEnumTestCase) {
		boolean bReturn = false;
		
		main:{
			try {				
				boolean btemp;
				
				String sExpression;  String sExpressionSubstituted; String sExpressionSolved; String sTag; String sTagSolved; 
				String sValue; 		
				ReferenceZZZ<IKernelConfigSectionEntryZZZ> objSectionEntryReference;
				IKernelConfigSectionEntryZZZ objEntry=null;
												
				IEnumSetMappedTestFlagsetZZZ objEnumFlagset = EnumSetMappedTestCaseFlagsetTypeZZZ.UNSOLVED;
				
				sExpression = sExpressionIn;
				sExpressionSubstituted = sExpressionSubstitutedIn;
				sExpressionSolved = sExpressionSolvedIn;
				sTag = sTagIn;
				sTagSolved = sTagSolvedIn;
				
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
					objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();				
					sValue = objExpressionHandler.parse(sExpression, objEnumSurrounding.isSurroundingValueToRemove_OnParse());
					assertEquals(sExpressionSubstituted, sValue);
					
					objEntry = objSectionEntryReference.get();
					assertNotNull(objEntry);
				}
				
				if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.SOLVE)) {											
					objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
					sValue = objExpressionHandler.solve(sExpression, objSectionEntryReference, objEnumSurrounding.isSurroundingValueToRemove_OnSolve());
					assertEquals(sExpressionSubstituted, sValue);
					
					objEntry = objSectionEntryReference.get();
					assertNotNull(objEntry);
				}
				
				//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
				
				//Nutze eine Sammlung von assert Methoden, die ein objEntry als input hat.
				//und in der die verschiedenen stati für den unexpressed, unsubstituted, substituted, unsolved, etc Fall stehen.
				btemp = TestUtilAsTestZZZ.assertFileIniEntry(objExpressionHandler, objEnumFlagset, objEnumSurrounding, objEnumTestCase, objEntry, sExpression, sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved);
				assertTrue(btemp);
				
				bReturn = true;
			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			}
		}//end main:
		return bReturn;
	}

	private boolean testCompute_PATH_PATH_unsubstituted_(String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, String sTagIn, String sTagSolvedIn, IEnumSetMappedTestSurroundingZZZ objEnumSurrounding, IEnumSetMappedTestCaseZZZ objEnumTestCase) {
		boolean bReturn = false;
		
		main:{
			try {
				boolean btemp;
			
				String sExpression; String sExpressionSubstituted; String sExpressionSolved; String sTag; String sTagSolved;
				String sValue;
				ReferenceZZZ<IKernelConfigSectionEntryZZZ> objSectionEntryReference=null; IKernelConfigSectionEntryZZZ objEntry=null;
				
				String sTagStartZ = "<Z>";
				String sTagEndZ = "</Z>";
						
				sExpression = sExpressionIn;
				sExpressionSubstituted = sExpressionSubstitutedIn;
				sExpressionSolved = sExpressionSolvedIn;
				sTag = sTagIn;
				sTagSolved = sTagSolvedIn;
				
				IEnumSetMappedTestFlagsetZZZ objEnumFlagset = EnumSetMappedTestCaseFlagsetTypeZZZ.PATH_UNSUBSTITUTED;
				
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
				
				boolean bUseExpressionGeneral = objExpressionHandler.isExpressionEnabledGeneral();
				boolean bUseSolver = objExpressionHandler.isSolverEnabledGeneral();
				
				//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++				
				if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.PARSE)) {				
					sValue = objExpressionHandler.parse(sExpression, objSectionEntryReference, objEnumSurrounding.getSurroundingValueUsedForMethod());
					assertEquals(sExpressionSubstituted, sValue);
					
					objEntry = objSectionEntryReference.get();
					assertNotNull(objEntry);
				}
				//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
				
				if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.SOLVE)) {
					sValue = objExpressionHandler.solve(sExpression, objSectionEntryReference, objEnumSurrounding.getSurroundingValueUsedForMethod());
					
					String sExpressionSolvedTemp = sExpressionSolved;
					if(bUseExpressionGeneral && bUseSolver && objEnumSurrounding.isSurroundingValueToRemove_OnSolve()) {
						sExpressionSolvedTemp = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
					}
					sExpressionSolved = sExpressionSolvedTemp;
					assertEquals(sExpressionSolved, sValue);
					
					objEntry = objSectionEntryReference.get();
					assertNotNull(objEntry);
				}
				
				//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
				
				//Nutze eine Sammlung von assert Methoden, die ein objEntry als input hat.
				//und in der die verschiedenen stati für den unexpressed, unsubstituted, substituted, unsolved, etc Fall stehen.
				btemp = TestUtilAsTestZZZ.assertFileIniEntry(objExpressionHandler, objEnumFlagset, objEnumSurrounding, objEnumTestCase, objEntry, sExpression, sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved);
				
				bReturn = true;
			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			}
		}//end main:
		return bReturn;
	}
	
	private boolean testCompute_PATH_VARIABLE_unsubstituted_(String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, String sTagIn, String sTagSolvedIn, IEnumSetMappedTestSurroundingZZZ objEnumSurrounding, IEnumSetMappedTestCaseZZZ objEnumTestCase) {
		boolean bReturn = false;
		
		main:{
			try {				
				boolean btemp;
				
				String sExpression;  String sExpressionSubstituted; String sExpressionSolved; String sTag; String sTagSolved; 
				String sValue; 		
				ReferenceZZZ<IKernelConfigSectionEntryZZZ> objSectionEntryReference;
				IKernelConfigSectionEntryZZZ objEntry=null;
				
				String sTagStartZ = "<Z>";
				String sTagEndZ = "</Z>";		
				
				IEnumSetMappedTestFlagsetZZZ objEnumFlagset = EnumSetMappedTestCaseFlagsetTypeZZZ.VARIABLE_UNSUBSTITUTED;
				
				//#########################################################
				//#### SECTION A ##########################################
				sExpression = sExpressionIn;
				sExpressionSubstituted = sExpressionSubstitutedIn;
				sExpressionSolved = sExpressionSolvedIn;
				sTag = sTagIn;
				sTagSolved = sTagSolvedIn;
				
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
					objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
					sValue = objExpressionHandler.parse(sExpression, objEnumSurrounding.getSurroundingValueUsedForMethod());
					assertEquals(sExpressionSubstituted, sValue);
					
					objEntry = objSectionEntryReference.get();
					assertNotNull(objEntry);
				}
				
				if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.SOLVE)) {
					objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
					sValue = objExpressionHandler.solve(sExpression, objEnumSurrounding.getSurroundingValueUsedForMethod());

					String sExpressionSolvedTemp = sExpressionSolved;
					if(objEnumSurrounding.isSurroundingValueToRemove_OnSolve()) {
						sExpressionSolvedTemp = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
					}
					sExpressionSolved = sExpressionSolvedTemp;
					assertEquals(sExpressionSolved, sValue);
					
					objEntry = objSectionEntryReference.get();
					assertNotNull(objEntry);
				}
				
				//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
												
				//Nutze eine Sammlung von assert Methoden, die ein objEntry als input hat.
				//und in der die verschiedenen stati für den unexpressed, unsubstituted, substituted, unsolved, etc Fall stehen.
				btemp = TestUtilAsTestZZZ.assertFileIniEntry(objExpressionHandler, objEnumFlagset, objEnumSurrounding, objEnumTestCase, objEntry, sExpression, sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved);
				assertTrue(btemp);
				
				bReturn = true;
			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			}
		}//end main:
		return bReturn;
	}

	
	private boolean testCompute_PATH_SOLVER_unsolve_(String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, String sTagIn, String sTagSolvedIn, IEnumSetMappedTestSurroundingZZZ objEnumSurrounding, IEnumSetMappedTestCaseZZZ objEnumTestCase) {
		boolean bReturn = false;
		
		main:{
			try {
				boolean btemp;
				
				String sExpression; String sExpressionSubstituted; String sExpressionSolved; String sTag; String sTagSolved;
				String sValue; 		
				ReferenceZZZ<IKernelConfigSectionEntryZZZ> objSectionEntryReference;
				IKernelConfigSectionEntryZZZ objEntry=null;
				
				String sTagStartZ = "<Z>";
				String sTagEndZ = "</Z>";		
				
				sExpression = sExpressionIn;
				sExpressionSubstituted = sExpressionSubstitutedIn;
				sExpressionSolved = sExpressionSolvedIn;
				sTag = sTagIn;
				sTagSolved = sTagSolvedIn;
				
				IEnumSetMappedTestFlagsetZZZ objEnumFlagset = EnumSetMappedTestCaseFlagsetTypeZZZ.UNSOLVED;
				
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
					sValue = objExpressionHandler.parse(sExpression);
					assertEquals(sExpressionSubstituted, sValue);
				}
				//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
					
				if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.SOLVE)) {
					sValue = objExpressionHandler.solve(sExpression, objEnumSurrounding.getSurroundingValueUsedForMethod());
					
					String sExpressionSolvedTemp = sExpressionSolved;
					if(objEnumSurrounding.isSurroundingValueToRemove_OnSolve()) {
						sExpressionSolvedTemp = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
					}
					sExpressionSolved = sExpressionSolvedTemp;
					assertEquals(sExpressionSolved, sValue);
				}
				
				//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
				
				//Nutze eine Sammlung von assert Methoden, die ein objEntry als input hat.
				//und in der die verschiedenen stati für den unexpressed, unsubstituted, substituted, unsolved, etc Fall stehen.
				btemp = TestUtilAsTestZZZ.assertFileIniEntry(objExpressionHandler, objEnumFlagset, objEnumSurrounding, objEnumTestCase, objEntry, sExpression, sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved);
				
				bReturn = true;
			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			}
		}//end main:
		return bReturn;
	}

	private boolean testCompute_PATH_FORMULA_unsolved_(String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, String sTagIn, String sTagSolvedIn, IEnumSetMappedTestSurroundingZZZ objEnumSurrounding, IEnumSetMappedTestCaseZZZ objEnumTestCase) {
		boolean bReturn = false;
		
		main:{
			try {
				boolean btemp; String stemp;				
				String sExpression; String sExpressionSubstituted; String sExpressionSolved; ; String sTag; String sTagSolved;
				String sValue;
				ReferenceZZZ<IKernelConfigSectionEntryZZZ> objSectionEntryReference=null; IKernelConfigSectionEntryZZZ objEntry=null;

				String sTagStartZ = "<Z>";
				String sTagEndZ = "</Z>";

				sExpression = sExpressionIn;
				sExpressionSubstituted = sExpressionSubstitutedIn;
				sExpressionSolved = sExpressionSolvedIn;
				sTag=sTagIn;
				sTagSolved = sTagSolvedIn;
				
				IEnumSetMappedTestFlagsetZZZ objEnumFlagset = EnumSetMappedTestCaseFlagsetTypeZZZ.FORMULA_UNSOLVED;
				
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
				
				boolean bUseExpressionGeneral = objExpressionHandler.isExpressionEnabledGeneral();
				boolean bUseSolver = objExpressionHandler.isSolverEnabledGeneral();
				boolean bUseParser = objExpressionHandler.isParserEnabledGeneral();
				
				//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
				//+++ ... parse sollte aber IMMER den Z-Tag entfernen...
				if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.PARSE)) {																	
					sValue = objExpressionHandler.parse(sExpression, objEnumSurrounding.isSurroundingValueToRemove_OnParse());
					
					String sExpressionSurroundedTemp = sExpressionSubstituted;
					if(bUseExpressionGeneral && bUseParser && objEnumSurrounding.isSurroundingValueToRemove_OnParse()) {
						sExpressionSurroundedTemp = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
					}
					sExpressionSubstituted = sExpressionSurroundedTemp;
					assertEquals(sExpressionSubstituted, sValue);
					
					objEntry = objSectionEntryReference.get();
					assertNotNull(objEntry);
				}
				//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
				
				if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.SOLVE)) {
					sValue = objExpressionHandler.solve(sExpression, objEnumSurrounding.isSurroundingValueToRemove_OnSolve());
					
					String sExpressionSurroundedTemp = sExpressionSolved;
					if(bUseExpressionGeneral && bUseSolver && objEnumSurrounding.isSurroundingValueToRemove_OnSolve()) {
						sExpressionSurroundedTemp = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSurroundedTemp, sTagStartZ, sTagEndZ);
					}
					sExpressionSolved = sExpressionSurroundedTemp;
					assertEquals(sExpressionSolved, sValue);
					
					objEntry = objSectionEntryReference.get();
					assertNotNull(objEntry);
				}
				
				//+++++++++++++++++++++++++++++++++++++++++++++++++++
				
				//Nutze eine Sammlung von assert Methoden, die ein objEntry als input hat.
				//und in der die verschiedenen stati für den unexpressed, unsubstituted, substituted, unsolved, etc Fall stehen.
				btemp = TestUtilAsTestZZZ.assertFileIniEntry(objExpressionHandler, objEnumFlagset, objEnumSurrounding, objEnumTestCase, objEntry, sExpression, sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved);
				assertTrue(btemp);
				
				bReturn = true;
			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			}
		}//end main:
		return bReturn;
	}


	
	private boolean testCompute_PATH_(String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, String sTagIn, String sTagSolvedIn, IEnumSetMappedTestSurroundingZZZ objEnumSurrounding, IEnumSetMappedTestCaseZZZ objEnumTestCase) {
		boolean bReturn = false;
		
		main:{
			try {
				String sExpression; String sExpressionSubstituted; String sExpressionSolved; String sTag; String sTagSolved;
				String sValue;
				boolean btemp; String stemp;
				ReferenceZZZ<IKernelConfigSectionEntryZZZ> objSectionEntryReference=null; IKernelConfigSectionEntryZZZ objEntry=null;				
												
				String sTagStartZ = "<Z>";
				String sTagEndZ = "</Z>";					
								
				sExpression = sExpressionIn;
				sExpressionSubstituted = sExpressionSubstitutedIn;
				sExpressionSolved = sExpressionSolvedIn;
				sTag=sTagIn;
				sTagSolved = sTagSolvedIn;
				
				IEnumSetMappedTestFlagsetZZZ objEnumFlagset = EnumSetMappedTestCaseFlagsetTypeZZZ.SOLVED;
				
				//###################
				
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
						
				boolean bUseExpressionGeneral = objExpressionHandler.isExpressionEnabledGeneral();
				boolean bUseSolver = objExpressionHandler.isSolverEnabledGeneral();
				boolean bUseParser = objExpressionHandler.isParserEnabledGeneral();
				
				if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.PARSE)) {

					//sExpressionSolved = "Der dynamische Wert ist '<Z>Testvalue1 to be found</Z>'. FGL rulez.";			
					objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
					sValue = objExpressionHandler.parse(sExpression, objSectionEntryReference, objEnumSurrounding.getSurroundingValueUsedForMethod()); //false ist wichtig, damit die Z-Tags enthalten bleiben, die die Formeln umgeben
					
					String sExpressionSurroundedTemp = sExpressionSubstituted;
					if(bUseExpressionGeneral && bUseParser && objEnumSurrounding.isSurroundingValueToRemove_OnParse()) {
						sExpressionSurroundedTemp = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSurroundedTemp, sTagStartZ, sTagEndZ);
					}
					sExpressionSubstituted = sExpressionSurroundedTemp;
					assertEquals(sExpressionSubstituted, sValue);
					
					objEntry = objSectionEntryReference.get();
					assertNotNull(objEntry);
				}
				
				if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.SOLVE)) {

					//sExpressionSolved = "Der dynamische Wert ist '<Z>Testvalue1 to be found</Z>'. FGL rulez.";			
					objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
					sValue = objExpressionHandler.solve(sExpression, objSectionEntryReference, objEnumSurrounding.getSurroundingValueUsedForMethod()); //false ist wichtig, damit die Z-Tags enthalten bleiben, die die Formeln umgeben
					
					String sExpressionSurroundedTemp = sExpressionSolved;
					if(bUseExpressionGeneral && bUseSolver && objEnumSurrounding.isSurroundingValueToRemove_OnSolve()) {
						sExpressionSurroundedTemp = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSurroundedTemp, sTagStartZ, sTagEndZ);
					}
					sExpressionSolved = sExpressionSurroundedTemp;
					assertEquals(sExpressionSolved, sValue);
										
					objEntry = objSectionEntryReference.get();
					assertNotNull(objEntry);
				}
				
				//++++++++++++++++++++++++++++++++++++++++++++

				
				//Nutze eine Sammlung von assert Methoden, die ein objEntry als input hat.
				//und in der die verschiedenen stati für den unexpressed, unsubstituted, substituted, unsolved, etc Fall stehen.
				btemp = TestUtilAsTestZZZ.assertFileIniEntry(objExpressionHandler, objEnumFlagset, objEnumSurrounding, objEnumTestCase, objEntry, sExpression, sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved);
				assertTrue(btemp);
				
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
		String sExpression=null; String sExpressionSubstituted=null; String sExpressionSolved=null; String sTag=null; String sTagSolved=null;
//		try {
			
			//+++++++ VORGEZOGENER LETZTER FEHLERTEST START

		//B) Tests mit MATH-Expression, aber ohne Operator (die Testwerte sind in der Funktion, Uebergabeparameter sind momentan noch dummy) //TODOGOON 20250407: Testwerte aus der Funktion herausholen und auch als Konstante in der MATH-Testklasse hinterlegen.
		sExpression = KernelZFormulaIniSolverZZZTest.sEXPRESSION_FORMULA_MATH02;
		sExpressionSubstituted = KernelZFormulaIniSolverZZZTest.sEXPRESSION_FORMULA_MATH02_SUBSTITUTED;
		sExpressionSolved = KernelZFormulaIniSolverZZZTest.sEXPRESSION_FORMULA_MATH02_SOLVED;	
		sTag=KernelZFormulaIniSolverZZZTest.sEXPRESSION_FORMULA_MATH02_CONTENT;
		sTagSolved=KernelZFormulaIniSolverZZZTest.sEXPRESSION_FORMULA_MATH02_CONTENT_SOLVED;
		testCompute_MATH02_withoutOperator_(sExpression,sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved);
		

		
			//+++++++ VORGEZOGENER LETZTER FEHLERTEST ENDE
		
		
			//A) Test ohne MATH-Expression
			sExpression = KernelExpressionIniHandlerZZZTest.sEXPRESSION_Expression01B;
			sExpressionSubstituted = KernelExpressionIniHandlerZZZTest.sEXPRESSION_Expression01B_SUBSTITUTED;
			sExpressionSolved = KernelExpressionIniHandlerZZZTest.sEXPRESSION_Expression01B_SOLVED;						
			sTag=KernelExpressionIniHandlerZZZTest.sEXPRESSION_Expression01_CONTENT;
			sTagSolved=KernelExpressionIniHandlerZZZTest.sEXPRESSION_Expression01_CONTENT_SOLVED;
			testCompute_MATH01_(sExpression,sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved);
			
			//B) Tests mit MATH-Expression, aber ohne Operator (die Testwerte sind in der Funktion, Uebergabeparameter sind momentan noch dummy) //TODOGOON 20250407: Testwerte aus der Funktion herausholen und auch als Konstante in der MATH-Testklasse hinterlegen.
			sExpression = KernelZFormulaIniSolverZZZTest.sEXPRESSION_FORMULA_MATH02;
			sExpressionSubstituted = KernelZFormulaIniSolverZZZTest.sEXPRESSION_FORMULA_MATH02_SUBSTITUTED;
			sExpressionSolved = KernelZFormulaIniSolverZZZTest.sEXPRESSION_FORMULA_MATH02_SOLVED;	
			sTag=KernelZFormulaIniSolverZZZTest.sEXPRESSION_FORMULA_MATH02_CONTENT;
			sTagSolved=KernelZFormulaIniSolverZZZTest.sEXPRESSION_FORMULA_MATH02_CONTENT_SOLVED;
			testCompute_MATH02_withoutOperator_(sExpression,sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved);
			
			
			//C) Tests mit MATH-Expression, mit Operator (die Testwerte sind in der Funktion, Uebergabeparameter sind momentan noch dummy) //TODOGOON 20250407: Testwerte aus der Funktion herausholen und auch als Konstante in der MATH-Testklasse hinterlegen.
			sExpression = KernelZFormulaIniSolverZZZTest.sEXPRESSION_FORMULA_MATH01;
			sExpressionSubstituted = KernelZFormulaIniSolverZZZTest.sEXPRESSION_FORMULA_MATH01_SUBSTITUTED;
			sExpressionSolved = KernelZFormulaIniSolverZZZTest.sEXPRESSION_FORMULA_MATH01_SOLVED;	
			sTag=KernelZFormulaIniSolverZZZTest.sEXPRESSION_FORMULA_MATH01_CONTENT;
			sTagSolved=KernelZFormulaIniSolverZZZTest.sEXPRESSION_FORMULA_MATH01_CONTENT_SOLVED;
			testCompute_MATH02_withOperator_(sExpression,sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved);
			
			
			//sExpressionSourceFormulaMath="Der dynamische Wert ist '<Z><Z:math><Z:val>2</Z:val><Z:op>*</Z:op><Z:val>3</Z:val></Z:math></Z>'. FGL rulez.";
			//sExpressionFormulaMathSolved = "Der dynamische Wert ist '<Z><Z:math><Z:val>2</Z:val><Z:op>*</Z:op><Z:val>3</Z:val></Z:math></Z>'. FGL rulez.";
			
			
			
//		} catch (ExceptionZZZ ez) {
//			fail("Method throws an exception." + ez.getMessageLast());
//		}
	}

	
	private void testCompute_MATH01_(String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, String sTagIn, String sTagSolvedIn) {
		try {	
			

			
			boolean btemp;
			String sTagStartZ = "<Z>";
			String sTagEndZ = "</Z>";						
			String sExpression; String sExpressionSubstituted; String sExpressionSolved; String sTag; String sTagSolved;
			String sExpressionSourceFormulaMath; String sExpressionFormulaMathSubstituted; String sExpressionFormulaMathSolved;
						
			//+++++++ VORGEZOGENER LETZTER FEHLERTEST START
			
						
			//+++++++ VORGEZOGENER LETZTER FEHLERTEST ENDE
			
						
			
			//###################################
			//### MATH
			//###################################
			
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//+++ Kein Math-Ausdruck drin, trotzdem den Pfad ausrechnen
			//a) parse
			sExpression = sExpressionIn;
			sExpressionSubstituted = sExpressionSubstitutedIn;
			sExpressionSolved = sExpressionSolvedIn;
			sTag=sTagIn;
			sTagSolved = sTagSolvedIn;
			btemp = testCompute_MATH_(sExpression, sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved, EnumSetMappedTestSurroundingTypeZZZ.PARSE_KEEP, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);	

			//b)Rechne fuer das Ergebnis die umgebenden Z-Tags raus
			sExpression = sExpressionIn;
			sExpressionSubstituted = sExpressionSubstitutedIn;
			sExpressionSubstituted = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSubstituted, sTagStartZ, sTagEndZ);
			sExpressionSolved = sExpressionSolvedIn;
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
			sTag=sTagIn;
			sTagSolved = sTagSolvedIn;
			btemp = testCompute_MATH_(sExpression, sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved, EnumSetMappedTestSurroundingTypeZZZ.PARSE_REMOVE, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
			
			//c)) solve
			sExpression = sExpressionIn;
			sExpressionSubstituted = sExpressionSubstitutedIn;
			sExpressionSolved = sExpressionSolvedIn;
			sTag=sTagIn;
			sTagSolved = sTagSolvedIn;
			btemp = testCompute_MATH_(sExpression, sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved, EnumSetMappedTestSurroundingTypeZZZ.SOLVE_KEEP, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
			
			//d)Rechne fuer das Ergebnis die umgebenden Z-Tags raus
			sExpression = sExpressionIn;
			sExpressionSubstituted = sExpressionSubstitutedIn;
			sExpressionSubstituted = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSubstituted, sTagStartZ, sTagEndZ);
			sExpressionSolved = sExpressionSolvedIn;
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
			sTag=sTagIn;
			sTagSolved = sTagSolvedIn;
			btemp = testCompute_MATH_(sExpression, sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved, EnumSetMappedTestSurroundingTypeZZZ.SOLVE_REMOVE, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}
		
	private void testCompute_MATH02_withoutOperator_(String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, String sTagIn, String sTagSolvedIn) {
		try {
			
			//TODOGOON20250407: Die Uebergabeparameter sind noch Dummy. Diese irgendwie von aussen reingeben.
			//Merke: Ganz ohne Uebergabeparameter wird eine Methode in dieser JUnit-Version als "Testfall" angesehen.
			
			boolean btemp;
			String sTagStartZ = "<Z>";
			String sTagEndZ = "</Z>";						
			String sExpression; String sExpressionSubstituted; String sExpressionSolved; String sTag; String sTagSolved;
			String sExpressionSourceFormulaMath; String sExpressionFormulaMathSubstituted; String sExpressionFormulaMathSolved;
						
			//+++++++ VORGEZOGENER LETZTER FEHLERTEST START
			
			//##########################################################
			//### Math-Ausdruck drin, OHNE einen OPERATOR zu verwenden
			//##########################################################
				
			//c) solve
			//sExpressionSourceFormulaMath="Der dynamische Wert ist '<Z><Z:formula><Z:math><Z:val>2</Z:val><Z:val>3</Z:val></Z:math></Z:formula></Z>'. FGL rulez.";
			//sExpression = sExpressionSourceFormulaMath;
			//sExpressionSubstituted = sExpression;
			//sExpressionSubstituted = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSubstituted, sTagStartZ, sTagEndZ);
			//sExpressionSolved = "Der dynamische Wert ist '<Z>23</Z>'. FGL rulez.";
			//sExpressionSolved= KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
			sExpression = sExpressionIn;
			sExpressionSubstituted = sExpressionSubstitutedIn;
			sExpressionSolved = sExpressionSolvedIn;
			sTag = sTagIn;
			sTagSolved = sTagSolvedIn;
			btemp = testCompute_MATH_(sExpression, sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved, EnumSetMappedTestSurroundingTypeZZZ.SOLVE_KEEP, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);	
	
			
			//+++++++ VORGEZOGENER LETZTER FEHLERTEST ENDE
	
			//##########################################################
			//### Math-Ausdruck drin, 
			//### OHNE einen OPERATOR zu 
			//### - FALL: KEINE AUFLOESUNG
			//##########################################################
			//a) parse
//			sExpressionSourceFormulaMath="Der dynamische Wert ist '<Z><Z:formula><Z:math><Z:val>2</Z:val><Z:val>3</Z:val></Z:math></Z:formula></Z>'. FGL rulez.";
//			sExpressionFormulaMathSubstituted = sExpressionSourceFormulaMath;
//			sExpressionFormulaMathSolved = sExpressionSourceFormulaMath;
			sExpression = sExpressionIn;
			sExpressionSubstituted = sExpressionSubstitutedIn;
			sExpressionSolved = sExpressionSolvedIn;
			sTag = sTagIn;
			sTagSolved = sTagSolvedIn; //TODOGOON20250803;//Z-Tags werden entfernt, obwohl sie eigentlich drin bleiben sollen!!!
			btemp = testCompute_MATH_unsolved_(sExpression, sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved, EnumSetMappedTestSurroundingTypeZZZ.PARSE_KEEP, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
	
			//b) ohne Z-Tags
//			sExpressionSourceFormulaMath="Der dynamische Wert ist '<Z><Z:formula><Z:math><Z:val>2</Z:val><Z:val>3</Z:val></Z:math></Z:formula></Z>'. FGL rulez.";			
//			sExpressionFormulaMathSubstituted = sExpressionSourceFormulaMath;
//			sExpressionFormulaMathSubstituted = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionFormulaMathSubstituted, sTagStartZ, sTagEndZ);
//			//unsolved Fall: sExpressionFormulaMathSolved = "Der dynamische Wert ist '<Z>23</Z>'. FGL rulez.";
//			sExpressionFormulaMathSolved = sExpressionFormulaMathSubstituted;
//			sExpressionFormulaMathSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionFormulaMathSolved, sTagStartZ, sTagEndZ);
			sExpression = sExpressionIn;
			sExpressionSubstituted = sExpressionSubstitutedIn;
			sExpressionSubstituted = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSubstituted, sTagStartZ, sTagEndZ);			
			sExpressionSolved = sExpressionSolvedIn;
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
			sTag = sTagIn;
			sTagSolved = sTagSolvedIn;
			btemp = testCompute_MATH_unsolved_(sExpression, sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved, EnumSetMappedTestSurroundingTypeZZZ.PARSE_REMOVE, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
				
			//c) solve
//			sExpressionSourceFormulaMath="Der dynamische Wert ist '<Z><Z:formula><Z:math><Z:val>2</Z:val><Z:val>3</Z:val></Z:math></Z:formula></Z>'. FGL rulez.";
//			sExpressionFormulaMathSubstituted = sExpressionSourceFormulaMath;
//			//sExpressionFormulaMathSubstituted = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionFormulaMathSubstituted, sTagStartZ, sTagEndZ);
//			//unsolved Fall: sExpressionFormulaMathSolved = "Der dynamische Wert ist '<Z>23</Z>'. FGL rulez.";
//			sExpressionFormulaMathSolved = sExpressionFormulaMathSubstituted;
//			//sExpressionFormulaMathSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionFormulaMathSolved, sTagStartZ, sTagEndZ);
//			
//			
			sExpression = sExpressionIn;
			sExpressionSubstituted = sExpressionSubstitutedIn;
			//sExpressionSubstituted = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSubstituted, sTagStartZ, sTagEndZ);			
			sExpressionSolved = sExpressionSubstitutedIn;
			//sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
			//...nur MATH-Solver wird nicht aufgeloest... Formula-Solver schon
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, KernelZFormulaIniSolverZZZ.sTAG_NAME);
			
			sTag = sTagIn;
			sTagSolved = sTagIn;
			sTagSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sTagSolved, KernelZFormulaIniSolverZZZ.sTAG_NAME);
			btemp = testCompute_MATH_unsolved_(sExpression, sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved, EnumSetMappedTestSurroundingTypeZZZ.SOLVE_KEEP, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);	
	
			//d) ohne Z-Tags
//			sExpressionSourceFormulaMath="Der dynamische Wert ist '<Z><Z:formula><Z:math><Z:val>2</Z:val><Z:val>3</Z:val></Z:math></Z:formula></Z>'. FGL rulez.";
//			sExpressionFormulaMathSubstituted = sExpressionSourceFormulaMath;
//			sExpressionFormulaMathSubstituted = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionFormulaMathSubstituted, sTagStartZ, sTagEndZ);
//			//unsolved Fall: sExpressionFormulaMathSolved = "Der dynamische Wert ist '<Z>23</Z>'. FGL rulez.";
//			sExpressionFormulaMathSolved = sExpressionFormulaMathSubstituted;
//			sExpressionFormulaMathSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionFormulaMathSolved, sTagStartZ, sTagEndZ);
//			
			
			

//			//...Ohne Formula-Tags
//			sExpressionFormulaMathSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionFormulaMathSolved, KernelZFormulaIniSolverZZZ.sTAG_NAME);
			sExpression = sExpressionIn;
			sExpressionSubstituted = sExpressionSubstitutedIn;
			sExpressionSubstituted = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSubstituted, sTagStartZ, sTagEndZ);			
			sExpressionSolved = sExpressionSubstitutedIn;
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
			//...nur MATH-Solver wird nicht aufgeloest... Formula-Solver schon
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, KernelZFormulaIniSolverZZZ.sTAG_NAME);			
			sTag = sTagIn;
			sTagSolved = sTagIn;
			//...nur MATH-Solver wird nicht aufgeloest... Formula-Solver schon
			sTagSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sTagSolved, KernelZFormulaIniSolverZZZ.sTAG_NAME);						
			btemp = testCompute_MATH_unsolved_(sExpression, sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved, EnumSetMappedTestSurroundingTypeZZZ.SOLVE_REMOVE, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
			
			//##########################################################
			//### Math-Ausdruck drin, OHNE einen OPERATOR zu verwenden
			//##########################################################
			//a) parse
//			sExpressionSourceFormulaMath="Der dynamische Wert ist '<Z><Z:formula><Z:math><Z:val>2</Z:val><Z:val>3</Z:val></Z:math></Z:formula></Z>'. FGL rulez.";
//			sExpression = sExpressionSourceFormulaMath;
//			sExpressionSubstituted = sExpression;
//			sExpressionSolved= KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSubstituted, sTagStartZ, sTagEndZ);
			
			sExpression = sExpressionIn;
			sExpressionSubstituted = sExpressionSubstitutedIn;
			//sExpressionSubstituted = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSubstituted, sTagStartZ, sTagEndZ);			
			sExpressionSolved = sExpressionSolvedIn;
			//sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
			sTag = sTagIn;
			sTagSolved = sTagSolvedIn;
			btemp = testCompute_MATH_(sExpression, sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved, EnumSetMappedTestSurroundingTypeZZZ.PARSE_KEEP, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
	
			//b) ohne Z-Tags
//			sExpressionSourceFormulaMath="Der dynamische Wert ist '<Z><Z:formula><Z:math><Z:val>2</Z:val><Z:val>3</Z:val></Z:math></Z:formula></Z>'. FGL rulez.";
//			sExpression = sExpressionSourceFormulaMath;
//			sExpressionSubstituted = sExpression;
//			sExpressionSubstituted = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSubstituted, sTagStartZ, sTagEndZ);
//			sExpressionSolved="Der dynamische Wert ist '<Z>23</Z>'. FGL rulez.";
//			sExpressionSolved= KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
			
			sExpression = sExpressionIn;
			sExpressionSubstituted = sExpressionSubstitutedIn;
			sExpressionSubstituted = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSubstituted, sTagStartZ, sTagEndZ);			
			sExpressionSolved = sExpressionSolvedIn;
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
			sTag = sTagIn;
			sTagSolved = sTagSolvedIn;
			btemp = testCompute_MATH_(sExpression, sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved, EnumSetMappedTestSurroundingTypeZZZ.PARSE_REMOVE, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
				
			//c) solve
//			sExpressionSourceFormulaMath="Der dynamische Wert ist '<Z><Z:formula><Z:math><Z:val>2</Z:val><Z:val>3</Z:val></Z:math></Z:formula></Z>'. FGL rulez.";
//			sExpression = sExpressionSourceFormulaMath;
//			sExpressionSubstituted = sExpression;
//			//sExpressionSubstituted = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSubstituted, sTagStartZ, sTagEndZ);
//			sExpressionSolved = "Der dynamische Wert ist '<Z>23</Z>'. FGL rulez.";
//			//sExpressionSolved= KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
			
			sExpression = sExpressionIn;
			sExpressionSubstituted = sExpressionSubstitutedIn;
			//sExpressionSubstituted = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSubstituted, sTagStartZ, sTagEndZ);			
			sExpressionSolved = sExpressionSolvedIn;
			//sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
			sTag = sTagIn;
			sTagSolved = sTagSolvedIn;
			btemp = testCompute_MATH_(sExpression, sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved, EnumSetMappedTestSurroundingTypeZZZ.SOLVE_KEEP, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);	
	
			//d) ohne Z-Tags
//			sExpressionSourceFormulaMath="Der dynamische Wert ist '<Z><Z:formula><Z:math><Z:val>2</Z:val><Z:val>3</Z:val></Z:math></Z:formula></Z>'. FGL rulez.";
//			sExpression = sExpressionSourceFormulaMath;
//			sExpressionSubstituted = sExpression;
//			sExpressionSubstituted = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSubstituted, sTagStartZ, sTagEndZ);
//			sExpressionSolved = "Der dynamische Wert ist '<Z>23</Z>'. FGL rulez.";
//			sExpressionSolved= KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
			
			sExpression = sExpressionIn;
			sExpressionSubstituted = sExpressionSubstitutedIn;
			sExpressionSubstituted = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSubstituted, sTagStartZ, sTagEndZ);			
			sExpressionSolved = sExpressionSolvedIn;
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
			sTag = sTagIn;
			sTagSolved = sTagSolvedIn;
			btemp = testCompute_MATH_(sExpression, sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved, EnumSetMappedTestSurroundingTypeZZZ.SOLVE_REMOVE, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
						
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
																						
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}

	private void testCompute_MATH02_withOperator_(String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, String sTagIn, String sTagSolvedIn) {
		try {
			
			//TODOGOON20250407: Die Uebergabeparameter sind noch Dummy. Diese irgendwie von aussen reingeben.
			//Merke: Ganz ohne Uebergabeparameter wird eine Methode in dieser JUnit-Version als "Testfall" angesehen.
			
			boolean btemp;
			String sTagStartZ = "<Z>";
			String sTagEndZ = "</Z>";						
			String sExpression; String sExpressionSubstituted; String sExpressionSolved; String sTag; String sTagSolved;
			String sExpressionSourceFormulaMath; String sExpressionFormulaMathSubstituted; String sExpressionFormulaMathSolved;
						
			//+++++++ VORGEZOGENER LETZTER FEHLERTEST START
			
			
			
			//+++++++ VORGEZOGENER LETZTER FEHLERTEST ENDE
	
				
			//#####################################################
			//### Math-Ausdruck drin, der einen Operator enthaelt
			//#####################################################
			//a) parse
			sExpressionSourceFormulaMath="Der dynamische Wert ist '<Z><Z:formula><Z:math><Z:val>2</Z:val><Z:op>*</Z:op><Z:val>3</Z:val></Z:math></Z:formula></Z>'. FGL rulez.";
			sExpressionFormulaMathSubstituted = sExpressionSourceFormulaMath;
			sExpressionFormulaMathSolved = sExpressionFormulaMathSubstituted;
			
			sExpression = sExpressionSourceFormulaMath;
			sExpressionSubstituted = sExpressionFormulaMathSubstituted;
			sExpressionSolved = sExpressionFormulaMathSolved;
			//sExpressionSolved= KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
			sTag=sTagIn;
			sTagSolved = sTagSolvedIn;
			btemp = testCompute_MATH_(sExpressionSourceFormulaMath, sExpressionFormulaMathSubstituted, sTag, sTagSolved, sExpressionSourceFormulaMath, EnumSetMappedTestSurroundingTypeZZZ.PARSE_KEEP, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
	
			//b) ohne Z-Tags
			sExpressionSourceFormulaMath="Der dynamische Wert ist '<Z><Z:formula><Z:math><Z:val>2</Z:val><Z:op>*</Z:op><Z:val>3</Z:val></Z:math></Z:formula></Z>'. FGL rulez.";
			sExpressionFormulaMathSubstituted = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSourceFormulaMath, sTagStartZ, sTagEndZ);
			sExpressionFormulaMathSolved = sExpressionFormulaMathSubstituted;
			
			sExpression = sExpressionSourceFormulaMath;
			sExpressionSubstituted = sExpressionFormulaMathSubstituted;
			sExpressionSolved = sExpressionFormulaMathSolved;
			sExpressionSolved= KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
			sTag=sTagIn;
			sTagSolved = sTagSolvedIn;
			btemp = testCompute_MATH_(sExpression, sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved, EnumSetMappedTestSurroundingTypeZZZ.PARSE_REMOVE, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
						
			//c) solve
			sExpressionSourceFormulaMath="Der dynamische Wert ist '<Z><Z:formula><Z:math><Z:val>2</Z:val><Z:op>*</Z:op><Z:val>3</Z:val></Z:math></Z:formula></Z>'. FGL rulez.";
			sExpressionFormulaMathSubstituted = sExpressionSourceFormulaMath;
			sExpressionFormulaMathSolved = "Der dynamische Wert ist '<Z>6</Z>'. FGL rulez.";
						
			sExpression = sExpressionSourceFormulaMath;
			sExpressionSubstituted = sExpressionFormulaMathSubstituted;			
			sExpressionSolved= sExpressionFormulaMathSolved;
			//sExpressionSolved= KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
			sTag=sTagIn;
			sTagSolved = sTagSolvedIn;
			btemp = testCompute_MATH_(sExpression, sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved, EnumSetMappedTestSurroundingTypeZZZ.SOLVE_KEEP, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);	
	
			//d) ohne Z-Tags
			sExpressionSourceFormulaMath="Der dynamische Wert ist '<Z><Z:formula><Z:math><Z:val>2</Z:val><Z:op>*</Z:op><Z:val>3</Z:val></Z:math></Z:formula></Z>'. FGL rulez.";
			sExpressionFormulaMathSubstituted = sExpressionSourceFormulaMath;
			sExpressionFormulaMathSolved = "Der dynamische Wert ist '<Z>6</Z>'. FGL rulez.";
			
			sExpression = sExpressionSourceFormulaMath;
			sExpressionSubstituted = sExpressionFormulaMathSubstituted;			
			sExpressionSolved= sExpressionFormulaMathSolved;
			sExpressionSolved= KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
			sTag=sTagIn;
			sTagSolved = sTagSolvedIn;
			btemp = testCompute_MATH_(sExpression, sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved, EnumSetMappedTestSurroundingTypeZZZ.SOLVE_REMOVE, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
						
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++									
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}


	private boolean testCompute_MATH_unsolved_(String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, String sTagIn, String sTagSolvedIn, IEnumSetMappedTestSurroundingZZZ objEnumSurrounding, IEnumSetMappedTestCaseZZZ objEnumTestCase) {
		boolean bReturn = false;
		
		main:{
			try {
				String sExpression; String sExpressionSubstituted; String sExpressionSolved;  String sTag; String sTagSolved;
				String sValue;
				boolean btemp;
				ReferenceZZZ<IKernelConfigSectionEntryZZZ> objSectionEntryReference=null; IKernelConfigSectionEntryZZZ objEntry=null;
				
				String sTagStartZ = "<Z>";
				String sTagEndZ = "</Z>";
				
				sExpression = sExpressionIn;
				sExpressionSubstituted = sExpressionSubstitutedIn;
				sExpressionSolved = sExpressionSolvedIn;
				sTag=sTagIn;
				sTagSolved = sTagSolvedIn;
				
				IEnumSetMappedTestFlagsetZZZ objEnumFlagset = EnumSetMappedTestCaseFlagsetTypeZZZ.FORMULA_MATH_UNSOLVED;
				
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
						
				boolean bUseExpressionGeneral = objExpressionHandler.isExpressionEnabledGeneral();
				boolean bUseSolver = objExpressionHandler.isSolverEnabledGeneral();
				boolean bUseParser = objExpressionHandler.isParserEnabledGeneral();
				
				//+++ ... parse ist nicht solve... also wird hier nichts aufgeloest, aussser die Pfade
				if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.PARSE)) {		
					objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
					sValue = objExpressionHandler.parse(sExpression, objSectionEntryReference, objEnumSurrounding.getSurroundingValueUsedForMethod());
					
					String sExpressionSurroundedTemp = sExpressionSubstituted;
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
					objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
					sValue = objExpressionHandler.solve(sExpression, objSectionEntryReference, objEnumSurrounding.getSurroundingValueUsedForMethod());
					
					String sExpressionSurroundedTemp = sExpressionSolved;
					if(bUseExpressionGeneral && bUseSolver && objEnumSurrounding.isSurroundingValueToRemove_OnSolve()) {
						sExpressionSurroundedTemp = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSurroundedTemp, sTagStartZ, sTagEndZ);
					}
					sExpressionSolved = sExpressionSurroundedTemp;
					assertEquals(sExpressionSolved, sValue);

					objEntry = objSectionEntryReference.get();
					assertNotNull(objEntry);
				}
				//+++++++++++++++++++++++++++++++++++++++++++++++++++
				
				//Nutze eine Sammlung von assert Methoden, die ein objEntry als input hat.
				//und in der die verschiedenen stati für den unexpressed, unsubstituted, substituted, unsolved, etc Fall stehen.
				btemp = TestUtilAsTestZZZ.assertFileIniEntry(objExpressionHandler, objEnumFlagset, objEnumSurrounding, objEnumTestCase, objEntry, sExpression, sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved);
				assertTrue(btemp);
				
				bReturn = true;
			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			}
		}//end main:
		return bReturn;
	}
	
	private boolean testCompute_MATH_(String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, String sTagIn, String sTagSolvedIn, IEnumSetMappedTestSurroundingZZZ objEnumSurrounding, IEnumSetMappedTestCaseZZZ objEnumTestCase) {
		boolean bReturn = false;
		
		main:{
			try {
				String sExpression; String sExpressionSubstituted; String sExpressionSolved;  String sTag; String sTagSolved;
				String sValue;
				boolean btemp; String stemp;
				ReferenceZZZ<IKernelConfigSectionEntryZZZ> objSectionEntryReference=null; IKernelConfigSectionEntryZZZ objEntry=null;
				
				String sTagStartZ = "<Z>";
				String sTagEndZ = "</Z>";	
				
				sExpression = sExpressionIn;
				sExpressionSubstituted = sExpressionSubstitutedIn;
				sExpressionSolved = sExpressionSolvedIn;
				sTag=sTagIn;
				sTagSolved = sTagSolvedIn;
				
				IEnumSetMappedTestFlagsetZZZ objEnumFlagset = EnumSetMappedTestCaseFlagsetTypeZZZ.FORMULA_MATH_SOLVED;
				
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
					
				boolean bUseExpressionGeneral = objExpressionHandler.isExpressionEnabledGeneral();
				boolean bUseSolver = objExpressionHandler.isSolverEnabledGeneral();
				boolean bUseParser = objExpressionHandler.isParserEnabledGeneral();
				
				//+++ ... parse ist nicht solve... also wird hier nichts aufgeloest, aussser die Pfade (substituiert)
				if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.PARSE)){		
					objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
					sValue = objExpressionHandler.parse(sExpression, objSectionEntryReference, objEnumSurrounding.getSurroundingValueUsedForMethod());
					
					String sExpressionSurroundedTemp = sExpressionSubstituted;
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
					objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
					sValue = objExpressionHandler.solve(sExpression, objSectionEntryReference, objEnumSurrounding.getSurroundingValueUsedForMethod());
					
					String sExpressionSurroundedTemp = sExpressionSolved;
					if(bUseExpressionGeneral && bUseSolver && objEnumSurrounding.isSurroundingValueToRemove_OnSolve()) {
						sExpressionSurroundedTemp = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSurroundedTemp, sTagStartZ, sTagEndZ);
					}
					sExpressionSolved = sExpressionSurroundedTemp;
					assertEquals(sExpressionSolved, sValue);
					
					objEntry = objSectionEntryReference.get();
					assertNotNull(objEntry);
				}
				//+++++++++++++++++++++++++++++++++++++++++++++++++++
				
				//Nutze eine Sammlung von assert Methoden, die ein objEntry als input hat.
				//und in der die verschiedenen stati für den unexpressed, unsubstituted, substituted, unsolved, etc Fall stehen.
				btemp = TestUtilAsTestZZZ.assertFileIniEntry(objExpressionHandler, objEnumFlagset, objEnumSurrounding, objEnumTestCase, objEntry, sExpression, sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved);
				assertTrue(btemp);
				
				
				bReturn = true;
			} catch (ExceptionZZZ ez) {
				ez.printStackTrace();
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
			sExpressionSolved = KernelExpressionIniHandlerZZZTest.sEXPRESSION_Expression01B;
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
			sTagSolved = "{[Section A]Testentry1}";
			btemp = testCompute_PATH_IniUsed_Unsubstituted_(sSection, sProperty, sExpressionSolved, sTagSolved);	
			
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//#### Hier das Solve ausschalten, ohne SOLVE bleibt das Z-Tag drin, auch wenn substituiert wird
			sSection = "Section for testCompute";
			sProperty = "Formula1";
			sExpressionSolved = KernelExpressionIniHandlerZZZTest.sEXPRESSION_Expression01B_SUBSTITUTED;//ohne Formelberechnung bleibt der umgebende Z-Tag drin
			//sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
			sTagSolved = "Testvalue1 to be found";
			btemp = testCompute_PATH_IniUsed_Unsolved_(sSection, sProperty, sExpressionSolved, sTagSolved);	
			

			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//#### Hier komplett aufloesen
			sSection = "Section for testCompute";
			sProperty = "Formula1";
			sExpressionSolved = KernelExpressionIniHandlerZZZTest.sEXPRESSION_Expression01B_SOLVED;
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
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
			sTagSolved = null; //Merke: Im direkten Wert steckt kein Z-Tag, darum ist der Wert hier leer 
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
		IKernelConfigSectionEntryZZZ objEntry=null;
		boolean btemp; String stemp;
		main:{
			try {				
				ReferenceZZZ<IKernelConfigSectionEntryZZZ> objSectionEntryReference;
				
				String sTagStartZ = "<Z>";
				String sTagEndZ = "</Z>";	
				
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
					
				boolean bUseExpressionGeneral = objFileIniTest.isExpressionEnabledGeneral();
				boolean bUseSolver = objFileIniTest.isSolverEnabledGeneral();
				
				objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
				objEntry = objFileIniTest.getPropertyValue(sSection, sProperty);
				assertNotNull(objEntry);
				 
				sValue = objEntry.getValue();
				
				String sExpressionSolvedTemp = sExpressionSolved;
				//hier immer true if(bUseExpressionGeneral && bUseSolver && objEnumSurrounding.isSurroundingValueToRemove_On()) {
				if(bUseExpressionGeneral && bUseSolver){
					sExpressionSolvedTemp = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
				}
				sExpressionSolved = sExpressionSolvedTemp;
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
				IKernelConfigSectionEntryZZZ objEntry=null;
				
				String sTagStartZ = "<Z>";
				String sTagEndZ = "</Z>";
				
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
				
				boolean bUseExpressionGeneral = objFileIniTest.isExpressionEnabledGeneral();
				boolean bUseSolver = objFileIniTest.isSolverEnabledGeneral();
				
				objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
				objEntry = objFileIniTest.getPropertyValue(sSection, sProperty);
				assertNotNull(objEntry);
				 
				sValue = objEntry.getValue();

				String sExpressionSolvedTemp = sExpressionSolved;
				//hier immer true if(bUseExpressionGeneral && bUseSolver && objEnumSurrounding.isSurroundingValueToRemove_On()) {
				if(bUseExpressionGeneral && bUseSolver){
					sExpressionSolvedTemp = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
				}
				sExpressionSolved = sExpressionSolvedTemp;
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
				IKernelConfigSectionEntryZZZ objEntry=null;

				String sTagStartZ = "<Z>";
				String sTagEndZ = "</Z>";
				
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
				
				boolean bUseExpressionGeneral = objFileIniTest.isExpressionEnabledGeneral();
				boolean bUseSolver = objFileIniTest.isSolverEnabledGeneral();
				
				objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
				objEntry = objFileIniTest.getPropertyValue(sSection, sProperty);
				assertNotNull(objEntry);
				 
				sValue = objEntry.getValue();
				
				String sExpressionSolvedTemp = sExpressionSolved;
				//hier immer true if(bUseExpressionGeneral && bUseSolver && objEnumSurrounding.isSurroundingValueToRemove_On()) {
				if(bUseExpressionGeneral && bUseSolver){
					sExpressionSolvedTemp = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
				}
				sExpressionSolved = sExpressionSolvedTemp;
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
				//a) ohne jegliche Aufloesung
				sSection = "Section for testComputeMathValue";
				sProperty = "Formula1";	
				sExpressionSolved = "Der dynamische Wert ist '<Z><Z:formula><Z:math><Z:val>2</Z:val><Z:val>3</Z:val></Z:math></Z:formula></Z>'. FGL rulez.";
				//ohne Solver bleiben die Z-Tags drin: 
				//sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
				sTagSolved = "<Z:formula><Z:math><Z:val>2</Z:val><Z:val>3</Z:val></Z:math></Z:formula>";
				btemp = testCompute_MATH_IniUsed_unsolved_(sSection, sProperty, sExpressionSolved, sTagSolved);	
				
				//b) ohne FORMULA Aufloesung
				sSection = "Section for testComputeMathValue";
				sProperty = "Formula1";							
				sExpressionSolved = "Der dynamische Wert ist '<Z><Z:formula><Z:math><Z:val>2</Z:val><Z:val>3</Z:val></Z:math></Z:formula></Z>'. FGL rulez.";
				
				//Generelle Aufloesung erwuenst. Dann fliegen die Z-Tags raus.
				sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
				sTagSolved = "<Z:formula><Z:math><Z:val>2</Z:val><Z:val>3</Z:val></Z:math></Z:formula>";
				btemp = testCompute_MATH_IniUsed_FORMULA_unsolved_(sSection, sProperty, sExpressionSolved, sTagSolved);
				
				//c) ohne MATH Aufloesung
				sSection = "Section for testComputeMathValue";
				sProperty = "Formula1";	
				
				//Merke: 1. Das Z-Formula Tag fliegt raus.								
				sExpressionSolved = "Der dynamische Wert ist '<Z><Z:math><Z:val>2</Z:val><Z:val>3</Z:val></Z:math></Z>'. FGL rulez.";
				
				//Merke: 2. Generelle Aufloesung erwuenst. Dann fliegen die Z-Tags raus.
				sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
				sTagSolved = "<Z:math><Z:val>2</Z:val><Z:val>3</Z:val></Z:math>";
				btemp = testCompute_MATH_IniUsed_FORMULA_MATH_unsolved_(sSection, sProperty, sExpressionSolved, sTagSolved);	
				
				
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
				
				String sTagStartZ = "<Z>";
				String sTagEndZ = "</Z>";
				
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
			
				//... mit Berechnung MATH
				btemp = objFileIniTest.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH, true); 
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH + "'", btemp);
							
				boolean bUseExpressionGeneral = objFileIniTest.isExpressionEnabledGeneral();
				boolean bUseSolver = objFileIniTest.isSolverEnabledGeneral();
				
				objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
				IKernelConfigSectionEntryZZZ objEntry = objFileIniTest.getPropertyValue(sSection, sProperty);
				assertNotNull(objEntry);
				 
				sValue = objEntry.getValue();

				String sExpressionSolvedTemp = sExpressionSolved;
				//hier immer true if(bUseExpressionGeneral && bUseSolver && objEnumSurrounding.isSurroundingValueToRemove_On()) {
				if(bUseExpressionGeneral && bUseSolver){
					sExpressionSolvedTemp = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
				}
				sExpressionSolved = sExpressionSolvedTemp;
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
				
				String sTagStartZ = "<Z>";
				String sTagEndZ = "</Z>";
				
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
				
				boolean bUseExpressionGeneral = objFileIniTest.isExpressionEnabledGeneral();
				boolean bUseSolver = objFileIniTest.isSolverEnabledGeneral();
				
				objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
				sValue = objFileIniTest.getPropertyValue(sSection, sProperty).getValue();

				String sExpressionSolvedTemp = sExpressionSolved;
				//hier immer true if(bUseExpressionGeneral && bUseSolver && objEnumSurrounding.isSurroundingValueToRemove_On()) {
				if(bUseExpressionGeneral && bUseSolver){
					sExpressionSolvedTemp = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
				}
				sExpressionSolved = sExpressionSolvedTemp;
				assertEquals(sExpressionSolved, sValue);
				
				bReturn = true;
			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			}
		}//end main:
		return bReturn;
	}

	
	private boolean testCompute_MATH_IniUsed_FORMULA_unsolved_(String sSectionIn, String sPropertyIn, String sExpressionSolvedIn, String sTagSolvedIn) {
		boolean bReturn = false;
		
		main:{
			try {
				String sSection; String sProperty; String sExpressionSolved; String sTagSolved; String sValue;
				boolean btemp; 
				ReferenceZZZ<IKernelConfigSectionEntryZZZ> objSectionEntryReference;
				
				String sTagStartZ = "<Z>";
				String sTagEndZ = "</Z>";
				
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
				
				btemp = objFileIniTest.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER, true); //Ansonsten wird der Wert sofort ausgerechnet
				assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
										
				btemp = objFileIniTest.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA, false);                     //sollte wg. USEEXPRESSION_SOLVER = false egal sein.
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA + "'", btemp);
				
				btemp = objFileIniTest.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH,true); //ohne Mathematische Berechnung, sollte daher egal sein
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH + "'", btemp);
					
				boolean bUseExpressionGeneral = objFileIniTest.isExpressionEnabledGeneral();
				boolean bUseSolver = objFileIniTest.isSolverEnabledGeneral();
				
				objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
				sValue = objFileIniTest.getPropertyValue(sSection, sProperty).getValue();
				String sExpressionSolvedTemp = sExpressionSolved;
				//hier immer true if(bUseExpressionGeneral && bUseSolver && objEnumSurrounding.isSurroundingValueToRemove_On()) {
				if(bUseExpressionGeneral && bUseSolver){
					sExpressionSolvedTemp = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
				}
				sExpressionSolved = sExpressionSolvedTemp;
				assertEquals(sExpressionSolved, sValue);
				
				
				bReturn = true;
			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			}
		}//end main:
		return bReturn;
	}
	
	private boolean testCompute_MATH_IniUsed_FORMULA_MATH_unsolved_(String sSectionIn, String sPropertyIn, String sExpressionSolvedIn, String sTagSolvedIn) {
		boolean bReturn = false;
		
		main:{
			try {
				String sSection; String sProperty; String sExpressionSolved; String sTagSolved; String sValue;
				boolean btemp; 
				ReferenceZZZ<IKernelConfigSectionEntryZZZ> objSectionEntryReference;
				
				String sTagStartZ = "<Z>";
				String sTagEndZ = "</Z>";
				
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
										
				btemp = objFileIniTest.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA, true);                   
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA + "'", btemp);
				
				btemp = objFileIniTest.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH,false); //ohne Mathematische Berechnung, sollte daher egal sein
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH + "'", btemp);
								
				boolean bUseExpressionGeneral = objFileIniTest.isExpressionEnabledGeneral();
				boolean bUseSolver = objFileIniTest.isSolverEnabledGeneral();
				
				objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
				sValue = objFileIniTest.getPropertyValue(sSection, sProperty).getValue();

				String sExpressionSolvedTemp = sExpressionSolved;
				//hier immer true if(bUseExpressionGeneral && bUseSolver && objEnumSurrounding.isSurroundingValueToRemove_On()) {
				if(bUseExpressionGeneral && bUseSolver){
					sExpressionSolvedTemp = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
				}
				sExpressionSolved = sExpressionSolvedTemp;
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
	public void testCompute_Json_JsonMap(){

		try {	
			boolean btemp; String stemp;
			
			String sTagStartZ = "<Z>";
			String sTagEndZ = "</Z>";						
			String sExpression; String sExpressionSubstituted; String sExpressionSolved; String sTag=null; String sTagSolved=null; HashMap hmExpressionSolved;
					
			String sExpressionIn = KernelJsonMapIniSolverZZZTest.sEXPRESSION_JSONMAP01_DEFAULT;
			String sExpressionSubstitutedIn = KernelJsonMapIniSolverZZZTest.sEXPRESSION_JSONMAP01_DEFAULT;
			String sExpressionSolvedIn = KernelJsonMapIniSolverZZZTest.sEXPRESSION_JSONMAP01_SOLVED;
			
			
			//+++++++ VORGEZOGENER LETZTER FEHLERTEST START
			
			//1a)
			sExpression = sExpressionIn;
			sExpressionSubstituted = sExpressionSubstitutedIn;
			sExpressionSolved = sExpressionSolvedIn;			
			btemp = testCompute_Json_JsonMap_JsonUnsolved_(sExpression, sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved, EnumSetMappedTestSurroundingTypeZZZ.PARSE_KEEP, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
			
											
			//+++++++ VORGEZOGENER LETZTER FEHLERTEST ENDE
			
			//################################################################
			//### 1. Varianten JSON nicht aufzuloesen
			//#################################################################
			//1a)
			sExpression = sExpressionIn;
			sExpressionSubstituted = sExpressionSubstitutedIn;
			sExpressionSolved = sExpressionSubstitutedIn;			
			btemp = testCompute_Json_JsonMap_JsonUnsolved_(sExpression, sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved, EnumSetMappedTestSurroundingTypeZZZ.PARSE_KEEP, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
			
			//1b)
			sExpression = sExpressionIn;
			sExpressionSubstituted = sExpressionSubstitutedIn;;
			sExpressionSubstituted = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSubstituted, sTagStartZ, sTagEndZ);
			sExpressionSolved = sExpression;
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
			btemp = testCompute_Json_JsonMap_JsonUnsolved_(sExpression, sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved, EnumSetMappedTestSurroundingTypeZZZ.PARSE_REMOVE, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
			
			//1c)
			sExpression = sExpressionIn;			
			sExpressionSubstituted = sExpressionSubstitutedIn;
			sExpressionSolved = sExpressionSubstitutedIn;
			btemp = testCompute_Json_JsonMap_JsonUnsolved_(sExpression, sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved, EnumSetMappedTestSurroundingTypeZZZ.SOLVE_KEEP, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
			
			//1d)
			sExpression = sExpressionIn;
			sExpressionSubstituted = sExpressionSubstitutedIn;
			sExpressionSubstituted = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSubstituted, sTagStartZ, sTagEndZ);
			sExpressionSolved = sExpressionSubstitutedIn;			
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);			
			btemp = testCompute_Json_JsonMap_JsonUnsolved_(sExpression, sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved, EnumSetMappedTestSurroundingTypeZZZ.SOLVE_REMOVE, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
			
			
			//################################################################
			//### 2. Varianten JSON-HashMap nicht aufzuloesen
			//#################################################################
			
			//2a)
			sExpression = sExpressionIn;
			sExpressionSubstituted = sExpressionSubstitutedIn;
			sExpressionSolved = sExpressionSubstitutedIn;			
			btemp = testCompute_Json_JsonMap_JsonMapUnsolved_(sExpression, sExpressionSubstituted, sExpressionSolved, EnumSetMappedTestSurroundingTypeZZZ.PARSE_KEEP, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
			

			//2b)
			sExpression = sExpressionIn;
			sExpressionSubstituted = sExpressionSubstitutedIn;
			sExpressionSubstituted = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSubstituted, sTagStartZ, sTagEndZ);
			sExpressionSolved = sExpressionSubstitutedIn;
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
			btemp = testCompute_Json_JsonMap_JsonMapUnsolved_(sExpression, sExpressionSubstituted, sExpressionSolved, EnumSetMappedTestSurroundingTypeZZZ.PARSE_REMOVE, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
			
			
			//2c)
			sExpression = sExpressionIn;
			sExpressionSubstituted = sExpressionSubstitutedIn;
			sExpressionSolved = sExpressionSubstitutedIn;
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, KernelJsonIniSolverZZZ.sTAG_NAME);			
			btemp = testCompute_Json_JsonMap_JsonMapUnsolved_(sExpression, sExpressionSubstituted, sExpressionSolved, EnumSetMappedTestSurroundingTypeZZZ.SOLVE_KEEP, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);

			//2d)
			sExpression = sExpressionIn;
			sExpressionSubstituted = sExpressionSubstitutedIn;
			sExpressionSubstituted = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSubstituted, sTagStartZ, sTagEndZ);
			sExpressionSolved = sExpressionSubstitutedIn;
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, KernelJsonIniSolverZZZ.sTAG_NAME);
			btemp = testCompute_Json_JsonMap_JsonMapUnsolved_(sExpression, sExpressionSubstituted, sExpressionSolved, EnumSetMappedTestSurroundingTypeZZZ.SOLVE_REMOVE, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
			
			//################################################################
			//### 3. Varianten JSON-HashMap aufzuloesen
			//#################################################################
			
			//3a)
			sExpression = sExpressionIn;
			sExpressionSubstituted = sExpressionSubstitutedIn;
			hmExpressionSolved = new HashMap<String,String>();
			sExpressionSolved = sExpressionSolvedIn;		
			btemp = testCompute_Json_JsonMap_JsonMapSolved_(sExpression, sExpressionSubstituted, sExpressionSolved, hmExpressionSolved, EnumSetMappedTestSurroundingTypeZZZ.PARSE_KEEP, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
			
			//3b)
			sExpression = sExpressionIn;
			sExpressionSubstituted = sExpressionSubstitutedIn;
			sExpressionSubstituted = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSubstituted, sTagStartZ, sTagEndZ);
			hmExpressionSolved = new HashMap<String,String>();
			sExpressionSolved = sExpressionSolvedIn;
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
			btemp = testCompute_Json_JsonMap_JsonMapSolved_(sExpression, sExpressionSubstituted, sExpressionSolved, hmExpressionSolved, EnumSetMappedTestSurroundingTypeZZZ.PARSE_REMOVE, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
			
			//3c)
			sExpression = sExpressionIn;
			sExpressionSubstituted = sExpressionSubstitutedIn;
			hmExpressionSolved = new HashMap<String,String>();
			//sExpressionSolved = "<Z>{UIText01=TESTWERT2DO2JSON01, UIText02=TESTWERT2DO2JSON02}</Z>";
			//TODOGOON20250412;//Die Reihenfolge der Werte passt nicht 01 solte der erste Wert in der HashMap sein. Ich weiss Maps sind nicht sortiert, sollten sie aber....
			sExpressionSolved = sExpressionSolvedIn;
			btemp = testCompute_Json_JsonMap_JsonMapSolved_(sExpression, sExpressionSubstituted, sExpressionSolved, hmExpressionSolved, EnumSetMappedTestSurroundingTypeZZZ.SOLVE_KEEP, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
			
			//3d)
			sExpression = sExpressionIn;
			sExpressionSubstituted = sExpressionSubstitutedIn;
			sExpressionSubstituted = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSubstituted, sTagStartZ, sTagEndZ);
			hmExpressionSolved = new HashMap<String,String>();
			//sExpressionSolved = "<Z>{UIText01=TESTWERT2DO2JSON01, UIText02=TESTWERT2DO2JSON02}</Z>";
			//TODOGOON20250412;//Die Reihenfolge der Werte passt nicht 01 solte der erste Wert in der HashMap sein. Ich weiss Maps sind nicht sortiert, sollten sie aber....
			sExpressionSolved = sExpressionSolvedIn;
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
			btemp = testCompute_Json_JsonMap_JsonMapSolved_(sExpression, sExpressionSubstituted, sExpressionSolved, hmExpressionSolved, EnumSetMappedTestSurroundingTypeZZZ.SOLVE_REMOVE, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
			
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}		
	}
	
	private boolean testCompute_Json_JsonMap_JsonUnsolved_(String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, String sTagIn, String sTagSolvedIn, IEnumSetMappedTestSurroundingZZZ objEnumSurrounding, IEnumSetMappedTestCaseZZZ objEnumTestCase) {
		boolean bReturn = false;
		
		main:{
			try {
				boolean btemp; String stemp;
				
				String sExpression;  String sExpressionSubstituted; String sExpressionSolved; String sTag; String sTagSolved; 
				String sValue;				
				ReferenceZZZ<IKernelConfigSectionEntryZZZ> objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
				IKernelConfigSectionEntryZZZ objEntry=null;
				
				String sTagStartZ = "<Z>";
				String sTagEndZ = "</Z>";	
				
				sExpression = sExpressionIn;
				sExpressionSubstituted = sExpressionSubstitutedIn;
				sExpressionSolved = sExpressionSolvedIn; //hmExpressionSolved.toString();
				sTag=sTagIn;
				sTagSolved = sTagSolvedIn;
				
				IEnumSetMappedTestFlagsetZZZ objEnumFlagset = EnumSetMappedTestCaseFlagsetTypeZZZ.JSON_UNSOLVED;
								
				//####################################################################################
				//### EXPRESSION - NICHT JSON BEHANDLUNG .solve
				//####################################################################################
								
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
								
				boolean bUseExpressionGeneral = objExpressionHandler.isExpressionEnabledGeneral();
				boolean bUseSolver = objExpressionHandler.isSolverEnabledGeneral();
				boolean bUseParser = objExpressionHandler.isParserEnabledGeneral();
				
				//+++ ... solve verhält sich NICHT wie parse(), bei solve wird aufgeloest...
				if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.PARSE)) {					
					sValue = objExpressionHandler.parse(sExpression, objSectionEntryReference, objEnumSurrounding.isSurroundingValueToKeep_OnParse());
					
					String sExpressionSurroundedTemp = sExpressionSubstituted;
					if(bUseExpressionGeneral && bUseParser && objEnumSurrounding.isSurroundingValueToRemove_OnParse()) {
						sExpressionSurroundedTemp = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSurroundedTemp, sTagStartZ, sTagEndZ);
					}
					sExpressionSubstituted = sExpressionSurroundedTemp;
					assertEquals(sExpressionSubstituted, sValue);
					
					objEntry = objSectionEntryReference.get();
					assertNotNull(objEntry);
				}
				//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

				//+++ ... parse ist nicht solve... also wird hier nichts aufgeloest, aussser die Pfade
				if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.SOLVE)) {
					sValue = objExpressionHandler.solve(sExpression, objSectionEntryReference, objEnumSurrounding.isSurroundingValueToRemove_OnSolve());
					
					String sExpressionSurroundedTemp = sExpressionSolved;
					if(bUseExpressionGeneral && bUseSolver && objEnumSurrounding.isSurroundingValueToRemove_OnSolve()) {
						sExpressionSurroundedTemp = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSurroundedTemp, sTagStartZ, sTagEndZ);
					}
					sExpressionSolved = sExpressionSurroundedTemp;
					assertEquals(sExpressionSolved, sValue);
					
					objEntry = objSectionEntryReference.get();
					assertNotNull(objEntry);
				}
				
				//+++++++++++++++++++++++++++++++++++++++++++++++++++
				
				//Nutze eine Sammlung von assert Methoden, die ein objEntry als input hat.
				//und in der die verschiedenen stati für den unexpressed, unsubstituted, substituted, unsolved, etc Fall stehen.
				btemp = TestUtilAsTestZZZ.assertFileIniEntry(objExpressionHandler, objEnumFlagset, objEnumSurrounding, objEnumTestCase, objEntry, sExpression, sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved);
				assertTrue(btemp);

				
				bReturn = true;
			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			}
		}//end main:
		return bReturn;
	}
	
	
boolean testCompute_Json_JsonMap_JsonMapUnsolved_(String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, IEnumSetMappedTestSurroundingZZZ objEnumSurrounding, IEnumSetMappedTestCaseZZZ objEnumTestCase) {
		boolean bReturn = false;
		
		main:{
			try {
				boolean btemp; String stemp;
				
				String sExpression; String sExpressionSubstituted; HashMap hmExpressionSolved; String sExpressionSolved; String sTag=null; String sTagSolved=null;
				String sValue;				
				IKernelConfigSectionEntryZZZ objEntry=null; ReferenceZZZ<IKernelConfigSectionEntryZZZ>objSectionEntryReference=null;
				
				sExpression = sExpressionIn;
				sExpressionSubstituted = sExpressionSubstitutedIn;
				sExpressionSolved = sExpressionSolvedIn; //hmExpressionSolved.toString();
				
				String sTagStartZ = "<Z>";
				String sTagEndZ = "</Z>";
				
				IEnumSetMappedTestFlagsetZZZ objEnumFlagset = EnumSetMappedTestCaseFlagsetTypeZZZ.JSONMAP_UNSOLVED;
				
				//####################################################################################
				//### EXPRESSION - NICHT JSONMAP BEHANDLUNG .solve
				//####################################################################################
								
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
					
				boolean bUseExpressionGeneral = objExpressionHandler.isExpressionEnabledGeneral();
				boolean bUseSolver = objExpressionHandler.isSolverEnabledGeneral();
				boolean bUseParser = objExpressionHandler.isParserEnabledGeneral();
				
				//+++ ... solve verhält sich NICHT wie parse(), bei solve wird aufgeloest...
				if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.PARSE)) {
					objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
					sValue = objExpressionHandler.parse(sExpression, objSectionEntryReference, objEnumSurrounding.isSurroundingValueToKeep_OnParse());
					
					String sExpressionSurroundedTemp = sExpressionSubstituted;
					if(bUseExpressionGeneral && bUseParser && objEnumSurrounding.isSurroundingValueToRemove_OnParse()) {
						sExpressionSurroundedTemp = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSurroundedTemp, sTagStartZ, sTagEndZ);
					}
					sExpressionSubstituted = sExpressionSurroundedTemp;
					assertEquals(sExpressionSubstituted, sValue);
										
					objEntry = objSectionEntryReference.get();
					assertNotNull(objEntry);
				}
				//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

				//+++ ... parse ist nicht solve... also wird hier nichts aufgeloest, aussser die Pfade
				if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.SOLVE)) {
					objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
					sValue = objExpressionHandler.solve(sExpression, objSectionEntryReference, objEnumSurrounding.isSurroundingValueToRemove_OnSolve());
					
					String sExpressionSurroundedTemp = sExpressionSolved;
					if(bUseExpressionGeneral && bUseSolver && objEnumSurrounding.isSurroundingValueToRemove_OnSolve()) {
						sExpressionSurroundedTemp = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSurroundedTemp, sTagStartZ, sTagEndZ);
					}
					sExpressionSolved = sExpressionSurroundedTemp;
					assertEquals(sExpressionSolved, sValue);
										
					objEntry = objSectionEntryReference.get();
					assertNotNull(objEntry);
				}
				//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
				
				//Nutze eine Sammlung von assert Methoden, die ein objEntry als input hat.
				//und in der die verschiedenen stati für den unexpressed, unsubstituted, substituted, unsolved, etc Fall stehen.
				btemp = TestUtilAsTestZZZ.assertFileIniEntry(objExpressionHandler, objEnumFlagset, objEnumSurrounding, objEnumTestCase, objEntry, sExpression, sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved);
				assertTrue(btemp);

				
				bReturn = true;
			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			}
		}//end main:
		return bReturn;
	}
	
	private boolean testCompute_Json_JsonMap_JsonMapSolved_(String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, HashMap hmExpressionSolvedIn, IEnumSetMappedTestSurroundingZZZ objEnumSurrounding, IEnumSetMappedTestCaseZZZ objEnumTestCase) {
		boolean bReturn = false;
		
		main:{
			try {
				boolean btemp; String stemp;
				
				String sExpression; String sExpressionSubstituted; String sExpressionSolved; String sTag=null; String sTagSolved=null; HashMap hmExpressionSolved; 
				String sValue;				
				IKernelConfigSectionEntryZZZ objEntry=null; ReferenceZZZ<IKernelConfigSectionEntryZZZ>objSectionEntryReference=null;
				
				String sTagStartZ = "<Z>";
				String sTagEndZ = "</Z>";	
				
				sExpression = sExpressionIn;
				sExpressionSubstituted = sExpressionSubstitutedIn;
				hmExpressionSolved = hmExpressionSolvedIn;
				sExpressionSolved = sExpressionSolvedIn; //hmExpressionSolved.toString();
				
				IEnumSetMappedTestFlagsetZZZ objEnumFlagset = EnumSetMappedTestCaseFlagsetTypeZZZ.JSONMAP_SOLVED;
				
				//####################################################################################
				//### EXPRESSION - Kompletter .solve
				//####################################################################################
								
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
				
				boolean bUseExpressionGeneral = objExpressionHandler.isExpressionEnabledGeneral();
				boolean bUseSolver = objExpressionHandler.isSolverEnabledGeneral();
				boolean bUseParser = objExpressionHandler.isParserEnabledGeneral();
				
				//+++ ... parse ist nicht solve... also wird hier nichts aufgeloest, aussser die Pfade
				if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.PARSE)) {	
					objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
					sValue = objExpressionHandler.parse(sExpression, objSectionEntryReference, objEnumSurrounding.isSurroundingValueToKeep_OnParse());
					
					String sExpressionSurroundedTemp = sExpressionSubstituted;
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
					objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
					sValue = objExpressionHandler.solve(sExpression, objSectionEntryReference, objEnumSurrounding.isSurroundingValueToRemove_OnSolve());
					
					String sExpressionSurroundedTemp = sExpressionSolved;
					if(bUseExpressionGeneral && bUseSolver && objEnumSurrounding.isSurroundingValueToRemove_OnSolve()) {
						sExpressionSurroundedTemp = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSurroundedTemp, sTagStartZ, sTagEndZ);
					}
					sExpressionSolved = sExpressionSurroundedTemp;
					assertEquals(sExpressionSolved, sValue);
					
					objEntry = objSectionEntryReference.get();
					assertNotNull(objEntry);
				}
				//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
								
				//Nutze eine Sammlung von assert Methoden, die ein objEntry als input hat.
				//und in der die verschiedenen stati für den unexpressed, unsubstituted, substituted, unsolved, etc Fall stehen.
				btemp = TestUtilAsTestZZZ.assertFileIniEntry(objExpressionHandler, objEnumFlagset, objEnumSurrounding, objEnumTestCase, objEntry, sExpression, sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved);
				assertTrue(btemp);
				
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
	public void testCompute_Call_CallJava(){
		
		boolean btemp; int itemp;
		
		String sSection; String sProperty;
		String sExpression; String sExpressionSubstituted; String sExpressionSolved; String sTag=null; String sTagSolved=null; 
		IKernelConfigSectionEntryZZZ objEntry=null; ReferenceZZZ<IKernelConfigSectionEntryZZZ>objSectionEntryReference=null;
	
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
			sExpression = "<Z><Z:Call><Z:Java><Z:Class><Z>xyz</Z></Z:Class><Z:Method><Z>abcde</Z></Z:Method></Z:Java></Z:Call></Z>"; //INI-Pfade werden trotzdem ersetzt
			sExpressionSolved = "<Z:Call><Z:Java><Z:Class><Z>xyz</Z></Z:Class><Z:Method><Z>abcde</Z></Z:Method></Z:Java></Z:Call>"; //INI-Pfade werden trotzdem ersetzt //INI-Pfade werden trotzdem ersetzt
			sValue = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpression, sTagStartZ, sTagEndZ, false);
			assertEquals(sExpressionSolved, sValue);
			
		
		
			//+++++++ VORGEZOGENER LETZTER FEHLERTEST START
			
			//+++ C) Ohne jegliche CallJava-Berechnung, aber der generelle Solver ist aktiviert
			//Ca) Merke: Beim Parsen bleiben alle Tags drin
			sExpression = KernelJavaCallIniSolverZZZTest.sEXPRESSION_CALL01_DEFAULT;
			sExpressionSubstituted = KernelJavaCallIniSolverZZZTest.sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT;
			sExpressionSolved = sExpressionSubstituted;			
			btemp = testCompute_Call_CallJava_unsolved_(sExpression, sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved, EnumSetMappedTestSurroundingTypeZZZ.PARSE_KEEP, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
							
			//+++++++ VORGEZOGENER LETZTER FEHLERTEST ENDE
			
			//###########################
		    //### objExpression
			//#########################
			
				
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//+++ A) Ohne jegliche Expression-Berechnung
			//Aa)
			sExpression = KernelJavaCallIniSolverZZZTest.sEXPRESSION_CALL01_DEFAULT;
			sExpressionSubstituted = sExpression;
			sExpressionSolved = sExpression;
			btemp = testCompute_Call_CallJava_unexpressed_(sExpression, sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved, EnumSetMappedTestSurroundingTypeZZZ.PARSE_KEEP, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
			
			//Ab)
			sExpression = KernelJavaCallIniSolverZZZTest.sEXPRESSION_CALL01_DEFAULT;
			sExpressionSubstituted = sExpression;
			sExpressionSolved = sExpression;			
			btemp = testCompute_Call_CallJava_unexpressed_(sExpression, sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved, EnumSetMappedTestSurroundingTypeZZZ.PARSE_REMOVE, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
			
			//Ac)
			sExpression = KernelJavaCallIniSolverZZZTest.sEXPRESSION_CALL01_DEFAULT;
			sExpressionSubstituted = sExpression;
			sExpressionSolved = sExpression; 			
			btemp = testCompute_Call_CallJava_unexpressed_(sExpression, sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved, EnumSetMappedTestSurroundingTypeZZZ.SOLVE_KEEP, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
				
			//Ad)
			sExpression = KernelJavaCallIniSolverZZZTest.sEXPRESSION_CALL01_DEFAULT;
			sExpressionSubstituted = sExpression;
			sExpressionSolved = sExpression;
			btemp = testCompute_Call_CallJava_unexpressed_(sExpression, sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved, EnumSetMappedTestSurroundingTypeZZZ.SOLVE_REMOVE, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//+++ B) Ohne Solver-Berechung
			//Ba)
			sExpression = KernelJavaCallIniSolverZZZTest.sEXPRESSION_CALL01_DEFAULT;
			sExpressionSubstituted = KernelJavaCallIniSolverZZZTest.sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT;
			sExpressionSolved = "<Z><Z:Call><Z:Java><Z:Class>" + sClassName + "</Z:Class><Z:Method>" + sMethodName +"</Z:Method></Z:Java></Z:Call></Z>"; //INI-Pfade werden trotzdem ersetzt
			btemp = testCompute_Call_CallJava_Solver_unsolved_(sExpression, sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved, EnumSetMappedTestSurroundingTypeZZZ.PARSE_KEEP, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
			
			//Bb) Merke: Ohne Solver-Berechnung bleibt der Z-Tag immer drin.
			sExpression = KernelJavaCallIniSolverZZZTest.sEXPRESSION_CALL01_DEFAULT;
			sExpressionSubstituted = KernelJavaCallIniSolverZZZTest.sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT;
			sExpressionSolved = sExpressionSubstituted;
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ, false);
			btemp = testCompute_Call_CallJava_Solver_unsolved_(sExpression, sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved, EnumSetMappedTestSurroundingTypeZZZ.PARSE_REMOVE, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
				
			//Bc) Merke: Ohne Solver-Berechnung bleibt der Z-Tag immer drin. 
			sExpression = KernelJavaCallIniSolverZZZTest.sEXPRESSION_CALL01_DEFAULT;
			sExpressionSubstituted = KernelJavaCallIniSolverZZZTest.sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT;			
			sExpressionSolved = sExpressionSubstituted;
			btemp = testCompute_Call_CallJava_Solver_unsolved_(sExpression, sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved, EnumSetMappedTestSurroundingTypeZZZ.SOLVE_KEEP, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
				
			//Bd)
			sExpression = KernelJavaCallIniSolverZZZTest.sEXPRESSION_CALL01_DEFAULT;
			sExpressionSubstituted = KernelJavaCallIniSolverZZZTest.sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT;
			sExpressionSolved = sExpressionSubstituted;			
			//ohne solver bleibt der Z-Tag drin  sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ, false);
			btemp = testCompute_Call_CallJava_Solver_unsolved_(sExpression, sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved, EnumSetMappedTestSurroundingTypeZZZ.SOLVE_REMOVE, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
			

			
			//+++ C) Ohne jegliche CallJava-Berechnung, aber der generelle Solver ist aktiviert
			//Ca) Merke: Beim Parsen bleiben alle Tags drin
			sExpression = KernelJavaCallIniSolverZZZTest.sEXPRESSION_CALL01_DEFAULT;
			sExpressionSubstituted = KernelJavaCallIniSolverZZZTest.sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT;
			sExpressionSolved = sExpressionSubstituted;			
			btemp = testCompute_Call_CallJava_unsolved_(sExpression, sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved, EnumSetMappedTestSurroundingTypeZZZ.PARSE_KEEP, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
			
			//Cb)Merke; Beim Parsen bleiben alle Tags drin, nur der umbebende Z-Tag soll jetzt raus
			sExpression = KernelJavaCallIniSolverZZZTest.sEXPRESSION_CALL01_DEFAULT;
			sExpressionSubstituted = KernelJavaCallIniSolverZZZTest.sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT;
			sExpressionSolved = sExpressionSubstituted;			
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ, false);  //Z-Tag raus, weil gewuenscht
			btemp = testCompute_Call_CallJava_unsolved_(sExpression, sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved, EnumSetMappedTestSurroundingTypeZZZ.PARSE_REMOVE, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
			
			//Cc) Merke: Es wird aber trotzdem eine CALL-Berechnung gemacht, darum ist der Tag dann im Enderegebnis raus.
			sExpression = KernelJavaCallIniSolverZZZTest.sEXPRESSION_CALL01_DEFAULT;
			sExpressionSubstituted = KernelJavaCallIniSolverZZZTest.sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT;
			sExpressionSolved = sExpressionSubstituted;
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, KernelCallIniSolverZZZ.sTAG_NAME);//Call Tag raus, der ist aktiviert
			//der eigene Tag bleibt drin, weil deaktiviert sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, KernelJavaCallIniSolverZZZ.sTAG_NAME);			
			//sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ, false);  //Z-Tag raus, weil gewuenscht
			btemp = testCompute_Call_CallJava_unsolved_(sExpression, sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved, EnumSetMappedTestSurroundingTypeZZZ.SOLVE_KEEP, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
						
			//Cd) Merke: Es wird aber trotzdem eine CALL-Berechnung gemacht, darum ist der Tag dann im Enderegebnis raus.
			sExpression = KernelJavaCallIniSolverZZZTest.sEXPRESSION_CALL01_DEFAULT;
			sExpressionSubstituted = KernelJavaCallIniSolverZZZTest.sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT;
			sExpressionSolved = sExpressionSubstituted;
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, KernelCallIniSolverZZZ.sTAG_NAME);//Call Tag raus, der ist aktiviert
			//der eigene Tag bleibt drin, weil deaktiviert sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, KernelCallIniSolverZZZ.sTAG_NAME); 
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ, false);  //Z-Tag raus, weil gewuenscht
			btemp = testCompute_Call_CallJava_unsolved_(sExpression, sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved, EnumSetMappedTestSurroundingTypeZZZ.SOLVE_REMOVE, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
			
			
			
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//+++ E) Mit CALL-Berechnung
			//Ea)
			sExpression = KernelJavaCallIniSolverZZZTest.sEXPRESSION_CALL01_DEFAULT;
			sExpressionSubstituted = KernelJavaCallIniSolverZZZTest.sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT;
			sExpressionSolved = sExpressionSubstituted; // "<Z><Z:Call><Z:Java><Z:Class><Z>basic.zBasic.util.machine.EnvironmentZZZ</Z></Z:Class><Z:Method><Z>getHostName</Z></Z:Method></Z:Java></Z:Call></Z>";						
			btemp = testCompute_Call_CallJava_(sExpression, sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved, EnumSetMappedTestSurroundingTypeZZZ.PARSE_KEEP, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
			
			//Eb)
			sExpression = KernelJavaCallIniSolverZZZTest.sEXPRESSION_CALL01_DEFAULT;
			sExpressionSubstituted = KernelJavaCallIniSolverZZZTest.sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT;
			sExpressionSolved = sExpressionSubstituted; //sExpressionSolved = "<Z:Call><Z:Java><Z:Class>basic.zBasic.util.machine.EnvironmentZZZ</Z:Class><Z:Method>getHostName</Z:Method></Z:Java></Z:Call>";
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
			btemp = testCompute_Call_CallJava_(sExpression, sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved, EnumSetMappedTestSurroundingTypeZZZ.PARSE_REMOVE, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
			
			//Ec)
			sExpression = KernelJavaCallIniSolverZZZTest.sEXPRESSION_CALL01_DEFAULT;
			sExpressionSubstituted = KernelJavaCallIniSolverZZZTest.sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT;
			sExpressionSolved = "<Z>" + sHostName + "</Z>";			
			btemp = testCompute_Call_CallJava_(sExpression, sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved, EnumSetMappedTestSurroundingTypeZZZ.SOLVE_KEEP, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
			
			//Ed)		
			sExpression = KernelJavaCallIniSolverZZZTest.sEXPRESSION_CALL01_DEFAULT;
			sExpressionSubstituted = KernelJavaCallIniSolverZZZTest.sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT;
			sExpressionSolved = sHostName;
			btemp = testCompute_Call_CallJava_(sExpression, sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved, EnumSetMappedTestSurroundingTypeZZZ.SOLVE_REMOVE, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
			
			
			//#####################################
			//### F) INI FILE
			//### Merke: Auf dieser obersten Ebene kann man NICHT steuern "mit oder ohne umgebende Tags".
			//#####################################
									
			//Fa) ohne EXPRESSION - Berechnung
			sSection = "Section for testCall";
			sProperty = "WertCalled";
			sExpression = KernelJavaCallIniSolverZZZTest.sEXPRESSION_CALL01_DEFAULT;
			sExpressionSubstituted = sExpression;
			sExpressionSolved = sExpression;
			sExpressionSolved = KernelJavaCallIniSolverZZZTest.sEXPRESSION_CALL01_DEFAULT; // "<Z><Z:Call><Z:Java><Z:Class>{[ArgumentSection for testCallComputed]JavaClass}</Z:Class><Z:Method>{[ArgumentSection for testCallComputed]JavaMethod}</Z:Method></Z:Java></Z:Call></Z>";
			btemp = testCompute_Call_CallJava_IniUsed_unexpressed_(sSection, sProperty, sExpression, sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved);
		
				
			//Fb ) ohne SOLVER - Berechnung
			sSection = "Section for testCall";
			sProperty = "WertCalled";
			sExpression = KernelJavaCallIniSolverZZZTest.sEXPRESSION_CALL01_DEFAULT;
			sExpressionSubstituted = KernelJavaCallIniSolverZZZTest.sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT;
			sExpressionSolved = sExpressionSubstituted;
			sExpressionSolved = KernelJavaCallIniSolverZZZTest.sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT;    //"<Z:Call><Z:Java><Z:Class>basic.zBasic.util.machine.EnvironmentZZZ</Z:Class><Z:Method>getHostName</Z:Method></Z:Java></Z:Call>";
			btemp = testCompute_Call_CallJava_IniUsed_unsolved_(sSection, sProperty, sExpression, sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved);	
	
			//Fc) mit CALL-Berechnung
			sSection = "Section for testCall";
			sProperty = "WertCalled";
			sExpression = KernelJavaCallIniSolverZZZTest.sEXPRESSION_CALL01_DEFAULT;
			sExpressionSubstituted = KernelJavaCallIniSolverZZZTest.sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT;
			sExpressionSolved = sHostName;
			sExpressionSolved=EnvironmentZZZ.getHostName();
			btemp = testCompute_Call_CallJava_IniUsed_(sSection, sProperty, sExpression, sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved);
							
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}		
	}
	
	
	private boolean testCompute_Call_CallJava_unexpressed_(String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, String sTagIn, String sTagSolvedIn, IEnumSetMappedTestSurroundingZZZ objEnumSurrounding, IEnumSetMappedTestCaseZZZ objEnumTestCase) {
		boolean bReturn = false;
		try {
			boolean btemp; 
			
			String sExpression;  String sExpressionSubstituted; String sExpressionSolved; String sTag=null; String sTagSolved=null; 
			String sValue; 		
			ReferenceZZZ<IKernelConfigSectionEntryZZZ> objSectionEntryReference=null; IKernelConfigSectionEntryZZZ objEntry=null;

			String sTagStartZ = "<Z>";
			String sTagEndZ = "</Z>";	
			
			sExpression = sExpressionIn;
			sExpressionSubstituted = sExpressionSubstitutedIn;
			sExpressionSolved = sExpressionSolvedIn;
			
			IEnumSetMappedTestFlagsetZZZ objEnumFlagset = EnumSetMappedTestCaseFlagsetTypeZZZ.UNEXPRESSED;
			
			//####################################################################################
			//### EXPRESSION - NICHT EXPRESSION BEHANDLUNG .solve
			//####################################################################################
			
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
			
			boolean bUseExpressionGeneral = objExpressionHandler.isExpressionEnabledGeneral();
			boolean bUseSolver = objExpressionHandler.isSolverEnabledGeneral();
			boolean bUseParser = objExpressionHandler.isParserEnabledGeneral();
			
			//+++ ... parse ist nicht solve... also wird hier nichts aufgeloest, aussser die Pfade
			if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.PARSE)){
				objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
				sValue = objExpressionHandler.parse(sExpression, objSectionEntryReference, objEnumSurrounding.isSurroundingValueToRemove_OnParse());
				
				String sExpressionSurroundedTemp = sExpressionSubstituted;
				if(bUseExpressionGeneral && bUseParser && objEnumSurrounding.isSurroundingValueToRemove_OnParse()) {
					sExpressionSurroundedTemp = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSurroundedTemp, sTagStartZ, sTagEndZ);
				}
				sExpressionSubstituted = sExpressionSurroundedTemp;
				assertEquals(sExpressionSubstituted, sValue);
				
				objEntry = objSectionEntryReference.get();
				assertNotNull(objEntry);
//				assertFalse(objEntry.isCall()); 		//Beim Solven erst wuerde der CallIniSolver erst aufgerufen, mit seinem parse(). Wert kommt aus parse()
//				assertFalse(objEntry.isJavaCall());	    //Beim Solven erst wuerde der JavaCallIniSolver erst aufgerufen, mit seinem parse(). Wert kommt aus parse()
//				assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingClassname() + "'", objEntry.getCallingClassname());
//				assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingMethodname() + "'", objEntry.getCallingMethodname());
			}
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
				
			//+++ ... solve verhält sich NICHT wie parse(), bei solve wird aufgeloest...
			if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.SOLVE)){
				objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
				sValue = objExpressionHandler.solve(sExpression, objSectionEntryReference, objEnumSurrounding.isSurroundingValueToRemove_OnSolve());
				
				String sExpressionSurroundedTemp = sExpressionSolved;
				if(bUseExpressionGeneral && bUseSolver && objEnumSurrounding.isSurroundingValueToRemove_OnSolve()) {
					sExpressionSurroundedTemp = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSurroundedTemp, sTagStartZ, sTagEndZ);
				}
				sExpressionSolved = sExpressionSurroundedTemp;
				assertEquals(sExpressionSolved, sValue);
				
				objEntry = objSectionEntryReference.get();
				assertNotNull(objEntry);
//				assertFalse(objEntry.isCall()); 		//Beim Solven erst wuerde der CallIniSolver erst aufgerufen, mit seinem parse(). Wert kommt aus parse()
//				assertFalse(objEntry.isJavaCall());	    //Beim Solven erst wuerde der JavaCallIniSolver erst aufgerufen, mit seinem parse(). Wert kommt aus parse()
//				assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingClassname() + "'", objEntry.getCallingClassname());
//				assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingMethodname() + "'", objEntry.getCallingMethodname());
			}
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			
			//Nutze eine Sammlung von assert Methoden, die ein objEntry als input hat.
			//und in der die verschiedenen stati für den unexpressed, unsubstituted, substituted, unsolved, etc Fall stehen.
			btemp = TestUtilAsTestZZZ.assertFileIniEntry(objExpressionHandler, objEnumFlagset, objEnumSurrounding, objEnumTestCase, objEntry, sExpression, sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved);
			assertTrue(btemp);
			
			
			bReturn = true;
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}
		return bReturn;
	}

	
	private boolean testCompute_Call_CallJava_unsolved_(String sExpressionIn, String sExpressionSubstitutedIn,String sExpressionSolvedIn, String sTagIn, String sTagSolvedIn, IEnumSetMappedTestSurroundingZZZ objEnumSurrounding, IEnumSetMappedTestCaseZZZ objEnumTestCase) {
		boolean bReturn = false;
		try {
			boolean btemp; 
			
			String sExpression;  String sExpressionSubstituted; String sExpressionSolved; String sTag=null; String sTagSolved=null;
			String sValue; 		
			ReferenceZZZ<IKernelConfigSectionEntryZZZ> objSectionEntryReference=null; IKernelConfigSectionEntryZZZ objEntry=null;
			
			String sTagStartZ = "<Z>";
			String sTagEndZ = "</Z>";
			
			sExpression = sExpressionIn;
			sExpressionSubstituted = sExpressionSubstitutedIn;
			sExpressionSolved = sExpressionSolvedIn;
			
			IEnumSetMappedTestFlagsetZZZ objEnumFlagset = EnumSetMappedTestCaseFlagsetTypeZZZ.JAVACALL_UNSOLVED;
			
			//####################################################################################
			//### EXPRESSION - NICHT EXPRESSION BEHANDLUNG .solve
			//####################################################################################
			
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
			
			boolean bUseExpressionGeneral = objExpressionHandler.isExpressionEnabledGeneral();
			boolean bUseSolver = objExpressionHandler.isSolverEnabledGeneral();
			boolean bUseParser = objExpressionHandler.isParserEnabledGeneral();
			
			//+++ ... parse ist nicht solve... also wird hier nichts aufgeloest, aussser die Pfade
			if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.PARSE)){	
				objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
				sValue = objExpressionHandler.parse(sExpression, objSectionEntryReference, objEnumSurrounding.isSurroundingValueToKeep_OnParse());
				
				String sExpressionSurroundedTemp = sExpressionSubstituted;
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
			if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.SOLVE)){
				objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
				sValue = objExpressionHandler.solve(sExpression, objSectionEntryReference, objEnumSurrounding.isSurroundingValueToRemove_OnSolve());

				String sExpressionSurroundedTemp = sExpressionSolved;
				if(bUseExpressionGeneral && bUseSolver && objEnumSurrounding.isSurroundingValueToRemove_OnSolve()) {
					sExpressionSurroundedTemp = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSurroundedTemp, sTagStartZ, sTagEndZ);
				}
				sExpressionSolved = sExpressionSurroundedTemp;
				assertEquals(sExpressionSolved, sValue);
				
				objEntry = objSectionEntryReference.get();
				assertNotNull(objEntry);
			}
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			
			//Nutze eine Sammlung von assert Methoden, die ein objEntry als input hat.
			//und in der die verschiedenen stati für den unexpressed, unsubstituted, substituted, unsolved, etc Fall stehen.
			btemp = TestUtilAsTestZZZ.assertFileIniEntry(objExpressionHandler, objEnumFlagset, objEnumSurrounding, objEnumTestCase, objEntry, sExpression, sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved);
			assertTrue(btemp);
						
			bReturn = true;
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}
		return bReturn;
	}
	
	private boolean testCompute_Call_CallJava_Solver_unsolved_(String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, String sTagIn, String sTagSolvedIn, IEnumSetMappedTestSurroundingZZZ objEnumSurrounding,  IEnumSetMappedTestCaseZZZ objEnumTestCase) {
		boolean bReturn = false;
		try {
			boolean btemp; 
			
			String sExpression;  String sExpressionSubstituted; String sExpressionSolved; String sTag=null; String sTagSolved=null; 
			String sValue; 		
			ReferenceZZZ<IKernelConfigSectionEntryZZZ> objSectionEntryReference=null; IKernelConfigSectionEntryZZZ objEntry=null;
			
			String sTagStartZ = "<Z>";
			String sTagEndZ = "</Z>";
			
			sExpression = sExpressionIn;
			sExpressionSubstituted = sExpressionSubstitutedIn;
			sExpressionSolved = sExpressionSolvedIn;
			
			IEnumSetMappedTestFlagsetZZZ objEnumFlagset = EnumSetMappedTestCaseFlagsetTypeZZZ.UNSOLVED;
			
			//####################################################################################
			//### EXPRESSION - CALL NICHT .solve
			//####################################################################################

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

			boolean bUseExpressionGeneral = objExpressionHandler.isExpressionEnabledGeneral();
			boolean bUseSolver = objExpressionHandler.isSolverEnabledGeneral();
			boolean bUseParser = objExpressionHandler.isParserEnabledGeneral();
			
			//+++ ... parse ist nicht solve... also wird hier nichts aufgeloest, aussser die Pfade
			if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.PARSE)) {	
				objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
				sValue = objExpressionHandler.parse(sExpression, objSectionEntryReference, objEnumSurrounding.isSurroundingValueToKeep_OnParse());
				
				String sExpressionSurroundedTemp = sExpressionSubstituted;
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
				objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
				sValue = objExpressionHandler.solve(sExpression, objSectionEntryReference, objEnumSurrounding.isSurroundingValueToRemove_OnSolve());

				String sExpressionSurroundedTemp = sExpressionSolved;
				if(bUseExpressionGeneral && bUseSolver && objEnumSurrounding.isSurroundingValueToRemove_OnSolve()) {
					sExpressionSurroundedTemp = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSurroundedTemp, sTagStartZ, sTagEndZ);
				}
				sExpressionSolved = sExpressionSurroundedTemp;
				assertEquals(sExpressionSolved, sValue);
				
				objEntry = objSectionEntryReference.get();
				assertNotNull(objEntry);
			}
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

			//Nutze eine Sammlung von assert Methoden, die ein objEntry als input hat.
			//und in der die verschiedenen stati für den unexpressed, unsubstituted, substituted, unsolved, etc Fall stehen.
			btemp = TestUtilAsTestZZZ.assertFileIniEntry(objExpressionHandler, objEnumFlagset, objEnumSurrounding, objEnumTestCase, objEntry, sExpression, sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved);
			assertTrue(btemp);
			
			bReturn = true;
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}
		return bReturn;
	}
	
	private boolean testCompute_Call_CallJava_( String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, String sTagIn, String sTagSolvedIn, IEnumSetMappedTestSurroundingZZZ objEnumSurrounding, IEnumSetMappedTestCaseZZZ objEnumTestCase) {
		boolean bReturn = false;
		try {
			boolean btemp; 
			
			String sExpression;  String sExpressionSubstituted; String sExpressionSolved; String sTag; String sTagSolved; 
			String sValue; 		
			IKernelConfigSectionEntryZZZ objEntry=null;
			ReferenceZZZ<IKernelConfigSectionEntryZZZ> objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
			
			String sTagStartZ = "<Z>";
			String sTagEndZ = "</Z>";
			
			sExpression = sExpressionIn;
			sExpressionSubstituted = sExpressionSubstitutedIn;
			sExpressionSolved = sExpressionSolvedIn;
			sTag = sTagIn;
			sTagSolved = sTagSolvedIn;
						
			IEnumSetMappedTestFlagsetZZZ objEnumFlagset = EnumSetMappedTestCaseFlagsetTypeZZZ.JAVACALL_SOLVED;
		
			//####################################################################################
			//### EXPRESSION .solve
			//####################################################################################
				
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
			
			boolean bUseExpressionGeneral = objExpressionHandler.isExpressionEnabledGeneral();
			boolean bUseSolver = objExpressionHandler.isSolverEnabledGeneral();
			boolean bUseParser = objExpressionHandler.isParserEnabledGeneral();
			
			//+++ ... parse ist nicht solve... also wird hier nichts aufgeloest, aussser die Pfade substituiert.
			if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.PARSE)) {									
				sValue = objExpressionHandler.parse(sExpression, objSectionEntryReference, objEnumSurrounding.getSurroundingValueUsedForMethod());
				
				String sExpressionSurroundedTemp = sExpressionSubstituted;
				if(bUseExpressionGeneral && bUseParser && objEnumSurrounding.isSurroundingValueToRemove_OnParse()) {
					sExpressionSurroundedTemp = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSurroundedTemp, sTagStartZ, sTagEndZ);
				}
				sExpressionSubstituted = sExpressionSurroundedTemp;
				assertEquals(sExpressionSubstituted, sValue);
				
				objEntry = objSectionEntryReference.get();
				assertNotNull(objEntry);
//				assertTrue(objEntry.isCall()); 		//Beim updateCustom() wird im ExpressionHandler alles geprueft. Beim Solven erst wuerde der CallIniSolver erst aufgerufen, mit seinem parse(). Wert kommt aus parse()
//				assertTrue(objEntry.isJavaCall());	//Beim updateCustom() wird im ExpressionHandler alles geprueft. Beim Solven erst wuerde der JavaCallIniSolver erst aufgerufen, mit seinem parse(). Wert kommt aus parse()
//				assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingClassname() + "'", objEntry.getCallingClassname());
//				assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingMethodname() + "'", objEntry.getCallingMethodname());
			}
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
				
			//+++ ... solve verhält sich NICHT wie parse(), bei solve wird aufgeloest...
			//+++ ... parse ist nicht solve... also wird hier nichts aufgeloest, aussser die Pfade substituiert.
			if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.SOLVE)) {
				sValue = objExpressionHandler.solve(sExpression, objSectionEntryReference, objEnumSurrounding.getSurroundingValueUsedForMethod());

				String sExpressionSurroundedTemp = sExpressionSolved;
				if(bUseExpressionGeneral && bUseSolver && objEnumSurrounding.isSurroundingValueToRemove_OnSolve()) {
					sExpressionSurroundedTemp = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSurroundedTemp, sTagStartZ, sTagEndZ);
				}
				sExpressionSolved = sExpressionSurroundedTemp;
				assertEquals(sExpressionSolved, sValue);
				
				objEntry = objSectionEntryReference.get();
				assertNotNull(objEntry);
//				assertTrue(objEntry.isCall());		//Beim updateCustom() wird im ExpressionHandler alles geprueft. Beim Solven erst wuerde der CallIniSolver erst aufgerufen, mit seinem parse(). Wert kommt aus parse()
//				assertTrue(objEntry.isJavaCall());  //Beim updateCustom() wird im ExpressionHandler alles geprueft. Beim Solven erst wuerde der JavaCallIniSolver erst aufgerufen, mit seinem parse(). Wert kommt aus parse()
//				assertNotNull(objEntry.getCallingClassname());
//				assertNotNull(objEntry.getCallingMethodname());
			}
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		
			objEntry = objSectionEntryReference.get();
			assertNotNull(objEntry);
			
			//Nutze eine Sammlung von assert Methoden, die ein objEntry als input hat.
			//und in der die verschiedenen stati für den unexpressed, unsubstituted, substituted, unsolved, etc Fall stehen.
			btemp = TestUtilAsTestZZZ.assertFileIniEntry(objExpressionHandler, objEnumFlagset, objEnumSurrounding, objEnumTestCase, objEntry, sExpression, sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved);
			assertTrue(btemp);
			
			bReturn = true;
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}
		return bReturn;
	}
		
	private boolean testCompute_Call_CallJava_IniUsed_unexpressed_(String sSectionIn, String sPropertyIn, String sExpressionIn, String sExpressionSubstitutedIn,String sExpressionSolvedIn, String sTagIn, String sTagSolvedIn) {
		boolean bReturn = false;
		try {
			boolean btemp; String stemp;
			
			String sSection; String sProperty; 
			String sExpression;  String sExpressionSubstituted; String sExpressionSolved; String sTag; String sTagSolved; 
			String sValue; 		
			ReferenceZZZ<IKernelConfigSectionEntryZZZ> objSectionEntryReference=null; IKernelConfigSectionEntryZZZ objEntry=null;
				
			sSection = sSectionIn;
			sProperty = sPropertyIn;
			sExpression = sExpressionIn;
			sExpressionSubstituted = sExpressionSubstitutedIn;
			sExpressionSolved = sExpressionSolvedIn;
			sTag = sTagIn;
			sTagSolved = sTagSolvedIn;
			
			String sTagStartZ = "<Z>";
			String sTagEndZ = "</Z>";	
			IEnumSetMappedTestFlagsetZZZ objEnumFlagset = EnumSetMappedTestCaseFlagsetTypeZZZ.UNEXPRESSED;
			
			//####################################################################################
			//### EXPRESSION - INI Handler .solve
			//####################################################################################
			
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
	
			boolean bUseExpressionGeneral = objFileIniTest.isExpressionEnabledGeneral();
			boolean bUseSolver = objFileIniTest.isSolverEnabledGeneral();
			
			sValue = objFileIniTest.getPropertyValue(sSection, sProperty).getValue();
			
			String sExpressionSolvedTemp = sExpressionSolved;
			//hier immer true if(bUseExpressionGeneral && bUseSolver && objEnumSurrounding.isSurroundingValueToRemove_On()) {
			if(bUseExpressionGeneral && bUseSolver){
				sExpressionSolvedTemp = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
			}
			sExpressionSolved = sExpressionSolvedTemp;
			assertEquals(sExpressionSolved, sValue);
			
			objEntry = objFileIniTest.getEntry();
			assertNotNull(objEntry);

			assertFalse(objEntry.isCall()); 		//Beim Solven erst wuerde der CallIniSolver erst aufgerufen, mit seinem parse(). Wert kommt aus parse()
			assertFalse(objEntry.isJavaCall());	    //Beim Solven erst wuerde der JavaCallIniSolver erst aufgerufen, mit seinem parse(). Wert kommt aus parse()
						
			//Nutze eine Sammlung von assert Methoden, die ein objEntry als input hat.
			//und in der die verschiedenen stati für den unexpressed, unsubstituted, substituted, unsolved, etc Fall stehen.
			btemp = TestUtilAsTestZZZ.assertFileIniEntry(objFileIniTest, objEnumFlagset, EnumSetMappedTestSurroundingTypeZZZ.SOLVE_REMOVE, EnumSetMappedTestCaseSolverTypeZZZ.AS_ENTRY, objEntry, sExpression, sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved);
			assertTrue(btemp);
			//###############################################################
						
			bReturn = true;
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}
		return bReturn;
	}
	
	private boolean testCompute_Call_CallJava_IniUsed_unsolved_(String sSectionIn, String sPropertyIn, String sExpressionIn, String sExpressionSubstitutedIn,String sExpressionSolvedIn, String sTagIn, String sTagSolvedIn) {
		boolean bReturn = false;
		try {
			boolean btemp; String stemp;
			
			String sSection; String sProperty; 
			String sExpression;  String sExpressionSubstituted; String sExpressionSolved; String sTag; String sTagSolved; 
			String sValue; 		
			ReferenceZZZ<IKernelConfigSectionEntryZZZ> objSectionEntryReference=null; IKernelConfigSectionEntryZZZ objEntry=null;
			
			sSection = sSectionIn;
			sProperty = sPropertyIn;
			sExpression = sExpressionIn;
			sExpressionSubstituted = sExpressionSubstitutedIn;
			sExpressionSolved = sExpressionSolvedIn;
			sTag = sTagIn;
			sTagSolved = sTagSolvedIn;
			
			String sTagStartZ = "<Z>";
			String sTagEndZ = "</Z>";	
			IEnumSetMappedTestFlagsetZZZ objEnumFlagset = EnumSetMappedTestCaseFlagsetTypeZZZ.UNSOLVED;
			
			//####################################################################################
			//### EXPRESSION - INI Handler .solve
			//####################################################################################
		
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
			
			boolean bUseExpressionGeneral = objFileIniTest.isExpressionEnabledGeneral();
			boolean bUseSolver = objFileIniTest.isSolverEnabledGeneral();
			
			sValue = objFileIniTest.getPropertyValue(sSection, sProperty).getValue();
			
			String sExpressionSolvedTemp = sExpressionSolved;
			//hier immer true if(bUseExpressionGeneral && bUseSolver && objEnumSurrounding.isSurroundingValueToRemove_On()) {
			if(bUseExpressionGeneral && bUseSolver){
				sExpressionSolvedTemp = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
			}
			sExpressionSolved = sExpressionSolvedTemp;
			assertEquals(sExpressionSolved, sValue);
			
			objEntry = objFileIniTest.getEntry();
			assertNotNull(objEntry);
			assertTrue(objEntry.isCall()); 		    //Beim Solven erst wuerde der CallIniSolver erst aufgerufen, mit seinem parse(). Wert kommt aus parse()
			assertTrue(objEntry.isJavaCall());	    //Beim Solven erst wuerde der JavaCallIniSolver erst aufgerufen, mit seinem parse(). Wert kommt aus parse()
			
			//Nutze eine Sammlung von assert Methoden, die ein objEntry als input hat.
			//und in der die verschiedenen stati für den unexpressed, unsubstituted, substituted, unsolved, etc Fall stehen.
			btemp = TestUtilAsTestZZZ.assertFileIniEntry(objFileIniTest, objEnumFlagset, EnumSetMappedTestSurroundingTypeZZZ.SOLVE_REMOVE, EnumSetMappedTestCaseSolverTypeZZZ.AS_ENTRY, objEntry, sExpression, sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved);
			
			//###############################################################
						
			bReturn = true;
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}
		return bReturn;
	}

	
	
	private boolean testCompute_Call_CallJava_IniUsed_(String sSectionIn, String sPropertyIn, String sExpressionIn, String sExpressionSubstitutedIn,String sExpressionSolvedIn, String sTagIn, String sTagSolvedIn) {
		boolean bReturn = false;
		try {
			boolean btemp; String stemp;
			
			String sSection; String sProperty;				
			String sExpression;  String sExpressionSubstituted; String sExpressionSolved; String sTag; String sTagSolved; 
			String sValue; 		
			ReferenceZZZ<IKernelConfigSectionEntryZZZ> objSectionEntryReference;
			IKernelConfigSectionEntryZZZ objEntry=null;

			String sTagStartZ = "<Z>";
			String sTagEndZ = "</Z>";	
			
			sSection = sSectionIn;
			sProperty = sPropertyIn;
			sExpression = sExpressionIn;
			sExpressionSubstituted = sExpressionSubstitutedIn;
			sExpressionSolved = sExpressionSolvedIn;
			sTag = sTagIn;
			sTagSolved = sTagSolvedIn;
			
			IEnumSetMappedTestFlagsetZZZ objEnumFlagset = EnumSetMappedTestCaseFlagsetTypeZZZ.JAVACALL_SOLVED;
						
			//####################################################################################
			//### EXPRESSION - INI Handler
			//####################################################################################
			
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
			
			boolean bUseExpressionGeneral = objFileIniTest.isExpressionEnabledGeneral();
			boolean bUseSolver = objFileIniTest.isSolverEnabledGeneral();
			
			sValue = objFileIniTest.getPropertyValue(sSection, sProperty).getValue();
			String sExpressionSolvedTemp = sExpressionSolved;
			//hier immer true if(bUseExpressionGeneral && bUseSolver && objEnumSurrounding.isSurroundingValueToRemove_On()) {
			if(bUseExpressionGeneral && bUseSolver){
				sExpressionSolvedTemp = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
			}
			sExpressionSolved = sExpressionSolvedTemp;
			assertEquals(sExpressionSolved, sValue);
			
			objEntry = objFileIniTest.getEntry();
			assertNotNull(objEntry);
			assertTrue(objEntry.isCall());
			assertTrue(objEntry.isJavaCall());
			
			//Nutze eine Sammlung von assert Methoden, die ein objEntry als input hat.
			//und in der die verschiedenen stati für den unexpressed, unsubstituted, substituted, unsolved, etc Fall stehen.
			btemp = TestUtilAsTestZZZ.assertFileIniEntry(objFileIniTest, objEnumFlagset, EnumSetMappedTestSurroundingTypeZZZ.SOLVE_REMOVE, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE_AS_ENTRY, objEntry, sExpression, sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved);
			assertTrue(btemp);
					
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
	public void testCompute_Call_CallJava_EntryDetail(){

		try {
			boolean btemp; 
			
			String sSection; String sProperty;
			String sExpression; String sExpressionSubstituted; String sExpressionSolved; String sTag=null; String sTagSolved=null;
			IKernelConfigSectionEntryZZZ objEntry; ReferenceZZZ<IKernelConfigSectionEntryZZZ>objSectionEntryReference;
		
			String sTagStartZ = "<Z>";
			String sTagEndZ = "</Z>";	
		
			String sFormulaSolvedAndConverted; String sFormulaSolvedAndConvertedAsExpression;
			String sHostName = EnvironmentZZZ.getHostName();
			//TODO 20241011: Source und Solved und solvedTagless etc. an die Methoden von aussen uebergeben, so dass die verschiednesten String sgetestet werden koennen.
			
			//+++++++ VORGEZOGENER LETZTER FEHLERTEST START
			
			//4)
			sExpression = KernelJavaCallIniSolverZZZTest.sEXPRESSION_CALL01_DEFAULT;
			sExpressionSubstituted = KernelJavaCallIniSolverZZZTest.sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT;			
			sExpressionSolved = sExpressionSubstituted;
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
			btemp = testCompute_Call_CallJava_EntryDetail_CallUnsolved_(sExpression, sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved, EnumSetMappedTestSurroundingTypeZZZ.PARSE_KEEP, EnumSetMappedTestCaseSolverTypeZZZ.AS_ENTRY);
			
			//+++++++ VORGEZOGENER LETZTER FEHLERTEST ENDE
			
			//1)
			sExpression = KernelJavaCallIniSolverZZZTest.sEXPRESSION_CALL01_DEFAULT; //sExpression = "<Z><Z:Call><Z:Java><Z:Class>{[ArgumentSection for testCallComputed]JavaClass}</Z:Class><Z:Method>{[ArgumentSection for testCallComputed]JavaMethod}</Z:Method></Z:Java></Z:Call></Z>";
			sExpressionSubstituted = sExpression;              
			sExpressionSolved = sExpressionSubstituted;
			btemp = testCompute_Call_CallJava_EntryDetail_SolverUnparsedUnsolved_(sExpression, sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved, EnumSetMappedTestSurroundingTypeZZZ.PARSE_KEEP, EnumSetMappedTestCaseSolverTypeZZZ.AS_ENTRY);			
						
			//2)
			sExpression = KernelJavaCallIniSolverZZZTest.sEXPRESSION_CALL01_DEFAULT; //sExpression = "<Z><Z:Call><Z:Java><Z:Class>{[ArgumentSection for testCallComputed]JavaClass}</Z:Class><Z:Method>{[ArgumentSection for testCallComputed]JavaMethod}</Z:Method></Z:Java></Z:Call></Z>";
			sExpressionSubstituted = sExpression;              
			sExpressionSolved = sExpressionSubstituted;
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ); //unparsed ja, unsolved nein... also Z-Tag drumherum entfernen
			btemp = testCompute_Call_CallJava_EntryDetail_SolverUnparsed_(objExpressionHandler, sExpression, sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved, EnumSetMappedTestSurroundingTypeZZZ.PARSE_KEEP, EnumSetMappedTestCaseSolverTypeZZZ.AS_ENTRY);			
			
			//3)
			sExpression = KernelJavaCallIniSolverZZZTest.sEXPRESSION_CALL01_DEFAULT;
			sExpressionSubstituted = KernelJavaCallIniSolverZZZTest.sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT;
			sExpressionSolved = sExpressionSubstituted;			
			btemp = testCompute_Call_CallJava_EntryDetail_SolverUnsolved_(sExpression, sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved, EnumSetMappedTestSurroundingTypeZZZ.PARSE_KEEP, EnumSetMappedTestCaseSolverTypeZZZ.AS_ENTRY);
			
			//4)
			sExpression = KernelJavaCallIniSolverZZZTest.sEXPRESSION_CALL01_DEFAULT;
			sExpressionSubstituted = KernelJavaCallIniSolverZZZTest.sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT;			
			sExpressionSolved = sExpressionSubstituted;
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
			btemp = testCompute_Call_CallJava_EntryDetail_CallUnsolved_(sExpression, sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved, EnumSetMappedTestSurroundingTypeZZZ.PARSE_KEEP, EnumSetMappedTestCaseSolverTypeZZZ.AS_ENTRY);

			//Alles aufloesen
			//A) einfach ohne extra SUBSTITUTION
			sExpression = KernelCallIniSolverZZZTest.sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT;
			sExpressionSubstituted = sExpression;
			sExpressionSolved = sHostName;
			btemp = testCompute_Call_CallJava_EntryDetail_(sExpression, sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved, EnumSetMappedTestSurroundingTypeZZZ.PARSE_KEEP, EnumSetMappedTestCaseSolverTypeZZZ.AS_ENTRY);
			
			//B) mit substitution, etc.

			//Solver ist deaktiviert, aber der darin ausgefuehrte Parser soll die Substitution trotzdem machen
		//	sExpression = "<Z><Z:Call><Z:Java><Z:Class>{[ArgumentSection for testCallComputed]JavaClass}</Z></Z:Class><Z:Method>{[ArgumentSection for testCallComputed]JavaMethod}</Z:Method></Z:Java></Z:Call></Z>";
		//	sExpressionSubstituted = "<Z><Z:Call><Z:Java><Z:Class>basic.zBasic.util.machine.EnvironmentZZZ</Z:Class><Z:Method>getHostName</Z:Method></Z:Java></Z:Call></Z>";;
		//	sExpressionSolved = sExpressionSubstituted;
		
			
			sExpression = KernelCallIniSolverZZZTest.sEXPRESSION_JAVACALL01_DEFAULT;
			sExpressionSubstituted = KernelCallIniSolverZZZTest.sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT;
			sExpressionSolved = sHostName;
			btemp = testCompute_Call_CallJava_EntryDetail_(sExpression, sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved, EnumSetMappedTestSurroundingTypeZZZ.PARSE_KEEP, EnumSetMappedTestCaseSolverTypeZZZ.AS_ENTRY);			
			
			
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}		
	}
	
	
	private boolean testCompute_Call_CallJava_EntryDetail_SolverUnparsedUnsolved_(String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, String sTagIn, String sTagSolvedIn, IEnumSetMappedTestSurroundingZZZ objEnumSurrounding, IEnumSetMappedTestCaseZZZ objEnumTestCase) {
		boolean bReturn = false;
		
		try {
			boolean btemp; 
			
			String sExpression;  String sExpressionSubstituted; String sExpressionSolved; String sTag; String sTagSolved; 
			String sValue; 		
			IKernelConfigSectionEntryZZZ objEntry=null;
			ReferenceZZZ<IKernelConfigSectionEntryZZZ> objSectionEntryReference=null; 
	
			sExpression = sExpressionIn;
			sExpressionSubstituted = sExpressionSubstitutedIn;
			sExpressionSolved = sExpressionSolvedIn;		
			sTag = sTagIn;
			sTagSolved = sTagSolvedIn;
			
			String sTagStartZ = "<Z>";
			String sTagEndZ = "</Z>";					
			
			IEnumSetMappedTestFlagsetZZZ  objEnumFlagset = EnumSetMappedTestCaseFlagsetTypeZZZ.UNPARSED_UNSOLVED;
			
			//###########################################################
			// unparsed / unsolved
			//###########################################################
				
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
				
			btemp = objFileIniTest.setFlag(IKernelCallIniSolverZZZ.FLAGZ.USECALL, true);
			assertTrue("Flag nicht vorhanden '" + IKernelCallIniSolverZZZ.FLAGZ.USECALL + "'", btemp);
		
			btemp = objFileIniTest.setFlag(IKernelJavaCallIniSolverZZZ.FLAGZ.USECALL_JAVA, true);
			assertTrue("Flag nicht vorhanden '" + IKernelJavaCallIniSolverZZZ.FLAGZ.USECALL_JAVA + "'", btemp);
		
			//### Generellen Solver und generellen Parser deaktiviert
			btemp = objFileIniTest.setFlag(IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER,false); //ohne aktivierten Parser keine SUBSTITUTION  
			assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER + "'", btemp);
						
			btemp = objFileIniTest.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER, false);
			assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);

			boolean bUseExpressionGeneral = objExpressionHandler.isExpressionEnabledGeneral();
			boolean bUseSolver = objExpressionHandler.isSolverEnabledGeneral();
			
			//#################################
			
			sValue = objFileIniTest.getPropertyValue("Section for testCall", "WertCalled").getValue();
			
			String sExpressionSurroundedTemp = sExpressionSolved;
			if(bUseExpressionGeneral && bUseSolver && objEnumSurrounding.isSurroundingValueToRemove_OnSolve()) {
				sExpressionSurroundedTemp = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSurroundedTemp, sTagStartZ, sTagEndZ);
			}
			sExpressionSolved = sExpressionSurroundedTemp;
			assertEquals(sExpressionSolved, sValue);
			
			//Wert mit Entry-Wert vergleichen
			objEntry = objFileIniTest.getEntry();			
			assertNotNull(objEntry);
			assertEquals(sValue, objEntry.getValue());
			
			//++++++++++++++++++++++++++++++++++++++++++++
			
			//Nutze eine Sammlung von assert Methoden, die ein objEntry als input hat.
			//und in der die verschiedenen stati für den unexpressed, unsubstituted, substituted, unsolved, etc Fall stehen.			
			btemp = TestUtilAsTestZZZ.assertFileIniEntry(objExpressionHandler, objEnumFlagset, EnumSetMappedTestSurroundingTypeZZZ.SOLVE_REMOVE, EnumSetMappedTestCaseSolverTypeZZZ.AS_ENTRY, objEntry, sExpression, sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved);
			assertTrue(btemp);
			
			
			
			bReturn = true;
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}
		
		return bReturn;
	}
	
	
	private boolean testCompute_Call_CallJava_EntryDetail_SolverUnparsed_(ISolveEnabledZZZ objSolver, String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, String sTagIn, String sTagSolvedIn, IEnumSetMappedTestSurroundingZZZ objEnumSurrounding, IEnumSetMappedTestCaseZZZ objEnumTestCase) {
		boolean bReturn = false;
		
		try {
			boolean btemp; 
			
			String sExpression;  String sExpressionSubstituted; String sExpressionSolved; String sTag; String sTagSolved; 
			String sValue; 		
			IKernelConfigSectionEntryZZZ objEntry=null;
			ReferenceZZZ<IKernelConfigSectionEntryZZZ> objSectionEntryReference=null; 
				
			sExpression = sExpressionIn;
			sExpressionSubstituted = sExpressionSubstitutedIn;
			sExpressionSolved = sExpressionSolvedIn;		
			sTag = sTagIn;
			sTagSolved = sTagSolvedIn;
			
			String sTagStartZ = "<Z>";
			String sTagEndZ = "</Z>";
			IEnumSetMappedTestFlagsetZZZ objEnumFlagset = EnumSetMappedTestCaseFlagsetTypeZZZ.UNPARSED;
			
			//###########################################################
			// unparsed 
			//###########################################################
				
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
				
			btemp = objFileIniTest.setFlag(IKernelCallIniSolverZZZ.FLAGZ.USECALL, true);
			assertTrue("Flag nicht vorhanden '" + IKernelCallIniSolverZZZ.FLAGZ.USECALL + "'", btemp);
		
			btemp = objFileIniTest.setFlag(IKernelJavaCallIniSolverZZZ.FLAGZ.USECALL_JAVA, true);
			assertTrue("Flag nicht vorhanden '" + IKernelJavaCallIniSolverZZZ.FLAGZ.USECALL_JAVA + "'", btemp);
		
			//######################################
			//### Generellen Parser deaktivieren
			btemp = objFileIniTest.setFlag(IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER, false); //ohne aktivierten Parser keine SUBSTITUTION  
			assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER + "'", btemp);
			
			boolean bUseExpressionGeneral = objExpressionHandler.isExpressionEnabledGeneral();
			boolean bUseSolver = objExpressionHandler.isSolverEnabledGeneral();
			
			//#################################
			
			sValue = objFileIniTest.getPropertyValue("Section for testCall", "WertCalled").getValue();
			
			String sExpressionSurroundedTemp = sExpressionSolved;
			if(bUseExpressionGeneral && bUseSolver && objEnumSurrounding.isSurroundingValueToRemove_OnSolve()) {
				sExpressionSurroundedTemp = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSurroundedTemp, sTagStartZ, sTagEndZ);
			}
			sExpressionSolved = sExpressionSurroundedTemp;
			assertEquals(sExpressionSolved, sValue);		
			
			//Wert mit Entry-Wert vergleichen
			objEntry = objFileIniTest.getEntry();			
			assertNotNull(objEntry);
			assertEquals(sValue, objEntry.getValue());
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			
			//Nutze eine Sammlung von assert Methoden, die ein objEntry als input hat.
			//und in der die verschiedenen stati für den unexpressed, unsubstituted, substituted, unsolved, etc Fall stehen.			
			btemp = TestUtilAsTestZZZ.assertFileIniEntry(objExpressionHandler, objEnumFlagset, EnumSetMappedTestSurroundingTypeZZZ.SOLVE_REMOVE, EnumSetMappedTestCaseSolverTypeZZZ.AS_ENTRY, objEntry, sExpression, sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved);
			assertTrue(btemp);
		
			bReturn = true;
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}
		
		return bReturn;
	}
	
	private boolean testCompute_Call_CallJava_EntryDetail_SolverUnsolved_(String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, String sTagIn, String sTagSolvedIn, IEnumSetMappedTestSurroundingZZZ objEnumSurrounding, IEnumSetMappedTestCaseZZZ objEnumTestCase) {
		boolean bReturn = false;
		
		try {
			boolean btemp; 
			
			String sExpression;  String sExpressionSubstituted; String sExpressionSolved; String sTag; String sTagSolved; 
			String sValue; 		
			IKernelConfigSectionEntryZZZ objEntry=null;
			ReferenceZZZ<IKernelConfigSectionEntryZZZ> objSectionEntryReference=null; 
			String sFormulaSolvedAndConverted; String sFormulaSolvedAndConvertedAsExpression;
						
			sExpression = sExpressionIn;
			sExpressionSubstituted = sExpressionSubstitutedIn;
			sExpressionSolved = sExpressionSolvedIn;		
			sTag = sTagIn;
			sTagSolved = sTagSolvedIn;

			String sTagStartZ = "<Z>";
			String sTagEndZ = "</Z>";
			IEnumSetMappedTestFlagsetZZZ objEnumFlagset = EnumSetMappedTestCaseFlagsetTypeZZZ.UNSOLVED;
		
			//###########################################################
			// unsolved
			//###########################################################
			
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
				
			btemp = objFileIniTest.setFlag(IKernelCallIniSolverZZZ.FLAGZ.USECALL, true);
			assertTrue("Flag nicht vorhanden '" + IKernelCallIniSolverZZZ.FLAGZ.USECALL + "'", btemp);
		
			btemp = objFileIniTest.setFlag(IKernelJavaCallIniSolverZZZ.FLAGZ.USECALL_JAVA, true);
			assertTrue("Flag nicht vorhanden '" + IKernelJavaCallIniSolverZZZ.FLAGZ.USECALL_JAVA + "'", btemp);
				
			//##################################################################################
			boolean bUseExpressionGeneral = objExpressionHandler.isExpressionEnabledGeneral();
			boolean bUseSolver = objExpressionHandler.isSolverEnabledGeneral();
			
			
			objEnumFlagset = EnumSetMappedTestCaseFlagsetTypeZZZ.UNSOLVED;
			
			//####################################################################################
			//### EXPRESSION - NICHT .solve
			//####################################################################################
		
			sValue = objFileIniTest.getPropertyValue("Section for testCall", "WertCalled").getValue();

			String sExpressionSurroundedTemp = sExpressionSolved;
			if(bUseExpressionGeneral && bUseSolver && objEnumSurrounding.isSurroundingValueToRemove_OnSolve()) {
				sExpressionSurroundedTemp = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSurroundedTemp, sTagStartZ, sTagEndZ);
			}
			sExpressionSolved = sExpressionSurroundedTemp;
			assertEquals(sExpressionSolved, sValue);
			
			//Wert mit Entry-Wert vergleichen
			objEntry = objFileIniTest.getEntry();
			assertNotNull(objEntry);
			assertEquals(sValue, objEntry.getValue());	
			
			//+++++++++++++++++++++++++++++++++++++++++++++++++
			
			//Nutze eine Sammlung von assert Methoden, die ein objEntry als input hat.
			//und in der die verschiedenen stati für den unexpressed, unsubstituted, substituted, unsolved, etc Fall stehen.			
			btemp = TestUtilAsTestZZZ.assertFileIniEntry(objExpressionHandler, objEnumFlagset, EnumSetMappedTestSurroundingTypeZZZ.SOLVE_REMOVE, EnumSetMappedTestCaseSolverTypeZZZ.AS_ENTRY, objEntry, sExpression, sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved);
			assertTrue(btemp);
			
			bReturn = true;
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}
		
		return bReturn;
	}
	
	private boolean testCompute_Call_CallJava_EntryDetail_CallUnsolved_(String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, String sTagIn, String sTagSolvedIn, IEnumSetMappedTestSurroundingZZZ objEnumSurrounding, IEnumSetMappedTestCaseZZZ objEnumTestCase) {
		boolean bReturn = false;
		
		try {
			boolean btemp; 
			
			String sExpression;  String sExpressionSubstituted; String sExpressionSolved; String sTag; String sTagSolved; 
			String sValue; String sValueFromFunction; 	
			IKernelConfigSectionEntryZZZ objEntry=null;
			ReferenceZZZ<IKernelConfigSectionEntryZZZ> objSectionEntryReference=null; 
			
			String sFormulaSolvedAndConverted; String sFormulaSolvedAndConvertedAsExpression;
			
			sExpression = sExpressionIn;
			sExpressionSubstituted = sExpressionSubstitutedIn;
			sExpressionSolved = sExpressionSolvedIn;
			sTag = sTagIn;
			sTagSolved = sTagSolvedIn;
			
			IEnumSetMappedTestFlagsetZZZ objEnumFlagset = EnumSetMappedTestCaseFlagsetTypeZZZ.CALL_UNSOLVED;

			//##################################################################
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
				
			btemp = objFileIniTest.setFlag(IKernelCallIniSolverZZZ.FLAGZ.USECALL, true);
			assertTrue("Flag nicht vorhanden '" + IKernelCallIniSolverZZZ.FLAGZ.USECALL + "'", btemp);
		
			btemp = objFileIniTest.setFlag(IKernelJavaCallIniSolverZZZ.FLAGZ.USECALL_JAVA, true);
			assertTrue("Flag nicht vorhanden '" + IKernelJavaCallIniSolverZZZ.FLAGZ.USECALL_JAVA + "'", btemp);

			
			//NUN DEAKTIVIEREN						
			btemp = objFileIniTest.setFlag(IKernelCallIniSolverZZZ.FLAGZ.USECALL, false); //Ansonsten wird der Wert sofort ausgerechnet
			assertTrue("Flag nicht vorhanden '" + IKernelCallIniSolverZZZ.FLAGZ.USECALL + "'", btemp);
		
			btemp = objFileIniTest.setFlag(IKernelJavaCallIniSolverZZZ.FLAGZ.USECALL_JAVA, false); //Ansonsten wird der Wert sofort ausgerechnet
			assertTrue("Flag nicht vorhanden '" + IKernelJavaCallIniSolverZZZ.FLAGZ.USECALL_JAVA + "'", btemp);
		
			
			//Wert mit dem Entry-Wert aus Methode vergleichen
			objEntry = objFileIniTest.getPropertyValue("Section for testCall", "WertCalled");
			assertNotNull(objEntry);
			sValue = objEntry.getValue();
			assertEquals(sExpressionSolved,sValue);
			sValueFromFunction = sValue;
			
			//Wert mit Entry-Wert aus dem Objekt vergleichen
			objEntry = objFileIniTest.getEntry();
			assertNotNull(objEntry);
			sValue = objEntry.getValue();
			assertEquals(sValueFromFunction,sValue);

			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++
			
			//Nutze eine Sammlung von assert Methoden, die ein objEntry als input hat.
			//und in der die verschiedenen stati für den unexpressed, unsubstituted, substituted, unsolved, etc Fall stehen.
			btemp = TestUtilAsTestZZZ.assertFileIniEntry(objFileIniTest, objEnumFlagset, EnumSetMappedTestSurroundingTypeZZZ.SOLVE_REMOVE,EnumSetMappedTestCaseSolverTypeZZZ.AS_ENTRY, objEntry, sExpression, sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved);
						
			bReturn = true;
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}
		
		return bReturn;
	}
	
	private boolean testCompute_Call_CallJava_EntryDetail_(String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, String sTagIn, String sTagSolvedIn, IEnumSetMappedTestSurroundingZZZ objEnumSurrounding, IEnumSetMappedTestCaseZZZ objEnumTestCase) {
		boolean bReturn = false;
		
		try {
			boolean btemp; 
			
			String sExpression;  String sExpressionSubstituted; String sExpressionSolved; String sTag; String sTagSolved; 
			String sValue; 		
			IKernelConfigSectionEntryZZZ objEntry=null;
			ReferenceZZZ<IKernelConfigSectionEntryZZZ> objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>(); 
			
			sExpression = sExpressionIn;
			sExpressionSubstituted = sExpressionSubstitutedIn;
			sExpressionSolved = sExpressionSolvedIn;
			sTag = sTagIn;
			sTagSolved = sTagSolvedIn;
			
			String sFormulaSolvedAndConverted; String sFormulaSolvedAndConvertedAsExpression;
			String sHostName = EnvironmentZZZ.getHostName();

			IEnumSetMappedTestFlagsetZZZ objEnumFlagset = EnumSetMappedTestCaseFlagsetTypeZZZ.SOLVED;
			
			//###################################################		
			
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

			//Nutze eine Sammlung von assert Methoden, die ein objEntry als input hat.
			//und in der die verschiedenen stati für den unexpressed, unsubstituted, substituted, unsolved, etc Fall stehen.
			btemp = TestUtilAsTestZZZ.assertFileIniEntry(objExpressionHandler, objEnumFlagset, EnumSetMappedTestSurroundingTypeZZZ.SOLVE_REMOVE, EnumSetMappedTestCaseSolverTypeZZZ.AS_ENTRY, objEntry, sExpression, sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved);
			
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
			String sExpression; String sExpressionSubstituted; String sExpressionSolved; ArrayList alExpressionSolved; String sTag; String sTagSolved; 
			String sSection; String sProperty;	
			
			//+++++++ VORGEZOGENER LETZTER FEHLERTEST START
								
						
			//+++++++ VORGEZOGENER LETZTER FEHLERTEST ENDE
			
			//################################################################
			//### 1) Varianten Encryption nicht aufzuloesen
			//#################################################################
			//1a)
			sExpression = sExpressionIn;
			sExpressionSubstituted = sExpressionIn;
			sExpressionSolved = sExpression;
			//sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
			alExpressionSolved = new ArrayList();
			sTag = "sTag muss konstante sein";
			sTagSolved = "sTagSolved muss konstante sein";
			btemp = testCompute_Encrypted_EncryptionUnsolved_(sExpression, sExpressionSubstituted, sExpressionSolved, alExpressionSolved, sTag, sTagSolved, EnumSetMappedTestSurroundingTypeZZZ.PARSE_KEEP, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
			
			//1b)
			sExpression = sExpressionIn;
			sExpressionSubstituted = sExpressionIn;
			sExpressionSolved = sExpression;
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
			alExpressionSolved = new ArrayList();
			sTag = "sTag muss konstante sein";
			sTagSolved = "sTagSolved muss konstante sein";
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
			btemp = testCompute_Encrypted_EncryptionUnsolved_(sExpression, sExpressionSubstituted, sExpressionSolved, alExpressionSolved, sTag, sTagSolved, EnumSetMappedTestSurroundingTypeZZZ.PARSE_REMOVE, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
			
			
			//1c)
			sExpression = sExpressionIn;
			sExpressionSubstituted = sExpressionIn;
			sExpressionSolved = sExpression;
			//sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
			alExpressionSolved = new ArrayList();
			sTag = "sTag muss konstante sein";
			sTagSolved = "sTagSolved muss konstante sein";			
			btemp = testCompute_Encrypted_EncryptionUnsolved_(sExpression, sExpressionSubstituted, sExpressionSolved, alExpressionSolved, sTag, sTagSolved, EnumSetMappedTestSurroundingTypeZZZ.SOLVE_KEEP, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
			
			
			//1d)
			sExpression = sExpressionIn;
			sExpressionSubstituted = sExpressionIn;
			sExpressionSolved = sExpression;
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
			alExpressionSolved = new ArrayList();
			sTag = "sTag muss konstante sein";
			sTagSolved = "sTagSolved muss konstante sein";			
			btemp = testCompute_Encrypted_EncryptionUnsolved_(sExpression, sExpressionSubstituted, sExpressionSolved, alExpressionSolved, sTag, sTagSolved, EnumSetMappedTestSurroundingTypeZZZ.PARSE_REMOVE, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
			
			
		
			//################################################################
			//### 2) Varianten Encryption aufzuloesen
			//#################################################################
			
			//2a)			
			sExpression = sExpressionIn;
			sExpressionSubstituted = sExpressionIn;
			sExpressionSolved = sExpression;
			alExpressionSolved = new ArrayList();
			sTag = "sTag muss konstante sein";
			sTagSolved = "sTagSolved muss konstante sein";
			btemp = testCompute_Encrypted_(sExpression, sExpressionSubstituted, sExpressionSolved, alExpressionSolved, sTag, sTagSolved, EnumSetMappedTestSurroundingTypeZZZ.PARSE_KEEP, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
			
			//2b)
			sExpression = sExpressionIn;
			sExpressionSubstituted = sExpressionIn;
			sExpressionSolved = sExpressionSubstituted;
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
			alExpressionSolved = new ArrayList();			
			sTag = "sTag muss konstante sein";
			sTagSolved = "sTagSolved muss konstante sein";
			btemp = testCompute_Encrypted_(sExpression, sExpressionSubstituted, sExpressionSolved, alExpressionSolved, sTag, sTagSolved, EnumSetMappedTestSurroundingTypeZZZ.PARSE_REMOVE, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
			
			
			//2c)
			sExpression = sExpressionIn;	
			sExpressionSubstituted = sExpressionIn;
			sExpressionSolved = "<Z>abcde</Z>";
			//sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
			alExpressionSolved = new ArrayList();
			sTag = "sTag muss konstante sein";
			sTagSolved = "sTagSolved muss konstante sein";			
			btemp = testCompute_Encrypted_(sExpression, sExpressionSubstituted, sExpressionSolved, alExpressionSolved, sTag, sTagSolved, EnumSetMappedTestSurroundingTypeZZZ.SOLVE_KEEP, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
						
			
			//2d)
			sExpression = sExpressionIn;
			sExpressionSubstituted = sExpressionIn;
			sExpressionSolved = "<Z>abcde</Z>";				
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
			alExpressionSolved = new ArrayList();
			sTag = "sTag muss konstante sein";
			sTagSolved = "sTagSolved muss konstante sein";
			btemp = testCompute_Encrypted_(sExpression, sExpressionSubstituted, sExpressionSolved, alExpressionSolved,  sTag, sTagSolved, EnumSetMappedTestSurroundingTypeZZZ.SOLVE_REMOVE, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
			
									
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}		
		
		
	}
	
	private boolean testCompute_Encrypted_(String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, ArrayList<String> alExpressionSolvedIn, String sTagIn, String sTagSolvedIn, IEnumSetMappedTestSurroundingZZZ objEnumSurrounding, IEnumSetMappedTestCaseZZZ objEnumTestCase) {
		boolean bReturn = false;
		
		main:{
			try {
				String sExpression; String sExpressionSubstituted; String sExpressionSolved;  String sTag; String sTagSolved;
				String sValue;
				boolean btemp; String stemp;	
				IKernelConfigSectionEntryZZZ objEntry=null;
				ReferenceZZZ<IKernelConfigSectionEntryZZZ> objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
				
				String sTagStartZ = "<Z>";
				String sTagEndZ = "</Z>";
				
				sExpression = sExpressionIn;
				sExpressionSubstituted = sExpressionSubstitutedIn;
				sExpressionSolved = sExpressionSolvedIn;
				sTag=sTagIn;
				sTagSolved = sTagSolvedIn;
				
				ArrayList<String> alExpressionSolved = alExpressionSolvedIn;
				IEnumSetMappedTestFlagsetZZZ objEnumFlagset = EnumSetMappedTestCaseFlagsetTypeZZZ.ENCRYPTION_SOLVED;
				
				//#########################################################
				//### Mit Berechnung Encryption
				//#########################################################


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
				
				boolean bUseExpressionGeneral = objExpressionHandler.isExpressionEnabledGeneral();
				boolean bUseSolver = objExpressionHandler.isSolverEnabledGeneral();
				boolean bUseParser = objExpressionHandler.isParserEnabledGeneral();
				
				//+++ ... parse ist nicht solve... also wird hier nichts aufgeloest, aussser die Pfade
				if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.PARSE)) {
					sValue = objExpressionHandler.parse(sExpression, objSectionEntryReference, objEnumSurrounding.getSurroundingValueUsedForMethod());
					
					String sExpressionSurroundedTemp = sExpressionSubstituted;
					if(bUseExpressionGeneral && bUseParser && objEnumSurrounding.isSurroundingValueToRemove_OnParse()) {
						sExpressionSurroundedTemp = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSurroundedTemp, sTagStartZ, sTagEndZ);
					}
					sExpressionSubstituted = sExpressionSurroundedTemp;
					assertEquals(sExpressionSubstituted, sValue);
					
					
					objEntry = objExpressionHandler.parseAsEntry(sExpression, objSectionEntryReference, objEnumSurrounding.getSurroundingValueUsedForMethod());
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
					sValue = objExpressionHandler.solve(sExpression, objSectionEntryReference, objEnumSurrounding.getSurroundingValueUsedForMethod());

					String sExpressionSurroundedTemp = sExpressionSolvedIn;					
					if(bUseExpressionGeneral && bUseSolver && objEnumSurrounding.isSurroundingValueToRemove_OnSolve()) {
						sExpressionSurroundedTemp = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSurroundedTemp, sTagStartZ, sTagEndZ);
					}
					sExpressionSolved = sExpressionSurroundedTemp;
					assertEquals(sExpressionSolved, sValue);
					
					
					objEntry = objExpressionHandler.solveAsEntry(sExpression, objSectionEntryReference, objEnumSurrounding.getSurroundingValueUsedForMethod());
					assertNotNull(objEntry);
					sValue = objEntry.getValue();	
					assertEquals(sExpressionSolved, sValue);
					
					assertFalse(objEntry.isJson());
					assertFalse(objEntry.isJsonArray());
					
					assertTrue(objEntry.isDecrypted());  //Nach dem Solven ist das entschluesselt
					assertTrue(objEntry.isRawEncrypted()); //Im Expression-Solver wird der konkrete Solver aufgerufen (Hier Encryption). Nur der konkrete Solver kennt dann solche speziellen Flags, die nur fuer ihn da sind.
				}
				//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

				//Nutze eine Sammlung von assert Methoden, die ein objEntry als input hat.
				//und in der die verschiedenen stati für den unexpressed, unsubstituted, substituted, unsolved, etc Fall stehen.
				btemp = TestUtilAsTestZZZ.assertFileIniEntry(objExpressionHandler, objEnumFlagset, objEnumSurrounding, objEnumTestCase, objEntry, sExpression, sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved);
				assertTrue(btemp);
				
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

				String sTagStartZ = "<Z>";
				String sTagEndZ = "</Z>";
				
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

					String sExpressionSolvedTemp = sExpressionSolved;
					if(bRemoveSurroundingSeparatorsOnSolve) {
						sExpressionSolvedTemp = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
					}
					sExpressionSolved = sExpressionSolvedTemp;
					assertEquals(sExpressionSolved, sValue);
					
					IKernelConfigSectionEntryZZZ objEntry = objExpressionHandler.solveAsEntry(sExpressionSource, objSectionEntryReference, bRemoveSurroundingSeparatorsOnSolve);
					assertNotNull(objEntry);
					sValue = objEntry.getValue();	
					
					assertEquals(sExpressionSolvedTemp, sValue);					assertFalse(objEntry.isJson());
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
	
	private boolean testCompute_Encrypted_EncryptionUnsolved_(String sExpressionIn, String sExpressionSolvedIn, String sExpressionSubstitutedIn,  ArrayList<String> alExpressionSolvedIn, String sTagIn, String sTagSolvedIn, IEnumSetMappedTestSurroundingZZZ objEnumSurrounding, IEnumSetMappedTestCaseZZZ objEnumTestCase) {
		boolean bReturn = false;
		
		main:{
			try {
				String sExpression; String sExpressionSubstituted; String sExpressionSolved;  String sTag; String sTagSolved;
				String sValue;
				boolean btemp; String stemp;	
				IKernelConfigSectionEntryZZZ objEntry=null;
				ReferenceZZZ<IKernelConfigSectionEntryZZZ> objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
				
				String sTagStartZ = "<Z>";
				String sTagEndZ = "</Z>";
				
				sExpression = sExpressionIn;
				sExpressionSubstituted = sExpressionSubstitutedIn;
				sExpressionSolved = sExpressionSolvedIn;
				sTag=sTagIn;
				sTagSolved = sTagSolvedIn;
				
				ArrayList<String> alExpressionSolved = alExpressionSolvedIn;
				IEnumSetMappedTestFlagsetZZZ objEnumFlagset = EnumSetMappedTestCaseFlagsetTypeZZZ.ENCRYPTION_UNSOLVED;
				
				//#########################################################
				//### Mit Encryption unsolved
				//#########################################################

				
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
							
				boolean bUseExpressionGeneral = objExpressionHandler.isExpressionEnabledGeneral();
				boolean bUseSolver = objExpressionHandler.isSolverEnabledGeneral();
				boolean bUseParser = objExpressionHandler.isParserEnabledGeneral();
				
				//+++ ... solve verhält sich NICHT wie parse(), bei solve wird aufgeloest...
				if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.PARSE)) {
					sValue = objExpressionHandler.parse(sExpression, objSectionEntryReference, objEnumSurrounding.getSurroundingValueUsedForMethod());
					
					String sExpressionSurroundedTemp = sExpressionSolved;
					if(bUseExpressionGeneral && bUseParser && objEnumSurrounding.isSurroundingValueToRemove_OnParse()) {
						sExpressionSurroundedTemp = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSurroundedTemp, sTagStartZ, sTagEndZ);
					}
					sExpressionSolved = sExpressionSurroundedTemp;
					assertEquals(sExpressionSolved, sValue);
					
					objEntry = objExpressionHandler.parseAsEntry(sExpression, objSectionEntryReference, objEnumSurrounding.getSurroundingValueUsedForMethod());
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
					sValue = objExpressionHandler.solve(sExpression, objSectionEntryReference, objEnumSurrounding.getSurroundingValueUsedForMethod());
					
					String sExpressionSurroundedTemp = sExpressionSolved;
					if(bUseExpressionGeneral && bUseParser && objEnumSurrounding.isSurroundingValueToRemove_OnSolve()) {
						sExpressionSurroundedTemp = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSurroundedTemp, sTagStartZ, sTagEndZ);
					}					
					sExpressionSolved = sExpressionSurroundedTemp;
					assertEquals(sExpressionSolved, sValue);
					
					objEntry = objExpressionHandler.solveAsEntry(sExpression, objSectionEntryReference, objEnumSurrounding.getSurroundingValueUsedForMethod());
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
					
				
				//+++++++ VORGEZOGENER LETZTER FEHLERTEST ENDE
			
			
				//################################################################
				//### Varianten ENCRYPTION per Section/Property in Ini-File aufloesen
				//#################################################################
				
				//a) ohne solver wird der Z-Tag nicht entfernt... 
				sSection = "Section for testEncrypted";
				sProperty = "WertAforDecrypt";			
				sExpressionSolved = KernelEncryptionIniSolverZZZTest.sEXPRESSION_ENCRYPTION01_DEFAULT;
				//sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
				btemp = testCompute_Encrypted_IniUsed_Unsolved_(sSection, sProperty, sExpressionSolved);
				
				//b) mit solver wird der Z-Tag nicht entfernt... auch wenn die encryption nicht aufgeloest wird
				sSection = "Section for testEncrypted";
				sProperty = "WertAforDecrypt";			
				sExpressionSolved = KernelEncryptionIniSolverZZZTest.sEXPRESSION_ENCRYPTION01_DEFAULT;
				sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
				btemp = testCompute_Encrypted_IniUsed_EncryptionUnsolved_(sSection, sProperty, sExpressionSolved);
					
				//c) und nun alles aufloesen
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
				boolean btemp; String stemp;
				String sSection; String sProperty; String sExpressionSolved; String sValue;
				IKernelConfigSectionEntryZZZ objEntry=null;
				ReferenceZZZ<IKernelConfigSectionEntryZZZ> objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
				
				String sTagStartZ = "<Z>";
				String sTagEndZ = "</Z>";	
				
				sSection = sSectionIn;
				sProperty = sPropertyIn;
				sExpressionSolved = sExpressionSolvedIn;
				
				IEnumSetMappedTestFlagsetZZZ objEnumFlagset = EnumSetMappedTestCaseFlagsetTypeZZZ.UNSOLVED;
				
				//#######################################
				//... OHNE Solver
				//#######################################
				
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
							
				boolean bUseExpressionGeneral = objFileIniTest.isExpressionEnabledGeneral();
				boolean bUseSolver = objFileIniTest.isSolverEnabledGeneral();
				
								
				objEntry = objFileIniTest.getPropertyValue(sSection, sProperty);
				assertNotNull(objEntry);
				sValue = objEntry.getValue();	
				
				String sExpressionSolvedTemp = sExpressionSolved;
				if(bUseExpressionGeneral && bUseSolver){
					sExpressionSolvedTemp = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
				}
				sExpressionSolved = sExpressionSolvedTemp;
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
				boolean btemp; String stemp;
				String sSection; String sProperty; String sExpressionSolved; String sValue;				
				ReferenceZZZ<IKernelConfigSectionEntryZZZ> objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();			

				String sTagStartZ = "<Z>";
				String sTagEndZ = "</Z>";	
				
				sSection = sSectionIn;
				sProperty = sPropertyIn;
				sExpressionSolved = sExpressionSolvedIn;
				
				IEnumSetMappedTestFlagsetZZZ objEnumFlagset = EnumSetMappedTestCaseFlagsetTypeZZZ.ENCRYPTION_UNSOLVED;
								
				//#######################################
				//... mit OHNE Encryption
				//#######################################
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
					
				boolean bUseExpressionGeneral = objFileIniTest.isExpressionEnabledGeneral();
				boolean bUseSolver = objFileIniTest.isSolverEnabledGeneral();
				
				
				
				IKernelConfigSectionEntryZZZ objEntry = objFileIniTest.getPropertyValue(sSection, sProperty);
				assertNotNull(objEntry);
				sValue = objEntry.getValue();	
				

				String sExpressionSurroundedTemp = sExpressionSolved;
				if(bUseExpressionGeneral && bUseSolver) {
					sExpressionSurroundedTemp = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSurroundedTemp, sTagStartZ, sTagEndZ);
				}
				sExpressionSolved = sExpressionSurroundedTemp;
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
				

				String sTagStartZ = "<Z>";
				String sTagEndZ = "</Z>";		
				
				sSection = sSectionIn;
				sProperty = sPropertyIn;							
				sExpressionSolved = sExpressionSolvedIn;
				
				IEnumSetMappedTestFlagsetZZZ objEnumFlagset = EnumSetMappedTestCaseFlagsetTypeZZZ.ENCRYPTION_SOLVED;
				
				
				//#########################################################
				//... Encryption
				//#########################################################
				
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
								
				boolean bUseExpressionGeneral = objFileIniTest.isExpressionEnabledGeneral();
				boolean bUseSolver = objFileIniTest.isSolverEnabledGeneral();
				
				IKernelConfigSectionEntryZZZ objEntry = objFileIniTest.getPropertyValue(sSection, sProperty);
				assertNotNull(objEntry);
				sValue = objEntry.getValue();
				
				String sExpressionSurroundedTemp = sExpressionSolved;
				if(bUseExpressionGeneral && bUseSolver) { // && objEnumSurrounding.isSurroundingValueToRemove_OnParse()) {
					sExpressionSurroundedTemp = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSurroundedTemp, sTagStartZ, sTagEndZ);
				}
				sExpressionSolved = sExpressionSurroundedTemp;
				assertEquals(sExpressionSolved, sValue);
				
				assertFalse(objEntry.isJson());
				assertFalse(objEntry.isJsonArray());
				
				assertTrue(objEntry.isDecrypted());
				assertTrue(objEntry.isRawEncrypted());
				
				ArrayList<String> als = objEntry.getValueArrayList();
				assertNull(als);
					
				//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
				
				//Nutze eine Sammlung von assert Methoden, die ein objEntry als input hat.
				//und in der die verschiedenen stati für den unexpressed, unsubstituted, substituted, unsolved, etc Fall stehen.
				//btemp = TestUtilAsTestZZZ.assertFileIniEntry(objExpressionHandler, objEnumFlagset, objEnumSurrounding, objEnumTestCase, objEntry, sExpression, sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved);
				//assertTrue(btemp);

				
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
	public void testCompute_Json_JsonArray(){
//		try {
			String sExpression=null; String sExpressionSubstituted=null; String sExpressionSolved=null; ArrayList<String>alsExpressionSolved=null;
			
			sExpression = KernelJsonArrayIniSolverZZZTest.sEXPRESSION_JSONARRAY01_DEFAULT;
			sExpressionSubstituted = KernelJsonArrayIniSolverZZZTest.sEXPRESSION_JSONARRAY01_DEFAULT;//es gibt noch keinen substituted Testfall.
			sExpressionSolved = KernelJsonArrayIniSolverZZZTest.sEXPRESSION_JSONARRAY01_SOLVED;
			alsExpressionSolved = new ArrayList();
			alsExpressionSolved.add("TESTWERT2DO2JSON01");
			alsExpressionSolved.add("TESTWERT2DO2JSON02");
			testCompute_Json_JsonArray_(sExpression, sExpressionSubstituted, sExpressionSolved, alsExpressionSolved);		
//		} catch (ExceptionZZZ ez) {
//			fail("Method throws an exception." + ez.getMessageLast());
//		}
	}
		
	/** void, Test: Reading an entry in a section of the ini-file
	* Lindhauer; 22.04.2006 12:54:32
	 */
	private void testCompute_Json_JsonArray_(String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, ArrayList<String>alsExpressionSolvedIn){

		try {	
			boolean btemp; String stemp;
			String sExpression; String sExpressionSubstituted; String sExpressionSolved; ArrayList<String>alsExpressionSolved;
			
			String sTagStartZ = "<Z>";
			String sTagEndZ = "</Z>";						
		
			//String sSection; String sProperty;	
			
			//+++++++ VORGEZOGENER LETZTER FEHLERTEST START
				
			//2a)
			sExpression = sExpressionIn;
			sExpressionSubstituted = sExpressionSubstitutedIn;
			sExpressionSolved = sExpressionSubstituted; //nur parse, darum solve=substituted,    und nicht  sExpressionSolvedIn;//alsExpressionSolved.toString();
			alsExpressionSolved = alsExpressionSolvedIn;
			btemp = testCompute_Json_JsonArray_JsonArrayUnsolved_(sExpression, sExpressionSubstituted, sExpressionSolved, alsExpressionSolved, EnumSetMappedTestSurroundingTypeZZZ.PARSE_KEEP, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
			

			
			//+++++++ VORGEZOGENER LETZTER FEHLERTEST ENDE
			
			//################################################################
			//### 1. Varianten JSON nicht aufzuloesen
			//#################################################################
			//1a)
			sExpression = sExpressionIn;
			sExpressionSubstituted = sExpressionSubstitutedIn;
			sExpressionSolved = sExpressionSubstituted; //nur parse, darum solve=substituted,    und nicht  sExpressionSolvedIn;//alsExpressionSolved.toString();
			alsExpressionSolved = alsExpressionSolvedIn;
			btemp = testCompute_Json_JsonArray_JsonUnsolved_(sExpression, sExpressionSubstituted, sExpressionSolved, alsExpressionSolved, EnumSetMappedTestSurroundingTypeZZZ.PARSE_KEEP, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
			
			//1b)
			sExpression = sExpressionIn;
			sExpressionSubstituted = sExpressionSubstitutedIn;
			sExpressionSubstituted = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSubstituted, sTagStartZ, sTagEndZ);
			sExpressionSolved = sExpressionSubstituted; //nur parse, darum solve=substituted,    und nicht  sExpressionSolvedIn;//alsExpressionSolved.toString();
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ); 
			alsExpressionSolved = alsExpressionSolvedIn;
			btemp = testCompute_Json_JsonArray_JsonUnsolved_(sExpression, sExpressionSubstituted, sExpressionSolved, alsExpressionSolved, EnumSetMappedTestSurroundingTypeZZZ.PARSE_REMOVE, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
			
			
			//1c)
			sExpression = sExpressionIn;
			sExpressionSubstituted = sExpressionSubstitutedIn;
			sExpressionSolved = sExpressionSubstituted; //nur parse, darum solve=substituted,    und nicht  sExpressionSolvedIn;//alsExpressionSolved.toString();
			alsExpressionSolved = alsExpressionSolvedIn;
			btemp = testCompute_Json_JsonArray_JsonUnsolved_(sExpression, sExpressionSubstituted, sExpressionSolved, alsExpressionSolved, EnumSetMappedTestSurroundingTypeZZZ.SOLVE_KEEP, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
			
			
			//1d)
			sExpression = sExpressionIn;
			sExpressionSubstituted = sExpressionSubstitutedIn;
			sExpressionSubstituted = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSubstituted, sTagStartZ, sTagEndZ);
			sExpressionSolved = sExpressionSubstituted; //nur parse, darum solve=substituted,    und nicht  sExpressionSolvedIn;//alsExpressionSolved.toString();
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
			alsExpressionSolved = alsExpressionSolvedIn;
			btemp = testCompute_Json_JsonArray_JsonUnsolved_(sExpression, sExpressionSubstituted, sExpressionSolved, alsExpressionSolved, EnumSetMappedTestSurroundingTypeZZZ.SOLVE_REMOVE, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
			
			
			//################################################################
			//### 2. Varianten JSON-ArrayList nicht aufzuloesen
			//#################################################################
			//2a)
			sExpression = sExpressionIn;
			sExpressionSubstituted = sExpressionSubstitutedIn;
			sExpressionSolved = sExpressionSubstituted; //nur parse, darum solve=substituted,    und nicht  sExpressionSolvedIn;//alsExpressionSolved.toString();
			alsExpressionSolved = alsExpressionSolvedIn;
			btemp = testCompute_Json_JsonArray_JsonArrayUnsolved_(sExpression, sExpressionSubstituted, sExpressionSolved, alsExpressionSolved, EnumSetMappedTestSurroundingTypeZZZ.PARSE_KEEP, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
			
			//2b)
			sExpression = sExpressionIn;
			sExpressionSubstituted = sExpressionSubstitutedIn;
			sExpressionSubstituted = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSubstituted, sTagStartZ, sTagEndZ);
			sExpressionSolved = sExpressionSubstituted; //nur parse, darum solve=substituted,    und nicht  sExpressionSolvedIn;//alsExpressionSolved.toString();
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
			alsExpressionSolved = alsExpressionSolvedIn;
			btemp = testCompute_Json_JsonArray_JsonArrayUnsolved_(sExpression, sExpressionSubstituted, sExpressionSolved, alsExpressionSolved, EnumSetMappedTestSurroundingTypeZZZ.PARSE_REMOVE, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
			
			//2c)
			sExpression = sExpressionIn;
			sExpressionSubstituted = sExpressionSubstitutedIn;
			sExpressionSolved = sExpressionSubstituted; //nur parse, darum solve=substituted,    und nicht  sExpressionSolvedIn;//alsExpressionSolved.toString();
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, KernelJsonIniSolverZZZ.sTAG_NAME);			
			alsExpressionSolved = null;
			btemp = testCompute_Json_JsonArray_JsonArrayUnsolved_(sExpression, sExpressionSubstituted, sExpressionSolved, alsExpressionSolved, EnumSetMappedTestSurroundingTypeZZZ.SOLVE_KEEP, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
			
			//2d)
			sExpression = sExpressionIn;
			sExpressionSubstituted = sExpressionSubstitutedIn;
			sExpressionSubstituted = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSubstituted, sTagStartZ, sTagEndZ);
			sExpressionSolved = sExpressionSubstituted; //nur parse, darum solve=substituted,    und nicht  sExpressionSolvedIn;//alsExpressionSolved.toString();
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, KernelJsonIniSolverZZZ.sTAG_NAME);
			alsExpressionSolved = null;
			btemp = testCompute_Json_JsonArray_JsonArrayUnsolved_(sExpression, sExpressionSubstituted, sExpressionSolved, alsExpressionSolved, EnumSetMappedTestSurroundingTypeZZZ.SOLVE_REMOVE, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
			
			//################################################################
			//### 3. Varianten JSON-ArrayList aufzuloesen
			//#################################################################
			
			//3a)			
			sExpression = sExpressionIn;
			sExpressionSubstituted = sExpressionSubstitutedIn;
			sExpressionSolved = sExpressionSubstitutedIn;//nur parse
			alsExpressionSolved = alsExpressionSolvedIn;
			btemp = testCompute_Json_JsonArray_JsonArraySolved_(sExpression, sExpressionSubstituted, sExpressionSolved, alsExpressionSolved, EnumSetMappedTestSurroundingTypeZZZ.PARSE_KEEP, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
			
			
			//3b)
			sExpression = sExpressionIn;	
			sExpressionSubstituted = sExpressionSubstitutedIn;
			sExpressionSubstituted = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSubstituted, sTagStartZ, sTagEndZ);
			sExpressionSolved = sExpressionSubstitutedIn; //nur parse, darum solve=substituted,    und nicht  sExpressionSolvedIn;//alsExpressionSolved.toString();
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
			alsExpressionSolved = alsExpressionSolvedIn;
			btemp = testCompute_Json_JsonArray_JsonArraySolved_(sExpression, sExpressionSubstituted, sExpressionSolved, alsExpressionSolved, EnumSetMappedTestSurroundingTypeZZZ.PARSE_REMOVE, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);

			
			
			//3c)
			sExpression = sExpressionIn;
			sExpressionSubstituted = sExpressionSubstitutedIn;
			alsExpressionSolved = alsExpressionSolvedIn;			
			sExpressionSolved = "<Z>" + alsExpressionSolved.toString() + "</Z>";			
			btemp = testCompute_Json_JsonArray_JsonArraySolved_(sExpression, sExpressionSubstituted, sExpressionSolved, alsExpressionSolved, EnumSetMappedTestSurroundingTypeZZZ.SOLVE_KEEP, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
						
			
			//3d)
			sExpression = sExpressionIn;
			sExpressionSubstituted = sExpressionSubstitutedIn;
			sExpressionSubstituted = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSubstituted, sTagStartZ, sTagEndZ);
			alsExpressionSolved = alsExpressionSolvedIn;
			sExpressionSolved = alsExpressionSolved.toString();
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
			btemp = testCompute_Json_JsonArray_JsonArraySolved_(sExpression, sExpressionSubstituted, sExpressionSolved, alsExpressionSolved, EnumSetMappedTestSurroundingTypeZZZ.SOLVE_REMOVE, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
			
									
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
	
	private boolean testCompute_Json_JsonArray_JsonArraySolved_(String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, ArrayList<String>alsExpressionSolvedIn, IEnumSetMappedTestSurroundingZZZ objEnumSurrounding, IEnumSetMappedTestCaseZZZ objEnumTestCase) {
		boolean bReturn = false;
		
		main:{
			try {
				boolean btemp; String stemp;
				
				String sExpression; String sExpressionSubstituted; HashMap hmExpressionSolved; String sExpressionSolved; ArrayList<String>alsExpressionSolved; String sTag=null; String sTagSolved=null; 
				String sValue;				
				IKernelConfigSectionEntryZZZ objEntry=null;
				ReferenceZZZ<IKernelConfigSectionEntryZZZ>objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
				
				String sTagStartZ = "<Z>";
				String sTagEndZ = "</Z>";	
				
				sExpression = sExpressionIn;
				sExpressionSubstituted = sExpressionSubstitutedIn;
				alsExpressionSolved = alsExpressionSolvedIn;		
				sExpressionSolved = sExpressionSolvedIn; //hmExpressionSolved.toString();
				
				IEnumSetMappedTestFlagsetZZZ objEnumFlagset = EnumSetMappedTestCaseFlagsetTypeZZZ.JSONARRAY_SOLVED;
				
				//####################################################################################
				//### EXPRESSION - JSONARRAY .solve
				//####################################################################################

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
				
				boolean bUseExpressionGeneral = objExpressionHandler.isExpressionEnabledGeneral();
				boolean bUseSolver = objExpressionHandler.isSolverEnabledGeneral();
				boolean bUseParser = objExpressionHandler.isParserEnabledGeneral();
							
				//+++ ... parse ist nicht solve... also wird hier nichts aufgeloest, aussser die Pfade
				if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.PARSE)) {					
					sValue = objExpressionHandler.parse(sExpression, objSectionEntryReference, objEnumSurrounding.isSurroundingValueToKeep_OnParse());
					
					String sExpressionSurroundedTemp = sExpressionSubstituted;
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
					sValue = objExpressionHandler.solve(sExpression, objSectionEntryReference, objEnumSurrounding.isSurroundingValueToRemove_OnSolve());
					
					String sExpressionSurroundedTemp = sExpressionSolved;
					if(bUseExpressionGeneral && bUseSolver && objEnumSurrounding.isSurroundingValueToRemove_OnSolve()) {
						sExpressionSurroundedTemp = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSurroundedTemp, sTagStartZ, sTagEndZ);
					}
					sExpressionSolved = sExpressionSurroundedTemp;
					assertEquals(sExpressionSolved, sValue);
					
					objEntry = objSectionEntryReference.get();
					assertNotNull(objEntry);
				}
				//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
				
				//Nutze eine Sammlung von assert Methoden, die ein objEntry als input hat.
				//und in der die verschiedenen stati für den unexpressed, unsubstituted, substituted, unsolved, etc Fall stehen.
				btemp = TestUtilAsTestZZZ.assertFileIniEntry(objExpressionHandler, objEnumFlagset, objEnumSurrounding, objEnumTestCase, objEntry, sExpression, sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved);
				assertTrue(btemp);

				
				bReturn = true;
			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			}
		}//end main:
		return bReturn;
	}
	
	private boolean testCompute_Json_JsonArray_JsonArrayUnsolved_(String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, ArrayList<String>alsSolvedIn, IEnumSetMappedTestSurroundingZZZ objEnumSurrounding, IEnumSetMappedTestCaseZZZ objEnumTestCase) {
		boolean bReturn = false;
		
		main:{
			try {
				boolean btemp; String stemp;
				
				String sExpression; String sExpressionSubstituted; HashMap hmExpressionSolved; String sExpressionSolved; String sTag=null; String sTagSolved=null;
				String sValue;				
				ReferenceZZZ<IKernelConfigSectionEntryZZZ>objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
				IKernelConfigSectionEntryZZZ objEntry=null; 
				
				String sTagStartZ = "<Z>";
				String sTagEndZ = "</Z>";
				
				sExpression = sExpressionIn;
				sExpressionSubstituted = sExpressionSubstitutedIn;
				sExpressionSolved = sExpressionSolvedIn;
				
				IEnumSetMappedTestFlagsetZZZ objEnumFlagset = EnumSetMappedTestCaseFlagsetTypeZZZ.JSONARRAY_UNSOLVED;
				
				//####################################################################################
				//### EXPRESSION - NICHT JSONMAP BEHANDLUNG .solve
				//####################################################################################

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
				assertTrue("Das Flag '" + IKernelJsonArrayIniSolverZZZ.FLAGZ.USEJSON_ARRAY +"' sollte zur Verfügung stehen.", btemp);
							
				boolean bUseExpressionGeneral = objExpressionHandler.isExpressionEnabledGeneral();
				boolean bUseSolver = objExpressionHandler.isSolverEnabledGeneral();
				boolean bUseParser = objExpressionHandler.isParserEnabledGeneral();
				
				//+++ ... solve verhält sich NICHT wie parse(), bei solve wird aufgeloest...
				if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.PARSE)) {					
					sValue = objExpressionHandler.parse(sExpression, objSectionEntryReference, objEnumSurrounding.isSurroundingValueToKeep_OnParse());
					
					String sExpressionSurroundedTemp = sExpressionSubstituted;
					if(bUseExpressionGeneral && bUseParser && objEnumSurrounding.isSurroundingValueToRemove_OnParse()) {
						sExpressionSurroundedTemp = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSurroundedTemp, sTagStartZ, sTagEndZ);
					}
					sExpressionSubstituted = sExpressionSurroundedTemp;
					assertEquals(sExpressionSubstituted, sValue);
					
					objEntry = objSectionEntryReference.get();
					assertNotNull(objEntry);
				}
				//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

				//+++ ... parse ist nicht solve... also wird hier nichts aufgeloest, aussser die Pfade
				if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.SOLVE)) {
					sValue = objExpressionHandler.solve(sExpression, objSectionEntryReference, objEnumSurrounding.isSurroundingValueToRemove_OnSolve());

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
				
				
				//Nutze eine Sammlung von assert Methoden, die ein objEntry als input hat.
				//und in der die verschiedenen stati für den unexpressed, unsubstituted, substituted, unsolved, etc Fall stehen.
				btemp = TestUtilAsTestZZZ.assertFileIniEntry(objExpressionHandler, objEnumFlagset, objEnumSurrounding, objEnumTestCase, objEntry, sExpression, sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved);
				assertTrue(btemp);

				
				bReturn = true;
			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			}
		}//end main:
		return bReturn;
	}
	
	private boolean testCompute_Json_JsonArray_JsonUnsolved_(String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, ArrayList<String>alsSolvedIn, IEnumSetMappedTestSurroundingZZZ objEnumSurrounding, IEnumSetMappedTestCaseZZZ objEnumTestCase) {
		boolean bReturn = false;
		
		main:{
			try {
				boolean btemp; String stemp;
				
				String sExpression; String sExpressionSubstituted; HashMap hmExpressionSolved; String sExpressionSolved; String sTag=null; String sTagSolved=null;
				String sValue;				
				ReferenceZZZ<IKernelConfigSectionEntryZZZ>objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
				IKernelConfigSectionEntryZZZ objEntry=null; 
				
				String sTagStartZ = "<Z>";
				String sTagEndZ = "</Z>";	
				
				sExpression = sExpressionIn;
				sExpressionSubstituted = sExpressionSubstitutedIn;
				sExpressionSolved = sExpressionSolvedIn;
				
				IEnumSetMappedTestFlagsetZZZ objEnumFlagset = EnumSetMappedTestCaseFlagsetTypeZZZ.JSON_UNSOLVED;
				
				//####################################################################################
				//### EXPRESSION - NICHT JSONMAP BEHANDLUNG .solve
				//####################################################################################

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
				
				boolean bUseExpressionGeneral = objExpressionHandler.isExpressionEnabledGeneral();
				boolean bUseSolver = objExpressionHandler.isSolverEnabledGeneral();
				boolean bUseParser = objExpressionHandler.isParserEnabledGeneral();
				
				//+++ ... solve verhält sich NICHT wie parse(), bei solve wird aufgeloest...
				if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.PARSE)) {					
					sValue = objExpressionHandler.parse(sExpression, objSectionEntryReference, objEnumSurrounding.isSurroundingValueToKeep_OnParse());
					
					String sExpressionSurroundedTemp = sExpressionSubstituted;
					if(bUseExpressionGeneral && bUseParser && objEnumSurrounding.isSurroundingValueToRemove_OnParse()) {
						sExpressionSurroundedTemp = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSurroundedTemp, sTagStartZ, sTagEndZ);
					}
					sExpressionSubstituted = sExpressionSurroundedTemp;
					assertEquals(sExpressionSubstituted, sValue);
										
					objEntry = objSectionEntryReference.get();
					assertNotNull(objEntry);
				}
				//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

				//+++ ... parse ist nicht solve... also wird hier nichts aufgeloest, aussser die Pfade
				if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.SOLVE)) {
					sValue = objExpressionHandler.solve(sExpression, objSectionEntryReference, objEnumSurrounding.isSurroundingValueToRemove_OnSolve());
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
				
				//Nutze eine Sammlung von assert Methoden, die ein objEntry als input hat.
				//und in der die verschiedenen stati für den unexpressed, unsubstituted, substituted, unsolved, etc Fall stehen.
				btemp = TestUtilAsTestZZZ.assertFileIniEntry(objExpressionHandler, objEnumFlagset, objEnumSurrounding, objEnumTestCase, objEntry, sExpression, sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved);
				assertTrue(btemp);

				
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
	public void testCompute_Json_JsonArray_IniUsed(){	
		boolean btemp;
		String sExpression=null; String sExpressionSubstituted; String sExpressionSolved; ArrayList<String>alsExpressionSolved;
		String sSection; String sProperty; 
		
		String sTagStartZ = "<Z>";
		String sTagEndZ = "</Z>";		
//		try {
				
			//################################################################
			//### Varianten JSON-ArrayList per Section/Property in Ini-File aufloesen
			//#################################################################
			
			sSection = "Section for testJsonArraylist";
			sProperty = "Array1";		
			sExpression = KernelJsonArrayIniSolverZZZTest.sEXPRESSION_JSONARRAY01_DEFAULT;
			sExpressionSubstituted = KernelJsonArrayIniSolverZZZTest.sEXPRESSION_JSONARRAY01_DEFAULT;
			sExpressionSolved = KernelJsonArrayIniSolverZZZTest.sEXPRESSION_JSONARRAY01_DEFAULT;
			alsExpressionSolved = new ArrayList();
			alsExpressionSolved.add("TESTWERT2DO2JSON01");
			alsExpressionSolved.add("TESTWERT2DO2JSON02");
			btemp = testCompute_Json_JsonArray_IniUsed_Unsolved_(sSection, sProperty, sExpression, sExpressionSubstituted, sExpressionSolved, alsExpressionSolved);
			
			sSection = "Section for testJsonArraylist";
			sProperty = "Array1";
			sExpression = KernelJsonArrayIniSolverZZZTest.sEXPRESSION_JSONARRAY01_DEFAULT;
			sExpressionSubstituted = KernelJsonArrayIniSolverZZZTest.sEXPRESSION_JSONARRAY01_DEFAULT;
			sExpressionSolved = KernelJsonArrayIniSolverZZZTest.sEXPRESSION_JSONARRAY01_DEFAULT;
			alsExpressionSolved = new ArrayList();
			alsExpressionSolved.add("TESTWERT2DO2JSON01");
			alsExpressionSolved.add("TESTWERT2DO2JSON02");			
			btemp = testCompute_Json_JsonArray_IniUsed_Detail_(sSection, sProperty, sExpression, sExpressionSubstituted, alsExpressionSolved);

			

//		} catch (ExceptionZZZ ez) {
//			fail("Method throws an exception." + ez.getMessageLast());
//		}
	}

	
	private boolean testCompute_Json_JsonArray_IniUsed_Unsolved_(String sSectionIn, String sPropertyIn, String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, ArrayList<String> alExpressionSolvedIn) {
		boolean bReturn = false;
		
		main:{
			try {
				boolean btemp; String stemp;
				
				String sSection; String sProperty; 
				String sExpression; String sExpressionSubstituted; String sExpressionSolved;  String sTag=null; String sTagSolved=null;
				String sValue;

				IKernelConfigSectionEntryZZZ objEntry=null; ReferenceZZZ<IKernelConfigSectionEntryZZZ>objSectionEntryReference=null;
				

				String sTagStartZ = "<Z>";
				String sTagEndZ = "</Z>";	
									
				sSection = sSectionIn;
				sProperty = sPropertyIn;
				sExpression = sExpressionIn;
				sExpressionSubstituted = sExpressionSubstitutedIn;
				sExpressionSolved = sExpressionSolvedIn;
				
				IEnumSetMappedTestFlagsetZZZ objEnumFlagset = EnumSetMappedTestCaseFlagsetTypeZZZ.UNSOLVED;
				
				//####################################################################################
				//### EXPRESSION - JSONARRAY BEHANDLUNG .solve
				//####################################################################################
				
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
				
				boolean bUseExpressionGeneral = objFileIniTest.isExpressionEnabledGeneral();
				boolean bUseSolver = objFileIniTest.isSolverEnabledGeneral();
				
				//TODOGOON20250614;//irgendwie wird nicht berücksichtigt, dass der Solver nicht verwendet werden soll
				                 //ohne Solver soll aber das Z-Tag drumherum bleiben.
				objEntry = objFileIniTest.getPropertyValue(sSection, sProperty);
				assertNotNull(objEntry);
				
				sValue = objEntry.getValue();
				
				IEnumSetMappedTestCaseZZZ objEnumTestCase = EnumSetMappedTestCaseSolverTypeZZZ.AS_ENTRY;
				IEnumSetMappedTestSurroundingZZZ objEnumSurrounding = EnumSetMappedTestSurroundingTypeZZZ.SOLVE_REMOVE;
				
				String sExpressionSurroundedTemp = sExpressionSolved;
				if(bUseExpressionGeneral && bUseSolver && objEnumSurrounding.isSurroundingValueToRemove_OnSolve()) {
					sExpressionSurroundedTemp = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSurroundedTemp, sTagStartZ, sTagEndZ);
				}
				sExpressionSolved = sExpressionSurroundedTemp;
				assertEquals(sExpressionSolved, sValue);

				//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
				
				//Nutze eine Sammlung von assert Methoden, die ein objEntry als input hat.
				//und in der die verschiedenen stati für den unexpressed, unsubstituted, substituted, unsolved, etc Fall stehen.
				btemp = TestUtilAsTestZZZ.assertFileIniEntry(objFileIniTest, objEnumFlagset, objEnumSurrounding, objEnumTestCase, objEntry, sExpression, sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved);
				assertTrue(btemp);

				
				bReturn = true;
			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			}
		}//end main:
		return bReturn;
	}
	
	boolean testCompute_Json_JsonArray_IniUsed_Detail_(String sSectionIn, String sPropertyIn, String sExpressionIn, String sExpressionSubstitutedIn, ArrayList<String> alExpressionSolvedIn) {
		boolean bReturn = false;
		
		main:{			
			try {
				boolean btemp; String stemp;
				
				String sSection; String sProperty; 
				String sExpression; String sExpressionSubstituted; String sExpressionSolved;  String sTag=null; String sTagSolved=null;
				String sValue;

				IKernelConfigSectionEntryZZZ objEntry=null; ReferenceZZZ<IKernelConfigSectionEntryZZZ>objSectionEntryReference=null;
								
				sSection = sSectionIn;
				sProperty = sPropertyIn;
				sExpression = sExpressionIn;
				sExpressionSubstituted = sExpressionSubstitutedIn;
				ArrayList<String>alExpressionSolved = alExpressionSolvedIn;
				sExpressionSolved = alExpressionSolved.toString();
				
				String sTagStartZ = "<Z>";
				String sTagEndZ = "</Z>";	
				
				IEnumSetMappedTestCaseZZZ objEnumTestCase = EnumSetMappedTestCaseSolverTypeZZZ.AS_ENTRY;
				IEnumSetMappedTestSurroundingZZZ objEnumSurrounding = EnumSetMappedTestSurroundingTypeZZZ.SOLVE_REMOVE;
				
				IEnumSetMappedTestFlagsetZZZ objEnumFlagset = EnumSetMappedTestCaseFlagsetTypeZZZ.JSONARRAY_SOLVED;
				
				//####################################################################################
				//### EXPRESSION - NICHT JSONMAP BEHANDLUNG .solve
				//####################################################################################

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
									
				boolean bUseExpressionGeneral = objExpressionHandler.isExpressionEnabledGeneral();
				boolean bUseSolver = objExpressionHandler.isSolverEnabledGeneral();
			
				
				objEntry = objFileIniTest.getPropertyValue(sSection, sProperty);
				assertNotNull(objEntry);
				sValue = objEntry.getValue();
				
				String sExpressionSurroundedTemp = sExpressionSolved;
				if(bUseExpressionGeneral && bUseSolver && objEnumSurrounding.isSurroundingValueToRemove_OnSolve()) {
					sExpressionSurroundedTemp = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSurroundedTemp, sTagStartZ, sTagEndZ);
				}
				sExpressionSolved = sExpressionSurroundedTemp;
				assertEquals(sExpressionSolved, sValue);
				
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
				
				//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
				
				//Nutze eine Sammlung von assert Methoden, die ein objEntry als input hat.
				//und in der die verschiedenen stati für den unexpressed, unsubstituted, substituted, unsolved, etc Fall stehen.
				btemp = TestUtilAsTestZZZ.assertFileIniEntry(objExpressionHandler, objEnumFlagset, objEnumSurrounding, objEnumTestCase, objEntry, sExpression, sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved);
				assertTrue(btemp);

				
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
	
