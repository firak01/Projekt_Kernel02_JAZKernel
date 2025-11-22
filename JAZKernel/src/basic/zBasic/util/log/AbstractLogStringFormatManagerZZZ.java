package basic.zBasic.util.log;

import java.util.LinkedHashMap;

import basic.zBasic.AbstractObjectWithFlagZZZ;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.datatype.string.IStringJustifierZZZ;
import basic.zBasic.util.datatype.string.StringJustifierZZZ;
import basic.zKernel.flag.IFlagZEnabledZZZ;

public abstract class AbstractLogStringFormatManagerZZZ extends AbstractObjectWithFlagZZZ implements ILogStringFormatManagerZZZ{
	private static final long serialVersionUID = 432992680546312138L;
	
	// --- Singleton Instanz ---
	//muss als Singleton static sein. //Muss in der Konkreten Manager Klasse definiert sein, da ja unterschiedlich
	//protected static ILogStringFormatManagerZZZ objLogStringManagerINSTANCE; //muss als Singleton static sein
	
	// --- Globale Objekte ---
	//Zum Buendig machen
	//Idee: Bei mehreren Instanzen per Default auf das Singleton zugreifen
	//      Aber ggfs. ueberschreibend auf einen hinterlegten zugreifen.
	protected volatile IStringJustifierZZZ objStringJustifier = null;
		
	//als private deklariert, damit man es nicht so instanzieren kann, sonder die Methode .getInstance() verwenden muss
	protected AbstractLogStringFormatManagerZZZ() throws ExceptionZZZ{
		super();
	}
	
	//##########################################################	
	//### GETTER / SETTER
	@Override
	public IStringJustifierZZZ getStringJustifier() throws ExceptionZZZ {
		if(!this.hasStringJustifierPrivate()) {
			//Verwende als default das Singleton
			return StringJustifierZZZ.getInstance();
		}else {
			//Verwende als "manual override" den einmal hinterlegten StringJustifier.
			return this.objStringJustifier;
		}
	}

	@Override
	public void setStringJustifier(IStringJustifierZZZ objStringJustifier) throws ExceptionZZZ {
		this.objStringJustifier = objStringJustifier;
	}

	//##########################################################
	//### METHODEN ##########
	@Override
	public boolean hasStringJustifierPrivate() throws ExceptionZZZ{
		if(this.objStringJustifier==null) {
			return false;
		}else {
			return true;
		}		
	}
	
	@Override
	public boolean reset() throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			//this.setStringJustifier(null);
			
			bReturn = true;
		}//end main:
		return bReturn;
	}
	
	
	@Override
	public String compute(IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		//IStringJustifierZZZ objJustifier = this.getStringJustifier();
		//LogStringFormaterZZZ objFormater = new LogStringFormaterZZZ(objJustifier);
		LogStringFormaterZZZ objFormater = new LogStringFormaterZZZ();
		return objFormater.compute(ienumFormatLogString);
	}

	@Override
	public String compute(Object obj, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		//StringJustifierZZZ objJustifier = (StringJustifierZZZ) this.getStringJustifier();
		//LogStringFormaterZZZ objFormater = new LogStringFormaterZZZ(objJustifier);
		LogStringFormaterZZZ objFormater = new LogStringFormaterZZZ();
		return objFormater.compute(obj, ienumFormatLogString);
	}
	
	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
//	@Override
//	public String compute(String sLog) throws ExceptionZZZ {		
//		LogStringFormaterZZZ objFormater = new LogStringFormaterZZZ();
//		return objFormater.compute(null, (IEnumSetMappedLogStringFormatZZZ[]) null, sLog);
//	}
	
	@Override
	public String compute(String... sLogs) throws ExceptionZZZ {		
		LogStringFormaterZZZ objFormater = new LogStringFormaterZZZ();
		return objFormater.compute(null, (IEnumSetMappedLogStringFormatZZZ[]) null, sLogs);
	}
	
//	@Override
//	public String compute(String... sLogs) throws ExceptionZZZ {
//		LogStringFormaterZZZ objFormater = new LogStringFormaterZZZ();
//		return objFormater.compute(null,(IEnumSetMappedLogStringFormatZZZ[]) null, sLogs);
//	}

//	@Override
//	public String compute(String sLog, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
//		//IStringJustifierZZZ objJustifier = this.getStringJustifier();
//		//LogStringFormaterZZZ objFormater = new LogStringFormaterZZZ(objJustifier);
//		LogStringFormaterZZZ objFormater = new LogStringFormaterZZZ();
//		return objFormater.compute(sLog, ienumFormatLogString);
//	}

