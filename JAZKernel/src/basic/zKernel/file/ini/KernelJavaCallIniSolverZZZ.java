package basic.zKernel.file.ini;

import java.util.EnumSet;
import java.util.Vector;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.ReflectUtilZZZ;
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

public class KernelJavaCallIniSolverZZZ<T>  extends AbstractKernelIniSolverZZZ<T>  implements IKernelJavaCallIniSolverZZZ{
	private static final long serialVersionUID = 6579389159644435205L;
	public static String sTAG_NAME = "Z:Java";
	public ICryptZZZ objCryptAlgorithmLast = null;
	public KernelJavaCallIniSolverZZZ() throws ExceptionZZZ{
		super();
	}
		
	public KernelJavaCallIniSolverZZZ(String[] saFlag) throws ExceptionZZZ{
		super(saFlag);
		KernelJavaCallIniSolverNew_();
	}
	
	public KernelJavaCallIniSolverZZZ(IKernelZZZ objKernel, String[] saFlag) throws ExceptionZZZ{
		super(objKernel,saFlag);
		KernelJavaCallIniSolverNew_();
	}
	
	
	private boolean KernelJavaCallIniSolverNew_() throws ExceptionZZZ {
		boolean bReturn = false; 
		 main:{
			if(this.getFlag("init")==true){
				bReturn = true;
				break main;
			}
										
			bReturn = true;
	 	}//end main:
		return bReturn;
	 }//end function KernelExpressionMathSolverNew_
	
	
	//### Aus ISolveEnabled		
	/**Methode ueberschreibt die Aufloesung von Pfaden und Ini-Variablen.
	 * @param sLineWithExpression
	 * @param objEntryReference
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 27.04.2023, 15:28:40
	 */	
	@Override
	public String solveParsed(String sExpression) throws ExceptionZZZ {
		return this.solveParsed_(sExpression, null, true);
	}
	
	@Override
	public String solveParsed(String sExpression, boolean bRemoveSurroundingSeparators)throws ExceptionZZZ {
		return this.solveParsed_(sExpression, null, bRemoveSurroundingSeparators);
	}
	
	@Override
	public String solveParsed(String sExpression,ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference) throws ExceptionZZZ {		
		return this.solveParsed_(sExpression, objReturnReference, true);
	}
	
	@Override
	public String solveParsed(String sExpressionIn, ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReferenceIn, boolean bRemoveSurroundingSeparators)	throws ExceptionZZZ {
		return this.solveParsed_(sExpressionIn, objReturnReferenceIn, bRemoveSurroundingSeparators);
	}
	
	private String solveParsed_(String sExpressionIn, ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReferenceIn, boolean bRemoveSurroundingSeparators) throws ExceptionZZZ {		
		String sReturn = sExpressionIn;
		boolean bUseExpression = false; 
		boolean bUseSolver = false;
		boolean bUseEncryption = false;
		
		ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference= null;		
		IKernelConfigSectionEntryZZZ objEntry = null;
		if(objReturnReferenceIn==null) {
			objReturnReference = new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
		}else {
			objReturnReference = objReturnReferenceIn;
		}
		objEntry = objReturnReference.get();
		if(objEntry==null) {
			objEntry = this.getEntryNew(); //Hier schon die Rückgabe vorbereiten, falls eine weitere Verarbeitung nicht konfiguriert ist.
										 //Wichtig: Als oberste Methode immer ein neues Entry-Objekt holen. Dann stellt man sicher, das nicht mit Werten der vorherigen Suche gearbeitet wird.
			objReturnReference.set(objEntry);
		}//Achtung: Das objReturn Objekt NICHT generell uebernehmen. Es verfaelscht bei einem 2. Suchaufruf das Ergebnis.
		objEntry.setRaw(sExpressionIn);
			
		main:{
			if(StringZZZ.isEmptyTrimmed(sExpressionIn)) break main;
			
			bUseExpression = this.getFlag(IIniTagWithExpressionZZZ.FLAGZ.USEEXPRESSION); 
			if(!bUseExpression) break main;
						
			bUseSolver = this.getFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER);
			if(!bUseSolver) break main;
			
			bUseEncryption = this.isSolverEnabledThis(); //this.getFlag(IKernelEncryptionIniSolverZZZ.FLAGZ.USEENCRYPTION);		
			if(!bUseEncryption) break main;
			
			String sExpression = sExpressionIn;
			String sExpressionUsed = sExpression;
			
			String sCode=null;
			
			TODOGOON20241028;//Hier das Framgen aus parseFirstVector
			
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
					 sExpressionUsed = objAlgorithm.decrypt(sCode);
					 if(sExpressionUsed!=null) {
						if(!sExpressionUsed.equals(sCode)) {
							objEntry.isDecrypted(true);
							objEntry.setValueDecrypted(sExpressionUsed);
						}							
					}					
				 }											
			}
			
			sReturn = sExpressionUsed;							
		}//end main:
		
