package basic.zKernel.file.ini;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractList.VectorExtendedDifferenceZZZ;
import basic.zBasic.util.abstractList.VectorZZZ;
import basic.zBasic.util.datatype.calling.ReferenceZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.datatype.xml.XmlUtilZZZ;
import basic.zKernel.IKernelConfigSectionEntryUserZZZ;
import basic.zKernel.IKernelConfigSectionEntryZZZ;
import basic.zKernel.IKernelConfigZZZ;
import basic.zKernel.IKernelEntryReferenceExpressionUserZZZ;
import basic.zKernel.IKernelFileIniUserZZZ;
import basic.zKernel.IKernelUserZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernel.KernelConfigSectionEntryZZZ;
import custom.zKernel.LogZZZ;
import custom.zKernel.file.ini.FileIniZZZ;

//DIES IST DER FLAG - WEG: AbstractObjectWithFlagZZZ -> AbstractObjectWithExpression -> AbstractTagWithExpressionBasic
//ALSO:      In AbstractInitTagWitchExpressionBasicZZZ steht schon ALLES WAS IN AbstractIniTagBasicZZZ und dessen Elternklasse implementiert ist
//NUN NOCH:  Alles WAS in AbstractIniTagSimpleZZZ steht hier auch noch hinein.

//ABER MIT DEM internen Entry-Objek aus IKernelConfigSectionEntryUserZZZ 
public abstract class AbstractKernelIniTagSimpleZZZ<T> extends AbstractIniTagWithExpressionBasicZZZ<T> implements IKernelUserZZZ, IKernelFileIniUserZZZ, IKernelEntryReferenceExpressionUserZZZ{
	private static final long serialVersionUID = -3319737210584524888L;
	protected volatile IKernelZZZ objKernel=null;
	protected volatile LogZZZ objLog = null; //Kann anders als beim Kernel selbst sein.
	protected volatile FileIniZZZ<T> objFileIni = null; //Kann anders als beim Kernel selbst sein.
	
	//Fuer Interface IKernelConfigSectionEntryUserZZZ
	protected volatile IKernelConfigSectionEntryZZZ objEntry = null; //Vereinfachung, ich speichere alles hier ab, hier werden auch die Statusergebnisse der Formelaufloesungsschritte verwaltet.	
			
	public AbstractKernelIniTagSimpleZZZ() throws ExceptionZZZ {
		super(); //Elternklasse kennt keinen Kernel
		AbstractKernelIniTagNew_(null, null);
	}

	public AbstractKernelIniTagSimpleZZZ(String sFlag) throws ExceptionZZZ {
		super(sFlag); //Elternklasse kennt keinen Kernel
		AbstractKernelIniTagNew_(null, null);
	}
	
	public AbstractKernelIniTagSimpleZZZ(String[] saFlag) throws ExceptionZZZ {
		super(saFlag);	//Elternklasse kennt keinen Kernel
		AbstractKernelIniTagNew_(null, null);
	}
	
	public AbstractKernelIniTagSimpleZZZ(IKernelZZZ objKernel) throws ExceptionZZZ {
		super(""); //Elternklasse kennt keinen Kernel, aber mit super() wuerde "init"-Flag gesetzt. Also Leerzeichen setzen.
		AbstractKernelIniTagNew_(objKernel, null);
	}
	
	public AbstractKernelIniTagSimpleZZZ(IKernelZZZ objKernel, String sFlag) throws ExceptionZZZ {
		super(sFlag);
		AbstractKernelIniTagNew_(objKernel, null);
	}
	
	public AbstractKernelIniTagSimpleZZZ(IKernelZZZ objKernel, String[] saFlag) throws ExceptionZZZ {
		super(saFlag);
		AbstractKernelIniTagNew_(objKernel, null);
	}

	public AbstractKernelIniTagSimpleZZZ(IKernelUserZZZ objKernelUsing) throws ExceptionZZZ {
		super(""); //Elternklasse kennt keinen Kernel, aber mit super() wuerde "init"-Flag gesetzt. Also Leerzeichen setzen.
		AbstractKernelIniTagNew_(null, objKernelUsing);
	}
	
