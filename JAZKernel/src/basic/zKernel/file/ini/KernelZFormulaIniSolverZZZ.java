package basic.zKernel.file.ini;

import java.util.Set;
import java.util.Vector;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.abstractList.HashMapCaseInsensitiveZZZ;
import basic.zBasic.util.abstractList.VectorZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zKernel.IKernelConfigSectionEntryZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernel.config.KernelConfigSectionEntryUtilZZZ;
import custom.zKernel.file.ini.FileIniZZZ;

/**Diese Klasse verarbeitet ggf. Ausdruecke/Formeln in Ini-Dateien.
 *  Es kann dann in einem dieser Formeln z.B. auf den Property-Wert einer anderen Sektion zugegriffen werden. So entstehen 'dynamische' ini-Dateien.
 * @author lindhaueradmin
 *
 */
public class KernelZFormulaIniSolverZZZ<T> extends AbstractKernelIniSolverZZZ<T> implements IKernelZFormulaIniSolverZZZ{
	private static final long serialVersionUID = 7989209806367224848L;
	public static String sTAG_NAME = "Z";
	private HashMapCaseInsensitiveZZZ<String,String> hmVariable =null;

	public KernelZFormulaIniSolverZZZ() throws ExceptionZZZ{
		super("init");
		KernelExpressionIniSolverNew_();
	}
	
	public KernelZFormulaIniSolverZZZ(String sFlag) throws ExceptionZZZ{
		super(sFlag);
		KernelExpressionIniSolverNew_();
	}
	
	public KernelZFormulaIniSolverZZZ(String[] saFlag) throws ExceptionZZZ{
		super(saFlag);
		KernelExpressionIniSolverNew_();
	}
	
	public KernelZFormulaIniSolverZZZ(FileIniZZZ objFileIni) throws ExceptionZZZ{
		super(objFileIni.getKernelObject());
		KernelExpressionIniSolverNew_();
	}
	
	public KernelZFormulaIniSolverZZZ(FileIniZZZ objFileIni, String[] saFlag) throws ExceptionZZZ{
		super(objFileIni.getKernelObject(), saFlag);
		KernelExpressionIniSolverNew_();
	}
	
	public KernelZFormulaIniSolverZZZ(IKernelZZZ objKernel, FileIniZZZ objFileIni, String[] saFlag) throws ExceptionZZZ{
		super(objKernel, saFlag);
		KernelExpressionIniSolverNew_();
	}
	
	public KernelZFormulaIniSolverZZZ(FileIniZZZ objFileIni, HashMapCaseInsensitiveZZZ<String,String> hmVariable) throws ExceptionZZZ{
		super(objFileIni.getKernelObject());
		KernelExpressionIniSolverNew_();
	}
	
	public KernelZFormulaIniSolverZZZ(FileIniZZZ objFileIni, HashMapCaseInsensitiveZZZ<String,String> hmVariable, String[] saFlag) throws ExceptionZZZ{
		super(objFileIni.getKernelObject(), saFlag);
		KernelExpressionIniSolverNew_();
	}
	
