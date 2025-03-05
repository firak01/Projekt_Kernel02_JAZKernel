package basic.zKernel.file.ini;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedTestCaseZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedTestFlagsetZZZ;
import basic.zBasic.util.crypt.code.ICharacterPoolUserZZZ;
import basic.zBasic.util.crypt.code.IROTUserZZZ;
import basic.zBasic.util.crypt.code.ROT13ZZZ;
import basic.zBasic.util.crypt.code.ROTnnZZZ;
import basic.zBasic.util.crypt.code.ROTnumericZZZ;
import basic.zBasic.util.datatype.character.CharacterExtendedZZZ;
import basic.zBasic.util.file.FileEasyZZZ;
import basic.zBasic.util.stream.IStreamZZZ;
import basic.zBasic.util.stream.StreamZZZ;
import basic.zKernel.IKernelConfigSectionEntryZZZ;
import junit.framework.TestCase;

public class TestUtilAsTestZZZ extends TestCase{
	
	//Diese Konstanten sind notwendig, um in einer switch...case Abfrage genutzt zu werden.
	//Das funktioniert nämlich nicht, da es keine Konstante ist   case EnumSetMappedTestCaseFlagsetTypeZZZ.UNEXPRESSED.getAbbreviation():
	
	//Diese Konstanten werden in einem enum verwendet. 
	//Diese finale Konstanten sind notwendig, um in einer switch...case Abfrage genutzt zu werden.
	//Das funktioniert nämlich nicht, da es keine finale Konstante ist   case EnumSetMappedTestCaseFlagsetTypeZZZ.UNEXPRESSED.getAbbreviation():	
	public static final String sFLAGSET_UNEXPRESSED="uex";
	public static final String sFLAGSET_UNSOLVED="uso";
	public static final String sFLAGSET_MATH_UNSOLVED="muso";
	public static final String sFLAGSET_SOLVED="so";
	public static final String sFLAGSET_UNSUBSTITUTED="usu";
	public static final String sFLAGSET_SUBSTITUTED="su";
	
	public static final String sFLAGSET_CALL_UNEXPRESSED="cuex";
	public static final String sFLAGSET_CALL_UNSOLVED="cus";
	public static final String sFLAGSET_JAVACALL_UNSOLVED="jcus";
	public static final String sFLAGSET_JAVACALL_SOLVED="jcs";
	
	
	
	public static final String sCASE_PARSE="p";
	public static final String sCASE_SOLVE="s";
	public static final String sCASE_PARSE_AS_ENTRY="pae";
	public static final String sCASE_SOLVE_AS_ENTRY="sae";
	
		
	
	private TestUtilAsTestZZZ() { 
		//Zum Verstecken des Konsruktors
	} //static methods only
			
