package basic.zKernel.file.ini;

import java.lang.reflect.Method;
import java.util.EnumSet;
import java.util.Vector;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.ReflectUtilZZZ;
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

public class KernelJavaCallIniSolverZZZ  extends AbstractKernelIniSolverZZZ  implements IKernelJavaCallIniSolverZZZ{
	public static String sTAG_NAME = "Z:Java";
	public ICryptZZZ objCryptAlgorithmLast = null;
	public KernelJavaCallIniSolverZZZ() throws ExceptionZZZ{
		super();
	}
		
	public KernelJavaCallIniSolverZZZ(String[] saFlag) throws ExceptionZZZ{
		super(saFlag);
		KernelJavaCallIniSolverNew_(saFlag);
	}
	
	public KernelJavaCallIniSolverZZZ(IKernelZZZ objKernel, String[] saFlag) throws ExceptionZZZ{
		super(objKernel,saFlag);
		KernelJavaCallIniSolverNew_(saFlag);
	}
	
	
	private boolean KernelJavaCallIniSolverNew_(String[] saFlagControlIn) throws ExceptionZZZ {
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
	
	public Vector<String>computeExpressionAllVector(String sLineWithExpression) throws ExceptionZZZ{		
			Vector<String>vecReturn = new Vector<String>();
			main:{
				if(StringZZZ.isEmpty(sLineWithExpression)) break main;
			
				String sValue=null;  String sCode=null;
				String sJavaCallClass = null; String sJavaCallMethod = null;
				
				//Mehrere Ausdruecke. Dann muss der jeweilige "Rest-Bestandteil" des ExpressionFirst-Vectors weiter zerlegt werden.
				vecReturn = this.computeExpressionFirstVector(sLineWithExpression);			
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
	

	//### Aus Interface IKernelExpressionIniZZZ
	@Override
	public String getExpressionTagName(){
		return KernelJavaCallIniSolverZZZ.sTAG_NAME;
	}
		
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
	public String computeAsExpression(String sLineWithExpression) throws ExceptionZZZ{
		String sReturn = sLineWithExpression;
		main:{			
			boolean bUse = this.getFlag(IKernelJavaCallIniSolverZZZ.FLAGZ.USECALL_JAVA);
			if(bUse) {
				sReturn = super.computeAsExpression(sLineWithExpression);
			}else {
				sReturn = sLineWithExpression;
			}									
		}//end main:
		return sReturn;
	}
	
	@Override
	public String convert(String sLine) throws ExceptionZZZ {		
		return null;
	}

	@Override
	public boolean isParseRelevant(String sExpressionToProof) throws ExceptionZZZ {		
		return false;
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
	public IKernelConfigSectionEntryZZZ computeAsEntry(String sLineWithExpression) throws ExceptionZZZ {
		IKernelConfigSectionEntryZZZ objReturn = new KernelConfigSectionEntryZZZ(); //Hier schon die Rückgabe vorbereiten, falls eine weitere Verarbeitung nicht konfiguriert ist.
		main:{			
			boolean bUse = this.getFlag(IKernelJavaCallIniSolverZZZ.FLAGZ.USECALL_JAVA);
			if(bUse) {
				objReturn = super.computeAsEntry(sLineWithExpression);				
			}else {
				objReturn.setValue(sLineWithExpression);
			}									
		}//end main:
		return objReturn;
	}
	
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