package basic.zBasic.util.datatype.counter;

import basic.zBasic.ExceptionZZZ;
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
			int iDiv = Math.abs(iResult / CounterByCharacterAscii_NumericZZZ.iNUMERIC_POSITION_MAX ); //durch abs wird also intern in ein Integer umgewandetl.... nicht nur das Weglassen des ggfs. negativen Vorzeichens.
			int iMod = iResult % CounterByCharacterAscii_NumericZZZ.iNUMERIC_POSITION_MAX;
    		
			if(iDiv==0 && iMod< CounterByCharacterAscii_NumericZZZ.iNUMERIC_POSITION_MIN){
	    		assertEquals("Bei <'" + CounterByCharacterAscii_NumericZZZ.iNUMERIC_POSITION_MIN + "' wird NULL erwartet. Ergebnis '" + iResult + "' für " + sInput, sInput);
	    	}
	    		
    		if(iMod>=CounterByCharacterAscii_NumericZZZ.iNUMERIC_POSITION_MIN){
	    		assertNotNull("Bei >= '" + CounterByCharacterAscii_NumericZZZ.iNUMERIC_POSITION_MIN + "' wird keine NULL erwartet. Ergebnis '" + iResult + "' für " + sInput, sInput);
	    	}	    		
    		bReturn=true;
    			    		    	
    	}//end main:
    	return bReturn;	    	
    }
    
    private boolean assertCheckRekonvert_(int itempNew, String sInputOld) throws ExceptionZZZ{
    	boolean bReturn = false;
    	
    	String stemp; boolean btemp; int itemp; int iInputOld;
    	main:{    		    		
    		//Test zurückkonvertieren
	    	stemp = CounterByCharacterAscii_NumericZZZ.getStringNumericForNumber(itempNew);
	    	btemp = assertCheckNullBordersNumeric_(itempNew,stemp);
	    	assertTrue("Fehler beim Check auf gültige Werte", btemp);
	    	assertEquals("Abweichung beim Zurückkonvertieren.",sInputOld,stemp);
	    	
	    	iInputOld = itempNew; 
	    	itemp = CounterByCharacterAscii_NumericZZZ.getNumberForStringNumeric(stemp);
	    	btemp = assertCheckNullBordersNumeric_(itemp,stemp);
	    	assertTrue("Fehler beim Check auf gültige Werte", btemp);
	    	assertEquals("Abweichung beim Zurückkonvertieren.",iInputOld,itemp);
	    	
	    	bReturn = true;
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
    
    public void testGetNumberForStringNumeric(){
    	String stemp; String stempold; int itemp; int itempold; boolean btemp;
    	try{
	    	stemp = "A";
	    	try{
		    	itemp = CounterByCharacterAscii_NumericZZZ.getNumberForStringNumeric(stemp);
		    	fail("Method should have thrown an exception for the string '"+stemp+"'");
	    	} catch (ExceptionZZZ ez) {
				//Erwartetete Exception
			} 
	    	
	    	stemp = "0Z";
	    	try{
		    	itemp = CounterByCharacterAscii_NumericZZZ.getNumberForStringNumeric(stemp);
		    	fail("Method should have thrown an exception for the string '"+stemp+"'");
	    	} catch (ExceptionZZZ ez) {
				//Erwartetete Exception
			} 
	    	
	    	stemp = "00"; //Serielle Strategie: Alle Zeichen links müssen den höchsten Wert haben. Korrekt wäre also "90"
	    	try{
		    	itemp = CounterByCharacterAscii_NumericZZZ.getNumberForStringNumeric(stemp);
		    	fail("Method should have thrown an exception for the string '"+stemp+"'");
	    	} catch (ExceptionZZZ ez) {
				//Erwartetete Exception
			} 
    		    		    		    	 
	    	//+++++++++++++++++++++++++++++++++++++++++
	    	stemp = "0";
	    	itemp = CounterByCharacterAscii_NumericZZZ.getNumberForStringNumeric(stemp);
	    	btemp = assertCheckNullBordersNumeric_(itemp,stemp);
	    	assertTrue("Fehler beim Check auf gültige Werte", btemp);
	    	assertEquals(1,itemp);
	    	btemp = assertCheckRekonvert_(itemp, stemp);
	    	assertTrue("Fehler beim Check auf gelungene Rekonvertierung", btemp);
	    	
	    	
	    	//++++++++++++++++++++++++++++++++++++++++
	    	stemp = "9";
	    	itemp = CounterByCharacterAscii_NumericZZZ.getNumberForStringNumeric(stemp);
	    	btemp = assertCheckNullBordersNumeric_(itemp,stemp);    	
	    	assertTrue("Fehler beim Check auf gültige Werte", btemp);
	    	assertEquals(10,itemp);
	    	btemp = assertCheckRekonvert_(itemp, stemp);
	    	assertTrue("Fehler beim Check auf gelungene Rekonvertierung", btemp);
	    	
	    	//++++++++++++++++++++++++++++++++++++++++++
	    	//Um +1 Weiter erhöht sollte sein ...
	    	stemp = "90";
	    	itemp = CounterByCharacterAscii_NumericZZZ.getNumberForStringNumeric(stemp);
	    	btemp = assertCheckNullBordersNumeric_(itemp,stemp);
	    	assertTrue("Fehler beim Check auf gültige Werte", btemp);
	    	assertEquals(10,itemp);
	    	btemp = assertCheckRekonvert_(itemp, stemp);
	    	assertTrue("Fehler beim Check auf gelungene Rekonvertierung", btemp);
	    	
    	} catch (ExceptionZZZ ez) {
    		fail("Method throws an exception." + ez.getMessageLast());
    	} 
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


