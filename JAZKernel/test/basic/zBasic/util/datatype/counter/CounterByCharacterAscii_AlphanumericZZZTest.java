package basic.zBasic.util.datatype.counter;

import java.util.EnumSet;
import java.util.Set;

import junit.framework.TestCase;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractEnum.EnumSetTestFactoryZZZ;
import basic.zBasic.util.abstractEnum.EnumSetMappedTestTypeZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetFactoryZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedZZZ;
import basic.zBasic.util.abstractList.HashMapExtendedZZZ;
import basic.zBasic.util.datatype.character.CharZZZ;
import basic.zBasic.util.datatype.enums.EnumSetMappedUtilZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zKernel.KernelZZZ;

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
	    		if(iInput< CounterByCharacterAscii_AlphanumericZZZ.iPOSITION_MIN){
		    		assertNull("Bei <'" + CounterByCharacterAscii_AlphanumericZZZ.iPOSITION_MIN + "' wird NULL erwartet. Ergebnis '" + sResult + "' für " + iInput, sResult);
		    	}else if(iInput>CounterByCharacterAscii_AlphanumericZZZ.iPOSITION_MAX){
		    		assertNull("Bei >'" + CounterByCharacterAscii_AlphanumericZZZ.iPOSITION_MAX + "' wird NULL erwartet. Ergebnis '" + sResult + "' für " + iInput, sResult);
		    	}   		
	    		bReturn=true;
	    			    		    	
	    	}//end main:
	    	return bReturn;	    	
	    }	    	    
	    
	    private boolean assertCheckNullBordersAlphanumericStrategyBased_(int iInput, String sResult){
	    	boolean bReturn = false;
	    	main:{
	    		if(iInput< CounterByCharacterAscii_AlphanumericZZZ.iPOSITION_MIN){
		    		assertNull("Bei <'" + CounterByCharacterAscii_AlphanumericZZZ.iPOSITION_MIN + "' wird NULL erwartet. Ergebnis '" + sResult + "' für " + iInput, sResult);
		    	}
		    		
	    		if(iInput>=CounterByCharacterAscii_AlphanumericZZZ.iPOSITION_MIN){
		    		assertNotNull("Bei >= '" + CounterByCharacterAscii_AlphanumericZZZ.iPOSITION_MIN + "' wird keine NULL erwartet. Ergebnis '" + sResult + "' für " + iInput, sResult);
		    	}	    		
	    		bReturn=true;
	    			    		    	
	    	}//end main:
	    	return bReturn;	    	
	    }
	    
	    private boolean assertCheckNullBordersAlphanumericStrategyBased_(String sInput, int iResult){
	    	boolean bReturn = false;
	    	main:{
	    		//Ermittle den "Teiler" und den Rest, Also Modulo - Operation
				int iDiv = Math.abs(iResult / CounterByCharacterAscii_AlphanumericZZZ.iPOSITION_MAX ); //durch abs wird also intern in ein Integer umgewandetl.... nicht nur das Weglassen des ggfs. negativen Vorzeichens.
				int iMod = iResult % CounterByCharacterAscii_AlphanumericZZZ.iPOSITION_MAX;
	    		
				if(iDiv==0 && iMod< CounterByCharacterAscii_AlphanumericZZZ.iPOSITION_MIN){
		    		assertEquals("Bei <'" + CounterByCharacterAscii_AlphanumericZZZ.iPOSITION_MIN + "' wird NULL erwartet. Ergebnis '" + iResult + "' für " + sInput, sInput);
		    	}
		    		
	    		if(iMod>=CounterByCharacterAscii_AlphanumericZZZ.iPOSITION_MIN){
		    		assertNotNull("Bei >= '" + CounterByCharacterAscii_AlphanumericZZZ.iPOSITION_MIN + "' wird keine NULL erwartet. Ergebnis '" + iResult + "' für " + sInput, sInput);
		    	}	    		
	    		bReturn=true;
	    			    		    	
	    	}//end main:
	    	return bReturn;	    	
	    }
	    
	    private boolean assertCheckNullBordersAlphanumericStrategyBasedSignificant_(String sInput, int iResult){
	    	boolean bReturn = false;
	    	main:{
	    		//Ermittle den "Teiler" und den Rest, Also Modulo - Operation
				int iDiv = Math.abs(iResult / CounterByCharacterAscii_AlphanumericZZZ.iPOSITION_MAX ); //durch abs wird also intern in ein Integer umgewandetl.... nicht nur das Weglassen des ggfs. negativen Vorzeichens.
				int iMod = iResult % CounterByCharacterAscii_AlphanumericZZZ.iPOSITION_MAX;
	    		
				//Wenn Die 1 = "0" in den Wert 0 geändert wurde (wg. der Berchung des Stellenwerts), dann geht das nicht mehr.
//				if(iDiv==0 && iMod < CounterByCharacterAscii_AlphanumericZZZ.iPOSITION_MIN){
//		    		assertEquals("Bei <'" + CounterByCharacterAscii_AlphanumericZZZ.iPOSITION_MIN + "' wird NULL erwartet. Ergebnis '" + iResult + "' für " + sInput, sInput);
//		    	}
		    		
	    		if(iMod>=CounterByCharacterAscii_AlphanumericZZZ.iPOSITION_MIN){
		    		assertNotNull("Bei >= '" + CounterByCharacterAscii_AlphanumericZZZ.iPOSITION_MIN + "' wird keine NULL erwartet. Ergebnis '" + iResult + "' für " + sInput, sInput);
		    	}	    		
	    		bReturn=true;
	    			    		    	
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
	    	int itemp = CounterByCharacterAscii_AlphanumericZZZ.getPositionForChar(ctemp);
	    	boolean btemp = assertCheckNullBordersAlphanumeric_(ctemp,itemp);
	    	assertTrue("Fehler beim Check auf gültige Werte", btemp);
	    	
	    	ctemp = ':';
	    	itemp = CounterByCharacterAscii_AlphanumericZZZ.getPositionForChar(ctemp);
	    	btemp = assertCheckNullBordersAlphanumeric_(ctemp,itemp);
	    	assertTrue("Fehler beim Check auf gültige Werte", btemp);
	    	
	    	ctemp = '@';
	    	itemp = CounterByCharacterAscii_AlphanumericZZZ.getPositionForChar(ctemp);
	    	btemp = assertCheckNullBordersAlphanumeric_(ctemp,itemp);
	    	assertTrue("Fehler beim Check auf gültige Werte", btemp);
	    	
	    	ctemp = '[';
	    	itemp = CounterByCharacterAscii_AlphanumericZZZ.getPositionForChar(ctemp);
	    	btemp = assertCheckNullBordersAlphanumeric_(ctemp,itemp);
	    	assertTrue("Fehler beim Check auf gültige Werte", btemp);
	    	
	    	ctemp = '´';
	    	itemp = CounterByCharacterAscii_AlphanumericZZZ.getPositionForChar(ctemp);
	    	btemp = assertCheckNullBordersAlphanumeric_(ctemp,itemp);
	    	assertTrue("Fehler beim Check auf gültige Werte", btemp);
	    	    	
	    	ctemp = '{';
	    	itemp = CounterByCharacterAscii_AlphanumericZZZ.getPositionForChar(ctemp);
	    	btemp = assertCheckNullBordersAlphanumeric_(ctemp,itemp);
	    	assertTrue("Fehler beim Check auf gültige Werte", btemp); 
	    	
	    	//+++++++++++++++++++++++++++++++++++++++++
	    	ctemp = '0';
	    	itemp = CounterByCharacterAscii_AlphanumericZZZ.getPositionForChar(ctemp);
	    	btemp = assertCheckNullBordersAlphanumeric_(ctemp,itemp);
	    	assertTrue("Fehler beim Check auf gültige Werte", btemp);
	    	assertEquals(1,itemp);
	    	
	    	ctemp = '9';
	    	itemp = CounterByCharacterAscii_AlphanumericZZZ.getPositionForChar(ctemp);
	    	btemp = assertCheckNullBordersAlphanumeric_(ctemp,itemp);
	    	assertTrue("Fehler beim Check auf gültige Werte", btemp);
	    	assertEquals(10,itemp);
	    	
	    	//+++++++++++++++++++++++++++++++++++++++++
	    	ctemp = 'A';
	    	itemp = CounterByCharacterAscii_AlphanumericZZZ.getPositionForChar(ctemp);
	    	btemp = assertCheckNullBordersAlphanumeric_(ctemp,itemp);
	    	assertTrue("Fehler beim Check auf gültige Werte", btemp);
	    	assertEquals(11,itemp);
	    	
	    	ctemp = 'a';
	    	itemp = CounterByCharacterAscii_AlphanumericZZZ.getPositionForChar(ctemp);
	    	btemp = assertCheckNullBordersAlphanumeric_(ctemp,itemp);
	    	assertTrue("Fehler beim Check auf gültige Werte", btemp);
	    	assertEquals(11,itemp);
	    	
	    	//++++++++++++++++++++++++++++++++++++++++
	    	ctemp = 'Z';
	    	itemp = CounterByCharacterAscii_AlphanumericZZZ.getPositionForChar(ctemp);
	    	btemp = assertCheckNullBordersAlphanumeric_(ctemp,itemp);
	    	assertTrue("Fehler beim Check auf gültige Werte", btemp);
	    	assertEquals(36,itemp);
	    	
	    	ctemp = 'z';
	    	itemp = CounterByCharacterAscii_AlphanumericZZZ.getPositionForChar(ctemp);
	    	btemp = assertCheckNullBordersAlphanumeric_(ctemp,itemp);
	    	assertTrue("Fehler beim Check auf gültige Werte", btemp);
	    	assertEquals(36,itemp);
	    	
	    }
	    
	    public void testGetCharForPositionInAlphanumeric(){	    		    	
	    	int itemp = CounterByCharacterAscii_AlphanumericZZZ.iPOSITION_MIN-1;
	    	String stemp = CounterByCharacterAscii_AlphanumericZZZ.getCharForPosition(itemp);
	    	boolean btemp = assertCheckNullBordersAlphanumeric_(itemp, stemp);
	    	assertTrue("Fehler beim Check auf Null Werte", btemp);
	    	
	    	//+++++++++++++++++++++++++++++++++++++++++++++++
	    	itemp = CounterByCharacterAscii_AlphanumericZZZ.iPOSITION_MIN;
	    	stemp = CounterByCharacterAscii_AlphanumericZZZ.getCharForPosition(itemp);
	    	btemp = assertCheckNullBordersAlphanumeric_(itemp, stemp);
	    	assertTrue("Fehler beim Check auf Null Werte", btemp);
    		assertEquals("0",stemp);
	    	
    			    	
    		//+++++++++++++++++++++++++++++++++++++++++++++++++++
    		itemp = CounterByCharacterAscii_AlphanumericZZZ.iPOSITION_MAX+1;
	    	stemp = CounterByCharacterAscii_AlphanumericZZZ.getCharForPosition(itemp);
	    	btemp = assertCheckNullBordersAlphanumeric_(itemp, stemp);
	    	assertTrue("Fehler beim Check auf Null Werte", btemp);
    			    	
    		//++++++++++++++++++++++++++++++++
    		itemp = CounterByCharacterAscii_AlphanumericZZZ.iPOSITION_MAX;
	    	stemp = CounterByCharacterAscii_AlphanumericZZZ.getCharForPosition(itemp);
	        btemp = assertCheckNullBordersAlphanumeric_(itemp, stemp);
	    	assertTrue("Fehler beim Check auf Null Werte", btemp);
    		assertEquals("Z",stemp);
    		
	    	stemp = CounterByCharacterAscii_AlphanumericZZZ.getCharForPosition(itemp, true); //Kleinbuchstaben
	        btemp = assertCheckNullBordersAlphanumeric_(itemp, stemp);
	    	assertTrue("Fehler beim Check auf Null Werte", btemp);
    		assertEquals("z",stemp);
    		
    		
    		//++++ WEITER WERTE AN "DEN INNEREN RÄNDERN" (also die Stelle an der intern die Sonderzeichen ausgeschnitten werden.
    		itemp = CounterByCharacterAscii_NumericZZZ.iPOSITION_MAX; //"9"....
    		stemp = CounterByCharacterAscii_AlphanumericZZZ.getCharForPosition(itemp);
	    	btemp = assertCheckNullBordersAlphanumeric_(itemp, stemp);
	    	assertTrue("Fehler beim Check auf Null Werte", btemp);
    		assertEquals("9",stemp);
    		
    		stemp = CounterByCharacterAscii_AlphanumericZZZ.getCharForPosition(itemp,true); //kleinbuschstaben, obwohl es dafür ("9") keine geben soll.
	    	btemp = assertCheckNullBordersAlphanumeric_(itemp, stemp);
	    	assertTrue("Fehler beim Check auf Null Werte", btemp);
    		assertEquals("9",stemp);
    		
    		//+++++++++
    		itemp = CounterByCharacterAscii_NumericZZZ.iPOSITION_MAX+1; //"A"....
    		stemp = CounterByCharacterAscii_AlphanumericZZZ.getCharForPosition(itemp);
	    	btemp = assertCheckNullBordersAlphanumeric_(itemp, stemp);
	    	assertTrue("Fehler beim Check auf Null Werte", btemp);
    		assertEquals("A",stemp);
    		
    		stemp = CounterByCharacterAscii_AlphanumericZZZ.getCharForPosition(itemp, true); //Kleinbuchstaben
    		btemp = assertCheckNullBordersAlphanumeric_(itemp, stemp);
	    	assertTrue("Fehler beim Check auf Null Werte", btemp);
    		assertEquals("a",stemp);
    		
	    }
	    
	   
	    public void testGetNumberForStringAlphanumeric_StrategyMultiple(){
	    	//"MULTIPLE STRATEGY"-Ergebnisse
	    	String stemp; int itemp; boolean btemp;
	    	ICounterStrategyAlphanumericZZZ objCounterStrategy=null;
	    	try{	    	
	    		objCounterStrategy = new CounterStrategyAlphanumericMultipleZZZ();
	    	} catch (ExceptionZZZ ez) {
	    		fail("Method throws an exception." + ez.getMessageLast());
	    	} 
	    	
	    	try {
				stemp = "0";				
				itemp = CounterByCharacterAscii_AlphanumericZZZ.getNumberForString(stemp,objCounterStrategy);
				btemp = assertCheckNullBordersAlphanumericStrategyBased_(stemp, itemp);
		    	assertTrue("Fehler beim Check auf Null Werte", btemp);
		    	
		    	//Mache die Gegenprobe
		    	String sCheck = CounterByCharacterAscii_AlphanumericZZZ.getStringMultipleForNumber(itemp);
		    	assertEquals("Gegenprobe wurde erfolgreich erwartet.", stemp, sCheck);
		    			    	
		    	
			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			} 
	    	
	    	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	    	
			try {
				stemp = "00"; //Parameter true="Multiple Strategy"
				itemp = CounterByCharacterAscii_AlphanumericZZZ.getNumberForString(stemp,objCounterStrategy);
				btemp = assertCheckNullBordersAlphanumericStrategyBased_(stemp, itemp);
		    	assertTrue("Fehler beim Check auf Null Werte", btemp);
		    	
		    	//Mache die Gegenprobe
		    	String sCheck = CounterByCharacterAscii_AlphanumericZZZ.getStringMultipleForNumber(itemp);
		    	assertEquals("Gegenprobe wurde erfolgreich erwartet.", stemp, sCheck);		    			    			    	
			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			} 
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	    	
			try {
				stemp = "9"; //Parameter true="Multiple Strategy"
				itemp = CounterByCharacterAscii_AlphanumericZZZ.getNumberForString(stemp,objCounterStrategy);
				btemp = assertCheckNullBordersAlphanumericStrategyBased_(stemp, itemp);
		    	assertTrue("Fehler beim Check auf Null Werte", btemp);
		    	
		    	//Mache die Gegenprobe
		    	String sCheck = CounterByCharacterAscii_AlphanumericZZZ.getStringMultipleForNumber(itemp);
		    	assertEquals("Gegenprobe wurde erfolgreich erwartet.", stemp, sCheck);		    			    			    	
			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			} 
			
//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	    	
			try {
				stemp = "99"; //Parameter true="Multiple Strategy"
				itemp = CounterByCharacterAscii_AlphanumericZZZ.getNumberForString(stemp,objCounterStrategy);
				btemp = assertCheckNullBordersAlphanumericStrategyBased_(stemp, itemp);
		    	assertTrue("Fehler beim Check auf Null Werte", btemp);
		    	
		    	//Mache die Gegenprobe
		    	String sCheck = CounterByCharacterAscii_AlphanumericZZZ.getStringMultipleForNumber(itemp);
		    	assertEquals("Gegenprobe wurde erfolgreich erwartet.", stemp, sCheck);		    			    			    	
			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			} 
			
//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	    	
			try {
				stemp = "A"; //Parameter true="Multiple Strategy"
				itemp = CounterByCharacterAscii_AlphanumericZZZ.getNumberForString(stemp,objCounterStrategy);
				btemp = assertCheckNullBordersAlphanumericStrategyBased_(stemp, itemp);
		    	assertTrue("Fehler beim Check auf Null Werte", btemp);
		    	
		    	//Mache die Gegenprobe
		    	String sCheck = CounterByCharacterAscii_AlphanumericZZZ.getStringMultipleForNumber(itemp);
		    	assertEquals("Gegenprobe wurde erfolgreich erwartet.", stemp, sCheck);		    			    			    
			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			} 
			
//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	    	
			try {
				stemp = "AA"; //Parameter true="Multiple Strategy"
				itemp = CounterByCharacterAscii_AlphanumericZZZ.getNumberForString(stemp,objCounterStrategy);
				btemp = assertCheckNullBordersAlphanumericStrategyBased_(stemp, itemp);
		    	assertTrue("Fehler beim Check auf Null Werte", btemp);
		    	
		    	//Mache die Gegenprobe
		    	String sCheck = CounterByCharacterAscii_AlphanumericZZZ.getStringMultipleForNumber(itemp);
		    	assertEquals("Gegenprobe wurde erfolgreich erwartet.", stemp, sCheck);		    			    			    
			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			} 
			
//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	    	
			try {
				stemp = "Z"; //Parameter true="Multiple Strategy"
				itemp = CounterByCharacterAscii_AlphanumericZZZ.getNumberForString(stemp,objCounterStrategy);
				btemp = assertCheckNullBordersAlphanumericStrategyBased_(stemp, itemp);
		    	assertTrue("Fehler beim Check auf Null Werte", btemp);
		    	
		    	//Mache die Gegenprobe
		    	String sCheck = CounterByCharacterAscii_AlphanumericZZZ.getStringMultipleForNumber(itemp);
		    	assertEquals("Gegenprobe wurde erfolgreich erwartet.", stemp, sCheck);		    	
			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			} 
			
//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	    	
			try {
				stemp = "ZZ"; //Parameter true="Multiple Strategy"
				itemp = CounterByCharacterAscii_AlphanumericZZZ.getNumberForString(stemp,objCounterStrategy);
				btemp = assertCheckNullBordersAlphanumericStrategyBased_(stemp, itemp);
		    	assertTrue("Fehler beim Check auf Null Werte", btemp);
		    	
		    	//Mache die Gegenprobe
		    	String sCheck = CounterByCharacterAscii_AlphanumericZZZ.getStringMultipleForNumber(itemp);
		    	assertEquals("Gegenprobe wurde erfolgreich erwartet.", stemp, sCheck);		    			    		    	
			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			} 		
	    }
	    	   
	    public void testGetNumberForStringAlphanumeric_StrategySerial(){
	    	
	    	String stemp; int itemp; boolean btemp;
	    	ICounterStrategyAlphanumericZZZ objCounterStrategy = null;
	    	try{
	    	 objCounterStrategy = new CounterStrategyAlphanumericSerialZZZ();
	    	} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			} 	
	    	
	    	//Ungültige Werte
	    	try {
				stemp = "?"; //Parameter false="Serielle Strategy"
				itemp = CounterByCharacterAscii_AlphanumericZZZ.getNumberForString(stemp,objCounterStrategy);
				fail("Method should have thrown an exception for the string '"+stemp+"'");
				
			} catch (ExceptionZZZ ez) {
				//Erwartetete Exception
			} 
	    	
	    	
	    	//Ungültige Werte, für die verschiedenen Strategien
	    	//...serielle Strategie
	    	try {
		    	stemp = "0aA"; //Groß-/Kleinschreibung nicht mischen
		    	itemp = CounterByCharacterAscii_AlphanumericZZZ.getNumberForString(stemp,objCounterStrategy);//Parameter true="Serielle Strategy"
		    	fail("Method should have thrown an exception for the string '"+stemp+"'");
						    	
			} catch (ExceptionZZZ ez) {
				//Erwartetete Exception
			} 
	    	
	    	//...multiple Strategie
	    	try {
		    	stemp = "01"; //Alle Zeichen müssen gleich sein
		    	itemp = CounterByCharacterAscii_AlphanumericZZZ.getNumberForString(stemp,objCounterStrategy);//Parameter true="Multiple Strategy"
		    	fail("Method should have thrown an exception for the string '"+stemp+"'");
				
			} catch (ExceptionZZZ ez) {
				//Erwartetete Exception
			} 
	    	
	    	
	    	//Ungültige Werte, da die Zeichen nicht in Relation zueinander passen.
	    	//... serielle Strategie
	    	try {
		    	stemp = "0A";//Merke: Die Zeichen links müssen immer das höchste Zeichen des Zeichenraums sein. 
		    	itemp = CounterByCharacterAscii_AlphanumericZZZ.getNumberForString(stemp,objCounterStrategy);//Parameter false="Serielle Strategy"
		    	fail("Method should have thrown an exception for the string '"+stemp+"'");
				
			} catch (ExceptionZZZ ez) {
				//Erwartetete Exception
			} 
	    	
	    	//"SERIAL STRATEGIE"-Ergebnisse	    		    	
	    	try {
				stemp = "0"; //Parameter false="Serielle Strategy"
				itemp = CounterByCharacterAscii_AlphanumericZZZ.getNumberForString(stemp,objCounterStrategy);
				btemp = assertCheckNullBordersAlphanumericStrategyBased_(stemp, itemp);
		    	assertTrue("Fehler beim Check auf Null Werte", btemp);
		    	
		    	//Mache die Gegenprobe
		    	String sCheck = CounterByCharacterAscii_AlphanumericZZZ.getStringForNumber(itemp, objCounterStrategy);
		    	assertEquals("Gegenprobe wurde erfolgreich erwartet.", stemp, sCheck);
		    			    	
		    	
			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			} 
	    	
	    	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	    	
			try {				
				stemp = "Z0"; //Parameter true="Multiple Strategy"
				itemp = CounterByCharacterAscii_AlphanumericZZZ.getNumberForString(stemp,objCounterStrategy);
				btemp = assertCheckNullBordersAlphanumericStrategyBased_(stemp, itemp);
		    	assertTrue("Fehler beim Check auf Null Werte", btemp);
		    	
		    	//Mache die Gegenprobe
		    	String sCheck = CounterByCharacterAscii_AlphanumericZZZ.getStringForNumber(itemp, objCounterStrategy);
		    	assertEquals("Gegenprobe wurde erfolgreich erwartet.", stemp, sCheck);
		    			    	
		    	
			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			} 
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	    	
			try {
				stemp = "9"; //Parameter true="Multiple Strategy"
				itemp = CounterByCharacterAscii_AlphanumericZZZ.getNumberForString(stemp,objCounterStrategy);
				btemp = assertCheckNullBordersAlphanumericStrategyBased_(stemp, itemp);
		    	assertTrue("Fehler beim Check auf Null Werte", btemp);
		    	
		    	//Mache die Gegenprobe
		    	String sCheck = CounterByCharacterAscii_AlphanumericZZZ.getStringForNumber(itemp, objCounterStrategy);
		    	assertEquals("Gegenprobe wurde erfolgreich erwartet.", stemp, sCheck);
		    			    	
		    	
			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			} 
			
//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	    	
			try {
				stemp = "Z9"; //Parameter true="Multiple Strategy"
				itemp = CounterByCharacterAscii_AlphanumericZZZ.getNumberForString(stemp,objCounterStrategy);
				btemp = assertCheckNullBordersAlphanumericStrategyBased_(stemp, itemp);
		    	assertTrue("Fehler beim Check auf Null Werte", btemp);
		    	
		    	//Mache die Gegenprobe
		    	String sCheck = CounterByCharacterAscii_AlphanumericZZZ.getStringForNumber(itemp, objCounterStrategy);
		    	assertEquals("Gegenprobe wurde erfolgreich erwartet.", stemp, sCheck);
		    			    	
		    	
			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			} 
			
//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	    	
			try {
				stemp = "A"; //Parameter true="Multiple Strategy"
				itemp = CounterByCharacterAscii_AlphanumericZZZ.getNumberForString(stemp,objCounterStrategy);
				btemp = assertCheckNullBordersAlphanumericStrategyBased_(stemp, itemp);
		    	assertTrue("Fehler beim Check auf Null Werte", btemp);
		    	
		    	//Mache die Gegenprobe
		    	String sCheck = CounterByCharacterAscii_AlphanumericZZZ.getStringForNumber(itemp, objCounterStrategy);
		    	assertEquals("Gegenprobe wurde erfolgreich erwartet.", stemp, sCheck);
		    			    	
		    	
			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			} 
			
//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	    	
			try {
				stemp = "ZA"; //Parameter true="Multiple Strategy"
				itemp = CounterByCharacterAscii_AlphanumericZZZ.getNumberForString(stemp,objCounterStrategy);
				btemp = assertCheckNullBordersAlphanumericStrategyBased_(stemp, itemp);
		    	assertTrue("Fehler beim Check auf Null Werte", btemp);
		    	
		    	//Mache die Gegenprobe
		    	String sCheck = CounterByCharacterAscii_AlphanumericZZZ.getStringForNumber(itemp, objCounterStrategy);
		    	assertEquals("Gegenprobe wurde erfolgreich erwartet.", stemp, sCheck);
		    			    	
		    	
			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			} 
			
//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	    	
			try {
				stemp = "Z"; //Parameter true="Multiple Strategy"
				itemp = CounterByCharacterAscii_AlphanumericZZZ.getNumberForString(stemp,objCounterStrategy);
				btemp = assertCheckNullBordersAlphanumericStrategyBased_(stemp, itemp);
		    	assertTrue("Fehler beim Check auf Null Werte", btemp);
		    	
		    	//Mache die Gegenprobe
		    	String sCheck = CounterByCharacterAscii_AlphanumericZZZ.getStringForNumber(itemp,  objCounterStrategy);
		    	assertEquals("Gegenprobe wurde erfolgreich erwartet.", stemp, sCheck);
		    			    	
		    	
			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			} 
			
//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	    	
			try {
				stemp = "ZZ"; //Parameter true="Multiple Strategy"
				itemp = CounterByCharacterAscii_AlphanumericZZZ.getNumberForString(stemp,objCounterStrategy);
				btemp = assertCheckNullBordersAlphanumericStrategyBased_(stemp, itemp);
		    	assertTrue("Fehler beim Check auf Null Werte", btemp);
		    	
		    	//Mache die Gegenprobe
		    	String sCheck = CounterByCharacterAscii_AlphanumericZZZ.getStringForNumber(itemp,  objCounterStrategy);
		    	assertEquals("Gegenprobe wurde erfolgreich erwartet.", stemp, sCheck);
		    	
			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			} 
	    	
	    }
	    
 public void testGetNumberForStringAlphanumeric_StrategySignificant(){
	    	
	    	String stemp; int itemp; int itempLeft; int itempRight; int itempCheck; boolean btemp;
	    	ICounterStrategyAlphanumericZZZ objCounterStrategy = null;
	    	try{
	    		objCounterStrategy = new CounterStrategyAlphanumericSignificantZZZ();
	    	} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			} 
	    	
	    	//Ungültige Werte
	    	try {
				stemp = "?"; //Parameter false="Significant Strategy"
				itemp = CounterByCharacterAscii_AlphanumericZZZ.getNumberForString(stemp,objCounterStrategy);
				fail("Method should have thrown an exception for the string '"+stemp+"'");
				
			} catch (ExceptionZZZ ez) {
				//Erwartetete Exception
			} 
	    	
	    	
	    	//Ungültige Werte, für die verschiedenen Strategien
	    	//...serielle Strategie
	    	try {
		    	stemp = "0aA"; //Groß-/Kleinschreibung nicht mischen
		    	itemp = CounterByCharacterAscii_AlphanumericZZZ.getNumberForString(stemp,objCounterStrategy);//Parameter true="Serielle Strategy"
		    	fail("Method should have thrown an exception for the string '"+stemp+"'");
						    	
			} catch (ExceptionZZZ ez) {
				//Erwartetete Exception
			} 
	    	
	    	//Merke: Bei der Signifikant-Strategie ist die Positon der Zeichen zueinander hinsichtlich der Gültigkeit egal....
	    	//Gehe daher sofort zu den "Berechnungstests":
	    	
	    	try {
				stemp = "00"; 
				itemp = CounterByCharacterAscii_AlphanumericZZZ.getNumberForString(stemp,objCounterStrategy);
				fail("Method should have thrown an exception for the string '"+stemp+"'");		    	
			} catch (ExceptionZZZ ez) {
				//Erwartetete Exception
			} 
	    	
	        //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	    	stemp = "0Z";
			try {				    				    				
				btemp = getNumberForStringAlphanumeric_StrategySignificantTest_(objCounterStrategy,stemp);
				assertTrue("Fehler beim allgemeinen Test.", btemp);
			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			} 
			
			try{
				objCounterStrategy.isRightAligned(true);
				itemp = CounterByCharacterAscii_AlphanumericZZZ.getNumberForString(stemp,objCounterStrategy);
				assertEquals(1260,itemp);
				
				//Gegenprobe
				String stempRight = CounterByCharacterAscii_AlphanumericZZZ.getStringForNumber(itemp, objCounterStrategy);
				assertEquals(stemp,stempRight);
				
			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			} 	
			
			try{
				objCounterStrategy.isRightAligned(true);
				itemp = CounterByCharacterAscii_AlphanumericZZZ.getNumberForString(stemp,objCounterStrategy);
				
			} catch (ExceptionZZZ ez) {
				//Erwartetete Exception
			} 	
			
	    	
	    	//"SIGNIFICANT STRATEGIE"-Ergebnisse	    		    		    		    	
	    	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	    	try {
				stemp = "Z";
				itemp = CounterByCharacterAscii_AlphanumericZZZ.getNumberForString(stemp,objCounterStrategy);
				btemp = assertCheckNullBordersAlphanumericStrategyBasedSignificant_(stemp, itemp);
		    	assertTrue("Fehler beim Check auf Null Werte", btemp);
		    	assertEquals("Erwarteter Wert ", 35,itemp);
		    	
		    	//Mache die Gegenprobe
		    	String sCheck = CounterByCharacterAscii_AlphanumericZZZ.getStringForNumber(itemp, objCounterStrategy);
		    	assertEquals("Gegenprobe wurde erfolgreich erwartet.", stemp, sCheck);
	    	} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			} 
