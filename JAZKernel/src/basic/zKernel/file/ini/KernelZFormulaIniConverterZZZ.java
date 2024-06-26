package basic.zKernel.file.ini;

import custom.zKernel.file.ini.FileIniZZZ;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractList.HashMapCaseInsensitiveZZZ;
import basic.zKernel.IKernelZFormulaIniZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernel.AbstractKernelUseObjectZZZ;
import basic.zKernel.flag.IFlagZUserZZZ;

/**Grundliegende Idee dahinter stammt aus den Converter-Klassen in JSF. 
 * 
 * 20190123
 * @author Fritz Lindhauer
 *
 */
public class KernelZFormulaIniConverterZZZ extends AbstractKernelUseObjectZZZ{
	private FileIniZZZ objFileIni=null;
	
	//###########################################
	public KernelZFormulaIniConverterZZZ() throws ExceptionZZZ{
		String[]saFlagControl = {"init"};
		KernelExpressionIniConverterNew_(null, saFlagControl);
	}
	
	public KernelZFormulaIniConverterZZZ(FileIniZZZ objFileIni) throws ExceptionZZZ{
		super(objFileIni.getKernelObject());
		KernelExpressionIniConverterNew_(objFileIni, null);
	}
	
	public KernelZFormulaIniConverterZZZ(FileIniZZZ objFileIni, String[] saFlag) throws ExceptionZZZ{
		super(objFileIni.getKernelObject());
		KernelExpressionIniConverterNew_(objFileIni, saFlag);
	}
	
	public KernelZFormulaIniConverterZZZ(IKernelZZZ objKernel, FileIniZZZ objFileIni, String[] saFlag) throws ExceptionZZZ{
		super(objKernel);
		KernelExpressionIniConverterNew_(objFileIni, saFlag);
	}
	
	private boolean KernelExpressionIniConverterNew_(FileIniZZZ objFileIn, String[] saFlagControlIn) throws ExceptionZZZ {
		 boolean bReturn = false;
		 String stemp; boolean btemp; 
		 main:{
			 	
		 	//try{	 		
		 			//setzen der übergebenen Flags	
					if(saFlagControlIn != null){
						for(int iCount = 0;iCount<=saFlagControlIn.length-1;iCount++){
							stemp = saFlagControlIn[iCount];
							btemp = setFlag(stemp, true);
							if(btemp==false){
								ExceptionZZZ ez = new ExceptionZZZ( "the flag '" + stemp + "' is not available.", IFlagZUserZZZ.iERROR_FLAG_UNAVAILABLE, this, ReflectCodeZZZ.getMethodCurrentName()); 
								throw ez;		 
							}
						}
						if(this.getFlag("init")==true){
							bReturn = true;
							break main;
						}
					}
				
					if(objFileIn==null ){
						ExceptionZZZ ez = new ExceptionZZZ("FileIni-Object", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
						throw ez; 
					}else{
						this.setFileIni(objFileIn);							
					}										
		 	}//end main:
			return bReturn;
		 }//end function KernelExpressionIniSolverNew_
		
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
		
		objTemp = new KernelZFormulaIni_NullZZZ();
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
			
			sReturn = objExpression.compute(sLineWithExpression);
		}
		return sReturn;
	}
	public static String getAsString(String sLineWithExpression)throws ExceptionZZZ{
		String sReturn = sLineWithExpression;
		main:{
			//Erstelle nun alle möglichen KernelExpressionIni-Klassen und prüfe, ob sie mit dem Ausdruck etwas anfangen können.			
			IKernelZFormulaIniZZZ objExpression = new KernelZFormulaIni_EmptyZZZ();
			if(objExpression.isStringForComputeRelevant(sLineWithExpression)){				
				sReturn = KernelZFormulaIniConverterZZZ.getAsString(objExpression, sLineWithExpression);
				break main;
			}
			
			objExpression = new KernelZFormulaIni_NullZZZ();
			if(objExpression.isStringForComputeRelevant(sLineWithExpression)){				
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
			if(objExpression.isStringForComputeRelevant(sLineWithExpression)){	
				
				sReturn = KernelZFormulaIniConverterZZZ.getAsExpression(objExpression, sLineWithExpression);
				break main;
			}
			
			objExpression = new KernelZFormulaIni_NullZZZ();
			if(objExpression.isStringForComputeRelevant(sLineWithExpression)){				
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
