package basic.zKernel.file.ini;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IObjectWithExpressionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.ReflectUtilZZZ;
import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.abstractList.HashMapCaseInsensitiveZZZ;
import basic.zBasic.util.abstractList.Vector3ZZZ;
import basic.zBasic.util.abstractList.VectorUtilZZZ;
import basic.zBasic.util.crypt.code.ICryptZZZ;
import basic.zBasic.util.datatype.calling.ReferenceZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.datatype.xml.XmlUtilZZZ;
import basic.zKernel.IKernelConfigSectionEntryZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernel.KernelConfigSectionEntryZZZ;
import basic.zKernel.config.KernelConfigSectionEntryUtilZZZ;
import custom.zKernel.file.ini.FileIniZZZ;

public class KernelJavaCallIniSolverZZZ<T>  extends AbstractKernelIniSolverZZZ<T>  implements IKernelJavaCallIniSolverZZZ{
	private static final long serialVersionUID = 6579389159644435205L;
	public static String sTAG_NAME = "Z:Java";	
	public KernelJavaCallIniSolverZZZ() throws ExceptionZZZ{
		super("init");
		KernelJavaCallIniSolverNew_(null, null);
	}
	
	public KernelJavaCallIniSolverZZZ(String sFlag) throws ExceptionZZZ{
		super(sFlag);
		KernelJavaCallIniSolverNew_(null, null);
	}
	
	public KernelJavaCallIniSolverZZZ(String[] saFlag) throws ExceptionZZZ{
		super(saFlag);
		KernelJavaCallIniSolverNew_(null, null);
	}
	
	public KernelJavaCallIniSolverZZZ(IKernelZZZ objKernel) throws ExceptionZZZ{
		super(objKernel);
		KernelJavaCallIniSolverNew_(null, null);
	}
	
	public KernelJavaCallIniSolverZZZ(FileIniZZZ<T> objFileIni) throws ExceptionZZZ{
		super(objFileIni); //als IKernelUserZZZ - Object
		KernelJavaCallIniSolverNew_(objFileIni, null);
	}
	
	public KernelJavaCallIniSolverZZZ(FileIniZZZ<T> objFileIni, String[] saFlag) throws ExceptionZZZ{
		super(objFileIni, saFlag); //als IKernelUserZZZ - Object
		KernelJavaCallIniSolverNew_(objFileIni, null);
	}
	
	public KernelJavaCallIniSolverZZZ(IKernelZZZ objKernel, String[] saFlag) throws ExceptionZZZ{
		super(objKernel,saFlag);
		KernelJavaCallIniSolverNew_(null, null);
	}
	
	public KernelJavaCallIniSolverZZZ(IKernelZZZ objKernel, FileIniZZZ<T> objFileIni, String[] saFlag) throws ExceptionZZZ{
		super(objKernel, saFlag);
		KernelJavaCallIniSolverNew_(objFileIni, null);
	}
	
	private boolean KernelJavaCallIniSolverNew_(FileIniZZZ<T> objFileIn, HashMapCaseInsensitiveZZZ<String,String> hmVariableIn) throws ExceptionZZZ {
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

	 }//end function KernelExpressionMathSolverNew_
	
	//### Aus ITagBasicZZZ	
	@Override
	public String getNameDefault() throws ExceptionZZZ {
		return KernelJavaCallIniSolverZZZ.sTAG_NAME;
	}
	
	//+++++++++++++++++++++++++++++++++++++++++
	//### aus IParseEnabled		
	@Override 
	public boolean isParserEnabledThis() throws ExceptionZZZ {
		return true; //Somit ist das Parsen vom Solven entkoppelt. Das wäre default in der abstracten Elternklasse, s. Solver:  return this.isSolverEnabledThis();
	}
	
	@Override
	public boolean isParserEnabledCustom() throws ExceptionZZZ {
		//Ziel ist, dass Solver, die Kinder-Tags haben auch deren Flags abrufen koennen.
		boolean bReturn = false;
		main:{
			boolean bEnabledThis = this.isParserEnabledThis();
			boolean bEnabledCall = this.getFlag(IKernelCallIniSolverZZZ.FLAGZ.USECALL);
					
			bReturn = bEnabledCall || bEnabledThis;
		}
		return bReturn;
	}
	
