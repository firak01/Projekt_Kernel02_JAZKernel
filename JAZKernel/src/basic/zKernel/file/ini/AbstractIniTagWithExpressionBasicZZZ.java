package basic.zKernel.file.ini;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IConvertEnabledZZZ;
import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.abstractList.ArrayListExtendedZZZ;
import basic.zBasic.util.abstractList.Vector3ZZZ;
import basic.zBasic.util.abstractList.VectorDifferenceZZZ;
import basic.zBasic.util.abstractList.VectorUtilZZZ;
import basic.zBasic.util.datatype.calling.ReferenceZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.datatype.xml.XmlUtilZZZ;
import basic.zBasic.util.file.ini.IIniStructureConstantZZZ;
import basic.zBasic.util.file.ini.IIniStructurePositionZZZ;
import basic.zBasic.util.file.ini.IniStructurePositionZZZ;
import basic.zBasic.util.xml.tagexpression.AbstractTagWithExpressionBasicZZZ;
import basic.zKernel.IKernelConfigSectionEntryZZZ;
import basic.zKernel.KernelConfigSectionEntryZZZ;

//DIES IST DER FLAG - WEG: AbstractObjectWithFlagZZZ -> AbstractObjectWithExpression -> AbstractTagWithExpressionBasic
//ALSO: ALLES WAS IN AbstractIniTagBasicZZZ und dessen Elternklasse implementiert ist hierein kopieren

//ABER OHNE einen IKernelConfigSectionEntryZZZ als interne Variable. D.h. die einzelnen Variablen müssen direkt hier gespeichert werden. 
public abstract class AbstractIniTagWithExpressionBasicZZZ<T> extends AbstractTagWithExpressionBasicZZZ<T> implements IIniTagBasicZZZ, IConvertEnabledZZZ, IIniTagWithExpressionZZZ{
	private static final long serialVersionUID = 4049221887081114236L;
	
	//aus IIniStructurePositionUserZZZ
	protected IIniStructurePositionZZZ objIniPosition;
	
	//Merke: Array erst auf Ini - Ebene behandeln, hier kann ein Separator String vewendet werden.
	//aus IValueArrayUserZZZ
	protected VectorDifferenceZZZ<ArrayList<String>> vecalValue = new VectorDifferenceZZZ<ArrayList<String>>();
	protected boolean bArrayValue = false; //Falls eine ArrayList gesetzt wurde.
		
	protected VectorDifferenceZZZ<HashMap<String,String>> vechmValue = new VectorDifferenceZZZ<HashMap<String,String>>();
	protected boolean bMapValue = false; //Falls eine ArrayList gesetzt wurde.
	
	public AbstractIniTagWithExpressionBasicZZZ() throws ExceptionZZZ{
		super();
		AbstractIniTagWithExpressionBasicNew_();
	}
	
	public AbstractIniTagWithExpressionBasicZZZ(String sFlagControl) throws ExceptionZZZ{
		super(sFlagControl);
		AbstractIniTagWithExpressionBasicNew_();
	}
	
	public AbstractIniTagWithExpressionBasicZZZ(String[] saFlagControl) throws ExceptionZZZ{
		super(saFlagControl);
		AbstractIniTagWithExpressionBasicNew_();
	}
	
	private boolean AbstractIniTagWithExpressionBasicNew_() throws ExceptionZZZ {
		 boolean bReturn = false;
		 main:{			 
				if(this.getFlag("init")==true){
					bReturn = true;
					break main;
				}
				
				bReturn = true;
		 	}//end main:
			return bReturn;
		 }//end function AbstractObjectWithFormulaNew_

	
	//######## Getter / Setter #################
	
	//### aus IConvertableZZZ
		
	/* IDEE: convertable != parseable.
    convertable bedeutet DER GANZE STRING Wird ersetzt, also nur wenn nix davor oder dahniter steht.
    parsable würde dann lediglich den Wert aus der Mitte (s. Vector.getIndex(1) ) durch ein Leerzeichen ersetzen
	
	* 
	* (non-Javadoc)
	* @see basic.zKernel.file.ini.AbstractIniTagSimpleZZZ#isConvertRelevant(java.lang.String)
	*/
	@Override
	public boolean isConvertRelevant(String sExpression) throws ExceptionZZZ {
	boolean bReturn = false;
	main:{
		if(StringZZZ.isEmptyTrimmed(sExpression)) break main;
		
		bReturn = this.getEmpty().equalsIgnoreCase(sExpression);
		if(bReturn) break main;
	
		
		bReturn = XmlUtilZZZ.isSurroundedByTag(sExpression, this.getTagStarting(), this.getTagClosing());		
	}//end main
	return bReturn;
	}
	
