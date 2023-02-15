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
		int iReturn = iAscii - objEnumSection.getStart() - (254 - objEnumSection.getEnd());
		iReturn = iReturn + 255;
		return iReturn;
		
		
//		int iReturn = iAscii - (AsciiTableZZZ.SectionZZZ.BLANK.getStart()-1) - (254-AsciiTableZZZ.SectionZZZ.LETTER_LOWERCASE.getEnd());//rueckwaerts weiterschieben zu den Kleinbuchstaben, ausgehend vom Leerzeichen
//		iReturn += 254; //... und wieder in der ASCII Tabelle von vorne anfangen.
//		return iReturn;
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
	
	public static int fromNumber2LetterUppercase(int iAscii) {
		//return iAscii + 7;//Zahlen zu Grossbuchstaben weiterschieben.
		return iAscii + AsciiTableZZZ.SectionZZZ.LETTER_UPPERCASE.getStart()-AsciiTableZZZ.SectionZZZ.NUMBER.getEnd() -1;
	}	
	public static int fromNumber2LetterLowercaseReverse(int iAscii) {
		//iRotated = iRotated - 31 - (254-122); //rueckwaerts weiterschieben zu den Kleinbuchstaben, ausgehend vom Leerzeichen
        //iRotated += 254;      //... und wieder in der ASCII Tabelle von vorne anfangen.
		int iReturn=iAscii;	
		iReturn = iReturn - (AsciiTableZZZ.SectionZZZ.NUMBER.getStart()-1) - (254-AsciiTableZZZ.SectionZZZ.LETTER_LOWERCASE.getEnd());//rueckwaerts weiterschieben zu den Kleinbuchstaben, ausgehend von den Zahlen		
		iReturn += 254; //... und wieder in der ASCII Tabelle von vorne anfangen.
					
		return iReturn;
	}
	public static int fromNumber2LetterUppercaseReverse(int iAscii) {
		TODOGOON
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
		return iAscii - (AsciiTableZZZ.SectionZZZ.LETTER_UPPERCASE.getStart()-AsciiTableZZZ.SectionZZZ.NUMBER.getEnd()-1);
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
		return iAscii - (AsciiTableZZZ.SectionZZZ.LETTER_LOWERCASE.getStart()-AsciiTableZZZ.SectionZZZ.LETTER_UPPERCASE.getEnd()-1);
	}

	
	
	
	
	}//End Class
