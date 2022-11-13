package basic.zBasic.util.crypt;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ObjectZZZ;

public abstract class AbstractVigenereZZZ extends ObjectZZZ implements ICryptZZZ{
	private String sCryptKey=null;
	
	public abstract String encrypt(String sInput) throws ExceptionZZZ;
	public abstract String decrypt(String sInput) throws ExceptionZZZ;
	
	public AbstractVigenereZZZ() {
		super();
	}
	
	public AbstractVigenereZZZ(String sFlagControl) throws ExceptionZZZ {
		super(sFlagControl);
	}
	
	@Override
	public int getSubtype() {
		return CryptAlgorithmMaintypeZZZ.TypeZZZ.VIGENERE.ordinal();
	}

	
	
	@Override
	public void setCryptKey(String sCryptKey) {
		this.sCryptKey = sCryptKey;
	}
	@Override
	public String getCryptKey() {
		return this.sCryptKey;
	}
	
	
	//Aus Interface ICryptZZZ, aber nur wichtig für ROT-Verfahren
	@Override
	public int getCryptNumber() {
		return 0;
	}

	@Override
	public void setCryptNumber(int iCryptKey) {
		// nix		
	}

	@Override
	public String getCharacterPool() {		
		return null;
	}

	@Override
	public void setCharacterPool(String sCharacterPool) {
		//nix
	}
}
