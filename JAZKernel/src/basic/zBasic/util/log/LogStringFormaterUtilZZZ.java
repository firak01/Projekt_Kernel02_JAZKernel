package basic.zBasic.util.log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IConstantZZZ;
import basic.zBasic.IReflectCodeZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedZZZ;
import basic.zBasic.util.abstractList.ArrayListZZZ;
import basic.zBasic.util.abstractList.ArrayListUniqueZZZ;
import basic.zBasic.util.abstractList.ArrayListUtilZZZ;
import basic.zBasic.util.datatype.enums.EnumAvailableHelperZZZ;
import basic.zBasic.util.datatype.string.IStringJustifierZZZ;
import basic.zBasic.util.datatype.string.StringArrayZZZ;
import basic.zBasic.util.datatype.string.SeparatorMessageStringJustifierZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.datatype.xml.XmlUtilZZZ;
import basic.zBasic.util.log.ILogStringFormatZZZ.LOGSTRINGFORMAT;
import basic.zBasic.xml.tagtype.ITagByTypeZZZ;
import basic.zBasic.xml.tagtype.TagByTypeFactoryZZZ;

import static basic.zBasic.util.log.ILogStringFormatZZZ.iFACTOR_CONTROLMESSAGESEPARATOR_STRING;
import static basic.zBasic.util.log.ILogStringFormatZZZ.iFACTOR_CONTROLMESSAGESEPARATOR_XML;
import static basic.zBasic.util.log.ILogStringFormatZZZ.iFACTOR_CONTROL01SEPARATOR_STRING;
import static basic.zBasic.util.log.ILogStringFormatZZZ.iFACTOR_CONTROL01SEPARATOR_XML;
import static basic.zBasic.util.log.ILogStringFormatZZZ.iFACTOR_CONTROL02SEPARATOR_STRING;
import static basic.zBasic.util.log.ILogStringFormatZZZ.iFACTOR_CONTROL02SEPARATOR_XML;
import static basic.zBasic.util.log.ILogStringFormatZZZ.iFACTOR_CONTROL03SEPARATOR_STRING;
import static basic.zBasic.util.log.ILogStringFormatZZZ.iFACTOR_CONTROL03SEPARATOR_XML;

import static basic.zBasic.util.log.ILogStringFormatZZZ.sSEPARATOR_MESSAGE_DEFAULT;
import static basic.zBasic.util.log.ILogStringFormatZZZ.sSEPARATOR_01_DEFAULT;
import static basic.zBasic.util.log.ILogStringFormatZZZ.sSEPARATOR_02_DEFAULT;
import static basic.zBasic.util.log.ILogStringFormatZZZ.sSEPARATOR_03_DEFAULT;

public class LogStringFormaterUtilZZZ implements IConstantZZZ{
	
