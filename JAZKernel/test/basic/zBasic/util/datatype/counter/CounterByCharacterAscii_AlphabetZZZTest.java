package basic.zBasic.util.datatype.counter;

import basic.zBasic.ExceptionZZZ;
import junit.framework.TestCase;

public class CounterByCharacterAscii_AlphabetZZZTest  extends TestCase{
//	 private HashMapExtendedZZZ<String, EnumSetMappedTestTypeZZZ> hmTestGenerics = null;
	 
    protected void setUp(){
      
	//try {			
	
		//### Das spezielle Generics Testobjekt			
//		hmTestGenerics = new HashMapExtendedZZZ<String, EnumSetMappedTestTypeZZZ>();
//		
//		Set<EnumSetMappedTestTypeZZZ> allTypes = EnumSet.allOf(EnumSetMappedTestTypeZZZ.class);
//		for(EnumSetMappedTestTypeZZZ myType : allTypes) {
//			//String sType = myType.getAbbreviation();
//			String sName = myType.name();
//			hmTestGenerics.put(sName,myType);
//		}
		
	
		
	/*
	} catch (ExceptionZZZ ez) {
		fail("Method throws an exception." + ez.getMessageLast());
	} 
	*/
	
}//END setup
    
    private boolean assertCheckNullBordersAlphabet_(int iInput, String sResult){
    	boolean bReturn = false;
    	main:{
    		int iDiv = Math.abs(iInput / CounterByCharacterAscii_AlphabetZZZ.iPOSITION_MAX ); //durch abs wird also intern in ein Integer umgewandetl.... nicht nur das Weglassen des ggfs. negativen Vorzeichens.
			int iMod = iInput% CounterByCharacterAscii_AlphabetZZZ.iPOSITION_MAX;
    		
    		if(iDiv<1){
	    		if(iInput< CounterByCharacterAscii_AlphabetZZZ.iPOSITION_MIN){
		    		assertNull("Bei <'" + CounterByCharacterAscii_AlphabetZZZ.iPOSITION_MIN + "' wird NULL erwartet. Ergebnis '" + sResult + "' für " + iInput, sResult);
		    	}else if(iInput>CounterByCharacterAscii_AlphabetZZZ.iPOSITION_MAX){
		    		assertNull("Bei >'" + CounterByCharacterAscii_AlphabetZZZ.iPOSITION_MAX + "' wird NULL erwartet. Ergebnis '" + sResult + "' für " + iInput, sResult);
		    	}
    		}else{	    	
	    		if(iInput>=CounterByCharacterAscii_AlphabetZZZ.iPOSITION_MIN && iInput <= CounterByCharacterAscii_AlphabetZZZ.iPOSITION_MAX){
		    		assertNotNull("Bei >= '" + CounterByCharacterAscii_AlphabetZZZ.iPOSITION_MIN +"' und <='" + CounterByCharacterAscii_AlphabetZZZ.iPOSITION_MAX + "' wird keine NULL erwartet. Ergebnis '" + sResult + "' für " + iInput, sResult);
		    	}	 
    		}
    		bReturn=true;
    			    		    	
    	}//end main:
    	return bReturn;	    	
    }
    
    private boolean assertCheckNullBordersAlphabet_(char cInput,int iResult){
    	boolean bReturn = false;
    	main:{
    		boolean bIsValidCharacter=CounterByCharacterAscii_AlphabetZZZ.isValidCharacter(cInput);    		
    		if(iResult< CounterByCharacterAscii_AlphabetZZZ.iPOSITION_MIN){
    				assertFalse("Bei <'" + CounterByCharacterAscii_AlphabetZZZ.iPOSITION_MIN + "' wird ein ungültiges Zeichen erwartet. Ergebnis '" + iResult + "' für " + cInput, bIsValidCharacter);
    		}else if(iResult>CounterByCharacterAscii_AlphabetZZZ.iPOSITION_MAX){
    				assertFalse("Bei >'" + CounterByCharacterAscii_AlphabetZZZ.iPOSITION_MIN + "' wird ein ungültiges Zeichen erwartet. Ergebnis '" + iResult + "' für " + cInput, bIsValidCharacter);
	    	}
	    		    		
    		bReturn=true;
    			    		    	
    	}//end main:
    	return bReturn;	    	
    }
    
