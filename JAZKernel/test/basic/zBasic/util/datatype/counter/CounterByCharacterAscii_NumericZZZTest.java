package basic.zBasic.util.datatype.counter;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import junit.framework.TestCase;

public class CounterByCharacterAscii_NumericZZZTest   extends TestCase{
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
        
    
    private boolean assertCheckNullBordersNumeric_(int iInput, String sResult){
    	boolean bReturn = false;
    	main:{
    		int iDiv = Math.abs(iInput / CounterByCharacterAscii_NumericZZZ.iPOSITION_MAX ); //durch abs wird also intern in ein Integer umgewandetl.... nicht nur das Weglassen des ggfs. negativen Vorzeichens.
			int iMod = iInput% CounterByCharacterAscii_NumericZZZ.iPOSITION_MAX;
    		
    		if(iDiv<1){
	    		if(iInput< CounterByCharacterAscii_NumericZZZ.iPOSITION_MIN){
		    		assertNull("Bei <'" + CounterByCharacterAscii_NumericZZZ.iPOSITION_MIN + "' wird NULL erwartet. Ergebnis '" + sResult + "' für " + iInput, sResult);
		    	}else if(iInput>CounterByCharacterAscii_NumericZZZ.iPOSITION_MAX){
		    		assertNull("Bei >'" + CounterByCharacterAscii_NumericZZZ.iPOSITION_MAX + "' wird NULL erwartet. Ergebnis '" + sResult + "' für " + iInput, sResult);
		    	}
		    		
	    		if(iInput>=CounterByCharacterAscii_NumericZZZ.iPOSITION_MIN && iInput <= CounterByCharacterAscii_NumericZZZ.iPOSITION_MAX){
		    		assertNotNull("Bei >= '" + CounterByCharacterAscii_NumericZZZ.iPOSITION_MIN +"' und <='" + CounterByCharacterAscii_NumericZZZ.iPOSITION_MAX + "' wird keine NULL erwartet. Ergebnis '" + sResult + "' für " + iInput, sResult);
		    	}	  
    		}else{
    			if(iMod>=CounterByCharacterAscii_NumericZZZ.iPOSITION_MIN){
    	    		assertNotNull("Bei >= '" + CounterByCharacterAscii_NumericZZZ.iPOSITION_MIN + "' wird keine NULL erwartet. Ergebnis '" + iInput + "' für " + sResult, sResult);
    	    	}	 
    		}
    		bReturn=true;
    			    		    	
    	}//end main:
    	return bReturn;	    	
    }
    private boolean assertCheckNullBordersNumeric_(char cInput,int iResult){
    	boolean bReturn = false;
    	main:{
    		boolean bIsValidCharacter=CounterByCharacterAscii_NumericZZZ.isValidCharacter(cInput);    		
    		if(iResult< CounterByCharacterAscii_NumericZZZ.iPOSITION_MIN){
    				assertFalse("Bei <'" + CounterByCharacterAscii_NumericZZZ.iPOSITION_MIN + "' wird ein ungültiges Zeichen erwartet. Ergebnis '" + iResult + "' für " + cInput, bIsValidCharacter);
    		}else if(iResult>CounterByCharacterAscii_NumericZZZ.iPOSITION_MAX){
    				assertFalse("Bei >'" + CounterByCharacterAscii_NumericZZZ.iPOSITION_MIN + "' wird ein ungültiges Zeichen erwartet. Ergebnis '" + iResult + "' für " + cInput, bIsValidCharacter);
	    	}
	    		    		
    		bReturn=true;
    			    		    	
    	}//end main:
    	return bReturn;	    	
    }
    
