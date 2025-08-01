package basic.zKernel.file.ini;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Vector;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.abstractList.ArrayListExtendedZZZ;
import basic.zBasic.util.abstractList.HashMapCaseInsensitiveZZZ;
import basic.zBasic.util.abstractList.HashMapExtendedZZZ;
import basic.zBasic.util.abstractList.Vector3ZZZ;
import basic.zBasic.util.abstractList.VectorUtilZZZ;
import basic.zBasic.util.datatype.calling.ReferenceZZZ;
import basic.zBasic.util.datatype.json.JsonUtilZZZ;
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
public class KernelJsonMapIniSolverZZZ<T> extends AbstractKernelIniSolverZZZ<T> implements IKernelJsonMapIniSolverZZZ{
	private static final long serialVersionUID = 1250082037671481847L;
	public static String sTAG_NAME = "JSON:MAP";
	public static String sTAG_PARENT_NAME = KernelJsonIniSolverZZZ.sTAG_NAME;
		
	public KernelJsonMapIniSolverZZZ() throws ExceptionZZZ{
		super("init");
		KernelJsonMapIniSolverNew_(null, null);
	}
	
	public KernelJsonMapIniSolverZZZ(String sFlag) throws ExceptionZZZ{
		super(sFlag);
		KernelJsonMapIniSolverNew_(null, null);
	}
	
	public KernelJsonMapIniSolverZZZ(String[] saFlag) throws ExceptionZZZ{
		super(saFlag);
		KernelJsonMapIniSolverNew_(null, null);
	}
	
	public KernelJsonMapIniSolverZZZ(IKernelZZZ objKernel) throws ExceptionZZZ{
		super(objKernel);
		KernelJsonMapIniSolverNew_(null, null);
	}
	
	public KernelJsonMapIniSolverZZZ(FileIniZZZ objFileIni) throws ExceptionZZZ{
		super(objFileIni);//als IKernelUserZZZ - Object
		KernelJsonMapIniSolverNew_(objFileIni, null);
	}
	
	public KernelJsonMapIniSolverZZZ(FileIniZZZ objFileIni, String[] saFlag) throws ExceptionZZZ{
		super(objFileIni, saFlag);//als IKernelUserZZZ - Object
		KernelJsonMapIniSolverNew_(objFileIni, null);
	}
	
	public KernelJsonMapIniSolverZZZ(IKernelZZZ objKernel, String[] saFlag) throws ExceptionZZZ{
		super(objKernel, saFlag);
		KernelJsonMapIniSolverNew_(null, null);
	}
	
	public KernelJsonMapIniSolverZZZ(IKernelZZZ objKernel, FileIniZZZ objFileIni, String[] saFlag) throws ExceptionZZZ{
		super(objKernel, saFlag);
		KernelJsonMapIniSolverNew_(objFileIni, null);
	}
		
	private boolean KernelJsonMapIniSolverNew_(FileIniZZZ objFileIn, HashMapCaseInsensitiveZZZ<String,String> hmVariableIn) throws ExceptionZZZ {
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
	 }//end function KernelJsonMapIniSolverNew_
			
	//###### Getter / Setter
	
