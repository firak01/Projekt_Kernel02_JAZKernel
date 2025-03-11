package basic.zBasic.util.crypt.code;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractList.ArrayListExtendedZZZ;
import basic.zBasic.util.datatype.character.CharacterExtendedZZZ;

public interface IROTUserZZZ extends IROTEnabledConstantZZZ{
	   //Merke: IFlagUser ermöglicht dann Methoden um <Z:FlagControl> zu setzen
		//Merke: Diese Flags beziehen sich auf den Characterpool, d.h. nur wichtig in IVigenereNnZZZ
		//public enum FLAGZ{
		//	USENUMERIC,USEUPPERCASE,USELOWERCASE,USECHARACTERPOOL
		//}	
		
		//damit muss man nicht mehr tippen hinter dem enum .name()
		public boolean getFlag(IROTUserZZZ.FLAGZ objEnumFlag);
		public boolean setFlag(IROTUserZZZ.FLAGZ objEnumFlag, boolean bFlagValue) throws ExceptionZZZ;
		public boolean[] setFlag(IROTUserZZZ.FLAGZ[] objaEnumFlag, boolean bFlagValue) throws ExceptionZZZ;
		public boolean proofFlagExists(IROTUserZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ;
}
