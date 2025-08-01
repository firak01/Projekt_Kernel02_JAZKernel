package basic.zKernel.file.ini;

import java.util.ArrayList;
import java.util.HashMap;
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
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.datatype.xml.XmlUtilZZZ;
import basic.zKernel.IKernelConfigSectionEntryZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernel.KernelConfigSectionEntryZZZ;
import basic.zKernel.config.KernelConfigSectionEntryUtilZZZ;
import basic.zKernel.flag.IFlagZEnabledZZZ;
import basic.zKernel.flag.util.FlagZFassadeZZZ;
import custom.zKernel.file.ini.FileIniZZZ;

/**Diese Klasse verarbeitet ggf. Ausdruecke/Formeln in Ini-Dateien.
 *  Es kann dann in einem dieser Formeln z.B. auf den Property-Wert einer anderen Sektion zugegriffen werden. So entstehen 'dynamische' ini-Dateien.
 * @author lindhaueradmin
 *
 */
public class KernelJsonIniSolverZZZ<T> extends AbstractKernelIniSolverZZZ<T> implements IKernelJsonIniSolverZZZ, IKernelJsonMapIniSolverZZZ, IKernelJsonArrayIniSolverZZZ{
	private static final long serialVersionUID = 6588176234400554782L;
	public static String sTAG_NAME = "JSON";
	public static String sTAG_PARENT_NAME = null;

	public KernelJsonIniSolverZZZ() throws ExceptionZZZ{
		super("init");
		KernelJsonIniSolverNew_(null, null);
	}
	
	public KernelJsonIniSolverZZZ(String sFlag) throws ExceptionZZZ{
		super(sFlag);
		KernelJsonIniSolverNew_(null, null);
	}
	
	public KernelJsonIniSolverZZZ(String[] saFlag) throws ExceptionZZZ{
		super(saFlag);
		KernelJsonIniSolverNew_(null, null);
	}
	
	public KernelJsonIniSolverZZZ(IKernelZZZ objKernel) throws ExceptionZZZ{
		super(objKernel);
		KernelJsonIniSolverNew_(null, null);
	}
	
	public KernelJsonIniSolverZZZ(IKernelZZZ objKernel, String[] saFlag) throws ExceptionZZZ{
		super(objKernel, saFlag);
		KernelJsonIniSolverNew_(null, null);
	}
	
	public KernelJsonIniSolverZZZ(FileIniZZZ<T> objFileIni) throws ExceptionZZZ{
		super(objFileIni);//als IKernelUserZZZ - Object
		KernelJsonIniSolverNew_(objFileIni, null);
	}
	
	public KernelJsonIniSolverZZZ(FileIniZZZ<T> objFileIni, String[] saFlag) throws ExceptionZZZ{
		super(objFileIni, saFlag);//als IKernelUserZZZ - Object
		KernelJsonIniSolverNew_(objFileIni, null);
	}
		
	public KernelJsonIniSolverZZZ(IKernelZZZ objKernel, FileIniZZZ<T> objFileIni, String[] saFlag) throws ExceptionZZZ{
		super(objKernel, saFlag);
		KernelJsonIniSolverNew_(objFileIni, null);
	}
	
	public KernelJsonIniSolverZZZ(FileIniZZZ<T> objFileIni, HashMapCaseInsensitiveZZZ<String,String> hmVariable, String[] saFlag) throws ExceptionZZZ{
		super(objFileIni, saFlag); //als IKernelUserZZZ - Object
		KernelJsonIniSolverNew_(objFileIni, hmVariable);
	}
	
	public KernelJsonIniSolverZZZ(IKernelZZZ objKernel, FileIniZZZ<T> objFileIni, HashMapCaseInsensitiveZZZ<String,String> hmVariable, String[] saFlag) throws ExceptionZZZ{
		super(objKernel, saFlag);
		KernelJsonIniSolverNew_(objFileIni, hmVariable);
	}
			
