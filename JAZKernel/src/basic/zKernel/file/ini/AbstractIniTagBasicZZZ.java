package basic.zKernel.file.ini;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import basic.zBasic.ExceptionZZZ;
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
import basic.zBasic.util.xml.tagsimple.AbstractTagParseEnabledZZZ;
import basic.zKernel.IKernelConfigSectionEntryZZZ;
import basic.zKernel.KernelConfigSectionEntryZZZ;
import basic.zKernel.config.KernelConfigSectionEntryUtilZZZ;

//DIES IST NICHT DER FLAG - WEG, sondern der reine Objekt Weg: AbstractObjectZZZ -> AbstractObjectWithValueBufferedZZZ -> AbstractTagParseEnabledZZZ -> AbstractIniTagBasicZZZ
//ALLES WAS IN AbstractIniTagWithExpressionBasicZZZ steht dort rein

//ABER OHNE einen IKernelConfigSectionEntryZZZ als interne Variable. D.h. die einzelnen Variablen müssen direkt hier gespeichert werden.
public abstract class AbstractIniTagBasicZZZ<T> extends AbstractTagParseEnabledZZZ<T>  implements IIniTagBasicZZZ{			
	private static final long serialVersionUID = -3411751655174978836L;

	//aus IIniStructurePositionUserZZZ
	protected IIniStructurePositionZZZ objIniPosition=null;
	
	//Merke: Array erst auf Ini - Ebene behandeln, hier kann ein Separator String vewendet werden.
	//aus IValueArrayUserZZZ
	protected VectorDifferenceZZZ<ArrayList<String>> vecalValue = new VectorDifferenceZZZ<ArrayList<String>>();
	protected boolean bArrayValue = false; //Falls eine ArrayList gesetzt wurde.
	
	protected VectorDifferenceZZZ<HashMap<String,String>> vechmValue = new VectorDifferenceZZZ<HashMap<String,String>>();
	protected boolean bMapValue = false; //Falls eine ArrayList gesetzt wurde.
	
	public AbstractIniTagBasicZZZ() throws ExceptionZZZ{
		super();
		AbstractIniTagBasicNew_(null);
	}
	
	public AbstractIniTagBasicZZZ(IIniStructurePositionZZZ objIniPosition) throws ExceptionZZZ{
		super();
		AbstractIniTagBasicNew_(objIniPosition);
	}
	
	public AbstractIniTagBasicZZZ(String sName, String sValue) throws ExceptionZZZ{
		super(sName, sValue);
		AbstractIniTagBasicNew_(null);
	}
	
	public AbstractIniTagBasicZZZ(IIniStructurePositionZZZ objIniPosition, String sName, String sValue) throws ExceptionZZZ{
		super(sName, sValue);
		AbstractIniTagBasicNew_(objIniPosition);
	}
		
	private boolean AbstractIniTagBasicNew_(IIniStructurePositionZZZ objIniPosition) throws ExceptionZZZ{
		this.setIniStructurePostion(objIniPosition);
		return true;
	}
	
	
	//### Aus IResettableValuesZZZ
	@Override
	public boolean reset() throws ExceptionZZZ{
		super.reset();
		this.objIniPosition=null; //so gesehen kein Value
		return true;
	}
	
	@Override
	public boolean resetValues() throws ExceptionZZZ{
		super.resetValues();
		//Merke objIniPosition ist so gesehen kein Value und wird nicht zurueckgesetzt.
		if(this.vecalValue!=null) this.vecalValue.clear();
		this.bArrayValue=false;
		
		if(this.vechmValue!=null) this.vechmValue.clear();
		this.bMapValue=false;
		return true;
	}
	
	
	//### aus IIniTagBasicZZZ  	
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
			
			Vector<String> vecReturn = this.parseFirstVector(sLineWithExpression);
			if(vecReturn==null)break main;
			if(StringZZZ.isEmpty((String)vecReturn.get(1))) break main; //Dann ist der Tag nicht enthalten und es darf(!) nicht weitergearbeitet werden.
		
			String sParsed = vecReturn.get(1);
			this.setValue(sParsed);
			
			String[] saExpression = StringZZZ.explode(sParsed, sDelimiter); //Dann löse Ihn als Mehrfachwert auf.
			ArrayListExtendedZZZ<String> listasValue = new ArrayListExtendedZZZ<String>(saExpression);
			this.setValue(listasValue);		
			
			//Fasse nun zusammen.
			ArrayListExtendedZZZ<String> listasReturnParsed = new ArrayListExtendedZZZ<String>();
			listasReturnParsed.add(vecReturn.get(0));
			
