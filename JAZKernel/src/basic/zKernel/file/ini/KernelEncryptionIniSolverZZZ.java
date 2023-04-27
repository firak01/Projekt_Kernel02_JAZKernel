package basic.zKernel.file.ini;

import java.util.EnumSet;
import java.util.Vector;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.crypt.code.CryptAlgorithmMappedValueZZZ;
import basic.zBasic.util.crypt.code.CryptEnumSetFactoryZZZ;
import basic.zBasic.util.crypt.code.ICryptZZZ;
import basic.zBasic.util.crypt.code.KernelCryptAlgorithmFactoryZZZ;
import basic.zBasic.util.datatype.calling.ReferenceZZZ;
import basic.zBasic.util.datatype.enums.EnumSetUtilZZZ;
import basic.zBasic.util.datatype.string.StringArrayZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zKernel.IKernelConfigSectionEntryZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernel.KernelConfigSectionEntryZZZ;

public class KernelEncryptionIniSolverZZZ  extends AbstractKernelIniSolverZZZ  implements IKernelEncryptionIniSolverZZZ{
	public static String sTAG_NAME = "Z:Encrypted";
	public ICryptZZZ objCryptAlgorithmLast = null;
	public KernelEncryptionIniSolverZZZ() throws ExceptionZZZ{
		super();
	}
		
	public KernelEncryptionIniSolverZZZ(String[] saFlag) throws ExceptionZZZ{
		super(saFlag);
		KernelEncryptionIniSolverNew_(saFlag);
	}
	
	public KernelEncryptionIniSolverZZZ(IKernelZZZ objKernel, String[] saFlag) throws ExceptionZZZ{
		super(objKernel,saFlag);
		KernelEncryptionIniSolverNew_(saFlag);
	}
	
	
	private boolean KernelEncryptionIniSolverNew_(String[] saFlagControlIn) throws ExceptionZZZ {
//	 boolean bReturn = false;
//	 String stemp; boolean btemp; 
//	 main:{
//		 	
//	 	//try{	 		
//	 			//setzen der �bergebenen Flags	
//				if(saFlagControlIn != null){
//					for(int iCount = 0;iCount<=saFlagControlIn.length-1;iCount++){
//						stemp = saFlagControlIn[iCount];
//						btemp = setFlag(stemp, true);
//						if(btemp==false){
//							ExceptionZZZ ez = new ExceptionZZZ( "the flag '" + stemp + "' is not available.", IFlagUserZZZ.iERROR_FLAG_UNAVAILABLE, this, ReflectCodeZZZ.getMethodCurrentName()); 
//							throw ez;		 
//						}
//					}
//					if(this.getFlag("init")==true){
//						bReturn = true;
//						break main;
//					}
//				}			
//	 	}//end main:
//		return bReturn;
		return true;
	 }//end function KernelExpressionMathSolverNew_
	
