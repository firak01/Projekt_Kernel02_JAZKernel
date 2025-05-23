package basic.zKernel.file.ini;

import java.util.Vector;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IObjectWithExpressionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.abstractList.ArrayListExtendedZZZ;
import basic.zBasic.util.abstractList.HashMapCaseInsensitiveZZZ;
import basic.zBasic.util.abstractList.HashMapExtendedZZZ;
import basic.zBasic.util.abstractList.Vector3ZZZ;
import basic.zBasic.util.abstractList.VectorUtilZZZ;
import basic.zBasic.util.crypt.code.ICryptZZZ;
import basic.zBasic.util.datatype.calling.ReferenceArrayZZZ;
import basic.zBasic.util.datatype.calling.ReferenceHashMapZZZ;
import basic.zBasic.util.datatype.calling.ReferenceZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.datatype.xml.XmlUtilZZZ;
import basic.zKernel.IKernelConfigSectionEntryZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernel.KernelConfigSectionEntryZZZ;
import basic.zKernel.config.KernelConfigSectionEntryUtilZZZ;
import basic.zKernel.flag.util.FlagZFassadeZZZ;
import custom.zKernel.file.ini.FileIniZZZ;

/** Klasse, welche die "Ini-Solver" Klassen anwendet.
 *  Somit ist sie die "Ausgangsklasse" für die FileIniZZZ-Methoden
 *  einen Wert zu errechnen.
 * 
 * @author Fritz Lindhauer, 02.05.2023, 19:55:30
 * 
 */
public class KernelExpressionIniHandlerZZZ<T>  extends AbstractKernelIniSolverZZZ<T> implements IKernelExpressionIniHandlerZZZ {
	private static final long serialVersionUID = -6430027792689200422L;
	public static String sTAG_NAME = "Z";
		
	public KernelExpressionIniHandlerZZZ() throws ExceptionZZZ{
		super("init");
		KernelExpressionIniHandlerNew_(null, null);
	}
	
	public KernelExpressionIniHandlerZZZ(FileIniZZZ<T> objFileIni) throws ExceptionZZZ{
		super(objFileIni);
		KernelExpressionIniHandlerNew_(objFileIni, null);
	}
	
	public KernelExpressionIniHandlerZZZ(FileIniZZZ<T> objFileIni, String[] saFlag) throws ExceptionZZZ{
		super(objFileIni.getKernelObject(), saFlag);
		KernelExpressionIniHandlerNew_(objFileIni, null);
	}
	
	public KernelExpressionIniHandlerZZZ(IKernelZZZ objKernel, FileIniZZZ<T> objFileIni, String[] saFlag) throws ExceptionZZZ{
		super(objKernel, saFlag);
		KernelExpressionIniHandlerNew_(objFileIni, null);
	}
	
	public KernelExpressionIniHandlerZZZ(FileIniZZZ<T> objFileIni, HashMapCaseInsensitiveZZZ<String,String> hmVariable) throws ExceptionZZZ{
		super(objFileIni);
		KernelExpressionIniHandlerNew_(objFileIni, hmVariable);
	}
	
	public KernelExpressionIniHandlerZZZ(FileIniZZZ<T> objFileIni, HashMapCaseInsensitiveZZZ<String,String> hmVariable, String[] saFlag) throws ExceptionZZZ{
		super(objFileIni, saFlag);
		KernelExpressionIniHandlerNew_(objFileIni, hmVariable);
	}
	