    private boolean assertCheckNullBordersNumericChar_(String sInput,int iResult){
    	boolean bReturn = false;
    	main:{
    		if(StringZZZ.isEmpty(sInput)) break main;
    		char[]ca=sInput.toCharArray();
    		for(int icount=0;icount<=ca.length-1;icount++){
    			char cInput = ca[icount];
    			bReturn = assertCheckNullBordersNumeric_(cInput, iResult);
    			if(!bReturn)break main;	    		
    		}
	    		    		
    		bReturn=true;
    			    		    	
    	}//end main:
    	return bReturn;	    	
    }
    
    
//    private boolean assertCheckNullBordersNumericStrategyBased_(int iInput, String sResult){
//    	boolean bReturn = false;
//    	main:{
//    		if(iInput< CounterByCharacterAscii_NumericZZZ.iNUMERIC_POSITION_MIN){
//	    		assertNull("Bei <'" + CounterByCharacterAscii_NumericZZZ.iNUMERIC_POSITION_MIN + "' wird NULL erwartet. Ergebnis '" + sResult + "' für " + iInput, sResult);
//	    	}
//	    		
//    		if(iInput>=CounterByCharacterAscii_NumericZZZ.iNUMERIC_POSITION_MIN ){
//	    		assertNotNull("Bei >= '" + CounterByCharacterAscii_NumericZZZ.iNUMERIC_POSITION_MIN  + "' wird keine NULL erwartet. Ergebnis '" + sResult + "' für " + iInput, sResult);
//	    	}
//    		
//    		bReturn=true;    			    		    
//    	}//end main:
//    	return bReturn;	    	
//    }
    
    private boolean assertCheckNullBordersNumericStrategyBased_(int iResult,String sInput){
    	boolean bReturn = false;
    	main:{
    		//Ermittle den "Teiler" und den Rest, Also Modulo - Operation
			int iDiv = Math.abs(iResult / CounterByCharacterAscii_NumericZZZ.iPOSITION_MAX ); //durch abs wird also intern in ein Integer umgewandetl.... nicht nur das Weglassen des ggfs. negativen Vorzeichens.
			int iMod = iResult % CounterByCharacterAscii_NumericZZZ.iPOSITION_MAX;
    		
			if(iDiv==0 && iMod< CounterByCharacterAscii_NumericZZZ.iPOSITION_MIN){
	    		assertNull("Bei <'" + CounterByCharacterAscii_NumericZZZ.iPOSITION_MIN + "' wird NULL erwartet. Ergebnis '" + iResult + "' für " + sInput, sInput);
	    	}
	    		
    		if(iMod>=CounterByCharacterAscii_NumericZZZ.iPOSITION_MIN){
	    		assertNotNull("Bei >= '" + CounterByCharacterAscii_NumericZZZ.iPOSITION_MIN + "' wird keine NULL erwartet. Ergebnis '" + iResult + "' für " + sInput, sInput);
	    	}	    		
    		bReturn=true;
    			    		    	
    	}//end main:
    	return bReturn;	    	
    }
    
    private boolean assertCheckReconvertNumeric_(int itempNew, String sInputOld) throws ExceptionZZZ{
    	boolean bReturn = false;
    	
    	String stemp; boolean btemp; int itemp; int iInputOld;
    	main:{    		    		
    		//Test zurückkonvertieren
	    	stemp = CounterByCharacterAscii_NumericZZZ.getStringForNumber(itempNew);
	    	btemp = assertCheckNullBordersNumeric_(itempNew,stemp);
	    	assertTrue("Fehler beim Check auf gültige Werte", btemp);
	    	assertEquals("Abweichung beim Zurückkonvertieren.",sInputOld,stemp);
	    	
	    	iInputOld = itempNew; 
	    	itemp = CounterByCharacterAscii_NumericZZZ.getNumberForString(stemp);
	    	btemp = assertCheckNullBordersNumeric_(itemp,stemp);
	    	assertTrue("Fehler beim Check auf gültige Werte", btemp);
	    	assertEquals("Abweichung beim Zurück-Zurückkonvertieren.",iInputOld,itemp);
	    	
	    	bReturn = true;
    	}//end main:
    	return bReturn;    	    	
    }
    
