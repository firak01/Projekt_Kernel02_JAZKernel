package basic.zKernel.file.ini;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Vector;

import junit.framework.TestCase;
import basic.javagently.Stream;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractList.HashMapCaseInsensitiveZZZ;
import basic.zBasic.util.abstractList.HashMapExtendedZZZ;
import basic.zBasic.util.datatype.calling.ReferenceZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.file.FileEasyZZZ;
import basic.zKernel.IKernelConfigSectionEntryZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernel.KernelConfigSectionEntryZZZ;
import basic.zKernel.KernelZZZ;
import basic.zKernel.config.KernelConfigSectionEntryUtilZZZ;
import basic.zKernel.file.ini.KernelZFormulaIniSolverZZZ;
import custom.zKernel.LogZZZ;
import custom.zKernel.file.ini.FileIniZZZ;

public class KernelEncryptionIniSolverZZZTest extends TestCase {	
	protected final static String sEXPRESSION_ENCRYPTION01_DEFAULT = "<Z><Z:Encrypted><Z:Cipher>ROT13</Z:Cipher><Z:Code>nopqr</Z:Code></Z:Encrypted></Z>";
	protected final static String sEXPRESSION_ENCRYPTION02_DEFAULT = "<Z><Z:Encrypted><Z:Cipher>ROTnumeric</Z:Cipher><z:KeyNumber>5</z:KeyNumber><Z:FlagControl>USENUMERIC</Z:FlagControl><Z:Code>fghij</Z:Code></Z:Encrypted></Z>";
	protected final static String sEXPRESSION_ENCRYPTION03_DEFAULT = "<Z><Z:Encrypted><Z:Cipher>ROTnn</Z:Cipher><z:KeyNumber>5</z:KeyNumber><z:CharacterPool> abcdefghijklmnopqrstuvwxyz?!</z:CharacterPool><z:FlagControl>USEUPPERCASE</Z:FlagControl><Z:Code>fghij</Z:Code></Z:Encrypted></Z>";
	
	private IKernelZZZ objKernel;
	
	/// +++ Die eigentlichen Test-Objekte	
	private KernelEncryptionIniSolverZZZ objExpressionSolver;
	private KernelEncryptionIniSolverZZZ objExpressionSolverInit;
	
	