	public KernelExpressionIniHandlerZZZ(IKernelZZZ objKernel, FileIniZZZ<T> objFileIni, HashMapCaseInsensitiveZZZ<String,String> hmVariable, String[] saFlag) throws ExceptionZZZ{
		super(objKernel, saFlag);
		KernelExpressionIniHandlerNew_(objFileIni, hmVariable);
	}
	
	
	private boolean KernelExpressionIniHandlerNew_(FileIniZZZ<T> objFileIni, HashMapCaseInsensitiveZZZ<String,String> hmVariable) throws ExceptionZZZ {
	 boolean bReturn = false;	
	 main:{
			if(this.getFlag("init")==true){
				bReturn = true;
				break main;
			}
		
			if(objFileIni==null ){
				ExceptionZZZ ez = new ExceptionZZZ("FileIni-Object", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
				throw ez; 
			}else{
				this.setFileConfigKernelIni(objFileIni);						
			}
			
			if(hmVariable!=null){				
					this.setVariable(hmVariable);			//soll zu den Variablen aus derm Ini-File hinzuaddieren, bzw. ersetzen		
			}else {
				if(hmVariable!=null){
					this.setHashMapVariable(hmVariable);
				}
			}
			
			bReturn = true;
	 	}//end main:
		return bReturn;
	 }//end function KernelExpressionIniSolverNew_
	
	//### GETTER / SETTER
	
	
	//######### Interfaces #############################################
	
	//+++++++++++++++++++++++++++++++++++++++++
	//### aus IParseEnabled		
	@Override 
	public boolean isParserEnabledThis() throws ExceptionZZZ {
		return true; //das wäre default, s. Solver:  return this.isSolverEnabledThis();
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
		
		
			//#####################################################################
			//Flags entscheiden, ob es weiter geht
			super.updateValueParseCustom(objReturnReference, sExpressionIn); //isExpression setzen
					
			if(!this.isExpressionEnabledGeneral()) break main;
			if(!this.isParserEnabledGeneral()) break main;
			if(!this.isParserEnabledThis()) break main;
			
			//Nun, ggfs. wird .solve() nicht aufgerufen, in dem alle Tags richtig geparsed werden
			//weil sie ihrerseits mit .solve() ausgeführt werden.
			
			//DARUM:
			//Hier die moeglichen enthaltenden Tags alle Pruefen..., siehe z.B. auch KernelCallIniSolverZZZ			
			//TODOGOON20250308; //TICKETGOON20250308;; //Analog zu dem PARENT - Tagnamen muesste es auch eine Loesung für die CHILD - Tagnamen geben
			
			objEntry = objReturnReference.get();			
			if(XmlUtilZZZ.containsTagName(sExpressionIn, KernelCallIniSolverZZZ.sTAG_NAME, false)) {
				objEntry.isCall(true);
				this.getEntry().isCall(true);
			}
			
			if(XmlUtilZZZ.containsTagName(sExpressionIn, KernelJavaCallIniSolverZZZ.sTAG_NAME, false)) {
				objEntry.isJavaCall(true);
				this.getEntry().isJavaCall(true);
			}
		
		}//end main:
	}

