package basic.zKernel.file.ini;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Hashtable;

import junit.framework.TestCase;
import basic.javagently.Stream;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.file.FileEasyZZZ;
import basic.zKernel.KernelZZZ;
import basic.zKernel.file.ini.KernelExpressionIniSolverZZZ;
import custom.zKernel.LogZZZ;
import custom.zKernel.file.ini.FileIniZZZ;

public class KernelExpressionIniSolverZZZTest extends TestCase {
	private final static String strFILE_DIRECTORY_DEFAULT = new String("c:\\fglKernel\\KernelTest");
	private final static String strFILE_NAME_DEFAULT = new String("JUnitTest.ini");
	
	
	private File objFile;
	private FileIniZZZ objFileIni;
	private KernelZZZ objKernel;
	
	/// +++ Die eigentlichen Test-Objekte	
	private KernelExpressionIniSolverZZZ objExpressionSolver;
	private KernelExpressionIniSolverZZZ objExpressionSolverInit;
	
	

	protected void setUp(){
		try {			
			
			//### Eine Beispieldatei. Merke: Die Einträge werden immer neu geschrieben und nicht etwa angehngt.
			//Merke: Erst wenn es �berhaupt einen Test gibt, wird diese DAtei erstellt
			String sFilePathTotal = null;		
			if(FileEasyZZZ.exists(strFILE_DIRECTORY_DEFAULT)){
				sFilePathTotal = FileEasyZZZ.joinFilePathName(strFILE_DIRECTORY_DEFAULT, strFILE_NAME_DEFAULT );
			}else{
				//Eclipse Worspace
				File f = new File("");
			    String sPathEclipse = f.getAbsolutePath();
			    System.out.println("Path for Kernel Directory Default does not exist. Using workspace absolut path: " + sPathEclipse);
				
			    sFilePathTotal = FileEasyZZZ.joinFilePathName(sPathEclipse, strFILE_NAME_DEFAULT );				
			}
			
			Stream objStreamFile = new Stream(sFilePathTotal, 1);  //This is not enough, to create the file			
			objStreamFile.println(";This is a temporarily test file for FileIniZZZTest.");      //Now the File is created. This is a comment line
			objStreamFile.println(";This file will be newly created by the setUp()-method of this JUnit Test class, every time before a testMethod will be invoked.");
			objStreamFile.println("#This is another commentline");
			objStreamFile.println("[Section A]");
			objStreamFile.println("Testentry1=Testvalue1 to be found");			
			objStreamFile.println("TestentryDymmy=nothing");
						
			objStreamFile.println("[Section B!01]");
			objStreamFile.println("Testentry2=Testvalue local to be found");
			
			objStreamFile.println("[Section B]");
			objStreamFile.println("Testentry2=Testvalue global. This should not be found");
			
			objStreamFile.println("[Section for testCompute]");
			objStreamFile.println("Formula1=Der dynamische Wert ist '<Z>[Section A]Testentry1</Z>'. FGL rulez.");
			objStreamFile.println("Formula2=Der dynamische Wert2 ist '<Z>[Section B]Testentry2</Z>'. FGL rulez.");
		
			objFile = new File(sFilePathTotal);
			
		
			
			//Kernel + Log - Object dem TestFixture hinzuf�gen. Siehe test.zzzKernel.KernelZZZTest
			objKernel = new KernelZZZ("FGL", "01", "", "ZKernelConfigKernel_test.ini",(String)null);	
			objFileIni = new FileIniZZZ(objKernel,  objFile, null);
			 			
			//### Die TestObjecte
			objExpressionSolverInit = new KernelExpressionIniSolverZZZ();
			objExpressionSolver = new KernelExpressionIniSolverZZZ(objKernel, objFileIni, null);
			
			//TestKonfiguration pr�fen
			assertTrue(objExpressionSolverInit.getFlag("init")==true);
			assertFalse(objExpressionSolver.getFlag("init")==true); //Nun w�re init falsch
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		} catch (FileNotFoundException e) {			
			e.printStackTrace();
		} catch (IOException e) {			
			e.printStackTrace();
		}		
	}//END setup
	
	/** void, Test: Reading an entry in a section of the ini-file
	* Lindhauer; 22.04.2006 12:54:32
	 */
	public void testCompute(){
		try {
			//Anwenden der ersten Formel
			objFileIni.setFlag("useformula", false); //Ansonsten wird der Wert sofort ausgerechnet
			String sExpression = objFileIni.getPropertyValue("Section for testCompute", "Formula1");
			String sValue = objExpressionSolver.compute(sExpression);
			assertEquals("Der dynamische Wert ist 'Testvalue1 to be found'. FGL rulez.", sValue);
			
			objFileIni.setFlag("useformula", true); //Damit der Wert sofort ausgerechnet wird
			sExpression = objFileIni.getPropertyValue("Section for testCompute", "Formula1");
			assertEquals(sValue, sExpression);
			
			//Anwenden der Formel, so da� ein localer Wert vor einem globalen Wert gefunden wird.
			/*objStreamFile.println("[Section B!01]");
			objStreamFile.println("Testentry2=Testvalue local to be found");
			
			objStreamFile.println("[Section B]");
			objStreamFile.println("Testentry2=Testvalue global. This should not be found");
			*/
			objFileIni.setFlag("useformula", false);//Ansonsten wird der Wert sofort ausgerechnet
			sExpression = objFileIni.getPropertyValue("Section for testCompute", "Formula2");
			sValue = objExpressionSolver.compute(sExpression);
			assertEquals("Der dynamische Wert2 ist 'Testvalue local to be found'. FGL rulez.", sValue);
			
			objFileIni.setFlag("useformula", true); //Damit der Wert sofort ausgerechnet wird
			sExpression = objFileIni.getPropertyValue("Section for testCompute", "Formula2");
			assertEquals(sValue, sExpression);
			
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}

	
}//END class
	
