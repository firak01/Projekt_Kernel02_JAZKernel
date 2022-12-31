package basic.zBasic.util.crypt.decrypt;

import base.files.DateiUtil;
import base.files.EncodingMaintypeZZZ;
import base.io.IoUtil;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.crypt.encode.Vigenere26ZZZ;
import basic.zBasic.util.crypt.encode.Vigenere96ZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.datatype.string.UnicodeZZZ;
import basic.zBasic.util.file.FileEasyZZZ;

class Vig_Decode26ZZZmain { 	// Vigenereentschluesselung mit bekanntem Schluesselwort!
  public static void main( String[] args) {
	  main:{
	  try {	  
		  //TODOGOON; //Mache Utility-Methoden im Consolen - Output
		  //          //- Erstelle eine Box mit Anzahl Breite Zeichen insgesamt, Rahmenzeichen,
		              //  Rahmenbreite links, Rahmenbreite rechts
		              //  Übergabe des Textinhalts per ArrayList
		              //  Automatischem Zeilenumbruch wg. Länge und nach <BR>
		              //  Auflistungszeichen <li>
		System.out.println("################################################");
		System.out.println("### DECHIFRIERUNG VIGENERE 26 Kodierter Text ###");
		System.out.println("### - mit bekanntem Schluesselwort,          ###");
		System.out.println("###   aus dem Dateinamen geholt              ###");
		System.out.println("################################################");
		
	    String sFilePathDefault;
	    sFilePathDefault = "tryout\\basic\\zBasic\\util\\crypt\\decrypt\\file\\VigenereCrypted_26_Beispieltext2_schluesselwort_HALLO.txt";    
    	
    	//Klappt, das Ergebnis der Datei passt zum Text im Buch.  
	    //sFilePathDefault = "tryout\\basic\\zBasic\\util\\crypt\\decrypt\\file\\VigenereCrypted_26_Beispieltext2_schluesselwort_HALLO.txt";    	  
	    //sFilePathDefault = "tryout\\basic\\zBasic\\util\\crypt\\decrypt\\file\\VigenereCrypted_26_Beispieltext2_schluesselwort_SchluesselWort.txt";
    	
    	
    	//Klappt nicht: Ergebnis der Datei passt nicht zum Text im Buch. Der Algorithmus weicht halt leicht ab mir dem Bezugszeichen und dem Modulus=Anzahl der Zeichen in der Zeichnmenge
	    //sFilePathDefault = "tryout\\basic\\zBasic\\util\\crypt\\decrypt\\file\\VigenereCrypted_26_LangerBeispieltext1_schluesselwort_SchluesselWort.txt";
	    //sFilePathDefault = "tryout\\basic\\zBasic\\util\\crypt\\decrypt\\file\\VigenereCrypted_96_LangerBeispieltext1_schluesselwort_SchluesselWort.txt";
    	    
	    String sFilePath;
	    if (args.length > 0) {
	    	sFilePath = args[0];
	    } else {
	    	System.out.print("\nVerschluesselte Datei auswaehlen (per Dialog)? (J/N): ");
		    if (IoUtil.JaNein()) {	
		    	DateiUtil Chiffre = new DateiUtil();
		    	Chiffre.selectLoad();
		    	sFilePath = Chiffre.computeFilePath();
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
    }else{    	    
    	//System.exit(0);//FGL: Nicht beenden, Defaultwert nehmen...
    	//Besser noch: Extrahiere das Schlüsselwort aus dem Dateinamen
    	//String SchluesselWort="HALLO";
    	//String SchluesselWort="SchluesselWort";
    	SchluesselWort = Vigenere26ZZZ.computeKeyWordFromEncryptedFile(sFilePath);
    	if(SchluesselWort==null) {
    		System.out.println("Kein Schluesselwort aus der Datei ermittelbar.");
    		System.out.println("Abbruch.");
    		break main;
    	}
    }
    Vigenere26ZZZ objVigenere = new Vigenere26ZZZ(SchluesselWort,sFilePath);
    objVigenere.isFileOriginalEncrypted(true);
    
    //Die Ausgabe der (hier schon chiffierten) Originaldatei
    System.out.print("\nChiffrierten-Text ausgeben? (J/N): ");
    if (IoUtil.JaNein()) {
      DateiUtil Chiffre = objVigenere.getFileOriginal();
      System.out.println("\n-- Chiffriertentext von: "+Chiffre.computeFilePath()+" --");
      int[]iaPure = objVigenere.readOriginalValuesAsInt();
      for (int i=0; i<iaPure.length; i++) {
        IoUtil.printCharWithPosition(iaPure[i],i,"|");
        if (((i+1)%80)==0) System.out.println();	// neue Zeile
      }
      System.out.println("\n---- Laenge: "+iaPure.length+" Bytes ----");
      
      /* alte Version, gibt es hier Abweichungen? 
      System.out.println("\nDatei einlesen ...");
      int[] c = Chiffre.liesAsInt(); //FGL: Fehlerkorrektur... das ist ja nicht als Unicode in die Datei geschrieben worden...  Chiffre.liesUnicode();	// Datei einlesen
      for(i=0; i < c.length; i++) {
      	int i2 = c[i];
      	IoUtil.printChar(i2);
      }
      System.out.println("\n---- Laenge: "+c.length+" Bytes ----");
      */
    }
    
    //Starten der Entschluesselung
    boolean btemp = objVigenere.decryptUI();
    if(btemp) {		    		    
	    System.out.print("\nEntschluesselten Text ausgeben? (J/N): ");
		    if (IoUtil.JaNein()) {		
		      DateiUtil Chiffre = objVigenere.getFileEncrypted();
		      System.out.println("\n\n-- Entschluesselter Text von: "+Chiffre.computeFilePath()+" --");
		      int[]ppure = objVigenere.getDecryptedValuesAsInt();
		      for (int i = 0; i < ppure.length; i++) {
		    	IoUtil.printCharWithPosition((ppure[i]),"|");
		        if (((i+1)%80)==0) System.out.println();	// neue Zeile
		      }
		    }
		    System.out.print("\n---- Laenge: "+objVigenere.getDecryptedValuesAsInt().length+" Bytes ----");
		    
		    System.out.println("\nEntschluesselten Text als Datei speichern (ueber Dialog)? (J/N): ");
		    if (IoUtil.JaNein()) {    	
		    	DateiUtil Entschluesselt = new DateiUtil();
		        //Entschluesselt.schreib(ppure, EncodingMaintypeZZZ.TypeZZZ.ASCII.ordinal());
		    	Entschluesselt.schreib(objVigenere.getDecryptedValuesAsInt(), EncodingMaintypeZZZ.TypeZZZ.UTF8.ordinal());
		    	objVigenere.setFileDecrypted(Entschluesselt);
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