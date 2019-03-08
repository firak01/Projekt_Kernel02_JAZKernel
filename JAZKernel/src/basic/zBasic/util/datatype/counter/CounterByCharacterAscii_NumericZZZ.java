package basic.zBasic.util.datatype.counter;

import java.util.ArrayList;

public class CounterByCharacterAscii_NumericZZZ  extends AbstractCounterByCharacterAsciiZZZ{

	public static int iNUMERIC_POSITION_MIN=1;  //Das Ziel sollte sein, dass iALPHABET_POSTION_MIN = 
	public static int iNUMERIC_POSITION_MAX=10;//die 10 Ziffern 0-9 dazu.
	
	//##### DAMIT DER COUNTER ÜBER DIE FACTORY ERZEUGT WERDEN KANN UND SICH DEN AKTUELLEN WERT, ETC. MERKT
	//Konstruktoren
	public CounterByCharacterAscii_NumericZZZ(){
		super();
		this.setValueCurrent(CounterByCharacterAscii_NumericZZZ.iNUMERIC_POSITION_MIN);
	}
	public CounterByCharacterAscii_NumericZZZ(int iStartingValue){
		super();
		this.setValueCurrent(iStartingValue);
	}
	public CounterByCharacterAscii_NumericZZZ(String sStartingValue){
		//TODO GOON: Erst einmal muss es die statische Methode geben, um vom String auf den int-Wert umzuschlüsseln.
		
	}
		
	
	//##### STATISCHE METHODEN ######
	/**Behandlung der Werte nach der "Serial"-Strategie. Dies ist Default.
	Z.B. 10 ==> "90" "Serielle" oder beim der "Multiplikator Strategie" "11". Multiplikator - Stategie bedeutet: Den Modulo Wert entsprechend häufig darstellen.
	 * @param i
	 * @return
	 * @author Fritz Lindhauer, 04.03.2019, 09:03:52
	 */
	public static String getStringNumericForNumber(int i){
		return CounterByCharacterAscii_NumericZZZ.getStringNumericForNumber_(i, false);//es gibt keine Kleinbuchstaben
	}
		
	/**Behandlung der Werte nach der "Multiple"-Strategie.
	Z.B. 10 ==> "90" "Serielle" oder beim der "Multiplikator Strategie" "11". Multiplikator - Stategie bedeutet: Den Modulo Wert entsprechend häufig darstellen.
	 * @param i
	 * @return
	 * @author Fritz Lindhauer, 04.03.2019, 09:03:52
	 */
	public static String getStringNumericMultipleForNumber(int i){
		return CounterByCharacterAscii_NumericZZZ.getStringNumericForNumber_(i, true);//es gibt keine Kleinbuschstaben
	}
	
	
	/** Merke: Es gibt keine Kleinbuchstaben bei NUMMERN
	 * @param i
	 * @param bMultipleStrategy
	 * @return
	 * @author Fritz Lindhauer, 04.03.2019, 12:00:15
	 */
	private static String getStringNumericForNumber_(int i, boolean bMultipleStrategy){
		String sReturn = null;		
		main:{
			
		   //Ermittle den "Teiler" und den Rest, Also Modulo - Operation
			int iDiv = Math.abs(i / CounterByCharacterAscii_NumericZZZ.iNUMERIC_POSITION_MAX ); //durch abs wird also intern in ein Integer umgewandetl.... nicht nur das Weglassen des ggfs. negativen Vorzeichens.
			int iMod = i % CounterByCharacterAscii_NumericZZZ.iNUMERIC_POSITION_MAX;

			
			if(!bMultipleStrategy){ //"SERIAL STRATEGY"
				ArrayList<String>listas=new ArrayList<String>();			
				for(int icount = 1; icount <= iDiv; icount++){
					String stemp = CounterByCharacterAscii_NumericZZZ.getCharForPositionInNumeric(CounterByCharacterAscii_NumericZZZ.iNUMERIC_POSITION_MAX);
					listas.add(stemp);
				}
				if(iMod>=1){
					String stemp = CounterByCharacterAscii_NumericZZZ.getCharForPositionInNumeric(iMod);
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
					sCharacter = CounterByCharacterAscii_NumericZZZ.getCharForPositionInNumeric(iMod);	
					sReturn = sCharacter;
				}else if(iMod==0){
					sCharacter = CounterByCharacterAscii_NumericZZZ.getCharForPositionInNumeric(CounterByCharacterAscii_NumericZZZ.iNUMERIC_POSITION_MAX);
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
	
	

	
	public static String getCharForPositionInNumeric(int i) {
		return i > (CounterByCharacterAscii_NumericZZZ.iNUMERIC_POSITION_MIN-1) && i < (CounterByCharacterAscii_NumericZZZ.iNUMERIC_POSITION_MAX+1) ? String.valueOf((char)(i + '0' - 1)) : null; //Merke: Es gibt keine Kleinbuchstaben Variante
	}


//### Aus Interface
	protected void setCurrent(String sCurrent){
		//Da der Wert nicht gespeichert wird, muss nun aus dem String die Zahl berechnet werden.
		//TODO GOON 20190308
	}

	@Override
	public String getStringFor(int iValue) {
		String sCurrent = CounterByCharacterAscii_NumericZZZ.getStringNumericForNumber(iValue);
		return sCurrent;
	}	
	
	
}
