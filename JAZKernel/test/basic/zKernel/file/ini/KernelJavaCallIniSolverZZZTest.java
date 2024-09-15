package basic.zKernel.file.ini;

import java.util.Vector;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.datatype.xml.XmlUtilZZZ;
import basic.zBasic.util.machine.EnvironmentZZZ;
import basic.zKernel.IKernelConfigSectionEntryZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernel.KernelConfigSectionEntryZZZ;
import basic.zKernel.KernelZZZ;
import basic.zKernel.config.KernelConfigSectionEntryUtilZZZ;
import junit.framework.TestCase;

public class KernelJavaCallIniSolverZZZTest  extends TestCase {
	protected final static String sEXPRESSION_CALL01_DEFAULT = "<Z><Z:Call><Z:Java><Z:Class>basic.zBasic.util.machine.EnvironmentZZZ</Z:Class><Z:Method>getHostName</Z:Method></Z:Java></Z:Call></Z>";
	//protected final static String sEXPRESSION_ENCRYPTION02_DEFAULT = "<Z><Z:Encrypted><Z:Cipher>ROTnumeric</Z:Cipher><z:KeyNumber>5</z:KeyNumber><Z:FlagControl>USENUMERIC</Z:FlagControl><Z:Code>fghij</Z:Code></Z:Encrypted></Z>";
	//protected final static String sEXPRESSION_ENCRYPTION03_DEFAULT = "<Z><Z:Encrypted><Z:Cipher>ROTnn</Z:Cipher><z:KeyNumber>5</z:KeyNumber><z:CharacterPool> abcdefghijklmnopqrstuvwxyz?!</z:CharacterPool><z:FlagControl>USEUPPERCASE</Z:FlagControl><Z:Code>fghij</Z:Code></Z:Encrypted></Z>";
	
	private IKernelZZZ objKernel;
	
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
		boolean btemp; String sSection; String sProperty; String sValue;
		String sExpressionSource; String sExpression;
		String sTagStartZ;	String sTagEndZ;
		Vector<String> vecReturn;
		
		try {
			//###############################################	
			//### JAVA:CALL Ausdruck aus INI-Datei lesen
			//###############################################
			
			////### Erst ohne Verarbeitung
			btemp = objExpressionJavaCallSolver.setFlag(IIniTagWithExpressionZZZ.FLAGZ.USEEXPRESSION, false); //Ansonsten wird der Wert sofort ausgerechnet 
			assertTrue("Flag nicht vorhanden '" + IIniTagWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
			
			btemp = objExpressionJavaCallSolver.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER, true);//Sollte dann egal sein
			assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
						
			btemp = objExpressionJavaCallSolver.setFlag(IKernelCallIniSolverZZZ.FLAGZ.USECALL, true); //Sollte dann egal sein
			assertTrue("Flag nicht vorhanden '" + IKernelCallIniSolverZZZ.FLAGZ.USECALL + "'", btemp);
						
			btemp = objExpressionJavaCallSolver.setFlag(IKernelJavaCallIniSolverZZZ.FLAGZ.USECALL_JAVA, true);//Sollte dann egal sein			
			assertTrue("Flag nicht vorhanden '" + IKernelJavaCallIniSolverZZZ.FLAGZ.USECALL_JAVA + "'", btemp);
			
			btemp = objExpressionJavaCallSolver.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH, true);			
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
		