	public KernelZFormulaIniSolverZZZ(IKernelZZZ objKernel, FileIniZZZ objFileIni, HashMapCaseInsensitiveZZZ<String,String> hmVariable, String[] saFlag) throws ExceptionZZZ{
		super(objKernel, saFlag);
		KernelExpressionIniSolverNew_();
	}
	
	
	private boolean KernelExpressionIniSolverNew_() throws ExceptionZZZ {
		 boolean bReturn = false;	
		 main:{
				if(this.getFlag("init")==true){
					bReturn = true;
					break main;
				}	
				
				bReturn = true;
		 	}//end main:
			return bReturn;
	 }//end function KernelExpressionIniSolverNew_
	
	
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
	
	
	public Vector<String> solveAllVector(String sLineWithExpression) throws ExceptionZZZ{
		Vector<String> vecReturn = new Vector<String>();
		main:{
			if(StringZZZ.isEmpty(sLineWithExpression)) break main;	
						
			boolean bHasVariableProcessed=false; boolean bHasPathProcessed=false;
			vecReturn = this.computeAsExpressionFirstVector(sLineWithExpression);	// <Z> Tags am Rand aussen entfernen	
			String sExpression = (String) vecReturn.get(1);									
			if(!StringZZZ.isEmpty(sExpression)){
				
				//ZUERST: Löse ggfs. übergebene Variablen auf.
				ZTagFormulaIni_VariableZZZ objVariable = new ZTagFormulaIni_VariableZZZ(this.getHashMapVariable());
				String sExpressionOld = sExpression;
				while(objVariable.isExpression(sExpressionOld)){
					//Nein, dann werden ggfs. Werte vor und nach dem Ausdruck unterschlagen
					//objVariable.compute(sExpressionOld);
					//Also: Einen Vektor holen....
					Vector<String> vecExpression = objVariable.parseFirstVector(sExpressionOld);
					
					sExpression = vecExpression.get(1); //Die umgebenden Werte aber auch noch sichern fuer die Rueckgabe
					if(!StringZZZ.equals(sExpression,sExpressionOld)){
						System.out.println(ReflectCodeZZZ.getPositionCurrent()+ ": Value durch FormulaIniSolver-VARIABLE verändert von '" + sExpressionOld + "' nach '" + sExpression +"'");
					}else {
						break;
					}					

					//Die umgebenden Werte sichern
					String s0=vecReturn.get(0);
					if(vecReturn.size()>=1) vecReturn.removeElementAt(0);
					vecReturn.add(0, s0 + vecExpression.get(0));
					
					String s1 = sExpression;
					if(vecReturn.size()>=2) vecReturn.removeElementAt(1);
					vecReturn.add(1, s1);
										
					String s2=vecReturn.get(2);
					if(vecReturn.size()>=3) vecReturn.removeElementAt(2);
					vecReturn.add(2, vecExpression.get(2) + s2);
					
					sExpression = VectorZZZ.implode(vecReturn);
					sExpressionOld=sExpression;//Sonst Endlosschleife.
					bHasVariableProcessed = true;
				} //end while
					
								
				//DANACH ALLE PATH-Ausdrücke, also [xxx]yyy ersetzen
				KernelZFormulaIni_PathZZZ objIniPath = new KernelZFormulaIni_PathZZZ(this.getKernelObject(), this.getFileConfigKernelIni());
				sExpressionOld = sExpression;
				while(objIniPath.isExpression(sExpressionOld)){
											
					//Verwende wie oben computeExpressionFirstVector(sExpressionOld);
					Vector<String> vecExpression = objIniPath.parseFirstVector(sExpressionOld);//in computeAsExpression wäre Z-Tags
					sExpression = vecExpression.get(1); //Die umgebenden Werte aber auch noch sichern fuer die Rueckgabe
					if(!sExpressionOld.equals(sExpression)) {
						System.out.println(ReflectCodeZZZ.getPositionCurrent()+ ": Value durch FormulaIniSolver-PATH verändert von '" + sExpressionOld + "' nach '" + sExpression +"'");
					}else {
						break;//Sonst Endlosschleife
					}
					
					//Die umgebenden Werte sichern
					if(bHasVariableProcessed || bHasPathProcessed) {
						//wenn der 0 Index und 2 Index des Vektors schon mal beruecksichtig wurde, dann wuerde man ihn immer verdoppeln	
						String s0 = vecExpression.get(0);
						if(vecReturn.size()>=1) vecReturn.removeElementAt(0);
						vecReturn.add(0, s0);
						
						String s1 = vecExpression.get(1);
						if(vecReturn.size()>=2) vecReturn.removeElementAt(1);
						vecReturn.add(1, s1);	
						
						String s2 = vecExpression.get(2);
						if(vecReturn.size()>=3) vecReturn.removeElementAt(2);
						vecReturn.add(2, s2);
						
						sExpression = VectorZZZ.implode(vecReturn);
						sExpressionOld=sExpression;//Sonst Endlosschleife.
					}else {
						//wenn der 0 Index und 2 Index des Vektors schon mal beruecksichtig wurde, dann wuerde man ihn immer verdoppeln
						String s0 = vecReturn.get(0);
						if(vecReturn.size()>=1) vecReturn.removeElementAt(0);
						vecReturn.add(0, s0 + vecExpression.get(0));
						
						String s1 = sExpression;
						if(vecReturn.size()>=2) vecReturn.removeElementAt(1);
						vecReturn.add(1, s1);
						
						String s2 = vecReturn.get(2);
						if(vecReturn.size()>=3) vecReturn.removeElementAt(2);
						vecReturn.add(2, vecExpression.get(2) + s2);
						
						sExpression = VectorZZZ.implode(vecReturn);
						sExpressionOld=sExpression;//Sonst Endlosschleife.
					}
						
					bHasPathProcessed = true;
				} //end while
			} //end if sExpression = ""					
		}//end main:
		return vecReturn;
	}
	
