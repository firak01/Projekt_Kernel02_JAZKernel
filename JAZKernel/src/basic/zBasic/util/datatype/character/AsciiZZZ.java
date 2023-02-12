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
	
	
	
	
	public static int fromBlank2Number(int iAscii) {
		//return iAscii + 15;//15 Zeichen ueberspringen, bis zu den Zahlen.
		return iAscii + AsciiTableZZZ.SectionZZZ.NUMBER.getStart()-AsciiTableZZZ.SectionZZZ.BLANK.getEnd() -1;
	}
	
	public static int fromNumber2LetterUppercase(int iAscii) {
		//return iAscii + 7;//Zahlen zu Grossbuchstaben weiterschieben.
		return iAscii + AsciiTableZZZ.SectionZZZ.LETTER_UPPERCASE.getStart()-AsciiTableZZZ.SectionZZZ.NUMBER.getEnd() -1;
	}
	
	public static int fromLetterUppercase2LetterLowercase(int iAscii) {
		//return iAscii + 6;//Grossbuschstaben zu Kleinbuchstaben weiterschieben.	
		return iAscii + AsciiTableZZZ.SectionZZZ.LETTER_LOWERCASE.getStart()-AsciiTableZZZ.SectionZZZ.LETTER_UPPERCASE.getEnd() -1;
	}
	
	public static int fromLetterLowercase2Blank(int iAscii) {
		//int iReturn = iAscii + (255-123)+32; //Kleinbuchstaben zu Leerzeichen machen
		int iReturn = iAscii + (255-AsciiTableZZZ.SectionZZZ.LETTER_LOWERCASE.getEnd()-1)+AsciiTableZZZ.SectionZZZ.BLANK.getStart();
		iReturn = iReturn - 255;
		return iReturn;
	}

	
	
	
	
	}//End Class
