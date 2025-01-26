package basic.zKernel.file.ini;

import java.io.File;
import java.util.Vector;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IObjectWithExpressionZZZ;
import basic.zBasic.ReflectCodeZZZ;
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

public class KernelJavaCallIniSolverZZZTest  extends TestCase {
	protected final static String sEXPRESSION_CALL01_DEFAULT = "<Z><Z:Call><Z:Java><Z:Class>basic.zBasic.util.machine.EnvironmentZZZ</Z:Class><Z:Method>getHostName</Z:Method></Z:Java></Z:Call></Z>";
	//protected final static String sEXPRESSION_ENCRYPTION02_DEFAULT = "<Z><Z:Encrypted><Z:Cipher>ROTnumeric</Z:Cipher><z:KeyNumber>5</z:KeyNumber><Z:FlagControl>USENUMERIC</Z:FlagControl><Z:Code>fghij</Z:Code></Z:Encrypted></Z>";
	//protected final static String sEXPRESSION_ENCRYPTION03_DEFAULT = "<Z><Z:Encrypted><Z:Cipher>ROTnn</Z:Cipher><z:KeyNumber>5</z:KeyNumber><z:CharacterPool> abcdefghijklmnopqrstuvwxyz?!</z:CharacterPool><z:FlagControl>USEUPPERCASE</Z:FlagControl><Z:Code>fghij</Z:Code></Z:Encrypted></Z>";
	
	private File objFile;
	private IKernelZZZ objKernel;
	private FileIniZZZ objFileIniTest=null;	
	
	/// +++ Die eigentlichen Test-Objekte	
	private KernelJavaCallIniSolverZZZ objExpressionJavaCallSolver;
	private KernelJavaCallIniSolverZZZ objExpressionJavaCallSolverInit;
	
	

	protected void setUp(){
		try {			
						
			objKernel = new KernelZZZ("FGL", "01", "test", "ZKernelConfigKernel_test.ini",(String)null);
			
			//#### Ein init TestObjekt
			String[] saFlagInit = {"init"};
			objExpressionJavaCallSolverInit = new KernelJavaCallIniSolverZZZ(objKernel, saFlagInit);
			
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
			btemp = objExpressionJavaCallSolver.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION, false); //Ansonsten wird der Wert sofort ausgerechnet 
			assertTrue("Flag nicht vorhanden '" + IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
			
			btemp = objExpressionJavaCallSolver.setFlag(IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER,true); //ohne aktivierten Parser keine SUBSTITUTION  
			assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER + "'", btemp);
			
			btemp = objExpressionJavaCallSolver.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH, true);			
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
		
