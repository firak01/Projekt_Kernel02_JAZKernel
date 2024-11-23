package basic.zKernel.file.ini;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.xmlbeans.impl.store.Path;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedTestCaseZZZ;
import basic.zBasic.util.crypt.code.ICharacterPoolUserZZZ;
import basic.zBasic.util.crypt.code.IROTUserZZZ;
import basic.zBasic.util.crypt.code.ROT13ZZZ;
import basic.zBasic.util.crypt.code.ROTnnZZZ;
import basic.zBasic.util.crypt.code.ROTnumericZZZ;
import basic.zBasic.util.datatype.calling.ReferenceZZZ;
import basic.zBasic.util.datatype.character.CharacterExtendedZZZ;
import basic.zBasic.util.datatype.xml.XmlUtilZZZ;
import basic.zBasic.util.file.FileEasyZZZ;
import basic.zBasic.util.machine.EnvironmentZZZ;
import basic.zBasic.util.stream.IStreamZZZ;
import basic.zBasic.util.stream.StreamZZZ;
import basic.zKernel.IKernelConfigSectionEntryZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernel.KernelZZZ;
import basic.zKernel.config.KernelConfigSectionEntryUtilZZZ;
import basic.zKernel.flag.util.FlagZFassadeZZZ;
import custom.zKernel.file.ini.FileIniZZZ;
import junit.framework.TestCase;

public class KernelExpressionIniHandlerZZZTest extends TestCase {	
	protected final static String sEXPRESSION_Expression01_DEFAULT = "Der dynamische Wert ist '<Z>[Section A]Testentry1</Z>'. FGL rulez.";
	protected final static String sEXPRESSION_Expression01_SOLVED = "Der dynamische Wert ist 'Testvalue1 to be found'. FGL rulez.";
	
		
	private File objFile;
	private IKernelZZZ objKernel;
	private FileIniZZZ objFileIniTest=null;
	
	/// +++ Die eigentlichen Test-Objekte	
	private KernelExpressionIniHandlerZZZ objExpressionHandler;
	private KernelExpressionIniHandlerZZZ objExpressionHandlerInit;
	
	

