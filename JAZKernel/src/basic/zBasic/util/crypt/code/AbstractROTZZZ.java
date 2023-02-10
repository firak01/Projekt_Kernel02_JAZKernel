package basic.zBasic.util.crypt.code;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ObjectZZZ;
import basic.zBasic.util.datatype.character.CharacterExtendedZZZ;

public abstract class AbstractROTZZZ extends ObjectZZZ implements IROTZZZ,IROTUserZZZ{
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
	
	@Override
	public boolean getFlag(IROTUserConstantZZZ.FLAGZ objEnumFlag) {		
		return this.getFlag(objEnumFlag.name());
	}
	@Override
	public void setFlag(IROTUserConstantZZZ.FLAGZ objEnumFlag, boolean bFlagValue) {
		this.setFlag(objEnumFlag.name(), bFlagValue);
	}
	
	
	//### Methoden werden zwar z.B. nur fuer Vigenere Verschluesselung, bzw. fuer Verschluesselung mit CharacterPool gebraucht.
	//    Um den Einsatz von ICrypt - Objekten zu standardiesieren, hier notwendig.
	@Override
	public void setCryptKey(String sCryptKey) {
		//Nur wg. ICryptZZZ
	}
	
	@Override
	public String getCryptKey() {		
		return null;
	}
	@Override
	public String getCharacterPool() throws ExceptionZZZ{
		return null; //Nur wg. ICryptZZZ
	}
	
	@Override
	public void setCharacterPoolBase(String sCharacterPoolBase) {
		//Nur wg. ICryptZZZ
	}
	
	@Override
	public String getCharacterPoolBase() {
		return null; //Nur wg. ICryptZZZ
	}
	
	@Override
	public void setCharacterPoolAdditional(String sCharacterPoolAdditional) {
		//Nur wg. ICryptZZZ
	}
	
	@Override
	public String getCharacterPoolAdditional() {
		return null; //Nur wg. ICryptZZZ
	}
		
	@Override
	public CharacterExtendedZZZ getCharacterMissingReplacment() throws ExceptionZZZ{
		return null; //Nur wg. ICryptZZZ
	}

	@Override
	public void setCharacterMissingReplacement(CharacterExtendedZZZ objCharacterMissingReplacement) {
		//Nur wg. ICryptZZZ
	}
}
