package debug.zBasic.util.crypt;

import basic.zBasic.util.crypt.Rot13ZZZ;
import basic.zBasic.util.crypt.RotNnZZZ;

public class DebugRotNnCryptZZZ {

	public static void main(String[] args) {
		// Rotate the input string.
        // ... Then rotate the rotated string.
        String input = "Do you have any cat pictures?";
        String rotNn = RotNnZZZ.encrypt(input,5);
        String roundTrip = RotNnZZZ.decrypt(rotNn,5);

        System.out.println(input);
        System.out.println(rotNn);
        System.out.println(roundTrip);
	}

}
