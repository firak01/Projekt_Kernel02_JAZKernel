package basic.zBasic.util.counter;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;

public class CounterStrategyAlphanumericMultipleZZZ extends AbstractCounterStrategyAlphanumericZZZ{
	public CounterStrategyAlphanumericMultipleZZZ() throws ExceptionZZZ{
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
		int iReturn = -1;
		main:{
			if(StringZZZ.isEmpty(sTotal)) break main;
			
			String sLetterFirst = StringZZZ.letterFirst(sTotal);
			char c = sLetterFirst.toCharArray()[0];
			if (sTotal.length()==1){	
				iReturn = CounterByCharacterAscii_AlphanumericZZZ.getPositionForChar(c);
			}else if(sTotal.length()>=2){					
				iReturn = CounterByCharacterAscii_AlphanumericZZZ.getPositionForChar(c);
				iReturn = iReturn + ((sTotal.length()-1)*CounterByCharacterAscii_AlphanumericZZZ.iPOSITION_MAX);
			}
		}//end main
		return iReturn;
	}

	@Override
	public String computeStringForNumber(int iNumber) {
		String sReturn = null;
		main:{
			  //Ermittle den "Teiler" und den Rest, Also Modulo - Operation
			int iDiv = Math.abs(iNumber / CounterByCharacterAscii_AlphanumericZZZ.iPOSITION_MAX ); //durch abs wird also intern in ein Integer umgewandetl.... nicht nur das Weglassen des ggfs. negativen Vorzeichens.
			int iMod = iNumber % CounterByCharacterAscii_AlphanumericZZZ.iPOSITION_MAX;

			if(iMod==0 && iDiv ==0) break main;
			
			boolean bLowercase = this.isLowercase();
			
			//Ermittle den "Modulo"-Wert und davon das Zeichen
			String sCharacter=null;			
			if(iMod>=1){
				sCharacter = CounterByCharacterAscii_AlphanumericZZZ.getCharForPosition(iMod, bLowercase);	
				sReturn = sCharacter;
			}else if(iMod==0){
				sCharacter = CounterByCharacterAscii_AlphanumericZZZ.getCharForPosition(CounterByCharacterAscii_AlphanumericZZZ.iPOSITION_MAX, bLowercase);
				sReturn = "";
			}
			
			
			//Zusammenfassen der Werte: Multiple Strategie
			for(int icount=1; icount <= iDiv; icount++){					
					sReturn+=sCharacter;
			}
		}//end main:
		return sReturn;
	}

	@Override
	public boolean makeReflectableInitialization() throws ExceptionZZZ {		
		return this.initClassMethodCallingString();
	}	
}