	//### Aus IParseUserZZZ
	@Override
	public void updateValueParseCustom(ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReferenceIn, String sExpressionIn) throws ExceptionZZZ {
		main:{
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
		
			if(StringZZZ.isEmpty(sExpressionIn)) break main;
			String sExpression = sExpressionIn;
		
			//#####################################################################
			super.updateValueParseCustom(objReturnReference, sExpressionIn);
						
			//Nun, ggfs. wird .solve() nicht aufgerufen, in dem alle Tags richtig geparsed werden
			//weil sie ihrerseits mit .solve() ausgeführt werden.
			
			//DARUM:
			//Hier die moeglichen enthaltenden Tags alle Pruefen..., siehe auch KernelExpressionIniHandlerZZZ
			
			//TODOGOON20250308; //TICKETGOON20250308;; //Analog zu dem PARENT - Tagnamen muesste es auch eine Loesung für die CHILD - Tagnamen geben
			if(this.isParserEnabledCustom()) {
				if(XmlUtilZZZ.containsTagName(sExpressionIn, KernelJavaCallIniSolverZZZ.sTAG_NAME, false)){
					objEntry.isCall(true);
					this.getEntry().isCall(true);
				}
			}
					
			if(!this.isParserEnabledThis()) break main;								
			if(XmlUtilZZZ.containsTagName(sExpressionIn, this.getName(), false)) {
				objEntry.isJavaCall(true);
				this.getEntry().isJavaCall(true);
			}
		}//end main:		}
	}
	
	//### aus ISolveEnablezZZZ
	@Override
	public boolean isSolverEnabledThis() throws ExceptionZZZ {		
		boolean bReturn = false;
		main:{
		  bReturn = this.getFlag(IKernelCallIniSolverZZZ.FLAGZ.USECALL);
		  if(!bReturn) break main;
		  
		  bReturn = this.getFlag(IKernelJavaCallIniSolverZZZ.FLAGZ.USECALL_JAVA);
		  if(!bReturn) break main;		  
		}//end main:
		return bReturn;
	}
		
	/**Methode ueberschreibt die Aufloesung von Pfaden und Ini-Variablen.
	 * @param sExpression
	 * @param objEntryReference
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 27.04.2023, 15:28:40
	 */	
	@Override
	public String solveParsed(String sExpression) throws ExceptionZZZ {
		return this.solveParsed_(sExpression, null, true);
	}
	
	@Override
	public String solveParsed(String sExpression, boolean bRemoveSurroundingSeparators)throws ExceptionZZZ {
		return this.solveParsed_(sExpression, null, bRemoveSurroundingSeparators);
	}
	
	@Override
	public String solveParsed(String sExpression,ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference) throws ExceptionZZZ {		
		return this.solveParsed_(sExpression, objReturnReference, true);
	}
	
	@Override
	public String solveParsed(String sExpressionIn, ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReferenceIn, boolean bRemoveSurroundingSeparators)	throws ExceptionZZZ {
		return this.solveParsed_(sExpressionIn, objReturnReferenceIn, bRemoveSurroundingSeparators);
	}
	
