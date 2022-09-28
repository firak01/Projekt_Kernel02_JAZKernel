package basic.zKernel.file.ini;

import java.util.EnumSet;
import java.util.Vector;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractEnum.EnumSetFactoryZZZ;
import basic.zBasic.util.abstractList.VectorZZZ;
import basic.zBasic.util.crypt.CryptCipherAlgorithmMappedValueZZZ;
import basic.zBasic.util.crypt.ICryptZZZ;
import basic.zBasic.util.crypt.KernelCryptAlgorithmFactoryZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.persistence.jdbc.JdbcDatabaseMappedValueZZZ;
import basic.zKernel.IKernelZFormulaIniZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernel.KernelUseObjectZZZ;
import basic.zKernel.KernelZZZ;
import basic.zKernel.flag.IFlagUserZZZ;
import custom.zKernel.file.ini.FileIniZZZ;

public class KernelEncryptionIniSolverZZZ  extends KernelUseObjectZZZ implements IKernelEncryptionIniSolverZZZ, IKernelZFormulaIniZZZ{
	public KernelEncryptionIniSolverZZZ() throws ExceptionZZZ{
		String[] saFlag = {"init"};
		KernelExpressionMathSolverNew_(saFlag);
	}
		
	public KernelEncryptionIniSolverZZZ(String[] saFlag) throws ExceptionZZZ{		
		KernelExpressionMathSolverNew_(saFlag);
	}
	
	public KernelEncryptionIniSolverZZZ(IKernelZZZ objKernel, String[] saFlag) throws ExceptionZZZ{
		super(objKernel);
		KernelExpressionMathSolverNew_(saFlag);
	}
	
	
	private boolean KernelExpressionMathSolverNew_(String[] saFlagControlIn) throws ExceptionZZZ {
	 boolean bReturn = false;
	 String stemp; boolean btemp; 
	 main:{
		 	
	 	//try{	 		
	 			//setzen der �bergebenen Flags	
				if(saFlagControlIn != null){
					for(int iCount = 0;iCount<=saFlagControlIn.length-1;iCount++){
						stemp = saFlagControlIn[iCount];
						btemp = setFlag(stemp, true);
						if(btemp==false){
							ExceptionZZZ ez = new ExceptionZZZ( "the flag '" + stemp + "' is not available.", IFlagUserZZZ.iERROR_FLAG_UNAVAILABLE, this, ReflectCodeZZZ.getMethodCurrentName()); 
							throw ez;		 
						}
					}
					if(this.getFlag("init")==true){
						bReturn = true;
						break main;
					}
				}			
	 	}//end main:
		return bReturn;
	 }//end function KernelExpressionMathSolverNew_
	
	
	
