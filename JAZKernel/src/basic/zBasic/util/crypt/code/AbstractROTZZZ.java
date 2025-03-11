package basic.zBasic.util.crypt.code;

import java.util.EnumSet;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.AbstractObjectWithFlagZZZ;
import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.abstractList.ArrayListExtendedZZZ;
import basic.zBasic.util.crypt.code.CryptAlgorithmMaintypeZZZ.TypeZZZ;
import basic.zBasic.util.datatype.character.CharacterExtendedZZZ;

public abstract class AbstractROTZZZ extends AbstractObjectWithFlagZZZ implements IROTZZZ,IROTUserZZZ{
	private static final long serialVersionUID = 1L;
	private int iCryptKey=-1;
	private int[] iaDecrypted=null;
	private int[] iaOriginal=null;
	
	
	
	public abstract String encrypt(String sInput) throws ExceptionZZZ;
	public abstract String decrypt(String sInput) throws ExceptionZZZ;
	
	public AbstractROTZZZ() {
		super();
	}
	
	public AbstractROTZZZ(String sFlagControl) throws ExceptionZZZ {
		super(sFlagControl);
	}
	
	@Override
	public int getSubtype() {
		return CryptAlgorithmMaintypeZZZ.TypeZZZ.ROT.ordinal();
	}
			
	@Override
	public void setCryptNumber(int iCryptKey) {
		this.iCryptKey = iCryptKey;
	}
	@Override
	public int getCryptNumber() {
		return this.iCryptKey;
	}
	
	@Override
	public int[] getDecryptedValuesAsInt() {
		return this.iaDecrypted;
	}
	
	@Override
	public void setDecryptedValues(int[] iaCrypted) {
		this.iaDecrypted = iaCrypted;
	}
	
	@Override
	public int[] getOriginalValuesAsInt() {
		return this.iaOriginal;
	}
	
	@Override
	public void setOriginalValues(int[] iaOriginal) {
		this.iaOriginal = iaOriginal;
	}
	
	@Override
	public boolean getFlag(IROTEnabledConstantZZZ.FLAGZ objEnumFlag) {		
		return this.getFlag(objEnumFlag.name());
	}
	
	@Override
	public boolean setFlag(IROTEnabledConstantZZZ.FLAGZ objEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		return this.setFlag(objEnumFlag.name(), bFlagValue);
	}
	
	@Override
	public boolean[] setFlag(IROTEnabledConstantZZZ.FLAGZ[] objaEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		boolean[] baReturn=null;
		main:{
			if(!ArrayUtilZZZ.isNull(objaEnumFlag)) {
				baReturn = new boolean[objaEnumFlag.length];
				int iCounter=-1;
				for(IROTEnabledConstantZZZ.FLAGZ objEnumFlag:objaEnumFlag) {
					iCounter++;
					boolean bReturn = this.setFlag(objEnumFlag, bFlagValue);
					baReturn[iCounter]=bReturn;
				}
			}
		}//end main:
	return baReturn;	
	}
	
	@Override
	public boolean proofFlagExists(IROTEnabledConstantZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
		return this.proofFlagExists(objEnumFlag.name());
	}
	
		
	//### Methoden werden zwar z.B. nur fuer Vigenere Verschluesselung, bzw. fuer Verschluesselung mit CharacterPool gebraucht.
	//    Um den Einsatz von ICrypt - Objekten zu standardiesieren, hier notwendig.
	@Override
	public void setCryptKey(String sCryptKey) {
		//Nur wg. ICryptZZZ
	}
	
	@Override
	public String getCryptKey() {		
		return null;
	}
	
	@Override
	public ArrayListExtendedZZZ<CharacterExtendedZZZ> getCharacterPoolList() throws ExceptionZZZ {
		return null; //Nur wg. ICryptZZZ
	}
	
	@Override
	public void setCharacterPoolList(ArrayListExtendedZZZ<CharacterExtendedZZZ> listasCharacterPool) {
		//Nur wg. ICryptZZZ
	}
		
	
	@Override
	public String getCharacterPool() throws ExceptionZZZ{
		return null; //Nur wg. ICryptZZZ
	}
	
	@Override
	public void setCharacterPoolBase(String sCharacterPoolBase) {
		//Nur wg. ICryptZZZ
	}
	
	@Override
	public String getCharacterPoolBase() {
		///Nur wg. ICryptZZZ
		return null;
	}
		
	
	@Override
	public void setCharacterPoolAdditional(String sCharacterPoolAdditional) {
		//Nur wg. ICryptZZZ
	}
	
	@Override
	public String getCharacterPoolAdditional() {
		//Nur wg. ICryptZZZ
		return null;
	}
		
	@Override
	public CharacterExtendedZZZ getCharacterMissingReplacment() throws ExceptionZZZ{
		//Nur wg. ICryptZZZ
		return null;
	}
		

	@Override
	public void setCharacterMissingReplacement(CharacterExtendedZZZ objCharacterMissingReplacement) {
		//Nur wg. ICryptZZZ
	} 
		
}
