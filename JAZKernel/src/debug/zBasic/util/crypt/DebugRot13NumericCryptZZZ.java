package debug.zBasic.util.crypt;

import java.util.ArrayList;
import java.util.List;

import basic.zBasic.util.abstractList.ArrayListZZZ;
import basic.zBasic.util.crypt.Rot13NumericZZZ;
import basic.zBasic.util.crypt.Rot13ZZZ;

public class DebugRot13NumericCryptZZZ {

	public static void main(String[] args) {
		// Rotate the input string.
        // ... Then rotate the rotated string.
        String input = "Do you have any cat pictures?";
		//String input = "Do you have any cat pictures";
		
        List<String> listRot13 = Rot13NumericZZZ.crypt(input,false);
        //String rot13 = ArrayListZZZ.implode((ArrayList<?>) listRot13, "");
        String rot13 = listRot13.get(1);
        
        List<String> listRoundTrip = Rot13NumericZZZ.crypt(rot13,false);
        //String roundTrip = ArrayListZZZ.implode((ArrayList<?>) listRoundTrip, "");
        String roundTrip = listRoundTrip.get(25);
        
        System.out.println(input);
        System.out.println(rot13);
        System.out.println(roundTrip);
        System.out.println("###########");
        
        rot13 = listRot13.get(2);
        List<String> listRoundTrip2 = Rot13NumericZZZ.crypt(rot13,false);
        roundTrip = listRoundTrip2.get(24);
        
        System.out.println(input);
        System.out.println(rot13);
        System.out.println(roundTrip);
        System.out.println("###########");
	}

}
