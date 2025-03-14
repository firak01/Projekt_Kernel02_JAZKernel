package basic.zKernel.file.ini;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IObjectWithExpressionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.abstractList.HashMapCaseInsensitiveZZZ;
import basic.zBasic.util.abstractList.Vector3ZZZ;
import basic.zBasic.util.abstractList.VectorUtilZZZ;
import basic.zBasic.util.datatype.calling.ReferenceZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.datatype.xml.XmlUtilZZZ;
import basic.zKernel.IKernelConfigSectionEntryZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernel.KernelConfigSectionEntryZZZ;
import basic.zKernel.config.KernelConfigSectionEntryUtilZZZ;
import basic.zKernel.flag.util.FlagZFassadeZZZ;
import custom.zKernel.file.ini.FileIniZZZ;

/**Diese Klasse verarbeitet ggf. Ausdruecke/Formeln in Ini-Dateien.
 *  Es kann dann in einem dieser Formeln z.B. auf den Property-Wert einer anderen Sektion zugegriffen werden. So entstehen 'dynamische' ini-Dateien.
 * @author lindhaueradmin
 *
 */
public class KernelCallIniSolverZZZ<T> extends AbstractKernelIniSolverZZZ<T> implements IKernelCallIniSolverZZZ, IKernelJavaCallIniSolverZZZ{
	private static final long serialVersionUID = -8017698515311079738L;
	public static String sTAG_NAME = "Z:Call";
	
	public KernelCallIniSolverZZZ() throws ExceptionZZZ{
		super("init");
		KernelCallIniSolverNew_(null, null);
	}
	
	public KernelCallIniSolverZZZ(String sFlag) throws ExceptionZZZ{
		super(sFlag);
		KernelCallIniSolverNew_(null, null);
	}
	
	public KernelCallIniSolverZZZ(IKernelZZZ objKernel, String[]saFlag) throws ExceptionZZZ{		
		super(objKernel,saFlag);
		KernelCallIniSolverNew_(null, null);
	}
	
	public KernelCallIniSolverZZZ(FileIniZZZ<T> objFileIni) throws ExceptionZZZ{
		super(objFileIni);//als IKernelUserZZZ - Object
		KernelCallIniSolverNew_(objFileIni, null);
	}
	
	public KernelCallIniSolverZZZ(FileIniZZZ<T> objFileIni, String[] saFlag) throws ExceptionZZZ{
		super(objFileIni, saFlag); //als IKernelUserZZZ - Object
		KernelCallIniSolverNew_(objFileIni, null);
	}
	
	public KernelCallIniSolverZZZ(IKernelZZZ objKernel, FileIniZZZ<T> objFileIni, String[] saFlag) throws ExceptionZZZ{
		super(objKernel);
		KernelCallIniSolverNew_(objFileIni, null);
	}
	
	public KernelCallIniSolverZZZ(FileIniZZZ<T> objFileIni, HashMapCaseInsensitiveZZZ<String,String> hmVariable, String[] saFlag) throws ExceptionZZZ{
		super(objFileIni, saFlag); //als IKernelUserZZZ - Object
		KernelCallIniSolverNew_(objFileIni, hmVariable);
	}
	
	public KernelCallIniSolverZZZ(IKernelZZZ objKernel, FileIniZZZ<T> objFileIni, HashMapCaseInsensitiveZZZ<String,String> hmVariable, String[] saFlag) throws ExceptionZZZ{
		super(objKernel, saFlag);
		KernelCallIniSolverNew_(objFileIni, hmVariable);
	}
			
