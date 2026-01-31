package basic.zBasic.util.log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

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
import basic.zBasic.util.datatype.enums.EnumMappedLogStringFormatAvailableHelperZZZ;
import basic.zBasic.util.datatype.string.IStringJustifierZZZ;
import basic.zBasic.util.datatype.string.StringArrayZZZ;
import basic.zBasic.util.datatype.string.SeparatorMessageStringJustifierZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.datatype.xml.XmlUtilZZZ;
import basic.zBasic.util.log.ILogStringFormatZZZ.LOGSTRINGFORMAT;
import basic.zBasic.xml.tagtype.ITagByTypeZZZ;
import basic.zBasic.xml.tagtype.TagByTypeFactoryZZZ;

import static basic.zBasic.util.log.IEnumSetMappedLogStringFormatZZZ.sENUMNAME;

import static basic.zBasic.util.log.ILogStringFormatZZZ.iFACTOR_LINENEXT_STRING;

import static basic.zBasic.util.log.ILogStringFormatZZZ.iFACTOR_CONTROLMESSAGESEPARATOR_STRING;
import static basic.zBasic.util.log.ILogStringFormatZZZ.iFACTOR_CONTROLMESSAGESEPARATOR_XML;
import static basic.zBasic.util.log.ILogStringFormatZZZ.iFACTOR_CONTROL01SEPARATOR_STRING;
import static basic.zBasic.util.log.ILogStringFormatZZZ.iFACTOR_CONTROL01SEPARATOR_XML;
import static basic.zBasic.util.log.ILogStringFormatZZZ.iFACTOR_CONTROL02SEPARATOR_STRING;
import static basic.zBasic.util.log.ILogStringFormatZZZ.iFACTOR_CONTROL02SEPARATOR_XML;
import static basic.zBasic.util.log.ILogStringFormatZZZ.iFACTOR_CONTROL03SEPARATOR_STRING;
import static basic.zBasic.util.log.ILogStringFormatZZZ.iFACTOR_CONTROL03SEPARATOR_XML;
import static basic.zBasic.util.log.ILogStringFormatZZZ.iFACTOR_CONTROL04SEPARATOR_STRING;
import static basic.zBasic.util.log.ILogStringFormatZZZ.iFACTOR_CONTROL04SEPARATOR_XML;

import static basic.zBasic.util.log.ILogStringFormatZZZ.sSEPARATOR_MESSAGE_DEFAULT;
import static basic.zBasic.util.log.ILogStringFormatZZZ.sSEPARATOR_01_DEFAULT;
import static basic.zBasic.util.log.ILogStringFormatZZZ.sSEPARATOR_02_DEFAULT;
import static basic.zBasic.util.log.ILogStringFormatZZZ.sSEPARATOR_03_DEFAULT;
import static basic.zBasic.util.log.ILogStringFormatZZZ.sSEPARATOR_04_DEFAULT;


public class LogStringFormaterUtilZZZ implements IConstantZZZ{
	
	 /* Macht eine HashMap mit dem ienumLogString.getFactor() als Key 
	 * und dem String "sSeparator" als Wert.
	 * 
	 */

	public static HashMap<Integer, String> getHashMapLogStringSeparatorAll() throws ExceptionZZZ {
		return LogStringFormaterUtilZZZ.getHashMapLogStringSeparatorAll_(false);
	}
	
	public static HashMap<Integer, String> getHashMapLogStringSeparatorAll(boolean bLineFilter) throws ExceptionZZZ {
		return LogStringFormaterUtilZZZ.getHashMapLogStringSeparatorAll_(bLineFilter);
	}
	