//		    			    	
	    	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	    	
		    	try {				
					stemp = "0";
			    	objCounterStrategy.isRightAligned(false);
					itempLeft = CounterByCharacterAscii_AlphanumericZZZ.getNumberForString(stemp,objCounterStrategy); //linksbündig 73
					btemp = assertCheckNullBordersAlphanumericStrategyBasedSignificant_(stemp, itempLeft);
			    	assertTrue("Fehler beim Check auf Null Werte", btemp);
			    	assertEquals("Erwarteter Wert ", 0,itempLeft); //Merke: Hier wird der Stellenwert berechnet. Und der ist um -1 kleiner als derASCII-Zeichnewert von "0"=1
			    	
			    	objCounterStrategy.isRightAligned(true);
			    	itempRight = CounterByCharacterAscii_AlphanumericZZZ.getNumberForString(stemp,objCounterStrategy); //rechtsbündig 73
					btemp = assertCheckNullBordersAlphanumericStrategyBasedSignificant_(stemp, itempRight);
			    	assertTrue("Fehler beim Check auf Null Werte", btemp);	
			    	assertEquals("Erwarteter Wert ", 0,itempRight); //Merke: Hier wird der Stellenwert berechnet. Und der ist um -1 kleiner als derASCII-Zeichnewert von "0"=1
			    	assertEquals("Links- oder Rechtsbündig soll bei '00' (also gleichen Zeichen) den gleichen Zahlenwert haben", itempLeft, itempRight);
			    		
			    	//Mache die Gegenprobe
			    	//"0" wird allerdings weggetrimmt
			    	objCounterStrategy.isRightAligned(false);
			    	boolean bLowercase = false;
			    	String sCharToStrip = CounterByCharacterAscii_AlphanumericZZZ.getCharForPosition(CounterByCharacterAscii_AlphanumericZZZ.iPOSITION_MIN, bLowercase);
			    	String stempStripped = StringZZZ.stripLeft(stemp, sCharToStrip);		    	
			    	String sCheckLeft = CounterByCharacterAscii_AlphanumericZZZ.getStringForNumber(itempLeft, objCounterStrategy);
			    	assertEquals("Gegenprobe ('0' vorne entfernt) wurde erfolgreich erwartet.", stempStripped, sCheckLeft);		    			    	
			    	
				} catch (ExceptionZZZ ez) {
					fail("Method throws an exception." + ez.getMessageLast());
				} 
		    	
		    	//###################################
		    	//### Tests mit der "private" Funktion, die alles kann....
		    	//###################################
		    	try {				
					stemp = "AA";
					btemp = getNumberForStringAlphanumeric_StrategySignificantTest_(objCounterStrategy,stemp);
					if(!btemp){
						ExceptionZZZ ez = new ExceptionZZZ("AlphanumericCounter: Fehler beim String '" + stemp + "'", KernelZZZ.iERROR_RUNTIME, CounterByCharacterAscii_AlphanumericZZZTest.class, ReflectCodeZZZ.getMethodCurrentName());
						throw ez;						
					}
				} catch (ExceptionZZZ ez) {
					fail("Method throws an exception." + ez.getMessageLast());
				} 
		    	
		    	
		    	try {				
					stemp = "AAA";
//			    	objCounterStrategy.isRightAligned(false);
//					itempLeft = CounterByCharacterAscii_AlphanumericZZZ.getNumberForString(stemp,objCounterStrategy); //linksbündig 73
//					btemp = assertCheckNullBordersAlphanumericStrategyBasedSignificant_(stemp, itempLeft);
//			    	assertTrue("Fehler beim Check auf Null Werte", btemp);
//			    	
//			    	objCounterStrategy.isRightAligned(true);
//			    	itempRight = CounterByCharacterAscii_AlphanumericZZZ.getNumberForString(stemp,objCounterStrategy); //rechtsbündig 73
//					btemp = assertCheckNullBordersAlphanumericStrategyBasedSignificant_(stemp, itempRight);
//			    	assertTrue("Fehler beim Check auf Null Werte", btemp);		    	
//			    	assertEquals("Links- oder Rechtsbündig soll bei 'AAA' (also gleichen Zeichen) den gleichen Zahlenwert haben", itempLeft, itempRight);
//			    			
//			    	//Mache die Gegenprobe
//			    	String sCheck = CounterByCharacterAscii_AlphanumericZZZ.getStringForNumber(itempLeft, objCounterStrategy);
//			    	assertEquals("Gegenprobe wurde erfolgreich erwartet.", stemp, sCheck);
					btemp = getNumberForStringAlphanumeric_StrategySignificantTest_(objCounterStrategy,stemp);
					if(!btemp){
						ExceptionZZZ ez = new ExceptionZZZ("AlphanumericCounter: Fehler beim String '" + stemp + "'", KernelZZZ.iERROR_RUNTIME, CounterByCharacterAscii_AlphanumericZZZTest.class, ReflectCodeZZZ.getMethodCurrentName());
						throw ez;
					}
				} catch (ExceptionZZZ ez) {
					fail("Method throws an exception." + ez.getMessageLast());
				} 
		    	
		    	try {				
					stemp = "ABC";
//			    	objCounterStrategy.isRightAligned(false);
//					itempLeft = CounterByCharacterAscii_AlphanumericZZZ.getNumberForString(stemp,objCounterStrategy); //linksbündig 73
//					btemp = assertCheckNullBordersAlphanumericStrategyBasedSignificant_(stemp, itempLeft);
//			    	assertTrue("Fehler beim Check auf Null Werte", btemp);
//			    	
//			    	objCounterStrategy.isRightAligned(true);
//			    	itempRight = CounterByCharacterAscii_AlphanumericZZZ.getNumberForString(stemp,objCounterStrategy); //rechtsbündig 73
//					btemp = assertCheckNullBordersAlphanumericStrategyBasedSignificant_(stemp, itempRight);
//			    	assertTrue("Fehler beim Check auf Null Werte", btemp);		    	
//			    	assertFalse("Links- oder Rechtsbündig soll bei 'ABC' (also ungleichen Zeichen) NICHT den gleichen Zahlenwert haben", itempLeft==itempRight);
//			    	itempCheck = itempRight;	
//			    	
//			    	//Mache die Gegenprobe
//			    	objCounterStrategy.isRightAligned(false);
//			    	String sCheckLeft = CounterByCharacterAscii_AlphanumericZZZ.getStringForNumber(itempLeft, objCounterStrategy);
//			    	assertEquals("Gegenprobe wurde erfolgreich erwartet.", stemp, sCheckLeft);		    			    	
//			    	
//			    	//Drehe die Ziffern um..
					
					btemp = getNumberForStringAlphanumeric_StrategySignificantTest_(objCounterStrategy,stemp);
					if(!btemp){
						ExceptionZZZ ez = new ExceptionZZZ("AlphanumericCounter: Fehler beim String '" + stemp + "'", KernelZZZ.iERROR_RUNTIME, CounterByCharacterAscii_AlphanumericZZZTest.class, ReflectCodeZZZ.getMethodCurrentName());
						throw ez;
					}			    				    	
				} catch (ExceptionZZZ ez) {
					fail("Method throws an exception." + ez.getMessageLast());
				} 
		    	
				    	
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			try{
					stemp = "1Z";
					btemp = getNumberForStringAlphanumeric_StrategySignificantTest_(objCounterStrategy,stemp);
					if(!btemp){
						ExceptionZZZ ez = new ExceptionZZZ("AlphanumericCounter: Fehler beim String '" + stemp + "'", KernelZZZ.iERROR_RUNTIME, CounterByCharacterAscii_AlphanumericZZZTest.class, ReflectCodeZZZ.getMethodCurrentName());
						throw ez;
					}
				} catch (ExceptionZZZ ez) {
					fail("Method throws an exception." + ez.getMessageLast());
				} 	
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			try{
					stemp = "1Z9B";
					btemp = getNumberForStringAlphanumeric_StrategySignificantTest_(objCounterStrategy,stemp);
					if(!btemp){
						ExceptionZZZ ez = new ExceptionZZZ("AlphanumericCounter: Fehler beim String '" + stemp + "'", KernelZZZ.iERROR_RUNTIME, CounterByCharacterAscii_AlphanumericZZZTest.class, ReflectCodeZZZ.getMethodCurrentName());
						throw ez;
					}
					
					stemp = "B9Z1";
					btemp = getNumberForStringAlphanumeric_StrategySignificantTest_(objCounterStrategy,stemp);
					if(!btemp){
						ExceptionZZZ ez = new ExceptionZZZ("AlphanumericCounter: Fehler beim String '" + stemp + "'", KernelZZZ.iERROR_RUNTIME, CounterByCharacterAscii_AlphanumericZZZTest.class, ReflectCodeZZZ.getMethodCurrentName());
						throw ez;
					}
				} catch (ExceptionZZZ ez) {
					fail("Method throws an exception." + ez.getMessageLast());
				}
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			try{
					objCounterStrategy.isLowercase(true);
					stemp = "1z9b";
					btemp = getNumberForStringAlphanumeric_StrategySignificantTest_(objCounterStrategy,stemp);
					if(!btemp){
						ExceptionZZZ ez = new ExceptionZZZ("AlphanumericCounter: Fehler beim String '" + stemp + "'", KernelZZZ.iERROR_RUNTIME, CounterByCharacterAscii_AlphanumericZZZTest.class, ReflectCodeZZZ.getMethodCurrentName());
						throw ez;
					}
					
					stemp = "B9Z1";
					objCounterStrategy.isLowercase(false);
					btemp = getNumberForStringAlphanumeric_StrategySignificantTest_(objCounterStrategy,stemp);
					if(!btemp){
						ExceptionZZZ ez = new ExceptionZZZ("AlphanumericCounter: Fehler beim String '" + stemp + "'", KernelZZZ.iERROR_RUNTIME, CounterByCharacterAscii_AlphanumericZZZTest.class, ReflectCodeZZZ.getMethodCurrentName());
						throw ez;
					}
				} catch (ExceptionZZZ ez) {
					fail("Method throws an exception." + ez.getMessageLast());
				}
			
			
	    }
 
	 private boolean getNumberForStringAlphanumeric_StrategySignificantTest_(ICounterStrategyAlphanumericZZZ objCounterStrategy, String sAlphanumeric) throws ExceptionZZZ{
			boolean bReturn = false;
			main:{			
					String stemp; String sCheckLeft; String sCheckRight; int itemp; int itempLeft; int itempRight; int itempRightCheck;  int itempLeftCheck; boolean btemp;													
					String sAlphanumericNormed = null; String sAlphanumericReversedNormed = null;
										
					//##################################################
			    	//### Den String testen
			    	//##################################################	
					objCounterStrategy.isRightAligned(false);
					sAlphanumericNormed = getAlphanumericNormed_(objCounterStrategy, sAlphanumeric);				
					itempLeft = CounterByCharacterAscii_AlphanumericZZZ.getNumberForString(sAlphanumericNormed,objCounterStrategy); 
					itempLeftCheck = itempLeft; //Variable speichern für die Umkehrung des Strings
					btemp = assertCheckNullBordersAlphanumericStrategyBasedSignificant_(sAlphanumericNormed, itempLeft);
			    	assertTrue("Fehler beim Check auf Null Werte", btemp);
			    	
			    	//Mache die Gegenprobe
			    	sCheckLeft = CounterByCharacterAscii_AlphanumericZZZ.getStringForNumber(itempLeft, objCounterStrategy);			    	
			    	assertEquals("Gegenprobe wurde erfolgreich erwartet.", sAlphanumericNormed, sCheckLeft);	
			    	
			    	//+++++++++++++++++++++++++++++++++++
			    	objCounterStrategy.isRightAligned(true);
			    	sAlphanumericNormed = getAlphanumericNormed_(objCounterStrategy, sAlphanumeric);	
			    	itempRight = CounterByCharacterAscii_AlphanumericZZZ.getNumberForString(sAlphanumericNormed,objCounterStrategy); 
			    	itempRightCheck = itempRight; //Variable speichern für die Umkehrung des Strings
					btemp = assertCheckNullBordersAlphanumericStrategyBasedSignificant_(sAlphanumericNormed, itempRight);
			    	assertTrue("Fehler beim Check auf Null Werte", btemp);		
			    				    	
			    	//Mache die Gegenprobe
			    	sCheckRight = CounterByCharacterAscii_AlphanumericZZZ.getStringForNumber(itempRight, objCounterStrategy);
			    	assertEquals("Gegenprobe wurde erfolgreich erwartet.", sAlphanumericNormed, sCheckRight);	
			    	
			    	
			    	//+++++++++++++++++++++++++++++++++++
			    	if(!StringZZZ.isPalindrom(sAlphanumericNormed)){
			    		assertFalse("Links- oder Rechtsbündig darf NICHT den gleichen Zahlenwert haben, außer bei 'Palindrom' wie z.B. AA. String ist '" + sAlphanumericNormed + "'.", itempLeft==itempRight);
			    	}else{
			    		assertTrue("Links- oder Rechtsbündig MUSS den gleichen Zahlenwert haben, da es ein 'Palindrom' wie z.B. AA. String ist '" + sAlphanumericNormed + "'.", itempLeft==itempRight);
			    	}
			    	
			    	
			    	//##################################################
			    	//### Den String umdrehen und erneut testen
			    	//##################################################			    	
			    	String sAlphanumericReversed = StringZZZ.reverse(sAlphanumeric);
			    	
			    	//+++++++++++++++++++++++++++++++++++
			    	objCounterStrategy.isRightAligned(false);
			    	sAlphanumericReversedNormed = getAlphanumericNormed_(objCounterStrategy, sAlphanumericReversed);					    
			    	itempLeft = CounterByCharacterAscii_AlphanumericZZZ.getNumberForString(sAlphanumericReversedNormed,objCounterStrategy); 
			    	btemp = assertCheckNullBordersAlphanumericStrategyBasedSignificant_(sAlphanumericReversedNormed, itempLeft);
			    	assertTrue("Fehler beim Check auf Null Werte", btemp);
			    	assertEquals("Umgedrehter String soll den gleichen Zahlenwert haben wie andersherum und andersbündig orientiert.  '" + sAlphanumericReversedNormed +"'", itempLeft,itempRightCheck);

			    	//Mache die Gegenprobe
			    	sCheckLeft = CounterByCharacterAscii_AlphanumericZZZ.getStringForNumber(itempLeft, objCounterStrategy);
			    	assertEquals("Gegenprobe wurde erfolgreich erwartet.", sAlphanumericReversedNormed, sCheckLeft);		    			    				    	    					    	    	
		    	
			    	//+++++++++++++++++++++++++++++++++++
			    	objCounterStrategy.isRightAligned(true);
			    	sAlphanumericReversedNormed = getAlphanumericNormed_(objCounterStrategy, sAlphanumericReversed);	
			    	itempRight = CounterByCharacterAscii_AlphanumericZZZ.getNumberForString(sAlphanumericReversedNormed,objCounterStrategy); 
					btemp = assertCheckNullBordersAlphanumericStrategyBasedSignificant_(sAlphanumericReversedNormed, itempRight);
			    	assertTrue("Fehler beim Check auf Null Werte", btemp);	
			    	assertEquals("Umgedrehter String soll den gleichen Zahlenwert haben wie anders-bündig orientiert.  '" + sAlphanumericReversedNormed +"'", itempLeft,itempRightCheck);
			    	
			    	//Mache die Gegenprobe
			    	sCheckRight = CounterByCharacterAscii_AlphanumericZZZ.getStringForNumber(itempRight, objCounterStrategy);
			    	assertEquals("Gegenprobe wurde erfolgreich erwartet.", sAlphanumericReversedNormed, sCheckRight);
			    	
			    	//+++++++++++++++++++++++++++++++++++
			    	if(!StringZZZ.isPalindrom(sAlphanumericReversedNormed)){
			    		assertFalse("Links- oder Rechtsbündig darf NICHT den gleichen Zahlenwert haben, außer bei 'Palindrom' wie z.B. AA. String ist '" + sAlphanumericReversedNormed + "'.", itempLeft==itempRight);
			    	}else{
			    		assertTrue("Links- oder Rechtsbündig MUSS den gleichen Zahlenwert haben, da es ein 'Palindrom' wie z.B. AA. String ist '" + sAlphanumericReversedNormed + "'.", itempLeft==itempRight);
			    	}
			    			
			    	bReturn = true;							    	    								
			}//end main:
			return bReturn;
		}
	 
	 /** TODO GOON IcounterStrategyAlphanumericSignificant als neues Interface und diese Methode darin einbauen.
	 * @param objCounterStrategy
	 * @param sAlphanumeric
	 * @return
	 * @author Fritz Lindhauer, 30.05.2019, 13:33:20
	 */
	private String getAlphanumericNormed_(ICounterStrategyAlphanumericZZZ objCounterStrategy, String sAlphanumeric){
		 String sReturn = null;
		 main:{
			 boolean bLowercase = objCounterStrategy.isLowercase();
		 	String sCharToStrip = CounterByCharacterAscii_AlphanumericZZZ.getCharForPosition(CounterByCharacterAscii_AlphanumericZZZ.iPOSITION_MIN, bLowercase);
			if(!objCounterStrategy.isRightAligned()){
				sReturn = StringZZZ.stripLeft(sAlphanumeric, sCharToStrip); //Merke: Führende "0" Werte können nicht wiederhergestelllt werden, aus dem Zahlenwert.
			}else{
				sReturn = StringZZZ.stripRight(sAlphanumeric, sCharToStrip); //Merke: Führende "0" Werte können nicht wiederhergestelllt werden, aus dem Zahlenwert.
			}
		 }//end main:
		 return sReturn;
	 }

