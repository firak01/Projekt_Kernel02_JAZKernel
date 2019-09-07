package basic.zBasic.util.counter;

import java.util.ArrayList;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.datatype.character.CharZZZ;
import basic.zBasic.util.datatype.doublevalue.DoubleZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.math.MathZZZ;

public class CounterStrategyAlphabetSignificantZZZ extends AbstractCounterStrategyAlphabetSignificantZZZ{
		
	public CounterStrategyAlphabetSignificantZZZ() throws ExceptionZZZ{
		super();
		this.makeReflectableInitialization();
	}
	
	public CounterStrategyAlphabetSignificantZZZ(int iLength, String sCounterFillingCharacter) throws ExceptionZZZ{
		super(iLength,sCounterFillingCharacter);
		this.makeReflectableInitialization();	
	}
	
	public CounterStrategyAlphabetSignificantZZZ(int iLength, String sCounterFillingCharacter, int iStartValue) throws ExceptionZZZ{
		super(iLength,sCounterFillingCharacter, iStartValue);
		this.initClassMethodCallingString();	
	}
	
	public CounterStrategyAlphabetSignificantZZZ(int iLength, String sCounterFillingCharacter, String sStartValue) throws ExceptionZZZ{
		super(iLength,sCounterFillingCharacter, sStartValue);
		this.makeReflectableInitialization();
	}

	
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
			    	ExceptionZZZ ez = new ExceptionZZZ("SignificantStrategy: Alle Zeichen des ASCII-Zählers müssen gleich sein hinsichtlich der Groß-/Kleinschreibung. String='"+sTotal+"'.", iERROR_PARAMETER_VALUE, CounterByCharacterAscii_AlphanumericSignificantZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
					throw ez;					
			}
			
			//Nur gültig ohne führende "0"
			//Allerdings "0" selbst darf übergeben werden. Man bekommt die führenden "0" Werte nämlich nicht durch den Zahlenwert wieder "rekonstruiert".
			//Merke: Ggfs. verwendete Füllzeichen wurden in diesem Überprüfungsschritt schon zuvor entfernt.
//			if(!sTotal.equals("0")){				
//				if(sTotal.startsWith("0") && !this.isRightAligned()){
//					ExceptionZZZ ez = new ExceptionZZZ("SignificantStrategy: Ein Wert mit führender '0' darf nicht übergeben werden - führende 0 läßt sich nicht wiederherstellen. Nur als einzelnes Zeichen ist führende '0' erlaubt. String='"+sTotal+"'.", iERROR_PARAMETER_VALUE, CounterByCharacterAscii_AlphanumericSignificantZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
//					throw ez;
//				}
//				if(sTotal.endsWith("0") && this.isRightAligned()){
//					ExceptionZZZ ez = new ExceptionZZZ("SignificantStrategy: Ein Wert mit führender '0' darf nicht übergeben werden - führende 0 läßt sich nicht wiederherstellen. Nur als einzelnes Zeichen ist führende '0' erlaubt. String='"+sTotal+"'.", iERROR_PARAMETER_VALUE, CounterByCharacterAscii_AlphanumericSignificantZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
//					throw ez;
//				}
//			}
			
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
		
		//wg. der Problematik der Füllzeichen. Arbeite nur mit dahingehend normiertem String. Also führende Füllzeichen wegtrimmen.		
		//String sFiller = CharZZZ.toString(this.getCounterFilling());
		//String sAlphanumericNormed = this.getAlphabetNormed(sTotalUnnormed, sFiller); //Merke: Führende "0" Werte können nicht wiederhergestelllt werden, aus dem Zahlenwert.
		String sAlphanumericNormed = this.getCounterStringNormed(sTotalUnnormed); //Merke: Führende "0" Werte können nicht wiederhergestelllt werden, aus dem Zahlenwert.
		String sTotal = sAlphanumericNormed;
		
