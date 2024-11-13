package basic.zKernel.file.ini;

import java.io.File;
import java.util.Vector;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedTestCaseZZZ;
import basic.zBasic.util.abstractList.Vector3ZZZ;
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
		public final static String sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT = "<Z><Z:Call><Z:Java><Z:Class>basic.zBasic.util.machine.EnvironmentZZZ</Z:Class><Z:Method>getHostName</Z:Method></Z:Java></Z:Call></Z>";
		public final static String sEXPRESSION_CALL01_DEFAULT = "<Z><Z:Call><Z:Java><Z:Class><Z>[ArgumentSection for testCallComputed]JavaClass</Z></Z:Class><Z:Method><Z>[ArgumentSection for testCallComputed]JavaMethod</Z></Z:Method></Z:Java></Z:Call></Z>";			
		
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
								IIniTagWithExpressionZZZ.FLAGZ.USEEXPRESSION.name(),
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
				//Test ohne notwendige Pfadersetzung
				sExpressionSource = KernelCallIniSolverZZZTest.sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT; 
				testCompute_Call_(sExpressionSource,"","");
				
				//Teste die Pfadersetzung, die nicht nur im KernelExpresssionIniHandlerZZZ funktionieren soll.
				sExpressionSource = KernelCallIniSolverZZZTest.sEXPRESSION_CALL01_DEFAULT; 
				testCompute_Call_(sExpressionSource,"","");
				

