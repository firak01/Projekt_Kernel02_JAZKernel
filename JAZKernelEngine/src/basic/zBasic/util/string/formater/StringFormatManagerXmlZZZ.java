package basic.zBasic.util.string.formater;

import java.util.LinkedHashMap;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.string.justifier.IStringJustifierManagerZZZ;
import basic.zBasic.util.string.justifier.StringJustifierManagerZZZ;

public class StringFormatManagerXmlZZZ extends AbstractStringFormatManagerZZZ{
	private static final long serialVersionUID = 5164996113432507434L;

	// --- Singleton Instanz ---
	//muss als Singleton static sein. //Muss in der Konkreten Manager Klasse definiert sein, da ja unterschiedlich
	protected static IStringFormatManagerZZZ objLogStringManagerINSTANCE=null;

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
	
	
	//als private deklariert, damit man es nicht so instanzieren kann, sonder die Methode .getInstance() verwenden muss
	protected StringFormatManagerXmlZZZ() throws ExceptionZZZ{
		super();
	}
	
	public static synchronized IStringFormatManagerZZZ getInstance() throws ExceptionZZZ{
		//siehe: https://www.digitalocean.com/community/tutorials/java-singleton-design-pattern-best-practices-examples
		//Threadsafe sicherstellen, dass nur 1 Instanz geholt wird. Hier doppelter Check mit synchronized, was performanter sein soll als die ganze Methode synchronized zu machen.
		synchronized(StringFormatManagerXmlZZZ.class) {
			if(objLogStringManagerINSTANCE == null) {
				if (INITIALIZED) {
		            throw new ExceptionZZZ(new IllegalStateException("Singleton already initialized"));
		        }
				objLogStringManagerINSTANCE = getNewInstance();
				INITIALIZED=true;
			}
		}
		return (IStringFormatManagerZZZ) objLogStringManagerINSTANCE;
	}
	
	public static IStringFormatManagerZZZ getNewInstance() throws ExceptionZZZ{
		//Damit wird garantiert einen neue, frische Instanz geholt.
		//Z.B. bei JUnit Tests ist das notwendig, denn in Folgetests wird mit .getInstance() doch tatsächlich mit dem Objekt des vorherigen Tests gearbeitet.
		objLogStringManagerINSTANCE = new StringFormatManagerXmlZZZ();
		return (IStringFormatManagerZZZ)objLogStringManagerINSTANCE;
	}
	
	public static synchronized void destroyInstance() throws ExceptionZZZ{
		objLogStringManagerINSTANCE = null;
	}
	
	
	//#################################################################
	//### COMPUTE
	//#################################################################
	@Override
	public String compute(IEnumSetMappedStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		IStringFormaterZZZ objFormater = new StringFormaterZZZ();
		return objFormater.compute(ienumFormatLogString);
	}

	@Override
	public String compute(Object obj, IEnumSetMappedStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {		
		IStringFormaterZZZ objFormater = new StringFormaterZZZ();
		return objFormater.compute(obj, ienumFormatLogString);
	}
	
	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

	@Override
	public String compute(String... sLogs) throws ExceptionZZZ {		
		IStringFormaterZZZ objFormater = new StringFormaterZZZ();
		return objFormater.compute(null, (IEnumSetMappedStringFormatZZZ[]) null, sLogs);
	}
	
	@Override
	public String compute(IEnumSetMappedStringFormatZZZ[] ienumaFormatLogString, String... sLogs)	throws ExceptionZZZ {
		IStringFormaterZZZ objFormater = new StringFormaterZZZ();
		return objFormater.compute(ienumaFormatLogString, sLogs);
	}

	@Override
	public String compute(Object obj, IEnumSetMappedStringFormatZZZ ienumFormatLogString, String... sLogs) throws ExceptionZZZ {
		IStringFormaterZZZ objFormater = new StringFormaterZZZ();
		return objFormater.compute(obj, ienumFormatLogString, sLogs);
	}

	@Override
	public String compute(Object obj, IEnumSetMappedStringFormatZZZ[] ienumaFormatLogString, String... sLogs) throws ExceptionZZZ {
		IStringFormaterZZZ objFormater = new StringFormaterZZZ();
		return objFormater.compute(obj, ienumaFormatLogString, sLogs);
	}

	@Override
	public String compute(Class classObj, IEnumSetMappedStringFormatZZZ ienumFormatLogString, String... sLogs) throws ExceptionZZZ {
		IStringFormaterZZZ objFormater = new StringFormaterZZZ();
		return objFormater.compute(classObj, ienumFormatLogString, sLogs);
	}

	@Override
	public String compute(Class classObj, IEnumSetMappedStringFormatZZZ[] ienumaFormatLogString, String... sLogs) throws ExceptionZZZ {
		IStringFormaterZZZ objFormater = new StringFormaterZZZ();
		return objFormater.compute(classObj, ienumaFormatLogString, sLogs);
	}

	@Override
	public String compute(Class classObj, IEnumSetMappedStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		IStringFormaterZZZ objFormater = new StringFormaterZZZ();
		return objFormater.compute(classObj, ienumFormatLogString);
	}

	@Override
	public String compute(LinkedHashMap<IEnumSetMappedStringFormatZZZ, String> hm) throws ExceptionZZZ {
		IStringFormaterZZZ objFormater = new StringFormaterZZZ();
		return objFormater.compute(hm);
	}

	@Override
	public String compute(Object obj, LinkedHashMap<IEnumSetMappedStringFormatZZZ, String> hm) throws ExceptionZZZ {
		IStringFormaterZZZ objFormater = new StringFormaterZZZ();
		return objFormater.compute(obj, hm);
	}

	@Override
	public String compute(Class classObj, LinkedHashMap<IEnumSetMappedStringFormatZZZ, String> hm) throws ExceptionZZZ {
		IStringFormaterZZZ objFormater = new StringFormaterZZZ();
		return objFormater.compute(classObj, hm);
	}

	@Override
	public String compute(Object obj, String... sLogs) throws ExceptionZZZ {
		IStringFormaterZZZ objFormater = new StringFormaterZZZ();
		return objFormater.compute(obj, sLogs);
	}

	@Override
	public String compute(Class classObj, String... sLogs) throws ExceptionZZZ {
		IStringFormaterZZZ objFormater = new StringFormaterZZZ();
		return objFormater.compute(classObj, sLogs);
	}
	
	//######################
	@Override
	public String compute(IStringFormaterZZZ objFormater, IEnumSetMappedStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		String sReturn = null;
		main:{			
			sReturn = objFormater.computeJagged_(ienumFormatLogString);
		}//end main:
		return sReturn;		
	}

	@Override
	public String compute(IStringFormaterZZZ objFormater, Object obj, IEnumSetMappedStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			sReturn = objFormater.computeJagged_(obj, ienumFormatLogString);							
		}//end main:
		return sReturn;			
	}

