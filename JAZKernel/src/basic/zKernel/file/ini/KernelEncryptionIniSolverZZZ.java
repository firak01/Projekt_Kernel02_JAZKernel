package basic.zKernel.file.ini;

import java.util.EnumSet;
import java.util.Vector;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.abstractList.HashMapCaseInsensitiveZZZ;
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
import custom.zKernel.file.ini.FileIniZZZ;

public class KernelEncryptionIniSolverZZZ<T>  extends AbstractKernelIniSolverZZZ<T>  implements IKernelEncryptionIniSolverZZZ{
	private static final long serialVersionUID = 5426925764480431586L;
	public static String sTAG_NAME = "Z:Encrypted";	
	public KernelEncryptionIniSolverZZZ() throws ExceptionZZZ{
		super("init");
		KernelEncryptionIniSolverNew_(null, null);
	}
	
	public KernelEncryptionIniSolverZZZ(String sFlag) throws ExceptionZZZ{
		super(sFlag);
		KernelEncryptionIniSolverNew_(null, null);
	}
	
	public KernelEncryptionIniSolverZZZ(String[] saFlag) throws ExceptionZZZ{
		super(saFlag);
		KernelEncryptionIniSolverNew_(null, null);
	}
	
	public KernelEncryptionIniSolverZZZ(IKernelZZZ objKernel) throws ExceptionZZZ{
		super(objKernel);
		KernelEncryptionIniSolverNew_(null, null);
	}
	
	public KernelEncryptionIniSolverZZZ(IKernelZZZ objKernel, String[] saFlag) throws ExceptionZZZ{
		super(objKernel,saFlag);
		KernelEncryptionIniSolverNew_(null, null);
	}
		
	public KernelEncryptionIniSolverZZZ(FileIniZZZ<T> objFileIni) throws ExceptionZZZ{
		super(objFileIni);//als IKernelUserZZZ - Object
		KernelEncryptionIniSolverNew_(objFileIni, null);
	}
	
	public KernelEncryptionIniSolverZZZ(FileIniZZZ<T> objFileIni, String[] saFlag) throws ExceptionZZZ{
		super(objFileIni, saFlag);//als IKernelUserZZZ - Object
		KernelEncryptionIniSolverNew_(objFileIni, null);
	}
		
	public KernelEncryptionIniSolverZZZ(IKernelZZZ objKernel, FileIniZZZ<T> objFileIni, String[] saFlag) throws ExceptionZZZ{
		super(objKernel, saFlag);
		KernelEncryptionIniSolverNew_(objFileIni, null);
	}
	
	public KernelEncryptionIniSolverZZZ(FileIniZZZ<T> objFileIni, HashMapCaseInsensitiveZZZ<String,String> hmVariable, String[] saFlag) throws ExceptionZZZ{
		super(objFileIni, saFlag); //als IKernelUserZZZ - Object
		KernelEncryptionIniSolverNew_(objFileIni, hmVariable);
	}
	
	public KernelEncryptionIniSolverZZZ(IKernelZZZ objKernel, FileIniZZZ<T> objFileIni, HashMapCaseInsensitiveZZZ<String,String> hmVariable, String[] saFlag) throws ExceptionZZZ{
		super(objKernel, saFlag);
		KernelEncryptionIniSolverNew_(objFileIni, hmVariable);
	}
	
