package basic.zKernel.file.ini;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.Vector;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractList.ArrayListExtendedZZZ;
import basic.zBasic.util.abstractList.HashMapCaseInsensitiveZZZ;
import basic.zBasic.util.abstractList.HashMapExtendedZZZ;
import basic.zBasic.util.abstractList.VectorZZZ;
import basic.zBasic.util.datatype.calling.ReferenceArrayZZZ;
import basic.zBasic.util.datatype.calling.ReferenceHashMapZZZ;
import basic.zBasic.util.datatype.calling.ReferenceZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zKernel.IKernelConfigSectionEntryZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernel.KernelUseObjectZZZ;
import basic.zKernel.config.KernelConfigEntryUtilZZZ;
import custom.zKernel.file.ini.FileIniZZZ;

public class KernelExpressionIniSolverZZZ  extends KernelUseObjectZZZ implements IKernelJsonIniSolverZZZ,IKernelZFormulaIniSolverZZZ{
	private FileIniZZZ objFileIni=null;
	private HashMapCaseInsensitiveZZZ<String,String> hmVariable =null;
	
	public KernelExpressionIniSolverZZZ() throws ExceptionZZZ{
		String[] saFlag = {"init"};
		KernelExpressionIniSolverNew_(null, null,saFlag);
	}
	
	public KernelExpressionIniSolverZZZ(FileIniZZZ objFileIni) throws ExceptionZZZ{
		super(objFileIni.getKernelObject());
		KernelExpressionIniSolverNew_(objFileIni, null, null);
	}
	
	public KernelExpressionIniSolverZZZ(FileIniZZZ objFileIni, String[] saFlag) throws ExceptionZZZ{
		super(objFileIni.getKernelObject());
		KernelExpressionIniSolverNew_(objFileIni, null, saFlag);
	}
	
	public KernelExpressionIniSolverZZZ(IKernelZZZ objKernel, FileIniZZZ objFileIni, String[] saFlag) throws ExceptionZZZ{
		super(objKernel);
		KernelExpressionIniSolverNew_(objFileIni, null, saFlag);
	}
	
	public KernelExpressionIniSolverZZZ(FileIniZZZ objFileIni, HashMapCaseInsensitiveZZZ<String,String> hmVariable) throws ExceptionZZZ{
		super(objFileIni.getKernelObject());
		KernelExpressionIniSolverNew_(objFileIni, hmVariable, null);
	}
	
	public KernelExpressionIniSolverZZZ(FileIniZZZ objFileIni, HashMapCaseInsensitiveZZZ<String,String> hmVariable, String[] saFlag) throws ExceptionZZZ{
		super(objFileIni.getKernelObject());
		KernelExpressionIniSolverNew_(objFileIni, hmVariable, saFlag);
	}
	
