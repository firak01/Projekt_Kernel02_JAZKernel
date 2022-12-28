package basic.zBasic.util.crypt.encode;

import base.files.DateiUtil;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.crypt.ICryptUIZZZ;
import basic.zBasic.util.crypt.ICryptZZZ;
import basic.zKernel.flag.IFlagUserZZZ;

public interface IVigenereZZZ extends ICryptZZZ, ICryptUIZZZ{
	//Merke: IFlagUser ermöglicht dann Methoden um <Z:FlagControl> zu setzen
	//Merke: Diese Flags beziehen sich auf den Characterpool, d.h. nur wichtig in IVigenereNnZZZ
//	public enum FLAGZ{
//		USENUMERIC,USEUPPERCASE,USELOWERCASE
//	}	
	
	
	public int[] decrypt(int[]p);
	public int[] getEncryptedValuesAsInt();
	
	//##########################################################
	//Methoden zum Dateihandling
	public DateiUtil getFileOriginal();
	
	
	//###########################################################
	//Methoden für Vigenere - Verfahren
	public String getCryptKey();
	public void setCryptKey(String sCryptKey);
}
