package debug.zBasic.util.crypt;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.crypt.ICryptZZZ;
import basic.zBasic.util.crypt.ROTnnZZZ;
import basic.zBasic.util.crypt.ROTnumericZZZ;
import basic.zBasic.util.crypt.VigenereNnZZZ;
import basic.zBasic.util.crypt.ROT13ZZZ;
import basic.zBasic.util.crypt.ROTnumericZZZ;

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

        String sKeyString = SchluesselWort;
        String sVigenere = VigenereNnZZZ.encrypt("KRYPTOGRAFIE", "", sKeyString); //FGL: passend zum Beispiel im Buch, S. 31;
        //String roundTrip = ROTnnZZZ.decrypt(rotNn,sCharacterPool,iKeyLength, true,false,false);

        System.out.println(sVigenere);
        
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
