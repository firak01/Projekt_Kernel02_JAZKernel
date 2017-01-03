package basic.zBasic.util.math;

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
	 * Gibt die 10er Potenz von <b>number</b> zurück.
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
	
	public static int min(int[] iaValue){
		int iReturn = 0;
		main:{
			if(iaValue==null) break main;
			if(iaValue.length==0)break main;
			
			iReturn = iaValue[0];
			for(int icount=0; icount <= iaValue.length-1; icount++){
				if(iaValue[icount]<iReturn) iReturn = iaValue[icount];
			}
		}
		return iReturn;
	}
	
	
	public static int max(int[] iaValue){
		int iReturn = 0;
		main:{
			if(iaValue==null) break main;
			if(iaValue.length==0)break main;
			
			iReturn = iaValue[0];
			for(int icount=0; icount <= iaValue.length-1; icount++){
				if(iaValue[icount]>iReturn) iReturn = iaValue[icount];
			}
		}
		return iReturn;
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
}//end class