		main:{
			if(StringZZZ.isEmpty(sTotal)) break main;
			int iTotalIndexLength=sTotal.length()-1;
									
			//Hier spielt links-/rechtsbündig eine Rolle:
			boolean bRightAligned = this.isRightAligned();
			if(bRightAligned){
				
				//Hier spielt der Stellenwert eine Rolle.				
			   for(int icount=0; icount<=iTotalIndexLength; icount++){
				   String stemp = StringZZZ.letterAtPosition(sTotal, icount);
				   
				    //Berechnung...
					char c = stemp.toCharArray()[0];
					int itemp = CounterByCharacterAscii_AlphabetZZZ.getPositionForChar(c);
					//itemp--; //es gibt keine 0
											
					int icountSignificant = icount; //sTotal.length()-1-icount;
					int icountSignificantValue = (int) Math.pow(CounterByCharacterAscii_AlphabetZZZ.iPOSITION_MAX,icountSignificant);//-1, es gibt keine 0
					int itempSignificant=0; 
					if(icountSignificant==0){
						itempSignificant = itemp*icountSignificantValue-1;//es gibt keine 0, darum -1			
					}else{
						itempSignificant = itemp*icountSignificantValue;
					}
					
					
					if(iReturn<0){
				    	iReturn = itempSignificant;	    					
					}
					else{
						iReturn = iReturn + itempSignificant;
					}					
			   }				   					
			}else{
				
				//Hier spielt der Stellenwert eine Rolle.
			   for(int icount=iTotalIndexLength; icount>=0; icount--){
				   String stemp = StringZZZ.letterAtPosition(sTotal, icount);
				   
				    //Berechnung...
					char c = stemp.toCharArray()[0];
					int itemp = CounterByCharacterAscii_AlphabetZZZ.getPositionForChar(c);
					//itemp--;
														
					int icountSignificant = sTotal.length()-icount-1; //
										
					int icountSignificantValue = (int) Math.pow(CounterByCharacterAscii_AlphabetZZZ.iPOSITION_MAX,icountSignificant);
					int itempSignificant=0;						
					if(icountSignificant==0){
						itempSignificant = itemp*icountSignificantValue-1;//es gibt keine 0, darum -1			
					}else{
						itempSignificant = itemp*icountSignificantValue;
					}		
					
					if(iReturn<0){
				    	iReturn = itempSignificant;		    					
					}
					else{
						iReturn = iReturn + itempSignificant;
					}
			   }
						
			}				
		}//end main;
		return iReturn;			
	}

	@Override
	public String computeStringForNumber(int iNumberIn) {
		//Variante, basierend auf "Stellenwert"
		
		//Analog zu: https://www.mathe-lexikon.at/mengenlehre/zahlensysteme/dezimalzahlen/dezimalzahl-in-hexadezimalzahl-umrechnen.html
		//Aber: Hier will ich nicht, dass die neue Stelle um +1 erhöht ist, wie z.B. auch im Dezimalsystem 0,..., 9 , 10,11,12
		//         Sondern A,...,Z, AA,AB,AC....
		String sReturn = null;
		main:{
			int itemp; int iChar;String stemp;
			
			ArrayList<String>listas=new ArrayList<String>();
			boolean bLowercase = this.isLowercase();
			boolean bRightAligned = this.isRightAligned();
			
			int iNumber = iNumberIn;
			int iRest = iNumber+1; //um "0" bei Anfangs-iNumber abzufangen.
			int iDigitpositionCur=0;		
			int iDigitPostionValueUsed = CounterByCharacterAscii_AlphabetZZZ.iPOSITION_MAX;

			//GGfs. für Debuggen, etwas Reflection
//			int iDigitpositionNext=iDigitpositionCur+1;
//			int iPositionValueCur;
//			boolean bWasIncreased=false;
//			boolean bWasReduced=true;
//			boolean bFinalDigitReached=false;
			
			boolean bDebug=false;			
//			if(iNumberIn==35777){
//				System.out.println("Fehler debuggen " + iNumberIn);
//				bDebug=true;
//			}

			while(iRest > 0){
				//1. Schritt: Teilen durch die Anzahl des "Zeichenraums"
				//Hole jeweils die Werte hinter dem Komma			
				float fRest = (float)iNumber / iDigitPostionValueUsed;  								

				//Bestimme daraus den Wert des Zeichens								
				//1. Schritt: Den Rest um den gerade betrachteten Wert reduzieren						
				double dtemp = DoubleZZZ.pointRight(fRest);					
				double dtemp2 = (dtemp * iDigitPostionValueUsed);	
				
				itemp  = DoubleZZZ.toIntRound(dtemp2);
				iChar = itemp+1;//wegen meiner Zeichenholerrei.
				iRest = DoubleZZZ.pointLeft(fRest);	//iNumber - (iPositionValueCur * iDigitPositionValueUsed);
				
				//Problem: Normalerweise ist dann bei eine Stellenübersprung die vordere Ziffer um +1 erhöht. Also 25 => Z , 26 würde zu BA.
				//              Ich will aber AA als Zählerwert erreichen.
				if(iDigitpositionCur>=1){
					if(bDebug) System.out.println(iDigitpositionCur + " . TEST " + iNumberIn + " - Charakter " + iChar + " reduziere wert um -1.");
					
					iChar=iChar-1;
					//bWasReduced = true;
					
					//Hier einen Übertrag quasi wieder rückgängig machen.
					if(iChar==0){
						iChar = CounterByCharacterAscii_AlphabetZZZ.iPOSITION_MAX;
						//bWasReduced=false;
					}
					
					if(DoubleZZZ.toInt(dtemp)==0 && DoubleZZZ.toInt(dtemp2)==0){
						iRest=iRest-1;//Damit ggfs. die Schleife verlassen, Ende.
					}
				}else{
					if(bDebug)  System.out.println(iDigitpositionCur + " . TEST " + iNumberIn + " - Charakter " + iChar + " übernehme Wert.");				
				}
				
				stemp = CounterByCharacterAscii_AlphabetZZZ.getCharForPosition(iChar, bLowercase);
				listas.add(stemp);	
				
				//Gehe zur nächsten Stelle
				iNumber = iRest;
				iDigitpositionCur++;
				//iDigitpositionNext=iDigitpositionCur+1;	
			}
			
			//Zusammenfassen der Werte: 
			//Hier spielt links-/rechtsbündig eine Rolle:
			if(bRightAligned){
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

	 /**TODO: Das als Bestandteil eines eigenen Interfaces
	 * @param sAlphabet
	 * @return
	 * @author Fritz Lindhauer, 30.05.2019, 13:28:43
	 */
	public String getAlphabetNormed(String sAlphabet, String sCharToStrip){
		 String sReturn = null;
		 main:{
			 boolean bLowercase = this.isLowercase();
		 	//String sCharToStrip = CounterByCharacterAscii_AlphabetZZZ.getCharForPosition(CounterByCharacterAscii_AlphabetZZZ.iPOSITION_MIN, bLowercase);
			if(!this.isRightAligned()){
				sReturn = StringZZZ.stripLeft(sAlphabet, sCharToStrip); //Merke: Führende "0" Werte können nicht wiederhergestelllt werden, aus dem Zahlenwert.
			}else{
				sReturn = StringZZZ.stripRight(sAlphabet, sCharToStrip); //Merke: Führende "0" Werte können nicht wiederhergestelllt werden, aus dem Zahlenwert.
			}
		 }//end main:
		 return sReturn;
	 }

	@Override
	public boolean makeReflectableInitialization() throws ExceptionZZZ {
		return this.initClassMethodCallingString();
	}



}
