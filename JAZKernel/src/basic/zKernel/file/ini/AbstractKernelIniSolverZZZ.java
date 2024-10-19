package basic.zKernel.file.ini;

import java.util.Set;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IConvertEnabledZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.abstractList.HashMapCaseInsensitiveZZZ;
import basic.zBasic.util.abstractList.Vector3ZZZ;
import basic.zBasic.util.abstractList.VectorUtilZZZ;
import basic.zBasic.util.crypt.code.ICryptUserZZZ;
import basic.zBasic.util.crypt.code.ICryptZZZ;
import basic.zBasic.util.datatype.calling.ReferenceZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.xml.tagsimple.IParseEnabledZZZ;
import basic.zKernel.IKernelConfigSectionEntryUserZZZ;
import basic.zKernel.IKernelConfigSectionEntryZZZ;
import basic.zKernel.IKernelEntryReferenceSolveUserZZZ;
import basic.zKernel.IKernelFileIniUserZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernel.KernelConfigSectionEntryZZZ;
import basic.zKernel.config.KernelConfigSectionEntryUtilZZZ;
import basic.zKernel.flag.util.FlagZFassadeZZZ;
import custom.zKernel.file.ini.FileIniZZZ;

/**Merke: Es gibt auch solver, die unabhaengig von einem ini-File funktionieren sollen.
 *        Darum 2 Kategorien bei den Konstruktoren
 * @author fl86kyvo
 *
 * @param <T>
 */
public abstract class AbstractKernelIniSolverZZZ<T>  extends AbstractKernelIniTagCascadedZZZ<T> implements IKernelFileIniUserZZZ, IKernelEntryReferenceSolveUserZZZ, IKernelExpressionIniSolverZZZ,  IKernelZFormulaIni_PathZZZ, IKernelZFormulaIni_VariableZZZ, IKernelConfigSectionEntryUserZZZ, ICryptUserZZZ, IValueVariableUserZZZ, IParseEnabledZZZ, ISolveEnabledZZZ, IConvertEnabledZZZ{
	private static final long serialVersionUID = -4816468638381054061L;
	protected HashMapCaseInsensitiveZZZ<String,String> hmVariable =null;
	protected ICryptZZZ objCrypt=null; //Das Verschlüsselungs-Algorithmus-Objekt, falls der Wert verschlüsselt ist.
			
	
	public AbstractKernelIniSolverZZZ() throws ExceptionZZZ{
		super("init");
		AbstractKernelIniSolverNew1_(null);
	}
		
	public AbstractKernelIniSolverZZZ(String sFlag) throws ExceptionZZZ{
		super(sFlag);
		AbstractKernelIniSolverNew1_(null);
	}
			
	public AbstractKernelIniSolverZZZ(String[] saFlag) throws ExceptionZZZ{
		super(saFlag);
		AbstractKernelIniSolverNew1_(null);
	}
	
	public AbstractKernelIniSolverZZZ(IKernelZZZ objKernel) throws ExceptionZZZ{
		super(objKernel);
		AbstractKernelIniSolverNew1_(null);
	}
	
	//Merke: Konstruktoren ohne ini-File machen keinen Sinn und muessen ohne "init"-Flag zu Exception fuehren
	public AbstractKernelIniSolverZZZ(IKernelZZZ objKernel, String[] saFlag) throws ExceptionZZZ{
		super(objKernel, saFlag);
		AbstractKernelIniSolverNew_(null, null);
	}
	
	//Merke: Konstruktoren ohne ini-File machen keinen Sinn und muessen ohne "init"-Flag zu Exception fuehren
	public AbstractKernelIniSolverZZZ(FileIniZZZ<T> objFileIniKernelConfig) throws ExceptionZZZ{
		super(objFileIniKernelConfig); //Inifile als IKernelUserZZZ		
		AbstractKernelIniSolverNew_(objFileIniKernelConfig, null);		
	}
	
