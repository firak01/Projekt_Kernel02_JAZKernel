package debug.zBasic.util.crypt;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.crypt.ICryptZZZ;
import basic.zBasic.util.crypt.ROTnnZZZ;
import basic.zBasic.util.crypt.ROTnumericZZZ;
import basic.zBasic.util.crypt.Rot13ZZZ;
import basic.zBasic.util.crypt.ROTnumericZZZ;

public class DebugRotNnCrypt_MyPassordZZZ {

	public static void main(String[] args) {
		//Use this allowed characters
		String sCharacterPool = " abcdefghijklmnopqrstuvwxyz1234567890!";
		
		// Rotate the input string.
        // ... Then rotate the rotated string.
		
		TODOGOON20220930;//Consoleneingabe zur Verfügung stellen
        String input = "xxxxxxx"; 
        String rotNn = ROTnnZZZ.encrypt(input,sCharacterPool,5, true);
        String roundTrip = ROTnnZZZ.decrypt(rotNn,sCharacterPool,5, true);

        System.out.println(input);
        System.out.println(rotNn);
        System.out.println(roundTrip);
        System.out.println("###################");
        
        try {        	
			ROTnnZZZ objCrypt = new ROTnnZZZ(sCharacterPool, 5, ICryptZZZ.FLAGZ.USEUPPERCASE.name());
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
