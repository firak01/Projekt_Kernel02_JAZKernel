package basic.zBasic.util.datatype.counter;

import java.util.ArrayList;

import basic.zBasic.ExceptionZZZ;
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
public class CounterByCharacterAscii_AlphabetZZZ<T extends ICounterStrategyAlphanumericZZZ> extends AbstractCounterByCharacterAsciiAlphanumericZZZ{
	private ICounterStrategyAlphanumericZZZ objCounterStrategy;
	
	public static int iPOSITION_MIN=1; //Merke: Als Alphabetgrundlage wird hier der ASCII Satz gesehen
	public static int iPOSITION_MAX=26;
	
	public static String sREGEX_CHARACTERS="[a-zA-Z]";

	
	public CounterByCharacterAscii_AlphabetZZZ(){
		super();
		this.setValueCurrent(CounterByCharacterAscii_AlphabetZZZ.iPOSITION_MIN);
	}
	public CounterByCharacterAscii_AlphabetZZZ(ICounterStrategyAlphanumericZZZ objCounterStrategy){
		super(objCounterStrategy);
		this.setValueCurrent(CounterByCharacterAscii_AlphabetZZZ.iPOSITION_MIN);
	}
	public CounterByCharacterAscii_AlphabetZZZ(int iStartingValue, ICounterStrategyAlphanumericZZZ objCounterStrategy){
		super(iStartingValue, objCounterStrategy);
	}
	public CounterByCharacterAscii_AlphabetZZZ(String sStartingValue) throws ExceptionZZZ{
		super(sStartingValue);
	}
	public CounterByCharacterAscii_AlphabetZZZ(int iStartingValue) throws ExceptionZZZ{
		super(iStartingValue);
	}
	public CounterByCharacterAscii_AlphabetZZZ(String sStartingValue, ICounterStrategyAlphanumericZZZ objCounterStrategy) throws ExceptionZZZ{
		super(sStartingValue, objCounterStrategy);		
	}
	
	
	
		
/**Behandlung der Werte nach der "Serial"-Strategie. Dies ist Default.
	Z.B. 27 ==> ZA "Serielle" oder beim der "Multiplikator Strategie" AA. Multiplikator - Stategie bedeutet: Den Modulo Wert entsprechend häufig darstellen.
	 * @param i
	 * @return
	 * @author Fritz Lindhauer, 04.03.2019, 09:03:52
	 */
	public static String getStringForNumber(int i){
		ICounterStrategyAlphanumericZZZ objCounterStrategy = new CounterStrategyAlphabetSerialZZZ();
		return CounterByCharacterAscii_AlphabetZZZ.getStringAlphabetForNumber_(i, objCounterStrategy);
	}
	
	/**Behandlung der Werte nach der "Serial"-Strategie. Dies ist Default.
	Z.B. 27 ==> ZA "Serielle" oder beim der "Multiplikator Strategie" AA. Multiplikator - Stategie bedeutet: Den Modulo Wert entsprechend häufig darstellen.
	 * @param i
	 * @return
	 * @author Fritz Lindhauer, 04.03.2019, 09:03:52
	 */
	public static String getStringForNumber(int i, boolean bLowercase){
		ICounterStrategyAlphanumericZZZ objCounterStrategy = new CounterStrategyAlphabetSerialZZZ();
		objCounterStrategy.isLowercase(bLowercase);
		return CounterByCharacterAscii_AlphabetZZZ.getStringAlphabetForNumber_(i, objCounterStrategy);
	}
	
	/**Behandlung der Werte nach der "Serial"-Strategie. Dies ist Default.
	Z.B. 27 ==> ZA "Serielle" oder beim der "Multiplikator Strategie" AA. Multiplikator - Stategie bedeutet: Den Modulo Wert entsprechend häufig darstellen.
	 * @param i
	 * @return
	 * @author Fritz Lindhauer, 04.03.2019, 09:03:52
	 */
	public static String getStringForNumber(int i, boolean bLowercase, boolean bRightAligned){
		ICounterStrategyAlphanumericZZZ objCounterStrategy = new CounterStrategyAlphabetSerialZZZ();
		objCounterStrategy.isLowercase(bLowercase);
		objCounterStrategy.isRightAligned(bRightAligned);
		return CounterByCharacterAscii_AlphabetZZZ.getStringAlphabetForNumber_(i, objCounterStrategy);
	}
	
