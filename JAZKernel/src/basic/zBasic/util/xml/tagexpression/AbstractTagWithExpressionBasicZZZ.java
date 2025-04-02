package basic.zBasic.util.xml.tagexpression;

import java.util.Vector;

import basic.zBasic.AbstractObjectWithExpressionZZZ;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IObjectWithExpressionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractList.Vector3ZZZ;
import basic.zBasic.util.abstractList.VectorDifferenceZZZ;
import basic.zBasic.util.abstractList.VectorUtilZZZ;
import basic.zBasic.util.datatype.calling.ReferenceZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.datatype.xml.XmlUtilZZZ;
import basic.zKernel.IKernelConfigSectionEntryZZZ;
import basic.zKernel.KernelConfigSectionEntryZZZ;
import basic.zKernel.config.KernelConfigSectionEntryUtilZZZ;
import basic.zKernel.file.ini.AbstractIniTagSimpleZZZ;
import basic.zKernel.file.ini.ExpressionIniUtilZZZ;
import basic.zKernel.file.ini.IExpressionUserZZZ;
import basic.zKernel.file.ini.IKernelEncryptionIniSolverZZZ;
import basic.zKernel.file.ini.IKernelExpressionIniSolverZZZ;
import basic.zKernel.file.ini.KernelEncryption_CodeZZZ;

public abstract class AbstractTagWithExpressionBasicZZZ<T> extends AbstractObjectWithExpressionZZZ<T> implements ITagWithExpressionZZZ{
	private static final long serialVersionUID = -367756957686201953L;

	//Merke: Der Name der Tags wird auf unterschiedliche Weise geholt.
	protected String sTagName = null; //String fuer den Fall, das ein Tag OHNE TagType erstellt wird.	
	
	//+++ fuer IValueBufferedUserZZZ
	protected VectorDifferenceZZZ<String> vecValue = null;
	protected boolean bAnyValueInObjectWithExpression = false;
	protected boolean bNullValueInObjectWithExpression = false;

	
	public AbstractTagWithExpressionBasicZZZ() throws ExceptionZZZ {
		super();
	}  
	
	public AbstractTagWithExpressionBasicZZZ(String sFlagControl) throws ExceptionZZZ {
		super(sFlagControl);
	} 
	
	public AbstractTagWithExpressionBasicZZZ(String[] saFlagControl) throws ExceptionZZZ {
		super(saFlagControl);
	} 
	