	protected void setUp(){
		try {			
						
			objKernel = new KernelZZZ("FGL", "01", "test", "ZKernelConfigKernel_test.ini",(String)null);
			
			//#### Ein init TestObjekt
			String[] saFlagInit = {"init"};
			objExpressionSolverInit = new KernelEncryptionIniSolverZZZ(objKernel, saFlagInit);
			
			String[] saFlag = {""};
			objExpressionSolver = new KernelEncryptionIniSolverZZZ(objKernel, saFlag);
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
		try{							
		assertTrue(objExpressionSolverInit.getFlag("init")==true);
		assertFalse(objExpressionSolver.getFlag("init")==true); //Nun wäre init falsch
		
		boolean bFlagAvailable = objExpressionSolver.setFlag("useencryption", false); //Ansonsten wird der Wert sofort ausgerechnet
		assertTrue("Das Flag 'useencryption' sollte zur Verfügung stehen.", bFlagAvailable);
		
		bFlagAvailable = objExpressionSolver.setFlag("gibtEsNicht", false); //Ansonsten wird der Wert sofort ausgerechnet
		assertFalse("Das Flag 'gibtEsNicht' sollte nicht zur Verfügung stehen.", bFlagAvailable);
		
		
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}
	
	/** void, Test: Reading an entry in a section of the ini-file
	* Lindhauer; 22.04.2006 12:54:32
	 */
	public void testCompute01(){
		TODOGOON20241015;//Mache testCompute... mit den entsprechenden Unterfunktionen _unsolved_ etc und uebergib die Werte von ausssen
		                 //dann wuerde testCompute04 auch ueberfluessig und nur noch ein weiterer Funktionsaufruf, mit anderen Parametern...
		
		String sValue; String sExpression; String sExpressionSource; String sExpressionSource2;String sExpessionSourceFormulaMath;
		String sTagStartZ; String sTagEndZ;
		boolean btemp;
		sExpressionSource = KernelEncryptionIniSolverZZZTest.sEXPRESSION_ENCRYPTION01_DEFAULT;
		
		try {

			btemp = objExpressionSolver.setFlag(IIniTagWithExpressionZZZ.FLAGZ.USEEXPRESSION, true); 
			assertTrue("Flag nicht vorhanden '" + IIniTagWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
			
			btemp = objExpressionSolver.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER,true); 
			assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
			
			//Nun erst werden Pfade ersetzt
			btemp = objExpressionSolver.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH, true); //muss egal sein, da EXPRESION = FALSE //muss egal sein, wenn EXPRESIONSOLVER = FALSE
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
						
			//Nun erst werden Variablen ersetzt			
			btemp = objExpressionSolver.setFlag(IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE,true);
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE + "'", btemp);
			
			//noch nicht sofort entschluesseln
			btemp = objExpressionSolver.setFlag(IKernelEncryptionIniSolverZZZ.FLAGZ.USEENCRYPTION, false); //Ansonsten wird der Wert sofort ausgerechnet
			assertTrue("Das Flag '" + IKernelEncryptionIniSolverZZZ.FLAGZ.USEENCRYPTION + "' sollte zur Verfügung stehen.", btemp);
			
			
			//### Teilberechnungen durchführen
			Vector<String> vecReturn = objExpressionSolver.parseFirstVector(sExpressionSource);
			assertFalse(StringZZZ.isEmpty(vecReturn.get(1))); //in der 0ten Position ist der String vor der Encryption, in der 3ten Position ist der String nach der Encryption.
			
			
			//### Nun die Gesamtberechnung durchführen
			sTagStartZ = "<Z>";
			sTagEndZ = "</Z>";
			sExpression = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSource, sTagStartZ, sTagEndZ);			
			sValue = objExpressionSolver.parse(sExpressionSource);
			assertEquals(sExpression, sValue);
		
			//Anwenden der ersten Formel, nun Entschluesselung	
			btemp = objExpressionSolver.setFlag(IKernelEncryptionIniSolverZZZ.FLAGZ.USEENCRYPTION, true); //Ansonsten wird der Wert sofort ausgerechnet
			assertTrue("Das Flag '" + IKernelEncryptionIniSolverZZZ.FLAGZ.USEENCRYPTION + "' sollte zur Verfügung stehen.", btemp);
		
			sExpression = "abcde";//"<Z><Z:Cipher>ROT13</Z:Cipher><Z:Code>nopqr</Z:Code></Z>";			
			sValue = objExpressionSolver.parse(sExpressionSource);			
			assertEquals(sExpression, sValue);
			System.out.println(ReflectCodeZZZ.getPositionCurrent() + "\tDebugausagabe: '" + sValue + "'\n");
			
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++
			
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}
	
