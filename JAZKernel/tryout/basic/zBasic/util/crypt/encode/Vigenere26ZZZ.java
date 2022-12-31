package basic.zBasic.util.crypt.encode;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import base.files.DateiUtil;
import base.files.EncodingMaintypeZZZ;
import base.files.EncodingMappedValueZZZ;
import base.io.IoUtil;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.datatype.string.FileEncodingUtil;
import basic.zBasic.util.crypt.AbstractVigenereZZZ;
import basic.zBasic.util.datatype.string.UnicodeZZZ;
import basic.zKernel.flag.IFlagUserZZZ;

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
  public static int iOffsetForAsciiRange=26;
  public static int iOffsetForUtf8Range=65;//Beginne mit "A"
  
  public Vigenere26ZZZ() {	  
	  super();
  }
  public Vigenere26ZZZ(String sSchluesselWort) {
	  super(sSchluesselWort);
  }
  public Vigenere26ZZZ(String sSchluesselWort, String sFilePath) {
	  super(sSchluesselWort, sFilePath);
  }
	
  public boolean encryptUI() throws ExceptionZZZ {
	  boolean bReturn = false;
	  main:{
	    String SchluesselWort=this.getCryptKey();	    
	    System.out.println("Schluesselwort: " + SchluesselWort);

	    DateiUtil Original = this.getFileOriginal();
	    int[]p = this.readOriginalValuesAsInt();	    
	    
	    System.out.print("\nOriginaltext ausgeben? (J/N): ");
	    if (IoUtil.JaNein()) {	      
	      System.out.println("---- Originaltext von: "+Original.computeFilePath()+" ----");	      
	      for (int i=0; i < p.length; i++) {
	        IoUtil.printChar(p[i]);	// druckbares Zeichen?
	        if (((i+1)%80)==0) System.out.println();
	      }
	    }
	    	  	         
	    System.out.println("\n-- Verschluessele Text von: "+Original.computeFilePath()+" --");
	    
	    int[]ppure = this.encrypt(p);
	    this.setEncryptedValues(ppure);
	    bReturn = true;
	}//end main:
	return bReturn;    
  }
  
  public boolean decryptUI() throws ExceptionZZZ{
	  boolean bReturn = false;
	  main:{		  		 
//		    System.out.print("\nChiffrierte Datei ausgeben? (J/N): ");
//		    if (IoUtil.JaNein()) {
//		      System.out.println("---- Chiffretext von: "+Chiffre.computeFilePath()+" ----");
//		      for (int i=0; i < c.length; i++) {
//		        IoUtil.printCharWithPosition(c[i],i,"|");
//		        if (((i+1)%80)==0) System.out.println(); 	// neue Zeile
//		      }
//		    }
		  
		    System.out.println("\nDatei einlesen ...");
		    DateiUtil Chiffre = this.getFileEncrypted();
		    System.out.println("Datei: '" + Chiffre.computeFilePath() + "'" );;
		    int[] c = Chiffre.liesAsInt(); //FGL: Fehlerkorrektur... das ist ja nicht als Unicode in die Datei geschrieben worden...  Chiffre.liesUnicode();	// Datei einlesen
		    for(int i=0; i < c.length; i++) {
		    	int i2 = c[i];
		    	IoUtil.printChar(i2);
		    }
		    
		    String SchluesselWort = this.getCryptKey();
			System.out.println("Schluesselwort: '"+SchluesselWort+"'");
			 
		    System.out.println("\nBeginne Entschluesselung ... ");		    
            int[]iaPure = this.decrypt(c);
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
	public int getOffsetForUtf8Range() {
		return Vigenere26ZZZ.iOffsetForUtf8Range;
	}
  
	@Override
	public int[] fromUtf8ToAsciiForOffset(int[] p) {
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
