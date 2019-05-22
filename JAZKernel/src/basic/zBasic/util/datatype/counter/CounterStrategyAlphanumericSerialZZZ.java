package basic.zBasic.util.datatype.counter;

import java.util.ArrayList;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;

public class CounterStrategyAlphanumericSerialZZZ extends AbstractCounterStrategyAlphanumericZZZ{

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
			String sLetterMax = CounterByCharacterAscii_AlphanumericZZZ.getCharHighest(bLowerized);
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
		int iReturn = -1;
		
		main:{
			if(StringZZZ.isEmpty(sTotal)) break main;			
			
			String sLetterLast = StringZZZ.letterLast(sTotal);
			
			//Hier spielt links-/rechtsbündig KEINE Rolle:
//			boolean bRightAligned = this.isRightAligned();
//			if(!bRightAligned){
			
				//Berechnung...
				char c = sLetterLast.toCharArray()[0];
				int itemp = CounterByCharacterAscii_AlphanumericZZZ.getPositionForChar(c);
				if(sTotal.length()==1){					
					iReturn = itemp;				    					
				}else if(sTotal.length()>=2){
					iReturn = CounterByCharacterAscii_AlphanumericZZZ.iPOSITION_MAX * (sTotal.length()-1);															
					iReturn = iReturn + itemp;
				}
//			}else{
//				//Berechnung...
//				char c = sLetterLast.toCharArray()[0];
//				int itemp = CounterByCharacterAscii_AlphanumericZZZ.getPositionForChar(c);
//				if(sTotal.length()==1){					
//					iReturn = itemp;				    					
//				}else if(sTotal.length()>=2){
//					iReturn = CounterByCharacterAscii_AlphanumericZZZ.iPOSITION_MAX * (sTotal.length()-1);															
//					iReturn = iReturn + itemp;
//				}
//			}
			
			//Merke der umgekehrte Weg aus der Zahl einen String zu machen geht so:
			//Ermittle den "Teiler" und den Rest, Also Modulo - Operation
			//int iDiv = Math.abs(i / CounterByCharacterAscii_AlphanumericZZZ.iALPHANUMERIC_POSITION_MAX ); //durch abs wird also intern in ein Integer umgewandetl.... nicht nur das Weglassen des ggfs. negativen Vorzeichens.
			//int iMod = i % CounterByCharacterAscii_AlphanumericZZZ.iALPHANUMERIC_POSITION_MAX;
		}//end main;
		return iReturn;			
	}

	@Override
	public String computeStringForNumber(int iNumber) {
		String sReturn = null;
		main:{
			  //Ermittle den "Teiler" und den Rest, Also Modulo - Operation
			int iDiv = Math.abs(iNumber / CounterByCharacterAscii_AlphanumericZZZ.iPOSITION_MAX ); //durch abs wird also intern in ein Integer umgewandetl.... nicht nur das Weglassen des ggfs. negativen Vorzeichens.
			int iMod = iNumber % CounterByCharacterAscii_AlphanumericZZZ.iPOSITION_MAX;

			boolean bLowercase = this.isLowercase();
			
			ArrayList<String>listas=new ArrayList<String>();
				for(int icount = 1; icount <= iDiv; icount++){
					String stemp = CounterByCharacterAscii_AlphanumericZZZ.getCharForPosition(CounterByCharacterAscii_AlphanumericZZZ.iPOSITION_MAX, bLowercase);
					listas.add(stemp);
				}
				if(iMod>=1){
					String stemp = CounterByCharacterAscii_AlphanumericZZZ.getCharForPosition(iMod, bLowercase);
					listas.add(stemp);
				}

			
			//Zusammenfassen der Werte: Serial Strategie
			//Hier spielt links-/rechtsbündig eine Rolle:
			boolean bRightAligned = this.isRightAligned();
			if(!bRightAligned){
				for(int icount=1; icount <= listas.size(); icount++){
					String sPosition = listas.get(icount-1);
					if(sReturn==null){
						sReturn=sPosition;
					}else{
						sReturn+=sPosition;
					}
				}
			}else{
				for(int icount=listas.size(); icount >= 1; icount--){
					String sPosition = listas.get(icount-1);
					if(sReturn==null){
						sReturn=sPosition;
					}else{
						sReturn+=sPosition;
					}
				}
				
			}
				
		}//end main:
		return sReturn;
	}

}
