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
	assertEquals("Fehler beim Setzen des Counters", "11111", stemp);
	
	itempold = itemp;
	itemp = objCounterString.getValueCurrent();
	assertTrue("Fehler beim Erhöhen des Counters", itempold==itemp);
				
	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	//B) Definiere per Factory einen Counter mit Serieller Strategy
	CounterStrategyNumericSerialZZZ objCounterStrategySerial = new CounterStrategyNumericSerialZZZ();
	ICounterNumericZZZ objCounterStringSeriel = objCounterFactory.createCounter(objCounterStrategySerial);
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
	
	
	
	///..................... TODO GOON 20190307
	//Erstelle einen Counter über den Konstruktor 
	//A) Mit int Wert
	//B) Mit String  Wert
	
	
	} catch (ExceptionZZZ ez) {
		fail("Method throws an exception." + ez.getMessageLast());
	} 

}


public void testGetStringAlphabetForNumber_FactoryBasedStrategyMultiple(){

	try {	
		int itemp; int itempold; String stemp; String stempold; boolean btemp;
		
		//Damit die Counter per Factory erzeugt werden könnne: Konstruktoren in alle Counter einbauen (die lediglich static-Methoden reichen nicht)	
		//A) Mache Alphabet-Counter per Factory und Miltiple Strategy
		CounterStrategyAlphabetMultipleZZZ objCounterStrategyAlphaM = new CounterStrategyAlphabetMultipleZZZ();
		ICounterAlphanumericZZZ objCounterAlphaM = objCounterFactory.createCounter(objCounterStrategyAlphaM);
		
		///+++++++++++++++++++++++++++++++
		//Hole Initialwert
		itemp = objCounterAlphaM.getValueCurrent();
		stemp = objCounterAlphaM.current();
		assertEquals("A",stemp);
		
		itempold = itemp;
		stemp = objCounterAlphaM.next();
		assertEquals("B",stemp);
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
		stemp = objCounterAlphaM.current();
		assertEquals(stemp, stempold);
			
		itempold = itemp;
		stemp = objCounterAlphaM.next();
		assertEquals("BB",stemp);
		itemp = objCounterAlphaM.getValueCurrent();
		assertTrue("Fehler beim Erhöhen des Counters", itempold+1==itemp);
		
		
		
		///..................... TODO GOON 20190307
		//Erstelle einen Counter über den Konstruktor 
		//A) Mit int Wert
		//B) Mit String  Wert
		

	} catch (ExceptionZZZ ez) {
		fail("Method throws an exception." + ez.getMessageLast());
	} 

}

