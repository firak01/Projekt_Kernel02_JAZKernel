package basic.zKernel.file.ini;

import java.util.Vector;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedTestCaseZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedTestFlagsetZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedTestSurroundingZZZ;
import basic.zBasic.util.abstractList.Vector3ZZZ;
import basic.zBasic.util.abstractList.VectorUtilZZZ;
import basic.zBasic.util.datatype.calling.ReferenceZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.datatype.xml.XmlUtilZZZ;
import basic.zBasic.util.xml.tagsimple.IParseZZZ;
import basic.zBasic.util.xml.tagsimple.ITagBasicChildZZZ;
import basic.zKernel.IKernelConfigSectionEntryZZZ;
import basic.zKernel.config.KernelConfigSectionEntryUtilZZZ;
import junit.framework.TestCase;

public class TestUtilAsTestZZZ extends TestCase{
	
	//Diese Konstanten sind notwendig, um in einer switch...case Abfrage genutzt zu werden.
	//Das funktioniert nämlich nicht, da es keine Konstante ist   case EnumSetMappedTestCaseFlagsetTypeZZZ.UNEXPRESSED.getAbbreviation():
	
	//Diese Konstanten werden in einem enum verwendet. 
	//Diese finale Konstanten sind notwendig, um in einer switch...case Abfrage genutzt zu werden.
	//Das funktioniert nämlich nicht, da es keine finale Konstante ist   case EnumSetMappedTestCaseFlagsetTypeZZZ.UNEXPRESSED.getAbbreviation():	
	public static final String sFLAGSET_UNEXPRESSED="uex";
	public static final String sFLAGSET_UNPARSED="upa";
	public static final String sFLAGSET_UNSOLVED="uso";
	public static final String sFLAGSET_UNPARSED_UNSOLVED="upa_uso";
	
	public static final String sFLAGSET_FORMULA_UNSOLVED="fuso";
	public static final String sFLAGSET_FORMULA_SOLVED="fso";
	
	public static final String sFLAGSET_MATH_UNSOLVED="muso";
	public static final String sFLAGSET_MATH_SOLVED="mso";
	
	public static final String sFLAGSET_SOLVED="so";
	public static final String sFLAGSET_PATH_UNSUBSTITUTED="pusu";
	public static final String sFLAGSET_PATH_SUBSTITUTED="psu";
	public static final String sFLAGSET_VARIABLE_UNSUBSTITUTED="vusu";
	public static final String sFLAGSET_VARIABLE_SUBSTITUTED="vsu";
	
	//ist nur uex: public static final String sFLAGSET_CALL_UNEXPRESSED="cuex";
	public static final String sFLAGSET_CALL_UNSOLVED="cus";
	public static final String sFLAGSET_CALL_UNSOLVED_JAVACALL_SOLVED="cusjcs";
	public static final String sFLAGSET_CALL_SOLVED="cs";	
	public static final String sFLAGSET_JAVACALL_UNSOLVED="jcus";
	public static final String sFLAGSET_JAVACALL_SOLVED="jcs";	
	
	//ist nur uex: public static final String sFLAGSET_JSON_UNEXPRESSED="juex";
	public static final String sFLAGSET_JSON_SOLVED="js";
	public static final String sFLAGSET_JSON_UNSOLVED="jus";
	
		
	//ist nur uex:  public static final String sFLAGSET_JSONARRAY_UNEXPRESSED="jauex";
	//ist nur jaus: public static final String sFLAGSET_JSONARRAY_JSONARRAY_UNSOLVED="jajaus";
	public static final String sFLAGSET_JSONARRAY_SOLVED="jas";
	public static final String sFLAGSET_JSONARRAY_UNSOLVED="jaus";
	
		
	//ist nur uex: public static final String sFLAGSET_JSONMAP_UNEXPRESSED="jmuex";
	public static final String sFLAGSET_JSONMAP_SOLVED="jms";
	public static final String sFLAGSET_JSONMAP_UNSOLVED="jmus";
	
	public static final String sCASE_PARSE="p";
	public static final String sCASE_SOLVE="s";
	public static final String sCASE_PARSE_AS_ENTRY="pae";
	public static final String sCASE_SOLVE_AS_ENTRY="sae";
	public static final String sCASE_AS_ENTRY="ae";
	
		
	
	private TestUtilAsTestZZZ() { 
		//Zum Verstecken des Konsruktors
	} //static methods only
			
	public static boolean assertFileIniEntry(IEnumSetMappedTestFlagsetZZZ objEnumFlagset, IEnumSetMappedTestSurroundingZZZ objEnumSurrounding, IEnumSetMappedTestCaseZZZ objEnumTestCase, IKernelConfigSectionEntryZZZ objEntry, String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			boolean bIsExpressionValidForTest=false;
			boolean bAsEntry=false;
												
			String sCaseSet = objEnumTestCase.getAbbreviation();
			switch(sCaseSet) {
			case sCASE_PARSE_AS_ENTRY:
			case sCASE_SOLVE_AS_ENTRY:
			case sCASE_AS_ENTRY:
				bAsEntry=true;
				break;
			default:
				bAsEntry=false;
				break;
			}
			
			
			switch(sCaseSet) {
			case sCASE_PARSE_AS_ENTRY:
			case sCASE_PARSE:{
				bIsExpressionValidForTest = TestUtilAsTestZZZ.isExpressionValidForFlagset_parse(objEnumFlagset, sExpressionIn);
				if(bIsExpressionValidForTest) {
					assertFileIniEntry_parse(objEnumSurrounding, objEnumFlagset, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
				}
				break;
			}	
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//https://stackoverflow.com/questions/18412936/or-operator-in-switch-case
			//also man kann in diesem Ausdruck nicht "verodern" ||, da dies keine feststehende Expression ist, sondern zur Laufzeit gebaut wuerde
			//case sCASE_SOLVE || sCASE_AS_ENTRY:{
			//praktikable Loesung: Schreibe die Cases untereinander und lasse das break weg!
			case sCASE_SOLVE_AS_ENTRY:
			case sCASE_AS_ENTRY:
			case sCASE_SOLVE:{
				bIsExpressionValidForTest = TestUtilAsTestZZZ.isExpressionValidForFlagset_solve(objEnumFlagset, sExpressionIn);
				if(bIsExpressionValidForTest) {
					assertFileIniEntry_solve(objEnumSurrounding, objEnumFlagset,objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
				}
				break;
			}
			default:
				fail("Test CaseSet '" + sCaseSet + "' ist nicht definiert");
				break;
			}
						
			bReturn = true;
		}//end main:
		return bReturn;
	}
	
	
	public static boolean assertFileIniEntry_parse(IEnumSetMappedTestSurroundingZZZ objEnumSurrounding, IEnumSetMappedTestFlagsetZZZ objEnumTestFlagset,IKernelConfigSectionEntryZZZ objEntry, String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, boolean bAsEntry) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			
			String sExpression2compareWithSolved = null; String sExpression2compareWithSubstituted = null;
			String sExpressionSubstituted2compare = null;
			
			String sExpression = sExpressionIn;
			String sExpressionSubstituted = sExpressionSubstitutedIn;
			String sExpressionSolved = sExpressionSolvedIn;
			
			String sTagStartZ = "<Z>";
			String sTagEndZ = "</Z>";	
		
			
			String sCaseSet = TestUtilAsTestZZZ.sCASE_PARSE;
			String sFlagSet = objEnumTestFlagset.getAbbreviation();

				switch(sFlagSet) {
				//Merke: Das ist keine Konstante, case EnumSetMappedTestCaseFlagsetTypeZZZ.UNEXPRESSED.getAbbreviation():
				case sFLAGSET_UNEXPRESSED:
					bReturn = assertFileIniEntry_parse_FLAGSET_UNEXPRESSED_(objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);					
					break;
					
				case sFLAGSET_UNSOLVED:		
					bReturn = assertFileIniEntry_parse_FLAGSET_UNSOLVED_(objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);					
					break;
					
				case sFLAGSET_MATH_UNSOLVED: //Z:Formula wird ja gesolved					
					bReturn = assertFileIniEntry_parse_FLAGSET_MATH_UNSOLVED_(objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);									
					break;
					
				case sFLAGSET_SOLVED:
					bReturn = assertFileIniEntry_parse_FLAGSET_SOLVED_(objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);									
					break;
					
				case sFLAGSET_PATH_UNSUBSTITUTED:
					bReturn = assertFileIniEntry_parse_FLAGSET_PATH_UNSUBSTITUTED_(objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
					break;
										
				case sFLAGSET_PATH_SUBSTITUTED:
					bReturn = assertFileIniEntry_parse_FLAGSET_PATH_SUBSTITUTED_(objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);					
					break;
					
				case sFLAGSET_CALL_UNSOLVED:			
					bReturn = assertFileIniEntry_parse_FLAGSET_CALL_UNSOLVED_(objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);					
					break;
					
				case sFLAGSET_CALL_UNSOLVED_JAVACALL_SOLVED:
					bReturn = assertFileIniEntry_parse_FLAGSET_CALL_UNSOLVED_JAVACALL_SOLVED_(objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);					
					break;
																			
				case sFLAGSET_JAVACALL_SOLVED:
					bReturn = assertFileIniEntry_parse_FLAGSET_JAVACALL_SOLVED_(objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
					break;
					
				case sFLAGSET_JAVACALL_UNSOLVED:				
					bReturn = assertFileIniEntry_parse_FLAGSET_JAVACALL_UNSOLVED_(objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
					break;
					
				case sFLAGSET_JSON_UNSOLVED:
					bReturn = assertFileIniEntry_parse_FLAGSET_JSON_UNSOLVED_(objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
					break;
				case sFLAGSET_JSONARRAY_UNSOLVED:
					bReturn = assertFileIniEntry_parse_FLAGSET_JSONARRAY_UNSOLVED_(objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);					
					break;
					
				case sFLAGSET_JSONARRAY_SOLVED:
					bReturn = assertFileIniEntry_parse_FLAGSET_JSONARRAY_SOLVED_(objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
					break;
				//Merke: Folgende Test sind zwar denkbar aber unnoetig
					// case sFLAGSET_JSONARRAY_UNEXPRESSED:
					//Merke: Diese Flagset Konstellation gibt es nicht. 
					//       Sinnvoll ist ein Test wenn die Expressionbehandlung ganz ausgeschaltet ist.					
					//       Auch JSONARRAY sollte getestet werden wenn die Expressionbehandlung ganz ausgeschaltet ist.
					//fail("Testutil for flagset missing: " + sFLAGSET_JSONARRAY_UNEXPRESSED);
					//break;
					//case sFLAGSET_JSONARRAY_JSONARRAY_UNEXPRESSED:
					//Merke: Diese Konstellation gibt es fuer einen "Untersolver" nicht. Es kann nur die Expressionbehandlung ganz ausgeschaltet werden.
					//fail("Testutil for flagset missing: " + sFLAGSET_JSONARRAY_UNEXPRESSED);
					//break;	
					
				case sFLAGSET_JSONMAP_UNSOLVED:
					bReturn = assertFileIniEntry_parse_FLAGSET_JSONMAP_UNSOLVED_(objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);				
					break;
				case sFLAGSET_JSONMAP_SOLVED:
					bReturn = assertFileIniEntry_parse_FLAGSET_JSONMAP_SOLVED_(objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
					break;
				default:
					fail("Test Flagset '" + sFlagSet + "' im Case '"+sCaseSet+"' ist nicht definiert");
					break;
				}					
										
			bReturn = true;
		}//end main:
		return bReturn;
	}

	private static boolean assertFileIniEntry_parse_FLAGSET_UNEXPRESSED_(IEnumSetMappedTestSurroundingZZZ objEnumSurrounding, IKernelConfigSectionEntryZZZ objEntry, String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, boolean bAsEntry) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			
			String sExpression2compareWithSolved = null; String sExpression2compareWithSubstituted = null;
			String sExpressionSubstituted2compare = null;
			
			String sExpression = sExpressionIn;
			String sExpressionSubstituted = sExpressionSubstitutedIn;
			String sExpressionSolved = sExpressionSolvedIn;
			
			String sTagStartZ = "<Z>";
			String sTagEndZ = "</Z>";	
		
			//######## TEST-TEIL ###########################
			assertFalse(objEntry.isExpression()); //ohne Expression-Nutzung kein Expression Eintrag!!!					
			assertTrue(objEntry.isParseCalled()); //Auch wenn die Expression nicht verarbeitet wird, dann ist doch geparsed worden....					
			assertFalse(objEntry.isSolveCalled()); //Der Solve-Schritt wurde ja NICHT gemacht.
				
			assertFalse(objEntry.isParsed()); //sollte ohne Expression abgebrochen worden sein
			assertFalse(objEntry.isSolved()); //sollte ohne Expression abgebrochen worden sein
				
			assertFalse(objEntry.isParsedChanged());
			assertFalse(objEntry.isSolvedChanged()); //Ohne Expression Behandlung wird auch nichts geaendert.
			assertFalse(objEntry.isSubstitutedChanged());
			assertFalse(objEntry.isPathSubstituted());					
			assertFalse(objEntry.isVariableSubstituted());
					
				
			assertFalse(objEntry.isDecrypted());
			assertNull(objEntry.getValueDecrypted()); //Merke: sValue kann unterschiedlich zu dem decrypted Wert sein. Wenn etwas drumherum steht.
					
			assertFalse(objEntry.isCall());
			assertFalse(objEntry.isJavaCall());
			assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingClassname() + "'", objEntry.getCallingClassname());
			assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingMethodname() + "'", objEntry.getCallingMethodname());
			
			bReturn = true;
		}//end main:
		return bReturn;
	}
	
	private static boolean assertFileIniEntry_parse_FLAGSET_UNSOLVED_(IEnumSetMappedTestSurroundingZZZ objEnumSurrounding, IKernelConfigSectionEntryZZZ objEntry, String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, boolean bAsEntry) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			
			String sExpression2compareWithSolved = null; String sExpression2compareWithSubstituted = null;
			String sExpressionSubstituted2compare = null;
			
			String sExpression = sExpressionIn;
			String sExpressionSubstituted = sExpressionSubstitutedIn;
			String sExpressionSolved = sExpressionSolvedIn;
			
			String sTagStartZ = "<Z>";
			String sTagEndZ = "</Z>";	
		
			//######## TEST-TEIL ###########################
			assertTrue(objEntry.isExpression()); //ohne Expression-Nutzung kein Expression Eintrag!!!
			
			//++++++++++++++++++++++ Weil nicht gesolved wurde, kann auf das Parse Ergebnis zugegriffen werden
			assertTrue(objEntry.isParseCalled()); //Auch wenn die Expression nicht verarbeitet wird, dann ist doch geparsed worden....
			sExpression2compareWithSubstituted = sExpression;
			if(objEnumSurrounding == EnumSetMappedTestSurroundingTypeZZZ.PARSE_REMOVE && objEntry.isParsed()) {
				sExpression2compareWithSubstituted = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpression2compareWithSubstituted, sTagStartZ, sTagEndZ, false);
			}
			System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpression2compareWithSubsituted=" + sExpression2compareWithSubstituted);
			System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpressionSubstituted=" + sExpressionSubstituted);
			if(sExpression2compareWithSubstituted.equals(sExpressionSubstituted)) {
				assertFalse(objEntry.isParsedChanged());						
			}else {
				assertTrue(objEntry.isParsedChanged());
			}
			assertTrue(objEntry.isParsed());
								
			//+++++++++++++++++++++++ ++++++++++++++++++++++ Weil nicht gesolved wurde, kann auf das Parse Ergebnis zugegriffen werden
			assertTrue(objEntry.isPathSubstituteCalled());
			sExpression2compareWithSubstituted = sExpression;
			if(objEnumSurrounding == EnumSetMappedTestSurroundingTypeZZZ.PARSE_REMOVE && objEntry.isParsed()) {
				sExpression2compareWithSubstituted = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpression2compareWithSubstituted, sTagStartZ, sTagEndZ, false);
			}
			System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpression2compareWithSubstituted=" + sExpression2compareWithSubstituted);
			System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpressionSubstituted=" + sExpressionSubstituted);					
			if(objEntry.isPathSubstituted()){ //Kann hier eigentlich nicht getestet werden. Ggfs. wird eine Expression ohne INI-PATH uebergeben
				if(sExpression2compareWithSubstituted.equals(sExpressionSubstituted)) { 
					assertFalse(objEntry.isSubstitutedChanged());
				}else {
					assertTrue(objEntry.isSubstitutedChanged());
				}
			}
			assertTrue(objEntry.isPathSubstituted()); //falls das entsprechende Flag gesetzt ist, unabhaengig davon, ob ein INI-PATH Ausdruck darin ist
			//++++++++++++++++++++++++
			//++++++++++++++++++++++++
			
			
