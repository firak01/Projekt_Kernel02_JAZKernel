package basic.zKernel.file.ini;

import java.io.File;
import java.util.Vector;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IObjectWithExpressionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedTestCaseZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedTestFlagsetZZZ;
import basic.zBasic.util.datatype.calling.ReferenceZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.datatype.xml.XmlUtilZZZ;
import basic.zBasic.util.machine.EnvironmentZZZ;
import basic.zKernel.IKernelConfigSectionEntryZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernel.KernelConfigSectionEntryZZZ;
import basic.zKernel.KernelZZZ;
import basic.zKernel.config.KernelConfigSectionEntryUtilZZZ;
import basic.zKernel.file.ini.KernelCallIniSolverZZZTest.TestSubtype;
import custom.zKernel.file.ini.FileIniZZZ;
import junit.framework.TestCase;

public class KernelJavaCallIniSolverZZZTest  extends TestCase {
	protected final static String sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT = "<Z><Z:Call><Z:Java><Z:Class>basic.zBasic.util.machine.EnvironmentZZZ</Z:Class><Z:Method>getHostName</Z:Method></Z:Java></Z:Call></Z>";
	//protected final static String sEXPRESSION_ENCRYPTION02_DEFAULT = "<Z><Z:Encrypted><Z:Cipher>ROTnumeric</Z:Cipher><z:KeyNumber>5</z:KeyNumber><Z:FlagControl>USENUMERIC</Z:FlagControl><Z:Code>fghij</Z:Code></Z:Encrypted></Z>";
	//protected final static String sEXPRESSION_ENCRYPTION03_DEFAULT = "<Z><Z:Encrypted><Z:Cipher>ROTnn</Z:Cipher><z:KeyNumber>5</z:KeyNumber><z:CharacterPool> abcdefghijklmnopqrstuvwxyz?!</z:CharacterPool><z:FlagControl>USEUPPERCASE</Z:FlagControl><Z:Code>fghij</Z:Code></Z:Encrypted></Z>";
	
	private File objFile;
	private IKernelZZZ objKernel;
	private FileIniZZZ objFileIniTest=null;	
	
	/// +++ Die eigentlichen Test-Objekte	
	private KernelJavaCallIniSolverZZZ objExpressionSolver;
	private KernelJavaCallIniSolverZZZ objExpressionSolverInit;
	
	

	protected void setUp(){
		try {			
						
			objKernel = new KernelZZZ("FGL", "01", "test", "ZKernelConfigKernel_test.ini",(String)null);
			
			//#### Ein init TestObjekt
			String[] saFlagInit = {"init"};
			objExpressionSolverInit = new KernelJavaCallIniSolverZZZ(objKernel, saFlagInit);
			
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
			objExpressionSolver = new KernelJavaCallIniSolverZZZ(objKernel, saFlag);
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		} 
//			catch (FileNotFoundException e) {			
//				e.printStackTrace();
//			} catch (IOException e) {			
//				e.printStackTrace();
//			}		
	}//END setup
	