	/** void, Test: Reading an entry in a section of the ini-file
	* Lindhauer; 22.04.2006 12:54:32
	 */
	public void testCompute01asEntry(){
		try {
			String sLineWithExpression = KernelEncryptionIniSolverZZZTest.sEXPRESSION_ENCRYPTION01_DEFAULT;
			
			
			boolean bFlagAvailable = objExpressionSolver.setFlag("useencryption", false); //Ansonsten wird der Wert sofort ausgerechnet
			assertTrue("Das Flag 'useencryption' sollte zur Verfügung stehen.", bFlagAvailable);
			
			
			//### Teilberechnungen durchführen
			IKernelConfigSectionEntryZZZ objEntryTemp = new KernelConfigSectionEntryZZZ();//Hierin können während der Verarbeitung Zwischenergebnisse abgelegt werden, z.B. vor der Entschluesselung der pure Verscluesselte Wert.
//			ReferenceZZZ<IKernelConfigSectionEntryZZZ> objEntryReference = new ReferenceZZZ();
//			objEntryReference.set(objEntryTemp);
			
			objExpressionSolver.setEntry(objEntryTemp);
			
			//TODOGOON20230427;//Als Zwischenschritt die bisherigen rein stringbasierten Methoden im objEntry erweitern
			Vector<String> vecReturn = objExpressionSolver.parseFirstVector(sLineWithExpression);
			assertFalse(StringZZZ.isEmpty(vecReturn.get(1))); //in der 0ten Position ist der String vor der Encryption, in der 3ten Position ist der String nach der Encryption.
			
			
			//### Nun die Gesamtberechnung durchführen
			IKernelConfigSectionEntryZZZ objEntry = objExpressionSolver.parseAsEntry(sLineWithExpression);
			//String sValue = objExpressionSolver.compute(sLineWithExpression);
			String sValue = objEntry.getValue();
			assertEquals("Ohne Auflösung soll Ausgabe gleich Eingabe sein",sLineWithExpression, sValue);
		
			//Anwenden der ersten Formel		
			objExpressionSolver.setFlag("useencryption", true); //Damit der Wert sofort ausgerechnet wird						
			IKernelConfigSectionEntryZZZ objEntry2 = objExpressionSolver.parseAsEntry(sLineWithExpression);
			sValue = objEntry2.getValue();
			assertFalse("Mit Auflösung soll Ausgabe anders als Eingabe sein.",sLineWithExpression.equals(sValue));
			System.out.println(ReflectCodeZZZ.getPositionCurrent() + "\tDebugausagabe1: '" + sValue + "'\n");
			
			//20230426: DAS IST VORERST DAS ZIEL, DAMIT IN DER FTPCREDENTIALS MASKE DER VERSCHLUESSELTE WERT AUCH ANGEZEIGT WERDEN KANN!!!
			sValue = objEntry2.getValueEncrypted();
			System.out.println(ReflectCodeZZZ.getPositionCurrent() + "\tDebugausagabe2: '" + sValue + "'\n");
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++
			
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}
	
	
	/** void, Test: Reading an entry in a section of the ini-file
	* Lindhauer; 22.04.2006 12:54:32
	 */
	public void testCompute02(){
		String sValue; String sExpression; String sExpressionSource; String sExpressionSource2;String sExpessionSourceFormulaMath;
		String sTagStartZ;	String sTagEndZ;
		boolean btemp;
		sExpressionSource = KernelEncryptionIniSolverZZZTest.sEXPRESSION_ENCRYPTION02_DEFAULT;
		
		try {
			btemp = objExpressionSolver.setFlag(IIniTagWithExpressionZZZ.FLAGZ.USEEXPRESSION, true); 
			assertTrue("Flag nicht vorhanden '" + IIniTagWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
			
			btemp = objExpressionSolver.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER,true); 
			assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
			
			//Nun erst werden Pfade ersetzt
			btemp = objExpressionSolver.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH, true); //muss egal sein, da EXPRESION = FALSE //muss egal sein, wenn EXPRESIONSOLVER = FALSE
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
						
			//Nun erst werden Variablen ersetzt			
			btemp = objExpressionSolver.setFlag(IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE,true);
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE + "'", btemp);
			
			//noch nicht sofort entschluesseln
			btemp = objExpressionSolver.setFlag(IKernelEncryptionIniSolverZZZ.FLAGZ.USEENCRYPTION, false); //Ansonsten wird der Wert sofort ausgerechnet
			assertTrue("Das Flag '" + IKernelEncryptionIniSolverZZZ.FLAGZ.USEENCRYPTION + "' sollte zur Verfügung stehen.", btemp);
						
			//Teilberechnungen durchführen
			//Vector<String> vecReturn = objExpressionSolver.parseFirstVector(sExpressionSource);
			Vector<String> vecReturn = objExpressionSolver.parseFirstVector(sExpressionSource);
			assertTrue(StringZZZ.isEmpty(vecReturn.get(0))); //in der 0ten Position ist der String vor der Encryption, in der 3ten Position ist der String nach der Encryption.
			assertFalse(StringZZZ.isEmpty(vecReturn.get(1))); //in der 0ten Position ist der String vor der Encryption, in der 3ten Position ist der String nach der Encryption.
			assertTrue(StringZZZ.isEmpty(vecReturn.get(2))); //in der 0ten Position ist der String vor der Encryption, in der 3ten Position ist der String nach der Encryption.			
			
			//Gesamtberechnung durchführen
			sTagStartZ = "<Z>";
			sTagEndZ = "</Z>";
			sExpression = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSource, sTagStartZ, sTagEndZ);			
			
