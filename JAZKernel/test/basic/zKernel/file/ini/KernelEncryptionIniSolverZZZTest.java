package basic.zKernel.file.ini;

import java.io.File;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IObjectWithExpressionZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedTestCaseZZZ;
import basic.zBasic.util.datatype.calling.ReferenceZZZ;
import basic.zKernel.IKernelConfigSectionEntryZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernel.KernelZZZ;
import basic.zKernel.config.KernelConfigSectionEntryUtilZZZ;
import custom.zKernel.file.ini.FileIniZZZ;
import junit.framework.TestCase;

public class KernelEncryptionIniSolverZZZTest extends TestCase {
	protected final static String sEXPRESSION_ENCRYPTION01_DEFAULT = "<Z><Z:Encrypted><Z:Cipher>ROT13</Z:Cipher><Z:Code>nopqr</Z:Code></Z:Encrypted></Z>";//= "abcde";	
	protected final static String sEXPRESSION_ENCRYPTION02_DEFAULT = "<Z><Z:Encrypted><Z:Cipher>ROTnumeric</Z:Cipher><z:KeyNumber>5</z:KeyNumber><Z:FlagControl>USENUMERIC</Z:FlagControl><Z:Code>fghij</Z:Code></Z:Encrypted></Z>";
	protected final static String sEXPRESSION_ENCRYPTION03_DEFAULT = "<Z><Z:Encrypted><Z:Cipher>ROTnn</Z:Cipher><z:KeyNumber>5</z:KeyNumber><z:CharacterPool> abcdefghijklmnopqrstuvwxyz?!</z:CharacterPool><z:FlagControl>USEUPPERCASE</Z:FlagControl><Z:Code>fghij</Z:Code></Z:Encrypted></Z>";
	//TODOGOON20220929; //siehe auskommentierten testCompute04: Mache eine richtige Entschlüsselung mit AES UND Danach noch testCompute05 mit MD5
	
	private File objFile;
	private IKernelZZZ objKernel;
	private FileIniZZZ objFileIniTest=null;
	
	/// +++ Die eigentlichen Test-Objekte	
	private KernelEncryptionIniSolverZZZ objExpressionSolver;
	private KernelEncryptionIniSolverZZZ objExpressionSolverInit;
	
	

	protected void setUp(){
		try {			
						
			objKernel = new KernelZZZ("FGL", "01", "test", "ZKernelConfigKernel_test.ini",(String)null);
			
			//#### Ein init TestObjekt
			String[] saFlagInit = {"init"};
			objExpressionSolverInit = new KernelEncryptionIniSolverZZZ(objKernel, saFlagInit);
			
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
			objExpressionSolver = new KernelEncryptionIniSolverZZZ(objKernel, objFileIniTest, saFlag);
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
		assertTrue(objExpressionSolverInit.getFlag("init")==true);
		assertFalse(objExpressionSolver.getFlag("init")==true); //Nun wäre init falsch
		
		btemp = objExpressionSolver.setFlag(IKernelEncryptionIniSolverZZZ.FLAGZ.USEENCRYPTION, false); //Ansonsten wird der Wert sofort ausgerechnet
		assertTrue("Das Flag '" + IKernelEncryptionIniSolverZZZ.FLAGZ.USEENCRYPTION +"' sollte zur Verfügung stehen.", btemp);
		
		//Dummy test
		btemp = objExpressionSolver.setFlag("gibtEsNicht", false); //Ansonsten wird der Wert sofort ausgerechnet
		assertFalse("Das Flag 'gibtEsNicht' sollte nicht zur Verfügung stehen.", btemp);
		
		//Flags, die es zwar gibt, aber nicht in der encryption!!!
		btemp = objExpressionSolver.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA, true); //soll dann egal sein
		assertFalse("Flag unerwartet vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA + "'", btemp);
		
		btemp = objExpressionSolver.setFlag(IKernelCallIniSolverZZZ.FLAGZ.USECALL, true); //soll dann egal sein
		assertFalse("Flag unerwartet vorhanden '" + IKernelCallIniSolverZZZ.FLAGZ.USECALL + "'", btemp);
		
		btemp = objExpressionSolver.setFlag(IKernelJavaCallIniSolverZZZ.FLAGZ.USECALL_JAVA, true);
		assertFalse("Flag unerwartet vorhanden '" + IKernelJavaCallIniSolverZZZ.FLAGZ.USECALL_JAVA + "'", btemp);
		
		//+++++++++++++++++
		btemp = objExpressionSolver.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION, false); 
		assertTrue("Flag nicht vorhanden '" + IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);

		btemp = objExpressionSolver.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH, true);			
		assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
	
