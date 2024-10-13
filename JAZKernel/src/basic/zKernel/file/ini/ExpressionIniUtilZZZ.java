package basic.zKernel.file.ini;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.datatype.xml.XmlUtilZZZ;

public class ExpressionIniUtilZZZ {

	private ExpressionIniUtilZZZ(){
		//zum 'Verstecken" des Konstruktors
	}//only static Methods
	
	public static boolean isParse(String sExpression, String sTagName) throws ExceptionZZZ {
		return ExpressionIniUtilZZZ.isParse_(sExpression, sTagName, true);
	}	
	
	public static boolean isParse(String sExpression, String sTagName, boolean bExactMatch) throws ExceptionZZZ {
		return ExpressionIniUtilZZZ.isParse_(sExpression, sTagName, bExactMatch);
	}
	
	private static boolean isParse_(String sExpression, String sTagName, boolean bExactMatch) throws ExceptionZZZ {
		boolean bReturn=false;
		main:{
			if(StringZZZ.isEmptyTrimmed(sExpression)) break main;
		
			bReturn = XmlUtilZZZ.containsTag(sExpression, sTagName, bExactMatch); //also, kein exact match
			if(bReturn) break main;
						
		}//end main
		return bReturn;
	}	
	
	//+++++++++++++++++++++
	//+++ RegEx sinnvoll fuer nicht normale Tags, z.B KernelZFormulaIni_aj
	public static boolean isParseRegEx(String sExpression, String sRegEx, boolean bExactMatch) throws ExceptionZZZ{
		boolean bReturn=false;
		main:{
			if(StringZZZ.isEmptyTrimmed(sExpression)) break main;
		
			bReturn = XmlUtilZZZ.containsRegEx(sExpression, sRegEx, bExactMatch); //also, kein exact match
			if(bReturn) break main;
						
		}//end main
		return bReturn;
	}
}
