package basic.zKernel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import basic.zBasic.AbstractObjectWithValueBufferedZZZ;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractList.ArrayListUtilZZZ;
import basic.zBasic.util.abstractList.HashMapMultiIndexedZZZ;
import basic.zBasic.util.abstractList.HashMapMultiZZZ;
import basic.zBasic.util.abstractList.HashMapUtilZZZ;
import basic.zBasic.util.abstractList.VectorDifferenceZZZ;
import basic.zBasic.util.crypt.code.ICryptZZZ;
import basic.zBasic.util.datatype.string.StringArrayZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.file.ini.IIniStructureConstantZZZ;
import basic.zBasic.util.file.ini.IIniStructurePositionZZZ;
import basic.zBasic.util.file.ini.IniStructurePositionZZZ;
import basic.zKernel.cache.ICachableObjectZZZ;
import basic.zKernel.config.KernelConfigSectionEntryUtilZZZ;
import basic.zKernel.file.ini.IIniTagBasicZZZ;

public class KernelConfigSectionEntryZZZ<T> extends AbstractObjectWithValueBufferedZZZ<T> implements IKernelConfigSectionEntryZZZ, ICachableObjectZZZ, Cloneable {
	private static final long serialVersionUID = -6413574962232912980L;
	protected HashMapMultiIndexedZZZ<String,Boolean>hmSectionsSearched = null;
	protected HashMapMultiIndexedZZZ<String,Boolean>hmPropertiesSearched = null;
	protected HashMapMultiZZZ<String,String>hmPropertiesSection = null;
	
	protected IIniStructurePositionZZZ objIniPosition = null;
	protected String sSystemNumber = null;
	protected VectorDifferenceZZZ<String> vecRaw = new VectorDifferenceZZZ<String>();	
	protected VectorDifferenceZZZ<String> vecValueAsExpression = new VectorDifferenceZZZ<String>();
	
	protected VectorDifferenceZZZ<HashMap<String,String>> vechmValue = new VectorDifferenceZZZ<HashMap<String,String>>();	
	protected VectorDifferenceZZZ<ArrayList<String>> vecalValue = new VectorDifferenceZZZ<ArrayList<String>>();
	protected boolean bSectionExists = false;
	protected boolean bAnySectionExists = false;
	protected boolean bPropertyExists = false;
	protected boolean bAnyPropertyExists = false;
	protected boolean bExpression = false;
	
	protected boolean bFormula = false;
	protected boolean bFormulaSolved = false;
	
	protected boolean bCrypt = false;
	
	protected VectorDifferenceZZZ<String> vecValueFormulaSolvedAndConverted = new VectorDifferenceZZZ<String>();

	
	protected boolean bParseCalled = false;
	protected boolean bParsed = false;
	protected boolean bParsedChanged = false;
	
	protected boolean bSubstituteCalled = false;
	protected boolean bSubstitutedChanged = false;
	protected boolean bPathSubstituted = false;
	protected boolean bPathSubstitutedChanged = false;
	protected boolean bVariableSubstituted = false;
	protected boolean bVariableSubstitutedChanged = false;
	
	
	protected boolean bSolveCalled = false;
	protected boolean bSolved = false;
	protected boolean bSolvedChanged = false;
	
	protected boolean bFormulaMathSolved = false;
	
	protected boolean bConversion = false;
	protected boolean bConverted = false;
	protected VectorDifferenceZZZ<String> vecValueAsConversion = new VectorDifferenceZZZ<String>();
	
	
	
	protected boolean bJson = false;
	protected boolean bJsonArray = false;
	protected boolean bJsonMap = false;
	
	protected boolean bCall = false;
	protected boolean bCallSolved = false;
	protected boolean bJavaCall = false;
	
	
	protected String sCallingClassname = null;
	protected String sCallingMethodname = null;
	protected String sValueCallSolved = null;
	protected String sValueCallSolvedAsExpression = null;