	private boolean KernelCallIniSolverNew_(FileIniZZZ<T> objFileIn, HashMapCaseInsensitiveZZZ<String,String> hmVariableIn) throws ExceptionZZZ {
	 boolean bReturn = false;	
	 main:{ 		
			if(this.getFlag("init")==true){
				bReturn = true;
				break main;
			}
			
			FileIniZZZ<T> objFile=null;
			if(objFileIn==null ){
				objFile = this.getKernelObject().getFileConfigKernelIni();
				if(objFile==null) {
					ExceptionZZZ ez = new ExceptionZZZ("FileIni-Object", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
					throw ez; 
				}
			}else {
				objFile = objFileIn;
			}

			//Ubernimm ggfs. das Kernel-Objekt aus dem FileIni-Objekt
			if(this.getKernelObject()==null) this.setKernelObject(objFileIni.getKernelObject());
						
			if(hmVariableIn!=null){				
				this.setVariable(hmVariableIn);			//soll zu den Variablen aus derm Ini-File hinzuaddieren, bzw. ersetzen		
			}else {
				
				//Uebernimm ggfs. die Variablen aus dem FileIni-Objekt
				this.setFileConfigKernelIni(objFile);	
				if(objFile.getHashMapVariable()!=null){
					this.setHashMapVariable(objFile.getHashMapVariable());			
				}
			}
						
			bReturn = true;
	 	}//end main:
		return bReturn;

	 }//end function KernelJsonIniSolverNew_
					
	//###### Getter / Setter
	
	//### aus ITagBasicZZZ
		@Override
		public String getNameDefault() throws ExceptionZZZ {
		
		return KernelCallIniSolverZZZ.sTAG_NAME;
	}
	
		

		//### aus IParseUserZZZ
		@Override
		public void updateValueSolveCalled(IKernelConfigSectionEntryZZZ objEntry, boolean bIsSolveCalled) throws ExceptionZZZ{
			super.updateValueSolveCalled(objEntry, bIsSolveCalled);
					
			//Den "Elternsolver", siehe dazu auch TicketGOON20250308
			//... gibt es hier nicht
			
			//Den eigenen Solver
			objEntry.isCallSolveCalled(bIsSolveCalled);
		}
		
		@Override
		public void updateValueSolved(IKernelConfigSectionEntryZZZ objEntry, boolean bIsSolveCalled) throws ExceptionZZZ{
			super.updateValueSolved(objEntry, bIsSolveCalled);
					
			//Den "Elternsolver", siehe dazu auch TicketGOON20250308
			//... gibt es hier nicht
			
			
			//Den eigenen Solver
			objEntry.isCallSolved(bIsSolveCalled);
		}
		
		@Override
		public void updateValueSolvedChanged(IKernelConfigSectionEntryZZZ objEntry, boolean bIsSolveCalled) throws ExceptionZZZ{
			super.updateValueSolvedChanged(objEntry, bIsSolveCalled);
					
			//Den "Elternsolver", siehe dazu auch TicketGOON20250308
			//... gibt es hier nicht
			
			//Den eigenen Solver
			objEntry.isCallSolvedChanged(bIsSolveCalled);		
		}
	
	//+++++++++++++++++++++++++++++++++++++++++
	//### aus IParseEnabled		
	//Analog zu KernelJsonMapIniSolverZZZ, KernelZFormulaMathSolver, KernelEncrytptionIniSolver aufbauen...	
	@Override
	public Vector3ZZZ<String> parseFirstVector(String sLineWithExpression) throws ExceptionZZZ {		
		return this.parseFirstVector_(sLineWithExpression, null, true);
	}
	
	@Override
	public Vector3ZZZ<String> parseFirstVector(String sLineWithExpression, boolean bRemoveSurroundingSeparators) throws ExceptionZZZ {		
		return this.parseFirstVector_(sLineWithExpression, null, bRemoveSurroundingSeparators);
	}
	
	//### Aus IKernelEntryExpressionUserZZZ	
	@Override
	public Vector3ZZZ<String>parseFirstVector(String sExpression, ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReferenceIn, boolean bRemoveSurroundingSeparators) throws ExceptionZZZ{		
		return this.parseFirstVector_(sExpression, objReturnReferenceIn, bRemoveSurroundingSeparators);
	}
		
	private Vector3ZZZ<String>parseFirstVector_(String sExpressionIn, ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReferenceIn, boolean bRemoveSurroundingSeparators) throws ExceptionZZZ{		
		Vector3ZZZ<String> vecReturn = new Vector3ZZZ<String>();
		String sReturn = sExpressionIn; 
		String sReturnTag = null; String sReturnLine = null;
		String sReturnLineParsed = null; String sReturnLineParsedCallInner = null;
		boolean bUseExpression=false; boolean bUseSolver=false; boolean bUseCall=false; boolean bUseSolverThis = false;
		
		IKernelConfigSectionEntryZZZ objEntry = null;
		ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReference = null;			
		if(objReturnReferenceIn==null) {
			objReturnReference =  new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();														
		}else {
			objReturnReference = objReturnReferenceIn;
			objEntry = objReturnReference.get();
		}
		
		if(objEntry==null) {
			//ZWAR: Das Ziel ist es moeglichst viel Informationen aus dem entry "zu retten"      =  this.parseAsEntryNew(sExpression);  //nein, dann gehen alle Informationen verloren   objReturn = this.parseAsEntryNew(sExpression);
			objEntry = new KernelConfigSectionEntryZZZ<T>();   //nicht den eigenen Tag uebergeben, das ist der Entry der ganzen Zeile!
			objReturnReference.set(objEntry);
		}
		objEntry.setRaw(sExpressionIn);		
		sReturnLine = sExpressionIn;
		sReturnTag = this.getValue();
		//20250312 objEntry.isParseCalled(true);
						
		vecReturn.set(0, sExpressionIn);//nur bei in dieser Methode neu erstellten Vector.
		this.setRaw(sExpressionIn);
		objEntry.setRaw(sExpressionIn);	
		this.updateValueParseCalled();
		this.updateValueParseCalled(objEntry);

		main:{
			String sExpression = sExpressionIn;
			if(StringZZZ.isEmpty(sExpression)) break main;
			
			bUseExpression = this.isExpressionEnabledGeneral();
			if(!bUseExpression) break main;
							
			//TODOGOON20250308; //TICKETGOON20250308;; //Analog zu dem PARENT - Tagnamen muesste es auch eine Loesung für die CHILD - Tagnamen geben
			if(XmlUtilZZZ.containsTagName(sExpressionIn, KernelJavaCallIniSolverZZZ.sTAG_NAME, false)) {
				objEntry.isJavaCall(true);
			}
			
			if(XmlUtilZZZ.containsTagName(sExpression, this.getName(), false)){
				objEntry.isCall(true);
			}
			
			
			//Mehrere Ausdruecke. Dann muss der jeweilige "Rest-Bestandteil" des ExpressionFirst-Vectors weiter zerlegt werden.
			//Im Aufruf der Eltern-Methode findet ggfs. auch eine Aufloesung von Pfaden und eine Ersetzung von Variablen statt.
			ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReferenceParse = new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
			objReturnReferenceParse.set(objEntry);
			vecReturn = super.parseFirstVector(sExpression, objReturnReferenceParse, bRemoveSurroundingSeparators);
			objEntry = objReturnReferenceParse.get();
			sReturnTag = this.getValue();	
			sReturnLineParsed = VectorUtilZZZ.implode(vecReturn);
			sReturnLine = sReturnLineParsed;
			sReturn = sReturnLine;
			
			//Das weitere Verarbeiten haengt aber davon ab, ob der Solver angestellt ist.
			bUseSolver = this.isSolverEnabledGeneral();//getFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER);
			if(!bUseSolver) break main;
						
			bUseSolverThis = this.isSolverEnabledThis();		
			if(!bUseSolverThis) break main;
			
			//1.+ 2. Kein Versuch als HashMap oder ArrayList
			
			//3. Versuch als Einzelwert
			if(this.getFlag(IKernelJavaCallIniSolverZZZ.FLAGZ.USECALL_JAVA)){
				if(vecReturn!=null) sReturnTag = (String) vecReturn.get(1);
				sExpression = sReturnTag;
				if(StringZZZ.isEmpty(sExpression)) break main;
				
				//WICHTIG: DIE FLAGS VERERBEN !!!
				KernelJavaCallIniSolverZZZ<T> init4FlagLookup = new KernelJavaCallIniSolverZZZ<T>();
				String[] saFlagZpassed = FlagZFassadeZZZ.seekFlagZrelevantForObject(this, init4FlagLookup, true);
				
				//FileIniZZZ objFileIni = this.getFileIni();
				IKernelZZZ objKernel = this.getKernelObject();
				
				//Dann erzeuge neues KernelJavaCallSolverZZZ - Objekt.				
				KernelJavaCallIniSolverZZZ<T> objJavaCallSolver = new KernelJavaCallIniSolverZZZ<T>(objKernel, saFlagZpassed);
				
				//Merke: sReturn hat dann wg. parse noch Werte drum herum. Darum den Wert es Tags holen.
				ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReferenceJavaCall = new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
				objReturnReferenceJavaCall.set(objEntry);
				sReturnLineParsedCallInner=objJavaCallSolver.parse(sExpression, objReturnReferenceJavaCall, bRemoveSurroundingSeparators);
				objEntry = objReturnReferenceJavaCall.get();
				
				//20250304: Beim Parsen ist dient das Parsen des inneren Tag dazu hier ggf. noch Entry-Werte z.B. isPath() oder so, zu setzten.
				//          Falsch ist es aber den Wert des inneren Tags als eigenen Tag-Wert zu uebernehmen.
				//sReturnTag = objJavaCallSolver.getValue();
				//this.setValue(sReturnTag);
																			
				//NUN DEN INNERHALB DER EXPRESSION BERECHUNG ERSTELLTEN WERT uebernehmen				
				sReturnTag = sReturnLineParsedCallInner;
								
				//20250304: Den Returnwert des geparsten inneren Strings übernehmen. Der könnte geaendert worden sein.
				vecReturn.replace(sReturnLineParsedCallInner);//NUR BEIM SOLVEN DEN WERT IN VEC(1) UEBERNEHMEN				
				sReturnLine = VectorUtilZZZ.implode(vecReturn);
			}else {
				//Der JAVA-CALL Tag wird nicht verwendet. Aber der CALL-TAG schon.
				//Aber die umgebenden Tags sollen drinbleiben, darum hier nicht nacharbeiten.
				
				//Der Reine CALL-Tag Wert
				sReturnTag = this.getValue();
			}
		}//end main:
		
		this.setValue(sReturnTag);
		sReturn = sReturnLine;
		
		if(objEntry!=null) {
			objEntry.setValue(sReturnLine);
			objEntry.setValueFromTag(sReturnTag);
			if(bUseExpression) {		
				if(bUseSolver && bUseSolverThis) {
					//++++ Die Besonderheit ist hier: CALL und JAVA_CALL werden in einer Klasse erledigt....
					//     nur .isJavaCall() wird vorher gesetzt.
					if(sReturnLineParsedCallInner!=null) {
						if(sReturnLineParsed.equals(sReturnLineParsedCallInner)) {
							objEntry.isCallParsedChanged(true);
						}
					}																								
				}
				
				if(!sExpressionIn.equals(sReturnLine)) {					
					//objEntry.isParsedChanged(true);
					this.updateValueParsedChanged();
					this.updateValueParsedChanged(objEntry);
				}
				
				if(objReturnReferenceIn!=null)objReturnReferenceIn.set(objEntry);//Wichtig: Reference nach aussen zurueckgeben.
				this.adoptEntryValuesMissing(objEntry);
			}
		}					
		return vecReturn;
	}
	
	
	//+++++++++++++++++++++++++++++++++++++++++			
	@Override
	public IKernelConfigSectionEntryZZZ parseAsEntryNew(String sExpression) throws ExceptionZZZ {
		IKernelConfigSectionEntryZZZ objReturn = new KernelConfigSectionEntryZZZ<T>(); //Hier schon die Rückgabe vorbereiten, falls eine weitere Verarbeitung nicht konfiguriert ist.
		main:{			
			boolean bUseCall = this.getFlag(IKernelCallIniSolverZZZ.FLAGZ.USECALL);
			if(bUseCall) {
				objReturn = super.parseAsEntryNew(sExpression);	
				
				//Speichere nun das USECALL - Ergebnis auch ab.
				String sReturn = objReturn.getValue();
				objReturn.setValue(sReturn);
				objReturn.setValueCallSolvedAsExpression(sReturn);				
			}else {
				objReturn.setValue(sExpression);
				objReturn.setValueCallSolvedAsExpression(null);
			}									
		}//end main:
		return objReturn;
	}
	
	
	//### Andere Interfaces
	
	//### Aus ISolveEnabled	
	//Analog zu KernelJavaCallIniSolverZZZ, KernelJavaCallIniSolverZZZ, KernelJsonMapInisolver, KernelZFormulaMathSolver aufbauen... Der code ist im Parser
	//Merke: Mit folgender Methode wird über das konkrete Flag dieser Solver ein-/ausgeschaltet. 
		
	//       Z.B. wichtig für das Entfernen der geparsten Tags, oder halt dieses Entfernen weglassen.
	//Merke: Folgende Methoden muessen im konkreten Solver implementiert werden, da der abstrakte Solver die konkreten Flags zur deaktivierung diese konkreten Solvers nicht kennt.
	@Override
	public boolean isSolverEnabledThis() throws ExceptionZZZ {
		return this.getFlag(IKernelCallIniSolverZZZ.FLAGZ.USECALL);
	}
		
	@Override
	public String solveParsed(String sExpression,ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference) throws ExceptionZZZ {		
		return this.solveParsed_(sExpression, objReturnReference, true);
	}
	
	@Override
	public String solveParsed(String sExpressionIn, ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReferenceIn, boolean bRemoveSurroundingSeparators)	throws ExceptionZZZ {
		return this.solveParsed_(sExpressionIn, objReturnReferenceIn, bRemoveSurroundingSeparators);
	}
	
	//Analog zu AbstractkernelIniSolverZZZ, nur jetzt mit Call-Tag (vorher aber noch Pfade und ini-Variablen aufloesen)
	/**Methode ersetzt in der Zeile alle CALL Werte.
	 * Methode überschreibt den abstrakten "solver", weil erst einmal Pfade oder Variablen ersetzt werden sollen.
	 * @param sLineWithExpression
	 * @param objEntryReference
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 27.04.2023, 15:28:40
	 */
	private String solveParsed_(String sExpressionIn, ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReferenceIn, boolean bRemoveSurroundingSeparators)	throws ExceptionZZZ {
		String sReturn = null; String sReturnLine = null; String sReturnTag = null;
		boolean bUseExpression = false; boolean bUseSolver = false; boolean bUseSolverThis = false;
		
		
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
		objEntry.setRaw(sExpressionIn);		
		this.updateValueSolveCalled();
		this.updateValueSolveCalled(objEntry);
		sReturnLine = sExpressionIn;
		sReturnTag = sExpressionIn; //schlieslich ist das eine .solve ! PARSED ! Methode, also nicht   this.getValue();
		sReturn = sReturnLine;
		main:{			
			if(StringZZZ.isEmpty(sExpressionIn)) break main;
									
			bUseExpression = this.isExpressionEnabledGeneral(); 
			if(!bUseExpression) break main;
						
			bUseSolver = this.isSolverEnabledGeneral();
			if(!bUseSolver) break main;
			
			bUseSolverThis = this.isSolverEnabledThis(); //this.getFlag(IKernelCallIniSolverZZZ.FLAGZ.USECALL);		
			if(!bUseSolverThis) break main;
			
			String sExpression = sExpressionIn;
			
			//Aufloesen des CALL-Tags
			sReturnLine = this.solveParsed_Call_(sExpression, objReturnReference, bRemoveSurroundingSeparators);
			sReturnTag = this.getValue();
			objEntry = objReturnReference.get();	
									
			//objEntry.isSolved(true);
			this.updateValueSolved();
		}//end main
		
		//NUN DEN INNERHALB DER EXPRESSION BERECHUNG ERSTELLTEN WERT uebernehmen
		sReturn = sReturnLine;
		this.setValue(sReturnTag);		
		if(objEntry!=null) {		
			objEntry.setValue(sReturnLine);
			objEntry.setValueFromTag(sReturnTag);
			if(sExpressionIn!=null) {				
				if(!sExpressionIn.equals(sReturnLine)) {				
					this.updateValueSolvedChanged();;
				}
			}
			if(objReturnReferenceIn!=null)objReturnReferenceIn.set(objEntry);
		}
		return sReturn;	
	}
	
	private String solveParsed_Call_(String sExpressionIn, ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReferenceIn, boolean bRemoveSurroundingSeparators) throws ExceptionZZZ{		
		String sReturn = null; String sReturnLine = null; String sReturnTag = null;
		boolean bUseCall = false; boolean bUseCallJava = false;
		
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
		this.updateValueSolveCalled();
		this.updateValueSolveCalled(objEntry);
		sReturnLine = sExpressionIn;
		sReturnTag = sExpressionIn; //schliesslich ist das eine solve ! PARSED ! Methode, also nicht this.getValue();
		sReturn = sReturnLine;
		main:{
			if(StringZZZ.isEmpty(sExpressionIn)) break main;
			String sExpression = sExpressionIn;
			
			bUseCall = this.getFlag(IKernelCallIniSolverZZZ.FLAGZ.USECALL);
			if(bUseCall) {	
//TODOGOON20250311;//Testweise hier die Flag-Abfrage auskommentiert, damit diese Abfrage im JavaCall-Solver selbst gemacht werden.
//				bUseCallJava = this.getFlag(IKernelJavaCallIniSolverZZZ.FLAGZ.USECALL_JAVA);
//				if(bUseCallJava) {									
					//Analog zum vorgehen in parseFirstVector(...), nur hier wird vom JavaCallIniSolver.solveParsed() aufgerufen.
					sExpression = sReturnTag;
					
					//Hier KernelJavaCallSolverZZZ verwenden
					//WICHTIG: DIE FLAGS VERERBEN !!!
					KernelJavaCallIniSolverZZZ<T> init4FlagLookup = new KernelJavaCallIniSolverZZZ<T>();
					String[] saFlagZpassed = FlagZFassadeZZZ.seekFlagZrelevantForObject(this, init4FlagLookup, true);
					
					IKernelZZZ objKernel = this.getKernelObject();		
					KernelJavaCallIniSolverZZZ<T> objJavaCallSolver = new KernelJavaCallIniSolverZZZ<T>(objKernel, saFlagZpassed);
					
					//Merke: sReturn hat dann wg. parse noch Werte drum herum. Darum den Wert es Tags holen.
					ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReferenceJavaCall = new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
					objReturnReferenceJavaCall.set(objEntry);
					sReturnLine=objJavaCallSolver.solveParsed(sExpression, objReturnReferenceJavaCall, bRemoveSurroundingSeparators);
					objEntry = objReturnReferenceJavaCall.get();
					
					//NUN DEN INNERHALB DER EXPRESSION BERECHNUNG ERSTELLTEN WERT uebernehmen
					sReturnTag = objJavaCallSolver.getValue();
				
//				}else {
//					//Also: CALL-Tag soll aufgeloest werden, JAVA-CALL aber nicht. 
//					//Dann muss/darf nur der CALL-Tag entfernt werden. Eine weitere Aufloesung passiert ja nicht.
//					sExpression = sReturn;				
//					sReturn = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpression, KernelCallIniSolverZZZ.sTAG_NAME);							
//				
//				}//end if bAnyJavaCall			
			}//end if bUseCall
		}//end main:
		sReturn = sReturnLine;
		this.setValue(sReturnTag);
		if(objEntry!=null) {		
			objEntry.setValue(sReturnLine);
			objEntry.setValueFromTag(sReturnTag);
			if(sExpressionIn!=null) {
				if(!sExpressionIn.equals(sReturnLine)) {			
					this.updateValueSolvedChanged();
				}
			}
			if(objReturnReferenceIn!=null)objReturnReferenceIn.set(objEntry);
		}
		return sReturn;
	}
	
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	
	//### aus IConvertableZZZ
	@Override
	public boolean isConvertRelevant(String sToProof) throws ExceptionZZZ {			
		return false;
	}
	
	//###############################
	//### FLAG Handling
	
	//### aus IKernelCallIniSolverZZZ
	@Override
	public boolean getFlag(IKernelCallIniSolverZZZ.FLAGZ objEnumFlag) {
		return this.getFlag(objEnumFlag.name());
	}
	
	@Override
	public boolean setFlag(IKernelCallIniSolverZZZ.FLAGZ objEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		return this.setFlag(objEnumFlag.name(), bFlagValue);
	}

	@Override
	public boolean[] setFlag(IKernelCallIniSolverZZZ.FLAGZ[] objaEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		boolean[] baReturn=null;
		main:{
			if(!ArrayUtilZZZ.isNull(objaEnumFlag)) {
				baReturn = new boolean[objaEnumFlag.length];
				int iCounter=-1;
				for(IKernelCallIniSolverZZZ.FLAGZ objEnumFlag:objaEnumFlag) {
					iCounter++;
					boolean bReturn = this.setFlag(objEnumFlag, bFlagValue);
					baReturn[iCounter]=bReturn;
				}
			}
		}//end main:
		return baReturn;
	}

	@Override
	public boolean proofFlagExists(IKernelCallIniSolverZZZ.FLAGZ objaEnumFlag) throws ExceptionZZZ {
		return this.proofFlagExists(objaEnumFlag.name());
	}
	
	@Override
	public boolean proofFlagSetBefore(IKernelCallIniSolverZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
			return this.proofFlagSetBefore(objEnumFlag.name());
	}
	
	//### Aus Interface IKernelJavaCallIniSolverZZZ
	@Override
	public boolean getFlag(IKernelJavaCallIniSolverZZZ.FLAGZ objEnum_IKernelJavaCallIniSolverZZZ) {
		return this.getFlag(objEnum_IKernelJavaCallIniSolverZZZ.name());
	}
	
	@Override
	public boolean setFlag(IKernelJavaCallIniSolverZZZ.FLAGZ objEnum_IKernelJavaCallIniSolverZZZ, boolean bFlagValue) throws ExceptionZZZ {
		return this.setFlag(objEnum_IKernelJavaCallIniSolverZZZ.name(), bFlagValue);
	}
			
	@Override
	public boolean[] setFlag(IKernelJavaCallIniSolverZZZ.FLAGZ[] objaEnum_IKernelJavaCallIniSolverZZZ, boolean bFlagValue) throws ExceptionZZZ {
		boolean[] baReturn=null;
		main:{
			if(!ArrayUtilZZZ.isNull(objaEnum_IKernelJavaCallIniSolverZZZ)) {
				baReturn = new boolean[objaEnum_IKernelJavaCallIniSolverZZZ.length];
				int iCounter=-1;
				for(IKernelJavaCallIniSolverZZZ.FLAGZ objEnum_IKernelJavaCallIniSolverZZZ:objaEnum_IKernelJavaCallIniSolverZZZ) {
					iCounter++;
					boolean bReturn = this.setFlag(objEnum_IKernelJavaCallIniSolverZZZ, bFlagValue);
					baReturn[iCounter]=bReturn;
				}
			}
		}//end main:
		return baReturn;
	}

	@Override
	public boolean proofFlagExists(IKernelJavaCallIniSolverZZZ.FLAGZ objEnum_IKernelJavaCallIniSolverZZZ) throws ExceptionZZZ {
		return this.proofFlagExists(objEnum_IKernelJavaCallIniSolverZZZ.name());
	}
	
	@Override
	public boolean proofFlagSetBefore(IKernelJavaCallIniSolverZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
			return this.proofFlagSetBefore(objEnumFlag.name());
	}


}//End class
