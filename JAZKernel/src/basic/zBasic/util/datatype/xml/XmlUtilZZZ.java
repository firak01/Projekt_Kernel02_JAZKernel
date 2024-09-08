package basic.zBasic.util.datatype.xml;

import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.measure.spi.SystemOfUnits;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractList.HashMapUtilZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.xml.tagsimple.ITagSimpleZZZ;
import basic.zBasic.util.xml.tagsimple.TagSimpleZZZ;
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
	public static String computeTag(String sTagName, String sTagValue) {
		String sReturn = null;
		main:{
			if(StringZZZ.isEmpty(sTagName)) break main;
			
			
			if(sTagValue==null) {
				sReturn = XmlUtilZZZ.computeTagEmpty(sTagName);
				break main;
			}
			
			String sTagStarting = XmlUtilZZZ.computeTagPartStarting(sTagName); 
			String sTagClosing = XmlUtilZZZ.computeTagPartClosing(sTagName);
			sReturn = sTagStarting + sTagValue + sTagClosing;
		}
		return sReturn;
	}
	
	public static String computeTagAsHashMapEntry(String sTagName, String sTagValue) {
		String sReturn = null;
		main:{
			if(StringZZZ.isEmpty(sTagName)) break main;
						
//			if(sTagValue==null) {
//				sReturn = XmlUtilZZZ.computeTagEmpty(sTagName);
//				break main;
//			}

			sReturn = HashMapUtilZZZ.computeAsHashMapEntry(sTagName, sTagValue);
		}
		return sReturn;
	}
	
	public static ITagSimpleZZZ computeAsTagSimple(String sTagName, String sTagValue) throws ExceptionZZZ {
		ITagSimpleZZZ objReturn = new TagSimpleZZZ(sTagName, sTagValue);
		return objReturn;
	}
	
	
	//++++++++++++++++++++++
	public static String computeTagEmpty(String sTagName) {
		return XmlUtilZZZ.computeTagPartEmpty(sTagName);
	}
	
	//++++++++++++++++++++++
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
	
	public static String computeTagPartClosing(String sTagName) {
		if(StringZZZ.isEmpty(sTagName)) {
			return "";		
		}else {
			return "</" + sTagName + ">"; 
		}
	}
	
	//################################
	public static boolean containsTag(String sExpression, String sTagName) {
		return XmlUtilZZZ.containsTag(sExpression, sTagName, true);
	}

		
	public static boolean containsTag(String sExpression, String sTagName, boolean bExactMatch) {
		boolean bReturn = false;
		main:{
			String sMatchTagStarting = XmlUtilZZZ.computeTagPartStarting(sTagName);
			String sMatchTagClosing = XmlUtilZZZ.computeTagPartClosing(sTagName); 
			bReturn=StringZZZ.containsAsTag(sExpression, sMatchTagStarting, sMatchTagClosing, bExactMatch);
			if(bReturn) break main;
			
			String sMatchTagEmpty = XmlUtilZZZ.computeTagEmpty(sTagName);
			bReturn=StringZZZ.containsAsTag(sExpression, sMatchTagEmpty, "", bExactMatch);
			if(bReturn) break main;
			
		}//end main
		return bReturn;
	}
	
	
	//################################
	public static boolean isTagEmpty(String sTag) {
		boolean bReturn = false;
		main:{			
			if(!StringZZZ.startsWith(sTag, "<")) break main;
			if(!StringZZZ.endsWith(sTag, "/>")) break main;
			
			bReturn = true;
		}//end main:
		return bReturn;
	}
	
	public static boolean isTagClosing(String sTag) {
		boolean bReturn = false;
		main:{			
			if(!StringZZZ.startsWith(sTag, "</")) break main;
			if(!StringZZZ.endsWith(sTag, ">")) break main;
			
			bReturn = true;
		}//end main:
		return bReturn;
	}
	
	public static boolean isTag(String sTag) {
		boolean bReturn = false;
		main:{			
			if(!StringZZZ.startsWith(sTag, "<")) break main;
			if(!StringZZZ.endsWith(sTag, ">")) break main;
			
			bReturn = true;
		}//end main:
		return bReturn;
	}
	
	public static boolean isTagStarting(String sTag) {
		boolean bReturn = false;
		main:{			
			if(!XmlUtilZZZ.isTag(sTag)) break main;			
			if(!XmlUtilZZZ.isTagClosing(sTag)) break main;
			if(!XmlUtilZZZ.isTagEmpty(sTag)) break main;
			
			bReturn = true;
		}//end main:
		return bReturn;
	}
	
	
	//+++++++++++++++++++++++++++++
	/**FGL: Der intern verwendete RegEx funktioniert nur, wenn man den ersten Tag kennt, beim Namen
	 * @param sXml
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 09.06.2024, 11:17:35
	 */
	public static ITagByTypeZZZ getTagNext(String sTagName, String sXml) throws ExceptionZZZ {
		ITagByTypeZZZ objReturn = null;
		main:{
			if(StringZZZ.isEmpty(sTagName)) break main;
			if(StringZZZ.isEmpty(sXml)) break main;
			
			//StringZZZ.left(sXml, XmlUtilZZZ.sTagClosing )		
			//Pattern regex = Pattern.compile("<DataElements>(.*?)</DataElements>", Pattern.DOTALL);
			String sTagStarting = XmlUtilZZZ.computeTagPartStarting(sTagName); 
			String sTagClosing = XmlUtilZZZ.computeTagPartClosing(sTagName);
						
			Pattern regex = Pattern.compile(sTagStarting + "(.*?)" + sTagClosing, Pattern.DOTALL);
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
	
	public static boolean isExpression4TagXml(String sLineWithExpression, String sTagName){
		boolean bReturn = false;
		main:{			
			bReturn = XmlUtilZZZ.containsTag(sLineWithExpression, sTagName, false);
			if(bReturn) break main;
			
		}//end main
		return bReturn;
	}
	
	/** Merke: Path-Anweisungen in einer ini-Datei haben eine andere Struktur als XML-Tags.
	 * @param sLineWitchExpression
	 * @param sTagName
	 * @return
	 * @author Fritz Lindhauer, 20.07.2024, 07:48:25
	 */
	public static boolean isExpression4Tag(String sLineWithExpression, String sTagStarting, String sTagClosing){
		boolean bReturn = false;
		main:{			
			bReturn=StringZZZ.containsAsTag(sLineWithExpression, sTagStarting, sTagClosing, false);
			if(bReturn) break main;
			
		}//end main
		return bReturn;
	}
	
	
	/** Also der String beginnt mit dem Starting Tag und enedet mit dem Closing Tag
	 * @param sLineWithExpression
	 * @param sTagStarting
	 * @param sTagClosing
	 * @return
	 * @author Fritz Lindhauer, 05.09.2024, 18:30:57
	 */
	public static boolean isSurroundedByTag(String sLineWithExpression, String sTagStarting, String sTagClosing){
		boolean bReturn = false;
		main:{
			if(StringZZZ.isEmpty(sLineWithExpression)) break main;
			
		
			boolean bSurroundedLeft = StringZZZ.startsWithIgnoreCase(sLineWithExpression, sTagStarting);
			boolean bSurroundedRight = StringZZZ.endsWithIgnoreCase(sLineWithExpression, sTagClosing);
			
			if(bSurroundedLeft && bSurroundedRight) bReturn = true;
		}//end main:
		return bReturn;
	}
	
}
