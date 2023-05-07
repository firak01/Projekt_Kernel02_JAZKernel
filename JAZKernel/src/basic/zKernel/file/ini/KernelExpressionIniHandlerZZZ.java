package basic.zKernel.file.ini;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.Vector;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.abstractList.ArrayListExtendedZZZ;
import basic.zBasic.util.abstractList.HashMapCaseInsensitiveZZZ;
import basic.zBasic.util.abstractList.HashMapExtendedZZZ;
import basic.zBasic.util.abstractList.VectorZZZ;
import basic.zBasic.util.crypt.code.ICryptZZZ;
import basic.zBasic.util.datatype.calling.ReferenceArrayZZZ;
import basic.zBasic.util.datatype.calling.ReferenceHashMapZZZ;
import basic.zBasic.util.datatype.calling.ReferenceZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zKernel.IKernelConfigSectionEntryZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernel.KernelConfigSectionEntryCreatorZZZ;
import basic.zKernel.KernelConfigSectionEntryZZZ;
import basic.zKernel.KernelUseObjectZZZ;
import basic.zKernel.config.KernelConfigEntryUtilZZZ;
import basic.zKernel.flag.IFlagZUserZZZ;
import basic.zKernel.flag.util.FlagZFassadeZZZ;
import custom.zKernel.file.ini.FileIniZZZ;

/** Klasse, welche die "Ini-Solver" Klassen anwendet.
 *  Somit ist sie die "Ausgangsklasse" für die FileIniZZZ-Methoden
 *  einen Wert zu errechnen.
 * 
 * @author Fritz Lindhauer, 02.05.2023, 19:55:30
 * 
 */
public class KernelExpressionIniHandlerZZZ  extends AbstractKernelIniTagSimpleZZZ implements IKernelExpressionIniSolverZZZ{
	public static String sTAG_NAME = "Z";
	private FileIniZZZ objFileIni=null;
	private ICryptZZZ objCrypt=null; //Das Verschlüsselungs-Algorithmus-Objekt, falls der Wert verschlüsselt ist.
	private HashMapCaseInsensitiveZZZ<String,String> hmVariable =null;
	
	IKernelConfigSectionEntryZZZ objEntry = null;
	
	public KernelExpressionIniHandlerZZZ() throws ExceptionZZZ{
		String[] saFlag = {"init"};
		KernelExpressionIniSolverNew_(null, null,saFlag);
	}
	
	public KernelExpressionIniHandlerZZZ(FileIniZZZ objFileIni) throws ExceptionZZZ{
		super(objFileIni.getKernelObject());
		KernelExpressionIniSolverNew_(objFileIni, null, null);
	}
	
	public KernelExpressionIniHandlerZZZ(FileIniZZZ objFileIni, String[] saFlag) throws ExceptionZZZ{
		super(objFileIni.getKernelObject());
		KernelExpressionIniSolverNew_(objFileIni, null, saFlag);
	}
	
	public KernelExpressionIniHandlerZZZ(IKernelZZZ objKernel, FileIniZZZ objFileIni, String[] saFlag) throws ExceptionZZZ{
		super(objKernel);
		KernelExpressionIniSolverNew_(objFileIni, null, saFlag);
	}
	
	public KernelExpressionIniHandlerZZZ(FileIniZZZ objFileIni, HashMapCaseInsensitiveZZZ<String,String> hmVariable) throws ExceptionZZZ{
		super(objFileIni.getKernelObject());
		KernelExpressionIniSolverNew_(objFileIni, hmVariable, null);
	}
	
	public KernelExpressionIniHandlerZZZ(FileIniZZZ objFileIni, HashMapCaseInsensitiveZZZ<String,String> hmVariable, String[] saFlag) throws ExceptionZZZ{
		super(objFileIni.getKernelObject());
		KernelExpressionIniSolverNew_(objFileIni, hmVariable, saFlag);
	}
	
	public KernelExpressionIniHandlerZZZ(IKernelZZZ objKernel, FileIniZZZ objFileIni, HashMapCaseInsensitiveZZZ<String,String> hmVariable, String[] saFlag) throws ExceptionZZZ{
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
				}
				if(this.getFlag("init")==true){
					bReturn = true;
					break main;
				}
			
