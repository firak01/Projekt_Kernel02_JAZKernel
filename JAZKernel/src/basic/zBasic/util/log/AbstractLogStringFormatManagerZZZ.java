package basic.zBasic.util.log;

import java.util.LinkedHashMap;

import basic.zBasic.AbstractObjectWithFlagZZZ;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.datatype.string.IStringJustifierZZZ;
import basic.zBasic.util.datatype.string.StringJustifierZZZ;
import basic.zKernel.flag.IFlagZEnabledZZZ;

public abstract class AbstractLogStringFormatManagerZZZ extends AbstractObjectWithFlagZZZ implements ILogStringFormatManagerZZZ, ILogStringFormatManagerJaggedZZZ{
	private static final long serialVersionUID = 432992680546312138L;
	
	// --- Singleton Instanz ---
	//muss als Singleton static sein. //Muss in der Konkreten Manager Klasse definiert sein, da ja unterschiedlich
	//protected static ILogStringFormatManagerZZZ objLogStringManagerINSTANCE; //muss als Singleton static sein
	
	// --- Globale Objekte ---
	//Zum Buendig machen
	//Idee: Bei mehreren Instanzen per Default auf das Singleton zugreifen
	//      Aber ggfs. ueberschreibend auf einen hinterlegten zugreifen.
	//Merke: XML-Strings werden nicht buendig gemacht. 
	//       Darum wird der StringJustifier nicht mehr in dieser Elternklasse im Konstruktor an den "Formater" uebergeben.
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
	
	
	//#################################################################
	//### COMPUTE
	//#################################################################
	@Override
	public String compute(IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		ILogStringFormaterZZZ objFormater = new LogStringFormaterZZZ();
		return objFormater.compute(ienumFormatLogString);
	}

