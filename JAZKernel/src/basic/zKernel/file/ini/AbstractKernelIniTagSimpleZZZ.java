package basic.zKernel.file.ini;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.abstractList.HashMapCaseInsensitiveZZZ;
import basic.zBasic.util.abstractList.Vector3ZZZ;
import basic.zBasic.util.abstractList.VectorDifferenceZZZ;
import basic.zBasic.util.abstractList.VectorUtilZZZ;
import basic.zBasic.util.datatype.calling.ReferenceZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.datatype.xml.XmlUtilZZZ;
import basic.zKernel.IKernelConfigSectionEntryUserZZZ;
import basic.zKernel.IKernelConfigSectionEntryZZZ;
import basic.zKernel.IKernelConfigZZZ;
import basic.zKernel.IKernelEntryReferenceExpressionUserZZZ;
import basic.zKernel.IKernelEntryReferenceSubstituteUserZZZ;
import basic.zKernel.IKernelFileIniUserZZZ;
import basic.zKernel.IKernelUserZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernel.KernelConfigSectionEntryZZZ;
import basic.zKernel.config.KernelConfigSectionEntryUtilZZZ;
import basic.zKernel.flag.util.FlagZFassadeZZZ;
import custom.zKernel.LogZZZ;
import custom.zKernel.file.ini.FileIniZZZ;

//DIES IST DER FLAG - WEG: AbstractObjectWithFlagZZZ -> AbstractObjectWithExpression -> AbstractTagWithExpressionBasic
//ALSO:      In AbstractInitTagWitchExpressionBasicZZZ steht schon ALLES WAS IN AbstractIniTagBasicZZZ und dessen Elternklasse implementiert ist
//NUN NOCH:  Alles WAS in AbstractIniTagSimpleZZZ steht hier auch noch hinein.

//ABER MIT DEM internen Entry-Objek aus IKernelConfigSectionEntryUserZZZ

//ZUDEM IST DAS DIE STELLE, AN DER KERNEL INI-PFADE/-VARIABLEN "substitiuert" werden beim Parsen.
//      Dadurch ist es notwendig eine Variablen HashMap zu verwalten und Flags für PATH und VARIABLE auch aufzunehmen
public abstract class AbstractKernelIniTagSimpleZZZ<T> extends AbstractIniTagWithExpressionBasicZZZ<T> implements IKernelUserZZZ, IKernelFileIniUserZZZ, IKernelEntryReferenceExpressionUserZZZ, ISubstituteEnabledZZZ, IKernelEntryReferenceSubstituteUserZZZ{
	private static final long serialVersionUID = -3319737210584524888L;
	protected volatile IKernelZZZ objKernel=null;
	protected volatile LogZZZ objLog = null; //Kann anders als beim Kernel selbst sein.
	protected volatile FileIniZZZ<T> objFileIni = null; //Kann anders als beim Kernel selbst sein.
	
	//Für Interface IValueVariableUserZZZ
	protected HashMapCaseInsensitiveZZZ<String,String> hmVariable =null;
	
	//Fuer Interface IKernelConfigSectionEntryUserZZZ
	protected volatile IKernelConfigSectionEntryZZZ objEntryInner = null; //Vereinfachung, ich speichere alles hier ab, hier werden auch die Statusergebnisse der Formelaufloesungsschritte verwaltet.	
			
	public AbstractKernelIniTagSimpleZZZ() throws ExceptionZZZ {
		super(); //Elternklasse kennt keinen Kernel
		AbstractKernelIniTagSimpleNew_(null, null);
	}

	public AbstractKernelIniTagSimpleZZZ(String sFlag) throws ExceptionZZZ {
		super(sFlag); //Elternklasse kennt keinen Kernel
		AbstractKernelIniTagSimpleNew_(null, null);
	}
	
	public AbstractKernelIniTagSimpleZZZ(String[] saFlag) throws ExceptionZZZ {
		super(saFlag);	//Elternklasse kennt keinen Kernel
		AbstractKernelIniTagSimpleNew_(null, null);
	}
	
	public AbstractKernelIniTagSimpleZZZ(IKernelZZZ objKernel) throws ExceptionZZZ {
		super(""); //Elternklasse kennt keinen Kernel, aber mit super() wuerde "init"-Flag gesetzt. Also Leerzeichen setzen.
		AbstractKernelIniTagSimpleNew_(objKernel, null);
	}
	
	public AbstractKernelIniTagSimpleZZZ(IKernelZZZ objKernel, String sFlag) throws ExceptionZZZ {
		super(sFlag);
		AbstractKernelIniTagSimpleNew_(objKernel, null);
	}
	
	public AbstractKernelIniTagSimpleZZZ(IKernelZZZ objKernel, String[] saFlag) throws ExceptionZZZ {
		super(saFlag);
		AbstractKernelIniTagSimpleNew_(objKernel, null);
	}

	public AbstractKernelIniTagSimpleZZZ(IKernelUserZZZ objKernelUsing) throws ExceptionZZZ {
		super(""); //Elternklasse kennt keinen Kernel, aber mit super() wuerde "init"-Flag gesetzt. Also Leerzeichen setzen.
		AbstractKernelIniTagSimpleNew_(null, objKernelUsing);
	}
	
