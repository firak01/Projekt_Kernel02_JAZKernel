package basic.zBasic.util.file;

import basic.zBasic.ObjectZZZ;

/** Utility Klasse um mit UTF Dateien (hautpsächlich UTF-8) zu arbeiten.
 *  
 * 
 * @author Fritz Lindhauer, 16.03.2020, 12:37:57
 * 
 */
public class UtfEasyZZZ  extends ObjectZZZ{
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
}
