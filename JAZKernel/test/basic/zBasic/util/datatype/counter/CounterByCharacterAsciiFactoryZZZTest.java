package basic.zBasic.util.datatype.counter;

import basic.zBasic.ExceptionZZZ;
import junit.framework.TestCase;

public class CounterByCharacterAsciiFactoryZZZTest   extends TestCase{
	private ICounterByCharacterAsciiFactoryZZZ objCounterFactory = null;
//	 private HashMapExtendedZZZ<String, EnumSetMappedTestTypeZZZ> hmTestGenerics = null;
	 
    protected void setUp(){
      
	//try {			
	
    	//### Das spezielle Singleton Factory Testobjekct
    	objCounterFactory = CounterByCharacterAsciiFactoryZZZ.getInstance();
    	
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
        
    /*
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
  */  
   
    
public void testGetStringNumericForNumber_FactoryBasedStrategySerial(){

	try {	
		int itemp; int itempold; String stemp; String stempold; boolean btemp;
		
	//Damit die Counter per Factory erzeugt werden könnne: Konstruktoren in alle Counter einbauen (die lediglich static-Methoden reichen nicht)
	//A) MULTIPLE-STRATEGY ist default.
	ICounterStringZZZ objCounterString = objCounterFactory.createCounter(CounterByCharacterAsciiFactoryZZZ.iCOUNTER_TYPE_NUMERIC);

	//Untenstehende Tests und Ergebnisse müssen auch mit der Factory und den daraus generierten Counter-Objekten erfüllbar sein.
    //A) OHNE STRATEGY-OBJEKT ZU ÜBERGEBEN
	itemp = objCounterString.getValueCurrent();
	stemp = objCounterString.current();
	btemp = assertCheckNullBordersNumericStrategyBased_(itemp, stemp);
	assertTrue("Fehler beim Check auf Null Werte", btemp);
	
	itempold = itemp;
	stemp = objCounterString.next();
	btemp = assertCheckNullBordersNumericStrategyBased_(itemp, stemp);
	assertTrue("Fehler beim Check auf Null Werte", btemp);
	itemp = objCounterString.getValueCurrent();
	assertTrue("Fehler beim Erhöhen des Counters", itempold+1==itemp);
	
	itemp = 42;
	stemp = objCounterString.change(itemp);
	btemp = assertCheckNullBordersNumericStrategyBased_(itemp, stemp);
	assertTrue("Fehler beim Check auf Null Werte", btemp);
	assertEquals("Fehler beim Setzen des Counters", "99991", stemp);
	
	itempold = itemp;
	itemp = objCounterString.getValueCurrent();
	assertTrue("Fehler beim Erhöhen des Counters", itempold==itemp);
				
	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	//B) Definiere per Factory einen Counter mit Serieller Strategy
	CounterStrategyNumericSerialZZZ objCounterStrategySerial = new CounterStrategyNumericSerialZZZ();
	ICounterStringStrategyNumericUserZZZ objCounterStringSeriel = objCounterFactory.createCounter(objCounterStrategySerial);
	itemp = objCounterStringSeriel.getValueCurrent();
	stemp = objCounterStringSeriel.current();
	
	itempold = itemp;
	stemp = objCounterStringSeriel.next();
	itemp = objCounterStringSeriel.getValueCurrent();
	assertTrue("Fehler beim Erhöhen des Counters", itempold+1==itemp);
	
	//+++++++++++++++++++++++++++++++++	
	//Setze den Counter durch String-Wert
	//...ungültige Zeichen
	try{
		stemp = "ZA";
		objCounterStringSeriel.setValueCurrent(stemp);
		fail("Method should have thrown an exception for the string '"+stemp+"'");
	} catch (ExceptionZZZ ez) {
		//Erwartetete Exception
	} 
	
	//... ungültige serielle Syntax
	try{
		stemp = "00";
		objCounterStringSeriel.setValueCurrent(stemp);
		fail("Method should have thrown an exception for the string '"+stemp+"'");
	} catch (ExceptionZZZ ez) {
		//Erwartetete Exception
	} 
	
	stemp = "90";
	objCounterStringSeriel.setValueCurrent(stemp);
	itemp = objCounterStringSeriel.getValueCurrent();
	
	stempold = stemp;
	stemp = objCounterStringSeriel.current();
	assertEquals(stemp, stempold);
		
	itempold = itemp;
	stemp = objCounterStringSeriel.next();
	itemp = objCounterStringSeriel.getValueCurrent();
	assertTrue("Fehler beim Erhöhen des Counters", itempold+1==itemp);
	
	//#################################################################
	//C) Mache Alphabet-Counter per Factory.
	CounterStrategyAlphabetMultipleZZZ objCounterStrategyAlphaM = new CounterStrategyAlphabetMultipleZZZ();
	//ICounterStringStrategyAlphanumericUserZZZ objCounterStringSeriel = objCounterFactory.createCounter(CounterByCharacterAsciiFactoryZZZ.iCOUNTER_TYPE_NUMERIC, objCounterStrategyAlphaM);
	ICounterStringStrategyAlphanumericUserZZZ objCounterAlphaM = objCounterFactory.createCounter(objCounterStrategyAlphaM);
	
	
	itemp = objCounterAlphaM.getValueCurrent();
	stemp = objCounterAlphaM.current();
	
	itempold = itemp;
	stemp = objCounterAlphaM.next();
	itemp = objCounterAlphaM.getValueCurrent();
	assertTrue("Fehler beim Erhöhen des Counters", itempold+1==itemp);
	
	//+++++++++++++++++++++++++++++++++	
	//Setze den Counter durch String-Wert
	//...ungültige Zeichen
	try{
		stemp = "90";
		objCounterAlphaM.setValueCurrent(stemp);
		fail("Method should have thrown an exception for the string '"+stemp+"'");
	} catch (ExceptionZZZ ez) {
		//Erwartetete Exception
	} 
	
	//... ungültige multiple  Syntax
	try{
		stemp = "ZA";
		objCounterAlphaM.setValueCurrent(stemp);
		fail("Method should have thrown an exception for the string '"+stemp+"'");
	} catch (ExceptionZZZ ez) {
		//Erwartetete Exception
	} 
	
	//... gültige multiple Syntax
	stemp = "AA";
	objCounterAlphaM.setValueCurrent(stemp);
	itemp = objCounterAlphaM.getValueCurrent();
	
	stempold = stemp;
	stemp = objCounterStringSeriel.current();
	assertEquals(stemp, stempold);
		
	itempold = itemp;
	stemp = objCounterAlphaM.next();
	itemp = objCounterAlphaM.getValueCurrent();
	assertTrue("Fehler beim Erhöhen des Counters", itempold+1==itemp);
	
	
	
	///..................... TODO GOON 20190307
	//Erstelle einen Counter über den Konstruktor 
	//A) Mit int Wert
	//B) Mit String  Wert
	
	
	
	//### Das spezielle Generics Testobjekt			
//	hmTestGenerics = new HashMapExtendedZZZ<String, EnumSetMappedTestTypeZZZ>();
//	
//	Set<EnumSetMappedTestTypeZZZ> allTypes = EnumSet.allOf(EnumSetMappedTestTypeZZZ.class);
//	for(EnumSetMappedTestTypeZZZ myType : allTypes) {
//		//String sType = myType.getAbbreviation();
//		String sName = myType.name();
//		hmTestGenerics.put(sName,myType);
//	}
	

	} catch (ExceptionZZZ ez) {
		fail("Method throws an exception." + ez.getMessageLast());
	} 

}

/*
 *TODO GOON: FEHLERFALL...
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
*/


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

}//end class


