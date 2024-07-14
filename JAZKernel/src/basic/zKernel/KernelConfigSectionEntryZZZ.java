package basic.zKernel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import basic.zBasic.AbstractObjectWithValueBufferedZZZ;
import basic.zBasic.AbstractObjectWithValueZZZ;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractList.HashMapMultiIndexedZZZ;
import basic.zBasic.util.abstractList.VectorExtendedDifferenceZZZ;
import basic.zBasic.util.crypt.code.ICryptZZZ;
import basic.zBasic.util.datatype.string.StringArrayZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.file.ini.IniFile;
import basic.zKernel.cache.ICachableObjectZZZ;
import basic.zKernel.config.KernelConfigSectionEntryUtilZZZ;

public class KernelConfigSectionEntryZZZ<T> extends AbstractObjectWithValueBufferedZZZ<T> implements IKernelConfigSectionEntryZZZ, ICachableObjectZZZ, Cloneable {
	private static final long serialVersionUID = -6413574962232912980L;
	private HashMapMultiIndexedZZZ<String,Boolean>hmSectionsSearched = new HashMapMultiIndexedZZZ<String,Boolean>();
	private String sSection = null;
	private String sProperty = null;
	private String sSystemNumber = null;
	private VectorExtendedDifferenceZZZ<String> vecRaw = new VectorExtendedDifferenceZZZ<String>();	
	private VectorExtendedDifferenceZZZ<String> vecValueAsExpression = new VectorExtendedDifferenceZZZ<String>();
	
	private VectorExtendedDifferenceZZZ<HashMap<String,String>> vechmValue = new VectorExtendedDifferenceZZZ<HashMap<String,String>>();	
	private VectorExtendedDifferenceZZZ<ArrayList<String>> vecalValue = new VectorExtendedDifferenceZZZ<ArrayList<String>>();
	private boolean bSectionExists = false;
	private boolean bAnySectionExists = false;
	private boolean bExpression = false;
	private boolean bFormula = false;
	private boolean bCrypt = false;
	
	private VectorExtendedDifferenceZZZ<String> vecValueFormulaSolvedAndConverted = new VectorExtendedDifferenceZZZ<String>();

	
	private boolean bConversion = false;
	private boolean bConverted = false;
	private VectorExtendedDifferenceZZZ<String> vecValueAsConversion = new VectorExtendedDifferenceZZZ<String>();
	
	
	
	private boolean bJson = false;
	private boolean bJsonArray = false;
	private boolean bJsonMap = false;
	
	private boolean bCall = false;
	private boolean bJavaCall = false;
	
	private String sCallingClassname = null;
	private String sCallingMethodname = null;
	private String sValueCallSolvedAsExpression = null;

	private ICryptZZZ objCrypt = null; //wichtig, falls z.B. ein verschluesselter Wert mit einem neuen Wert ersetzt werden soll. Dann muss das Algortithmus-Objekt nicht neu erstellt werden.
	
	private boolean bEncrypted = false;
	private boolean bRawEncrypted = false;
	private VectorExtendedDifferenceZZZ<String> vecRawEncrypted = new VectorExtendedDifferenceZZZ<String>();
	private VectorExtendedDifferenceZZZ<String> vecValueEncrypted = new VectorExtendedDifferenceZZZ<String>();
	private VectorExtendedDifferenceZZZ<String> vecValueDecrypted = new VectorExtendedDifferenceZZZ<String>();
	
	private boolean bDecrypted = false;
	private boolean bRawDecrypted = false;	
	private VectorExtendedDifferenceZZZ<String> vecRawDecrypted = new VectorExtendedDifferenceZZZ<String>();
	
	private String sKey = null;
	
	private boolean bExploded = false; //Falls es das Ergebnis einer Zerlegung eines Arrays ist
	private int iIndex = 0;            //dito
		
	private boolean bSkipCache = false;
	
	//#### Konstruktor ##########################
	public KernelConfigSectionEntryZZZ() {
		super();
	}
	
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
			if(!objEntry.hasAnyValue()) break main;
			if(!objEntry.isJsonMap()) break main;			
							
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
										
