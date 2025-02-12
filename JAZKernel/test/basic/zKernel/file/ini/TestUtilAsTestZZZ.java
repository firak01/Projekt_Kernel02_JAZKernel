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
			String sExpression = sExpressionIn;
			String sExpressionSubstituted = sExpressionSubstitutedIn;
			String sExpressionSolved = sExpressionSolvedIn;
			
			
			String sCaseset = objEnumTestCase.getAbbreviation();
			String sFlagset = objEnumTestFlagset.getAbbreviation();
			
			switch(sCaseset) {
			case sCASE_PARSE:{							
				//
				switch(sFlagset) {
				//Das ist keine Konstante, case EnumSetMappedTestCaseFlagsetTypeZZZ.UNEXPRESSED.getAbbreviation():
				case sFLAGSET_UNEXPRESSED:
					assertFalse(objEntry.isExpression()); //ohne Expression-Nutzung kein Expression Eintrag!!!
					assertTrue(objEntry.isParseCalled()); //Auch wenn die Expression nicht verarbeitet wird, dann ist doch geparsed worden....					
					assertFalse(objEntry.isSolveCalled()); //Der Solve-Schritt wurde ja NICHT gemacht.
					
				
					assertFalse(objEntry.isParsedChanged());						
					assertFalse(objEntry.isSubstitutedChanged());
					assertFalse(objEntry.isSolvedChanged()); //Ohne Expression Behandlung wird auch nichts geaendert.
				
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

					if(sExpression.equals(sExpressionSolved)) {
						assertFalse(objEntry.isParsedChanged());						
					}else {
						assertTrue(objEntry.isParsedChanged());
					}	
					if(sExpression.equals(sExpressionSubstituted)) {						
						assertFalse(objEntry.isPathSubstituted());
					}else {
						assertFalse(objEntry.isPathSubstituted());
					}
					assertFalse(objEntry.isSolvedChanged()); //Ohne Expression Behandlung wird auch nichts geaendert.
				
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

					if(sExpression.equals(sExpressionSolved)) {
						assertFalse(objEntry.isParsedChanged());						
					}else {
						assertTrue(objEntry.isParsedChanged());
					}	
					if(sExpression.equals(sExpressionSubstituted)) {						
						assertFalse(objEntry.isPathSubstituted());
					}else {
						assertFalse(objEntry.isPathSubstituted());
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

					
				case sFLAGSET_SOLVED:
					assertTrue(objEntry.isExpression()); //ohne Expression-Nutzung kein Expression Eintrag!!!
					assertTrue(objEntry.isParseCalled()); //Auch wenn die Expression nicht verarbeitet wird, dann ist doch geparsed worden....					
					assertFalse(objEntry.isSolveCalled());
				
					if(sExpression.equals(sExpressionSolved)) {
						assertFalse(objEntry.isParsedChanged()); 
					}else {
						assertTrue(objEntry.isParsedChanged()); 
					}
					if(sExpression.equals(sExpressionSubstituted)) {						
						assertFalse(objEntry.isPathSubstituted());
					}else {
						assertFalse(objEntry.isPathSubstituted());
					}	
					if(sExpressionSolved.equals(sExpressionSubstituted)|sExpressionSolved.equals(sExpression)) {
						assertFalse(objEntry.isSolvedChanged()); //es wurden ja die Z-Tags drumherum NICHT entfernt also "veraendert"
					}else {
						assertTrue(objEntry.isSolvedChanged()); //es werden ja die Z-Tags drumherum ZUMINDEST entfernt also "veraendert"
					}
//					assertFalse(objEntry.isSolvedChanged()); //es wurden ja die Z-Tags drumherum NICHT entfernt also "veraendert"
									
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
								
					sExpressionSolved = sExpressionSolvedIn;
					if(sExpression.equals(sExpressionSolved)) {
						assertFalse(objEntry.isParsedChanged());						
					}else {
						assertTrue(objEntry.isParsedChanged());
					}	
					if(sExpressionSolved.equals(sExpressionSubstituted)|sExpressionSolved.equals(sExpression)) {
						assertFalse(objEntry.isSolvedChanged()); //es wurden ja die Z-Tags drumherum NICHT entfernt also "veraendert"
					}else {
						assertTrue(objEntry.isSolvedChanged()); //es werden ja die Z-Tags drumherum ZUMINDEST entfernt also "veraendert"
					}
					//assertFalse(objEntry.isSolvedChanged()); //es wird ja nix gemacht, also "unveraendert" 
					
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
				
				default:
					fail("Test Flagset '" + sFlagset + "' im Case '"+sCaseset+"' ist nicht definiert");
					break;
				}	
				break;
			}	
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			case sCASE_SOLVE:{	
				//
				switch(sFlagset) {
				//Das ist keine Konstante, case EnumSetMappedTestCaseFlagsetTypeZZZ.UNEXPRESSED.getAbbreviation():
				case sFLAGSET_UNEXPRESSED:
					assertTrue(objEntry.isParseCalled()); //Auch wenn die Expression nicht verarbeitet wird, dann ist doch geparsed worden....
					assertTrue(objEntry.isSolveCalled()); //dito mit solve(). Der Solve-Schritt wurde gemacht.
					assertFalse(objEntry.isExpression());
											
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
					
					//Merke: .isParsedChange() laesst sich hier nicht ermitteln.
					assertFalse(objEntry.isPathSubstituted());
					if(sExpressionSolved.equals(sExpressionSubstituted)|sExpressionSolved.equals(sExpression)) {
						assertFalse(objEntry.isSolvedChanged()); //es wurden ja die Z-Tags drumherum NICHT entfernt also "veraendert"
					}else {
						assertTrue(objEntry.isSolvedChanged()); //es werden ja die Z-Tags drumherum ZUMINDEST entfernt also "veraendert"
					}																							
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
					assertTrue(objEntry.isSolveCalled()); //Aufgerufen wurde der solveCall ja...
					
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
					assertFalse(objEntry.isVariableSubstituted()); //dieses ggfs. als extra Test machen.
				
				
					assertFalse(objEntry.isDecrypted());
					assertNull(objEntry.getValueDecrypted()); //Merke: sValue kann unterschiedlich zu dem decrypted Wert sein. Wenn etwas drumherum steht.
					
					assertFalse(objEntry.isCall());
					assertFalse(objEntry.isJavaCall());
					assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingClassname() + "'", objEntry.getCallingClassname());
					assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingMethodname() + "'", objEntry.getCallingMethodname());
					break;
					
				default:
					fail("Test Flagset '" + sFlagset + "' im Case '"+sCaseset+"' ist nicht definiert");
					break;
				}
				break;
			}
			default:
				fail("Test Caseset '" + sCaseset + "' ist nicht definiert");
				break;
			}
						
			bReturn = true;
		}//end main:
		return bReturn;
	}
}