	 /* Macht eine HashMap mit dem ienumLogString.getFactor() als Key 
	 * und dem String "sSeparator" als Wert.
	 * 
	 */
	public static HashMap<Integer, String> getHashMapLogStringSeparatorAll() throws ExceptionZZZ {
		HashMap<Integer, String> hmReturn = new HashMap<Integer,String>();
		main:{
			//HashMap automatisch aus dem Enum errechnen.
			
			//Problem: ClassCastException.
			//Merksatz (wichtig!) - Erstellt von ChatGPT
			//Ein Enum-Array kann niemals direkt zu einem Interface-Array gecastet werden,
			//auch wenn das Enum dieses Interface implementiert			
			//IEnumSetMappedLogStringFormatZZZ[] ienuma = EnumAvailableHelperZZZ.searchEnumMapped(LogStringFormaterZZZ.class, ILogStringFormatZZZ.sENUMNAME);
			
			//Aber das geht nicht, s. ChatGPT vom 20260110
			List<IEnumSetMappedLogStringFormatZZZ>listaEnum = EnumAvailableHelperZZZ.searchEnumMappedLogStringFormatList(LogStringFormaterZZZ.class, ILogStringFormatZZZ.sENUMNAME);			
			for(IEnumSetMappedLogStringFormatZZZ ienum : listaEnum) {
				int iArgumentType =  ienum.getArgumentType();
				if(iArgumentType==ILogStringFormatZZZ.iARG_CONTROL) {
					int iFaktor = ienum.getFactor();
					 switch(iFaktor){
				        case iFACTOR_CONTROLMESSAGESEPARATOR_STRING:
				        	hmReturn.put(iFaktor, sSEPARATOR_MESSAGE_DEFAULT);
				            break;
				        case iFACTOR_CONTROLMESSAGESEPARATOR_XML:
				        	hmReturn.put(iFaktor, sSEPARATOR_MESSAGE_DEFAULT); //das eigentlich noch in XML umwandeln
				            break;
				        case iFACTOR_CONTROL01SEPARATOR_STRING:
				        	hmReturn.put(iFaktor, sSEPARATOR_01_DEFAULT);
				            break;
				        case iFACTOR_CONTROL01SEPARATOR_XML:
				        	hmReturn.put(iFaktor, sSEPARATOR_01_DEFAULT); //das eigentlich noch in XML umwandeln
				            break;
				        case iFACTOR_CONTROL02SEPARATOR_STRING:
				        	hmReturn.put(iFaktor, sSEPARATOR_02_DEFAULT);
				            break;
				        case iFACTOR_CONTROL02SEPARATOR_XML:
				        	hmReturn.put(iFaktor, sSEPARATOR_02_DEFAULT); //das eigentlich noch in XML umwandeln
				            break;
				        case iFACTOR_CONTROL03SEPARATOR_STRING:
				        	hmReturn.put(iFaktor, sSEPARATOR_03_DEFAULT);
				            break;
				        case iFACTOR_CONTROL03SEPARATOR_XML:
				        	hmReturn.put(iFaktor, sSEPARATOR_03_DEFAULT); //das eigentlich noch in XML umwandeln
				            break;				       
				        default:
				            System.out.println("Faktor iFaktor="+iFaktor + " wird nicht behandelt");
				            break;
				        }					
				}				
			}				
		}//end main:
		return hmReturn;
	}
	
	
	 /* Macht eine HashMap mit dem ienumLogString.getFactor() als Key 
	 * und dem IEnumSetMappedZZZ "Abarbeitungstypen" als Wert.
	 */
	public static HashMap<Integer, IEnumSetMappedLogStringFormatZZZ> getHashMapFormatPositionAll() throws ExceptionZZZ {
		HashMap<Integer, IEnumSetMappedLogStringFormatZZZ> hmReturn = new HashMap<Integer,IEnumSetMappedLogStringFormatZZZ>();
		main:{
			//HashMap automatisch aus dem Enum errechnen.
			ArrayList<IEnumSetMappedLogStringFormatZZZ> ienuma = EnumAvailableHelperZZZ.searchEnumMappedLogStringFormatList(LogStringFormaterZZZ.class, ILogStringFormatZZZ.sENUMNAME);
			for(IEnumSetMappedZZZ ienum : ienuma) {
				IEnumSetMappedLogStringFormatZZZ ienumLogString = (IEnumSetMappedLogStringFormatZZZ) ienum;
				hmReturn.put(new Integer(ienumLogString.getFactor()), ienumLogString);
			}				
		}//end main:
		return hmReturn;
	}
	
	/* Analog zu
	 * @see basic.zBasic.util.log.ILogStringFormaterZZZ#getHashMapFormatPositionStringDefault()
	 * 
	 * Macht eine HashMap mit dem ienumLogString.getFactor() als Key 
	 * und der Formatanweisung des IEnumSetMappedZZZ "Abarbeitungstypen" als Wert.
	 */
	public static HashMap<Integer, String> getHashMapFormatPositionStringAll() throws ExceptionZZZ {
		HashMap<Integer, String> hmReturn = new HashMap<Integer,String>();
		main:{
			//HashMap automatisch aus dem Enum errechnen.
			ArrayList<IEnumSetMappedLogStringFormatZZZ> ienuma = EnumAvailableHelperZZZ.searchEnumMappedLogStringFormatList(LogStringFormaterZZZ.class, ILogStringFormatZZZ.sENUMNAME);
			for(IEnumSetMappedZZZ ienum : ienuma) {
				IEnumSetMappedLogStringFormatZZZ ienumLogString = (IEnumSetMappedLogStringFormatZZZ) ienum;
				hmReturn.put(new Integer(ienumLogString.getFactor()), ienumLogString.getFormat());
			}				
		}//end main:
		return hmReturn;
	}
	
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
		String sReturn = sLog;
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
			if(objStringJustifier==null) {
				sReturn = StringArrayZZZ.implode(saLine, StringZZZ.crlf());
				break main;
			}
			
			
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
		String sReturn = sLog;
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
			String sSeparatorMessageDefault = LogStringFormaterUtilZZZ.computeLinePartInLog_ControlCommentSeparator();
			
				
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
			if(objStringJustifier==null) {
				saReturn = saLogIn;
				break main;
			}
			String sSeparatorCommentDefault = ILogStringFormatZZZ.sSEPARATOR_MESSAGE_DEFAULT; //Also nicht ThreadIdSeparator, das bringt nur Probleme //;objStringJustifier.getPositionSeparator();
			
