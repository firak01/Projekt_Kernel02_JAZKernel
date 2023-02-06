package basic.zBasic.util.crypt.code;

public interface ICharacterPoolUserConstantZZZ {
	   //Merke: IFlagUser ermöglicht dann Methoden um <Z:FlagControl> zu setzen
		//Merke: Diese Flags beziehen sich auf den Characterpool, d.h. nur wichtig in IVigenereNnZZZ
		public enum FLAGZ{
			USENUMERIC,USEUPPERCASE,USELOWERCASE,USEADDITIONALCHARACTER,USECHARACTERPOOL
		}	
		
		public static char cCHARACTER_MISSING_REPLACEMENT_DEFAULT = '¦'; // ein anderer schoener Charakter wäre '¶'
		
		
}