		btemp = objExpressionSolver.setFlag(IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE, true);			
		assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE + "'", btemp);
					
		btemp = objExpressionSolver.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER, true); //soll dann egal sein
		assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
		
		btemp = objExpressionSolver.setFlag(IKernelEncryptionIniSolverZZZ.FLAGZ.USEENCRYPTION, true); //soll dann egal sein
		assertTrue("Flag nicht vorhanden '" + IKernelEncryptionIniSolverZZZ.FLAGZ.USEENCRYPTION + "'", btemp);
		
		
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}
	
	public void testCompute_Enrcyption() {
		String sExpressionSource=null;
//		try {
			sExpressionSource = KernelEncryptionIniSolverZZZTest.sEXPRESSION_ENCRYPTION01_DEFAULT;
			testCompute_Encryption_(sExpressionSource);
			
			sExpressionSource = KernelEncryptionIniSolverZZZTest.sEXPRESSION_ENCRYPTION02_DEFAULT;
			testCompute_Encryption_(sExpressionSource);
			
			sExpressionSource = KernelEncryptionIniSolverZZZTest.sEXPRESSION_ENCRYPTION03_DEFAULT;
			testCompute_Encryption_(sExpressionSource);
//		} catch (ExceptionZZZ ez) {
//			fail("Method throws an exception." + ez.getMessageLast());
//		}
	}
	
	/**void, Test: Reading an entry in a section of the ini-file
	 * 
	 * @author Fritz Lindhauer, 05.05.2023, 08:54:30
	 */
	private void testCompute_Encryption_(String sExpressionSourceIn){
		
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
			
			//!!!!!!!!!!! BERECHTIGTES PROBLEM Z-Tag aussen sollte nicht mehr da sein...
			
			//+++ Mit Encryption-Berechnung
						
			//c)
			sExpressionSource = sExpressionSourceIn;
			sExpressionSolved = sTagStartZ + "abcde" + sTagEndZ; 
			//Wichtig: z:Encrypted soll aus dem Ergebnis weg sein, wg. Aufloesen!!! Auch wenn die umgebenden Z-Tags drin bleiben.   //"<Z><Z:Cipher>ROT13</Z:Cipher><Z:Code>nopqr</Z:Code></Z>";
			btemp = testCompute_Encryption_(sExpressionSource, sExpressionSolved, false, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);

			
			//+++++++ VORGEZOGENER LETZTER FEHLERTEST ENDE
			
			
			
			
			//###########################
		    //### objExpression
			//#########################
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//+++ Ohne jegliche Expression-Berechnung
			//a)
			sExpressionSource = sExpressionSourceIn;
			sExpressionSolved = sExpressionSource;
			btemp = testCompute_Encryption_Unexpressed_(sExpressionSource, sExpressionSolved, false, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
			
			//b)
			sExpressionSource = sExpressionSourceIn;
			sExpressionSolved = sExpressionSource;			
			btemp = testCompute_Encryption_Unexpressed_(sExpressionSource, sExpressionSolved, true, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
			
			//c)
			sExpressionSource = sExpressionSourceIn;
			sExpressionSolved = sExpressionSource; 			
			btemp = testCompute_Encryption_Unexpressed_(sExpressionSource, sExpressionSolved, false, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
				
			//d)
			sExpressionSource = sExpressionSourceIn;
			sExpressionSolved = sExpressionSource;
			btemp = testCompute_Encryption_Unexpressed_(sExpressionSource, sExpressionSolved, true, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
			
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//+++ Ohne Solver-Berechung
			//a)
			sExpressionSource = sExpressionSourceIn;
			sExpressionSolved = sExpressionSource;
			btemp = testCompute_Encryption_Unsolved_(sExpressionSource, sExpressionSolved, false, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
			
			//b)
			sExpressionSource = sExpressionSourceIn;
			sExpressionSolved = sExpressionSource;
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ, false);
			//Beim Parsen ohne Solver, bleibt sogar das Encryption-Tag drin, auch wenn sonst die Tags entfernt werden.
			btemp = testCompute_Encryption_Unsolved_(sExpressionSource, sExpressionSolved, true, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);

			//c)
			sExpressionSource = sExpressionSourceIn;
			sExpressionSolved = sExpressionSource;
			//Beim Solven ohne Solver, bleibt alles wie est ist.
			btemp = testCompute_Encryption_Unsolved_(sExpressionSource, sExpressionSolved, false, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
			
			//d)
			sExpressionSource = sExpressionSourceIn;
			sExpressionSolved = sExpressionSource;
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ, false);
			//Beim Solven ohne Solver, werden nur die äusseren Z-Tags ggfs. entfernt.
			btemp = testCompute_Encryption_Unsolved_(sExpressionSource, sExpressionSolved, true, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
			
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//+++ Ohne Encryption-Solver-Berechung
			//a)
			sExpressionSource = sExpressionSourceIn;
			sExpressionSolved = sExpressionSource;
			btemp = testCompute_Encryption_EncryptionUnsolved_(sExpressionSource, sExpressionSolved, false, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
			
			//b)
			sExpressionSource = sExpressionSourceIn;
			sExpressionSolved = sExpressionSource; 
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ, false);
			//Beim Parsen ohne encryption, muss doch dieser encryption - Tag drinbleiben. Hier werden also nur die aeussern Z-Tags entfernt.
			btemp = testCompute_Encryption_EncryptionUnsolved_(sExpressionSource, sExpressionSolved, true, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
			
			//c)
			sExpressionSource = sExpressionSourceIn;
			sExpressionSolved = sExpressionSource;
			//Beim Solven ohne encryption, bleibt alles an Tags drin.
			btemp = testCompute_Encryption_EncryptionUnsolved_(sExpressionSource, sExpressionSolved, false, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
		
			//d)
			sExpressionSource = sExpressionSourceIn;
			sExpressionSolved = sExpressionSource;	
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ, false);
			//Beim Solven ohne encryption muss dieser encryption - Tag drinbleiben
			btemp = testCompute_Encryption_EncryptionUnsolved_(sExpressionSource, sExpressionSolved, true, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
			
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//+++ Mit Encryption-Berechnung
			//a) nur parsen bringt keinen Unterschied, wenn die Tags drinbleiben sollen
			sExpressionSource = sExpressionSourceIn;
			sExpressionSolved = sExpressionSourceIn; 
			//false: d.h. Tags sollen drin bleiben sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ, false);
			btemp = testCompute_Encryption_(sExpressionSource, sExpressionSolved, false, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);

			//b) Werdem beim reinen Parsen die umgebenden Tags entfernt, dann wird auch das Encryption-Tag entfernt. Das wird naemlich auch durch Parsen "aufgeloest". Das eigentliche Aufloesen findet aber nicht statt.
			sExpressionSource = sExpressionSourceIn;
			sExpressionSolved = sExpressionSource;			
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, objExpressionSolver.getName(), false);
			btemp = testCompute_Encryption_(sExpressionSource, sExpressionSolved, true, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
			
			//c)
			sExpressionSource = sExpressionSourceIn;
			sExpressionSolved = sTagStartZ + "abcde" + sTagEndZ; 
			//Wichtig: z:Encrypted soll aus dem Ergebnis weg sein, wg. Aufloesen!!! Auch wenn die umgebenden Z-Tags drin bleiben.   //"<Z><Z:Cipher>ROT13</Z:Cipher><Z:Code>nopqr</Z:Code></Z>";
			btemp = testCompute_Encryption_(sExpressionSource, sExpressionSolved, false, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
			
			//d)
			sExpressionSource = sExpressionSourceIn;
			sExpressionSolved = "abcde";//"<Z><Z:Cipher>ROT13</Z:Cipher><Z:Code>nopqr</Z:Code></Z>";			
			btemp = testCompute_Encryption_(sExpressionSource, sExpressionSolved, true, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
							
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}		
	}
	
	
	
	private boolean testCompute_Encryption_Unexpressed_(String sExpressionSourceIn, String sExpressionSolvedIn, boolean bRemoveSurroundingSeparatorsOnSolve, IEnumSetMappedTestCaseZZZ objEnumTestCase) {
		boolean bReturn = false;
		try {
			boolean btemp; 
			
			String sExpression; 
			String sExpressionSolved; String sValue; String sValueUsed;
			ReferenceZZZ<IKernelConfigSectionEntryZZZ> objSectionEntryReference;
			IKernelConfigSectionEntryZZZ objEntry; IKernelConfigSectionEntryZZZ objEntryUsed;
			
			
			//####################################################################################
			//### EXPRESSION - NICHT EXPRESSION BEHANDLUNG .solve
			//####################################################################################
			
			//Anwenden der ersten Formel, ohne Berechnung oder Formelersetzung						
			sExpression = sExpressionSourceIn;
			sExpressionSolved = sExpressionSolvedIn;
			
			btemp = objExpressionSolver.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION, false); 
			assertTrue("Flag nicht vorhanden '" + IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);

			btemp = objExpressionSolver.setFlag(IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER,true); //ohne aktivierten Parser keine SUBSTITUTION  
			assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER + "'", btemp);
			
			btemp = objExpressionSolver.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH, true);			
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
		
			btemp = objExpressionSolver.setFlag(IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE, true);			
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE + "'", btemp);
						
			btemp = objExpressionSolver.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER, true); //soll dann egal sein
			assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
			
			btemp = objExpressionSolver.setFlag(IKernelEncryptionIniSolverZZZ.FLAGZ.USEENCRYPTION, true); //soll dann egal sein
			assertTrue("Flag nicht vorhanden '" + IKernelEncryptionIniSolverZZZ.FLAGZ.USEENCRYPTION + "'", btemp);
		
			//+++ ... parse ist nicht solve... also wird hier nichts aufgeloest, aussser die Pfade substituiert.
			if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.PARSE)) {
				sExpression = sExpressionSourceIn;
				sExpressionSolved = sExpressionSolvedIn;		
				objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
				sValue = objExpressionSolver.parse(sExpression, objSectionEntryReference, bRemoveSurroundingSeparatorsOnSolve);
				assertEquals(sExpressionSolved, sValue);
				
				objEntry = objSectionEntryReference.get();
				assertNotNull(objEntry);
				
				assertTrue(objEntry.isParseCalled()); //Der Parse-Schritt wurde gemacht.
				
				sExpressionSolved = sExpressionSolvedIn;
				if(sExpression.equals(sExpressionSolved)) {
					assertFalse(objEntry.isParsedChanged());						
				}else {
					assertTrue(objEntry.isParsedChanged());
				}
			
				assertFalse(objEntry.isPathSubstituted());
				assertFalse(objEntry.isVariableSubstituted());
				
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
				sExpression = sExpressionSourceIn;
				sExpressionSolved = sExpressionSolvedIn;
				objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
				sValue = objExpressionSolver.solve(sExpression, objSectionEntryReference, bRemoveSurroundingSeparatorsOnSolve);
				assertEquals(sExpressionSolved, sValue);
				
				objEntry = objSectionEntryReference.get();
				assertNotNull(objEntry);
				
				assertFalse(objEntry.isParseCalled()); //Der Parse-Schritt wurde NICHT gemacht.
				assertFalse(objEntry.isParsedChanged()); //es wird ja nix gemacht, also "unveraendert"
									
				assertFalse(objEntry.isPathSubstituted());
				assertFalse(objEntry.isVariableSubstituted());
								
				assertTrue(objEntry.isSolveCalled()); //Der Solve-Schritt wurde gemacht.
				assertFalse(objEntry.isSolvedChanged()); //es wird ja nix gemacht, also "unveraendert" 
								
				assertFalse(objEntry.isDecrypted());
				assertNull(objEntry.getValueDecrypted()); //Merke: sValue kann unterschiedlich zu dem decrypted Wert sein. Wenn etwas drumherum steht.
								
				assertFalse(objEntry.isCall());
				assertFalse(objEntry.isJavaCall());
				assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingClassname() + "'", objEntry.getCallingClassname());
				assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingMethodname() + "'", objEntry.getCallingMethodname());
			}
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			
			//+++ Variante fuer den AsEntry-Test
			if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.PARSE_AS_ENTRY)) {
				sExpression = sExpressionSourceIn;
				sExpressionSolved = sExpressionSolvedIn;
				objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
				objEntryUsed = objExpressionSolver.parseAsEntry(sExpression, bRemoveSurroundingSeparatorsOnSolve);				
				assertNotNull(objEntryUsed);
							
				assertTrue(objEntryUsed.isParseCalled()); //Der Parse-Schritt wurde gemacht.
				assertFalse(objEntryUsed.isParsedChanged()); //es wird ja nix gemacht, also immer "unveraendert"
				
				assertFalse(objEntryUsed.isPathSubstituted());
				assertFalse(objEntryUsed.isVariableSubstituted());
				
				assertFalse(objEntryUsed.isSolveCalled()); //Der Solve-Schritt wurde gemacht.
				assertFalse(objEntryUsed.isSolvedChanged()); //es wird ja nix gemacht, also "unveraendert" 
								
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
				sValue = objExpressionSolver.solve(sExpression, objSectionEntryReference, bRemoveSurroundingSeparatorsOnSolve);
				assertEquals(sExpressionSolved, sValue);
				
				objEntryUsed = objSectionEntryReference.get();
				assertNotNull(objEntryUsed);
				
				assertFalse(objEntryUsed.isParseCalled()); //Der Parse-Schritt wurde NICHT gemacht.
				assertFalse(objEntryUsed.isParsedChanged()); //es wird ja nix gemacht, also immer "unveraendert"
				
				assertFalse(objEntryUsed.isPathSubstituted());
				assertFalse(objEntryUsed.isVariableSubstituted());

				assertTrue(objEntryUsed.isSolveCalled()); //Der Solve-Schritt wurde gemacht.
				assertFalse(objEntryUsed.isSolvedChanged()); //es wird ja nix gemacht, also "unveraendert" 
								
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
	
	private boolean testCompute_Encryption_Unsolved_(String sExpressionSourceIn, String sExpressionSolvedIn, boolean bRemoveSurroundingSeparators, IEnumSetMappedTestCaseZZZ objEnumTestCase) {
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
			//### EXPRESSION - CALL NICHT .solve
			//####################################################################################
						
			//Anwenden des Ausdrucks ohne Solver - Aufruf
			btemp = objExpressionSolver.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION, true); 
			assertTrue("Flag nicht vorhanden '" + IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
			
			btemp = objExpressionSolver.setFlag(IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER,true); //ohne aktivierten Parser keine SUBSTITUTION  
			assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER + "'", btemp);
			
			btemp = objExpressionSolver.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH, true);			
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
		
			btemp = objExpressionSolver.setFlag(IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE, true);			
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE + "'", btemp);
			
			btemp = objExpressionSolver.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER, false); 
			assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
			
			btemp = objExpressionSolver.setFlag(IKernelEncryptionIniSolverZZZ.FLAGZ.USEENCRYPTION, true); //sollte dann egal sein
			assertTrue("Flag nicht vorhanden '" + IKernelEncryptionIniSolverZZZ.FLAGZ.USEENCRYPTION + "'", btemp);
			
						
			//+++ ... parse ist nicht solve... also wird hier nichts aufgeloest, aussser die Pfade
			if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.PARSE)) {
				sExpression = sExpressionSourceIn;
				sExpressionSolved = sExpressionSolvedIn;		
				objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
				sValue = objExpressionSolver.parse(sExpression, objSectionEntryReference, bRemoveSurroundingSeparators);
				assertEquals(sExpressionSolved, sValue);
				
				sExpressionSolved = sExpressionSolvedIn; //der Wert des Tags selbst unterscheidet sich immer vom Wert der Zeile
				sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ, false);
				sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, objExpressionSolver.getName(), false);								
				sValue = objExpressionSolver.getValue();
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
				assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingClassname() + "'", objEntry.getCallingClassname());
				assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingMethodname() + "'", objEntry.getCallingMethodname());
			}
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
				
			//+++ ... solve verhält sich NICHT wie parse(), bei solve wird aufgeloest...
			if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.SOLVE)) {
				sExpression = sExpressionSourceIn;
				sExpressionSolved = sExpressionSolvedIn;
				objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
				sValue = objExpressionSolver.solve(sExpression, objSectionEntryReference, bRemoveSurroundingSeparators);
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
				assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingClassname() + "'", objEntry.getCallingClassname());
				assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingMethodname() + "'", objEntry.getCallingMethodname());
			}
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			
			//+++ Variante fuer den AsEntry-Test
			if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.PARSE_AS_ENTRY)) {
				sExpression = sExpressionSourceIn;
				sExpressionSolved = sExpressionSolvedIn;
				objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
				objEntryUsed = objExpressionSolver.parseAsEntry(sExpression, bRemoveSurroundingSeparators);
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
				objEntry = objExpressionSolver.solveAsEntry(sExpression, objSectionEntryReference, bRemoveSurroundingSeparators);
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
	
	private boolean testCompute_Encryption_(String sExpressionSourceIn, String sExpressionSolvedIn, boolean bRemoveSurroundingSeparatorsOnSolve, IEnumSetMappedTestCaseZZZ objEnumTestCase) {
		boolean bReturn = false;
		try {
			boolean btemp; 
			
			String sExpression; String sExpressionSolved; String sValue; String sValueUsed;				
			ReferenceZZZ<IKernelConfigSectionEntryZZZ> objSectionEntryReference;
			IKernelConfigSectionEntryZZZ objEntry; IKernelConfigSectionEntryZZZ objEntryUsed;
			
			String sTagStartZ = "<Z>";
			String sTagEndZ = "</Z>";
			
			
			//####################################################################################
			//### EXPRESSION .solve
			//####################################################################################
			
			//Anwenden der ersten Formel, ohne Berechnung oder Formelersetzung						
			sExpression = sExpressionSourceIn;
			sExpressionSolved = sExpressionSolvedIn;
			
			btemp = objExpressionSolver.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION, true); 
			assertTrue("Flag nicht vorhanden '" + IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);

			btemp = objExpressionSolver.setFlag(IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER,true); //ohne aktivierten Parser keine SUBSTITUTION  
			assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER + "'", btemp);
			
			btemp = objExpressionSolver.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH, true);			
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
		
			btemp = objExpressionSolver.setFlag(IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE, true);			
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE + "'", btemp);
									
			btemp = objExpressionSolver.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER, true); 
			assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
			
			btemp = objExpressionSolver.setFlag(IKernelEncryptionIniSolverZZZ.FLAGZ.USEENCRYPTION, true); 
			assertTrue("Flag nicht vorhanden '" + IKernelEncryptionIniSolverZZZ.FLAGZ.USEENCRYPTION + "'", btemp);
			
				
			//+++ ... parse ist nicht solve... also wird hier nichts aufgeloest, aussser die Pfade
			if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.PARSE)) {
				sExpression = sExpressionSourceIn;
				sExpressionSolved = sExpressionSolvedIn;				
				objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
				sValue = objExpressionSolver.parse(sExpression, objSectionEntryReference, bRemoveSurroundingSeparatorsOnSolve);
				assertEquals(sExpressionSolved, sValue);

				//Der Wert des Tags unterscheidet sich vom Wert der gesamt geparsten Zeile
				sExpressionSolved = sExpressionSolvedIn;	//Der Wert selbst hat dann keine Tags drumherum.			
				sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ, false);
				sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, objExpressionSolver.getName(), false);				
				sValue = objExpressionSolver.getValue();					
				assertEquals(sExpressionSolved, sValue);
				
				objEntry = objSectionEntryReference.get();
				assertNotNull(objEntry);

				sExpressionSolved = sExpressionSolvedIn;
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
				sExpression = sExpressionSourceIn;
				sExpressionSolved = sExpressionSolvedIn;
				objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
				sValue = objExpressionSolver.solve(sExpression, objSectionEntryReference, bRemoveSurroundingSeparatorsOnSolve);
				assertEquals(sExpressionSolved, sValue);
				
				objEntry = objSectionEntryReference.get();
				assertNotNull(objEntry);
				
				//Der Wert des Tags unterscheidet sich vom Wert der gesamt geparsten Zeile
				sExpressionSolved = sExpressionSolvedIn;	//Der Wert selbst hat dann keine Tags drumherum.			
				sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ, false);
				sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, objExpressionSolver.getName(), false);				
				sValue = objExpressionSolver.getValue();					
				assertEquals(sExpressionSolved, sValue);
				
				
				assertTrue(objEntry.isParseCalled());
				if(bRemoveSurroundingSeparatorsOnSolve) {
					assertTrue(objEntry.isParsedChanged());
				}else {
					assertFalse(objEntry.isParsedChanged());						
				}
				
				assertTrue(objEntry.isSolveCalled()); //Der Solve-Schritt wurde gemacht.
				assertTrue(objEntry.isSolvedChanged());  
				
				assertTrue(objEntry.isDecrypted());
				assertNotNull(objEntry.getValueDecrypted()); //Merke: sValue kann unterschiedlich zu dem decrypted Wert sein. Wenn etwas drumherum steht. 
								
				assertFalse(objEntry.isCall());
				assertFalse(objEntry.isJavaCall());
				assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingClassname() + "'", objEntry.getCallingClassname());
				assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingMethodname() + "'", objEntry.getCallingMethodname());
			}
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		
			//+++ Variante fuer den AsEntry-Test
			if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.PARSE_AS_ENTRY)) {
				sExpression = sExpressionSourceIn;
				sExpressionSolved = sExpressionSolvedIn;
				objEntryUsed = objExpressionSolver.parseAsEntry(sExpression, bRemoveSurroundingSeparatorsOnSolve);				
				assertNotNull(objEntryUsed);
												
				sValue = objExpressionSolver.getValue();				
				assertEquals(sExpressionSolved, sValue);

				assertTrue(objEntryUsed.isParseCalled());
				if(bRemoveSurroundingSeparatorsOnSolve) {
					assertTrue(objEntryUsed.isParsedChanged());
				}else {
					assertFalse(objEntryUsed.isParsedChanged());						
				}
				
				assertFalse(objEntryUsed.isSolveCalled()); //Der Solve-Schritt wurde gemacht.
				assertFalse(objEntryUsed.isSolvedChanged()); //es wird ja nix gemacht, also "unveraendert" 
			
			
				assertFalse(objEntryUsed.isDecrypted()); //Ist kein solve-Schritt involviert.
				assertNull(objEntryUsed.getValueDecrypted());  				
				
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
				objEntryUsed = objExpressionSolver.solveAsEntry(sExpression, objSectionEntryReference, bRemoveSurroundingSeparatorsOnSolve);				
				assertNotNull(objEntryUsed);
				
				//Der Wert des Tags unterscheidet sich vom Wert der gesamt geparsten Zeile
				sValue = objExpressionSolver.getValue();
				assertEquals(sExpressionSolved, sValue);
				
				assertTrue(objEntryUsed.isParseCalled());				
				if(bRemoveSurroundingSeparatorsOnSolve) {
					assertTrue(objEntryUsed.isParsedChanged());
				}else {
					assertFalse(objEntryUsed.isParsedChanged());						
				}
				
				assertTrue(objEntryUsed.isSolveCalled()); //Der Solve-Schritt wurde gemacht.
				assertTrue(objEntryUsed.isSolvedChanged()); //es wird ja nix gemacht, also "unveraendert" 
			
			
				assertTrue(objEntryUsed.isDecrypted()); //Ist ja ein solve-Schritt involviert und es wurde aufgeloest.
				assertNotNull(objEntryUsed.getValueDecrypted());  				
				
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
		
	
	
	
	
	
	
	private boolean testCompute_Encryption_EncryptionUnsolved_(String sExpressionSourceIn, String sExpressionSolvedIn, boolean bRemoveSurroundingSeparatorsOnSolve, IEnumSetMappedTestCaseZZZ objEnumTestCase) {
		boolean bReturn = false;
		
		try {
			boolean btemp; 
			
			String sExpression; 
			String sExpressionSolved; String sValue; String sValueUsed;			
			ReferenceZZZ<IKernelConfigSectionEntryZZZ> objSectionEntryReference;
			IKernelConfigSectionEntryZZZ objEntry; IKernelConfigSectionEntryZZZ objEntryUsed;  
					
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++		
			//Nur Expression ausrechnen, ist aber unverändert vom reinen Ergebnis her.		
			btemp = objExpressionSolver.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION, true); 
			assertTrue("Flag nicht vorhanden '" + IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);

			btemp = objExpressionSolver.setFlag(IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER,true); //ohne aktivierten Parser keine SUBSTITUTION  
			assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER + "'", btemp);
			
			btemp = objExpressionSolver.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH, true);			
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
		
			btemp = objExpressionSolver.setFlag(IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE, true);			
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE + "'", btemp);
							
			btemp = objExpressionSolver.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER, true);
			assertTrue("Das Flag '"+IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER+"' sollte zur Verfügung stehen.", btemp);
			
			btemp = objExpressionSolver.setFlag(IKernelEncryptionIniSolverZZZ.FLAGZ.USEENCRYPTION, false);
			assertTrue("Flag nicht vorhanden '" + IKernelEncryptionIniSolverZZZ.FLAGZ.USEENCRYPTION + "'", btemp);
		
			
			//+++ ... parse ist nicht solve... also wird hier nichts aufgeloest, aussser die Pfade
			if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.PARSE)) {
				sExpression = sExpressionSourceIn;
				sExpressionSolved = sExpressionSolvedIn;		
				objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
				sValue = objExpressionSolver.parse(sExpression, objSectionEntryReference, bRemoveSurroundingSeparatorsOnSolve);
				assertEquals(sExpressionSolved, sValue);
				
				objEntry = objSectionEntryReference.get();
				assertNotNull(objEntry);
				
				//spannend, nicht aufgeloest, aber geparsed!!!
				//allerdings gibt es nur einen Unterschied, wenn die umgebenden Tags entfernt wurden!!!
//				if(bRemoveSurroundingSeparatorsOnSolve) {
//					assertTrue(objEntry.isParsed());  
//				}else {
//					assertFalse(objEntry.isParsed());
//				}
				
				//Falls Pfade substituiert werden, gibt es ebenfalls einen Unterschied, der fuer isParsedChanged sorgt.
//				if(bRemoveSurroundingSeparators | objEntry.isPathSubstituted()) {
//					assertTrue(objEntry.isParsed());
//				}else {
//					assertFalse(objEntry.isParsed());
//				}
				assertTrue(objEntry.isParseCalled());
				if(sExpression.equals(sExpressionSolved)) {
					assertFalse(objEntry.isParsedChanged());						
				}else {
					assertTrue(objEntry.isParsedChanged());
				}
				
				assertFalse(objEntry.isSolveCalled()); //Der konkrete Solver ist nicht involviert
				
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
				sExpression = sExpressionSourceIn;
				sExpressionSolved = sExpressionSolvedIn;
				objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
				sValue = objExpressionSolver.solve(sExpression, objSectionEntryReference, bRemoveSurroundingSeparatorsOnSolve);
				assertEquals(sExpressionSolved, sValue);

				objEntry = objSectionEntryReference.get();
				assertNotNull(objEntry);
				
				assertTrue(objEntry.isParseCalled()); //Der parse Schritt wird ausgefuehrt.				
				if(sExpression.equals(sExpressionSolved)) {
					assertFalse(objEntry.isParsedChanged());						
				}else {
					assertTrue(objEntry.isParsedChanged());
				}
				
				assertFalse(objEntry.isPathSubstituted());
				assertFalse(objEntry.isVariableSubstituted());
								
				assertTrue(objEntry.isSolveCalled()); //der solve Schritt wird ausgefuehrt
				assertFalse(objEntry.isSolvedChanged()); //der Solver ist ja abgestellt
				
				assertFalse(objEntry.isDecrypted());
				assertNull(objEntry.getValueDecrypted()); //Merke: sValue kann unterschiedlich zu dem decrypted Wert sein. Wenn etwas drumherum steht.
				
				assertFalse(objEntry.isCall());
				assertFalse(objEntry.isJavaCall());
				assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingClassname() + "'", objEntry.getCallingClassname());
				assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingMethodname() + "'", objEntry.getCallingMethodname());
			}
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			
			//+++ Variante fuer den AsEntry-Test
			if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.PARSE_AS_ENTRY)) {
				sExpression = sExpressionSourceIn;
				sExpressionSolved = sExpressionSolvedIn;
				objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
				objEntryUsed = objExpressionSolver.parseAsEntry(sExpression, bRemoveSurroundingSeparatorsOnSolve);				
				assertNotNull(objEntryUsed);
				sValueUsed = objEntryUsed.getValue();
				assertEquals(sExpressionSolved, sValueUsed);
				
				assertTrue(objEntryUsed.isParseCalled());
				if(sExpression.equals(sExpressionSolved)) {
					assertFalse(objEntryUsed.isParsedChanged());						
				}else {
					assertTrue(objEntryUsed.isParsedChanged());
				}
				
				assertFalse(objEntryUsed.isSolveCalled());
				assertFalse(objEntryUsed.isSolvedChanged());
				
				assertFalse(objEntryUsed.isDecrypted());
				assertNull(objEntryUsed.getValueDecrypted()); 				
				
				assertFalse(objEntryUsed.isCall());
				assertFalse(objEntryUsed.isJavaCall());
				assertNull(objEntryUsed.getCallingClassname());
				assertNull(objEntryUsed.getCallingMethodname());
			}
			
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
	public void testCompute_EncryptionAsEntry(){
		String sExpressionSource=null;
//		try {
			sExpressionSource = KernelEncryptionIniSolverZZZTest.sEXPRESSION_ENCRYPTION01_DEFAULT;
			testCompute_EncryptionAsEntry_(sExpressionSource);
			
			sExpressionSource = KernelEncryptionIniSolverZZZTest.sEXPRESSION_ENCRYPTION02_DEFAULT;
			testCompute_EncryptionAsEntry_(sExpressionSource);
			
			sExpressionSource = KernelEncryptionIniSolverZZZTest.sEXPRESSION_ENCRYPTION03_DEFAULT;
			testCompute_EncryptionAsEntry_(sExpressionSource);
//		} catch (ExceptionZZZ ez) {
//			fail("Method throws an exception." + ez.getMessageLast());
//		}
	}
	
	/**void, Test: Reading an entry in a section of the ini-file
	 * 
	 * @author Fritz Lindhauer, 05.05.2023, 08:54:30
	 */
	private void testCompute_EncryptionAsEntry_(String sExpressionSourceIn){
		
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
			//sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSolved, KernelEncryptionIniSolverZZZ.sTAG_NAME);
				
			//+++ Mit Encryption-Berechnung
			
			//c)
			sExpressionSource = sExpressionSourceIn;
			sExpressionSolved = "<Z><Z:Encrypted>abcde</Z:Encrypted></Z>";//Der Wert des Tags selbst unterscheidet sich vom Wert der gesamt geparsten Zeile und hat nie umgebende Tags des solvers selbst drumherum
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, objExpressionSolver.getName(), false);
			btemp = testCompute_Encryption_(sExpressionSource, sExpressionSolved, false, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE_AS_ENTRY);
			
			
			
			//+++++++ VORGEZOGENER LETZTER FEHLERTEST ENDE
			
			
			
			//###########################
		    //### objExpression
			//#########################
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//+++ Ohne jegliche Expression-Berechnung
			//a)
			sExpressionSource = sExpressionSourceIn;
			sExpressionSolved = sExpressionSource;
			btemp = testCompute_Encryption_Unexpressed_(sExpressionSource, sExpressionSolved, false, EnumSetMappedTestCaseSolverTypeZZZ.PARSE_AS_ENTRY);
			
			//b)
			sExpressionSource = sExpressionSourceIn;
			sExpressionSolved = sExpressionSource;			
			btemp = testCompute_Encryption_Unexpressed_(sExpressionSource, sExpressionSolved, true, EnumSetMappedTestCaseSolverTypeZZZ.PARSE_AS_ENTRY);
			
			//c)
			sExpressionSource = sExpressionSourceIn;
			sExpressionSolved = sExpressionSource;
			btemp = testCompute_Encryption_Unexpressed_(sExpressionSource, sExpressionSolved, false, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE_AS_ENTRY);
			
			
			//d)
			sExpressionSource = sExpressionSourceIn;
			sExpressionSolved = sExpressionSource;			
			btemp = testCompute_Encryption_Unexpressed_(sExpressionSource, sExpressionSolved, true, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE_AS_ENTRY);
						
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//+++ Ohne Solver-Berechung		
			//a) Wenn die Tags drinbleiben sollen ist es egal, ob der Solver an oder aus ist.
			sExpressionSource = sExpressionSourceIn;
			sExpressionSolved = sExpressionSource;
			btemp = testCompute_Encryption_Unsolved_(sExpressionSource, sExpressionSolved, false, EnumSetMappedTestCaseSolverTypeZZZ.PARSE_AS_ENTRY);
			
			//b) Ist der Solver aus, dann bleiben beim Parsen die Tags des Solver drin, obwohl Tags entfernt werden sollen.
			sExpressionSource = sExpressionSourceIn;
			sExpressionSolved = sExpressionSource;
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ, false);
			//sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSolved, KernelEncryptionIniSolverZZZ.sTAG_NAME, false);
			btemp = testCompute_Encryption_Unsolved_(sExpressionSource, sExpressionSolved, true, EnumSetMappedTestCaseSolverTypeZZZ.PARSE_AS_ENTRY);

			//c)  Wenn die Tags drinbleiben sollen ist es egal, ob der Solver an oder aus ist.
			sExpressionSource = sExpressionSourceIn;
			sExpressionSolved = sExpressionSource;						
			btemp = testCompute_Encryption_Unsolved_(sExpressionSource, sExpressionSolved, false, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE_AS_ENTRY);
			
			//d) Ist der Solver aus, dann bleiben beim Parsen die Tags des Solver drin, obwohl Tags entfernt werden sollen.
			sExpressionSource = sExpressionSourceIn;
			sExpressionSolved = sExpressionSource;
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ, false);
			//sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSolved, KernelEncryptionIniSolverZZZ.sTAG_NAME, false);
			btemp = testCompute_Encryption_Unsolved_(sExpressionSource, sExpressionSolved, true, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE_AS_ENTRY);

			
			
			//+++ Ohne Encryption-Berechung
			//a) Wenn die Tags drinbleiben sollen ist es egal, ob der Solver an oder aus ist.
			sExpressionSource = sExpressionSourceIn;
			sExpressionSolved = sExpressionSource;
			btemp = testCompute_Encryption_EncryptionUnsolved_(sExpressionSource, sExpressionSolved, false, EnumSetMappedTestCaseSolverTypeZZZ.PARSE_AS_ENTRY);
			
			//b) Ist der Solver aus, dann bleiben beim Parsen die Tags des Solver drin, obwohl Tags entfernt werden sollen.
			sExpressionSource = sExpressionSourceIn;
			sExpressionSolved = sExpressionSource; 
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ, false);
			//sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSolved, KernelEncryptionIniSolverZZZ.sTAG_NAME, false);			
			btemp = testCompute_Encryption_EncryptionUnsolved_(sExpressionSource, sExpressionSolved, true, EnumSetMappedTestCaseSolverTypeZZZ.PARSE_AS_ENTRY);
			
			//c)
			sExpressionSource = sExpressionSourceIn;
			sExpressionSolved = sExpressionSource;
			btemp = testCompute_Encryption_EncryptionUnsolved_(sExpressionSource, sExpressionSolved, false, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE_AS_ENTRY);
			
			//d)
			sExpressionSource = sExpressionSourceIn;
			sExpressionSolved = sExpressionSource; 
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ, false);
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, KernelEncryptionIniSolverZZZ.sTAG_NAME, false);			
			btemp = testCompute_Encryption_EncryptionUnsolved_(sExpressionSource, sExpressionSolved, true, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE_AS_ENTRY);
			
			
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			
			//+++ Mit Encryption-Berechnung
			//a)
			sExpressionSource = sExpressionSourceIn;
			sExpressionSolved = sExpressionSource;//Der Wert des Tags selbst unterscheidet sich vom Wert der gesamt geparsten Zeile und hat nie umgebende Tags des solvers selbst drumherum
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, KernelEncryptionIniSolverZZZ.sTAG_NAME);
			btemp = testCompute_Encryption_(sExpressionSource, sExpressionSolved, false, EnumSetMappedTestCaseSolverTypeZZZ.PARSE_AS_ENTRY);
		
			//b)
			sExpressionSource = sExpressionSourceIn;
			sExpressionSolved = sExpressionSource;//Der Wert des Tags selbst unterscheidet sich vom Wert der gesamt geparsten Zeile und hat nie umgebende Tags des solvers selbst drumherum
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, KernelEncryptionIniSolverZZZ.sTAG_NAME);
			btemp = testCompute_Encryption_(sExpressionSource, sExpressionSolved, true, EnumSetMappedTestCaseSolverTypeZZZ.PARSE_AS_ENTRY);
			
			//c)
			sExpressionSource = sExpressionSourceIn;
			sExpressionSolved = "<Z><Z:Encrypted>abcde</Z:Encrypted></Z>";//Der Wert des Tags selbst unterscheidet sich vom Wert der gesamt geparsten Zeile und hat nie umgebende Tags des solvers selbst drumherum
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, objExpressionSolver.getName(), false);
			btemp = testCompute_Encryption_(sExpressionSource, sExpressionSolved, false, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE_AS_ENTRY);
			
			//d)
			sExpressionSource = sExpressionSourceIn;
			sExpressionSolved = "<Z><Z:Encrypted>abcde</Z:Encrypted></Z>";//Der Wert des Tags selbst unterscheidet sich vom Wert der gesamt geparsten Zeile und hat nie umgebende Tags des solvers selbst drumherum
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ, false);
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, objExpressionSolver.getName(), false);			
			btemp = testCompute_Encryption_(sExpressionSource, sExpressionSolved, true, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE_AS_ENTRY);
			
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}		
	}

	

	
