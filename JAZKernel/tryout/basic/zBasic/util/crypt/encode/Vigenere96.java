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
class Vigenere96 { 		// Vigenereverschluesselung

  public static void main( String[] arg) {
    String SchluesselWort="HALLO"; //FGL: passend zum Beispiel im Buch
	//String SchluesselWort="SchluesselWort"; //FGL: passend zur Datei Vigenere.txt im poly - Verzeichnis der Begleit CD
	                                        
	System.out.println("Schluesselwort: " + SchluesselWort);
	int[] iaSchluesselwort = UnicodeZZZ.toIntArray(SchluesselWort);

	 //Auf Seite 34 steht... "wird auf space (Nr. 32) bezogen, 
//    for(int i=0; i < iaSchluesselwort.length; i++) {
//    	iaSchluesselwort[i]=iaSchluesselwort[i]-32;
//    }
	iaSchluesselwort = UnicodeZZZ.fromAsciiToUtf8For96(iaSchluesselwort);
	
    
    
    DateiUtil Original;
    int c,laengeSW;                  
    if (arg.length > 0) {
    	Original = new DateiUtil(arg[0]);
    } else {
    	Original = new DateiUtil("tryout\\basic\\zBasic\\util\\crypt\\encode\\file\\Langer_Beispieltext1_ohne_Sonderzeichen.txt");
    	//Original = new DateiUtil("tryout\\basic\\zBasic\\util\\crypt\\encode\\file\\Beispieltext2_ohne_Sonderzeichen.txt");
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
      for (int i=0; i < p.length; i++) {
        IoUtil.printChar(p[i]);	// druckbares Zeichen?
        if (((i+1)%80)==0) System.out.println();
      }
    }
       
    //Auf Seite 34 steht... "wird auf space (Nr. 32) bezogen, 
//    for(int i=0; i < p.length; i++) {
//    	p[i]=p[i]-32;
//    }
    p = UnicodeZZZ.fromUtf8ToAsciiFor96(p);
    
    System.out.println("\n-- Verschluessele Text von: "+Original.computeFilePath()+" --");
    int[]ppure = new int[p.length];
    for (int i = 0; i < p.length; i++) {
    	if(i>=1) System.out.print("|");
        //Das steht in der Codedatei
    	//Merke: c = Chiffrebuchstabe
    	int iIndexS = i%laengeSW;
    	int iSum = iaSchluesselwort[iIndexS]+p[i];
    	int iFormula = (iSum)%96;  //auf Seite 35 wird der Modulus 96 verwendet. Merke 32+96=128    	
    	c = iFormula; //FGL: 	Das ist der Mathematische Ansatz: 
      								//		Die Buchstaben wurden durch natuerliche Zahlen ersetzt.
                                    //		Dann fiel eine Gesetzmaessigkeit auf (s. Seite 32 im Buch), die so ausgenutzt wurde.
      
      //Gemaes Seite 35 noch 32 wieder draufaddieren, das ist das Leerzeichen "blank".
      //c = c +32;
      ppure[i] = c;				// nur wegen abspeichern  
      System.out.print("i="+i+", c='"+c+"'"); 
    }
    ppure = UnicodeZZZ.fromAsciiToUtf8For96(ppure);
    
    
    System.out.print("\nVerschluesselten Text ausgeben? (J/N): ");
    if (IoUtil.JaNein()) {
      System.out.println("\n\n-- Verschluesselter Text von: "+Original.computeFilePath()+" --");
      for (int i = 0; i < ppure.length; i++) {
    	//IoUtil.printCharWithPosition((ppure[i]+65),"|");
    	IoUtil.printCharWithPosition((ppure[i]),i,"|");
        if (((i+1)%80)==0) System.out.println();	// neue Zeile
      }
    }
    System.out.println("\n---- Laenge: "+ppure.length+" Bytes ----");

    System.out.print("\nVerschluesselten Text als Datei speichern (ueber Dialog)? (J/N): ");
    if (IoUtil.JaNein()) {
    	DateiUtil Kodiert = new DateiUtil();
        Kodiert.schreib(ppure, EncodingMaintypeZZZ.TypeZZZ.UTF8.ordinal());
    }
    
    System.exit(0);
  }
}