				if(objFileIn==null ){
					ExceptionZZZ ez = new ExceptionZZZ("FileIni-Object", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
					throw ez; 
				}else{
					this.setFileIni(objFileIn);						
				}
				
				if(hmVariable!=null){				
						this.setVariable(hmVariable);			//soll zu den Variablen aus derm Ini-File hinzuaddieren, bzw. ersetzen		
				}else {
					if(objFileIn.getHashMapVariable()!=null){
						this.setHashMapVariable(objFileIn.getHashMapVariable());
					}
				}
	 	}//end main:
		return bReturn;
	 }//end function KernelExpressionIniSolverNew_
	
	//### GETTER / SETTER
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
			
			/** Berechne die INI-Werte. 
			 * 	 
			 *  STRATEGIE:
			 *  1. Ini-Variablen und Pfade aufloesen
			 *  2. JavaCalls aufloesen
			 *  3. JSON Werte
			 *  4. Entschluesseln 			 
			 *  
			 *  IDEE: 
			 *  Die Entschluesselung zuletzt. Damit kann z.B. theoretisch in den JSON-Werten verschluesselter Inhalt enthalten sein.
			 *  
			 * @param sLineWithExpression
			 * @param objReturnReference
			 * @return
			 * @throws ExceptionZZZ
			 * @author Fritz Lindhauer, 06.05.2023, 07:41:02
			 */
			public int compute(String sLineWithExpression, ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference) throws ExceptionZZZ{		
				int iReturn = 0;
				boolean bAnyEncryption = false;		boolean bAnyCall = false;	boolean bAnyFormula = false; boolean bAnyJson = false;
				IKernelConfigSectionEntryZZZ objReturn=objReturnReference.get();
				if(objReturn==null) {
					objReturn = new KernelConfigSectionEntryZZZ();
					objReturnReference.set(objReturn);
				}
				main:{
					if(StringZZZ.isEmpty(sLineWithExpression)) break main;
					boolean bUseExpression = this.getFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION.name());
					if(!bUseExpression) break main;
					
					iReturn=0;
					
					String sLineWithExpressionUsed = sLineWithExpression;
					objReturn.setValue(sLineWithExpressionUsed);//Schon mal setzen, falls es ein normaler Wert ist. Falls es ein Formelwert/Verschluesselter Wert ist, wird das eh ueberschrieben.

					boolean bUseFormula = this.getFlag(IKernelZFormulaIniSolverZZZ.FLAGZ.USEFORMULA.name());
					if(bUseFormula) {
					
						//Hier KernelZFormulIniSolverZZZ
						//und KernelJsonInisolverZZZ verwenden
						//Merke: Die statischen Methoden leisten mehr als nur die ...Solver....
						//       Durch den int Rückgabwert sorgen sie nämlich für die korrekte Befüllung von 
						//       objReturn, also auch der darin verwendeten Flags bIsJson, bIsJsonMap, etc.
						KernelZFormulaIniSolverZZZ formulaDummy = new KernelZFormulaIniSolverZZZ();
						String[] saFlagZpassed = FlagZFassadeZZZ.seekFlagZrelevantForObject(this, formulaDummy, true);
						HashMapCaseInsensitiveZZZ<String,String>hmVariable = this.getHashMapVariable();
						
						//Merke: objReturnReference ist ein Hilfsobjekt, mit dem CallByReference hinsichtlich der Werte realisiert wird.													
						int iReturnExpression = KernelConfigEntryUtilZZZ.getValueExpressionSolvedAndConverted(this.getFileIni(), sLineWithExpressionUsed, bUseFormula, hmVariable, saFlagZpassed, objReturnReference);			
						if(iReturnExpression>=1){	
							bAnyFormula = true;																			
						}
						if(bAnyFormula) {
							objReturn.isFormula(true);
						}//Merke: Keinen Else-Zweig. Vielleicht war in einem vorherigen Schritt ja durchaus eine Formel enthalten
					}//end bUseFormula
					
					
					boolean bUseCall = this.getFlag(IKernelCallIniSolverZZZ.FLAGZ.USECALL.name());
					if(bUseCall){
						KernelCallIniSolverZZZ callDummy = new KernelCallIniSolverZZZ();
						String[] saFlagZpassed = FlagZFassadeZZZ.seekFlagZrelevantForObject(this, callDummy, true);
						
						//Merke: objReturnReference ist ein Hilfsobjekt, mit dem CallByReference hinsichtlich der Werte realisiert wird.
						boolean bForFurtherProcessing = false; 
						bAnyCall = KernelConfigEntryUtilZZZ.getValueCallSolved(this.getFileIni(), sLineWithExpressionUsed, bUseCall, bForFurtherProcessing, saFlagZpassed, objReturnReference);
						if(bAnyCall) {
							objReturn.isCall(true);						
						}//Merke: Keinen Else-Zweig. Vielleicht war in einem vorherigen Schritt ja durchaus ein Call enthalten
					}
												
					boolean bUseJson = this.getFlag(IKernelJsonIniSolverZZZ.FLAGZ.USEJSON);
					if(bUseJson) {
						int iReturnJson=0;
						
						KernelJsonIniSolverZZZ exDummy03 = new KernelJsonIniSolverZZZ();
						String[] saFlagZpassed = FlagZFassadeZZZ.seekFlagZrelevantForObject(this, exDummy03, true); //this.getFlagZ_passable(true, exDummy);					
						HashMapCaseInsensitiveZZZ<String,String>hmVariable = this.getHashMapVariable();

						//Merke: objReturnValue ist ein Hilfsobjekt, mit dem CallByReference hinsichtlich der Werte realisiert wird.
						ReferenceArrayZZZ<String>objalsReturnValueJsonSolved=new ReferenceArrayZZZ<String>();
						ReferenceHashMapZZZ<String,String>objhmReturnValueJsonSolved=new ReferenceHashMapZZZ<String,String>();														
						iReturnJson = KernelConfigEntryUtilZZZ.getValueJsonSolved(this.getFileIni(), sLineWithExpressionUsed, bUseJson, saFlagZpassed, objalsReturnValueJsonSolved,objhmReturnValueJsonSolved);					
						if(iReturnJson==5) {
							objReturn.isJsonArray(true);
							objReturn.isJson(true);
							objReturn.isExpression(true);
							objReturn.setValue(ArrayListExtendedZZZ.debugString(objalsReturnValueJsonSolved.getArrayList()));
							objReturn.setValue(objalsReturnValueJsonSolved.getArrayList());												
						}//Merke: Keinen Else-Zweig. Vielleicht war in einem vorherigen Schritt ja durchaus Json enthalten
					
					
						if(iReturnJson==6) {
							objReturn.isJsonMap(true);
							objReturn.isJson(true);
							objReturn.isExpression(true);
							objReturn.setValue(HashMapExtendedZZZ.debugString(objhmReturnValueJsonSolved.get()));
							objReturn.setValue(objhmReturnValueJsonSolved.get());
						}//Merke: Keinen Else-Zweig. Vielleicht war in einem vorherigen Schritt ja durchaus Json enthalten
						
						iReturn = iReturn + iReturnJson;						
					}									
					
					boolean bUseEncryption = this.getFlag(IKernelEncryptionIniSolverZZZ.FLAGZ.USEENCRYPTION.name());
					if(bUseEncryption) {
						KernelEncryptionIniSolverZZZ encryptionDummy = new KernelEncryptionIniSolverZZZ();
						String[] saFlagZpassed = FlagZFassadeZZZ.seekFlagZrelevantForObject(this, encryptionDummy, true);
						
						//Merke: objReturnReference ist ein Hilfsobjekt, mit dem CallByReference hinsichtlich der Werte realisiert wird.
						boolean bForFurtherProcessing = false; 
						bAnyEncryption = KernelConfigEntryUtilZZZ.getValueEncryptionSolved(this.getFileIni(), sLineWithExpressionUsed, bUseEncryption, bForFurtherProcessing, saFlagZpassed, objReturnReference);
						if(bAnyEncryption) {
							objReturn.isRawEncrypted(true);
							String sLineDecrypted = objReturnReference.get().getRawDecrypted();//Wert zur weiteren Verarbeitung weitergeben						
							if(sLineWithExpressionUsed.equals(sLineDecrypted)) {												
								objReturn.isDecrypted(false);
							}else {
								objReturn.isDecrypted(true);
								objReturn.setRawDecrypted(sLineDecrypted);
								objReturn.setValue(sLineDecrypted);       //quasi erst mal den Zwischenstand festhalten.							
							}
							sLineWithExpressionUsed = sLineDecrypted; //Zur Verarbeitung weitergeben			
						}//Merke: Keinen Else-Zweig. Vielleicht war in einem vorherigen Schritt ja durchaus Encryption enthalten
					}
					
				}//end main:
				if(bAnyFormula) {
					iReturn = iReturn+1;
				}
				if(bAnyEncryption) {				
					iReturn = iReturn+10; //Falls irgendeine Verschlüsselung vorliegt den Wert um 10 erhöhen.
				}
				if(bAnyCall) {
					iReturn = iReturn+100;
				}
				if(bAnyFormula || bAnyEncryption || bAnyCall) {
					objReturn.isExpression(true);
				}
				objReturnReference.set(objReturn);
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
	
	//######### Interfaces #############################################
	
	//### aus IKernelZFormulaIniSolverZZZ
			@Override
			public boolean getFlag(IKernelZFormulaIniSolverZZZ.FLAGZ objEnumFlag) {
				return this.getFlag(objEnumFlag.name());
			}
			
			@Override
			public boolean setFlag(IKernelZFormulaIniSolverZZZ.FLAGZ objEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
				return this.setFlag(objEnumFlag.name(), bFlagValue);
			}
			
			@Override
			public boolean[] setFlag(IKernelZFormulaIniSolverZZZ.FLAGZ[] objaEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
				boolean[] baReturn=null;
				main:{
					if(!ArrayUtilZZZ.isEmpty(objaEnumFlag)) {
						baReturn = new boolean[objaEnumFlag.length];
						int iCounter=-1;
						for(IKernelZFormulaIniSolverZZZ.FLAGZ objEnumFlag:objaEnumFlag) {
							iCounter++;
							boolean bReturn = this.setFlag(objEnumFlag, bFlagValue);
							baReturn[iCounter]=bReturn;
						}
					}
				}//end main:
				return baReturn;
			}
			

			@Override
			public boolean proofFlagExists(IKernelZFormulaIniSolverZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
				return this.proofFlagExists(objEnumFlag.name());
			}
	
	
	//### Aus Interface IKernelExpressionIniSolverZZZ
	@Override
	public boolean getFlag(IKernelExpressionIniSolverZZZ.FLAGZ objEnumFlag) {
		return this.getFlag(objEnumFlag.name());
	}
	
	@Override
	public boolean setFlag(IKernelExpressionIniSolverZZZ.FLAGZ objEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		return this.setFlag(objEnumFlag.name(), bFlagValue);
	}
	
	@Override
	public boolean[] setFlag(IKernelExpressionIniSolverZZZ.FLAGZ[] objaEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		boolean[] baReturn=null;
		main:{
			if(!ArrayUtilZZZ.isEmpty(objaEnumFlag)) {
				baReturn = new boolean[objaEnumFlag.length];
				int iCounter=-1;
				for(IKernelExpressionIniSolverZZZ.FLAGZ objEnumFlag:objaEnumFlag) {
					iCounter++;
					boolean bReturn = this.setFlag(objEnumFlag, bFlagValue);
					baReturn[iCounter]=bReturn;
				}
			}
		}//end main:
		return baReturn;
	}
	
	@Override
	public boolean proofFlagExists(IKernelExpressionIniSolverZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
		return this.proofFlagExists(objEnumFlag.name());
	}
	
	//### aus IKernelEncryptionIniSolverZZZ
	@Override
	public boolean getFlag(IKernelEncryptionIniSolverZZZ.FLAGZ objEnumFlag) {
		return this.getFlag(objEnumFlag.name());
	}
	
	@Override
	public boolean setFlag(IKernelEncryptionIniSolverZZZ.FLAGZ objEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		return this.setFlag(objEnumFlag.name(), bFlagValue);
	}
	
	@Override
	public boolean[] setFlag(IKernelEncryptionIniSolverZZZ.FLAGZ[] objaEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		boolean[] baReturn=null;
		main:{
			if(!ArrayUtilZZZ.isEmpty(objaEnumFlag)) {
				baReturn = new boolean[objaEnumFlag.length];
				int iCounter=-1;
				for(IKernelEncryptionIniSolverZZZ.FLAGZ objEnumFlag:objaEnumFlag) {
					iCounter++;
					boolean bReturn = this.setFlag(objEnumFlag, bFlagValue);
					baReturn[iCounter]=bReturn;
				}
			}
		}//end main:
		return baReturn;
	}
	
	@Override
	public boolean proofFlagExists(IKernelEncryptionIniSolverZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
		return this.proofFlagExists(objEnumFlag.name());
	}

		//### Interface aus IKernelExpressionIniSolver
		public IKernelConfigSectionEntryZZZ getEntry() {
			if(this.objEntry==null) {
				this.objEntry = new KernelConfigSectionEntryZZZ();			
			}
			return this.objEntry;
		}
		public void setEntry(IKernelConfigSectionEntryZZZ objEntry) {
			this.objEntry = objEntry;
		}
		
		//### Aus Interface ICryptUserZZZ
		@Override
		public ICryptZZZ getCryptAlgorithmType() throws ExceptionZZZ {
			return this.objCrypt;
		}

		@Override
		public void setCryptAlgorithmType(ICryptZZZ objCrypt) {
			this.objCrypt = objCrypt;
		}

		//### Interface
		/* (non-Javadoc)
		 * @see basic.zKernel.file.ini.AbstractKernelIniSolverZZZ#getExpressionTagName()
		 */
		@Override
		public String getExpressionTagName() {
			return KernelExpressionIniHandlerZZZ.sTAG_NAME;
		}

		@Override
		public Vector computeExpressionAllVector(String sLineWithExpression) throws ExceptionZZZ {
			// TODO Auto-generated method stub
			return null;
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
		
		//### Sonstige Interfaces		
}
