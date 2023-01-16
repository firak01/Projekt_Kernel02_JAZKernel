package debug.zBasic.util.crypt;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.crypt.code.ICryptZZZ;
import basic.zBasic.util.crypt.code.ROT13ZZZ;
import basic.zBasic.util.crypt.code.AbstractROTnnZZZ;
import basic.zBasic.util.crypt.code.ROTnnZZZ;
import basic.zBasic.util.crypt.code.ROTnumericZZZ;

public class DebugRotAsciiZZZ {

	public static void main(String[] args) {
		//Use this allowed characters		
		int iKeyLength=3;
		
		// Rotate the input string.
        // ... Then rotate the rotated string.
        String input = "Do you have any cat pictures?";
        String rotNn = AbstractROTnnZZZ.encrypt(input,iKeyLength);
        String roundTrip = AbstractROTnnZZZ.decrypt(rotNn,iKeyLength);

        System.out.println(input);
        System.out.println(rotNn);
        System.out.println(roundTrip);
        System.out.println("###################");
        
        try {        	
        	ROTnnZZZ objCrypt = new ROTnnZZZ(iKeyLength);
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
