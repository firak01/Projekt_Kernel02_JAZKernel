package basic.zBasic.util.counter;

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

public class CounterByCharacterAscii_AlphabetSignificantZZZTest  extends TestCase{
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
	    
	   
	    
	    private boolean assertCheckNullBordersAlphabetStrategyBasedSignificant_(String sInput, int iResult){
	    	boolean bReturn = false;
	    	main:{
	    		//Ermittle den "Teiler" und den Rest, Also Modulo - Operation
				int iDiv = Math.abs(iResult / CounterByCharacterAscii_AlphabetZZZ.iPOSITION_MAX ); //durch abs wird also intern in ein Integer umgewandetl.... nicht nur das Weglassen des ggfs. negativen Vorzeichens.
				int iMod = iResult % CounterByCharacterAscii_AlphabetZZZ.iPOSITION_MAX;
	    				
				if(iDiv==0 && iMod < (CounterByCharacterAscii_AlphabetZZZ.iPOSITION_MIN-1)){ //-1, weil es keine 0 im reinen Alphabet gibt.
		    		assertEquals("Bei <'" + CounterByCharacterAscii_AlphabetZZZ.iPOSITION_MIN + "' wird NULL erwartet. Ergebnis '" + iResult + "' für " + sInput, sInput);
		    	}
		    		
	    		if(iMod>=(CounterByCharacterAscii_AlphabetZZZ.iPOSITION_MIN-1)){ //-1, weil es keine 0 im reinen Alphabet gibt.
		    		assertNotNull("Bei >= '" + CounterByCharacterAscii_AlphabetZZZ.iPOSITION_MIN + "' wird keine NULL erwartet. Ergebnis '" + iResult + "' für " + sInput, sInput);
		    	}	    		
	    		bReturn=true;
	    			    		    	
	    	}//end main:
	    	return bReturn;	    	
	    }
	    
	   
	    
 public void testGetNumberForStringAlphabet_StrategySignificant(){
	    	String stemp; int itemp; int itempLeft; int itempRight; int itempCheck; boolean btemp;
	    	ICounterStrategyAlphabetSignificantZZZ objCounterStrategy = null;
	    	try{
	    		objCounterStrategy = new CounterStrategyAlphabetSignificantZZZ();
	    	} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			} 
	    	
	    	//Ungültige Werte
	    	try {
				stemp = "?"; //Parameter false="Significant Strategy"
				itemp = CounterByCharacterAscii_AlphabetZZZ.getNumberForString(stemp,objCounterStrategy);
				fail("Method should have thrown an exception for the string '"+stemp+"'");
				
			} catch (ExceptionZZZ ez) {
				//Erwartetete Exception
			} 
	    	
	    	try {
				stemp = "0"; //Parameter false="Significant Strategy"
				itemp = CounterByCharacterAscii_AlphabetZZZ.getNumberForString(stemp,objCounterStrategy);
				fail("Method should have thrown an exception for the string '"+stemp+"'");
				
			} catch (ExceptionZZZ ez) {
				//Erwartetete Exception
			} 
	    	
	    	
	    	//Ungültige Werte, für die verschiedenen Strategien
	    	try {
		    	stemp = "aaA"; //Groß-/Kleinschreibung nicht mischen
		    	itemp = CounterByCharacterAscii_AlphabetZZZ.getNumberForString(stemp,objCounterStrategy);//Parameter true="Serielle Strategy"
		    	fail("Method should have thrown an exception for the string '"+stemp+"'");
						    	
			} catch (ExceptionZZZ ez) {
				//Erwartetete Exception
			} 
	    	