	//### Aus IParseEnabledZZZ	
	@Override
	public String parse(String sExpression) throws ExceptionZZZ{
		return this.parse_(sExpression, true);
	}	
	
	@Override
	public String parse(String sExpression, boolean bRemoveSurroundingSeparators) throws ExceptionZZZ{
		return this.parse_(sExpression, bRemoveSurroundingSeparators);
	}
	
	private String parse_(String sExpression, boolean bRemoveSurroundingSeparators) throws ExceptionZZZ{
		String sReturn = sExpression;
		main:{
			if(! this.getFlag(IIniTagWithExpressionZZZ.FLAGZ.USEEXPRESSION)) break main;
			
			sReturn = super.parse(sExpression, bRemoveSurroundingSeparators);
		}//end main:
		return sReturn;
	}
	
	//++++++++++++++++++++++++++++++++++++++++++++++
	//++++++++++++++++++++++++++++++++++++++++++
	//### aus IIniTagBasicZZZ  	
//	@Override
//	public IKernelConfigSectionEntryZZZ parseAsEntryNew(String sExpression) throws ExceptionZZZ{
//		//Nein, das setzt das Entry-Objekt des Solvers zurueck IKernelConfigSectionEntryZZZ objReturn = this.getEntryNew();
//		//und damit sind bestehende Eintragswerte ggfs. uebernommen IKernelConfigSectionEntryZZZ objReturn = new KernelConfigSectionEntryZZZ<T>(this);
//		IKernelConfigSectionEntryZZZ objReturn = new KernelConfigSectionEntryZZZ<T>();						
//		main:{			
//			if(StringZZZ.isEmptyTrimmed(sExpression)) break main;
//			objReturn.setRaw(sExpression);
//					
//			ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReferenceSolve = new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
//			objReturnReferenceSolve.set(objReturn);
//			objReturn = this.parseAsEntry_(sExpression, objReturnReferenceSolve, true);			
//		}//end main:
//		return objReturn;
//	}	
		
	//!!! Methoden der Elternklasse ueberschreiben, weil das hier der Flag-Vererbungsweg ist!!!
	@Override
	public IKernelConfigSectionEntryZZZ parseAsEntry(String sExpression) throws ExceptionZZZ{
		return this.parseAsEntry_(sExpression, null, true);
	}
	
	@Override
	public IKernelConfigSectionEntryZZZ parseAsEntry(String sExpression, boolean bRemoveSurroundingSeparators) throws ExceptionZZZ{
		return this.parseAsEntry_(sExpression, null, bRemoveSurroundingSeparators);
	}
	
	@Override
	public IKernelConfigSectionEntryZZZ parseAsEntry(String sExpression, ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReferenceIn) throws ExceptionZZZ{
		return this.parseAsEntry_(sExpression, objReturnReferenceIn, true);
	}
	
	@Override
	public IKernelConfigSectionEntryZZZ parseAsEntry(String sExpression, ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReferenceIn, boolean bRemoveSurroundingSeparators) throws ExceptionZZZ{
		return this.parseAsEntry(sExpression, objReturnReferenceIn, bRemoveSurroundingSeparators);
	}
	
