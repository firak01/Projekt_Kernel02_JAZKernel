package basic.zKernel.file.ini;

import java.io.File;
import java.io.IOException;
import java.util.Set;
import java.util.Vector;

import custom.zKernel.file.ini.FileIniZZZ;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.abstractList.HashMapCaseInsensitiveZZZ;
import basic.zBasic.util.abstractList.VectorZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zKernel.IKernelZFormulaIniZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernel.KernelUseObjectZZZ;
import basic.zKernel.KernelZZZ;
import basic.zKernel.flag.IFlagZUserZZZ;

/**Diese Klasse verarbeitet ggf. Ausdruecke/Formeln in Ini-Dateien.
 *  Es kann dann in einem dieser Formeln z.B. auf den Property-Wert einer anderen Sektion zugegriffen werden. So entstehen 'dynamische' ini-Dateien.
 * @author lindhaueradmin
 *
 */
public class KernelZFormulaIniSolverZZZ extends AbstractKernelIniSolverZZZ implements IKernelZFormulaIniSolverZZZ{
	public static String sTAG_NAME = "Z";
	private FileIniZZZ objFileIni=null;
	private HashMapCaseInsensitiveZZZ<String,String> hmVariable =null;
	
	public KernelZFormulaIniSolverZZZ() throws ExceptionZZZ{
		super();
		KernelExpressionIniSolverNew_(null, null, null);
	}
	
	public KernelZFormulaIniSolverZZZ(FileIniZZZ objFileIni) throws ExceptionZZZ{
		super(objFileIni.getKernelObject());
		KernelExpressionIniSolverNew_(objFileIni, null, null);
	}
	
	public KernelZFormulaIniSolverZZZ(FileIniZZZ objFileIni, String[] saFlag) throws ExceptionZZZ{
		super(objFileIni.getKernelObject());
		KernelExpressionIniSolverNew_(objFileIni, null, saFlag);
	}
	
	public KernelZFormulaIniSolverZZZ(IKernelZZZ objKernel, FileIniZZZ objFileIni, String[] saFlag) throws ExceptionZZZ{
		super(objKernel);
		KernelExpressionIniSolverNew_(objFileIni, null, saFlag);
	}
	
	public KernelZFormulaIniSolverZZZ(FileIniZZZ objFileIni, HashMapCaseInsensitiveZZZ<String,String> hmVariable) throws ExceptionZZZ{
		super(objFileIni.getKernelObject());
		KernelExpressionIniSolverNew_(objFileIni, hmVariable, null);
	}
	
	public KernelZFormulaIniSolverZZZ(FileIniZZZ objFileIni, HashMapCaseInsensitiveZZZ<String,String> hmVariable, String[] saFlag) throws ExceptionZZZ{
		super(objFileIni.getKernelObject());
		KernelExpressionIniSolverNew_(objFileIni, hmVariable, saFlag);
	}
	
