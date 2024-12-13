package basic.zKernel.file.ini;

import java.util.Set;
import java.util.Vector;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.abstractList.HashMapCaseInsensitiveZZZ;
import basic.zBasic.util.abstractList.Vector3ZZZ;
import basic.zBasic.util.abstractList.VectorUtilZZZ;
import basic.zBasic.util.crypt.code.ICryptZZZ;
import basic.zBasic.util.datatype.calling.ReferenceZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zKernel.IKernelConfigSectionEntryZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernel.KernelConfigSectionEntryZZZ;
import basic.zKernel.config.KernelConfigSectionEntryUtilZZZ;
import basic.zKernel.flag.event.IListenerObjectFlagZsetZZZ;
import basic.zKernel.flag.util.FlagZFassadeZZZ;
import custom.zKernel.file.ini.FileIniZZZ;

/**Diese Klasse verarbeitet ggf. Ausdruecke/Formeln in Ini-Dateien.
 *  Es kann dann in einem dieser Formeln z.B. auf den Property-Wert einer anderen Sektion zugegriffen werden. So entstehen 'dynamische' ini-Dateien.
 * @author lindhaueradmin
 *
 */
public class KernelZFormulaIniSolverZZZ<T> extends AbstractKernelIniSolverZZZ<T> implements IKernelZFormulaIniZZZ{
	private static final long serialVersionUID = 7989209806367224848L;
	public static String sTAG_NAME = "Z";
	
	public KernelZFormulaIniSolverZZZ() throws ExceptionZZZ{
		super("init");
		KernelZFormulaIniSolverNew_(null, null);
	}
	
	public KernelZFormulaIniSolverZZZ(String sFlag) throws ExceptionZZZ{
		super(sFlag);
		KernelZFormulaIniSolverNew_(null, null);
	}
		
	public KernelZFormulaIniSolverZZZ(String[] saFlag) throws ExceptionZZZ{
		super(saFlag);
		KernelZFormulaIniSolverNew_(null, null);
	}
	
	public KernelZFormulaIniSolverZZZ(IKernelZZZ objKernel) throws ExceptionZZZ{
		super(objKernel);
		KernelZFormulaIniSolverNew_(null, null);
	}

	
	public KernelZFormulaIniSolverZZZ(FileIniZZZ<T> objFileIni) throws ExceptionZZZ{
		super(objFileIni); //als IKernelUserZZZ - Object
		KernelZFormulaIniSolverNew_(objFileIni, null);
	}
	
	public KernelZFormulaIniSolverZZZ(FileIniZZZ<T> objFileIni, String[] saFlag) throws ExceptionZZZ{
		super(objFileIni, saFlag); //als IKernelUserZZZ - Object
		KernelZFormulaIniSolverNew_(objFileIni, null);
	}
	
	public KernelZFormulaIniSolverZZZ(IKernelZZZ objKernel, FileIniZZZ<T> objFileIni, String[] saFlag) throws ExceptionZZZ{
		super(objKernel, objFileIni, saFlag);
		KernelZFormulaIniSolverNew_(objFileIni, null);
	}
	
	public KernelZFormulaIniSolverZZZ(FileIniZZZ<T> objFileIni, HashMapCaseInsensitiveZZZ<String,String> hmVariable) throws ExceptionZZZ{
		super(objFileIni, hmVariable); //als IKernelUserZZZ - Object
	KernelZFormulaIniSolverNew_(objFileIni, hmVariable);
	}
	
	public KernelZFormulaIniSolverZZZ(FileIniZZZ<T> objFileIni, HashMapCaseInsensitiveZZZ<String,String> hmVariable, String[] saFlag) throws ExceptionZZZ{
		super(objFileIni, saFlag); //als IKernelUserZZZ - Object
		KernelZFormulaIniSolverNew_(objFileIni, hmVariable);
	}
	
