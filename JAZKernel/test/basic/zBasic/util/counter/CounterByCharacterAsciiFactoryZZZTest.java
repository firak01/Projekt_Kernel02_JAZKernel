package basic.zBasic.util.counter;

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
		ICounterAlphabetZZZ objCounterAlphaM = objCounterFactory.createCounter(objCounterStrategyAlphaM);
		
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

public void testGetStringAlphanumericForNumber_FactoryBasedStrategy_UsingDefault(){

	try {	
		int itemp; int itempold; String stemp; String stempold; boolean btemp;
		
		//Damit die Counter per Factory erzeugt werden könnne: Konstruktoren in alle Counter einbauen (die lediglich static-Methoden reichen nicht)
		
		//####################################
		//A) Mache Alphabet-Counter per Factory und nur der Typenangabe. 
		//    Herauskommen soll ein Counter mit Multiple Strategy als Default-Einstellung
		//####################################		
		//CounterStrategyAlphanumericMultipleZZZ objCounterStrategyAlphaNumM = new CounterStrategyAlphanumericMultipleZZZ();
		ICounterAlphanumericZZZ objCounterAlphaM = (ICounterAlphanumericZZZ) objCounterFactory.createCounter(ICounterByCharacterAsciiFactoryZZZ.iCOUNTER_TYPE_ALPHANUMERIC);
		
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
		
		
		
		//Arbeite mit dem Strategy-Objekt, das intern vorhanden ist.
		stempold = "AA";
		objCounterAlphaM.setValueCurrent(stempold);
		ICounterStrategyAlphanumericZZZ objCounterStrategyAlphaNumM = objCounterAlphaM.getCounterStrategyObject();
		objCounterStrategyAlphaNumM.isLowercase(true);
		stemp = objCounterAlphaM.getString();
		assertFalse("Fehler bei Manipulation des Counters über das Strategy Objekt", stempold.equals(stemp));
		assertEquals("aa",stemp);

		//Arbeite direkt mit dem Counter-Objekt, das über das Interface erstellt worden ist.
		stempold = stemp;
		objCounterAlphaM.isLowercase(false);
		stemp = objCounterAlphaM.getString();
		assertFalse("Fehler bei Manipulation des Counters über das Strategy Objekt", stempold.equals(stemp));
		assertEquals("AA",stemp);
		
		
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
		//### A) Erzeugen per "Type" und CASTE anschliessend.
		//#############################################
		ICounterStringZZZ objCounterStringTemp = objCounterFactory.createCounter(CounterByCharacterAsciiFactoryZZZ.iCounter_TYPE_ALPHANUMERIC_SIGNIFICANT);
		ICounterAlphanumericSignificantZZZ objCounterString = (ICounterAlphanumericSignificantZZZ) objCounterStringTemp;
		
		//Untenstehende Tests und Ergebnisse müssen auch mit der Factory und den daraus generierten Counter-Objekten erfüllbar sein.
	    //A) OHNE STRATEGY-OBJEKT ZU ÜBERGEBEN
		itemp = objCounterString.getValueCurrent();
		stemp = objCounterString.current();
		btemp = assertCheckNullBordersNumericStrategyBased_(itemp, stemp);
		assertTrue("Fehler beim Check auf Null Werte", btemp);
		assertEquals("Fehler beim Ermitteln des Default Stringwerts", "0001", stemp);	
		
		itempold = itemp;
		stemp = objCounterString.next();
		btemp = assertCheckNullBordersNumericStrategyBased_(itemp, stemp);
		assertTrue("Fehler beim Check auf Null Werte", btemp);
		itemp = objCounterString.getValueCurrent();
		assertTrue("Fehler beim Erhöhen des Counters", itempold+1==itemp);
		assertEquals("Fehler beim Ermitteln des Default nächsten Stringwerts", "0002", stemp);
		
		itemp = 42;
		objCounterString.setCounterLength(2);//!!!! Damit keine führende 0 vorhanden ist.
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
		ICounterAlphanumericSignificantZZZ<CounterStrategyAlphanumericSignificantZZZ> objCounterAlphaS = objCounterFactory.createCounter(objCounterStrategyAlphaNumSig);
		objCounterAlphaS.setCounterLength(5);//setze die Counterlänge fest. Dann kann man die erwartetetn Stringwerte vergleichen.
		///+++++++++++++++++++++++++++++++
		//Hole Initialwert
		itemp = objCounterAlphaS.getValueCurrent();
		stemp = objCounterAlphaS.current();		
		assertEquals("00001",stemp);
		
		itempold = itemp;
		stemp = objCounterAlphaS.next();
		assertEquals("00002",stemp);
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
		
		//+++++++++++++++++++++++++++++++++++	
		try{
			stemp = "0ZA";
			//... gültige significant Syntax (String beginnt zwar mit "0", wird aber von Rechts analysiert)
			objCounterAlphaS.isRightAligned(true);
			objCounterAlphaS.setValueCurrent(stemp);
			//NEIN, jetzt wird die führende 0 einfach weggetrimmt... also kein Fehler fail("Method should have thrown an exception for the string '"+stemp+"'");
			int itempRight = objCounterAlphaS.getValueCurrent();
			assertTrue(itempRight==14220);
			
			//Gegenprobe
			objCounterAlphaS.setValueCurrent(itempRight);
			String sCheck = objCounterAlphaS.getString(); 
			assertEquals(stemp+"00", sCheck); //Merke: oben wurde als Zählerlänge 5 eingestellt. Wenn der Zähler rechts-ausgelegt ist, kommen die Füllzeichen nach rechts.
			
			//Gegen-Gegenprobe
			objCounterAlphaS.setValueCurrent(sCheck);
			//NEIN, jetzt wird die führende 0 einfach weggetrimmt... also kein Fehler fail("Method should have thrown an exception for the string '"+stemp+"'");
			int itempCheckRight = objCounterAlphaS.getValueCurrent();
			assertTrue(itempCheckRight==itempRight);
			
			
			//... eigentlich ungültige significant Syntax (vorne keine "0" erlaubt), aber... die werden nun intern weggetrimmt.
			objCounterAlphaS.isRightAligned(false);
			objCounterAlphaS.setValueCurrent(stemp);
			int itempLeft = objCounterAlphaS.getValueCurrent();
			assertTrue(itempLeft==1270);

			//Gegenprobe
			objCounterAlphaS.setValueCurrent(itempLeft);
			sCheck = objCounterAlphaS.getString();
			assertEquals("00"+stemp, sCheck);//Merke: oben wurde als Zählerlänge 5 eingestellt. Wenn der Zähler links-ausgelegt ist, kommen die Füllzeichen nach links.
			
			//Gegen-Gegenprobe
			objCounterAlphaS.setValueCurrent(sCheck);
			//NEIN, jetzt wird die führende 0 einfach weggetrimmt... also kein Fehler fail("Method should have thrown an exception for the string '"+stemp+"'");
			int itempCheckLeft = objCounterAlphaS.getValueCurrent();
			assertTrue(itempCheckLeft==itempLeft);
			
			//+++++++++++++
			assertFalse("Bei diesem String '" + stemp + "' müssen links-/rechtsbündig andere Werte herauskommen.",itempRight==itempLeft);
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		} 
		
		//... setze die Zählerlänge auf 3 fest... Dann wird das gültig ..mit Zählerlänge lassen sich auch führende "0" en wiederherstellen.
		try{
			objCounterAlphaS.setCounterLength(3);
			
			stemp = "0ZA";
			objCounterAlphaS.setValueCurrent(stemp);
			itemp = objCounterAlphaS.getValueCurrent();
			
			stempold = stemp;
			stemp = objCounterAlphaS.current();
			assertEquals(stemp, stempold);
				
			itempold = itemp;
			stemp = objCounterAlphaS.next();
			assertEquals("0ZB",stemp); //...mit Zählerlänge lassen sich auch führende "0" en wiederherstellen.
			itemp = objCounterAlphaS.getValueCurrent();
			assertTrue("Fehler beim Erhöhen des Counters", itempold+1==itemp);
			
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		} 
		
		//... aber das bleibt ungültig, wenn Zählerlänge 3 und rechstbündig, d.h. nur rechts dürften beliebig viele Füllzeichen sein.
		try{
			stemp = "00ZA";
			objCounterAlphaS.isRightAligned(true);
			objCounterAlphaS.setValueCurrent(stemp);
			fail("Method should have thrown an exception for the string '"+stemp+"' with counterlength smaller and rightaligned.");
		} catch (ExceptionZZZ ez) {
			//Erwartetete Exception
		} 
		
		//... und das wird nun im einfachen Vergleich ungültig
		objCounterAlphaS.isRightAligned(false);
		stemp = "ZA";
		objCounterAlphaS.setValueCurrent(stemp);
		itemp = objCounterAlphaS.getValueCurrent();
		
		stempold = stemp;
		stemp = objCounterAlphaS.current();
		assertFalse("Wegen des Füllzeichens dürfen die Strings nicht gleich sein. '(" + stemp + "')('"+stempold +"')", stemp.equals(stempold));
			
		itempold = itemp;
		stemp = objCounterAlphaS.next();
		assertEquals("0ZB",stemp);
		itemp = objCounterAlphaS.getValueCurrent();
		assertTrue("Fehler beim Erhöhen des Counters", itempold+1==itemp);
		
		//###########################################
		//... gültige significant Syntax
		objCounterAlphaS.setCounterLength(0); //dann wird wieder der DEFAULT Wert genommen.
				
		stemp = "ZA";
		stempold = stemp;
		objCounterAlphaS.setValueCurrent(stemp);
		itemp = objCounterAlphaS.getValueCurrent();
		itempold = itemp;
		
		stemp = objCounterAlphaS.current();
		assertEquals("00"+stempold, stemp);//wg. Defaultlänge von 4 und Couner ist linksbündig: Dann kommen passende Füllzeichen links.
		
		stemp = objCounterAlphaS.next();
		assertEquals("00ZB",stemp);
		itemp = objCounterAlphaS.getValueCurrent();
		assertTrue("Fehler beim Erhöhen des Counters", itempold+1==itemp);
		
		
		
		//Erstelle einen Counter über den Konstruktor 
		//A) Mit int Wert
		ICounterAlphanumericSignificantZZZ<?> objCounterAlphaS2 = objCounterFactory.createCounter(objCounterStrategyAlphaNumSig,10);
		itemp = objCounterAlphaS2.getValueCurrent();
		stemp = objCounterAlphaS2.current();
		assertEquals("000A",stemp);//Merke: int Wert 10==>Ziffern 0 bis 9 => es wird "A" zurückgegeben.
		
		itempold = itemp;
		stemp = objCounterAlphaS2.next();
		assertEquals("000B",stemp);
		itemp = objCounterAlphaS2.getValueCurrent();
		assertTrue("Fehler beim Erhöhen des Counters", itempold+1==itemp);
		
		objCounterAlphaS2.isLowercase(true);//Teste, ob die Änderung am Strategie-Objekt auch zu einer Änderung am Ergebnis führt.
		itempold = itemp;
		stemp = objCounterAlphaS2.next();
		assertEquals("000c",stemp);
		itemp = objCounterAlphaS2.getValueCurrent();
		assertTrue("Fehler beim Erhöhen des Counters", itempold+1==itemp);
		
		//B) Mit String  Wert
		objCounterStrategyAlphaNumSig.isLowercase(false);
		ICounterAlphanumericSignificantZZZ<?> objCounterAlphaS3 = objCounterFactory.createCounter(objCounterStrategyAlphaNumSig,"9");
		itemp = objCounterAlphaS3.getValueCurrent();
		stemp = objCounterAlphaS3.current();
		assertEquals("0009",stemp);//Merke: int Wert 10==>Ziffern 0 bis 9 => es wird "A" zurückgegeben.
		
		itempold = itemp;
		stemp = objCounterAlphaS3.next();
		assertEquals("000A",stemp);
		itemp = objCounterAlphaS3.getValueCurrent();
		assertTrue("Fehler beim Erhöhen des Counters", itempold+1==itemp);
		
		objCounterStrategyAlphaNumSig.isLowercase(true);//Teste, ob die Änderung am Strategie-Objekt auch zu einer Änderung am Ergebnis führt.
		itempold = itemp;
		stemp = objCounterAlphaS3.next();
		assertEquals("000b",stemp);
		itemp = objCounterAlphaS3.getValueCurrent();
		assertTrue("Fehler beim Erhöhen des Counters", itempold+1==itemp);
		
		//+++ Mit einem mehrstelligen String-Wert
		objCounterAlphaS3.isLowercase(false);
		ICounterAlphanumericSignificantZZZ objCounterAlphaS4 = objCounterFactory.createCounter(objCounterStrategyAlphaNumSig,"ZZA");
		itemp = objCounterAlphaS4.getValueCurrent();
		stemp = objCounterAlphaS4.current();
		assertEquals("0ZZA",stemp);
		
		itempold = itemp;
		stemp = objCounterAlphaS4.next();
		assertEquals("0ZZB",stemp);
		itemp = objCounterAlphaS4.getValueCurrent();
		assertTrue("Fehler beim Erhöhen des Counters", itempold+1==itemp);
		
		objCounterStrategyAlphaNumSig.isLowercase(true);//Teste, ob die Änderung am Strategie-Objekt auch zu einer Änderung am Ergebnis führt.
		itempold = itemp;
		stemp = objCounterAlphaS4.next();
		assertEquals("0zzc",stemp);
		itemp = objCounterAlphaS4.getValueCurrent();
		assertTrue("Fehler beim Erhöhen des Counters", itempold+1==itemp);
		
		//Überabeitetet am 20190724: OHNE FÜLLWERT-ZEICHEN MUSS ES AUCH GEHEN
		itempold = itemp;
		objCounterAlphaS4.setCounterFilling((Character) null);				
		stemp = objCounterAlphaS4.next();
		assertEquals("zzd",stemp);
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