	public KernelZFormulaIniSolverZZZ(IKernelZZZ objKernel, FileIniZZZ objFileIni, HashMapCaseInsensitiveZZZ<String,String> hmVariable, String[] saFlag) throws ExceptionZZZ{
		super(objKernel);
		KernelExpressionIniSolverNew_(objFileIni, hmVariable, saFlag);
	}
	
	
	private boolean KernelExpressionIniSolverNew_(FileIniZZZ objFileIn, HashMapCaseInsensitiveZZZ hmVariable, String[] saFlagControlIn) throws ExceptionZZZ {
	 boolean bReturn = false;
	 String stemp; boolean btemp; 
	 main:{
		 	
	 	//try{	 		
	 			//setzen der übergebenen Flags	
				if(saFlagControlIn != null){
					for(int iCount = 0;iCount<=saFlagControlIn.length-1;iCount++){
						stemp = saFlagControlIn[iCount];
						btemp = setFlag(stemp, true);
						if(btemp==false){
							ExceptionZZZ ez = new ExceptionZZZ( "the flag '" + stemp + "' is not available.", IFlagZUserZZZ.iERROR_FLAG_UNAVAILABLE, this, ReflectCodeZZZ.getMethodCurrentName()); 
							throw ez;		 
						}
					}					
				}
				if(this.getFlag("init")==true){
					bReturn = true;
					break main;
				}
			
				if(objFileIn==null ){
					ExceptionZZZ ez = new ExceptionZZZ("FileIni-Object", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
					throw ez; 
				}else{
					this.setFileIni(objFileIn);	
					if(objFileIn.getHashMapVariable()!=null){
						this.setHashMapVariable(objFileIn.getHashMapVariable());
					}
				}
				
				if(hmVariable!=null){				
						this.setVariable(hmVariable);			//soll zu den Variablen aus derm Ini-File hinzuaddieren, bzw. ersetzen		
				}
	 	}//end main:
		return bReturn;
	 }//end function KernelExpressionIniSolverNew_
	
	
	/** Gibt einen Vector zurück, in dem das erste Element der Ausdruck VOR der ersten 'Expression' ist. Das 2. Element ist die Expression. Das 3. Element ist der Ausdruck NACH der ersten Expression.
	* @param sLineWithExpression
	* @return
	* 
	* lindhaueradmin; 06.03.2007 11:20:34
	 * @throws ExceptionZZZ 
	 */
	public Vector<String>computeExpressionFirstVector(String sLineWithExpression) throws ExceptionZZZ{
		Vector<String>vecReturn = new Vector();		
		main:{		
			//Merke: Wie beim "Cascaded" Solver die Tags "vorne und hinten abschneiden".
			//ABER: Beim "Formelausrechen" die Z-Tags im Ergebnisvector mitgeben.
			vecReturn = StringZZZ.vecMid(sLineWithExpression, this.getExpressionTagStarting(), this.getExpressionTagClosing(), true, false);
			
			//Merke: Das ist zwar ein "Cascaded" Solver, aber hier die Tags wie beim einfachen Solver nehmen. Also "aufeinander folgend".
			//Bei dem einfachen Tag wird die naechste Tag genommen und dann auch das naeste schliessende Tag...
			//vecReturn = StringZZZ.vecMidFirst(sLineWithExpression, this.getExpressionTagStarting(), this.getExpressionTagClosing(), false, false);
			
		}
		return vecReturn;
	}
	
	public Vector<String> computeExpressionAllVector(String sLineWithExpression) throws ExceptionZZZ{
		Vector<String> vecReturn = new Vector<String>();
		main:{
			if(StringZZZ.isEmpty(sLineWithExpression)) break main;			
			vecReturn = this.computeExpressionFirstVector(sLineWithExpression);	// <Z> Tags entfernen	
			String sExpression = (String) vecReturn.get(1);									
			if(!StringZZZ.isEmpty(sExpression)){
				
				//ZUERST: Löse ggfs. übergebene Variablen auf.
				KernelZFormulaIni_VariableZZZ objVariable = new KernelZFormulaIni_VariableZZZ(this.getKernelObject(), this.getHashMapVariable());
				while(objVariable.isExpression(sExpression)){
					sExpression = objVariable.compute(sExpression);			
				} //end while
					
								
				//DANACH ALLE PATH-Ausdrücke, also [xxx]yyy ersetzen
				KernelZFormulaIni_PathZZZ objIniPath = new KernelZFormulaIni_PathZZZ(this.getKernelObject(), this.getFileIni());
				String sExpressionOld = sExpression;
				while(KernelZFormulaIni_PathZZZ.isExpression(sExpression)){
						sExpression = objIniPath.compute(sExpression);//in computeAsExpression wäre Z-Tags
						if(sExpressionOld.equals(sExpression)) break;//Sonst Endlosschleife
						sExpressionOld = sExpression;
				} //end while
				
				//NUN DEN INNERHALB DER EXPRESSION BERECHUNG ERSTELLTEN WERT in den Return-Vector übernehmen
				if(!StringZZZ.isEmpty(sExpression)){
					String sBefore = vecReturn.get(0);
					
					//Dann hat man auch den Fall, dass dies Bestandteil einer Formel ist. Also den Wert vorher und den Rest in den Vektor packen
					if(!StringZZZ.isEmpty(sBefore)){
						if(vecReturn.size()>=1) vecReturn.removeElementAt(0);
						//Nachbereitung: Ein ggfs. Z-Tag am Ende entfernen
						//Hier: Nur dann, wenn es nicht der String selber ist.
						if(!sBefore.equals("<Z>") & StringZZZ.endsWithIgnoreCase(sBefore, "<Z>")) {
							sBefore = StringZZZ.leftback(sBefore, "<Z>");
						}
						vecReturn.add(0, sBefore);
					}else{
						vecReturn.add(0,"");
					}
															
					if(vecReturn.size()>=2) vecReturn.removeElementAt(1);
					vecReturn.add(1, sExpression);
					
					String sRest = vecReturn.get(2);					
					if(!StringZZZ.isEmpty(sRest)){	
						if(vecReturn.size()>=2) vecReturn.removeElementAt(2);
						
						//Nachbereitung: Ein ggfs. /Z-Tag am Anfang des Rest entfernen
						//Hier: Nur dann, wenn es nicht der String selber ist.
						if(!sRest.equals("</Z>") & StringZZZ.startsWithIgnoreCase(sRest, "</Z>")) {
							sRest = StringZZZ.rightback(sRest, "</Z>");
						}
						vecReturn.add(2, sRest); //Falls vorhanden einen Restwert eintragen.
					}else{
						vecReturn.add(2,"");
					}		
			}//end if sValue!=null
			} //end if sExpression = ""					
		}//end main:
		return vecReturn;
	}

	//###### Getter / Setter
	public String getExpressionTagName(){
		return KernelZFormulaIniSolverZZZ.sTAG_NAME;
	}
	
	public void setFileIni(FileIniZZZ objFileIni){
		this.objFileIni = objFileIni;
	}
	public FileIniZZZ getFileIni(){
		return this.objFileIni;
	}
	
	public void setHashMapVariable(HashMapCaseInsensitiveZZZ<String,String> hmVariable){
		this.hmVariable = hmVariable;
	}
	public HashMapCaseInsensitiveZZZ<String,String> getHashMapVariable(){
		return this.hmVariable;
	}
	
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
	
	public String getVariable(String sKey){
		return (String) this.getHashMapVariable().get(sKey);
	}
	
	public String compute(String sLineWithExpression) throws ExceptionZZZ{
		String sReturn = sLineWithExpression;
		main:{
			if(StringZZZ.isEmpty(sLineWithExpression)) break main;
			if(! this.getFlag(IKernelZFormulaIniSolverZZZ.FLAGZ.USEFORMULA)) break main;
			
			Vector vecAll = this.computeExpressionAllVector(sLineWithExpression);//Hole hier erst einmal die Variablen-Anweisung und danach die IniPath-Anweisungen und ersetze sie durch Werte.
			sReturn = VectorZZZ.implode(vecAll);
			
			//20180714 Hole Ausdrücke mit <z:math>...</z:math>, wenn das entsprechende Flag gesetzt ist.
			//Beispiel dafür: TileHexMap-Projekt: GuiLabelFontSize_Float
			//GuiLabelFontSize_float=<Z><Z:math><Z:val>[THM]GuiLabelFontSizeBase_float</Z:val><Z:op>*</Z:op><Z:val><z:var>GuiZoomFactorUsed</z:var></Z:val></Z:math></Z>
			if(!this.getFlag(IKernelZFormulaIniSolverZZZ.FLAGZ.USEFORMULA_MATH)) break main;				
						
			//Dann erzeuge neues KernelExpressionMathSolverZZZ - Objekt.
			KernelZFormulaMathSolverZZZ objMathSolver = new KernelZFormulaMathSolverZZZ(); 
												
			//2. Ist in dem String math?	Danach den Math-Teil herausholen und in einen neuen vec packen.
			while(objMathSolver.isExpression(sReturn)){
				String sValueMath = objMathSolver.compute(sReturn);
				sReturn=sValueMath;				
			}																
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
			if(!ArrayUtilZZZ.isEmpty(objaEnum_IKernelZFormulaIniSolverZZZ)) {
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
	public String convert(String sLine) throws ExceptionZZZ {		
		return null;
	}

	@Override
	public boolean isStringForComputeRelevant(String sExpressionToProof) throws ExceptionZZZ {		
		return false;
	}
}//End class
