package basic.zBasic.util.string.formater;

import java.util.LinkedHashMap;

import basic.zBasic.ExceptionZZZ;

/** Merke: Nur String ohne XML-Inhalt sollten per Justified angeglichen werden.
 *         Die XML-Tags blähen die Zeile auf und der Kommentartrenner wandert ganz weit nach rechts.
 *  
 *  Ziel diese Interface ist, dass der XML-Format Manager diese Methoden nicht zu implementieren braucht.
 *  
 * @author Fritz Lindhauer, 30.11.2025, 09:43:07
 * 
 */
public interface IStringFormatManagerComputerJustifiedZZZ extends IStringFormatComputerJustifiedZZZ {

	
		//...justified Variante wichtiger Methoden, mit objFormater		
		public String computeJustified(IStringFormaterZZZ objFormater, Object obj, IEnumSetMappedStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ;
		public String computeJustified(IStringFormaterZZZ objFormater, IEnumSetMappedStringFormatZZZ[] ienumaFormatLogString, String... sLogs) throws ExceptionZZZ;
		public String computeJustified(IStringFormaterZZZ objFormater, Object obj, IEnumSetMappedStringFormatZZZ ienumFormatLogString, String... sLogs) throws ExceptionZZZ;	
		public String computeJustified(IStringFormaterZZZ objFormater, Object obj, IEnumSetMappedStringFormatZZZ[] ienumaFormatLogString, String... sLogs) throws ExceptionZZZ;
		public String computeJustified(IStringFormaterZZZ objFormater, Class classObj, IEnumSetMappedStringFormatZZZ ienumFormatLogString, String... sLogs) throws ExceptionZZZ;
		public String computeJustified(IStringFormaterZZZ objFormater, Class classObj, IEnumSetMappedStringFormatZZZ[] ienumaFormatLogString, String... sLogs) throws ExceptionZZZ;
		public String computeJustified(IStringFormaterZZZ objFormater, Class classObj, IEnumSetMappedStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ;
		public String computeJustified(IStringFormaterZZZ objFormater, LinkedHashMap<IEnumSetMappedStringFormatZZZ, String> hm) throws ExceptionZZZ;
		public String computeJustified(IStringFormaterZZZ objFormater, Object obj, LinkedHashMap<IEnumSetMappedStringFormatZZZ, String> hm) throws ExceptionZZZ;
		public String computeJustified(IStringFormaterZZZ objFormater, Class classObj, LinkedHashMap<IEnumSetMappedStringFormatZZZ, String> hm) throws ExceptionZZZ;
		public String computeJustified(IStringFormaterZZZ objFormater, Object obj, String... sLogs) throws ExceptionZZZ;
		public String computeJustified(IStringFormaterZZZ objFormater, Class classObj, String... sLogs) throws ExceptionZZZ;		
	
		public String computeJustified(IStringFormaterZZZ objFormater, IEnumSetMappedStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ;
}
