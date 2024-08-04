package basic.zKernel.file.ini;

import java.util.ArrayList;
import java.util.Vector;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractList.ArrayListExtendedZZZ;
import basic.zBasic.util.abstractList.VectorExtendedDifferenceZZZ;
import basic.zBasic.util.abstractList.VectorZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.file.ini.IIniStructureConstantZZZ;
import basic.zBasic.util.file.ini.IIniStructurePositionZZZ;
import basic.zBasic.util.file.ini.IniStructurePositionZZZ;
import basic.zBasic.util.xml.tagsimple.AbstractTagParseEnabledZZZ;
import basic.zKernel.IKernelConfigSectionEntryZZZ;
import basic.zKernel.KernelConfigSectionEntryZZZ;

//DIES IST DER ERG OHNE FLAG - HANDLING
//UND OHNE einen IKernelConfigSectionEntryZZZ als interne Variable. D.h. die einzelnen Variablen müssen direkt hier gespeichert werden.
public abstract class AbstractIniTagBasicZZZ<T> extends AbstractTagParseEnabledZZZ<T> implements IIniTagBasicZZZ{			
	private static final long serialVersionUID = -3411751655174978836L;

	//aus IIniStructurePositionUserZZZ
	protected IIniStructurePositionZZZ objIniPosition=null;
	
	//Merke: Array erst auf Ini - Ebene behandeln, hier kann ein Separator String vewendet werden.
	//aus IValueArrayUserZZZ
	protected VectorExtendedDifferenceZZZ<ArrayList<String>> vecalValue = new VectorExtendedDifferenceZZZ<ArrayList<String>>();
	protected boolean bArrayValue = false; //Falls eine ArrayList gesetzt wurde.
	
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
	
			
	//######## Getter / Setter #################
	
	//### aus IIniTagBasicZZZ
	@Override
	public IKernelConfigSectionEntryZZZ parseAsEntry(String sLineWithExpression) throws ExceptionZZZ{
		IKernelConfigSectionEntryZZZ objReturn = null;
		main:{
			if(StringZZZ.isEmptyTrimmed(sLineWithExpression)) break main;
											
			Vector<String>vecAll = this.parseAllVector(sLineWithExpression);
			
			//Das ist bei einfachen Tag Werten so
			String sReturn = (String) vecAll.get(1);
			this.setValue(sReturn); 
			
			objReturn = new KernelConfigSectionEntryZZZ(this);			
		}//end main:
		return objReturn;
	}	
	
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
//				ExceptionZZZ ez = new ExceptionZZZ("Delimiter for Array Values", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
//				throw ez;
			}else {
				sDelimiter = sDelimiterIn;
			}
			
			Vector<String> vecParsed = this.parseFirstVector(sLineWithExpression);
			String sParsed = this.getValue();
			
			String[] saParsed = StringZZZ.explode(sParsed, sDelimiter); //Dann löse Ihn als Mehrfachwert auf.
			ArrayListExtendedZZZ<String> listasValue = new ArrayListExtendedZZZ<String>(saParsed);
			this.setValue(listasValue);		
			
			//Fasse nun zusammen.
			listasValue.add(0, vecParsed.get(0));
			listasValue.add(vecParsed.get(2));
				
			saReturn = listasValue.toStringArray();				
		}//end main:
		return saReturn;
	}
	
	//### Aus IParseEnabled
	@Override
	public String parse(String sLineWithExpression) throws ExceptionZZZ{
		String sReturn = sLineWithExpression;
		main:{
			if(StringZZZ.isEmptyTrimmed(sLineWithExpression)) break main;
				
			//Bei einfachen Tags den Ersten Vektor holen
			Vector<String> vecAll = this.parseFirstVector(sLineWithExpression);
			
			//Bei einfachen Tags, den Wert zurückgeben
			sReturn = (String) vecAll.get(1);
			this.setValue(sReturn);
			
			//implode NUR bei CASCADED Tags, NEIN: Es koennen ja einfache String vor- bzw. nachstehend sein.
			String sExpressionImploded = VectorZZZ.implode(vecAll);
			sReturn = sExpressionImploded; //Der zurückgegebene Wert unterscheide sich also von dem Wert des Tags!!!
		}//end main:
		return sReturn;
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
	public Vector<String>parseFirstVector(String sLineWithExpression) throws ExceptionZZZ{
		Vector<String>vecReturn = new Vector<String>();		
		main:{
			//Bei dem einfachen Tag wird die naechste Tag genommen und dann auch das naechste schliessende Tag...
			vecReturn = StringZZZ.vecMidFirst(sLineWithExpression, this.getTagStarting(), this.getTagClosing(), false, false);
		}
		return vecReturn;
	}
	
	@Override
	public Vector<String>parseAllVector(String sLineWithExpression) throws ExceptionZZZ{
		Vector<String> vecReturn = new Vector<String>();
		main:{
			if(StringZZZ.isEmpty(sLineWithExpression)) break main;
						
			//Merke: Das ist der Fall, das ein Ausdruck NICHT verschachtelt ist
			//       Für verschachtelte Tags muss hier extra was programmiert und diese Methode ueberschrieben werden.
			vecReturn = this.parseFirstVector(sLineWithExpression);			
			
		}
		return vecReturn;
	}
	
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
	public VectorExtendedDifferenceZZZ<ArrayList<String>> getValueArrayListVector() throws ExceptionZZZ{
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
	
	//### Merke: Kein IValueSolvedUserZZZ hier eingebunden, da es keine Expression ist

	
	//### aus IExpressionUserZZZ
	@Override
	public boolean isExpression(String sLineWithExpression) throws ExceptionZZZ {
		return ComputableExpressionUtilZZZ.isExpression4TagXml(sLineWithExpression, this.getName());
	}	
}
