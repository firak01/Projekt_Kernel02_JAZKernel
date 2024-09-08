package basic.zBasic.util.xml.tagsimple;

import java.util.Vector;

import basic.zBasic.AbstractObjectWithValueBufferedZZZ;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractList.VectorZZZ;
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

	//### aus IParseEnabledZZZ
	@Override
	public boolean isParseRelevant(String sLineWithExpression) throws ExceptionZZZ {
		boolean bReturn=false;
		main:{
			if(StringZZZ.isEmptyTrimmed(sLineWithExpression)) break main;
		
			bReturn = XmlUtilZZZ.containsTag(sLineWithExpression, this.getName(), false); //also, kein exact match
			if(bReturn) break main;
			
			
		}//end main
	return bReturn;
	}
	
	@Override
	public String parse(String sLineWithExpression) throws ExceptionZZZ {
		return this.parse(sLineWithExpression, true);
	}
	
	@Override
	public String parse(String sLineWithExpression, boolean bRemoveSurroundingSeparators) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			Vector<String> vecParse = this.parseFirstVector(sLineWithExpression, bRemoveSurroundingSeparators);
			if(vecParse!=null) {
				this.setValue(vecParse.get(1)); //uebernimm also das 1. Element			
				sReturn = VectorZZZ.implode(vecParse); //gib den gesamtstring mit einer ggfs. erfolgten Uebearbeitung zurueck.
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
	public Vector<String>parseFirstVector(String sLineWithExpression) throws ExceptionZZZ{
		return this.parseFirstVector(sLineWithExpression, true);
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
	public Vector<String>parseFirstVector(String sLineWithExpression, boolean bRemoveSurroundingSeparators) throws ExceptionZZZ{
		Vector<String>vecReturn = new Vector<String>();		
		main:{
			//Bei dem einfachen Tag wird das naechste oeffnende Tag genommen und dann auch das naechste schliessende Tag...
			vecReturn = StringZZZ.vecMidFirst(sLineWithExpression, this.getTagStarting(), this.getTagClosing(), !bRemoveSurroundingSeparators, false);
			this.setValue(vecReturn.get(1));
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