	@Override
	public boolean resetValues() throws ExceptionZZZ{
		super.resetValues();
		this.bAnyValueInObjectWithExpression=false;
		this.bNullValueInObjectWithExpression=false;
		if(this.vecValue!=null) this.vecValue.clear();
		return true;
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
	
	
	
		
	//### Aus IValueBufferedUserZZZ
	@Override 
	public VectorDifferenceZZZ<String> getValueVector() throws ExceptionZZZ{
		if(this.vecValue==null) {
			this.vecValue = new VectorDifferenceZZZ<String>();
		}
		return this.vecValue;
	}
	
	//Merke: Muss wg. dem Vector als Buffer ueberschrieben werden
	@Override
	public String getValue() throws ExceptionZZZ {
		if(this.hasNullValue()){
			return null;		
		}else if (!this.hasAnyValue()){
			return null; //wenn die Section nicht existiert, dann auch kein Wert.			
		}else {
			return this.getValueVector().getEntryHigh();
		}
	}

	//Merke: Muss wg. dem Vector als Buffer ueberschrieben werden
	@Override
	public void setValue(String sValue) throws ExceptionZZZ {
		
		this.hasAnyValue(true);
		this.getValueVector().add(sValue);	
		if(sValue!=null){		
			this.hasNullValue(false);
		}else{
			this.hasNullValue(true);
		}		
	}

	@Override
	public boolean hasAnyValue() throws ExceptionZZZ {
		return this.bAnyValueInObjectWithExpression;
	}	
	
	//Wird beim Setzen des Werts automatisch mit gesetzt. Also nicht "von aussen" setzbar
	//daher protected. Was nicht im Intface definierbar ist.
	@Override
	public void hasAnyValue(boolean bAnyValue) throws ExceptionZZZ {
		this.bAnyValueInObjectWithExpression=bAnyValue;
	}
	
	@Override
	public boolean hasNullValue() throws ExceptionZZZ {
		return this.bNullValueInObjectWithExpression;
	}
	//Wird beim Setzen des Werts automatisch mit gesetzt. Also nicht "von aussen" setzbar, 
	//daher protected. Was nicht im Interface definierbar ist.
	@Override
	public void hasNullValue(boolean bNullValue) {
		this.bNullValueInObjectWithExpression=bNullValue;
	}
	
	//####################################################
	//### Aus IValueComputedBufferedUserZZZ
	@Override 
	public VectorDifferenceZZZ<String> getRawVector() throws ExceptionZZZ{
		if(this.vecRaw==null) {
			this.vecRaw = new VectorDifferenceZZZ<String>();
		}
		return this.vecRaw;
	}
	
	@Override
	public String getRaw() throws ExceptionZZZ {
		return this.getRawVector().getEntryLow();//anders als bei den anderen Strings und Vectoren hier den .Lows() zurueckgeben
	}

	@Override
	public void setRaw(String sRaw) throws ExceptionZZZ {
		this.getRawVector().add(sRaw);
	}

	
	//### Aus IObjectWithExpression
	
			
	
	//### Aus IParseEnabledZZZ
	
	@Override
	public abstract boolean isParserEnabledThis() throws ExceptionZZZ;
	
	@Override
	public boolean isParseRelevant() {
		//Kann ggfs. von einem konkreten Tag uberschrieben werden.
		return true;
	}
			
//			@Override
//			public boolean isParseRelevant(String sExpressionToProof) throws ExceptionZZZ{
//				boolean bReturn = false;
//				main:{
//					bReturn = this.isParseRelevant();
//					if(!bReturn)break main;
//					
//					//auf dieser Ebene gibt es keine Tags
//					//bReturn = XmlUtilZZZ.containsTag(sExpression, this.getTagName(());
//				}//end main:
//				return bReturn;
//			}
	
	@Override
	public boolean isParseRelevant(String sExpression) throws ExceptionZZZ {
		boolean bReturn=false;
		main:{
			if(StringZZZ.isEmpty(sExpression)) break main;
			
			bReturn = this.isParseRelevant();
			if(!bReturn) break main;
			
			//Merke: Das waere der Ausdruck für Tags, die nicht dem nomalen XML-Tag-Konzept entsprechen.
			//bReturn = ExpressionIniUtilZZZ.isParseRegEx(sExpression, this.getName(), false);
			
			//Fuer Tags, die dem nomalen XML-Tag-Konzept entsprechen.
			bReturn = ExpressionIniUtilZZZ.isParse(sExpression, sExpression, false);
			
			//Auf dieser Ebene gibt es dann erstmals Tags
			bReturn = XmlUtilZZZ.containsTagName(sExpression, this.getName(), false); //also, kein exact match
			if(!bReturn) break main;
			
		}//end main
		return bReturn;
	}
	
	
	@Override
	public String parse(String sLExpression) throws ExceptionZZZ{
		return this.parse_(sLExpression, true);
	}	
	
	@Override
	public String parse(String sExpression, boolean bKeepSurroundingSeparatorsOnParse) throws ExceptionZZZ{
		return this.parse_(sExpression, bKeepSurroundingSeparatorsOnParse);
	}	
	
	private String parse_(String sExpressionIn, boolean bKeepSurroundingSeparatorsOnParse) throws ExceptionZZZ{
		String sReturnLine = sExpressionIn;
		String sReturnTag = "";
		main:{			
			if(StringZZZ.isEmptyTrimmed(sExpressionIn)) break main;
			
			boolean bUseExpression = this.getFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION); 
			if(!bUseExpression) break main;
							
			//Falls man diesen Tag aus dem Parsen (des Gesamtstrings) rausnimmt, muessen die umgebenden Tags drin bleiben
			boolean bUseParse = this.isParserEnabledThis();
			if(!bUseParse) break main;
			
			//Bei einfachen Tags den Ersten Vektor holen
			String sExpression = sExpressionIn;
			Vector3ZZZ<String> vecReturn = this.parseFirstVector(sExpression, bKeepSurroundingSeparatorsOnParse);
			if(vecReturn==null) break main;
			if(StringZZZ.isEmpty((String)vecReturn.get(1))) break main; //Dann ist der Tag nicht enthalten und es darf(!) nicht weitergearbeitet werden.
			
			sReturnTag = (String) vecReturn.get(1);
			this.setValue(sReturnTag);
			
			vecReturn = this.parsePost(vecReturn, bKeepSurroundingSeparatorsOnParse);
			
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
	public Vector3ZZZ<String> parsePost(Vector3ZZZ<String> vecExpression,boolean bKeepSurroundingSeparatorsOnParse) throws ExceptionZZZ {		
		return this.parsePost_(vecExpression, bKeepSurroundingSeparatorsOnParse, true);
	}
	
	@Override
	public Vector3ZZZ<String> parsePost(Vector3ZZZ<String> vecExpression, boolean bKeepSurroundingSeparatorsOnParse,
			boolean bRemoveOwnTagParts) throws ExceptionZZZ {
		return this.parsePost_(vecExpression, bKeepSurroundingSeparatorsOnParse, bRemoveOwnTagParts);
	}
	
	private Vector3ZZZ<String> parsePost_(Vector3ZZZ<String> vecExpressionIn, boolean bKeepSurroundingSeparatorsOnParse, boolean bRemoveOwnTagParts) throws ExceptionZZZ {		
		Vector3ZZZ<String> vecReturn = vecExpressionIn;
		String sReturn = null;
		String sReturnTag = null;
		String sExpressionIn = null;		
		boolean bUseExpression = false; boolean bUseParse = false;
		
							
		main:{			
			if(vecExpressionIn==null) break main;
					
			sExpressionIn = VectorUtilZZZ.implode(vecExpressionIn);
			this.setRaw(sExpressionIn);
			if(StringZZZ.isEmptyTrimmed(sExpressionIn)) break main;
			
			bUseExpression = this.isExpressionEnabledGeneral(); 
			if(!bUseExpression) break main;	
			
			bUseParse = this.isParserEnabledThis();
			if(!bUseParse) break main;
			
//			sReturnTag = (String) vecExpressionIn.get(1);
//			sReturn = sReturnTag;
//			this.setValue(sReturnTag);
			
							
			//Als echten Ergebniswert aber die konkreten <Z>-Tags (z.B. eines Solves) ggfs. rausrechnen, falls gewuenscht
			//Z...-Tags "aus der Mitte entfernen"... Wichtig für das Ergebnis eines Parsens					
			if(!bKeepSurroundingSeparatorsOnParse) {
				String sTagStartZ = "<Z>"; //this.getTagStarting();
				String sTagEndZ = "</Z>";  //this.getTagClosing();
				KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(vecReturn, sTagStartZ, sTagEndZ);  //also von innen nach aussen					
				sReturnTag = (String) vecReturn.get(1);

			}	
			
			
			if(bRemoveOwnTagParts) {
								
				//Aber: den Wert des eigenen Tags setzen. Das ist der Wert aus vec(1), und dann den Tag-Namen darum entfernt. 				
				String sTagStart = this.getTagPartOpening();
				String sTagEnd = this.getTagPartClosing();
				
//				//Entferne auch nur die eigenen Tags, wenn das nicht schon durch bRemoveSurroundigSeperators erlegigt wurde
//				//bzw. bei Gleichnamigkeit ueberhaupt entfernt werden darf.
//				String sTagStartZ = "<Z>";
//				String sTagEndZ = "</Z>";				
//				if(!(sTagStartZ.equals(sTagStart) & sTagEndZ.equals(sTagEnd))) {
				sReturnTag = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sReturnTag, sTagStart, sTagEnd, true, false); //also an jeder Stelle (d.h. nicht mehr am Anfang), also von aussen nach innen!!!
//				}	
								
				this.setValue(sReturnTag);	
			}
		
			
			//ggfs. weitere Sachen rausrechnen, falls gewuenscht
			vecReturn = this.parsePostCustom(vecReturn, bKeepSurroundingSeparatorsOnParse);
			sReturnTag = (String) vecReturn.get(1);
			sReturn = sReturnTag;
			this.setValue(sReturnTag);
			
		}//end main:
				
		//#################################
		return vecReturn;
	}

	
	//+++++++++++++++++++++++++++++++++++++++
	@Override
	public Vector3ZZZ<String> parsePostCustom(Vector3ZZZ<String> vecExpression) throws ExceptionZZZ {
		return this.parsePostCustom_(vecExpression, true);
	}
	
	@Override
	public Vector3ZZZ<String> parsePostCustom(Vector3ZZZ<String> vecExpression, boolean bKeepSurroundingSeparatorsOnParse) throws ExceptionZZZ {		
		return this.parsePostCustom_(vecExpression, bKeepSurroundingSeparatorsOnParse);
	}
	
	//Methode mit Reference Objekt
	private Vector3ZZZ<String> parsePostCustom_(Vector3ZZZ<String> vecExpressionIn, boolean bKeepSurroundingSeparatorsOnParse) throws ExceptionZZZ {		
		Vector3ZZZ<String> vecReturn = vecExpressionIn; String sReturn;
		String sExpressionIn = null; 
		boolean bUseExpression = false; boolean bUseParse = false;
		
		main:{			
			if(vecExpressionIn==null) break main;
			
			sExpressionIn = VectorUtilZZZ.implode(vecExpressionIn);
			if(StringZZZ.isEmpty(sExpressionIn)) break main;
			this.setRaw(sExpressionIn);
					
			sReturn = sExpressionIn;
					
			bUseExpression = this.isExpressionEnabledGeneral(); 
			if(!bUseExpression) break main;
			
			bUseParse = this.isParserEnabledThis(); 
			if(!bUseParse) break main;
			
			
			//.... hier könnte dann ein echter custom Code in einer Klasse stehen.
			
								
		}//end main:
				
		//#################################
		return vecReturn;
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
	public Vector3ZZZ<String>parseFirstVector(String sExpression, boolean bKeepSurroundingSeparatorsOnParse) throws ExceptionZZZ{
		return this.parseFirstVector_(sExpression, bKeepSurroundingSeparatorsOnParse);
	}

	//Nein, auf dieser Ebene ist es ein einfache Tag und kennt IKernelConfigSectionEntryZZZ ueberhaupt nicht.
	//public Vector<String>parseFirstVector(String sLineWithExpression, ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReferenceIn, boolean bKeepSurroundingSeparatorsOnParse) throws ExceptionZZZ{
		
	private Vector3ZZZ<String>parseFirstVector_(String sExpressionIn, boolean bKeepSurroundingSeparatorsOnParse) throws ExceptionZZZ{
		Vector3ZZZ<String>vecReturn = new Vector3ZZZ<String>();		
		String sExpression = null;
		main:{
			if(StringZZZ.isEmptyTrimmed(sExpressionIn)) break main;
			vecReturn.replace(0, sExpressionIn);
						
			boolean bUseExpression = this.getFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION); 
			if(!bUseExpression) break main;
									
			//Falls man diesen Tag aus dem Parsen (des Gesamtstrings) rausnimmt, muessen die umgebenden Tags drin bleiben
			boolean bUseParse = this.isParserEnabledThis();
			if(!bUseParse) break main;
			
			sExpression = sExpressionIn;
			
			//Bei dem einfachen Tag wird das naechste oeffnende Tag genommen und dann auch das naechste schliessende Tag...
			vecReturn = StringZZZ.vecMidFirst(sExpression, this.getTagPartOpening(), this.getTagPartClosing(), bKeepSurroundingSeparatorsOnParse, false);
			if(vecReturn==null)break main;			
						
			//+++ Der endgueltige Wert der Zeile und eigenen Wert setzen 
			//Als echten Ergebniswert aber die <Z>-Tags und den eigenen Tag rausrechnen, falls gewuenscht
			vecReturn = this.parseFirstVectorPost(vecReturn, bKeepSurroundingSeparatorsOnParse);		
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
			boolean bUseExpression = false; 
			
			main:{			
				if(vecExpressionIn==null) break main;
				
				bUseExpression = this.isExpressionEnabledGeneral(); 
				if(!bUseExpression) break main;
												
				if(StringZZZ.isEmpty(sReturn)) break main;			
										
				//.... hier könnte dann ein echter custom Code in einer Klasse stehen.
				
												
			}//end main:
					
			//################################
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
			Vector3ZZZ<String> vecReturn = vecExpressionIn;
			String sReturn = null; String sReturnTag = null;
			main:{
				
				sReturnTag = (String) vecReturn.get(1);		
				this.setValue(sReturnTag);	
				
				vecReturn = this.parseFirstVectorPostCustom(vecReturn, bKeepSurroundingSeparatorsOnParse);
				sReturnTag = (String) vecReturn.get(1);
				this.setValue(sReturnTag);
			}//end main
			return vecReturn;
		}

	
	
	//### aus IExpressionUserZZZ
	@Override
	public boolean isExpression(String sExpression) throws ExceptionZZZ {
		return XmlUtilZZZ.isExpression4TagXml(sExpression, this.getName());
	}	
	

	@Override
	public String makeAsExpression(String sString) throws ExceptionZZZ{
		return ExpressionIniUtilZZZ.makeAsExpression(sString, this.getName());
	}

	//### aus IConvertEnabledZZZ
	@Override
	public String convert(String sExpression) throws ExceptionZZZ {
		return sExpression;
	}	
}