	private boolean KernelEncryptionIniSolverNew_(FileIniZZZ<T> objFileIn, HashMapCaseInsensitiveZZZ<String,String> hmVariableIn) throws ExceptionZZZ {
		 boolean bReturn = false;	
		 main:{		
			if(this.getFlag("init")==true){
				bReturn = true;
				break main;
			}
		
			FileIniZZZ<T> objFile=null;
			if(objFileIn==null ){
				objFile = this.getKernelObject().getFileConfigKernelIni();
				if(objFile==null) {
					ExceptionZZZ ez = new ExceptionZZZ("FileIni-Object", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
					throw ez; 
				}
			}else {
				objFile = objFileIn;
			}

			//Ubernimm ggfs. das Kernel-Objekt aus dem FileIni-Objekt
			if(this.getKernelObject()==null) this.setKernelObject(objFileIni.getKernelObject());
						
			if(hmVariableIn!=null){				
				this.setVariable(hmVariableIn);			//soll zu den Variablen aus derm Ini-File hinzuaddieren, bzw. ersetzen		
			}else {
				
				//Uebernimm ggfs. die Variablen aus dem FileIni-Objekt
				this.setFileConfigKernelIni(objFile);	
				if(objFile.getHashMapVariable()!=null){
					this.setHashMapVariable(objFile.getHashMapVariable());			
				}
			}
						
			bReturn = true;
	 	}//end main:
		return bReturn;
	 }//end function KernelEncryptionIniSolverNew_
	
	//### aus ITagBasicZZZ
	@Override
	public String getNameDefault() throws ExceptionZZZ{
		return KernelEncryptionIniSolverZZZ.sTAG_NAME;
	}
	
	
	//Analog zu KernelJavaCallIniSolverZZZ, KernelJavaCallIniSolverZZZ, KernelJsonMapInisolver, KernelZFormulaMathSolver aufbauen... Der code ist im Parser
	//### aus ISolveEnabled
	@Override
	public boolean isSolverEnabledThis() throws ExceptionZZZ {
		return this.getFlag(IKernelEncryptionIniSolverZZZ.FLAGZ.USEENCRYPTION);
	}
	
	@Override
	public Vector3ZZZ<String> solvePostCustom(Vector3ZZZ<String> vecExpression) throws ExceptionZZZ {
		return this.solvePostCustom_(vecExpression, null, true);
	}
	
	@Override
	public Vector3ZZZ<String> solvePostCustom(Vector3ZZZ<String> vecExpression, boolean bRemoveSurroundingSeparators) throws ExceptionZZZ {		
		return this.solvePostCustom_(vecExpression, null, bRemoveSurroundingSeparators);
	}
	
	//### aus IKernelEntryReferenceSolveUserZZZ
	@Override
	public Vector3ZZZ<String> solvePostCustom(Vector3ZZZ<String> vecExpression, ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference) throws ExceptionZZZ {
		return this.solvePostCustom_(vecExpression, objReturnReference, true);
	}
	
	@Override
	public Vector3ZZZ<String> solvePostCustom(Vector3ZZZ<String> vecExpression, ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReference, boolean bRemoveSurroundingSeparators) throws ExceptionZZZ {		
		return this.solvePostCustom_(vecExpression, objReturnReference, bRemoveSurroundingSeparators);
	}
	
	private Vector3ZZZ<String> solvePostCustom_(Vector3ZZZ<String> vecExpressionIn, ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReferenceIn, boolean bRemoveSurroundingSeparators) throws ExceptionZZZ {		
		Vector3ZZZ<String> vecReturn = null;		
		String sReturn = null;
		String sReturnTag = null;
		String sExpressionIn=null; String sExpression;	
		
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
		sExpressionIn = VectorUtilZZZ.implode(vecExpressionIn);
		this.setRaw(sExpressionIn);
		objEntry.setRaw(sExpressionIn);	
		objEntry.isParseCalled(true);
	
		main:{	
			if(vecExpressionIn==null) break main;
			vecReturn=vecExpressionIn;			
						
			sExpression = (String) vecExpressionIn.get(1);//VectorUtilZZZ.implode(vecExpressionIn);
			sReturnTag = sExpression;
			sReturn = sReturnTag;
													
			//Folgender speziellen code soll auch ohne vorherige Aufloesung etwas setzen, naemlich den Code selbst.
			if(StringZZZ.isEmpty(sExpression)){
				
				//Da gibt es wohl nix weiter auszurechen....	also die Werte als String nebeneinander setzen....
				//Nun die z:value-of Einträge suchen, Diese werden jeweils zu eine String.
				KernelEncryption_CodeZZZ<T> objValue = new KernelEncryption_CodeZZZ<T>();
				if(objValue.isExpression(sExpression)){						
					this.getEntry().setValueEncrypted(sExpression);//Zwischenstand festhalten
					sReturnTag = objValue.parse(sExpression);
					sReturn = sReturnTag;
				}						
				this.getEntry().setValueDecrypted(sReturn);//Zwischenstand festhalten													
			}							
		}//end main:
	
			
		//#################################
		//Den Wert ersetzen, wenn es was zu ersetzen gibt.
		this.setValue(sReturnTag);
		if(vecReturn!=null) vecReturn.replace(sReturn);
						
		if(objEntry!=null) {
			sReturn = VectorUtilZZZ.implode(vecReturn);
			objEntry.setValue(sReturn);	
			if(sExpressionIn!=null) {				 							
				if(!sExpressionIn.equals(sReturn)) {
					objEntry.isExpression(true);
					objEntry.isParsedChanged(true); //zur Not nur, weil die Z-Tags entfernt wurden.									
				}
			}			
			if(objReturnReferenceIn!=null) objReturnReferenceIn.set(objEntry);
			if(objEntry!=null) {						
				sReturn = VectorUtilZZZ.implode(vecReturn);
				objEntry.setValue(sReturn);
				if(objEntry.isEncrypted()) objEntry.setValueEncrypted(sReturn);
				if(sExpressionIn!=null) {
					objEntry.isExpression(true);
					objEntry.isParseCalled(true); 								
					if(!sExpressionIn.equals(sReturn)) objEntry.isParsedChanged(true); //zur Not nur, weil die Z-Tags entfernt wurden.									
				}			
				if(objReturnReferenceIn!=null)objReturnReferenceIn.set(objEntry);//Wichtig: Reference nach aussen zurueckgeben.
				this.adoptEntryValuesMissing(objEntry);
			}
		}
		return vecReturn;
	}
	
	
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
		String sReturnTag = null;
		boolean bUseExpression = false; 
		boolean bUseSolver = false;
		
		ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference= null;		
		IKernelConfigSectionEntryZZZ objEntry = null;
		if(objReturnReferenceIn==null) {
			objReturnReference = new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
		}else {
			objReturnReference = objReturnReferenceIn;
		}
		objEntry = objReturnReference.get();
		if(objEntry==null) {
			//Nein, das holt auch ein neues inneres Objekt und die teilen sich dann die Referenz... objEntry = this.getEntryNew(); //Hier schon die Rückgabe vorbereiten, falls eine weitere Verarbeitung nicht konfiguriert ist.
			 //Wichtig: Als oberste Methode immer ein neues Entry-Objekt holen. Dann stellt man sicher, das nicht mit Werten der vorherigen Suche gearbeitet wird.
			objEntry = new KernelConfigSectionEntryZZZ<T>();
			objReturnReference.set(objEntry);
		}//Achtung: Das objReturn Objekt NICHT generell uebernehmen. Es verfaelscht bei einem 2. Suchaufruf das Ergebnis.
		this.setRaw(sExpressionIn);
		objEntry.setRaw(sExpressionIn);	
		objEntry.isSolveCalled(true);
		
		main:{
			if(StringZZZ.isEmptyTrimmed(sExpressionIn)) break main;
			
			bUseExpression = this.isExpressionEnabledGeneral();
			if(!bUseExpression) break main;
						
			bUseSolver = this.isSolverEnabledEveryRelevant(); //this.getFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER);
			if(!bUseSolver) break main;
						
			String sExpression = sExpressionIn;
			String sExpressionUsed = sExpression;
			
			String sCode=null;
			
			//Nun den z:cipher Tag suchen				
			KernelEncryption_CipherZZZ<T> objCipher = new KernelEncryption_CipherZZZ<T>();
			if(objCipher.isExpression(sExpression)){						
				String sRemaining = objCipher.parse(sExpression); //wichtig: Erst parsen, bevor man den Wert holen kann.
				String sCipher = objCipher.getValue();
				 
				//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
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
				//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
				
				
				//Nun mit diesem Schlüssel über eine Factory den SchlüsselAlgorithmus holen
				KernelCryptAlgorithmFactoryZZZ objFactory = KernelCryptAlgorithmFactoryZZZ.getInstance(objKernel);					 
				ICryptZZZ objAlgorithm = objFactory.createAlgorithmType(sCipher);
				this.setCryptAlgorithmType(objAlgorithm); //Damit kann der gefundene Wert durch einen anderen Wert ersetzt werden ohne die CryptAlgorithmFactoryZZZ neu zu bemuehen.
			 
				//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
				//+++++ Weitere Parameter suchen und ggfs. dem Algorithmusobjekt hinzufügen.
				//das ist einfacher als den konstruktor direkt aufzurufen
				//ICryptZZZ objAlgorithm = objFactory.createAlgorithmTypeByCipher(objKernel, sCipher, iKeyNumber, sCharacterPool);
										
				KernelEncryption_KeyNumberZZZ<T> objKeyNumber = new KernelEncryption_KeyNumberZZZ<T>();
				if(objKeyNumber.isExpression(sExpression)){					
					String sRemaining2 = objKeyNumber.parse(sExpression); //wichtig: Erst parsen, bevor man den Wert holen kann.
					String sKeyNumber = objKeyNumber.getValue();
					if(!StringZZZ.isEmptyTrimmed(sKeyNumber)) {
						if(StringZZZ.isNumeric(sKeyNumber)) {
							int iKeyNumber = StringZZZ.toInteger(sKeyNumber);
							objAlgorithm.setCryptNumber(iKeyNumber);
						}								
					}													
			 	}
			 
			 	ZTagEncryption_KeyStringZZZ<T> objKeyString = new ZTagEncryption_KeyStringZZZ<T>();
				if(objKeyString.isExpression(sExpression)){					
					String sRemaining2 = objKeyString.parse(sExpression);//wichtig: Erst parsen, bevor man den Wert holen kann.
					String sKeyString = objKeyString.getValue();
					if(!StringZZZ.isEmptyTrimmed(sKeyString)) {
							objAlgorithm.setCryptKey(sKeyString);											
					}													
			 	}
			 
			 					
				 KernelEncryption_CharacterPoolZZZ<T> objCharacterPool = new KernelEncryption_CharacterPoolZZZ<T>();
				 if(objCharacterPool.isExpression(sExpression)){					
				 	String sRemaining2 = objCharacterPool.parse(sExpression);//wichtig: Erst parsen, bevor man den Wert holen kann.
				 	String sCharacterPool = objCharacterPool.getValue();
					if(!StringZZZ.isEmpty(sCharacterPool)) {//Merke: Leerzeichen wäre erlaubt								
						objAlgorithm.setCharacterPoolBase(sCharacterPool);
					}																											
			 	}
			 
				 KernelEncryption_CharacterPoolAdditionalZZZ<T> objCharacterPoolAdditional = new KernelEncryption_CharacterPoolAdditionalZZZ<T>();
				 if(objCharacterPoolAdditional.isExpression(sExpression)){					
				 	String sRemaining2 = objCharacterPoolAdditional.parse(sExpression);//wichtig: Erst parsen, bevor man den Wert holen kann.
				 	String sCharacterPoolAdditional = objCharacterPoolAdditional.getValue();
					if(!StringZZZ.isEmpty(sCharacterPoolAdditional)) {							
						objAlgorithm.setCharacterPoolAdditional(sCharacterPoolAdditional);
					}																											
			 	}
			 
				//saFlagcontrol verarbeiten, Also der String ist mit "," getrennt. 
				Kernel_FlagControlZZZ<T> objFlagControl = new Kernel_FlagControlZZZ<T>();
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
								 
			 	KernelEncryption_CodeZZZ<T> objValue = new KernelEncryption_CodeZZZ<T>();
				if(objValue.isExpression(sExpression)){
					this.getEntry().setValueEncrypted(sExpression);	//Zwischenstand festhalten
					String sRemaining2 = objValue.parse(sExpression);//wichtig: Erst parsen, bevor man den Wert holen kann.
					sCode = objValue.getValue();
//							String sDebug = (String) vecValue.get(1);
//							System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": Value01=" + sDebug);
//							System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": Gesamt-Reststring soweit=" + sExpression);
				}
									                 
				 if(!StringZZZ.isEmpty(sCode)) {
					 //Das ist der reine kodierte Wert. Er gehört in objEntry.getValueEncrypted().
					 this.getEntry().isEncrypted(true);
					 objEntry.isEncrypted(true);
					 this.getEntry().setValueEncryptedPart(sCode);	//Zwischenstand festhalten
					 objEntry.setValueEncryptedPart(sCode);
					 sExpressionUsed = objAlgorithm.decrypt(sCode);
					 if(sExpressionUsed!=null) {
						if(!sExpressionUsed.equals(sCode)) {
							this.getEntry().isDecrypted(true);
							objEntry.isDecrypted(true);
							this.getEntry().setValueDecryptedPart(sExpressionUsed);
							objEntry.setValueDecryptedPart(sExpressionUsed);
						}							
					}					
				 }											
			}
			
			sReturnTag = sExpressionUsed;
			sReturn = sReturnTag;
		}//end main:	
				
		//NUN DEN INNERHALB DER EXPRESSION BERECHUNG ERSTELLTEN WERT uebernehmen
		this.setValue(sReturnTag);		
		if(objEntry!=null) {		
			objEntry.setValue(sReturn); //Der Handler bekommt die ganze Zeile als Wert
			if(objEntry.isEncrypted()) {
				this.getEntry().setValueDecrypted(sReturn);
				objEntry.setValueDecrypted(sReturn);
			}
			if(sExpressionIn!=null) {
				if(!sExpressionIn.equals(sReturn)) {
					this.getEntry().isSolveCalled(true);
					
				}
			}						
			if(objReturnReferenceIn!=null)objReturnReferenceIn.set(objEntry);//Wichtig: Reference nach aussen zurueckgeben.
			this.adoptEntryValuesMissing(objEntry);			
		}
		return sReturn;				
	}
	
	

	//### Aus Interface IParseEnabledZZZ		
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
//	@Override
//	public String parseAsExpression(String sLineWithExpression) throws ExceptionZZZ{
//		String sReturn = sLineWithExpression;
//		main:{	
//			
//			//Ergaenzen der Elternmethode um das Nachsehen in einem Flag
//			boolean bUseEncryption = this.getFlag(IKernelEncryptionIniSolverZZZ.FLAGZ.USEENCRYPTION.name());
//			if(bUseEncryption) {
//				sReturn = super.parseAsExpression(sLineWithExpression);
//			}else {
//				sReturn = sLineWithExpression;
//			}									
//		}//end main:
//		return sReturn;
//	}
	
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