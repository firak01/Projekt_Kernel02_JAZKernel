package basic.zBasic.util.log;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
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
			
			
			if(saLine.length>=2) {
				//Nun über mehrere Zeilen das machen!!! Einmal hin und wieder zurueck
				String[] saLineReversed1 = ArrayUtilZZZ.reverse(saLine);
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
				String sLine = saLine[0];
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
}
