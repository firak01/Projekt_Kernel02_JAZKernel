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
import basic.zBasic.util.machine.EnvironmentZZZ;
import basic.zKernel.IKernelConfigSectionEntryZZZ;
import basic.zKernel.KernelConfigSectionEntryZZZ;
import basic.zKernel.KernelZZZ;
import basic.zKernel.file.ini.KernelZFormulaIniSolverZZZ;
import custom.zKernel.LogZZZ;
import custom.zKernel.file.ini.FileIniZZZ;

public class KernelCallIniSolverZZZTest  extends TestCase {
		protected final static String sEXPRESSION_CALL01COMPUTED_DEFAULT = "<Z><Z:Call><Z:Java><Z:Class>basic.zBasic.util.machine.EnvironmentZZZ</Z:Class><Z:Method>getHostName</Z:Method></Z:Java></Z:Call></Z>";
		protected final static String sEXPRESSION_CALL01_DEFAULT = "<Z><Z:Call><Z:Java><Z:Class><Z>[ArgumentSection for testCallComputed]JavaClass</Z></Z:Class><Z:Method><Z>[ArgumentSection for testCallComputed]JavaMethod</Z></Z:Method></Z:Java></Z:Call></Z>";		
		//protected final static String sEXPRESSION_ENCRYPTION03_DEFAULT = "<Z><Z:Encrypted><Z:Cipher>ROTnn</Z:Cipher><z:KeyNumber>5</z:KeyNumber><z:CharacterPool> abcdefghijklmnopqrstuvwxyz?!</z:CharacterPool><z:FlagControl>USEUPPERCASE</Z:FlagControl><Z:Code>fghij</Z:Code></Z:Encrypted></Z>";
		
		private KernelZZZ objKernel;
		
		/// +++ Die eigentlichen Test-Objekte	
		private KernelCallIniSolverZZZ objExpressionCallSolver;
		private KernelCallIniSolverZZZ objExpressionCallSolverInit;
		
		