	public void testFlagHandling(){
		try{							
		assertTrue(objExpressionSolverInit.getFlag("init")==true);
		assertFalse(objExpressionSolver.getFlag("init")==true); //Nun wäre init falsch
		
		boolean bFlagAvailable = objExpressionSolver.setFlag("usecall", false); //Ansonsten wird der Wert sofort ausgerechnet
		assertTrue("Das Flag 'usecall' sollte zur Verfügung stehen.", bFlagAvailable);
		
		bFlagAvailable = objExpressionSolver.setFlag("usecall_java", false); //Ansonsten wird der Wert sofort ausgerechnet
		assertTrue("Das Flag 'usecall_java' sollte zur Verfügung stehen.", bFlagAvailable);
		
		bFlagAvailable = objExpressionSolver.setFlag("gibtEsNicht", false); //Ansonsten wird der Wert sofort ausgerechnet
		assertFalse("Das Flag 'gibtEsNicht' sollte nicht zur Verfügung stehen.", bFlagAvailable);
		
		
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}
	
	
	public void testCompute_Call() {
		String sExpressionSource=null;
//		try {
		
		//##################################
		//### Vorgezogener letzter Fehlertest: START

		//++++++++++ PRE / POST
		sExpressionSource = KernelCallIniSolverZZZTest.sEXPRESSION_CALL01_DEFAULT;
		testCompute_JavaCall_(sExpressionSource, "PRE", "POST", TestSubtype.DEFAULT);
		
		sExpressionSource = KernelCallIniSolverZZZTest.sEXPRESSION_CALL01_DEFAULT;
		testCompute_JavaCall_(sExpressionSource, "PRE", "POST", TestSubtype.AS_ENTRY);

	
		//### Vorgezogener letzter Fehlertest: ENDE
		//##################################
	
	
		//Teste die Pfadersetzung, die nicht nur im KernelExpresssionIniHandlerZZZ funktionieren soll.
		sExpressionSource = KernelCallIniSolverZZZTest.sEXPRESSION_CALL01_DEFAULT; 
		testCompute_JavaCall_(sExpressionSource,"","", TestSubtype.DEFAULT);
		
		sExpressionSource = KernelCallIniSolverZZZTest.sEXPRESSION_CALL01_DEFAULT; 
		testCompute_JavaCall_(sExpressionSource,"","", TestSubtype.AS_ENTRY);
		
		
		//Test ohne notwendige Pfadersetzung
		sExpressionSource = KernelCallIniSolverZZZTest.sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT; 
		testCompute_JavaCall_(sExpressionSource,"","", TestSubtype.DEFAULT);

		sExpressionSource = KernelCallIniSolverZZZTest.sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT; 
		testCompute_JavaCall_(sExpressionSource,"","", TestSubtype.AS_ENTRY);

		
		//++++++++++ PRE / POST
		sExpressionSource = KernelCallIniSolverZZZTest.sEXPRESSION_CALL01_DEFAULT;
		testCompute_JavaCall_(sExpressionSource, "PRE", "POST", TestSubtype.DEFAULT);
		
		sExpressionSource = KernelCallIniSolverZZZTest.sEXPRESSION_CALL01_DEFAULT;
		testCompute_JavaCall_(sExpressionSource, "PRE", "POST", TestSubtype.AS_ENTRY);
		
//		} catch (ExceptionZZZ ez) {
//			fail("Method throws an exception." + ez.getMessageLast());
//		}
}
	
	/**void, Test: Reading an entry in a section of the ini-file
	 * 
	 * @author Fritz Lindhauer, 05.05.2023, 08:54:30
	 */
	private void testCompute_JavaCall_(String sExpressionIn, String sPreIn, String sPostIn, TestSubtype enumTestSubtype){
		//SIEHE AUCH testCompute_CallWithPrePost
		boolean btemp; int itemp;
		
		String sSection; String sProperty;
		String sExpression; String sExpressionSubstituted; String sExpressionSolved; String sTag; String sTagSolved;
		IKernelConfigSectionEntryZZZ objEntry; ReferenceZZZ<IKernelConfigSectionEntryZZZ>objSectionEntryReference;
	
		String sValue;
		String sTagStartZ = "<Z>";
		String sTagEndZ = "</Z>";	
			
		String sPre = sPreIn;
		String sPost = sPostIn;
		
		try {		
			String sHostName = EnvironmentZZZ.getHostName();
			assertNotNull(sHostName);
					
			//+++++++ VORGEZOGENER LETZTER FEHLERTEST START
			
		

			//+++++++ VORGEZOGENER LETZTER FEHLERTEST ENDE
			
			
			
			//###########################
		    //### objExpression
			//#########################
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//+++ Ohne jegliche Expression-Berechnung
			//a)
			sExpression = sPre + sExpressionIn + sPost;
			sExpressionSubstituted = sExpression;
			sExpressionSolved = sPre + sExpressionIn + sPost;
			
			//Der reine Tag, ohne umgebende Z-Tags und Call-Tags
			sTag = null; //ohne Expression-Auswertung
			sTagSolved = null;
			sTagSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sTagSolved, sTagStartZ, sTagEndZ, false); //von aussen nach innen. So bleiben Z-Tags innen(z.B. um den Pfad herum) erhalten.
			sTagSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sTagSolved, KernelCallIniSolverZZZ.sTAG_NAME, false);
            
			if(enumTestSubtype != null && enumTestSubtype == TestSubtype.AS_ENTRY) {
				btemp = testCompute_JavaCall_Unexpressed_(sExpression, sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved, false, EnumSetMappedTestCaseSolverTypeZZZ.PARSE_AS_ENTRY);
			}else {
				btemp = testCompute_JavaCall_Unexpressed_(sExpression, sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved, false, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
			}
			
			//b)
			sExpression = sPre + sExpressionIn + sPost;
			sExpressionSubstituted = sExpression;
			sExpressionSolved = sPre + sExpressionIn + sPost;
			
			//Der reine Tag, ohne umgebende Z-Tags und Call-Tags
			sTag = null;//ohne Expression-Auswertung
			sTagSolved = null;
			sTagSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sTagSolved, sTagStartZ, sTagEndZ, false); //von aussen nach innen. So bleiben Z-Tags innen(z.B. um den Pfad herum) erhalten.
			sTagSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sTagSolved, KernelCallIniSolverZZZ.sTAG_NAME, false);

