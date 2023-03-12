package basic.zBasic.util.crypt.code;

import basic.zBasic.ExceptionZZZ;

public interface ICryptUserZZZ {
	//public ICryptZZZ createAlgorithmType(String sCipher) throws ExceptionZZZ;
	public ICryptZZZ getAlgorithmType() throws ExceptionZZZ;
	public void setAlgorithmType(ICryptZZZ objCrypt);
}