	private String solveParsed_(String sExpressionIn, ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReferenceIn, boolean bRemoveSurroundingSeparators) throws ExceptionZZZ {		
		String sReturn = null; String sReturnLine = null; String sReturnTag = null; String sReturnTagParsed = null; String sReturnTagSolved = null;
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
		this.setRaw(sExpressionIn);
		objEntry.setRaw(sExpressionIn);
		this.updateValueSolveCalled();//Stichwort TODOGOON20250308 , auch die Entry-Werte der Parents muessen gesetzt werden
		this.updateValueSolveCalled(objEntry);
		sReturnLine = sExpressionIn;
		sReturnTag = sExpressionIn; //nein, schliesslich heisst diese Methode solve ! parsed ! //this.getValue();
		sReturnTagParsed = sExpressionIn;
		sReturnTagSolved = sExpressionIn;
		sReturn = sReturnLine;
		main:{
			if(StringZZZ.isEmptyTrimmed(sExpressionIn)) break main;
			
			bUseExpression = this.isExpressionEnabledGeneral();			
			if(!bUseExpression) break main;
			
			String sExpression = sExpressionIn;
			
			bUseSolver = this.isSolverEnabledGeneral();
			if(!bUseSolver) break main;
			
			bUseSolverThis = this.isSolverEnabledThis(); 		
			if(!bUseSolverThis) break main;
			
			//##################################
			//### Besonderheiten dieses Solvers
			//###################################			
			///+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			String sJavaCallClass = null; String sJavaCallMethod = null;
											
			//++++++++++++++++++++++++++++++++++++++++++++
			//Nun den z:class Tag suchen				
			KernelJavaCall_ClassZZZ objJavaCallClass = new KernelJavaCall_ClassZZZ();
			if(objJavaCallClass.isExpression(sExpression)){		
				
				sReturnLine = objJavaCallClass.parse(sExpression);
				sJavaCallClass = objJavaCallClass.getValue();
				if(StringZZZ.isEmpty(sJavaCallClass)) break main;

				//Merke: wenn die Klasse nicht gefunden werden kann, dann gibt es später eine Fehlermeldung, die dies ausgibt
				objEntry.setCallingClassname(sJavaCallClass);
				
			}else{
				//Da gibt es wohl nix weiter auszurechen....	also die Werte als String nebeneinander setzen....
				sReturnLine = sExpression;					
				break main;
			}
			
			//++++++++++++++++++++++++++++++++++++++++++++
			//Nun den z:method Tag suchen
			KernelJavaCall_MethodZZZ objJavaCallMethod = new KernelJavaCall_MethodZZZ();
			if(objJavaCallMethod.isExpression(sExpression)){					
				sReturnLine = objJavaCallMethod.parse(sExpression);
				sJavaCallMethod = objJavaCallMethod.getValue();
				if(StringZZZ.isEmpty(sJavaCallMethod)) break main;
				
				objEntry.setCallingMethodname(sJavaCallMethod);
				
			}else{
				//Da gibt es wohl nix weiter auszurechen....	also die Werte als String nebeneinander setzen....
				sReturnLine = sExpression;
				break main;
			}
									
			//Nun die Methode aufrufen.
			Object objReturn = ReflectUtilZZZ.invokeStaticMethod(sJavaCallClass,sJavaCallMethod);
			if(objReturn==null)break main;		
			
			sReturnTag = objReturn.toString();
			sReturnTagSolved = sReturnTag;
			sReturnLine = sReturnTag;
			
			this.updateValueSolved();
			this.updateValueSolved(objEntry);						
		}//end main:	
						
		//NUN DEN INNERHALB DER EXPRESSION BERECHNUNG ERSTELLTEN WERT uebernehmen
		this.setValue(sReturnTag);
		sReturn = sReturnLine;
		
		if(objEntry!=null) {			
			objEntry.setValueCallSolved(sReturnLine);	
			objEntry.setValue(sReturnLine);
			objEntry.setValueFromTag(sReturnTag);
			if(objReturnReferenceIn!=null)objReturnReferenceIn.set(objEntry);//Wichtig: Reference nach aussen zurueckgeben.
			
			if(bUseExpression && bUseSolver && bUseSolverThis) {
				if(sReturnTagSolved!=null) {
					if(!sReturnTagSolved.equals(sReturnTagParsed)) {	
						this.updateValueSolvedChanged();
						this.updateValueSolvedChanged(objEntry);					
					}
				}	
				if(objEntry.isEncrypted()) objEntry.setValueDecrypted(sReturn);
			}
			this.adoptEntryValuesMissing(objEntry);			
		}		
		return sReturn;
	}
	

