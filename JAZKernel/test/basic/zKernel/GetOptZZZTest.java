package basic.zKernel;

import basic.zBasic.ExceptionZZZ;
import basic.zKernel.GetOptZZZ;
import junit.framework.TestCase;

public class GetOptZZZTest extends TestCase{
	private GetOptZZZ objOptTest;

	
	
	protected void setUp(){
		//try {
			//objKernel= new KernelZZZ("FGL", "01", "c:\\fglKernel\\KernelTest", "ZKernelConfigKernel_test.ini",null);
			
		/* damit wird logObject null und der test darf nicht weitergehen
			String[] a = {"init"};
			objKernelTest = new KernelZZZ("FGL", "01", "", "ZKernelConfigKernel_test.ini",a);		
			*/
			objOptTest = new GetOptZZZ();
			
			
		//} catch (ExceptionZZZ e) {
		//	fail("Method throws an exception." + e.getMessageLast());
		//}		
	}
	
	/**F�r den Pattern String gilt: 1 Zeichen, ggf. gefolgt von einem Doppelpunkt
	 * Pr�fe auf: 
	 * - doppelte Zeichen (au�er dem Doppelpunkt)
	 * - pr�fe auf zwei hintereinander folgende Doppelpunkte
	 * 
	* lindhauer; 30.06.2007 08:21:33
	 */
	public void testIsPatternStringValid(){
		try{
			String stemp=null;
			String sResult=null;
			int itemp= 0;
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++
			stemp = ":a:bc:;";//Mit einem Doppelpunkt startend
			sResult = objOptTest.proofPatternString(stemp);
			itemp = GetOptZZZ.computeCodeFromProofResult(sResult);
			assertEquals(3, itemp);
			
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++
			stemp = "aa:bc:";  //doppelt am Anfang
			sResult = objOptTest.proofPatternString(stemp);
			itemp = GetOptZZZ.computeCodeFromProofResult(sResult);
			assertEquals(1, itemp);
			
			stemp = "a:bc:dd"; //doppelt am Ende
			sResult = objOptTest.proofPatternString(stemp);
			itemp = GetOptZZZ.computeCodeFromProofResult(sResult);
			assertEquals(1, itemp);
			
			stemp = "a:bbc:d"; //doppelt in der Mitte
			sResult = objOptTest.proofPatternString(stemp);
			itemp = GetOptZZZ.computeCodeFromProofResult(sResult);
			assertEquals(1, itemp);
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++
			stemp = "a::bc:";  //doppelter Doppelpunkt in der Mitte
			sResult = objOptTest.proofPatternString(stemp);
			itemp = GetOptZZZ.computeCodeFromProofResult(sResult);
			assertEquals(2, itemp);
			
			stemp = "a:bc::";  //doppelter Doppelpunkt am Ende
			sResult = objOptTest.proofPatternString(stemp);
			itemp = GetOptZZZ.computeCodeFromProofResult(sResult);
			assertEquals(2, itemp);
			
			
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++
			stemp = "a:bc:d"; //o.k.
			sResult = objOptTest.proofPatternString(stemp);
			itemp = GetOptZZZ.computeCodeFromProofResult(sResult);
			assertEquals(-1, itemp);
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++
			
			
			
		} catch (ExceptionZZZ e) {
			fail("Method throws an exception." + e.getMessageLast());
		}	
	}
	
