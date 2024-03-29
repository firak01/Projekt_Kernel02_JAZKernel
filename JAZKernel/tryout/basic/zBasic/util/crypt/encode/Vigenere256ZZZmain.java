package basic.zBasic.util.crypt.encode;

import base.files.DateiUtil;
import base.files.EncodingMaintypeZZZ;
import base.io.IoUtil;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.crypt.code.IVigenereUiZZZ;
import basic.zBasic.util.crypt.code.IVigenereZZZ;
import basic.zBasic.util.crypt.code.Vigenere256UiZZZ;
import basic.zBasic.util.crypt.code.Vigenere256ZZZ;
import basic.zBasic.util.datatype.character.CharArrayZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.datatype.string.UnicodeZZZ;

/** Aus "Kryptographie mit Java", Seite 33f
 *  Die Klasse stammt aus der Buch CD, aus dem Verzeichnis poly
 *  Die Verschlusselungsmethode gehoert zu den "polyalphabetischen Ansaetzen"
 *  
 * Erweitert und angepasst
 * Insbesondere Anpassung an 256 Zeichen, damit es zu den Zahlenwerten und den Verschluesselten Buchstabenwerten im Buchbeispiel auf der angegebenen Seite passt
 * Merke: Der verwendete Moduluswert entspricht der 256 im Klassennamen
 * @author Fritz Lindhauer, 08.10.2022, 08:29:08
 * 
 */
public class Vigenere256ZZZmain { 		// Vigenereverschluesselung

  public static void main( String[] args) {
	  main:{
		try {
			boolean btemp; String stemp;
			
		//ohne UI
		String SchluesselWortDefault="HALLO"; //FGL: passend zum Beispiel im Buch, S.31	
//		Vigenere256ZZZ objVigenere = new Vigenere256ZZZ(SchluesselWortDefault);
//		stemp = objVigenere.encrypt("KRYPTOGRAFIE");
				
//		String SchluesselWortDefault="SchluesselWort"; //FGL: passend zum Beispiel im Buch, S.31 bzw. 34
		Vigenere256ZZZ objVigenere = new Vigenere256ZZZ(SchluesselWortDefault);
		stemp = objVigenere.encrypt("Bei d");
		
		//TESTS
//		int[]iaTest = UnicodeZZZ.fromUtf8ToAscii(stemp);
//		stemp = CharArrayZZZ.toString(iaTest);
		
	
		System.out.println("encrypted: " + stemp);
		
		//PROBLEM: WENN MAN MIT DEM STRING WEITERARBEITET
		int[]iaTest = objVigenere.getEncryptedValuesAsInt();
		stemp = CharArrayZZZ.toString(iaTest);
		System.out.println("encrypted: " + stemp);
		
		int[]iaErg = objVigenere.decrypt(iaTest);
		stemp = CharArrayZZZ.toString(iaErg);
		//stemp = objVigenere.decrypt(stemp);//DAS KLAPPT BEI 256 ZEICHEN NICHT!!!
		System.out.println("decrypted: " + stemp);
		System.out.println("##################################################");
		
		
		
		 //mit UI	
	//Buchbeispiel Seite 34
	String SchluesselWortUIDefault="SchluesselWort"; //FGL: passend zur Datei Vigenere.txt im poly - Verzeichnis der Begleit CD	
	String sFilePathDefault = "tryout\\basic\\zBasic\\util\\crypt\\encode\\file\\Langer_Beispieltext1_ohne_Sonderzeichen.txt"; 
   
	//im Test, ggfs abweichend vom Buch
	//A) Schluesselwort
	//SchluesselWortDefault="HALLO"; //FGL: passend zum Beispiel im Buch
	SchluesselWortUIDefault="SchluesselWort"; //FGL: passend zur Datei Vigenere.txt im poly - Verzeichnis der Begleit CD
			 
	//B) Datei;
	sFilePathDefault = "tryout\\basic\\zBasic\\util\\crypt\\encode\\file\\Beispieltext2_ohne_Sonderzeichen.txt";   	
    //sFilePathDefault = "tryout\\basic\\zBasic\\util\\crypt\\encode\\file\\Langer_Beispieltext1_ohne_Sonderzeichen.txt";    	
    //sFilePathDefault = "tryout\\basic\\zBasic\\util\\crypt\\encode\\file\\Langer_Beispieltext2_zur_Vigenere_Verschluesselung.txt";
 
            
	//Klappt
    //sFilePathDefault = "tryout\\basic\\zBasic\\util\\crypt\\encode\\file\\Langer_Beispieltext1_ohne_Sonderzeichen.txt";
    //sFilePathDefault = "tryout\\basic\\zBasic\\util\\crypt\\encode\\file\\Beispieltext2_ohne_Sonderzeichen.txt";
   		    	
    //Klappt nicht
    //Buchoriginal S. 33
//...   
    
	 	String sFilePath;
	    if (args.length > 0) {
	    	sFilePath = args[0];
	    } else {
	    	System.out.print("\nZu verschluesselnde Datei auswaehlen (per Dialog)? (J/N): ");
	    	if (IoUtil.JaNein()) {	
		    	DateiUtil Original = new DateiUtil();
		    	Original.selectLoad();
		    	sFilePath = Original.computeFilePath();
		    	if(StringZZZ.isEmpty(sFilePath)) {
		    		break main;
		    	}	
		    }else {
		    	sFilePath = sFilePathDefault;
		    }			    	
	    }
	    
	    String SchluesselWort;
	    if (args.length > 1) {
	    	SchluesselWort = (args[1]); 
	    }else {
	    	SchluesselWort = SchluesselWortUIDefault;
	    }
	    
	    IVigenereUiZZZ objVigenereUI = new Vigenere256UiZZZ(SchluesselWort,sFilePath);
	    btemp = objVigenereUI.encryptUI();
		
	    if(btemp) {		    		    
	    System.out.print("\nVerschluesselten Text ausgeben? (J/N): ");
		    if (IoUtil.JaNein()) {
		      DateiUtil Original = objVigenereUI.getFileOriginal();
		      System.out.println("\n\n-- Verschluesselter Text von: "+Original.computeFilePath()+" --");
		      int[]ppure = objVigenereUI.getEncryptedValuesAsInt();
		      for (int i = 0; i < ppure.length; i++) {
		    	IoUtil.printCharWithPosition((ppure[i]),"|");
		        if (((i+1)%80)==0) System.out.println();	// neue Zeile
		      }
		    }
		    System.out.println("\n---- Laenge: "+objVigenereUI.getEncryptedValuesAsInt().length+" Bytes ----");
		    
		    System.out.print("\nVerschluesselten Text als Datei speichern (ueber Dialog)? (J/N): ");
		    if (IoUtil.JaNein()) {    	
		    	DateiUtil Kodiert = new DateiUtil();
		        //Kodiert.schreib(ppure, EncodingMaintypeZZZ.TypeZZZ.ASCII.ordinal());
		    	Kodiert.schreib(objVigenereUI.getEncryptedValuesAsInt(), EncodingMaintypeZZZ.TypeZZZ.UTF8.ordinal());
		    }
	    }		
	} catch (ExceptionZZZ e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
  }//end main:
    System.exit(0);
  }
}
