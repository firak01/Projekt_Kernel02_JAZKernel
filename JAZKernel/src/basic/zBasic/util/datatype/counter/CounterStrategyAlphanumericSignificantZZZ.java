package basic.zBasic.util.datatype.counter;

import java.util.ArrayList;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.datatype.doublevalue.DoubleZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.math.MathZZZ;

public class CounterStrategyAlphanumericSignificantZZZ extends AbstractCounterStrategyAlphanumericZZZ{

	@Override
	public boolean checkSyntax(String sTotal) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			if(StringZZZ.isEmpty(sTotal)) break main;			
			
			String sLetterLast = StringZZZ.letterLast(sTotal);						
			
			//C) Significant			   
			//C1) Überprüfe hinsichtlich der Groß-/Kleinschreibung. Die Zeichen müssen hier durchgängig groß oder klein sein.
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
			    	ExceptionZZZ ez = new ExceptionZZZ("SignificantStrategy: Alle Zeichen des ASCII-Zählers müssen gleich sein hinsichtlich der Groß-/Kleinschreibung. String='"+sTotal+"'.", iERROR_PARAMETER_VALUE, CounterByCharacterAscii_AlphanumericZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
					throw ez;					
			}
			
			//Hier spielt links-/rechtsbündig KEINE Rolle bzgl. der Gültigkeit:
//			boolean bRightAligned = this.isRightAligned();
//			if(!bRightAligned){
//			
//				//C2a) Die Zeichen links müssen immer das kleinste Zeichen des Zeichenraums sein.
//				String sLetterMin = CounterByCharacterAscii_AlphanumericZZZ.getCharLowest(bLowerized);
//				for (int icount=1;icount<=sTotal.length()-1;icount++){
//					String stemp = StringZZZ.letterAtPosition(sTotal,icount);
//					if(!sLetterMin.equals(stemp)){
//						ExceptionZZZ ez = new ExceptionZZZ("SignificantStrategy: Alle Zeichen links der rechtesten Stelle müssen das kleinste Zeichen sein (kleinstes Zeichen='"+sLetterMin+"'), String='"+sTotal+"'.", iERROR_PARAMETER_VALUE, CounterByCharacterAscii_AlphanumericZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
//						throw ez;	
//					}
//				}
//			}else{
//				//C2a) Die Zeichen links müssen immer das kleinste Zeichen des Zeichenraums sein.
//				String sLetterMin = CounterByCharacterAscii_AlphanumericZZZ.getCharLowest(bLowerized);
//				for (int icount=sTotal.length()-1;icount>=1;icount--){
//					String stemp = StringZZZ.letterAtPosition(sTotal,icount);
//					if(!sLetterMin.equals(stemp)){
//						ExceptionZZZ ez = new ExceptionZZZ("SerialStrategy: Alle Zeichen rechts der linksten Stelle müssen das kleinste Zeichen sein (kleinstes Zeichen='"+sLetterMin+"'), String='"+sTotal+"'.", iERROR_PARAMETER_VALUE, CounterByCharacterAscii_AlphanumericZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
//						throw ez;	
//					}
//				}
//			}
			
