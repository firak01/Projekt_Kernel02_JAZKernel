package debug.zBasic.util.crypt;

import basic.zBasic.util.crypt.Rot13ZZZ;

public class DebugRot13CryptZZZ {

	public static void main(String[] args) {
		// Rotate the input string.
        // ... Then rotate the rotated string.
        String input = "Do you have any cat pictures?";
        String rot13 = Rot13ZZZ.crypt(input);
        String roundTrip = Rot13ZZZ.crypt(rot13);

        System.out.println(input);
        System.out.println(rot13);
        System.out.println(roundTrip);
	}

}
