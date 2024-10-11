package basic.zKernel.file.ini;

import java.util.EnumSet;
import java.util.Vector;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.abstractList.Vector3ZZZ;
import basic.zBasic.util.abstractList.VectorUtilZZZ;
import basic.zBasic.util.crypt.code.CryptAlgorithmMappedValueZZZ;
import basic.zBasic.util.crypt.code.CryptEnumSetFactoryZZZ;
import basic.zBasic.util.crypt.code.ICryptZZZ;
import basic.zBasic.util.crypt.code.KernelCryptAlgorithmFactoryZZZ;
import basic.zBasic.util.datatype.calling.ReferenceZZZ;
import basic.zBasic.util.datatype.enums.EnumSetUtilZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zKernel.IKernelConfigSectionEntryZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernel.KernelConfigSectionEntryZZZ;
import basic.zKernel.config.KernelConfigSectionEntryUtilZZZ;

public class KernelEncryptionIniSolverZZZ<T>  extends AbstractKernelIniSolverZZZ<T>  implements IKernelEncryptionIniSolverZZZ{
	private static final long serialVersionUID = 5426925764480431586L;
	public static String sTAG_NAME = "Z:Encrypted";
	public ICryptZZZ objCryptAlgorithmLast = null;
	public KernelEncryptionIniSolverZZZ() throws ExceptionZZZ{
		super("init");
		KernelEncryptionIniSolverNew_();
	}
		
	public KernelEncryptionIniSolverZZZ(String[] saFlag) throws ExceptionZZZ{
		super(saFlag);
		KernelEncryptionIniSolverNew_();
	}
	
	public KernelEncryptionIniSolverZZZ(IKernelZZZ objKernel, String[] saFlag) throws ExceptionZZZ{
		super(objKernel,saFlag);
		KernelEncryptionIniSolverNew_();
	}
	
	
	private boolean KernelEncryptionIniSolverNew_() throws ExceptionZZZ {
		 boolean bReturn = false;
		 main:{	
			if(this.getFlag("init")==true){
				bReturn = true;
				break main;
			}
			
			bReturn = true;
		}//end main:
		return bReturn;
	 }//end function KernelEncryptionIniSolverNew_
	
	//Analog zu KernelJavaCallIniSolverZZZ, KernelJavaCallIniSolverZZZ, KernelJsonMapInisolver, KernelZFormulaMathSolver aufbauen... Der code ist im Parser
	@Override
	public Vector3ZZZ<String> parseFirstVector(String sLineWithExpression, boolean bRemoveSurroundingSeparators) throws ExceptionZZZ {		
		return this.parseFirstVector_(sLineWithExpression, null, bRemoveSurroundingSeparators);
	}
	
	@Override
	public Vector3ZZZ<String> parseFirstVector(String sLineWithExpression, ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReference, boolean bRemoveSurroundingSeparators) throws ExceptionZZZ {		
		return this.parseFirstVector_(sLineWithExpression, objReturnReference, bRemoveSurroundingSeparators);
	}
	