	@Override
	public String compute(IStringFormaterZZZ objFormater, IEnumSetMappedStringFormatZZZ[] ienumaFormatLogString, String... sLogs) throws ExceptionZZZ {
		String sReturn = null;
		main:{			
			sReturn = objFormater.computeJagged_(ienumaFormatLogString, sLogs);						
		}//end main:
		return sReturn;	
	}

	@Override
	public String compute(IStringFormaterZZZ objFormater, Object obj, IEnumSetMappedStringFormatZZZ ienumFormatLogString, String... sLogs) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			sReturn = objFormater.computeJagged_(obj, ienumFormatLogString, sLogs);						
		}//end main:
		return sReturn;			
	}

	@Override
	public String compute(IStringFormaterZZZ objFormater, Object obj, IEnumSetMappedStringFormatZZZ[] ienumaFormatLogString, String... sLogs) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			sReturn = objFormater.computeJagged_(obj, ienumaFormatLogString, sLogs);						
		}//end main:
		return sReturn;			
	}

	@Override
	public String compute(IStringFormaterZZZ objFormater, Class classObj, IEnumSetMappedStringFormatZZZ ienumFormatLogString, String... sLogs) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			sReturn = objFormater.computeJagged_(classObj, ienumFormatLogString, sLogs);					
		}//end main:
		return sReturn;			
	}

	@Override
	public String compute(IStringFormaterZZZ objFormater, Class classObj, IEnumSetMappedStringFormatZZZ[] ienumaFormatLogString, String... sLogs) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			sReturn = objFormater.computeJagged_(classObj, ienumaFormatLogString, sLogs);						
		}//end main:
		return sReturn;		
	}

	@Override
	public String compute(IStringFormaterZZZ objFormater, Class classObj, IEnumSetMappedStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			//... die konkrete Formatanweisung wurde schon aus einem Array der "normierten" Formatanweisungen geholt.
			sReturn = objFormater.computeJagged_(classObj, ienumFormatLogString);							
		}//end main:
		return sReturn;				
	}

	@Override
	public String compute(IStringFormaterZZZ objFormater, LinkedHashMap<IEnumSetMappedStringFormatZZZ, String> hm) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			sReturn = objFormater.computeJagged_(hm);		
		}//end main:
		return sReturn;		
	}

	@Override
	public String compute(IStringFormaterZZZ objFormater, Object obj, LinkedHashMap<IEnumSetMappedStringFormatZZZ, String> hm) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			sReturn = objFormater.computeJagged_(obj, hm);		
		}//end main:
		return sReturn;			
	}

	@Override
	public String compute(IStringFormaterZZZ objFormater, Class classObj, LinkedHashMap<IEnumSetMappedStringFormatZZZ, String> hm) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			sReturn = objFormater.computeJagged_(classObj, hm);	
		}//end main:
		return sReturn;		
	}

	@Override
	public String compute(IStringFormaterZZZ objFormater, Object obj, String... sLogs) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			sReturn = objFormater.computeJagged_(obj, sLogs);
		}//end main:
		return sReturn;				
	}

	@Override
	public String compute(IStringFormaterZZZ objFormater, Class classObj, String... sLogs) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			sReturn = objFormater.computeJagged_(classObj, sLogs);		
		}//end main:
		return sReturn;		
	}
}
