package basic.zBasic.util.crypt.encode;

import base.files.DateiUtil;
import base.files.EncodingMaintypeZZZ;
import base.io.IoUtil;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractList.ArrayListExtendedZZZ;
import basic.zBasic.util.crypt.VigenereNnZZZ;
import basic.zBasic.util.datatype.character.CharArrayZZZ;
import basic.zBasic.util.datatype.character.CharacterExtendedZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.datatype.string.UnicodeZZZ;
import basic.zKernel.flag.IFlagUserZZZ;

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
public class VigenereNnZZZmain { 		// Vigenereverschluesselung

  public static void main( String[] args) {
	  main:{
		try {
			boolean btemp; String stemp;
			
		//ohne UI
		String SchluesselWortDefault="HALLO"; //FGL: passend zum Beispiel im Buch, S.31	
//		Vigenere256ZZZ objVigenere = new Vigenere256ZZZ(SchluesselWortDefault);
//		stemp = objVigenere.encrypt("KRYPTOGRAFIE");
				
//		String SchluesselWortDefault="SchluesselWort"; //FGL: passend zum Beispiel im Buch, S.31 bzw. 34
		VigenereNnZZZ objVigenere = new VigenereNnZZZ(SchluesselWortDefault);
		objVigenere.setFlag(IVigenereNnZZZ.FLAGZ.USELOWERCASE, true);
		objVigenere.setFlag(IVigenereNnZZZ.FLAGZ.USEUPPERCASE, true);
		objVigenere.setFlag(IVigenereNnZZZ.FLAGZ.USENUMERIC, true);
		String sEncrypted = objVigenere.encrypt("Bei d");
		System.out.println("encrypted: " + sEncrypted);
						
		//1. Variante, mit dem int-Array weiterarbeiten
		int[]iaTest = objVigenere.getEncryptedValuesAsInt();		
		int[]iaDecrypted = objVigenere.decrypt(iaTest);
		ArrayListExtendedZZZ<CharacterExtendedZZZ> listasCharacterPool = objVigenere.getCharacterPoolList();
		stemp = CharacterExtendedZZZ.computeStringFromCharacterPoolPosition(iaDecrypted, listasCharacterPool);
		System.out.println("decrypted1: " + stemp);
		
		//2. Variante, mit dem encrypted-String weiterarbeiten
		String sDecrypted = objVigenere.decrypt(sEncrypted);
		System.out.println("decrypted2: " + sDecrypted);
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
		    	Original.select();
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
	    
	    IVigenereZZZ objVigenereUI = new VigenereNnZZZ(SchluesselWort,sFilePath);
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