    private boolean assertCheckNullBordersAlphabetStrategyBased_(int iInput, String sResult){
    	boolean bReturn = false;
    	main:{
    		if(iInput< CounterByCharacterAscii_AlphabetZZZ.iPOSITION_MIN){
	    		assertNull("Bei <'" + CounterByCharacterAscii_AlphabetZZZ.iPOSITION_MIN + "' wird NULL erwartet. Ergebnis '" + sResult + "' für " + iInput, sResult);
	    	}	
    		if(iInput>=CounterByCharacterAscii_AlphabetZZZ.iPOSITION_MIN ){
	    		assertNotNull("Bei >= '" + CounterByCharacterAscii_AlphabetZZZ.iPOSITION_MIN +"' wird keine NULL erwartet. Ergebnis '" + sResult + "' für " + iInput, sResult);
	    	}	     		
    		bReturn=true;
    			    		    	
    	}//end main:
    	return bReturn;	    	
    }
    
    private boolean assertCheckReconvertAlphabet_(int itempNew, String sInputOld) throws ExceptionZZZ{
    	boolean bReturn = false;
    	
    	String stemp; boolean btemp; int itemp; int iInputOld;
    	main:{    		    		
    		//Test zurückkonvertieren
	    	stemp = CounterByCharacterAscii_AlphabetZZZ.getStringForNumber(itempNew);
	    	btemp = assertCheckNullBordersAlphabet_(itempNew,stemp);
	    	assertTrue("Fehler beim Check auf gültige Werte", btemp);
	    	assertEquals("Abweichung beim Zurückkonvertieren.",sInputOld,stemp);
	    	
	    	iInputOld = itempNew; 
	    	itemp = CounterByCharacterAscii_AlphabetZZZ.getNumberForString(stemp);
	    	btemp = assertCheckNullBordersAlphabet_(itemp,stemp);
	    	assertTrue("Fehler beim Check auf gültige Werte", btemp);
	    	assertEquals("Abweichung beim Zurück-Zurückkonvertieren.",iInputOld,itemp);
	    	
	    	bReturn = true;
    	}//end main:
    	return bReturn;    	    	
    }
    
    private boolean assertCheckReconvertAlphabetMultiple_(int itempNew, String sInputOld) throws ExceptionZZZ{
    	boolean bReturn = false;
    	
    	String stemp; boolean btemp; int itemp; int iInputOld;
    	main:{    		    		
    		//Test zurückkonvertieren
	    	stemp = CounterByCharacterAscii_AlphabetZZZ.getStringMultipleForNumber(itempNew);
	    	btemp = assertCheckNullBordersAlphabet_(itempNew,stemp);
	    	assertTrue("Fehler beim Check auf gültige Werte", btemp);
	    	assertEquals("Abweichung beim Zurückkonvertieren.",sInputOld,stemp);
	    	
	    	iInputOld = itempNew; 
	    	itemp = CounterByCharacterAscii_AlphabetZZZ.getNumberForStringMultiple(stemp);
	    	btemp = assertCheckNullBordersAlphabet_(itemp,stemp);
	    	assertTrue("Fehler beim Check auf gültige Werte", btemp);
	    	assertEquals("Abweichung beim Zurück-Zurückkonvertieren.",iInputOld,itemp);
	    	
	    	bReturn = true;
    	}//end main:
    	return bReturn;    	    	
    }
    
    /** Als Grundlage für den Konstruktor, bei dem das "Startzeichen" übergeben wird. 
     * 
     * @author Fritz Lindhauer, 16.03.2019, 08:34:53
     */
    public void testGetPositionInAlphabetForChar(){
    	char ctemp = '0';
    	int itemp = CounterByCharacterAscii_AlphabetZZZ.getPositionForChar(ctemp);
    	boolean btemp = assertCheckNullBordersAlphabet_(ctemp,itemp);
    	assertTrue("Fehler beim Check auf gültige Werte", btemp);
    	    	
    	//+++++++++++++++++++++++++++++++++++++++++
    	ctemp = 'A';
    	itemp = CounterByCharacterAscii_AlphabetZZZ.getPositionForChar(ctemp);
    	btemp = assertCheckNullBordersAlphabet_(ctemp,itemp);
    	assertTrue("Fehler beim Check auf gültige Werte", btemp);
    	assertEquals(1,itemp);
    	
    	ctemp = 'a';
    	itemp = CounterByCharacterAscii_AlphabetZZZ.getPositionForChar(ctemp);
    	btemp = assertCheckNullBordersAlphabet_(ctemp,itemp);
    	assertTrue("Fehler beim Check auf gültige Werte", btemp);
    	assertEquals(1,itemp);
    	
    	//++++++++++++++++++++++++++++++++++++++++
    	ctemp = 'Z';
    	itemp = CounterByCharacterAscii_AlphabetZZZ.getPositionForChar(ctemp);
    	btemp = assertCheckNullBordersAlphabet_(ctemp,itemp);
    	assertTrue("Fehler beim Check auf gültige Werte", btemp);
    	assertEquals(26,itemp);
    	
    	ctemp = 'z';
    	itemp = CounterByCharacterAscii_AlphabetZZZ.getPositionForChar(ctemp);
    	btemp = assertCheckNullBordersAlphabet_(ctemp,itemp);
    	assertTrue("Fehler beim Check auf gültige Werte", btemp);
    	assertEquals(26,itemp);
    	
    }
    
