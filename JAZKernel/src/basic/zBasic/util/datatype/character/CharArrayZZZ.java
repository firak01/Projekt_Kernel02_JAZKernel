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
	
	
	
	/**
	 * @param ca
	 * @param iPositionLastIn -1 = nimm alle Zeichen, dito wenn > Anzahl Zeichen.
	 * @return
	 * @author Fritz Lindhauer, 05.11.2022, 11:01:48
	 */
	public static String toString(char[]ca, int iPositionLastIn) {
		String sReturn = null;
		main:{
			if(CharArrayZZZ.isEmpty(ca))break main;
			
			int iPositionLast;
			if(iPositionLastIn>ca.length) {
				iPositionLast=ca.length;
			}else if(iPositionLastIn<0){
				break main;
			}else {
				iPositionLast=iPositionLastIn;
			}
			
			sReturn = "";
			for(int iIndex=0;iIndex <= iPositionLast; iIndex++) {
				char c = ca[iIndex];
				sReturn = sReturn + c; 
			}			
		}//end main:
		return sReturn;
	}
}
