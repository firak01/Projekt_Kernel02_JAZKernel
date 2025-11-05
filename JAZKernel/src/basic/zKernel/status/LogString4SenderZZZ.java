package basic.zKernel.status;

import java.util.HashMap;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.log.AbstractLogStringZZZ;
import basic.zBasic.util.log.IEnumSetMappedLogStringFormatZZZ;
import basic.zBasic.util.log.ILogStringZZZ;

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
public class LogString4SenderZZZ extends AbstractLogStringZZZ{
	private static final long serialVersionUID = 1660001806157846669L;
	protected static ILogStringZZZ objLogStringSingletonHERE; //muss als Singleton static sein, und HERE weil das Objekt in AbstractLogString vom Typ LogStringZZZ ist, gibt es dann eine TypeCastException.

	//als private deklariert, damit man es nicht so instanzieren kann, sonder die Methode .getInstance() verwenden muss
	private LogString4SenderZZZ() throws ExceptionZZZ{		
	}
			
	public static LogString4SenderZZZ getInstance() throws ExceptionZZZ{
		if(objLogStringSingletonHERE==null){
			
			//siehe: https://www.digitalocean.com/community/tutorials/java-singleton-design-pattern-best-practices-examples
			//Threadsafe sicherstellen, dass nur 1 Instanz geholt wird. Hier doppelter Check mit synchronized, was performanter sein soll als die ganze Methode synchronized zu machen.
			synchronized(LogString4SenderZZZ.class) {
				if(objLogStringSingletonHERE == null) {
					objLogStringSingletonHERE = getNewInstance();
				}
			}
			
		}
		return (LogString4SenderZZZ) objLogStringSingletonHERE;
	}
	
	public static LogString4SenderZZZ getNewInstance() throws ExceptionZZZ{
		//Damit wird garantiert einen neue, frische Instanz geholt.
		//Z.B. bei JUnit Tests ist das notwendig, denn in Folgetests wird mit .getInstance() doch tatsächlich mit dem Objekt des vorherigen Tests gearbeitet.
		objLogStringSingletonHERE = new LogString4SenderZZZ();
		return (LogString4SenderZZZ)objLogStringSingletonHERE;
	}
	
	public static void destroyInstance() throws ExceptionZZZ{
		objLogStringSingletonHERE = null;
	}
	
	@Override
	public IEnumSetMappedLogStringFormatZZZ[] getFormatPositionsMappedCustom() {
		//TODOGOON20240503: Eine Sortierung des Arrays ist wünschenswert.
		//                  Zuerst die ohne Argument, dann die mit Objekt als Argument, dann erst die Stringbasierten Argumente.
		IEnumSetMappedLogStringFormatZZZ[] iaenumReturn = {
				//raus, weil dieser String nur eine Ergaenzung des eigentlichen LogString ist, somit waere Datum-Uhrzeit redundant...
				//ILogStringZZZ.LOGSTRING.DATE,
												
				ILogStringZZZ.LOGSTRING.CLASSNAMESIMPLE,
				
				//Bleibt drin aus ueberichtlichkeitsgruenden
				ILogStringZZZ.LOGSTRING.THREADID,
				ILogStringZZZ.LOGSTRING.ARGNEXT01,					
				ILogStringZZZ.LOGSTRING.ARGNEXT02,
				ILogStringZZZ.LOGSTRING.CLASSMETHOD_REFLECTED,
		};
		return iaenumReturn;
	}

	@Override
	public HashMap<Integer, String> getHashMapFormatPositionStringCustom() throws ExceptionZZZ {
		HashMap<Integer,String>hmReturn=super.getHashMapFormatPositionStringDefault();
		
		//Hier mögliche Abweichende Strings angeben, z.B. in einfache Hochkommata packen			
		//hmReturn.put(new Integer(ILogStringZZZ.iFACTOR_CLASSNAME),"'" + ILogStringZZZ.LOGSTRING.CLASSNAME.getFormat() + "'");
		hmReturn.put(new Integer(ILogStringZZZ.iFACTOR_CLASSNAMESIMPLE),"'%s'");
		
		//hmReturn.put(new Integer(ILogStringZZZ.iFACTOR_THREADID), "-" + ILogStringZZZ.LOGSTRING.THREADID.getFormat() + "-");
		hmReturn.put(new Integer(ILogStringZZZ.iFACTOR_THREADID), "- Thread: %s");
		
		//und das ist nicht notwendig, dar argnext02 schon einfache hochkommata enthaelt: hmReturn.put(new Integer(ILogStringZZZ.iARGNEXT01), "'" + ILogStringZZZ.sARGNEXT01 + "'");
		
		return hmReturn;
	}

}