	protected void setUp(){
		try {			
//			//### Eine Beispieldatei. Merke: Die Einträge werden immer neu geschrieben und nicht etwa angehngt.
//			//Merke: Erst wenn es überhaupt einen Test gibt, wird diese Datei erstellt
//			String sFilePathTotal = null;		
//			if(FileEasyZZZ.exists(strFILE_DIRECTORY_DEFAULT)){
//				sFilePathTotal = FileEasyZZZ.joinFilePathName(strFILE_DIRECTORY_DEFAULT, strFILE_NAME_DEFAULT );
//			}else{
//				//Eclipse Worspace
//				File f = new File("");
//			    String sPathEclipse = f.getAbsolutePath();
//			    System.out.println("Path for Kernel Directory Default does not exist. Using workspace absolut path: " + sPathEclipse);
//				
//			    sFilePathTotal = FileEasyZZZ.joinFilePathName(sPathEclipse, strFILE_NAME_DEFAULT );				
//			}
//			
//			IStreamZZZ objStreamFile = new StreamZZZ(sFilePathTotal, 1);  //This is not enough, to create the file			
//			objStreamFile.println(";This is a temporarily test file for FileIniZZZTest.");      //Now the File is created. This is a comment line
//			objStreamFile.println(";This file will be newly created by the setUp()-method of this JUnit Test class, every time before a testMethod will be invoked.");
//			objStreamFile.println("#This is another commentline");
//			objStreamFile.println("[Section A]");
//			objStreamFile.println("Testentry1=Testvalue1 to be found");			
//			objStreamFile.println("TestentryDymmy=nothing");
//						
//			objStreamFile.println("[Section B!01]");
//			objStreamFile.println("Testentry2=Testvalue2 local to be found");
//			
//			objStreamFile.println("[Section B]");
//			objStreamFile.println("Testentry2=Testvalue2 global. This should not be found!");
//			
//			objStreamFile.println("[FGL_Section C]");
//			objStreamFile.println("Testentry3=Testvalue3 global. This should not be found!");
//			
//			objStreamFile.println("[FGL!01_Section C]");
//			objStreamFile.println("Testentry3=Testvalue3 local to be found");
//						
//			objStreamFile.println("[Section for testCompute]");
//			objStreamFile.println("Formula1=Der dynamische Wert ist '<Z>[Section A]Testentry1</Z>'. FGL rulez.");
//			objStreamFile.println("Formula2=Der dynamische Wert2 ist '<Z>[Section B]Testentry2</Z>'. FGL rulez.");
//			objStreamFile.println("Formula3=Der dynamische Wert3 ist '<Z>[Section C]Testentry3</Z>'. FGL rulez.");
//							
//			objStreamFile.println("[Section for testComputeMathArguments]");
//			objStreamFile.println("WertA=4");
//			objStreamFile.println("WertB=5");
//			
//			objStreamFile.println("[Section for testComputeMathValue]");
//			objStreamFile.println("Formula1=Der dynamische Wert ist '<Z><Z:math><Z:val>2</Z:val><Z:val>3</Z:val></Z:math></Z>'. FGL rulez.");
//			objStreamFile.println("Formula2=Der dynamische Wert2 ist '<Z><Z:math><Z:val>2</Z:val><Z:op>*</Z:op><Z:val>[Section for testComputeMathArguments]WertA</Z:val></Z:math></Z>'. FGL rulez.");		
//			
//			objStreamFile.println("[Section for testComputeMath]");
//			objStreamFile.println("Formula1=Der dynamische Wert ist '<Z><Z:math><Z:val>2</Z:val><Z:op>*</Z:op><Z:val>3</Z:val></Z:math></Z>'. FGL rulez.");
//			objStreamFile.println("Formula2=Der dynamische Wert2 ist '<Z><Z:math><Z:val>2</Z:val><Z:op>*</Z:op><Z:val>[Section for testComputeMathArguments]WertA</Z:val></Z:math></Z>'. FGL rulez.");
//			objStreamFile.println("Formula3=Der dynamische Wert3 ist '<Z><Z:math><Z:val>[Section for testComputeMathArguments]WertB</Z:val><Z:op>*</Z:op><Z:val>[Section for testComputeMathArguments]WertA</Z:val></Z:math></Z>'. FGL rulez.");
//			 
//			
//			objStreamFile.println("[Section for testComputePathWithMath]");
//			objStreamFile.println("Formula1=<Z>[Section for testComputeMath]Formula1</Z>");
//			objStreamFile.println("Formula2=<Z>[Section for testComputeMath]Formula2</Z>");
//			objStreamFile.println("Formula3=<Z>[Section for testComputeMath]Formula3</Z>");
//			
//			objStreamFile.println("[Section for testComputeMath NOT EXACTMATCH]");
//			objStreamFile.println("Formula1=Der dynamische Wert ist '<Z><z:Math><Z:VAL>6</Z:val><Z:oP>+</Z:op><Z:val>7</Z:val></Z:math></Z>'. FGL rulez.");
//			 
//			
//			objStreamFile.println("[Section for testComputeMathArguments FLOAT]");
//			objStreamFile.println("WertA_float=4.0");
//			objStreamFile.println("WertB_float=5.0");
//			objStreamFile.println("[Section for testComputeMath FLOAT]");
//			objStreamFile.println("Formula1=Der dynamische Wert ist '<Z><z:Math><Z:VAL>[Section for testComputeMathArguments FLOAT]WertA_float</Z:val><Z:oP>*</Z:op><Z:val>[Section for testComputeMathArguments FLOAT]WertB_float</Z:val></Z:math></Z>'. FGL rulez.");
//			
//			objStreamFile.println("[Section for testComputeMathVariable FLOAT]");
//			objStreamFile.println("WertB_float=<Z><z:Var>myTestVariableFloat</z:Var></z>");
//			
//			//Beachte Variablen können wie INI-Path auch ausserhalb einer MATH - Anweisung gelten.
//			objStreamFile.println("[Section for testPassVariable]");
//			objStreamFile.println("Formula1=<Z>Der dynamische Wert ist '<z:Var>myTestVariableString</z:Var>'. FGL rulez.</Z>");
//			objStreamFile.println("Formula2=Der dynamische Wert ist '<Z><z:Math><Z:VAL>[Section for testComputeMathArguments FLOAT]WertA_float</Z:val><Z:oP>*</Z:op><Z:val><Z:Var>myTestVariableFloat</z:Var></Z:val></Z:math></Z>'. FGL rulez.");
//			objStreamFile.println("Formula3=Der dynamische Wert ist '<Z><z:Math><Z:VAL>[Section for testComputeMathArguments FLOAT]WertA_float</Z:val><Z:oP>*</Z:op><Z:val>[Section for testComputeMathVariable FLOAT]WertB_float</Z:val></Z:math></Z>'. FGL rulez.");
//			
//			
//			//20210707 Tests für die Arbeit mit JSON Strings
//			//Merke:			
//			//Gib den JSON-Hashmap-Wert so an: {"DEBUGUI_PANELLABEL_ON":true} Merke: Intern hier eine HashMap String, Boolean Das ist aber nur sinnvoll bei der FLAG übergabe, da weiss man, dass der Wert Boolean ist.
//			//                           also: NavigatorContentJson=<JSON>{"UIText01":"TESTWERT2DO2JSON01","UIText02":"TESTWERT2DO2JSON02"}</JSON>
//			//Gib den JSON-Array-Wert so an: {"wert1","wert2"}
//			objStreamFile.println("[Section for testJsonHashmap]");
//			objStreamFile.println("Map1="+ KernelJsonMapIniSolverZZZTest.sEXPRESSION_JSONMAP01_DEFAULT);
//				
//			objStreamFile.println("[Section for testJsonArraylist]");
//			objStreamFile.println("Array1="+ KernelJsonArrayIniSolverZZZTest.sEXPRESSION_JSONARRAY01_DEFAULT);
//			
//			
//			//20220926 Tests fuer die Arbeit mit verschluesselten / encrypted Werten
//			String sValue = "abcde"; int iKeyNumber=5; String sCharacterPool="?! abcdefghijklmnopqrstuvwxyz";
//			String sFlagNumeric = ICharacterPoolUserZZZ.FLAGZ.USENUMERIC.name();
//			String sFlagUppercase = ICharacterPoolUserZZZ.FLAGZ.USEUPPERCASE.name();
//			String sFlagLOWERcase = ICharacterPoolUserZZZ.FLAGZ.USELOWERCASE.name();
//			String sFlagBlank = IROTUserZZZ.FLAGZ.USEBLANK.name();
//			String sEncrypted = ROT13ZZZ.encryptIt(sValue);
//			objStreamFile.println("[Section for testEncrypted]");
//			objStreamFile.println("WertA="+sValue);
//			objStreamFile.println("WertAencrypted="+sEncrypted);
//			objStreamFile.println("WertAforDecrypt="+ KernelEncryptionIniSolverZZZTest.sEXPRESSION_ENCRYPTION01_DEFAULT); //<Z><Z:Encrypted><Z:Cipher>ROT13</Z:Cipher><Z:Code>"+sEncrypted+"</Z:Code></Z:Encrypted></Z>");
//			
//			sEncrypted = ROTnumericZZZ.encrypt(sValue, iKeyNumber, true,false);
//			objStreamFile.println("WertB="+sValue);
//			objStreamFile.println("WertBencrypted="+sEncrypted);
//			objStreamFile.println("WertBforDecrypt="+ KernelEncryptionIniSolverZZZTest.sEXPRESSION_ENCRYPTION02_DEFAULT); //<Z><Z:Encrypted><Z:Cipher>ROTnumeric</Z:Cipher><z:KeyNumber>"+iKeyNumber+"</z:KeyNumber><Z:FlagControl>"+sFlagNumeric+"</Z:FlagControl><Z:Code>"+sEncrypted+"</Z:Code></Z:Encrypted></Z>");
//			
//			CharacterExtendedZZZ objCharacterMissingReplacement = new CharacterExtendedZZZ(ICharacterPoolUserZZZ.cCHARACTER_MISSING_REPLACEMENT_DEFAULT);
//			sEncrypted = ROTnnZZZ.encrypt(sValue, sCharacterPool, objCharacterMissingReplacement, iKeyNumber, true,false,false,false);
//			objStreamFile.println("WertC="+sValue);
//			objStreamFile.println("WertCencrypted="+sEncrypted);
//			objStreamFile.println("WertCForDecrypt="+ KernelEncryptionIniSolverZZZTest.sEXPRESSION_ENCRYPTION03_DEFAULT); //<Z><Z:Encrypted><Z:Cipher>ROTnn</Z:Cipher><z:KeyNumber>"+iKeyNumber+"</z:KeyNumber><z:CharacterPool>"+sCharacterPool+"</z:CharacterPool><z:FlagControl>"+sFlagUppercase+"</Z:FlagControl><Z:Code>"+sEncrypted+"</Z:Code></Z:Encrypted></Z>");
//			
//			//20230505 Tests fuer die Arbeit mit JavaCall Aufrufen
//			objStreamFile.println("[Section for testCall]");
//			objStreamFile.println("WertCalled="+KernelCallIniSolverZZZTest.sEXPRESSION_CALL01_DEFAULT); //<Z><Z:Call><Z:Java><Z:Class>basic.zBasic.util.machine.EnvironmentZZZ</Z:Class><Z:Method>getHostName</Z:Method></Z:Java></Z:Call></Z>");
//			
//			
//			//20230505 Tests fuer die Arbeit mit Werten aus einem JavaCall Aufruf
//			objStreamFile.println("[ArgumentSection for testCallComputed]");
//			objStreamFile.println("JavaClass=basic.zBasic.util.machine.EnvironmentZZZ");
//			objStreamFile.println("JavaMethod=getHostName");
//			
//			objStreamFile.println("[Section for testCallComputed]");
//			objStreamFile.println("WertCalledComputed="+ KernelCallIniSolverZZZTest.sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT); //<Z><Z:Call><Z:Java><Z:Class><Z>[ArgumentSection for testCallComputed]JavaClass</Z></Z:Class><Z:Method><Z>[ArgumentSection for testCallComputed]JavaMethod</Z></Z:Method></Z:Java></Z:Call></Z>");
//			
//			
//			objFile = new File(sFilePathTotal);		
//			
			
			objFile = TestUtilZZZ.createKernelFileUsed();
			
			//#################
			objKernel = new KernelZZZ("FGL", "01", "test", "ZKernelConfigKernel_test.ini",(String)null);
			
			//#### Ein init TestObjekt
			String[] saFlagInit = {"init"};
			objExpressionHandlerInit = new KernelExpressionIniHandlerZZZ(objKernel, objFileIniTest, saFlagInit);
			
			//#### Das konkrete TestObject
			//Merke: Für diesen Test das konkrete Ini-File an das Test-Objekt uebergeben und sich nicht auf den Kernel selbst beziehen.
			String[] saFlagFileIni= {
							IIniTagWithExpressionZZZ.FLAGZ.USEEXPRESSION.name(),
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
			
			String[] saFlag = {""}; //Merke: Die Flags des Testobjekts selbst werden in den einzelnen Tests explizit gesetzt.
			objExpressionHandler = new KernelExpressionIniHandlerZZZ(objKernel, objFileIniTest, saFlag);
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}	
	}//END setup
	
	public void testFlagHandling(){
		try{							
			assertTrue(objExpressionHandlerInit.getFlag("init")==true);
			assertFalse(objExpressionHandler.getFlag("init")==true); //Nun wäre init falsch
			
			String[] saFlag = objExpressionHandler.getFlagZ();
			assertEquals(14,saFlag.length);
			
			boolean bFlagAvailable = objExpressionHandler.setFlag("usejson", false); //Ansonsten wird der Wert sofort ausgerechnet
			assertTrue("Das Flag 'usejson' sollte zur Verfügung stehen.", bFlagAvailable);
			
			bFlagAvailable = objExpressionHandler.setFlag("usejson_array", false); //Ansonsten wird der Wert sofort ausgerechnet
			assertTrue("Das Flag 'usejson_array' sollte zur Verfügung stehen.", bFlagAvailable);
			
			bFlagAvailable = objExpressionHandler.setFlag("usejson_map", false); //Ansonsten wird der Wert sofort ausgerechnet
			assertTrue("Das Flag 'usejson_map' sollte zur Verfügung stehen.", bFlagAvailable);
			
			bFlagAvailable = objExpressionHandler.setFlag(IKernelEncryptionIniSolverZZZ.FLAGZ.USEENCRYPTION, false); //Ansonsten wird der Wert sofort ausgerechnet
			assertTrue("Das Flag '"+ IKernelEncryptionIniSolverZZZ.FLAGZ.USEENCRYPTION + "' sollte zur Verfügung stehen.", bFlagAvailable);
			
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}
	
	public void testCompute() {
		try {			
			boolean btemp;
			String sSection; String sProperty;
			
			String sTagStartZ = "<Z>";
			String sTagEndZ = "</Z>";						
			String sExpressionSource; String sExpressionSolved; String sExpressionSolvedTagless;
			String sExpressionSourceFormulaMath; String sExpressionFormulaMathSolved;
			
			
			//+++++++ VORGEZOGENER LETZTER FEHLERTEST START
			
			
			
			//+++++++ VORGEZOGENER LETZTER FEHLERTEST ENDE
			
			//##########################################################
			//### PFAD-Ausdruck drin
			//##########################################################
			//TODOGOON 20241008: btemp = testCompute_PATHunsolved_(sExpressionSource, sExpressionSolved, true);
			
			
			sExpressionSource = KernelExpressionIniHandlerZZZTest.sEXPRESSION_Expression01_DEFAULT;
			sExpressionSolved = KernelExpressionIniHandlerZZZTest.sEXPRESSION_Expression01_SOLVED;
			sExpressionSolvedTagless = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
			//"Der dynamische Wert ist '<Z>[Section A]Testentry1</Z>'. FGL rulez.";
			//"Der dynamische Wert ist 'Testvalue1 to be found'. FGL rulez.";
			btemp = testCompute_PATH_(sExpressionSource, sExpressionSolved, false, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);	//Z-Tags um PATH-Aufloesung immer rausrechnen!!!		
			btemp = testCompute_PATH_(sExpressionSource, sExpressionSolvedTagless, true, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);			
			
			//Rechne fuer das Ergebnis die umgebenden Z-Tags raus

			sExpressionSourceFormulaMath="Der dynamische Wert ist '<Z><Z:math><Z:val>2</Z:val><Z:op>*</Z:op><Z:val>3</Z:val></Z:math></Z>'. FGL rulez.";
			sExpressionFormulaMathSolved = "Der dynamische Wert ist '<Z><Z:math><Z:val>2</Z:val><Z:op>*</Z:op><Z:val>3</Z:val></Z:math></Z>'. FGL rulez.";
			sExpressionSolvedTagless = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionFormulaMathSolved, sTagStartZ, sTagEndZ);
			btemp = testCompute_PATH_unsolved_(sExpressionSourceFormulaMath, sExpressionFormulaMathSolved, false, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
			btemp = testCompute_PATH_unsolved_(sExpressionSourceFormulaMath, sExpressionSolvedTagless, true, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);


			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//+++ Kein Math-Ausdruck drin, trotzdem den Pfad ausrechen
			//a) parse
			sExpressionSource = KernelExpressionIniHandlerZZZTest.sEXPRESSION_Expression01_DEFAULT;
			sExpressionSolved = KernelExpressionIniHandlerZZZTest.sEXPRESSION_Expression01_SOLVED;
			btemp = testCompute_MATH_(sExpressionSource, sExpressionSolved, false, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);	

			//Rechne fuer das Ergebnis die umgebenden Z-Tags raus
			sExpressionSource = KernelExpressionIniHandlerZZZTest.sEXPRESSION_Expression01_DEFAULT;
			sExpressionSolved = KernelExpressionIniHandlerZZZTest.sEXPRESSION_Expression01_SOLVED;
			sExpressionSolvedTagless = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);			
			btemp = testCompute_MATH_(sExpressionSource, sExpressionSolvedTagless, true, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
			
			//b) solve
			sExpressionSource = KernelExpressionIniHandlerZZZTest.sEXPRESSION_Expression01_DEFAULT;
			sExpressionSolved = KernelExpressionIniHandlerZZZTest.sEXPRESSION_Expression01_SOLVED;
			btemp = testCompute_MATH_(sExpressionSource, sExpressionSolved, false, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
			
			//Rechne fuer das Ergebnis die umgebenden Z-Tags raus
			sExpressionSource = KernelExpressionIniHandlerZZZTest.sEXPRESSION_Expression01_DEFAULT;
			sExpressionSolved = KernelExpressionIniHandlerZZZTest.sEXPRESSION_Expression01_SOLVED;
			sExpressionSolvedTagless = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
			btemp = testCompute_MATH_(sExpressionSource, sExpressionSolvedTagless, true, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			
			
			//##########################################################
			//### Math-Ausdruck drin, 
			//### OHNE einen OPERATOR zu 
			//### - FALL: KEINE AUFLOESUNG
			//##########################################################
			//a) parse
			sExpressionSourceFormulaMath="Der dynamische Wert ist '<Z><Z:math><Z:val>2</Z:val><Z:val>3</Z:val></Z:math></Z>'. FGL rulez.";
			sExpressionFormulaMathSolved = sExpressionSourceFormulaMath;
			btemp = testCompute_MATH_unsolved_(sExpressionSourceFormulaMath, sExpressionFormulaMathSolved, false, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);

			sExpressionSourceFormulaMath="Der dynamische Wert ist '<Z><Z:math><Z:val>2</Z:val><Z:val>3</Z:val></Z:math></Z>'. FGL rulez.";
			sExpressionFormulaMathSolved = sExpressionSourceFormulaMath;
			sExpressionSolvedTagless= KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionFormulaMathSolved, sTagStartZ, sTagEndZ);
			btemp = testCompute_MATH_unsolved_(sExpressionSourceFormulaMath, sExpressionSolvedTagless, true, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
				
			//b) solve
			sExpressionSourceFormulaMath="Der dynamische Wert ist '<Z><Z:math><Z:val>2</Z:val><Z:val>3</Z:val></Z:math></Z>'. FGL rulez.";
			sExpressionFormulaMathSolved = sExpressionSourceFormulaMath;
			btemp = testCompute_MATH_unsolved_(sExpressionSourceFormulaMath, sExpressionFormulaMathSolved, false, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);	

			sExpressionSourceFormulaMath="Der dynamische Wert ist '<Z><Z:math><Z:val>2</Z:val><Z:val>3</Z:val></Z:math></Z>'. FGL rulez.";
			sExpressionFormulaMathSolved = sExpressionSourceFormulaMath;
			sExpressionSolvedTagless= KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionFormulaMathSolved, sTagStartZ, sTagEndZ);			
			btemp = testCompute_MATH_unsolved_(sExpressionSourceFormulaMath, sExpressionSolvedTagless, true, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
			
			//##########################################################
			//### Math-Ausdruck drin, OHNE einen OPERATOR zu verwenden
			//##########################################################
			//a) parse
			sExpressionSourceFormulaMath="Der dynamische Wert ist '<Z><Z:math><Z:val>2</Z:val><Z:val>3</Z:val></Z:math></Z>'. FGL rulez.";
			btemp = testCompute_MATH_(sExpressionSourceFormulaMath, sExpressionSourceFormulaMath, false, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);

			sExpressionSourceFormulaMath="Der dynamische Wert ist '<Z><Z:math><Z:val>2</Z:val><Z:val>3</Z:val></Z:math></Z>'. FGL rulez.";
			sExpressionSolvedTagless= KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSourceFormulaMath, sTagStartZ, sTagEndZ);
			btemp = testCompute_MATH_(sExpressionSourceFormulaMath, sExpressionSolvedTagless, true, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
				
			//b) solve
			sExpressionSourceFormulaMath="Der dynamische Wert ist '<Z><Z:math><Z:val>2</Z:val><Z:val>3</Z:val></Z:math></Z>'. FGL rulez.";
			sExpressionFormulaMathSolved = "Der dynamische Wert ist '<Z>23</Z>'. FGL rulez.";
			btemp = testCompute_MATH_(sExpressionSourceFormulaMath, sExpressionFormulaMathSolved, false, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);	


			sExpressionSourceFormulaMath="Der dynamische Wert ist '<Z><Z:math><Z:val>2</Z:val><Z:val>3</Z:val></Z:math></Z>'. FGL rulez.";
			sExpressionFormulaMathSolved = "Der dynamische Wert ist '<Z>23</Z>'. FGL rulez.";
			sExpressionSolvedTagless= KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionFormulaMathSolved, sTagStartZ, sTagEndZ);			
			btemp = testCompute_MATH_(sExpressionSourceFormulaMath, sExpressionSolvedTagless, true, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
						
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			
			
			
			//#####################################################
			//### Math-Ausdruck drin, der einen Operator enthaelt
			//#####################################################
			//a) parse
			sExpressionSourceFormulaMath="Der dynamische Wert ist '<Z><Z:math><Z:val>2</Z:val><Z:op>*</Z:op><Z:val>3</Z:val></Z:math></Z>'. FGL rulez.";
			btemp = testCompute_MATH_(sExpressionSourceFormulaMath, sExpressionSourceFormulaMath, false, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);

			sExpressionSourceFormulaMath="Der dynamische Wert ist '<Z><Z:math><Z:val>2</Z:val><Z:op>*</Z:op><Z:val>3</Z:val></Z:math></Z>'. FGL rulez.";			
			sExpressionSolvedTagless= KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSourceFormulaMath, sTagStartZ, sTagEndZ);
			btemp = testCompute_MATH_(sExpressionSourceFormulaMath, sExpressionSolvedTagless, true, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
						
			//b) solve
			sExpressionSourceFormulaMath="Der dynamische Wert ist '<Z><Z:math><Z:val>2</Z:val><Z:op>*</Z:op><Z:val>3</Z:val></Z:math></Z>'. FGL rulez.";
			sExpressionFormulaMathSolved = "Der dynamische Wert ist '<Z>6</Z>'. FGL rulez.";
			btemp = testCompute_MATH_(sExpressionSourceFormulaMath, sExpressionFormulaMathSolved, false, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);	


			sExpressionSourceFormulaMath="Der dynamische Wert ist '<Z><Z:math><Z:val>2</Z:val><Z:op>*</Z:op><Z:val>3</Z:val></Z:math></Z>'. FGL rulez.";
			sExpressionFormulaMathSolved = "Der dynamische Wert ist '<Z>6</Z>'. FGL rulez.";
			sExpressionSolvedTagless= KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionFormulaMathSolved, sTagStartZ, sTagEndZ);			
			btemp = testCompute_MATH_(sExpressionSourceFormulaMath, sExpressionSolvedTagless, true, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
						
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			
			
			//################################################################################
			//### Verwende den Expression-Sover ueber das FileIni-Objekt
			//################################################################################
			//TODO diesen String testen:  String sExpressionSource2 = "Der dynamische Wert2 ist '<Z>[Section B]Testentry2</Z>'. FGL rulez.";		
			
			
			//#########################################################
			//#### SECTION A ##########################################
			//#### In der Section Property-Kombination liegt ein Pfad mit einem anderen Wert
			sSection = "Section for testCompute";
			sProperty = "Formula1";
			sExpressionSolved = "Der dynamische Wert ist '<Z>[Section A]Testentry1</Z>'. FGL rulez.";
			sExpressionSolvedTagless = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
			btemp = testCompute_IniUsedUnsolved_(sSection, sProperty, sExpressionSolvedTagless);	
			
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			
			sSection = "Section for testCompute";
			sProperty = "Formula1";
			sExpressionSolved = "Der dynamische Wert ist '<Z>Testvalue1 to be found</Z>'. FGL rulez.";
			sExpressionSolvedTagless = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
			btemp = testCompute_IniUsed_(sSection, sProperty, sExpressionSolvedTagless);	
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			
			
			
			//#########################################################
			//#### SECTION B ##########################################
			//#### In der Section Property-Kombination liegt ein Pfad mit einem anderen Wert
			//#### HIER LIEGT DER WERT IN EINER SECTION MIT EINER SYSTEMNUMBER 
			sSection = "Section for testCompute";
			sProperty = "Formula2";						
			sExpressionSolved = "Der dynamische Wert2 ist '<Z>[Section B]Testentry2</Z>'. FGL rulez.";
			sExpressionSolvedTagless = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
			btemp = testCompute_IniUsedUnsolved_(sSection, sProperty, sExpressionSolvedTagless);	
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		
			sSection = "Section for testCompute";
			sProperty = "Formula2";						
			sExpressionSolved = "Der dynamische Wert2 ist '<Z>Testvalue2 local to be found</Z>'. FGL rulez.";
			sExpressionSolvedTagless = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
			btemp = testCompute_IniUsed_(sSection, sProperty, sExpressionSolvedTagless);	
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			
			//#########################################################
			//#### SECTION C ##########################################
			//#### In der Section Property-Kombination liegt ein Pfad mit einem anderen Wert
			//#### HIER LIEGT DER WERT IN EINER SECTION MIT EINER SYSTEMNUMBER
			sSection = "Section for testCompute";
			sProperty = "Formula3";	
			sExpressionSolved = "Der dynamische Wert3 ist '<Z>[Section C]Testentry3</Z>'. FGL rulez.";
			sExpressionSolvedTagless = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
			btemp = testCompute_IniUsedUnsolved_(sSection, sProperty, sExpressionSolvedTagless);	
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			
			sSection = "Section C";
			sProperty = "Testentry3";						
			sExpressionSolved = "Testvalue3 local to be found";
			sExpressionSolvedTagless = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
			btemp = testCompute_IniUsed_(sSection, sProperty, sExpressionSolvedTagless);	
			
			sSection = "Section for testCompute";
			sProperty = "Formula3";	
			sExpressionSolved = "Der dynamische Wert3 ist '<Z>Testvalue3 local to be found</Z>'. FGL rulez.";
			sExpressionSolvedTagless = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
			btemp = testCompute_IniUsed_(sSection, sProperty, sExpressionSolvedTagless);	
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			
			//##############################################################
			//#### SECTION 1 MATH ##########################################
			//#### Einfache MATH Formel ohne Operator, ohne PATH, etc.
			//a) ohne jedliche Aufloesung
			sSection = "Section for testComputeMathValue";
			sProperty = "Formula1";	
			sExpressionSolved = "Der dynamische Wert ist '<Z><Z:math><Z:val>2</Z:val><Z:val>3</Z:val></Z:math></Z>'. FGL rulez.";
			sExpressionSolvedTagless = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
			btemp = testCompute_IniUsedUnsolved_(sSection, sProperty, sExpressionSolvedTagless);	
			
			//b) ohne FORMULA Aufloesung
			sSection = "Section for testComputeMathValue";
			sProperty = "Formula1";							
			sExpressionSolved = "Der dynamische Wert ist '<Z><Z:math><Z:val>2</Z:val><Z:val>3</Z:val></Z:math></Z>'. FGL rulez.";
			sExpressionSolvedTagless = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
			btemp = testCompute_IniUsed_(sSection, sProperty, sExpressionSolvedTagless);
			
			//c) ohne MATH Aufloesung
			sSection = "Section for testComputeMathValue";
			sProperty = "Formula1";	
			sExpressionSolved = "Der dynamische Wert ist '<Z><Z:math><Z:val>2</Z:val><Z:val>3</Z:val></Z:math></Z>'. FGL rulez.";
			sExpressionSolvedTagless = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
			btemp = testCompute_MATH_IniUsedUnsolved_(sSection, sProperty, sExpressionSolvedTagless);	
			
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//#### Einfache MATH Formel ohne Operator, ohne PATH, etc.
			//d) Aufloesen
			sSection = "Section for testComputeMathValue";
			sProperty = "Formula1";							
			sExpressionSolved = "Der dynamische Wert ist '<Z>23</Z>'. FGL rulez.";
			sExpressionSolvedTagless = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
			btemp = testCompute_MATH_IniUsed_(sSection, sProperty, sExpressionSolvedTagless);	
			
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}
	
	private boolean testCompute_PATH_unsolved_(String sExpressionSourceIn, String sExpressionSolvedIn, boolean bRemoveSuroundingSeparators, IEnumSetMappedTestCaseZZZ objEnumTestCase) {
		boolean bReturn = false;
		
		main:{
			try {
				String sExpressionSource; String sExpressionSolved; String sValue;
				boolean btemp; String stemp;
												

				//#########################################################
				//#### SECTION A ########################################## 
				//+++ Anwenden der ersten Formel, ohne Berechnung
				btemp = objExpressionHandler.setFlag(IIniTagWithExpressionZZZ.FLAGZ.USEEXPRESSION, false); 
				assertTrue("Flag nicht vorhanden '" + IIniTagWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
					
				btemp = objExpressionHandler.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH,true);  //sollte wg. useexpression egal sein
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
				
				btemp = objExpressionHandler.setFlag(IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE,false);  
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE + "'", btemp);

				btemp = objExpressionHandler.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER, true); //sollte wg. useexpression egal sein
				assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
				
				btemp = objExpressionHandler.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA,true);  //sollte wg. useexpression_solver = false egal sein.
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA + "'", btemp);
				
				btemp = objExpressionHandler.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH,true);
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH + "'", btemp);
				
				sExpressionSource = sExpressionSourceIn;
				sExpressionSolved = sExpressionSourceIn; //OHNE EXPRESSION-BEHANDLUNG BLEIBEN DIE TAGS DRIN					
				sValue = objExpressionHandler.solve(sExpressionSource, bRemoveSuroundingSeparators);	
				assertEquals(sExpressionSolved, sValue);
				
				
				//+++ ... noch fehlt "useexpression_solver" als gesetztes Flag
				btemp = objExpressionHandler.setFlag(IIniTagWithExpressionZZZ.FLAGZ.USEEXPRESSION, true); //sollte wg. useexpression egal sein
				assertTrue("Flag nicht vorhanden '" + IIniTagWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
				
				btemp = objExpressionHandler.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH,true);  
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
				
				btemp = objExpressionHandler.setFlag(IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE,false);  
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE + "'", btemp);
				
				btemp = objExpressionHandler.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER, false); //Ansonsten wird der Wert sofort ausgerechnet
				assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
				
