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
import basic.zKernel.flag.IFlagUserZZZ;
import custom.zKernel.file.ini.FileIniZZZ;

public class KernelExpressionIniSolverZZZ  extends KernelUseObjectZZZ implements IKernelExpressionIniSolverZZZ{
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
	 main:{
		 	
	 	//try{	 		
	 			//setzen der übergebenen Flags	
				if(saFlagControlIn != null){
					 String stemp; boolean btemp; String sLog;
					for(int iCount = 0;iCount<=saFlagControlIn.length-1;iCount++){
						stemp = saFlagControlIn[iCount];
						btemp = setFlag(stemp, true);
						if(btemp==false){
							 String sKey = stemp;
							 sLog = "the passed flag '" + sKey + "' is not available for class '" + this.getClass() + "'.";
							 this.logLineDate(ReflectCodeZZZ.getPositionCurrent() + ": " + sLog);
							//							  Bei der "Übergabe auf Verdacht" keinen Fehler werfen!!!							
							// ExceptionZZZ ez = new ExceptionZZZ(stemp, IFlagUserZZZ.iERROR_FLAG_UNAVAILABLE, this, ReflectCodeZZZ.getMethodCurrentName()); 							
							// throw ez;		 
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
				boolean bUseExpression = this.getFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION.name());
				if(!bUseExpression) break main;
				
				boolean bUseFormula = this.getFlag(IKernelZFormulaIniSolverZZZ.FLAGZ.USEFORMULA.name());
				if(bUseFormula) {
				
					//Hier KernelZFormulIniSolverZZZ
					//und KernelJsonInisolverZZZ verwenden
					//Merke: Die statischen Methoden leisten mehr als nur die ...Solver....
					//       Durch den int Rückgabwert sorgen sie nämlich für die korrekte Befüllung von 
					//       objReturn, also auch der darin verwendeten Flags bIsJson, bIsJsonMap, etc.
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
						bAnyExpression = true;
						objReturn.isExpression(true);
						
						//++++ usw. das Ergebnis als String in objReturn packen.
						String sReturnValue=null;
							
						String sReturnFormula = objsReturnValueExpressionSolved.get();			
						if(iReturn==1 | iReturn==3) {
							objReturn.isFormula(true);
							sReturnValue=sReturnFormula;
						}
						
						String sReturnExpression = objsReturnValueConverted.get();			
						if(iReturn==2 | iReturn==3) {
							objReturn.isConverted(true);							
							sReturnValue = sReturnExpression;
						}	
						
						if(!bAnyExpression){
							sReturnValue = objsReturnValue.get();
						}
						objReturn.setValue(sReturnValue);						 					
					break main;
					}
				}
				
				boolean bUseJson = this.getFlag(IKernelJsonIniSolverZZZ.FLAGZ.USEJSON.name());
				if(bUseJson) {
					KernelJsonIniSolverZZZ exDummy03 = new KernelJsonIniSolverZZZ();
					String[] saFlagZpassed = this.getFlagZ_passable(true, exDummy03);					
					HashMapCaseInsensitiveZZZ<String,String>hmVariable = this.getHashMapVariable();

					//Merke: objReturnValue ist ein Hilfsobjekt, mit dem CallByReference hinsichtlich der Werte realisiert wird.
					ReferenceArrayZZZ<String>objalsReturnValueJsonSolved=new ReferenceArrayZZZ<String>();
					ReferenceHashMapZZZ<String,String>objhmReturnValueJsonSolved=new ReferenceHashMapZZZ<String,String>();
					
					//TODOGOON; //20210729 Hier nur 1 statische Methode aufrufen, die einen Integerwert zurückliefert, der dann die Befüllung von objReturn steuert.					
					iReturn = KernelConfigEntryUtilZZZ.getValueJsonSolved(this.getFileIni(), sLineWithExpression, bUseJson, saFlagZpassed, objalsReturnValueJsonSolved,objhmReturnValueJsonSolved);
					
					if(iReturn==5) {
						objReturn.isJsonArray(true);
						objReturn.isJson(true);
						objReturn.isExpression(true);
						objReturn.setValue(ArrayListExtendedZZZ.debugString(objalsReturnValueJsonSolved.getArrayList()));
						objReturn.setValue(objalsReturnValueJsonSolved.getArrayList());												
					}
					
					if(iReturn==6) {
						objReturn.isJsonMap(true);
						objReturn.isJson(true);
						objReturn.isExpression(true);
						objReturn.setValue(HashMapExtendedZZZ.debugString(objhmReturnValueJsonSolved.get()));
						objReturn.setValue(objhmReturnValueJsonSolved.get());
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