			bReturn = true;
		}//end main
		return bReturn;
		
	}

	@Override
	public int computeNumberForString(String sTotalUnnormed) {
		int iReturn = -1;
		
		//wg. der Problematik der führenden 0: Arbeite nur mit dahingehend normiertem String.
		boolean bLowercase = this.isLowercase();		
		String sAlphanumericNormed = this.getAlphanumericNormed(sTotalUnnormed); //StringZZZ.stripLeft(sTotalUnnormed, sCharToStrip); //Merke: Führende "0" Werte können nicht wiederhergestelllt werden, aus dem Zahlenwert.
		String sTotal = sAlphanumericNormed;
		
		main:{
			if(StringZZZ.isEmpty(sTotal)) break main;
			int iTotalLength=sTotal.length();
									
			//Hier spielt links-/rechtsbündig EINE Rolle:
			boolean bRightAligned = this.isRightAligned();
			if(!bRightAligned){
				//Hier spielt der Stellenwert eine Rolle.
				   for(int icount=0; icount<=sTotal.length()-1; icount++){
					   String stemp = StringZZZ.letterAtPosition(sTotal, icount);
					   
					    //Berechnung...
						char c = stemp.toCharArray()[0];
						int itemp = CounterByCharacterAscii_AlphanumericZZZ.getPositionForChar(c);
						itemp--;
						
						//Besonderheit, normalerweise darf eine führende 0 nicht sein, ausser man füllte auf: 
//						if(icount==0){
//							if(itemp==1){
//								itemp=0;
//							}
						
//						  if(icount==sTotal.length()-1 || iTotalLength<=1) itemp--;
						  
//						}
						
						int icountSignificant = sTotal.length()-1-icount;
						int icountSignificantValue = (int) Math.pow(CounterByCharacterAscii_AlphanumericZZZ.iPOSITION_MAX,icountSignificant);
						int itempSignificant = itemp*icountSignificantValue;			
						
						if(iReturn<=0){
					    	iReturn = itempSignificant;		    					
						}
						else{
							iReturn = iReturn + itempSignificant;
						}
				   }				   		
			
			}else{
				//Hier spielt der Stellenwert eine Rolle.
				   for(int icount=sTotal.length()-1; icount>=0; icount--){
					   String stemp = StringZZZ.letterAtPosition(sTotal, icount);
					   
					    //Berechnung...
						char c = stemp.toCharArray()[0];
						int itemp = CounterByCharacterAscii_AlphanumericZZZ.getPositionForChar(c);
						itemp--;
						
						//Besonderheit, normalerweise darf eine führende 0 (hier die rechstausgerichtet Variante) nicht sein, ausser man füllte auf: 
//						if(icount==sTotal.length()-1){
//							if(itemp==1){
//								itemp=0;
//							}
						
//						    if(icount==0 || iTotalLength<=1) itemp--;
						    
//						}
						
						
						int icountSignificant = icount;
						int icountSignificantValue = (int) Math.pow(CounterByCharacterAscii_AlphanumericZZZ.iPOSITION_MAX,icountSignificant);
						int itempSignificant = itemp*icountSignificantValue;	
						
						if(iReturn<=0){
					    	iReturn = itempSignificant;		    					
						}
						else{
							iReturn = iReturn + itempSignificant;
						}
				   }
						
			}
				
							
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
			ArrayList<String>listas=new ArrayList<String>();
			boolean bLowercase = this.isLowercase();
			
			int itemp; int iChar;String stemp;
			
			//Variante, basierend auf "Stellenwert"
			//1. Schritt: Teilen durch die Anzahl des "Zeichenraums"			
			int iRest = iNumber;
			int iDigitposition=1;
			int iPositionValue = 0;//-1 um "0" abzufangen.
			int iPositionValueMax;
			int iPositionValueCur;
			while(iRest >= iPositionValue){
				iPositionValue = MathZZZ.pow(CounterByCharacterAscii_AlphanumericZZZ.iPOSITION_MAX, iDigitposition);
				iPositionValueMax = iPositionValue * (CounterByCharacterAscii_AlphanumericZZZ.iPOSITION_MAX-1);
				//Hole jeweils die Werte hinter dem Komma			
				float fRest = (float)iRest / iPositionValue; //Merke: damit das Ergebnis der Division zweiter int Werte float wird: Casten. 								

				//Bestimme daraus den Wert des Zeichens								
				//1. Schritt: Den Rest um den gerade betrachteten Wert reduzieren						
				double dtemp = DoubleZZZ.pointRight(fRest);					
				double dtemp2 = (dtemp * CounterByCharacterAscii_AlphanumericZZZ.iPOSITION_MAX);				
				itemp = DoubleZZZ.toInt(dtemp2);//Merke: Das schneidet nur den Wert vor dem Komma ab: (int) (dtemp * CounterByCharacterAscii_AlphanumericZZZ.iPOSITION_MAX);
				iChar = itemp+1;//Merke: itemp++ erhöht nur itemp, nicht aber iChar an dieser Stelle.
					
				stemp = CounterByCharacterAscii_AlphanumericZZZ.getCharForPosition(iChar, bLowercase);
				listas.add(stemp);	
				
				iPositionValueCur = DoubleZZZ.pointLeft(fRest);
				if(iPositionValueCur <= CounterByCharacterAscii_AlphanumericZZZ.iPOSITION_MAX-1 && iPositionValueCur >= 1){
					iChar = iPositionValueCur+1;
					stemp = CounterByCharacterAscii_AlphanumericZZZ.getCharForPosition(iChar, bLowercase);
					listas.add(stemp);
					
					iRest = -1;
					
				}else{
			
					//Gehe zur nächsten Stelle										
					iRest = iPositionValueCur * iPositionValue;
					iDigitposition++;	
					iPositionValue = MathZZZ.pow(CounterByCharacterAscii_AlphanumericZZZ.iPOSITION_MAX, iDigitposition);
				}
																						
				
								
			}
			
			//Zusammenfassen der Werte: Serial Strategie
			//Hier spielt links-/rechtsbündig eine Rolle:
			boolean bRightAligned = this.isRightAligned();
			if(!bRightAligned){
				for(int icount=listas.size(); icount >= 1; icount--){
					String sPosition = listas.get(icount-1);
					if(sReturn==null){
						sReturn=sPosition;
					}else{
						sReturn+=sPosition;
					}
				}
			}else{
				for(int icount=1; icount <= listas.size(); icount++){
					String sPosition = listas.get(icount-1);
					if(sReturn==null){
						sReturn=sPosition;
					}else{
						sReturn+=sPosition;
					}
				}								
			}
			
			//###### alt...
			  //Ermittle den "Teiler" und den Rest, Also Modulo - Operation
//			int iDiv = Math.abs(iNumber / CounterByCharacterAscii_AlphanumericZZZ.iPOSITION_MAX ); //durch abs wird also intern in ein Integer umgewandetl.... nicht nur das Weglassen des ggfs. negativen Vorzeichens.
//			int iMod = iNumber % CounterByCharacterAscii_AlphanumericZZZ.iPOSITION_MAX;
//
//			
//			
//			
//			//TODO GOON 20190526
//			boolean bLowercase = this.isLowercase();
//			
//			ArrayList<String>listas=new ArrayList<String>();
//				for(int icount = 1; icount <= iDiv; icount++){
//					String stemp = CounterByCharacterAscii_AlphanumericZZZ.getCharForPosition(CounterByCharacterAscii_AlphanumericZZZ.iPOSITION_MIN, bLowercase);
//					listas.add(stemp);
//				}
//				if(iMod>=1){
//					//int iSignificant = 
//					String stemp = CounterByCharacterAscii_AlphanumericZZZ.getCharForPosition(iMod, bLowercase);
//					listas.add(stemp);
//				}

			
			
				
		}//end main:
		return sReturn;
	}

	 /**TODO: Das als Bestandteil eines eigenen Interfaces
	 * @param sAlphanumeric
	 * @return
	 * @author Fritz Lindhauer, 30.05.2019, 13:28:43
	 */
	public String getAlphanumericNormed(String sAlphanumeric){
		 String sReturn = null;
		 main:{
			 boolean bLowercase = this.isLowercase();
		 	String sCharToStrip = CounterByCharacterAscii_AlphanumericZZZ.getCharForPosition(CounterByCharacterAscii_AlphanumericZZZ.iPOSITION_MIN, bLowercase);
			if(!this.isRightAligned()){
				sReturn = StringZZZ.stripLeft(sAlphanumeric, sCharToStrip); //Merke: Führende "0" Werte können nicht wiederhergestelllt werden, aus dem Zahlenwert.
			}else{
				sReturn = StringZZZ.stripRight(sAlphanumeric, sCharToStrip); //Merke: Führende "0" Werte können nicht wiederhergestelllt werden, aus dem Zahlenwert.
			}
		 }//end main:
		 return sReturn;
	 }
}