	public KernelExpressionIniSolverZZZ(IKernelZZZ objKernel, FileIniZZZ objFileIni, HashMapCaseInsensitiveZZZ<String,String> hmVariable, String[] saFlag) throws ExceptionZZZ{
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
							ExceptionZZZ ez = new ExceptionZZZ( "the flag '" + stemp + "' is not available.", iERROR_FLAG_UNAVAILABLE, this, ReflectCodeZZZ.getMethodCurrentName()); 
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
				
				if(hmVariable!=null){				
						this.setVariable(hmVariable);			//soll zu den Variablen aus derm Ini-File hinzuaddieren, bzw. ersetzen		
				}
	 	}//end main:
		return bReturn;
	 }//end function KernelExpressionIniSolverNew_
	
	//###### Getter / Setter	
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
		
		public int compute(String sLineWithExpression, IKernelConfigSectionEntryZZZ objReturn) throws ExceptionZZZ{
			int iReturn = 0;
			main:{
				if(StringZZZ.isEmpty(sLineWithExpression)) break main;
				
				boolean bUseFormula = this.getFlag(IKernelZFormulaIniSolverZZZ.FLAGZ.USEFORMULA.name());
				if(bUseFormula) {
				
					//Hier KernelZFormulIniSolverZZZ
					//und KernelJsonInisolverZZZ verwenden
					KernelZFormulaIniSolverZZZ formulaDummy = new KernelZFormulaIniSolverZZZ();
					String[] saFlagZpassed = this.getFlagZ_passable(true, formulaDummy);
					HashMapCaseInsensitiveZZZ<String,String>hmVariable = this.getHashMapVariable();
					
					//Merke: objReturnValue ist ein Hilfsobjekt, mit dem CallByReference hinsichtlich der Werte realisiert wird.
					ReferenceZZZ<String>objsReturnValueConverted=new ReferenceZZZ<String>();
					ReferenceZZZ<String>objsReturnValueExpressionSolved=new ReferenceZZZ<String>();
					ReferenceZZZ<String>objsReturnValue=new ReferenceZZZ<String>();			
					boolean bAnyExpression = false;
					iReturn = KernelConfigEntryUtilZZZ.getValueExpressionSolvedAndConverted(this.getFileIni(), sLineWithExpression, bUseFormula, hmVariable, saFlagZpassed, objsReturnValueExpressionSolved, objsReturnValueConverted, objsReturnValue);			
					if(iReturn>=1){
						break main;
					}
				}
				
				boolean bUseJson = this.getFlag(IKernelJsonIniSolverZZZ.FLAGZ.USEJSON.name());
				if(bUseJson) {
					KernelJsonIniSolverZZZ exDummy03 = new KernelJsonIniSolverZZZ();
					String[] saFlagZpassed = this.getFlagZ_passable(true, exDummy03);					
					
					boolean bUseJsonMap = this.getFlag(IKernelJsonIniSolverZZZ.FLAGZ.USEJSON_MAP.name());
					if(bUseJsonMap) {				
						//Merke: objReturnValue ist ein Hilfsobjekt, mit dem CallByReference hinsichtlich der Werte realisiert wird.
						ReferenceHashMapZZZ<String,String>objhmReturnValue=new ReferenceHashMapZZZ<String,String>();				                   
						boolean bAnyJsonMap = KernelConfigEntryUtilZZZ.getValueJsonMapSolved(this.getFileIni(), sLineWithExpression, bUseJson, saFlagZpassed, objhmReturnValue);			
						if(bAnyJsonMap) {
							objReturn.isJsonMap(true);
							objReturn.isJson(true);
							objReturn.setValue(HashMapExtendedZZZ.debugString(objhmReturnValue.get()));
							objReturn.setValue(objhmReturnValue.get());
							iReturn = 5;
							break main;
						}else {										
						}
					}
					
					boolean bUseJsonArray = this.getFlag(IKernelJsonIniSolverZZZ.FLAGZ.USEJSON_ARRAY.name());
					if(bUseJsonArray) {
						//Merke: objReturnValue ist ein Hilfsobjekt, mit dem CallByReference hinsichtlich der Werte realisiert wird.
						ReferenceArrayZZZ<String>objalReturnValue=new ReferenceArrayZZZ<String>();
						boolean bAnyJsonArray = KernelConfigEntryUtilZZZ.getValueJsonArraySolved(this.getFileIni(), sLineWithExpression, bUseJson, saFlagZpassed, objalReturnValue);			
						if(bAnyJsonArray) {
							objReturn.isJsonArray(true);
							objReturn.isJson(true);
							objReturn.setValue(ArrayListExtendedZZZ.debugString(objalReturnValue.getArrayList()));
							objReturn.setValue(objalReturnValue.getArrayList());
							iReturn = 6;
							break main;
						}else {										
						}					
					}
				}	
			}//end main:
			return iReturn;
		}
	
		public ArrayList<String> computeArrayList(String sLineWithExpression)throws ExceptionZZZ{
			ArrayList<String> alsReturn=  new ArrayList<String>();
			main:{
				
				//Hier KernelJsonInisolverZZZ verwenden
				
			}//end main;
			return alsReturn;
			
		}
		
		public HashMap<String,String> computeHashMap(String sLineWithExpression)throws ExceptionZZZ{
			HashMap<String,String> hmReturn=  new HashMap<String,String>();
			main:{
				
				//Hier KernelJsonInisolverZZZ verwenden				
				
			}//end main;
			return hmReturn;
			
		}
}
