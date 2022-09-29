package basic.zBasic.util.crypt;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ObjectZZZ;

public abstract class AbstractROTZZZ extends ObjectZZZ implements ICryptZZZ{
	private int iCryptKey=-1; //Das ist der Wert, um den die ROT Verschiebung passiert
	private String sCharacterPool=null; //Das ist ein Wert, der für eine ROT Verschiebung gültige Zeichen enthält
	
	public abstract String encrypt(String sInput) throws ExceptionZZZ;
	public abstract String decrypt(String sInput) throws ExceptionZZZ;
	
	public AbstractROTZZZ() {
		super();
	}
	
	public void setCryptNumber(int iCryptKey) {
		this.iCryptKey = iCryptKey;
	}
	public int getCryptNumber() {
		return this.iCryptKey;
	}
	
	public String getCharacterPool() {
		return this.sCharacterPool;
	}
	public void setCharacterPool(String sCharacterPool) {
		this.sCharacterPool = sCharacterPool;
	}
}
