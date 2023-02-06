package basic.zBasic.util.crypt.thread;

import basic.zBasic.util.console.multithread.IKeyPressThreadConstantZZZ;
import basic.zBasic.util.crypt.code.ICharacterPoolUserZZZ;

/**Konstanten, mit denen Werte in einer HashMap gespeichert werden.
 * Die HashMap wird verwendet um die Daten zwischen Threads auszutauschen.
 * @author Fritz Lindhauer, 22.01.2023, 09:21:59
 * 
 */
public interface IKeyPressThreadCryptConstantsZZZ extends IKeyPressThreadConstantZZZ{
	public static String sINPUT_CIPHER = "INPUT_CIPHER";            //Der Verschluesselungsalgorithmus wird mit diesem Schluessel in der HashMap gespeichert
	public static String sINPUT_KEY_NUMERIC = "INPUT_KEY_NUMERIC";  //Schluesselwert, z.B. fuer ROT Verschluesselung
	public static String sINPUT_KEY_STRING = "INPUT_KEY_STRING";    //Schluesselwort, z.B. fuer Vigenere Verschlüsselung
	public static String sINPUT_TEXT_UNCRYPTED = "INPUT_TEXT_UNCRYPTED";
	public static String sINPUT_CHARACTERPOOL = "INPUT_CHARACTERPOOL";
	public static String sINPUT_CHARACTERPOOL_ADDITIONAL = "INPUT_CHARACTERPOOL_ADDITIONAL";
	public static String sINPUT_BOOLEAN_SKIP_ARGUMENTS = "INPUT_BOOLEAN_SKIP_ARGUMENTS";
	
	//Merke: Mit "INPUT_FLAG" werden Eingabewerte gekennzeichnet, die später dann als Flag den Objekten übergeben werden können.	
	//public static String sINPUT_FLAG_CHARACTER_UPPERCASE = IKeyPressThreadConstantZZZ.sINPUT_FLAG + "UseUpperCase";
	public static String sINPUT_FLAG_CHARACTER_UPPERCASE = IKeyPressThreadConstantZZZ.sINPUT_FLAG + ICharacterPoolUserZZZ.FLAGZ.USEUPPERCASE.name();
	//public static String sINPUT_FLAG_CHARACTER_LOWERCASE = IKeyPressThreadConstantZZZ.sINPUT_FLAG + "UseLowerCase";
	public static String sINPUT_FLAG_CHARACTER_LOWERCASE = IKeyPressThreadConstantZZZ.sINPUT_FLAG + ICharacterPoolUserZZZ.FLAGZ.USELOWERCASE.name();
	//public static String sINPUT_FLAG_CHARACTER_NUMERIC = IKeyPressThreadConstantZZZ.sINPUT_FLAG + "UseNumeric";
	public static String sINPUT_FLAG_CHARACTER_NUMERIC = IKeyPressThreadConstantZZZ.sINPUT_FLAG + ICharacterPoolUserZZZ.FLAGZ.USENUMERIC.name();	
	public static String sINPUT_FLAG_CHARACTER_ADDITIONAL = IKeyPressThreadConstantZZZ.sINPUT_FLAG + ICharacterPoolUserZZZ.FLAGZ.USEADDITIONALCHARACTER.name();
	
	//public static String sINPUT_FLAG_USE_CHARACTERPOOL = IKeyPressThreadConstantZZZ.sINPUT_FLAG + "UseCharacterPool";
	public static String sINPUT_FLAG_USE_CHARACTERPOOL = IKeyPressThreadConstantZZZ.sINPUT_FLAG + ICharacterPoolUserZZZ.FLAGZ.USECHARACTERPOOL.name();
	
	
	
	public static String sOUTPUT_TEXT_CRYPTED = "OUTPUT_TEXT_CRYPTED";   
	public static String sOUTPUT_TEXT_UNCRYPTED = "OUTPUT_TEXT_UNCRYPTED";
    
}
