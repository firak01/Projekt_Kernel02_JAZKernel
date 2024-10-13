package basic.zBasic.util.datatype.xml;

import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.measure.spi.SystemOfUnits;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IConstantZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractList.HashMapUtilZZZ;
import basic.zBasic.util.abstractList.Vector3ZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.xml.tagsimple.ITagSimpleZZZ;
import basic.zBasic.util.xml.tagsimple.TagSimpleZZZ;
import basic.zBasic.xml.tagtype.ITagTypeZZZ;
import basic.zBasic.xml.tagtype.ITagByTypeZZZ;
import basic.zKernel.config.KernelConfigSectionEntryUtilZZZ;
import basic.zKernel.file.ini.KernelZFormulaIni_EmptyZZZ;
import basic.zKernel.file.ini.ZTagFormulaIni_NullZZZ;

/**Einfache Klasse, in der ohne ein XML-Dokument zu haben
 * Statt dessen mit den Interfaces ITagZZZ, bzw. ITagTypeZZZ arbeiten. 
 * Tags mit den Mitteln der Stringverarbeitung verarbeitet, gesucht, Werte herausgeloest, etc. werden.
 * @author fl86kyvo
 *
 * Merke: Im Gegensatz zu XMLUtil oder XMLDocumentUtilZZZ Klasse
 * wird hier nicht JDom oder ein XML - Dokument gebraucht.
 *
 */
public class XmlUtilZZZ implements IConstantZZZ{
	public static boolean containsRegEx(String sExpression, String sRegEx) throws ExceptionZZZ{
		return StringZZZ.matchesPattern(sExpression, sRegEx, true);
	}
	
	public static boolean containsRegEx(String sExpression, String sRegEx, boolean bExactMatch) throws ExceptionZZZ{
		return StringZZZ.matchesPattern(sExpression, sRegEx, bExactMatch);	
	}
	
	public static boolean containsRegEx_(String sExpression, String sRegEx, boolean bExactMatch) throws ExceptionZZZ{
		return StringZZZ.matchesPattern(sExpression, sRegEx, bExactMatch);
	}
	
	
	//++++++++++++++++++++++++++++++++++++++++++++++++++++
	public static String computeTag(String sTagName, String sTagValue) throws ExceptionZZZ {
		String sReturn = null;
		main:{				
			if(sTagValue==null) {
				//Das enthaelt nicht den TagNamen  sReturn = XmlUtilZZZ.computeTagNull();
				//Die aufrufende Methode kann ggfs. diesen Z:Null - Tag verwenden, wenn null zurueckkommt.
				break main;
			}else if(sTagValue.equals("")) {
				sReturn = XmlUtilZZZ.computeTagEmpty(sTagName);
				break main;
			}
			
			String sTagStarting = XmlUtilZZZ.computeTagPartStarting(sTagName); 
			String sTagClosing = XmlUtilZZZ.computeTagPartClosing(sTagName);
			sReturn = sTagStarting + sTagValue + sTagClosing;
		}
		return sReturn;
	}
	
	public static String computeTagAsHashMapEntry(String sTagName, String sTagValue) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			if(StringZZZ.isEmpty(sTagName)) break main;
			
			if(sTagValue==null) {
				//Das enthaelt nicht den TagNamen  sReturn = XmlUtilZZZ.computeTagNull();
				//Die aufrufende Methode kann ggfs. diesen Z:Null - Tag verwenden, wenn null zurueckkommt.
				break main;
			}else if(sTagValue.equals("")) {
				sReturn = XmlUtilZZZ.computeTagEmpty(sTagName);
				break main;
			}