	//+++++++++++++++++++++++++++
	public HashMap<String,String> computeHashMapFromJson(String sLineWithJson) throws ExceptionZZZ{
		HashMap hmReturn = new HashMap<String,String>();
		String sReturn = sLineWithJson;
		main:{
			if(StringZZZ.isEmpty(sLineWithJson)) break main;
			
			//Keine weiteren Flags verwenden, ggfs. nur noch Formeln aufloesen....
			
			boolean bUseFormula = this.getFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA);		
			boolean bUseFormulaMath = this.getFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH);				
			if(bUseFormula && bUseFormulaMath){		
				//20180714 Hole Ausdrücke mit <z:math>...</z:math>, wenn das entsprechende Flag gesetzt ist.
				//Beispiel dafür: TileHexMap-Projekt: GuiLabelFontSize_Float
				//GuiLabelFontSize_float=<Z><Z:math><Z:val>[THM]GuiLabelFontSizeBase_float</Z:val><Z:op>*</Z:op><Z:val><z:var>GuiZoomFactorUsed</z:var></Z:val></Z:math></Z>
												
				//Dann erzeuge neues KernelExpressionMathSolverZZZ - Objekt.
				KernelZFormulaMathSolverZZZ<T> formulaSolverDummy = new KernelZFormulaMathSolverZZZ<T>();
				String[] saFlagZpassed = FlagZFassadeZZZ.seekFlagZrelevantForObject(this, formulaSolverDummy, true);
				
				KernelZFormulaMathSolverZZZ objMathSolver = new KernelZFormulaMathSolverZZZ(objKernel, saFlagZpassed);
													
				//2. Ist in dem String math?	Danach den Math-Teil herausholen und in einen neuen vec packen.
				//Sollte das nicht für mehrerer Werte in einen Vector gepackt werden und dann immer weiter mit vec.get(1) ausgerechnet werden?				
				boolean bAnyFormula = false;
				String sRaw = sReturn;
				String sRawOld = sRaw;
				while(objMathSolver.isExpression(sRaw)){
					bAnyFormula = true;
						
					String sValueMath = objMathSolver.solve(sReturn);										
					if(!StringZZZ.equals(sRaw, sValueMath)){
						System.out.println(ReflectCodeZZZ.getPositionCurrent()+ ": Value durch ExpressionIniSolver verändert von '" + sRaw + "' nach '" + sValueMath +"'");
					}else {
						break;
					}
					sRaw = sReturn;
					if(sRawOld.equals(sRaw)) break; //Sonst Endlosschleife.
					sRawOld = sRaw;
					sReturn = sValueMath;					
				}	
				
			}
						
			//Merke: Die Reihenfolge der MapEintraege nicht berücksichtigen		
			HashMap hmFromJson = JsonUtilZZZ.toHashMap(sReturn);
			if(hmFromJson==null) break main; //dadurch ist hmReturn nie NULL !!!
			
			hmReturn = hmFromJson;
			