public void testGetStringAlphanumericForNumber_StrategyMultiple(){
	try{
		
	//"MULTIPLE STRATEGY"-Ergebnisse
	int itemp = CounterByCharacterAscii_AlphanumericZZZ.iPOSITION_MIN-1;
	String stemp = CounterByCharacterAscii_AlphanumericZZZ.getStringMultipleForNumber(itemp, false); // .getCharMultipleForNumber(itemp);
	boolean btemp = assertCheckNullBordersAlphanumericStrategyBased_(itemp, stemp);
	assertTrue("Fehler beim Check auf Null Werte", btemp);
	
	//+++++++++++++++++++++++++++++++++++++++++++++++
	itemp = CounterByCharacterAscii_AlphanumericZZZ.iPOSITION_MIN;
		
	//"MULTIPLE STRATEGY"-Ergebnisse
	stemp = CounterByCharacterAscii_AlphanumericZZZ.getStringMultipleForNumber(itemp);
	btemp = assertCheckNullBordersAlphanumericStrategyBased_(itemp, stemp);
	assertTrue("Fehler beim Check auf Null Werte", btemp);
	assertEquals("0",stemp);
	
	stemp = CounterByCharacterAscii_AlphanumericZZZ.getStringMultipleForNumber(itemp, true); //true ist lowercase, false ist uppercase
	btemp = assertCheckNullBordersAlphanumericStrategyBased_(itemp, stemp);
	assertTrue("Fehler beim Check auf Null Werte", btemp);
	assertEquals("0",stemp); //Kleinbuchstaben für Zahlen gibt es ja nicht.
		    	
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	itemp = CounterByCharacterAscii_AlphanumericZZZ.iPOSITION_MAX;

	//"MULTIPLE STRATEGY"-Ergebnisse
	stemp = CounterByCharacterAscii_AlphanumericZZZ.getStringMultipleForNumber(itemp);
	btemp = assertCheckNullBordersAlphanumericStrategyBased_(itemp, stemp);
	assertTrue("Fehler beim Check auf Null Werte", btemp);
	assertEquals("Z",stemp);
	
	stemp = CounterByCharacterAscii_AlphanumericZZZ.getStringMultipleForNumber(itemp, true); //true ist lowercase, false ist uppercase
	btemp = assertCheckNullBordersAlphanumericStrategyBased_(itemp, stemp);
	assertTrue("Fehler beim Check auf Null Werte", btemp);
	assertEquals("z",stemp);
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	
	
	//######################
	//Nun Werte eingeben, die über 27 liegen. Diese müssen erlaubt sein.
	//+++++++++++++++++++++++++++++++++++++++++++++++++++
	itemp = CounterByCharacterAscii_AlphanumericZZZ.iPOSITION_MAX+1;
	
	//"MULTIPLE STRATEGY"-Ergebnisse
	stemp = CounterByCharacterAscii_AlphanumericZZZ.getStringMultipleForNumber(itemp);
	btemp = assertCheckNullBordersAlphanumericStrategyBased_(itemp, stemp);
	assertTrue("Fehler beim Check auf Null Werte", btemp);
	assertEquals("00",stemp);
	
	stemp = CounterByCharacterAscii_AlphanumericZZZ.getStringMultipleForNumber(itemp, true); //true ist lowercase, false ist uppercase
	btemp = assertCheckNullBordersAlphanumericStrategyBased_(itemp, stemp);
	assertTrue("Fehler beim Check auf Null Werte", btemp);
	assertEquals("00",stemp); //Keine Kleinbuchstaben bei Ziffern
	
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	
	//... weitere Tests	
	itemp = CounterByCharacterAscii_AlphanumericZZZ.iPOSITION_MAX+2;
	
	//"MULTIPLE STRATEGY"-Ergebnisse
	stemp = CounterByCharacterAscii_AlphanumericZZZ.getStringMultipleForNumber(itemp);
	btemp = assertCheckNullBordersAlphanumericStrategyBased_(itemp, stemp);
	assertTrue("Fehler beim Check auf Null Werte", btemp);
	assertEquals("11",stemp);
	
	stemp = CounterByCharacterAscii_AlphanumericZZZ.getStringMultipleForNumber(itemp, true); //true ist lowercase, false ist uppercase
	btemp = assertCheckNullBordersAlphanumericStrategyBased_(itemp, stemp);
	assertTrue("Fehler beim Check auf Null Werte", btemp);
	assertEquals("11",stemp);
	
	//... weitere Tests	
	itemp = CounterByCharacterAscii_AlphanumericZZZ.iPOSITION_MAX*2;
	
	//"MULTIPLE STRATEGY"-Ergebnisse
	stemp = CounterByCharacterAscii_AlphanumericZZZ.getStringMultipleForNumber(itemp);
	btemp = assertCheckNullBordersAlphanumericStrategyBased_(itemp, stemp);
	assertTrue("Fehler beim Check auf Null Werte", btemp);
	assertEquals("ZZ",stemp);
	
	stemp = CounterByCharacterAscii_AlphanumericZZZ.getStringMultipleForNumber(itemp,  true); //true ist lowercase, false ist uppercase
	btemp = assertCheckNullBordersAlphanumericStrategyBased_(itemp, stemp);
	assertTrue("Fehler beim Check auf Null Werte", btemp);
	assertEquals("zz",stemp);
	
} catch (ExceptionZZZ ez) {
	fail("Method throws an exception." + ez.getMessageLast());
} 
}