	/**Behandlung der Werte nach der "Multiple"-Strategie.
	Z.B. 27 ==> ZA "Serielle" oder beim der "Multiplikator Strategie" AA. Multiplikator - Stategie bedeutet: Den Modulo Wert entsprechend häufig darstellen.
	 * @param i
	 * @return
	 * @author Fritz Lindhauer, 04.03.2019, 09:03:52
	 */
	public static String getStringMultipleForNumber(int i){
		ICounterStrategyAlphanumericZZZ objCounterStrategy = new CounterStrategyAlphabetMultipleZZZ();
		return CounterByCharacterAscii_AlphabetZZZ.getStringAlphabetForNumber_(i, objCounterStrategy);
	}
	
	/**Behandlung der Werte nach der "Multiple"-Strategie. 
	Z.B. 27 ==> ZA "Serielle" oder beim der "Multiplikator Strategie" AA. Multiplikator - Stategie bedeutet: Den Modulo Wert entsprechend häufig darstellen.
	 * @param i
	 * @param bLowercase
	 * @return
	 * @author Fritz Lindhauer, 04.03.2019, 09:03:56
	 */
	public static String getStringMultipleForNumber(int i, boolean bLowercase){
		ICounterStrategyAlphanumericZZZ objCounterStrategy = new CounterStrategyAlphabetMultipleZZZ();
		objCounterStrategy.isLowercase(bLowercase);
		return CounterByCharacterAscii_AlphabetZZZ.getStringAlphabetForNumber_(i, objCounterStrategy);		
	}
	
	public static String getStringForNumber(int i, ICounterStrategyAlphanumericZZZ objCounterStrategy) throws ExceptionZZZ{
		return CounterByCharacterAscii_AlphabetZZZ.getStringAlphabetForNumber_(i, objCounterStrategy);
	}
	
	private static String getStringAlphabetForNumber_(int iNumber, ICounterStrategyAlphanumericZZZ objCounterStrategy){
		String sReturn = null;		
		main:{
				if(iNumber<=0) break main;			
				if(objCounterStrategy==null){
					objCounterStrategy = new CounterStrategyAlphabetMultipleZZZ();
//					ExceptionZZZ ez = new ExceptionZZZ("AlphanumericCounter: Kein CounterStrategy-Objekt übergeben.", iERROR_PARAMETER_VALUE, CounterByCharacterAscii_AlphanumericZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
//					throw ez;
				}
																			
				sReturn = objCounterStrategy.computeStringForNumber(iNumber);

		}//end main:
		return sReturn;		
	}
	
	//###################
	public static int getNumberForString(String sValue) throws ExceptionZZZ{
		ICounterStrategyAlphanumericZZZ objCounterStrategy = new CounterStrategyAlphabetSerialZZZ();
		return CounterByCharacterAscii_AlphabetZZZ.getNumberForStringAlphabet_(sValue, objCounterStrategy);
	}
	
	public static int getNumberForString(String sValue, boolean bLowercase) throws ExceptionZZZ{
		ICounterStrategyAlphanumericZZZ objCounterStrategy = new CounterStrategyAlphabetSerialZZZ();		
		objCounterStrategy.isLowercase(bLowercase);
		return CounterByCharacterAscii_AlphabetZZZ.getNumberForStringAlphabet_(sValue, objCounterStrategy);
	}
	public static int getNumberForString(String sValue, ICounterStrategyAlphanumericZZZ objCounterStrategy) throws ExceptionZZZ{
		return CounterByCharacterAscii_AlphabetZZZ.getNumberForStringAlphabet_(sValue, objCounterStrategy);
	}

	
	public static int getNumberForStringMultiple(String sValue) throws ExceptionZZZ{
		ICounterStrategyAlphanumericZZZ objCounterStrategy = new CounterStrategyAlphabetMultipleZZZ();
		return CounterByCharacterAscii_AlphabetZZZ.getNumberForStringAlphabet_(sValue, objCounterStrategy);
	}
	
	public static int getNumberForStringMultiple(String sValue, boolean bLowercase) throws ExceptionZZZ{
		ICounterStrategyAlphanumericZZZ objCounterStrategy = new CounterStrategyAlphabetMultipleZZZ();		
		objCounterStrategy.isLowercase(bLowercase);
		return CounterByCharacterAscii_AlphabetZZZ.getNumberForStringAlphabet_(sValue, objCounterStrategy);
	}
	
