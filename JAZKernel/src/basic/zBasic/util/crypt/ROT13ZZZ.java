package basic.zBasic.util.crypt;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;

public class ROT13ZZZ extends AbstractROTZZZ{
	public ROT13ZZZ() throws ExceptionZZZ {
		super();		
		Rot13New_();
	}
	
	private boolean Rot13New_() throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
		
		}//end main:
		return bReturn;
	}
	
	@Override
	public String encrypt(String sInput) throws ExceptionZZZ {
		return ROT13ZZZ.encryptIt(sInput);
	}

	@Override
	public String decrypt(String sInput) throws ExceptionZZZ {
		return ROT13ZZZ.decryptIt(sInput);
	}
	
	public static String encryptIt(String input) {
		return ROT13ZZZ.crypt(input);
	}
	public static String decryptIt(String input) {
		return ROT13ZZZ.crypt(input);
	}
	private static String crypt(String input) {

        char[] values = input.toCharArray();
        for (int i = 0; i < values.length; i++) {
            char letter = values[i];

            if (letter >= 'a' && letter <= 'z') {
                // Rotate lowercase letters.

                if (letter > 'm') {
                    letter -= 13;
                } else {
                    letter += 13;
                }
            } else if (letter >= 'A' && letter <= 'Z') {
                // Rotate uppercase letters.

                if (letter > 'M') {
                    letter -= 13;
                } else {
                    letter += 13;
                }
            }
            values[i] = letter;
        }
        // Convert array to a new String.
        return new String(values);
    }
}
