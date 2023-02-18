package basic.zBasic.util.datatype.character;

import java.util.EnumSet;

import basic.zBasic.util.abstractEnum.IEnumSetMappedZZZ;
import basic.zBasic.util.crypt.code.CryptAlgorithmMaintypeZZZ.TypeZZZ;
import basic.zBasic.util.datatype.character.AsciiTableZZZ.SectionZZZ;

/**Mit dieser Klasse bewegt man sich in der ASCII-Tabelle.
 * Wird z.B. verwendet in den Verschluesselungsmethoden, ROT..., also zum Verschieben von Zeichen.
 *
 * Etwas anders als CharZZZ.
 * 
 *  
 * 
 * @author Fritz Lindhauer, 11.02.2023, 09:49:51
 * 
 */
public class AsciiZZZ {
	
	//TODOGOON20230211;//Mache eine enum mit 
	//Blank,Number,Letter_Lowercase,Letter_Uppercase
	//Weise dieser Enumeration einen Ascii-Codewert zu
	//Beispiel für solch eine Enum ist CryptAlgorithmMaintypeZZZ
	
	//Mache dann eine Methode
	//public static boolean is(enum,int iAscii){
	//}
	//Mache dann eine Methode
	//pulbic static boolean isHigher(enum, int iAscii){
	//}
	
	public static boolean is(int iAscii,AsciiTableZZZ.SectionZZZ objEnumSection) {
		boolean bReturn = false;
		main:{
			if(objEnumSection==null)break main;
			
			if(iAscii < objEnumSection.getStart()) break main;
			if(iAscii > objEnumSection.getEnd()) break main;
			
			bReturn = true;
		}
		return bReturn;		
	}

	public static boolean isHigher(int iAscii,AsciiTableZZZ.SectionZZZ objEnumSection) {
		boolean bReturn = false;
		main:{
			if(objEnumSection==null)break main;
			
			if(iAscii <= objEnumSection.getEnd()) break main;
			
			bReturn = true;
		}
		return bReturn;		
	}
	
	public static boolean isLower(int iAscii,AsciiTableZZZ.SectionZZZ objEnumSection) {
		boolean bReturn = false;
		main:{
			if(objEnumSection==null)break main;
			
			if(iAscii >= objEnumSection.getStart()) break main;
			
			bReturn = true;
		}
		return bReturn;		
	}
	
	
	
	
	
	public static boolean isBlank(char cAscii) {
		return AsciiZZZ.isBlank((int)cAscii);
	}
	public static boolean isBlank(int iAscii) {
		//return iAscii==32;	
		//return iAscii == AsciiTableZZZ.SectionZZZ.BLANK.getStart();
		return AsciiZZZ.is(iAscii,AsciiTableZZZ.SectionZZZ.BLANK);
	}
	public static boolean isHigherBlank(int iAscii) {
		return AsciiZZZ.isHigher(iAscii, AsciiTableZZZ.SectionZZZ.BLANK);
	}
	public static boolean isLowerBlank(int iAscii) {
		return AsciiZZZ.isLower(iAscii, AsciiTableZZZ.SectionZZZ.BLANK);
	}
	
	//###########
	public static boolean isNumber(char cAscii) {
		return AsciiZZZ.isNumber((int)cAscii);
	}
	public static boolean isNumber(int iAscii) {
		return AsciiZZZ.is(iAscii,AsciiTableZZZ.SectionZZZ.NUMBER);
	}
	public static boolean isHigherNumber(int iAscii) {
		return AsciiZZZ.isHigher(iAscii, AsciiTableZZZ.SectionZZZ.NUMBER);
	}
	public static boolean isLowerNumber(int iAscii) {
		return AsciiZZZ.isLower(iAscii, AsciiTableZZZ.SectionZZZ.NUMBER);
	}
	
	//###########
	public static boolean isLetterLowercase(char cAscii) {
		return AsciiZZZ.isLetterLowercase((int)cAscii);
	}
	public static boolean isLetterLowercase(int iAscii) {
		return AsciiZZZ.is(iAscii,AsciiTableZZZ.SectionZZZ.LETTER_LOWERCASE);
	}
	public static boolean isHigherLetterLowercase(int iAscii) {
		return AsciiZZZ.isHigher(iAscii, AsciiTableZZZ.SectionZZZ.LETTER_LOWERCASE);
	}
	public static boolean isLowerLetterLowercase(int iAscii) {
		return AsciiZZZ.isLower(iAscii, AsciiTableZZZ.SectionZZZ.LETTER_LOWERCASE);
	}
	
