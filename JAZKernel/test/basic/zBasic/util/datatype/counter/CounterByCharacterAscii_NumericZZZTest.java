package basic.zBasic.util.datatype.counter;

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
    		if(iInput< CounterByCharacterAscii_NumericZZZ.iNUMERIC_POSITION_MIN){
	    		assertNull("Bei <'" + CounterByCharacterAscii_NumericZZZ.iNUMERIC_POSITION_MIN + "' wird NULL erwartet. Ergebnis '" + sResult + "' für " + iInput, sResult);
	    	}else if(iInput>CounterByCharacterAscii_NumericZZZ.iNUMERIC_POSITION_MAX){
	    		assertNull("Bei >'" + CounterByCharacterAscii_NumericZZZ.iNUMERIC_POSITION_MAX + "' wird NULL erwartet. Ergebnis '" + sResult + "' für " + iInput, sResult);
	    	}
	    		
    		if(iInput>=CounterByCharacterAscii_NumericZZZ.iNUMERIC_POSITION_MIN && iInput <= CounterByCharacterAscii_NumericZZZ.iNUMERIC_POSITION_MAX){
	    		assertNotNull("Bei >= '" + CounterByCharacterAscii_NumericZZZ.iNUMERIC_POSITION_MIN +"' und <='" + CounterByCharacterAscii_NumericZZZ.iNUMERIC_POSITION_MAX + "' wird keine NULL erwartet. Ergebnis '" + sResult + "' für " + iInput, sResult);
	    	}	    		
    		bReturn=true;
    			    		    	
    	}//end main:
    	return bReturn;	    	
    }
    private boolean assertCheckNullBordersNumeric_(char cInput,int iResult){
    	boolean bReturn = false;
    	main:{
    		boolean bIsValidCharacter=CounterByCharacterAscii_NumericZZZ.isValidCharacter(cInput);    		
    		if(iResult< CounterByCharacterAscii_NumericZZZ.iNUMERIC_POSITION_MIN){
    				assertFalse("Bei <'" + CounterByCharacterAscii_NumericZZZ.iNUMERIC_POSITION_MIN + "' wird ein ungültiges Zeichen erwartet. Ergebnis '" + iResult + "' für " + cInput, bIsValidCharacter);
    		}else if(iResult>CounterByCharacterAscii_NumericZZZ.iNUMERIC_POSITION_MAX){
    				assertFalse("Bei >'" + CounterByCharacterAscii_NumericZZZ.iNUMERIC_POSITION_MIN + "' wird ein ungültiges Zeichen erwartet. Ergebnis '" + iResult + "' für " + cInput, bIsValidCharacter);
	    	}
	    		    		
    		bReturn=true;
    			    		    	
    	}//end main:
    	return bReturn;	    	
    }
    private boolean assertCheckNullBordersNumericStrategyBased_(int iInput, String sResult){
    	boolean bReturn = false;
    	main:{
    		if(iInput< CounterByCharacterAscii_NumericZZZ.iNUMERIC_POSITION_MIN){
	    		assertNull("Bei <'" + CounterByCharacterAscii_NumericZZZ.iNUMERIC_POSITION_MIN + "' wird NULL erwartet. Ergebnis '" + sResult + "' für " + iInput, sResult);
	    	}
	    		
    		if(iInput>=CounterByCharacterAscii_NumericZZZ.iNUMERIC_POSITION_MIN ){
	    		assertNotNull("Bei >= '" + CounterByCharacterAscii_NumericZZZ.iNUMERIC_POSITION_MIN  + "' wird keine NULL erwartet. Ergebnis '" + sResult + "' für " + iInput, sResult);
	    	}	    		
    		bReturn=true;
    			    		    	
    	}//end main:
    	return bReturn;	    	
    }
    
    public void testGetPositionInAlphabetForChar(){
    	char ctemp = 'A';
    	int itemp = CounterByCharacterAscii_NumericZZZ.getPositionInNumericForChar(ctemp);
    	boolean btemp = assertCheckNullBordersNumeric_(ctemp,itemp);
    	assertTrue("Fehler beim Check auf gültige Werte", btemp);
    	    	
    	//+++++++++++++++++++++++++++++++++++++++++
    	ctemp = '0';
    	itemp = CounterByCharacterAscii_NumericZZZ.getPositionInNumericForChar(ctemp);
    	btemp = assertCheckNullBordersNumeric_(ctemp,itemp);
    	assertTrue("Fehler beim Check auf gültige Werte", btemp);
    	assertEquals(1,itemp);

    	//++++++++++++++++++++++++++++++++++++++++
    	ctemp = '9';
    	itemp = CounterByCharacterAscii_NumericZZZ.getPositionInNumericForChar(ctemp);
    	btemp = assertCheckNullBordersNumeric_(ctemp,itemp);
    	assertTrue("Fehler beim Check auf gültige Werte", btemp);
    	assertEquals(10,itemp);

    	
    }
    
    public void testGetCharForPositionInNumeric(){	    		    	
    	int itemp = CounterByCharacterAscii_NumericZZZ.iNUMERIC_POSITION_MIN-1;
    	String stemp = CounterByCharacterAscii_NumericZZZ.getCharForPositionInNumeric(itemp);
    	boolean btemp = assertCheckNullBordersNumeric_(itemp, stemp);
    	assertTrue("Fehler beim Check auf Null Werte", btemp);
    	
    	//+++++++++++++++++++++++++++++++++++++++++++++++
    	itemp = CounterByCharacterAscii_NumericZZZ.iNUMERIC_POSITION_MIN;
    	stemp = CounterByCharacterAscii_NumericZZZ.getCharForPositionInNumeric(itemp);
    	btemp = assertCheckNullBordersNumeric_(itemp, stemp);
    	assertTrue("Fehler beim Check auf Null Werte", btemp);
		assertEquals("0",stemp);
    	
			    	
		//+++++++++++++++++++++++++++++++++++++++++++++++++++
		itemp = CounterByCharacterAscii_NumericZZZ.iNUMERIC_POSITION_MAX+1;
    	stemp = CounterByCharacterAscii_NumericZZZ.getCharForPositionInNumeric(itemp);
    	btemp = assertCheckNullBordersNumeric_(itemp, stemp);
    	assertTrue("Fehler beim Check auf Null Werte", btemp);
			    	
		//++++++++++++++++++++++++++++++++
		itemp = CounterByCharacterAscii_NumericZZZ.iNUMERIC_POSITION_MAX;
    	stemp = CounterByCharacterAscii_NumericZZZ.getCharForPositionInNumeric(itemp);
        btemp = assertCheckNullBordersNumeric_(itemp, stemp);
    	assertTrue("Fehler beim Check auf Null Werte", btemp);
		assertEquals("9",stemp);
    }
    
