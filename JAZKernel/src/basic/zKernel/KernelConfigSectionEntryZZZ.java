package basic.zKernel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.regex.Pattern;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractList.ArrayListZZZ;
import basic.zBasic.util.crypt.code.ICryptZZZ;
import basic.zBasic.util.datatype.string.StringArrayZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.file.ini.IniFile;
import basic.zKernel.cache.ICachableObjectZZZ;
import basic.zKernel.cache.ICacheFilterZZZ;
import basic.zKernel.file.ini.KernelZFormulaIni_EmptyZZZ;

public class KernelConfigSectionEntryZZZ implements IKernelConfigSectionEntryZZZ, ICachableObjectZZZ, Cloneable {
	private String sSection = null;
	private String sProperty = null;
	private String sSystemNumber = null;
	private String sRaw = null;
	private String sValue = new String("");
	private HashMap<String,String> hmValue = new HashMap<String,String>();
	private ArrayList<String> alValue = new ArrayList<String>();
	private boolean bSectionExists = false;
	private boolean bAnySectionExists = false;
	private boolean bAnyValue = false;
	private boolean bNullValue = false;
	private boolean bExpression = false;
	private boolean bConverted = false;
	private boolean bFormula = false;
	
	private boolean bJson = false;
	private boolean bJsonArray = false;
	private boolean bJsonMap = false;
	
	private boolean bCall = false;
	private boolean bJavaCall = false;
	
	private String sCallingClassname = null;
	private String sCallingMethodname = null;

	private ICryptZZZ objCrypt = null; //wichtig, falls z.B. ein verschluesselter Wert mit einem neuen Wert ersetzt werden soll. Dann muss das Algortithmus-Objekt nicht neu erstellt werden.
	
	private boolean bEncrypted = false;
	private boolean bRawEncrypted = false;
	private String sRawEncrypted = null;
	private String sValueEncrypted=new String("");
	private String sValueDecrypted=new String("");
	
	private boolean bDecrypted = false;
	private boolean bRawDecrypted = false;	
	private String sRawDecrypted = null;
	
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
						
						objEntrytemp.isExploded(true);
						
						iCounter++;
						objEntrytemp.setIndex(iCounter);
						
