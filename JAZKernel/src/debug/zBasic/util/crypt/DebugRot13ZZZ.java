package debug.zBasic.util.crypt;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.crypt.ROT13ZZZ;

public class DebugRot13ZZZ {

	public static void main(String[] args) {
		// Rotate the input string.
        // ... Then rotate the rotated string.
        String input = "Do you have any cat pictures?";
        String rot13 = ROT13ZZZ.encryptIt(input);
        String roundTrip = ROT13ZZZ.decryptIt(rot13);

        System.out.println(input);
        System.out.println(rot13);
        System.out.println(roundTrip);
        
        System.out.println("#####################");
        
        try {
			ROT13ZZZ objCrypt = new ROT13ZZZ();
			rot13 = objCrypt.encrypt(input);
			roundTrip = objCrypt.decrypt(rot13);
			
			System.out.println(input);
		    System.out.println(rot13);
	        System.out.println(roundTrip);
	        
	        System.out.println("#####################");
			
		} catch (ExceptionZZZ e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
