package basic.zBasic.util.log;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IConstantZZZ;
import basic.zBasic.IReflectCodeZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.abstractList.ArrayListZZZ;
import basic.zBasic.util.abstractList.ArrayListUniqueZZZ;
import basic.zBasic.util.abstractList.ArrayListUtilZZZ;
import basic.zBasic.util.datatype.string.IStringJustifierZZZ;
import basic.zBasic.util.datatype.string.StringArrayZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.datatype.xml.XmlUtilZZZ;
import basic.zBasic.xml.tagtype.ITagByTypeZZZ;
import basic.zBasic.xml.tagtype.TagByTypeFactoryZZZ;

public class LogStringFormaterUtilZZZ implements IConstantZZZ{
	
	public static boolean isFormatUsingControl(IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) {
		boolean bReturn = false;
		main:{
			if(ienumFormatLogString==null) break main;
			
			int iArgumentType = ienumFormatLogString.getArgumentType();
			
			switch(iArgumentType) {
			case ILogStringFormatZZZ.iARG_CONTROL:
				bReturn = true;
				break;
			case ILogStringFormatZZZ.iARG_CONTROLSTRING:
				bReturn = true;
				break;
			default:
				bReturn = false;
			};
		}//end main:
		return bReturn;
		
	}

	public static boolean isFormatUsingHashMap(IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			if(ienumFormatLogString==null) break main;
			
			int iArgumentType = ienumFormatLogString.getArgumentType();
			
			switch(iArgumentType) {
			case ILogStringFormatZZZ.iARG_STRINGHASHMAP:
				bReturn = true;
				break;
			default:
				bReturn = false;
			};
		}//end main:
		return bReturn;
		
	}

	public static boolean isFormatUsingObject(IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) {
		boolean bReturn = false;
		main:{
			if(ienumFormatLogString==null) break main;
			
			int iArgumentType = ienumFormatLogString.getArgumentType();
			
			switch(iArgumentType) {
			case ILogStringFormatZZZ.iARG_OBJECT:
				bReturn = true;
				break;
			case ILogStringFormatZZZ.iARG_SYSTEM:
				bReturn = true;
				break;
			default:
				bReturn = false;
			};
		}//end main:
		return bReturn;
		
	}

	//############################################################
	public static boolean isFormatUsingString(IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			if(ienumFormatLogString==null) break main;
			
			int iArgumentType = ienumFormatLogString.getArgumentType();
			
			switch(iArgumentType) {
			case ILogStringFormatZZZ.iARG_STRING:
				bReturn = true;
				break;
			case ILogStringFormatZZZ.iARG_CONTROLSTRING:
				bReturn = true;
				break;
			default:
				bReturn = false;
			};
		}//end main:
		return bReturn;
		
	}

	public static boolean isFormatUsingStringXml(IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			if(ienumFormatLogString==null) break main;
			
			int iArgumentType = ienumFormatLogString.getArgumentType();
			
			switch(iArgumentType) {
			case ILogStringFormatZZZ.iARG_STRINGXML:
				bReturn = true;
				break;
			default:
				bReturn = false;
			};
		}//end main:
		return bReturn;
		
	}
	
	//#######################################################################################################################
	public static String justifyInfoPart(IStringJustifierZZZ objStringJustifier, String sLog) throws ExceptionZZZ{
		String sReturn = null;
		main:{
			if(StringZZZ.isEmpty(sLog)) break main;
					
			//Teile die Zeile ggfs. am Zeilenumbruch auf
			String[] saLine = StringZZZ.explode(sLog, StringZZZ.crlf());
			sReturn = LogStringFormaterUtilZZZ.justifyInfoPart(objStringJustifier, saLine);			
		}//end main:
		return sReturn;
	}
	
	public static String justifyInfoPart(IStringJustifierZZZ objStringJustifier, String[] saLine) throws ExceptionZZZ{
		String sReturn = null;
		main:{
			if(StringArrayZZZ.isEmpty(saLine)) break main;
			
			//In den übergebenen Zeilen koennten weitere Kommentartrenner stecken.
			//Daher jede Zeile splitten und danach das neue "Standardisierte Array" weiterverarbeiten
			//Ggfs. auch die erste Zeile und die 2te Zeile zusammenfassen.
			String[] saLineNormed = normStringForJustify(objStringJustifier,saLine);
			
			//Bei 0 wuerde nix buendig gemacht, darum die Grenze ggfs. explizit auf die Position des ersten SepartorStrings setzen
			int iIndexMayIncrease = objStringJustifier.getInfoPartBoundLeftBehind2use(saLineNormed[0]); 
			objStringJustifier.setInfoPartBoundLeftBehindIncreased(iIndexMayIncrease);
			
			//Nun das Standardisierte Array anpassen
			if(saLineNormed.length>=2) {
				//Nun über mehrere Zeilen das machen!!! Einmal hin und wieder zurueck
				String[] saLineReversed1 = ArrayUtilZZZ.reverse(saLineNormed);
				ArrayListUniqueZZZ<String>listasLineReversedJustified1 = new ArrayListUniqueZZZ<String>();
				for(String sLine : saLineReversed1) {
					String sLineJustified = objStringJustifier.justifyInfoPart(sLine);
					listasLineReversedJustified1.add(sLineJustified);
				}
				
				ArrayListUniqueZZZ<String>listasLineReversed2 = (ArrayListUniqueZZZ<String>) ArrayListUtilZZZ.reverse(listasLineReversedJustified1);
				ArrayListUniqueZZZ<String>listasLineReversedJustified2 = new ArrayListUniqueZZZ<String>();
				for(String sLine : listasLineReversed2) {
					String sLineJustified = objStringJustifier.justifyInfoPart(sLine);
					listasLineReversedJustified2.add(sLineJustified);
				}
				
				//Die Zeilen so verbinden, das sie mit einem "System.println" ausgegeben werden können.
				for(String sLineJustified : listasLineReversedJustified2) {
					if(sReturn==null){
						sReturn = sLineJustified;
					}else {					
						sReturn = sReturn + StringZZZ.crlf() + sLineJustified;
					}
				}							
			}else {
				String sLine = saLineNormed[0];
				if(sLine!=null) {
					sReturn = objStringJustifier.justifyInfoPart(sLine);					
				}
			}		
		}//end main:
		return sReturn;
	}
	public static String justifyInfoPart(IStringJustifierZZZ objStringJustifier, ArrayListUniqueZZZ<String> listasLine) throws ExceptionZZZ{
		String sReturn = null;
		main:{
			if(listasLine==null) break main;
			if(listasLine.isEmpty()) break main;
			
			String[] saLine = ArrayListUtilZZZ.toArray(listasLine, String.class);
			sReturn = LogStringFormaterUtilZZZ.justifyInfoPart(objStringJustifier, saLine);
			
		}//end  main:
		return sReturn;
	}
	
	//###############################################
	//Wenn nun schon eine StringPosition uebergeben wurde, dann wird die Kommentarmarkierung ggfs. unnoetigerweise weit nach rechts verschoben.
	//Also aufteilen, trimmen und neu bauen.
	public static String justifyInfoPartAdded(IStringJustifierZZZ objStringJustifier, String sLine, String sLog) throws ExceptionZZZ{
		String sReturn = null;
		main:{
			boolean bLineEmpty = StringZZZ.isEmpty(sLine);
			boolean bLogEmpty  = StringZZZ.isEmpty(sLog);
			if( bLogEmpty & bLineEmpty ) break main;
			if(bLogEmpty && !bLineEmpty) {
				return justifyInfoPart(objStringJustifier, sLine);
			}else if (!bLogEmpty && bLineEmpty) {
				return justifyInfoPart(objStringJustifier, sLog);
			}
			
			String[]saLogCommented = normStringForJustify(objStringJustifier, sLine, sLog);
			
			sReturn = justifyInfoPart(objStringJustifier, saLogCommented);			
		}//end  main:
		return sReturn;
	}
	
	
	public static String[] normStringForJustify(IStringJustifierZZZ objStringJustifier,String sLine, String sLog) throws ExceptionZZZ {
		String[] saReturn=null;
		main:{
			boolean bLineEmpty = StringZZZ.isEmpty(sLine);
			boolean bLogEmpty  = StringZZZ.isEmpty(sLog);
			if(bLineEmpty && bLogEmpty) break main;
			
			if(bLogEmpty) {
				saReturn = new String[1];
				saReturn[0] = sLine;
				break main;
			}
			
			if(bLineEmpty) {
				sLine = "";
			}
			
			//es reicht nicht  ILogStringFormatZZZ.sSEPARATOR_MESSAGE_DEFAULT, es muss der Formatierte Kommentartrenner sein.
			String sSeparatorMessageDefault = LogStringFormaterUtilZZZ.computeLinePartInLog(ILogStringFormatZZZ.LOGSTRINGFORMAT.CONTROL_SEPARATORMESSAGE_STRING);
			
				
			//Splitte sLog auf, ggfs. Hier vorhandene Kommentarzeilen
			String[]saLog = StringZZZ.explode(sLog, sSeparatorMessageDefault);
			
			//Trimme nun ggf. Leerzeichen weg
			String[]saLogTrimmed = StringArrayZZZ.trim(saLog);
			
			//Ergänze nun die getrimmten String mit einem neuen Kommentar-Marker voranstellen
			int iIndex=-1;
			saReturn = new String[saLogTrimmed.length];
			for(String sLogTrimmed : saLogTrimmed ) {
				if(!StringZZZ.isEmpty(sLogTrimmed)){					
					iIndex++;
					if(iIndex==0) {
						//vergiss nicht den sLine String an der ersten Stelle voranzustellen (das ist der add-Moment)	
						if(sLine.endsWith(sSeparatorMessageDefault)) {
							saReturn[iIndex] = sLine + sLogTrimmed;
						}else {
							saReturn[iIndex] = sLine + sSeparatorMessageDefault + sLogTrimmed;
						}
					}else {					
						saReturn[iIndex] = sSeparatorMessageDefault + sLogTrimmed;					
					}		
				}
			}
		}//end main:
		return saReturn;
	}
	
	
	public static String[] normStringForJustify(IStringJustifierZZZ objStringJustifier, String sLog) throws ExceptionZZZ {
		return normStringForJustify_(objStringJustifier, sLog);
	}
	
	public static String[] normStringForJustify(IStringJustifierZZZ objStringJustifier,String[] saLogIn) throws ExceptionZZZ {
		return normStringForJustify_(objStringJustifier, saLogIn);
	}
	
	private static String[] normStringForJustify_(IStringJustifierZZZ objStringJustifier,String... saLogIn) throws ExceptionZZZ {
		String[] saReturn=null;
		main:{		
			if(ArrayUtilZZZ.isEmpty(saLogIn)) break main;
			
			
			//TODOGOON20251215;//nur der ILogStringFormatZZZ.sSEPARATOR_MESSAGE_DEFAULT reicht nicht, das muss der Formatierte String sein: "[A00/]# "
			String sSeparatorMessageDefault = LogStringFormaterUtilZZZ.computeLinePartInLog(ILogStringFormatZZZ.LOGSTRINGFORMAT.CONTROL_SEPARATORMESSAGE_STRING);
				
			
			//Die Einträge aufteilen und trimmen.
			ArrayListZZZ<String>listasLogTrimmed = new ArrayListZZZ<String>();
			for(String sLog : saLogIn) {
				if(sLog!=null) {
					
					String[] saLogSub = StringZZZ.explode(sLog, sSeparator);
					for(String sLogSub : saLogSub) {
						if(!StringZZZ.isEmpty(sLogSub)){         //Leerwerte weglassen
							listasLogTrimmed.add(sLogSub.trim());//trimmen
						}
					}
				}
			}			
			String[] saLogTrimmed = listasLogTrimmed.toStringArray();				
			if(ArrayUtilZZZ.isEmpty(saLogTrimmed)) break main;
			
			//Merke: Ziel ist das Zusammenfassen der 1. + 2. Zeile
			//       Dabei wird davon ausgegangen, das in der 1. Zeile so etwas wie die Codepostion steht
			//       und in der 2. Zeile ff dann die Kommentare.
			int iIndex=0;
			ArrayListZZZ<String>listasReturn = new ArrayListZZZ<String>();
			if(saLogTrimmed.length>=2) {
				//Ergänze nun die getrimmten String mit einem neuen Kommentar-Marker voranstellen
				for(String sLog : saLogTrimmed ) {					
					if(iIndex==0) {
						//Zusammenfassen der Zeilen 1. + 2. . Dabei gehe ich davon aus, das im 1. Kommentar so etwas wie die CodePosition steht.					
						String sReturnTemp = saLogTrimmed[0] + sSeparatorMessageDefault + saLogTrimmed[1];
						listasReturn.add(sReturnTemp);
						iIndex++;
					}else if(iIndex==1) {
						//Mache wg. der Zusammenfassung in 0 hier nix.
						iIndex++;
					}else if(iIndex>=2) {	
						if(!StringZZZ.isEmpty(sLog)) { //Damit keine Leeren Kommentarzeilen reinkommen in das Return-Array
							String sReturnTemp = sSeparatorMessageDefault + sLog;
							listasReturn.add(sReturnTemp);
							iIndex++;
						}
					}				
				}
			}else {
				if(!StringZZZ.isEmpty(saLogTrimmed[0])) { //Damit keine Leeren Kommentarzeilen reinkommen in das Return-Array
					//Zur Vorbereitung, falls irgendwann ein echter Kommentar kommt, einen Kommentar-Marker hintenangestellt
					String sReturnTemp = saLogTrimmed[0] + sSeparatorMessageDefault; 				
					listasReturn.add(sReturnTemp);
				}
			}
						
			saReturn = listasReturn.toStringArray();
		}//end main:
		return saReturn;
	}
	
	
	public static String computeLinePartInLog(IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		String sReturn = null;
		
		main:{
//			Class classObj = null;		
//			if(classObjIn==null) {
//				//In den aufrufenden Methoden dieser private Methode sollte das schon geklaert sein.
//				ExceptionZZZ ez = new ExceptionZZZ("Class-Object", iERROR_PARAMETER_MISSING, AbstractLogStringFormaterZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
//				throw ez;					
//			}else {
//				classObj = classObjIn;
//			}
			
			if(ienumFormatLogString == null) {
				ExceptionZZZ ez = new ExceptionZZZ("IEnumSetMappedLogStringFormatZZZ", iERROR_PARAMETER_MISSING, AbstractLogStringFormaterZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;				
			}
			if (!isFormatUsingControl(ienumFormatLogString)) break main; // Hier werden also nur Werte errechnet aufgrund des Objekts selbst
		
		    					   
			String sFormat=null; String sLeft=null; String sMid = null; String sRight=null;
			String sMessageSeparator=null;
			
			String sPrefixSeparator = ienumFormatLogString.getPrefixSeparator();
			String sPostfixSeparator = ienumFormatLogString.getPostfixSeparator();
//			String sLog= null;
//			if(sLogIn!=null) {
//				String sOuter = XmlUtilZZZ.findTextOuterXml(sLogIn);
//				if(!StringZZZ.isEmpty(sOuter)) {				
//					//+++ Problem: Wenn '# ' um den XML String stehen, dann wird das fuer eine neue Zeile verwendet
//					//    Das wird erzeugt durch ReflectCodeZZZ.getPositionCurrent()
//					//    sPOSITION_MESSAGE_SEPARATOR wird explizit dahinter gesetzt.
//					//Darum entfernen wir dies ggfs.
//					sOuter = StringZZZ.trimRight(sOuter, IReflectCodeZZZ.sPOSITION_MESSAGE_SEPARATOR );
//					if(StringZZZ.isEmpty(sOuter)) break main;
//					sLog = StringZZZ.joinAll(sLog, sOuter);
//				}else {
//					//Also: sOuter ist Leerstring oder Null UND es nicht explizit ein XML, nur dann den String übernehme
//					boolean bContainsXml = XmlUtilZZZ.isXmlContained(sLogIn);	
//					if(bContainsXml) {
//						//mache nix
//					}else {
//						sLog = StringZZZ.joinAll(sLog, sOuter);
//					}
//				}				
//			}
			//Ziel ist es eine unnoetigerweise erzeugte Leerzeile mit KommentarSeparator zu verhindern.
			if(sLog==null)break main; //Ein explizit uebergebener Leerstring gilt aber.
			
			String sLog = "";
	        switch (ienumFormatLogString.getFactor()) {
	            case ILogStringFormatZZZ.iFACTOR_CONTROLMESSAGESEPARATOR_STRING:
	            	//ByControl?
	                  sFormat = this.getHashMapFormatPositionString().get(
	                        new Integer(ILogStringFormatZZZ.iFACTOR_CONTROLMESSAGESEPARATOR_STRING));	                    
	                  sMessageSeparator = String.format(sFormat, ILogStringFormatZZZ.sSEPARATOR_MESSAGE_DEFAULT);
	                  sMessageSeparator = sPrefixSeparator + sMessageSeparator + sLog + sPostfixSeparator;
	                  
	                  sReturn = sMessageSeparator;
	                break;
	                
	            case ILogStringFormatZZZ.iFACTOR_CONTROLMESSAGESEPARATOR_XML:
	            	//ByControl?
	                sFormat = this.getHashMapFormatPositionString().get(
	                        new Integer(ILogStringFormatZZZ.iFACTOR_CONTROLMESSAGESEPARATOR_XML));	                    
	                sMessageSeparator = String.format(sFormat, ILogStringFormatZZZ.sSEPARATOR_MESSAGE_DEFAULT);
	                sMessageSeparator = sPrefixSeparator + sMessageSeparator + sLog + sPostfixSeparator;
	                  

		        	ITagByTypeZZZ objTagMessageSeparator = TagByTypeFactoryZZZ.createTagByName(TagByTypeFactoryZZZ.TAGTYPE.MESSAGESEPARATOR, sMessageSeparator);
		        	String sMessageSeparatorTag = objTagMessageSeparator.getElementString();
		            
	                sReturn = sMessageSeparatorTag;
	                break;
	                
	            default:
	                System.out.println("AbstractLogStringZZZ.computeByControl_(..,..): Dieses Format ist nicht in den gültigen Formaten für einen objektbasierten LogString vorhanden. iFaktor=" + ienumFormatLogString.getFactor());
	                break;
	        }				          
		}//end main: 
		return sReturn;
	}


	
}