    public void testGetCharForPositionInAlphabet(){	    		    	
    	int itemp = CounterByCharacterAscii_AlphabetZZZ.iPOSITION_MIN-1;
    	String stemp = CounterByCharacterAscii_AlphabetZZZ.getCharForPosition(itemp);
    	boolean btemp = assertCheckNullBordersAlphabet_(itemp, stemp);
    	assertTrue("Fehler beim Check auf Null Werte", btemp);
    	
    	//+++++++++++++++++++++++++++++++++++++++++++++++
    	itemp = CounterByCharacterAscii_AlphabetZZZ.iPOSITION_MIN;
    	stemp = CounterByCharacterAscii_AlphabetZZZ.getCharForPosition(itemp);
    	btemp = assertCheckNullBordersAlphabet_(itemp, stemp);
    	assertTrue("Fehler beim Check auf Null Werte", btemp);
		assertEquals("A",stemp);
    	
		stemp = CounterByCharacterAscii_AlphabetZZZ.getCharForPosition(itemp, true); //Kleinbuchstaben
		btemp = assertCheckNullBordersAlphabet_(itemp, stemp);
    	assertTrue("Fehler beim Check auf Null Werte", btemp);
		assertEquals("a",stemp);
    	
		//+++++++++++++++++++++++++++++++++++++++++++++++++++
		itemp = CounterByCharacterAscii_AlphabetZZZ.iPOSITION_MAX+1;
    	stemp = CounterByCharacterAscii_AlphabetZZZ.getCharForPosition(itemp);
    	btemp = assertCheckNullBordersAlphabet_(itemp, stemp);
    	assertTrue("Fehler beim Check auf Null Werte", btemp);
			    	
		//++++++++++++++++++++++++++++++++
		itemp = CounterByCharacterAscii_AlphabetZZZ.iPOSITION_MAX;
    	stemp = CounterByCharacterAscii_AlphabetZZZ.getCharForPosition(itemp);
    	btemp = assertCheckNullBordersAlphabet_(itemp, stemp);
    	assertTrue("Fehler beim Check auf Null Werte", btemp);
		assertEquals("Z",stemp);
		
		stemp = CounterByCharacterAscii_AlphabetZZZ.getCharForPosition(itemp, true); //Kleinbuchstaben
		btemp = assertCheckNullBordersAlphabet_(itemp, stemp);
    	assertTrue("Fehler beim Check auf Null Werte", btemp);
		assertEquals("z",stemp);
    }
    
    
    
    
public void testGetStringAlphabetForNumber_StrategySerial(){
    	try{    		    	
			//"SERIAL STRATEGIE"-Ergebnisse
	    	int itemp = CounterByCharacterAscii_AlphabetZZZ.iPOSITION_MIN-1;
	    	String stemp = CounterByCharacterAscii_AlphabetZZZ.getStringForNumber(itemp);
	    	boolean btemp = assertCheckNullBordersAlphabetStrategyBased_(itemp, stemp);
	    	assertTrue("Fehler beim Check auf Null Werte", btemp);
	    	
	    	//+++++++++++++++++++++++++++++++++++++++++++++++
	    	itemp = CounterByCharacterAscii_AlphabetZZZ.iPOSITION_MIN;
	    	
	    	
	    	stemp = CounterByCharacterAscii_AlphabetZZZ.getStringForNumber(itemp);
	    	btemp = assertCheckNullBordersAlphabetStrategyBased_(itemp, stemp);
	    	assertTrue("Fehler beim Check auf Null Werte", btemp);
			assertEquals("A",stemp);
	    	
			stemp = CounterByCharacterAscii_AlphabetZZZ.getStringForNumber(itemp, true); //Kleinbuchstaben
			btemp = assertCheckNullBordersAlphabetStrategyBased_(itemp, stemp);
	    	assertTrue("Fehler beim Check auf Null Werte", btemp);
			assertEquals("a",stemp);
	    		    	
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			itemp = CounterByCharacterAscii_AlphabetZZZ.iPOSITION_MAX;
			
			//"SERIAL STRATEGIE"-Ergebnisse
	    	stemp = CounterByCharacterAscii_AlphabetZZZ.getStringForNumber(itemp);
	    	btemp = assertCheckNullBordersAlphabetStrategyBased_(itemp, stemp);
	    	assertTrue("Fehler beim Check auf Null Werte", btemp);
			assertEquals("Z",stemp);
			
			stemp = CounterByCharacterAscii_AlphabetZZZ.getStringForNumber(itemp, true); //Kleinbuchstaben
			btemp = assertCheckNullBordersAlphabetStrategyBased_(itemp, stemp);
	    	assertTrue("Fehler beim Check auf Null Werte", btemp);
			assertEquals("z",stemp);
	
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	    	
	    	
			//######################
			//Nun Werte eingeben, die über 27 liegen. Diese müssen erlaubt sein.
			//+++++++++++++++++++++++++++++++++++++++++++++++++++
			
	    	//"SERIAL STRATEGIE"-Ergebnisse
			itemp = CounterByCharacterAscii_AlphabetZZZ.iPOSITION_MAX+1;
	    	stemp = CounterByCharacterAscii_AlphabetZZZ.getStringForNumber(itemp);
	    	btemp = assertCheckNullBordersAlphabetStrategyBased_(itemp, stemp);
	    	assertTrue("Fehler beim Check auf Null Werte", btemp);
	    	assertEquals("ZA",stemp);
	    	
	    	stemp = CounterByCharacterAscii_AlphabetZZZ.getStringForNumber(itemp, true); //Kleinbuchstaben
	    	btemp = assertCheckNullBordersAlphabetStrategyBased_(itemp, stemp);
	    	assertTrue("Fehler beim Check auf Null Werte", btemp);
	    	assertEquals("za",stemp);
	    	
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	    	
	    	//... weitere Tests
	     	//"SERIAL STRATEGIE"-Ergebnisse
			itemp = CounterByCharacterAscii_AlphabetZZZ.iPOSITION_MAX+2;
	    	stemp = CounterByCharacterAscii_AlphabetZZZ.getStringForNumber(itemp);
	    	btemp = assertCheckNullBordersAlphabetStrategyBased_(itemp, stemp);
	    	assertTrue("Fehler beim Check auf Null Werte", btemp);
	    	assertEquals("ZB",stemp);
	    	
	    	stemp = CounterByCharacterAscii_AlphabetZZZ.getStringForNumber(itemp, true); //Kleinbuchstaben
	    	btemp = assertCheckNullBordersAlphabetStrategyBased_(itemp, stemp);
	    	assertTrue("Fehler beim Check auf Null Werte", btemp);
	    	assertEquals("zb",stemp);
    	} catch (ExceptionZZZ ez) {
    		fail("Method throws an exception." + ez.getMessageLast());
    	} 
    }

public void testGetStringAlphabetForNumber_StrategySerialRightAligned(){
	try{
		//"SERIAL STRATEGIE"-Ergebnisse
		//1. true ist lowercase, 2. true ist rightbound
		int itemp = CounterByCharacterAscii_AlphabetZZZ.iPOSITION_MIN-1;
		String stemp = CounterByCharacterAscii_AlphabetZZZ.getStringForNumber(itemp, false, true);
		boolean btemp = assertCheckNullBordersAlphabetStrategyBased_(itemp, stemp);
		assertTrue("Fehler beim Check auf Null Werte", btemp);
		
		//+++++++++++++++++++++++++++++++++++++++++++++++
		itemp = CounterByCharacterAscii_AlphabetZZZ.iPOSITION_MIN;
		
		
		stemp = CounterByCharacterAscii_AlphabetZZZ.getStringForNumber(itemp, false, true);
		btemp = assertCheckNullBordersAlphabetStrategyBased_(itemp, stemp);
		assertTrue("Fehler beim Check auf Null Werte", btemp);
		assertEquals("A",stemp);
		
		stemp = CounterByCharacterAscii_AlphabetZZZ.getStringForNumber(itemp, true, true);  //Kleinbuchstaben
		btemp = assertCheckNullBordersAlphabetStrategyBased_(itemp, stemp);
		assertTrue("Fehler beim Check auf Null Werte", btemp);
		assertEquals("a",stemp);
			    	
		//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		itemp = CounterByCharacterAscii_AlphabetZZZ.iPOSITION_MAX;
		
		//"SERIAL STRATEGIE"-Ergebnisse
		stemp = CounterByCharacterAscii_AlphabetZZZ.getStringForNumber(itemp, false, true);
		btemp = assertCheckNullBordersAlphabetStrategyBased_(itemp, stemp);
		assertTrue("Fehler beim Check auf Null Werte", btemp);
		assertEquals("Z",stemp);
		
		stemp = CounterByCharacterAscii_AlphabetZZZ.getStringForNumber(itemp, true, true); //Kleinbuchstaben
		btemp = assertCheckNullBordersAlphabetStrategyBased_(itemp, stemp);
		assertTrue("Fehler beim Check auf Null Werte", btemp);
		assertEquals("z",stemp);
	
		//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		
		
		//######################
		//Nun Werte eingeben, die über 27 liegen. Diese müssen erlaubt sein.
		//+++++++++++++++++++++++++++++++++++++++++++++++++++
		
		//"SERIAL STRATEGIE"-Ergebnisse
		itemp = CounterByCharacterAscii_AlphabetZZZ.iPOSITION_MAX+1;
		stemp = CounterByCharacterAscii_AlphabetZZZ.getStringForNumber(itemp, false, true);
		btemp = assertCheckNullBordersAlphabetStrategyBased_(itemp, stemp);
		assertTrue("Fehler beim Check auf Null Werte", btemp);
		assertEquals("AZ",stemp);
		
		stemp = CounterByCharacterAscii_AlphabetZZZ.getStringForNumber(itemp, true, true); //Kleinbuchstaben
		btemp = assertCheckNullBordersAlphabetStrategyBased_(itemp, stemp);
		assertTrue("Fehler beim Check auf Null Werte", btemp);
		assertEquals("az",stemp);
		
		//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		
		//... weitere Tests
	 	//"SERIAL STRATEGIE"-Ergebnisse
		itemp = CounterByCharacterAscii_AlphabetZZZ.iPOSITION_MAX+2;
		stemp = CounterByCharacterAscii_AlphabetZZZ.getStringForNumber(itemp, false, true);
		btemp = assertCheckNullBordersAlphabetStrategyBased_(itemp, stemp);
		assertTrue("Fehler beim Check auf Null Werte", btemp);
		assertEquals("BZ",stemp);
		
		stemp = CounterByCharacterAscii_AlphabetZZZ.getStringForNumber(itemp, true, true); //Kleinbuchstaben
		btemp = assertCheckNullBordersAlphabetStrategyBased_(itemp, stemp);
		assertTrue("Fehler beim Check auf Null Werte", btemp);
		assertEquals("bz",stemp);
	} catch (ExceptionZZZ ez) {
		fail("Method throws an exception." + ez.getMessageLast());
	} 
}

public void testGetNumberForStringAlphabet(){
	String stemp; String stempold; int itemp; int itempold; boolean btemp;
	try{
    	stemp = "0";
    	try{
	    	itemp = CounterByCharacterAscii_AlphabetZZZ.getNumberForString(stemp);
	    	fail("Method should have thrown an exception for the string '"+stemp+"'");
    	} catch (ExceptionZZZ ez) {
			//Erwartetete Exception
		} 
    	
    	stemp = "AZ";//Ungültig hinsichtlich der Syntax
    	try{
	    	itemp = CounterByCharacterAscii_AlphabetZZZ.getNumberForString(stemp);
	    	fail("Method should have thrown an exception for the string '"+stemp+"'");
    	} catch (ExceptionZZZ ez) {
			//Erwartetete Exception
		} 
    	
    	stemp = "az";//Ungültig hinsichtlich der Syntax
    	try{
	    	itemp = CounterByCharacterAscii_AlphabetZZZ.getNumberForString(stemp, true);
	    	fail("Method should have thrown an exception for the string '"+stemp+"'");
    	} catch (ExceptionZZZ ez) {
			//Erwartetete Exception
		} 
    	
    	stemp = "AA"; //Serielle Strategie: Alle Zeichen links müssen den höchsten Wert haben. Korrekt wäre also "ZA"
    	try{
	    	itemp = CounterByCharacterAscii_AlphabetZZZ.getNumberForString(stemp);
	    	fail("Method should have thrown an exception for the string '"+stemp+"'");
    	} catch (ExceptionZZZ ez) {
			//Erwartetete Exception
		} 
    	
    	stemp = "aa"; //Serielle Strategie: Alle Zeichen links müssen den höchsten Wert haben. Korrekt wäre also "ZA"
    	try{
	    	itemp = CounterByCharacterAscii_AlphabetZZZ.getNumberForString(stemp, true);
	    	fail("Method should have thrown an exception for the string '"+stemp+"'");
    	} catch (ExceptionZZZ ez) {
			//Erwartetete Exception
		} 
		    		    		    	 
    	//+++++++++++++++++++++++++++++++++++++++++
    	stemp = "A";
    	itemp = CounterByCharacterAscii_AlphabetZZZ.getNumberForString(stemp);
    	btemp = assertCheckNullBordersAlphabet_(itemp,stemp);
    	assertTrue("Fehler beim Check auf gültige Werte", btemp);
    	assertEquals(1,itemp);
    	btemp = assertCheckReconvertAlphabet_(itemp, stemp);
    	assertTrue("Fehler beim Check auf gelungene Rekonvertierung", btemp);
    	
    	
    	//++++++++++++++++++++++++++++++++++++++++
    	stemp = "Z";
    	itemp = CounterByCharacterAscii_AlphabetZZZ.getNumberForString(stemp);
    	btemp = assertCheckNullBordersAlphabet_(itemp,stemp);    	
    	assertTrue("Fehler beim Check auf gültige Werte", btemp);
    	assertEquals(26,itemp);
    	btemp = assertCheckReconvertAlphabet_(itemp, stemp);
    	assertTrue("Fehler beim Check auf gelungene Rekonvertierung", btemp);
    	
    	//++++++++++++++++++++++++++++++++++++++++++
    	//Um +1 Weiter erhöht sollte sein ..."ZA", bei serieller Zählweise | "AA" bei Multiple-Strategy
    	stemp = "ZA";
    	itemp = CounterByCharacterAscii_AlphabetZZZ.getNumberForString(stemp);
    	btemp = assertCheckNullBordersAlphabet_(itemp,stemp);
    	assertTrue("Fehler beim Check auf gültige Werte", btemp);
    	assertEquals(27,itemp);
    	btemp = assertCheckReconvertAlphabet_(itemp, stemp);
    	assertTrue("Fehler beim Check auf gelungene Rekonvertierung", btemp);
    	
	} catch (ExceptionZZZ ez) {
		fail("Method throws an exception." + ez.getMessageLast());
	} 
}


public void testGetNumberForStringAlphabet_StrategyMultiple(){
	String stemp; String stempold; int itemp; int itempold; boolean btemp;
	try{
    	stemp = "0";
    	try{
	    	itemp = CounterByCharacterAscii_AlphabetZZZ.getNumberForStringMultiple(stemp);
	    	fail("Method should have thrown an exception for the string '"+stemp+"'");
    	} catch (ExceptionZZZ ez) {
			//Erwartetete Exception
		} 
    	
    	stemp = "A0";
    	try{
	    	itemp = CounterByCharacterAscii_AlphabetZZZ.getNumberForStringMultiple(stemp);
	    	fail("Method should have thrown an exception for the string '"+stemp+"'");
    	} catch (ExceptionZZZ ez) {
			//Erwartetete Exception
		} 
    	
    
    	
    	//stemp = "AA";//Serielle Strategie: Alle Zeichen links müssen den höchsten Wert haben. Korrekt wäre also "ZA"
    	stemp = "AZ";//Multiple Strategie: Alle Zeichen müssen gleich sein.
    	try{
	    	itemp = CounterByCharacterAscii_AlphabetZZZ.getNumberForStringMultiple(stemp);
	    	fail("Method should have thrown an exception for the string '"+stemp+"'");
    	} catch (ExceptionZZZ ez) {
			//Erwartetete Exception
		} 
		    		    		    	 
    	//+++++++++++++++++++++++++++++++++++++++++
    	stemp = "A";
    	itemp = CounterByCharacterAscii_AlphabetZZZ.getNumberForStringMultiple(stemp);
    	btemp = assertCheckNullBordersAlphabet_(itemp,stemp);
    	assertTrue("Fehler beim Check auf gültige Werte", btemp);
    	assertEquals(1,itemp);
    	btemp = assertCheckReconvertAlphabetMultiple_(itemp, stemp);
    	assertTrue("Fehler beim Check auf gelungene Rekonvertierung", btemp);
    	
    	
    	//++++++++++++++++++++++++++++++++++++++++
    	stemp = "Z";
    	itemp = CounterByCharacterAscii_AlphabetZZZ.getNumberForStringMultiple(stemp);
    	btemp = assertCheckNullBordersAlphabet_(itemp,stemp);    	
    	assertTrue("Fehler beim Check auf gültige Werte", btemp);
    	assertEquals(26,itemp);
    	btemp = assertCheckReconvertAlphabetMultiple_(itemp, stemp);
    	assertTrue("Fehler beim Check auf gelungene Rekonvertierung", btemp);
    	
    	//++++++++++++++++++++++++++++++++++++++++++
    	//Um +1 Weiter erhöht sollte sein ..."ZA", bei serieller Zählweise | "AA" bei Multiple-Strategy
    	stemp = "AA";
    	itemp = CounterByCharacterAscii_AlphabetZZZ.getNumberForStringMultiple(stemp);
    	btemp = assertCheckNullBordersAlphabet_(itemp,stemp);
    	assertTrue("Fehler beim Check auf gültige Werte", btemp);
    	assertEquals(27,itemp);
    	btemp = assertCheckReconvertAlphabetMultiple_(itemp, stemp);
    	assertTrue("Fehler beim Check auf gelungene Rekonvertierung", btemp);
    	
	} catch (ExceptionZZZ ez) {
		fail("Method throws an exception." + ez.getMessageLast());
	} 
}


public void testGetStringAlphabetForNumber_StrategyMultiple(){
	int itemp; String stemp; boolean btemp;
//"MULTIPLE STRATEGY"-Ergebnisse
	try{
		itemp = CounterByCharacterAscii_AlphabetZZZ.iPOSITION_MIN-1;
		stemp = CounterByCharacterAscii_AlphabetZZZ.getStringMultipleForNumber(itemp);
		btemp = assertCheckNullBordersAlphabetStrategyBased_(itemp, stemp);
		assertTrue("Fehler beim Check auf Null Werte", btemp);
	} catch (ExceptionZZZ ez) {
		fail("Method throws an exception." + ez.getMessageLast());
	}
//+++++++++++++++++++++++++++++++++++++++++++++++
	//"MULTIPLE STRATEGY"-Ergebnisse
	try{
		itemp = CounterByCharacterAscii_AlphabetZZZ.iPOSITION_MIN;
		stemp = CounterByCharacterAscii_AlphabetZZZ.getStringMultipleForNumber(itemp);
		btemp = assertCheckNullBordersAlphabetStrategyBased_(itemp, stemp);
		assertTrue("Fehler beim Check auf Null Werte", btemp);
		assertEquals("A",stemp);
	} catch (ExceptionZZZ ez) {
		fail("Method throws an exception." + ez.getMessageLast());
	}

	try{
		itemp = CounterByCharacterAscii_AlphabetZZZ.iPOSITION_MIN;
		stemp = CounterByCharacterAscii_AlphabetZZZ.getStringMultipleForNumber(itemp, true); //Kleinbuchstaben
		btemp = assertCheckNullBordersAlphabetStrategyBased_(itemp, stemp);
		assertTrue("Fehler beim Check auf Null Werte", btemp);
		assertEquals("a",stemp);
	} catch (ExceptionZZZ ez) {
		fail("Method throws an exception." + ez.getMessageLast());
	}
	    	
//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	//"MULTIPLE STRATEGY"-Ergebnisse
	try{
		itemp = CounterByCharacterAscii_AlphabetZZZ.iPOSITION_MAX;
		stemp = CounterByCharacterAscii_AlphabetZZZ.getStringMultipleForNumber(itemp);
		btemp = assertCheckNullBordersAlphabetStrategyBased_(itemp, stemp);
		assertTrue("Fehler beim Check auf Null Werte", btemp);
		assertEquals("Z",stemp);
		
		stemp = CounterByCharacterAscii_AlphabetZZZ.getStringMultipleForNumber(itemp, true); //Kleinbuchstaben
		btemp = assertCheckNullBordersAlphabetStrategyBased_(itemp, stemp);
		assertTrue("Fehler beim Check auf Null Werte", btemp);
		assertEquals("z",stemp);
	} catch (ExceptionZZZ ez) {
		fail("Method throws an exception." + ez.getMessageLast());
	}
//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++


//######################
//Nun Werte eingeben, die über 27 liegen. Diese müssen erlaubt sein.
//+++++++++++++++++++++++++++++++++++++++++++++++++++
	//"MULTIPLE STRATEGY"-Ergebnisse
	try{
		itemp = CounterByCharacterAscii_AlphabetZZZ.iPOSITION_MAX+1;
		stemp = CounterByCharacterAscii_AlphabetZZZ.getStringMultipleForNumber(itemp);
		btemp = assertCheckNullBordersAlphabetStrategyBased_(itemp, stemp);
		assertTrue("Fehler beim Check auf Null Werte", btemp);
		assertEquals("AA",stemp);
		
		stemp = CounterByCharacterAscii_AlphabetZZZ.getStringMultipleForNumber(itemp, true); //Kleinbuchstaben
		btemp = assertCheckNullBordersAlphabetStrategyBased_(itemp, stemp);
		assertTrue("Fehler beim Check auf Null Werte", btemp);
		assertEquals("aa",stemp);
	} catch (ExceptionZZZ ez) {
		fail("Method throws an exception." + ez.getMessageLast());
	}

//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

//... weitere Tests
	//"MULTIPLE STRATEGY"-Ergebnisse
	try{
		itemp = CounterByCharacterAscii_AlphabetZZZ.iPOSITION_MAX+2;
		stemp = CounterByCharacterAscii_AlphabetZZZ.getStringMultipleForNumber(itemp);
		btemp = assertCheckNullBordersAlphabetStrategyBased_(itemp, stemp);
		assertTrue("Fehler beim Check auf Null Werte", btemp);
		assertEquals("BB",stemp);
		
		stemp = CounterByCharacterAscii_AlphabetZZZ.getStringMultipleForNumber(itemp, true); //Kleinbuchstaben
		btemp = assertCheckNullBordersAlphabetStrategyBased_(itemp, stemp);
		assertTrue("Fehler beim Check auf Null Werte", btemp);
		assertEquals("bb",stemp);
	} catch (ExceptionZZZ ez) {
		fail("Method throws an exception." + ez.getMessageLast());
	}




//try{
//	boolean btemp;
//	
//	//Static Zugriff
//	//EnumSet soll von der EnumerationZZZ-Klasse schon "generisch/per Reflektion" erzeugt und mitgebracht werden.
//	EnumSet setEnumGenerated = EnumSetMappedTestTypeZZZ.getEnumSet();		
//	int iSize = setEnumGenerated.size();
//	assertTrue("3 Elemente im Set erwartet.", iSize==3);
//	
//	//Generischer Zugriff, ist  nicht ganz möglich, darum Verwendung der Factory Klasse
//	IEnumSetFactoryZZZ objFactory = EnumSetTestFactoryZZZ.getInstance();				
//	Class objClass = EnumSetMappedTestTypeZZZ.class;
//	EnumSetMappedUtilZZZ enumSetUtil = new EnumSetMappedUtilZZZ(objFactory, objClass);
//	
//	//Beim Wiederholten Zugriff über die Util-Klasse soll das einmal erstellte EnumSet wiederverwendet werden.	
//	EnumSet setEnumReused = enumSetUtil.getEnumSetCurrent();
//	assertNotNull(setEnumReused);
//	
//	int iSizeReused = setEnumReused.size();
//	assertTrue("3 Elemente im SetReused erwartet.", iSizeReused==3);				
//
//} catch (ExceptionZZZ ez) {
//	fail("Method throws an exception." + ez.getMessageLast());
//} 
}

}//end class
