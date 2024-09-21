package basic.zKernel.file.ini;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Vector;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.abstractList.VectorUtilZZZ;
import basic.zBasic.util.datatype.json.JsonEasyZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernel.config.KernelConfigSectionEntryUtilZZZ;
import basic.zKernel.flag.util.FlagZFassadeZZZ;
import custom.zKernel.file.ini.FileIniZZZ;

/**Diese Klasse verarbeitet ggf. Ausdruecke/Formeln in Ini-Dateien.
 *  Es kann dann in einem dieser Formeln z.B. auf den Property-Wert einer anderen Sektion zugegriffen werden. So entstehen 'dynamische' ini-Dateien.
 * @author lindhaueradmin
 *
 */
public class KernelJsonMapIniSolverZZZ<T> extends AbstractKernelIniSolverZZZ<T> implements IKernelJsonMapIniSolverZZZ{
	public static String sTAG_NAME = "JSON:MAP";
		
	public KernelJsonMapIniSolverZZZ() throws ExceptionZZZ{
		super("init");
		KernelJsonMapIniSolverNew_(null);
	}
	
	public KernelJsonMapIniSolverZZZ(IKernelZZZ objKernel) throws ExceptionZZZ{
		super(objKernel);
		KernelJsonMapIniSolverNew_(null);
	}
	
	public KernelJsonMapIniSolverZZZ(FileIniZZZ objFileIni) throws ExceptionZZZ{
		super(objFileIni);
		KernelJsonMapIniSolverNew_(objFileIni);
	}
	
	public KernelJsonMapIniSolverZZZ(FileIniZZZ objFileIni, String[] saFlag) throws ExceptionZZZ{
		super(objFileIni, saFlag);
		KernelJsonMapIniSolverNew_(objFileIni);
	}
	
	public KernelJsonMapIniSolverZZZ(IKernelZZZ objKernel, String[] saFlag) throws ExceptionZZZ{
		super(objKernel, saFlag);
		KernelJsonMapIniSolverNew_(null);
	}
	
	public KernelJsonMapIniSolverZZZ(IKernelZZZ objKernel, FileIniZZZ objFileIni, String[] saFlag) throws ExceptionZZZ{
		super(objKernel, saFlag);
		KernelJsonMapIniSolverNew_(objFileIni);
	}
		
	private boolean KernelJsonMapIniSolverNew_(FileIniZZZ objFileIni) throws ExceptionZZZ {
	 boolean bReturn = false; 
	 main:{
			if(this.getFlag("init")==true){
				bReturn = true;
				break main;
			}
							
			this.setFileConfigKernelIni(objFileIni);		
				
			bReturn = true;
	 	}//end main:
		return bReturn;
	 }//end function KernelJsonMapIniSolverNew_
			
	//###### Getter / Setter
	
	//### Aus ITagBasicZZZ
	@Override
	public String getNameDefault() throws ExceptionZZZ {
		return KernelJsonMapIniSolverZZZ.sTAG_NAME;
	}
		
//	/**Eine HashMap wird zum String umgewandelt. 
//	 * Das ist momentan die DEBUG-Variante, z.B. für die Ausgabe auf der Konsole.
//	 * @param sLineWithExpression
//	 * @return
//	 * @throws ExceptionZZZ
//	 * @author Fritz Lindhauer, 17.07.2021, 09:06:10
//	 */
//	public String parse(String sLineWithExpression) throws ExceptionZZZ{
//		String sReturn = sLineWithExpression;
//		main:{			
//			if(StringZZZ.isEmpty(sLineWithExpression)) break main;
//				
//			sReturn = sLineWithExpression;
//			if(!this.getFlag(IKernelJsonIniSolverZZZ.FLAGZ.USEJSON)) break main;
//			if(!this.getFlag(IKernelJsonMapIniSolverZZZ.FLAGZ.USEJSON_MAP)) break main;
//			
//			HashMap<String,String> hmReturn = this.computeHashMap(sLineWithExpression);
//			if(hmReturn!=null) {
//				sReturn = HashMapExtendedZZZ.computeDebugString(hmReturn);
//			}
//		}
//		return sReturn;
//	}
//	
//	
		
	//### aus IParseEnabled
	@Override
	public Vector<String>parseFirstVector(String sLineWithExpression) throws ExceptionZZZ{
		return this.parseFirstVector_(sLineWithExpression, true);
	}
	
	//Analog zu KernelZFormulaMathSolver, KernelEncrytptionIniSolver aufbauen...
	@Override
	public Vector<String>parseFirstVector(String sLineWithExpression, boolean bRemoveSurroundingSeparators) throws ExceptionZZZ{
		return this.parseFirstVector_(sLineWithExpression, bRemoveSurroundingSeparators);
	}
	