    private boolean assertCheckReconvertNumericMultiple_(int itempNew, String sInputOld) throws ExceptionZZZ{
    	boolean bReturn = false;
    	
    	String stemp; boolean btemp; int itemp; int iInputOld;
    	main:{    		    		
    		//Test zurückkonvertieren
	    	stemp = CounterByCharacterAscii_NumericZZZ.getStringMultipleForNumber(itempNew);
	    	btemp = assertCheckNullBordersNumeric_(itempNew,stemp);
	    	assertTrue("Fehler beim Check auf gültige Werte", btemp);
	    	assertEquals("Abweichung beim Zurückkonvertieren.",sInputOld,stemp);
	    	
	    	iInputOld = itempNew; 
	    	itemp = CounterByCharacterAscii_NumericZZZ.getNumberForStringMultiple(stemp);
	    	btemp = assertCheckNullBordersNumeric_(itemp,stemp);
	    	assertTrue("Fehler beim Check auf gültige Werte", btemp);
	    	assertEquals("Abweichung beim Zurück-Zurückkonvertieren.",iInputOld,itemp);
	    	
	    	bReturn = true;
    	}//end main:
    	return bReturn;    	    	
    }
    
    public void testGetPositionInAlphabetForChar(){
    	char ctemp = 'A';
    	int itemp = CounterByCharacterAscii_NumericZZZ.getPositionForChar(ctemp);
    	boolean btemp = assertCheckNullBordersNumeric_(ctemp,itemp);
    	assertTrue("Fehler beim Check auf gültige Werte", btemp);
    	    	
    	//+++++++++++++++++++++++++++++++++++++++++
    	ctemp = '0';
    	itemp = CounterByCharacterAscii_NumericZZZ.getPositionForChar(ctemp);
    	btemp = assertCheckNullBordersNumeric_(ctemp,itemp);
    	assertTrue("Fehler beim Check auf gültige Werte", btemp);
    	assertEquals(1,itemp);

    	//++++++++++++++++++++++++++++++++++++++++
    	ctemp = '9';
    	itemp = CounterByCharacterAscii_NumericZZZ.getPositionForChar(ctemp);
    	btemp = assertCheckNullBordersNumeric_(ctemp,itemp);
    	assertTrue("Fehler beim Check auf gültige Werte", btemp);
    	assertEquals(10,itemp);

    	
    }
    
