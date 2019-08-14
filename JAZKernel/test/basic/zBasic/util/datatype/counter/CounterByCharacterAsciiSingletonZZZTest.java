package basic.zBasic.util.datatype.counter;

import basic.zBasic.ExceptionZZZ;
import junit.framework.TestCase;

public class CounterByCharacterAsciiSingletonZZZTest   extends TestCase{	
    protected void setUp(){
      
	//try {			
	
    	//### Das spezielle Singleton Factory Testobjekct
//    	objCounterFactory = CounterByCharacterAsciiFactoryZZZ.getInstance();
    	
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
   
    
public void testUsingSingleton01(){

	try {	
			int itemp; int itempold; String stemp; String stempold; boolean btemp;
			
			CounterHandlerSingleton_AlphanumericSignificantZZZ objHandler = CounterHandlerSingleton_AlphanumericSignificantZZZ.getInstance();
			ICounterAlphanumericSignificantZZZ objCounter = objHandler.getCounterFor();
			int iValue = objCounter.getValueCurrent();
			assertTrue("Nach der Initialisierung soll der Wert 0 sein", iValue==0);
			
			String sValue = objCounter.getString();
			assertTrue("Nach der Initialisierung soll der Counter einen Defaultlänge von 4 haben", sValue.equals("0000"));
		
			//TEST: Nutze den Counter erneut in einer Unterfunktion.
			boolean bExecuted = reuseCounterSingleton();
			if(bExecuted){
				int iValueOld = iValue;
				
				sValue = objCounter.next();
				assertTrue("Nach der Initialisierung soll der Counter einen Defaultlänge von 4 haben", sValue.equals("0002"));

				iValue = objCounter.getValueCurrent();
				assertTrue("Der Zähler soll mehr als um 1 erhöht sein", iValue >= iValueOld +2);
				
			}
	
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		} 
	}

	private boolean reuseCounterSingleton(){
		boolean bReturn = false;
		main:{
			ICounterAlphanumericSignificantZZZ objCounter = null;
			try{
				CounterHandlerSingleton_AlphanumericSignificantZZZ objHandler = CounterHandlerSingleton_AlphanumericSignificantZZZ.getInstance();
				objCounter = objHandler.getCounterFor();
				String sValue = objCounter.current();
				assertNotNull("Nach der Initialisierung soll der Counterwert nicht NULL sein", sValue);
				assertTrue("Nach der Initialisierung soll der Counter einen Defaultlänge von 4 haben", sValue.length()==4);
				assertTrue("Nach der Initialisierung soll der Counter den Wert 0000 haben.", sValue.equals("0000"));
			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			} 
			
			try{
				String sValue = objCounter.next();
				assertNotNull("Nach der Erhöhung soll der Counterwert nicht NULL sein", sValue);
				assertTrue("Nach der Erhöhung soll der Counter einen Defaultlänge von 4 haben", sValue.length()==4);
				assertTrue("Nach der Erhöhung soll der Counter de Wert 0001 haben.", sValue.equals("0001"));
							
			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			} 
			bReturn = true;
		}
		return bReturn;
	}


}//end class