	public Vector computeExpressionAllVector(String sLineWithExpression) throws ExceptionZZZ{
		Vector vecReturn = new Vector();
		main:{
			if(StringZZZ.isEmpty(sLineWithExpression)) break main;
			
			String sValue=null;  String sCode=null;
			
			//Mehrere Ausdruecke. Dann muss der jeweilige "Rest-Bestandteil" des ExpressionFirst-Vectors weiter zerlegt werden.
			vecReturn = this.computeExpressionFirstVector(sLineWithExpression);			
			String sExpression = (String) vecReturn.get(1);
			if(!StringZZZ.isEmpty(sExpression)){
					
				//Nun den z:cipher Tag suchen				
				KernelEncryption_CipherZZZ objCipher = new KernelEncryption_CipherZZZ();
				if(objCipher.isExpression(sExpression)){					
					String sCipher = objCipher.compute(sExpression);	
					 
					 //TODOGOON: WAS BRINGT NUN DIE ENUMERATION? +++++++++++++++++++
					 EnumSet<?> objEnumSet = EnumSetFactoryZZZ.getInstance().getEnumSet(sExpression);					
					 CryptCipherAlgorithmMappedValueZZZ.CryptCipherTypeZZZ.getEnumSet();					 
					 CryptCipherAlgorithmMappedValueZZZ objEnums = new CryptCipherAlgorithmMappedValueZZZ();
					 Class enumClass = objEnums.getEnumClassStatic();
					 //+++++++++++++
					 

					 //Nun mit diesem Schlüssel über eine Factory den SchlüsselAlgorithmus holen
					 KernelCryptAlgorithmFactoryZZZ objFactory = KernelCryptAlgorithmFactoryZZZ.getInstance();
					 
					 //TODOGOON20220927;//Es müssen nun für weitere Konstruktoren auch weitere Parameter aus dem sExpression Wert ausgelesen werden.
					 //iKeyNumber für die "Rotation" der Buchstaben
					 //sCharacterPool für die "erlaubten" Buchstaben.
					 //Nachdem diese Werte errechnet worden sind (und ggfs. nicht gefunden wurden) trotdem alle als Parameter übergeben.					 				
					 ICryptZZZ objAlgorithm = objFactory.createAlgorithmTypeByCipher(objKernel, sCipher);
					 //ICryptZZZ objAlgorithm = objFactory.createAlgorithmTypeByCipher(objKernel, sCipher, iKeyNumber, sCharacterPool);
					 	
					 KernelEncryption_CodeZZZ objValue = new KernelEncryption_CodeZZZ();
					 if(objValue.isExpression(sExpression)){
						
						sCode = objValue.compute(sExpression);
//						String sDebug = (String) vecValue.get(1);
//						System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": Value01=" + sDebug);
//						System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": Gesamt-Reststring soweit=" + sExpression);
					}
					
					 if(!StringZZZ.isEmpty(sCode)) {
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
			}
		}
		return vecReturn;
	}
	
	/** Gibt einen Vector zurück, in dem das erste Element der Ausdruck VOR der ersten 'Expression' ist. Das 2. Element ist die Expression. Das 3. Element ist der Ausdruck NACH der ersten Expression.
	* @param sLineWithExpression
	* @return
	* 
	* lindhaueradmin; 06.03.2007 11:20:34
	 * @throws ExceptionZZZ 
	 */
	public Vector computeExpressionFirstVector(String sLineWithExpression) throws ExceptionZZZ{
		Vector vecReturn = new Vector();		
		main:{
			vecReturn = StringZZZ.vecMidFirst(sLineWithExpression, KernelEncryptionIniSolverZZZ.getExpressionTagStarting(), KernelEncryptionIniSolverZZZ.getExpressionTagClosing(), false,false);
		}
		return vecReturn;
	}
	
	
	public static boolean isExpression(String sLine){
		boolean bReturn = false;
		main:{
			boolean btemp = StringZZZ.contains(sLine, KernelEncryptionIniSolverZZZ.getExpressionTagStarting(), false);
			if(btemp==false) break main;
		
			btemp = StringZZZ.contains(sLine, KernelEncryptionIniSolverZZZ.getExpressionTagClosing(), false);
			if(btemp==false) break main;
			
			bReturn = true;
		}//end main
		return bReturn;
	}
	
	
	//###### Getter / Setter
	public static String getExpressionTagName(){
		return "Z:encrypted";
	}
	public static String getExpressionTagStarting(){
		return "<" + KernelEncryptionIniSolverZZZ.getExpressionTagName() + ">";
	}
	public static String getExpressionTagClosing(){
		return "</" + KernelEncryptionIniSolverZZZ.getExpressionTagName() + ">"; 
	}	
	public static String getExpressionTagEmpty(){
		return "<" + KernelEncryptionIniSolverZZZ.getExpressionTagName() + "/>";
	}

	//### Aus Interface IKernelExpressionIniZZZ
	@Override
	public boolean isStringForConvertRelevant(String sStringToProof) throws ExceptionZZZ {
		boolean bReturn=false;
		
		//Hier noch was Relevantes für die KernelExpressionIniConverter-Klasse finden.
//				if(StringZZZ.isEmpty(sToProof)){
//					bReturn = true;
//				}
		return bReturn;
	}
	
	@Override
	public String compute(String sLineWithExpression) throws ExceptionZZZ{
		String sReturn = null;
		main:{
			if(StringZZZ.isEmpty(sLineWithExpression)) break main;
			
			boolean bUseEncryption = this.getFlag(IKernelEncryptionIniSolverZZZ.FLAGZ.USEENCRYPTION.name());
			if(bUseEncryption) {
				Vector vecAll = this.computeExpressionAllVector(sLineWithExpression);
				
				//Der Vector ist schon so aufbereiten, dass hier nur noch "zusammenaddiert" werden muss
				sReturn = VectorZZZ.implode(vecAll);
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
}//End class