//	@Override
//	public String compute(String sLog, IEnumSetMappedLogStringFormatZZZ[] ienumaFormatLogString) throws ExceptionZZZ {
//		//IStringJustifierZZZ objJustifier = this.getStringJustifier();
//		//LogStringFormaterZZZ objFormater = new LogStringFormaterZZZ(objJustifier);
//		LogStringFormaterZZZ objFormater = new LogStringFormaterZZZ();
//		return objFormater.compute(sLog, ienumaFormatLogString);
//	}

	@Override
	public String compute(IEnumSetMappedLogStringFormatZZZ[] ienumaFormatLogString, String... sLogs)	throws ExceptionZZZ {
	//	IStringJustifierZZZ objJustifier = this.getStringJustifier();
		//LogStringFormaterZZZ objFormater = new LogStringFormaterZZZ(objJustifier);
		LogStringFormaterZZZ objFormater = new LogStringFormaterZZZ();
		return objFormater.compute(ienumaFormatLogString, sLogs);
	}

//	@Override
//	public String compute(Object obj, String sLog, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
//		//IStringJustifierZZZ objJustifier = this.getStringJustifier();
//		//LogStringFormaterZZZ objFormater = new LogStringFormaterZZZ(objJustifier);
//		LogStringFormaterZZZ objFormater = new LogStringFormaterZZZ();
//		return objFormater.compute(sLog, ienumFormatLogString);
//	}

//	@Override
//	public String compute(Object obj, String sLog, IEnumSetMappedLogStringFormatZZZ[] ienumaFormatLogString) throws ExceptionZZZ {
//		//IStringJustifierZZZ objJustifier = this.getStringJustifier();
//		//LogStringFormaterZZZ objFormater = new LogStringFormaterZZZ(objJustifier);
//		LogStringFormaterZZZ objFormater = new LogStringFormaterZZZ();
//		return objFormater.compute(obj, sLog, ienumaFormatLogString);
//	}

	@Override
	public String compute(Object obj, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString, String... sLogs) throws ExceptionZZZ {
		//IStringJustifierZZZ objJustifier = this.getStringJustifier();
		//LogStringFormaterZZZ objFormater = new LogStringFormaterZZZ(objJustifier);
		LogStringFormaterZZZ objFormater = new LogStringFormaterZZZ();
		return objFormater.compute(obj, ienumFormatLogString, sLogs);
	}

	@Override
	public String compute(Object obj, IEnumSetMappedLogStringFormatZZZ[] ienumaFormatLogString, String... sLogs) throws ExceptionZZZ {
		//IStringJustifierZZZ objJustifier = this.getStringJustifier();
		//LogStringFormaterZZZ objFormater = new LogStringFormaterZZZ(objJustifier);
		LogStringFormaterZZZ objFormater = new LogStringFormaterZZZ();
		return objFormater.compute(obj, ienumaFormatLogString, sLogs);
	}

//	@Override
//	public String compute(Object obj, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString, String... sLogs) throws ExceptionZZZ {
//		//IStringJustifierZZZ objJustifier = this.getStringJustifier();
//		//LogStringFormaterZZZ objFormater = new LogStringFormaterZZZ(objJustifier);
//		LogStringFormaterZZZ objFormater = new LogStringFormaterZZZ();
//		return objFormater.compute(obj, sLog01, sLog02, ienumFormatLogString);
//	}

//	@Override
//	public String compute(Object obj, IEnumSetMappedLogStringFormatZZZ[] ienumaFormatLogString, String... slogs) throws ExceptionZZZ {
//		//IStringJustifierZZZ objJustifier = this.getStringJustifier();
//		//LogStringFormaterZZZ objFormater = new LogStringFormaterZZZ(objJustifier);
//		LogStringFormaterZZZ objFormater = new LogStringFormaterZZZ();
//		return objFormater.compute(obj, sLog01, sLog02, ienumaFormatLogString);
//	}

//	@Override
//	public String compute(Class classObj, String sLog, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
//		//IStringJustifierZZZ objJustifier = this.getStringJustifier();
//		//LogStringFormaterZZZ objFormater = new LogStringFormaterZZZ(objJustifier);
//		LogStringFormaterZZZ objFormater = new LogStringFormaterZZZ();
//		return objFormater.compute(classObj, sLog, ienumFormatLogString);
//	}

