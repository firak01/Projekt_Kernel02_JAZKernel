package basic.zBasic.util.crypt.code;

import base.files.DateiUtil;
import basic.zBasic.ExceptionZZZ;
import basic.zKernel.flag.IFlagZEnabledZZZ;

public interface ICryptUIZZZ{	
	
	//Merke: Kein Eingabestring, da die Werte kommen sollen:
	//       a) aus der Eingabe ueber Konsole
	//       b) aus der Dateiauswahl per Dialog
	//Merke2: Dementsprechend auch keinen String zurückliefern, 
	//        da die Werte ggfs. per Datei oder Konsole zurückgeliefert werden.
	public boolean encryptUI() throws ExceptionZZZ;
	public boolean decryptUI() throws ExceptionZZZ;
	
	//##########################################################
	//Methoden zum Dateihandling
	public DateiUtil getFileOriginal() throws ExceptionZZZ;
	public void setFileOriginal(DateiUtil objDatei) throws ExceptionZZZ;
	
	public DateiUtil getFileEncrypted() throws ExceptionZZZ;
	public void setFileEncrypted(DateiUtil objDatei) throws ExceptionZZZ;
	
	public DateiUtil getFileDecrypted() throws ExceptionZZZ;
	public void setFileDecrypted(DateiUtil objDatei) throws ExceptionZZZ;
		
	public boolean isFileOriginalEncrypted() throws ExceptionZZZ;
	public void isFileOriginalEncrypted(boolean bFileOriginalEncryted) throws ExceptionZZZ;
	
	public int[] readOriginalValuesAsInt() throws ExceptionZZZ;	
}