	public AbstractKernelIniSolverZZZ(FileIniZZZ<T> objFileIniKernelConfig, String[] saFlag) throws ExceptionZZZ{
		super(objFileIniKernelConfig, saFlag);//Inifile als IKernelUserZZZ
		AbstractKernelIniSolverNew_(objFileIniKernelConfig, null);
	}
	
	public AbstractKernelIniSolverZZZ(FileIniZZZ<T> objFileIniKernelConfig, HashMapCaseInsensitiveZZZ<String,String> hmVariable) throws ExceptionZZZ{
		super(objFileIniKernelConfig, null);//Inifile als IKernelUserZZZ		
		AbstractKernelIniSolverNew_(objFileIniKernelConfig, hmVariable);		
	}
	
	public AbstractKernelIniSolverZZZ(FileIniZZZ<T> objFileIniKernelConfig, HashMapCaseInsensitiveZZZ<String,String> hmVariable, String[] saFlag) throws ExceptionZZZ{
		super(objFileIniKernelConfig, saFlag);//Inifile als IKernelUserZZZ		
		AbstractKernelIniSolverNew_(objFileIniKernelConfig, hmVariable);		
	}
	
	
	public AbstractKernelIniSolverZZZ(IKernelZZZ objKernel, FileIniZZZ<T> objFileIni) throws ExceptionZZZ{
		super(objKernel, null);
		AbstractKernelIniSolverNew_(objFileIni, null);
	}
	
	public AbstractKernelIniSolverZZZ(IKernelZZZ objKernel, FileIniZZZ<T> objFileIni, HashMapCaseInsensitiveZZZ<String,String> hmVariable) throws ExceptionZZZ{
		super(objKernel, null);
		AbstractKernelIniSolverNew_(objFileIni, hmVariable);
	}
	
	public AbstractKernelIniSolverZZZ(IKernelZZZ objKernel, FileIniZZZ<T> objFileIni, String[] saFlag) throws ExceptionZZZ{
		super(objKernel, saFlag);
		AbstractKernelIniSolverNew_(objFileIni, null);
	}
	
	public AbstractKernelIniSolverZZZ(IKernelZZZ objKernel, FileIniZZZ<T> objFileIni, HashMapCaseInsensitiveZZZ<String,String> hmVariable, String[] saFlag) throws ExceptionZZZ{
		super(objKernel, saFlag);
		AbstractKernelIniSolverNew_(objFileIni, hmVariable);
	}
		
