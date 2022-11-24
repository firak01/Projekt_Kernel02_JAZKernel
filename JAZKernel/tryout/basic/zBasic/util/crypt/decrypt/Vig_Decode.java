package basic.zBasic.util.crypt.decrypt;

import base.files.DateiUtil;
import base.files.EncodingMaintypeZZZ;
import base.io.IoUtil;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.datatype.string.UnicodeZZZ;
import basic.zBasic.util.file.FileEasyZZZ;

class Vig_Decode { 	// Vigenereentschluesselung mit bekanntem Schluesselwort!
  public static void main( String[] arg) {
    String SchluesselWort;
    DateiUtil Chiffre;
    int p, i, laengeSW;
    
    //+++++++++++++++++++
    
    if (arg.length > 0) {
    	Chiffre = new DateiUtil(arg[0]); 
    } else {
    	//Chiffre = new Datei();
    	//Klappt, das Ergebnis der Datei passt zum Text im Buch.  
    	//Chiffre = new DateiUtil("tryout\\basic\\zBasic\\util\\crypt\\decrypt\\file\\VigenereCrypted_26_Beispieltext2_schluesselwort_HALLO.txt");    	  
    	//Chiffre = new DateiUtil("tryout\\basic\\zBasic\\util\\crypt\\decrypt\\file\\VigenereCrypted_26_Beispieltext2_schluesselwort_SchluesselWort.txt");
    	
    	
    	//Klappt nicht: Ergebnis der Datei passt nicht zum Text im Buch    	
    	Chiffre = new DateiUtil("tryout\\basic\\zBasic\\util\\crypt\\decrypt\\file\\VigenereCrypted_128_LangerBeispieltext1_schluesselwort_SchluesselWort.txt");
    	//Chiffre = new DateiUtil("tryout\\basic\\zBasic\\util\\crypt\\decrypt\\file\\VigenereCrypted_26_LangerBeispieltext1_schluesselwort_SchluesselWort.txt");
    	//Chiffre = new DateiUtil("tryout\\basic\\zBasic\\util\\crypt\\decrypt\\file\\VigenereCrypted_26_LangerBeispieltext1_schluesselwort_HALLO.txt");
    	//Chiffre = new DateiUtil("tryout\\basic\\zBasic\\util\\crypt\\decrypt\\file\\VigenereCrypted_26_LangerBeispieltext1_schluesselwort_SchluesselWort.txt");
    	//Chiffre = new DateiUtil("tryout\\basic\\zBasic\\util\\crypt\\decrypt\\file\\VigenereCrypted1_256_LangerBeispieltext1_schluesselwort_SchluesselWort.txt"); 
    	//Chiffre = new DateiUtil("tryout\\basic\\zBasic\\util\\crypt\\decrypt\\file\\VigenereCrypted3_LangerBeispieltext2_schluesselwort_SchluesselWort");
    }
    
    if (arg.length > 1) {
    	SchluesselWort = (arg[1]);
    }else{    	    
    	//System.exit(0);//FGL: Nicht beenden, Defaultwert nehmen...
    	//Besser noch: Extrahiere das Schlüsselwort aus dem Dateinamen
    	//String SchluesselWort="HALLO";
    	//String SchluesselWort="SchluesselWort";
    	SchluesselWort = Vig_Decode.computeKeyWordFromFile(Chiffre);
    }
    System.out.println("Schluesselwort: '"+SchluesselWort+"'");
    int[] iasPure = IoUtil.Unicode(SchluesselWort.getBytes());
    //int[]iasPure = UnicodeZZZ.fromByteToInt(SchluesselWort.getBytes());
    //int[]iasPure = UnicodeZZZ.fromAsciiToUtf8(SchluesselWort.getBytes());
    
    laengeSW = SchluesselWort.length();
    
    System.out.println("\nDatei einlesen ...");
    int[] c = Chiffre.liesAsInt(); //FGL: Fehlerkorrektur... das ist ja nicht als Unicode in die Datei geschrieben worden...  Chiffre.liesUnicode();	// Datei einlesen
    for(i=0; i < c.length; i++) {
    	int i2 = c[i];
    	IoUtil.printChar(i2);
    }
    
    System.out.print("\nChiffrierte Datei ausgeben? (J/N): ");
    if (IoUtil.JaNein()) {
      System.out.println("---- Chiffretext von: "+Chiffre.computeFilePath()+" ----");
      for (i=0; i < c.length; i++) {
        IoUtil.printCharWithPosition(c[i],i,"|");
        if (((i+1)%80)==0) System.out.println(); 	// neue Zeile
      }
    }
    System.out.println("\nBeginne Entschluesselung ... ");
    for (i=0; i<c.length; i++) {
      int iModLaengeSW = i%laengeSW;
      int iBezug = iasPure[iModLaengeSW];
      p = c[i]-iBezug;			// c-s
      if (p < 0) {    	  
    	  //p+=256;
    	  p+=26; //Fuer Viginere26 Verschluesselung  
      }   
      c[i]=p;
    }
    
    //++++++++++++++++++
    //int[]iaPure = UnicodeZZZ.fromUtf8ToAscii(c);
    int[]iaPure = UnicodeZZZ.fromAsciiToUtf8For26(c);//funktioniert bei Viginere26 Verschluesselung
    //int[]iaPure = c;
    
    //++++++++++++++++++
    System.out.print("\nOriginal-Text ausgeben? (J/N): ");
    if (IoUtil.JaNein()) {
      System.out.println("\n\n-- Originaltext von: "+Chiffre.computeFilePath()+" --");
      for (i=0; i<iaPure.length; i++) {
        IoUtil.printCharWithPosition(iaPure[i],i,"|");
        //if (((i+1)%80)==0) System.out.println();	// neue Zeile
      }
      System.out.println("\n---- Laenge: "+c.length+" Bytes ----");
    }
    
    System.out.print("\nOriginal-Datei speichern? (J/N): ");
    if (IoUtil.JaNein()) {
    	DateiUtil Original = new DateiUtil();
        Original.schreib(c, EncodingMaintypeZZZ.TypeZZZ.ASCII.ordinal());
    }   
    System.exit(0);
  }
  
  public static String computeKeyWordFromFile(DateiUtil fileUtil) {
	  String sReturn=null;
	  main:{
		  if(fileUtil==null)break main;
		  String sFilepath = fileUtil.computeFilePath();
		  if(StringZZZ.isEmpty(sFilepath)) break main;
		  
		  sReturn = StringZZZ.leftback(sFilepath, FileEasyZZZ.sFILE_ENDING_SEPARATOR);
		  sReturn = StringZZZ.right(sReturn, FileEasyZZZ.sDIRECTORY_SEPARATOR);
		  sReturn = StringZZZ.right(sReturn, "_");
	  }//end main:
	  return sReturn;
	  
	  
  }
}