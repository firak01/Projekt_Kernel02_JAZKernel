package debug.zBasic.util.crypt;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.crypt.code.ICharacterPoolEnabledZZZ;
import basic.zBasic.util.crypt.code.ICryptZZZ;
import basic.zBasic.util.crypt.code.IROTUserZZZ;
import basic.zBasic.util.crypt.code.IROTZZZ;
import basic.zBasic.util.crypt.code.ROT13ZZZ;
import basic.zBasic.util.crypt.code.ROTnnZZZ;
import basic.zBasic.util.crypt.code.ROTnumericZZZ;
import basic.zBasic.util.datatype.character.CharacterExtendedZZZ;

public class DebugRotNnZZZ {

	public static void main(String[] args) {        
	     try { 
			//Use this allowed characters
			String sCharacterPool = " abcdefghijklmnopqrstuvwxyz?";
			int iKeyLength=5;
			CharacterExtendedZZZ objCharacterMissingReplacement = new CharacterExtendedZZZ(ICharacterPoolEnabledZZZ.cCHARACTER_MISSING_REPLACEMENT_DEFAULT);
			
			// Rotate the input string.
	        // ... Then rotate the rotated string.
	        String input = "Do you have any cat pictures?";
	        String rotNn = ROTnnZZZ.encrypt(input,sCharacterPool,objCharacterMissingReplacement, iKeyLength, true,false,false,false);
	        String roundTrip = ROTnnZZZ.decrypt(rotNn,sCharacterPool,objCharacterMissingReplacement, iKeyLength, true,false,false,false);
	
	        System.out.println(input);
	        System.out.println(rotNn);
	        System.out.println(roundTrip);
	        System.out.println("###################");
	       	
			ROTnnZZZ objCrypt = new ROTnnZZZ(sCharacterPool, iKeyLength, ICharacterPoolEnabledZZZ.FLAGZ.USEUPPERCASE.name());
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
