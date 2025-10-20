package basic.zKernel.file.ini;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.datatype.xml.XmlUtilZZZ;
import basic.zBasic.util.xml.tagsimple.ITagBasicChildZZZ;
import basic.zBasic.util.xml.tagsimple.ITagBasicZZZ;

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
	
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	TODOGOON20251020;//Irgendwo alle Klassen, die einen Expression haben, als ein Array deklarieren.
	//+++++++++++++++++++++
	public static boolean isExpressionDefault(String sExpression, ITagBasicZZZ objWithTag) throws ExceptionZZZ {
		Class classObjectWithTag = objWithTag.getClass();
		return isExpressionDefault(sExpression, classObjectWithTag);
	}
	
	public static boolean isExpressionDefault(String sString, Class classObjectWithTag) throws ExceptionZZZ {
		String sTag = ExpressionIniUtilZZZ.getTagNameDefault(classObjectWithTag);
		return XmlUtilZZZ.isExpression4TagXml(sString, sTag);		
	}
	
	//Gibt es irgendeinen Expression - Ausdruck im String
	//Merke: Ein Solver oder der Handler betrachtet immer nur seinen eigenen Tag.
	//       Hier werden alle Solver/Handler verwendet.
	//
	//Merke: Fuer den JUnit-Test gilt: Erfasse alle Klassen auch in 
	//       TestUtilZZZ.createStringsUsed_ExpressionAny();
	public static boolean isExpressionDefaultAny(String sString) throws ExceptionZZZ{
		boolean bReturn=true;
		main:{
			if(StringZZZ.isEmptyTrimmed(sString)) break main;
		
			try {
				bReturn = isExpressionDefault(sString, KernelExpressionIniHandlerZZZ.class);
				if(bReturn) break main;
				
				bReturn = isExpressionDefault(sString, KernelCallIniSolverZZZ.class);
				if(bReturn) break main;
			
			}catch(Exception e) {
				ExceptionZZZ ez = new ExceptionZZZ(e);
				throw ez;
			}
			
			bReturn = false;
		}//end main
		return bReturn;		
	}
	
	//Merke: Der Default-Tagname wird in einer Konstanten in der konkreten Klasse verwaltet.
	//Merke: Aufgrund des statischen Contexts in Java,  
	//       und der Tatsache, dass statische Variablen in den erbenden Klassen nicht ueberschrieben werden funktioniert das nicht: 
	//@Override
	//public String getParentNameDefault() throws ExceptionZZZ {
	//	return sTAG_PARENT_NAME;
	//}
	
	//Ein Loesungsansatz wäre dies in jeder Klasse die Methode zu implementieren, 
	//also kann man das so erzwingen:
	//
	//Merke: Erst ab Java 8 können static Ausdrücke in ein interface	
	//@Override
	//public abstract String getParentNameDefault() throws ExceptionZZZ; 
		
	//Merke: 20251019 Vorgeschlagener Loesungsansatz ist dies per Reflection als instanzbezogene Methode zu implementieren, 
	//                hier an zentraler Stelle fuer die verschiedenenen Vererbungspfade:	
	public static String getTagParentNameDefault(ITagBasicChildZZZ objWithTag) throws ExceptionZZZ {
		try {
			//Hole das Feld sTAG_NAME der tatsächlichen Klasse
			//return (String) objWithTag.getClass().getField("sTAG_PARENT_NAME").get(null);
			Class classObjectWithTag = objWithTag.getClass();
			return getTagParentNameDefault(classObjectWithTag);
	  } catch (Exception e) {
		  	ExceptionZZZ ez = new ExceptionZZZ(e);
		  	throw ez;
	  }
	}
	
	public static String getTagParentNameDefault(Class classObjectWithTag) throws ExceptionZZZ {
		try {
			//Hole das Feld sTAG_NAME der tatsächlichen Klasse
			return (String) classObjectWithTag.getField("sTAG_PARENT_NAME").get(null);
	  } catch (Exception e) {
		  	ExceptionZZZ ez = new ExceptionZZZ(e);
		  	throw ez;
	  }
	}
	
	public static String getTagNameDefault(ITagBasicZZZ objWithTag) throws ExceptionZZZ {
		try {
			//Hole das Feld sTAG_NAME der tatsächlichen Klasse
			//return (String) objWithTag.getClass().getField("sTAG_NAME").get(null);
			Class classObjectWithTag = objWithTag.getClass();
			return getTagParentNameDefault(classObjectWithTag);
	  } catch (Exception e) {
		  	ExceptionZZZ ez = new ExceptionZZZ(e);
		  	throw ez;
	  }
	}
	
	public static String getTagNameDefault(Class classObjectWithTag) throws ExceptionZZZ {
		try {
			//Hole das Feld sTAG_NAME der tatsächlichen Klasse
			return (String) classObjectWithTag.getField("sTAG_NAME").get(null);
	  } catch (Exception e) {
		  	ExceptionZZZ ez = new ExceptionZZZ(e);
		  	throw ez;
	  }
	}
}