	public Vector computeExpressionAllVector(String sLineWithExpression) throws ExceptionZZZ{		
			Vector vecReturn = new Vector();
			main:{
				if(StringZZZ.isEmpty(sLineWithExpression)) break main;
				
//				IKernelConfigSectionEntryZZZ objReturn=objReturnReference.get();
//				if(objReturn==null) objReturn = new KernelConfigSectionEntryZZZ();//Hier schon die Rückgabe vorbereiten, falls eine weitere Verarbeitung nicht konfiguriert ist.

				
				String sValue=null;  String sCode=null;
				
				//Mehrere Ausdruecke. Dann muss der jeweilige "Rest-Bestandteil" des ExpressionFirst-Vectors weiter zerlegt werden.
				vecReturn = this.computeExpressionFirstVector(sLineWithExpression);			
				String sExpression = (String) vecReturn.get(1);
				if(!StringZZZ.isEmpty(sExpression)){
					
					//++++++++++++++++++++++++++++++++++++++++++++
					//Nun den z:cipher Tag suchen				
					KernelEncryption_CipherZZZ objCipher = new KernelEncryption_CipherZZZ();
					if(objCipher.isExpression(sExpression)){					
						String sCipher = objCipher.compute(sExpression);	
						 
						 //START: TODOGOON: WAS BRINGT NUN DIE ENUMERATION? +++++++++++++++++++
						 EnumSet<?> objEnumSet = CryptEnumSetFactoryZZZ.getInstance().getEnumSet(sCipher);
						 //wie verwenden???? EnumSetUtilZZZ.readEnumConstant_IndexValue(, "ROT13");
						 
						 EnumSet<?> objEnumSet2 =CryptAlgorithmMappedValueZZZ.CipherTypeZZZ.getEnumSet();					 
						 CryptAlgorithmMappedValueZZZ objEnums = new CryptAlgorithmMappedValueZZZ();
						 Class enumClass = objEnums.getEnumClassStatic();					
						 int itest = EnumSetUtilZZZ.readEnumConstant_IndexValue(enumClass, "ROT13");	
						 System.out.println(ReflectCodeZZZ.getPositionCurrent()+ " Wert aus Enum-Klasse ueber EnumSetUtilZZZ itest="+itest);
						 
						 String stest = EnumSetUtilZZZ.readEnumConstant_StringValue(enumClass, "ROT13");
						 System.out.println(ReflectCodeZZZ.getPositionCurrent()+ " Wert aus Enum-Klasse ueber EnumSetUtilZZZ stest="+stest);
						 stest = EnumSetUtilZZZ.readEnumConstant_NameValue(enumClass, "ROT13");
						 System.out.println(ReflectCodeZZZ.getPositionCurrent()+ " Wert aus Enum-Klasse ueber EnumSetUtilZZZ stest="+stest);
						 
						 
						 //ENDE: TODOGOON: WAS BRINGT NUN DIE ENUMERATION? +++++++++++++
						 
						 //Nun mit diesem Schlüssel über eine Factory den SchlüsselAlgorithmus holen
						 KernelCryptAlgorithmFactoryZZZ objFactory = KernelCryptAlgorithmFactoryZZZ.getInstance(objKernel);					 
						 ICryptZZZ objAlgorithm = objFactory.createAlgorithmType(sCipher);
						 this.setCryptAlgorithmType(objAlgorithm); //Damit kann der gefundene Wert durch einen anderen Wert ersetzt werden ohne die CryptAlgorithmFactoryZZZ neu zu bemuehen.
						 
						 //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
						 //+++++ Weitere Parameter suchen und ggfs. dem Algorithmusobjekt hinzufügen.
						 //das ist einfacher als den konstruktor direkt aufzurfen
						 //ICryptZZZ objAlgorithm = objFactory.createAlgorithmTypeByCipher(objKernel, sCipher, iKeyNumber, sCharacterPool);
							
						 
						 
						//TODOGOON20220928;//Es müssen nun für weitere Konstruktoren auch weitere Parameter aus dem sExpression Wert ausgelesen werden.
						 //iKeyNumber für die "Rotation" der Buchstaben
						 //sCharacterPool für die "erlaubten" Buchstaben.
						 //Nachdem diese Werte errechnet worden sind (und ggfs. nicht gefunden wurden) trotdem alle als Parameter übergeben.					 				

						 KernelEncryption_KeyNumberZZZ objKeyNumber = new KernelEncryption_KeyNumberZZZ();
						 if(objKeyNumber.isExpression(sExpression)){					
							String sKeyNumber = objKeyNumber.compute(sExpression);
							if(!StringZZZ.isEmptyTrimmed(sKeyNumber)) {
								if(StringZZZ.isNumeric(sKeyNumber)) {
									int iKeyNumber = StringZZZ.toInteger(sKeyNumber);
									objAlgorithm.setCryptNumber(iKeyNumber);
								}								
							}													
						 }
						 
						 KernelEncryption_KeyStringZZZ objKeyString = new KernelEncryption_KeyStringZZZ();
						 if(objKeyString.isExpression(sExpression)){					
							String sKeyString = objKeyString.compute(sExpression);
							if(!StringZZZ.isEmptyTrimmed(sKeyString)) {
									objAlgorithm.setCryptKey(sKeyString);											
							}													
						 }
						 
						 					
						 KernelEncryption_CharacterPoolZZZ objCharacterPool = new KernelEncryption_CharacterPoolZZZ();
						 if(objCharacterPool.isExpression(sExpression)){					
						 	String sCharacterPool = objCharacterPool.compute(sExpression);
							if(!StringZZZ.isEmpty(sCharacterPool)) {//Merke: Leerzeichen wäre erlaubt								
								objAlgorithm.setCharacterPoolBase(sCharacterPool);
							}																											
						}
						 
						 KernelEncryption_CharacterPoolAdditionalZZZ objCharacterPoolAdditional = new KernelEncryption_CharacterPoolAdditionalZZZ();
						 if(objCharacterPoolAdditional.isExpression(sExpression)){					
						 	String sCharacterPoolAdditional = objCharacterPoolAdditional.compute(sExpression);
							if(!StringZZZ.isEmpty(sCharacterPoolAdditional)) {							
								objAlgorithm.setCharacterPoolAdditional(sCharacterPoolAdditional);
							}																											
						}
						 
						 //saFlagcontrol verarbeiten, Also der String ist mit "," getrennt. 
						 String sFlagControl="";
						 Kernel_FlagControlZZZ objFlagControl = new Kernel_FlagControlZZZ();
						 if(objFlagControl.isExpression(sExpression)){					
							String[] saControl = objFlagControl.computeAsArray(sExpression,",");						
							boolean[] baFound = objAlgorithm.setFlag(saControl, true);
							if(!ArrayUtilZZZ.isEmpty(baFound)) {
								int iCounter = -1;
								for(boolean bFound:baFound) {
									iCounter++;
									if(!bFound) {
										this.getLogObject().WriteLineDate("Flag not available: '"+saControl[iCounter]+"'");
									}
								}
							}													
						 }
						
						 KernelEncryption_CodeZZZ objValue = new KernelEncryption_CodeZZZ();
						 if(objValue.isExpression(sExpression)){
							
							sCode = objValue.compute(sExpression);
//							String sDebug = (String) vecValue.get(1);
//							System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": Value01=" + sDebug);
//							System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": Gesamt-Reststring soweit=" + sExpression);
						}
						
						
						                 //Vielleicht mit einem zusätzlichen Referenze-Objekt als Argument?
						 if(!StringZZZ.isEmpty(sCode)) {
							 //Das ist der reine kodierte Wert. Er gehört in objEntry.getValueEncrypted().
							 //objReturn.setValueEncrypted(sCode);						 
							 sValue = objAlgorithm.decrypt(sCode);
						 }
						 
					}else{
						//Da gibt es wohl nix weiter auszurechen....	also die Werte als String nebeneinander setzen....
						//Nun die z:value-of Einträge suchen, Diese werden jeweils zu eine String.
						KernelEncryption_CodeZZZ objValue = new KernelEncryption_CodeZZZ();
						if(objValue.isExpression(sExpression)){						
							sCode = objValue.compute(sExpression);
						}						
						sValue = sCode;
					}
										
					//Den Wert ersetzen, wenn es was zu ersetzen gibt.
					if(sValue!=null){
						vecReturn.removeElementAt(1);
						vecReturn.add(1, sValue);
					}	
					
					//Das Referenzobjekt auch zurückgeben.
//					objReturnReference.set(objReturn);
				}
			}
			return vecReturn;
		}
	
