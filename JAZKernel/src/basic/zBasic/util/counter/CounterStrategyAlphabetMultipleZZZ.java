package basic.zBasic.util.counter;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;

public class CounterStrategyAlphabetMultipleZZZ extends AbstractCounterStrategyAlphabetZZZ{
	public CounterStrategyAlphabetMultipleZZZ() throws ExceptionZZZ{
		super();
		this.makeReflectableInitialization();
	}
	
	public CounterStrategyAlphabetMultipleZZZ(String sStartValue) throws ExceptionZZZ{
		super(sStartValue);
		this.makeReflectableInitialization();
	}
	
	public CounterStrategyAlphabetMultipleZZZ(boolean bLowercase) throws ExceptionZZZ{
		super(bLowercase);
		this.makeReflectableInitialization();
	}
	
	public CounterStrategyAlphabetMultipleZZZ(String sStartValue, boolean bLowercase) throws ExceptionZZZ {
		super(sStartValue, bLowercase);
		this.makeReflectableInitialization();
	}
	
	@Override
	public boolean checkSyntax(String sTotal) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			if(StringZZZ.isEmpty(sTotal)) break main;
			
			String sLetterFirst = StringZZZ.letterFirst(sTotal);
			
			//A) Multiple: 
			//Merke: Links/Rechtsbündig spielt keine Rolle
			for(int icounter=1;icounter<=sTotal.length()-1;icounter++){
				String stemp=StringZZZ.mid(sTotal, icounter, 1);
			    if(!sLetterFirst.equals(stemp)){
			    	ExceptionZZZ ez = new ExceptionZZZ("MultipleStrategy: Alle Zeichen des ASCII-Zählers müssen gleich sein. String='"+sTotal+"'.", iERROR_PARAMETER_VALUE, CounterByCharacterAscii_AlphanumericZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
			    }
			}
			bReturn = true;
		}//end main:
		return bReturn;
	}

	@Override
	public int computeNumberForString(String sTotal) {
		int iReturn = -1;
		main:{
			char[] caValue = sTotal.toCharArray();
			if(caValue.length>=2){
				char c = caValue[caValue.length-1];
				
				//Multiple Zählvariante
				int iC = CounterByCharacterAscii_AlphabetZZZ.getPositionForChar(c);				
				iReturn= iC + (CounterByCharacterAscii_AlphabetZZZ.iPOSITION_MAX * (caValue.length-1));	//An der letzten Stelle den ermittelten Wert nehmen	 und hinzuzählen		
				
			}else{				
				char c = caValue[0];
				iReturn = CounterByCharacterAscii_AlphabetZZZ.getPositionForChar(c);
			}		
		}//end main; 
		iReturn = this.getDigitValueForPositionValue(iReturn); //weil der Stellenwert nicht gleich der CharacterPosition ist
		return iReturn;			
	}

	@Override
	public String computeStringForNumber(int iNumber) {
		String sReturn = null;
		main:{
			  //Ermittle den "Teiler" und den Rest, Also Modulo - Operation
			//int iDiv = Math.abs(iNumber / CounterByCharacterAscii_AlphabetZZZ.iPOSITION_MAX ); //durch abs wird also intern in ein Integer umgewandetl.... nicht nur das Weglassen des ggfs. negativen Vorzeichens.
			//int iMod = iNumber % CounterByCharacterAscii_AlphabetZZZ.iPOSITION_MAX;

			int iDiv = Math.abs(iNumber / this.getDigitValueMax()); //durch abs wird also intern in ein Integer umgewandetl.... nicht nur das Weglassen des ggfs. negativen Vorzeichens.
			int iMod = iNumber % this.getDigitValueMax();
			
			boolean bLowercase = this.isLowercase();
			if(iMod==0 && iDiv ==0){
				sReturn = CounterByCharacterAscii_AlphabetZZZ.getCharForPosition(CounterByCharacterAscii_AlphabetZZZ.iPOSITION_MIN,bLowercase);
				break main;
			}else{
				//Ermittle den "Modulo"-Wert und davon das Zeichen
				int iModPosition;
				if(iDiv==0){
					iModPosition = iMod +1;
				}else{
					iModPosition = iMod;
				}
				
				String sCharacter=null;
				if(iMod>=1){
					sCharacter = CounterByCharacterAscii_AlphabetZZZ.getCharForPosition(iModPosition,bLowercase);	
					sReturn = sCharacter;				
				}else if(iMod==0){
					sCharacter = CounterByCharacterAscii_AlphabetZZZ.getCharForPosition(CounterByCharacterAscii_AlphabetZZZ.iPOSITION_MAX,bLowercase);
					sReturn = "";				
				}
										
				//Zusammenfassen der Werte: Multiple Strategie
				sReturn = sReturn + CounterStrategyHelperZZZ.getStringConsolidated(sCharacter, iDiv);
			}
		}//end main:
		return sReturn;
	}
	
	@Override
	public boolean makeReflectableInitialization() throws ExceptionZZZ {
		return this.initClassMethodCallingString();
	}
}