	@Override
	public Vector<String>computeAsExpressionFirstVector(String sLineWithExpression) throws ExceptionZZZ{
		Vector<String>vecReturn = new Vector<String>();		
		main:{		
			//Merke: Wie beim "Cascaded" Solver die Tags "vorne und hinten abschneiden".
			//ABER: Beim "Formelausrechen" die Z-Tags im Ergebnisvector mitgeben.
			vecReturn = StringZZZ.vecMid(sLineWithExpression, this.getTagStarting(), this.getTagClosing(), true, false); //asExpression, d.h. lass z-Tags drin.
		}
		return vecReturn;
	}

	//###### Getter / Setter

	@Override
	public void setHashMapVariable(HashMapCaseInsensitiveZZZ<String,String> hmVariable){
		this.hmVariable = hmVariable;
	}
	
	@Override
	public HashMapCaseInsensitiveZZZ<String,String> getHashMapVariable(){
		return this.hmVariable;
	}
	
	@Override
	public void setVariable(HashMapCaseInsensitiveZZZ<String,String> hmVariable){
		if(this.hmVariable==null){
			this.hmVariable = hmVariable;
		}else{
			if(hmVariable==null){
				//nix....
			}else{
				//füge Werte hinzu.
				Set<String> sSet =  this.hmVariable.keySet();
				for(String sKey : sSet){
					this.hmVariable.put(sKey, (String)hmVariable.get(sKey));
				}
			}
		}
	}
	
	@Override
	public String getVariable(String sKey){
		return (String) this.getHashMapVariable().get(sKey);
	}
	