	private boolean AbstractKernelIniSolverNew_(FileIniZZZ<T> objFileIniIn, HashMapCaseInsensitiveZZZ<String,String> hmVariable) throws ExceptionZZZ {
		
	boolean bReturn = false;		 
		main:{
			if(this.getFlag("init")==true){
				bReturn = true;
				break main;
			}
			
			//Merke: Konstruktoren ohne ini-File machen keinen Sinn und muessen ohne "init"-Flag zu Exception fuehren
			FileIniZZZ<T> objFileIni = null;
			if(objFileIniIn==null ){
				IKernelZZZ objKernel = this.getKernelObject();
				if(objKernel==null) {
					ExceptionZZZ ez = new ExceptionZZZ("Kernel-Object or FileIni-Object", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
					throw ez; 
				}
				
				objFileIni = objKernel.getFileConfigKernelIni();				
				if(objFileIni == null) {
					ExceptionZZZ ez = new ExceptionZZZ("FileIni-Object", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
					throw ez; 
				}
			}else{
				objFileIni = objFileIniIn;
			}
			
			this.setFileConfigKernelIni(objFileIni);	
				
			if(hmVariable!=null){				
				this.setVariable(hmVariable);					
			}else if(hmVariable == null && objFileIni.getHashMapVariable() !=null){
				this.setHashMapVariable(objFileIni.getHashMapVariable());
			}else {
				//soll zu den Variablen aus dem Ini-File hinzuaddieren, bzw. ersetzen
				//TODOGOON20240806; //HashMapCaseInsensitive PLus HashMapCaseInsensitive..
			}
			
			
			bReturn = true;
	 	}//end main:
		return bReturn;
	}//AbstractKernelIniSolverNew_
	
	
	private boolean AbstractKernelIniSolverNew1_(HashMapCaseInsensitiveZZZ<String,String> hmVariable) throws ExceptionZZZ {		
		boolean bReturn = false;		 
		main:{
			if(this.getFlag("init")==true){
				bReturn = true;
				break main;
			}
			
	
			if(hmVariable!=null){				
				this.setVariable(hmVariable);									
			}
			
			bReturn = true;
	 	}//end main:
		return bReturn;
	}//AbstractKernelIniSolverNew2_
	
	//### Aus Interface ICryptUserZZZ
	@Override
	public ICryptZZZ getCryptAlgorithmType() throws ExceptionZZZ {
		return this.objCrypt;
	}

	@Override
	public void setCryptAlgorithmType(ICryptZZZ objCrypt) {
		this.objCrypt = objCrypt;
	}
		
	//### Aus ISolveEnabled
	@Override
	public boolean isSolveRelevant(String sExpression) throws ExceptionZZZ {
		return this.isSolve(sExpression);
	}

	@Override
	public boolean isSolve(String sExpression) throws ExceptionZZZ {
		return this.isExpression(sExpression);
	}
	
	@Override
	public String solveParsed(Vector3ZZZ<String> vecExpression) throws ExceptionZZZ {
		return this.solveParsed_(vecExpression, null, true);
	}

	@Override
	public String solveParsed(Vector3ZZZ<String> vecExpression, boolean bRemoveSuroundingSeparators) throws ExceptionZZZ {
		return this.solveParsed_(vecExpression, null, bRemoveSuroundingSeparators);
	}
	
	@Override
	public String solveParsed(Vector3ZZZ<String> vecExpression, ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReference, boolean bRemoveSuroundingSeparators) throws ExceptionZZZ {
		return this.solveParsed_(vecExpression, objReturnReference, bRemoveSuroundingSeparators);
	}

	private String solveParsed_(Vector3ZZZ<String> vecExpression, ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReference, boolean bRemoveSuroundingSeparators) throws ExceptionZZZ {
		String sReturn = "";
		main:{
			if(vecExpression==null) break main;
			
			String sExpression = (String) vecExpression.get(1);
			sReturn = this.solveParsed(sExpression, objReturnReference, bRemoveSuroundingSeparators);
			
			//NUN DEN INNERHALB DER EXPRESSION BERECHUNG ERSTELLTEN WERT uebernehmen
			this.setValue(sReturn);		
			vecExpression.replace(sReturn);
			if(objEntry!=null) {			
				sReturn = VectorUtilZZZ.implode(vecExpression);
				objEntry.setValue(sReturn);				
			}						
		}//end main:
		return sReturn;
	}
	
	@Override
	public String solve(String sExpression) throws ExceptionZZZ{
		return this.solve_(sExpression, true);
	}
	
	@Override
	public String solve(String sExpression, boolean bRemoveSuroundingSeparators) throws ExceptionZZZ {
		return this.solve_(sExpression, bRemoveSuroundingSeparators);
	}
	
	/** Methoder ruft erst parse() auf, dann solvedParsed()**/
	private String solve_(String sExpression, boolean bRemoveSuroundingSeparators) throws ExceptionZZZ {
		String sReturn = sExpression;
		
		//Wichtig: Als oberste Methode immer ein neues Entry-Objekt holen. Dann stellt man sicher, das nicht mit Werten der vorherigen Suche gearbeitet wird.
		IKernelConfigSectionEntryZZZ objReturn = this.getEntryNew(); //Hier schon die Rückgabe vorbereiten, falls eine weitere Verarbeitung nicht konfiguriert ist.		
		objReturn.setRaw(sExpression);

		main:{			
			ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
			objReturnReference.set(objReturn);
			
			sReturn = this.solve(sExpression, objReturnReference, bRemoveSuroundingSeparators);
		}//end main:						
		return sReturn;

	}

	@Override
	public String solveParsed(String sExpression) throws ExceptionZZZ {
		return this.solveParsed_(sExpression, null, true);
	}

	@Override
	public String solveParsed(String sExpression, boolean bRemoveSurroundingSeparators)throws ExceptionZZZ {
		return this.solveParsed_(sExpression, null, bRemoveSurroundingSeparators);
	}

	//### IKernelEntryReferenceSolveUserZZZ	
	@Override
	public String solve(String sLineWithExpression, ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference) throws ExceptionZZZ{
		return this.solve_(sLineWithExpression, objReturnReference, true);
	}
	
	@Override
	public String solve(String sExpressionIn, ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReferenceIn,	boolean bRemoveSurroundingSeparators) throws ExceptionZZZ {
		return this.solve_(sExpressionIn, objReturnReferenceIn, bRemoveSurroundingSeparators);
	}
	
	private String solve_(String sExpressionIn, ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReferenceIn,	boolean bRemoveSurroundingSeparators) throws ExceptionZZZ {
		String sReturn=sExpressionIn;
		Vector3ZZZ<String> vecReturn = new Vector3ZZZ<String>();
		boolean bUseExpression = false;
		boolean bUseSolver = false;
		
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
		objEntry.setRaw(sExpressionIn);
		
		main:{
			if(StringZZZ.isEmptyTrimmed(sExpressionIn)) break main;
			
			bUseExpression = this.getFlag(IIniTagWithExpressionZZZ.FLAGZ.USEEXPRESSION); 
			if(!bUseExpression) break main;		
						
			//Rufe nun parse() auf...
			ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReferenceParse= new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
			objReturnReferenceParse.set(objEntry); 
			vecReturn = this.parseFirstVector(sExpressionIn, objReturnReferenceParse, false);//!!! Die Z-Tags drin lassen, ggfs. benoetigt fuer weitere Berechnungen.
			objEntry = objReturnReferenceParse.get();
			
			String sExpressionParsed = (String) vecReturn.get(1);
			sReturn = sExpressionParsed; //Zwischenstand
			
			//Rufe nun solveParsed() auf...
			bUseSolver = this.getFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER);
			if(!bUseSolver) break main;
									
			ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReferenceSolve= new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
			objReturnReferenceSolve.set(objEntry);
			String sExpressionSolved = this.solveParsed(sExpressionParsed, objReturnReferenceSolve, bRemoveSurroundingSeparators);
			objEntry = objReturnReferenceSolve.get();		
			sReturn = sExpressionSolved; //Zwischenstand.	
																		
		}//end main:
		
		//NUN DEN INNERHALB DER EXPRESSION BERECHUNG ERSTELLTEN WERT uebernehmen
		this.setValue(sReturn);		
		vecReturn.replace(sReturn);
		
		//Als echten Ergebniswert aber die <Z>-Encryption Tags rausrechnen (unabhaengig von bRemoveSurroundingSeperators)
		//Merke: Weil hier auf abstrakter Ebene nicht auf bUseEncryption (also das spezielle Solver Flag) abgefragt werden kann, dies hier nicht machen.
		//       Statt dessen uf objEntry.isSolved() pruefen.
		if(bUseExpression & bUseSolver & objEntry.isSolved()) {
			String sTagStart = this.getTagStarting();
			String sTagEnd = this.getTagClosing();
			KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(vecReturn, sTagStart, sTagEnd);
			
			sReturn = (String) vecReturn.get(1);
			this.setValue(sReturn);
		}	
		
		//Als echten Ergebniswert aber die <Z>-Tags ggfs. rausrechnen
		if(bRemoveSurroundingSeparators & bUseExpression) {
			String sTagStart = "<Z>";
			String sTagEnd = "</Z>";
			KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(vecReturn, sTagStart, sTagEnd, false); //also von aussen nach innen!!!
			
			sReturn = (String) vecReturn.get(1);
			this.setValue(sReturn);
		}
				
		if(objEntry!=null) {			
			sReturn = VectorUtilZZZ.implode(vecReturn);
			objEntry.setValue(sReturn);
			if(sExpressionIn!=null) {
				if(!sExpressionIn.equals(sReturn)) {
					objEntry.isExpression(true);
					objEntry.isParsed(true); //zur Not nur, weil die Z-Tags entfernt wurden.
				}
			}			
			if(objReturnReferenceIn!=null)objReturnReferenceIn.set(objEntry);//Wichtig: Reference nach aussen zurueckgeben.
		}
		return sReturn;	
	}
	
	
	/**Methode muss vom konkreten "solver" ueberschrieben werden, wenn darin keine Pfade oder Variablen ersetzt werden sollen.
	 * @param sLineWithExpression
	 * @param objEntryReference
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 27.04.2023, 15:28:40
	 */	
	@Override
	public String solveParsed(String sExpression,ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference) throws ExceptionZZZ {		
		return this.solveParsed_(sExpression, objReturnReference, true);
	}
	
	@Override
	public String solveParsed(String sExpressionIn, ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReferenceIn, boolean bRemoveSurroundingSeparators)	throws ExceptionZZZ {
		return this.solveParsed_(sExpressionIn, objReturnReferenceIn, bRemoveSurroundingSeparators);
	}

	/* Aufloesen der INI-Pfade und Variablen. */
	private String solveParsed_(String sExpressionIn, ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReferenceIn, boolean bRemoveSurroundingSeparators)	throws ExceptionZZZ {
		String sReturn = sExpressionIn; //Darin können also auch Variablen, etc. sein
		String sExpressionUsed = sExpressionIn;
		
		ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference= null;		
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
			objReturnReference.set(objEntry);
		}//Achtung: Das objReturn Objekt NICHT generell uebernehmen. Es verfaelscht bei einem 2. Suchaufruf das Ergebnis.
		objEntry.setRaw(sExpressionIn);
			
		main:{
			if(StringZZZ.isEmptyTrimmed(sExpressionIn)) break main;			
								
			if(!this.getFlag(IIniTagWithExpressionZZZ.FLAGZ.USEEXPRESSION)) break main;			
						
//			if(this.getFlag(IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE)) {
//				//ZUERST: Löse ggfs. übergebene Variablen auf.
//				//!!! WICHTIG: BEI DIESEN AUFLOESUNGEN NICHT DAS UEBERGEORNETE OBJENTRY VERWENDEN, SONDERN INTERN EIN EIGENES!!! 
//									
//				//Merke: Fuer einfache Tag gibt es keine zu verarbeitenden Flags, also muss man auch keine suchen und uebergeben.
//				//       Hier aber ein 
//				String sExpressionOld = sExpressionUsed;
//				String sExpressionTemp;					
//				
//				ZTagFormulaIni_VariableZZZ<T> exDummy = new ZTagFormulaIni_VariableZZZ<T>();
//				String[] saFlagZpassed = FlagZFassadeZZZ.seekFlagZrelevantForObject(this, exDummy, true); //this.getFlagZ_passable(true, exDummy);
//					
//				ZTagFormulaIni_VariableZZZ<T> objVariable = new ZTagFormulaIni_VariableZZZ<T>(this.getHashMapVariable(), saFlagZpassed); 
//				while(objVariable.isExpression(sExpressionUsed)){
//					Vector3ZZZ<String> vecExpressionTemp =  objVariable.parseFirstVector(sExpressionUsed, true); //auf jeden Fall um Variablen herum den Z-Tag entfernen
//					if(vecExpressionTemp==null) break;
//					
//					sExpressionTemp = (String) vecExpressionTemp.get(1);
//					if(StringZZZ.isEmpty(sExpressionTemp)) {
//						break;
//					}else if(sExpressionUsed.equals(sExpressionTemp)) {
//						break;
//					}else{
//						sExpressionUsed = VectorUtilZZZ.implode(vecExpressionTemp);					
//					}
//				} //end while
//				sReturn = sExpressionUsed;
//				this.setValue(sReturn);
//				objEntry.setValue(sReturn);
//				if(sReturn!=sExpressionOld) {
//					objEntry.isParsed(true);
//					objEntry.isVariableSubstiuted(true);
//				}
//				sExpressionUsed = sReturn; //fuer ggfs. notwendige Weiterverarbeitung
//			}	
			
//			if(this.getFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH)) {	
//				//DANACH: ALLE PATH-Ausdrücke, also [xxx]yyy ersetzen
//				//Problem hier: [ ] ist auch der JSON Array-Ausdruck
//				String sExpressionOld = sExpressionUsed;
//				String sExpressionTemp;
//				
//				KernelZFormulaIni_PathZZZ<T> exDummy = new KernelZFormulaIni_PathZZZ<T>();
//				String[] saFlagZpassed = FlagZFassadeZZZ.seekFlagZrelevantForObject(this, exDummy, true); //this.getFlagZ_passable(true, exDummy);
//								
//				KernelZFormulaIni_PathZZZ<T> objFormulaIniPath = new KernelZFormulaIni_PathZZZ<T>(this.getKernelObject(), this.getFileConfigKernelIni(), saFlagZpassed);
//				while(objFormulaIniPath.isExpression(sExpressionUsed)){
//						Vector3ZZZ<String> vecExpressionTemp = objFormulaIniPath.parseFirstVector(sExpressionUsed, true); //auf jeden Fall um PATH-Anweisungen herum den Z-Tag entfernen
//						if(vecExpressionTemp==null) break;
//						
//						sExpressionTemp = VectorUtilZZZ.implode(vecExpressionTemp);	
//						if(StringZZZ.isEmpty(sExpressionTemp)) {
//							break;
//						}else if(sExpressionUsed.equals(sExpressionTemp)) {
//							break;
//						}else{
//							sExpressionUsed = sExpressionTemp;						
//						}											
//				} //end while
//				sReturn = sExpressionUsed;
//				this.setValue(sReturn);
//				objEntry.setValue(sReturn);
//				if(!sExpressionOld.equals(sReturn)) {
//					objEntry.isParsed(true);
//					objEntry.isPathSubstituted(true);
//				}
//				sExpressionUsed = sReturn;  //fuer ggfs. notwendige Weiterverarbeitung
//			}//end if .getFlag(..USE_...Path...)
			
			//Merke: Weitere Aufloesung bedarf das explizite solver-Flag
			//if(!this.getFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER)) break main;
			
		}//end main:
		
		//NUN DEN INNERHALB DER EXPRESSION BERECHUNG ERSTELLTEN WERT uebernehmen
		//Der Wert ist nur der TagInhalt this.setValue(sReturn);		
		if(objEntry!=null) {		
			objEntry.setValue(sReturn);
//			objEntry.setValue(sReturn);	
//20241011: Nein, diese Aufloesung hat mit den eigentlichen Solvern nix zu tun!!!
//			if(sExpressionIn!=null) {
//				if(!sExpressionIn.equals(sReturn)) objEntry.isSolved(true);
//			}
			if(objReturnReferenceIn!=null)objReturnReferenceIn.set(objEntry);
		}
		return sReturn;	
	}

	
	//###### Getter / Setter
	
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
	//#########################################	
	
//	@Override
//	public String[] parseAsArray(String sLineWithExpression, String sDelimiterIn) throws ExceptionZZZ{
//		String[] saReturn = null; //new String[];//sLineWithExpression;
//		main:{
//			if(StringZZZ.isEmptyTrimmed(sLineWithExpression)) break main;
//			
//			String sDelimiter;
//			if(StringZZZ.isEmpty(sDelimiterIn)) {
//				sDelimiter = IIniStructureConstantZZZ.sINI_MULTIVALUE_SEPARATOR; 
//			}else {
//				sDelimiter = sDelimiterIn;
//			}
//				   
//			String sExpressionTotal = this.parse(sLineWithExpression); //Hole erst einmal den Kernel-Tag-Wert.
//			if(!StringZZZ.isEmpty(sExpressionTotal)) {
//				String[] saExpression = StringZZZ.explode(sExpressionTotal, sDelimiter); //Dann löse Ihn als Mehrfachwert auf.
//				
//				String sValue = null;
//				ArrayListExtendedZZZ listasValue = new ArrayListExtendedZZZ();
//				for(String sExpression : saExpression) {
//					
//					//Nur für den etwas komplizierteren Fall einer Verschachtelung ...
//					if(this.isExpression(sExpression)){
//						sValue = this.parse(sExpression);
//					}else {
//						sValue = sExpression;
//					}
//					listasValue.add(sValue);
//				}
//								
//				saReturn = listasValue.toStringArray();				
//			}
//		}//end main:
//		return saReturn;
//	}
	
	
	
