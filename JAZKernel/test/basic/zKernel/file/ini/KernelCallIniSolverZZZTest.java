package basic.zKernel.file.ini;

import java.io.File;
import java.util.Vector;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IObjectWithExpressionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedTestCaseZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedTestFlagsetZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedTestSurroundingZZZ;
import basic.zBasic.util.abstractList.Vector3ZZZ;
import basic.zBasic.util.abstractList.VectorUtilZZZ;
import basic.zBasic.util.abstractList.VectorZZZ;
import basic.zBasic.util.datatype.calling.ReferenceZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.datatype.xml.XmlUtilZZZ;
import basic.zBasic.util.machine.EnvironmentZZZ;
import basic.zKernel.IKernelConfigSectionEntryZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernel.KernelConfigSectionEntryZZZ;
import basic.zKernel.KernelZZZ;
import basic.zKernel.config.KernelConfigSectionEntryUtilZZZ;
import custom.zKernel.file.ini.FileIniZZZ;
import junit.framework.TestCase;

public class KernelCallIniSolverZZZTest  extends TestCase {
		public final static String sEXPRESSION_JAVACALL01_PATHSECTION = KernelJavaCallIniSolverZZZTest.sEXPRESSION_CALL01_PATHSECTION;
		public final static String sEXPRESSION_JAVACALL01_CLASS_PATHPROPERTY = KernelJavaCallIniSolverZZZTest.sEXPRESSION_CALL01_CLASS_PATHPROPERTY;
		public final static String sEXPRESSION_JAVACALL01_METHOD_PATHPROPERTY = KernelJavaCallIniSolverZZZTest.sEXPRESSION_CALL01_METHOD_PATHPROPERTY;
		public final static String sEXPRESSION_JAVACALL01_DEFAULT = KernelJavaCallIniSolverZZZTest.sEXPRESSION_CALL01_DEFAULT;
		public final static String sEXPRESSION_JAVACALL01_CLASS_DEFAULT = KernelJavaCallIniSolverZZZTest.sEXPRESSION_CALL01_CLASS_DEFAULT;
		public final static String sEXPRESSION_JAVACALL01_METHOD_DEFAULT = KernelJavaCallIniSolverZZZTest.sEXPRESSION_CALL01_METHOD_DEFAULT;
		//public final static String sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT = "<Z><Z:Call><Z:Java><Z:Class>" + KernelJavaCallIniSolverZZZTest.sEXPRESSION_CALL01_CLASS_DEFAULT + "</Z:Class><Z:Method>" + KernelJavaCallIniSolverZZZTest.sEXPRESSION_CALL01_METHOD_DEFAULT + "</Z:Method></Z:Java></Z:Call></Z>";
		public final static String sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT = KernelJavaCallIniSolverZZZTest.sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT;
		
		
		//public final static String sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT = "<Z><Z:Call><Z:Java><Z:Class>basic.zBasic.util.machine.EnvironmentZZZ</Z:Class><Z:Method>getHostName</Z:Method></Z:Java></Z:Call></Z>";
		
		public enum TestSubtype{
				DEFAULT,
				AS_ENTRY
		}
		
		private File objFile;
		private IKernelZZZ objKernel;
		private FileIniZZZ objFileIniTest=null;
		
		
		/// +++ Die eigentlichen Test-Objekte	
		private KernelCallIniSolverZZZ objExpressionSolver;
		private KernelCallIniSolverZZZ objExpressionSolverInit;
		
		

		protected void setUp(){
			try {			
				
				//Mache die utility Klasse TestUtilZZZ.createKernelFileUsed()
				//Diese sollten alle Tests verwenden und nicht mehr die im Projekt hinterlegte ini-Datei.
				//Hintergrund: Es fehlen sonst ggfs. Einträge / Pfade, bzw. die sind nicht mehr aktuell!!!!
													
				//#################
				objKernel = new KernelZZZ("FGL", "01", "test", "ZKernelConfigKernel_test.ini",(String)null);
				
				//#### Ein init TestObjekt
				String[] saFlagInit = {"init"};
				objExpressionSolverInit = new KernelCallIniSolverZZZ(objKernel, saFlagInit);
					
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
				objExpressionSolver = new KernelCallIniSolverZZZ(objKernel, objFileIniTest, saFlag);

			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			} 
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
//			try {
			
			//##################################
			//### Vorgezogener letzter Fehlertest: START

			//++++++++++ PRE / POST
			sExpressionSource = KernelCallIniSolverZZZTest.sEXPRESSION_JAVACALL01_DEFAULT;
			testCompute_Call_(sExpressionSource, "PRE", "POST", TestSubtype.AS_ENTRY);
			
			
			sExpressionSource = KernelCallIniSolverZZZTest.sEXPRESSION_JAVACALL01_DEFAULT;
			testCompute_Call_(sExpressionSource, "PRE", "POST", TestSubtype.DEFAULT);
			
			

		
			//### Vorgezogener letzter Fehlertest: ENDE
			//##################################
		
		
			//Teste die Pfadersetzung, die nicht nur im KernelExpresssionIniHandlerZZZ funktionieren soll.
			sExpressionSource = KernelCallIniSolverZZZTest.sEXPRESSION_JAVACALL01_DEFAULT; 
			testCompute_Call_(sExpressionSource,"","", TestSubtype.DEFAULT);
			
			sExpressionSource = KernelCallIniSolverZZZTest.sEXPRESSION_JAVACALL01_DEFAULT; 
			testCompute_Call_(sExpressionSource,"","", TestSubtype.AS_ENTRY);
			
			
			//Test ohne notwendige Pfadersetzung
			sExpressionSource = KernelCallIniSolverZZZTest.sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT; 
			testCompute_Call_(sExpressionSource,"","", TestSubtype.DEFAULT);

			sExpressionSource = KernelCallIniSolverZZZTest.sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT; 
			testCompute_Call_(sExpressionSource,"","", TestSubtype.AS_ENTRY);

			
			//++++++++++ PRE / POST
			sExpressionSource = KernelCallIniSolverZZZTest.sEXPRESSION_JAVACALL01_DEFAULT;
			testCompute_Call_(sExpressionSource, "PRE", "POST", TestSubtype.DEFAULT);
			
			sExpressionSource = KernelCallIniSolverZZZTest.sEXPRESSION_JAVACALL01_DEFAULT;
			testCompute_Call_(sExpressionSource, "PRE", "POST", TestSubtype.AS_ENTRY);
			
//			} catch (ExceptionZZZ ez) {
//				fail("Method throws an exception." + ez.getMessageLast());
//			}
	}
		
		
		
