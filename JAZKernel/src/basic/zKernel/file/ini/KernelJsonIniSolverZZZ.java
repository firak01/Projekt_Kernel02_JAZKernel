package basic.zKernel.file.ini;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.abstractList.ArrayListExtendedZZZ;
import basic.zBasic.util.abstractList.HashMapCaseInsensitiveZZZ;
import basic.zBasic.util.abstractList.HashMapExtendedZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernel.flag.IFlagZUserZZZ;
import basic.zKernel.flag.util.FlagZFassadeZZZ;
import custom.zKernel.file.ini.FileIniZZZ;

/**Diese Klasse verarbeitet ggf. Ausdruecke/Formeln in Ini-Dateien.
 *  Es kann dann in einem dieser Formeln z.B. auf den Property-Wert einer anderen Sektion zugegriffen werden. So entstehen 'dynamische' ini-Dateien.
 * @author lindhaueradmin
 *
 */
public class KernelJsonIniSolverZZZ<T> extends AbstractKernelIniSolverZZZ<T> implements IKernelJsonIniSolverZZZ, IKernelJsonMapIniSolverZZZ, IKernelJsonArrayIniSolverZZZ{
	private static final long serialVersionUID = 6588176234400554782L;
	public static String sTAG_NAME = "JSON";
	private FileIniZZZ objFileIni=null;
	private HashMapCaseInsensitiveZZZ<String,String> hmVariable =null;
	
	
	public KernelJsonIniSolverZZZ() throws ExceptionZZZ{
		String[] saFlag = {"init"};
		KernelJsonIniSolverNew_(null, saFlag);
	}
	
	public KernelJsonIniSolverZZZ(FileIniZZZ objFileIni) throws ExceptionZZZ{
		super(objFileIni.getKernelObject());
		KernelJsonIniSolverNew_(objFileIni, null);
	}
	
	public KernelJsonIniSolverZZZ(FileIniZZZ objFileIni, String[] saFlag) throws ExceptionZZZ{
		super(objFileIni.getKernelObject());
		KernelJsonIniSolverNew_(objFileIni, saFlag);
	}
	
	public KernelJsonIniSolverZZZ(IKernelZZZ objKernel, FileIniZZZ objFileIni, String[] saFlag) throws ExceptionZZZ{
		super(objKernel);
		KernelJsonIniSolverNew_(objFileIni, saFlag);
	}
			
	private boolean KernelJsonIniSolverNew_(FileIniZZZ objFileIn, String[] saFlagControlIn) throws ExceptionZZZ {
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
					ExceptionZZZ ez = new ExceptionZZZ("FileIni-Object", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
					throw ez; 
				}else{
					this.setFileConifgKernelIni(objFileIn);	
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
		return KernelJsonIniSolverZZZ.sTAG_NAME;
	}
	
	public void setFileConifgKernelIni(FileIniZZZ objFileIni){
		this.objFileIni = objFileIni;
	}
	public FileIniZZZ getFileConfigKernelIni(){
		return this.objFileIni;
	}
	
	public void setHashMapVariable(HashMapCaseInsensitiveZZZ<String,String> hmVariable){
		this.hmVariable = hmVariable;
	}
	public HashMapCaseInsensitiveZZZ<String,String> getHashMapVariable(){
		return this.hmVariable;
	}
	
	public String compute(String sLineWithExpression) throws ExceptionZZZ {
		String sReturn = new String("");
		main:{
			if(StringZZZ.isEmpty(sLineWithExpression)) break main;
			if(this.getFlag(IKernelJsonIniSolverZZZ.FLAGZ.USEJSON.name())==false) break main;
			
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
										
		}
		return sReturn;
	}
			
	public HashMap<String,String> computeHashMap(String sLineWithExpression) throws ExceptionZZZ{
		HashMap<String,String>hmReturn=new HashMap<String,String>();				
		main:{
			if(StringZZZ.isEmpty(sLineWithExpression)) break main;
			if(this.getFlag(IKernelJsonIniSolverZZZ.FLAGZ.USEJSON)==false) break main; 			
			if(this.getFlag(IKernelJsonMapIniSolverZZZ.FLAGZ.USEJSON_MAP.name())==true){				
		
			//Dann erzeuge neues KernelJsonMapSolverZZZ - Objekt.			
			FileIniZZZ objFileIni = this.getFileConfigKernelIni();
				
			KernelJsonMapIniSolverZZZ objJsonMapSolver = new KernelJsonMapIniSolverZZZ(objFileIni);
			String[] saFlag = FlagZFassadeZZZ.seekFlagZrelevantForObject(this, objJsonMapSolver, true);
			objJsonMapSolver.setFlag(saFlag,true);
			hmReturn=objJsonMapSolver.computeHashMap(sLineWithExpression);			
			}				
		}//end main:
		return hmReturn;
	}
	
	public ArrayList<String> computeArrayList(String sLineWithExpression) throws ExceptionZZZ{
		ArrayList<String>alsReturn=new ArrayList<String>();				
		main:{
			if(StringZZZ.isEmpty(sLineWithExpression)) break main;
			if(!this.getFlag(IKernelJsonIniSolverZZZ.FLAGZ.USEJSON)) break main; 			
			if(!this.getFlag(IKernelJsonArrayIniSolverZZZ.FLAGZ.USEJSON_ARRAY)) break main;				
		
			//Dann erzeuge neues KernelJsonArraySolverZZZ - Objekt.		
			FileIniZZZ objFileIni = this.getFileConfigKernelIni();
			
			KernelJsonArrayIniSolverZZZ objJsonArraySolver = new KernelJsonArrayIniSolverZZZ(objFileIni);
			String[] saFlag = FlagZFassadeZZZ.seekFlagZrelevantForObject(this, objJsonArraySolver, true);
			objJsonArraySolver.setFlag(saFlag,true);
			alsReturn=objJsonArraySolver.computeArrayList(sLineWithExpression);															
							
		}//end main:
		return alsReturn;
	}
	
	//### Andere Interfaces
	/* (non-Javadoc)
	 * @see basic.zKernel.file.ini.AbstractKernelIniSolverZZZ#computeExpressionAllVector(java.lang.String)
	 */
	@Override
	public Vector computeExpressionAllVector(String sLineWithExpression) throws ExceptionZZZ {
		return null;
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
	
	
	//Aus IKernelJsonArrayIniSolverZZZ
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
}//End class
