package basic.zBasic.util.log;

import java.util.HashMap;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;

public class LogString4ReflectCodeZZZ extends AbstractLogStringZZZ{
	private static final long serialVersionUID = 5164996113432507434L;
	protected static ILogStringZZZ objLogStringSingletonHERE; //muss als Singleton static sein, und HERE weil das Objekt in AbstractLogString vom Typ LogStringZZZ ist, gibt es dann eine TypeCastException.
	
	//als private deklariert, damit man es nicht so instanzieren kann, sonder die Methode .getInstance() verwenden muss
	private LogString4ReflectCodeZZZ() throws ExceptionZZZ{		
	}
	
	public static LogString4ReflectCodeZZZ getInstance() throws ExceptionZZZ{
		if(objLogStringSingletonHERE==null){
			
			//siehe: https://www.digitalocean.com/community/tutorials/java-singleton-design-pattern-best-practices-examples
			//Threadsafe sicherstellen, dass nur 1 Instanz geholt wird. Hier doppelter Check mit synchronized, was performanter sein soll als die ganze Methode synchronized zu machen.
			synchronized(LogString4ReflectCodeZZZ.class) {
				if(objLogStringSingletonHERE == null) {
					objLogStringSingletonHERE = getNewInstance();
				}
			}
			
		}
		return (LogString4ReflectCodeZZZ) objLogStringSingletonHERE;
	}
	
	public static LogString4ReflectCodeZZZ getNewInstance() throws ExceptionZZZ{
		//Damit wird garantiert einen neue, frische Instanz geholt.
		//Z.B. bei JUnit Tests ist das notwendig, denn in Folgetests wird mit .getInstance() doch tatsächlich mit dem Objekt des vorherigen Tests gearbeitet.
		objLogStringSingletonHERE = new LogString4ReflectCodeZZZ();
		return (LogString4ReflectCodeZZZ)objLogStringSingletonHERE;
	}
	
	public static void destroyInstance() throws ExceptionZZZ{
		objLogStringSingletonHERE = null;
	}
	
	@Override
	public IEnumSetMappedLogStringFormatZZZ[] getFormatPositionsMappedCustom() {
		//TODOGOON20240503: Eine Sortierung des Arrays ist wünschenswert.
		//                  Zuerst die ohne Argument, dann die mit Objekt als Argument, dann erst die Stringbasierten Argumente.
		IEnumSetMappedLogStringFormatZZZ[] iaenumReturn = {
				ILogStringZZZ.LOGSTRING.CLASSMETHOD_REFLECTED,
				ILogStringZZZ.LOGSTRING.ARGNEXT,
				ILogStringZZZ.LOGSTRING.CLASSFILEPOSITION_REFLECTED,
				ILogStringZZZ.LOGSTRING.ARGNEXT,
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

		
}
