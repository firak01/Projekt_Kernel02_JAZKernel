package basic.zKernel.file.ini;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IConvertEnabledZZZ;
import basic.zBasic.IObjectWithExpressionZZZ;
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
import basic.zBasic.util.xml.tagsimple.IParseEnabledZZZ;
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
				
				//Den Tag-Namen setzen. Dann kann der Wert auch immer im Debugger gesehen werden.
				if(StringZZZ.isEmpty(this.sTagName)) {
					this.sTagName = this.getNameDefault();
				}
								
				bReturn = true;
		 	}//end main:
			return bReturn;
		 }//end function AbstractObjectWithFormulaNew_

	//### aus IResettableValues
	@Override
	public boolean resetValues() throws ExceptionZZZ{
		super.resetValues();
		
		//Merke: Array erst auf Ini - Ebene behandeln, hier kann ein Separator String vewendet werden.
		//aus IValueArrayUserZZZ
		if(this.vecalValue!=null) vecalValue.clear();
		this.bArrayValue = false; 
			
		if(this.vechmValue!=null) vechmValue.clear();
		this.bMapValue = false;
		
		return true;
	}
	
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
	
		
		bReturn = XmlUtilZZZ.isSurroundedByTag(sExpression, this.getTagPartOpening(), this.getTagPartClosing());		
	}//end main
	return bReturn;
	}
	
	//### Aus IParseEnabledZZZ	
	@Override
	public boolean isParserEnabledThis() throws ExceptionZZZ {
		return this.getFlag(IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER);
	}

	@Override
	public String parse(String sExpression) throws ExceptionZZZ{
		return this.parse_(sExpression, true);
	}	
	
	@Override
	public String parse(String sExpression, boolean bRemoveSurroundingSeparators) throws ExceptionZZZ{
		return this.parse_(sExpression, bRemoveSurroundingSeparators);
	}
	
	private String parse_(String sExpressionIn, boolean bRemoveSurroundingSeparators) throws ExceptionZZZ{
		String sReturn = sExpressionIn;
		main:{			
			if(StringZZZ.isEmptyTrimmed(sExpressionIn)) break main;
			
			boolean bUseExpression = this.getFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION); 
			if(!bUseExpression) break main;
							
			//Falls man diesen Tag aus dem Parsen (des Gesamtstrings) rausnimmt, muessen die umgebenden Tags drin bleiben
			boolean bUseParse = this.isParserEnabledThis();
			if(!bUseParse) break main;
						
			String sExpression = sExpressionIn;
			
			sReturn = super.parse(sExpression, bRemoveSurroundingSeparators);
		}//end main:
		return sReturn;
	}
	
		
	//++++++++++++++++++++++++++++++++++++++++++++++

	
	
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	//Aus IIniTagBasicZZZ
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
	
	
	//### aus IParseEnabledZZZ

	
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
	public boolean isMapValue() throws ExceptionZZZ {
		return this.bMapValue;
	}
	
	@Override 
	public void isMapValue(boolean bIsMapValue) throws ExceptionZZZ {
		this.bMapValue = bIsMapValue;
	}
	
	//### aus IExpressionUserZZZ
	@Override
	public boolean isExpression(String sLineWithExpression) throws ExceptionZZZ {
		return XmlUtilZZZ.isExpression4TagXml(sLineWithExpression, this.getName());
	}	
	
