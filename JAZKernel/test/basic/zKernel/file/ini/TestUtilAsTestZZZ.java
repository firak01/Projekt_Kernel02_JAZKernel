package basic.zKernel.file.ini;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedTestCaseZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedTestFlagsetZZZ;
import basic.zBasic.util.datatype.xml.XmlUtilZZZ;
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
	
	public static final String sFLAGSET_MATH_UNSOLVED="muso";
	public static final String sFLAGSET_SOLVED="so";
	public static final String sFLAGSET_PATH_UNSUBSTITUTED="pusu";
	public static final String sFLAGSET_PATH_SUBSTITUTED="psu";
	public static final String sFLAGSET_VARIABLE_UNSUBSTITUTED="vusu";
	public static final String sFLAGSET_VARIABLE_SUBSTITUTED="vsu";
	
	public static final String sFLAGSET_CALL_UNEXPRESSED="cuex";
	public static final String sFLAGSET_CALL_UNSOLVED="cus";
	public static final String sFLAGSET_JAVACALL_UNSOLVED="jcus";
	public static final String sFLAGSET_JAVACALL_SOLVED="jcs";	
	
	public static final String sCASE_PARSE="p";
	public static final String sCASE_SOLVE="s";
	public static final String sCASE_PARSE_AS_ENTRY="pae";
	public static final String sCASE_SOLVE_AS_ENTRY="sae";
	public static final String sCASE_AS_ENTRY="ae";
	
		
	
	private TestUtilAsTestZZZ() { 
		//Zum Verstecken des Konsruktors
	} //static methods only
			
	public static boolean assertFileIniEntry(IEnumSetMappedTestCaseZZZ objEnumTestCase, IEnumSetMappedTestFlagsetZZZ objEnumTestFlagset,IKernelConfigSectionEntryZZZ objEntry, String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			
			String sCaseSet = objEnumTestCase.getAbbreviation();
			
			switch(sCaseSet) {
			case sCASE_PARSE_AS_ENTRY:
			case sCASE_PARSE:{
				assertFileIniEntry_parse(objEnumTestFlagset,objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn);
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
				assertFileIniEntry_solve(objEnumTestFlagset,objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn);
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
	
	
	public static boolean assertFileIniEntry_parse(IEnumSetMappedTestFlagsetZZZ objEnumTestFlagset,IKernelConfigSectionEntryZZZ objEntry, String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			String sExpression = sExpressionIn;
			String sExpressionSubstituted = sExpressionSubstitutedIn;
			String sExpressionSolved = sExpressionSolvedIn;
			
			String sCaseSet = TestUtilAsTestZZZ.sCASE_PARSE;
			String sFlagSet = objEnumTestFlagset.getAbbreviation();

				switch(sFlagSet) {
				//Merke: Das ist keine Konstante, case EnumSetMappedTestCaseFlagsetTypeZZZ.UNEXPRESSED.getAbbreviation():
				case sFLAGSET_UNEXPRESSED:
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
					break;
				case sFLAGSET_UNSOLVED:					
					assertTrue(objEntry.isExpression()); //ohne Expression-Nutzung kein Expression Eintrag!!!
					assertTrue(objEntry.isParseCalled()); //Auch wenn die Expression nicht verarbeitet wird, dann ist doch geparsed worden....					
					assertFalse(objEntry.isSolveCalled()); //hier nur der PARSE - Fall
					assertTrue(objEntry.isPathSubstituteCalled());
					assertTrue(objEntry.isVariableSubstituteCalled());
					
					assertTrue(objEntry.isParsed()); 
					assertFalse(objEntry.isSolved()); //sollte ohne SOLVE abgebrochen worden sein
				
					
					if(sExpression.equals(sExpressionSolved)) {
						assertFalse(objEntry.isParsedChanged());						
					}else {
						assertTrue(objEntry.isParsedChanged());
					}	
					assertFalse(objEntry.isSolvedChanged()); //nur mit parse wird hier nix geaendert
										
					assertTrue(objEntry.isPathSubstituted()); //falls das entsprechende Flag gesetzt ist, unabhaengig davon, ob ein INI-PATH Ausdruck darin ist
					//if(objEntry.isPathSubstituted()){ //Kann hier eigentlich nicht getestet werden. Ggfs. wird eine Expression ohne INI-PATH uebergeben
					//	if(sExpression.equals(sExpressionSubstituted)) { 
					//		assertFalse(objEntry.isSubstitutedChanged());
					//	}else {
					//		assertFalse(objEntry.isSubstitutedChanged());
					//	}
					//}
					
					assertTrue(objEntry.isVariableSubstituted()); //falls das entsprechende Flag gesetzt ist, unabhaengig davon, ob eine INI-Variable darin ist
					//+++ Auf Werte kann man hier eigentlich nicht so abfragen, weil ggfs. keine Variablen in der Expression sind.
					//++++++++++++++++++++++++++++++++++++++++++++++++++++++++
						
					//+++ Auf Werte kann man hier eigentlich nicht so abfragen, weil ggfs. doch CRYPTED Werte in der Expression sind
					assertFalse(objEntry.isDecrypted());
					assertNull(objEntry.getValueDecrypted()); //Merke: sValue kann unterschiedlich zu dem decrypted Wert sein. Wenn etwas drumherum steht.
					
					//++++++++++++++++++++++++++++++++++++++++++++++++++++++++
					//Kann hier eigentlich nicht abgefragt werden, weil ggfs. doch ein CALL in der Expression ist.
					//assertFalse(objEntry.isCall());
					//assertFalse(objEntry.isJavaCall());
					//assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingClassname() + "'", objEntry.getCallingClassname());
					//assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingMethodname() + "'", objEntry.getCallingMethodname());
					break;
				case sFLAGSET_MATH_UNSOLVED: //Z:Formula wird ja gesolved					
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
					break;

					
				case sFLAGSET_SOLVED:
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
					if(sExpression.equals(sExpressionSubstituted)) {						
						assertFalse(objEntry.isPathSubstitutedChanged());
					}else {
						assertTrue(objEntry.isPathSubstitutedChanged());
					}	
					//++++++++++++++++++++++++++++++++++++++++++
					
					assertTrue(objEntry.isVariableSubstituted());
					//+++ kann man hier doch auch eigentlich nicht so abfragen					
					
					//++++++++++++++++++++++++++++++++++++++++++++++++++++++++
												
					assertFalse(objEntry.isDecrypted());
					assertNull(objEntry.getValueDecrypted()); //Merke: sValue kann unterschiedlich zu dem decrypted Wert sein. Wenn etwas drumherum steht.				
					break;
				case sFLAGSET_PATH_UNSUBSTITUTED:
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
					break;
										
				case sFLAGSET_PATH_SUBSTITUTED:
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
					if(sExpression.equals(sExpressionSubstituted)) {  //+++ kann man hier doch auch eigentlich nicht so abfragen						
						assertFalse(objEntry.isPathSubstitutedChanged());
					}else {
						assertTrue(objEntry.isPathSubstitutedChanged());
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
					break;
					
				case sFLAGSET_CALL_UNSOLVED:
					
					assertTrue(objEntry.isParseCalled()); 					
					assertFalse(objEntry.isSolveCalled()); //hier nur .parse() - Fall
					assertFalse(objEntry.isCallSolveCalled()); //nur PARSE - Fall
					assertFalse(objEntry.isJavaCallSolveCalled());
					assertTrue(objEntry.isExpression());
					
					assertTrue(objEntry.isParsed());
					assertFalse(objEntry.isSolved()); //Der konkrete Solver ist nicht involviert
					
					assertFalse(objEntry.isDecrypted());
					assertNull(objEntry.getValueDecrypted()); //Merke: sValue kann unterschiedlich zu dem decrypted Wert sein. Wenn etwas drumherum steht.
					
					assertTrue(objEntry.isCall());		//Beim Parsen wird das festgestellt
					//kann ja theoretisch auch ein anderrer Call sein... assertTrue(objEntry.isJavaCall());	//Beim Parsen wird das festgestellt
					assertNull(objEntry.getCallingClassname());
					assertNull(objEntry.getCallingMethodname());
					break;
					
				case sFLAGSET_JAVACALL_SOLVED:
					
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
					break;
					
				case sFLAGSET_JAVACALL_UNSOLVED:
					
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
					assertTrue(objEntry.isJavaCall());	//Beim Parsen wird das festgestellt
					assertNull(objEntry.getCallingClassname());
					assertNull(objEntry.getCallingMethodname());
					break;
					
				default:
					fail("Test Flagset '" + sFlagSet + "' im Case '"+sCaseSet+"' ist nicht definiert");
					break;
				}					
										
			bReturn = true;
		}//end main:
		return bReturn;
	}

	public static boolean assertFileIniEntry_solve(IEnumSetMappedTestFlagsetZZZ objEnumTestFlagset,IKernelConfigSectionEntryZZZ objEntry, String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn) throws ExceptionZZZ{
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
			String sExpressionSubstituted4Compare=null;
			
			
			
			switch(sFlagSet) {
			//Das ist keine Konstante, case EnumSetMappedTestCaseFlagsetTypeZZZ.UNEXPRESSED.getAbbreviation():
			case sFLAGSET_UNEXPRESSED:
				assertTrue(objEntry.isSolveCalled()); //Auch wenn die Expression nicht verarbeitet wird, dann ist doch der solve() Schritt aufgerufen worden....
				assertFalse(objEntry.isParseCalled()); //...aber, danach wurde abgebrochen
				assertFalse(objEntry.isExpression());
													
				//Keine Parser Ergebnisse (Abfrage der asserts in umgekehrter Reihenfolge ihres moeglichen Wertesetzens im Code)
				assertFalse(objEntry.isParsed()); //sollte ohne EXPRESSION abgebrochen worden sein
				assertFalse(objEntry.isPathSubstituted());				
				assertFalse(objEntry.isVariableSubstituted());
				assertFalse(objEntry.isParsedChanged());
				
				//Keine Solver Ergebnisse (Abfrage der asserts in umgekehrter Reihenfolge ihres moeglichen Wertesetzens im Code)
				assertFalse(objEntry.isSolved()); //sollte ohne EXPRESSION abgebrochen worden sein
				assertFalse(objEntry.isSolvedChanged());						
				
				//++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			
				assertFalse(objEntry.isDecrypted());
				assertNull(objEntry.getValueDecrypted()); //Merke: sValue kann unterschiedlich zu dem decrypted Wert sein. Wenn etwas drumherum steht.
				
				assertFalse(objEntry.isCall());
				assertFalse(objEntry.isJavaCall());
				assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingClassname() + "'", objEntry.getCallingClassname());
				assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingMethodname() + "'", objEntry.getCallingMethodname());
				break;	
			case sFLAGSET_UNPARSED:
				//wie unsolved, aber hier gibt es auch keine Parser - Ergebnisse
				assertTrue(objEntry.isExpression()); //ohne Expression-Nutzung kein Expression Eintrag!!!
				assertTrue(objEntry.isParseCalled()); //Auch wenn die Expression nicht verarbeitet wird, dann ist doch geparsed worden....					
				assertTrue(objEntry.isSolveCalled());
				
				//+++++++++++++++++++++++++++++++++++++++
				//Keine Parser Ergebnisse (Abfrage der asserts in umgekehrter Reihenfolge ihres moeglichen Wertesetzens im Code)
				assertFalse(objEntry.isParsed());
				assertFalse(objEntry.isPathSubstituted()); //falls das entsprechende Flag gesetzt ist, unabhaengig davon, ob ein INI-PATH Ausdruck darin ist
				assertFalse(objEntry.isPathSubstituteCalled());
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
				assertFalse(objEntry.isSolved()); //sollte ohne SOLVE abgebrochen worden sein
				assertFalse(objEntry.isSolvedChanged());//schliesslich wird hier nix gesolved()!!!
														
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
				
				break;		
			case sFLAGSET_UNSOLVED:					
				assertTrue(objEntry.isExpression()); //ohne Expression-Nutzung kein Expression Eintrag!!!
				assertTrue(objEntry.isParseCalled()); //Auch wenn die Expression nicht verarbeitet wird, dann ist doch geparsed worden....					
				assertTrue(objEntry.isSolveCalled());
				
				//+++++++++++++++++++++++++++++++++++++++++++++++++++++
				//Parser Ergebnisse
				assertTrue(objEntry.isParsed());
				assertTrue(objEntry.isPathSubstituteCalled());
				assertTrue(objEntry.isVariableSubstituteCalled());
				assertTrue(objEntry.isPathSubstituted()); //falls das entsprechende Flag gesetzt ist, unabhaengig davon, ob ein INI-PATH Ausdruck darin ist
			
				//if(objEntry.isPathSubstituted()){ //Kann hier eigentlich nicht getestet werden. Ggfs. wird eine Expression ohne INI-PATH uebergeben
				//	if(sExpression.equals(sExpressionSubstituted)) { 
				//		assertFalse(objEntry.isSubstitutedChanged());
				//	}else {
				//		assertFalse(objEntry.isSubstitutedChanged());
				//	}
				//}
				
				assertTrue(objEntry.isVariableSubstituted()); //falls das entsprechende Flag gesetzt ist, unabhaengig davon, ob eine INI-Variable darin ist
				//+++ Auf Werte kann man hier eigentlich nicht so abfragen, weil ggfs. keine Variablen in der Expression sind.
				
				//+++++++++++++++++++++++++++++++++++++++++++++
				//Keine Solver Ergebnisse
				assertFalse(objEntry.isSolved()); //sollte ohne SOLVE abgebrochen worden sein
				assertFalse(objEntry.isSolvedChanged());//schliesslich wird hier nix gesolved()!!!
				
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
				
				break;
			case sFLAGSET_UNPARSED_UNSOLVED:
				//wie unsolved, aber hier gibt es auch keine Parser - Ergebnisse
				assertTrue(objEntry.isExpression()); //ohne Expression-Nutzung kein Expression Eintrag!!!
				assertTrue(objEntry.isParseCalled()); //Auch wenn die Expression nicht verarbeitet wird, dann ist doch geparsed worden....					
				assertTrue(objEntry.isSolveCalled());
				
				//+++++++++++++++++++++++++++++++++++++++
				//Keine Parser Ergebnisse (Abfrage der asserts in umgekehrter Reihenfolge ihres moeglichen Wertesetzens im Code)
				assertFalse(objEntry.isParsed());
				assertFalse(objEntry.isPathSubstituted()); //falls das entsprechende Flag gesetzt ist, unabhaengig davon, ob ein INI-PATH Ausdruck darin ist
				assertFalse(objEntry.isPathSubstituteCalled());
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
				assertFalse(objEntry.isSolved()); //sollte ohne SOLVE abgebrochen worden sein
				assertFalse(objEntry.isSolvedChanged());//schliesslich wird hier nix gesolved()!!!
														
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
				
				break;										
			case sFLAGSET_MATH_UNSOLVED:	//Es wird z:Formula gesolved!!!				
				assertTrue(objEntry.isExpression()); //ohne Expression-Nutzung kein Expression Eintrag!!!
				assertTrue(objEntry.isParseCalled()); //Auch wenn die Expression nicht verarbeitet wird, dann ist doch geparsed worden....					
				assertTrue(objEntry.isSolveCalled());

				assertTrue(objEntry.isParsed()); 
				assertTrue(objEntry.isSolved()); //sollte auch SOLVE_MATH wird solve ausgefuehrt
									
				//Merke: .isParsedChange() laesst sich hier nicht ermitteln.
				if(sExpression.equals(sExpressionSubstituted)) {						
					assertFalse(objEntry.isPathSubstituted());
				}else {
					assertTrue(objEntry.isPathSubstituted());
				}	
				if(sExpressionSolved.equals(sExpressionSubstituted)|sExpressionSolved.equals(sExpression)) {
					assertFalse(objEntry.isSolvedChanged()); //es wurden ja die Z-Tags drumherum NICHT entfernt also "veraendert"
				}else {
					assertTrue(objEntry.isSolvedChanged()); //es werden ja die Z-Tags drumherum ZUMINDEST entfernt also "veraendert"
				}
														
				assertFalse(objEntry.isDecrypted());
				assertNull(objEntry.getValueDecrypted()); //Merke: sValue kann unterschiedlich zu dem decrypted Wert sein. Wenn etwas drumherum steht.
				
				assertFalse(objEntry.isCall());
				assertFalse(objEntry.isJavaCall());
				assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingClassname() + "'", objEntry.getCallingClassname());
				assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingMethodname() + "'", objEntry.getCallingMethodname());	
				break;
				
			case sFLAGSET_SOLVED:				
				assertTrue(objEntry.isExpression()); //ohne Expression-Nutzung kein Expression Eintrag!!!
									
				//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
				assertTrue(objEntry.isParseCalled()); //Auch wenn die Expression nicht verarbeitet wird, dann ist doch geparsed worden....
				//Merke: .isParsedChange() laesst sich hier nicht ermitteln.
//				if(sExpression.equals(sExpressionSolved)) {
//					assertFalse(objEntry.isParsedChanged());						
//				}else {
//					assertTrue(objEntry.isParsedChanged());
//				}
				assertTrue(objEntry.isParsed());
				
				//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
				assertTrue(objEntry.isPathSubstituteCalled());																
				if(sExpression.equals(sExpressionSubstituted)) {						
					assertFalse(objEntry.isPathSubstitutedChanged());
				}else {
					assertTrue(objEntry.isPathSubstitutedChanged());
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
				if(sExpressionSolved.equals(sExpressionSubstituted)|sExpressionSolved.equals(sExpression)) {
					assertFalse(objEntry.isSolvedChanged()); //es wurden ja die Z-Tags drumherum NICHT entfernt also "veraendert"
				}else {
					assertTrue(objEntry.isSolvedChanged()); //es werden ja die Z-Tags drumherum ZUMINDEST entfernt also "veraendert"
				}				
				assertTrue(objEntry.isSolved());
				//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
				
				assertFalse(objEntry.isDecrypted());
				assertNull(objEntry.getValueDecrypted()); //Merke: sValue kann unterschiedlich zu dem decrypted Wert sein. Wenn etwas drumherum steht.
				break;
			
			case sFLAGSET_PATH_UNSUBSTITUTED:				
				assertTrue(objEntry.isExpression()); //ohne Expression-Nutzung kein Expression Eintrag!!!
									
				//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
				assertTrue(objEntry.isParseCalled()); //Auch wenn die Expression nicht verarbeitet wird, dann ist doch geparsed worden....
				//Merke: .isParsedChange() laesst sich hier nicht ermitteln.
//				if(sExpression.equals(sExpressionSolved)) {
//					assertFalse(objEntry.isParsedChanged());						
//				}else {
//					assertTrue(objEntry.isParsedChanged());
//				}
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
				if(sExpressionSolved.equals(sExpressionSubstituted)|sExpressionSolved.equals(sExpression)) {
					assertFalse(objEntry.isSolvedChanged()); //es wurden ja die Z-Tags drumherum NICHT entfernt also "veraendert"
				}else {
					assertTrue(objEntry.isSolvedChanged()); //es werden ja die Z-Tags drumherum ZUMINDEST entfernt also "veraendert"
				}				
				assertTrue(objEntry.isSolved());
				//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
				
				assertFalse(objEntry.isDecrypted());
				assertNull(objEntry.getValueDecrypted()); //Merke: sValue kann unterschiedlich zu dem decrypted Wert sein. Wenn etwas drumherum steht.
				break;

			case sFLAGSET_PATH_SUBSTITUTED:
				assertTrue(objEntry.isExpression()); //ohne Expression-Nutzung kein Expression Eintrag!!!
				assertTrue(objEntry.isParseCalled()); //Auch wenn die Expression nicht verarbeitet wird, dann ist doch geparsed worden....					
				assertTrue(objEntry.isSolveCalled()); //Aufgerufen wurde der solveCall ja...
				
				assertTrue(objEntry.isParsed()); 
				assertTrue(objEntry.isSolved());
				
				
				//Merke: .isParsedChange() laesst sich hier nicht ermitteln.
				if(sExpression.equals(sExpressionSubstituted)) {						
					assertFalse(objEntry.isPathSubstituted());
				}else {
					assertTrue(objEntry.isPathSubstituted());
				}					
				if(sExpressionSolved.equals(sExpressionSubstituted)|sExpressionSolved.equals(sExpression)) {
					assertFalse(objEntry.isSolvedChanged()); //es wurden ja die Z-Tags drumherum NICHT entfernt also "veraendert"
				}else {
					assertTrue(objEntry.isSolvedChanged()); //es werden ja die Z-Tags drumherum ZUMINDEST entfernt also "veraendert"
				}																								
				
				//+++ kann man hier doch auch eigentlich nicht so abfragen					
				assertFalse(objEntry.isVariableSubstituted());
				//++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			
				assertFalse(objEntry.isDecrypted());
				assertNull(objEntry.getValueDecrypted()); //Merke: sValue kann unterschiedlich zu dem decrypted Wert sein. Wenn etwas drumherum steht.
				break;
				
			case sFLAGSET_CALL_UNSOLVED:					
				assertTrue(objEntry.isExpression()); //ohne Expression-Nutzung kein Expression Eintrag!!!
				
				//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
				assertTrue(objEntry.isParseCalled()); //Auch wenn die Expression nicht verarbeitet wird, dann ist doch geparsed worden....
				//Merke: .isParsedChange() laesst sich hier nicht ermitteln.
	//			if(sExpression.equals(sExpressionSolved)) {
	//				assertFalse(objEntry.isParsedChanged());						
	//			}else {
	//				assertTrue(objEntry.isParsedChanged());
	//			}
				assertTrue(objEntry.isParsed());
				
				//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
				assertTrue(objEntry.isPathSubstituteCalled());						
				if(objEntry.isPathSubstituted()){ 
					if(sExpression.equals(sExpressionSubstituted)) { 
						assertFalse(objEntry.isPathSubstitutedChanged());
					}else {
						assertTrue(objEntry.isPathSubstitutedChanged());
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
				sExpressionSubstituted4Compare = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
				if(sExpressionSolved.equals(sExpressionSubstituted4Compare)) {
					assertFalse(objEntry.isSolvedChanged()); 
				}else {
					assertTrue(objEntry.isSolvedChanged());
				}				
				assertTrue(objEntry.isSolved());
				//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++				
				//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
				//Frueher: Ist der JavaCall-SOLVER deaktiviert, wird nun nicht der CallSolver aufgerufen, also das Kennzeichen nicht gesetzt
				//Jetzt 20250311: Durch die zu ueberschreibende Methode aus ISolveUserZZZ .updateValueSolveCalled(...) werden alle relevanten Kennzeichen gesetzt
				//Generische Problematik. Stichwort "Elterntag" TODOGOON20250308; TICKET20250308; Diese Testutility wird auch von KernelJavaCallIniSolverZZZTest aufgerufen.
				//ABER: Wenn der Solver generel aus gestellt wird, dann wird nix dahinter aufgerufen.
				assertTrue(objEntry.isCall());		//Beim Parsen wird das festgestellt
				assertFalse(objEntry.isCallSolveCalled());
				assertFalse(objEntry.isCallSolved());					

				//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
				//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
				assertTrue(objEntry.isJavaCall());            ///Beim Parsen wird das festgestellt
				assertFalse(objEntry.isJavaCallSolveCalled()); //OHNE Call-Solver Aufruf, keinen JavaCallSolver aufruf.
				assertFalse(objEntry.isJavaCallSolved());      //Der konkrete JAVACALL-Solver ist duch Flags deaktiviert, er wird zwar aufgerufen, aber nicht ausgefuehrt
				assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingClassname() + "'", objEntry.getCallingClassname());
				assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingMethodname() + "'", objEntry.getCallingMethodname());
				//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
								
				assertFalse(objEntry.isDecrypted());
				assertNull(objEntry.getValueDecrypted()); //Merke: sValue kann unterschiedlich zu dem decrypted Wert sein. Wenn etwas drumherum steht.
				break;

			
			case sFLAGSET_JAVACALL_SOLVED:
				assertTrue(objEntry.isExpression()); //ohne Expression-Nutzung kein Expression Eintrag!!!
				assertTrue(objEntry.isParseCalled()); //Auch wenn die Expression nicht verarbeitet wird, dann ist doch geparsed worden....					
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
				if(sExpressionSolved.equals(sExpressionSubstituted)|sExpressionSolved.equals(sExpression)) {
					assertFalse(objEntry.isSolvedChanged()); //es wurden ja die Z-Tags drumherum NICHT entfernt also "veraendert"
				}else {
					assertTrue(objEntry.isSolvedChanged()); //es werden ja die Z-Tags drumherum ZUMINDEST entfernt also "veraendert"
				}
				assertTrue(objEntry.isSolved());
				//+++++++++++++++++++++++++
				//+++++++++++++++++++++++++
				assertTrue(objEntry.isCallSolveCalled());
				assertTrue(objEntry.isCallSolved());//Der konkrete Solver ist nicht involviert
				//+++++++++++++++++++++++++
				//+++++++++++++++++++++++++
				assertTrue(objEntry.isJavaCallSolveCalled()); //trotz JAVACALL-Unsolved Flag wird der JAVACALL-Solver durchaus aufgerufen
				assertTrue(objEntry.isJavaCallSolved());//Der konkrete Solver ist nicht involviert
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
				break;

			case sFLAGSET_JAVACALL_UNSOLVED:
				assertTrue(objEntry.isExpression()); //ohne Expression-Nutzung kein Expression Eintrag!!!
				
				//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
				assertTrue(objEntry.isParseCalled()); //Auch wenn die Expression nicht verarbeitet wird, dann ist doch geparsed worden....
				//Merke: .isParsedChange() laesst sich hier nicht ermitteln.
	//			if(sExpression.equals(sExpressionSolved)) {
	//				assertFalse(objEntry.isParsedChanged());						
	//			}else {
	//				assertTrue(objEntry.isParsedChanged());
	//			}
				assertTrue(objEntry.isParsed());
				
				//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
				assertTrue(objEntry.isPathSubstituteCalled());						
				if(objEntry.isPathSubstituted()){ 
					if(sExpression.equals(sExpressionSubstituted)) { 
						assertFalse(objEntry.isPathSubstitutedChanged());
					}else {
						assertTrue(objEntry.isPathSubstitutedChanged());
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
				sExpressionSubstituted4Compare = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
				if(sExpressionSolved.equals(sExpressionSubstituted4Compare)) {
					assertFalse(objEntry.isSolvedChanged()); 
				}else {
					assertTrue(objEntry.isSolvedChanged());
				}				
				assertTrue(objEntry.isSolved());
				//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++				
				//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
				//Frueher: Ist der JavaCall-SOLVER deaktiviert, wird nun nicht der CallSolver aufgerufen, also das Kennzeichen nicht gesetzt
				//Jetzt 20250311: Durch die zu ueberschreibende Methode aus ISolveUserZZZ .updateValueSolveCalled(...) werden alle relevanten Kennzeichen gesetzt
				//Generische Problematik. Stichwort "Elterntag" TODOGOON20250308; TICKET20250308; Diese Testutility wird auch von KernelJavaCallIniSolverZZZTest aufgerufen.
				//ABER: Wenn der Solver generel aus gestellt wird, dann wird nix dahinter aufgerufen.
				assertTrue(objEntry.isCall());		//Beim Parsen wird das festgestellt
				assertTrue(objEntry.isCallSolveCalled());
				assertTrue(objEntry.isCallSolved()); //trotz Deaktivierung des Solvers mit JAVACALL-Unsolved Flag wird der CALL-Solver ausgefuehrt					

				//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
				//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
				assertTrue(objEntry.isJavaCall());            ///Beim Parsen wird das festgestellt
				assertFalse(objEntry.isJavaCallSolveCalled()); //Der Aufruf wird vom CALL-Handler vermieden, da durch Flag deaktiviert.
				assertFalse(objEntry.isJavaCallSolved());      //Der konkrete JAVACALL-Solver ist duch Flags deaktiviert, er wird zwar aufgerufen, aber nicht ausgefuehrt
				assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingClassname() + "'", objEntry.getCallingClassname());
				assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingMethodname() + "'", objEntry.getCallingMethodname());
				//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
								
				assertFalse(objEntry.isDecrypted());
				assertNull(objEntry.getValueDecrypted()); //Merke: sValue kann unterschiedlich zu dem decrypted Wert sein. Wenn etwas drumherum steht.
				break;
				
			default:
				fail("Test Flagset '" + sFlagSet + "' im Case '"+sCaseSet+"' ist nicht definiert");
				break;
			}
									
			bReturn = true;
		}//end main:
		return bReturn;
	}

}
