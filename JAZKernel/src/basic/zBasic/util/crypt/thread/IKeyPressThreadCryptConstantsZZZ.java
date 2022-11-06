package basic.zBasic.util.crypt.thread;

import basic.zBasic.util.console.multithread.IKeyPressThreadConstantZZZ;

public interface IKeyPressThreadCryptConstantsZZZ extends IKeyPressThreadConstantZZZ{
	public static String sINPUT_CIPHER = "INPUT_CIPHER";
	public static String sINPUT_KEY_NUMERIC = "INPUT_KEY_NUMERIC";
	public static String sINPUT_KEY_STRING = "INPUT_KEY_STRING";
	public static String sINPUT_TEXT_UNCRYPTED = "INPUT_TEXT_UNCRYPTED";
	public static String sINPUT_CHARACTERPOOL = "INPUT_CHARACTERPOOL";
	public static String sINPUT_SKIP_ARGUMENTS = "INPUT_SKIP_ARGUMENTS";
	
	//Merke: Mit "INPUT_FLAG" werden Eingabewerte gekennzeichnet, die später dann als Flag den Objekten übergeben werden können.	
	public static String sINPUT_FLAG_CHARACTER_UPPERCASE = IKeyPressThreadConstantZZZ.sINPUT_FLAG + "UseUpperCase";
	public static String sINPUT_FLAG_CHARACTER_LOWERCASE = IKeyPressThreadConstantZZZ.sINPUT_FLAG + "UseLowerCase";
	public static String sINPUT_FLAG_CHARACTER_NUMERIC = IKeyPressThreadConstantZZZ.sINPUT_FLAG + "UseNumeric";
	
	
	
	public static String sOUTPUT_TEXT_CRYPTED = "OUTPUT_TEXT_CRYPTED";   
	public static String sOUTPUT_TEXT_UNCRYPTED = "OUTPUT_TEXT_UNCRYPTED";
    
}
