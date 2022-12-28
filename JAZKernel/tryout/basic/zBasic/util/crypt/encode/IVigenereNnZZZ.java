package basic.zBasic.util.crypt.encode;

import base.files.DateiUtil;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.crypt.ICryptUIZZZ;
import basic.zBasic.util.crypt.ICryptZZZ;
import basic.zKernel.flag.IFlagUserZZZ;

public interface IVigenereNnZZZ extends IVigenereZZZ{
	//Merke: IFlagUser ermöglicht dann Methoden um <Z:FlagControl> zu setzen
	//Merke: Diese Flags beziehen sich auf den Characterpool, d.h. nur wichtig in IVigenereNnZZZ
	public enum FLAGZ{
		USENUMERIC,USEUPPERCASE,USELOWERCASE
	}	
}
