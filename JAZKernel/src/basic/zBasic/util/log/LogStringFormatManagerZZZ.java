package basic.zBasic.util.log;

import java.util.LinkedHashMap;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
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
	protected static ILogStringFormatManagerZZZ objLogStringManagerINSTANCE;

	
	//als private deklariert, damit man es nicht so instanzieren kann, sonder die Methode .getInstance() verwenden muss
	private LogStringFormatManagerZZZ() throws ExceptionZZZ{
		super();
	}
	
	public static synchronized ILogStringFormatManagerJustifiedZZZ getInstance() throws ExceptionZZZ{
		if(objLogStringManagerINSTANCE==null){
			
			//siehe: https://www.digitalocean.com/community/tutorials/java-singleton-design-pattern-best-practices-examples
			//Threadsafe sicherstellen, dass nur 1 Instanz geholt wird. Hier doppelter Check mit synchronized, was performanter sein soll als die ganze Methode synchronized zu machen.
			synchronized(LogStringFormatManagerZZZ.class) {
				if(objLogStringManagerINSTANCE == null) {
					objLogStringManagerINSTANCE = getNewInstance();
				}
			}
			
		}
		return (ILogStringFormatManagerJustifiedZZZ) objLogStringManagerINSTANCE;
	}
	
	public static synchronized LogStringFormatManagerZZZ getNewInstance() throws ExceptionZZZ{
		//Damit wird garantiert einen neue, frische Instanz geholt.
		//Z.B. bei JUnit Tests ist das notwendig, denn in Folgetests wird mit .getInstance() doch tatsächlich mit dem Objekt des vorherigen Tests gearbeitet.
		objLogStringManagerINSTANCE = new LogStringFormatManagerZZZ();
		return (LogStringFormatManagerZZZ)objLogStringManagerINSTANCE;
	}
	
	public static synchronized void destroyInstance() throws ExceptionZZZ{
		objLogStringManagerINSTANCE = null;
	}
	
	
	//################################################
	//### Methoden
	
	//++++++++++++++++++++++
	//+++ Wie von der Eltern-/Abstrakten Klasse
	//+++ Hier: Entferne aus dem Rückgabestring die XML Tags, die in ReflectCodeZZZ.getPositionCurrent() erzeugt worden sind.
	@Override
	public String compute(IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		String sReturn = this.computeJustified(ienumFormatLogString);	
		return sReturn;
	}
	
	@Override
	public String computeJustified(IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		String sReturn = super.compute(ienumFormatLogString);
		sReturn = ReflectCodeZZZ.removePositionCurrentTagPartsFrom(sReturn);
		
		//### Versuch den Infoteil ueber alle Zeilen buendig zu halten
	    //WICHTIG1: DAS ERST NACHDEM ALLE STRING-TEILE, ALLER FORMATSTYPEN ABGEARBEITET WURDEN UND ZUSAMMENGESETZT WORDEN SIND.
		//WICHTIG2: DAHER AUCH NACH DEM ENTFERNEN DER XML-TAGS NEU AUSRECHNEN
		
		IStringJustifierZZZ objStringJustifier = this.getStringJustifier();
		sReturn = LogStringFormaterUtilZZZ.justifyInfoPart(objStringJustifier, sReturn);	
		return sReturn;
	}

	@Override
	public String compute(Object obj, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		String sReturn = this.computeJustified(obj, ienumFormatLogString);
		return sReturn;
	}
	
	@Override
	public String computeJustified(Object obj, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		String sReturn = super.compute(obj, ienumFormatLogString);
		sReturn = ReflectCodeZZZ.removePositionCurrentTagPartsFrom(sReturn);
		
		//### Versuch den Infoteil ueber alle Zeilen buendig zu halten
	    //WICHTIG1: DAS ERST NACHDEM ALLE STRING-TEILE, ALLER FORMATSTYPEN ABGEARBEITET WURDEN UND ZUSAMMENGESETZT WORDEN SIND.
		//WICHTIG2: DAHER AUCH NACH DEM ENTFERNEN DER XML-TAGS NEU AUSRECHNEN
		
		IStringJustifierZZZ objStringJustifier = this.getStringJustifier();
		sReturn = LogStringFormaterUtilZZZ.justifyInfoPart(objStringJustifier, sReturn);	
		return sReturn;
	}
	
	@Override
	public String compute(String... sLogs) throws ExceptionZZZ {
		String sReturn = this.computeJustified(sLogs);
		return sReturn;
	}
	
	@Override
	public String computeJustified(String... sLogs) throws ExceptionZZZ {
		String sReturn = super.compute(sLogs);
		sReturn = ReflectCodeZZZ.removePositionCurrentTagPartsFrom(sReturn);
		
		//### Versuch den Infoteil ueber alle Zeilen buendig zu halten
	    //WICHTIG1: DAS ERST NACHDEM ALLE STRING-TEILE, ALLER FORMATSTYPEN ABGEARBEITET WURDEN UND ZUSAMMENGESETZT WORDEN SIND.
		//WICHTIG2: DAHER AUCH NACH DEM ENTFERNEN DER XML-TAGS NEU AUSRECHNEN
		
		IStringJustifierZZZ objStringJustifier = this.getStringJustifier();
		sReturn = LogStringFormaterUtilZZZ.justifyInfoPart(objStringJustifier, sReturn);	
		return sReturn;
	}
	
	@Override
	public String compute(IEnumSetMappedLogStringFormatZZZ[] ienumaFormatLogString, String... sLogs)	throws ExceptionZZZ {
		String sReturn = super.compute(ienumaFormatLogString, sLogs);
		sReturn = ReflectCodeZZZ.removePositionCurrentTagPartsFrom(sReturn);
		
		//### Versuch den Infoteil ueber alle Zeilen buendig zu halten
	    //WICHTIG1: DAS ERST NACHDEM ALLE STRING-TEILE, ALLER FORMATSTYPEN ABGEARBEITET WURDEN UND ZUSAMMENGESETZT WORDEN SIND.
		//WICHTIG2: DAHER AUCH NACH DEM ENTFERNEN DER XML-TAGS NEU AUSRECHNEN
		
		IStringJustifierZZZ objStringJustifier = this.getStringJustifier();
		sReturn = LogStringFormaterUtilZZZ.justifyInfoPart(objStringJustifier, sReturn);	
		return sReturn;
	}

	@Override
	public String compute(Object obj, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString, String... sLogs) throws ExceptionZZZ {
		String sReturn = this.computeJustified(obj, ienumFormatLogString, sLogs);	
		return sReturn;
	}
	
	@Override
	public String computeJustified(Object obj, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString, String... sLogs) throws ExceptionZZZ {
		String sReturn = super.compute(obj, ienumFormatLogString, sLogs);
		sReturn = ReflectCodeZZZ.removePositionCurrentTagPartsFrom(sReturn);
		
		//### Versuch den Infoteil ueber alle Zeilen buendig zu halten
	    //WICHTIG1: DAS ERST NACHDEM ALLE STRING-TEILE, ALLER FORMATSTYPEN ABGEARBEITET WURDEN UND ZUSAMMENGESETZT WORDEN SIND.
		//WICHTIG2: DAHER AUCH NACH DEM ENTFERNEN DER XML-TAGS NEU AUSRECHNEN
		
		IStringJustifierZZZ objStringJustifier = this.getStringJustifier();
		sReturn = LogStringFormaterUtilZZZ.justifyInfoPart(objStringJustifier, sReturn);	
		return sReturn;
	}

	@Override
	public String compute(Object obj, IEnumSetMappedLogStringFormatZZZ[] ienumaFormatLogString, String... sLogs) throws ExceptionZZZ {
		String sReturn = this.computeJustified(obj, ienumaFormatLogString, sLogs);	
		return sReturn;
	}
	
	@Override
	public String computeJustified(Object obj, IEnumSetMappedLogStringFormatZZZ[] ienumaFormatLogString, String... sLogs) throws ExceptionZZZ {
		String sReturn = super.compute(obj, ienumaFormatLogString, sLogs);
		sReturn = ReflectCodeZZZ.removePositionCurrentTagPartsFrom(sReturn);
		
		//### Versuch den Infoteil ueber alle Zeilen buendig zu halten
	    //WICHTIG1: DAS ERST NACHDEM ALLE STRING-TEILE, ALLER FORMATSTYPEN ABGEARBEITET WURDEN UND ZUSAMMENGESETZT WORDEN SIND.
		//WICHTIG2: DAHER AUCH NACH DEM ENTFERNEN DER XML-TAGS NEU AUSRECHNEN
		
		IStringJustifierZZZ objStringJustifier = this.getStringJustifier();
		sReturn = LogStringFormaterUtilZZZ.justifyInfoPart(objStringJustifier, sReturn);	
		return sReturn;
	}

	@Override
	public String compute(Class classObj, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString, String... sLogs) throws ExceptionZZZ {
		String sReturn = this.computeJustified(classObj, ienumFormatLogString, sLogs);
		return sReturn;
		
	}
	
	@Override
	public String computeJustified(Class classObj, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString, String... sLogs) throws ExceptionZZZ {		
		String sReturn = super.compute(classObj, ienumFormatLogString, sLogs);
		sReturn = ReflectCodeZZZ.removePositionCurrentTagPartsFrom(sReturn);
		
		//### Versuch den Infoteil ueber alle Zeilen buendig zu halten
	    //WICHTIG1: DAS ERST NACHDEM ALLE STRING-TEILE, ALLER FORMATSTYPEN ABGEARBEITET WURDEN UND ZUSAMMENGESETZT WORDEN SIND.
		//WICHTIG2: DAHER AUCH NACH DEM ENTFERNEN DER XML-TAGS NEU AUSRECHNEN
		
		IStringJustifierZZZ objStringJustifier = this.getStringJustifier();
		sReturn = LogStringFormaterUtilZZZ.justifyInfoPart(objStringJustifier, sReturn);	
		return sReturn;
	}

	@Override
	public String compute(Class classObj, IEnumSetMappedLogStringFormatZZZ[] ienumaFormatLogString, String... sLogs) throws ExceptionZZZ {
		String sReturn = this.computeJustified(classObj, ienumaFormatLogString, sLogs);	
		return sReturn;
	}
	
	@Override
	public String computeJustified(Class classObj, IEnumSetMappedLogStringFormatZZZ[] ienumaFormatLogString, String... sLogs) throws ExceptionZZZ {
		String sReturn = super.compute(classObj, ienumaFormatLogString, sLogs);
		sReturn = ReflectCodeZZZ.removePositionCurrentTagPartsFrom(sReturn);
		
		//### Versuch den Infoteil ueber alle Zeilen buendig zu halten
	    //WICHTIG1: DAS ERST NACHDEM ALLE STRING-TEILE, ALLER FORMATSTYPEN ABGEARBEITET WURDEN UND ZUSAMMENGESETZT WORDEN SIND.
		//WICHTIG2: DAHER AUCH NACH DEM ENTFERNEN DER XML-TAGS NEU AUSRECHNEN
		
		IStringJustifierZZZ objStringJustifier = this.getStringJustifier();
		sReturn = LogStringFormaterUtilZZZ.justifyInfoPart(objStringJustifier, sReturn);	
		return sReturn;
	}

	@Override
	public String computeJustified(Class classObj, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		String sReturn = super.compute(classObj, ienumFormatLogString);
		sReturn = ReflectCodeZZZ.removePositionCurrentTagPartsFrom(sReturn);
		
		//### Versuch den Infoteil ueber alle Zeilen buendig zu halten
	    //WICHTIG1: DAS ERST NACHDEM ALLE STRING-TEILE, ALLER FORMATSTYPEN ABGEARBEITET WURDEN UND ZUSAMMENGESETZT WORDEN SIND.
		//WICHTIG2: DAHER AUCH NACH DEM ENTFERNEN DER XML-TAGS NEU AUSRECHNEN
		
		IStringJustifierZZZ objStringJustifier = this.getStringJustifier();
		sReturn = LogStringFormaterUtilZZZ.justifyInfoPart(objStringJustifier, sReturn);	
		return sReturn;
	}

	@Override
	public String compute(LinkedHashMap<IEnumSetMappedLogStringFormatZZZ, String> hm) throws ExceptionZZZ {
		String sReturn = this.computeJustified(hm);
		return sReturn;
	}

	@Override
	public String compute(Object obj, LinkedHashMap<IEnumSetMappedLogStringFormatZZZ, String> hm) throws ExceptionZZZ {
		String sReturn = super.compute(obj, hm);
		sReturn = ReflectCodeZZZ.removePositionCurrentTagPartsFrom(sReturn);
		
		//### Versuch den Infoteil ueber alle Zeilen buendig zu halten
	    //WICHTIG1: DAS ERST NACHDEM ALLE STRING-TEILE, ALLER FORMATSTYPEN ABGEARBEITET WURDEN UND ZUSAMMENGESETZT WORDEN SIND.
		//WICHTIG2: DAHER AUCH NACH DEM ENTFERNEN DER XML-TAGS NEU AUSRECHNEN
		
		IStringJustifierZZZ objStringJustifier = this.getStringJustifier();
		sReturn = LogStringFormaterUtilZZZ.justifyInfoPart(objStringJustifier, sReturn);	
		return sReturn;
	}

	@Override
	public String compute(Class classObj, LinkedHashMap<IEnumSetMappedLogStringFormatZZZ, String> hm) throws ExceptionZZZ {
		String sReturn = super.compute(classObj, hm);	
		sReturn = ReflectCodeZZZ.removePositionCurrentTagPartsFrom(sReturn);
		
		//### Versuch den Infoteil ueber alle Zeilen buendig zu halten
	    //WICHTIG1: DAS ERST NACHDEM ALLE STRING-TEILE, ALLER FORMATSTYPEN ABGEARBEITET WURDEN UND ZUSAMMENGESETZT WORDEN SIND.
		//WICHTIG2: DAHER AUCH NACH DEM ENTFERNEN DER XML-TAGS NEU AUSRECHNEN
		
		IStringJustifierZZZ objStringJustifier = this.getStringJustifier();
		sReturn = LogStringFormaterUtilZZZ.justifyInfoPart(objStringJustifier, sReturn);	
		return sReturn;
	}

	@Override
	public String compute(Object obj, String... sLogs) throws ExceptionZZZ {
		String sReturn = this.computeJustified(obj, sLogs);
		return sReturn;
	}
	
	@Override
	public String computeJustified(Object obj, String... sLogs) throws ExceptionZZZ {
		String sReturn = super.compute(obj, sLogs);
		sReturn = ReflectCodeZZZ.removePositionCurrentTagPartsFrom(sReturn);
		
		//### Versuch den Infoteil ueber alle Zeilen buendig zu halten
	    //WICHTIG1: DAS ERST NACHDEM ALLE STRING-TEILE, ALLER FORMATSTYPEN ABGEARBEITET WURDEN UND ZUSAMMENGESETZT WORDEN SIND.
		//WICHTIG2: DAHER AUCH NACH DEM ENTFERNEN DER XML-TAGS NEU AUSRECHNEN
		
		IStringJustifierZZZ objStringJustifier = this.getStringJustifier();
		sReturn = LogStringFormaterUtilZZZ.justifyInfoPart(objStringJustifier, sReturn);	
		return sReturn;
	}
	
	@Override
	public String compute(Class classObj, String... sLogs) throws ExceptionZZZ {
		String sReturn = this.computeJustified(classObj, sLogs);
		return sReturn;
	}
	
	@Override
	public String computeJustified(Class classObj, String... sLogs) throws ExceptionZZZ {		
		String sReturn = super.compute(classObj, sLogs);
		sReturn = ReflectCodeZZZ.removePositionCurrentTagPartsFrom(sReturn);	
		
		//### Versuch den Infoteil ueber alle Zeilen buendig zu halten
	    //WICHTIG1: DAS ERST NACHDEM ALLE STRING-TEILE, ALLER FORMATSTYPEN ABGEARBEITET WURDEN UND ZUSAMMENGESETZT WORDEN SIND.
		//WICHTIG2: DAHER AUCH NACH DEM ENTFERNEN DER XML-TAGS NEU AUSRECHNEN
		
		IStringJustifierZZZ objStringJustifier = this.getStringJustifier();
		sReturn = LogStringFormaterUtilZZZ.justifyInfoPart(objStringJustifier, sReturn);	
		return sReturn;
	}
	
	//######################
	
	
	
	@Override
	public String compute(ILogStringFormaterZZZ objFormater, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		String sReturn = this.computeJustified(objFormater, ienumFormatLogString);
		return sReturn;
	}
	
	@Override
	public String computeJustified(ILogStringFormaterZZZ objFormater, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {		
		String sReturn = super.compute(objFormater, ienumFormatLogString);
		sReturn = ReflectCodeZZZ.removePositionCurrentTagPartsFrom(sReturn);
		
		//### Versuch den Infoteil ueber alle Zeilen buendig zu halten
	    //WICHTIG1: DAS ERST NACHDEM ALLE STRING-TEILE, ALLER FORMATSTYPEN ABGEARBEITET WURDEN UND ZUSAMMENGESETZT WORDEN SIND.
		//WICHTIG2: DAHER AUCH NACH DEM ENTFERNEN DER XML-TAGS NEU AUSRECHNEN
		
		IStringJustifierZZZ objStringJustifier = this.getStringJustifier();
		sReturn = LogStringFormaterUtilZZZ.justifyInfoPart(objStringJustifier, sReturn);	
		return sReturn;
	}

	@Override
	public String compute(ILogStringFormaterZZZ objFormater, Object obj, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		String sReturn = this.computeJustified(objFormater, obj, ienumFormatLogString);
		return sReturn;
	}
	
	@Override
	public String computeJustified(ILogStringFormaterZZZ objFormater, Object obj, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		String sReturn = super.compute(objFormater, obj, ienumFormatLogString);
		sReturn = ReflectCodeZZZ.removePositionCurrentTagPartsFrom(sReturn);
		
		//### Versuch den Infoteil ueber alle Zeilen buendig zu halten
	    //WICHTIG1: DAS ERST NACHDEM ALLE STRING-TEILE, ALLER FORMATSTYPEN ABGEARBEITET WURDEN UND ZUSAMMENGESETZT WORDEN SIND.
		//WICHTIG2: DAHER AUCH NACH DEM ENTFERNEN DER XML-TAGS NEU AUSRECHNEN
		
		IStringJustifierZZZ objStringJustifier = this.getStringJustifier();
		sReturn = LogStringFormaterUtilZZZ.justifyInfoPart(objStringJustifier, sReturn);	
		return sReturn;
	}

	@Override
	public String compute(ILogStringFormaterZZZ objFormater, IEnumSetMappedLogStringFormatZZZ[] ienumaFormatLogString, String... sLogs) throws ExceptionZZZ {
		String sReturn = this.computeJustified(objFormater, ienumaFormatLogString, sLogs);
		return sReturn;
	}
	
	@Override
	public String computeJustified(ILogStringFormaterZZZ objFormater, IEnumSetMappedLogStringFormatZZZ[] ienumaFormatLogString, String... sLogs) throws ExceptionZZZ {		
		String sReturn = super.compute(objFormater, ienumaFormatLogString, sLogs);
		sReturn = ReflectCodeZZZ.removePositionCurrentTagPartsFrom(sReturn);
		
		//### Versuch den Infoteil ueber alle Zeilen buendig zu halten
	    //WICHTIG1: DAS ERST NACHDEM ALLE STRING-TEILE, ALLER FORMATSTYPEN ABGEARBEITET WURDEN UND ZUSAMMENGESETZT WORDEN SIND.
		//WICHTIG2: DAHER AUCH NACH DEM ENTFERNEN DER XML-TAGS NEU AUSRECHNEN
		
		IStringJustifierZZZ objStringJustifier = this.getStringJustifier();
		sReturn = LogStringFormaterUtilZZZ.justifyInfoPart(objStringJustifier, sReturn);	
		return sReturn;
	}
	
//	@Override
//	public String computeJustified(ILogStringFormaterZZZ objFormater, LinkedHashMap<IEnumSetMappedLogStringFormatZZZ, String> hmLogString) throws ExceptionZZZ {
//		String sReturn = 
//	    return sReturn;
//	}

	@Override
	public String compute(ILogStringFormaterZZZ objFormater, Object obj, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString, String... sLogs) throws ExceptionZZZ {
		String sReturn = this.computeJustified(objFormater, obj, ienumFormatLogString, sLogs);
		return sReturn;
	}
	
	@Override
	public String computeJustified(ILogStringFormaterZZZ objFormater, Object obj, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString, String... sLogs) throws ExceptionZZZ {		
		String sReturn = super.compute(objFormater, obj, ienumFormatLogString, sLogs);
		sReturn = ReflectCodeZZZ.removePositionCurrentTagPartsFrom(sReturn);
		
		//### Versuch den Infoteil ueber alle Zeilen buendig zu halten
	    //WICHTIG1: DAS ERST NACHDEM ALLE STRING-TEILE, ALLER FORMATSTYPEN ABGEARBEITET WURDEN UND ZUSAMMENGESETZT WORDEN SIND.
		//WICHTIG2: DAHER AUCH NACH DEM ENTFERNEN DER XML-TAGS NEU AUSRECHNEN
		
		IStringJustifierZZZ objStringJustifier = this.getStringJustifier();
		sReturn = LogStringFormaterUtilZZZ.justifyInfoPart(objStringJustifier, sReturn);	
		return sReturn;
	}

	@Override
	public String compute(ILogStringFormaterZZZ objFormater, Object obj, IEnumSetMappedLogStringFormatZZZ[] ienumaFormatLogString, String... sLogs) throws ExceptionZZZ {
		String sReturn = this.computeJustified(objFormater, obj, ienumaFormatLogString, sLogs);
		return sReturn;
	}
	
	@Override
	public String computeJustified(ILogStringFormaterZZZ objFormater, Object obj, IEnumSetMappedLogStringFormatZZZ[] ienumaFormatLogString, String... sLogs) throws ExceptionZZZ {		
		String sReturn = super.compute(objFormater, obj, ienumaFormatLogString, sLogs);
		sReturn = ReflectCodeZZZ.removePositionCurrentTagPartsFrom(sReturn);
		
		//### Versuch den Infoteil ueber alle Zeilen buendig zu halten
	    //WICHTIG1: DAS ERST NACHDEM ALLE STRING-TEILE, ALLER FORMATSTYPEN ABGEARBEITET WURDEN UND ZUSAMMENGESETZT WORDEN SIND.
		//WICHTIG2: DAHER AUCH NACH DEM ENTFERNEN DER XML-TAGS NEU AUSRECHNEN
		
		IStringJustifierZZZ objStringJustifier = this.getStringJustifier();
		sReturn = LogStringFormaterUtilZZZ.justifyInfoPart(objStringJustifier, sReturn);	
		return sReturn;
	}

	@Override
	public String compute(ILogStringFormaterZZZ objFormater, Class classObj, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString, String... sLogs) throws ExceptionZZZ {
		String sReturn = this.computeJustified(objFormater, classObj, ienumFormatLogString, sLogs);				
		return sReturn;
	}
	
	@Override
	public String computeJustified(ILogStringFormaterZZZ objFormater, Class classObj, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString, String... sLogs) throws ExceptionZZZ {		
		String sReturn = super.compute(objFormater, classObj, ienumFormatLogString, sLogs);
		sReturn = ReflectCodeZZZ.removePositionCurrentTagPartsFrom(sReturn);
		
		//### Versuch den Infoteil ueber alle Zeilen buendig zu halten
	    //WICHTIG1: DAS ERST NACHDEM ALLE STRING-TEILE, ALLER FORMATSTYPEN ABGEARBEITET WURDEN UND ZUSAMMENGESETZT WORDEN SIND.
		//WICHTIG2: DAHER AUCH NACH DEM ENTFERNEN DER XML-TAGS NEU AUSRECHNEN
		
		IStringJustifierZZZ objStringJustifier = this.getStringJustifier();
		sReturn = LogStringFormaterUtilZZZ.justifyInfoPart(objStringJustifier, sReturn);	
		return sReturn;
	}
	
	

	@Override
	public String compute(ILogStringFormaterZZZ objFormater, Class classObj, IEnumSetMappedLogStringFormatZZZ[] ienumaFormatLogString, String... sLogs) throws ExceptionZZZ {
		String sReturn = this.computeJustified(objFormater, classObj, ienumaFormatLogString, sLogs);		
		return sReturn;
	}
	
	@Override
	public String computeJustified(ILogStringFormaterZZZ objFormater, Class classObj, IEnumSetMappedLogStringFormatZZZ[] ienumaFormatLogString, String... sLogs) throws ExceptionZZZ {		
		String sReturn = super.compute(objFormater, classObj, ienumaFormatLogString, sLogs);
		sReturn = ReflectCodeZZZ.removePositionCurrentTagPartsFrom(sReturn);		
		
		//### Versuch den Infoteil ueber alle Zeilen buendig zu halten
	    //WICHTIG1: DAS ERST NACHDEM ALLE STRING-TEILE, ALLER FORMATSTYPEN ABGEARBEITET WURDEN UND ZUSAMMENGESETZT WORDEN SIND.
		//WICHTIG2: DAHER AUCH NACH DEM ENTFERNEN DER XML-TAGS NEU AUSRECHNEN
		
		IStringJustifierZZZ objStringJustifier = this.getStringJustifier();
		sReturn = LogStringFormaterUtilZZZ.justifyInfoPart(objStringJustifier, sReturn);	
		return sReturn;
	}

	@Override
	public String compute(ILogStringFormaterZZZ objFormater, Class classObj, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		String sReturn = this.computeJustified(objFormater, classObj, ienumFormatLogString);
		return sReturn;
	}
		
	@Override
	public String computeJustified(ILogStringFormaterZZZ objFormater, Class classObj, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {		
		String sReturn = super.compute(objFormater, classObj, ienumFormatLogString);
		sReturn = ReflectCodeZZZ.removePositionCurrentTagPartsFrom(sReturn);
		
		//### Versuch den Infoteil ueber alle Zeilen buendig zu halten
	    //WICHTIG1: DAS ERST NACHDEM ALLE STRING-TEILE, ALLER FORMATSTYPEN ABGEARBEITET WURDEN UND ZUSAMMENGESETZT WORDEN SIND.
		//WICHTIG2: DAHER AUCH NACH DEM ENTFERNEN DER XML-TAGS NEU AUSRECHNEN
		
		IStringJustifierZZZ objStringJustifier = this.getStringJustifier();
		sReturn = LogStringFormaterUtilZZZ.justifyInfoPart(objStringJustifier, sReturn);	
		return sReturn;
	}

	@Override
	public String compute(ILogStringFormaterZZZ objFormater, LinkedHashMap<IEnumSetMappedLogStringFormatZZZ, String> hm) throws ExceptionZZZ {
		String sReturn = this.computeJustified(objFormater, hm);
		return sReturn;
	}
	
	@Override
	public String computeJustified(ILogStringFormaterZZZ objFormater, LinkedHashMap<IEnumSetMappedLogStringFormatZZZ, String> hm) throws ExceptionZZZ {		
		String sReturn = super.compute(objFormater, hm);
		sReturn = ReflectCodeZZZ.removePositionCurrentTagPartsFrom(sReturn);
		
		//### Versuch den Infoteil ueber alle Zeilen buendig zu halten
	    //WICHTIG1: DAS ERST NACHDEM ALLE STRING-TEILE, ALLER FORMATSTYPEN ABGEARBEITET WURDEN UND ZUSAMMENGESETZT WORDEN SIND.
		//WICHTIG2: DAHER AUCH NACH DEM ENTFERNEN DER XML-TAGS NEU AUSRECHNEN
		
		IStringJustifierZZZ objStringJustifier = this.getStringJustifier();
		sReturn = LogStringFormaterUtilZZZ.justifyInfoPart(objStringJustifier, sReturn);	
		return sReturn;
	}

	@Override
	public String compute(ILogStringFormaterZZZ objFormater, Object obj, LinkedHashMap<IEnumSetMappedLogStringFormatZZZ, String> hm) throws ExceptionZZZ {
		String sReturn = this.computeJustified(objFormater, obj, hm);
		return sReturn;
	}

	@Override
	public String computeJustified(ILogStringFormaterZZZ objFormater, Object obj, LinkedHashMap<IEnumSetMappedLogStringFormatZZZ, String> hm) throws ExceptionZZZ {		
		String sReturn = super.compute(objFormater, obj, hm);
		sReturn = ReflectCodeZZZ.removePositionCurrentTagPartsFrom(sReturn);
		
		//### Versuch den Infoteil ueber alle Zeilen buendig zu halten
	    //WICHTIG1: DAS ERST NACHDEM ALLE STRING-TEILE, ALLER FORMATSTYPEN ABGEARBEITET WURDEN UND ZUSAMMENGESETZT WORDEN SIND.
		//WICHTIG2: DAHER AUCH NACH DEM ENTFERNEN DER XML-TAGS NEU AUSRECHNEN
		
		IStringJustifierZZZ objStringJustifier = this.getStringJustifier();
		sReturn = LogStringFormaterUtilZZZ.justifyInfoPart(objStringJustifier, sReturn);	
		return sReturn;
	}
	
	@Override
	public String compute(ILogStringFormaterZZZ objFormater, Class classObj, LinkedHashMap<IEnumSetMappedLogStringFormatZZZ, String> hm) throws ExceptionZZZ {
		String sReturn = this.computeJustified(objFormater, classObj, hm);
		return sReturn;
	}
	
	@Override
	public String computeJustified(ILogStringFormaterZZZ objFormater, Class classObj, LinkedHashMap<IEnumSetMappedLogStringFormatZZZ, String> hm) throws ExceptionZZZ {		
		String sReturn = super.compute(objFormater, classObj, hm);
		sReturn = ReflectCodeZZZ.removePositionCurrentTagPartsFrom(sReturn);
		
		//### Versuch den Infoteil ueber alle Zeilen buendig zu halten
	    //WICHTIG1: DAS ERST NACHDEM ALLE STRING-TEILE, ALLER FORMATSTYPEN ABGEARBEITET WURDEN UND ZUSAMMENGESETZT WORDEN SIND.
		//WICHTIG2: DAHER AUCH NACH DEM ENTFERNEN DER XML-TAGS NEU AUSRECHNEN
		
		IStringJustifierZZZ objStringJustifier = this.getStringJustifier();
		sReturn = LogStringFormaterUtilZZZ.justifyInfoPart(objStringJustifier, sReturn);	
		return sReturn;
	}

	@Override
	public String compute(ILogStringFormaterZZZ objFormater, Object obj, String... sLogs) throws ExceptionZZZ {
		String sReturn = this.computeJustified(objFormater, obj, sLogs);
		return sReturn;
	}
	
	@Override
	public String computeJustified(ILogStringFormaterZZZ objFormater, Object obj, String... sLogs) throws ExceptionZZZ {
		String sReturn = super.compute(objFormater, obj, sLogs);
		sReturn = ReflectCodeZZZ.removePositionCurrentTagPartsFrom(sReturn);
		
		//### Versuch den Infoteil ueber alle Zeilen buendig zu halten
	    //WICHTIG1: DAS ERST NACHDEM ALLE STRING-TEILE, ALLER FORMATSTYPEN ABGEARBEITET WURDEN UND ZUSAMMENGESETZT WORDEN SIND.
		//WICHTIG2: DAHER AUCH NACH DEM ENTFERNEN DER XML-TAGS NEU AUSRECHNEN
		
		IStringJustifierZZZ objStringJustifier = this.getStringJustifier();
		sReturn = LogStringFormaterUtilZZZ.justifyInfoPart(objStringJustifier, sReturn);	
		return sReturn;
	}
	
	
	@Override
	public String compute(ILogStringFormaterZZZ objFormater, Class classObj, String... sLogs) throws ExceptionZZZ {
		String sReturn = this.computeJustified(objFormater, classObj, sLogs);	
		return sReturn;
	}
	
	@Override
	public String computeJustified(ILogStringFormaterZZZ objFormater, Class classObj, String... sLogs) throws ExceptionZZZ {
		String sReturn = super.compute(objFormater, classObj, sLogs);
		sReturn = ReflectCodeZZZ.removePositionCurrentTagPartsFrom(sReturn);	
		
		//### Versuch den Infoteil ueber alle Zeilen buendig zu halten
	    //WICHTIG1: DAS ERST NACHDEM ALLE STRING-TEILE, ALLER FORMATSTYPEN ABGEARBEITET WURDEN UND ZUSAMMENGESETZT WORDEN SIND.
		//WICHTIG2: DAHER AUCH NACH DEM ENTFERNEN DER XML-TAGS NEU AUSRECHNEN
		
		IStringJustifierZZZ objStringJustifier = this.getStringJustifier();
		sReturn = LogStringFormaterUtilZZZ.justifyInfoPart(objStringJustifier, sReturn);	
		return sReturn;
	}

	

	

	
}
