package basic.zKernel.file.ini;

import java.util.ArrayList;

import basic.zBasic.AbstractObjectWithValueZZZ;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IValueArrayUserZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractList.VectorExtendedDifferenceZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.datatype.xml.XmlUtilZZZ;
import basic.zBasic.util.file.ini.IIniStructurePositionZZZ;
import basic.zBasic.util.file.ini.IniStructurePositionZZZ;
import basic.zBasic.util.xml.tagexpression.AbstractTagWithExpressionBasicZZZ;
import basic.zBasic.util.xml.tagsimple.AbstractTagParseEnabledZZZ;
import basic.zBasic.util.xml.tagsimple.ITagBasicZZZ;
import basic.zKernel.IKernelConfigSectionEntryZZZ;
import basic.zKernel.KernelConfigSectionEntryZZZ;
import basic.zKernel.file.ini.AbstractIniTagSimpleZZZ;

//DIES IST DER ERG OHNE FLAG - HANDLING
//UND OHNE einen IKernelConfigSectionEntryZZZ als interne Variable. D.h. die einzelnen Variablen müssen direkt hier gespeichert werden.
public abstract class AbstractIniTagBasicZZZ<T> extends AbstractTagParseEnabledZZZ<T> implements IIniTagBasicZZZ{			
	private static final long serialVersionUID = -3411751655174978836L;

	//aus IIniStructurePositionUserZZZ
	protected IIniStructurePositionZZZ objIniPosition;
	
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
		return this.getIniStructurePosition().getSection();
	}

	@Override
	public void setSection(String sSection) throws ExceptionZZZ {
		this.getIniStructurePosition().setSection(sSection);
	}

	@Override
	public String getProperty() throws ExceptionZZZ {
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
