package basic.zBasic.util.datatype.counter;

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
    		if(iInput< CounterByCharacterAscii_AlphabetZZZ.iALPHABET_POSITION_MIN){
	    		assertNull("Bei <'" + CounterByCharacterAscii_AlphabetZZZ.iALPHABET_POSITION_MIN + "' wird NULL erwartet. Ergebnis '" + sResult + "' für " + iInput, sResult);
	    	}else if(iInput>CounterByCharacterAscii_AlphabetZZZ.iALPHABET_POSITION_MAX){
	    		assertNull("Bei >'" + CounterByCharacterAscii_AlphabetZZZ.iALPHABET_POSITION_MAX + "' wird NULL erwartet. Ergebnis '" + sResult + "' für " + iInput, sResult);
	    	}
	    		
    		if(iInput>=CounterByCharacterAscii_AlphabetZZZ.iALPHABET_POSITION_MIN && iInput <= CounterByCharacterAscii_AlphabetZZZ.iALPHABET_POSITION_MAX){
	    		assertNotNull("Bei >= '" + CounterByCharacterAscii_AlphabetZZZ.iALPHABET_POSITION_MIN +"' und <='" + CounterByCharacterAscii_AlphabetZZZ.iALPHABET_POSITION_MAX + "' wird keine NULL erwartet. Ergebnis '" + sResult + "' für " + iInput, sResult);
	    	}	    		
    		bReturn=true;
    			    		    	
    	}//end main:
    	return bReturn;	    	
    }
    
    private boolean assertCheckNullBordersAlphabet_(char cInput,int iResult){
    	boolean bReturn = false;
    	main:{
    		boolean bIsValidCharacter=CounterByCharacterAscii_AlphabetZZZ.isValidCharacter(cInput);    		
    		if(iResult< CounterByCharacterAscii_AlphabetZZZ.iALPHABET_POSITION_MIN){
    				assertFalse("Bei <'" + CounterByCharacterAscii_AlphabetZZZ.iALPHABET_POSITION_MIN + "' wird ein ungültiges Zeichen erwartet. Ergebnis '" + iResult + "' für " + cInput, bIsValidCharacter);
    		}else if(iResult>CounterByCharacterAscii_AlphabetZZZ.iALPHABET_POSITION_MAX){
    				assertFalse("Bei >'" + CounterByCharacterAscii_AlphabetZZZ.iALPHABET_POSITION_MIN + "' wird ein ungültiges Zeichen erwartet. Ergebnis '" + iResult + "' für " + cInput, bIsValidCharacter);
	    	}
	    		    		
    		bReturn=true;
    			    		    	
    	}//end main:
    	return bReturn;	    	
    }
    
    private boolean assertCheckNullBordersAlphabetStrategyBased_(int iInput, String sResult){
    	boolean bReturn = false;
    	main:{
    		if(iInput< CounterByCharacterAscii_AlphabetZZZ.iALPHABET_POSITION_MIN){
	    		assertNull("Bei <'" + CounterByCharacterAscii_AlphabetZZZ.iALPHABET_POSITION_MIN + "' wird NULL erwartet. Ergebnis '" + sResult + "' für " + iInput, sResult);
	    	}	
    		if(iInput>=CounterByCharacterAscii_AlphabetZZZ.iALPHABET_POSITION_MIN ){
	    		assertNotNull("Bei >= '" + CounterByCharacterAscii_AlphabetZZZ.iALPHABET_POSITION_MIN +"' wird keine NULL erwartet. Ergebnis '" + sResult + "' für " + iInput, sResult);
	    	}	     		
    		bReturn=true;
    			    		    	
    	}//end main:
    	return bReturn;	    	
    }
    
    /** Als Grundlage für den Konstruktor, bei dem das "Startzeichen" übergeben wird. 
     * 
     * @author Fritz Lindhauer, 16.03.2019, 08:34:53
     */
    public void testGetPositionInAlphabetForChar(){
    	char ctemp = '0';
    	int itemp = CounterByCharacterAscii_AlphabetZZZ.getPositionInAlphabetForChar(ctemp);
    	boolean btemp = assertCheckNullBordersAlphabet_(ctemp,itemp);
    	assertTrue("Fehler beim Check auf gültige Werte", btemp);
    	    	
    	//+++++++++++++++++++++++++++++++++++++++++
    	ctemp = 'A';
    	itemp = CounterByCharacterAscii_AlphabetZZZ.getPositionInAlphabetForChar(ctemp);
    	btemp = assertCheckNullBordersAlphabet_(ctemp,itemp);
    	assertTrue("Fehler beim Check auf gültige Werte", btemp);
    	assertEquals(1,itemp);
    	
    	ctemp = 'a';
    	itemp = CounterByCharacterAscii_AlphabetZZZ.getPositionInAlphabetForChar(ctemp);
    	btemp = assertCheckNullBordersAlphabet_(ctemp,itemp);
    	assertTrue("Fehler beim Check auf gültige Werte", btemp);
    	assertEquals(1,itemp);
    	
    	//++++++++++++++++++++++++++++++++++++++++
    	ctemp = 'Z';
    	itemp = CounterByCharacterAscii_AlphabetZZZ.getPositionInAlphabetForChar(ctemp);
    	btemp = assertCheckNullBordersAlphabet_(ctemp,itemp);
    	assertTrue("Fehler beim Check auf gültige Werte", btemp);
    	assertEquals(26,itemp);
    	
    	ctemp = 'z';
    	itemp = CounterByCharacterAscii_AlphabetZZZ.getPositionInAlphabetForChar(ctemp);
    	btemp = assertCheckNullBordersAlphabet_(ctemp,itemp);
    	assertTrue("Fehler beim Check auf gültige Werte", btemp);
    	assertEquals(26,itemp);
    	
    }
    
    public void testGetCharForPositionInAlphabet(){	    		    	
    	int itemp = CounterByCharacterAscii_AlphabetZZZ.iALPHABET_POSITION_MIN-1;
    	String stemp = CounterByCharacterAscii_AlphabetZZZ.getCharForPositionInAlphabet(itemp);
    	boolean btemp = assertCheckNullBordersAlphabet_(itemp, stemp);
    	assertTrue("Fehler beim Check auf Null Werte", btemp);
    	
    	//+++++++++++++++++++++++++++++++++++++++++++++++
    	itemp = CounterByCharacterAscii_AlphabetZZZ.iALPHABET_POSITION_MIN;
    	stemp = CounterByCharacterAscii_AlphabetZZZ.getCharForPositionInAlphabet(itemp);
    	btemp = assertCheckNullBordersAlphabet_(itemp, stemp);
    	assertTrue("Fehler beim Check auf Null Werte", btemp);
		assertEquals("A",stemp);
    	
		stemp = CounterByCharacterAscii_AlphabetZZZ.getCharForPositionInAlphabet(itemp, true); //Kleinbuchstaben
		btemp = assertCheckNullBordersAlphabet_(itemp, stemp);
    	assertTrue("Fehler beim Check auf Null Werte", btemp);
		assertEquals("a",stemp);
    	
		//+++++++++++++++++++++++++++++++++++++++++++++++++++
		itemp = CounterByCharacterAscii_AlphabetZZZ.iALPHABET_POSITION_MAX+1;
    	stemp = CounterByCharacterAscii_AlphabetZZZ.getCharForPositionInAlphabet(itemp);
    	btemp = assertCheckNullBordersAlphabet_(itemp, stemp);
    	assertTrue("Fehler beim Check auf Null Werte", btemp);
			    	
		//++++++++++++++++++++++++++++++++
		itemp = CounterByCharacterAscii_AlphabetZZZ.iALPHABET_POSITION_MAX;
    	stemp = CounterByCharacterAscii_AlphabetZZZ.getCharForPositionInAlphabet(itemp);
    	btemp = assertCheckNullBordersAlphabet_(itemp, stemp);
    	assertTrue("Fehler beim Check auf Null Werte", btemp);
		assertEquals("Z",stemp);
		
		stemp = CounterByCharacterAscii_AlphabetZZZ.getCharForPositionInAlphabet(itemp, true); //Kleinbuchstaben
		btemp = assertCheckNullBordersAlphabet_(itemp, stemp);
    	assertTrue("Fehler beim Check auf Null Werte", btemp);
		assertEquals("z",stemp);
    }
    
    
    
    
