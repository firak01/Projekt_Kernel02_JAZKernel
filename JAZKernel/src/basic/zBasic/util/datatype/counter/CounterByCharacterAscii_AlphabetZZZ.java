package basic.zBasic.util.datatype.counter;

import java.util.ArrayList;

import basic.zBasic.IConstantZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.datatype.character.CharZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;


/**
 *  
 * 
 * @author Fritz Lindhauer, 03.03.2019, 12:49:22
 * 
 */
public class CounterByCharacterAscii_AlphabetZZZ extends AbstractCounterByCharacterAsciiZZZ implements ICounterStringAlphanumericZZZ{
	public static int iALPHABET_POSITION_MIN=1; //Merke: Als Alphabetgrundlage wird hier der ASCII Satz gesehen
	public static int iALPHABET_POSITION_MAX=26;
	
	public static String sREGEX_CHARACTERS="[a-zA-Z]";
	
	private boolean bLowercase = false;
		
/**Behandlung der Werte nach der "Serial"-Strategie. Dies ist Default.
	Z.B. 27 ==> ZA "Serielle" oder beim der "Multiplikator Strategie" AA. Multiplikator - Stategie bedeutet: Den Modulo Wert entsprechend häufig darstellen.
	 * @param i
	 * @return
	 * @author Fritz Lindhauer, 04.03.2019, 09:03:52
	 */
	public static String getStringAlphabetForNumber(int i){
		ICounterStrategyAlphanumericZZZ objCounterStrategy = new CounterStrategyAlphabetSerialZZZ();
		return CounterByCharacterAscii_AlphabetZZZ.getStringAlphabetForNumber_(i, false, objCounterStrategy);
	}
	
	/**Behandlung der Werte nach der "Serial"-Strategie. Dies ist Default.
	Z.B. 27 ==> ZA "Serielle" oder beim der "Multiplikator Strategie" AA. Multiplikator - Stategie bedeutet: Den Modulo Wert entsprechend häufig darstellen.
	 * @param i
	 * @param bLowercase
	 * @return
	 * @author Fritz Lindhauer, 04.03.2019, 09:03:56
	 */
	public static String getStringAlphabetForNumber(int i, boolean bLowercase){
		ICounterStrategyAlphanumericZZZ objCounterStrategy = new CounterStrategyAlphabetSerialZZZ();
		return CounterByCharacterAscii_AlphabetZZZ.getStringAlphabetForNumber_(i, bLowercase, objCounterStrategy);		
	}
	
	/**Behandlung der Werte nach der "Multiple"-Strategie.
	Z.B. 27 ==> ZA "Serielle" oder beim der "Multiplikator Strategie" AA. Multiplikator - Stategie bedeutet: Den Modulo Wert entsprechend häufig darstellen.
	 * @param i
	 * @return
	 * @author Fritz Lindhauer, 04.03.2019, 09:03:52
	 */
	public static String getStringAlphabetMultipleForNumber(int i){
		ICounterStrategyAlphanumericZZZ objCounterStrategy = new CounterStrategyAlphabetMultipleZZZ();
		return CounterByCharacterAscii_AlphabetZZZ.getStringAlphabetForNumber_(i, false, objCounterStrategy);
	}
	
	/**Behandlung der Werte nach der "Multiple"-Strategie. 
	Z.B. 27 ==> ZA "Serielle" oder beim der "Multiplikator Strategie" AA. Multiplikator - Stategie bedeutet: Den Modulo Wert entsprechend häufig darstellen.
	 * @param i
	 * @param bLowercase
	 * @return
	 * @author Fritz Lindhauer, 04.03.2019, 09:03:56
	 */
	public static String getStringAlphabetMultipleForNumber(int i, boolean bLowercase){
		ICounterStrategyAlphanumericZZZ objCounterStrategy = new CounterStrategyAlphabetMultipleZZZ();
		return CounterByCharacterAscii_AlphabetZZZ.getStringAlphabetForNumber_(i, bLowercase, objCounterStrategy);		
	}
	
	private static String getStringAlphabetForNumber_(int iNumber, boolean bLowercase, ICounterStrategyAlphanumericZZZ objCounterStrategy){
		String sReturn = null;		
		main:{
				if(iNumber<=0) break main;			
				if(objCounterStrategy==null){
					objCounterStrategy = new CounterStrategyAlphabetMultipleZZZ();
//					ExceptionZZZ ez = new ExceptionZZZ("AlphanumericCounter: Kein CounterStrategy-Objekt übergeben.", iERROR_PARAMETER_VALUE, CounterByCharacterAscii_AlphanumericZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
//					throw ez;
				}
				
				sReturn = objCounterStrategy.computeStringForNumber(iNumber, bLowercase);

		}//end main:
		return sReturn;		
	}
	
	//####################
	
	/** Grundlage für einen Konstruktor, bei dem ein String als Ausgangszähler übergeben wird.
	 * @param c
	 * @return
	 * @author Fritz Lindhauer, 16.03.2019, 08:46:42
	 */
	public static int getPositionInAlphabetForChar(char c){
		int iReturn = -1;
		main:{
			//1. Prüfen, ist das überhaupt ein erlaubtes Zeichen?
			boolean bValid = CounterByCharacterAscii_AlphabetZZZ.isValidCharacter(c);
			if(!bValid) break main;
			
			boolean bIsLowercase = CharZZZ.isLowercase(c);
					
			//2. Umrechnung auf den ASCII-Integer-Wert
			int i = c;
			
			//3. Umrechnung dieses Wertes auf die Position im Alphabet
			//    Berücksichtige dabei, ob es ein Klein-oder Großbuchstabe ist.
			if(bIsLowercase){
				//System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": Lowercase");
				//String.valueOf((char)(i + 'a' - 1)
				iReturn = i - 'a' + 1;//Umgerechnet aus der Methode getCharForPositiionInAlphabet
			}else{
				//System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": Uppercase");				
				//String.valueOf((char)(i + 'a' - 1)
				iReturn = i -'A'+1; //Umgerechnet aus der Methode getCharForPositiionInAlphabet
			}
			
			
		}
		return iReturn;
	}
	
	
	
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

//++++++++++
public static boolean isValidCharacter(char c){
	boolean bReturn = false;
	main:{
		String s = Character.toString(c);
		bReturn = CounterByCharacterAscii_AlphabetZZZ.isValidCharacter(s);
	}
	return bReturn;
}

public static boolean isValidCharacter(String s){
	boolean bReturn = false;
	main:{
		if(StringZZZ.isEmpty(s)) break main;
		
		String sRegex = CounterByCharacterAscii_AlphabetZZZ.sREGEX_CHARACTERS;
		
		bReturn = true;
		char[] ca = s.toCharArray();
		for(int icount = 0; icount<= ca.length-1; icount++){
			char ctemp = ca[icount];
			String stemp = Character.toString(ctemp);
			if(!stemp.matches(sRegex)){
				bReturn = false;
				break main;
			}
		}
	}
	return bReturn;
}


// ++++ Aus Interface
@Override
public boolean isLowercase() {
	return this.bLowercase;
}

@Override
public void isLowercase(boolean bValue) {
	this.bLowercase = bValue;
}





	
	
}