	@Override
	public String compute(Object obj, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {		
		ILogStringFormaterZZZ objFormater = new LogStringFormaterZZZ();
		return objFormater.compute(obj, ienumFormatLogString);
	}
	
	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

	@Override
	public String compute(String... sLogs) throws ExceptionZZZ {		
		ILogStringFormaterZZZ objFormater = new LogStringFormaterZZZ();
		return objFormater.compute(null, (IEnumSetMappedLogStringFormatZZZ[]) null, sLogs);
	}
	
	@Override
	public String compute(IEnumSetMappedLogStringFormatZZZ[] ienumaFormatLogString, String... sLogs)	throws ExceptionZZZ {
		ILogStringFormaterZZZ objFormater = new LogStringFormaterZZZ();
		return objFormater.compute(ienumaFormatLogString, sLogs);
	}

	@Override
	public String compute(Object obj, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString, String... sLogs) throws ExceptionZZZ {
		ILogStringFormaterZZZ objFormater = new LogStringFormaterZZZ();
		return objFormater.compute(obj, ienumFormatLogString, sLogs);
	}

	@Override
	public String compute(Object obj, IEnumSetMappedLogStringFormatZZZ[] ienumaFormatLogString, String... sLogs) throws ExceptionZZZ {
		ILogStringFormaterZZZ objFormater = new LogStringFormaterZZZ();
		return objFormater.compute(obj, ienumaFormatLogString, sLogs);
	}

	@Override
	public String compute(Class classObj, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString, String... sLogs) throws ExceptionZZZ {
		ILogStringFormaterZZZ objFormater = new LogStringFormaterZZZ();
		return objFormater.compute(classObj, ienumFormatLogString, sLogs);
	}

	@Override
	public String compute(Class classObj, IEnumSetMappedLogStringFormatZZZ[] ienumaFormatLogString, String... sLogs) throws ExceptionZZZ {
		ILogStringFormaterZZZ objFormater = new LogStringFormaterZZZ();
		return objFormater.compute(classObj, ienumaFormatLogString, sLogs);
	}

	@Override
	public String compute(Class classObj, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		ILogStringFormaterZZZ objFormater = new LogStringFormaterZZZ();
		return objFormater.compute(classObj, ienumFormatLogString);
	}

	@Override
	public String compute(LinkedHashMap<IEnumSetMappedLogStringFormatZZZ, String> hm) throws ExceptionZZZ {
		ILogStringFormaterZZZ objFormater = new LogStringFormaterZZZ();
		return objFormater.compute(hm);
	}

	@Override
	public String compute(Object obj, LinkedHashMap<IEnumSetMappedLogStringFormatZZZ, String> hm) throws ExceptionZZZ {
		ILogStringFormaterZZZ objFormater = new LogStringFormaterZZZ();
		return objFormater.compute(obj, hm);
	}

	@Override
	public String compute(Class classObj, LinkedHashMap<IEnumSetMappedLogStringFormatZZZ, String> hm) throws ExceptionZZZ {
		ILogStringFormaterZZZ objFormater = new LogStringFormaterZZZ();
		return objFormater.compute(classObj, hm);
	}

	@Override
	public String compute(Object obj, String... sLogs) throws ExceptionZZZ {
		ILogStringFormaterZZZ objFormater = new LogStringFormaterZZZ();
		return objFormater.compute(obj, sLogs);
	}

	@Override
	public String compute(Class classObj, String... sLogs) throws ExceptionZZZ {
		ILogStringFormaterZZZ objFormater = new LogStringFormaterZZZ();
		return objFormater.compute(classObj, sLogs);
	}
	
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

	@Override
	public String compute(ILogStringFormaterZZZ objFormater, IEnumSetMappedLogStringFormatZZZ[] ienumaFormatLogString, String... sLogs) throws ExceptionZZZ {
		if(this.hasStringJustifierPrivate()) {
			IStringJustifierZZZ objJustifier = this.getStringJustifier();
			objFormater.setStringJustifier(objJustifier);
		}
		return objFormater.compute(ienumaFormatLogString, sLogs);
	}

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

	@Override
	public String compute(ILogStringFormaterZZZ objFormater, Object obj, String... sLogs) throws ExceptionZZZ {
		if(this.hasStringJustifierPrivate()) {
			IStringJustifierZZZ objJustifier = this.getStringJustifier();
			objFormater.setStringJustifier(objJustifier);
		}
		return objFormater.compute(obj, sLogs);
	}

	@Override
	public String compute(ILogStringFormaterZZZ objFormater, Class classObj, String... sLogs) throws ExceptionZZZ {
		if(this.hasStringJustifierPrivate()) {
			IStringJustifierZZZ objJustifier = this.getStringJustifier();
			objFormater.setStringJustifier(objJustifier);
		}
		return objFormater.compute(classObj, sLogs);
	}
		
	//#####################################################
	//### Aus ILogStringFormat Manager JAGGED
	//#####################################################
	@Override
	public String computeJagged(String... sLogs) throws ExceptionZZZ {
		ILogStringFormaterZZZ objFormater = new LogStringFormaterZZZ();
		return objFormater.computeJagged(sLogs);
	}

	
	@Override
	public String computeJagged(Object obj, String... sLogs) throws ExceptionZZZ {
		ILogStringFormaterZZZ objFormater = new LogStringFormaterZZZ();
		return objFormater.computeJagged(obj, sLogs);
	}

	@Override
	public String computeJagged(Class classObj, String... sLogs) throws ExceptionZZZ {
		ILogStringFormaterZZZ objFormater = new LogStringFormaterZZZ();
		return objFormater.computeJagged(classObj, (IEnumSetMappedLogStringFormatZZZ[]) null, sLogs);
	}

	
	
	@Override
	public String computeJagged(LinkedHashMap<IEnumSetMappedLogStringFormatZZZ, String> hm) throws ExceptionZZZ {
		ILogStringFormaterZZZ objFormater = new LogStringFormaterZZZ();
		return objFormater.computeJagged(hm);
	}

	@Override
	public String computeJagged(Object obj, LinkedHashMap<IEnumSetMappedLogStringFormatZZZ, String> hm)	throws ExceptionZZZ {
		ILogStringFormaterZZZ objFormater = new LogStringFormaterZZZ();
		return objFormater.computeJagged(obj, hm);
	}

	@Override
	public String computeJagged(Class classObj, LinkedHashMap<IEnumSetMappedLogStringFormatZZZ, String> hmLog) throws ExceptionZZZ {
		ILogStringFormaterZZZ objFormater = new LogStringFormaterZZZ();
		return objFormater.computeJagged(classObj, hmLog);
	}

	@Override
	public String computeJagged(IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		ILogStringFormaterZZZ objFormater = new LogStringFormaterZZZ();
		return objFormater.computeJagged(ienumFormatLogString);
	}
	
	@Override
	public String computeJagged(IEnumSetMappedLogStringFormatZZZ[] ienumaFormatLogString, String... sLogs) throws ExceptionZZZ {
		ILogStringFormaterZZZ objFormater = new LogStringFormaterZZZ();
		return objFormater.computeJagged(ienumaFormatLogString, sLogs);
	}


	
	@Override
	public String computeJagged(Object obj, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		ILogStringFormaterZZZ objFormater = new LogStringFormaterZZZ();
		return objFormater.computeJagged(obj, ienumFormatLogString);
	}
	
	@Override
	public String computeJagged(Class classObj, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		ILogStringFormaterZZZ objFormater = new LogStringFormaterZZZ();
		return objFormater.computeJagged(classObj, ienumFormatLogString);
	}

	@Override
	public String computeJagged(Object obj, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString, String... sLogs) throws ExceptionZZZ {
		ILogStringFormaterZZZ objFormater = new LogStringFormaterZZZ();
		return objFormater.computeJagged(obj, ienumFormatLogString, sLogs);
	}

	@Override
	public String computeJagged(Class classObj, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString, String... sLogs) throws ExceptionZZZ {
		ILogStringFormaterZZZ objFormater = new LogStringFormaterZZZ();
		return objFormater.computeJagged(classObj, ienumFormatLogString, sLogs);
	}

	@Override
	public String computeJagged(Object obj, IEnumSetMappedLogStringFormatZZZ[] ienumaFormatLogString, String... sLogs) throws ExceptionZZZ {
		ILogStringFormaterZZZ objFormater = new LogStringFormaterZZZ();
		return objFormater.computeJagged(obj, ienumaFormatLogString, sLogs);
	}

	@Override
	public String computeJagged(Class classObj, IEnumSetMappedLogStringFormatZZZ[] ienumaFormatLogString, String... sLogs) throws ExceptionZZZ {
		ILogStringFormaterZZZ objFormater = new LogStringFormaterZZZ();
		return objFormater.computeJagged(classObj, ienumaFormatLogString, sLogs);
	}

	@Override
	public String computeJagged(ILogStringFormaterZZZ objFormater, Object obj, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		return objFormater.computeJagged(obj, ienumFormatLogString);
	}

	@Override
	public String computeJagged(ILogStringFormaterZZZ objFormater, IEnumSetMappedLogStringFormatZZZ[] ienumaFormatLogString, String... sLogs) throws ExceptionZZZ {
		return objFormater.computeJagged(ienumaFormatLogString, sLogs);
	}

	@Override
	public String computeJagged(ILogStringFormaterZZZ objFormater, Object obj, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString, String... sLogs) throws ExceptionZZZ {
		return objFormater.computeJagged(obj, ienumFormatLogString, sLogs);
	}

	@Override
	public String computeJagged(ILogStringFormaterZZZ objFormater, Object obj, IEnumSetMappedLogStringFormatZZZ[] ienumaFormatLogString, String... sLogs) throws ExceptionZZZ {
		return objFormater.computeJagged(obj, ienumaFormatLogString, sLogs);
	}

	@Override
	public String computeJagged(ILogStringFormaterZZZ objFormater, Class classObj, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString, String... sLogs) throws ExceptionZZZ {
		return objFormater.computeJagged(classObj, ienumFormatLogString, sLogs);
	}

	@Override
	public String computeJagged(ILogStringFormaterZZZ objFormater, Class classObj, IEnumSetMappedLogStringFormatZZZ[] ienumaFormatLogString, String... sLogs) throws ExceptionZZZ {
		return objFormater.computeJagged(classObj, ienumaFormatLogString, sLogs);
	}

	@Override
	public String computeJagged(ILogStringFormaterZZZ objFormater, Class classObj, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		return objFormater.computeJagged(classObj, ienumFormatLogString);
	}

	@Override
	public String computeJagged(ILogStringFormaterZZZ objFormater, LinkedHashMap<IEnumSetMappedLogStringFormatZZZ, String> hm) throws ExceptionZZZ {
		return objFormater.computeJagged(hm);
	}

	@Override
	public String computeJagged(ILogStringFormaterZZZ objFormater, Object obj, LinkedHashMap<IEnumSetMappedLogStringFormatZZZ, String> hm) throws ExceptionZZZ {
		return objFormater.computeJagged(obj, hm);
	}

	@Override
	public String computeJagged(ILogStringFormaterZZZ objFormater, Class classObj, LinkedHashMap<IEnumSetMappedLogStringFormatZZZ, String> hm) throws ExceptionZZZ {
		return objFormater.computeJagged(classObj, hm);
	}

	@Override
	public String computeJagged(ILogStringFormaterZZZ objFormater, Object obj, String... sLogs) throws ExceptionZZZ {
		return objFormater.computeJagged(obj, sLogs);
	}

	@Override
	public String computeJagged(ILogStringFormaterZZZ objFormater, Class classObj, String... sLogs)	throws ExceptionZZZ {
		return objFormater.computeJagged(classObj, sLogs);
	}

	@Override
	public String computeJagged(ILogStringFormaterZZZ objFormater, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		return objFormater.computeJagged(ienumFormatLogString);
	}
	
	//###################################################
	//### Merke: COMPUTE JUSTIFIED gibt es nicht bei XML-Format Manager, darum die entsprechenden Methoden direkt in FormatManager-Klasse
	//###################################################

	
	
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