		protected void setUp(){
			try {			
							
				objKernel = new KernelZZZ("FGL", "01", "test", "ZKernelConfigKernel_test.ini",(String)null);
				
				//#### Ein init TestObjekt
				String[] saFlagInit = {"init"};
				objExpressionCallSolverInit = new KernelCallIniSolverZZZ(objKernel, saFlagInit);
				
				String[] saFlag = {""};
				objExpressionCallSolver = new KernelCallIniSolverZZZ(objKernel, saFlag);
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
			assertTrue(objExpressionCallSolverInit.getFlag("init")==true);
			assertFalse(objExpressionCallSolver.getFlag("init")==true); //Nun wäre init falsch
			
			boolean bFlagAvailable = objExpressionCallSolver.setFlag("usecall", false); //Ansonsten wird der Wert sofort ausgerechnet
			assertTrue("Das Flag 'usecall' sollte zur Verfügung stehen.", bFlagAvailable);
			
			bFlagAvailable = objExpressionCallSolver.setFlag("usecall_java", false); //Ansonsten wird der Wert sofort ausgerechnet
			assertTrue("Das Flag 'usecall_java' sollte zur Verfügung stehen.", bFlagAvailable);
			
			bFlagAvailable = objExpressionCallSolver.setFlag("gibtEsNicht", false); //Ansonsten wird der Wert sofort ausgerechnet
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
				String sLineWithExpression = KernelCallIniSolverZZZTest.sEXPRESSION_CALL01COMPUTED_DEFAULT;
				
				boolean bFlagAvailable = objExpressionCallSolver.setFlag(IKernelCallIniSolverZZZ.FLAGZ.USECALL, false); //Ansonsten wird der Wert sofort ausgerechnet
				assertTrue("Das Flag 'usecall' sollte zur Verfügung stehen.", bFlagAvailable);
				bFlagAvailable = objExpressionCallSolver.setFlag(IKernelJavaCallIniSolverZZZ.FLAGZ.USECALL_JAVA, false); //Ansonsten wird der Wert sofort ausgerechnet
				assertTrue("Das Flag 'usecall_java' sollte zur Verfügung stehen.", bFlagAvailable);
				
				
				//### Teilberechnungen durchführen
				Vector<String> vecReturn = objExpressionCallSolver.computeExpressionFirstVector(sLineWithExpression);
				assertFalse(StringZZZ.isEmpty(vecReturn.get(1))); //in der 0ten Position ist der String vor der Encryption, in der 3ten Position ist der String nach der Encryption.
				
				
				//### Nun die Gesamtberechnung durchführen
				String sValue = objExpressionCallSolver.compute(sLineWithExpression);
				assertEquals("Ohne Auflösung soll Ausgabe gleich Eingabe sein",sLineWithExpression, sValue);
			
				//Anwenden der ersten Formel	
				bFlagAvailable = objExpressionCallSolver.setFlag(IKernelCallIniSolverZZZ.FLAGZ.USECALL, true); //Ansonsten wird der Wert sofort ausgerechnet
				assertTrue("Das Flag 'usecall' sollte zur Verfügung stehen.", bFlagAvailable);
				bFlagAvailable = objExpressionCallSolver.setFlag(IKernelJavaCallIniSolverZZZ.FLAGZ.USECALL_JAVA, true); //Damit der Wert sofort ausgerechnet wird				
				assertTrue("Das Flag 'usecall_java' sollte zur Verfügung stehen.", bFlagAvailable);
				
				sValue = objExpressionCallSolver.compute(sLineWithExpression);			
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
				String sLineWithExpression = KernelCallIniSolverZZZTest.sEXPRESSION_CALL01COMPUTED_DEFAULT;//Merke: Die Aufloesung von Formeln wird dann vom ExpressionSolver gemacht!!!
								
				boolean bFlagAvailable = objExpressionCallSolver.setFlag(IKernelJavaCallIniSolverZZZ.FLAGZ.USECALL_JAVA, false); //Ansonsten wird der Wert sofort ausgerechnet
				assertTrue("Das Flag 'usecall' sollte zur Verfügung stehen.", bFlagAvailable);
				
				
				//### Teilberechnungen durchführen
				IKernelConfigSectionEntryZZZ objEntryTemp = new KernelConfigSectionEntryZZZ();//Hierin können während der Verarbeitung Zwischenergebnisse abgelegt werden, z.B. vor der Entschluesselung der pure Verscluesselte Wert.
				objExpressionCallSolver.setEntry(objEntryTemp);
			
				//Als Zwischenschritt die bisherigen rein stringbasierten Methoden im objEntry erweitern
				Vector<String> vecReturn = objExpressionCallSolver.computeExpressionFirstVector(sLineWithExpression);
				assertFalse(StringZZZ.isEmpty(vecReturn.get(1))); //in der 0ten Position ist der String vor der Encryption, in der 3ten Position ist der String nach der Encryption.
				
				
				//### Nun die Gesamtberechnung durchführen				
				IKernelConfigSectionEntryZZZ objEntry = objExpressionCallSolver.computeAsEntry(sLineWithExpression);
				String sValue = objEntry.getValue();
				assertEquals("Ohne Aufloesung soll Ausgabe gleich Eingabe sein",sLineWithExpression, sValue);
			
				String sValueAsExpression = objEntry.getValueFormulaSolvedAndConvertedAsExpression();
				assertNull("Ohne Aufloesung soll die Ausgabe des Expression Werts NULL sein", sValueAsExpression);				
				assertFalse("Ohne Aufloesung soll auch keine Expression vorliegen",objEntry.isExpression());
				
				
				//Anwenden der ersten Formel		
				objExpressionCallSolver.setFlag(IKernelCallIniSolverZZZ.FLAGZ.USECALL, true); //Damit der Wert sofort ausgerechnet wird
				objExpressionCallSolver.setFlag(IKernelJavaCallIniSolverZZZ.FLAGZ.USECALL_JAVA, true); //Damit der Wert sofort ausgerechnet wird						
				IKernelConfigSectionEntryZZZ objEntry2 = objExpressionCallSolver.computeAsEntry(sLineWithExpression);
				sValue = objEntry2.getValue();
				assertFalse("Mit Aufloesung soll Ausgabe anders als Eingabe sein.",sLineWithExpression.equals(sValue));
				System.out.println(ReflectCodeZZZ.getPositionCurrent() + "\tDebugausgabe1: '" + sValue + "'\n");
				
				//20230426: DAS IST VORERST DAS ZIEL, DAMIT IN DER FTPCREDENTIALS MASKE DER VERSCHLUESSELTE WERT AUCH ANGEZEIGT WERDEN KANN!!!
				boolean bValue = objEntry2.isCall();
				System.out.println(ReflectCodeZZZ.getPositionCurrent() + "\tDebugausgabe2: '" + bValue + "'\n");
				assertTrue(bValue);
				
				bValue = objEntry2.isJavaCall();
				assertTrue(bValue);
				
				String sClassname = objEntry2.getCallingClassname();
				System.out.println(ReflectCodeZZZ.getPositionCurrent() + "\tDebugausgabe3: '" + sClassname + "'\n");
				assertEquals("basic.zBasic.util.machine.EnvironmentZZZ",sClassname);
				
				String sMethodname = objEntry2.getCallingMethodname();
				System.out.println(ReflectCodeZZZ.getPositionCurrent() + "\tDebugausgabe4: '" + sMethodname + "'\n");
				assertEquals("getHostName",sMethodname);
				
				//TESTE DEN WERT:
				sValue = objEntry2.getValue();
				System.out.println(ReflectCodeZZZ.getPositionCurrent() + "\tDebugausgabe5: '" + sValue + "'\n");
				String sTest = EnvironmentZZZ.getHostName();
				
				assertEquals(sTest,sValue);
				
				//++++++++++++++++++++++++++++++++++++++++++++++++++
			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			}
		}		
		