	//### Aus IParseUserZZZ
//	@Override
//	public void updateValueParseCalled() throws ExceptionZZZ {
//		IKernelConfigSectionEntryZZZ objEntry = this.getEntry();
//		this.updateValueParseCalled(objEntry, true);
//	}
//
//
//	@Override
//	public void updateValueParseCalled(boolean bIsParseCalled) throws ExceptionZZZ {
//		IKernelConfigSectionEntryZZZ objEntry = this.getEntry();
//		this.updateValueParseCalled(objEntry, bIsParseCalled);
//	}
//
//
//	@Override
//	public void updateValueParseCalled(IKernelConfigSectionEntryZZZ objEntry) throws ExceptionZZZ {
//		this.updateValueParseCalled(objEntry, true);
//	}


	@Override
	public void updateValueParseCalled(ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference, boolean bIsParseCalled) throws ExceptionZZZ {
		super.updateValueParseCalled(objReturnReference, bIsParseCalled);
		
		//Den "Elternparser", siehe dazu auch TicketGOON20250308
		IKernelConfigSectionEntryZZZ objEntry = objReturnReference.get();
		if(this.getFlag(IKernelCallIniSolverZZZ.FLAGZ.USECALL)){
			objEntry.isCallParseCalled(bIsParseCalled);
		}
		
		//Den eigenen Parser
		if(this.isParserEnabledThis()) {
			objEntry.isJavaCallParseCalled(bIsParseCalled);
		}
	}
	
	@Override
	public void updateValueParsed(ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference, boolean bIsJavaCallParsed) throws ExceptionZZZ {
		super.updateValueParsed(objReturnReference, bIsJavaCallParsed);
		
		//Den "ElternPaser", siehe dazu auch TicketGOON20250308
		IKernelConfigSectionEntryZZZ objEntry = objReturnReference.get();
		if(this.getFlag(IKernelCallIniSolverZZZ.FLAGZ.USECALL)){
			objEntry.isCallParsed(bIsJavaCallParsed);
		}
		
		//Den eigenen Parser
		if(this.isParserEnabledThis()) {
			objEntry.isJavaCallParsed(bIsJavaCallParsed);
		}
	}
	
	//### aus ISolveUserZZZ
	@Override
	public void updateValueSolveCalled(IKernelConfigSectionEntryZZZ objEntry, boolean bIsSolveCalled) throws ExceptionZZZ{
		super.updateValueSolveCalled(objEntry, bIsSolveCalled);
				
		//Den "Elternsolver", siehe dazu auch TicketGOON20250308
		objEntry.isCallSolveCalled(bIsSolveCalled);//der Aufruf findet immer statt, auch ohne gesetztes Flag
		
		//Den eigenen Solver
		objEntry.isJavaCallSolveCalled(bIsSolveCalled);
	}
	
	@Override
	public void updateValueSolved(IKernelConfigSectionEntryZZZ objEntry, boolean bIsSolveCalled) throws ExceptionZZZ{
		super.updateValueSolved(objEntry, bIsSolveCalled);
				
		//Den "Elternsolver", siehe dazu auch TicketGOON20250308
		if(this.getFlag(IKernelCallIniSolverZZZ.FLAGZ.USECALL)){
			objEntry.isCallSolved(bIsSolveCalled);
		}
		
		//Den eigenen Solver
		if(this.isSolverEnabledThis()) {
			objEntry.isJavaCallSolved(bIsSolveCalled);
		}
	}
	
	@Override
	public void updateValueSolvedChanged(IKernelConfigSectionEntryZZZ objEntry, boolean bIsSolveChanged) throws ExceptionZZZ{
		super.updateValueSolvedChanged(objEntry, bIsSolveChanged);
				
		//Den "Elternsolver", siehe dazu auch TicketGOON20250308
		if(this.getFlag(IKernelCallIniSolverZZZ.FLAGZ.USECALL)){
			objEntry.isCallSolvedChanged(bIsSolveChanged);
		}
		
		//Den eigenen Solver
		if(this.isSolverEnabledThis()) {
			objEntry.isJavaCallSolvedChanged(bIsSolveChanged);
		}
	}
		