//	/** void, Test: Reading an entry in a section of the ini-file
//	* Lindhauer; 22.04.2006 12:54:32
//	 */
//	public void testCompute04(){
//			TODOGOON20220929; //Mache eine richtige Entschlüsselung mit AES UND Danach noch testCompute05 mit MD5			
//			String sValue; String sExpression; String sExpressionSource; String sExpressionSource2;String sExpessionSourceFormulaMath;
//			String sTagStartZ;	String sTagEndZ;
//			boolean btemp;
//			sExpressionSource = KernelEncryptionIniSolverZZZTest.sEXPRESSION_ENCRYPTION04_DEFAULT;
//			try {				
//				btemp = objExpressionSolver.setFlag(IIniTagWithExpressionZZZ.FLAGZ.USEEXPRESSION, true); 
//				assertTrue("Flag nicht vorhanden '" + IIniTagWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
//				
//				btemp = objExpressionSolver.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER,true); 
//				assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
//				
//				//Nun erst werden Pfade ersetzt
//				btemp = objExpressionSolver.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH, true); //muss egal sein, da EXPRESION = FALSE //muss egal sein, wenn EXPRESIONSOLVER = FALSE
//				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
//							
//				//Nun erst werden Variablen ersetzt			
//				btemp = objExpressionSolver.setFlag(IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE,true);
//				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE + "'", btemp);
//				
//				//noch nicht sofort entschluesseln
//				btemp = objExpressionSolver.setFlag(IKernelEncryptionIniSolverZZZ.FLAGZ.USEENCRYPTION, false); //Ansonsten wird der Wert sofort ausgerechnet
//				assertTrue("Das Flag '" + IKernelEncryptionIniSolverZZZ.FLAGZ.USEENCRYPTION + "' sollte zur Verfügung stehen.", btemp);
//											
//				//### Teilberechnungen durchführen
//				Vector<String> vecReturn = objExpressionSolver.parseFirstVector(sExpressionSource);
//				assertTrue(StringZZZ.isEmpty(vecReturn.get(0))); //in der 0ten Position ist der String vor der Encryption, in der 3ten Position ist der String nach der Encryption.
//				assertFalse(StringZZZ.isEmpty(vecReturn.get(1))); //in der 0ten Position ist der String vor der Encryption, in der 3ten Position ist der String nach der Encryption.
//				assertTrue(StringZZZ.isEmpty(vecReturn.get(2))); //in der 0ten Position ist der String vor der Encryption, in der 3ten Position ist der String nach der Encryption.		
//				
//				//Gesamtberechnung durchführen
//				sTagStartZ = "<Z>";
//				sTagEndZ = "</Z>";
//				sExpression = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSource, sTagStartZ, sTagEndZ);			
//				
//				sValue = objExpressionSolver.parse(sExpressionSource);
//				assertEquals(sExpression, sValue);
//			
//				//#############################################################
//				//Sofort entschluesseln
//				btemp = objExpressionSolver.setFlag(IKernelEncryptionIniSolverZZZ.FLAGZ.USEENCRYPTION, true); //Ansonsten wird der Wert sofort ausgerechnet
//				assertTrue("Das Flag '" + IKernelEncryptionIniSolverZZZ.FLAGZ.USEENCRYPTION + "' sollte zur Verfügung stehen.", btemp);
//												
//				sExpression = "abcde";			
//				
//				sValue = objExpressionSolver.parse(sExpressionSource);
//				System.out.println(ReflectCodeZZZ.getPositionCurrent() + "\tDebugausagabe: '" + sValue + "'\n");
//				assertEquals(sExpression, sValue);
//				
//				//++++++++++++++++++++++++++++++++++++++++++++++++++								
//			
//		} catch (ExceptionZZZ ez) {
//			fail("Method throws an exception." + ez.getMessageLast());
//		}
//	}
	
}//END class
	
