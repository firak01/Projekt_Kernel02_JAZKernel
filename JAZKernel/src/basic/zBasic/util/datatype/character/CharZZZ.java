package basic.zBasic.util.datatype.character;

import java.nio.charset.Charset;

import basic.zBasic.util.datatype.string.StringZZZ;

public class CharZZZ {

	
	private CharZZZ(){
		//zum 'Verstecken" des Konstruktors
	}
	
	/**
	 * @param c
	 * @return
	 * @author Fritz Lindhauer, 09.06.2019, 09:42:58
	 * siehe: 
	 * https://stackoverflow.com/questions/8534178/how-to-represent-empty-char-in-java-character-class
	 */
	public static boolean isEmpty(char c){
		if(c == CharZZZ.getEmpty()){
			return true;
		}else{
			return false;
		}
	}
	
	/** Auch Leerzeichen zählt hier als empty
	 * @param c
	 * @return
	 * @author Fritz Lindhauer, 06.11.2022, 10:27:28
	 */
	public static boolean isEmptyBlank(char c){
		if(CharZZZ.isEmpty(c)){
			return true;
		}else if(c ==' ') {
			return true;
		}else {
			return false;
		}
	}
	
	
	public static boolean isLowercase(char c){
		boolean bReturn = false;
		main:{
			String stemp = Character.toString(c);			
			bReturn = StringZZZ.isLowerized(stemp);
		}//end main:
		return bReturn;
	}
	
	public static boolean isNumeric(char c){
		boolean bReturn = false;
		main:{
			String stemp = Character.toString(c);			
			bReturn = StringZZZ.isNumeric(stemp);
		}//end main:
		return bReturn;
	}
	
	public static boolean isNumericPrefix(char c) {
		boolean bReturn = false;
		main:{
			if(CharZZZ.isEmpty(c))break main;
			
			if("-".toCharArray()[0] == c) {
				bReturn = true;
				break main;
			}
			if("+".toCharArray()[0] == c) {
				bReturn = true;
				break main;
			}
		}
		return bReturn;
	}
	
	public static boolean isUppercase(char c){
		boolean bReturn = false;
		main:{
			String stemp = Character.toString(c);			
			bReturn = StringZZZ.isUpperized(stemp);
		}//end main:
		return bReturn;
	}
	
	
	
	
	/**
	 * Merke Char kann nie NULL sein.
	 * @return
	 * @author Fritz Lindhauer, 24.07.2019, 09:57:46
	 */
	public static char getEmpty(){
		return Character.MIN_VALUE;
	}
	
	public static String toString(int iCharValue) {
		char c = (char)iCharValue;
		return CharZZZ.toString(c);
	}
	
	public static String toLowercase(int iCharValue) {
		char c = (char)iCharValue;
		char cl = CharZZZ.toLowercase(c);
		return CharZZZ.toString(cl);
	}
	
	public static String toUppercase(int iCharValue) {
		char c = (char)iCharValue;
		char cu = CharZZZ.toUppercase(c);
		return CharZZZ.toString(cu);
	}
	
	
	public static String toString(char c){
		return Character.toString(c);    
	}
	
	public static char toLowercase(char c) {
		return Character.toLowerCase(c);
	}
	
	public static char toUppercase(char c) {
		return Character.toUpperCase(c);
	}
		
}
