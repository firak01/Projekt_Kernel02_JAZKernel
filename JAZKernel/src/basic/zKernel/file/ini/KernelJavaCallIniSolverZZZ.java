package basic.zKernel.file.ini;

import java.util.Vector;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectUtilZZZ;
import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.abstractList.Vector3ZZZ;
import basic.zBasic.util.abstractList.VectorUtilZZZ;
import basic.zBasic.util.crypt.code.ICryptZZZ;
import basic.zBasic.util.datatype.calling.ReferenceZZZ;
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
	
	//##### Getter / Setter
	
	
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
			sReturn = (String) vecReturn.get(1);
			if(StringZZZ.isEmpty(sReturn)) break main;
			
			objEntry.setRaw(sReturn);
			
			//++++ Die Besonderheit ist hier: CALL und JAVA_CALL werden in einer Klasse erledigt....
			if(!sExpression.equals(sReturn)) {
				objEntry.isCall(true);
				objEntry.isJavaCall(true);				
			}
			
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
		
		vecReturn.replace(sReturn);
				
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