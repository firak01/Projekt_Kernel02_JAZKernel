package basic.zBasic.util.log;

import java.util.HashMap;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;

public class LogStringZZZ extends AbstractLogStringZZZ{
	private static final long serialVersionUID = 5164996113432507434L;

		//als private deklariert, damit man es nicht so instanzieren kann, sonder die Methode .getInstance() verwenden muss
		private LogStringZZZ() throws ExceptionZZZ{		
		}
		
		public static LogStringZZZ getInstance() throws ExceptionZZZ{
			if(objLogStringSingleton==null){
				objLogStringSingleton = new LogStringZZZ();
			}
			return (LogStringZZZ) objLogStringSingleton;	
		}
		
		@Override
		public IEnumSetMappedLogStringZZZ[] getFormatPositionsMappedCustom() {
			//TODOGOON20240503: Eine Sortierung des Arrays ist wünschenswert.
			//                  Zuerst die ohne Argument, dann die mit Objekt als Argument, dann erst die Stringbasierten Argumente.
			IEnumSetMappedLogStringZZZ[] iaenumReturn = {
					ILogStringZZZ.LOGSTRING.DATE,
					ILogStringZZZ.LOGSTRING.THREADID,
					ILogStringZZZ.LOGSTRING.CLASSSIMPLENAME,
					ILogStringZZZ.LOGSTRING.ARGNEXT01,					
					ILogStringZZZ.LOGSTRING.ARGNEXT02,
					ILogStringZZZ.LOGSTRING.CLASSMETHOD,
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
