package basic.zBasic.util.xml.tagexpression;

import java.util.Vector;

import basic.zBasic.AbstractObjectWithExpressionZZZ;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractList.Vector3ZZZ;
import basic.zBasic.util.abstractList.VectorUtilZZZ;
import basic.zBasic.util.datatype.calling.ReferenceZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.datatype.xml.XmlUtilZZZ;
import basic.zKernel.IKernelConfigSectionEntryZZZ;
import basic.zKernel.file.ini.AbstractIniTagSimpleZZZ;
import basic.zKernel.file.ini.IExpressionUserZZZ;
import basic.zKernel.file.ini.IIniTagWithExpressionZZZ;

public abstract class AbstractTagWithExpressionBasicZZZ<T> extends AbstractObjectWithExpressionZZZ<T> implements ITagWithExpressionZZZ{
	private static final long serialVersionUID = -367756957686201953L;

	//Merke: Der Name der Tags wird auf unterschiedliche Weise geholt.
	protected String sTagName = null; //String fuer den Fall, das ein Tag OHNE TagType erstellt wird.	
		
	public AbstractTagWithExpressionBasicZZZ() throws ExceptionZZZ {
		super();
	}  
	
	public AbstractTagWithExpressionBasicZZZ(String sFlagControl) throws ExceptionZZZ {
		super(sFlagControl);
	} 
	
	public AbstractTagWithExpressionBasicZZZ(String[] saFlagControl) throws ExceptionZZZ {
		super(saFlagControl);
	} 

	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	//+++ Details aus ITagBasicsZZZ +++++++++++++++++++++++++++++++++++++++++
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	//#### Aus Interfacace
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
	
	//### Aus IParseEnabledZZZ	
	@Override
	public boolean isParseRelevant(String sExpression) throws ExceptionZZZ {
		boolean bReturn=false;
		main:{
			if(StringZZZ.isEmptyTrimmed(sExpression)) break main;
		
			bReturn = XmlUtilZZZ.containsTag(sExpression, this.getName(), false); //also, kein exact match
			if(bReturn) break main;
			
			
		}//end main
	return bReturn;
	}
	
	@Override
	public String parse(String sLExpression) throws ExceptionZZZ{
		return this.parse_(sLExpression, true);
	}	
	
	@Override
	public String parse(String sExpression, boolean bRemoveSurroundingSeparators) throws ExceptionZZZ{
		return this.parse_(sExpression, bRemoveSurroundingSeparators);
	}	
	
	private String parse_(String sExpression, boolean bRemoveSurroundingSeparators) throws ExceptionZZZ{
		String sReturn = sExpression;
		main:{
			if(!this.getFlag(IIniTagWithExpressionZZZ.FLAGZ.USEEXPRESSION)) break main;
			if(StringZZZ.isEmptyTrimmed(sExpression)) break main;
			
			//Bei einfachen Tags den Ersten Vektor holen
			Vector<String> vecAll = this.parseFirstVector(sExpression, bRemoveSurroundingSeparators);
			if(vecAll!=null) {
				
				//Der zurueckgegebene Wert unterscheidet sich vom Wert des Tags selber.						
				this.setValue(vecAll.get(1));
				sReturn = VectorUtilZZZ.implode(vecAll);
			}									
		}//end main:
		return sReturn;
	}	
	
	/**
	 * Gibt einen Vector zurück, in dem das erste Element der Ausdruck VOR der
	 * ersten 'Expression' ist. Das 2. Element ist die Expression. Das 3. Element
	 * ist der Ausdruck NACH der ersten Expression.
	 * 
	 * @param sExpression
	 * @throws ExceptionZZZ
	 */
	@Override
	public Vector3ZZZ<String>parseFirstVector(String sExpression) throws ExceptionZZZ{
		return this.parseFirstVector_(sExpression, true);
	}
	
	/**
	 * Gibt einen Vector zurück, in dem das erste Element der Ausdruck VOR der
	 * ersten 'Expression' ist. Das 2. Element ist die Expression. Das 3. Element
	 * ist der Ausdruck NACH der ersten Expression.
	 * 
	 * @param sExpression
	 * @throws ExceptionZZZ
	 */
	@Override
	public Vector3ZZZ<String>parseFirstVector(String sExpression, boolean bRemoveSurroundingSeparators) throws ExceptionZZZ{
		return this.parseFirstVector_(sExpression, bRemoveSurroundingSeparators);
	}

