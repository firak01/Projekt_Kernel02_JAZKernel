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
import basic.zBasic.util.datatype.character.CharZZZ;
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
	    
	    private boolean assertCheckNullBordersAlphanumeric_(char cInput,int iResult){
	    	boolean bReturn = false;
	    	main:{
	    		int iAsciiBase = '0';
	    		int iOffset = 0;
	    		boolean bIsLowercase = CharZZZ.isLowercase(cInput);
	    		boolean bIsNumeric = CharZZZ.isNumeric(cInput);
	    		if (bIsNumeric){
	    			iOffset = iAsciiBase-1; 			
	    		}else if(bIsLowercase){
	    			iOffset = 7+7+iAsciiBase-1;
	    		}else{
	    			iOffset = 7+iAsciiBase-1;	
	    		}
	    		
	    			    		
	    		boolean bIsValidCharacter=CounterByCharacterAscii_AlphanumericZZZ.isValidCharacter(cInput);
	    		if(iResult+iOffset>='0' && iResult+iOffset<='9'){
	    			assertTrue("Bei >='0' und <='9' wird ein gültiges Zeichen erwartet. Ergebnis '" + iResult + "' für " + cInput, bIsValidCharacter);					
				}else if(iResult+iOffset>='A' && iResult+iOffset <='Z'){
					assertTrue("Bei >='A' und <='Z' wird ein gültiges Zeichen erwartet. Ergebnis '" + iResult + "' für " + cInput, bIsValidCharacter);
				}else if(iResult+iOffset>='a' && iResult+iOffset <= 'z'){
					assertTrue("Bei >='a' und <='z' wird ein gültiges Zeichen erwartet. Ergebnis '" + iResult + "' für " + cInput, bIsValidCharacter);
				}else{
					assertFalse("Es wurde ein ungültiges Zeichen erwartet. Ergebnis '" + iResult + "' für " + cInput, bIsValidCharacter);					
				}	    		
	    		bReturn=true;
	    			    		    	
	    	}//end main:
	    	return bReturn;	    	
	    }
	    
	    private boolean assertCheckNullBordersAlphanumeric_(int iInput, String sResult){
	    	boolean bReturn = false;
	    	main:{
	    		if(iInput< CounterByCharacterAscii_AlphanumericZZZ.iALPHANUMERIC_POSITION_MIN){
		    		assertNull("Bei <'" + CounterByCharacterAscii_AlphanumericZZZ.iALPHANUMERIC_POSITION_MIN + "' wird NULL erwartet. Ergebnis '" + sResult + "' für " + iInput, sResult);
		    	}else if(iInput>CounterByCharacterAscii_AlphanumericZZZ.iALPHANUMERIC_POSITION_MAX){
		    		assertNull("Bei >'" + CounterByCharacterAscii_AlphanumericZZZ.iALPHANUMERIC_POSITION_MAX + "' wird NULL erwartet. Ergebnis '" + sResult + "' für " + iInput, sResult);
		    	}   		
	    		bReturn=true;
	    			    		    	
	    	}//end main:
	    	return bReturn;	    	
	    }	    	    
	    
	    private boolean assertCheckNullBordersAlphanumericStrategyBased_(int iInput, String sResult){
	    	boolean bReturn = false;
	    	main:{
	    		if(iInput< CounterByCharacterAscii_AlphanumericZZZ.iALPHANUMERIC_POSITION_MIN){
		    		assertNull("Bei <'" + CounterByCharacterAscii_AlphanumericZZZ.iALPHANUMERIC_POSITION_MIN + "' wird NULL erwartet. Ergebnis '" + sResult + "' für " + iInput, sResult);
		    	}
		    		
	    		if(iInput>=CounterByCharacterAscii_AlphanumericZZZ.iALPHANUMERIC_POSITION_MIN){
		    		assertNotNull("Bei >= '" + CounterByCharacterAscii_AlphanumericZZZ.iALPHANUMERIC_POSITION_MIN + "' wird keine NULL erwartet. Ergebnis '" + sResult + "' für " + iInput, sResult);
		    	}	    		
	    		bReturn=true;
	    			    		    	
	    	}//end main:
	    	return bReturn;	    	
	    }
	    
	    private boolean assertCheckNullBordersAlphanumericStrategyBased_(String sInput, int iResult){
	    	boolean bReturn = false;
	    	main:{
	    		
	    		//bReturn=true;
	    			    		    	
	    	}//end main:
	    	return bReturn;	    	
	    }
	    
	    /** Als Grundlage für den Konstruktor, bei dem das "Startzeichen" übergeben wird. 
	     * 
	     * @author Fritz Lindhauer, 16.03.2019, 08:34:53
	     */
	    public void testGetPositionInAlphanumericForChar(){
	    	//Grenzwerte: Geholt aus der ASCII Tabelle
	    	char ctemp = '/';
	    	int itemp = CounterByCharacterAscii_AlphanumericZZZ.getPositionInAlphanumericForChar(ctemp);
	    	boolean btemp = assertCheckNullBordersAlphanumeric_(ctemp,itemp);
	    	assertTrue("Fehler beim Check auf gültige Werte", btemp);
	    	
	    	ctemp = ':';
	    	itemp = CounterByCharacterAscii_AlphanumericZZZ.getPositionInAlphanumericForChar(ctemp);
	    	btemp = assertCheckNullBordersAlphanumeric_(ctemp,itemp);
	    	assertTrue("Fehler beim Check auf gültige Werte", btemp);
	    	
	    	ctemp = '@';
	    	itemp = CounterByCharacterAscii_AlphanumericZZZ.getPositionInAlphanumericForChar(ctemp);
	    	btemp = assertCheckNullBordersAlphanumeric_(ctemp,itemp);
	    	assertTrue("Fehler beim Check auf gültige Werte", btemp);
	    	
	    	ctemp = '[';
	    	itemp = CounterByCharacterAscii_AlphanumericZZZ.getPositionInAlphanumericForChar(ctemp);
	    	btemp = assertCheckNullBordersAlphanumeric_(ctemp,itemp);
	    	assertTrue("Fehler beim Check auf gültige Werte", btemp);
	    	
	    	ctemp = '´';
	    	itemp = CounterByCharacterAscii_AlphanumericZZZ.getPositionInAlphanumericForChar(ctemp);
	    	btemp = assertCheckNullBordersAlphanumeric_(ctemp,itemp);
	    	assertTrue("Fehler beim Check auf gültige Werte", btemp);
	    	    	
	    	ctemp = '{';
	    	itemp = CounterByCharacterAscii_AlphanumericZZZ.getPositionInAlphanumericForChar(ctemp);
	    	btemp = assertCheckNullBordersAlphanumeric_(ctemp,itemp);
	    	assertTrue("Fehler beim Check auf gültige Werte", btemp); 
	    	
	    	//+++++++++++++++++++++++++++++++++++++++++
	    	ctemp = '0';
	    	itemp = CounterByCharacterAscii_AlphanumericZZZ.getPositionInAlphanumericForChar(ctemp);
	    	btemp = assertCheckNullBordersAlphanumeric_(ctemp,itemp);
	    	assertTrue("Fehler beim Check auf gültige Werte", btemp);
	    	assertEquals(1,itemp);
	    	
	    	ctemp = '9';
	    	itemp = CounterByCharacterAscii_AlphanumericZZZ.getPositionInAlphanumericForChar(ctemp);
	    	btemp = assertCheckNullBordersAlphanumeric_(ctemp,itemp);
	    	assertTrue("Fehler beim Check auf gültige Werte", btemp);
	    	assertEquals(10,itemp);
	    	
	    	//+++++++++++++++++++++++++++++++++++++++++
	    	ctemp = 'A';
	    	itemp = CounterByCharacterAscii_AlphanumericZZZ.getPositionInAlphanumericForChar(ctemp);
	    	btemp = assertCheckNullBordersAlphanumeric_(ctemp,itemp);
	    	assertTrue("Fehler beim Check auf gültige Werte", btemp);
	    	assertEquals(11,itemp);
	    	
	    	ctemp = 'a';
	    	itemp = CounterByCharacterAscii_AlphanumericZZZ.getPositionInAlphanumericForChar(ctemp);
	    	btemp = assertCheckNullBordersAlphanumeric_(ctemp,itemp);
	    	assertTrue("Fehler beim Check auf gültige Werte", btemp);
	    	assertEquals(11,itemp);
	    	
	    	//++++++++++++++++++++++++++++++++++++++++
	    	ctemp = 'Z';
	    	itemp = CounterByCharacterAscii_AlphanumericZZZ.getPositionInAlphanumericForChar(ctemp);
	    	btemp = assertCheckNullBordersAlphanumeric_(ctemp,itemp);
	    	assertTrue("Fehler beim Check auf gültige Werte", btemp);
	    	assertEquals(36,itemp);
	    	
	    	ctemp = 'z';
	    	itemp = CounterByCharacterAscii_AlphanumericZZZ.getPositionInAlphanumericForChar(ctemp);
	    	btemp = assertCheckNullBordersAlphanumeric_(ctemp,itemp);
	    	assertTrue("Fehler beim Check auf gültige Werte", btemp);
	    	assertEquals(36,itemp);
	    	
	    }
	    
	    public void testGetCharForPositionInAlphanumeric(){	    		    	
	    	int itemp = CounterByCharacterAscii_AlphanumericZZZ.iALPHANUMERIC_POSITION_MIN-1;
	    	String stemp = CounterByCharacterAscii_AlphanumericZZZ.getCharForPositionInAlphanumeric(itemp);
	    	boolean btemp = assertCheckNullBordersAlphanumeric_(itemp, stemp);
	    	assertTrue("Fehler beim Check auf Null Werte", btemp);
	    	
	    	//+++++++++++++++++++++++++++++++++++++++++++++++
	    	itemp = CounterByCharacterAscii_AlphanumericZZZ.iALPHANUMERIC_POSITION_MIN;
	    	stemp = CounterByCharacterAscii_AlphanumericZZZ.getCharForPositionInAlphanumeric(itemp);
	    	btemp = assertCheckNullBordersAlphanumeric_(itemp, stemp);
	    	assertTrue("Fehler beim Check auf Null Werte", btemp);
    		assertEquals("0",stemp);
	    	
    			    	
    		//+++++++++++++++++++++++++++++++++++++++++++++++++++
    		itemp = CounterByCharacterAscii_AlphanumericZZZ.iALPHANUMERIC_POSITION_MAX+1;
	    	stemp = CounterByCharacterAscii_AlphanumericZZZ.getCharForPositionInAlphanumeric(itemp);
	    	btemp = assertCheckNullBordersAlphanumeric_(itemp, stemp);
	    	assertTrue("Fehler beim Check auf Null Werte", btemp);
    			    	
    		//++++++++++++++++++++++++++++++++
    		itemp = CounterByCharacterAscii_AlphanumericZZZ.iALPHANUMERIC_POSITION_MAX;
	    	stemp = CounterByCharacterAscii_AlphanumericZZZ.getCharForPositionInAlphanumeric(itemp);
	        btemp = assertCheckNullBordersAlphanumeric_(itemp, stemp);
	    	assertTrue("Fehler beim Check auf Null Werte", btemp);
    		assertEquals("Z",stemp);
    		
	    	stemp = CounterByCharacterAscii_AlphanumericZZZ.getCharForPositionInAlphanumeric(itemp, true); //Kleinbuchstaben
	        btemp = assertCheckNullBordersAlphanumeric_(itemp, stemp);
	    	assertTrue("Fehler beim Check auf Null Werte", btemp);
    		assertEquals("z",stemp);
    		
    		
    		//++++ WEITER WERTE AN "DEN INNEREN RÄNDERN" (also die Stelle an der intern die Sonderzeichen ausgeschnitten werden.
    		itemp = CounterByCharacterAscii_NumericZZZ.iNUMERIC_POSITION_MAX; //"9"....
    		stemp = CounterByCharacterAscii_AlphanumericZZZ.getCharForPositionInAlphanumeric(itemp);
	    	btemp = assertCheckNullBordersAlphanumeric_(itemp, stemp);
	    	assertTrue("Fehler beim Check auf Null Werte", btemp);
    		assertEquals("9",stemp);
    		
    		stemp = CounterByCharacterAscii_AlphanumericZZZ.getCharForPositionInAlphanumeric(itemp,true); //kleinbuschstaben, obwohl es dafür ("9") keine geben soll.
	    	btemp = assertCheckNullBordersAlphanumeric_(itemp, stemp);
	    	assertTrue("Fehler beim Check auf Null Werte", btemp);
    		assertEquals("9",stemp);
    		
    		//+++++++++
    		itemp = CounterByCharacterAscii_NumericZZZ.iNUMERIC_POSITION_MAX+1; //"A"....
    		stemp = CounterByCharacterAscii_AlphanumericZZZ.getCharForPositionInAlphanumeric(itemp);
	    	btemp = assertCheckNullBordersAlphanumeric_(itemp, stemp);
	    	assertTrue("Fehler beim Check auf Null Werte", btemp);
    		assertEquals("A",stemp);
    		
    		stemp = CounterByCharacterAscii_AlphanumericZZZ.getCharForPositionInAlphanumeric(itemp, true); //Kleinbuchstaben
    		btemp = assertCheckNullBordersAlphanumeric_(itemp, stemp);
	    	assertTrue("Fehler beim Check auf Null Werte", btemp);
    		assertEquals("a",stemp);
    		
	    }
	    
	   
	    public void testGetNumberForStringAlphanumeric_StrategyMultiple(){
	    	//TODO GOON 20190317:
	    	
	    	
	    	//"MULTIPLE STRATEGY"-Ergebnisse
	    	String stemp = "00";
	    	int itemp = CounterByCharacterAscii_AlphanumericZZZ.getNumberForStringAlphanumericMultiple(itemp, false); // .getCharMultipleForNumber(itemp);
	    	boolean btemp = assertCheckNullBordersAlphanumericStrategyBased_(stemp, itemp);
	    	assertTrue("Fehler beim Check auf Null Werte", btemp);
	    	
	    	//+++++++++++++++++++++++++++++++++++++++++++++++
	    	itemp = CounterByCharacterAscii_AlphanumericZZZ.iALPHANUMERIC_POSITION_MIN;
	    		
	    	//"MULTIPLE STRATEGY"-Ergebnisse
	    	stemp = CounterByCharacterAscii_AlphanumericZZZ.getStringAlphanumericMultipleForNumber(itemp);
	    	btemp = assertCheckNullBordersAlphanumericStrategyBased_(itemp, stemp);
	    	assertTrue("Fehler beim Check auf Null Werte", btemp);
	    	assertEquals("0",stemp);
	    	
	    	stemp = CounterByCharacterAscii_AlphanumericZZZ.getStringAlphanumericMultipleForNumber(itemp, true); //Kleinbuchstaben
	    	btemp = assertCheckNullBordersAlphanumericStrategyBased_(itemp, stemp);
	    	assertTrue("Fehler beim Check auf Null Werte", btemp);
	    	assertEquals("0",stemp); //Kleinbuchstaben für Zahlen gibt es ja nicht.
	    		    	
	    	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	    	itemp = CounterByCharacterAscii_AlphanumericZZZ.iALPHANUMERIC_POSITION_MAX;

	    	//"MULTIPLE STRATEGY"-Ergebnisse
	    	stemp = CounterByCharacterAscii_AlphanumericZZZ.getStringAlphanumericMultipleForNumber(itemp);
	    	btemp = assertCheckNullBordersAlphanumericStrategyBased_(itemp, stemp);
	    	assertTrue("Fehler beim Check auf Null Werte", btemp);
	    	assertEquals("Z",stemp);
	    	
	    	stemp = CounterByCharacterAscii_AlphanumericZZZ.getStringAlphanumericMultipleForNumber(itemp, true); //Kleinbuchstaben
	    	btemp = assertCheckNullBordersAlphanumericStrategyBased_(itemp, stemp);
	    	assertTrue("Fehler beim Check auf Null Werte", btemp);
	    	assertEquals("z",stemp);
	    	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	    	
	    	
	    	//######################
	    	//Nun Werte eingeben, die über 27 liegen. Diese müssen erlaubt sein.
	    	//+++++++++++++++++++++++++++++++++++++++++++++++++++
	    	itemp = CounterByCharacterAscii_AlphanumericZZZ.iALPHANUMERIC_POSITION_MAX+1;
	    	
	    	//"MULTIPLE STRATEGY"-Ergebnisse
	    	stemp = CounterByCharacterAscii_AlphanumericZZZ.getStringAlphanumericMultipleForNumber(itemp);
	    	btemp = assertCheckNullBordersAlphanumericStrategyBased_(itemp, stemp);
	    	assertTrue("Fehler beim Check auf Null Werte", btemp);
	    	assertEquals("00",stemp);
	    	
	    	stemp = CounterByCharacterAscii_AlphanumericZZZ.getStringAlphanumericMultipleForNumber(itemp, true); //Kleinbuchstaben
	    	btemp = assertCheckNullBordersAlphanumericStrategyBased_(itemp, stemp);
	    	assertTrue("Fehler beim Check auf Null Werte", btemp);
	    	assertEquals("00",stemp); //Keine Kleinbuchstaben bei Ziffern
	    	
	    	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	    	
	    	//... weitere Tests	
	    	itemp = CounterByCharacterAscii_AlphanumericZZZ.iALPHANUMERIC_POSITION_MAX+2;
	    	
	    	//"MULTIPLE STRATEGY"-Ergebnisse
	    	stemp = CounterByCharacterAscii_AlphanumericZZZ.getStringAlphanumericMultipleForNumber(itemp);
	    	btemp = assertCheckNullBordersAlphanumericStrategyBased_(itemp, stemp);
	    	assertTrue("Fehler beim Check auf Null Werte", btemp);
	    	assertEquals("11",stemp);
	    	
	    	stemp = CounterByCharacterAscii_AlphanumericZZZ.getStringAlphanumericMultipleForNumber(itemp, true); //Kleinbuchstaben
	    	btemp = assertCheckNullBordersAlphanumericStrategyBased_(itemp, stemp);
	    	assertTrue("Fehler beim Check auf Null Werte", btemp);
	    	assertEquals("11",stemp);
	    	
	    	//... weitere Tests	
	    	itemp = CounterByCharacterAscii_AlphanumericZZZ.iALPHANUMERIC_POSITION_MAX*2;
	    	
	    	//"MULTIPLE STRATEGY"-Ergebnisse
	    	stemp = CounterByCharacterAscii_AlphanumericZZZ.getStringAlphanumericMultipleForNumber(itemp);
	    	btemp = assertCheckNullBordersAlphanumericStrategyBased_(itemp, stemp);
	    	assertTrue("Fehler beim Check auf Null Werte", btemp);
	    	assertEquals("ZZ",stemp);
	    	
	    	stemp = CounterByCharacterAscii_AlphanumericZZZ.getStringAlphanumericMultipleForNumber(itemp, true); //Kleinbuchstaben
	    	btemp = assertCheckNullBordersAlphanumericStrategyBased_(itemp, stemp);
	    	assertTrue("Fehler beim Check auf Null Werte", btemp);
	    	assertEquals("zz",stemp);
	    }
	    
	    public void testGetNumberForStringAlphanumeric_StrategySerial(){
	    	
	    	//TODO GOON 20190317
	    	
	    	//"SERIAL STRATEGIE"-Ergebnisse
	    	int itemp = CounterByCharacterAscii_AlphanumericZZZ.iALPHANUMERIC_POSITION_MIN-1;
	    	String stemp = CounterByCharacterAscii_AlphanumericZZZ.getStringAlphanumericForNumber(itemp);
	    	boolean btemp = assertCheckNullBordersAlphanumericStrategyBased_(itemp, stemp);
	    	assertTrue("Fehler beim Check auf Null Werte", btemp);
	    	
	    	//+++++++++++++++++++++++++++++++++++++++++++++++
	    	itemp = CounterByCharacterAscii_AlphanumericZZZ.iALPHANUMERIC_POSITION_MIN;
	    	
	    	
	    	stemp = CounterByCharacterAscii_AlphanumericZZZ.getStringAlphanumericForNumber(itemp);
	    	btemp = assertCheckNullBordersAlphanumericStrategyBased_(itemp, stemp);
	    	assertTrue("Fehler beim Check auf Null Werte", btemp);
	    	assertEquals("0",stemp);
	    	
	    	stemp = CounterByCharacterAscii_AlphanumericZZZ.getStringAlphanumericForNumber(itemp, true); //Kleinbuchstaben
	    	btemp = assertCheckNullBordersAlphanumericStrategyBased_(itemp, stemp);
	    	assertTrue("Fehler beim Check auf Null Werte", btemp);
	    	assertEquals("0",stemp); //die Kleinbuchstaben für Zahlen gibt es ja nicht
	    		    	
	    	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	    	itemp = CounterByCharacterAscii_AlphanumericZZZ.iALPHANUMERIC_POSITION_MAX;
	    	
	    	//"SERIAL STRATEGIE"-Ergebnisse
	    	stemp = CounterByCharacterAscii_AlphanumericZZZ.getStringAlphanumericForNumber(itemp);
	    	btemp = assertCheckNullBordersAlphanumericStrategyBased_(itemp, stemp);
	    	assertTrue("Fehler beim Check auf Null Werte", btemp);
	    	assertEquals("Z",stemp);
	    	
	    	stemp = CounterByCharacterAscii_AlphanumericZZZ.getStringAlphanumericForNumber(itemp, true); //Kleinbuchstaben
	    	btemp = assertCheckNullBordersAlphanumericStrategyBased_(itemp, stemp);
	    	assertTrue("Fehler beim Check auf Null Werte", btemp);
	    	assertEquals("z",stemp);

	    	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	    	
	    	
	    	//######################
	    	//Nun Werte eingeben, die über 27 liegen. Diese müssen erlaubt sein.
	    	//+++++++++++++++++++++++++++++++++++++++++++++++++++
	    	
	    	//"SERIAL STRATEGIE"-Ergebnisse
	    	itemp = CounterByCharacterAscii_AlphanumericZZZ.iALPHANUMERIC_POSITION_MAX+1;
	    	stemp = CounterByCharacterAscii_AlphanumericZZZ.getStringAlphanumericForNumber(itemp);
	    	btemp = assertCheckNullBordersAlphanumericStrategyBased_(itemp, stemp);
	    	assertTrue("Fehler beim Check auf Null Werte", btemp);
	    	assertEquals("Z0",stemp);
	    	
	    	stemp = CounterByCharacterAscii_AlphanumericZZZ.getStringAlphanumericForNumber(itemp, true); //Kleinbuchstaben
	    	btemp = assertCheckNullBordersAlphanumericStrategyBased_(itemp, stemp);
	    	assertTrue("Fehler beim Check auf Null Werte", btemp);
	    	assertEquals("z0",stemp);
	    	
	    	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	    	
	    	//... weitere Tests
	     	//"SERIAL STRATEGIE"-Ergebnisse
	    	itemp = CounterByCharacterAscii_AlphanumericZZZ.iALPHANUMERIC_POSITION_MAX+2;
	    	stemp = CounterByCharacterAscii_AlphanumericZZZ.getStringAlphanumericForNumber(itemp);
	    	btemp = assertCheckNullBordersAlphanumericStrategyBased_(itemp, stemp);
	    	assertTrue("Fehler beim Check auf Null Werte", btemp);
	    	assertEquals("Z1",stemp);
	    	
	    	stemp = CounterByCharacterAscii_AlphanumericZZZ.getStringAlphanumericForNumber(itemp, true); //Kleinbuchstaben
	    	btemp = assertCheckNullBordersAlphanumericStrategyBased_(itemp, stemp);
	    	assertTrue("Fehler beim Check auf Null Werte", btemp);
	    	assertEquals("z1",stemp);
	    	
	    	//"SERIAL STRATEGIE"-Ergebnisse
	    	itemp = CounterByCharacterAscii_AlphanumericZZZ.iALPHANUMERIC_POSITION_MAX*2;
	    	stemp = CounterByCharacterAscii_AlphanumericZZZ.getStringAlphanumericForNumber(itemp);
	    	btemp = assertCheckNullBordersAlphanumericStrategyBased_(itemp, stemp);
	    	assertTrue("Fehler beim Check auf Null Werte", btemp);
	    	assertEquals("ZZ",stemp);
	    	
	    	stemp = CounterByCharacterAscii_AlphanumericZZZ.getStringAlphanumericForNumber(itemp, true); //Kleinbuchstaben
	    	btemp = assertCheckNullBordersAlphanumericStrategyBased_(itemp, stemp);
	    	assertTrue("Fehler beim Check auf Null Werte", btemp);
	    	assertEquals("zz",stemp);
	    }
	    

	

