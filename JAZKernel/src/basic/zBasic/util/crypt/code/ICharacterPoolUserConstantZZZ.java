package basic.zBasic.util.crypt.code;

public interface ICharacterPoolUserConstantZZZ extends IROTUserConstantZZZ{
	   //Merke: IFlagUser ermöglicht dann Methoden um <Z:FlagControl> zu setzen
		//Merke: Diese Flags beziehen sich auf den Characterpool, d.h. teilweise nur wichtig in IVigenereNnZZZ
	    //       Die Flags werden aber auch teilweise in den ROTnumeric Algorithmen verwendet 
		public enum FLAGZ{
			USESTRATEGY_CHARACTERPOOL, USEADDITIONALCHARACTER,USENUMERIC,USEUPPERCASE,USELOWERCASE
		}	
		
		public static char cCHARACTER_MISSING_REPLACEMENT_DEFAULT = '¦'; // ein anderer schoener Charakter wäre '¶'
		
		
}