	private IKernelConfigSectionEntryZZZ parseAsEntry_(String sExpressionIn, ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReferenceIn, boolean bRemoveSurroundingSeparators) throws ExceptionZZZ{
		IKernelConfigSectionEntryZZZ objReturn = null;
		String sReturn = sExpressionIn;
		main:{
			if(StringZZZ.isEmptyTrimmed(sExpressionIn)) break main;
									
			ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReference = new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
			if(objReturnReferenceIn==null) {
				objReturnReference =  new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();				
			}else {
				objReturnReference =  objReturnReferenceIn;
				objReturn = objReturnReference.get();				
			}
			
			if(objReturn==null) {
				//Das Ziel ist es moeglichst viel Informationen aus dem entry "zu retten"
				//Achtung: Das objReturn Objekt NICHT generell versuchen ueber .getEntry() und dann ggfs. .getEntryNew() zu uebernehmen. Es verfaelscht bei einem 2. Suchaufruf das Ergebnis.
				//objEntry = this.getEntry();
				
				//nein, dann gehen alle Informationen verloren
				//objReturn = this.parseAsEntryNew(sExpression);
				
				objReturn = new KernelConfigSectionEntryZZZ<T>(this);  
				objReturnReference.set(objReturn);
			}
			objReturn.setRaw(sExpressionIn);
			
			//Es soll immer ein Entry Objekt zurückkommen, darum hier erst auf das Expression-Flag abpruefen.
			boolean bUseExpression = this.getFlag(IIniTagWithExpressionZZZ.FLAGZ.USEEXPRESSION); 
			if(!bUseExpression) break main;
			
			//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!1
			//Merke: in Elternklassen gibt es diese Methode nur ohne Reference, da ohne KernelEbene das Objekt nicht vorhanden ist.
			//       Darum wird die Methode auch hier von erbenden Klassen ueberschrieben.
			//Hier Methode nur ohne Reference... String sReturn = this.parse(sExpression, objReturnReferenceParse, bRemoveSurroundingSeparators);
			//Mit Reference geht ab: AbstractKernelIniTagSimpleZZZ
			//ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReferenceParse = new ReferenceZZZ<IKernelConfigSectionEntryZZZ>(); 			
			//objReturnReferenceParse.set(objReturn);
			//String sExpression = sExpressionIn;
			//sReturn = this.parse(sExpression, objReturnReferenceParse, bRemoveSurroundingSeparators);
			//objReturn = objReturnReferenceParse.get();			
			
			String sExpression = sExpressionIn;
			sReturn = this.parse(sExpression, bRemoveSurroundingSeparators);
			
			this.setValue(sReturn);				
		}//end main:
		
		if(objReturn!=null) {
			objReturn.setValue(sReturn);	
			if(sExpressionIn!=null) {
				if(!sExpressionIn.equals(sReturn)) objReturn.isParsed(true);
			}				
			if(objReturnReferenceIn!=null) objReturnReferenceIn.set(objReturn);
		}					
		return objReturn;
	}

	//++++++++++++++++++++++++++++++++++++++++++++++
	@Override
	public String[] parseAsArray(String sLineWithExpression) throws ExceptionZZZ{
		return this.parseAsArray(sLineWithExpression, IIniStructureConstantZZZ.sINI_MULTIVALUE_SEPARATOR);
	}
	
