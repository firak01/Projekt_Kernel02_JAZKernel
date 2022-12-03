package basic.zBasic.util.crypt;

import basic.zBasic.ExceptionZZZ;
import basic.zKernel.flag.IFlagUserZZZ;

public interface ICryptZZZ extends IFlagUserZZZ{	
	
	public String encrypt(String sInput) throws ExceptionZZZ;
	public String decrypt(String sInput) throws ExceptionZZZ;
	public int getSubtype(); //Hiermit wird festgelegt, welches Unterverfahren verwendet werden soll (also auch welche weiteren Parameter benötigt werden, z.B. KeyNumber oder KeyString)
	
	//#########################################################
	//### Methoden, die Klassen verwendet werden, die ICryptZZZ nutzen
	//### und daher nicht in dem Interface des eingentlichen Verschlüsselungsalgorithmus bleiben können.
	//### Genutzt z.B. durch KernelEncryptionIniSolverZZZ
	//#########################################################
		
	//###########################################################
	//Methoden für ROT - Verfahren
	//Methoden um <Z:KeyNumber> Werte zu setzen
	//Wichtig für alle ROT - Verschluesselungen
	public void setCryptNumber(int iCryptKey); //Manche Algorithmen benoetigen eine Zahl, z.B. alle ROT (=rotierenden) Algorithmen.
	
	//Methoden um <Z:CharacterPool> Werte zu setzen
	//Wichtig fuer ROTnn - Verschluesselung
	public void setCharacterPool(String sCharacterPool);
	
	
	//############################################################
	//Methoden für Vigenere - Verfahren
	public void setCryptKey(String sCryptKey); //Manche Algorithmen benoetigen einen String, z.B. alle Vigenere Verfahren.
	
}
