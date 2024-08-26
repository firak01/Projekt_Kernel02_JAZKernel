package basic.zKernel.file.ini;

import java.util.Set;
import java.util.Vector;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.abstractList.HashMapCaseInsensitiveZZZ;
import basic.zBasic.util.abstractList.VectorZZZ;
import basic.zBasic.util.crypt.code.ICryptZZZ;
import basic.zBasic.util.datatype.calling.ReferenceZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zKernel.IKernelConfigSectionEntryZZZ;
import basic.zKernel.IKernelZZZ;
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
	
	
	private boolean KernelZFormulaIniSolverNew_(FileIniZZZ<T> objFileIni, HashMapCaseInsensitiveZZZ<String,String> hmVariable) throws ExceptionZZZ {
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
				if(objFileIni.getHashMapVariable()!=null){
					this.setHashMapVariable(objFileIni.getHashMapVariable());
				}
			}
						
			bReturn = true;
	 	}//end main:
		return bReturn;
	 }//end function KernelExpressionIniSolverNew_
	
	
	//### Andere Interfaces
	@Override
	public boolean isStringForConvertRelevant(String sToProof) throws ExceptionZZZ {		
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
	@Override
	public Vector<String> solveFirstVector(String sLineWithExpression,ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference) throws ExceptionZZZ {		
	     //Darin können also auch Variablen, etc. sein
		Vector<String> vecReturn = null;
		String sReturn = sLineWithExpression;
		
		IKernelConfigSectionEntryZZZ objReturn=objReturnReference.get();
		if(objReturn==null) {
			objReturn = this.getEntryNew(); //Hier schon die Rückgabe vorbereiten, falls eine weitere Verarbeitung nicht konfiguriert ist.
										 //Wichtig: Als oberste Methode immer ein neues Entry-Objekt holen. Dann stellt man sicher, das nicht mit Werten der vorherigen Suche gearbeitet wird.
			objReturnReference.set(objReturn);
		}//Achtung: Das objReturn Objekt NICHT generell uebernehmen. Es verfaelscht bei einem 2. Suchaufruf das Ergebnis.
		objReturn.setRaw(sLineWithExpression);
		
		main:{			
			vecReturn = super.solveFirstVector(sLineWithExpression, objReturnReference);
			if(vecReturn==null) break main;
			
			sReturn = VectorZZZ.implode(vecReturn);				
			objReturn.setValue(sReturn);
			if(!sLineWithExpression.equals(sReturn)) {
				objReturn.isSolved(true);
			}	
			
			//### Nun erst der MATH Teil, ggfs. mit ersetzten Variablen
			if(this.getFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA)) {
				String sExpressionWithTags = vecReturn.get(1); //VectorZZZ.implode(vecAll); //Der String hat jetzt Z-Tags
						
				//20180714 Hole Ausdrücke mit <z:math>...</z:math>, wenn das entsprechende Flag gesetzt ist.
				//Beispiel dafür: TileHexMap-Projekt: GuiLabelFontSize_Float
				//GuiLabelFontSize_float=<Z><Z:math><Z:val>[THM]GuiLabelFontSizeBase_float</Z:val><Z:op>*</Z:op><Z:val><z:var>GuiZoomFactorUsed</z:var></Z:val></Z:math></Z>
				if(this.getFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH)) {				
							
					//Dann erzeuge neues KernelExpressionMathSolverZZZ - Objekt.
					KernelZFormulaMathSolverZZZ<T> objMathSolver = new KernelZFormulaMathSolverZZZ<T>(); 
														
					//2. Ist in dem String math?	Danach den Math-Teil herausholen und in einen neuen vec packen.
					String sExpressionWithTagsOld = sExpressionWithTags;
					while(objMathSolver.isExpression(sExpressionWithTags)){
						
						TODOGOON20240826;//Aber das Parsen wird nicht ausgeführt. Interne Auflösung der MatheAusdrücke erfolgt nicht...
						String sValueMath = objMathSolver.parse(sExpressionWithTags);
						sExpressionWithTags=sValueMath;				
						
						if(sExpressionWithTagsOld.equals(sExpressionWithTags)) break; //Sicherheitsmassnahme gegen Endlosschleife
						sExpressionWithTagsOld = sExpressionWithTags;					
					}
					
					sReturn = sExpressionWithTags;
				}	
			}			
		}//end main:
		
		//NUN DEN INNERHALB DER EXPRESSION BERECHUNG ERSTELLTEN WERT in den Return-Vector übernehmen
		if(vecReturn!=null) {
			if(vecReturn.size()>=1) vecReturn.removeElementAt(0);
			vecReturn.add(0, "");
			
			if(vecReturn.size()>=2) vecReturn.removeElementAt(1);
			vecReturn.add(1, sReturn);
			
			if(vecReturn.size()>=3) vecReturn.removeElementAt(2);
			vecReturn.add(2, "");
		}			
		return vecReturn;			
	}
	
	
	
	@Override
	public String parse(String sLineWithExpression) throws ExceptionZZZ{
		return this.parse(sLineWithExpression, true);
	}
	
	@Override
	public String parse(String sLineWithExpression, boolean bRemoveSurroundingSeparators) throws ExceptionZZZ{
		String sReturn = sLineWithExpression;
		
		//Diesen Zwischenstand fuer weitere Verarbeitungen festhalten
		//Wichtig: Als oberste Methode immer ein neues Entry-Objekt holen. Dann stellt man sicher, das nicht mit Werten der vorherigen Suche gearbeitet wird.
		IKernelConfigSectionEntryZZZ objReturn = this.getEntryNew();
		objReturn.setRaw(sLineWithExpression);
		
		main:{
			if(! this.getFlag(IIniTagWithExpressionZZZ.FLAGZ.USEEXPRESSION)) break main;
			if(StringZZZ.isEmpty(sLineWithExpression)) break main;
					
			
			Vector<String> vecAll = this.parseAllVectorAsExpression(sLineWithExpression);//Hole hier erst einmal die Variablen-Anweisung und danach die IniPath-Anweisungen und ersetze sie durch Werte.			
			sReturn = VectorZZZ.implode(vecAll);
			objReturn.setValueAsExpression(sReturn); //nicht noch andere Z-Tags rumsetzen
						
			if(this.getFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA)) {
				String sExpressionWithTags = vecAll.get(1); //VectorZZZ.implode(vecAll); //Der String hat jetzt Z-Tags
						
				//20180714 Hole Ausdrücke mit <z:math>...</z:math>, wenn das entsprechende Flag gesetzt ist.
				//Beispiel dafür: TileHexMap-Projekt: GuiLabelFontSize_Float
				//GuiLabelFontSize_float=<Z><Z:math><Z:val>[THM]GuiLabelFontSizeBase_float</Z:val><Z:op>*</Z:op><Z:val><z:var>GuiZoomFactorUsed</z:var></Z:val></Z:math></Z>
				if(this.getFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH)) {				
							
					//Dann erzeuge neues KernelExpressionMathSolverZZZ - Objekt.
					KernelZFormulaMathSolverZZZ objMathSolver = new KernelZFormulaMathSolverZZZ(); 
														
					//2. Ist in dem String math?	Danach den Math-Teil herausholen und in einen neuen vec packen.
					String sExpressionWithTagsOld = sExpressionWithTags;
					while(objMathSolver.isExpression(sExpressionWithTags)){
						String sValueMath = objMathSolver.parse(sExpressionWithTags);
						sExpressionWithTags=sValueMath;				
						
						if(sExpressionWithTagsOld.equals(sExpressionWithTags)) break; //Sicherheitsmassnahme gegen Endlosschleife
						sExpressionWithTagsOld = sExpressionWithTags;					
					}
					
					
				}	
								
				vecAll.remove(1);
				vecAll.add(1, sExpressionWithTags);
				
				sReturn = VectorZZZ.implode(vecAll);
				objReturn.setValueAsExpression(sReturn); //nicht noch andere Z-Tags rumsetzen				
			}
			
			objReturn.setValue(sReturn);
			
			//Als echten Ergebniswert aber die Z-Tags rausrechnen
			//An dieser Stelle die Tags vom akuellen "Solver" Rausnehmen
			//AUSSER: Die <Z>-Tags sind am Anfang/Ende UND(!) es sind noch andere Formel Z-Tags "<Z:... im String vorhanden
			if(bRemoveSurroundingSeparators) {
				String sTagStart = this.getTagStarting();
				String sTagEnd = this.getTagClosing();
				String sValue = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sReturn, sTagStart, sTagEnd);												
				sReturn = sValue;
			}
		