	public static boolean assertFileIniEntry(IEnumSetMappedTestCaseZZZ objEnumTestCase, IEnumSetMappedTestFlagsetZZZ objEnumTestFlagset,IKernelConfigSectionEntryZZZ objEntry, String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			
			String sCaseSet = objEnumTestCase.getAbbreviation();
			
			switch(sCaseSet) {
			case sCASE_PARSE:{
				assertFileIniEntry_parse(objEnumTestFlagset,objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn);
				break;
			}	
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
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
				//Das ist keine Konstante, case EnumSetMappedTestCaseFlagsetTypeZZZ.UNEXPRESSED.getAbbreviation():
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
					assertFalse(objEntry.isSolveCalled()); //Aufgerufen wurde der solveCall ja...
					assertFalse(objEntry.isPathSubstituted());
					
					assertTrue(objEntry.isParsed()); 
					assertFalse(objEntry.isSolved()); //sollte ohne SOLVE abgebrochen worden sein
				
					
					if(sExpression.equals(sExpressionSolved)) {
						assertFalse(objEntry.isParsedChanged());						
					}else {
						assertTrue(objEntry.isParsedChanged());
					}	
					assertFalse(objEntry.isSolvedChanged()); //nur mit parse wird hier nix geaendert
					
					if(sExpression.equals(sExpressionSubstituted)) {
						assertFalse(objEntry.isSubstitutedChanged());

					}else {
						assertFalse(objEntry.isSubstitutedChanged());
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
					assertTrue(objEntry.isExpression()); //ohne Expression-Nutzung kein Expression Eintrag!!!
					assertTrue(objEntry.isParseCalled()); //Auch wenn die Expression nicht verarbeitet wird, dann ist doch geparsed worden....					
					assertFalse(objEntry.isSolveCalled());
				
					assertTrue(objEntry.isParsed()); 
					assertTrue(objEntry.isSolved());
					
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
				case sFLAGSET_UNSUBSTITUTED:
					assertTrue(objEntry.isExpression()); //ohne Expression-Nutzung kein Expression Eintrag!!!
					assertTrue(objEntry.isParseCalled()); //Auch wenn die Expression nicht verarbeitet wird, dann ist doch geparsed worden....					
					assertFalse(objEntry.isSolveCalled()); //Der Solve-Schritt wurde nicht gemacht.
								
					assertTrue(objEntry.isParsed()); 
					assertFalse(objEntry.isSolved());

					
					sExpressionSolved = sExpressionSolvedIn;
					if(sExpression.equals(sExpressionSolved)) {
						assertFalse(objEntry.isParsedChanged());						
					}else {
						assertTrue(objEntry.isParsedChanged());
					}	
					assertFalse(objEntry.isSolvedChanged()); //nur mit parse wird hier nix geaendert
					
					assertFalse(objEntry.isPathSubstituted());
					assertFalse(objEntry.isVariableSubstituted());
															
					assertFalse(objEntry.isDecrypted());
					assertNull(objEntry.getValueDecrypted()); //Merke: sValue kann unterschiedlich zu dem decrypted Wert sein. Wenn etwas drumherum steht.
					
					assertFalse(objEntry.isCall());
					assertFalse(objEntry.isJavaCall());
					assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingClassname() + "'", objEntry.getCallingClassname());
					assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingMethodname() + "'", objEntry.getCallingMethodname());
					
					break;
				case sFLAGSET_SUBSTITUTED:				
					assertTrue(objEntry.isExpression()); //ohne Expression-Nutzung kein Expression Eintrag!!!
					assertTrue(objEntry.isParseCalled()); //Auch wenn die Expression nicht verarbeitet wird, dann ist doch geparsed worden....					
					assertFalse(objEntry.isSolveCalled()); //Der Solve-Schritt wurde nicht gemacht.
					
					sExpressionSolved = sExpressionSolvedIn;
					if(sExpression.equals(sExpressionSolved)) {
						assertFalse(objEntry.isParsedChanged());						
					}else {
						assertTrue(objEntry.isParsedChanged());
					}						
					if(sExpression.equals(sExpressionSubstituted)) {						
						assertFalse(objEntry.isPathSubstituted());
					}else {
						assertTrue(objEntry.isPathSubstituted());
					}					
					assertFalse(objEntry.isSolvedChanged()); //nur mit parse wird hier nix geaendert									 
																
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
				
				case sFLAGSET_CALL_UNSOLVED:
					
					assertTrue(objEntry.isParseCalled()); 					
					assertFalse(objEntry.isSolveCalled()); //Der konkrete Solver ist nicht involviert
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
					assertFalse(objEntry.isSolveCalled()); //Der konkrete Solver ist nicht involviert
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
					assertFalse(objEntry.isSolveCalled()); //Der konkrete Solver ist nicht involviert
					assertTrue(objEntry.isExpression());
					
					assertFalse(objEntry.isSolved()); //Der konkrete Solver ist nicht involviert
					
					assertFalse(objEntry.isDecrypted());
					assertNull(objEntry.getValueDecrypted()); 				
					
					assertTrue(objEntry.isCall());		//Beim Parsen wird das festgestellt
					assertFalse(objEntry.isJavaCall());	//weil der Solver nicht betrachtet wird, kommt da nix raus
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
			
			
				switch(sFlagSet) {
				//Das ist keine Konstante, case EnumSetMappedTestCaseFlagsetTypeZZZ.UNEXPRESSED.getAbbreviation():
				case sFLAGSET_UNEXPRESSED:
					assertTrue(objEntry.isParseCalled()); //Auch wenn die Expression nicht verarbeitet wird, dann ist doch geparsed worden....
					assertTrue(objEntry.isSolveCalled()); //dito mit solve(). Der Solve-Schritt wurde gemacht.
					assertFalse(objEntry.isExpression());
						
					assertFalse(objEntry.isParsed()); //sollte ohne EXPRESSION abgebrochen worden sein
					assertFalse(objEntry.isSolved()); //sollte ohne EXPRESSION abgebrochen worden sein

					
					assertFalse(objEntry.isParsedChanged());
					assertFalse(objEntry.isPathSubstituted());
					assertFalse(objEntry.isSolvedChanged());						
					
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
				case sFLAGSET_UNSOLVED:					
					assertTrue(objEntry.isExpression()); //ohne Expression-Nutzung kein Expression Eintrag!!!
					assertTrue(objEntry.isParseCalled()); //Auch wenn die Expression nicht verarbeitet wird, dann ist doch geparsed worden....					
					assertTrue(objEntry.isSolveCalled());

					assertTrue(objEntry.isParsed()); 
					assertFalse(objEntry.isSolved()); //sollte ohne SOLVE abgebrochen worden sein
					
					//Merke: .isParsedChange() laesst sich hier nicht ermitteln.
					if(sExpression.equals(sExpressionSubstituted)) {						
						assertFalse(objEntry.isPathSubstituted());
					}else {
						assertTrue(objEntry.isPathSubstituted());
					}	
					assertFalse(objEntry.isSolvedChanged());//schliesslich wird hier nix gesolved()!!!
															
					assertFalse(objEntry.isDecrypted());
					assertNull(objEntry.getValueDecrypted()); //Merke: sValue kann unterschiedlich zu dem decrypted Wert sein. Wenn etwas drumherum steht.
					
					assertFalse(objEntry.isCall());
					assertFalse(objEntry.isJavaCall());
					assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingClassname() + "'", objEntry.getCallingClassname());
					assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingMethodname() + "'", objEntry.getCallingMethodname());	
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
					
