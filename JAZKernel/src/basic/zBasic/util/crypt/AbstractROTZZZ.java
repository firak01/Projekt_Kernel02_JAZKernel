package basic.zBasic.util.crypt;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ObjectZZZ;

public abstract class AbstractROTZZZ extends ObjectZZZ implements IROTZZZ{
	private int iCryptKey=-1;
	private String sCharacterPool=null; //Das ist ein Wert, der für eine ROT Verschiebung gültige Zeichen enthält
	
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
	public String getCharacterPool() {
		return this.sCharacterPool;
	}
	@Override
	public void setCharacterPool(String sCharacterPool) {
		this.sCharacterPool = sCharacterPool;
	}	
	
	
	//nur wichtig für Vigenere Verschluesselung
	@Override
	public void setCryptKey(String sCryptKey) {
		// TODO Auto-generated method stub
		
	}
}
