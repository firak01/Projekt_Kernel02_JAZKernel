package basic.zBasic.util.datatype.doublevalue;

public class DoubleZZZ {
	/** Gib den Integer Wert links neben dem Komma/Punkt zurück.
	 * @param d
	 * @return
	 */
	public static int pointLeft(double d){
		int iReturn = (int)d;////schneidet alles nach dem Komma ab
		return iReturn;
	}
	
	/** Gib den Double Wert rechts neben dem Komma/Punkt zurück.
	 * @param d
	 * @return
	 */
	public static double pointRight(double d){
		double dReturn = 0;
		main:{
			//Modulo 1....der Rest von x dividiert durch 1: also das nach dem Komma:
			dReturn = d%1;			
		}
		return dReturn;
	}
	
	public static int toInt(double d){
		//Merke: Einfaches casten würde nur den Wert vor dem Komma holen. also bei 10.9999 käme 10 heraus.
		//        Diesen Wert also einfach um +1, zum Aufrunden.	
		int iReturn = (int)d;	
		return iReturn;
	}
	
	public static int toIntRound(double d){
		//Merke: Einfaches casten würde nur den Wert vor dem Komma holen. also bei 10.9999 käme 10 heraus.
		//       Besser ist es daher zu runden.
		long ltemp = Math.round(d);
		int iReturn = (int)ltemp;
		return iReturn;
	}
	
	public static int toIntRoundUp(double d){
		//Merke: Einfaches casten würde nur den Wert vor dem Komma holen. also bei 10.9999 käme 10 heraus.
		//        Diesen Wert also einfach um +1, zum Aufrunden.	
		int iReturn = (int)d;
		iReturn = iReturn +1;
		return iReturn;
	}
	
	public static long toLong(double d){
		//Merke: Einfaches casten würde nur den Wert vor dem Komma holen. also bei 10.9999 käme 10 heraus.
		//       Besser ist es daher zu runden.
		long lReturn = Math.round(d);
		return lReturn;
	}
	
	/** Vergleiche zwei Double-Werte miteinander.
	 *  Laut https://howtodoinjava.com/java/basics/correctly-compare-float-double/
	 *  wird empfohlen dies mit einem Schwellenwert zu lösen
	 * @param d1
	 * @param d2
	 * @return
	 */
	public boolean equalByThreshold(double d1, double d2){
		boolean bReturn = false;
		main:{
			/*
			 final double THRESHOLD = .0001;
 
    //Method 1
    double f1 = .0;
    for (int i = 1; i <= 11; i++) {
        f1 += .1;
    }
 
    //Method 2
    double f2 = .1 * 11;
 
    System.out.println("f1 = " + f1);
    System.out.println("f2 = " + f2);
 
    if (Math.abs(f1 - f2) < THRESHOLD)
        System.out.println("f1 and f2 are equal using threshold\n");
    else
        System.out.println("f1 and f2 are not equal using threshold\n");
			  
			 */
		}
		return bReturn;		
	}
}
