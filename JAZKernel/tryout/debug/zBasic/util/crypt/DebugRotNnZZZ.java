package debug.zBasic.util.crypt;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.crypt.code.ICryptZZZ;
import basic.zBasic.util.crypt.code.IROTZZZ;
import basic.zBasic.util.crypt.code.ROT13ZZZ;
import basic.zBasic.util.crypt.code.ROTnnZZZ;
import basic.zBasic.util.crypt.code.ROTnumericZZZ;

public class DebugRotNnZZZ {

	public static void main(String[] args) {
		//Use this allowed characters
		String sCharacterPool = " abcdefghijklmnopqrstuvwxyz?";
		int iKeyLength=5;
		
		// Rotate the input string.
        // ... Then rotate the rotated string.
        String input = "Do you have any cat pictures?";
        String rotNn = ROTnnZZZ.encrypt(input,sCharacterPool,iKeyLength, true,false,false);
        String roundTrip = ROTnnZZZ.decrypt(rotNn,sCharacterPool,iKeyLength, true,false,false);

        System.out.println(input);
        System.out.println(rotNn);
        System.out.println(roundTrip);
        System.out.println("###################");
        
        try {        	
			ROTnnZZZ objCrypt = new ROTnnZZZ(sCharacterPool, iKeyLength, IROTZZZ.FLAGZ.USEUPPERCASE.name());
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
