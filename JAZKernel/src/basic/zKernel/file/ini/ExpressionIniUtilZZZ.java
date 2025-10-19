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
		String sTagOpening = XmlUtilZZZ.computeTagPartOpening(sTagName);
		String sTagClosing = XmlUtilZZZ.computeTagPartClosing(sTagName);
		return 	ExpressionIniUtilZZZ.makeAsExpression_(sString, sTagOpening, sTagClosing);
	}
	
	/* Setze den angegebenen Tag um den Ausdruck rum, falls noch nicht vorhanden. 
	 */
	public static String makeAsExpression(String sString, String  sTagOpening, String sTagClosing) throws ExceptionZZZ {
		return 	ExpressionIniUtilZZZ.makeAsExpression_(sString, sTagOpening, sTagClosing);
	}
	
	private static String makeAsExpression_(String sString, String sTagOpening, String sTagClosing) throws ExceptionZZZ {
		String sReturn = sString;
		main:{
			if(XmlUtilZZZ.isSurroundedByTag(sString, sTagOpening, sTagClosing)) {
				//mache nix
			}else {
				//umgib den String mit den gewuenschten Tags
				sReturn = sTagOpening + sString + sTagClosing;
			}
			
			/*
			//TODOGOON20241116;
			//Finde den ersten Tag... Ist das der angegebene...von vorne, dito von hinten.
			//was liefert das zurueck?
			sReturn = XmlUtilZZZ.getTagNext(sTagOpening, sString);
			
			//neue methoden
			XmlUtilZZZ.getTagNextStarting(sTagOpening, sString);
			XmlUtilZZZ.getTagNextClosing(sTagClosing, sString);
			
			
			*/
			
		}//end main:
		return sReturn;
	}
	
	//++++++++++
	
	public static boolean isExpression(String sExpression, String sTagName) throws ExceptionZZZ {
		return ExpressionIniUtilZZZ.isExpression_(sExpression, sTagName, true);
	}	
	
	public static boolean isExpression(String sExpression, String sTagName, boolean bExactMatch) throws ExceptionZZZ {
		return ExpressionIniUtilZZZ.isExpression_(sExpression, sTagName, bExactMatch);
	}
	
	private static boolean isExpression_(String sExpression, String sTagName, boolean bExactMatch) throws ExceptionZZZ {
		boolean bReturn=false;
		main:{
			if(StringZZZ.isEmptyTrimmed(sExpression)) break main;
		
			bReturn = XmlUtilZZZ.containsTagName(sExpression, sTagName, bExactMatch); //also, kein exact match
			if(bReturn) break main;
						
		}//end main
		return bReturn;
	}	
	
	//+++++++++++++++++++++
	//+++ RegEx sinnvoll fuer nicht normale Tags, z.B KernelZFormulaIni_aj
	public static boolean isExpressionRegEx(String sExpression, String sRegEx, boolean bExactMatch) throws ExceptionZZZ{
		boolean bReturn=false;
		main:{
			if(StringZZZ.isEmptyTrimmed(sExpression)) break main;
		
			bReturn = XmlUtilZZZ.containsRegEx(sExpression, sRegEx, bExactMatch); //also, kein exact match
			if(bReturn) break main;
						
		}//end main
		return bReturn;
	}
	
	
	//+++++++++++++++++++++
	//Gibt es irgendeinen Expression - Ausdruck im String
	//Merke: Ein Solver oder der Handler betrachtet immer nur seinen eigenen Tag.
	//       Hier werden alle Solver/Handler verwendet.
	public static boolean isExpressionAny(String sString) throws ExceptionZZZ{
		boolean bReturn=false;
		main:{
			if(StringZZZ.isEmptyTrimmed(sString)) break main;
		
			KernelExpressionIniHandlerZZZ obj = new KernelExpressionIniHandlerZZZ();
			TODOGOON20251019; //obj.parse(sExpression)
					
		}//end main
		return bReturn;		
	}
}
