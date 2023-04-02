package basic.zBasic.util.crypt.code;

import base.files.DateiUtil;
import basic.zBasic.ExceptionZZZ;
import basic.zKernel.flag.IFlagZUserZZZ;

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
	public DateiUtil getFileOriginal();
	public void setFileOriginal(DateiUtil objDatei);
	
	public DateiUtil getFileEncrypted();
	public void setFileEncrypted(DateiUtil objDatei);
	
	public DateiUtil getFileDecrypted();
	public void setFileDecrypted(DateiUtil objDatei);
		
	public boolean isFileOriginalEncrypted();
	public void isFileOriginalEncrypted(boolean bFileOriginalEncryted);
	
	public int[] readOriginalValuesAsInt();	
}
