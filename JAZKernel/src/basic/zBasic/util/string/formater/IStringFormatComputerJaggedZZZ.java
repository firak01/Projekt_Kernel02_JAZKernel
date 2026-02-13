package basic.zBasic.util.string.formater;

import java.util.LinkedHashMap;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractList.ArrayListZZZ;

/** Merke: Nur String ohne XML-Inhalt sollten per Jagged angeglichen werden.
 *         Die XML-Tags blähen die Zeile auf und der Kommentartrenner wandert ganz weit nach rechts.
 *  
 *  Ziel diese Interface ist, dass der XML-Format Manager diese Methoden nicht zu implementieren braucht.
 *  
 * @author Fritz Lindhauer, 30.11.2025, 09:43:07
 * 
 */
public interface IStringFormatComputerJaggedZZZ extends IStringFormatComputerZZZ {

	
		//...Jagged Variante wichtiger Methoden
		public String computeJagged_(String... sLogs) throws ExceptionZZZ;
	
		public String computeJagged_(LinkedHashMap<IEnumSetMappedStringFormatZZZ, String> hm) throws ExceptionZZZ;
		public String computeJagged_(Object obj, LinkedHashMap<IEnumSetMappedStringFormatZZZ, String> hm) throws ExceptionZZZ;
		public String computeJagged_(Class classObj, LinkedHashMap<IEnumSetMappedStringFormatZZZ, String> hmLog) throws ExceptionZZZ;
		
		public String computeJagged_(Object obj, String... sLogs) throws ExceptionZZZ;
		public String computeJagged_(Class classObj, String... sLogs) throws ExceptionZZZ;	
		
		public String computeJagged_(IEnumSetMappedStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ;
		public String computeJagged_(IEnumSetMappedStringFormatZZZ[] ienumaFormatLogString, String... sLogs) throws ExceptionZZZ;
		
		public String computeJagged_(Object obj, IEnumSetMappedStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ;
		public String computeJagged_(Class classObj, IEnumSetMappedStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ;
		public String computeJagged_(Object obj, IEnumSetMappedStringFormatZZZ ienumFormatLogString, String... sLogs) throws ExceptionZZZ;		
		public String computeJagged_(Class classObj, IEnumSetMappedStringFormatZZZ ienumFormatLogString, String... sLogs) throws ExceptionZZZ;
		public String computeJagged_(Object obj, IEnumSetMappedStringFormatZZZ[] ienumaFormatLogString, String... sLogs) throws ExceptionZZZ;
		public String computeJagged_(Class classObj, IEnumSetMappedStringFormatZZZ[] ienumaFormatLogString, String... sLogs) throws ExceptionZZZ;

		
}