	//### aus IParseEnabledZZZ
//	@Override
//	public Vector3ZZZ<String> parseFirstVector(String sExpression, boolean bKeepSurroundingSeparatorsOnParse) throws ExceptionZZZ {
//		//Muss ueberschrieben werden, damit die "einfache Tag" Methode nicht greift und wir mit der parse - Methode dieser konkreten Klasse arbeiten.
//		return this.parseFirstVector_(sExpression, null, bKeepSurroundingSeparatorsOnParse, true);
//	}
//		
	//### Aus IKernelEntryExpressionUserZZZ	
//	@Override
//	public Vector3ZZZ<String>parseFirstVector(String sExpression, ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReference) throws ExceptionZZZ{
//		return this.parseFirstVector_(sExpression, objReturnReference, true, true);
//	}
//
//	@Override
//	public Vector3ZZZ<String> parseFirstVector(String sExpression, ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReferenceIn, boolean bKeepSurroundingSeparatorsOnParse) throws ExceptionZZZ {
//		return this.parseFirstVector_(sExpression, objReturnReferenceIn, bKeepSurroundingSeparatorsOnParse, true);
//	}
//	
//	/**Methode ueberschreibt das parsen des cascaded Tags.
//	 * Loest nun INI-Pfade und INI-Variablen auf, 
//	 * ABER: macht kein vollständiges solve()!!!
//	 **/
//	private Vector3ZZZ<String> parseFirstVector_(String sExpressionIn, ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReferenceIn, boolean bKeepSurroundingSeparatorsOnParse, boolean bIgnoreCase) throws ExceptionZZZ {
//		String sReturn = null; String sReturnTag = null; String sReturnLine = null;
//		Vector3ZZZ<String> vecReturn = new Vector3ZZZ<String>();
//		boolean bUseExpression = false; boolean bUseParser = false; boolean bUseParserThis = false;
//		
//		ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference= null;		
//		IKernelConfigSectionEntryZZZ objEntry = null;
//		if(objReturnReferenceIn==null) {
//			objReturnReference = new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
//		}else {
//			objReturnReference = objReturnReferenceIn;
//		}
//		objEntry = objReturnReference.get();
//		if(objEntry==null) {
//			//Nein, das holt auch ein neues inneres Objekt und die teilen sich dann die Referenz... objEntry = this.getEntryNew(); //Hier schon die Rückgabe vorbereiten, falls eine weitere Verarbeitung nicht konfiguriert ist.
//			 //Wichtig: Als oberste Methode immer ein neues Entry-Objekt holen. Dann stellt man sicher, das nicht mit Werten der vorherigen Suche gearbeitet wird.
//			objEntry = new KernelConfigSectionEntryZZZ<T>();
//			objReturnReference.set(objEntry);
//		}//Achtung: Das objReturn Objekt NICHT generell uebernehmen. Es verfaelscht bei einem 2. Suchaufruf das Ergebnis.
//
//		this.updateValueParseCalled();
//		this.updateValueParseCalled(objReturnReference);
//		
//		main:{		
//			if(StringZZZ.isEmpty(sExpressionIn)) break main;			
//			String sExpression = sExpressionIn;
//			
//			this.setRaw(sExpressionIn);
//			objEntry.setRaw(sExpressionIn);	
//			
//			sReturnTag = this.getValue();
//			sReturnLine=sExpressionIn;
//			vecReturn.set(0, sReturnLine);//nur bei in dieser Methode neu erstellten Vector.
//			sReturn = sReturnLine;
//						
//			this.updateValueParseCustom(objReturnReference, sExpression);
//						
//			bUseExpression = this.isExpressionEnabledGeneral();
//			if(!bUseExpression) break main;						
//			
//			bUseParser = this.isParserEnabledGeneral();
//			if(!bUseParser) break main;
//						
//			//Falls man diesen Tag aus dem Parsen (des Gesamtstrings) rausnimmt, muessen die umgebenden Tags drin bleiben
//			bUseParserThis = this.isParserEnabledThis();
//			if(!bUseParserThis)break main;
//			
//			//###########################################
//			//### 
//			//###########################################
//			
//			//Zerlegen des Z-Tags, d.h. Teil vorher, Teil dahinter.
//			ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReferenceParserSuper= new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
//			objReturnReferenceParserSuper.set(objEntry);
//			vecReturn = super.parseFirstVector(sExpressionIn, objReturnReferenceParserSuper, bKeepSurroundingSeparatorsOnParse);
//			objEntry = objReturnReferenceParserSuper.get();
//			if(vecReturn==null) break main;
//			if(StringZZZ.isEmpty((String)vecReturn.get(1))) break main; //Dann ist der Tag nicht enthalten und es darf(!) nicht weitergearbeitet werden.
//			
//			sReturnTag = (String) vecReturn.get(1);
//			this.setValue(sReturnTag);
//			
//			//+++ Der endgueltige Wert der Zeile und eigenen Wert setzen 
//			//Als echten Ergebniswert aber die <Z>-Tags und den eigenen Tag rausrechnen, falls gewuenscht			
//			vecReturn = this.parseFirstVectorPost(vecReturn, objReturnReferenceParserSuper, bKeepSurroundingSeparatorsOnParse);
//			sReturnTag = this.getValue();
//			sReturnLine = VectorUtilZZZ.implode(vecReturn);	
//			
//			this.updateValueParsed();
//			this.updateValueParsed(objReturnReference);
//		}//end main:
//		
//		//NUN DEN INNERHALB DER EXPRESSION BERECHUNG ERSTELLTEN WERT uebernehmen		
//		this.setValue(sReturnTag);	
//		sReturn = sReturnLine;	
//		
//		if(objEntry!=null) {
//			objEntry.setValue(sReturnLine);
//			objEntry.setValueFromTag(sReturnTag);
//			if(objReturnReference!=null)objReturnReference.set(objEntry);//Wichtig: Reference nach aussen zurueckgeben.
//			if(bUseExpression) {				
//				if(sExpressionIn!=null) {			 							
//					if(!sExpressionIn.equals(sReturn)) {								
//						this.updateValueParsedChanged();
//						this.updateValueParsedChanged(objReturnReference);
//					}
//				}						
//				this.adoptEntryValuesMissing(objEntry);
//			}
//		}
//		return vecReturn;
//	}
	
	
	
