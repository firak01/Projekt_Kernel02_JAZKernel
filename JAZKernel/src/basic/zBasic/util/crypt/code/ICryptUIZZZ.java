package basic.zBasic.util.crypt.code;

import basic.zBasic.ExceptionZZZ;
import basic.zKernel.flag.IFlagUserZZZ;

public interface ICryptUIZZZ{	
	
	//Merke: Kein Eingabestring, da die Werte kommen sollen:
	//       a) aus der Eingabe ueber Konsole
	//       b) aus der Dateiauswahl per Dialog
	//Merke2: Dementsprechend auch keinen String zurückliefern, 
	//        da die Werte ggfs. per Datei oder Konsole zurückgeliefert werden.
	public boolean encryptUI() throws ExceptionZZZ;
	public boolean decryptUI() throws ExceptionZZZ;
}