	public Vector computeExpressionAllVector(String sLineWithExpression, ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference) throws ExceptionZZZ{
	//public Vector computeExpressionAllVector(String sLineWithExpression) throws ExceptionZZZ{
		Vector vecReturn = new Vector();
		main:{
			if(StringZZZ.isEmpty(sLineWithExpression)) break main;
			
			IKernelConfigSectionEntryZZZ objReturn=objReturnReference.get();
			if(objReturn==null) objReturn = new KernelConfigSectionEntryZZZ();//Hier schon die Rückgabe vorbereiten, falls eine weitere Verarbeitung nicht konfiguriert ist.

			
			String sValue=null;  String sCode=null;
			
			//Mehrere Ausdruecke. Dann muss der jeweilige "Rest-Bestandteil" des ExpressionFirst-Vectors weiter zerlegt werden.
			vecReturn = this.computeExpressionFirstVector(sLineWithExpression);			
			String sExpression = (String) vecReturn.get(1);
			if(!StringZZZ.isEmpty(sExpression)){
				
				//++++++++++++++++++++++++++++++++++++++++++++
				//Nun den z:cipher Tag suchen				
				KernelEncryption_CipherZZZ objCipher = new KernelEncryption_CipherZZZ();
				if(objCipher.isExpression(sExpression)){					
					String sCipher = objCipher.compute(sExpression);	
					 
					 //START: TODOGOON: WAS BRINGT NUN DIE ENUMERATION? +++++++++++++++++++
					 EnumSet<?> objEnumSet = CryptEnumSetFactoryZZZ.getInstance().getEnumSet(sCipher);
					 //wie verwenden???? EnumSetUtilZZZ.readEnumConstant_IndexValue(, "ROT13");
					 
					 EnumSet<?> objEnumSet2 =CryptAlgorithmMappedValueZZZ.CipherTypeZZZ.getEnumSet();					 
					 CryptAlgorithmMappedValueZZZ objEnums = new CryptAlgorithmMappedValueZZZ();
					 Class enumClass = objEnums.getEnumClassStatic();					
					 int itest = EnumSetUtilZZZ.readEnumConstant_IndexValue(enumClass, "ROT13");	
					 System.out.println(ReflectCodeZZZ.getPositionCurrent()+ " Wert aus Enum-Klasse ueber EnumSetUtilZZZ itest="+itest);
					 
					 String stest = EnumSetUtilZZZ.readEnumConstant_StringValue(enumClass, "ROT13");
					 System.out.println(ReflectCodeZZZ.getPositionCurrent()+ " Wert aus Enum-Klasse ueber EnumSetUtilZZZ stest="+stest);
					 stest = EnumSetUtilZZZ.readEnumConstant_NameValue(enumClass, "ROT13");
					 System.out.println(ReflectCodeZZZ.getPositionCurrent()+ " Wert aus Enum-Klasse ueber EnumSetUtilZZZ stest="+stest);
					 
					 
					 //ENDE: TODOGOON: WAS BRINGT NUN DIE ENUMERATION? +++++++++++++
					 
					 //Nun mit diesem Schlüssel über eine Factory den SchlüsselAlgorithmus holen
					 KernelCryptAlgorithmFactoryZZZ objFactory = KernelCryptAlgorithmFactoryZZZ.getInstance(objKernel);					 
					 ICryptZZZ objAlgorithm = objFactory.createAlgorithmType(sCipher);
					 this.setCryptAlgorithmType(objAlgorithm); //Damit kann der gefundene Wert durch einen anderen Wert ersetzt werden ohne die CryptAlgorithmFactoryZZZ neu zu bemuehen.
					 
					 //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
					 //+++++ Weitere Parameter suchen und ggfs. dem Algorithmusobjekt hinzufügen.
					 //das ist einfacher als den konstruktor direkt aufzurfen
					 //ICryptZZZ objAlgorithm = objFactory.createAlgorithmTypeByCipher(objKernel, sCipher, iKeyNumber, sCharacterPool);
						
					 
					 
					//TODOGOON20220928;//Es müssen nun für weitere Konstruktoren auch weitere Parameter aus dem sExpression Wert ausgelesen werden.
					 //iKeyNumber für die "Rotation" der Buchstaben
					 //sCharacterPool für die "erlaubten" Buchstaben.
					 //Nachdem diese Werte errechnet worden sind (und ggfs. nicht gefunden wurden) trotdem alle als Parameter übergeben.					 				

					 KernelEncryption_KeyNumberZZZ objKeyNumber = new KernelEncryption_KeyNumberZZZ();
					 if(objKeyNumber.isExpression(sExpression)){					
						String sKeyNumber = objKeyNumber.compute(sExpression);
						if(!StringZZZ.isEmptyTrimmed(sKeyNumber)) {
							if(StringZZZ.isNumeric(sKeyNumber)) {
								int iKeyNumber = StringZZZ.toInteger(sKeyNumber);
								objAlgorithm.setCryptNumber(iKeyNumber);
							}								
						}													
					 }
					 
					 KernelEncryption_KeyStringZZZ objKeyString = new KernelEncryption_KeyStringZZZ();
					 if(objKeyString.isExpression(sExpression)){					
						String sKeyString = objKeyString.compute(sExpression);
						if(!StringZZZ.isEmptyTrimmed(sKeyString)) {
								objAlgorithm.setCryptKey(sKeyString);											
						}													
					 }
					 
					 					
					 KernelEncryption_CharacterPoolZZZ objCharacterPool = new KernelEncryption_CharacterPoolZZZ();
					 if(objCharacterPool.isExpression(sExpression)){					
					 	String sCharacterPool = objCharacterPool.compute(sExpression);
						if(!StringZZZ.isEmpty(sCharacterPool)) {//Merke: Leerzeichen wäre erlaubt								
							objAlgorithm.setCharacterPoolBase(sCharacterPool);
						}																											
					}
					 
					 KernelEncryption_CharacterPoolAdditionalZZZ objCharacterPoolAdditional = new KernelEncryption_CharacterPoolAdditionalZZZ();
					 if(objCharacterPoolAdditional.isExpression(sExpression)){					
					 	String sCharacterPoolAdditional = objCharacterPoolAdditional.compute(sExpression);
						if(!StringZZZ.isEmpty(sCharacterPoolAdditional)) {							
							objAlgorithm.setCharacterPoolAdditional(sCharacterPoolAdditional);
						}																											
					}
					 
					 //saFlagcontrol verarbeiten, Also der String ist mit "," getrennt. 
					 String sFlagControl="";
					 Kernel_FlagControlZZZ objFlagControl = new Kernel_FlagControlZZZ();
					 if(objFlagControl.isExpression(sExpression)){					
						String[] saControl = objFlagControl.computeAsArray(sExpression,",");						
						boolean[] baFound = objAlgorithm.setFlag(saControl, true);
						if(!ArrayUtilZZZ.isEmpty(baFound)) {
							int iCounter = -1;
							for(boolean bFound:baFound) {
								iCounter++;
								if(!bFound) {
									this.getLogObject().WriteLineDate("Flag not available: '"+saControl[iCounter]+"'");
								}
							}
						}													
					 }
					
					 KernelEncryption_CodeZZZ objValue = new KernelEncryption_CodeZZZ();
					 if(objValue.isExpression(sExpression)){
						
						sCode = objValue.compute(sExpression);
//						String sDebug = (String) vecValue.get(1);
//						System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": Value01=" + sDebug);
//						System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": Gesamt-Reststring soweit=" + sExpression);
					}
					
					
					                 //Vielleicht mit einem zusätzlichen Referenze-Objekt als Argument?
					 if(!StringZZZ.isEmpty(sCode)) {
						 //Das ist der reine kodierte Wert. Er gehört in objEntry.getValueEncrypted().
						 objReturn.setValueEncrypted(sCode);						 
						 sValue = objAlgorithm.decrypt(sCode);
					 }
					 
				}else{
					//Da gibt es wohl nix weiter auszurechen....	also die Werte als String nebeneinander setzen....
					//Nun die z:value-of Einträge suchen, Diese werden jeweils zu eine String.
					KernelEncryption_CodeZZZ objValue = new KernelEncryption_CodeZZZ();
					if(objValue.isExpression(sExpression)){						
						sCode = objValue.compute(sExpression);
					}						
					sValue = sCode;
				}
									
				//Den Wert ersetzen, wenn es was zu ersetzen gibt.
				if(sValue!=null){
					vecReturn.removeElementAt(1);
					vecReturn.add(1, sValue);
				}	
				
				//Das Referenzobjekt auch zurückgeben.
				objReturnReference.set(objReturn);
			}
		}
		return vecReturn;
	}
	