public void testGetStringAlphanumericForNumber_StrategyMultiple(){
	
	//"MULTIPLE STRATEGY"-Ergebnisse
	int itemp = CounterByCharacterAscii_AlphanumericZZZ.iALPHANUMERIC_POSITION_MIN-1;
	String stemp = CounterByCharacterAscii_AlphanumericZZZ.getStringAlphanumericMultipleForNumber(itemp, false); // .getCharMultipleForNumber(itemp);
	boolean btemp = assertCheckNullBordersAlphanumericStrategyBased_(itemp, stemp);
	assertTrue("Fehler beim Check auf Null Werte", btemp);
	
	//+++++++++++++++++++++++++++++++++++++++++++++++
	itemp = CounterByCharacterAscii_AlphanumericZZZ.iALPHANUMERIC_POSITION_MIN;
		
	//"MULTIPLE STRATEGY"-Ergebnisse
	stemp = CounterByCharacterAscii_AlphanumericZZZ.getStringAlphanumericMultipleForNumber(itemp);
	btemp = assertCheckNullBordersAlphanumericStrategyBased_(itemp, stemp);
	assertTrue("Fehler beim Check auf Null Werte", btemp);
	assertEquals("0",stemp);
	
	stemp = CounterByCharacterAscii_AlphanumericZZZ.getStringAlphanumericMultipleForNumber(itemp, true); //Kleinbuchstaben
	btemp = assertCheckNullBordersAlphanumericStrategyBased_(itemp, stemp);
	assertTrue("Fehler beim Check auf Null Werte", btemp);
	assertEquals("0",stemp); //Kleinbuchstaben für Zahlen gibt es ja nicht.
		    	
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	itemp = CounterByCharacterAscii_AlphanumericZZZ.iALPHANUMERIC_POSITION_MAX;

	//"MULTIPLE STRATEGY"-Ergebnisse
	stemp = CounterByCharacterAscii_AlphanumericZZZ.getStringAlphanumericMultipleForNumber(itemp);
	btemp = assertCheckNullBordersAlphanumericStrategyBased_(itemp, stemp);
	assertTrue("Fehler beim Check auf Null Werte", btemp);
	assertEquals("Z",stemp);
	
	stemp = CounterByCharacterAscii_AlphanumericZZZ.getStringAlphanumericMultipleForNumber(itemp, true); //Kleinbuchstaben
	btemp = assertCheckNullBordersAlphanumericStrategyBased_(itemp, stemp);
	assertTrue("Fehler beim Check auf Null Werte", btemp);
	assertEquals("z",stemp);
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	
	
	//######################
	//Nun Werte eingeben, die über 27 liegen. Diese müssen erlaubt sein.
	//+++++++++++++++++++++++++++++++++++++++++++++++++++
	itemp = CounterByCharacterAscii_AlphanumericZZZ.iALPHANUMERIC_POSITION_MAX+1;
	
	//"MULTIPLE STRATEGY"-Ergebnisse
	stemp = CounterByCharacterAscii_AlphanumericZZZ.getStringAlphanumericMultipleForNumber(itemp);
	btemp = assertCheckNullBordersAlphanumericStrategyBased_(itemp, stemp);
	assertTrue("Fehler beim Check auf Null Werte", btemp);
	assertEquals("00",stemp);
	
	stemp = CounterByCharacterAscii_AlphanumericZZZ.getStringAlphanumericMultipleForNumber(itemp, true); //Kleinbuchstaben
	btemp = assertCheckNullBordersAlphanumericStrategyBased_(itemp, stemp);
	assertTrue("Fehler beim Check auf Null Werte", btemp);
	assertEquals("00",stemp); //Keine Kleinbuchstaben bei Ziffern
	
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	
	//... weitere Tests	
	itemp = CounterByCharacterAscii_AlphanumericZZZ.iALPHANUMERIC_POSITION_MAX+2;
	
	//"MULTIPLE STRATEGY"-Ergebnisse
	stemp = CounterByCharacterAscii_AlphanumericZZZ.getStringAlphanumericMultipleForNumber(itemp);
	btemp = assertCheckNullBordersAlphanumericStrategyBased_(itemp, stemp);
	assertTrue("Fehler beim Check auf Null Werte", btemp);
	assertEquals("11",stemp);
	
	stemp = CounterByCharacterAscii_AlphanumericZZZ.getStringAlphanumericMultipleForNumber(itemp, true); //Kleinbuchstaben
	btemp = assertCheckNullBordersAlphanumericStrategyBased_(itemp, stemp);
	assertTrue("Fehler beim Check auf Null Werte", btemp);
	assertEquals("11",stemp);
	
	//... weitere Tests	
	itemp = CounterByCharacterAscii_AlphanumericZZZ.iALPHANUMERIC_POSITION_MAX*2;
	
	//"MULTIPLE STRATEGY"-Ergebnisse
	stemp = CounterByCharacterAscii_AlphanumericZZZ.getStringAlphanumericMultipleForNumber(itemp);
	btemp = assertCheckNullBordersAlphanumericStrategyBased_(itemp, stemp);
	assertTrue("Fehler beim Check auf Null Werte", btemp);
	assertEquals("ZZ",stemp);
	
	stemp = CounterByCharacterAscii_AlphanumericZZZ.getStringAlphanumericMultipleForNumber(itemp, true); //Kleinbuchstaben
	btemp = assertCheckNullBordersAlphanumericStrategyBased_(itemp, stemp);
	assertTrue("Fehler beim Check auf Null Werte", btemp);
	assertEquals("zz",stemp);
}

