package basic.zKernel.file.ini;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedTestCaseZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedTestFlagsetZZZ;
import basic.zBasic.util.abstractList.ArrayListUniqueZZZ;
import basic.zBasic.util.abstractList.ArrayListUtilZZZ;
import basic.zBasic.util.crypt.code.ICharacterPoolEnabledZZZ;
import basic.zBasic.util.crypt.code.IROTUserZZZ;
import basic.zBasic.util.crypt.code.ROT13ZZZ;
import basic.zBasic.util.crypt.code.ROTnnZZZ;
import basic.zBasic.util.crypt.code.ROTnumericZZZ;
import basic.zBasic.util.datatype.character.CharacterExtendedZZZ;
import basic.zBasic.util.file.FileEasyZZZ;
import basic.zBasic.util.stream.IStreamZZZ;
import basic.zBasic.util.stream.StreamZZZ;
import basic.zKernel.IKernelConfigSectionEntryZZZ;
import basic.zKernel.KernelZZZ;
import basic.zKernel.KernelZZZTest;

public class TestUtilZZZ {
	public enum TestSubtype{
			DEFAULT,
			AS_ENTRY
	}


	public final static String strFILE_DIRECTORY_DEFAULT = new String("c:\\fglKernel\\KernelTest");
	public final static String strFILE_NAME_DEFAULT = new String("JUnit_KernelExpressionIniHandlerZZZTest.ini");
	