	private Vector3ZZZ<String> parseFirstVector_(String sExpressionIn, ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReferenceIn, boolean bRemoveSurroundingSeparators) throws ExceptionZZZ {		
		Vector3ZZZ<String> vecReturn = new Vector3ZZZ<String>();
		String sReturn=sExpressionIn;
		boolean bUseExpression=false;
		
		IKernelConfigSectionEntryZZZ objEntry = null;
		ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReference = null;
		if(objReturnReferenceIn==null) {				
			objReturnReference = new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();								
		}else {
			objReturnReference = objReturnReferenceIn;
			objEntry = objReturnReference.get();
		}
		if(objEntry==null) {
			//Achtung: Das objReturn Objekt NICHT generell mit .getEntry() und darin ggfs. .getEntryNew() versuchen zu uebernehmen. Es verfaelscht bei einem 2. Suchaufruf das Ergebnis.
			objEntry = new KernelConfigSectionEntryZZZ<T>(this); //Das Ziel ist es moeglichst viel Informationen aus dem entry "zu retten"      =  this.parseAsEntryNew(sExpression);  //nein, dann gehen alle Informationen verloren   objReturn = this.parseAsEntryNew(sExpression);
			objReturnReference.set(objEntry);
		}							
		objEntry.setRaw(sExpressionIn);
	
		main:{			
			if(StringZZZ.isEmpty(sExpressionIn)) break main;
			
			bUseExpression = this.getFlag(IIniTagWithExpressionZZZ.FLAGZ.USEEXPRESSION); 
			if(!bUseExpression) break main;
						
			boolean bUseSolver = this.getFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER);
			if(!bUseSolver) break main;
			
			boolean bUseEncryption = this.getFlag(IKernelEncryptionIniSolverZZZ.FLAGZ.USEENCRYPTION);		
			if(!bUseEncryption) break main;
			
			String sExpression = sExpressionIn;
			
			//Mehrere Ausdruecke. Dann muss der jeweilige "Rest-Bestandteil" des ExpressionFirst-Vectors weiter zerlegt werden.
			//Im Aufruf der Eltern-Methode findet ggfs. auch eine Aufloesung von Pfaden und eine Ersetzung von Variablen statt.
			//Z:Encryption drumherum entfernen
			vecReturn = super.parseFirstVector(sExpression, bRemoveSurroundingSeparators);			
			sExpression = (String) vecReturn.get(1);				
			if(!StringZZZ.isEmpty(sExpression)){
			
			this.getEntry().setRaw(sExpression);
				
			//TODOGOON 20241011; //Das muss in solveParsed verschoben werden!!!!	
			//++++++++++++++++++++++++++++++++++++++++++++
			String sCode=null;
				
			//Nun den z:cipher Tag suchen				
			KernelEncryption_CipherZZZ objCipher = new KernelEncryption_CipherZZZ();
			if(objCipher.isExpression(sExpression)){						
				String sRemaining = objCipher.parse(sExpression);
				String sCipher = objCipher.getValue();
				 
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
				 //das ist einfacher als den konstruktor direkt aufzurufen
				 //ICryptZZZ objAlgorithm = objFactory.createAlgorithmTypeByCipher(objKernel, sCipher, iKeyNumber, sCharacterPool);
										
				 KernelEncryption_KeyNumberZZZ objKeyNumber = new KernelEncryption_KeyNumberZZZ();
				 if(objKeyNumber.isExpression(sExpression)){					
					String sRemainig = objKeyNumber.parse(sExpression);
					String sKeyNumber = objKeyNumber.getValue();
					if(!StringZZZ.isEmptyTrimmed(sKeyNumber)) {
						if(StringZZZ.isNumeric(sKeyNumber)) {
							int iKeyNumber = StringZZZ.toInteger(sKeyNumber);
							objAlgorithm.setCryptNumber(iKeyNumber);
						}								
					}													
			 	}
			 
			 	ZTagEncryption_KeyStringZZZ objKeyString = new ZTagEncryption_KeyStringZZZ();
				if(objKeyString.isExpression(sExpression)){					
					String sRemaining2 = objKeyString.parse(sExpression);
					String sKeyString = objKeyString.getValue();
					if(!StringZZZ.isEmptyTrimmed(sKeyString)) {
							objAlgorithm.setCryptKey(sKeyString);											
					}													
			 	}
			 
			 					
				 KernelEncryption_CharacterPoolZZZ objCharacterPool = new KernelEncryption_CharacterPoolZZZ();
				 if(objCharacterPool.isExpression(sExpression)){					
				 	String sRemaining3 = objCharacterPool.parse(sExpression);
				 	String sCharacterPool = objCharacterPool.getValue();
					if(!StringZZZ.isEmpty(sCharacterPool)) {//Merke: Leerzeichen wäre erlaubt								
						objAlgorithm.setCharacterPoolBase(sCharacterPool);
					}																											
			 	}
			 
				 KernelEncryption_CharacterPoolAdditionalZZZ objCharacterPoolAdditional = new KernelEncryption_CharacterPoolAdditionalZZZ();
				 if(objCharacterPoolAdditional.isExpression(sExpression)){					
				 	String sRemaining4 = objCharacterPoolAdditional.parse(sExpression);
				 	String sCharacterPoolAdditional = objCharacterPoolAdditional.getValue();
					if(!StringZZZ.isEmpty(sCharacterPoolAdditional)) {							
						objAlgorithm.setCharacterPoolAdditional(sCharacterPoolAdditional);
					}																											
			 	}
			 
				 //saFlagcontrol verarbeiten, Also der String ist mit "," getrennt. 
				 String sFlagControl="";
				 Kernel_FlagControlZZZ objFlagControl = new Kernel_FlagControlZZZ();
				 if(objFlagControl.isExpression(sExpression)){					
					String[] saControl = objFlagControl.parseAsArray(sExpression,",");						
					boolean[] baFound = objAlgorithm.setFlag(saControl, true);
					if(!ArrayUtilZZZ.isNull(baFound)) {
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
					this.getEntry().setValueEncrypted(sExpression);	//Zwischenstand festhalten
					sRemaining = objValue.parse(sExpression);
					sCode = objValue.getValue();
//							String sDebug = (String) vecValue.get(1);
//							System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": Value01=" + sDebug);
//							System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": Gesamt-Reststring soweit=" + sExpression);
				}
									                 
				 if(!StringZZZ.isEmpty(sCode)) {
					 //Das ist der reine kodierte Wert. Er gehört in objEntry.getValueEncrypted().
					 this.getEntry().setValueEncrypted(sCode);	//Zwischenstand festhalten
					 sReturn = objAlgorithm.decrypt(sCode);
					 this.getEntry().setValueDecrypted(sReturn); //Zwischenstand festhalten
				 }
				 
			}else{
				//Da gibt es wohl nix weiter auszurechen....	also die Werte als String nebeneinander setzen....
				//Nun die z:value-of Einträge suchen, Diese werden jeweils zu eine String.
				KernelEncryption_CodeZZZ objValue = new KernelEncryption_CodeZZZ();
				if(objValue.isExpression(sExpression)){						
					this.getEntry().setValueEncrypted(sExpression);//Zwischenstand festhalten
					String sRemaining = objValue.parse(sExpression);
					sCode = objValue.getValue();
				}						
				this.getEntry().setValueDecrypted(sCode);//Zwischenstand festhalten
				sReturn = sCode;						
			}													
		}
	}//end main:
	
		
	//#################################
	//Den Wert ersetzen, wenn es was zu ersetzen gibt.
	vecReturn.replace(sReturn);
	
	// Z-Tags entfernen.
	if(bRemoveSurroundingSeparators) {
		if(bUseExpression) {
			String sTagStartZ = "<Z>";
			String sTagEndZ = "</Z>";
			KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(vecReturn, sTagStartZ, sTagEndZ);
		}
	}		

	this.setValue((String) vecReturn.get(1));
	if(objEntry!=null) {
		objEntry.setValue(VectorUtilZZZ.implode(vecReturn));	
		if(sExpressionIn!=null) {
			if(!sExpressionIn.equals(sReturn)) objEntry.isParsed(true);
		}				
		if(objReturnReferenceIn!=null) objReturnReferenceIn.set(objEntry);
	}
	return vecReturn;
	}
	
	
//	@Override
//	public Vector<String> solveFirstVector(String sLineWithExpression,ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference) throws ExceptionZZZ {		
//		Vector<String> vecReturn = new Vector<String>();
//		main:{
//			if(StringZZZ.isEmpty(sLineWithExpression)) break main;
//		
//			String sValue=null;  String sCode=null;
//			
//			//Mehrere Ausdruecke. Dann muss der jeweilige "Rest-Bestandteil" des ExpressionFirst-Vectors weiter zerlegt werden.
//			vecReturn = this.parseFirstVector(sLineWithExpression);			
//			String sExpression = (String) vecReturn.get(1);				
//			if(!StringZZZ.isEmpty(sExpression)){
//				
//				//++++++++++++++++++++++++++++++++++++++++++++
//				//Nun den z:cipher Tag suchen				
//				KernelEncryption_CipherZZZ objCipher = new KernelEncryption_CipherZZZ();
//				if(objCipher.isExpression(sExpression)){					
//					String sCipher = objCipher.parse(sExpression);	
//					 
//					 //START: TODOGOON: WAS BRINGT NUN DIE ENUMERATION? +++++++++++++++++++
//					 EnumSet<?> objEnumSet = CryptEnumSetFactoryZZZ.getInstance().getEnumSet(sCipher);
//					 //wie verwenden???? EnumSetUtilZZZ.readEnumConstant_IndexValue(, "ROT13");
//					 
//					 EnumSet<?> objEnumSet2 =CryptAlgorithmMappedValueZZZ.CipherTypeZZZ.getEnumSet();					 
//					 CryptAlgorithmMappedValueZZZ objEnums = new CryptAlgorithmMappedValueZZZ();
//					 Class enumClass = objEnums.getEnumClassStatic();					
//					 int itest = EnumSetUtilZZZ.readEnumConstant_IndexValue(enumClass, "ROT13");	
//					 System.out.println(ReflectCodeZZZ.getPositionCurrent()+ " Wert aus Enum-Klasse ueber EnumSetUtilZZZ itest="+itest);
//					 
//					 String stest = EnumSetUtilZZZ.readEnumConstant_StringValue(enumClass, "ROT13");
//					 System.out.println(ReflectCodeZZZ.getPositionCurrent()+ " Wert aus Enum-Klasse ueber EnumSetUtilZZZ stest="+stest);
//					 stest = EnumSetUtilZZZ.readEnumConstant_NameValue(enumClass, "ROT13");
//					 System.out.println(ReflectCodeZZZ.getPositionCurrent()+ " Wert aus Enum-Klasse ueber EnumSetUtilZZZ stest="+stest);
//					 //ENDE: TODOGOON: WAS BRINGT NUN DIE ENUMERATION? +++++++++++++
//					 
//					 //Nun mit diesem Schlüssel über eine Factory den SchlüsselAlgorithmus holen
//					 KernelCryptAlgorithmFactoryZZZ objFactory = KernelCryptAlgorithmFactoryZZZ.getInstance(objKernel);					 
//					 ICryptZZZ objAlgorithm = objFactory.createAlgorithmType(sCipher);
//					 this.setCryptAlgorithmType(objAlgorithm); //Damit kann der gefundene Wert durch einen anderen Wert ersetzt werden ohne die CryptAlgorithmFactoryZZZ neu zu bemuehen.
//					 
//					 //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
//					 //+++++ Weitere Parameter suchen und ggfs. dem Algorithmusobjekt hinzufügen.
//					 //das ist einfacher als den konstruktor direkt aufzurufen
//					 //ICryptZZZ objAlgorithm = objFactory.createAlgorithmTypeByCipher(objKernel, sCipher, iKeyNumber, sCharacterPool);
//											
//					 KernelEncryption_KeyNumberZZZ objKeyNumber = new KernelEncryption_KeyNumberZZZ();
//					 if(objKeyNumber.isExpression(sExpression)){					
//						String sKeyNumber = objKeyNumber.parse(sExpression);
//						if(!StringZZZ.isEmptyTrimmed(sKeyNumber)) {
//							if(StringZZZ.isNumeric(sKeyNumber)) {
//								int iKeyNumber = StringZZZ.toInteger(sKeyNumber);
//								objAlgorithm.setCryptNumber(iKeyNumber);
//							}								
//						}													
//					 }
//					 
//					 ZTagEncryption_KeyStringZZZ objKeyString = new ZTagEncryption_KeyStringZZZ();
//					 if(objKeyString.isExpression(sExpression)){					
//						String sKeyString = objKeyString.parse(sExpression);
//						if(!StringZZZ.isEmptyTrimmed(sKeyString)) {
//								objAlgorithm.setCryptKey(sKeyString);											
//						}													
//					 }
//					 
//					 					
//					 KernelEncryption_CharacterPoolZZZ objCharacterPool = new KernelEncryption_CharacterPoolZZZ();
//					 if(objCharacterPool.isExpression(sExpression)){					
//					 	String sCharacterPool = objCharacterPool.parse(sExpression);
//						if(!StringZZZ.isEmpty(sCharacterPool)) {//Merke: Leerzeichen wäre erlaubt								
//							objAlgorithm.setCharacterPoolBase(sCharacterPool);
//						}																											
//					}
//					 
//					 KernelEncryption_CharacterPoolAdditionalZZZ objCharacterPoolAdditional = new KernelEncryption_CharacterPoolAdditionalZZZ();
//					 if(objCharacterPoolAdditional.isExpression(sExpression)){					
//					 	String sCharacterPoolAdditional = objCharacterPoolAdditional.parse(sExpression);
//						if(!StringZZZ.isEmpty(sCharacterPoolAdditional)) {							
//							objAlgorithm.setCharacterPoolAdditional(sCharacterPoolAdditional);
//						}																											
//					}
//					 
//					 //saFlagcontrol verarbeiten, Also der String ist mit "," getrennt. 
//					 String sFlagControl="";
//					 Kernel_FlagControlZZZ objFlagControl = new Kernel_FlagControlZZZ();
//					 if(objFlagControl.isExpression(sExpression)){					
//						String[] saControl = objFlagControl.parseAsArray(sExpression,",");						
//						boolean[] baFound = objAlgorithm.setFlag(saControl, true);
//						if(!ArrayUtilZZZ.isNull(baFound)) {
//							int iCounter = -1;
//							for(boolean bFound:baFound) {
//								iCounter++;
//								if(!bFound) {
//									this.getLogObject().WriteLineDate("Flag not available: '"+saControl[iCounter]+"'");
//								}
//							}
//						}													
//					 }
//					
//					 KernelEncryption_CodeZZZ objValue = new KernelEncryption_CodeZZZ();
//					 if(objValue.isExpression(sExpression)){
//						this.getEntry().setValueEncrypted(sExpression);	//Zwischenstand festhalten
//						sCode = objValue.parse(sExpression);
////							String sDebug = (String) vecValue.get(1);
////							System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": Value01=" + sDebug);
////							System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": Gesamt-Reststring soweit=" + sExpression);
//					}
//											                 
//					 if(!StringZZZ.isEmpty(sCode)) {
//						 //Das ist der reine kodierte Wert. Er gehört in objEntry.getValueEncrypted().
//						 this.getEntry().setValueEncrypted(sCode);	//Zwischenstand festhalten
//						 sValue = objAlgorithm.decrypt(sCode);
//						 this.getEntry().setValueDecrypted(sValue); //Zwischenstand festhalten
//					 }
//					 
//				}else{
//					//Da gibt es wohl nix weiter auszurechen....	also die Werte als String nebeneinander setzen....
//					//Nun die z:value-of Einträge suchen, Diese werden jeweils zu eine String.
//					KernelEncryption_CodeZZZ objValue = new KernelEncryption_CodeZZZ();
//					if(objValue.isExpression(sExpression)){						
//						this.getEntry().setValueEncrypted(sExpression);//Zwischenstand festhalten
//						sCode = objValue.parse(sExpression);
//					}						
//					this.getEntry().setValueDecrypted(sCode);//Zwischenstand festhalten
//					sValue = sCode;						
//				}
//									
//				//Den Wert ersetzen, wenn es was zu ersetzen gibt.
//				if(sValue!=null){
//					vecReturn.removeElementAt(1);
//					vecReturn.add(1, sValue);
//				}	
//			}
//		}
//		return vecReturn;
//	}
	
