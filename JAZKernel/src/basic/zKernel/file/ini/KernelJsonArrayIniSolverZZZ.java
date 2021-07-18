package basic.zKernel.file.ini;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.Vector;

import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;

import custom.zKernel.file.ini.FileIniZZZ;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractList.ArrayListExtendedZZZ;
import basic.zBasic.util.abstractList.HashMapCaseInsensitiveZZZ;
import basic.zBasic.util.abstractList.HashMapExtendedZZZ;
import basic.zBasic.util.abstractList.VectorZZZ;
import basic.zBasic.util.datatype.json.JsonEasyZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zKernel.IKernelExpressionIniZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernel.KernelUseObjectZZZ;
import basic.zKernel.KernelZZZ;

/**Diese Klasse verarbeitet ggf. Ausdruecke/Formeln in Ini-Dateien.
 *  Es kann dann in einem dieser Formeln z.B. auf den Property-Wert einer anderen Sektion zugegriffen werden. So entstehen 'dynamische' ini-Dateien.
 * @author lindhaueradmin
 *
 */
public class KernelJsonArrayIniSolverZZZ extends KernelUseObjectZZZ implements IKerneJsonIniSolverZZZ{
	private FileIniZZZ objFileIni=null;
	private HashMapCaseInsensitiveZZZ<String,String> hmVariable =null;
		
	public KernelJsonArrayIniSolverZZZ() throws ExceptionZZZ{
		String[] saFlag = {"init"};
		KernelJsonArrayIniSolverNew_(null, saFlag);
	}
	
	public KernelJsonArrayIniSolverZZZ(String[] saFlag) throws ExceptionZZZ{
		super(null, saFlag);
		KernelJsonArrayIniSolverNew_(null, saFlag);
	}
	
	public KernelJsonArrayIniSolverZZZ(FileIniZZZ objFileIni, String[] saFlag) throws ExceptionZZZ{
		super(null, saFlag);
		KernelJsonArrayIniSolverNew_(objFileIni, saFlag);
	}
	
	public KernelJsonArrayIniSolverZZZ(IKernelZZZ objKernel, String[] saFlag) throws ExceptionZZZ{
		super(objKernel, saFlag);
		KernelJsonArrayIniSolverNew_(null, saFlag);
	}
	
	public KernelJsonArrayIniSolverZZZ(IKernelZZZ objKernel, FileIniZZZ objFileIni, String[] saFlag) throws ExceptionZZZ{
		super(objKernel, saFlag);
		KernelJsonArrayIniSolverNew_(objFileIni, saFlag);
	}
		
