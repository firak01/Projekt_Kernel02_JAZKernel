package basic.zBasic.util.crypt.code;

import base.files.DateiUtil;
import base.io.IoUtil;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractList.ArrayListExtendedZZZ;
import basic.zBasic.util.datatype.character.CharacterExtendedZZZ;

public class VigenereNnZZZ extends AbstractVigenereNnZZZ implements IVigenereNnZZZ{
	private static final long serialVersionUID = -2833560399688739434L;		

	public VigenereNnZZZ() {
		super();
	}
	public VigenereNnZZZ(String[]saFlagControl) throws ExceptionZZZ {	  
		super(saFlagControl);
	}
	
	public VigenereNnZZZ(String sKeyString) throws ExceptionZZZ {
		super(sKeyString);		
	}
	
	
	public VigenereNnZZZ(String sKeyString, String sFilePath) throws ExceptionZZZ {
		super(sKeyString,sFilePath);		
	}

	/** Wie AbstractVigenereZZZ, aber auf den CharacterPool bezogen
	 * @param sInput
	 * @param sCharacterPoolIn
	 * @param n
	 * @return
	 * @throws IllegalArgumentException
	 * @author Fritz Lindhauer, 18.12.2022, 08:58:20
	 * @throws ExceptionZZZ 
	 */
	public static String encrypt(String sInput, String sCharacterPoolBaseIn, char cCharacterMissingReplacement, String sKeyword) throws IllegalArgumentException, ExceptionZZZ {
		return VigenereNnZZZ.encrypt(sInput, sCharacterPoolBaseIn, cCharacterMissingReplacement,true,true,true,true,null, sKeyword);		
    }
	
	
	/** Wie AbstractVigenereZZZ, aber auf den CharacterPool bezogen
	 * @param sInput
	 * @param sCharacterPoolIn
	 * @param n
	 * @return
	 * @throws IllegalArgumentException
	 * @author Fritz Lindhauer, 18.12.2022, 08:58:20
	 * @throws ExceptionZZZ 
	 */
	public static String encrypt(String sInput, String sCharacterPoolBaseIn, char cCharacterMissingReplacement, boolean bUseUppercasePool, boolean bUseLowercasePool, boolean bUseNumericPool,String sKeyword) throws IllegalArgumentException, ExceptionZZZ {
		return AbstractVigenereNnZZZ.encrypt(sInput, sCharacterPoolBaseIn, cCharacterMissingReplacement, bUseUppercasePool,  bUseLowercasePool, bUseNumericPool, false, null, sKeyword);
    }
	
	public static String encrypt(String sInput, String sCharacterPoolBaseIn, char cCharacterMissingReplacement, boolean bUseUppercasePool, boolean bUseLowercasePool, boolean bUseNumericPool, boolean bUseAdditionalCharacter, String sCharacterPoolAdditional, String sKeyword) throws IllegalArgumentException, ExceptionZZZ {
		return AbstractVigenereNnZZZ.encrypt(sInput, sCharacterPoolBaseIn, cCharacterMissingReplacement, bUseUppercasePool,  bUseLowercasePool, bUseNumericPool, bUseAdditionalCharacter, sCharacterPoolAdditional, sKeyword);
    }
	
	/** Wie AbstractVigenereZZZ, aber auf den CharacterPool bezogen
	 * @param sInput
	 * @param sCharacterPoolIn
	 * @param n
	 * @return
	 * @throws IllegalArgumentException
	 * @author Fritz Lindhauer, 18.12.2022, 08:58:20
	 * @throws ExceptionZZZ 
	 */
	public static int[] encryptAsPositionInPool(String sInput, ArrayListExtendedZZZ<CharacterExtendedZZZ> listasCharacterPool, String sKeyword) throws IllegalArgumentException, ExceptionZZZ {
		return AbstractVigenereNnZZZ.encryptAsPositionInPool(sInput, listasCharacterPool, sKeyword);
    }
	
		
	/** Wie AbstractVigenereZZZ, aber auf den CharacterPool bezogen
	 * @param sInput
	 * @param sCharacterPoolIn
	 * @param n
	 * @return
	 * @throws IllegalArgumentException
	 * @author Fritz Lindhauer, 18.12.2022, 08:58:20
	 * @throws ExceptionZZZ 
	 */
	public static String decrypt(String sInput, String sCharacterPoolBaseIn, boolean bUseUppercasePool, boolean bUseLowercasePool, boolean bUseNumericPool, String sKeyword) throws IllegalArgumentException, ExceptionZZZ {
		return AbstractVigenereNnZZZ.decrypt(sInput, sCharacterPoolBaseIn, bUseUppercasePool, bUseLowercasePool, bUseNumericPool, false, null, sKeyword);
    }
	
