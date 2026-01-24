package basic.zBasic.util.log;

import java.util.LinkedHashMap;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractList.ArrayListZZZ;
import basic.zBasic.util.datatype.string.IStringJustifierZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.datatype.xml.XmlUtilTagByTypeZZZ;
import basic.zBasic.xml.tagtype.ITagByTypeZZZ;
import basic.zBasic.xml.tagtype.ITagTypeZZZ;
import basic.zBasic.xml.tagtype.TagByTypeFactoryZZZ;

public class LogStringFormatManagerZZZ extends AbstractLogStringFormatManagerZZZ implements ILogStringFormatManagerJustifiedZZZ{
	private static final long serialVersionUID = 5164996113432507434L;

	// --- Singleton Instanz ---
	//muss als Singleton static sein. //Muss in der Konkreten Manager Klasse definiert sein, da ja unterschiedlich
	protected static ILogStringFormatManagerZZZ objLogStringManagerINSTANCE=null;

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
	private LogStringFormatManagerZZZ() throws ExceptionZZZ{
		super();
	}
	
	public static ILogStringFormatManagerJustifiedZZZ getInstance() throws ExceptionZZZ{
		//siehe: https://www.digitalocean.com/community/tutorials/java-singleton-design-pattern-best-practices-examples
		//Threadsafe sicherstellen, dass nur 1 Instanz geholt wird. Hier doppelter Check mit synchronized, was performanter sein soll als die ganze Methode synchronized zu machen.
		synchronized(LogStringFormatManagerZZZ.class) {
			if(objLogStringManagerINSTANCE == null) {
				if (INITIALIZED) {
		            throw new ExceptionZZZ(new IllegalStateException("Singleton already initialized"));
		        }
				objLogStringManagerINSTANCE = getNewInstance();
				INITIALIZED=true;
			}
		}
		return (ILogStringFormatManagerJustifiedZZZ) objLogStringManagerINSTANCE;
	}
	
	public static LogStringFormatManagerZZZ getNewInstance() throws ExceptionZZZ{
		//Damit wird garantiert einen neue, frische Instanz geholt.
		//Z.B. bei JUnit Tests ist das notwendig, denn in Folgetests wird mit .getInstance() doch tatsächlich mit dem Objekt des vorherigen Tests gearbeitet.
		objLogStringManagerINSTANCE = new LogStringFormatManagerZZZ();
		return (LogStringFormatManagerZZZ)objLogStringManagerINSTANCE;
	}
	
	public static synchronized void destroyInstance() throws ExceptionZZZ{
		objLogStringManagerINSTANCE = null;
	}
	
	 // Trick, um Mehrfachinstanzen zu verhindern (optional)
    private static class HolderAlreadyInitializedHolder {
        private static final boolean INITIALIZED = true;
    }
    
	//################################################
	//### Methoden
	
	//++++++++++++++++++++++
	//+++ Wie von der Eltern-/Abstrakten Klasse
	//+++ Hier: Entferne aus dem Rückgabestring die XML Tags, die in ReflectCodeZZZ.getPositionCurrent() erzeugt worden sind.
	@Override
	public synchronized String compute(IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		String sReturn = this.computeJustified(ienumFormatLogString);	
		return sReturn;
	}
		
	@Override
	public synchronized String compute(String... sLogs) throws ExceptionZZZ {
		String sReturn = this.computeJustified(sLogs);
		return sReturn;
	}
	
	@Override
	public synchronized String computeJustified(String... sLogs) throws ExceptionZZZ {
		String sReturn = super.computeJagged(sLogs);
		sReturn = ReflectCodeZZZ.removePositionCurrentTagPartsFrom(sReturn);
		
		//### Versuch den Infoteil ueber alle Zeilen buendig zu halten
	    //WICHTIG1: DAS ERST NACHDEM ALLE STRING-TEILE, ALLER FORMATSTYPEN ABGEARBEITET WURDEN UND ZUSAMMENGESETZT WORDEN SIND.
		//WICHTIG2: DAHER AUCH NACH DEM ENTFERNEN DER XML-TAGS NEU AUSRECHNEN
		
		//OHNE FORMATANWEISUNGSSTRING DIE DEFAULTREIHENFOLGE DER FILTER NEHMEN
		ArrayListZZZ<IStringJustifierZZZ> listaStringJustifier = this.getStringJustifierListDefault();
		this.setStringJustifierList(listaStringJustifier);
		for(int icount=0; icount<=listaStringJustifier.size()-1;icount++) {
			IStringJustifierZZZ objJustifier = this.getStringJustifier(icount);		
			this.getStringJustifierListUsed().add(objJustifier);
			sReturn = LogStringFormaterUtilZZZ.justifyInfoPart(objJustifier, sReturn);
		}	
		return sReturn;
	}
	
