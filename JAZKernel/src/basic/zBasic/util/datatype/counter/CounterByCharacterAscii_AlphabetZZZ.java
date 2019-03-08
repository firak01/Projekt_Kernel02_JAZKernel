package basic.zBasic.util.datatype.counter;

import java.util.ArrayList;

import basic.zBasic.IConstantZZZ;


/**
 *  
 * 
 * @author Fritz Lindhauer, 03.03.2019, 12:49:22
 * 
 */
public class CounterByCharacterAscii_AlphabetZZZ extends AbstractCounterByCharacterAsciiZZZ{
	public static int iALPHABET_POSITION_MIN=1; //Merke: Als Alphabetgrundlage wird hier der ASCII Satz gesehen
	public static int iALPHABET_POSITION_MAX=26;
		
/**Behandlung der Werte nach der "Serial"-Strategie. Dies ist Default.
	Z.B. 27 ==> ZA "Serielle" oder beim der "Multiplikator Strategie" AA. Multiplikator - Stategie bedeutet: Den Modulo Wert entsprechend häufig darstellen.
	 * @param i
	 * @return
	 * @author Fritz Lindhauer, 04.03.2019, 09:03:52
	 */
	public static String getStringAlphabetForNumber(int i){
		return CounterByCharacterAscii_AlphabetZZZ.getStringAlphabetForNumber_(i, false, false);
	}
	
	/**Behandlung der Werte nach der "Serial"-Strategie. Dies ist Default.
	Z.B. 27 ==> ZA "Serielle" oder beim der "Multiplikator Strategie" AA. Multiplikator - Stategie bedeutet: Den Modulo Wert entsprechend häufig darstellen.
	 * @param i
	 * @param bLowercase
	 * @return
	 * @author Fritz Lindhauer, 04.03.2019, 09:03:56
	 */
	public static String getStringAlphabetForNumber(int i, boolean bLowercase){
		return CounterByCharacterAscii_AlphabetZZZ.getStringAlphabetForNumber_(i, bLowercase, false);		
	}
	
	/**Behandlung der Werte nach der "Multiple"-Strategie.
	Z.B. 27 ==> ZA "Serielle" oder beim der "Multiplikator Strategie" AA. Multiplikator - Stategie bedeutet: Den Modulo Wert entsprechend häufig darstellen.
	 * @param i
	 * @return
	 * @author Fritz Lindhauer, 04.03.2019, 09:03:52
	 */
	public static String getStringAlphabetMultipleForNumber(int i){
		return CounterByCharacterAscii_AlphabetZZZ.getStringAlphabetForNumber_(i, false, true);
	}
	
	/**Behandlung der Werte nach der "Multiple"-Strategie. 
	Z.B. 27 ==> ZA "Serielle" oder beim der "Multiplikator Strategie" AA. Multiplikator - Stategie bedeutet: Den Modulo Wert entsprechend häufig darstellen.
	 * @param i
	 * @param bLowercase
	 * @return
	 * @author Fritz Lindhauer, 04.03.2019, 09:03:56
	 */
	public static String getStringAlphabetMultipleForNumber(int i, boolean bLowercase){
		return CounterByCharacterAscii_AlphabetZZZ.getStringAlphabetForNumber_(i, bLowercase, true);		
	}
	
	private static String getStringAlphabetForNumber_(int i, boolean bLowercase, boolean bMultipleStrategy){
		String sReturn = null;		
		main:{
			
		   //Ermittle den "Teiler" und den Rest, Also Modulo - Operation
			int iDiv = Math.abs(i / CounterByCharacterAscii_AlphabetZZZ.iALPHABET_POSITION_MAX ); //durch abs wird also intern in ein Integer umgewandetl.... nicht nur das Weglassen des ggfs. negativen Vorzeichens.
			int iMod = i % CounterByCharacterAscii_AlphabetZZZ.iALPHABET_POSITION_MAX;

			
			if(!bMultipleStrategy){ //"SERIAL STRATEGY"
				ArrayList<String>listas=new ArrayList<String>();			
				for(int icount = 1; icount <= iDiv; icount++){
					String stemp = CounterByCharacterAscii_AlphabetZZZ.getCharForPositionInAlphabet(CounterByCharacterAscii_AlphabetZZZ.iALPHABET_POSITION_MAX,bLowercase);
					listas.add(stemp);
				}
				if(iMod>=1){
					String stemp = CounterByCharacterAscii_AlphabetZZZ.getCharForPositionInAlphabet(iMod,bLowercase);
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
					sCharacter = CounterByCharacterAscii_AlphabetZZZ.getCharForPositionInAlphabet(iMod,bLowercase);	
					sReturn = sCharacter;
				}else if(iMod==0){
					sCharacter = CounterByCharacterAscii_AlphabetZZZ.getCharForPositionInAlphabet(CounterByCharacterAscii_AlphabetZZZ.iALPHABET_POSITION_MAX,bLowercase);
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
	
	//####################
	
	public static String getCharForPositionInAlphabet(int i) {
			return i > (CounterByCharacterAscii_AlphabetZZZ.iALPHABET_POSITION_MIN-1) && i < (CounterByCharacterAscii_AlphabetZZZ.iALPHABET_POSITION_MAX+1) ? String.valueOf((char)(i + 'A' - 1)) : null;
		}
		
	public static String getCharForPositionInAlphabet(int i, boolean bLowercase) {
		if(bLowercase){
			return i > (CounterByCharacterAscii_AlphabetZZZ.iALPHABET_POSITION_MIN-1) && i < (CounterByCharacterAscii_AlphabetZZZ.iALPHABET_POSITION_MAX+1) ? String.valueOf((char)(i + 'a' - 1)) : null; //Bei Kleinbuchstaben sind das andere ASCII Werte. Aber mit 'Zeichen'/Characters kann man wie mit Integer rechnen
		}else{
			return CounterByCharacterAscii_AlphabetZZZ.getCharForPositionInAlphabet(i);
		}
	}
	
//### Aus Interface
protected void setCurrent(String sCurrent){
	//Da der Wert nicht gespeichert wird, muss nun aus dem String die Zahl berechnet werden.
	//TODO GOON 20190308
}

@Override
public String getStringFor(int iValue) {
	String sCurrent = CounterByCharacterAscii_AlphabetZZZ.getStringAlphabetForNumber(iValue);
	return sCurrent;
}





	
	
}