			sValue = objExpressionSolver.parse(sExpressionSource);
			assertEquals(sExpression, sValue);
		
			
			//#############################################################
			//Sofort entschluesseln
			btemp = objExpressionSolver.setFlag(IKernelEncryptionIniSolverZZZ.FLAGZ.USEENCRYPTION, true); //Ansonsten wird der Wert sofort ausgerechnet
			assertTrue("Das Flag '" + IKernelEncryptionIniSolverZZZ.FLAGZ.USEENCRYPTION + "' sollte zur Verfügung stehen.", btemp);
										
			sExpression = "abcde";			
			
			sValue = objExpressionSolver.parse(sExpressionSource);
			System.out.println(ReflectCodeZZZ.getPositionCurrent() + "\tDebugausagabe: '" + sValue + "'\n");
			assertEquals(sExpression, sValue);
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++
			
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}
	
	/** void, Test: Reading an entry in a section of the ini-file
	* Lindhauer; 22.04.2006 12:54:32
	 */
	public void testCompute03(){				
			String sValue; String sExpression; String sExpressionSource; String sExpressionSource2;String sExpessionSourceFormulaMath;
			String sTagStartZ;	String sTagEndZ;
			boolean btemp;
			sExpressionSource = KernelEncryptionIniSolverZZZTest.sEXPRESSION_ENCRYPTION03_DEFAULT;
			try {				
				btemp = objExpressionSolver.setFlag(IIniTagWithExpressionZZZ.FLAGZ.USEEXPRESSION, true); 
				assertTrue("Flag nicht vorhanden '" + IIniTagWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
				
				btemp = objExpressionSolver.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER,true); 
				assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
				
				//Nun erst werden Pfade ersetzt
				btemp = objExpressionSolver.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH, true); //muss egal sein, da EXPRESION = FALSE //muss egal sein, wenn EXPRESIONSOLVER = FALSE
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
							
				//Nun erst werden Variablen ersetzt			
				btemp = objExpressionSolver.setFlag(IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE,true);
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE + "'", btemp);
				
				//noch nicht sofort entschluesseln
				btemp = objExpressionSolver.setFlag(IKernelEncryptionIniSolverZZZ.FLAGZ.USEENCRYPTION, false); //Ansonsten wird der Wert sofort ausgerechnet
				assertTrue("Das Flag '" + IKernelEncryptionIniSolverZZZ.FLAGZ.USEENCRYPTION + "' sollte zur Verfügung stehen.", btemp);
											
				//### Teilberechnungen durchführen
				Vector<String> vecReturn = objExpressionSolver.parseFirstVector(sExpressionSource);
				assertTrue(StringZZZ.isEmpty(vecReturn.get(0))); //in der 0ten Position ist der String vor der Encryption, in der 3ten Position ist der String nach der Encryption.
				assertFalse(StringZZZ.isEmpty(vecReturn.get(1))); //in der 0ten Position ist der String vor der Encryption, in der 3ten Position ist der String nach der Encryption.
				assertTrue(StringZZZ.isEmpty(vecReturn.get(2))); //in der 0ten Position ist der String vor der Encryption, in der 3ten Position ist der String nach der Encryption.		
				
				//### Gesamtberechnung durchführen
				sTagStartZ = "<Z>";
				sTagEndZ = "</Z>";
				sExpression = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSource, sTagStartZ, sTagEndZ);			
				
				sValue = objExpressionSolver.parse(sExpressionSource);
				assertEquals(sExpression, sValue);
			
				//#############################################################
				//Sofort entschluesseln
				btemp = objExpressionSolver.setFlag(IKernelEncryptionIniSolverZZZ.FLAGZ.USEENCRYPTION, true); //Ansonsten wird der Wert sofort ausgerechnet
				assertTrue("Das Flag '" + IKernelEncryptionIniSolverZZZ.FLAGZ.USEENCRYPTION + "' sollte zur Verfügung stehen.", btemp);
												
				sExpression = "abcde";			
				
				sValue = objExpressionSolver.parse(sExpressionSource);
				System.out.println(ReflectCodeZZZ.getPositionCurrent() + "\tDebugausagabe: '" + sValue + "'\n");
				assertEquals(sExpression, sValue);
				
				//++++++++++++++++++++++++++++++++++++++++++++++++++				
				
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}
	
	/** void, Test: Reading an entry in a section of the ini-file
	* Lindhauer; 22.04.2006 12:54:32
	 */
	public void testCompute04(){
			TODOGOON20220929; //Mache eine richtige Entschlüsselung mit AES UND Danach noch testCompute05 mit MD5			
			String sValue; String sExpression; String sExpressionSource; String sExpressionSource2;String sExpessionSourceFormulaMath;
			String sTagStartZ;	String sTagEndZ;
			boolean btemp;
			sExpressionSource = KernelEncryptionIniSolverZZZTest.sEXPRESSION_ENCRYPTION04_DEFAULT;
			try {				
				btemp = objExpressionSolver.setFlag(IIniTagWithExpressionZZZ.FLAGZ.USEEXPRESSION, true); 
				assertTrue("Flag nicht vorhanden '" + IIniTagWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
				
				btemp = objExpressionSolver.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER,true); 
				assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
				
				//Nun erst werden Pfade ersetzt
				btemp = objExpressionSolver.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH, true); //muss egal sein, da EXPRESION = FALSE //muss egal sein, wenn EXPRESIONSOLVER = FALSE
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
							
				//Nun erst werden Variablen ersetzt			
				btemp = objExpressionSolver.setFlag(IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE,true);
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE + "'", btemp);
				
				//noch nicht sofort entschluesseln
				btemp = objExpressionSolver.setFlag(IKernelEncryptionIniSolverZZZ.FLAGZ.USEENCRYPTION, false); //Ansonsten wird der Wert sofort ausgerechnet
				assertTrue("Das Flag '" + IKernelEncryptionIniSolverZZZ.FLAGZ.USEENCRYPTION + "' sollte zur Verfügung stehen.", btemp);
											
				//### Teilberechnungen durchführen
				Vector<String> vecReturn = objExpressionSolver.parseFirstVector(sExpressionSource);
				assertTrue(StringZZZ.isEmpty(vecReturn.get(0))); //in der 0ten Position ist der String vor der Encryption, in der 3ten Position ist der String nach der Encryption.
				assertFalse(StringZZZ.isEmpty(vecReturn.get(1))); //in der 0ten Position ist der String vor der Encryption, in der 3ten Position ist der String nach der Encryption.
				assertTrue(StringZZZ.isEmpty(vecReturn.get(2))); //in der 0ten Position ist der String vor der Encryption, in der 3ten Position ist der String nach der Encryption.		
				
				//Gesamtberechnung durchführen
				sTagStartZ = "<Z>";
				sTagEndZ = "</Z>";
				sExpression = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSource, sTagStartZ, sTagEndZ);			
				
				sValue = objExpressionSolver.parse(sExpressionSource);
				assertEquals(sExpression, sValue);
			
				//#############################################################
				//Sofort entschluesseln
				btemp = objExpressionSolver.setFlag(IKernelEncryptionIniSolverZZZ.FLAGZ.USEENCRYPTION, true); //Ansonsten wird der Wert sofort ausgerechnet
				assertTrue("Das Flag '" + IKernelEncryptionIniSolverZZZ.FLAGZ.USEENCRYPTION + "' sollte zur Verfügung stehen.", btemp);
												
				sExpression = "abcde";			
				
				sValue = objExpressionSolver.parse(sExpressionSource);
				System.out.println(ReflectCodeZZZ.getPositionCurrent() + "\tDebugausagabe: '" + sValue + "'\n");
				assertEquals(sExpression, sValue);
				
				//++++++++++++++++++++++++++++++++++++++++++++++++++								
			
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}
	
}//END class
	