	public KernelZFormulaIniSolverZZZ(IKernelZZZ objKernel, FileIniZZZ<T> objFileIni, HashMapCaseInsensitiveZZZ<String,String> hmVariable, String[] saFlag) throws ExceptionZZZ{
		super(objKernel, saFlag);
		KernelZFormulaIniSolverNew_(objFileIni, hmVariable);
	}
	
	
	private boolean KernelZFormulaIniSolverNew_(FileIniZZZ<T> objFileIn, HashMapCaseInsensitiveZZZ<String,String> hmVariable) throws ExceptionZZZ {
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
						
			if(hmVariable!=null){				
				this.setVariable(hmVariable);			//soll zu den Variablen aus derm Ini-File hinzuaddieren, bzw. ersetzen		
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
	 }//end function KernelExpressionIniSolverNew_
	
	
	//### Andere Interfaces
	
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	//### aus ISolveEnabledZZZ
	//Merke: Mit folgender Methode wird über das konkrete Flag dieser Solver ein-/ausgeschaltet. 
	//       Z.B. wichtig für das Entfernen der geparsten Tags, oder halt dieses Entfernen weglassen.
	//Merke: Folgende Methoden muessen im konkreten Solver implementiert werden, da der abstrakte Solver die konkreten Flags zur deaktivierung diese konkreten Solvers nicht kennt.
	@Override
	public boolean isSolverEnabledThis() throws ExceptionZZZ {
		return this.getFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA);
	}
	
	
	//### aus IConvertableZZZ
	@Override
	public boolean isConvertRelevant(String sToProof) throws ExceptionZZZ {		
		return false;
	}

