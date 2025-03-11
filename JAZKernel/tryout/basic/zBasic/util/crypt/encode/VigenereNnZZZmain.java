package basic.zBasic.util.crypt.encode;

import base.files.DateiUtil;
import base.files.EncodingMaintypeZZZ;
import base.io.IoUtil;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractList.ArrayListExtendedZZZ;
import basic.zBasic.util.crypt.code.ICharacterPoolEnabledZZZ;
import basic.zBasic.util.crypt.code.IROTUserZZZ;
import basic.zBasic.util.crypt.code.IVigenereNnUiZZZ;
import basic.zBasic.util.crypt.code.IVigenereNnZZZ;
import basic.zBasic.util.crypt.code.VigenereNnUiZZZ;
import basic.zBasic.util.crypt.code.VigenereNnZZZ;
import basic.zBasic.util.datatype.character.CharArrayZZZ;
import basic.zBasic.util.datatype.character.CharacterExtendedZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.datatype.string.UnicodeZZZ;
import basic.zKernel.flag.IFlagZEnabledZZZ;

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
		String sText = "Bei d";
		debug(sText,SchluesselWortDefault);
		
		
		SchluesselWortDefault="SchluesselWort"; //FGL: passend zum Beispiel im Buch, S.31 bzw. 34
		sText = "KRYPTOGRAFIE";
		debug(sText,SchluesselWortDefault);
		
		
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

	    IVigenereNnUiZZZ objVigenereUI = new VigenereNnUiZZZ(SchluesselWort,sFilePath);	    	    
		objVigenereUI.setFlag(ICharacterPoolEnabledZZZ.FLAGZ.USEUPPERCASE,true);
		objVigenereUI.setFlag(ICharacterPoolEnabledZZZ.FLAGZ.USELOWERCASE,true);		
		objVigenereUI.setFlag(ICharacterPoolEnabledZZZ.FLAGZ.USENUMERIC,true);		
		objVigenereUI.setFlag(IROTUserZZZ.FLAGZ.USEBLANK,true);
		
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
		        //Kodiert.schreib(objVigenereUI.getEncryptedValuesAsInt(), EncodingMaintypeZZZ.TypeZZZ.ASCII.ordinal());
		    	Kodiert.schreib(objVigenereUI.getEncryptedValuesAsInt(), EncodingMaintypeZZZ.TypeZZZ.UTF8.ordinal());
		    	//Kodiert.schreibUsingPoolPosition(objVigenereUI.getEncryptedCharacterPoolPosition(), EncodingMaintypeZZZ.TypeZZZ.UTF8.ordinal(), objVigenereUI.getCharacterPoolList());
		    }
		    
		    int[] iaDecrypted2 = objVigenereUI.decrypt(objVigenereUI.getEncryptedValuesAsInt());
		    for (int i = 0; i < iaDecrypted2.length; i++) {
		    	IoUtil.printCharWithPosition((iaDecrypted2[i]),"|");
		        if (((i+1)%80)==0) System.out.println();	// neue Zeile
		      }
	    }		
	} catch (ExceptionZZZ e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
  }//end main:
    System.exit(0);
  }
  
  public static boolean debug(String sText, String sSchluesselwort) throws ExceptionZZZ {
	  boolean bReturn = false;
	  main:{
	  
		//ohne UI
		VigenereNnZZZ objVigenere = new VigenereNnZZZ(sSchluesselwort);
		objVigenere.setFlag(ICharacterPoolEnabledZZZ.FLAGZ.USELOWERCASE, true);
		objVigenere.setFlag(ICharacterPoolEnabledZZZ.FLAGZ.USEUPPERCASE, true);
		objVigenere.setFlag(ICharacterPoolEnabledZZZ.FLAGZ.USENUMERIC, true);
		objVigenere.setFlag(IROTUserZZZ.FLAGZ.USEBLANK, true);
		String sEncrypted = objVigenere.encrypt(sText);
		System.out.println("encrypted: " + sEncrypted);
						
		//1. Variante, mit dem int-Array weiterarbeiten
		int[]iaTest = objVigenere.getEncryptedValuesAsInt();		
		int[]iaDecrypted = objVigenere.decrypt(iaTest);
		String stemp = CharArrayZZZ.toString(iaDecrypted);
		System.out.println("decrypted1: " + stemp);
		
		//2. Variante, mit dem int-Array der Position weiterarbeiten
		int[]iaDecryptedCharacterPositionInPool = objVigenere.getDecryptedCharacterPoolPosition();
		ArrayListExtendedZZZ<CharacterExtendedZZZ> listasCharacterPool = objVigenere.getCharacterPoolList();
		stemp = CharacterExtendedZZZ.computeStringFromCharacterPoolPosition(iaDecryptedCharacterPositionInPool, listasCharacterPool);
		System.out.println("decrypted2: " + stemp);
		
		//3. Variante, mit dem encrypted-String weiterarbeiten
		String sDecrypted = objVigenere.decrypt(sEncrypted);
		System.out.println("decrypted3: " + sDecrypted);
		System.out.println("##################################################");
		
		bReturn = true;
	  }//end main
	  return bReturn;
  }
  
}
