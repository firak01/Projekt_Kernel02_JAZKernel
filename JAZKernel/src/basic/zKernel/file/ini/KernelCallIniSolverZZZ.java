package basic.zKernel.file.ini;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.ReflectUtilZZZ;
import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.abstractList.ArrayListExtendedZZZ;
import basic.zBasic.util.abstractList.HashMapCaseInsensitiveZZZ;
import basic.zBasic.util.abstractList.HashMapExtendedZZZ;
import basic.zBasic.util.abstractList.VectorZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.datatype.xml.XmlUtilZZZ;
import basic.zKernel.IKernelConfigSectionEntryZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernel.KernelConfigSectionEntryZZZ;
import basic.zKernel.flag.util.FlagZFassadeZZZ;
import custom.zKernel.file.ini.FileIniZZZ;

/**Diese Klasse verarbeitet ggf. Ausdruecke/Formeln in Ini-Dateien.
 *  Es kann dann in einem dieser Formeln z.B. auf den Property-Wert einer anderen Sektion zugegriffen werden. So entstehen 'dynamische' ini-Dateien.
 * @author lindhaueradmin
 *
 */
public class KernelCallIniSolverZZZ<T> extends AbstractKernelIniSolverZZZ<T> implements IKernelCallIniSolverZZZ, IKernelJavaCallIniSolverZZZ{
	private static final long serialVersionUID = -8017698515311079738L;
	public static String sTAG_NAME = "Z:Call";
	private HashMapCaseInsensitiveZZZ<String,String> hmVariable =null;
	
	
	public KernelCallIniSolverZZZ() throws ExceptionZZZ{
		super("init");
		KernelCallIniSolverNew_(null);
	}
	
	public KernelCallIniSolverZZZ(IKernelZZZ objKernel, String[]saFlag) throws ExceptionZZZ{		
		super(objKernel,saFlag);
		KernelCallIniSolverNew_(null);
	}
	
	public KernelCallIniSolverZZZ(FileIniZZZ objFileIni) throws ExceptionZZZ{
		super(objFileIni.getKernelObject());
		KernelCallIniSolverNew_(objFileIni);
	}
	
	public KernelCallIniSolverZZZ(FileIniZZZ objFileIni, String[] saFlag) throws ExceptionZZZ{
		super(objFileIni.getKernelObject(), saFlag);
		KernelCallIniSolverNew_(objFileIni);
	}
	
	public KernelCallIniSolverZZZ(IKernelZZZ objKernel, FileIniZZZ objFileIni, String[] saFlag) throws ExceptionZZZ{
		super(objKernel);
		KernelCallIniSolverNew_(objFileIni);
	}
			
