package basic.zBasic.util.datatype.counter;

import java.util.ArrayList;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.datatype.character.CharZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;

public class CounterByCharacterAscii_NumericZZZ  extends AbstractCounterByCharacterAsciiNumericZZZ{

	public static int iNUMERIC_POSITION_MIN=1;  //Das Ziel sollte sein, dass iALPHABET_POSTION_MIN = 
	public static int iNUMERIC_POSITION_MAX=10;//die 10 Ziffern 0-9 dazu.
	
	public static String sREGEX_CHARACTERS="[0-9]";
	
	//##### DAMIT DER COUNTER ÜBER DIE FACTORY ERZEUGT WERDEN KANN UND SICH DEN AKTUELLEN WERT, ETC. MERKT
	//Konstruktoren
	public CounterByCharacterAscii_NumericZZZ(){
		super();
		this.setValueCurrent(CounterByCharacterAscii_NumericZZZ.iNUMERIC_POSITION_MIN);
	}
	public CounterByCharacterAscii_NumericZZZ(int iStartingValue){
		super(iStartingValue);		
	}
	public CounterByCharacterAscii_NumericZZZ(String sStartingValue){
		super(sStartingValue);
	}
		
	
	//##### STATISCHE METHODEN ######
	/**Behandlung der Werte nach der "Serial"-Strategie. Dies ist Default.
	Z.B. 10 ==> "90" "Serielle" oder beim der "Multiplikator Strategie" "11". Multiplikator - Stategie bedeutet: Den Modulo Wert entsprechend häufig darstellen.
	 * @param i
	 * @return
	 * @author Fritz Lindhauer, 04.03.2019, 09:03:52
	 */
	public static String getStringNumericForNumber(int i){
		ICounterStrategyZZZ objCounterStrategy = new CounterStrategyNumericSerialZZZ();
		return CounterByCharacterAscii_NumericZZZ.getStringNumericForNumber_(i, objCounterStrategy);//es gibt keine Kleinbuchstaben
	}
		
	/**Behandlung der Werte nach der "Multiple"-Strategie.
	Z.B. 10 ==> "90" "Serielle" oder beim der "Multiplikator Strategie" "11". Multiplikator - Stategie bedeutet: Den Modulo Wert entsprechend häufig darstellen.
	 * @param i
	 * @return
	 * @author Fritz Lindhauer, 04.03.2019, 09:03:52
	 */
	public static String getStringNumericMultipleForNumber(int i){
		ICounterStrategyZZZ objCounterStrategy = new CounterStrategyNumericMultipleZZZ();
		return CounterByCharacterAscii_NumericZZZ.getStringNumericForNumber_(i, objCounterStrategy);//es gibt keine Kleinbuschstaben
	}
	
	
	/** Merke: Es gibt keine Kleinbuchstaben bei NUMMERN
	 * @param i
	 * @param bMultipleStrategy
	 * @return
	 * @author Fritz Lindhauer, 04.03.2019, 12:00:15
	 */
	private static String getStringNumericForNumber_(int iNumber, ICounterStrategyZZZ objCounterStrategy){
		String sReturn = null;		
		main:{
				if(iNumber<0) break main;			
				if(objCounterStrategy==null){
					objCounterStrategy = new CounterStrategyNumericMultipleZZZ();
//					ExceptionZZZ ez = new ExceptionZZZ("AlphanumericCounter: Kein CounterStrategy-Objekt übergeben.", iERROR_PARAMETER_VALUE, CounterByCharacterAscii_AlphanumericZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
//					throw ez;
				}
				
				sReturn = objCounterStrategy.computeStringForNumber(iNumber);
				
			
		}//end main:
		return sReturn;		
	}
	
	

	
	public static String getCharForPositionInNumeric(int i) {
		return i > (CounterByCharacterAscii_NumericZZZ.iNUMERIC_POSITION_MIN-1) && i < (CounterByCharacterAscii_NumericZZZ.iNUMERIC_POSITION_MAX+1) ? String.valueOf((char)(i + '0' - 1)) : null; //Merke: Es gibt keine Kleinbuchstaben Variante
	}
	public static String getCharHighestInNumeric(boolean bLowercase){
		int  i= CounterByCharacterAscii_NumericZZZ.iNUMERIC_POSITION_MAX;
		return CounterByCharacterAscii_NumericZZZ.getCharForPositionInNumeric(i);
	}
	public static String getCharLowestInNumeric(boolean bLowercase){
		int  i= CounterByCharacterAscii_NumericZZZ.iNUMERIC_POSITION_MIN;
		return CounterByCharacterAscii_NumericZZZ.getCharForPositionInNumeric(i);
	}
	
	
	/** Grundlage für einen Konstruktor, bei dem ein String als Ausgangszähler übergeben wird.
	 * @param c
	 * @return
	 * @author Fritz Lindhauer, 16.03.2019, 08:46:42
	 */
	public static int getPositionInNumericForChar(char c){
		int iReturn = -1;
		main:{
			//1. Prüfen, ist das überhaupt ein erlaubtes Zeichen?
			boolean bValid = CounterByCharacterAscii_NumericZZZ.isValidCharacter(c);
			if(!bValid) break main;
			
			//2. Umrechnung auf den ASCII-Integer-Wert
			int i = c;
			
			//3. Umrechnung dieses Wertes auf die Position in Nummerischen ASCII-Bereich
			//String.valueOf((char)(i + '0' - 1))
			iReturn = i - '0' + 1;//Umgerechnet aus der Methode getCharForPositiionInNumeric
							
		}
		return iReturn;
	}
	
