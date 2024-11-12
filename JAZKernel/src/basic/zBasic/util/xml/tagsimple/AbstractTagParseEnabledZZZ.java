package basic.zBasic.util.xml.tagsimple;

import java.util.Vector;

import basic.zBasic.AbstractObjectWithValueBufferedZZZ;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractList.Vector3ZZZ;
import basic.zBasic.util.abstractList.VectorUtilZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.datatype.xml.XmlUtilZZZ;
import basic.zKernel.file.ini.AbstractIniTagSimpleZZZ;

public abstract class AbstractTagParseEnabledZZZ<T> extends AbstractObjectWithValueBufferedZZZ<T> implements IParseEnabledZZZ, ITagBasicZZZ{			
	private static final long serialVersionUID = -3411751655174978836L;
	
	//Merke: Der Name der Tags wird auf unterschiedliche Weise geholt.
	protected String sTagName = null; //String fuer den Fall, das ein Tag OHNE TagType erstellt wird.	
		
	
	public AbstractTagParseEnabledZZZ() throws ExceptionZZZ{
		super();		
	}
	
	public AbstractTagParseEnabledZZZ(String sName, String sValue) throws ExceptionZZZ{
		super(sValue);
		AbstractTagParseEnabledNew_(sName);
	}
	
	private void AbstractTagParseEnabledNew_(String sName) throws ExceptionZZZ{
		main:{
			if(StringZZZ.isEmpty(sName)){
				ExceptionZZZ ez = new ExceptionZZZ("TagName", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}

			this.setName(sName);			
		}//End main
	}
	
		
	//######## Getter / Setter ##################
	
	//### Aus IParseEnabledZZZ
	@Override
	public boolean isParseRelevant() throws ExceptionZZZ{
		return true;
	}
	
	@Override
	public boolean isParse(String sExpression) throws ExceptionZZZ {
		boolean bReturn=false;
		main:{
			if(StringZZZ.isEmptyTrimmed(sExpression)) break main;
		
			bReturn = XmlUtilZZZ.containsTag(sExpression, this.getName(), false); //also, kein exact match
			if(bReturn) break main;
			
			
		}//end main
	return bReturn;
	}
	
	@Override
	public String parse(String sExpression) throws ExceptionZZZ {
		return this.parse(sExpression, true);
	}
	
	@Override
	public String parse(String sExpression, boolean bRemoveSurroundingSeparators) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			Vector<String> vecParse = this.parseFirstVector(sExpression, bRemoveSurroundingSeparators);
			if(vecParse!=null) {
				this.setValue(vecParse.get(1)); //uebernimm also das 1. Element			
				sReturn = VectorUtilZZZ.implode(vecParse); //gib den gesamtstring mit einer ggfs. erfolgten Uebearbeitung zurueck.
			}
		}
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
	public Vector3ZZZ<String>parseFirstVector(String sExpression) throws ExceptionZZZ{
		return this.parseFirstVector(sExpression, true);
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
	public Vector3ZZZ<String>parseFirstVector(String sExpression, boolean bRemoveSurroundingSeparators) throws ExceptionZZZ{
		Vector3ZZZ<String>vecReturn = new Vector3ZZZ<String>();		
		main:{
			//Bei dem einfachen Tag wird das naechste oeffnende Tag genommen und dann auch das naechste schliessende Tag...
			vecReturn = XmlUtilZZZ.parseFirstVector(sExpression, this.getTagStarting(), this.getTagClosing(), bRemoveSurroundingSeparators);
			this.setValue((String) vecReturn.get(1));
		}
		return vecReturn;
	}
	
	//### Analog zu AbstractTagBasicZZZ
	//### aus ITagBasicZZZ
	//Merke: Der Name wird auf unterschiedliche Arten geholt. Z.B. aus dem TagTypeZZZ, darum diese Methode dann ueberschreiben.
	@Override
	public String getName() throws ExceptionZZZ{
		if(this.sTagName==null) {
			return this.getNameDefault();
		}else {
			return this.sTagName;
		}
	}	
	
	//Merke: Der Default-Tagname wird in einer Konstanten in der konkreten Klasse verwaltet.
	//Merke: Erst ab Java 8 können static Ausdrücke in ein interface	
	@Override
	public abstract String getNameDefault() throws ExceptionZZZ; 	
	
	@Override
	public void setName(String sTagName) throws ExceptionZZZ{
		if(StringZZZ.isEmptyTrimmed(sTagName)) {
			ExceptionZZZ ez = new ExceptionZZZ( "Missing TagName.", iERROR_PARAMETER_MISSING, AbstractIniTagSimpleZZZ.class, ReflectCodeZZZ.getMethodCurrentName()); 
			throw ez;
		}
		this.sTagName = sTagName;		
	}
			
	@Override
	public String getTagStarting() throws ExceptionZZZ{
		String sTagName = this.getName();
		if(StringZZZ.isEmptyTrimmed(sTagName)) {
			ExceptionZZZ ez = new ExceptionZZZ( "Missing TagName.", iERROR_PARAMETER_MISSING, AbstractIniTagSimpleZZZ.class, ReflectCodeZZZ.getMethodCurrentName()); 
			throw ez;
		}
		return XmlUtilZZZ.computeTagPartStarting(sTagName);
	}
	
	@Override
	public String getTagClosing() throws ExceptionZZZ{
		String sTagName = this.getName();
		if(StringZZZ.isEmptyTrimmed(sTagName)) {
			ExceptionZZZ ez = new ExceptionZZZ( "Missing TagName.", iERROR_PARAMETER_MISSING, AbstractIniTagSimpleZZZ.class, ReflectCodeZZZ.getMethodCurrentName()); 
			throw ez;
		}
		return XmlUtilZZZ.computeTagPartClosing(sTagName);
	}	
	
	@Override
	public String getTagEmpty() throws ExceptionZZZ{
		String sTagName = this.getName();
		if(StringZZZ.isEmptyTrimmed(sTagName)) {
			ExceptionZZZ ez = new ExceptionZZZ( "Missing TagName.", iERROR_PARAMETER_MISSING, AbstractIniTagSimpleZZZ.class, ReflectCodeZZZ.getMethodCurrentName()); 
			throw ez;
		}
		return XmlUtilZZZ.computeTagPartEmpty(sTagName);
	}	
	
	@Override
	public String getEmpty() throws ExceptionZZZ{
		return this.getTagEmpty();
	}	
	
	@Override 
	public String getElementString() throws ExceptionZZZ{
		String sReturn = null;
		main:{
			String sValue = this.getValue();
			if(sValue==null) {
				sReturn = this.getEmpty();
				break main;
			}
			
			sReturn = this.getTagStarting() + sValue + this.getTagClosing();
		}
		return sReturn;
	}
}
