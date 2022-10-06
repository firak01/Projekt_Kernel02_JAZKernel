package basic.zKernel.file.ini;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Vector;

import junit.framework.TestCase;
import basic.javagently.Stream;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractList.HashMapCaseInsensitiveZZZ;
import basic.zBasic.util.abstractList.HashMapExtendedZZZ;
import basic.zBasic.util.crypt.ICryptZZZ;
import basic.zBasic.util.crypt.ROTnnZZZ;
import basic.zBasic.util.crypt.ROTnumericZZZ;
import basic.zBasic.util.crypt.ROT13ZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.file.FileEasyZZZ;
import basic.zKernel.IKernelConfigSectionEntryZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernel.KernelConfigSectionEntryZZZ;
import basic.zKernel.KernelZZZ;
import basic.zKernel.file.ini.KernelZFormulaIniSolverZZZ;
import custom.zKernel.LogZZZ;
import custom.zKernel.file.ini.FileIniZZZ;

public class KernelExpressionIniSolverZZZTest extends TestCase {	
	private final static String strFILE_DIRECTORY_DEFAULT = new String("c:\\fglKernel\\KernelTest");
	private final static String strFILE_NAME_DEFAULT = new String("JUnit_KernelExpressionIniSolverZZZTest.ini");
	
	private File objFile;
	private IKernelZZZ objKernel;
	private FileIniZZZ objFileIniTest=null;
	
	/// +++ Die eigentlichen Test-Objekte	
	private KernelExpressionIniSolverZZZ objExpressionSolver;
	private KernelExpressionIniSolverZZZ objExpressionSolverInit;
	
	

