package basic.zKernel;

import java.util.ArrayList;
import java.util.HashMap;
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
	private HashMap<String,String> hmValue = new HashMap<String,String>();
	private ArrayList<String> alValue = new ArrayList<String>();
	private boolean bSectionExists = false;
	private boolean bAnyValue = false;
	private boolean bNullValue = false;
	private boolean bExpression = false;
	private boolean bConverted = false;
	private boolean bFormula = false;
	private boolean bJson = false;
	private boolean bJsonArray = false;
	private boolean bJsonMap = false;
	private String sKey = null;
	
	private boolean bExploded = false; //Falls es das Ergebnis einer Zerlegung eines Arrays ist
	private int iIndex = 0;            //dito
		
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
					objEntrytemp.isExploded(true);
					objEntrytemp.setIndex(iCounter);
					objaReturn[iCounter] = objEntrytemp;
				}			
			}
		}//end main:
		return objaReturn;
	}
	
	
	public static IKernelConfigSectionEntryZZZ[] explodeJsonArray(IKernelConfigSectionEntryZZZ objEntry) throws CloneNotSupportedException {
		IKernelConfigSectionEntryZZZ[] objaReturn = {};//Mit diesem Trick wird ein leeres Array initialisiert, statt null;
		main:{
			if(objEntry==null) break main;
			if(!objEntry.hasAnyValue()) {
//				objaReturn = new IKernelConfigSectionEntryZZZ[1];
//				objaReturn[0] = objEntry;
				break main;
			}else {
				if(!objEntry.isJsonArray()) {
					break main;
				}else {
					ArrayList<String>als = objEntry.getValueArrayList();
					if(als==null)break main;
					if(als.isEmpty())break main;
					
					objaReturn = new IKernelConfigSectionEntryZZZ[als.size()];
					int iCounter=-1;
					
					//for(int iCounter=0;iCounter<=als.size()-1;iCounter++) {
					for(String stemp : als) {
						IKernelConfigSectionEntryZZZ objEntrytemp = objEntry.clone(); //damit werden alle Ursprungswerte übernommen.
						objEntrytemp.setValue(stemp);		//Merke: sRaw wird nicht zerlegt!!!	
						//objEntrytemp.isJson(false);       //Dementsprechend soll auch Ursprungstyp nicht geändert werden.
						//objEntrytemp.isJsonArray(false);
						iCounter++;
						objaReturn[iCounter] = objEntrytemp;
						objEntrytemp.isExploded(true);
						objEntrytemp.setIndex(iCounter);
					}
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
	public HashMap<String, String> getValueHashMap() {		
		return this.hmValue;
	}

	@Override
	public void setValue(HashMap<String, String> hmValue) {
		this.hmValue = hmValue;
	}


	@Override
	public ArrayList<String> getValueArrayList() {
		return this.alValue;
	}


	@Override
	public void setValue(ArrayList<String> alValue) {
		this.alValue = alValue;
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
	public boolean isConverted() {
		return this.bConverted;
	}

	@Override
	public void isConverted(boolean bConverted) {
		this.bConverted = bConverted;
	}
	
	@Override
	public boolean isJson(){
		return this.bJson;
	}
	
	@Override
	public void isJson(boolean bJson){
		this.bJson = bJson;
	}
	
	@Override
	public boolean isJsonArray(){
		return this.bJsonArray;
	}
	
	@Override
	public void isJsonArray(boolean bJsonArray){
		this.bJsonArray = bJsonArray;
	}
	
	@Override
	public boolean isJsonMap(){
		return this.bJsonMap;
	}
	
	@Override
	public void isJsonMap(boolean bJsonMap){
		this.bJsonMap = bJsonMap;
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


	@Override
	public boolean isExploded() {
		return this.bExploded;
	}

	@Override
	public void isExploded(boolean bIsExploded) {
		this.bExploded = bIsExploded;
	}

	@Override
	public int getIndex() {
		return this.iIndex;
	}

	@Override
	public void setIndex(int iIndex) {
		this.iIndex = iIndex;
	}

	@Override
	public String getKey() {
		return this.sKey;
	}

	@Override
	public void setKey(String sKey) {
		this.sKey = sKey;
	}
}
