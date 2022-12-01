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
	public Vigenere96ZZZ() {	  
		  super();
	  }
	  public Vigenere96ZZZ(String sFilePath, String sSchluesselWort) {
		  super(sFilePath, sSchluesselWort);
	  }
		
	  public boolean encrypt() throws ExceptionZZZ{
		  boolean bReturn = false;
		  main:{
		    String SchluesselWort=this.getKeyWord();
		    
		    System.out.println("Schluesselwort: " + SchluesselWort);
		    int laengeSW = SchluesselWort.length();    
		    int[] iaSchluesselwort = UnicodeZZZ.toIntArray(SchluesselWort);
		
			 //Auf Seite 34 steht... "wird auf space (Nr. 32) bezogen, 
		//    for(int i=0; i < iaSchluesselwort.length; i++) {
		//    	iaSchluesselwort[i]=iaSchluesselwort[i]-32;
		//    }
			iaSchluesselwort = UnicodeZZZ.fromUtf8ToAsciiFor96(iaSchluesselwort);
			
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
				
		//Nun gemaess Buch auf Seite 35, d.h. "blank/Leerzeichen" beziehen.
		//Merke: "B" ist im Buchbeispiel der erste Buchstabe mit einem ASCII Code von 66
        //       Also kommt für den ersten Buchstaben 66-32=34 heraus.
		p = UnicodeZZZ.fromUtf8ToAsciiFor96(p);
			
		//Auf Seite 34 steht... "wird auf space (Nr. 32) bezogen, 
		//    for(int i=0; i < p.length; i++) {
		//    	p[i]=p[i]-32;
		//    }
		    
		
	    
	    System.out.println("\n-- Verschluessele Text von: "+Original.computeFilePath()+" --");
	    int[]ppure = new int[p.length];
	    for (int i = 0; i < p.length; i++) {
	    	if(i>=1) System.out.print("|");
	        //Das steht in der Codedatei
	    	//Merke: c = Chiffrebuchstabe
	    	int iIndexS = i%laengeSW;
	    	int iSum = iaSchluesselwort[iIndexS]+p[i];
	    	int iFormula = (iSum)%96;  //auf Seite 35 wird der Modulus 96 verwendet. Merke 32+96=128    	
	    	int c = iFormula; //FGL: 	Das ist der Mathematische Ansatz: 
	      								//		Die Buchstaben wurden durch natuerliche Zahlen ersetzt.
	                                    //		Dann fiel eine Gesetzmaessigkeit auf (s. Seite 32 im Buch), die so ausgenutzt wurde.            
	      ppure[i] = c;				// nur wegen abspeichern  
	      System.out.print("i="+i+", c='"+c+"'"); 
	    }
	    //Gemaess Seite 35 noch 32 wieder draufaddieren, das ist das Leerzeichen "blank".
	    //c = c +32;
	    ppure = UnicodeZZZ.fromAsciiToUtf8For96(ppure);
	    this.setEncryptedValues(ppure);
	    bReturn = true;
	}//end main:
	return bReturn;    
  }
	  
  public boolean decrypt() throws ExceptionZZZ{
	  boolean bReturn = false;
	  main:{
		  System.out.println("\nBeginne Entschluesselung ... ");
		  String SchluesselWort=this.getKeyWord();		   
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
		    	  //TODOGOON20221130; //ABWEICHUNG BEI DER ENTSCHLUESSELUNG!!!
		    	  //TODOGOON20221130://Die decrypt Methode soweit wie möglich in die Abstracte Klasse verschieben.
		    	  //TODOGOON20221130://Diesen Wert 96 bzw. 26 (fue Vigenere 26) 
		    		               //in der Abstrakten Klasse speichern und aus einer Konstanten der erbenden Klasse speichern 
		    	  p+=96; //Fuer Viginere96 Verschluesselung  
		      }   
		      iaPure[i]=p;
		    }
		    
		    //Analog zum buch, nur mit +32
		    //Im Buch auf Seite 36 wird dann 32 draufgerechnet ("blank")
		    iaPure = UnicodeZZZ.fromAsciiToUtf8For96(iaPure);//funktioniert bei Viginere26 Verschluesselung, es wird 65 draufgerechnet
		    
		    this.setDecryptedValues(iaPure);		    
		    //++++++++++++++++++
		  
		  bReturn = true;
	  }//end main:
	  return bReturn;
  }
}
