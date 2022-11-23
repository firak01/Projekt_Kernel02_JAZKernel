package basic.zBasic.util.crypt.encode;

import java.io.File;
import java.io.IOException;

import base.files.DateiUtil;
import base.files.EncodingMaintypeZZZ;
import base.files.EncodingMappedValueZZZ;
import base.io.IoUtil;
import basic.zBasic.datatype.string.FileEncodingUtil;
import basic.zBasic.util.datatype.string.UnicodeZZZ;

/** Aus "Kryptographie mit Java", Seite 31ff
 *  Die Klasse stammt aus der Buch CD, aus dem Verzeichnis poly
 *  Die Verschlusselungsmethode gehoert zu den "polyalphabetischen Ansaetzen"
 *  
 * Erweitert und angepasst.
 * Insbesondere Anpassung an 26 Zeichen, damit es zu den Zahlenwerten und den Verschluesselten Buchstabenwerten im Buchbeispiel auf der angegebenen Seite passt
 * Merke: Der verwendete Moduluswert entpricht der 26 im Klasennamen

 * @author Fritz Lindhauer, 08.10.2022, 08:29:08
 * 
 */
class Vigenere26 { 		// Vigenereverschluesselung

  public static void main( String[] arg) {
    String SchluesselWort="HALLO"; //FGL: passend zum Beispiel im Buch, S.31
	//String SchluesselWort="SchluesselWort"; //FGL: passend zum Beispiel im Buch, S.33

    System.out.println("Schluesselwort: " + SchluesselWort);	
    
    DateiUtil Original;
    int c, i, laengeSW;   

    //int[]iasPure = UnicodeZZZ.fromUtf8ToAscii(SchluesselWort);
    int[] s = IoUtil.Unicode(SchluesselWort.getBytes()); //Die Schlüsselwortbuchstaben
    int[] iasPure = s;
    
    if (arg.length > 0) {
    	Original = new DateiUtil(arg[0]);
    } else {
    	//Buchoriginal, S. 31
    	//Original = new DateiUtil("tryout\\basic\\zBasic\\util\\crypt\\encode\\file\\Beispieltext2_ohne_Sonderzeichen.txt");
    	
    	//Buchoriginal S. 33
    	Original = new DateiUtil("tryout\\basic\\zBasic\\util\\crypt\\encode\\file\\Langer_Beispieltext1_ohne_Sonderzeichen.txt");
    	
    	//Original = new DateiUtil("tryout\\basic\\zBasic\\util\\crypt\\encode\\file\\Langer_Beispieltext2_zur_Vigenere_Verschluesselung.txt");
    
    }
    
    if (arg.length > 1) {
    	SchluesselWort = (arg[1]); 
    }else {
    	//Nur weil das Schlüsselwort nicht angegeben wurde... ?    System.exit(0);
    }
    laengeSW = SchluesselWort.length();
    
    int[] p = Original.liesUnicode();//FGL: Der Klartextbuchstabe
    System.out.print("\nOriginaltext ausgeben? (J/N): ");
    if (IoUtil.JaNein()) {
      System.out.println("---- Originaltext von: "+Original.computeFilePath()+" ----");
      for (i=0; i < p.length; i++) {
        IoUtil.printChar(p[i]);	// druckbares Zeichen?
        if (((i+1)%80)==0) System.out.println();
      }
    }
    
    //int[]ppure = UnicodeZZZ.fromUtf8ToAscii(p);
    //int[]ppure = p;
    int[]ppure = new int[p.length];
    
    System.out.println("\n-- Verschluessele Text von: "+Original.computeFilePath()+" --");
    for (i = 0; i < p.length; i++) {
    	if(i>=1) System.out.print("|");
      //Das steht in der Codedatei
    	//Merke: c = Chiffrebuchstabe
    	int iModLaengeSW = i%laengeSW;
    	int iBezug = iasPure[iModLaengeSW];
    	int iSum = p[i]+iBezug;
    	   	
    	int iFormula = (iSum)%26;//An das Beispiel im Buch angepasst
    	//int iFormula = (iSum)%256;
    	c = iFormula; //FGL: 	Das ist der Mathematische Ansatz: 
      								//		Die Buchstaben wurden durch natuerliche Zahlen ersetzt.
                                    //		Dann fiel eine Gesetzmaessigkeit auf (s. Seite 32 im Buch), die so ausgenutzt wurde.
      System.out.print("i="+i+", c='"+c+"'");
      ppure[i] = c;				// nur wegen abspeichern
      
    }	
    
    ppure = UnicodeZZZ.fromAsciiToUtf8(ppure);//funktioniert bei %26
    //ppure = UnicodeZZZ.fromUtf8ToAscii(ppure);//funktioniert bei %26
    
    
    System.out.print("\nVerschluesselten Text ausgeben? (J/N): ");
    if (IoUtil.JaNein()) {
      System.out.println("\n\n-- Verschluesselter Text von: "+Original.computeFilePath()+" --");
      for (i = 0; i < ppure.length; i++) {
    	//IoUtil.printCharWithPosition((ppure[i]+65),"|");
    	IoUtil.printCharWithPosition((ppure[i]),"|");
        if (((i+1)%80)==0) System.out.println();	// neue Zeile
      }
    }
    System.out.println("\n---- Laenge: "+ppure.length+" Bytes ----");
    
    System.out.print("\nVerschluesselten Text als Datei speichern (ueber Dialog)? (J/N): ");
    if (IoUtil.JaNein()) {    	
    	DateiUtil Kodiert = new DateiUtil();
        //Kodiert.schreib(ppure, EncodingMaintypeZZZ.TypeZZZ.ASCII.ordinal());
    	Kodiert.schreib(ppure, EncodingMaintypeZZZ.TypeZZZ.UTF8.ordinal());
    }
    
    System.exit(0);
  }
}
