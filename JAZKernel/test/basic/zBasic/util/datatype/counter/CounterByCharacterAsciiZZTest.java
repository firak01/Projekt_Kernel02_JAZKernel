package basic.zBasic.util.datatype.counter;

import java.util.EnumSet;
import java.util.Set;

import junit.framework.TestCase;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractEnum.EnumSetTestFactoryZZZ;
import basic.zBasic.util.abstractEnum.EnumSetMappedTestTypeZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetFactoryZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedZZZ;
import basic.zBasic.util.abstractList.HashMapExtendedZZZ;
import basic.zBasic.util.datatype.enums.EnumSetMappedUtilZZZ;

public class CounterByCharacterAsciiZZTest  extends TestCase{
	 private HashMapExtendedZZZ<String, EnumSetMappedTestTypeZZZ> hmTestGenerics = null;
	 
	    protected void setUp(){
	      
		//try {			
		
			//### Das spezielle Generics Testobjekt			
//			hmTestGenerics = new HashMapExtendedZZZ<String, EnumSetMappedTestTypeZZZ>();
//			
//			Set<EnumSetMappedTestTypeZZZ> allTypes = EnumSet.allOf(EnumSetMappedTestTypeZZZ.class);
//			for(EnumSetMappedTestTypeZZZ myType : allTypes) {
//				//String sType = myType.getAbbreviation();
//				String sName = myType.name();
//				hmTestGenerics.put(sName,myType);
//			}
			
		
			
		/*
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		} 
		*/
		
	}//END setup
	    
	    private boolean assertCheckNullBordersAlphabet_(int iInput, String sResult){
	    	boolean bReturn = false;
	    	main:{
	    		if(iInput< CounterByCharacterAsciiZZZ.iALPHABET_POSITION_MIN){
		    		assertNull("Bei <'" + CounterByCharacterAsciiZZZ.iALPHABET_POSITION_MIN + "' wird NULL erwartet. Ergebnis '" + sResult + "' für " + iInput, sResult);
		    	}else if(iInput>CounterByCharacterAsciiZZZ.iALPHABET_POSITION_MAX){
		    		assertNull("Bei >'" + CounterByCharacterAsciiZZZ.iALPHABET_POSITION_MAX + "' wird NULL erwartet. Ergebnis '" + sResult + "' für " + iInput, sResult);
		    	}
		    		
	    		if(iInput>=CounterByCharacterAsciiZZZ.iALPHABET_POSITION_MIN && iInput <= CounterByCharacterAsciiZZZ.iALPHABET_POSITION_MAX){
		    		assertNotNull("Bei >= '" + CounterByCharacterAsciiZZZ.iALPHABET_POSITION_MIN +"' und <='" + CounterByCharacterAsciiZZZ.iALPHABET_POSITION_MAX + "' wird keine NULL erwartet. Ergebnis '" + sResult + "' für " + iInput, sResult);
		    	}	    		
	    		bReturn=true;
	    			    		    	
	    	}//end main:
	    	return bReturn;	    	
	    }
	    
	    private boolean assertCheckNullBordersAlphabetStrategyBased_(int iInput, String sResult){
	    	boolean bReturn = false;
	    	main:{
	    		if(iInput< CounterByCharacterAsciiZZZ.iALPHABET_POSITION_MIN){
		    		assertNull("Bei <'" + CounterByCharacterAsciiZZZ.iALPHABET_POSITION_MIN + "' wird NULL erwartet. Ergebnis '" + sResult + "' für " + iInput, sResult);
		    	}	
	    		if(iInput>=CounterByCharacterAsciiZZZ.iALPHABET_POSITION_MIN ){
		    		assertNotNull("Bei >= '" + CounterByCharacterAsciiZZZ.iALPHABET_POSITION_MIN +"' wird keine NULL erwartet. Ergebnis '" + sResult + "' für " + iInput, sResult);
		    	}	     		
	    		bReturn=true;
	    			    		    	
	    	}//end main:
	    	return bReturn;	    	
	    }
	    
	    private boolean assertCheckNullBordersAlphanumeric_(int iInput, String sResult){
	    	boolean bReturn = false;
	    	main:{
	    		if(iInput< CounterByCharacterAsciiZZZ.iALPHANUMERIC_POSITION_MIN){
		    		assertNull("Bei <'" + CounterByCharacterAsciiZZZ.iALPHANUMERIC_POSITION_MIN + "' wird NULL erwartet. Ergebnis '" + sResult + "' für " + iInput, sResult);
		    	}else if(iInput>CounterByCharacterAsciiZZZ.iALPHANUMERIC_POSITION_MAX){
		    		assertNull("Bei >'" + CounterByCharacterAsciiZZZ.iALPHANUMERIC_POSITION_MAX + "' wird NULL erwartet. Ergebnis '" + sResult + "' für " + iInput, sResult);
		    	}
		    		
	    		if(iInput>=CounterByCharacterAsciiZZZ.iALPHANUMERIC_POSITION_MIN && iInput <= CounterByCharacterAsciiZZZ.iALPHANUMERIC_POSITION_MAX){
		    		assertNotNull("Bei >= '" + CounterByCharacterAsciiZZZ.iALPHANUMERIC_POSITION_MIN +"' und <='" + CounterByCharacterAsciiZZZ.iALPHANUMERIC_POSITION_MAX + "' wird keine NULL erwartet. Ergebnis '" + sResult + "' für " + iInput, sResult);
		    	}	    		
	    		bReturn=true;
	    			    		    	
	    	}//end main:
	    	return bReturn;	    	
	    }
	    
