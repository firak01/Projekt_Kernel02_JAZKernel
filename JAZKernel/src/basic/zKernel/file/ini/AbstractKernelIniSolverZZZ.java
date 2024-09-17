package basic.zKernel.file.ini;

import java.util.Set;
import java.util.Vector;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IConvertEnabledZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.abstractList.HashMapCaseInsensitiveZZZ;
import basic.zBasic.util.abstractList.VectorZZZ;
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
	
	
	//### aus IParseEnabledZZZ		
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
		return this.parseFirstVector_(sLineWithExpression, null, true);
	}
	
	@Override
	public Vector<String>parseFirstVector(String sLineWithExpression, ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReference) throws ExceptionZZZ{		
		return this.parseFirstVector_(sLineWithExpression, objReturnReference, true);
	}
	
	@Override
	public Vector<String>parseFirstVector(String sExpression, ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReferenceIn, boolean bRemoveSurroundingSeparators) throws ExceptionZZZ{					
		return this.parseFirstVector_(sExpression, objReturnReferenceIn, bRemoveSurroundingSeparators);
	}
	
	
	private Vector<String>parseFirstVector_(String sExpression, ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReferenceIn, boolean bRemoveSurroundingSeparators) throws ExceptionZZZ{					
		Vector<String>vecReturn = new Vector<String>();
		IKernelConfigSectionEntryZZZ objEntry = null;
		String sReturn = sExpression;						
		main:{
			boolean bUseExpression = this.getFlag(IIniTagWithExpressionZZZ.FLAGZ.USEEXPRESSION); 
			if(!bUseExpression) break main;
						
			boolean bUseSolver = this.getFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER);
			if(!bUseSolver) break main;
						
			ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReference = null;			
			if(objReturnReferenceIn==null) {
				objReturnReference = new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();								
			}else {
				objReturnReference = objReturnReferenceIn;
				objEntry = objReturnReference.get();
			}
						
			if(objEntry==null) {
				//Das Ziel ist es moeglichst viel Informationen aus dem entry "zu retten"				
				objEntry = new KernelConfigSectionEntryZZZ<T>(this); //this.getEntryNew(); es gingen alle Informationen verloren				
				                                                     //nein, dann gehen alle Informationen verloren   objReturn = this.parseAsEntryNew(sExpression);
				objReturnReference.set(objEntry);
			}	//Achtung: Das objReturn Objekt NICHT generell uebernehmen. Es verfaelscht bei einem 2. Suchaufruf das Ergebnis.
			objEntry.setRaw(sExpression);
			
			
						
			//Hole zuerst den Tag-Inhalt, lasse die umgebenden Tags drin. Sie werden für die anderen Solver gebraucht.
			//Es wird also nur der Z-Tag rausgenommen (und was vorne/am Ende steht).
			ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReferenceParser= new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
			objReturnReferenceParser.set(objEntry);			
			vecReturn = super.parseFirstVector(sExpression, objReturnReferenceParser); //entferne den umgebenden Tag...
			objEntry = objReturnReferenceParser.get();
			this.setEntry(objEntry);			
			if(vecReturn==null)break main;
			
			
			//Mache nun die Aufloesung des speziellen Solvers, Elternsolvers, etc. .  Also auch Aufloesung von Pfaden etc. 
			String sExpressionToSolve = vecReturn.get(1);
			ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReferenceSolver= new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
			objReturnReferenceSolver.set(objEntry);
			int iReturn = this.solve(sExpressionToSolve, objReturnReferenceSolver, bRemoveSurroundingSeparators);
			this.setEntry(objEntry);			
			//if(iReturn>=1) {  //gehe auch damit um, wenn nix aufgeloest wurde.
				String sExpressionSolved = objEntry.getValue();
				
				if(vecReturn.size()==0) vecReturn.add(0, "");
				
				if(vecReturn.size()>=2) vecReturn.removeElementAt(1);
				if(!StringZZZ.isEmpty(sExpressionSolved)){
					vecReturn.add(1, sExpressionSolved);
				}else {
					vecReturn.add(1, "");
				}
				
				if(vecReturn.size()==2) vecReturn.add(2, "");	
				
				sReturn = VectorZZZ.implode(vecReturn);
		//	}
									
			sReturn = sExpressionSolved;//Wir sind gerade beim Aufloesen/Parsen des ersten Werts. ... Also nicht zusammenfassen....//VectorZZZ.implode(vecReturn);//beim Parsen implode machen..., beim Solven vecReturn.get(1);			
		}//end main;
		this.setValue(sReturn);
		if(objEntry!=null) objEntry.setValue(sReturn);
		if(objReturnReferenceIn!=null) objReturnReferenceIn.set(objEntry);
		return vecReturn;		
	}
	
	
	//### aus IIniTagWithExpressionZZZ	
		
	/** Gibt einen Vector zurück, in dem das erste Element der Ausdruck VOR der ersten 'Expression' ist. Das 2. Element ist die Expression. Das 3. Element ist der Ausdruck NACH der ersten Expression.
	* @param sLineWithExpression
	* @return
	* 
	* lindhaueradmin; 06.03.2007 11:20:34
	 * @throws ExceptionZZZ 
	 */
	@Override
	public Vector<String>parseFirstVectorAsExpression(String sExpression) throws ExceptionZZZ{
		Vector<String>vecReturn = null;		
		main:{
			vecReturn = super.parseFirstVectorAsExpression(sExpression);
					
			//NUN DEN INNERHALB DER SOLVE BERECHUNG ERSTELLTEN WERT in den Return-Vector übernehmen
			if(vecReturn!=null) {
				String sExpressionToSolve = (String) vecReturn.get(1);						
				String sExpressionSolved = this.solve(sExpressionToSolve);
																		
				if(vecReturn.size()>=2) vecReturn.removeElementAt(1);
				if(!StringZZZ.isEmpty(sExpressionSolved)){
					vecReturn.add(1, sExpressionSolved);
				}else {
					vecReturn.add(1, "");
				}					
			}
		}
		return vecReturn;
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
	public String solve(String sExpression) throws ExceptionZZZ{
		return this.solve_(sExpression, true);
	}
	
	@Override
	public String solve(String sExpression, boolean bRemoveSuroundingSeparators) throws ExceptionZZZ {
		return this.solve_(sExpression, bRemoveSuroundingSeparators);
	}
	
	private String solve_(String sExpression, boolean bRemoveSuroundingSeparators) throws ExceptionZZZ {
		String sReturn = sExpression;
		IKernelConfigSectionEntryZZZ objReturn = this.getEntryNew(); //Hier schon die Rückgabe vorbereiten, falls eine weitere Verarbeitung nicht konfiguriert ist.
		//Wichtig: Als oberste Methode immer ein neues Entry-Objekt holen. Dann stellt man sicher, das nicht mit Werten der vorherigen Suche gearbeitet wird.
		objReturn.setRaw(sExpression);

		main:{
			if(StringZZZ.isEmptyTrimmed(sExpression)) break main;
			if(!this.getFlag(IIniTagWithExpressionZZZ.FLAGZ.USEEXPRESSION)) break main;			
			if(!this.getFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER)) break main;			
			
			ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
			objReturnReference.set(objReturn);
			this.solve(sExpression, objReturnReference, bRemoveSuroundingSeparators);
			objReturn = objReturnReference.get();
			this.setEntry(objReturn);
			sReturn = objReturn.getValue();
		}//end main:
		this.setValue(sReturn);	//bei Solvern wird der ganze Text als Wert uebernommen.	
		objReturn.setValue(sReturn);						
		return sReturn;

	}

	@Override
	public Vector<String> solveFirstVector(String sExpression) throws ExceptionZZZ {
		return this.solveFirstVector_(sExpression, null, true);
	}

	@Override
	public Vector<String> solveFirstVector(String sExpression, boolean bRemoveSurroundingSeparators)throws ExceptionZZZ {
		return this.solveFirstVector_(sExpression, null, bRemoveSurroundingSeparators);
	}

	//### IKernelEntryReferenceSolveUserZZZ	
	@Override
	public int solve(String sLineWithExpression, ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference) throws ExceptionZZZ{
		return this.solve_(sLineWithExpression, objReturnReference, true);
	}
	
	@Override
	public int solve(String sExpressionIn, ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReferenceIn,	boolean bRemoveSurroundingSeparators) throws ExceptionZZZ {
		return this.solve_(sExpressionIn, objReturnReferenceIn, bRemoveSurroundingSeparators);
	}
	
	private int solve_(String sExpressionIn, ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReferenceIn,	boolean bRemoveSurroundingSeparators) throws ExceptionZZZ {
		int iReturn=0;
		String sReturn=sExpressionIn;
				 
		IKernelConfigSectionEntryZZZ objEntry = null;
		if(objReturnReferenceIn==null) {				
		}else {
			objEntry = objReturnReferenceIn.get();
		}
		if(objEntry==null) {
			objEntry = this.getEntryNew(); //Hier schon die Rückgabe vorbereiten, falls eine weitere Verarbeitung nicht konfiguriert ist.
										   //Wichtig: Als oberste Methode immer ein neues Entry-Objekt holen. Dann stellt man sicher, das nicht mit Werten der vorherigen Suche gearbeitet wird.
			//unten objEntry immer an das ReferenceOjekt zurueckgeben
		}//Achtung: Das objReturn Objekt NICHT generell uebernehmen. Es verfaelscht bei einem 2. Suchaufruf das Ergebnis.
		objEntry.setRaw(sExpressionIn);
		
		main:{
			if(StringZZZ.isEmptyTrimmed(sExpressionIn)) break main;
			if(!this.getFlag(IIniTagWithExpressionZZZ.FLAGZ.USEEXPRESSION)) break main;			
			if(!this.getFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER)) break main;			
		
			String sExpression = sExpressionIn;
			
			//Solver haben immer die Aufgabe einen IKernelConfigSectionEntryZZZ zu fuellen. Das ueber ein Referenzobjekt loesen.
			//Darin dann neue Zustaende fuellen isPathSolved, isVariableReplaced
			ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference= new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
			objReturnReference.set(objEntry);
			Vector<String> vecAll = this.solveFirstVector(sExpression, objReturnReference, bRemoveSurroundingSeparators);
			if(vecAll!=null) {
					
				//Zwischenstand zurückgeben. Implode, weil: Es koennen ja einfache String vor- bzw. nachstehend sein.
				objEntry = objReturnReference.get();
				objReturnReferenceIn.set(objEntry);
				this.setEntry(objEntry);
				
				sReturn = VectorZZZ.implode(vecAll);
				if(!sExpression.equals(sReturn)) {
					objEntry.isSolved(true);
				}
			}
		}//end main;
		this.setValue(sReturn);
		objEntry.setValue(sReturn);
		
		if(objEntry.isParsed()) iReturn = iReturn + 1;
		if(objEntry.isSolved()) iReturn = iReturn + 100;
		
		//Wichtig: Reference nach aussen zurueckgeben.
		if(objReturnReferenceIn!=null) objReturnReferenceIn.set(objEntry);
		return iReturn;
	}
	
	
	/**Methode muss vom konkreten "solver" ueberschrieben werden, wenn darin keine Pfade oder Variablen ersetzt werden sollen.
	 * @param sLineWithExpression
	 * @param objEntryReference
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 27.04.2023, 15:28:40
	 */	
	/* (non-Javadoc)
	 * @see basic.zKernel.file.ini.IKernelIniSolverZZZ#solveFirstVector(java.lang.String, basic.zBasic.util.datatype.calling.ReferenceZZZ)
	 */
	@Override
	public Vector<String> solveFirstVector(String sExpression,ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference) throws ExceptionZZZ {		
		return this.solveFirstVector_(sExpression, objReturnReference, true);
	}
	
	/* Aufloesen der INI-Pfade und Variablen. */
	@Override
	public Vector<String> solveFirstVector(String sExpressionIn, ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReferenceIn, boolean bRemoveSurroundingSeparators)	throws ExceptionZZZ {
		return this.solveFirstVector_(sExpressionIn, objReturnReferenceIn, bRemoveSurroundingSeparators);
	}

	private Vector<String> solveFirstVector_(String sExpressionIn, ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReferenceIn, boolean bRemoveSurroundingSeparators)	throws ExceptionZZZ {
		//Merke: Ein solve() ruft nicht direkt ein parse() auf.
		Vector<String> vecReturn = null;
		String sReturn = sExpressionIn; //Darin können also auch Variablen, etc. sein
		
		IKernelConfigSectionEntryZZZ objEntry = null;
		if(objReturnReferenceIn==null) {				
		}else {
			objEntry = objReturnReferenceIn.get();
		}
		if(objEntry==null) {
			objEntry = this.getEntryNew(); //Hier schon die Rückgabe vorbereiten, falls eine weitere Verarbeitung nicht konfiguriert ist.
										 //Wichtig: Als oberste Methode immer ein neues Entry-Objekt holen. Dann stellt man sicher, das nicht mit Werten der vorherigen Suche gearbeitet wird.				
		}//Achtung: Das objReturn Objekt NICHT generell uebernehmen. Es verfaelscht bei einem 2. Suchaufruf das Ergebnis.
		objEntry.setRaw(sExpressionIn);
			
		main:{
			if(StringZZZ.isEmptyTrimmed(sExpressionIn)) break main;			
			
			vecReturn = new Vector<String>();						
			if(!this.getFlag(IIniTagWithExpressionZZZ.FLAGZ.USEEXPRESSION)) break main;			
			if(!this.getFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER)) break main;
			
			String sExpression = sExpressionIn;
			
			if(this.getFlag(IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE)) {
				//ZUERST: Löse ggfs. übergebene Variablen auf.
				//!!! WICHTIG: BEI DIESEN AUFLOESUNGEN NICHT DAS UEBERGEORNETE OBJENTRY VERWENDEN, SONDERN INTERN EIN EIGENES!!! 
									
				//Merke: Als einfaches Tag gibt es keine zu verarbeitenden Flags, also muss man auch keine suchen und uebergeben.
				//ZTagFormulaIni_VariableZZZ<T> exDummy = new ZTagFormulaIni_VariableZZZ<T>();
				//String[] saFlagZpassed = FlagZFassadeZZZ.seekFlagZrelevantForObject(this, exDummy, true); //this.getFlagZ_passable(true, exDummy);
				
				String sExpressionOld = sExpression;
				String sExpressionTemp;					
				
				ZTagFormulaIni_VariableZZZ<T> exDummy = new ZTagFormulaIni_VariableZZZ<T>();
				String[] saFlagZpassed = FlagZFassadeZZZ.seekFlagZrelevantForObject(this, exDummy, true); //this.getFlagZ_passable(true, exDummy);
					
				ZTagFormulaIni_VariableZZZ<T> objVariable = new ZTagFormulaIni_VariableZZZ<T>(this.getHashMapVariable(), saFlagZpassed);
				while(objVariable.isExpression(sReturn)){
					sExpressionTemp = objVariable.parse(sReturn, false); //beim reinen Aufloesen die Z-Tags drin belassen, ausserdem ist das ein einfacher Tag OHNE IKernelConfigSectionEntry.
					if(StringZZZ.isEmpty(sExpressionTemp)) {
						break;
					}else if(sReturn.equals(sExpressionTemp)) {
						break;
					}else{
						sReturn = sExpressionTemp;							
					}
				} //end while
				objEntry.setValue(sReturn);
				if(sReturn!=sExpressionOld) {
					objEntry.isParsed(true);
					objEntry.isVariableSolved(true);
				}
				sExpression = sReturn;
			}	
			
			if(this.getFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH)) {	
				//DANACH: ALLE PATH-Ausdrücke, also [xxx]yyy ersetzen
				//Problem hier: [ ] ist auch der JSON Array-Ausdruck
				String sExpressionOld = sExpression;
				String sExpressionTemp;
				
				KernelZFormulaIni_PathZZZ<T> exDummy = new KernelZFormulaIni_PathZZZ<T>();
				String[] saFlagZpassed = FlagZFassadeZZZ.seekFlagZrelevantForObject(this, exDummy, true); //this.getFlagZ_passable(true, exDummy);
								
				KernelZFormulaIni_PathZZZ<T> objIniPath = new KernelZFormulaIni_PathZZZ<T>(this.getKernelObject(), this.getFileConfigKernelIni(), saFlagZpassed);
				while(objIniPath.isExpression(sReturn)){
						sExpressionTemp = objIniPath.parse(sReturn, true);//Da wir den Ausdruck komplett ersetzen wollen unbedingt die Z-Tags raus.	
						if(StringZZZ.isEmpty(sExpressionTemp)) {
							break;
						}else if(sReturn.equals(sExpressionTemp)) {
							break;
						}else{
							sReturn = sExpressionTemp;							
						}
				} //end while
				objEntry.setValue(sReturn);
				if(!sExpressionOld.equals(sReturn)) {
					objEntry.isParsed(true);
					objEntry.isPathSolved(true);
				}
				sExpression = sReturn;
			}//end if .getFlag(..USE_...Path...)	
			
			
			if(objReturnReferenceIn!=null) objReturnReferenceIn.set(objEntry);			
		}//end main:
		
		//NUN DEN INNERHALB DER EXPRESSION BERECHUNG ERSTELLTEN WERT in den Return-Vector übernehmen
		if(vecReturn!=null) {
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
		//nein, solve liefert nur 1 Wert zurueck if(objEntry!=null) objEntry.setValue(VectorZZZ.implode(vecReturn));
		if(objEntry!=null) objEntry.setValue(sReturn);
		if(objReturnReferenceIn!=null) objReturnReferenceIn.set(objEntry);
		return vecReturn;	
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