	@Override
	public String[] parseAsArray(String sLineWithExpression, String sDelimiterIn) throws ExceptionZZZ{
		String[] saReturn = null; //new String[];//sLineWithExpression;
		main:{
			if(StringZZZ.isEmptyTrimmed(sLineWithExpression)) break main;
			
			String sDelimiter;
			if(StringZZZ.isEmpty(sDelimiterIn)) {
				sDelimiter = IIniStructureConstantZZZ.sINI_MULTIVALUE_SEPARATOR; 
//					ExceptionZZZ ez = new ExceptionZZZ("Delimiter for Array Values", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
//					throw ez;
			}else {
				sDelimiter = sDelimiterIn;
			}
			
			Vector3ZZZ<String> vecParsed = this.parseFirstVector(sLineWithExpression);
			String sParsed = VectorUtilZZZ.implode(vecParsed);
			
			String[] saExpression = StringZZZ.explode(sParsed, sDelimiter); //Dann löse Ihn als Mehrfachwert auf.
			ArrayListExtendedZZZ<String> listasValue = new ArrayListExtendedZZZ<String>(saExpression);
			this.setValue(listasValue);		
			
			//Fasse nun zusammen.
			ArrayListExtendedZZZ<String> listasReturnParsed = new ArrayListExtendedZZZ<String>();
			listasReturnParsed.add((String) vecParsed.get(0));
			
			//Fuer den Wert lediglich
			ArrayListExtendedZZZ<String> listasValueParsed = new ArrayListExtendedZZZ<String>();
			
			String sValue = null;			
			for(String sExpression : saExpression) {
				sValue = this.parse(sExpression);
				listasReturnParsed.add(sValue);
				listasValueParsed.add(sValue);
			}
			listasReturnParsed.add((String) vecParsed.get(2));
			
			this.setValue(listasValueParsed);
			saReturn = listasReturnParsed.toStringArray();				
		}//end main:
		return saReturn;
	}
	
	
	
//	@Override
//	public IKernelConfigSectionEntryZZZ parseAsEntryNew(String sExpression) throws ExceptionZZZ{
//		//Nein, das setzt das Entry-Objekt des Solvers zurueck IKernelConfigSectionEntryZZZ objReturn = this.getEntryNew();
//		//und damit sind bestehende Eintragswerte ggfs. uebernommen IKernelConfigSectionEntryZZZ objReturn = new KernelConfigSectionEntryZZZ<T>(this);
//		IKernelConfigSectionEntryZZZ objReturn = new KernelConfigSectionEntryZZZ<T>();						
//		main:{
//			if(StringZZZ.isEmptyTrimmed(sExpression)) break main;
//			objReturn.setRaw(sExpression);
//
//			ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReferenceSolve = new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
//			objReturnReferenceSolve.set(objReturn);
//			objReturn = this.parseAsEntry_(sExpression, objReturnReferenceSolve, true);			
//		}//end main:
//		return objReturn;
//	}	
//
//	//++++++++++++++++++++++++++++++++++++++++++
//	@Override
//	public IKernelConfigSectionEntryZZZ parseAsEntry(String sExpression) throws ExceptionZZZ{
//		return this.parseAsEntry_(sExpression, null, true);
//	}
//	
//	@Override
//	public IKernelConfigSectionEntryZZZ parseAsEntry(String sExpression, boolean bRemoveSurroundingSeparators) throws ExceptionZZZ{
//		return this.parseAsEntry_(sExpression, null, bRemoveSurroundingSeparators);
//	}
//	
//	@Override
//	public IKernelConfigSectionEntryZZZ parseAsEntry(String sExpression, ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReferenceIn) throws ExceptionZZZ{
//		return this.parseAsEntry_(sExpression, objReturnReferenceIn, true);
//	}
//	
//	@Override
//	public IKernelConfigSectionEntryZZZ parseAsEntry(String sExpression, ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReferenceIn, boolean bRemoveSurroundingSeparators) throws ExceptionZZZ{
//		return this.parseAsEntry(sExpression, objReturnReferenceIn, bRemoveSurroundingSeparators);
//	}
//	
//	private IKernelConfigSectionEntryZZZ parseAsEntry_(String sExpressionIn, ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReferenceIn, boolean bRemoveSurroundingSeparators) throws ExceptionZZZ{
//		IKernelConfigSectionEntryZZZ objReturn = null; //new KernelConfigSectionEntryZZZ<T>(this);
//		String sReturn = sExpressionIn;
//		main:{
//			if(StringZZZ.isEmptyTrimmed(sExpressionIn)) break main;
//						
//			ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReference = new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
//			if(objReturnReferenceIn==null) {
//				//Das Ziel ist es moeglichst viel Informationen aus dem entry "zu retten"
//				objReturn = new KernelConfigSectionEntryZZZ<T>(this); //geht hier nicht... this.getEntryNew(); ausserdem gingen alle Informationen verloren				
//				                                                      //nein, dann gehen alls Informationen verloren   objReturn = this.parseAsEntryNew(sExpression);				
//			}else {
//				objReturn = objReturnReferenceIn.get();				
//			}
//			
//			if(objReturn==null) {
//				// =  this.parseAsEntryNew(sExpression);  //nein, dann gehen alle Informationen verloren   objReturn = this.parseAsEntryNew(sExpression);
//				objReturn = new KernelConfigSectionEntryZZZ<T>(this);
//			}
//			objReturn.setRaw(sExpressionIn);
//						
//			//Hier Methode nur ohne Reference... String sReturn = this.parse(sExpression, objReturnReferenceParse, bRemoveSurroundingSeparators);
//			//Mit Reference geht ab: AbstractKernelIniTagSimpleZZZ
//			ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReferenceParse = new ReferenceZZZ<IKernelConfigSectionEntryZZZ>(); 			
//			objReturnReference.set(objReturn)
//			String sExpression = sExpressionIn;
//			sReturn = this.parse(sExpression, objReturnReference, bRemoveSurroundingSeparators);
//			//objReturn = objReturnReference.get();			
//			this.setValue(sReturn);
//			
//		}//end main:
//		
//
//		if(objReturn!=null) {
//			objReturn.setValue(sReturn);	
//			if(sExpressionIn!=null) {
//				if(!sExpressionIn.equals(sReturn)) objReturn.isParsed(true);
//			}				
//			if(objReturnReferenceIn!=null) objReturnReferenceIn.set(objReturn);
//		}					
//		return objReturn;
//	}
		