			btemp = objExpressionJavaCallSolver.setFlag(IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE, true);			
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE + "'", btemp);
		
			btemp = objExpressionJavaCallSolver.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER, true);//Sollte dann egal sein
			assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
						
			btemp = objExpressionJavaCallSolver.setFlag(IKernelCallIniSolverZZZ.FLAGZ.USECALL, true); //Sollte dann egal sein
			assertTrue("Flag nicht vorhanden '" + IKernelCallIniSolverZZZ.FLAGZ.USECALL + "'", btemp);
						
			btemp = objExpressionJavaCallSolver.setFlag(IKernelJavaCallIniSolverZZZ.FLAGZ.USECALL_JAVA, true);//Sollte dann egal sein			
			assertTrue("Flag nicht vorhanden '" + IKernelJavaCallIniSolverZZZ.FLAGZ.USECALL_JAVA + "'", btemp);
			
			
			
			//Anwenden der 1. Formel			
			sExpression = KernelJavaCallIniSolverZZZTest.sEXPRESSION_CALL01_DEFAULT; 
			sExpressionParsed = sExpression;	
			sValue = objExpressionJavaCallSolver.parse(sExpression);			
			assertEquals(sExpressionParsed, sValue);
			
			//Beim Parsen fuer den Wert des Tags selbst(!) ist es nicht egal, ob Expression(!) verwendet wird ....
			//Dann wird nicht geparsed!!!
			sExpressionParsed = sExpression;	
			sValue = objExpressionJavaCallSolver.getValue();
			assertEquals(sExpressionParsed, sValue);
			
			//### Erst ohne Verarbeitung 2
			btemp = objExpressionJavaCallSolver.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION, true); //nun wird der Wert nach dem Parsen zumindest geaendert 
			assertTrue("Flag nicht vorhanden '" + IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
						
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
		
			sExpression = KernelJavaCallIniSolverZZZTest.sEXPRESSION_CALL01_DEFAULT;

			//Es muss beim Parsen fuer den Wert des Tags selbst(!) ist es egal, ob ein Solver(!) verwendet wird ....
			sTagStartZ = "<Z>"; //Zwar wird der Solver nicht ausgefuehrt, aber die Expression wird schon aufgeloest.
			sTagEndZ = "</Z>";
			sExpressionParsed = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpression, sTagStartZ, sTagEndZ);
				
			sValue = objExpressionJavaCallSolver.parse(sExpression);
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
			sValue = objExpressionJavaCallSolver.getValue();
			assertEquals(sExpressionParsed, sValue);
			
			//### Teilberechnungen durchführen
			btemp = objExpressionJavaCallSolver.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION, true); //nun wird der Wert nach dem Parsen zumindest geaendert 
			assertTrue("Flag nicht vorhanden '" + IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
			
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
		
			
			sExpression = KernelJavaCallIniSolverZZZTest.sEXPRESSION_CALL01_DEFAULT;
			
			sTagStartZ = "<Z>";
			sTagEndZ = "</Z>";
			sExpressionParsed = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpression, sTagStartZ, sTagEndZ);
			sExpressionParsed = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionParsed, objExpressionJavaCallSolver.getName());
			sValue = objExpressionJavaCallSolver.parse(sExpression);
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
		
			sValue = objExpressionJavaCallSolver.getValue();
			assertEquals(sExpressionParsed, sValue);
			
					
			//### Nun die Gesamtberechnung durchführen
			btemp = objExpressionJavaCallSolver.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION, true); //nun wird der Wert nach dem Parsen zumindest geaendert 
			assertTrue("Flag nicht vorhanden '" + IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
			
			btemp = objExpressionJavaCallSolver.setFlag(IKernelCallIniSolverZZZ.FLAGZ.USECALL, true); 
			assertTrue("Flag nicht vorhanden '" + IKernelCallIniSolverZZZ.FLAGZ.USECALL + "'", btemp);
						
			btemp = objExpressionJavaCallSolver.setFlag(IKernelJavaCallIniSolverZZZ.FLAGZ.USECALL_JAVA, true);			
			assertTrue("Flag nicht vorhanden '" + IKernelJavaCallIniSolverZZZ.FLAGZ.USECALL_JAVA + "'", btemp);
		
			sExpression = KernelJavaCallIniSolverZZZTest.sEXPRESSION_CALL01_DEFAULT;
						
			//Hier findet nur parse() statt. Darum wird nix aufgeloest, egal welche Flags gesetzt sind.
			sTagStartZ = "<Z>";
			sTagEndZ = "</Z>";
			sExpressionParsed = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpression, sTagStartZ, sTagEndZ);
			sExpressionParsed = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionParsed, objExpressionJavaCallSolver.getName());
			sValue = objExpressionJavaCallSolver.parse(sExpression);			
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
		
			sValue = objExpressionJavaCallSolver.getValue();
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
			btemp = objExpressionJavaCallSolver.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION, false); //Ansonsten wird der Wert sofort ausgerechnet 
			assertTrue("Flag nicht vorhanden '" + IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
			
			btemp = objExpressionJavaCallSolver.setFlag(IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER,true); //ohne aktivierten Parser keine SUBSTITUTION  
			assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER + "'", btemp);
			
			btemp = objExpressionJavaCallSolver.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH, true);			
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
		
			btemp = objExpressionJavaCallSolver.setFlag(IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE, true);			
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE + "'", btemp);
		
			btemp = objExpressionJavaCallSolver.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER, true);//Sollte dann egal sein
			assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
						
			btemp = objExpressionJavaCallSolver.setFlag(IKernelCallIniSolverZZZ.FLAGZ.USECALL, true); //Sollte dann egal sein
			assertTrue("Flag nicht vorhanden '" + IKernelCallIniSolverZZZ.FLAGZ.USECALL + "'", btemp);
						
			btemp = objExpressionJavaCallSolver.setFlag(IKernelJavaCallIniSolverZZZ.FLAGZ.USECALL_JAVA, true);//Sollte dann egal sein			
			assertTrue("Flag nicht vorhanden '" + IKernelJavaCallIniSolverZZZ.FLAGZ.USECALL_JAVA + "'", btemp);
			
			
			
			//Anwenden der 1. Formel			
			sExpression = KernelJavaCallIniSolverZZZTest.sEXPRESSION_CALL01_DEFAULT; 
			sExpressionSolved = sExpression;	
			sValue = objExpressionJavaCallSolver.solve(sExpression);			
			assertEquals(sExpressionSolved, sValue);
			
			//### Erst ohne Verarbeitung 2
			btemp = objExpressionJavaCallSolver.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION, true); //nun wird der Wert nach dem Parsen zumindest geaendert 
			assertTrue("Flag nicht vorhanden '" + IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
						
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
		
			sExpression = KernelJavaCallIniSolverZZZTest.sEXPRESSION_CALL01_DEFAULT;
			
			sTagStartZ = "<Z>"; //Zwar wird der Solver nicht ausgefuehrt, aber die Expression wird schon aufgeloest.
			sTagEndZ = "</Z>";
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpression, sTagStartZ, sTagEndZ);
				
			sValue = objExpressionJavaCallSolver.solve(sExpression);
			assertEquals(sExpressionSolved, sValue);
			
			
			//### Teilberechnungen durchführen
			btemp = objExpressionJavaCallSolver.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION, true); //nun wird der Wert nach dem Parsen zumindest geaendert 
			assertTrue("Flag nicht vorhanden '" + IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
			
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
		
			
			sExpression = KernelJavaCallIniSolverZZZTest.sEXPRESSION_CALL01_DEFAULT;		
			sExpressionSolved = "<Z:Call>" + sHostName + "</Z:Call>";		//Da der Z-Call Solver nicht involviert ist, ist das quasi wie einfacher Text zu behandeln.
			sValue = objExpressionJavaCallSolver.solve(sExpression);
			assertEquals(sExpressionSolved, sValue);
			
					
			//### Nun die Gesamtberechnung durchführen
			btemp = objExpressionJavaCallSolver.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION, true); //nun wird der Wert nach dem Parsen zumindest geaendert 
			assertTrue("Flag nicht vorhanden '" + IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
			
			btemp = objExpressionJavaCallSolver.setFlag(IKernelCallIniSolverZZZ.FLAGZ.USECALL, true); 
			assertTrue("Flag nicht vorhanden '" + IKernelCallIniSolverZZZ.FLAGZ.USECALL + "'", btemp);
						
			btemp = objExpressionJavaCallSolver.setFlag(IKernelJavaCallIniSolverZZZ.FLAGZ.USECALL_JAVA, true);			
			assertTrue("Flag nicht vorhanden '" + IKernelJavaCallIniSolverZZZ.FLAGZ.USECALL_JAVA + "'", btemp);
		
			sExpression = KernelJavaCallIniSolverZZZTest.sEXPRESSION_CALL01_DEFAULT;
						
			//Es sollte nur der Rechnername uebrigbleiben... Z-Tag raus ABER Z:Call-Tag NICHT raus, da der Call Solver nicht als Objekt hier verwendet wird...			
			sExpressionSolved = "<Z:Call>" + sHostName + "</Z:Call>"; ////Den Rechnernamen dynamisch ermitteln..., z.B.: "HannibalDEV04bVM";						
			sValue = objExpressionJavaCallSolver.solve(sExpression);			
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
			
			String sLineWithExpression = KernelJavaCallIniSolverZZZTest.sEXPRESSION_CALL01_DEFAULT;
			
			//###############################################	
			//### JAVA:CALL Ausdruck aus INI-Datei lesen. Es geht um das Entry-Objekt
			//###############################################
							
			//### Nun die Gesamtberechnung durchführen
			btemp = objExpressionJavaCallSolver.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION, true); //nun wird der Wert nach dem Parsen zumindest geaendert 
			assertTrue("Flag nicht vorhanden '" + IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
			
			btemp = objExpressionJavaCallSolver.setFlag(IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER,true); //ohne aktivierten Parser keine SUBSTITUTION  
			assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER + "'", btemp);
			
			btemp = objExpressionJavaCallSolver.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH, true);			
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
		
			btemp = objExpressionJavaCallSolver.setFlag(IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE, true);			
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE + "'", btemp);		
			
			btemp = objExpressionJavaCallSolver.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER, true);
			assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
							
			btemp = objExpressionJavaCallSolver.setFlag(IKernelCallIniSolverZZZ.FLAGZ.USECALL, true); 
			assertTrue("Flag nicht vorhanden '" + IKernelCallIniSolverZZZ.FLAGZ.USECALL + "'", btemp);
						
			btemp = objExpressionJavaCallSolver.setFlag(IKernelJavaCallIniSolverZZZ.FLAGZ.USECALL_JAVA, true);			
			assertTrue("Flag nicht vorhanden '" + IKernelJavaCallIniSolverZZZ.FLAGZ.USECALL_JAVA + "'", btemp);
		
			
			
			sExpression = KernelJavaCallIniSolverZZZTest.sEXPRESSION_CALL01_DEFAULT;
						
			//Es sollte nur der Rechnername uebrigbleiben... Z-Tag raus UND Z:Call-Tag auch raus			
			sHostName = EnvironmentZZZ.getHostName(); ////Den Rechnernamen dynamisch ermitteln..., z.B.: "HannibalDEV04bVM";
			
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			objEntry = objExpressionJavaCallSolver.parseAsEntry(sLineWithExpression);
			assertNotNull(objEntry);
			sValue = objEntry.getValue();
			System.out.println(ReflectCodeZZZ.getPositionCurrent() + "\tDebugausagabe: '" + sValue + "'\n");
			
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpression, "Z");
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, objExpressionJavaCallSolver.getName());
			assertEquals("Ohne Auflösung soll Ausgabe gleich Eingabe sein, abzueglich der Z-Tags, die wg. Parse wegfallen.",sExpressionSolved, sValue);

			//Merke: Da der Call-Solver nicht verwendet wird, kann hier nur false sein...
			bValue = objEntry.isCall();
			assertFalse(bValue);
			
			//Hier hingegen muss true stehen.
			bValue = objEntry.isJavaCall();
			assertTrue(bValue);
			
			//ohne solve wird das bisher nicht gemacht
			sClassname = objEntry.getCallingClassname();
			assertNull(sClassname);			
			
			//ohne solve wird das bisher nicht gemacht
			sMethodname = objEntry.getCallingMethodname();
			assertNull(sMethodname);

			

			
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
			
			String sLineWithExpression = KernelJavaCallIniSolverZZZTest.sEXPRESSION_CALL01_DEFAULT;
			
			//###############################################	
			//### JAVA:CALL Ausdruck aus INI-Datei lesen. Es geht um das Entry-Objekt
			//###############################################
							
			//### Nun die Gesamtberechnung durchführen
			btemp = objExpressionJavaCallSolver.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION, true); //nun wird der Wert nach dem Parsen zumindest geaendert 
			assertTrue("Flag nicht vorhanden '" + IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
			
			btemp = objExpressionJavaCallSolver.setFlag(IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER,true); //ohne aktivierten Parser keine SUBSTITUTION  
			assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER + "'", btemp);
			
			btemp = objExpressionJavaCallSolver.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH, true);			
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
		
			btemp = objExpressionJavaCallSolver.setFlag(IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE, true);			
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE + "'", btemp);		
			
			btemp = objExpressionJavaCallSolver.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER, true);
			assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
							
			btemp = objExpressionJavaCallSolver.setFlag(IKernelCallIniSolverZZZ.FLAGZ.USECALL, true); 
			assertTrue("Flag nicht vorhanden '" + IKernelCallIniSolverZZZ.FLAGZ.USECALL + "'", btemp);
						
			btemp = objExpressionJavaCallSolver.setFlag(IKernelJavaCallIniSolverZZZ.FLAGZ.USECALL_JAVA, true);			
			assertTrue("Flag nicht vorhanden '" + IKernelJavaCallIniSolverZZZ.FLAGZ.USECALL_JAVA + "'", btemp);
		
			
			
			sExpression = KernelJavaCallIniSolverZZZTest.sEXPRESSION_CALL01_DEFAULT;
						
			//Es sollte nur der Rechnername uebrigbleiben... Z-Tag raus UND Z:Call-Tag auch raus			
			sHostName = EnvironmentZZZ.getHostName(); ////Den Rechnernamen dynamisch ermitteln..., z.B.: "HannibalDEV04bVM";
			
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			objEntry = objExpressionJavaCallSolver.solveAsEntry(sLineWithExpression);
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
		