	private static int getNumberForStringAlphabet_(String sValue, ICounterStrategyAlphanumericZZZ objCounterStrategy) throws ExceptionZZZ{
		int iReturn = 0;
		main:{
			if(StringZZZ.isEmpty(sValue))break main;
			
			//1. Prüfen, ist das überhaupt ein erlaubtes Zeichen?
			boolean bValid = CounterByCharacterAscii_AlphabetZZZ.isValidCharacter(sValue);
			if(!bValid){
				ExceptionZZZ ez = new ExceptionZZZ("NumericCounter: Ungültiges Zeichen übergeben im String '" + sValue + "'", iERROR_PARAMETER_VALUE, CounterByCharacterAscii_AlphabetZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			
			//.... Besonderheiten der Zählerstrategien 
			boolean bSyntaxValid = objCounterStrategy.checkSyntax(sValue);
			if(!bSyntaxValid){
				ExceptionZZZ ez = new ExceptionZZZ("NumericCounter: Für die Strategy '" + objCounterStrategy.getClass().getName() + "' ist die Syntax des String snicht korrekt '" + sValue +"'", iERROR_PARAMETER_VALUE, CounterByCharacterAscii_AlphabetZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			iReturn = objCounterStrategy.computeNumberForString(sValue);														
		}//end main:
		return iReturn;
	}
	
	
	//####################
	
	/** Grundlage für einen Konstruktor, bei dem ein String als Ausgangszähler übergeben wird.
	 * @param c
	 * @return
	 * @author Fritz Lindhauer, 16.03.2019, 08:46:42
	 */
	public static int getPositionForChar(char c){
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
	
	
	
	public static String getCharForPosition(int i) {
			return i > (CounterByCharacterAscii_AlphabetZZZ.iPOSITION_MIN-1) && i < (CounterByCharacterAscii_AlphabetZZZ.iPOSITION_MAX+1) ? String.valueOf((char)(i + 'A' - 1)) : null;
	}
		
	public static String getCharForPosition(int i, boolean bLowercase) {
		if(bLowercase){
			return i > (CounterByCharacterAscii_AlphabetZZZ.iPOSITION_MIN-1) && i < (CounterByCharacterAscii_AlphabetZZZ.iPOSITION_MAX+1) ? String.valueOf((char)(i + 'a' - 1)) : null; //Bei Kleinbuchstaben sind das andere ASCII Werte. Aber mit 'Zeichen'/Characters kann man wie mit Integer rechnen
		}else{
			return CounterByCharacterAscii_AlphabetZZZ.getCharForPosition(i);
		}
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

	//### Aus Interface	
	@Override
	public String peekChange(int iValue) throws ExceptionZZZ {	
		String sReturn = null;
		main:{
			ICounterStrategyAlphanumericZZZ objCounterStrategy = this.getCounterStrategyObject();
			sReturn = CounterByCharacterAscii_AlphabetZZZ.getStringForNumber(iValue, objCounterStrategy);	
		}//end main:
		return sReturn;
	}

	@Override
	public void setValueCurrent(String sValue) throws ExceptionZZZ{			
		main:{
			ICounterStrategyAlphanumericZZZ objCounterStrategy = this.getCounterStrategyObject();
			int iValue = CounterByCharacterAscii_AlphabetZZZ.getNumberForString(sValue, objCounterStrategy);
			this.setValueCurrent(iValue);
		}//end main:
	}	
	
	// ++++ Aus Interface
//			public ICounterStrategyAlphanumericZZZ getCounterStrategyObject(){
//				if(this.objCounterStrategy==null){
//					ICounterStrategyAlphanumericZZZ objCounterStrategy = new CounterStrategyAlphanumericMultipleZZZ();
//					this.objCounterStrategy = objCounterStrategy;
//				}
//				return this.objCounterStrategy;
//			}
	public void setCounterStrategyObject(ICounterStrategyAlphanumericZZZ objCounterStrategy){
		this.objCounterStrategy = objCounterStrategy;
	}		
	
	//nach Umstellung auf Generics
	public T getCounterStrategyObject(){
		if(this.objCounterStrategy==null){
			ICounterStrategyAlphanumericZZZ objCounterStrategy = new CounterStrategyAlphanumericMultipleZZZ();
			this.objCounterStrategy = objCounterStrategy;
		}
		return (T) this.objCounterStrategy;
	}
//	public <T> void setCounterStrategyObject(T objCounterStrategy){
//		this.objCounterStrategy = (ICounterStrategyAlphanumericZZZ) objCounterStrategy;
//	}				
	@Override
	public void setCounterStrategyObject(ICounterStrategyZZZ objCounterStrategy) {
		// TODO Auto-generated method stub
		
	}
}//End class