	@Override
	public String getNameDefault() throws ExceptionZZZ {
	 return KernelZFormulaIniSolverZZZ.sTAG_NAME;
	}
	
//	/** Gibt einen Vector zurück, in dem das erste Element der Ausdruck VOR der ersten 'Expression' ist. Das 2. Element ist die Expression. Das 3. Element ist der Ausdruck NACH der ersten Expression.
//	* @param sLineWithExpression
//	* @return
//	* 
//	* lindhaueradmin; 06.03.2007 11:20:34
//	 * @throws ExceptionZZZ 
//	 */
//	@Override
//	public Vector<String>parseExpressionFirstVector(String sLineWithExpression) throws ExceptionZZZ{
//		Vector<String>vecReturn = new Vector<String>();		
//		main:{		
//			//Merke: Wie beim "Cascaded" Solver die Tags "vorne und hinten abschneiden".
//			//ABER: Beim "Formelausrechen" die Z-Tags im Ergebnisvector mitgeben.
//			vecReturn = StringZZZ.vecMid(sLineWithExpression, this.getTagStarting(), this.getTagClosing(), false, false);
//		}
//		return vecReturn;
//	}
	
//	@Override
//	public Vector<String> parseAllVector(String sLineWithExpression) throws ExceptionZZZ{
//		Vector<String> vecReturn = new Vector<String>();
//		main:{
//			if(StringZZZ.isEmpty(sLineWithExpression)) break main;			
//			vecReturn = this.parseFirstVector(sLineWithExpression);	// <Z> Tags am Rand aussen entfernen	
//			String sExpression = (String) vecReturn.get(1);									
//			if(!StringZZZ.isEmpty(sExpression)){
//				
//				//ZUERST: Löse ggfs. übergebene Variablen auf.
//				ZTagFormulaIni_VariableZZZ objVariable = new ZTagFormulaIni_VariableZZZ(this.getHashMapVariable());
//				while(objVariable.isExpression(sExpression)){
//					sExpression = objVariable.parse(sExpression);			
//				} //end while
//					
//								
//				//DANACH ALLE PATH-Ausdrücke, also [xxx]yyy ersetzen
//				KernelZFormulaIni_PathZZZ objIniPath = new KernelZFormulaIni_PathZZZ(this.getKernelObject(), this.getFileConfigKernelIni());
//				String sExpressionOld = sExpression;
//				while(objIniPath.isExpression(sExpression)){
//						sExpression = objIniPath.parse(sExpression);//in computeAsExpression wäre Z-Tags
//						if(sExpressionOld.equals(sExpression)) break;//Sonst Endlosschleife
//						sExpressionOld = sExpression;
//				} //end while
//				
//				//NUN DEN INNERHALB DER EXPRESSION BERECHUNG ERSTELLTEN WERT in den Return-Vector übernehmen										
//				//Den Wert ersetzen, wenn es was zu ersetzen gibt.
//				//MERKE: DER HAT Ggfs. NOCH Z-Tags drin in den "Before" und "Rest" Index-Werten
//				if(sExpression!=null){
//					if(vecReturn.size()>=2) vecReturn.removeElementAt(1);						
//					vecReturn.add(1, sExpression);
//				}
//				
//
//			} //end if sExpression = ""					
//		}//end main:
//		return vecReturn;
//	}
	
	
//	public Vector<String> solveAllVector(String sLineWithExpression) throws ExceptionZZZ{
//		Vector<String> vecReturn = new Vector<String>();
//		main:{
//			if(StringZZZ.isEmpty(sLineWithExpression)) break main;	
//						
//			boolean bHasVariableProcessed=false; boolean bHasPathProcessed=false;
//			vecReturn = this.computeAsExpressionFirstVector(sLineWithExpression);	// <Z> Tags am Rand aussen entfernen	
//			String sExpression = (String) vecReturn.get(1);									
//			if(!StringZZZ.isEmpty(sExpression)){
//				
//				//ZUERST: Löse ggfs. übergebene Variablen auf.
//				ZTagFormulaIni_VariableZZZ objVariable = new ZTagFormulaIni_VariableZZZ(this.getHashMapVariable());
//				String sExpressionOld = sExpression;
//				while(objVariable.isExpression(sExpressionOld)){
//					//Nein, dann werden ggfs. Werte vor und nach dem Ausdruck unterschlagen
//					//objVariable.compute(sExpressionOld);
//					//Also: Einen Vektor holen....
//					Vector<String> vecExpression = objVariable.parseFirstVector(sExpressionOld);
//					
//					sExpression = vecExpression.get(1); //Die umgebenden Werte aber auch noch sichern fuer die Rueckgabe
//					if(!StringZZZ.equals(sExpression,sExpressionOld)){
//						System.out.println(ReflectCodeZZZ.getPositionCurrent()+ ": Value durch FormulaIniSolver-VARIABLE verändert von '" + sExpressionOld + "' nach '" + sExpression +"'");
//					}else {
//						break;
//					}					
//
//					//Die umgebenden Werte sichern
//					String s0=vecReturn.get(0);
//					if(vecReturn.size()>=1) vecReturn.removeElementAt(0);
//					vecReturn.add(0, s0 + vecExpression.get(0));
//					
//					String s1 = sExpression;
//					if(vecReturn.size()>=2) vecReturn.removeElementAt(1);
//					vecReturn.add(1, s1);
//										
//					String s2=vecReturn.get(2);
//					if(vecReturn.size()>=3) vecReturn.removeElementAt(2);
//					vecReturn.add(2, vecExpression.get(2) + s2);
//					
//					sExpression = VectorZZZ.implode(vecReturn);
//					sExpressionOld=sExpression;//Sonst Endlosschleife.
//					bHasVariableProcessed = true;
//				} //end while
//					
//								
//				//DANACH ALLE PATH-Ausdrücke, also [xxx]yyy ersetzen
//				KernelZFormulaIni_PathZZZ objIniPath = new KernelZFormulaIni_PathZZZ(this.getKernelObject(), this.getFileConfigKernelIni());
//				sExpressionOld = sExpression;
//				while(objIniPath.isExpression(sExpressionOld)){
//											
//					//Verwende wie oben computeExpressionFirstVector(sExpressionOld);
//					Vector<String> vecExpression = objIniPath.parseFirstVector(sExpressionOld);//in computeAsExpression wäre Z-Tags
//					sExpression = vecExpression.get(1); //Die umgebenden Werte aber auch noch sichern fuer die Rueckgabe
//					if(!sExpressionOld.equals(sExpression)) {
//						System.out.println(ReflectCodeZZZ.getPositionCurrent()+ ": Value durch FormulaIniSolver-PATH verändert von '" + sExpressionOld + "' nach '" + sExpression +"'");
//					}else {
//						break;//Sonst Endlosschleife
//					}
//					
//					//Die umgebenden Werte sichern
//					if(bHasVariableProcessed || bHasPathProcessed) {
//						//wenn der 0 Index und 2 Index des Vektors schon mal beruecksichtig wurde, dann wuerde man ihn immer verdoppeln	
//						String s0 = vecExpression.get(0);
//						if(vecReturn.size()>=1) vecReturn.removeElementAt(0);
//						vecReturn.add(0, s0);
//						
//						String s1 = vecExpression.get(1);
//						if(vecReturn.size()>=2) vecReturn.removeElementAt(1);
//						vecReturn.add(1, s1);	
//						
//						String s2 = vecExpression.get(2);
//						if(vecReturn.size()>=3) vecReturn.removeElementAt(2);
//						vecReturn.add(2, s2);
//						
//						sExpression = VectorZZZ.implode(vecReturn);
//						sExpressionOld=sExpression;//Sonst Endlosschleife.
//					}else {
//						//wenn der 0 Index und 2 Index des Vektors schon mal beruecksichtig wurde, dann wuerde man ihn immer verdoppeln
//						String s0 = vecReturn.get(0);
//						if(vecReturn.size()>=1) vecReturn.removeElementAt(0);
//						vecReturn.add(0, s0 + vecExpression.get(0));
//						
//						String s1 = sExpression;
//						if(vecReturn.size()>=2) vecReturn.removeElementAt(1);
//						vecReturn.add(1, s1);
//						
//						String s2 = vecReturn.get(2);
//						if(vecReturn.size()>=3) vecReturn.removeElementAt(2);
//						vecReturn.add(2, vecExpression.get(2) + s2);
//						
//						sExpression = VectorZZZ.implode(vecReturn);
//						sExpressionOld=sExpression;//Sonst Endlosschleife.
//					}
//						
//					bHasPathProcessed = true;
//				} //end while
//			} //end if sExpression = ""					
//		}//end main:
//		return vecReturn;
//	}
	


