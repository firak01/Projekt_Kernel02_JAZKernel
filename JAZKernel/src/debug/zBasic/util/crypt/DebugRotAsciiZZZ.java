package debug.zBasic.util.crypt;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.crypt.ICryptZZZ;
import basic.zBasic.util.crypt.ROTnnZZZ;
import basic.zBasic.util.crypt.ROTnumericZZZ;
import basic.zBasic.util.crypt.ROT13ZZZ;
import basic.zBasic.util.crypt.ROTasciiZZZ;
import basic.zBasic.util.crypt.ROTnumericZZZ;

public class DebugRotAsciiZZZ {

	public static void main(String[] args) {
		//Use this allowed characters		
		int iKeyLength=3;
		
		// Rotate the input string.
        // ... Then rotate the rotated string.
        String input = "Do you have any cat pictures?";
        String rotNn = ROTasciiZZZ.encrypt(input,iKeyLength);
        String roundTrip = ROTasciiZZZ.decrypt(rotNn,iKeyLength);

        System.out.println(input);
        System.out.println(rotNn);
        System.out.println(roundTrip);
        System.out.println("###################");
        
        try {        	
        	ROTasciiZZZ objCrypt = new ROTasciiZZZ(iKeyLength);
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
