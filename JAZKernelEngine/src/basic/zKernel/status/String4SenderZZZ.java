package basic.zKernel.status;

import java.util.HashMap;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.string.formater.AbstractStringFormaterZZZ;
import basic.zBasic.util.string.formater.IEnumSetMappedStringFormatZZZ;
import basic.zBasic.util.string.formater.IStringFormatZZZ;

/** Alternatives LogString-Format zu LogStringZZZ.
 *  Dieses ist dafuer gedacht in einem "Sender / EventBroker" Objekt 
 *  einen String zu definieren, der Details für das Objekt/die Klasse auszugeben,
 *  fuer die Gerade der Event "gefeuert" wird.
 *  - Darum fehlt hier sinnvollerweise Datum-Uhrzeit am Anfang
 *  - Der Thread steht wg. der Uebersichtlichkeit drin
 * 
 * @author Fritz Lindhauer, 11.05.2024, 18:12:55
 * 
 */
public class String4SenderZZZ extends AbstractStringFormaterZZZ{
	private static final long serialVersionUID = 1660001806157846669L;
	
	//als private deklariert, damit man es nicht so instanzieren kann, sonder die Methode .getInstance() verwenden muss
	public String4SenderZZZ() throws ExceptionZZZ{		
	}
		
	@Override
	public IEnumSetMappedStringFormatZZZ[] getFormatPositionsMappedCustom() {
		//TODOGOON20240503: Eine Sortierung des Arrays ist wünschenswert.
		//                  Zuerst die ohne Argument, dann die mit Objekt als Argument, dann erst die Stringbasierten Argumente.
		IEnumSetMappedStringFormatZZZ[] iaenumReturn = {
				//raus, weil dieser String nur eine Ergaenzung des eigentlichen LogString ist, somit waere Datum-Uhrzeit redundant...
				//ILogStringZZZ.LOGSTRING.DATE,
												
				IStringFormatZZZ.LOGSTRINGFORMAT.CLASSNAMESIMPLE_STRING,
				
				//Bleibt drin aus ueberichtlichkeitsgruenden
				IStringFormatZZZ.LOGSTRINGFORMAT.THREADID_STRING,
				IStringFormatZZZ.LOGSTRINGFORMAT.STRINGTYPE01_STRING_BY_STRING,					
				IStringFormatZZZ.LOGSTRINGFORMAT.STRINGTYPE02_STRING_BY_STRING,
				IStringFormatZZZ.LOGSTRINGFORMAT.CLASSMETHOD_XML_BY_XML,
		};
		return iaenumReturn;
	}

	@Override
	public HashMap<Integer, String> getHashMapFormatPositionStringCustom() throws ExceptionZZZ {
		HashMap<Integer,String>hmReturn=super.getHashMapFormatPositionStringDefault();
		
		//Hier mögliche Abweichende Strings angeben, z.B. in einfache Hochkommata packen			
		//hmReturn.put(new Integer(ILogStringZZZ.iFACTOR_CLASSNAME),"'" + ILogStringZZZ.LOGSTRING.CLASSNAME.getFormat() + "'");
		hmReturn.put(new Integer(IStringFormatZZZ.iFACTOR_CLASSNAMESIMPLE_STRING),"'%s'");
		
		//hmReturn.put(new Integer(ILogStringZZZ.iFACTOR_THREADID), "-" + ILogStringZZZ.LOGSTRING.THREADID.getFormat() + "-");
		hmReturn.put(new Integer(IStringFormatZZZ.iFACTOR_THREADID_STRING), "- Thread: %s");
		
		//und das ist nicht notwendig, dar argnext02 schon einfache hochkommata enthaelt: hmReturn.put(new Integer(ILogStringZZZ.iARGNEXT01), "'" + ILogStringZZZ.sARGNEXT01 + "'");
		
		return hmReturn;
	}

}
