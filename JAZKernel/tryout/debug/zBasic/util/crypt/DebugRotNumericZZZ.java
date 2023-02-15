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
        	String input = "Z 9DzfA04";//Merke: Zum Testen die Grenzwerte der Zeichenbereiche und einen Wert in der Mitte genommen.        
        	int iCryptKeyMax = 26; //63; //Merke: Bei 63 ist man einmal durch und der verschluesselte Werte entspricht dem entschlusselten wert
        	                    //       Anzahl Leerzeichen + Anzahl Ziffern + Anzahl Kleinbuchstaben + Anzahl Grossbuchstaben 
        	String sOutput = null;
        	String sRoundtrip = null;
        	boolean bSkip = false;
        	
        	//Zum schnellen Debuggen, falls eine ABWEICHUNG auftritt
        	bSkip=false;
        	if(!bSkip) {
        		System.out.println("\n###################################");
	        	System.out.println("### DEBUG EINZELWERT - SPEZIELL EINGETELLT ########");
	        	//Den problematischen Buchstaben als erstes Zeichen unten eintragen.
        		String sInputDebug = "DfA09Zz 4";//Merke: Zum Testen die Grenzwerte der Zeichenbereiche und einen Wert in der Mitte genommen.
        		int iCryptKey=49;
        		System.out.println("### SCHIEBE UM "+ iCryptKey);
	        	sOutput = ROTnumericZZZ.encrypt(sInputDebug,iCryptKey,true,false);            
		        sRoundtrip = ROTnumericZZZ.decrypt(sOutput,iCryptKey,true,false);
		        
		        System.out.println(sInputDebug);
		        System.out.println(sOutput);
		        System.out.print(sRoundtrip);
		        if(!sInputDebug.equals(sRoundtrip)) System.out.print(" <== ABWEICHUNG !!!");
		        System.out.println("\n###########");
        	}
        	
        	//Alle Verschiebungen testen
        	bSkip=false;
        	if(!bSkip) {
	        	System.out.println("\n###################################");
	        	System.out.println("### OHNE WEITERE ARGUMENTE ########");
	        	iCryptKeyMax=26;
	        	for(int iSchieber=0;iSchieber<=iCryptKeyMax;iSchieber++) {
		        	System.out.println("### SCHIEBE UM "+ iSchieber);	       		
			        sOutput = ROTnumericZZZ.encrypt(input,iSchieber,false,false);
			        sRoundtrip = ROTnumericZZZ.decrypt(sOutput,iSchieber,false,false);
			        
			        System.out.println(input);
			        System.out.println(sOutput);
			        System.out.print(sRoundtrip);
			        if(!input.equals(sRoundtrip)) System.out.print(" <== ABWEICHUNG !!!");
			        System.out.println("\n###########");
	        	}
        	}
        	
        	bSkip=false;
	        	if(!bSkip) {
	        	System.out.println("\n#############################");
	        	System.out.println("### VERWENDE NUMERIC ########");
	        	iCryptKeyMax=63;
	        	for(int iSchieber=0;iSchieber<=iCryptKeyMax;iSchieber++) {
		        	System.out.println("### SCHIEBE UM "+ iSchieber);	       		
			        sOutput = ROTnumericZZZ.encrypt(input,iSchieber,true,false);
			        sRoundtrip = ROTnumericZZZ.decrypt(sOutput,iSchieber,true,false);
			        
			        System.out.println(input);
			        System.out.println(sOutput);
			        System.out.print(sRoundtrip);
			        if(!input.equals(sRoundtrip)) System.out.print(" <== ABWEICHUNG !!!");
			        System.out.println("\n###########");
	        	}
        	}
        	
        	bSkip=true;
        	if(!bSkip) {
	        	System.out.println("\n###################################");
	        	System.out.println("### VERWENDE NUMERIC,BLANK ########");
	        	iCryptKeyMax=63;
	        	for(int iSchieber=0;iSchieber<=iCryptKeyMax;iSchieber++) {
		        	System.out.println("### SCHIEBE UM "+ iSchieber);	       		
			        sOutput = ROTnumericZZZ.encrypt(input,iSchieber,true,true);
			        sRoundtrip = ROTnumericZZZ.decrypt(sOutput,iSchieber,true,true);
			        
			        System.out.println(input);
			        System.out.println(sOutput);
			        System.out.print(sRoundtrip);
			        if(!input.equals(sRoundtrip)) System.out.print(" <== ABWEICHUNG !!!");
			        System.out.println("\n###########");
	        	}
        	}
                
        	System.out.println("\n#####################################");
        	System.out.println("### PER OBJEKT ######################");
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
	        String sOutput1 = ROTnumericZZZ.encrypt(input1,3,false,false);
	              
	        String sRoundtrip1 = ROTnumericZZZ.decrypt(sOutput1,3,false,false);
	        
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
			
	        String sOutput2 = ROTnumericZZZ.encrypt(input2,3,true,false);
	              
	        String sRoundtrip2 = ROTnumericZZZ.decrypt(sOutput2,3,true,false);
	        
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
			
	        String sOutput3 = ROTnumericZZZ.encrypt(input3,3,true,false);
	              
	        String sRoundtrip3 = ROTnumericZZZ.decrypt(sOutput3,3,true,false);
	        
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
			
	        String sOutput4 = ROTnumericZZZ.encrypt(input4,3,true,false);
	              
	        String sRoundtrip4 = ROTnumericZZZ.decrypt(sOutput4,3,true,false);
	        
	        System.out.println(input4);
	        System.out.println(sOutput4);
	        System.out.println(sRoundtrip4);
	        
	        System.out.println("###########");
                

			ROTnumericZZZ objCrypt4 = new ROTnumericZZZ(5);
			sOutput4 = objCrypt4.encrypt(input4);
			sRoundtrip4 = objCrypt4.decrypt(sOutput4);
			
			System.out.println(input4);
		    System.out.println(sOutput4);
	        System.out.println(sRoundtrip4);

	        
	        
	        
			
		} catch (ExceptionZZZ e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        
        
	}

}
