package basic.zBasic.util.file;

import basic.zBasic.AbstractObjectZZZ;

/** Utility Klasse um mit UTF Dateien (hautpsächlich UTF-8) zu arbeiten.
 *  
 * 
 * @author Fritz Lindhauer, 16.03.2020, 12:37:57
 * 
 */
public class UtfEasyZZZ  extends AbstractObjectZZZ{
	public static final String UTF8_BOM = "\uFEFF";
	
	
	/** Zur Behandlung von UTF-8 mit BOM Dateien.
	 * 
	 Java does not handle BOM properly. In fact Java handles a BOM like every other char.
	Found this:
	http://www.rgagnon.com/javadetails/java-handle-utf8-file-with-bom.html
	
	May be I would use apache IO instead:
	http://commons.apache.org/proper/commons-io/apidocs/org/apache/commons/io/input/BOMInputStream.html
	 * @param s
	 * @return
	 * @author Fritz Lindhauer, 16.03.2020, 12:21:01
	 */
	public static String removeUTF8BOM(String s) {
	    if (s.startsWith(UTF8_BOM)) {
	        s = s.substring(1);
	    }
	    return s;
	}
	
	
	/**Folgende Lösung gefunden.
	 * https://stackoverflow.com/questions/655891/converting-utf-8-to-iso-8859-1-in-java-how-to-keep-it-as-single-byte
	 * 
	 * Merke: 0x3a ist der HEXCode in ASCII für den Doppelpunkt
	 *        Wird also 0x3a als Inhalt eines ByteArrays uebergeben, kommt ":" als Teil des Strings heraus.
	 * 
	 * TODO: Wenn das mal sinnvolleingesetzt werden kann ausprogrammieren.
	 *       Ggfs. auf ein einzelnes Zeichen (char) abwandeln und dann nur ein Byte übergeben und kein Array.
	 * @return
	 * @author Fritz Lindhauer, 23.03.2023, 12:58:10
	 */
	public static String makeStringFromUTF8_ByteArray_TODO() {
		String sReturn = null;
		main:{		
			//Charset utf8charset = Charset.forName("UTF-8");
			//Charset iso88591charset = Charset.forName("ISO-8859-1");
			//byte[] utf8bytes = { (byte)0xc3, (byte)0xa2, 0x61, 0x62, 0x63, 0x64, 0x58, 0x3a };			    
		    //String string = new String ( utf8bytes, utf8charset );//ergibt den String "a mit Dach" a b c d X :
		    //System.out.println(string);

		}//end main
		return sReturn;
		}	
}
