package basic.zBasic.util.log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;

import basic.zBasic.AbstractObjectWithFlagZZZ;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IReflectCodeZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.reflection.position.TagTypeFileNameZZZ;
import basic.zBasic.reflection.position.TagTypeFilePositionZZZ;
import basic.zBasic.reflection.position.TagTypeLineNumberZZZ;
import basic.zBasic.reflection.position.TagTypeMethodZZZ;
import basic.zBasic.reflection.position.TagTypePositionCurrentZZZ;
import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedZZZ;
import basic.zBasic.util.abstractList.ArrayListUniqueZZZ;
import basic.zBasic.util.abstractList.ArrayListUtilZZZ;
import basic.zBasic.util.abstractList.HashMapIndexedZZZ;
import basic.zBasic.util.abstractList.HashMapMultiIndexedZZZ;
import basic.zBasic.util.datatype.enums.EnumAvailableHelperZZZ;
import basic.zBasic.util.datatype.string.StringArrayZZZ;
import basic.zBasic.util.datatype.string.StringJustifierZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.datatype.xml.XmlUtilZZZ;
import basic.zBasic.util.file.FileEasyZZZ;
import basic.zBasic.util.math.PrimeNumberZZZ;
import basic.zBasic.xml.tagtype.ITagByTypeZZZ;
import basic.zBasic.xml.tagtype.ITagTypeZZZ;
import basic.zBasic.xml.tagtype.TagByTypeFactoryZZZ;
import basic.zBasic.xml.tagtype.TagByTypeZZZ;
import basic.zKernel.flag.IFlagZEnabledZZZ;

public abstract class AbstractLogStringFormatManagerZZZ extends AbstractObjectWithFlagZZZ implements ILogStringFormatManagerZZZ{
	private static final long serialVersionUID = 432992680546312138L;
	
	// --- Singleton Instanz ---
	//muss als Singleton static sein. //Muss in der Konkreten Manager Klasse definiert sein, da ja unterschiedlich
	//protected static ILogStringFormatManagerZZZ objLogStringManagerINSTANCE; //muss als Singleton static sein
	
	// --- Globale Objekte ---
	//Zum Buendig machen
	protected volatile StringJustifierZZZ objStringJustifier = null;
		
	
	//##########################################################	
	//### GETTER / SETTER
	
	@Override
	public StringJustifierZZZ getStringJustifier() throws ExceptionZZZ {
		if(this.objStringJustifier==null) {
			this.objStringJustifier = new StringJustifierZZZ();
		}
		return this.objStringJustifier;
	}

	@Override
	public void setStringJustifier(StringJustifierZZZ objStringJustifier) throws ExceptionZZZ {
		this.objStringJustifier = objStringJustifier;
	}

	//##########################################################
	//### METHODEN ##########
	@Override
	public boolean reset() throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			this.setStringJustifier(null);
			
