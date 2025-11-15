package basic.zBasic.util.log;

import java.util.LinkedHashMap;

import basic.zBasic.ExceptionZZZ;

public class LogStringFormatManagerZZZ extends AbstractLogStringFormatManagerZZZ{
	private static final long serialVersionUID = 5164996113432507434L;

	//als private deklariert, damit man es nicht so instanzieren kann, sonder die Methode .getInstance() verwenden muss
	private LogStringFormatManagerZZZ() throws ExceptionZZZ{		
	}
	
	public static LogStringFormatManagerZZZ getInstance() throws ExceptionZZZ{
		if(objLogStringManagerINSTANCE==null){
			
			//siehe: https://www.digitalocean.com/community/tutorials/java-singleton-design-pattern-best-practices-examples
			//Threadsafe sicherstellen, dass nur 1 Instanz geholt wird. Hier doppelter Check mit synchronized, was performanter sein soll als die ganze Methode synchronized zu machen.
			synchronized(LogStringFormatManagerZZZ.class) {
				if(objLogStringManagerINSTANCE == null) {
					objLogStringManagerINSTANCE = getNewInstance();
				}
			}
			
		}
		return (LogStringFormatManagerZZZ) objLogStringManagerINSTANCE;
	}
	
	public static LogStringFormatManagerZZZ getNewInstance() throws ExceptionZZZ{
		//Damit wird garantiert einen neue, frische Instanz geholt.
		//Z.B. bei JUnit Tests ist das notwendig, denn in Folgetests wird mit .getInstance() doch tatsächlich mit dem Objekt des vorherigen Tests gearbeitet.
		objLogStringManagerINSTANCE = new LogStringFormatManagerZZZ();
		return (LogStringFormatManagerZZZ)objLogStringManagerINSTANCE;
	}
	
	public static void destroyInstance() throws ExceptionZZZ{
		objLogStringManagerINSTANCE = null;
	}
}