				btemp = objExpressionHandler.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA,true);  //sollte wg. useexpression_solver = false egal sein.
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA + "'", btemp);
				
				btemp = objExpressionHandler.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH,false);
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH + "'", btemp);
				
				sExpressionSource = sExpressionSourceIn;
				sExpressionSolved = sExpressionSolvedIn;
				sValue = objExpressionHandler.solve(sExpressionSource, bRemoveSuroundingSeparators);
				assertEquals(sExpressionSolved, sValue);
				
				
				//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
				//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++		
				btemp = objExpressionHandler.setFlag(IIniTagWithExpressionZZZ.FLAGZ.USEEXPRESSION, true); //sollte wg. useexpression egal sein
				assertTrue("Flag nicht vorhanden '" + IIniTagWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
				
				btemp = objExpressionHandler.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH,true);  
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
				
				btemp = objExpressionHandler.setFlag(IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE,false);  
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE + "'", btemp);
				
				btemp = objExpressionHandler.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER, true); 
				assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
				
				btemp = objExpressionHandler.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA,false);  //Ansonsten wird der Wert sofort ausgerechnet
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA + "'", btemp);
					
				btemp = objExpressionHandler.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH,true); //sollte wg. usefomula = false egal sein.
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH + "'", btemp);
				
				if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.SOLVE)) {
				sExpressionSource = sExpressionSourceIn;
				sExpressionSolved = sExpressionSolvedIn;
				sValue = objExpressionHandler.solve(sExpressionSource, bRemoveSuroundingSeparators);
				assertEquals(sExpressionSolved, sValue);

				}
				
				
				//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
				//+++ ... parse sollte aber IMMER den Z-Tag entfernen...
				if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.PARSE)) {
				String sTagStartZ = "<Z>";
				String sTagEndZ = "</Z>";
												
				sExpressionSource = sExpressionSourceIn;		
				sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSource, sTagStartZ, sTagEndZ);				
				sValue = objExpressionHandler.parse(sExpressionSource);
				assertEquals(sExpressionSolved, sValue);
				}
				//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
				
				
				bReturn = true;
			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			}
		}//end main:
		return bReturn;
	}
	
	private boolean testCompute_PATH_(String sExpressionSourceIn, String sExpressionSolvedIn, boolean bRemoveSuroundingSeparators, IEnumSetMappedTestCaseZZZ objEnumTestCase) {
		boolean bReturn = false;
		
		main:{
			try {
				String sExpressionSource; String sExpressionSolved; String sValue;
				boolean btemp; String stemp;
				ReferenceZZZ<IKernelConfigSectionEntryZZZ> objSectionEntryReference;
				
				//... mit Berechnung PATH
				btemp = objExpressionHandler.setFlag(IIniTagWithExpressionZZZ.FLAGZ.USEEXPRESSION, true); //sollte wg. useexpression egal sein
				assertTrue("Flag nicht vorhanden '" + IIniTagWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
				
				btemp = objExpressionHandler.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH,true);  
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
				
				btemp = objExpressionHandler.setFlag(IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE,true);  
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE + "'", btemp);
				
				btemp = objExpressionHandler.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER, true); //Ansonsten wird der Wert sofort ausgerechnet
				assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
				
				btemp = objExpressionHandler.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA,true);
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA + "'", btemp);
				
				btemp = objExpressionHandler.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH,true); //ohne Mathematische Berechnung, sollte daher egal sein
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH + "'", btemp);
								
				if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.SOLVE)) {
				
					sExpressionSource = sExpressionSourceIn;
					sExpressionSolved = sExpressionSolvedIn;
					//sExpressionSolved = "Der dynamische Wert ist '<Z>Testvalue1 to be found</Z>'. FGL rulez.";			
					objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
					sValue = objExpressionHandler.solve(sExpressionSource, bRemoveSuroundingSeparators); //false ist wichtig, damit die Z-Tags enthalten bleiben, die die Formeln umgeben
					assertEquals(sExpressionSolved, sValue);
				}
				
				bReturn = true;
			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			}
		}//end main:
		return bReturn;
	}

	private boolean testCompute_MATH_unsolved_(String sExpressionSourceIn, String sExpressionSolvedIn, boolean bRemoveSuroundingSeparators, IEnumSetMappedTestCaseZZZ objEnumTestCase) {
		boolean bReturn = false;
		
		main:{
			try {
				String sExpressionSource; String sExpressionSolved; String sValue;
				boolean btemp; String stemp;
				ReferenceZZZ<IKernelConfigSectionEntryZZZ> objSectionEntryReference;
				String sTagStartZ = "<Z>";
				String sTagEndZ = "</Z>";
				
				//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
				//... ohne Berechnung (math)
				btemp = objExpressionHandler.setFlag(IIniTagWithExpressionZZZ.FLAGZ.USEEXPRESSION, true); //sollte wg. useexpression egal sein
				assertTrue("Flag nicht vorhanden '" + IIniTagWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
				
				btemp = objExpressionHandler.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH,true);  
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
				
				btemp = objExpressionHandler.setFlag(IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE,true);  
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE + "'", btemp);
				
				btemp = objExpressionHandler.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER, true); //Ansonsten wird der Wert sofort ausgerechnet
				assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
				
				btemp = objExpressionHandler.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA,true);  
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA + "'", btemp);
				
				btemp = objExpressionHandler.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH,false);
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH + "'", btemp);
					
				sExpressionSource = sExpressionSourceIn;
				sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSource, sTagStartZ, sTagEndZ);		
												
				//+++ ... parse ist nicht solve... also wird hier nichts aufgeloest, aussser die Pfade
				if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.PARSE)) {
					sExpressionSource = sExpressionSourceIn;
					sExpressionSolved = sExpressionSolvedIn;		
					objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
					sValue = objExpressionHandler.parse(sExpressionSource, objSectionEntryReference, bRemoveSuroundingSeparators);
					assertEquals(sExpressionSolved, sValue);
				}
				//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
					
				//+++ ... solve verhält sich NICHT wie parse(), bei solve wird aufgeloest...
				if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.SOLVE)) {
					sExpressionSource = sExpressionSourceIn;
					sExpressionSolved = sExpressionSolvedIn;
					objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
					sValue = objExpressionHandler.solve(sExpressionSource, objSectionEntryReference, bRemoveSuroundingSeparators);
					assertEquals(sExpressionSolved, sValue);
				}
				//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
				
				
				bReturn = true;
			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			}
		}//end main:
		return bReturn;
	}
	
	private boolean testCompute_MATH_(String sExpressionSourceIn, String sExpressionSolvedIn, boolean bRemoveSuroundingSeparators, IEnumSetMappedTestCaseZZZ objEnumTestCase) {
		boolean bReturn = false;
		
		main:{
			try {
				String sExpressionSource; String sExpressionSolved; String sValue;
				boolean btemp; String stemp;
				ReferenceZZZ<IKernelConfigSectionEntryZZZ> objSectionEntryReference;
				String sTagStartZ = "<Z>";
				String sTagEndZ = "</Z>";
					
				//#########################################################
				//### Mit Berechnung MATH 
				//#########################################################
				
				//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
				//... mit Berechnung (math)
				btemp = objExpressionHandler.setFlag(IIniTagWithExpressionZZZ.FLAGZ.USEEXPRESSION, true); //sollte wg. useexpression egal sein
				assertTrue("Flag nicht vorhanden '" + IIniTagWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
				
				btemp = objExpressionHandler.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH,true);  
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
				
				btemp = objExpressionHandler.setFlag(IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE,true);  
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE + "'", btemp);
				
				btemp = objExpressionHandler.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER, true); //Ansonsten wird der Wert sofort ausgerechnet
				assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
				
				btemp = objExpressionHandler.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA,true);  
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA + "'", btemp);
				
				btemp = objExpressionHandler.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH,true);
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH + "'", btemp);
					
				sExpressionSource = sExpressionSourceIn;
				sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSource, sTagStartZ, sTagEndZ);		
												
				//+++ ... parse ist nicht solve... also wird hier nichts aufgeloest, aussser die Pfade
				if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.PARSE)){
				sExpressionSource = sExpressionSourceIn;
				sExpressionSolved = sExpressionSolvedIn;		
				objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
				sValue = objExpressionHandler.parse(sExpressionSource, objSectionEntryReference, bRemoveSuroundingSeparators);
				assertEquals(sExpressionSolved, sValue);
				}
				//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
					
				//+++ ... solve verhält sich NICHT wie parse(), bei solve wird aufgeloest...
				if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.SOLVE)) {
				sExpressionSource = sExpressionSourceIn;
				sExpressionSolved = sExpressionSolvedIn;
				objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
				sValue = objExpressionHandler.solve(sExpressionSource, objSectionEntryReference, bRemoveSuroundingSeparators);
				assertEquals(sExpressionSolved, sValue);
				}
				//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
				
				
				bReturn = true;
			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			}
		}//end main:
		return bReturn;
	}
	
	private boolean testCompute_IniUsed_(String sSectionIn, String sPropertyIn, String sExpressionSolvedIn) {
		boolean bReturn = false;
		
		main:{
			try {
				String sSection; String sProperty; String sExpressionSolved; String sValue;
				boolean btemp; String stemp;
				ReferenceZZZ<IKernelConfigSectionEntryZZZ> objSectionEntryReference;
				
				//... mit Berechnung PATH
				btemp = objFileIniTest.setFlag(IIniTagWithExpressionZZZ.FLAGZ.USEEXPRESSION, true); 
				assertTrue("Flag nicht vorhanden '" + IIniTagWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
				
				btemp = objFileIniTest.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH,true);  
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
				
				btemp = objFileIniTest.setFlag(IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE,false);  
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE + "'", btemp);
				
				btemp = objFileIniTest.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER, true); 
				assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
								
				btemp = objFileIniTest.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA,false);
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA + "'", btemp);
				
				btemp = objFileIniTest.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH, false); //ohne Mathematische Berechnung, sollte daher egal sein
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH + "'", btemp);
									
				sSection = sSectionIn;
				sProperty = sPropertyIn;
				sExpressionSolved = sExpressionSolvedIn;						
				objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
				sValue = objFileIniTest.getPropertyValue(sSection, sProperty).getValue();
				assertEquals(sExpressionSolved, sValue);
				
				
				bReturn = true;
			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			}
		}//end main:
		return bReturn;
	}
	
	
	private boolean testCompute_IniUsedUnsolved_(String sSectionIn, String sPropertyIn, String sExpressionSolvedIn) {
		boolean bReturn = false;
		
		main:{
			try {
				String sSection; String sProperty; String sExpressionSolved; String sValue;
				boolean btemp; String stemp;
				ReferenceZZZ<IKernelConfigSectionEntryZZZ> objSectionEntryReference;
				
				//... mit Berechnung PATH
				btemp = objFileIniTest.setFlag(IIniTagWithExpressionZZZ.FLAGZ.USEEXPRESSION, true); 
				assertTrue("Flag nicht vorhanden '" + IIniTagWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
			
				btemp = objFileIniTest.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH,false);  
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
								
				btemp = objFileIniTest.setFlag(IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE,false);  
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE + "'", btemp);
				
				btemp = objFileIniTest.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER, false); //Ansonsten wird der Wert sofort ausgerechnet
				assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
				
				//... ohne solve-Flag ist die eine Berechnung egal
				btemp = objFileIniTest.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA,true);                     //sollte wg. USEEXPRESSION_SOLVER = false egal sein.
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA + "'", btemp);
				
				//... ohne solve-Flag ist die Mathematische Berechnung egal
				btemp = objFileIniTest.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH,true); 
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH + "'", btemp);
									
				sSection = sSectionIn;
				sProperty = sPropertyIn;
				sExpressionSolved = sExpressionSolvedIn;						
				objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
				sValue = objFileIniTest.getPropertyValue(sSection, sProperty).getValue();
				assertEquals(sExpressionSolved, sValue);
				
				
				bReturn = true;
			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			}
		}//end main:
		return bReturn;
	}
	
	private boolean testCompute_MATH_IniUsed_(String sSectionIn, String sPropertyIn, String sExpressionSolvedIn) {
		boolean bReturn = false;
		
		main:{
			try {
				String sSection; String sProperty; String sExpressionSolved; String sValue;
				boolean btemp; String stemp;
				ReferenceZZZ<IKernelConfigSectionEntryZZZ> objSectionEntryReference;
				
				//... mit Berechnung PATH
				btemp = objFileIniTest.setFlag(IIniTagWithExpressionZZZ.FLAGZ.USEEXPRESSION, true); 
				assertTrue("Flag nicht vorhanden '" + IIniTagWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
				
				btemp = objFileIniTest.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH,true);  
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
				
				btemp = objFileIniTest.setFlag(IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE,false);  
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE + "'", btemp);
								
				btemp = objFileIniTest.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER, true); 
				assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
				
				btemp = objFileIniTest.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA,true);
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA + "'", btemp);
			
				//... mit Berechnung MATH
				btemp = objFileIniTest.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH, true); 
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH + "'", btemp);
									
				sSection = sSectionIn;
				sProperty = sPropertyIn;
				sExpressionSolved = sExpressionSolvedIn;						
				objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
				sValue = objFileIniTest.getPropertyValue(sSection, sProperty).getValue();
				assertEquals(sExpressionSolved, sValue);
				
				
				bReturn = true;
			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			}
		}//end main:
		return bReturn;
	}
	
	private boolean testCompute_MATH_IniUsedUnsolved_(String sSectionIn, String sPropertyIn, String sExpressionSolvedIn) {
		boolean bReturn = false;
		
		main:{
			try {
				String sSection; String sProperty; String sExpressionSolved; String sValue;
				boolean btemp; String stemp;
				ReferenceZZZ<IKernelConfigSectionEntryZZZ> objSectionEntryReference;
				
				//... mit Berechnung PATH
				btemp = objFileIniTest.setFlag(IIniTagWithExpressionZZZ.FLAGZ.USEEXPRESSION, true); 
				assertTrue("Flag nicht vorhanden '" + IIniTagWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
			
				btemp = objFileIniTest.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH,true);  
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
				
				btemp = objFileIniTest.setFlag(IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE,false);  
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE + "'", btemp);
				
				btemp = objFileIniTest.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER, true); //Ansonsten wird der Wert sofort ausgerechnet
				assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
										
				btemp = objFileIniTest.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA,true);                     //sollte wg. USEEXPRESSION_SOLVER = false egal sein.
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA + "'", btemp);
				
				btemp = objFileIniTest.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH,false); //ohne Mathematische Berechnung, sollte daher egal sein
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH + "'", btemp);
									
				sSection = sSectionIn;
				sProperty = sPropertyIn;
				sExpressionSolved = sExpressionSolvedIn;						
				objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
				sValue = objFileIniTest.getPropertyValue(sSection, sProperty).getValue();
				assertEquals(sExpressionSolved, sValue);
				
				
				bReturn = true;
			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			}
		}//end main:
		return bReturn;
	}

	
	/** void, Test: Reading an entry in a section of the ini-file
	* Lindhauer; 22.04.2006 12:54:32
	 */
	public void testCompute_JsonMap(){

		try {	
			boolean btemp; String stemp;
			
			String sTagStartZ = "<Z>";
			String sTagEndZ = "</Z>";						
			String sExpressionSource; String sExpressionSolved; HashMap hmExpressionSolved;
					
			
			//+++++++ VORGEZOGENER LETZTER FEHLERTEST START
								
			//################################################################
			//### Varianten JSON-HashMap aufzuloesen
			//#################################################################
			//..........
			
			sExpressionSource = KernelJsonMapIniSolverZZZTest.sEXPRESSION_JSONMAP01_DEFAULT;
			hmExpressionSolved = new HashMap();
			sExpressionSolved = "<Z>{UIText01=TESTWERT2DO2JSON01, UIText02=TESTWERT2DO2JSON02}</Z>";
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
			btemp = testCompute_JsonMap_(sExpressionSource, sExpressionSolved, hmExpressionSolved, false, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
			
			
			//+++++++ VORGEZOGENER LETZTER FEHLERTEST ENDE
			
			//################################################################
			//### Varianten JSON nicht aufzuloesen
			//#################################################################
			sExpressionSource = KernelJsonMapIniSolverZZZTest.sEXPRESSION_JSONMAP01_DEFAULT;
			sExpressionSolved = sExpressionSource;
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
			btemp = testCompute_JsonMap_JsonUnsolved_(sExpressionSource, sExpressionSolved, false, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
			
			sExpressionSource = KernelJsonMapIniSolverZZZTest.sEXPRESSION_JSONMAP01_DEFAULT;
			sExpressionSolved = sExpressionSource;
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
			btemp = testCompute_JsonMap_JsonUnsolved_(sExpressionSource, sExpressionSolved, false, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
			
			sExpressionSource = KernelJsonMapIniSolverZZZTest.sEXPRESSION_JSONMAP01_DEFAULT;
			sExpressionSolved = sExpressionSource;
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
			btemp = testCompute_JsonMap_JsonUnsolved_(sExpressionSource, sExpressionSolved, true, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
			
			sExpressionSource = KernelJsonMapIniSolverZZZTest.sEXPRESSION_JSONMAP01_DEFAULT;
			sExpressionSolved = sExpressionSource;
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
			btemp = testCompute_JsonMap_JsonUnsolved_(sExpressionSource, sExpressionSolved, true, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
			
			
			//################################################################
			//### Varianten JSON-HashMap nicht aufzuloesen
			//#################################################################
			sExpressionSource = KernelJsonMapIniSolverZZZTest.sEXPRESSION_JSONMAP01_DEFAULT;
			sExpressionSolved = sExpressionSource;
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
			btemp = testCompute_JsonMap_Unsolved_(sExpressionSource, sExpressionSolved, false, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
			
			sExpressionSource = KernelJsonMapIniSolverZZZTest.sEXPRESSION_JSONMAP01_DEFAULT;
			sExpressionSolved = sExpressionSource;
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
			btemp = testCompute_JsonMap_Unsolved_(sExpressionSource, sExpressionSolved, false, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
			
			sExpressionSource = KernelJsonMapIniSolverZZZTest.sEXPRESSION_JSONMAP01_DEFAULT;
			sExpressionSolved = sExpressionSource;
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
			btemp = testCompute_JsonMap_Unsolved_(sExpressionSource, sExpressionSolved, true, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
			
			sExpressionSource = KernelJsonMapIniSolverZZZTest.sEXPRESSION_JSONMAP01_DEFAULT;
			sExpressionSolved = sExpressionSource;
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
			btemp = testCompute_JsonMap_Unsolved_(sExpressionSource, sExpressionSolved, true, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
			
			//################################################################
			//### Varianten JSON-HashMap aufzuloesen
			//#################################################################
			sExpressionSource = KernelJsonMapIniSolverZZZTest.sEXPRESSION_JSONMAP01_DEFAULT;
			hmExpressionSolved = new HashMap();
			sExpressionSolved = sExpressionSource;
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
			btemp = testCompute_JsonMap_(sExpressionSource, sExpressionSolved, hmExpressionSolved, false, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
			
			sExpressionSource = KernelJsonMapIniSolverZZZTest.sEXPRESSION_JSONMAP01_DEFAULT;
			hmExpressionSolved = new HashMap();
			sExpressionSolved = "<Z>{UIText01=TESTWERT2DO2JSON01, UIText02=TESTWERT2DO2JSON02}</Z>";
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
			btemp = testCompute_JsonMap_(sExpressionSource, sExpressionSolved, hmExpressionSolved, false, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
			
			sExpressionSource = KernelJsonMapIniSolverZZZTest.sEXPRESSION_JSONMAP01_DEFAULT;
			hmExpressionSolved = new HashMap();
			sExpressionSolved = sExpressionSource;
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
			btemp = testCompute_JsonMap_(sExpressionSource, sExpressionSolved, hmExpressionSolved, true, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
			
			sExpressionSource = KernelJsonMapIniSolverZZZTest.sEXPRESSION_JSONMAP01_DEFAULT;
			hmExpressionSolved = new HashMap();
			sExpressionSolved = "{UIText01=TESTWERT2DO2JSON01, UIText02=TESTWERT2DO2JSON02}";
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
			btemp = testCompute_JsonMap_(sExpressionSource, sExpressionSolved, hmExpressionSolved, true, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
			
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}		
	}
	
	private boolean testCompute_JsonMap_JsonUnsolved_(String sExpressionSourceIn, String sExpressionSolvedIn, boolean bRemoveSuroundingSeparators, IEnumSetMappedTestCaseZZZ objEnumTestCase) {
		boolean bReturn = false;
		
		main:{
			try {
				boolean btemp; String stemp;
				
				String sExpressionSource; HashMap hmExpressionSolved; String sExpressionSolved; String sValue;				
				ReferenceZZZ<IKernelConfigSectionEntryZZZ> objSectionEntryReference;
				
				btemp = objExpressionHandler.setFlag(IIniTagWithExpressionZZZ.FLAGZ.USEEXPRESSION, true); 
				assertTrue("Flag nicht vorhanden '" + IIniTagWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
				
				btemp = objExpressionHandler.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH,true);  
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
				
				btemp = objExpressionHandler.setFlag(IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE,true);  
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE + "'", btemp);
								
				btemp = objExpressionHandler.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER, true); 
				assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
				
				//... ohne Formelberechnung
				btemp = objExpressionHandler.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA,false);
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA + "'", btemp);
			
				//... ohne Berechnung MATH
				btemp = objExpressionHandler.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH, false); 
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH + "'", btemp);
				
				//... ohne Berechnung JSON
				btemp = objExpressionHandler.setFlag(IKernelJsonIniSolverZZZ.FLAGZ.USEJSON, false); //Ansonsten wird der Wert sofort ausgerechnet
				assertTrue("Das Flag '" + IKernelJsonIniSolverZZZ.FLAGZ.USEJSON +"' sollte zur Verfügung stehen.", btemp);
								
				btemp = objExpressionHandler.setFlag(IKernelJsonMapIniSolverZZZ.FLAGZ.USEJSON_MAP, true); //sollte egal sein
				assertTrue("Das Flag '" + IKernelJsonMapIniSolverZZZ.FLAGZ.USEJSON_MAP +"' sollte zur Verfügung stehen.", btemp);
								
				//+++ ... solve verhält sich NICHT wie parse(), bei solve wird aufgeloest...
				if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.PARSE)) {
					sExpressionSource = sExpressionSourceIn;					
					sExpressionSolved = sExpressionSolvedIn;
					objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
					sValue = objExpressionHandler.solve(sExpressionSource, objSectionEntryReference, bRemoveSuroundingSeparators);
					assertEquals(sExpressionSolved, sValue);
				}
				//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

				//+++ ... parse ist nicht solve... also wird hier nichts aufgeloest, aussser die Pfade
				if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.SOLVE)) {
					sExpressionSource = sExpressionSourceIn;							
					sExpressionSolved = sExpressionSolvedIn;
					objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
					sValue = objExpressionHandler.parse(sExpressionSource, objSectionEntryReference, bRemoveSuroundingSeparators);
					assertEquals(sExpressionSolved, sValue);
				}
				//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
					
				
				bReturn = true;
			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			}
		}//end main:
		return bReturn;
	}
	
	
	private boolean testCompute_JsonMap_Unsolved_(String sExpressionSourceIn, String sExpressionSolvedIn, boolean bRemoveSuroundingSeparators, IEnumSetMappedTestCaseZZZ objEnumTestCase) {
		boolean bReturn = false;
		
		main:{
			try {
				boolean btemp; String stemp;
				
				String sExpressionSource; HashMap hmExpressionSolved; String sExpressionSolved; String sValue;				
				ReferenceZZZ<IKernelConfigSectionEntryZZZ> objSectionEntryReference;
				
				btemp = objExpressionHandler.setFlag(IIniTagWithExpressionZZZ.FLAGZ.USEEXPRESSION, true); 
				assertTrue("Flag nicht vorhanden '" + IIniTagWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
				
				btemp = objExpressionHandler.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH,true);  
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
				
				btemp = objExpressionHandler.setFlag(IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE,true);  
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE + "'", btemp);
								
				btemp = objExpressionHandler.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER, true); 
				assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
				
				//... ohne Formelberechnung
				btemp = objExpressionHandler.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA,false);
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA + "'", btemp);
			
				//... ohne Berechnung MATH
				btemp = objExpressionHandler.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH, false); 
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH + "'", btemp);
				
				//... mit Berechnung JSON
				btemp = objExpressionHandler.setFlag(IKernelJsonIniSolverZZZ.FLAGZ.USEJSON, true); 
				assertTrue("Das Flag '" + IKernelJsonIniSolverZZZ.FLAGZ.USEJSON +"' sollte zur Verfügung stehen.", btemp);
								
				//... ohne Berechnung JSON-MAP
				btemp = objExpressionHandler.setFlag(IKernelJsonMapIniSolverZZZ.FLAGZ.USEJSON_MAP, false);//Ansonsten wird der Wert sofort ausgerechnet 
				assertTrue("Das Flag '" + IKernelJsonMapIniSolverZZZ.FLAGZ.USEJSON_MAP +"' sollte zur Verfügung stehen.", btemp);
								
				//+++ ... solve verhält sich NICHT wie parse(), bei solve wird aufgeloest...
				if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.PARSE)) {
					sExpressionSource = sExpressionSourceIn;					
					sExpressionSolved = sExpressionSolvedIn;
					objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
					sValue = objExpressionHandler.solve(sExpressionSource, objSectionEntryReference, bRemoveSuroundingSeparators);
					assertEquals(sExpressionSolved, sValue);
				}
				//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

				//+++ ... parse ist nicht solve... also wird hier nichts aufgeloest, aussser die Pfade
				if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.SOLVE)) {
					sExpressionSource = sExpressionSourceIn;							
					sExpressionSolved = sExpressionSolvedIn;
					objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
					sValue = objExpressionHandler.parse(sExpressionSource, objSectionEntryReference, bRemoveSuroundingSeparators);
					assertEquals(sExpressionSolved, sValue);
				}
				//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
					
				
				bReturn = true;
			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			}
		}//end main:
		return bReturn;
	}
	
	private boolean testCompute_JsonMap_(String sExpressionSourceIn, String sExpressionSolvedIn, HashMap hmExpressionSolvedIn, boolean bRemoveSuroundingSeparators, IEnumSetMappedTestCaseZZZ objEnumTestCase) {
		boolean bReturn = false;
		
		main:{
			try {
				boolean btemp; String stemp;
				
				String sExpressionSource; String sExpressionSolved; HashMap hmExpressionSolved; String sValue;				
				ReferenceZZZ<IKernelConfigSectionEntryZZZ> objSectionEntryReference;
				
				//... mit Berechnung PATH
				btemp = objExpressionHandler.setFlag(IIniTagWithExpressionZZZ.FLAGZ.USEEXPRESSION, true); 
				assertTrue("Flag nicht vorhanden '" + IIniTagWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
				
				btemp = objExpressionHandler.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH,true);  
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
				
				btemp = objExpressionHandler.setFlag(IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE,true);  
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE + "'", btemp);
								
				btemp = objExpressionHandler.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER, true); 
				assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
				
				btemp = objExpressionHandler.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA,true);
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA + "'", btemp);
			
				//... mit Berechnung MATH
				btemp = objExpressionHandler.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH, true); 
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH + "'", btemp);
					
				//... mit Berechnung JSON
				btemp = objExpressionHandler.setFlag(IKernelJsonIniSolverZZZ.FLAGZ.USEJSON, true); 
				assertTrue("Das Flag '" + IKernelJsonIniSolverZZZ.FLAGZ.USEJSON +"' sollte zur Verfügung stehen.", btemp);
								
				//... mit Berechnung JSON-MAP
				btemp = objExpressionHandler.setFlag(IKernelJsonMapIniSolverZZZ.FLAGZ.USEJSON_MAP, true);//Ansonsten wird der Wert sofort ausgerechnet 
				assertTrue("Das Flag '" + IKernelJsonMapIniSolverZZZ.FLAGZ.USEJSON_MAP +"' sollte zur Verfügung stehen.", btemp);
				
				
				//+++ ... parse ist nicht solve... also wird hier nichts aufgeloest, aussser die Pfade
				if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.PARSE)) {
					sExpressionSource = sExpressionSourceIn;
					hmExpressionSolved = hmExpressionSolvedIn;		
					sExpressionSolved = sExpressionSolvedIn; //hmExpressionSolved.toString();
					objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
					sValue = objExpressionHandler.parse(sExpressionSource, objSectionEntryReference, bRemoveSuroundingSeparators);
					assertEquals(sExpressionSolved, sValue);
				}
				//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
					
				//+++ ... solve verhält sich NICHT wie parse(), bei solve wird aufgeloest...
				if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.SOLVE)) {
					sExpressionSource = sExpressionSourceIn;
					hmExpressionSolved = hmExpressionSolvedIn;
					sExpressionSolved = sExpressionSolvedIn; //hmExpressionSolved.toString();
					objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
					sValue = objExpressionHandler.solve(sExpressionSource, objSectionEntryReference, bRemoveSuroundingSeparators);
					assertEquals(sExpressionSolved, sValue);
				}
				//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

				bReturn = true;
			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			}
		}//end main:
		return bReturn;
	}
	
	
	/**void, Test: Reading an entry in a section of the ini-file
	 * 
	 * @author Fritz Lindhauer, 05.05.2023, 08:54:30
	 */
	public void testCompute_CallJava(){
		
		boolean btemp; int itemp;
		
		String sSection; String sProperty;
		String sExpressionSource; 
		String sExpressionSolved; String sExpressionSolvedTagless;
		IKernelConfigSectionEntryZZZ objEntry; ReferenceZZZ<IKernelConfigSectionEntryZZZ>objSectionEntryReference;
	
		String sValue;
		String sTagStartZ = "<Z>";
		String sTagEndZ = "</Z>";	
		
		try {		
			String sHostName = EnvironmentZZZ.getHostName();
			assertNotNull(sHostName);
		
			//+++++++ VORGEZOGENER LETZTER FEHLERTEST START
			
			sExpressionSource = KernelCallIniSolverZZZTest.sEXPRESSION_CALL01_DEFAULT;
			sExpressionSolved = "<Z>"+sHostName+"</Z>";			
			btemp = testCompute_CallJava_(sExpressionSource, sExpressionSolved, false, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
			
			
			//+++++++ VORGEZOGENER LETZTER FEHLERTEST ENDE
			
			//###########################
		    //### objExpression
			//#########################
			
			//+ Wichtige Hilfsmethode pruefen, wichtig ist false am Ende, damit wird von aussen nach innen das Tag entfernt.
			sExpressionSource = "<Z><Z:Call><Z:Java><Z:Class><Z>xyz</Z></Z:Class><Z:Method><Z>abcde</Z></Z:Method></Z:Java></Z:Call></Z>"; //INI-Pfade werden trotzdem ersetzt
			sExpressionSolvedTagless = "<Z:Call><Z:Java><Z:Class><Z>xyz</Z></Z:Class><Z:Method><Z>abcde</Z></Z:Method></Z:Java></Z:Call>"; //INI-Pfade werden trotzdem ersetzt //INI-Pfade werden trotzdem ersetzt
			sValue = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSource, sTagStartZ, sTagEndZ, false);
			assertEquals(sExpressionSolvedTagless, sValue);
			
			//+ Voraussetzungen fuer Aufruf in .ini pruefen
			String sClassName = null;
			String sMethodName = null;
			
			String sSectionParameter = "ArgumentSection for testCallComputed";
			String sProperty_className = "JavaClass";
			String sExpressionSolved_className = "basic.zBasic.util.machine.EnvironmentZZZ";
			objEntry = objFileIniTest.getPropertyValueDirectLookup(sSectionParameter, sProperty_className);
			sValue = objEntry.getValue();
			assertEquals(sExpressionSolved_className, sValue);
			sClassName = sValue;
			
			String sProperty_methodName = "JavaMethod";
			String sExpressionSolved_methodName = "getHostName";
			objEntry = objFileIniTest.getPropertyValueDirectLookup(sSectionParameter, sProperty_methodName);
			sValue = objEntry.getValue();
			assertEquals(sExpressionSolved_methodName, sValue);
			sMethodName = sValue;
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//+++ Ohne jegliche Expression-Berechnung
			sExpressionSource = KernelCallIniSolverZZZTest.sEXPRESSION_CALL01_DEFAULT;
			sExpressionSolved = sExpressionSource;
			btemp = testCompute_CallJava_Unsolved_(sExpressionSource, sExpressionSolved, false, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
			
			sExpressionSource = KernelCallIniSolverZZZTest.sEXPRESSION_CALL01_DEFAULT;
			sExpressionSolved = sExpressionSource; 			
			btemp = testCompute_CallJava_Unsolved_(sExpressionSource, sExpressionSolved, false, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
			
			sExpressionSource = KernelCallIniSolverZZZTest.sEXPRESSION_CALL01_DEFAULT;
			sExpressionSolved = sExpressionSource;			
			btemp = testCompute_CallJava_Unsolved_(sExpressionSource, sExpressionSolved, true, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
						
			sExpressionSource = KernelCallIniSolverZZZTest.sEXPRESSION_CALL01_DEFAULT;
			sExpressionSolved = sExpressionSource;
			btemp = testCompute_CallJava_Unsolved_(sExpressionSource, sExpressionSolved, true, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//+++ Ohne Call-Berechung
			sExpressionSource = KernelCallIniSolverZZZTest.sEXPRESSION_CALL01_DEFAULT;
			sExpressionSolved = "<Z><Z:Call><Z:Java><Z:Class>" + sClassName + "</Z:Class><Z:Method>" + sMethodName +"</Z:Method></Z:Java></Z:Call></Z>"; //INI-Pfade werden trotzdem ersetzt
			btemp = testCompute_CallJava_SolverUnsolved_(sExpressionSource, sExpressionSolved, false, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
			
			sExpressionSource = KernelCallIniSolverZZZTest.sEXPRESSION_CALL01_DEFAULT;
			sExpressionSolved = "<Z><Z:Call><Z:Java><Z:Class>" + sClassName + "</Z:Class><Z:Method>" + sMethodName +"</Z:Method></Z:Java></Z:Call></Z>"; //INI-Pfade werden trotzdem ersetzt 			
			btemp = testCompute_CallJava_SolverUnsolved_(sExpressionSource, sExpressionSolved, false, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
			
			sExpressionSource = KernelCallIniSolverZZZTest.sEXPRESSION_CALL01_DEFAULT;
			sExpressionSolved = "<Z><Z:Call><Z:Java><Z:Class>" + sClassName + "</Z:Class><Z:Method>" + sMethodName +"</Z:Method></Z:Java></Z:Call></Z>"; //INI-Pfade werden trotzdem ersetzt
			sExpressionSolvedTagless = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ, false);
			btemp = testCompute_CallJava_SolverUnsolved_(sExpressionSource, sExpressionSolvedTagless, true, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
					
			sExpressionSource = KernelCallIniSolverZZZTest.sEXPRESSION_CALL01_DEFAULT;
			sExpressionSolved = "<Z><Z:Call><Z:Java><Z:Class>" + sClassName + "</Z:Class><Z:Method>" + sMethodName +"</Z:Method></Z:Java></Z:Call></Z>"; //INI-Pfade werden trotzdem ersetzt
			sExpressionSolvedTagless = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ, false);
			btemp = testCompute_CallJava_SolverUnsolved_(sExpressionSource, sExpressionSolvedTagless, true, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
			
			
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//+++ Mit CALL-Berechnung
			sExpressionSource = KernelCallIniSolverZZZTest.sEXPRESSION_CALL01_DEFAULT;
			sExpressionSolved = "<Z><Z:Call><Z:Java><Z:Class>basic.zBasic.util.machine.EnvironmentZZZ</Z:Class><Z:Method>getHostName</Z:Method></Z:Java></Z:Call></Z>";						
			btemp = testCompute_CallJava_(sExpressionSource, sExpressionSolved, false, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
						
			sExpressionSource = KernelCallIniSolverZZZTest.sEXPRESSION_CALL01_DEFAULT;
			sExpressionSolved = "<Z>" + sHostName + "</Z>";			
			btemp = testCompute_CallJava_(sExpressionSource, sExpressionSolved, false, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
			
			sExpressionSource = KernelCallIniSolverZZZTest.sEXPRESSION_CALL01_DEFAULT;
			sExpressionSolved = "<Z:Call><Z:Java><Z:Class>basic.zBasic.util.machine.EnvironmentZZZ</Z:Class><Z:Method>getHostName</Z:Method></Z:Java></Z:Call>";
			sExpressionSolvedTagless = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
			btemp = testCompute_CallJava_(sExpressionSource, sExpressionSolvedTagless, true, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
						
			sExpressionSource = KernelCallIniSolverZZZTest.sEXPRESSION_CALL01_DEFAULT;
			sExpressionSolved = sHostName;
			sExpressionSolvedTagless = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
			btemp = testCompute_CallJava_(sExpressionSource, sExpressionSolvedTagless, true, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
			
			
			//#####################################
			//### INI FILE
			//### Merke: Auf dieser obersten Ebene kann man NICHT steuern "mit oder ohne umgebende Tags".
			//#####################################
						
			//+++ ohne CALL-Berechnung
			sSection = "Section for testCall";
			sProperty = "WertCalled";
			sExpressionSolved = "<Z><Z:Call><Z:Java><Z:Class><Z>[ArgumentSection for testCallComputed]JavaClass</Z></Z:Class><Z:Method><Z>[ArgumentSection for testCallComputed]JavaMethod</Z></Z:Method></Z:Java></Z:Call></Z>";
			btemp = testCompute_CallJava_IniUsedExpressionUnsolved_(sSection, sProperty, sExpressionSolved);
			
				
			//+++ ohne EXPRESSION - SOLVER - Berechnung
			sSection = "Section for testCall";
			sProperty = "WertCalled";
			sExpressionSolved = "<Z:Call><Z:Java><Z:Class>basic.zBasic.util.machine.EnvironmentZZZ</Z:Class><Z:Method>getHostName</Z:Method></Z:Java></Z:Call>";
			btemp = testCompute_CallJava_IniUsedUnsolved_(sSection, sProperty, sExpressionSolved);	
			
			//+++ mit CALL-Berechnung
			sSection = "Section for testCall";
			sProperty = "WertCalled";
			sExpressionSolved=EnvironmentZZZ.getHostName();
			btemp = testCompute_CallJava_IniUsed_(sSection, sProperty, sExpressionSolved);
							
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}		
	}
	
	private boolean testCompute_CallJava_Unsolved_(String sExpressionSourceIn, String sExpressionSolvedIn, boolean bRemoveSuroundingSeparators, IEnumSetMappedTestCaseZZZ objEnumTestCase) {
		boolean bReturn = false;
		try {
			boolean btemp; 
			
			String sExpressionSource; 
			String sExpressionSolved; String sValue;				
			ReferenceZZZ<IKernelConfigSectionEntryZZZ> objSectionEntryReference;
			IKernelConfigSectionEntryZZZ objEntry;
			
			
			//####################################################################################
			//### EXPRESSION - NICHT EXPRESSION BEHANDLUNG .solve
			//####################################################################################
			
			//Anwenden der ersten Formel, ohne Berechnung oder Formelersetzung						
			sExpressionSource = sExpressionSourceIn;
			sExpressionSolved = sExpressionSolvedIn;
			
			btemp = objExpressionHandler.setFlag(IIniTagWithExpressionZZZ.FLAGZ.USEEXPRESSION, false); 
			assertTrue("Flag nicht vorhanden '" + IIniTagWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);

			btemp = objExpressionHandler.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH, true);			
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
		
			btemp = objExpressionHandler.setFlag(IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE, true);			
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE + "'", btemp);
						
			btemp = objExpressionHandler.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER, true); //soll dann egal sein
			assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
			
			btemp = objExpressionHandler.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA, true); //soll dann egal sein
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA + "'", btemp);
			
			btemp = objExpressionHandler.setFlag(IKernelCallIniSolverZZZ.FLAGZ.USECALL, true); //soll dann egal sein
			assertTrue("Flag nicht vorhanden '" + IKernelCallIniSolverZZZ.FLAGZ.USECALL + "'", btemp);
			
			btemp = objExpressionHandler.setFlag(IKernelJavaCallIniSolverZZZ.FLAGZ.USECALL_JAVA, true);
			assertTrue("Flag nicht vorhanden '" + IKernelJavaCallIniSolverZZZ.FLAGZ.USECALL_JAVA + "'", btemp);
			
			
			//+++ ... parse ist nicht solve... also wird hier nichts aufgeloest, aussser die Pfade
			if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.PARSE)){
				sExpressionSource = sExpressionSourceIn;
				sExpressionSolved = sExpressionSolvedIn;		
				objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
				sValue = objExpressionHandler.parse(sExpressionSource, objSectionEntryReference, bRemoveSuroundingSeparators);
				assertEquals(sExpressionSolved, sValue);
				
				objEntry = objSectionEntryReference.get();
				assertNotNull(objEntry);
				assertFalse(objEntry.isCall());
				assertFalse(objEntry.isJavaCall());
				assertNull(objEntry.getCallingClassname());
				assertNull(objEntry.getCallingMethodname());
			}
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
				
			//+++ ... solve verhält sich NICHT wie parse(), bei solve wird aufgeloest...
			if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.SOLVE)){
				sExpressionSource = sExpressionSourceIn;
				sExpressionSolved = sExpressionSolvedIn;
				objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
				sValue = objExpressionHandler.solve(sExpressionSource, objSectionEntryReference, bRemoveSuroundingSeparators);
				assertEquals(sExpressionSolved, sValue);
				
				objEntry = objSectionEntryReference.get();
				assertNotNull(objEntry);
				assertFalse(objEntry.isCall());
				assertFalse(objEntry.isJavaCall());
				assertNull(objEntry.getCallingClassname());
				assertNull(objEntry.getCallingMethodname());
			}
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			
			
			
			bReturn = true;
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}
		return bReturn;
	}
	
	private boolean testCompute_CallJava_SolverUnsolved_(String sExpressionSourceIn, String sExpressionSolvedIn, boolean bRemoveSuroundingSeparators, IEnumSetMappedTestCaseZZZ objEnumTestCase) {
		boolean bReturn = false;
		try {
			boolean btemp; 
			
			String sExpressionSource; 
			String sExpressionSolved; String sValue;				
			ReferenceZZZ<IKernelConfigSectionEntryZZZ> objSectionEntryReference;
			IKernelConfigSectionEntryZZZ objEntry;
			
			
			//####################################################################################
			//### EXPRESSION - CALL NICHT .solve
			//####################################################################################
						
			//Anwenden des Ausdrucks ohne Solver - Aufruf
			btemp = objExpressionHandler.setFlag(IIniTagWithExpressionZZZ.FLAGZ.USEEXPRESSION, true); 
			assertTrue("Flag nicht vorhanden '" + IIniTagWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
			
			btemp = objExpressionHandler.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH, true);			
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
		
			btemp = objExpressionHandler.setFlag(IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE, true);			
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE + "'", btemp);
			
			btemp = objExpressionHandler.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER, false); 
			assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
			
			btemp = objExpressionHandler.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA, true); //soll dann egal sein
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA + "'", btemp);
			
			btemp = objExpressionHandler.setFlag(IKernelCallIniSolverZZZ.FLAGZ.USECALL, true); //soll dann egal sein
			assertTrue("Flag nicht vorhanden '" + IKernelCallIniSolverZZZ.FLAGZ.USECALL + "'", btemp);
			
			btemp = objExpressionHandler.setFlag(IKernelJavaCallIniSolverZZZ.FLAGZ.USECALL_JAVA, true);
			assertTrue("Flag nicht vorhanden '" + IKernelJavaCallIniSolverZZZ.FLAGZ.USECALL_JAVA + "'", btemp);
						
			//+++ ... parse ist nicht solve... also wird hier nichts aufgeloest, aussser die Pfade
			if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.PARSE)) {
				sExpressionSource = sExpressionSourceIn;
				sExpressionSolved = sExpressionSolvedIn;		
				objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
				sValue = objExpressionHandler.parse(sExpressionSource, objSectionEntryReference, bRemoveSuroundingSeparators);
				assertEquals(sExpressionSolved, sValue);
			}
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
				
			//+++ ... solve verhält sich NICHT wie parse(), bei solve wird aufgeloest...
			if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.SOLVE)) {
				sExpressionSource = sExpressionSourceIn;
				sExpressionSolved = sExpressionSolvedIn;
				objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
				sValue = objExpressionHandler.solve(sExpressionSource, objSectionEntryReference, bRemoveSuroundingSeparators);
				assertEquals(sExpressionSolved, sValue);
			}
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			
			bReturn = true;
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}
		return bReturn;
	}
	
	private boolean testCompute_CallJava_(String sExpressionSourceIn, String sExpressionSolvedIn, boolean bRemoveSuroundingSeparators, IEnumSetMappedTestCaseZZZ objEnumTestCase) {
		boolean bReturn = false;
		try {
			boolean btemp; 
			
			String sExpressionSource; 
			String sExpressionSolved; String sValue;				
			ReferenceZZZ<IKernelConfigSectionEntryZZZ> objSectionEntryReference;
			IKernelConfigSectionEntryZZZ objEntry;
			
			
			//####################################################################################
			//### EXPRESSION .solve
			//####################################################################################
			
			//Anwenden der ersten Formel, ohne Berechnung oder Formelersetzung						
			sExpressionSource = sExpressionSourceIn;
			sExpressionSolved = sExpressionSolvedIn;
			
			btemp = objExpressionHandler.setFlag(IIniTagWithExpressionZZZ.FLAGZ.USEEXPRESSION, true); 
			assertTrue("Flag nicht vorhanden '" + IIniTagWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);

			btemp = objExpressionHandler.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH, true);			
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
		
			btemp = objExpressionHandler.setFlag(IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE, true);			
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE + "'", btemp);
									
			btemp = objExpressionHandler.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER, true); //soll dann egal sein
			assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
			
			btemp = objExpressionHandler.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA, true); //soll dann egal sein
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA + "'", btemp);
			
			btemp = objExpressionHandler.setFlag(IKernelCallIniSolverZZZ.FLAGZ.USECALL, true); //soll dann egal sein
			assertTrue("Flag nicht vorhanden '" + IKernelCallIniSolverZZZ.FLAGZ.USECALL + "'", btemp);
			
			btemp = objExpressionHandler.setFlag(IKernelJavaCallIniSolverZZZ.FLAGZ.USECALL_JAVA, true);
			assertTrue("Flag nicht vorhanden '" + IKernelJavaCallIniSolverZZZ.FLAGZ.USECALL_JAVA + "'", btemp);
			
			
			//+++ ... parse ist nicht solve... also wird hier nichts aufgeloest, aussser die Pfade substituiert.
			if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.PARSE)) {			
				sExpressionSource = sExpressionSourceIn;
				sExpressionSolved = sExpressionSolvedIn;		
				objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
				sValue = objExpressionHandler.parse(sExpressionSource, objSectionEntryReference, bRemoveSuroundingSeparators);
				assertEquals(sExpressionSolved, sValue);
				
				objEntry = objSectionEntryReference.get();
				assertNotNull(objEntry);
				assertFalse(objEntry.isCall());
				assertFalse(objEntry.isJavaCall());
				assertNull(objEntry.getCallingClassname());
				assertNull(objEntry.getCallingMethodname());
			}
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
				
			//+++ ... solve verhält sich NICHT wie parse(), bei solve wird aufgeloest...
			//+++ ... parse ist nicht solve... also wird hier nichts aufgeloest, aussser die Pfade substituiert.
			if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.SOLVE)) {
				sExpressionSource = sExpressionSourceIn;
				sExpressionSolved = sExpressionSolvedIn;
				objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
				sValue = objExpressionHandler.solve(sExpressionSource, objSectionEntryReference, bRemoveSuroundingSeparators);
				assertEquals(sExpressionSolved, sValue);
				
				objEntry = objSectionEntryReference.get();
				assertNotNull(objEntry);
				assertTrue(objEntry.isCall());
				assertTrue(objEntry.isJavaCall());
				assertNotNull(objEntry.getCallingClassname());
				assertNotNull(objEntry.getCallingMethodname());
			}
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		
			bReturn = true;
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}
		return bReturn;
	}
		
	private boolean testCompute_CallJava_IniUsedExpressionUnsolved_(String sSectionIn, String sPropertyIn, String sExpressionSolvedIn) {
		boolean bReturn = false;
		try {
			boolean btemp; String stemp;
			
			String sSection; String sProperty; 
			String sExpressionSolved; String sValue;				
			ReferenceZZZ<IKernelConfigSectionEntryZZZ> objSectionEntryReference;
			IKernelConfigSectionEntryZZZ objEntry;
			
			
			//####################################################################################
			//### EXPRESSION - INI Handler .solve
			//####################################################################################
			
			//Anwenden der ersten Formel, ohne Berechnung oder Formelersetzung						
			sSection = sSectionIn;
			sProperty = sPropertyIn;
			sExpressionSolved = sExpressionSolvedIn;
			
			//Anwenden der ersten Formel, ohne Berechnung oder Formelersetzung
			btemp = objFileIniTest.setFlag(IIniTagWithExpressionZZZ.FLAGZ.USEEXPRESSION, false); 
			assertTrue("Flag nicht vorhanden '" + IIniTagWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
							
			btemp = objFileIniTest.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER, true); //sollte dann egal sein
			assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
			
			btemp = objFileIniTest.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA, true); 
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA + "'", btemp);
			
			btemp = objFileIniTest.setFlag(IKernelCallIniSolverZZZ.FLAGZ.USECALL, true); //soll dann egal sein
			assertTrue("Flag nicht vorhanden '" + IKernelCallIniSolverZZZ.FLAGZ.USECALL + "'", btemp);
			
			btemp = objFileIniTest.setFlag(IKernelJavaCallIniSolverZZZ.FLAGZ.USECALL_JAVA, true);
			assertTrue("Flag nicht vorhanden '" + IKernelJavaCallIniSolverZZZ.FLAGZ.USECALL_JAVA + "'", btemp);
			
			btemp = objFileIniTest.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH, true);			
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
		
			btemp = objFileIniTest.setFlag(IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE, true);			
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE + "'", btemp);
	
			sValue = objFileIniTest.getPropertyValue(sSection, sProperty).getValue();
			assertEquals(sExpressionSolved, sValue);
			
			objEntry = objFileIniTest.getEntry();
			assertNotNull(objEntry);
			assertNotNull(objEntry);
			assertFalse(objEntry.isCall());
			assertFalse(objEntry.isJavaCall());
						
			//###############################################################
						
			bReturn = true;
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}
		return bReturn;
	}
	
	private boolean testCompute_CallJava_IniUsedUnsolved_(String sSectionIn, String sPropertyIn, String sExpressionSolvedIn) {
		boolean bReturn = false;
		try {
			boolean btemp; String stemp;
			
			String sSection; String sProperty; 
			String sExpressionSolved; String sValue;				
			ReferenceZZZ<IKernelConfigSectionEntryZZZ> objSectionEntryReference;
			IKernelConfigSectionEntryZZZ objEntry;
			
			
			//####################################################################################
			//### EXPRESSION - INI Handler .solve
			//####################################################################################
		
			//########################################################################################
			//Anwenden der ersten Formel, ohne Berechnung oder Formelersetzung						
			sSection = sSectionIn;
			sProperty = sPropertyIn;
			sExpressionSolved = sExpressionSolvedIn;
			
			//Anwenden der ersten Formel, Mit Berechnung oder Formelersetzung
			btemp = objFileIniTest.setFlag(IIniTagWithExpressionZZZ.FLAGZ.USEEXPRESSION, true); 
			assertTrue("Flag nicht vorhanden '" + IIniTagWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
			
			btemp = objFileIniTest.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH, true);			
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
		
			btemp = objFileIniTest.setFlag(IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE, true);			
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE + "'", btemp);
			
			btemp = objFileIniTest.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER, false); 
			assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
			
			btemp = objFileIniTest.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA, true); 
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA + "'", btemp);
			
			btemp = objFileIniTest.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH,true); 
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH + "'", btemp);
			
			btemp = objFileIniTest.setFlag(IKernelCallIniSolverZZZ.FLAGZ.USECALL, true); //soll dann egal sein
			assertTrue("Flag nicht vorhanden '" + IKernelCallIniSolverZZZ.FLAGZ.USECALL + "'", btemp);
			
			btemp = objFileIniTest.setFlag(IKernelJavaCallIniSolverZZZ.FLAGZ.USECALL_JAVA, true);
			assertTrue("Flag nicht vorhanden '" + IKernelJavaCallIniSolverZZZ.FLAGZ.USECALL_JAVA + "'", btemp);
			
			sValue = objFileIniTest.getPropertyValue(sSection, sProperty).getValue();
			assertEquals(sExpressionSolved, sValue);
			
			objEntry = objFileIniTest.getEntry();
			assertNotNull(objEntry);
			assertFalse(objEntry.isCall());
			assertFalse(objEntry.isJavaCall());
					
			//###############################################################
						
			bReturn = true;
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}
		return bReturn;
	}

	
	
	private boolean testCompute_CallJava_IniUsed_(String sSectionIn, String sPropertyIn, String sExpressionSolvedIn) {
		boolean bReturn = false;
		try {
			boolean btemp; String stemp;
			
			String sSection; String sProperty; String sExpressionSolved; String sValue;				
			ReferenceZZZ<IKernelConfigSectionEntryZZZ> objSectionEntryReference;
			IKernelConfigSectionEntryZZZ objEntry;
			
			
			//####################################################################################
			//### EXPRESSION - INI Handler
			//####################################################################################
			
			//Anwenden der ersten Formel, MIT BERECHNUNG				
			sSection = sSectionIn;
			sProperty = sPropertyIn;
			sExpressionSolved = sExpressionSolvedIn;
			
			//Anwenden der ersten Formel, Mit Berechnung oder Formelersetzung
			btemp = objFileIniTest.setFlag(IIniTagWithExpressionZZZ.FLAGZ.USEEXPRESSION, true); 
			assertTrue("Flag nicht vorhanden '" + IIniTagWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
			
			btemp = objFileIniTest.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH, true);			
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
		
			btemp = objFileIniTest.setFlag(IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE, true);			
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE + "'", btemp);
			
			btemp = objFileIniTest.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER, true); 
			assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
			
			btemp = objFileIniTest.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA, true); 
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA + "'", btemp);
			
			btemp = objFileIniTest.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH,false); //ohne Mathematische Berechnung, sollte daher egal sein
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH + "'", btemp);
			
			btemp = objFileIniTest.setFlag(IKernelCallIniSolverZZZ.FLAGZ.USECALL, true); //soll dann egal sein
			assertTrue("Flag nicht vorhanden '" + IKernelCallIniSolverZZZ.FLAGZ.USECALL + "'", btemp);
			
			btemp = objFileIniTest.setFlag(IKernelJavaCallIniSolverZZZ.FLAGZ.USECALL_JAVA, true);
			assertTrue("Flag nicht vorhanden '" + IKernelJavaCallIniSolverZZZ.FLAGZ.USECALL_JAVA + "'", btemp);
			
			sValue = objFileIniTest.getPropertyValue(sSection, sProperty).getValue();
			assertEquals(sExpressionSolved, sValue);
			
			IKernelConfigSectionEntryZZZ objSectionEntry = objFileIniTest.getEntry();
			assertNotNull(objSectionEntry);
			assertTrue(objSectionEntry.isCall());
			assertTrue(objSectionEntry.isJavaCall());
					
			bReturn = true;
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}
		return bReturn;
	}
	
	/**void, Test: Reading an entry in a section of the ini-file
	 * 
	 * @author Fritz Lindhauer, 05.05.2023, 08:54:30
	 */
	public void testCompute_CallJavaEntry_Detail(){

//		try {
			boolean btemp; 
			
			String sExpressionSource; 
			String sExpressionSolved; String sValue;				
			ReferenceZZZ<IKernelConfigSectionEntryZZZ> objSectionEntryReference;
			IKernelConfigSectionEntryZZZ objEntry;
		
			String sFormulaSolvedAndConverted; String sFormulaSolvedAndConvertedAsExpression;
			//TODO 20241011: Source und Solved und solvedTagless etc. an die Methoden von aussen uebergeben, so dass die verschiednesten String sgetestet werden koennen.
			
			//+++++++ VORGEZOGENER LETZTER FEHLERTEST START
			
			btemp = testCompute_CallJavaEntry_Detail_SolverUnsolved_();
			
			//+++++++ VORGEZOGENER LETZTER FEHLERTEST ENDE
			
			
			
			btemp = testCompute_CallJavaEntry_Detail_SolverUnsolved_();
			
			btemp = testCompute_CallJavaEntry_Detail_CallUnsolved_();
			
			btemp = testCompute_CallJavaEntry_Detail_();
		
//		} catch (ExceptionZZZ ez) {
//			fail("Method throws an exception." + ez.getMessageLast());
//		}		
	}
	
	private boolean testCompute_CallJavaEntry_Detail_SolverUnsolved_() {
		boolean bReturn = false;
		
		try {
			boolean btemp; 
			
			String sExpressionSource; 
			String sExpressionSolved; String sValue;				
			ReferenceZZZ<IKernelConfigSectionEntryZZZ> objSectionEntryReference;
			IKernelConfigSectionEntryZZZ objEntry;
		
			String sFormulaSolvedAndConverted; String sFormulaSolvedAndConvertedAsExpression;
			
			
			
			//###########################################################
			//Anwenden der ersten Formel, ohne Berechnung
			btemp = objFileIniTest.setFlag(IIniTagWithExpressionZZZ.FLAGZ.USEEXPRESSION, true); 
			assertTrue("Flag nicht vorhanden '" + IIniTagWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);

			btemp = objFileIniTest.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH, true);			
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
		
			btemp = objFileIniTest.setFlag(IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE, true);			
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE + "'", btemp);
			
			btemp = objFileIniTest.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER, false);
			assertTrue("Das Flag 'useexpression' sollte zur Verfügung stehen.", btemp);
				
			btemp = objFileIniTest.setFlag(IKernelCallIniSolverZZZ.FLAGZ.USECALL.name(), true); //Ansonsten wird der Wert sofort ausgerechnet
			assertTrue("Das Flag 'usecall' sollte zur Verfügung stehen.", btemp);
		
			
			sValue = objFileIniTest.getPropertyValue("Section for testCall", "WertCalled").getValue();
			sExpressionSolved = "<Z:Call><Z:Java><Z:Class>basic.zBasic.util.machine.EnvironmentZZZ</Z:Class><Z:Method>getHostName</Z:Method></Z:Java></Z:Call>";
			assertEquals(sExpressionSolved, sValue);			
			
			//Wert mit Entry-Wert vergleichen
			objEntry = objFileIniTest.getEntry();
			assertNotNull(objEntry);
			assertEquals(sValue, objEntry.getValue());			
			assertTrue(objEntry.isExpression()); //Zumindest die PATH Anweisungen wurden ersetzt
			
			assertFalse(objEntry.isFormula());
			assertFalse(objEntry.isSolved()); //Solver wird nicht ausgeführt. PATH Anweisungen werden auch ohne Solver ersetzt.
			assertFalse(objEntry.isCall());
			assertFalse(objEntry.isJavaCall());
			assertNull(objEntry.getCallingClassname());
			assertNull(objEntry.getCallingMethodname());
			
			//Wert mit speziellen Entry-Formelwert vergleichen
			sFormulaSolvedAndConverted = objEntry.getValueFormulaSolvedAndConverted();
			assertNull(sFormulaSolvedAndConverted); //Da keine Formel enthalten ist
						
			sFormulaSolvedAndConvertedAsExpression = objEntry.getValueFormulaSolvedAndConvertedAsExpression();					
			assertEquals(XmlUtilZZZ.computeTagNull(), sFormulaSolvedAndConvertedAsExpression);//Da keine Formel enthalten ist.

			bReturn = true;
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}
		
		return bReturn;
	}
	
	private boolean testCompute_CallJavaEntry_Detail_CallUnsolved_() {
		boolean bReturn = false;
		
		try {
			boolean btemp; 
			
			String sExpressionSource; 
			String sExpressionSolved; String sValue;				
			ReferenceZZZ<IKernelConfigSectionEntryZZZ> objSectionEntryReference;
			IKernelConfigSectionEntryZZZ objEntry;
		
			String sFormulaSolvedAndConverted; String sFormulaSolvedAndConvertedAsExpression;
			
			
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++		
			//Nur Expression ausrechnen, ist aber unverändert vom reinen Ergebnis her.		
			btemp = objFileIniTest.setFlag(IIniTagWithExpressionZZZ.FLAGZ.USEEXPRESSION, true); 
			assertTrue("Flag nicht vorhanden '" + IIniTagWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);

			btemp = objFileIniTest.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH, true);			
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
		
			btemp = objFileIniTest.setFlag(IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE, true);			
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE + "'", btemp);
							
			btemp = objFileIniTest.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER, true);
			assertTrue("Das Flag 'useexpression' sollte zur Verfügung stehen.", btemp);
			
			btemp = objFileIniTest.setFlag(IKernelCallIniSolverZZZ.FLAGZ.USECALL.name(), false); //Ansonsten wird der Wert sofort ausgerechnet
			assertTrue("Das Flag 'usecall' sollte zur Verfügung stehen.", btemp);
		
			
			sValue = objFileIniTest.getPropertyValue("Section for testCall", "WertCalled").getValue();
			sExpressionSolved = "<Z:Call><Z:Java><Z:Class>basic.zBasic.util.machine.EnvironmentZZZ</Z:Class><Z:Method>getHostName</Z:Method></Z:Java></Z:Call>";
			assertEquals(sExpressionSolved,sValue);
			
			//Wert mit Entry-Wert vergleichen
			objEntry = objFileIniTest.getEntry();
			assertNotNull(objEntry);
			assertEquals(sValue, objEntry.getValue());
			assertTrue(objEntry.isExpression()); //Zumindest die PATH Anweisungen wurden ersetzt
			
			assertFalse(objEntry.isFormula());
			
			//Merke: Der Solver wird zwar ausgefuehrt. INI-Pfade werden auch ausgetauscht
			//       (was kein solve ist, sondern ein substitute!). Aber der JAVACall wird nicht gemacht und daher kein Wert veraendert!
			assertFalse(objEntry.isSolved()); 
			
			assertFalse(objEntry.isCall());
			assertFalse(objEntry.isJavaCall());
			assertNull(objEntry.getCallingClassname());
			assertNull(objEntry.getCallingMethodname());
						
			//Wert mit speziellen Entry-Formelwert vergleichen
			sFormulaSolvedAndConverted = objEntry.getValueFormulaSolvedAndConverted();
			assertNull(sFormulaSolvedAndConverted); //Da keine Formel enthalten ist
						
			sFormulaSolvedAndConvertedAsExpression = objEntry.getValueFormulaSolvedAndConvertedAsExpression();					
			assertEquals(XmlUtilZZZ.computeTagNull(), sFormulaSolvedAndConvertedAsExpression);//Da keine Formel enthalten ist.

			bReturn = true;
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}
		
		return bReturn;
	}
	
	private boolean testCompute_CallJavaEntry_Detail_() {
		boolean bReturn = false;
		
		try {
			boolean btemp; 
			
			String sExpressionSource; 
			String sExpressionSolved; String sValue;				
			ReferenceZZZ<IKernelConfigSectionEntryZZZ> objSectionEntryReference;
			IKernelConfigSectionEntryZZZ objEntry;
		
			String sFormulaSolvedAndConverted; String sFormulaSolvedAndConvertedAsExpression;
			
			
			
			//###################################################		
			//Berechne die erste Formel, DIRECT			
			String sHostName = EnvironmentZZZ.getHostName();
			
			btemp = objExpressionHandler.setFlag(IIniTagWithExpressionZZZ.FLAGZ.USEEXPRESSION, true); 
			assertTrue("Flag nicht vorhanden '" + IIniTagWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);

			btemp = objExpressionHandler.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH, true);			
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
		
			btemp = objExpressionHandler.setFlag(IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE, true);			
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE + "'", btemp);
			
			
			btemp = objExpressionHandler.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER, true);
			assertTrue("Das Flag '"+IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER +"' sollte zur Verfügung stehen.", btemp);
			
			btemp = objExpressionHandler.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA,true);
			assertTrue("Das Flag '"+IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA+"' sollte zur Verfügung stehen.", btemp);
			
			btemp = objExpressionHandler.setFlag(IKernelCallIniSolverZZZ.FLAGZ.USECALL, true);
			assertTrue("Das Flag '"+IKernelCallIniSolverZZZ.FLAGZ.USECALL+"' sollte zur Verfügung stehen.", btemp);			
			
			btemp = objExpressionHandler.setFlag(IKernelJavaCallIniSolverZZZ.FLAGZ.USECALL_JAVA, true);
			assertTrue("Das Flag '"+IKernelJavaCallIniSolverZZZ.FLAGZ.USECALL_JAVA+"' sollte zur Verfügung stehen.", btemp);
			
			String sExpression = KernelCallIniSolverZZZTest.sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT; 
			objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
			sValue = objExpressionHandler.solve(sExpression, objSectionEntryReference);			
			assertEquals(sHostName,sValue);
			
			//Diese Berechnung im Entry Objekt nachvollziehen
			objEntry = objSectionEntryReference.get();
			assertNotNull(objEntry);
			assertEquals(sValue,objEntry.getValue());
			assertTrue(objEntry.isExpression());
			assertFalse(objEntry.isFormula());
			assertTrue(objEntry.isSolved());
			assertTrue(objEntry.isCall());
			assertTrue(objEntry.isJavaCall());
			assertEquals("basic.zBasic.util.machine.EnvironmentZZZ",objEntry.getCallingClassname());
			assertEquals("getHostName",objEntry.getCallingMethodname());
			
			//Wert mit speziellen Entry-Formelwert vergleichen
			sFormulaSolvedAndConverted = objEntry.getValueFormulaSolvedAndConverted();
			assertNull(sFormulaSolvedAndConverted); //Da keine Formel enthalten ist
						
			sFormulaSolvedAndConvertedAsExpression = objEntry.getValueFormulaSolvedAndConvertedAsExpression();					
			assertEquals(XmlUtilZZZ.computeTagNull(), sFormulaSolvedAndConvertedAsExpression);//Da keine Formel enthalten ist.

		
		bReturn = true;
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}
		
		return bReturn;
	}
	
	/** void, Test: Reading an entry in a section of the ini-file
	* Lindhauer; 22.04.2006 12:54:32
	 */
	public void testCompute_Encrypted(){
		TODOGOON20241015;//Mit Unterfunktionen analog zu den anderen Solvern aufbauen
		                 //objExpressionHandler
		                 //iniUsed mit objFileIniTest
		                 //iniUsed_detail mit objfileinitest
		
		try {					
			boolean bFlagAvailable = objExpressionHandler.setFlag(IKernelEncryptionIniSolverZZZ.FLAGZ.USEENCRYPTION.name(), false); //Ansonsten wird der Wert sofort ausgerechnet
			assertTrue("Das Flag '" + IKernelEncryptionIniSolverZZZ.FLAGZ.USEENCRYPTION +"' sollte zur Verfügung stehen.", bFlagAvailable);
			
			//Anwenden der ersten Formel, ohne Berechnung			
			String sExpression = objFileIniTest.getPropertyValue("Section for testEncrypted", "WertAforDecrypt").getValue();
			assertEquals(KernelEncryptionIniSolverZZZTest.sEXPRESSION_ENCRYPTION01_DEFAULT,sExpression);
			
			IKernelConfigSectionEntryZZZ objSectionEntry = objFileIniTest.getEntry();
			assertNotNull(objSectionEntry);
			assertFalse(objSectionEntry.isDecrypted());
			assertFalse(objSectionEntry.isRawEncrypted());
			
			//###################################################
			//Berechne die erste Formel, DIRECT			
			bFlagAvailable = objExpressionHandler.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER, true);
			assertTrue("Das Flag 'useexpression' sollte zur Verfügung stehen.", bFlagAvailable);
			bFlagAvailable = objExpressionHandler.setFlag(IKernelEncryptionIniSolverZZZ.FLAGZ.USEENCRYPTION, true);
			assertTrue("Das Flag 'usejson' sollte zur Verfügung stehen.", bFlagAvailable);
			
			ReferenceZZZ<IKernelConfigSectionEntryZZZ>objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
			String sReturn = objExpressionHandler.solve(sExpression, objSectionEntryReference);
			//assertEquals(10,iReturn); //+10 für die Encryption
			
			objSectionEntry = objSectionEntryReference.get();
			assertNotNull(objSectionEntry);
			assertTrue(objSectionEntry.isDecrypted());
			assertTrue(objSectionEntry.isRawEncrypted());
			
			
			//#####################################################
			bFlagAvailable = objExpressionHandler.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA, true);
			assertTrue("Das Flag 'useformula' sollte zur Verfügung stehen.", bFlagAvailable);				
			sReturn = objExpressionHandler.solve(sExpression, objSectionEntryReference);
			//assertTrue(iReturn==11); //10 für die Encryption  PLUS 1 für die Formelauswertung als String
			assertNotNull(objSectionEntry);
			assertTrue(objSectionEntry.isDecrypted());
			assertTrue(objSectionEntry.isRawEncrypted());
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}		
	}
	
	
	/** void, Test: Reading an entry in a section of the ini-file
	* Lindhauer; 22.04.2006 12:54:32
	 */
	public void testCompute_JsonArray(){

		try {	
			boolean btemp; String stemp;
			
			String sTagStartZ = "<Z>";
			String sTagEndZ = "</Z>";						
			String sExpressionSource; String sExpressionSolved; ArrayList alExpressionSolved;
			String sSection; String sProperty;	
			
			//+++++++ VORGEZOGENER LETZTER FEHLERTEST START
								
			//################################################################
			//### Varianten JSON-ArrayList aufzuloesen
			//#################################################################
			//...
			
			//......
			
			//.........
			
			sExpressionSource = KernelJsonArrayIniSolverZZZTest.sEXPRESSION_JSONARRAY01_DEFAULT;
			alExpressionSolved = new ArrayList();
			alExpressionSolved.add("TESTWERT2DO2JSON01");
			alExpressionSolved.add("TESTWERT2DO2JSON02");
			sExpressionSolved = alExpressionSolved.toString();
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
			btemp = testCompute_JsonArray_(sExpressionSource, sExpressionSolved, alExpressionSolved, true, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
			
			//+++++++ VORGEZOGENER LETZTER FEHLERTEST ENDE
			
			//################################################################
			//### Varianten JSON nicht aufzuloesen
			//#################################################################
			//a)
			sExpressionSource = KernelJsonArrayIniSolverZZZTest.sEXPRESSION_JSONARRAY01_DEFAULT;
			sExpressionSolved = sExpressionSource;
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
			btemp = testCompute_JsonArray_JsonUnsolved_(sExpressionSource, sExpressionSolved, false, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
			
			//b)
			sExpressionSource = KernelJsonArrayIniSolverZZZTest.sEXPRESSION_JSONARRAY01_DEFAULT;
			sExpressionSolved = sExpressionSource;
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
			btemp = testCompute_JsonArray_JsonUnsolved_(sExpressionSource, sExpressionSolved, false, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
			
			//c)
			sExpressionSource = KernelJsonArrayIniSolverZZZTest.sEXPRESSION_JSONARRAY01_DEFAULT;
			sExpressionSolved = sExpressionSource;
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
			btemp = testCompute_JsonArray_JsonUnsolved_(sExpressionSource, sExpressionSolved, true, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
			
			//d)
			sExpressionSource = KernelJsonArrayIniSolverZZZTest.sEXPRESSION_JSONARRAY01_DEFAULT;
			sExpressionSolved = sExpressionSource;
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
			btemp = testCompute_JsonArray_JsonUnsolved_(sExpressionSource, sExpressionSolved, true, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
			
			
			//################################################################
			//### Varianten JSON-ArrayList nicht aufzuloesen
			//#################################################################
			sExpressionSource = KernelJsonArrayIniSolverZZZTest.sEXPRESSION_JSONARRAY01_DEFAULT;
			sExpressionSolved = sExpressionSource;
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
			btemp = testCompute_JsonArray_Unsolved_(sExpressionSource, sExpressionSolved, false, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
			
			sExpressionSource = KernelJsonArrayIniSolverZZZTest.sEXPRESSION_JSONARRAY01_DEFAULT;
			sExpressionSolved = sExpressionSource;
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
			btemp = testCompute_JsonArray_Unsolved_(sExpressionSource, sExpressionSolved, false, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
			
			sExpressionSource = KernelJsonArrayIniSolverZZZTest.sEXPRESSION_JSONARRAY01_DEFAULT;
			sExpressionSolved = sExpressionSource;
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
			btemp = testCompute_JsonArray_Unsolved_(sExpressionSource, sExpressionSolved, true, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
			
			sExpressionSource = KernelJsonArrayIniSolverZZZTest.sEXPRESSION_JSONARRAY01_DEFAULT;
			sExpressionSolved = sExpressionSource;
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
			btemp = testCompute_JsonArray_Unsolved_(sExpressionSource, sExpressionSolved, true, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
			
			//################################################################
			//### Varianten JSON-ArrayList aufzuloesen
			//#################################################################
			sExpressionSource = KernelJsonArrayIniSolverZZZTest.sEXPRESSION_JSONARRAY01_DEFAULT;
			sExpressionSolved = sExpressionSource;
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
			btemp = testCompute_JsonArray_(sExpressionSource, sExpressionSolved, alExpressionSolved, false, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
			
			sExpressionSource = KernelJsonArrayIniSolverZZZTest.sEXPRESSION_JSONARRAY01_DEFAULT;
			alExpressionSolved = new ArrayList();
			alExpressionSolved.add("TESTWERT2DO2JSON01");
			alExpressionSolved.add("TESTWERT2DO2JSON02");			
			sExpressionSolved = "<Z>" + alExpressionSolved.toString() + "</Z>";
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
			btemp = testCompute_JsonArray_(sExpressionSource, sExpressionSolved, alExpressionSolved, false, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
						
			sExpressionSource = KernelJsonArrayIniSolverZZZTest.sEXPRESSION_JSONARRAY01_DEFAULT;						
			sExpressionSolved = sExpressionSource;
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
			btemp = testCompute_JsonArray_(sExpressionSource, sExpressionSolved, alExpressionSolved, true, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
			
			sExpressionSource = KernelJsonArrayIniSolverZZZTest.sEXPRESSION_JSONARRAY01_DEFAULT;
			alExpressionSolved = new ArrayList();
			alExpressionSolved.add("TESTWERT2DO2JSON01");
			alExpressionSolved.add("TESTWERT2DO2JSON02");
			sExpressionSolved = alExpressionSolved.toString();
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
			btemp = testCompute_JsonArray_(sExpressionSource, sExpressionSolved, alExpressionSolved, true, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
			
			//################################################################
			//### Varianten JSON-ArrayList per Section/Property in Ini-File aufloesen
			//#################################################################
			
			sSection = "Section for testJsonArraylist";
			sProperty = "Array1";			
			sExpressionSolved = KernelJsonArrayIniSolverZZZTest.sEXPRESSION_JSONARRAY01_DEFAULT;
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
			btemp = testCompute_JsonArray_IniUsedUnsolved_(sSection, sProperty, sExpressionSolved);
			
			sSection = "Section for testJsonArraylist";
			sProperty = "Array1";
			alExpressionSolved = new ArrayList();
			alExpressionSolved.add("TESTWERT2DO2JSON01");
			alExpressionSolved.add("TESTWERT2DO2JSON02");			
			btemp = testCompute_JsonArray_IniUsed_Detail_(sSection, sProperty, alExpressionSolved);
									
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}		
		
		//TODOGOON20241014;//mache Methode wie testCompute_IniUsed_   bzw. testCompute_IniUsedUnsolved_
		
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
	
	private boolean testCompute_JsonArray_(String sExpressionSourceIn, String sExpressionSolvedIn, ArrayList alExpressionSolvedIn, boolean bRemoveSuroundingSeparators, IEnumSetMappedTestCaseZZZ objEnumTestCase) {
		boolean bReturn = false;
		
		main:{
			try {
				boolean btemp; String stemp;
				
				String sExpressionSource; String sExpressionSolved; ArrayList alExpressionSolved; String sValue;				
				ReferenceZZZ<IKernelConfigSectionEntryZZZ> objSectionEntryReference;
				
				//... mit Berechnung PATH
				btemp = objExpressionHandler.setFlag(IIniTagWithExpressionZZZ.FLAGZ.USEEXPRESSION, true); 
				assertTrue("Flag nicht vorhanden '" + IIniTagWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
				
				btemp = objExpressionHandler.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH,true);  
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
				
				btemp = objExpressionHandler.setFlag(IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE,true);  
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE + "'", btemp);
								
				btemp = objExpressionHandler.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER, true); 
				assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
				
				btemp = objExpressionHandler.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA,true);
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA + "'", btemp);
			
				//... mit Berechnung MATH
				btemp = objExpressionHandler.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH, true); 
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH + "'", btemp);
					
				//... mit Berechnung JSON
				btemp = objExpressionHandler.setFlag(IKernelJsonIniSolverZZZ.FLAGZ.USEJSON, true); 
				assertTrue("Das Flag '" + IKernelJsonIniSolverZZZ.FLAGZ.USEJSON +"' sollte zur Verfügung stehen.", btemp);
								
				//... mit Berechnung JSON-ARRAY
				btemp = objExpressionHandler.setFlag(IKernelJsonArrayIniSolverZZZ.FLAGZ.USEJSON_ARRAY, true);//Ansonsten wird der Wert sofort ausgerechnet 
				assertTrue("Das Flag '" + IKernelJsonArrayIniSolverZZZ.FLAGZ.USEJSON_ARRAY +"' sollte zur Verfügung stehen.", btemp);
				
				
				//+++ ... parse ist nicht solve... also wird hier nichts aufgeloest, aussser die Pfade
				if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.PARSE)) {
					sExpressionSource = sExpressionSourceIn;
					alExpressionSolved = alExpressionSolvedIn;		
					sExpressionSolved = sExpressionSolvedIn; //hmExpressionSolved.toString();
					objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
					sValue = objExpressionHandler.parse(sExpressionSource, objSectionEntryReference, bRemoveSuroundingSeparators);
					assertEquals(sExpressionSolved, sValue);
				}
				//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
					
				//+++ ... solve verhält sich NICHT wie parse(), bei solve wird aufgeloest...
				if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.SOLVE)) {
					sExpressionSource = sExpressionSourceIn;
					alExpressionSolved = alExpressionSolvedIn;
					sExpressionSolved = sExpressionSolvedIn; //hmExpressionSolved.toString();
					objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
					sValue = objExpressionHandler.solve(sExpressionSource, objSectionEntryReference, bRemoveSuroundingSeparators);
					assertEquals(sExpressionSolved, sValue);
				}
				//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

				bReturn = true;
			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			}
		}//end main:
		return bReturn;
	}
	
	private boolean testCompute_JsonArray_Unsolved_(String sExpressionSourceIn, String sExpressionSolvedIn, boolean bRemoveSuroundingSeparators, IEnumSetMappedTestCaseZZZ objEnumTestCase) {
		boolean bReturn = false;
		
		main:{
			try {
				boolean btemp; String stemp;
				
				String sExpressionSource; ArrayList alExpressionSolved; String sExpressionSolved; String sValue;				
				ReferenceZZZ<IKernelConfigSectionEntryZZZ> objSectionEntryReference;
				
				btemp = objExpressionHandler.setFlag(IIniTagWithExpressionZZZ.FLAGZ.USEEXPRESSION, true); 
				assertTrue("Flag nicht vorhanden '" + IIniTagWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
				
				btemp = objExpressionHandler.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH,true);  
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
				
				btemp = objExpressionHandler.setFlag(IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE,true);  
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE + "'", btemp);
								
				btemp = objExpressionHandler.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER, true); 
				assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
				
				//... mit Formelberechnung
				btemp = objExpressionHandler.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA,true);
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA + "'", btemp);
			
				//... mit Berechnung MATH
				btemp = objExpressionHandler.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH, true); 
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH + "'", btemp);
				
				//... mit Berechnung JSON
				btemp = objExpressionHandler.setFlag(IKernelJsonIniSolverZZZ.FLAGZ.USEJSON, true); 
				assertTrue("Das Flag '" + IKernelJsonIniSolverZZZ.FLAGZ.USEJSON +"' sollte zur Verfügung stehen.", btemp);
								
				//... ohne Berechnung JSON-ARRAY
				btemp = objExpressionHandler.setFlag(IKernelJsonArrayIniSolverZZZ.FLAGZ.USEJSON_ARRAY, false);//Ansonsten wird der Wert sofort ausgerechnet 
				assertTrue("Das Flag '" + IKernelJsonMapIniSolverZZZ.FLAGZ.USEJSON_MAP +"' sollte zur Verfügung stehen.", btemp);
								
				//+++ ... solve verhält sich NICHT wie parse(), bei solve wird aufgeloest...
				if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.PARSE)) {
					sExpressionSource = sExpressionSourceIn;					
					sExpressionSolved = sExpressionSolvedIn;
					objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
					sValue = objExpressionHandler.solve(sExpressionSource, objSectionEntryReference, bRemoveSuroundingSeparators);
					assertEquals(sExpressionSolved, sValue);
				}
				//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

				//+++ ... parse ist nicht solve... also wird hier nichts aufgeloest, aussser die Pfade
				if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.SOLVE)) {
					sExpressionSource = sExpressionSourceIn;							
					sExpressionSolved = sExpressionSolvedIn;
					objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
					sValue = objExpressionHandler.parse(sExpressionSource, objSectionEntryReference, bRemoveSuroundingSeparators);
					assertEquals(sExpressionSolved, sValue);
				}
				//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
					
				
				bReturn = true;
			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			}
		}//end main:
		return bReturn;
	}
	
	private boolean testCompute_JsonArray_JsonUnsolved_(String sExpressionSourceIn, String sExpressionSolvedIn, boolean bRemoveSuroundingSeparators, IEnumSetMappedTestCaseZZZ objEnumTestCase) {
		boolean bReturn = false;
		
		main:{
			try {
				boolean btemp; String stemp;
				
				String sExpressionSource; ArrayList alExpressionSolved; String sExpressionSolved; String sValue;				
				ReferenceZZZ<IKernelConfigSectionEntryZZZ> objSectionEntryReference;
				
				btemp = objExpressionHandler.setFlag(IIniTagWithExpressionZZZ.FLAGZ.USEEXPRESSION, true); 
				assertTrue("Flag nicht vorhanden '" + IIniTagWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
				
				btemp = objExpressionHandler.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH,true);  
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
				
				btemp = objExpressionHandler.setFlag(IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE,true);  
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE + "'", btemp);
								
				btemp = objExpressionHandler.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER, true); 
				assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
				
				//... mit Formelberechnung
				btemp = objExpressionHandler.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA,true);
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA + "'", btemp);
			
				//... mit Berechnung MATH
				btemp = objExpressionHandler.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH, true); 
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH + "'", btemp);
				
				//... ohne Berechnung JSON
				btemp = objExpressionHandler.setFlag(IKernelJsonIniSolverZZZ.FLAGZ.USEJSON, false); //Ansonsten wird der Wert sofort ausgerechnet
				assertTrue("Das Flag '" + IKernelJsonIniSolverZZZ.FLAGZ.USEJSON +"' sollte zur Verfügung stehen.", btemp);
								
				btemp = objExpressionHandler.setFlag(IKernelJsonArrayIniSolverZZZ.FLAGZ.USEJSON_ARRAY, true); //sollte egal sein
				assertTrue("Das Flag '" + IKernelJsonArrayIniSolverZZZ.FLAGZ.USEJSON_ARRAY +"' sollte zur Verfügung stehen.", btemp);
								
				//+++ ... solve verhält sich NICHT wie parse(), bei solve wird aufgeloest...
				if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.PARSE)) {
					sExpressionSource = sExpressionSourceIn;					
					sExpressionSolved = sExpressionSolvedIn;
					objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
					sValue = objExpressionHandler.solve(sExpressionSource, objSectionEntryReference, bRemoveSuroundingSeparators);
					assertEquals(sExpressionSolved, sValue);
				}
				//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

				//+++ ... parse ist nicht solve... also wird hier nichts aufgeloest, aussser die Pfade
				if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.SOLVE)) {
					sExpressionSource = sExpressionSourceIn;							
					sExpressionSolved = sExpressionSolvedIn;
					objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
					sValue = objExpressionHandler.parse(sExpressionSource, objSectionEntryReference, bRemoveSuroundingSeparators);
					assertEquals(sExpressionSolved, sValue);
				}
				//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
					
				
				bReturn = true;
			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			}
		}//end main:
		return bReturn;
	}
	
	
	private boolean testCompute_JsonArray_IniUsedUnsolved_(String sSectionIn, String sPropertyIn, String sExpressionSolvedIn) {
		boolean bReturn = false;
		
		main:{
			try {
				String sSection; String sProperty; String sExpressionSolved; String sValue;
				boolean btemp; String stemp;
				ReferenceZZZ<IKernelConfigSectionEntryZZZ> objSectionEntryReference;
				
				//... mit Berechnung PATH
				btemp = objFileIniTest.setFlag(IIniTagWithExpressionZZZ.FLAGZ.USEEXPRESSION, true); 
				assertTrue("Flag nicht vorhanden '" + IIniTagWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
			
				btemp = objFileIniTest.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH,true);  
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
								
				btemp = objFileIniTest.setFlag(IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE,true);  
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE + "'", btemp);
				
				btemp = objFileIniTest.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER, false); //Ansonsten wird der Wert sofort ausgerechnet
				assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
				
				//... ohne solve-Flag ist die eine Berechnung egal
				btemp = objFileIniTest.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA,true);                     //sollte wg. USEEXPRESSION_SOLVER = false egal sein.
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA + "'", btemp);
				
				//... ohne solve-Flag ist die Mathematische Berechnung egal
				btemp = objFileIniTest.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH,true); 
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH + "'", btemp);
							
				//... mit Berechnung JSON
				btemp = objFileIniTest.setFlag(IKernelJsonIniSolverZZZ.FLAGZ.USEJSON, true); 
				assertTrue("Das Flag '" + IKernelJsonIniSolverZZZ.FLAGZ.USEJSON +"' sollte zur Verfügung stehen.", btemp);
								
				//... ohne Berechnung JSON-ARRAY
				btemp = objFileIniTest.setFlag(IKernelJsonArrayIniSolverZZZ.FLAGZ.USEJSON_ARRAY, true);//Ansonsten wird der Wert sofort ausgerechnet 
				assertTrue("Das Flag '" + IKernelJsonMapIniSolverZZZ.FLAGZ.USEJSON_MAP +"' sollte zur Verfügung stehen.", btemp);
												
				sSection = sSectionIn;
				sProperty = sPropertyIn;
				sExpressionSolved = sExpressionSolvedIn;						
				objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
				sValue = objFileIniTest.getPropertyValue(sSection, sProperty).getValue();
				assertEquals(sExpressionSolved, sValue);
				
				
				bReturn = true;
			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			}
		}//end main:
		return bReturn;
	}
	
	private boolean testCompute_JsonArray_IniUsed_Detail_(String sSectionIn, String sPropertyIn, ArrayList<String> alExpressionSolvedIn) {
		boolean bReturn = false;
		
		main:{
			try {
				String sSection; String sProperty; String sExpressionSolved; String sValue;
				boolean btemp; String stemp;
				ReferenceZZZ<IKernelConfigSectionEntryZZZ> objSectionEntryReference;
				
				//... mit Berechnung PATH
				btemp = objFileIniTest.setFlag(IIniTagWithExpressionZZZ.FLAGZ.USEEXPRESSION, true); 
				assertTrue("Flag nicht vorhanden '" + IIniTagWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
			
				btemp = objFileIniTest.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH,true);  
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
								
				btemp = objFileIniTest.setFlag(IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE,true);  
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE + "'", btemp);
				
				btemp = objFileIniTest.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER, true); 
				assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
				
				btemp = objFileIniTest.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA,true);                     //sollte wg. USEEXPRESSION_SOLVER = false egal sein.
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA + "'", btemp);
				
				btemp = objFileIniTest.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH,true); 
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH + "'", btemp);
							
				//... mit Berechnung JSON
				btemp = objFileIniTest.setFlag(IKernelJsonIniSolverZZZ.FLAGZ.USEJSON, true); 
				assertTrue("Das Flag '" + IKernelJsonIniSolverZZZ.FLAGZ.USEJSON +"' sollte zur Verfügung stehen.", btemp);
								
				//... ohne Berechnung JSON-ARRAY
				btemp = objFileIniTest.setFlag(IKernelJsonArrayIniSolverZZZ.FLAGZ.USEJSON_ARRAY, true);//Ansonsten wird der Wert sofort ausgerechnet 
				assertTrue("Das Flag '" + IKernelJsonMapIniSolverZZZ.FLAGZ.USEJSON_MAP +"' sollte zur Verfügung stehen.", btemp);
												
				sSection = sSectionIn;
				sProperty = sPropertyIn;			
				ArrayList<String>alExpressionSolved = alExpressionSolvedIn;
				sExpressionSolved = alExpressionSolved.toString();
				
				IKernelConfigSectionEntryZZZ objEntry = objFileIniTest.getPropertyValue(sSection, sProperty);
				assertNotNull(objEntry);
				sValue = objEntry.getValue();				
				assertEquals(sExpressionSolved, sValue);
				assertTrue(objEntry.isJson());
				assertTrue(objEntry.isJsonArray());
				
				ArrayList<String> als = objEntry.getValueArrayList();
				assertNotNull(als);
				String sValue00 = als.get(0);
				String sValue00expected = alExpressionSolved.get(0);
				assertEquals(sValue00expected, sValue00);
				
				String sValue01 = als.get(1);
				String sValue01expected = alExpressionSolved.get(1);
				assertEquals(sValue01expected, sValue01);
				
				bReturn = true;
			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			}
		}//end main:
		return bReturn;
	}
	
		
	public void testIsExpression() {
		fail("JUnit Test not developed");
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
	
