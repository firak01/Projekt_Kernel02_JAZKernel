package basic.zBasic.util.xml;

import java.util.HashMap;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractList.HashMapIndexedZZZ;
import basic.zBasic.util.abstractList.HashMapMultiIndexedZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;

/**Loese XML-Tags per Regulaerem Ausdruck auf.
 * 
 * Ideen dazu hier:
 * https://colinchjava.github.io/2023-10-01/17-43-33-264642-matching-xml-tags-with-java-regular-expressions/
 * https://stackoverflow.com/questions/335250/parsing-xml-with-regex-in-java 
 *  
 * 
 * @author Fritz Lindhauer, 25.05.2024, 06:41:20
 */
public class XmlTagMatcherZZZ {
	public static Vector<String> parseElementsAsVector(String sXml) throws ExceptionZZZ {
		return XmlTagMatcherZZZ.parseElementsAsVector(sXml, true);
	}
	public static Vector<String> parseElementsAsVector(String sXml, boolean bWithAnyValue) throws ExceptionZZZ {
		Vector<String> vecReturn = null;
		main:{
			if(StringZZZ.isEmpty(sXml)) break main;
			
			vecReturn = new Vector<String>();
			
			//siehe in den Tryouts die Loesungsschritte hierfuer:
			String sXmlRemain = sXml;
			String sLeft;
			 
			//##################################
			String regex = "<(.*?)>";
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(sXml);	 	
			while (matcher.find()) {
			    String sTag = matcher.group(1);
			    sLeft = StringZZZ.left(sXmlRemain, "<" + sTag + ">");
			    System.out.println("sLeft:\t\t" + sLeft);//so etwas ohne Tag muss in ein Tag vom ITagTypeZZZ "Text"
			    System.out.println("sTag:\t\t"  + sTag);
			    
			    if(bWithAnyValue){			    	
			    	if(!StringZZZ.isEmpty(sLeft)) {
			    		vecReturn.add(sLeft);
			    	}
			    }else {
			    	
			    }
			    vecReturn.add("<" + sTag + ">");
			    
			    //Wenn man jetzt schon sXmlRemain verwendet, dann findet der Ausdruck zu Suche nach dem TagNamen nix mehr.
			    sXmlRemain = StringZZZ.rightback(sXmlRemain, "<"+sTag+">");
			    System.out.println("sRemain:\t" + sXmlRemain);
			  
			    //weil man sich wg. sXmlRemain diesen Pattern sparen kann
			    //Pattern regex2 = Pattern.compile("<"+sTag +">(.*?)</"+sTag+">", Pattern.DOTALL);     
			    //Matcher matcher2 = regex2.matcher(sXmlRemain);
			    //while (matcher2.find()) {
			    //	String stemp = matcher2.group(1);
			    //	System.out.println("g1: '" + stemp+ "'");		    	
			    //}
			}
				
			//Den Rest hinzufuegen
			if(bWithAnyValue) {
				if(!StringZZZ.isEmpty(sXmlRemain)) {
					vecReturn.add(sXmlRemain);
				}
			}
		}//end main:
		return vecReturn;
	}
	
	public static Vector<String>parseAnyValueForTagAsVector(String sXml, String sTag) throws ExceptionZZZ{
		Vector<String>vecReturn= null;
		main:{
			if(StringZZZ.isEmpty(sXml))break main;
			if(StringZZZ.isEmpty(sTag))break main;			
			vecReturn = new Vector<String>();
			
			String sXmlRemain = sXml;
			
			// sXmlRemain = StringZZZ.rightback(sXmlRemain, "<"+sTag+">");
			// System.out.println("sRemain:\t" + sXmlRemain);
			  
			
			Pattern regex2 = Pattern.compile("<"+sTag +">(.*?)</"+sTag+">", Pattern.DOTALL);     
		    Matcher matcher2 = regex2.matcher(sXmlRemain);
		    while (matcher2.find()) {
		    	String stemp = matcher2.group(1);
		    	System.out.println("g1: '" + stemp+ "'");
		    	
		    	vecReturn.add(stemp);
		    }
		    
		    Pattern regex3 = Pattern.compile("<"+sTag +"/>");     
		    Matcher matcher3 = regex3.matcher(sXmlRemain);
		    while (matcher2.find()) {
		    	String stemp = matcher2.group(1);
		    	System.out.println("g1: '" + stemp+ "'");
		    	
		    	vecReturn.add(stemp);
		    }

			
		}//end main_
		return vecReturn;
	}
	
