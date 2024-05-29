package basic.zKernel.file.ini;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.Vector;

import custom.zKernel.file.ini.FileIniZZZ;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.ReflectUtilZZZ;
import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.abstractList.ArrayListExtendedZZZ;
import basic.zBasic.util.abstractList.HashMapCaseInsensitiveZZZ;
import basic.zBasic.util.abstractList.HashMapExtendedZZZ;
import basic.zBasic.util.abstractList.VectorZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zKernel.IKernelConfigSectionEntryZZZ;
import basic.zKernel.IKernelZFormulaIniZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernel.KernelConfigSectionEntryZZZ;
import basic.zKernel.AbstractKernelUseObjectZZZ;
import basic.zKernel.KernelZZZ;
import basic.zKernel.flag.IFlagZUserZZZ;
import basic.zKernel.flag.util.FlagZFassadeZZZ;

/**Diese Klasse verarbeitet ggf. Ausdruecke/Formeln in Ini-Dateien.
 *  Es kann dann in einem dieser Formeln z.B. auf den Property-Wert einer anderen Sektion zugegriffen werden. So entstehen 'dynamische' ini-Dateien.
 * @author lindhaueradmin
 *
 */
public class KernelCallIniSolverZZZ extends AbstractKernelIniSolverZZZ implements IKernelCallIniSolverZZZ, IKernelJavaCallIniSolverZZZ{
	public static String sTAG_NAME = "Z:Call";
	private FileIniZZZ objFileIni=null;
	private HashMapCaseInsensitiveZZZ<String,String> hmVariable =null;
	
	
	public KernelCallIniSolverZZZ() throws ExceptionZZZ{
		String[] saFlag = {"init"};
		KernelCallIniSolverNew_(null, saFlag);
	}
	
	public KernelCallIniSolverZZZ(IKernelZZZ objKernel, String[]saFlag) throws ExceptionZZZ{		
		super(objKernel,saFlag);
		KernelCallIniSolverNew_(null, saFlag);
	}
	
	public KernelCallIniSolverZZZ(FileIniZZZ objFileIni) throws ExceptionZZZ{
		super(objFileIni.getKernelObject());
		KernelCallIniSolverNew_(objFileIni, null);
	}
	
	public KernelCallIniSolverZZZ(FileIniZZZ objFileIni, String[] saFlag) throws ExceptionZZZ{
		super(objFileIni.getKernelObject());
		KernelCallIniSolverNew_(objFileIni, saFlag);
	}
	
	public KernelCallIniSolverZZZ(IKernelZZZ objKernel, FileIniZZZ objFileIni, String[] saFlag) throws ExceptionZZZ{
		super(objKernel);
		KernelCallIniSolverNew_(objFileIni, saFlag);
	}
			
	private boolean KernelCallIniSolverNew_(FileIniZZZ objFileIn, String[] saFlagControlIn) throws ExceptionZZZ {
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
					if(this.getFlag("init")==true){
						bReturn = true;
						break main;
					}
				}
			
				if(objFileIn==null ){
//					ExceptionZZZ ez = new ExceptionZZZ("FileIni-Object", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
//					throw ez; 
				}else{
					this.setFileIni(objFileIn);	
					if(objFileIn.getHashMapVariable()!=null){
						this.setHashMapVariable(objFileIn.getHashMapVariable());
					}
				}

	 	}//end main:
		return bReturn;
	 }//end function KernelJsonIniSolverNew_
					
	//###### Getter / Setter
	@Override
	public String getExpressionTagName(){
		return KernelCallIniSolverZZZ.sTAG_NAME;
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
	
	public String compute(String sLineWithExpression) throws ExceptionZZZ {
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
				sReturn = HashMapExtendedZZZ.debugString(hm);
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
			sReturn=objJavaCallSolver.compute(sLineWithExpression);		
										
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
	public IKernelConfigSectionEntryZZZ computeAsEntry(String sLineWithExpression) throws ExceptionZZZ {
		IKernelConfigSectionEntryZZZ objReturn = new KernelConfigSectionEntryZZZ(); //Hier schon die Rückgabe vorbereiten, falls eine weitere Verarbeitung nicht konfiguriert ist.
		main:{			
			boolean bUseCall = this.getFlag(IKernelCallIniSolverZZZ.FLAGZ.USECALL);
			if(bUseCall) {
				objReturn = super.computeAsEntry(sLineWithExpression);
				objReturn.setValueCallSolvedAsExpression(objReturn.getValueAsExpression());
			}else {
				objReturn.setValue(sLineWithExpression);
			}									
		}//end main:
		return objReturn;
	}
	
	/* (non-Javadoc)
	 * @see basic.zKernel.file.ini.AbstractKernelIniSolverZZZ#computeAsExpression(java.lang.String)
	 */
	@Override
	public String computeAsExpression(String sLineWithExpression) throws ExceptionZZZ{
		String sReturn = sLineWithExpression;
		main:{			
			boolean bUseCall = this.getFlag(IKernelCallIniSolverZZZ.FLAGZ.USECALL);
			if(bUseCall) {
				sReturn = super.computeAsExpression(sLineWithExpression);
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
	public Vector<String>computeExpressionAllVector(String sLineWithExpression) throws ExceptionZZZ{
		Vector<String> vecReturn = new Vector<String>();
		main:{
			if(StringZZZ.isEmpty(sLineWithExpression)) break main;
						
			String sExpression = null; boolean bAnyCall = false; boolean bAnyJavaCall = false; boolean bAnyJavaClass = false; boolean bAnyJavaMethod = false;
						
			//Fuer den abschliessenden Aufruf selbst.
			String sClassnameWithPackage=null; String sMethodname=null;
						
			vecReturn = this.computeExpressionFirstVector(sLineWithExpression);//Alle Z:Call Ausdruecke ersetzen						
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
				KernelJavaCall_ClassZZZ objClassname = new KernelJavaCall_ClassZZZ(this.getKernelObject());
				while(objClassname.isExpression(sExpression)){
						IKernelConfigSectionEntryZZZ objEntry = objClassname.computeAsEntry(sLineWithExpression2);	
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
				KernelJavaCall_MethodZZZ objMethodname = new KernelJavaCall_MethodZZZ(this.getKernelObject());
				while(objMethodname.isExpression(sExpression)){
						IKernelConfigSectionEntryZZZ objEntry = objMethodname.computeAsEntry(sExpression);
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
					sExpression = KernelZFormulaIni_EmptyZZZ.getExpressionTagEmpty();
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
	public boolean isStringForComputeRelevant(String sExpressionToProof) throws ExceptionZZZ {			
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
