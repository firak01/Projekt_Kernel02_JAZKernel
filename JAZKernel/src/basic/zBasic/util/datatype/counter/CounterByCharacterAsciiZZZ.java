package basic.zBasic.util.datatype.counter;

import java.util.ArrayList;

import basic.zBasic.IConstantZZZ;


/**
 *  
 * 
 * @author Fritz Lindhauer, 03.03.2019, 12:49:22
 * 
 */
public class CounterByCharacterAsciiZZZ implements IConstantZZZ {
	public static int iALPHABET_POSITION_MIN=1; //Merke: Als Alphabetgrundlage wird hier der ASCII Satz gesehen
	public static int iALPHABET_POSITION_MAX=26;
	
	/*
	 Eine noch einfacherer Lösung ist: 	 https://forum.chip.de/discussion/832166/in-while-schleife-alphabet-hochzaehlen

		//Characters können wie Integer ebenfalls hochgezählt werden,somit entfällt die Suche nach dem Ascii-Wert...so müsste es in etwa dann aussehen 
		public static void main (String args[]) {
		
		   char anfang='a';
		   char ende='z';
		
		   String AbisZ="";
		
		   while (anfang<=ende) {
		   
		      AbisZ=AbisZ+anfang;
		      anfang++;
		
		    }
	 */
	
/**Behandlung der Werte nach der "Serial"-Strategie. Dies ist Default.
	Z.B. 27 ==> ZA "Serielle" oder beim der "Multiplikator Strategie" AA. Multiplikator - Stategie bedeutet: Den Modulo Wert entsprechend häufig darstellen.
	 * @param i
	 * @return
	 * @author Fritz Lindhauer, 04.03.2019, 09:03:52
	 */
	public static String getCharForNumber(int i){
		return CounterByCharacterAsciiZZZ.getCharForNumber_(i, false, false);
	}
	
	/**Behandlung der Werte nach der "Serial"-Strategie. Dies ist Default.
	Z.B. 27 ==> ZA "Serielle" oder beim der "Multiplikator Strategie" AA. Multiplikator - Stategie bedeutet: Den Modulo Wert entsprechend häufig darstellen.
	 * @param i
	 * @param bLowercase
	 * @return
	 * @author Fritz Lindhauer, 04.03.2019, 09:03:56
	 */
	public static String getCharForNumber(int i, boolean bLowercase){
		return CounterByCharacterAsciiZZZ.getCharForNumber_(i, bLowercase, false);		
	}
	
	/**Behandlung der Werte nach der "Serial"-Strategie. Dies ist Default.
	Z.B. 27 ==> ZA "Serielle" oder beim der "Multiplikator Strategie" AA. Multiplikator - Stategie bedeutet: Den Modulo Wert entsprechend häufig darstellen.
	 * @param i
	 * @return
	 * @author Fritz Lindhauer, 04.03.2019, 09:03:52
	 */
	public static String getCharMultipleForNumber(int i){
		return CounterByCharacterAsciiZZZ.getCharForNumber_(i, false, true);
	}
	
	/**Behandlung der Werte nach der "Serial"-Strategie. Dies ist Default.
	Z.B. 27 ==> ZA "Serielle" oder beim der "Multiplikator Strategie" AA. Multiplikator - Stategie bedeutet: Den Modulo Wert entsprechend häufig darstellen.
	 * @param i
	 * @param bLowercase
	 * @return
	 * @author Fritz Lindhauer, 04.03.2019, 09:03:56
	 */
	public static String getCharMultipleForNumber(int i, boolean bLowercase){
		return CounterByCharacterAsciiZZZ.getCharForNumber_(i, bLowercase, true);		
	}
	
	private static String getCharForNumber_(int i, boolean bLowercase, boolean bMultipleStrategy){
		String sReturn = null;		
		main:{
			
		   //Ermittle den "Teiler" und den Rest, Also Modulo - Operation
			int iDiv = Math.abs(i / CounterByCharacterAsciiZZZ.iALPHABET_POSITION_MAX ); //durch abs wird also intern in ein Integer umgewandetl.... nicht nur das Weglassen des ggfs. negativen Vorzeichens.
			int iMod = i % CounterByCharacterAsciiZZZ.iALPHABET_POSITION_MAX;

			
			if(!bMultipleStrategy){ //"SERIAL STRATEGY"
				ArrayList<String>listas=new ArrayList<String>();			
				for(int icount = 1; icount <= iDiv; icount++){
					String stemp = CounterByCharacterAsciiZZZ.getCharForPositionInAlphabet(CounterByCharacterAsciiZZZ.iALPHABET_POSITION_MAX,bLowercase);
					listas.add(stemp);
				}
				if(iMod>=1){
					String stemp = CounterByCharacterAsciiZZZ.getCharForPositionInAlphabet(iMod,bLowercase);
					listas.add(stemp);
				}
				
				//Zusammenfassen der Werte: Serial Strategie
				for(int icount=1; icount <= listas.size(); icount++){
					String sPosition = listas.get(icount-1);
					if(sReturn==null){
						sReturn=sPosition;
					}else{
						sReturn+=sPosition;
					}
				}
			}else{ //"MULTIPLE STATEGY"
				if(iMod==0 && iDiv ==0) break main;
				
				//Ermittle den "Modulo"-Wert und davon das Zeichen
				String sCharacter=null;
				if(iMod>=1){
					sCharacter = CounterByCharacterAsciiZZZ.getCharForPositionInAlphabet(iMod,bLowercase);	
					sReturn = sCharacter;
				}else if(iMod==0){
					sCharacter = CounterByCharacterAsciiZZZ.getCharForPositionInAlphabet(CounterByCharacterAsciiZZZ.iALPHABET_POSITION_MAX,bLowercase);
					sReturn = "";
				}
				
				
				//Zusammenfassen der Werte: Multiple Strategie
				for(int icount=1; icount <= iDiv; icount++){					
						sReturn+=sCharacter;
				}
				
			}
			
		}//end main:
		return sReturn;		
	}
	
	
	public static String getCharForPositionInAlphabet(int i) {
			return i > (CounterByCharacterAsciiZZZ.iALPHABET_POSITION_MIN-1) && i < (CounterByCharacterAsciiZZZ.iALPHABET_POSITION_MAX+1) ? String.valueOf((char)(i + 'A' - 1)) : null;
		}
		
	public static String getCharForPositionInAlphabet(int i, boolean bLowercase) {
		if(bLowercase){
			return i > (CounterByCharacterAsciiZZZ.iALPHABET_POSITION_MIN-1) && i < (CounterByCharacterAsciiZZZ.iALPHABET_POSITION_MAX+1) ? String.valueOf((char)(i + 'a' - 1)) : null; //Bei Kleinbuchstaben sind das andere ASCII Werte. Aber mit 'Zeichen'/Characters kann man wie mit Integer rechnen
		}else{
			return CounterByCharacterAsciiZZZ.getCharForPositionInAlphabet(i);
		}
	}
}
