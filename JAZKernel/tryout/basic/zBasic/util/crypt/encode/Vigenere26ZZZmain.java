package basic.zBasic.util.crypt.encode;

import base.files.DateiUtil;
import base.files.EncodingMaintypeZZZ;
import base.io.IoUtil;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
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
		main:{
		try {
		 //Buchbeispiel Seite 31
		 String SchluesselWortDefault="HALLO"; //FGL: passend zum Beispiel im Buch, S.31
		 String sFilePathDefault = "tryout\\basic\\zBasic\\util\\crypt\\encode\\file\\Beispieltext2_ohne_Sonderzeichen.txt";
		 
		//im Test, ggfs abweichend vom Buch
		//A) Schluesselwort
		//SchluesselWortDefault="HALLO"; //FGL: passend zum Beispiel im Buch
		//SchluesselWortDefault="SchluesselWort"; //FGL: passend zur Datei Vigenere.txt im poly - Verzeichnis der Begleit CD
					 
		
		//B) Datei;
		//sFilePathDefault = "tryout\\basic\\zBasic\\util\\crypt\\encode\\file\\Beispieltext2_ohne_Sonderzeichen.txt";
		 
		 //Klappt
	     //sFilePathDefault = "tryout\\basic\\zBasic\\util\\crypt\\encode\\file\\Beispieltext2_ohne_Sonderzeichen.txt";
	    		    	
	     //Klappt nicht
	     //Buchoriginal S. 33
	     //sFilePathDefault = "tryout\\basic\\zBasic\\util\\crypt\\encode\\file\\Langer_Beispieltext1_ohne_Sonderzeichen.txt");    	
	     //sFilePathDefault = "tryout\\basic\\zBasic\\util\\crypt\\encode\\file\\Langer_Beispieltext2_zur_Vigenere_Verschluesselung.txt");
	    
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
		    	SchluesselWort = SchluesselWortDefault;
		    }
		    
		    Vigenere26ZZZ objVigenere = new Vigenere26ZZZ(sFilePath, SchluesselWort);
		    boolean btemp = objVigenere.encryptUI();
		    if(btemp) {		    		    
		    System.out.print("\nVerschluesselten Text ausgeben? (J/N): ");
			    if (IoUtil.JaNein()) {
			      DateiUtil Original = objVigenere.getFileOriginal();
			      System.out.println("\n\n-- Verschluesselter Text von: "+Original.computeFilePath()+" --");
			      int[]ppure = objVigenere.getEncryptedValuesAsInt();
			      for (int i = 0; i < ppure.length; i++) {
			    	IoUtil.printCharWithPosition((ppure[i]),"|");
			        if (((i+1)%80)==0) System.out.println();	// neue Zeile
			      }
			    }
			    System.out.println("\n---- Laenge: "+objVigenere.getEncryptedValuesAsInt().length+" Bytes ----");
			    
			    System.out.print("\nVerschluesselten Text als Datei speichern (ueber Dialog)? (J/N): ");
			    if (IoUtil.JaNein()) {    	
			    	DateiUtil Kodiert = new DateiUtil();
			        //Kodiert.schreib(ppure, EncodingMaintypeZZZ.TypeZZZ.ASCII.ordinal());
			    	Kodiert.schreib(objVigenere.getEncryptedValuesAsInt(), EncodingMaintypeZZZ.TypeZZZ.UTF8.ordinal());
			    }
		    }		   
		} catch (ExceptionZZZ e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}//end main
		System.exit(0);
	}

}