	//### aus ITagBasicZZZ
	@Override
	public String getNameDefault() throws ExceptionZZZ{
		return KernelEncryptionIniSolverZZZ.sTAG_NAME;
	}

	//### Aus Interface IParseEnabledZZZ
//	@Override
//	public String parse(String sLineWithExpression) throws ExceptionZZZ{
//		String sReturn = sLineWithExpression;
//		main:{			
//			
//			//Ergaenzen der Elternmethode um das nachsehen in einem Flag
//			boolean bUseEncryption = this.getFlag(IKernelEncryptionIniSolverZZZ.FLAGZ.USEENCRYPTION);
//			if(bUseEncryption) {
//				sReturn = super.parse(sLineWithExpression);
//			}else {
//				sReturn = sLineWithExpression;
//			}									
//		}//end main:
//		return sReturn;
//	}
		
	@Override
	public String[] parseAsArray(String sLineWithExpression, String sDelimiter) throws ExceptionZZZ{
		String[] saReturn = null;
		main:{
			
			//Ergaenzen der Elternmethode um das nachsehen in einem Flag
			boolean bUseEncryption = this.getFlag(IKernelEncryptionIniSolverZZZ.FLAGZ.USEENCRYPTION);
			if(bUseEncryption) {
				saReturn = super.parseAsArray(sLineWithExpression, sDelimiter);
			}else {
				saReturn = StringZZZ.explode(sLineWithExpression, sDelimiter);
			}		
		}//end main
		return saReturn;
	}

