package basic.zBasic.util.abstractArray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
			bReturn = ArrayUtilZZZ.isNull(objArray);
			if(bReturn) break main;
			
			for(int i = 0; i<=objArray.length-1;i++) {
				if(objArray[i]!=null) {
					break main;					
				}
			}
			bReturn = true;
		}//end main:
		return bReturn;
	}
	public static boolean isEmpty(boolean[] ba) {
		boolean bReturn = false;
		main:{
			bReturn = ArrayUtilZZZ.isNull(ba);
			if(bReturn) break main;
			
			for(int i = 0; i<=ba.length-1;i++) {
				if(Boolean.valueOf(ba[i])!=null) {
					break main;					
				}
			}
			bReturn = true;
		}//end main:
		return bReturn;
	}
	
	public static boolean isEmpty(int[] ia) {
		boolean bReturn = false;
		main:{
			bReturn = ArrayUtilZZZ.isNull(ia);
			if(bReturn) break main;
			
			for(int i = 0; i<=ia.length-1;i++) {
				if(Integer.valueOf(ia[i])!=null) {
					break main;					
				}
			}
			bReturn = true;
		}//end main:
		return bReturn;
	}
	public static boolean isEmpty(char[] ca) {
		boolean bReturn = false;
		main:{
			bReturn = ArrayUtilZZZ.isNull(ca);
			if(bReturn) break main;
			
			for(int i = 0; i<=ca.length-1;i++) {
				if(Character.valueOf(ca[i])!=null) {
					break main;					
				}
			}
			bReturn = true;
		}//end main:
		return bReturn;
	}
	
	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	public static <T> boolean isNull(T[] objArray) {
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
	public static boolean isNull(boolean[] ba) {
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
	
	public static boolean isNull(int[] ia) {
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
	public static boolean isNull(char[] ca) {
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
			boolean bEmptyArray1 = ArrayUtilZZZ.isNull(objArray1);
			boolean bEmptyArray2 = ArrayUtilZZZ.isNull(objArray2);
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
	
	/** s. https://sentry.io/answers/arraylist-from-array/
	 * 
	 * 
	 * @author Fritz Lindhauer, 16.03.2024, 14:50:47
	 */
	public static <T> ArrayList<? extends T> toArrayList(T[] objArray) {
		List<? extends T> listReturn = new ArrayList<T>(Arrays.asList(objArray));
		return (ArrayList<? extends T>) listReturn;
	}
}
