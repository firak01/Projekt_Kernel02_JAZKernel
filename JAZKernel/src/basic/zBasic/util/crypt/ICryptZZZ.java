package basic.zBasic.util.crypt;

import basic.zBasic.ExceptionZZZ;
import basic.zKernel.flag.IFlagUserZZZ;

public interface ICryptZZZ extends IFlagUserZZZ{
	//Merke: IFlagUser ermöglicht dann Methoden um <Z:FlagControl> zu setzen
	
	public enum FLAGZ{
		USENUMERIC,USEUPPERCASE,USELOWERCASE
	}	
	
	public String encrypt(String sInput) throws ExceptionZZZ;
	public String decrypt(String sInput) throws ExceptionZZZ;
	public int getSubtype(); //Hiermit wird festgelegt, welches Unterverfahren verwendet werden soll (also auch welche weiteren Parameter benötigt werden, z.B. KeyNumber oder KeyString)
	
	//###########################################################
	//Methoden für Vigenere - Verfahren
	public String getCryptKey();
	public void setCryptKey(String sCryptKey);
	
	
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
