package basic.zBasic.util.log;

import java.util.HashMap;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.datatype.string.StringArrayZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;

public class LogStringFormater4ReflectCodeZZZ extends AbstractLogStringFormaterZZZ{
	private static final long serialVersionUID = 5164996113432507434L;
	
	public LogStringFormater4ReflectCodeZZZ() throws ExceptionZZZ{		
		super();
	}
	
	
	
	@Override
	public IEnumSetMappedLogStringFormatZZZ[] getFormatPositionsMappedCustom() {
		//TODOGOON20240503: Eine Sortierung des Arrays ist wünschenswert.
		//                  Zuerst die ohne Argument, dann die mit Objekt als Argument, dann erst die Stringbasierten Argumente.
		IEnumSetMappedLogStringFormatZZZ[] iaenumReturn = {
				ILogStringFormatZZZ.LOGSTRINGFORMAT.CLASSMETHOD_XML,
				ILogStringFormatZZZ.LOGSTRINGFORMAT.LINENEXT,
				ILogStringFormatZZZ.LOGSTRINGFORMAT.CLASSFILEPOSITION_XML,
				ILogStringFormatZZZ.LOGSTRINGFORMAT.LINENEXT,
		};
		return iaenumReturn;
	}

	@Override
	public HashMap<Integer, String> getHashMapFormatPositionStringCustom() throws ExceptionZZZ {
		HashMap<Integer,String>hmReturn=super.getHashMapFormatPositionStringDefault();
		
		//Hier mögliche Abweichende Strings angeben, z.B. in einfache Hochkommata packen			
		//hmReturn.put(new Integer(ILogStringZZZ.iFACTOR_CLASSNAME),"'" + ILogStringZZZ.LOGSTRING.CLASSNAME.getFormat() + "'");
		
		//und das ist nicht notwendig, dar argnext02 schon einfache hochkommata enthaelt: hmReturn.put(new Integer(ILogStringZZZ.iARGNEXT01), "'" + ILogStringZZZ.sARGNEXT01 + "'");
		//und das bleibt unveraendert hmReturn.put(new Integer(ILogStringZZZ.iTHREAD), ILogStringZZZ.sTHREAD);
		
		
		return hmReturn;
	}
	
	//Wenn ganz einfache Ausgabe gewünscht wird, .getPositionCurrentSimple verwenden
	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++	
}
