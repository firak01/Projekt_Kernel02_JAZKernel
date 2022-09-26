package basic.zBasic.util.crypt;

import basic.zBasic.ExceptionZZZ;

public interface ICryptZZZ {
	public enum FLAGZ{
		USENUMERIC,USEUPPERCASE
	}	
	
	public String encrypt(String sInput) throws ExceptionZZZ;
	public String decrypt(String sInput) throws ExceptionZZZ;
}