	public AbstractKernelIniTagSimpleZZZ(IKernelUserZZZ objKernelUsing, String[] saFlag) throws ExceptionZZZ {
		super(saFlag);
		AbstractKernelIniTagSimpleNew_(null, objKernelUsing);
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
	private boolean AbstractKernelIniTagSimpleNew_(IKernelZZZ objKernel, IKernelUserZZZ objKernelUsing) throws ExceptionZZZ {
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
		this.objEntryInner = null; //Also explizit leer setzen, um etwas neues zu holen. Ist wichtig für Objekte, die eine 2te Verarbeitung starten. z.B. KernelFileIni.
		return this.getEntry();
	}
				
	@Override
	public IKernelConfigSectionEntryZZZ getEntry() throws ExceptionZZZ{
		if(this.objEntryInner==null) {
			//!!! Endlosschleifengefahr.
			//Damit der Tag selbst uebegeben werden kann muss beim 
			//holen der Werte mit getValue() unbedigt auf this.objEntry==null geprueft werden.
			this.objEntryInner = new KernelConfigSectionEntryZZZ<T>(this);		
		}
		return this.objEntryInner;
	}
	
	@Override
	public void setEntry(IKernelConfigSectionEntryZZZ objEntry) throws ExceptionZZZ {
		this.objEntryInner = objEntry;
	}
	
	@Override 
	public void adoptEntryValuesMissing(IKernelConfigSectionEntryZZZ objEntry) throws ExceptionZZZ {
		
		ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReferenceTarget = new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
		objReturnReferenceTarget.set(this.getEntry());
		
		ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReferenceSource = new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
		objReturnReferenceSource.set(objEntry);
		KernelConfigSectionEntryUtilZZZ.adoptEntryValuesMissing(objReturnReferenceTarget, objReturnReferenceSource);
		
		IKernelConfigSectionEntryZZZ objEntryTarget = objReturnReferenceTarget.get();
		this.setEntry(objEntryTarget);
	}
		
	// Die Methoden aus AbstractObjectWithExpressionZZZ muessen nur auf den Entry umgebogen werden.
	// Aber Achtung: Endlosschleifengefahr. Wg. Konstruktor mit diesem Objekt selbst als Uebergabe (this)
	//               Darum beim holen der Werte mit .get...() immer auf this.objEntry==null prüfen.

	//### Aus IValueBufferedUserZZZ
	@Override
	public VectorDifferenceZZZ<String> getValueVector() throws ExceptionZZZ {
		if(this.objEntryInner==null)return null; //!!!Sonst Endlosschleifengefahr.
		return this.getEntry().getValueVector();
	}
	
	@Override
	public String getValue() throws ExceptionZZZ {
		if(this.objEntryInner==null)return null; //!!!Sonst Endlosschleifengefahr. Wg. Konstruktor mit diesem Objekt selbst als Uebergabe (this)
		return this.getEntry().getValue();
	}

	@Override
	public void setValue(String sValue) throws ExceptionZZZ {
		this.getEntry().setValue(sValue);
	}
	
	@Override
	public boolean hasAnyValue() throws ExceptionZZZ {
		if(this.objEntryInner==null)return false; //!!!Sonst Endlosschleifengefahr. Wg. Konstruktor mit diesem Objekt selbst als Uebergabe (this)
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
		if(this.objEntryInner==null)return false; //!!!Sonst Endlosschleifengefahr. Wg. Konstruktor mit diesem Objekt selbst als Uebergabe (this)
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
	public VectorDifferenceZZZ<String> getRawVector() throws ExceptionZZZ {
		if(this.objEntryInner==null)return null; //!!!Sonst Endlosschleifengefahr. Wg. Konstruktor mit diesem Objekt selbst als Uebergabe (this)
		return this.getEntry().getRawVector();
	}

	@Override
	public String getRaw() throws ExceptionZZZ {
		if(this.objEntryInner==null)return null; //!!!Sonst Endlosschleifengefahr. Wg. Konstruktor mit diesem Objekt selbst als Uebergabe (this)
		return this.getEntry().getRaw();
	}

	@Override
	public void setRaw(String sRaw) throws ExceptionZZZ {
		this.getEntry().setRaw(sRaw);
	}
	
	//##############################
	//### aus IValueArrayUserZZZ
	//Merke: Arrays erst in ini-Tag behandeln, da es dafuer Separatorn in der Zeile geben muss
	@Override	
	public ArrayList<String> getValueArrayList() throws ExceptionZZZ{
		if(this.objEntryInner==null)return null; //!!!Sonst Endlosschleifengefahr. Wg. Konstruktor mit diesem Objekt selbst als Uebergabe (this)
		return this.getEntry().getValueArrayList();
	}

	@Override
	public void setValue(ArrayList<String> listasValue) throws ExceptionZZZ{
		this.getEntry().setValue(listasValue);		
	}
	
	@Override
	public boolean isArrayValue() throws ExceptionZZZ {
		if(this.objEntryInner==null)return false; //!!!Sonst Endlosschleifengefahr. Wg. Konstruktor mit diesem Objekt selbst als Uebergabe (this)
		return this.getEntry().isArrayValue();
	}

	@Override
	public void isArrayValue(boolean bIsArrayValue) throws ExceptionZZZ {
		this.getEntry().isArrayValue(bIsArrayValue);
	}
	
	
	//### Aus IParseEnabledZZZ		
	@Override
	public String parse(String sExpression) throws ExceptionZZZ{
		return this.parse_(sExpression, null, true);
	}	
	
	@Override
	public String parse(String sExpression, boolean bRemoveSurroundingSeparators) throws ExceptionZZZ{
		return this.parse_(sExpression, null, bRemoveSurroundingSeparators);
	}
	
	//++++++++++++++++++++++++++++++++++++++++++
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
		return this.parseAsEntry(sExpression, objReturnReferenceIn, bRemoveSurroundingSeparators);
	}
	
	private IKernelConfigSectionEntryZZZ parseAsEntry_(String sExpressionIn, ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReferenceIn, boolean bRemoveSurroundingSeparators) throws ExceptionZZZ{
		IKernelConfigSectionEntryZZZ objReturn = null;		
		String sReturn = sExpressionIn;
		boolean bUseExpression = false;
		
		ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference= null;		
		IKernelConfigSectionEntryZZZ objEntry = null;
		if(objReturnReferenceIn==null) {
			objReturnReference = new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
		}else {
			objReturnReference = objReturnReferenceIn;
		}
		objEntry = objReturnReference.get();
		if(objEntry==null) {
			//Nein, das holt auch ein neues inneres Objekt und die teilen sich dann die Referenz... objEntry = this.getEntryNew(); //Hier schon die Rückgabe vorbereiten, falls eine weitere Verarbeitung nicht konfiguriert ist.
										 //Wichtig: Als oberste Methode immer ein neues Entry-Objekt holen. Dann stellt man sicher, das nicht mit Werten der vorherigen Suche gearbeitet wird.
			objEntry = new KernelConfigSectionEntryZZZ<T>();
			objReturnReference.set(objEntry);
		}//Achtung: Das objReturn Objekt NICHT generell uebernehmen. Es verfaelscht bei einem 2. Suchaufruf das Ergebnis.
		this.setRaw(sExpressionIn);
		objEntry.setRaw(sExpressionIn);
		objReturn = objEntry;
	
		main:{
			if(StringZZZ.isEmptyTrimmed(sExpressionIn)) break main;

			
			//Es soll immer ein Entry Objekt zurückkommen, darum hier erst auf das Expression-Flag abpruefen.
			bUseExpression = this.getFlag(IIniTagWithExpressionZZZ.FLAGZ.USEEXPRESSION); 
			if(!bUseExpression) break main;
			
			//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!1
			//Merke: in Elternklassen gibt es diese Methode nur ohne Reference, da ohne KernelEbene das Objekt nicht vorhanden ist.
			//       Darum wird die Methode auch hier ueberschrieben.
			//Hier Methode nur ohne Reference... String sReturn = this.parse(sExpression, objReturnReferenceParse, bRemoveSurroundingSeparators);
			//Mit Reference geht ab: AbstractKernelIniTagSimpleZZZ
			ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReferenceParse = new ReferenceZZZ<IKernelConfigSectionEntryZZZ>(); 			
			objReturnReferenceParse.set(objEntry);
			String sExpression = sExpressionIn;
			sReturn = this.parse(sExpression, objReturnReferenceParse, bRemoveSurroundingSeparators);
			objEntry = objReturnReferenceParse.get();
			objReturn = objEntry;
		}//end main:
		
		if(objEntry!=null) {	
			objEntry.setValue(sReturn);
			objEntry.isParsed(true); 								
			if(!sExpressionIn.equals(sReturn)) objEntry.isParsedChanged(true); //zur Not nur, weil die Z-Tags entfernt wurden.									
						
			if(objReturnReferenceIn!=null) objReturnReferenceIn.set(objEntry);
			this.adoptEntryValuesMissing(objEntry);
		}	
		return objReturn;
	}
	
	//++++++++++++++++++++++++++++++++	
	//### Aus IKernelEntryExpressionUserZZZ
	@Override
	public String parse(String sExpression, ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference) throws ExceptionZZZ {
		return this.parse_(sExpression, objReturnReference, true);
	}
	
	@Override
	public String parse(String sExpression, ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReferenceIn, boolean bRemoveSurroundingSeparators) throws ExceptionZZZ {
		return this.parse_(sExpression, objReturnReferenceIn, bRemoveSurroundingSeparators);
	}
	
	private String parse_(String sExpressionIn, ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReferenceIn, boolean bRemoveSurroundingSeparators) throws ExceptionZZZ {
		String sReturn = sExpressionIn;
		Vector3ZZZ<String> vecReturn = new Vector3ZZZ<String>();
		boolean bUseExpression = false; boolean bUseParse = false;
		
		IKernelConfigSectionEntryZZZ objEntry = null;
		ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReference = null;
		if(objReturnReferenceIn==null) {	
			objReturnReference = new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
		}else {
			objReturnReference = objReturnReferenceIn;
			objEntry = objReturnReference.get();
		}
		if(objEntry==null) {
			//Nein, das holt auch ein neues inneres Objekt und die teilen sich dann die Referenz... objEntry = this.getEntryNew(); //Hier schon die Rückgabe vorbereiten, falls eine weitere Verarbeitung nicht konfiguriert ist.
			 //Wichtig: Als oberste Methode immer ein neues Entry-Objekt holen. Dann stellt man sicher, das nicht mit Werten der vorherigen Suche gearbeitet wird.
			objEntry = new KernelConfigSectionEntryZZZ<T>();
			objReturnReference.set(objEntry);							
		}//Achtung: Das objReturn Objekt NICHT generell versuchen mit .getEnry() und ggfs. dann darin .getEntryNew89 uebernehmen. Es verfaelscht bei einem 2. Suchaufruf das Ergebnis.
		this.setRaw(sExpressionIn);
		objEntry.setRaw(sExpressionIn);	
			
		main:{		
			if(StringZZZ.isEmpty(sExpressionIn)) break main;
			
			//Es soll immer ein Entry Objekt zurückkommen, darum hier erst auf das Expression-Flag abpruefen.
			bUseExpression = this.isExpressionEnabledAny();
			if(!bUseExpression) break main;
			
			String sExpression = sExpressionIn;
			
			
			//Falls man diesen Tag aus dem Parsen (des Gesamtstrings) rausnimmt, muessen die umgebenden Tags drin bleiben
			bUseParse = this.isParserEnabledThis();
			
			//Den ersten Vektor bearbeiten. Darin wird auch die Kernel Ini-Pfad/-Variablenersetzung gemacht
			ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReferenceParse = new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
			objReturnReferenceParse.set(objEntry);
			
			if(bUseParse) {
				vecReturn = this.parseFirstVector(sExpression, objReturnReferenceParse, bRemoveSurroundingSeparators);						
				objEntry = objReturnReferenceParse.get();			
				if(vecReturn==null) break main;		
			}else {
				vecReturn = this.parseFirstVector(sExpression, objReturnReferenceParse, false);						
				objEntry = objReturnReferenceParse.get();			
				if(vecReturn==null) break main;
			}
				
			sReturn = (String) vecReturn.get(1); //Der eigene Wert, ohne drumherum							
		}//end main:
		this.setValue(sReturn);
	
		//Als echten Ergebniswert aber die <Z>-Tags ggfs. rausrechnen, falls gewuenscht
		if(vecReturn!=null) vecReturn.replace(sReturn);
		if(bRemoveSurroundingSeparators & bUseExpression) {
			String sTagStartZ = "<Z>";
			String sTagEndZ = "</Z>";
			KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(vecReturn, sTagStartZ, sTagEndZ, true, false); //also von aussen nach innen!!!
			
			sReturn = (String) vecReturn.get(1);
			this.setValue(sReturn);
		}
		   
		if(objEntry!=null) {
			sReturn = VectorUtilZZZ.implode(vecReturn);	
			objEntry.setValue(sReturn);
			objEntry.isParsed(true);
			if(sExpressionIn!=null) {				 								
				if(!sExpressionIn.equals(sReturn)) objEntry.isParsedChanged(true); //zur Not nur, weil die Z-Tags entfernt wurden.
				if(bUseExpression) objEntry.isExpression(true);
			}			
			if(objReturnReferenceIn!=null) objReturnReferenceIn.set(objEntry);
			this.adoptEntryValuesMissing(objEntry);
		}		
		return sReturn;
	}
	
	//Die Idee ist, das die konkreten Klassen den ersten Vector parsen
	//also ggfs. überschreiben: public Vector<String>parseFirstVector(String sLineWithExpression, boolean bRemoveSurroundingSeparators) throws ExceptionZZZ{
	
	//### aus IParseEnabledZZZ
	@Override
	public Vector3ZZZ<String> parseFirstVector(String sExpression) throws ExceptionZZZ {
		//Muss ueberschrieben werden, damit die "einfache Tag" Methode nicht greift und wir mit der parse - Methode dieser konkreten Klasse arbeiten.
		return this.parseFirstVector_(sExpression, null, true);
	}
	
	@Override
	public Vector3ZZZ<String> parseFirstVector(String sExpression, boolean bRemoveSurroundingSeparators) throws ExceptionZZZ {
		//Muss ueberschrieben werden, damit die "einfache Tag" Methode nicht greift und wir mit der parse - Methode dieser konkreten Klasse arbeiten.
		return this.parseFirstVector_(sExpression, null, bRemoveSurroundingSeparators);
	}
	
	@Override
	public Vector3ZZZ<String> parseFirstVectorPost(Vector3ZZZ<String> vecExpression) throws ExceptionZZZ {
		return this.parseFirstVectorPost(vecExpression, null);
	}

	@Override
	public Vector3ZZZ<String> parseFirstVectorPost(Vector3ZZZ<String> vecExpression, boolean bRemoveSurroundingSeparators) throws ExceptionZZZ {
		return this.parseFirstVectorPost(vecExpression, null, bRemoveSurroundingSeparators);
	}
	
	//### Aus IKernelEntryReferenceExpressionUserZZZ	
	@Override
	public Vector3ZZZ<String>parseFirstVector(String sExpression, ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReference) throws ExceptionZZZ{
		return this.parseFirstVector_(sExpression, objReturnReference, true);
	}

	@Override
	public Vector3ZZZ<String> parseFirstVector(String sExpression, ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReferenceIn, boolean bRemoveSurroundingSeparators) throws ExceptionZZZ {
		return this.parseFirstVector_(sExpression, objReturnReferenceIn, bRemoveSurroundingSeparators);
	}
	
	private Vector3ZZZ<String> parseFirstVector_(String sExpression, ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReferenceIn, boolean bRemoveSurroundingSeparators) throws ExceptionZZZ {
		Vector3ZZZ<String>vecReturn = new Vector3ZZZ<String>();		
		String sReturn = sExpression;
		
		IKernelConfigSectionEntryZZZ objEntry = null;
		ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReference = null;			
		if(objReturnReferenceIn==null) {
			objReturnReference =  new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();								
			objEntry = new KernelConfigSectionEntryZZZ<T>(this); //this.getEntryNew(); es gingen alle Informationen verloren				
			                                                     //nein, dann gehen alle Informationen verloren   objReturn = this.parseAsEntryNew(sExpression);				
		}else {
			objReturnReference = objReturnReferenceIn;
			objEntry = objReturnReference.get();
		}
		
		if(objEntry==null) {
			//Das Ziel ist es moeglichst viel Informationen aus dem entry "zu retten"
			//Achtung: Das objReturn Objekt NICHT generell versuchen ueber .getEntry() und dann ggfs. .getEntryNew() zu uebernehmen. Es verfaelscht bei einem 2. Suchaufruf das Ergebnis.
			//objEntry = this.getEntry();
			objEntry = new KernelConfigSectionEntryZZZ<T>(this); // =  this.parseAsEntryNew(sExpression);  //nein, dann gehen alle Informationen verloren   objReturn = this.parseAsEntryNew(sExpression);
			objReturnReference.set(objEntry);
		}	
		objEntry.setRaw(sExpression);
		
		main:{
			if(StringZZZ.isEmpty(sExpression)) break main;
			if(!this.getFlag(IIniTagWithExpressionZZZ.FLAGZ.USEEXPRESSION)) break main;		
									
			vecReturn = super.parseFirstVector(sExpression, bRemoveSurroundingSeparators);
			if(vecReturn!=null) sReturn = (String)vecReturn.get(1);
			
			//20241023 Erweiterungsarbeiten, Ini-Pfade und Variablen "substituieren"
			sExpression = sReturn;
			ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReferenceSubstitute= new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
			sReturn = this.substituteParsed(sExpression, objReturnReferenceSubstitute, bRemoveSurroundingSeparators);			
			objEntry = objReturnReferenceSubstitute.get();
			this.setValue(sReturn);
			vecReturn.replace(sReturn); //da noch weiter verarbeitet werden muss.
			sExpression = sReturn; //fuer einen moeglichen naechsten Schritt
									
		}//end main:				
		
		if(vecReturn!=null) sReturn = (String) vecReturn.get(1); //der eigene Wert ohne drumherum	
		this.setValue(sReturn);
		
		if(objEntry!=null) {
			sReturn = VectorUtilZZZ.implode(vecReturn);
			objEntry.setValue(sReturn);
			if(objReturnReferenceIn!=null)objReturnReferenceIn.set(objEntry);
		}		
		return vecReturn;
	}
	
	//+++++++++++++++++++++++++++++++++++++++
	@Override
	public Vector3ZZZ<String> parseFirstVectorPost(Vector3ZZZ<String> vecExpression, ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference) throws ExceptionZZZ {
		return this.parseFirstVectorPost_(vecExpression, objReturnReference, true);
	}
	
	@Override
	public Vector3ZZZ<String> parseFirstVectorPost(Vector3ZZZ<String> vecExpression, ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReference, boolean bRemoveSurroundingSeparators) throws ExceptionZZZ {		
		return this.parseFirstVectorPost_(vecExpression, objReturnReference, bRemoveSurroundingSeparators);
	}
	
	private Vector3ZZZ<String> parseFirstVectorPost_(Vector3ZZZ<String> vecExpressionIn, ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReferenceIn, boolean bRemoveSurroundingSeparators) throws ExceptionZZZ {		
		Vector3ZZZ<String> vecReturn = vecExpressionIn;
		String sReturn = null;
		String sExpressionIn = null;		
		boolean bUseExpression = false; boolean bUseParse = false;
		
		IKernelConfigSectionEntryZZZ objEntry = null;
		ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReference = null;
		if(objReturnReferenceIn==null) {				
			objReturnReference = new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();								
		}else {
			objReturnReference = objReturnReferenceIn;
			objEntry = objReturnReference.get();
		}
		if(objEntry==null) {
			//Achtung: Das objReturn Objekt NICHT generell mit .getEntry() und darin ggfs. .getEntryNew() versuchen zu uebernehmen. Es verfaelscht bei einem 2. Suchaufruf das Ergebnis.
			objEntry = new KernelConfigSectionEntryZZZ<T>(this); //Das Ziel ist es moeglichst viel Informationen aus dem entry "zu retten"      =  this.parseAsEntryNew(sExpression);  //nein, dann gehen alle Informationen verloren   objReturn = this.parseAsEntryNew(sExpression);
			objReturnReference.set(objEntry);
		}							
		
		main:{			
			if(vecExpressionIn==null) break main;
		
			sExpressionIn = VectorUtilZZZ.implode(vecExpressionIn);
			if(StringZZZ.isEmpty(sExpressionIn)) break main;
			this.setRaw(sExpressionIn);
			objEntry.setRaw(sExpressionIn);
			
			sReturn = (String) vecExpressionIn.get(1);
			this.setValue(sReturn);
			
			bUseExpression = this.isExpressionEnabledAny(); 
			if(!bUseExpression) break main;						
							
			//Als echten Ergebniswert aber die konkreten <Z>-Tags (z.B. eines Solves) ggfs. rausrechnen, falls gewuenscht
			//Z...-Tags "aus der Mitte entfernen"... Wichtig für das Ergebnis eines Parsens
			bUseParse = this.isParserEnabledThis();
			if(bUseParse) {			
				if(bRemoveSurroundingSeparators) {
					String sTagStart = this.getTagStarting();
					String sTagEnd = this.getTagClosing();
					KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(vecReturn, sTagStart, sTagEnd);  //also von innen nach aussen
//					KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(vecReturn, sTagStart, sTagEnd, true, false); //also von aussen nach innen!!!
	
					sReturn = (String) vecReturn.get(1);
					this.setValue(sReturn);
				}	
			}else {
				//Wenn der Parser herausgenommen ist, seine Tags nicht entfernen.
			}
			
			
			//ggfs. weitere Sachen rausrechnen, falls gewuenscht
			vecReturn = this.parseFirstVectorPostCustom(vecReturn, bRemoveSurroundingSeparators);
			sReturn = (String) vecReturn.get(1);
			this.setValue(sReturn);
			
			//Als echten Ergebniswert immer die <Z>-Tags ggfs. rausrechnen
			if(bRemoveSurroundingSeparators) {
				String sTagStart = "<Z>";
				String sTagEnd = "</Z>";
				KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(vecReturn, sTagStart, sTagEnd, true, false); //also von aussen nach innen!!!
				
				sReturn = (String) vecReturn.get(1);
			}
			this.setValue(sReturn);
		}//end main:
				
		//#################################
	
		if(objEntry!=null) {
			sReturn = VectorUtilZZZ.implode(vecReturn);
			objEntry.setValue(sReturn);	
			if(sExpressionIn!=null) {
				objEntry.isExpression(true);
				objEntry.isParsed(true); 								
				if(!sExpressionIn.equals(sReturn)) objEntry.isParsedChanged(true); //zur Not nur, weil die Z-Tags entfernt wurden.									
			}			
			if(objReturnReferenceIn!=null) objReturnReferenceIn.set(objEntry);
		}
		return vecReturn;
	}

	
	//+++++++++++++++++++++++++++++++++++++++
	@Override
	public Vector3ZZZ<String> parseFirstVectorPostCustom(Vector3ZZZ<String> vecExpression, ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference) throws ExceptionZZZ {
		return this.parseFirstVectorPostCustom_(vecExpression, objReturnReference, true);
	}
	
	@Override
	public Vector3ZZZ<String> parseFirstVectorPostCustom(Vector3ZZZ<String> vecExpression, ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReference, boolean bRemoveSurroundingSeparators) throws ExceptionZZZ {		
		return this.parseFirstVectorPostCustom_(vecExpression, objReturnReference, bRemoveSurroundingSeparators);
	}
	
	//Methode mit Reference Objekt
	private Vector3ZZZ<String> parseFirstVectorPostCustom_(Vector3ZZZ<String> vecExpressionIn, ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReferenceIn, boolean bRemoveSurroundingSeparators) throws ExceptionZZZ {		
		Vector3ZZZ<String> vecReturn = vecExpressionIn;
		String sReturn = null;
		String sExpressionIn = null;		
		boolean bUseExpression = false;
		
		IKernelConfigSectionEntryZZZ objEntry = null;
		ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReference = null;
		if(objReturnReferenceIn==null) {				
			objReturnReference = new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();								
		}else {
			objReturnReference = objReturnReferenceIn;
			objEntry = objReturnReference.get();
		}
		if(objEntry==null) {
			//Achtung: Das objReturn Objekt NICHT generell mit .getEntry() und darin ggfs. .getEntryNew() versuchen zu uebernehmen. Es verfaelscht bei einem 2. Suchaufruf das Ergebnis.
			objEntry = new KernelConfigSectionEntryZZZ<T>(this); //Das Ziel ist es moeglichst viel Informationen aus dem entry "zu retten"      =  this.parseAsEntryNew(sExpression);  //nein, dann gehen alle Informationen verloren   objReturn = this.parseAsEntryNew(sExpression);
			objReturnReference.set(objEntry);
		}							
		
		main:{			
			if(vecExpressionIn==null) break main;
			
			sExpressionIn = VectorUtilZZZ.implode(vecExpressionIn);
			if(StringZZZ.isEmpty(sExpressionIn)) break main;
			this.setRaw(sExpressionIn);
			objEntry.setRaw(sExpressionIn);
			
			sReturn = (String) vecExpressionIn.get(1);
					
			bUseExpression = this.getFlag(IIniTagWithExpressionZZZ.FLAGZ.USEEXPRESSION); 
			if(!bUseExpression) break main;
				
			//.... hier könnte dann ein echter custom Code in einer Klasse stehen.
			
								
		}//end main:
				
		//#################################
		
		
		
		//Als echten Ergebniswert die <Z>-Tags ggfs. rausrechnen
		if(bRemoveSurroundingSeparators & bUseExpression) {
			String sTagStart = "<Z>";
			String sTagEnd = "</Z>";
			KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(vecReturn, sTagStart, sTagEnd, true, false); //also von aussen nach innen!!!
			
			sReturn = (String) vecReturn.get(1);
		}
		this.setValue(sReturn);
		
		
		if(objEntry!=null) {
			sReturn = VectorUtilZZZ.implode(vecReturn);
			objEntry.setValue(sReturn);	
			if(sExpressionIn!=null) {
				objEntry.isExpression(true);
				objEntry.isParsed(true); 								
				if(!sExpressionIn.equals(sReturn)) objEntry.isParsedChanged(true); //zur Not nur, weil die Z-Tags entfernt wurden.									
			}			
			if(objReturnReferenceIn!=null) objReturnReferenceIn.set(objEntry);
		}
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
	public Vector3ZZZ<String>parseFirstVectorAsExpression(String sExpression) throws ExceptionZZZ{
		Vector3ZZZ<String> vecReturn = null;

		main:{
			if(sExpression==null) break main;
			
			vecReturn = new Vector3ZZZ<String>();
			if(StringZZZ.isEmpty(sExpression)) break main;
			
			if(! this.getFlag(IIniTagWithExpressionZZZ.FLAGZ.USEEXPRESSION)) break main;
								
			//Bei dem einfachen Tag wird die naechste Tag genommen und dann auch das naechste schliessende Tag...
			//vecReturn = StringZZZ.vecMidFirst(sExpression, this.getTagStarting(), this.getTagClosing(), true, false);
			vecReturn = this.parseFirstVector(sExpression, false);
				
		}//end main:			
		return vecReturn;
	}
	
	@Override
	public Vector3ZZZ<String>parseAllVectorAsExpression(String sExpression) throws ExceptionZZZ{
		Vector3ZZZ<String> vecReturn = null;		
		main:{
			if(sExpression==null) break main;
			
			vecReturn = new Vector3ZZZ<String>();
			if(StringZZZ.isEmpty(sExpression)) break main;
			
			if(! this.getFlag(IIniTagWithExpressionZZZ.FLAGZ.USEEXPRESSION)) break main;
								
			//Merke: Das ist der Fall, das ein Ausdruck NICHT verschachtelt ist
			//       Für verschachtelte Tags muss hier extra was programmiert und diese Methode ueberschrieben werden.
			vecReturn = this.parseFirstVectorAsExpression(sExpression); 			
			
		}//end main:
		
		return vecReturn;
	}
	
	//### aus IConvertableZZZ
		
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
	
	//### aus ISubstituteEnabledZZZ
		@Override
		public boolean isSubstituteRelevant() throws ExceptionZZZ {
			return true;
		}
		
		
		@Override
		public boolean isSubstitute(String sExpression) throws ExceptionZZZ {
			boolean bReturn = false;
			main:{
				bReturn = this.isSubstitutePath(sExpression);
				if(bReturn) break main;
				
				bReturn = this.isSubstituteVariable(sExpression);
				if(bReturn) break main;
							
			}//end main;
			return bReturn;
		}
		
		@Override
		public boolean isSubstitutePath(String sExpression) throws ExceptionZZZ {
			return ExpressionIniUtilZZZ.isParse(sExpression, KernelZFormulaIni_PathZZZ.sTAG_NAME, false);
		}
		
		@Override
		public boolean isSubstituteVariable(String sExpression) throws ExceptionZZZ {
			return ExpressionIniUtilZZZ.isParse(sExpression, ZTagFormulaIni_VariableZZZ.sTAG_NAME, false);
		}
		@Override
		public String substitute(String sExpression) throws ExceptionZZZ {
			return this.substitute_(sExpression, null, true);
		}

		@Override
		public String substitute(String sExpression, boolean bRemoveSurroundingSeparators) throws ExceptionZZZ {
			return this.substitute_(sExpression, null, bRemoveSurroundingSeparators);
		}
			
		//### aus IKernelEntryReferenceSubstituteUserZZZ
		@Override
		public String substitute(String sExpressionIn, ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReferenceIn,	boolean bRemoveSurroundingSeparators) throws ExceptionZZZ {
			return this.substitute_(sExpressionIn, objReturnReferenceIn, bRemoveSurroundingSeparators);
		}
		
		@Override
		public String substitute(String sExpressionIn, ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReferenceIn) throws ExceptionZZZ {
			return this.substitute_(sExpressionIn, objReturnReferenceIn, true);
		}
		
		private String substitute_(String sExpressionIn, ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReferenceIn,	boolean bRemoveSurroundingSeparators) throws ExceptionZZZ {
			String sReturn=sExpressionIn;
			Vector3ZZZ<String> vecReturn = new Vector3ZZZ<String>();
			
			ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference = null;
			IKernelConfigSectionEntryZZZ objEntry = null;
			if(objReturnReferenceIn==null) {
				objReturnReference = new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
			}else {
				objReturnReference = objReturnReferenceIn;
			}
			
			objEntry = objReturnReference.get();
			if(objEntry==null) {
				objEntry = this.getEntryNew(); //Hier schon die Rückgabe vorbereiten, falls eine weitere Verarbeitung nicht konfiguriert ist.
											   //Wichtig: Als oberste Methode immer ein neues Entry-Objekt holen. Dann stellt man sicher, das nicht mit Werten der vorherigen Suche gearbeitet wird.
				//unten objEntry immer an das ReferenceOjekt zurueckgeben
			}//Achtung: Das objReturn Objekt NICHT generell uebernehmen. Es verfaelscht bei einem 2. Suchaufruf das Ergebnis.
			this.setRaw(sExpressionIn);
			objEntry.setRaw(sExpressionIn);
			
			main:{
				if(StringZZZ.isEmptyTrimmed(sExpressionIn)) break main;
				
				if(!this.getFlag(IIniTagWithExpressionZZZ.FLAGZ.USEEXPRESSION)) break main;			
							
				//Rufe nun parse() auf...
				ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReferenceParse= new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
				objReturnReferenceParse.set(objEntry); 
				vecReturn = this.parseFirstVector(sExpressionIn, objReturnReferenceParse, bRemoveSurroundingSeparators);
				objEntry = objReturnReferenceParse.get();
				
				String sExpressionParsed = (String) vecReturn.get(1);
				if(!sExpressionIn.equals(sExpressionParsed)) {				
					objEntry.isParsed(true);
				}
				sReturn = sExpressionParsed; //Zwischenstand.			
				
				
				//Rufe nun substituteParsed() auf...		
				if(!this.getFlag(IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE)
				|  !this.getFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH)) break main;
				
				
				ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReferenceSolve= new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
				objReturnReferenceSolve.set(objEntry);
				String sExpressionSolved = this.substituteParsed(sExpressionParsed, objReturnReferenceSolve, bRemoveSurroundingSeparators);
				objEntry = objReturnReferenceSolve.get();
				
				if(!sExpressionParsed.equals(sExpressionSolved)) {
					objEntry.isSolved(true);
				}			
				sReturn = sExpressionSolved; //Zwischenstand.	
																			
			}//end main:
					
			//NUN DEN INNERHALB DER EXPRESSION BERECHUNG ERSTELLTEN WERT uebernehmen
			this.setValue(sReturn);		
			vecReturn.replace(sReturn);
			if(objEntry!=null) {			
				sReturn = VectorUtilZZZ.implode(vecReturn);
				objEntry.setValue(sReturn);
				if(sExpressionIn!=null) {
					if(!sExpressionIn.equals(sReturn)) objEntry.isExpression(true);
				}			
				if(objReturnReferenceIn!=null)objReturnReferenceIn.set(objEntry);//Wichtig: Reference nach aussen zurueckgeben.
				this.adoptEntryValuesMissing(objEntry);
			}
			return sReturn;	
		}
		
		
		//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		@Override
		public String substituteParsed(String sExpression) throws ExceptionZZZ {
			return this.substituteParsed_(sExpression, null, true);
		}
		
		@Override
		public String substituteParsed(String sExpression,ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference) throws ExceptionZZZ {		
			return this.substituteParsed_(sExpression, objReturnReference, true);
		}
		
		@Override
		public String substituteParsed(String sExpressionIn, ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReferenceIn, boolean bRemoveSurroundingSeparators)	throws ExceptionZZZ {
			return this.substituteParsed_(sExpressionIn, objReturnReferenceIn, bRemoveSurroundingSeparators);
		}

		@Override
		public String substituteParsed(Vector3ZZZ<String> vecExpression) throws ExceptionZZZ {
			return this.substituteParsed_(vecExpression, null, true);
		}

		@Override
		public String substituteParsed(Vector3ZZZ<String> vecExpression, boolean bRemoveSurroundingSeparators) throws ExceptionZZZ {
			return this.substituteParsed_(vecExpression, null, bRemoveSurroundingSeparators);
		}
		
		@Override
		public String substituteParsed(Vector3ZZZ<String> vecExpression, ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference, boolean bRemoveSurroundingSeparators) throws ExceptionZZZ {
			return this.substituteParsed_(vecExpression, objReturnReference, bRemoveSurroundingSeparators);
		}
		
		@Override
		public String substituteParsed(String sExpression, boolean bRemoveSurroundingSeparators) throws ExceptionZZZ {
			return this.substituteParsed_(sExpression, null, bRemoveSurroundingSeparators);
		}

		/* Aufloesen der INI-Pfade und Variablen. */
		private String substituteParsed_(String sExpressionIn, ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReferenceIn, boolean bRemoveSurroundingSeparators)	throws ExceptionZZZ {
			String sReturn = sExpressionIn; //Darin können also auch Variablen, etc. sein
			String sExpression = sExpressionIn;
			
			ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference= null;		
			IKernelConfigSectionEntryZZZ objEntry = null;
			if(objReturnReferenceIn==null) {
				objReturnReference = new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
			}else {
				objReturnReference = objReturnReferenceIn;
			}
			objEntry = objReturnReference.get();
			if(objEntry==null) {
				//Nein, das holt auch ein neues inneres Objekt und die teilen sich dann die Referenz... objEntry = this.getEntryNew(); //Hier schon die Rückgabe vorbereiten, falls eine weitere Verarbeitung nicht konfiguriert ist.
				 //Wichtig: Als oberste Methode immer ein neues Entry-Objekt holen. Dann stellt man sicher, das nicht mit Werten der vorherigen Suche gearbeitet wird.
				objEntry = new KernelConfigSectionEntryZZZ<T>();
				objReturnReference.set(objEntry);
			}//Achtung: Das objReturn Objekt NICHT generell uebernehmen. Es verfaelscht bei einem 2. Suchaufruf das Ergebnis.
			this.setRaw(sExpressionIn);
			objEntry.setRaw(sExpressionIn);	
							
			main:{
				if(StringZZZ.isEmptyTrimmed(sExpressionIn)) break main;			
									
				if(!this.getFlag(IIniTagWithExpressionZZZ.FLAGZ.USEEXPRESSION)) break main;			
							
				if(this.getFlag(IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE)) {
					
					//Pruefe vorher ob ueberhaupt eine Variable in der Expression definiert ist
					if(ExpressionIniUtilZZZ.isParse(sExpression, ZTagFormulaIni_VariableZZZ.sTAG_NAME, false)) {
					
						//ZUERST: Löse ggfs. übergebene Variablen auf.
						//!!! WICHTIG: BEI DIESEN AUFLOESUNGEN NICHT DAS UEBERGEORNETE OBJENTRY VERWENDEN, SONDERN INTERN EIN EIGENES!!! 
											
						//Merke: Fuer einfache Tag gibt es keine zu verarbeitenden Flags, also muss man auch keine suchen und uebergeben.
						//       Hier aber ein 
						String sExpressionOld = sExpression;
						String sExpressionTemp;					
						
						ZTagFormulaIni_VariableZZZ<T> exDummy = new ZTagFormulaIni_VariableZZZ<T>();
						String[] saFlagZpassed = FlagZFassadeZZZ.seekFlagZrelevantForObject(this, exDummy, true); //this.getFlagZ_passable(true, exDummy);
							
						ZTagFormulaIni_VariableZZZ<T> objVariable = new ZTagFormulaIni_VariableZZZ<T>(this.getHashMapVariable(), saFlagZpassed); 
						while(objVariable.isExpression(sExpression)){
							Vector3ZZZ<String> vecExpressionTemp =  objVariable.parseFirstVector(sExpression, true); //auf jeden Fall um Variablen herum den Z-Tag entfernen
							if(vecExpressionTemp==null) break;
							
							sExpressionTemp = (String) vecExpressionTemp.get(1);
							if(StringZZZ.isEmpty(sExpressionTemp)) {
								break;
							}else if(sExpression.equals(sExpressionTemp)) {
								break;
							}else{
								sExpression = VectorUtilZZZ.implode(vecExpressionTemp);					
							}
						} //end while
						sReturn = sExpression;
						this.setValue(sReturn);
						objEntry.setValue(sReturn);
						if(sReturn!=sExpressionOld) {
							objEntry.isParsed(true);
							objEntry.isVariableSubstituted(true);
						}
						sExpression = sReturn; //fuer ggfs. notwendige Weiterverarbeitung
					}
				}	
				
				if(this.getFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH)) {	
					
					//Pruefe vorher ob ueberhaupt eine Variable in der Expression definiert ist
					if(ExpressionIniUtilZZZ.isParseRegEx(sExpression, KernelZFormulaIni_PathZZZ.sTAG_NAME, false)) {
						
						//DANACH: ALLE PATH-Ausdrücke, also [xxx]yyy ersetzen
						//Problem hier: [ ] ist auch der JSON Array-Ausdruck
						String sExpressionOld = sExpression;
						String sExpressionTemp;
						
						KernelZFormulaIni_PathZZZ<T> exDummy = new KernelZFormulaIni_PathZZZ<T>();
						String[] saFlagZpassed = FlagZFassadeZZZ.seekFlagZrelevantForObject(this, exDummy, true); //this.getFlagZ_passable(true, exDummy);
										
						KernelZFormulaIni_PathZZZ<T> objFormulaIniPath = new KernelZFormulaIni_PathZZZ<T>(this.getKernelObject(), this.getFileConfigKernelIni(), saFlagZpassed);
						while(objFormulaIniPath.isExpression(sExpression)){
							//Problem [ ] aus dem JSON:ARRAY (Z.B.: <Z><JSON><JSON:ARRAY>["TESTWERT2DO2JSON01","TESTWERT2DO2JSON02"]</JSON:ARRAY></JSON></Z> )
					        //sind auch Bestandteile des INI-Pfads (z.B. [SECTION]Property )
							//Der INI-Pfad wird per RegEx-Ausdruck definiert.
							//IDEE: Der RegEx-Ausdruck muss Hochkommata zwischen den [ ] ausschliessen!
					   
							Vector3ZZZ<String> vecExpressionTemp = objFormulaIniPath.parseFirstVector(sExpression, true); //auf jeden Fall um PATH-Anweisungen herum den Z-Tag entfernen
							if(vecExpressionTemp==null) break;
								
							sExpressionTemp = VectorUtilZZZ.implode(vecExpressionTemp);	
							if(StringZZZ.isEmpty(sExpressionTemp)) {
								break;
							}else if(sExpression.equals(sExpressionTemp)) {
								break;
							}else{
								sExpression = sExpressionTemp;						
							}											
						} //end while
						sReturn = sExpression;
						this.setValue(sReturn);
						objEntry.setValue(sReturn);
						if(!sExpressionOld.equals(sReturn)) {
							objEntry.isParsed(true);
							objEntry.isPathSubstituted(true);
						}
						sExpression = sReturn;  //fuer ggfs. notwendige Weiterverarbeitung
					}//end if .isParseRegEx();
				}//end if .getFlag(..USE_...Path...)
				
				//Merke: Weitere Aufloesung bedarf das explizite solver-Flag
				//if(!this.getFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER)) break main;
				
			}//end main:
			
			//NUN DEN INNERHALB DER EXPRESSION BERECHUNG ERSTELLTEN WERT uebernehmen
			//Der Wert ist nur der TagInhalt this.setValue(sReturn);		
			if(objEntry!=null) {		
				objEntry.setValue(sReturn);
	//20241011: Nein, diese Aufloesung hat mit den eigentlichen Solvern nix zu tun!!!
//				if(sExpressionIn!=null) {
//					if(!sExpressionIn.equals(sReturn)) objEntry.isSolved(true);
//				}
				if(objReturnReferenceIn!=null)objReturnReferenceIn.set(objEntry);
			}
			return sReturn;	
		}
		
