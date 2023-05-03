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
import basic.zKernel.KernelConfigSectionEntryZZZ;
import basic.zKernel.KernelZZZ;
import basic.zKernel.file.ini.KernelZFormulaIniSolverZZZ;
import custom.zKernel.LogZZZ;
import custom.zKernel.file.ini.FileIniZZZ;

public class KernelJavaCallIniSolverZZZTest  extends TestCase {
		protected final static String sEXPRESSION_CALL01_DEFAULT = "<Z><Z:Call><Z:Java></Z:Class>basic.zBasic.util.machine.EnvironmentZZZ</Z:Class><Z:Method>getHostName</Z:Method></Z:Java></Call></Z>";
		//protected final static String sEXPRESSION_ENCRYPTION02_DEFAULT = "<Z><Z:Encrypted><Z:Cipher>ROTnumeric</Z:Cipher><z:KeyNumber>5</z:KeyNumber><Z:FlagControl>USENUMERIC</Z:FlagControl><Z:Code>fghij</Z:Code></Z:Encrypted></Z>";
		//protected final static String sEXPRESSION_ENCRYPTION03_DEFAULT = "<Z><Z:Encrypted><Z:Cipher>ROTnn</Z:Cipher><z:KeyNumber>5</z:KeyNumber><z:CharacterPool> abcdefghijklmnopqrstuvwxyz?!</z:CharacterPool><z:FlagControl>USEUPPERCASE</Z:FlagControl><Z:Code>fghij</Z:Code></Z:Encrypted></Z>";
		
		private KernelZZZ objKernel;
		
		/// +++ Die eigentlichen Test-Objekte	
		private KernelJavaCallIniSolverZZZ objExpressionSolver;
		private KernelJavaCallIniSolverZZZ objExpressionSolverInit;
		
		

