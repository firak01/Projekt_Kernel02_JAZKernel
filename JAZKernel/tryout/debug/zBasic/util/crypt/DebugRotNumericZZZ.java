package debug.zBasic.util.crypt;

import java.util.ArrayList;
import java.util.List;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractList.ArrayListZZZ;
import basic.zBasic.util.crypt.code.ROT13ZZZ;
import basic.zBasic.util.crypt.code.ROTnumericZZZ;

public class DebugRotNumericZZZ {

	public static void main(String[] args) {
        try {
        	
        	// Rotate the input string.        	
	        // ... Then rotate the rotated string.
        	String input = "z 9fADZ04";
        	String sOutput = ROTnumericZZZ.encrypt(input,12,true);            
	        String sRoundtrip = ROTnumericZZZ.decrypt(sOutput,12,true);
        	
        	
        	
        	
        	for(int iSchieber=1;iSchieber<=13;iSchieber++) {
	        	System.out.println("### SCHIEBE UM "+ iSchieber);	       		
		        sOutput = ROTnumericZZZ.encrypt(input,iSchieber,true);
		        sRoundtrip = ROTnumericZZZ.decrypt(sOutput,iSchieber,true);
		        
		        System.out.println(input);
		        System.out.println(sOutput);
		        System.out.print(sRoundtrip);
		        if(!input.equals(sRoundtrip)) System.out.print(" <== ABWEICHUNG !!!");
		        System.out.println("\n###########");
        	}
                
        	
        	System.out.println("### PER OBJEKT ########");
        	int iSchieber = 5;
        	System.out.println("### SCHIEBE UM "+ iSchieber);
			ROTnumericZZZ objCrypt = new ROTnumericZZZ(iSchieber,"usenumeric");
			sOutput = objCrypt.encrypt(input);
			sRoundtrip = objCrypt.decrypt(sOutput);
			
			System.out.println(input);
		    System.out.println(sOutput);
	        System.out.println(sRoundtrip);
	        
	        System.out.println("#####################");
	        
	        //##############################################
        	
        	
			// Rotate the input string.
	        // ... Then rotate the rotated string.
	        String input1 = "Do you have any CAT pictures?";			
	        String sOutput1 = ROTnumericZZZ.encrypt(input1,3,false);
	              
	        String sRoundtrip1 = ROTnumericZZZ.decrypt(sOutput1,3,false);
	        
	        System.out.println(input1);
	        System.out.println(sOutput1);
	        System.out.println(sRoundtrip1);
	        System.out.println("###########");
                

			ROTnumericZZZ objCrypt1 = new ROTnumericZZZ(5);
			sOutput = objCrypt.encrypt(input1);
			sRoundtrip = objCrypt.decrypt(sOutput1);
			
			System.out.println(input1);
		    System.out.println(sOutput1);
	        System.out.println(sRoundtrip1);
	        
	        System.out.println("#####################");
	        
	        //##############################################
	        
	        String input2 = "Do 9 cat pictures";
			
	        String sOutput2 = ROTnumericZZZ.encrypt(input2,3,true);
	              
	        String sRoundtrip2 = ROTnumericZZZ.decrypt(sOutput2,3,true);
	        
	        System.out.println(input2);
	        System.out.println(sOutput2);
	        System.out.println(sRoundtrip2);
	        System.out.println("###########");
                

			ROTnumericZZZ objCrypt2 = new ROTnumericZZZ(5);
			sOutput2 = objCrypt.encrypt(input2);
			sRoundtrip2 = objCrypt.decrypt(sOutput2);
			
			System.out.println(input2);
		    System.out.println(sOutput2);
	        System.out.println(sRoundtrip2);
	        
	        //##################################################
	        
	        String input3 = "Do 1 cat picture";
			
	        String sOutput3 = ROTnumericZZZ.encrypt(input3,3,true);
	              
	        String sRoundtrip3 = ROTnumericZZZ.decrypt(sOutput3,3,true);
	        
	        System.out.println(input3);
	        System.out.println(sOutput3);
	        System.out.println(sRoundtrip3);
	        System.out.println("###########");
                

			ROTnumericZZZ objCrypt3 = new ROTnumericZZZ(5);
			sOutput3 = objCrypt.encrypt(input3);
			sRoundtrip3 = objCrypt.decrypt(sOutput3);
			
			System.out.println(input3);
		    System.out.println(sOutput3);
	        System.out.println(sRoundtrip3);
	        
	        //##################################################
	        
	        String input4 = "Do 4 cat pictures";
			
	        String sOutput4 = ROTnumericZZZ.encrypt(input4,3,true);
	              
	        String sRoundtrip4 = ROTnumericZZZ.decrypt(sOutput4,3,true);
	        
	        System.out.println(input4);
	        System.out.println(sOutput4);
	        System.out.println(sRoundtrip4);
	        
	        System.out.println("###########");
                

			ROTnumericZZZ objCrypt4 = new ROTnumericZZZ(5);
			sOutput4 = objCrypt.encrypt(input4);
			sRoundtrip4 = objCrypt.decrypt(sOutput4);
			
			System.out.println(input4);
		    System.out.println(sOutput4);
	        System.out.println(sRoundtrip4);

	        
	        
	        
			
		} catch (ExceptionZZZ e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        
        
	}

}
