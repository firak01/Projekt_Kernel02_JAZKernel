package debug.zBasic.util.crypt;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.crypt.ICryptZZZ;
import basic.zBasic.util.crypt.Rot13NumericZZZ;
import basic.zBasic.util.crypt.Rot13ZZZ;
import basic.zBasic.util.crypt.RotNnZZZ;

public class DebugRotNnCryptZZZ {

	public static void main(String[] args) {
		//Use this allowed characters
		String sCharacterPool = " abcdefghijklmnopqrstuvwxyz?";
		
		// Rotate the input string.
        // ... Then rotate the rotated string.
        String input = "Do you have any cat pictures?";
        String rotNn = RotNnZZZ.encrypt(input,sCharacterPool,true,5);
        String roundTrip = RotNnZZZ.decrypt(rotNn,sCharacterPool,true,5);

        System.out.println(input);
        System.out.println(rotNn);
        System.out.println(roundTrip);
        System.out.println("###################");
        
        try {        	
			RotNnZZZ objCrypt = new RotNnZZZ(sCharacterPool, 5, ICryptZZZ.FLAGZ.USEUPPERCASE.name());
			rotNn = objCrypt.encrypt(input);
			roundTrip = objCrypt.decrypt(rotNn);
			
			System.out.println(input);
		    System.out.println(rotNn);
	        System.out.println(roundTrip);
	        
	        System.out.println("#####################");
			
		} catch (ExceptionZZZ e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
	}

}
