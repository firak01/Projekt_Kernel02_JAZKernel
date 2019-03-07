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

public class CounterByCharacterAscii_AlphanumericZZZTest  extends TestCase{
//	 private HashMapExtendedZZZ<String, EnumSetMappedTestTypeZZZ> hmTestGenerics = null;
	 
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
    		btemp = assertCheckNullBordersAlphanumeric_(itemp, stemp);
	    	assertTrue("Fehler beim Check auf Null Werte", btemp);
    		assertEquals("a",stemp);
    		
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
}//end class
	

