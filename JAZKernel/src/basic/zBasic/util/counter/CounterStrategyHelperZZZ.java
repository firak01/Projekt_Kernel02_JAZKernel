package basic.zBasic.util.counter;

import java.util.ArrayList;

/** Enthält statische Methoden, die in mehreren CounterStrategy-Klassen verwendet werden.
 * @author lindhaueradmin, 10.09.2019, 07:19:24
 */
public class CounterStrategyHelperZZZ {

	/**Zusammenfassen der Werte: z.B. auch Multiple Strategie
	 * @param sCharacter
	 * @param iDiv
	 * @return
	 * @author lindhaueradmin, 10.09.2019, 07:40:41
	 */
	public static String getStringConsolitated(String sCharacter, int iDiv){
		String sReturn = "";
		main:{
			if(sCharacter==null) break main;
			
			//Zusammenfassen der Werte: Multiple Strategie
			for(int icount=1; icount <= iDiv; icount++){					
					sReturn+=sCharacter;
			}
		}//end main:
		return sReturn;		
	}

	/**Zusammenfassen der Werte: z.B. auch Serial Strategie
	   Hier spielt links-/rechtsbündig eine Rolle:
	 * @param listas
	 * @param bRightAligned
	 * @return
	 * @author lindhaueradmin, 10.09.2019, 07:21:53
	 */
	public static String getStringConsolidated(ArrayList<String>listas, boolean bRightAligned){
		String sReturn = null;
		main:{
			if(listas==null)break main;
			
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
}