package basic.zKernel.file.ini;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.datatype.xml.XmlUtilZZZ;

public class ExpressionIniUtilZZZ {

	private ExpressionIniUtilZZZ(){
		//zum 'Verstecken" des Konstruktors
	}//only static Methods
	
	/* Setze Z-Tags um den Ausdruck rum, falls noch nicht vorhanden. 
	 */
	public static String makeAsExpression(String sString) throws ExceptionZZZ {
		return ExpressionIniUtilZZZ.makeAsExpression(sString, "Z");
	}
	
	/* Setze den angegebenen Tag um den Ausdruck rum, falls noch nicht vorhanden. 
	 */
	public static String makeAsExpression(String sString, String sTagName) throws ExceptionZZZ {
		String sTagStarting = XmlUtilZZZ.computeTagPartStarting(sTagName);
		String sTagClosing = XmlUtilZZZ.computeTagPartClosing(sTagName);
		return 	ExpressionIniUtilZZZ.makeAsExpression_(sString, sTagStarting, sTagClosing);
	}
	
	/* Setze den angegebenen Tag um den Ausdruck rum, falls noch nicht vorhanden. 
	 */
	public static String makeAsExpression(String sString, String  sTagStarting, String sTagClosing) throws ExceptionZZZ {
		return 	ExpressionIniUtilZZZ.makeAsExpression_(sString, sTagStarting, sTagClosing);
	}
	
	private static String makeAsExpression_(String sString, String sTagStarting, String sTagClosing) throws ExceptionZZZ {
		String sReturn = sString;
		main:{
			//Finde den ersten Tag... Ist das der angegebene...von vorne, dito von hinten.
			//was liefert das zurueck?
			sReturn = XmlUtilZZZ.getTagNext(sTagStarting, sString);
			
			//neue methoden
			XmlUtilZZZ.getTagNextStarting(sTagStarting, sString);
			XmlUtilZZZ.getTagNextClosing(sTagClosing, sString);
			
			TODOGOON20241116;
			
		}//end main:
		return sReturn;
	}
	
	//++++++++++
	
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
