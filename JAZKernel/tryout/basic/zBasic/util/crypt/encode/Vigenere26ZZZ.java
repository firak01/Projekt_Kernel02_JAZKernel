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
class Vigenere26ZZZ extends AbstractVigenereZZZ{ 	

  public Vigenere26ZZZ() {	  
	  super();
  }
  public Vigenere26ZZZ(String sFilePath, String sSchluesselWort) {
	  super(sFilePath, sSchluesselWort);
  }
	
  public boolean crypt() {
	  boolean bReturn = false;
	  main:{
    String SchluesselWort=this.getSchluesselWort();
    
    System.out.println("Schluesselwort: " + SchluesselWort);
    int laengeSW = SchluesselWort.length();    
    int[] iaSchluesselwort = UnicodeZZZ.toIntArray(SchluesselWort);

    //Analog zu Vigenere96.java, nur mit 65
    //Auf Seite 34 steht... "wird auf space (Nr. 32) bezogen, 
//    for(int i=0; i < iaSchluesselwort.length; i++) {
//    	iaSchluesselwort[i]=iaSchluesselwort[i]-65;
//    }
     iaSchluesselwort=UnicodeZZZ.fromUtf8ToAscii(iaSchluesselwort);
    
    
    String sFilePath = this.getFilePath();
    DateiUtil Original = new DateiUtil(sFilePath);
    this.setDateiOriginal(Original);
    
    int[] p = Original.liesUnicode();//FGL: Der Klartextbuchstabe
    System.out.print("\nOriginaltext ausgeben? (J/N): ");
    if (IoUtil.JaNein()) {
      System.out.println("---- Originaltext von: "+Original.computeFilePath()+" ----");
      for (int i=0; i < p.length; i++) {
        IoUtil.printChar(p[i]);	// druckbares Zeichen?
        if (((i+1)%80)==0) System.out.println();
      }
    }
    
  //Analog zu Vigenere96.java, nur mit 65
  //Auf Seite 34 steht... "wird auf space (Nr. 32) bezogen, 
	//    for(int i=0; i < p.length; i++) {
	//    	p[i]=p[i]-65;
	//    }
    p = UnicodeZZZ.fromUtf8ToAsciiFor26(p);
          
    System.out.println("\n-- Verschluessele Text von: "+Original.computeFilePath()+" --");
    int[]ppure = new int[p.length];
    for (int i = 0; i < p.length; i++) {
    	if(i>=1) System.out.print("|");
        //Das steht in der Codedatei
    	//Merke: c = Chiffrebuchstabe
    	int iModLaengeSW = i%laengeSW;
    	int iBezug = iaSchluesselwort[iModLaengeSW];
    	int iSum = p[i]+iBezug;
    	   	
    	int iFormula = (iSum)%26;//An das Beispiel im Buch angepasst
    	int c = iFormula; //FGL: 	Das ist der Mathematische Ansatz: 
      								//		Die Buchstaben wurden durch natuerliche Zahlen ersetzt.
                                    //		Dann fiel eine Gesetzmaessigkeit auf (s. Seite 32 im Buch), die so ausgenutzt wurde.
    	
        //Gemaess Seite 35, analog zu Vigenere96 noch 65 wieder draufaddieren
        //c = c +65;
      ppure[i] = c;				// nur wegen abspeichern  	
      System.out.print("i="+i+", c='"+c+"'");      
    }	    
    ppure = UnicodeZZZ.fromAsciiToUtf8For26(ppure);
    
    System.out.print("\nVerschluesselten Text ausgeben? (J/N): ");
    if (IoUtil.JaNein()) {
      System.out.println("\n\n-- Verschluesselter Text von: "+Original.computeFilePath()+" --");
      for (int i = 0; i < ppure.length; i++) {
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
    
    	bReturn = true;
	}//end main:
	return bReturn;    
  }
}
