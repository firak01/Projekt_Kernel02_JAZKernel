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
	//muss als Singleton static sein.
	protected static ILogStringFormatManagerZZZ objLogStringManagerINSTANCE; //muss als Singleton static sein


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
	public String compute(String sMessage, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		StringJustifierZZZ objJustifier = this.getStringJustifier();
		LogStringFormaterZZZ objFormater = new LogStringFormaterZZZ(objJustifier);
		return objFormater.compute(sMessage, ienumFormatLogString);
	}

	@Override
	public String compute(String sMessage, IEnumSetMappedLogStringFormatZZZ[] ienumaFormatLogString) throws ExceptionZZZ {
		StringJustifierZZZ objJustifier = this.getStringJustifier();
		LogStringFormaterZZZ objFormater = new LogStringFormaterZZZ(objJustifier);
		return objFormater.compute(sMessage, ienumaFormatLogString);
	}

	@Override
	public String compute(String[] saMessage, IEnumSetMappedLogStringFormatZZZ[] ienumaFormatLogString)	throws ExceptionZZZ {
		StringJustifierZZZ objJustifier = this.getStringJustifier();
		LogStringFormaterZZZ objFormater = new LogStringFormaterZZZ(objJustifier);
		return objFormater.compute(saMessage, ienumaFormatLogString);
	}

	@Override
	public String compute(Object obj, String sMessage, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		StringJustifierZZZ objJustifier = this.getStringJustifier();
		LogStringFormaterZZZ objFormater = new LogStringFormaterZZZ(objJustifier);
		return objFormater.compute(sMessage, ienumFormatLogString);
	}

	@Override
	public String compute(Object obj, String sMessage, IEnumSetMappedLogStringFormatZZZ[] ienumaFormatLogString) throws ExceptionZZZ {
		StringJustifierZZZ objJustifier = this.getStringJustifier();
		LogStringFormaterZZZ objFormater = new LogStringFormaterZZZ(objJustifier);
		return objFormater.compute(obj, sMessage, ienumaFormatLogString);
	}

	@Override
	public String compute(Object obj, String[] saLog, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		StringJustifierZZZ objJustifier = this.getStringJustifier();
		LogStringFormaterZZZ objFormater = new LogStringFormaterZZZ(objJustifier);
		return objFormater.compute(obj, saLog, ienumFormatLogString);
	}

	@Override
	public String compute(Object obj, String[] saLog, IEnumSetMappedLogStringFormatZZZ[] ienumFormatLogString) throws ExceptionZZZ {
		StringJustifierZZZ objJustifier = this.getStringJustifier();
		LogStringFormaterZZZ objFormater = new LogStringFormaterZZZ(objJustifier);
		return objFormater.compute(obj, saLog, ienumFormatLogString);
	}

	@Override
	public String compute(Object obj, String sMessage01, String sMessage02, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		StringJustifierZZZ objJustifier = this.getStringJustifier();
		LogStringFormaterZZZ objFormater = new LogStringFormaterZZZ(objJustifier);
		return objFormater.compute(obj, sMessage01, sMessage02, ienumFormatLogString);
	}

	@Override
	public String compute(Object obj, String sMessage01, String sMessage02, IEnumSetMappedLogStringFormatZZZ[] ienumaFormatLogString) throws ExceptionZZZ {
		StringJustifierZZZ objJustifier = this.getStringJustifier();
		LogStringFormaterZZZ objFormater = new LogStringFormaterZZZ(objJustifier);
		return objFormater.compute(obj, sMessage01, sMessage02, ienumaFormatLogString);
	}

	@Override
	public String compute(Class classObj, String sMessage, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		StringJustifierZZZ objJustifier = this.getStringJustifier();
		LogStringFormaterZZZ objFormater = new LogStringFormaterZZZ(objJustifier);
		return objFormater.compute(classObj, sMessage, ienumFormatLogString);
	}

	@Override
	public String compute(Class classObj, String sMessage, IEnumSetMappedLogStringFormatZZZ[] ienumaFormatLogString) throws ExceptionZZZ {
		StringJustifierZZZ objJustifier = this.getStringJustifier();
		LogStringFormaterZZZ objFormater = new LogStringFormaterZZZ(objJustifier);
		return objFormater.compute(classObj, sMessage, ienumaFormatLogString);
	}

	@Override
	public String compute(Class classObj, String[] saLog, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		StringJustifierZZZ objJustifier = this.getStringJustifier();
		LogStringFormaterZZZ objFormater = new LogStringFormaterZZZ(objJustifier);
		return objFormater.compute(classObj, saLog, ienumFormatLogString);
	}

	@Override
	public String compute(Class classObj, String[] saLog, IEnumSetMappedLogStringFormatZZZ[] ienumFormatLogString) throws ExceptionZZZ {
		StringJustifierZZZ objJustifier = this.getStringJustifier();
		LogStringFormaterZZZ objFormater = new LogStringFormaterZZZ(objJustifier);
		return objFormater.compute(classObj, saLog, ienumFormatLogString);
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
	public String compute(Object obj, String sMessage) throws ExceptionZZZ {
		StringJustifierZZZ objJustifier = this.getStringJustifier();
		LogStringFormaterZZZ objFormater = new LogStringFormaterZZZ(objJustifier);
		return objFormater.compute(obj, sMessage);
	}

	@Override
	public String compute(Object obj, String[] saMessage) throws ExceptionZZZ {
		StringJustifierZZZ objJustifier = this.getStringJustifier();
		LogStringFormaterZZZ objFormater = new LogStringFormaterZZZ(objJustifier);
		return objFormater.compute(obj, saMessage);
	}

	@Override
	public String compute(Object obj, String sMessage01, String sMessage02) throws ExceptionZZZ {
		StringJustifierZZZ objJustifier = this.getStringJustifier();
		LogStringFormaterZZZ objFormater = new LogStringFormaterZZZ(objJustifier);
		return objFormater.compute(obj, sMessage01, sMessage02);
	}

	@Override
	public String compute(Class classObj, String sMessage) throws ExceptionZZZ {
		StringJustifierZZZ objJustifier = this.getStringJustifier();
		LogStringFormaterZZZ objFormater = new LogStringFormaterZZZ(objJustifier);
		return objFormater.compute(classObj, sMessage);
	}

	@Override
	public String compute(Class classObj, String sMessage01, String sMessage02) throws ExceptionZZZ {
		StringJustifierZZZ objJustifier = this.getStringJustifier();
		LogStringFormaterZZZ objFormater = new LogStringFormaterZZZ(objJustifier);
		return objFormater.compute(classObj, sMessage01, sMessage02);
	}

	@Override
	public String compute(Class classObj, String[] saMessage) throws ExceptionZZZ {
		StringJustifierZZZ objJustifier = this.getStringJustifier();
		LogStringFormaterZZZ objFormater = new LogStringFormaterZZZ(objJustifier);
		return objFormater.compute(classObj, saMessage);
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
	public String compute(ILogStringFormaterZZZ objFormater, String sMessage, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		StringJustifierZZZ objJustifier = this.getStringJustifier();
		objFormater.setStringJustifier(objJustifier);
		return objFormater.compute(sMessage, ienumFormatLogString);
	}

	@Override
	public String compute(ILogStringFormaterZZZ objFormater, String sMessage, IEnumSetMappedLogStringFormatZZZ[] ienumaFormatLogString) throws ExceptionZZZ {
		StringJustifierZZZ objJustifier = this.getStringJustifier();
		objFormater.setStringJustifier(objJustifier);
		return objFormater.compute(sMessage, ienumaFormatLogString);
	}

	@Override
	public String compute(ILogStringFormaterZZZ objFormater, String[] saMessage, IEnumSetMappedLogStringFormatZZZ[] ienumaFormatLogString) throws ExceptionZZZ {
		StringJustifierZZZ objJustifier = this.getStringJustifier();
		objFormater.setStringJustifier(objJustifier);
		return objFormater.compute(saMessage, ienumaFormatLogString);
	}

	@Override
	public String compute(ILogStringFormaterZZZ objFormater, Object obj, String sMessage, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		StringJustifierZZZ objJustifier = this.getStringJustifier();
		objFormater.setStringJustifier(objJustifier);
		return objFormater.compute(obj, sMessage, ienumFormatLogString);
	}

	@Override
	public String compute(ILogStringFormaterZZZ objFormater, Object obj, String sMessage, IEnumSetMappedLogStringFormatZZZ[] ienumaFormatLogString) throws ExceptionZZZ {
		StringJustifierZZZ objJustifier = this.getStringJustifier();
		objFormater.setStringJustifier(objJustifier);
		return objFormater.compute(obj, sMessage, ienumaFormatLogString);
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
	public String compute(ILogStringFormaterZZZ objFormater, Object obj, String sMessage01, String sMessage02, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		StringJustifierZZZ objJustifier = this.getStringJustifier();
		objFormater.setStringJustifier(objJustifier);
		return objFormater.compute(obj, sMessage01, sMessage02, ienumFormatLogString);
	}

	@Override
	public String compute(ILogStringFormaterZZZ objFormater, Object obj, String sMessage01, String sMessage02, IEnumSetMappedLogStringFormatZZZ[] ienumaFormatLogString) throws ExceptionZZZ {
		StringJustifierZZZ objJustifier = this.getStringJustifier();
		objFormater.setStringJustifier(objJustifier);
		return objFormater.compute(obj, sMessage01, sMessage02, ienumaFormatLogString);
	}

	@Override
	public String compute(ILogStringFormaterZZZ objFormater, Class classObj, String sMessage, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		StringJustifierZZZ objJustifier = this.getStringJustifier();
		objFormater.setStringJustifier(objJustifier);
		return objFormater.compute(classObj, sMessage, ienumFormatLogString);
	}

	@Override
	public String compute(ILogStringFormaterZZZ objFormater, Class classObj, String sMessage, IEnumSetMappedLogStringFormatZZZ[] ienumaFormatLogString) throws ExceptionZZZ {
		StringJustifierZZZ objJustifier = this.getStringJustifier();
		objFormater.setStringJustifier(objJustifier);
		return objFormater.compute(classObj, sMessage, ienumaFormatLogString);
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
	public String compute(ILogStringFormaterZZZ objFormater, Object obj, String sMessage) throws ExceptionZZZ {
		StringJustifierZZZ objJustifier = this.getStringJustifier();
		objFormater.setStringJustifier(objJustifier);
		return objFormater.compute(obj, sMessage);
	}

	@Override
	public String compute(ILogStringFormaterZZZ objFormater, Object obj, String[] saMessage) throws ExceptionZZZ {
		StringJustifierZZZ objJustifier = this.getStringJustifier();
		objFormater.setStringJustifier(objJustifier);
		return objFormater.compute(obj, saMessage);
	}

	@Override
	public String compute(ILogStringFormaterZZZ objFormater, Object obj, String sMessage01, String sMessage02) throws ExceptionZZZ {
		StringJustifierZZZ objJustifier = this.getStringJustifier();
		objFormater.setStringJustifier(objJustifier);
		return objFormater.compute(obj, sMessage01, sMessage02);
	}

	@Override
	public String compute(ILogStringFormaterZZZ objFormater, Class classObj, String sMessage) throws ExceptionZZZ {
		StringJustifierZZZ objJustifier = this.getStringJustifier();
		objFormater.setStringJustifier(objJustifier);
		return objFormater.compute(classObj, sMessage);
	}

	@Override
	public String compute(ILogStringFormaterZZZ objFormater, Class classObj, String sMessage01, String sMessage02) throws ExceptionZZZ {
		StringJustifierZZZ objJustifier = this.getStringJustifier();
		objFormater.setStringJustifier(objJustifier);
		return objFormater.compute(classObj, sMessage01, sMessage02);
	}

	@Override
	public String compute(ILogStringFormaterZZZ objFormater, Class classObj, String[] saMessage) throws ExceptionZZZ {
		StringJustifierZZZ objJustifier = this.getStringJustifier();
		objFormater.setStringJustifier(objJustifier);
		return objFormater.compute(classObj, saMessage);
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
