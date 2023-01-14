package basic.zBasic.util.crypt.code;

import base.files.DateiUtil;
import basic.zBasic.ExceptionZZZ;
import basic.zKernel.flag.IFlagUserZZZ;

public interface IVigenereZZZ extends ICryptZZZ, ICryptUIZZZ{
	//Merke: IFlagUser ermöglicht dann Methoden um <Z:FlagControl> zu setzen
	//Merke: Diese Flags beziehen sich auf den Characterpool, d.h. nur wichtig in IVigenereNnZZZ
//	public enum FLAGZ{
//		USENUMERIC,USEUPPERCASE,USELOWERCASE
//	}	
	
	
	public int[] decrypt(int[]p) throws ExceptionZZZ;
	public int[] getEncryptedValuesAsInt();
	
	//##########################################################
	//Methoden zum Dateihandling
	public DateiUtil getFileOriginal();
	
	
	//###########################################################
	//Methoden für Vigenere - Verfahren
	public String getCryptKey();
	public void setCryptKey(String sCryptKey);
}
