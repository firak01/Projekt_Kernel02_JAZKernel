package basic.zBasic.util.crypt.code;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ObjectZZZ;
import basic.zBasic.util.datatype.character.CharacterExtendedZZZ;

public abstract class AbstractROTZZZ extends ObjectZZZ implements IROTZZZ{
	private static final long serialVersionUID = 1L;
	private int iCryptKey=-1;
	private int[] iaDecrypted=null;
	private int[] iaOriginal=null;
	
	
	
	public abstract String encrypt(String sInput) throws ExceptionZZZ;
	public abstract String decrypt(String sInput) throws ExceptionZZZ;
	
	public AbstractROTZZZ() {
		super();
	}
	
	public AbstractROTZZZ(String sFlagControl) throws ExceptionZZZ {
		super(sFlagControl);
	}
	
	@Override
	public int getSubtype() {
		return CryptAlgorithmMaintypeZZZ.TypeZZZ.ROT.ordinal();
	}
		
	@Override
	public void setCryptNumber(int iCryptKey) {
		this.iCryptKey = iCryptKey;
	}
	@Override
	public int getCryptNumber() {
		return this.iCryptKey;
	}
	
	@Override
	public int[] getDecryptedValuesAsInt() {
		return this.iaDecrypted;
	}
	
	@Override
	public void setDecryptedValues(int[] iaCrypted) {
		this.iaDecrypted = iaCrypted;
	}
	
	@Override
	public int[] getOriginalValuesAsInt() {
		return this.iaOriginal;
	}
	
	@Override
	public void setOriginalValues(int[] iaOriginal) {
		this.iaOriginal = iaOriginal;
	}
	
	
	//### Methoden werden zwar z.B. nur fuer Vigenere Verschluesselung, bzw. fuer Verschluesselung mit CharacterPool gebraucht.
	//    Um den Einsatz von ICrypt - Objekten zu standardiesieren, hier notwendig.
	@Override
	public void setCryptKey(String sCryptKey) {
		//Nur wg. ICryptZZZ
	}
	
	@Override
	public String getCryptKey() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getCharacterPool() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setCharacterPool(String sCharacterPool) {
		// TODO Auto-generated method stub		
	}
	
	@Override
	public CharacterExtendedZZZ getCharacterMissingReplacment() throws ExceptionZZZ {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setCharacterMissingReplacement(CharacterExtendedZZZ objCharacterMissingReplacement) {
		// TODO Auto-generated method stub		
	}
}
