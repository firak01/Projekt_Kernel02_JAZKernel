package basic.zBasic.util.datatype.integer;

import basic.zBasic.util.datatype.character.CharZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.math.MathZZZ;

public class IntegerZZZ {
	public static Integer parseAbsolutFromRight(String sToParse) {
		Integer intReturn = null;
		main:{
			if(StringZZZ.isEmpty(sToParse)) break main;
			
			//int i = string.startsWith("-") ? 1 : 0;
			//Die Zeichen durchgehen, von rechts.
			int iMax = sToParse.length();
			int i=iMax;
			
		    for (; i >=1; i--) {
		       if (!Character.isDigit(sToParse.charAt(i-1))) {
		          break;
		        }
		    }
		    String substring = sToParse.substring(i,iMax);		        		    
		    intReturn = new Integer(substring);
		}//end main:
		return intReturn;
	}
	
	public static Integer parseIntFromRight(String sToParse) {
		Integer intReturn = null;
		main:{
			if(StringZZZ.isEmpty(sToParse)) break main;
			
			//Die Zeichen durchgehen, von rechts.
			int iMax = sToParse.length();
			int i=iMax;
			
		    for (; i >=1; i--) {
		       if (!Character.isDigit(sToParse.charAt(i-1))) {
		          break;
		        }
		    }
		    
		    //Nun kann aber noch ein Vorzeichen vorhanden sein.
		    String sNumericPrefix = sToParse.substring(i-1,i);
		    if(CharZZZ.isNumericPrefix(sNumericPrefix.toCharArray()[0])) {
		    	i = i-1;
		    }
		    
		    String substring = sToParse.substring(i,iMax);		        		    
		    intReturn = new Integer(substring);
		}//end main:
		return intReturn;
	}
}