	//###### Getter / Setter
	
	
	//### Aus Interface IKernelEncryptionIniSolverZZZ
	@Override
	public boolean getFlag(IKernelEncryptionIniSolverZZZ.FLAGZ objEnum_IKernelEncryptionIniSolverZZZ) {
		return this.getFlag(objEnum_IKernelEncryptionIniSolverZZZ.name());
	}
	
	@Override
	public boolean setFlag(IKernelEncryptionIniSolverZZZ.FLAGZ objEnum_IKernelEncryptionIniSolverZZZ, boolean bFlagValue) throws ExceptionZZZ {
		return this.setFlag(objEnum_IKernelEncryptionIniSolverZZZ.name(), bFlagValue);
	}
	
	@Override
	public boolean proofFlagExists(IKernelEncryptionIniSolverZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
		return this.proofFlagExists(objEnumFlag.name());
	}
	
	@Override
	public boolean[] setFlag(IKernelEncryptionIniSolverZZZ.FLAGZ[] objaEnum_IKernelEncryptionIniSolverZZZ, boolean bFlagValue) throws ExceptionZZZ {
		boolean[] baReturn=null;
		main:{
			if(!ArrayUtilZZZ.isEmpty(objaEnum_IKernelEncryptionIniSolverZZZ)) {
				baReturn = new boolean[objaEnum_IKernelEncryptionIniSolverZZZ.length];
				int iCounter=-1;
				for(IKernelEncryptionIniSolverZZZ.FLAGZ objEnum_IKernelEncryptionIniSolverZZZ:objaEnum_IKernelEncryptionIniSolverZZZ) {
					iCounter++;
					boolean bReturn = this.setFlag(objEnum_IKernelEncryptionIniSolverZZZ, bFlagValue);
					baReturn[iCounter]=bReturn;
				}
			}
		}//end main:
		return baReturn;
	}
	