//			} catch (ExceptionZZZ ez) {
//				fail("Method throws an exception." + ez.getMessageLast());
//			}
		}
		
		
		
		/**void, Test: Reading an entry in a section of the ini-file
		 * 
		 * @author Fritz Lindhauer, 05.05.2023, 08:54:30
		 */
		private void testCompute_Call_(String sExpressionSourceIn, String sPreIn, String sPostIn){
			
			boolean btemp; int itemp;
			
			String sSection; String sProperty;
			String sExpressionSource; 
			String sExpressionSolved; String sExpressionSolvedTagless;
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
				
				//+++ Ohne jegliche Expression-Berechnung
			
				//c)
				sExpressionSource = sPre + sExpressionSourceIn + sPost;
				sExpressionSolved = sPre + sExpressionSourceIn + sPost; 			
				btemp = testCompute_Call_Unexpressed_(sExpressionSource, sExpressionSolved, false, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
							
				//+++++++ VORGEZOGENER LETZTER FEHLERTEST ENDE
				
				
				
				//###########################
			    //### objExpression
				//#########################
				
				//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
				//+++ Ohne jegliche Expression-Berechnung
				//a)
				sExpressionSource = sPre + sExpressionSourceIn + sPost;
				sExpressionSolved = sPre + sExpressionSourceIn + sPost;
				btemp = testCompute_Call_Unexpressed_(sExpressionSource, sExpressionSolved, false, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
				
				//b)
				sExpressionSource = sPre + sExpressionSourceIn + sPost;
				sExpressionSolved = sPre + sExpressionSourceIn + sPost;			
				btemp = testCompute_Call_Unexpressed_(sExpressionSource, sExpressionSolved, true, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
				
				//c)
				sExpressionSource = sPre + sExpressionSourceIn + sPost;
				sExpressionSolved = sPre + sExpressionSourceIn + sPost; 			
				btemp = testCompute_Call_Unexpressed_(sExpressionSource, sExpressionSolved, false, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
					
				//d)
				sExpressionSource = sPre + sExpressionSourceIn + sPost;
				sExpressionSolved = sPre + sExpressionSourceIn + sPost;
				btemp = testCompute_Call_Unexpressed_(sExpressionSource, sExpressionSolved, true, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
				
				
				//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
				//+++ Ohne Solver-Berechung		
				//a)
				sExpressionSource = sPre + sExpressionSourceIn + sPost;
				sExpressionSolved = sPre + sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT + sPost; //auch ohne Solver werden die Pfade substituiert!!!
				btemp = testCompute_Call_SolverUnsolved_(sExpressionSource, sExpressionSolved, false, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
				
				//b)
				sExpressionSource = sPre + sExpressionSourceIn + sPost;
				sExpressionSolved = sPre + sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT + sPost; //auch ohne Solver werden die Pfade substituiert!!!
				sExpressionSolvedTagless = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ, false);
				//Beim Parsen ohne Solver, bleibt sogar das Encryption-Tag drin, auch wenn sonst die Tags entfernt werden.
				btemp = testCompute_Call_SolverUnsolved_(sExpressionSource, sExpressionSolvedTagless, true, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);

				//c)
				sExpressionSource = sPre + sExpressionSourceIn + sPost;
				sExpressionSolved = sPre + sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT + sPost; //auch ohne Solver werden die Pfade substituiert!!!
				//Beim Solven ohne Solver, bleibt alles wie est ist.
				btemp = testCompute_Call_SolverUnsolved_(sExpressionSource, sExpressionSolved, false, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
				
				//d)
				sExpressionSource = sPre + sExpressionSourceIn + sPost;
				sExpressionSolved = sPre + sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT + sPost; //auch ohne Solver werden die Pfade substituiert!!!
				sExpressionSolvedTagless = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ, false);
				//Beim Solven ohne Solver, werden nur die äusseren Z-Tags ggfs. entfernt.
				btemp = testCompute_Call_SolverUnsolved_(sExpressionSource, sExpressionSolvedTagless, true, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
				
				//++++++++++++++++++++++++++++++++++++++++++++++++++
				//+++ Ohne Call-Berechung
				//a)
				sExpressionSource = sPre + sExpressionSourceIn + sPost;
				sExpressionSolved = sPre + sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT + sPost; //auch ohne Solver werden die Pfade substituiert!!!
				btemp = testCompute_Call_CallUnsolved_(sExpressionSource, sExpressionSolved, false, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
				
				//b)
				sExpressionSource = sPre + sExpressionSourceIn + sPost;
				sExpressionSolved = sPre + sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT + sPost; //auch ohne Solver werden die Pfade substituiert!!!
				//Halt kein Call - Tag mehr im Ergebnis Erwartet....   sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSolved, KernelCallIniSolverZZZ.sTAG_NAME);
				sExpressionSolvedTagless = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ, false);				
				//Beim Parsen ohne call, muss doch dieser call - Tag drinbleiben. Hier werden also nur die aeussern Z-Tags entfernt.
				btemp = testCompute_Call_CallUnsolved_(sExpressionSource, sExpressionSolvedTagless, true, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
				
				//c)
				sExpressionSource = sPre + sExpressionSourceIn + sPost;
				sExpressionSolved = sPre + sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT + sPost; //auch ohne Solver werden die Pfade substituiert!!!
				//Beim Solven ohne call, bleibt alles an Tags drin.
				btemp = testCompute_Call_CallUnsolved_(sExpressionSource, sExpressionSolved, false, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
			
				//d)
				sExpressionSource = sPre + sExpressionSourceIn + sPost;
				sExpressionSolved = sPre + sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT + sPost; //auch ohne Solver werden die Pfade substituiert!!!
				sExpressionSolvedTagless = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ, false);
				btemp = testCompute_Call_CallUnsolved_(sExpressionSource, sExpressionSolvedTagless, true, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
				
				//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
				//+++ Mit Call-Berechnung OHNE JavaCall-Berechnung
				//a)
				sExpressionSource = sPre + sExpressionSourceIn + sPost;
				sExpressionSolved = sPre + sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT + sPost; //auch ohne Solver werden die Pfade substituiert!!!				
				btemp = testCompute_Call_JavaCallUnsolved_(sExpressionSource, sExpressionSolved, false, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
				
				//b)
				sExpressionSource = sPre + sExpressionSourceIn + sPost;
				sExpressionSolved = sPre + sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT + sPost; //auch ohne Solver werden die Pfade substituiert!!!
				sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSolved, KernelCallIniSolverZZZ.sTAG_NAME);				
				sExpressionSolvedTagless = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ, false); //von aussen nach innen. So bleiben Z-Tags innen(z.B. um den Pfad herum) erhalten.
				//Werdem beim reinen Parsen die umgebenden Tags entfernt, dann wird auch das call-Tag entfernt. Das wird naemlich auch durch Parsen "aufgeloest". Das eigentliche Aufloesen findet aber nicht statt.
				btemp = testCompute_Call_JavaCallUnsolved_(sExpressionSource, sExpressionSolvedTagless, true, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
				
				//c)
				sExpressionSource = sPre + sExpressionSourceIn + sPost;
				sExpressionSolved = sPre + sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT + sPost; //auch ohne Solver werden die Pfade substituiert!!!
				
				//Konflikt: 
				//Normalfall: Aufloesen mit solve -> z:call, z:JavaCall weg, etc.
				//      ABER: bRemoveSurrounding ist false gesetzt!!! -> D.h. Tags bleiben drin.
				
				//Also: z:Call soll aus dem Ergebnis weg sein, wg. Aufloesen!!! Auch wenn die umgebenden Z-Tags drin bleiben.
				//      Weil JavaCall in diesem Test nicht verwendet wird, bleibt dieser drin.
				sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSolved, KernelCallIniSolverZZZ.sTAG_NAME);
				btemp = testCompute_Call_JavaCallUnsolved_(sExpressionSource, sExpressionSolved, false, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
				
				//d)
				sExpressionSource = sPre + sExpressionSourceIn + sPost;
				sExpressionSolved = sPre + sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT + sPost; //auch ohne Solver werden die Pfade substituiert!!!
				sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSolved, KernelCallIniSolverZZZ.sTAG_NAME);		
				sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
				btemp = testCompute_Call_JavaCallUnsolved_(sExpressionSource, sExpressionSolved, true, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
		
				
				//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
				//+++ Mit Call-Berechnung UND JavaCall-Berechnung
				//a)
				sExpressionSource = sPre + sExpressionSourceIn + sPost;
				sExpressionSolved = sPre + sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT + sPost; //auch ohne Solver werden die Pfade substituiert!!!				
				btemp = testCompute_Call_(sExpressionSource, sExpressionSolved, false, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
				
				//b)
				sExpressionSource = sPre + sExpressionSourceIn + sPost;
				sExpressionSolved = sPre + sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT + sPost; //auch ohne Solver werden die Pfade substituiert!!!
				sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSolved, KernelCallIniSolverZZZ.sTAG_NAME);
				sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSolved, KernelJavaCallIniSolverZZZ.sTAG_NAME);
				sExpressionSolvedTagless = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ, false); //von aussen nach innen. So bleiben Z-Tags innen(z.B. um den Pfad herum) erhalten.
				//Werdem beim reinen Parsen die umgebenden Tags entfernt, dann wird auch das call-Tag entfernt. Das wird naemlich auch durch Parsen "aufgeloest". Das eigentliche Aufloesen findet aber nicht statt.
				btemp = testCompute_Call_(sExpressionSource, sExpressionSolvedTagless, true, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
				
				//c)
				sExpressionSource = sPre + sExpressionSourceIn + sPost;
				sExpressionSolved = sPre + sTagStartZ + sHostName + sTagEndZ + sPost; 
				//Wichtig: z:Call und z:Java sollen aus dem Ergebnis weg sein, wg. Aufloesen!!! Auch wenn die umgebenden Z-Tags drin bleiben.
				//Merke: Das geht aber bei uebergebenen Pfaden nur, wenn sie auch aufgeloest werden und einen sinnvolle Klasse und Methode zurueckkommt.
				btemp = testCompute_Call_(sExpressionSource, sExpressionSolved, false, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
				
				//d)
				sExpressionSource = sPre + sExpressionSourceIn + sPost;
				sExpressionSolved = sPre + sHostName +sPre;			
				btemp = testCompute_Call_(sExpressionSource, sExpressionSolved, true, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
								
			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			}		
		}
		
		
		private boolean testCompute_Call_(String sExpressionSourceIn, String sExpressionSolvedIn, boolean bRemoveSurroundingSeparators, IEnumSetMappedTestCaseZZZ objEnumTestCase) {
			boolean bReturn = false;
			try {
				boolean btemp; 
				
				String sExpressionSource; 
				String sExpressionSolved; String sValue; String sValueUsed;				
				ReferenceZZZ<IKernelConfigSectionEntryZZZ> objSectionEntryReference;
				IKernelConfigSectionEntryZZZ objEntry; IKernelConfigSectionEntryZZZ objEntryUsed;
				
				
				//####################################################################################
				//### EXPRESSION .solve
				//####################################################################################
				
				//Anwenden der ersten Formel, ohne Berechnung oder Formelersetzung						
				sExpressionSource = sExpressionSourceIn;
				sExpressionSolved = sExpressionSolvedIn;
				
				btemp = objExpressionSolver.setFlag(IIniTagWithExpressionZZZ.FLAGZ.USEEXPRESSION, true); 
				assertTrue("Flag nicht vorhanden '" + IIniTagWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);

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
				
					
				//+++ ... parse ist nicht solve... also wird hier nichts aufgeloest, aussser die Pfade
				if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.PARSE)) {
					sExpressionSource = sExpressionSourceIn;
					sExpressionSolved = sExpressionSolvedIn;		
					objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
					sValue = objExpressionSolver.parse(sExpressionSource, objSectionEntryReference, bRemoveSurroundingSeparators);
					assertEquals(sExpressionSolved, sValue);
					
					objEntry = objSectionEntryReference.get();
					assertNotNull(objEntry);
					
					//Falls Pfade substituiert werden, gibt es ebenfalls einen unterschied, der fuer isParsed sorgt.
//					if(bRemoveSurroundingSeparators | objEntry.isPathSubstituted()) {
//						assertTrue(objEntry.isParsed());
//					}else {
//						assertFalse(objEntry.isParsed());
//					}
//					if(!sExpressionSource.equals(sExpressionSolved)) {
//						assertTrue(objEntry.isParsed());						
//					}else {
//						assertFalse(objEntry.isParsed());
//					}
					
					assertTrue(objEntry.isParsed());
					assertTrue(objEntry.isExpression());
					assertFalse(objEntry.isSolved()); //Ist halt kein Solve-Schritt involviert.
					
					assertFalse(objEntry.isDecrypted());
					assertNull(objEntry.getValueDecrypted()); //Merke: sValue kann unterschiedlich zu dem decrypted Wert sein. Wenn etwas drumherum steht.
					
					assertTrue(objEntry.isCall());			//Beim Parsen wird das festgestellt
					assertTrue(objEntry.isJavaCall());		//Beim Parsen wird das festgestellt
										
					assertNull(objEntry.getCallingClassname());
					assertNull(objEntry.getCallingMethodname());
				}
				//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
					
				//+++ ... solve verhält sich NICHT wie parse(), bei solve wird aufgeloest...
				if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.SOLVE)) {
					sExpressionSource = sExpressionSourceIn;
					sExpressionSolved = sExpressionSolvedIn;
					objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
					sValue = objExpressionSolver.solve(sExpressionSource, objSectionEntryReference, bRemoveSurroundingSeparators);
					assertEquals(sExpressionSolved, sValue);
					
					objEntry = objSectionEntryReference.get();
					assertNotNull(objEntry);
					
					assertTrue(objEntry.isParsed());
					assertTrue(objEntry.isExpression());
					assertTrue(objEntry.isSolved());
					
					assertFalse(objEntry.isDecrypted());
					assertNull(objEntry.getValueDecrypted()); //Merke: sValue kann unterschiedlich zu dem decrypted Wert sein. Wenn etwas drumherum steht. 
									
					assertTrue(objEntry.isCall());			//Beim Parsen wird das festgestellt
					assertTrue(objEntry.isJavaCall());		//Beim Parsen wird das festgestellt
					assertNotNull(objEntry.getCallingClassname());
					assertNotNull(objEntry.getCallingMethodname());
				}
				//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			
				//+++ Variante fuer einen AsEntry-Test
				if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.PARSE_AS_ENTRY)) {
					sExpressionSource = sExpressionSourceIn;
					sExpressionSolved = sExpressionSolvedIn;
					objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
					objEntryUsed = objExpressionSolver.parseAsEntry(sExpressionSource, bRemoveSurroundingSeparators);				
					assertNotNull(objEntryUsed);
					
					//Falls Pfade substituiert werden, gibt es ebenfalls einen unterschied, der fuer isParsed sorgt.
//					if(bRemoveSurroundingSeparators | objEntryUsed.isPathSubstituted()) {
//						assertTrue(objEntryUsed.isParsed());
//					}else {
//						assertFalse(objEntryUsed.isParsed());
//					}
//					if(!sExpressionSource.equals(sExpressionSolved)) {
//						assertTrue(objEntryUsed.isParsed());						
//					}else {
//						assertFalse(objEntryUsed.isParsed());
//					}
					
					assertTrue(objEntryUsed.isParsed());
					assertTrue(objEntryUsed.isExpression());
					assertFalse(objEntryUsed.isSolved()); //Ist kein solve-Schritt involviert.				
					sValueUsed = objEntryUsed.getValue();
					assertEquals(sExpressionSolved, sValueUsed);
				
					assertFalse(objEntryUsed.isDecrypted()); //Ist kein solve-Schritt involviert.
					assertNull(objEntryUsed.getValueDecrypted());  				
					
					assertTrue(objEntryUsed.isCall());     //Beim Parsen wird das festgestellt
					assertTrue(objEntryUsed.isJavaCall()); //Beim Parsen wird das festgestellt
					assertNull(objEntryUsed.getCallingClassname());
					assertNull(objEntryUsed.getCallingMethodname());
				}
				
				//+++ Variante fuer einen AsEntry-Test
				if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.SOLVE_AS_ENTRY)) {
					sExpressionSource = sExpressionSourceIn;
					sExpressionSolved = sExpressionSolvedIn;
					objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
										
					objEntryUsed = objExpressionSolver.solveAsEntry(sExpressionSource, bRemoveSurroundingSeparators);				
					assertNotNull(objEntryUsed);
					
					//Falls Pfade substituiert werden, gibt es ebenfalls einen unterschied, der fuer isParsed sorgt.
//					if(bRemoveSurroundingSeparators | objEntryUsed.isPathSubstituted()) {
//						assertTrue(objEntryUsed.isParsed());
//					}else {
//						assertFalse(objEntryUsed.isParsed());
//					}	
//					if(!sExpressionSource.equals(sExpressionSolved)) {
//						assertTrue(objEntryUsed.isParsed());						
//					}else {
//						assertFalse(objEntryUsed.isParsed());
//					}
					assertTrue(objEntryUsed.isParsed());
					assertTrue(objEntryUsed.isExpression());
					assertTrue(objEntryUsed.isSolved());				
					sValueUsed = objEntryUsed.getValue();
					assertEquals(sExpressionSolved, sValueUsed);
				
					assertFalse(objEntryUsed.isDecrypted()); //Ist kein solve-Schritt involviert.
					assertNull(objEntryUsed.getValueDecrypted());  				
					
					assertTrue(objEntryUsed.isCall());			//Beim Parsen wird das festgestellt
					assertTrue(objEntryUsed.isJavaCall());		//Beim Parsen wird das festgestellt
					assertNotNull(objEntryUsed.getCallingClassname());
					assertNotNull(objEntryUsed.getCallingMethodname());
				}
				
				
				bReturn = true;
			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			}
			return bReturn;
		}
		
		private boolean testCompute_Call_JavaCallUnsolved_(String sExpressionSourceIn, String sExpressionSolvedIn, boolean bRemoveSuroundingSeparators, IEnumSetMappedTestCaseZZZ objEnumTestCase) {
			boolean bReturn = false;
			
			try {
				boolean btemp; 
				
				String sExpressionSource; 
				String sExpressionSolved; String sValue; String sValueUsed;			
				ReferenceZZZ<IKernelConfigSectionEntryZZZ> objSectionEntryReference;
				IKernelConfigSectionEntryZZZ objEntry; IKernelConfigSectionEntryZZZ objEntryUsed;  
						
				//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++		
				//Nur Expression ausrechnen, ist aber unverändert vom reinen Ergebnis her.		
				btemp = objExpressionSolver.setFlag(IIniTagWithExpressionZZZ.FLAGZ.USEEXPRESSION, true); 
				assertTrue("Flag nicht vorhanden '" + IIniTagWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);

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
			
			
				
				//+++ ... parse ist nicht solve... also wird hier nichts aufgeloest, aussser die Pfade
				if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.PARSE)) {
					sExpressionSource = sExpressionSourceIn;
					sExpressionSolved = sExpressionSolvedIn;		
					objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
					sValue = objExpressionSolver.parse(sExpressionSource, objSectionEntryReference, bRemoveSuroundingSeparators);
					assertEquals(sExpressionSolved, sValue);
					
					objEntry = objSectionEntryReference.get();
					assertNotNull(objEntry);
					
					//spannend, nicht aufgeloest, aber geparsed!!!
					//allerdings gibt es nur einen Unterschied, wenn die umgebenden Tags entfernt wurden!!!
					//also, pruefen, ob die Pfade aufgeloest sind... dann ist es klar, dass ein Unterschied vorhanden ist, auch wenn nix "gesolved" ist.
					//if(bRemoveSuroundingSeparators | objEntry.isPathSubstituted() ) {
					//	assertTrue(objEntry.isParsed());  
					//}else {
					//	assertFalse(objEntry.isParsed());
					//}
					
//					if(!sExpressionSource.equals(sExpressionSolved)) {
//						assertTrue(objEntry.isParsed());						
//					}else {
//						assertFalse(objEntry.isParsed());
//					}
					assertTrue(objEntry.isParsed());
					assertTrue(objEntry.isExpression());
					
					assertFalse(objEntry.isSolved()); //Der konkrete Solver ist nicht involviert
					
					assertFalse(objEntry.isDecrypted());
					assertNull(objEntry.getValueDecrypted()); //Merke: sValue kann unterschiedlich zu dem decrypted Wert sein. Wenn etwas drumherum steht.
					
					assertTrue(objEntry.isCall());		//Beim Parsen wird das festgestellt
					assertFalse(objEntry.isJavaCall());	//weil der Solver nicht betrachtet wird, kommt da nix raus
					assertNull(objEntry.getCallingClassname());
					assertNull(objEntry.getCallingMethodname());
				}
				//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
					
				//+++ ... solve verhält sich NICHT wie parse(), bei solve wird aufgeloest...
				if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.SOLVE)) {
					sExpressionSource = sExpressionSourceIn;
					sExpressionSolved = sExpressionSolvedIn;
					objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
					sValue = objExpressionSolver.solve(sExpressionSource, objSectionEntryReference, bRemoveSuroundingSeparators);
					assertEquals(sExpressionSolved, sValue);

					objEntry = objSectionEntryReference.get();
					assertNotNull(objEntry);
					
					//spannend, nicht aufgeloest, aber geparsed!!!
					//allerdings gibt es nur einen Unterschied, wenn die umgebenden Tags entfernt wurden!!!
					//Das gilt hier: Da nix aufgeloest wird durch den solver.
					//also, pruefen, ob die Pfade aufgeloest sind... dann ist es klar, dass ein Unterschied vorhanden ist, auch wenn nix "gesolved" ist.
					//if(bRemoveSuroundingSeparators | objEntry.isPathSubstituted()) {
					//	assertTrue(objEntry.isParsed());  
					//}else {
					//	assertFalse(objEntry.isParsed());
					//}
					
//					if(!sExpressionSource.equals(sExpressionSolved)) {
//						assertTrue(objEntry.isParsed());						
//					}else {
//						assertFalse(objEntry.isParsed());
//					}
					assertTrue(objEntry.isParsed());
					assertTrue(objEntry.isExpression());
					assertFalse(objEntry.isSolved()); //Der konkrete Solver ist nicht involviert
					
					assertFalse(objEntry.isDecrypted());
					assertNull(objEntry.getValueDecrypted()); //Merke: sValue kann unterschiedlich zu dem decrypted Wert sein. Wenn etwas drumherum steht.
					
					assertTrue(objEntry.isCall());		//Beim Parsen wird das festgestellt
					assertFalse(objEntry.isJavaCall());	//weil der Solver nicht betrachtet wird, kommt da nix raus
					assertNull(objEntry.getCallingClassname());
					assertNull(objEntry.getCallingMethodname());
				}
				//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
				
				//+++ Variante fuer den AsEntry-Test
				if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.PARSE_AS_ENTRY)) {
					sExpressionSource = sExpressionSourceIn;
					sExpressionSolved = sExpressionSolvedIn;
					objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
					objEntryUsed = objExpressionSolver.parseAsEntry(sExpressionSource, bRemoveSuroundingSeparators);				
					assertNotNull(objEntryUsed);
					
					//also, pruefen, ob die Pfade aufgeloest sind... dann ist es klar, dass ein Unterschied vorhanden ist, auch wenn nix "gesolved" ist.
					//if(bRemoveSuroundingSeparators | objEntryUsed.isPathSubstituted()) {
					//	assertTrue(objEntryUsed.isParsed());  
					//}else {
					//	assertFalse(objEntryUsed.isParsed());
					//}
//					if(!sExpressionSource.equals(sExpressionSolved)) {
//						assertTrue(objEntryUsed.isParsed());						
//					}else {
//						assertFalse(objEntryUsed.isParsed());
//					}
					assertTrue(objEntryUsed.isParsed());	
					assertTrue(objEntryUsed.isExpression());
					assertFalse(objEntryUsed.isSolved()); //Der konkrete Solver ist nicht involviert
					
					sValueUsed = objEntryUsed.getValue();
					assertEquals(sExpressionSolved, sValueUsed);
					
					
					assertFalse(objEntryUsed.isDecrypted());
					assertNull(objEntryUsed.getValueDecrypted()); 				
					
					assertTrue(objEntryUsed.isCall());		//Beim Parsen wird das festgestellt
					assertFalse(objEntryUsed.isJavaCall());	//weil der Solver nicht betrachtet wird, kommt da nix raus
					assertNull(objEntryUsed.getCallingClassname());
					assertNull(objEntryUsed.getCallingMethodname());
				}
				
				bReturn = true;
			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			}
			
			return bReturn;
		}
		
		private boolean testCompute_Call_CallUnsolved_(String sExpressionSourceIn, String sExpressionSolvedIn, boolean bRemoveSuroundingSeparators, IEnumSetMappedTestCaseZZZ objEnumTestCase) {
			boolean bReturn = false;
			
			try {
				boolean btemp; 
				
				String sExpressionSource; 
				String sExpressionSolved; String sValue; String sValueUsed;			
				ReferenceZZZ<IKernelConfigSectionEntryZZZ> objSectionEntryReference;
				IKernelConfigSectionEntryZZZ objEntry; IKernelConfigSectionEntryZZZ objEntryUsed;  
						
				//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++		
				//Nur Expression ausrechnen, ist aber unverändert vom reinen Ergebnis her.		
				btemp = objExpressionSolver.setFlag(IIniTagWithExpressionZZZ.FLAGZ.USEEXPRESSION, true); 
				assertTrue("Flag nicht vorhanden '" + IIniTagWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);

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
			
			
				
				//+++ ... parse ist nicht solve... also wird hier nichts aufgeloest, aussser die Pfade
				if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.PARSE)) {
					sExpressionSource = sExpressionSourceIn;
					sExpressionSolved = sExpressionSolvedIn;		
					objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
					sValue = objExpressionSolver.parse(sExpressionSource, objSectionEntryReference, bRemoveSuroundingSeparators);
					assertEquals(sExpressionSolved, sValue);
					
					objEntry = objSectionEntryReference.get();
					assertNotNull(objEntry);
					
					//spannend, nicht aufgeloest, aber geparsed!!!
					//allerdings gibt es nur einen Unterschied, wenn die umgebenden Tags entfernt wurden!!!
					//also, pruefen, ob die Pfade aufgeloest sind... dann ist es klar, dass ein Unterschied vorhanden ist, auch wenn nix "gesolved" ist.
//					if(bRemoveSuroundingSeparators | objEntry.isPathSubstituted() ) {
//						assertTrue(objEntry.isParsed());  
//					}else {
//						assertFalse(objEntry.isParsed());
//					}
					assertTrue(objEntry.isParsed()); 
					assertTrue(objEntry.isExpression()); 
					assertFalse(objEntry.isSolved()); //Der konkrete Solver ist nicht involviert
					
					assertFalse(objEntry.isDecrypted());
					assertNull(objEntry.getValueDecrypted()); //Merke: sValue kann unterschiedlich zu dem decrypted Wert sein. Wenn etwas drumherum steht.
					
					assertTrue(objEntry.isCall());		//Beim Parsen wird das festgestellt
					assertFalse(objEntry.isJavaCall());	//weil der Solver nicht betrachtet wird, kommt da nix raus
					assertNull(objEntry.getCallingClassname());
					assertNull(objEntry.getCallingMethodname());
				}
				//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
					
				//+++ ... solve verhält sich NICHT wie parse(), bei solve wird aufgeloest...
				if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.SOLVE)) {
					sExpressionSource = sExpressionSourceIn;
					sExpressionSolved = sExpressionSolvedIn;
					objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
					sValue = objExpressionSolver.solve(sExpressionSource, objSectionEntryReference, bRemoveSuroundingSeparators);
					assertEquals(sExpressionSolved, sValue);

					objEntry = objSectionEntryReference.get();
					assertNotNull(objEntry);
					
					//spannend, nicht aufgeloest, aber geparsed!!!
					//allerdings gibt es nur einen Unterschied, wenn die umgebenden Tags entfernt wurden!!!
					//Das gilt hier: Da nix aufgeloest wird durch den solver.
					//also, pruefen, ob die Pfade aufgeloest sind... dann ist es klar, dass ein Unterschied vorhanden ist, auch wenn nix "gesolved" ist.
//					if(bRemoveSuroundingSeparators | objEntry.isPathSubstituted()) {
//						assertTrue(objEntry.isParsed());  
//					}else {
//						assertFalse(objEntry.isParsed());
//					}
					assertTrue(objEntry.isParsed());
					assertTrue(objEntry.isExpression());					
					assertFalse(objEntry.isSolved()); //Der konkrete Solver ist nicht involviert
					
					assertFalse(objEntry.isDecrypted());
					assertNull(objEntry.getValueDecrypted()); //Merke: sValue kann unterschiedlich zu dem decrypted Wert sein. Wenn etwas drumherum steht.
					
					assertTrue(objEntry.isCall());		//Beim Parsen wird das festgestellt
					assertFalse(objEntry.isJavaCall());	//weil der Solver nicht betrachtet wird, kommt da nix rausJavaCall());
					assertNull(objEntry.getCallingClassname());
					assertNull(objEntry.getCallingMethodname());
				}
				//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
				
				//+++ Variante fuer den AsEntry-Test
				if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.PARSE_AS_ENTRY)) {
					sExpressionSource = sExpressionSourceIn;
					sExpressionSolved = sExpressionSolvedIn;					
					objEntryUsed = objExpressionSolver.parseAsEntry(sExpressionSource, bRemoveSuroundingSeparators);				
					assertNotNull(objEntryUsed);
					
					//also, pruefen, ob die Pfade aufgeloest sind... dann ist es klar, dass ein Unterschied vorhanden ist, auch wenn nix "gesolved" ist.
//					if(bRemoveSuroundingSeparators | objEntryUsed.isPathSubstituted()) {
//						assertTrue(objEntryUsed.isParsed());  
//					}else {
//						assertFalse(objEntryUsed.isParsed());
//					}
					assertTrue(objEntryUsed.isParsed());
					assertTrue(objEntryUsed.isExpression());
					assertFalse(objEntryUsed.isSolved()); //Der konkrete Solver ist nicht involviert
					
					sValueUsed = objEntryUsed.getValue();
					assertEquals(sExpressionSolved, sValueUsed);
					
					
					assertFalse(objEntryUsed.isDecrypted());
					assertNull(objEntryUsed.getValueDecrypted()); 				
					
					assertTrue(objEntryUsed.isCall());		//Beim Parsen wird das festgestellt
					assertFalse(objEntryUsed.isJavaCall());	//weil der Solver nicht betrachtet wird, kommt da nix raus
					assertNull(objEntryUsed.getCallingClassname());
					assertNull(objEntryUsed.getCallingMethodname());
				}
				
				//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
				
				//+++ ... solve verhält sich NICHT wie parse(), bei solve wird aufgeloest...
				if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.SOLVE_AS_ENTRY)) {
					sExpressionSource = sExpressionSourceIn;
					sExpressionSolved = sExpressionSolvedIn;					
					objEntryUsed = objExpressionSolver.solveAsEntry(sExpressionSource, bRemoveSuroundingSeparators);
					assertNotNull(objEntryUsed);
					
					sValue = objEntryUsed.getValue();
					assertEquals(sExpressionSolved, sValue);

					
					//spannend, nicht aufgeloest, aber geparsed!!!
					//allerdings gibt es nur einen Unterschied, wenn die umgebenden Tags entfernt wurden!!!
					//Das gilt hier: Da nix aufgeloest wird durch den solver.
					//also, pruefen, ob die Pfade aufgeloest sind... dann ist es klar, dass ein Unterschied vorhanden ist, auch wenn nix "gesolved" ist.
//					if(bRemoveSuroundingSeparators | objEntryUsed.isPathSubstituted()) {
//						assertTrue(objEntryUsed.isParsed());  
//					}else {
//						assertFalse(objEntryUsed.isParsed());
//					}
					assertTrue(objEntryUsed.isParsed());  
					assertTrue(objEntryUsed.isExpression());
					assertFalse(objEntryUsed.isSolved()); //Der konkrete Solver ist nicht involviert
					
					assertFalse(objEntryUsed.isDecrypted());
					assertNull(objEntryUsed.getValueDecrypted()); //Merke: sValue kann unterschiedlich zu dem decrypted Wert sein. Wenn etwas drumherum steht.
					
					assertTrue(objEntryUsed.isCall());		//Beim Parsen wird das festgestellt
					assertFalse(objEntryUsed.isJavaCall());	//weil der Solver nicht betrachtet wird, kommt da nix rausJavaCall());
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
		
		
		private boolean testCompute_Call_SolverUnsolved_(String sExpressionSourceIn, String sExpressionSolvedIn, boolean bRemoveSuroundingSeparators, IEnumSetMappedTestCaseZZZ objEnumTestCase) {
			boolean bReturn = false;
			try {
				boolean btemp; 
				
				String sExpressionSource; 
				String sExpressionSolved; String sValue; String sValueUsed;				
				ReferenceZZZ<IKernelConfigSectionEntryZZZ> objSectionEntryReference;
				IKernelConfigSectionEntryZZZ objEntry; IKernelConfigSectionEntryZZZ objEntryUsed;
				
				
				//####################################################################################
				//### EXPRESSION - CALL NICHT .solve
				//####################################################################################
							
				//Anwenden des Ausdrucks ohne Solver - Aufruf
				btemp = objExpressionSolver.setFlag(IIniTagWithExpressionZZZ.FLAGZ.USEEXPRESSION, true); 
				assertTrue("Flag nicht vorhanden '" + IIniTagWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
				
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
				
							
				//+++ ... parse ist nicht solve... also wird hier nichts aufgeloest, aussser die Pfade
				if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.PARSE)) {
					sExpressionSource = sExpressionSourceIn;
					sExpressionSolved = sExpressionSolvedIn;		
					objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
					sValue = objExpressionSolver.parse(sExpressionSource, objSectionEntryReference, bRemoveSuroundingSeparators);
					assertEquals(sExpressionSolved, sValue);
					
					objEntry = objSectionEntryReference.get();
					assertNotNull(objEntry);
					
					//spannend, nicht aufgeloest, aber geparsed!!!
					//allerdings gibt es nur einen Unterschied, wenn die umgebenden Tags entfernt wurden!!!
					//also, pruefen, ob die Pfade aufgeloest sind... dann ist es klar, dass ein Unterschied vorhanden ist, auch wenn nix "gesolved" ist.
//					if(bRemoveSuroundingSeparators | objEntry.isPathSubstituted()) {
//						assertTrue(objEntry.isParsed());  
//					}else {
//						assertFalse(objEntry.isParsed());
//					}
					assertTrue(objEntry.isParsed());
					assertTrue(objEntry.isExpression());
					assertFalse(objEntry.isSolved());
					
					assertFalse(objEntry.isDecrypted());
					assertNull(objEntry.getValueDecrypted()); //Merke: sValue kann unterschiedlich zu dem decrypted Wert sein. Wenn etwas drumherum steht.
					
					assertTrue(objEntry.isCall());		//Beim Parsen wird das festgestellt
					assertFalse(objEntry.isJavaCall());	//weil der Solver nicht betrachtet wird, kommt da nix raus
					assertNull(objEntry.getCallingClassname());
					assertNull(objEntry.getCallingMethodname());
				}
				//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
					
				//+++ ... solve verhält sich NICHT wie parse(), bei solve wird aufgeloest...
				if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.SOLVE)) {
					sExpressionSource = sExpressionSourceIn;
					sExpressionSolved = sExpressionSolvedIn;
					objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
					sValue = objExpressionSolver.solve(sExpressionSource, objSectionEntryReference, bRemoveSuroundingSeparators);
					assertEquals(sExpressionSolved, sValue);

					objEntry = objSectionEntryReference.get();
					assertNotNull(objEntry);
					
					//spannend, nicht aufgeloest, aber geparsed!!!
					//allerdings gibt es nur einen Unterschied, wenn die umgebenden Tags entfernt wurden!!!
					//Das gilt hier: Da nix aufgeloest wird durch den solver.
					//also, pruefen, ob die Pfade aufgeloest sind... dann ist es klar, dass ein Unterschied vorhanden ist, auch wenn nix "gesolved" ist.
//					if(bRemoveSuroundingSeparators | objEntry.isPathSubstituted()) {
//						assertTrue(objEntry.isParsed());  
//					}else {
//						assertFalse(objEntry.isParsed());
//					}
					assertTrue(objEntry.isParsed()); 
					assertTrue(objEntry.isExpression());
					assertFalse(objEntry.isSolved());
					
					assertFalse(objEntry.isDecrypted());
					assertNull(objEntry.getValueDecrypted()); //Merke: sValue kann unterschiedlich zu dem decrypted Wert sein. Wenn etwas drumherum steht.
					
					assertTrue(objEntry.isCall());		//Beim Parsen wird das festgestellt
					assertFalse(objEntry.isJavaCall());	//weil der Solver nicht betrachtet wird, kommt da nix raus
					assertNull(objEntry.getCallingClassname());
					assertNull(objEntry.getCallingMethodname());
				}
				//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
				
				//+++ Variante fuer den AsEntry-Test
				if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.PARSE_AS_ENTRY)) {
					sExpressionSource = sExpressionSourceIn;
					sExpressionSolved = sExpressionSolvedIn;
					objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
					objEntryUsed = objExpressionSolver.parseAsEntry(sExpressionSource, bRemoveSuroundingSeparators);				
					assertNotNull(objEntryUsed);
					
					//also, pruefen, ob die Pfade aufgeloest sind... dann ist es klar, dass ein Unterschied vorhanden ist, auch wenn nix "gesolved" ist.
//					if(bRemoveSuroundingSeparators | objEntryUsed.isPathSubstituted()) {
//						assertTrue(objEntryUsed.isParsed());  
//					}else {
//						assertFalse(objEntryUsed.isParsed());
//					}
					assertTrue(objEntryUsed.isParsed()); 
					assertTrue(objEntryUsed.isExpression());
					assertFalse(objEntryUsed.isSolved());				
					sValueUsed = objEntryUsed.getValue();
					assertEquals(sExpressionSolved, sValueUsed);
									
					assertFalse(objEntryUsed.isDecrypted());
					assertNull(objEntryUsed.getValueDecrypted()); //Merke: sValue kann unterschiedlich zu dem decrypted Wert sein. Wenn etwas drumherum steht.
					
					assertTrue(objEntryUsed.isCall()); 		//Beim Parsen wird das festgestellt
					assertFalse(objEntryUsed.isJavaCall());	//weil der Solver nicht betrachtet wird, kommt da nix raus
					assertNull(objEntryUsed.getCallingClassname());
					assertNull(objEntryUsed.getCallingMethodname());
				}
				
				//+++ ... solve verhält sich NICHT wie parse(), bei solve wird aufgeloest...
				if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.SOLVE_AS_ENTRY)) {
					sExpressionSource = sExpressionSourceIn;
					sExpressionSolved = sExpressionSolvedIn;
					objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
					objEntryUsed = objExpressionSolver.solveAsEntry(sExpressionSource, bRemoveSuroundingSeparators);				
					assertNotNull(objEntryUsed);
					
					//also, pruefen, ob die Pfade aufgeloest sind... dann ist es klar, dass ein Unterschied vorhanden ist, auch wenn nix "gesolved" ist.
//					if(bRemoveSuroundingSeparators | objEntryUsed.isPathSubstituted()) {
//						assertTrue(objEntryUsed.isParsed());  
//					}else {
//						assertFalse(objEntryUsed.isParsed());
//					}
					assertTrue(objEntryUsed.isParsed());
					assertTrue(objEntryUsed.isExpression());
					assertFalse(objEntryUsed.isSolved());				
					sValueUsed = objEntryUsed.getValue();
					assertEquals(sExpressionSolved, sValueUsed);
									
					assertFalse(objEntryUsed.isDecrypted());
					assertNull(objEntryUsed.getValueDecrypted()); //Merke: sValue kann unterschiedlich zu dem decrypted Wert sein. Wenn etwas drumherum steht.
					
					assertTrue(objEntryUsed.isCall()); 		//Beim Parsen wird das festgestellt
					assertFalse(objEntryUsed.isJavaCall());	//weil der Solver nicht betrachtet wird, kommt da nix raus
					assertNull(objEntryUsed.getCallingClassname());
					assertNull(objEntryUsed.getCallingMethodname());
				}
				
				bReturn = true;
			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			}
			return bReturn;
		}
		
		private boolean testCompute_Call_Unexpressed_(String sExpressionSourceIn, String sExpressionSolvedIn, boolean bRemoveSuroundingSeparators, IEnumSetMappedTestCaseZZZ objEnumTestCase) {
			boolean bReturn = false;
			try {
				boolean btemp; 
				
				String sExpressionSource; 
				String sExpressionSolved; String sValue; String sValueUsed;
				ReferenceZZZ<IKernelConfigSectionEntryZZZ> objSectionEntryReference;
				IKernelConfigSectionEntryZZZ objEntry; IKernelConfigSectionEntryZZZ objEntryUsed;
				
				
				//####################################################################################
				//### EXPRESSION - NICHT EXPRESSION BEHANDLUNG
				//####################################################################################
				
				//Anwenden der ersten Formel, ohne Berechnung oder Formelersetzung						
				sExpressionSource = sExpressionSourceIn;
				sExpressionSolved = sExpressionSolvedIn;
				
				btemp = objExpressionSolver.setFlag(IIniTagWithExpressionZZZ.FLAGZ.USEEXPRESSION, false); //Gar kein solver aktiviert
				assertTrue("Flag nicht vorhanden '" + IIniTagWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);

				btemp = objExpressionSolver.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH, true);			
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
			
				btemp = objExpressionSolver.setFlag(IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE, true);			
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE + "'", btemp);
							
				btemp = objExpressionSolver.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER, true); //soll dann egal sein
				assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
				
				btemp = objExpressionSolver.setFlag(IKernelCallIniSolverZZZ.FLAGZ.USECALL, true); //soll dann egal sein
				assertTrue("Flag nicht vorhanden '" + IKernelCallIniSolverZZZ.FLAGZ.USECALL + "'", btemp);
				
				btemp = objExpressionSolver.setFlag(IKernelJavaCallIniSolverZZZ.FLAGZ.USECALL_JAVA, true); //soll dann egal sein
				assertTrue("Flag nicht vorhanden '" + IKernelJavaCallIniSolverZZZ.FLAGZ.USECALL_JAVA + "'", btemp);
			
				//+++ ... parse ist nicht solve... also wird hier nichts aufgeloest, aussser die Pfade substituiert.
				if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.PARSE)) {
					sExpressionSource = sExpressionSourceIn;
					sExpressionSolved = sExpressionSolvedIn;		
					objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
					sValue = objExpressionSolver.parse(sExpressionSource, objSectionEntryReference, bRemoveSuroundingSeparators);
					assertEquals(sExpressionSolved, sValue);
					
					objEntry = objSectionEntryReference.get();
					assertNotNull(objEntry);
					
					//also, pruefen, ob die Pfade aufgeloest sind... dann ist es klar, dass ein Unterschied vorhanden ist, auch wenn nix "gesolved" ist.
//					if(bRemoveSuroundingSeparators | objEntry.isPathSubstituted()) {
//						assertTrue(objEntry.isParsed());
//					}else {
//						assertFalse(objEntry.isParsed());
//					}
					assertTrue(objEntry.isParsed()); //Auch wenn die Expression nicht verarbeitet wird, dann ist doch geparsed worden....
					assertFalse(objEntry.isExpression()); //Expression ist generell ausgestellt
					assertFalse(objEntry.isSolved());
					
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
					sExpressionSource = sExpressionSourceIn;
					sExpressionSolved = sExpressionSolvedIn;
					objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
					sValue = objExpressionSolver.solve(sExpressionSource, objSectionEntryReference, bRemoveSuroundingSeparators);
					assertEquals(sExpressionSolved, sValue);
					
					objEntry = objSectionEntryReference.get();
					assertNotNull(objEntry);
					

					//also, pruefen, ob die Pfade aufgeloest sind... dann ist es klar, dass ein Unterschied vorhanden ist, auch wenn nix "gesolved" ist.
//					if(bRemoveSuroundingSeparators | objEntry.isPathSubstituted()) {
//						assertTrue(objEntry.isParsed());
//					}else {
//						assertFalse(objEntry.isParsed());
//					}
					assertTrue(objEntry.isParsed()); //Auch wenn die Expression nicht verarbeitet wird, dann ist doch geparsed worden....
					assertFalse(objEntry.isExpression()); //Expression ist generell ausgestellt
					assertFalse(objEntry.isSolved());
									
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
					sExpressionSource = sExpressionSourceIn;
					sExpressionSolved = sExpressionSolvedIn;					
					objEntryUsed = objExpressionSolver.parseAsEntry(sExpressionSource, bRemoveSuroundingSeparators);				
					assertNotNull(objEntryUsed);
								
					 

					//also, pruefen, ob die Pfade aufgeloest sind... dann ist es klar, dass ein Unterschied vorhanden ist, auch wenn nix "gesolved" ist.
//					if(bRemoveSuroundingSeparators | objEntryUsed.isPathSubstituted()) {
//						assertTrue(objEntryUsed.isParsed());
//					}else {
//						assertFalse(objEntryUsed.isParsed());//es wird keinerlei expression Berechnung gemacht
//					}
					assertTrue(objEntryUsed.isParsed());     //es wurde ja geparsed
					assertFalse(objEntryUsed.isExpression()); //Expression ist generell ausgestellt
					assertFalse(objEntryUsed.isSolved()); //es ist auch kein Solver involviert
									
					sValueUsed = objEntryUsed.getValue();
					assertEquals(sExpressionSolved, sValueUsed);
					
					assertFalse(objEntryUsed.isDecrypted());
					assertNull(objEntryUsed.getValueDecrypted()); //Merke: sValue kann unterschiedlich zu dem decrypted Wert sein. Wenn etwas drumherum steht.
										
					assertFalse(objEntryUsed.isCall());
					assertFalse(objEntryUsed.isJavaCall());
					assertNull(objEntryUsed.getCallingClassname());
					assertNull(objEntryUsed.getCallingMethodname());
				}
				
				//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
				
				//+++ ... solve verhält sich NICHT wie parse(), bei solve wird aufgeloest...
				if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.SOLVE_AS_ENTRY)) {
					sExpressionSource = sExpressionSourceIn;
					sExpressionSolved = sExpressionSolvedIn;					
					objEntryUsed = objExpressionSolver.solveAsEntry(sExpressionSource, bRemoveSuroundingSeparators);
					assertNotNull(objEntryUsed);
					

					//also, pruefen, ob die Pfade aufgeloest sind... dann ist es klar, dass ein Unterschied vorhanden ist, auch wenn nix "gesolved" ist.
//					if(bRemoveSuroundingSeparators | objEntry.isPathSubstituted()) {
//						assertTrue(objEntry.isParsed());
//					}else {
//						assertFalse(objEntry.isParsed());
//					}
					assertTrue(objEntryUsed.isParsed());     //es wurde ja geparsed
					assertFalse(objEntryUsed.isExpression()); //Expression ist generell ausgestellt
					assertFalse(objEntryUsed.isSolved());
									
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
		
		
		//##########################################################
		//##########################################################
		
		/** void, Test: Reading an entry in a section of the ini-file
		* Lindhauer; 22.04.2006 12:54:32
		 */
		public void testCompute_CallAsEntry(){
			String sExpressionSource=null;
//			try {
				sExpressionSource = KernelCallIniSolverZZZTest.sEXPRESSION_CALL01_DEFAULT;
				testCompute_CallAsEntry_(sExpressionSource);

//			} catch (ExceptionZZZ ez) {
//				fail("Method throws an exception." + ez.getMessageLast());
//			}
		}
		
		/**void, Test: Reading an entry in a section of the ini-file
		 * 
		 * @author Fritz Lindhauer, 05.05.2023, 08:54:30
		 */
		private void testCompute_CallAsEntry_(String sExpressionSourceIn){
			
			boolean btemp; int itemp;
			
			String sSection; String sProperty;
			String sExpressionSource; 
			String sExpressionSolved; String sExpressionSolvedTagless;
			IKernelConfigSectionEntryZZZ objEntry; ReferenceZZZ<IKernelConfigSectionEntryZZZ>objSectionEntryReference;
		
			String sValue;
			String sTagStartZ = "<Z>";
			String sTagEndZ = "</Z>";	
			
			try {		
				String sHostName = EnvironmentZZZ.getHostName();
				assertNotNull(sHostName);
				
				//+++++++ VORGEZOGENER LETZTER FEHLERTEST START
				
				//+++ Ohne Solver-Berechung	
	
				//c)
				sExpressionSource = sExpressionSourceIn;
				sExpressionSolved = KernelCallIniSolverZZZTest.sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT;
				btemp = testCompute_Call_SolverUnsolved_(sExpressionSource, sExpressionSolved, false, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE_AS_ENTRY);
				
							
				//+++++++ VORGEZOGENER LETZTER FEHLERTEST ENDE
				
				
				
				//###########################
			    //### objExpression .... asEntry
				//#########################
				
				//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
				//+++ Ohne jegliche Expression-Berechnung
				//a)
				sExpressionSource = sExpressionSourceIn;
				sExpressionSolved = sExpressionSource;
				btemp = testCompute_Call_Unexpressed_(sExpressionSource, sExpressionSolved, false, EnumSetMappedTestCaseSolverTypeZZZ.PARSE_AS_ENTRY);
				
				//b)
				sExpressionSource = sExpressionSourceIn;
				sExpressionSolved = sExpressionSource;			
				btemp = testCompute_Call_Unexpressed_(sExpressionSource, sExpressionSolved, true, EnumSetMappedTestCaseSolverTypeZZZ.PARSE_AS_ENTRY);

				//c)
				sExpressionSource = sExpressionSourceIn;
				sExpressionSolved = sExpressionSource;
				btemp = testCompute_Call_Unexpressed_(sExpressionSource, sExpressionSolved, false, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE_AS_ENTRY);
				
				//d)
				sExpressionSource = sExpressionSourceIn;
				sExpressionSolved = sExpressionSource;			
				btemp = testCompute_Call_Unexpressed_(sExpressionSource, sExpressionSolved, true, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE_AS_ENTRY);
				
				
				
				
				//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
				//+++ Ohne Solver-Berechung	
				//a)
				sExpressionSource = sExpressionSourceIn;
				sExpressionSolved = KernelCallIniSolverZZZTest.sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT;
				btemp = testCompute_Call_SolverUnsolved_(sExpressionSource, sExpressionSolved, false, EnumSetMappedTestCaseSolverTypeZZZ.PARSE_AS_ENTRY);
				
				//b)
				sExpressionSource = sExpressionSourceIn;
				sExpressionSolved = KernelCallIniSolverZZZTest.sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT;
				sExpressionSolvedTagless = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ, false);
				//Beim Parsen ohne Solver, bleibt sogar das Call-Tag drin, auch wenn sonst die Tags entfernt werden.
				btemp = testCompute_Call_SolverUnsolved_(sExpressionSource, sExpressionSolvedTagless, true, EnumSetMappedTestCaseSolverTypeZZZ.PARSE_AS_ENTRY);

				//c)
				sExpressionSource = sExpressionSourceIn;
				sExpressionSolved = KernelCallIniSolverZZZTest.sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT;
				btemp = testCompute_Call_SolverUnsolved_(sExpressionSource, sExpressionSolved, false, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE_AS_ENTRY);
				
				//d)
				sExpressionSource = sExpressionSourceIn;
				sExpressionSolved = KernelCallIniSolverZZZTest.sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT;
				sExpressionSolvedTagless = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ, false);
				//Beim Parsen ohne Solver, bleibt sogar das Call-Tag drin, auch wenn sonst die Tags entfernt werden.
				btemp = testCompute_Call_SolverUnsolved_(sExpressionSource, sExpressionSolvedTagless, true, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE_AS_ENTRY);

				
				
				//+++ Ohne Call-Berechung
				//a)
				sExpressionSource = sExpressionSourceIn;
				sExpressionSolved = sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT; //auch ohne Solver werden die Pfade substituiert!!!
				btemp = testCompute_Call_CallUnsolved_(sExpressionSource, sExpressionSolved, false, EnumSetMappedTestCaseSolverTypeZZZ.PARSE_AS_ENTRY);
				
				
				//b)
				sExpressionSource = sExpressionSourceIn;
				sExpressionSolved = sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT; //auch ohne Solver werden die Pfade substituiert!!! 
				//Halt kein Call - Tag mehr im Ergebnis Erwartet....   sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSolved, KernelCallIniSolverZZZ.sTAG_NAME);
				sExpressionSolvedTagless = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ, false);				
				//Beim Parsen ohne call, muss doch dieser call - Tag drinbleiben. Hier werden also nur die aeussern Z-Tags entfernt.
				btemp = testCompute_Call_CallUnsolved_(sExpressionSource, sExpressionSolvedTagless, true, EnumSetMappedTestCaseSolverTypeZZZ.PARSE_AS_ENTRY);
				
				
				//c)
				sExpressionSource = sExpressionSourceIn;
				sExpressionSolved = KernelCallIniSolverZZZTest.sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT;
				//Beim Solven ohne call, bleibt alles an Tags drin.
				btemp = testCompute_Call_CallUnsolved_(sExpressionSource, sExpressionSolved, false, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE_AS_ENTRY);
				
				
				//d)
				sExpressionSource = sExpressionSourceIn;
				sExpressionSolved = KernelCallIniSolverZZZTest.sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT; 
				sExpressionSolvedTagless = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ, false);
				//Beim Parsen ohne call, muss doch dieser call - Tag drinbleiben. Hier werden also nur die aeussern Z-Tags entfernt.
				btemp = testCompute_Call_CallUnsolved_(sExpressionSource, sExpressionSolvedTagless, true, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE_AS_ENTRY);
				
				
				
				//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
				//+++ Mit Call-Berechnung, OHNE JavaCall Berechnung!!!
				//a)
				sExpressionSource = sExpressionSourceIn;
				sExpressionSolved = sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT; //auch ohne Solver werden die Pfade substituiert!!!	
				//sExpressionSolvedTagless = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ, false);				  						
				btemp = testCompute_Call_JavaCallUnsolved_(sExpressionSource, sExpressionSolved, false, EnumSetMappedTestCaseSolverTypeZZZ.PARSE_AS_ENTRY);
				
				//b)
				sExpressionSource = sExpressionSourceIn;
				sExpressionSolved = sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT; //auch ohne Solver werden die Pfade substituiert!!!
				sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSolved, KernelCallIniSolverZZZ.sTAG_NAME);				
				sExpressionSolvedTagless = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ, false); //von aussen nach innen. So bleiben Z-Tags innen(z.B. um den Pfad herum) erhalten.
				//Werdem beim reinen Parsen die umgebenden Tags entfernt, dann wird auch das call-Tag entfernt. Das wird naemlich auch durch Parsen "aufgeloest". Das eigentliche Aufloesen findet aber nicht statt.
				btemp = testCompute_Call_JavaCallUnsolved_(sExpressionSource, sExpressionSolvedTagless, true, EnumSetMappedTestCaseSolverTypeZZZ.PARSE_AS_ENTRY);
				
				//c)
				sExpressionSource = sExpressionSourceIn;
				sExpressionSolved = sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT; //auch ohne Solver werden die Pfade substituiert!!!
				
				//Konflikt: 
				//Normalfall: Aufloesen mit solve -> z:call, z:JavaCall weg, etc.
				//      ABER: bRemoveSurrounding ist false gesetzt!!! -> D.h. Tags bleiben drin.
				
				//Also: z:Call soll aus dem Ergebnis weg sein, wg. Aufloesen!!! Auch wenn die umgebenden Z-Tags drin bleiben.
				//      Weil JavaCall in diesem Test nicht verwendet wird, bleibt dieser drin.
				sExpressionSolvedTagless = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSolved, KernelCallIniSolverZZZ.sTAG_NAME);
				btemp = testCompute_Call_JavaCallUnsolved_(sExpressionSource, sExpressionSolvedTagless, false, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE_AS_ENTRY);
				
				//d)
				sExpressionSource = sExpressionSourceIn;
				sExpressionSolved = sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT; //auch ohne Solver werden die Pfade substituiert!!!
				sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSolved, KernelCallIniSolverZZZ.sTAG_NAME);		
				sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
				btemp = testCompute_Call_JavaCallUnsolved_(sExpressionSource, sExpressionSolvedTagless, true, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE_AS_ENTRY);
				
				
				//+++ Mit Call-Berechnung, und JavaCall-Berechnung!!!
				//a)
				sExpressionSource = sExpressionSourceIn;
				sExpressionSolved = sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT; //auch ohne Solver werden die Pfade substituiert!!!				  						
				btemp = testCompute_Call_(sExpressionSource, sExpressionSolved, false, EnumSetMappedTestCaseSolverTypeZZZ.PARSE_AS_ENTRY);
				
				//b)
				sExpressionSource = sExpressionSourceIn;
				sExpressionSolved = sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT; //auch ohne Solver werden die Pfade substituiert!!! 
				sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSolved, KernelCallIniSolverZZZ.sTAG_NAME);
				sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSolved, KernelJavaCallIniSolverZZZ.sTAG_NAME);
				sExpressionSolvedTagless = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ, false); //von aussen nach innen. So bleiben Z-Tags innen(z.B. um den Pfad herum) erhalten.
				//Werdem beim reinen Parsen die umgebenden Tags entfernt, dann wird auch das call-Tag entfernt. Das wird naemlich auch durch Parsen "aufgeloest". Das eigentliche Aufloesen findet aber nicht statt.
				btemp = testCompute_Call_(sExpressionSource, sExpressionSolvedTagless, true, EnumSetMappedTestCaseSolverTypeZZZ.PARSE_AS_ENTRY);
					
				//c)
				sExpressionSource = sExpressionSourceIn;
				sExpressionSolved = sTagStartZ + sHostName + sTagEndZ; 
				//Wichtig: z:Call und z:Java sollen aus dem Ergebnis weg sein, wg. Aufloesen!!! Auch wenn die umgebenden Z-Tags drin bleiben.
				//Merke: Das geht aber bei uebergebenen Pfaden nur, wenn sie auch aufgeloest werden und einen sinnvolle Klasse und Methode zurueckkommt.									
				btemp = testCompute_Call_(sExpressionSource, sExpressionSolved, false, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE_AS_ENTRY);
				
				//d)
				sExpressionSource = sExpressionSourceIn;
				sExpressionSolved = sHostName; 
				btemp = testCompute_Call_(sExpressionSource, sExpressionSolved, true, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE_AS_ENTRY);
					
			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			}		
		}

		
		
		
		
		/** void, Test: Reading an entry in a section of the ini-file
		* Lindhauer; 22.04.2006 12:54:32
		 */
		public void testCompute01ParseVsSolve(){
			
			boolean btemp; int itemp;
			Vector3ZZZ<String> vecReturn = null; Vector3ZZZ<String> vecReturnSubstituted = null;
			String sLineWithExpression = null; String sExpressionSolvedTagless = null;
			String sValue; String sValueSubstituted;
			String sTagStart; String sTagEnd;
			
			String sTagStartZ = "<Z>";
			String sTagEndZ = "</Z>";				
			try {									
				String sHostName = EnvironmentZZZ.getHostName();
				
				
				
				btemp = objExpressionSolver.setFlag(IIniTagWithExpressionZZZ.FLAGZ.USEEXPRESSION, true); 
				assertTrue("Flag nicht vorhanden '" + IIniTagWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);

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
				
				
				//+++++++ VORGEZOGENER LETZTER FEHLERTEST START
	
				
				
				//+++++++ VORGEZOGENER LETZTER FEHLERTEST ENDE
				
				
				
				//### Ohne Substitution durchführen
				sLineWithExpression = KernelCallIniSolverZZZTest.sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT;
				
				vecReturn = objExpressionSolver.parseFirstVector(sLineWithExpression);
				assertNotNull(vecReturn);
				assertFalse(StringZZZ.isEmpty((String)vecReturn.get(1))); //in der 0ten Position ist der String vor der Encryption, in der 3ten Position ist der String nach der Encryption.
								
				sValue = objExpressionSolver.parse(sLineWithExpression);
				System.out.println(ReflectCodeZZZ.getPositionCurrent() + "\tDebugausaugabe: '" + sValue + "'\n");
				assertEquals("parse(s) und parseFirstVector(s) sollte gleich sein.",vecReturn.get(1), sValue);

				//################################
				//### Mit Substitution
				sLineWithExpression = KernelCallIniSolverZZZTest.sEXPRESSION_CALL01_DEFAULT;
				
				vecReturnSubstituted = objExpressionSolver.parseFirstVector(sLineWithExpression);
				assertNotNull(vecReturnSubstituted);
				assertFalse(StringZZZ.isEmpty((String)vecReturnSubstituted.get(1))); //in der 0ten Position ist der String vor der Encryption, in der 3ten Position ist der String nach der Encryption.
								
				sValueSubstituted = objExpressionSolver.parse(sLineWithExpression);
				System.out.println(ReflectCodeZZZ.getPositionCurrent() + "\tDebugausaugabe: '" + sValueSubstituted + "'\n");
				assertEquals("parse(s) und parseFirstVector(s) sollte gleich sein.",vecReturnSubstituted.get(1), sValueSubstituted);
				
				
				sExpressionSolvedTagless = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(KernelCallIniSolverZZZTest.sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT, sTagStartZ, sTagEndZ, false);
				
				sTagStart = XmlUtilZZZ.computeTagPartStarting(KernelCallIniSolverZZZ.sTAG_NAME);
				sTagEnd = XmlUtilZZZ.computeTagPartClosing(KernelCallIniSolverZZZ.sTAG_NAME);
				sExpressionSolvedTagless = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSolvedTagless, sTagStart, sTagEnd, false);				
				
				sTagStart = XmlUtilZZZ.computeTagPartStarting(KernelJavaCallIniSolverZZZ.sTAG_NAME);
				sTagEnd = XmlUtilZZZ.computeTagPartClosing(KernelJavaCallIniSolverZZZ.sTAG_NAME);
				sExpressionSolvedTagless = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSolvedTagless, sTagStart, sTagEnd, false);
				System.out.println(ReflectCodeZZZ.getPositionCurrent() + "\tDebugausaugabe: '" + sExpressionSolvedTagless + "'\n");
				assertEquals(sExpressionSolvedTagless, sValueSubstituted);
					
				
				//###############################
				//### Nun die Gesamtberechnung, d.h. incl. Aufloesen durchführen
				//Anwenden der ersten Formel: Erst jetzt kommt das Ergebnis raus	
				sLineWithExpression = KernelCallIniSolverZZZTest.sEXPRESSION_CALL01_DEFAULT;
				
				sValue = objExpressionSolver.solve(sLineWithExpression);			
				assertFalse("Mit Auflösung soll Ausgabe anders als Eingabe sein.",sLineWithExpression.equals(sValue));
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
			String sValue; boolean bValue;
			String sClassname; String sMethodname;
			String sExpression ; String sExpressionSolved; String sExpressionSolvedTagless; String sValueAsExpression;			
			String sTagStartZ = "<Z>";
			String sTagEndZ = "</Z>";
			try {				
				sExpression = KernelCallIniSolverZZZTest.sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT;//Merke: Die Aufloesung von Formeln wird dann vom ExpressionSolver gemacht!!!
								
				boolean bFlagAvailable = objExpressionSolver.setFlag(IKernelJavaCallIniSolverZZZ.FLAGZ.USECALL_JAVA, false); //Ansonsten wird der Wert sofort ausgerechnet
				assertTrue("Das Flag 'usecall' sollte zur Verfügung stehen.", bFlagAvailable);
				
				//#################################################################################
				//### PARSE #######################################################################
				//#################################################################################
				
				//##########################################################
				//Teilberechnungen durchführen
				IKernelConfigSectionEntryZZZ objEntryTemp = new KernelConfigSectionEntryZZZ();//Hierin können während der Verarbeitung Zwischenergebnisse abgelegt werden, z.B. vor der Entschluesselung der pure Verscluesselte Wert.
				objExpressionSolver.setEntry(objEntryTemp);
			
				//Als Zwischenschritt die bisherigen rein stringbasierten Methoden im objEntry erweitern
				Vector<String> vecReturn = objExpressionSolver.parseFirstVector(sExpression);
				assertFalse(StringZZZ.isEmpty(vecReturn.get(1))); //in der 0ten Position ist der String vor der Encryption, in der 3ten Position ist der String nach der Encryption.
				
				
				//### Nun die Gesamtberechnung durchführen				
				IKernelConfigSectionEntryZZZ objEntry = objExpressionSolver.parseAsEntryNew(sExpression);
				sValue = objEntry.getValue();
				assertEquals("Ohne Aufloesung soll Ausgabe gleich Eingabe sein",sExpression, sValue);
			
				sValueAsExpression = objEntry.getValueFormulaSolvedAndConverted();
				assertNull("Ohne Aufloesung soll die Ausgabe des Expression Werts NULL sein", sValueAsExpression);				
				assertFalse("Ohne Aufloesung soll auch keine Expression vorliegen",objEntry.isExpression());
				
				//###########################################################
				//Anwenden der ersten Formel	
				objExpressionSolver.setFlag(IIniTagWithExpressionZZZ.FLAGZ.USEEXPRESSION, true); 
				objExpressionSolver.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER, true);
				objExpressionSolver.setFlag(IKernelCallIniSolverZZZ.FLAGZ.USECALL, true); //Damit der Wert sofort ausgerechnet wird				
				objExpressionSolver.setFlag(IKernelJavaCallIniSolverZZZ.FLAGZ.USECALL_JAVA, true); //Damit der Wert sofort ausgerechnet wird						
				IKernelConfigSectionEntryZZZ objEntry2 = objExpressionSolver.parseAsEntryNew(sExpression);
				sValue = objEntry2.getValue();				
				assertFalse("Mit Aufloesung soll Ausgabe anders als Eingabe sein.",sExpression.equals(sValue));
				System.out.println(ReflectCodeZZZ.getPositionCurrent() + "\tDebugausgabe1: '" + sValue + "'\n");
				
				//20230426: DAS IST VORERST DAS ZIEL, DAMIT IN DER FTPCREDENTIALS MASKE DER VERSCHLUESSELTE WERT AUCH ANGEZEIGT WERDEN KANN!!!
				bValue = objEntry2.isCall();
				System.out.println(ReflectCodeZZZ.getPositionCurrent() + "\tDebugausgabe2: '" + bValue + "'\n");
				assertTrue(bValue);
				
				bValue = objEntry2.isJavaCall();
				assertTrue(bValue);
				
				//ohne solve ist das alles NULL	
				sClassname = objEntry2.getCallingClassname();
				assertNull(sClassname);
				
				sMethodname = objEntry2.getCallingMethodname();
				assertNull(sMethodname);
				
				//TESTE DEN WERT:
				sValue = objEntry2.getValue();
				System.out.println(ReflectCodeZZZ.getPositionCurrent() + "\tDebugausgabe5: '" + sValue + "'\n");
				
				sExpressionSolved = sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT; //auch ohne Solver werden die Pfade substituiert!!!
				sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSolved, KernelCallIniSolverZZZ.sTAG_NAME);
				sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSolved, KernelJavaCallIniSolverZZZ.sTAG_NAME);
				sExpressionSolvedTagless = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ, false); //von aussen nach innen. So bleiben Z-Tags innen(z.B. um den Pfad herum) erhalten.
				
				//<Z:Class>basic.zBasic.util.machine.EnvironmentZZZ</Z:Class><Z:Method>getHostName</Z:Method>		
				assertEquals(sExpressionSolvedTagless,sValue);
				
				
				
				//##############################################
				//### SOLVE ####################################
				//##############################################
				sExpression = KernelCallIniSolverZZZTest.sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT;//Merke: Die Aufloesung von Formeln wird dann vom ExpressionSolver gemacht!!!
								
				IKernelConfigSectionEntryZZZ objEntry3 = objExpressionSolver.solveAsEntryNew(sExpression);
				sValue = objEntry3.getValue();				
				assertFalse("Mit Aufloesung soll Ausgabe anders als Eingabe sein.",sExpression.equals(sValue));
				System.out.println(ReflectCodeZZZ.getPositionCurrent() + "\tDebugausgabe1: '" + sValue + "'\n");
				
				//20230426: DAS IST VORERST DAS ZIEL, DAMIT IN DER FTPCREDENTIALS MASKE DER VERSCHLUESSELTE WERT AUCH ANGEZEIGT WERDEN KANN!!!
				bValue = objEntry3.isCall();
				System.out.println(ReflectCodeZZZ.getPositionCurrent() + "\tDebugausgabe2: '" + bValue + "'\n");
				assertTrue(bValue);
				
				bValue = objEntry3.isJavaCall();
				assertTrue(bValue);
								
				sClassname = objEntry3.getCallingClassname();
				System.out.println(ReflectCodeZZZ.getPositionCurrent() + "\tDebugausgabe3: '" + sClassname + "'\n");
				assertEquals("basic.zBasic.util.machine.EnvironmentZZZ",sClassname);
				
				sMethodname = objEntry3.getCallingMethodname();
				System.out.println(ReflectCodeZZZ.getPositionCurrent() + "\tDebugausgabe4: '" + sMethodname + "'\n");
				assertEquals("getHostName",sMethodname);
				
				//TESTE DEN WERT:
				sValue = objEntry3.getValue();
				System.out.println(ReflectCodeZZZ.getPositionCurrent() + "\tDebugausgabe5: '" + sValue + "'\n");
				sExpressionSolved = EnvironmentZZZ.getHostName();				
				assertEquals(sExpressionSolved,sValue);
				
				//++++++++++++++++++++++++++++++++++++++++++++++++++
			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			}
		}		
		
		/** void, Test: Reading an entry in a section of the ini-file
		* Lindhauer; 22.04.2006 12:54:32
		 */
		public void testCompute_CallWithPrePost(){
			String sExpressionSource=null;
//			try {
				sExpressionSource = KernelCallIniSolverZZZTest.sEXPRESSION_CALL01_DEFAULT;
				testCompute_CallWithPrePost_(sExpressionSource, "PRE", "POST");

//			} catch (ExceptionZZZ ez) {
//				fail("Method throws an exception." + ez.getMessageLast());
//			}

		}	
		
		/**void, Test: Reading an entry in a section of the ini-file
		 * 
		 * @author Fritz Lindhauer, 05.05.2023, 08:54:30
		 */
		private void testCompute_CallWithPrePost_(String sExpressionSourceIn, String sPreIn, String sPostIn){

			boolean btemp; int itemp;
			
			String sSection; String sProperty;
			String sExpressionSource; 
			String sExpressionSolved; String sExpressionSolvedTagless;
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
				
				//+++ Ohne Solver-Berechung		
				
				//b)
				sExpressionSource = sPre + sExpressionSourceIn + sPost;
				sExpressionSolved = sPre + sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT + sPost; //auch ohne Solver werden die Pfade substituiert!!!
				sExpressionSolvedTagless = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ, false);
				//Beim Parsen ohne Solver, bleibt sogar das Encryption-Tag drin, auch wenn sonst die Tags entfernt werden.
				btemp = testCompute_Call_SolverUnsolved_(sExpressionSource, sExpressionSolvedTagless, true, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);

			
				//+++++++ VORGEZOGENER LETZTER FEHLERTEST ENDE
				
				
				
				//###########################
			    //### objExpression
				//#########################
				
				//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
				//+++ Ohne jegliche Expression-Berechnung
				//a)
				sExpressionSource = sPre + sExpressionSourceIn + sPost;
				sExpressionSolved = sPre + sExpressionSourceIn + sPost;
				btemp = testCompute_Call_Unexpressed_(sExpressionSource, sExpressionSolved, false, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
				
				//b)
				sExpressionSource = sPre + sExpressionSourceIn + sPost;
				sExpressionSolved = sPre + sExpressionSourceIn + sPost;			
				btemp = testCompute_Call_Unexpressed_(sExpressionSource, sExpressionSolved, true, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
				
				//c)
				sExpressionSource = sPre + sExpressionSourceIn + sPost;
				sExpressionSolved = sPre + sExpressionSourceIn + sPost; 			
				btemp = testCompute_Call_Unexpressed_(sExpressionSource, sExpressionSolved, false, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
					
				//d)
				sExpressionSource = sPre + sExpressionSourceIn + sPost;
				sExpressionSolved = sPre + sExpressionSourceIn + sPost;
				btemp = testCompute_Call_Unexpressed_(sExpressionSource, sExpressionSolved, true, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
				
				
				//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
				//+++ Ohne Solver-Berechung		
				//a)
				sExpressionSource = sPre + sExpressionSourceIn + sPost;
				sExpressionSolved = sPre + sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT + sPost; //auch ohne Solver werden die Pfade substituiert!!!
				btemp = testCompute_Call_SolverUnsolved_(sExpressionSource, sExpressionSolved, false, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
				
				//b)
				sExpressionSource = sPre + sExpressionSourceIn + sPost;
				sExpressionSolved = sPre + sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT + sPost; //auch ohne Solver werden die Pfade substituiert!!!
				sExpressionSolvedTagless = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ, false);
				//Beim Parsen ohne Solver, bleibt sogar das Encryption-Tag drin, auch wenn sonst die Tags entfernt werden.
				btemp = testCompute_Call_SolverUnsolved_(sExpressionSource, sExpressionSolvedTagless, true, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);

				//c)
				sExpressionSource = sPre + sExpressionSourceIn + sPost;
				sExpressionSolved = sPre + sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT + sPost; //auch ohne Solver werden die Pfade substituiert!!!
				//Beim Solven ohne Solver, bleibt alles wie est ist.
				btemp = testCompute_Call_SolverUnsolved_(sExpressionSource, sExpressionSolved, false, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
				
				//d)
				sExpressionSource = sPre + sExpressionSourceIn + sPost;
				sExpressionSolved = sPre + sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT + sPost; //auch ohne Solver werden die Pfade substituiert!!!
				sExpressionSolvedTagless = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ, false);
				//Beim Solven ohne Solver, werden nur die äusseren Z-Tags ggfs. entfernt.
				btemp = testCompute_Call_SolverUnsolved_(sExpressionSource, sExpressionSolvedTagless, true, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
				
				//++++++++++++++++++++++++++++++++++++++++++++++++++
				//+++ Ohne Call-Berechung
				//a)
				sExpressionSource = sPre + sExpressionSourceIn + sPost;
				sExpressionSolved = sPre + sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT + sPost; //auch ohne Solver werden die Pfade substituiert!!!
				btemp = testCompute_Call_CallUnsolved_(sExpressionSource, sExpressionSolved, false, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
				
				//b)
				sExpressionSource = sPre + sExpressionSourceIn + sPost;
				sExpressionSolved = sPre + sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT + sPost; //auch ohne Solver werden die Pfade substituiert!!!
				//Halt kein Call - Tag mehr im Ergebnis Erwartet....   sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSolved, KernelCallIniSolverZZZ.sTAG_NAME);
				sExpressionSolvedTagless = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ, false);				
				//Beim Parsen ohne call, muss doch dieser call - Tag drinbleiben. Hier werden also nur die aeussern Z-Tags entfernt.
				btemp = testCompute_Call_CallUnsolved_(sExpressionSource, sExpressionSolvedTagless, true, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
				
				//c)
				sExpressionSource = sPre + sExpressionSourceIn + sPost;
				sExpressionSolved = sPre + sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT + sPost; //auch ohne Solver werden die Pfade substituiert!!!
				//Beim Solven ohne call, bleibt alles an Tags drin.
				btemp = testCompute_Call_CallUnsolved_(sExpressionSource, sExpressionSolved, false, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
			
				//d)
				sExpressionSource = sPre + sExpressionSourceIn + sPost;
				sExpressionSolved = sPre + sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT + sPost; //auch ohne Solver werden die Pfade substituiert!!!
				sExpressionSolvedTagless = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ, false);
				btemp = testCompute_Call_CallUnsolved_(sExpressionSource, sExpressionSolvedTagless, true, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
				
				//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
				//+++ Mit Call-Berechnung OHNE JavaCall-Berechnung
				//a)
				sExpressionSource = sPre + sExpressionSourceIn + sPost;
				sExpressionSolved = sPre + sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT + sPost; //auch ohne Solver werden die Pfade substituiert!!!				
				btemp = testCompute_Call_JavaCallUnsolved_(sExpressionSource, sExpressionSolved, false, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
				
				//b)
				sExpressionSource = sPre + sExpressionSourceIn + sPost;
				sExpressionSolved = sPre + sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT + sPost; //auch ohne Solver werden die Pfade substituiert!!!
				sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSolved, KernelCallIniSolverZZZ.sTAG_NAME);				
				sExpressionSolvedTagless = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ, false); //von aussen nach innen. So bleiben Z-Tags innen(z.B. um den Pfad herum) erhalten.
				//Werdem beim reinen Parsen die umgebenden Tags entfernt, dann wird auch das call-Tag entfernt. Das wird naemlich auch durch Parsen "aufgeloest". Das eigentliche Aufloesen findet aber nicht statt.
				btemp = testCompute_Call_JavaCallUnsolved_(sExpressionSource, sExpressionSolvedTagless, true, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
				
				//c)
				sExpressionSource = sPre + sExpressionSourceIn + sPost;
				sExpressionSolved = sPre + sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT + sPost; //auch ohne Solver werden die Pfade substituiert!!!
				
				//Konflikt: 
				//Normalfall: Aufloesen mit solve -> z:call, z:JavaCall weg, etc.
				//      ABER: bRemoveSurrounding ist false gesetzt!!! -> D.h. Tags bleiben drin.
				
				//Also: z:Call soll aus dem Ergebnis weg sein, wg. Aufloesen!!! Auch wenn die umgebenden Z-Tags drin bleiben.
				//      Weil JavaCall in diesem Test nicht verwendet wird, bleibt dieser drin.
				sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSolved, KernelCallIniSolverZZZ.sTAG_NAME);
				btemp = testCompute_Call_JavaCallUnsolved_(sExpressionSource, sExpressionSolved, false, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
				
				//d)
				sExpressionSource = sPre + sExpressionSourceIn + sPost;
				sExpressionSolved = sPre + sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT + sPost; //auch ohne Solver werden die Pfade substituiert!!!
				sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSolved, KernelCallIniSolverZZZ.sTAG_NAME);		
				sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
				btemp = testCompute_Call_JavaCallUnsolved_(sExpressionSource, sExpressionSolved, true, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
		
				
				//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
				//+++ Mit Call-Berechnung UND JavaCall-Berechnung
				//a)
				sExpressionSource = sPre + sExpressionSourceIn + sPost;
				sExpressionSolved = sPre + sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT + sPost; //auch ohne Solver werden die Pfade substituiert!!!				
				btemp = testCompute_Call_(sExpressionSource, sExpressionSolved, false, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
				
				//b)
				sExpressionSource = sPre + sExpressionSourceIn + sPost;
				sExpressionSolved = sPre + sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT + sPost; //auch ohne Solver werden die Pfade substituiert!!!
				sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSolved, KernelCallIniSolverZZZ.sTAG_NAME);
				sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSolved, KernelJavaCallIniSolverZZZ.sTAG_NAME);
				sExpressionSolvedTagless = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ, false); //von aussen nach innen. So bleiben Z-Tags innen(z.B. um den Pfad herum) erhalten.
				//Werdem beim reinen Parsen die umgebenden Tags entfernt, dann wird auch das call-Tag entfernt. Das wird naemlich auch durch Parsen "aufgeloest". Das eigentliche Aufloesen findet aber nicht statt.
				btemp = testCompute_Call_(sExpressionSource, sExpressionSolvedTagless, true, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
				
				//c)
				sExpressionSource = sPre + sExpressionSourceIn + sPost;
				sExpressionSolved = sPre + sTagStartZ + sHostName + sTagEndZ + sPost; 
				//Wichtig: z:Call und z:Java sollen aus dem Ergebnis weg sein, wg. Aufloesen!!! Auch wenn die umgebenden Z-Tags drin bleiben.
				//Merke: Das geht aber bei uebergebenen Pfaden nur, wenn sie auch aufgeloest werden und einen sinnvolle Klasse und Methode zurueckkommt.
				btemp = testCompute_Call_(sExpressionSource, sExpressionSolved, false, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
				
				//d)
				sExpressionSource = sPre + sExpressionSourceIn + sPost;
				sExpressionSolved = sPre + sHostName +sPost;			
				btemp = testCompute_Call_(sExpressionSource, sExpressionSolved, true, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
								
			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			}		
		}
		
	}//END class
		

