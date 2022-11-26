package basic.zBasic.util.crypt.decrypt;

import base.files.DateiUtil;
import base.files.EncodingMaintypeZZZ;
import base.io.IoUtil;
import basic.zBasic.util.crypt.encode.Vigenere26ZZZ;
import basic.zBasic.util.crypt.encode.Vigenere96ZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.datatype.string.UnicodeZZZ;
import basic.zBasic.util.file.FileEasyZZZ;

class Vig_Decode26ZZZmain { 	// Vigenereentschluesselung mit bekanntem Schluesselwort!
  public static void main( String[] args) {
	  //String SchluesselWortDefault="HALLO"; //FGL: passend zum Beispiel im Buch
		String SchluesselWortDefault="SchluesselWort"; //FGL: passend zur Datei Vigenere.txt im poly - Verzeichnis der Begleit CD
		
	    String sFilePathDefault;
	    sFilePathDefault = "tryout\\basic\\zBasic\\util\\crypt\\decrypt\\file\\VigenereCrypted_26_Beispieltext2_schluesselwort_HALLO.txt";    
    	
    	//Klappt, das Ergebnis der Datei passt zum Text im Buch.  
	    //sFilePathDefault = "tryout\\basic\\zBasic\\util\\crypt\\decrypt\\file\\VigenereCrypted_26_Beispieltext2_schluesselwort_HALLO.txt";    	  
	    //sFilePathDefault = "tryout\\basic\\zBasic\\util\\crypt\\decrypt\\file\\VigenereCrypted_26_Beispieltext2_schluesselwort_SchluesselWort.txt";
    	
    	
    	//Klappt nicht: Ergebnis der Datei passt nicht zum Text im Buch. Der Algorithmus weicht halt leicht ab mir dem Bezugszeichen und dem Modulus=Anzahl der Zeichen in der Zeichnmenge   	
	    //sFilePathDefault = "tryout\\basic\\zBasic\\util\\crypt\\decrypt\\file\\VigenereCrypted_96_LangerBeispieltext1_schluesselwort_SchluesselWort.txt";
	    //sFilePathDefault = "tryout\\basic\\zBasic\\util\\crypt\\decrypt\\file\\VigenereCrypted_26_LangerBeispieltext1_schluesselwort_SchluesselWort.txt";
	    //sFilePathDefault = "tryout\\basic\\zBasic\\util\\crypt\\decrypt\\file\\VigenereCrypted_26_LangerBeispieltext1_schluesselwort_HALLO.txt";
	    //sFilePathDefault = "tryout\\basic\\zBasic\\util\\crypt\\decrypt\\file\\VigenereCrypted_26_LangerBeispieltext1_schluesselwort_SchluesselWort.txt";
	    //sFilePathDefault = "tryout\\basic\\zBasic\\util\\crypt\\decrypt\\file\\VigenereCrypted1_256_LangerBeispieltext1_schluesselwort_SchluesselWort.txt"; 
	    //sFilePathDefault = "tryout\\basic\\zBasic\\util\\crypt\\decrypt\\file\\VigenereCrypted3_LangerBeispieltext2_schluesselwort_SchluesselWort";
    
	    String sFilePath;
	    if (args.length > 0) {
	    	sFilePath = args[0];
	    } else {
	    	sFilePath = sFilePathDefault;		    	
	    }
	    
    String SchluesselWort;    
    if (args.length > 1) {
    	SchluesselWort = (args[1]);
    }else{    	    
    	//System.exit(0);//FGL: Nicht beenden, Defaultwert nehmen...
    	//Besser noch: Extrahiere das Schlüsselwort aus dem Dateinamen
    	//String SchluesselWort="HALLO";
    	//String SchluesselWort="SchluesselWort";
    	SchluesselWort = Vigenere26ZZZ.computeKeyWordFromEncryptedFile(sFilePath);
    }
    Vigenere26ZZZ objVigenere = new Vigenere26ZZZ(sFilePath, SchluesselWort);
    boolean btemp = objVigenere.decrypt();
    
    TODOGOON; //20221126: Erst einmal die Ausgabe des Ergebnisses anpassen.
                          //Danach die decrypt()-Methode selbst.
    System.out.print("\nOriginal-Text ausgeben? (J/N): ");
    if (IoUtil.JaNein()) {
      System.out.println("\n\n-- Originaltext von: "+Chiffre.computeFilePath()+" --");
      for (i=0; i<iaPure.length; i++) {
        IoUtil.printCharWithPosition(iaPure[i],i,"|");
        if (((i+1)%80)==0) System.out.println();	// neue Zeile
      }
      System.out.println("\n---- Laenge: "+c.length+" Bytes ----");
    }
    
    System.out.print("\nOriginal-Datei speichern (ueber Dialog)? (J/N): ");
    if (IoUtil.JaNein()) {
    	DateiUtil Original = new DateiUtil();
        //Original.schreib(c, EncodingMaintypeZZZ.TypeZZZ.ASCII.ordinal());
    	Original.schreib(iaPure, EncodingMaintypeZZZ.TypeZZZ.UTF8.ordinal());
    }   
    System.exit(0);
  }   
}