public void testGetStringNumericForNumber_StrategySerial(){

//"SERIAL STRATEGIE"-Ergebnisse
int itemp = CounterByCharacterAscii_NumericZZZ.iNUMERIC_POSITION_MIN-1;
String stemp = CounterByCharacterAscii_NumericZZZ.getStringNumericForNumber(itemp);
boolean btemp = assertCheckNullBordersNumericStrategyBased_(itemp, stemp);
assertTrue("Fehler beim Check auf Null Werte", btemp);

//+++++++++++++++++++++++++++++++++++++++++++++++
itemp = CounterByCharacterAscii_NumericZZZ.iNUMERIC_POSITION_MIN;


stemp = CounterByCharacterAscii_NumericZZZ.getStringNumericForNumber(itemp);
btemp = assertCheckNullBordersNumericStrategyBased_(itemp, stemp);
assertTrue("Fehler beim Check auf Null Werte", btemp);
assertEquals( "0",stemp);

//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
itemp = CounterByCharacterAscii_NumericZZZ.iNUMERIC_POSITION_MAX;

//"SERIAL STRATEGIE"-Ergebnisse
stemp = CounterByCharacterAscii_NumericZZZ.getStringNumericForNumber(itemp);
btemp = assertCheckNullBordersNumericStrategyBased_(itemp, stemp);
assertTrue("Fehler beim Check auf Null Werte", btemp);
assertEquals("9",stemp);

//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++


//######################
//Nun Werte eingeben, die über 10 liegen. Diese müssen erlaubt sein.
//+++++++++++++++++++++++++++++++++++++++++++++++++++

//"SERIAL STRATEGIE"-Ergebnisse
itemp = CounterByCharacterAscii_NumericZZZ.iNUMERIC_POSITION_MAX+1;
stemp = CounterByCharacterAscii_NumericZZZ.getStringNumericForNumber(itemp);
btemp = assertCheckNullBordersNumericStrategyBased_(itemp, stemp);
assertTrue("Fehler beim Check auf Null Werte", btemp);
assertEquals("90",stemp);

//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

//... weitere Tests
	//"SERIAL STRATEGIE"-Ergebnisse
itemp = CounterByCharacterAscii_NumericZZZ.iNUMERIC_POSITION_MAX+2;
stemp = CounterByCharacterAscii_NumericZZZ.getStringNumericForNumber(itemp);
btemp = assertCheckNullBordersNumericStrategyBased_(itemp, stemp);
assertTrue("Fehler beim Check auf Null Werte", btemp);
assertEquals( "91",stemp);
}

