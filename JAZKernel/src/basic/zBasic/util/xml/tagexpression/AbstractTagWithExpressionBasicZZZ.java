package basic.zBasic.util.xml.tagexpression;

import java.util.Vector;

import basic.zBasic.AbstractObjectWithExpressionZZZ;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractList.VectorZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.datatype.xml.XmlUtilZZZ;
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
	public boolean isParseRelevant(String sExpressionToProof) throws ExceptionZZZ {
		boolean bReturn=false;
		main:{
			if(StringZZZ.isEmptyTrimmed(sExpressionToProof)) break main;
		
			bReturn = XmlUtilZZZ.containsTag(sExpressionToProof, this.getName(), false); //also, kein exact match
			if(bReturn) break main;
			
			
		}//end main
	return bReturn;
	}
	
	@Override
	public String parse(String sLineWithExpression) throws ExceptionZZZ{
		return this.parse(sLineWithExpression, true);
	}	
	
	@Override
	public String parse(String sLineWithExpression, boolean bRemoveSurroundingSeparators) throws ExceptionZZZ{
		String sReturn = sLineWithExpression;
		main:{
			if(!this.getFlag(IIniTagWithExpressionZZZ.FLAGZ.USEEXPRESSION)) break main;
			if(StringZZZ.isEmptyTrimmed(sLineWithExpression)) break main;
			
			//Bei einfachen Tags den Ersten Vektor holen
			Vector<String> vecAll = this.parseFirstVector(sLineWithExpression, bRemoveSurroundingSeparators);
			if(vecAll!=null) {
				
				//Der zurueckgegebene Wert unterscheidet sich vom Wert des Tags selber.						
				this.setValue(vecAll.get(1));
				sReturn = VectorZZZ.implode(vecAll);
			}									
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
			sReturn = VectorZZZ.implode(vecAll);
			this.setValue(vecAll.get(1));
			
		}//end main:
		return sReturn;
	}
	
	@Override
	public Vector<String> parseFirstVectorAsExpression(String sLineWithExpression) throws ExceptionZZZ{
		Vector<String>vecReturn = new Vector<String>();
		String sReturn = sLineWithExpression;
		main:{
			if(!this.getFlag(IIniTagWithExpressionZZZ.FLAGZ.USEEXPRESSION)) break main;
			
			//Bei dem einfachen Tag wird die naechste Tag genommen und dann auch das naechste schliessende Tag...
			//Fuer die EXPRESSION gilt: Es werden die Separatoren zurueckgegeben (mit true)
			vecReturn = StringZZZ.vecMidFirst(sLineWithExpression, this.getTagStarting(), this.getTagClosing(), true, false);
		}//end main:
		
		//NUN DEN INNERHALB DER SOLVE BERECHNUNG ERSTELLTEN WERT in den Return-Vector übernehmen
		if(vecReturn.size()>=2) vecReturn.removeElementAt(1);
		if(!StringZZZ.isEmpty(sReturn)){
			vecReturn.add(1, sReturn);
		}else {
			vecReturn.add(1, "");
		}
		this.setValue(vecReturn.get(1));
		return vecReturn;	
	}
	
	
	//### aus IExpressionUserZZZ
	@Override
	public Vector<String>parseAllVectorAsExpression(String sLineWithExpression) throws ExceptionZZZ{
		Vector<String> vecReturn = new Vector<String>();
		main:{
			if(StringZZZ.isEmpty(sLineWithExpression)) break main;
						
			//Merke: Das ist der Fall, das ein Ausdruck NICHT verschachtelt ist
			//       Für verschachtelte Tags muss hier extra was programmiert und diese Methode ueberschrieben werden.
			vecReturn = this.parseFirstVectorAsExpression(sLineWithExpression);			
			
		}
		return vecReturn;
	}

	@Override
	public String convert(String sLine) throws ExceptionZZZ {
		return sLine; //Also was reinkommt wieder rausgeben, keine spezielle Konvertierung. Die muss in der erbenden Klasse implementiert werden.
	}
}
