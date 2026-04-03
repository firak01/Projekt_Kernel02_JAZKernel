package basic.zBasic.util.machine;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IConstantZZZ;
import basic.zBasic.util.datatype.string.StringArrayZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;

public class EnvironmentPlaceholderUtilZZZ  implements IConstantZZZ{
	
	private EnvironmentPlaceholderUtilZZZ(){
		//zum 'Verstecken" des Konstruktors
	}//only static Methods
	
	public static String readPlaceholderContent(String sArgument) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			if(StringZZZ.isEmpty(sArgument)) break main;
			
			if(!sArgument.endsWith(IEnvironmentPlaceholderZZZ.sCLOSING)) break main;
			
			if(!sArgument.startsWith(IEnvironmentPlaceholderZZZ.sPREFIX)) break main;
			
			//Nun die Varianten mit oder ohne Parameter durchgehen
			String sParamTemp = sArgument.substring(1,2);
			
			String[]saParam = new String[2];
			saParam[0] = IEnvironmentPlaceholderZZZ.sPARAM_RETURN_EMPTY;
			saParam[1] = IEnvironmentPlaceholderZZZ.sPARAM_RETURN_NAME;
			
			if(sParamTemp.equals(IEnvironmentPlaceholderZZZ.sOPENING)){
				//Fall, keine Parameter
				sReturn = sArgument.substring(2,sArgument.length()-1);
				
			}else if (StringArrayZZZ.contains(saParam,  sParamTemp)){
				//Fall definierte Parameter
				sReturn = StringZZZ.mid(sArgument,IEnvironmentPlaceholderZZZ.sOPENING , IEnvironmentPlaceholderZZZ.sCLOSING);
			}else {
				break main; //Kein Fehler werfen. Schliesslich wird diese Methode auf beliebige Strings angewendet.
			}
					
		}//end main:
		return sReturn;
	}
				
	public static String readPlaceholderName(String sArgument) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			String sContent = EnvironmentPlaceholderUtilZZZ.readPlaceholderContent(sArgument);
			if(StringZZZ.isEmpty(sContent)) break main;
			
			sReturn = StringZZZ.left(sContent+IEnvironmentPlaceholderZZZ.sDELIM_ALTERNATIVE, IEnvironmentPlaceholderZZZ.sDELIM_ALTERNATIVE);
												
		}//end main:
		return sReturn;
	}
	
	public static String readPlaceholderAlternative(String sArgument) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			String sContent = EnvironmentPlaceholderUtilZZZ.readPlaceholderContent(sArgument);
			if(StringZZZ.isEmpty(sContent)) break main;

			sReturn = StringZZZ.right(IEnvironmentPlaceholderZZZ.sDELIM_ALTERNATIVE + sContent, IEnvironmentPlaceholderZZZ.sDELIM_ALTERNATIVE);												
		}//end main:
		return sReturn;
	}
	
	
	
}