//	@Override
//	public String compute(Class classObj, String sLog, IEnumSetMappedLogStringFormatZZZ[] ienumaFormatLogString) throws ExceptionZZZ {
//		//IStringJustifierZZZ objJustifier = this.getStringJustifier();
//		//LogStringFormaterZZZ objFormater = new LogStringFormaterZZZ(objJustifier);
//		LogStringFormaterZZZ objFormater = new LogStringFormaterZZZ();
//		return objFormater.compute(classObj, sLog, ienumaFormatLogString);
//	}

	@Override
	public String compute(Class classObj, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString, String... sLogs) throws ExceptionZZZ {
//		IStringJustifierZZZ objJustifier = this.getStringJustifier();
//		LogStringFormaterZZZ objFormater = new LogStringFormaterZZZ(objJustifier);
		LogStringFormaterZZZ objFormater = new LogStringFormaterZZZ();
		return objFormater.compute(classObj, ienumFormatLogString, sLogs);
	}

	@Override
	public String compute(Class classObj, IEnumSetMappedLogStringFormatZZZ[] ienumaFormatLogString, String... sLogs) throws ExceptionZZZ {
//		IStringJustifierZZZ objJustifier = this.getStringJustifier();
//		LogStringFormaterZZZ objFormater = new LogStringFormaterZZZ(objJustifier);
		LogStringFormaterZZZ objFormater = new LogStringFormaterZZZ();
		return objFormater.compute(classObj, ienumaFormatLogString, sLogs);
	}

	@Override
	public String compute(Class classObj, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
//		IStringJustifierZZZ objJustifier = this.getStringJustifier();
//		LogStringFormaterZZZ objFormater = new LogStringFormaterZZZ(objJustifier);
		LogStringFormaterZZZ objFormater = new LogStringFormaterZZZ();
		return objFormater.compute(classObj, ienumFormatLogString);
	}

	@Override
	public String compute(LinkedHashMap<IEnumSetMappedLogStringFormatZZZ, String> hm) throws ExceptionZZZ {
//		IStringJustifierZZZ objJustifier = this.getStringJustifier();
//		LogStringFormaterZZZ objFormater = new LogStringFormaterZZZ(objJustifier);
		LogStringFormaterZZZ objFormater = new LogStringFormaterZZZ();
		return objFormater.compute(hm);
	}

	@Override
	public String compute(Object obj, LinkedHashMap<IEnumSetMappedLogStringFormatZZZ, String> hm) throws ExceptionZZZ {
//		IStringJustifierZZZ objJustifier = this.getStringJustifier();
//		LogStringFormaterZZZ objFormater = new LogStringFormaterZZZ(objJustifier);
		LogStringFormaterZZZ objFormater = new LogStringFormaterZZZ();
		return objFormater.compute(obj, hm);
	}

	@Override
	public String compute(Class classObj, LinkedHashMap<IEnumSetMappedLogStringFormatZZZ, String> hm) throws ExceptionZZZ {
//		IStringJustifierZZZ objJustifier = this.getStringJustifier();
//		LogStringFormaterZZZ objFormater = new LogStringFormaterZZZ(objJustifier);
		LogStringFormaterZZZ objFormater = new LogStringFormaterZZZ();
		return objFormater.compute(classObj, hm);
	}

//	@Override
//	public String compute(Object obj, String sLog) throws ExceptionZZZ {
////		IStringJustifierZZZ objJustifier = this.getStringJustifier();
////		LogStringFormaterZZZ objFormater = new LogStringFormaterZZZ(objJustifier);
//		LogStringFormaterZZZ objFormater = new LogStringFormaterZZZ();
//		return objFormater.compute(obj, sLog);
//	}

	@Override
	public String compute(Object obj, String... sLogs) throws ExceptionZZZ {
//		IStringJustifierZZZ objJustifier = this.getStringJustifier();
//		LogStringFormaterZZZ objFormater = new LogStringFormaterZZZ(objJustifier);
		LogStringFormaterZZZ objFormater = new LogStringFormaterZZZ();
		return objFormater.compute(obj, sLogs);
	}

//	@Override
//	public String compute(Object obj, String sLog01, String sLog02) throws ExceptionZZZ {
////		IStringJustifierZZZ objJustifier = this.getStringJustifier();
////		LogStringFormaterZZZ objFormater = new LogStringFormaterZZZ(objJustifier);
//		LogStringFormaterZZZ objFormater = new LogStringFormaterZZZ();
//		return objFormater.compute(obj, sLog01, sLog02);
//	}