//      //Merke: Wird in aufrufender Methode anhand von .isSolved() gemacht		
//		//Als echten Ergebniswert aber die <Z>-Encryption Tags rausrechnen
//		if(bUseExpression & bUseSolver & bUseEncryption) {
//			String sTagStart = this.getTagStarting();
//			String sTagEnd = this.getTagClosing();
//			String sValue = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sReturn, sTagStart, sTagEnd);												
//			sReturn = sValue;
//		}	
		
		//Als echten Ergebniswert aber die <Z>-Tags ggfs. rausrechnen (von innen nach aussen)
		if(bRemoveSurroundingSeparators & bUseExpression) {
			String sTagStart = "<Z>";
			String sTagEnd = "</Z>";
			String sValue = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sReturn, sTagStart, sTagEnd, true); //also von innen nach aussen!!!												
			sReturn = sValue;
		}
		
		//NUN DEN INNERHALB DER EXPRESSION BERECHUNG ERSTELLTEN WERT uebernehmen
		this.setValue(sReturn);	//Der Handler bekommt die ganze Zeile als Wert	
		if(objEntry!=null) {		
			objEntry.setValue(sReturn);
			if(sExpressionIn!=null) {
				if(!sExpressionIn.equals(sReturn)) objEntry.isSolved(true);
			}
			if(objReturnReferenceIn!=null)objReturnReferenceIn.set(objEntry);
		}
		return sReturn;	
			
	}
	
	
	//### Aus ITagBasicZZZ	
	@Override
	public String getNameDefault() throws ExceptionZZZ {
		return KernelJavaCallIniSolverZZZ.sTAG_NAME;
	}
	
	
	//### aus IParseEnabled		
	//Analog zu KernelJsonMapIniSolverZZZ, KernelZFormulaMathSolver, KernelEncrytptionIniSolver aufbauen...	
	@Override
	public Vector3ZZZ<String>parseFirstVector(String sExpression, ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReferenceIn, boolean bRemoveSurroundingSeparators) throws ExceptionZZZ{		
		return this.parseFirstVector_(sExpression, objReturnReferenceIn, bRemoveSurroundingSeparators);
	}
	
	private Vector3ZZZ<String>parseFirstVector_(String sExpressionIn, ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReferenceIn, boolean bRemoveSurroundingSeparators) throws ExceptionZZZ{		
		Vector3ZZZ<String>vecReturn = null;
		String sReturn = sExpressionIn;
		boolean bUseExpression=false; boolean bUseSolver=false; boolean bUseCall=false;
		
		IKernelConfigSectionEntryZZZ objEntry = null;
		ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReference = null;			
		if(objReturnReferenceIn==null) {
			objReturnReference =  new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();								
			objEntry = new KernelConfigSectionEntryZZZ<T>(this); //this.getEntryNew(); es gingen alle Informationen verloren				
			                                                     //nein, dann gehen alle Informationen verloren   objReturn = this.parseAsEntryNew(sExpression);				
		}else {
			objReturnReference = objReturnReferenceIn;
			objEntry = objReturnReference.get();
		}
		
		if(objEntry==null) {
			//Das Ziel ist es moeglichst viel Informationen aus dem entry "zu retten"
			//Achtung: Das objReturn Objekt NICHT generell versuchen ueber .getEntry() und dann ggfs. .getEntryNew() zu uebernehmen. Es verfaelscht bei einem 2. Suchaufruf das Ergebnis.
			//objEntry = this.getEntry();
			objEntry = new KernelConfigSectionEntryZZZ<T>(this); // =  this.parseAsEntryNew(sExpression);  //nein, dann gehen alle Informationen verloren   objReturn = this.parseAsEntryNew(sExpression);
			objReturnReference.set(objEntry);
		}	
		objEntry.setRaw(sExpressionIn);		
		
		
		main:{			
			if(StringZZZ.isEmpty(sExpressionIn)) break main;
			
			bUseExpression = this.getFlag(IIniTagWithExpressionZZZ.FLAGZ.USEEXPRESSION); 
			if(!bUseExpression) break main;
						
			bUseSolver = this.getFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER);
			if(!bUseSolver) break main;
						
			bUseCall = this.getFlag(IKernelCallIniSolverZZZ.FLAGZ.USECALL);		
			if(!bUseCall) break main;
			
			boolean bUseCallJava = this.getFlag(IKernelJavaCallIniSolverZZZ.FLAGZ.USECALL_JAVA);		
			if(!bUseCallJava) break main;
					
			String sExpression = sExpressionIn;
			
			
			//Mehrere Ausdruecke. Dann muss der jeweilige "Rest-Bestandteil" des ExpressionFirst-Vectors weiter zerlegt werden.
			//Im Aufruf der Eltern-Methode findet ggfs. auch eine Aufloesung von Pfaden und eine Ersetzung von Variablen statt.
			//Z:call drumherum entfernen
			ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReferenceParse = new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
			objReturnReferenceParse.set(objEntry);
			vecReturn = super.parseFirstVector(sExpression, objReturnReferenceParse, bRemoveSurroundingSeparators);			
			if(vecReturn!=null) sReturn = (String) vecReturn.get(1);
			if(StringZZZ.isEmpty(sReturn)) break main;
			
			objEntry.setRaw(sReturn);
			
			//++++ Die Besonderheit ist hier: CALL und JAVA_CALL werden in einer Klasse erledigt....
			if(!sExpression.equals(sReturn)) {
				objEntry.isCall(true);
				objEntry.isJavaCall(true);				
			}
			
			TODOGOON20241028;//das muss irgendwie in einen Methode solveParsed()
			
			///+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			String sValue=null;  String sCode=null;
			String sJavaCallClass = null; String sJavaCallMethod = null;
											
			//++++++++++++++++++++++++++++++++++++++++++++
			//Nun den z:class Tag suchen				
			KernelJavaCall_ClassZZZ objJavaCallClass = new KernelJavaCall_ClassZZZ();
			if(objJavaCallClass.isExpression(sExpression)){		
				
				sExpression = objJavaCallClass.parse(sExpression);
				sJavaCallClass = objJavaCallClass.getValue();
				if(StringZZZ.isEmpty(sJavaCallClass)) break main;
				
				objEntry.setCallingClassname(sJavaCallClass);
				
				//NEIN, wenn die Klasse nicht gefunden werden kann, dann gibt es später eine Fehlermeldung, die dies ausgibt
				//Mit diesem Klassennamen nun das Class-Objekt erstellen
				//objClass = ReflectUtilZZZ.findClass(sJavaCallClass);
				//if(objClass==null) break main;
								
				
			}else{
				//Da gibt es wohl nix weiter auszurechen....	also die Werte als String nebeneinander setzen....
				sValue = sExpression;	
				sReturn = sValue;
				break main;
			}
			
			//++++++++++++++++++++++++++++++++++++++++++++
			//Nun den z:method Tag suchen
			KernelJavaCall_MethodZZZ objJavaCallMethod = new KernelJavaCall_MethodZZZ();
			if(objJavaCallMethod.isExpression(sExpression)){					
				sExpression = objJavaCallMethod.parse(sExpression);
				sJavaCallMethod = objJavaCallMethod.getValue();
				if(StringZZZ.isEmpty(sJavaCallMethod)) break main;
				
				objEntry.setCallingMethodname(sJavaCallMethod);
				
				//NEIN, wenn die Methode nicht gefunden werden kann, dann gibt es später eine Fehlermeldung, die dies ausgibt
				//Mit diesem Klassennamen nun das Method-Objekt erstellen
				//objMethod = ReflectUtilZZZ.findMethodForMethodName(objClass, sJavaCallMethod);
				//if(objMethod==null) break main;

			}else{
				//Da gibt es wohl nix weiter auszurechen....	also die Werte als String nebeneinander setzen....
				sValue = sExpression;
				sReturn = sValue;
				break main;
			}
			
							
			//Nun die Methode aufrufen.
			Object objReturn = ReflectUtilZZZ.invokeStaticMethod(sJavaCallClass,sJavaCallMethod);
			if(objReturn==null)break main;						
			sValue = objReturn.toString();
			sReturn = sValue;									
		}//end main:	
				
		//#################################		
		if(vecReturn!=null) vecReturn.replace(sReturn);
				
		// Z-Tags entfernen.
		if(bRemoveSurroundingSeparators) {
			//++++ Die Besonderheit ist hier: CALL und JAVA_CALL werden in einer Klasse erledigt....
			//     Das Entfernen der umgebenden Tags geht standardmaessig von innen nach aussen.
			if(bUseExpression) {
				if(bUseSolver) {
					if(bUseCall) {
						String sTagStartZCall = "<Z:Call>";
						String sTagEndZCall = "</Z:Call>";
						KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(vecReturn, sTagStartZCall, sTagEndZCall);
					}
				}
								
				String sTagStartZ = "<Z>";
				String sTagEndZ = "</Z>";
				KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(vecReturn, sTagStartZ, sTagEndZ);
			}
		}	
		this.setValue(sReturn);
		if(objEntry!=null) {
			objEntry.setValue(VectorUtilZZZ.implode(vecReturn));	
			if(sExpressionIn!=null) {
				if(!sExpressionIn.equals(sReturn)) objEntry.isParsed(true);
			}				
			if(objReturnReferenceIn!=null) objReturnReferenceIn.set(objEntry);
		}		
		return vecReturn;						
	}
	
	@Override
	public String[] parseAsArray(String sLineWithExpression, String sDelimiter) throws ExceptionZZZ{
		String[] saReturn = null;
		main:{			
			boolean bUse = this.getFlag(IKernelJavaCallIniSolverZZZ.FLAGZ.USECALL_JAVA);
			if(bUse) {
				saReturn = super.parseAsArray(sLineWithExpression, sDelimiter);
			}else {
				saReturn = StringZZZ.explode(sLineWithExpression, sDelimiter);
			}		
		}//end main
		return saReturn;
	}
	
	@Override
	public String parseAsExpression(String sLineWithExpression) throws ExceptionZZZ{
		String sReturn = sLineWithExpression;
		main:{			
			boolean bUse = this.getFlag(IKernelJavaCallIniSolverZZZ.FLAGZ.USECALL_JAVA);
			if(bUse) {
				sReturn = super.parseAsExpression(sLineWithExpression);
			}else {
				sReturn = sLineWithExpression;
			}									
		}//end main:
		return sReturn;
	}

	@Override
	public boolean isConvertRelevant(String sToProof) throws ExceptionZZZ {		
		return false;
	}
	
	//### aus ISolverEnablezZZZ
	@Override
	public boolean isSolverEnabledThis() throws ExceptionZZZ {
		return this.getFlag(IKernelJavaCallIniSolverZZZ.FLAGZ.USECALL_JAVA);
	}


	//### Aus IKernelIniSolver
	@Override
	public IKernelConfigSectionEntryZZZ parseAsEntryNew(String sLineWithExpression) throws ExceptionZZZ {
		IKernelConfigSectionEntryZZZ objReturn = this.getEntryNew(); //null; //new KernelConfigSectionEntryZZZ(); //Hier schon die Rückgabe vorbereiten, falls eine weitere Verarbeitung nicht konfiguriert ist.
		main:{			
			boolean bUseCall = this.getFlag(IKernelCallIniSolverZZZ.FLAGZ.USECALL);
			
			if(bUseCall) {				
				boolean bUseJava = this.getFlag(IKernelJavaCallIniSolverZZZ.FLAGZ.USECALL_JAVA);
				if(bUseJava) {
					ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
					objReturnReference.set(objReturn);
					
					String sReturn = super.parse(sLineWithExpression, objReturnReference);		
					objReturn = objReturnReference.get();
					
					String sValue = objReturn.getValue();
					if(!sLineWithExpression.equals(sValue)) {
						objReturn.setValueCallSolvedAsExpression(sValue);
					}
				}else{
					objReturn = this.getEntryNew();
					objReturn.isCall(true);
					objReturn.setValue(sLineWithExpression);
				}
			}else {
				objReturn = this.getEntryNew();
				objReturn.setValue(sLineWithExpression);
			}									
		}//end main:
		return objReturn;
	}
	
	//#######################################
	//### FLAG Handling
	
	//### Aus Interface IKernelCallIniSolverZZZ
	@Override
	public boolean getFlag(IKernelCallIniSolverZZZ.FLAGZ objEnum_IKernelCallIniSolverZZZ) {
		return this.getFlag(objEnum_IKernelCallIniSolverZZZ.name());
	}
	
	@Override
	public boolean setFlag(IKernelCallIniSolverZZZ.FLAGZ objEnum_IKernelCallIniSolverZZZ, boolean bFlagValue) throws ExceptionZZZ {
		return this.setFlag(objEnum_IKernelCallIniSolverZZZ.name(), bFlagValue);
	}
	
	@Override
	public boolean[] setFlag(IKernelCallIniSolverZZZ.FLAGZ[] objaEnum_IKernelCallIniSolverZZZ, boolean bFlagValue) throws ExceptionZZZ {
		boolean[] baReturn=null;
		main:{
			if(!ArrayUtilZZZ.isNull(objaEnum_IKernelCallIniSolverZZZ)) {
				baReturn = new boolean[objaEnum_IKernelCallIniSolverZZZ.length];
				int iCounter=-1;
				for(IKernelCallIniSolverZZZ.FLAGZ objEnum_IKernelCallIniSolverZZZ:objaEnum_IKernelCallIniSolverZZZ) {
					iCounter++;
					boolean bReturn = this.setFlag(objEnum_IKernelCallIniSolverZZZ, bFlagValue);
					baReturn[iCounter]=bReturn;
				}
			}
		}//end main:
		return baReturn;
	}
	
	@Override
	public boolean proofFlagExists(IKernelCallIniSolverZZZ.FLAGZ objEnum_IKernelCallIniSolverZZZ) throws ExceptionZZZ {
		return this.proofFlagExists(objEnum_IKernelCallIniSolverZZZ.name());
	}
	
	@Override
	public boolean proofFlagSetBefore(IKernelCallIniSolverZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
			return this.proofFlagSetBefore(objEnumFlag.name());
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
	
	@Override
	public boolean proofFlagExists(IKernelJavaCallIniSolverZZZ.FLAGZ objEnum_IKernelJavaCallIniSolverZZZ) throws ExceptionZZZ {
		return this.proofFlagExists(objEnum_IKernelJavaCallIniSolverZZZ.name());
	}
	
	@Override
	public boolean proofFlagSetBefore(IKernelJavaCallIniSolverZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
			return this.proofFlagSetBefore(objEnumFlag.name());
	}
}//End class