public void testGetStringAlphabetForNumber_StrategySerial(){
    	
		//"SERIAL STRATEGIE"-Ergebnisse
    	int itemp = CounterByCharacterAscii_AlphabetZZZ.iALPHABET_POSITION_MIN-1;
    	String stemp = CounterByCharacterAscii_AlphabetZZZ.getStringAlphabetForNumber(itemp);
    	boolean btemp = assertCheckNullBordersAlphabetStrategyBased_(itemp, stemp);
    	assertTrue("Fehler beim Check auf Null Werte", btemp);
    	
    	//+++++++++++++++++++++++++++++++++++++++++++++++
    	itemp = CounterByCharacterAscii_AlphabetZZZ.iALPHABET_POSITION_MIN;
    	
    	
    	stemp = CounterByCharacterAscii_AlphabetZZZ.getStringAlphabetForNumber(itemp);
    	btemp = assertCheckNullBordersAlphabetStrategyBased_(itemp, stemp);
    	assertTrue("Fehler beim Check auf Null Werte", btemp);
		assertEquals("A",stemp);
    	
		stemp = CounterByCharacterAscii_AlphabetZZZ.getStringAlphabetForNumber(itemp, true); //Kleinbuchstaben
		btemp = assertCheckNullBordersAlphabetStrategyBased_(itemp, stemp);
    	assertTrue("Fehler beim Check auf Null Werte", btemp);
		assertEquals("a",stemp);
    		    	
		//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		itemp = CounterByCharacterAscii_AlphabetZZZ.iALPHABET_POSITION_MAX;
		
		//"SERIAL STRATEGIE"-Ergebnisse
    	stemp = CounterByCharacterAscii_AlphabetZZZ.getStringAlphabetForNumber(itemp);
    	btemp = assertCheckNullBordersAlphabetStrategyBased_(itemp, stemp);
    	assertTrue("Fehler beim Check auf Null Werte", btemp);
		assertEquals("Z",stemp);
		
		stemp = CounterByCharacterAscii_AlphabetZZZ.getStringAlphabetForNumber(itemp, true); //Kleinbuchstaben
		btemp = assertCheckNullBordersAlphabetStrategyBased_(itemp, stemp);
    	assertTrue("Fehler beim Check auf Null Werte", btemp);
		assertEquals("z",stemp);

		//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    	
    	
		//######################
		//Nun Werte eingeben, die über 27 liegen. Diese müssen erlaubt sein.
		//+++++++++++++++++++++++++++++++++++++++++++++++++++
		
    	//"SERIAL STRATEGIE"-Ergebnisse
		itemp = CounterByCharacterAscii_AlphabetZZZ.iALPHABET_POSITION_MAX+1;
    	stemp = CounterByCharacterAscii_AlphabetZZZ.getStringAlphabetForNumber(itemp);
    	btemp = assertCheckNullBordersAlphabetStrategyBased_(itemp, stemp);
    	assertTrue("Fehler beim Check auf Null Werte", btemp);
    	assertEquals("ZA",stemp);
    	
    	stemp = CounterByCharacterAscii_AlphabetZZZ.getStringAlphabetForNumber(itemp, true); //Kleinbuchstaben
    	btemp = assertCheckNullBordersAlphabetStrategyBased_(itemp, stemp);
    	assertTrue("Fehler beim Check auf Null Werte", btemp);
    	assertEquals("za",stemp);
    	
		//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    	
    	//... weitere Tests
     	//"SERIAL STRATEGIE"-Ergebnisse
		itemp = CounterByCharacterAscii_AlphabetZZZ.iALPHABET_POSITION_MAX+2;
    	stemp = CounterByCharacterAscii_AlphabetZZZ.getStringAlphabetForNumber(itemp);
    	btemp = assertCheckNullBordersAlphabetStrategyBased_(itemp, stemp);
    	assertTrue("Fehler beim Check auf Null Werte", btemp);
    	assertEquals("ZB",stemp);
    	
    	stemp = CounterByCharacterAscii_AlphabetZZZ.getStringAlphabetForNumber(itemp, true); //Kleinbuchstaben
    	btemp = assertCheckNullBordersAlphabetStrategyBased_(itemp, stemp);
    	assertTrue("Fehler beim Check auf Null Werte", btemp);
    	assertEquals("zb",stemp);
    }


