package basic.zBasic.util.crypt;

import java.util.HashMap;
import java.util.Map;

import basic.zBasic.util.datatype.string.StringZZZ;

/**Definiere eine Liste von Zeichen.
 * Diese wird dann (auch als Großbuchstaben) durch das ROT (also Zeichenverschiebung) verschlüsselt.
 * @author Fritz Lindhauer, 25.09.2022, 08:10:43
 * 
 * Anregung von:  
 * https://codereview.stackexchange.com/questions/7241/rot-n-algorithm-in-java
 * 
 */
public class RotNnZZZ {
	public static String encrypt(String input, int n) {

	    String abc = " abcdefghijklmnopqrstuvwxyz?";
	    String abcABC = abc + abc.toUpperCase();
	    int len = abcABC.length();

	    Map<Character, Character> map = new HashMap<Character, Character>();
	    for (int i = 0; i < len; i++) {
	        map.put(abcABC.charAt(i), abcABC.charAt((i + n + len) % len));
	    }

	    StringBuilder sb = new StringBuilder();
	    for(int i = 0; i < input.length(); i++) {
	        Character ch = map.get(input.charAt(i));
	        if (ch == null) {
	            throw new IllegalArgumentException("Illegal character " + input.charAt(i));
	        }
	        sb.append(ch);
	    }
	    return sb.toString();
    }
	
	public static String decrypt(String input, int n) {
	    String list = " abcdefghijklmnopqrstuvwxyz?";
	    String abc = StringZZZ.reverse(list); 
	    String abcABC = abc + abc.toUpperCase();
	    int len = abcABC.length();

	    Map<Character, Character> map = new HashMap<Character, Character>();
	    for (int i = 0; i < len; i++) {
	        map.put(abcABC.charAt(i), abcABC.charAt((i + n + len) % len));
	    }

	    StringBuilder sb = new StringBuilder();
	    for(int i = 0; i < input.length(); i++) {
	        Character ch = map.get(input.charAt(i));
	        if (ch == null) {
	            throw new IllegalArgumentException("Illegal character " + input.charAt(i));
	        }
	        sb.append(ch);
	    }
	    return sb.toString();
	}
}