public void testGetStringAlphanumericForNumber_FactoryBasedStrategyMultiple(){

	try {	
		int itemp; int itempold; String stemp; String stempold; boolean btemp;
		
		//Damit die Counter per Factory erzeugt werden könnne: Konstruktoren in alle Counter einbauen (die lediglich static-Methoden reichen nicht)
		
		//####################################
		//A) Mache Alphabet-Counter per Factory und Multiple Strategy
		//####################################
		CounterStrategyAlphanumericMultipleZZZ objCounterStrategyAlphaNumM = new CounterStrategyAlphanumericMultipleZZZ();
		ICounterAlphanumericZZZ objCounterAlphaM = objCounterFactory.createCounter(objCounterStrategyAlphaNumM);
		
		///+++++++++++++++++++++++++++++++
		//Hole Initialwert
		itemp = objCounterAlphaM.getValueCurrent();
		stemp = objCounterAlphaM.current();
		assertEquals("0",stemp);
		
		itempold = itemp;
		stemp = objCounterAlphaM.next();
		assertEquals("1",stemp);
		itemp = objCounterAlphaM.getValueCurrent();
		assertTrue("Fehler beim Erhöhen des Counters", itempold+1==itemp);
		
		//+++++++++++++++++++++++++++++++++	
		//Setze den Counter durch String-Wert
		//...ungültige Zeichen
		try{
			stemp = "+";
			objCounterAlphaM.setValueCurrent(stemp);
			fail("Method should have thrown an exception for the string '"+stemp+"'");
		} catch (ExceptionZZZ ez) {
			//Erwartetete Exception
		} 
		
		//... ungültige multiple  Syntax
		try{
			stemp = "90";
			objCounterAlphaM.setValueCurrent(stemp);
			fail("Method should have thrown an exception for the string '"+stemp+"'");
		} catch (ExceptionZZZ ez) {
			//Erwartetete Exception
		} 
		
		//... gültige multiple Syntax
		stemp = "00";
		objCounterAlphaM.setValueCurrent(stemp);
		itemp = objCounterAlphaM.getValueCurrent();
		
		stempold = stemp;
		stemp = objCounterAlphaM.current();
		assertEquals(stemp, stempold);
			
		itempold = itemp;
		stemp = objCounterAlphaM.next();
		assertEquals("11",stemp);
		itemp = objCounterAlphaM.getValueCurrent();
		assertTrue("Fehler beim Erhöhen des Counters", itempold+1==itemp);
		
		
		
		//Erstelle einen Counter über den Konstruktor 
		//A) Mit int Wert
		ICounterAlphanumericZZZ objCounterAlphaM2 = objCounterFactory.createCounter(objCounterStrategyAlphaNumM,10);
		itemp = objCounterAlphaM2.getValueCurrent();
		stemp = objCounterAlphaM2.current();
		assertEquals("9",stemp);//Merke: int Wert 10==>Ziffern 0 bis 9 => es wird "9" zurückgegeben.
		
		itempold = itemp;
		stemp = objCounterAlphaM2.next();
		assertEquals("A",stemp);
		itemp = objCounterAlphaM2.getValueCurrent();
		assertTrue("Fehler beim Erhöhen des Counters", itempold+1==itemp);
		
		objCounterStrategyAlphaNumM.isLowercase(true);//Teste, ob die Änderung am Strategie-Objekt auch zu einer Änderung am Ergebnis führt.
		itempold = itemp;
		stemp = objCounterAlphaM2.next();
		assertEquals("b",stemp);
		itemp = objCounterAlphaM2.getValueCurrent();
		assertTrue("Fehler beim Erhöhen des Counters", itempold+1==itemp);
		
		//B) Mit String  Wert		
		objCounterStrategyAlphaNumM.isLowercase(false);
		ICounterAlphanumericZZZ objCounterAlphaM3 = objCounterFactory.createCounter(objCounterStrategyAlphaNumM,"9");
		itemp = objCounterAlphaM3.getValueCurrent();
		stemp = objCounterAlphaM3.current();
		assertEquals("9",stemp);//Merke: int Wert 10==>Ziffern 0 bis 9 => es wird "9" zurückgegeben.
		
		itempold = itemp;
		stemp = objCounterAlphaM3.next();
		assertEquals("A",stemp);
		itemp = objCounterAlphaM3.getValueCurrent();
		assertTrue("Fehler beim Erhöhen des Counters", itempold+1==itemp);
		
		objCounterStrategyAlphaNumM.isLowercase(true);//Teste, ob die Änderung am Strategie-Objekt auch zu einer Änderung am Ergebnis führt.
		itempold = itemp;
		stemp = objCounterAlphaM3.next();
		assertEquals("b",stemp);
		itemp = objCounterAlphaM3.getValueCurrent();
		assertTrue("Fehler beim Erhöhen des Counters", itempold+1==itemp);
		
		

	} catch (ExceptionZZZ ez) {
		fail("Method throws an exception." + ez.getMessageLast());
	} 

}