public void testGetStringAlphanumericForNumber_StrategySerial(){
	
	//"SERIAL STRATEGIE"-Ergebnisse
	int itemp = CounterByCharacterAscii_AlphanumericZZZ.iALPHANUMERIC_POSITION_MIN-1;
	String stemp = CounterByCharacterAscii_AlphanumericZZZ.getStringAlphanumericForNumber(itemp);
	boolean btemp = assertCheckNullBordersAlphanumericStrategyBased_(itemp, stemp);
	assertTrue("Fehler beim Check auf Null Werte", btemp);
	
	//+++++++++++++++++++++++++++++++++++++++++++++++
	itemp = CounterByCharacterAscii_AlphanumericZZZ.iALPHANUMERIC_POSITION_MIN;
	
	
	stemp = CounterByCharacterAscii_AlphanumericZZZ.getStringAlphanumericForNumber(itemp);
	btemp = assertCheckNullBordersAlphanumericStrategyBased_(itemp, stemp);
	assertTrue("Fehler beim Check auf Null Werte", btemp);
	assertEquals("0",stemp);
	
	stemp = CounterByCharacterAscii_AlphanumericZZZ.getStringAlphanumericForNumber(itemp, true); //Kleinbuchstaben
	btemp = assertCheckNullBordersAlphanumericStrategyBased_(itemp, stemp);
	assertTrue("Fehler beim Check auf Null Werte", btemp);
	assertEquals("0",stemp); //die Kleinbuchstaben für Zahlen gibt es ja nicht
		    	
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	itemp = CounterByCharacterAscii_AlphanumericZZZ.iALPHANUMERIC_POSITION_MAX;
	
	//"SERIAL STRATEGIE"-Ergebnisse
	stemp = CounterByCharacterAscii_AlphanumericZZZ.getStringAlphanumericForNumber(itemp);
	btemp = assertCheckNullBordersAlphanumericStrategyBased_(itemp, stemp);
	assertTrue("Fehler beim Check auf Null Werte", btemp);
	assertEquals("Z",stemp);
	
	stemp = CounterByCharacterAscii_AlphanumericZZZ.getStringAlphanumericForNumber(itemp, true); //Kleinbuchstaben
	btemp = assertCheckNullBordersAlphanumericStrategyBased_(itemp, stemp);
	assertTrue("Fehler beim Check auf Null Werte", btemp);
	assertEquals("z",stemp);

	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	
	
	//######################
	//Nun Werte eingeben, die über 27 liegen. Diese müssen erlaubt sein.
	//+++++++++++++++++++++++++++++++++++++++++++++++++++
	
	//"SERIAL STRATEGIE"-Ergebnisse
	itemp = CounterByCharacterAscii_AlphanumericZZZ.iALPHANUMERIC_POSITION_MAX+1;
	stemp = CounterByCharacterAscii_AlphanumericZZZ.getStringAlphanumericForNumber(itemp);
	btemp = assertCheckNullBordersAlphanumericStrategyBased_(itemp, stemp);
	assertTrue("Fehler beim Check auf Null Werte", btemp);
	assertEquals("Z0",stemp);
	
	stemp = CounterByCharacterAscii_AlphanumericZZZ.getStringAlphanumericForNumber(itemp, true); //Kleinbuchstaben
	btemp = assertCheckNullBordersAlphanumericStrategyBased_(itemp, stemp);
	assertTrue("Fehler beim Check auf Null Werte", btemp);
	assertEquals("z0",stemp);
	
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	
	//... weitere Tests
 	//"SERIAL STRATEGIE"-Ergebnisse
	itemp = CounterByCharacterAscii_AlphanumericZZZ.iALPHANUMERIC_POSITION_MAX+2;
	stemp = CounterByCharacterAscii_AlphanumericZZZ.getStringAlphanumericForNumber(itemp);
	btemp = assertCheckNullBordersAlphanumericStrategyBased_(itemp, stemp);
	assertTrue("Fehler beim Check auf Null Werte", btemp);
	assertEquals("Z1",stemp);
	
	stemp = CounterByCharacterAscii_AlphanumericZZZ.getStringAlphanumericForNumber(itemp, true); //Kleinbuchstaben
	btemp = assertCheckNullBordersAlphanumericStrategyBased_(itemp, stemp);
	assertTrue("Fehler beim Check auf Null Werte", btemp);
	assertEquals("z1",stemp);
	
	//"SERIAL STRATEGIE"-Ergebnisse
	itemp = CounterByCharacterAscii_AlphanumericZZZ.iALPHANUMERIC_POSITION_MAX*2;
	stemp = CounterByCharacterAscii_AlphanumericZZZ.getStringAlphanumericForNumber(itemp);
	btemp = assertCheckNullBordersAlphanumericStrategyBased_(itemp, stemp);
	assertTrue("Fehler beim Check auf Null Werte", btemp);
	assertEquals("ZZ",stemp);
	
	stemp = CounterByCharacterAscii_AlphanumericZZZ.getStringAlphanumericForNumber(itemp, true); //Kleinbuchstaben
	btemp = assertCheckNullBordersAlphanumericStrategyBased_(itemp, stemp);
	assertTrue("Fehler beim Check auf Null Werte", btemp);
	assertEquals("zz",stemp);
}
}//end class
	