	protected void setUp(){
		try {			
			//### Eine Beispieldatei. Merke: Die Einträge werden immer neu geschrieben und nicht etwa angehngt.
			//Merke: Erst wenn es überhaupt einen Test gibt, wird diese Datei erstellt
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
			objStreamFile.println("Testentry2=Testvalue2 local to be found");
			
			objStreamFile.println("[Section B]");
			objStreamFile.println("Testentry2=Testvalue2 global. This should not be found!");
			
			objStreamFile.println("[FGL_Section C]");
			objStreamFile.println("Testentry3=Testvalue3 global. This should not be found!");
			
			objStreamFile.println("[FGL!01_Section C]");
			objStreamFile.println("Testentry3=Testvalue3 local to be found");
						
			objStreamFile.println("[Section for testCompute]");
			objStreamFile.println("Formula1=Der dynamische Wert ist '<Z>[Section A]Testentry1</Z>'. FGL rulez.");
			objStreamFile.println("Formula2=Der dynamische Wert2 ist '<Z>[Section B]Testentry2</Z>'. FGL rulez.");
			objStreamFile.println("Formula3=Der dynamische Wert3 ist '<Z>[Section C]Testentry3</Z>'. FGL rulez.");
			
			objStreamFile.println("[Section for testComputeMathArguments]");
			objStreamFile.println("WertA=4");
			objStreamFile.println("WertB=5");
			
			objStreamFile.println("[Section for testComputeMathValue]");
			objStreamFile.println("Formula1=Der dynamische Wert ist '<Z><Z:math><Z:val>2</Z:val><Z:val>3</Z:val></Z:math></Z>'. FGL rulez.");
			objStreamFile.println("Formula2=Der dynamische Wert2 ist '<Z><Z:math><Z:val>2</Z:val><Z:op>*</Z:op><Z:val>[Section for testComputeMathArguments]WertA</Z:val></Z:math></Z>'. FGL rulez.");		
			
			objStreamFile.println("[Section for testComputeMath]");
			objStreamFile.println("Formula1=Der dynamische Wert ist '<Z><Z:math><Z:val>2</Z:val><Z:op>*</Z:op><Z:val>3</Z:val></Z:math></Z>'. FGL rulez.");
			objStreamFile.println("Formula2=Der dynamische Wert2 ist '<Z><Z:math><Z:val>2</Z:val><Z:op>*</Z:op><Z:val>[Section for testComputeMathArguments]WertA</Z:val></Z:math></Z>'. FGL rulez.");
			objStreamFile.println("Formula3=Der dynamische Wert3 ist '<Z><Z:math><Z:val>[Section for testComputeMathArguments]WertB</Z:val><Z:op>*</Z:op><Z:val>[Section for testComputeMathArguments]WertA</Z:val></Z:math></Z>'. FGL rulez.");
			 
			
			objStreamFile.println("[Section for testComputePathWithMath]");
			objStreamFile.println("Formula1=<Z>[Section for testComputeMath]Formula1</Z>");
			objStreamFile.println("Formula2=<Z>[Section for testComputeMath]Formula2</Z>");
			objStreamFile.println("Formula3=<Z>[Section for testComputeMath]Formula3</Z>");
			
			objStreamFile.println("[Section for testComputeMath NOT EXACTMATCH]");
			objStreamFile.println("Formula1=Der dynamische Wert ist '<Z><z:Math><Z:VAL>6</Z:val><Z:oP>+</Z:op><Z:val>7</Z:val></Z:math></Z>'. FGL rulez.");
			 
			
			objStreamFile.println("[Section for testComputeMathArguments FLOAT]");
			objStreamFile.println("WertA_float=4.0");
			objStreamFile.println("WertB_float=5.0");
			objStreamFile.println("[Section for testComputeMath FLOAT]");
			objStreamFile.println("Formula1=Der dynamische Wert ist '<Z><z:Math><Z:VAL>[Section for testComputeMathArguments FLOAT]WertA_float</Z:val><Z:oP>*</Z:op><Z:val>[Section for testComputeMathArguments FLOAT]WertB_float</Z:val></Z:math></Z>'. FGL rulez.");
			
			objStreamFile.println("[Section for testComputeMathVariable FLOAT]");
			objStreamFile.println("WertB_float=<Z><z:Var>myTestVariableFloat</z:Var></z>");
			
			//Beachte Variablen können wie INI-Path auch ausserhalb einer MATH - Anweisung gelten.
			objStreamFile.println("[Section for testPassVariable]");
			objStreamFile.println("Formula1=<Z>Der dynamische Wert ist '<z:Var>myTestVariableString</z:Var>'. FGL rulez.</Z>");
			objStreamFile.println("Formula2=Der dynamische Wert ist '<Z><z:Math><Z:VAL>[Section for testComputeMathArguments FLOAT]WertA_float</Z:val><Z:oP>*</Z:op><Z:val><Z:Var>myTestVariableFloat</z:Var></Z:val></Z:math></Z>'. FGL rulez.");
			objStreamFile.println("Formula3=Der dynamische Wert ist '<Z><z:Math><Z:VAL>[Section for testComputeMathArguments FLOAT]WertA_float</Z:val><Z:oP>*</Z:op><Z:val>[Section for testComputeMathVariable FLOAT]WertB_float</Z:val></Z:math></Z>'. FGL rulez.");
			
			
			//20210707 Tests für die Arbeit mit JSON Strings
			//Merke:			
			//Gib den JSON-Hashmap-Wert so an: {"DEBUGUI_PANELLABEL_ON":true} Merke: Intern hier eine HashMap String, Boolean Das ist aber nur sinnvoll bei der FLAG übergabe, da weiss man, dass der Wert Boolean ist.
			//                           also: NavigatorContentJson=<JSON>{"UIText01":"TESTWERT2DO2JSON01","UIText02":"TESTWERT2DO2JSON02"}</JSON>
			//Gib den JSON-Array-Wert so an: {"wert1","wert2"}
			objStreamFile.println("[Section for testJsonHashmap]");
			objStreamFile.println("Map1="+ KernelJsonMapIniSolverZZZTest.sEXPRESSION_JSONMAP01_DEFAULT);
				
			objStreamFile.println("[Section for testJsonArraylist]");
			objStreamFile.println("Array1="+ KernelJsonArrayIniSolverZZZTest.sEXPRESSION_JSONARRAY01_DEFAULT);
			
			
			//20220926 Tests für die Arbeit mit verschluesselten / encrypted Werten
			String sValue = "abcde"; int iKeyNumber=5; String sCharacterPool="?! abcdefghijklmnopqrstuvwxyz";
			String sFlagNumeric = ICryptZZZ.FLAGZ.USENUMERIC.name();
			String sFlagUppercase = ICryptZZZ.FLAGZ.USEUPPERCASE.name();
			String sEncrypted = ROT13ZZZ.encryptIt(sValue);
			objStreamFile.println("[Section for testEncrypted]");
			objStreamFile.println("WertA="+sValue);
			objStreamFile.println("WertAencrypted=<Z><Z:Encrypted><Z:Cipher>ROT13</Z:Cipher><Z:Code>"+sEncrypted+"</Z:Code></Z:Encrypted></Z>");
			
			sEncrypted = ROTnumericZZZ.encrypt(sValue, iKeyNumber, true);
			objStreamFile.println("WertB="+sValue);
			objStreamFile.println("WertBencrypted=<Z><Z:Encrypted><Z:Cipher>ROTnumeric</Z:Cipher><z:KeyNumber>"+iKeyNumber+"</z:KeyNumber><Z:FlagControl>"+sFlagNumeric+"</Z:FlagControl><Z:Code>"+sEncrypted+"</Z:Code></Z:Encrypted></Z>");
			
			sEncrypted = ROTnnZZZ.encrypt(sValue, sCharacterPool, iKeyNumber, true);
			objStreamFile.println("WertC="+sValue);
			objStreamFile.println("WertCencrypted=<Z><Z:Encrypted><Z:Cipher>ROTnn</Z:Cipher><z:KeyNumber>"+iKeyNumber+"</z:KeyNumber><z:CharacterPool>"+sCharacterPool+"</z:CharacterPool><z:FlagControl>"+sFlagUppercase+"</Z:FlagControl><Z:Code>"+sEncrypted+"</Z:Code></Z:Encrypted></Z>");
			
			
			objFile = new File(sFilePathTotal);		
			
			
			//#################
			objKernel = new KernelZZZ("FGL", "01", "test", "ZKernelConfigKernel_test.ini",(String)null);
			objFileIniTest = new FileIniZZZ(objKernel,  objFile, (String[]) null);
			
			//#### Ein init TestObjekt
			String[] saFlagInit = {"init"};
			objExpressionSolverInit = new KernelExpressionIniSolverZZZ(objKernel, objFileIniTest, saFlagInit);
			
			String[] saFlag = {""};
			objExpressionSolver = new KernelExpressionIniSolverZZZ(objKernel, objFileIniTest, saFlag);
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		} 
		catch (FileNotFoundException e) {			
			e.printStackTrace();
		} catch (IOException e) {			
			e.printStackTrace();
		}		
	}//END setup
	
