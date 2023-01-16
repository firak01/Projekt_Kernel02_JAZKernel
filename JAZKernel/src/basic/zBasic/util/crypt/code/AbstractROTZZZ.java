package basic.zBasic.util.crypt.code;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ObjectZZZ;

public abstract class AbstractROTZZZ extends ObjectZZZ implements IROTZZZ{
	private static final long serialVersionUID = 1L;
	private int iCryptKey=-1;
	
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
	public void setCharacterPool(String sCharacterPool) {
		//Nur wg. ICrypZZZ
	}

	@Override
	public void setCryptKey(String sCryptKey) {
		//Nur wg. ICryptZZZ
	}
}
