package basic.zBasic.util.datatype.character;

import basic.zBasic.util.datatype.string.StringZZZ;

public class CharZZZ {

	
	private CharZZZ(){
		//zum 'Verstecken" des Konstruktors
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
	
	
	/**
	 * Merke Char kann nie NULL sein.
	 * @return
	 * @author Fritz Lindhauer, 24.07.2019, 09:57:46
	 */
	public static char getEmpty(){
		return Character.MIN_VALUE;
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