    public void testGetNumberForStringNumeric(){
    	String stemp; String stempold; int itemp; int itempold; boolean btemp;
    	try{
	    	stemp = "A";
	    	try{
		    	itemp = CounterByCharacterAscii_NumericZZZ.getNumberForString(stemp);
		    	fail("Method should have thrown an exception for the string '"+stemp+"'");
	    	} catch (ExceptionZZZ ez) {
				//Erwartetete Exception
			} 
	    	
	    	stemp = "0Z";
	    	try{
		    	itemp = CounterByCharacterAscii_NumericZZZ.getNumberForString(stemp);
		    	fail("Method should have thrown an exception for the string '"+stemp+"'");
	    	} catch (ExceptionZZZ ez) {
				//Erwartetete Exception
			} 
	    	
	    	stemp = "00"; //Serielle Strategie: Alle Zeichen links müssen den höchsten Wert haben. Korrekt wäre also "90"
	    	try{
		    	itemp = CounterByCharacterAscii_NumericZZZ.getNumberForString(stemp);
		    	fail("Method should have thrown an exception for the string '"+stemp+"'");
	    	} catch (ExceptionZZZ ez) {
				//Erwartetete Exception
			} 
    		    		    		    	 
	    	//+++++++++++++++++++++++++++++++++++++++++
	    	stemp = "0";
	    	itemp = CounterByCharacterAscii_NumericZZZ.getNumberForString(stemp);
	    	btemp = assertCheckNullBordersNumeric_(itemp,stemp);
	    	assertTrue("Fehler beim Check auf gültige Werte", btemp);
	    	assertEquals(1,itemp);
	    	btemp = assertCheckReconvertNumeric_(itemp, stemp);
	    	assertTrue("Fehler beim Check auf gelungene Rekonvertierung", btemp);
	    	
	    	
	    	//++++++++++++++++++++++++++++++++++++++++
	    	stemp = "9";
	    	itemp = CounterByCharacterAscii_NumericZZZ.getNumberForString(stemp);
	    	btemp = assertCheckNullBordersNumeric_(itemp,stemp);    	
	    	assertTrue("Fehler beim Check auf gültige Werte", btemp);
	    	assertEquals(10,itemp);
	    	btemp = assertCheckReconvertNumeric_(itemp, stemp);
	    	assertTrue("Fehler beim Check auf gelungene Rekonvertierung", btemp);
	    	
	    	//++++++++++++++++++++++++++++++++++++++++++
	    	//Um +1 Weiter erhöht sollte sein ..."90", bei serieller Zählweise | "00" bei Multiple-Strategy
	    	stemp = "90";
	    	itemp = CounterByCharacterAscii_NumericZZZ.getNumberForString(stemp);
	    	btemp = assertCheckNullBordersNumeric_(itemp,stemp);
	    	assertTrue("Fehler beim Check auf gültige Werte", btemp);
	    	assertEquals(11,itemp);
	    	btemp = assertCheckReconvertNumeric_(itemp, stemp);
	    	assertTrue("Fehler beim Check auf gelungene Rekonvertierung", btemp);
	    	
    	} catch (ExceptionZZZ ez) {
    		fail("Method throws an exception." + ez.getMessageLast());
    	} 
    }
    
    
    public void testGetNumberForStringNumeric_StrategyMultiple(){
    	String stemp; String stempold; int itemp; int itempold; boolean btemp;
    	try{
	    	stemp = "A";
	    	try{
		    	itemp = CounterByCharacterAscii_NumericZZZ.getNumberForStringMultiple(stemp);
		    	fail("Method should have thrown an exception for the string '"+stemp+"'");
	    	} catch (ExceptionZZZ ez) {
				//Erwartetete Exception
			} 
	    	
	    	stemp = "0Z";
	    	try{
		    	itemp = CounterByCharacterAscii_NumericZZZ.getNumberForStringMultiple(stemp);
		    	fail("Method should have thrown an exception for the string '"+stemp+"'");
	    	} catch (ExceptionZZZ ez) {
				//Erwartetete Exception
			} 
	    	
	    
	    	
	    	//stemp = "00";//Serielle Strategie: Alle Zeichen links müssen den höchsten Wert haben. Korrekt wäre also "90"
	    	stemp = "90";//Multiple Strategie: Alle Zeichen müssen gleich sein.
	    	try{
		    	itemp = CounterByCharacterAscii_NumericZZZ.getNumberForStringMultiple(stemp);
		    	fail("Method should have thrown an exception for the string '"+stemp+"'");
	    	} catch (ExceptionZZZ ez) {
				//Erwartetete Exception
			} 
    		    		    		    	 
	    	//+++++++++++++++++++++++++++++++++++++++++
	    	stemp = "0";
	    	itemp = CounterByCharacterAscii_NumericZZZ.getNumberForStringMultiple(stemp);
	    	btemp = assertCheckNullBordersNumeric_(itemp,stemp);
	    	assertTrue("Fehler beim Check auf gültige Werte", btemp);
	    	assertEquals(1,itemp);
	    	btemp = assertCheckReconvertNumericMultiple_(itemp, stemp);
	    	assertTrue("Fehler beim Check auf gelungene Rekonvertierung", btemp);
	    	
	    	
	    	//++++++++++++++++++++++++++++++++++++++++
	    	stemp = "9";
	    	itemp = CounterByCharacterAscii_NumericZZZ.getNumberForStringMultiple(stemp);
	    	btemp = assertCheckNullBordersNumeric_(itemp,stemp);    	
	    	assertTrue("Fehler beim Check auf gültige Werte", btemp);
	    	assertEquals(10,itemp);
	    	btemp = assertCheckReconvertNumericMultiple_(itemp, stemp);
	    	assertTrue("Fehler beim Check auf gelungene Rekonvertierung", btemp);
	    	
	    	//++++++++++++++++++++++++++++++++++++++++++
	    	//Um +1 Weiter erhöht sollte sein ..."90", bei serieller Zählweise | "00" bei Multiple-Strategy
	    	stemp = "00";
	    	itemp = CounterByCharacterAscii_NumericZZZ.getNumberForStringMultiple(stemp);
	    	btemp = assertCheckNullBordersNumeric_(itemp,stemp);
	    	assertTrue("Fehler beim Check auf gültige Werte", btemp);
	    	assertEquals(11,itemp);
	    	btemp = assertCheckReconvertNumericMultiple_(itemp, stemp);
	    	assertTrue("Fehler beim Check auf gelungene Rekonvertierung", btemp);
	    	
    	} catch (ExceptionZZZ ez) {
    		fail("Method throws an exception." + ez.getMessageLast());
    	} 
    }
    