	//++++++++++++++++++++++++++++++++++++++++++++++
//	@Override
//	public String[] parseAsArray(String sLineWithExpression) throws ExceptionZZZ{
//		return this.parseAsArray(sLineWithExpression, IIniStructureConstantZZZ.sINI_MULTIVALUE_SEPARATOR);
//	}
//	
//	@Override
//	public String[] parseAsArray(String sLineWithExpression, String sDelimiterIn) throws ExceptionZZZ{
//		String[] saReturn = null; //new String[];//sLineWithExpression;
//		main:{
//			if(StringZZZ.isEmptyTrimmed(sLineWithExpression)) break main;
//			
//			String sDelimiter;
//			if(StringZZZ.isEmpty(sDelimiterIn)) {
//				sDelimiter = IIniStructureConstantZZZ.sINI_MULTIVALUE_SEPARATOR; 
////					ExceptionZZZ ez = new ExceptionZZZ("Delimiter for Array Values", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
////					throw ez;
//			}else {
//				sDelimiter = sDelimiterIn;
//			}
//			
//			Vector3ZZZ<String> vecParsed = this.parseFirstVector(sLineWithExpression);
//			String sParsed = VectorUtilZZZ.implode(vecParsed);
//			
//			String[] saExpression = StringZZZ.explode(sParsed, sDelimiter); //Dann löse Ihn als Mehrfachwert auf.
//			ArrayListExtendedZZZ<String> listasValue = new ArrayListExtendedZZZ<String>(saExpression);
//			this.setValue(listasValue);		
//			
//			//Fasse nun zusammen.
//			ArrayListExtendedZZZ<String> listasReturnParsed = new ArrayListExtendedZZZ<String>();
//			listasReturnParsed.add((String) vecParsed.get(0));
//			
//			//Fuer den Wert lediglich
//			ArrayListExtendedZZZ<String> listasValueParsed = new ArrayListExtendedZZZ<String>();
//			
//			String sValue = null;			
//			for(String sExpression : saExpression) {
//				sValue = this.parse(sExpression);
//				listasReturnParsed.add(sValue);
//				listasValueParsed.add(sValue);
//			}
//			listasReturnParsed.add((String) vecParsed.get(2));
//			
//			this.setValue(listasValueParsed);
//			saReturn = listasReturnParsed.toStringArray();				
//		}//end main:
//		return saReturn;
//	}
	
	//aus IParseEnabledZZZ
	@Override
	public boolean isParse(String sExpressionToProof) throws ExceptionZZZ {
		return ExpressionIniUtilZZZ.isParse(sExpressionToProof, this.getNameDefault() , false);
	}
	
	//### aus IIniStructurePositionUserZZZ
	@Override
	public IIniStructurePositionZZZ getIniStructurePosition() throws ExceptionZZZ{
		if(this.objIniPosition==null) {
			this.objIniPosition = new IniStructurePositionZZZ(this);
		}
		return this.objIniPosition;
	}
	
	@Override 
	public void setIniStructurePostion(IIniStructurePositionZZZ objIniPosition) throws ExceptionZZZ{
		this.objIniPosition = objIniPosition;
	}
	
	//### aus IIniStructurePositionZZZ
	@Override
	public String getSection() throws ExceptionZZZ {
		if(this.objIniPosition==null)return null;
		return this.getIniStructurePosition().getSection();
	}

	@Override
	public void setSection(String sSection) throws ExceptionZZZ {
		this.getIniStructurePosition().setSection(sSection);
	}

	@Override
	public String getProperty() throws ExceptionZZZ {
		if(this.objIniPosition==null)return null; 
		return this.getIniStructurePosition().getProperty();
	}

	@Override
	public void setProperty(String sProperty) throws ExceptionZZZ {
		this.getIniStructurePosition().setProperty(sProperty);
	}
	
	//###############################################
	//### aus IValueArrayUserZZZ
	@Override 
	public VectorDifferenceZZZ<ArrayList<String>> getValueArrayListVector() throws ExceptionZZZ{
		return this.vecalValue;
	}
	
	
	@Override
	public ArrayList<String> getValueArrayList() throws ExceptionZZZ {		
		if(this.hasNullValue()){
			return null;		
		}else if (!this.hasAnyValue()){
			return new ArrayList<String>(); //also anders als beim definierten </NULL> -Objekt hier einen Leerstring zurückgeben. Ein Leerstring kann nämlich auch gewuenscht sein!				
		}else {
			return this.getValueArrayListVector().getEntryHigh();
		}
	}


	@Override
	public void setValue(ArrayList<String> alValue) throws ExceptionZZZ {
		this.getValueArrayListVector().add(alValue);
		this.isArrayValue(true);
	}
	