	//###########
	public static boolean isLetterUppercase(char cAscii) {
		return AsciiZZZ.isLetterUppercase((int)cAscii);
	}
	public static boolean isLetterUppercase(int iAscii) {
		return AsciiZZZ.is(iAscii,AsciiTableZZZ.SectionZZZ.LETTER_UPPERCASE);
	}
	public static boolean isHigherLetterUppercase(int iAscii) {
		return AsciiZZZ.isHigher(iAscii, AsciiTableZZZ.SectionZZZ.LETTER_UPPERCASE);
	}
	public static boolean isLowerLetterUppercase(int iAscii) {
		return AsciiZZZ.isLower(iAscii, AsciiTableZZZ.SectionZZZ.LETTER_UPPERCASE);
	}
	
	
	//######################################################################
	/** Schiebe innerhalb des gelich Typs, 
	 * also Zahlen bleiben Zahlen, 
	 * Grossbuchstaben bleiben Grossbuchstaben, 
	 * Kleinbuchstaben bleiben Kleinbuchstaben
	 * @param iAscii
	 * @param objEnumSection
	 * @return
	 * @author Fritz Lindhauer, 14.02.2023, 09:50:52
	 */
	public static int from2from(int iAscii, AsciiTableZZZ.SectionZZZ objEnumSection) {
		int iReturn = iAscii + ((255-objEnumSection.getEnd()-1)+objEnumSection.getStart());
		iReturn = iReturn - 255;
		return iReturn;
	}
	public static int from2fromReverse(int iAscii, AsciiTableZZZ.SectionZZZ objEnumSection) {
		int iReturn = (iAscii+254) - objEnumSection.getStart() - (254 - objEnumSection.getEnd());
		//if(!AsciiZZZ.is(iReturn, objEnumSection)) {
			//Dann noch mal in die Section verschieben
			iReturn = iReturn - (objEnumSection.getEnd()-objEnumSection.getStart());
		//}
		
		return iReturn;
	}
	public static int from2fromReverse(int iAscii, AsciiTableZZZ.SectionZZZ objEnumSectionStart, AsciiTableZZZ.SectionZZZ objEnumSectionEnd ) {
		int iReturn=0;
		if(objEnumSectionStart.getStart() > objEnumSectionEnd.getStart()) {
			//Verschiebung ohne das Verlassen der Tabelle über die untere Grenze hinaus.
			iReturn = iAscii - (objEnumSectionStart.getStart() - objEnumSectionEnd.getEnd());
			iReturn = iReturn + 1;
		}else if(objEnumSectionStart.getStart() < objEnumSectionEnd.getStart()) {
			//Verschiebund mit Verlassen der Tabelle über die untere Grenze hinaus.
			iReturn = (iAscii) - (objEnumSectionStart.getStart() - (254-objEnumSectionEnd.getEnd()));
			iReturn = iReturn - (objEnumSectionStart.getEnd() - objEnumSectionStart.getStart());	
		}else if(objEnumSectionStart.equals(objEnumSectionEnd)) {
			//Verschieben im gleichen Typ
			iReturn = AsciiZZZ.from2from(iAscii, objEnumSectionStart);
		}
		return iReturn;
	}
	
	//###################
	public static int fromBlank2Number(int iAscii) {
		//return iAscii + 15;//15 Zeichen ueberspringen, bis zu den Zahlen.
		return iAscii + AsciiTableZZZ.SectionZZZ.NUMBER.getStart()-AsciiTableZZZ.SectionZZZ.BLANK.getEnd() -1;
	}
	public static int fromBlank2LetterLowercaseReverse(int iAscii) {
		//iRotated = iRotated - 31 - (254-122); //rueckwaerts weiterschieben zu den Kleinbuchstaben, ausgehend vom Leerzeichen
        //iRotated += 254;      //... und wieder in der ASCII Tabelle von vorne anfangen.
		int iReturn = iAscii - (AsciiTableZZZ.SectionZZZ.BLANK.getStart()-1) - (254-AsciiTableZZZ.SectionZZZ.LETTER_LOWERCASE.getEnd());//rueckwaerts weiterschieben zu den Kleinbuchstaben, ausgehend vom Leerzeichen
		iReturn += 254; //... und wieder in der ASCII Tabelle von vorne anfangen.
		return iReturn;
	}
	
	
	
