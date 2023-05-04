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
import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.abstractList.ArrayListExtendedZZZ;
import basic.zBasic.util.abstractList.HashMapCaseInsensitiveZZZ;
import basic.zBasic.util.abstractList.HashMapExtendedZZZ;
import basic.zBasic.util.abstractList.VectorZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zKernel.IKernelZFormulaIniZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernel.KernelUseObjectZZZ;
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
				sReturn = HashMapExtendedZZZ.debugString(hm);
				break main;
			}
			
			//3. Versuch als Einzelwert
			//WICHTIG: DIE FLAGS VERERBEN !!!
			KernelJavaCallIniSolverZZZ init4FlagLookup = new KernelJavaCallIniSolverZZZ();
			String[] saFlagZpassed = FlagZFassadeZZZ.seekFlagZrelevantForObject(init4FlagLookup, this, true);
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
					if(!ArrayUtilZZZ.isEmpty(objaEnumFlag)) {
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
				if(!ArrayUtilZZZ.isEmpty(objaEnum_IKernelJavaCallIniSolverZZZ)) {
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