//	@Override
//	public String compute(Class classObj, String sLog) throws ExceptionZZZ {
////		IStringJustifierZZZ objJustifier = this.getStringJustifier();
////		LogStringFormaterZZZ objFormater = new LogStringFormaterZZZ(objJustifier);
//		LogStringFormaterZZZ objFormater = new LogStringFormaterZZZ();
//		return objFormater.compute(classObj, sLog);
//	}

	@Override
	public String compute(Class classObj, String... sLogs) throws ExceptionZZZ {
//		IStringJustifierZZZ objJustifier = this.getStringJustifier();
//		LogStringFormaterZZZ objFormater = new LogStringFormaterZZZ(objJustifier);
		LogStringFormaterZZZ objFormater = new LogStringFormaterZZZ();
		return objFormater.compute(classObj, sLogs);
	}

//	@Override
//	public String compute(Class classObj, String[] saLog) throws ExceptionZZZ {
////		IStringJustifierZZZ objJustifier = this.getStringJustifier();			
////		ILogStringFormaterZZZ objFormater = new LogStringFormaterZZZ(objJustifier);
//		LogStringFormaterZZZ objFormater = new LogStringFormaterZZZ();		
//		return objFormater.compute(classObj, saLog);
//	}
	
	//######################
	@Override
	public String compute(ILogStringFormaterZZZ objFormater, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		if(this.hasStringJustifierPrivate()) {
			IStringJustifierZZZ objJustifier = this.getStringJustifier();
			objFormater.setStringJustifier(objJustifier);
		}
		return objFormater.compute(ienumFormatLogString);
	}

	@Override
	public String compute(ILogStringFormaterZZZ objFormater, Object obj, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		if(this.hasStringJustifierPrivate()) {
			IStringJustifierZZZ objJustifier = this.getStringJustifier();
			objFormater.setStringJustifier(objJustifier);
		}
		return objFormater.compute(obj, ienumFormatLogString);
	}

//	@Override
//	public String compute(ILogStringFormaterZZZ objFormater, String sLog, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
//		if(this.hasStringJustifierPrivate()) {
//			IStringJustifierZZZ objJustifier = this.getStringJustifier();
//			objFormater.setStringJustifier(objJustifier);
//		}
//		return objFormater.compute(sLog, ienumFormatLogString);
//	}

//	@Override
//	public String compute(ILogStringFormaterZZZ objFormater, String sLog, IEnumSetMappedLogStringFormatZZZ[] ienumaFormatLogString) throws ExceptionZZZ {
//		if(this.hasStringJustifierPrivate()) {
//			IStringJustifierZZZ objJustifier = this.getStringJustifier();
//			objFormater.setStringJustifier(objJustifier);
//		}
//		return objFormater.compute(sLog, ienumaFormatLogString);
//	}

	@Override
	public String compute(ILogStringFormaterZZZ objFormater, IEnumSetMappedLogStringFormatZZZ[] ienumaFormatLogString, String... sLogs) throws ExceptionZZZ {
		if(this.hasStringJustifierPrivate()) {
			IStringJustifierZZZ objJustifier = this.getStringJustifier();
			objFormater.setStringJustifier(objJustifier);
		}
		return objFormater.compute(ienumaFormatLogString, sLogs);
	}

//	@Override
//	public String compute(ILogStringFormaterZZZ objFormater, Object obj, String sLog, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
//		if(this.hasStringJustifierPrivate()) {
//			IStringJustifierZZZ objJustifier = this.getStringJustifier();
//			objFormater.setStringJustifier(objJustifier);
//		}
//		return objFormater.compute(obj, sLog, ienumFormatLogString);
//	}

//	@Override
//	public String compute(ILogStringFormaterZZZ objFormater, Object obj, String sLog, IEnumSetMappedLogStringFormatZZZ[] ienumaFormatLogString) throws ExceptionZZZ {
//		if(this.hasStringJustifierPrivate()) {
//			IStringJustifierZZZ objJustifier = this.getStringJustifier();
//			objFormater.setStringJustifier(objJustifier);
//		}
//		return objFormater.compute(obj, sLog, ienumaFormatLogString);
//	}

//	@Override
//	public String compute(ILogStringFormaterZZZ objFormater, Object obj, String[] saLog, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
//		if(this.hasStringJustifierPrivate()) {
//			IStringJustifierZZZ objJustifier = this.getStringJustifier();
//			objFormater.setStringJustifier(objJustifier);
//		}
//		return objFormater.compute(obj, saLog, ienumFormatLogString);
//	}