//			Vector<String>vecReturn=new Vector<String>();
//			String sBefore = vecAll.get(0);
//			String sRest = vecAll.get(2);
//			if(!StringZZZ.isEmpty(sBefore)){
//				if(vecReturn.size()>=1) vecReturn.removeElementAt(0);
//				//Nachbereitung: Ein ggfs. /Z-Tag am Anfang des Rest entfernen
//				//Hier: Nur dann, wenn es nicht der String selber ist.
//				//if(!sRest.equals(sTagEnd) & StringZZZ.startsWithIgnoreCase(sRest, sTagEnd)) {
//				//Nein: Der Z-Tag einzeln hat nur Sinn, wenn noch andere Z: Tags drin enthalten sind. Wird weiter oben schon erledigt.				
//				if(StringZZZ.endsWithIgnoreCase(sBefore, sTagStart)) {
//					sBefore = StringZZZ.leftback(sBefore, sTagStart);
//				}
//				vecReturn.add(0, sBefore);
//			}else{
//				vecReturn.add(0,"");
//			}
//													
//			if(vecReturn.size()>=2) vecReturn.removeElementAt(1);
//			vecReturn.add(1, sValue);
//			
//			if(vecReturn.size()>=3) vecReturn.removeElementAt(2); 
//			if(!StringZZZ.isEmpty(sRest)){	
//				//Nachbereitung: Ein ggfs. /Z-Tag am Anfang des Rest entfernen
//				//Hier: Nur dann, wenn es nicht der String selber ist.
//				//if(!sRest.equals(sTagEnd) & StringZZZ.startsWithIgnoreCase(sRest, sTagEnd)) {
//				//Nein: Der Z-Tag einzeln hat nur Sinn, wenn noch andere Z: Tags drin enthalten sind. Wird weiter oben schon erledigt.
//				
//				if(StringZZZ.startsWithIgnoreCase(sRest, sTagEnd)) {
//					sRest = StringZZZ.rightback(sRest, sTagEnd);
//				}
//				vecReturn.add(2, sRest); //Falls vorhanden einen Restwert eintragen.
//			}else{
//				vecReturn.add(2,"");
//			}
			
//			sReturn = VectorZZZ.implode(vecReturn);			
		}//end main:
		
		objReturn.setValue(sReturn);
		return sReturn;
	}

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
