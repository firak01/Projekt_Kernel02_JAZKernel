package basic.zKernel.file.ini;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zKernel.AbstractKernelUseObjectZZZ;
import basic.zKernel.IKernelZFormulaIniZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernel.flag.IFlagZUserZZZ;
import custom.zKernel.file.ini.FileIniZZZ;

/**Grundliegende Idee dahinter stammt aus den Converter-Klassen in JSF. 
 * 
 * 20190123
 * @author Fritz Lindhauer
 * @param <T>
 *
 */
public class KernelZFormulaIniConverterZZZ<T> extends AbstractKernelUseObjectZZZ<T>{
	private static final long serialVersionUID = -8982048164029456581L;
	private FileIniZZZ objFileIni=null;
	
	//###########################################
	public KernelZFormulaIniConverterZZZ() throws ExceptionZZZ{
		super("init");
		KernelExpressionIniConverterNew_(null);
	}
	
	public KernelZFormulaIniConverterZZZ(FileIniZZZ objFileIni) throws ExceptionZZZ{
		super(objFileIni.getKernelObject());
		KernelExpressionIniConverterNew_(objFileIni);
	}
	
	public KernelZFormulaIniConverterZZZ(FileIniZZZ objFileIni, String[] saFlag) throws ExceptionZZZ{
		super(objFileIni.getKernelObject(),saFlag);
		KernelExpressionIniConverterNew_(objFileIni);
	}
	
	public KernelZFormulaIniConverterZZZ(IKernelZZZ objKernel, FileIniZZZ objFileIni, String[] saFlag) throws ExceptionZZZ{
		super(objKernel,saFlag);
		KernelExpressionIniConverterNew_(objFileIni);
	}
	
	private boolean KernelExpressionIniConverterNew_(FileIniZZZ objFileIn) throws ExceptionZZZ {
		 boolean bReturn = false;
		 main:{
			 
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
		 	}//end main:
			return bReturn;
		 }//end function KernelExpressionIniConverterNew_
		
	//### GETTER / SETTER ########################
	public void setFileIni(FileIniZZZ objFileIni){
		this.objFileIni = objFileIni;
	}
	public FileIniZZZ getFileIni(){
		return this.objFileIni;
	}
	
	//############################################
	public static IKernelZFormulaIniZZZ getAsObject(String sToConvert)throws ExceptionZZZ{
		IKernelZFormulaIniZZZ objReturn = null;
	main:{
			
		//Erstelle nun alle möglichen KernelExpressionIni-Klassen und prüfe, ob sie mit dem String etwas anfangen können.			
		IKernelZFormulaIniZZZ objTemp = new KernelZFormulaIni_EmptyZZZ();
		if(objTemp.isStringForConvertRelevant(sToConvert)){
			objReturn = objTemp;
			break main;
		}
		
		objTemp = new ZTagFormulaIni_NullZZZ();
		if(objTemp.isStringForConvertRelevant(sToConvert)){
			objReturn = objTemp;
			break main;
		}
		
	}
		return objReturn;
	}
	
	public static String getAsString(IKernelZFormulaIniZZZ objExpression, String sLineWithExpression) throws ExceptionZZZ{
		String sReturn = sLineWithExpression;
		main:{
			if(objExpression==null) break main;
			
			sReturn = objExpression.parse(sLineWithExpression);
		}
		return sReturn;
	}
	public static String getAsString(String sLineWithExpression)throws ExceptionZZZ{
		String sReturn = sLineWithExpression;
		main:{
			//Erstelle nun alle möglichen KernelExpressionIni-Klassen und prüfe, ob sie mit dem Ausdruck etwas anfangen können.			
			IKernelZFormulaIniZZZ objExpression = new KernelZFormulaIni_EmptyZZZ();
			if(objExpression.isParseRelevant(sLineWithExpression)){				
				sReturn = KernelZFormulaIniConverterZZZ.getAsString(objExpression, sLineWithExpression);
				break main;
			}
			
			objExpression = new ZTagFormulaIni_NullZZZ();
			if(objExpression.isParseRelevant(sLineWithExpression)){				
				sReturn = KernelZFormulaIniConverterZZZ.getAsString(objExpression, sLineWithExpression);
				break main;
			}
			
			
		}
		return sReturn;
	}
	
	public static String getAsExpression(String sLineWithExpression)throws ExceptionZZZ{
		String sReturn = sLineWithExpression;
		main:{
			//Erstelle nun alle möglichen KernelExpressionIni-Klassen und prüfe, ob sie mit dem Ausdruck etwas anfangen können.			
			IKernelZFormulaIniZZZ objExpression = new KernelZFormulaIni_EmptyZZZ();
			if(objExpression.isParseRelevant(sLineWithExpression)){	
				
				sReturn = KernelZFormulaIniConverterZZZ.getAsExpression(objExpression, sLineWithExpression);
				break main;
			}
			
			objExpression = new ZTagFormulaIni_NullZZZ();
			if(objExpression.isParseRelevant(sLineWithExpression)){				
				sReturn = KernelZFormulaIniConverterZZZ.getAsExpression(objExpression, sLineWithExpression);
				break main;
			}
			
			
		}
		return sReturn;
	}
	public static String getAsExpression(IKernelZFormulaIniZZZ objExpression, String sLineWithExpression) throws ExceptionZZZ{
		String sReturn = sLineWithExpression;
		main:{
			if(objExpression==null) break main;
			
			sReturn = objExpression.computeAsExpression(sLineWithExpression);
		}
		return sReturn;
	}
}