		private String substituteParsed_(Vector3ZZZ<String> vecExpression, ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReferenceIn, boolean bRemoveSurroundingSeparators) throws ExceptionZZZ {
			String sReturn = "";
			main:{
				if(vecExpression==null) break main;
				
				String sExpression = (String) vecExpression.get(1);
				sReturn = this.substituteParsed(sExpression, objReturnReferenceIn, bRemoveSurroundingSeparators);
					
				//NUN DEN INNERHALB DER EXPRESSION BERECHUNG ERSTELLTEN WERT uebernehmen
				this.setValue(sReturn);		
				vecExpression.replace(sReturn);
				if(objEntryInner!=null) {			
					sReturn = VectorUtilZZZ.implode(vecExpression);
					objEntryInner.setValue(sReturn);				
				}	
			}//end main:
			return sReturn;
		}
		
		
	//### Aus IValueVariableUserZZZ
	@Override
	public void setHashMapVariable(HashMapCaseInsensitiveZZZ<String,String> hmVariable) throws ExceptionZZZ{				
		this.hmVariable = hmVariable;
	}
	
	@Override
	public HashMapCaseInsensitiveZZZ<String,String> getHashMapVariable() throws ExceptionZZZ{
		if(this.hmVariable==null) {
			this.hmVariable = new HashMapCaseInsensitiveZZZ<String, String>();
		}
		return this.hmVariable;
	}
	