			//Fuer den Wert lediglich
			ArrayListExtendedZZZ<String> listasValueParsed = new ArrayListExtendedZZZ<String>();
			
			String sValue = null;			
			for(String sExpression : saExpression) {
				sValue = this.parse(sExpression);
				listasReturnParsed.add(sValue);
				listasValueParsed.add(sValue);
			}
			listasReturnParsed.add(vecReturn.get(2));
			
			this.setValue(listasValueParsed);
			saReturn = listasReturnParsed.toStringArray();				
		}//end main:
		return saReturn;
	}
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	
	//### Aus IParseEnabledZZZ	
	@Override
	public String parse(String sLineWithExpression) throws ExceptionZZZ{
		return this.parse_(sLineWithExpression, true);
	}
				
	@Override
	public String parse(String sLineWithExpression, boolean bKeepSurroundingSeparators) throws ExceptionZZZ{
		return this.parse_(sLineWithExpression, bKeepSurroundingSeparators);
	}	
	
	private String parse_(String sExpressionIn, boolean bKeepSurroundingSeparators) throws ExceptionZZZ{
		String sReturnLine = sExpressionIn;
		String sReturnTag = null;
		main:{
			if(StringZZZ.isEmptyTrimmed(sExpressionIn)) break main;
			
			Vector3ZZZ<String> vecReturn = this.parseFirstVector(sExpressionIn, bKeepSurroundingSeparators);
			if(vecReturn==null) break main;
			if(StringZZZ.isEmpty((String)vecReturn.get(1))) break main; //Dann ist der Tag nicht enthalten und es darf(!) nicht weitergearbeitet werden.
			
			sReturnTag = (String) vecReturn.get(1);
			this.setValue(sReturnTag);
							
			vecReturn = this.parsePost(vecReturn, bKeepSurroundingSeparators);
			sReturnTag = (String) vecReturn.get(1); //den ggfs. leer gewordenen Tag setzen
			this.setValue(sReturnTag);
				
			//Der zurueckgegebene Wert unterscheidet sich vom Wert des Tags selber.
			sReturnLine = VectorUtilZZZ.implode(vecReturn);									
		}//end main:
		return sReturnLine;
	}	
	
	@Override
	public Vector3ZZZ<String> parsePost(Vector3ZZZ<String> vecExpression) throws ExceptionZZZ {
		return this.parsePost_(vecExpression, true, true);
	}
	
	@Override
	public Vector3ZZZ<String> parsePost(Vector3ZZZ<String> vecExpression,boolean bKeepSurroundingSeparators) throws ExceptionZZZ {		
		return this.parsePost_(vecExpression, bKeepSurroundingSeparators, true);
	}

	@Override
	public Vector3ZZZ<String> parsePost(Vector3ZZZ<String> vecExpression, boolean bKeepSurroundingSeparators, boolean bRemoveOwnTagParts) throws ExceptionZZZ {
		return this.parsePost_(vecExpression, bKeepSurroundingSeparators, bRemoveOwnTagParts);
	}

	
	private Vector3ZZZ<String> parsePost_(Vector3ZZZ<String> vecExpressionIn, boolean bKeepSurroundingSeparators, boolean bRemoveOwnTagParts) throws ExceptionZZZ {		
		Vector3ZZZ<String> vecReturn = vecExpressionIn;
		String sReturn = null;
		String sReturnTag = null;		
		boolean bUseParse = false;
		
							
		main:{			
			if(vecExpressionIn==null) break main;
						
			sReturnTag = (String) vecExpressionIn.get(1);
			sReturn = sReturnTag;
			this.setValue(sReturnTag);
									
			//Als echten Ergebniswert aber die konkreten <Z>-Tags (z.B. eines Solves) ggfs. rausrechnen, falls gewuenscht
			//Z...-Tags "aus der Mitte entfernen"... Wichtig für das Ergebnis eines Parsens
			bUseParse = this.isParserEnabledThis();
			if(bUseParse) {			
				if(!bKeepSurroundingSeparators) {
					String sTagStart = "<Z>"; //this.getTagStarting();
					String sTagEnd = "</Z>";  //this.getTagClosing();
					KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(vecReturn, sTagStart, sTagEnd);  //also an jeder Position (d.h. nicht nur am Anfang), also von innen nach aussen
	
					sReturnTag = (String) vecReturn.get(1);
					sReturn = sReturnTag;
					this.setValue(sReturnTag);
				}	
			}else {
				//Wenn der Parser herausgenommen ist, seine Tags nicht entfernen.
			}
			
			//ggfs. weitere Sachen rausrechnen, falls gewuenscht
			vecReturn = this.parsePostCustom(vecReturn, bKeepSurroundingSeparators);
			sReturnTag = (String) vecReturn.get(1);
			sReturn = sReturnTag; //muesste ja eigentlich sReturnLine sein, und ein implode des Vektors.
			this.setValue(sReturnTag);
			
		}//end main:
				
		//#################################
		return vecReturn;
	}

	@Override
	public Vector3ZZZ<String> parsePostCustom(Vector3ZZZ<String> vecExpression) throws ExceptionZZZ {
		return vecExpression;
	}


	@Override
	public Vector3ZZZ<String> parsePostCustom(Vector3ZZZ<String> vecExpression, boolean bKeepSurroundingSeparators) throws ExceptionZZZ {
		return vecExpression;
	}
	
	
	/**
	 * Gibt einen Vector zurück, in dem das erste Element der Ausdruck VOR der
	 * ersten 'Expression' ist. Das 2. Element ist die Expression. Das 3. Element
	 * ist der Ausdruck NACH der ersten Expression.
	 * 
	 * @param sLineWithExpression
	 * @throws ExceptionZZZ
	 */
	@Override
	public Vector3ZZZ<String>parseFirstVector(String sExpression) throws ExceptionZZZ{
		Vector3ZZZ<String>vecReturn = new Vector3ZZZ<String>();		
		main:{
			//Bei dem einfachen Tag wird die naechste Tag genommen und dann auch das naechste schliessende Tag...
			vecReturn = StringZZZ.vecMidFirst(sExpression, this.getTagPartOpening(), this.getTagPartClosing(), false, false);
			vecReturn = this.parseFirstVectorPost(vecReturn, false, false);
		}
		return vecReturn;
	}
	
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	
	

	
	
	
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	
	
	
	@Override
	public Vector3ZZZ<String> parseFirstVectorPostCustom(Vector3ZZZ<String> vecExpression) throws ExceptionZZZ {
		return this.parseFirstVectorPostCustom_(vecExpression, true);
	}
	
	@Override
	public Vector3ZZZ<String> parseFirstVectorPostCustom(Vector3ZZZ<String> vecExpression, boolean bKeepSurroundingSeparators) throws ExceptionZZZ {		
		return this.parseFirstVectorPostCustom_(vecExpression, bKeepSurroundingSeparators);

	}
	
	private Vector3ZZZ<String> parseFirstVectorPostCustom_(Vector3ZZZ<String> vecExpressionIn, boolean bKeepSurroundingSeparators) throws ExceptionZZZ{
		Vector3ZZZ<String> vecReturn = vecExpressionIn;
		String sReturn = null;
		String sReturnTag = null;
				
		main:{			
			if(vecExpressionIn==null) break main;
			
			//Das gibt es dann erst in AbstractTagWithExpressionBasicZZZ
			//bUseExpression = this.isExpressionEnabledAny(); 
			//if(!bUseExpression) break main;
							
			sReturnTag = (String) vecExpressionIn.get(1);
			sReturn = sReturnTag;
			if(StringZZZ.isEmpty(sReturnTag)) break main;			
				
			
			//.... hier könnte dann ein echter custom Code in einer Klasse stehen.
			
			sReturn = sReturnTag;
			this.setValue(sReturnTag);											
		}//end main:
				
		//################################
		return vecReturn;
	}
	//+++++++++++++++++++++++++++++++++++++++++++++++
	
	
	
	

	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	//### aus IIniStructurePositionUserZZZ
	@Override
	public IIniStructurePositionZZZ getIniStructurePosition() throws ExceptionZZZ{
		if(this.objIniPosition==null) {
			this.objIniPosition = new IniStructurePositionZZZ();
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
		if(this.objIniPosition==null) return null; //wg. Konstutruktor mit (this) beim Holen von Werten auf null pruefen. 
		return this.getIniStructurePosition().getSection();
	}

	@Override
	public void setSection(String sSection) throws ExceptionZZZ {
		this.getIniStructurePosition().setSection(sSection);
	}

	@Override
	public String getProperty() throws ExceptionZZZ {
		if(this.objIniPosition==null) return null; //wg. Konstutruktor mit (this) beim Holen von Werten auf null pruefen.
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
	public boolean isArrayValue() {
		return this.bArrayValue;
	}
	
	@Override 
	public void isArrayValue(boolean bIsArrayValue) {
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
		
	
	//### Merke: Kein IValueSolvedUserZZZ hier eingebunden, da es keine Expression ist

	
	//### aus IExpressionUserZZZ
	@Override
	public boolean isExpression(String sLineWithExpression) throws ExceptionZZZ {
		return XmlUtilZZZ.isExpression4TagXml(sLineWithExpression, this.getName());
	}	
	
	
	
}