	public void testProofArgument(){
		try{
			String stemp=null;
			String sResult=null;
			int itemp= 0;
			
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++
			objOptTest.setPattern("a:bc:");
			stemp = "-a hallo -b -c Welt";
			sResult = objOptTest.proofArgument(stemp);
			itemp = GetOptZZZ.computeCodeFromProofResult(sResult);
			assertEquals(-1, itemp);
			
			stemp="-a hallo -b -c"; 
			sResult = objOptTest.proofArgument(stemp);
			itemp = GetOptZZZ.computeCodeFromProofResult(sResult);
			assertEquals(60, itemp);//'c' als Steuerungszeichen erkannt, aber nix folgt.
			
			
			stemp="-a hallo -b -c welt zwei"; //ist NICHT valid. 'zwei' wird als Argumentwert erkannt, der "�berfl�ssig" ist.
			sResult= objOptTest.proofArgument(stemp);
			itemp = GetOptZZZ.computeCodeFromProofResult(sResult);
			assertEquals(40, itemp); //Fehler: 'Der vorherige Eintrag ist kein Steuerzeichen'
			
			
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++
			objOptTest.setPattern("a:b:c:");
			stemp="-a hallo -b -c"; //ist auch valid 'c' wird in diesem Fall als Wert und nicht als Steuerungszeichen erkannt.
			sResult= objOptTest.proofArgument(stemp);
			itemp = GetOptZZZ.computeCodeFromProofResult(sResult);
			assertEquals(-1, itemp);
			
			stemp="-a hallo -b -cc";
			sResult = objOptTest.proofArgument(stemp);
			itemp = GetOptZZZ.computeCodeFromProofResult(sResult);
			assertEquals(-1, itemp);//Kein Fehler, weil -cc als Wert und nicht als Steuerzeichen erkannt wird, darum kein Fehler
			
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++
			objOptTest.setPattern("a:b:c:");
			stemp="-a hallo -b welt -c zwei"; //ist auch valid, Normalfall
			sResult = objOptTest.proofArgument(stemp);
			itemp = GetOptZZZ.computeCodeFromProofResult(sResult);
			assertEquals(-1, itemp);
			
			
			stemp="-a hallo -b welt -c zwei drei"; 
			sResult = objOptTest.proofArgument(stemp);
			itemp = GetOptZZZ.computeCodeFromProofResult(sResult);
			assertEquals(40, itemp); //Fehler: 'Der vorherige Eintrag ist kein Steuerzeichen'
			
			//+++++++++++++++++++++++++++++++++++++++++
			objOptTest.setPattern("a:bc:"); //Nun soll -cc falsch sein
			stemp="-a hallo -b -cc";  
			sResult = objOptTest.proofArgument(stemp);
			itemp = GetOptZZZ.computeCodeFromProofResult(sResult);
			assertEquals(20,itemp); //Fehler "Falsche L�nge des Steuerzeichens
			
			stemp="-a hallo -b -cc blabla";  
			sResult = objOptTest.proofArgument(stemp);
			itemp = GetOptZZZ.computeCodeFromProofResult(sResult);
			assertEquals(20, itemp);//Fehler: 'Falsche L�nge eines Steuerzeichens'
			
			stemp="-a hallo -b -d blabla";  
			sResult = objOptTest.proofArgument(stemp);
			itemp = GetOptZZZ.computeCodeFromProofResult(sResult);
			assertEquals(25, itemp);//Fehler: 'Steuerzeichen nicht im pattern string'
			
			//###################
			//Test mit nur einem Steuerzeichen
			objOptTest.setPattern("a"); //Nun soll -cc falsch sein
			stemp="-a";  
			sResult = objOptTest.proofArgument(stemp);
			itemp = GetOptZZZ.computeCodeFromProofResult(sResult);
			assertEquals(-1,itemp); 
			
			stemp="-b";  
			sResult = objOptTest.proofArgument(stemp);
			itemp = GetOptZZZ.computeCodeFromProofResult(sResult);
			assertEquals(25,itemp); //Steuerzeichen nicht im pattern string
			
			stemp = "-a welt";
			sResult = objOptTest.proofArgument(stemp);
			itemp = GetOptZZZ.computeCodeFromProofResult(sResult);
			assertEquals(40,itemp); //der vorherige Eintrag -a ist kein Steuerzeichen mit Argument
			
			
			objOptTest.setPattern("a:");
			stemp="-a";  
			sResult = objOptTest.proofArgument(stemp);
			itemp = GetOptZZZ.computeCodeFromProofResult(sResult);
			assertEquals(60,itemp); //Steuerzeichen verlang Argument, aber nix folgt
			
			stemp="-a welt";  
			sResult = objOptTest.proofArgument(stemp);
			itemp = GetOptZZZ.computeCodeFromProofResult(sResult);
			assertEquals(-1,itemp); //o.k.
			
			
			//Test ohne ein  Steuerzeichen
			try{
				objOptTest.setPattern("");
				stemp="-a";  
				sResult = objOptTest.proofArgument(stemp);
				fail("Method should throw an exception: No pattern string");
				
			}catch(ExceptionZZZ ez){
			}
			
			//Test ohne ein en wert
    		stemp="";  
	    	sResult = objOptTest.proofArgument(stemp);
	    	itemp = GetOptZZZ.computeCodeFromProofResult(sResult);
			assertEquals(-1,itemp); //o.k.	
			
			
			//### Test mit leerem Argument f�r ein Steuerzeichen (das geht nur als Array)
			objOptTest.setPattern("a:");
			String[] saArg={"-a",""};
			sResult = objOptTest.proofArgument(saArg);
			
			//Test mit mehreren hintereinanderfolgenden Werten
			objOptTest.setPattern("a:b:");
			String[] saArg2={"-a","", "-b", ""};
			sResult = objOptTest.proofArgument(saArg2);
			
			//Test mit gemischten Werten
			objOptTest.setPattern("a:b:c:");
			String[] saArg3={"-a","", "-b", "xyz", "-c", ""};
			sResult = objOptTest.proofArgument(saArg3);
			
		} catch (ExceptionZZZ e) {
			fail("Method throws an exception." + e.getMessageLast());
		}	
	}
	