public void testGetStringAlphabetForNumber_StrategyMultiple(){

//"MULTIPLE STRATEGY"-Ergebnisse
int itemp = CounterByCharacterAscii_AlphabetZZZ.iALPHABET_POSITION_MIN-1;
String stemp = CounterByCharacterAscii_AlphabetZZZ.getStringAlphabetMultipleForNumber(itemp);
boolean btemp = assertCheckNullBordersAlphabetStrategyBased_(itemp, stemp);
assertTrue("Fehler beim Check auf Null Werte", btemp);

//+++++++++++++++++++++++++++++++++++++++++++++++
itemp = CounterByCharacterAscii_AlphabetZZZ.iALPHABET_POSITION_MIN;
	
//"MULTIPLE STRATEGY"-Ergebnisse
stemp = CounterByCharacterAscii_AlphabetZZZ.getStringAlphabetMultipleForNumber(itemp);
btemp = assertCheckNullBordersAlphabetStrategyBased_(itemp, stemp);
assertTrue("Fehler beim Check auf Null Werte", btemp);
assertEquals("A",stemp);

stemp = CounterByCharacterAscii_AlphabetZZZ.getStringAlphabetMultipleForNumber(itemp, true); //Kleinbuchstaben
btemp = assertCheckNullBordersAlphabetStrategyBased_(itemp, stemp);
assertTrue("Fehler beim Check auf Null Werte", btemp);
assertEquals("a",stemp);
	    	
//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
itemp = CounterByCharacterAscii_AlphabetZZZ.iALPHABET_POSITION_MAX;

//"MULTIPLE STRATEGY"-Ergebnisse
stemp = CounterByCharacterAscii_AlphabetZZZ.getStringAlphabetMultipleForNumber(itemp);
btemp = assertCheckNullBordersAlphabetStrategyBased_(itemp, stemp);
assertTrue("Fehler beim Check auf Null Werte", btemp);
assertEquals("Z",stemp);

stemp = CounterByCharacterAscii_AlphabetZZZ.getStringAlphabetMultipleForNumber(itemp, true); //Kleinbuchstaben
btemp = assertCheckNullBordersAlphabetStrategyBased_(itemp, stemp);
assertTrue("Fehler beim Check auf Null Werte", btemp);
assertEquals("z",stemp);
//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++


//######################
//Nun Werte eingeben, die über 27 liegen. Diese müssen erlaubt sein.
//+++++++++++++++++++++++++++++++++++++++++++++++++++
itemp = CounterByCharacterAscii_AlphabetZZZ.iALPHABET_POSITION_MAX+1;

//"MULTIPLE STRATEGY"-Ergebnisse
stemp = CounterByCharacterAscii_AlphabetZZZ.getStringAlphabetMultipleForNumber(itemp);
btemp = assertCheckNullBordersAlphabetStrategyBased_(itemp, stemp);
assertTrue("Fehler beim Check auf Null Werte", btemp);
assertEquals("AA",stemp);

stemp = CounterByCharacterAscii_AlphabetZZZ.getStringAlphabetMultipleForNumber(itemp, true); //Kleinbuchstaben
btemp = assertCheckNullBordersAlphabetStrategyBased_(itemp, stemp);
assertTrue("Fehler beim Check auf Null Werte", btemp);
assertEquals("aa",stemp);

//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

//... weitere Tests	
itemp = CounterByCharacterAscii_AlphabetZZZ.iALPHABET_POSITION_MAX+2;

//"MULTIPLE STRATEGY"-Ergebnisse
stemp = CounterByCharacterAscii_AlphabetZZZ.getStringAlphabetMultipleForNumber(itemp);
btemp = assertCheckNullBordersAlphabetStrategyBased_(itemp, stemp);
assertTrue("Fehler beim Check auf Null Werte", btemp);
assertEquals("BB",stemp);

stemp = CounterByCharacterAscii_AlphabetZZZ.getStringAlphabetMultipleForNumber(itemp, true); //Kleinbuchstaben
btemp = assertCheckNullBordersAlphabetStrategyBased_(itemp, stemp);
assertTrue("Fehler beim Check auf Null Werte", btemp);
assertEquals("bb",stemp);





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