public void testGetStringAlphanumericForNumber_FactoryBasedStrategySerial(){

	try {	
		int itemp; int itempold; String stemp; String stempold; boolean btemp;
		
		//Damit die Counter per Factory erzeugt werden könnne: Konstruktoren in alle Counter einbauen (die lediglich static-Methoden reichen nicht)
		
		//####################################
		//B) Mache Alphabet-Counter per Factory und Serieller Strategy
		//####################################
		CounterStrategyAlphanumericSerialZZZ objCounterStrategyAlphaNumS = new CounterStrategyAlphanumericSerialZZZ();
		ICounterAlphanumericZZZ objCounterAlphaS = objCounterFactory.createCounter(objCounterStrategyAlphaNumS);
		
		///+++++++++++++++++++++++++++++++
		//Hole Initialwert
		itemp = objCounterAlphaS.getValueCurrent();
		stemp = objCounterAlphaS.current();
		assertEquals("0",stemp);
		
		itempold = itemp;
		stemp = objCounterAlphaS.next();
		assertEquals("1",stemp);
		itemp = objCounterAlphaS.getValueCurrent();
		assertTrue("Fehler beim Erhöhen des Counters", itempold+1==itemp);
		
		//+++++++++++++++++++++++++++++++++	
		//Setze den Counter durch String-Wert
		//...ungültige Zeichen
		try{
			stemp = "+";
			objCounterAlphaS.setValueCurrent(stemp);
			fail("Method should have thrown an exception for the string '"+stemp+"'");
		} catch (ExceptionZZZ ez) {
			//Erwartetete Exception
		} 
		
		//... ungültige serielle  Syntax
		try{
			stemp = "99";
			objCounterAlphaS.setValueCurrent(stemp);
			fail("Method should have thrown an exception for the string '"+stemp+"'");
		} catch (ExceptionZZZ ez) {
			//Erwartetete Exception
		} 
		
		//... gültige serielle Syntax
		stemp = "ZA";
		objCounterAlphaS.setValueCurrent(stemp);
		itemp = objCounterAlphaS.getValueCurrent();
		
		stempold = stemp;
		stemp = objCounterAlphaS.current();
		assertEquals(stemp, stempold);
			
		itempold = itemp;
		stemp = objCounterAlphaS.next();
		assertEquals("ZB",stemp);
		itemp = objCounterAlphaS.getValueCurrent();
		assertTrue("Fehler beim Erhöhen des Counters", itempold+1==itemp);
		
		
		
		//Erstelle einen Counter über den Konstruktor 
		//A) Mit int Wert
		ICounterAlphanumericZZZ objCounterAlphaS2 = objCounterFactory.createCounter(objCounterStrategyAlphaNumS,10);
		itemp = objCounterAlphaS2.getValueCurrent();
		stemp = objCounterAlphaS2.current();
		assertEquals("9",stemp);//Merke: int Wert 10==>Ziffern 0 bis 9 => es wird "9" zurückgegeben.
		
		itempold = itemp;
		stemp = objCounterAlphaS2.next();
		assertEquals("A",stemp);
		itemp = objCounterAlphaS2.getValueCurrent();
		assertTrue("Fehler beim Erhöhen des Counters", itempold+1==itemp);
		
		objCounterStrategyAlphaNumS.isLowercase(true);//Teste, ob die Änderung am Strategie-Objekt auch zu einer Änderung am Ergebnis führt.
		itempold = itemp;
		stemp = objCounterAlphaS2.next();
		assertEquals("b",stemp);
		itemp = objCounterAlphaS2.getValueCurrent();
		assertTrue("Fehler beim Erhöhen des Counters", itempold+1==itemp);
		
		//B) Mit String  Wert
		objCounterStrategyAlphaNumS.isLowercase(false);
		ICounterAlphanumericZZZ objCounterAlphaS3 = objCounterFactory.createCounter(objCounterStrategyAlphaNumS,"9");
		itemp = objCounterAlphaS3.getValueCurrent();
		stemp = objCounterAlphaS3.current();
		assertEquals("9",stemp);//Merke: int Wert 10==>Ziffern 0 bis 9 => es wird "9" zurückgegeben.
		
		itempold = itemp;
		stemp = objCounterAlphaS3.next();
		assertEquals("A",stemp);
		itemp = objCounterAlphaS3.getValueCurrent();
		assertTrue("Fehler beim Erhöhen des Counters", itempold+1==itemp);
		
		objCounterStrategyAlphaNumS.isLowercase(true);//Teste, ob die Änderung am Strategie-Objekt auch zu einer Änderung am Ergebnis führt.
		itempold = itemp;
		stemp = objCounterAlphaS3.next();
		assertEquals("b",stemp);
		itemp = objCounterAlphaS3.getValueCurrent();
		assertTrue("Fehler beim Erhöhen des Counters", itempold+1==itemp);
		
		//+++ Mit einem mehrstelligen String-Wert
		objCounterStrategyAlphaNumS.isLowercase(false);
		ICounterAlphanumericZZZ objCounterAlphaS4 = objCounterFactory.createCounter(objCounterStrategyAlphaNumS,"ZZA");
		itemp = objCounterAlphaS4.getValueCurrent();
		stemp = objCounterAlphaS4.current();
		assertEquals("ZZA",stemp);
		
		itempold = itemp;
		stemp = objCounterAlphaS4.next();
		assertEquals("ZZB",stemp);
		itemp = objCounterAlphaS4.getValueCurrent();
		assertTrue("Fehler beim Erhöhen des Counters", itempold+1==itemp);
		
		objCounterStrategyAlphaNumS.isLowercase(true);//Teste, ob die Änderung am Strategie-Objekt auch zu einer Änderung am Ergebnis führt.
		itempold = itemp;
		stemp = objCounterAlphaS4.next();
		assertEquals("zzc",stemp);
		itemp = objCounterAlphaS4.getValueCurrent();
		assertTrue("Fehler beim Erhöhen des Counters", itempold+1==itemp);
		
		
	} catch (ExceptionZZZ ez) {
		fail("Method throws an exception." + ez.getMessageLast());
	} 

}

