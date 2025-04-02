package basic.zBasic.util.datatype.xml;

import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.measure.spi.SystemOfUnits;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IConstantZZZ;
import basic.zBasic.ObjectUtilZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractList.HashMapUtilZZZ;
import basic.zBasic.util.abstractList.Vector3ZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.math.MathZZZ;
import basic.zBasic.util.xml.tagsimple.ITagSimpleZZZ;
import basic.zBasic.util.xml.tagsimple.TagSimpleZZZ;
import basic.zBasic.xml.tagtype.ITagTypeZZZ;
import basic.zBasic.xml.tagtype.TagByTypeFactoryZZZ;
import basic.zBasic.xml.tagtype.ITagByTypeZZZ;
import basic.zKernel.config.KernelConfigSectionEntryUtilZZZ;
import basic.zKernel.file.ini.KernelZFormulaIni_EmptyZZZ;
import basic.zKernel.file.ini.KernelZFormulaIni_PathZZZ;
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
	public static String sERROR_TAGPARTS_UNEQUAL_TOTAL = "unequal total number of surrounding opening/closing tags";
	public static String sERROR_TAGPARTS_SURROUNDING_CONTAINER_OPENING_MISSING = "missing the opening tag in PRE";
	public static String sERROR_TAGPARTS_SURROUNDING_CONTAINER_CLOSING_MISSING = "missing the closing tag in POST";
	public static String sERROR_TAGPARTS_SURROUNDING_CONTAINER_UNEQUAL = "not enough surrounding opening/closing tags in PRE compared with POST";
	public static String sERROR_TAGPARTS_SURROUNDING_CONTAINER_OPENING_SURPLUS_MISSING = "not enough free removable opening tags in PRE available";
	public static String sERROR_TAGPARTS_SURROUNDING_CONTAINER_CLOSING_SURPLUS_MISSING = "not enough free removable closing tags in POST available";
	
	public static String sERROR_TAGPARTS_SURROUNDING_CONTAINER_OPENING_TOOMANY = "too many opening tags in PRE";
	public static String sERROR_TAGPARTS_SURROUNDING_CONTAINER_CLOSING_TOOMANY = "too many closing tags in POST";
	
	//TODOGOON 20250210; Symmetrie der xml-Strings muss noch defniert werden
	public static String sERROR_TAGPARTS_SURROUNDING_CONTAINER_ASYMETRIC = "asymetric allocation of surrounding opening/closing tags in PRE compared with POST. This tree nodes are asymetric.";

	
	
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
			
			String sTagOpening = XmlUtilZZZ.computeTagPartOpening(sTagName); 
			String sTagClosing = XmlUtilZZZ.computeTagPartClosing(sTagName);
			sReturn = sTagOpening + sTagValue + sTagClosing;
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
		return XmlUtilZZZ.computeTagEmpty(ZTagFormulaIni_NullZZZ.sTAG_NAME);
	}	
	
	public static String computeTagEmpty() throws ExceptionZZZ {
		return XmlUtilZZZ.computeTagEmpty(KernelZFormulaIni_EmptyZZZ.sTAG_NAME);
	}
		
	//+++++++++++++++++++++
	public static String computeTagNameFromTagPart(String sTagPart) throws ExceptionZZZ{
		String sReturn = null;
		main:{
			ensureTagPart(sTagPart);
			
			if(XmlUtilZZZ.isTagPartOpening(sTagPart)) {
				sReturn = StringZZZ.midLeftRight(sTagPart, "<" ,">");
			}else if(XmlUtilZZZ.isTagPartClosing(sTagPart)) {
				sReturn = StringZZZ.midLeftRight(sTagPart, "</" ,">");
			}else {
				ExceptionZZZ ez = new ExceptionZZZ("Unexpected parameter sTagPart='" + sTagPart +"'", iERROR_PARAMETER_VALUE, XmlUtilZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}			
		}//end main:
		return sReturn;
	}
	
	
	//++++++++++++++++++++++
	public static String computeTagPartOpening(String sTagName) throws ExceptionZZZ {
		XmlUtilZZZ.ensureExpressionTagNameValid(sTagName);
		return "<" + sTagName + ">";		
	}
	
	public static String computeTagPartClosing(String sTagName) throws ExceptionZZZ {	
		XmlUtilZZZ.ensureExpressionTagNameValid(sTagName);
		return "</" + sTagName + ">"; 		
	}
	
	public static String computeTagEmpty(String sTagName) throws ExceptionZZZ {
		XmlUtilZZZ.ensureExpressionTagNameValid(sTagName);				
		return "<" + sTagName + "/>"; 		
	}
	
	//################################
	public static boolean containsTagName(String sExpression, String sTagName) throws ExceptionZZZ {
		return XmlUtilZZZ.containsTagName(sExpression, sTagName, true);
	}

		
	public static boolean containsTagName(String sExpression, String sTagName, boolean bExactMatch) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			String sMatchTagStarting = XmlUtilZZZ.computeTagPartOpening(sTagName);
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
	public static int countTagName(String sExpression, String sTagName, boolean bExactMatch) throws ExceptionZZZ {
		int iReturn = -1;
		main:{
			String sMatchTagStarting = XmlUtilZZZ.computeTagPartOpening(sTagName);
			String sMatchTagClosing = XmlUtilZZZ.computeTagPartClosing(sTagName); 
			int iStarting = StringZZZ.count(sExpression, sMatchTagStarting, bExactMatch);
			int iClosing = StringZZZ.count(sExpression, sMatchTagClosing, bExactMatch);
			
			int iValued = MathZZZ.min(iStarting, iClosing);
			
			String sMatchTagEmpty = XmlUtilZZZ.computeTagEmpty(sTagName);
			int iEmpty=StringZZZ.count(sExpression, sMatchTagEmpty, bExactMatch);
			
			iReturn = iValued + iEmpty;
								
		}//end main
		return iReturn;
	}
	
	public static int countTagPart(String sExpression, String sTagPart, boolean bExactMatch) throws ExceptionZZZ {
		int iReturn = -1;
		main:{
			ensureTagPart(sTagPart);			
			iReturn = StringZZZ.count(sExpression, sTagPart, bExactMatch);							
		}//end main
		return iReturn;
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
	
	public static boolean isTagPartOpening(String sTagPartOpening) {
		boolean bReturn = false;
		main:{			
			if(!StringZZZ.startsWith(sTagPartOpening, "<")) break main;
			if(!StringZZZ.endsWith(sTagPartOpening, ">")) break main;
			
			if(XmlUtilZZZ.isTagPartClosing(sTagPartOpening)) break main;
			if(XmlUtilZZZ.isTagEmpty(sTagPartOpening)) break main;
			
			bReturn = true;
		}//end main:
		return bReturn;
	}
		
	public static boolean isTagPartClosing(String sTagPart) {
		boolean bReturn = false;
		main:{			
			if(!StringZZZ.startsWith(sTagPart, "</")) break main;
			if(!StringZZZ.endsWith(sTagPart, ">")) break main;
			
			bReturn = true;
		}//end main:
		return bReturn;
	}
	
	public static boolean isTag(String sTagName) {
		boolean bReturn = false;
		main:{						
			if(!StringZZZ.startsWith(sTagName, "<")) break main;
			if(!StringZZZ.endsWith(sTagName, ">")) break main;
			
			//Aber das reicht noch nicht. Ein Tag hat einen Opening-Part und einen Closing-Part
			//oder ist ein Leertag
			boolean bTagEmpty = XmlUtilZZZ.isTagEmpty(sTagName);
			if(bTagEmpty) break main;
			
			//reine Closing oder Opening Tagparts ausschliessen
			boolean bTagOpening = XmlUtilZZZ.isTagPartOpening(sTagName);
			if(bTagOpening) break main;
			
			boolean bTagClosing = XmlUtilZZZ.isTagPartClosing(sTagName);
			if(bTagClosing) break main;
			
			//Ansonsten wird es schwierig, wir müssen den Tagnamen holen
			//Danach kann man pruefen, ob wirklich ein opening Tag am Anfang steht und ein closing Tag am Ende
			//TODOGOON20250209;
			
			bReturn = true;
		}//end main:
		return bReturn;
	}
	
	public static boolean isTagPart(String sTagPart) {
		boolean bReturn = false;
		main:{			
			boolean bTagPartStarting = XmlUtilZZZ.isTagPartOpening(sTagPart);
			boolean bTagPartClosing = XmlUtilZZZ.isTagPartClosing(sTagPart);
			if(!bTagPartStarting & !bTagPartClosing) break main;
			
			bReturn = true;
		}//end main:
		return bReturn;
	}
	
	//+++++
	/*
	 * https://stackoverflow.com/questions/5396164/java-how-to-check-if-string-is-a-valid-xml-element-name
	 */
	public static boolean isTagNameValid(String sTagName) throws ExceptionZZZ{
		return org.apache.xerces.util.XMLChar.isValidName(sTagName);
	}
	
	//+++++++++++++++++++++++++++++
	/**FGL: Der intern verwendete RegEx funktioniert nur, wenn man den ersten Tag kennt, beim Namen
	 * @param sXml
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 09.06.2024, 11:17:35
	 */
	public static ITagByTypeZZZ getTagNextByName(String sTagName, String sXml) throws ExceptionZZZ {
		ITagByTypeZZZ objReturn = null;
		main:{
			if(StringZZZ.isEmpty(sTagName)) break main;
			if(StringZZZ.isEmpty(sXml)) break main;
			
			//StringZZZ.left(sXml, XmlUtilZZZ.sTagClosing )		
			//Pattern regex = Pattern.compile("<DataElements>(.*?)</DataElements>", Pattern.DOTALL);
			String sTagOpening = XmlUtilZZZ.computeTagPartOpening(sTagName); 
			String sTagClosing = XmlUtilZZZ.computeTagPartClosing(sTagName);
					
			objReturn = TagByTypeFactoryZZZ.createTagByName(sTagName);
			
			Pattern regex = Pattern.compile(sTagOpening + "(.*?)" + sTagClosing, Pattern.DOTALL);
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
	
	//######################################
	//### Ensure wirft immer eine Exception, wenn was nicht klappt
	//######################################
	public static boolean ensureExpressionTagNameValid(String sTagName) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			
			if(StringZZZ.isEmpty(sTagName)) {
				ExceptionZZZ ez = new ExceptionZZZ("Expected a filled String as TagName '" + sTagName +"'", iERROR_PARAMETER_VALUE, ReflectCodeZZZ.getClassCallingName(), ReflectCodeZZZ.getMethodCallingName());
				throw ez;				
			}
			
			if(isTag(sTagName)) {
				ExceptionZZZ ez = new ExceptionZZZ("Expected only the tagname as parameter not the tag itself '" + sTagName +"'", iERROR_PARAMETER_VALUE, KernelConfigSectionEntryUtilZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			if(isTagPart(sTagName)) {
				ExceptionZZZ ez = new ExceptionZZZ("Expected only the tagname as parameter not a tagpart itself '" + sTagName +"'", iERROR_PARAMETER_VALUE, KernelConfigSectionEntryUtilZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
						
			bReturn = true;
		}//end main:
		return bReturn;
	}
	
	/*
	 Ensure wirft immer eine Exception, wenn was nicht klappt
	 */
	public static boolean ensureNotTagPart(String sString) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{					
			boolean bTagPart = XmlUtilZZZ.isTagPart(sString);
			if(!bTagPart) {
				ExceptionZZZ ez = new ExceptionZZZ("Expected not a tagpart as parameter '" + sString +"'", iERROR_PARAMETER_VALUE, ReflectCodeZZZ.getClassCallingName(), ReflectCodeZZZ.getMethodCallingName());
				throw ez;
			}
			bReturn = true;
		}//end main:
		return bReturn;
	}
	
	
	/*
	 Ensure wirft immer eine Exception, wenn was nicht klappt
	 */
	public static boolean ensureTagPart(String sTagPart) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			boolean bTag = XmlUtilZZZ.isTag(sTagPart);
			if(bTag) {
				ExceptionZZZ ez = new ExceptionZZZ("Expected only the tagpartname as parameter not tag itself '" + sTagPart +"'", iERROR_PARAMETER_VALUE, ReflectCodeZZZ.getClassCallingName(), ReflectCodeZZZ.getMethodCallingName());
				throw ez;
			}
			
			boolean bTagPart = XmlUtilZZZ.isTagPart(sTagPart);
			if(!bTagPart) {
				ExceptionZZZ ez = new ExceptionZZZ("Expected a tagpartname as parameter '" + sTagPart +"'", iERROR_PARAMETER_VALUE, ReflectCodeZZZ.getClassCallingName(), ReflectCodeZZZ.getMethodCallingName());
				throw ez;
			}
			
			bReturn = true;
		}//end main:
		return bReturn;
	}
	
	/*
	 Ensure wirft immer eine Exception, wenn was nicht klappt
	 */
	public static boolean ensureTagPartOpening(String sTagPartOpening) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			if(StringZZZ.isEmpty(sTagPartOpening)) {
				ExceptionZZZ ez = new ExceptionZZZ("A tagpartname as parameter expected. Is '" + sTagPartOpening +"'", iERROR_PARAMETER_VALUE, ReflectCodeZZZ.getClassCallingName(), ReflectCodeZZZ.getMethodCallingName());
				throw ez;
			}
			
			
			boolean bTag = XmlUtilZZZ.isTag(sTagPartOpening);
			if(bTag) {
				ExceptionZZZ ez = new ExceptionZZZ("Expected only the tagpartname as parameter not tag itself '" + sTagPartOpening +"'", iERROR_PARAMETER_VALUE, ReflectCodeZZZ.getClassCallingName(), ReflectCodeZZZ.getMethodCallingName());
				throw ez;
			}
			
			boolean bTagPart = XmlUtilZZZ.isTagPartOpening(sTagPartOpening);
			if(!bTagPart) {
				ExceptionZZZ ez = new ExceptionZZZ("Expected a tagpartname type opening as parameter '" + sTagPartOpening +"'", iERROR_PARAMETER_VALUE, ReflectCodeZZZ.getClassCallingName(), ReflectCodeZZZ.getMethodCallingName());
				throw ez;
			}
			
			bReturn = true;
		}//end main:
		return bReturn;
	}
	
	/*
	 Ensure wirft immer eine Exception, wenn was nicht klappt
	 */
	public static boolean ensureTagPartClosing(String sTagPartClosing) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{			
			if(StringZZZ.isEmpty(sTagPartClosing)) {
				ExceptionZZZ ez = new ExceptionZZZ("A tagpartname as parameter expected. Is '" + sTagPartClosing +"'", iERROR_PARAMETER_VALUE, ReflectCodeZZZ.getClassCallingName(), ReflectCodeZZZ.getMethodCallingName());
				throw ez;
			}
			
			boolean bTag = XmlUtilZZZ.isTag(sTagPartClosing);
			if(bTag) {
				ExceptionZZZ ez = new ExceptionZZZ("Expected only the tagpartname as parameter not tag itself '" + sTagPartClosing +"'", iERROR_PARAMETER_VALUE, ReflectCodeZZZ.getClassCallingName(), ReflectCodeZZZ.getMethodCallingName());
				throw ez;
			}
			
			boolean bTagPart = XmlUtilZZZ.isTagPartClosing(sTagPartClosing);
			if(!bTagPart) {
				ExceptionZZZ ez = new ExceptionZZZ("Expected a tagpartname type closing as parameter '" + sTagPartClosing +"'", iERROR_PARAMETER_VALUE, ReflectCodeZZZ.getClassCallingName(), ReflectCodeZZZ.getMethodCallingName());
				throw ez;
			}
			
			bReturn = true;
		}//end main:
		return bReturn;
	}
	
	
	//########################################
	//### PREVIOUS
	//########################################
				
	public static String findFirstTagNamePrevious(String sXml, String sSep) throws ExceptionZZZ{
		String sReturn = null;
		main:{
			String sTagPart = XmlUtilZZZ.findFirstTagPartPrevious(sXml, sSep);
			if(sTagPart==null) break main;
			
			sReturn = XmlUtilZZZ.computeTagNameFromTagPart(sTagPart);
		}//end main:
		return sReturn;		
	}
	
	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	
	public static String findFirstOpeningTagNamePrevious(String sXml, String sSep) throws ExceptionZZZ{
		String sReturn = null;
		main:{
			String sTagPart = XmlUtilZZZ.findFirstOpeningTagPartPrevious(sXml, sSep);
			if(sTagPart==null) break main;
			
			sReturn = XmlUtilZZZ.computeTagNameFromTagPart(sTagPart);
		}//end main:
		return sReturn;		
	}
	
	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	
	public static String findFirstClosingTagNamePrevious(String sXml, String sSep) throws ExceptionZZZ{
		String sReturn = null;
		main:{
			String sTagPart = XmlUtilZZZ.findFirstClosingTagPartPrevious(sXml, sSep);
			if(sTagPart==null) break main;
			
			sReturn = XmlUtilZZZ.computeTagNameFromTagPart(sTagPart);
		}//end main:
		return sReturn;		
	}


	//+++++++++++++++++++++++++++++++++++++++++++++++++++
	public static String findFirstTagPartPrevious(String sXml, String sSep) throws ExceptionZZZ{	
		String sReturn = null;
		main:{
			if(!StringZZZ.contains(sXml, sSep)) break main;
			
			String sLEFT = StringZZZ.left(sXml, sSep);
			if(StringZZZ.isEmpty(sLEFT)) break main;
			
			int iIndexCLOSING=-1; int iIndexOPENING = -1;
			
			//Suche nach dem TAG, bzw. dem naechsten gueltigen TAG.
			boolean bGoon = false; boolean bValidTagName = false;
			String sTagPartName = null; String sTagPartNameProof = null;
			do {
 				
				iIndexCLOSING = StringZZZ.indexOfLastBefore(sLEFT, ">");
				if(iIndexCLOSING <= -1) break main; //Dann gibt es kein (weiteres) abschliessendes Tagzeichen
								
				iIndexOPENING = StringZZZ.indexOfLastBehind(sLEFT, "<", iIndexCLOSING); //Dann gibt es kein (weiteres) oeffnendes Tagzeichen
				if(iIndexOPENING <= -1) break main;
				
				
				sTagPartName = StringZZZ.midLeftRight(sLEFT, iIndexOPENING, iIndexCLOSING);
				if(sTagPartName==null)break main;
				
				//Ein gueltiges Tag gefunden? //Leere Tags werden nicht beruecksichtigt	
				sTagPartNameProof = StringZZZ.stripLeft(sTagPartName, "/"); //Egal ob Oeffnendes oder Schliessendes Tag					
				bValidTagName = XmlUtilZZZ.isTagNameValid(sTagPartNameProof);
				if(bValidTagName) {
					bGoon=true;
					break;
				}
				
				sLEFT = StringZZZ.left(sLEFT, iIndexCLOSING-1);//Verbereiten fuer Weitersuchen //falls ein > zwischen opening und closing lag, also nun closing verringern
			} while(bGoon==false);
			
			sReturn = "<" + sTagPartName + ">"; // bei einem abschliesenden Tag ist der SLASH voran im Namen mit dabei.
		}//end main:
		return sReturn;
	}

	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	
	public static String findFirstOpeningTagPartPrevious(String sXml, String sSep) throws ExceptionZZZ{	
		String sReturn = null;
		main:{
			if(!StringZZZ.contains(sXml, sSep)) break main;
			
			String sLEFT = StringZZZ.left(sXml, sSep);
			if(StringZZZ.isEmpty(sLEFT)) break main;
			
			int iIndexCLOSING=-1; int iIndexOPENING = -1;
			
			//Suche nach dem TAG, bzw. dem naechsten gueltigen TAG.
			boolean bGoon = false; boolean bValidTagName = false;
			String sTagPartName = null; 
			do {
 				
				iIndexCLOSING = StringZZZ.indexOfLastBefore(sLEFT,  ">");
				if(iIndexCLOSING <= -1) break main; //Dann gibt es kein (weiteres) abschliessendes Tagzeichen
								
		
				iIndexOPENING = StringZZZ.indexOfLastBehind(sLEFT, "<", iIndexCLOSING); //Dann gibt es kein (weiteres) oeffnendes Tagzeichen
				if(iIndexOPENING <= -1) break main;

				
				
				sTagPartName = StringZZZ.midLeftRight(sLEFT, iIndexOPENING, iIndexCLOSING);
				if(sTagPartName==null)break main;
				
				//Ein gueltiges OEFFNENDES Tag gefunden? 
				if(StringZZZ.startsWith(sTagPartName, "/")) {
					//Kein oeffnendes Tag gefunden
				}else {
					if(StringZZZ.endsWith(sTagPartName, "/")) {
						//Leere Tags werden nicht beruecksichtigt
					}else {						
						bValidTagName = XmlUtilZZZ.isTagNameValid(sTagPartName);
						if(bValidTagName) {
							bGoon=true;
							break;
						}
					}
				}
								
				sLEFT = StringZZZ.left(sLEFT, iIndexCLOSING-1); //Vorbereitung zum Weitersuchen. //falls ein > zwischen opening und closing lag, also nun closing verringern
				
			} while(bGoon==false);
			
			sReturn = "<" + sTagPartName + ">";
		}//end main:
		return sReturn;
	}

	public static String findFirstClosingTagPartPrevious(String sXml, String sSep) throws ExceptionZZZ{	
		String sReturn = null;
		main:{
			if(!StringZZZ.contains(sXml, sSep)) break main;
			
			String sLEFT = StringZZZ.left(sXml, sSep);
			if(StringZZZ.isEmpty(sLEFT)) break main;
			
			int iIndexCLOSING=-1; int iIndexOPENING = -1;
			
			//Suche nach dem TAG, bzw. dem naechsten gueltigen TAG.
			boolean bGoon = false; boolean bValidTagName = false;
			String sTagPartName = null; 
			do {
 				
				iIndexOPENING = StringZZZ.indexOfLastBehind(sLEFT, "</"); //Dann gibt es kein (weiteres) oeffendes Tagzeichen, des Schliessenden TagParts
				if(iIndexOPENING <= -1) break main;		
				
				iIndexCLOSING = StringZZZ.indexOfFirstBefore(sLEFT, ">", iIndexOPENING);
				if(iIndexCLOSING <= -1) break main; //Dann gibt es kein (weiteres) abschliessendes Tagzeichen
								
														
				sTagPartName = StringZZZ.midLeftRight(sLEFT, iIndexOPENING, iIndexCLOSING);
				if(sTagPartName==null)break main;
				
				//Kein schliesendes Tag gefunden
				if(StringZZZ.endsWith(sTagPartName, "/")) {
					//Leere Tags werden nicht beruecksichtigt
				}else {						
					bValidTagName = XmlUtilZZZ.isTagNameValid(sTagPartName);
					if(bValidTagName) {
						bGoon=true;
						break;
					}
				}
				
				sLEFT = StringZZZ.left(sLEFT, (iIndexCLOSING)); //Vorbereitung zum Weitersuchen.
			} while(bGoon==false);
			
			sReturn = "</" + sTagPartName + ">"; 
		}//end main:
		return sReturn;
	}
	
	
	//########################################
	//### NEXT
	//########################################

	public static String findFirstTagNameNext(String sXml, String sSep) throws ExceptionZZZ{
		String sReturn = null;
		main:{
			String sTagPart = XmlUtilZZZ.findFirstTagPartNext(sXml, sSep);
			if(sTagPart==null) break main;
			
			sReturn = XmlUtilZZZ.computeTagNameFromTagPart(sTagPart);
		}//end main:
		return sReturn;
	}

	//++++++++++++++++++++++
	public static String findFirstOpeningTagNameNext(String sXml, String sSep) throws ExceptionZZZ{
		String sReturn = null;
		main:{
			String sTagPart = XmlUtilZZZ.findFirstOpeningTagPartNext(sXml, sSep);
			if(sTagPart==null) break main;
			
			sReturn = XmlUtilZZZ.computeTagNameFromTagPart(sTagPart);
		}//end main:
		return sReturn;
	}
	
	//+++++++++++++++++++++++
	public static String findFirstClosingTagNameNext(String sXml, String sSep) throws ExceptionZZZ{
		String sReturn = null;
		main:{
			String sTagPart = XmlUtilZZZ.findFirstClosingTagPartNext(sXml, sSep);
			if(sTagPart==null) break main;
			
			sReturn = XmlUtilZZZ.computeTagNameFromTagPart(sTagPart);
		}//end main:
		return sReturn;
	}
	
	
	//+++++++++++++++++++++++++++++++++++++++++++++++++++
	public static String findFirstTagPartNext(String sXml, String sSep) throws ExceptionZZZ{
		String sReturn = null;
		main:{
			if(!StringZZZ.contains(sXml, sSep)) break main;
			
			String sRIGHT = StringZZZ.rightback(sXml, sSep);
			if(StringZZZ.isEmpty(sRIGHT)) break main;
			
			int iIndexCLOSING=-1; int iIndexOPENING = -1;
			
			//Suche nach dem TAG, bzw. dem naechsten gueltigen TAG.
			boolean bGoon = false; boolean bValidTagName = false;
			String sTagPartName = null; String sTagPartNameProof = null;
			do {
 				
				iIndexOPENING = StringZZZ.indexOfFirstBehind(sRIGHT , "<");
				if(iIndexOPENING <= -1) break main; //Dann gibt es kein (weiteres) abschliessendes Tagzeichen
								
				
				iIndexCLOSING = StringZZZ.indexOfFirstBefore(sRIGHT , ">", iIndexOPENING); //Dann gibt es kein (weiteres) oeffnendes Tagzeichen
				if(iIndexCLOSING <= -1) break main;
			
				
				sTagPartName = StringZZZ.midLeftRight(sRIGHT, iIndexOPENING, iIndexCLOSING);
				if(sTagPartName==null)break main;
				
				//Ein gueltiges Tag gefunden? //Leere Tags werden nicht beruecksichtigt				
				sTagPartNameProof = StringZZZ.stripLeft(sTagPartName, "/"); //Egal ob Oeffnendes oder Schliessendes Tag
				bValidTagName = XmlUtilZZZ.isTagNameValid(sTagPartNameProof);
				if(bValidTagName) {					
					bGoon=true;
					break;
				}
				
				sRIGHT = StringZZZ.rightback(sRIGHT, iIndexOPENING+1);//falls ein < zwischen opening und closing lag, also nur opening erhöhen und nicht von closing ausgehen.
			} while(bGoon==false);
			
			sReturn = "<" + sTagPartName + ">"; // bei einem abschliesenden Tag ist der SLASH voran im Namen mit dabei.
		}//end main:
		return sReturn;			
	}
	
	//+++++++++++++++++++++++++++++++++++++++++++++++++++
	public static String findFirstOpeningTagPartNext(String sXml, String sSep) throws ExceptionZZZ{
		String sReturn = null;
		main:{
			if(!StringZZZ.contains(sXml, sSep)) break main;
			
			String sRIGHT = StringZZZ.rightback(sXml, sSep);
			if(StringZZZ.isEmpty(sRIGHT)) break main;
			
			int iIndexCLOSING=-1; int iIndexOPENING = -1;
			
			//Suche nach dem TAG, bzw. dem naechsten gueltigen TAG.
			boolean bGoon = false; boolean bValidTagName = false;
			String sTagPartName = null; 
			do {
 				
				iIndexOPENING = StringZZZ.indexOfFirstBehind(sRIGHT, "<"); //Dann gibt es kein (weiteres) oeffnendes Tagzeichen
				if(iIndexOPENING <= -1) break main;

				iIndexCLOSING = StringZZZ.indexOfFirstBefore(sRIGHT,  ">", iIndexOPENING);
				if(iIndexCLOSING <= -1) break main; //Dann gibt es kein (weiteres) abschliessendes Tagzeichen
								
	
				sTagPartName = StringZZZ.midLeftRight(sRIGHT, iIndexOPENING, iIndexCLOSING);
				if(sTagPartName==null)break main;
				
				//Ein gueltiges OEFFNENDES Tag gefunden? 
				if(StringZZZ.startsWith(sTagPartName, "/")) {
					//Kein oeffnendes Tag gefunden
				}else {
					if(StringZZZ.endsWith(sTagPartName, "/")) {
						//Leere Tags werden nicht beruecksichtigt
					}else {						
						bValidTagName = XmlUtilZZZ.isTagNameValid(sTagPartName);
						if(bValidTagName) {
							bGoon=true;
							break;
						}
					}
				}
				
				sTagPartName=null;
				sRIGHT = StringZZZ.rightback(sRIGHT, iIndexOPENING+1); //Vorbereitung zum Weitersuchen. //falls ein < zwischen opening und closing lag, also nur opening erhöhen
			} while(bGoon==false);
			
			sReturn = "<" + sTagPartName + ">";
		}//end main:
		return sReturn;			
	}
	
	//+++++++++++++++++++++++++++++++++++++++++++++++++++
	public static String findFirstClosingTagPartNext(String sXml, String sSep) throws ExceptionZZZ{
		String sReturn = null;
		main:{
			if(!StringZZZ.contains(sXml, sSep)) break main;
			
			String sRIGHT = StringZZZ.rightback(sXml, sSep);
			if(StringZZZ.isEmpty(sRIGHT)) break main;
			
			int iIndexCLOSING=-1; int iIndexOPENING = -1;
			
			//Suche nach dem TAG, bzw. dem naechsten gueltigen TAG.
			boolean bGoon = false; boolean bValidTagName = false;
			String sTagPartName = null; 
			do {
 				
				iIndexOPENING = StringZZZ.indexOfFirstBehind(sRIGHT, "</"); //Dann gibt es kein (weiteres) oeffnendes Tagzeichen
				if(iIndexOPENING <= -1) break main;

				iIndexCLOSING = StringZZZ.indexOfFirstBefore(sRIGHT,  ">", iIndexOPENING);
				if(iIndexCLOSING <= -1) break main; //Dann gibt es kein (weiteres) abschliessendes Tagzeichen
								
	
				sTagPartName = StringZZZ.midLeftRight(sRIGHT, iIndexOPENING, iIndexCLOSING);
				if(sTagPartName==null)break main;
				
				//Kein schliesendes Tag gefunden
				if(StringZZZ.endsWith(sTagPartName, "/")) {
					//Leere Tags werden nicht beruecksichtigt
				}else {						
					bValidTagName = XmlUtilZZZ.isTagNameValid(sTagPartName);
					if(bValidTagName) {
						bGoon=true;
						break;
					}
				}
				
				
				sTagPartName=null;
				sRIGHT = StringZZZ.rightback(sRIGHT, iIndexOPENING+1); //Vorbereitung zum Weitersuchen.
				
				
			
			} while(bGoon==false);
			
			sReturn = "</" + sTagPartName + ">";
		}//end main:
		return sReturn;						
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
				vecReturn = StringZZZ.vecMid(sExpression, objTagType.getTagPartOpening(), objTagType.getTagPartClosing(), false,true);
				
			}
		}
		return vecReturn;
	}
	
	public static boolean isExpression(String sExpression) throws ExceptionZZZ{
		boolean bReturn = true;
		main:{
			if(StringZZZ.isEmpty(sExpression)) break main;
			
			boolean bCheck = false;
			bCheck = XmlUtilZZZ.containsTagName(sExpression, "Z", false);
			if(bCheck) break main;
					
			//Ein Z-Tag ist nicht zwingend notwendig
			//Weil nur der Start-Tag nicht reicht, auch den Ende Tag mitnehmen.
			int iIndex = StringZZZ.indexOfFirst(sExpression, "<Z:", false);
			if (iIndex>=0) {
			
				//Das Ende Tag sollte auch hinter dem Starttag liegen
				iIndex = StringZZZ.indexOfFirst(sExpression, "</Z:", iIndex, false);
				if (iIndex>=0)break main;
			}
			
			//Path Anweisungen koennen auch ohne Z-Tags hier stehen
			iIndex = StringZZZ.indexOfFirst(sExpression, KernelZFormulaIni_PathZZZ.sTAGPART_OPENING, false);
			if (iIndex>=0) {
				
				//Das Ende Tag sollte auch hinter dem Starttag liegen
				iIndex = StringZZZ.indexOfFirst(sExpression, KernelZFormulaIni_PathZZZ.sTAGPART_CLOSING, iIndex, false);
				if (iIndex>=0)break main;				
			}
			
			//TODOGOON20250222: Evt. Noch Empty-Tag und Null-Tag hier hierein
			
			
			bReturn = false;
		}//end main:
		return bReturn;
	}
	
	public static boolean isExpression(String sExpression, ITagTypeZZZ objTagType) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			boolean btemp = StringZZZ.contains(sExpression, objTagType.getTagPartOpening(), false);
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
			bReturn = XmlUtilZZZ.containsTagName(sExpression, sTagName, false);
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
	public static boolean isExpression4Tag(String sExpression, String sTagOpening, String sTagClosing){
		boolean bReturn = false;
		main:{			
			bReturn=StringZZZ.containsAsTag(sExpression, sTagOpening, sTagClosing, false);
			if(bReturn) break main;
			
		}//end main
		return bReturn;
	}
	
	
	/** Also der String beginnt mit dem Starting Tag und endet mit dem Closing Tag
	 * @param sLineWithExpression
	 * @param sTagOpening
	 * @param sTagClosing
	 * @return
	 * @author Fritz Lindhauer, 05.09.2024, 18:30:57
	 */
	public static boolean isSurroundedByTag(String sExpression, String sTagOpening, String sTagClosing){
		boolean bReturn = false;
		main:{
			if(StringZZZ.isEmpty(sExpression)) break main;
			
		
			boolean bSurroundedLeft = StringZZZ.startsWithIgnoreCase(sExpression, sTagOpening);
			boolean bSurroundedRight = StringZZZ.endsWithIgnoreCase(sExpression, sTagClosing);
			
			if(bSurroundedLeft && bSurroundedRight) bReturn = true;
		}//end main:
		return bReturn;
	}
	
	public static Vector3ZZZ<String>parseFirstVector(String sExpression, String sTagOpening, String sTagClosing, boolean bKeepSurroundingSeparatorsOnParse) throws ExceptionZZZ{
		Vector3ZZZ<String>vecReturn = new Vector3ZZZ<String>();		
		main:{
			//Bei dem einfachen Tag wird das naechste oeffnende Tag genommen und dann auch das naechste schliessende Tag...
			vecReturn = StringZZZ.vecMidFirst(sExpression, sTagOpening, sTagClosing, bKeepSurroundingSeparatorsOnParse, false);			
		}
		return vecReturn;
	}

	
	
}