	//### aus  ISolveEnabledZZZ
	@Override
	public boolean isSolverEnabledThis() throws ExceptionZZZ {
		return this.getFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER);
	}
		
	/** Berechne die INI-Werte. 
	 *  Methode ueberschreibt die Aufloesung von Pfaden und Ini-Variablen.
	 * 	 
	 *  STRATEGIE:
	 *  1. Ini-Variablen und Pfade aufloesen (wird im solve(...) gemacht.
	
   *  Hier also:	
	 *  2. JavaCalls aufloesen
	 *  3. JSON Werte
	 *  4. Entschluesseln 			 
	 *  
	 *  IDEE: 
	 *  Die Entschluesselung zuletzt. Damit kann z.B. theoretisch in den JSON-Werten verschluesselter Inhalt enthalten sein.
	 *  
	 * @param sExpression
	 * @param objReturnReference
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 06.05.2023, 07:41:02
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
		this.updateValueSolveCalled();
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
			
			//Löse die anderen Solver auf.
			boolean bUseFormula = this.getFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA);
			boolean bUseCall = this.getFlag(IKernelCallIniSolverZZZ.FLAGZ.USECALL);
			boolean bUseJson = this.getFlag(IKernelJsonIniSolverZZZ.FLAGZ.USEJSON);
			boolean bUseEncryption = this.getFlag(IKernelEncryptionIniSolverZZZ.FLAGZ.USEENCRYPTION);				
			if(!(bUseFormula | bUseCall | bUseJson | bUseEncryption )) break main;

			String sExpressionUsed = sExpression;
			if(bUseFormula) {				
				//Hier KernelZFormulIniSolverZZZ verwenden
				KernelZFormulaIniSolverZZZ<T> formulaSolverDummy = new KernelZFormulaIniSolverZZZ<T>();
				String[] saFlagZpassed = FlagZFassadeZZZ.seekFlagZrelevantForObject(this, formulaSolverDummy, true);
				HashMapCaseInsensitiveZZZ<String,String>hmVariable = this.getHashMapVariable();
					
				KernelZFormulaIniSolverZZZ<T> objFormulaSolver = new KernelZFormulaIniSolverZZZ<T>(this.getKernelObject(), this.getFileConfigKernelIni(), hmVariable, saFlagZpassed);
				ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReferenceFormula = new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
				objReturnReferenceFormula.set(objEntry);
				sExpressionUsed = objFormulaSolver.solve(sExpressionUsed, objReturnReferenceFormula, false); //behalte die Z-Tags, fuer ggfs. andere Abarbeitungsschritte
				objEntry = objReturnReferenceFormula.get();
				if(objEntry.isExpression()){
					this.getEntry().isExpression(true);
					if(objEntry.isFormulaSolved()){	
						this.getEntry().isFormulaSolved(true);
						objEntry.setValueFormulaSolvedAndConverted(objEntry.getValue());						
						sExpressionUsed = objEntry.getValue(); //Zur Verarbeitung weitergeben
					}
				}//Merke: Keinen Else-Zweig zum false setzen. Vielleicht war in einem vorherigen Schritt ja durchaus eine Formel enthalten
			}//end bUseFormula
				
									
			if(bUseCall){
				//Hier KernelCallIniSolverZZZ verwenden
				KernelCallIniSolverZZZ<T> callDummy = new KernelCallIniSolverZZZ<T>();
				String[] saFlagZpassed = FlagZFassadeZZZ.seekFlagZrelevantForObject(this, callDummy, true);
				
				//Merke: objReturnReference ist ein Hilfsobjekt, mit dem CallByReference hinsichtlich der Werte realisiert wird.
				boolean bForFurtherProcessing = false; 
				ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReferenceSolverCall = new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
				objReturnReferenceSolverCall.set(objEntry);
				
				boolean bAnyCall = KernelConfigSectionEntryUtilZZZ.getCallSolved(this.getFileConfigKernelIni(), sExpressionUsed, bUseCall, bForFurtherProcessing, saFlagZpassed, objReturnReferenceSolverCall);
				objEntry = objReturnReferenceSolverCall.get();
				if(bAnyCall) {						
					this.getEntry().isCallSolved(true);
					objEntry.isCallSolved(true);
					
					String sValueCallSolved = objEntry.getValue(); //Zur Verarbeitung weitergeben
					this.getEntry().setValueCallSolved(sValueCallSolved);										
					objEntry.setValueCallSolved(sValueCallSolved);
					sExpressionUsed = sValueCallSolved;
				}//Merke: Keinen Else-Zweig zum false setzen. Vielleicht war in einem vorherigen Schritt ja durchaus ein Call enthalten
			}//end if busecall
											
				
			if(bUseJson) {
				//Hier KernelJsonInisolverZZZ verwenden 
				KernelJsonIniSolverZZZ<T> exDummy03 = new KernelJsonIniSolverZZZ<T>();
				String[] saFlagZpassed = FlagZFassadeZZZ.seekFlagZrelevantForObject(this, exDummy03, true); //this.getFlagZ_passable(true, exDummy);					
				
				ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReferenceSolverJson = new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
				objReturnReferenceSolverJson.set(objEntry);
				
				//Merke: objReturnValue ist ein Hilfsobjekt, mit dem CallByReference hinsichtlich der Werte realisiert wird.
				ReferenceArrayZZZ<String>objalsReturnValueJsonSolved=new ReferenceArrayZZZ<String>();
				ReferenceHashMapZZZ<String,String>objhmReturnValueJsonSolved=new ReferenceHashMapZZZ<String,String>();
				
				//Merke: Die statischen Methoden leisten mehr als nur die ...Solver....
				//       Durch den int Rückgabwert sorgen sie nämlich für die korrekte Befüllung von 
				//       objReturn, also auch der darin verwendeten Flags bIsJson, bIsJsonMap, etc.					
				sExpressionUsed = KernelConfigSectionEntryUtilZZZ.getJsonSolved(this.getFileConfigKernelIni(), sExpressionUsed, bUseJson, saFlagZpassed, objReturnReferenceSolverJson, objalsReturnValueJsonSolved,objhmReturnValueJsonSolved);					
				objEntry = objReturnReferenceSolverJson.get();
				if(objEntry.isExpression()) {
					this.getEntry().isExpression(true);
					if(objEntry.isJson()) {
						this.getEntry().isJson(true);
						if(objEntry.isJsonArray()) {
							this.getEntry().isJsonArray(true);								
							this.setValue(objalsReturnValueJsonSolved.getArrayList());
							//NEIN, nicht den DebugString verwenden, es gibt auch einen echten String.  
							//der wird automatisch beim Setzen der ArrayList mitgesetzt.
							//this.setValue(ArrayListExtendedZZZ.debugString(objalsReturnValueJsonSolved.getArrayList()));						
						}
						
						if(objEntry.isJsonMap()) {	
							this.getEntry().isJsonMap(true);
							this.setValue(objhmReturnValueJsonSolved.get());	
							//NEIN, nicht den DebugString verwenden, es gibt auch einen echten String.  
							//der wird automatisch beim Setzen der HashMap mitgesetzt. 
							//this.setValue(HashMapExtendedZZZ.computeDebugString(objhmReturnValueJsonSolved.get()));
						}
						sExpressionUsed = objEntry.getValue(); //Zur Verarbeitung weitergeben
					}//Merke: Keinen Else-Zweig zum false setzen. Vielleicht war in einem vorherigen Schritt ja durchaus Json enthalten
				}
			}//end if busejson									
									
			if(bUseEncryption) {
				KernelEncryptionIniSolverZZZ<T> encryptionDummy = new KernelEncryptionIniSolverZZZ<T>();
				String[] saFlagZpassed = FlagZFassadeZZZ.seekFlagZrelevantForObject(this, encryptionDummy, true);
				
				//Merke: objReturnReference ist ein Hilfsobjekt, mit dem CallByReference hinsichtlich der Werte realisiert wird.
				boolean bForFurtherProcessing = false;
				ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReferenceEncryption = new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
				objReturnReferenceEncryption.set(objEntry);
				boolean bAnyEncryption = KernelConfigSectionEntryUtilZZZ.getEncryptionSolved(this.getFileConfigKernelIni(), sExpressionUsed, bUseEncryption, bForFurtherProcessing, saFlagZpassed, objReturnReferenceEncryption);
				objEntry = objReturnReferenceEncryption.get();
				if(bAnyEncryption) {
					
					objEntry.isRawEncrypted(true);
					String sLineDecrypted = objEntry.getRawDecrypted();//Wert zur weiteren Verarbeitung weitergeben						
					if(!sExpressionUsed.equals(sLineDecrypted)) {													
						objEntry.isDecrypted(true);
						objEntry.setRawDecrypted(sLineDecrypted);
						objEntry.setValue(sLineDecrypted);       //quasi erst mal den Zwischenstand festhalten.							
					}//Merke: Keinen Else-Zweig zum false setzen. Vielleicht war in einem vorherigen Schritt ja durchaus Verschluesselung enthalten
					
					sExpressionUsed = sLineDecrypted; //Zur Verarbeitung weitergeben			
				}//Merke: Keinen Else-Zweig. Vielleicht war in einem vorherigen Schritt ja durchaus Encryption enthalten
			}//end if buseencryption						
			this.updateValueSolved();
			this.updateValueSolved(objEntry);
			sReturnLine = sExpressionUsed;	
			sReturnTagSolved = sReturnLine;
		}//end main:
						
		//NUN DEN INNERHALB DER EXPRESSION BERECHUNG ERSTELLTEN WERT uebernehmen
		sReturn = sReturnLine;
		this.setValue(sReturn);	//Der Handler bekommt die ganze Zeile als Wert	
		if(objEntry!=null) {
			objEntry.setValue(sReturn);
			objEntry.setValueFromTag(sReturn);
			if(objReturnReferenceIn!=null)objReturnReferenceIn.set(objEntry);
			if(sReturnTagSolved!=null) {				
				if(!sReturnTagSolved.equals(sReturnTagParsed)) {
					this.updateValueSolvedChanged();
					this.updateValueSolvedChanged(objEntry);
				}
			}			
			this.adoptEntryValuesMissing(objEntry);
		}
		return sReturn;				
	}

	//### Aus ITagBasicZZZ
	@Override
	public String getNameDefault() {
		return KernelExpressionIniHandlerZZZ.sTAG_NAME;
	}
	
	//### Interface aus IKernelExpressionIniSolver
	public IKernelConfigSectionEntryZZZ getEntry() throws ExceptionZZZ {
		if(this.objEntryInner==null) {
			this.objEntryInner = new KernelConfigSectionEntryZZZ<T>(this);			
		}
		return this.objEntryInner;
	}
	public void setEntry(IKernelConfigSectionEntryZZZ objEntry) throws ExceptionZZZ{
		this.objEntryInner = objEntry;
	}
	
	//##############################################
	//### FLAG HANDLING
	
	//### aus IKernelZFormulaIniZZZ
	@Override
	public boolean getFlag(IKernelZFormulaIniZZZ.FLAGZ objEnumFlag) {
		return this.getFlag(objEnumFlag.name());
	}
	
	@Override
	public boolean setFlag(IKernelZFormulaIniZZZ.FLAGZ objEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		return this.setFlag(objEnumFlag.name(), bFlagValue);
	}
	
	@Override
	public boolean[] setFlag(IKernelZFormulaIniZZZ.FLAGZ[] objaEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		boolean[] baReturn=null;
		main:{
			if(!ArrayUtilZZZ.isNull(objaEnumFlag)) {
				baReturn = new boolean[objaEnumFlag.length];
				int iCounter=-1;
				for(IKernelZFormulaIniZZZ.FLAGZ objEnumFlag:objaEnumFlag) {
					iCounter++;
					boolean bReturn = this.setFlag(objEnumFlag, bFlagValue);
					baReturn[iCounter]=bReturn;
				}
			}
		}//end main:
		return baReturn;
	}
	

	@Override
	public boolean proofFlagExists(IKernelZFormulaIniZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
		return this.proofFlagExists(objEnumFlag.name());
	}
	
	@Override
	public boolean proofFlagSetBefore(IKernelZFormulaIniZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
			return this.proofFlagSetBefore(objEnumFlag.name());
	}


	//### Aus Interface IKernelExpressionIniSolverZZZ
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
	
	//### aus IKernelEncryptionIniSolverZZZ
	@Override
	public boolean getFlag(IKernelEncryptionIniSolverZZZ.FLAGZ objEnumFlag) {
		return this.getFlag(objEnumFlag.name());
	}
	
	@Override
	public boolean setFlag(IKernelEncryptionIniSolverZZZ.FLAGZ objEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		return this.setFlag(objEnumFlag.name(), bFlagValue);
	}
	
	@Override
	public boolean[] setFlag(IKernelEncryptionIniSolverZZZ.FLAGZ[] objaEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		boolean[] baReturn=null;
		main:{
			if(!ArrayUtilZZZ.isNull(objaEnumFlag)) {
				baReturn = new boolean[objaEnumFlag.length];
				int iCounter=-1;
				for(IKernelEncryptionIniSolverZZZ.FLAGZ objEnumFlag:objaEnumFlag) {
					iCounter++;
					boolean bReturn = this.setFlag(objEnumFlag, bFlagValue);
					baReturn[iCounter]=bReturn;
				}
			}
		}//end main:
		return baReturn;
	}
	
	@Override
	public boolean proofFlagExists(IKernelEncryptionIniSolverZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
		return this.proofFlagExists(objEnumFlag.name());
	}
	
	@Override
	public boolean proofFlagSetBefore(IKernelEncryptionIniSolverZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
			return this.proofFlagSetBefore(objEnumFlag.name());
	}

	//### Aus IKernelJsonMapIniSolverZZZ
	@Override
	public boolean getFlag(IKernelJsonMapIniSolverZZZ.FLAGZ objEnumFlag) {
		return this.getFlag(objEnumFlag.name());
	}
	
	@Override
	public boolean setFlag(IKernelJsonMapIniSolverZZZ.FLAGZ objEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		return this.setFlag(objEnumFlag.name(), bFlagValue);
	}

	@Override
	public boolean[] setFlag(IKernelJsonMapIniSolverZZZ.FLAGZ[] objaEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		boolean[] baReturn=null;
		main:{
			if(!ArrayUtilZZZ.isNull(objaEnumFlag)) {
				baReturn = new boolean[objaEnumFlag.length];
				int iCounter=-1;
				for(IKernelJsonMapIniSolverZZZ.FLAGZ objEnumFlag:objaEnumFlag) {
					iCounter++;
					boolean bReturn = this.setFlag(objEnumFlag, bFlagValue);
					baReturn[iCounter]=bReturn;
				}
			}
		}//end main:
		return baReturn;
	}
	
	@Override
	public boolean proofFlagExists(IKernelJsonMapIniSolverZZZ.FLAGZ objaEnumFlag) throws ExceptionZZZ {
		return this.proofFlagExists(objaEnumFlag.name());
	}
	
	@Override
	public boolean proofFlagSetBefore(IKernelJsonMapIniSolverZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
			return this.proofFlagSetBefore(objEnumFlag.name());
	}

	//### aus IKernelJsonArrayIniSolverZZZ
	@Override
	public boolean getFlag(IKernelJsonArrayIniSolverZZZ.FLAGZ objEnumFlag) {
		return this.getFlag(objEnumFlag.name());
	}
	
	@Override
	public boolean setFlag(IKernelJsonArrayIniSolverZZZ.FLAGZ objEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		return this.setFlag(objEnumFlag.name(), bFlagValue);
	}

	@Override
		public boolean[] setFlag(IKernelJsonArrayIniSolverZZZ.FLAGZ[] objaEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
			boolean[] baReturn=null;
			main:{
				if(!ArrayUtilZZZ.isNull(objaEnumFlag)) {
					baReturn = new boolean[objaEnumFlag.length];
					int iCounter=-1;
					for(IKernelJsonArrayIniSolverZZZ.FLAGZ objEnumFlag:objaEnumFlag) {
						iCounter++;
						boolean bReturn = this.setFlag(objEnumFlag, bFlagValue);
						baReturn[iCounter]=bReturn;
					}
				}
			}//end main:
			return baReturn;
		}
	
	@Override
	public boolean proofFlagExists(IKernelJsonArrayIniSolverZZZ.FLAGZ objaEnumFlag) throws ExceptionZZZ {
		return this.proofFlagExists(objaEnumFlag.name());
	}
	
	@Override
	public boolean proofFlagSetBefore(IKernelJsonArrayIniSolverZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
			return this.proofFlagSetBefore(objEnumFlag.name());
	}
	
	//### aus IKernelJsonIniSolverZZZ
	@Override
	public boolean getFlag(IKernelJsonIniSolverZZZ.FLAGZ objEnumFlag) {
		return this.getFlag(objEnumFlag.name());
	}
	
	@Override
	public boolean setFlag(IKernelJsonIniSolverZZZ.FLAGZ objEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		return this.setFlag(objEnumFlag.name(), bFlagValue);
	}

	@Override
		public boolean[] setFlag(IKernelJsonIniSolverZZZ.FLAGZ[] objaEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
			boolean[] baReturn=null;
			main:{
				if(!ArrayUtilZZZ.isNull(objaEnumFlag)) {
					baReturn = new boolean[objaEnumFlag.length];
					int iCounter=-1;
					for(IKernelJsonIniSolverZZZ.FLAGZ objEnumFlag:objaEnumFlag) {
						iCounter++;
						boolean bReturn = this.setFlag(objEnumFlag, bFlagValue);
						baReturn[iCounter]=bReturn;
					}
				}
			}//end main:
			return baReturn;
		}
	
	@Override
	public boolean proofFlagExists(IKernelJsonIniSolverZZZ.FLAGZ objaEnumFlag) throws ExceptionZZZ {
		return this.proofFlagExists(objaEnumFlag.name());
	}
	
	@Override
	public boolean proofFlagSetBefore(IKernelJsonIniSolverZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
			return this.proofFlagSetBefore(objEnumFlag.name());
	}
	
	
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
}