	public void testFlagHandling(){
		try{							
			assertTrue(objExpressionSolverInit.getFlag("init")==true);
			assertFalse(objExpressionSolver.getFlag("init")==true); //Nun wäre init falsch
			
			String[] saFlag = objExpressionSolver.getFlagZ();
			assertTrue(saFlag.length==9);
			
			boolean bFlagAvailable = objExpressionSolver.setFlag("usejson", false); //Ansonsten wird der Wert sofort ausgerechnet
			assertTrue("Das Flag 'usejson' sollte zur Verfügung stehen.", bFlagAvailable);
			
			bFlagAvailable = objExpressionSolver.setFlag("usejson_array", false); //Ansonsten wird der Wert sofort ausgerechnet
			assertTrue("Das Flag 'usejson_array' sollte zur Verfügung stehen.", bFlagAvailable);
			
			bFlagAvailable = objExpressionSolver.setFlag("usejson_map", false); //Ansonsten wird der Wert sofort ausgerechnet
			assertTrue("Das Flag 'usejson_map' sollte zur Verfügung stehen.", bFlagAvailable);
			
			bFlagAvailable = objExpressionSolver.setFlag("useencryption", false); //Ansonsten wird der Wert sofort ausgerechnet
			assertTrue("Das Flag 'useencryption' sollte zur Verfügung stehen.", bFlagAvailable);
			
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}
	
	public void testCompute() {
		try {					
			String sFlagUseExpression = IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION.name();
			
			boolean bFlagAvailable = objExpressionSolver.setFlag(sFlagUseExpression, false); //Ansonsten wird der Wert sofort ausgerechnet
			assertTrue("Das Flag '"+sFlagUseExpression+"' sollte zur Verfügung stehen.", bFlagAvailable);
			
			
			
			//#########################################################
			//#### SECTION A ########################################## 
			//+++ Anwenden der ersten Formel, ohne Berechnung
			objExpressionSolver.setFlag(sFlagUseExpression, false); //Ansonsten wird der Wert sofort ausgerechnet
			String sLineWithExpression = objFileIniTest.getPropertyValue("Section for testCompute", "Formula1").getValue();
			assertEquals("Der dynamische Wert ist '<Z>[Section A]Testentry1</Z>'. FGL rulez.",sLineWithExpression);
			
			String sLineWithValue = objFileIniTest.getPropertyValue("Section A", "Testentry1").getValue();
			assertEquals("Testvalue1 to be found",sLineWithValue);
			
			//+++ Anwenden der ersten Formel, mit Berechnung
			IKernelConfigSectionEntryZZZ objSectionEntry = new KernelConfigSectionEntryZZZ();
			objExpressionSolver.setFlag(sFlagUseExpression, true); 
			objExpressionSolver.setFlag(IKernelZFormulaIniSolverZZZ.FLAGZ.USEFORMULA.name(),true);
			int iReturn = objExpressionSolver.compute(sLineWithExpression, objSectionEntry);
			assertTrue(iReturn==1);
			String sValue = objSectionEntry.getValue();
			assertEquals("Der dynamische Wert ist 'Testvalue1 to be found'. FGL rulez.", sValue);
			
			objFileIniTest.setFlag(sFlagUseExpression, true);
			objFileIniTest.setFlag(IKernelZFormulaIniSolverZZZ.FLAGZ.USEFORMULA.name(),true);
			String sExpression = objFileIniTest.getPropertyValue("Section for testCompute", "Formula1").getValue();
			assertEquals("Der dynamische Wert ist 'Testvalue1 to be found'. FGL rulez.",sExpression);
		
			
			//#########################################################
			//#### SECTION B ##########################################
			//#### HIER GIBT ES EINEN WERT FUER DIE SYSTEMNUMBER    ###
			
//			objExpressionSolver.setFlag(sFlagUseExpression, false);
//			boolean btemp = objExpressionSolver.getFlag(sFlagUseExpression);
//			assertFalse(btemp);
			
			//+++ Anwenden der zweiten Formel, ohne Berechnung
			objFileIniTest.setFlag(sFlagUseExpression, false);
			String sLineWithExpression2 = objFileIniTest.getPropertyValue("Section for testCompute", "Formula2").getValue();
			assertEquals("Der dynamische Wert2 ist '<Z>[Section B]Testentry2</Z>'. FGL rulez.",sLineWithExpression2);
			
			String sLineWithValue2 = objFileIniTest.getPropertyValue("Section B", "Testentry2").getValue();
			assertEquals("Testvalue2 local to be found",sLineWithValue2);
			
			//+++ Anwenden der zweiten Formel, mit Berechnung
			IKernelConfigSectionEntryZZZ objSectionEntry2 = new KernelConfigSectionEntryZZZ();
			objExpressionSolver.setFlag(sFlagUseExpression, true); 
			objExpressionSolver.setFlag(IKernelZFormulaIniSolverZZZ.FLAGZ.USEFORMULA.name(),true);
			int iReturn2 = objExpressionSolver.compute(sLineWithExpression2, objSectionEntry2);
			assertTrue(iReturn2==1);
			String sValue2 = objSectionEntry2.getValue();
			assertEquals("Der dynamische Wert2 ist 'Testvalue2 local to be found'. FGL rulez.", sValue2 );
			
			objFileIniTest.setFlag("useExpression", true);
			objFileIniTest.setFlag(IKernelZFormulaIniSolverZZZ.FLAGZ.USEFORMULA.name(),true);
			String sExpression2 = objFileIniTest.getPropertyValue("Section for testCompute", "Formula2").getValue();
			assertEquals("Der dynamische Wert2 ist 'Testvalue2 local to be found'. FGL rulez.",sExpression2);
		
			//#########################################################
			//#### SECTION C ##########################################
			//#### HIER GIBT ES EINEN WERT FUER DIE SYSTEMNUMBER, anders deklariert    ###
			
//			objExpressionSolver.setFlag(sFlagUseExpression, false);
//			boolean btemp = objExpressionSolver.getFlag(sFlagUseExpression);
//			assertFalse(btemp);
			
			//+++ Anwenden der zweiten Formel, ohne Berechnung
			objFileIniTest.setFlag(sFlagUseExpression, false);
			String sLineWithExpression3 = objFileIniTest.getPropertyValue("Section for testCompute", "Formula3").getValue();
			assertEquals("Der dynamische Wert3 ist '<Z>[Section C]Testentry3</Z>'. FGL rulez.",sLineWithExpression3);
			
			String sLineWithValue3 = objFileIniTest.getPropertyValue("Section C", "Testentry3").getValue();
			assertEquals("Testvalue3 local to be found",sLineWithValue3);
			
			//+++ Anwenden der zweiten Formel, mit Berechnung
			IKernelConfigSectionEntryZZZ objSectionEntry3 = new KernelConfigSectionEntryZZZ();
			objExpressionSolver.setFlag(sFlagUseExpression, true); 
			objExpressionSolver.setFlag(IKernelZFormulaIniSolverZZZ.FLAGZ.USEFORMULA.name(),true);
			int iReturn3 = objExpressionSolver.compute(sLineWithExpression3, objSectionEntry3);
			assertTrue(iReturn3==1);
			String sValue3 = objSectionEntry3.getValue();
			assertEquals("Der dynamische Wert3 ist 'Testvalue3 local to be found'. FGL rulez.", sValue3 );
			
			objFileIniTest.setFlag("useExpression", true);
			objFileIniTest.setFlag(IKernelZFormulaIniSolverZZZ.FLAGZ.USEFORMULA.name(),true);
			String sExpression3 = objFileIniTest.getPropertyValue("Section for testCompute", "Formula3").getValue();
			assertEquals("Der dynamische Wert3 ist 'Testvalue3 local to be found'. FGL rulez.",sExpression3);
		
			
			
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}
	
	/** void, Test: Reading an entry in a section of the ini-file
	* Lindhauer; 22.04.2006 12:54:32
	 */
	public void testComputeHashMap(){

		try {					
			boolean bFlagAvailable = objExpressionSolver.setFlag("usejson", false); //Ansonsten wird der Wert sofort ausgerechnet
			assertTrue("Das Flag 'usejson' sollte zur Verfügung stehen.", bFlagAvailable);
			
			//Anwenden der ersten Formel, ohne Berechnung			
			String sExpression = objFileIniTest.getPropertyValue("Section for testJsonHashmap", "Map1").getValue();
			assertEquals(KernelJsonMapIniSolverZZZTest.sEXPRESSION_JSONMAP01_DEFAULT,sExpression);
			
			//Berechne die erste Formel, DIRECT
			IKernelConfigSectionEntryZZZ objSectionEntry = new KernelConfigSectionEntryZZZ();
			bFlagAvailable = objExpressionSolver.setFlag("useexpression", true);
			assertTrue("Das Flag 'useexpression' sollte zur Verfügung stehen.", bFlagAvailable);
			bFlagAvailable = objExpressionSolver.setFlag("usejson", true);
			assertTrue("Das Flag 'usejson' sollte zur Verfügung stehen.", bFlagAvailable);
			bFlagAvailable = objExpressionSolver.setFlag("usejson_map", true);
			assertTrue("Das Flag 'usejson_map' sollte zur Verfügung stehen.", bFlagAvailable);
			
			int iReturn = objExpressionSolver.compute(sExpression, objSectionEntry);
			assertTrue(iReturn==6);
			
			HashMap<String,String> hm = objSectionEntry.getValueHashMap();
			assertNotNull(hm);
			String sValue01 = hm.get("UIText01");
			assertTrue(sValue01.equals("TESTWERT2DO2JSON01"));			
			assertFalse(objSectionEntry.getValue().equals(""));//Auch wenn es nur ein Debug-String ist, so ist er immer verändert.
			
			
			//NUN INDIREKT IM INI-OBJEKT TESTEN
			bFlagAvailable = objFileIniTest.setFlag("useexpression", true); //Ansonsten wird der Wert sofort ausgerechnet
			assertTrue("Das Flag 'useexpression' sollte zur Verfügung stehen.", bFlagAvailable);
			bFlagAvailable = objFileIniTest.setFlag("usejson", true); //Ansonsten wird der Wert sofort ausgerechnet
			assertTrue("Das Flag 'usejson' sollte zur Verfügung stehen.", bFlagAvailable);
			bFlagAvailable = objFileIniTest.setFlag("usejson_map", true); //Ansonsten wird der Wert sofort ausgerechnet
			assertTrue("Das Flag 'usejson_map' sollte zur Verfügung stehen.", bFlagAvailable);
			
			IKernelConfigSectionEntryZZZ objFileEntry = objFileIniTest.getPropertyValue("Section for testJsonHashmap", "Map1");
			assertNotNull(objFileEntry);
			assertTrue(objFileEntry.isJson());
			assertTrue(objFileEntry.isJsonMap());
			
			HashMap<String,String> hmFile = objFileEntry.getValueHashMap();
			assertNotNull(hmFile);
			String sValueFile01 = hmFile.get("UIText01");
			assertTrue(sValueFile01.equals("TESTWERT2DO2JSON01"));
															
			
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}		
	}
	
	/** void, Test: Reading an entry in a section of the ini-file
	* Lindhauer; 22.04.2006 12:54:32
	 */
	public void testComputeEncrypted(){

		try {					
			boolean bFlagAvailable = objExpressionSolver.setFlag(IKernelEncryptionIniSolverZZZ.FLAGZ.USEENCRYPTION.name(), false); //Ansonsten wird der Wert sofort ausgerechnet
			assertTrue("Das Flag 'useencryption' sollte zur Verfügung stehen.", bFlagAvailable);
			
			//Anwenden der ersten Formel, ohne Berechnung			
			String sExpression = objFileIniTest.getPropertyValue("Section for testEncrypted", "WertAencrypted").getValue();
			assertEquals(KernelEncryptionIniSolverZZZTest.sEXPRESSION_ENCRYPTION01_DEFAULT,sExpression);
			
			//Berechne die erste Formel, DIRECT
			IKernelConfigSectionEntryZZZ objSectionEntry = new KernelConfigSectionEntryZZZ();
			bFlagAvailable = objExpressionSolver.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION.name(), true);
			assertTrue("Das Flag 'useexpression' sollte zur Verfügung stehen.", bFlagAvailable);
			bFlagAvailable = objExpressionSolver.setFlag(IKernelEncryptionIniSolverZZZ.FLAGZ.USEENCRYPTION.name(), true);
			assertTrue("Das Flag 'usejson' sollte zur Verfügung stehen.", bFlagAvailable);
						
			int iReturn = objExpressionSolver.compute(sExpression, objSectionEntry);
			assertTrue(iReturn==10); //10 für die Encryption
									
			bFlagAvailable = objExpressionSolver.setFlag(IKernelZFormulaIniSolverZZZ.FLAGZ.USEFORMULA.name(), true);
			assertTrue("Das Flag 'useformula' sollte zur Verfügung stehen.", bFlagAvailable);				
			iReturn = objExpressionSolver.compute(sExpression, objSectionEntry);
			assertTrue(iReturn==11); //10 für die Encryption  PLUS 1 für die Formelauswertung als String
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}		
	}
	
	
	/** void, Test: Reading an entry in a section of the ini-file
	* Lindhauer; 22.04.2006 12:54:32
	 */
	public void testComputeArrayList(){
		//TODOGOON; //20210728 
		/*
		try {	
					
			boolean bFlagAvailable = objExpressionSolver.setFlag("usejson", false); //Ansonsten wird der Wert sofort ausgerechnet
			assertTrue("Das Flag 'usejson' sollte zur Verfügung stehen.", bFlagAvailable);
			
			//Anwenden der ersten Formel, ohne Berechnung			
			String sExpression = objFileIniTest.getPropertyValue("Section for testJsonArraylist", "Array1").getValue();
			assertEquals(KernelJsonArrayIniSolverZZZTest.sEXPRESSION_JSONARRAY01_DEFAULT,sExpression);
			
			//Berechne die erste Formel
			objExpressionSolver.setFlag("usejson", true);
			objExpressionSolver.setFlag("usejson_array", true);
			String sValue = objExpressionSolver.compute(sExpression);//compute gibt nur einen DebugString zurück
			assertNotNull(sValue);
			assertFalse(sValue.equals(KernelJsonArrayIniSolverZZZTest.sEXPRESSION_JSONARRAY01_DEFAULT));//Auch wenn es nur ein Debug-String ist, so ist er immer verändert.
			
			bFlagAvailable = objFileIniTest.setFlag("usejson", true); //Ansonsten wird der Wert sofort ausgerechnet
			assertTrue("Das Flag 'usejson' sollte zur Verfügung stehen.", bFlagAvailable);
			bFlagAvailable = objFileIniTest.setFlag("usejson_array", true); //Ansonsten wird der Wert sofort ausgerechnet
			assertTrue("Das Flag 'usejson_array' sollte zur Verfügung stehen.", bFlagAvailable);
			
			IKernelConfigSectionEntryZZZ objEntry = objFileIniTest.getPropertyValue("Section for testJsonArraylist", "Array1");
			assertNotNull(objEntry);
			assertTrue(objEntry.isJson());
			assertTrue(objEntry.isJsonArray());
			
			ArrayList<String> als = objEntry.getValueArrayList();
			assertNotNull(als);
			String sValue01 = als.get(0);
			assertTrue(sValue01.equals("TESTWERT2DO2JSON01"));
			
			
			
			
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}
		*/
	}
	
		
	public void testIsExpression() {
		/* TODOGOON
//		try {			
			String sExpression = "<Z>bin kein JSON</Z>";
			boolean bValue = objExpressionSolver.isExpression(sExpression);
			assertFalse(bValue);
		
		
			sExpression = "<JSON>kljkljlkjklj</JSON>";
			bValue = objExpressionSolver.isExpression(sExpression);
			assertTrue(bValue);
			
			//TODOGOON: DIE BERECHUNG ERFOLGT ANHAND DER KONKRETEN ERSTELLENT DATEI
//			sExpression = sEXPRESSION01_DEFAULT;
//			bValue = objExpressionSolver.isExpression(sExpression);
//			assertTrue(bValue);
			
//		} catch (ExceptionZZZ ez) {
//			fail("Method throws an exception." + ez.getMessageLast());
//		}
 * */ 
	}
	
}//END class
	