	//### aus IKernelZFormulaIniZZZ
	/* (non-Javadoc)
	 * @see basic.zKernel.IKernelZFormulaIniZZZ#isStringForConvertRelevant(java.lang.String)
	 */
	
	//### aus IConvertableZZZ
	@Override
	public boolean isConvertRelevant(String sStringToProof) throws ExceptionZZZ {
		return KernelConfigSectionEntryUtilZZZ.isConvertable(sStringToProof);
	}
	
	//Analog zu KernelJavaCallIniSolverZZZ, KernelJavaCallIniSolverZZZ, KernelJsonMapInisolver, KernelZFormulaMathSolver aufbauen... Der code ist im Parser
	@Override
	public Vector3ZZZ<String> parseFirstVector(String sLineWithExpression, boolean bRemoveSurroundingSeparators) throws ExceptionZZZ {		
		return this.parseFirstVector_(sLineWithExpression, null, bRemoveSurroundingSeparators);
	}
	
	@Override
	public Vector3ZZZ<String> parseFirstVector(String sLineWithExpression, ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReference, boolean bRemoveSurroundingSeparators) throws ExceptionZZZ {		
		return this.parseFirstVector_(sLineWithExpression, objReturnReference, bRemoveSurroundingSeparators);
	}
	
	private Vector3ZZZ<String> parseFirstVector_(String sExpressionIn, ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReferenceIn, boolean bRemoveSurroundingSeparators) throws ExceptionZZZ {		
		Vector3ZZZ<String> vecReturn = new Vector3ZZZ<String>();
		String sReturn=sExpressionIn;
		boolean bUseExpression = false;
		boolean bUseSolver = false;
				
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
		objEntry.setRaw(sExpressionIn);
	
		main:{			
			if(StringZZZ.isEmpty(sExpressionIn)) break main;
			
			bUseExpression = this.getFlag(IIniTagWithExpressionZZZ.FLAGZ.USEEXPRESSION); 
			if(!bUseExpression) break main;
						
			String sExpression = sExpressionIn;
			
			
			//Mehrere Ausdruecke. Dann muss der jeweilige "Rest-Bestandteil" des ExpressionFirst-Vectors weiter zerlegt werden.
			//Im Aufruf der Eltern-Methode findet ggfs. auch eine Aufloesung von Pfaden und eine Ersetzung von Variablen statt.
			//Z:Encryption drumherum entfernen
			ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReferenceParse = new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
			objReturnReferenceParse.set(objEntry);
			vecReturn = super.parseFirstVector(sExpression, objReturnReferenceParse, false); //nur in dieser aufrufenden Methode entscheiden, ob Tags entfernt werden sollen.	
			objEntry = objReturnReferenceParse.get();
			if(vecReturn==null) break main;
			sReturn = (String)vecReturn.get(1);			
			
			//++++++++++++++++++++++++
			bUseSolver = this.getFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER);
			if(!bUseSolver) break main;
			
			ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReferenceParseThis = new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
			objReturnReferenceParseThis.set(objEntry);
			vecReturn = this.parseFirstVectorSolverCustomPost(vecReturn, objReturnReferenceParseThis, bRemoveSurroundingSeparators);	
			objEntry = objReturnReferenceParseThis.get();
			if(vecReturn==null) break main;
			sReturn = (String)vecReturn.get(1);	
		}//end main:
	
			
		//#################################
		//Den Wert ersetzen, wenn es was zu ersetzen gibt.
		vecReturn.replace(sReturn);
		this.setValue(sReturn);		
		