		/**void, Test: Reading an entry in a section of the ini-file
		 * 
		 * @author Fritz Lindhauer, 05.05.2023, 08:54:30
		 */
		private void testCompute_Call_(String sExpressionIn, String sPreIn, String sPostIn, TestSubtype enumTestSubtype){
			//SIEHE AUCH testCompute_CallWithPrePost
			boolean btemp; int itemp;
			
			String sSection; String sProperty;
			String sExpression; String sExpressionSubstituted; String sExpressionSolved; String sTag; String sTagSolved;
			ReferenceZZZ<IKernelConfigSectionEntryZZZ>objSectionEntryReference=null; IKernelConfigSectionEntryZZZ objEntry=null; 
		
			String sValue;
			String sTagStartZ = "<Z>";
			String sTagEndZ = "</Z>";	
				
			String sPre = sPreIn;
			String sPost = sPostIn;
			
			try {		
				String sHostName = EnvironmentZZZ.getHostName();
				assertNotNull(sHostName);
						
				//+++++++ VORGEZOGENER LETZTER FEHLERTEST START
				
				//+++ Ohne Call-Berechnung
				
				//d) Solve ... mit Entfernen der umgebenden Z-Tags
				sExpression = sPre + sExpressionIn + sPost;
				sExpressionSubstituted = sPre + sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT + sPost;
				sExpressionSolved = sPre + sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT + sPost; //auch ohne Solver werden die Pfade substituiert!!!
				sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ, false);//DerGesamtsolver sollte aber ausgefuehrt werden und fuer das Entfernen der Z-Tags sorgen!!!
				
				//Der reine Tag, ohne umgebende Z-Tags und Call-Tags
				sTag = sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT;
				sTagSolved = sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT;
				sTagSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sTagSolved, sTagStartZ, sTagEndZ, false); //von aussen nach innen. So bleiben Z-Tags innen(z.B. um den Pfad herum) erhalten.
				sTagSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sTagSolved, KernelCallIniSolverZZZ.sTAG_NAME, false);
                
				if(enumTestSubtype != null && enumTestSubtype == TestSubtype.AS_ENTRY) {
					btemp = testCompute_Call_CallUnsolved_(sExpression,  sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved, EnumSetMappedTestSurroundingTypeZZZ.SOLVE_REMOVE, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE_AS_ENTRY);
				} else {
					btemp = testCompute_Call_CallUnsolved_(sExpression,  sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved, EnumSetMappedTestSurroundingTypeZZZ.SOLVE_REMOVE, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
				}
				

				//+++++++ VORGEZOGENER LETZTER FEHLERTEST ENDE
				
				
				
				//###########################
			    //### objExpression
				//#########################
				
				//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
				//+++ Ohne jegliche Expression-Berechnung
				//a) Parse ... ohne Entfernen der umgebenden Z-Tags
				sExpression = sPre + sExpressionIn + sPost;
				sExpressionSubstituted = sExpression;
				sExpressionSolved = sPre + sExpressionIn + sPost;
				
				//Der reine Tag, ohne umgebende Z-Tags und Call-Tags
				sTag = null; //ohne Expression-Auswertung
				sTagSolved = null;
				sTagSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sTagSolved, sTagStartZ, sTagEndZ, false); //von aussen nach innen. So bleiben Z-Tags innen(z.B. um den Pfad herum) erhalten.
				sTagSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sTagSolved, KernelCallIniSolverZZZ.sTAG_NAME, false);
                
				if(enumTestSubtype != null && enumTestSubtype == TestSubtype.AS_ENTRY) {
					btemp = testCompute_Call_Unexpressed_(sExpression, sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved, EnumSetMappedTestSurroundingTypeZZZ.PARSE_KEEP, EnumSetMappedTestCaseSolverTypeZZZ.PARSE_AS_ENTRY);
				}else {
					btemp = testCompute_Call_Unexpressed_(sExpression, sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved, EnumSetMappedTestSurroundingTypeZZZ.PARSE_KEEP, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
				}
				
				//b) Parse ... mit Entfernen der umgebenden Z-Tags
				sExpression = sPre + sExpressionIn + sPost;
				sExpressionSubstituted = sExpression;
				sExpressionSolved = sPre + sExpressionIn + sPost;
				
				//Der reine Tag, ohne umgebende Z-Tags und Call-Tags
				sTag = null;//ohne Expression-Auswertung
				sTagSolved = null;
				sTagSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sTagSolved, sTagStartZ, sTagEndZ, false); //von aussen nach innen. So bleiben Z-Tags innen(z.B. um den Pfad herum) erhalten.
				sTagSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sTagSolved, KernelCallIniSolverZZZ.sTAG_NAME, false);

				if(enumTestSubtype != null && enumTestSubtype == TestSubtype.AS_ENTRY) {
					btemp = testCompute_Call_Unexpressed_(sExpression,sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved, EnumSetMappedTestSurroundingTypeZZZ.PARSE_REMOVE, EnumSetMappedTestCaseSolverTypeZZZ.PARSE_AS_ENTRY);
				}else {
					btemp = testCompute_Call_Unexpressed_(sExpression,sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved, EnumSetMappedTestSurroundingTypeZZZ.PARSE_REMOVE, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
				}
				
				//c) Solve ... OHNE Entfernen der umgebenden Z-Tags
				sExpression = sPre + sExpressionIn + sPost;
				sExpressionSubstituted = sExpression;
				sExpressionSolved = sPre + sExpressionIn + sPost; 	
				
				//Der reine Tag, ohne umgebende Z-Tags und Call-Tags
				sTag = null;//ohne Expression-Auswertung
				sTagSolved = null;
				sTagSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sTagSolved, sTagStartZ, sTagEndZ, false); //von aussen nach innen. So bleiben Z-Tags innen(z.B. um den Pfad herum) erhalten.
				sTagSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sTagSolved, KernelCallIniSolverZZZ.sTAG_NAME, false);
                
				if(enumTestSubtype != null && enumTestSubtype == TestSubtype.AS_ENTRY) {
					btemp = testCompute_Call_Unexpressed_(sExpression, sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved, EnumSetMappedTestSurroundingTypeZZZ.SOLVE_KEEP, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE_AS_ENTRY);
				} else {
					btemp = testCompute_Call_Unexpressed_(sExpression, sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved, EnumSetMappedTestSurroundingTypeZZZ.SOLVE_KEEP, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
				}
				
					
				//d) Solve ... mit Entfernen der umgebenden Z-Tags
				sExpression = sPre + sExpressionIn + sPost;
				sExpressionSubstituted = sExpression;
				sExpressionSolved = sPre + sExpressionIn + sPost;
				
				//Der reine Tag, ohne umgebende Z-Tags und Call-Tags
				sTag = null;//ohne Expression-Auswertung
				sTagSolved = null;
				sTagSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sTagSolved, sTagStartZ, sTagEndZ, false); //von aussen nach innen. So bleiben Z-Tags innen(z.B. um den Pfad herum) erhalten.
				sTagSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sTagSolved, KernelCallIniSolverZZZ.sTAG_NAME, false);
                
				if(enumTestSubtype != null && enumTestSubtype == TestSubtype.AS_ENTRY) {
					btemp = testCompute_Call_Unexpressed_(sExpression, sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved, EnumSetMappedTestSurroundingTypeZZZ.SOLVE_REMOVE, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE_AS_ENTRY);
				}else {
					btemp = testCompute_Call_Unexpressed_(sExpression, sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved, EnumSetMappedTestSurroundingTypeZZZ.SOLVE_REMOVE, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
				}
				
				//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
				//+++ Ohne Solver-Berechnung	(Ergebnisse muessen so sein wie bei Parse)
				//a) Parse ... ohne Entfernen der umgebenden Z-Tags
				sExpression = sPre + sExpressionIn + sPost;
				sExpressionSubstituted = sPre + sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT + sPost;
				sExpressionSolved = sPre + sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT + sPost; //auch ohne Solver werden die Pfade substituiert!!!
				
				//Der reine Tag, ohne umgebende Z-Tags und Call-Tags
				sTag = sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT;
				sTagSolved = sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT;
				sTagSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sTagSolved, sTagStartZ, sTagEndZ, false); //von aussen nach innen. So bleiben Z-Tags innen(z.B. um den Pfad herum) erhalten.
				sTagSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sTagSolved, KernelCallIniSolverZZZ.sTAG_NAME, false);
                
				if(enumTestSubtype != null && enumTestSubtype == TestSubtype.AS_ENTRY) {
					btemp = testCompute_Call_Unsolved_(sExpression, sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved, EnumSetMappedTestSurroundingTypeZZZ.PARSE_KEEP, EnumSetMappedTestCaseSolverTypeZZZ.PARSE_AS_ENTRY);
				}else {
					btemp = testCompute_Call_Unsolved_(sExpression, sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved, EnumSetMappedTestSurroundingTypeZZZ.PARSE_KEEP, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
				}
				
				//b) Parse ... mit Entfernen der umgebenden Z-Tags
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
					btemp = testCompute_Call_Unsolved_(sExpression, sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved, EnumSetMappedTestSurroundingTypeZZZ.PARSE_REMOVE, EnumSetMappedTestCaseSolverTypeZZZ.PARSE_AS_ENTRY);
				} else {
					btemp = testCompute_Call_Unsolved_(sExpression, sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved, EnumSetMappedTestSurroundingTypeZZZ.PARSE_REMOVE, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
				}

				//c) Solve ... ohne Entfernen der umgebenden Z-Tags
				sExpression = sPre + sExpressionIn + sPost;
				sExpressionSubstituted = sPre + sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT + sPost;
				sExpressionSolved = sPre + sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT + sPost; //auch ohne Solver werden die Pfade substituiert!!!
				//Beim Solven ohne Solver, bleibt eh alles wie est ist.
				
				//Der reine Tag, ohne umgebende Z-Tags und Call-Tags
				sTag = sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT;
				sTagSolved = sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT;
				sTagSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sTagSolved, sTagStartZ, sTagEndZ, false); //von aussen nach innen. So bleiben Z-Tags innen(z.B. um den Pfad herum) erhalten.
				sTagSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sTagSolved, KernelCallIniSolverZZZ.sTAG_NAME, false);
                
				if(enumTestSubtype != null && enumTestSubtype == TestSubtype.AS_ENTRY) {
					btemp = testCompute_Call_Unsolved_(sExpression, sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved, EnumSetMappedTestSurroundingTypeZZZ.SOLVE_KEEP, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE_AS_ENTRY);
				} else {
					btemp = testCompute_Call_Unsolved_(sExpression, sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved, EnumSetMappedTestSurroundingTypeZZZ.SOLVE_KEEP, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
				}
				
				//d) Solve ... mit Entfernen der umgebenden Z-Tags
				sExpression = sPre + sExpressionIn + sPost;
				sExpressionSubstituted = sPre + sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT + sPost;
				sExpressionSolved = sPre + sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT + sPost; //auch ohne Solver werden die Pfade substituiert!!!				
				//Ohne Solver, bleibt es wie es ist. Zudem: Beim Solven GANZ ohne Solver, werden die äusseren Z-Tags nicht entfernt.
				//sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ, false);
				
				//Der reine Tag, ohne umgebende Z-Tags und Call-Tags
				sTag = sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT;
				sTagSolved = sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT;
				sTagSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sTagSolved, sTagStartZ, sTagEndZ, false); //von aussen nach innen. So bleiben Z-Tags innen(z.B. um den Pfad herum) erhalten.
				sTagSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sTagSolved, KernelCallIniSolverZZZ.sTAG_NAME, false);
                
				if(enumTestSubtype != null && enumTestSubtype == TestSubtype.AS_ENTRY) {
					btemp = testCompute_Call_Unsolved_(sExpression, sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved, EnumSetMappedTestSurroundingTypeZZZ.SOLVE_REMOVE, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE_AS_ENTRY);
				}else {
					btemp = testCompute_Call_Unsolved_(sExpression, sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved, EnumSetMappedTestSurroundingTypeZZZ.SOLVE_REMOVE, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
				}
				
				//++++++++++++++++++++++++++++++++++++++++++++++++++
				//+++ Ohne Call-Berechung
				//a) Parse ... ohne Entfernen der umgebenden Z-Tags
				sExpression = sPre + sExpressionIn + sPost;
				sExpressionSubstituted = sPre + sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT + sPost;
				sExpressionSolved = sPre + sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT + sPost; //auch ohne Solver werden die Pfade substituiert!!!
				
				//Der reine Tag, ohne umgebende Z-Tags und Call-Tags
				sTag = sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT;
				sTagSolved = sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT;
				sTagSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sTagSolved, sTagStartZ, sTagEndZ, false); //von aussen nach innen. So bleiben Z-Tags innen(z.B. um den Pfad herum) erhalten.
				sTagSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sTagSolved, KernelCallIniSolverZZZ.sTAG_NAME, false);
                
				if(enumTestSubtype != null && enumTestSubtype == TestSubtype.AS_ENTRY) {
					btemp = testCompute_Call_CallUnsolved_(sExpression, sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved, EnumSetMappedTestSurroundingTypeZZZ.PARSE_KEEP, EnumSetMappedTestCaseSolverTypeZZZ.PARSE_AS_ENTRY);
				} else {
					btemp = testCompute_Call_CallUnsolved_(sExpression,  sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved, EnumSetMappedTestSurroundingTypeZZZ.PARSE_KEEP, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
				}
				
				//b) Parse ... mit Entfernen der umgebenden Z-Tags
				sExpression = sPre + sExpressionIn + sPost;
				sExpressionSubstituted = sPre + sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT + sPost;
				sExpressionSolved = sPre + sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT + sPost; //auch ohne Solver werden die Pfade substituiert!!!				
				sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ, false);
				//Beim Parsen ohne call, muss doch dieser call - Tag drinbleiben. Hier werden also nur die aeussern Z-Tags entfernt.
				//ALSO NICHT: Halt kein Call - Tag mehr im Ergebnis Erwartet....   sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSolved, KernelCallIniSolverZZZ.sTAG_NAME);
				
				//Der reine Tag, ohne umgebende Z-Tags und Call-Tags
				sTag = sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT;
				sTagSolved = sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT;
				sTagSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sTagSolved, sTagStartZ, sTagEndZ, false); //von aussen nach innen. So bleiben Z-Tags innen(z.B. um den Pfad herum) erhalten.
				sTagSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sTagSolved, KernelCallIniSolverZZZ.sTAG_NAME, false);
                
				if(enumTestSubtype != null && enumTestSubtype == TestSubtype.AS_ENTRY) {
					btemp = testCompute_Call_CallUnsolved_(sExpression,  sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved, EnumSetMappedTestSurroundingTypeZZZ.PARSE_REMOVE, EnumSetMappedTestCaseSolverTypeZZZ.PARSE_AS_ENTRY);
				} else {
					btemp = testCompute_Call_CallUnsolved_(sExpression,  sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved, EnumSetMappedTestSurroundingTypeZZZ.PARSE_REMOVE, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
				}
				
				//c) Solve ... ohne Entfernen der umgebenden Z-Tags
				sExpression = sPre + sExpressionIn + sPost;
				sExpressionSubstituted = sPre + sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT + sPost;
				sExpressionSolved = sPre + sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT + sPost; //auch ohne Solver werden die Pfade substituiert!!!
				//Beim Solven ohne call, bleibt alles an Tags drin.
				
				//Der reine Tag, ohne umgebende Z-Tags und Call-Tags
				sTag = sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT;
				sTagSolved = sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT;
				sTagSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sTagSolved, sTagStartZ, sTagEndZ, false); //von aussen nach innen. So bleiben Z-Tags innen(z.B. um den Pfad herum) erhalten.
				sTagSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sTagSolved, KernelCallIniSolverZZZ.sTAG_NAME, false);
                
				if(enumTestSubtype != null && enumTestSubtype == TestSubtype.AS_ENTRY) {
					btemp = testCompute_Call_CallUnsolved_(sExpression, sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved, EnumSetMappedTestSurroundingTypeZZZ.SOLVE_KEEP, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE_AS_ENTRY);
				} else {
					btemp = testCompute_Call_CallUnsolved_(sExpression, sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved, EnumSetMappedTestSurroundingTypeZZZ.SOLVE_KEEP, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
				}
			
				//d) Solve ... mit Entfernen der umgebenden Z-Tags
				sExpression = sPre + sExpressionIn + sPost;
				sExpressionSubstituted = sPre + sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT + sPost;
				sExpressionSolved = sPre + sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT + sPost; //auch ohne Solver werden die Pfade substituiert!!!
				sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ, false);//DerGesamtsolver sollte aber ausgefuehrt werden und fuer das Entfernen der Z-Tags sorgen!!!
				
				//Der reine Tag, ohne umgebende Z-Tags und Call-Tags
				sTag = sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT;
				sTagSolved = sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT;
				sTagSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sTagSolved, sTagStartZ, sTagEndZ, false); //von aussen nach innen. So bleiben Z-Tags innen(z.B. um den Pfad herum) erhalten.
				sTagSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sTagSolved, KernelCallIniSolverZZZ.sTAG_NAME, false);
                
				if(enumTestSubtype != null && enumTestSubtype == TestSubtype.AS_ENTRY) {
					btemp = testCompute_Call_CallUnsolved_(sExpression,  sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved, EnumSetMappedTestSurroundingTypeZZZ.SOLVE_REMOVE, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE_AS_ENTRY);
				} else {
					btemp = testCompute_Call_CallUnsolved_(sExpression,  sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved, EnumSetMappedTestSurroundingTypeZZZ.SOLVE_REMOVE, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
				}
				
				//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
				//+++ Mit Call-Berechnung OHNE JavaCall-Berechnung
				
				//a) Parse ... ohne Entfernen der umgebenden Z-Tags
				sExpression = sPre + sExpressionIn + sPost;
				sExpressionSubstituted = sPre + sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT + sPost;
				sExpressionSolved = sPre + sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT + sPost; //auch ohne Solver werden die Pfade substituiert!!!
				//Werdem beim reinen Parsen die umgebenden Tags nicht entfernt, dann bleibt das call-Tag drin. Das wird naemlich auch durch Parsen NICHT "aufgeloest".
				//sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ, false); //von aussen nach innen. So bleiben Z-Tags innen(z.B. um den Pfad herum) erhalten.						
				//sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sTagSolved, KernelCallIniSolverZZZ.sTAG_NAME, false);				
				
				sTag = sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT;
				
				//Der reine Tag, ohne umgebende Z-Tags und Call-Tags
				sTagSolved = sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT;
				sTagSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sTagSolved, sTagStartZ, sTagEndZ, false); //von aussen nach innen. So bleiben Z-Tags innen(z.B. um den Pfad herum) erhalten.
				sTagSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sTagSolved, KernelCallIniSolverZZZ.sTAG_NAME, false);

				if(enumTestSubtype != null && enumTestSubtype == TestSubtype.AS_ENTRY) {
					btemp = testCompute_Call_JavaCall_Unsolved_(sExpression, sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved, EnumSetMappedTestSurroundingTypeZZZ.PARSE_KEEP, EnumSetMappedTestCaseSolverTypeZZZ.PARSE_AS_ENTRY);
				} else {
					btemp = testCompute_Call_JavaCall_Unsolved_(sExpression, sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved, EnumSetMappedTestSurroundingTypeZZZ.PARSE_KEEP, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
				}

										
				//b) Parse ... ohne Behalten der umgebenden Z-Tags
				sExpression = sPre + sExpressionIn + sPost;
				sExpressionSubstituted = sPre + sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT + sPost;
				sExpressionSolved = sPre + sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT + sPost; //auch ohne Solver werden die Pfade substituiert!!!
				
				//Besonderer Test: Entferne beim Parsen die umgebenden Z-Tags
				sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ, false); //von aussen nach innen. So bleiben Z-Tags innen(z.B. um den Pfad herum) erhalten.						

				
				sTag = sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT;
				
				//Der reine Tag, ohne umgebende Z-Tags und Call-Tags
				sTagSolved = sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT;
				sTagSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sTagSolved, sTagStartZ, sTagEndZ, false); //von aussen nach innen. So bleiben Z-Tags innen(z.B. um den Pfad herum) erhalten.
				sTagSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sTagSolved, KernelCallIniSolverZZZ.sTAG_NAME, false);
                
			
				if(enumTestSubtype != null && enumTestSubtype == TestSubtype.AS_ENTRY) {
					btemp = testCompute_Call_JavaCall_Unsolved_(sExpression, sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved, EnumSetMappedTestSurroundingTypeZZZ.PARSE_REMOVE, EnumSetMappedTestCaseSolverTypeZZZ.PARSE_AS_ENTRY);
				} else {
					btemp = testCompute_Call_JavaCall_Unsolved_(sExpression, sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved, EnumSetMappedTestSurroundingTypeZZZ.PARSE_REMOVE, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
				}	
			
				//c) Solve ... ohne Entfernen der umgebenden Z-Tags
				sExpression = sPre + sExpressionIn + sPost;
				sExpressionSubstituted = sPre + sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT + sPost;
				sExpressionSolved = sPre + sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT + sPost; //auch ohne Solver werden die Pfade substituiert!!!

				//Konflikt: 
				//Normalfall: Aufloesen mit solve -> z:call, z:JavaCall weg, etc.
				//      ABER: bRemoveSurrounding ist false gesetzt!!! -> D.h. AEUSSERE Tags bleiben drin.
				
				//Also: z:Call soll aus dem Ergebnis weg sein, wg. Aufloesen!!! Auch wenn die umgebenden Z-Tags drin bleiben.
				//      Weil JavaCall in diesem Test nicht verwendet wird, bleibt dieser drin.
				sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, KernelCallIniSolverZZZ.sTAG_NAME);

				
				sTag = sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT;
				
				//Der reine Tag, ohne umgebende Z-Tags und Call-Tags
				sTagSolved = sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT;
				sTagSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sTagSolved, sTagStartZ, sTagEndZ, false); //von aussen nach innen. So bleiben Z-Tags innen(z.B. um den Pfad herum) erhalten.
				sTagSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sTagSolved, KernelCallIniSolverZZZ.sTAG_NAME, false);
                								
				
				if(enumTestSubtype != null && enumTestSubtype == TestSubtype.AS_ENTRY) {
					btemp = testCompute_Call_JavaCall_Unsolved_(sExpression, sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved, EnumSetMappedTestSurroundingTypeZZZ.SOLVE_KEEP, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE_AS_ENTRY);
				} else {
					btemp = testCompute_Call_JavaCall_Unsolved_(sExpression, sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved, EnumSetMappedTestSurroundingTypeZZZ.SOLVE_KEEP, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
				}
	
				//d) Solve ... mit Entfernen der umgebenden Z-Tags
				sExpression = sPre + sExpressionIn + sPost;
				sExpressionSubstituted = sPre + sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT + sPost;
				sExpressionSolved = sPre + sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT + sPost; //auch ohne Solver werden die Pfade substituiert!!!
				
				//Also: z:Call soll aus dem Ergebnis weg sein, wg. Aufloesen!!! Und die umgebenden Z-Tags sollen weg.
				//      Weil JavaCall in diesem Test nicht verwendet wird, bleibt dieser drin.
				sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, KernelCallIniSolverZZZ.sTAG_NAME);		
				sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
				
				
				
				sTag = sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT;
				
				//Der reine Tag, ohne umgebende Z-Tags und Call-Tags
				sTagSolved = sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT;
				sTagSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sTagSolved, sTagStartZ, sTagEndZ, false); //von aussen nach innen. So bleiben Z-Tags innen(z.B. um den Pfad herum) erhalten.
				sTagSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sTagSolved, KernelCallIniSolverZZZ.sTAG_NAME, false);
                
				

				if(enumTestSubtype != null && enumTestSubtype == TestSubtype.AS_ENTRY) {
					btemp = testCompute_Call_JavaCall_Unsolved_(sExpression, sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved, EnumSetMappedTestSurroundingTypeZZZ.SOLVE_REMOVE, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE_AS_ENTRY);
				} else {
					btemp = testCompute_Call_JavaCall_Unsolved_(sExpression, sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved, EnumSetMappedTestSurroundingTypeZZZ.SOLVE_REMOVE, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
				}

				
				
				//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
				//+++ Mit Call-Berechnung UND JavaCall-Berechnung
				sExpressionSubstituted = sPre + sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT + sPost;
				//a) Der umgebene Z-Tag soll drin bleiben
				sExpression = sPre + sExpressionIn + sPost;
				sExpressionSolved = sPre + sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT + sPost; //auch ohne Solver werden die Pfade substituiert!!!
				
				sTag = sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT;
				
				//Der reine Tag, ohne umgebende Z-Tags und Call-Tags
				sTagSolved = sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT;
				sTagSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sTagSolved, sTagStartZ, sTagEndZ, false); //von aussen nach innen. So bleiben Z-Tags innen(z.B. um den Pfad herum) erhalten.
				sTagSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sTagSolved, KernelCallIniSolverZZZ.sTAG_NAME, false);
				//Nur Call-Ebene, darum nicht: sTagSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sTagSolved, KernelJavaCallIniSolverZZZ.sTAG_NAME, false);
				
				//Entferne die Z-Tags von der Zeile, was aber nicht gewünscht ist.  Zudem: Hier wird nur geparsed, also bleiben die anderen Tags auch drin
				//sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ, false); //von aussen nach innen. So bleiben Z-Tags innen(z.B. um den Pfad herum) erhalten.						
				//sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sTagSolved, KernelCallIniSolverZZZ.sTAG_NAME, false);				
				//sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sTagSolved, KernelJavaCallIniSolverZZZ.sTAG_NAME, false);
					
				if(enumTestSubtype != null && enumTestSubtype == TestSubtype.AS_ENTRY) {
					btemp = testCompute_Call_(sExpression, sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved, EnumSetMappedTestSurroundingTypeZZZ.PARSE_KEEP, EnumSetMappedTestCaseSolverTypeZZZ.PARSE_AS_ENTRY);
				} else {
					btemp = testCompute_Call_(sExpression, sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved, EnumSetMappedTestSurroundingTypeZZZ.PARSE_KEEP, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
				}
				
				//b) Umgebender Z-Tag wird entfernt
				sExpression = sPre + sExpressionIn + sPost;
				sExpressionSubstituted = sPre + sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT + sPost;
				sExpressionSolved = sPre + sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT + sPost; //auch ohne Solver werden die Pfade substituiert!!!
				
				//Der reine Tag, ohne umgebende Z-Tags und Call-Tags
				sTagSolved = sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT;
				sTagSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sTagSolved, sTagStartZ, sTagEndZ, false); //von aussen nach innen. So bleiben Z-Tags innen(z.B. um den Pfad herum) erhalten.
				sTagSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sTagSolved, KernelCallIniSolverZZZ.sTAG_NAME, false);
				//Nur Call-Ebene, darum nicht: sTagSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sTagSolved, KernelJavaCallIniSolverZZZ.sTAG_NAME, false);
				
				//Entferne den Z-Tag von der Zeile, aber weil nur geparsed wird, bleiben der CALL-Tag und der JAVA-CALL Tag drin
				sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ, false); //von aussen nach innen. So bleiben Z-Tags innen(z.B. um den Pfad herum) erhalten.						
				//sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sTagSolved, KernelCallIniSolverZZZ.sTAG_NAME, false);				
				//sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sTagSolved, KernelJavaCallIniSolverZZZ.sTAG_NAME, false);				
				
				if(enumTestSubtype != null && enumTestSubtype == TestSubtype.AS_ENTRY) {
					btemp = testCompute_Call_(sExpression, sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved, EnumSetMappedTestSurroundingTypeZZZ.PARSE_REMOVE, EnumSetMappedTestCaseSolverTypeZZZ.PARSE_AS_ENTRY);
				} else {
					btemp = testCompute_Call_(sExpression, sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved, EnumSetMappedTestSurroundingTypeZZZ.PARSE_REMOVE, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);					
				}
				
				//c)
				sExpression = sPre + sExpressionIn + sPost;
				sExpressionSubstituted = sPre + sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT + sPost;
				sExpressionSolved = sPre + sTagStartZ + sHostName + sTagEndZ + sPost; 
				//Wichtig: z:Call und z:Java sollen aus dem Ergebnis weg sein, wg. Aufloesen!!! Auch wenn die umgebenden Z-Tags drin bleiben.
				//Merke: Das geht aber bei uebergebenen Pfaden nur, wenn sie auch aufgeloest werden und einen sinnvolle Klasse und Methode zurueckkommt.
				
				sTag = sHostName;
				
				sTagSolved = sHostName;	
				
				if(enumTestSubtype != null && enumTestSubtype == TestSubtype.AS_ENTRY) {
					btemp = testCompute_Call_(sExpression, sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved, EnumSetMappedTestSurroundingTypeZZZ.SOLVE_KEEP, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE_AS_ENTRY);
				}else {
					btemp = testCompute_Call_(sExpression, sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved, EnumSetMappedTestSurroundingTypeZZZ.SOLVE_KEEP, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
				}
				
				//d)
				sExpression = sPre + sExpressionIn + sPost;
				sExpressionSubstituted = sPre + sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT + sPost;
				sExpressionSolved = sPre + sHostName + sPost;		
				
				sTag = sHostName;
				sTagSolved = sHostName;
				
				if(enumTestSubtype != null && enumTestSubtype == TestSubtype.AS_ENTRY) {
					btemp = testCompute_Call_(sExpression, sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved, EnumSetMappedTestSurroundingTypeZZZ.SOLVE_REMOVE, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE_AS_ENTRY);
				} else {
					btemp = testCompute_Call_(sExpression, sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved, EnumSetMappedTestSurroundingTypeZZZ.SOLVE_REMOVE, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
				}
								
			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			}		
		}
		
		
		private boolean testCompute_Call_(String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, String sTagIn, String sTagSolvedIn, IEnumSetMappedTestSurroundingZZZ objEnumSurrounding, IEnumSetMappedTestCaseZZZ objEnumTestCase) {
			boolean bReturn = false;
			try {
				boolean btemp; 
				
				String sExpression;  String sExpressionSubstituted; String sExpressionSolved; String sTag; String sTagSolved;
				String sValue; 
				ReferenceZZZ<IKernelConfigSectionEntryZZZ> objSectionEntryReference=null; IKernelConfigSectionEntryZZZ objEntry=null;
				
				String sTagStartZ = "<Z>";
				String sTagEndZ = "</Z>";
				
				sExpression = sExpressionIn;
				sExpressionSubstituted = sExpressionSubstitutedIn;
				sExpressionSolved = sExpressionSolvedIn;
				sTag = sTagIn;
				sTagSolved = sTagSolvedIn;
				
				IEnumSetMappedTestFlagsetZZZ objEnumFunction = EnumSetMappedTestCaseFlagsetTypeZZZ.JAVACALL_SOLVED;
				
				
				//####################################################################################
				//### EXPRESSION .solve
				//####################################################################################
																	
				btemp = objExpressionSolver.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION, true); 
				assertTrue("Flag nicht vorhanden '" + IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);

				btemp = objExpressionSolver.setFlag(IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER, true);			
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
				
				boolean bUseExpressionGeneral = objExpressionSolver.isExpressionEnabledGeneral();	
				boolean bUseSolver = objExpressionSolver.isSolverEnabledGeneral();
				
				//+++ ... parse ist nicht solve... also wird hier nichts aufgeloest, aussser die Pfade
				if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.PARSE)) {					
					objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
					sValue = objExpressionSolver.parse(sExpression, objSectionEntryReference, objEnumSurrounding.getSurroundingValueUsed());
					assertEquals(sExpressionSolved, sValue);
					
					sValue = objExpressionSolver.getValue();
					assertEquals(sTagSolved, sValue);
					
					objEntry = objSectionEntryReference.get();
					assertNotNull(objEntry);
				}
				//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
					
				//+++ ... solve verhält sich NICHT wie parse(), bei solve wird aufgeloest...
				if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.SOLVE)) {
					objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
					sValue = objExpressionSolver.solve(sExpression, objSectionEntryReference, objEnumSurrounding.getSurroundingValueUsed());
					
					String sExpressionSolvedTemp = sExpressionSolved;
					if(bUseExpressionGeneral && bUseSolver && objEnumSurrounding.getSurroundingValueUsed()) {
						sExpressionSolvedTemp = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
					}
					assertEquals(sExpressionSolvedTemp, sValue);
					
					sValue = objExpressionSolver.getValue();
					assertEquals(sTagSolved, sValue);
					
					objEntry = objSectionEntryReference.get();
					assertNotNull(objEntry);
				}
				//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			
				//+++ Variante fuer einen AsEntry-Test
				if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.PARSE_AS_ENTRY)) {
					objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
					objEntry = objExpressionSolver.parseAsEntry(sExpression, objEnumSurrounding.getSurroundingValueUsed());				
					assertNotNull(objEntry);
				}
				
				//+++ Variante fuer einen AsEntry-Test
				if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.SOLVE_AS_ENTRY)) {
					objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
										
					objEntry = objExpressionSolver.solveAsEntry(sExpression, objEnumSurrounding.getSurroundingValueUsed());				
					assertNotNull(objEntry);
				}
				
				//Nutze eine Sammlung von assert Methoden, die ein objEntry als input hat.
				//und in der die verschiedenen stati für den unexpressed, unsubstituted, substituted, unsolved, etc Fall stehen.
				btemp = TestUtilAsTestZZZ.assertFileIniEntry(objEnumTestCase, objEnumFunction, objEntry, sExpression, sExpressionSubstituted, sExpressionSolved);
				assertTrue(btemp);
				
				bReturn = true;
			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			}
			return bReturn;
		}
		
		private boolean testCompute_Call_JavaCall_Unsolved_(String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, String sTagIn, String sTagSolvedIn, IEnumSetMappedTestSurroundingZZZ objEnumSurrounding, IEnumSetMappedTestCaseZZZ objEnumTestCase) {
			boolean bReturn = false;
			
			try {
				boolean btemp; 
				
				String sExpression;  String sExpressionSubstituted; String sExpressionSolved; String sTag; String sTagSolved; 
				String sValue; 		
				ReferenceZZZ<IKernelConfigSectionEntryZZZ> objSectionEntryReference=null; IKernelConfigSectionEntryZZZ objEntry=null;
				
				String sTagStartZ = "<Z>";
				String sTagEndZ = "</Z>";
				
				sExpression = sExpressionIn;
				sExpressionSubstituted = sExpressionSubstitutedIn;
				sExpressionSolved = sExpressionSolvedIn;
				sTag = sTagIn;
				sTagSolved = sTagSolvedIn;

				
				IEnumSetMappedTestFlagsetZZZ objEnumFunction = EnumSetMappedTestCaseFlagsetTypeZZZ.JAVACALL_UNSOLVED;
				
				//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++			
				btemp = objExpressionSolver.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION, true); 
				assertTrue("Flag nicht vorhanden '" + IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);

				btemp = objExpressionSolver.setFlag(IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER, true);			
				assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER + "'", btemp);
				
				btemp = objExpressionSolver.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH, true);			
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
			
				btemp = objExpressionSolver.setFlag(IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE, true);			
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE + "'", btemp);
								
				btemp = objExpressionSolver.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER, true);
				assertTrue("Das Flag '"+IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER+"' sollte zur Verfügung stehen.", btemp);
				
				btemp = objExpressionSolver.setFlag(IKernelCallIniSolverZZZ.FLAGZ.USECALL, true); //keinen Call - Solver nutzen
				assertTrue("Flag nicht vorhanden '" + IKernelCallIniSolverZZZ.FLAGZ.USECALL + "'", btemp);
				
				btemp = objExpressionSolver.setFlag(IKernelJavaCallIniSolverZZZ.FLAGZ.USECALL_JAVA, false); 
				assertTrue("Flag nicht vorhanden '" + IKernelJavaCallIniSolverZZZ.FLAGZ.USECALL_JAVA + "'", btemp);
			
				boolean bUseExpressionGeneral = objExpressionSolver.isExpressionEnabledGeneral();
				boolean bUseSolver = objExpressionSolver.isSolverEnabledGeneral();
				
				//+++ ... parse ist nicht solve... also wird hier nichts aufgeloest, aussser die Pfade
				if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.PARSE)) {
					objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
					sValue = objExpressionSolver.parse(sExpression, objSectionEntryReference, objEnumSurrounding.getSurroundingValueUsed());
					assertEquals(sExpressionSolved, sValue);
					
					sValue = objExpressionSolver.getValue();
					assertEquals(sTagSolved, sValue);
					
					objEntry = objSectionEntryReference.get();
					assertNotNull(objEntry);
				}
				//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
					
				//+++ ... solve verhält sich NICHT wie parse(), bei solve wird aufgeloest...
				if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.SOLVE)) {
					objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
					sValue = objExpressionSolver.solve(sExpression, objSectionEntryReference, objEnumSurrounding.getSurroundingValueUsed());
					assertEquals(sExpressionSolved, sValue);

					String sExpressionSolvedTemp = sExpressionSolved;
					if(bUseExpressionGeneral && bUseSolver && objEnumSurrounding.getSurroundingValueUsed()) {
						sExpressionSolvedTemp = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
					}
					assertEquals(sExpressionSolvedTemp, sValue);
										
					objEntry = objSectionEntryReference.get();
					assertNotNull(objEntry);
				}
				//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
				
				//+++ Variante fuer den AsEntry-Test
				if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.PARSE_AS_ENTRY)) {
					objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
					objEntry = objExpressionSolver.parseAsEntry(sExpression, objEnumSurrounding.getSurroundingValueUsed());				
					assertNotNull(objEntry);
					
					sValue = objEntry.getValue();
					assertEquals(sExpressionSolved, sValue);
				}
				
				//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
				
				//+++ ... solve verhält sich NICHT wie parse(), bei solve wird aufgeloest...
				if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.SOLVE_AS_ENTRY)) {					
					objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
					sValue = objExpressionSolver.solve(sExpression, objSectionEntryReference, objEnumSurrounding.getSurroundingValueUsed());
					
					String sExpressionSolvedTemp = sExpressionSolved;
					if(bUseExpressionGeneral && bUseSolver && objEnumSurrounding.getSurroundingValueUsed()) {
						sExpressionSolvedTemp = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
					}
					assertEquals(sExpressionSolvedTemp, sValue);

					objEntry = objSectionEntryReference.get();
					assertNotNull(objEntry);					
				}
				//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

				//Nutze eine Sammlung von assert Methoden, die ein objEntry als input hat.
				//und in der die verschiedenen stati für den unexpressed, unsubstituted, substituted, unsolved, etc Fall stehen.
				btemp = TestUtilAsTestZZZ.assertFileIniEntry(objEnumTestCase, objEnumFunction, objEntry, sExpression, sExpressionSubstituted, sExpressionSolved);
				assertTrue(btemp);
		
				bReturn = true;
			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			}
			
			return bReturn;
		}
		
		private boolean testCompute_Call_CallUnsolved_(String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, String sTagIn, String sTagSolvedIn, IEnumSetMappedTestSurroundingZZZ objEnumSurrounding, IEnumSetMappedTestCaseZZZ objEnumTestCase) {
			boolean bReturn = false;
			
			try {
				boolean btemp; 
				
				String sExpression;  String sExpressionSubstituted; String sExpressionSolved; String sTag; String sTagSolved; 
				String sValue;			
				ReferenceZZZ<IKernelConfigSectionEntryZZZ> objSectionEntryReference=null; IKernelConfigSectionEntryZZZ objEntry=null;  
				
				String sTagStartZ = "<Z>";
				String sTagEndZ = "</Z>";
				
				sExpression = sExpressionIn;
				sExpressionSubstituted = sExpressionSubstitutedIn;
				sExpressionSolved = sExpressionSolvedIn;
				sTag = sTagIn;
				sTagSolved = sTagSolvedIn;
				
				IEnumSetMappedTestFlagsetZZZ objEnumFunction = EnumSetMappedTestCaseFlagsetTypeZZZ.CALL_UNSOLVED;

				//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++		
				//Nur Expression ausrechnen, ist aber unverändert vom reinen Ergebnis her.		
				btemp = objExpressionSolver.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION, true); 
				assertTrue("Flag nicht vorhanden '" + IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);

				btemp = objExpressionSolver.setFlag(IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER, true);			
				assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER + "'", btemp);
								
				btemp = objExpressionSolver.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH, true);			
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
			
				btemp = objExpressionSolver.setFlag(IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE, true);			
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE + "'", btemp);
								
				btemp = objExpressionSolver.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER, true);
				assertTrue("Das Flag '"+IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER+"' sollte zur Verfügung stehen.", btemp);
				
				btemp = objExpressionSolver.setFlag(IKernelCallIniSolverZZZ.FLAGZ.USECALL, false); //keinen Call - Solver nutzen
				assertTrue("Flag nicht vorhanden '" + IKernelCallIniSolverZZZ.FLAGZ.USECALL + "'", btemp);
				
				btemp = objExpressionSolver.setFlag(IKernelJavaCallIniSolverZZZ.FLAGZ.USECALL_JAVA, true); 
				assertTrue("Flag nicht vorhanden '" + IKernelJavaCallIniSolverZZZ.FLAGZ.USECALL_JAVA + "'", btemp);
			
				boolean bUseExpressionGeneral = objExpressionSolver.isExpressionEnabledGeneral();
				boolean bUseSolver = objExpressionSolver.isSolverEnabledGeneral();
				
				//+++ ... parse ist nicht solve... also wird hier nichts aufgeloest, aussser die Pfade
				if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.PARSE)) {		
					objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
					sValue = objExpressionSolver.parse(sExpression, objSectionEntryReference, objEnumSurrounding.getSurroundingValueUsed());
					assertEquals(sExpressionSolved, sValue);
					
					sValue = objExpressionSolver.getValue();
					assertEquals(sTagSolved, sValue);
					
					objEntry = objSectionEntryReference.get();
					assertNotNull(objEntry);
				}
				//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
					
				//+++ ... solve verhält sich NICHT wie parse(), bei solve wird aufgeloest...
				if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.SOLVE)) {
					objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
					sValue = objExpressionSolver.solve(sExpression, objSectionEntryReference, objEnumSurrounding.getSurroundingValueUsed());
					assertEquals(sExpressionSolved, sValue);

					String sExpressionSolvedTemp = sExpressionSolved;
					if(bUseExpressionGeneral && bUseSolver && objEnumSurrounding.getSurroundingValueUsed()) {
						sExpressionSolvedTemp = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
					}
					assertEquals(sExpressionSolvedTemp, sValue);
					
					objEntry = objSectionEntryReference.get();
					assertNotNull(objEntry);
				}
				//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
				
				//+++ Variante fuer den AsEntry-Test
				if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.PARSE_AS_ENTRY)) {					
					objEntry = objExpressionSolver.parseAsEntry(sExpression, objEnumSurrounding.getSurroundingValueUsed());				
					assertNotNull(objEntry);
				}
				
				//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
				
				//+++ ... solve verhält sich NICHT wie parse(), bei solve wird aufgeloest...
				if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.SOLVE_AS_ENTRY)) {				
					objEntry = objExpressionSolver.solveAsEntry(sExpression, objEnumSurrounding.getSurroundingValueUsed());
					assertNotNull(objEntry);
					
					sValue = objEntry.getValue();
					
					String sExpressionSolvedTemp = sExpressionSolved;
					if(bUseExpressionGeneral && bUseSolver && objEnumSurrounding.getSurroundingValueUsed()) {
						sExpressionSolvedTemp = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
					}
					assertEquals(sExpressionSolvedTemp, sValue);
					
					sValue = objExpressionSolver.getValue();
					assertEquals(sTagSolved, sValue);
				}
				//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
				
				//Nutze eine Sammlung von assert Methoden, die ein objEntry als input hat.
				//und in der die verschiedenen stati für den unexpressed, unsubstituted, substituted, unsolved, etc Fall stehen.
				btemp = TestUtilAsTestZZZ.assertFileIniEntry(objEnumTestCase, objEnumFunction, objEntry, sExpression, sExpressionSubstituted, sExpressionSolved);
				assertTrue(btemp);
				
				bReturn = true;
			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			}
			
			return bReturn;
		}
		
		
		private boolean testCompute_Call_Unsolved_(String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, String sTagIn, String sTagSolvedIn, IEnumSetMappedTestSurroundingZZZ objEnumSurrounding, IEnumSetMappedTestCaseZZZ objEnumTestCase) {
			boolean bReturn = false;
			try {
				boolean btemp; 
				
				String sExpression;  String sExpressionSubstituted; String sExpressionSolved; String sTag; String sTagSolved; 
				String sValue;			
				ReferenceZZZ<IKernelConfigSectionEntryZZZ> objSectionEntryReference=null; IKernelConfigSectionEntryZZZ objEntry=null;  
				
				String sTagStartZ = "<Z>";
				String sTagEndZ = "</Z>";
				
				sExpression = sExpressionIn;
				sExpressionSubstituted = sExpressionSubstitutedIn;
				sExpressionSolved = sExpressionSolvedIn;
				sTag = sTagIn;
				sTagSolved = sTagSolvedIn;
				
				IEnumSetMappedTestFlagsetZZZ objEnumFunction = EnumSetMappedTestCaseFlagsetTypeZZZ.UNSOLVED;
				
				//####################################################################################
				//### EXPRESSION - CALL NICHT .solve
				//####################################################################################

				btemp = objExpressionSolver.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION, true); 
				assertTrue("Flag nicht vorhanden '" + IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
				
				btemp = objExpressionSolver.setFlag(IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER, true);			
				assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER + "'", btemp);
								
				btemp = objExpressionSolver.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH, true);			
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
			
				btemp = objExpressionSolver.setFlag(IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE, true);			
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE + "'", btemp);
				
				btemp = objExpressionSolver.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER, false); 
				assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
				
				btemp = objExpressionSolver.setFlag(IKernelCallIniSolverZZZ.FLAGZ.USECALL, true); 
				assertTrue("Flag nicht vorhanden '" + IKernelCallIniSolverZZZ.FLAGZ.USECALL + "'", btemp);
				
				btemp = objExpressionSolver.setFlag(IKernelJavaCallIniSolverZZZ.FLAGZ.USECALL_JAVA, true); 
				assertTrue("Flag nicht vorhanden '" + IKernelJavaCallIniSolverZZZ.FLAGZ.USECALL_JAVA + "'", btemp);
				
				boolean bUseExpressionGeneral = objExpressionSolver.isExpressionEnabledGeneral();
				boolean bUseSolver = objExpressionSolver.isSolverEnabledGeneral();
				
				//+++ ... parse ist nicht solve... also wird hier nichts aufgeloest, aussser die Pfade
				if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.PARSE)) {	
					objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
					sValue = objExpressionSolver.parse(sExpression, objSectionEntryReference, objEnumSurrounding.getSurroundingValueUsed());
					assertEquals(sExpressionSolved, sValue);
					
					objEntry = objSectionEntryReference.get();
					assertNotNull(objEntry);
				}
				//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
					
				//+++ ... solve verhält sich NICHT wie parse(), bei solve wird aufgeloest...
				if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.SOLVE)) {
					objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
					sValue = objExpressionSolver.solve(sExpression, objSectionEntryReference, objEnumSurrounding.getSurroundingValueUsed());
					
					String sExpressionSolvedTemp = sExpressionSolved;
					if(bUseExpressionGeneral && bUseSolver && objEnumSurrounding.getSurroundingValueUsed()) {
						sExpressionSolvedTemp = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
					}
					assertEquals(sExpressionSolvedTemp, sValue);

					objEntry = objSectionEntryReference.get();
					assertNotNull(objEntry);
				}
				//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
				
				//+++ Variante fuer den AsEntry-Test
				if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.PARSE_AS_ENTRY)) {				
					objEntry = objExpressionSolver.parseAsEntry(sExpression, objEnumSurrounding.getSurroundingValueUsed());				
					assertNotNull(objEntry);
				}
				
				//+++ ... solve verhält sich NICHT wie parse(), bei solve wird aufgeloest...
				if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.SOLVE_AS_ENTRY)) {
					objEntry = objExpressionSolver.solveAsEntry(sExpression, objEnumSurrounding.getSurroundingValueUsed());				
					assertNotNull(objEntry);
				}
				
				//++++++++++++++++++++++++++++++++++++++++
				
				//Nutze eine Sammlung von assert Methoden, die ein objEntry als input hat.
				//und in der die verschiedenen stati für den unexpressed, unsubstituted, substituted, unsolved, etc Fall stehen.
				btemp = TestUtilAsTestZZZ.assertFileIniEntry(objEnumTestCase, objEnumFunction, objEntry, sExpression, sExpressionSubstituted, sExpressionSolved);
				assertTrue(btemp);
				
				bReturn = true;
			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			}
			return bReturn;
		}
		
		private boolean testCompute_Call_Unexpressed_(String sExpressionIn,String sExpressionSubstitutedIn, String sExpressionSolvedIn, String sTagIn, String sTagSolvedIn, IEnumSetMappedTestSurroundingZZZ objEnumSurrounding, IEnumSetMappedTestCaseZZZ objEnumTestCase) {
			boolean bReturn = false;
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

				IEnumSetMappedTestFlagsetZZZ objEnumFunction = EnumSetMappedTestCaseFlagsetTypeZZZ.UNEXPRESSED;
				
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
			
				boolean bUseExpressionGeneral = objExpressionSolver.isExpressionEnabledGeneral();
				boolean bUseSolver = objExpressionSolver.isSolverEnabledGeneral();
				
				//+++ ... parse ist nicht solve... also wird hier nichts aufgeloest, aussser die Pfade substituiert.
				if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.PARSE)) {							
					objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
					sValue = objExpressionSolver.parse(sExpression, objSectionEntryReference, objEnumSurrounding.getSurroundingValueUsed());
					assertEquals(sExpressionSolved, sValue);
					
					sValue = objExpressionSolver.getValue();
					assertEquals(sTagSolved, sValue);
										
					objEntry = objSectionEntryReference.get();
					assertNotNull(objEntry);
				}
				//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
					
				//+++ ... solve verhält sich NICHT wie parse(), bei solve wird aufgeloest...
				if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.SOLVE)) {
					objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
					sValue = objExpressionSolver.solve(sExpression, objSectionEntryReference, objEnumSurrounding.getSurroundingValueUsed());
					
					String sExpressionSolvedTemp = sExpressionSolved;
					if(bUseExpressionGeneral && bUseSolver && objEnumSurrounding.getSurroundingValueUsed()) {
						sExpressionSolvedTemp = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
					}
					assertEquals(sExpressionSolvedTemp, sValue);
					
					sValue = objExpressionSolver.getValue();
					assertEquals(sTagSolved, sValue);
					
					objEntry = objSectionEntryReference.get();
					assertNotNull(objEntry);
				}
				//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
				
				//+++ Variante fuer den AsEntry-Test
				if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.PARSE_AS_ENTRY)) {					
					objEntry = objExpressionSolver.parseAsEntry(sExpression, objEnumSurrounding.getSurroundingValueUsed());				
					assertNotNull(objEntry);
								
					sValue = objEntry.getValue();
					assertEquals(sExpressionSolved, sValue);
				}
				
				//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
				
				//+++ ... solve verhält sich NICHT wie parse(), bei solve wird aufgeloest...
				if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.SOLVE_AS_ENTRY)) {
					sExpression = sExpressionIn;
					sExpressionSolved = sExpressionSolvedIn;					
					objEntry = objExpressionSolver.solveAsEntry(sExpression, objEnumSurrounding.getSurroundingValueUsed());
					assertNotNull(objEntry);
					
					sValue = objEntry.getValue();
					
					String sExpressionSolvedTemp = sExpressionSolved;
					if(bUseExpressionGeneral && bUseSolver && objEnumSurrounding.getSurroundingValueUsed()) {
						sExpressionSolvedTemp = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
					}
					assertEquals(sExpressionSolvedTemp, sValue);
				}
				//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
								
				//Nutze eine Sammlung von assert Methoden, die ein objEntry als input hat.
				//und in der die verschiedenen stati für den unexpressed, unsubstituted, substituted, unsolved, etc Fall stehen.
				btemp = TestUtilAsTestZZZ.assertFileIniEntry(objEnumTestCase, objEnumFunction, objEntry, sExpression, sExpressionSubstituted, sExpressionSolved);
				assertTrue(btemp);
				
				bReturn = true;
			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			}
			return bReturn;
		}
		
		
		/** void, Test: Reading an entry in a section of the ini-file
		* Lindhauer; 22.04.2006 12:54:32
		 */
		public void testCompute01ParseVsSolve(){
			
			boolean btemp;
			Vector3ZZZ<String> vecReturn = null; Vector3ZZZ<String> vecReturnSubstituted = null;
			String sExpression = null; String sExpressionSolved = null;
			String sValue; String sValueSubstituted; String sValueParseFirstVector;
			
			String sTagStartZ = "<Z>";
			String sTagEndZ = "</Z>";				
			try {									
				String sHostName = EnvironmentZZZ.getHostName();
				
				
				
				btemp = objExpressionSolver.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION, true); 
				assertTrue("Flag nicht vorhanden '" + IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);

				btemp = objExpressionSolver.setFlag(IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER, true);			
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
				
				boolean bUseExpressionGeneral = objExpressionSolver.isExpressionEnabledGeneral();
				boolean bUseSolver = objExpressionSolver.isSolverEnabledGeneral();
				
				//+++++++ VORGEZOGENER LETZTER FEHLERTEST START
	
				
				
				//+++++++ VORGEZOGENER LETZTER FEHLERTEST ENDE
				
				
				
				//### Ohne Substitution durchführen
				btemp = objExpressionSolver.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH, false);			
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
							
				sExpression = KernelCallIniSolverZZZTest.sEXPRESSION_JAVACALL01_DEFAULT;
				sExpressionSolved = sExpression;
				//sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpression, sTagStartZ, sTagEndZ, false);
				
				vecReturn = objExpressionSolver.parseFirstVector(sExpression);
				assertNotNull(vecReturn);
				sValue = (String) vecReturn.get(1);
				assertFalse(StringZZZ.isEmpty(sValue)); //in der 0ten Position ist der String vor der Encryption, in der 3ten Position ist der String nach der Encryption.
				
				sValue = VectorUtilZZZ.implode(vecReturn);
				String sExpressionSolvedTemp = sExpressionSolved;
				//In diesem Fall immer True if(objEnumSurrounding.getSurroundingValueUsed()) {
				if(bUseExpressionGeneral && bUseSolver){
					sExpressionSolvedTemp = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
				}
				assertEquals(sExpressionSolvedTemp, sValue);
					
				sValueParseFirstVector = sValue;
				
				sValue = objExpressionSolver.parse(sExpression);
				System.out.println(ReflectCodeZZZ.getPositionCurrent() + "\tDebugausaugabe: '" + sValue + "'\n");
				assertEquals("parse(s) und parseFirstVector(s) sollte gleich sein",sValueParseFirstVector, sValue);
				
				//################################
				//### Mit Substitution
				btemp = objExpressionSolver.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH, true);			
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
							
				sExpression = KernelCallIniSolverZZZTest.sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT;;
				sExpressionSolved = sExpression;
				//sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpression, sTagStartZ, sTagEndZ, false);
				
				vecReturn = objExpressionSolver.parseFirstVector(sExpression);
				assertNotNull(vecReturn);
				sValue = (String) vecReturn.get(1);
				assertFalse(StringZZZ.isEmpty(sValue)); //in der 0ten Position ist der String vor der Encryption, in der 3ten Position ist der String nach der Encryption.
				
				sValue = VectorUtilZZZ.implode(vecReturn);

				//In diesem Fall immer True if(objEnumSurrounding.getSurroundingValueUsed()) {
				if(bUseExpressionGeneral && bUseSolver){
					sExpressionSolvedTemp = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
				}
				assertEquals(sExpressionSolvedTemp, sValue);
				sValueParseFirstVector = sValue;
				
				sValue = objExpressionSolver.parse(sExpression);
				System.out.println(ReflectCodeZZZ.getPositionCurrent() + "\tDebugausaugabe: '" + sValue + "'\n");
				assertEquals("parse(s) und parseFirstVector(s) sollte gleich sein",sValueParseFirstVector, sValue);
				
				
				//###############################
				//### Nun die Gesamtberechnung, d.h. incl. Aufloesen durchführen
				//Anwenden der ersten Formel: Erst jetzt kommt das Ergebnis raus	
				sExpression = KernelCallIniSolverZZZTest.sEXPRESSION_JAVACALL01_DEFAULT;
				
				sValue = objExpressionSolver.solve(sExpression);			
				assertFalse("Mit Auflösung soll Ausgabe anders als Eingabe sein.",sExpression.equals(sValue));
				System.out.println(ReflectCodeZZZ.getPositionCurrent() + "\tDebugausaugabe: '" + sValue + "'\n");
								
				sHostName = EnvironmentZZZ.getHostName();
				assertEquals(sHostName, sValue);
				
				
				//++++++++++++++++++++++++++++++++++++++++++++++++++
				
			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			}
		}
		
		/** void, Test: Reading an entry in a section of the ini-file
		* Lindhauer; 22.04.2006 12:54:32
		 */
		public void testCompute01asEntry(){
			boolean btemp;
			String sValue; boolean bValue; String sValueFromParse;
			String sClassname; String sMethodname;
			String sExpression ; String sExpressionSolved; String sValueAsExpression;
			Vector<String> vecReturn = null;
			String sTagStartZ = "<Z>";
			String sTagEndZ = "</Z>";
			try {													
				//Jetzt den Parser und alles notwendig einschalten
				btemp = objExpressionSolver.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION, true); 
				assertTrue("Flag nicht vorhanden '" + IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);

				btemp = objExpressionSolver.setFlag(IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER, true);			
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
				
				boolean bUseExpressionGeneral = objExpressionSolver.isExpressionEnabledGeneral();
				boolean bUseSolver = objExpressionSolver.isSolverEnabledGeneral();
				
				//#################################################################################
				//### PARSE #######################################################################
				//#################################################################################
				
				btemp = objExpressionSolver.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER, false); 
				assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
								
				sExpression = KernelCallIniSolverZZZTest.sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT;//Merke: Die Aufloesung von Formeln wird dann vom ExpressionSolver gemacht!!!
				sExpressionSolved = sExpression;
				
				IKernelConfigSectionEntryZZZ objEntryTemp = new KernelConfigSectionEntryZZZ();//Hierin können während der Verarbeitung Zwischenergebnisse abgelegt werden, z.B. vor der Entschluesselung der pure Verscluesselte Wert.
				objExpressionSolver.setEntry(objEntryTemp);
			
				//Als Zwischenschritt die bisherigen rein stringbasierten Methoden im objEntry erweitern
				//Hier mit abgeschalteter Expression...
				vecReturn = objExpressionSolver.parseFirstVector(sExpression); 
				assertFalse(VectorUtilZZZ.isEmpty(vecReturn));
			
				sValue = VectorUtilZZZ.implode(vecReturn);				
				assertEquals("Ohne Aufloesung soll Ausgabe gleich Eingabe sein",sExpressionSolved, sValue);
				
				sValueFromParse = sValue;
				
				//###########################################################
				//### Gesamtberechnung: OHNE AUFLOESUNG
				btemp = objExpressionSolver.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER, false); 
				assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
							
				sExpression = KernelCallIniSolverZZZTest.sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT;//Merke: Die Aufloesung von Formeln wird dann vom ExpressionSolver gemacht!!!
				sExpressionSolved = sExpression;
				//sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ, false); //von aussen nach innen. So bleiben Z-Tags innen(z.B. um den Pfad herum) erhalten.	
				
				IKernelConfigSectionEntryZZZ objEntry = objExpressionSolver.parseAsEntryNew(sExpression);
				sValue = objEntry.getValue();				

				assertEquals("Ohne Aufloesung soll Ausgabe gleich Eingabe sein",sExpressionSolved, sValue);
			
				sValueAsExpression = objEntry.getValueFormulaSolvedAndConverted();
				assertNull("Ohne Aufloesung soll die Ausgabe des Expression Werts NULL sein", sValueAsExpression);				
				assertTrue("Auch nur mit Parsen liegt eine Expression vor",objEntry.isExpression());
				
				//###########################################################
				//### Gesamtberechnung: MIT AUFLOESUNG
				btemp = objExpressionSolver.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER, true); 
				assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
				
				sExpression = KernelCallIniSolverZZZTest.sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT;//Merke: Die Aufloesung von Formeln wird dann vom ExpressionSolver gemacht!!!
				sExpressionSolved = sExpression;				
				//Nur durch Parsen wird nichts aufgeloest... trotz Aufloesen Flag, also nicht:   sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ, false); //von aussen nach innen. So bleiben Z-Tags innen(z.B. um den Pfad herum) erhalten.
				
				
				IKernelConfigSectionEntryZZZ objEntry2 = objExpressionSolver.parseAsEntryNew(sExpression);
				sValue = objEntry2.getValue();
				System.out.println(ReflectCodeZZZ.getPositionCurrent() + "\tDebugausgabe1: '" + sValue + "'\n");
				assertEquals("Auch nur mit Parsen soll der geparste Wert anders als die Eingabe sein.",sExpressionSolved,sValue);
				
				
				//20230426: DAS IST VORERST DAS ZIEL, DAMIT IN DER FTPCREDENTIALS MASKE DER VERSCHLUESSELTE WERT AUCH ANGEZEIGT WERDEN KANN!!!
				bValue = objEntry2.isCall();				
				assertTrue(bValue);
				
				bValue = objEntry2.isJavaCall();
				assertTrue(bValue);
				
				//ohne solve ist das alles NULL	
				sClassname = objEntry2.getCallingClassname();
				assertNull("NULL erwartet. Wert ist aber '" + sClassname + "'", sClassname);
				
				sMethodname = objEntry2.getCallingMethodname();
				assertNull("NULL erwartet. Wert ist aber '" + sMethodname + "'", sMethodname);
				
				
				//##############################################
				//### SOLVE ####################################
				//##############################################
				btemp = objExpressionSolver.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER, true); 
				assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
			
				sExpression = KernelCallIniSolverZZZTest.sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT;//Merke: Die Aufloesung von Formeln wird dann vom ExpressionSolver gemacht!!!
				//sExpressionSolved = sExpression;
				//sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ, false); //von aussen nach innen. So bleiben Z-Tags innen(z.B. um den Pfad herum) erhalten.
				sExpressionSolved = EnvironmentZZZ.getHostName();	
				
				IKernelConfigSectionEntryZZZ objEntry3 = objExpressionSolver.solveAsEntryNew(sExpression);
				sValue = objEntry3.getValue();

				String sExpressionSolvedTemp = sExpressionSolved;
				//in diesem Fall immer true  if(objEnumSurrounding.getSurroundingValueUsed()) {
				if(bUseExpressionGeneral && bUseSolver){
					sExpressionSolvedTemp = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
				}
				assertEquals(sExpressionSolvedTemp, sValue);
				assertFalse("Mit Aufloesung soll Ausgabe anders als Eingabe sein.",sExpression.equals(sValue));
				
				bValue = objEntry3.isCall();				
				assertTrue(bValue);
				
				bValue = objEntry3.isJavaCall();
				assertTrue(bValue);
								
				sClassname = objEntry3.getCallingClassname();
				assertEquals("basic.zBasic.util.machine.EnvironmentZZZ",sClassname);
				
				sMethodname = objEntry3.getCallingMethodname();
				assertEquals("getHostName",sMethodname);
				
				
				//++++++++++++++++++++++++++++++++++++++++++++++++++
			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			}
		}

		
	}//END class
		

