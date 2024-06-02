package basic.zBasic.util.datatype.xml;

import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.measure.spi.SystemOfUnits;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.xml.tagtype.ITagTypeZZZ;
import basic.zBasic.xml.tagtype.ITagByTypeZZZ;
import basic.zKernel.file.ini.KernelZFormulaIni_EmptyZZZ;

/**Einfache Klasse, in der ohne ein XML-Dokument zu haben
 * Statt dessen mit den Interfaces ITagZZZ, bzw. ITagTypeZZZ arbeiten. 
 * Tags mit den Mitteln der Stringverarbeitung verarbeitet, gesucht, Werte herausgeloest, etc. werden.
 * @author fl86kyvo
 *
 * Merke: Im Gegensatz zu XMLUtil oder XMLDocumentUtilZZZ Klasse
 * wird hier nicht JDom oder ein XML - Dokument gebraucht.
 *
 */
public class XmlUtilZZZ {
    
	//
	public static String computeTagPartStarting(String sTagName) {
		if(StringZZZ.isEmpty(sTagName)) {
			return "";
		}else {
			return "<" + sTagName + ">";
		}
	}
	
	public static String computeTagPartEmpty(String sTagName) {
		if(StringZZZ.isEmpty(sTagName)) {
			return "";		
		}else {
			return "<" + sTagName + "/>"; 
		}
	}
	
	public static String computeTagEmpty(String sTagName) {
		return XmlUtilZZZ.computeTagPartEmpty(sTagName);
	}
	
	public static String computeTagPartClosing(String sTagName) {
		if(StringZZZ.isEmpty(sTagName)) {
			return "";		
		}else {
			return "</" + sTagName + ">"; 
		}
	}
	
	public static ITagByTypeZZZ getTagNext(String sXml) throws ExceptionZZZ {
		ITagByTypeZZZ objReturn = null;
		main:{
			//StringZZZ.left(sXml, XmlUtilZZZ.sTagClosing )
			
			//FGL: Das scheint nur zu funktionieren, wenn man den ersten Tag kennt, beim Namen
			//Pattern regex = Pattern.compile("<DataElements>(.*?)</DataElements>", Pattern.DOTALL);
			Pattern regex = Pattern.compile("<abc>(.*?)</abc>", Pattern.DOTALL);
			Matcher matcher = regex.matcher(sXml);
			Pattern regex2 = Pattern.compile("<([^<>]+)>([^<>]+)</\\1>");
			if (matcher.find()) {
			    String DataElements = matcher.group(1);
		    	System.out.println(DataElements);
			    Matcher matcher2 = regex2.matcher(DataElements);
			    while (matcher2.find()) {
			        //list.add(new DataElement(matcher2.group(1), matcher2.group(2)));
			    	String stemp = matcher2.group(2);
			    	System.out.println(stemp);
			    	
			    } 
			}
			
		}//end main:
		return objReturn;
	}
	

	
	
	//+++++++++++++++++++++++++++++++++++++++++++++
	/** Gibt einen Vector zurück, in dem das erste Element der Ausdruck VOR der ersten 'Expression' ist. Das 2. Element ist die Expression. Das 3. Element ist der Ausdruck NACH der ersten Expression.
	* @param sLineWithExpression
	* @return
	* 
	* lindhaueradmin; 06.03.2007 11:20:34
	 * @throws ExceptionZZZ 
	 */
	public static Vector computeExpressionFirstVector(String sLineWithExpression, ITagTypeZZZ objTagType) throws ExceptionZZZ{
		Vector vecReturn = new Vector();		
		main:{
			if(objTagType==null) break main;
			
			if(StringZZZ.contains(sLineWithExpression, objTagType.getTagPartEmpty())) {
				vecReturn = StringZZZ.vecSplitFirst(sLineWithExpression, objTagType.getTagPartEmpty(), false,true);				
				
			}else {
				vecReturn = StringZZZ.vecMid(sLineWithExpression, objTagType.getTagPartStarting(), objTagType.getTagPartClosing(), false,true);
				
			}
		}
		return vecReturn;
	}
	
	public static boolean isExpression(String sLine, ITagTypeZZZ objTagType) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			boolean btemp = StringZZZ.contains(sLine, objTagType.getTagPartStarting(), false);
			if(btemp) {
				btemp = StringZZZ.contains(sLine, objTagType.getTagPartClosing(), false);
				if(btemp==false) break main;
			}else {
				btemp = StringZZZ.contains(sLine, objTagType.getTagPartEmpty(), false);
				if(btemp==false) break main;
			}
												
			bReturn = true;
		}//end main
		return bReturn;
	}
}