public void testGetStringAlphanumericForNumber_FactoryBasedStrategySignificant(){

	try {	
		int itemp; int itempold; String stemp; String stempold; boolean btemp;
				
		//Damit die Counter per Factory erzeugt werden könnne: Konstruktoren in alle Counter einbauen (die lediglich static-Methoden reichen nicht)
		//#############################################
		//### A) Erzeugen per "Type"
		//#############################################
		ICounterStringZZZ objCounterString = objCounterFactory.createCounter(CounterByCharacterAsciiFactoryZZZ.iCounter_TYPE_ALPHANUMERIC_SIGNIFICANT);

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
		assertEquals("Fehler beim Setzen des Counters", "16", stemp);
		
		itempold = itemp;
		itemp = objCounterString.getValueCurrent();
		assertTrue("Fehler beim Erhöhen des Counters", itempold==itemp);
		

		//####################################
		//B) Mache Alphabet-Counter per Factory und Signifikant Strategy
		//####################################
		CounterStrategyAlphanumericSignificantZZZ objCounterStrategyAlphaNumSig = new CounterStrategyAlphanumericSignificantZZZ();
		ICounterAlphanumericZZZ objCounterAlphaS = objCounterFactory.createCounter(objCounterStrategyAlphaNumSig);
		
		///+++++++++++++++++++++++++++++++
		//Hole Initialwert
		itemp = objCounterAlphaS.getValueCurrent();
		stemp = objCounterAlphaS.current();
		assertEquals("1",stemp);
		
		itempold = itemp;
		stemp = objCounterAlphaS.next();
		assertEquals("2",stemp);
		itemp = objCounterAlphaS.getValueCurrent();
		assertTrue("Fehler beim Erhöhen des Counters", itempold+1==itemp);
		
		//+++++++++++++++++++++++++++++++++	
		//Setze den Counter durch String-Wert
		//...ungültige Zeichen
		try{
			stemp = "+";
			objCounterAlphaS.setValueCurrent(stemp);
			fail("Method should have thrown an exception for the string '"+stemp+"'");
		} catch (ExceptionZZZ ez) {
			//Erwartetete Exception
		} 
		
		//... ungültige significant  Syntax (vorne keine "0")
		try{
			stemp = "0ZA";
			objCounterAlphaS.setValueCurrent(stemp);
			fail("Method should have thrown an exception for the string '"+stemp+"'");
		} catch (ExceptionZZZ ez) {
			//Erwartetete Exception
		} 
		
		//... gültige significant Syntax
		stemp = "ZA";
		objCounterAlphaS.setValueCurrent(stemp);
		itemp = objCounterAlphaS.getValueCurrent();
		
		stempold = stemp;
		stemp = objCounterAlphaS.current();
		assertEquals(stemp, stempold);
			
		itempold = itemp;
		stemp = objCounterAlphaS.next();
		assertEquals("ZB",stemp);
		itemp = objCounterAlphaS.getValueCurrent();
		assertTrue("Fehler beim Erhöhen des Counters", itempold+1==itemp);
		
		
		
		//Erstelle einen Counter über den Konstruktor 
		//A) Mit int Wert
		ICounterAlphanumericZZZ objCounterAlphaS2 = objCounterFactory.createCounter(objCounterStrategyAlphaNumSig,10);
		itemp = objCounterAlphaS2.getValueCurrent();
		stemp = objCounterAlphaS2.current();
		assertEquals("A",stemp);//Merke: int Wert 10==>Ziffern 0 bis 9 => es wird "A" zurückgegeben.
		
		itempold = itemp;
		stemp = objCounterAlphaS2.next();
		assertEquals("B",stemp);
		itemp = objCounterAlphaS2.getValueCurrent();
		assertTrue("Fehler beim Erhöhen des Counters", itempold+1==itemp);
		
		objCounterStrategyAlphaNumSig.isLowercase(true);//Teste, ob die Änderung am Strategie-Objekt auch zu einer Änderung am Ergebnis führt.
		itempold = itemp;
		stemp = objCounterAlphaS2.next();
		assertEquals("c",stemp);
		itemp = objCounterAlphaS2.getValueCurrent();
		assertTrue("Fehler beim Erhöhen des Counters", itempold+1==itemp);
		
		//B) Mit String  Wert
		objCounterStrategyAlphaNumSig.isLowercase(false);
		ICounterAlphanumericZZZ objCounterAlphaS3 = objCounterFactory.createCounter(objCounterStrategyAlphaNumSig,"9");
		itemp = objCounterAlphaS3.getValueCurrent();
		stemp = objCounterAlphaS3.current();
		assertEquals("A",stemp);//Merke: int Wert 10==>Ziffern 0 bis 9 => es wird "A" zurückgegeben.
		
		itempold = itemp;
		stemp = objCounterAlphaS3.next();
		assertEquals("B",stemp);
		itemp = objCounterAlphaS3.getValueCurrent();
		assertTrue("Fehler beim Erhöhen des Counters", itempold+1==itemp);
		
		objCounterStrategyAlphaNumSig.isLowercase(true);//Teste, ob die Änderung am Strategie-Objekt auch zu einer Änderung am Ergebnis führt.
		itempold = itemp;
		stemp = objCounterAlphaS3.next();
		assertEquals("c",stemp);
		itemp = objCounterAlphaS3.getValueCurrent();
		assertTrue("Fehler beim Erhöhen des Counters", itempold+1==itemp);
		
		//+++ Mit einem mehrstelligen String-Wert
		objCounterStrategyAlphaNumSig.isLowercase(false);
		ICounterAlphanumericZZZ objCounterAlphaS4 = objCounterFactory.createCounter(objCounterStrategyAlphaNumSig,"ZZA");
		itemp = objCounterAlphaS4.getValueCurrent();
		stemp = objCounterAlphaS4.current();
		assertEquals("ZZA",stemp);
		
		itempold = itemp;
		stemp = objCounterAlphaS4.next();
		assertEquals("ZZB",stemp);
		itemp = objCounterAlphaS4.getValueCurrent();
		assertTrue("Fehler beim Erhöhen des Counters", itempold+1==itemp);
		
		objCounterStrategyAlphaNumSig.isLowercase(true);//Teste, ob die Änderung am Strategie-Objekt auch zu einer Änderung am Ergebnis führt.
		itempold = itemp;
		stemp = objCounterAlphaS4.next();
		assertEquals("zzc",stemp);
		itemp = objCounterAlphaS4.getValueCurrent();
		assertTrue("Fehler beim Erhöhen des Counters", itempold+1==itemp);
		
		
	} catch (ExceptionZZZ ez) {
		fail("Method throws an exception." + ez.getMessageLast());
	} 

}


