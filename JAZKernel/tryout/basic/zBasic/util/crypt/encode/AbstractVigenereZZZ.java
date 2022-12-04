package basic.zBasic.util.crypt.encode;

import base.files.DateiUtil;
import base.io.IoUtil;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ObjectZZZ;
import basic.zBasic.util.crypt.CryptAlgorithmMaintypeZZZ;
import basic.zBasic.util.datatype.character.CharArrayZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.datatype.string.UnicodeZZZ;
import basic.zBasic.util.file.FileEasyZZZ;

public abstract class AbstractVigenereZZZ extends ObjectZZZ implements IVigenereZZZ{
	private String sFilePath=null;
	private String sKeyWord=null;
	
	private DateiUtil dateiOriginal=null; boolean bDateiOriginalIsEncrypted=false;
	private DateiUtil dateiEncrypted=null;
	private DateiUtil dateiDecrypted=null;
	private int[] iaOriginal=null; 
	private int[] iaEncrypted=null;
	private int[] iaDecrypted=null;
	
	public AbstractVigenereZZZ() {		
	}
	public AbstractVigenereZZZ(String sSchluesselwort) {
		this.setCryptKey(sSchluesselwort);
	}
	public AbstractVigenereZZZ(String sSchluesselWort, String sFilePath) {		
		this.setCryptKey(sSchluesselWort);
		this.setFilePath(sFilePath);
	}
		
	//+++++++ Getter / Setter
	public void setFilePath(String sFilePath) {
		this.sFilePath = sFilePath;
	}
	public String getFilePath() {
		return this.sFilePath;
	}
	
	public int[] getOriginalValuesAsInt() {
		if(this.iaOriginal==null) {
			DateiUtil Original = this.getFileOriginal();
			if(Original!=null) {
				int[] p = Original.liesUnicode();//FGL: Der Klartextbuchstabe
				this.setOriginalValues(p);
			}
		}
		return this.iaOriginal;
	}
	public void setOriginalValues(int[] iaOriginal) {
		this.iaOriginal = iaOriginal;
	}
	
	public boolean isFileOriginalEncrypted() {
		return this.bDateiOriginalIsEncrypted;
	}
	public void isFileOriginalEncrypted(boolean bFileOriginalIsEncrypted) {
		bDateiOriginalIsEncrypted = bFileOriginalIsEncrypted;
	}
	
	//######################
	public abstract int getOffsetForUtf8Range();
	public abstract int getOffsetForAsciiRange();
	public abstract int[]fromUtf8ToAsciiForOffset(int[] p);
	public abstract int[]fromAsciiToUtf8ForOffset(int[] p);

	//###########################
	//### Verschluesselungsmethoden
	//###########################	
	/** Starte die Verschluesselung, weitere Eingaben sind moeglich:
	 *  - per Konsole 
	 *  - per Dialog, z.B. Auswahl von Dateien 
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 03.12.2022, 05:43:47
	 */
	public abstract boolean encryptUI() throws ExceptionZZZ;
	
	/** Der reine Entschluesselungsalgorithmus, ohne Eingaben per Console, etc.
	 * @param p
	 * @return
	 * @author Fritz Lindhauer, 02.12.2022, 08:31:12
	 */
	public int[] encrypt(int[]p) {
		int[]iaReturn=null;
		main:{
			if(p==null)break main;
			iaReturn=new int[p.length];
			
			String sKeyWord = this.getCryptKey();
			if(StringZZZ.isEmpty(sKeyWord)) {
				iaReturn=p;
				break main;
			}
			
			int[] iaSchluesselwort = UnicodeZZZ.toIntArray(sKeyWord);
			
			//Auf Seite 34 steht... "wird auf space (Nr. 32) bezogen, 
			//    for(int i=0; i < iaSchluesselwort.length; i++) {
			//    	iaSchluesselwort[i]=iaSchluesselwort[i]-32;
			//    }
			iaSchluesselwort = this.fromUtf8ToAsciiForOffset(iaSchluesselwort);
				
			int laengeSW = sKeyWord.length();
			
			//Nun gemaess Buch auf Seite 35, d.h. "blank/Leerzeichen" beziehen.
			//Merke: "B" ist im Buchbeispiel der erste Buchstabe mit einem ASCII Code von 66
	        //       Also kommt für den ersten Buchstaben 66-32=34 heraus.
			p = this.fromUtf8ToAsciiForOffset(p);
			
		    int[]ppure = new int[p.length];
		    for (int i = 0; i < p.length; i++) {
		    	if(i>=1) System.out.print("|");
		        //Das steht in der Codedatei
		    	//Merke: c = Chiffrebuchstabe
		    	int iIndexS = i%laengeSW;
		    	int iSum = iaSchluesselwort[iIndexS]+p[i];
		    	int iFormula = (iSum)% this.getOffsetForAsciiRange();  //auf Seite 35 wird der Modulus 96 verwendet. Merke 32+96=128    	
		    	int c = iFormula; //FGL: 	Das ist der Mathematische Ansatz: 
		      								//		Die Buchstaben wurden durch natuerliche Zahlen ersetzt.
		                                    //		Dann fiel eine Gesetzmaessigkeit auf (s. Seite 32 im Buch), die so ausgenutzt wurde.            
		      ppure[i] = c;				// nur wegen abspeichern  
		      System.out.print("i="+i+", c='"+c+"'"); 
		    }
		    System.out.print("\n");
		    //Gemaess Seite 35 noch 32 wieder draufaddieren, das ist das Leerzeichen "blank".
		    //c = c +32;
		    iaReturn = this.fromAsciiToUtf8ForOffset(ppure);
		}//end main:
		return iaReturn;
	}
	
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
	public int[] getEncryptedValuesAsInt() {
		return this.iaEncrypted;
	}
	public void setEncryptedValues(int[] iaCrypted) {
		this.iaEncrypted = iaCrypted;
	}
	
