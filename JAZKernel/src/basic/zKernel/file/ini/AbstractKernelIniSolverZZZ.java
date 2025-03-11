package basic.zKernel.file.ini;

import java.util.ArrayList;
import java.util.Set;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IConvertEnabledZZZ;
import basic.zBasic.IObjectWithExpressionZZZ;
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
	
	
	//### Aus Interface IResettableValuesZZZ
	@Override
	public boolean reset() throws ExceptionZZZ{
		super.reset();
		this.objCrypt=null;
		return true;
	}
	
	@Override 
	public boolean resetValues() throws ExceptionZZZ{
		super.resetValues();		
		this.resetEntry();
		return true;
	}
	
	//### Aus Interface ICryptUserZZZ
	@Override
	public ICryptZZZ getCryptAlgorithmType() throws ExceptionZZZ {
		return this.objCrypt;
	}

	@Override
	public void setCryptAlgorithmType(ICryptZZZ objCrypt) {
		this.objCrypt = objCrypt;
	}
		
	//### Aus IObject with Expression
	
	
	
	//### Aus IParseEnabled
//	@Override
//	public boolean isParserEnabledThis() throws ExceptionZZZ{
//		return this.isSolverEnabledEveryRelevant();		
//	}
	
	//### Aus ISolveEnabled
	//In folgender konkreten Implementierung kann ueber das konkrete Flag des konkreten Solvers, dieser ein-/ausgeschaltet werden.
	@Override
	public abstract boolean isSolverEnabledThis() throws ExceptionZZZ;
	
	//Merke: Wenn ein "Elternsolver" auch relevant sein soll, dann kann in einer ueberschriebenen Version dieser hier aufgenommen werden.
	@Override 
	public boolean isSolverEnabledEveryRelevantThis() throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			//Merke: Die Abfrage auf isSolverEnabledEveryRelevant() ... nicht hierein, damit wird ggf. noch eine Feinsteuerung auf Entfernen des reinen Z-Tags gesteuert.
			//       Muss also immer eine extra Abfrage bleiben.
			bReturn = this.isSolverEnabledThis();
			if(!bReturn) break main;
			
			//Hier kaeme dann das ueberschiebene rein, z.B. "Elternsolver"
							
		}//end main:
		return bReturn;
	}
	
	@Override
	public boolean isSolverEnabledGeneral() throws ExceptionZZZ{
		return this.getFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER);
	}
	
	@Override 
	public boolean isSolverEnabledEveryRelevant() throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			//Merke: Die Abfrage auf isExpressionEnabledGeneral() ... nicht hierein, damit wird ggf. noch eine Feinsteuerung auf Entfernen des reinen Z-Tags gesteuert.
			//       Muss also immer eine extra Abfrage bleiben.
			bReturn = this.isSolverEnabledEveryRelevantThis();
			if(!bReturn) break main;
			
			bReturn = this.isSolverEnabledGeneral();
			if(!bReturn) break main;						
		}//end main:
		return bReturn;
	}
	
	@Override 
	public boolean isSolverEnabledAnyRelevant() throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			//Merke: Die Abfrage auf isExpressionEnabledGeneral() ... nicht hierein, damit wird ggf. noch eine Feinsteuerung auf Entfernen des reinen Z-Tags gesteuert.
			//       Muss also immer eine extra Abfrage bleiben.
			boolean bReturn1 = this.isSolverEnabledThis();						
			boolean bReturn2 = this.isSolverEnabledGeneral();
			
			bReturn = bReturn1 | bReturn2;
		}//end main:
		return bReturn;
	}
	
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
		String sReturnTag = null;
		main:{
			if(vecExpression==null) break main;
			
			String sExpression = (String) vecExpression.get(1);
			sReturnTag = this.solveParsed(sExpression, objReturnReference, bRemoveSuroundingSeparators);
			sReturn = sReturnTag;
			
			//NUN DEN INNERHALB DER EXPRESSION BERECHUNG ERSTELLTEN WERT uebernehmen
			this.setValue(sReturnTag);
			vecExpression.replace(sReturnTag);
			sReturn = VectorUtilZZZ.implode(vecExpression);			
			if(objEntryInner!=null) objEntryInner.setValue(sReturn);				
		}//end main:
		return sReturn;
	}
	
	@Override
	public String solve(String sExpression) throws ExceptionZZZ{
		return this.solve_(sExpression, true);
	}
	
	@Override
	public String solve(String sExpression, boolean bRemoveSurroundingSeparators) throws ExceptionZZZ {
		return this.solve_(sExpression, bRemoveSurroundingSeparators);
	}
	
	/** Methoder ruft erst parse() auf, dann solvedParsed()**/
	private String solve_(String sExpression, boolean bRemoveSurroundingSeparators) throws ExceptionZZZ {
		String sReturn = sExpression;
		
		//Wichtig: Als oberste Methode immer ein neues Entry-Objekt holen. Dann stellt man sicher, das nicht mit Werten der vorherigen Suche gearbeitet wird.
		IKernelConfigSectionEntryZZZ objReturn = this.getEntryNew(); //Hier schon die Rückgabe vorbereiten, falls eine weitere Verarbeitung nicht konfiguriert ist.		
		objReturn.setRaw(sExpression);

		main:{			
			ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
			objReturnReference.set(objReturn);
			
			sReturn = this.solve(sExpression, objReturnReference, bRemoveSurroundingSeparators);
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
	
	//+++++++++++++++++++++
	//Merke: Folgende Methoden koennen nur im konkreten Solver implementiert werden.
	//       Z.B. kennt nur der konkrete Solver das Flag, das ihn deaktiviert.
	//            Ist der Solver deaktiviert, findet dann auch das Entfernen umgebender Tags nicht statt.
	//public abstract Vector3ZZZ<String> parseFirstVectorSolverCustomPost(Vector3ZZZ<String> vecExpression) throws ExceptionZZZ;
	//public abstract Vector3ZZZ<String> parseFirstVectorSolverCustomPost(Vector3ZZZ<String> vecExpression, boolean bRemoveSurroundingSeparators) throws ExceptionZZZ;
		
	@Override
	public Vector3ZZZ<String> solvePost(Vector3ZZZ<String> vecExpression) throws ExceptionZZZ {
		return this.solvePost_(vecExpression, null, true);
	}
	
	@Override
	public Vector3ZZZ<String> solvePost(Vector3ZZZ<String> vecExpression, boolean bRemoveSurroundingSeparators) throws ExceptionZZZ {		
		return this.solvePost_(vecExpression, null, bRemoveSurroundingSeparators);
	}
	
	//++++++++++++++++++++++

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
		String sReturn = null; String sReturnTag = null; String sReturnLine = null;	String sReturnTag2Solve = null; String sReturnTagSolved = null;	String sReturnLine2Solve = null;String sReturnLineSolved = null;	
		Vector3ZZZ<String> vecReturn = new Vector3ZZZ<String>();
		
		boolean bUseExpression = false;	boolean bUseSolver = false; boolean bUseSolverThis = false;
		
		
		//############
		//Wichtig: Bei jedem neuen Parsen (bzw. vor dem Solven(), nicht parse/solveFirstVector!) die internen Werte zuruecksetzen, sonst wird alles verfaelscht.
		//         Ziel ist, das nach einem erfolgreichen Parsen/Solven das Flag deaktiviert werden kann UND danach bei einem neuen Parsen/Solven die Werte null sind.
		this.resetValues();			
		//#######
		
		ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference = null;
		IKernelConfigSectionEntryZZZ objEntry = null;
		if(objReturnReferenceIn==null) {
			objReturnReference = new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
		}else {
			objReturnReference = objReturnReferenceIn;
		}
		
		objEntry = objReturnReference.get();
		if(objEntry==null) {
			//Das Ziel ist es moeglichst viel Informationen aus dem entry "zu retten"
			//Achtung: Das objReturn Objekt NICHT generell versuchen ueber .getEntry() und dann ggfs. .getEntryNew() zu uebernehmen. Es verfaelscht bei einem 2. Suchaufruf das Ergebnis.
			//objEntry = this.getEntry();
			
			//nein, dann gehen alle Informationen verloren
			//objReturn = this.parseAsEntryNew(sExpression);
			
			objEntry = new KernelConfigSectionEntryZZZ<T>(this);  
			objReturnReference.set(objEntry);
		}else {
			
		}//Achtung: Das objReturn Objekt NICHT generell uebernehmen. Es verfaelscht bei einem 2. Suchaufruf das Ergebnis.
		this.setRaw(sExpressionIn);		
		objEntry.setRaw(sExpressionIn);
		objEntry.isSolveCalled(true);
		
		//TODOGOON: ueberschreibbare Methode der Solver und auch Parser 
		this.updateCalledValue(objEntry, "solve_case", true);
		
		sReturnLine=sExpressionIn;		
		sReturnTag = this.getValue();
		vecReturn.set(0, sReturnLine);//nur bei in dieser Methode neu erstellten Vector.
		sReturn = sReturnLine;
		
		main:{
			if(StringZZZ.isEmpty(sExpressionIn)) break main;
			String sExpression = sExpressionIn;
			
			//Rufe nun parseFirstVector() auf... (und nicht das gesamte Parse!!!
			ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReferenceParse= new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
			objReturnReferenceParse.set(objEntry); 
			vecReturn = this.parseFirstVector(sExpression, objReturnReferenceParse, bRemoveSurroundingSeparators);
			if(vecReturn==null)break main;
			objEntry = objReturnReferenceParse.get();
			
			//solve ruft immer parse() auf, wenn die generelle Auswertung der Expression abgeschaltet ist,
			//so wird .isParseCalled() dort trotzdem gesetzt sein.
			bUseExpression = this.isExpressionEnabledGeneral(); 
			if(!bUseExpression) break main;		
						
//			//Da wir hier verkuerzt parseFirstVector aufrufen... Explizit parsePost() ausfuehren.
//			//Nur so werden die Z-Tags auch entfernt, auch wenn der Solver selbst deaktiviert ist.
			vecReturn = this.parsePost(vecReturn, objReturnReferenceParse, bRemoveSurroundingSeparators);			
			sReturnTag = this.getValue();
			if(vecReturn==null) break main;
		
			sReturnLine = VectorUtilZZZ.implode(vecReturn); //Zwischenstand ENTRY-Zeile
			
			//Rufe nun solveParsed() auf...
			bUseSolver = this.isSolverEnabledGeneral();
			if(!bUseSolver) break main;
			
			//TODOGOON20250308; //TICKETGOON20250308;;//Generische Problematik, wenn man hier nur auf den eigenen Solver abbprüft, kann man nicht das "Elternflag setzen"
			
			bUseSolverThis = this.isSolverEnabledThis();
			if(!bUseSolverThis) break main;
			
			sReturnTag2Solve = sReturnTag; //(String) vecReturn.get(1); //Nicht sReturnTag zu verwenden ist Absicht, im Ausdruck aus dem Vector der Zeile sind dann für das solven ggfs. noch wichtige Tags drin.
			sReturnLine2Solve = sReturnLine;
			
			ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReferenceSolve= new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
			objReturnReferenceSolve.set(objEntry);
			sReturnLineSolved = this.solveParsed(sReturnLine2Solve, objReturnReferenceSolve, bRemoveSurroundingSeparators);
			objEntry = objReturnReferenceSolve.get();		
			
			//Nun als Tag Value die Solve Loesung einsetzen vor dem ... post
			sReturnTag = this.getValue(); 
			vecReturn.replace(sReturnTag);
			vecReturn = this.solvePost(vecReturn, bRemoveSurroundingSeparators);
			
			//Nun als Tag Value die Solve Loesung einsetzen nach dem ... post
			sReturnTag = this.getValue();
			sReturnLine = VectorUtilZZZ.implode(vecReturn);
		}//end main:
		
		//NUN DEN INNERHALB DER EXPRESSION BERECHUNG ERSTELLTEN WERT uebernehmen
		this.setValue(sReturnTag);	
		sReturn = sReturnLine;
		
		if(objEntry!=null) {
			objEntry.setValue(sReturnLine);
			if(bUseExpression) {			
				if(bUseSolver && bUseSolverThis) {
					if(sReturnLineSolved!=null) {
						//Ziel ist es zu ermitteln, ob durch das Solven selbst ein Aenderung passierte.
						//Daher absichtlich nicht sExpressionIn und sReturn verwenden. Darin sind ggfs. Aenderungen durch das Parsen enthalten. 
						if(!sReturnLineSolved.equals(sReturnLine)) {
							objEntry.isSolvedChanged(true); //zur Not nur, weil die Z-Tags entfernt wurden.	
						}
					}
				}						
				if(objEntry.isEncrypted()) objEntry.setValueDecrypted(sReturn);
				
				if(objReturnReferenceIn!=null)objReturnReferenceIn.set(objEntry);//Wichtig: Reference nach aussen zurueckgeben.
				this.adoptEntryValuesMissing(objEntry);
			}
		}
		return sReturn;	
	}
	
	
	/**Methode muss vom konkreten "solver" ueberschrieben werden (!!!!) , wenn darin keine Pfade oder Variablen ersetzt werden sollen.
	 * Nachfolgender Code ist also eine Art Blaupause.
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

	//!!! METHODE IST AN DIESER STELLE EINE BLAUPAUSE, die vom konkreten Solver mit der konkreten "Aufloesung" ueberschrieben werden muss. 
	private String solveParsed_(String sExpressionIn, ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReferenceIn, boolean bRemoveSurroundingSeparators) throws ExceptionZZZ {		
		String sReturn = sExpressionIn;
		String sReturnTag = sExpressionIn;
		
		boolean bUseExpression = false; 
		boolean bUseSolver = false;
		
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
			
			bUseExpression = this.isExpressionEnabledGeneral();
			if(!bUseExpression) break main;
						
			bUseSolver = this.isSolverEnabledEveryRelevant(); //this.getFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER);
			if(!bUseSolver) break main;
			
			String sExpression = sExpressionIn;
				
			//!!! METHODE IST EINE BLAUPAUSE, die vom konkreten Solver ueberschrieben werden muss.
			//!!! HIer steht dann die konkrete Aufloesung. 
			
		}//end main:	
				
		//NUN DEN INNERHALB DER EXPRESSION BERECHUNG ERSTELLTEN WERT uebernehmen
		if(sReturnTag!=null) this.setValue(sReturnTag);	//Der Handler bekommt die ganze Zeile als Wert	
		if(objEntry!=null) {		
			objEntry.setValue(sReturn);
						
			if(objReturnReferenceIn!=null)objReturnReferenceIn.set(objEntry);//Wichtig: Reference nach aussen zurueckgeben.
			this.adoptEntryValuesMissing(objEntry);			
		}
		return sReturn;				
	}

	//+++++++++++++++++++++
	//Merke: Folgende Methoden koennen nur im konkreten Solver implementiert werden.
	//       Z.B. kennt nur der konkrete Solver das Flag, das ihn deaktiviert.
	//            Ist der Solver deaktiviert, findet dann auch das Entfernen umgebender Tags nicht statt.
	//public abstract Vector3ZZZ<String> parseFirstVectorSolverCustomPost(Vector3ZZZ<String> vecExpression, ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference) throws ExceptionZZZ;
	//public abstract Vector3ZZZ<String> parseFirstVectorSolverCustomPost(Vector3ZZZ<String> vecExpression, ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReference, boolean bRemoveSurroundingSeparators) throws ExceptionZZZ;
	
	
	@Override
	public Vector3ZZZ<String> solvePost(Vector3ZZZ<String> vecExpression, ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference) throws ExceptionZZZ {
		return this.solvePost_(vecExpression, objReturnReference, true);
	}
	
	@Override
	public Vector3ZZZ<String> solvePost(Vector3ZZZ<String> vecExpression, ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReference, boolean bRemoveSurroundingSeparators) throws ExceptionZZZ {		
		return this.solvePost_(vecExpression, objReturnReference, bRemoveSurroundingSeparators);
	}
	
	private Vector3ZZZ<String> solvePost_(Vector3ZZZ<String> vecExpressionIn, ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReferenceIn, boolean bRemoveSurroundingSeparators) throws ExceptionZZZ {		
		Vector3ZZZ<String> vecReturn = vecExpressionIn;		
		String sReturn = null;
		String sReturnTag = null; String sReturnLine = null;
		String sExpressionIn=null;
		boolean bUseExpression = false;
		boolean bUseSolver = false;
		boolean bUseSolverThis = false;		
		
		
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
		sExpressionIn = VectorUtilZZZ.implode(vecExpressionIn);
		this.setRaw(sExpressionIn);
		objEntry.setRaw(sExpressionIn);
		sReturnTag = this.getValue();
		sReturnLine = sExpressionIn;	
		main:{	
			if(vecExpressionIn==null) break main;
			vecReturn=vecExpressionIn;			
						
			bUseExpression = this.isExpressionEnabledGeneral(); 
			if(!bUseExpression) break main;
						
			bUseSolver = this.isSolverEnabledGeneral();
			if(!bUseSolver) break main;
										
			bUseSolverThis = this.isSolverEnabledThis(); //this.getFlag(IKernelCallIniSolverZZZ.FLAGZ.USECALL);		
			if(!bUseSolverThis) break main;
			
			
			//Als echten Ergebniswert aber die <Z: ... konkreten Solver Tags rausrechnen (!!! unabhaengig von bRemoveSurroundingSeperators)
			if(bUseExpression & bUseSolver & bUseSolverThis){
				String sTagStart = this.getTagPartOpening();
				String sTagEnd = this.getTagPartClosing();
				if(sTagStart.equalsIgnoreCase("<Z>")) {
					//dann mache nix... der Tag wird spaeter behandelt...
				}else {
					KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(vecReturn, sTagStart, sTagEnd);
				}
			}	
			
			//Als echten Ergebniswert aber die <Z>-Tags ggfs. rausrechnen, falls gewuenscht
			if(bRemoveSurroundingSeparators & bUseExpression) {
				String sTagStartZ = "<Z>";
				String sTagEndZ = "</Z>";
				KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(vecReturn, sTagStartZ, sTagEndZ, true, false); //also von aussen nach innen!!!				
			}
			
			
			//+++ Aufruf einer Methode, die vom konkreten Solver ueberschrieben werden kann.
			vecReturn = this.solvePostCustom(vecReturn, objReturnReference, bRemoveSurroundingSeparators);
			objEntry = objReturnReference.get();
			
			
			sReturnTag = (String) vecReturn.get(1);
			sReturnLine = VectorUtilZZZ.implode(vecReturn);
			
			objEntry.isSolved(true);
		}//end main:
	
			
		//#################################
		//Den Wert ersetzen, wenn es was zu ersetzen gibt.		
		vecReturn.replace(sReturnTag);						
		this.setValue(sReturnTag);
		sReturn = sReturnLine;
		
		if(objEntry!=null) {				
			objEntry.setValue(sReturnLine);		
			if(objReturnReferenceIn!=null) objReturnReferenceIn.set(objEntry);
		}
		return vecReturn;
	}
	
	//+++++++++++++++++++++++++++++++++++++++++++++
	//+++ Folgende Methoden koennen ueberschrieben werden um fuer den konkreten Solver eine Loesung einzubauen.
	//+++ Als Standard werden hier die Z-Tags des Solvers entfernt.
	@Override
	public Vector3ZZZ<String> solvePostCustom(Vector3ZZZ<String> vecExpression) throws ExceptionZZZ {
		return this.solvePostCustom_(vecExpression, null, true);
	}

	@Override
	public Vector3ZZZ<String> solvePostCustom(Vector3ZZZ<String> vecExpression, boolean bRemoveSurroundingSeparators)throws ExceptionZZZ {
		return this.solvePostCustom_(vecExpression, null, bRemoveSurroundingSeparators);
	}
	
	@Override
	public Vector3ZZZ<String> solvePostCustom(Vector3ZZZ<String> vecExpression, ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference) throws ExceptionZZZ {
		return this.solvePostCustom_(vecExpression, objReturnReference, true);
	}
	
	@Override
	public Vector3ZZZ<String> solvePostCustom(Vector3ZZZ<String> vecExpression, ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReference, boolean bRemoveSurroundingSeparators) throws ExceptionZZZ {		
		return this.solvePostCustom_(vecExpression, objReturnReference, bRemoveSurroundingSeparators);
	}
	
	//!!! nur eine Blaupause, die vom konkreten Solver ueberschrieben werden kann.
	//!!! hier wuerde dann etwas konkretes stehen.
	private Vector3ZZZ<String> solvePostCustom_(Vector3ZZZ<String> vecExpressionIn, ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReferenceIn, boolean bRemoveSurroundingSeparators) throws ExceptionZZZ {		
		Vector3ZZZ<String> vecReturn = vecExpressionIn;		
		String sReturn = null;
		String sReturnTag = null; String sReturnLine = null;
		String sExpressionIn=null;
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
		sExpressionIn = VectorUtilZZZ.implode(vecExpressionIn);
		this.setRaw(sExpressionIn);
		objEntry.setRaw(sExpressionIn);
		sReturnTag = this.getValue();
		sReturnLine = sExpressionIn;
		
		main:{	
			if(vecExpressionIn==null) break main;
				
			bUseExpression = this.isExpressionEnabledGeneral(); 
			if(!bUseExpression) break main;
			
			
			//!!! nur eine Blaupause, die vom konkreten Solver ueberschrieben werden kann.
			//!!! hier wuerde dann etwas konkretes stehen.
						
			sReturnTag = (String) vecReturn.get(1);
			sReturnLine = VectorUtilZZZ.implode(vecReturn);
		}//end main:
	
		//#################################
		//Den Wert ersetzen, wenn es was zu ersetzen gibt.
		if(vecReturn!=null && sReturnTag!=null) vecReturn.replace(sReturnTag);
		this.setValue(sReturnTag);
		sReturn = sReturnLine;
						
		if(objEntry!=null) {
			objEntry.setValue(sReturnLine);	
			if(sExpressionIn!=null) {				
				objEntry.isParseCalled(true); 								
				if(!sExpressionIn.equals(sReturn)) objEntry.isParsedChanged(true); //zur Not nur, weil die Z-Tags entfernt wurden.									
			}			
			if(objReturnReferenceIn!=null) objReturnReferenceIn.set(objEntry);
		}
		return vecReturn;
	}
	
	
	//+++++++++++++++++++++++++++++++++ siehe analog zu AbstractIniTagWithExpressionBasicZZZ
	@Override
	public IKernelConfigSectionEntryZZZ solveAsEntryNew(String sExpression) throws ExceptionZZZ {
		//Nein, das setzt das Entry-Objekt des Solvers zurueck IKernelConfigSectionEntryZZZ objReturn = this.getEntryNew();
		//und damit sind bestehende Eintragswerte ggfs. uebernommen IKernelConfigSectionEntryZZZ objReturn = new KernelConfigSectionEntryZZZ<T>(this);
		IKernelConfigSectionEntryZZZ objReturn = new KernelConfigSectionEntryZZZ<T>();		
		main:{
			if(StringZZZ.isEmptyTrimmed(sExpression)) break main;
			objReturn.setRaw(sExpression);
			
			ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReferenceSolve = new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
			objReturnReferenceSolve.set(objReturn);
			objReturn = this.solveAsEntry_(sExpression, objReturnReferenceSolve, true);
			
		}//end main:
		return objReturn;
	}

	@Override
	public IKernelConfigSectionEntryZZZ solveAsEntry(String sExpression) throws ExceptionZZZ {
		return this.solveAsEntry_(sExpression, null, true);
	}

	@Override
	public IKernelConfigSectionEntryZZZ solveAsEntry(String sExpression, boolean bRemoveSurroundingSeparators) throws ExceptionZZZ {
		return this.solveAsEntry_(sExpression, null, bRemoveSurroundingSeparators);
	}

	@Override
	public IKernelConfigSectionEntryZZZ solveAsEntry(String sExpression, ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReferenceIn) throws ExceptionZZZ {
		return this.solveAsEntry_(sExpression, objReturnReferenceIn, true);
	}

	@Override
	public IKernelConfigSectionEntryZZZ solveAsEntry(String sExpression, ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReferenceIn, boolean bRemoveSurroundingSeparators)	throws ExceptionZZZ {
		return this.solveAsEntry_(sExpression, objReturnReferenceIn, bRemoveSurroundingSeparators);
	}
	
	private IKernelConfigSectionEntryZZZ solveAsEntry_(String sExpressionIn, ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReferenceIn, boolean bRemoveSurroundingSeparators) throws ExceptionZZZ{
		IKernelConfigSectionEntryZZZ objReturn = null; //new KernelConfigSectionEntryZZZ<T>(this);
		String sReturn = sExpressionIn;
		main:{
			if(StringZZZ.isEmptyTrimmed(sExpressionIn)) break main;
						
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
				
						
			//Merke: Mit Reference geht ab: AbstractKernelIniTagSimpleZZZ
			String sExpression = sExpressionIn;
			
			ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReferenceSolve = new ReferenceZZZ<IKernelConfigSectionEntryZZZ>(); 			
			objReturnReferenceSolve.set(objEntry);
			sReturn = this.solve(sExpression, objReturnReferenceSolve, bRemoveSurroundingSeparators);					
			//Nein, das ist nicht der Wert des Tags... sondern der Gesamtenzeile   this.setValue(sReturn);
			objEntry = objReturnReferenceSolve.get();
			objReturn = objEntry;
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


	
	//###### Getter / Setter
	
	//### aus IKernelZFormulaIniZZZ
	/* (non-Javadoc)
	 * @see basic.zKernel.IKernelZFormulaIniZZZ#isStringForConvertRelevant(java.lang.String)
	 */
	
	//### aus IConvertableZZZ
	@Override
	public boolean isConvertRelevant(String sStringToProof) throws ExceptionZZZ {
		return KernelConfigSectionEntryUtilZZZ.isConvertable(sStringToProof);
	}
	
	//##################################
	//### Flag handling	
	//Merke: Nicht jeder Solver implementiert alle Interfaces, noch nicht einmal IKernelExpressionIniSolverZZZ (also mit <Z>-Tags arbeiten) notwendigerweise 
	//darum sind hier nur:
	//-- IKernelExpressionIniSolverZZZ	
	
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
}//End class