private boolean assertCheckNullBordersNumeric_(int iInput, String sResult){
	boolean bReturn = false;
	main:{
		if(iInput< CounterByCharacterAscii_NumericZZZ.iPOSITION_MIN){
    		assertNull("Bei <'" + CounterByCharacterAscii_NumericZZZ.iPOSITION_MIN + "' wird NULL erwartet. Ergebnis '" + sResult + "' für " + iInput, sResult);
    	}else if(iInput>CounterByCharacterAscii_NumericZZZ.iPOSITION_MAX){
    		assertNull("Bei >'" + CounterByCharacterAscii_NumericZZZ.iPOSITION_MAX + "' wird NULL erwartet. Ergebnis '" + sResult + "' für " + iInput, sResult);
    	}
    		
		if(iInput>=CounterByCharacterAscii_NumericZZZ.iPOSITION_MIN && iInput <= CounterByCharacterAscii_NumericZZZ.iPOSITION_MAX){
    		assertNotNull("Bei >= '" + CounterByCharacterAscii_NumericZZZ.iPOSITION_MIN +"' und <='" + CounterByCharacterAscii_NumericZZZ.iPOSITION_MAX + "' wird keine NULL erwartet. Ergebnis '" + sResult + "' für " + iInput, sResult);
    	}	    		
		bReturn=true;
			    		    	
	}//end main:
	return bReturn;	    	
}
private boolean assertCheckNullBordersNumericStrategyBased_(int iInput, String sResult){
	boolean bReturn = false;
	main:{
		if(iInput< CounterByCharacterAscii_NumericZZZ.iPOSITION_MIN){
    		assertNull("Bei <'" + CounterByCharacterAscii_NumericZZZ.iPOSITION_MIN + "' wird NULL erwartet. Ergebnis '" + sResult + "' für " + iInput, sResult);
    	}
    		
		if(iInput>=CounterByCharacterAscii_NumericZZZ.iPOSITION_MIN ){
    		assertNotNull("Bei >= '" + CounterByCharacterAscii_NumericZZZ.iPOSITION_MIN  + "' wird keine NULL erwartet. Ergebnis '" + sResult + "' für " + iInput, sResult);
    	}	    		
		bReturn=true;
			    		    	
	}//end main:
	return bReturn;	    	
}

}//end class