	//##########################
	//### Entschluesselungsmethoden
	//##########################	
	public abstract boolean decryptUI() throws ExceptionZZZ;
    
	/** Der reine Verschluesselungsalgorithmus, ohne Eingaben per Console, etc.
	 * @param p
	 * @return
	 * @author Fritz Lindhauer, 02.12.2022, 08:31:12
	 */
	public int[] decrypt(int[]c) {
		int[]iaReturn=null;
		main:{
			if(c==null)break main;
			iaReturn=new int[c.length];
			
			String sKeyWord = this.getCryptKey();
			if(StringZZZ.isEmpty(sKeyWord)) {
				iaReturn=c;
				break main;
			}
				
			int[] iaSchluesselwort = UnicodeZZZ.toIntArray(sKeyWord);
				
				//Auf Seite 34 steht... "wird auf space (Nr. 32) bezogen, 
				//    for(int i=0; i < iaSchluesselwort.length; i++) {
				//    	iaSchluesselwort[i]=iaSchluesselwort[i]-32;
				//    }
				iaSchluesselwort = this.fromUtf8ToAsciiForOffset(iaSchluesselwort);
					
				int laengeSW = sKeyWord.length();
				
				System.out.println("\nBeginne Entschluesselung ... ");
			    int[]iaPure = new int[c.length];
			    for (int i=0; i<c.length; i++) {
			      int iModLaengeSW = i%laengeSW;
			      int iBezug = iaSchluesselwort[iModLaengeSW];
			      int p = c[i]-iBezug;			// c-s
			      if (p < this.getOffsetForUtf8Range()) {    	  
			    	  p+=this.getOffsetForAsciiRange();//26 Fuer Viginere26 Verschluesselung
			      }   
			      iaPure[i]=p;
			    }			  
			    iaReturn = iaPure;
		}//end main:
		return iaReturn;   
	}
	
	
	public void setFileEncrypted(DateiUtil datei) {
		this.dateiEncrypted = datei;
	}
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
	public void setFileDecrypted(DateiUtil datei) {
		this.dateiDecrypted = datei;
	}
	public DateiUtil getFileDecrypted() {		
		return this.dateiDecrypted;
	}
	public int[] getDecryptedValuesAsInt() {
		return this.iaDecrypted;
	}
	public void setDecryptedValues(int[] iaCrypted) {
		this.iaDecrypted = iaCrypted;
	}
	
	public static String computeKeyWordFromEncryptedFile(String sFilePath) {
		String sReturn=null;
		main:{
			if(StringZZZ.isEmpty(sFilePath)) break main;
			
			DateiUtil objFile = new DateiUtil(sFilePath);
			sReturn = computeKeyWordFromEncryptedFile(objFile);
		}//end main:
		return sReturn;
	}
	public static String computeKeyWordFromEncryptedFile(DateiUtil fileUtil) {
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
	
	//####################################################
		
	//####################################################	
	//### Methoden aus ICryptZZZ
	//####################################################
	@Override
	public void setCryptKey(String sKeyWord) {
		this.sKeyWord= sKeyWord;
	}
	
	@Override
	public String getCryptKey() {
		return this.sKeyWord;
	}
	
	@Override
	public int getSubtype() {
		return CryptAlgorithmMaintypeZZZ.TypeZZZ.VIGENERE.ordinal();
	}
	
	@Override
	public String encrypt(String sInput) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			int[] iaInput = UnicodeZZZ.toIntArray(sInput);
			int[] iaEncrypted = this.encrypt(iaInput);
			this.setEncryptedValues(iaEncrypted);
			
		    sReturn = CharArrayZZZ.toString(iaEncrypted);
		}//end main:
		return sReturn;
	}
	@Override
	public String decrypt(String sInput) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			int[] iaInput = UnicodeZZZ.toIntArray(sInput);
			int[] iaDecrypted = this.decrypt(iaInput);
			this.setDecryptedValues(iaDecrypted);
		    sReturn = CharArrayZZZ.toString(iaDecrypted);
		}//end main:
		return sReturn;
	}
	
	
	
	//+++ nur wichtig für ROT-Verfahren, werden wg. Interface uebernommen.
	@Override
	public void setCryptNumber(int iCryptKey) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setCharacterPool(String sCharacterPool) {
		// TODO Auto-generated method stub
		
	}
}