	@Override
	public void setVariable(HashMapCaseInsensitiveZZZ<String,String> hmVariable) throws ExceptionZZZ{
		if(this.hmVariable==null){
			this.hmVariable = hmVariable;
		}else{
			if(hmVariable==null){
				//nix....
			}else{
				//füge Werte hinzu.
				Set<String> sSet =  hmVariable.keySet();
				for(String sKey : sSet){
					this.hmVariable.put(sKey, (String)hmVariable.get(sKey));
				}
			}
		}	
	}
	
	@Override
	public void setVariable(String sVariable, String sValue) throws ExceptionZZZ{
		this.getHashMapVariable().put(sVariable, sValue);
	}
	
	@Override
	public String getVariable(String sKey) throws ExceptionZZZ{
		return (String) this.getHashMapVariable().get(sKey);
	}
	
	//###################################
	//### FLAG Handling
	@Override
	public boolean getFlag(IKernelZFormulaIni_VariableZZZ.FLAGZ objEnum_IKernelZFormulaIni_VariableZZZ) {
		return this.getFlag(objEnum_IKernelZFormulaIni_VariableZZZ.name());
	}
	
	@Override
	public boolean setFlag(IKernelZFormulaIni_VariableZZZ.FLAGZ objEnum_IKernelZFormulaIni_VariableZZZ, boolean bFlagValue) throws ExceptionZZZ {
		return this.setFlag(objEnum_IKernelZFormulaIni_VariableZZZ.name(), bFlagValue);
	}
			