	@Override
	public String[] parseAsArray(String sExpression, String sDelimiter) throws ExceptionZZZ{
		String[] saReturn = null;
		main:{			
			boolean bUse = this.getFlag(IKernelJavaCallIniSolverZZZ.FLAGZ.USECALL_JAVA);
			if(bUse) {
				saReturn = super.parseAsArray(sExpression, sDelimiter);
			}else {
				saReturn = StringZZZ.explode(sExpression, sDelimiter);
			}		
		}//end main
		return saReturn;
	}
	
	//### Aus IKernelIniSolver
	@Override
	public IKernelConfigSectionEntryZZZ parseAsEntryNew(String sLineWithExpression) throws ExceptionZZZ {
		//Wichtig: Das InnerEntry-Objekt vom externen Entry-Objekt TRENNEN, also nicht: IKernelConfigSectionEntryZZZ objReturn = this.getEntryNew(); //Hier schon die Rückgabe vorbereiten, falls eine weitere Verarbeitung nicht konfiguriert ist.
		IKernelConfigSectionEntryZZZ objReturn = new KernelConfigSectionEntryZZZ<T>();		
		main:{			
			boolean bUseCall = this.getFlag(IKernelCallIniSolverZZZ.FLAGZ.USECALL);
			
			if(bUseCall) {				
				boolean bUseJava = this.getFlag(IKernelJavaCallIniSolverZZZ.FLAGZ.USECALL_JAVA);
				if(bUseJava) {
					ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
					objReturnReference.set(objReturn);
					
					String sReturn = super.parse(sLineWithExpression, objReturnReference);		
					objReturn = objReturnReference.get();
					
					String sValue = objReturn.getValue();
					if(!sLineWithExpression.equals(sValue)) {
						objReturn.setValueCallSolvedAsExpression(sValue);
					}
				}else{
					objReturn = this.getEntryNew();
					objReturn.isCall(true);
					objReturn.setValue(sLineWithExpression);
				}
			}else {
				objReturn = this.getEntryNew();
				objReturn.setValue(sLineWithExpression);
			}									
		}//end main:
		return objReturn;
	}
	
	//+++ Folgende Methoden koennen ueberschrieben werden um fuer den konkreten Solver eine Loesung einzubauen.
	//### aus ISolveZZZ
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
		boolean bUseExpression = false; boolean bUseSolver = false; boolean bUseSolverThis = false;
			
		IKernelConfigSectionEntryZZZ objEntry = null;
		ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReference = null;
		if(objReturnReferenceIn==null) {				
			objReturnReference = new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();								
		}else {
			objReturnReference = objReturnReferenceIn;
			objEntry = objReturnReference.get();
		}
		if(objEntry==null) {
			//ZWAR: Das Ziel ist es moeglichst viel Informationen aus dem entry "zu retten"      =  this.parseAsEntryNew(sExpression);  //nein, dann gehen alle Informationen verloren   objReturn = this.parseAsEntryNew(sExpression);
			objEntry = new KernelConfigSectionEntryZZZ<T>();   //nicht den eigenen Tag uebergeben, das ist der Entry der ganzen Zeile!
			objReturnReference.set(objEntry);
		}	
			
		sReturnTag = this.getValue();
		sReturnLine = sExpressionIn;
				