	//Nein, auf dieser Ebene ist es ein einfache Tag und kennt IKernelConfigSectionEntryZZZ ueberhaupt nicht.
	//public Vector<String>parseFirstVector(String sLineWithExpression, ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReferenceIn, boolean bRemoveSurroundingSeparators) throws ExceptionZZZ{
		
	private Vector3ZZZ<String>parseFirstVector_(String sExpression, boolean bRemoveSurroundingSeparators) throws ExceptionZZZ{
		Vector3ZZZ<String>vecReturn = new Vector3ZZZ<String>();		
		main:{
			if(StringZZZ.isEmpty(sExpression)) break main;
		
			//Bei dem einfachen Tag wird das naechste oeffnende Tag genommen und dann auch das naechste schliessende Tag...
			vecReturn = StringZZZ.vecMidFirst(sExpression, this.getTagStarting(), this.getTagClosing(), !bRemoveSurroundingSeparators, false);
			if(vecReturn==null)break main;			
			
			String sReturn = VectorUtilZZZ.implode(vecReturn);
			this.setValue(sReturn);
		}
		return vecReturn;
	}
	
	//### aus IExpressionUserZZZ
	@Override
	public boolean isExpression(String sExpression) throws ExceptionZZZ {
		return XmlUtilZZZ.isExpression4TagXml(sExpression, this.getName());
	}	
	
	@Override
	public String parseAsExpression() throws ExceptionZZZ {
		String sExpression = this.getValue();
		return this.parseAsExpression(sExpression);
	}	


	@Override
	public String parseAsExpression(String sExpression) throws ExceptionZZZ{
		String sReturn = sExpression;
		main:{
			if(StringZZZ.isEmptyTrimmed(sExpression)) break main;
			
			Vector3ZZZ<String> vecAll = this.parseFirstVectorAsExpression(sExpression);
			
			//Der Vector ist schon so aufbereiten, dass hier nur noch "zusammenaddiert" werden muss					
			sReturn = VectorUtilZZZ.implode(vecAll);
			this.setValue((String) vecAll.get(1));
			
		}//end main:
		return sReturn;
	}
	
	@Override
	public Vector3ZZZ<String> parseFirstVectorAsExpression(String sExpression) throws ExceptionZZZ{
		Vector3ZZZ<String>vecReturn = new Vector3ZZZ<String>();
		String sReturn = sExpression;
		main:{
			if(!this.getFlag(IIniTagWithExpressionZZZ.FLAGZ.USEEXPRESSION)) break main;
			
			//Bei dem einfachen Tag wird die naechste Tag genommen und dann auch das naechste schliessende Tag...
			//Fuer die EXPRESSION gilt: Es werden die Separatoren zurueckgegeben (mit true)
			vecReturn = StringZZZ.vecMidFirst(sExpression, this.getTagStarting(), this.getTagClosing(), true, false);
		}//end main:
		this.setValue((String) VectorUtilZZZ.implode(vecReturn));
		return vecReturn;	
	}
	
	
	//### aus IExpressionUserZZZ
	@Override
	public Vector3ZZZ<String>parseAllVectorAsExpression(String sExpression) throws ExceptionZZZ{
		Vector3ZZZ<String> vecReturn = new Vector3ZZZ<String>();
		main:{
			if(StringZZZ.isEmpty(sExpression)) break main;
						
			//Merke: Das ist der Fall, das ein Ausdruck NICHT verschachtelt ist
			//       Für verschachtelte Tags muss hier extra was programmiert und diese Methode ueberschrieben werden.
			vecReturn = this.parseFirstVectorAsExpression(sExpression);			
			
		}//end main:
		this.setValue((String) VectorUtilZZZ.implode(vecReturn));
		return vecReturn;
	}

	@Override
	public String convert(String sExpression) throws ExceptionZZZ {
		return sExpression;
	}	
}