		/** void, Test: Reading an entry in a section of the ini-file
		* Lindhauer; 22.04.2006 12:54:32
		 */
		public void testCompute01withPrePost(){
			try {				
				//Merke: Die Auflösung einer "Pfadformel" [...] findet im ExpressionIniHandler statt. Darum wird hier der Computed Wert verwendet, in dem schon Klassennamen, Methodenname enthalten ist.
				String sLineWithExpression = "PRE" + KernelCallIniSolverZZZTest.sEXPRESSION_CALL01COMPUTED_DEFAULT + "POST";//Merke: Die Aufloesung von Formeln wird dann vom ExpressionSolver gemacht!!!
								
				boolean bFlagAvailable = objExpressionCallSolver.setFlag(IKernelJavaCallIniSolverZZZ.FLAGZ.USECALL_JAVA, false); //Ansonsten wird der Wert sofort ausgerechnet
				assertTrue("Das Flag 'usecall' sollte zur Verfügung stehen.", bFlagAvailable);
				
				
				//### Teilberechnungen durchführen
				IKernelConfigSectionEntryZZZ objEntryTemp = new KernelConfigSectionEntryZZZ();//Hierin können während der Verarbeitung Zwischenergebnisse abgelegt werden, z.B. vor der Entschluesselung der pure Verscluesselte Wert.
				objExpressionCallSolver.setEntry(objEntryTemp);
				
				//Als Zwischenschritt die bisherigen rein stringbasierten Methoden im objEntry erweitern
				Vector<String> vecReturn = objExpressionCallSolver.computeExpressionFirstVector(sLineWithExpression);
				assertFalse(StringZZZ.isEmpty(vecReturn.get(1))); //in der 0ten Position ist der String vor der Encryption, in der 3ten Position ist der String nach der Encryption.
				
				
				//### Nun die Gesamtberechnung durchführen				
				IKernelConfigSectionEntryZZZ objEntry = objExpressionCallSolver.computeAsEntry(sLineWithExpression);
				String sValue = objEntry.getValue();
				assertEquals("Ohne Auflösung soll Ausgabe gleich Eingabe sein",sLineWithExpression, sValue);
			
				String sValueAsExpressionSolved = objEntry.getValueFormulaSolvedAndConvertedAsExpression();				
				assertNull("Ohne Aufloesung soll die Ausgabe des Expression Werts NULL sein", sValueAsExpressionSolved);				
				assertFalse("Ohne Aufloesung soll auch keine Expression vorliegen",objEntry.isExpression());
				
				//Anwenden der ersten Formel		
				objExpressionCallSolver.setFlag(IKernelCallIniSolverZZZ.FLAGZ.USECALL, true); //Damit der Wert sofort ausgerechnet wird
				objExpressionCallSolver.setFlag(IKernelJavaCallIniSolverZZZ.FLAGZ.USECALL_JAVA, true); //Damit der Wert sofort ausgerechnet wird						
				IKernelConfigSectionEntryZZZ objEntrySolved = objExpressionCallSolver.computeAsEntry(sLineWithExpression);
				sValue = objEntrySolved.getValue();
				assertFalse("Mit Aufloesung soll Ausgabe anders als Eingabe sein.",sLineWithExpression.equals(sValue));
				System.out.println(ReflectCodeZZZ.getPositionCurrent() + "\tDebugausgabe1: '" + sValue + "'\n");
				
				//Merke: Die Aufloesung der Expressin findet im ExpressionIniSolver statt...
//				sValueAsExpressionSolved = objEntry.getValueFormulaSolvedAndConvertedAsExpression();				
//				assertNotNull("Mit Aufloesung soll die Ausgabe des Expression Werts NICHT NULL sein", sValueAsExpressionSolved);				
//				assertTrue("Mit Aufloesung soll eine Expression vorliegen",objEntry.isExpression());
//				
				//!!! ABER: Vorangestellt soll PRE sein und am Schluss POST !!!
				boolean bTest = StringZZZ.startsWithIgnoreCase(sValue, "PRE");
				assertTrue("Der String soll berechnet sein mit 'PRE' vorangestellt", bTest);
				bTest = StringZZZ.endsWithIgnoreCase(sValue, "POST");
				assertTrue("Der String soll berechnet sein mit 'POST' am Ende", bTest);
				
				//in dem Expression Ausdruck sollen Z-Tags sein
				String sValueWithExpression = objEntrySolved.getValueAsExpression();
				bTest = StringZZZ.startsWithIgnoreCase(sValueWithExpression, "PRE<Z>");
				assertTrue("Der String als Expression soll berechnet sein mit 'PRE<Z>' vorangestellt", bTest);
				bTest = StringZZZ.endsWithIgnoreCase(sValueWithExpression, "</Z>POST");
				assertTrue("Der String als Expression soll berechnet sein mit '</Z>POST' am Ende", bTest);
				
				//Der Zwischenstand des Call Ergebnisses wird auch als Expression festgehalten
				//Aktuell dann gleich.
				String sValueCallWithExpression = objEntrySolved.getValueCallSolvedAsExpression();
				assertEquals(sValueWithExpression, sValueCallWithExpression);
				
				//20230426: DAS IST VORERST DAS ZIEL, DAMIT IN DER FTPCREDENTIALS MASKE DER VERSCHLUESSELTE WERT AUCH ANGEZEIGT WERDEN KANN!!!
				boolean bValue = objEntrySolved.isCall();
				System.out.println(ReflectCodeZZZ.getPositionCurrent() + "\tDebugausgabe2: '" + bValue + "'\n");
				assertTrue(bValue);
				
				bValue = objEntrySolved.isJavaCall();
				assertTrue(bValue);
				
				String sClassname = objEntrySolved.getCallingClassname();
				System.out.println(ReflectCodeZZZ.getPositionCurrent() + "\tDebugausgabe3: '" + sClassname + "'\n");
				assertEquals("basic.zBasic.util.machine.EnvironmentZZZ",sClassname);
				
				String sMethodname = objEntrySolved.getCallingMethodname();
				System.out.println(ReflectCodeZZZ.getPositionCurrent() + "\tDebugausgabe4: '" + sMethodname + "'\n");
				assertEquals("getHostName",sMethodname);
				
				//TESTE DEN WERT:
				sValue = objEntrySolved.getValue();
				System.out.println(ReflectCodeZZZ.getPositionCurrent() + "\tDebugausgabe5: '" + sValue + "'\n");
				String sTest = "PRE" + EnvironmentZZZ.getHostName() + "POST";
				
				assertEquals(sTest,sValue);
				
				//++++++++++++++++++++++++++++++++++++++++++++++++++
			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			}
		}		
		
	}//END class
		

