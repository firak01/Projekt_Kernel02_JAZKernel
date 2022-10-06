package debug.zBasic.util.crypt;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.crypt.Rot13ZZZ;

public class DebugRot13ZZZ {

	public static void main(String[] args) {
		// Rotate the input string.
        // ... Then rotate the rotated string.
        String input = "Do you have any cat pictures?";
        String rot13 = Rot13ZZZ.encryptIt(input);
        String roundTrip = Rot13ZZZ.decryptIt(rot13);

        System.out.println(input);
        System.out.println(rot13);
        System.out.println(roundTrip);
        
        System.out.println("#####################");
        
        try {
			Rot13ZZZ objCrypt = new Rot13ZZZ();
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
