package basic.zBasic.util.crypt;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ObjectZZZ;

public abstract class AbstractROTZZZ extends ObjectZZZ implements ICryptZZZ{
	private int iCryptKey=-1; //Das ist der Wert, um den die ROT Verschiebung passiert
	
	public abstract String encrypt(String sInput) throws ExceptionZZZ;
	public abstract String decrypt(String sInput) throws ExceptionZZZ;
	
	public AbstractROTZZZ() {
		super();
	}
	
	public void setCryptKey(int iCryptKey) {
		this.iCryptKey = iCryptKey;
	}
	public int getCryptKey() {
		return this.iCryptKey;
	}
}