	    private boolean assertCheckNullBordersAlphanumericStrategyBased_(int iInput, String sResult){
	    	boolean bReturn = false;
	    	main:{
	    		if(iInput< CounterByCharacterAsciiZZZ.iALPHANUMERIC_POSITION_MIN){
		    		assertNull("Bei <'" + CounterByCharacterAsciiZZZ.iALPHANUMERIC_POSITION_MIN + "' wird NULL erwartet. Ergebnis '" + sResult + "' für " + iInput, sResult);
		    	}
		    		
	    		if(iInput>=CounterByCharacterAsciiZZZ.iALPHANUMERIC_POSITION_MIN){
		    		assertNotNull("Bei >= '" + CounterByCharacterAsciiZZZ.iALPHANUMERIC_POSITION_MIN + "' wird keine NULL erwartet. Ergebnis '" + sResult + "' für " + iInput, sResult);
		    	}	    		
	    		bReturn=true;
	    			    		    	
	    	}//end main:
	    	return bReturn;	    	
	    }
	    
	    private boolean assertCheckNullBordersNumeric_(int iInput, String sResult){
	    	boolean bReturn = false;
	    	main:{
	    		if(iInput< CounterByCharacterAsciiZZZ.iNUMERIC_POSITION_MIN){
		    		assertNull("Bei <'" + CounterByCharacterAsciiZZZ.iNUMERIC_POSITION_MIN + "' wird NULL erwartet. Ergebnis '" + sResult + "' für " + iInput, sResult);
		    	}else if(iInput>CounterByCharacterAsciiZZZ.iNUMERIC_POSITION_MAX){
		    		assertNull("Bei >'" + CounterByCharacterAsciiZZZ.iNUMERIC_POSITION_MAX + "' wird NULL erwartet. Ergebnis '" + sResult + "' für " + iInput, sResult);
		    	}
		    		
	    		if(iInput>=CounterByCharacterAsciiZZZ.iNUMERIC_POSITION_MIN && iInput <= CounterByCharacterAsciiZZZ.iNUMERIC_POSITION_MAX){
		    		assertNotNull("Bei >= '" + CounterByCharacterAsciiZZZ.iNUMERIC_POSITION_MIN +"' und <='" + CounterByCharacterAsciiZZZ.iNUMERIC_POSITION_MAX + "' wird keine NULL erwartet. Ergebnis '" + sResult + "' für " + iInput, sResult);
		    	}	    		
	    		bReturn=true;
	    			    		    	
	    	}//end main:
	    	return bReturn;	    	
	    }
	    private boolean assertCheckNullBordersNumericStrategyBased_(int iInput, String sResult){
	    	boolean bReturn = false;
	    	main:{
	    		if(iInput< CounterByCharacterAsciiZZZ.iNUMERIC_POSITION_MIN){
		    		assertNull("Bei <'" + CounterByCharacterAsciiZZZ.iNUMERIC_POSITION_MIN + "' wird NULL erwartet. Ergebnis '" + sResult + "' für " + iInput, sResult);
		    	}
		    		
	    		if(iInput>=CounterByCharacterAsciiZZZ.iNUMERIC_POSITION_MIN ){
		    		assertNotNull("Bei >= '" + CounterByCharacterAsciiZZZ.iNUMERIC_POSITION_MIN  + "' wird keine NULL erwartet. Ergebnis '" + sResult + "' für " + iInput, sResult);
		    	}	    		
	    		bReturn=true;
	    			    		    	
	    	}//end main:
	    	return bReturn;	    	
	    }
	    
	    
	    
	    
	    
	   
	    
	   
	    
	    
	    public void testGetCharForPositionInAlphabet(){	    		    	
	    	int itemp = CounterByCharacterAsciiZZZ.iALPHABET_POSITION_MIN-1;
	    	String stemp = CounterByCharacterAsciiZZZ.getCharForPositionInAlphabet(itemp);
	    	boolean btemp = assertCheckNullBordersAlphabet_(itemp, stemp);
	    	assertTrue("Fehler beim Check auf Null Werte", btemp);
	    	
	    	//+++++++++++++++++++++++++++++++++++++++++++++++
	    	itemp = CounterByCharacterAsciiZZZ.iALPHABET_POSITION_MIN;
	    	stemp = CounterByCharacterAsciiZZZ.getCharForPositionInAlphabet(itemp);
	    	btemp = assertCheckNullBordersAlphabet_(itemp, stemp);
	    	assertTrue("Fehler beim Check auf Null Werte", btemp);
    		assertEquals("A",stemp);
	    	
    		stemp = CounterByCharacterAsciiZZZ.getCharForPositionInAlphabet(itemp, true); //Kleinbuchstaben
    		btemp = assertCheckNullBordersAlphabet_(itemp, stemp);
	    	assertTrue("Fehler beim Check auf Null Werte", btemp);
    		assertEquals("a",stemp);
	    	
    		//+++++++++++++++++++++++++++++++++++++++++++++++++++
    		itemp = CounterByCharacterAsciiZZZ.iALPHABET_POSITION_MAX+1;
	    	stemp = CounterByCharacterAsciiZZZ.getCharForPositionInAlphabet(itemp);
	    	btemp = assertCheckNullBordersAlphabet_(itemp, stemp);
	    	assertTrue("Fehler beim Check auf Null Werte", btemp);
    			    	
    		//++++++++++++++++++++++++++++++++
    		itemp = CounterByCharacterAsciiZZZ.iALPHABET_POSITION_MAX;
	    	stemp = CounterByCharacterAsciiZZZ.getCharForPositionInAlphabet(itemp);
	    	btemp = assertCheckNullBordersAlphabet_(itemp, stemp);
	    	assertTrue("Fehler beim Check auf Null Werte", btemp);
    		assertEquals("Z",stemp);
    		
    		stemp = CounterByCharacterAsciiZZZ.getCharForPositionInAlphabet(itemp, true); //Kleinbuchstaben
    		btemp = assertCheckNullBordersAlphabet_(itemp, stemp);
	    	assertTrue("Fehler beim Check auf Null Werte", btemp);
    		assertEquals("z",stemp);
	    }
	    