			//Merke: Die Positionen vec(0) und vec(2) werden also dann entfallen.
		}//end main:
		return hmReturn;
	}
	
	@SuppressWarnings("unchecked")
	public HashMap<String,String> computeLinkedHashMap(String sExpressionIn) throws ExceptionZZZ{
		LinkedHashMap<String,String> hmReturn = new LinkedHashMap<String,String>();
		String sReturn = sExpressionIn;  //Hole den Wert innerhalb von JSON:MAP. Merke: Ausgangswert ist normalerweise schon ein nach Z-Tag geparster Wert.
		String sReturnTag = null; String sReturnLine = null;
		main:{
			if(StringZZZ.isEmpty(sExpressionIn)) break main;
			if(this.getFlag(IKernelJsonIniSolverZZZ.FLAGZ.USEJSON.name())== false) break main;
			if(this.getFlag(IKernelJsonMapIniSolverZZZ.FLAGZ.USEJSON_MAP)== false) break main;
			
			Vector<String> vecReturn = this.parseFirstVector(sExpressionIn);//Hole hier erst einmal die Variablen-Anweisung und danach die IniPath-Anweisungen und ersetze sie durch Werte.
			if(vecReturn==null) break main;		
			if(StringZZZ.isEmpty(vecReturn.get(1).toString())) break main; //Dann ist der Tag nicht enthalten und es darf(!) nicht weitergearbeitet werden.
			
			//Beschränke das Ausrechnen auf den JSON-MAP Teil  sReturn = VectorZZZ.implode(vecAll);//Erst den Vector der "übersetzten" Werte zusammensetzen
			sReturnTag = VectorUtilZZZ.getElementAsValueOf(vecReturn, 1);//Damit wird aus dem NullObjectZZZ ggfs. NULL als Wert geholt.
			sReturnLine = VectorUtilZZZ.implode(vecReturn);
			if(this.getFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH)){				
				//Dann erzeuge neues KernelExpressionMathSolverZZZ - Objekt.
				KernelZFormulaMathSolverZZZ<T> objMathSolverDummy = new KernelZFormulaMathSolverZZZ<T>();
				String[] saFlagZpassed = FlagZFassadeZZZ.seekFlagZrelevantForObject(this, objMathSolverDummy, true); //this.getFlagZ_passable(true, exDummy);					
								
				KernelZFormulaMathSolverZZZ objMathSolver = new KernelZFormulaMathSolverZZZ(this.getKernelObject(), this.getFileConfigKernelIni(), saFlagZpassed); 
													
				//2. Ist in dem String math?	Danach den Math-Teil herausholen und in einen neuen vec packen.
				//Sollte das nicht für mehrerer Werte in einen Vector gepackt werden und dann immer weiter mit vec.get(1) ausgerechnet werden?
				String sExpression = sReturn;
				String sExpressionOld = sExpression;
				while(objMathSolver.isExpression(sExpression)){
					String sValueMath = objMathSolver.solve(sExpression);
					if(sExpression.equals(sValueMath)) break; //Sicherheitsmassnahme gegen Endlosschleife
					sExpression = sValueMath;						
				}					
				if(!sExpressionOld.equals(sExpression)) {
					//TODO GOON;//20241009: Eigentlich muss hier noch objReference uebergeben werden und dort objEntry raus geholt werden...
					objEntryInner.isFormulaMathSolved(true);
					objEntryInner.setValueFormulaSolvedAndConverted(sExpression);
				}
				sReturnTag=sExpression;
				sReturnLine = VectorUtilZZZ.implode(vecReturn);
				sReturn = sReturnLine;
			}
			
			//ANSCHLIESSEND die HashMap erstellen
			if(!JsonUtilZZZ.isJsonValid(sReturnTag)) break main;
			
			//Ziel: Die Reihenfolge berücksichtige		
			hmReturn = (LinkedHashMap<String, String>) JsonUtilZZZ.toLinkedHashMap(sReturnTag);
			this.setValue(hmReturn);
			this.setValue(sReturnTag);
			//oder einen extra Json Value Wert einfuehren? TODOGOON 20241009;
			//this.setValue(hmReturn.toString());
			//this.setValueJson(sReturn);
			//Merke: Die Positionen vec(0) und vec(2) werden also dann entfallen.
		}//end main:
		return hmReturn;
	}

	//### Aus ITagBasicZZZ
	@Override
	public String getNameDefault() throws ExceptionZZZ {
		return KernelJsonMapIniSolverZZZ.sTAG_NAME;
	}
	
	//### Aus ITagBasicChildZZZ
	@Override
	public String getParentNameDefault() throws ExceptionZZZ {
		return KernelJsonMapIniSolverZZZ.sTAG_PARENT_NAME;
	}
			
	//+++++++++++++++++++++++++++++++++++++++++
	//### aus IParseEnabled		
	@Override 
	public boolean isParserEnabledThis() throws ExceptionZZZ {
	  //Ziel ist es das Parsen vom Solven zu entkoppelt.
	  //Das wäre default in der abstracten Elternklasse, s. Solver:  return this.isSolverEnabledThis();
	  //return true; 
		  
	  boolean bReturn = false;
		main:{
//			  bReturn = this.getFlag(IKernelJsonIniSolverZZZ.FLAGZ.USEJSON);
//			  if(!bReturn) break main;
		  
		  bReturn = this.getFlag(IKernelJsonMapIniSolverZZZ.FLAGZ.USEJSON_MAP);
		  if(!bReturn) break main;		  
		}//end main:
		return bReturn;
	}
		
	@Override 
	public boolean isParserEnabledCustom() throws ExceptionZZZ {		
		//Ziel ist, dass Solver, die Eltern-Tags haben auch deren Flags abrufen koennen.
		boolean bReturn = false;
		main:{
			boolean bEnabledThis = this.isParserEnabledThis();
			boolean bEnabledJson = this.getFlag(IKernelJsonIniSolverZZZ.FLAGZ.USEJSON);
				
			bReturn = bEnabledJson || bEnabledThis; 
		}
		return bReturn; 	
	}
	
