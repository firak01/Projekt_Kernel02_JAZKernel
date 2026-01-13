package basic.zBasic.util.crypt.code;

import java.util.EnumSet;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractList.ArrayListZZZ;
import basic.zBasic.util.crypt.code.CryptAlgorithmMappedValueZZZ.CipherTypeZZZ;
import basic.zBasic.util.datatype.character.CharacterExtendedZZZ;
import basic.zKernel.flag.IFlagZEnabledZZZ;

public interface ICryptZZZ extends IFlagZEnabledZZZ{	
	//Merke: IFlagUser ermöglicht dann Methoden um <Z:FlagControl> zu setzen	
//	public enum FLAGZ{
//		USENUMERIC,USEUPPERCASE,USELOWERCASE,USECHARACTERPOOL
//	}
	
	//WICHTIG: Hier kein setFlag(enum) zur Verfügung stellen, da z.B. CharacterPoolUser dann auch bei den ROT Verschluesselungen implementiert werden muesste.
	
	
	public String encrypt(String sInput) throws ExceptionZZZ;
	public String decrypt(String sInput) throws ExceptionZZZ;
	public int[] getDecryptedValuesAsInt() throws ExceptionZZZ;
	public void setDecryptedValues(int[] iaCrypted) throws ExceptionZZZ;
	public int[] getOriginalValuesAsInt(Object newParam) throws ExceptionZZZ;
	public void setOriginalValues(int[] iaOriginal) throws ExceptionZZZ;
	
	public Enum<?> getCipherTypeAsEnum() throws ExceptionZZZ;    //Also der Eintrag aus der Enumeration in CryptAlgorithmMappedValue
	public CipherTypeZZZ getCipherType() throws ExceptionZZZ;    //Also der Eintrag aus der Enumeration in CryptAlgorithmMappedValue
	public int getSubtype() throws ExceptionZZZ; //Hiermit wird festgelegt, welches Unterverfahren verwendet werden soll (also auch welche weiteren Parameter benötigt werden, z.B. KeyNumber oder KeyString)
	
	
	
	//#########################################################
	//### Methoden, die Klassen verwendet werden, die ICryptZZZ nutzen
	//### und daher nicht in dem Interface des eingentlichen Verschlüsselungsalgorithmus bleiben können.
	//### Genutzt z.B. durch KernelEncryptionIniSolverZZZ
	//#########################################################
		
	//###########################################################
	//Methoden für ROT - Verfahren
	//Methoden um <Z:KeyNumber> Werte zu setzen
	//Wichtig für alle ROT - Verschluesselungen
	public int getCryptNumber() throws ExceptionZZZ;
	public void setCryptNumber(int iCryptKey) throws ExceptionZZZ; //Manche Algorithmen benoetigen eine Zahl, z.B. alle ROT (=rotierenden) Algorithmen.
	
	//############################################################
	//Methoden für Vigenere - Verfahren, müssten sonst in ICharacterPoolUserZZZ
	public String getCryptKey() throws ExceptionZZZ;
	public void setCryptKey(String sCryptKey) throws ExceptionZZZ; //Manche Algorithmen benoetigen einen String, z.B. alle Vigenere Verfahren.
	
	//Methoden um <Z:CharacterPool> Werte zu setzen
	//Wichtig fuer ROTnn - Verschluesselung
	public ArrayListZZZ<CharacterExtendedZZZ> getCharacterPoolList() throws ExceptionZZZ;
	public void setCharacterPoolList(ArrayListZZZ<CharacterExtendedZZZ> listasCharacterPool) throws ExceptionZZZ;
	
	public String getCharacterPool() throws ExceptionZZZ;
	//Merke: Kein Setzen... dies wird aus CharacterPoolList berechnet
	
	public String getCharacterPoolBase() throws ExceptionZZZ;
	public void setCharacterPoolBase(String sCharacterPoolBase) throws ExceptionZZZ;
	
	public String getCharacterPoolAdditional() throws ExceptionZZZ;
	public void setCharacterPoolAdditional(String sCharacterPoolAdditional) throws ExceptionZZZ;
	
	//Falls ein Zeichen nicht im CharacterPool enthalten ist.
	public CharacterExtendedZZZ getCharacterMissingReplacment() throws ExceptionZZZ;
	public void setCharacterMissingReplacement(CharacterExtendedZZZ objCharacterMissingReplacement) throws ExceptionZZZ;
}