			//Nur der ILogStringFormatZZZ.sSEPARATOR_MESSAGE_DEFAULT reicht nicht, das muss der Formatierte String sein: "[A00/]# "
			String sSeparatorCommentDefaultFormated = LogStringFormaterUtilZZZ.computeLinePartInLog_ControlCommentSeparator();
			//Natürlich wg. dem explode ohne den sSeparatorMessageDefault
			String sSeparatorCommentDefaultFormatedLeft = StringZZZ.left(sSeparatorCommentDefaultFormated, sSeparatorCommentDefault);
			String sSeparatorCommentDefaultFormatedRight = StringZZZ.right(sSeparatorCommentDefaultFormated, sSeparatorCommentDefault);
			
			//Die Einträge aufteilen und trimmen.
			ArrayListZZZ<String>listasLogTrimmed = new ArrayListZZZ<String>();
			for(String sLog : saLogIn) {
				if(sLog!=null) {
					
					String[] saLogSub = StringZZZ.explode(sLog, sSeparatorCommentDefault);
					for(String sLogSub : saLogSub) {
						if(!StringZZZ.isEmpty(sLogSub) & !sLogSub.equalsIgnoreCase(sSeparatorCommentDefaultFormatedLeft) & !sLogSub.equalsIgnoreCase(sSeparatorCommentDefaultFormatedRight)){         //Leerwerte weglassen , Separator (links, rechts) weglassen
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
						String sReturnTemp = saLogTrimmed[0] + sSeparatorCommentDefault + saLogTrimmed[1];
						listasReturn.add(sReturnTemp);
						iIndex++;
					}else if(iIndex==1) {
						//Mache wg. der Zusammenfassung in 0 hier nix.
						iIndex++;
					}else if(iIndex>=2) {	
						if(!StringZZZ.isEmpty(sLog)) { //Damit keine Leeren Kommentarzeilen reinkommen in das Return-Array
							String sReturnTemp = sSeparatorCommentDefault + sLog;
							listasReturn.add(sReturnTemp);
							iIndex++;
						}
					}				
				}
			}else {
				//Zur Vorbereitung, falls irgendwann ein echter Kommentar kommt, einen Kommentar-Marker hintenangestellt
				if(sSeparatorCommentDefault.equals(ILogStringFormatZZZ.sSEPARATOR_MESSAGE_DEFAULT)) {
					if(!StringZZZ.isEmpty(saLogTrimmed[0])) { //Damit keine Leeren Kommentarzeilen reinkommen in das Return-Array					
						String sReturnTemp = saLogTrimmed[0] + sSeparatorCommentDefault; 				
						listasReturn.add(sReturnTemp);
					}
				}else {
					listasReturn.add(saLogTrimmed[0]);
				}
			}
						
			saReturn = listasReturn.toStringArray();
		}//end main:
		return saReturn;
	}
	
	public static String computeLinePartInLog_ControlCommentSeparator() throws ExceptionZZZ {
		IEnumSetMappedLogStringFormatZZZ ienumFormatLogString = LogStringFormaterUtilZZZ.getHashMapFormatPositionAll().get(new Integer(ILogStringFormatZZZ.iFACTOR_CONTROLMESSAGESEPARATOR_STRING));
		return LogStringFormaterUtilZZZ.computeLinePartInLog(ienumFormatLogString);
	}
	
	public static String computeLinePartInLog_ControlThreadIdSeparator() throws ExceptionZZZ {
		IEnumSetMappedLogStringFormatZZZ ienumFormatLogString = LogStringFormaterUtilZZZ.getHashMapFormatPositionAll().get(new Integer(ILogStringFormatZZZ.iFACTOR_CONTROL01SEPARATOR_STRING));
		return LogStringFormaterUtilZZZ.computeLinePartInLog(ienumFormatLogString);
	}
	
	/** Nur das formatierte SEPARATOR-ZEICHEN
	 * @param ienumFormatLogString
	 * @return
	 * @throws ExceptionZZZ
	 */
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
			ITagByTypeZZZ objTagMessageSeparator = null; String sMessageSeparatorTag = null;
			
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
//			//Ziel ist es eine unnoetigerweise erzeugte Leerzeile mit KommentarSeparator zu verhindern.
//			if(sLog==null)break main; //Ein explizit uebergebener Leerstring gilt aber.
			
			String sLog = "";
	        switch (ienumFormatLogString.getFactor()) {
	            case ILogStringFormatZZZ.iFACTOR_CONTROLMESSAGESEPARATOR_STRING:
	            	//ByControl?
	                //  sFormat = this.getHashMapFormatPositionString().get(
	                //        new Integer(ILogStringFormatZZZ.iFACTOR_CONTROLMESSAGESEPARATOR_STRING));	 
	            	  sFormat = LogStringFormaterUtilZZZ.getHashMapFormatPositionStringAll().get(new Integer(ILogStringFormatZZZ.iFACTOR_CONTROLMESSAGESEPARATOR_STRING));
	                  sMessageSeparator = String.format(sFormat, ILogStringFormatZZZ.sSEPARATOR_MESSAGE_DEFAULT);
	                  sMessageSeparator = sPrefixSeparator + sMessageSeparator + sLog + sPostfixSeparator;
	                  
	                  sReturn = sMessageSeparator;
	                break;
	                
	            case ILogStringFormatZZZ.iFACTOR_CONTROLMESSAGESEPARATOR_XML:
	            	//ByControl?
	               // sFormat = this.getHashMapFormatPositionString().get(
	                //        new Integer(ILogStringFormatZZZ.iFACTOR_CONTROLMESSAGESEPARATOR_XML));
	            	sFormat = LogStringFormaterUtilZZZ.getHashMapFormatPositionStringAll().get(new Integer(ILogStringFormatZZZ.iFACTOR_CONTROLMESSAGESEPARATOR_XML));
	                sMessageSeparator = String.format(sFormat, ILogStringFormatZZZ.sSEPARATOR_MESSAGE_DEFAULT);
	                sMessageSeparator = sPrefixSeparator + sMessageSeparator + sLog + sPostfixSeparator;
	                  

		        	objTagMessageSeparator = TagByTypeFactoryZZZ.createTagByName(TagByTypeFactoryZZZ.TAGTYPE.SEPARATORMESSAGE, sMessageSeparator);
		        	sMessageSeparatorTag = objTagMessageSeparator.getElementString();
		            
	                sReturn = sMessageSeparatorTag;
	                break;
	                
	            case ILogStringFormatZZZ.iFACTOR_CONTROL01SEPARATOR_STRING:
	            	//ByControl?
	                //  sFormat = this.getHashMapFormatPositionString().get(
	                //        new Integer(ILogStringFormatZZZ.iFACTOR_CONTROLMESSAGESEPARATOR_STRING));	 
	            	  sFormat = LogStringFormaterUtilZZZ.getHashMapFormatPositionStringAll().get(new Integer(ILogStringFormatZZZ.iFACTOR_CONTROL01SEPARATOR_STRING));
	                  sMessageSeparator = String.format(sFormat, ILogStringFormatZZZ.sSEPARATOR_01_DEFAULT);
	                  sMessageSeparator = sPrefixSeparator + sMessageSeparator + sLog + sPostfixSeparator;
	                  
	                  sReturn = sMessageSeparator;
	                break;
	           
	            case ILogStringFormatZZZ.iFACTOR_CONTROL01SEPARATOR_XML:
	            	//ByControl?
	               // sFormat = this.getHashMapFormatPositionString().get(
	                //        new Integer(ILogStringFormatZZZ.iFACTOR_CONTROLMESSAGESEPARATOR_XML));
	            	sFormat = LogStringFormaterUtilZZZ.getHashMapFormatPositionStringAll().get(new Integer(ILogStringFormatZZZ.iFACTOR_CONTROL01SEPARATOR_XML));
	                sMessageSeparator = String.format(sFormat, ILogStringFormatZZZ.sSEPARATOR_01_DEFAULT);
	                sMessageSeparator = sPrefixSeparator + sMessageSeparator + sLog + sPostfixSeparator;
	                  

		        	objTagMessageSeparator = TagByTypeFactoryZZZ.createTagByName(TagByTypeFactoryZZZ.TAGTYPE.SEPARATOR01, sMessageSeparator);
		        	sMessageSeparatorTag = objTagMessageSeparator.getElementString();
		            
	                sReturn = sMessageSeparatorTag;
	                break;
	            case ILogStringFormatZZZ.iFACTOR_CONTROL02SEPARATOR_STRING:
	            	//ByControl?
	                //  sFormat = this.getHashMapFormatPositionString().get(
	                //        new Integer(ILogStringFormatZZZ.iFACTOR_CONTROLMESSAGESEPARATOR_STRING));	 
	            	  sFormat = LogStringFormaterUtilZZZ.getHashMapFormatPositionStringAll().get(new Integer(ILogStringFormatZZZ.iFACTOR_CONTROL02SEPARATOR_STRING));
	                  sMessageSeparator = String.format(sFormat, ILogStringFormatZZZ.sSEPARATOR_02_DEFAULT);
	                  sMessageSeparator = sPrefixSeparator + sMessageSeparator + sLog + sPostfixSeparator;
	                  
	                  sReturn = sMessageSeparator;
	                break;
	           
	            case ILogStringFormatZZZ.iFACTOR_CONTROL02SEPARATOR_XML:
	            	//ByControl?
	               // sFormat = this.getHashMapFormatPositionString().get(
	                //        new Integer(ILogStringFormatZZZ.iFACTOR_CONTROLMESSAGESEPARATOR_XML));
	            	sFormat = LogStringFormaterUtilZZZ.getHashMapFormatPositionStringAll().get(new Integer(ILogStringFormatZZZ.iFACTOR_CONTROL02SEPARATOR_XML));
	                sMessageSeparator = String.format(sFormat, ILogStringFormatZZZ.sSEPARATOR_02_DEFAULT);
	                sMessageSeparator = sPrefixSeparator + sMessageSeparator + sLog + sPostfixSeparator;
	                  

		        	objTagMessageSeparator = TagByTypeFactoryZZZ.createTagByName(TagByTypeFactoryZZZ.TAGTYPE.SEPARATOR02, sMessageSeparator);
		        	sMessageSeparatorTag = objTagMessageSeparator.getElementString();
		            
	                sReturn = sMessageSeparatorTag;
	                break;
	            case ILogStringFormatZZZ.iFACTOR_CONTROL03SEPARATOR_STRING:
	            	//ByControl?
	                //  sFormat = this.getHashMapFormatPositionString().get(
	                //        new Integer(ILogStringFormatZZZ.iFACTOR_CONTROLMESSAGESEPARATOR_STRING));	 
	            	  sFormat = LogStringFormaterUtilZZZ.getHashMapFormatPositionStringAll().get(new Integer(ILogStringFormatZZZ.iFACTOR_CONTROL03SEPARATOR_STRING));
	                  sMessageSeparator = String.format(sFormat, ILogStringFormatZZZ.sSEPARATOR_03_DEFAULT);
	                  sMessageSeparator = sPrefixSeparator + sMessageSeparator + sLog + sPostfixSeparator;
	                  
	                  sReturn = sMessageSeparator;
	                break;
	           
	            case ILogStringFormatZZZ.iFACTOR_CONTROL03SEPARATOR_XML:
	            	//ByControl?
	               // sFormat = this.getHashMapFormatPositionString().get(
	                //        new Integer(ILogStringFormatZZZ.iFACTOR_CONTROLMESSAGESEPARATOR_XML));
	            	sFormat = LogStringFormaterUtilZZZ.getHashMapFormatPositionStringAll().get(new Integer(ILogStringFormatZZZ.iFACTOR_CONTROL03SEPARATOR_XML));
	                sMessageSeparator = String.format(sFormat, ILogStringFormatZZZ.sSEPARATOR_03_DEFAULT);
	                sMessageSeparator = sPrefixSeparator + sMessageSeparator + sLog + sPostfixSeparator;
	                  

		        	objTagMessageSeparator = TagByTypeFactoryZZZ.createTagByName(TagByTypeFactoryZZZ.TAGTYPE.SEPARATOR03, sMessageSeparator);
		        	sMessageSeparatorTag = objTagMessageSeparator.getElementString();
		            
	                sReturn = sMessageSeparatorTag;
	                break;
	            default:
	                System.out.println("AbstractLogStringZZZ.computeByControl_(..,..): Dieses Format ist nicht in den gültigen Formaten für einen objektbasierten LogString vorhanden. iFaktor=" + ienumFormatLogString.getFactor());
	                break;
	        }				          
		}//end main: 
		return sReturn;
	}
	
	/** Nur das unformatierte SEPARATOR-ZEICHEN
	 * @param ienumFormatLogString
	 * @return
	 * @throws ExceptionZZZ
	 */
	public static String getLinePartInLog(IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
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
			ITagByTypeZZZ objTagMessageSeparator = null; String sMessageSeparatorTag = null;
			
			String sPrefixSeparator = ienumFormatLogString.getPrefixSeparator();
			String sPostfixSeparator = ienumFormatLogString.getPostfixSeparator();

			
			String sLog = "";
	        switch (ienumFormatLogString.getFactor()) {
	            case ILogStringFormatZZZ.iFACTOR_CONTROLMESSAGESEPARATOR_STRING:	            	
            	  sReturn = LogStringFormaterUtilZZZ.getHashMapFormatPositionStringAll().get(new Integer(ILogStringFormatZZZ.iFACTOR_CONTROLMESSAGESEPARATOR_STRING));
            	  break;
	                
	            case ILogStringFormatZZZ.iFACTOR_CONTROLMESSAGESEPARATOR_XML:
	            	sMessageSeparator = LogStringFormaterUtilZZZ.getHashMapFormatPositionStringAll().get(new Integer(ILogStringFormatZZZ.iFACTOR_CONTROLMESSAGESEPARATOR_XML));
	                 
		        	objTagMessageSeparator = TagByTypeFactoryZZZ.createTagByName(TagByTypeFactoryZZZ.TAGTYPE.SEPARATORMESSAGE, sMessageSeparator);
		        	sMessageSeparatorTag = objTagMessageSeparator.getElementString();
		            
	                sReturn = sMessageSeparatorTag;
	                break;
	                
	            case ILogStringFormatZZZ.iFACTOR_CONTROL01SEPARATOR_STRING: 
	            	sReturn = LogStringFormaterUtilZZZ.getHashMapFormatPositionStringAll().get(new Integer(ILogStringFormatZZZ.iFACTOR_CONTROL01SEPARATOR_STRING));	                
	                break;
	           
	            case ILogStringFormatZZZ.iFACTOR_CONTROL01SEPARATOR_XML:
	            	//ByControl?
	               // sFormat = this.getHashMapFormatPositionString().get(
	                //        new Integer(ILogStringFormatZZZ.iFACTOR_CONTROLMESSAGESEPARATOR_XML));
	            	sFormat = LogStringFormaterUtilZZZ.getHashMapFormatPositionStringAll().get(new Integer(ILogStringFormatZZZ.iFACTOR_CONTROL01SEPARATOR_XML));
	                sMessageSeparator = String.format(sFormat, ILogStringFormatZZZ.sSEPARATOR_01_DEFAULT);
	                sMessageSeparator = sPrefixSeparator + sMessageSeparator + sLog + sPostfixSeparator;
	                  

		        	objTagMessageSeparator = TagByTypeFactoryZZZ.createTagByName(TagByTypeFactoryZZZ.TAGTYPE.SEPARATOR01, sMessageSeparator);
		        	sMessageSeparatorTag = objTagMessageSeparator.getElementString();
		            
	                sReturn = sMessageSeparatorTag;
	                break;
	            case ILogStringFormatZZZ.iFACTOR_CONTROL02SEPARATOR_STRING:
	            	//ByControl?
	                //  sFormat = this.getHashMapFormatPositionString().get(
	                //        new Integer(ILogStringFormatZZZ.iFACTOR_CONTROLMESSAGESEPARATOR_STRING));	 
	            	  sFormat = LogStringFormaterUtilZZZ.getHashMapFormatPositionStringAll().get(new Integer(ILogStringFormatZZZ.iFACTOR_CONTROL02SEPARATOR_STRING));
	                  sMessageSeparator = String.format(sFormat, ILogStringFormatZZZ.sSEPARATOR_02_DEFAULT);
	                  sMessageSeparator = sPrefixSeparator + sMessageSeparator + sLog + sPostfixSeparator;
	                  
	                  sReturn = sMessageSeparator;
	                break;
	           
	            case ILogStringFormatZZZ.iFACTOR_CONTROL02SEPARATOR_XML:
	            	//ByControl?
	               // sFormat = this.getHashMapFormatPositionString().get(
	                //        new Integer(ILogStringFormatZZZ.iFACTOR_CONTROLMESSAGESEPARATOR_XML));
	            	sFormat = LogStringFormaterUtilZZZ.getHashMapFormatPositionStringAll().get(new Integer(ILogStringFormatZZZ.iFACTOR_CONTROL02SEPARATOR_XML));
	                sMessageSeparator = String.format(sFormat, ILogStringFormatZZZ.sSEPARATOR_02_DEFAULT);
	                sMessageSeparator = sPrefixSeparator + sMessageSeparator + sLog + sPostfixSeparator;
	                  

		        	objTagMessageSeparator = TagByTypeFactoryZZZ.createTagByName(TagByTypeFactoryZZZ.TAGTYPE.SEPARATOR02, sMessageSeparator);
		        	sMessageSeparatorTag = objTagMessageSeparator.getElementString();
		            
	                sReturn = sMessageSeparatorTag;
	                break;
	            case ILogStringFormatZZZ.iFACTOR_CONTROL03SEPARATOR_STRING:
	            	//ByControl?
	                //  sFormat = this.getHashMapFormatPositionString().get(
	                //        new Integer(ILogStringFormatZZZ.iFACTOR_CONTROLMESSAGESEPARATOR_STRING));	 
	            	  sFormat = LogStringFormaterUtilZZZ.getHashMapFormatPositionStringAll().get(new Integer(ILogStringFormatZZZ.iFACTOR_CONTROL03SEPARATOR_STRING));
	                  sMessageSeparator = String.format(sFormat, ILogStringFormatZZZ.sSEPARATOR_03_DEFAULT);
	                  sMessageSeparator = sPrefixSeparator + sMessageSeparator + sLog + sPostfixSeparator;
	                  
	                  sReturn = sMessageSeparator;
	                break;
	           
	            case ILogStringFormatZZZ.iFACTOR_CONTROL03SEPARATOR_XML:
	            	//ByControl?
	               // sFormat = this.getHashMapFormatPositionString().get(
	                //        new Integer(ILogStringFormatZZZ.iFACTOR_CONTROLMESSAGESEPARATOR_XML));
	            	sFormat = LogStringFormaterUtilZZZ.getHashMapFormatPositionStringAll().get(new Integer(ILogStringFormatZZZ.iFACTOR_CONTROL03SEPARATOR_XML));
	                sMessageSeparator = String.format(sFormat, ILogStringFormatZZZ.sSEPARATOR_03_DEFAULT);
	                sMessageSeparator = sPrefixSeparator + sMessageSeparator + sLog + sPostfixSeparator;
	                  

		        	objTagMessageSeparator = TagByTypeFactoryZZZ.createTagByName(TagByTypeFactoryZZZ.TAGTYPE.SEPARATOR03, sMessageSeparator);
		        	sMessageSeparatorTag = objTagMessageSeparator.getElementString();
		            
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