	//### aus IExpressionUserZZZ
	@Override
	public String parseAsExpression(String sLineWithExpression) throws ExceptionZZZ{
		String sReturn = sLineWithExpression;
		main:{	
			
			//Ergaenzen der Elternmethode um das Nachsehen in einem Flag
			boolean bUseEncryption = this.getFlag(IKernelEncryptionIniSolverZZZ.FLAGZ.USEENCRYPTION.name());
			if(bUseEncryption) {
				sReturn = super.parseAsExpression(sLineWithExpression);
			}else {
				sReturn = sLineWithExpression;
			}									
		}//end main:
		return sReturn;
	}
	
	//### aus IConvertableZZZ
	@Override
	public boolean isConvertRelevant(String sToProof) throws ExceptionZZZ {		
		return false;
	}
	
	//### Aus IKernelIniSolver
	/* (non-Javadoc)
	 * @see basic.zKernel.file.ini.AbstractKernelIniSolverZZZ#computeAsEntry(java.lang.String)
	 */
	@Override
	public IKernelConfigSectionEntryZZZ parseAsEntryNew(String sLineWithExpression) throws ExceptionZZZ {
		IKernelConfigSectionEntryZZZ objReturn = new KernelConfigSectionEntryZZZ(); //Hier schon die Rückgabe vorbereiten, falls eine weitere Verarbeitung nicht konfiguriert ist.
		main:{		
			
			//Ergaenzen der Elternmethode um das Nachsehen in einem Flag
			boolean bUseEncryption = this.getFlag(IKernelEncryptionIniSolverZZZ.FLAGZ.USEENCRYPTION);
			if(bUseEncryption) {
				objReturn = super.parseAsEntryNew(sLineWithExpression);
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
	
	//###### Getter / Setter
	
	//##########################
	//### FLAG Handling
	
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
	public boolean[] setFlag(IKernelEncryptionIniSolverZZZ.FLAGZ[] objaEnum_IKernelEncryptionIniSolverZZZ, boolean bFlagValue) throws ExceptionZZZ {
		boolean[] baReturn=null;
		main:{
			if(!ArrayUtilZZZ.isNull(objaEnum_IKernelEncryptionIniSolverZZZ)) {
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

	@Override
	public boolean proofFlagExists(IKernelEncryptionIniSolverZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
		return this.proofFlagExists(objEnumFlag.name());
	}
	
	@Override
	public boolean proofFlagSetBefore(IKernelEncryptionIniSolverZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
			return this.proofFlagSetBefore(objEnumFlag.name());
	}
	
		
}//End class