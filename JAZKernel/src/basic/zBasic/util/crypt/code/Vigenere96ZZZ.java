package basic.zBasic.util.crypt.code;

import base.files.DateiUtil;
import base.files.EncodingMaintypeZZZ;
import base.io.IoUtil;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.crypt.code.CryptAlgorithmMappedValueZZZ.CipherTypeZZZ;
import basic.zBasic.util.datatype.string.UnicodeZZZ;

/** Aus "Kryptographie mit Java", Seite 33f
 *  Die Klasse stammt aus der Buch CD, aus dem Verzeichnis poly
 *  Die Verschlusselungsmethode gehoert zu den "polyalphabetischen Ansaetzen"
 *  
 * Erweitert und angepasst
 * Insbesondere Anpassung an 96 Zeichen, damit es zu den Zahlenwerten und den Verschluesselten Buchstabenwerten im Buchbeispiel auf der angegebenen Seite passt
 * Merke: Der verwendete Moduluswert entspricht dem 96 im Klassennamen
 * @author Fritz Lindhauer, 08.10.2022, 08:29:08
 * 
 */
public class Vigenere96ZZZ extends AbstractVigenereZZZ{ 		// Vigenereverschluesselung
	private static final long serialVersionUID = 1L;
	
	public static int iOffsetForAsciiRange=96;
	public static int iOffsetForUtf8Range=32;
	public Vigenere96ZZZ() {	  
		super();
	}
	public Vigenere96ZZZ(String[]saFlagControl) throws ExceptionZZZ {	  
		super(saFlagControl);
	}
	public Vigenere96ZZZ(String sSchluesselWort) throws ExceptionZZZ {
		super(sSchluesselWort);
	}
	public Vigenere96ZZZ(String sSchluesselWort, String sFilePath) throws ExceptionZZZ {
		super(sSchluesselWort, sFilePath);
	}
	
	@Override
	public Enum<?> getCipherTypeAsEnum() throws ExceptionZZZ {
		return CryptAlgorithmMappedValueZZZ.CipherTypeZZZ.VIGENERE96;
	}
	
	@Override
	public CipherTypeZZZ getCipherType() throws ExceptionZZZ {
		return CryptAlgorithmMappedValueZZZ.CipherTypeZZZ.VIGENERE96;
	}
 
	@Override
	public int getOffsetForAsciiRange() {
		return Vigenere96ZZZ.iOffsetForAsciiRange;
	}
	
	@Override
	public int getOffsetForUtf8Range() {
		return Vigenere96ZZZ.iOffsetForUtf8Range;
	}
	
	@Override
	public int[] fromUtf8ToAsciiForOffset(int[] p) {
		 //Analog zu Vigenere96.java, nur mit 65
		 //ImBuch, auf Seite 34 steht... "wird auf space (Nr. 32) bezogen, 
		 //    for(int i=0; i < p.length; i++) {
		 //    	p[i]=p[i]-65;
		 //    }
		 return UnicodeZZZ.fromUtf8ToAsciiFor96(p);
	}
	
	@Override
	public int[] fromAsciiToUtf8ForOffset(int[] p) {
		//Analog zum buch, nur mit +32
	    //Im Buch auf Seite 36 wird dann 32 draufgerechnet ("blank")
	    return UnicodeZZZ.fromAsciiToUtf8For96(p);//funktioniert bei Viginere26 Verschluesselung, es wird 65 draufgerechnet	   
	}
}