	//##########################
	public static int fromNumber(char cAscii, int iRotationValue) {
		int iReturn=cAscii+iRotationValue;
		
		 boolean bCorrectCharacterFound=false;
         do {
         	if(AsciiZZZ.isHigherNumber(iReturn)) {
         		iReturn = (char) AsciiZZZ.fromNumber2LetterUppercase(iReturn);
	            	 if(AsciiZZZ.isHigherLetterUppercase(iReturn)) {
	            		 iReturn = (char) AsciiZZZ.fromLetterUppercase2LetterLowercase(iReturn);
	                
		            	 if(AsciiZZZ.isHigherLetterLowercase(iReturn)) {
		            		 iReturn = (char) AsciiZZZ.fromLetterLowercase2Number(iReturn);
		            	 }else {
		            		 bCorrectCharacterFound=true;
		            	 }
	            	 }else {
	            		 bCorrectCharacterFound=true;
	            	 }
         	}else {
         		bCorrectCharacterFound=true;
         	}
         }while(!bCorrectCharacterFound);
		return iReturn;
	}
	public static int fromNumberReverse(char cAscii, int iRotationValue) {
		int iReturn=0;    	
		int iAscii=cAscii-iRotationValue;
    	if(AsciiZZZ.isNumber(iAscii)) {
    		iReturn = AsciiZZZ.fromNumber2NumberReverse(iAscii); 
		}else if(AsciiZZZ.isLowerNumber(iAscii)) {
			iReturn = AsciiZZZ.fromNumber2LetterLowercaseReverse(iAscii);
    		
    		if(AsciiZZZ.isLowerLetterLowercase(iReturn)) {
    			iReturn = AsciiZZZ.fromLetterLowercase2LetterUppercaseReverse(iReturn);
    			
    			if(AsciiZZZ.isLowerLetterUppercase(iReturn)) {
        			iReturn = AsciiZZZ.fromLetterUppercase2NumberReverse(iReturn);
        		}
    		}    		
    	}
    	return iReturn;
	}
	
	public static int fromNumberReverseTYPECONSTANT(char cAscii, int iRotationValue) {
		int iReturn=0;    	
		int iAscii=cAscii-iRotationValue;
		iReturn = iAscii;
		while(!AsciiZZZ.isNumber(iReturn)){
			if(AsciiZZZ.isLowerNumber(iReturn)) {
				iReturn = iReturn + (57 - 47) ;	
	    	}else if(AsciiZZZ.isHigherNumber(iReturn)) {
	    		iReturn = iReturn - 57 + 47;
	    	}
		}
    	return iReturn;
	}
	