	private boolean KernelJsonArrayIniSolverNew_(FileIniZZZ objFileIni, String[] saFlagControlIn) throws ExceptionZZZ {
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
							ExceptionZZZ ez = new ExceptionZZZ( "the flag '" + stemp + "' is not available.", iERROR_FLAG_UNAVAILABLE, this, ReflectCodeZZZ.getMethodCurrentName()); 
							throw ez;		 
						}
					}
					if(this.getFlag("init")==true){
						bReturn = true;
						break main;
					}
				}
				
				this.setFileIni(objFileIni);
							
	 	}//end main:
		return bReturn;
	 }//end function KernelJsonMapIniSolverNew_
	
	
	
	public Vector computeExpressionAllVector(String sLineWithExpression) throws ExceptionZZZ{
		Vector vecReturn = new Vector();
		main:{
			if(StringZZZ.isEmpty(sLineWithExpression)) break main;
			
			vecReturn = this.computeExpressionFirstVector(sLineWithExpression);			
			String sExpression = (String) vecReturn.get(1);									
			if(!StringZZZ.isEmpty(sExpression)){
				
				//ZUERST: Löse ggfs. übergebene Variablen auf.
				KernelExpressionIni_VariableZZZ objVariable = new KernelExpressionIni_VariableZZZ(this.getKernelObject(), this.getHashMapVariable());
				while(KernelExpressionIni_VariableZZZ.isExpression(sExpression)){
					sExpression = objVariable.compute(sExpression);			
				} //end while
					
								
				//DANACH: ALLE PATH-Ausdrücke, also [xxx]yyy ersetzen
				//Problem hier: [ ] ist auch der JSON Array-Ausdruck
				String sExpressionOld = sExpression;
				KernelExpressionIni_PathZZZ objIniPath = new KernelExpressionIni_PathZZZ(this.getKernelObject(), this.getFileIni());
				while(KernelExpressionIni_PathZZZ.isExpression(sExpression)){
						sExpression = objIniPath.compute(sExpression);	
						if(StringZZZ.isEmpty(sExpression)) {
							sExpression = sExpressionOld;
							break;
						}else{
							sExpressionOld = sExpression;							
						}
				} //end while
									
				//NUN DEN INNERHALB DER EXPRESSION BERECHUNG ERSTELLTEN WERT in den Return-Vector übernehmen
				if(vecReturn.size()>=2) vecReturn.removeElementAt(1);
				vecReturn.add(1, sExpression);
			
			} //end if sExpression = ""					
		}//end main:
		return vecReturn;
	}
	
	/** Gibt einen Vector zurück, in dem das erste Element der Ausdruck VOR der ersten 'Expression' ist. Das 2. Element ist die Expression. Das 3. Element ist der Ausdruck NACH der ersten Expression.
	* @param sLineWithExpression
	* @return
	* 
	* lindhaueradmin; 06.03.2007 11:20:34
	 * @throws ExceptionZZZ 
	 */
	public Vector computeExpressionFirstVector(String sLineWithExpression) throws ExceptionZZZ{
		Vector vecReturn = new Vector();		
		main:{
			vecReturn = StringZZZ.vecMidFirst(sLineWithExpression, KernelJsonArrayIniSolverZZZ.getExpressionTagStarting(), KernelJsonArrayIniSolverZZZ.getExpressionTagClosing(), false,false);
		}
		return vecReturn;
	}
	
	
	public static boolean isExpression(String sLine){
		boolean bReturn = false;
		main:{
			boolean btemp = StringZZZ.contains(sLine, KernelJsonArrayIniSolverZZZ.getExpressionTagStarting(),false);
			if(btemp==false) break main;
		
			btemp = StringZZZ.contains(sLine, KernelJsonArrayIniSolverZZZ.getExpressionTagClosing(),false);
			if(btemp==false) break main;
			
			bReturn = true;
		}//end main
		return bReturn;
	}
	
	
	//###### Getter / Setter
	public static String getExpressionTagName(){
		return "JSON:ARRAY";
	}
	public static String getExpressionTagStarting(){
		return "<" + KernelJsonArrayIniSolverZZZ.getExpressionTagName() + ">";
	}
	public static String getExpressionTagClosing(){
		return "</" + KernelJsonArrayIniSolverZZZ.getExpressionTagName() + ">"; 
	}
	public static String getExpressionTagEmpty(){
		return "<" + KernelJsonArrayIniSolverZZZ.getExpressionTagName() + "/>";
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
	
	/**Eine ArrayList wird zum String umgewandelt. 
	 * Das ist momentan die DEBUG-Variante, z.B. für die Ausgabe auf der Konsole.
	 * @param sLineWithExpression
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 17.07.2021, 09:06:10
	 */
	public String compute(String sLineWithExpression) throws ExceptionZZZ{
		String sReturn = null;
		main:{			
			if(StringZZZ.isEmpty(sLineWithExpression)) break main;
				
			sReturn = sLineWithExpression;
			if(this.getFlag(IKerneJsonIniSolverZZZ.FLAGZ.USEJSON.name())== false) break main;
			if(this.getFlag(IKerneJsonIniSolverZZZ.FLAGZ.USEJSON_ARRAY.name())== false) break main;
						
			ArrayList<String> alsReturn = this.computeArrayList(sLineWithExpression);
			if(alsReturn!=null) {
				sReturn = ArrayListExtendedZZZ.debugString(alsReturn);
			}
		}
		return sReturn;
	}
	
	public ArrayList<String> computeArrayList(String sLineWithExpression) throws ExceptionZZZ{
		ArrayList<String> alsReturn = new ArrayList<String>();
		main:{
			if(StringZZZ.isEmpty(sLineWithExpression)) break main;
			if(this.getFlag(IKerneJsonIniSolverZZZ.FLAGZ.USEJSON.name())== false) break main;
			if(this.getFlag(IKerneJsonIniSolverZZZ.FLAGZ.USEJSON_ARRAY.name())== false) break main;
			
			String sReturn = "";
			Vector<String> vecAll = this.computeExpressionAllVector(sLineWithExpression);//Hole hier erst einmal die Variablen-Anweisung und danach die IniPath-Anweisungen und ersetze sie durch Werte.
			
			//20180714 Hole Ausdrücke mit <z:math>...</z:math>, wenn das entsprechende Flag gesetzt ist.
			//Beispiel dafür: TileHexMap-Projekt: GuiLabelFontSize_Float
			//GuiLabelFontSize_float=<Z><Z:math><Z:val>[THM]GuiLabelFontSizeBase_float</Z:val><Z:op>*</Z:op><Z:val><z:var>GuiZoomFactorUsed</z:var></Z:val></Z:math></Z>
			
			//Beschränke das ausrechnen auf den JSON-MAP Teil  sReturn = VectorZZZ.implode(vecAll);//Erst den Vector der "übersetzten" Werte zusammensetzen
			sReturn = vecAll.get(1);
			if(this.getFlag("useFormula_math")==true){				
				//Dann erzeuge neues KernelExpressionMathSolverZZZ - Objekt.
				KernelExpressionMathSolverZZZ objMathSolver = new KernelExpressionMathSolverZZZ(); 
													
				//2. Ist in dem String math?	Danach den Math-Teil herausholen und in einen neuen vec packen.
				//Sollte das nicht für mehrerer Werte in einen Vector gepackt werden und dann immer weiter mit vec.get(1) ausgerechnet werden?
				while(objMathSolver.isExpression(sReturn)){
					String sValueMath = objMathSolver.compute(sReturn);
					sReturn=sValueMath;				
				}	
				//sReturn = VectorZZZ.implode(vecAll);
			}
			
			//ANSCHnLIESSEND die HashMap erstellen
			if(!JsonEasyZZZ.isJsonValid(sReturn)) break main;
			
			
			//JsonArray ja = JsonEasyZZZ.toJsonArray(sReturn);
			alsReturn = JsonEasyZZZ.toArrayListString(sReturn);
			
			//Verwendete Lösung für die HashMap
//			TypeToken<HashMap<String, String>> typeToken = new TypeToken<HashMap<String, String>>(){};
//			hmReturn = JsonEasyZZZ.toHashMap(typeToken, sReturn);
			
			//Alternativer, generische Lösung
//			TypeToken<ArrayList<String>> typeToken = new TypeToken<ArrayList<String>>(){};
//			alsReturn = JsonEasyZZZ.toArrayList(typeToken, sReturn);
			
			//Merke: Die Positionen vec(0) und vec(2) werden also dann entfallen.
		}//end main:
		return alsReturn;
	}
}//End class
