package basic.zBasic.util.string.justifier;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.string.formater.IStringFormatManagerJustifiedZZZ;
import basic.zBasic.util.string.formater.IStringFormatManagerZZZ;
import basic.zBasic.util.string.formater.StringFormatManagerZZZ;

public class StringJustifierManagerZZZ extends AbstractStringJustifierManagerZZZ{

	// --- Singleton Instanz ---
	//muss als Singleton static sein. //Muss in der Konkreten Manager Klasse definiert sein, da ja unterschiedlich		
	protected static IStringJustifierManagerZZZ objJustifierManagerINSTANCE=null;
	
	//##########################################################
		//Trick, um Mehrfachinstanzen zu verhindern (optional)
		//Warum das funktioniert:
		//initialized ist static → nur einmal pro ClassLoader
		//Wird beim ersten Konstruktoraufruf gesetzt
		//Jeder weitere Versuch (Reflection!) schlägt fehl
	    private static boolean INITIALIZED = false;
	    
	    //Reflection-Schutz ist eine Hürde, kein Sicherheitsmechanismus.
	    //Denn:
	    //Field f = AbstractService.class.getDeclaredField("initialized");
	    //f.setAccessible(true);
	    //f.set(null, false);
	    //Danach kann man wieder instanziieren.
		//##########################################################
		    
		//als private deklariert, damit man es nicht so instanzieren kann, sondern die Methode .getInstance() verwenden muss
		private StringJustifierManagerZZZ() throws ExceptionZZZ{
			super();
		}
		
		public static IStringJustifierManagerZZZ getInstance() throws ExceptionZZZ{
			//siehe: https://www.digitalocean.com/community/tutorials/java-singleton-design-pattern-best-practices-examples
			//Threadsafe sicherstellen, dass nur 1 Instanz geholt wird. Hier doppelter Check mit synchronized, was performanter sein soll als die ganze Methode synchronized zu machen.
			synchronized(StringJustifierManagerZZZ.class) {
				if(objJustifierManagerINSTANCE == null) {
					if (INITIALIZED) {
			            throw new ExceptionZZZ(new IllegalStateException("Singleton already initialized"));
			        }
					objJustifierManagerINSTANCE = getNewInstance();
					INITIALIZED=true;
				}
			}
			return (IStringJustifierManagerZZZ) objJustifierManagerINSTANCE;
		}
		
		public static StringJustifierManagerZZZ getNewInstance() throws ExceptionZZZ{
			//Damit wird garantiert einen neue, frische Instanz geholt.
			//Z.B. bei JUnit Tests ist das notwendig, denn in Folgetests wird mit .getInstance() doch tatsächlich mit dem Objekt des vorherigen Tests gearbeitet.
			objJustifierManagerINSTANCE = new StringJustifierManagerZZZ();
			return (StringJustifierManagerZZZ)objJustifierManagerINSTANCE;
		}
		
		public static synchronized void destroyInstance() throws ExceptionZZZ{
			objJustifierManagerINSTANCE = null;
		}
		
		 // Trick, um Mehrfachinstanzen zu verhindern (optional)
	    private static class HolderAlreadyInitializedHolder {
	        private static final boolean INITIALIZED = true;
	    }
	    
		//################################################
		//### Methoden
	   
}
