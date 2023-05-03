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

/**Diese Klasse verarbeitet ggf. Ausdruecke/Formeln in Ini-Dateien.
 *  Es kann dann in einem dieser Formeln z.B. auf den Property-Wert einer anderen Sektion zugegriffen werden. So entstehen 'dynamische' ini-Dateien.
 * @author lindhaueradmin
 *
 */
public class KernelCallIniSolverZZZ extends AbstractKernelIniSolverZZZ implements IKernelJsonIniSolverZZZ, IKernelJsonMapIniSolverZZZ, IKernelJsonArrayIniSolverZZZ{
	public static String sTAG_NAME = "Z:Call";
	private FileIniZZZ objFileIni=null;
	private HashMapCaseInsensitiveZZZ<String,String> hmVariable =null;
	
	
	public KernelCallIniSolverZZZ() throws ExceptionZZZ{
		String[] saFlag = {"init"};
		KernelJsonIniSolverNew_(null, saFlag);
	}
	
	public KernelCallIniSolverZZZ(FileIniZZZ objFileIni) throws ExceptionZZZ{
		super(objFileIni.getKernelObject());
		KernelJsonIniSolverNew_(objFileIni, null);
	}
	
	public KernelCallIniSolverZZZ(FileIniZZZ objFileIni, String[] saFlag) throws ExceptionZZZ{
		super(objFileIni.getKernelObject());
		KernelJsonIniSolverNew_(objFileIni, saFlag);
	}
	
	public KernelCallIniSolverZZZ(IKernelZZZ objKernel, FileIniZZZ objFileIni, String[] saFlag) throws ExceptionZZZ{
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
										
		}
		return sReturn;
	}
			
	public HashMap<String,String> computeHashMap(String sLineWithExpression) throws ExceptionZZZ{
		HashMap<String,String>hmReturn=new HashMap<String,String>();				
		main:{
			if(StringZZZ.isEmpty(sLineWithExpression)) break main;
			if(this.getFlag(IKernelJsonIniSolverZZZ.FLAGZ.USEJSON)==false) break main; 			
			if(this.getFlag(IKernelJsonMapIniSolverZZZ.FLAGZ.USEJSON_MAP.name())==true){				
		
				//WICHTIG: DIE FLAGS VERERBEN !!!
				KernelJsonMapIniSolverZZZ init4FlagLookup = new KernelJsonMapIniSolverZZZ();
				String[] saFlagZpassed = this.getFlagZ_passable(true, init4FlagLookup);
				HashMapCaseInsensitiveZZZ<String,String>hmVariable = this.getHashMapVariable();
				FileIniZZZ objFileIni = this.getFileIni();
				
				//Dann erzeuge neues KernelJsonMapSolverZZZ - Objekt.				
				KernelJsonMapIniSolverZZZ objJsonMapSolver = new KernelJsonMapIniSolverZZZ(objFileIni, saFlagZpassed); 
				hmReturn=objJsonMapSolver.computeHashMap(sLineWithExpression);			
			}				
		}//end main:
		return hmReturn;
	}
	
	public ArrayList<String> computeArrayList(String sLineWithExpression) throws ExceptionZZZ{
		ArrayList<String>alsReturn=new ArrayList<String>();				
		main:{
			if(StringZZZ.isEmpty(sLineWithExpression)) break main;
			if(this.getFlag(IKernelJsonIniSolverZZZ.FLAGZ.USEJSON)==false) break main; 			
			if(this.getFlag(IKernelJsonArrayIniSolverZZZ.FLAGZ.USEJSON_ARRAY.name())==true){				
		
				//Dann erzeuge neues KernelJsonArraySolverZZZ - Objekt.
				KernelJsonArrayIniSolverZZZ objJsonArraySolver = new KernelJsonArrayIniSolverZZZ(); 
				alsReturn=objJsonArraySolver.computeArrayList(sLineWithExpression);															
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
					if(!ArrayUtilZZZ.isEmpty(objaEnumFlag)) {
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
							if(!ArrayUtilZZZ.isEmpty(objaEnumFlag)) {
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
							if(!ArrayUtilZZZ.isEmpty(objaEnumFlag)) {
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
