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
	


public void testParameterByProgramAlias(){
//Merke: Mache folgendes
//String sClassname = this.getClass().getName(); 
//sClassname = StringZZZ.replace(sClassname, "Isolated", "");
	
	/*
	try{	
//		D) Neu 20061021 Section des Aliasnamen mit Systemnumber, wenn  ein Paramenter in der Section des "nur" Aliasnamens nicht gefunden wird
		//D1) Teste das Setzen von Parameterwerten, bei gleichem Modulnamen / Aliasnamen
		String sToSet3 = new String("testwert progname equals module");
		String sClassname = this.getClass().getName(); 
		sClassname = StringZZZ.replace(sClassname, "Isolated", "");
		objKernelFGL.setParameterByProgramAlias(sClassname, "testProgramProperty4", sToSet3);
		
		String stemp5 = objKernelFGL.getParameterByProgramAlias(sClassname, "testProgramProperty4");
		assertEquals("Expected as a value of the just setted property 'testProgramProperty4'", sToSet3, stemp5);
	}catch(ExceptionZZZ ez){
		fail("An exception happend testing: " + ez.getDetailAllLast());
	}
	*/	
	//######################################
	
	try{
		//B) Übergabe als Programname testen. 20061021 nun muss der Wert gefunden werden, auch wenn der Programalias ohne Systemnumber angegeben wird
		String stemp2 = objKernelFGL.getParameterByProgramAlias("TestModule", "TestProgramName", "testProgramProperty");
		assertEquals("Expected as a value of property 'testProgramProperty'. Configured in the 'TestModule' of the Application 'FGL'", "testwert" , stemp2);
	}catch(ExceptionZZZ ez){
		fail("An exception happend testing: " + ez.getDetailAllLast());
	}	
	
	
//	try{
////		E) Neu 20070116 Direktes Setzen eines Parameters auf Modulebene
//		String sToSet4 = new String("testwert for module");
//		String sClassname = this.getClass().getName(); 
//		objKernelFGL.setParameterByModuleAlias(sClassname, "testProgramProperty5", sToSet4, true);
//		
//		String stemp6 = objKernelFGL.getParameterByModuleAlias(sClassname, "testProgramProperty5");
//		assertEquals("Expected as a value of the just setted property 'testProgramProperty5'", sToSet4, stemp6);
//	}catch(ExceptionZZZ ez){
//		fail("An exception happend testing: " + ez.getDetailAllLast());
//	}	
//		
//		//Zuletzt) Behandlung "Parameter ist nicht vorhanden": Dann soll eine ExceptionZZZ geworfen werden.
//		//Zuletzt A) Übergabe als directe Section testen	
//		try{
//		String stempZuletzt = null;
//		stempZuletzt = objKernelFGL.getParameterByProgramAlias("TestModule", "FGL!01!TestProg","testProgramPropertyNIXDA" );
//		assertNull("Expected an exception using not existing Property 'testProgramPropertyNIXDA'. Configured in the 'TestModule' of the Application 'FGL'", stempZuletzt);
//		
//		}catch(ExceptionZZZ ez){
//			fail("An exception happend testing: " + ez.getDetailAllLast());
//		}			
				

}


}//END Class