			bReturn = true;
		}//end main:
		return bReturn;
	}
	
	
	@Override
	public String compute(IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		StringJustifierZZZ objJustifier = this.getStringJustifier();
		LogStringFormaterZZZ objFormater = new LogStringFormaterZZZ(objJustifier);
		return objFormater.compute(ienumFormatLogString);
	}

	@Override
	public String compute(Object obj, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		StringJustifierZZZ objJustifier = this.getStringJustifier();
		LogStringFormaterZZZ objFormater = new LogStringFormaterZZZ(objJustifier);
		return objFormater.compute(obj, ienumFormatLogString);
	}

	@Override
	public String compute(String sLog, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		StringJustifierZZZ objJustifier = this.getStringJustifier();
		LogStringFormaterZZZ objFormater = new LogStringFormaterZZZ(objJustifier);
		return objFormater.compute(sLog, ienumFormatLogString);
	}

	@Override
	public String compute(String sLog, IEnumSetMappedLogStringFormatZZZ[] ienumaFormatLogString) throws ExceptionZZZ {
		StringJustifierZZZ objJustifier = this.getStringJustifier();
		LogStringFormaterZZZ objFormater = new LogStringFormaterZZZ(objJustifier);
		return objFormater.compute(sLog, ienumaFormatLogString);
	}

	@Override
	public String compute(String[] saLog, IEnumSetMappedLogStringFormatZZZ[] ienumaFormatLogString)	throws ExceptionZZZ {
		StringJustifierZZZ objJustifier = this.getStringJustifier();
		LogStringFormaterZZZ objFormater = new LogStringFormaterZZZ(objJustifier);
		return objFormater.compute(saLog, ienumaFormatLogString);
	}

	@Override
	public String compute(Object obj, String sLog, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		StringJustifierZZZ objJustifier = this.getStringJustifier();
		LogStringFormaterZZZ objFormater = new LogStringFormaterZZZ(objJustifier);
		return objFormater.compute(sLog, ienumFormatLogString);
	}

	@Override
	public String compute(Object obj, String sLog, IEnumSetMappedLogStringFormatZZZ[] ienumaFormatLogString) throws ExceptionZZZ {
		StringJustifierZZZ objJustifier = this.getStringJustifier();
		LogStringFormaterZZZ objFormater = new LogStringFormaterZZZ(objJustifier);
		return objFormater.compute(obj, sLog, ienumaFormatLogString);
	}

	@Override
	public String compute(Object obj, String[] saLog, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		StringJustifierZZZ objJustifier = this.getStringJustifier();
		LogStringFormaterZZZ objFormater = new LogStringFormaterZZZ(objJustifier);
		return objFormater.compute(obj, saLog, ienumFormatLogString);
	}

	@Override
	public String compute(Object obj, String[] saLog, IEnumSetMappedLogStringFormatZZZ[] ienumaFormatLogString) throws ExceptionZZZ {
		StringJustifierZZZ objJustifier = this.getStringJustifier();
		LogStringFormaterZZZ objFormater = new LogStringFormaterZZZ(objJustifier);
		return objFormater.compute(obj, saLog, ienumaFormatLogString);
	}

	@Override
	public String compute(Object obj, String sLog01, String sLog02, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		StringJustifierZZZ objJustifier = this.getStringJustifier();
		LogStringFormaterZZZ objFormater = new LogStringFormaterZZZ(objJustifier);
		return objFormater.compute(obj, sLog01, sLog02, ienumFormatLogString);
	}

	@Override
	public String compute(Object obj, String sLog01, String sLog02, IEnumSetMappedLogStringFormatZZZ[] ienumaFormatLogString) throws ExceptionZZZ {
		StringJustifierZZZ objJustifier = this.getStringJustifier();
		LogStringFormaterZZZ objFormater = new LogStringFormaterZZZ(objJustifier);
		return objFormater.compute(obj, sLog01, sLog02, ienumaFormatLogString);
	}

	@Override
	public String compute(Class classObj, String sLog, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		StringJustifierZZZ objJustifier = this.getStringJustifier();
		LogStringFormaterZZZ objFormater = new LogStringFormaterZZZ(objJustifier);
		return objFormater.compute(classObj, sLog, ienumFormatLogString);
	}

	@Override
	public String compute(Class classObj, String sLog, IEnumSetMappedLogStringFormatZZZ[] ienumaFormatLogString) throws ExceptionZZZ {
		StringJustifierZZZ objJustifier = this.getStringJustifier();
		LogStringFormaterZZZ objFormater = new LogStringFormaterZZZ(objJustifier);
		return objFormater.compute(classObj, sLog, ienumaFormatLogString);
	}

	@Override
	public String compute(Class classObj, String[] saLog, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		StringJustifierZZZ objJustifier = this.getStringJustifier();
		LogStringFormaterZZZ objFormater = new LogStringFormaterZZZ(objJustifier);
		return objFormater.compute(classObj, saLog, ienumFormatLogString);
	}

	@Override
	public String compute(Class classObj, String[] saLog, IEnumSetMappedLogStringFormatZZZ[] ienumaFormatLogString) throws ExceptionZZZ {
		StringJustifierZZZ objJustifier = this.getStringJustifier();
		LogStringFormaterZZZ objFormater = new LogStringFormaterZZZ(objJustifier);
		return objFormater.compute(classObj, saLog, ienumaFormatLogString);
	}

	@Override
	public String compute(Class classObj, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		StringJustifierZZZ objJustifier = this.getStringJustifier();
		LogStringFormaterZZZ objFormater = new LogStringFormaterZZZ(objJustifier);
		return objFormater.compute(classObj, ienumFormatLogString);
	}

	@Override
	public String compute(LinkedHashMap<IEnumSetMappedLogStringFormatZZZ, String> hm) throws ExceptionZZZ {
		StringJustifierZZZ objJustifier = this.getStringJustifier();
		LogStringFormaterZZZ objFormater = new LogStringFormaterZZZ(objJustifier);
		return objFormater.compute(hm);
	}

	@Override
	public String compute(Object obj, LinkedHashMap<IEnumSetMappedLogStringFormatZZZ, String> hm) throws ExceptionZZZ {
		StringJustifierZZZ objJustifier = this.getStringJustifier();
		LogStringFormaterZZZ objFormater = new LogStringFormaterZZZ(objJustifier);
		return objFormater.compute(obj, hm);
	}

	@Override
	public String compute(Class classObj, LinkedHashMap<IEnumSetMappedLogStringFormatZZZ, String> hm) throws ExceptionZZZ {
		StringJustifierZZZ objJustifier = this.getStringJustifier();
		LogStringFormaterZZZ objFormater = new LogStringFormaterZZZ(objJustifier);
		return objFormater.compute(classObj, hm);
	}

	@Override
	public String compute(Object obj, String sLog) throws ExceptionZZZ {
		StringJustifierZZZ objJustifier = this.getStringJustifier();
		LogStringFormaterZZZ objFormater = new LogStringFormaterZZZ(objJustifier);
		return objFormater.compute(obj, sLog);
	}

	@Override
	public String compute(Object obj, String[] saLog) throws ExceptionZZZ {
		StringJustifierZZZ objJustifier = this.getStringJustifier();
		LogStringFormaterZZZ objFormater = new LogStringFormaterZZZ(objJustifier);
		return objFormater.compute(obj, saLog);
	}

	@Override
	public String compute(Object obj, String sLog01, String sLog02) throws ExceptionZZZ {
		StringJustifierZZZ objJustifier = this.getStringJustifier();
		LogStringFormaterZZZ objFormater = new LogStringFormaterZZZ(objJustifier);
		return objFormater.compute(obj, sLog01, sLog02);
	}

	@Override
	public String compute(Class classObj, String sLog) throws ExceptionZZZ {
		StringJustifierZZZ objJustifier = this.getStringJustifier();
		LogStringFormaterZZZ objFormater = new LogStringFormaterZZZ(objJustifier);
		return objFormater.compute(classObj, sLog);
	}

	@Override
	public String compute(Class classObj, String sLog01, String sLog02) throws ExceptionZZZ {
		StringJustifierZZZ objJustifier = this.getStringJustifier();
		LogStringFormaterZZZ objFormater = new LogStringFormaterZZZ(objJustifier);
		return objFormater.compute(classObj, sLog01, sLog02);
	}

	@Override
	public String compute(Class classObj, String[] saLog) throws ExceptionZZZ {
		StringJustifierZZZ objJustifier = this.getStringJustifier();
		LogStringFormaterZZZ objFormater = new LogStringFormaterZZZ(objJustifier);
		return objFormater.compute(classObj, saLog);
	}
	
	//######################
	@Override
	public String compute(ILogStringFormaterZZZ objFormater, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		StringJustifierZZZ objJustifier = this.getStringJustifier();
		objFormater.setStringJustifier(objJustifier);
		return objFormater.compute(ienumFormatLogString);
	}

	@Override
	public String compute(ILogStringFormaterZZZ objFormater, Object obj, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		StringJustifierZZZ objJustifier = this.getStringJustifier();
		objFormater.setStringJustifier(objJustifier);
		return objFormater.compute(obj, ienumFormatLogString);
	}

	@Override
	public String compute(ILogStringFormaterZZZ objFormater, String sLog, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		StringJustifierZZZ objJustifier = this.getStringJustifier();
		objFormater.setStringJustifier(objJustifier);
		return objFormater.compute(sLog, ienumFormatLogString);
	}

	@Override
	public String compute(ILogStringFormaterZZZ objFormater, String sLog, IEnumSetMappedLogStringFormatZZZ[] ienumaFormatLogString) throws ExceptionZZZ {
		StringJustifierZZZ objJustifier = this.getStringJustifier();
		objFormater.setStringJustifier(objJustifier);
		return objFormater.compute(sLog, ienumaFormatLogString);
	}

	@Override
	public String compute(ILogStringFormaterZZZ objFormater, String[] saLog, IEnumSetMappedLogStringFormatZZZ[] ienumaFormatLogString) throws ExceptionZZZ {
		StringJustifierZZZ objJustifier = this.getStringJustifier();
		objFormater.setStringJustifier(objJustifier);
		return objFormater.compute(saLog, ienumaFormatLogString);
	}

	@Override
	public String compute(ILogStringFormaterZZZ objFormater, Object obj, String sLog, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		StringJustifierZZZ objJustifier = this.getStringJustifier();
		objFormater.setStringJustifier(objJustifier);
		return objFormater.compute(obj, sLog, ienumFormatLogString);
	}

	@Override
	public String compute(ILogStringFormaterZZZ objFormater, Object obj, String sLog, IEnumSetMappedLogStringFormatZZZ[] ienumaFormatLogString) throws ExceptionZZZ {
		StringJustifierZZZ objJustifier = this.getStringJustifier();
		objFormater.setStringJustifier(objJustifier);
		return objFormater.compute(obj, sLog, ienumaFormatLogString);
	}

	@Override
	public String compute(ILogStringFormaterZZZ objFormater, Object obj, String[] saLog, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		StringJustifierZZZ objJustifier = this.getStringJustifier();
		objFormater.setStringJustifier(objJustifier);
		return objFormater.compute(obj, saLog, ienumFormatLogString);
	}

	@Override
	public String compute(ILogStringFormaterZZZ objFormater, Object obj, String[] saLog, IEnumSetMappedLogStringFormatZZZ[] ienumaFormatLogString) throws ExceptionZZZ {
		StringJustifierZZZ objJustifier = this.getStringJustifier();
		objFormater.setStringJustifier(objJustifier);
		return objFormater.compute(obj, saLog, ienumaFormatLogString);
	}

	@Override
	public String compute(ILogStringFormaterZZZ objFormater, Object obj, String sLog01, String sLog02, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		StringJustifierZZZ objJustifier = this.getStringJustifier();
		objFormater.setStringJustifier(objJustifier);
		return objFormater.compute(obj, sLog01, sLog02, ienumFormatLogString);
	}

	@Override
	public String compute(ILogStringFormaterZZZ objFormater, Object obj, String sLog01, String sLog02, IEnumSetMappedLogStringFormatZZZ[] ienumaFormatLogString) throws ExceptionZZZ {
		StringJustifierZZZ objJustifier = this.getStringJustifier();
		objFormater.setStringJustifier(objJustifier);
		return objFormater.compute(obj, sLog01, sLog02, ienumaFormatLogString);
	}

	@Override
	public String compute(ILogStringFormaterZZZ objFormater, Class classObj, String sLog, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		StringJustifierZZZ objJustifier = this.getStringJustifier();
		objFormater.setStringJustifier(objJustifier);
		return objFormater.compute(classObj, sLog, ienumFormatLogString);
	}

	@Override
	public String compute(ILogStringFormaterZZZ objFormater, Class classObj, String sLog, IEnumSetMappedLogStringFormatZZZ[] ienumaFormatLogString) throws ExceptionZZZ {
		StringJustifierZZZ objJustifier = this.getStringJustifier();
		objFormater.setStringJustifier(objJustifier);
		return objFormater.compute(classObj, sLog, ienumaFormatLogString);
	}

	@Override
	public String compute(ILogStringFormaterZZZ objFormater, Class classObj, String[] saLog, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		StringJustifierZZZ objJustifier = this.getStringJustifier();
		objFormater.setStringJustifier(objJustifier);
		return objFormater.compute(classObj, saLog, ienumFormatLogString);
	}

	@Override
	public String compute(ILogStringFormaterZZZ objFormater, Class classObj, String[] saLog, IEnumSetMappedLogStringFormatZZZ[] ienumaFormatLogString) throws ExceptionZZZ {
		StringJustifierZZZ objJustifier = this.getStringJustifier();
		objFormater.setStringJustifier(objJustifier);
		return objFormater.compute(classObj, saLog, ienumaFormatLogString);
	}

	@Override
	public String compute(ILogStringFormaterZZZ objFormater, Class classObj, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		StringJustifierZZZ objJustifier = this.getStringJustifier();
		objFormater.setStringJustifier(objJustifier);
		return objFormater.compute(classObj, ienumFormatLogString);
	}

	@Override
	public String compute(ILogStringFormaterZZZ objFormater, LinkedHashMap<IEnumSetMappedLogStringFormatZZZ, String> hm) throws ExceptionZZZ {
		StringJustifierZZZ objJustifier = this.getStringJustifier();
		objFormater.setStringJustifier(objJustifier);
		return objFormater.compute(hm);
	}

	@Override
	public String compute(ILogStringFormaterZZZ objFormater, Object obj, LinkedHashMap<IEnumSetMappedLogStringFormatZZZ, String> hm) throws ExceptionZZZ {
		StringJustifierZZZ objJustifier = this.getStringJustifier();
		objFormater.setStringJustifier(objJustifier);
		return objFormater.compute(obj, hm);
	}

	@Override
	public String compute(ILogStringFormaterZZZ objFormater, Class classObj, LinkedHashMap<IEnumSetMappedLogStringFormatZZZ, String> hm) throws ExceptionZZZ {
		StringJustifierZZZ objJustifier = this.getStringJustifier();
		objFormater.setStringJustifier(objJustifier);
		return objFormater.compute(classObj, hm);
	}

	@Override
	public String compute(ILogStringFormaterZZZ objFormater, Object obj, String sLog) throws ExceptionZZZ {
		StringJustifierZZZ objJustifier = this.getStringJustifier();
		objFormater.setStringJustifier(objJustifier);
		return objFormater.compute(obj, sLog);
	}

	@Override
	public String compute(ILogStringFormaterZZZ objFormater, Object obj, String[] saLog) throws ExceptionZZZ {
		StringJustifierZZZ objJustifier = this.getStringJustifier();
		objFormater.setStringJustifier(objJustifier);
		return objFormater.compute(obj, saLog);
	}

	@Override
	public String compute(ILogStringFormaterZZZ objFormater, Object obj, String sLog01, String sLog02) throws ExceptionZZZ {
		StringJustifierZZZ objJustifier = this.getStringJustifier();
		objFormater.setStringJustifier(objJustifier);
		return objFormater.compute(obj, sLog01, sLog02);
	}

	@Override
	public String compute(ILogStringFormaterZZZ objFormater, Class classObj, String sLog) throws ExceptionZZZ {
		StringJustifierZZZ objJustifier = this.getStringJustifier();
		objFormater.setStringJustifier(objJustifier);
		return objFormater.compute(classObj, sLog);
	}

	@Override
	public String compute(ILogStringFormaterZZZ objFormater, Class classObj, String sLog01, String sLog02) throws ExceptionZZZ {
		StringJustifierZZZ objJustifier = this.getStringJustifier();
		objFormater.setStringJustifier(objJustifier);
		return objFormater.compute(classObj, sLog01, sLog02);
	}

	@Override
	public String compute(ILogStringFormaterZZZ objFormater, Class classObj, String[] saLog) throws ExceptionZZZ {
		StringJustifierZZZ objJustifier = this.getStringJustifier();
		objFormater.setStringJustifier(objJustifier);
		return objFormater.compute(classObj, saLog);
	}
		
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
