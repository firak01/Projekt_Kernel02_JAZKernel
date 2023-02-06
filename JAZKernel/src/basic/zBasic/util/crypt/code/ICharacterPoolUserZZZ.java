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
		//stehen schon in ICryptZZZ
		//public String getCharacterPool();
		//public void setCharacterPool(String sCharacterPool);	
		//public String getCryptKey();
		//public void setCryptKey(String sCryptKey); //Manche Algorithmen benoetigen einen String, z.B. alle Vigenere Verfahren.

		public ArrayListExtendedZZZ<CharacterExtendedZZZ>getCharacterPoolList() throws ExceptionZZZ;
		

		public String getCharacterPoolBase();
		public void setCharacterPoolBase(String sCharacterPool);
				
		public String getCharacterPoolAdditional();
		public void setCharacterPoolAdditional(String sCharacterPool);
		
		
		
		public int[] getEncryptedCharacterPoolPosition();
		public void setEncryptedCharacterPoolPosition(int[]iaPoolPosition);

		public int[] getDecryptedCharacterPoolPosition();
		public void setDecryptedCharacterPoolPosition(int[]iaPoolPosition);
	
		//Verlagert nach ICryptZZZ, wie auch der CharacterPool
		//public CharacterExtendedZZZ getCharacterMissingReplacment() throws ExceptionZZZ;
		//public void setCharacterMissingReplacement(CharacterExtendedZZZ objCharacterMissingReplacement);
}