	@Override
	public synchronized String compute(IEnumSetMappedLogStringFormatZZZ[] ienumaFormatLogString, String... sLogs)	throws ExceptionZZZ {
		String sReturn = super.computeJagged(ienumaFormatLogString, sLogs);
		sReturn = ReflectCodeZZZ.removePositionCurrentTagPartsFrom(sReturn);
		
		//### Versuch den Infoteil ueber alle Zeilen buendig zu halten
	    //WICHTIG1: DAS ERST NACHDEM ALLE STRING-TEILE, ALLER FORMATSTYPEN ABGEARBEITET WURDEN UND ZUSAMMENGESETZT WORDEN SIND.
		//WICHTIG2: DAHER AUCH NACH DEM ENTFERNEN DER XML-TAGS NEU AUSRECHNEN
		
		ArrayListZZZ<IStringJustifierZZZ> listaStringJustifier = this.getStringJustifierListFiltered(ienumaFormatLogString);
		for(int icount=0; icount<=listaStringJustifier.size()-1;icount++) {
			IStringJustifierZZZ objJustifier = this.getStringJustifier(icount);			
			sReturn = LogStringFormaterUtilZZZ.justifyInfoPart(objJustifier, sReturn);
		}
		return sReturn;
	}

//	@Override
//	public synchronized String compute(Object obj, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString, String... sLogs) throws ExceptionZZZ {
//		String sReturn = this.computeJustified(obj, ienumFormatLogString, sLogs);	
//		return sReturn;
//	}
	
//	@Override
//	public synchronized String computeJustified(Object obj, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString, String... sLogs) throws ExceptionZZZ {
//		String sReturn = super.computeJagged(obj, ienumFormatLogString, sLogs);
//		sReturn = ReflectCodeZZZ.removePositionCurrentTagPartsFrom(sReturn);
//		
//		//### Versuch den Infoteil ueber alle Zeilen buendig zu halten
//	    //WICHTIG1: DAS ERST NACHDEM ALLE STRING-TEILE, ALLER FORMATSTYPEN ABGEARBEITET WURDEN UND ZUSAMMENGESETZT WORDEN SIND.
//		//WICHTIG2: DAHER AUCH NACH DEM ENTFERNEN DER XML-TAGS NEU AUSRECHNEN
//		
//		ArrayListZZZ<IStringJustifierZZZ> listaStringJustifier = this.getStringJustifierList(ienumFormatLogString);
//		for(int icount=0; icount<=listaStringJustifier.size()-1;icount++) {
//			IStringJustifierZZZ objJustifier = this.getStringJustifier(icount);			
//			sReturn = LogStringFormaterUtilZZZ.justifyInfoPart(objJustifier, sReturn);
//		}
//		return sReturn;
//	}

	@Override
	public synchronized String compute(Object obj, IEnumSetMappedLogStringFormatZZZ[] ienumaFormatLogString, String... sLogs) throws ExceptionZZZ {
		String sReturn = this.computeJustified(obj, ienumaFormatLogString, sLogs);	
		return sReturn;
	}
	
	@Override
	public synchronized String computeJustified(Object obj, IEnumSetMappedLogStringFormatZZZ[] ienumaFormatLogString, String... sLogs) throws ExceptionZZZ {
		String sReturn = super.computeJagged(obj, ienumaFormatLogString, sLogs);
		sReturn = ReflectCodeZZZ.removePositionCurrentTagPartsFrom(sReturn);
		
		//### Versuch den Infoteil ueber alle Zeilen buendig zu halten
	    //WICHTIG1: DAS ERST NACHDEM ALLE STRING-TEILE, ALLER FORMATSTYPEN ABGEARBEITET WURDEN UND ZUSAMMENGESETZT WORDEN SIND.
		//WICHTIG2: DAHER AUCH NACH DEM ENTFERNEN DER XML-TAGS NEU AUSRECHNEN
		
		ArrayListZZZ<IStringJustifierZZZ> listaStringJustifier = this.getStringJustifierListFiltered(ienumaFormatLogString);
		this.setStringJustifierList(listaStringJustifier);
		for(int icount=0; icount<=listaStringJustifier.size()-1;icount++) {
			IStringJustifierZZZ objJustifier = this.getStringJustifier(icount);		
			this.getStringJustifierListUsed().add(objJustifier);
			sReturn = LogStringFormaterUtilZZZ.justifyInfoPart(objJustifier, sReturn);
		}	
		return sReturn;
	}

//	@Override
//	public synchronized String compute(Class classObj, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString, String... sLogs) throws ExceptionZZZ {
//		String sReturn = this.computeJustified(classObj, ienumFormatLogString, sLogs);
//		return sReturn;
//		
//	}
	
//	@Override
//	public synchronized String computeJustified(Class classObj, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString, String... sLogs) throws ExceptionZZZ {		
//		String sReturn = super.computeJagged(classObj, ienumFormatLogString, sLogs);
//		sReturn = ReflectCodeZZZ.removePositionCurrentTagPartsFrom(sReturn);
//		
//		//### Versuch den Infoteil ueber alle Zeilen buendig zu halten
//	    //WICHTIG1: DAS ERST NACHDEM ALLE STRING-TEILE, ALLER FORMATSTYPEN ABGEARBEITET WURDEN UND ZUSAMMENGESETZT WORDEN SIND.
//		//WICHTIG2: DAHER AUCH NACH DEM ENTFERNEN DER XML-TAGS NEU AUSRECHNEN
//		
//		ArrayListZZZ<IStringJustifierZZZ> listaStringJustifier = this.getStringJustifierList(ienumFormatLogString);
//		for(int icount=0; icount<=listaStringJustifier.size()-1;icount++) {
//			IStringJustifierZZZ objJustifier = this.getStringJustifier(icount);			
//			sReturn = LogStringFormaterUtilZZZ.justifyInfoPart(objJustifier, sReturn);
//		}	
//		return sReturn;
//	}

	@Override
	public synchronized String compute(Class classObj, IEnumSetMappedLogStringFormatZZZ[] ienumaFormatLogString, String... sLogs) throws ExceptionZZZ {
		String sReturn = this.computeJustified(classObj, ienumaFormatLogString, sLogs);	
		return sReturn;
	}
	
