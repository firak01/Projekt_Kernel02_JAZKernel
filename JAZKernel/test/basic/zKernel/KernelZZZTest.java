package basic.zKernel;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractList.HashMapIndexedZZZ;
import basic.zBasic.util.datatype.dateTime.DateTimeZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.file.FileEasyZZZ;
import basic.zKernel.KernelZZZ;
import custom.zKernel.ConfigFGL;
import custom.zKernel.LogZZZ;
import custom.zKernel.file.ini.FileIniZZZ;
import custom.zUtil.io.FileZZZ;
import junit.framework.TestCase;

public class KernelZZZTest extends TestCase {
	/** wird in der TestSuite AllTest zusammengefasst.
	 * Darum gibt es hier keine Main - Methode.
	 * Die Main Methode von AllTest kann ausgef�hrt werden.
	 */
	
	private KernelZZZ objKernelFGL;
	//TODO IFlag muss in IKernelZZZ rein, damit geht: IKernelZZZ objKernelFGL = new KernelZZZ();
	
	private KernelZZZ objKernelTest;
	//TODO IFlag muss in IKernelZZZ rein, damit geht: IKernelZZZ objKernelTest = new KernelZZZ();
	
	
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
	
	private void removeLogFile(IKernelZZZ objKerneltemp) throws ExceptionZZZ{
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
	private void removeLogFile(KernelZZZ objKerneltemp) throws ExceptionZZZ{
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
	
public void testContructor(){
		//Wichtig: Das Entfernen des Log-Files, das ja immer erzeugt wird bei der Kernel Initiierung explizit programmieren.
	
	
		//Falls ein Datei nicht existiert, dann soll ein Fehler geworfen werden
		IKernelZZZ objKerneltemp01=null;
		try{
			objKerneltemp01 = new KernelZZZ("TEST", "01", "c:\\fglkernel\\NotExisting", "ZKernelConfigKernel_test.ini",(String[]) null);
			fail("Directory 'c:\\fglkernel\\NotExisting' was expected not to exist. An expected exception was not thrown");
		}catch(ExceptionZZZ ez){
			//Dieser Fehler wird erwartet.
			//Log-File entfernen
			try {
				this.removeLogFile(objKerneltemp01);
			} catch (ExceptionZZZ e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		IKernelZZZ objKerneltemp02=null;
		try{
			objKerneltemp02 = new KernelZZZ("TEST", "01", "", "ZKernelConfigKernel_testNotExisting.ini", (String[]) null);
			fail("Directory 'c:\\fglkernel\\NotExisting' was expected not to exist. An expected exception was not thrown");
		}catch(ExceptionZZZ ez){
			//Dieser Fehler wird erwartet.
//			Log-File entfernen
			try {
				this.removeLogFile(objKerneltemp02);
			} catch (ExceptionZZZ e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		try{
		//Dann wird das Objekt nur initialisiert, um statische Methoden zu verwenden
		//TODO IFlag muss in IKernelZZZ rein, damit geht: IKernelZZZ objKernelInit = new KernelZZZ();
		KernelZZZ objKernelInit = new KernelZZZ();
		assertTrue(objKernelInit.getFlag("init")==true);
		
		//TestKonfiguration pr�fen
		assertFalse(objKernelFGL.getFlag("init")==true); //Nun w�re init falsch
		assertEquals("FGL", objKernelFGL.getApplicationKey());
		assertEquals("01", objKernelFGL.getSystemNumber());
		assertEquals("FGL!01", objKernelFGL.getSystemKey()); //so wird die Categorie in der ini-Datei bezeichnet
		
		assertNotNull("Test-Configuration-File does not exist", objKernelFGL.getFileConfigKernelAsIni()); //die Test-Konfigurationsdatei soll vorhanden sein.
				
		assertNotNull("Log-Filepath in configuration file does not exist", objKernelFGL.getLogObject());
		
		
		
		//Testkonfiguration pr�fen
		assertFalse(objKernelTest.getFlag("init")==true); //Nun w�re init falsch
		assertEquals("TEST", objKernelTest.getApplicationKey());
		assertEquals("01", objKernelTest.getSystemNumber());
		assertEquals("TEST!01", objKernelTest.getSystemKey()); //so wird die Categorie in der ini-Datei bezeichnet
		
		assertNotNull("Test-Configuration-File does not exist", objKernelTest.getFileConfigKernelAsIni()); //die Test-Konfigurationsdatei soll vorhanden sein.
				
		assertNotNull("Log-Filepath in configuration file does not exist", objKernelTest.getLogObject());
		
		
		//TEST: Einen angegebenen Applikationsschlussel gibt es n der Datei nicht
		IKernelZZZ objKerneltemp=null;
		try{
			objKerneltemp = new KernelZZZ("NichtDa", "01", "c:\\fglKernel\\KernelTest", "ZKernelConfigKernel_test.ini",(String)null); //Keine Objektzuweiseung durchf�hren, es geht nur darum die Exception auszul�sen !!!
			fail("A not existing application-key should throw an error");
		}catch (ExceptionZZZ ez){
			//Erwarteter Fehler, keine Ausgabe !!!
//			Log-File entfernen
			this.removeLogFile(objKerneltemp);
		}
		
		}catch(ExceptionZZZ ez){
			fail("Method throws an exception." + ez.getMessageLast());
		}
		
		//###############################################
		//Exceptions für fehlende Parameter im Construktor
		IKernelZZZ objKerneltemp=null;
		try{
			objKerneltemp = new KernelZZZ("", "01", "", "ZKernelConfigKernel_test.ini", (String)null);
			fail("Application Key was not passed. An expected exception was not thrown");
		}catch(ExceptionZZZ ez){
			//Dieser Fehler wird erwartet.
//			Log-File entfernen
			try {
				this.removeLogFile(objKerneltemp);
			} catch (ExceptionZZZ e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		try{
			objKerneltemp = new KernelZZZ("TEST", "", "", "ZKernelConfigKernel_test.ini", (String)null);
			fail("SystemNumber was not passed. An expected exception was not thrown");
		}catch(ExceptionZZZ ez){
			//Dieser Fehler wird erwartet.
//			Log-File entfernen
			try {
				this.removeLogFile(objKerneltemp);
			} catch (ExceptionZZZ e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		try{
			objKerneltemp = new KernelZZZ("TEST", "01", "", "",(String) null);
			fail("File was not passed. An expected exception was not thrown");
		}catch(ExceptionZZZ ez){
			//Dieser Fehler wird erwartet.
//			Log-File entfernen
			try {
				this.removeLogFile(objKerneltemp);
			} catch (ExceptionZZZ e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
}//END Function

public void testConstructorConfigObject(){

	try{
		String[] saArg ={"-k","TEST","-s","01" ,"-d","test", "-f", "ZKernelConfigKernel_test.ini"};
		IKernelConfigZZZ objConfig = new ConfigFGL(saArg);
		assertTrue(objConfig.isOptionObjectLoaded());
		 
		IKernelZZZ objKerneltemp01 = new KernelZZZ(objConfig, (String[]) null);
		assertEquals("TEST", objKerneltemp01.getApplicationKey());
		assertEquals("01", objKerneltemp01.getSystemNumber());
		
		File objFile = objKerneltemp01.getFileConfigKernel();
		assertEquals("ZKernelConfigKernel_test.ini", objFile.getName());
		
//		Log-File entfernen
		this.removeLogFile(objKerneltemp01);
	}catch(ExceptionZZZ ez){
		fail("Method throws an exception." + ez.getMessageLast());
	}
		
		//########################	
	try{
		String[] saArg2 ={"-s","01" ,"-d","test", "-f", "ZKernelConfigKernel_test.ini"};
		IKernelConfigZZZ objConfig = new ConfigFGL(saArg2);
		assertTrue(objConfig.isOptionObjectLoaded());
		IKernelZZZ objKerneltemp02  = new KernelZZZ(objConfig, (String[]) null);
		try{					
			//Der Default - Applikation - key (des Config-Objekts)  sollte hier stehen, weil es wurde kein anderer uebergeben.
			assertEquals(objKerneltemp02.getApplicationKey(), objConfig.getApplicationKeyDefault());
			assertFalse(objKerneltemp02.getApplicationKey().equals(""));
		}catch(ExceptionZZZ ez){
			//erwarteter Fehler
//			Log-File entfernen
			this.removeLogFile(objKerneltemp02);
		}
		
	}catch(Exception e){
		fail("Method throws an exception." + e.getMessage());
	}
	

}

public void testProofModuleIsConfigured(){
	try{
		boolean btemp = objKernelFGL.proofModuleFileIsConfigured("TestModule");
		assertTrue(btemp);
	}catch(ExceptionZZZ ez){
		fail("Method throws an exception." + ez.getMessageLast());
	}
}

/** void, Testet die Methode und auch, ob es das Test-Modul-Konfigurations-File auch gibt.
* Lindhauer; 20.04.2006 09:05:11
 */
public void testFileConfigAllByDir(){
	try{
		//Konfiguration & Methode testen
		//1. Hole das aktuelle Verzeichnis
		File objFileDir = objKernelFGL.getFileConfigKernel().getParentFile();
		String sDir = objFileDir.getAbsolutePath();
				
		//Hier sollte jetzt etwas gefunden werden
		File[]objaFileConfig = objKernelFGL.getFileConfigAllByDir(sDir);
		assertNotNull("There should be at least one ini-file.", objaFileConfig);
		assertTrue("There should be at least two files. ArraySize: " + objaFileConfig.length, objaFileConfig.length>1);
		
		//2. Test mit einem Verzeichnis, das es nicht gibt.
		String sDirNixda = "c:\\temp\\nixda";
		File[] objaFileConfigNixda=null;
		try {
			objaFileConfigNixda = objKernelFGL.getFileConfigAllByDir(sDirNixda);
			fail("An Exception should have happened looking in a not existing directory '" + sDirNixda + "'");
		}catch(ExceptionZZZ ez){			
		}
		assertNull("This directory was expected to not exist. Ergo there shouldn´t be any ini-file.", objaFileConfigNixda);
				
		
	}catch(ExceptionZZZ ez){
		fail("An Exception happend looking for the configuration file for some alias: " + ez.getDetailAllLast());
	}
}

/** void, Testet die Methode und auch, ob es das Test-Modul-Konfigurations-File auch gibt.
* Lindhauer; 20.04.2006 09:05:11
 */
public void testFileConfigByAlias(){
	try{
		//Konfiguration & Methode testen
		File objFile = objKernelFGL.getFileConfigByAlias("TestModule");
		assertNotNull("The module for the alias 'TestModule' is not configured in the kernel-configuration-file.", objFile);
		
		//Testen, ob die Modulkonfiguration auch vorhanden ist
		assertTrue("The configuration file for the alias 'TestModule' does not exist.", objFile.exists());
				
		//Diese Konfiguration sollte es nicht geben
		assertNull("The module for the alias 'NotExistingModuleTest' seems to be configured in the kernel-configuration-file, or this tested method is buggy.", objKernelFGL.getFileConfigByAlias("NotExistingModuleTest"));
	}catch(ExceptionZZZ ez){
		fail("An Exception happend looking for the configuration file for some alias: " + ez.getDetailAllLast());
	}
}

public void testFileConfigModuleExternByAlias(){
	try{
		//Konfiguration & Methode testen
		File objFile = objKernelFGL.getFileConfigByAlias("TestModuleExtern");
		assertNotNull("The module for the alias 'TestModuleExtern' is not configured in the kernel-configuration-file.", objFile);
		
		//Testen, ob die Modulkonfiguration auch vorhanden ist
		assertTrue("The configuration file for the alias 'TestModuleExtern' does not exist.", objFile.exists());
				
		//Diese Konfiguration sollte es nicht geben
		assertNull("The module for the alias 'NotExistingModuleTest' seems to be configured in the kernel-configuration-file, or this tested method is buggy.", objKernelFGL.getFileConfigByAlias("NotExistingModuleTest"));
		
		//#### Auslesen von Werten aus dieser Datei
		String sValueModuleExtern = objKernelFGL.getParameterByProgramAlias("TestModuleExtern", "TestProgExtern", "testGlobalProperty").getValue();
		assertEquals("The configuration of 'TestProg' in the module 'TestModuleExtern' returns an unexpected value.","testWert global extern",sValueModuleExtern);
		
	}catch(ExceptionZZZ ez){
		fail("An Exception happend looking for the configuration file for some alias: " + ez.getDetailAllLast());
	}
}

public void testProofModuleFileIsConfigured(){
	try{
		assertEquals(true, objKernelFGL.proofModuleFileIsConfigured("TestModule"));
	}catch(ExceptionZZZ ez){
		fail("An exception happend testing: " + ez.getDetailAllLast());
	}
}

public void testProofModuleFileExists(){
	try{
		// Dieses Modul soll existieren
		assertEquals(true, objKernelFGL.proofModuleFileExists("TestModule"));
		
		// Dieses Modul sollte nicht existieren
		assertEquals(false, objKernelFGL.proofModuleFileExists("blablablablabla"));
		
		}catch(ExceptionZZZ ez){
			fail("An exception happend testing: " + ez.getDetailAllLast());
		}
}

public void testGetModuleAll(){
	try{
		ArrayList listaModuleString = objKernelFGL.getModuleAll();
		assertNotNull(listaModuleString);
		assertTrue(listaModuleString.size()>=1);
	}catch(ExceptionZZZ ez){
		fail("An exception happend testing: " + ez.getDetailAllLast());
	}
}

public void testGetParameter(){
	try{
		// Dieses Modul soll existieren
		String stemp = objKernelTest.getParameter("TestGetParameter").getValue();
		assertEquals("Test erfolgreich", stemp);
	}catch(ExceptionZZZ ez){
		fail("An exception happend testing: " + ez.getDetailAllLast());
	}
}

/** void
 * Voraussetzung ist: Vorhandensein einer entsprechenden Modul-Konfigurations-.ini-Datei. Siehe testModuleExists()
* Lindhauer; 20.04.2006 07:49:38
 */
public void testGetParameterByModuleAlias(){
	try{
		// In der Modulkonfiguration soll dieser Eintrag existieren
		String stemp = objKernelFGL.getParameterByModuleAlias("TestModule", "testProgramName").getValue();
		assertEquals("Expected 'TestProg' as a value of property 'testProgramName'. Configured in the 'TestModule' of the Application 'FGL'", "TestProg" , stemp);
	}catch(ExceptionZZZ ez){
		fail("An exception happend testing: " + ez.getDetailAllLast());
	}
}

public void testGetParameterByProgramAlias(){
	try{
		//A) Übergabe als direkte Section testen
		String stemp = objKernelFGL.getParameterByProgramAlias("TestModule", "FGL!01_TestProg","testProgramProperty" ).getValue(); 
		assertEquals("Expected as a value of property 'testProgramProperty'. Configured in the 'TestModule' of the Application 'FGL'", "testwert local 4 program" , stemp);
	}catch(ExceptionZZZ ez){
		fail("An exception happend testing: " + ez.getDetailAllLast());
	}	
			
	try{
		//B1) Übergabe als Programname testen. 20061021 nun muss der Wert gefunden werden, auch wenn der Programalias ohne Systemnumber angegeben wird
		//!!! GROSS-/Keinschreibung ist NICHT relevant		
		//Hier ist also 'testProgramName' ein Parameter, für den ein Alias in [FGL!01] definiert ist.
		String stemp2 = objKernelFGL.getParameterByProgramAlias("TestModule", "testProgramName", "testProgramProperty").getValue(); 
		assertEquals("Expected as a value of property 'testProgramProperty'. Configured in the 'TestModule' of the Application 'FGL'", "testwert local 4 program" , stemp2);
	}catch(ExceptionZZZ ez){
		fail("An exception happend testing: " + ez.getDetailAllLast());
	}	
	
	try{
		//B2) 20061021 dieser Wert ist dann global definiert		
		String stemp2 = objKernelFGL.getParameterByProgramAlias("TestModule", "TestProgramName", "testGlobalProperty").getValue(); 
		assertEquals("testWert global", stemp2);
	}catch(ExceptionZZZ ez){
		fail("An exception happend testing: " + ez.getDetailAllLast());
	}	
	
	try{
		//C) Einen Parameterwert setzen und anschliessend auslesen
		//C1) Direkt als Section
		String sToSet1 = new String("testwert section");
		objKernelFGL.setParameterByProgramAlias("TestModule", "FGL!01_TestProg", "testProgramProperty2", sToSet1);
		
		String stemp3 = objKernelFGL.getParameterByProgramAlias("TestModule", "testProgramName", "testProgramProperty2").getValue();  //Auslesen nun �ber den anderen Weg testen. Es soll ja das gleiche rauskommen.
		assertEquals("Expected as a value of the just setted property 'testProgramProperty2'", sToSet1, stemp3);
	}catch(ExceptionZZZ ez){
		fail("An exception happend testing: " + ez.getDetailAllLast());
	}	
		
	try{
		//C2) Setzen als Programname testen (!!! SOFORTIGES SCHREIBEN. Merke: Verz�gertes Schreiben ist nicht m�glich)
		String sToSet2 = new String("testwert progname");
		objKernelFGL.setParameterByProgramAlias("TestModule", "FGL!01_TestProg", "testProgramProperty3", sToSet2);
		
		String stemp4 = objKernelFGL.getParameterByProgramAlias("TestModule", "testProgramName", "testProgramProperty3").getValue();  //Auslesen nun �ber den anderen Weg testen. Es soll ja das gleiche rauskommen.
		assertEquals("Expected as a value of the just setted property 'testProgramProperty3'", sToSet2, stemp4);
	}catch(ExceptionZZZ ez){
		fail("An exception happend testing: " + ez.getDetailAllLast());
	}		
	
	try{	
		String sClassname = this.getClass().getName(); 
		String stempx = objKernelFGL.getParameterByProgramAlias(sClassname, "testProgramProperty4").getValue(); 
		assertFalse("Expected as a value in the property 'testProgramProperty4'", StringZZZ.isEmpty(stempx));
		
		
//		D) Neu 20061021 Section des Aliasnamen mit Systemnumber, wenn  ein Paramenter in der Section des "nur" Aliasnamens nicht gefunden wird
		//D1) Teste das Setzen von Parameterwerten, bei gleichem Modulnamen / Aliasnamen
		String sToSet3 = new String("testwert progname equals module" + DateTimeZZZ.computeTimestampUniqueString());		
		//TODOGOO; //20210303: Fehler wird geworfen, weil die Klasse nicht als Modul definiert ist.
		objKernelFGL.setParameterByProgramAlias(sClassname, "testProgramProperty4", sToSet3);
		
		String stemp5 = objKernelFGL.getParameterByProgramAlias(sClassname, "testProgramProperty4").getValue(); 
		assertEquals("Expected as a value of the just setted property 'testProgramProperty4'", sToSet3, stemp5);
		
		assertFalse("Expected a changed Value", stempx.equals(stemp5));
		
		//TODOGOON 20210303 Definiere ein Modul als externe ini Datei und schreibe darin hinein
		//........
		
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

public void testSearchAliasForModule() {
	try {
		//Erst testen, dass auch kein Leerwert kommt
		String sModule = this.getClass().getName();//basic.zKernel.KernelZZZTest
		String sReturnAlias = objKernelFGL.searchAliasForModule(sModule);
		//System.out.println("TEST: "+sReturnAlias);//TestProg
		assertEquals("TestProg", sReturnAlias);
		
	} catch (ExceptionZZZ ez) {
		fail("Method throws an exception." + ez.getMessageLast());
	}
	
}


/** void, Test: Replacing an entry in a section of the ini-file
 * @author Fritz Lindhauer, 13.08.2022, 08:46:20
 */
public void testSetParameterByProgramAlias(){
	try {
		String sModuleExtern = "TestModuleExtern";
		String sModule = this.getClass().getName();
		
		String sProgram = "TestProgExtern";
		String sProperty = "testProgramPropertyExtern4";
		
		//##########################################
		//### EXTERNES MODUL
		//##########################################
		
		//Erst testen, dass auch kein Leerwert kommt			
		IKernelConfigSectionEntryZZZ objEntry = objKernelFGL.getParameterByProgramAlias(sModuleExtern, sProgram, sProperty);
		assertTrue(objEntry.hasAnyValue());
		String sReturnSaved = objEntry.getValue();
		assertEquals("testwertextern progname equals module",sReturnSaved);
		
		String sValue = sReturnSaved + "|Timestamp: "+DateTimeZZZ.computeTimestamp();
		objKernelFGL.setParameterByProgramAlias(sModuleExtern, sProgram, sProperty, sValue, true);

		//############################################
		//### "NICHT EXTERNES" MODUL
		//############################################
		//Teste das "Parameter Holen" auch für dieses "nicht externe" Test Modul
		
		
		
//		
//		
//		String sTestValueTemp =  objFileIniTest.getPropertyValue("Section A", "Testentry1").getValue();
//		assertFalse("An empty entry was expected for  the property 'Testentry1' in 'Section A'", sTestValueTemp.equals(""));
//		
//		//nun den Wert testen, wie er im setup definiert wurde
//		assertEquals("Testvalue1", objFileIniTest.getPropertyValue("Section A", "Testentry1").getValue());
//		
//		//auch wenn es die Section überhaupt nicht gibt, darf kein Fehler entstehen
//		assertEquals("", objFileIniTest.getPropertyValue("blablbllalbl SECTION DOES NOT EXIST", "Not existing entry").getValue());
//		
//		//NEU 20070306: Hier über eine Formel die Property auslesen
//		objFileIniTest.setFlag("useFormula", false);
//		String sTestValueFormula = objFileIniTest.getPropertyValue("Section for formula", "Formula1").getValue();
//		assertEquals("Das ist der '<Z>[Section for formula value]Value1</Z>' Wert.", sTestValueFormula); 
//		
//		objFileIniTest.setFlag("useFormula", true);
//		sTestValueFormula = objFileIniTest.getPropertyValue("Section for formula", "Formula1").getValue();
//		assertEquals("Das ist der 'first value' Wert.", sTestValueFormula); //Schliesslich soll erst hier umgerechnet werden.
//		
	} catch (ExceptionZZZ ez) {
		fail("Method throws an exception." + ez.getMessageLast());
	}
}


public void testGetParameterFromClass(){
	
	//Erst mal die Konfiguration per Klassennamen sicherstellen
	String sClassname = this.getClass().getName();
			
	try{		
		File objFile = objKernelFGL.getFileConfigByAlias(sClassname);
		assertNotNull(objFile);
	}catch(ExceptionZZZ ez){
		fail("An exception happend testing: " + ez.getDetailAllLast());
	}	
	
	try{
		//a) Parameter per Methode holen
		String stemp = objKernelFGL.getParameterByModuleAlias(sClassname, "TestParameter1FromClass").getValue();
		assertEquals("TestValue1FromClass", stemp);
	}catch(ExceptionZZZ ez){
		fail("An exception happend testing: " + ez.getDetailAllLast());
	}	
	
	try{
		//b) Parameter aus dem Programm (das als Alias so aussieht wie der Klassenname) holen
		String sAgentSection = objKernelFGL.getSystemKey() + "!" + sClassname;
		String stemp2 = objKernelFGL.getParameterByProgramAlias(sClassname, sAgentSection, "TestParameter2FromClass").getValue(); 
		assertEquals("TestValue2FromClass", stemp2);
	}catch(ExceptionZZZ ez){
		fail("An exception happend testing: " + ez.getDetailAllLast());
	}	
		
	try{
		//b2) Nun soll der Klassenname ohne den Systemkey funktionieren
		String stemp4 = objKernelFGL.getParameterByProgramAlias(sClassname, sClassname, "TestParameter2FromClass").getValue(); 
		assertEquals("AnotherTestValue2FromClass", stemp4);   //Womit der Eintrag ein anderer wäre als der mit "SystemKey" - spezifizierte
	}catch(ExceptionZZZ ez){
		fail("An exception happend testing: " + ez.getDetailAllLast());
	}	
	
	try{		
		TODOGOON20220825;//Der Wert existiert nur im Externen Modul. Ist das das Problem???
		                 //Also Idee: Hier nach dem Test noch Tests einbauen, ie  explizit auf das externe Modul abprüfen. 
						//String stemp5 = objKernelFGL.getParameterByProgramAlias("TestModuleExtern", sClassname, "TestParameterGlobal1FromClass").getValue(); 
						//assertEquals("FürAlleSystemNumberGültig Extern", stemp5); //Merke: Damit Java per Default als UTF-8 encodiert ist. Auf Systemebene setzen: -Dfile.encoding=UTF-8
		
		
		//ZUDEM TODOGOON20220825: In den Methoden (auch in den Static Methoden) immer folgende Reihenfolge sicherstellen. Dazu Parameterlisten umbauen:
			//Applikation, Modul, Program, Property
		//b3) Ein Parameterwert, der in der "speziellen" Section nicht gefunden werden kann, soll in der "allgemeinen" Section (d.h. der Section ohne die SystemNumber) gefunden werden
		//TestParameterGlobal1FromClass=FürAlleSystemNumberGültig
		String stemp5 = objKernelFGL.getParameterByProgramAlias(sClassname, sClassname, "TestParameterGlobal1FromClass").getValue(); 
		assertEquals("FürAlleSystemNumberGültig", stemp5); //Merke: Damit Java per Default als UTF-8 encodiert ist. Auf Systemebene setzen: -Dfile.encoding=UTF-8
	}catch(ExceptionZZZ ez){
		fail("An exception happend testing: " + ez.getDetailAllLast());
	}	
	
	try{
		//C) "Verkürzte Parameter"
		//Wenn der Modulname und der Programname gleich sind, dann soll es moeglich sein ganz einfach nur den Programnamen und die gesuchte Property zu uebergeben
		String stemp6 = objKernelFGL.getParameterByProgramAlias(sClassname, "TestParameter1Abbreviated").getValue(); 
		assertEquals("TestValue1Abbreviated", stemp6);
	}catch(ExceptionZZZ ez){
		fail("An exception happend testing: " + ez.getDetailAllLast());
	}	
	
}

public void testGetModuleAliasFromFilename(){
	assertEquals("MYTEST", KernelZZZ.computeModuleAliasByFilename("ZKernelConfigMYTEST_blabla.ini"));
	assertEquals("MYTEST", KernelZZZ.computeModuleAliasByFilename("ZKernelConfigMYTEST.ini"));
	assertEquals("", KernelZZZ.computeModuleAliasByFilename("ZKernelMyNixTest.ini"));
	assertEquals("", KernelZZZ.computeModuleAliasByFilename("ZKernelxyzConfigMyNixTest.ini"));
	assertEquals("", KernelZZZ.computeModuleAliasByFilename("MyNixTest.ini"));
	assertEquals("", KernelZZZ.computeModuleAliasByFilename("MyNixTest.txt"));
}

/** void, testet den Zugriff auf die Aliasnamen, welche der Kernel im Zugriff hat.
 * Dabei gibt es zwei Parameter:
 * 1) Konfiguriert
 * 2) Als Datei existent
* Lindhauer; 30.04.2006 09:14:41
 */
public void testGetModuleAliasAll(){
	try{
		//Hole alle Werte, die konfiguriert sind und existieren
	//		+++ Von den konfigurierten Modulen nur diejenige, die auch existieren.
		ArrayList listaAlias = objKernelFGL.getModuleFileAliasAll(true, true);
		assertNotNull("There is no alias configured and no module does exist (arraylist null case)", listaAlias);
		assertFalse("There is no alias configured and no module does exist (arraylist empty case)", listaAlias.isEmpty()==true);
		assertTrue("There was the correct configuration expected of the module 'TestModule'. Application 'TEST'", listaAlias.contains("TestModule"));
	}catch(ExceptionZZZ ez){
		fail("An exception happend testing: " + ez.getDetailAllLast());
	}
	
	try{
	//	//+++ Von den konfigurierten Modulen diejenigen, deren Konfigurationsdatei fehlt
		ArrayList listaAlias3 = objKernelTest.getModuleFileAliasAll(false, true);
		assertNotNull("A configured module with missing configuraton file was expected (arraylist null case)", listaAlias3);
		assertFalse("A configured module with missing configuraton file was expected (arraylist empty case)", listaAlias3.isEmpty()==true);
		assertTrue("There was a missing configuration file expected. Application 'TEST'", listaAlias3.contains("NotExisting"));  //Das ist die Datei 'ZKernelConfigTestModuleNotExisting_test.ini'
	    assertFalse("There was only one missing configuration file expected. Application 'TEST", listaAlias3.size() > 1);
	}catch(ExceptionZZZ ez){
		fail("An exception happend testing: " + ez.getDetailAllLast());
	}
    	
	try{
		//+++ Von den in dem Kernel-Verzeichnis existierenden Moduldateien, diejenigen, deren Konfiguration in dieser "Hauptkernelkonfiguration" (noch) nicht erfolgt ist.
		ArrayList listaAlias2 = objKernelTest.getModuleFileAliasAll(true, false);
		assertNotNull("There is no alias configured and no module does exist (arraylist null case)", listaAlias2);
		assertFalse("There is no file found wich is not configured (use a dummy configuration file at least. Starting the filename with 'ZKernelConfig ....' (arraylist empty case)", listaAlias2.isEmpty()==true);
		assertTrue("The configuration file for the module  'ExistingButNotConfigured' should exist. But was expected to be NOT configured in the applicationalias 'TEST'", listaAlias2.contains("ExistingButNotConfigured")); //Das KernelModul wird in der Applikation FGL konfiguriert als Section der Ini-Datei:  [FGL#01]
	}catch(ExceptionZZZ ez){
		fail("An exception happend testing: " + ez.getDetailAllLast());
	}
	
	try{
		//+++ Alle Module, sowohl aus der Konfiguration als auch aus dem Kernel-Verzeichnis 
		ArrayList listaAlias4 = objKernelTest.getModuleFileAliasAll(false, false);
		assertNotNull("There is no alias configured and no module does exist (arraylist null case)", listaAlias4);
		assertFalse("There is no alias configured and no module does exist (arraylist empty case)", listaAlias4.isEmpty()==true);
		assertTrue("The module 'Kernel' was expected to be NOT configured in the application 'TEST' but a file should be there", listaAlias4.contains("Kernel")); //Das KernelModul wird in der Applikation FGL konfiguriert als Section der Ini-Datei:  [FGL#01]
		assertTrue("The module 'NotExisting' was expected to be configured, but a file should be not there. Application 'TEST'", listaAlias4.contains("NotExisting"));  //Das ist die Datei 'ZKernelConfigTestModuleNotExisting_test.ini'
	    assertTrue("The module 'Test' was expected to be configured PLUS an existing file. Application 'TEST'", listaAlias4.contains("Test"));	
	}catch(ExceptionZZZ ez){
		fail("An exception happend testing: " + ez.getDetailAllLast());
	}
	
	}

	public void  testGetSectionAliasFor() {
		try {
			String sClassname = this.getClass().getName();
			IKernelConfigSectionEntryZZZ objEntryAlias = null;
			
			//Negativfall							
			objEntryAlias = objKernelTest.getSectionAliasFor(sClassname);
			assertFalse(objEntryAlias.hasAnyValue());
		
			//Jetzt den Positivfall
			objEntryAlias = objKernelFGL.getSectionAliasFor(sClassname);
			assertTrue(objEntryAlias.hasAnyValue());
						
			String sAlias = objEntryAlias.getValue();
			assertTrue(sAlias.equals("TestProg"));						
		}catch(ExceptionZZZ ez){
			fail("An exception happend testing: " + ez.getDetailAllLast());
		}
	}
	
	public void testGetParameterHashMapStringByProgramAlias() {
		try {
	
			HashMap<String,String> hm = objKernelFGL.getParameterHashMapStringByProgramAlias("Test", "testGetParameterHashMapStringByProgramAlias", "testValue01");
			assertNotNull(hm);
			assertFalse(hm.isEmpty());
									
		}catch(ExceptionZZZ ez){
			fail("An exception happend testing: " + ez.getDetailAllLast());
		}
	}
	
	public void testGetParameterHashMapEntryByProgramAlias() {
		try {

			HashMapIndexedZZZ<Integer,IKernelConfigSectionEntryZZZ>hm= objKernelFGL.getParameterHashMapEntryByProgramAlias("Test", "testGetParameterHashMapStringByProgramAlias", "testValue01");
			assertNotNull(hm);
			assertFalse(hm.isEmpty());
			
			//Wichtig: In den einzelnen EntryObjekten sollte jetzt immer ein anderer Alias sein....
			//........
			
		}catch(ExceptionZZZ ez){
			fail("An exception happend testing: " + ez.getDetailAllLast());
		}
	}
	
	public void testSearchModuleFileByProgramAlias() {
		try {

			String sModule="TestModuleExtern";
			String sProgramOrSection="TestProg";
			FileIniZZZ objFileIniModule = objKernelFGL.searchModuleFileByProgramAlias(sModule, sProgramOrSection);
			assertNotNull(objFileIniModule);
			
			boolean bExists = FileEasyZZZ.exists(objFileIniModule.getFileObject());
			assertTrue(bExists);
			
			//Wichtig: In den einzelnen EntryObjekten sollte jetzt immer ein anderer Alias sein....
			//........
			
		}catch(ExceptionZZZ ez){
			fail("An exception happend testing: " + ez.getDetailAllLast());
		}
	}
	
	
}//END Class