//	//### aus IParseEnabled		
//	//Analog zu KernelJsonMapIniSolverZZZ, KernelZFormulaMathSolver, KernelEncrytptionIniSolver aufbauen...	
//	@Override
//	public Vector3ZZZ<String> parseFirstVector(String sExpression) throws ExceptionZZZ {		
//		return this.parseFirstVector_(sExpression, null, true);
//	}
//	
//	@Override
//	public Vector3ZZZ<String> parseFirstVector(String sExpression, boolean bKeepSurroundingSeparatorsOnParse) throws ExceptionZZZ {		
//		return this.parseFirstVector_(sExpression, null, bKeepSurroundingSeparatorsOnParse);
//	}
	
	//### aus IParseUserZZZ
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
				if(XmlUtilZZZ.containsTagName(sExpressionIn, KernelJsonIniSolverZZZ.sTAG_NAME, false)) {
					objEntry.isJson(true);
					this.getEntry().isJson(true);
				}
			}
			
			if(!this.isParserEnabledThis()) break main;								
			if(XmlUtilZZZ.containsTagName(sExpressionIn, this.getName(), false)){
				objEntry.isJsonMap(true);
				this.getEntry().isJsonMap(true);
			}
		
		}//end main:
	}

				
	//### aus ISolveUserZZZ
	@Override
	public void updateValueSolved(ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference, boolean bIsSolveCalled) throws ExceptionZZZ{
		super.updateValueSolved(objReturnReference, bIsSolveCalled);
				
		//Den "Elternsolver", siehe dazu auch TicketGOON20250308
		IKernelConfigSectionEntryZZZ objEntry = objReturnReference.get();
		objEntry.isJsonSolved(bIsSolveCalled);
		
		//Den eigenen Solver
		if(this.isSolverEnabledThis()) {
			objEntry.isJsonMapSolved(bIsSolveCalled);
		}
	}
	
	@Override
	public void updateValueSolveCalled(ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference, boolean bIsSolveCalled) throws ExceptionZZZ{
		super.updateValueSolveCalled(objReturnReference, bIsSolveCalled);
				
		//Den "Elternsolver", siehe dazu auch TicketGOON20250308
		IKernelConfigSectionEntryZZZ objEntry = objReturnReference.get();
		objEntry.isJsonSolveCalled(bIsSolveCalled);
		
		//Den eigenen Solver
		objEntry.isJsonMapSolveCalled(bIsSolveCalled);
	}
	
	@Override
	public void updateValueSolvedChanged(ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference, boolean bIsSolveCalled) throws ExceptionZZZ{
		super.updateValueSolvedChanged(objReturnReference, bIsSolveCalled);
				
		//Den "Elternsolver", siehe dazu auch TicketGOON20250308
		IKernelConfigSectionEntryZZZ objEntry = objReturnReference.get();
		objEntry.isJsonSolvedChanged(bIsSolveCalled);
		
		//Den eigenen Solver
		objEntry.isJsonMapSolvedChanged(bIsSolveCalled);
	}
	
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	//### aus ISolveEnabledZZZ
	//Merke: Mit folgender Methode wird über das konkrete Flag dieser Solver ein-/ausgeschaltet. 
	//       Z.B. wichtig für das Entfernen der geparsten Tags, oder halt dieses Entfernen weglassen.
	//Merke: Folgende Methoden muessen im konkreten Solver implementiert werden, da der abstrakte Solver die konkreten Flags zur deaktivierung diese konkreten Solvers nicht kennt.
	@Override
	public boolean isSolverEnabledThis() throws ExceptionZZZ {
		boolean bReturn = false;
		main:{  
		  bReturn = this.getFlag(IKernelJsonMapIniSolverZZZ.FLAGZ.USEJSON_MAP);
		  
		}//end main:
		return bReturn;
	}
	
	@Override
	public boolean isSolverEnabledCustom() throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			bReturn = this.isSolverEnabledThis();
			if(!bReturn) break main;
			
			bReturn = this.getFlag(IKernelJsonIniSolverZZZ.FLAGZ.USEJSON);
			if(!bReturn) break main;
		
		}//end main:
		return bReturn;
	}
	
	