					assertFalse(objEntry.isCall());
					assertFalse(objEntry.isJavaCall());
					assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingClassname() + "'", objEntry.getCallingClassname());
					assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingMethodname() + "'", objEntry.getCallingMethodname());
					break;
				
				case sFLAGSET_UNSUBSTITUTED:
					assertTrue(objEntry.isExpression()); //ohne Expression-Nutzung kein Expression Eintrag!!!
					assertTrue(objEntry.isParseCalled()); //Auch wenn die Expression nicht verarbeitet wird, dann ist doch geparsed worden....					
					assertTrue(objEntry.isSolveCalled()); //Aufgerufen wurde der solveCall ja...
					
					assertTrue(objEntry.isParsed()); 
					assertTrue(objEntry.isSolved());
										
					//Merke: .isParsedChange() laesst sich hier nicht ermitteln.
					assertFalse(objEntry.isPathSubstituted());
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
					
					assertFalse(objEntry.isCall());
					assertFalse(objEntry.isJavaCall());
					assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingClassname() + "'", objEntry.getCallingClassname());
					assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingMethodname() + "'", objEntry.getCallingMethodname());
					break;
					
				case sFLAGSET_SUBSTITUTED:
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
					
					assertFalse(objEntry.isCall());
					assertFalse(objEntry.isJavaCall());
					assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingClassname() + "'", objEntry.getCallingClassname());
					assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingMethodname() + "'", objEntry.getCallingMethodname());
					break;
				case sFLAGSET_CALL_UNSOLVED:					
					assertTrue(objEntry.isParseCalled());
					assertTrue(objEntry.isSolveCalled()); //Der konkrete Solver ist nicht involviert
					assertTrue(objEntry.isExpression());
										
					assertTrue(objEntry.isParsed()); 							
					assertTrue(objEntry.isSolved()); //Generell wurde ein Solver ausgefuehrt
					
					assertFalse(objEntry.isCallSolved());//Der konkrete Solver ist nicht involviert
					assertFalse(objEntry.isJavaCallSolved());//Der konkrete Solver ist nicht involviert
					
					assertFalse(objEntry.isDecrypted());
					assertNull(objEntry.getValueDecrypted()); //Merke: sValue kann unterschiedlich zu dem decrypted Wert sein. Wenn etwas drumherum steht.
					
					assertTrue(objEntry.isCall());		//Beim Parsen wird das festgestellt
					assertFalse(objEntry.isJavaCall());	//weil der Solver nicht betrachtet wird, kommt da nix raus
					assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingClassname() + "'", objEntry.getCallingClassname());
					assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingMethodname() + "'", objEntry.getCallingMethodname());
					break;
				
				case sFLAGSET_JAVACALL_SOLVED:
					assertTrue(objEntry.isExpression()); //ohne Expression-Nutzung kein Expression Eintrag!!!
					assertTrue(objEntry.isParseCalled()); //Auch wenn die Expression nicht verarbeitet wird, dann ist doch geparsed worden....					
					assertTrue(objEntry.isSolveCalled()); //Aufgerufen wurde der solveCall ja...
					
					assertTrue(objEntry.isParsed()); 
					assertTrue(objEntry.isSolved()); 
					
					assertTrue(objEntry.isJavaCallSolved());//Der konkrete Solver ist nicht involviert
					
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
					
					assertTrue(objEntry.isCall());
					assertTrue(objEntry.isJavaCall());
					assertNotNull("NOT NULL erwartet. Wert ist aber '" + objEntry.getCallingClassname() + "'", objEntry.getCallingClassname());
					assertNotNull("NOT NULL erwartet. Wert ist aber '" + objEntry.getCallingMethodname() + "'", objEntry.getCallingMethodname());
					break;

				case sFLAGSET_JAVACALL_UNSOLVED:
					
					assertTrue(objEntry.isParseCalled());
					assertTrue(objEntry.isSolveCalled()); //Der konkrete Solver ist nicht involviert
					assertTrue(objEntry.isExpression());
										
					assertTrue(objEntry.isParsed()); 							
					assertTrue(objEntry.isSolved()); //Generell wurde ein Solver ausgefuehrt
					
					assertFalse(objEntry.isJavaCallSolved());//Der konkrete Solver ist nicht involviert
					
					assertFalse(objEntry.isDecrypted());
					assertNull(objEntry.getValueDecrypted()); //Merke: sValue kann unterschiedlich zu dem decrypted Wert sein. Wenn etwas drumherum steht.
					
					assertTrue(objEntry.isCall());		//Beim Parsen wird das festgestellt
					assertFalse(objEntry.isJavaCall());	//weil der Solver nicht betrachtet wird, kommt da nix raus
					assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingClassname() + "'", objEntry.getCallingClassname());
					assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingMethodname() + "'", objEntry.getCallingMethodname());
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
