package basic.zBasic.util.xml.tagsimple;

import java.util.Vector;

import basic.zBasic.AbstractObjectWithValueBufferedZZZ;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.abstractList.Vector3ZZZ;
import basic.zBasic.util.abstractList.VectorUtilZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.datatype.xml.XmlUtilZZZ;
import basic.zKernel.file.ini.AbstractIniTagSimpleZZZ;
import basic.zKernel.file.ini.IKernelExpressionIniSolverZZZ;

/** TAGS ohne FlagZZZ Behandlung
 * @param <T> 
 * 
 * @author Fritz Lindhauer, 21.04.2025, 09:56:19
 * 
 */
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
	
	//### Aus IResettableValuesZZZ
	@Override
	public boolean reset() throws ExceptionZZZ{
		super.reset();
		this.sTagName=null;
		return true;
	}
	
	@Override
	public boolean resetValues() throws ExceptionZZZ{
		super.resetValues();
		//Merke sFlagName ist so gesehen kein Value und wird nicht zurueckgesetzt.
		return true;
	}
	
		
	//######## Getter / Setter ##################
	
	//### Aus IParseEnabledZZZ
	@Override 
	public boolean isParserEnabledGeneral() throws ExceptionZZZ{
		return true;
	}
	
	@Override
	public boolean isParseRelevant() throws ExceptionZZZ{
		return true;
	}
	
	@Override
	public boolean isParseRelevant(String sExpression) throws ExceptionZZZ {
		boolean bReturn=false;
		main:{
			if(StringZZZ.isEmpty(sExpression)) break main;
		
			bReturn = this.isParseRelevant();
			if(!bReturn) break main;
			
			bReturn = XmlUtilZZZ.containsTagName(sExpression, this.getName(), false); //also, kein exact match
			if(!bReturn) break main;
			
			
		}//end main
	return bReturn;
	}
	
	@Override
	public String parse(String sExpression) throws ExceptionZZZ {
		return this.parse_(sExpression, true, true);
	}
	
	@Override
	public String parse(String sExpression, boolean bKeepSurroundingSeparatorsOnParse) throws ExceptionZZZ {
		return this.parse_(sExpression, bKeepSurroundingSeparatorsOnParse, true);
	}
	
	@Override
	public String parse(String sExpression, boolean bKeepSurroundingSeparatorsOnParse, boolean bIgnoreCase) throws ExceptionZZZ {
		return this.parse_(sExpression, bKeepSurroundingSeparatorsOnParse, bIgnoreCase);
	}

	private String parse_(String sExpressionIn, boolean bKeepSurroundingSeparatorsOnParse, boolean bIgnoreCase) throws ExceptionZZZ {
		String sReturnLine = sExpressionIn;		
		main:{
			if(StringZZZ.isEmpty(sExpressionIn)) break main;
			
			String sExpression = sExpressionIn;
			
			Vector3ZZZ<String> vecReturn = this.parseFirstVector(sExpression, bKeepSurroundingSeparatorsOnParse, bIgnoreCase);
			if(vecReturn==null) break main;
			if(StringZZZ.isEmpty((String)vecReturn.get(1))) break main; //Dann ist der Tag nicht enthalten und es darf(!) nicht weitergearbeitet werden.
			
			String sReturnTag = (String) vecReturn.get(1); //uebernimm also das 1. Element
			this.setValue(sReturnTag);
			
			
			vecReturn = this.parsePost(vecReturn, bKeepSurroundingSeparatorsOnParse);
			if(vecReturn==null) break main;
			sReturnTag = (String) vecReturn.get(1); //uebernimm also das nun ggfs. auch leere 1. Element
			this.setValue(sReturnTag);
			
			sReturnLine = VectorUtilZZZ.implode(vecReturn); //gib den gesamtstring mit einer ggfs. erfolgten Uebearbeitung zurueck.								
		}//end main: 
		return sReturnLine;
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
		return this.parseFirstVector_(sExpression, true, true);
	}
	
	@Override
	public Vector3ZZZ<String>parseFirstVector(String sExpression, boolean bKeepSurroundingSeparatorsOnParse) throws ExceptionZZZ{
		return this.parseFirstVector_(sExpression, bKeepSurroundingSeparatorsOnParse, true);
	}
	
	@Override
	public Vector3ZZZ<String>parseFirstVector(String sExpression, boolean bKeepSurroundingSeparatorsOnParse, boolean bIgnoreCase) throws ExceptionZZZ{
		return this.parseFirstVector_(sExpression, bKeepSurroundingSeparatorsOnParse, bIgnoreCase);
	}
	
	
	/**
	 * Gibt einen Vector zurück, in dem das erste Element der Ausdruck VOR der
	 * ersten 'Expression' ist. Das 2. Element ist die Expression. Das 3. Element
	 * ist der Ausdruck NACH der ersten Expression.
	 * 
	 * @param sExpression
	 * @throws ExceptionZZZ
	 */
	private Vector3ZZZ<String>parseFirstVector_(String sExpressionIn, boolean bKeepSurroundingSeparatorsOnParse, boolean bIgnoreCase) throws ExceptionZZZ{
		Vector3ZZZ<String>vecReturn = new Vector3ZZZ<String>();

		main:{			
			//Bei dem einfachen Tag wird das naechste oeffnende Tag genommen und dann auch das naechste schliessende Tag...
			vecReturn = XmlUtilZZZ.parseFirstVector(sExpressionIn, this.getTagPartOpening(), this.getTagPartClosing(), bKeepSurroundingSeparatorsOnParse);
			if(vecReturn==null) break main;
			if(StringZZZ.isEmpty((String)vecReturn.get(1))) break main; //Dann ist der Tag nicht enthalten und es darf(!) nicht weitergearbeitet werden.
			
			String sTag = vecReturn.getEntry(1);//uebernimm also das nun ggfs. auch leere 1. Element
			this.setValue(sTag);
						
			//Als echten Ergebniswert aber die <Z>-Tags und den eigenen Tag rausrechnen, falls gewuenscht
			vecReturn = this.parseFirstVectorPost(vecReturn, bKeepSurroundingSeparatorsOnParse);	
			if(vecReturn==null) break main;
			
			sTag = vecReturn.getEntry(1);//uebernimm also das nun ggfs. auch leere 1. Element
			this.setValue(sTag);
		}
		return vecReturn;
	}
	
	@Override
	public Vector3ZZZ<String> parseFirstVectorPost(Vector3ZZZ<String> vecExpression) throws ExceptionZZZ {
		return this.parseFirstVectorPost_(vecExpression, true, true);
	}

	@Override
	public Vector3ZZZ<String> parseFirstVectorPost(Vector3ZZZ<String> vecExpression, boolean bKeepSurroundingSeparatorsOnParse) throws ExceptionZZZ {
		return this.parseFirstVectorPost_(vecExpression, bKeepSurroundingSeparatorsOnParse, true);
	}
	
	@Override
	public Vector3ZZZ<String> parseFirstVectorPost(Vector3ZZZ<String> vecExpression, boolean bKeepSurroundingSeparatorsOnParse, boolean bRemoveOwnTagParts) throws ExceptionZZZ {		
		return this.parseFirstVectorPost_(vecExpression, bKeepSurroundingSeparatorsOnParse, bRemoveOwnTagParts);		
	}
	
	
	private Vector3ZZZ<String> parseFirstVectorPost_(Vector3ZZZ<String> vecExpressionIn, boolean bKeepSurroundingSeparatorsOnParse, boolean bRemoveOwnTagParts) throws ExceptionZZZ {
		Vector3ZZZ<String>vecReturn = vecExpressionIn;
		String sReturnTag=null;
		main:{
			vecReturn = this.parseFirstVectorPostCustom(vecExpressionIn, bKeepSurroundingSeparatorsOnParse);
			sReturnTag = (String) vecReturn.get(1);
			this.setValue(sReturnTag);		
		}
		return vecReturn;
	}
	
	
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		@Override
		public Vector3ZZZ<String> parseFirstVectorPostCustom(Vector3ZZZ<String> vecExpression) throws ExceptionZZZ {
			return this.parseFirstVectorPostCustom_(vecExpression, true);
		}
		
		@Override
		public Vector3ZZZ<String> parseFirstVectorPostCustom(Vector3ZZZ<String> vecExpression, boolean bKeepSurroundingSeparatorsOnParse) throws ExceptionZZZ {		
			return this.parseFirstVectorPostCustom_(vecExpression, bKeepSurroundingSeparatorsOnParse);
		}
		
		//Methode ohne Refernce-Objekt
		private Vector3ZZZ<String> parseFirstVectorPostCustom_(Vector3ZZZ<String> vecExpressionIn, boolean bKeepSurroundingSeparatorsOnParse) throws ExceptionZZZ{
			Vector3ZZZ<String> vecReturn = vecExpressionIn;
			String sReturn = null;
			String sReturnTag = null;
			String sExpressionIn = null;		
			
			main:{			
				if(vecExpressionIn==null) break main;
		
				//Erst bei AbstractTagWithExpression basic an oder ausstellen mit Flag
				//bUseExpression = this.isExpressionEnabledGeneral(); 
				//if(!bUseExpression) break main;
						
				sReturnTag = (String) vecExpressionIn.get(1);
				sReturn = sReturnTag;
				this.setValue(sReturnTag);
				if(StringZZZ.isEmpty(sReturnTag)) break main;			
				
			
				//.... hier könnte dann ein echter custom Code in einer Klasse stehen.
				
				sReturn = sReturnTag;
				this.setValue(sReturnTag);											
			}//end main:
					
			//################################
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
	public String getTagPartOpening() throws ExceptionZZZ{
		String sTagName = this.getName();
		if(StringZZZ.isEmptyTrimmed(sTagName)) {
			ExceptionZZZ ez = new ExceptionZZZ( "Missing TagName.", iERROR_PARAMETER_MISSING, AbstractIniTagSimpleZZZ.class, ReflectCodeZZZ.getMethodCurrentName()); 
			throw ez;
		}
		return XmlUtilZZZ.computeTagPartOpening(sTagName);
	}
	
	@Override
	public String getTagPartClosing() throws ExceptionZZZ{
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
		return XmlUtilZZZ.computeTagEmpty(sTagName);
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
			
			sReturn = this.getTagPartOpening() + sValue + this.getTagPartClosing();
		}
		return sReturn;
	}
}