	private boolean KernelJsonIniSolverNew_(FileIniZZZ objFileIn, HashMapCaseInsensitiveZZZ<String,String> hmVariableIn) throws ExceptionZZZ {
	 boolean bReturn = false;
	 String stemp; boolean btemp; 
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
	public HashMap<String,String> computeHashMap(String sExpression) throws ExceptionZZZ{
		HashMap<String,String>hmReturn=new HashMap<String,String>();				
		main:{
			if(StringZZZ.isEmpty(sExpression)) break main;
			if(this.getFlag(IKernelJsonIniSolverZZZ.FLAGZ.USEJSON)==false) break main; 			
			if(this.getFlag(IKernelJsonMapIniSolverZZZ.FLAGZ.USEJSON_MAP.name())==true){				
		
			//Dann erzeuge neues KernelJsonMapSolverZZZ - Objekt.			
			FileIniZZZ objFileIni = this.getFileConfigKernelIni();
				
			KernelJsonMapIniSolverZZZ objJsonMapSolver = new KernelJsonMapIniSolverZZZ(objFileIni);
			String[] saFlag = FlagZFassadeZZZ.seekFlagZrelevantForObject(this, objJsonMapSolver, true);
			objJsonMapSolver.setFlag(saFlag,true);
			hmReturn=objJsonMapSolver.computeHashMapFromJson(sExpression);			
			}				
		}//end main:
		this.setValue(hmReturn);
		return hmReturn;
	}
	
	public ArrayList<String> computeArrayList(String sLineWithExpression) throws ExceptionZZZ{
		ArrayList<String>alsReturn=new ArrayList<String>();				
		main:{
			if(StringZZZ.isEmpty(sLineWithExpression)) break main;
			if(!this.getFlag(IKernelJsonIniSolverZZZ.FLAGZ.USEJSON)) break main; 			
			if(!this.getFlag(IKernelJsonArrayIniSolverZZZ.FLAGZ.USEJSON_ARRAY)) break main;				
		
			//Dann erzeuge neues KernelJsonArraySolverZZZ - Objekt.		
			FileIniZZZ objFileIni = this.getFileConfigKernelIni();
			
			KernelJsonArrayIniSolverZZZ objJsonArraySolver = new KernelJsonArrayIniSolverZZZ(objFileIni);
			String[] saFlag = FlagZFassadeZZZ.seekFlagZrelevantForObject(this, objJsonArraySolver, true);
			objJsonArraySolver.setFlag(saFlag,true);
			alsReturn=objJsonArraySolver.computeArrayList(sLineWithExpression);															
							
		}//end main:
		this.setValue(alsReturn);
		return alsReturn;
	}
	

	//######### Interfaces #######################################################
	
	//### aus ITagBasicZZZ
	@Override
	public String getNameDefault(){
		return KernelJsonIniSolverZZZ.sTAG_NAME;
	}
	
	//### Aus ITagBasicChildZZZ
	@Override
	public String getParentNameDefault() throws ExceptionZZZ {
		return KernelJsonIniSolverZZZ.sTAG_PARENT_NAME;
	}
	
	//### aus IParseEnabled		
//	@Override 
//	public boolean isParserEnabledThis() throws ExceptionZZZ {
//		return true; //Somit ist das Parsen vom Solven entkoppelt. Das wäre default in der abstracten Elternklasse, s. Solver:  return this.isSolverEnabledThis();
//	}
	
	@Override
	public boolean isParserEnabledCustom() throws ExceptionZZZ {
		//Ziel ist, dass Solver, die Kinder-Tags haben auch deren Flags abrufen koennen.
		boolean bReturn = false;
		main:{
			boolean bEnabledThis = this.isParserEnabledThis();
			//boolean bEnabledJsonArray = this.getFlag(IKernelJsonArrayIniSolverZZZ.FLAGZ.USEJSON_ARRAY);
			//boolean bEnabledJsonMap = this.getFlag(IKernelJsonMapIniSolverZZZ.FLAGZ.USEJSON_MAP);
					
			bReturn = bEnabledThis;//| bEnabledJsonArray | bEnabledJsonMap;
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
		//Flags entscheiden, ob es weiter geht
		super.updateValueParseCustom(objReturnReference, sExpression); //isExpression setzen
				
		if(!this.isExpressionEnabledGeneral()) break main;
		if(!this.isParserEnabledGeneral()) break main;
		if(!this.isParserEnabledCustom()) break main;
		
		if(XmlUtilZZZ.containsTagName(sExpressionIn, this.getName(), false)){
			objEntry.isJson(true);
			this.getEntry().isJson(true);
		}
				
		//########################
		//Nun, ggfs. wird .solve() nicht aufgerufen, in dem alle Tags richtig geparsed werden
		//weil sie ihrerseits mit .solve() ausgeführt werden.
			
		//DARUM:
		//Hier die moeglichen enthaltenden Tags alle Pruefen..., siehe auch KernelExpressionIniHandlerZZZ
		//Das kann man auch 
		//a) durch die Flags steuern
		//b) durch direkten Aufruf der "Untersolver".custom-Methode
		//c) durch Analyse der Tags
	
//		//a)
//		boolean bUseJsonArray = this.getFlag(IKernelJsonArrayIniSolverZZZ.FLAGZ.USEJSON_ARRAY);
//		boolean bUseJsonMap = this.getFlag(IKernelJsonMapIniSolverZZZ.FLAGZ.USEJSON_MAP);
//		if(!(bUseJsonMap | bUseJsonArray )) break main;
//							
		
		String[] saFlagZpassed=null;
//		if(bUseJsonArray) {				
		KernelJsonArrayIniSolverZZZ<T> jsonArraySolverDummy = new KernelJsonArrayIniSolverZZZ<T>();
		saFlagZpassed = FlagZFassadeZZZ.seekFlagZrelevantForObject(this, jsonArraySolverDummy, true);
			
		KernelJsonArrayIniSolverZZZ<T> objJsonArraySolver = new KernelJsonArrayIniSolverZZZ<T>(this.getKernelObject(), this.getFileConfigKernelIni(), saFlagZpassed);
		objJsonArraySolver.updateValueParseCustom(objReturnReference, sExpression);
//		}
		
							
//		if(bUseJsonMap){
		KernelJsonMapIniSolverZZZ<T> jsonMapSolverDummy = new KernelJsonMapIniSolverZZZ<T>();
		saFlagZpassed = FlagZFassadeZZZ.seekFlagZrelevantForObject(this, jsonMapSolverDummy, true);
		
		KernelJsonMapIniSolverZZZ<T> objJsonMapSolver = new KernelJsonMapIniSolverZZZ(this.getKernelObject(), this.getFileConfigKernelIni(), saFlagZpassed);
		objJsonMapSolver.updateValueParseCustom(objReturnReference, sExpression);
//		}
	
		
//		//c)
//		//TODOGOON20250308; //TICKETGOON20250308;; //Analog zu dem PARENT - Tagnamen muesste es auch eine Loesung für die CHILD - Tagnamen geben
//		if(XmlUtilZZZ.containsTagName(sExpressionIn, KernelJavaCallIniSolverZZZ.sTAG_NAME, false)) {
//			objEntry.isJavaCall(true);
//			this.getEntry().isJavaCall(true);
//		}

		
		
		
		//##########################.	
		
		
	
		
	
		
		//################################################################################################
		//Alternativ kann man hier verkürzt alle möglichen Tags aufnehmen...
		
		//DARUM:
		//Hier die moeglichen enthaltenden Tags alle Pruefen..., siehe z.B. auch KernelCallIniSolverZZZ			
		//TODOGOON20250308; //TICKETGOON20250308;; //Analog zu dem PARENT - Tagnamen muesste es auch eine Loesung für die CHILD - Tagnamen geben
		
		//#####################################################################
//		objEntry = objReturnReference.get();			
//		if(XmlUtilZZZ.containsTagName(sExpressionIn, KernelCallIniSolverZZZ.sTAG_NAME, false)) {
//			objEntry.isCall(true);
//			this.getEntry().isCall(true);
//		}
//		
//		if(XmlUtilZZZ.containsTagName(sExpressionIn, KernelJavaCallIniSolverZZZ.sTAG_NAME, false)) {
//			objEntry.isJavaCall(true);
//			this.getEntry().isJavaCall(true);
//		}
		
		//#####################################################################
		
		
	
	}//end main
	}

	//### aus IParseEnabled		
//	//Analog zu KernelJsonMapIniSolverZZZ, KernelZFormulaMathSolver, KernelEncrytptionIniSolver aufbauen...				
//	@Override
//	public Vector3ZZZ<String> parseFirstVector(String sLineWithExpression) throws ExceptionZZZ {		
//		return this.parseFirstVector_(sLineWithExpression, null, true);
//	}
//	
//	@Override
//	public Vector3ZZZ<String> parseFirstVector(String sLineWithExpression, boolean bKeepSurroundingSeparatorsOnParse) throws ExceptionZZZ {		
//		return this.parseFirstVector_(sLineWithExpression, null, bKeepSurroundingSeparatorsOnParse);
//	}
//		
	//### Aus IKernelEntryExpressionUserZZZ	
//	@Override
//	public Vector3ZZZ<String>parseFirstVector(String sExpression, ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReferenceIn, boolean bKeepSurroundingSeparatorsOnParse) throws ExceptionZZZ{		
//		return this.parseFirstVector_(sExpression, objReturnReferenceIn, bKeepSurroundingSeparatorsOnParse);
//	}
//	
//	private Vector3ZZZ<String>parseFirstVector_(String sExpressionIn, ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReferenceIn, boolean bKeepSurroundingSeparatorsOnParse) throws ExceptionZZZ{		
//		Vector3ZZZ<String> vecReturn = new Vector3ZZZ<String>();
//		String sReturn = sExpressionIn;
//		String sReturnTag = null; String sReturnLine = null;
//		boolean bUseExpression=false;
//		
//		String sTagStartZ = "<Z>";
//		String sTagEndZ = "</Z>";
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
//		
//		this.setRaw(sExpressionIn);
//		objEntry.setRaw(sExpressionIn);	
//		this.updateValueParseCalled();
//		this.updateValueParseCalled(objReturnReference);
//		sReturnLine = sExpressionIn;
//		vecReturn.set(0, sReturnLine);//nur bei in dieser Methode neu erstellten Vector.
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
//			if(XmlUtilZZZ.containsTagName(sExpressionIn, KernelJsonArrayIniSolverZZZ.sTAG_NAME, false)){
//				objEntry.isJsonArray(true);
//			}
//			
//			if(XmlUtilZZZ.containsTagName(sExpressionIn, KernelJsonMapIniSolverZZZ.sTAG_NAME, false)){
//				objEntry.isJsonMap(true);
//			}
//						
//			//Mehrere Z-Ausdruecke. Dann muss der jeweilige "Rest-Bestandteil" des ExpressionFirst-Vectors weiter zerlegt werden.
//			//Im Aufruf der Eltern-Methode findet ggfs. auch eine Aufloesung von Pfaden und eine Ersetzung von Variablen statt.
//			ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReferenceParse = new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
//			objReturnReferenceParse.set(objEntry);
//			vecReturn = super.parseFirstVector(sExpressionIn, objReturnReferenceParse, bKeepSurroundingSeparatorsOnParse);
//			objEntry = objReturnReferenceParse.get();
//			if(vecReturn!=null) break main;
//			if(StringZZZ.isEmpty(vecReturn.get(1).toString())) break main; //Dann ist der Tag nicht enthalten und es darf(!) nicht weitergearbeitet werden.
//			
//			sReturnTag = vecReturn.get(1).toString();			
//			sReturnLine = VectorUtilZZZ.implode(vecReturn);
//		}//end main:
//		
//		//NUN DEN INNERHALB DER EXPRESSION BERECHUNG ERSTELLTEN WERT uebernehmen
//		vecReturn.replace(sReturnTag);
//		this.setValue(sReturnTag);
//		sReturn = sReturnLine;				
//		
//		if(objEntry!=null) {
//			objEntry.setValue(sReturnLine);
//			objEntry.setValueFromTag(sReturnTag);
//			if(objReturnReference!=null)objReturnReference.set(objEntry);//Wichtig: Reference nach aussen zurueckgeben.
//			if(bUseExpression) {	
//				if(objEntry.isEncrypted()) objEntry.setValueEncrypted(sReturnLine);
//				if(sExpressionIn!=null) {														
//					String sExpression2Compare = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionIn, sTagStartZ, sTagEndZ, true, false); //also an jeder Position (d.h. nicht nur am Anfang) ,also von aussen nach innen!!!
//					String sReturnLine2Compare = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sReturnLine, sTagStartZ, sTagEndZ, true, false); //also an jeder Position (d.h. nicht nur am Anfang) ,also von aussen nach innen!!!
//					if(!sExpression2Compare.equals(sReturnLine2Compare)) {
//						this.updateValueParsedChanged();
//						this.updateValueParsedChanged(objReturnReference);
//					}
//				}							
//				this.adoptEntryValuesMissing(objEntry);
//			}
//		}				
//		return vecReturn;
//	}

	
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	//### aus ISolveEnabledZZZ
	//Merke: Mit folgender Methode wird über das konkrete Flag dieser Solver ein-/ausgeschaltet. 
	//       Z.B. wichtig für das Entfernen der geparsten Tags, oder halt dieses Entfernen weglassen.
	//Merke: Folgende Methoden muessen im konkreten Solver implementiert werden, da der abstrakte Solver die konkreten Flags zur deaktivierung diese konkreten Solvers nicht kennt.
	@Override
	public boolean isSolverEnabledThis() throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
		  bReturn = this.getFlag(IKernelJsonIniSolverZZZ.FLAGZ.USEJSON);
		  if(!bReturn) break main;
	  
		}//end main:
		return bReturn;
	}
	
	@Override
	public boolean isSolverEnabledCustom() throws ExceptionZZZ {
		return true;
	}

	
	
	/**Methode ueberschreibt die Aufloesung von Pfaden und Ini-Variablen.
	 * @param sLine
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
		String sExpression = sExpressionIn;
		String sReturn = sExpression;
		String sReturnTag = null;
		ArrayList<String> alsReturn = null;
		HashMap<String,String> hmReturn = null;
		
		boolean bUseExpression = false; 
		boolean bUseSolver = false;
		boolean bUseJsonArray = false; boolean bUseJsonMap = false;
		
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
		objEntry.isSolveCalled(true);
		
		main:{
			if(StringZZZ.isEmptyTrimmed(sExpressionIn)) break main;
			
			bUseExpression = this.isExpressionEnabledGeneral();
			if(!bUseExpression) break main;
						
			bUseSolver = this.isSolverEnabledEveryRelevant(); //this.getFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER);
			if(!bUseSolver) break main;
						
			bUseJsonArray = this.getFlag(IKernelJsonArrayIniSolverZZZ.FLAGZ.USEJSON_ARRAY);
			if(bUseJsonArray) {
			
				alsReturn = this.computeArrayList(sExpression);
				if(alsReturn!=null) {
					sReturnTag = alsReturn.toString(); //ArrayListExtendedZZZ.computeDebugString(alsReturn);
					sReturn = sReturnTag;
				}	
			}
			
			bUseJsonMap = this.getFlag(IKernelJsonMapIniSolverZZZ.FLAGZ.USEJSON_MAP);
			if(bUseJsonMap) {
			
				hmReturn = this.computeHashMap(sExpression);
				if(!hmReturn.isEmpty()) {
					sReturnTag = hmReturn.toString();
					sReturn = sReturnTag;
				}	
			}
			
		}//end main:	
		
		//NUN DEN INNERHALB DER EXPRESSION BERECHUNG ERSTELLTEN WERT uebernehmen
		if(sReturnTag!=null) this.setValue(sReturnTag);	//Der Handler bekommt die ganze Zeile als Wert	
		if(objEntry!=null) {		
			objEntry.setValue(sReturn);
			if(alsReturn!=null) {
				objEntry.isArrayValue(true);
				objEntry.isJsonArray(true);
				objEntry.setValue(alsReturn);
			}
			if(hmReturn!=null) {
				objEntry.setValue(hmReturn);
				objEntry.isJsonMap(true);
				objEntry.setValue(hmReturn);
			}
							
			if(objReturnReferenceIn!=null)objReturnReferenceIn.set(objEntry);//Wichtig: Reference nach aussen zurueckgeben.
			this.adoptEntryValuesMissing(objEntry);			
		}
		return sReturn;				
	}

	

	
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		
	//### aus IConvertableZZZ
	@Override
	public boolean isConvertRelevant(String sToProof) throws ExceptionZZZ {			
		return false;
	}
	
	//####################################
	//### FLAG Handling
	
	
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
	public boolean proofFlagExists(IKernelExpressionIniSolverZZZ.FLAGZ objaEnumFlag) throws ExceptionZZZ {
		return this.proofFlagExists(objaEnumFlag.name());
	}
	
	@Override
	public boolean proofFlagSetBefore(IKernelExpressionIniSolverZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
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
	
	
	//Aus IKernelJsonArrayIniSolverZZZ
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

	
	
}//End class