	    	//Merke: Bei der Signifikant-Strategie ist die Positon der Zeichen zueinander hinsichtlich der Gültigkeit egal....
	    	//Gehe daher sofort zu den "Berechnungstests":
	    	
	    	
	    	//"SIGNIFICANT STRATEGIE"-Ergebnisse	    		    	
	    	try {
				stemp = "A"; 
				itemp = CounterByCharacterAscii_AlphabetZZZ.getNumberForString(stemp,objCounterStrategy);
				btemp = assertCheckNullBordersAlphabetStrategyBasedSignificant_(stemp, itemp);
		    	assertTrue("Fehler beim Check auf Null Werte", btemp);
		    	assertEquals("Erwarteter Wert ", 0,itemp); //Merke: Hier wird der Stellenwert berechnet. Und der ist um -1 kleiner als derASCII-Zeichenwert von "0"=1

		    	String sCheck = CounterByCharacterAscii_AlphabetZZZ.getStringForNumber(itemp, objCounterStrategy);
		    	assertEquals("Gegenprobe wurde erfolgreich erwartet.", stemp, sCheck);		    			    	
		    	
			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			} 
	    	
	    	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	    	try {
				stemp = "Z";
				itemp = CounterByCharacterAscii_AlphabetZZZ.getNumberForString(stemp,objCounterStrategy);
				btemp = assertCheckNullBordersAlphabetStrategyBasedSignificant_(stemp, itemp);
		    	assertTrue("Fehler beim Check auf Null Werte", btemp);
		    	assertEquals("Erwarteter Wert ", 25,itemp);
		    	
		    	//Mache die Gegenprobe
		    	String sCheck = CounterByCharacterAscii_AlphabetZZZ.getStringForNumber(itemp, objCounterStrategy);
		    	assertEquals("Gegenprobe wurde erfolgreich erwartet.", stemp, sCheck);
	    	} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			} 
//		    			    	
	    	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	    	try {				
				stemp = "a";
				
				//++++++++
				objCounterStrategy.isLowercase(true);
		    	objCounterStrategy.isRightAligned(false);
				itempLeft = CounterByCharacterAscii_AlphabetZZZ.getNumberForString(stemp,objCounterStrategy); //linksbündig 73		    	
				btemp = assertCheckNullBordersAlphabetStrategyBasedSignificant_(stemp, itempLeft);
		    	assertTrue("Fehler beim Check auf Null Werte", btemp);
		    	assertEquals("Erwarteter Wert ", 0,itempLeft); //Merke: Hier wird der Stellenwert berechnet. Und der ist um -1 kleiner als derASCII-Zeichnewert von "0"=1
		    	
		    	//Mache die Gegenprobe
		    	String sCheckLeft = CounterByCharacterAscii_AlphabetZZZ.getStringForNumber(itempLeft, objCounterStrategy);
		    	assertEquals("Gegenprobe wurde erfolgreich erwartet.", stemp, sCheckLeft);
		    	
		    	
		    	//++++++++
		    	objCounterStrategy.isRightAligned(true);
		    	itempRight = CounterByCharacterAscii_AlphabetZZZ.getNumberForString(stemp,objCounterStrategy); //rechtsbündig 73
				btemp = assertCheckNullBordersAlphabetStrategyBasedSignificant_(stemp, itempRight);
		    	assertTrue("Fehler beim Check auf Null Werte", btemp);	
		    	assertEquals("Erwarteter Wert ", 0,itempRight); //Merke: Hier wird der Stellenwert berechnet. Und der ist um -1 kleiner als derASCII-Zeichnewert von "0"=1
		    	
		    	//Mache die Gegenprobe
		    	String sCheckRight = CounterByCharacterAscii_AlphabetZZZ.getStringForNumber(itempRight, objCounterStrategy);
		    	assertEquals("Gegenprobe wurde erfolgreich erwartet.", stemp, sCheckRight);
		    	
		    	//++++++++++++
		    	//Gesamt
		    	assertEquals("Links- oder Rechtsbündig soll bei 'a' (also gleichen Zeichen) den gleichen Zahlenwert haben", itempLeft, itempRight);		   		    
			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			} 
	    	
	    	//++++++++++++++++++++++++++++++