	//###### Getter / Setter
	
	//############################################
	//### Aus ISolveEnabled		
		/**Methode ueberschreibt die Aufloesung von Pfaden und Ini-Variablen.
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
		
		//Analog zu AbstractkernelIniSolverZZZ, nur jetzt mit MATH-Tag (vorher aber noch Pfade und ini-Variablen aufloesen)
		private String solveParsed_(String sExpressionIn, ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReferenceIn, boolean bRemoveSurroundingSeparators)	throws ExceptionZZZ {
			String sReturn = sExpressionIn; //Darin können also auch Variablen, etc. sein
			String sReturnTag = null;
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
				//Nein, das holt auch ein neues inneres Objekt und die teilen sich dann die Referenz... objEntry = this.getEntryNew(); //Hier schon die Rückgabe vorbereiten, falls eine weitere Verarbeitung nicht konfiguriert ist.
				 //Wichtig: Als oberste Methode immer ein neues Entry-Objekt holen. Dann stellt man sicher, das nicht mit Werten der vorherigen Suche gearbeitet wird.
				objEntry = new KernelConfigSectionEntryZZZ<T>();
				objReturnReference.set(objEntry);
			}//Achtung: Das objReturn Objekt NICHT generell uebernehmen. Es verfaelscht bei einem 2. Suchaufruf das Ergebnis.
			this.setRaw(sExpressionIn);
			objEntry.setRaw(sExpressionIn);	
			objEntry.isSolveCalled(true);
			
			main:{	
				//Aufloesen von Pfaden und ini-Variablen
				ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReferenceSolverSuper= new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
				objReturnReferenceSolverSuper.set(objEntry);
				sReturnTag = super.solveParsed(sExpressionUsed, objReturnReferenceSolverSuper, bRemoveSurroundingSeparators);
				sReturn = sReturnTag;
				objEntry = objReturnReferenceSolverSuper.get();

				//Aufloesen des Math-Tags
				sReturnTag = this.solveParsed_Expression_(sExpressionIn, objReturnReference, bRemoveSurroundingSeparators);
				sReturn = sReturnTag;
				objEntry = objReturnReference.get();	
				
				objEntry.isSolved(true);
				if(!sExpressionUsed.equals(sReturn)) {
					objEntry.isSolvedChanged(true);
					objEntry.setValueFormulaSolvedAndConverted(sReturn);					
					objEntry.setValueAsExpression(sReturn);
				}																
			}//end main
			
			//NUN DEN INNERHALB DER EXPRESSION BERECHUNG ERSTELLTEN WERT uebernehmen
			this.setValue(sReturnTag);		
			if(objEntry!=null) {		
				objEntry.setValue(sReturn);
				if(sExpressionIn!=null) {
					if(!sExpressionIn.equals(sReturn)) objEntry.isSolveCalled(true);
				}
				if(objReturnReferenceIn!=null)objReturnReferenceIn.set(objEntry);
			}
			return sReturn;	
		}
	
	
	//############################################
	private String solveParsed_Expression_(String sExpressionIn,ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReferenceIn, boolean bRemoveSurroundingSeparators) throws ExceptionZZZ {				
		String sReturn = sExpressionIn;
		String sReturnTag = null;
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
			//Nein, das holt auch ein neues inneres Objekt und die teilen sich dann die Referenz... objEntry = this.getEntryNew(); //Hier schon die Rückgabe vorbereiten, falls eine weitere Verarbeitung nicht konfiguriert ist.
			 //Wichtig: Als oberste Methode immer ein neues Entry-Objekt holen. Dann stellt man sicher, das nicht mit Werten der vorherigen Suche gearbeitet wird.
			objEntry = new KernelConfigSectionEntryZZZ<T>();
			objReturnReference.set(objEntry);
		}//Achtung: Das objReturn Objekt NICHT generell uebernehmen. Es verfaelscht bei einem 2. Suchaufruf das Ergebnis.
		this.setRaw(sExpressionIn);
		objEntry.setRaw(sExpressionIn);			
		objEntry.isSolveCalled(true);
		
		main:{			
			//!!! Pfade und Variablen ersetzen, wurde schon vorher gemacht !!!
			
			//### Nun erst der MATH Teil, ggfs. mit ersetzten Variablen
			if(this.getFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA)) {
				String sExpressionWithTags = sExpressionUsed;
						
				//20180714 Hole Ausdrücke mit <z:math>...</z:math>, wenn das entsprechende Flag gesetzt ist.
				//Beispiel dafür: TileHexMap-Projekt: GuiLabelFontSize_Float
				//GuiLabelFontSize_float=<Z><Z:math><Z:val>[THM]GuiLabelFontSizeBase_float</Z:val><Z:op>*</Z:op><Z:val><z:var>GuiZoomFactorUsed</z:var></Z:val></Z:math></Z>
				if(this.getFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH)) {				
												
					//Hier KernelZFormulaMathSolverZZZ verwenden
					//Merke: Die statischen Methoden leisten mehr als nur die ...Solver....
					//       Durch den int Rückgabwert sorgen sie nämlich für die korrekte Befüllung von 
					//       objReturn, also auch der darin verwendeten Flags bIsJson, bIsJsonMap, etc.
					KernelZFormulaMathSolverZZZ<T> objMathSolverDummy = new KernelZFormulaMathSolverZZZ<T>();
					String[] saFlagZpassed = FlagZFassadeZZZ.seekFlagZrelevantForObject(this, objMathSolverDummy, true);
					
					//Variablen sind vorher schon aufgeloest, wir brauchen also keine HashMap mit den Variablen:	HashMapCaseInsensitiveZZZ<String,String>hmVariable = this.getHashMapVariable();					
					KernelZFormulaMathSolverZZZ<T> objMathSolver = new KernelZFormulaMathSolverZZZ<T>(this.getKernelObject(), this.getFileConfigKernelIni(), saFlagZpassed);
																									
					//2. Ist in dem String math?	Danach den Math-Teil herausholen und in einen neuen vec packen.
					String sExpressionWithTagsOld = sExpressionWithTags;
					while(objMathSolver.isExpression(sExpressionWithTags)){
												
						String sExpressionMathParsedAndSolved = objMathSolver.solve(sExpressionWithTags);
						if(sExpressionWithTags.equals(sExpressionMathParsedAndSolved)) break; //Sicherheitsmassnahme gegen Endlosschleife
						sExpressionWithTags=sExpressionMathParsedAndSolved;															
					}
					
					sReturnTag = sExpressionWithTags;
					objEntry.isSolved(true);
					if(!sExpressionWithTagsOld.equals(sExpressionWithTags)) {						
						objEntry.isFormulaMathSolved(true);
						objEntry.setValueFormulaSolvedAndConverted(sReturnTag);
					}					
				}	
			}			
		}//end main:
		
		//NUN DEN INNERHALB DER EXPRESSION BERECHUNG ERSTELLTEN WERT uebernehmen
		if(sReturnTag!=null) sReturn = sReturnTag;
		this.setValue(sReturnTag);		
		if(objEntry!=null) {		
			objEntry.setValue(sReturn);
			if(sExpressionIn!=null) {
				if(!sExpressionIn.equals(sReturn)) {
					objEntry.isExpression(true);
					objEntry.isSolvedChanged(true);
				}
			}
			if(objReturnReferenceIn!=null)objReturnReferenceIn.set(objEntry);
		}
		return sReturn;	
	}
	
	//### Aus IParseEnabledZZZ	
	
	//### aus IKernelEntryExpressionUserZZZ
	
	/** Gibt einen Vector zurück, in dem das erste Element der Ausdruck VOR der ersten 'Expression' ist. Das 2. Element ist die Expression. Das 3. Element ist der Ausdruck NACH der ersten Expression.
	* @param sLineWithExpression
	* @return
	* 
	* lindhaueradmin; 06.03.2007 11:20:34
	 * @throws ExceptionZZZ 
	 */
//	@Override
//	public Vector3ZZZ<String> parseFirstVector(String sExpression) throws ExceptionZZZ {
//		return this.parseFirstVector_(sExpression, null, true);
//	}
//	
//	@Override
//	public Vector3ZZZ<String> parseFirstVector(String sExpression, ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReferenceIn) throws ExceptionZZZ {
//		return this.parseFirstVector_(sExpression, objReturnReferenceIn, true);
//	}
//	
//	@Override
//	public Vector3ZZZ<String> parseFirstVector(String sExpression, ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReferenceIn, boolean bRemoveSurroundingSeparators) throws ExceptionZZZ {
//		return this.parseFirstVector_(sExpression, objReturnReferenceIn, bRemoveSurroundingSeparators);
//	}
//	
//	private Vector3ZZZ<String> parseFirstVector_(String sExpressionIn, ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReferenceIn, boolean bRemoveSurroundingSeparators) throws ExceptionZZZ {
//		Vector3ZZZ<String> vecReturn = new Vector3ZZZ<String>();
//		IKernelConfigSectionEntryZZZ objEntry = null;
//		
//		String sReturn = sExpressionIn;
//		String sExpressionUsed = sExpressionIn;
//
//		main:{		
//			if(StringZZZ.isEmpty(sExpressionIn)) break main;
//			if(! this.getFlag(IIniTagWithExpressionZZZ.FLAGZ.USEEXPRESSION)) break main;
//			if(!this.getFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA)) break main;			
//					
//			
//			ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReference = null;
//			if(objReturnReferenceIn==null) {	
//				objReturnReference = new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
//			}else {
//				objReturnReference = objReturnReferenceIn;
//				objEntry = objReturnReference.get();
//			}
//			if(objEntry==null) {
//				objEntry = new KernelConfigSectionEntryZZZ<T>(this); //this.getEntryNew(); es gingen alle Informationen verloren //Hier schon die Rückgabe vorbereiten, falls eine weitere Verarbeitung nicht konfiguriert ist.
//											 //Wichtig: Als oberste Methode immer ein neues Entry-Objekt holen. Dann stellt man sicher, das nicht mit Werten der vorherigen Suche gearbeitet wird.
//				objReturnReference.set(objEntry);								
//			}//Achtung: Das objReturn Objekt NICHT generell versuchen mit .getEnry() und ggfs. dann darin .getEntryNew89 uebernehmen. Es verfaelscht bei einem 2. Suchaufruf das Ergebnis.
//			objEntry.setRaw(sExpressionIn);
//			
//			vecReturn = StringZZZ.vecMidFirst(sExpressionUsed, this.getTagStarting(), this.getTagClosing(), true, false);			
//			sExpressionUsed = (String) vecReturn.get(1);
//			sReturn = sExpressionUsed;
//			objEntry.setValueAsExpression(sReturn); //nicht noch andere Z-Tags rumsetzen
//			
//			
//			//20180714 Hole Ausdrücke mit <z:math>...</z:math>, wenn das entsprechende Flag gesetzt ist.
//			//Beispiel dafür: TileHexMap-Projekt: GuiLabelFontSize_Float
//			//GuiLabelFontSize_float=<Z><Z:math><Z:val>[THM]GuiLabelFontSizeBase_float</Z:val><Z:op>*</Z:op><Z:val><z:var>GuiZoomFactorUsed</z:var></Z:val></Z:math></Z>
//			if(this.getFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH)) {			
//																						
//				//Dann erzeuge neues KernelExpressionMathSolverZZZ - Objekt.
//				KernelZFormulaMathSolverZZZ<T> objMathSolverDummy = new KernelZFormulaMathSolverZZZ<T>();
//				String[] saFlagZpassed = FlagZFassadeZZZ.seekFlagZrelevantForObject(this, objMathSolverDummy, true);
//				HashMapCaseInsensitiveZZZ<String,String>hmVariable = this.getHashMapVariable(); //wird wohl nicht benutzt, noch nicht...
//				
//				KernelZFormulaMathSolverZZZ<T> objMathSolver = new KernelZFormulaMathSolverZZZ<T>(this.getKernelObject(), this.getFileConfigKernelIni(), saFlagZpassed);
//				 														
//				//2. Ist in dem String math?	Danach den Math-Teil herausholen und in einen neuen vec packen.								
//				ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReferenceMathSolver = new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
//				objReturnReferenceMathSolver.set(objEntry);
//
//				String sExpressionTemp;				
//				while(objMathSolver.isExpression(sExpressionUsed)){
//					sExpressionTemp = objMathSolver.solve(sExpressionUsed, objReturnReferenceMathSolver, true); //die Z-Math Tags rausnehmen
//					
//					if(StringZZZ.isEmpty(sExpressionTemp)){
//						break;
//					}else if(sExpressionUsed.equals(sExpressionTemp)) {
//						break;
//					}else {
//						sExpressionUsed = sExpressionTemp;
//					}											
//				}//end while
//				sReturn = sExpressionUsed;
//				
//				this.setValue(sReturn);
//				vecReturn.replace(sReturn);
//				objEntry.setValue(sReturn);
//				if(sReturn!=sExpressionIn) {
//					objEntry.isParsed(true);
//					objEntry.isFormulaMathSolved(true);
//				}				
//			}//end if flag useFormulaMath
//						
//			//Als echten Ergebniswert aber die Z-Tags rausrechnen
//			//An dieser Stelle die Tags vom akuellen "Solver" Rausnehmen
//			//AUSSER: Die <Z>-Tags sind am Anfang/Ende UND(!) es sind noch andere Formel Z-Tags "<Z:... im String vorhanden
//			if(bRemoveSurroundingSeparators) {
//				String sTagStart = this.getTagStarting();
//				String sTagEnd = this.getTagClosing();
//				String sValue = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sReturn, sTagStart, sTagEnd);												
//				sReturn = sValue;
//			}			
//		}//end main:
//		
//		vecReturn.replace(sReturn);
//		
//		//Z-Tags "aus der Mitte entfernen"... Wichtig für das Ergebnis eines Parsens
//		//...aber nur, wenn ein Pfad gefunden wurde.
//		if(bRemoveSurroundingSeparators) {
//			String sTagStart="<Z>";
//			String sTagEnd="</Z>";
//			KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(vecReturn, sTagStart, sTagEnd);
//		}
//
//		this.setValue((String) vecReturn.get(1));
//		if(objEntry!=null) {
//			objEntry.setValue(sReturn);	
//			if(sExpressionIn!=null) {
//				if(!sExpressionIn.equals(sReturn)) objEntry.isParsed(true);
//			}				
//			if(objReturnReferenceIn!=null) objReturnReferenceIn.set(objEntry);
//		}			
//		return vecReturn;
//	}