//	@Override
//	public String compute(ILogStringFormaterZZZ objFormater, Object obj, String[] saLog, IEnumSetMappedLogStringFormatZZZ[] ienumaFormatLogString) throws ExceptionZZZ {
//		if(this.hasStringJustifierPrivate()) {
//			IStringJustifierZZZ objJustifier = this.getStringJustifier();
//			objFormater.setStringJustifier(objJustifier);
//		}
//		return objFormater.compute(obj, saLog, ienumaFormatLogString);
//	}

	@Override
	public String compute(ILogStringFormaterZZZ objFormater, Object obj, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString, String... sLogs) throws ExceptionZZZ {
		if(this.hasStringJustifierPrivate()) {
			IStringJustifierZZZ objJustifier = this.getStringJustifier();
			objFormater.setStringJustifier(objJustifier);
		}
		return objFormater.compute(obj, ienumFormatLogString, sLogs);
	}

	@Override
	public String compute(ILogStringFormaterZZZ objFormater, Object obj, IEnumSetMappedLogStringFormatZZZ[] ienumaFormatLogString, String... sLogs) throws ExceptionZZZ {
		if(this.hasStringJustifierPrivate()) {
			IStringJustifierZZZ objJustifier = this.getStringJustifier();
			objFormater.setStringJustifier(objJustifier);
		}
		return objFormater.compute(obj, ienumaFormatLogString, sLogs);
	}

//	@Override
//	public String compute(ILogStringFormaterZZZ objFormater, Class classObj, String sLog, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
//		if(this.hasStringJustifierPrivate()) {
//			IStringJustifierZZZ objJustifier = this.getStringJustifier();
//			objFormater.setStringJustifier(objJustifier);
//		}
//		return objFormater.compute(classObj, sLog, ienumFormatLogString);
//	}

//	@Override
//	public String compute(ILogStringFormaterZZZ objFormater, Class classObj, String sLog, IEnumSetMappedLogStringFormatZZZ[] ienumaFormatLogString) throws ExceptionZZZ {
//		if(this.hasStringJustifierPrivate()) {
//			IStringJustifierZZZ objJustifier = this.getStringJustifier();
//			objFormater.setStringJustifier(objJustifier);
//		}
//		return objFormater.compute(classObj, sLog, ienumaFormatLogString);
//	}

	@Override
	public String compute(ILogStringFormaterZZZ objFormater, Class classObj, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString, String... sLogs) throws ExceptionZZZ {
		if(this.hasStringJustifierPrivate()) {
			IStringJustifierZZZ objJustifier = this.getStringJustifier();
			objFormater.setStringJustifier(objJustifier);
		}
		return objFormater.compute(classObj, ienumFormatLogString, sLogs);
	}

	@Override
	public String compute(ILogStringFormaterZZZ objFormater, Class classObj, IEnumSetMappedLogStringFormatZZZ[] ienumaFormatLogString, String... sLogs) throws ExceptionZZZ {
		if(this.hasStringJustifierPrivate()) {
			IStringJustifierZZZ objJustifier = this.getStringJustifier();
			objFormater.setStringJustifier(objJustifier);
		}
		return objFormater.compute(classObj, ienumaFormatLogString, sLogs);
	}

	@Override
	public String compute(ILogStringFormaterZZZ objFormater, Class classObj, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		if(this.hasStringJustifierPrivate()) {
			IStringJustifierZZZ objJustifier = this.getStringJustifier();
			objFormater.setStringJustifier(objJustifier);
		}
		return objFormater.compute(classObj, ienumFormatLogString);
	}

	@Override
	public String compute(ILogStringFormaterZZZ objFormater, LinkedHashMap<IEnumSetMappedLogStringFormatZZZ, String> hm) throws ExceptionZZZ {
		if(this.hasStringJustifierPrivate()) {
			IStringJustifierZZZ objJustifier = this.getStringJustifier();
			objFormater.setStringJustifier(objJustifier);
		}
		return objFormater.compute(hm);
	}

	@Override
	public String compute(ILogStringFormaterZZZ objFormater, Object obj, LinkedHashMap<IEnumSetMappedLogStringFormatZZZ, String> hm) throws ExceptionZZZ {
		if(this.hasStringJustifierPrivate()) {
			IStringJustifierZZZ objJustifier = this.getStringJustifier();
			objFormater.setStringJustifier(objJustifier);
		}
		return objFormater.compute(obj, hm);
	}

	@Override
	public String compute(ILogStringFormaterZZZ objFormater, Class classObj, LinkedHashMap<IEnumSetMappedLogStringFormatZZZ, String> hm) throws ExceptionZZZ {
		if(this.hasStringJustifierPrivate()) {
			IStringJustifierZZZ objJustifier = this.getStringJustifier();
			objFormater.setStringJustifier(objJustifier);
		}
		return objFormater.compute(classObj, hm);
	}