	public static int fromNumber2LetterUppercase(int iAscii) {
		//return iAscii + 7;//Zahlen zu Grossbuchstaben weiterschieben.
		return iAscii + AsciiTableZZZ.SectionZZZ.LETTER_UPPERCASE.getStart()-AsciiTableZZZ.SectionZZZ.NUMBER.getEnd() -1;
	}	
	public static int fromNumber2LetterLowercaseReverse(int iAscii) {
//		//iRotated = iRotated - 31 - (254-122); //rueckwaerts weiterschieben zu den Kleinbuchstaben, ausgehend vom Leerzeichen
//        //iRotated += 254;      //... und wieder in der ASCII Tabelle von vorne anfangen.
//		int iReturn=iAscii;	
//		iReturn = iReturn - (AsciiTableZZZ.SectionZZZ.NUMBER.getStart()-1) - (254-AsciiTableZZZ.SectionZZZ.LETTER_LOWERCASE.getEnd());//rueckwaerts weiterschieben zu den Kleinbuchstaben, ausgehend von den Zahlen		
//		iReturn += 254; //... und wieder in der ASCII Tabelle von vorne anfangen.
//					
//		return iReturn;
		return AsciiZZZ.from2fromReverse(iAscii, AsciiTableZZZ.SectionZZZ.NUMBER, AsciiTableZZZ.SectionZZZ.LETTER_LOWERCASE);
	}
	public static int fromNumber2LetterUppercaseReverse(int iAscii) {
		//iRotated = iRotated - 31 - (254-122); //rueckwaerts weiterschieben zu den Kleinbuchstaben, ausgehend vom Leerzeichen
        //iRotated += 254;      //... und wieder in der ASCII Tabelle von vorne anfangen.
		int iReturn=iAscii;	
		iReturn = 255-AsciiTableZZZ.SectionZZZ.LETTER_LOWERCASE.getEnd();		
		iReturn = iReturn - (AsciiTableZZZ.SectionZZZ.LETTER_LOWERCASE.getEnd()-AsciiTableZZZ.SectionZZZ.LETTER_LOWERCASE.getStart());		
		iReturn = iReturn - (AsciiTableZZZ.SectionZZZ.LETTER_LOWERCASE.getStart() - AsciiTableZZZ.SectionZZZ.LETTER_UPPERCASE.getEnd());
		
		iReturn = iReturn - (AsciiTableZZZ.SectionZZZ.LETTER_UPPERCASE.getEnd() - AsciiTableZZZ.SectionZZZ.LETTER_UPPERCASE.getStart());
		
		iReturn = iReturn - (AsciiTableZZZ.SectionZZZ.NUMBER.getStart()-1) - (254-AsciiTableZZZ.SectionZZZ.LETTER_UPPERCASE.getEnd());//rueckwaerts weiterschieben zu den Kleinbuchstaben, ausgehend von den Zahlen
		
		iReturn += 254; //... und wieder in der ASCII Tabelle von vorne anfangen.					
		return iReturn;
	}
	
	
	public static int fromNumber2BlankReverse(int iAscii) {
		//iRotated=iRotated-15; //15 Zeichen ueberspringen, bis zum Leerzeichen
		return iAscii - (AsciiTableZZZ.SectionZZZ.NUMBER.getStart()-AsciiTableZZZ.SectionZZZ.BLANK.getEnd() -1);
	}
	public static int fromNumber2Number(int iAscii) { //verwendet bei cyrptNumericSimple, Zahlen bleiben Zahlen 9 wird also wieder zur 0
		//return iAscii + ((255-AsciiTableZZZ.SectionZZZ.NUMBER.getEnd()-1)+AsciiTableZZZ.SectionZZZ.NUMBER.getStart())-255;
		return AsciiZZZ.from2from(iAscii, AsciiTableZZZ.SectionZZZ.NUMBER);
	}
	public static int fromNumber2NumberReverse(int iAscii) { //verwendet bei cyrptNumericSimple, Zahlen bleiben Zahlen 9 wird also wieder zur 0
		//return iAscii + ((255-AsciiTableZZZ.SectionZZZ.NUMBER.getEnd()-1)+AsciiTableZZZ.SectionZZZ.NUMBER.getStart())-255;
		return AsciiZZZ.from2fromReverse(iAscii, AsciiTableZZZ.SectionZZZ.NUMBER);
	}
	
	public static int fromLetterUppercase(char cAscii, int iRotationValue) {
		int iReturn=cAscii+iRotationValue;
		
		//Ziel: Auch mit iCryptKey >63 arbeiten können!!!
    	//darum in einer Schleife
        boolean bCorrectCharacterFound=false;
        do {
            if(AsciiZZZ.isHigherLetterUppercase(iReturn)) {		                    
            	iReturn = AsciiZZZ.fromLetterUppercase2LetterLowercase(iReturn);
            	
            	if(AsciiZZZ.isHigherLetterLowercase(iReturn)) {
            		iReturn = AsciiZZZ.fromLetterLowercase2Number(iReturn);            		
            		if(AsciiZZZ.isHigherNumber(iReturn)) {
            			iReturn = AsciiZZZ.fromNumber2LetterUppercase(iReturn);            			
            		}else {
            			bCorrectCharacterFound=true;
            		}
            	}else {
            		bCorrectCharacterFound=true;
            	}
            }else {
            	bCorrectCharacterFound=true;
            }
        }while(!bCorrectCharacterFound);		
		return iReturn;
	}
	public static int fromLetterUppercaseReverse(char cAscii, int iRotationValue) {
		int iReturn=cAscii-iRotationValue;
    	if(AsciiZZZ.isLetterUppercase(iReturn)) {
    		iReturn = AsciiZZZ.fromLetterUppercase2LetterUppercaseReverse(iReturn); 
		}else if(AsciiZZZ.isLowerLetterUppercase(iReturn)) {
    		iReturn = AsciiZZZ.fromLetterUppercase2NumberReverse(iReturn);
    		
    		if(AsciiZZZ.isLowerNumber(iReturn)) {
    			iReturn = AsciiZZZ.fromNumber2LetterLowercaseReverse(iReturn);
    			
    			if(AsciiZZZ.isLowerLetterLowercase(iReturn)) {
        			iReturn = AsciiZZZ.fromLetterLowercase2LetterUppercaseReverse(iReturn);
        		}
    		}    		    	
    	}
    	return iReturn;
	}
	public static int fromLetterUppercaseReverseTYPECONSTANT(char cAscii, int iRotationValue) {
		int iReturn=0;    	
		int iAscii=cAscii-iRotationValue;
		iReturn = iAscii;
    	while(!AsciiZZZ.isLetterUppercase(iReturn)) {
    		if(AsciiZZZ.isLowerLetterUppercase(iReturn)) {
	    		iReturn = iReturn + (90 - 64);	    	
	    	}else if(AsciiZZZ.isHigherLetterUppercase(iReturn)) {
	    		iReturn = iReturn - 90 + 64;
	    	}
    	}
    	return iReturn;
	}
	
	
	