	@Override
	public synchronized String computeJustified(Class classObj, IEnumSetMappedLogStringFormatZZZ[] ienumaFormatLogString, String... sLogs) throws ExceptionZZZ {
		String sReturn = super.computeJagged(classObj, ienumaFormatLogString, sLogs);
		sReturn = ReflectCodeZZZ.removePositionCurrentTagPartsFrom(sReturn);
		
		//### Versuch den Infoteil ueber alle Zeilen buendig zu halten
	    //WICHTIG1: DAS ERST NACHDEM ALLE STRING-TEILE, ALLER FORMATSTYPEN ABGEARBEITET WURDEN UND ZUSAMMENGESETZT WORDEN SIND.
		//WICHTIG2: DAHER AUCH NACH DEM ENTFERNEN DER XML-TAGS NEU AUSRECHNEN
		
		ArrayListZZZ<IStringJustifierZZZ> listaStringJustifier = this.getStringJustifierListFiltered(ienumaFormatLogString);
		this.setStringJustifierList(listaStringJustifier);
		for(int icount=0; icount<=listaStringJustifier.size()-1;icount++) {
			IStringJustifierZZZ objJustifier = this.getStringJustifier(icount);		
			this.getStringJustifierListUsed().add(objJustifier);
			sReturn = LogStringFormaterUtilZZZ.justifyInfoPart(objJustifier, sReturn);
		}		
		return sReturn;
	}

//	@Override
//	public synchronized String computeJustified(Class classObj, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
//		String sReturn = super.computeJagged(classObj, ienumFormatLogString);
//		sReturn = ReflectCodeZZZ.removePositionCurrentTagPartsFrom(sReturn);
//		
//		//### Versuch den Infoteil ueber alle Zeilen buendig zu halten
//	    //WICHTIG1: DAS ERST NACHDEM ALLE STRING-TEILE, ALLER FORMATSTYPEN ABGEARBEITET WURDEN UND ZUSAMMENGESETZT WORDEN SIND.
//		//WICHTIG2: DAHER AUCH NACH DEM ENTFERNEN DER XML-TAGS NEU AUSRECHNEN
//		
//		ArrayListZZZ<IStringJustifierZZZ> listaStringJustifier = this.getStringJustifierList(ienumFormatLogString);
//		for(int icount=0; icount<=listaStringJustifier.size();icount++) {
//			IStringJustifierZZZ objJustifier = this.getStringJustifier(icount);			
//			sReturn = LogStringFormaterUtilZZZ.justifyInfoPart(objJustifier, sReturn);
//		}	
//		return sReturn;
//	}

	@Override
	public synchronized String compute(LinkedHashMap<IEnumSetMappedLogStringFormatZZZ, String> hm) throws ExceptionZZZ {
		String sReturn = super.computeJagged(hm);
		sReturn = ReflectCodeZZZ.removePositionCurrentTagPartsFrom(sReturn);
		
		//### Versuch den Infoteil ueber alle Zeilen buendig zu halten
	    //WICHTIG1: DAS ERST NACHDEM ALLE STRING-TEILE, ALLER FORMATSTYPEN ABGEARBEITET WURDEN UND ZUSAMMENGESETZT WORDEN SIND.
		//WICHTIG2: DAHER AUCH NACH DEM ENTFERNEN DER XML-TAGS NEU AUSRECHNEN
		
		ArrayListZZZ<IStringJustifierZZZ> listaStringJustifier = this.getStringJustifierListFiltered(hm);
		for(int icount=0; icount<=listaStringJustifier.size()-1;icount++) {
			IStringJustifierZZZ objJustifier = this.getStringJustifier(icount);			
			sReturn = LogStringFormaterUtilZZZ.justifyInfoPart(objJustifier, sReturn);
		}
			
		return sReturn;
	}

	@Override
	public synchronized String compute(Object obj, LinkedHashMap<IEnumSetMappedLogStringFormatZZZ, String> hm) throws ExceptionZZZ {
		return this.computeJustified(obj, hm);
//		String sReturn = super.compute(obj, hm);
//		sReturn = ReflectCodeZZZ.removePositionCurrentTagPartsFrom(sReturn);
//		
//		//### Versuch den Infoteil ueber alle Zeilen buendig zu halten
//	    //WICHTIG1: DAS ERST NACHDEM ALLE STRING-TEILE, ALLER FORMATSTYPEN ABGEARBEITET WURDEN UND ZUSAMMENGESETZT WORDEN SIND.
//		//WICHTIG2: DAHER AUCH NACH DEM ENTFERNEN DER XML-TAGS NEU AUSRECHNEN
//		
//		IStringJustifierZZZ objStringJustifier = this.getStringJustifier();
//		sReturn = LogStringFormaterUtilZZZ.justifyInfoPart(objStringJustifier, sReturn);	
//		return sReturn;
	}

	@Override
	public synchronized String compute(Class classObj, LinkedHashMap<IEnumSetMappedLogStringFormatZZZ, String> hm) throws ExceptionZZZ {
		String sReturn = super.computeJagged(classObj, hm);	
		sReturn = ReflectCodeZZZ.removePositionCurrentTagPartsFrom(sReturn);
		
		//### Versuch den Infoteil ueber alle Zeilen buendig zu halten
	    //WICHTIG1: DAS ERST NACHDEM ALLE STRING-TEILE, ALLER FORMATSTYPEN ABGEARBEITET WURDEN UND ZUSAMMENGESETZT WORDEN SIND.
		//WICHTIG2: DAHER AUCH NACH DEM ENTFERNEN DER XML-TAGS NEU AUSRECHNEN
		
		ArrayListZZZ<IStringJustifierZZZ> listaStringJustifier = this.getStringJustifierListFiltered(hm);
		for(int icount=0; icount<=listaStringJustifier.size()-1;icount++) {
			IStringJustifierZZZ objJustifier = this.getStringJustifier(icount);			
			sReturn = LogStringFormaterUtilZZZ.justifyInfoPart(objJustifier, sReturn);
		}
		return sReturn;
	}