	@Override
	public boolean isArrayValue() throws ExceptionZZZ {
		return this.bArrayValue;
	}
	
	@Override 
	public void isArrayValue(boolean bIsArrayValue) throws ExceptionZZZ {
		this.bArrayValue = bIsArrayValue;
	}
	
	//###############################################
	//### aus IValueMapUserZZZ
	@Override 
	public VectorDifferenceZZZ<HashMap<String,String>> getValueHashMapVector() throws ExceptionZZZ{
		return this.vechmValue;
	}
		
	@Override
	public HashMap<String,String> getValueHashMap() throws ExceptionZZZ {		
		if(this.hasNullValue()){
			return null;		
		}else if (!this.hasAnyValue()){
			return new HashMap<String,String>(); //also anders als beim definierten </NULL> -Objekt hier einen Leerstring zurückgeben. Ein Leerstring kann nämlich auch gewuenscht sein!				
		}else {
			return this.getValueHashMapVector().getEntryHigh();
		}
	}

	@Override
	public void setValue(HashMap<String,String> hmValue) throws ExceptionZZZ {
		this.getValueHashMapVector().add(hmValue);
		this.isMapValue(true);
	}
	
	@Override
	public boolean isMapValue() {
		return this.bMapValue;
	}
	
	@Override 
	public void isMapValue(boolean bIsMapValue) {
		this.bMapValue = bIsMapValue;
	}
	
	//### aus IExpressionUserZZZ
	@Override
	public boolean isExpression(String sLineWithExpression) throws ExceptionZZZ {
		return XmlUtilZZZ.isExpression4TagXml(sLineWithExpression, this.getName());
	}	
	
	@Override
	public String parseAsExpression() throws ExceptionZZZ {
		String sExpression = this.getValue();
		return this.parseAsExpression(sExpression);
	}	


	@Override
	public String parseAsExpression(String sLineWithExpression) throws ExceptionZZZ{
		String sReturn = sLineWithExpression;
		main:{
			if(StringZZZ.isEmptyTrimmed(sLineWithExpression)) break main;
			
			Vector<String> vecAll = this.parseFirstVectorAsExpression(sLineWithExpression);
			
			//Der Vector ist schon so aufbereiten, dass hier nur noch "zusammenaddiert" werden muss					
			sReturn = VectorUtilZZZ.implode(vecAll);
			this.setValue(vecAll.get(1));
			
		}//end main:
		return sReturn;
	}
	
	@Override
	public Vector3ZZZ<String> parseFirstVectorAsExpression(String sLineWithExpression) throws ExceptionZZZ{
		Vector3ZZZ<String>vecReturn = new Vector3ZZZ<String>();
		main:{
			//Bei dem einfachen Tag wird die naechste Tag genommen und dann auch das naechste schliessende Tag...
			//Fuer die EXPRESSION gilt: Es werden die Separatoren zurueckgegeben (mit true)
			vecReturn = StringZZZ.vecMidFirst(sLineWithExpression, this.getTagStarting(), this.getTagClosing(), true, false);
		}
		return vecReturn;
			
	}

	
	//###################################
	//### FLAG Handling
	
	//### aus IIniTagWithExpressionZZZ	
	@Override
	public boolean getFlag(IIniTagWithExpressionZZZ.FLAGZ objEnumFlag) {
		return this.getFlag(objEnumFlag.name());
	}
	@Override
	public boolean setFlag(IIniTagWithExpressionZZZ.FLAGZ objEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		return this.setFlag(objEnumFlag.name(), bFlagValue);
	}
	
	@Override
	public boolean[] setFlag(IIniTagWithExpressionZZZ.FLAGZ[] objaEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		boolean[] baReturn=null;
		main:{
			if(!ArrayUtilZZZ.isNull(objaEnumFlag)) {
				baReturn = new boolean[objaEnumFlag.length];
				int iCounter=-1;
				for(IIniTagWithExpressionZZZ.FLAGZ objEnumFlag:objaEnumFlag) {
					iCounter++;
					boolean bReturn = this.setFlag(objEnumFlag, bFlagValue);
					baReturn[iCounter]=bReturn;
				}
			}
		}//end main:
		return baReturn;
	}
	
	@Override
	public boolean proofFlagExists(IIniTagWithExpressionZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
			return this.proofFlagExists(objEnumFlag.name());
	}
	
	@Override
	public boolean proofFlagSetBefore(IIniTagWithExpressionZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
			return this.proofFlagSetBefore(objEnumFlag.name());
	}
}