	public static int fromLetterUppercase2LetterLowercase(int iAscii) {
		//return iAscii + 6;//Grossbuschstaben zu Kleinbuchstaben weiterschieben.	
		return iAscii + AsciiTableZZZ.SectionZZZ.LETTER_LOWERCASE.getStart()-AsciiTableZZZ.SectionZZZ.LETTER_UPPERCASE.getEnd() -1;
	}
	public static int fromLetterUppercase2LetterUppercase(int iAscii) {//verwendet bei cyrptNumericSimple, Grossbuchstaben bleiben Grossbuchstaben Z wird also wieder zu A
		return AsciiZZZ.from2from(iAscii,AsciiTableZZZ.SectionZZZ.LETTER_UPPERCASE);			
	}
	public static int fromLetterUppercase2LetterUppercaseReverse(int iAscii) {//verwendet bei cyrptNumericSimple, Grossbuchstaben bleiben Grossbuchstaben Z wird also wieder zu A
		return AsciiZZZ.from2fromReverse(iAscii,AsciiTableZZZ.SectionZZZ.LETTER_UPPERCASE);			
	}
	public static int fromLetterUppercase2NumberReverse(int iAscii) {
		//iRotated=iRotated-7; //7 Zeichen ueberspringen, bis in den Zahlenbereich
		//return iAscii - (AsciiTableZZZ.SectionZZZ.LETTER_UPPERCASE.getStart()-AsciiTableZZZ.SectionZZZ.NUMBER.getEnd()-1);
		return AsciiZZZ.from2fromReverse(iAscii,AsciiTableZZZ.SectionZZZ.LETTER_UPPERCASE, AsciiTableZZZ.SectionZZZ.NUMBER);
	}
	