	    public void testGetCharForPositionInAlphanumeric(){	    		    	
	    	int itemp = CounterByCharacterAsciiZZZ.iALPHANUMERIC_POSITION_MIN-1;
	    	String stemp = CounterByCharacterAsciiZZZ.getCharForPositionInAlphanumeric(itemp);
	    	boolean btemp = assertCheckNullBordersAlphanumeric_(itemp, stemp);
	    	assertTrue("Fehler beim Check auf Null Werte", btemp);
	    	
	    	//+++++++++++++++++++++++++++++++++++++++++++++++
	    	itemp = CounterByCharacterAsciiZZZ.iALPHANUMERIC_POSITION_MIN;
	    	stemp = CounterByCharacterAsciiZZZ.getCharForPositionInAlphanumeric(itemp);
	    	btemp = assertCheckNullBordersAlphanumeric_(itemp, stemp);
	    	assertTrue("Fehler beim Check auf Null Werte", btemp);
    		assertEquals("0",stemp);
	    	
    			    	
    		//+++++++++++++++++++++++++++++++++++++++++++++++++++
    		itemp = CounterByCharacterAsciiZZZ.iALPHANUMERIC_POSITION_MAX+1;
	    	stemp = CounterByCharacterAsciiZZZ.getCharForPositionInAlphanumeric(itemp);
	    	btemp = assertCheckNullBordersAlphanumeric_(itemp, stemp);
	    	assertTrue("Fehler beim Check auf Null Werte", btemp);
    			    	
    		//++++++++++++++++++++++++++++++++
    		itemp = CounterByCharacterAsciiZZZ.iALPHANUMERIC_POSITION_MAX;
	    	stemp = CounterByCharacterAsciiZZZ.getCharForPositionInAlphanumeric(itemp);
	        btemp = assertCheckNullBordersAlphanumeric_(itemp, stemp);
	    	assertTrue("Fehler beim Check auf Null Werte", btemp);
    		assertEquals("Z",stemp);
    		
	    	stemp = CounterByCharacterAsciiZZZ.getCharForPositionInAlphanumeric(itemp, true); //Kleinbuchstaben
	        btemp = assertCheckNullBordersAlphanumeric_(itemp, stemp);
	    	assertTrue("Fehler beim Check auf Null Werte", btemp);
    		assertEquals("z",stemp);
    		
    		
    		//++++ WEITER WERTE AN "DEN INNEREN RÄNDERN" (also die Stelle an der intern die Sonderzeichen ausgeschnitten werden.
    		itemp = CounterByCharacterAsciiZZZ.iNUMERIC_POSITION_MAX; //"9"....
    		stemp = CounterByCharacterAsciiZZZ.getCharForPositionInAlphanumeric(itemp);
	    	btemp = assertCheckNullBordersAlphanumeric_(itemp, stemp);
	    	assertTrue("Fehler beim Check auf Null Werte", btemp);
    		assertEquals("9",stemp);
    		
    		stemp = CounterByCharacterAsciiZZZ.getCharForPositionInAlphanumeric(itemp,true); //kleinbuschstaben, obwohl es dafür ("9") keine geben soll.
	    	btemp = assertCheckNullBordersAlphanumeric_(itemp, stemp);
	    	assertTrue("Fehler beim Check auf Null Werte", btemp);
    		assertEquals("9",stemp);
    		
    		//+++++++++
    		itemp = CounterByCharacterAsciiZZZ.iNUMERIC_POSITION_MAX+1; //"A"....
    		stemp = CounterByCharacterAsciiZZZ.getCharForPositionInAlphanumeric(itemp);
	    	btemp = assertCheckNullBordersAlphanumeric_(itemp, stemp);
	    	assertTrue("Fehler beim Check auf Null Werte", btemp);
    		assertEquals("A",stemp);
    		
    		stemp = CounterByCharacterAsciiZZZ.getCharForPositionInAlphanumeric(itemp, true); //Kleinbuchstaben
    		btemp = assertCheckNullBordersAlphabet_(itemp, stemp);
	    	assertTrue("Fehler beim Check auf Null Werte", btemp);
    		assertEquals("a",stemp);
    		
	    }
	    
