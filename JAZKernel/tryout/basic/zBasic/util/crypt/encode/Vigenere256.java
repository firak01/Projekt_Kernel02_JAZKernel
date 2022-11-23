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
 * Insbesondere Anpassung an 128 Zeichen, damit es zu den Zahlenwerten und den Verschluesselten Buchstabenwerten im Buchbeispiel auf der angegebenen Seite passt
 * Merke: Der verwendete Moduluswert entspricht dem 128 im Klassennamen
 * @author Fritz Lindhauer, 08.10.2022, 08:29:08
 * 
 */
class Vigenere256 { 		// Vigenereverschluesselung

  public static void main( String[] arg) {
    //String SchluesselWort="HALLO"; //FGL: passend zum Beispiel im Buch
	String SchluesselWort="SchluesselWort"; //FGL: passend zur Datei Vigenere.txt im poly - Verzeichnis der Begleit CD
	                                        //     ABER: TODOGOON20221118 DAS ERGEBNIS WEICHT AB!!!
	System.out.println("Schluesselwort: " + SchluesselWort);
	
    DateiUtil Original;
    int c, i, laengeSW;   
   
    int[] s = IoUtil.Unicode(SchluesselWort.getBytes()); //Die Schlüsselwortbuchstaben
    int[] iasPure = s;
    
    
    if (arg.length > 0) {
    	Original = new DateiUtil(arg[0]);
    } else {
    	//Original = new DateiUtil("tryout\\basic\\zBasic\\util\\crypt\\encode\\file\\Beispieltext2_ohne_Sonderzeichen.txt");
    	//Original = new DateiUtil("tryout\\basic\\zBasic\\util\\crypt\\encode\\file\\Langer_Beispieltext2_zur_Vigenere_Verschluesselung.txt");
    	Original = new DateiUtil("tryout\\basic\\zBasic\\util\\crypt\\encode\\file\\Langer_Beispieltext1_ohne_Sonderzeichen.txt");
    }
    
    if (arg.length > 1) {
    	SchluesselWort = (arg[1]); 
    }else {
    	//Nur weil das Schlüsselwort nicht angegeben wurde... ?    System.exit(0);
    }
    laengeSW = SchluesselWort.length();
    
    int[] p = Original.liesUnicode();//FGL: Der Klartextbuchstabe
    System.out.print("Originaltext ausgeben? (J/N): ");
    if (IoUtil.JaNein()) {
      System.out.println("---- Originaltext von: "+Original.computeFilePath()+" ----");
      for (i=0; i < p.length; i++) {
        IoUtil.printChar(p[i]);	// druckbares Zeichen?
    	//IoUtil.printCharWithPosition(p[i], i, "|");
        if (((i+1)%80)==0) System.out.println();
      }
    }
    
    //FGL: Nun die Zeichen an die printversion im Buch anpassen   
   // int[]ppure = UnicodeZZZ.fromUtf8ToAscii(SchluesselWort);
    
    System.out.println("\n-- Verschluessele Text von: "+Original.computeFilePath()+" --");
    for (i = 0; i < p.length; i++) {
    	if(i>=1) System.out.print("|");
      //Das steht in der Codedatei
    	//Merke: c = Chiffrebuchstabe
    	int iIndexS = i%laengeSW;
    	int iSum = iasPure[iIndexS]+p[i];
    	//int iSum = spure[iIndexS]+ppure[i];
    	int iFormula = (iSum)%256;
    	//int iFormula = (iSum)%128;
    	//int iFormula = (iSum)%26;//An das Beispiel im Buch angepasst
      c = iFormula; //FGL: 	Das ist der Mathematische Ansatz: 
      								//		Die Buchstaben wurden durch natuerliche Zahlen ersetzt.
                                    //		Dann fiel eine Gesetzmaessigkeit auf (s. Seite 32 im Buch), die so ausgenutzt wurde.
      //++++FGL: Fehlerkorrektur, damit es dem Beispiel im Buch Seite 33ff entspricht
      c = c-128;
      if(c<0) c=c+128;
      //++++
      //System.out.print("i="+c);    
      p[i] = c;				// nur wegen abspeichern
      IoUtil.printCharWithPosition(p[i], i, "|");      
    }	
    System.out.print("Verschluesselten Text ausgeben? (J/N): ");
    if (IoUtil.JaNein()) {
      System.out.println("\n\n-- Verschluesselter Text von: "+Original.computeFilePath()+" --");
      for (i = 0; i < p.length; i++) {
    	//IoUtil.printCharWithPosition((ppure[i]+65),"|");
    	IoUtil.printCharWithPosition((p[i]),i,"|");
        //if (((i+1)%80)==0) System.out.println();	// neue Zeile
      }
    }
    System.out.println("\n---- Laenge: "+p.length+" Bytes ----");
    
    int[]ppure = p;
    System.out.print("\nVerschluesselten Text als Datei speichern? (J/N): ");
    if (IoUtil.JaNein()) {
    	DateiUtil Kodiert = new DateiUtil();
        Kodiert.schreib(ppure, EncodingMaintypeZZZ.TypeZZZ.ASCII.ordinal());
    }
    
    System.exit(0);
  }
}