public void testGetStringAlphanumericForNumber_StrategySerial(){
	try{
	
	//"SERIAL STRATEGIE"-Ergebnisse
	int itemp = CounterByCharacterAscii_AlphanumericZZZ.iPOSITION_MIN-1;
	String stemp = CounterByCharacterAscii_AlphanumericZZZ.getStringForNumber(itemp);
	boolean btemp = assertCheckNullBordersAlphanumericStrategyBased_(itemp, stemp);
	assertTrue("Fehler beim Check auf Null Werte", btemp);
	
	//+++++++++++++++++++++++++++++++++++++++++++++++
	itemp = CounterByCharacterAscii_AlphanumericZZZ.iPOSITION_MIN;
	
	
	stemp = CounterByCharacterAscii_AlphanumericZZZ.getStringForNumber(itemp);
	btemp = assertCheckNullBordersAlphanumericStrategyBased_(itemp, stemp);
	assertTrue("Fehler beim Check auf Null Werte", btemp);
	assertEquals("0",stemp);
	
	stemp = CounterByCharacterAscii_AlphanumericZZZ.getStringForNumber(itemp, true); //true ist lowercase, false ist uppercase
	btemp = assertCheckNullBordersAlphanumericStrategyBased_(itemp, stemp);
	assertTrue("Fehler beim Check auf Null Werte", btemp);
	assertEquals("0",stemp); //die Kleinbuchstaben für Zahlen gibt es ja nicht
		    	
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	itemp = CounterByCharacterAscii_AlphanumericZZZ.iPOSITION_MAX;
	
	//"SERIAL STRATEGIE"-Ergebnisse
	stemp = CounterByCharacterAscii_AlphanumericZZZ.getStringForNumber(itemp);
	btemp = assertCheckNullBordersAlphanumericStrategyBased_(itemp, stemp);
	assertTrue("Fehler beim Check auf Null Werte", btemp);
	assertEquals("Z",stemp);
	
	stemp = CounterByCharacterAscii_AlphanumericZZZ.getStringForNumber(itemp, true); //true ist lowercase, false ist uppercase
	btemp = assertCheckNullBordersAlphanumericStrategyBased_(itemp, stemp);
	assertTrue("Fehler beim Check auf Null Werte", btemp);
	assertEquals("z",stemp);

	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	
	
	//######################
	//Nun Werte eingeben, die über 27 liegen. Diese müssen erlaubt sein.
	//+++++++++++++++++++++++++++++++++++++++++++++++++++
	
	//"SERIAL STRATEGIE"-Ergebnisse
	itemp = CounterByCharacterAscii_AlphanumericZZZ.iPOSITION_MAX+1;
	stemp = CounterByCharacterAscii_AlphanumericZZZ.getStringForNumber(itemp);
	btemp = assertCheckNullBordersAlphanumericStrategyBased_(itemp, stemp);
	assertTrue("Fehler beim Check auf Null Werte", btemp);
	assertEquals("Z0",stemp);
	
	stemp = CounterByCharacterAscii_AlphanumericZZZ.getStringForNumber(itemp, true); //true ist lowercase, false ist uppercase
	btemp = assertCheckNullBordersAlphanumericStrategyBased_(itemp, stemp);
	assertTrue("Fehler beim Check auf Null Werte", btemp);
	assertEquals("z0",stemp);
	
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	
	//... weitere Tests
 	//"SERIAL STRATEGIE"-Ergebnisse
	itemp = CounterByCharacterAscii_AlphanumericZZZ.iPOSITION_MAX+2;
	stemp = CounterByCharacterAscii_AlphanumericZZZ.getStringForNumber(itemp);
	btemp = assertCheckNullBordersAlphanumericStrategyBased_(itemp, stemp);
	assertTrue("Fehler beim Check auf Null Werte", btemp);
	assertEquals("Z1",stemp);
	
	stemp = CounterByCharacterAscii_AlphanumericZZZ.getStringForNumber(itemp, true); //true ist lowercase, false ist uppercase
	btemp = assertCheckNullBordersAlphanumericStrategyBased_(itemp, stemp);
	assertTrue("Fehler beim Check auf Null Werte", btemp);
	assertEquals("z1",stemp);
	
	//"SERIAL STRATEGIE"-Ergebnisse
	itemp = CounterByCharacterAscii_AlphanumericZZZ.iPOSITION_MAX*2;
	stemp = CounterByCharacterAscii_AlphanumericZZZ.getStringForNumber(itemp);
	btemp = assertCheckNullBordersAlphanumericStrategyBased_(itemp, stemp);
	assertTrue("Fehler beim Check auf Null Werte", btemp);
	assertEquals("ZZ",stemp);
	
	stemp = CounterByCharacterAscii_AlphanumericZZZ.getStringForNumber(itemp, true); //true ist lowercase, false ist uppercase
	btemp = assertCheckNullBordersAlphanumericStrategyBased_(itemp, stemp);
	assertTrue("Fehler beim Check auf Null Werte", btemp);
	assertEquals("zz",stemp);
	} catch (ExceptionZZZ ez) {
		fail("Method throws an exception." + ez.getMessageLast());
	} 
}

