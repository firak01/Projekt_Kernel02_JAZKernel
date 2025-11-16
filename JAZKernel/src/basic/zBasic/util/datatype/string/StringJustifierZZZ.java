package basic.zBasic.util.datatype.string;

import basic.zBasic.AbstractObjectWithExceptionZZZ;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.log.ILogStringFormatManagerZZZ;
import basic.zBasic.util.log.LogStringFormatManagerXmlZZZ;

public class StringJustifierZZZ extends AbstractStringJustifierZZZ {
	private static final long serialVersionUID = 1931006668388552859L;
	
	// --- Singleton Instanz ---
	//muss als Singleton static sein. //Muss in der Konkreten Manager Klasse definiert sein, da ja unterschiedlich
	protected static IStringJustifierZZZ objStringJustifierINSTANCE;
	
	
	//als private deklariert, damit man es nicht so instanzieren kann, sonder die Methode .getInstance() verwenden muss
	protected StringJustifierZZZ() throws ExceptionZZZ{
		super();
	}

	public static synchronized IStringJustifierZZZ getInstance() throws ExceptionZZZ{
		if(objStringJustifierINSTANCE==null){
			
			//siehe: https://www.digitalocean.com/community/tutorials/java-singleton-design-pattern-best-practices-examples
			//Threadsafe sicherstellen, dass nur 1 Instanz geholt wird. Hier doppelter Check mit synchronized, was performanter sein soll als die ganze Methode synchronized zu machen.
			synchronized(StringJustifierZZZ.class) {
				if(objStringJustifierINSTANCE == null) {
					objStringJustifierINSTANCE = getNewInstance();
				}
			}
			
		}
		return (IStringJustifierZZZ) objStringJustifierINSTANCE;
	}
	
	public static synchronized IStringJustifierZZZ getNewInstance() throws ExceptionZZZ{
		//Damit wird garantiert einen neue, frische Instanz geholt.
		//Z.B. bei JUnit Tests ist das notwendig, denn in Folgetests wird mit .getInstance() doch tatsächlich mit dem Objekt des vorherigen Tests gearbeitet.
		objStringJustifierINSTANCE = new StringJustifierZZZ();
		return (IStringJustifierZZZ)objStringJustifierINSTANCE;
	}
	
	public static synchronized void destroyInstance() throws ExceptionZZZ{
		objStringJustifierINSTANCE = null;
	}
	
	//##########################################################
	//### METHODEN ##########
	
	
	//### STATIC METHODEN
	
}
