package basic.zKernel;

import java.io.File;
import java.util.ArrayList;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zKernel.KernelZZZ;
import custom.zKernel.LogZZZ;
import custom.zUtil.io.FileZZZ;
import junit.framework.TestCase;

public class KernelZZZIsolatedTest extends TestCase {
	/** wird in der TestSuite AllTest zusammengefasst.
	 * Darum gibt es hier keine Main - Methode.
	 * Die Main Methode von AllTest kann ausgef�hrt werden.
	 */
	
	private KernelZZZ objKernelFGL;
	private KernelZZZ objKernelTest;
	
	
	protected void setUp(){
		try {			
			//TODO: Diese Datei zuvor per Programm erstellen
			objKernelFGL = new KernelZZZ("FGL", "01", "test", "ZKernelConfigKernel_test.ini",(String[]) null);
			
			//TODO: Diese Datei zuvor per Programm erstellen
			objKernelTest = new KernelZZZ("TEST", "01", "test", "ZKernelConfigKernel_test.ini",(String[]) null);
	
		} catch (ExceptionZZZ e) {
			fail("Method throws an exception." + e.getMessageLast());
		}		
	}
	
	private void removeLogFile(KernelZZZ objKerneltemp){
		//		Log-File entfernen
		if(objKerneltemp != null){
			LogZZZ objLog = objKerneltemp.getLogObject();
			if(objLog != null){
				FileZZZ objFile = objLog.getFileObject();
				if(objFile!=null){
					objFile.deleteOnExit();
				}
			}
		}
	}
	


/** Kopiere hier einzelne Testausdrücke rein, um nicht alle Tests debuggen zu müssen
 * 
 * @author Fritz Lindhauer, 13.07.2019, 08:38:03
 */
public void testParameterByProgramAlias(){
//Merke: Mache folgendes
//String sClassname = this.getClass().getName(); 
//sClassname = StringZZZ.replace(sClassname, "Isolated", "");
	
	try{
		//C2) Setzen als Programname testen (!!! SOFORTIGES SCHREIBEN. Merke: Verz�gertes Schreiben ist nicht m�glich)
		String sToSet2 = new String("testwert progname");
		objKernelFGL.setParameterByProgramAlias("TestModule", "FGL!01!TestProg", "testProgramProperty3", sToSet2);
		
		String stemp4 = objKernelFGL.getParameterByProgramAlias("TestModule", "testProgramName", "testProgramProperty3"); //Auslesen nun �ber den anderen Weg testen. Es soll ja das gleiche rauskommen.
		assertEquals("Expected as a value of the just setted property 'testProgramProperty3'", sToSet2, stemp4);
	}catch(ExceptionZZZ ez){
		fail("An exception happend testing: " + ez.getDetailAllLast());
	}			
				

}


}//END Class