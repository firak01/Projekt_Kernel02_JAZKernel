package debug.zBasic.util.crypt;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.crypt.code.ICryptZZZ;
import basic.zBasic.util.crypt.code.ROT13ZZZ;
import basic.zBasic.util.crypt.code.ROTnnZZZ;
import basic.zBasic.util.crypt.code.ROTnumericZZZ;
import basic.zBasic.util.crypt.code.VigenereNnZZZ;
import basic.zBasic.util.datatype.character.CharZZZ;

public class DebugVigenereZZZ {

	public static void main(String[] args) {
		
		try {
		//Use this allowed characters
		
		
		// Rotate the input string.
        // ... Then rotate the rotated string.
        //String input = "Do you have any cat pictures?";
		
		String SchluesselWort="HALLO"; //FGL: passend zum Beispiel im Buch, S. 30;
		//String SchluesselWort="SchluesselWort"; //FGL: passend zur Datei Vigenere.txt im poly - Verzeichnis der Begleit CD
		                                        //     ABER: DAS ERGEBNIS WEICHT AB!!!
		
		boolean bUseUppercasePool = true;
		boolean bUseLowercasePool = false;
		boolean bUseNumericPool = false;
		boolean bUseAdditionalCharacter = false;
        String sKeyString = SchluesselWort;
        String sVigenere = VigenereNnZZZ.encrypt("KRYPTOGRAFIE", "", CharZZZ.getEmpty(), bUseUppercasePool,bUseLowercasePool, bUseNumericPool, bUseAdditionalCharacter, sKeyString); //FGL: passend zum Beispiel im Buch, S. 31;
        System.out.println(sVigenere);
        
        String roundTrip = VigenereNnZZZ.decrypt(sVigenere,"",bUseUppercasePool,bUseLowercasePool, bUseNumericPool,bUseAdditionalCharacter,sKeyString);
        System.out.println(roundTrip);
        
        
//        System.out.println(rotNn);
//        System.out.println(roundTrip);
//        System.out.println("###################");
//        
//        try {        	
//			ROTnnZZZ objCrypt = new ROTnnZZZ(sCharacterPool, iKeyLength, ICryptZZZ.FLAGZ.USEUPPERCASE.name());
//			rotNn = objCrypt.encrypt(input);
//			roundTrip = objCrypt.decrypt(rotNn);
//			
//			System.out.println(input);
//		    System.out.println(rotNn);
//	        System.out.println(roundTrip);
//	        
//	        System.out.println("#####################");
			
		} catch (ExceptionZZZ e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
	}

}