	public static int fromLetterLowercase(char cAscii, int iRotationValue) {
		int iReturn=cAscii+iRotationValue;
		
		//Ziel: Auch mit iCryptKey >63 arbeiten können!!!
    	//darum in einer Schleife
        boolean bCorrectCharacterFound=false;
        do {
            if(AsciiZZZ.isHigherLetterLowercase(iReturn)) {
            	bCorrectCharacterFound=false;
            	iReturn = (char) AsciiZZZ.fromLetterLowercase2Number(iReturn);
            }else {
            	bCorrectCharacterFound=true;
            }
            if(AsciiZZZ.isHigherNumber(iReturn)) {
            	bCorrectCharacterFound=false;
            	iReturn = (char) AsciiZZZ.fromNumber2LetterUppercase(iReturn);
            }else {
            	bCorrectCharacterFound=true;
            }
            if(AsciiZZZ.isHigherLetterUppercase(iReturn)) {
            	bCorrectCharacterFound=false;
            	iReturn = (char) AsciiZZZ.fromLetterUppercase2LetterLowercase(iReturn);
            }else {
            	bCorrectCharacterFound=true;
            }
        }while(!bCorrectCharacterFound);		
		return iReturn;
	}
	public static int fromLetterLowercaseReverse(char cAscii, int iRotationValue) {
		int iReturn=cAscii-iRotationValue;
		
		//Ziel: Auch mit iCryptKey >63 arbeiten können!!!
    	//darum in einer Schleife
        boolean bCorrectCharacterFound=false;
		do {
	    	if(AsciiZZZ.isLetterLowercase(iReturn)) {
	    		iReturn = AsciiZZZ.fromLetterLowercase2LetterLowercaseReverse(iReturn);
	    		bCorrectCharacterFound=true;
	    	}else if(AsciiZZZ.isLowerLetterLowercase(iReturn)) {
	    		iReturn = AsciiZZZ.fromLetterLowercase2LetterUppercaseReverse(iReturn);
	    		
	    		if(AsciiZZZ.isLowerLetterUppercase(iReturn)) {
	    			iReturn = AsciiZZZ.fromLetterUppercase2NumberReverse(iReturn);
	    			
	    			if(AsciiZZZ.isLowerNumber(iReturn)) {
	        			iReturn = AsciiZZZ.fromNumber2LetterLowercaseReverse(iReturn);
	        		}else {
	        			bCorrectCharacterFound=true;
	        		}
	    		}else {
	    			bCorrectCharacterFound=true;
	    		}
	    	}else {
	    		bCorrectCharacterFound=true;
	    	}
		}while(!bCorrectCharacterFound);		
    	return iReturn;
	}
	public static int fromLetterLowercaseReverseTYPECONSTANT(char cAscii, int iRotationValue) {
		int iReturn=0;    	
		int iAscii=cAscii-iRotationValue;
		iReturn = iAscii;
    	while(!AsciiZZZ.isLetterLowercase(iReturn)) {
    		if(AsciiZZZ.isLowerLetterLowercase(iReturn)) {
	    		iReturn = iReturn + (122 - 96);
	    	}else if(AsciiZZZ.isHigherLetterLowercase(iReturn)) {
	    		iReturn = iReturn - 122 + 96;
	    	}
    	}
    	return iReturn;
	}
	public static int fromLetterLowercase2Blank(int iAscii) {
		//int iReturn = iAscii + (255-123)+32; //Kleinbuchstaben zu Leerzeichen machen
		int iReturn = iAscii + (255-AsciiTableZZZ.SectionZZZ.LETTER_LOWERCASE.getEnd()-1)+AsciiTableZZZ.SectionZZZ.BLANK.getStart();
		iReturn = iReturn - 255;
		return iReturn;
	}
	public static int fromLetterLowercase2Number(int iAscii) {
		int iReturn = iAscii + (255-AsciiTableZZZ.SectionZZZ.LETTER_LOWERCASE.getEnd()-1)+AsciiTableZZZ.SectionZZZ.NUMBER.getStart();
		iReturn = iReturn - 255;
		return iReturn;
	}
	public static int fromLetterLowercase2LetterLowercase(int iAscii) {//verwendet bei cyrptNumericSimple, Kleinbuchstaben bleiben Kleinbuchstaben z wird also wieder zu a
		return AsciiZZZ.from2from(iAscii,AsciiTableZZZ.SectionZZZ.LETTER_LOWERCASE);
	}
	public static int fromLetterLowercase2LetterLowercaseReverse(int iAscii) {//verwendet bei cyrptNumericSimple, Kleinbuchstaben bleiben Kleinbuchstaben z wird also wieder zu a
		return AsciiZZZ.from2fromReverse(iAscii,AsciiTableZZZ.SectionZZZ.LETTER_LOWERCASE);
	}
	
//	public static int fromLetterLowercase2BlankReverse(int iAscii) {
//		//iRotated = iRotated - 91; //Zeichen groesser als Kleinbuchstaben zu Leerzeichen machen.
//		return iAscii - (AsciiTableZZZ.SectionZZZ.LETTER_LOWERCASE.getEnd())-AsciiTableZZZ.SectionZZZ.BLANK.getStart(); 			
//	}
	
	public static int fromLetterLowercase2LetterUppercaseReverse(int iAscii) {
		 //iRotated = iRotated - 6;  //rueckwaerts weiterschieben von den Kleinbuchstaben zu den Grossbuchstaben
		//return iAscii - (AsciiTableZZZ.SectionZZZ.LETTER_LOWERCASE.getStart()-AsciiTableZZZ.SectionZZZ.LETTER_UPPERCASE.getEnd()-1);
		return AsciiZZZ.from2fromReverse(iAscii, AsciiTableZZZ.SectionZZZ.LETTER_LOWERCASE, AsciiTableZZZ.SectionZZZ.LETTER_UPPERCASE);
	}

	
	
	
	
	}//End Class