	public static HashMapMultiIndexedZZZ<String,String> parseElementsAsMap(String sXml) throws ExceptionZZZ {
		return XmlTagMatcherZZZ.parseElementsAsMap(sXml, true);
	}
	
	/**Gib eine HashMap zurueck mit Tag als Schluessel und String als Wert.
	 * 
	 * 
	 * @param sXml
	 * @param bWithAnyValue
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 25.05.2024, 09:08:35
	 */
	public static HashMapMultiIndexedZZZ<String,String> parseElementsAsMap(String sXml, boolean bWithAnyValue) throws ExceptionZZZ {
		return parseElementsAsMap_(null, sXml, bWithAnyValue);
	}
	
	public static HashMapMultiIndexedZZZ<String,String> parseElementsAsMap(HashMapMultiIndexedZZZ<String,String> hmReturnIn, String sXml, boolean bWithAnyValue) throws ExceptionZZZ {
		return parseElementsAsMap_(hmReturnIn, sXml, bWithAnyValue);
	}
	
	private static HashMapMultiIndexedZZZ<String,String> parseElementsAsMap_(HashMapMultiIndexedZZZ<String,String> hmReturnIn, String sXml, boolean bWithAnyValue) throws ExceptionZZZ {
		HashMapMultiIndexedZZZ<String,String> hmReturn = null;
		main:{		
			boolean bMapFilledBefore;
			if(hmReturnIn!=null) {
				hmReturn = hmReturnIn;
				if(StringZZZ.isEmpty(sXml)) break main;
				
				bMapFilledBefore=true;//Falls jetzt nur noch der Wert vorhanden ist, kaeme sonst eind text-Tag drum.
				                      //Das ist aber falsch, weil der Wert schon in dem XML-Wert vorhanden sein sollte.
			}else {
				if(StringZZZ.isEmpty(sXml)) break main;				
				hmReturn = new HashMapMultiIndexedZZZ<String,String>();
				
				bMapFilledBefore=false; //Nimm nun einzelstehende Werte mit einen text-Tag auf.
			}
			
			//siehe in den Tryouts die Loesungsschritte hierfuer:
			String sXmlRemain = sXml;
			String sLeft;
			String sContinuingClosingTag = null;
			boolean bParseNextTagOnThisLevel=true;
			 
			//##################################
			String regex = "<(.*?)>";
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(sXml);	 	
			while (matcher.find()) {
				boolean bClosingTag = false;
			    String sValue = null;
			    String sTag = matcher.group(1);			    
			      	
			    if(sContinuingClosingTag!=null) {
			    	//Warte nun Darauf, dass der passende EndTag gefunden wird.
			    	if(sTag.equals(sContinuingClosingTag)) {
			    		bParseNextTagOnThisLevel=true;
			    	}else {
			    		bParseNextTagOnThisLevel=false;
			    	}
			    }else {
			    	bParseNextTagOnThisLevel=true;
			    }
			    
			    if(bParseNextTagOnThisLevel) {//alle "zwischentags des Werts wurden in Rekursion verarbeitet. Nun geht es zu neuen Werten.
				    //Also den/einen abschliessenden Tag nicht betrachten
				    //if(!StringZZZ.startsWith(sTag, sContinuingClosingTag)) {
			    	  if(!StringZZZ.startsWith(sTag, "/")) {
				    	
				    	
					    if(bWithAnyValue){
					    	//Nimm ggfs. existierende Texte VOR dem Tag auf.
							sLeft = StringZZZ.left(sXmlRemain, "<" + sTag + ">");
						    System.out.println("sLeft:\t\t'" + sLeft+"'");//so etwas ohne Tag muss in ein Tag vom ITagTypeZZZ "Text"
						    System.out.println("sTag:\t\t'"  + sTag+"'");
						    
					    	if(!StringZZZ.isEmpty(sLeft)) {
					    		hmReturn.put("text",sLeft);
					    	}
					    }else {
					    	
					    }
			    	
						//Ermittle die Werte fuer den Tag per RegEx und packe den Tag und den Wert in die Map
					    Vector<String>vecValue=XmlTagMatcherZZZ.parseAnyValueForTagAsVector(sXmlRemain, sTag);
						if(vecValue!=null) {
						    if(!vecValue.isEmpty()) {
						    	sValue = vecValue.get(0);
						    	hmReturn.put(sTag, sValue);
						    	
						    	//TJA: und nun ggfs. in sValue vorhandene Tags per Rekursion aufloesen.....
						    	hmReturn = XmlTagMatcherZZZ.parseElementsAsMap(hmReturn, sValue, bWithAnyValue);
						    	sContinuingClosingTag = "/"+sTag;
						    	//Der naechste Tag ist in der While Schleife entweder ein Tag "dazwischen" oder der abschliessende Tag
						    	//bParseNextTagOnThisLevel = true; //Solane bis der abschliesende Tag nicht kommt nix weitermachen.
						    }else {
						    	sValue = null; //Fazit: Leere Wertvektoren nicht übernehmen, das ist wichtig, sonst wird der Index der aeusseren HashMap auch noch um 1 erhoeht.
						    }				    					    					    				    					    	
					    }
				    }else {
				    	bClosingTag = true;	
				    					    	
				    	 //Text nach einem abschliessenden Text erfassen, bis zu einem anderen abschliessenden Tag			
						  //Erst nach der Bestimmung des Werts sXmlRemain errechnen.
						    if(sValue!=null) {
						    	//sXmlRemain = StringZZZ.rightback(sXmlRemain, "<"+sTag+">"+sValue);
						    	//sXmlRemain = StringZZZ.rightback(sXmlRemain, sValue + "<"+sTag+">");
						    	sXmlRemain = StringZZZ.rightback(sXmlRemain, sValue + "<"+sContinuingClosingTag+">");
						    }else {
						    	//sXmlRemain = StringZZZ.rightback(sXmlRemain, "<"+sTag+">");
						    	sXmlRemain = StringZZZ.rightback(sXmlRemain, "<"+sContinuingClosingTag+">");
						    }
						   //sXmlRemain = StringZZZ.rightback("<"+sContinuingClosingTag+">" + sXmlRemain, "<"+sContinuingClosingTag+">");
						   System.out.println("sRemain:\t'" + sXmlRemain+"'");
						   if(StringZZZ.isEmpty(sXmlRemain)) break main;
	
						   sContinuingClosingTag=null;
				    }//end if( Abschliessende Tags ausschliessen )
			    
			    }//end if (ParseNextTagOnThisLevel)
			    
			   
			   if(bClosingTag && bWithAnyValue) {

			    	sLeft = StringZZZ.left(sXmlRemain + "<", "<");
				    System.out.println("sRightClosing:\t'" + sLeft + "'");//so etwas ohne Tag muss in ein Tag vom ITagTypeZZZ "Text"
				   
				    if(!StringZZZ.isEmpty(sLeft)) {
				    	hmReturn.put("text",sLeft);
				    	sXmlRemain = StringZZZ.rightback(sXmlRemain, sLeft);
				    }else {
				    	
				    }
				   
			   }
			}//end while (matcher.find()) {
				
			//Den Rest hinzufuegen
			if(bWithAnyValue) {
				if(bMapFilledBefore && !sXml.equals(sXmlRemain)) { //Damit sollen einzelstehnde Werte zwar erfasst werden, aber nicht wenn sie schon in einem Tag vorhanden sind.
					if(!StringZZZ.isEmpty(sXmlRemain)) {
						hmReturn.put("text", sXmlRemain);
					}
				}else if(!bMapFilledBefore) {
					if(!StringZZZ.isEmpty(sXmlRemain)) {  //Das ist dann der Fall für nur einzelstehende Werte OHNE ein Tag.
						hmReturn.put("text", sXmlRemain);
					}
				}
			}
		}//end main:
		return hmReturn;
	}
	
}
