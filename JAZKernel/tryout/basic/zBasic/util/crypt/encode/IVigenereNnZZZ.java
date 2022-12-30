package basic.zBasic.util.crypt.encode;

import base.files.DateiUtil;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractList.ArrayListExtendedZZZ;
import basic.zBasic.util.crypt.ICryptUIZZZ;
import basic.zBasic.util.crypt.ICryptZZZ;
import basic.zBasic.util.datatype.character.CharacterExtendedZZZ;
import basic.zKernel.flag.IFlagUserZZZ;

public interface IVigenereNnZZZ extends IVigenereZZZ{
	//Merke: IFlagUser ermöglicht dann Methoden um <Z:FlagControl> zu setzen
	//Merke: Diese Flags beziehen sich auf den Characterpool, d.h. nur wichtig in IVigenereNnZZZ
	public enum FLAGZ{
		USENUMERIC,USEUPPERCASE,USELOWERCASE
	}	
	
	//damit muss man nicht mehr tippen hinter dem enum .name()
	public boolean getFlag(IVigenereNnZZZ.FLAGZ objEnumFlag);
	public void setFlag(IVigenereNnZZZ.FLAGZ objEnumFlag, boolean bFlagValue);
	
	//Besonderheit bei Nn ist der CharacterPool
	public ArrayListExtendedZZZ<CharacterExtendedZZZ>getCharacterPoolList() throws ExceptionZZZ;
	public void setCharacterPoolList(ArrayListExtendedZZZ<CharacterExtendedZZZ> listasCharacterPool);
	
	public String getCharacterPool();
	public void setCharacterPool(String sCharacterPool);
}