//	@Override
//	public boolean isSolverEnabledCustom() throws ExceptionZZZ {
//		boolean bReturn = false;
//		main:{
//			bReturn = this.isSolverEnabledThis();
//			if(!bReturn) break main;
//			
//			bReturn = this.getFlag(IKernelJsonIniSolverZZZ.FLAGZ.USEJSON);
//	if(!bReturn) break main;
//			
//		}//end main:
//		return bReturn;
//	}

	
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
		HashMap<String,String> hmReturn = null;		
		boolean bUseExpression = false; boolean bUseSolver = false; //boolean bUseSolverThis = false;
		
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
		this.updateValueSolveCalled(objReturnReference);
		sReturnLine = sExpressionIn;
		sReturnTag = sExpressionIn; //schlieslich ist das eine .solve ! PARSED ! Methode, also nicht   this.getValue();
		sReturnTagParsed = sReturnTag;
		sReturnTagSolved = sReturnTag;
		sReturn = sReturnLine;
		main:{
			if(StringZZZ.isEmptyTrimmed(sExpressionIn)) break main;
			
			bUseExpression = this.isExpressionEnabledGeneral();
			if(!bUseExpression) break main;
			
			String sExpression = sExpressionIn;
			
			bUseSolver = this.isSolverEnabledEveryRelevant(); //this.getFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER);
			if(!bUseSolver) break main;
						
			//##################################
			//### Besonderheiten dieses Solvers
			//###################################		
			
			//Berechnen der HashMap aus einem vermeintlichen JSON-Ausdruck
			String sExpressionUsed = sExpression;
			hmReturn = this.computeHashMapFromJson(sExpression);
			if(hmReturn!=null) {
				//sExpressionUsed = HashMapExtendedZZZ.computeDebugString(hmReturn);
				sExpressionUsed = hmReturn.toString();
			}
			
			sReturnTag = sExpressionUsed;
			sReturnTagSolved = sReturnTag;
			sReturnLine = sReturnTag;
			sReturn = sReturnLine;
			
			this.updateValueSolved();
			this.updateValueSolved(objReturnReference);
		}//end main:	
	
		//NUN DEN INNERHALB DER EXPRESSION BERECHUNG ERSTELLTEN WERT uebernehmen
		this.setValue(sReturnTag);	//Der Handler bekommt die ganze Zeile als Wert
		sReturn = sReturnLine;
		
		if(objEntry!=null) {		
			objEntry.setValue(sReturnLine);
			objEntry.setValueFromTag(sReturnTag);
			if(objReturnReferenceIn!=null)objReturnReferenceIn.set(objEntry);
			
			if(bUseExpression && bUseSolver) {
				if(sReturnTagSolved!=null) {				
					if(!sReturnTagSolved.equals(sReturnTagParsed)) {				
						this.updateValueSolvedChanged();
						this.updateValueSolvedChanged(objReturnReference);
					}
										
					if(hmReturn!=null) {						
						objEntry.isJsonMap(true);//Sollte das nicht im PARSE gesetzt werden?
						objEntry.setValue(hmReturn); //Merke: Das wird nicht im PARSE gesetzt, da wir ja es hier mit einem SOLVE Wert zu tun haben 
					}else{
						objEntry.isJsonMap(false);
					}
				}
				if(objEntry.isEncrypted()) objEntry.setValueDecrypted(sReturn);
			}
			this.adoptEntryValuesMissing(objEntry);			
		}
		return sReturn;				
	}

	
		
