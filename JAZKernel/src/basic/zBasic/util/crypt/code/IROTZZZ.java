package basic.zBasic.util.crypt.code;

import basic.zBasic.ExceptionZZZ;
import basic.zKernel.flag.IFlagUserZZZ;

public interface IROTZZZ extends ICryptZZZ{
	//Merke: IFlagUser ermöglicht dann Methoden um <Z:FlagControl> zu setzen
	
	public enum FLAGZ{
		USENUMERIC,USEUPPERCASE,USELOWERCASE,USECHARACTERPOOL
	}	
	
	//###########################################################
	//Methoden für ROT - Verfahren
	//Methoden um <Z:KeyNumber> Werte zu setzen
	//Wichtig für alle ROT - Verschluesselungen
	public int getCryptNumber();
	public void setCryptNumber(int iCryptKey); //Manche Algorithmen benötigen eine Zahl, z.B. alle ROT (=rotierenden) Algorithmen.
	
	//Methoden um <Z:CharacterPool> Werte zu setzen
	//Wichtig fuer ROTnn - Verschluesselung
	public String getCharacterPool();
	public void setCharacterPool(String sCharacterPool);
}
