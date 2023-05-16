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
import basic.zKernel.config.KernelConfigEntryUtilZZZ;
import basic.zKernel.file.ini.KernelZFormulaIniSolverZZZ;
import custom.zKernel.LogZZZ;
import custom.zKernel.file.ini.FileIniZZZ;

public class KernelJavaCallIniSolverZZZTest  extends TestCase {
		protected final static String sEXPRESSION_CALL01_DEFAULT = "<Z><Z:Call><Z:Java><Z:Class>basic.zBasic.util.machine.EnvironmentZZZ</Z:Class><Z:Method>getHostName</Z:Method></Z:Java></Z:Call></Z>";
		//protected final static String sEXPRESSION_ENCRYPTION02_DEFAULT = "<Z><Z:Encrypted><Z:Cipher>ROTnumeric</Z:Cipher><z:KeyNumber>5</z:KeyNumber><Z:FlagControl>USENUMERIC</Z:FlagControl><Z:Code>fghij</Z:Code></Z:Encrypted></Z>";
		//protected final static String sEXPRESSION_ENCRYPTION03_DEFAULT = "<Z><Z:Encrypted><Z:Cipher>ROTnn</Z:Cipher><z:KeyNumber>5</z:KeyNumber><z:CharacterPool> abcdefghijklmnopqrstuvwxyz?!</z:CharacterPool><z:FlagControl>USEUPPERCASE</Z:FlagControl><Z:Code>fghij</Z:Code></Z:Encrypted></Z>";
		
		private KernelZZZ objKernel;
		
		/// +++ Die eigentlichen Test-Objekte	
		private KernelJavaCallIniSolverZZZ objExpressionJavaCallSolver;
		private KernelJavaCallIniSolverZZZ objExpressionJavaCallSolverInit;
		
		