		protected void setUp(){
			try {			
							
				objKernel = new KernelZZZ("FGL", "01", "test", "ZKernelConfigKernel_test.ini",(String)null);
				
				//#### Ein init TestObjekt
				String[] saFlagInit = {"init"};
				objExpressionSolverInit = new KernelJavaCallIniSolverZZZ(objKernel, saFlagInit);
				
				String[] saFlag = {""};
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
			
			bFlagAvailable = objExpressionSolver.setFlag("usejavacall", false); //Ansonsten wird der Wert sofort ausgerechnet
			assertTrue("Das Flag 'usecall' sollte zur Verfügung stehen.", bFlagAvailable);
			
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
			try {
				TODOGOON;//20230504: 
				
				
				String sLineWithExpression = KernelEncryptionIniSolverZZZTest.sEXPRESSION_ENCRYPTION01_DEFAULT;
				
				
				boolean bFlagAvailable = objExpressionSolver.setFlag("useencryption", false); //Ansonsten wird der Wert sofort ausgerechnet
				assertTrue("Das Flag 'useencryption' sollte zur Verfügung stehen.", bFlagAvailable);
				
				
				//### Teilberechnungen durchführen
				Vector<String> vecReturn = objExpressionSolver.computeExpressionFirstVector(sLineWithExpression);
				assertFalse(StringZZZ.isEmpty(vecReturn.get(1))); //in der 0ten Position ist der String vor der Encryption, in der 3ten Position ist der String nach der Encryption.
				
				
				//### Nun die Gesamtberechnung durchführen
				String sValue = objExpressionSolver.compute(sLineWithExpression);
				assertEquals("Ohne Auflösung soll Ausgabe gleich Eingabe sein",sLineWithExpression, sValue);
			
				//Anwenden der ersten Formel		
				objExpressionSolver.setFlag("useencryption", true); //Damit der Wert sofort ausgerechnet wird			
				sValue = objExpressionSolver.compute(sLineWithExpression);			
				assertFalse("Mit Auflösung soll Ausgabe anders als Eingabe sein.",sLineWithExpression.equals(sValue));
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
//				ReferenceZZZ<IKernelConfigSectionEntryZZZ> objEntryReference = new ReferenceZZZ();
//				objEntryReference.set(objEntryTemp);
				
				objExpressionSolver.setEntry(objEntryTemp);
				
				//TODOGOON20230427;//Als Zwischenschritt die bisherigen rein stringbasierten Methoden im objEntry erweitern
				Vector<String> vecReturn = objExpressionSolver.computeExpressionFirstVector(sLineWithExpression);
				assertFalse(StringZZZ.isEmpty(vecReturn.get(1))); //in der 0ten Position ist der String vor der Encryption, in der 3ten Position ist der String nach der Encryption.
				
				
				//### Nun die Gesamtberechnung durchführen
				IKernelConfigSectionEntryZZZ objEntry = objExpressionSolver.computeAsEntry(sLineWithExpression);
				//String sValue = objExpressionSolver.compute(sLineWithExpression);
				String sValue = objEntry.getValue();
				assertEquals("Ohne Auflösung soll Ausgabe gleich Eingabe sein",sLineWithExpression, sValue);
			
				//Anwenden der ersten Formel		
				objExpressionSolver.setFlag("useencryption", true); //Damit der Wert sofort ausgerechnet wird						
				IKernelConfigSectionEntryZZZ objEntry2 = objExpressionSolver.computeAsEntry(sLineWithExpression);
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
			try {
				String sLineWithExpression = KernelEncryptionIniSolverZZZTest.sEXPRESSION_ENCRYPTION02_DEFAULT;
				
				
				boolean bFlagAvailable = objExpressionSolver.setFlag("useencryption", false); //Ansonsten wird der Wert sofort ausgerechnet
				assertTrue("Das Flag 'useencryption' sollte zur Verfügung stehen.", bFlagAvailable);
							
				//### Teilberechnungen durchführen
				Vector<String> vecReturn = objExpressionSolver.computeExpressionFirstVector(sLineWithExpression);
				assertFalse(StringZZZ.isEmpty(vecReturn.get(1))); //in der 0ten Position ist der String vor der Encryption, in der 3ten Position ist der String nach der Encryption.
							
				//### Nun die Gesamtberechnung durchführen
				String sValue = objExpressionSolver.compute(sLineWithExpression);
				assertEquals("Ohne Auflösung soll Ausgabe gleich Eingabe sein",sLineWithExpression, sValue);
			
				//Anwenden der zweiten Formel		
				objExpressionSolver.setFlag("useencryption", true); //Damit der Wert sofort ausgerechnet wird			
				sValue = objExpressionSolver.compute(sLineWithExpression);			
				assertFalse("Mit Auflösung soll Ausgabe anders als Eingabe sein.",sLineWithExpression.equals(sValue));
				System.out.println(ReflectCodeZZZ.getPositionCurrent() + "\tDebugausagabe: '" + sValue + "'\n");
				
				
				//++++++++++++++++++++++++++++++++++++++++++++++++++
				
			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			}
		}
		
		/** void, Test: Reading an entry in a section of the ini-file
		* Lindhauer; 22.04.2006 12:54:32
		 */
		public void testCompute03(){
			try {
				String sLineWithExpression = KernelEncryptionIniSolverZZZTest.sEXPRESSION_ENCRYPTION03_DEFAULT;
				
				
				boolean bFlagAvailable = objExpressionSolver.setFlag("useencryption", false); //Ansonsten wird der Wert sofort ausgerechnet
				assertTrue("Das Flag 'useencryption' sollte zur Verfügung stehen.", bFlagAvailable);
							
				//### Teilberechnungen durchführen
				Vector<String> vecReturn = objExpressionSolver.computeExpressionFirstVector(sLineWithExpression);
				assertFalse(StringZZZ.isEmpty(vecReturn.get(1))); //in der 0ten Position ist der String vor der Encryption, in der 3ten Position ist der String nach der Encryption.
							
				//### Nun die Gesamtberechnung durchführen
				String sValue = objExpressionSolver.compute(sLineWithExpression);
				assertEquals("Ohne Auflösung soll Ausgabe gleich Eingabe sein",sLineWithExpression, sValue);
			
				//Anwenden der zweiten Formel		
				objExpressionSolver.setFlag("useencryption", true); //Damit der Wert sofort ausgerechnet wird			
				sValue = objExpressionSolver.compute(sLineWithExpression);			
				assertFalse("Mit Auflösung soll Ausgabe anders als Eingabe sein.",sLineWithExpression.equals(sValue));
				System.out.println(ReflectCodeZZZ.getPositionCurrent() + "\tDebugausagabe: '" + sValue + "'\n");
				
				
				//++++++++++++++++++++++++++++++++++++++++++++++++++
				
			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			}
		}
		
		/** void, Test: Reading an entry in a section of the ini-file
		* Lindhauer; 22.04.2006 12:54:32
		 */
		public void testCompute04(){
			try {
				TODOGOON20220929; //Mache eine richtige Entschlüsselung mit AES UND Danach noch testCompute05 mit MD5
				String sLineWithExpression = KernelEncryptionIniSolverZZZTest.sEXPRESSION_ENCRYPTION04_DEFAULT;
				
				
				boolean bFlagAvailable = objExpressionSolver.setFlag("useencryption", false); //Ansonsten wird der Wert sofort ausgerechnet
				assertTrue("Das Flag 'useencryption' sollte zur Verfügung stehen.", bFlagAvailable);
							
				//### Teilberechnungen durchführen
				Vector<String> vecReturn = objExpressionSolver.computeExpressionFirstVector(sLineWithExpression);
				assertFalse(StringZZZ.isEmpty(vecReturn.get(1))); //in der 0ten Position ist der String vor der Encryption, in der 3ten Position ist der String nach der Encryption.
							
				//### Nun die Gesamtberechnung durchführen
				String sValue = objExpressionSolver.compute(sLineWithExpression);
				assertEquals("Ohne Auflösung soll Ausgabe gleich Eingabe sein",sLineWithExpression, sValue);
			
				//Anwenden der zweiten Formel		
				objExpressionSolver.setFlag("useencryption", true); //Damit der Wert sofort ausgerechnet wird			
				sValue = objExpressionSolver.compute(sLineWithExpression);			
				assertFalse("Mit Auflösung soll Ausgabe anders als Eingabe sein.",sLineWithExpression.equals(sValue));
				System.out.println(ReflectCodeZZZ.getPositionCurrent() + "\tDebugausagabe: '" + sValue + "'\n");
				
				
				//++++++++++++++++++++++++++++++++++++++++++++++++++
				
			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			}
		}
		
	}//END class
		