	protected ICryptZZZ objCrypt = null; //wichtig, falls z.B. ein verschluesselter Wert mit einem neuen Wert ersetzt werden soll. Dann muss das Algortithmus-Objekt nicht neu erstellt werden.
	
	protected boolean bEncrypted = false;
	protected boolean bRawEncrypted = false;
	protected VectorDifferenceZZZ<String> vecRawEncrypted = new VectorDifferenceZZZ<String>();
	protected VectorDifferenceZZZ<String> vecValueEncrypted = new VectorDifferenceZZZ<String>();
	protected VectorDifferenceZZZ<String> vecValueEncryptedPart = new VectorDifferenceZZZ<String>();
	protected VectorDifferenceZZZ<String> vecValueDecrypted = new VectorDifferenceZZZ<String>();
	protected VectorDifferenceZZZ<String> vecValueDecryptedPart = new VectorDifferenceZZZ<String>();
	
	protected boolean bDecrypted = false;
	protected boolean bRawDecrypted = false;	
	protected VectorDifferenceZZZ<String> vecRawDecrypted = new VectorDifferenceZZZ<String>();
	
	protected String sKey = null;
	
	protected boolean bArrayValue = false; //Falls eine ArrayList gesetzt wurde.
	protected boolean bMapValue = false;   //Falls eine HashMap gesetzt wurde.
	protected boolean bExploded = false; //Falls es das Ergebnis einer Zerlegung eines Arrays ist
	protected int iIndex = 0;            //dito
		
	protected boolean bSkipCache = false;
	
	
	//#### Konstruktor ##########################
	public KernelConfigSectionEntryZZZ() throws ExceptionZZZ{
		super();
		KernelConfigSectionEntryNew_(null);
	}
	
	public KernelConfigSectionEntryZZZ(IIniTagBasicZZZ objTag) throws ExceptionZZZ{
		super();
		KernelConfigSectionEntryNew_(objTag);
	}
	
	private boolean KernelConfigSectionEntryNew_(IIniTagBasicZZZ objTag) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			//Kopiere die Werte aus objTag hierher
			if(objTag==null) break main;
			
			this.setIniStructurePostion(objTag.getIniStructurePosition()); //Section, Property
			
			//OVERFLOW SCHLEIFENGEFAHR, wg. Ruekursion !!!
			//Wenn in dem objTag das KenelConfigSectionEntry-Objekt neu erstellt werde muss 
			//und dabei auch das Tag übergeben wird (mit "this");
			//Darum in diesem Tag beim Holen der Werte ueber as KernelConfigSectionEntry-Objekt
			//unbedingt immer auf objEntry==null abprüfen und dann ggfs. auch null oder false zurückliefern.
			
			String sFromTag = objTag.getValue();
			if(sFromTag!=null) {
				this.setValue(sFromTag);
			}
			
			
			ArrayList<String>alFromTag = objTag.getValueArrayList();
			if(alFromTag!=null) {
				this.setValue(alFromTag);
			}
			
			//Das geht mit IIniTagBasicZZZ nicht....
			//this.setValueAsExpression(objTag.getValueAsExpression());
			