public void testGetStringAlphanumericForNumber_StrategySerialRightAligned(){
	try{
		
		//"SERIAL STRATEGIE"-Ergebnisse, Grossbuchstaben und rechtsbündig 
		int itemp = CounterByCharacterAscii_AlphanumericZZZ.iPOSITION_MIN-1;
		String stemp = CounterByCharacterAscii_AlphanumericZZZ.getStringForNumber(itemp, false, true);
		boolean btemp = assertCheckNullBordersAlphanumericStrategyBased_(itemp, stemp);
		assertTrue("Fehler beim Check auf Null Werte", btemp);
		
		//+++++++++++++++++++++++++++++++++++++++++++++++
		itemp = CounterByCharacterAscii_AlphanumericZZZ.iPOSITION_MIN;
		
		//1. true ist lowercase, false ist uppercase | 2. true ist rightbound
		stemp = CounterByCharacterAscii_AlphanumericZZZ.getStringForNumber(itemp, false, true);
		btemp = assertCheckNullBordersAlphanumericStrategyBased_(itemp, stemp);
		assertTrue("Fehler beim Check auf Null Werte", btemp);
		assertEquals("0",stemp);
		
		stemp = CounterByCharacterAscii_AlphanumericZZZ.getStringForNumber(itemp, true, true); 
		btemp = assertCheckNullBordersAlphanumericStrategyBased_(itemp, stemp);
		assertTrue("Fehler beim Check auf Null Werte", btemp);
		assertEquals("0",stemp); //die Kleinbuchstaben für Zahlen gibt es ja nicht
			    	
		//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		itemp = CounterByCharacterAscii_AlphanumericZZZ.iPOSITION_MAX;
		
		//"SERIAL STRATEGIE"-Ergebnisse
		stemp = CounterByCharacterAscii_AlphanumericZZZ.getStringForNumber(itemp, false, true);
		btemp = assertCheckNullBordersAlphanumericStrategyBased_(itemp, stemp);
		assertTrue("Fehler beim Check auf Null Werte", btemp);
		assertEquals("Z",stemp);
		
		stemp = CounterByCharacterAscii_AlphanumericZZZ.getStringForNumber(itemp, true, true); //true ist lowercase, false ist uppercase
		btemp = assertCheckNullBordersAlphanumericStrategyBased_(itemp, stemp);
		assertTrue("Fehler beim Check auf Null Werte", btemp);
		assertEquals("z",stemp);

		//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		
		
		//######################
		//Nun Werte eingeben, die über 36 liegen. Diese müssen erlaubt sein.
		//+++++++++++++++++++++++++++++++++++++++++++++++++++
		
		//"SERIAL STRATEGIE"-Ergebnisse
		//1. true ist lowercase, false ist uppercase | 2. true ist rightbound
		itemp = CounterByCharacterAscii_AlphanumericZZZ.iPOSITION_MAX+1;
		stemp = CounterByCharacterAscii_AlphanumericZZZ.getStringForNumber(itemp, false, true);
		btemp = assertCheckNullBordersAlphanumericStrategyBased_(itemp, stemp);
		assertTrue("Fehler beim Check auf Null Werte", btemp);
		assertEquals("0Z",stemp);
		
		stemp = CounterByCharacterAscii_AlphanumericZZZ.getStringForNumber(itemp, true, true);
		btemp = assertCheckNullBordersAlphanumericStrategyBased_(itemp, stemp);
		assertTrue("Fehler beim Check auf Null Werte", btemp);
		assertEquals("0z",stemp);
		
		//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		
		//... weitere Tests
	 	//"SERIAL STRATEGIE"-Ergebnisse
		//1. true ist lowercase, false ist uppercase | 2. true ist rightbound
		itemp = CounterByCharacterAscii_AlphanumericZZZ.iPOSITION_MAX+2;
		stemp = CounterByCharacterAscii_AlphanumericZZZ.getStringForNumber(itemp, false, true);
		btemp = assertCheckNullBordersAlphanumericStrategyBased_(itemp, stemp);
		assertTrue("Fehler beim Check auf Null Werte", btemp);
		assertEquals("1Z",stemp);
		
		stemp = CounterByCharacterAscii_AlphanumericZZZ.getStringForNumber(itemp, true, true);
		btemp = assertCheckNullBordersAlphanumericStrategyBased_(itemp, stemp);
		assertTrue("Fehler beim Check auf Null Werte", btemp);
		assertEquals("1z",stemp);
		
		//"SERIAL STRATEGIE"-Ergebnisse
		itemp = CounterByCharacterAscii_AlphanumericZZZ.iPOSITION_MAX*2;
		stemp = CounterByCharacterAscii_AlphanumericZZZ.getStringForNumber(itemp, false, true);
		btemp = assertCheckNullBordersAlphanumericStrategyBased_(itemp, stemp);
		assertTrue("Fehler beim Check auf Null Werte", btemp);
		assertEquals("ZZ",stemp);
		
		stemp = CounterByCharacterAscii_AlphanumericZZZ.getStringForNumber(itemp, true, true); //true ist lowercase, false ist uppercase
		btemp = assertCheckNullBordersAlphanumericStrategyBased_(itemp, stemp);
		assertTrue("Fehler beim Check auf Null Werte", btemp);
		assertEquals("zz",stemp);
		
		//++++++ Mal einfach nur hochzählen... dann merkt man den Unterschied zu "Signifikant" Strategie
		itemp = CounterByCharacterAscii_AlphanumericZZZ.iPOSITION_MAX+3;
		for(int icount=itemp; icount <= 100; icount++){
			stemp = CounterByCharacterAscii_AlphanumericZZZ.getStringForNumber(icount, false, true);
			btemp = assertCheckNullBordersAlphanumericStrategyBased_(itemp, stemp);
			//assertTrue("Fehler beim Check auf Null Werte", btemp);
			//assertEquals("1Z",stemp);
		}
		//Ergebnisse sind 2Z, 3Z, ... , AZ, BZ, ...
		//Das sieht also anders aus als 10, 11, ..., 19, 20, 21, .... wie es bei einer "signifikatem Stellenwert" wäre.
		
		
		
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		} 
}


}//end class
	

