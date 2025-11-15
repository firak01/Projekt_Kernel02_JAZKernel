package basic.zBasic.util.log;

import java.util.LinkedHashMap;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.datatype.xml.XmlUtilTagByTypeZZZ;
import basic.zBasic.xml.tagtype.ITagByTypeZZZ;
import basic.zBasic.xml.tagtype.ITagTypeZZZ;
import basic.zBasic.xml.tagtype.TagByTypeFactoryZZZ;

public class LogStringFormatManagerZZZ extends LogStringFormatManagerXmlZZZ{
	private static final long serialVersionUID = 5164996113432507434L;

	// --- Singleton Instanz ---
	//muss als Singleton static sein. //Muss in der Konkreten Manager Klasse definiert sein, da ja unterschiedlich
	protected static ILogStringFormatManagerZZZ objLogStringManagerINSTANCE;

	
	//als private deklariert, damit man es nicht so instanzieren kann, sonder die Methode .getInstance() verwenden muss
	private LogStringFormatManagerZZZ() throws ExceptionZZZ{
		super();
	}
	
	public static ILogStringFormatManagerZZZ getInstance() throws ExceptionZZZ{
		if(objLogStringManagerINSTANCE==null){
			
			//siehe: https://www.digitalocean.com/community/tutorials/java-singleton-design-pattern-best-practices-examples
			//Threadsafe sicherstellen, dass nur 1 Instanz geholt wird. Hier doppelter Check mit synchronized, was performanter sein soll als die ganze Methode synchronized zu machen.
			synchronized(LogStringFormatManagerZZZ.class) {
				if(objLogStringManagerINSTANCE == null) {
					objLogStringManagerINSTANCE = getNewInstance();
				}
			}
			
		}
		return (ILogStringFormatManagerZZZ) objLogStringManagerINSTANCE;
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
	
	
	//################################################
	//### Methoden
	
	//++++++++++++++++++++++
	//+++ Wie von der Eltern-/Abstrakten Klasse
	//+++ Hier: Entferne aus dem Rückgabestring die XML Tags, die in ReflectCodeZZZ.getPositionCurrent() erzeugt worden sind.
	@Override
	public String compute(IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		String sReturn = super.compute(ienumFormatLogString);
		sReturn = ReflectCodeZZZ.removePositionCurrentTagPartsFrom(sReturn);		
		return sReturn;
	}

	@Override
	public String compute(Object obj, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		String sReturn = super.compute(obj, ienumFormatLogString);
		sReturn = ReflectCodeZZZ.removePositionCurrentTagPartsFrom(sReturn);
		return sReturn;
	}

	@Override
	public String compute(String sMessage, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		String sReturn = super.compute(sMessage, ienumFormatLogString);
		sReturn = ReflectCodeZZZ.removePositionCurrentTagPartsFrom(sReturn);
		return sReturn;
	}

	@Override
	public String compute(String sMessage, IEnumSetMappedLogStringFormatZZZ[] ienumaFormatLogString) throws ExceptionZZZ {
		String sReturn = super.compute(sMessage, ienumaFormatLogString);
		sReturn = ReflectCodeZZZ.removePositionCurrentTagPartsFrom(sReturn);
		return sReturn;
	}

	@Override
	public String compute(String[] saMessage, IEnumSetMappedLogStringFormatZZZ[] ienumaFormatLogString)	throws ExceptionZZZ {
		String sReturn = super.compute(saMessage, ienumaFormatLogString);
		sReturn = ReflectCodeZZZ.removePositionCurrentTagPartsFrom(sReturn);
		return sReturn;
	}

	@Override
	public String compute(Object obj, String sMessage, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		String sReturn = super.compute(obj, sMessage, ienumFormatLogString);
		sReturn = ReflectCodeZZZ.removePositionCurrentTagPartsFrom(sReturn);
		return sReturn;
	}

	@Override
	public String compute(Object obj, String sMessage, IEnumSetMappedLogStringFormatZZZ[] ienumaFormatLogString) throws ExceptionZZZ {
		String sReturn = super.compute(obj, sMessage, ienumaFormatLogString);
		sReturn = ReflectCodeZZZ.removePositionCurrentTagPartsFrom(sReturn);
		return sReturn;
	}

	@Override
	public String compute(Object obj, String[] saLog, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		String sReturn = super.compute(obj, saLog, ienumFormatLogString);
		sReturn = ReflectCodeZZZ.removePositionCurrentTagPartsFrom(sReturn);
		return sReturn;
	}

	@Override
	public String compute(Object obj, String[] saLog, IEnumSetMappedLogStringFormatZZZ[] ienumaFormatLogString) throws ExceptionZZZ {
		String sReturn = super.compute(obj, saLog, ienumaFormatLogString);
		sReturn = ReflectCodeZZZ.removePositionCurrentTagPartsFrom(sReturn);
		return sReturn;
	}

	@Override
	public String compute(Object obj, String sMessage01, String sMessage02, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		String sReturn = super.compute(obj, sMessage01, sMessage02, ienumFormatLogString);
		sReturn = ReflectCodeZZZ.removePositionCurrentTagPartsFrom(sReturn);
		return sReturn;
	}

	@Override
	public String compute(Object obj, String sMessage01, String sMessage02, IEnumSetMappedLogStringFormatZZZ[] ienumaFormatLogString) throws ExceptionZZZ {
		String sReturn = super.compute(obj, sMessage01, sMessage02, ienumaFormatLogString);
		sReturn = ReflectCodeZZZ.removePositionCurrentTagPartsFrom(sReturn);
		return sReturn;
	}

	@Override
	public String compute(Class classObj, String sMessage, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		String sReturn = super.compute(classObj, sMessage, ienumFormatLogString);
		sReturn = ReflectCodeZZZ.removePositionCurrentTagPartsFrom(sReturn);
		return sReturn;
	}

	@Override
	public String compute(Class classObj, String sMessage, IEnumSetMappedLogStringFormatZZZ[] ienumaFormatLogString) throws ExceptionZZZ {
		String sReturn = super.compute(classObj, sMessage, ienumaFormatLogString);
		sReturn = ReflectCodeZZZ.removePositionCurrentTagPartsFrom(sReturn);
		return sReturn;
	}

	@Override
	public String compute(Class classObj, String[] saLog, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		String sReturn = super.compute(classObj, saLog, ienumFormatLogString);
		sReturn = ReflectCodeZZZ.removePositionCurrentTagPartsFrom(sReturn);
		return sReturn;
	}

	@Override
	public String compute(Class classObj, String[] saLog, IEnumSetMappedLogStringFormatZZZ[] ienumaFormatLogString) throws ExceptionZZZ {
		String sReturn = super.compute(classObj, saLog, ienumaFormatLogString);
		sReturn = ReflectCodeZZZ.removePositionCurrentTagPartsFrom(sReturn);
		return sReturn;
	}

	@Override
	public String compute(Class classObj, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		String sReturn = super.compute(classObj, ienumFormatLogString);
		sReturn = ReflectCodeZZZ.removePositionCurrentTagPartsFrom(sReturn);
		return sReturn;
	}

	@Override
	public String compute(LinkedHashMap<IEnumSetMappedLogStringFormatZZZ, String> hm) throws ExceptionZZZ {
		String sReturn = super.compute(hm);
		sReturn = ReflectCodeZZZ.removePositionCurrentTagPartsFrom(sReturn);
		return sReturn;
	}

	@Override
	public String compute(Object obj, LinkedHashMap<IEnumSetMappedLogStringFormatZZZ, String> hm) throws ExceptionZZZ {
		String sReturn = super.compute(obj, hm);
		sReturn = ReflectCodeZZZ.removePositionCurrentTagPartsFrom(sReturn);
		return sReturn;
	}

	@Override
	public String compute(Class classObj, LinkedHashMap<IEnumSetMappedLogStringFormatZZZ, String> hm) throws ExceptionZZZ {
		String sReturn = super.compute(classObj, hm);
		sReturn = ReflectCodeZZZ.removePositionCurrentTagPartsFrom(sReturn);
		return sReturn;
	}

	@Override
	public String compute(Object obj, String sMessage) throws ExceptionZZZ {
		String sReturn = super.compute(obj, sMessage);
		sReturn = ReflectCodeZZZ.removePositionCurrentTagPartsFrom(sReturn);
		return sReturn;
	}

	@Override
	public String compute(Object obj, String[] saMessage) throws ExceptionZZZ {
		String sReturn = super.compute(obj, saMessage);
		sReturn = ReflectCodeZZZ.removePositionCurrentTagPartsFrom(sReturn);
		return sReturn;
	}

	@Override
	public String compute(Object obj, String sMessage01, String sMessage02) throws ExceptionZZZ {
		String sReturn = super.compute(obj, sMessage01, sMessage02);
		sReturn = ReflectCodeZZZ.removePositionCurrentTagPartsFrom(sReturn);
		return sReturn;
	}

	@Override
	public String compute(Class classObj, String sMessage) throws ExceptionZZZ {
		String sReturn = super.compute(classObj, sMessage);
		sReturn = ReflectCodeZZZ.removePositionCurrentTagPartsFrom(sReturn);
		return sReturn;
	}

	@Override
	public String compute(Class classObj, String sMessage01, String sMessage02) throws ExceptionZZZ {
		String sReturn = super.compute(classObj, sMessage01, sMessage02);
		sReturn = ReflectCodeZZZ.removePositionCurrentTagPartsFrom(sReturn);
		return sReturn;
	}

	@Override
	public String compute(Class classObj, String[] saMessage) throws ExceptionZZZ {
		String sReturn = super.compute(classObj, saMessage);
		sReturn = ReflectCodeZZZ.removePositionCurrentTagPartsFrom(sReturn);
		return sReturn;
	}
	
	//######################
	@Override
	public String compute(ILogStringFormaterZZZ objFormater, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		String sReturn = super.compute(objFormater, ienumFormatLogString);
		sReturn = ReflectCodeZZZ.removePositionCurrentTagPartsFrom(sReturn);
		return sReturn;
	}

	@Override
	public String compute(ILogStringFormaterZZZ objFormater, Object obj, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		String sReturn = super.compute(objFormater, obj, ienumFormatLogString);
		sReturn = ReflectCodeZZZ.removePositionCurrentTagPartsFrom(sReturn);
		return sReturn;
	}

	@Override
	public String compute(ILogStringFormaterZZZ objFormater, String sMessage, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		String sReturn = super.compute(objFormater, sMessage, ienumFormatLogString);
		sReturn = ReflectCodeZZZ.removePositionCurrentTagPartsFrom(sReturn);
		return sReturn;
	}

	@Override
	public String compute(ILogStringFormaterZZZ objFormater, String sMessage, IEnumSetMappedLogStringFormatZZZ[] ienumaFormatLogString) throws ExceptionZZZ {
		String sReturn = super.compute(objFormater, sMessage, ienumaFormatLogString);
		sReturn = ReflectCodeZZZ.removePositionCurrentTagPartsFrom(sReturn);
		return sReturn;
	}

	@Override
	public String compute(ILogStringFormaterZZZ objFormater, String[] saMessage, IEnumSetMappedLogStringFormatZZZ[] ienumaFormatLogString) throws ExceptionZZZ {
		String sReturn = super.compute(objFormater, saMessage, ienumaFormatLogString);
		sReturn = ReflectCodeZZZ.removePositionCurrentTagPartsFrom(sReturn);
		return sReturn;
	}

	@Override
	public String compute(ILogStringFormaterZZZ objFormater, Object obj, String sMessage, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		String sReturn = super.compute(objFormater, obj, sMessage, ienumFormatLogString);
		sReturn = ReflectCodeZZZ.removePositionCurrentTagPartsFrom(sReturn);
		return sReturn;
	}

	@Override
	public String compute(ILogStringFormaterZZZ objFormater, Object obj, String sMessage, IEnumSetMappedLogStringFormatZZZ[] ienumaFormatLogString) throws ExceptionZZZ {
		String sReturn = super.compute(objFormater, obj, sMessage, ienumaFormatLogString);
		sReturn = ReflectCodeZZZ.removePositionCurrentTagPartsFrom(sReturn);
		return sReturn;
	}

	@Override
	public String compute(ILogStringFormaterZZZ objFormater, Object obj, String[] saLog, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		String sReturn = super.compute(objFormater, obj, saLog, ienumFormatLogString);
		sReturn = ReflectCodeZZZ.removePositionCurrentTagPartsFrom(sReturn);
		return sReturn;
	}

	@Override
	public String compute(ILogStringFormaterZZZ objFormater, Object obj, String[] saLog, IEnumSetMappedLogStringFormatZZZ[] ienumaFormatLogString) throws ExceptionZZZ {
		String sReturn = super.compute(objFormater, obj, saLog, ienumaFormatLogString);
		sReturn = ReflectCodeZZZ.removePositionCurrentTagPartsFrom(sReturn);
		return sReturn;
	}

	@Override
	public String compute(ILogStringFormaterZZZ objFormater, Object obj, String sMessage01, String sMessage02, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		String sReturn = super.compute(objFormater, obj, sMessage01, sMessage02, ienumFormatLogString);
		sReturn = ReflectCodeZZZ.removePositionCurrentTagPartsFrom(sReturn);
		return sReturn;
	}

	@Override
	public String compute(ILogStringFormaterZZZ objFormater, Object obj, String sMessage01, String sMessage02, IEnumSetMappedLogStringFormatZZZ[] ienumaFormatLogString) throws ExceptionZZZ {
		String sReturn = super.compute(objFormater, obj, sMessage01, sMessage02, ienumaFormatLogString);
		sReturn = ReflectCodeZZZ.removePositionCurrentTagPartsFrom(sReturn);
		return sReturn;
	}

	@Override
	public String compute(ILogStringFormaterZZZ objFormater, Class classObj, String sMessage, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		String sReturn = super.compute(objFormater, classObj, sMessage, ienumFormatLogString);
		sReturn = ReflectCodeZZZ.removePositionCurrentTagPartsFrom(sReturn);
		return sReturn;
	}

	@Override
	public String compute(ILogStringFormaterZZZ objFormater, Class classObj, String sMessage, IEnumSetMappedLogStringFormatZZZ[] ienumaFormatLogString) throws ExceptionZZZ {
		String sReturn = super.compute(objFormater, classObj, sMessage, ienumaFormatLogString);
		sReturn = ReflectCodeZZZ.removePositionCurrentTagPartsFrom(sReturn);
		return sReturn;
	}

	@Override
	public String compute(ILogStringFormaterZZZ objFormater, Class classObj, String[] saLog, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		String sReturn = super.compute(objFormater, classObj, saLog, ienumFormatLogString);
		sReturn = ReflectCodeZZZ.removePositionCurrentTagPartsFrom(sReturn);
		return sReturn;
	}

	@Override
	public String compute(ILogStringFormaterZZZ objFormater, Class classObj, String[] saLog, IEnumSetMappedLogStringFormatZZZ[] ienumaFormatLogString) throws ExceptionZZZ {
		String sReturn = super.compute(objFormater, classObj, saLog, ienumaFormatLogString);
		sReturn = ReflectCodeZZZ.removePositionCurrentTagPartsFrom(sReturn);
		return sReturn;
	}

	@Override
	public String compute(ILogStringFormaterZZZ objFormater, Class classObj, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		String sReturn = super.compute(objFormater, classObj, ienumFormatLogString);
		sReturn = ReflectCodeZZZ.removePositionCurrentTagPartsFrom(sReturn);
		return sReturn;
	}

	@Override
	public String compute(ILogStringFormaterZZZ objFormater, LinkedHashMap<IEnumSetMappedLogStringFormatZZZ, String> hm) throws ExceptionZZZ {
		String sReturn = super.compute(objFormater, hm);
		sReturn = ReflectCodeZZZ.removePositionCurrentTagPartsFrom(sReturn);
		return sReturn;
	}

	@Override
	public String compute(ILogStringFormaterZZZ objFormater, Object obj, LinkedHashMap<IEnumSetMappedLogStringFormatZZZ, String> hm) throws ExceptionZZZ {
		String sReturn = super.compute(objFormater, obj, hm);
		sReturn = ReflectCodeZZZ.removePositionCurrentTagPartsFrom(sReturn);
		return sReturn;
	}

	@Override
	public String compute(ILogStringFormaterZZZ objFormater, Class classObj, LinkedHashMap<IEnumSetMappedLogStringFormatZZZ, String> hm) throws ExceptionZZZ {
		String sReturn = super.compute(objFormater, classObj, hm);
		sReturn = ReflectCodeZZZ.removePositionCurrentTagPartsFrom(sReturn);
		return sReturn;
	}

	@Override
	public String compute(ILogStringFormaterZZZ objFormater, Object obj, String sMessage) throws ExceptionZZZ {
		String sReturn = super.compute(objFormater, obj, sMessage);
		sReturn = ReflectCodeZZZ.removePositionCurrentTagPartsFrom(sReturn);
		return sReturn;
	}

	@Override
	public String compute(ILogStringFormaterZZZ objFormater, Object obj, String[] saMessage) throws ExceptionZZZ {
		String sReturn = super.compute(objFormater, obj, saMessage);
		sReturn = ReflectCodeZZZ.removePositionCurrentTagPartsFrom(sReturn);
		return sReturn;
	}

	@Override
	public String compute(ILogStringFormaterZZZ objFormater, Object obj, String sMessage01, String sMessage02) throws ExceptionZZZ {
		String sReturn = super.compute(objFormater, obj, sMessage01, sMessage02);
		sReturn = ReflectCodeZZZ.removePositionCurrentTagPartsFrom(sReturn);
		return sReturn;
	}

	@Override
	public String compute(ILogStringFormaterZZZ objFormater, Class classObj, String sMessage) throws ExceptionZZZ {
		String sReturn = super.compute(objFormater, classObj, sMessage);
		sReturn = ReflectCodeZZZ.removePositionCurrentTagPartsFrom(sReturn);
		return sReturn;
	}

	@Override
	public String compute(ILogStringFormaterZZZ objFormater, Class classObj, String sMessage01, String sMessage02) throws ExceptionZZZ {
		String sReturn = super.compute(objFormater, classObj, sMessage01, sMessage02);
		sReturn = ReflectCodeZZZ.removePositionCurrentTagPartsFrom(sReturn);
		return sReturn;
	}

	@Override
	public String compute(ILogStringFormaterZZZ objFormater, Class classObj, String[] saMessage) throws ExceptionZZZ {
		String sReturn = super.compute(objFormater, classObj, saMessage);
		sReturn = ReflectCodeZZZ.removePositionCurrentTagPartsFrom(sReturn);
		return sReturn;
	}
}
