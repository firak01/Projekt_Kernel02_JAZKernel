package basic.zKernel.file.ini;

import java.util.ArrayList;
import java.util.Vector;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.abstractList.ArrayListExtendedZZZ;
import basic.zBasic.util.datatype.calling.ReferenceZZZ;
import basic.zBasic.util.datatype.json.JsonEasyZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zKernel.IKernelConfigSectionEntryZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernel.flag.IFlagZUserZZZ;
import custom.zKernel.file.ini.FileIniZZZ;

/**Diese Klasse verarbeitet ggf. Ausdruecke/Formeln in Ini-Dateien.
 *  Es kann dann in einem dieser Formeln z.B. auf den Property-Wert einer anderen Sektion zugegriffen werden. So entstehen 'dynamische' ini-Dateien.
 * @author lindhaueradmin
 * @param <T>
 *
 */
public class KernelJsonArrayIniSolverZZZ<T> extends AbstractKernelIniSolverZZZ<T> implements IKernelJsonArrayIniSolverZZZ{
	private static final long serialVersionUID = 8458180798440285715L;
	public static String sTAG_NAME = "JSON:ARRAY";
			
	public KernelJsonArrayIniSolverZZZ() throws ExceptionZZZ{
		super("init");
		KernelJsonArrayIniSolverNew_(null);
	}
	
	public KernelJsonArrayIniSolverZZZ(IKernelZZZ objKernel) throws ExceptionZZZ{
		super(objKernel);
		KernelJsonArrayIniSolverNew_(null);
	}
	
	public KernelJsonArrayIniSolverZZZ(FileIniZZZ objFileIni) throws ExceptionZZZ{
		super(objFileIni);
		KernelJsonArrayIniSolverNew_(objFileIni);
	}
	
	public KernelJsonArrayIniSolverZZZ(FileIniZZZ objFileIni, String[] saFlag) throws ExceptionZZZ{
		super(objFileIni, saFlag);
		KernelJsonArrayIniSolverNew_(objFileIni);
	}
	
	public KernelJsonArrayIniSolverZZZ(IKernelZZZ objKernel, String[] saFlag) throws ExceptionZZZ{
		super(objKernel, saFlag);
		KernelJsonArrayIniSolverNew_(null);
	}
	
	public KernelJsonArrayIniSolverZZZ(IKernelZZZ objKernel, FileIniZZZ objFileIni, String[] saFlag) throws ExceptionZZZ{
		super(objKernel, saFlag);
		KernelJsonArrayIniSolverNew_(objFileIni);
	}
		
	private boolean KernelJsonArrayIniSolverNew_(FileIniZZZ objFileIni) throws ExceptionZZZ {
	 boolean bReturn = false;	
	 main:{
			if(this.getFlag("init")==true){
				bReturn = true;
				break main;
			}
					
			if(objFileIni==null) {
				//Kann ggfs. später aus dem Kernel-Objekt geholt werden.							
			}else {
				this.setFileConfigKernelIni(objFileIni);
				if(this.getKernelObject()==null) this.setKernelObject(objFileIni.getKernelObject());
			}
										
	 	}//end main:
		return bReturn;
	 }//end function KernelJsonMapIniSolverNew_
		
	//###### Getter / Setter
	
	
	public String getNameDefault(){
		return KernelJsonArrayIniSolverZZZ.sTAG_NAME;
	}

	/**Eine ArrayList wird zum String umgewandelt. 
	 * Das ist momentan die DEBUG-Variante, z.B. für die Ausgabe auf der Konsole.
	 * @param sLineWithExpression
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 17.07.2021, 09:06:10
	 */
	@Override
	public String parse(String sLineWithExpression) throws ExceptionZZZ{
		return this.parse_(sLineWithExpression);
	}
	
	private String parse_(String sLineWithExpression) throws ExceptionZZZ{
		String sReturn = sLineWithExpression;
		main:{			
			if(StringZZZ.isEmpty(sLineWithExpression)) break main;
				
			sReturn = sLineWithExpression;
			if(!this.getFlag(IKernelJsonIniSolverZZZ.FLAGZ.USEJSON)) break main;
			if(!this.getFlag(IKernelJsonArrayIniSolverZZZ.FLAGZ.USEJSON_ARRAY)) break main;
						
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
			if(!this.getFlag(IKernelJsonIniSolverZZZ.FLAGZ.USEJSON)) break main;
			if(!this.getFlag(IKernelJsonArrayIniSolverZZZ.FLAGZ.USEJSON_ARRAY)) break main;
			
			String sReturn = "";
			Vector<String> vecAll = this.parseFirstVector(sLineWithExpression);//Hole hier erst einmal die Variablen-Anweisung und danach die IniPath-Anweisungen und ersetze sie durch Werte.
			
			//20180714 Hole Ausdrücke mit <z:math>...</z:math>, wenn das entsprechende Flag gesetzt ist.
			//Beispiel dafür: TileHexMap-Projekt: GuiLabelFontSize_Float
			//GuiLabelFontSize_float=<Z><Z:math><Z:val>[THM]GuiLabelFontSizeBase_float</Z:val><Z:op>*</Z:op><Z:val><z:var>GuiZoomFactorUsed</z:var></Z:val></Z:math></Z>
			
			//Beschränke das ausrechnen auf den JSON-Array Teil  sReturn = VectorZZZ.implode(vecAll);//Erst den Vector der "übersetzten" Werte zusammensetzen
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
			
			//ANSCHLIESSEND die ArrayList erstellen
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
	
	//######### Interfaces #######################################################
	
		
	//### Andere Interfaces
	@Override
	public boolean isConvertRelevant(String sToProof) throws ExceptionZZZ {			
		return false;
	}
	
	
	//########################################################################
	//### FLAG - Handling
	
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
}//End class