//	@Override
//	public String compute(ILogStringFormaterZZZ objFormater, Object obj, String sLog) throws ExceptionZZZ {
//		if(this.hasStringJustifierPrivate()) {
//			IStringJustifierZZZ objJustifier = this.getStringJustifier();
//			objFormater.setStringJustifier(objJustifier);
//		}
//		return objFormater.compute(obj, sLog);
//	}

//	@Override
//	public String compute(ILogStringFormaterZZZ objFormater, Object obj, String[] saLog) throws ExceptionZZZ {
//		if(this.hasStringJustifierPrivate()) {
//			IStringJustifierZZZ objJustifier = this.getStringJustifier();
//			objFormater.setStringJustifier(objJustifier);
//		}
//		return objFormater.compute(obj, saLog);
//	}

	@Override
	public String compute(ILogStringFormaterZZZ objFormater, Object obj, String... sLogs) throws ExceptionZZZ {
		if(this.hasStringJustifierPrivate()) {
			IStringJustifierZZZ objJustifier = this.getStringJustifier();
			objFormater.setStringJustifier(objJustifier);
		}
		return objFormater.compute(obj, sLogs);
	}

//	@Override
//	public String compute(ILogStringFormaterZZZ objFormater, Class classObj, String sLog) throws ExceptionZZZ {
//		if(this.hasStringJustifierPrivate()) {
//			IStringJustifierZZZ objJustifier = this.getStringJustifier();
//			objFormater.setStringJustifier(objJustifier);
//		}
//		return objFormater.compute(classObj, sLog);
//	}

	@Override
	public String compute(ILogStringFormaterZZZ objFormater, Class classObj, String... sLogs) throws ExceptionZZZ {
		if(this.hasStringJustifierPrivate()) {
			IStringJustifierZZZ objJustifier = this.getStringJustifier();
			objFormater.setStringJustifier(objJustifier);
		}
		return objFormater.compute(classObj, sLogs);
	}

//	@Override
//	public String compute(ILogStringFormaterZZZ objFormater, Class classObj, String[] saLog) throws ExceptionZZZ {
//		if(this.hasStringJustifierPrivate()) {
//			IStringJustifierZZZ objJustifier = this.getStringJustifier();
//			objFormater.setStringJustifier(objJustifier);
//		}
//		return objFormater.compute(classObj, saLog);
//	}
		
	//###################################################
	//### FLAG: ILogStringFormatManagerZZZ
	//###################################################
	@Override
	public boolean getFlag(ILogStringFormatManagerZZZ.FLAGZ objEnumFlag) {
		return this.getFlag(objEnumFlag.name());
	}	
	
	@Override
	public boolean setFlag(ILogStringFormatManagerZZZ.FLAGZ objEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		return this.setFlag(objEnumFlag.name(), bFlagValue);
	}

	@Override
	public boolean[] setFlag(ILogStringFormatManagerZZZ.FLAGZ[] objaEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		boolean[] baReturn=null;
		main:{
			if(!ArrayUtilZZZ.isNull(objaEnumFlag)) {
				baReturn = new boolean[objaEnumFlag.length];
				int iCounter=-1;
				for(ILogStringFormatManagerZZZ.FLAGZ objEnumFlag:objaEnumFlag) {
					iCounter++;
					boolean bReturn = this.setFlag(objEnumFlag, bFlagValue);
					baReturn[iCounter]=bReturn;
				}
				
				//!!! Ein mögliches init-Flag ist beim direkten setzen der Flags unlogisch.
				//    Es wird entfernt.
				this.setFlag(IFlagZEnabledZZZ.FLAGZ.INIT, false);
			}
		}//end main:
		return baReturn;
	}

	@Override
	public boolean proofFlagExists(ILogStringFormatManagerZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
		return this.proofFlagExists(objEnumFlag.name());
	}

	@Override
	public boolean proofFlagSetBefore(ILogStringFormatManagerZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
		return this.proofFlagSetBefore(objEnumFlag.name());
	}
}