	@Override
	public synchronized String compute(Object obj, String... sLogs) throws ExceptionZZZ {
		String sReturn = this.computeJustified(obj, sLogs);
		return sReturn;
	}
	
	@Override
	public synchronized String computeJustified(Object obj, String... sLogs) throws ExceptionZZZ {
		String sReturn = super.computeJagged(obj, sLogs);
		sReturn = ReflectCodeZZZ.removePositionCurrentTagPartsFrom(sReturn);
		
		//### Versuch den Infoteil ueber alle Zeilen buendig zu halten
	    //WICHTIG1: DAS ERST NACHDEM ALLE STRING-TEILE, ALLER FORMATSTYPEN ABGEARBEITET WURDEN UND ZUSAMMENGESETZT WORDEN SIND.
		//WICHTIG2: DAHER AUCH NACH DEM ENTFERNEN DER XML-TAGS NEU AUSRECHNEN
		
		//OHNE FORMATANWEISUNGSSTRING DIE DEFAULTREIHENFOLGE DER FILTER NEHMEN
		ArrayListZZZ<IStringJustifierZZZ> listaStringJustifier = this.getStringJustifierListDefault();				
		this.setStringJustifierList(listaStringJustifier);
		for(int icount=0; icount<=listaStringJustifier.size()-1;icount++) {
			IStringJustifierZZZ objJustifier = this.getStringJustifier(icount);		
			this.getStringJustifierListUsed().add(objJustifier);
			sReturn = LogStringFormaterUtilZZZ.justifyInfoPart(objJustifier, sReturn);
		}		
		return sReturn;
	}
	
	@Override
	public synchronized String compute(Class classObj, String... sLogs) throws ExceptionZZZ {
		String sReturn = this.computeJustified(classObj, sLogs);
		return sReturn;
	}
	
	@Override
	public synchronized String computeJustified(Class classObj, String... sLogs) throws ExceptionZZZ {		
		String sReturn = super.computeJagged(classObj, sLogs);
		sReturn = ReflectCodeZZZ.removePositionCurrentTagPartsFrom(sReturn);	
		
		//### Versuch den Infoteil ueber alle Zeilen buendig zu halten
	    //WICHTIG1: DAS ERST NACHDEM ALLE STRING-TEILE, ALLER FORMATSTYPEN ABGEARBEITET WURDEN UND ZUSAMMENGESETZT WORDEN SIND.
		//WICHTIG2: DAHER AUCH NACH DEM ENTFERNEN DER XML-TAGS NEU AUSRECHNEN
		
		//OHNE FORMATANWEISUNGSSTRING DIE DEFAULTREIHENFOLGE DER FILTER NEHMEN
		ArrayListZZZ<IStringJustifierZZZ> listaStringJustifier = this.getStringJustifierListDefault();
		this.setStringJustifierList(listaStringJustifier);
		for(int icount=0; icount<=listaStringJustifier.size()-1;icount++) {
			IStringJustifierZZZ objJustifier = this.getStringJustifier(icount);		
			this.getStringJustifierListUsed().add(objJustifier);
			sReturn = LogStringFormaterUtilZZZ.justifyInfoPart(objJustifier, sReturn);
		}		
		return sReturn;
	}
	
	//######################
	
	
	
	@Override
	public synchronized String compute(ILogStringFormaterZZZ objFormater, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		String sReturn = this.computeJustified(objFormater, ienumFormatLogString);
		return sReturn;
	}
	
