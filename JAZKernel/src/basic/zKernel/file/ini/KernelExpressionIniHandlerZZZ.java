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
	
	//### aus IParseEnabledZZZ
	@Override
	public Vector3ZZZ<String> parseFirstVector(String sExpression, boolean bRemoveSurroundingSeparators) throws ExceptionZZZ {
		//Muss ueberschrieben werden, damit die "einfache Tag" Methode nicht greift und wir mit der parse - Methode dieser konkreten Klasse arbeiten.
		return this.parseFirstVector_(sExpression, null, bRemoveSurroundingSeparators, true);
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
		return XmlUtilZZZ.isParse(sExpression, KernelZFormulaIni_PathZZZ.sTAG_NAME, false);
		return KernelZFormulaIni_PathZZZ.isParseStatic(sExpression);
	}
	
	@Override
	public boolean isSubstituteVariable(String sExpression) throws ExceptionZZZ {
		return XmlUtilZZZ.isParse(sExpression, ZTagFormulaIni_VariableZZZ.sTAG_NAME, false);
		return ZTagFormulaIni_VariableZZZ.isParseStatic(sExpression);
	}
	@Override
	public String substitute(String sExpression) throws ExceptionZZZ {
		return this.substitute_(sExpression, null, true);
	}

	@Override
	public String substitute(String sExpression, boolean bRemoveSuroundingSeparators) throws ExceptionZZZ {
		return this.substitute_(sExpression, null, bRemoveSuroundingSeparators);
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
	public String substituteParsed(Vector3ZZZ<String> vecExpression, boolean bRemoveSuroundingSeparators) throws ExceptionZZZ {
		return this.substituteParsed_(vecExpression, null, bRemoveSuroundingSeparators);
	}
	
	@Override
	public String substituteParsed(Vector3ZZZ<String> vecExpression, ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference, boolean bRemoveSuroundingSeparators) throws ExceptionZZZ {
		return this.substituteParsed_(vecExpression, objReturnReference, bRemoveSuroundingSeparators);
	}
	
	

	@Override
	public String substituteParsed(String sExpression, boolean bRemoveSuroundingSeparators) throws ExceptionZZZ {
		// TODO Auto-generated method stub
		return null;
	}

	/* Aufloesen der INI-Pfade und Variablen. */
	private String substituteParsed_(String sExpressionIn, ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReferenceIn, boolean bRemoveSurroundingSeparators)	throws ExceptionZZZ {
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
						
			if(this.getFlag(IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE)) {
				//ZUERST: Löse ggfs. übergebene Variablen auf.
				//!!! WICHTIG: BEI DIESEN AUFLOESUNGEN NICHT DAS UEBERGEORNETE OBJENTRY VERWENDEN, SONDERN INTERN EIN EIGENES!!! 
									
				//Merke: Fuer einfache Tag gibt es keine zu verarbeitenden Flags, also muss man auch keine suchen und uebergeben.
				//       Hier aber ein 
				String sExpressionOld = sExpressionUsed;
				String sExpressionTemp;					
				
				ZTagFormulaIni_VariableZZZ<T> exDummy = new ZTagFormulaIni_VariableZZZ<T>();
				String[] saFlagZpassed = FlagZFassadeZZZ.seekFlagZrelevantForObject(this, exDummy, true); //this.getFlagZ_passable(true, exDummy);
					
				ZTagFormulaIni_VariableZZZ<T> objVariable = new ZTagFormulaIni_VariableZZZ<T>(this.getHashMapVariable(), saFlagZpassed); 
				while(objVariable.isExpression(sExpressionUsed)){
					Vector3ZZZ<String> vecExpressionTemp =  objVariable.parseFirstVector(sExpressionUsed, true); //auf jeden Fall um Variablen herum den Z-Tag entfernen
					if(vecExpressionTemp==null) break;
					
					sExpressionTemp = (String) vecExpressionTemp.get(1);
					if(StringZZZ.isEmpty(sExpressionTemp)) {
						break;
					}else if(sExpressionUsed.equals(sExpressionTemp)) {
						break;
					}else{
						sExpressionUsed = VectorUtilZZZ.implode(vecExpressionTemp);					
					}
				} //end while
				sReturn = sExpressionUsed;
				this.setValue(sReturn);
				objEntry.setValue(sReturn);
				if(sReturn!=sExpressionOld) {
					objEntry.isParsed(true);
					objEntry.isVariableSubstiuted(true);
				}
				sExpressionUsed = sReturn; //fuer ggfs. notwendige Weiterverarbeitung
			}	
			
			if(this.getFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH)) {	
				//DANACH: ALLE PATH-Ausdrücke, also [xxx]yyy ersetzen
				//Problem hier: [ ] ist auch der JSON Array-Ausdruck
				String sExpressionOld = sExpressionUsed;
				String sExpressionTemp;
				
				KernelZFormulaIni_PathZZZ<T> exDummy = new KernelZFormulaIni_PathZZZ<T>();
				String[] saFlagZpassed = FlagZFassadeZZZ.seekFlagZrelevantForObject(this, exDummy, true); //this.getFlagZ_passable(true, exDummy);
								
				KernelZFormulaIni_PathZZZ<T> objFormulaIniPath = new KernelZFormulaIni_PathZZZ<T>(this.getKernelObject(), this.getFileConfigKernelIni(), saFlagZpassed);
				while(objFormulaIniPath.isExpression(sExpressionUsed)){
						Vector3ZZZ<String> vecExpressionTemp = objFormulaIniPath.parseFirstVector(sExpressionUsed, true); //auf jeden Fall um PATH-Anweisungen herum den Z-Tag entfernen
						if(vecExpressionTemp==null) break;
						
						sExpressionTemp = VectorUtilZZZ.implode(vecExpressionTemp);	
						if(StringZZZ.isEmpty(sExpressionTemp)) {
							break;
						}else if(sExpressionUsed.equals(sExpressionTemp)) {
							break;
						}else{
							sExpressionUsed = sExpressionTemp;						
						}											
				} //end while
				sReturn = sExpressionUsed;
				this.setValue(sReturn);
				objEntry.setValue(sReturn);
				if(!sExpressionOld.equals(sReturn)) {
					objEntry.isParsed(true);
					objEntry.isPathSubstituted(true);
				}
				sExpressionUsed = sReturn;  //fuer ggfs. notwendige Weiterverarbeitung
			}//end if .getFlag(..USE_...Path...)
			
			//Merke: Weitere Aufloesung bedarf das explizite solver-Flag
			//if(!this.getFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER)) break main;
			
		}//end main:
		
		//NUN DEN INNERHALB DER EXPRESSION BERECHUNG ERSTELLTEN WERT uebernehmen
		//Der Wert ist nur der TagInhalt this.setValue(sReturn);		
		if(objEntry!=null) {		
			objEntry.setValue(sReturn);
			objEntry.setValue(sReturn);	
//20241011: Nein, diese Aufloesung hat mit den eigentlichen Solvern nix zu tun!!!
//			if(sExpressionIn!=null) {
//				if(!sExpressionIn.equals(sReturn)) objEntry.isSolved(true);
//			}
			if(objReturnReferenceIn!=null)objReturnReferenceIn.set(objEntry);
		}
		return sReturn;	
	}
	
	private String substituteParsed_(Vector3ZZZ<String> vecExpression, ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReferenceIn, boolean bRemoveSuroundingSeparators) throws ExceptionZZZ {
		String sReturn = "";
		main:{
			if(vecExpression==null) break main;
			
			String sExpression = (String) vecExpression.get(1);
			sReturn = this.substituteParsed(sExpression, objReturnReferenceIn, bRemoveSuroundingSeparators);
				
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
			if(StringZZZ.isEmpty(sExpressionIn)) break main;
			
			boolean bUseExpression = this.getFlag(IIniTagWithExpressionZZZ.FLAGZ.USEEXPRESSION); 
			if(!bUseExpression) break main;
			
			String sExpressionUsed = sExpressionIn;
			
			//Aufloesen von Pfaden und ini-Variablen VOR den anderen SOLVERN, darum schon beim Parsen.
//			ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReferenceSolverSuper= new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
//			objReturnReferenceSolverSuper.set(objEntry);
//			sReturn = super.solveParsed(sExpressionUsed, objReturnReferenceSolverSuper, bRemoveSurroundingSeparators);
//			objEntry = objReturnReferenceSolverSuper.get();
//			sExpressionUsed = sReturn; //da noch weiter verarbeitet werden muss.
			
			ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReferenceSubstitute= new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
			sReturn = this.substituteParsed(sExpressionUsed, objReturnReferenceSubstitute, bRemoveSurroundingSeparators);			
			objEntry = objReturnReferenceSubstitute.get();
			sExpressionUsed = sReturn; //da noch weiter verarbeitet werden muss.
			
			//Zerlegen des Z-Tags, d.h. Teil vorher, Teil dahinter.
			ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReferenceParserSuper= new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
			objReturnReferenceParserSuper.set(objEntry);
			vecReturn = super.parseFirstVector(sExpressionUsed, objReturnReferenceParserSuper, bRemoveSurroundingSeparators);
			objEntry = objReturnReferenceParserSuper.get();
			
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
	
	
	
	
	/** Berechne die INI-Werte. 
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
	 * @param sLineWithExpression
	 * @param objReturnReference
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 06.05.2023, 07:41:02
	 */
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
			if(sExpressionIn!=null) {
				if(!sExpressionIn.equals(sReturn)) objEntry.isSolved(true);
			}
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
}
