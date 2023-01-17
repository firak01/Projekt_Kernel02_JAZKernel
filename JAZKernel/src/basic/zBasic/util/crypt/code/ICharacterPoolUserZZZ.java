package basic.zBasic.util.crypt.code;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractList.ArrayListExtendedZZZ;
import basic.zBasic.util.datatype.character.CharacterExtendedZZZ;

public interface ICharacterPoolUserZZZ extends ICharacterPoolUserConstantZZZ{
	   //Merke: IFlagUser ermöglicht dann Methoden um <Z:FlagControl> zu setzen
		//Merke: Diese Flags beziehen sich auf den Characterpool, d.h. nur wichtig in IVigenereNnZZZ
		//public enum FLAGZ{
		//	USENUMERIC,USEUPPERCASE,USELOWERCASE,USECHARACTERPOOL
		//}	
		
		//damit muss man nicht mehr tippen hinter dem enum .name()
		public boolean getFlag(ICharacterPoolUserZZZ.FLAGZ objEnumFlag);
		public void setFlag(ICharacterPoolUserZZZ.FLAGZ objEnumFlag, boolean bFlagValue);
		
		//Besonderheit bei Nn ist der CharacterPool
		public ArrayListExtendedZZZ<CharacterExtendedZZZ>getCharacterPoolList() throws ExceptionZZZ;
		public void setCharacterPoolList(ArrayListExtendedZZZ<CharacterExtendedZZZ> listasCharacterPool);
		
		public String getCharacterPool();
		public void setCharacterPool(String sCharacterPool);						
		
		public int[] getEncryptedCharacterPoolPosition();
		public void setEncryptedCharacterPoolPosition(int[]iaPoolPosition);

		public int[] getDecryptedCharacterPoolPosition();
		public void setDecryptedCharacterPoolPosition(int[]iaPoolPosition);
}