		protected void setUp(){
			try {			
							
				objKernel = new KernelZZZ("FGL", "01", "test", "ZKernelConfigKernel_test.ini",(String)null);
				
				//#### Ein init TestObjekt
				String[] saFlagInit = {"init"};
				objExpressionJavaCallSolverInit = new KernelJavaCallIniSolverZZZ(objKernel, saFlagInit);
				
				String[] saFlag = {""};
				objExpressionJavaCallSolver = new KernelJavaCallIniSolverZZZ(objKernel, saFlag);
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
			assertTrue(objExpressionJavaCallSolverInit.getFlag("init")==true);
			assertFalse(objExpressionJavaCallSolver.getFlag("init")==true); //Nun wäre init falsch
			
			boolean bFlagAvailable = objExpressionJavaCallSolver.setFlag("usecall", false); //Ansonsten wird der Wert sofort ausgerechnet
			assertTrue("Das Flag 'usecall' sollte zur Verfügung stehen.", bFlagAvailable);
			
			bFlagAvailable = objExpressionJavaCallSolver.setFlag("usecall_java", false); //Ansonsten wird der Wert sofort ausgerechnet
			assertTrue("Das Flag 'usecall_java' sollte zur Verfügung stehen.", bFlagAvailable);
			
			bFlagAvailable = objExpressionJavaCallSolver.setFlag("gibtEsNicht", false); //Ansonsten wird der Wert sofort ausgerechnet
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
				String sLineWithExpression = KernelJavaCallIniSolverZZZTest.sEXPRESSION_CALL01_DEFAULT;
				
				boolean bFlagAvailable = objExpressionJavaCallSolver.setFlag(IKernelJavaCallIniSolverZZZ.FLAGZ.USECALL_JAVA, false); //Ansonsten wird der Wert sofort ausgerechnet
				assertTrue("Das Flag 'usecall_java' sollte zur Verfügung stehen.", bFlagAvailable);
				
				
				//### Teilberechnungen durchführen
				Vector<String> vecReturn = objExpressionJavaCallSolver.computeExpressionFirstVector(sLineWithExpression);
				assertFalse(StringZZZ.isEmpty(vecReturn.get(1))); //in der 0ten Position ist der String vor der Encryption, in der 3ten Position ist der String nach der Encryption.
				
				
				//### Nun die Gesamtberechnung durchführen
				String sValue = objExpressionJavaCallSolver.compute(sLineWithExpression);
				assertEquals("Ohne Auflösung soll Ausgabe gleich Eingabe sein",sLineWithExpression, sValue);
			
				//Anwenden der ersten Formel		
				objExpressionJavaCallSolver.setFlag(IKernelCallIniSolverZZZ.FLAGZ.USECALL, true); //Damit der Wert sofort ausgerechnet wird
				objExpressionJavaCallSolver.setFlag(IKernelJavaCallIniSolverZZZ.FLAGZ.USECALL_JAVA, true); //Damit der Wert sofort ausgerechnet wird			
				sValue = objExpressionJavaCallSolver.compute(sLineWithExpression);			
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
				String sLineWithExpression = KernelJavaCallIniSolverZZZTest.sEXPRESSION_CALL01_DEFAULT;
								
				boolean bFlagAvailable = objExpressionJavaCallSolver.setFlag(IKernelJavaCallIniSolverZZZ.FLAGZ.USECALL_JAVA, false); //Ansonsten wird der Wert sofort ausgerechnet
				assertTrue("Das Flag 'usecall' sollte zur Verfügung stehen.", bFlagAvailable);
				
				
				//### Teilberechnungen durchführen
				IKernelConfigSectionEntryZZZ objEntryTemp = new KernelConfigSectionEntryZZZ();//Hierin können während der Verarbeitung Zwischenergebnisse abgelegt werden, z.B. vor der Entschluesselung der pure Verscluesselte Wert.
//				ReferenceZZZ<IKernelConfigSectionEntryZZZ> objEntryReference = new ReferenceZZZ();
//				objEntryReference.set(objEntryTemp);
				
				objExpressionJavaCallSolver.setEntry(objEntryTemp);
				
				//TODOGOON20230427;//Als Zwischenschritt die bisherigen rein stringbasierten Methoden im objEntry erweitern
				Vector<String> vecReturn = objExpressionJavaCallSolver.computeExpressionFirstVector(sLineWithExpression);
				assertFalse(StringZZZ.isEmpty(vecReturn.get(1))); //in der 0ten Position ist der String vor der Encryption, in der 3ten Position ist der String nach der Encryption.
				
				
				//### Nun die Gesamtberechnung durchführen
				IKernelConfigSectionEntryZZZ objEntry = objExpressionJavaCallSolver.computeAsEntry(sLineWithExpression);
				//String sValue = objExpressionSolver.compute(sLineWithExpression);
				String sValue = objEntry.getValue();
				assertEquals("Ohne Auflösung soll Ausgabe gleich Eingabe sein",sLineWithExpression, sValue);
			
				//Anwenden der ersten Formel		
				objExpressionJavaCallSolver.setFlag(IKernelJavaCallIniSolverZZZ.FLAGZ.USECALL_JAVA, true); //Damit der Wert sofort ausgerechnet wird						
				IKernelConfigSectionEntryZZZ objEntry2 = objExpressionJavaCallSolver.computeAsEntry(sLineWithExpression);
				sValue = objEntry2.getValue();
				assertFalse("Mit Auflösung soll Ausgabe anders als Eingabe sein.",sLineWithExpression.equals(sValue));
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
				
				//Besonderheit: Weil noch ein Call-Solver drum ist, ist korrekterweise beim Ausfuehren des Z:JavaCall noch der Z:Call Tag drin. Zum Vergleichen also noch rausrechnen.
				String sTagStart = KernelCallIniSolverZZZ.computeExpressionTagStarting(KernelCallIniSolverZZZ.sTAG_NAME);
				String sTagEnd = KernelCallIniSolverZZZ.computeExpressionTagClosing(KernelCallIniSolverZZZ.sTAG_NAME);
				sValue = KernelConfigEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sValue, sTagStart, sTagEnd);	
				System.out.println(ReflectCodeZZZ.getPositionCurrent() + "\tDebugausgabe6: '" + sValue + "'\n");
				
				//Besonderheit: Weil der Z:Call noch drin ist, bleiben die <Z>-Tags auch noch drin. Zum Vergleichen also noch rausrechnen.
				String sTagStartZ = "<Z>";
				String sTagEndZ = "</Z>"; 
				sValue = KernelConfigEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sValue, sTagStartZ, sTagEndZ);	
				System.out.println(ReflectCodeZZZ.getPositionCurrent() + "\tDebugausgabe7: '" + sValue + "'\n");
				
				String sTest = EnvironmentZZZ.getHostName();
				assertEquals(sTest,sValue);
				
				//++++++++++++++++++++++++++++++++++++++++++++++++++
				
			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			}
		}		
	}//END class
		