	@Override
	public boolean[] setFlag(IKernelZFormulaIni_VariableZZZ.FLAGZ[] objaEnum_IKernelZFormulaIni_VariableZZZ, boolean bFlagValue) throws ExceptionZZZ {
		boolean[] baReturn=null;
		main:{
			if(!ArrayUtilZZZ.isNull(objaEnum_IKernelZFormulaIni_VariableZZZ)) {
				baReturn = new boolean[objaEnum_IKernelZFormulaIni_VariableZZZ.length];
				int iCounter=-1;
				for(IKernelZFormulaIni_VariableZZZ.FLAGZ objEnum_IKernelZFormulaIni_VariableZZZ:objaEnum_IKernelZFormulaIni_VariableZZZ) {
					iCounter++;
					boolean bReturn = this.setFlag(objEnum_IKernelZFormulaIni_VariableZZZ, bFlagValue);
					baReturn[iCounter]=bReturn;
				}
			}
		}//end main:
		return baReturn;
	}

	@Override
	public boolean proofFlagExists(IKernelZFormulaIni_VariableZZZ.FLAGZ objEnum_IKernelZFormulaIni_VariableZZZ) throws ExceptionZZZ {
		return this.proofFlagExists(objEnum_IKernelZFormulaIni_VariableZZZ.name());
	}
	
