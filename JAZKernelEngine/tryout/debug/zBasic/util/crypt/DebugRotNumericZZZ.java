package debug.zBasic.util.crypt;

import java.util.ArrayList;
import java.util.List;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractList.ArrayListUtilZZZ;
import basic.zBasic.util.crypt.code.ROT13ZZZ;
import basic.zBasic.util.crypt.code.ROTnumericZZZ;

public class DebugRotNumericZZZ {

	public static void main(String[] args) {
        try {
        	
        	// Rotate the input string.        	
	        // ... Then rotate the rotated string.
        	String input = "Z 9DzfA04";//Merke: Zum Testen die Grenzwerte der Zeichenbereiche und einen Wert in der Mitte genommen.        
        	int iCryptKeyMax = 27; //63; //Merke: Bei 63 ist man einmal durch und der verschluesselte Werte entspricht dem entschlusselten wert
        	                    //       Anzahl Leerzeichen + Anzahl Ziffern + Anzahl Kleinbuchstaben + Anzahl Grossbuchstaben 
        	String sOutput = null;
        	String sRoundtrip = null;
        	boolean bSkip = false;
        	
        	//Zum schnellen Debuggen, falls eine ABWEICHUNG auftritt
        	bSkip=true;
        	if(!bSkip) {
        		System.out.println("\n###################################");
	        	System.out.println("### DEBUG EINZELWERT - SPEZIELL EINGESTELLT ########");
	        	
	        	//Den problematischen Buchstaben als erstes Zeichen unten eintragen.	        		        	
        		String sInputDebug = "Z 9DzfA04";//Merke: Zum Testen die Grenzwerte der Zeichenbereiche und einen Wert in der Mitte genommen.
        		int iCryptKey=64;
        		boolean bNumeric=true;
        		boolean bBlank=true;        		
        		System.out.println("### INPUT='" + sInputDebug + "' | SCHIEBE UM iCryptKey="+iCryptKey);
        		
        		String sNumeric=""; String sBlank="";
        		if(bNumeric) {
        			sNumeric = "Vertausche Zahlen mit Buchstaben, Gross-/Kleinschreibung";
        		}else {
        			sNumeric = "Zahlen bleiben Zahlen, dito Grossbuchstaben und Kleinbuchstaben.";
        		}
        		if(bBlank) {
        			sBlank = "Leerzeichen wird einbezogen.";
        		}else {
        			sBlank = "Leerzeichen bleibt unveraendert.";
        		}
        		System.out.println("### " + sNumeric);
        		System.out.println("### " + sBlank);
        		System.out.println(sInputDebug);
        		//TODOGOON20230222; //Diese Testkonstellation liefert falsches Ergebnis, ggfs. intern eine Schleife einführen.
	        	sOutput = ROTnumericZZZ.encrypt(sInputDebug,iCryptKey,bNumeric,bBlank); 	        	
	        	System.out.println(sOutput);
	        	
		        sRoundtrip = ROTnumericZZZ.decrypt(sOutput,iCryptKey,bNumeric,bBlank);
		        System.out.print(sRoundtrip);
		        if(!sInputDebug.equals(sRoundtrip)) System.out.print(" <== ABWEICHUNG !!!");
		        System.out.println("\n###########");
        	}
        	
        	//#######################################################
        	//Alle Verschiebungen testen, per bSkip ein-/ausschaltbar
        	//#######################################################
        	bSkip=false;
        	if(!bSkip) {
	        	System.out.println("\n###################################");
	        	System.out.println("### OHNE WEITERE ARGUMENTE ########");
	        	
	        	iCryptKeyMax=64;
	        	for(int iSchieber=0;iSchieber<=iCryptKeyMax;iSchieber++) {
		        	System.out.println("### SCHIEBE UM "+ iSchieber);	       					        
			        System.out.println(input);
			        sOutput = ROTnumericZZZ.encrypt(input,iSchieber,false,false);
			        System.out.print(sOutput);
			        if(input.equals(sOutput)) System.out.print(" <== VERSCHLUESSELT IN GLEICHEN WERT !!!");
			        sRoundtrip = ROTnumericZZZ.decrypt(sOutput,iSchieber,false,false);		       
			        System.out.print("\n" + sRoundtrip);
			        if(!input.equals(sRoundtrip)) System.out.print(" <== ABWEICHUNG !!!");
			        System.out.println("\n###########");
	        	}
        	}
        	
        	bSkip=false;
	        	if(!bSkip) {
	        	System.out.println("\n#############################");
	        	System.out.println("### VERWENDE NUR NUMERIC, ohne blank ########");
	        	
	        	iCryptKeyMax=64;
	        	for(int iSchieber=0;iSchieber<=iCryptKeyMax;iSchieber++) {
		        	System.out.println("### SCHIEBE UM "+ iSchieber);
		        	System.out.println(input);
			        sOutput = ROTnumericZZZ.encrypt(input,iSchieber,true,false);
			        System.out.print(sOutput);
			        if(input.equals(sOutput)) System.out.print(" <== VERSCHLUESSELT IN GLEICHEN WERT !!!");			        
			        sRoundtrip = ROTnumericZZZ.decrypt(sOutput,iSchieber,true,false);
			        System.out.print("\n"+sRoundtrip);
			        if(!input.equals(sRoundtrip)) System.out.print(" <== ABWEICHUNG !!!");
			        System.out.println("\n###########");
	        	}
        	}
        	
        	bSkip=false;
        	if(!bSkip) {
	        	System.out.println("\n###################################");
	        	System.out.println("### VERWENDE NUMERIC,BLANK ########");
	        	
	        	iCryptKeyMax=64;
	        	for(int iSchieber=0;iSchieber<=iCryptKeyMax;iSchieber++) {
		        	System.out.println("### SCHIEBE UM "+ iSchieber);
		        	System.out.println(input);
			        sOutput = ROTnumericZZZ.encrypt(input,iSchieber,true,true);
			        System.out.print(sOutput);
			        if(input.equals(sOutput)) System.out.print(" <== VERSCHLUESSELT IN GLEICHEN WERT !!!");			        
			        sRoundtrip = ROTnumericZZZ.decrypt(sOutput,iSchieber,true,true);			        			     
			        System.out.print("\n"+sRoundtrip);
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