	//########################################################
	//### FLAG Handling
	
	//### aus IKernelZFormulaIniZZZ
	@Override
	public boolean getFlag(IKernelZFormulaIniZZZ.FLAGZ objEnum_IKernelZFormulaIniZZZ) {
		return this.getFlag(objEnum_IKernelZFormulaIniZZZ.name());
	}
	
	@Override
	public boolean setFlag(IKernelZFormulaIniZZZ.FLAGZ objEnum_IKernelZFormulaIniZZZ, boolean bFlagValue) throws ExceptionZZZ {
		return this.setFlag(objEnum_IKernelZFormulaIniZZZ.name(), bFlagValue);
	}
	
	@Override
	public boolean[] setFlag(IKernelZFormulaIniZZZ.FLAGZ[] objaEnum_IKernelZFormulaIniZZZ, boolean bFlagValue) throws ExceptionZZZ {
		boolean[] baReturn=null;
		main:{
			if(!ArrayUtilZZZ.isNull(objaEnum_IKernelZFormulaIniZZZ)) {
				baReturn = new boolean[objaEnum_IKernelZFormulaIniZZZ.length];
				int iCounter=-1;
				for(IKernelZFormulaIniZZZ.FLAGZ objEnum_IKernelZFormulaIniZZZ:objaEnum_IKernelZFormulaIniZZZ) {
					iCounter++;
					boolean bReturn = this.setFlag(objEnum_IKernelZFormulaIniZZZ, bFlagValue);
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
	
	//### aus IKernelZFormulaIni_PathZZZ
	@Override
	public boolean getFlag(IKernelZFormulaIni_PathZZZ.FLAGZ objEnum_IKernelZFormulaIni_PathZZZ) {
		return this.getFlag(objEnum_IKernelZFormulaIni_PathZZZ.name());
	}
	
	@Override
	public boolean setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ objEnum_IKernelZFormulaIni_PathZZZ, boolean bFlagValue) throws ExceptionZZZ {
		return this.setFlag(objEnum_IKernelZFormulaIni_PathZZZ.name(), bFlagValue);
	}
	
	@Override
	public boolean[] setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ[] objaEnum_IKernelZFormulaIni_PathZZZ, boolean bFlagValue) throws ExceptionZZZ {
		boolean[] baReturn=null;
		main:{
			if(!ArrayUtilZZZ.isNull(objaEnum_IKernelZFormulaIni_PathZZZ)) {
				baReturn = new boolean[objaEnum_IKernelZFormulaIni_PathZZZ.length];
				int iCounter=-1;
				for(IKernelZFormulaIni_PathZZZ.FLAGZ objEnum_IKernelZFormulaIni_PathZZZ:objaEnum_IKernelZFormulaIni_PathZZZ) {
					iCounter++;
					boolean bReturn = this.setFlag(objEnum_IKernelZFormulaIni_PathZZZ, bFlagValue);
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
}//End class