	public AbstractKernelIniTagSimpleZZZ(IKernelUserZZZ objKernelUsing, String[] saFlag) throws ExceptionZZZ {
		super(saFlag);
		AbstractKernelIniTagNew_(null, objKernelUsing);
	}
	
	
	/** Wg. Interface IKernelUserZZZ notwendige Redundanz zu 
	 *  AbstractKernelUseObjectZZZ
	 *  und dem dortigen Konstruktor
	 *  
	 * @param objKernel
	 * @param objKernelUsing
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 05.07.2024, 07:10:15
	 */
	private boolean AbstractKernelIniTagNew_(IKernelZZZ objKernel, IKernelUserZZZ objKernelUsing) throws ExceptionZZZ {
		boolean bReturn = false;		
		main: {			
			if(this.getFlag("INIT")==true){
				bReturn = true;
				break main; 
			}	
			
			boolean btemp;
			String sLog = null;
			if(objKernel==null & objKernelUsing==null) {				
				ExceptionZZZ ez = new ExceptionZZZ("KernelObject and KernelUsingObject missing",iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
				throw ez;					
			}
				
			IKernelZZZ objKernelUsed=null;
			if(objKernelUsing!=null) {	
				objKernelUsed = objKernelUsing.getKernelObject();
				if(objKernelUsed==null && objKernel==null) {
					ExceptionZZZ ez = new ExceptionZZZ("KernelObject missing",iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}else if (objKernelUsed==null){
					objKernelUsed = objKernel;
				}
			}else {
				objKernelUsed = objKernel;
			}
			
			this.objKernel = objKernelUsed;
			this.objLog = objKernelUsed.getLogObject();
									
			//++++++++++++++++++++++++++++++
			//HIER geht es darum ggfs. die Flags zu übernehmen, die irgendwo gesetzt werden sollen und aus dem Kommandozeilenargument -z stammen.
			//D.h. ggfs. stehen sie in dieser Klasse garnicht zur Verfügung
			//Kommandozeilen-Argument soll alles übersteuern. Darum auch FALSE setzbar. Darum auch nach den "normalen" Flags verarbeiten.
			if(this.getKernelObject()!=null) {
				IKernelConfigZZZ objConfig = this.getKernelObject().getConfigObject();
				if(objConfig!=null) {
					//Übernimm die als Kommandozeilenargument gesetzten FlagZ... die können auch "false" sein.
					Map<String,Boolean>hmFlagZpassed = objConfig.getHashMapFlagPassed();
					if(hmFlagZpassed!=null) {
						Set<String> setFlag = hmFlagZpassed.keySet();
						Iterator<String> itFlag = setFlag.iterator();
						while(itFlag.hasNext()) {
							String sKey = itFlag.next();
							 if(!StringZZZ.isEmpty(sKey)){
								 Boolean booValue = hmFlagZpassed.get(sKey);
								 btemp = setFlag(sKey, booValue.booleanValue());//setzen der "auf Verdacht" indirekt übergebenen Flags
								 if(btemp==false){						 
									 sLog = ReflectCodeZZZ.getPositionCurrent() +"The passed flag '" + sKey + "' is not available for class '" + this.getClass() + "'.";
									 this.logLineDate(sLog);
		//							  Bei der "Übergabe auf Verdacht" keinen Fehler werfen!!!
		//							  ExceptionZZZ ez = new ExceptionZZZ(sLog, iERROR_PARAMETER_VALUE, this,  ReflectCodeZZZ.getMethodCurrentName()); 
		//							  throw ez;		 
								  }
							 }
						}
					}
				}	
			}			
			//+++++++++++++++++++++++++++++++						
			bReturn = true;
		}//end main;
		return bReturn;
	}

	
	//### aus IKernelUserZZZ
	@Override
	public IKernelZZZ getKernelObject() throws ExceptionZZZ {
		return this.objKernel;
	}

	@Override
	public void setKernelObject(IKernelZZZ objKernel) {
		this.objKernel=objKernel;
	}
	
	//### aus IKernelLogUserZZZ
	@Override
	public LogZZZ getLogObject() throws ExceptionZZZ {
		return this.objLog;
	}

	@Override
	public void setLogObject(LogZZZ objLog) throws ExceptionZZZ {
		this.objLog = objLog;
	}	
	
	//### aus IKernelFileIniuserZZZ
	@Override
	public void setFileConfigKernelIni(FileIniZZZ objFileIni){
		this.objFileIni = objFileIni;
	}
	@Override
	public FileIniZZZ<T> getFileConfigKernelIni()throws ExceptionZZZ{
		if(this.objFileIni==null) {
			IKernelZZZ objKernel = this.getKernelObject();
			if(objKernel==null) {
				ExceptionZZZ ez = new ExceptionZZZ("FileIni and KernelObject", iERROR_PROPERTY_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			return objKernel.getFileConfigKernelIni();						
		}else {
			return this.objFileIni;
		}
	}
		
	//### aus IKernelConfigSectionEntryUserZZZ 	
	@Override
	public IKernelConfigSectionEntryZZZ getEntryNew() throws ExceptionZZZ {
		this.objEntry = null; //Also explizit leer setzen, um etwas neues zu holen. Ist wichtig für Objekte, die eine 2te Verarbeitung starten. z.B. KernelFileIni.
		return this.getEntry();
	}
				
	@Override
	public IKernelConfigSectionEntryZZZ getEntry() throws ExceptionZZZ{
		if(this.objEntry==null) {
			//!!! Endlosschleifengefahr.
			//Damit der Tag selbst uebegeben werden kann muss beim 
			//holen der Werte mit getValue() unbedigt auf this.objEntry==null geprueft werden.
			this.objEntry = new KernelConfigSectionEntryZZZ<T>(this);		
		}
		return this.objEntry;
	}
	
	@Override
	public void setEntry(IKernelConfigSectionEntryZZZ objEntry) throws ExceptionZZZ {
		this.objEntry = objEntry;
	}
		
	// Die Methoden aus AbstractObjectWithExpressionZZZ muessen nur auf den Entry umgebogen werden.
	// Aber Achtung: Endlosschleifengefahr. Wg. Konstruktor mit diesem Objekt selbst als Uebergabe (this)
	//               Darum beim holen der Werte mit .get...() immer auf this.objEntry==null prüfen.

	//### Aus IValueBufferedUserZZZ
	@Override
	public VectorExtendedDifferenceZZZ<String> getValueVector() throws ExceptionZZZ {
		if(this.objEntry==null)return null; //!!!Sonst Endlosschleifengefahr.
		return this.getEntry().getValueVector();
	}
	
	@Override
	public String getValue() throws ExceptionZZZ {
		if(this.objEntry==null)return null; //!!!Sonst Endlosschleifengefahr. Wg. Konstruktor mit diesem Objekt selbst als Uebergabe (this)
		return this.getEntry().getValue();
	}

	@Override
	public void setValue(String sValue) throws ExceptionZZZ {
		this.getEntry().setValue(sValue);
	}
	
	@Override
	public boolean hasAnyValue() throws ExceptionZZZ {
		if(this.objEntry==null)return false; //!!!Sonst Endlosschleifengefahr. Wg. Konstruktor mit diesem Objekt selbst als Uebergabe (this)
		return this.getEntry().hasAnyValue();
	}	
	
	//Wird beim Setzen des Werts automatisch mit gesetzt. Also nicht "von aussen" setzbar
	//daher protected. Was nicht im Intface definierbar ist.
	@Override
	public void hasAnyValue(boolean bAnyValue) throws ExceptionZZZ {		
		this.getEntry().hasAnyValue(bAnyValue);
	}
	
	@Override
	public boolean hasNullValue() throws ExceptionZZZ {
		if(this.objEntry==null)return false; //!!!Sonst Endlosschleifengefahr. Wg. Konstruktor mit diesem Objekt selbst als Uebergabe (this)
		return this.getEntry().hasNullValue();
	}
	//Wird beim Setzen des Werts automatisch mit gesetzt. Also nicht "von aussen" setzbar, 
	//daher protected. Was nicht im Intface definierbar ist.
	@Override
	public void hasNullValue(boolean bNullValue) {		
		this.bNullValueInObjectWithExpression=bNullValue;
	}
	
	//####################################################
    //### aus IValueComputedBufferedUserZZZ
	@Override
	public VectorExtendedDifferenceZZZ<String> getRawVector() throws ExceptionZZZ {
		if(this.objEntry==null)return null; //!!!Sonst Endlosschleifengefahr. Wg. Konstruktor mit diesem Objekt selbst als Uebergabe (this)
		return this.getEntry().getRawVector();
	}

	@Override
	public String getRaw() throws ExceptionZZZ {
		if(this.objEntry==null)return null; //!!!Sonst Endlosschleifengefahr. Wg. Konstruktor mit diesem Objekt selbst als Uebergabe (this)
		return this.getEntry().getRaw();
	}

	@Override
	public void setRaw(String sRaw) throws ExceptionZZZ {
		this.getEntry().setValue(sRaw);
	}
	
	//##############################
	//### aus IValueArrayUserZZZ
	//Merke: Arrays erst in ini-Tag behandeln, da es dafuer Separatorn in der Zeile geben muss
	@Override	
	public ArrayList<String> getValueArrayList() throws ExceptionZZZ{
		if(this.objEntry==null)return null; //!!!Sonst Endlosschleifengefahr. Wg. Konstruktor mit diesem Objekt selbst als Uebergabe (this)
		return this.getEntry().getValueArrayList();
	}

	@Override
	public void setValue(ArrayList<String> listasValue) throws ExceptionZZZ{
		this.getEntry().setValue(listasValue);
	}
	
	@Override
	public boolean isArrayValue() throws ExceptionZZZ {
		if(this.objEntry==null)return false; //!!!Sonst Endlosschleifengefahr. Wg. Konstruktor mit diesem Objekt selbst als Uebergabe (this)
		return this.getEntry().isArrayValue();
	}

	@Override
	public void isArrayValue(boolean bIsArrayValue) throws ExceptionZZZ {
		this.getEntry().isArrayValue(bIsArrayValue);
	}
	
//	//### aus IIniTagBasicZZZ
//	@Override
//	public IKernelConfigSectionEntryZZZ parseAsEntryNew(String sExpression) throws ExceptionZZZ{
//		IKernelConfigSectionEntryZZZ objReturn = this.getEntryNew();
//		main:{
//			if(StringZZZ.isEmptyTrimmed(sExpression)) break main;
//			objReturn.setRaw(sExpression);
//			
//			ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
//			objReturnReference.set(objReturn);					
//			int iReturn = this.parse(sExpression, objReturnReference);
//			objReturn = objReturnReference.get();
//		}//end main:		
//		return objReturn;
//	}
//	
//	@Override
//	public IKernelConfigSectionEntryZZZ parseAsEntry(String sExpression) throws ExceptionZZZ{
//		return this.parseAsEntry(sExpression, null);
//	}
//	
//	@Override
//	public IKernelConfigSectionEntryZZZ parseAsEntry(String sExpression, ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReferenceIn) throws ExceptionZZZ{
//		return this.parseAsEntry(sExpression, objReturnReferenceIn, true);
//	}
//	
//	@Override
//	public IKernelConfigSectionEntryZZZ parseAsEntry(String sExpression, ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReferenceIn, boolean bRemoveSurroundingSeparators) throws ExceptionZZZ{
//		IKernelConfigSectionEntryZZZ objReturn = this.getEntryNew();
//		main:{
//			if(StringZZZ.isEmptyTrimmed(sExpression)) break main;
//			if(objReturnReferenceIn==null) {
//				objReturn = this.parseAsEntryNew(sExpression);
//				break main;
//			}
//			
//			IKernelConfigSectionEntryZZZ objEntry = objReturnReferenceIn.get();
//			if(objEntry==null) {
//				objReturn =  this.parseAsEntryNew(sExpression);							
//			}else {			
//				ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReference = new ReferenceZZZ<IKernelConfigSectionEntryZZZ>(); 			
//				objReturnReference.set(objEntry);
//				
//				int iReturn = this.parse(sExpression, objReturnReference, bRemoveSurroundingSeparators);
//				objReturn = objReturnReference.get();						
//			}
//			if(objReturnReferenceIn!=null) objReturnReferenceIn.set(objReturn);
//			
//		}//end main:		
//		return objReturn;
//	}
	
	//### Aus IKernelEntryExpressionUserZZZ
	@Override
	public int parse(String sExpression, ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference) throws ExceptionZZZ {
		return this.parse(sExpression, objReturnReference, true);
	}
	
	@Override
	public int parse(String sExpression, ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReferenceIn, boolean bRemoveSurroundingSeparators) throws ExceptionZZZ {
		int iReturn = 0;
		main:{		
			IKernelConfigSectionEntryZZZ objEntry = null;
			ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReference = new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
			if(objReturnReferenceIn==null) {				
			}else {
				objEntry = objReturnReferenceIn.get();
			}
			if(objEntry==null) {
				objEntry = this.getEntryNew(); //Hier schon die Rückgabe vorbereiten, falls eine weitere Verarbeitung nicht konfiguriert ist.
											 //Wichtig: Als oberste Methode immer ein neues Entry-Objekt holen. Dann stellt man sicher, das nicht mit Werten der vorherigen Suche gearbeitet wird.				
			}//Achtung: Das objReturn Objekt NICHT generell uebernehmen. Es verfaelscht bei einem 2. Suchaufruf das Ergebnis.
			objEntry.setRaw(sExpression);
			objReturnReference.set(objEntry);
							
			Vector<String> vecReturn = this.parseFirstVector(sExpression, objReturnReference, bRemoveSurroundingSeparators);						
			if(vecReturn==null) break main;
						
			String sReturn = VectorZZZ.implode(vecReturn);	//Merke: Implode nur bei parse(), solve() gibt vecReturn.get(1) zurueck.						
			this.setValue(sReturn);
			
			objEntry.setValue(sReturn);				
			if(!sExpression.equals(sReturn)) {
				objEntry.isParsed(true);			
				iReturn = 1;
			}
			if(objReturnReferenceIn!=null) objReturnReferenceIn.set(objEntry);			
		}//end main:
		return iReturn;
	}
	
	//Die Idee ist, das die konkreten Klassen den ersten Vector parsen
	//also ggfs. überschreiben: public Vector<String>parseFirstVector(String sLineWithExpression, boolean bRemoveSurroundingSeparators) throws ExceptionZZZ{
	
	//### aus IParseEnabledZZZ
	@Override
	public Vector<String> parseFirstVector(String sLineWithExpression, boolean bRemoveSurroundingSeparators) throws ExceptionZZZ {
		//Muss ueberschrieben werden, damit die "einfache Tag" Methode nicht greift und wir mit der parse - Methode dieser konkreten Klasse arbeiten.
		return this.parseFirstVector(sLineWithExpression, null, bRemoveSurroundingSeparators);
	}
	
	//### Aus IKernelEntryExpressionUserZZZ	
	@Override
	public Vector<String>parseFirstVector(String sLineWithExpression, ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReference) throws ExceptionZZZ{
		return this.parseFirstVector(sLineWithExpression, objReturnReference, true);
	}

	@Override
	public Vector<String> parseFirstVector(String sExpression, ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReferenceIn, boolean bRemoveSurroundingSeparators) throws ExceptionZZZ {
		Vector<String>vecReturn = new Vector<String>();
		IKernelConfigSectionEntryZZZ objEntry = null;
		String sReturn = sExpression;
		main:{
			if(StringZZZ.isEmpty(sExpression)) break main;
			if(!this.getFlag(IIniTagWithExpressionZZZ.FLAGZ.USEEXPRESSION)) break main;		
			
			ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReference = new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();			
			if(objReturnReferenceIn==null) {
				//Das Ziel ist es moeglichst viel Informationen aus dem entry "zu retten"
				objEntry = new KernelConfigSectionEntryZZZ<T>(this); //this.getEntryNew(); es gingen alle Informationen verloren				
				                                                     //nein, dann gehen alle Informationen verloren   objReturn = this.parseAsEntryNew(sExpression);				
			}else {
				objEntry = objReturnReferenceIn.get();
			}
			
			if(objEntry==null) {
				//Achtung: Das objReturn Objekt NICHT generell uebernehmen. Es verfaelscht bei einem 2. Suchaufruf das Ergebnis.
				//objEntry = this.getEntry();
				objEntry = new KernelConfigSectionEntryZZZ<T>(this); // =  this.parseAsEntryNew(sExpression);  //nein, dann gehen alle Informationen verloren   objReturn = this.parseAsEntryNew(sExpression);
			}	
			objEntry.setRaw(sExpression);
			objReturnReference.set(objEntry);
						
			vecReturn = super.parseFirstVector(sExpression, bRemoveSurroundingSeparators);
			if (vecReturn==null)break main;			
			

//			String sReturn = VectorZZZ.implode(vecReturn);
//			this.setValue(sReturn);						
//			objEntry.setValue(sReturn);

						
			//NUN DEN INNERHALB DER EXPRESSION BERECHUNG ERSTELLTEN WERT in den Return-Vector übernehmen			
			//Nein, muss ja irgendwie weitergehen und das tut es nicht, wenn wieder 1:1 alles zurückgegeben wird.
			//sReturn = VectorZZZ.implode(vecReturn);
			sReturn = vecReturn.get(1);
				
			if(vecReturn.size()==0) vecReturn.add(0, "");
							
			if(vecReturn.size()>=2) vecReturn.removeElementAt(1);
			if(!StringZZZ.isEmpty(sReturn)){
				vecReturn.add(1, sReturn);
			}else {
				vecReturn.add(1, "");
			}
				
			if(vecReturn.size()==2) vecReturn.add(2, "");
		}				
		
		this.setValue(sReturn);
		if(objEntry!=null) objEntry.setValue(VectorZZZ.implode(vecReturn));			
		if(objReturnReferenceIn!=null)objReturnReferenceIn.set(objEntry);
		return vecReturn;
	}
	
	
	
	
	//### aus IExpressionUserZZZ	
	/** Gibt einen Vector zurück, in dem das erste Element der Ausdruck VOR der ersten 'Expression' ist. Das 2. Element ist die Expression. Das 3. Element ist der Ausdruck NACH der ersten Expression.
	* @param sExpression
	* @return
	* 
	* lindhaueradmin; 06.03.2007 11:20:34
	 * @throws ExceptionZZZ 
	 */
	@Override
	public Vector<String>parseFirstVectorAsExpression(String sExpression) throws ExceptionZZZ{
		Vector<String> vecReturn = null;

		main:{
			if(sExpression==null) break main;
			
			vecReturn = new Vector<String>();
			if(StringZZZ.isEmpty(sExpression)) break main;
			
			if(! this.getFlag(IIniTagWithExpressionZZZ.FLAGZ.USEEXPRESSION)) break main;
								
			//Bei dem einfachen Tag wird die naechste Tag genommen und dann auch das naechste schliessende Tag...
			vecReturn = StringZZZ.vecMidFirst(sExpression, this.getTagStarting(), this.getTagClosing(), true, false);
				
		}//end main:			
		return vecReturn;
	}
	
	@Override
	public Vector<String>parseAllVectorAsExpression(String sExpression) throws ExceptionZZZ{
		Vector<String> vecReturn = null;		
		main:{
			if(sExpression==null) break main;
			
			vecReturn = new Vector<String>();
			if(StringZZZ.isEmpty(sExpression)) break main;
			
			if(! this.getFlag(IIniTagWithExpressionZZZ.FLAGZ.USEEXPRESSION)) break main;
								
			//Merke: Das ist der Fall, das ein Ausdruck NICHT verschachtelt ist
			//       Für verschachtelte Tags muss hier extra was programmiert und diese Methode ueberschrieben werden.
			vecReturn = this.parseFirstVectorAsExpression(sExpression); 			
			
		}//end main:
		
		return vecReturn;
	}
	
	//### aus IConvertableZZZ
	@Override
	public String convert(String sLine) throws ExceptionZZZ {
		return sLine;
	}	
	
	/* IDEE: convertable != parasable.
    convertable bedeutet DER GANZE STRING Wird ersetzt, also nur wenn nix davor oder dahniter steht.
    parsable würde dann lediglich den Wert aus der Mitte (s. Vector.getIndex(1) ) durch ein Leerzeichen ersetzen
	
	* 
	* (non-Javadoc)
	* @see basic.zKernel.file.ini.AbstractIniTagSimpleZZZ#isConvertRelevant(java.lang.String)
	*/
	@Override
	public boolean isConvertRelevant(String sExpression) throws ExceptionZZZ {
	boolean bReturn = false;
	main:{
		if(StringZZZ.isEmptyTrimmed(sExpression)) break main;
		
		bReturn = this.getEmpty().equalsIgnoreCase(sExpression);
		if(bReturn) break main;
	
		bReturn = XmlUtilZZZ.isSurroundedByTag(sExpression, this.getTagStarting(), this.getTagClosing());		
	}//end main
	return bReturn;
	}
	
	//###################################
	//### FLAG Handling
	

	
}// End class