			btemp = objExpressionJavaCallSolver.setFlag(IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE, true);			
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE + "'", btemp);
		
			
			//Anwenden der 1. Formel			
			sExpressionSource = KernelJavaCallIniSolverZZZTest.sEXPRESSION_CALL01_DEFAULT; 
			sExpression = sExpressionSource;	
			sValue = objExpressionJavaCallSolver.parse(sExpressionSource);			
			assertEquals(sExpression, sValue);
			
			//### Erst ohne Verarbeitung 2
			btemp = objExpressionJavaCallSolver.setFlag(IIniTagWithExpressionZZZ.FLAGZ.USEEXPRESSION, true); //nun wird der Wert nach dem Parsen zumindest geaendert 
			assertTrue("Flag nicht vorhanden '" + IIniTagWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
						
			btemp = objExpressionJavaCallSolver.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER, false);
			assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
			
			btemp = objExpressionJavaCallSolver.setFlag(IKernelCallIniSolverZZZ.FLAGZ.USECALL, true); //Sollte dann egal sein
			assertTrue("Flag nicht vorhanden '" + IKernelCallIniSolverZZZ.FLAGZ.USECALL + "'", btemp);
						
			btemp = objExpressionJavaCallSolver.setFlag(IKernelJavaCallIniSolverZZZ.FLAGZ.USECALL_JAVA, true);//Sollte dann egal sein			
			assertTrue("Flag nicht vorhanden '" + IKernelJavaCallIniSolverZZZ.FLAGZ.USECALL_JAVA + "'", btemp);
		
			btemp = objExpressionJavaCallSolver.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH, true);			
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
		
			btemp = objExpressionJavaCallSolver.setFlag(IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE, true);			
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE + "'", btemp);
		
			sExpressionSource = KernelJavaCallIniSolverZZZTest.sEXPRESSION_CALL01_DEFAULT;
			
			sTagStartZ = "<Z>"; //Zwar wird der Solver nicht ausgefuehrt, aber die Expression wird schon aufgeloest.
			sTagEndZ = "</Z>";
			sExpression = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSource, sTagStartZ, sTagEndZ);
				
			sValue = objExpressionJavaCallSolver.parse(sExpressionSource);
			assertEquals(sExpression, sValue);
			
			
			//### Teilberechnungen durchführen
			btemp = objExpressionJavaCallSolver.setFlag(IIniTagWithExpressionZZZ.FLAGZ.USEEXPRESSION, true); //nun wird der Wert nach dem Parsen zumindest geaendert 
			assertTrue("Flag nicht vorhanden '" + IIniTagWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
			
			btemp = objExpressionJavaCallSolver.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER, true);
			assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
						
			btemp = objExpressionJavaCallSolver.setFlag(IKernelCallIniSolverZZZ.FLAGZ.USECALL, false); 
			assertTrue("Flag nicht vorhanden '" + IKernelCallIniSolverZZZ.FLAGZ.USECALL + "'", btemp);
						
			btemp = objExpressionJavaCallSolver.setFlag(IKernelJavaCallIniSolverZZZ.FLAGZ.USECALL_JAVA, true);//Sollte dann egal sein			
			assertTrue("Flag nicht vorhanden '" + IKernelJavaCallIniSolverZZZ.FLAGZ.USECALL_JAVA + "'", btemp);
		
			btemp = objExpressionJavaCallSolver.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH, true);			
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
		
			btemp = objExpressionJavaCallSolver.setFlag(IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE, true);			
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE + "'", btemp);
		
			
			sExpressionSource = KernelJavaCallIniSolverZZZTest.sEXPRESSION_CALL01_DEFAULT;
			
			sTagStartZ = "<Z>";
			sTagEndZ = "</Z>";
			sExpression = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSource, sTagStartZ, sTagEndZ);
			
			sValue = objExpressionJavaCallSolver.parse(sExpressionSource);
			assertEquals(sExpression, sValue);
			
					
			//### Nun die Gesamtberechnung durchführen
			btemp = objExpressionJavaCallSolver.setFlag(IIniTagWithExpressionZZZ.FLAGZ.USEEXPRESSION, true); //nun wird der Wert nach dem Parsen zumindest geaendert 
			assertTrue("Flag nicht vorhanden '" + IIniTagWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
			
			btemp = objExpressionJavaCallSolver.setFlag(IKernelCallIniSolverZZZ.FLAGZ.USECALL, true); 
			assertTrue("Flag nicht vorhanden '" + IKernelCallIniSolverZZZ.FLAGZ.USECALL + "'", btemp);
						
			btemp = objExpressionJavaCallSolver.setFlag(IKernelJavaCallIniSolverZZZ.FLAGZ.USECALL_JAVA, true);			
			assertTrue("Flag nicht vorhanden '" + IKernelJavaCallIniSolverZZZ.FLAGZ.USECALL_JAVA + "'", btemp);
		
			sExpressionSource = KernelJavaCallIniSolverZZZTest.sEXPRESSION_CALL01_DEFAULT;
						
			//Es sollte nur der Rechnername uebrigbleiben... Z-Tag raus UND Z:Call-Tag auch raus			
			sExpression = EnvironmentZZZ.getHostName(); ////Den Rechnernamen dynamisch ermitteln..., z.B.: "HannibalDEV04bVM";						
			sValue = objExpressionJavaCallSolver.parse(sExpressionSource);			
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
	public void testCompute01asEntry(){
		try {		
			boolean btemp; String sSection; String sProperty; String sValue; boolean bValue; 
			String sClassname;	String sMethodname;								
			String sExpressionSource; String sExpression; IKernelConfigSectionEntryZZZ objEntry;
			String sTagStartZ;	String sTagEndZ;
			Vector<String> vecReturn;
			
			String sLineWithExpression = KernelJavaCallIniSolverZZZTest.sEXPRESSION_CALL01_DEFAULT;
			
			//###############################################	
			//### JAVA:CALL Ausdruck aus INI-Datei lesen. Es geht um das Entry-Objekt
			//###############################################
			
//				////### Erst ohne Verarbeitung
//				btemp = objExpressionJavaCallSolver.setFlag(IIniTagWithExpressionZZZ.FLAGZ.USEEXPRESSION, false); //Ansonsten wird der Wert sofort ausgerechnet 
//				assertTrue("Flag nicht vorhanden '" + IIniTagWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
//				
//				btemp = objExpressionJavaCallSolver.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER, true);//Sollte dann egal sein
//				assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
//							
//				btemp = objExpressionJavaCallSolver.setFlag(IKernelCallIniSolverZZZ.FLAGZ.USECALL, true); //Sollte dann egal sein
//				assertTrue("Flag nicht vorhanden '" + IKernelCallIniSolverZZZ.FLAGZ.USECALL + "'", btemp);
//							
//				btemp = objExpressionJavaCallSolver.setFlag(IKernelJavaCallIniSolverZZZ.FLAGZ.USECALL_JAVA, true);//Sollte dann egal sein			
//				assertTrue("Flag nicht vorhanden '" + IKernelJavaCallIniSolverZZZ.FLAGZ.USECALL_JAVA + "'", btemp);
//			
//				//Anwenden der 1. Formel			
//				sExpressionSource = KernelJavaCallIniSolverZZZTest.sEXPRESSION_CALL01_DEFAULT; 
//				sExpression = sExpressionSource;	
//				
//				objEntry = objExpressionJavaCallSolver.parseAsEntry(sExpressionSource);
//				assertNotNull(objEntry);
//				sValue = objEntry.getValue();
//				assertEquals(sExpression, sValue);
//				
//				//### Erst ohne Verarbeitung 2
//				btemp = objExpressionJavaCallSolver.setFlag(IIniTagWithExpressionZZZ.FLAGZ.USEEXPRESSION, true); //nun wird der Wert nach dem Parsen zumindest geaendert 
//				assertTrue("Flag nicht vorhanden '" + IIniTagWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
//							
//				btemp = objExpressionJavaCallSolver.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER, false);
//				assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
//				
//				btemp = objExpressionJavaCallSolver.setFlag(IKernelCallIniSolverZZZ.FLAGZ.USECALL, true); //Sollte dann egal sein
//				assertTrue("Flag nicht vorhanden '" + IKernelCallIniSolverZZZ.FLAGZ.USECALL + "'", btemp);
//							
//				btemp = objExpressionJavaCallSolver.setFlag(IKernelJavaCallIniSolverZZZ.FLAGZ.USECALL_JAVA, true);//Sollte dann egal sein			
//				assertTrue("Flag nicht vorhanden '" + IKernelJavaCallIniSolverZZZ.FLAGZ.USECALL_JAVA + "'", btemp);
//			
//				sExpressionSource = KernelJavaCallIniSolverZZZTest.sEXPRESSION_CALL01_DEFAULT;
//				
//				sTagStartZ = "<Z>"; //Zwar wird der Solver nicht ausgefuehrt, aber die Expression wird schon aufgeloest.
//				sTagEndZ = "</Z>";
//				sExpression = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSource, sTagStartZ, sTagEndZ);
//					
//				objEntry = objExpressionJavaCallSolver.parseAsEntry(sExpressionSource);
//				assertNotNull(objEntry);
//				sValue = objEntry.getValue();
//				assertEquals(sExpression, sValue);
//				
//				//### Teilberechnungen durchführen
//				btemp = objExpressionJavaCallSolver.setFlag(IIniTagWithExpressionZZZ.FLAGZ.USEEXPRESSION, true); //nun wird der Wert nach dem Parsen zumindest geaendert 
//				assertTrue("Flag nicht vorhanden '" + IIniTagWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
//				
//				btemp = objExpressionJavaCallSolver.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER, true);
//				assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
//							
//				btemp = objExpressionJavaCallSolver.setFlag(IKernelCallIniSolverZZZ.FLAGZ.USECALL, false); 
//				assertTrue("Flag nicht vorhanden '" + IKernelCallIniSolverZZZ.FLAGZ.USECALL + "'", btemp);
//							
//				btemp = objExpressionJavaCallSolver.setFlag(IKernelJavaCallIniSolverZZZ.FLAGZ.USECALL_JAVA, true);//Sollte dann egal sein			
//				assertTrue("Flag nicht vorhanden '" + IKernelJavaCallIniSolverZZZ.FLAGZ.USECALL_JAVA + "'", btemp);
//			
//				sExpressionSource = KernelJavaCallIniSolverZZZTest.sEXPRESSION_CALL01_DEFAULT;
//				
//				sTagStartZ = "<Z>";
//				sTagEndZ = "</Z>";
//				sExpression = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSource, sTagStartZ, sTagEndZ);
//				
//				objEntry = objExpressionJavaCallSolver.parseAsEntry(sExpressionSource);
//				assertNotNull(objEntry);
//				sValue = objEntry.getValue();
//				assertEquals(sExpression, sValue);
//				
//				//Details
//				bValue = objEntry.isCall();
//				assertFalse(bValue);
//				
//				bValue = objEntry.isJavaCall();
//				assertFalse(bValue);
//				
//				sClassname = objEntry.getCallingClassname();
//				System.out.println(ReflectCodeZZZ.getPositionCurrent() + "\tDebugausgabe3: '" + sClassname + "'\n");
//				assertNull(sClassname);
//				
//				sMethodname = objEntry.getCallingMethodname();
//				System.out.println(ReflectCodeZZZ.getPositionCurrent() + "\tDebugausgabe4: '" + sMethodname + "'\n");
//				assertNull(sMethodname);
			
			
							
			//### Nun die Gesamtberechnung durchführen
			btemp = objExpressionJavaCallSolver.setFlag(IIniTagWithExpressionZZZ.FLAGZ.USEEXPRESSION, true); //nun wird der Wert nach dem Parsen zumindest geaendert 
			assertTrue("Flag nicht vorhanden '" + IIniTagWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
			
			btemp = objExpressionJavaCallSolver.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER, true);
			assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
							
			btemp = objExpressionJavaCallSolver.setFlag(IKernelCallIniSolverZZZ.FLAGZ.USECALL, true); 
			assertTrue("Flag nicht vorhanden '" + IKernelCallIniSolverZZZ.FLAGZ.USECALL + "'", btemp);
						
			btemp = objExpressionJavaCallSolver.setFlag(IKernelJavaCallIniSolverZZZ.FLAGZ.USECALL_JAVA, true);			
			assertTrue("Flag nicht vorhanden '" + IKernelJavaCallIniSolverZZZ.FLAGZ.USECALL_JAVA + "'", btemp);
		
			btemp = objExpressionJavaCallSolver.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH, true);			
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
		
			btemp = objExpressionJavaCallSolver.setFlag(IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE, true);			
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE + "'", btemp);
		
			
			sExpressionSource = KernelJavaCallIniSolverZZZTest.sEXPRESSION_CALL01_DEFAULT;
						
			//Es sollte nur der Rechnername uebrigbleiben... Z-Tag raus UND Z:Call-Tag auch raus			
			sExpression = EnvironmentZZZ.getHostName(); ////Den Rechnernamen dynamisch ermitteln..., z.B.: "HannibalDEV04bVM";
			
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			objEntry = objExpressionJavaCallSolver.parseAsEntry(sLineWithExpression);
			assertNotNull(objEntry);
			sValue = objEntry.getValue();
			System.out.println(ReflectCodeZZZ.getPositionCurrent() + "\tDebugausagabe: '" + sValue + "'\n");
			assertEquals("Ohne Auflösung soll Ausgabe gleich Eingabe sein",sExpression, sValue);
		
			//TODOGOON20240908;//Die Werte in objEntry gehen verloren... Lösungsansatz: Beim solve(..) Reference verwenden
			
			//Details	
			//20230426: DAS IST VORERST DAS ZIEL, DAMIT IN DER FTPCREDENTIALS MASKE DER VERSCHLUESSELTE WERT AUCH ANGEZEIGT WERDEN KANN!!!
			bValue = objEntry.isCall();
			assertTrue(bValue);
			
			bValue = objEntry.isJavaCall();
			assertTrue(bValue);
			
			sClassname = objEntry.getCallingClassname();
			System.out.println(ReflectCodeZZZ.getPositionCurrent() + "\tDebugausgabe3: '" + sClassname + "'\n");
			assertEquals("basic.zBasic.util.machine.EnvironmentZZZ",sClassname);
			
			sMethodname = objEntry.getCallingMethodname();
			System.out.println(ReflectCodeZZZ.getPositionCurrent() + "\tDebugausgabe4: '" + sMethodname + "'\n");
			assertEquals("getHostName",sMethodname);
			
			//TESTE DEN WERT:
//				sValue = objEntry2.getValue();
//				System.out.println(ReflectCodeZZZ.getPositionCurrent() + "\tDebugausgabe5: '" + sValue + "'\n");
//				
//				//Besonderheit: Weil noch ein Call-Solver drum ist, ist korrekterweise beim Ausfuehren des Z:JavaCall noch der Z:Call Tag drin. Zum Vergleichen also noch rausrechnen.
//				String sTagStart = XmlUtilZZZ.computeTagPartStarting(KernelCallIniSolverZZZ.sTAG_NAME);
//				String sTagEnd = XmlUtilZZZ.computeTagPartClosing(KernelCallIniSolverZZZ.sTAG_NAME);
//				sValue = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sValue, sTagStart, sTagEnd);	
//				System.out.println(ReflectCodeZZZ.getPositionCurrent() + "\tDebugausgabe6: '" + sValue + "'\n");
//				
//				//Besonderheit: Weil der Z:Call noch drin ist, bleiben die <Z>-Tags auch noch drin. Zum Vergleichen also noch rausrechnen.
//				String sTagStartZ = "<Z>";
//				String sTagEndZ = "</Z>"; 
//				sValue = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sValue, sTagStartZ, sTagEndZ);	
//				System.out.println(ReflectCodeZZZ.getPositionCurrent() + "\tDebugausgabe7: '" + sValue + "'\n");
//				
//				String sTest = EnvironmentZZZ.getHostName();
//				assertEquals(sTest,sValue);
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++
			
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}		
}//END class
		