		//Als echten Ergebniswert die <Z>-Tags ggfs. rausrechnen
		if(bRemoveSurroundingSeparators & bUseExpression) {
			String sTagStart = "<Z>";
			String sTagEnd = "</Z>";
			KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(vecReturn, sTagStart, sTagEnd, false); //also von aussen nach innen!!!
			
			sReturn = (String) vecReturn.get(1);
			this.setValue(sReturn);
		}	
	
		if(objEntry!=null) {
			sReturn = VectorUtilZZZ.implode(vecReturn);
			objEntry.setValue(sReturn);	
			if(sExpressionIn!=null) {
				if(!sExpressionIn.equals(sReturn)) objEntry.isParsed(true);
			}				
			if(objReturnReferenceIn!=null) objReturnReferenceIn.set(objEntry);
		}
		return vecReturn;
	}
	
	//##################################
	//### Flag handling
	
	//Merke: Nicht jeder Solver implementiert alle Interfaces, noch nicht einmal IKernelExpressionIniSolverZZZ (also mit <Z>-Tags arbeiten) notwendigerweise 
	//darum sind hier nur:
	//-- IKernelExpressionIniSolverZZZ
	//-- IKernelZFormulaIni_PathZZZ
	//-- IKernelZVariableIniSolverZZZ
	
	
	//### aus IKernelExpressionIniSolverZZZ	
	@Override
	public boolean getFlag(IKernelExpressionIniSolverZZZ.FLAGZ objEnumFlag) {
		return this.getFlag(objEnumFlag.name());
	}
	@Override
	public boolean setFlag(IKernelExpressionIniSolverZZZ.FLAGZ objEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		return this.setFlag(objEnumFlag.name(), bFlagValue);
	}
	
	@Override
	public boolean[] setFlag(IKernelExpressionIniSolverZZZ.FLAGZ[] objaEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		boolean[] baReturn=null;
		main:{
			if(!ArrayUtilZZZ.isNull(objaEnumFlag)) {
				baReturn = new boolean[objaEnumFlag.length];
				int iCounter=-1;
				for(IKernelExpressionIniSolverZZZ.FLAGZ objEnumFlag:objaEnumFlag) {
					iCounter++;
					boolean bReturn = this.setFlag(objEnumFlag, bFlagValue);
					baReturn[iCounter]=bReturn;
				}
			}
		}//end main:
		return baReturn;
	}
	
	@Override
	public boolean proofFlagExists(IKernelExpressionIniSolverZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
			return this.proofFlagExists(objEnumFlag.name());
	}
	
	@Override
	public boolean proofFlagSetBefore(IKernelExpressionIniSolverZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
			return this.proofFlagSetBefore(objEnumFlag.name());
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
	
	
	//### aus IKernelZVariableIniSolverZZZ	
	@Override
	public boolean getFlag(IKernelZFormulaIni_VariableZZZ.FLAGZ objEnumFlag) {
		return this.getFlag(objEnumFlag.name());
	}
	@Override
	public boolean setFlag(IKernelZFormulaIni_VariableZZZ.FLAGZ objEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		return this.setFlag(objEnumFlag.name(), bFlagValue);
	}
	
	@Override
	public boolean[] setFlag(IKernelZFormulaIni_VariableZZZ.FLAGZ[] objaEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		boolean[] baReturn=null;
		main:{
			if(!ArrayUtilZZZ.isNull(objaEnumFlag)) {
				baReturn = new boolean[objaEnumFlag.length];
				int iCounter=-1;
				for(IKernelZFormulaIni_VariableZZZ.FLAGZ objEnumFlag:objaEnumFlag) {
					iCounter++;
					boolean bReturn = this.setFlag(objEnumFlag, bFlagValue);
					baReturn[iCounter]=bReturn;
				}
			}
		}//end main:
		return baReturn;
	}
	
	@Override
	public boolean proofFlagExists(IKernelZFormulaIni_VariableZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
			return this.proofFlagExists(objEnumFlag.name());
	}
	
	@Override
	public boolean proofFlagSetBefore(IKernelZFormulaIni_VariableZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
			return this.proofFlagSetBefore(objEnumFlag.name());
	}
	
	
	
}//End class