//			assertTrue(objEntry.isParsed()); 
//			assertFalse(objEntry.isSolved()); //sollte ohne SOLVE abgebrochen worden sein									
//			if(sExpression.equals(sExpressionSolved)) {
//				assertFalse(objEntry.isParsedChanged());						
//			}else {
//				assertTrue(objEntry.isParsedChanged());
//			}	
//			assertFalse(objEntry.isSolvedChanged()); //nur mit parse wird hier nix geaendert
//								
//			assertTrue(objEntry.isPathSubstituted()); //falls das entsprechende Flag gesetzt ist, unabhaengig davon, ob ein INI-PATH Ausdruck darin ist
//			if(objEntry.isPathSubstituted()){ //Kann hier eigentlich nicht getestet werden. Ggfs. wird eine Expression ohne INI-PATH uebergeben
//				if(sExpression.equals(sExpressionSubstituted)) { 
//					assertFalse(objEntry.isSubstitutedChanged());
//				}else {
//					assertFalse(objEntry.isSubstitutedChanged());
//				}
//			}

			//+++ Auf Werte kann man hier eigentlich nicht so abfragen, weil ggfs. keine Variablen in der Expression sind.
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			assertTrue(objEntry.isVariableSubstituteCalled());					
			assertTrue(objEntry.isVariableSubstituted()); //falls das entsprechende Flag gesetzt ist, unabhaengig davon, ob eine INI-Variable darin ist

			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			assertFalse(objEntry.isSolveCalled()); //hier nur der PARSE - Fall
			assertFalse(objEntry.isSolvedChanged());
			assertFalse(objEntry.isSolved());
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			
								
			//+++ Auf Werte kann man hier eigentlich nicht so abfragen, weil ggfs. doch CRYPTED Werte in der Expression sind
			assertFalse(objEntry.isDecrypted());
			assertNull(objEntry.getValueDecrypted()); //Merke: sValue kann unterschiedlich zu dem decrypted Wert sein. Wenn etwas drumherum steht.
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//Kann hier eigentlich nicht abgefragt werden, weil ggfs. doch ein CALL in der Expression ist.
			//assertFalse(objEntry.isCall());
			//assertFalse(objEntry.isJavaCall());
			//assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingClassname() + "'", objEntry.getCallingClassname());
			//assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingMethodname() + "'", objEntry.getCallingMethodname());
						
			bReturn = true;
		}//end main:
		return bReturn;
	}
	
	private static boolean assertFileIniEntry_parse_FLAGSET_SOLVED_(IEnumSetMappedTestSurroundingZZZ objEnumSurrounding, IKernelConfigSectionEntryZZZ objEntry, String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, boolean bAsEntry) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			
			String sExpression2compareWithSolved = null; String sExpression2compareWithSubstituted = null;
			String sExpressionSubstituted2compare = null; String sExpressionSubstituted2compareWithExpression = null;
			
			String sExpression = sExpressionIn;
			String sExpressionSubstituted = sExpressionSubstitutedIn;
			String sExpressionSolved = sExpressionSolvedIn;
			
			String sTagStartZ = "<Z>";
			String sTagEndZ = "</Z>";	
		
			//######## TEST-TEIL ###########################
			assertTrue(objEntry.isExpression()); 	//ohne Expression-Nutzung kein Expression Eintrag!!!
			assertTrue(objEntry.isParseCalled()); 	//Auch wenn die Expression nicht verarbeitet wird, dann ist doch geparsed worden....					
			assertFalse(objEntry.isSolveCalled()); //Noch wurde Solve nicht aufgerufen
		
			assertTrue(objEntry.isParsed()); 
			assertFalse(objEntry.isSolved());      //Noch wurde Solve nicht aufgerufen
			
			if(sExpression.equals(sExpressionSolved)) {
				assertFalse(objEntry.isParsedChanged()); 
			}else {
				assertTrue(objEntry.isParsedChanged()); 
			}
			assertFalse(objEntry.isSolvedChanged()); //nur mit parse wird hier nix geaendert
			
			assertTrue(objEntry.isPathSubstituted());
			//+++ kann man hier doch auch eigentlich nicht so abfragen
			sExpression2compareWithSubstituted = sExpression;
			if(objEnumSurrounding == EnumSetMappedTestSurroundingTypeZZZ.PARSE_REMOVE && objEntry.isParsed()) {
				sExpression2compareWithSubstituted = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpression2compareWithSubstituted, sTagStartZ, sTagEndZ, false);
			}
			System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpression2compareWithSubstituted=" + sExpression2compareWithSubstituted);
			System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpressionSubstituted=" + sExpressionSubstituted);					
			if(objEntry.isPathSubstituted()){ //Kann hier eigentlich nicht getestet werden. Ggfs. wird eine Expression ohne INI-PATH uebergeben
				if(sExpression2compareWithSubstituted.equals(sExpressionSubstituted)) { 
					assertFalse(objEntry.isPathSubstitutedChanged());
				}else {
					assertTrue(objEntry.isPathSubstitutedChanged());
				}
			}
			//++++++++++++++++++++++++++++++++++++++++++
			
			assertTrue(objEntry.isVariableSubstituted());
			//+++ kann man hier doch auch eigentlich nicht so abfragen					
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++
										
			assertFalse(objEntry.isDecrypted());
			assertNull(objEntry.getValueDecrypted()); //Merke: sValue kann unterschiedlich zu dem decrypted Wert sein. Wenn etwas drumherum steht.						
			bReturn = true;
		}//end main:
		return bReturn;
	}

	
	private static boolean assertFileIniEntry_parse_FLAGSET_MATH_UNSOLVED_(IEnumSetMappedTestSurroundingZZZ objEnumSurrounding, IKernelConfigSectionEntryZZZ objEntry, String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, boolean bAsEntry) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			
			String sExpression2compareWithSolved = null; String sExpression2compareWithSubstituted = null;
			String sExpressionSubstituted2compare = null;
			
			String sExpression = sExpressionIn;
			String sExpressionSubstituted = sExpressionSubstitutedIn;
			String sExpressionSolved = sExpressionSolvedIn;
			
			String sTagStartZ = "<Z>";
			String sTagEndZ = "</Z>";	
		
			//######## TEST-TEIL ###########################
			assertTrue(objEntry.isExpression()); //ohne Expression-Nutzung kein Expression Eintrag!!!
			assertTrue(objEntry.isParseCalled()); //Auch wenn die Expression nicht verarbeitet wird, dann ist doch geparsed worden....					
			assertFalse(objEntry.isSolveCalled()); //Aufgerufen wurde der solveCall ja...

			assertTrue(objEntry.isParsed()); 
			assertTrue(objEntry.isSolved()); //auch ohne Math wird SOLVE ja gemacht

			
			if(sExpression.equals(sExpressionSolved)) {
				assertFalse(objEntry.isParsedChanged());						
			}else {
				assertTrue(objEntry.isParsedChanged());
			}
			assertFalse(objEntry.isSolvedChanged()); //nur mit parse wird hier nix geaendert
			
			if(sExpression.equals(sExpressionSubstituted)) {						
				assertFalse(objEntry.isPathSubstituted());
			}else {
				assertFalse(objEntry.isPathSubstituted());
			}
			
			//+++ kann man hier doch auch eigentlich nicht so abfragen					
			assertFalse(objEntry.isVariableSubstituted());
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++
													
									
			assertFalse(objEntry.isDecrypted());
			assertNull(objEntry.getValueDecrypted()); //Merke: sValue kann unterschiedlich zu dem decrypted Wert sein. Wenn etwas drumherum steht.
			
			assertFalse(objEntry.isCall());
			assertFalse(objEntry.isJavaCall());
			assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingClassname() + "'", objEntry.getCallingClassname());
			assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingMethodname() + "'", objEntry.getCallingMethodname());

			bReturn = true;
		}//end main:
		return bReturn;
	}
	
	private static boolean assertFileIniEntry_parse_FLAGSET_PATH_UNSUBSTITUTED_(IEnumSetMappedTestSurroundingZZZ objEnumSurrounding, IKernelConfigSectionEntryZZZ objEntry, String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, boolean bAsEntry) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			
			String sExpression2compareWithSolved = null; String sExpression2compareWithSubstituted = null;
			String sExpressionSubstituted2compare = null;
			
			String sExpression = sExpressionIn;
			String sExpressionSubstituted = sExpressionSubstitutedIn;
			String sExpressionSolved = sExpressionSolvedIn;
			
			String sTagStartZ = "<Z>";
			String sTagEndZ = "</Z>";	
		
			//######## TEST-TEIL ###########################
			sExpressionSolved = sExpressionSolvedIn;
			assertTrue(objEntry.isExpression()); //ohne Expression-Nutzung kein Expression Eintrag!!!
								
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			assertTrue(objEntry.isParseCalled()); //Auch wenn die Expression nicht verarbeitet wird, dann ist doch geparsed worden....
			if(sExpression.equals(sExpressionSolved)) {
				assertFalse(objEntry.isParsedChanged());						
			}else {
				assertTrue(objEntry.isParsedChanged());
			}
			assertTrue(objEntry.isParsed());
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			assertTrue(objEntry.isPathSubstituteCalled());																
			assertFalse(objEntry.isPathSubstitutedChanged());									
			assertFalse(objEntry.isPathSubstituted());
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			assertFalse(objEntry.isVariableSubstituteCalled());
																//+++ kann man hier doch auch eigentlich nicht so abfragen														
			assertFalse(objEntry.isVariableSubstituted());
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			
			assertFalse(objEntry.isSolveCalled()); //Der Solve-Schritt wurde nicht gemacht.					
			assertFalse(objEntry.isSolvedChanged()); //nur mit parse wird hier nix geaendert									 
			assertFalse(objEntry.isSolved()); //Der Solve-Schritt wurde nicht gemacht.
			
			assertFalse(objEntry.isDecrypted());
			assertNull(objEntry.getValueDecrypted()); //Merke: sValue kann unterschiedlich zu dem decrypted Wert sein. Wenn etwas drumherum steht.
			
			bReturn = true;
		}//end main:
		return bReturn;
	}

	private static boolean assertFileIniEntry_parse_FLAGSET_PATH_SUBSTITUTED_(IEnumSetMappedTestSurroundingZZZ objEnumSurrounding, IKernelConfigSectionEntryZZZ objEntry, String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, boolean bAsEntry) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			
			String sExpression2compareWithSolved = null; String sExpression2compareWithSubstituted = null;
			String sExpressionSubstituted2compare = null; String sExpressionSubstituted2compareWithExpression = null;
			
			String sExpression = sExpressionIn;
			String sExpressionSubstituted = sExpressionSubstitutedIn;
			String sExpressionSolved = sExpressionSolvedIn;
			
			String sTagStartZ = "<Z>";
			String sTagEndZ = "</Z>";	
		
			//######## TEST-TEIL ###########################
			sExpressionSolved = sExpressionSolvedIn;
			assertTrue(objEntry.isExpression()); //ohne Expression-Nutzung kein Expression Eintrag!!!
								
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			assertTrue(objEntry.isParseCalled()); //Auch wenn die Expression nicht verarbeitet wird, dann ist doch geparsed worden....
			if(sExpression.equals(sExpressionSolved)) {
				assertFalse(objEntry.isParsedChanged());						
			}else {
				assertTrue(objEntry.isParsedChanged());
			}
			assertTrue(objEntry.isParsed());
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			assertTrue(objEntry.isPathSubstituteCalled());					
			sExpression2compareWithSubstituted = sExpression;
			if(objEnumSurrounding == EnumSetMappedTestSurroundingTypeZZZ.PARSE_REMOVE && objEntry.isParsed()) {
				sExpression2compareWithSubstituted = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpression2compareWithSubstituted, sTagStartZ, sTagEndZ, false);
			}
			System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpression2compareWithSubstituted=" + sExpression2compareWithSubstituted);
			System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpressionSubstituted=" + sExpressionSubstituted);					
			if(objEntry.isPathSubstituted()){ //Kann hier eigentlich nicht getestet werden. Ggfs. wird eine Expression ohne INI-PATH uebergeben
				if(sExpression2compareWithSubstituted.equals(sExpressionSubstituted)) { 
					assertFalse(objEntry.isPathSubstitutedChanged());
				}else {
					assertTrue(objEntry.isPathSubstitutedChanged());
				}
			}				
			assertTrue(objEntry.isPathSubstituted());
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			assertFalse(objEntry.isVariableSubstituteCalled());
																//+++ kann man hier doch auch eigentlich nicht so abfragen														
			assertFalse(objEntry.isVariableSubstituted());
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			
			assertFalse(objEntry.isSolveCalled()); //Der Solve-Schritt wurde nicht gemacht.					
			assertFalse(objEntry.isSolvedChanged()); //nur mit parse wird hier nix geaendert									 
			assertFalse(objEntry.isSolved()); //Der Solve-Schritt wurde nicht gemacht.
			
			assertFalse(objEntry.isDecrypted());
			assertNull(objEntry.getValueDecrypted()); //Merke: sValue kann unterschiedlich zu dem decrypted Wert sein. Wenn etwas drumherum steht.
			
			bReturn = true;
		}//end main:
		return bReturn;
	}
	
	private static boolean assertFileIniEntry_parse_FLAGSET_CALL_UNSOLVED_(IEnumSetMappedTestSurroundingZZZ objEnumSurrounding, IKernelConfigSectionEntryZZZ objEntry, String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, boolean bAsEntry) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			
			String sExpression2compareWithSolved = null; String sExpression2compareWithSubstituted = null;
			String sExpressionSubstituted2compare = null;
			
			String sExpression = sExpressionIn;
			String sExpressionSubstituted = sExpressionSubstitutedIn;
			String sExpressionSolved = sExpressionSolvedIn;
			
			String sTagStartZ = "<Z>";
			String sTagEndZ = "</Z>";	
		
			//######## TEST-TEIL ###########################
			assertTrue(objEntry.isParseCalled()); 					
			assertFalse(objEntry.isSolveCalled()); //hier nur .parse() - Fall
			assertFalse(objEntry.isCallSolveCalled()); //nur PARSE - Fall
			assertFalse(objEntry.isJavaCallSolveCalled());
			assertTrue(objEntry.isExpression());
			
			assertFalse(objEntry.isParsed());
			assertFalse(objEntry.isSolved()); //Der konkrete Solver ist nicht involviert
			
			assertFalse(objEntry.isDecrypted());
			assertNull(objEntry.getValueDecrypted()); //Merke: sValue kann unterschiedlich zu dem decrypted Wert sein. Wenn etwas drumherum steht.
			
			assertFalse(objEntry.isCall());		//Beim Parsen wird das festgestellt
			//kann ja theoretisch auch ein anderer Call sein... assertTrue(objEntry.isJavaCall());	//Beim Parsen wird das festgestellt
			assertNull(objEntry.getCallingClassname());
			assertNull(objEntry.getCallingMethodname());
			
			bReturn = true;
		}//end main:
		return bReturn;
	}
	
	private static boolean assertFileIniEntry_parse_FLAGSET_CALL_UNSOLVED_JAVACALL_SOLVED_(IEnumSetMappedTestSurroundingZZZ objEnumSurrounding, IKernelConfigSectionEntryZZZ objEntry, String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, boolean bAsEntry) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			
			String sExpression2compareWithSolved = null; String sExpression2compareWithSubstituted = null;
			String sExpressionSubstituted2compare = null;
			
			String sExpression = sExpressionIn;
			String sExpressionSubstituted = sExpressionSubstitutedIn;
			String sExpressionSolved = sExpressionSolvedIn;
			
			String sTagStartZ = "<Z>";
			String sTagEndZ = "</Z>";	
		
			//######## TEST-TEIL ###########################
			assertTrue(objEntry.isParseCalled()); 					
			assertFalse(objEntry.isSolveCalled()); //hier nur .parse() - Fall
			assertFalse(objEntry.isCallSolveCalled()); //nur PARSE - Fall
			assertFalse(objEntry.isJavaCallSolveCalled());
			assertTrue(objEntry.isExpression());
			
			assertTrue(objEntry.isParsed());  //Merke: Auch wenn Call disabled ist, so ist JAVACALL aktiv. also wird geparsed:
			assertFalse(objEntry.isSolved()); //Der konkrete Solver ist nicht involviert
			
			assertFalse(objEntry.isDecrypted());
			assertNull(objEntry.getValueDecrypted()); //Merke: sValue kann unterschiedlich zu dem decrypted Wert sein. Wenn etwas drumherum steht.
			
			assertTrue(objEntry.isCall());		//Beim Parsen wird das festgestellt (auch wenn call disabled ist).
			assertTrue(objEntry.isJavaCall());
			
			//kann ja theoretisch auch ein anderer Call sein... assertTrue(objEntry.isJavaCall());	//Beim Parsen wird das festgestellt
			assertNull(objEntry.getCallingClassname());
			assertNull(objEntry.getCallingMethodname());
			
			bReturn = true;
		}//end main:
		return bReturn;
	}
	
	private static boolean assertFileIniEntry_parse_FLAGSET_JAVACALL_SOLVED_(IEnumSetMappedTestSurroundingZZZ objEnumSurrounding, IKernelConfigSectionEntryZZZ objEntry, String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, boolean bAsEntry) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			
			String sExpression2compareWithSolved = null; String sExpression2compareWithSubstituted = null;
			String sExpressionSubstituted2compare = null;
			
			String sExpression = sExpressionIn;
			String sExpressionSubstituted = sExpressionSubstitutedIn;
			String sExpressionSolved = sExpressionSolvedIn;
			
			String sTagStartZ = "<Z>";
			String sTagEndZ = "</Z>";	
		
			//######## TEST-TEIL ###########################
			assertTrue(objEntry.isParseCalled());
			
			assertFalse(objEntry.isSolveCalled()); //nur parse - Fall
			assertFalse(objEntry.isCallSolveCalled()); //nur PARSE - Fall
			assertFalse(objEntry.isJavaCallSolveCalled());
			assertTrue(objEntry.isExpression());
			
			assertFalse(objEntry.isSolved()); //Der konkrete Solver ist nicht involviert
			
			assertFalse(objEntry.isDecrypted());
			assertNull(objEntry.getValueDecrypted()); 				
			
			assertTrue(objEntry.isCall());		//Beim Parsen wird das festgestellt
			assertTrue(objEntry.isJavaCall());	//Beim Parsen wird das festgestellt
			assertNull(objEntry.getCallingClassname());
			assertNull(objEntry.getCallingMethodname());
					
			bReturn = true;
		}//end main:
		return bReturn;
	}
	
	private static boolean assertFileIniEntry_parse_FLAGSET_JAVACALL_UNSOLVED_(IEnumSetMappedTestSurroundingZZZ objEnumSurrounding, IKernelConfigSectionEntryZZZ objEntry, String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, boolean bAsEntry) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			
			String sExpression2compareWithSolved = null; String sExpression2compareWithSubstituted = null;
			String sExpressionSubstituted2compare = null;
			
			String sExpression = sExpressionIn;
			String sExpressionSubstituted = sExpressionSubstitutedIn;
			String sExpressionSolved = sExpressionSolvedIn;
			
			String sTagStartZ = "<Z>";
			String sTagEndZ = "</Z>";	
		
			//######## TEST-TEIL ###########################
			assertTrue(objEntry.isParseCalled());
			assertFalse(objEntry.isSolveCalled()); 
			assertFalse(objEntry.isCallSolveCalled()); 
			assertFalse(objEntry.isJavaCallSolveCalled());
			assertTrue(objEntry.isExpression());
			
			assertFalse(objEntry.isSolved()); //Der konkrete Solver ist nicht involviert
			
			assertFalse(objEntry.isDecrypted());
			assertNull(objEntry.getValueDecrypted()); 				
			
			/*Zum Suchen der Gegenstelle TODOGOON20250308;
			 * 				//Es gibt keinen generischen Ansatz .isCall(true) zu erzeugen.
			                 //Das koennte man in KernelJavaCallIniSolverZZZ.parseFirstVector(...) zwar speziell tun.
			                 //Aber eigentlich will ich das irgendwie generisch machen...
			                 //so etwas mit "ElternTags"
			//TODOGOON20250308; //TICKETGOON20250308; //Analog zu dem PARENT - Tagnamen muesste es auch eine Loesung für die CHILD - Tagnamen geben
			*/
			assertTrue(objEntry.isCall());		//Beim Parsen wird das festgestellt
			assertFalse(objEntry.isJavaCall());	//JavaCall ist "unused" 
			assertNull(objEntry.getCallingClassname());
			assertNull(objEntry.getCallingMethodname());
			
			bReturn = true;
		}//end main:
		return bReturn;
	}
	
	private static boolean assertFileIniEntry_parse_FLAGSET_JSON_UNSOLVED_(IEnumSetMappedTestSurroundingZZZ objEnumSurrounding, IKernelConfigSectionEntryZZZ objEntry, String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, boolean bAsEntry) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			
			String sExpression2compareWithSolved = null; String sExpression2compareWithSubstituted = null;
			String sExpressionSubstituted2compare = null;
			
			String sExpression = sExpressionIn;
			String sExpressionSubstituted = sExpressionSubstitutedIn;
			String sExpressionSolved = sExpressionSolvedIn;
			
			String sTagStartZ = "<Z>";
			String sTagEndZ = "</Z>";	
		
			//######## TEST-TEIL ###########################
			assertTrue(objEntry.isExpression()); //ohne Expression-Nutzung kein Expression Eintrag!!!
			
			//++++++++++++++++++++++ Weil nicht gesolved wurde, kann auf das Parse Ergebnis zugegriffen werden
			assertTrue(objEntry.isParseCalled()); //Auch wenn die Expression nicht verarbeitet wird, dann ist doch geparsed worden....
			sExpression2compareWithSubstituted = sExpression;
			sExpressionSubstituted2compare = sExpressionSubstituted;
			if(objEnumSurrounding == EnumSetMappedTestSurroundingTypeZZZ.PARSE_REMOVE && objEntry.isParsed()) {
				sExpression2compareWithSubstituted = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpression2compareWithSubstituted, sTagStartZ, sTagEndZ, false);
				sExpressionSubstituted2compare = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSubstituted2compare, sTagStartZ, sTagEndZ, false);
			}
			System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpression2compareWithSubstituted=" + sExpression2compareWithSubstituted);
			System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpressionSubstituted2Compare=" + sExpressionSubstituted2compare);
			if(sExpression2compareWithSubstituted.equals(sExpressionSubstituted2compare)) {
				assertFalse(objEntry.isParsedChanged());						
			}else {
				assertTrue(objEntry.isParsedChanged());
			}
			assertTrue(objEntry.isParsed());
								
			//+++++++++++++++++++++++ ++++++++++++++++++++++ Weil nicht gesolved wurde, kann auf das Parse Ergebnis zugegriffen werden
			assertTrue(objEntry.isPathSubstituteCalled());
			sExpression2compareWithSubstituted = sExpression;
			sExpressionSubstituted2compare = sExpressionSubstituted;
			if(objEnumSurrounding == EnumSetMappedTestSurroundingTypeZZZ.PARSE_REMOVE && objEntry.isParsed()) {
				sExpression2compareWithSubstituted = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpression2compareWithSubstituted, sTagStartZ, sTagEndZ, false);
				sExpressionSubstituted2compare = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSubstituted2compare, sTagStartZ, sTagEndZ, false);
			}
			System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpression2compareWithSubstituted=" + sExpression2compareWithSubstituted);
			System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpressionSubstituted2compare=" + sExpressionSubstituted2compare);					
			if(objEntry.isPathSubstituted()){ //Kann hier eigentlich nicht getestet werden. Ggfs. wird eine Expression ohne INI-PATH uebergeben
				if(sExpression2compareWithSubstituted.equals(sExpressionSubstituted2compare)) { 
					assertFalse(objEntry.isSubstitutedChanged());
				}else {
					assertTrue(objEntry.isSubstitutedChanged());
				}
			}
			assertTrue(objEntry.isPathSubstituted()); //falls das entsprechende Flag gesetzt ist, unabhaengig davon, ob ein INI-PATH Ausdruck darin ist
			//++++++++++++++++++++++++
			//++++++++++++++++++++++++
			
			assertTrue(objEntry.isVariableSubstituted()); //falls das entsprechende Flag gesetzt ist, unabhaengig davon, ob eine INI-Variable darin ist
			//++++++++++++++++++++++++				
			//++++++++++++++++++++++++
			assertFalse(objEntry.isSolveCalled()); //Aufgerufen wurde der solveCall ja...					
			assertFalse(objEntry.isSolvedChanged()); 
			assertFalse(objEntry.isSolved());
			//+++++++++++++++++++++++++
			//+++++++++++++++++++++++++
			
			assertTrue(objEntry.isJson()); //bei diesem Flagset wird json zwar nicht aufgeloest, aber geparsed trotzdem
			//Zwar gilt: Bei diesem Flagset wird json zwar nicht aufgeloest, aber geparsed trotzdem
			//ABER:      Auf einzelne Werte kann nicht geprueft werden, da das Flagset sowohl für Map als auch Array verwendet wird
			//assertTrue(objEntry.isJsonMap());
			//assertTrue(objEntry.isJsonArray()); 
							
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//+++ Auf Werte kann man hier eigentlich nicht so abfragen, weil ggfs. keine Variablen in der Expression sind.
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			assertFalse(objEntry.isCallSolveCalled());
			assertFalse(objEntry.isCallSolved());//Der konkrete Solver ist nicht involviert
			//+++++++++++++++++++++++++
			//+++++++++++++++++++++++++
			assertFalse(objEntry.isJavaCallSolveCalled()); //trotz JAVACALL-Unsolved Flag wird der JAVACALL-Solver durchaus aufgerufen
			assertFalse(objEntry.isJavaCallSolved());//Der konkrete Solver ist nicht involviert
			//+++++++++++++++++++++++++
			
			//+++ Auf Werte kann man hier eigentlich nicht so abfragen, weil ggfs. doch CRYPTED Werte in der Expression sind
			assertFalse(objEntry.isDecrypted());
			assertNull(objEntry.getValueDecrypted()); //Merke: sValue kann unterschiedlich zu dem decrypted Wert sein. Wenn etwas drumherum steht.
			
			//+++ Auf Werte kann man hier eigentlich nicht so abfragen, weil ggfs. KEINE CALL-Werte in der Expression sind
			assertFalse(objEntry.isCall());		//Beim Parsen wird das festgestellt
			assertFalse(objEntry.isJavaCall()); 	//Beim Parsen wird das festgestellt
			assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingClassname() + "'", objEntry.getCallingClassname());
			assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingMethodname() + "'", objEntry.getCallingMethodname());
								
			//fail("Testutil for flagset missing: " + sFLAGSET_JSON_UNSOLVED);		
			bReturn = true;
		}//end main:
		return bReturn;
	}
	
	
	private static boolean assertFileIniEntry_parse_FLAGSET_JSONARRAY_UNSOLVED_(IEnumSetMappedTestSurroundingZZZ objEnumSurrounding, IKernelConfigSectionEntryZZZ objEntry, String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, boolean bAsEntry) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			
			String sExpression2compareWithSolved = null; String sExpression2compareWithSubstituted = null;
			String sExpressionSubstituted2compare = null;
			
			String sExpression = sExpressionIn;
			String sExpressionSubstituted = sExpressionSubstitutedIn;
			String sExpressionSolved = sExpressionSolvedIn;
			
			String sTagStartZ = "<Z>";
			String sTagEndZ = "</Z>";	
		
			//######## TEST-TEIL ###########################
			assertTrue(objEntry.isExpression()); //ohne Expression-Nutzung kein Expression Eintrag!!!
			
			//++++++++++++++++++++++ Weil nicht gesolved wurde, kann auf das Parse Ergebnis zugegriffen werden
			assertTrue(objEntry.isParseCalled()); //Auch wenn die Expression nicht verarbeitet wird, dann ist doch geparsed worden....
			sExpression2compareWithSubstituted = sExpression;
			if(objEnumSurrounding == EnumSetMappedTestSurroundingTypeZZZ.PARSE_REMOVE && objEntry.isParsed()) {
				sExpression2compareWithSubstituted = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpression2compareWithSubstituted, sTagStartZ, sTagEndZ, false);
			}
			System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpression2compareWithSubsituted=" + sExpression2compareWithSubstituted);
			System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpressionSubstituted=" + sExpressionSubstituted);
			if(sExpression2compareWithSubstituted.equals(sExpressionSubstituted)) {
				assertFalse(objEntry.isParsedChanged());						
			}else {
				assertTrue(objEntry.isParsedChanged());
			}
			assertTrue(objEntry.isParsed());
								
			//+++++++++++++++++++++++ ++++++++++++++++++++++ Weil nicht gesolved wurde, kann auf das Parse Ergebnis zugegriffen werden
			assertTrue(objEntry.isPathSubstituteCalled());
			sExpression2compareWithSubstituted = sExpression;
			if(objEnumSurrounding == EnumSetMappedTestSurroundingTypeZZZ.PARSE_REMOVE && objEntry.isParsed()) {
				sExpression2compareWithSubstituted = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpression2compareWithSubstituted, sTagStartZ, sTagEndZ, false);
			}
			System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpression2compareWithSubstituted=" + sExpression2compareWithSubstituted);
			System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpressionSubstituted=" + sExpressionSubstituted);					
			if(objEntry.isPathSubstituted()){ //Kann hier eigentlich nicht getestet werden. Ggfs. wird eine Expression ohne INI-PATH uebergeben
				if(sExpression2compareWithSubstituted.equals(sExpressionSubstituted)) { 
					assertFalse(objEntry.isSubstitutedChanged());
				}else {
					assertTrue(objEntry.isSubstitutedChanged());
				}
			}
			assertTrue(objEntry.isPathSubstituted()); //falls das entsprechende Flag gesetzt ist, unabhaengig davon, ob ein INI-PATH Ausdruck darin ist
			//++++++++++++++++++++++++
			//++++++++++++++++++++++++
			
			assertTrue(objEntry.isVariableSubstituteCalled()); //falls das entsprechende Flag gesetzt ist, unabhaengig davon, ob eine INI-Variable darin ist
			//auf WErte kann hier nicht abgefragt werden, ggf. ja keine Variablen im String drin.
			assertTrue(objEntry.isVariableSubstituted()); //falls das entsprechende Flag gesetzt ist, unabhaengig davon, ob eine INI-Variable darin ist
			//++++++++++++++++++++++++				
			//++++++++++++++++++++++++
			assertFalse(objEntry.isSolveCalled()); //Aufgerufen wurde der solveCall ja...					
			assertFalse(objEntry.isSolvedChanged()); 
			assertFalse(objEntry.isSolved());
			//+++++++++++++++++++++++++
			//+++++++++++++++++++++++++
			
			assertTrue(objEntry.isJson()); //Werte kommen aus dem Parsen.  
			assertFalse(objEntry.isJsonMap());
			assertFalse(objEntry.isJsonArray());
							
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//+++ Auf Werte kann man hier eigentlich nicht so abfragen, weil ggfs. keine Variablen in der Expression sind.
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			assertFalse(objEntry.isCallSolveCalled());
			assertFalse(objEntry.isCallSolved());//Der konkrete Solver ist nicht involviert
			//+++++++++++++++++++++++++
			//+++++++++++++++++++++++++
			assertFalse(objEntry.isJavaCallSolveCalled()); //trotz JAVACALL-Unsolved Flag wird der JAVACALL-Solver durchaus aufgerufen
			assertFalse(objEntry.isJavaCallSolved());//Der konkrete Solver ist nicht involviert
			//+++++++++++++++++++++++++
			
			//+++ Auf Werte kann man hier eigentlich nicht so abfragen, weil ggfs. doch CRYPTED Werte in der Expression sind
			assertFalse(objEntry.isDecrypted());
			assertNull(objEntry.getValueDecrypted()); //Merke: sValue kann unterschiedlich zu dem decrypted Wert sein. Wenn etwas drumherum steht.
			
			//+++ Auf Werte kann man hier eigentlich nicht so abfragen, weil ggfs. KEINE CALL-Werte in der Expression sind
			assertFalse(objEntry.isCall());		//Beim Parsen wird das festgestellt
			assertFalse(objEntry.isJavaCall()); 	//Beim Parsen wird das festgestellt
			assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingClassname() + "'", objEntry.getCallingClassname());
			assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingMethodname() + "'", objEntry.getCallingMethodname());
			
			//fail("Testutil for flagset missing: " + sFLAGSET_JSONARRAY_UNSOLVED);		
			bReturn = true;
		}//end main:
		return bReturn;
	}
	
	private static boolean assertFileIniEntry_parse_FLAGSET_JSONARRAY_SOLVED_(IEnumSetMappedTestSurroundingZZZ objEnumSurrounding, IKernelConfigSectionEntryZZZ objEntry, String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, boolean bAsEntry) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			
			String sExpression2compareWithSolved = null; String sExpression2compareWithSubstituted = null;
			String sExpressionSubstituted2compare = null;
			
			String sExpression = sExpressionIn;
			String sExpressionSubstituted = sExpressionSubstitutedIn;
			String sExpressionSolved = sExpressionSolvedIn;
			
			String sTagStartZ = "<Z>";
			String sTagEndZ = "</Z>";	
		
			//######## TEST-TEIL ###########################
			assertTrue(objEntry.isExpression()); //ohne Expression-Nutzung kein Expression Eintrag!!!
			
			//++++++++++++++++++++++
			assertTrue(objEntry.isParseCalled()); //Es ist geparsed worden....
			//Kann nicht getestet wurde, weil SOLVE den Tag ggfs. verfaelscht: objEntry.isParsedChanged()					
			assertTrue(objEntry.isParsed());
								
			//+++++++++++++++++++++++
			assertTrue(objEntry.isPathSubstituteCalled());															
			//Kann nicht getestet wurde, weil SOLVE den Tag ggfs. verfaelscht: objEntry.isSubstitutedChanged()					
			assertTrue(objEntry.isPathSubstituted()); //falls das entsprechende Flag gesetzt ist, unabhaengig davon, ob ein INI-PATH Ausdruck darin ist
			//++++++++++++++++++++++++
			//++++++++++++++++++++++++
			
			
			assertTrue(objEntry.isVariableSubstituted()); //falls das entsprechende Flag gesetzt ist, unabhaengig davon, ob eine INI-Variable darin ist
			//++++++++++++++++++++++++				
			//++++++++++++++++++++++++
			assertFalse(objEntry.isSolveCalled()); //Wir sind im PARSE Test, da wurde noch nix gesolved					
			assertFalse(objEntry.isSolvedChanged()); 
			assertFalse(objEntry.isSolved());
			//+++++++++++++++++++++++++
			//+++++++++++++++++++++++++
			
			assertTrue(objEntry.isJson()); //Kommt aus parse
			assertFalse(objEntry.isJsonMap());
			assertTrue(objEntry.isJsonArray());
							
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//+++ Auf Werte kann man hier eigentlich nicht so abfragen, weil ggfs. keine Variablen in der Expression sind.
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			assertFalse(objEntry.isCallSolveCalled());
			assertFalse(objEntry.isCallSolved());//Der konkrete Solver ist nicht involviert
			//+++++++++++++++++++++++++
			//+++++++++++++++++++++++++
			assertFalse(objEntry.isJavaCallSolveCalled()); //trotz JAVACALL-Unsolved Flag wird der JAVACALL-Solver durchaus aufgerufen
			assertFalse(objEntry.isJavaCallSolved());//Der konkrete Solver ist nicht involviert
			//+++++++++++++++++++++++++
			
			//+++ Auf Werte kann man hier eigentlich nicht so abfragen, weil ggfs. doch CRYPTED Werte in der Expression sind
			assertFalse(objEntry.isDecrypted());
			assertNull(objEntry.getValueDecrypted()); //Merke: sValue kann unterschiedlich zu dem decrypted Wert sein. Wenn etwas drumherum steht.
			
			//+++ Auf Werte kann man hier eigentlich nicht so abfragen, weil ggfs. KEINE CALL-Werte in der Expression sind
			assertFalse(objEntry.isCall());		//Beim Parsen wird das festgestellt
			assertFalse(objEntry.isJavaCall()); 	//Beim Parsen wird das festgestellt
			assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingClassname() + "'", objEntry.getCallingClassname());
			assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingMethodname() + "'", objEntry.getCallingMethodname());
			
			//fail("Testutil for flagset missing: " + sFLAGSET_JSONARRAY_SOLVED);
			
			bReturn = true;
		}//end main:
		return bReturn;
	}
	
	private static boolean assertFileIniEntry_parse_FLAGSET_JSONMAP_UNSOLVED_(IEnumSetMappedTestSurroundingZZZ objEnumSurrounding, IKernelConfigSectionEntryZZZ objEntry, String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, boolean bAsEntry) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			
			String sExpression2compareWithSolved = null; String sExpression2compareWithSubstituted = null;
			String sExpressionSubstituted2compare = null;
			
			String sExpression = sExpressionIn;
			String sExpressionSubstituted = sExpressionSubstitutedIn;
			String sExpressionSolved = sExpressionSolvedIn;
			
			String sTagStartZ = "<Z>";
			String sTagEndZ = "</Z>";	
		
			//######## TEST-TEIL ###########################
			assertTrue(objEntry.isExpression()); //ohne Expression-Nutzung kein Expression Eintrag!!!
			
			//++++++++++++++++++++++ Weil nicht gesolved wurde, kann auf das Parse Ergebnis zugegriffen werden
			assertTrue(objEntry.isParseCalled()); //Auch wenn die Expression nicht verarbeitet wird, dann ist doch geparsed worden....
			sExpression2compareWithSubstituted = sExpression;
			if(objEnumSurrounding == EnumSetMappedTestSurroundingTypeZZZ.PARSE_REMOVE && objEntry.isParsed()) {
				sExpression2compareWithSubstituted = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpression2compareWithSubstituted, sTagStartZ, sTagEndZ, false);
			}
			System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpression2compareWithSubsituted=" + sExpression2compareWithSubstituted);
			System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpressionSubstituted=" + sExpressionSubstituted);
			if(sExpression2compareWithSubstituted.equals(sExpressionSubstituted)) {
				assertFalse(objEntry.isParsedChanged());						
			}else {
				assertTrue(objEntry.isParsedChanged());
			}
			assertTrue(objEntry.isParsed());
								
			//+++++++++++++++++++++++ ++++++++++++++++++++++ Weil nicht gesolved wurde, kann auf das Parse Ergebnis zugegriffen werden
			assertTrue(objEntry.isPathSubstituteCalled());
			sExpression2compareWithSubstituted = sExpression;
			if(objEnumSurrounding == EnumSetMappedTestSurroundingTypeZZZ.PARSE_REMOVE && objEntry.isParsed()) {
				sExpression2compareWithSubstituted = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpression2compareWithSubstituted, sTagStartZ, sTagEndZ, false);
			}
			System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpression2compareWithSubstituted=" + sExpression2compareWithSubstituted);
			System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpressionSubstituted=" + sExpressionSubstituted);					
			if(objEntry.isPathSubstituted()){ //Kann hier eigentlich nicht getestet werden. Ggfs. wird eine Expression ohne INI-PATH uebergeben
				if(sExpression2compareWithSubstituted.equals(sExpressionSubstituted)) { 
					assertFalse(objEntry.isPathSubstitutedChanged());
				}else {
					assertTrue(objEntry.isPathSubstitutedChanged());
				}
			}
			assertTrue(objEntry.isPathSubstituted()); //falls das entsprechende Flag gesetzt ist, unabhaengig davon, ob ein INI-PATH Ausdruck darin ist
			//++++++++++++++++++++++++
			//++++++++++++++++++++++++
			
			assertTrue(objEntry.isVariableSubstituteCalled()); //falls das entsprechende Flag gesetzt ist, unabhaengig davon, ob eine INI-Variable darin ist
			//auf Werte kann hier nicht abgefragt werden, ggf. ja keine Variablen im String drin.
			assertTrue(objEntry.isVariableSubstituted()); //falls das entsprechende Flag gesetzt ist, unabhaengig davon, ob eine INI-Variable darin ist
			//++++++++++++++++++++++++				
			//++++++++++++++++++++++++
			assertFalse(objEntry.isSolveCalled()); //Aufgerufen wurde der solveCall ja...					
			assertFalse(objEntry.isSolvedChanged()); 
			assertFalse(objEntry.isSolved());
			//+++++++++++++++++++++++++
			//+++++++++++++++++++++++++
			
			assertTrue(objEntry.isJson()); //JSON_MAP unsolved, aber JSON solved
			assertFalse(objEntry.isJsonMap()); //Wert kommt aus parse.. zwar ist parserEnabledCustom=true, aber parserEnabledThis=false
			assertFalse(objEntry.isJsonArray());
							
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//+++ Auf Werte kann man hier eigentlich nicht so abfragen, weil ggfs. keine Variablen in der Expression sind.
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			assertFalse(objEntry.isCallSolveCalled());
			assertFalse(objEntry.isCallSolved());//Der konkrete Solver ist nicht involviert
			//+++++++++++++++++++++++++
			//+++++++++++++++++++++++++
			assertFalse(objEntry.isJavaCallSolveCalled()); //trotz JAVACALL-Unsolved Flag wird der JAVACALL-Solver durchaus aufgerufen
			assertFalse(objEntry.isJavaCallSolved());//Der konkrete Solver ist nicht involviert
			//+++++++++++++++++++++++++
			
			//+++ Auf Werte kann man hier eigentlich nicht so abfragen, weil ggfs. doch CRYPTED Werte in der Expression sind
			assertFalse(objEntry.isDecrypted());
			assertNull(objEntry.getValueDecrypted()); //Merke: sValue kann unterschiedlich zu dem decrypted Wert sein. Wenn etwas drumherum steht.
			
			//+++ Auf Werte kann man hier eigentlich nicht so abfragen, weil ggfs. KEINE CALL-Werte in der Expression sind
			assertFalse(objEntry.isCall());		//Beim Parsen wird das festgestellt
			assertFalse(objEntry.isJavaCall()); 	//Beim Parsen wird das festgestellt
			assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingClassname() + "'", objEntry.getCallingClassname());
			assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingMethodname() + "'", objEntry.getCallingMethodname());
			
			//fail("Testutil for flagset missing: " + sFLAGSET_JSONMAP_UNSOLVED);
			
			bReturn = true;
		}//end main:
		return bReturn;
	}
	
	private static boolean assertFileIniEntry_parse_FLAGSET_JSONMAP_SOLVED_(IEnumSetMappedTestSurroundingZZZ objEnumSurrounding, IKernelConfigSectionEntryZZZ objEntry, String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, boolean bAsEntry) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			
			String sExpression2compareWithSolved = null; String sExpression2compareWithSubstituted = null;
			String sExpressionSubstituted2compare = null;
			
			String sExpression = sExpressionIn;
			String sExpressionSubstituted = sExpressionSubstitutedIn;
			String sExpressionSolved = sExpressionSolvedIn;
			
			String sTagStartZ = "<Z>";
			String sTagEndZ = "</Z>";	
		
			//######## TEST-TEIL ###########################
			assertTrue(objEntry.isExpression()); //ohne Expression-Nutzung kein Expression Eintrag!!!
			
			//++++++++++++++++++++++
			assertTrue(objEntry.isParseCalled()); //Es ist geparsed worden....
			//Kann nicht getestet wurde, weil SOLVE den Tag ggfs. verfaelscht: objEntry.isParsedChanged()					
			assertTrue(objEntry.isParsed());
								
			//+++++++++++++++++++++++
			assertTrue(objEntry.isPathSubstituteCalled());															
			//Kann nicht getestet wurde, weil SOLVE den Tag ggfs. verfaelscht: objEntry.isSubstitutedChanged()					
			assertTrue(objEntry.isPathSubstituted()); //falls das entsprechende Flag gesetzt ist, unabhaengig davon, ob ein INI-PATH Ausdruck darin ist
			//++++++++++++++++++++++++
			//++++++++++++++++++++++++
			
			
			assertTrue(objEntry.isVariableSubstituted()); //falls das entsprechende Flag gesetzt ist, unabhaengig davon, ob eine INI-Variable darin ist
			//++++++++++++++++++++++++				
			//++++++++++++++++++++++++
			assertFalse(objEntry.isSolveCalled()); //Wir sind im PARSE Test, da wurde noch nix gesolved					
			assertFalse(objEntry.isSolvedChanged()); 
			assertFalse(objEntry.isSolved());
			//+++++++++++++++++++++++++
			//+++++++++++++++++++++++++
			
			assertTrue(objEntry.isJson()); //Kommt aus parse
			assertTrue(objEntry.isJsonMap());
			assertFalse(objEntry.isJsonArray());
							
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//+++ Auf Werte kann man hier eigentlich nicht so abfragen, weil ggfs. keine Variablen in der Expression sind.
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			assertFalse(objEntry.isCallSolveCalled());
			assertFalse(objEntry.isCallSolved());//Der konkrete Solver ist nicht involviert
			//+++++++++++++++++++++++++
			//+++++++++++++++++++++++++
			assertFalse(objEntry.isJavaCallSolveCalled()); //trotz JAVACALL-Unsolved Flag wird der JAVACALL-Solver durchaus aufgerufen
			assertFalse(objEntry.isJavaCallSolved());//Der konkrete Solver ist nicht involviert
			//+++++++++++++++++++++++++
			
			//+++ Auf Werte kann man hier eigentlich nicht so abfragen, weil ggfs. doch CRYPTED Werte in der Expression sind
			assertFalse(objEntry.isDecrypted());
			assertNull(objEntry.getValueDecrypted()); //Merke: sValue kann unterschiedlich zu dem decrypted Wert sein. Wenn etwas drumherum steht.
			
			//+++ Auf Werte kann man hier eigentlich nicht so abfragen, weil ggfs. KEINE CALL-Werte in der Expression sind
			assertFalse(objEntry.isCall());		//Beim Parsen wird das festgestellt
			assertFalse(objEntry.isJavaCall()); 	//Beim Parsen wird das festgestellt
			assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingClassname() + "'", objEntry.getCallingClassname());
			assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingMethodname() + "'", objEntry.getCallingMethodname());

			//fail("Testutil for flagset missing: " + sFLAGSET_JSONMAP_SOLVED);
		
			bReturn = true;
		}//end main:
		return bReturn;
	}
	
		
	/**
	 * @param objEnumSurrounding
	 * @param objEnumTestFlagset
	 * @param objEntry
	 * @param sExpressionIn
	 * @param sExpressionSubstitutedIn
	 * @param sExpressionSolvedIn
	 * @param bAsEntry
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 25.05.2025, 06:58:22
	 */
	public static boolean assertFileIniEntry_solve(IEnumSetMappedTestSurroundingZZZ objEnumSurrounding, IEnumSetMappedTestFlagsetZZZ objEnumTestFlagset,IKernelConfigSectionEntryZZZ objEntry, String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, boolean bAsEntry) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			String sExpression = sExpressionIn;
			String sExpressionSubstituted = sExpressionSubstitutedIn;
			String sExpressionSolved = sExpressionSolvedIn;
			
			String sCaseSet = TestUtilAsTestZZZ.sCASE_SOLVE;
			String sFlagSet = objEnumTestFlagset.getAbbreviation();
			
			String sTagStartZ = "<Z>";
			String sTagEndZ = "</Z>";
			String sFormulaSolvedAndConverted=null; String sFormulaSolvedAndConvertedAsExpression=null;
			String sExpressionSubstituted2compareWithSolved=null; String sExpressionSubstituted2compareWithExpression = null; 
			String sExpressionParsed2compareWithSubstituted=null; String sExpressionSolved2compareWithSubstituted=null;
			String sExpression2compareWithSubstituted=null; String sExpression2compareWithSolved = null;
			String sSubstituted2compare=null;
			
			
			boolean bCallSolverCalledPrevious=false;
			
			switch(sFlagSet) {
			//Das ist keine Konstante, case EnumSetMappedTestCaseFlagsetTypeZZZ.UNEXPRESSED.getAbbreviation():
			case sFLAGSET_UNEXPRESSED:
				bReturn = assertFileIniEntry_solve_FLAGSET_UNEXPRESSED_(objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
				break;	
			case sFLAGSET_UNPARSED:
				bReturn = assertFileIniEntry_solve_FLAGSET_UNPARSED_(objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);								
				break;		
			case sFLAGSET_UNSOLVED:					
				bReturn = assertFileIniEntry_solve_FLAGSET_UNSOLVED_(objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);								
				break;
			case sFLAGSET_UNPARSED_UNSOLVED:
				bReturn = assertFileIniEntry_solve_FLAGSET_UNPARSED_UNSOLVED_(objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
				break;										
			case sFLAGSET_MATH_UNSOLVED:	//Es wird z:Formula gesolved!!!
				bReturn = assertFileIniEntry_solve_FLAGSET_MATH_UNSOLVED_(objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
				break;
				
			case sFLAGSET_SOLVED:				
				bReturn = assertFileIniEntry_solve_FLAGSET_SOLVED_(objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);				
				break;
			
			case sFLAGSET_PATH_UNSUBSTITUTED:				
				bReturn = assertFileIniEntry_solve_FLAGSET_PATH_UNSUBSTITUTED_(objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
				break;

			case sFLAGSET_PATH_SUBSTITUTED:
				bReturn = assertFileIniEntry_solve_FLAGSET_PATH_SUBSTITUTED_(objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
				break;
				
			case sFLAGSET_CALL_UNSOLVED:
				bReturn = assertFileIniEntry_solve_FLAGSET_CALL_UNSOLVED_(objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
				break;
			
			case sFLAGSET_CALL_UNSOLVED_JAVACALL_SOLVED:	
				bReturn = assertFileIniEntry_solve_FLAGSET_CALL_UNSOLVED_JAVACALL_SOLVED_(objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
				break;
											
			case sFLAGSET_JAVACALL_SOLVED:
				bReturn = assertFileIniEntry_solve_FLAGSET_JAVACALL_SOLVED_(objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
				break;

			case sFLAGSET_JAVACALL_UNSOLVED:
				bReturn = assertFileIniEntry_solve_FLAGSET_JAVACALL_UNSOLVED_(objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
				break;
				
			case sFLAGSET_JSON_SOLVED:
				fail("Testutil for flagset missing: " + sFLAGSET_JSON_SOLVED);
				break;
				
			case sFLAGSET_JSON_UNSOLVED:
				bReturn = assertFileIniEntry_solve_FLAGSET_JSON_UNSOLVED_(objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
				break;			
				
			case sFLAGSET_JSONARRAY_SOLVED:
				bReturn = assertFileIniEntry_solve_FLAGSET_JSONARRAY_SOLVED_(objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
				break;
								
			case sFLAGSET_JSONARRAY_UNSOLVED:
				bReturn = assertFileIniEntry_solve_FLAGSET_JSONARRAY_UNSOLVED_(objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
				break;
//			case sFLAGSET_JSONARRAY_JSONARRAY_UNSOLVED:
//				assertTrue(objEntry.isExpression()); //ohne Expression-Nutzung kein Expression Eintrag!!!
//				
//				//++++++++++++++++++++++ Weil der Tag nicht gesolved wurde, kann auf das Parse Ergebnis zugegriffen werden
//				assertTrue(objEntry.isParseCalled()); //Auch wenn die Expression nicht verarbeitet wird, dann ist doch geparsed worden....
//				sExpression2compareWithSubstituted = sExpression;
//				if(objEnumSurrounding == EnumSetMappedTestSurroundingTypeZZZ.SOLVE_REMOVE && objEntry.isSolved()) {) {
//					sExpression2compareWithSubstituted = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpression2compareWithSubstituted, sTagStartZ, sTagEndZ, false);
//				}
//				System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpression2compareWithSubsituted=" + sExpression2compareWithSubstituted);
//				System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpressionSubstituted=" + sExpressionSubstituted);
//				if(sExpression2compareWithSubstituted.equals(sExpressionSubstituted)) {
//					assertFalse(objEntry.isParsedChanged());						
//				}else {
//					assertTrue(objEntry.isParsedChanged());
//				}
//				assertTrue(objEntry.isParsed());
//									
//				//+++++++++++++++++++++++ weil der Tag nicht gesolved wurde, kann auf das Parse Ergebnis zugegriffen werden
//				assertTrue(objEntry.isPathSubstituteCalled());
//				sExpression2compareWithSubstituted = sExpression;
//				if(objEnumSurrounding == EnumSetMappedTestSurroundingTypeZZZ.SOLVE_REMOVE && objEntry.isSolved()) {) {
//					sExpression2compareWithSubstituted = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpression2compareWithSubstituted, sTagStartZ, sTagEndZ, false);
//				}
//				System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpression2compareWithSubstituted=" + sExpression2compareWithSubstituted);
//				System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpressionSubstituted=" + sExpressionSubstituted);					
//				if(objEntry.isPathSubstituted()){ //Kann hier eigentlich nicht getestet werden. Ggfs. wird eine Expression ohne INI-PATH uebergeben
//					if(sExpression2compareWithSubstituted.equals(sExpressionSubstituted)) { 
//						assertFalse(objEntry.isSubstitutedChanged());
//					}else {
//						assertTrue(objEntry.isSubstitutedChanged());
//					}
//				}
//				assertTrue(objEntry.isPathSubstituted()); //falls das entsprechende Flag gesetzt ist, unabhaengig davon, ob ein INI-PATH Ausdruck darin ist													
//				//++++++++++++++++++++++++
//				//++++++++++++++++++++++++
//				
//				assertTrue(objEntry.isVariableSubstituted()); //falls das entsprechende Flag gesetzt ist, unabhaengig davon, ob eine INI-Variable darin ist
//				//++++++++++++++++++++++++				
//				//++++++++++++++++++++++++
//				assertTrue(objEntry.isSolveCalled()); //Aufgerufen wurde der solveCall ja...					
//				assertFalse(objEntry.isSolvedChanged()); 
//				assertTrue(objEntry.isSolved());      //Ausgefuerhrt wurde der generelle Solver ja...     
//				//+++++++++++++++++++++++++
//				//+++++++++++++++++++++++++
//				
//				assertTrue(objEntry.isJson()); //Ergebnisse kommen vom Parsen
//				assertFalse(objEntry.isJsonMap());
//				assertTrue(objEntry.isJsonArray());
//								
//				//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
//				//+++ Auf Werte kann man hier eigentlich nicht so abfragen, weil ggfs. keine Variablen in der Expression sind.
//				//++++++++++++++++++++++++++++++++++++++++++++++++++++++++
//				assertFalse(objEntry.isCallSolveCalled());
//				assertFalse(objEntry.isCallSolved());//Der konkrete Solver ist nicht involviert
//				//+++++++++++++++++++++++++
//				//+++++++++++++++++++++++++
//				assertFalse(objEntry.isJavaCallSolveCalled()); //trotz JAVACALL-Unsolved Flag wird der JAVACALL-Solver durchaus aufgerufen
//				assertFalse(objEntry.isJavaCallSolved());//Der konkrete Solver ist nicht involviert
//				//+++++++++++++++++++++++++
//				
//				//+++ Auf Werte kann man hier eigentlich nicht so abfragen, weil ggfs. doch CRYPTED Werte in der Expression sind
//				assertFalse(objEntry.isDecrypted());
//				assertNull(objEntry.getValueDecrypted()); //Merke: sValue kann unterschiedlich zu dem decrypted Wert sein. Wenn etwas drumherum steht.
//				
//				//+++ Auf Werte kann man hier eigentlich nicht so abfragen, weil ggfs. KEINE CALL-Werte in der Expression sind
//				assertFalse(objEntry.isCall());		//Beim Parsen wird das festgestellt
//				assertFalse(objEntry.isJavaCall()); 	//Beim Parsen wird das festgestellt
//				assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingClassname() + "'", objEntry.getCallingClassname());
//				assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingMethodname() + "'", objEntry.getCallingMethodname());
//				
//				//fail("Testutil for flagset missing: " + sFLAGSET_JSONARRAY_JSONARRAY_UNSOLVED);
//				break;		
			case sFLAGSET_JSONMAP_SOLVED:
				bReturn = assertFileIniEntry_solve_FLAGSET_JSONMAP_SOLVED_(objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
				break;
				
			case sFLAGSET_JSONMAP_UNSOLVED:					
				bReturn = assertFileIniEntry_solve_FLAGSET_JSONMAP_UNSOLVED_(objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
				break;
			default:
				fail("Test Flagset '" + sFlagSet + "' im Case '"+sCaseSet+"' ist nicht definiert");
				break;
			}
									
			bReturn = true;
		}//end main:
		return bReturn;
	}
	
	private static boolean assertFileIniEntry_solve_FLAGSET_UNEXPRESSED_(IEnumSetMappedTestSurroundingZZZ objEnumSurrounding, IKernelConfigSectionEntryZZZ objEntry, String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, boolean bAsEntry) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			String sExpression = sExpressionIn;
			String sExpressionSubstituted = sExpressionSubstitutedIn;
			String sExpressionSolved = sExpressionSolvedIn;
			
			String sTagStartZ = "<Z>";
			String sTagEndZ = "</Z>";	
			
			String sFormulaSolvedAndConverted=null; String sFormulaSolvedAndConvertedAsExpression=null;
			String sExpressionSubstituted2compareWithSolved=null; String sExpressionSubstituted2compareWithExpression = null; 
			String sExpressionParsed2compareWithSubstituted=null; String sExpressionSolved2compareWithSubstituted=null;
			String sExpression2compareWithSubstituted=null; String sExpression2compareWithSolved = null;
			String sSubstituted2compare=null;
				
			boolean bCallSolverCalledPrevious=false;
		
		
			//######## TEST-TEIL ###########################
			assertTrue(objEntry.isSolveCalled()); //auch ohne Expression Auswertung wurde hier solve() aufgerufen.
			assertFalse(objEntry.isParseCalled()); //... danach wurde abgebrochen, also auch kein parse ausgefuehrt.
			assertFalse(objEntry.isExpression());
												
			//Keine Parser Ergebnisse (Abfrage der asserts in umgekehrter Reihenfolge ihres moeglichen Wertesetzens im Code)
			assertFalse(objEntry.isParsed()); //sollte ohne EXPRESSION abgebrochen worden sein				
			assertFalse(objEntry.isParsedChanged());
			
			assertFalse(objEntry.isPathSubstituted());				
			assertFalse(objEntry.isVariableSubstituted());
			
			//Keine Solver Ergebnisse (Abfrage der asserts in umgekehrter Reihenfolge ihres moeglichen Wertesetzens im Code)
			assertFalse(objEntry.isSolved()); //sollte ohne EXPRESSION abgebrochen worden sein
			assertFalse(objEntry.isSolvedChanged());						
			
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			
			sFormulaSolvedAndConverted = objEntry.getValueFormulaSolvedAndConverted();
			assertNull("NULL erwartet. Wert ist aber '" + sFormulaSolvedAndConverted + "'", sFormulaSolvedAndConverted); //Da keine Formel enthalten ist
					
			sFormulaSolvedAndConvertedAsExpression = objEntry.getValueFormulaSolvedAndConvertedAsExpression();					
			assertEquals(XmlUtilZZZ.computeTagNull(), sFormulaSolvedAndConvertedAsExpression);//Da keine Formel enthalten ist.

			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++

			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		
			assertFalse(objEntry.isDecrypted());
			assertNull(objEntry.getValueDecrypted()); //Merke: sValue kann unterschiedlich zu dem decrypted Wert sein. Wenn etwas drumherum steht.
			
			assertFalse(objEntry.isCall());
			assertFalse(objEntry.isJavaCall());
			assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingClassname() + "'", objEntry.getCallingClassname());
			assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingMethodname() + "'", objEntry.getCallingMethodname());
			
			bReturn = true;
		}//end main:
		return bReturn;
	}

	private static boolean assertFileIniEntry_solve_FLAGSET_UNPARSED_(IEnumSetMappedTestSurroundingZZZ objEnumSurrounding, IKernelConfigSectionEntryZZZ objEntry, String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, boolean bAsEntry) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			String sExpression = sExpressionIn;
			String sExpressionSubstituted = sExpressionSubstitutedIn;
			String sExpressionSolved = sExpressionSolvedIn;
			
			String sTagStartZ = "<Z>";
			String sTagEndZ = "</Z>";	
			
			String sFormulaSolvedAndConverted=null; String sFormulaSolvedAndConvertedAsExpression=null;
			String sExpressionSubstituted2compareWithSolved=null; String sExpressionSubstituted2compareWithExpression = null; 
			String sExpressionParsed2compareWithSubstituted=null; String sExpressionSolved2compareWithSubstituted=null;
			String sExpression2compareWithSubstituted=null; String sExpression2compareWithSolved = null;
			String sSubstituted2compare=null;
				
			boolean bCallSolverCalledPrevious=false;
		
		
			//######## TEST-TEIL ###########################
			//wie unsolved, aber hier gibt es auch keine Parser - Ergebnisse
			assertTrue(objEntry.isExpression()); //ohne Expression-Nutzung kein Expression Eintrag!!!									
			
			//+++++++++++++++++++++++++++++++++++++++
			//Keine Parser Ergebnisse (Abfrage der asserts in umgekehrter Reihenfolge ihres moeglichen Wertesetzens im Code)
			assertTrue(objEntry.isParseCalled()); //Auch wenn die Expression nicht verarbeitet wird, dann ist doch geparsed worden....
			assertFalse(objEntry.isParsed());
			
			//++++++++++++++++++++++++++++++++++++++++++++				
			assertFalse(objEntry.isPathSubstituteCalled());
			assertFalse(objEntry.isPathSubstituted()); //falls das entsprechende Flag gesetzt ist, unabhaengig davon, ob ein INI-PATH Ausdruck darin ist
			
			//++++++++++++++++++++++++++++++++++++++++
			assertFalse(objEntry.isVariableSubstituteCalled());
							 				
			//if(objEntry.isPathSubstituted()){ //Kann hier eigentlich nicht getestet werden. Ggfs. wird eine Expression ohne INI-PATH uebergeben
			//	if(sExpression.equals(sExpressionSubstituted)) { 
			//		assertFalse(objEntry.isSubstitutedChanged());
			//	}else {
			//		assertFalse(objEntry.isSubstitutedChanged());
			//	}
			//}
			
			assertFalse(objEntry.isVariableSubstituted()); //falls das entsprechende Flag gesetzt ist, unabhaengig davon, ob eine INI-Variable darin ist
			//+++ Auf Werte kann man hier eigentlich nicht so abfragen, weil ggfs. keine Variablen in der Expression sind.
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			
			
			//++++++++++++++++++++++++++++++++++++++++++
			//Keine Solver Ergebnisse
			assertTrue(objEntry.isSolveCalled());
			assertFalse(objEntry.isSolved()); //sollte ohne SOLVE abgebrochen worden sein
			assertFalse(objEntry.isSolvedChanged());//schliesslich wird hier nix gesolved()!!!
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			
			sFormulaSolvedAndConverted = objEntry.getValueFormulaSolvedAndConverted();
			assertNull("NULL erwartet. Wert ist aber '" + sFormulaSolvedAndConverted + "'", sFormulaSolvedAndConverted); //Da keine Formel enthalten ist
					
			sFormulaSolvedAndConvertedAsExpression = objEntry.getValueFormulaSolvedAndConvertedAsExpression();					
			assertEquals(XmlUtilZZZ.computeTagNull(), sFormulaSolvedAndConvertedAsExpression);//Da keine Formel enthalten ist.

			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++

			
			//+++ Auf Werte kann man hier eigentlich nicht so abfragen, weil ggfs. doch CRYPTED Werte in der Expression sind					
			assertFalse(objEntry.isDecrypted());
			assertNull(objEntry.getValueDecrypted()); //Merke: sValue kann unterschiedlich zu dem decrypted Wert sein. Wenn etwas drumherum steht.
			
			//+++ Kann hier eigentlich nicht abgefragt werden, weil ggfs. eine Expression ohne CALL-Ausdruck vorliegt
			//assertFalse(objEntry.isCall());
			//assertFalse(objEntry.isJavaCall());
			//assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingClassname() + "'", objEntry.getCallingClassname());
			//assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingMethodname() + "'", objEntry.getCallingMethodname());
			
			//Wert mit speziellen Entry-Formelwert vergleichen
			sFormulaSolvedAndConverted = objEntry.getValueFormulaSolvedAndConverted();
			assertNull("NULL erwartet. Wert ist aber '" + sFormulaSolvedAndConverted + "'", sFormulaSolvedAndConverted); //Da keine Formel enthalten ist
						
			sFormulaSolvedAndConvertedAsExpression = objEntry.getValueFormulaSolvedAndConvertedAsExpression();					
			assertEquals(XmlUtilZZZ.computeTagNull(), sFormulaSolvedAndConvertedAsExpression);//Da keine Formel enthalten ist.

			bReturn = true;
		}//end main:
		return bReturn;
	}
	
	private static boolean assertFileIniEntry_solve_FLAGSET_UNSOLVED_(IEnumSetMappedTestSurroundingZZZ objEnumSurrounding, IKernelConfigSectionEntryZZZ objEntry, String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, boolean bAsEntry) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			String sExpression = sExpressionIn;
			String sExpressionSubstituted = sExpressionSubstitutedIn;
			String sExpressionSolved = sExpressionSolvedIn;
			
			String sTagStartZ = "<Z>";
			String sTagEndZ = "</Z>";	
			
			String sFormulaSolvedAndConverted=null; String sFormulaSolvedAndConvertedAsExpression=null;
			String sExpressionSubstituted2compareWithSolved=null; String sExpressionSubstituted2compareWithExpression = null; 
			String sExpressionParsed2compareWithSubstituted=null; String sExpressionSolved2compareWithSubstituted=null;
			String sExpression2compareWithSubstituted=null; String sExpression2compareWithSolved = null;
			String sSubstituted2compare=null;
				
			boolean bCallSolverCalledPrevious=false;
		
		
			//######## TEST-TEIL ###########################
			assertTrue(objEntry.isExpression()); //ohne Expression-Nutzung kein Expression Eintrag!!!
			
			
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++
			//Parser Ergebnisse
			assertTrue(objEntry.isParseCalled()); //Auch wenn die Expression nicht verarbeitet wird, dann ist doch geparsed worden....									
			assertTrue(objEntry.isParsed()); //Auch ohne Solver wird geparsed
			
			//++++++++++++++++++++++++++++++++
			assertTrue(objEntry.isPathSubstituteCalled());		
			sExpression2compareWithSubstituted = sExpression;
			if(objEnumSurrounding == EnumSetMappedTestSurroundingTypeZZZ.SOLVE_REMOVE && objEntry.isSolved()) {
				sExpression2compareWithSubstituted = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpression2compareWithSubstituted, sTagStartZ, sTagEndZ, false);
			}
			System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpression2compareWithSubstituted=" + sExpression2compareWithSubstituted);
			System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpressionSubstituted=" + sExpressionSubstituted);					
			if(objEntry.isPathSubstituted()){ //Kann hier eigentlich nicht getestet werden. Ggfs. wird eine Expression ohne INI-PATH uebergeben
				if(sExpression2compareWithSubstituted.equals(sExpressionSubstituted)) { 
					assertFalse(objEntry.isPathSubstitutedChanged());
				}else {
					assertTrue(objEntry.isPathSubstitutedChanged());
				}
			}
			assertTrue(objEntry.isPathSubstituted()); //falls das entsprechende Flag gesetzt ist, unabhaengig davon, ob ein INI-PATH Ausdruck darin ist
			//++++++++++++++++++++++++++++++++
			//++++++++++++++++++++++++++++++++
			assertTrue(objEntry.isVariableSubstituteCalled());
			//Auf Werte kann man hier eigentlich nicht so abfragen, weil ggfs. keine Variablen in der Expression sind.
			assertTrue(objEntry.isVariableSubstituted()); //falls das entsprechende Flag gesetzt ist, unabhaengig davon, ob eine INI-Variable darin ist
			
			
			//+++++++++++++++++++++++++++++++++++++++++++++
			//Keine Solver Ergebnisse
			assertTrue(objEntry.isSolveCalled());  //aufgerufen wurde der Solver ja...
			assertFalse(objEntry.isSolved()); //sollte ohne SOLVE abgebrochen worden sein
			assertFalse(objEntry.isSolvedChanged());//schliesslich wird hier nix gesolved()!!!
			
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			
			sFormulaSolvedAndConverted = objEntry.getValueFormulaSolvedAndConverted();
			assertNull("NULL erwartet. Wert ist aber '" + sFormulaSolvedAndConverted + "'", sFormulaSolvedAndConverted); //Da keine Formel enthalten ist
					
			sFormulaSolvedAndConvertedAsExpression = objEntry.getValueFormulaSolvedAndConvertedAsExpression();					
			assertEquals(XmlUtilZZZ.computeTagNull(), sFormulaSolvedAndConvertedAsExpression);//Da keine Formel enthalten ist.

			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++

			
			//+++ Auf Werte kann man hier eigentlich nicht so abfragen, weil ggfs. doch CRYPTED Werte in der Expression sind					
			assertFalse(objEntry.isDecrypted());
			assertNull(objEntry.getValueDecrypted()); //Merke: sValue kann unterschiedlich zu dem decrypted Wert sein. Wenn etwas drumherum steht.
			
			//+++ Kann hier eigentlich nicht abgefragt werden, weil ggfs. eine Expression ohne CALL-Ausdruck vorliegt
			//assertFalse(objEntry.isCall());
			//assertFalse(objEntry.isJavaCall());
			//assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingClassname() + "'", objEntry.getCallingClassname());
			//assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingMethodname() + "'", objEntry.getCallingMethodname());
			
			//Wert mit speziellen Entry-Formelwert vergleichen
			sFormulaSolvedAndConverted = objEntry.getValueFormulaSolvedAndConverted();
			assertNull("NULL erwartet. Wert ist aber '" + sFormulaSolvedAndConverted + "'", sFormulaSolvedAndConverted); //Da keine Formel enthalten ist
						
			sFormulaSolvedAndConvertedAsExpression = objEntry.getValueFormulaSolvedAndConvertedAsExpression();					
			assertEquals(XmlUtilZZZ.computeTagNull(), sFormulaSolvedAndConvertedAsExpression);//Da keine Formel enthalten ist.

			bReturn = true;
		}//end main:
		return bReturn;
	}

	
	private static boolean assertFileIniEntry_solve_FLAGSET_UNPARSED_UNSOLVED_(IEnumSetMappedTestSurroundingZZZ objEnumSurrounding, IKernelConfigSectionEntryZZZ objEntry, String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, boolean bAsEntry) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			String sExpression = sExpressionIn;
			String sExpressionSubstituted = sExpressionSubstitutedIn;
			String sExpressionSolved = sExpressionSolvedIn;
			
			String sTagStartZ = "<Z>";
			String sTagEndZ = "</Z>";	
			
			String sFormulaSolvedAndConverted=null; String sFormulaSolvedAndConvertedAsExpression=null;
			String sExpressionSubstituted2compareWithSolved=null; String sExpressionSubstituted2compareWithExpression = null; 
			String sExpressionParsed2compareWithSubstituted=null; String sExpressionSolved2compareWithSubstituted=null;
			String sExpression2compareWithSubstituted=null; String sExpression2compareWithSolved = null;
			String sSubstituted2compare=null;
				
			boolean bCallSolverCalledPrevious=false;
		
		
			//######## TEST-TEIL ###########################
			//wie unsolved, aber hier gibt es auch keine Parser - Ergebnisse
			assertTrue(objEntry.isExpression()); //ohne Expression-Nutzung kein Expression Eintrag!!!
								
			//+++++++++++++++++++++++++++++++++++++++
			//Keine Parser Ergebnisse (Abfrage der asserts in umgekehrter Reihenfolge ihres moeglichen Wertesetzens im Code)
			assertTrue(objEntry.isParseCalled()); //Auch wenn die Expression nicht verarbeitet wird, dann ist doch geparsed worden....
			assertFalse(objEntry.isParsed());     //Nun wird weder geparsed noch gesolved.
			
			//+++++++++++++++++++++++++++++++++++++++
			assertFalse(objEntry.isPathSubstituteCalled());				
			sExpression2compareWithSubstituted = sExpression;
			if(objEnumSurrounding == EnumSetMappedTestSurroundingTypeZZZ.SOLVE_REMOVE && objEntry.isSolved()) {
				sExpression2compareWithSubstituted = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpression2compareWithSubstituted, sTagStartZ, sTagEndZ, false);
			}
			System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpression2compareWithSubstituted=" + sExpression2compareWithSubstituted);
			System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpressionSubstituted=" + sExpressionSubstituted);					
			if(objEntry.isPathSubstituted()){ //Kann hier eigentlich nicht getestet werden. Ggfs. wird eine Expression ohne INI-PATH uebergeben
				if(sExpression2compareWithSubstituted.equals(sExpressionSubstituted)) { 
					assertFalse(objEntry.isPathSubstitutedChanged());
				}else {
					assertTrue(objEntry.isPathSubstitutedChanged());
				}
			}
			assertFalse(objEntry.isPathSubstituted()); //falls das entsprechende Flag gesetzt ist, unabhaengig davon, ob ein INI-PATH Ausdruck darin ist				
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			assertFalse(objEntry.isVariableSubstituteCalled());
			assertFalse(objEntry.isVariableSubstituted()); //falls das entsprechende Flag gesetzt ist, unabhaengig davon, ob eine INI-Variable darin ist
			//+++ Auf Werte kann man hier eigentlich nicht so abfragen, weil ggfs. keine Variablen in der Expression sind.
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			
			
			//++++++++++++++++++++++++++++++++++++++++++
			//Keine Solver Ergebnisse
			assertTrue(objEntry.isSolveCalled()); //aufgerufen wurde der Solver ja
			assertFalse(objEntry.isSolved()); //sollte ohne SOLVE abgebrochen worden sein
			assertFalse(objEntry.isSolvedChanged());//schliesslich wird hier nix gesolved()!!!
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			
			sFormulaSolvedAndConverted = objEntry.getValueFormulaSolvedAndConverted();
			assertNull("NULL erwartet. Wert ist aber '" + sFormulaSolvedAndConverted + "'", sFormulaSolvedAndConverted); //Da keine Formel enthalten ist
					
			sFormulaSolvedAndConvertedAsExpression = objEntry.getValueFormulaSolvedAndConvertedAsExpression();					
			assertEquals(XmlUtilZZZ.computeTagNull(), sFormulaSolvedAndConvertedAsExpression);//Da keine Formel enthalten ist.

			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++

			
			//+++ Auf Werte kann man hier eigentlich nicht so abfragen, weil ggfs. doch CRYPTED Werte in der Expression sind					
			assertFalse(objEntry.isDecrypted());
			assertNull(objEntry.getValueDecrypted()); //Merke: sValue kann unterschiedlich zu dem decrypted Wert sein. Wenn etwas drumherum steht.
			
			//+++ Kann hier eigentlich nicht abgefragt werden, weil ggfs. eine Expression ohne CALL-Ausdruck vorliegt
			//assertFalse(objEntry.isCall());
			//assertFalse(objEntry.isJavaCall());
			//assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingClassname() + "'", objEntry.getCallingClassname());
			//assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingMethodname() + "'", objEntry.getCallingMethodname());
			
			//Wert mit speziellen Entry-Formelwert vergleichen
			sFormulaSolvedAndConverted = objEntry.getValueFormulaSolvedAndConverted();
			assertNull("NULL erwartet. Wert ist aber '" + sFormulaSolvedAndConverted + "'", sFormulaSolvedAndConverted); //Da keine Formel enthalten ist
						
			sFormulaSolvedAndConvertedAsExpression = objEntry.getValueFormulaSolvedAndConvertedAsExpression();					
			assertEquals(XmlUtilZZZ.computeTagNull(), sFormulaSolvedAndConvertedAsExpression);//Da keine Formel enthalten ist.

			bReturn = true;
		}//end main:
		return bReturn;
	}


	private static boolean assertFileIniEntry_solve_FLAGSET_MATH_UNSOLVED_(IEnumSetMappedTestSurroundingZZZ objEnumSurrounding, IKernelConfigSectionEntryZZZ objEntry, String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, boolean bAsEntry) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			String sExpression = sExpressionIn;
			String sExpressionSubstituted = sExpressionSubstitutedIn;
			String sExpressionSolved = sExpressionSolvedIn;
			
			String sTagStartZ = "<Z>";
			String sTagEndZ = "</Z>";	
			
			String sFormulaSolvedAndConverted=null; String sFormulaSolvedAndConvertedAsExpression=null;
			String sExpressionSubstituted2compareWithSolved=null; String sExpressionSubstituted2compareWithExpression = null; 
			String sExpressionParsed2compareWithSubstituted=null; String sExpressionSolved2compareWithSubstituted=null;
			String sExpression2compareWithSubstituted=null; String sExpression2compareWithSolved = null;
			String sSubstituted2compare=null;
				
			boolean bCallSolverCalledPrevious=false;
		
		
			//######## TEST-TEIL ###########################
			assertTrue(objEntry.isExpression()); //ohne Expression-Nutzung kein Expression Eintrag!!!
			
			//++++++++++++++++++++++++++++++++
			assertTrue(objEntry.isParseCalled()); //Auch wenn die Expression nicht verarbeitet wird, dann ist doch geparsed worden....
			//Merke: .isParsedChange() laesst sich hier nicht ermitteln.
			assertTrue(objEntry.isParsed()); 
			//+++++++++++++++++++++++++++++++				
			
			//+++++++++++++++++++++++++++++++									
			assertTrue(objEntry.isPathSubstituteCalled());																
			sExpression2compareWithSubstituted = sExpression;
			if(objEnumSurrounding == EnumSetMappedTestSurroundingTypeZZZ.SOLVE_REMOVE && objEntry.isSolved()) {
				sExpression2compareWithSubstituted = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpression2compareWithSubstituted, sTagStartZ, sTagEndZ, false);
			}
			System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpression2compareWithSubstituted=" + sExpression2compareWithSubstituted);
			System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpressionSubstituted=" + sExpressionSubstituted);					
			if(objEntry.isPathSubstituted()){ //Kann hier eigentlich nicht getestet werden. Ggfs. wird eine Expression ohne INI-PATH uebergeben
				if(sExpression2compareWithSubstituted.equals(sExpressionSubstituted)) { 
					assertFalse(objEntry.isPathSubstitutedChanged());
				}else {
					assertTrue(objEntry.isPathSubstitutedChanged());
				}
			}							
			assertTrue(objEntry.isPathSubstituted());	
			
			//+++++++++++++++++++++++++++++++
			assertTrue(objEntry.isSolveCalled());
			sExpressionSubstituted2compareWithSolved = sExpressionSubstituted;				
			sExpressionSubstituted2compareWithSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSubstituted2compareWithSolved, sTagStartZ, sTagEndZ);				
			sExpressionSolved2compareWithSubstituted = sExpressionSolved;
			sExpressionSolved2compareWithSubstituted = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved2compareWithSubstituted, sTagStartZ, sTagEndZ);				
			System.out.println("sExpressionSolved2compareWithSubstituted="+sExpressionSolved2compareWithSubstituted);
			System.out.println("sExpressionSubstituted2compareWithSolved="+sExpressionSubstituted2compareWithSolved);
			if(sExpressionSolved2compareWithSubstituted.equals(sExpressionSubstituted2compareWithSolved)) {
				assertFalse(objEntry.isSolvedChanged()); 
			}else {					
				assertTrue(objEntry.isSolvedChanged());
			}		
			assertTrue(objEntry.isSolved()); //sollte auch SOLVE_MATH wird solve ausgefuehrt
			//++++++++++++++++++++++				
			//++++++++++++++++++++++									
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			
			sFormulaSolvedAndConverted = objEntry.getValueFormulaSolvedAndConverted();
			assertNull("NULL erwartet. Wert ist aber '" + sFormulaSolvedAndConverted + "'", sFormulaSolvedAndConverted); //Da keine Formel enthalten ist
					
			sFormulaSolvedAndConvertedAsExpression = objEntry.getValueFormulaSolvedAndConvertedAsExpression();					
			assertEquals(XmlUtilZZZ.computeTagNull(), sFormulaSolvedAndConvertedAsExpression);//Da keine Formel enthalten ist.

			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++

			
			assertFalse(objEntry.isDecrypted());
			assertNull(objEntry.getValueDecrypted()); //Merke: sValue kann unterschiedlich zu dem decrypted Wert sein. Wenn etwas drumherum steht.
			
			assertFalse(objEntry.isCall());
			assertFalse(objEntry.isJavaCall());
			assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingClassname() + "'", objEntry.getCallingClassname());
			assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingMethodname() + "'", objEntry.getCallingMethodname());	

			bReturn = true;
		}//end main:
		return bReturn;
	}

	private static boolean assertFileIniEntry_solve_FLAGSET_SOLVED_(IEnumSetMappedTestSurroundingZZZ objEnumSurrounding, IKernelConfigSectionEntryZZZ objEntry, String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, boolean bAsEntry) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			String sExpression = sExpressionIn;
			String sExpressionSubstituted = sExpressionSubstitutedIn;
			String sExpressionSolved = sExpressionSolvedIn;
			
			String sTagStartZ = "<Z>";
			String sTagEndZ = "</Z>";	
			
			String sFormulaSolvedAndConverted=null; String sFormulaSolvedAndConvertedAsExpression=null;
			String sExpressionSubstituted2compareWithSolved=null; String sExpressionSubstituted2compareWithExpression = null; 
			String sExpressionParsed2compareWithSubstituted=null; String sExpressionSolved2compareWithSubstituted=null;
			String sExpression2compareWithSubstituted=null; String sExpression2compareWithSolved = null;
			String sSubstituted2compare=null;
				
			boolean bCallSolverCalledPrevious=false;
		
		
			//######## TEST-TEIL ###########################
			assertTrue(objEntry.isExpression()); //ohne Expression-Nutzung kein Expression Eintrag!!!
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			assertTrue(objEntry.isParseCalled()); //Auch wenn die Expression nicht verarbeitet wird, dann ist doch geparsed worden....
			//Beim Solven kann nicht auf die Änderung vom Parsen geprüft werden, 
			//da wir das Ergebnis des Parsens nicht haben.
			//Merke: .isParsedChanged() laesst sich hier nicht ermitteln.
			assertTrue(objEntry.isParsed());
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			assertTrue(objEntry.isPathSubstituteCalled());																
			sExpression2compareWithSubstituted = sExpression;
			if(objEnumSurrounding == EnumSetMappedTestSurroundingTypeZZZ.SOLVE_REMOVE && objEntry.isSolved()) {
				sExpression2compareWithSubstituted = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpression2compareWithSubstituted, sTagStartZ, sTagEndZ, false);
			}
			System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpression2compareWithSubstituted=" + sExpression2compareWithSubstituted);
			System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpressionSubstituted=" + sExpressionSubstituted);					
			if(objEntry.isPathSubstituted()){ //Kann hier eigentlich nicht getestet werden. Ggfs. wird eine Expression ohne INI-PATH uebergeben
				if(sExpression2compareWithSubstituted.equals(sExpressionSubstituted)) { 
					assertFalse(objEntry.isPathSubstitutedChanged());
				}else {
					assertTrue(objEntry.isPathSubstitutedChanged());
				}
			}								
			assertTrue(objEntry.isPathSubstituted());
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			
			
			//+++Varaiablen Substitution waere an +++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			assertTrue(objEntry.isVariableSubstituteCalled());
																//+++ kann man hier doch auch eigentlich nicht so abfragen														
			assertTrue(objEntry.isVariableSubstituted());
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			assertTrue(objEntry.isSolveCalled()); //Aufgerufen wurde der solveCall ja...												
			sExpressionSubstituted2compareWithSolved = sExpressionSubstituted;				
			sExpressionSubstituted2compareWithSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSubstituted2compareWithSolved, sTagStartZ, sTagEndZ);				
			sExpressionSolved2compareWithSubstituted = sExpressionSolved;
			sExpressionSolved2compareWithSubstituted = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved2compareWithSubstituted, sTagStartZ, sTagEndZ);				
			System.out.println("sExpressionSolved2compareWithSubstituted="+sExpressionSolved2compareWithSubstituted);
			System.out.println("sExpressionSubstituted2compareWithSolved="+sExpressionSubstituted2compareWithSolved);
			if(sExpressionSolved2compareWithSubstituted.equals(sExpressionSubstituted2compareWithSolved)) {
				assertFalse(objEntry.isSolvedChanged()); 
			}else {					
				assertTrue(objEntry.isSolvedChanged());
			}			
			assertTrue(objEntry.isSolved());
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			
			sFormulaSolvedAndConverted = objEntry.getValueFormulaSolvedAndConverted();
			assertNull("NULL erwartet. Wert ist aber '" + sFormulaSolvedAndConverted + "'", sFormulaSolvedAndConverted); //Da keine Formel enthalten ist
					
			sFormulaSolvedAndConvertedAsExpression = objEntry.getValueFormulaSolvedAndConvertedAsExpression();					
			assertEquals(XmlUtilZZZ.computeTagNull(), sFormulaSolvedAndConvertedAsExpression);//Da keine Formel enthalten ist.

			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++

			
			assertFalse(objEntry.isDecrypted());
			assertNull(objEntry.getValueDecrypted()); //Merke: sValue kann unterschiedlich zu dem decrypted Wert sein. Wenn etwas drumherum steht.

			bReturn = true;
		}//end main:
		return bReturn;
	}


	private static boolean assertFileIniEntry_solve_FLAGSET_PATH_UNSUBSTITUTED_(IEnumSetMappedTestSurroundingZZZ objEnumSurrounding, IKernelConfigSectionEntryZZZ objEntry, String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, boolean bAsEntry) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			String sExpression = sExpressionIn;
			String sExpressionSubstituted = sExpressionSubstitutedIn;
			String sExpressionSolved = sExpressionSolvedIn;
			
			String sTagStartZ = "<Z>";
			String sTagEndZ = "</Z>";	
			
			String sFormulaSolvedAndConverted=null; String sFormulaSolvedAndConvertedAsExpression=null;
			String sExpressionSubstituted2compareWithSolved=null; String sExpressionSubstituted2compareWithExpression = null; 
			String sExpressionParsed2compareWithSubstituted=null; String sExpressionSolved2compareWithSubstituted=null;
			String sExpression2compareWithSubstituted=null; String sExpression2compareWithSolved = null;
			String sSubstituted2compare=null;
				
			boolean bCallSolverCalledPrevious=false;
		
		
			//######## TEST-TEIL ###########################
			assertTrue(objEntry.isExpression()); //ohne Expression-Nutzung kein Expression Eintrag!!!
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			assertTrue(objEntry.isParseCalled()); //Auch wenn die Expression nicht verarbeitet wird, dann ist doch geparsed worden....
			//Beim Solven kann nicht auf die Änderung vom Parsen geprüft werden, 
			//da wir das Ergebnis des Parsens nicht haben.
			//Merke: .isParsedChanged() laesst sich hier nicht ermitteln.
			assertTrue(objEntry.isParsed());
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			assertTrue(objEntry.isPathSubstituteCalled());																
			assertFalse(objEntry.isPathSubstitutedChanged());									
			assertFalse(objEntry.isPathSubstituted());
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			
			
			//+++Varaiablen Substitution waere an +++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			assertTrue(objEntry.isVariableSubstituteCalled());
																//+++ kann man hier doch auch eigentlich nicht so abfragen														
			assertTrue(objEntry.isVariableSubstituted());
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			assertTrue(objEntry.isSolveCalled()); //Aufgerufen wurde der solveCall ja...								
			sExpressionSubstituted2compareWithSolved = sExpressionSubstituted;				
			sExpressionSubstituted2compareWithSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSubstituted2compareWithSolved, sTagStartZ, sTagEndZ);				
			sExpressionSolved2compareWithSubstituted = sExpressionSolved;
			sExpressionSolved2compareWithSubstituted = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved2compareWithSubstituted, sTagStartZ, sTagEndZ);				
			System.out.println("sExpressionSolved2compareWithSubstituted="+sExpressionSolved2compareWithSubstituted);
			System.out.println("sExpressionSubstituted2compareWithSolved="+sExpressionSubstituted2compareWithSolved);
			if(sExpressionSolved2compareWithSubstituted.equals(sExpressionSubstituted2compareWithSolved)) {
				assertFalse(objEntry.isSolvedChanged()); 
			}else {					
				assertTrue(objEntry.isSolvedChanged());
			}					
			assertTrue(objEntry.isSolved());
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			
			sFormulaSolvedAndConverted = objEntry.getValueFormulaSolvedAndConverted();
			assertNull("NULL erwartet. Wert ist aber '" + sFormulaSolvedAndConverted + "'", sFormulaSolvedAndConverted); //Da keine Formel enthalten ist
					
			sFormulaSolvedAndConvertedAsExpression = objEntry.getValueFormulaSolvedAndConvertedAsExpression();					
			assertEquals(XmlUtilZZZ.computeTagNull(), sFormulaSolvedAndConvertedAsExpression);//Da keine Formel enthalten ist.

			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++

			
			assertFalse(objEntry.isDecrypted());
			assertNull(objEntry.getValueDecrypted()); //Merke: sValue kann unterschiedlich zu dem decrypted Wert sein. Wenn etwas drumherum steht.

			bReturn = true;
		}//end main:
		return bReturn;
	}


	private static boolean assertFileIniEntry_solve_FLAGSET_PATH_SUBSTITUTED_(IEnumSetMappedTestSurroundingZZZ objEnumSurrounding, IKernelConfigSectionEntryZZZ objEntry, String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, boolean bAsEntry) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			String sExpression = sExpressionIn;
			String sExpressionSubstituted = sExpressionSubstitutedIn;
			String sExpressionSolved = sExpressionSolvedIn;
			
			String sTagStartZ = "<Z>";
			String sTagEndZ = "</Z>";	
			
			String sFormulaSolvedAndConverted=null; String sFormulaSolvedAndConvertedAsExpression=null;
			String sExpressionSubstituted2compareWithSolved=null; String sExpressionSubstituted2compareWithExpression = null; 
			String sExpressionParsed2compareWithSubstituted=null; String sExpressionSolved2compareWithSubstituted=null;
			String sExpression2compareWithSubstituted=null; String sExpression2compareWithSolved = null;
			String sSubstituted2compare=null;
				
			boolean bCallSolverCalledPrevious=false;
		
		
			//######## TEST-TEIL ###########################
			assertTrue(objEntry.isExpression()); //ohne Expression-Nutzung kein Expression Eintrag!!!
			
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			assertTrue(objEntry.isParseCalled()); //Auch wenn die Expression nicht verarbeitet wird, dann ist doch geparsed worden....					
			//Beim Solven kann nicht auf die Änderung vom Parsen geprüft werden, 
			//da wir das Ergebnis des Parsens nicht haben.
			//Merke: .isParsedChanged() laesst sich hier nicht ermitteln.	
			assertTrue(objEntry.isParsed()); 
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			assertTrue(objEntry.isPathSubstituteCalled());																
			sExpression2compareWithSubstituted = sExpression;
			if(objEnumSurrounding == EnumSetMappedTestSurroundingTypeZZZ.SOLVE_REMOVE && objEntry.isSolved()) {
				sExpression2compareWithSubstituted = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpression2compareWithSubstituted, sTagStartZ, sTagEndZ, false);
			}
			System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpression2compareWithSubstituted=" + sExpression2compareWithSubstituted);
			System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpressionSubstituted=" + sExpressionSubstituted);					
			if(objEntry.isPathSubstituted()){ //Kann hier eigentlich nicht getestet werden. Ggfs. wird eine Expression ohne INI-PATH uebergeben
				if(sExpression2compareWithSubstituted.equals(sExpressionSubstituted)) { 
					assertFalse(objEntry.isPathSubstitutedChanged());
				}else {
					assertTrue(objEntry.isPathSubstitutedChanged());
				}
			}													
			assertFalse(objEntry.isPathSubstituted());
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			
			
			//+++++++++++++++++++++++++++++++++++++++++++
			assertTrue(objEntry.isSolveCalled()); //Aufgerufen wurde der solveCall ja...				
			sExpressionSubstituted2compareWithSolved = sExpressionSubstituted;				
			sExpressionSubstituted2compareWithSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSubstituted2compareWithSolved, sTagStartZ, sTagEndZ);				
			sExpressionSolved2compareWithSubstituted = sExpressionSolved;
			sExpressionSolved2compareWithSubstituted = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved2compareWithSubstituted, sTagStartZ, sTagEndZ);				
			System.out.println("sExpressionSolved2compareWithSubstituted="+sExpressionSolved2compareWithSubstituted);
			System.out.println("sExpressionSubstituted2compareWithSolved="+sExpressionSubstituted2compareWithSolved);
			if(sExpressionSolved2compareWithSubstituted.equals(sExpressionSubstituted2compareWithSolved)) {
				assertFalse(objEntry.isSolvedChanged()); 
			}else {					
				assertTrue(objEntry.isSolvedChanged());
			}
			assertTrue(objEntry.isSolved());
			//+++++++++++++++++++++++++++++++++++++++++++


			//+++ kann man hier doch auch eigentlich nicht so abfragen					
			assertFalse(objEntry.isVariableSubstituted());
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			
			sFormulaSolvedAndConverted = objEntry.getValueFormulaSolvedAndConverted();
			assertNull("NULL erwartet. Wert ist aber '" + sFormulaSolvedAndConverted + "'", sFormulaSolvedAndConverted); //Da keine Formel enthalten ist
					
			sFormulaSolvedAndConvertedAsExpression = objEntry.getValueFormulaSolvedAndConvertedAsExpression();					
			assertEquals(XmlUtilZZZ.computeTagNull(), sFormulaSolvedAndConvertedAsExpression);//Da keine Formel enthalten ist.

			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++

			
			assertFalse(objEntry.isDecrypted());
			assertNull(objEntry.getValueDecrypted()); //Merke: sValue kann unterschiedlich zu dem decrypted Wert sein. Wenn etwas drumherum steht.

			bReturn = true;
		}//end main:
		return bReturn;
	}

	private static boolean assertFileIniEntry_solve_FLAGSET_CALL_UNSOLVED_(IEnumSetMappedTestSurroundingZZZ objEnumSurrounding, IKernelConfigSectionEntryZZZ objEntry, String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, boolean bAsEntry) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			String sExpression = sExpressionIn;
			String sExpressionSubstituted = sExpressionSubstitutedIn;
			String sExpressionSolved = sExpressionSolvedIn;
			
			String sTagStartZ = "<Z>";
			String sTagEndZ = "</Z>";	
			
			String sFormulaSolvedAndConverted=null; String sFormulaSolvedAndConvertedAsExpression=null;
			String sExpressionSubstituted2compareWithSolved=null; String sExpressionSubstituted2compareWithExpression = null; 
			String sExpressionParsed2compareWithSubstituted=null; String sExpressionSolved2compareWithSubstituted=null;
			String sExpression2compareWithSubstituted=null; String sExpression2compareWithSolved = null;
			String sSubstituted2compare=null;
				
			boolean bCallSolverCalledPrevious=false;
		
		
			//######## TEST-TEIL ###########################
			assertTrue(objEntry.isExpression()); //ohne Expression-Nutzung kein Expression Eintrag!!!
			
			//++++++++++++++++++++++ Weil nicht gesolved wurde, kann auf das Parse Ergebnis zugegriffen werden
			assertTrue(objEntry.isParseCalled()); //Auch wenn die Expression nicht verarbeitet wird, dann ist doch geparsed worden....
			sExpression2compareWithSubstituted = sExpression;
			if(objEnumSurrounding == EnumSetMappedTestSurroundingTypeZZZ.SOLVE_REMOVE && objEntry.isSolved()) {
				sExpression2compareWithSubstituted = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpression2compareWithSubstituted, sTagStartZ, sTagEndZ, false);
			}
			System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpression2compareWithSubsituted=" + sExpression2compareWithSubstituted);
			System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpressionSubstituted=" + sExpressionSubstituted);
			if(sExpression2compareWithSubstituted.equals(sExpressionSubstituted)) {
				assertFalse(objEntry.isParsedChanged());						
			}else {
				assertTrue(objEntry.isParsedChanged());
			}
			assertTrue(objEntry.isParsed());
								
			//+++++++++++++++++++++++ ++++++++++++++++++++++ Weil nicht gesolved wurde, kann auf das Parse Ergebnis zugegriffen werden
			assertTrue(objEntry.isPathSubstituteCalled());
			sExpression2compareWithSubstituted = sExpression;
			if(objEnumSurrounding == EnumSetMappedTestSurroundingTypeZZZ.SOLVE_REMOVE && objEntry.isSolved()) {
				sExpression2compareWithSubstituted = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpression2compareWithSubstituted, sTagStartZ, sTagEndZ, false);
			}
			System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpression2compareWithSubstituted=" + sExpression2compareWithSubstituted);
			System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpressionSubstituted=" + sExpressionSubstituted);					
			if(objEntry.isPathSubstituted()){ //Kann hier eigentlich nicht getestet werden. Ggfs. wird eine Expression ohne INI-PATH uebergeben
				if(sExpression2compareWithSubstituted.equals(sExpressionSubstituted)) { 
					assertFalse(objEntry.isSubstitutedChanged());
				}else {
					assertTrue(objEntry.isSubstitutedChanged());
				}
			}
			assertTrue(objEntry.isPathSubstituted()); //falls das entsprechende Flag gesetzt ist, unabhaengig davon, ob ein INI-PATH Ausdruck darin ist
		
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++				
			//+++Variablen Substitution waere an +++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			assertTrue(objEntry.isVariableSubstituteCalled());
																//+++ kann man hier doch auch eigentlich nicht so abfragen														
			assertTrue(objEntry.isVariableSubstituted());
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++				
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			assertTrue(objEntry.isSolveCalled()); //Aufgerufen wurde der solveCall ja...

			sExpressionSubstituted2compareWithSolved = sExpressionSubstituted;				
			sExpressionSubstituted2compareWithSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSubstituted2compareWithSolved, sTagStartZ, sTagEndZ);				
			sExpressionSolved2compareWithSubstituted = sExpressionSolved;
			sExpressionSolved2compareWithSubstituted = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved2compareWithSubstituted, sTagStartZ, sTagEndZ);				
			System.out.println("sExpressionSolved2compareWithSubstituted="+sExpressionSolved2compareWithSubstituted);
			System.out.println("sExpressionSubstituted2compareWithSolved="+sExpressionSubstituted2compareWithSolved);
			if(sExpressionSolved2compareWithSubstituted.equals(sExpressionSubstituted2compareWithSolved)) {
				assertFalse(objEntry.isSolvedChanged()); 
			}else {					
				assertTrue(objEntry.isSolvedChanged());
			}
			
			assertTrue(objEntry.isSolved());
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			
			sFormulaSolvedAndConverted = objEntry.getValueFormulaSolvedAndConverted();
			assertNull("NULL erwartet. Wert ist aber '" + sFormulaSolvedAndConverted + "'", sFormulaSolvedAndConverted); //Da keine Formel enthalten ist
					
			sFormulaSolvedAndConvertedAsExpression = objEntry.getValueFormulaSolvedAndConvertedAsExpression();					
			assertEquals(XmlUtilZZZ.computeTagNull(), sFormulaSolvedAndConvertedAsExpression);//Da keine Formel enthalten ist.

			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++

			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++				
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//Frueher: Ist der JavaCall-SOLVER deaktiviert, wird nun nicht der CallSolver aufgerufen, also das Kennzeichen nicht gesetzt
			//Jetzt 20250311: Durch die zu ueberschreibende Methode aus ISolveUserZZZ .updateValueSolveCalled(...) werden alle relevanten Kennzeichen gesetzt
			//Generische Problematik. Stichwort "Elterntag" TODOGOON20250308; TICKET20250308; Diese Testutility wird auch von KernelJavaCallIniSolverZZZTest aufgerufen.

			//DEFAULT: Wenn der Solver generel ausgestellt wird, dann wird nix dahinter aufgerufen.
			//ABER:    Call und JavaCallSolver ueberschreiben die Methode isParserenabledThis.      
			assertTrue(objEntry.isCall());		//Beim Parsen wird das festgestellt
			
			//Hier differenzieren, ob der Aufruf direkt erfolgte oder schon der Solver des "Elterntags" aufgerufen worden ist......
			bCallSolverCalledPrevious = VectorUtilZZZ.containsString((Vector) objEntry.getHistorySolveCalledVector(), KernelCallIniSolverZZZ.sTAG_NAME);
			if(bCallSolverCalledPrevious) {
				System.out.println(("Vorher wurde Call-Solver aufgerufen. Also Java-Call-Solver nicht direkt aufgerufen."));
				assertFalse(objEntry.isJavaCallSolveCalled()); //Der Aufruf wird vom CALL-Handler vermieden, da durch Flag deaktiviert.
			}else {
				//ABER: Bei einem "entry"-Aufruf wird auch der JavaCall-Solver nicht direkt aufgerufen.
				if(bAsEntry) {
					System.out.println(("Aufruf als Entry. Also kann JavaCall-Solver nicht direkt aufgerufen worden sein."));
					assertFalse(objEntry.isJavaCallSolveCalled()); //Der Aufruf wird vom CALL-Handler vermieden, da durch Flag deaktiviert.
				}else {
					System.out.println(("Vorher wurde Call-Solver NOCH NICHT aufgerufen. Also JavaCall-Solver direkt aufgerufen."));
					assertTrue(objEntry.isJavaCallSolveCalled());  //aber wenn der JavaCallSolver direkt aufgerufen wurde. Wird der Aufruf nicht vermieden....
				}
			}
			
			assertFalse(objEntry.isCallSolved());					

			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			assertFalse(objEntry.isJavaCall());            ///Beim Parsen wird das festgestellt
			
			assertFalse(objEntry.isJavaCallSolveCalled()); //OHNE Call-Solver Aufruf, keinen JavaCallSolver aufruf.
			assertFalse(objEntry.isJavaCallSolved());      //Der konkrete JAVACALL-Solver ist duch Flags deaktiviert, er wird zwar aufgerufen, aber nicht ausgefuehrt
			assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingClassname() + "'", objEntry.getCallingClassname());
			assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingMethodname() + "'", objEntry.getCallingMethodname());
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
							
			assertFalse(objEntry.isDecrypted());
			assertNull(objEntry.getValueDecrypted()); //Merke: sValue kann unterschiedlich zu dem decrypted Wert sein. Wenn etwas drumherum steht.

			bReturn = true;
		}//end main:
		return bReturn;
	}

	private static boolean assertFileIniEntry_solve_FLAGSET_CALL_UNSOLVED_JAVACALL_SOLVED_(IEnumSetMappedTestSurroundingZZZ objEnumSurrounding, IKernelConfigSectionEntryZZZ objEntry, String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, boolean bAsEntry) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			String sExpression = sExpressionIn;
			String sExpressionSubstituted = sExpressionSubstitutedIn;
			String sExpressionSolved = sExpressionSolvedIn;
			
			String sTagStartZ = "<Z>";
			String sTagEndZ = "</Z>";	
			
			String sFormulaSolvedAndConverted=null; String sFormulaSolvedAndConvertedAsExpression=null;
			String sExpressionSubstituted2compareWithSolved=null; String sExpressionSubstituted2compareWithExpression = null; 
			String sExpressionParsed2compareWithSubstituted=null; String sExpressionSolved2compareWithSubstituted=null;
			String sExpression2compareWithSubstituted=null; String sExpression2compareWithSolved = null;
			String sSubstituted2compare=null;
				
			boolean bCallSolverCalledPrevious=false;
		
		
			//######## TEST-TEIL ###########################
			assertTrue(objEntry.isExpression()); //ohne Expression-Nutzung kein Expression Eintrag!!!
			
			//++++++++++++++++++++++ Weil nicht gesolved wurde, kann auf das Parse Ergebnis zugegriffen werden
			assertTrue(objEntry.isParseCalled()); //Auch wenn die Expression nicht verarbeitet wird, dann ist doch geparsed worden....
			sExpression2compareWithSubstituted = sExpression;
			if(objEnumSurrounding == EnumSetMappedTestSurroundingTypeZZZ.SOLVE_REMOVE && objEntry.isSolved()) {
				sExpression2compareWithSubstituted = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpression2compareWithSubstituted, sTagStartZ, sTagEndZ, false);
			}
			System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpression2compareWithSubsituted=" + sExpression2compareWithSubstituted);
			System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpressionSubstituted=" + sExpressionSubstituted);
			if(sExpression2compareWithSubstituted.equals(sExpressionSubstituted)) {
				assertFalse(objEntry.isParsedChanged());						
			}else {
				assertTrue(objEntry.isParsedChanged());
			}
			assertTrue(objEntry.isParsed());
								
			//+++++++++++++++++++++++ ++++++++++++++++++++++ Weil nicht gesolved wurde, kann auf das Parse Ergebnis zugegriffen werden
			assertTrue(objEntry.isPathSubstituteCalled());
			sExpression2compareWithSubstituted = sExpression;
			if(objEnumSurrounding == EnumSetMappedTestSurroundingTypeZZZ.SOLVE_REMOVE && objEntry.isSolved()) {
				sExpression2compareWithSubstituted = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpression2compareWithSubstituted, sTagStartZ, sTagEndZ, false);
			}
			System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpression2compareWithSubstituted=" + sExpression2compareWithSubstituted);
			System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpressionSubstituted=" + sExpressionSubstituted);					
			if(objEntry.isPathSubstituted()){ //Kann hier eigentlich nicht getestet werden. Ggfs. wird eine Expression ohne INI-PATH uebergeben
				if(sExpression2compareWithSubstituted.equals(sExpressionSubstituted)) { 
					assertFalse(objEntry.isSubstitutedChanged());
				}else {
					assertTrue(objEntry.isSubstitutedChanged());
				}
			}
			assertTrue(objEntry.isPathSubstituted()); //falls das entsprechende Flag gesetzt ist, unabhaengig davon, ob ein INI-PATH Ausdruck darin ist
			
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++				
			//+++Variablen Substitution waere an +++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			assertTrue(objEntry.isVariableSubstituteCalled());
																//+++ kann man hier doch auch eigentlich nicht so abfragen														
			assertTrue(objEntry.isVariableSubstituted());
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++				
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			assertTrue(objEntry.isSolveCalled()); //Aufgerufen wurde der solveCall ja...

			sExpressionSubstituted2compareWithSolved = sExpressionSubstituted;				
			sExpressionSubstituted2compareWithSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSubstituted2compareWithSolved, sTagStartZ, sTagEndZ);				
			sExpressionSolved2compareWithSubstituted = sExpressionSolved;
			sExpressionSolved2compareWithSubstituted = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved2compareWithSubstituted, sTagStartZ, sTagEndZ);				
			System.out.println("sExpressionSolved2compareWithSubstituted="+sExpressionSolved2compareWithSubstituted);
			System.out.println("sExpressionSubstituted2compareWithSolved="+sExpressionSubstituted2compareWithSolved);
			if(sExpressionSolved2compareWithSubstituted.equals(sExpressionSubstituted2compareWithSolved)) {
				assertFalse(objEntry.isSolvedChanged()); 
			}else {					
				assertTrue(objEntry.isSolvedChanged());
			}
			
			assertTrue(objEntry.isSolved());
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			
			sFormulaSolvedAndConverted = objEntry.getValueFormulaSolvedAndConverted();
			assertNull("NULL erwartet. Wert ist aber '" + sFormulaSolvedAndConverted + "'", sFormulaSolvedAndConverted); //Da keine Formel enthalten ist
					
			sFormulaSolvedAndConvertedAsExpression = objEntry.getValueFormulaSolvedAndConvertedAsExpression();					
			assertEquals(XmlUtilZZZ.computeTagNull(), sFormulaSolvedAndConvertedAsExpression);//Da keine Formel enthalten ist.

			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++

			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++				
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//Frueher: Ist der JavaCall-SOLVER deaktiviert, wird nun nicht der CallSolver aufgerufen, also das Kennzeichen nicht gesetzt
			//Jetzt 20250311: Durch die zu ueberschreibende Methode aus ISolveUserZZZ .updateValueSolveCalled(...) werden alle relevanten Kennzeichen gesetzt
			//Generische Problematik. Stichwort "Elterntag" TODOGOON20250308; TICKET20250308; Diese Testutility wird auch von KernelJavaCallIniSolverZZZTest aufgerufen.

			//DEFAULT: Wenn der Solver generel ausgestellt wird, dann wird nix dahinter aufgerufen.
			//ABER:    Call und JavaCallSolver ueberschreiben die Methode isParserenabledThis.      
			assertTrue(objEntry.isCall());		//Beim Parsen wird das festgestellt
			
			//Hier differenzieren, ob der Aufruf direkt erfolgte oder schon der Solver des "Elterntags" aufgerufen worden ist......
			bCallSolverCalledPrevious = VectorUtilZZZ.containsString((Vector) objEntry.getHistorySolveCalledVector(), KernelCallIniSolverZZZ.sTAG_NAME);
			if(bCallSolverCalledPrevious) {
				System.out.println(("Vorher wurde Call-Solver aufgerufen. Also Java-Call-Solver nicht direkt aufgerufen."));
				assertFalse(objEntry.isJavaCallSolveCalled()); //Der Aufruf wird vom CALL-Handler vermieden, da durch Flag deaktiviert.
				assertFalse(objEntry.isCallSolved());
			}else {
							
				//ABER: Bei einem "entry"-Aufruf wird auch der JavaCall-Solver nicht direkt aufgerufen.
				if(bAsEntry) {
					System.out.println(("Aufruf als Entry. Also kann JavaCall-Solver nicht direkt aufgerufen worden sein."));
					assertTrue(objEntry.isJavaCallSolveCalled()); //Der Aufruf wird vom JAVACALL-Handler gemacht, wenn CALL-Handler nicht verwendet wurde
				}else {
					System.out.println(("Vorher wurde Call-Solver NOCH NICHT aufgerufen. Also JavaCall-Solver direkt aufgerufen."));
					assertTrue(objEntry.isJavaCallSolveCalled());  //aber wenn der JavaCallSolver direkt aufgerufen wurde. Wird der Aufruf nicht vermieden....
				}
			}
			
								
			assertTrue(objEntry.isJavaCall());            ///Beim Parsen wird das festgestellt, JavaCall ist enabled
			assertTrue(objEntry.isJavaCallSolved());      //Der konkrete JAVACALL-Solver ist duch Flags deaktiviert, er wird zwar aufgerufen, aber nicht ausgefuehrt
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		
			assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingClassname() + "'", objEntry.getCallingClassname());
			assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingMethodname() + "'", objEntry.getCallingMethodname());
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
							
			assertFalse(objEntry.isDecrypted());
			assertNull(objEntry.getValueDecrypted()); //Merke: sValue kann unterschiedlich zu dem decrypted Wert sein. Wenn etwas drumherum steht.

			bReturn = true;
		}//end main:
		return bReturn;
	}


	private static boolean assertFileIniEntry_solve_FLAGSET_JAVACALL_SOLVED_(IEnumSetMappedTestSurroundingZZZ objEnumSurrounding, IKernelConfigSectionEntryZZZ objEntry, String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, boolean bAsEntry) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			String sExpression = sExpressionIn;
			String sExpressionSubstituted = sExpressionSubstitutedIn;
			String sExpressionSolved = sExpressionSolvedIn;
			
			String sTagStartZ = "<Z>";
			String sTagEndZ = "</Z>";	
			
			String sFormulaSolvedAndConverted=null; String sFormulaSolvedAndConvertedAsExpression=null;
			String sExpressionSubstituted2compareWithSolved=null; String sExpressionSubstituted2compareWithExpression = null; 
			String sExpressionParsed2compareWithSubstituted=null; String sExpressionSolved2compareWithSubstituted=null;
			String sExpression2compareWithSubstituted=null; String sExpression2compareWithSolved = null;
			String sSubstituted2compare=null;
				
			boolean bCallSolverCalledPrevious=false;
		
		
			//######## TEST-TEIL ###########################
			assertTrue(objEntry.isExpression()); //ohne Expression-Nutzung kein Expression Eintrag!!!
			
			//+++++++++++++++++++++
			assertTrue(objEntry.isParseCalled()); //Auch wenn die Expression nicht verarbeitet wird, dann ist doch geparsed worden....
			//Beim Solven kann nicht auf die Änderung vom Parsen geprüft werden, 
			//da wir das Ergebnis des Parsens nicht haben.
			//Merke: .isParsedChanged() laesst sich hier nicht ermitteln.
			assertTrue(objEntry.isParsed()); 
			
			//+++++++++++++++++++++++
			assertTrue(objEntry.isPathSubstituteCalled());
			if(objEntry.isPathSubstituted()){ //Kann hier eigentlich nicht getestet werden. Ggfs. wird eine Expression ohne INI-PATH uebergeben
				if(sExpression.equals(sExpressionSubstituted)) { 
					assertFalse(objEntry.isSubstitutedChanged());
				}else {
					assertTrue(objEntry.isSubstitutedChanged());
				}
			}
			assertTrue(objEntry.isPathSubstituted()); //falls das entsprechende Flag gesetzt ist, unabhaengig davon, ob ein INI-PATH Ausdruck darin ist
			//++++++++++++++++++++++++
			//++++++++++++++++++++++++
			
			assertTrue(objEntry.isVariableSubstituted()); //falls das entsprechende Flag gesetzt ist, unabhaengig davon, ob eine INI-Variable darin ist
			//++++++++++++++++++++++++				
			//++++++++++++++++++++++++
			assertTrue(objEntry.isSolveCalled()); //Aufgerufen wurde der solveCall ja...
			sExpressionSubstituted2compareWithSolved = sExpressionSubstituted;				
			sExpressionSubstituted2compareWithSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSubstituted2compareWithSolved, sTagStartZ, sTagEndZ);				
			sExpressionSolved2compareWithSubstituted = sExpressionSolved;
			sExpressionSolved2compareWithSubstituted = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved2compareWithSubstituted, sTagStartZ, sTagEndZ);				
			System.out.println("sExpressionSolved2compareWithSubstituted="+sExpressionSolved2compareWithSubstituted);
			System.out.println("sExpressionSubstituted2compareWithSolved="+sExpressionSubstituted2compareWithSolved);
			if(sExpressionSolved2compareWithSubstituted.equals(sExpressionSubstituted2compareWithSolved)) {
				assertFalse(objEntry.isSolvedChanged()); 
			}else {					
				assertTrue(objEntry.isSolvedChanged());
			}
			assertTrue(objEntry.isSolved());
			
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			
			sFormulaSolvedAndConverted = objEntry.getValueFormulaSolvedAndConverted();
			assertNull("NULL erwartet. Wert ist aber '" + sFormulaSolvedAndConverted + "'", sFormulaSolvedAndConverted); //Da keine Formel enthalten ist
					
			sFormulaSolvedAndConvertedAsExpression = objEntry.getValueFormulaSolvedAndConvertedAsExpression();					
			assertEquals(XmlUtilZZZ.computeTagNull(), sFormulaSolvedAndConvertedAsExpression);//Da keine Formel enthalten ist.

			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++

			
			//+++++++++++++++++++++++++
			//+++++++++++++++++++++++++
			assertTrue(objEntry.isCallSolveCalled());
			assertFalse(objEntry.isCallSolved());//Der Eltern-Solver ist abgestellt
			//+++++++++++++++++++++++++
			//+++++++++++++++++++++++++
			assertTrue(objEntry.isJavaCallSolveCalled()); //trotz JAVACALL-Unsolved Flag wird der JAVACALL-Solver durchaus aufgerufen
			assertTrue(objEntry.isJavaCallSolved());
			//+++++++++++++++++++++++++
			
			//+++ Auf Werte kann man hier eigentlich nicht so abfragen, weil ggfs. keine Variablen in der Expression sind.
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			
			//+++ Auf Werte kann man hier eigentlich nicht so abfragen, weil ggfs. doch CRYPTED Werte in der Expression sind
			assertFalse(objEntry.isDecrypted());
			assertNull(objEntry.getValueDecrypted()); //Merke: sValue kann unterschiedlich zu dem decrypted Wert sein. Wenn etwas drumherum steht.
			
			//+++ Auf Werte kann man hier eigentlich nicht so abfragen, weil ggfs. KEINE CALL-Werte in der Expression sind
			assertTrue(objEntry.isCall());		//Beim Parsen wird das festgestellt
			assertTrue(objEntry.isJavaCall()); 	//Beim Parsen wird das festgestellt
			assertNotNull("NOT NULL erwartet. Wert ist aber '" + objEntry.getCallingClassname() + "'", objEntry.getCallingClassname());
			assertNotNull("NOT NULL erwartet. Wert ist aber '" + objEntry.getCallingMethodname() + "'", objEntry.getCallingMethodname());

			bReturn = true;
		}//end main:
		return bReturn;
	}


	private static boolean assertFileIniEntry_solve_FLAGSET_JAVACALL_UNSOLVED_(IEnumSetMappedTestSurroundingZZZ objEnumSurrounding, IKernelConfigSectionEntryZZZ objEntry, String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, boolean bAsEntry) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			String sExpression = sExpressionIn;
			String sExpressionSubstituted = sExpressionSubstitutedIn;
			String sExpressionSolved = sExpressionSolvedIn;
			
			String sTagStartZ = "<Z>";
			String sTagEndZ = "</Z>";	
			
			String sFormulaSolvedAndConverted=null; String sFormulaSolvedAndConvertedAsExpression=null;
			String sExpressionSubstituted2compareWithSolved=null; String sExpressionSubstituted2compareWithExpression = null; 
			String sExpressionParsed2compareWithSubstituted=null; String sExpressionSolved2compareWithSubstituted=null;
			String sExpression2compareWithSubstituted=null; String sExpression2compareWithSolved = null;
			String sSubstituted2compare=null;
				
			boolean bCallSolverCalledPrevious=false;
		
		
			//######## TEST-TEIL ###########################
			assertTrue(objEntry.isExpression()); //ohne Expression-Nutzung kein Expression Eintrag!!!
			
			//++++++++++++++++++++++ Weil nicht gesolved wurde, kann auf das Parse Ergebnis zugegriffen werden
			assertTrue(objEntry.isParseCalled()); //Auch wenn die Expression nicht verarbeitet wird, dann ist doch geparsed worden....
			sExpression2compareWithSubstituted = sExpression;
			sSubstituted2compare = sExpressionSubstituted;
			if(objEnumSurrounding == EnumSetMappedTestSurroundingTypeZZZ.SOLVE_REMOVE && objEntry.isSolved()) {
				sExpression2compareWithSubstituted = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpression2compareWithSubstituted, sTagStartZ, sTagEndZ, false);
				sSubstituted2compare = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sSubstituted2compare, sTagStartZ, sTagEndZ, false);
			}
			System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpression2compareWithSubstituted=" + sExpression2compareWithSubstituted);
			System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sSubstituted2compare=" + sSubstituted2compare);
			if(sExpression2compareWithSubstituted.equals(sSubstituted2compare)) {
				assertFalse(objEntry.isParsedChanged());						
			}else {
				assertTrue(objEntry.isParsedChanged());
			}
			assertTrue(objEntry.isParsed());
								
			//+++++++++++++++++++++++ ++++++++++++++++++++++ Weil nicht gesolved wurde, kann auf das Parse Ergebnis zugegriffen werden
			assertTrue(objEntry.isPathSubstituteCalled());
			sExpression2compareWithSubstituted = sExpression;
			sSubstituted2compare = sExpressionSubstituted;
			if(objEnumSurrounding == EnumSetMappedTestSurroundingTypeZZZ.SOLVE_REMOVE && objEntry.isSolved()) {
				sExpression2compareWithSubstituted = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpression2compareWithSubstituted, sTagStartZ, sTagEndZ, false);
				sSubstituted2compare = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sSubstituted2compare, sTagStartZ, sTagEndZ, false);
			}
			System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpression2compareWithSubstituted=" + sExpression2compareWithSubstituted);
			System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpressionSubstituted=" + sExpressionSubstituted);					
			if(objEntry.isPathSubstituted()){ //Kann hier eigentlich nicht getestet werden. Ggfs. wird eine Expression ohne INI-PATH uebergeben
				if(sExpression2compareWithSubstituted.equals(sSubstituted2compare)) { 
					assertFalse(objEntry.isSubstitutedChanged());
				}else {
					assertTrue(objEntry.isSubstitutedChanged());
				}
			}
			assertTrue(objEntry.isPathSubstituted()); //falls das entsprechende Flag gesetzt ist, unabhaengig davon, ob ein INI-PATH Ausdruck darin ist
	
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++				
			//+++Varaiablen Substitution waere an +++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			assertTrue(objEntry.isVariableSubstituteCalled());
																//+++ kann man hier doch auch eigentlich nicht so abfragen														
			assertTrue(objEntry.isVariableSubstituted());
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++				
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			assertTrue(objEntry.isSolveCalled()); //Aufgerufen wurde der solveCall ja...
			
			sExpressionSubstituted2compareWithSolved = sExpressionSubstituted;				
			sExpressionSubstituted2compareWithSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSubstituted2compareWithSolved, sTagStartZ, sTagEndZ);				
			sExpressionSolved2compareWithSubstituted = sExpressionSolved;
			sExpressionSolved2compareWithSubstituted = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved2compareWithSubstituted, sTagStartZ, sTagEndZ);				
			System.out.println("sExpressionSolved2compareWithSubstituted="+sExpressionSolved2compareWithSubstituted);
			System.out.println("sExpressionSubstituted2compareWithSolved="+sExpressionSubstituted2compareWithSolved);
			if(sExpressionSolved2compareWithSubstituted.equals(sExpressionSubstituted2compareWithSolved)) {
				assertFalse(objEntry.isSolvedChanged()); 
			}else {					
				assertTrue(objEntry.isSolvedChanged());
			}
											
			assertTrue(objEntry.isSolved());
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			
			sFormulaSolvedAndConverted = objEntry.getValueFormulaSolvedAndConverted();
			assertNull("NULL erwartet. Wert ist aber '" + sFormulaSolvedAndConverted + "'", sFormulaSolvedAndConverted); //Da keine Formel enthalten ist
					
			sFormulaSolvedAndConvertedAsExpression = objEntry.getValueFormulaSolvedAndConvertedAsExpression();					
			assertEquals(XmlUtilZZZ.computeTagNull(), sFormulaSolvedAndConvertedAsExpression);//Da keine Formel enthalten ist.

			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++

			assertFalse(objEntry.isJson()); //Ergebnisse kommen vom Parsen, JSON unsolved
			
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//Frueher: Ist der JavaCall-SOLVER deaktiviert, wird nun nicht der CallSolver aufgerufen, also das Kennzeichen nicht gesetzt
			//Jetzt 20250311: Durch die zu ueberschreibende Methode aus ISolveUserZZZ .updateValueSolveCalled(...) werden alle relevanten Kennzeichen gesetzt
			//Generische Problematik. Stichwort "Elterntag" TODOGOON20250308; TICKET20250308; Diese Testutility wird auch von KernelJavaCallIniSolverZZZTest aufgerufen.
			//ABER: Wenn der Solver generel aus gestellt wird, dann wird nix dahinter aufgerufen.
			assertTrue(objEntry.isCall());		//Beim Parsen wird das festgestellt
			assertTrue(objEntry.isCallSolveCalled());
			assertTrue(objEntry.isCallSolved()); //trotz Deaktivierung des Solvers mit JAVACALL-Unsolved Flag wird der CALL-Solver ausgefuehrt					

			sFormulaSolvedAndConvertedAsExpression = objEntry.getValueFormulaSolvedAndConvertedAsExpression();					
			assertEquals(XmlUtilZZZ.computeTagNull(), sFormulaSolvedAndConvertedAsExpression);//Da keine Formel enthalten ist.

			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++							
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
						
			assertFalse(objEntry.isJavaCall());
			
			//Hier differenzieren, ob der Aufruf direkt erfolgte oder schon der Solver des "Elterntags" aufgerufen worden ist......
			bCallSolverCalledPrevious = VectorUtilZZZ.containsString((Vector) objEntry.getHistorySolveCalledVector(), KernelCallIniSolverZZZ.sTAG_NAME);
			if(bCallSolverCalledPrevious) {
				System.out.println(("Vorher wurde Call-Solver aufgerufen. Also Java-Call-Solver nicht direkt aufgerufen."));
				assertFalse(objEntry.isJavaCallSolveCalled()); //Der Aufruf wird vom CALL-Handler vermieden, da durch Flag deaktiviert.
			}else {
				//ABER: Bei einem "entry"-Aufruf wird auch der JavaCall-Solver nicht direkt aufgerufen.
//				if(bAsEntry) {
//					System.out.println(("Aufruf als Entry. Also kann JavaCall-Solver nicht direkt aufgerufen worden sein."));
//					assertFalse(objEntry.isJavaCallSolveCalled()); //Der Aufruf wird vom CALL-Handler vermieden, da durch Flag deaktiviert.
//				}else {
//					System.out.println(("Vorher wurde Call-Solver NOCH NICHT aufgerufen. Also JavaCall-Solver direkt aufgerufen."));
//					assertTrue(objEntry.isJavaCallSolveCalled());  //aber wenn der JavaCallSolver direkt aufgerufen wurde. Wird der Aufruf nicht vermieden....
//				}
				
				//ABER: Hier ist nicht call-deaktiviert, sondern nur javacall. Darum wird call immer vorher aufgerufen, egal ob entry oder nicht.
				System.out.println(("Vorher wurde Call-Solver NOCH NICHT aufgerufen. Also JavaCall-Solver direkt aufgerufen."));
				assertTrue(objEntry.isJavaCallSolveCalled());  //aber wenn der JavaCallSolver direkt aufgerufen wurde. Wird der Aufruf nicht vermieden....									
			}
			assertFalse(objEntry.isJavaCallSolved());      //Aber: Das es gilt auch beim direkten Aufruf: Der konkrete JAVACALL-Solver ist duch Flags deaktiviert, er wird zwar aufgerufen, aber nicht ausgefuehrt				
			assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingClassname() + "'", objEntry.getCallingClassname());
			assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingMethodname() + "'", objEntry.getCallingMethodname());
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
							
			assertFalse(objEntry.isDecrypted());
			assertNull(objEntry.getValueDecrypted()); //Merke: sValue kann unterschiedlich zu dem decrypted Wert sein. Wenn etwas drumherum steht.

			bReturn = true;
		}//end main:
		return bReturn;
	}
	
	private static boolean assertFileIniEntry_solve_FLAGSET_JSON_UNSOLVED_(IEnumSetMappedTestSurroundingZZZ objEnumSurrounding, IKernelConfigSectionEntryZZZ objEntry, String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, boolean bAsEntry) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			String sExpression = sExpressionIn;
			String sExpressionSubstituted = sExpressionSubstitutedIn;
			String sExpressionSolved = sExpressionSolvedIn;
			
			String sTagStartZ = "<Z>";
			String sTagEndZ = "</Z>";	
			
			String sFormulaSolvedAndConverted=null; String sFormulaSolvedAndConvertedAsExpression=null;
			String sExpressionSubstituted2compareWithSolved=null; String sExpressionSubstituted2compareWithExpression = null; 
			String sExpressionParsed2compareWithSubstituted=null; String sExpressionSolved2compareWithSubstituted=null;
			String sExpression2compareWithSubstituted=null; String sExpression2compareWithSolved = null;
			String sSubstituted2compare=null;
				
			boolean bCallSolverCalledPrevious=false;
		
		
			//######## TEST-TEIL ###########################
			assertTrue(objEntry.isExpression()); 
			
			//++++++++++++++++++++++ Weil nicht gesolved wurde, kann auf das Parse Ergebnis zugegriffen werden
			assertTrue(objEntry.isParseCalled()); //Auch wenn die Expression nicht verarbeitet wird, dann ist doch geparsed worden....
			sExpression2compareWithSubstituted = sExpression;
			if(objEnumSurrounding == EnumSetMappedTestSurroundingTypeZZZ.SOLVE_REMOVE && objEntry.isSolved()) {
				sExpression2compareWithSubstituted = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpression2compareWithSubstituted, sTagStartZ, sTagEndZ, false);
			}
			System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpression2compareWithSubsituted=" + sExpression2compareWithSubstituted);
			System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpressionSubstituted=" + sExpressionSubstituted);
			if(sExpression2compareWithSubstituted.equals(sExpressionSubstituted)) {
				assertFalse(objEntry.isParsedChanged());						
			}else {
				assertTrue(objEntry.isParsedChanged());
			}
			assertTrue(objEntry.isParsed());
								
			//+++++++++++++++++++++++ ++++++++++++++++++++++ Weil nicht gesolved wurde, kann auf das Parse Ergebnis zugegriffen werden
			assertTrue(objEntry.isPathSubstituteCalled());
			sExpression2compareWithSubstituted = sExpression;
			if(objEnumSurrounding == EnumSetMappedTestSurroundingTypeZZZ.SOLVE_REMOVE && objEntry.isSolved()) {
				sExpression2compareWithSubstituted = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpression2compareWithSubstituted, sTagStartZ, sTagEndZ, false);
			}
			System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpression2compareWithSubstituted=" + sExpression2compareWithSubstituted);
			System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpressionSubsituted=" + sExpressionSubstituted);					
			if(objEntry.isPathSubstituted()){ //Kann hier eigentlich nicht getestet werden. Ggfs. wird eine Expression ohne INI-PATH uebergeben
				if(sExpression2compareWithSubstituted.equals(sExpressionSubstituted)) { 
					assertFalse(objEntry.isSubstitutedChanged());
				}else {
					assertTrue(objEntry.isSubstitutedChanged());
				}
			}
			assertTrue(objEntry.isPathSubstituted()); //falls das entsprechende Flag gesetzt ist, unabhaengig davon, ob ein INI-PATH Ausdruck darin ist
			//++++++++++++++++++++++++
			//++++++++++++++++++++++++
			
			assertTrue(objEntry.isVariableSubstituted()); //falls das entsprechende Flag gesetzt ist, unabhaengig davon, ob eine INI-Variable darin ist
				
		
			//++++++++++++++++++++++++				
			//++++++++++++++++++++++++
			assertTrue(objEntry.isSolveCalled()); //Aufgerufen wurde der solveCall ja...					
			assertFalse(objEntry.isSolvedChanged()); 
			assertTrue(objEntry.isSolved());
			//+++++++++++++++++++++++++
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			
			sFormulaSolvedAndConverted = objEntry.getValueFormulaSolvedAndConverted();
			assertNull("NULL erwartet. Wert ist aber '" + sFormulaSolvedAndConverted + "'", sFormulaSolvedAndConverted); //Da keine Formel enthalten ist
					
			sFormulaSolvedAndConvertedAsExpression = objEntry.getValueFormulaSolvedAndConvertedAsExpression();					
			assertEquals(XmlUtilZZZ.computeTagNull(), sFormulaSolvedAndConvertedAsExpression);//Da keine Formel enthalten ist.

			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++
		
			assertTrue(objEntry.isJson()); 		
			
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//+++ Auf Werte kann man auf JSON-Ebene, die ggfs. auch fuer andere Eingabestrings verwendet wird, nicht abfragen
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//assertFalse(objEntry.isJsonMap());
			//assertFalse(objEntry.isJsonArray());
							
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//+++ Auf Werte kann man hier eigentlich nicht so abfragen, weil ggfs. keine Variablen in der Expression sind.
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			assertFalse(objEntry.isCallSolveCalled());
			assertFalse(objEntry.isCallSolved());//Der konkrete Solver ist nicht involviert
			//+++++++++++++++++++++++++
			//+++++++++++++++++++++++++
			assertFalse(objEntry.isJavaCallSolveCalled()); //trotz JAVACALL-Unsolved Flag wird der JAVACALL-Solver durchaus aufgerufen
			assertFalse(objEntry.isJavaCallSolved());//Der konkrete Solver ist nicht involviert
			//+++++++++++++++++++++++++
			
			//+++ Auf Werte kann man hier eigentlich nicht so abfragen, weil ggfs. doch CRYPTED Werte in der Expression sind
			assertFalse(objEntry.isDecrypted());
			assertNull(objEntry.getValueDecrypted()); //Merke: sValue kann unterschiedlich zu dem decrypted Wert sein. Wenn etwas drumherum steht.
			
			//+++ Auf Werte kann man hier eigentlich nicht so abfragen, weil ggfs. KEINE CALL-Werte in der Expression sind
			assertFalse(objEntry.isCall());		//Beim Parsen wird das festgestellt
			assertFalse(objEntry.isJavaCall()); 	//Beim Parsen wird das festgestellt
			assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingClassname() + "'", objEntry.getCallingClassname());
			assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingMethodname() + "'", objEntry.getCallingMethodname());
			
			//fail("Testutil for flagset missing: " + sFLAGSET_JSON_UNSOLVED);

			bReturn = true;
		}//end main:
		return bReturn;
	}

	
	private static boolean assertFileIniEntry_solve_FLAGSET_JSONARRAY_SOLVED_(IEnumSetMappedTestSurroundingZZZ objEnumSurrounding, IKernelConfigSectionEntryZZZ objEntry, String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, boolean bAsEntry) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			String sExpression = sExpressionIn;
			String sExpressionSubstituted = sExpressionSubstitutedIn;
			String sExpressionSolved = sExpressionSolvedIn;
			
			String sTagStartZ = "<Z>";
			String sTagEndZ = "</Z>";	
			
			String sFormulaSolvedAndConverted=null; String sFormulaSolvedAndConvertedAsExpression=null;
			String sExpressionSubstituted2compareWithSolved=null; String sExpressionSubstituted2compareWithExpression = null; 
			String sExpressionParsed2compareWithSubstituted=null; String sExpressionSolved2compareWithSubstituted=null;
			String sExpression2compareWithSubstituted=null; String sExpression2compareWithSolved = null;
			String sSubstituted2compare=null;
				
			boolean bCallSolverCalledPrevious=false;
		
		
			//######## TEST-TEIL ###########################
			assertTrue(objEntry.isExpression()); //ohne Expression-Nutzung kein Expression Eintrag!!!
			
			//++++++++++++++++++++++
			assertTrue(objEntry.isParseCalled()); //Auch wenn die Expression nicht verarbeitet wird, dann ist doch geparsed worden....
			//Beim Solven kann nicht auf die Änderung vom Parsen geprüft werden, 
			//da wir das Ergebnis des Parsens nicht haben.
			//Merke: .isParsedChanged() laesst sich hier nicht ermitteln.
			assertTrue(objEntry.isParsed()); 
			
			//+++++++++++++++++++++++
			assertTrue(objEntry.isPathSubstituteCalled());
			//Beim Solven kann nicht auf die Änderung vom Parsen geprüft werden, 
			//da wir das Ergebnis des Parsens nicht haben.
			//Merke: .isParsedChanged() laesst sich hier nicht ermitteln.
			assertTrue(objEntry.isPathSubstituted()); //falls das entsprechende Flag gesetzt ist, unabhaengig davon, ob ein INI-PATH Ausdruck darin ist
			//++++++++++++++++++++++++
			//++++++++++++++++++++++++
			
			assertTrue(objEntry.isVariableSubstituted()); //falls das entsprechende Flag gesetzt ist, unabhaengig davon, ob eine INI-Variable darin ist
			//++++++++++++++++++++++++				
			//++++++++++++++++++++++++
			assertTrue(objEntry.isSolveCalled()); //Aufgerufen wurde der solveCall ja...
			sExpressionSubstituted2compareWithSolved = sExpressionSubstituted;				
			sExpressionSubstituted2compareWithSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSubstituted2compareWithSolved, sTagStartZ, sTagEndZ);				
			sExpressionSolved2compareWithSubstituted = sExpressionSolved;
			sExpressionSolved2compareWithSubstituted = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved2compareWithSubstituted, sTagStartZ, sTagEndZ);				
			System.out.println("sExpressionSolved2compareWithSubstituted="+sExpressionSolved2compareWithSubstituted);
			System.out.println("sExpressionSubstituted2compareWithSolved="+sExpressionSubstituted2compareWithSolved);
			if(sExpressionSolved2compareWithSubstituted.equals(sExpressionSubstituted2compareWithSolved)) {
				assertFalse(objEntry.isSolvedChanged()); 
			}else {					
				assertTrue(objEntry.isSolvedChanged());
			}
			assertTrue(objEntry.isSolved());
			//+++++++++++++++++++++++++
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			
			sFormulaSolvedAndConverted = objEntry.getValueFormulaSolvedAndConverted();
			assertNull("NULL erwartet. Wert ist aber '" + sFormulaSolvedAndConverted + "'", sFormulaSolvedAndConverted); //Da keine Formel enthalten ist
					
			sFormulaSolvedAndConvertedAsExpression = objEntry.getValueFormulaSolvedAndConvertedAsExpression();					
			assertEquals(XmlUtilZZZ.computeTagNull(), sFormulaSolvedAndConvertedAsExpression);//Da keine Formel enthalten ist.

			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++

			
			assertTrue(objEntry.isJson());
			assertFalse(objEntry.isJsonMap());
			assertTrue(objEntry.isJsonArray());
							
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//+++ Auf Werte kann man hier eigentlich nicht so abfragen, weil ggfs. keine Variablen in der Expression sind.
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			assertFalse(objEntry.isCallSolveCalled());
			assertFalse(objEntry.isCallSolved());//Der konkrete Solver ist nicht involviert
			//+++++++++++++++++++++++++
			//+++++++++++++++++++++++++
			assertFalse(objEntry.isJavaCallSolveCalled()); //trotz JAVACALL-Unsolved Flag wird der JAVACALL-Solver durchaus aufgerufen
			assertFalse(objEntry.isJavaCallSolved());//Der konkrete Solver ist nicht involviert
			//+++++++++++++++++++++++++
			
			//+++ Auf Werte kann man hier eigentlich nicht so abfragen, weil ggfs. doch CRYPTED Werte in der Expression sind
			assertFalse(objEntry.isDecrypted());
			assertNull(objEntry.getValueDecrypted()); //Merke: sValue kann unterschiedlich zu dem decrypted Wert sein. Wenn etwas drumherum steht.
			
			//+++ Auf Werte kann man hier eigentlich nicht so abfragen, weil ggfs. KEINE CALL-Werte in der Expression sind
			assertFalse(objEntry.isCall());		//Beim Parsen wird das festgestellt
			assertFalse(objEntry.isJavaCall()); 	//Beim Parsen wird das festgestellt
			assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingClassname() + "'", objEntry.getCallingClassname());
			assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingMethodname() + "'", objEntry.getCallingMethodname());

			bReturn = true;
		}//end main:
		return bReturn;
	}


	private static boolean assertFileIniEntry_solve_FLAGSET_JSONARRAY_UNSOLVED_(IEnumSetMappedTestSurroundingZZZ objEnumSurrounding, IKernelConfigSectionEntryZZZ objEntry, String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, boolean bAsEntry) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			String sExpression = sExpressionIn;
			String sExpressionSubstituted = sExpressionSubstitutedIn;
			String sExpressionSolved = sExpressionSolvedIn;
			
			String sTagStartZ = "<Z>";
			String sTagEndZ = "</Z>";	
			
			String sFormulaSolvedAndConverted=null; String sFormulaSolvedAndConvertedAsExpression=null;
			String sExpressionSubstituted2compareWithSolved=null; String sExpressionSubstituted2compareWithExpression = null; 
			String sExpressionParsed2compareWithSubstituted=null; String sExpressionSolved2compareWithSubstituted=null;
			String sExpression2compareWithSubstituted=null; String sExpression2compareWithSolved = null;
			String sSubstituted2compare=null;
				
			boolean bCallSolverCalledPrevious=false;
		
		
			//######## TEST-TEIL ###########################
			assertTrue(objEntry.isExpression()); //ohne Expression-Nutzung kein Expression Eintrag!!!
			
			//++++++++++++++++++++++ Weil nicht gesolved wurde, kann auf das Parse Ergebnis zugegriffen werden
			assertTrue(objEntry.isParseCalled()); //Auch wenn die Expression nicht verarbeitet wird, dann ist doch geparsed worden....
			sExpression2compareWithSolved = sExpression;
			if(objEntry.isSolved() && objEnumSurrounding == EnumSetMappedTestSurroundingTypeZZZ.SOLVE_REMOVE) { //ohne das Solve ausgefuehrt wurde wird auch nichts veraendert.
				sExpression2compareWithSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpression2compareWithSolved, sTagStartZ, sTagEndZ, false);
				System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpression2compareWithSolved (veraendert)=" + sExpression2compareWithSolved);
			}else {
				System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpression2compareWithSolved (unveraendert)=" + sExpression2compareWithSolved);	
			}
			
			
			System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpressionSolved=" + sExpressionSolved);
			if(sExpression2compareWithSolved.equals(sExpressionSolved)) {
				assertFalse(objEntry.isParsedChanged());						
			}else {
				assertTrue(objEntry.isParsedChanged());
			}
			assertTrue(objEntry.isParsed());
								
			//+++++++++++++++++++++++ ++++++++++++++++++++++ Weil nicht gesolved wurde, kann auf das Parse Ergebnis zugegriffen werden
			assertTrue(objEntry.isPathSubstituteCalled());
			sExpression2compareWithSubstituted = sExpression;
			if(objEntry.isSolved() && objEnumSurrounding == EnumSetMappedTestSurroundingTypeZZZ.SOLVE_REMOVE) { //Ohne das der Solver abgeschlossen wurde, wird auch nichts entfernt.
				sExpression2compareWithSubstituted = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpression2compareWithSubstituted, sTagStartZ, sTagEndZ, false);
				System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpression2compareWithSubstituted (veraendert)=" + sExpression2compareWithSubstituted);
			}else {
				System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpression2compareWithSubstituted (unveraendert)=" + sExpression2compareWithSubstituted);					
			}
			
			System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpressionSubstituted=" + sExpressionSubstituted);					
			if(objEntry.isPathSubstituted()){ //Kann hier eigentlich nicht getestet werden. Ggfs. wird eine Expression ohne INI-PATH uebergeben
				if(sExpression2compareWithSubstituted.equals(sExpressionSubstituted)) { 
					assertFalse(objEntry.isSubstitutedChanged());
				}else {
					assertTrue(objEntry.isSubstitutedChanged());
				}
			}
			assertTrue(objEntry.isPathSubstituted()); //falls das entsprechende Flag gesetzt ist, unabhaengig davon, ob ein INI-PATH Ausdruck darin ist
			//++++++++++++++++++++++++
			//++++++++++++++++++++++++
			
			assertTrue(objEntry.isVariableSubstituted()); //falls das entsprechende Flag gesetzt ist, unabhaengig davon, ob eine INI-Variable darin ist
			//++++++++++++++++++++++++				
			//++++++++++++++++++++++++
			assertTrue(objEntry.isSolveCalled()); //Aufgerufen wurde der solveCall ja...					
			assertFalse(objEntry.isSolvedChanged()); 
			assertTrue(objEntry.isSolved());     //Solve wird sogar abgeschlossen, nur JSON_ARRAY bleibt unaufgeloest. 
			//+++++++++++++++++++++++++
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			
			sFormulaSolvedAndConverted = objEntry.getValueFormulaSolvedAndConverted();
			assertNull("NULL erwartet. Wert ist aber '" + sFormulaSolvedAndConverted + "'", sFormulaSolvedAndConverted); //Da keine Formel enthalten ist
					
			sFormulaSolvedAndConvertedAsExpression = objEntry.getValueFormulaSolvedAndConvertedAsExpression();					
			assertEquals(XmlUtilZZZ.computeTagNull(), sFormulaSolvedAndConvertedAsExpression);//Da keine Formel enthalten ist.

			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++

			
			assertTrue(objEntry.isJson()); //Ergebnisse kommen vom Parsen.
			assertFalse(objEntry.isJsonMap());
			assertFalse(objEntry.isJsonArray()); 
							
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//+++ Auf Werte kann man hier eigentlich nicht so abfragen, weil ggfs. keine Variablen in der Expression sind.
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			assertFalse(objEntry.isCallSolveCalled());
			assertFalse(objEntry.isCallSolved());//Der konkrete Solver ist nicht involviert
			//+++++++++++++++++++++++++
			//+++++++++++++++++++++++++
			assertFalse(objEntry.isJavaCallSolveCalled()); //trotz JAVACALL-Unsolved Flag wird der JAVACALL-Solver durchaus aufgerufen
			assertFalse(objEntry.isJavaCallSolved());//Der konkrete Solver ist nicht involviert
			//+++++++++++++++++++++++++
			
			//+++ Auf Werte kann man hier eigentlich nicht so abfragen, weil ggfs. doch CRYPTED Werte in der Expression sind
			assertFalse(objEntry.isDecrypted());
			assertNull(objEntry.getValueDecrypted()); //Merke: sValue kann unterschiedlich zu dem decrypted Wert sein. Wenn etwas drumherum steht.
			
			//+++ Auf Werte kann man hier eigentlich nicht so abfragen, weil ggfs. KEINE CALL-Werte in der Expression sind
			assertFalse(objEntry.isCall());		//Beim Parsen wird das festgestellt
			assertFalse(objEntry.isJavaCall()); 	//Beim Parsen wird das festgestellt
			assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingClassname() + "'", objEntry.getCallingClassname());
			assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingMethodname() + "'", objEntry.getCallingMethodname());
			
			//fail("Testutil for flagset missing: " + sFLAGSET_JSONARRAY_UNSOLVED);

			bReturn = true;
		}//end main:
		return bReturn;
	}


	private static boolean assertFileIniEntry_solve_FLAGSET_JSONMAP_SOLVED_(IEnumSetMappedTestSurroundingZZZ objEnumSurrounding, IKernelConfigSectionEntryZZZ objEntry, String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, boolean bAsEntry) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			String sExpression = sExpressionIn;
			String sExpressionSubstituted = sExpressionSubstitutedIn;
			String sExpressionSolved = sExpressionSolvedIn;
			
			String sTagStartZ = "<Z>";
			String sTagEndZ = "</Z>";	
			
			String sFormulaSolvedAndConverted=null; String sFormulaSolvedAndConvertedAsExpression=null;
			String sExpressionSubstituted2compareWithSolved=null; String sExpressionSubstituted2compareWithExpression = null; 
			String sExpressionParsed2compareWithSubstituted=null; String sExpressionSolved2compareWithSubstituted=null;
			String sExpression2compareWithSubstituted=null; String sExpression2compareWithSolved = null;
			String sSubstituted2compare=null;
				
			boolean bCallSolverCalledPrevious=false;
		
		
			//######## TEST-TEIL ###########################
			assertTrue(objEntry.isExpression()); //ohne Expression-Nutzung kein Expression Eintrag!!!
			
			//++++++++++++++++++++++
			assertTrue(objEntry.isParseCalled()); //Auch wenn die Expression nicht verarbeitet wird, dann ist doch geparsed worden....
			//Beim Solven kann nicht auf die Änderung vom Parsen geprüft werden, 
			//da wir das Ergebnis des Parsens nicht haben.
			//Merke: .isParsedChanged() laesst sich hier nicht ermitteln.
			assertTrue(objEntry.isParsed()); 
			
			//+++++++++++++++++++++++
			assertTrue(objEntry.isPathSubstituteCalled());
			//Beim Solven kann nicht auf die Änderung vom Parsen geprüft werden, 
			//da wir das Ergebnis des Parsens nicht haben.
			//Merke: .isParsedChanged() laesst sich hier nicht ermitteln.
			assertTrue(objEntry.isPathSubstituted()); //falls das entsprechende Flag gesetzt ist, unabhaengig davon, ob ein INI-PATH Ausdruck darin ist
			//++++++++++++++++++++++++
			//++++++++++++++++++++++++
			
			assertTrue(objEntry.isVariableSubstituted()); //falls das entsprechende Flag gesetzt ist, unabhaengig davon, ob eine INI-Variable darin ist
			//++++++++++++++++++++++++				
			//++++++++++++++++++++++++
			assertTrue(objEntry.isSolveCalled()); //Aufgerufen wurde der solveCall ja...
			sExpressionSubstituted2compareWithSolved = sExpressionSubstituted;				
			sExpressionSubstituted2compareWithSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSubstituted2compareWithSolved, sTagStartZ, sTagEndZ);				
			sExpressionSolved2compareWithSubstituted = sExpressionSolved;
			sExpressionSolved2compareWithSubstituted = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved2compareWithSubstituted, sTagStartZ, sTagEndZ);				
			System.out.println("sExpressionSolved2compareWithSubstituted="+sExpressionSolved2compareWithSubstituted);
			System.out.println("sExpressionSubstituted2compareWithSolved="+sExpressionSubstituted2compareWithSolved);
			if(sExpressionSolved2compareWithSubstituted.equals(sExpressionSubstituted2compareWithSolved)) {
				assertFalse(objEntry.isSolvedChanged()); 
			}else {					
				assertTrue(objEntry.isSolvedChanged());
			}
			assertTrue(objEntry.isSolved());
			//+++++++++++++++++++++++++
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			
			sFormulaSolvedAndConverted = objEntry.getValueFormulaSolvedAndConverted();
			assertNull("NULL erwartet. Wert ist aber '" + sFormulaSolvedAndConverted + "'", sFormulaSolvedAndConverted); //Da keine Formel enthalten ist
					
			sFormulaSolvedAndConvertedAsExpression = objEntry.getValueFormulaSolvedAndConvertedAsExpression();					
			assertEquals(XmlUtilZZZ.computeTagNull(), sFormulaSolvedAndConvertedAsExpression);//Da keine Formel enthalten ist.

			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++

			
			assertTrue(objEntry.isJson());
			assertTrue(objEntry.isJsonMap());
			assertFalse(objEntry.isJsonArray());
			
			
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//+++ Auf Werte kann man hier eigentlich nicht so abfragen, weil ggfs. keine Variablen in der Expression sind.
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			assertFalse(objEntry.isCallSolveCalled());
			assertFalse(objEntry.isCallSolved());//Der konkrete Solver ist nicht involviert
			//+++++++++++++++++++++++++
			//+++++++++++++++++++++++++
			assertFalse(objEntry.isJavaCallSolveCalled()); //trotz JAVACALL-Unsolved Flag wird der JAVACALL-Solver durchaus aufgerufen
			assertFalse(objEntry.isJavaCallSolved());//Der konkrete Solver ist nicht involviert
			//+++++++++++++++++++++++++
			
			
			
			//+++ Auf Werte kann man hier eigentlich nicht so abfragen, weil ggfs. doch CRYPTED Werte in der Expression sind
			assertFalse(objEntry.isDecrypted());
			assertNull(objEntry.getValueDecrypted()); //Merke: sValue kann unterschiedlich zu dem decrypted Wert sein. Wenn etwas drumherum steht.
			
			//+++ Auf Werte kann man hier eigentlich nicht so abfragen, weil ggfs. KEINE CALL-Werte in der Expression sind
			assertFalse(objEntry.isCall());		//Beim Parsen wird das festgestellt
			assertFalse(objEntry.isJavaCall()); 	//Beim Parsen wird das festgestellt
			assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingClassname() + "'", objEntry.getCallingClassname());
			assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingMethodname() + "'", objEntry.getCallingMethodname());

			bReturn = true;
		}//end main:
		return bReturn;
	}


	private static boolean assertFileIniEntry_solve_FLAGSET_JSONMAP_UNSOLVED_(IEnumSetMappedTestSurroundingZZZ objEnumSurrounding, IKernelConfigSectionEntryZZZ objEntry, String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, boolean bAsEntry) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			String sExpression = sExpressionIn;
			String sExpressionSubstituted = sExpressionSubstitutedIn;
			String sExpressionSolved = sExpressionSolvedIn;
			
			String sTagStartZ = "<Z>";
			String sTagEndZ = "</Z>";	
			
			String sFormulaSolvedAndConverted=null; String sFormulaSolvedAndConvertedAsExpression=null;
			String sExpressionSubstituted2compareWithSolved=null; String sExpressionSubstituted2compareWithExpression = null; 
			String sExpressionParsed2compareWithSubstituted=null; String sExpressionSolved2compareWithSubstituted=null;
			String sExpression2compareWithSubstituted=null; String sExpression2compareWithSolved = null;
			String sSubstituted2compare=null;
				
			boolean bCallSolverCalledPrevious=false;
		
		
			//######## TEST-TEIL ###########################
			assertTrue(objEntry.isExpression()); //ohne Expression-Nutzung kein Expression Eintrag!!!
			
			//++++++++++++++++++++++ Weil nicht gesolved wurde, kann auf das Parse Ergebnis zugegriffen werden
			assertTrue(objEntry.isParseCalled()); //Auch wenn die Expression nicht verarbeitet wird, dann ist doch geparsed worden....
			sExpression2compareWithSolved = sExpression;
			if(objEntry.isSolved() && objEnumSurrounding == EnumSetMappedTestSurroundingTypeZZZ.SOLVE_REMOVE) { //ohne das solve ausgefuehrt wurde wird auch nichts veraendert
				sExpression2compareWithSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpression2compareWithSolved, sTagStartZ, sTagEndZ, false);
				System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpression2compareWithSolved (veraendert)=" + sExpression2compareWithSolved);
			}else {
				System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpression2compareWithSolved (unveraendert)=" + sExpression2compareWithSolved);
			}
						
			System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpressionSolved=" + sExpressionSolved);
			if(sExpression2compareWithSolved.equals(sExpressionSolved)) {
				assertFalse(objEntry.isParsedChanged());						
			}else {
				assertTrue(objEntry.isParsedChanged());
			}
			assertTrue(objEntry.isParsed());
								
			//+++++++++++++++++++++++ ++++++++++++++++++++++ Weil nicht gesolved wurde, kann auf das Parse Ergebnis zugegriffen werden
			assertTrue(objEntry.isPathSubstituteCalled());
			sExpression2compareWithSubstituted = sExpression;
			if(objEnumSurrounding == EnumSetMappedTestSurroundingTypeZZZ.SOLVE_REMOVE && objEntry.isSolved()) {
				sExpression2compareWithSubstituted = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpression2compareWithSubstituted, sTagStartZ, sTagEndZ, false);
				System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpression2compareWithSubstituted (veraendert)=" + sExpression2compareWithSubstituted);
				sExpressionSubstituted = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSubstituted, sTagStartZ, sTagEndZ, false);
				System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpressionSubstituted (veraendert)=" + sExpressionSubstituted);
			}else {
				System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpression2compareWithSubstituted (unveraendert)=" + sExpression2compareWithSubstituted);
				System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpressionSubstituted (unveraendert)=" + sExpressionSubstituted);
			}
			
								
			if(objEntry.isPathSubstituted()){ //Kann hier eigentlich nicht getestet werden. Ggfs. wird eine Expression ohne INI-PATH uebergeben
				if(sExpression2compareWithSubstituted.equals(sExpressionSubstituted)) { 
					assertFalse(objEntry.isSubstitutedChanged());
				}else {
					assertTrue(objEntry.isSubstitutedChanged());
				}
			}
			assertTrue(objEntry.isPathSubstituted()); //falls das entsprechende Flag gesetzt ist, unabhaengig davon, ob ein INI-PATH Ausdruck darin ist
			//++++++++++++++++++++++++
			//++++++++++++++++++++++++
			
			assertTrue(objEntry.isVariableSubstituted()); //falls das entsprechende Flag gesetzt ist, unabhaengig davon, ob eine INI-Variable darin ist
			//++++++++++++++++++++++++				
			//++++++++++++++++++++++++
			assertTrue(objEntry.isSolveCalled()); //Aufgerufen wurde der solveCall ja...					
			assertFalse(objEntry.isSolvedChanged()); 
			assertTrue(objEntry.isSolved());     //solve selbst wurde ja ausgefuehrt. 
			//+++++++++++++++++++++++++
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			
			sFormulaSolvedAndConverted = objEntry.getValueFormulaSolvedAndConverted();
			assertNull("NULL erwartet. Wert ist aber '" + sFormulaSolvedAndConverted + "'", sFormulaSolvedAndConverted); //Da keine Formel enthalten ist
					
			sFormulaSolvedAndConvertedAsExpression = objEntry.getValueFormulaSolvedAndConvertedAsExpression();					
			assertEquals(XmlUtilZZZ.computeTagNull(), sFormulaSolvedAndConvertedAsExpression);//Da keine Formel enthalten ist.

			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++

			
			assertTrue(objEntry.isJson()); //Ergebnisse kommen vom Parsen
			assertFalse(objEntry.isJsonMap()); //isParserEnabledCustom=true, aber isParserEnabledThis=false also wird der eigene Tag nicht gefunden
			assertFalse(objEntry.isJsonArray()); //JSON_ARRAY wird nicht benutzt
							
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//+++ Auf Werte kann man hier eigentlich nicht so abfragen, weil ggfs. keine Variablen in der Expression sind.
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			assertFalse(objEntry.isCallSolveCalled());
			assertFalse(objEntry.isCallSolved());//Der konkrete Solver ist nicht involviert
			//+++++++++++++++++++++++++
			//+++++++++++++++++++++++++
			assertFalse(objEntry.isJavaCallSolveCalled()); //trotz JAVACALL-Unsolved Flag wird der JAVACALL-Solver durchaus aufgerufen
			assertFalse(objEntry.isJavaCallSolved());//Der konkrete Solver ist nicht involviert
			//+++++++++++++++++++++++++
			
			//+++ Auf Werte kann man hier eigentlich nicht so abfragen, weil ggfs. doch CRYPTED Werte in der Expression sind
			assertFalse(objEntry.isDecrypted());
			assertNull(objEntry.getValueDecrypted()); //Merke: sValue kann unterschiedlich zu dem decrypted Wert sein. Wenn etwas drumherum steht.
			
			//+++ Auf Werte kann man hier eigentlich nicht so abfragen, weil ggfs. KEINE CALL-Werte in der Expression sind
			assertFalse(objEntry.isCall());		//Beim Parsen wird das festgestellt
			assertFalse(objEntry.isJavaCall()); 	//Beim Parsen wird das festgestellt
			assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingClassname() + "'", objEntry.getCallingClassname());
			assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingMethodname() + "'", objEntry.getCallingMethodname());
			
			//fail("Testutil for flagset missing: " + sFLAGSET_JSONMAP_UNSOLVED);

			bReturn = true;
		}//end main:
		return bReturn;
	}

	
	public static boolean isExpressionValidForFlagset_parse(IEnumSetMappedTestFlagsetZZZ objEnumFlagset, String sExpressionIn) throws ExceptionZZZ {
		boolean bReturn = false;		
		main:{
			//+++ TEST DISPATCHER (light) +++++++++++
			//Wenn ein Ausdruck keine Expression ist, dann nur den _unexpressed Test erlauben:
			
			bReturn = true;
		}//end main:
		return bReturn;
	}

	public static boolean isExpressionValidForFlagset_solve(IEnumSetMappedTestFlagsetZZZ objEnumFlagset, String sExpressionIn) throws ExceptionZZZ {
		boolean bReturn = false;		
		main:{
			//+++ TEST DISPATCHER (light) +++++++++++
			//Wenn ein Ausdruck keine Expression ist, dann nur den _unexpressed Test erlauben:
			boolean bHasZ = XmlUtilZZZ.isExpression4TagXml(sExpressionIn, KernelExpressionIniHandlerZZZ.sTAG_NAME);
			if(!bHasZ) {
				if(!(objEnumFlagset == EnumSetMappedTestCaseFlagsetTypeZZZ.UNEXPRESSED)) {
					break main;
				}
			}
			
			bReturn = true;
		}//end main:
		return bReturn;
	}
	
	public static boolean assertObjectValue_Solver_OnParseFirstVector(ITagBasicChildZZZ objExpressionSolver, Vector3ZZZ<String>vecValue, IEnumSetMappedTestSurroundingZZZ objEnumSurrounding, boolean bUseExpressionGeneral, boolean bUseParser, boolean bUseSolver, String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, String sPreIn, String sPostIn) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			String sExpression; String sExpressionSubstituted; String sExpressionSolved; String sPre; String sPost;
			String sValue; String sValueUsed;		
			ReferenceZZZ<IKernelConfigSectionEntryZZZ> objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
			IKernelConfigSectionEntryZZZ objEntry=null;
			
			String sExpressionSubstituted2Compare = null; String sExpressionSurroundedTemp = null;
			
			String sTagStartZ = "<Z>";
			String sTagEndZ = "</Z>";		
			
			sExpression = sExpressionIn;
			sExpressionSubstituted = sExpressionSubstitutedIn;
			sExpressionSolved = sExpressionSolvedIn;
			sPre = sPreIn;
			sPost = sPostIn;
				
			System.out.println(ReflectCodeZZZ.getPositionCurrent() + "sExpressionSolved='"+sExpressionSolved+"'");
			System.out.println(ReflectCodeZZZ.getPositionCurrent() + "sValue @ 0='"+(String) vecValue.get(0)+"'");
			System.out.println(ReflectCodeZZZ.getPositionCurrent() + "sValue @ 1='"+(String) vecValue.get(1)+"'");
			System.out.println(ReflectCodeZZZ.getPositionCurrent() + "sValue @ 2='"+(String) vecValue.get(2)+"'");
			if(bUseParser && bUseExpressionGeneral) {
				assertFalse(StringZZZ.isEmpty((String) vecValue.get(0))); //in der 0ten Position ist der Tag vor dem gesuchten String ODER wenn nicht geparst wurde ODER wenn der Tag nicht enthalten ist.
				assertFalse(StringZZZ.isEmpty((String) vecValue.get(1))); //in der 1ten Position ist der Tag
				assertFalse(StringZZZ.isEmpty((String) vecValue.get(2))); //in der 2ten Position ist der Tag nach dem gesuchten String	
			}else {
				assertFalse(StringZZZ.isEmpty((String) vecValue.get(0))); //in der 0ten Position ist der Tag vor dem gesuchten String ODER wenn nicht geparst wurde ODER wenn der Tag nicht enthalten ist.
				assertTrue(StringZZZ.isEmpty((String) vecValue.get(1))); //in der 1ten Position ist der Tag
				assertTrue(StringZZZ.isEmpty((String) vecValue.get(2))); //in der 2ten Position ist der Tag nach dem gesuchten String					
			}
			sValue = VectorUtilZZZ.implode(vecValue);
			assertEquals(sExpressionSubstituted, sValue); //dann sollen auch die Z-Tags drumherum nicht entfernt werden.
			
			//+++
			sExpressionSurroundedTemp = sExpressionSubstituted;
			if(bUseParser && bUseExpressionGeneral) {		
				sValue = (String) vecValue.get(1);//in der 0ten Position ist der String vor der Map, in der 3ten Position ist der String nach der Map.
				
				sExpressionSurroundedTemp = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSurroundedTemp, sTagStartZ, sTagEndZ);
				sExpressionSurroundedTemp = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSurroundedTemp, objExpressionSolver.getParentName());
				sExpressionSurroundedTemp = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSurroundedTemp, objExpressionSolver.getName());
				
				//PRE und POST umgebend entfernen!!!
				sExpressionSurroundedTemp = StringZZZ.trimLeft(sExpressionSurroundedTemp, sPre);
				sExpressionSurroundedTemp = StringZZZ.trimRight(sExpressionSurroundedTemp, sPost);
				
				assertEquals(sExpressionSurroundedTemp, sValue); //dann sollen auch die Z-Tags drumherum nicht entfernt werden.
			}else {
				sValue = (String) vecValue.get(0);//in der 0ten Position ist der String, entweder wenn der Tag nicht enthalten ist ODER der Parser (ggfs. entsprechend dem Solver) abgestellt ist
				assertEquals(sExpressionSurroundedTemp, sValue); //dann sollen auch die Z-Tags drumherum nicht entfernt werden.
			}
			assertTrue(StringZZZ.contains(sValue,sExpressionSurroundedTemp,false)); //da der Wert selbst nicht als Argument in der Methode uebergeben wurde, koennen wir nur auf Existenz im Gesamtergebnis pruefen.
			
			
			//TODOGOON20250615;//Hole den objEntry.getValue() und vergleiche mit dem Tag-Wert-selbst
			
			bReturn = true;
		}//end main:
		return bReturn;
	}
}
