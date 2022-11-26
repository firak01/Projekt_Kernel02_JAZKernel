package basic.zBasic.util.crypt.encode;

import base.files.DateiUtil;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.file.FileEasyZZZ;

public abstract class AbstractVigenereZZZ {
	private String sFilePath=null;
	private String sKeyWord=null;
	
	private DateiUtil dateiOriginal=null;
	private DateiUtil dateiEncrypted=null;
	private int[] iaEncrypted=null;
	private int[] iaDecrypted=null;
	
	public AbstractVigenereZZZ() {		
	}
	
	public AbstractVigenereZZZ(String sFilePath, String sSchluesselWort) {
		this.setFilePath(sFilePath);
		this.setKeyWord(sSchluesselWort);
	}
		
	//+++++++ Getter / Setter
	public void setFilePath(String sFilePath) {
		this.sFilePath = sFilePath;
	}
	public String getFilePath() {
		return this.sFilePath;
	}
	
	public void setKeyWord(String sKeyWord) {
		this.sKeyWord= sKeyWord;
	}
	public String getKeyWord() {
		return this.sKeyWord;
	}
	
//###########################
//### Verschluesselungsmethoden
//###########################
	public abstract boolean encrypt() throws ExceptionZZZ;
	
	public void setFileOriginal(DateiUtil datei) {
		this.dateiOriginal = datei;
	}
	public DateiUtil getFileOriginal() {
		return this.dateiOriginal;
	}				
	public int[] getEncryptedValuesAsInt() {
		return this.iaEncrypted;
	}
	public void setEncryptedValues(int[] iaCrypted) {
		this.iaEncrypted = iaCrypted;
	}
	
//##########################
//### Entschluesselungsmethoden
//##########################	
	public abstract boolean decrypt() throws ExceptionZZZ;

	public void setFileEncrypted(DateiUtil datei) {
		this.dateiEncrypted = datei;
	}
	public DateiUtil getFileEncrypted() {
		if(this.dateiEncrypted==null) {
			this.dateiEncrypted = this.dateiOriginal;
		}
		return this.dateiEncrypted;
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
}