//	@Override
//	public String parseAsExpression() throws ExceptionZZZ {
//		String sExpression = this.getValue();
//		return this.parseAsExpression(sExpression);
//	}	
//
//
//	@Override
//	public String parseAsExpression(String sLineWithExpression) throws ExceptionZZZ{
//		String sReturn = sLineWithExpression;
//		main:{
//			if(StringZZZ.isEmptyTrimmed(sLineWithExpression)) break main;
//			
//			Vector<String> vecAll = this.parseFirstVectorAsExpression(sLineWithExpression);
//			
//			//Der Vector ist schon so aufbereiten, dass hier nur noch "zusammenaddiert" werden muss					
//			sReturn = VectorUtilZZZ.implode(vecAll);
//			this.setValue(vecAll.get(1));
//			
//		}//end main:
//		return sReturn;
//	}
//	
//	@Override
//	public Vector3ZZZ<String> parseFirstVectorAsExpression(String sLineWithExpression) throws ExceptionZZZ{
//		Vector3ZZZ<String>vecReturn = new Vector3ZZZ<String>();
//		main:{
//			//Bei dem einfachen Tag wird die naechste Tag genommen und dann auch das naechste schliessende Tag...
//			//Fuer die EXPRESSION gilt: Es werden die Separatoren zurueckgegeben (mit true)
//			vecReturn = StringZZZ.vecMidFirst(sLineWithExpression, this.getTagStarting(), this.getTagClosing(), true, false);
//		}
//		return vecReturn;
//			
//	}
	
	@Override
	public String makeAsExpression(String sString) throws ExceptionZZZ{
		return ExpressionIniUtilZZZ.makeAsExpression(sString, this.getName());
	}

	
	//###################################
	//### FLAG Handling
	
	//### aus IIniTagWithExpressionZZZ	
	@Override
	public boolean getFlag(IObjectWithExpressionZZZ.FLAGZ objEnumFlag) {
		return this.getFlag(objEnumFlag.name());
	}
	@Override
	public boolean setFlag(IObjectWithExpressionZZZ.FLAGZ objEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		return this.setFlag(objEnumFlag.name(), bFlagValue);
	}
	
	@Override
	public boolean[] setFlag(IObjectWithExpressionZZZ.FLAGZ[] objaEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		boolean[] baReturn=null;
		main:{
			if(!ArrayUtilZZZ.isNull(objaEnumFlag)) {
				baReturn = new boolean[objaEnumFlag.length];
				int iCounter=-1;
				for(IObjectWithExpressionZZZ.FLAGZ objEnumFlag:objaEnumFlag) {
					iCounter++;
					boolean bReturn = this.setFlag(objEnumFlag, bFlagValue);
					baReturn[iCounter]=bReturn;
				}
			}
		}//end main:
		return baReturn;
	}
	
	@Override
	public boolean proofFlagExists(IObjectWithExpressionZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
			return this.proofFlagExists(objEnumFlag.name());
	}
	
	@Override
	public boolean proofFlagSetBefore(IObjectWithExpressionZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
			return this.proofFlagSetBefore(objEnumFlag.name());
	}
	
	
	//### Aus IKernelExpressionIniParserZZZ
	@Override
	public boolean getFlag(IKernelExpressionIniParserZZZ.FLAGZ objEnumFlag) {
		return this.getFlag(objEnumFlag.name());
	}
	@Override
	public boolean setFlag(IKernelExpressionIniParserZZZ.FLAGZ objEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		return this.setFlag(objEnumFlag.name(), bFlagValue);
	}
	
	@Override
	public boolean[] setFlag(IKernelExpressionIniParserZZZ.FLAGZ[] objaEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		boolean[] baReturn=null;
		main:{
			if(!ArrayUtilZZZ.isNull(objaEnumFlag)) {
				baReturn = new boolean[objaEnumFlag.length];
				int iCounter=-1;
				for(IKernelExpressionIniParserZZZ.FLAGZ objEnumFlag:objaEnumFlag) {
					iCounter++;
					boolean bReturn = this.setFlag(objEnumFlag, bFlagValue);
					baReturn[iCounter]=bReturn;
				}
			}
		}//end main:
		return baReturn;
	}
	
	@Override
	public boolean proofFlagExists(IKernelExpressionIniParserZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
			return this.proofFlagExists(objEnumFlag.name());
	}
	
	@Override
	public boolean proofFlagSetBefore(IKernelExpressionIniParserZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
			return this.proofFlagSetBefore(objEnumFlag.name());
	}
}