//	//### Aus IKernelEntryExpressionUserZZZ	
//	@Override
//	public Vector3ZZZ<String>parseFirstVector(String sExpression, ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReferenceIn, boolean bKeepSurroundingSeparatorsOnParse) throws ExceptionZZZ{		
//		return this.parseFirstVector_(sExpression, objReturnReferenceIn, bKeepSurroundingSeparatorsOnParse);
//	}
//	
//	private Vector3ZZZ<String>parseFirstVector_(String sExpressionIn, ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReferenceIn, boolean bKeepSurroundingSeparatorsOnParse) throws ExceptionZZZ{		
//		Vector3ZZZ<String> vecReturn = new Vector3ZZZ<String>();
//		String sReturn = sExpressionIn;
//		String sReturnTag = null; String sReturnLine;
//		
//		boolean bUseExpression=false;
//		
//		IKernelConfigSectionEntryZZZ objEntry = null;
//		ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReference = null;			
//		if(objReturnReferenceIn==null) {
//			objReturnReference =  new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();															
//		}else {
//			objReturnReference = objReturnReferenceIn;
//			objEntry = objReturnReference.get();
//		}
//		
//		if(objEntry==null) {
//			//ZWAR: Das Ziel ist es moeglichst viel Informationen aus dem entry "zu retten"      =  this.parseAsEntryNew(sExpression);  //nein, dann gehen alle Informationen verloren   objReturn = this.parseAsEntryNew(sExpression);
//			objEntry = new KernelConfigSectionEntryZZZ<T>();   //nicht den eigenen Tag uebergeben, das ist der Entry der ganzen Zeile!
//			objReturnReference.set(objEntry);
//		}
//		this.setRaw(sExpressionIn);
//		objEntry.setRaw(sExpressionIn);	
//		//20250312 objEntry.isParseCalled(true);
//		this.updateValueParseCalled();
//		this.updateValueParseCalled(objReturnReference);
//		sReturnLine = sExpressionIn;
//		vecReturn.set(0, sReturnLine);//nur bei in dieser Methode neu erstellten Vector.
//		
//		main:{			
//			if(StringZZZ.isEmpty(sExpressionIn)) break main;
//			
//			bUseExpression = this.isExpressionEnabledGeneral();
//			if(!bUseExpression) break main;
//						
//			if(XmlUtilZZZ.containsTagName(sExpressionIn, KernelJsonIniSolverZZZ.sTAG_NAME, false)){
//				objEntry.isJson(true);
//			}
//			
//			//wg. dieser boolean Zuweisung als eigene Methode, die dann nur die Elternmethode aufruft.
//			if(XmlUtilZZZ.containsTagName(sExpressionIn, this.getName(), false)){
//				objEntry.isJsonMap(true);
//			}
//						
//			//Mehrere Z-Ausdruecke. Dann muss der jeweilige "Rest-Bestandteil" des ExpressionFirst-Vectors weiter zerlegt werden.
//			//Im Aufruf der Eltern-Methode findet ggfs. auch eine Aufloesung von Pfaden und eine Ersetzung von Variablen statt.
//			ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReferenceParse = new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
//			objReturnReferenceParse.set(objEntry);
//			vecReturn = super.parseFirstVector(sExpressionIn, objReturnReferenceParse, bKeepSurroundingSeparatorsOnParse);
//			objEntry = objReturnReferenceParse.get();
//			if(vecReturn==null) break main;
//			if(StringZZZ.isEmpty(vecReturn.get(1).toString())) break main; //Dann ist der Tag nicht enthalten und es darf(!) nicht weitergearbeitet werden.
//			
//			sReturnTag = vecReturn.get(1).toString();			
//			sReturnLine = VectorUtilZZZ.implode(vecReturn);
//		}//end main:
//		
//		//NUN DEN INNERHALB DER EXPRESSION BERECHUNG ERSTELLTEN WERT uebernehmen
//		if(vecReturn!=null) vecReturn.replace(sReturnTag);
//		this.setValue(sReturnTag);
//		sReturn = sReturnLine;
//		
//		if(objEntry!=null) {
//			objEntry.setValue(sReturnLine);
//			objEntry.setValueFromTag(sReturnTag);
//			if(objReturnReferenceIn!=null)objReturnReferenceIn.set(objEntry);//Wichtig: Reference nach aussen zurueckgeben.
//			if(bUseExpression) {
//				this.adoptEntryValuesMissing(objEntry);
//			}
//		}				
//		return vecReturn;
//	}

	
	
	//+++++++++++++++++++++++++++++++++++++++++++++
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
					sTagStart = XmlUtilZZZ.computeTagPartOpening(KernelJsonIniSolverZZZ.sTAG_NAME);
					sTagEnd = XmlUtilZZZ.computeTagPartClosing(KernelJsonIniSolverZZZ.sTAG_NAME);					
					KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(vecExpressionIn, sTagStart,sTagEnd, true, false); //also AN JDEDER POSITION (d.h. nicht nur am Anfang) von aussen nach innen!!!
				}
			}	
						
			//##############
			sReturnTag = VectorUtilZZZ.getElementAsValueOf(vecReturn, 1);//Damit wird aus dem NullObjectZZZ ggfs. NULL als Wert geholt.
			sReturnLine = VectorUtilZZZ.implode(vecReturn);
			
			this.updateValueSolved();
			this.updateValueSolved(objReturnReference);
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
			if(sExpressionIn!=null) {												
				if(!sExpressionIn.equals(sReturn)) {
					this.updateValueSolvedChanged();
					this.updateValueSolvedChanged(objReturnReference); //zur Not nur, weil die Z-Tags entfernt wurden.									
				}
			}			
			if(objReturnReferenceIn!=null) objReturnReferenceIn.set(objEntry);
		}
		return vecReturn;
	}		

	
	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++		
	//### aus IConvertableZZZ
	@Override
	public boolean isConvertRelevant(String sToProof) throws ExceptionZZZ {
		return false;
	}
	
	//##############################################
	//### FLAG HANDLING
				
	//Aus IKernelJsonMapIniSolverZZZ
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
	
	//Aus IKernelJsonIniSolverZZZ
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
}//End class
