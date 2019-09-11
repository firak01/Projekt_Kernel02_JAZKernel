package basic.zBasic.util.counter;

import java.util.ArrayList;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;

public class CounterStrategyNumericSerialZZZ extends AbstractCounterStrategyNumbersOnlyZZZ{
	public CounterStrategyNumericSerialZZZ() throws ExceptionZZZ{
		super();
		this.makeReflectableInitialization();
	}
	
	@Override
	public boolean checkSyntax(String sTotal) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			if(StringZZZ.isEmpty(sTotal)) break main;			
			
			String sLetterLast = StringZZZ.letterLast(sTotal);						
			
			//B) Seriell			   
			//B1) Überprüfe hinsichtlich der Groß-/Kleinschreibung. Die Zeichen müssen hier durchgängig groß oder klein sein.
			//Das prüft man am besten durch Kleinsetzung ab und durch Großsetzung.
			String sLowerized = sTotal.toLowerCase();
			boolean bLowerized = false; boolean bCapsized = false;
			if(sLowerized.equals(sTotal)){
				bLowerized = true;
			}
			
			String sCapsized = sTotal.toUpperCase();
			if(sCapsized.equals(sTotal)){
				bCapsized = true;
			}
			if(!(bLowerized || bCapsized)){
			    	ExceptionZZZ ez = new ExceptionZZZ("SerialStrategy: Alle Zeichen des ASCII-Zählers müssen gleich sein hinsichtlich der Groß-/Kleinschreibung. String='"+sTotal+"'.", iERROR_PARAMETER_VALUE, CounterByCharacterAscii_AlphanumericZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
					throw ez;					
			}
			
			//B2: Die Zeichen links müssen immer das höchste Zeichen des Zeichenraums sein.
			String sLetterMax = CounterByCharacterAscii_NumericZZZ.getCharHighest(bLowerized);
			for (int icount=0;icount<=sTotal.length()-2;icount++){
				String stemp = StringZZZ.letterAtPosition(sTotal,icount);
				if(!sLetterMax.equals(stemp)){
					ExceptionZZZ ez = new ExceptionZZZ("SerialStrategy: Alle Zeichen links der rechtesten Stelle müssen das höchste Zeichen sein (Höchstes Zeiche='"+sLetterMax+"'), String='"+sTotal+"'.", iERROR_PARAMETER_VALUE, CounterByCharacterAscii_AlphanumericZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
					throw ez;	
				}
			}
			
			bReturn = true;
		}//end main
		return bReturn;
		
	}

	@Override
	public int computeNumberForString(String sTotal) {
		int iReturn = 0;
		
		main:{
			boolean bRightAligned = this.isRightAligned();
			if(bRightAligned){				
				char[] caValue = sTotal.toCharArray();
				for (int icounter=caValue.length-1; icounter>=0 ; icounter--){
					char c = caValue[icounter];
					
					//Serielle Zählvariante
					int iC = CounterByCharacterAscii_NumericZZZ.getPositionForChar(c);
					if(icounter==0){
						iReturn+= iC;	//An der letzten Stelle den ermittelten Wert nehmen	 und hinzuzählen		
					}else{
						iReturn+= (CounterByCharacterAscii_NumericZZZ.iPOSITION_MAX * (icounter+1));//Den "Stellenwert" ermitteln und hinzuzählen.
					}	
				}
			}else{
				char[] caValue = sTotal.toCharArray();
				for (int icounter=0; icounter<= caValue.length-1; icounter++){
					char c = caValue[icounter];
					
					//Serielle Zählvariante
					int iC = CounterByCharacterAscii_NumericZZZ.getPositionForChar(c);
					if(icounter==(caValue.length-1)){
						iReturn+= iC;	//An der letzten Stelle den ermittelten Wert nehmen	 und hinzuzählen		
					}else{
						iReturn+= (CounterByCharacterAscii_NumericZZZ.iPOSITION_MAX * (icounter+1));//Den "Stellenwert" ermitteln und hinzuzählen.
					}	
				}
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
			
			
			ArrayList<String>listas=new ArrayList<String>();
			boolean bRightAligned = this.isRightAligned();
			//boolean bLowercase = this.isLowercase(); //numerisch nicht vorhanden.		
			
			if(bRightAligned){
				for(int icount = 1; icount <= iDiv; icount++){
					String stemp = CounterByCharacterAscii_NumericZZZ.getCharForPosition(CounterByCharacterAscii_NumericZZZ.iPOSITION_MAX);
					listas.add(stemp);
				}
				if(iMod>=1){
					String stemp = CounterByCharacterAscii_NumericZZZ.getCharForPosition(iMod);
					listas.add(stemp);
				}
			}else{
				if(iMod>=1){
					String stemp = CounterByCharacterAscii_NumericZZZ.getCharForPosition(iMod);
					listas.add(stemp);
				}
				
				for(int icount = 1; icount <= iDiv; icount++){
					String stemp = CounterByCharacterAscii_NumericZZZ.getCharForPosition(CounterByCharacterAscii_NumericZZZ.iPOSITION_MAX);
					listas.add(stemp);
				}				
			}
			
			//Das Zusammenfassen der Werte in eine HelperKlasse verlagert						
			sReturn = CounterStrategyHelperZZZ.getStringConsolidated(listas, bRightAligned);					
		}//end main:
		return sReturn;
	}

	@Override
	public boolean makeReflectableInitialization() throws ExceptionZZZ {
		return this.initClassMethodCallingString();
	}
}