	//++++++++++
	public static boolean isValidCharacter(char c){
		boolean bReturn = false;
		main:{
			String s = Character.toString(c);
			bReturn = CounterByCharacterAscii_NumericZZZ.isValidCharacter(s);
		}
		return bReturn;
	}
	
	
	
	//##############################################
	//Statische Methoden, um aus einer Buchstabenkombination eine Zahl zu machen.
	public static int getNumberForStringNumeric(String sValue) throws ExceptionZZZ{			
				ICounterStrategyZZZ objCounterStrategy = new CounterStrategyNumericSerialZZZ();
				return CounterByCharacterAscii_NumericZZZ.getNumberForStringNumeric_(sValue, objCounterStrategy);						
	}
	public static int getNumberForStringNumericMultiple(String sValue) throws ExceptionZZZ{			
		ICounterStrategyZZZ objCounterStrategy = new CounterStrategyNumericMultipleZZZ();
		return CounterByCharacterAscii_NumericZZZ.getNumberForStringNumeric_(sValue, objCounterStrategy);						
}
	
	private static int getNumberForStringNumeric_(String sValue, ICounterStrategyZZZ objCounterStrategy) throws ExceptionZZZ{
		int iReturn = 0;
		main:{
			if(StringZZZ.isEmpty(sValue))break main;
			
			//1. Prüfen, ist das überhaupt ein erlaubtes Zeichen?
			boolean bValid = CounterByCharacterAscii_NumericZZZ.isValidCharacter(sValue);
			if(!bValid){
				ExceptionZZZ ez = new ExceptionZZZ("NumericCounter: Ungültiges Zeichen übergeben im String '" + sValue + "'", iERROR_PARAMETER_VALUE, CounterByCharacterAscii_AlphanumericZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			
			//.... Besonderheiten der Zählerstrategien 
			boolean bSyntaxValid = objCounterStrategy.checkSyntax(sValue);
			if(!bSyntaxValid){
				ExceptionZZZ ez = new ExceptionZZZ("NumericCounter: Für die Strategy '" + objCounterStrategy.getClass().getName() + "' ist die Syntax des String snicht korrekt '" + sValue +"'", iERROR_PARAMETER_VALUE, CounterByCharacterAscii_AlphanumericZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			iReturn = objCounterStrategy.computeNumberForString(sValue);														
		}//end main:
		return iReturn;
	}

	public static boolean isValidCharacter(String s){
		boolean bReturn = false;
		main:{
			if(StringZZZ.isEmpty(s)) break main;
			
			String sRegex = CounterByCharacterAscii_NumericZZZ.sREGEX_CHARACTERS;
			
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
		String sCurrent = CounterByCharacterAscii_NumericZZZ.getStringNumericForNumber(iValue);
		return sCurrent;
	}
	@Override
	public void setValueCurrent(String sValue) throws ExceptionZZZ{	
		int iValue = CounterByCharacterAscii_NumericZZZ.getNumberForStringNumeric(sValue);
		this.setValueCurrent(iValue);
	}	
	
	
}
