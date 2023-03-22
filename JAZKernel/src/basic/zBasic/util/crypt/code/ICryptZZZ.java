package basic.zBasic.util.crypt.code;

import java.util.EnumSet;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractList.ArrayListExtendedZZZ;
import basic.zBasic.util.crypt.code.CryptAlgorithmMappedValueZZZ.CipherTypeZZZ;
import basic.zBasic.util.datatype.character.CharacterExtendedZZZ;
import basic.zKernel.flag.IFlagUserZZZ;

public interface ICryptZZZ extends IFlagUserZZZ{	
	//Merke: IFlagUser ermöglicht dann Methoden um <Z:FlagControl> zu setzen	
//	public enum FLAGZ{
//		USENUMERIC,USEUPPERCASE,USELOWERCASE,USECHARACTERPOOL
//	}
	
	public String encrypt(String sInput) throws ExceptionZZZ;
	public String decrypt(String sInput) throws ExceptionZZZ;
	public int[] getDecryptedValuesAsInt();
	public void setDecryptedValues(int[] iaCrypted);
	public int[] getOriginalValuesAsInt();
	public void setOriginalValues(int[] iaOriginal);
	
	public Enum<?> getCipherTypeAsEnum();    //Also der Eintrag aus der Enumeration in CryptAlgorithmMappedValue
	public CipherTypeZZZ getCipherType();    //Also der Eintrag aus der Enumeration in CryptAlgorithmMappedValue
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
	
	//############################################################
	//Methoden für Vigenere - Verfahren, müssten sonst in ICharacterPoolUserZZZ
	public String getCryptKey();
	public void setCryptKey(String sCryptKey); //Manche Algorithmen benoetigen einen String, z.B. alle Vigenere Verfahren.
	
	//Methoden um <Z:CharacterPool> Werte zu setzen
	//Wichtig fuer ROTnn - Verschluesselung
	public ArrayListExtendedZZZ<CharacterExtendedZZZ> getCharacterPoolList() throws ExceptionZZZ;
	public void setCharacterPoolList(ArrayListExtendedZZZ<CharacterExtendedZZZ> listasCharacterPool);
	
	public String getCharacterPool() throws ExceptionZZZ;
	//Merke: Kein Setzen... dies wird aus CharacterPoolList berechnet
	
	public String getCharacterPoolBase();
	public void setCharacterPoolBase(String sCharacterPoolBase);
	
	public String getCharacterPoolAdditional();
	public void setCharacterPoolAdditional(String sCharacterPoolAdditional);
	
	//Falls ein Zeichen nicht im CharacterPool enthalten ist.
	public CharacterExtendedZZZ getCharacterMissingReplacment() throws ExceptionZZZ;
	public void setCharacterMissingReplacement(CharacterExtendedZZZ objCharacterMissingReplacement);
	
}
