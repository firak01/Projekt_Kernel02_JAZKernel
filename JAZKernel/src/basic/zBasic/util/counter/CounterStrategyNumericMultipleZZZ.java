package basic.zBasic.util.counter;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;

public class CounterStrategyNumericMultipleZZZ extends AbstractCounterStrategyNumbersOnlyZZZ{
	public CounterStrategyNumericMultipleZZZ() throws ExceptionZZZ{
		super();
		this.makeReflectableInitialization();
	}
	
	@Override
	public boolean checkSyntax(String sTotal) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			if(StringZZZ.isEmpty(sTotal)) break main;
			
			String sLetterFirst = StringZZZ.letterFirst(sTotal);
			
			//A) Multiple: 
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
		int iReturn = 0;
		
		main:{
			char[] caValue = sTotal.toCharArray();
			if(caValue.length>=2){
				char c = caValue[caValue.length-1];
				
				//Serielle Zählvariante
				int iC = CounterByCharacterAscii_NumericZZZ.getPositionForChar(c);				
				iReturn= iC + (CounterByCharacterAscii_NumericZZZ.iPOSITION_MAX * (caValue.length-1));	//An der letzten Stelle den ermittelten Wert nehmen	 und hinzuzählen		
				
			}else{				
				char c = caValue[0];
				iReturn = CounterByCharacterAscii_NumericZZZ.getPositionForChar(c);
			}
		
		}//end main;
		return iReturn;			
	}

	@Override
	public String computeStringForNumber(int iNumber) {
		String sReturn = null;
		main:{
			  //Ermittle den "Teiler" und den Rest, Also Modulo - Operation
			int iDiv = Math.abs(iNumber / CounterByCharacterAscii_NumericZZZ.iPOSITION_MAX ); //durch abs wird also intern in ein Integer umgewandetl.... nicht nur das Weglassen des ggfs. negativen Vorzeichens.
			int iMod = iNumber % CounterByCharacterAscii_NumericZZZ.iPOSITION_MAX;
	
			if(iMod==0 && iDiv ==0) break main;
			
			//Ermittle den "Modulo"-Wert und davon das Zeichen
			String sCharacter=null;
			if(iMod>=1){
				sCharacter = CounterByCharacterAscii_NumericZZZ.getCharForPosition(iMod);	
				sReturn = sCharacter;
			}else if(iMod==0){
				sCharacter = CounterByCharacterAscii_NumericZZZ.getCharForPosition(CounterByCharacterAscii_NumericZZZ.iPOSITION_MAX);
				sReturn = "";
			}
			
			//Zusammenfassen der Werte: Multiple Strategie
			sReturn = sReturn + CounterStrategyHelperZZZ.getStringConsolitated(sCharacter, iDiv);		
		}//end main:
		return sReturn;
	}

	@Override
	public boolean makeReflectableInitialization() throws ExceptionZZZ {
		return this.initClassMethodCallingString();
	}


}