	private Vector<String>parseFirstVector_(String sLineWithExpression, boolean bRemoveSurroundingSeparators) throws ExceptionZZZ{
		Vector<String>vecReturn = new Vector<String>();
		String sReturn = sLineWithExpression;
		boolean bUseExpression=false;
		main:{
			if(StringZZZ.isEmpty(sLineWithExpression)) break main;
			
			bUseExpression = this.getFlag(IIniTagWithExpressionZZZ.FLAGZ.USEEXPRESSION); 
			if(!bUseExpression) break main;
						
			boolean bUseSolver = this.getFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER);
			if(!bUseSolver) break main;	
			
			boolean bUseJson = this.getFlag(IKernelJsonIniSolverZZZ.FLAGZ.USEJSON);		
			if(!bUseJson) break main;
			
			boolean bUseJsonMap = this.getFlag(IKernelJsonMapIniSolverZZZ.FLAGZ.USEJSON_MAP);		
			if(!bUseJsonMap) break main;
			
			
			//Mehrere Ausdruecke. Dann muss der jeweilige "Rest-Bestandteil" des ExpressionFirst-Vectors weiter zerlegt werden.
			//Im Aufruf der Eltern-Methode findet ggfs. auch eine Aufloesung von Pfaden und eine Ersetzung von Variablen statt.
			//Z:math drumherum entfernen
			vecReturn = super.parseFirstVector(sLineWithExpression, bRemoveSurroundingSeparators);			
			String sExpression = (String) vecReturn.get(1);
			if(StringZZZ.isEmpty(sExpression)) break main;
			
			this.getEntry().setRaw(sExpression);
			
			
			//++++++++++++++++++++++++++++++
		    //Das Ziel ist, nun die JSON:MAP umzuwandeln
//				HashMap<String,String> hmReturn = this.computeHashMapFromJson(sExpression);
//				if(hmReturn!=null) {
//					sReturn = HashMapExtendedZZZ.computeDebugString(hmReturn);
//				}
			
			//Hier nur den String so zurückgeben. Für die Umwandlung in den Debug - String oder die HashMap selbst gibt es andere Methoden.
			sReturn = sExpression;
		}//end main:
		
		
		//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		//Den Wert ersetzen, wenn es was zu ersetzen gibt.
		if(sReturn!=null){
			if(vecReturn.size()==0) vecReturn.add(0,"");
			
			if(vecReturn.size()>=2) vecReturn.removeElementAt(1);
			if(!StringZZZ.isEmpty(sReturn)){
				vecReturn.add(1, sReturn);
			}else {
				vecReturn.add(1, "");
			}
			
			if(vecReturn.size()==2) vecReturn.add(2,"");			
		}	
		this.setValue(sReturn);
		
		// Z-Tags entfernen.
		if(bRemoveSurroundingSeparators) {
			if(bUseExpression) {
				String sTagStartZ = "<Z>";
				String sTagEndZ = "</Z>";
				KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(vecReturn, sTagStartZ, sTagEndZ);
			}
		}	
		return vecReturn;
	}
	
	
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
						
					String sValueMath = objMathSolver.parse(sReturn);										
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
			
			//ANSCHLIESSEND die HashMap erstellen
			if(!JsonEasyZZZ.isJsonValid(sReturn)) break main;
			
			//Merke: Die Reihenfolge nicht berücksichtige		
			hmReturn = JsonEasyZZZ.toHashMap(sReturn);
			
			//Merke: Die Positionen vec(0) und vec(2) werden also dann entfallen.
		}//end main:
		return hmReturn;
	}
	
	@SuppressWarnings("unchecked")
	public HashMap<String,String> computeLinkedHashMap(String sLineWithExpression) throws ExceptionZZZ{
		LinkedHashMap<String,String> hmReturn = new LinkedHashMap<String,String>();
		main:{
			if(StringZZZ.isEmpty(sLineWithExpression)) break main;
			if(this.getFlag(IKernelJsonIniSolverZZZ.FLAGZ.USEJSON.name())== false) break main;
			if(this.getFlag(IKernelJsonMapIniSolverZZZ.FLAGZ.USEJSON_MAP)== false) break main;
			
			String sReturn = "";
			Vector<String> vecAll = this.parseFirstVector(sLineWithExpression);//Hole hier erst einmal die Variablen-Anweisung und danach die IniPath-Anweisungen und ersetze sie durch Werte.
			
			//20180714 Hole Ausdrücke mit <z:math>...</z:math>, wenn das entsprechende Flag gesetzt ist.
			//Beispiel dafür: TileHexMap-Projekt: GuiLabelFontSize_Float
			//GuiLabelFontSize_float=<Z><Z:math><Z:val>[THM]GuiLabelFontSizeBase_float</Z:val><Z:op>*</Z:op><Z:val><z:var>GuiZoomFactorUsed</z:var></Z:val></Z:math></Z>
			
			//Beschränke das ausrechnen auf den JSON-MAP Teil  sReturn = VectorZZZ.implode(vecAll);//Erst den Vector der "übersetzten" Werte zusammensetzen
			sReturn = vecAll.get(1);
			if(this.getFlag("useFormula_math")==true){				
				//Dann erzeuge neues KernelExpressionMathSolverZZZ - Objekt.
				KernelZFormulaMathSolverZZZ objMathSolver = new KernelZFormulaMathSolverZZZ(); 
													
				//2. Ist in dem String math?	Danach den Math-Teil herausholen und in einen neuen vec packen.
				//Sollte das nicht für mehrerer Werte in einen Vector gepackt werden und dann immer weiter mit vec.get(1) ausgerechnet werden?
				while(objMathSolver.isExpression(sReturn)){
					String sValueMath = objMathSolver.parse(sReturn);
					sReturn=sValueMath;				
				}	
				//sReturn = VectorZZZ.implode(vecAll);
			}
			
			//ANSCHLIESSEND die HashMap erstellen
			if(!JsonEasyZZZ.isJsonValid(sReturn)) break main;
			
			//Ziel: Die Reihenfolge berücksichtige		
			hmReturn = (LinkedHashMap<String, String>) JsonEasyZZZ.toLinkedHashMap(sReturn);
			
			//Merke: Die Positionen vec(0) und vec(2) werden also dann entfallen.
		}//end main:
		return hmReturn;
	}

	//### Andere Interfaces
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
