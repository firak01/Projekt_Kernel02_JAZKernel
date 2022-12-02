package basic.zBasic.util.crypt.encode;

import java.io.File;
import java.io.IOException;

import base.files.DateiUtil;
import base.files.EncodingMaintypeZZZ;
import base.files.EncodingMappedValueZZZ;
import base.io.IoUtil;
import basic.zBasic.ExceptionZZZ;
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
public class Vigenere26ZZZ extends AbstractVigenereZZZ{ 	
  private static int iOffsetForAsciiRange=26;
  
  public Vigenere26ZZZ() {	  
	  super();
  }
  public Vigenere26ZZZ(String sFilePath, String sSchluesselWort) {
	  super(sFilePath, sSchluesselWort);
  }
	
  public boolean encryptUI() throws ExceptionZZZ {
	  boolean bReturn = false;
	  main:{
	    String SchluesselWort=this.getKeyWord();
	    
	    System.out.println("Schluesselwort: " + SchluesselWort);
//	    int laengeSW = SchluesselWort.length();    
//	    int[] iaSchluesselwort = UnicodeZZZ.toIntArray(SchluesselWort);
//	
//	     iaSchluesselwort=this.fromUTF8ToAsciiForOffset(iaSchluesselwort);
	     DateiUtil Original = this.getFileOriginal();
	     int[]p = this.getOriginalValuesAsInt();	    
	    
	    System.out.print("\nOriginaltext ausgeben? (J/N): ");
	    if (IoUtil.JaNein()) {	      
	      System.out.println("---- Originaltext von: "+Original.computeFilePath()+" ----");	      
	      for (int i=0; i < p.length; i++) {
	        IoUtil.printChar(p[i]);	// druckbares Zeichen?
	        if (((i+1)%80)==0) System.out.println();
	      }
	    }
	    	  	         
	    System.out.println("\n-- Verschluessele Text von: "+Original.computeFilePath()+" --");
//	    p = this.fromUTF8ToAsciiForOffset(p);
//	    
//	    int[]ppure = new int[p.length];
//	    for (int i = 0; i < p.length; i++) {
//	    	if(i>=1) System.out.print("|");
//	        //Das steht in der Codedatei
//	    	//Merke: c = Chiffrebuchstabe
//	    	int iModLaengeSW = i%laengeSW;
//	    	int iBezug = iaSchluesselwort[iModLaengeSW];
//	    	int iSum = p[i]+iBezug;
//	    	   	
//	    	int iFormula = (iSum)%this.getOffsetForAsciiRange();//An das Beispiel im Buch angepasst
//	    	int c = iFormula; //FGL: 	Das ist der Mathematische Ansatz: 
//	      								//		Die Buchstaben wurden durch natuerliche Zahlen ersetzt.
//	                                    //		Dann fiel eine Gesetzmaessigkeit auf (s. Seite 32 im Buch), die so ausgenutzt wurde.	    	
//	      ppure[i] = c;				// nur wegen abspeichern  	
//	      System.out.print("i="+i+", c='"+c+"'");      
//	    }
//        //Gemaess Seite 35, analog zu Vigenere96 noch 65 wieder draufaddieren
//        //c = c +65;
//	    ppure = UnicodeZZZ.fromAsciiToUtf8For26(ppure);
	    
	    int[]ppure = new int[p.length];
		ppure = this.encrypt(p);
	    this.setEncryptedValues(ppure);
	    bReturn = true;
	}//end main:
	return bReturn;    
  }
  
  public boolean decryptUI() throws ExceptionZZZ{
	  boolean bReturn = false;
	  main:{
		  System.out.println("\nBeginne Entschluesselung ... ");
		  String SchluesselWort = this.getKeyWord();
		  System.out.println("Schluesselwort: '"+SchluesselWort+"'");
		    //int[] iasPure = IoUtil.Unicode(SchluesselWort.getBytes());
		    int[] iasPure = UnicodeZZZ.toIntArray(SchluesselWort);		    
		    int laengeSW = SchluesselWort.length();
		  
		    System.out.println("\nDatei einlesen ...");
		    DateiUtil Chiffre = this.getFileEncrypted();
		    System.out.println("Datei: '" + Chiffre.computeFilePath() + "'" );;
		    int[] c = Chiffre.liesAsInt(); //FGL: Fehlerkorrektur... das ist ja nicht als Unicode in die Datei geschrieben worden...  Chiffre.liesUnicode();	// Datei einlesen
		    for(int i=0; i < c.length; i++) {
		    	int i2 = c[i];
		    	IoUtil.printChar(i2);
		    }
		    
//		    System.out.print("\nChiffrierte Datei ausgeben? (J/N): ");
//		    if (IoUtil.JaNein()) {
//		      System.out.println("---- Chiffretext von: "+Chiffre.computeFilePath()+" ----");
//		      for (int i=0; i < c.length; i++) {
//		        IoUtil.printCharWithPosition(c[i],i,"|");
//		        if (((i+1)%80)==0) System.out.println(); 	// neue Zeile
//		      }
//		    }
		    
		    
		    System.out.println("\nBeginne Entschluesselung ... ");
		    int[]iaPure = new int[c.length];
		    for (int i=0; i<c.length; i++) {
		      int iModLaengeSW = i%laengeSW;
		      int iBezug = iasPure[iModLaengeSW];
		      int p = c[i]-iBezug;			// c-s
		      if (p < 0) {    	  
		    	  p+=this.getOffsetForAsciiRange(); //Fuer Viginere26 Verschluesselung  
		      }   
		      iaPure[i]=p;
		    }
		    iaPure = this.fromAsciiToUtf8ForOffset(iaPure);//funktioniert bei Viginere26 Verschluesselung, es wird 65 draufgerechnet		    

		    this.setDecryptedValues(iaPure);
		    
		    bReturn = true;
	  }//end main:
	  return bReturn;
  }
  
  @Override
	public int getOffsetForAsciiRange() {
		return Vigenere26ZZZ.iOffsetForAsciiRange;
	}
  
	@Override
	public int[] fromUTF8ToAsciiForOffset(int[] p) {
		 //Analog zu Vigenere96.java, nur mit 65
		 //ImBuch, auf Seite 34 steht entsprechend ... "wird auf space (Nr. 32) bezogen, 
		 //    for(int i=0; i < p.length; i++) {
		 //    	p[i]=p[i]-65;
		 //    }
		 return UnicodeZZZ.fromUtf8ToAsciiFor26(p);
	}
	
	@Override
	public int[] fromAsciiToUtf8ForOffset(int[] p) {
		//Analog zum buch, nur mit +65
	    //Im Buch auf Seite 36 wird dann ensprechend 32 draufgerechnet ("blank")
	    return UnicodeZZZ.fromAsciiToUtf8For26(p);//funktioniert bei Viginere26 Verschluesselung, es wird 65 draufgerechnet	   
	}
}