    public void testGetCharForPositionInNumeric(){	 
    	String stemp; boolean btemp; int itemp;
    	
    	main:{
	    	itemp = CounterByCharacterAscii_NumericZZZ.iPOSITION_MIN-1;
	    	stemp = CounterByCharacterAscii_NumericZZZ.getCharForPosition(itemp);
	    	btemp = assertCheckNullBordersNumericChar_(stemp, itemp);
	    	assertFalse("Fehler beim Check auf Null Werte", btemp);
	    	
	    	//+++++++++++++++++++++++++++++++++++++++++++++++++++			
	    	itemp = CounterByCharacterAscii_NumericZZZ.iPOSITION_MAX+1;
	    	stemp = CounterByCharacterAscii_NumericZZZ.getCharForPosition(itemp);
	    	btemp = assertCheckNullBordersNumericChar_(stemp, itemp);
	    	assertFalse("Fehler beim Check auf Null Werte", btemp);
			
	    	//############################################################	    	
	    	itemp = CounterByCharacterAscii_NumericZZZ.iPOSITION_MIN;
	    	stemp = CounterByCharacterAscii_NumericZZZ.getCharForPosition(itemp);
	    	btemp = assertCheckNullBordersNumericChar_(stemp, itemp);
	    	assertTrue("Fehler beim Check auf Null Werte", btemp);
			assertEquals("0",stemp);
	    					    					    	
			//++++++++++++++++++++++++++++++++
			itemp = CounterByCharacterAscii_NumericZZZ.iPOSITION_MAX;
	    	stemp = CounterByCharacterAscii_NumericZZZ.getCharForPosition(itemp);
	        btemp = assertCheckNullBordersNumericChar_(stemp, itemp);
	    	assertTrue("Fehler beim Check auf Null Werte", btemp);
			assertEquals("9",stemp);
    	}//end main:
    	
    }
    
public void testGetStringNumericForNumber_StrategySerial(){

	//"SERIAL STRATEGIE"-Ergebnisse
	int itemp; String stemp; boolean btemp;
	try{
		itemp = CounterByCharacterAscii_NumericZZZ.iPOSITION_MIN-1;
		stemp = CounterByCharacterAscii_NumericZZZ.getStringForNumber(itemp);
		btemp = assertCheckNullBordersNumericStrategyBased_(itemp, stemp);
		assertTrue("Fehler beim Check auf Null Werte", btemp);
	} catch (ExceptionZZZ ez) {
		fail("Method throws an exception." + ez.getMessageLast());
	} 

//+++++++++++++++++++++++++++++++++++++++++++++++
	try{
		itemp = CounterByCharacterAscii_NumericZZZ.iPOSITION_MIN;
		stemp = CounterByCharacterAscii_NumericZZZ.getStringForNumber(itemp);
		btemp = assertCheckNullBordersNumericStrategyBased_(itemp, stemp);
		assertTrue("Fehler beim Check auf Null Werte", btemp);
		assertEquals( "0",stemp);
	} catch (ExceptionZZZ ez) {
		fail("Method throws an exception." + ez.getMessageLast());
	} 

//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

//"SERIAL STRATEGIE"-Ergebnisse
	try{
		itemp = CounterByCharacterAscii_NumericZZZ.iPOSITION_MAX;
		stemp = CounterByCharacterAscii_NumericZZZ.getStringForNumber(itemp);
		btemp = assertCheckNullBordersNumericStrategyBased_(itemp, stemp);
		assertTrue("Fehler beim Check auf Null Werte", btemp);
		assertEquals("9",stemp);
	} catch (ExceptionZZZ ez) {
		fail("Method throws an exception." + ez.getMessageLast());
	} 
//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++


//######################
//Nun Werte eingeben, die über 10 liegen. Diese müssen erlaubt sein.
//+++++++++++++++++++++++++++++++++++++++++++++++++++

//"SERIAL STRATEGIE"-Ergebnisse
	try{
		itemp = CounterByCharacterAscii_NumericZZZ.iPOSITION_MAX+1;
		stemp = CounterByCharacterAscii_NumericZZZ.getStringForNumber(itemp);
		btemp = assertCheckNullBordersNumericStrategyBased_(itemp, stemp);
		assertTrue("Fehler beim Check auf Null Werte", btemp);
		assertEquals("90",stemp);
	} catch (ExceptionZZZ ez) {
		fail("Method throws an exception." + ez.getMessageLast());
	} 

//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

//... weitere Tests
	//"SERIAL STRATEGIE"-Ergebnisse
	try{
		itemp = CounterByCharacterAscii_NumericZZZ.iPOSITION_MAX+2;
		stemp = CounterByCharacterAscii_NumericZZZ.getStringForNumber(itemp);
		btemp = assertCheckNullBordersNumericStrategyBased_(itemp, stemp);
		assertTrue("Fehler beim Check auf Null Werte", btemp);
		assertEquals( "91",stemp);
	} catch (ExceptionZZZ ez) {
		fail("Method throws an exception." + ez.getMessageLast());
	} 
}

public void testGetStringNumericForNumber_StrategySerialRightAligned(){
	int itemp; String stemp; boolean btemp;
	//"SERIAL STRATEGIE"-Ergebnisse
	//1. true ist rightbound
	try{
		itemp = CounterByCharacterAscii_NumericZZZ.iPOSITION_MIN-1;
		stemp = CounterByCharacterAscii_NumericZZZ.getStringForNumber(itemp, true);
		btemp = assertCheckNullBordersNumericStrategyBased_(itemp, stemp);
		assertTrue("Fehler beim Check auf Null Werte", btemp);
	} catch (ExceptionZZZ ez) {
		fail("Method throws an exception." + ez.getMessageLast());
	} 
	
	//+++++++++++++++++++++++++++++++++++++++++++++++
	try{
		itemp = CounterByCharacterAscii_NumericZZZ.iPOSITION_MIN;
		stemp = CounterByCharacterAscii_NumericZZZ.getStringForNumber(itemp, true);
		btemp = assertCheckNullBordersNumericStrategyBased_(itemp, stemp);
		assertTrue("Fehler beim Check auf Null Werte", btemp);
		assertEquals( "0",stemp);
	} catch (ExceptionZZZ ez) {
		fail("Method throws an exception." + ez.getMessageLast());
	} 
	
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	//"SERIAL STRATEGIE"-Ergebnisse	
	try{		
		itemp = CounterByCharacterAscii_NumericZZZ.iPOSITION_MAX;
		stemp = CounterByCharacterAscii_NumericZZZ.getStringForNumber(itemp, true);
		btemp = assertCheckNullBordersNumericStrategyBased_(itemp, stemp);
		assertTrue("Fehler beim Check auf Null Werte", btemp);
		assertEquals("9",stemp);
	} catch (ExceptionZZZ ez) {
		fail("Method throws an exception." + ez.getMessageLast());
	} 
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++


	//######################
	//Nun Werte eingeben, die über 10 liegen. Diese müssen erlaubt sein.
	//+++++++++++++++++++++++++++++++++++++++++++++++++++

	//"SERIAL STRATEGIE"-Ergebnisse
	try{
		itemp = CounterByCharacterAscii_NumericZZZ.iPOSITION_MAX+1;
		stemp = CounterByCharacterAscii_NumericZZZ.getStringForNumber(itemp, true);
		btemp = assertCheckNullBordersNumericStrategyBased_(itemp, stemp);
		assertTrue("Fehler beim Check auf Null Werte", btemp);
		assertEquals("09",stemp);
	} catch (ExceptionZZZ ez) {
		fail("Method throws an exception." + ez.getMessageLast());
	} 

	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

	//... weitere Tests
		//"SERIAL STRATEGIE"-Ergebnisse
	try{
		itemp = CounterByCharacterAscii_NumericZZZ.iPOSITION_MAX+2;
		stemp = CounterByCharacterAscii_NumericZZZ.getStringForNumber(itemp, true);
		btemp = assertCheckNullBordersNumericStrategyBased_(itemp, stemp);
		assertTrue("Fehler beim Check auf Null Werte", btemp);
		assertEquals( "19",stemp);
	} catch (ExceptionZZZ ez) {
		fail("Method throws an exception." + ez.getMessageLast());
	} 
	}

public void testGetStringNumericForNumber_StrategyMultiple(){
	int itemp; String stemp; boolean btemp;
//"MULTIPLE STRATEGIE"-Ergebnisse
	try{
		itemp = CounterByCharacterAscii_NumericZZZ.iPOSITION_MIN-1;
		stemp = CounterByCharacterAscii_NumericZZZ.getStringMultipleForNumber(itemp);
		btemp = assertCheckNullBordersNumericStrategyBased_(itemp, stemp);
		assertTrue("Fehler beim Check auf Null Werte", btemp);
} catch (ExceptionZZZ ez) {
	fail("Method throws an exception." + ez.getMessageLast());
} 

//+++++++++++++++++++++++++++++++++++++++++++++++
	try{
		itemp = CounterByCharacterAscii_NumericZZZ.iPOSITION_MIN;
		stemp = CounterByCharacterAscii_NumericZZZ.getStringMultipleForNumber(itemp);
		btemp = assertCheckNullBordersNumericStrategyBased_(itemp, stemp);
		assertTrue("Fehler beim Check auf Null Werte", btemp);
		assertEquals( "0",stemp);
	} catch (ExceptionZZZ ez) {
		fail("Method throws an exception." + ez.getMessageLast());
	}
//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

	//"SERIAL STRATEGIE"-Ergebnisse
	try{
		itemp = CounterByCharacterAscii_NumericZZZ.iPOSITION_MAX;
		stemp = CounterByCharacterAscii_NumericZZZ.getStringMultipleForNumber(itemp);
		btemp = assertCheckNullBordersNumericStrategyBased_(itemp, stemp);
		assertTrue("Fehler beim Check auf Null Werte", btemp);
		assertEquals("9",stemp);
	} catch (ExceptionZZZ ez) {
		fail("Method throws an exception." + ez.getMessageLast());
	}

//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++


//######################
//Nun Werte eingeben, die über 10 liegen. Diese müssen erlaubt sein.
//+++++++++++++++++++++++++++++++++++++++++++++++++++

//"SERIAL STRATEGIE"-Ergebnisse
	try{
		itemp = CounterByCharacterAscii_NumericZZZ.iPOSITION_MAX+1;
		stemp = CounterByCharacterAscii_NumericZZZ.getStringMultipleForNumber(itemp);
		btemp = assertCheckNullBordersNumericStrategyBased_(itemp, stemp);
		assertTrue("Fehler beim Check auf Null Werte", btemp);
		assertEquals("00",stemp);
	} catch (ExceptionZZZ ez) {
		fail("Method throws an exception." + ez.getMessageLast());
	}
//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

//... weitere Tests
	//"SERIAL STRATEGIE"-Ergebnisse
	try{
		itemp = CounterByCharacterAscii_NumericZZZ.iPOSITION_MAX+2;
		stemp = CounterByCharacterAscii_NumericZZZ.getStringMultipleForNumber(itemp);
		btemp = assertCheckNullBordersNumericStrategyBased_(itemp, stemp);
		assertTrue("Fehler beim Check auf Null Werte", btemp);
		assertEquals( "11",stemp);
	} catch (ExceptionZZZ ez) {
		fail("Method throws an exception." + ez.getMessageLast());
	}
}


}//end class


