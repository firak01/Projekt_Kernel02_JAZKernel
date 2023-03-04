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
import basic.zKernel.file.ini.IKernelEncryptionIniSolverZZZ;
import basic.zKernel.file.ini.IKernelExpressionIniSolverZZZ;
import basic.zKernel.file.ini.IKernelZFormulaIniSolverZZZ;
import custom.zKernel.ConfigFGL;
import custom.zKernel.LogZZZ;
import custom.zKernel.file.ini.FileIniZZZ;
import custom.zUtil.io.FileZZZ;
import junit.framework.TestCase;

public class KernelZZZTest extends TestCase {
	/** wird in der TestSuite AllTest zusammengefasst.
	 * Darum gibt es hier keine Main - Methode.
	 * Die Main Methode von AllTest kann ausgefuehrt werden.
	 */
	
	private IKernelZZZ objKernelFGL;	
	private IKernelZZZ objKernelTest;
		
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

public void testComputeSystemSectionNamesForProgram() {
	try{
		ArrayList<String> listasProgramSection = objKernelFGL.computeSystemSectionNamesForProgram("TestProgramName");
		assertNotNull(listasProgramSection);
	}catch(ExceptionZZZ ez){
		fail("Method throws an exception." + ez.getMessageLast());
	}
}




public void testProofModuleIsConfigured(){
	try{
		boolean btemp = objKernelFGL.proofFileConfigModuleIsConfigured("TestModule");
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
		File[]objaFileConfig = objKernelFGL.getFileConfigModuleAllByDir(sDir);
		assertNotNull("There should be at least one ini-file.", objaFileConfig);
		assertTrue("There should be at least two files. ArraySize: " + objaFileConfig.length, objaFileConfig.length>1);
		
		//2. Test mit einem Verzeichnis, das es nicht gibt.
		String sDirNixda = "c:\\temp\\nixda";
		File[] objaFileConfigNixda=null;
		try {
			objaFileConfigNixda = objKernelFGL.getFileConfigModuleAllByDir(sDirNixda);
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
public void testGetFileConfigModuleIniByAlias(){
	try{
		//Konfiguration & Methode testen
		File objFile = objKernelFGL.getFileConfigModuleOLDDIRECT("TestModule");
		assertNotNull("The module for the alias 'TestModule' is not configured in the kernel-configuration-file.", objFile);
		
		//Testen, ob die Modulkonfiguration auch vorhanden ist
		assertTrue("The configuration file for the alias 'TestModule' does not exist.", objFile.exists());
				
		//Diese Konfiguration sollte es nicht geben
		assertNull("The module for the alias 'NotExistingModuleTest' seems to be configured in the kernel-configuration-file, or this tested method is buggy.", objKernelFGL.getFileConfigModuleOLDDIRECT("NotExistingModuleTest"));
	}catch(ExceptionZZZ ez){
		fail("An Exception happend looking for the configuration file for some alias: " + ez.getDetailAllLast());
	}
	
	
	try{
		//Konfiguration & Methode testen
		File objFile = objKernelFGL.getFileConfigModuleOLDDIRECT("TestModuleExtern");
		assertNotNull("The module for the alias 'TestModuleExtern' is not configured in the kernel-configuration-file.", objFile);
		
		//Testen, ob die Modulkonfiguration auch vorhanden ist
		assertTrue("The configuration file for the alias 'TestModuleExtern' does not exist.", objFile.exists());
				
		//Diese Konfiguration sollte es nicht geben
		assertNull("The module for the alias 'NotExistingModuleTest' seems to be configured in the kernel-configuration-file, or this tested method is buggy.", objKernelFGL.getFileConfigModuleOLDDIRECT("NotExistingModuleTest"));
		
		//#### Auslesen von Werten aus dieser Datei
		//+++ über module Alias
		String sValueModuleExtern = objKernelFGL.getParameterByModuleAlias("TestModuleExtern", "testModulePropertyExtern").getValue();
		sValueModuleExtern = StringZZZ.left(sValueModuleExtern+"|","|");
		assertEquals("The configuration of 'TestProg' in the module 'TestModuleExtern' returns an unexpected value.","TestModuleValueExtern",sValueModuleExtern);
		
		
		//+++ über Program Alias
		String sValueProgramExtern = objKernelFGL.getParameterByProgramAlias("TestModuleExtern", "TestProgExtern", "testGlobalProperty").getValue();
		sValueProgramExtern = StringZZZ.left(sValueProgramExtern+"|","|");
		assertEquals("The configuration of 'TestProg' in the module 'TestModuleExtern' returns an unexpected value.","testWert global extern",sValueProgramExtern);
		
	}catch(ExceptionZZZ ez){
		fail("An Exception happend looking for the configuration file for some alias: " + ez.getDetailAllLast());
	}
	
	
	//#################################################
	try{
		//Konfiguration & Methode testen
		FileIniZZZ objFileConfig = objKernelFGL.getFileConfigModuleIni("TestModuleExtern");
		assertNotNull("The module for the alias 'TestModuleExtern' is not configured in the kernel-configuration-file.", objFileConfig);
		
		//Testen, ob die Modulkonfiguration auch vorhanden ist
		File objFile = objFileConfig.getFileObject();
		assertNotNull(objFile);		
		assertTrue("The configuration file for the alias 'TestModuleExtern' does not exist.", objFile.exists());
				
		//Diese Konfiguration sollte es nicht geben
		FileIniZZZ objFileConfigNotExisting = objKernelFGL.getFileConfigModuleIni("NotExistingModuleTest");
		assertNull("The module for the alias 'NotExistingModuleTest' seems to be configured in the kernel-configuration-file, or this tested method is buggy.", objFileConfigNotExisting);
		
		//#### Auslesen von Werten aus dieser Datei
		//+++ über module Alias
		String sValueModuleExtern = objKernelFGL.getParameterByModuleAlias("TestModuleExtern", "testModulePropertyExtern").getValue();
		sValueModuleExtern = StringZZZ.left(sValueModuleExtern+"|","|");
		assertEquals("The configuration of 'TestProg' in the module 'TestModuleExtern' returns an unexpected value.","TestModuleValueExtern",sValueModuleExtern);
		
		
		//+++ über Program Alias
		String sValueProgramExtern = objKernelFGL.getParameterByProgramAlias("TestModuleExtern", "TestProgExtern", "testGlobalProperty").getValue();
		sValueProgramExtern = StringZZZ.left(sValueProgramExtern+"|","|");
		assertEquals("The configuration of 'TestProg' in the module 'TestModuleExtern' returns an unexpected value.","testWert global extern",sValueProgramExtern);
		
	}catch(ExceptionZZZ ez){
		fail("An Exception happend looking for the configuration file for some alias: " + ez.getDetailAllLast());
	}
	
}

public void testProofModuleFileIsConfigured(){
	try{
		assertEquals(true, objKernelFGL.proofFileConfigModuleIsConfigured("TestModule"));
	}catch(ExceptionZZZ ez){
		fail("An exception happend testing: " + ez.getDetailAllLast());
	}
}

public void testProofModuleFileExists(){
	try{
		// Dieses Modul soll existieren
		assertEquals(true, objKernelFGL.proofFileConfigModuleExists("TestModule"));
		
		// Dieses Modul sollte nicht existieren
		assertEquals(false, objKernelFGL.proofFileConfigModuleExists("blablablablabla"));
		
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
		String sValue = objKernelTest.getParameter("TestGetParameter").getValue();
		String stemp = StringZZZ.left(sValue+"|","|");
		assertEquals("Test erfolgreich", stemp);
		
		
		String sRaw = objKernelTest.getParameter("TestGetParameter_Encrypted").getValue();
		stemp = StringZZZ.left(sRaw+"|","|");
		assertEquals("<Z><Z:Encrypted><Z:Cipher>VigenereNn</Z:Cipher><z:KeyString>Hundi</z:KeyString><z:CharacterPool> abcdefghijklmnopqrstuvwxyz</z:CharacterPool><z:CharacterPoolAdditional>!</z:CharacterPoolAdditional><z:FlagControl>USEUPPERCASE,USENUMERIC,USELOWERCASE,USEADDITIONALCHARACTER</Z:FlagControl><Z:Code>pzGxiMMtsuOMsmlPt</Z:Code></Z:Encrypted></Z>",sRaw);

		//############################################################
		//Nun die Verschluesselung "einschalten"	
		//objKernelTest.setFlag(IKernelEncryptionIniSolverZZZ.FLAGZ.USEENCRYPTION, true);
		//....
		
		//Hiermit teste ich aber auch das "Flag-Array-Setzen" per Enumeration
		IKernelEncryptionIniSolverZZZ.FLAGZ[] objaEnum_IKernelEncryptionIniSolverZZZ = new IKernelEncryptionIniSolverZZZ.FLAGZ[1];
		objaEnum_IKernelEncryptionIniSolverZZZ[0]=IKernelEncryptionIniSolverZZZ.FLAGZ.USEENCRYPTION;		
		boolean[] baReturn0 = objKernelTest.setFlag(objaEnum_IKernelEncryptionIniSolverZZZ, true);
		assertTrue("Das Flag USEENCRYPTION wurde erwartet",baReturn0[0]);//Verschluesselung aufloesen ist ja genau das was wir wollen.
		
		IKernelExpressionIniSolverZZZ.FLAGZ[] objaEnum_IKernelExpressionIniSolverZZZ = new IKernelExpressionIniSolverZZZ.FLAGZ[1];
		objaEnum_IKernelExpressionIniSolverZZZ[0]=IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION;
		boolean[] baReturn1 = objKernelTest.setFlag(objaEnum_IKernelExpressionIniSolverZZZ, true);
		assertTrue("Das Flag USEEXPRESSION wurde erwartet",baReturn1[0]);//Ohne das Flag wird die Behandlung irgendwelcher Ausdrücke gar nicht gemacht.
		
		IKernelZFormulaIniSolverZZZ.FLAGZ[] objaEnum_IKernelZFormulaIniSolverZZZ = new IKernelZFormulaIniSolverZZZ.FLAGZ[1];
		objaEnum_IKernelZFormulaIniSolverZZZ[0]=IKernelZFormulaIniSolverZZZ.FLAGZ.USEFORMULA;
		boolean[] baReturn2 = objKernelTest.setFlag(objaEnum_IKernelZFormulaIniSolverZZZ, true);
		assertTrue("Das Flag USEFORMULA wurde erwartet",baReturn2[0]);//Ohne das Flag wird <z>...</z> um das Ergebnis ggfs. rum sein.
		
		//Das reicht noch nicht, unbeding den Cache leeren, sonst wird das unverschlüsselte Objekt zurueckgeliefert
		objKernelTest.getCacheObject().clear();
		
		//Jetzt erst wird der neue Wert auch mit "Entschluesselung" geholt
		String sDecrypted = objKernelTest.getParameter("TestGetParameter_Encrypted").getValue();
		stemp = StringZZZ.left(sDecrypted+"|","|");
		assertEquals("Test erfolgreich!", stemp);

	}catch(ExceptionZZZ ez){
		fail("An exception happend testing: " + ez.getDetailAllLast());
	}
}

/** void
 * Voraussetzung ist: Vorhandensein einer entsprechenden Modul-Konfigurations-.ini-Datei. Siehe testModuleExists()
* Lindhauer; 20.04.2006 07:49:38
 */
public void testGetParameterByModuleAlias(){
	
	//####################################
	//#### TESTS VORWEG
	String sModuleName = "TestModule";
	try{		
		File objFile = objKernelFGL.getFileConfigModuleOLDDIRECT(sModuleName);
		assertNotNull(objFile);
	}catch(ExceptionZZZ ez){
		fail("An exception happend testing: " + ez.getDetailAllLast());
	}	
	
	
	//Konfiguration im "Externen" Modul, d.h. in einer anderen Datei als der ApplicationKey
	String sModuleNameExtern = "TestModuleExtern";
	try{		
		File objFileExtern = objKernelFGL.getFileConfigModuleOLDDIRECT(sModuleNameExtern);
		assertNotNull(objFileExtern);
	}catch(ExceptionZZZ ez){
		fail("An exception happend testing: " + ez.getDetailAllLast());
	}	
	
	
	//######################################################
	try{
		//Konfiguration im "Lokalen" Module
		// In der Modulkonfiguration soll dieser Eintrag existieren
		String stemp = objKernelFGL.getParameterByModuleAlias(sModuleName, "testModuleProperty").getValue();
		stemp = StringZZZ.left(stemp+"|","|");
		assertEquals("Expected 'TestModuleValue' as a value of property 'testModuleProperty'. Configured in the Module 'TestModule' of the Application 'FGL'", "TestModuleValue" , stemp);
	}catch(ExceptionZZZ ez){
		fail("An exception happend testing: " + ez.getDetailAllLast());
	}	
	
	//+++++++++++++++++++++++++++++++++++++
	try{
		//a) Parameter per Methode holen
		String stemp = objKernelFGL.getParameterByModuleAlias(sModuleNameExtern, "testModulePropertyExtern").getValue();
		stemp = StringZZZ.left(stemp+"|","|");
		assertEquals("TestModuleValueExtern", stemp);
	}catch(ExceptionZZZ ez){
		fail("An exception happend testing: " + ez.getDetailAllLast());
	}				
}

public void testGetParameterByProgramAlias(){
	// !!!ACHTUNG / VORSICHT, DIE WERTE KÖNNEN AUS EINEM CACHE KOMMEN, darum vor jedem Test den Cache leeren!!!
	
	try{
		//A1) Übergabe als direkte Section testen
		String stemp = objKernelFGL.getParameterByProgramAlias("FGL!01_TestProg","testProgramProperty" ).getValue();
		stemp = StringZZZ.left(stemp+"|","|");
		assertEquals("Expected as a value of property 'testProgramProperty'. Configured in the 'TestModule' of the Application 'FGL'", "testwert local 4 program" , stemp);
	}catch(ExceptionZZZ ez){
		fail("An exception happend testing: " + ez.getDetailAllLast());
	}	
	
	try {
		int iClearedObjects = objKernelFGL.getCacheObject().clear();
		assertTrue(iClearedObjects>=1);
	}catch(ExceptionZZZ ez){
		fail("An exception happend testing: " + ez.getDetailAllLast());
	}	
		
		
		
	try{
		//A2) Übergabe als direkte Section testen
		String stemp = objKernelFGL.getParameterByProgramAlias("TestModule", "FGL!01_TestProg","testProgramProperty" ).getValue();
		stemp = StringZZZ.left(stemp+"|","|");
		assertEquals("Expected as a value of property 'testProgramProperty'. Configured in the 'TestModule' of the Application 'FGL'", "testwert local 4 program" , stemp);
	}catch(ExceptionZZZ ez){
		fail("An exception happend testing: " + ez.getDetailAllLast());
	}	
		

	//B1) Übergabe als Programname testen. 20061021 nun muss der Wert gefunden werden, auch wenn der Programalias ohne Systemnumber angegeben wird
			//!!! GROSS-/Keinschreibung ist NICHT relevant		
		 
	//Program nicht vorhanden Fall!!! D.h. in der TestModule-Datei gibt es das angegebene Program nicht
	try{		
		String stemp2 = objKernelFGL.getParameterByProgramAlias("TestModule", "testProgramNameNICHTVORHANDEN", "testProgramProperty").getValue(); 
		assertNull("No Value should have been found for the NICHTVORHANDEN program", stemp2);
	}catch(ExceptionZZZ ez){
		fail("An exception happend testing: " + ez.getDetailAllLast());
	}
	
	
	//Nicht vorhanden Fall!!! D.h. in der TestModule-Datei gibt es kein "testProgramName" mit der Property
	try{		
		String stemp2 = objKernelFGL.getParameterByProgramAlias("TestModule", "testProgramName", "testProgramPropertyNICHTVORHANDEN").getValue(); 
		assertNull("No Value should have been found for the NICHTVORHANDEN program", stemp2);
	}catch(ExceptionZZZ ez){
		fail("An exception happend testing: " + ez.getDetailAllLast());
	}
	
	
	//Vorhanden Fall. Hier ist also 'testProgramName' ein Parameter, für den ein Alias in [TestModule!01] definiert ist.
	//Reihenfolge des Sections! Der [TestProg] Eintrag muss hinter dem [FGL!01_TestProg] Eintrag
	try{		
		String stemp2 = objKernelFGL.getParameterByProgramAlias("TestModule", "testProgramName", "testProgramProperty4").getValue();
		stemp2 = StringZZZ.left(stemp2+"|","|");
		assertEquals("Expected as a value of property 'testProgramProperty4'. Configured in the 'TestModule' of the Application 'FGL'", "testwert4 local 4 program" , stemp2);
	}catch(ExceptionZZZ ez){
		fail("An exception happend testing: " + ez.getDetailAllLast());
	}	
	
	//Hier ist also 'testProgramName' ein Parameter, für den ein Alias in [FGL!01] und der Wert aber nur GLOBAL definiert ist.	
	try{		
		String stemp2 = objKernelFGL.getParameterByProgramAlias("TestModule", "testProgramName", "testProgramPropertyGLOBAL").getValue();
		stemp2 = StringZZZ.left(stemp2+"|","|");
		assertEquals("Expected as a value of property 'testProgramProperty'. Configured in the 'TestModule' of the Application 'FGL'", "testwert global 4 progname SOLL GEFUNDEN WERDEN" , stemp2);
	}catch(ExceptionZZZ ez){
		fail("An exception happend testing: " + ez.getDetailAllLast());
	}	
	
	
	}

public void testGetParameterByProgramAlias_ExternesModul() {
	//######################################################################
		//### TESTS IM "EXTERNEN MODUL"
		//### D.h. das Modul ist in einer anderen Datei definiert!!!
		//######################################################################
					
		try{
			//A1) Übergabe als direkte Section testen. Modulname, ProgramName und Systemnummer werden daraus gezogen.
			//    Der Programname wird dann sogar noch in einen Alias umgewandelt.
			String stemp = objKernelFGL.getParameterByProgramAlias("TestModuleExtern!01_TestProgramName","testProgramPropertyExtern4" ).getValue();
			stemp = StringZZZ.left(stemp,"|");			
			assertEquals("Expected as a value of property 'testProgramPropertyExtern4'. Configured in the 'TestModuleExtern' of the Application 'FGL'", "testwertextern by progalias GLOBAL" , stemp);
		}catch(ExceptionZZZ ez){
			fail("An exception happend testing: " + ez.getDetailAllLast());
		}	
		try {
			int iClearedObjects = objKernelFGL.getCacheObject().clear();
			assertTrue(iClearedObjects>=1);
		}catch(ExceptionZZZ ez){
			fail("An exception happend testing: " + ez.getDetailAllLast());
		}
			
		try{
			//A2) Übergabe als direkte Section testen
			String stemp = objKernelFGL.getParameterByProgramAlias("TestModuleExtern", "TestProgExtern","testProgramPropertyExtern4" ).getValue();
			stemp = StringZZZ.left(stemp+"|","|");
			assertEquals("Expected as a value of property 'testProgramPropertyExtern4'. Configured in the 'TestModuleExtern' of the Application 'FGL'", "testwertextern by progalias GLOBAL" , stemp);
		}catch(ExceptionZZZ ez){
			fail("An exception happend testing: " + ez.getDetailAllLast());
		}	
		
		//B1) Übergabe als Programname testen. 20061021 nun muss der Wert gefunden werden, auch wenn der Programalias ohne Systemnumber angegeben wird
		//!!! GROSS-/Keinschreibung ist NICHT relevant		
	 
		//Program nicht vorhanden Fall!!! D.h. in der TestModule-Datei gibt es das angegebene Program nicht
		try{		
			String stemp2 = objKernelFGL.getParameterByProgramAlias("TestModuleExtern", "testProgramNameNICHTVORHANDEN", "testProgramProperty").getValue();
			assertNull("No Value should have been found for the NICHTVORHANDEN program", stemp2);
		}catch(ExceptionZZZ ez){
			fail("An exception happend testing: " + ez.getDetailAllLast());
		}

		//Nicht vorhanden Fall!!! D.h. in der TestModule-Datei gibt es kein "testProgramName" mit der Property
		try{		
			String stemp2 = objKernelFGL.getParameterByProgramAlias("TestModuleExtern", "testProgramName", "testProgramPropertyNICHTVORHANDEN").getValue();			
			assertNull("No Value should have been found for the NICHTVORHANDEN program", stemp2);
		}catch(ExceptionZZZ ez){
			fail("An exception happend testing: " + ez.getDetailAllLast());
		}
			
		//Vorhanden Fall. Hier ist also 'testProgramName' ein Parameter, für den ein Alias in [TestModule!01] definiert ist.
		try{		
			String stemp2 = objKernelFGL.getParameterByProgramAlias("TestModuleExtern", "testProgramName", "testProgramPropertyExtern4").getValue();
			stemp2 = StringZZZ.left(stemp2+"|","|");
			assertEquals("Expected as a value of property 'testProgramPropertyExtern4'. Configured in the 'TestModuleExtern' of the Application 'FGL'", "testwertextern by progalias GLOBAL" , stemp2);
		}catch(ExceptionZZZ ez){
			fail("An exception happend testing: " + ez.getDetailAllLast());
		}	
		
		//Hier ist also 'testProgramName' ein Parameter, für den ein Alias in [FGL!01] und der Wert aber nur GLOBAL definiert ist.
		//Merke: Wird das Modul als Alias definiert, muss irgenwo auch der Modulkonfigurationsstring stehen: KernelConfigFileTestModuleAliasExtern, und wenn es eben in einer Section mit dem Modulalias selbst ist
		try{		
			String stemp2 = objKernelFGL.getParameterByProgramAlias("TestModuleAliasExtern", "testProgramName", "testProgramProperty5").getValue();
			assertNull(stemp2);//es heiss testProgramNameExtern. Mit dem anderen Programnamen soll nichts gefunden werden.
		}catch(ExceptionZZZ ez){
			fail("An exception happend testing: " + ez.getDetailAllLast());
		}	
				
		try{		
			String stemp2 = objKernelFGL.getParameterByProgramAlias("TestModuleAliasExtern", "testProgramNameExtern", "testProgramPropertyExtern5").getValue();
			stemp2 = StringZZZ.left(stemp2+"|","|");			
			assertEquals("Expected as a value of property 'testProgramPropertyExtern5'. Configured in the 'TestModule' of the Application 'FGL'", "TestModuleValueExtern by modulealias local SOLL GEFUNDEN WERDEN" , stemp2);
		}catch(ExceptionZZZ ez){
			fail("An exception happend testing: " + ez.getDetailAllLast());
		}	
}


public void testSearchAliasForProgram() {
	try {
		//TestProgramName=TestProg
		String sProgramAlias = objKernelFGL.searchAliasForProgram("TestProgramName");
		assertEquals("TestProg", sProgramAlias);
			
		String sProgramAliasUnavailable = objKernelFGL.searchAliasForProgram("TestProgramNameUNAVAILABLE");
		assertNull(sProgramAliasUnavailable);
		
		//Test mit Modul in externer Datei
		String sProgramAliasExtern = objKernelFGL.searchAliasForProgram("TestModuleExtern", "TestProgramName");
		assertEquals("TestProgExtern", sProgramAliasExtern);				
	} catch (ExceptionZZZ ez) {
		fail("Method throws an exception." + ez.getMessageLast());
	}
}

public void testSearchAliasForModule() {
	try {
		//Erst testen, dass auch kein Leerwert kommt
		String sModule = this.getClass().getName();//basic.zKernel.KernelZZZTest
		String sReturnAlias = objKernelFGL.searchAliasForModule(sModule);
		//System.out.println("TEST: "+sReturnAlias);//TestProg
		sReturnAlias = StringZZZ.left(sReturnAlias+"|", "|");//Damit das Setzen des Timestamps in der Property keinen Fehler erzeugt.
		assertEquals("TestProg", sReturnAlias);
		
		//+++ Externes Modul
		String sReturnAliasExtern = objKernelFGL.searchAliasForModule("TestModuleExtern");
		//System.out.println("TEST: "+sReturnAlias);//TestProg
		sReturnAliasExtern = StringZZZ.left(sReturnAliasExtern+"|", "|");//Damit das Setzen des Timestamps in der Property keinen Fehler erzeugt.
		assertEquals("TestModuleAliasExtern", sReturnAliasExtern);
		
	} catch (ExceptionZZZ ez) {
		fail("Method throws an exception." + ez.getMessageLast());
	}
	
}

public void testGetProgramAliasFor() {
	try {
		String sClassname = this.getClass().getName();
		IKernelConfigSectionEntryZZZ objEntryAlias = null;
		
		//Negativfall, d.h. in den FGL Sections ist diese Testklasse nicht vorhanden!							
		objEntryAlias = objKernelTest.getProgramAliasFor(sClassname);
		assertFalse(objEntryAlias.hasAnyValue());
	
		//Jetzt den Positivfall
		objEntryAlias = objKernelFGL.getProgramAliasFor(sClassname);
		assertTrue(objEntryAlias.hasAnyValue());
					
		String sAlias = objEntryAlias.getValue();
		sAlias = StringZZZ.left(sAlias+"|", "|");//Damit das Setzen des Timestamps in der Property keinen Fehler erzeugt.
		assertTrue(sAlias.equals("TestProg"));				
	} catch (ExceptionZZZ ez) {
		fail("Method throws an exception." + ez.getMessageLast());
	}
	
}

/** void, Test: Replacing an entry in a section of the ini-file
 * @author Fritz Lindhauer, 13.08.2022, 08:46:20
 */
public void testSetParameterByProgramAlias(){
	try {
		String sModule = this.getClass().getName();
		String sProgram = "TestProg";
		String sProperty = "testProgramProperty4";
		
		//############################################
		//### "NICHT EXTERNES" MODUL
		//############################################
		//Teste das "Parameter Holen" auch für das "nicht externe", d.h. über den aktuellen Klassennamen angegebene Test Modul
		
		//Erst testen, dass auch kein Leerwert kommt			
		IKernelConfigSectionEntryZZZ objEntry = objKernelFGL.getParameterByProgramAlias(sModule, sProgram, sProperty);
		assertTrue(objEntry.hasAnyValue());
		String sReturnSaved = objEntry.getValue();
		sReturnSaved = StringZZZ.left(sReturnSaved+"|", "|");//Damit das Setzen des Timestamps in der Property keinen Fehler erzeugt.
		assertEquals("testwert4 local 4 program",sReturnSaved);
		
		//Testvariante A: Jetzt wird der Wert aber aus dem Cache geholt
		sReturnSaved = StringZZZ.left(sReturnSaved+"|", "|");//STRING OHNE DEN TIMESTAMP WERT.
		String sValue = sReturnSaved + "|Timestamp: "+DateTimeZZZ.computeTimestamp();
		objKernelFGL.setParameterByProgramAlias(sModule, sProgram, sProperty, sValue, true);				
		objEntry = objKernelFGL.getParameterByProgramAlias(sModule, sProgram, sProperty);
		assertTrue(objEntry.hasAnyValue());		
		sReturnSaved = objEntry.getValue();
		//Vergleiche den String MIT der TIMESTAMP VARIANTE, also nicht: sReturnSaved = StringZZZ.left(sReturnSaved+"|", "|");//Damit das Setzen des Timestamps in der Property keinen Fehler erzeugt.
		assertEquals(sValue,sReturnSaved);		
	
		//Testvariante B: Den Cache bei jedem Schritt explizit leeren
		objKernelFGL.getCacheObject().clear();
		sReturnSaved = StringZZZ.left(sReturnSaved+"|", "|");//STRING OHNE DEN TIMESTAMP TEIL!!!
		sValue = sReturnSaved + "|Timestamp: "+DateTimeZZZ.computeTimestamp();
		objKernelFGL.setParameterByProgramAlias(sModule, sProgram, sProperty, sValue, true);
		
		objKernelFGL.getCacheObject().clear();
		objEntry = objKernelFGL.getParameterByProgramAlias(sModule, sProgram, sProperty);
		assertTrue(objEntry.hasAnyValue());		
		sReturnSaved = objEntry.getValue();
		//Vergleiche den String MIT der TIMESTAMP VARIANTE, also nicht: sReturnSaved = StringZZZ.left(sReturnSaved+"|", "|");//Damit das Setzen des Timestamps in der Property keinen Fehler erzeugt.
		assertEquals(sValue,sReturnSaved);	
		
	} catch (ExceptionZZZ ez) {
		fail("Method throws an exception." + ez.getMessageLast());
	}
}

/** void, Test: Replacing an entry in a section of the ini-file
 * @author Fritz Lindhauer, 13.08.2022, 08:46:20
 */
public void testSetParameterByProgramAlias_Encrypted(){
	try {
		TODOGOON20230304;					
		String sModule = this.getClass().getName();
		String sProgram = "TestProg";
		String sProperty = "testProgramPropertyEncrypted1";
		
		//############################################
		//### "NICHT EXTERNES" MODUL
		//############################################
		//Teste das "Parameter Holen" auch für das "nicht externe", d.h. über den aktuellen Klassennamen angegebene Test Modul
				
		//Erst testen, dass auch kein Leerwert kommt			
		IKernelConfigSectionEntryZZZ objEntry = objKernelFGL.getParameterByProgramAlias(sModule, sProgram, sProperty);
		assertTrue(objEntry.hasAnyValue());
		String sReturnSaved = objEntry.getValue();
		sReturnSaved = StringZZZ.left(sReturnSaved+"|", "|");//Damit das Setzen des Timestamps in der Property keinen Fehler erzeugt.
		assertEquals("testwert4 local 4 program",sReturnSaved);
		
		//Testvariante A: Jetzt wird der Wert aber aus dem Cache geholt
		sReturnSaved = StringZZZ.left(sReturnSaved+"|", "|");//STRING OHNE DEN TIMESTAMP WERT.
		String sValue = sReturnSaved + "|Timestamp: "+DateTimeZZZ.computeTimestamp();
		objKernelFGL.setParameterByProgramAlias(sModule, sProgram, sProperty, sValue, true);				
		objEntry = objKernelFGL.getParameterByProgramAlias(sModule, sProgram, sProperty);
		assertTrue(objEntry.hasAnyValue());		
		sReturnSaved = objEntry.getValue();
		//Vergleiche den String MIT der TIMESTAMP VARIANTE, also nicht: sReturnSaved = StringZZZ.left(sReturnSaved+"|", "|");//Damit das Setzen des Timestamps in der Property keinen Fehler erzeugt.
		assertEquals(sValue,sReturnSaved);		
	
		//Testvariante B: Den Cache bei jedem Schritt explizit leeren
		objKernelFGL.getCacheObject().clear();
		sReturnSaved = StringZZZ.left(sReturnSaved+"|", "|");//STRING OHNE DEN TIMESTAMP TEIL!!!
		sValue = sReturnSaved + "|Timestamp: "+DateTimeZZZ.computeTimestamp();
		objKernelFGL.setParameterByProgramAlias(sModule, sProgram, sProperty, sValue, true);
		
		objKernelFGL.getCacheObject().clear();
		objEntry = objKernelFGL.getParameterByProgramAlias(sModule, sProgram, sProperty);
		assertTrue(objEntry.hasAnyValue());		
		sReturnSaved = objEntry.getValue();
		//Vergleiche den String MIT der TIMESTAMP VARIANTE, also nicht: sReturnSaved = StringZZZ.left(sReturnSaved+"|", "|");//Damit das Setzen des Timestamps in der Property keinen Fehler erzeugt.
		assertEquals(sValue,sReturnSaved);	
		
	} catch (ExceptionZZZ ez) {
		fail("Method throws an exception." + ez.getMessageLast());
	}
}

/** void, Test: Replacing an entry in a section of the ini-file
 * @author Fritz Lindhauer, 13.08.2022, 08:46:20
 */
public void testSetParameterByProgramAlias_ExternesModule(){
	try {
		String sModuleExtern = "TestModuleExtern";
		
		String sProgram = "TestProgExtern";
		String sProperty = "testProgramPropertyExtern4";
		
		//##########################################
		//### EXTERNES MODUL
		//##########################################
		//Teste das "Parameter Holen" auch für oben angegebenes "externe" Test Modul
		
		//Erst testen, dass auch kein Leerwert kommt			
		IKernelConfigSectionEntryZZZ objEntry = objKernelFGL.getParameterByProgramAlias(sModuleExtern, sProgram, sProperty);
		assertTrue(objEntry.hasAnyValue());
		String sReturnSaved = objEntry.getValue();
		sReturnSaved = StringZZZ.left(sReturnSaved+"|", "|");//Damit das Setzen des Timestamps in der Property keinen Fehler erzeugt.
		assertEquals("testwertextern by progalias GLOBAL",sReturnSaved);
		
		//Testvariante A: Jetzt wird der Wert aber aus dem Cache geholt
		sReturnSaved = StringZZZ.left(sReturnSaved+"|", "|");//STRING OHNE DEN TIMESTAMP WERT.
		String sValue = sReturnSaved + "|Timestamp: "+DateTimeZZZ.computeTimestamp();
		objKernelFGL.setParameterByProgramAlias(sModuleExtern, sProgram, sProperty, sValue, true);				
		objEntry = objKernelFGL.getParameterByProgramAlias(sModuleExtern, sProgram, sProperty);
		assertTrue(objEntry.hasAnyValue());		
		sReturnSaved = objEntry.getValue();
		//sReturnSaved = StringZZZ.left(sReturnSaved+"|", "|");//Damit das Setzen des Timestamps in der Property keinen Fehler erzeugt.
		assertEquals(sValue,sReturnSaved);		
	
		//Testvariante B: Den Cache bei jedem Schritt explizit leeren
		objKernelFGL.getCacheObject().clear();
		sReturnSaved = StringZZZ.left(sReturnSaved+"|", "|");//STRING OHNE DEN TIMESTAMP TEIL!!!
		sValue = sReturnSaved + "|Timestamp: "+DateTimeZZZ.computeTimestamp();
		objKernelFGL.setParameterByProgramAlias(sModuleExtern, sProgram, sProperty, sValue, true);
		
		objKernelFGL.getCacheObject().clear();
		objEntry = objKernelFGL.getParameterByProgramAlias(sModuleExtern, sProgram, sProperty);
		assertTrue(objEntry.hasAnyValue());		
		sReturnSaved = objEntry.getValue();
		//sReturnSaved = StringZZZ.left(sReturnSaved+"|", "|");//Damit das Setzen des Timestamps in der Property keinen Fehler erzeugt.
		assertEquals(sValue,sReturnSaved);	
		
	} catch (ExceptionZZZ ez) {
		fail("Method throws an exception." + ez.getMessageLast());
	}
}


public void testGetParameterFromClass_ALS_SAMMLUNG_VERSCHIEDENER_METHODEN(){
	
	//#########################################################################
	//Erst mal die Konfiguration per Klassennamen sicherstellen
	String sClassname = this.getClass().getName();

	try{		
		File objFile = objKernelFGL.getFileConfigModuleOLDDIRECT(sClassname);
		assertNotNull(objFile);
				
		FileIniZZZ objFileConfigIni = objKernelFGL.getFileConfigModuleIni(sClassname);
		assertNotNull(objFileConfigIni);
		objFile = objFileConfigIni.getFileObject();
		
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
	
	//+++ STEHEN LASSEN AUS DOKUGRUENDEN
	//Hier wird derjetzt der GLOBALE Wert zurückgeliefert, das sollte frueher nicht sein...
	//Aktuell werden die Programnamen mit UNterstrich _ abgetrennt.	
//	try{
//		//b) Parameter aus dem Programm (das als Alias so aussieht wie der Klassenname) holen
//		String sAgentSection = objKernelFGL.getSystemKey() + "!" + sClassname;
//		String stemp2 = objKernelFGL.getParameterByProgramAlias(sClassname, sAgentSection, "TestParameter2FromClass").getValue(); 
//		assertEquals("TestValue2FromClass", stemp2);
//	}catch(ExceptionZZZ ez){
//		fail("An exception happend testing: " + ez.getDetailAllLast());
//	}	
	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	//TODOGOON20220912;//Neuer GITHUB TOKEN
	try{
		//b2) Nun soll der Klassenname ohne den Systemkey funktionieren
		String stemp4 = objKernelFGL.getParameterByProgramAlias(sClassname, sClassname, "TestParameter2FromClass").getValue();
		
		assertEquals("TestValue2FromClass SOLL GEFUNDEN WERDEN", stemp4);   //Womit der Eintrag ein anderer wäre als der mit "SystemKey" - spezifizierte
	}catch(ExceptionZZZ ez){
		fail("An exception happend testing: " + ez.getDetailAllLast());
	}	
		
	try{
		//C) "Verkürzte Parameter"
		//Wenn der Modulname und der Programname gleich sind, dann soll es moeglich sein ganz einfach nur den Programnamen und die gesuchte Property zu uebergeben
		String stemp6 = objKernelFGL.getParameterByProgramAlias(sClassname, "TestParameter1Abbreviated").getValue();
		
		assertEquals("TestValue1Abbreviated SOLL GEFUNDEN WERDEN", stemp6);
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
		ArrayList listaAlias = objKernelFGL.getFileConfigModuleAliasAll(true, true);
		assertNotNull("There is no alias configured and no module does exist (arraylist null case)", listaAlias);
		assertFalse("There is no alias configured and no module does exist (arraylist empty case)", listaAlias.isEmpty()==true);
		assertTrue("There was the correct configuration expected of the module 'TestModule'. Application 'TEST'", listaAlias.contains("TestModule"));
	}catch(ExceptionZZZ ez){
		fail("An exception happend testing: " + ez.getDetailAllLast());
	}
	
	try{
	//	//+++ Von den konfigurierten Modulen diejenigen, deren Konfigurationsdatei fehlt
		ArrayList listaAlias3 = objKernelTest.getFileConfigModuleAliasAll(false, true);
		assertNotNull("A configured module with missing configuraton file was expected (arraylist null case)", listaAlias3);
		assertFalse("A configured module with missing configuraton file was expected (arraylist empty case)", listaAlias3.isEmpty()==true);
		assertTrue("There was a missing configuration file expected. Application 'TEST'", listaAlias3.contains("NotExisting"));  //Das ist die Datei 'ZKernelConfigTestModuleNotExisting_test.ini'
	    assertFalse("There was only one missing configuration file expected. Application 'TEST", listaAlias3.size() > 1);
	}catch(ExceptionZZZ ez){
		fail("An exception happend testing: " + ez.getDetailAllLast());
	}
    	
	try{
		//+++ Von den in dem Kernel-Verzeichnis existierenden Moduldateien, diejenigen, deren Konfiguration in dieser "Hauptkernelkonfiguration" (noch) nicht erfolgt ist.
		ArrayList listaAlias2 = objKernelTest.getFileConfigModuleAliasAll(true, false);
		assertNotNull("There is no alias configured and no module does exist (arraylist null case)", listaAlias2);
		assertFalse("There is no file found wich is not configured (use a dummy configuration file at least. Starting the filename with 'ZKernelConfig ....' (arraylist empty case)", listaAlias2.isEmpty()==true);
		assertTrue("The configuration file for the module  'ExistingButNotConfigured' should exist. But was expected to be NOT configured in the applicationalias 'TEST'", listaAlias2.contains("ExistingButNotConfigured")); //Das KernelModul wird in der Applikation FGL konfiguriert als Section der Ini-Datei:  [FGL#01]
	}catch(ExceptionZZZ ez){
		fail("An exception happend testing: " + ez.getDetailAllLast());
	}
	
	try{
		//+++ Alle Module, sowohl aus der Konfiguration als auch aus dem Kernel-Verzeichnis 
		ArrayList listaAlias4 = objKernelTest.getFileConfigModuleAliasAll(false, false);
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
			FileIniZZZ objFileIniModule = objKernelFGL.searchModuleFileWithProgramAlias(sModule, sProgramOrSection);
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