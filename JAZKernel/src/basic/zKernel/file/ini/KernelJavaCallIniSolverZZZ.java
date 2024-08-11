package basic.zKernel.file.ini;

import java.util.Vector;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectUtilZZZ;
import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.crypt.code.ICryptZZZ;
import basic.zBasic.util.datatype.calling.ReferenceZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zKernel.IKernelConfigSectionEntryZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernel.KernelConfigSectionEntryZZZ;

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
		return true;
	 }//end function KernelExpressionMathSolverNew_
	
	public Vector<String>solveFirstVector(String sLineWithExpression) throws ExceptionZZZ{		
			Vector<String>vecReturn = new Vector<String>();
			main:{
				if(StringZZZ.isEmpty(sLineWithExpression)) break main;
			
				String sValue=null;  String sCode=null;
				String sJavaCallClass = null; String sJavaCallMethod = null;
				
				//Mehrere Ausdruecke. Dann muss der jeweilige "Rest-Bestandteil" des ExpressionFirst-Vectors weiter zerlegt werden.
				vecReturn = this.parseFirstVector(sLineWithExpression);			
				String sExpression = (String) vecReturn.get(1);
				this.getEntry().setRaw(sExpression);
				if(!StringZZZ.isEmpty(sExpression)){
					this.getEntry().isCall(true);
					this.getEntry().isJavaCall(true);
										
					//++++++++++++++++++++++++++++++++++++++++++++
					//Nun den z:class Tag suchen				
					KernelJavaCall_ClassZZZ objJavaCallClass = new KernelJavaCall_ClassZZZ();
					if(objJavaCallClass.isExpression(sExpression)){					
						sJavaCallClass = objJavaCallClass.parse(sExpression);	
						if(StringZZZ.isEmpty(sJavaCallClass)) break main;
						
						this.getEntry().setCallingClassname(sJavaCallClass);
						
						//NEIN, wenn die Klasse nicht gefunden werden kann, dann gibt es später eine Fehlermeldung, die dies ausgibt
						//Mit diesem Klassennamen nun das Class-Objekt erstellen
						//objClass = ReflectUtilZZZ.findClass(sJavaCallClass);
						//if(objClass==null) break main;
						
						
						
					}else{
						//Da gibt es wohl nix weiter auszurechen....	also die Werte als String nebeneinander setzen....
						sValue = sExpression;	
						break main;
					}
					
					//++++++++++++++++++++++++++++++++++++++++++++
					//Nun den z:method Tag suchen
					KernelJavaCall_MethodZZZ objJavaCallMethod = new KernelJavaCall_MethodZZZ();
					if(objJavaCallMethod.isExpression(sExpression)){					
						sJavaCallMethod = objJavaCallMethod.parse(sExpression);	
						if(StringZZZ.isEmpty(sJavaCallMethod)) break main;
						
						this.getEntry().setCallingMethodname(sJavaCallMethod);
						
						//NEIN, wenn die Methode nicht gefunden werden kann, dann gibt es später eine Fehlermeldung, die dies ausgibt
						//Mit diesem Klassennamen nun das Method-Objekt erstellen
						//objMethod = ReflectUtilZZZ.findMethodForMethodName(objClass, sJavaCallMethod);
						//if(objMethod==null) break main;
	
					}else{
						//Da gibt es wohl nix weiter auszurechen....	also die Werte als String nebeneinander setzen....
						sValue = sExpression;	
						break main;
					}
					
								
					//Nun die Methode aufrufen.
					Object objReturn = ReflectUtilZZZ.invokeStaticMethod(sJavaCallClass,sJavaCallMethod);
					if(objReturn==null)break main;						
					sValue = objReturn.toString();
					
					//NUN DEN INNERHALB DER EXPRESSION BERECHUNG ERSTELLTEN WERT in den Return-Vector übernehmen										
					//Den Wert ersetzen, wenn es was zu ersetzen gibt.
					if(sValue!=null){
						if(vecReturn.size()>=1) vecReturn.removeElementAt(1);						
						vecReturn.add(1, sValue);
					}	
				}
			}//end main:
			return vecReturn;
		}
	
	//###### Getter / Setter
	

	//### Aus ITagBasicZZZ
	@Override
	public String getNameDefault(){
		return KernelJavaCallIniSolverZZZ.sTAG_NAME;
	}
		
	//### Aus IParseEnabledZZZ
	@Override
	public String parse(String sLineWithExpression) throws ExceptionZZZ{
		String sReturn = sLineWithExpression;
		main:{	
			
			if(!this.getFlag(IKernelCallIniSolverZZZ.FLAGZ.USECALL)) break main;
			if(!this.getFlag(IKernelJavaCallIniSolverZZZ.FLAGZ.USECALL_JAVA)) break main;
			
			sReturn = super.parse(sLineWithExpression);
			
		}//end main:
		return sReturn;
	}
	
	@Override
	public int parse(String sLineWithExpression, ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference) throws ExceptionZZZ {
		// TODO Auto-generated method stub
		return 0;
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
	
	/* (non-Javadoc)
	 * @see basic.zKernel.file.ini.AbstractKernelIniSolverZZZ#computeAsExpression(java.lang.String)
	 */
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
	public boolean isStringForConvertRelevant(String sToProof) throws ExceptionZZZ {		
		return false;
	}

	//### Aus IKernelIniSolver
	/* (non-Javadoc)
	 * @see basic.zKernel.file.ini.AbstractKernelIniSolverZZZ#computeAsEntry(java.lang.String)
	 */
	@Override
	public IKernelConfigSectionEntryZZZ parseAsEntry(String sLineWithExpression) throws ExceptionZZZ {
		IKernelConfigSectionEntryZZZ objReturn = new KernelConfigSectionEntryZZZ(); //Hier schon die Rückgabe vorbereiten, falls eine weitere Verarbeitung nicht konfiguriert ist.
		main:{			
			boolean bUse = this.getFlag(IKernelJavaCallIniSolverZZZ.FLAGZ.USECALL_JAVA);
			if(bUse) {
				objReturn = super.parseAsEntry(sLineWithExpression);				
			}else {
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
	public boolean proofFlagExists(IKernelCallIniSolverZZZ.FLAGZ objEnum_IKernelCallIniSolverZZZ) throws ExceptionZZZ {
		return this.proofFlagExists(objEnum_IKernelCallIniSolverZZZ.name());
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
}//End class