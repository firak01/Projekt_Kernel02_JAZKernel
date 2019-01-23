package basic.zKernel.file.ini;

import basic.zBasic.ExceptionZZZ;
import basic.zKernel.IKernelExpressionIniZZZ;

/**Grundliegende Idee dahinter stammt aus den Converter-Klassen in JSF. 
 * 
 * 20190123
 * @author Fritz Lindhauer
 *
 */
public class KernelExpressionIniConverterZZZ {
	public static IKernelExpressionIniZZZ getAsObject(String sToConvert)throws ExceptionZZZ{
		IKernelExpressionIniZZZ objReturn = null;
	main:{
			
		//Erstelle nun alle möglichen KernelExpressionIni-Klassen und prüfe, ob sie mit dem String etwas anfangen können.			
		IKernelExpressionIniZZZ objTemp = new KernelExpressionIni_EmptyZZZ();
		if(objTemp.isStringForConvertRelevant(sToConvert)){
			objReturn = objTemp;
			break main;
		}
		
	}
		return objReturn;
	}
	
	public static String getAsString(IKernelExpressionIniZZZ objExpression, String sLineWithExpression) throws ExceptionZZZ{
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
			IKernelExpressionIniZZZ objExpression = new KernelExpressionIni_EmptyZZZ();
			if(objExpression.isStringForComputeRelevant(sLineWithExpression)){				
				sReturn = KernelExpressionIniConverterZZZ.getAsString(objExpression, sLineWithExpression);
				break main;
			}
			
			
		}
		return sReturn;
	}
}