	public static String decrypt(String sInput, String sCharacterPoolBaseIn, boolean bUseUppercasePool, boolean bUseLowercasePool, boolean bUseNumericPool, boolean bUseAdditionalCharacter, String sCharacterPoolAdditional, String sKeyword) throws IllegalArgumentException, ExceptionZZZ {
		return AbstractVigenereNnZZZ.decrypt(sInput, sCharacterPoolBaseIn, bUseUppercasePool, bUseLowercasePool, bUseNumericPool, bUseAdditionalCharacter, sCharacterPoolAdditional, sKeyword);
    }
	
	/** Wie AbstractVigenereZZZ, aber auf den CharacterPool bezogen
	 * @param sInput
	 * @param sCharacterPoolIn
	 * @param n
	 * @return
	 * @throws IllegalArgumentException
	 * @author Fritz Lindhauer, 18.12.2022, 08:58:20
	 * @throws ExceptionZZZ 
	 */
	public static int[] decrypt(String sInput, ArrayListExtendedZZZ<CharacterExtendedZZZ> listasCharacterPool, String sKeyword) throws IllegalArgumentException, ExceptionZZZ {
		return AbstractVigenereNnZZZ.decrypt(sInput, listasCharacterPool, sKeyword);
    }
	
	/** Wie AbstractVigenereZZZ, aber auf den CharacterPool bezogen
	 * @param sInput
	 * @param sCharacterPoolIn
	 * @param n
	 * @return
	 * @throws IllegalArgumentException
	 * @author Fritz Lindhauer, 18.12.2022, 08:58:20
	 * @throws ExceptionZZZ 
	 */
	public static int[] decryptAsPositionInPool(int[] iaEncryptedText, ArrayListExtendedZZZ<CharacterExtendedZZZ> listasCharacterPool, int[] iaSchluesselwort) throws IllegalArgumentException, ExceptionZZZ {
		return AbstractVigenereNnZZZ.decryptAsPositionInPool(iaEncryptedText, listasCharacterPool, iaSchluesselwort); 
    }
	
	/** Wie AbstractVigenereZZZ, aber auf den CharacterPool bezogen.
	 * -1 steht fuer: Zeichen war nicht im CharacterPool enthalten.
	 * @param sInput
	 * @param sCharacterPoolIn
	 * @param n
	 * @return
	 * @throws IllegalArgumentException
	 * @author Fritz Lindhauer, 18.12.2022, 08:58:20
	 * @throws ExceptionZZZ 
	 */
	public static int[] decryptAsPositionInPool(int[] iaEncryptedText, ArrayListExtendedZZZ<CharacterExtendedZZZ> listasCharacterPool, int[] iaSchluesselwort, CharacterExtendedZZZ objCharMissingReplacement) throws IllegalArgumentException, ExceptionZZZ {
		return AbstractVigenereNnZZZ.decryptAsPositionInPool(iaEncryptedText, listasCharacterPool, iaSchluesselwort, objCharMissingReplacement); 
    }
	
	public static int[] makeOriginalValuesAsCharacterPoolPosition(int[] iaPure, ArrayListExtendedZZZ<CharacterExtendedZZZ> listasCharacterPool) throws ExceptionZZZ {
		return AbstractVigenereNnZZZ.makeOriginalValuesAsCharacterPoolPosition(iaPure, listasCharacterPool);
	}
}
