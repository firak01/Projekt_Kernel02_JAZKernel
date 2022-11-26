package basic.zBasic.util.crypt.encode;

import base.files.DateiUtil;
import base.files.EncodingMaintypeZZZ;
import base.io.IoUtil;
import basic.zBasic.util.datatype.string.UnicodeZZZ;

/** Aus "Kryptographie mit Java", Seite 33f
 *  Die Klasse stammt aus der Buch CD, aus dem Verzeichnis poly
 *  Die Verschlusselungsmethode gehoert zu den "polyalphabetischen Ansaetzen"
 *  
 * Erweitert und angepasst
 * Insbesondere Anpassung an 26 Zeichen, damit es zu den Zahlenwerten und den Verschluesselten Buchstabenwerten im Buchbeispiel auf der angegebenen Seite passt
 * Merke: Der verwendete Moduluswert entspricht dem 128 im Klassennamen
 * @author Fritz Lindhauer, 08.10.2022, 08:29:08
 * 
 */
public class Vigenere26ZZZmain {

	public static void main(String[] args) {
		 String SchluesselWortDefault="HALLO"; //FGL: passend zum Beispiel im Buch, S.31
		 //String SchluesselWortDefault="SchluesselWort"; //FGL: passend zum Beispiel im Buch, S.33
		 
		 String sFilePathDefault;
		
		 //im Test:
		 sFilePathDefault = "tryout\\basic\\zBasic\\util\\crypt\\encode\\file\\Beispieltext2_ohne_Sonderzeichen.txt";
		 
		 //Klappt
	     //Buchoriginal, S. 31
	     //sFilePathDefault = "tryout\\basic\\zBasic\\util\\crypt\\encode\\file\\Beispieltext2_ohne_Sonderzeichen.txt";
	    		    	
	     //Klappt nicht
	     //Buchoriginal S. 33
	     //sFilePathDefault = "tryout\\basic\\zBasic\\util\\crypt\\encode\\file\\Langer_Beispieltext1_ohne_Sonderzeichen.txt");    	
	     //sFilePathDefault = "tryout\\basic\\zBasic\\util\\crypt\\encode\\file\\Langer_Beispieltext2_zur_Vigenere_Verschluesselung.txt");
	    
		    String sFilePath;
		    if (args.length > 0) {
		    	sFilePath = args[0];
		    } else {
		    	sFilePath = sFilePathDefault;		    	
		    }
		    
		    String SchluesselWort;
		    if (args.length > 1) {
		    	SchluesselWort = (args[1]); 
		    }else {
		    	SchluesselWort = SchluesselWortDefault;
		    }
		    
		    Vigenere26ZZZ objVigenere = new Vigenere26ZZZ(sFilePath, SchluesselWort);
		    boolean btemp = objVigenere.crypt();
		    if(btemp) {		    		    
		    System.out.print("\nVerschluesselten Text ausgeben? (J/N): ");
			    if (IoUtil.JaNein()) {
			      DateiUtil Original = objVigenere.getDateiOriginal();
			      System.out.println("\n\n-- Verschluesselter Text von: "+Original.computeFilePath()+" --");
			      int[]ppure = objVigenere.getCryptedValuesAsInt();
			      for (int i = 0; i < ppure.length; i++) {
			    	IoUtil.printCharWithPosition((ppure[i]),"|");
			        if (((i+1)%80)==0) System.out.println();	// neue Zeile
			      }
			    }
			    System.out.println("\n---- Laenge: "+objVigenere.getCryptedValuesAsInt().length+" Bytes ----");
			    
			    System.out.print("\nVerschluesselten Text als Datei speichern (ueber Dialog)? (J/N): ");
			    if (IoUtil.JaNein()) {    	
			    	DateiUtil Kodiert = new DateiUtil();
			        //Kodiert.schreib(ppure, EncodingMaintypeZZZ.TypeZZZ.ASCII.ordinal());
			    	Kodiert.schreib(objVigenere.getCryptedValuesAsInt(), EncodingMaintypeZZZ.TypeZZZ.UTF8.ordinal());
			    }
		    }		    
		    System.exit(0);
	}

}
