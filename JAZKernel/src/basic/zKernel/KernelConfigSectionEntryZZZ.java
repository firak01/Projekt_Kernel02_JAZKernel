package basic.zKernel;

import java.util.ArrayList;
import java.util.regex.Pattern;

import basic.zBasic.util.abstractList.ArrayListZZZ;
import basic.zBasic.util.datatype.string.StringArrayZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.file.ini.IniFile;
import basic.zKernel.cache.ICachableObjectZZZ;
import basic.zKernel.cache.ICacheFilterZZZ;

public class KernelConfigSectionEntryZZZ implements IKernelConfigSectionEntryZZZ, ICachableObjectZZZ, Cloneable {
	private String sSection = null;
	private String sProperty = null;
	private String sRaw = null;
	private String sValue = new String("");
	private boolean bSectionExists = false;
	private boolean bAnyValue = false;
	private boolean bNullValue = false;
	private boolean bExpression = false;
	private boolean bFormula = false;
	
	private boolean bSkipCache = false;
	
	//############################
	public static IKernelConfigSectionEntryZZZ[] explode(IKernelConfigSectionEntryZZZ objEntry, String sDelimiterIn) throws CloneNotSupportedException {
		IKernelConfigSectionEntryZZZ[] objaReturn = null;
		main:{
			if(objEntry==null) break main;
			if(!objEntry.hasAnyValue()) {
				objaReturn = new IKernelConfigSectionEntryZZZ[1];
				objaReturn[0] = objEntry;
			}else {
				String sDelimiter;
				if(StringZZZ.isEmpty(sDelimiterIn)) {
					sDelimiter = IniFile.sINI_MULTIVALUE_SEPARATOR;
				}else {
					sDelimiter = sDelimiterIn;
					}
					
				String sValue = objEntry.getValue();
				String[] saValue = StringZZZ.explode(sValue, sDelimiter);
				if(StringArrayZZZ.isEmpty(saValue))break main;
				
				objaReturn = new IKernelConfigSectionEntryZZZ[saValue.length];
				for(int iCounter = 0; iCounter<=saValue.length-1;iCounter++) {
					IKernelConfigSectionEntryZZZ objEntrytemp = objEntry.clone();
					objEntrytemp.setValue(saValue[iCounter]);		//Merke: sRaw wird nicht zerlegt!!!	
					objaReturn[iCounter] = objEntrytemp;
				}			
			}
		}//end main:
		return objaReturn;
	}
	
	
	//#############################
	@Override
	public String getSection() {
		return this.sSection;
	}

	@Override
	public void setSection(String sSection) {
		if(sSection!=null){
			this.sSection=sSection;
			this.sectionExists(true);
		}else{
			this.sectionExists(false);
		}
	}

	@Override
	public String getProperty() {
		return this.sProperty;
	}

	@Override
	public void setProperty(String sProperty) {
		this.sProperty=sProperty;
	}

	@Override
	public String getRaw() {
		return this.sRaw;
	}

	@Override
	public void setRaw(String sRaw) {
		this.sRaw=sRaw;
	}

	@Override
	public String getValue() {
		if(this.hasNullValue()){
			return null;
		}else{
			return this.sValue;
		}
	}

	@Override
	public void setValue(String sValue) {
		if(sValue!=null){
			this.sValue = sValue;
			this.hasAnyValue(true);
			this.hasNullValue(false);
		}else{
			//WENN NULL als Ergebnis einer Expression/Formel herauskommt, dann IST DAS DER WERT
			if(this.isExpression() || this.isFormula()){
				this.hasAnyValue(true);
				this.hasNullValue(true);
			}else{
				this.hasAnyValue(false);
				this.hasNullValue(false);
			}
		}
	}

	@Override
	public boolean hasAnyValue() {
		return this.bAnyValue;
	}
	//Wird beim Setzen des Werts automatisch mit gesetzt. Also nicht "von aussen" setzbar.
	private void hasAnyValue(boolean bAnyValue) {
		this.bAnyValue=bAnyValue;
	}
	
	
	@Override
	public boolean hasNullValue() {
		return this.bNullValue;
	}
	//Wird beim Setzen des Werts automatisch mit gesetzt. Also nicht "von aussen" setzbar.
		private void hasNullValue(boolean bNullValue) {
			this.bNullValue=bNullValue;
		}
	
	@Override
	public boolean isExpression(){
		return this.bExpression;
	}
	
	@Override
	public void isExpression(boolean bExpression){
		this.bExpression = bExpression;
	}

	@Override
	public boolean isFormula() {
		return this.bFormula;
	}

	@Override
	public void isFormula(boolean bFormula) {
		this.bFormula = bFormula;
	}
	
	@Override
	public boolean sectionExists() {
		return this.bSectionExists;
	}

	@Override
	public void sectionExists(boolean bSectionExists) {
		this.bSectionExists = bSectionExists;
	}

	//Aus Interface IObjectCachableZZZ
	@Override
	public boolean isCacheSkipped() {
		return this.bSkipCache;
	}

	@Override
	public void isCacheSkipped(boolean bSkip) {
		this.bSkipCache = bSkip;
	}

	@Override
	public String getValueForFilter() {
		if(this.isFormula()){
			return this.getRaw();
		}else{
			return this.getValue();
		}
	}

	@Override
	public boolean wasValueComputed() {
		if(this.isFormula()){
			return true;
		}else{
			return false;
		}
	}
	
	//Interface clonable
	//s. https://howtodoinjava.com/java/cloning/a-guide-to-object-cloning-in-java/
	 @Override
	public IKernelConfigSectionEntryZZZ clone() throws CloneNotSupportedException {
	        return (IKernelConfigSectionEntryZZZ) super.clone();
	    }
	

	
	
}
