package debug.zBasic.util.crypt;

import java.util.Scanner;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.crypt.ICryptZZZ;
import basic.zBasic.util.crypt.ROTnnZZZ;
import basic.zBasic.util.crypt.ROTnumericZZZ;

/** Rotate the input string.
 *   ... Then rotate the rotated string.
 * @author Fritz Lindhauer, 01.10.2022, 11:12:27
 * 
 */
public class DebugRotNn_MyPassordZZZ {

	public static void main(String[] args) {
		//Use this allowed characters
		String sCharacterPoolDefault = " abcdefghijklmnopqrstuvwxyz1234567890!";
		
		
		
        String inputDefault = "abcef";
        String input = null;
        
        System.out.println("Verschlüsselung eines Kennworts mit ROTnn");
        System.out.println("Geben Sie als Key eine Zahl n ein ( 0 <= n ): ");        
        Scanner objScanner = new Scanner(System.in);
        int iKey = objScanner.nextInt();
        
        //Merke: Mit DebugCryptConsoelMainZZZ gibt es eine Klasse, die in einem separaten Thread immer wieder aufgerufen wird.
        //       Zudem hat diese Klasse auch eine komfortable Menuesteuerung.
        //       Dort koennen auch andere Algorithmen und CharacterPools verwendete werden. 
        String sCharacterPool = sCharacterPoolDefault;
        System.out.println("Merke: Das Kennwort darf nur folgende Zeichen beinhalten: '" + sCharacterPool + "'");
        
        
        System.out.println("Geben Sie das zu verschlüsselnde Kennwort ein: ");
        input = objScanner.next();
                               
        try {        	
        	System.out.println("###################");
        	
			ROTnnZZZ objCrypt = new ROTnnZZZ(sCharacterPool, iKey, ICryptZZZ.FLAGZ.USEUPPERCASE.name());
			String rotNn = objCrypt.encrypt(input);
			String roundTrip = objCrypt.decrypt(rotNn);
			
			System.out.println("Eingabe:\t" + input);
		    System.out.println("Verschlüsselt:\t"+rotNn);
	        System.out.println("Entschlüsselt:\t"+roundTrip);
	        
	        System.out.println("#####################");
			
		} catch (ExceptionZZZ e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
	}

}
