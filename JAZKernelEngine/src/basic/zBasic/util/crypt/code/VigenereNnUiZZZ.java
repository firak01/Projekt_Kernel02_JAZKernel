package basic.zBasic.util.crypt.code;

import base.files.DateiUtil;
import base.io.IoUtil;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractList.ArrayListZZZ;
import basic.zBasic.util.datatype.character.CharacterExtendedZZZ;

public class VigenereNnUiZZZ extends VigenereNnZZZ implements IVigenereNnUiZZZ{
	private static final long serialVersionUID = 1L;
	
	private DateiUtil dateiOriginal=null; 
	boolean bDateiOriginalIsEncrypted=false;
	private DateiUtil dateiEncrypted=null;
	private DateiUtil dateiDecrypted=null;
	
	public VigenereNnUiZZZ() {
		super();
	}
	public VigenereNnUiZZZ(String[]saFlagControl) throws ExceptionZZZ {	  
		super(saFlagControl);
	}
	
	public VigenereNnUiZZZ(String sKeyString) throws ExceptionZZZ {
		super(sKeyString);		
	}
	
	
	public VigenereNnUiZZZ(String sKeyString, String sFilePath) throws ExceptionZZZ {
		super(sKeyString,sFilePath);		
	}

	@Override
	public boolean encryptUI() throws ExceptionZZZ {
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
		    

		this.encrypt(p);
	    bReturn = true;
	}//end main:
	return bReturn;    
	}

	@Override
	public boolean decryptUI() throws ExceptionZZZ {
		 boolean bReturn = false;
		  main:{
//			    System.out.print("\nChiffrierte Datei ausgeben? (J/N): ");
//			    if (IoUtil.JaNein()) {
//			      System.out.println("---- Chiffretext von: "+Chiffre.computeFilePath()+" ----");
//			      for (int i=0; i < c.length; i++) {
//			        IoUtil.printCharWithPosition(c[i],i,"|");
//			        if (((i+1)%80)==0) System.out.println(); 	// neue Zeile
//			      }
//			    }
			   
			    System.out.println("\nDatei einlesen ...");
			    DateiUtil Chiffre = this.getFileEncrypted();
			    System.out.println("Datei: '" + Chiffre.computeFilePath() + "'" );;
			    int[] c = Chiffre.liesAsInt(); //FGL: Fehlerkorrektur... das ist ja nicht als Unicode in die Datei geschrieben worden...  Chiffre.liesUnicode();	// Datei einlesen
			    for(int i=0; i < c.length; i++) {
			    	int i2 = c[i];
			    	IoUtil.printChar(i2);
			    }
			    
			    String SchluesselWort=this.getCryptKey();		   
				System.out.println("\nSchluesselwort: '"+SchluesselWort+"'");
				  		    
			    System.out.println("\nBeginne Entschluesselung ... ");

			    this.decrypt(c);
			    bReturn = true;
		  }//end main:
		  return bReturn;
	}
	

	  @Override
		public void setFileOriginal(DateiUtil datei) throws ExceptionZZZ {
			this.dateiOriginal = datei;
		}
		
		@Override
		public DateiUtil getFileOriginal() throws ExceptionZZZ {
			if(this.dateiOriginal==null) {
				String sFilePath = this.getFilePath();
				DateiUtil Original = new DateiUtil(sFilePath);
			    this.setFileOriginal(Original);
			}
			return this.dateiOriginal;
		}

		@Override
	public void setFileEncrypted(DateiUtil datei) throws ExceptionZZZ {
			this.dateiEncrypted = datei;
		}
		
		@Override
		public DateiUtil getFileEncrypted() throws ExceptionZZZ {
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
		public void setFileDecrypted(DateiUtil datei) throws ExceptionZZZ {
			this.dateiDecrypted = datei;
		}
		
		@Override
		public DateiUtil getFileDecrypted() throws ExceptionZZZ {		
			return this.dateiDecrypted;
		}

		@Override
		public boolean isFileOriginalEncrypted() throws ExceptionZZZ {
			return this.bDateiOriginalIsEncrypted;
		}
		
		@Override
		public void isFileOriginalEncrypted(boolean bFileOriginalIsEncrypted) throws ExceptionZZZ {
			bDateiOriginalIsEncrypted = bFileOriginalIsEncrypted;
		}
		
		@Override
		public int[] readOriginalValuesAsInt() throws ExceptionZZZ {
		
		DateiUtil Original = this.getFileOriginal();
		if(Original!=null) {
			int[] p = Original.liesUnicode();//FGL: Der Klartextbuchstabe
			this.setOriginalValues(p);
		}
		
		return this.getOriginalValuesAsInt(null);
	}
}