	@Override
	public boolean proofFlagSetBefore(IKernelZFormulaIni_VariableZZZ.FLAGZ objEnum_IKernelZFormulaIni_VariableZZZ) throws ExceptionZZZ {
			return this.proofFlagSetBefore(objEnum_IKernelZFormulaIni_VariableZZZ.name());
	}
	
	//### aus IKernelZFormulaIni_PathZZZ	
	@Override
	public boolean getFlag(IKernelZFormulaIni_PathZZZ.FLAGZ objEnumFlag) {
		return this.getFlag(objEnumFlag.name());
	}
	@Override
	public boolean setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ objEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		return this.setFlag(objEnumFlag.name(), bFlagValue);
	}
	
	@Override
	public boolean[] setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ[] objaEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		boolean[] baReturn=null;
		main:{
			if(!ArrayUtilZZZ.isNull(objaEnumFlag)) {
				baReturn = new boolean[objaEnumFlag.length];
				int iCounter=-1;
				for(IKernelZFormulaIni_PathZZZ.FLAGZ objEnumFlag:objaEnumFlag) {
					iCounter++;
					boolean bReturn = this.setFlag(objEnumFlag, bFlagValue);
					baReturn[iCounter]=bReturn;
				}
			}
		}//end main:
		return baReturn;
	}
	
	@Override
	public boolean proofFlagExists(IKernelZFormulaIni_PathZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
			return this.proofFlagExists(objEnumFlag.name());
	}
	
	@Override
	public boolean proofFlagSetBefore(IKernelZFormulaIni_PathZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
			return this.proofFlagSetBefore(objEnumFlag.name());
	}
}// End class