	/** Pr�fe den Argument String:
	 * - hinter dem Pattern String muss immer etwas stehen (also beim Pattern String a:b:c: f�hr der Argument String "-a wert1 -b -c" dazu dass -c als Wert und nicht als Streuerungzeichen erkannt wird).
	 * - nicht jede option braucht da zu sein
	* - Optionen oder Stringsbestandteile, die nicht beachtet w�rden, bedeuten, dass der Argumentstring nicht valid ist:
	*    (also beim Pattern String a:b:c: f�hr der Argument String "-a wert1 -b -c wert2" zu dem Oben ganannten Problem
	*      dar�ber hinaus wird 'wert2' nicht mehr als Steuerungswert erkannt, weil -c als Wert und nicht als Streuerungzeichen erkannt wurde).
	*    
	* 
	* lindhauer; 30.06.2007 08:26:25
	 */
	public void testIsArgumentStringValid(){		
		try{
			String stemp=null;
			boolean btemp=false;
			
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++
			objOptTest.setPattern("a:bc:");
			stemp = "-a hallo -b -c Welt";
			btemp = objOptTest.isArgumentValid(stemp);
			assertTrue(btemp);
			
			stemp="-a hallo -b -c"; //ist auch valid 'c' als Steuerungszeichen 'mit Argument' erkannt, aber nix folgt.
			btemp = objOptTest.isArgumentValid(stemp);
			assertFalse(btemp);
			
			stemp="-a hallo -b -c welt zwei"; //ist NICHT valid. 'zwei' wird als Argumentwert erkannt, der "�berfl�ssig" ist.
			btemp = objOptTest.isArgumentValid(stemp);
			assertFalse(btemp);
			
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++
			objOptTest.setPattern("a:b:c:");
			stemp="-a hallo -b -c"; //ist auch valid 'c' wird in diesem Fall als Wert und nicht als Steuerungszeichen erkannt.
			btemp = objOptTest.isArgumentValid(stemp);
			assertTrue(btemp);
			
			stemp="-a hallo -b welt -c zwei"; //ist auch valid, Normalfall
			btemp = objOptTest.isArgumentValid(stemp);
			assertTrue(btemp);
			
			stemp="-a hallo -b welt -c zwei drei"; 
			btemp = objOptTest.isArgumentValid(stemp);
			assertFalse(btemp);
			
			//Falsche L�nge der Steuerungzeichen eingeben
			stemp="-a hallo -b -cc"; //ist auch valid 'c' wird in diesem Fall als Wert und nicht als Steuerungszeichen erkannt.
			btemp = objOptTest.isArgumentValid(stemp);
			assertTrue(btemp);
			
			
			//Merke: noch mehr tests in testProofArgumentString
			
		} catch (ExceptionZZZ e) {
			fail("Method throws an exception." + e.getMessageLast());
		}	
	}
	

