package basic.zBasic.util.log;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.abstractList.ArrayListZZZ;
import basic.zBasic.util.abstractList.ArrayListUniqueZZZ;
import basic.zBasic.util.abstractList.ArrayListUtilZZZ;
import basic.zBasic.util.datatype.string.IStringJustifierZZZ;
import basic.zBasic.util.datatype.string.StringArrayZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;

public class LogStringFormaterUtilZZZ {
	
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
			

//			//Splitte sLog auf, ggfs. Hier vorhandene Kommentarzeilen
//			String[]saLog = StringZZZ.explode(sLog, ILogStringFormatZZZ.sSEPARATOR_MESSAGE_DEFAULT);
//			
//			//Trimme nun ggf. Leerzeichen weg
//			String[] saLogTrimmed = StringArrayZZZ.trim(saLog);
//			
//			//Ergänze nun die getrimmten String mit einem neuen Kommentar voranstellen
//			int iIndex=-1;
//			String[]saLogCommented = new String[saLogTrimmed.length];
//			for(String sLogTrimmed : saLogTrimmed ) {
//				iIndex++;
//				if(iIndex==0) {
//					//vergiss nicht den sLine String an der ersten Stelle voranzustellen (das ist der add-Moment)
//					saLogCommented[0] = sLine + sLogTrimmed; 
//				}else {
//					if(!StringZZZ.endsWith(sLogTrimmed, ILogStringFormatZZZ.sSEPARATOR_MESSAGE_DEFAULT)) {
//						saLogCommented[iIndex] = ILogStringFormatZZZ.sSEPARATOR_MESSAGE_DEFAULT + sLogTrimmed;
//					}else {
//						saLogCommented[iIndex] = sLogTrimmed;
//					}
//				}				
//			}
			
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
				
			//Splitte sLog auf, ggfs. Hier vorhandene Kommentarzeilen
			String[]saLog = StringZZZ.explode(sLog, ILogStringFormatZZZ.sSEPARATOR_MESSAGE_DEFAULT);
			
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
						if(sLine.endsWith(ILogStringFormatZZZ.sSEPARATOR_MESSAGE_DEFAULT)) {
							saReturn[iIndex] = sLine + sLogTrimmed;
						}else {
							saReturn[iIndex] = sLine + ILogStringFormatZZZ.sSEPARATOR_MESSAGE_DEFAULT + sLogTrimmed;
						}
					}else {					
						saReturn[iIndex] = ILogStringFormatZZZ.sSEPARATOR_MESSAGE_DEFAULT + sLogTrimmed;					
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
				
			//Die Einträge aufteilen und trimmen.
			ArrayListZZZ<String>listasLogTrimmed = new ArrayListZZZ<String>();
			for(String sLog : saLogIn) {
				if(sLog!=null) {
					String[] saLogSub = StringZZZ.explode(sLog, ILogStringFormatZZZ.sSEPARATOR_MESSAGE_DEFAULT);
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
						String sReturnTemp = saLogTrimmed[0] + ILogStringFormatZZZ.sSEPARATOR_MESSAGE_DEFAULT + saLogTrimmed[1];
						listasReturn.add(sReturnTemp);
						iIndex++;
					}else if(iIndex==1) {
						//Mache wg. der Zusammenfassung in 0 hier nix.
						iIndex++;
					}else if(iIndex>=2) {	
						if(!StringZZZ.isEmpty(sLog)) { //Damit keine Leeren Kommentarzeilen reinkommen in das Return-Array
							String sReturnTemp = ILogStringFormatZZZ.sSEPARATOR_MESSAGE_DEFAULT + sLog;
							listasReturn.add(sReturnTemp);
							iIndex++;
						}
					}				
				}
			}else {
				if(!StringZZZ.isEmpty(saLogTrimmed[0])) { //Damit keine Leeren Kommentarzeilen reinkommen in das Return-Array
					//Zur Vorbereitung, falls irgendwann ein echter Kommentar kommt, einen Kommentar-Marker hintenangestellt
					String sReturnTemp = saLogTrimmed[0] + ILogStringFormatZZZ.sSEPARATOR_MESSAGE_DEFAULT; 				
					listasReturn.add(sReturnTemp);
				}
			}
						
			saReturn = listasReturn.toStringArray();
		}//end main:
		return saReturn;
	}
	
}
