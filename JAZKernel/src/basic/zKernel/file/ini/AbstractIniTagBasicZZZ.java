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
	
	//### aus IIniTagBasicZZZ  	
	@Override
	public IKernelConfigSectionEntryZZZ parseAsEntryNew(String sExpression) throws ExceptionZZZ{
		//Nein, das setzt das Entry-Objekt des Solvers zurueck IKernelConfigSectionEntryZZZ objReturn = this.getEntryNew();
		//und damit sind bestehende Eintragswerte ggfs. uebernommen IKernelConfigSectionEntryZZZ objReturn = new KernelConfigSectionEntryZZZ<T>(this);
		IKernelConfigSectionEntryZZZ objReturn = new KernelConfigSectionEntryZZZ<T>();						
		main:{
			if(StringZZZ.isEmptyTrimmed(sExpression)) break main;
			objReturn.setRaw(sExpression);

			ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReferenceSolve = new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
			objReturnReferenceSolve.set(objReturn);
			objReturn = this.parseAsEntry_(sExpression, objReturnReferenceSolve, true);			
		}//end main:
		return objReturn;
	}	
	
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	@Override
	public IKernelConfigSectionEntryZZZ parseAsEntry(String sExpression) throws ExceptionZZZ{
		return this.parseAsEntry_(sExpression, null, true);
	}
	
	@Override
	public IKernelConfigSectionEntryZZZ parseAsEntry(String sExpression, boolean bRemoveSurroundingSeparators) throws ExceptionZZZ{
		return this.parseAsEntry_(sExpression, null, bRemoveSurroundingSeparators);
	}
	
	@Override
	public IKernelConfigSectionEntryZZZ parseAsEntry(String sExpression, ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReferenceIn) throws ExceptionZZZ{
		return this.parseAsEntry_(sExpression, objReturnReferenceIn, true);
	}
	
	@Override
	public IKernelConfigSectionEntryZZZ parseAsEntry(String sExpression, ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReferenceIn, boolean bRemoveSurroundingSeparators) throws ExceptionZZZ{
		return this.parseAsEntry_(sExpression, objReturnReferenceIn, bRemoveSurroundingSeparators);
	}
	
	private IKernelConfigSectionEntryZZZ parseAsEntry_(String sExpressionIn, ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReferenceIn, boolean bRemoveSurroundingSeparators) throws ExceptionZZZ{
		IKernelConfigSectionEntryZZZ objReturn = null;
		String sReturn = sExpressionIn;
		main:{
			if(StringZZZ.isEmptyTrimmed(sExpressionIn)) break main;
			
			ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReference = new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
			if(objReturnReferenceIn==null) {
				//Das Ziel ist es moeglichst viel Informationen aus dem entry "zu retten"
				objReturn = new KernelConfigSectionEntryZZZ<T>(this); //geht hier nicht... this.getEntryNew(); ausserdem gingen alle Informationen verloren								                                                      //nein, dann gehen alls Informationen verloren   objReturn = this.parseAsEntryNew(sExpression);
																		//nein, dann gehen alls Informationen verloren   objReturn = this.parseAsEntryNew(sExpression);
			}else {
				objReturn = objReturnReferenceIn.get();				
			}
				
			if(objReturn==null) {
				//Das Ziel ist es moeglichst viel Informationen aus dem entry "zu retten"
				//Achtung: Das objReturn Objekt NICHT generell versuchen ueber .getEntry() und dann ggfs. .getEntryNew() zu uebernehmen. Es verfaelscht bei einem 2. Suchaufruf das Ergebnis.
				//objEntry = this.getEntry();
				
				//nein, dann gehen alle Informationen verloren
				//objReturn = this.parseAsEntryNew(sExpression);
				
				objReturn = new KernelConfigSectionEntryZZZ<T>(this);  
				objReturnReference.set(objReturn);
			}
			objReturn.setRaw(sExpressionIn);
			
//!!!Nicht der Flag-Vererbungsweg //Es soll immer ein Entry Objekt zurückkommen, darum hier erst auf das Expression-Flag abpruefen.
//			boolean bUseExpression = this.getFlag(IIniTagWithExpressionZZZ.FLAGZ.USEEXPRESSION); 
//			if(!bUseExpression) break main;
			
			//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!1
			//Merke: in Elternklassen gibt es diese Methode nur ohne Reference, da ohne KernelEbene das Objekt nicht vorhanden ist.
			//       Darum wird die Methode auch hier von erbenden Klassen ueberschrieben.
			//Hier Methode nur ohne Reference... String sReturn = this.parse(sExpression, objReturnReferenceParse, bRemoveSurroundingSeparators);
			//Mit Reference geht ab: AbstractKernelIniTagSimpleZZZ
			//ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReferenceParse = new ReferenceZZZ<IKernelConfigSectionEntryZZZ>(); 			
			//objReturnReferenceParse.set(objReturn);
			//String sExpression = sExpressionIn;
			//sReturn = this.parse(sExpression, objReturnReferenceParse, bRemoveSurroundingSeparators);
			//objReturn = objReturnReferenceParse.get();			
			
			String sExpression = sExpressionIn;
			sReturn = this.parse(sExpression, bRemoveSurroundingSeparators);
			
			//NEIN, auf gar keinen Fall diesen Wert uebernehmen. Damit wuerde hier der Wert des Tags selbst ueberschrieben
			//this.setValue(sReturn);						
		}//end main:
		
		if(objReturn!=null) {
			objReturn.setValue(sReturn);	
			if(sExpressionIn!=null) {
				if(!sExpressionIn.equals(sReturn)) objReturn.isParseCalled(true);
			}				
			if(objReturnReferenceIn!=null) objReturnReferenceIn.set(objReturn);
		}					
		return objReturn;
	}
	
	//+++++++++++++++++++++++++++++++++++++
	
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
			
			Vector<String> vecParsed = this.parseFirstVector(sLineWithExpression);
			String sParsed = vecParsed.get(1);
			
			String[] saExpression = StringZZZ.explode(sParsed, sDelimiter); //Dann löse Ihn als Mehrfachwert auf.
			ArrayListExtendedZZZ<String> listasValue = new ArrayListExtendedZZZ<String>(saExpression);
			this.setValue(listasValue);		
			
			//Fasse nun zusammen.
			ArrayListExtendedZZZ<String> listasReturnParsed = new ArrayListExtendedZZZ<String>();
			listasReturnParsed.add(vecParsed.get(0));
			
			//Fuer den Wert lediglich
			ArrayListExtendedZZZ<String> listasValueParsed = new ArrayListExtendedZZZ<String>();
			
			String sValue = null;			
			for(String sExpression : saExpression) {
				sValue = this.parse(sExpression);
				listasReturnParsed.add(sValue);
				listasValueParsed.add(sValue);
			}
			listasReturnParsed.add(vecParsed.get(2));
			
			this.setValue(listasValueParsed);
			saReturn = listasReturnParsed.toStringArray();				
		}//end main:
		return saReturn;
	}
	
	//### Aus IParseEnabledZZZ	
	@Override
	public String parse(String sLineWithExpression) throws ExceptionZZZ{
		return this.parse_(sLineWithExpression, true);
	}
				
	@Override
	public String parse(String sLineWithExpression, boolean bRemoveSurroundingSeparators) throws ExceptionZZZ{
		return this.parse_(sLineWithExpression, bRemoveSurroundingSeparators);
	}	
	
	private String parse_(String sLineWithExpression, boolean bRemoveSurroundingSeparators) throws ExceptionZZZ{
		String sReturn = sLineWithExpression;
		String sReturnTag = null;
		main:{
			if(StringZZZ.isEmptyTrimmed(sLineWithExpression)) break main;
			
			Vector3ZZZ<String> vecExpression = this.parseFirstVector(sLineWithExpression, bRemoveSurroundingSeparators);
			if(vecExpression==null) break main;

			sReturnTag = (String) vecExpression.get(1);
			this.setValue(sReturnTag);
							
			vecExpression = this.parsePost(vecExpression, bRemoveSurroundingSeparators);
			sReturnTag = (String) vecExpression.get(1);
			this.setValue(sReturnTag);
				
			//Der zurueckgegebene Wert unterscheidet sich vom Wert des Tags selber.
			sReturn = VectorUtilZZZ.implode(vecExpression);									
		}//end main:
		return sReturn;
	}	
	
	@Override
	public Vector3ZZZ<String> parsePost(Vector3ZZZ<String> vecExpression) throws ExceptionZZZ {
		return this.parsePost_(vecExpression, true);
	}
	
	@Override
	public Vector3ZZZ<String> parsePost(Vector3ZZZ<String> vecExpression,boolean bRemoveSurroundingSeparators) throws ExceptionZZZ {		
		return this.parsePost_(vecExpression, bRemoveSurroundingSeparators);
	}
	
	private Vector3ZZZ<String> parsePost_(Vector3ZZZ<String> vecExpressionIn, boolean bRemoveSurroundingSeparators) throws ExceptionZZZ {		
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
				if(bRemoveSurroundingSeparators) {
					String sTagStart = "<Z>"; //this.getTagStarting();
					String sTagEnd = "</Z>";  //this.getTagClosing();
					KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(vecReturn, sTagStart, sTagEnd);  //also von innen nach aussen
	
					sReturnTag = (String) vecReturn.get(1);
					sReturn = sReturnTag;
					this.setValue(sReturnTag);
				}	
			}else {
				//Wenn der Parser herausgenommen ist, seine Tags nicht entfernen.
			}
			
			//ggfs. weitere Sachen rausrechnen, falls gewuenscht
			vecReturn = this.parsePostCustom(vecReturn, bRemoveSurroundingSeparators);
			sReturnTag = (String) vecReturn.get(1);
			sReturn = sReturnTag;
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
	public Vector3ZZZ<String> parsePostCustom(Vector3ZZZ<String> vecExpression, boolean bRemoveSurroundingSeparators) throws ExceptionZZZ {
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
	public Vector3ZZZ<String>parseFirstVector(String sLineWithExpression) throws ExceptionZZZ{
		Vector3ZZZ<String>vecReturn = new Vector3ZZZ<String>();		
		main:{
			//Bei dem einfachen Tag wird die naechste Tag genommen und dann auch das naechste schliessende Tag...
			vecReturn = StringZZZ.vecMidFirst(sLineWithExpression, this.getTagStarting(), this.getTagClosing(), false, false);
			this.setValue((String) vecReturn.get(1));
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
		public Vector3ZZZ<String> parseFirstVectorPostCustom(Vector3ZZZ<String> vecExpression, boolean bRemoveSurroundingSeparators) throws ExceptionZZZ {		
			return this.parseFirstVectorPostCustom_(vecExpression, bRemoveSurroundingSeparators);
		}
		
		private Vector3ZZZ<String> parseFirstVectorPostCustom_(Vector3ZZZ<String> vecExpressionIn, boolean bRemoveSurroundingSeparators) throws ExceptionZZZ{
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