	@Override
	public String parse(String sLineWithExpression) throws ExceptionZZZ{
		String sReturn = sLineWithExpression;
		main:{
			if(StringZZZ.isEmpty(sLineWithExpression)) break main;
			if(! this.getFlag(IKernelZFormulaIniSolverZZZ.FLAGZ.USEFORMULA)) break main;
			
			//Diesen Zwischenstand fuer weitere Verarbeitungen festhalten
			IKernelConfigSectionEntryZZZ objReturn = this.getEntry();
			objReturn.setRaw(sLineWithExpression);
			
			//Vector vecAll = this.computeExpressionAllVector(sLineWithExpression);//Hole hier erst einmal die Variablen-Anweisung und danach die IniPath-Anweisungen und ersetze sie durch Werte.
			//String sExpressionWithTags = VectorZZZ.implode(vecAll); //Der String hat NICHT mehr alle Z-Tags
			
			Vector<String> vecAll = this.computeAsExpressionAllVector(sLineWithExpression);//Hole hier erst einmal die Variablen-Anweisung und danach die IniPath-Anweisungen und ersetze sie durch Werte.
			String sExpressionWithTags = VectorZZZ.implode(vecAll); //Der String hat jetzt Z-Tags
			objReturn.setValueAsExpression(sExpressionWithTags); //nicht noch andere Z-Tags rumsetzen
			
			//20180714 Hole Ausdrücke mit <z:math>...</z:math>, wenn das entsprechende Flag gesetzt ist.
			//Beispiel dafür: TileHexMap-Projekt: GuiLabelFontSize_Float
			//GuiLabelFontSize_float=<Z><Z:math><Z:val>[THM]GuiLabelFontSizeBase_float</Z:val><Z:op>*</Z:op><Z:val><z:var>GuiZoomFactorUsed</z:var></Z:val></Z:math></Z>
			if(this.getFlag(IKernelZFormulaIniSolverZZZ.FLAGZ.USEFORMULA_MATH)) {				
						
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

			//Als echten Ergebniswert aber die Z-Tags rausrechnen
			//An dieser Stelle die Tags vom akuellen "Solver" Rausnehmen
			//AUSSER: Die <Z>-Tags sind am Anfang/Ende UND(!) es sind noch andere Formel Z-Tags "<Z:... im String vorhanden 
			String sTagStart = this.getTagStarting();
			String sTagEnd = this.getTagClosing();
			String sExpression = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionWithTags, sTagStart, sTagEnd);
			
			//TODOGOON; HIER vecAll wieder ins Spiel bringen????????????????
			
			objReturn.setValue(sExpression);
			
			sReturn = sExpression;			
		}//end main:
		return sReturn;
	}
	
	//### aus IKernelZFormulaIniSolverZZZ
	@Override
	public boolean getFlag(IKernelZFormulaIniSolverZZZ.FLAGZ objEnum_IKernelZFormulaIniSolverZZZ) {
		return this.getFlag(objEnum_IKernelZFormulaIniSolverZZZ.name());
	}
	
	@Override
	public boolean setFlag(IKernelZFormulaIniSolverZZZ.FLAGZ objEnum_IKernelZFormulaIniSolverZZZ, boolean bFlagValue) throws ExceptionZZZ {
		return this.setFlag(objEnum_IKernelZFormulaIniSolverZZZ.name(), bFlagValue);
	}
	
	@Override
	public boolean[] setFlag(IKernelZFormulaIniSolverZZZ.FLAGZ[] objaEnum_IKernelZFormulaIniSolverZZZ, boolean bFlagValue) throws ExceptionZZZ {
		boolean[] baReturn=null;
		main:{
			if(!ArrayUtilZZZ.isNull(objaEnum_IKernelZFormulaIniSolverZZZ)) {
				baReturn = new boolean[objaEnum_IKernelZFormulaIniSolverZZZ.length];
				int iCounter=-1;
				for(IKernelZFormulaIniSolverZZZ.FLAGZ objEnum_IKernelZFormulaIniSolverZZZ:objaEnum_IKernelZFormulaIniSolverZZZ) {
					iCounter++;
					boolean bReturn = this.setFlag(objEnum_IKernelZFormulaIniSolverZZZ, bFlagValue);
					baReturn[iCounter]=bReturn;
				}
			}
		}//end main:
		return baReturn;
	}
	
	@Override
	public boolean proofFlagExists(IKernelZFormulaIniSolverZZZ.FLAGZ objaEnumFlag) throws ExceptionZZZ {
		return this.proofFlagExists(objaEnumFlag.name());
	}

	//### Andere Interfaces
	@Override
	public boolean isStringForConvertRelevant(String sToProof) throws ExceptionZZZ {		
		return false;
	}

	@Override
	public String getNameDefault() throws ExceptionZZZ {
	 return KernelZFormulaIniSolverZZZ.sTAG_NAME;
	}
}//End class