	@Override
	public synchronized String computeJustified(ILogStringFormaterZZZ objFormater, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {		
		String sReturn = super.computeJagged(objFormater, ienumFormatLogString);
		sReturn = ReflectCodeZZZ.removePositionCurrentTagPartsFrom(sReturn);
		
		//### Versuch den Infoteil ueber alle Zeilen buendig zu halten
	    //WICHTIG1: DAS ERST NACHDEM ALLE STRING-TEILE, ALLER FORMATSTYPEN ABGEARBEITET WURDEN UND ZUSAMMENGESETZT WORDEN SIND.
		//WICHTIG2: DAHER AUCH NACH DEM ENTFERNEN DER XML-TAGS NEU AUSRECHNEN
		
		ArrayListZZZ<IStringJustifierZZZ> listaStringJustifier = this.getStringJustifierListFiltered(ienumFormatLogString);
		this.setStringJustifierList(listaStringJustifier);
		for(int icount=0; icount<=listaStringJustifier.size()-1;icount++) {
			IStringJustifierZZZ objJustifier = this.getStringJustifier(icount);		
			this.getStringJustifierListUsed().add(objJustifier);
			sReturn = LogStringFormaterUtilZZZ.justifyInfoPart(objJustifier, sReturn);
		}	
		return sReturn;
	}

	@Override
	public synchronized String compute(ILogStringFormaterZZZ objFormater, Object obj, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		String sReturn = this.computeJustified(objFormater, obj, ienumFormatLogString);
		return sReturn;
	}
	
	@Override
	public synchronized String computeJustified(ILogStringFormaterZZZ objFormater, Object obj, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		String sReturn = super.computeJagged(objFormater, obj, ienumFormatLogString);
		sReturn = ReflectCodeZZZ.removePositionCurrentTagPartsFrom(sReturn);
		
		//### Versuch den Infoteil ueber alle Zeilen buendig zu halten
	    //WICHTIG1: DAS ERST NACHDEM ALLE STRING-TEILE, ALLER FORMATSTYPEN ABGEARBEITET WURDEN UND ZUSAMMENGESETZT WORDEN SIND.
		//WICHTIG2: DAHER AUCH NACH DEM ENTFERNEN DER XML-TAGS NEU AUSRECHNEN
		
		ArrayListZZZ<IStringJustifierZZZ> listaStringJustifier = this.getStringJustifierListFiltered(ienumFormatLogString);
		this.setStringJustifierList(listaStringJustifier);
		for(int icount=0; icount<=listaStringJustifier.size()-1;icount++) {
			IStringJustifierZZZ objJustifier = this.getStringJustifier(icount);		
			this.getStringJustifierListUsed().add(objJustifier);
			sReturn = LogStringFormaterUtilZZZ.justifyInfoPart(objJustifier, sReturn);
		}	
		return sReturn;
	}

	@Override
	public synchronized String compute(ILogStringFormaterZZZ objFormater, IEnumSetMappedLogStringFormatZZZ[] ienumaFormatLogString, String... sLogs) throws ExceptionZZZ {
		String sReturn = this.computeJustified(objFormater, ienumaFormatLogString, sLogs);
		return sReturn;
	}
	
	@Override
	public synchronized String computeJustified(ILogStringFormaterZZZ objFormater, IEnumSetMappedLogStringFormatZZZ[] ienumaFormatLogString, String... sLogs) throws ExceptionZZZ {		
		String sReturn = super.computeJagged(objFormater, ienumaFormatLogString, sLogs);
		sReturn = ReflectCodeZZZ.removePositionCurrentTagPartsFrom(sReturn);
		
		//### Versuch den Infoteil ueber alle Zeilen buendig zu halten
	    //WICHTIG1: DAS ERST NACHDEM ALLE STRING-TEILE, ALLER FORMATSTYPEN ABGEARBEITET WURDEN UND ZUSAMMENGESETZT WORDEN SIND.
		//WICHTIG2: DAHER AUCH NACH DEM ENTFERNEN DER XML-TAGS NEU AUSRECHNEN
		
		ArrayListZZZ<IStringJustifierZZZ> listaStringJustifier = this.getStringJustifierListFiltered(ienumaFormatLogString);
		this.setStringJustifierList(listaStringJustifier);
		for(int icount=0; icount<=listaStringJustifier.size()-1;icount++) {
			IStringJustifierZZZ objJustifier = this.getStringJustifier(icount);		
			this.getStringJustifierListUsed().add(objJustifier);
			sReturn = LogStringFormaterUtilZZZ.justifyInfoPart(objJustifier, sReturn);
		}		
		return sReturn;
	}
	
//	@Override
//	public String computeJustified(ILogStringFormaterZZZ objFormater, LinkedHashMap<IEnumSetMappedLogStringFormatZZZ, String> hmLogString) throws ExceptionZZZ {
//		String sReturn = 
//	    return sReturn;
//	}

	@Override
	public synchronized String compute(ILogStringFormaterZZZ objFormater, Object obj, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString, String... sLogs) throws ExceptionZZZ {
		String sReturn = this.computeJustified(objFormater, obj, ienumFormatLogString, sLogs);
		return sReturn;
	}
	
	@Override
	public synchronized String computeJustified(ILogStringFormaterZZZ objFormater, Object obj, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString, String... sLogs) throws ExceptionZZZ {		
		String sReturn = super.computeJagged(objFormater, obj, ienumFormatLogString, sLogs);
		sReturn = ReflectCodeZZZ.removePositionCurrentTagPartsFrom(sReturn);
		
		//### Versuch den Infoteil ueber alle Zeilen buendig zu halten
	    //WICHTIG1: DAS ERST NACHDEM ALLE STRING-TEILE, ALLER FORMATSTYPEN ABGEARBEITET WURDEN UND ZUSAMMENGESETZT WORDEN SIND.
		//WICHTIG2: DAHER AUCH NACH DEM ENTFERNEN DER XML-TAGS NEU AUSRECHNEN
		
		ArrayListZZZ<IStringJustifierZZZ> listaStringJustifier = this.getStringJustifierListFiltered(ienumFormatLogString);
		this.setStringJustifierList(listaStringJustifier);
		for(int icount=0; icount<=listaStringJustifier.size()-1;icount++) {
			IStringJustifierZZZ objJustifier = this.getStringJustifier(icount);		
			this.getStringJustifierListUsed().add(objJustifier);
			sReturn = LogStringFormaterUtilZZZ.justifyInfoPart(objJustifier, sReturn);
		}	
		return sReturn;
	}

	@Override
	public synchronized String compute(ILogStringFormaterZZZ objFormater, Object obj, IEnumSetMappedLogStringFormatZZZ[] ienumaFormatLogString, String... sLogs) throws ExceptionZZZ {
		String sReturn = this.computeJustified(objFormater, obj, ienumaFormatLogString, sLogs);
		return sReturn;
	}
	
	@Override
	public synchronized String computeJustified(ILogStringFormaterZZZ objFormater, Object obj, IEnumSetMappedLogStringFormatZZZ[] ienumaFormatLogString, String... sLogs) throws ExceptionZZZ {		
		String sReturn = super.computeJagged(objFormater, obj, ienumaFormatLogString, sLogs);
		sReturn = ReflectCodeZZZ.removePositionCurrentTagPartsFrom(sReturn);
		
		//### Versuch den Infoteil ueber alle Zeilen buendig zu halten
	    //WICHTIG1: DAS ERST NACHDEM ALLE STRING-TEILE, ALLER FORMATSTYPEN ABGEARBEITET WURDEN UND ZUSAMMENGESETZT WORDEN SIND.
		//WICHTIG2: DAHER AUCH NACH DEM ENTFERNEN DER XML-TAGS NEU AUSRECHNEN
		
		ArrayListZZZ<IStringJustifierZZZ> listaStringJustifier = this.getStringJustifierListFiltered(ienumaFormatLogString);
		this.setStringJustifierList(listaStringJustifier);
		for(int icount=0; icount<=listaStringJustifier.size()-1;icount++) {
			IStringJustifierZZZ objJustifier = this.getStringJustifier(icount);		
			this.getStringJustifierListUsed().add(objJustifier);
			sReturn = LogStringFormaterUtilZZZ.justifyInfoPart(objJustifier, sReturn);
		}	
		return sReturn;
	}

	@Override
	public synchronized String compute(ILogStringFormaterZZZ objFormater, Class classObj, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString, String... sLogs) throws ExceptionZZZ {
		String sReturn = this.computeJustified(objFormater, classObj, ienumFormatLogString, sLogs);				
		return sReturn;
	}
	
	@Override
	public synchronized String computeJustified(ILogStringFormaterZZZ objFormater, Class classObj, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString, String... sLogs) throws ExceptionZZZ {		
		String sReturn = super.computeJagged(objFormater, classObj, ienumFormatLogString, sLogs);
		sReturn = ReflectCodeZZZ.removePositionCurrentTagPartsFrom(sReturn);
		
		//### Versuch den Infoteil ueber alle Zeilen buendig zu halten
	    //WICHTIG1: DAS ERST NACHDEM ALLE STRING-TEILE, ALLER FORMATSTYPEN ABGEARBEITET WURDEN UND ZUSAMMENGESETZT WORDEN SIND.
		//WICHTIG2: DAHER AUCH NACH DEM ENTFERNEN DER XML-TAGS NEU AUSRECHNEN
		
		ArrayListZZZ<IStringJustifierZZZ> listaStringJustifier = this.getStringJustifierListFiltered(ienumFormatLogString);
		this.setStringJustifierList(listaStringJustifier);
		for(int icount=0; icount<=listaStringJustifier.size()-1;icount++) {
			IStringJustifierZZZ objJustifier = this.getStringJustifier(icount);		
			this.getStringJustifierListUsed().add(objJustifier);
			sReturn = LogStringFormaterUtilZZZ.justifyInfoPart(objJustifier, sReturn);
		}		
		return sReturn;
	}
	
	

	@Override
	public synchronized String compute(ILogStringFormaterZZZ objFormater, Class classObj, IEnumSetMappedLogStringFormatZZZ[] ienumaFormatLogString, String... sLogs) throws ExceptionZZZ {
		String sReturn = this.computeJustified(objFormater, classObj, ienumaFormatLogString, sLogs);		
		return sReturn;
	}
	
	@Override
	public synchronized String computeJustified(ILogStringFormaterZZZ objFormater, Class classObj, IEnumSetMappedLogStringFormatZZZ[] ienumaFormatLogString, String... sLogs) throws ExceptionZZZ {		
		String sReturn = super.computeJagged(objFormater, classObj, ienumaFormatLogString, sLogs);
		sReturn = ReflectCodeZZZ.removePositionCurrentTagPartsFrom(sReturn);		
		
		//### Versuch den Infoteil ueber alle Zeilen buendig zu halten
	    //WICHTIG1: DAS ERST NACHDEM ALLE STRING-TEILE, ALLER FORMATSTYPEN ABGEARBEITET WURDEN UND ZUSAMMENGESETZT WORDEN SIND.
		//WICHTIG2: DAHER AUCH NACH DEM ENTFERNEN DER XML-TAGS NEU AUSRECHNEN
		
		ArrayListZZZ<IStringJustifierZZZ> listaStringJustifier = this.getStringJustifierListFiltered(ienumaFormatLogString);
		this.setStringJustifierList(listaStringJustifier);
		for(int icount=0; icount<=listaStringJustifier.size()-1;icount++) {
			IStringJustifierZZZ objJustifier = this.getStringJustifier(icount);		
			this.getStringJustifierListUsed().add(objJustifier);
			sReturn = LogStringFormaterUtilZZZ.justifyInfoPart(objJustifier, sReturn);
		}		
		return sReturn;
	}

	@Override
	public synchronized String compute(ILogStringFormaterZZZ objFormater, Class classObj, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		String sReturn = this.computeJustified(objFormater, classObj, ienumFormatLogString);	
		return sReturn;
	}
		
	@Override
	public synchronized String computeJustified(ILogStringFormaterZZZ objFormater, Class classObj, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {		
		String sReturn = super.computeJagged(objFormater, classObj, ienumFormatLogString);
		sReturn = ReflectCodeZZZ.removePositionCurrentTagPartsFrom(sReturn);
		
		//### Versuch den Infoteil ueber alle Zeilen buendig zu halten
	    //WICHTIG1: DAS ERST NACHDEM ALLE STRING-TEILE, ALLER FORMATSTYPEN ABGEARBEITET WURDEN UND ZUSAMMENGESETZT WORDEN SIND.
		//WICHTIG2: DAHER AUCH NACH DEM ENTFERNEN DER XML-TAGS NEU AUSRECHNEN
		
		ArrayListZZZ<IStringJustifierZZZ> listaStringJustifier = this.getStringJustifierListFiltered(ienumFormatLogString);
		this.setStringJustifierList(listaStringJustifier);
		for(int icount=0; icount<=listaStringJustifier.size()-1;icount++) {
			IStringJustifierZZZ objJustifier = this.getStringJustifier(icount);		
			this.getStringJustifierListUsed().add(objJustifier);
			sReturn = LogStringFormaterUtilZZZ.justifyInfoPart(objJustifier, sReturn);
		}		
		return sReturn;
	}

	@Override
	public synchronized String compute(ILogStringFormaterZZZ objFormater, LinkedHashMap<IEnumSetMappedLogStringFormatZZZ, String> hm) throws ExceptionZZZ {
		String sReturn = this.computeJustified(objFormater, hm);
		return sReturn;
	}
	
	@Override
	public synchronized String computeJustified(ILogStringFormaterZZZ objFormater, LinkedHashMap<IEnumSetMappedLogStringFormatZZZ, String> hm) throws ExceptionZZZ {		
		String sReturn = super.computeJagged(objFormater, hm);
		sReturn = ReflectCodeZZZ.removePositionCurrentTagPartsFrom(sReturn);
		
		//### Versuch den Infoteil ueber alle Zeilen buendig zu halten
	    //WICHTIG1: DAS ERST NACHDEM ALLE STRING-TEILE, ALLER FORMATSTYPEN ABGEARBEITET WURDEN UND ZUSAMMENGESETZT WORDEN SIND.
		//WICHTIG2: DAHER AUCH NACH DEM ENTFERNEN DER XML-TAGS NEU AUSRECHNEN
		
		ArrayListZZZ<IStringJustifierZZZ> listaStringJustifier = this.getStringJustifierListFiltered(hm);
		this.setStringJustifierList(listaStringJustifier);
		for(int icount=0; icount<=listaStringJustifier.size()-1;icount++) {
			IStringJustifierZZZ objJustifier = this.getStringJustifier(icount);		
			this.getStringJustifierListUsed().add(objJustifier);
			sReturn = LogStringFormaterUtilZZZ.justifyInfoPart(objJustifier, sReturn);
		}		
		return sReturn;
	}

	@Override
	public synchronized String compute(ILogStringFormaterZZZ objFormater, Object obj, LinkedHashMap<IEnumSetMappedLogStringFormatZZZ, String> hm) throws ExceptionZZZ {
		String sReturn = this.computeJustified(objFormater, obj, hm);
		return sReturn;
	}

	@Override
	public synchronized String computeJustified(ILogStringFormaterZZZ objFormater, Object obj, LinkedHashMap<IEnumSetMappedLogStringFormatZZZ, String> hm) throws ExceptionZZZ {		
		String sReturn = super.computeJagged(objFormater, obj, hm);
		sReturn = ReflectCodeZZZ.removePositionCurrentTagPartsFrom(sReturn);
		
		//### Versuch den Infoteil ueber alle Zeilen buendig zu halten
	    //WICHTIG1: DAS ERST NACHDEM ALLE STRING-TEILE, ALLER FORMATSTYPEN ABGEARBEITET WURDEN UND ZUSAMMENGESETZT WORDEN SIND.
		//WICHTIG2: DAHER AUCH NACH DEM ENTFERNEN DER XML-TAGS NEU AUSRECHNEN
		
		ArrayListZZZ<IStringJustifierZZZ> listaStringJustifier = this.getStringJustifierListFiltered(hm);
		this.setStringJustifierList(listaStringJustifier);
		for(int icount=0; icount<=listaStringJustifier.size()-1;icount++) {
			IStringJustifierZZZ objJustifier = this.getStringJustifier(icount);		
			this.getStringJustifierListUsed().add(objJustifier);
			sReturn = LogStringFormaterUtilZZZ.justifyInfoPart(objJustifier, sReturn);
		}	
		return sReturn;
	}
	
	@Override
	public synchronized String compute(ILogStringFormaterZZZ objFormater, Class classObj, LinkedHashMap<IEnumSetMappedLogStringFormatZZZ, String> hm) throws ExceptionZZZ {
		String sReturn = this.computeJustified(objFormater, classObj, hm);
		return sReturn;
	}
	
	@Override
	public synchronized String computeJustified(ILogStringFormaterZZZ objFormater, Class classObj, LinkedHashMap<IEnumSetMappedLogStringFormatZZZ, String> hm) throws ExceptionZZZ {		
		String sReturn = super.computeJagged(objFormater, classObj, hm);
		sReturn = ReflectCodeZZZ.removePositionCurrentTagPartsFrom(sReturn);
		
		//### Versuch den Infoteil ueber alle Zeilen buendig zu halten
	    //WICHTIG1: DAS ERST NACHDEM ALLE STRING-TEILE, ALLER FORMATSTYPEN ABGEARBEITET WURDEN UND ZUSAMMENGESETZT WORDEN SIND.
		//WICHTIG2: DAHER AUCH NACH DEM ENTFERNEN DER XML-TAGS NEU AUSRECHNEN
		
		ArrayListZZZ<IStringJustifierZZZ> listaStringJustifier = this.getStringJustifierListFiltered(hm);
		this.setStringJustifierList(listaStringJustifier);
		for(int icount=0; icount<=listaStringJustifier.size()-1;icount++) {
			IStringJustifierZZZ objJustifier = this.getStringJustifier(icount);		
			this.getStringJustifierListUsed().add(objJustifier);
			sReturn = LogStringFormaterUtilZZZ.justifyInfoPart(objJustifier, sReturn);
		}		
		return sReturn;
	}

	@Override
	public synchronized String compute(ILogStringFormaterZZZ objFormater, Object obj, String... sLogs) throws ExceptionZZZ {
		String sReturn = this.computeJustified(objFormater, obj, sLogs);
		return sReturn;
	}
	
	@Override
	public synchronized String computeJustified(ILogStringFormaterZZZ objFormater, Object obj, String... sLogs) throws ExceptionZZZ {
		String sReturn = super.computeJagged(objFormater, obj, sLogs);
		sReturn = ReflectCodeZZZ.removePositionCurrentTagPartsFrom(sReturn);
		
		//### Versuch den Infoteil ueber alle Zeilen buendig zu halten
	    //WICHTIG1: DAS ERST NACHDEM ALLE STRING-TEILE, ALLER FORMATSTYPEN ABGEARBEITET WURDEN UND ZUSAMMENGESETZT WORDEN SIND.
		//WICHTIG2: DAHER AUCH NACH DEM ENTFERNEN DER XML-TAGS NEU AUSRECHNEN
		
		//OHNE FORMATANWEISUNGSSTRING DIE DEFAULTREIHENFOLGE DER FILTER NEHMEN
		ArrayListZZZ<IStringJustifierZZZ> listaStringJustifier = this.getStringJustifierListDefault();
		this.setStringJustifierList(listaStringJustifier);
		for(int icount=0; icount<=listaStringJustifier.size()-1;icount++) {
			IStringJustifierZZZ objJustifier = this.getStringJustifier(icount);		
			this.getStringJustifierListUsed().add(objJustifier);
			sReturn = LogStringFormaterUtilZZZ.justifyInfoPart(objJustifier, sReturn);
		}	
		return sReturn;
	}
	
	
	@Override
	public synchronized String compute(ILogStringFormaterZZZ objFormater, Class classObj, String... sLogs) throws ExceptionZZZ {
		String sReturn = this.computeJustified(objFormater, classObj, sLogs);	
		return sReturn;
	}
	
	@Override
	public synchronized String computeJustified(ILogStringFormaterZZZ objFormater, Class classObj, String... sLogs) throws ExceptionZZZ {
		String sReturn = super.computeJagged(objFormater, classObj, sLogs);
		sReturn = ReflectCodeZZZ.removePositionCurrentTagPartsFrom(sReturn);	
		
		//### Versuch den Infoteil ueber alle Zeilen buendig zu halten
	    //WICHTIG1: DAS ERST NACHDEM ALLE STRING-TEILE, ALLER FORMATSTYPEN ABGEARBEITET WURDEN UND ZUSAMMENGESETZT WORDEN SIND.
		//WICHTIG2: DAHER AUCH NACH DEM ENTFERNEN DER XML-TAGS NEU AUSRECHNEN
		
		IEnumSetMappedLogStringFormatZZZ[] ienumaFormatLogString = objFormater.getFormatPositionsMapped();
		ArrayListZZZ<IStringJustifierZZZ> listaStringJustifier = this.getStringJustifierListFiltered(ienumaFormatLogString);
		this.setStringJustifierList(listaStringJustifier);
		for(int icount=0; icount<=listaStringJustifier.size()-1;icount++) {
			IStringJustifierZZZ objJustifier = this.getStringJustifier(icount);
			this.getStringJustifierListUsed().add(objJustifier);
			sReturn = LogStringFormaterUtilZZZ.justifyInfoPart(objJustifier, sReturn);		
		}
		return sReturn;
	}

	@Override
	public synchronized String computeJustified(LinkedHashMap<IEnumSetMappedLogStringFormatZZZ, String> hm) throws ExceptionZZZ {
		String sReturn = super.computeJagged(hm);
		sReturn = ReflectCodeZZZ.removePositionCurrentTagPartsFrom(sReturn);	
		
		//### Versuch den Infoteil ueber alle Zeilen buendig zu halten
	    //WICHTIG1: DAS ERST NACHDEM ALLE STRING-TEILE, ALLER FORMATSTYPEN ABGEARBEITET WURDEN UND ZUSAMMENGESETZT WORDEN SIND.
		//WICHTIG2: DAHER AUCH NACH DEM ENTFERNEN DER XML-TAGS NEU AUSRECHNEN
		
		ArrayListZZZ<IStringJustifierZZZ> listaStringJustifier = this.getStringJustifierListFiltered(hm);
		this.setStringJustifierList(listaStringJustifier);
		for(int icount=0; icount<=listaStringJustifier.size()-1;icount++) {
			IStringJustifierZZZ objJustifier = this.getStringJustifier(icount);		
			this.getStringJustifierListUsed().add(objJustifier);
			sReturn = LogStringFormaterUtilZZZ.justifyInfoPart(objJustifier, sReturn);
		}	
		return sReturn;
	}
	
	@Override
	public synchronized String computeJustified(Object obj, LinkedHashMap<IEnumSetMappedLogStringFormatZZZ, String> hm) throws ExceptionZZZ {
		String sReturn = super.computeJagged(obj, hm);
		sReturn = ReflectCodeZZZ.removePositionCurrentTagPartsFrom(sReturn);	
		
		//### Versuch den Infoteil ueber alle Zeilen buendig zu halten
	    //WICHTIG1: DAS ERST NACHDEM ALLE STRING-TEILE, ALLER FORMATSTYPEN ABGEARBEITET WURDEN UND ZUSAMMENGESETZT WORDEN SIND.
		//WICHTIG2: DAHER AUCH NACH DEM ENTFERNEN DER XML-TAGS NEU AUSRECHNEN
		
		ArrayListZZZ<IStringJustifierZZZ> listaStringJustifier = this.getStringJustifierListFiltered(hm);
		this.setStringJustifierList(listaStringJustifier);
		for(int icount=0; icount<=listaStringJustifier.size()-1;icount++) {
			IStringJustifierZZZ objJustifier = this.getStringJustifier(icount);		
			this.getStringJustifierListUsed().add(objJustifier);
			sReturn = LogStringFormaterUtilZZZ.justifyInfoPart(objJustifier, sReturn);
		}		
		return sReturn;
	}

	
}