	public void testLoadOptionAll(){
		try{
			
			//+++ Einfache Variante
			objOptTest.setPattern("a");
			String[] saTemp = {"-a"};
			boolean btemp = objOptTest.loadOptionAll(saTemp);
			assertTrue(btemp);
			
			String stemp = objOptTest.readOptionNext();
			assertEquals("a", stemp);
			
			
			
			//+++ Mehr Pattern und Pattern zur�cksetzen
			objOptTest.setPattern("bc");
			String[] saTemp2 ={"-c","-b"}; //Mal die Reihenfolge verglichen mit dem PAttern vertauschen
			btemp = objOptTest.loadOptionAll(saTemp2);
			assertTrue(btemp);
			
			stemp = objOptTest.readOptionNext();
			assertNotNull(stemp);
			assertFalse(stemp.equalsIgnoreCase("a"));
			
			String stemp2 = objOptTest.readOptionNext();
			assertNotNull(stemp2);
			assertTrue(stemp2.equalsIgnoreCase("b") | stemp2.equalsIgnoreCase("c"));
			
			String stemp3 = objOptTest.readOptionNext();
			assertNull("NULL erwartet. Wert ist aber '" + stemp3 + "'", stemp3);  //Das waere das 3. auslesen, bei nur 2 Parametern
			
			stemp3 = objOptTest.readOptionFirst(); //Wieder von vorne anfangen !!!
			assertNotNull(stemp3);
			assertTrue(stemp3.equalsIgnoreCase("b") | stemp3.equalsIgnoreCase("c"));
			
			
			stemp = objOptTest.readValue("a");  //+++ Werte auslesen
			assertNull("NULL erwartet. Wert ist aber '" + stemp + "'", stemp); //Key nicht vorhanden
			
			stemp = objOptTest.readValue("b");
			assertNull("NULL erwartet. Wert ist aber '" + stemp + "'", stemp);//Kein Wert fuer diesen Key gepflegt.
			
			//+++++++++++++++++ Pattern mit Keys, die Argumente beinhalten
			objOptTest.setPattern("wx:y:z");
			String[] saTemp3 ={"-y","werty", "-w", "-x", "wertx", "-z"}; //Mal die Reihenfolge verglichen mit dem Pattern vertauschen
			btemp = objOptTest.loadOptionAll(saTemp3);
			assertTrue(btemp);
			
			stemp = objOptTest.readValue("w");  //+++ Werte auslesen
			assertNull("NULL erwartet. Wert ist aber '" + stemp + "'", stemp); //Key nicht vorhanden
			stemp = objOptTest.readValue("z");  //+++ Werte auslesen
			assertNull("NULL erwartet. Wert ist aber '" + stemp + "'", stemp); //Key nicht vorhanden
			
			stemp = objOptTest.readValue("x");
			assertNotNull(stemp); 
			assertEquals("wertx", stemp);
			
			stemp = objOptTest.readValue("y");
			assertNotNull(stemp); 
			assertEquals("werty", stemp);
			
			
			
		} catch (ExceptionZZZ e) {
			fail("Method throws an exception." + e.getMessageLast());
		}	
	}
}
