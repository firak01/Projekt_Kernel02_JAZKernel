package basic.zKernel.file.ini;

import java.util.Vector;

import basic.zBasic.ExceptionZZZ;
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
	
	
//	/** Berechne die INI-Werte. 
//	 * 	 
//	 *  STRATEGIE:
//	 *  1. Ini-Variablen und Pfade aufloesen (wird im solve(...) gemacht.
	
//   *  Hier also:	
//	 *  2. JavaCalls aufloesen
//	 *  3. JSON Werte
//	 *  4. Entschluesseln 			 
//	 *  
//	 *  IDEE: 
//	 *  Die Entschluesselung zuletzt. Damit kann z.B. theoretisch in den JSON-Werten verschluesselter Inhalt enthalten sein.
//	 *  
//	 * @param sLineWithExpression
//	 * @param objReturnReference
//	 * @return
//	 * @throws ExceptionZZZ
//	 * @author Fritz Lindhauer, 06.05.2023, 07:41:02
//	 */
//	@Override
//	public String parse(String sExpression, ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReferenceIn) throws ExceptionZZZ{
//		return this.parse_(sExpression, objReturnReferenceIn, true);
//	}
//	
//	
//	@Override
//	public String parse(String sExpression, ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReferenceIn, boolean bRemoveSurroundingSeparators) throws ExceptionZZZ{
//		return this.parse_(sExpression, objReturnReferenceIn, bRemoveSurroundingSeparators);
//	}
//	
//	private String parse_(String sExpression, ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReferenceIn, boolean bRemoveSurroundingSeparators) throws ExceptionZZZ{
//		
//		//TODOGOON20240915: Das kommt nach solveFirstVector(), groesstenteils
//		//      Der Rest kommt nach parseFirstVector().
//		//      parseFirstVector() ruft also solveFirstVector() auf 
//		//      und macht dann mit parse() erst das implode um die Werte links und rechts zusammenzufassen.
//		//
//		//      Problem: Rueckgabewert int irgendwie bilden aus den gesetzten is... Werten.
//		//
//		//      Merke:
//		//      solve() wird nur den vec.getIndex(1) zurueckliefern.
//		
//		Vector3ZZZ<String> vecReturn = null;
//		IKernelConfigSectionEntryZZZ objEntry = null;
//		String sReturn = sExpression;						
//		main:{
//			if(StringZZZ.isEmpty(sExpression)) break main;
//			if(!this.getFlag(IIniTagWithExpressionZZZ.FLAGZ.USEEXPRESSION)) break main;
//						
//			ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReference = null;			
//			if(objReturnReferenceIn==null) {
//				objReturnReference =  new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();								
//				objEntry = new KernelConfigSectionEntryZZZ<T>(this); //this.getEntryNew(); es gingen alle Informationen verloren				
//				                                                     //nein, dann gehen alle Informationen verloren   objReturn = this.parseAsEntryNew(sExpression);				
//			}else {
//				objReturnReference = objReturnReferenceIn;
//				objEntry = objReturnReference.get();
//			}
//			
//			if(objEntry==null) {
//				//Das Ziel ist es moeglichst viel Informationen aus dem entry "zu retten"
//				//Achtung: Das objReturn Objekt NICHT generell versuchen ueber .getEntry() und dann ggfs. .getEntryNew() zu uebernehmen. Es verfaelscht bei einem 2. Suchaufruf das Ergebnis.
//				//objEntry = this.getEntry();
//				objEntry = new KernelConfigSectionEntryZZZ<T>(this); // =  this.parseAsEntryNew(sExpression);  //nein, dann gehen alle Informationen verloren   objReturn = this.parseAsEntryNew(sExpression);
//				objReturnReference.set(objEntry);
//			}	
//			objEntry.setRaw(sExpression);
//						
//
//			boolean bAnyEncryption = false;		boolean bAnyCall = false;	boolean bAnyFormula = false; boolean bAnyJson = false;
//			//TODOGOON20240810; //jetzt sollte eigentlich hier super.parse()
//								//und diese Anpassung in .parseFirstVector() mit Referenz IKernelConfigSectionEntryZZZ passieren.
//						        //und darin dann das machen...
//			
//			//Aber <z:Null/> und <z:Empty/> muessen auch behandelt werden durch die Expression verarbeitung
//			boolean bIsConversion = this.isConvertRelevant(sExpression); 
//			if(bIsConversion) {
//				objEntry.isConversion(true);
//			}
//			
//			//Behandle die konkreten Tags dieses Ausdrucks <Z>
//			boolean bIsExpression = this.isExpression(sExpression);
//			if(bIsExpression){
//				objEntry.isExpression(true);	
//			}
//			if(! (bIsExpression  | bIsConversion)) break main;						
//			
//			
//			String sExpressionUsed = sExpression;
//			solver:{			
//			boolean bUseExpressionSolver = this.getFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER);
//			boolean bUseFormula = this.getFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA);
//			boolean bUseCall = this.getFlag(IKernelCallIniSolverZZZ.FLAGZ.USECALL);
//			boolean bUseJson = this.getFlag(IKernelJsonIniSolverZZZ.FLAGZ.USEJSON);
//			boolean bUseEncryption = this.getFlag(IKernelEncryptionIniSolverZZZ.FLAGZ.USEENCRYPTION);
//			if(!(bUseExpressionSolver | bUseFormula | bUseCall | bUseJson | bUseEncryption )) break solver;
//			
//			//Darin dann weiterrechnen...										
//			if(bUseFormula | bUseExpressionSolver) {
//			
//				//Hier KernelZFormulIniSolverZZZ
//				//Merke: Die statischen Methoden leisten mehr als nur die ...Solver....
//				//       Durch den int Rückgabwert sorgen sie nämlich für die korrekte Befüllung von 
//				//       objReturn, also auch der darin verwendeten Flags bIsJson, bIsJsonMap, etc.
//				KernelZFormulaIniSolverZZZ<T> formulaSolverDummy = new KernelZFormulaIniSolverZZZ<T>();
//				String[] saFlagZpassed = FlagZFassadeZZZ.seekFlagZrelevantForObject(this, formulaSolverDummy, true);
//				HashMapCaseInsensitiveZZZ<String,String>hmVariable = this.getHashMapVariable();
//				
//				//Hier werden Variablen aufgeloest und anschliessend auch Ini-Pfade
//				//Aber: Solve entfernt die Tags drumherum, darum parse()... Es muss ja anschliessend weiterverarbeitet werden.
//				KernelZFormulaIniSolverZZZ<T> objFormulaSolver = new KernelZFormulaIniSolverZZZ<T>(this.getKernelObject(), this.getFileConfigKernelIni(), hmVariable, saFlagZpassed);
//				ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReferenceParse = new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
//				objReturnReferenceParse.set(objEntry);
//				sReturn = objFormulaSolver.parse(sExpressionUsed, objReturnReferenceParse, false);//!!! Extrahiere das innere des Tags, lasse umgebende Tags drin.
//			
//				objEntry = objReturnReferenceParse.get();
//				if(objEntry!=null) sExpressionUsed = objEntry.getValue(); 
//			}//end bUseFormula
//			
//								
//			if(bUseCall){
//				KernelCallIniSolverZZZ<T> callDummy = new KernelCallIniSolverZZZ<T>();
//				String[] saFlagZpassed = FlagZFassadeZZZ.seekFlagZrelevantForObject(this, callDummy, true);
//				
//				
//				
//				//Merke: objReturnReference ist ein Hilfsobjekt, mit dem CallByReference hinsichtlich der Werte realisiert wird.				
//				ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReferenceCall = new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
//				objReturnReferenceCall.set(objEntry);
//				boolean bForFurtherProcessing = false; 				
//				bAnyCall = KernelConfigSectionEntryUtilZZZ.getValueCallSolved(this.getFileConfigKernelIni(), sExpressionUsed, bUseCall, bForFurtherProcessing, saFlagZpassed, objReturnReferenceCall);
//				if(bAnyCall) {
//					objEntry = objReturnReferenceCall.get();				
//					if(objEntry!=null) {
//						objEntry.isCall(true);															
//						sExpressionUsed = objEntry.getValue(); 
//					}//Merke: Keinen Else-Zweig. Vielleicht war in einem vorherigen Schritt ja durchaus ein Call enthalten
//				}
//			}
//										
//			
//			if(bUseJson) {
//				int iReturnJson=0;
//				
//				KernelJsonIniSolverZZZ<T> exDummy03 = new KernelJsonIniSolverZZZ<T>();
//				String[] saFlagZpassed = FlagZFassadeZZZ.seekFlagZrelevantForObject(this, exDummy03, true); //this.getFlagZ_passable(true, exDummy);					
//				
//				//Merke: objReturnValue ist ein Hilfsobjekt, mit dem CallByReference hinsichtlich der Werte realisiert wird.
//				ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReferenceJson = new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
//				objReturnReferenceJson.set(objEntry);
//				
//				ReferenceArrayZZZ<String>objalsReturnValueJsonSolved=new ReferenceArrayZZZ<String>();
//				ReferenceHashMapZZZ<String,String>objhmReturnValueJsonSolved=new ReferenceHashMapZZZ<String,String>();														
//				sReturn = KernelConfigSectionEntryUtilZZZ.getValueJsonSolved(this.getFileConfigKernelIni(), sExpressionUsed, bUseJson, saFlagZpassed, objReturnReferenceJson, objalsReturnValueJsonSolved,objhmReturnValueJsonSolved);					
//				
//				//Nein, objEntry muss es schon vorher geben.... objEntry ist nicht Bestandteil von objalsReturnValueJsonSolved.get();
//				if(objEntry!=null) {
//					if(objEntry.isJson()){
//						this.getEntry().isExpression(true);
//						this.getEntry().isJson(true);
//						if(objEntry.isJsonArray()) {
//							this.getEntry().isJsonArray(true);
//							this.setValue(ArrayListExtendedZZZ.debugString(objalsReturnValueJsonSolved.getArrayList()));
//							this.setValue(objalsReturnValueJsonSolved.getArrayList());
//						}
//						
//						if(objEntry.isJsonMap()){
//							this.getEntry().isJsonMap(true);
//							this.setValue(HashMapExtendedZZZ.computeDebugString(objhmReturnValueJsonSolved.get()));
//							this.setValue(objhmReturnValueJsonSolved.get());
//						}
//					}
//				}//Merke: Keinen Else-Zweig. Vielleicht war in einem vorherigen Schritt ja durchaus Json enthalten						
//			}									
//								
//			if(bUseEncryption) {
//				KernelEncryptionIniSolverZZZ<T> encryptionDummy = new KernelEncryptionIniSolverZZZ<T>();
//				String[] saFlagZpassed = FlagZFassadeZZZ.seekFlagZrelevantForObject(this, encryptionDummy, true);
//				
//				//Merke: objReturnReference ist ein Hilfsobjekt, mit dem CallByReference hinsichtlich der Werte realisiert wird.
//				boolean bForFurtherProcessing = false;
//				ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReferenceEncryption = new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
//				objReturnReferenceEncryption.set(objEntry);
//				bAnyEncryption = KernelConfigSectionEntryUtilZZZ.getValueEncryptionSolved(this.getFileConfigKernelIni(), sExpressionUsed, bUseEncryption, bForFurtherProcessing, saFlagZpassed, objReturnReferenceEncryption);
//				if(bAnyEncryption) {
//					objEntry = objReturnReferenceEncryption.get();
//						if(objEntry!=null) {
//						objEntry.isRawEncrypted(true);
//						String sLineDecrypted = objEntry.getRawDecrypted();//Wert zur weiteren Verarbeitung weitergeben						
//						if(sExpressionUsed.equals(sLineDecrypted)) {						
//							objEntry.isDecrypted(false);
//						}else {
//							objEntry.isDecrypted(true);
//							objEntry.setRawDecrypted(sLineDecrypted);
//							objEntry.setValue(sLineDecrypted);       //quasi erst mal den Zwischenstand festhalten.							
//						}
//						sExpressionUsed = sLineDecrypted; //Zur Verarbeitung weitergeben
//					}							
//				}//Merke: Keinen Else-Zweig. Vielleicht war in einem vorherigen Schritt ja durchaus Encryption enthalten
//			}
//			
//			
//			} //end solver:
//			
//			sReturn = sExpressionUsed;
//			if(bRemoveSurroundingSeparators) {
//				String sTagStart = this.getTagStarting();
//				String sTagEnd = this.getTagClosing();				
//				String sValue = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sReturn, sTagStart, sTagEnd);			
//                sReturn = sValue;												
//			}
//		}//end main:		
//		
//		//NUN DEN INNERHALB DER EXPRESSION BERECHUNG ERSTELLTEN WERT uebernehmen
//		//this.setValue(sReturn);		
//		if(objEntry!=null) {		
//			objEntry.setValue(sReturn);
//			if(!sExpression.equals(sReturn)) {
//				objEntry.isParsed(true);
//			}
//			if(objReturnReferenceIn!=null)objReturnReferenceIn.set(objEntry);
//		}
//		return sReturn;
//	}
	
	
//	@Override
//	public Vector3ZZZ<String> parseFirstVector(String sExpression, ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReferenceIn, boolean bRemoveSurroundingSeparators) throws ExceptionZZZ{
//		
//		//TODO: Das kommt nach solveFirstVector(), groesstenteils
//		//      Der Rest kommt nach parseFirstVector().
//		//      parseFirstVector() ruft also solveFirstVector() auf 
//		//      und macht dann mit parse() erst das implode um die Werte links und rechts zusammenzufassen.
//		//
//		//      Merke:
//		//      solve() wird nur den vec.getIndex(1) zurueckliefern.
//		
//		Vector3ZZZ<String> vecReturn = null;
//		IKernelConfigSectionEntryZZZ objEntry = null;
//		String sReturn = sExpression;						
//		main:{
//			if(StringZZZ.isEmpty(sExpression)) break main;
//			if(!this.getFlag(IIniTagWithExpressionZZZ.FLAGZ.USEEXPRESSION)) break main;
//						
//			ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReference = null;			
//			if(objReturnReferenceIn==null) {
//				objReturnReference =  new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();								
//				objEntry = new KernelConfigSectionEntryZZZ<T>(this); //this.getEntryNew(); es gingen alle Informationen verloren				
//				                                                     //nein, dann gehen alle Informationen verloren   objReturn = this.parseAsEntryNew(sExpression);				
//			}else {
//				objReturnReference = objReturnReferenceIn;
//				objEntry = objReturnReference.get();
//			}
//			
//			if(objEntry==null) {
//				//Das Ziel ist es moeglichst viel Informationen aus dem entry "zu retten"
//				//Achtung: Das objReturn Objekt NICHT generell versuchen ueber .getEntry() und dann ggfs. .getEntryNew() zu uebernehmen. Es verfaelscht bei einem 2. Suchaufruf das Ergebnis.
//				//objEntry = this.getEntry();
//				objEntry = new KernelConfigSectionEntryZZZ<T>(this); // =  this.parseAsEntryNew(sExpression);  //nein, dann gehen alle Informationen verloren   objReturn = this.parseAsEntryNew(sExpression);
//				objReturnReference.set(objEntry);
//			}	
//			objEntry.setRaw(sExpression);
//			
//			
//			boolean bAnyEncryption = false;		boolean bAnyCall = false;	boolean bAnyFormula = false; boolean bAnyJson = false;
//			//TODOGOON20240810; //jetzt sollte eigentlich hier super.parse()
//								//und diese Anpassung in .parseFirstVector() mit Referenz IKernelConfigSectionEntryZZZ passieren.
//						        //und darin dann das machen...
//			
//			//Aber <z:Null/> und <z:Empty/> muessen auch behandelt werden durch die Expression verarbeitung
//			boolean bIsConversion = this.isConvertRelevant(sExpression); 
//			if(bIsConversion) {
//				objEntry.isConversion(true);
//			}
//			
//			//Behandle die konkreten Tags dieses Ausdrucks <Z>
//			boolean bIsExpression = this.isExpression(sExpression);
//			if(bIsExpression){
//				objEntry.isExpression(true);	
//			}
//			if(! (bIsExpression  | bIsConversion)) break main;						
//			
//			vecReturn = new Vector3ZZZ<String>();
//			sReturn = objEntry.getValue();
//			if(bRemoveSurroundingSeparators) {
//				String sTagStart = this.getTagStarting();
//				String sTagEnd = this.getTagClosing();				
//				String sValue = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sReturn, sTagStart, sTagEnd);			
//                sReturn = sValue;												
//			}
//			
////			if(bAnyEncryption) {				
////				iReturn = iReturn+10; //Falls irgendeine Verschlüsselung vorliegt den Wert um 10 erhöhen.
////			}
////			if(bAnyCall) {
////				iReturn = iReturn+100;
////			}
//		}//end main:
//
//		//NUN DEN INNERHALB DER EXPRESSION BERECHUNG ERSTELLTEN WERT in den Return-Vector übernehmen
//		vecReturn.replace(sReturn);
//				
//		if(!sExpression.equals(sReturn)) objEntry.isParsed(true);
//		
//		this.setValue(sReturn);
//		if(objEntry!=null) objEntry.setValue(VectorUtilZZZ.implode(vecReturn));
//		if(objReturnReferenceIn!=null) objReturnReferenceIn.set(objEntry);		
//		return vecReturn;		
//	}

	//### aus IParseEnabledZZZ
	@Override
	public Vector3ZZZ<String> parseFirstVector(String sExpression, boolean bRemoveSurroundingSeparators) throws ExceptionZZZ {
		//Muss ueberschrieben werden, damit die "einfache Tag" Methode nicht greift und wir mit der parse - Methode dieser konkreten Klasse arbeiten.
		return this.parseFirstVector_(sExpression, null, bRemoveSurroundingSeparators, true);
	}
	
	//### Aus IKernelEntryExpressionUserZZZ	
	@Override
	public Vector3ZZZ<String>parseFirstVector(String sExpression, ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReference) throws ExceptionZZZ{
		return this.parseFirstVector_(sExpression, objReturnReference, true, true);
	}

	@Override
	public Vector3ZZZ<String> parseFirstVector(String sExpression, ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReferenceIn, boolean bRemoveSurroundingSeparators) throws ExceptionZZZ {
		return this.parseFirstVector_(sExpression, objReturnReferenceIn, bRemoveSurroundingSeparators, true);
	}
	
	/**Methode ueberschreibt das parsen des cascaded Tags.
	 * Loest nun INI-Pfade und INI-Variablen auf, 
	 * ABER: macht kein vollständiges solve()!!!
	 **/
	private Vector3ZZZ<String> parseFirstVector_(String sExpressionIn, ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReferenceIn, boolean bRemoveSurroundingSeparators, boolean bIgnoreCase) throws ExceptionZZZ {
		Vector3ZZZ<String>vecReturn = new Vector3ZZZ<String>();
		String sReturn = sExpressionIn; //Darin können also auch Variablen, etc. sein
		String sExpressionUsed = sExpressionIn;
		
		main:{
			if(StringZZZ.isEmpty(sExpressionIn)) break main;
			
			boolean bUseExpression = this.getFlag(IIniTagWithExpressionZZZ.FLAGZ.USEEXPRESSION); 
			if(!bUseExpression) break main;
			
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
				
			//Aufloesen von Pfaden und ini-Variablen VOR den anderen SOLVERN, darum schon beim Parsen.
			ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReferenceSolverSuper= new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
			objReturnReferenceSolverSuper.set(objEntry);
			sReturn = super.solveParsed(sExpressionUsed, objReturnReferenceSolverSuper, bRemoveSurroundingSeparators);
			objEntry = objReturnReferenceSolverSuper.get();
			sExpressionUsed = sReturn; //da noch weiter verarbeitet werden muss.
			
			//Zerlegen des Z-Tags, d.h. Teil vorher, Teil dahinter.
			ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReferenceParserSuper= new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
			objReturnReferenceParserSuper.set(objEntry);
			vecReturn = super.parseFirstVector(sExpressionUsed, objReturnReferenceParserSuper, bRemoveSurroundingSeparators);
			objEntry = objReturnReferenceSolverSuper.get();
			
			sReturn = (String) vecReturn.get(1);
			//sExpressionUsed = sReturn; //falls noch weiterverarbeitet werden muesste
			
			//Merke: Weitere Aufloesung bedarf das explizite solver-Flag
			//if(!this.getFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER)) break main;			
		}//end main:
		
		//NUN DEN INNERHALB DER EXPRESSION BERECHUNG ERSTELLTEN WERT uebernehmen
		this.setValue(sReturn);	
		vecReturn.replace(sReturn);		
		if(objEntry!=null) {		
			sReturn = VectorUtilZZZ.implode(vecReturn);
			objEntry.setValue(sReturn);
			if(sExpressionIn!=null) {
				if(!sExpressionIn.equals(sReturn)) objEntry.isParsed(true);
			}			
			if(objReturnReferenceIn!=null)objReturnReferenceIn.set(objEntry);//Wichtig: Reference nach aussen zurueckgeben.
		}
		return vecReturn;
	}
	
	@Override
	public String solveParsed(String sExpressionIn, ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReferenceIn, boolean bRemoveSurroundingSeparators) throws ExceptionZZZ {		
		String sReturn = sExpressionIn;
		
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
			if(!this.getFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER)) break main;
			
			String sExpression = sExpressionIn;

			
			//Löse Pfade, etc. auf wird schon vorher gemacht....
			//Löse nun die anderen Solver auf.
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
				
				boolean bAnyCall = KernelConfigSectionEntryUtilZZZ.getValueCallSolved(this.getFileConfigKernelIni(), sExpressionUsed, bUseCall, bForFurtherProcessing, saFlagZpassed, objReturnReferenceSolverCall);
				objEntry = objReturnReferenceSolverCall.get();
				if(bAnyCall) {
					this.getEntry().isExpression(true);
					this.getEntry().isCallSolved(true);
					this.getEntry().setValueCallSolved(objEntry.getValue());
					
					sExpressionUsed = objEntry.getValue(); //Zur Verarbeitung weitergeben
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
				sExpressionUsed = KernelConfigSectionEntryUtilZZZ.getValueJsonSolved(this.getFileConfigKernelIni(), sExpressionUsed, bUseJson, saFlagZpassed, objReturnReferenceSolverJson, objalsReturnValueJsonSolved,objhmReturnValueJsonSolved);					
				objEntry = objReturnReferenceSolverJson.get();
				if(objEntry.isExpression()) {
					this.getEntry().isExpression(true);
					if(objEntry.isJson()) {
						this.getEntry().isJson(true);
						if(objEntry.isJsonArray()) {
							this.getEntry().isJsonArray(true);								
							this.setValue(objalsReturnValueJsonSolved.getArrayList());
							this.setValue(ArrayListExtendedZZZ.debugString(objalsReturnValueJsonSolved.getArrayList()));						
						}
						
						if(objEntry.isJsonMap()) {	
							this.getEntry().isJsonMap(true);
							this.setValue(objhmReturnValueJsonSolved.get());	
							this.setValue(HashMapExtendedZZZ.computeDebugString(objhmReturnValueJsonSolved.get()));
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
				boolean bAnyEncryption = KernelConfigSectionEntryUtilZZZ.getValueEncryptionSolved(this.getFileConfigKernelIni(), sExpressionUsed, bUseEncryption, bForFurtherProcessing, saFlagZpassed, objReturnReferenceEncryption);
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
								
			sReturn = sExpressionUsed;							
		}//end main:
		
		//Als echten Ergebniswert aber die <Z>-Tags rausrechnen
		if(bRemoveSurroundingSeparators) {
			String sTagStart = this.getTagStarting();
			String sTagEnd = this.getTagClosing();
			String sValue = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sReturn, sTagStart, sTagEnd);												
			sReturn = sValue;
		}	
				
		//NUN DEN INNERHALB DER EXPRESSION BERECHUNG ERSTELLTEN WERT uebernehmen
		this.setValue(sReturn);	//Der Handler bekommt die ganze Zeile als Wert	
		if(objEntry!=null) {		
			objEntry.setValue(sReturn);
			if(objReturnReferenceIn!=null)objReturnReferenceIn.set(objEntry);
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
		if(this.objEntry==null) {
			this.objEntry = new KernelConfigSectionEntryZZZ<T>(this);			
		}
		return this.objEntry;
	}
	public void setEntry(IKernelConfigSectionEntryZZZ objEntry) throws ExceptionZZZ{
		this.objEntry = objEntry;
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

	@Override
	public ICryptZZZ getCryptAlgorithmType() throws ExceptionZZZ {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setCryptAlgorithmType(ICryptZZZ objCrypt) {
		// TODO Auto-generated method stub
		
	}

	
	
}