	//### Aus Interface IKernelExpressionIniZZZ
	public String getExpressionTagName(){
		return KernelEncryptionIniSolverZZZ.sTAG_NAME;
	}
		
	@Override
	public String compute(String sLineWithExpression) throws ExceptionZZZ{
		String sReturn = null;
		main:{			
			boolean bUseEncryption = this.getFlag(IKernelEncryptionIniSolverZZZ.FLAGZ.USEENCRYPTION);
			if(bUseEncryption) {
				sReturn = super.compute(sLineWithExpression);
			}else {
				sReturn = sLineWithExpression;
			}									
		}//end main:
		return sReturn;
	}
		
	@Override
	public String[] computeAsArray(String sLineWithExpression, String sDelimiter) throws ExceptionZZZ{
		String[] saReturn = null;
		main:{
			boolean bUseEncryption = this.getFlag(IKernelEncryptionIniSolverZZZ.FLAGZ.USEENCRYPTION);
			if(bUseEncryption) {
				saReturn = super.computeAsArray(sLineWithExpression, sDelimiter);
			}else {
				saReturn = StringZZZ.explode(sLineWithExpression, sDelimiter);
			}		
		}//end main
		return saReturn;
	}
	
	@Override
	public String computeAsExpression(String sLineWithExpression) throws ExceptionZZZ{
		String sReturn = null;
		main:{			
			boolean bUseEncryption = this.getFlag(IKernelEncryptionIniSolverZZZ.FLAGZ.USEENCRYPTION.name());
			if(bUseEncryption) {
				sReturn = super.computeAsExpression(sLineWithExpression);
			}else {
				sReturn = sLineWithExpression;
			}									
		}//end main:
		return sReturn;
	}
	

