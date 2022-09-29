package basic.zBasic.util.crypt;

import basic.zBasic.ExceptionZZZ;
import basic.zKernel.flag.IFlagUserZZZ;

public interface ICryptZZZ extends IFlagUserZZZ{
	public enum FLAGZ{
		USENUMERIC,USEUPPERCASE
	}	
	
	public String encrypt(String sInput) throws ExceptionZZZ;
	public String decrypt(String sInput) throws ExceptionZZZ;
	
	//Methoden um <Z:KeyNumber> Werte zu setzen
	public int getCryptNumber();
	public void setCryptNumber(int iCryptKey); //Manche Algorithmen benötigen eine Zahl, z.B. alle ROT (=rotierenden) Algorithmen.
	
	//Merke: IFlagUser ermöglicht dann Methoden um <Z:FlagControl> zu setzen
	
	//Methoden um <Z:CharacterPool> Werte zu setzen
	public String getCharacterPool();
	public void setCharacterPool(String sCharacterPool);
	
}