			this.setSection(objTag.getSection());			
			this.setProperty(objTag.getProperty());
			bReturn = true;
		}//end main:
		return bReturn;
	}
	
	//############################
	public static IKernelConfigSectionEntryZZZ[] explode(IKernelConfigSectionEntryZZZ objEntry, String sDelimiterIn) throws CloneNotSupportedException, ExceptionZZZ {
		IKernelConfigSectionEntryZZZ[] objaReturn = null;
		main:{
			if(objEntry==null) break main;
			if(!objEntry.hasAnyValue()) {
				objaReturn = new IKernelConfigSectionEntryZZZ[1];
				objaReturn[0] = objEntry;
			}else {
				String sDelimiter;
				if(StringZZZ.isEmpty(sDelimiterIn)) {
					sDelimiter = IIniStructureConstantZZZ.sINI_MULTIVALUE_SEPARATOR;
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
	
	
	public static IKernelConfigSectionEntryZZZ[] explodeJsonArray(IKernelConfigSectionEntryZZZ objEntry) throws CloneNotSupportedException, ExceptionZZZ {
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
	
	public static IKernelConfigSectionEntryZZZ[] explodeJsonMap(IKernelConfigSectionEntryZZZ objEntry) throws CloneNotSupportedException, ExceptionZZZ {
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
	
	//### Aus IKernelConfigSectionEntryZZZ
	@Override
	public HashMapMultiIndexedZZZ<String,Boolean> getSectionsSearchedHashMap(){
		if(this.hmSectionsSearched==null) {
			this.hmSectionsSearched = new HashMapMultiIndexedZZZ<String,Boolean>();
		}
		return this.hmSectionsSearched;
	}
	
	@Override 
	public void setSectionsSearchedHashMap(HashMapMultiIndexedZZZ<String,Boolean> hmSectionsSearched) {
		this.hmSectionsSearched = hmSectionsSearched;
	}
	
	@Override
	public HashMapMultiIndexedZZZ<String,Boolean> getPropertiesSearchedHashMap(){
		if(this.hmPropertiesSearched==null) {
			this.hmPropertiesSearched = new HashMapMultiIndexedZZZ<String,Boolean>();
		}
		return this.hmPropertiesSearched;
	}
	
	@Override 
	public void setPropertiesSearchedHashMap(HashMapMultiIndexedZZZ<String,Boolean> hmPropertiesSearched) {
		this.hmPropertiesSearched = hmPropertiesSearched;
	}
	
	@Override
	public HashMapMultiZZZ<String,String> getPropertiesSectionHashMap(){
		if(this.hmPropertiesSection==null) {
			this.hmPropertiesSection = new HashMapMultiIndexedZZZ<String,String>();
		}
		return this.hmPropertiesSection;
	}
	
	@Override 
	public void setPropertiesSectionHashMap(HashMapMultiZZZ<String,String> hmPropertiesSection) {
		this.hmPropertiesSection = hmPropertiesSection;
	}
	
	//###########################################
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
	public VectorDifferenceZZZ<String> getRawVector(){
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
	public VectorDifferenceZZZ<String> getValueAsExpressionVector(){
		return this.vecValueAsExpression;
	}
	
	@Override
	public String getValueAsExpression() {
		return this.getValueAsExpressionVector().getEntryHigh();
	}
	
	@Override 
	public void setValueAsExpression(String sValueAsExpression) throws ExceptionZZZ {
		this.setValueAsExpression_(sValueAsExpression, true);
	}
	
	@Override 
	public void setValueAsExpression(String sValueAsExpression, boolean bReflected) throws ExceptionZZZ {
		this.setValueAsExpression_(sValueAsExpression, bReflected);
	}
	
	private void setValueAsExpression_(String sValueAsExpression, boolean bReflected) throws ExceptionZZZ {
		if(bReflected) {
			String sValueAsExpressionNew = KernelConfigSectionEntryUtilZZZ.computeAsExpressionReflected(sValueAsExpression);
			this.getValueAsExpressionVector().add(sValueAsExpressionNew);
		}else {
			this.getValueAsExpressionVector().add(sValueAsExpression);
		}
	}
	
	
	//#####################################
	@Override 
	public VectorDifferenceZZZ<String> getValueAsConversionVector(){
		return this.vecValueAsConversion;
	}
	
	public void setValueAsConversionVector(VectorDifferenceZZZ<String> vecValueConversion) {
		this.vecValueAsConversion = vecValueConversion;
	}
	
	@Override
	public void setValueAsConversion(String sValueConverted) {
		this.getValueAsConversionVector().add(sValueConverted);
	}
	
	@Override 
	public String getValueAsConversion() {
		return this.getValueAsConversionVector().getEntryHigh();
	}
	
	//###############################################
	@Override 
	public VectorDifferenceZZZ<String> getValueFormulaSolvedAndConvertedVector(){
		return this.vecValueFormulaSolvedAndConverted;
	}
	
	@Override
	public void setValueFormulaSolvedAndConvertedVector(VectorDifferenceZZZ<String> vecValueSolvedAndConverted) {
		this.vecValueFormulaSolvedAndConverted = vecValueSolvedAndConverted;
	}
	
	@Override 
	public String getValueFormulaSolvedAndConverted() {
		return this.getValueFormulaSolvedAndConvertedVector().getEntryHigh();
	}
	
	@Override
	public void setValueFormulaSolvedAndConverted(String sValueSolvedAndConverted) {
		this.getValueFormulaSolvedAndConvertedVector().add(sValueSolvedAndConverted);
	}
	
	@Override
	public String getValueFormulaSolvedAndConvertedAsExpression() throws ExceptionZZZ {
		String sValue = this.getValueFormulaSolvedAndConverted();
		return KernelConfigSectionEntryUtilZZZ.computeAsExpressionReflected(sValue);
	}
	
	
	
	//################################################
	@Override 
	public VectorDifferenceZZZ<String> getValueEncryptedVector(){
		return this.vecValueEncrypted;
	}
	
	@Override
	public void setValueEncryptedVector(VectorDifferenceZZZ<String> vecValueEncrypted) {
		this.vecValueEncrypted = vecValueEncrypted;
	}
	
	@Override
	public String getValueEncrypted() {
		return this.getValueEncryptedVector().getEntryHigh();
	}
	
	@Override
	public String getValueEncryptedAsExpression() throws ExceptionZZZ {
		String sValue = this.getValueEncrypted();
		return KernelConfigSectionEntryUtilZZZ.computeAsExpressionReflected(sValue);
	}

	@Override
	public void setValueEncrypted(String sValueEncrypted) {
		this.getValueEncryptedVector().addElement(sValueEncrypted);
	}
	
	//################################################
	@Override 
	public VectorDifferenceZZZ<String> getValueEncryptedPartVector(){
		return this.vecValueEncryptedPart;
	}
	
	@Override
	public void setValueEncryptedPartVector(VectorDifferenceZZZ<String> vecValueEncryptedPart) {
		this.vecValueEncryptedPart = vecValueEncryptedPart;
	}
	
	@Override
	public String getValueEncryptedPart() {
		return this.getValueEncryptedPartVector().getEntryHigh();
	}
	
	@Override
	public void setValueEncryptedPart(String sValueEncrypted) {
		this.getValueEncryptedPartVector().addElement(sValueEncrypted);
	}
	
	//############################################
	@Override 
	public VectorDifferenceZZZ<String> getValueDecryptedVector(){
		return this.vecValueDecrypted;
	}
	
	@Override
	public void setValueDecryptedVector(VectorDifferenceZZZ<String> vecValueDecrypted) {
		this.vecValueDecrypted = vecValueDecrypted;
	}
	
	@Override
	public String getValueDecrypted() {
		return this.getValueDecryptedVector().getEntryHigh();
	}
	
	@Override
	public String getValueDecryptedAsExpression() throws ExceptionZZZ {
		String sValue = this.getValueDecrypted();
		return KernelConfigSectionEntryUtilZZZ.computeAsExpressionReflected(sValue);
	}

	@Override
	public void setValueDecrypted(String sValueDecrypted) {
		this.getValueDecryptedVector().add(sValueDecrypted);
	}
	
	//################################################
	@Override 
	public VectorDifferenceZZZ<String> getValueDecryptedPartVector(){
		return this.vecValueDecryptedPart;
	}
	
	@Override
	public void setValueDecryptedPartVector(VectorDifferenceZZZ<String> vecValueDecryptedPart) {
		this.vecValueDecryptedPart = vecValueDecryptedPart;
	}
	
	@Override
	public String getValueDecryptedPart() {
		return this.getValueDecryptedPartVector().getEntryHigh();
	}
	
	@Override
	public void setValueDecryptedPart(String sValueDecrypted) {
		this.getValueDecryptedPartVector().addElement(sValueDecrypted);
	}
	
	//###############################################
	@Override 
	public VectorDifferenceZZZ<HashMap<String,String>> getValueHashMapVector(){
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
		if(!HashMapUtilZZZ.isEmpty(hmValue)) {				
			this.isMapValue(true);
			this.setValue(hmValue.toString());
		}else {
			this.isMapValue(false);
			this.setValue((String)null);
		}
	}

	//###############################################
	//### aus IValueArrayUserZZZ
	@Override 
	public VectorDifferenceZZZ<ArrayList<String>> getValueArrayListVector(){
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
		if(!ArrayListUtilZZZ.isEmpty(alValue)) {				
			this.isArrayValue(true);
			if(alValue!=null) this.setValue(alValue.toString());
		}else {
			this.isArrayValue(false);
			this.setValue((String)null);
		}
		
	}
	
	@Override
	public boolean isArrayValue() {
		return this.bArrayValue;
	}
	
	@Override 
	public void isArrayValue(boolean bIsArrayValue) {
		this.bArrayValue = bIsArrayValue;
	}
	
	
	@Override
	public boolean isMapValue() {
		return this.bMapValue;
	}
	
	@Override 
	public void isMapValue(boolean bIsMapValue) {
		this.bMapValue = bIsMapValue;
	}

	//##############################################
	
	@Override
	public boolean hasAnySectionExists() {
		return this.bAnySectionExists;
	}
	
	//Wird beim Setzen des Werts automatisch mit gesetzt. Also nicht "von aussen" setzbar.
	//Wurde einmal true gesetzt, dann bleibt das auch so. Damit wird bei der nächsten Suche nach einer Section der Wert nicht verändet, auch wenn die neue Section nicht existiert!!!
	public void hasAnySectionExists(boolean bAnySectionExists) {
		if(!this.bAnySectionExists) this.bAnySectionExists=bAnySectionExists;
	}
		
	@Override
	public boolean hasAnyPropertyExists() {
		return this.bAnyPropertyExists;
	}
	
	//Wird beim Setzen des Werts automatisch mit gesetzt. Also nicht "von aussen" setzbar.
	//Wurde einmal true gesetzt, dann bleibt das auch so. Damit wird bei der nächsten Suche nach einer Section der Wert nicht verändet, auch wenn die neue Section nicht existiert!!!
		public void hasAnyPropertyExists(boolean bAnyPropertyExists) {
		if(!this.bAnyPropertyExists) this.bAnyPropertyExists=bAnyPropertyExists;
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
	public boolean isFormulaSolved() {
		return this.bFormulaSolved;
	}

	@Override
	public void isFormulaSolved(boolean bFormulaSolved) {
		this.bFormulaSolved = bFormulaSolved;
	}
	
	@Override 
	public void isFormulaMathSolved(boolean bFormulaMathSolved) {
		this.bFormulaMathSolved = bFormulaMathSolved;
	}
	
	@Override
	public boolean isFormulaMathSolved() {
		return this.bFormulaMathSolved; 
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
	
	//#######################################
	@Override 
	public boolean isParseCalled() {
		return this.bParseCalled;
	}
	
	@Override
	public void isParseCalled(boolean bParseCalled) {
		this.bParseCalled = bParseCalled;
	}
	
	@Override 
	public boolean isParsed() {
		return this.bParsed;
	}
	
	@Override
	public void isParsed(boolean bParsed) {
		this.bParsed = bParsed;
	}
	
	@Override 
	public boolean isParsedChanged() {
		return this.bParsedChanged;
	}
	
	@Override
	public void isParsedChanged(boolean bParsedChanged) {
		this.bParsedChanged = bParsedChanged;
	}
	
	//################################
	@Override 
	public boolean isSubstituteCalled() {
		return this.bSubstituteCalled;
	}
	
	@Override
	public void isSubstituteCalled(boolean bSubstituteCalled) {
		this.bSubstituteCalled = bSubstituteCalled;
	}
	
	@Override 
	public boolean isSubstitutedChanged() {
		return this.bSubstitutedChanged;
	}
	
	@Override
	public void isSubstitutedChanged(boolean bSubstitutedChanged) {
		this.bSubstitutedChanged = bSubstitutedChanged;
	}
	
	@Override 
	public boolean isPathSubstituted() {
		return this.bPathSubstituted;
	}
	
	@Override
	public void isPathSubstituted(boolean bPathSubstituted) {
		this.bPathSubstituted = bPathSubstituted;
	}
	
	@Override
	public boolean isPathSubstitutedChanged() {
		return this.bPathSubstitutedChanged;
	}
	
	@Override
	public void isPathSubstitutedChanged(boolean bPathSubstitutedChanged) {
		this.bPathSubstitutedChanged = bPathSubstitutedChanged;
	}
	
	@Override
	public boolean isVariableSubstituted() {
		return this.bVariableSubstituted;
	}
	
	@Override
	public void isVariableSubstituted(boolean bVariableSubstituted) {
		this.bVariableSubstituted = bVariableSubstituted;
	}
	
	@Override
	public boolean isVariableSubstitutedChanged() {
		return this.bVariableSubstitutedChanged;
	}
	
	@Override
	public void isVariableSubstitutedChanged(boolean bVariableSubstitutedChanged) {
		this.bVariableSubstitutedChanged = bVariableSubstitutedChanged;
	}
	
	//########################################
	
	@Override 
	public boolean isSolveCalled() {
		return this.bSolveCalled;
	}
	
	@Override
	public void isSolveCalled(boolean bSolveCalled) {
		this.bSolveCalled = bSolveCalled;
	}
	
	@Override 
	public boolean isSolved() {
		return this.bSolved;
	}
	
	@Override
	public void isSolved(boolean bSolved) {
		this.bSolved = bSolved;
	}
	
	
	@Override
	public boolean isSolvedChanged() {
		return this.bSolvedChanged; 
	}

	@Override
	public void isSolvedChanged(boolean bIsSolvedChanged) {
		this.bSolvedChanged = bIsSolvedChanged;
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
		this.bCall = bCall;
	}
	
	@Override
	public boolean isCallSolved() {
		return this.bCallSolved;
	}
	
	@Override
	public void isCallSolved(boolean bCallSolved) {
		this.bCallSolved = bCallSolved;
	}
	
	@Override
	public boolean isJavaCall() {
		return this.bJavaCall;
	}
	
	@Override
	public void isJavaCall(boolean bJavaCall) {
		this.bJavaCall = bJavaCall;
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
	public VectorDifferenceZZZ<String> getRawDecryptedVector(){
		return this.vecRawDecrypted;
	}
	
	@Override
	public void setRawDecryptedVector(VectorDifferenceZZZ<String> vecRawDecrypted) {
		this.vecRawDecrypted = vecRawDecrypted;
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
	public VectorDifferenceZZZ<String> getRawEncryptedVector(){
		return this.vecRawEncrypted;
	}
	
	@Override
	public void setRawEncryptedVector(VectorDifferenceZZZ<String> vecRawEncrypted) {
		this.vecRawEncrypted = vecRawEncrypted;
	}
	
	@Override
	public String getRawEncrypted() {
		return this.getRawEncryptedVector().getEntryLow(); //!!! Das ist also der allererste RAW String, nicht ggfs. spaeter weiter aufgeloeste Strings.
	}
	
	@Override
	public String getRawEncryptedAsExpression() throws ExceptionZZZ {
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
	public String getValueCallSolved() {
		return this.sValueCallSolved;
	}
	
	@Override
	public void setValueCallSolved(String sValueCallSolved) {
		this.sValueCallSolved = sValueCallSolved;
	}
	
	@Override
	public String getValueCallSolvedAsExpression() {
		return this.sValueCallSolvedAsExpression;
	}
	
	@Override
	public void setValueCallSolvedAsExpression(String sValueCallSolvedAsExpression) throws ExceptionZZZ {
		this.setValueCallSolvedAsExpression_(sValueCallSolvedAsExpression, false);
	}
	
	@Override
	public void setValueCallSolvedAsExpression(String sValueCallSolvedAsExpression, boolean bEnforce) throws ExceptionZZZ {
		this.setValueCallSolvedAsExpression_(sValueCallSolvedAsExpression, bEnforce);
	}
	
	private void setValueCallSolvedAsExpression_(String sValueCallSolvedAsExpression, boolean bEnforce) throws ExceptionZZZ {
		if(bEnforce) {
			String sValueAsExpressionNew = KernelConfigSectionEntryUtilZZZ.computeAsExpressionReflected(sValueCallSolvedAsExpression);
			this.sValueCallSolvedAsExpression = sValueAsExpressionNew;
		}else {
			this.sValueCallSolvedAsExpression = sValueCallSolvedAsExpression;
		}
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
	public boolean propertyExists() {
		return this.bPropertyExists;
	}

	@Override
	public void propertyExists(boolean bPropertyExists) throws ExceptionZZZ {
//      TODOGOON20240509: Eine der Stellen, an denen ein LogLevel=Debug sinnvoll waere.
		//                Ggfs. einbauen wenn dies alles auf die Verwendung von LogStringZZZ umgestellt wird.
//		if(bSectionExists) {
//			System.out.println(ReflectCodeZZZ.getPositionCurrent() + "Section Exists");
//		}
		this.bPropertyExists = bPropertyExists;
		this.hasAnyPropertyExists(bPropertyExists);
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

	//### aus IIniStructurePositionUserZZZ
	@Override
	public IIniStructurePositionZZZ getIniStructurePosition() throws ExceptionZZZ {
		if(this.objIniPosition==null) {
			this.objIniPosition = new IniStructurePositionZZZ(this);
		}
		return this.objIniPosition;
	}

	@Override
	public void setIniStructurePostion(IIniStructurePositionZZZ objIniStructurePosition) throws ExceptionZZZ {
		this.objIniPosition = objIniStructurePosition;
	}
	
	//### aus IIniStructurePositionUserZZZ
		@Override
		public String getSection() throws ExceptionZZZ {
			if(this.objIniPosition==null)return null;
			return this.getIniStructurePosition().getSection();
		}

		@Override
		//WICHTIG: Darin wird wieder SectionExists auf false gesetzt... ALSO unbedingt VOR dem setzten von sectionExists(true) verwenden!!!
		public void setSection(String sSection) throws ExceptionZZZ {
				this.getIniStructurePosition().setSection(sSection);
				
				//!!! wg. folgender Codezeile bedenken, dass erst der Section-Name gesetzt werden muss
				//    und dann erst der bSectionExists Wert. Sonst wird dieser naemlich wieder ueberschrieben.
				this.sectionExists(false);//es ist noch nicht bewiesen, dass es diese Section ueberhaupt gibt!!!
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
		public String getProperty() throws ExceptionZZZ{
			if(this.objIniPosition==null)return null;
			return this.getIniStructurePosition().getProperty();		
		}

		@Override
		public void setProperty(String sProperty) throws ExceptionZZZ{
			this.getIniStructurePosition().setProperty(sProperty);
			
			//!!! wg. folgender Codezeile bedenken, dass erst der Section-Name gesetzt werden muss
			//    und dann erst der bSectionExists Wert. Sonst wird dieser naemlich wieder ueberschrieben.
			this.propertyExists(false);//es ist noch nicht bewiesen, dass es diese Section ueberhaupt gibt!!!
		}
		
		
		@Override
		public void setProperty(String sProperty, boolean bExists) throws ExceptionZZZ{
			if(sProperty!=null) {
				this.setProperty(sProperty); //Beachte die Reihenfolge... erst Section setzen, dann ggfs. den true Wert
				this.propertyExists(bExists);
				this.getPropertiesSearchedHashMap().putAsLast(sProperty, bExists);
			}
		}

		@Override
		public void reset() throws ExceptionZZZ {
			TODOGOON20250203;//Setze Werte zurück...
		}
}
