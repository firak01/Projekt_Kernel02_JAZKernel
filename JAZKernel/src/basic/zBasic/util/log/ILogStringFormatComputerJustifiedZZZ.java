package basic.zBasic.util.log;

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
public interface ILogStringFormatComputerJustifiedZZZ extends ILogStringFormatComputerZZZ {

	
		//...justified Variante wichtiger Methoden
		public String computeJustified(String... sLogs) throws ExceptionZZZ;
		
		public String computeJustified(IEnumSetMappedLogStringFormatZZZ[] ienumaFormatLogString, String... sLogs) throws ExceptionZZZ;
		
	
		public String computeJustified(LinkedHashMap<IEnumSetMappedLogStringFormatZZZ, String> hm) throws ExceptionZZZ;
		public String computeJustified(Object obj, LinkedHashMap<IEnumSetMappedLogStringFormatZZZ, String> hm) throws ExceptionZZZ;
	
		//sinn? public String computeJustified(IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ;
		//sinn? public String computeJustified(Object obj, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ;
		
		public String computeJustified(Object obj, String... sLogs) throws ExceptionZZZ;
		public String computeJustified(Class classObj, String... sLogs) throws ExceptionZZZ;	
		
		//sinn? public String computeJustified(Class classObj, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ;
	
		//sinn? public String computeJustified(Object obj, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString, String... sLogs) throws ExceptionZZZ;		
		//sinn? public String computeJustified(Class classObj, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString, String... sLogs) throws ExceptionZZZ;
		public String computeJustified(Object obj, IEnumSetMappedLogStringFormatZZZ[] ienumaFormatLogString, String... sLogs) throws ExceptionZZZ;
		public String computeJustified(Class classObj, IEnumSetMappedLogStringFormatZZZ[] ienumFormatLogString, String... sLogs) throws ExceptionZZZ;
}
