package basic.zBasic.util.abstractArray;

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
}