	@Override
	public String convert(String sLine) throws ExceptionZZZ {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isStringForComputeRelevant(String sExpressionToProof)
			throws ExceptionZZZ {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isStringForConvertRelevant(String sToProof) throws ExceptionZZZ {
		// TODO Auto-generated method stub
		return false;
	}

	//### Aus IKernelIniSolver
	@Override
	public IKernelConfigSectionEntryZZZ computeAsEntry(String sLineWithExpression) throws ExceptionZZZ {
		IKernelConfigSectionEntryZZZ objReturn = new KernelConfigSectionEntryZZZ(); //Hier schon die Rückgabe vorbereiten, falls eine weitere Verarbeitung nicht konfiguriert ist.
		main:{			
			boolean bUseEncryption = this.getFlag(IKernelEncryptionIniSolverZZZ.FLAGZ.USEENCRYPTION);
			if(bUseEncryption) {
				objReturn = super.computeAsEntry(sLineWithExpression);
			}else {
				objReturn.setValue(sLineWithExpression);
			}									
		}//end main:
		return objReturn;
	}
	
	//### Aus Inteface ICryptUserZZZ
	@Override
	public ICryptZZZ getCryptAlgorithmType() throws ExceptionZZZ {
		return this.objCryptAlgorithmLast;
	}

	@Override
	public void setCryptAlgorithmType(ICryptZZZ objCrypt) {
		this.objCryptAlgorithmLast = objCrypt;
	}

		
}//End class