//		    	try {				
//					stemp = "aa";
//					objCounterStrategy.isLowercase(true);
//			    	objCounterStrategy.isRightAligned(false);
//					itempLeft = CounterByCharacterAscii_AlphabetZZZ.getNumberForString(stemp,objCounterStrategy); //linksbündig 73		    
//					fail("Methode sollte eine Exception geworfen haben: Führende 0 ist nicht erlaubt, da sich der String aus dem Wert nicht wiederherstellen lässt.");
//		    	}catch (ExceptionZZZ ez){
//		    		//Erwarteter Fehler
//		    	}
//			    		
//			    	//Mache die Gegenprobe
//		    	try {				
//					stemp = "00";
//			    	objCounterStrategy.isRightAligned(true);
//					itempLeft = CounterByCharacterAscii_AlphanumericZZZ.getNumberForString(stemp,objCounterStrategy); //linksbündig 73		    
//					fail("Methode sollte eine Exception geworfen haben: Führende 0 ist nicht erlaubt, da sich der String aus dem Wert nicht wiederherstellen lässt.");
//		    	}catch (ExceptionZZZ ez){
//		    		//Erwarteter Fehler
//		    	}
		    	
		    	//###################################
		    	//### Tests mit der "private" Funktion, die alles kann....
		    	//###################################
		    	try {				
		    		objCounterStrategy.isLowercase(false);
					stemp = "AA";
					btemp = getNumberForStringAlphabet_StrategySignificantTest_(objCounterStrategy,stemp);
					if(!btemp){
						ExceptionZZZ ez = new ExceptionZZZ("AlphabetCounter: Fehler beim String '" + stemp + "'", KernelZZZ.iERROR_RUNTIME, CounterByCharacterAscii_AlphabetSignificantZZZTest.class, ReflectCodeZZZ.getMethodCurrentName());
						throw ez;						
					}
				} catch (ExceptionZZZ ez) {
					fail("Method throws an exception." + ez.getMessageLast());
				} 
		    	
		    	
		    	try {				
					stemp = "AAA";
					btemp = getNumberForStringAlphabet_StrategySignificantTest_(objCounterStrategy,stemp);
					if(!btemp){
						ExceptionZZZ ez = new ExceptionZZZ("AlphanumericCounter: Fehler beim String '" + stemp + "'", KernelZZZ.iERROR_RUNTIME, CounterByCharacterAscii_AlphabetSignificantZZZTest.class, ReflectCodeZZZ.getMethodCurrentName());
						throw ez;
					}
				} catch (ExceptionZZZ ez) {
					fail("Method throws an exception." + ez.getMessageLast());
				} 
		    	
		    	try {				
					stemp = "ABC";
					btemp = getNumberForStringAlphabet_StrategySignificantTest_(objCounterStrategy,stemp);
					if(!btemp){
						ExceptionZZZ ez = new ExceptionZZZ("AlphanumericCounter: Fehler beim String '" + stemp + "'", KernelZZZ.iERROR_RUNTIME, CounterByCharacterAscii_AlphabetSignificantZZZTest.class, ReflectCodeZZZ.getMethodCurrentName());
						throw ez;
					}			    				    	
				} catch (ExceptionZZZ ez) {
					fail("Method throws an exception." + ez.getMessageLast());
				} 
		    	
//		    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
//			try {		
//				objCounterStrategy.isRightAligned(false);
//				stemp = "0Z";
//				itempLeft = CounterByCharacterAscii_AlphabetZZZ.getNumberForString(stemp,objCounterStrategy); //linksbündig Fehler    
//				fail("Methode sollte eine Exception geworfen haben: Führende 0 ist nicht erlaubt, da sich der String aus dem Wert nicht wiederherstellen lässt.");
//	    	}catch (ExceptionZZZ ez){
//	    		//Erwarteter Fehler
//	    	}