						objaReturn[iCounter] = objEntrytemp;
					}
				}			
			}
		}//end main:
		return objaReturn;
	}
	
	public static IKernelConfigSectionEntryZZZ[] explodeJsonMap(IKernelConfigSectionEntryZZZ objEntry) throws CloneNotSupportedException {
		IKernelConfigSectionEntryZZZ[] objaReturn = {};//Mit diesem Trick wird ein leeres Array initialisiert, statt null;
		main:{
			if(objEntry==null) break main;
			if(!objEntry.hasAnyValue()) {
//				objaReturn = new IKernelConfigSectionEntryZZZ[1];
//				objaReturn[0] = objEntry;
				break main;
			}else {
				if(!objEntry.isJsonMap()) {
					break main;
				}else {
					
					//20210812 jetzt auch für die Map clonen UND DABEI DEN ALIAS-WERT SETZEN
					HashMap<String,String>hmss = objEntry.getValueHashMap();
					if(hmss==null)break main;
					if(hmss.isEmpty())break main;
					
					objaReturn = new IKernelConfigSectionEntryZZZ[hmss.size()];
					int iCounter=-1;
					
					Set<String> setKey = hmss.keySet();
					Iterator<String>itKey = setKey.iterator();
					while(itKey.hasNext()) {
						IKernelConfigSectionEntryZZZ objEntrytemp = objEntry.clone(); //damit werden alle Ursprungswerte übernommen.
						
						String sKey = itKey.next();
						String sValue = hmss.get(sKey);
						
						objEntrytemp.setKey(sKey);        //Speziell für die HashMap						
						objEntrytemp.setValue(sValue);		//Merke: sRaw wird nicht zerlegt!!!	
						//objEntrytemp.isJson(false);       //Dementsprechend soll auch Ursprungstyp nicht geändert werden.
						//objEntrytemp.isJsonArray(false);
												
						objEntrytemp.isExploded(true);
						
						iCounter++; 
						objEntrytemp.setIndex(iCounter);
		
						objaReturn[iCounter] = objEntrytemp;
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
		//if(sSection!=null){
			this.sSection=sSection;
			this.sectionExists(false);//es ist noch nicht bewiesen, dass es diese Section ueberhaupt gibt!!!
		//}else{
		//	this.sectionExists(false);
		//}
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
	public String getSystemNumber() {
		return this.sSystemNumber;
	}
	
	@Override
	public void setSystemNumber(String sSystemNumber) {
		this.sSystemNumber = sSystemNumber;
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
		}else if (!this.hasAnyValue()){
			//if(this.sectionExists()) {
			if(this.hasAnySectionExists()) {
				//return KernelZFormulaIni_EmptyZZZ.getExpressionTagEmpty();
				return new String(""); //also anders als beim definierten </NULL> -Objekt hier einen Leerstring zurückgeben. Ein Leerstring kann nämlich auch gewuenscht sein!				
			}else {
				return null; //wenn die Section nicht existiert, dann auch kein Wert.
			}
		}else {
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
	public String getValueEncrypted() {
		return this.sValueEncrypted;
	}

	@Override
	public void setValueEncrypted(String sValueEncrypted) {
		this.sValueEncrypted = sValueEncrypted;
	}
	
	@Override
	public String getValueDecrypted() {
		return this.sValueDecrypted;
	}

	@Override
	public void setValueDecrypted(String sValueDecrypted) {
		this.sValueDecrypted = sValueDecrypted;
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
	
	@Override
	public boolean hasNullValue() {
		return this.bNullValue;
	}
	//Wird beim Setzen des Werts automatisch mit gesetzt. Also nicht "von aussen" setzbar.
	private void hasNullValue(boolean bNullValue) {
		this.bNullValue=bNullValue;
	}
	
	//Wird beim Setzen des Werts automatisch mit gesetzt. Also nicht "von aussen" setzbar.
	//Wurde einmal true gesetzt, dann bleibt das auch so. Damit wird bei der nächsten Suche nach einer Section der Wert nicht verändet, auch wenn die neue Section nicht existiert!!!
	private void hasAnySectionExists(boolean bAnySectionExists) {
		if(!this.bAnySectionExists) this.bAnySectionExists=bAnySectionExists;
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
	public boolean isCall() {
		return this.bCall;
	}
	
	@Override
	public void isCall(boolean bCall) {
		this.bCall = true;
	}
	
	@Override
	public boolean isJavaCall() {
		return this.bJavaCall;
	}
	
	@Override
	public void isJavaCall(boolean bJavaCall) {
		this.bJavaCall = bJavaCall;
	}
	
	@Override
	public boolean sectionExists() {
		return this.bSectionExists;
	}

	@Override
	public void sectionExists(boolean bSectionExists) {
		this.bSectionExists = bSectionExists;
		this.hasAnySectionExists(bSectionExists);
	}
	
	@Override
	public boolean hasAnySectionExists() {
		return this.bAnySectionExists;
	}
	//Wird beim Setzen des Werts automatisch mit gesetzt. Also nicht "von aussen" setzbar.
	private void hasAnyValue(boolean bAnyValue) {
		this.bAnyValue=bAnyValue;
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


	@Override
	public boolean isDecrypted() {
		return this.bDecrypted;
	}


	@Override
	public void isDecrypted(boolean bDecrypted) {
		this.bDecrypted = bDecrypted;
	}
	
	
	@Override
	public boolean isEncrypted() {
		return this.bEncrypted;
	}


	@Override
	public void isEncrypted(boolean bEncrypted) {
		this.bEncrypted = bEncrypted;
	}
	
	@Override
	public void isRawDecrypted(boolean bRawDecrypted) {
		this.bRawDecrypted = bRawDecrypted;
	}
	
	@Override
	public boolean isRawDecrypted() {
		return this.bRawDecrypted;
	}
	
	@Override
	public String getRawDecrypted() {
		return this.sRawDecrypted;
	}


	@Override
	public void setRawDecrypted(String sRaw) {
		this.sRawDecrypted = sRaw;
	}


	@Override
	public boolean isRawEncrypted() {
		return this.bRawEncrypted;
	}


	@Override
	public void isRawEncrpyted(boolean bRawEncrypted) {
		this.bRawEncrypted = bRawEncrypted;
	}

	@Override
	public String getRawEncrypted() {
		return this.sRawEncrypted;
	}


	@Override
	public void setRawEncrypted(String sRaw) {
		this.sRawEncrypted = sRaw;
	}

	
	//### Java Methoden Aufruf in ini-Datei konfigurieren
	@Override
	public String getCallingClassname() {
		return this.sCallingClassname;
	}

	@Override
	public void setCallingClassname(String sJavaCallingClassName) {
		this.sCallingClassname = sJavaCallingClassName;
	}

	@Override
	public String getCallingMethodname() {
		return this.sCallingMethodname;
	}

	@Override
	public void setCallingMethodname(String sJavaCallingMethodName) {
		this.sCallingMethodname = sJavaCallingMethodName;
	}
	

	//### Aus Interface ICryptZZZ
	@Override
	public ICryptZZZ getCryptAlgorithmType() throws ExceptionZZZ {
		return this.objCrypt;
	}


	@Override
	public void setCryptAlgorithmType(ICryptZZZ objCrypt) {
		this.objCrypt = objCrypt;
	}
}