				objEntrytemp.isExploded(true);
				
				iCounter++; 
				objEntrytemp.setIndex(iCounter);

				objaReturn[iCounter] = objEntrytemp;
			}		
		}//end main:
		return objaReturn;
	}
	
	
	//#############################
	@Override
	public HashMapMultiIndexedZZZ<String,Boolean> getSectionsSearchedHashMap(){
		return this.hmSectionsSearched;
	}
	
	@Override 
	public void setSectionsSearchedHashMap(HashMapMultiIndexedZZZ<String,Boolean> hmSectionsSearched) {
		this.hmSectionsSearched = hmSectionsSearched;
	}
	
	@Override
	public String getSection() {
		return this.sSection;
	}

	@Override
	public void setSection(String sSection) throws ExceptionZZZ {
		//TODOGOON: in dem Entry-Object eine HashMap Unique Pflegen... hm(Section,bSectionExists)
		//          Das soll beim Debuggen helfen.
		
		
		//if(sSection!=null){
			this.sSection=sSection;
			
			//!!! wg. folgender Codezeile bedenken, dass erst der Section-Name gesetzt werden muss
			//    und dann erst der bSectionExists Wert. Sonst wird dieser naemlich wieder ueberschrieben.
			this.sectionExists(false);//es ist noch nicht bewiesen, dass es diese Section ueberhaupt gibt!!!
			
		//}else{
		//	this.sectionExists(false);
		//}
	}
	
	@Override
	public void setSection(String sSection, boolean bExists) throws ExceptionZZZ{
		if(sSection!=null) {
			this.setSection(sSection); //Beachte die Reihenfolge... erst Section setzen, dann ggfs. den true Wert
			this.sectionExists(bExists);
			this.getSectionsSearchedHashMap().putAsLast(sSection, bExists);
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
	public String getSystemNumber() {
		return this.sSystemNumber;
	}
	
	@Override
	public void setSystemNumber(String sSystemNumber) {
		this.sSystemNumber = sSystemNumber;
	}

	//###############################
	@Override 
	public VectorExtendedDifferenceZZZ<String> getRawVector(){
		return this.vecRaw;
	}
	
	@Override
	public String getRaw() {
		return this.getRawVector().getEntryLow();//anders als bei den anderen Strings und Vectoren hier den .Lows() zurueckgeben
	}

	@Override
	public void setRaw(String sRaw) {
		this.getRawVector().add(sRaw);
	}

	//####################################
	//Wg. Der Expression und Sectionbehandlung die einfachen getter/Setter überschreiben
	@Override
	public String getValue() {
		if(this.hasNullValue()){
			return null;		
		}else if (!this.hasAnyValue()){
			if(this.hasAnySectionExists()) {
				return new String(""); //also anders als beim definierten </NULL> -Objekt hier einen Leerstring zurückgeben. Ein Leerstring kann nämlich auch gewuenscht sein!				
			}else {
				return null; //wenn die Section nicht existiert, dann auch kein Wert.
			}
		}else {
			return this.getValueVector().getEntryHigh();
		}
	}

	@Override
	public void setValue(String sValue) {
		if(sValue!=null){
			this.getValueVector().add(sValue);
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
	
	//#################################
	@Override 
	public VectorExtendedDifferenceZZZ<String> getValueAsExpressionVector(){
		return this.vecValueAsExpression;
	}
	
	@Override
	public String getValueAsExpression() {
		return this.getValueAsExpressionVector().getEntryHigh();
	}
	
	@Override 
	public void setValueAsExpression(String sValueAsExpression) {
		this.setValueAsExpression_(sValueAsExpression, true);
	}
	
	@Override 
	public void setValueAsExpression(String sValueAsExpression, boolean bReflected) {
		this.setValueAsExpression_(sValueAsExpression, bReflected);
	}
	
	private void setValueAsExpression_(String sValueAsExpression, boolean bReflected) {
		if(bReflected) {
			String sValueAsExpressionNew = KernelConfigSectionEntryUtilZZZ.computeAsExpressionReflected(sValueAsExpression);
			this.getValueAsExpressionVector().add(sValueAsExpressionNew);
		}else {
			this.getValueAsExpressionVector().add(sValueAsExpression);
		}
	}
	
	
	//#####################################
	@Override 
	public VectorExtendedDifferenceZZZ<String> getValueAsConversionVector(){
		return this.vecValueAsConversion;
	}
	
	@Override
	public void setValueAsConversion(String sValueConverted) {
		this.getValueAsConversionVector().add(sValueConverted);
	}
	
	@Override 
	public String getValueAsConversion() {
		return this.getValueAsExpressionVector().getEntryHigh();
	}
	
	//###############################################
	@Override 
	public VectorExtendedDifferenceZZZ<String> getValueFormulaSolvedAndConvertedVector(){
		return this.vecValueFormulaSolvedAndConverted;
	}
	
	
	@Override 
	public String getValueFormulaSolvedAndConverted() {
		return this.getValueFormulaSolvedAndConvertedVector().getEntryHigh();
	}
	
	@Override
	public String getValueFormulaSolvedAndConvertedAsExpression() {
		String sValue = this.getValueFormulaSolvedAndConverted();
		return KernelConfigSectionEntryUtilZZZ.computeAsExpressionReflected(sValue);
	}
	
	@Override
	public void setValueFormulaSolvedAndConverted(String sValueSolvedAndConverted) {
		this.getValueFormulaSolvedAndConvertedVector().add(sValueSolvedAndConverted);
	}
	
	//################################################
	@Override 
	public VectorExtendedDifferenceZZZ<String> getValueEncryptedVector(){
		return this.vecValueEncrypted;
	}
	
	@Override
	public String getValueEncrypted() {
		return this.getValueEncryptedVector().getEntryHigh();
	}
	
	@Override
	public String getValueEncryptedAsExpression() {
		String sValue = this.getValueEncrypted();
		return KernelConfigSectionEntryUtilZZZ.computeAsExpressionReflected(sValue);
	}

	@Override
	public void setValueEncrypted(String sValueEncrypted) {
		this.getValueEncryptedVector().addElement(sValueEncrypted);
	}
	
	//############################################
	@Override 
	public VectorExtendedDifferenceZZZ<String> getValueDecryptedVector(){
		return this.vecValueDecrypted;
	}
	
	@Override
	public String getValueDecrypted() {
		return this.getValueEncryptedVector().getEntryHigh();
	}
	
	@Override
	public String getValueDecryptedAsExpression() {
		String sValue = this.getValueDecrypted();
		return KernelConfigSectionEntryUtilZZZ.computeAsExpressionReflected(sValue);
	}

	@Override
	public void setValueDecrypted(String sValueDecrypted) {
		this.getValueDecryptedVector().add(sValueDecrypted);
	}
	
	//###############################################
	@Override 
	public VectorExtendedDifferenceZZZ<HashMap<String,String>> getValueHashMapVector(){
		return this.vechmValue;
	}
	
	@Override
	public HashMap<String, String> getValueHashMap() {		
		if(this.hasNullValue()){
			return null;		
		}else if (!this.hasAnyValue()){
			if(this.hasAnySectionExists()) {
				return new HashMap<String,String>(); //also anders als beim definierten </NULL> -Objekt hier einen Leerstring zurückgeben. Ein Leerstring kann nämlich auch gewuenscht sein!				
			}else {
				return null; //wenn die Section nicht existiert, dann auch kein Wert.
			}
		}else {
			return this.getValueHashMapVector().getEntryHigh();
		}
	}

	@Override
	public void setValue(HashMap<String, String> hmValue) {
		this.getValueHashMapVector().add(hmValue);
	}

	//###############################################
	@Override 
	public VectorExtendedDifferenceZZZ<ArrayList<String>> getValueArrayListVector(){
		return this.vecalValue;
	}
	
	
	@Override
	public ArrayList<String> getValueArrayList() {		
		if(this.hasNullValue()){
			return null;		
		}else if (!this.hasAnyValue()){
			if(this.hasAnySectionExists()) {
				return new ArrayList<String>(); //also anders als beim definierten </NULL> -Objekt hier einen Leerstring zurückgeben. Ein Leerstring kann nämlich auch gewuenscht sein!				
			}else {
				return null; //wenn die Section nicht existiert, dann auch kein Wert.
			}
		}else {
			return this.getValueArrayListVector().getEntryHigh();
		}
	}


	@Override
	public void setValue(ArrayList<String> alValue) {
		this.getValueArrayListVector().add(alValue);
	}

	//##############################################
	
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
	public boolean isConversion() {
		return this.bConversion;
	}

	@Override
	public void isConversion(boolean bConversion) {
		this.bConversion = bConversion;
	}
	
	@Override 
	public void isConverted(boolean bConverted) {
		this.bConverted = bConverted;
	}
	
	@Override
	public boolean isConverted() {
		return this.bConverted; 
	}
	
	//#####################################
	
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
	public void sectionExists(boolean bSectionExists) throws ExceptionZZZ {
//      TODOGOON20240509: Eine der Stellen, an denen ein LogLevel=Debug sinnvoll waere.
		//                Ggfs. einbauen wenn dies alles auf die Verwendung von LogStringZZZ umgestellt wird.
//		if(bSectionExists) {
//			System.out.println(ReflectCodeZZZ.getPositionCurrent() + "Section Exists");
//		}
		this.bSectionExists = bSectionExists;
		this.hasAnySectionExists(bSectionExists);
	}
	
	@Override
	public boolean hasAnySectionExists() {
		return this.bAnySectionExists;
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
	public boolean isCrypt() {
		return this.bCrypt;		
	}
	
	@Override
	public void isCrypt(boolean bCrypt) {
		this.bCrypt = bCrypt;
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
	public VectorExtendedDifferenceZZZ<String> getRawDecryptedVector(){
		return this.vecRawDecrypted;
	}
	
	
	@Override
	public String getRawDecrypted() {
		return this.getRawVector().getEntryHigh();
	}


	@Override
	public void setRawDecrypted(String sRaw) {
		this.getRawVector().add(sRaw);
	}


	@Override
	public boolean isRawEncrypted() {
		return this.bRawEncrypted;
	}


	@Override
	public void isRawEncrypted(boolean bRawEncrypted) {
		this.bRawEncrypted = bRawEncrypted;
	}

	@Override
	public VectorExtendedDifferenceZZZ<String> getRawEncryptedVector(){
		return this.vecRawEncrypted;
	}
	
	@Override
	public String getRawEncrypted() {
		return this.getRawEncryptedVector().getEntryLow(); //!!! Das ist also der allererste RAW String, nicht ggfs. spaeter weiter aufgeloeste Strings.
	}
	
	@Override
	public String getRawEncryptedAsExpression() {
		String sValue = this.getRawEncrypted();
		return KernelConfigSectionEntryUtilZZZ.computeAsExpressionReflected(sValue);
	}


	@Override
	public void setRawEncrypted(String sRaw) {
		this.getRawEncryptedVector().add(sRaw);
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
	
	@Override
	public String getValueCallSolvedAsExpression() {
		return this.sValueCallSolvedAsExpression;
	}
	
	@Override
	public void setValueCallSolvedAsExpression(String sValueCallSolvedAsExpression) {
		this.setValueCallSolvedAsExpression_(sValueCallSolvedAsExpression, false);
	}
	
	@Override
	public void setValueCallSolvedAsExpression(String sValueCallSolvedAsExpression, boolean bEnforce) {
		this.setValueCallSolvedAsExpression_(sValueCallSolvedAsExpression, bEnforce);
	}
	
	private void setValueCallSolvedAsExpression_(String sValueCallSolvedAsExpression, boolean bEnforce) {
		if(bEnforce) {
			String sValueAsExpressionNew = KernelConfigSectionEntryUtilZZZ.computeAsExpressionReflected(sValueCallSolvedAsExpression);
			this.sValueCallSolvedAsExpression = sValueAsExpressionNew;
		}else {
			this.sValueCallSolvedAsExpression = sValueCallSolvedAsExpression;
		}
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