public void testGetStringNumericForNumber_StrategyMultiple(){

//"MULTIPLE STRATEGIE"-Ergebnisse
int itemp = CounterByCharacterAscii_NumericZZZ.iNUMERIC_POSITION_MIN-1;
String stemp = CounterByCharacterAscii_NumericZZZ.getStringNumericMultipleForNumber(itemp);
boolean btemp = assertCheckNullBordersNumericStrategyBased_(itemp, stemp);
assertTrue("Fehler beim Check auf Null Werte", btemp);

//+++++++++++++++++++++++++++++++++++++++++++++++
itemp = CounterByCharacterAscii_NumericZZZ.iNUMERIC_POSITION_MIN;


stemp = CounterByCharacterAscii_NumericZZZ.getStringNumericMultipleForNumber(itemp);
btemp = assertCheckNullBordersNumericStrategyBased_(itemp, stemp);
assertTrue("Fehler beim Check auf Null Werte", btemp);
assertEquals( "0",stemp);

//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
itemp = CounterByCharacterAscii_NumericZZZ.iNUMERIC_POSITION_MAX;

//"SERIAL STRATEGIE"-Ergebnisse
stemp = CounterByCharacterAscii_NumericZZZ.getStringNumericMultipleForNumber(itemp);
btemp = assertCheckNullBordersNumericStrategyBased_(itemp, stemp);
assertTrue("Fehler beim Check auf Null Werte", btemp);
assertEquals("9",stemp);

//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++


//######################
//Nun Werte eingeben, die über 10 liegen. Diese müssen erlaubt sein.
//+++++++++++++++++++++++++++++++++++++++++++++++++++

//"SERIAL STRATEGIE"-Ergebnisse
itemp = CounterByCharacterAscii_NumericZZZ.iNUMERIC_POSITION_MAX+1;
stemp = CounterByCharacterAscii_NumericZZZ.getStringNumericMultipleForNumber(itemp);
btemp = assertCheckNullBordersNumericStrategyBased_(itemp, stemp);
assertTrue("Fehler beim Check auf Null Werte", btemp);
assertEquals("00",stemp);

//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

//... weitere Tests
	//"SERIAL STRATEGIE"-Ergebnisse
itemp = CounterByCharacterAscii_NumericZZZ.iNUMERIC_POSITION_MAX+2;
stemp = CounterByCharacterAscii_NumericZZZ.getStringNumericMultipleForNumber(itemp);
btemp = assertCheckNullBordersNumericStrategyBased_(itemp, stemp);
assertTrue("Fehler beim Check auf Null Werte", btemp);
assertEquals( "11",stemp);
}


}//end class


