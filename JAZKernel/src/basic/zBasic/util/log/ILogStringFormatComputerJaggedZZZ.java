package basic.zBasic.util.log;

import java.util.LinkedHashMap;

import basic.zBasic.ExceptionZZZ;

/** Merke: Nur String ohne XML-Inhalt sollten per Jagged angeglichen werden.
 *         Die XML-Tags blähen die Zeile auf und der Kommentartrenner wandert ganz weit nach rechts.
 *  
 *  Ziel diese Interface ist, dass der XML-Format Manager diese Methoden nicht zu implementieren braucht.
 *  
 * @author Fritz Lindhauer, 30.11.2025, 09:43:07
 * 
 */
public interface ILogStringFormatComputerJaggedZZZ extends ILogStringFormatComputerZZZ {

	
		//...Jagged Variante wichtiger Methoden
		public String computeJagged(String... sLogs) throws ExceptionZZZ;
	
		public String computeJagged(LinkedHashMap<IEnumSetMappedLogStringFormatZZZ, String> hm) throws ExceptionZZZ;
		public String computeJagged(Object obj, LinkedHashMap<IEnumSetMappedLogStringFormatZZZ, String> hm) throws ExceptionZZZ;
		public String computeJagged(Class classObj, LinkedHashMap<IEnumSetMappedLogStringFormatZZZ, String> hmLog) throws ExceptionZZZ;
		
		public String computeJagged(Object obj, String... sLogs) throws ExceptionZZZ;
		public String computeJagged(Class classObj, String... sLogs) throws ExceptionZZZ;	
		
		public String computeJagged(IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ;
		public String computeJagged(Object obj, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ;
		public String computeJagged(Class classObj, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ;
		public String computeJagged(Object obj, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString, String... sLogs) throws ExceptionZZZ;		
		public String computeJagged(Class classObj, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString, String... sLogs) throws ExceptionZZZ;
		public String computeJagged(Object obj, IEnumSetMappedLogStringFormatZZZ[] ienumaFormatLogString, String... sLogs) throws ExceptionZZZ;
		public String computeJagged(Class classObj, IEnumSetMappedLogStringFormatZZZ[] ienumFormatLogString, String... sLogs) throws ExceptionZZZ;
}
