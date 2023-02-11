package basic.zBasic.util.datatype.character;

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
	
	TODOGOON20230211;//Mache eine enum mit 
	//Blank,Number,Letter_Lowercase,Letter_Uppercase
	//Weise dieser Enumeration einen Ascii-Codewert zu
	//Beispiel für solch eine Enum ist CryptAlgorithmMaintypeZZZ
	
	//Mache dann eine Methode
	//public static boolean is(enum,int iAscii){
	//}
	//Mache dann eine Methode
	//pulbic static boolean isHigher(enum, int iAscii){
	//}
	
	public static boolean isBlank(char cAscii) {
		return AsciiZZZ.isBlank((int)cAscii);
	}
	public static boolean isBlank(int iAscii) {
		return iAscii==32;		
	}
	
	
	public static int fromBlank2Number(int iAscii) {
		return iAscii + 15;//15 Zeichen ueberspringen, bis zu den Zahlen.
	}
	
	public static int fromNumber2LetterUppercase(int iAscii) {
		return iAscii + 7;//Zahlen zu Grossbuchstaben weiterschieben.
	}
	
	public static int fromLetterUppercase2LetterLowercase(int iAscii) {
		return iAscii + 6;//Grossbuschstaben zu Kleinbuchstaben weiterschieben.	
	}
	
	public static int fromLetterLowercase2Blank(int iAscii) {
		int iReturn = iAscii + (255-123)+32; //Kleinbuchstaben zu Leerzeichen machen
		iReturn = iReturn - 255;
		return iReturn;
	}
}
