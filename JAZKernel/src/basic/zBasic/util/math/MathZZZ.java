package basic.zBasic.util.math;

import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.datatype.character.CharZZZ;

/**
 * @author Lindhauer
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public abstract class MathZZZ {
	private MathZZZ(){
		//Nur zum "Verstecken" des Konstruktors
	}
	//+++++++++++++++++++++++++++++++++++++++++++
	public static int pow(int iBase, int iExpo){
		int iFunction = 0;
		function:{
			paramcheck:{
				if(iExpo == 0){
					iFunction = 1;
					break function;
				}
			}//end paramcheck
			
			iFunction = iBase * pow(iBase, iExpo - 1);
			
		}//end function
		return iFunction;
	}
	
	/**
	 * Gibt die 10er Potenz von <b>number</b> zur�ck.
	 * @param number
	 * @return 10er Potenz von number
	 */
	public static final int whichPowerOfTen (int number) {
		for (int k = 0; k < 10; k++) {
			if (Math.pow (10,k) > number) {
				return k-1;
			}
		}
		return 0;
	}
	
	public static int max(int i1, int i2){
		int iReturn = 0;
		main:{
			if( i1 > i2){
				iReturn = i1;
			}else{
				iReturn = i2;
			}
		}
		return iReturn;
	}
	
	public static int min(int i1, int i2){
		int iReturn = 0;
		main:{
			if( i1 < i2){
				iReturn = i1;
			}else{
				iReturn = i2;
			}
		}
		return iReturn;
	}
	
	public static int min(int... iValues){
		int iReturn = 0;
		main:{
			if(iValues==null) break main;
			if(iValues.length==0)break main;
			
			iReturn = iValues[0];
			for(int icount=0; icount <= iValues.length-1; icount++){
				if(iValues[icount]<iReturn) iReturn = iValues[icount];
			}
		}
		return iReturn;
	}
	
//	public static int min(int[] iaValue){
//		int iReturn = 0;
//		main:{
//			if(iaValue==null) break main;
//			if(iaValue.length==0)break main;
//			
//			iReturn = iaValue[0];
//			for(int icount=0; icount <= iaValue.length-1; icount++){
//				if(iaValue[icount]<iReturn) iReturn = iaValue[icount];
//			}
//		}
//		return iReturn;
//	}
	
	public static int max(int... iValues){
		int iReturn = 0;
		main:{
			if(iValues==null) break main;
			if(iValues.length==0)break main;
			
			iReturn = iValues[0];
			for(int icount=0; icount <= iValues.length-1; icount++){
				if(iValues[icount]>iReturn) iReturn = iValues[icount];
			}
		}
		return iReturn;
	}
	
	
//	public static int max(int[] iaValue){
//		int iReturn = 0;
//		main:{
//			if(iaValue==null) break main;
//			if(iaValue.length==0)break main;
//			
//			iReturn = iaValue[0];
//			for(int icount=0; icount <= iaValue.length-1; icount++){
//				if(iaValue[icount]>iReturn) iReturn = iaValue[icount];
//			}
//		}
//		return iReturn;
//	}
	
	/** Wenn man 5/2 teilt kommt normalerweise immer 2.0 raus.
	 *  diese Funktion setzt um: ttps://stackoverflow.com/questions/43300892/dividing-numbers
		Man muss also in double casten, damit 5/2 nicht 2.0 ergibt !!!
	 * @param i1
	 * @param i2
	 * @return
	 * @author Fritz Lindhauer, 04.11.2021, 13:07:13
	 */
	public static double divide(int i1, int i2) {
		double dReturn = 0;
		main:{
			double d1 = (double)i1;
			double d2 = (double)i2;
			
			dReturn = d1/d2;
		}
		return dReturn;
	}
	
	public static boolean isDivisibleWithoutRemainder(int iValue, int iDivisor) {
		boolean bReturn = false;
		main:{
			if(iDivisor==0)break main;
			
			int iRemainder = iValue%iDivisor;
			if(iRemainder==0) {
				bReturn = true;
			}
		}
		return bReturn;
	}
	
	/** True, wenn es sich um eine gerade Zahl handelt.
	* @param iValue
	* 
	* lindhaueradmin; 12.09.2008 10:43:02
	 */
	public static boolean  isEven(int iValue){
		boolean bReturn = false;
		main:{
			if(iValue%2==0){
				bReturn = true;
			}else{
				bReturn = false;
			}
		}
		return bReturn;
	}
	
	/** Rundet eine double Wert auf, der z.B. bei MathZZZ.divide(...) entstanden sein könnte.
	 * @param d
	 * @return
	 * @author Fritz Lindhauer, 04.11.2021, 13:06:04
	 */
	public static int roundUp(double d) {
		return (int) (d + 0.5);
	}
	
	/**"double wurzel = Math.sqrt(double zahl);" 
	 * @param dValue
	 * @return
	 * lindhaueradmin, 08.07.2013
	 */
	public static double square2(double dValue){
		return Math.sqrt(dValue);
	}
	public static double squareX(int iValue, int iX){
		return Math.sqrt(iValue^1/iX);
	}
	

	public static int sum(int... iValue) {
		int iReturn=0;
		main:{
			if(ArrayUtilZZZ.isNull(iValue))break main;
			
			for (int i : iValue) {
				iReturn += i;
			}
		}
		return iReturn;
	}
	
//	public static int sum(int[] ia) {
//		int iReturn=0;
//		main:{
//			if(ArrayUtilZZZ.isNull(ia))break main;
//			
//			for(int i : ia) {
//				iReturn = iReturn+i;
//			}
//		}//end main:
//		return iReturn;
//	}
}//end class