		main:{	
			if(vecExpressionIn==null) break main;
				
			bUseExpression = this.isExpressionEnabledGeneral(); 
			if(!bUseExpression) break main;
			
			bUseSolver = this.isSolverEnabledEveryRelevant(); //this.getFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER);
			if(!bUseSolver) break main;
			
			bUseSolverThis = this.isSolverEnabledThis(); //this.getFlag(IKernelCallIniSolverZZZ.FLAGZ.USECALL);		
			if(!bUseSolverThis) break main;
				
			sExpressionIn = VectorUtilZZZ.implode(vecExpressionIn);
			this.setRaw(sExpressionIn);
			objEntry.setRaw(sExpressionIn);
			
			//##############
			//### Hier die konkrete Erweiterung
			
			//Es muss nicht nur der eigene Tag und der Z-Tag entfernt werden,
			//sondern auch der JSON-Tag, quasi als "Elterntag" auch entfernen.			
			if(bUseExpression & bUseSolver & bUseSolverThis){
				String sTagStart = this.getTagPartOpening();
				String sTagEnd = this.getTagPartClosing();
				if(sTagStart.equalsIgnoreCase("<Z>")) {
					//dann mache nix... der Tag wird anders behandelt...
				}else {
					sTagStart = XmlUtilZZZ.computeTagPartOpening(KernelCallIniSolverZZZ.sTAG_NAME);
					sTagEnd = XmlUtilZZZ.computeTagPartClosing(KernelCallIniSolverZZZ.sTAG_NAME);					
					KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(vecExpressionIn, sTagStart,sTagEnd, true, false); //also AN JDEDER POSITION (d.h. nicht nur am Anfang) von aussen nach innen!!!
				}
			}	
						
			//##############
			sReturnTag = (String) vecReturn.get(1);
			sReturnLine = VectorUtilZZZ.implode(vecReturn);
		}//end main:
	
		//#################################
		//Den Wert ersetzen, wenn es was zu ersetzen gibt.
		if(vecReturn!=null) vecReturn.replace(sReturnTag);
		this.setValue(sReturnTag);
		sReturn = sReturnLine;
						
		if(objEntry!=null) {
			//NUN DEN INNERHALB DER EXPRESSION BERECHNUNG ERSTELLTEN WERT uebernehmen
			objEntry.setValue(sReturnLine);
			objEntry.setValueFromTag(sReturnTag);
			this.updateValueSolved(objEntry);
			if(sExpressionIn!=null) {												
				if(!sExpressionIn.equals(sReturn)) {
					this.updateValueSolvedChanged();
					this.updateValueSolvedChanged(objEntry); //zur Not nur, weil die Z-Tags entfernt wurden.									
				}
			}			
			if(objReturnReferenceIn!=null) objReturnReferenceIn.set(objEntry);
		}
		return vecReturn;
	}		

	//### aus IConvertableZZZ
	@Override
	public boolean isConvertRelevant(String sToProof) throws ExceptionZZZ {
		return false;
	}
	
	//#######################################
	//### FLAG Handling
	
	//### Aus Interface IKernelCallIniSolverZZZ
	@Override
	public boolean getFlag(IKernelCallIniSolverZZZ.FLAGZ objEnum_IKernelCallIniSolverZZZ) {
		return this.getFlag(objEnum_IKernelCallIniSolverZZZ.name());
	}
	
	@Override
	public boolean setFlag(IKernelCallIniSolverZZZ.FLAGZ objEnum_IKernelCallIniSolverZZZ, boolean bFlagValue) throws ExceptionZZZ {
		return this.setFlag(objEnum_IKernelCallIniSolverZZZ.name(), bFlagValue);
	}
	
	@Override
	public boolean[] setFlag(IKernelCallIniSolverZZZ.FLAGZ[] objaEnum_IKernelCallIniSolverZZZ, boolean bFlagValue) throws ExceptionZZZ {
		boolean[] baReturn=null;
		main:{
			if(!ArrayUtilZZZ.isNull(objaEnum_IKernelCallIniSolverZZZ)) {
				baReturn = new boolean[objaEnum_IKernelCallIniSolverZZZ.length];
				int iCounter=-1;
				for(IKernelCallIniSolverZZZ.FLAGZ objEnum_IKernelCallIniSolverZZZ:objaEnum_IKernelCallIniSolverZZZ) {
					iCounter++;
					boolean bReturn = this.setFlag(objEnum_IKernelCallIniSolverZZZ, bFlagValue);
					baReturn[iCounter]=bReturn;
				}
			}
		}//end main:
		return baReturn;
	}
	
	@Override
	public boolean proofFlagExists(IKernelCallIniSolverZZZ.FLAGZ objEnum_IKernelCallIniSolverZZZ) throws ExceptionZZZ {
		return this.proofFlagExists(objEnum_IKernelCallIniSolverZZZ.name());
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