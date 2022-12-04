package basic.zBasic.util.crypt.encode;

import base.files.DateiUtil;
import base.files.EncodingMaintypeZZZ;
import base.io.IoUtil;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.datatype.string.UnicodeZZZ;

/** Aus "Kryptographie mit Java", Seite 33f
 *  Die Klasse stammt aus der Buch CD, aus dem Verzeichnis poly
 *  Die Verschlusselungsmethode gehoert zu den "polyalphabetischen Ansaetzen"
 *  
 * Erweitert und angepasst
 * Insbesondere Anpassung an 96 Zeichen, damit es zu den Zahlenwerten und den Verschluesselten Buchstabenwerten im Buchbeispiel auf der angegebenen Seite passt
 * Merke: Der verwendete Moduluswert entspricht dem 96 im Klassennamen
 * @author Fritz Lindhauer, 08.10.2022, 08:29:08
 * 
 */
public class Vigenere96ZZZ extends AbstractVigenereZZZ{ 		// Vigenereverschluesselung
	private static int iOffsetForAsciiRange=96;
	private static int iOffsetForUtf8Range=32;
	public Vigenere96ZZZ() {	  
		  super();
	  }
		public Vigenere96ZZZ(String sSchluesselWort) {
		  super(sSchluesselWort);
	  }
	  public Vigenere96ZZZ(String sFilePath, String sSchluesselWort) {
		  super(sFilePath, sSchluesselWort);
	  }
		
	  public boolean encryptUI() throws ExceptionZZZ{
		  boolean bReturn = false;
		  main:{
		    String SchluesselWort=this.getCryptKey();
		    
		    System.out.println("Schluesselwort: " + SchluesselWort);
			
		    String sFilePath = this.getFilePath();
		    DateiUtil Original = new DateiUtil(sFilePath);
		    this.setFileOriginal(Original);
		   
		    int[] p = Original.liesUnicode();//FGL: Der Klartextbuchstabe
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
		    
			String SchluesselWort=this.getCryptKey();		   
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
		return Vigenere96ZZZ.iOffsetForAsciiRange;
	}
	
	@Override
	public int getOffsetForUtf8Range() {
		return Vigenere96ZZZ.iOffsetForUtf8Range;
	}
	
	@Override
	public int[] fromUtf8ToAsciiForOffset(int[] p) {
		 //Analog zu Vigenere96.java, nur mit 65
		 //ImBuch, auf Seite 34 steht... "wird auf space (Nr. 32) bezogen, 
		 //    for(int i=0; i < p.length; i++) {
		 //    	p[i]=p[i]-65;
		 //    }
		 return UnicodeZZZ.fromUtf8ToAsciiFor96(p);
	}
	
	@Override
	public int[] fromAsciiToUtf8ForOffset(int[] p) {
		//Analog zum buch, nur mit +32
	    //Im Buch auf Seite 36 wird dann 32 draufgerechnet ("blank")
	    return UnicodeZZZ.fromAsciiToUtf8For96(p);//funktioniert bei Viginere26 Verschluesselung, es wird 65 draufgerechnet	   
	}
}
