package basic.zBasic.util.abstractArray;

import org.apache.commons.lang.ArrayUtils;

import base.util.abstractArray.ArrayUtil;

/** Erweitere nach Bedarf:
 *  base.util.abstractArray.ArrayUtil
 *  
 * @param <T> 
 * 
 * @author Fritz Lindhauer, 29.12.2022, 09:25:21
 * 
 */
public class ArrayUtilZZZ<T>{
	private ArrayUtilZZZ() {}//static methods only
	public static <T> boolean isEmpty(T[] objArray) {
		boolean bReturn = false;
		main:{
			if(objArray == null) {
				bReturn = true;
				break main;
			}
			
			if(objArray.length==0) {
				bReturn = true;
				break main;
			}
		}
		return bReturn;
	}
	public static boolean isEmpty(boolean[] ba) {
		boolean bReturn = false;
		main:{
			if(ba == null) {
				bReturn = true;
				break main;
			}
			
			if(ba.length==0) {
				bReturn = true;
				break main;
			}
		}
		return bReturn;
	}
	
	public static boolean isEmpty(int[] ia) {
		boolean bReturn = false;
		main:{
			if(ia == null) {
				bReturn = true;
				break main;
			}
			
			if(ia.length==0) {
				bReturn = true;
				break main;
			}
		}
		return bReturn;
	}
	public static boolean isEmpty(char[] ca) {
		boolean bReturn = false;
		main:{
			if(ca == null) {
				bReturn = true;
				break main;
			}
			
			if(ca.length==0) {
				bReturn = true;
				break main;
			}
		}
		return bReturn;
	}
	
	public static <T> T[] join(T[] objArray1,T[] objArray2) {
		T[] objaReturn=null;
		main:{
			boolean bEmptyArray1 = ArrayUtilZZZ.isEmpty(objArray1);
			boolean bEmptyArray2 = ArrayUtilZZZ.isEmpty(objArray2);
			if( bEmptyArray1 && bEmptyArray2) break main;
			
			if( bEmptyArray1 && !bEmptyArray2){
				objaReturn = objArray2;
				break main;
			}
			
			if( !bEmptyArray1 && bEmptyArray2){
				objaReturn = objArray1;
				break main;
			}
			
			//Aus Apache commons
			objaReturn = (T[]) ArrayUtils.addAll(objArray1, objArray2);			
		}//end main:
		return objaReturn;
	}
}