			sReturn = HashMapUtilZZZ.computeAsHashMapEntry(sTagName, sTagValue);
		}
		return sReturn;
	}
	
	public static ITagSimpleZZZ computeAsTagSimple(String sTagName, String sTagValue) throws ExceptionZZZ {
		ITagSimpleZZZ objReturn = new TagSimpleZZZ(sTagName, sTagValue);
		return objReturn;
	}
	
	
	//++++++++++++++++++++++
	public static String computeTagNull() throws ExceptionZZZ {
		return XmlUtilZZZ.computeTagPartEmpty(ZTagFormulaIni_NullZZZ.sTAG_NAME);
	}	
	
	public static String computeTagEmpty() throws ExceptionZZZ {
		return XmlUtilZZZ.computeTagPartEmpty(KernelZFormulaIni_EmptyZZZ.sTAG_NAME);
	}
	
	public static String computeTagEmpty(String sTagName) throws ExceptionZZZ {
		return XmlUtilZZZ.computeTagPartEmpty(sTagName);
	}
	
	//++++++++++++++++++++++
	public static String computeTagPartStarting(String sTagName) throws ExceptionZZZ {
		if(StringZZZ.isEmpty(sTagName)) {
			return "";
		}else {
			if(XmlUtilZZZ.isTag(sTagName)) {
				ExceptionZZZ ez = new ExceptionZZZ("Expected only the tagname as parameter not the tag itself '" + sTagName +"'", iERROR_PARAMETER_VALUE, XmlUtilZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			return "<" + sTagName + ">";
		}
	}
	
	public static String computeTagPartEmpty(String sTagName) throws ExceptionZZZ {
		if(StringZZZ.isEmpty(sTagName)) {
			return "";		
		}else {
			if(XmlUtilZZZ.isTag(sTagName)) {
				ExceptionZZZ ez = new ExceptionZZZ("Expected only the tagname as parameter not the tag itself '" + sTagName +"'", iERROR_PARAMETER_VALUE, XmlUtilZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}						
			return "<" + sTagName + "/>"; 
		}
	}
	
	public static String computeTagPartClosing(String sTagName) throws ExceptionZZZ {
		if(StringZZZ.isEmpty(sTagName)) {
			return "";		
		}else {
			if(XmlUtilZZZ.isTag(sTagName)) {
				ExceptionZZZ ez = new ExceptionZZZ("Expected only the tagname as parameter not the tag itself '" + sTagName +"'", iERROR_PARAMETER_VALUE, XmlUtilZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			return "</" + sTagName + ">"; 
		}
	}
	
	//################################
	public static boolean containsTag(String sExpression, String sTagName) throws ExceptionZZZ {
		return XmlUtilZZZ.containsTag(sExpression, sTagName, true);
	}

		
	public static boolean containsTag(String sExpression, String sTagName, boolean bExactMatch) throws ExceptionZZZ {
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
	* @param sExpression
	* @return
	* 
	* lindhaueradmin; 06.03.2007 11:20:34
	 * @throws ExceptionZZZ 
	 */
	public static Vector3ZZZ<String> computeExpressionFirstVector(String sExpression, ITagTypeZZZ objTagType) throws ExceptionZZZ{
		Vector3ZZZ<String> vecReturn = new Vector3ZZZ<String>();		
		main:{
			if(objTagType==null) break main;
			
			if(StringZZZ.contains(sExpression, objTagType.getTagPartEmpty())) {
				vecReturn = StringZZZ.vecSplitFirst(sExpression, objTagType.getTagPartEmpty(), false,true);				
				
			}else {
				vecReturn = StringZZZ.vecMid(sExpression, objTagType.getTagPartStarting(), objTagType.getTagPartClosing(), false,true);
				
			}
		}
		return vecReturn;
	}
	
	public static boolean isExpression(String sExpression, ITagTypeZZZ objTagType) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			boolean btemp = StringZZZ.contains(sExpression, objTagType.getTagPartStarting(), false);
			if(btemp) {
				btemp = StringZZZ.contains(sExpression, objTagType.getTagPartClosing(), false);
				if(btemp==false) break main;
			}else {
				btemp = StringZZZ.contains(sExpression, objTagType.getTagPartEmpty(), false);
				if(btemp==false) break main;
			}
												
			bReturn = true;
		}//end main
		return bReturn;
	}
	
	public static boolean isExpression4TagXml(String sExpression, String sTagName) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{			
			bReturn = XmlUtilZZZ.containsTag(sExpression, sTagName, false);
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
	public static boolean isExpression4Tag(String sExpression, String sTagStarting, String sTagClosing){
		boolean bReturn = false;
		main:{			
			bReturn=StringZZZ.containsAsTag(sExpression, sTagStarting, sTagClosing, false);
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
	public static boolean isSurroundedByTag(String sExpression, String sTagStarting, String sTagClosing){
		boolean bReturn = false;
		main:{
			if(StringZZZ.isEmpty(sExpression)) break main;
			
		
			boolean bSurroundedLeft = StringZZZ.startsWithIgnoreCase(sExpression, sTagStarting);
			boolean bSurroundedRight = StringZZZ.endsWithIgnoreCase(sExpression, sTagClosing);
			
			if(bSurroundedLeft && bSurroundedRight) bReturn = true;
		}//end main:
		return bReturn;
	}
	
	public static Vector3ZZZ<String>parseFirstVector(String sExpression, String sTagStarting, String sTagClosing, boolean bRemoveSurroundingSeparators) throws ExceptionZZZ{
		Vector3ZZZ<String>vecReturn = new Vector3ZZZ<String>();		
		main:{
			//Bei dem einfachen Tag wird das naechste oeffnende Tag genommen und dann auch das naechste schliessende Tag...
			vecReturn = StringZZZ.vecMidFirst(sExpression, sTagStarting, sTagClosing, !bRemoveSurroundingSeparators, false);			
		}
		return vecReturn;
	}
	
}