	private TestUtilZZZ() { 
		//Zum Verstecken des Konsruktors
	} //static methods only
	
	
	public static File createKernelFileUsed4JUnit_KernelExpressionIniHandlerZZZTest() throws ExceptionZZZ {
		File objReturn = null;
		
		main:{
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
				
				IStreamZZZ objStreamFile = new StreamZZZ(sFilePathTotal, 1);  //This is not enough, to create the file			
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
				objStreamFile.println("Formula1=Der dynamische Wert1 ist '<Z>{[Section A]Testentry1}</Z>'. FGL rulez.");
				objStreamFile.println("Formula2=Der dynamische Wert2 ist '<Z>{[Section B]Testentry2}</Z>'. FGL rulez.");
				objStreamFile.println("Formula3=Der dynamische Wert3 ist '<Z>{[Section C]Testentry3}</Z>'. FGL rulez.");
						
				objStreamFile.println("Formula01_01=" + KernelExpressionIniHandlerZZZTest.sEXPRESSION_Expression01A);
				objStreamFile.println("Formula01_02=" + KernelExpressionIniHandlerZZZTest.sEXPRESSION_Expression01B);
				
				objStreamFile.println("[Section for testComputeMathArguments]");
				objStreamFile.println("WertA=4");
				objStreamFile.println("WertB=5");
				
				objStreamFile.println("[Section for testComputeMathValue]");
				objStreamFile.println("Formula1=Der dynamische Wert ist '<Z><Z:formula><Z:math><Z:val>2</Z:val><Z:val>3</Z:val></Z:math></Z:formula></Z>'. FGL rulez.");
				objStreamFile.println("Formula2=Der dynamische Wert2 ist '<Z><z:formula><Z:math><Z:val>2</Z:val><Z:op>*</Z:op><Z:val>{[Section for testComputeMathArguments]WertA}</Z:val></Z:math></Z:formula></Z>'. FGL rulez.");		
				
				objStreamFile.println("[Section for testComputeMath]");
				objStreamFile.println("Formula1=Der dynamische Wert ist '<Z><Z:formula><Z:math><Z:val>2</Z:val><Z:op>*</Z:op><Z:val>3</Z:val></Z:math></Z:formula></Z>'. FGL rulez.");
				objStreamFile.println("Formula2=Der dynamische Wert2 ist '<Z><Z:formula><Z:math><Z:val>2</Z:val><Z:op>*</Z:op><Z:val>[Section for testComputeMathArguments]WertA</Z:val></Z:math></Z:formula></Z>'. FGL rulez.");
				objStreamFile.println("Formula3=Der dynamische Wert3 ist '<Z><Z:formula><Z:math><Z:val>{[Section for testComputeMathArguments]WertB}</Z:val><Z:op>*</Z:op><Z:val>{[Section for testComputeMathArguments]WertA}</Z:val></Z:math></Z:formula></Z>'. FGL rulez.");
				 
				
				objStreamFile.println("[Section for testComputePathWithMath]");
				objStreamFile.println("Formula1=<Z>{[Section for testComputeMath]Formula1}</Z>");
				objStreamFile.println("Formula2=<Z>{[Section for testComputeMath]Formula2}</Z>");
				objStreamFile.println("Formula3=<Z>{[Section for testComputeMath]Formula3}</Z>");
				
				objStreamFile.println("[Section for testComputeMath NOT EXACTMATCH]");
				objStreamFile.println("Formula1=Der dynamische Wert ist '<Z><Z:formula><z:Math><Z:VAL>6</Z:val><Z:oP>+</Z:op><Z:val>7</Z:val></Z:math></Z:formula></Z>'. FGL rulez.");
				 
				
				objStreamFile.println("[Section for testComputeMathArguments FLOAT]");
				objStreamFile.println("WertA_float=4.0");
				objStreamFile.println("WertB_float=5.0");
				objStreamFile.println("[Section for testComputeMath FLOAT]");
				objStreamFile.println("Formula1=Der dynamische Wert ist '<Z><Z:formula><z:Math><Z:VAL>{[Section for testComputeMathArguments FLOAT]WertA_float}</Z:val><Z:oP>*</Z:op><Z:val>{[Section for testComputeMathArguments FLOAT]WertB_float}</Z:val></Z:math></Z:formula></Z>'. FGL rulez.");
				
				objStreamFile.println("[Section for testComputeMathVariable FLOAT]");
				objStreamFile.println("WertB_float=<Z><z:Var>myTestVariableFloat</z:Var></z>");
				
				//Beachte Variablen können wie INI-Path auch ausserhalb einer MATH - Anweisung gelten.
				objStreamFile.println("[Section for testPassVariable]");
				objStreamFile.println("Formula1=<Z>Der dynamische Wert ist '<z:Var>myTestVariableString</z:Var>'. FGL rulez.</Z>");
				
				//Erst Pfad, dann Variable
				objStreamFile.println("Formula2=Der dynamische Wert ist '<Z><Z:formula><z:Math><Z:VAL>{[Section for testComputeMathArguments FLOAT]WertA_float}</Z:val><Z:oP>*</Z:op><Z:val><Z:Var>myTestVariableFloat</z:Var></Z:val></Z:math></Z:formula></Z>'. FGL rulez.");
				
				//zwei Pfade
				objStreamFile.println("Formula3=Der dynamische Wert ist '<Z><Z:formula><z:Math><Z:VAL>{[Section for testComputeMathArguments FLOAT]WertA_float}</Z:val><Z:oP>*</Z:op><Z:val>{[Section for testComputeMathVariable FLOAT]WertB_float}</Z:val></Z:math></Z:formula></Z>'. FGL rulez.");
				
				//Erst Variable, dann Pfad
				objStreamFile.println("Formula4=Der dynamische Wert ist '<Z><Z:formula><z:Math><Z:val><Z:Var>myTestVariableFloat</z:Var></Z:val><Z:oP>*</Z:op><Z:VAL>[Section for testComputeMathArguments FLOAT]WertA_float</Z:val></Z:math></Z:formula></Z>'. FGL rulez.");
				
				
				//20210707 Tests für die Arbeit mit JSON Strings
				//Merke:			
				//Gib den JSON-Hashmap-Wert so an: {"DEBUGUI_PANELLABEL_ON":true} Merke: Intern hier eine HashMap String, Boolean Das ist aber nur sinnvoll bei der FLAG übergabe, da weiss man, dass der Wert Boolean ist.
				//                           also: NavigatorContentJson=<JSON>{"UIText01":"TESTWERT2DO2JSON01","UIText02":"TESTWERT2DO2JSON02"}</JSON>
				//Gib den JSON-Array-Wert so an: {"wert1","wert2"}
				objStreamFile.println("[Section for testJsonHashmap]");
				objStreamFile.println("Map1="+ KernelJsonMapIniSolverZZZTest.sEXPRESSION_JSONMAP01_DEFAULT);
					
				objStreamFile.println("[Section for testJsonArraylist]");
				objStreamFile.println("Array1="+ KernelJsonArrayIniSolverZZZTest.sEXPRESSION_JSONARRAY01_DEFAULT);
				
				
				//20220926 Tests fuer die Arbeit mit verschluesselten / encrypted Werten
				String sValue = "abcde"; int iKeyNumber=5; String sCharacterPool="?! abcdefghijklmnopqrstuvwxyz";
				String sFlagNumeric = ICharacterPoolEnabledZZZ.FLAGZ.USENUMERIC.name();
				String sFlagUppercase = ICharacterPoolEnabledZZZ.FLAGZ.USEUPPERCASE.name();
				String sFlagLOWERcase = ICharacterPoolEnabledZZZ.FLAGZ.USELOWERCASE.name();
				String sFlagBlank = IROTUserZZZ.FLAGZ.USEBLANK.name();
				String sEncrypted = ROT13ZZZ.encryptIt(sValue);
				objStreamFile.println("[Section for testEncrypted]");
				objStreamFile.println("WertA="+sValue);
				objStreamFile.println("WertAencrypted="+sEncrypted);
				objStreamFile.println("WertAforDecrypt="+ KernelEncryptionIniSolverZZZTest.sEXPRESSION_ENCRYPTION01_DEFAULT); //<Z><Z:Encrypted><Z:Cipher>ROT13</Z:Cipher><Z:Code>"+sEncrypted+"</Z:Code></Z:Encrypted></Z>");
				
				sEncrypted = ROTnumericZZZ.encrypt(sValue, iKeyNumber, true,false);
				objStreamFile.println("WertB="+sValue);
				objStreamFile.println("WertBencrypted="+sEncrypted);
				objStreamFile.println("WertBforDecrypt="+ KernelEncryptionIniSolverZZZTest.sEXPRESSION_ENCRYPTION02_DEFAULT); //<Z><Z:Encrypted><Z:Cipher>ROTnumeric</Z:Cipher><z:KeyNumber>"+iKeyNumber+"</z:KeyNumber><Z:FlagControl>"+sFlagNumeric+"</Z:FlagControl><Z:Code>"+sEncrypted+"</Z:Code></Z:Encrypted></Z>");
				
				CharacterExtendedZZZ objCharacterMissingReplacement = new CharacterExtendedZZZ(ICharacterPoolEnabledZZZ.cCHARACTER_MISSING_REPLACEMENT_DEFAULT);
				sEncrypted = ROTnnZZZ.encrypt(sValue, sCharacterPool, objCharacterMissingReplacement, iKeyNumber, true,false,false,false);
				objStreamFile.println("WertC="+sValue);
				objStreamFile.println("WertCencrypted="+sEncrypted);
				objStreamFile.println("WertCForDecrypt="+ KernelEncryptionIniSolverZZZTest.sEXPRESSION_ENCRYPTION03_DEFAULT); //<Z><Z:Encrypted><Z:Cipher>ROTnn</Z:Cipher><z:KeyNumber>"+iKeyNumber+"</z:KeyNumber><z:CharacterPool>"+sCharacterPool+"</z:CharacterPool><z:FlagControl>"+sFlagUppercase+"</Z:FlagControl><Z:Code>"+sEncrypted+"</Z:Code></Z:Encrypted></Z>");
				
				//20230505 Tests fuer die Arbeit mit JavaCall Aufrufen
				objStreamFile.println("[Section for testCall]");
				objStreamFile.println("WertCalled="+KernelJavaCallIniSolverZZZTest.sEXPRESSION_CALL01_DEFAULT); //<Z><Z:Call><Z:Java><Z:Class>basic.zBasic.util.machine.EnvironmentZZZ</Z:Class><Z:Method>getHostName</Z:Method></Z:Java></Z:Call></Z>");
				
				
				//20230505 Tests fuer die Arbeit mit Werten aus einem JavaCall Aufruf
				objStreamFile.println("[" + KernelJavaCallIniSolverZZZTest.sEXPRESSION_CALL01_PATHSECTION + "]");
				objStreamFile.println(KernelJavaCallIniSolverZZZTest.sEXPRESSION_CALL01_CLASS_PATHPROPERTY + "=" + KernelJavaCallIniSolverZZZTest.sCALL01_CLASS_DEFAULT);
				objStreamFile.println(KernelJavaCallIniSolverZZZTest.sEXPRESSION_CALL01_METHOD_PATHPROPERTY + "=" + KernelJavaCallIniSolverZZZTest.sCALL01_METHOD_DEFAULT);
				
				objStreamFile.println("[Section for testCallComputed]");
				objStreamFile.println("WertCalledComputed="+ KernelCallIniSolverZZZTest.sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT); //<Z><Z:Call><Z:Java><Z:Class><Z>[ArgumentSection for testCallComputed]JavaClass</Z></Z:Class><Z:Method><Z>[ArgumentSection for testCallComputed]JavaMethod</Z></Z:Method></Z:Java></Z:Call></Z>");
				
				
				objReturn = new File(sFilePathTotal);		
			} catch (FileNotFoundException e) {			
				e.printStackTrace();
				ExceptionZZZ ez = new ExceptionZZZ("FileNotFoundException", e);
				throw ez;
			} catch (IOException e) {			
				e.printStackTrace();
				ExceptionZZZ ez = new ExceptionZZZ("IOException", e);
				throw ez;
			} catch (Exception e) {			
				e.printStackTrace();
				ExceptionZZZ ez = new ExceptionZZZ("Exception", e);
				throw ez;
			}								
		}//end main:
		return objReturn;
	}
	
	
	public static File createKernelFileUsed_FileIniZZZTest() throws ExceptionZZZ {
		File objReturn = null;
		
		main:{
			try {
			//### Eine Beispieldatei. Merke: Die Einträge werden immer neu geschrieben und nicht etwa angehängt. 
			//Merke: Erst wenn es überhaupt einen Test gibt, wird diese Datei erstellt
			//Merke: Damit die Datei nicht im Code-Repository landet, wird sie explizit immer auf der lokalen Festplatte erzeugt.
			//       ALSO NICHT im Eclipse Workspace
			
			String sFileDirectoryUsed = FileIniZZZTest.strFILE_DIRECTORY_DEFAULT;
			if(!FileEasyZZZ.exists(sFileDirectoryUsed)){
				FileEasyZZZ.createDirectoryForDirectory(sFileDirectoryUsed);
			}
			String sFilePathTotal = FileEasyZZZ.joinFilePathName(sFileDirectoryUsed, FileIniZZZTest.strFILE_NAME_DEFAULT );				
//			if(sFilePathTotal==null){
//				//Eclipse Workspace
//				File f = new File("");
//			    String sPathEclipse = f.getAbsolutePath();
//			    System.out.println("Path for Kernel Directory Default does not exist. Using workspace absolut path: " + sPathEclipse);
//			    sFilePathTotal = FileEasyZZZ.joinFilePathName(sPathEclipse + File.separator + "test", strFILE_NAME_DEFAULT );			   
//			}
			
			IStreamZZZ objStreamFile = null;
			try{
				objStreamFile = new StreamZZZ(sFilePathTotal, 1);  //This is not enough, to create the file			
			} catch (FileNotFoundException e) {
				sFileDirectoryUsed = "c:\\temp";
				sFilePathTotal = FileEasyZZZ.joinFilePathName(sFileDirectoryUsed, FileIniZZZTest.strFILE_NAME_DEFAULT );
				objStreamFile = new StreamZZZ(sFilePathTotal, 1);  //This is not enough, to create the file
			} catch (Exception e) {
				e.printStackTrace();
			} 
			objStreamFile.println(";This is a temporarily test file for FileIniZZZTest.");      //Now the File is created. This is a comment line
			objStreamFile.println(";This file will be newly created by the setUp()-method of this JUnit Test class, every time before a testMethod will be invoked.");
			objStreamFile.println("#This is another commentline");
			objStreamFile.println("[Section A]");
			objStreamFile.println("Testentry1=Testvalue1");			
			objStreamFile.println("TestentryToBeDeleted=will be deleted by testStringPropertyDelete()");
			
			objStreamFile.println("[Section for testGetPropertyAll]");
			objStreamFile.println("Property1=");
			objStreamFile.println("Property2=");
			objStreamFile.println("Property3=");
			objStreamFile.println("Property4=");
			objStreamFile.println("Property5=");
			
			objStreamFile.println("[Section for testGetValueAll]");
			objStreamFile.println("PropertyA=" + FileIniZZZTest.saValueTestGetAll[0]);
			objStreamFile.println("PropertyB=" + FileIniZZZTest.saValueTestGetAll[1]);
			objStreamFile.println("PropertyC=" + FileIniZZZTest.saValueTestGetAll[2]);
			objStreamFile.println("PropertyD=" + FileIniZZZTest.saValueTestGetAll[3]);
			objStreamFile.println("PropertyE=" + FileIniZZZTest.saValueTestGetAll[4]);			
			
			objStreamFile.println("[Section for deletion]");
			objStreamFile.println("TestentryToBeDeleted=the one and only entry in this section. Will be deleted by testStringPropertyDelete()");
			
			objStreamFile.println("[Section for formula value]");
			objStreamFile.println("Value1=first value");
			
			objStreamFile.println("[Section for formula]");
			objStreamFile.println("Formula1=Das ist der '<Z>{[Section for formula value]Value1}</Z>' Wert.");
			
			objStreamFile.println("[Section for testGetPropertyValueSystemNrSearched]");
			objStreamFile.println("Value1=first value global");
			objStreamFile.println("ValueGlobalOnly=second value global");
			objStreamFile.println("[Section for testGetPropertyValueSystemNrSearched!01]");
			objStreamFile.println("Value1=first value systemnr 01");
			objStreamFile.println("ValueLocalOnly=local value systemnr 01");
			objStreamFile.println("[Section for testGetPropertyValueSystemNrSearched!02]");
			objStreamFile.println("Value1=first value systemnr 02");
			
			objStreamFile.println("[Section for ProofSectionExists]");
			
			
			//20210707 Tests für die Arbeit mit JSON Strings
			//Merke:			
			//Gib den JSON-Hashmap-Wert so an: {"DEBUGUI_PANELLABEL_ON":true} Merke: Intern hier eine HashMap String, Boolean Das ist aber nur sinnvoll bei der FLAG übergabe, da weiss man, dass der Wert Boolean ist.
			//                           also: NavigatorContentJson=<JSON>{"UIText01":"TESTWERT2DO2JSON01","UIText02":"TESTWERT2DO2JSON02"}</JSON>
			//Gib den JSON-Array-Wert so an: {"wert1","wert2"}
			objStreamFile.println("[Section for testJsonHashmap]");
			objStreamFile.println("Map1="+ KernelJsonMapIniSolverZZZTest.sEXPRESSION_JSONMAP01_DEFAULT);
				
			objStreamFile.println("[Section for testJsonArraylist]");
			objStreamFile.println("Array1="+ KernelJsonArrayIniSolverZZZTest.sEXPRESSION_JSONARRAY01_DEFAULT);
			
			objStreamFile.close();
		
			objReturn = new File(sFilePathTotal);
		} catch (FileNotFoundException e) {			
			e.printStackTrace();
			ExceptionZZZ ez = new ExceptionZZZ("FileNotFoundException", e);
			throw ez;
		} catch (IOException e) {			
			e.printStackTrace();
			ExceptionZZZ ez = new ExceptionZZZ("IOException", e);
			throw ez;
		} catch (Exception e) {			
			e.printStackTrace();
			ExceptionZZZ ez = new ExceptionZZZ("Exception", e);
			throw ez;
		}	
		}//end main:
		return objReturn;
	}
	
	
	public static File createKernelFileUsed_KernelZZZTest(String sFileDirectorySourceIn, String sFileNameSourceIn) throws ExceptionZZZ {
		File objReturn = null;
		String sFilePathTotalSource = null; String sFilePathTotalTarget = null;  
		main:{
			try {
			//### Uebernommene Werte siehe echte Datei.
			//### Die Datei fuer den JUniTest danach extra erstellen (ausserhalb des Git-Repositories)
			                  
			String sFileDirectorySourceUsed = sFileDirectorySourceIn;
			String sFileNameSourceUsed = sFileNameSourceIn;

			//#############################################
			//Erst einmal die Pfade ausrechnen
			sFileDirectorySourceUsed = FileEasyZZZ.getFileUsedPath(sFileDirectorySourceUsed);
			if(!FileEasyZZZ.exists(sFileDirectorySourceUsed)){
				FileEasyZZZ.createDirectoryForDirectory(sFileDirectorySourceUsed);
			}
			sFilePathTotalSource = FileEasyZZZ.joinFilePathName(sFileDirectorySourceUsed, sFileNameSourceUsed );				
//				if(sFilePathTotal==null){
//					//Eclipse Workspace
//					File f = new File("");
//				    String sPathEclipse = f.getAbsolutePath();
//				    System.out.println("Path for Kernel Directory Default does not exist. Using workspace absolut path: " + sPathEclipse);
//				    sFilePathTotal = FileEasyZZZ.joinFilePathName(sPathEclipse + File.separator + "test", strFILE_NAME_DEFAULT );			   
//				}
			
			
			String sFileDirectoryTargetUsed = KernelZZZTest.strFILE_DIRECTORY_DEFAULT_EXTERNAL;
			sFileDirectoryTargetUsed = FileEasyZZZ.getFileUsedPath(sFileDirectoryTargetUsed);
			
		
			if(!FileEasyZZZ.exists(sFileDirectoryTargetUsed)){
				FileEasyZZZ.createDirectoryForDirectory(sFileDirectoryTargetUsed);
			}
			//sFilePathTotalTarget = FileEasyZZZ.joinFilePathName(sFileDirectoryTargetUsed, KernelZZZTest.strFILE_NAME_DEFAULT );				
			sFilePathTotalTarget = FileEasyZZZ.joinFilePathName(sFileDirectoryTargetUsed, sFileNameSourceUsed );
//			if(sFilePathTotal==null){
//				//Eclipse Workspace
//				File f = new File("");
//			    String sPathEclipse = f.getAbsolutePath();
//			    System.out.println("Path for Kernel Directory Default does not exist. Using workspace absolut path: " + sPathEclipse);
//			    sFilePathTotal = FileEasyZZZ.joinFilePathName(sPathEclipse + File.separator + "test", strFILE_NAME_DEFAULT );			   
//			}
			
			if(sFilePathTotalSource.equals(sFilePathTotalTarget)) {
				ExceptionZZZ ez = new ExceptionZZZ("Ziel und Quellpfad identisch:'" + sFilePathTotalSource + "'");
				throw ez;
			}
			
			
			//### Nun die Streams erstellen
			IStreamZZZ objStreamFileSource = null;
			try{
				objStreamFileSource = new StreamZZZ(sFilePathTotalSource, 0); //0 = read the file			
			} catch (FileNotFoundException e) {
				sFileDirectorySourceUsed = KernelZZZ.sDIRECTORY_CONFIG_DEFAULT;
				sFilePathTotalSource = FileEasyZZZ.joinFilePathName(sFileDirectorySourceUsed, KernelZZZ.sFILENAME_CONFIG_DEFAULT );
				objStreamFileSource = new StreamZZZ(sFilePathTotalSource, 0);  //0 = read the file	
			} catch (Exception e) {
				e.printStackTrace();
			} 
				
				
			IStreamZZZ objStreamFileTarget = null;
			try{
				objStreamFileTarget = new StreamZZZ(sFilePathTotalTarget, 1);  // 1 = write to the file		
			} catch (FileNotFoundException e) {
				sFileDirectoryTargetUsed = "c:\\temp";
				sFilePathTotalTarget = FileEasyZZZ.joinFilePathName(sFileDirectoryTargetUsed, KernelZZZTest.strFILE_NAME_DEFAULT );
				objStreamFileTarget = new StreamZZZ(sFilePathTotalTarget, 1);  // 1 = write to the file
			} catch (Exception e) {
				e.printStackTrace();
			} 
			
			//### Eine Beispieldatei. Merke: Die Einträge werden immer neu geschrieben und nicht etwa angehängt. 
			//Merke: Erst wenn es überhaupt einen Test gibt, wird diese Datei erstellt
			//Merke: Damit die Datei nicht im Code-Repository landet, wird sie explizit immer auf der lokalen Festplatte erzeugt.
			//       ALSO NICHT im Eclipse Workspace
			//Kopiere aus der bestehenden Datei in die JUnitDatei
			String sLine; boolean bGoon=true;
			do {
				sLine = objStreamFileSource.readLineNext();
				if(sLine!=null) {
					objStreamFileTarget.println(sLine);
				}else {
					bGoon = false;
				}
			}while(bGoon);
						
			objStreamFileTarget.close();
			objStreamFileSource.close();
			
			objReturn = new File(sFilePathTotalTarget);
		} catch (FileNotFoundException e) {			
			e.printStackTrace();
			ExceptionZZZ ez = new ExceptionZZZ("FileNotFoundException", e);
			throw ez;
		} catch (IOException e) {			
			e.printStackTrace();
			ExceptionZZZ ez = new ExceptionZZZ("IOException", e);
			throw ez;
		} catch (Exception e) {			
			e.printStackTrace();
			ExceptionZZZ ez = new ExceptionZZZ("Exception", e);
			throw ez;
		}	
		}//end main:
		return objReturn;
	}
	
	//Merke: Erfasse alle Klassen auch in:
	//       ExpressionIniUtilZZZ.isExpressionDefaultAny(...)
	public static String[] createStringsUsed_ExpressionAny() throws ExceptionZZZ{
		String[] saReturn = null;
		main:{
			String sExpression=null;
			ArrayListUniqueZZZ<String>listasReturn=new ArrayListUniqueZZZ<String>();
			
			//+++ ExpressionHandler
			sExpression = ExpressionIniUtilZZZ.makeAsExpression("dummy");
			sExpression = "PRE" + sExpression + "POST";
			listasReturn.add(sExpression);
			
			//+++ Alle ExpressionSolver
			ArrayListUniqueZZZ<Class>listaClass=ExpressionIniUtilZZZ.getExpressionUsingSolver_Classes();
			for(Class classObj : listaClass) {
				String sTag = ExpressionIniUtilZZZ.getTagNameDefault(classObj);
				
				sExpression = ExpressionIniUtilZZZ.makeAsExpression("dummy", sTag);
				sExpression = "PRE" + sExpression + "POST";
				listasReturn.add(sExpression);
			}
																		
			saReturn = ArrayListUtilZZZ.toStringArray(listasReturn);
		}//end main:
		return saReturn;
	}
}