			if(enumTestSubtype != null && enumTestSubtype == TestSubtype.AS_ENTRY) {
				btemp = testCompute_JavaCall_Unexpressed_(sExpression,sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved, true, EnumSetMappedTestCaseSolverTypeZZZ.PARSE_AS_ENTRY);
			}else {
				btemp = testCompute_JavaCall_Unexpressed_(sExpression,sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved, true, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
			}
			
			//c)
			sExpression = sPre + sExpressionIn + sPost;
			sExpressionSubstituted = sExpression;
			sExpressionSolved = sPre + sExpressionIn + sPost; 	
			
			//Der reine Tag, ohne umgebende Z-Tags und Call-Tags
			sTag = null;//ohne Expression-Auswertung
			sTagSolved = null;
			sTagSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sTagSolved, sTagStartZ, sTagEndZ, false); //von aussen nach innen. So bleiben Z-Tags innen(z.B. um den Pfad herum) erhalten.
			sTagSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sTagSolved, KernelCallIniSolverZZZ.sTAG_NAME, false);
            
			if(enumTestSubtype != null && enumTestSubtype == TestSubtype.AS_ENTRY) {
				btemp = testCompute_JavaCall_Unexpressed_(sExpression, sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved, false, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE_AS_ENTRY);
			} else {
				btemp = testCompute_JavaCall_Unexpressed_(sExpression, sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved, false, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
			}
			
				
			//d)
			sExpression = sPre + sExpressionIn + sPost;
			sExpressionSubstituted = sExpression;
			sExpressionSolved = sPre + sExpressionIn + sPost;
			
			//Der reine Tag, ohne umgebende Z-Tags und Call-Tags
			sTag = null;//ohne Expression-Auswertung
			sTagSolved = null;
			sTagSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sTagSolved, sTagStartZ, sTagEndZ, false); //von aussen nach innen. So bleiben Z-Tags innen(z.B. um den Pfad herum) erhalten.
			sTagSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sTagSolved, KernelCallIniSolverZZZ.sTAG_NAME, false);
            
			if(enumTestSubtype != null && enumTestSubtype == TestSubtype.AS_ENTRY) {
				btemp = testCompute_JavaCall_Unexpressed_(sExpression, sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved, true, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE_AS_ENTRY);
			}else {
				btemp = testCompute_JavaCall_Unexpressed_(sExpression, sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved, true, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
			}
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//+++ Ohne Solver-Berechnung	(Ergebnisse muessen so sein wie bei Parse)
			//a)
			sExpression = sPre + sExpressionIn + sPost;
			sExpressionSubstituted = sPre + sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT + sPost;
			sExpressionSolved = sPre + sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT + sPost; //auch ohne Solver werden die Pfade substituiert!!!
			
			//Der reine Tag, ohne umgebende Z-Tags und Call-Tags
			sTag = sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT;
			sTagSolved = sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT;
			sTagSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sTagSolved, sTagStartZ, sTagEndZ, false); //von aussen nach innen. So bleiben Z-Tags innen(z.B. um den Pfad herum) erhalten.
			sTagSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sTagSolved, KernelCallIniSolverZZZ.sTAG_NAME, false);
            
			if(enumTestSubtype != null && enumTestSubtype == TestSubtype.AS_ENTRY) {
				btemp = testCompute_JavaCall_Unsolved_(sExpression, sExpressionSolved, false, EnumSetMappedTestCaseSolverTypeZZZ.PARSE_AS_ENTRY);
			}else {
				btemp = testCompute_JavaCall_Unsolved_(sExpression, sExpressionSolved, false, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
			}
			
			//b)
			sExpression = sPre + sExpressionIn + sPost;
			sExpressionSubstituted = sPre + sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT + sPost;
			sExpressionSolved = sPre + sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT + sPost; //auch ohne Solver werden die Pfade substituiert!!!
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ, false);
			//Beim Parsen ohne Solver, bleibt sogar das Encryption-Tag drin, auch wenn sonst die Tags entfernt werden.
			
			//Der reine Tag, ohne umgebende Z-Tags und Call-Tags
			sTag = sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT;
			sTagSolved = sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT;
			sTagSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sTagSolved, sTagStartZ, sTagEndZ, false); //von aussen nach innen. So bleiben Z-Tags innen(z.B. um den Pfad herum) erhalten.
			sTagSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sTagSolved, KernelCallIniSolverZZZ.sTAG_NAME, false);
            
			if(enumTestSubtype != null && enumTestSubtype == TestSubtype.AS_ENTRY) {
				btemp = testCompute_JavaCall_Unsolved_(sExpression, sExpressionSolved, true, EnumSetMappedTestCaseSolverTypeZZZ.PARSE_AS_ENTRY);
			} else {
				btemp = testCompute_JavaCall_Unsolved_(sExpression, sExpressionSolved, true, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
			}

			//c)
			sExpression = sPre + sExpressionIn + sPost;
			sExpressionSubstituted = sPre + sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT + sPost;
			sExpressionSolved = sPre + sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT + sPost; //auch ohne Solver werden die Pfade substituiert!!!
			//Beim Solven ohne Solver, bleibt alles wie est ist.
			
			//Der reine Tag, ohne umgebende Z-Tags und Call-Tags
			sTag = sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT;
			sTagSolved = sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT;
			sTagSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sTagSolved, sTagStartZ, sTagEndZ, false); //von aussen nach innen. So bleiben Z-Tags innen(z.B. um den Pfad herum) erhalten.
			sTagSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sTagSolved, KernelCallIniSolverZZZ.sTAG_NAME, false);
            
			if(enumTestSubtype != null && enumTestSubtype == TestSubtype.AS_ENTRY) {
				btemp = testCompute_JavaCall_Unsolved_(sExpression, sExpressionSolved, false, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE_AS_ENTRY);
			} else {
				btemp = testCompute_JavaCall_Unsolved_(sExpression, sExpressionSolved, false, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
			}
			
			//d)
			sExpression = sPre + sExpressionIn + sPost;
			sExpressionSubstituted = sPre + sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT + sPost;
			sExpressionSolved = sPre + sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT + sPost; //auch ohne Solver werden die Pfade substituiert!!!
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ, false);
			//Beim Solven ohne Solver, werden nur die äusseren Z-Tags ggfs. entfernt.
			
			//Der reine Tag, ohne umgebende Z-Tags und Call-Tags
			sTag = sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT;
			sTagSolved = sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT;
			sTagSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sTagSolved, sTagStartZ, sTagEndZ, false); //von aussen nach innen. So bleiben Z-Tags innen(z.B. um den Pfad herum) erhalten.
			sTagSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sTagSolved, KernelCallIniSolverZZZ.sTAG_NAME, false);
            
			if(enumTestSubtype != null && enumTestSubtype == TestSubtype.AS_ENTRY) {
				btemp = testCompute_JavaCall_Unsolved_(sExpression, sExpressionSolved, true, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE_AS_ENTRY);
			}else {
				btemp = testCompute_JavaCall_Unsolved_(sExpression, sExpressionSolved, true, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
			}
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}
	
	
	
	private boolean testCompute_JavaCall_Unexpressed_(String sExpressionIn,String sExpressionSubstitutedIn, String sExpressionSolvedIn, String sTagIn, String sTagSolvedIn, boolean bRemoveSuroundingSeparators, IEnumSetMappedTestCaseZZZ objEnumTestCase) {
		boolean bReturn = false;
		try {
			boolean btemp; 
			
			String sExpression; String sExpressionSubstituted; String sExpressionSolved; String sTag; String sTagSolved; 
			String sValue; 
			ReferenceZZZ<IKernelConfigSectionEntryZZZ> objSectionEntryReference;
			IKernelConfigSectionEntryZZZ objEntry;
			
			IEnumSetMappedTestFlagsetZZZ objEnumFunction = EnumSetMappedTestCaseFlagsetTypeZZZ.UNEXPRESSED;
			
			sExpression = sExpressionIn;
			sExpressionSubstituted = sExpressionSubstitutedIn;
			sExpressionSolved = sExpressionSolvedIn;
			sTag = sTagIn;
			sTagSolved = sTagSolvedIn;
			
			//####################################################################################
			//### EXPRESSIONSOLVER - NICHT EXPRESSION BEHANDLUNG
			//####################################################################################
													
			btemp = objExpressionSolver.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION, false); //Gar keine Expression aktiviert
			assertTrue("Flag nicht vorhanden '" + IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);

			btemp = objExpressionSolver.setFlag(IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER, true); //soll dann egal sein			
			assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER + "'", btemp);
							
			btemp = objExpressionSolver.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH, true);	//soll dann egal sein		
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
		
			btemp = objExpressionSolver.setFlag(IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE, true);	//soll dann egal sein		
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE + "'", btemp);
						
			btemp = objExpressionSolver.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER, true); //soll dann egal sein
			assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
			
			btemp = objExpressionSolver.setFlag(IKernelCallIniSolverZZZ.FLAGZ.USECALL, true); //soll dann egal sein
			assertTrue("Flag nicht vorhanden '" + IKernelCallIniSolverZZZ.FLAGZ.USECALL + "'", btemp);
			
			btemp = objExpressionSolver.setFlag(IKernelJavaCallIniSolverZZZ.FLAGZ.USECALL_JAVA, true); //soll dann egal sein
			assertTrue("Flag nicht vorhanden '" + IKernelJavaCallIniSolverZZZ.FLAGZ.USECALL_JAVA + "'", btemp);
		
			//+++ ... parse ist nicht solve... also wird hier nichts aufgeloest, aussser die Pfade substituiert.
			if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.PARSE)) {							
				objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
				sValue = objExpressionSolver.parse(sExpression, objSectionEntryReference, bRemoveSuroundingSeparators);
				assertEquals(sExpressionSolved, sValue);
				
				sValue = objExpressionSolver.getValue();
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
				sValue = objExpressionSolver.solve(sExpression, objSectionEntryReference, bRemoveSuroundingSeparators);
				assertEquals(sExpressionSolved, sValue);
				
				sValue = objExpressionSolver.getValue();
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
				objEntry = objExpressionSolver.parseAsEntry(sExpression, bRemoveSuroundingSeparators);				
				assertNotNull(objEntry);
							
				sValue = objEntry.getValue();
				assertEquals(sExpressionSolved, sValue);
				
				//Nutze eine Sammlung von assert Methoden, die ein objEntry als input hat.
				//und in der die verschiedenen stati für den unexpressed, unsubstituted, substituted, unsolved, etc Fall stehen.
				btemp = TestUtilAsTestZZZ.assertFileIniEntry(EnumSetMappedTestCaseSolverTypeZZZ.PARSE, objEnumFunction, objEntry, sExpression, sExpressionSubstituted, sExpressionSolved);
				assertTrue(btemp);
			}
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			
			//+++ ... solve verhält sich NICHT wie parse(), bei solve wird aufgeloest...
			if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.SOLVE_AS_ENTRY)) {
				sExpression = sExpressionIn;
				sExpressionSolved = sExpressionSolvedIn;					
				objEntry = objExpressionSolver.solveAsEntry(sExpression, bRemoveSuroundingSeparators);
				assertNotNull(objEntry);
				
				sValue = objEntry.getValue();
				assertEquals(sExpressionSolved, sValue);
				
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

	
	
	
	/** void, Test: Reading an entry in a section of the ini-file
	* Lindhauer; 22.04.2006 12:54:32
	 */
	public void testCompute01parse(){
		boolean btemp; String sSection; String sProperty; String sValue;
		String sExpression; String sExpressionParsed;
		String sTagStartZ;	String sTagEndZ;
		Vector<String> vecReturn;
		
		try {
			//###############################################	
			//### JAVA:CALL Ausdruck aus INI-Datei lesen
			//###############################################
			
			////### Erst ohne Verarbeitung
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
						
			btemp = objExpressionSolver.setFlag(IKernelCallIniSolverZZZ.FLAGZ.USECALL, true); //Sollte dann egal sein
			assertTrue("Flag nicht vorhanden '" + IKernelCallIniSolverZZZ.FLAGZ.USECALL + "'", btemp);
						
			btemp = objExpressionSolver.setFlag(IKernelJavaCallIniSolverZZZ.FLAGZ.USECALL_JAVA, true);//Sollte dann egal sein			
			assertTrue("Flag nicht vorhanden '" + IKernelJavaCallIniSolverZZZ.FLAGZ.USECALL_JAVA + "'", btemp);
			
			
			
			//Anwenden der 1. Formel			
			sExpression = KernelJavaCallIniSolverZZZTest.sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT; 
			sExpressionParsed = sExpression;	
			sValue = objExpressionSolver.parse(sExpression);			
			assertEquals(sExpressionParsed, sValue);
			
			//Beim Parsen fuer den Wert des Tags selbst(!) ist es nicht egal, ob Expression(!) verwendet wird ....
			//Dann wird nicht geparsed!!!
			sExpressionParsed = sExpression;	
			sValue = objExpressionSolver.getValue();
			assertEquals(sExpressionParsed, sValue);
			
			//### Erst ohne Verarbeitung 2
			btemp = objExpressionSolver.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION, true); //nun wird der Wert nach dem Parsen zumindest geaendert 
			assertTrue("Flag nicht vorhanden '" + IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
						
			btemp = objExpressionSolver.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER, false);
			assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
			
			btemp = objExpressionSolver.setFlag(IKernelCallIniSolverZZZ.FLAGZ.USECALL, true); //Sollte dann egal sein
			assertTrue("Flag nicht vorhanden '" + IKernelCallIniSolverZZZ.FLAGZ.USECALL + "'", btemp);
						
			btemp = objExpressionSolver.setFlag(IKernelJavaCallIniSolverZZZ.FLAGZ.USECALL_JAVA, true);//Sollte dann egal sein			
			assertTrue("Flag nicht vorhanden '" + IKernelJavaCallIniSolverZZZ.FLAGZ.USECALL_JAVA + "'", btemp);
		
			btemp = objExpressionSolver.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH, true);			
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
		
			btemp = objExpressionSolver.setFlag(IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE, true);			
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE + "'", btemp);
		
			sExpression = KernelJavaCallIniSolverZZZTest.sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT;

			//Es muss beim Parsen fuer den Wert des Tags selbst(!) ist es egal, ob ein Solver(!) verwendet wird ....
			sTagStartZ = "<Z>"; //Zwar wird der Solver nicht ausgefuehrt, aber die Expression wird schon aufgeloest.
			sTagEndZ = "</Z>";
			sExpressionParsed = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpression, sTagStartZ, sTagEndZ);
				
			sValue = objExpressionSolver.parse(sExpression);
			assertEquals(sExpressionParsed, sValue);
			
			
			sTagStartZ = "<Z>"; //Zwar wird der Solver nicht ausgefuehrt, aber die Expression wird schon aufgeloest.
			sTagEndZ = "</Z>";
			sExpressionParsed = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpression, sTagStartZ, sTagEndZ);
			
			sTagStartZ = "<Z:Call>"; //Ist ja nicht innerhalb des Tags vorhanden
			sTagEndZ = "</Z:Call>";
			sExpressionParsed = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionParsed, sTagStartZ, sTagEndZ);
			
			sTagStartZ = "<Z:Java>"; //Ist ja nicht innerhalb des Tags vorhanden
			sTagEndZ = "</Z:Java>";
			sExpressionParsed = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionParsed, sTagStartZ, sTagEndZ);							
			//sExpressionParsed = "<Z:Class>basic.zBasic.util.machine.EnvironmentZZZ</Z:Class><Z:Method>getHostName</Z:Method>";
			sValue = objExpressionSolver.getValue();
			assertEquals(sExpressionParsed, sValue);
			
			//### Teilberechnungen durchführen
			btemp = objExpressionSolver.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION, true); //nun wird der Wert nach dem Parsen zumindest geaendert 
			assertTrue("Flag nicht vorhanden '" + IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
			
			btemp = objExpressionSolver.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER, true);
			assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
						
			btemp = objExpressionSolver.setFlag(IKernelCallIniSolverZZZ.FLAGZ.USECALL, false); 
			assertTrue("Flag nicht vorhanden '" + IKernelCallIniSolverZZZ.FLAGZ.USECALL + "'", btemp);
						
			btemp = objExpressionSolver.setFlag(IKernelJavaCallIniSolverZZZ.FLAGZ.USECALL_JAVA, true);//Sollte dann egal sein			
			assertTrue("Flag nicht vorhanden '" + IKernelJavaCallIniSolverZZZ.FLAGZ.USECALL_JAVA + "'", btemp);
		
			btemp = objExpressionSolver.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH, true);			
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
		
			btemp = objExpressionSolver.setFlag(IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE, true);			
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE + "'", btemp);
		
			
			sExpression = KernelJavaCallIniSolverZZZTest.sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT;
			
			sTagStartZ = "<Z>";
			sTagEndZ = "</Z>";
			sExpressionParsed = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpression, sTagStartZ, sTagEndZ);
			sExpressionParsed = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionParsed, objExpressionSolver.getName());
			sValue = objExpressionSolver.parse(sExpression);
			assertEquals(sExpressionParsed, sValue);
			
			//Es muss beim Parsen fuer den Wert des Tags selbst(!) egal sein, ob USECALL verwendet wird ....
			sTagStartZ = "<Z>"; //Zwar wird der Solver nicht ausgefuehrt, aber die Expression wird schon aufgeloest.
			sTagEndZ = "</Z>";
			sExpressionParsed = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpression, sTagStartZ, sTagEndZ);
			
			sTagStartZ = "<Z:Call>"; //Ist ja nicht innerhalb des Tags vorhanden
			sTagEndZ = "</Z:Call>";
			sExpressionParsed = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionParsed, sTagStartZ, sTagEndZ);
			
			sTagStartZ = "<Z:Java>"; //Ist ja nicht innerhalb des Tags vorhanden
			sTagEndZ = "</Z:Java>";
			sExpressionParsed = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionParsed, sTagStartZ, sTagEndZ);							
			//sExpressionParsed = "<Z:Class>basic.zBasic.util.machine.EnvironmentZZZ</Z:Class><Z:Method>getHostName</Z:Method>";
		
			sValue = objExpressionSolver.getValue();
			assertEquals(sExpressionParsed, sValue);
			
					
			//### Nun die Gesamtberechnung durchführen
			btemp = objExpressionSolver.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION, true); //nun wird der Wert nach dem Parsen zumindest geaendert 
			assertTrue("Flag nicht vorhanden '" + IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
			
			btemp = objExpressionSolver.setFlag(IKernelCallIniSolverZZZ.FLAGZ.USECALL, true); 
			assertTrue("Flag nicht vorhanden '" + IKernelCallIniSolverZZZ.FLAGZ.USECALL + "'", btemp);
						
			btemp = objExpressionSolver.setFlag(IKernelJavaCallIniSolverZZZ.FLAGZ.USECALL_JAVA, true);			
			assertTrue("Flag nicht vorhanden '" + IKernelJavaCallIniSolverZZZ.FLAGZ.USECALL_JAVA + "'", btemp);
		
			sExpression = KernelJavaCallIniSolverZZZTest.sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT;
						
			//Hier findet nur parse() statt. Darum wird nix aufgeloest, egal welche Flags gesetzt sind.
			sTagStartZ = "<Z>";
			sTagEndZ = "</Z>";
			sExpressionParsed = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpression, sTagStartZ, sTagEndZ);
			sExpressionParsed = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionParsed, objExpressionSolver.getName());
			sValue = objExpressionSolver.parse(sExpression);			
			System.out.println(ReflectCodeZZZ.getPositionCurrent() + "\tDebugausagabe: '" + sValue + "'\n");
			assertEquals(sExpressionParsed, sValue);
			
			//Es muss beim Parsen fuer den Wert des Tags selbst(!) egal sein, ob USECALL verwendet wird ....
			sTagStartZ = "<Z>"; //Zwar wird der Solver nicht ausgefuehrt, aber die Expression wird schon aufgeloest.
			sTagEndZ = "</Z>";
			sExpressionParsed = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpression, sTagStartZ, sTagEndZ);
			
			sTagStartZ = "<Z:Call>"; //Ist ja nicht innerhalb des Tags vorhanden
			sTagEndZ = "</Z:Call>";
			sExpressionParsed = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionParsed, sTagStartZ, sTagEndZ);
			
			sTagStartZ = "<Z:Java>"; //Ist ja nicht innerhalb des Tags vorhanden
			sTagEndZ = "</Z:Java>";
			sExpressionParsed = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionParsed, sTagStartZ, sTagEndZ);							
			//sExpressionParsed = "<Z:Class>basic.zBasic.util.machine.EnvironmentZZZ</Z:Class><Z:Method>getHostName</Z:Method>";
		
			sValue = objExpressionSolver.getValue();
			assertEquals(sExpressionParsed, sValue);	
		//++++++++++++++++++++++++++++++++++++++++++++++++++
			
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}
	
	/** void, Test: Reading an entry in a section of the ini-file
	* Lindhauer; 22.04.2006 12:54:32
	 */
	public void testCompute01solve(){
		boolean btemp; String sSection; String sProperty; String sValue;
		String sExpression; String sExpressionSolved;
		String sTagStartZ;	String sTagEndZ;
		Vector<String> vecReturn;
		
		try {
			//###############################################	
			//### JAVA:CALL Ausdruck aus INI-Datei lesen
			//###############################################
			String sHostName = EnvironmentZZZ.getHostName(); ////Den Rechnernamen dynamisch ermitteln..., z.B.: "HannibalDEV04bVM";
			
			
			////### Erst ohne Verarbeitung
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
						
			btemp = objExpressionSolver.setFlag(IKernelCallIniSolverZZZ.FLAGZ.USECALL, true); //Sollte dann egal sein
			assertTrue("Flag nicht vorhanden '" + IKernelCallIniSolverZZZ.FLAGZ.USECALL + "'", btemp);
						
			btemp = objExpressionSolver.setFlag(IKernelJavaCallIniSolverZZZ.FLAGZ.USECALL_JAVA, true);//Sollte dann egal sein			
			assertTrue("Flag nicht vorhanden '" + IKernelJavaCallIniSolverZZZ.FLAGZ.USECALL_JAVA + "'", btemp);
			
			
			
			//Anwenden der 1. Formel			
			sExpression = KernelJavaCallIniSolverZZZTest.sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT; 
			sExpressionSolved = sExpression;	
			sValue = objExpressionSolver.solve(sExpression);			
			assertEquals(sExpressionSolved, sValue);
			
			//### Erst ohne Verarbeitung 2
			btemp = objExpressionSolver.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION, true); //nun wird der Wert nach dem Parsen zumindest geaendert 
			assertTrue("Flag nicht vorhanden '" + IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
						
			btemp = objExpressionSolver.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER, false);
			assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
			
			btemp = objExpressionSolver.setFlag(IKernelCallIniSolverZZZ.FLAGZ.USECALL, true); //Sollte dann egal sein
			assertTrue("Flag nicht vorhanden '" + IKernelCallIniSolverZZZ.FLAGZ.USECALL + "'", btemp);
						
			btemp = objExpressionSolver.setFlag(IKernelJavaCallIniSolverZZZ.FLAGZ.USECALL_JAVA, true);//Sollte dann egal sein			
			assertTrue("Flag nicht vorhanden '" + IKernelJavaCallIniSolverZZZ.FLAGZ.USECALL_JAVA + "'", btemp);
		
			btemp = objExpressionSolver.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH, true);			
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
		
			btemp = objExpressionSolver.setFlag(IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE, true);			
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE + "'", btemp);
		
			sExpression = KernelJavaCallIniSolverZZZTest.sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT;
			
			sTagStartZ = "<Z>"; //Zwar wird der Solver nicht ausgefuehrt, aber die Expression wird schon aufgeloest.
			sTagEndZ = "</Z>";
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpression, sTagStartZ, sTagEndZ);
				
			sValue = objExpressionSolver.solve(sExpression);
			assertEquals(sExpressionSolved, sValue);
			
			
			//### Teilberechnungen durchführen
			btemp = objExpressionSolver.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION, true); //nun wird der Wert nach dem Parsen zumindest geaendert 
			assertTrue("Flag nicht vorhanden '" + IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
			
			btemp = objExpressionSolver.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER, true);
			assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
						
			btemp = objExpressionSolver.setFlag(IKernelCallIniSolverZZZ.FLAGZ.USECALL, false); 
			assertTrue("Flag nicht vorhanden '" + IKernelCallIniSolverZZZ.FLAGZ.USECALL + "'", btemp);
						
			btemp = objExpressionSolver.setFlag(IKernelJavaCallIniSolverZZZ.FLAGZ.USECALL_JAVA, true);//Sollte dann egal sein			
			assertTrue("Flag nicht vorhanden '" + IKernelJavaCallIniSolverZZZ.FLAGZ.USECALL_JAVA + "'", btemp);
		
			btemp = objExpressionSolver.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH, true);			
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
		
			btemp = objExpressionSolver.setFlag(IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE, true);			
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE + "'", btemp);
		
			
			sExpression = KernelJavaCallIniSolverZZZTest.sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT;		
			sExpressionSolved = "<Z:Call>" + sHostName + "</Z:Call>";		//Da der Z-Call Solver nicht involviert ist, ist das quasi wie einfacher Text zu behandeln.
			sValue = objExpressionSolver.solve(sExpression);
			assertEquals(sExpressionSolved, sValue);
			
					
			//### Nun die Gesamtberechnung durchführen
			btemp = objExpressionSolver.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION, true); //nun wird der Wert nach dem Parsen zumindest geaendert 
			assertTrue("Flag nicht vorhanden '" + IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
			
			btemp = objExpressionSolver.setFlag(IKernelCallIniSolverZZZ.FLAGZ.USECALL, true); 
			assertTrue("Flag nicht vorhanden '" + IKernelCallIniSolverZZZ.FLAGZ.USECALL + "'", btemp);
						
			btemp = objExpressionSolver.setFlag(IKernelJavaCallIniSolverZZZ.FLAGZ.USECALL_JAVA, true);			
			assertTrue("Flag nicht vorhanden '" + IKernelJavaCallIniSolverZZZ.FLAGZ.USECALL_JAVA + "'", btemp);
		
			sExpression = KernelJavaCallIniSolverZZZTest.sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT;
						
			//Es sollte nur der Rechnername uebrigbleiben... Z-Tag raus ABER Z:Call-Tag NICHT raus, da der Call Solver nicht als Objekt hier verwendet wird...			
			sExpressionSolved = "<Z:Call>" + sHostName + "</Z:Call>"; ////Den Rechnernamen dynamisch ermitteln..., z.B.: "HannibalDEV04bVM";						
			sValue = objExpressionSolver.solve(sExpression);			
			System.out.println(ReflectCodeZZZ.getPositionCurrent() + "\tDebugausagabe: '" + sValue + "'\n");
			assertEquals(sExpressionSolved, sValue);
			
		
			
		//++++++++++++++++++++++++++++++++++++++++++++++++++
			
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}
		
	/** void, Test: Reading an entry in a section of the ini-file
	* Lindhauer; 22.04.2006 12:54:32
	 */
	public void testCompute01parseAsEntry(){
		try {		
			boolean btemp; String sSection; String sProperty; String sValue; boolean bValue; 
			String sClassname;	String sMethodname;								
			String sExpressionSource; String sExpression; String sExpressionSolved; IKernelConfigSectionEntryZZZ objEntry;
			String sTagStartZ;	String sTagEndZ; String sHostName;
			Vector<String> vecReturn;
			
			String sLineWithExpression = KernelJavaCallIniSolverZZZTest.sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT;
			
			//###############################################	
			//### JAVA:CALL Ausdruck aus INI-Datei lesen. Es geht um das Entry-Objekt
			//###############################################
							
			//### Nun die Gesamtberechnung durchführen
			btemp = objExpressionSolver.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION, true); //nun wird der Wert nach dem Parsen zumindest geaendert 
			assertTrue("Flag nicht vorhanden '" + IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
			
			btemp = objExpressionSolver.setFlag(IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER,true); //ohne aktivierten Parser keine SUBSTITUTION  
			assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER + "'", btemp);
			
			btemp = objExpressionSolver.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH, true);			
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
		
			btemp = objExpressionSolver.setFlag(IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE, true);			
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE + "'", btemp);		
			
			btemp = objExpressionSolver.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER, true);
			assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
							
			btemp = objExpressionSolver.setFlag(IKernelCallIniSolverZZZ.FLAGZ.USECALL, true); 
			assertTrue("Flag nicht vorhanden '" + IKernelCallIniSolverZZZ.FLAGZ.USECALL + "'", btemp);
						
			btemp = objExpressionSolver.setFlag(IKernelJavaCallIniSolverZZZ.FLAGZ.USECALL_JAVA, true);			
			assertTrue("Flag nicht vorhanden '" + IKernelJavaCallIniSolverZZZ.FLAGZ.USECALL_JAVA + "'", btemp);
		
			
			
			sExpression = KernelJavaCallIniSolverZZZTest.sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT;
						
			//Es sollte nur der Rechnername uebrigbleiben... Z-Tag raus UND Z:Call-Tag auch raus			
			sHostName = EnvironmentZZZ.getHostName(); ////Den Rechnernamen dynamisch ermitteln..., z.B.: "HannibalDEV04bVM";
			
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			objEntry = objExpressionSolver.parseAsEntry(sLineWithExpression);
			assertNotNull(objEntry);
			sValue = objEntry.getValue();
			System.out.println(ReflectCodeZZZ.getPositionCurrent() + "\tDebugausagabe: '" + sValue + "'\n");
			
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpression, "Z");
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, objExpressionSolver.getName());
			assertEquals("Ohne Auflösung soll Ausgabe gleich Eingabe sein, abzueglich der Z-Tags, die wg. Parse wegfallen.",sExpressionSolved, sValue);

			//Merke: Da der Call-Solver nicht verwendet wird, kann hier nur false sein...
			bValue = objEntry.isCall();
			assertFalse(bValue);
			
			//Hier hingegen muss true stehen.
			bValue = objEntry.isJavaCall();
			assertTrue(bValue);
			
			//ohne solve wird das bisher nicht gemacht
			sClassname = objEntry.getCallingClassname();
			assertNull("NULL erwartet. Wert ist aber '" + sClassname + "'", sClassname);			
			
			//ohne solve wird das bisher nicht gemacht
			sMethodname = objEntry.getCallingMethodname();
			assertNull("NULL erwartet. Wert ist aber '" + sMethodname + "'", sMethodname);

			

			
			//++++++++++++++++++++++++++++++++++++++++++++++++++
			
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}		
	
	/** void, Test: Reading an entry in a section of the ini-file
	* Lindhauer; 22.04.2006 12:54:32
	 */
	public void testCompute01solveAsEntry(){
		try {		
			boolean btemp; String sSection; String sProperty; String sValue; boolean bValue; 
			String sClassname;	String sMethodname;								
			String sExpressionSource; String sExpression; String sExpressionSolved; IKernelConfigSectionEntryZZZ objEntry;
			String sTagStartZ;	String sTagEndZ; String sHostName;
			Vector<String> vecReturn;
			
			String sLineWithExpression = KernelJavaCallIniSolverZZZTest.sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT;
			
			//###############################################	
			//### JAVA:CALL Ausdruck aus INI-Datei lesen. Es geht um das Entry-Objekt
			//###############################################
							
			//### Nun die Gesamtberechnung durchführen
			btemp = objExpressionSolver.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION, true); //nun wird der Wert nach dem Parsen zumindest geaendert 
			assertTrue("Flag nicht vorhanden '" + IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
			
			btemp = objExpressionSolver.setFlag(IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER,true); //ohne aktivierten Parser keine SUBSTITUTION  
			assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER + "'", btemp);
			
			btemp = objExpressionSolver.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH, true);			
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
		
			btemp = objExpressionSolver.setFlag(IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE, true);			
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE + "'", btemp);		
			
			btemp = objExpressionSolver.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER, true);
			assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
							
			btemp = objExpressionSolver.setFlag(IKernelCallIniSolverZZZ.FLAGZ.USECALL, true); 
			assertTrue("Flag nicht vorhanden '" + IKernelCallIniSolverZZZ.FLAGZ.USECALL + "'", btemp);
						
			btemp = objExpressionSolver.setFlag(IKernelJavaCallIniSolverZZZ.FLAGZ.USECALL_JAVA, true);			
			assertTrue("Flag nicht vorhanden '" + IKernelJavaCallIniSolverZZZ.FLAGZ.USECALL_JAVA + "'", btemp);
		
			
			
			sExpression = KernelJavaCallIniSolverZZZTest.sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT;
						
			//Es sollte nur der Rechnername uebrigbleiben... Z-Tag raus UND Z:Call-Tag auch raus			
			sHostName = EnvironmentZZZ.getHostName(); ////Den Rechnernamen dynamisch ermitteln..., z.B.: "HannibalDEV04bVM";
			
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			objEntry = objExpressionSolver.solveAsEntry(sLineWithExpression);
			assertNotNull(objEntry);
			sValue = objEntry.getValue();
			System.out.println(ReflectCodeZZZ.getPositionCurrent() + "\tDebugausagabe: '" + sValue + "'\n");
			
			//Merke: Da der Call-Solver nicht verwendet wird, sind seine Tags drumherum...
			sExpressionSolved = "<Z:Call>" + sHostName + "</Z:Call>";
			assertEquals("Mit Aufloesung ist das der zu errechnende Wert, ohne Tags drumherum (ausser dem hier nicht verwendeten Call-Tag).",sExpressionSolved, sValue);
	
			//Merke: Da der Call-Solver nicht verwendet wird, kann hier nur false sein...
			bValue = objEntry.isCall();
			assertFalse(bValue);
			
			//Hier hingegen muss true stehen.
			bValue = objEntry.isJavaCall();
			assertTrue(bValue);
			
			sClassname = objEntry.getCallingClassname();		
			System.out.println(ReflectCodeZZZ.getPositionCurrent() + "\tDebugausgabe3: '" + sClassname + "'\n");
			assertEquals("basic.zBasic.util.machine.EnvironmentZZZ",sClassname);
			
			//ohne solve wird das bisher nicht gemacht
			sMethodname = objEntry.getCallingMethodname();			
			System.out.println(ReflectCodeZZZ.getPositionCurrent() + "\tDebugausgabe4: '" + sMethodname + "'\n");
			assertEquals("getHostName",sMethodname);
						
			//++++++++++++++++++++++++++++++++++++++++++++++++++
			
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}		
}//END class
		

