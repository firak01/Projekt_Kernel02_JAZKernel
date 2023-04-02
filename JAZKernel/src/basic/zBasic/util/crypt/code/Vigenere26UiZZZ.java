package basic.zBasic.util.crypt.code;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import base.files.DateiUtil;
import base.files.EncodingMaintypeZZZ;
import base.files.EncodingMappedValueZZZ;
import base.io.IoUtil;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.datatype.string.FileEncodingUtil;
import basic.zBasic.util.datatype.string.UnicodeZZZ;
import basic.zKernel.flag.IFlagZUserZZZ;

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
public class Vigenere26UiZZZ extends Vigenere26ZZZ implements IVigenereUiZZZ{ 	
	private static final long serialVersionUID = 1L;
	
	private DateiUtil dateiOriginal=null; 
	boolean bDateiOriginalIsEncrypted=false;
	private DateiUtil dateiEncrypted=null;
	private DateiUtil dateiDecrypted=null;

	public Vigenere26UiZZZ() {	  
	  super();
	  }
	public Vigenere26UiZZZ(String[]saFlagControl) throws ExceptionZZZ {	  
	  super(saFlagControl);
	}
	public Vigenere26UiZZZ(String sSchluesselWort) throws ExceptionZZZ {
	  super(sSchluesselWort);
	}
	public Vigenere26UiZZZ(String sSchluesselWort, String sFilePath) throws ExceptionZZZ {
	  super(sSchluesselWort, sFilePath);
	}

	  @Override
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
	  
	  @Override
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
	public void setFileOriginal(DateiUtil datei) {
		this.dateiOriginal = datei;
	}
	
	@Override
	public DateiUtil getFileOriginal() {
		if(this.dateiOriginal==null) {
			String sFilePath = this.getFilePath();
			DateiUtil Original = new DateiUtil(sFilePath);
		    this.setFileOriginal(Original);
		}
		return this.dateiOriginal;
	}

	@Override
public void setFileEncrypted(DateiUtil datei) {
		this.dateiEncrypted = datei;
	}
	
	@Override
	public DateiUtil getFileEncrypted() {
		if(this.dateiEncrypted==null) {
			if(this.isFileOriginalEncrypted()) {
				this.dateiEncrypted = this.getFileOriginal();
			}else {
				this.isFileOriginalEncrypted(true);
				this.dateiEncrypted = this.getFileOriginal();
			}
		}
		return this.dateiEncrypted;
	}	
	
	@Override
	public void setFileDecrypted(DateiUtil datei) {
		this.dateiDecrypted = datei;
	}
	
	@Override
	public DateiUtil getFileDecrypted() {		
		return this.dateiDecrypted;
	}

	@Override
	public boolean isFileOriginalEncrypted() {
		return this.bDateiOriginalIsEncrypted;
	}
	
	@Override
	public void isFileOriginalEncrypted(boolean bFileOriginalIsEncrypted) {
		bDateiOriginalIsEncrypted = bFileOriginalIsEncrypted;
	}
	
	@Override
	public int[] readOriginalValuesAsInt() {
		
		DateiUtil Original = this.getFileOriginal();
		if(Original!=null) {
			int[] p = Original.liesUnicode();//FGL: Der Klartextbuchstabe
			this.setOriginalValues(p);
		}
		
		return this.getOriginalValuesAsInt();
	}
}