	public static HashMap<Integer, String> getHashMapLogStringSeparatorAllForLine() throws ExceptionZZZ {
		return LogStringFormaterUtilZZZ.getHashMapLogStringSeparatorAll_(true);
	}
	
	
	private static HashMap<Integer, String> getHashMapLogStringSeparatorAll_(boolean bLineFilter) throws ExceptionZZZ {
		HashMap<Integer, String> hmReturn = new HashMap<Integer,String>();
		main:{
			//HashMap automatisch aus dem Enum errechnen.
			
			//Problem: ClassCastException.
			//Merksatz (wichtig!) - Erstellt von ChatGPT
			//Ein Enum-Array kann niemals direkt zu einem Interface-Array gecastet werden,
			//auch wenn das Enum dieses Interface implementiert
			
			//Aber das geht nicht, s. ChatGPT vom 20260110
			//IEnumSetMappedLogStringFormatZZZ[] ienuma = EnumAvailableHelperZZZ.searchEnumMapped(LogStringFormaterZZZ.class, ILogStringFormatZZZ.sENUMNAME);
			IEnumSetMappedLogStringFormatZZZ[] ienuma = EnumMappedLogStringFormatAvailableHelperZZZ.searchEnumMappedArray(LogStringFormaterZZZ.class, sENUMNAME);
			for(IEnumSetMappedLogStringFormatZZZ ienum : ienuma) {
			

			//List<IEnumSetMappedLogStringFormatZZZ>listaEnum = EnumMappedLogStringFormatAvailableHelperZZZ.searchEnumMappedLogStringFormatList(LogStringFormaterZZZ.class, ILogStringFormatZZZ.sENUMNAME);			
			//for(IEnumSetMappedLogStringFormatZZZ ienum : listaEnum) {
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
				        case iFACTOR_CONTROL04SEPARATOR_STRING:
				        	hmReturn.put(iFaktor, sSEPARATOR_04_DEFAULT);
				            break;
				        case iFACTOR_CONTROL04SEPARATOR_XML:
				        	hmReturn.put(iFaktor, sSEPARATOR_04_DEFAULT); //das eigentlich noch in XML umwandeln
				            break;
				        case iFACTOR_LINENEXT_STRING:
				        	if(!bLineFilter) {
				        		//Der Format Manager teilt anhand dieses Kennzeichens die Formatanweisungen auf die verschiedenenen Zeile auf
				        		hmReturn.put(iFaktor,  StringZZZ.crlf());				        		
				        	}
				        	break;
				        default:
				            System.out.println("LogStringFormaterUtilZZZ.getHashMapLogStringSeparatorAll_(): Faktor iFaktor="+iFaktor + " wird nicht behandelt");
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
			ArrayList<IEnumSetMappedLogStringFormatZZZ> ienuma = EnumMappedLogStringFormatAvailableHelperZZZ.searchEnumMappedList(LogStringFormaterZZZ.class, sENUMNAME);
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
			ArrayList<IEnumSetMappedLogStringFormatZZZ> ienuma = EnumMappedLogStringFormatAvailableHelperZZZ.searchEnumMappedList(LogStringFormaterZZZ.class, sENUMNAME);
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
			sReturn = justifyInfoPart_(objStringJustifier, false, saLine);			
		}//end main:
		return sReturn;
	}
	
	public static String justifyInfoPart(IStringJustifierZZZ objStringJustifier, String[] saLog) throws ExceptionZZZ{
		String sReturn = null;
		main:{
			if(saLog==null) break main;						
			sReturn = justifyInfoPart_(objStringJustifier, false, saLog);			
		}//end main:
		return sReturn;		
	}
	
	
	public static String justifyInfoPart(IStringJustifierZZZ objStringJustifier, boolean bMergeFirst2Lines, String sLog) throws ExceptionZZZ{
		String sReturn = sLog;
		main:{
			if(StringZZZ.isEmpty(sLog)) break main;
								
			//Teile die Zeile ggfs. am Zeilenumbruch auf
			String[] saLine = StringZZZ.explode(sLog, StringZZZ.crlf());
			sReturn = justifyInfoPart_(objStringJustifier, bMergeFirst2Lines, saLine);			
		}//end main:
		return sReturn;
	}
	
	public static String justifyInfoPart(IStringJustifierZZZ objStringJustifier, boolean bMergeFirst2Lines, String[] saLine) throws ExceptionZZZ{
		return justifyInfoPart_(objStringJustifier, bMergeFirst2Lines, saLine);
	}
	
	private static String justifyInfoPart_(IStringJustifierZZZ objStringJustifier, boolean bMergeFirst2Lines, String[] saLine) throws ExceptionZZZ{
		String sReturn = null;
		main:{
			if(saLine==null) break main;
			
			ArrayListZZZ<String> listasLineZ = ArrayListUtilZZZ.toArrayList(saLine);
			
			ArrayListZZZ<String> listasReturn = justifyInfoPartArrayList_(objStringJustifier, bMergeFirst2Lines, listasLineZ);
			sReturn = ArrayListUtilZZZ.implode(listasReturn, StringZZZ.crlf());
		}//end main:
		return sReturn;
	}
	
//	private static String justifyInfoPart_(IStringJustifierZZZ objStringJustifier, boolean bMergeFirst2Lines, String[] saLine) throws ExceptionZZZ{
//		String sReturn = null;
//		main:{
//			if(StringArrayZZZ.isEmpty(saLine)) break main;
//			if(objStringJustifier==null) {
//				sReturn = StringArrayZZZ.implode(saLine, StringZZZ.crlf());
//				break main;
//			}
//			
//			
//			//In den übergebenen Zeilen koennten weitere Kommentartrenner stecken.
//			//Daher jede Zeile splitten und danach das neue "Standardisierte Array" weiterverarbeiten
//			//Ggfs. auch die erste Zeile und die 2te Zeile zusammenfassen.
//			String[] saLineNormed = normStringForJustify_(objStringJustifier,bMergeFirst2Lines, saLine);
//			
//			//Bei 0 wuerde nix buendig gemacht, darum die Grenze ggfs. explizit auf die Position des ersten SepartorStrings setzen
//			int iIndexMayIncrease = objStringJustifier.getInfoPartBoundLeftBehind2use(saLineNormed[0]); 
//			objStringJustifier.setInfoPartBoundLeftBehindIncreased(iIndexMayIncrease);
//			
//			//Nun das Standardisierte Array anpassen			
//				if(saLineNormed.length>=2) {
//					//Nun über mehrere Zeilen das machen!!! Einmal hin und wieder zurueck
//					String[] saLineReversed1 = ArrayUtilZZZ.reverse(saLineNormed);
//					ArrayListUniqueZZZ<String>listasLineReversedJustified1 = new ArrayListUniqueZZZ<String>();
//					for(String sLine : saLineReversed1) {
//						String sLineJustified = objStringJustifier.justifyInfoPart(sLine);
//						listasLineReversedJustified1.add(sLineJustified);
//					}
//					
//					ArrayListUniqueZZZ<String>listasLineReversed2 = (ArrayListUniqueZZZ<String>) ArrayListUtilZZZ.reverse(listasLineReversedJustified1);
//					ArrayListUniqueZZZ<String>listasLineReversedJustified2 = new ArrayListUniqueZZZ<String>();
//					for(String sLine : listasLineReversed2) {
//						String sLineJustified = objStringJustifier.justifyInfoPart(sLine);
//						listasLineReversedJustified2.add(sLineJustified);
//					}
//					
//					//Die Zeilen so verbinden, das sie mit einem "System.println" ausgegeben werden können.
//					for(String sLineJustified : listasLineReversedJustified2) {
//						if(sReturn==null){
//							sReturn = sLineJustified;
//						}else {					
//							sReturn = sReturn + StringZZZ.crlf() + sLineJustified;
//						}
//					}	
//				}else {
//					String sLine = saLineNormed[0];
//					if(sLine!=null) {
//						sReturn = objStringJustifier.justifyInfoPart(sLine);					
//					}
//				}			
//		}//end main:
//		return sReturn;
//	}
	
	
	
	public static String justifyInfoPart(IStringJustifierZZZ objStringJustifier, boolean bMergeFirst2Lines, ArrayListZZZ<String> listasLine) throws ExceptionZZZ{
		String sReturn = null;
		main:{			
			if(listasLine==null) break main;
			if(listasLine.isEmpty()) break main;			
			
			ArrayListZZZ<String> listasReturn = justifyInfoPartArrayList_(objStringJustifier, bMergeFirst2Lines, listasLine);
			sReturn = ArrayListUtilZZZ.implode(listasReturn, StringZZZ.crlf());
		}//end  main:
		return sReturn;
	}

	public static ArrayListZZZ<String> justifyInfoPartArrayList(IStringJustifierZZZ objStringJustifier, ArrayListZZZ<String>listasLine) throws ExceptionZZZ{
		return justifyInfoPartArrayList_(objStringJustifier, true, listasLine);
	}
	
	public static ArrayListZZZ<String> justifyInfoPartArrayList(IStringJustifierZZZ objStringJustifier, boolean bMergeFirst2Lines, ArrayListZZZ<String>listasLine) throws ExceptionZZZ{
		return justifyInfoPartArrayList_(objStringJustifier, bMergeFirst2Lines, listasLine);
	}
	
	private static ArrayListZZZ<String> justifyInfoPartArrayList_(IStringJustifierZZZ objStringJustifier, boolean bMergeFirst2Lines, ArrayListZZZ<String>listasLine) throws ExceptionZZZ{
		ArrayListZZZ<String>listasReturn=null;;
		main:{	
			if(listasLine==null) break main;
			
			if(objStringJustifier==null | listasLine.isEmpty()) {
				listasReturn=listasLine;
				break main;
			}else {
				listasReturn = new ArrayListZZZ<String>();
			}
			
			
			//In den übergebenen Zeilen koennten weitere Kommentartrenner stecken.
			//Daher jede Zeile splitten und danach das neue "Standardisierte Array" weiterverarbeiten
			//Ggfs. auch die erste Zeile und die 2te Zeile zusammenfassen.
			ArrayListZZZ<String>listasLineNormed = normStringForJustifyArrayList_(objStringJustifier, bMergeFirst2Lines, listasLine);
			
			//Bei 0 wuerde nix buendig gemacht, darum die Grenze ggfs. explizit auf die Position des ersten SeparatorStrings setzen
			int iIndexMayIncrease = objStringJustifier.getInfoPartBoundLeftBehind2use(listasLineNormed.get(0)); 
			objStringJustifier.setInfoPartBoundLeftBehindIncreased(iIndexMayIncrease);
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//Nun das Standardisierte Array anpassen
			if(listasLineNormed.size()>=2) {
				//Nun über mehrere Zeilen das machen!!! Einmal hin und wieder zurueck
				ArrayListZZZ<String> listasLineReversed1 =  ArrayListUtilZZZ.reverse(listasLineNormed);
				ArrayListZZZ<String>listasLineReversedJustified1 = new ArrayListZZZ<String>();
				for(String sLine : listasLineReversed1) {
					String sLineJustified = objStringJustifier.justifyInfoPart(sLine);
					listasLineReversedJustified1.add(sLineJustified);
				}
				
				ArrayListZZZ<String>listasLineReversed2 =  ArrayListUtilZZZ.reverse(listasLineReversedJustified1);
				ArrayListZZZ<String>listasLineReversedJustified2 = new ArrayListZZZ<String>();
				for(String sLine : listasLineReversed2) {
					String sLineJustified = objStringJustifier.justifyInfoPart(sLine);
					listasLineReversedJustified2.add(sLineJustified);
				}
				
				listasReturn = listasLineReversedJustified2;
			}else {				
				String sLine = listasLineNormed.get(0);
				if(sLine!=null) {
					String sLineJustified = objStringJustifier.justifyInfoPart(sLine);
					listasReturn.add(sLineJustified);
				}
			}		
		}//end main:
		return listasReturn;
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
			
			String[]saLogCommented = normStringForJustify_(objStringJustifier, sLine, sLog);
			
			sReturn = justifyInfoPart(objStringJustifier, false, saLogCommented);			
		}//end  main:
		return sReturn;
	}
	
	
	private static String[] normStringForJustify_(IStringJustifierZZZ objStringJustifier,String sLine, String sLog) throws ExceptionZZZ {
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
			
				
			//Splitte sLog auf, ggfs. Hier vorhandene Kommentarzeilen. Auf jeden Fall wird ein abschliessendes Kommentarzeichen zum Leerstring am Ende.
			//String[]saLog = StringZZZ.explode(sLog, sSeparatorMessageDefault);
			
			//Trimme nun ggf. Leerzeichen weg
			//reicht nicht String[]saLogTrimmed = StringArrayZZZ.trim(saLog);
			String[]saLineTrimmed = normStringForJustify_(objStringJustifier, false, sLine);
			
			//Ergänze nun die getrimmten String mit einem neuen Kommentar-Marker voranstellen
			int iIndex=-1;
			saReturn = new String[saLineTrimmed.length];
			for(String sLineTrimmed : saLineTrimmed ) {
				if(!StringZZZ.isEmpty(sLineTrimmed)){					
					iIndex++;
					if(iIndex==saLineTrimmed.length-1) { //Das Log an der letzten Zeile anhaengen
						//Beim Anhaengen nur den Kommentartrenner hinzufuegen, wenn er noch nicht im Log ist.
						//Merke: Der Kommentartrenner kann ja schon vorher durch andere Log Berechnungen reingekommen sein.
						//if(sLog.contains(sSeparatorMessageDefault)) { //TODOGOON: Wenn ueberall nur noch der FormatKommentar verwendet wird, sonst...:  
						if(sLog.contains(sSeparatorMessageDefault) || sLog.contains(ILogStringFormatZZZ.sSEPARATOR_MESSAGE_DEFAULT)){
							if(sLineTrimmed.endsWith(sSeparatorMessageDefault)){
								sLineTrimmed = StringZZZ.stripRight(sLineTrimmed, sSeparatorMessageDefault);
								saReturn[iIndex] = sLineTrimmed + sLog;
							}else if (sLineTrimmed.endsWith(ILogStringFormatZZZ.sSEPARATOR_MESSAGE_DEFAULT)) {
								sLineTrimmed = StringZZZ.stripRight(sLineTrimmed, ILogStringFormatZZZ.sSEPARATOR_MESSAGE_DEFAULT);
								saReturn[iIndex] = sLineTrimmed + sLog;
							}else {
								saReturn[iIndex] = sLineTrimmed + sLog;
							}
						}else {
							if(sLineTrimmed.endsWith(sSeparatorMessageDefault) || sLineTrimmed.endsWith(ILogStringFormatZZZ.sSEPARATOR_MESSAGE_DEFAULT)) {
								saReturn[iIndex] = sLineTrimmed + sLog;
							}else {
								saReturn[iIndex] = sLineTrimmed + sSeparatorMessageDefault + sLog;
							}
						}						
					}else {					
						saReturn[iIndex] = sLineTrimmed;					
					}		
				}
			}
		}//end main:
		return saReturn;
	}
	
	
	
	
	public static String[] normStringForJustify(IStringJustifierZZZ objStringJustifier,String[] saLogIn) throws ExceptionZZZ {
		return normStringForJustify_(objStringJustifier, false, saLogIn);
	}
	
	
	private static String[] normStringForJustify_(IStringJustifierZZZ objStringJustifier, String sLog) throws ExceptionZZZ {
		return normStringForJustify_(objStringJustifier, false, sLog);
	}
	
	private static String[] normStringForJustify_(IStringJustifierZZZ objStringJustifier, boolean bMergeFirst2Lines, String... saLogIn) throws ExceptionZZZ {
		String[] saReturn=null;
		main:{			
			if(ArrayUtilZZZ.isEmpty(saLogIn)) break main;
			if(objStringJustifier==null) {
				saReturn = saLogIn;
				break main;
			}
			
			ArrayListZZZ<String> listasLogZ = ArrayListUtilZZZ.toArrayList(saLogIn);
			ArrayListZZZ<String> listasReturn = normStringForJustifyArrayList_(objStringJustifier, bMergeFirst2Lines, listasLogZ);
								
			saReturn = listasReturn.toStringArray();
		}//end main:
		return saReturn;
	}
	
	private static ArrayListZZZ<String> normStringForJustifyArrayList_(IStringJustifierZZZ objStringJustifier, ArrayListZZZ<String>listasLog) throws ExceptionZZZ {
		return normStringForJustifyArrayList_(objStringJustifier, false, listasLog);
	}
	
	private static ArrayListZZZ<String> normStringForJustifyArrayList_(IStringJustifierZZZ objStringJustifier, boolean bMergeFirst2Lines, ArrayListZZZ<String>listasLog) throws ExceptionZZZ {
		ArrayListZZZ<String>listasReturn=null;;
		main:{	
			if(listasLog==null) break main;
			
			if(objStringJustifier==null | listasLog.isEmpty()) {
				listasReturn=listasLog;
				break main;
			}else {
				listasReturn = new ArrayListZZZ<String>();
			}
											
			String sSeparatorCommentDefault = ILogStringFormatZZZ.sSEPARATOR_MESSAGE_DEFAULT; //Also nicht ThreadIdSeparator, das bringt nur Probleme //;objStringJustifier.getPositionSeparator();
		
			//Nur der ILogStringFormatZZZ.sSEPARATOR_MESSAGE_DEFAULT reicht nicht, das muss der Formatierte String sein: "[A00/]# "
			String sSeparatorCommentDefaultFormated = LogStringFormaterUtilZZZ.computeLinePartInLog_ControlCommentSeparator();
			//Natürlich wg. dem explode ohne den sSeparatorMessageDefault
			//String sSeparatorCommentDefaultFormatedLeft = StringZZZ.left(sSeparatorCommentDefaultFormated, sSeparatorCommentDefault);
			//String sSeparatorCommentDefaultFormatedRight = StringZZZ.right(sSeparatorCommentDefaultFormated, sSeparatorCommentDefault);
			
			//Die Einträge aufteilen und trimmen. Damit macht ein ein vorheriges "justifien" wieder rueckgaengig
			ArrayListZZZ<String>listasLogTrimmed = new ArrayListZZZ<String>();
			int iIndex=-1; int iIndexSub; String sReturnSub=null;
			for(String sLog : listasLog) {
				if(sLog!=null) {
					iIndex++;
					if(StringZZZ.contains(sLog, sSeparatorCommentDefault)) {
						String[] saLogSub = StringZZZ.explode(sLog, sSeparatorCommentDefault);
						iIndexSub=-1;
						sReturnSub="";
						for(String sLogSub : saLogSub) {	
							iIndexSub++;
							sLogSub = sLogSub.trim(); //trimmen
							if(!StringZZZ.isEmpty(sLogSub)) {
								if(iIndexSub==0) {	
									if(bMergeFirst2Lines && listasLog.size()>= 2) {
										sReturnSub = sLogSub;                          //Dann wird später zusammengefasst und dabei der Trenner gesetzt
									}else {
										sReturnSub = sLogSub+sSeparatorCommentDefault; // + wieder den Trenner;
									}
								}else {																			
									sReturnSub = sReturnSub + sLogSub; // + wieder den hinteren Kommentarteil
								}																		
							}//isEmpty(sLogSub)
						}//end for	
						listasLogTrimmed.add(sReturnSub);
					}else {
						sLog = sLog.trim();
						listasLogTrimmed.add(sLog);
					}//end if constains
				}//end if sLog!=null	
			}//end for
			
			if(bMergeFirst2Lines) {
				//Merke: Ziel ist das Zusammenfassen der 1. + 2. Zeile
				//       Dabei wird davon ausgegangen, das in der 1. Zeile so etwas wie die Codepostion steht
				//       und in der 2. Zeile ff dann die Kommentare.
				iIndex=-1;
				if(listasLogTrimmed.size()>=2) {
					//Ergänze nun die getrimmten String mit einem neuen Kommentar-Marker voranstellen
					for(String sLog : listasLogTrimmed ) {
						iIndex++;
						if(iIndex==0) {
							//Zusammenfassen der Zeilen 1. + 2. . Dabei gehe ich davon aus, das im 1. Kommentar so etwas wie die CodePosition steht.					
							String sReturnTemp = listasLogTrimmed.get(0) + sSeparatorCommentDefault + listasLogTrimmed.get(1);						
							listasReturn.add(sReturnTemp);
							
						}else if(iIndex==1) {
							//Mache wg. der Zusammenfassung in 0 hier nix.
						}else if(iIndex>=2) {	
							listasReturn.add(sLog);						
						}				
					}
				}else {
					//mache nix weiter
					listasReturn = listasLogTrimmed;
				}
			}else {	
				//mache nix weiter
				listasReturn = listasLogTrimmed;
			}
			
			//Zur Vorbereitung, wenn noch kein falls irgendwann ein echter Kommentar kommt, einen Kommentar-Marker hintenangestellt
//			int iIndexLast = listasReturn.size()-1;
//			if(!listasReturn.get(iIndexLast).contains(sSeparatorCommentDefault)) {
//				String stemp = listasReturn.get(iIndexLast) + sSeparatorCommentDefault;
//				listasReturn.replace(iIndexLast, stemp);
//			}
		}//end main:
		return listasReturn;
	}
	
	
	//###############################################################################################
	//### Berechne alle moeglichen Kommentar-, etc. Trenner. 
	//### - auch um auszuschliessen, das eine Zeile nur aus diesem beseht.
	public static String[] computeLinePartInLog_ControlSeparatorAny() throws ExceptionZZZ{
		String[] saReturn = null;
		main:{
			String stemp=null; 
			ArrayList<String>listasSeparator=new ArrayList<String>();
			
			//+++++++++++++++++++++
			stemp = computeLinePartInLog_ControlCommentSeparator();
			listasSeparator.add(stemp);
			stemp = computeLinePartInLog_ControlCommentSeparatorXml();
			listasSeparator.add(stemp);
			
			stemp = computeLinePartInLog_Control01Separator();
			listasSeparator.add(stemp);
			stemp = computeLinePartInLog_Control01SeparatorXml();
			listasSeparator.add(stemp);
			
			stemp = computeLinePartInLog_Control02Separator();
			listasSeparator.add(stemp);
			stemp = computeLinePartInLog_Control02SeparatorXml();
			listasSeparator.add(stemp);
			
			stemp = computeLinePartInLog_Control03Separator();
			listasSeparator.add(stemp);
			stemp = computeLinePartInLog_Control03SeparatorXml();
			listasSeparator.add(stemp);
			
			stemp = computeLinePartInLog_Control04Separator();
			listasSeparator.add(stemp);
			stemp = computeLinePartInLog_Control04SeparatorXml();
			listasSeparator.add(stemp);
			
			//+++++++++++++++++++++++++++++
			saReturn = ArrayListUtilZZZ.toStringArray(listasSeparator);
		}//end main:
		return saReturn;
	}
	
	public static String computeLinePartInLog_ControlCommentSeparator() throws ExceptionZZZ {
		IEnumSetMappedLogStringFormatZZZ ienumFormatLogString = LogStringFormaterUtilZZZ.getHashMapFormatPositionAll().get(new Integer(ILogStringFormatZZZ.iFACTOR_CONTROLMESSAGESEPARATOR_STRING));
		return LogStringFormaterUtilZZZ.computeLinePartInLog(ienumFormatLogString);
	}
	public static String computeLinePartInLog_ControlCommentSeparatorXml() throws ExceptionZZZ {
		IEnumSetMappedLogStringFormatZZZ ienumFormatLogString = LogStringFormaterUtilZZZ.getHashMapFormatPositionAll().get(new Integer(ILogStringFormatZZZ.iFACTOR_CONTROLMESSAGESEPARATOR_XML));
		return LogStringFormaterUtilZZZ.computeLinePartInLog(ienumFormatLogString);
	}
	
	public static String computeLinePartInLog_Control01Separator() throws ExceptionZZZ {
		IEnumSetMappedLogStringFormatZZZ ienumFormatLogString = LogStringFormaterUtilZZZ.getHashMapFormatPositionAll().get(new Integer(ILogStringFormatZZZ.iFACTOR_CONTROL01SEPARATOR_STRING));
		return LogStringFormaterUtilZZZ.computeLinePartInLog(ienumFormatLogString);
	}
	public static String computeLinePartInLog_Control01SeparatorXml() throws ExceptionZZZ {
		IEnumSetMappedLogStringFormatZZZ ienumFormatLogString = LogStringFormaterUtilZZZ.getHashMapFormatPositionAll().get(new Integer(ILogStringFormatZZZ.iFACTOR_CONTROL01SEPARATOR_XML));
		return LogStringFormaterUtilZZZ.computeLinePartInLog(ienumFormatLogString);
	}
	
	public static String computeLinePartInLog_Control02Separator() throws ExceptionZZZ {
		IEnumSetMappedLogStringFormatZZZ ienumFormatLogString = LogStringFormaterUtilZZZ.getHashMapFormatPositionAll().get(new Integer(ILogStringFormatZZZ.iFACTOR_CONTROL02SEPARATOR_STRING));
		return LogStringFormaterUtilZZZ.computeLinePartInLog(ienumFormatLogString);
	}
	public static String computeLinePartInLog_Control02SeparatorXml() throws ExceptionZZZ {
		IEnumSetMappedLogStringFormatZZZ ienumFormatLogString = LogStringFormaterUtilZZZ.getHashMapFormatPositionAll().get(new Integer(ILogStringFormatZZZ.iFACTOR_CONTROL02SEPARATOR_XML));
		return LogStringFormaterUtilZZZ.computeLinePartInLog(ienumFormatLogString);
	}
	
	public static String computeLinePartInLog_Control03Separator() throws ExceptionZZZ {
		IEnumSetMappedLogStringFormatZZZ ienumFormatLogString = LogStringFormaterUtilZZZ.getHashMapFormatPositionAll().get(new Integer(ILogStringFormatZZZ.iFACTOR_CONTROL03SEPARATOR_STRING));
		return LogStringFormaterUtilZZZ.computeLinePartInLog(ienumFormatLogString);
	}
	public static String computeLinePartInLog_Control03SeparatorXml() throws ExceptionZZZ {
		IEnumSetMappedLogStringFormatZZZ ienumFormatLogString = LogStringFormaterUtilZZZ.getHashMapFormatPositionAll().get(new Integer(ILogStringFormatZZZ.iFACTOR_CONTROL03SEPARATOR_XML));
		return LogStringFormaterUtilZZZ.computeLinePartInLog(ienumFormatLogString);
	}
	
	public static String computeLinePartInLog_Control04Separator() throws ExceptionZZZ {
		IEnumSetMappedLogStringFormatZZZ ienumFormatLogString = LogStringFormaterUtilZZZ.getHashMapFormatPositionAll().get(new Integer(ILogStringFormatZZZ.iFACTOR_CONTROL04SEPARATOR_STRING));
		return LogStringFormaterUtilZZZ.computeLinePartInLog(ienumFormatLogString);
	}
	public static String computeLinePartInLog_Control04SeparatorXml() throws ExceptionZZZ {
		IEnumSetMappedLogStringFormatZZZ ienumFormatLogString = LogStringFormaterUtilZZZ.getHashMapFormatPositionAll().get(new Integer(ILogStringFormatZZZ.iFACTOR_CONTROL04SEPARATOR_XML));
		return LogStringFormaterUtilZZZ.computeLinePartInLog(ienumFormatLogString);
	}
	
	//###############################################################################################
	
	
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
	            case ILogStringFormatZZZ.iFACTOR_CONTROL04SEPARATOR_STRING:
	            	//ByControl?
	                //  sFormat = this.getHashMapFormatPositionString().get(
	                //        new Integer(ILogStringFormatZZZ.iFACTOR_CONTROLMESSAGESEPARATOR_STRING));	 
	            	  sFormat = LogStringFormaterUtilZZZ.getHashMapFormatPositionStringAll().get(new Integer(ILogStringFormatZZZ.iFACTOR_CONTROL03SEPARATOR_STRING));
	                  sMessageSeparator = String.format(sFormat, ILogStringFormatZZZ.sSEPARATOR_04_DEFAULT);
	                  sMessageSeparator = sPrefixSeparator + sMessageSeparator + sLog + sPostfixSeparator;
	                  
	                  sReturn = sMessageSeparator;
	                break;
	           
	            case ILogStringFormatZZZ.iFACTOR_CONTROL04SEPARATOR_XML:
	            	//ByControl?
	               // sFormat = this.getHashMapFormatPositionString().get(
	                //        new Integer(ILogStringFormatZZZ.iFACTOR_CONTROLMESSAGESEPARATOR_XML));
	            	sFormat = LogStringFormaterUtilZZZ.getHashMapFormatPositionStringAll().get(new Integer(ILogStringFormatZZZ.iFACTOR_CONTROL03SEPARATOR_XML));
	                sMessageSeparator = String.format(sFormat, ILogStringFormatZZZ.sSEPARATOR_04_DEFAULT);
	                sMessageSeparator = sPrefixSeparator + sMessageSeparator + sLog + sPostfixSeparator;
	                  

		        	objTagMessageSeparator = TagByTypeFactoryZZZ.createTagByName(TagByTypeFactoryZZZ.TAGTYPE.SEPARATOR04, sMessageSeparator);
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
	

	public static ArrayListZZZ<IEnumSetMappedLogStringFormatZZZ> getArrayListLogStringSeparatorFrom(LinkedHashMap<IEnumSetMappedLogStringFormatZZZ, String> hm) {
		ArrayListZZZ<IEnumSetMappedLogStringFormatZZZ> listaReturn = null;
		main:{
			if(hm==null)break main;
			listaReturn = new ArrayListZZZ<IEnumSetMappedLogStringFormatZZZ>();
			
			Set<IEnumSetMappedLogStringFormatZZZ> set = hm.keySet();
			Iterator<IEnumSetMappedLogStringFormatZZZ> it = set.iterator();
			while(it.hasNext()) {
				IEnumSetMappedLogStringFormatZZZ objFormatSet = it.next();
				if(objFormatSet.getArgumentType()==ILogStringFormatZZZ.iARG_CONTROL) {
					listaReturn.add(objFormatSet);
				}
			}
			
		}//end main:
		return listaReturn;
	}

	
}
