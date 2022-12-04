package basic.zBasic.util.datatype.character;

public class CharArrayZZZ {
	public static boolean contains(char[]ca, char cToFind) {
		boolean bReturn = false;
		main:{
			if(ca==null)break main;
			if(CharZZZ.isEmpty(cToFind)) {
				for(char c : ca) {
					if(CharZZZ.isEmpty(c)) {
						bReturn = true;
						break main;
					}
				}
			}else {				
				for(char c : ca) {
					if(c == cToFind) {
						bReturn = true;
						break main;
					}
				}
			}								
		}//end main:
		return bReturn;
	}
	
	public static char[] from(int[]ia) {
		char[]caReturn=null;
		main:{
			if(ia==null)break main;
			caReturn = new char[ia.length];
			for(int i=0;i<ia.length;i++) {
				char c = (char) ia[i];
				caReturn[i] = c;
			}
		}
		return caReturn;
	}
	
	public static boolean isEmpty(char[]ca) {
		boolean bReturn = true;
		main:{
			if(ca==null)break main;
			for(char c : ca) {
				if(!CharZZZ.isEmpty(c)) {
					bReturn = false;
					break main;
				}
			}
		}//end main:
		return bReturn;		
	}
	
	
	public static String toString(int[]ia) {
		String sReturn = null;
		main:{
			if(ia==null) break main;
			
			char[]ca = CharArrayZZZ.from(ia);
			sReturn = CharArrayZZZ.toString(ca);
		}
		return sReturn;
	}
	
	public static String toString(char[]ca) {
		String sReturn = null;
		main:{
			if(CharArrayZZZ.isEmpty(ca)) break main;
			
			sReturn = CharArrayZZZ.toString(ca, ca.length);
		}
		return sReturn;		
	}
	
	
	/**
	 * @param ca
	 * @param iPositionLastIn wenn > Anzahl Zeichen, < Anzahl Zeichen, >bbruch.
	 * @return
	 * @author Fritz Lindhauer, 05.11.2022, 11:01:48
	 */
	public static String toString(char[]ca, int iPositionLastIn) {
		String sReturn = null;
		main:{
			if(CharArrayZZZ.isEmpty(ca))break main;
			
			int iPositionLast;
			if(iPositionLastIn>ca.length) {
				break main;
			}else if(iPositionLastIn<0){
				break main;
			}else {
				iPositionLast=iPositionLastIn;
			}
			
			sReturn = "";
			for(int iIndex=0;iIndex < iPositionLast; iIndex++) {
				char c = ca[iIndex];
				sReturn = sReturn + c; 
			}			
		}//end main:
		return sReturn;
	}
}