	    public void testGetCharForPositionInNumeric(){	    		    	
	    	int itemp = CounterByCharacterAsciiZZZ.iNUMERIC_POSITION_MIN-1;
	    	String stemp = CounterByCharacterAsciiZZZ.getCharForPositionInNumeric(itemp);
	    	boolean btemp = assertCheckNullBordersNumeric_(itemp, stemp);
	    	assertTrue("Fehler beim Check auf Null Werte", btemp);
	    	
	    	//+++++++++++++++++++++++++++++++++++++++++++++++
	    	itemp = CounterByCharacterAsciiZZZ.iNUMERIC_POSITION_MIN;
	    	stemp = CounterByCharacterAsciiZZZ.getCharForPositionInNumeric(itemp);
	    	btemp = assertCheckNullBordersNumeric_(itemp, stemp);
	    	assertTrue("Fehler beim Check auf Null Werte", btemp);
    		assertEquals("0",stemp);
	    	
    			    	
    		//+++++++++++++++++++++++++++++++++++++++++++++++++++
    		itemp = CounterByCharacterAsciiZZZ.iNUMERIC_POSITION_MAX+1;
	    	stemp = CounterByCharacterAsciiZZZ.getCharForPositionInNumeric(itemp);
	    	btemp = assertCheckNullBordersNumeric_(itemp, stemp);
	    	assertTrue("Fehler beim Check auf Null Werte", btemp);
    			    	
    		//++++++++++++++++++++++++++++++++
    		itemp = CounterByCharacterAsciiZZZ.iNUMERIC_POSITION_MAX;
	    	stemp = CounterByCharacterAsciiZZZ.getCharForPositionInNumeric(itemp);
	        btemp = assertCheckNullBordersNumeric_(itemp, stemp);
	    	assertTrue("Fehler beim Check auf Null Werte", btemp);
    		assertEquals("9",stemp);
	    }
	    
	   
	    

	

public void testGetStringAlphanumericForNumber_StrategyMultiple(){
	
	//"MULTIPLE STRATEGY"-Ergebnisse
	int itemp = CounterByCharacterAsciiZZZ.iALPHANUMERIC_POSITION_MIN-1;
	String stemp = CounterByCharacterAsciiZZZ.getStringAlphanumericMultipleForNumber(itemp, false); // .getCharMultipleForNumber(itemp);
	boolean btemp = assertCheckNullBordersAlphanumericStrategyBased_(itemp, stemp);
	assertTrue("Fehler beim Check auf Null Werte", btemp);
	
	//+++++++++++++++++++++++++++++++++++++++++++++++
	itemp = CounterByCharacterAsciiZZZ.iALPHANUMERIC_POSITION_MIN;
		
	//"MULTIPLE STRATEGY"-Ergebnisse
	stemp = CounterByCharacterAsciiZZZ.getStringAlphanumericMultipleForNumber(itemp);
	btemp = assertCheckNullBordersAlphanumericStrategyBased_(itemp, stemp);
	assertTrue("Fehler beim Check auf Null Werte", btemp);
	assertEquals("0",stemp);
	
	stemp = CounterByCharacterAsciiZZZ.getStringAlphanumericMultipleForNumber(itemp, true); //Kleinbuchstaben
	btemp = assertCheckNullBordersAlphanumericStrategyBased_(itemp, stemp);
	assertTrue("Fehler beim Check auf Null Werte", btemp);
	assertEquals("0",stemp); //Kleinbuchstaben für Zahlen gibt es ja nicht.
		    	
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	itemp = CounterByCharacterAsciiZZZ.iALPHANUMERIC_POSITION_MAX;

	//"MULTIPLE STRATEGY"-Ergebnisse
	stemp = CounterByCharacterAsciiZZZ.getStringAlphanumericMultipleForNumber(itemp);
	btemp = assertCheckNullBordersAlphanumericStrategyBased_(itemp, stemp);
	assertTrue("Fehler beim Check auf Null Werte", btemp);
	assertEquals("Z",stemp);
	
	stemp = CounterByCharacterAsciiZZZ.getStringAlphanumericMultipleForNumber(itemp, true); //Kleinbuchstaben
	btemp = assertCheckNullBordersAlphanumericStrategyBased_(itemp, stemp);
	assertTrue("Fehler beim Check auf Null Werte", btemp);
	assertEquals("z",stemp);
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	
	
	//######################
	//Nun Werte eingeben, die über 27 liegen. Diese müssen erlaubt sein.
	//+++++++++++++++++++++++++++++++++++++++++++++++++++
	itemp = CounterByCharacterAsciiZZZ.iALPHANUMERIC_POSITION_MAX+1;
	
	//"MULTIPLE STRATEGY"-Ergebnisse
	stemp = CounterByCharacterAsciiZZZ.getStringAlphanumericMultipleForNumber(itemp);
	btemp = assertCheckNullBordersAlphanumericStrategyBased_(itemp, stemp);
	assertTrue("Fehler beim Check auf Null Werte", btemp);
	assertEquals("00",stemp);
	
	stemp = CounterByCharacterAsciiZZZ.getStringAlphanumericMultipleForNumber(itemp, true); //Kleinbuchstaben
	btemp = assertCheckNullBordersAlphanumericStrategyBased_(itemp, stemp);
	assertTrue("Fehler beim Check auf Null Werte", btemp);
	assertEquals("00",stemp); //Keine Kleinbuchstaben bei Ziffern
	
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	
	//... weitere Tests	
	itemp = CounterByCharacterAsciiZZZ.iALPHANUMERIC_POSITION_MAX+2;
	
	//"MULTIPLE STRATEGY"-Ergebnisse
	stemp = CounterByCharacterAsciiZZZ.getStringAlphanumericMultipleForNumber(itemp);
	btemp = assertCheckNullBordersAlphanumericStrategyBased_(itemp, stemp);
	assertTrue("Fehler beim Check auf Null Werte", btemp);
	assertEquals("11",stemp);
	
	stemp = CounterByCharacterAsciiZZZ.getStringAlphanumericMultipleForNumber(itemp, true); //Kleinbuchstaben
	btemp = assertCheckNullBordersAlphanumericStrategyBased_(itemp, stemp);
	assertTrue("Fehler beim Check auf Null Werte", btemp);
	assertEquals("11",stemp);
	
	//... weitere Tests	
	itemp = CounterByCharacterAsciiZZZ.iALPHANUMERIC_POSITION_MAX*2;
	
	//"MULTIPLE STRATEGY"-Ergebnisse
	stemp = CounterByCharacterAsciiZZZ.getStringAlphanumericMultipleForNumber(itemp);
	btemp = assertCheckNullBordersAlphanumericStrategyBased_(itemp, stemp);
	assertTrue("Fehler beim Check auf Null Werte", btemp);
	assertEquals("ZZ",stemp);
	
	stemp = CounterByCharacterAsciiZZZ.getStringAlphanumericMultipleForNumber(itemp, true); //Kleinbuchstaben
	btemp = assertCheckNullBordersAlphanumericStrategyBased_(itemp, stemp);
	assertTrue("Fehler beim Check auf Null Werte", btemp);
	assertEquals("zz",stemp);
}

public void testGetStringAlphanumericForNumber_StrategySerial(){
	
	//"SERIAL STRATEGIE"-Ergebnisse
	int itemp = CounterByCharacterAsciiZZZ.iALPHANUMERIC_POSITION_MIN-1;
	String stemp = CounterByCharacterAsciiZZZ.getStringAlphanumericForNumber(itemp);
	boolean btemp = assertCheckNullBordersAlphanumericStrategyBased_(itemp, stemp);
	assertTrue("Fehler beim Check auf Null Werte", btemp);
	
	//+++++++++++++++++++++++++++++++++++++++++++++++
	itemp = CounterByCharacterAsciiZZZ.iALPHANUMERIC_POSITION_MIN;
	
	
	stemp = CounterByCharacterAsciiZZZ.getStringAlphanumericForNumber(itemp);
	btemp = assertCheckNullBordersAlphanumericStrategyBased_(itemp, stemp);
	assertTrue("Fehler beim Check auf Null Werte", btemp);
	assertEquals("0",stemp);
	
	stemp = CounterByCharacterAsciiZZZ.getStringAlphanumericForNumber(itemp, true); //Kleinbuchstaben
	btemp = assertCheckNullBordersAlphanumericStrategyBased_(itemp, stemp);
	assertTrue("Fehler beim Check auf Null Werte", btemp);
	assertEquals("0",stemp); //die Kleinbuchstaben für Zahlen gibt es ja nicht
		    	
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	itemp = CounterByCharacterAsciiZZZ.iALPHANUMERIC_POSITION_MAX;
	
	//"SERIAL STRATEGIE"-Ergebnisse
	stemp = CounterByCharacterAsciiZZZ.getStringAlphanumericForNumber(itemp);
	btemp = assertCheckNullBordersAlphanumericStrategyBased_(itemp, stemp);
	assertTrue("Fehler beim Check auf Null Werte", btemp);
	assertEquals("Z",stemp);
	
	stemp = CounterByCharacterAsciiZZZ.getStringAlphanumericForNumber(itemp, true); //Kleinbuchstaben
	btemp = assertCheckNullBordersAlphanumericStrategyBased_(itemp, stemp);
	assertTrue("Fehler beim Check auf Null Werte", btemp);
	assertEquals("z",stemp);

	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	
	
	//######################
	//Nun Werte eingeben, die über 27 liegen. Diese müssen erlaubt sein.
	//+++++++++++++++++++++++++++++++++++++++++++++++++++
	
	//"SERIAL STRATEGIE"-Ergebnisse
	itemp = CounterByCharacterAsciiZZZ.iALPHANUMERIC_POSITION_MAX+1;
	stemp = CounterByCharacterAsciiZZZ.getStringAlphanumericForNumber(itemp);
	btemp = assertCheckNullBordersAlphanumericStrategyBased_(itemp, stemp);
	assertTrue("Fehler beim Check auf Null Werte", btemp);
	assertEquals("Z0",stemp);
	
	stemp = CounterByCharacterAsciiZZZ.getStringAlphanumericForNumber(itemp, true); //Kleinbuchstaben
	btemp = assertCheckNullBordersAlphanumericStrategyBased_(itemp, stemp);
	assertTrue("Fehler beim Check auf Null Werte", btemp);
	assertEquals("z0",stemp);
	
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	
	//... weitere Tests
 	//"SERIAL STRATEGIE"-Ergebnisse
	itemp = CounterByCharacterAsciiZZZ.iALPHANUMERIC_POSITION_MAX+2;
	stemp = CounterByCharacterAsciiZZZ.getStringAlphanumericForNumber(itemp);
	btemp = assertCheckNullBordersAlphanumericStrategyBased_(itemp, stemp);
	assertTrue("Fehler beim Check auf Null Werte", btemp);
	assertEquals("Z1",stemp);
	
	stemp = CounterByCharacterAsciiZZZ.getStringAlphanumericForNumber(itemp, true); //Kleinbuchstaben
	btemp = assertCheckNullBordersAlphanumericStrategyBased_(itemp, stemp);
	assertTrue("Fehler beim Check auf Null Werte", btemp);
	assertEquals("z1",stemp);
	
	//"SERIAL STRATEGIE"-Ergebnisse
	itemp = CounterByCharacterAsciiZZZ.iALPHANUMERIC_POSITION_MAX*2;
	stemp = CounterByCharacterAsciiZZZ.getStringAlphanumericForNumber(itemp);
	btemp = assertCheckNullBordersAlphanumericStrategyBased_(itemp, stemp);
	assertTrue("Fehler beim Check auf Null Werte", btemp);
	assertEquals("ZZ",stemp);
	
	stemp = CounterByCharacterAsciiZZZ.getStringAlphanumericForNumber(itemp, true); //Kleinbuchstaben
	btemp = assertCheckNullBordersAlphanumericStrategyBased_(itemp, stemp);
	assertTrue("Fehler beim Check auf Null Werte", btemp);
	assertEquals("zz",stemp);
}

	    
	    
public void testGetStringAlphabetForNumber_StrategySerial(){
	    	
			//"SERIAL STRATEGIE"-Ergebnisse
	    	int itemp = CounterByCharacterAsciiZZZ.iALPHABET_POSITION_MIN-1;
	    	String stemp = CounterByCharacterAsciiZZZ.getStringAlphabetForNumber(itemp);
	    	boolean btemp = assertCheckNullBordersAlphabetStrategyBased_(itemp, stemp);
	    	assertTrue("Fehler beim Check auf Null Werte", btemp);
	    	
	    	//+++++++++++++++++++++++++++++++++++++++++++++++
	    	itemp = CounterByCharacterAsciiZZZ.iALPHABET_POSITION_MIN;
	    	
	    	
	    	stemp = CounterByCharacterAsciiZZZ.getStringAlphabetForNumber(itemp);
	    	btemp = assertCheckNullBordersAlphabetStrategyBased_(itemp, stemp);
	    	assertTrue("Fehler beim Check auf Null Werte", btemp);
    		assertEquals("A",stemp);
	    	
    		stemp = CounterByCharacterAsciiZZZ.getStringAlphabetForNumber(itemp, true); //Kleinbuchstaben
    		btemp = assertCheckNullBordersAlphabetStrategyBased_(itemp, stemp);
	    	assertTrue("Fehler beim Check auf Null Werte", btemp);
    		assertEquals("a",stemp);
	    		    	
    		//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    		itemp = CounterByCharacterAsciiZZZ.iALPHABET_POSITION_MAX;
    		
    		//"SERIAL STRATEGIE"-Ergebnisse
	    	stemp = CounterByCharacterAsciiZZZ.getStringAlphabetForNumber(itemp);
	    	btemp = assertCheckNullBordersAlphabetStrategyBased_(itemp, stemp);
	    	assertTrue("Fehler beim Check auf Null Werte", btemp);
    		assertEquals("Z",stemp);
    		
    		stemp = CounterByCharacterAsciiZZZ.getStringAlphabetForNumber(itemp, true); //Kleinbuchstaben
    		btemp = assertCheckNullBordersAlphabetStrategyBased_(itemp, stemp);
	    	assertTrue("Fehler beim Check auf Null Werte", btemp);
    		assertEquals("z",stemp);

    		//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	    	
	    	
    		//######################
    		//Nun Werte eingeben, die über 27 liegen. Diese müssen erlaubt sein.
    		//+++++++++++++++++++++++++++++++++++++++++++++++++++
    		
	    	//"SERIAL STRATEGIE"-Ergebnisse
    		itemp = CounterByCharacterAsciiZZZ.iALPHABET_POSITION_MAX+1;
	    	stemp = CounterByCharacterAsciiZZZ.getStringAlphabetForNumber(itemp);
	    	btemp = assertCheckNullBordersAlphabetStrategyBased_(itemp, stemp);
	    	assertTrue("Fehler beim Check auf Null Werte", btemp);
	    	assertEquals("ZA",stemp);
	    	
	    	stemp = CounterByCharacterAsciiZZZ.getStringAlphabetForNumber(itemp, true); //Kleinbuchstaben
	    	btemp = assertCheckNullBordersAlphabetStrategyBased_(itemp, stemp);
	    	assertTrue("Fehler beim Check auf Null Werte", btemp);
	    	assertEquals("za",stemp);
	    	
    		//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	    	
	    	//... weitere Tests
	     	//"SERIAL STRATEGIE"-Ergebnisse
    		itemp = CounterByCharacterAsciiZZZ.iALPHABET_POSITION_MAX+2;
	    	stemp = CounterByCharacterAsciiZZZ.getStringAlphabetForNumber(itemp);
	    	btemp = assertCheckNullBordersAlphabetStrategyBased_(itemp, stemp);
	    	assertTrue("Fehler beim Check auf Null Werte", btemp);
	    	assertEquals("ZB",stemp);
	    	
	    	stemp = CounterByCharacterAsciiZZZ.getStringAlphabetForNumber(itemp, true); //Kleinbuchstaben
	    	btemp = assertCheckNullBordersAlphabetStrategyBased_(itemp, stemp);
	    	assertTrue("Fehler beim Check auf Null Werte", btemp);
	    	assertEquals("zb",stemp);
	    }
	

public void testGetStringAlphabetForNumber_StrategyMultiple(){
	
	//"MULTIPLE STRATEGY"-Ergebnisse
	int itemp = CounterByCharacterAsciiZZZ.iALPHABET_POSITION_MIN-1;
	String stemp = CounterByCharacterAsciiZZZ.getStringAlphabetMultipleForNumber(itemp);
	boolean btemp = assertCheckNullBordersAlphabetStrategyBased_(itemp, stemp);
	assertTrue("Fehler beim Check auf Null Werte", btemp);
	
	//+++++++++++++++++++++++++++++++++++++++++++++++
	itemp = CounterByCharacterAsciiZZZ.iALPHABET_POSITION_MIN;
		
	//"MULTIPLE STRATEGY"-Ergebnisse
	stemp = CounterByCharacterAsciiZZZ.getStringAlphabetMultipleForNumber(itemp);
	btemp = assertCheckNullBordersAlphabetStrategyBased_(itemp, stemp);
	assertTrue("Fehler beim Check auf Null Werte", btemp);
	assertEquals("A",stemp);
	
	stemp = CounterByCharacterAsciiZZZ.getStringAlphabetMultipleForNumber(itemp, true); //Kleinbuchstaben
	btemp = assertCheckNullBordersAlphabetStrategyBased_(itemp, stemp);
	assertTrue("Fehler beim Check auf Null Werte", btemp);
	assertEquals("a",stemp);
		    	
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	itemp = CounterByCharacterAsciiZZZ.iALPHABET_POSITION_MAX;

	//"MULTIPLE STRATEGY"-Ergebnisse
	stemp = CounterByCharacterAsciiZZZ.getStringAlphabetMultipleForNumber(itemp);
	btemp = assertCheckNullBordersAlphabetStrategyBased_(itemp, stemp);
	assertTrue("Fehler beim Check auf Null Werte", btemp);
	assertEquals("Z",stemp);
	
	stemp = CounterByCharacterAsciiZZZ.getStringAlphabetMultipleForNumber(itemp, true); //Kleinbuchstaben
	btemp = assertCheckNullBordersAlphabetStrategyBased_(itemp, stemp);
	assertTrue("Fehler beim Check auf Null Werte", btemp);
	assertEquals("z",stemp);
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	
	
	//######################
	//Nun Werte eingeben, die über 27 liegen. Diese müssen erlaubt sein.
	//+++++++++++++++++++++++++++++++++++++++++++++++++++
	itemp = CounterByCharacterAsciiZZZ.iALPHABET_POSITION_MAX+1;
	
	//"MULTIPLE STRATEGY"-Ergebnisse
	stemp = CounterByCharacterAsciiZZZ.getStringAlphabetMultipleForNumber(itemp);
	btemp = assertCheckNullBordersAlphabetStrategyBased_(itemp, stemp);
	assertTrue("Fehler beim Check auf Null Werte", btemp);
	assertEquals("AA",stemp);
	
	stemp = CounterByCharacterAsciiZZZ.getStringAlphabetMultipleForNumber(itemp, true); //Kleinbuchstaben
	btemp = assertCheckNullBordersAlphabetStrategyBased_(itemp, stemp);
	assertTrue("Fehler beim Check auf Null Werte", btemp);
	assertEquals("aa",stemp);
	
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	
	//... weitere Tests	
	itemp = CounterByCharacterAsciiZZZ.iALPHABET_POSITION_MAX+2;
	
	//"MULTIPLE STRATEGY"-Ergebnisse
	stemp = CounterByCharacterAsciiZZZ.getStringAlphabetMultipleForNumber(itemp);
	btemp = assertCheckNullBordersAlphabetStrategyBased_(itemp, stemp);
	assertTrue("Fehler beim Check auf Null Werte", btemp);
	assertEquals("BB",stemp);
	
	stemp = CounterByCharacterAsciiZZZ.getStringAlphabetMultipleForNumber(itemp, true); //Kleinbuchstaben
	btemp = assertCheckNullBordersAlphabetStrategyBased_(itemp, stemp);
	assertTrue("Fehler beim Check auf Null Werte", btemp);
	assertEquals("bb",stemp);
	
	
	
	
	
//	try{
//		boolean btemp;
//		
//		//Static Zugriff
//		//EnumSet soll von der EnumerationZZZ-Klasse schon "generisch/per Reflektion" erzeugt und mitgebracht werden.
//		EnumSet setEnumGenerated = EnumSetMappedTestTypeZZZ.getEnumSet();		
//		int iSize = setEnumGenerated.size();
//		assertTrue("3 Elemente im Set erwartet.", iSize==3);
//		
//		//Generischer Zugriff, ist  nicht ganz möglich, darum Verwendung der Factory Klasse
//		IEnumSetFactoryZZZ objFactory = EnumSetTestFactoryZZZ.getInstance();				
//		Class objClass = EnumSetMappedTestTypeZZZ.class;
//		EnumSetMappedUtilZZZ enumSetUtil = new EnumSetMappedUtilZZZ(objFactory, objClass);
//		
//		//Beim Wiederholten Zugriff über die Util-Klasse soll das einmal erstellte EnumSet wiederverwendet werden.	
//		EnumSet setEnumReused = enumSetUtil.getEnumSetCurrent();
//		assertNotNull(setEnumReused);
//		
//		int iSizeReused = setEnumReused.size();
//		assertTrue("3 Elemente im SetReused erwartet.", iSizeReused==3);				
//	
//	} catch (ExceptionZZZ ez) {
//		fail("Method throws an exception." + ez.getMessageLast());
//	} 
}

public void testGetStringNumericForNumber_StrategySerial(){
	
	//"SERIAL STRATEGIE"-Ergebnisse
	int itemp = CounterByCharacterAsciiZZZ.iNUMERIC_POSITION_MIN-1;
	String stemp = CounterByCharacterAsciiZZZ.getStringNumericForNumber(itemp);
	boolean btemp = assertCheckNullBordersNumericStrategyBased_(itemp, stemp);
	assertTrue("Fehler beim Check auf Null Werte", btemp);
	
	//+++++++++++++++++++++++++++++++++++++++++++++++
	itemp = CounterByCharacterAsciiZZZ.iNUMERIC_POSITION_MIN;
	
	
	stemp = CounterByCharacterAsciiZZZ.getStringNumericForNumber(itemp);
	btemp = assertCheckNullBordersNumericStrategyBased_(itemp, stemp);
	assertTrue("Fehler beim Check auf Null Werte", btemp);
	assertEquals( "0",stemp);
	
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	itemp = CounterByCharacterAsciiZZZ.iNUMERIC_POSITION_MAX;
	
	//"SERIAL STRATEGIE"-Ergebnisse
	stemp = CounterByCharacterAsciiZZZ.getStringNumericForNumber(itemp);
	btemp = assertCheckNullBordersNumericStrategyBased_(itemp, stemp);
	assertTrue("Fehler beim Check auf Null Werte", btemp);
	assertEquals("9",stemp);
	
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	
	
	//######################
	//Nun Werte eingeben, die über 10 liegen. Diese müssen erlaubt sein.
	//+++++++++++++++++++++++++++++++++++++++++++++++++++
	
	//"SERIAL STRATEGIE"-Ergebnisse
	itemp = CounterByCharacterAsciiZZZ.iNUMERIC_POSITION_MAX+1;
	stemp = CounterByCharacterAsciiZZZ.getStringNumericForNumber(itemp);
	btemp = assertCheckNullBordersNumericStrategyBased_(itemp, stemp);
	assertTrue("Fehler beim Check auf Null Werte", btemp);
	assertEquals("90",stemp);
	
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	
	//... weitere Tests
 	//"SERIAL STRATEGIE"-Ergebnisse
	itemp = CounterByCharacterAsciiZZZ.iNUMERIC_POSITION_MAX+2;
	stemp = CounterByCharacterAsciiZZZ.getStringNumericForNumber(itemp);
	btemp = assertCheckNullBordersNumericStrategyBased_(itemp, stemp);
	assertTrue("Fehler beim Check auf Null Werte", btemp);
	assertEquals( "91",stemp);
}

public void testGetStringNumericForNumber_StrategyMultiple(){
	
	//"MULTIPLE STRATEGIE"-Ergebnisse
	int itemp = CounterByCharacterAsciiZZZ.iNUMERIC_POSITION_MIN-1;
	String stemp = CounterByCharacterAsciiZZZ.getStringNumericMultipleForNumber(itemp);
	boolean btemp = assertCheckNullBordersNumericStrategyBased_(itemp, stemp);
	assertTrue("Fehler beim Check auf Null Werte", btemp);
	
	//+++++++++++++++++++++++++++++++++++++++++++++++
	itemp = CounterByCharacterAsciiZZZ.iNUMERIC_POSITION_MIN;
	
	
	stemp = CounterByCharacterAsciiZZZ.getStringNumericMultipleForNumber(itemp);
	btemp = assertCheckNullBordersNumericStrategyBased_(itemp, stemp);
	assertTrue("Fehler beim Check auf Null Werte", btemp);
	assertEquals( "0",stemp);
	
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	itemp = CounterByCharacterAsciiZZZ.iNUMERIC_POSITION_MAX;
	
	//"SERIAL STRATEGIE"-Ergebnisse
	stemp = CounterByCharacterAsciiZZZ.getStringNumericMultipleForNumber(itemp);
	btemp = assertCheckNullBordersNumericStrategyBased_(itemp, stemp);
	assertTrue("Fehler beim Check auf Null Werte", btemp);
	assertEquals("9",stemp);
	
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	
	
	//######################
	//Nun Werte eingeben, die über 10 liegen. Diese müssen erlaubt sein.
	//+++++++++++++++++++++++++++++++++++++++++++++++++++
	
	//"SERIAL STRATEGIE"-Ergebnisse
	itemp = CounterByCharacterAsciiZZZ.iNUMERIC_POSITION_MAX+1;
	stemp = CounterByCharacterAsciiZZZ.getStringNumericMultipleForNumber(itemp);
	btemp = assertCheckNullBordersNumericStrategyBased_(itemp, stemp);
	assertTrue("Fehler beim Check auf Null Werte", btemp);
	assertEquals("00",stemp);
	
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	
	//... weitere Tests
 	//"SERIAL STRATEGIE"-Ergebnisse
	itemp = CounterByCharacterAsciiZZZ.iNUMERIC_POSITION_MAX+2;
	stemp = CounterByCharacterAsciiZZZ.getStringNumericMultipleForNumber(itemp);
	btemp = assertCheckNullBordersNumericStrategyBased_(itemp, stemp);
	assertTrue("Fehler beim Check auf Null Werte", btemp);
	assertEquals( "11",stemp);
}


	}//end class
	