//			try {				
//				objCounterStrategy.isRightAligned(true);
//				stemp = "0Z";
//				itempRight = CounterByCharacterAscii_AlphanumericZZZ.getNumberForString(stemp,objCounterStrategy); //rechtsbündig 73		    
//				assertEquals("Erwarteter Wert ", 1260,itempRight); //Merke: Hier wird der Stellenwert berechnet. Und der ist um -1 kleiner als derASCII-Zeichnewert von "0"=1
//				
//				//+++ Gegenprobe
//				String stempRight = CounterByCharacterAscii_AlphanumericZZZ.getStringForNumber(itempRight, objCounterStrategy);
//				assertEquals("Erwarteter Wert ", stemp,stempRight);
//				
//			} catch (ExceptionZZZ ez) {
//				fail("Method throws an exception." + ez.getMessageLast());
//			} 	
		    	
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			try{
					stemp = "AZ";
					btemp = getNumberForStringAlphabet_StrategySignificantTest_(objCounterStrategy,stemp);
					if(!btemp){
						ExceptionZZZ ez = new ExceptionZZZ("AlphabetCounter: Fehler beim String '" + stemp + "'", KernelZZZ.iERROR_RUNTIME, CounterByCharacterAscii_AlphabetSignificantZZZTest.class, ReflectCodeZZZ.getMethodCurrentName());
						throw ez;
					}
				} catch (ExceptionZZZ ez) {
					fail("Method throws an exception." + ez.getMessageLast());
				} 	
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			try{
					stemp = "AZXB";
					btemp = getNumberForStringAlphabet_StrategySignificantTest_(objCounterStrategy,stemp);
					if(!btemp){
						ExceptionZZZ ez = new ExceptionZZZ("AlphabetCounter: Fehler beim String '" + stemp + "'", KernelZZZ.iERROR_RUNTIME, CounterByCharacterAscii_AlphabetSignificantZZZTest.class, ReflectCodeZZZ.getMethodCurrentName());
						throw ez;
					}
					
					stemp = "BZXA";
					btemp = getNumberForStringAlphabet_StrategySignificantTest_(objCounterStrategy,stemp);
					if(!btemp){
						ExceptionZZZ ez = new ExceptionZZZ("AlphabetCounter: Fehler beim String '" + stemp + "'", KernelZZZ.iERROR_RUNTIME, CounterByCharacterAscii_AlphabetSignificantZZZTest.class, ReflectCodeZZZ.getMethodCurrentName());
						throw ez;
					}
				} catch (ExceptionZZZ ez) {
					fail("Method throws an exception." + ez.getMessageLast());
				}				    						
	    }
 
	 private boolean getNumberForStringAlphabet_StrategySignificantTest_(ICounterStrategyAlphabetZZZ objCounterStrategy, String sAlphabet){
			boolean bReturn = false;
			main:{
				try{
					String stemp; String sCheckLeft; String sCheckRight; int itemp; int itempLeft; int itempRight; int itempRightCheck;  int itempLeftCheck; boolean btemp;													
					String sAlphabetNormed = null; String sAlphabetReversedNormed = null;
										
					//##################################################
			    	//### Den String testen
			    	//##################################################	
					objCounterStrategy.isRightAligned(false);					
					sAlphabetNormed = getAlphabetNormed_(objCounterStrategy, sAlphabet);				
					itempLeft = CounterByCharacterAscii_AlphabetZZZ.getNumberForString(sAlphabetNormed,objCounterStrategy); 
					itempLeftCheck = itempLeft; //Variable speichern für die Umkehrung des Strings
					btemp = assertCheckNullBordersAlphabetStrategyBasedSignificant_(sAlphabetNormed, itempLeft);
			    	assertTrue("Fehler beim Check auf Null Werte", btemp);
			    	
			    	//Mache die Gegenprobe
			    	sCheckLeft = CounterByCharacterAscii_AlphabetZZZ.getStringForNumber(itempLeft, objCounterStrategy);			    	
			    	assertEquals("Gegenprobe wurde erfolgreich erwartet.", sAlphabetNormed, sCheckLeft);	
			    	
			    	//+++++++++++++++++++++++++++++++++++
			    	objCounterStrategy.isRightAligned(true);			    	
			    	sAlphabetNormed = getAlphabetNormed_(objCounterStrategy, sAlphabet);	
			    	itempRight = CounterByCharacterAscii_AlphabetZZZ.getNumberForString(sAlphabetNormed,objCounterStrategy); 
			    	itempRightCheck = itempRight; //Variable speichern für die Umkehrung des Strings
					btemp = assertCheckNullBordersAlphabetStrategyBasedSignificant_(sAlphabetNormed, itempRight);
			    	assertTrue("Fehler beim Check auf Null Werte", btemp);		
			    				    	
			    	//Mache die Gegenprobe
			    	sCheckRight = CounterByCharacterAscii_AlphabetZZZ.getStringForNumber(itempRight, objCounterStrategy);
			    	assertEquals("Gegenprobe wurde erfolgreich erwartet.", sAlphabetNormed, sCheckRight);	
			    	
			    	
			    	//+++++++++++++++++++++++++++++++++++
			    	if(!StringZZZ.isPalindrom(sAlphabetNormed)){
			    		assertFalse("Links- oder Rechtsbündig darf NICHT den gleichen Zahlenwert haben, außer bei 'Palindrom' wie z.B. AA. String ist '" + sAlphabetNormed + "'.", itempLeft==itempRight);
			    	}else{
			    		assertTrue("Links- oder Rechtsbündig MUSS den gleichen Zahlenwert haben, da es ein 'Palindrom' wie z.B. AA. String ist '" + sAlphabetNormed + "'.", itempLeft==itempRight);
			    	}
			    	
			    	
			    	//##################################################
			    	//### Den String umdrehen und erneut testen
			    	//##################################################			    	
			    	String sAlphabetReversed = StringZZZ.reverse(sAlphabet);
			    	
			    	//+++++++++++++++++++++++++++++++++++
			    	objCounterStrategy.isRightAligned(false);
			    	sAlphabetReversedNormed = getAlphabetNormed_(objCounterStrategy, sAlphabetReversed);					    
			    	itempLeft = CounterByCharacterAscii_AlphabetZZZ.getNumberForString(sAlphabetReversedNormed,objCounterStrategy); 
			    	btemp = assertCheckNullBordersAlphabetStrategyBasedSignificant_(sAlphabetReversedNormed, itempLeft);
			    	assertTrue("Fehler beim Check auf Null Werte", btemp);
			    	assertEquals("Umgedrehter String soll den gleichen Zahlenwert haben wie andersherum und andersbündig orientiert.  '" + sAlphabetReversedNormed +"'", itempLeft,itempRightCheck);

			    	//Mache die Gegenprobe
			    	sCheckLeft = CounterByCharacterAscii_AlphabetZZZ.getStringForNumber(itempLeft, objCounterStrategy);
			    	assertEquals("Gegenprobe wurde erfolgreich erwartet.", sAlphabetReversedNormed, sCheckLeft);		    			    				    	    					    	    	
		    	
			    	//+++++++++++++++++++++++++++++++++++
			    	objCounterStrategy.isRightAligned(true);
			    	sAlphabetReversedNormed = getAlphabetNormed_(objCounterStrategy, sAlphabetReversed);	
			    	itempRight = CounterByCharacterAscii_AlphabetZZZ.getNumberForString(sAlphabetReversedNormed,objCounterStrategy); 
					btemp = assertCheckNullBordersAlphabetStrategyBasedSignificant_(sAlphabetReversedNormed, itempRight);
			    	assertTrue("Fehler beim Check auf Null Werte", btemp);	
			    	assertEquals("Umgedrehter String soll den gleichen Zahlenwert haben wie anders-bündig orientiert.  '" + sAlphabetReversedNormed +"'", itempLeft,itempRightCheck);
			    	
			    	//Mache die Gegenprobe
			    	sCheckRight = CounterByCharacterAscii_AlphabetZZZ.getStringForNumber(itempRight, objCounterStrategy);
			    	assertEquals("Gegenprobe wurde erfolgreich erwartet.", sAlphabetReversedNormed, sCheckRight);
			    	
			    	//+++++++++++++++++++++++++++++++++++
			    	if(!StringZZZ.isPalindrom(sAlphabetReversedNormed)){
			    		assertFalse("Links- oder Rechtsbündig darf NICHT den gleichen Zahlenwert haben, außer bei 'Palindrom' wie z.B. AA. String ist '" + sAlphabetReversedNormed + "'.", itempLeft==itempRight);
			    	}else{
			    		assertTrue("Links- oder Rechtsbündig MUSS den gleichen Zahlenwert haben, da es ein 'Palindrom' wie z.B. AA. String ist '" + sAlphabetReversedNormed + "'.", itempLeft==itempRight);
			    	}
			    			
			    	bReturn = true;
				} catch (ExceptionZZZ ez) {
					fail("Method throws an exception." + ez.getMessageLast());
				} 				    	    								
			}//end main:
			return bReturn;
		}
	 
	 /** TODO GOON IcounterStrategyAlphanumericSignificant als neues Interface und diese Methode darin einbauen.
	 * @param objCounterStrategy
	 * @param sAlphanumeric
	 * @return
	 * @author Fritz Lindhauer, 30.05.2019, 13:33:20
	 */
	private String getAlphabetNormed_(ICounterStrategyAlphabetZZZ objCounterStrategy, String sAlphabet){
		 String sReturn = null;
		 main:{
			 boolean bLowercase = objCounterStrategy.isLowercase();
		 	String sCharToStrip = CounterByCharacterAscii_AlphanumericZZZ.getCharForPosition(CounterByCharacterAscii_AlphanumericZZZ.iPOSITION_MIN, bLowercase);
			if(!objCounterStrategy.isRightAligned()){
				sReturn = StringZZZ.stripLeft(sAlphabet, sCharToStrip); //Merke: Führende "0" Werte können nicht wiederhergestelllt werden, aus dem Zahlenwert.
			}else{
				sReturn = StringZZZ.stripRight(sAlphabet, sCharToStrip); //Merke: Führende "0" Werte können nicht wiederhergestelllt werden, aus dem Zahlenwert.
			}
		 }//end main:
		 return sReturn;
	 }




}//end class
	