	private boolean KernelCallIniSolverNew_(FileIniZZZ objFileIn) throws ExceptionZZZ {
	 boolean bReturn = false;	
	 main:{ 		
			if(this.getFlag("init")==true){
				bReturn = true;
				break main;
			}
			
			FileIniZZZ objFile=null;
			if(objFileIn==null ){
				objFile = this.getKernelObject().getFileConfigKernelIni();
				if(objFile==null) {
					ExceptionZZZ ez = new ExceptionZZZ("FileIni-Object", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
					throw ez; 
				}
			}else {
				objFile = objFileIn;
			}
			
			this.setFileConfigKernelIni(objFile);	
			if(objFile.getHashMapVariable()!=null){
				this.setHashMapVariable(objFile.getHashMapVariable());			
			}
	 	}//end main:
		return bReturn;
	 }//end function KernelJsonIniSolverNew_
					
	//###### Getter / Setter
	@Override
	public String getNameDefault(){
		return KernelCallIniSolverZZZ.sTAG_NAME;
	}
	
	public void setHashMapVariable(HashMapCaseInsensitiveZZZ<String,String> hmVariable){
		this.hmVariable = hmVariable;
	}
	public HashMapCaseInsensitiveZZZ<String,String> getHashMapVariable(){
		return this.hmVariable;
	}
	
	public String parse(String sLineWithExpression) throws ExceptionZZZ {
		String sReturn = sLineWithExpression;
		main:{
			if(StringZZZ.isEmpty(sLineWithExpression)) break main;
			if(this.getFlag(IKernelCallIniSolverZZZ.FLAGZ.USECALL)==false) break main;
			
			//1. Versuch als Array
			ArrayList<String> als = this.computeArrayList(sLineWithExpression);
			if(!als.isEmpty()) {
				sReturn = ArrayListExtendedZZZ.debugString(als);
				break main;
			}
			
			//2. Versuch als HashMap
			HashMap<String,String>hm = this.computeHashMap(sLineWithExpression);
			if(!hm.isEmpty()) {
				sReturn = HashMapExtendedZZZ.computeDebugString(hm);
				break main;
			}
			
			//3. Versuch als Einzelwert
			if(this.getFlag(IKernelJavaCallIniSolverZZZ.FLAGZ.USECALL_JAVA)==false) break main;
			
			//WICHTIG: DIE FLAGS VERERBEN !!!
			KernelJavaCallIniSolverZZZ init4FlagLookup = new KernelJavaCallIniSolverZZZ();
			String[] saFlagZpassed = FlagZFassadeZZZ.seekFlagZrelevantForObject(this, init4FlagLookup, true);
			HashMapCaseInsensitiveZZZ<String,String>hmVariable = this.getHashMapVariable();
			
			//FileIniZZZ objFileIni = this.getFileIni();
			IKernelZZZ objKernel = this.getKernelObject();
			
			//Dann erzeuge neues KernelJavaCallSolverZZZ - Objekt.				
			KernelJavaCallIniSolverZZZ objJavaCallSolver = new KernelJavaCallIniSolverZZZ(objKernel, saFlagZpassed); 
			sReturn=objJavaCallSolver.parse(sLineWithExpression);		
										
		}
		return sReturn;
	}
			
	public HashMap<String,String> computeHashMap(String sLineWithExpression) throws ExceptionZZZ{
		HashMap<String,String>hmReturn=new HashMap<String,String>();				
		main:{
			if(StringZZZ.isEmpty(sLineWithExpression)) break main;
			if(this.getFlag(IKernelCallIniSolverZZZ.FLAGZ.USECALL)==false) break main; 			
			if(this.getFlag(IKernelJavaCallIniSolverZZZ.FLAGZ.USECALL_JAVA)==true){				
		
				//WICHTIG: DIE FLAGS VERERBEN !!!
				KernelJavaCallIniSolverZZZ init4FlagLookup = new KernelJavaCallIniSolverZZZ();
				String[] saFlagZpassed = FlagZFassadeZZZ.seekFlagZrelevantForObject(init4FlagLookup, this, true);
				HashMapCaseInsensitiveZZZ<String,String>hmVariable = this.getHashMapVariable();
				
				//FileIniZZZ objFileIni = this.getFileIni();
				IKernelZZZ objKernel = this.getKernelObject();
				
				//Dann erzeuge neues KernelJavaCallSolverZZZ - Objekt.				
				KernelJavaCallIniSolverZZZ objJavaCallSolver = new KernelJavaCallIniSolverZZZ(objKernel, saFlagZpassed); 
				//hmReturn=objJavaCallSolver.computeHashMap(sLineWithExpression);			
			}				
		}//end main:
		return hmReturn;
	}
	
	public ArrayList<String> computeArrayList(String sLineWithExpression) throws ExceptionZZZ{
		ArrayList<String>alsReturn=new ArrayList<String>();				
		main:{
			if(StringZZZ.isEmpty(sLineWithExpression)) break main;
			if(this.getFlag(IKernelCallIniSolverZZZ.FLAGZ.USECALL)==false) break main; 
			
			
			if(this.getFlag(IKernelJavaCallIniSolverZZZ.FLAGZ.USECALL_JAVA)==true){				
		
				//Dann erzeuge neues KernelJsonArraySolverZZZ - Objekt.
				KernelJavaCallIniSolverZZZ objSolver = new KernelJavaCallIniSolverZZZ(); 
				//alsReturn=objSolver.computeArrayList(sLineWithExpression);															
			}					
		}//end main:
		return alsReturn;
	}
	
	@Override
	public IKernelConfigSectionEntryZZZ parseAsEntry(String sLineWithExpression) throws ExceptionZZZ {
		IKernelConfigSectionEntryZZZ objReturn = new KernelConfigSectionEntryZZZ(); //Hier schon die Rückgabe vorbereiten, falls eine weitere Verarbeitung nicht konfiguriert ist.
		main:{			
			boolean bUseCall = this.getFlag(IKernelCallIniSolverZZZ.FLAGZ.USECALL);
			if(bUseCall) {
				objReturn = super.parseAsEntry(sLineWithExpression);	
				
				//Speichere nun das USECALL - Ergebnis auch ab.
				objReturn.setValueCallSolvedAsExpression(objReturn.getValueAsExpression());				
			}else {
				objReturn.setValue(sLineWithExpression);
				objReturn.setValueCallSolvedAsExpression(null);
			}									
		}//end main:
		return objReturn;
	}
	
	/* (non-Javadoc)
	 * @see basic.zKernel.file.ini.AbstractKernelIniSolverZZZ#computeAsExpression(java.lang.String)
	 */
	@Override
	public String parseAsExpression(String sLineWithExpression) throws ExceptionZZZ{
		String sReturn = sLineWithExpression;
		main:{			
			boolean bUseCall = this.getFlag(IKernelCallIniSolverZZZ.FLAGZ.USECALL);
			if(bUseCall) {
				sReturn = super.parseAsExpression(sLineWithExpression);
			}else {
				sReturn = sLineWithExpression;
			}									
		}//end main:
		return sReturn;
	}
	
	//### Andere Interfaces
	/**Methode ersetzt in der Zeile alle CALL Werte.
	 * Methode überschreibt den abstrakten "solver", weil erst einmal keine Pfade oder Variablen ersetzt werden sollen.
	 * @param sLineWithExpression
	 * @param objEntryReference
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 27.04.2023, 15:28:40
	 */
	public Vector<String>solveFirstVector(String sLineWithExpression) throws ExceptionZZZ{
		Vector<String> vecReturn = new Vector<String>();
		main:{
			if(StringZZZ.isEmpty(sLineWithExpression)) break main;
					
			String sExpression = null; boolean bAnyCall = false; boolean bAnyJavaCall = false; boolean bAnyJavaClass = false; boolean bAnyJavaMethod = false;
						
			//Fuer den abschliessenden Aufruf selbst.
			String sClassnameWithPackage=null; String sMethodname=null;
						
			vecReturn = this.parseFirstVector(sLineWithExpression);//Alle Z:Call Ausdruecke ersetzen						
			String sLineWithExpression2 = (String) vecReturn.get(1);
			if(!StringZZZ.isEmpty(sLineWithExpression2)& !sLineWithExpression.equals(sLineWithExpression2)) {
				bAnyCall = true;
			}
			
			
			//Dazwischen müsste eigentlich noch ein KernelJavaCallIniSolver stehen
            //Aber jetzt entfernen wir das einfach so:
			//Bei dem verschachtelten Tag werden die äußeren Tags genommen...
			Vector<String>vecReturn2 = StringZZZ.vecMid(sLineWithExpression2, "<Z:Java>", "</Z:Java>", false, false);//Alle z:Java Ausdruecke ersetzen
			String sLineWithExpression3=VectorZZZ.implode(vecReturn2);
			if(!StringZZZ.isEmpty(sLineWithExpression3)& !sLineWithExpression2.equals(sLineWithExpression3)) {
				bAnyJavaCall = true;
			}
			
			
			if(!StringZZZ.isEmpty(sLineWithExpression3)){
				
				//++++++++++++++++++++++++++++++++++++++++++++				
				sExpression = sLineWithExpression3;
				String sExpressionOld = sExpression;
				KernelJavaCall_ClassZZZ objClassname = new KernelJavaCall_ClassZZZ();
				while(objClassname.isExpression(sExpression)){
						IKernelConfigSectionEntryZZZ objEntry = objClassname.parseAsEntry(sLineWithExpression2);	
						sExpression = objEntry.getValue();
						if(StringZZZ.isEmpty(sExpression)) {
							sExpression = sExpressionOld;
							break;
						}else{
							sExpressionOld = sExpression;
							sClassnameWithPackage = sExpression;							
							bAnyJavaClass=true;
						}
				} //end while
			}
			
							
			if(!StringZZZ.isEmpty(sLineWithExpression3)){
				
				//++++++++++++++++++++++++++++++++++++++++++++++
				sExpression = sLineWithExpression3;
				String sExpressionOld = sExpression;
				KernelJavaCall_MethodZZZ objMethodname = new KernelJavaCall_MethodZZZ();
				while(objMethodname.isExpression(sExpression)){
						IKernelConfigSectionEntryZZZ objEntry = objMethodname.parseAsEntry(sExpression);
						sExpression = objEntry.getValue();
						if(StringZZZ.isEmpty(sExpression)) {
							sExpression = sExpressionOld;
							break;
						}else{
							sExpressionOld = sExpression;
							sMethodname = sExpression;							
							bAnyJavaMethod=true;
						}
				} //end while
				
				if(bAnyCall) {
					this.getEntry().setCallingClassname(sClassnameWithPackage);
					this.getEntry().setCallingMethodname(sMethodname);
					this.getEntry().isCall(true);					
				}
				if(bAnyJavaCall) {
					this.getEntry().isJavaCall(true);
				}
				
				
				//Abschliessend die Berechung durchführen.
				Object objValue = ReflectUtilZZZ.invokeStaticMethod(sClassnameWithPackage, sMethodname);
				if(objValue!=null) {
					sExpression = objValue.toString();					
				}else {
					sExpression = XmlUtilZZZ.computeTagEmpty(KernelZFormulaIni_EmptyZZZ.sTAG_NAME);
				}
								
				//NUN DEN INNERHALB DER EXPRESSION BERECHUNG ERSTELLTEN WERT in den Return-Vector übernehmen
				if(vecReturn.size()>=2) vecReturn.removeElementAt(1);
				vecReturn.add(1, sExpression);

			} //end if sExpression = ""					
		}//end main:
		return vecReturn;
	
	}

	@Override
	public boolean isStringForConvertRelevant(String sToProof) throws ExceptionZZZ {			
		return false;
	}

	@Override
	public String convert(String sLine) throws ExceptionZZZ {			
		return null;
	}

	@Override
	public boolean isParseRelevant(String sExpressionToProof) throws ExceptionZZZ {			
		return false;
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
		public boolean proofFlagExists(IKernelJavaCallIniSolverZZZ.FLAGZ objEnum_IKernelJavaCallIniSolverZZZ) throws ExceptionZZZ {
			return this.proofFlagExists(objEnum_IKernelJavaCallIniSolverZZZ.name());
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
}//End class
