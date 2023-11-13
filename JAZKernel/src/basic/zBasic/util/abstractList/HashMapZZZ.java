package basic.zBasic.util.abstractList;

import java.io.File;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.AbstractObjectWithFlagZZZ;
import basic.zBasic.AbstractObjectZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;

public class HashMapZZZ<T,X> extends AbstractObjectZZZ {
	
	private HashMapZZZ(){
	}
		
	/**
	 * Merke: Eine normale HashMap ist NIE sortierbar.
	 * @param map
	 * @return
	 * @author Fritz Lindhauer, 26.02.2020, 17:50:30
	 * @throws ExceptionZZZ 
	 */
	public static HashMapIterableKeyZZZ<String, Object> sortByKeyAsInteger_StringObject(Map<String,Object> map, int iSortDirection) throws ExceptionZZZ{
		HashMapIterableKeyZZZ<String, Object>hmReturn=null;
		main:{					
			if(map==null)break main;
			
			//1. Hole das KeySet, als Liste sortiert
			hmReturn = new HashMapIterableKeyZZZ<String,Object>();//new HashMap<String, ?>() funktioniert dagegen nicht. ? ist zu unspezifisch;
			
			Set<String> setStrToBeSorted = map.keySet();			
			if(setStrToBeSorted.size()==0) break main;
						
			List<Integer> listIntSorted = SetZZZ.sortToInteger(setStrToBeSorted, iSortDirection);
			
			//2. Gehe die sortierte Liste durch, hole den Wert und füge alles der neuen Hashmap hinzu.
			for(Integer intSorted : listIntSorted) {
				String sKey = intSorted.toString();
				Object objValue = map.get(sKey);
				hmReturn.put(sKey, objValue);				
			}						
		}//end main:
		return hmReturn;
	}
	

	/**
	 * Merke: Eine normale HashMap ist NIE sortierbar.
	 * @param map
	 * @return
	 * @author Fritz Lindhauer, 26.02.2020, 17:50:30
	 * @throws ExceptionZZZ 
	 */
	public static HashMapIterableKeyZZZ<String, Object> sortByKeyAsInteger_StringString(Map<String,String> map) throws ExceptionZZZ{
		HashMapIterableKeyZZZ<String, Object>hmReturn=null;
		main:{					
			if(map==null)break main;
			
			//1. Hole das KeySet, als Liste sortiert
			hmReturn = new HashMapIterableKeyZZZ<String,Object>();//new HashMap<String, ?>() funktioniert dagegen nicht. ? ist zu unspezifisch;
			
			Set<String> setStrToBeSorted = map.keySet();			
			if(setStrToBeSorted.size()==0) break main;
			
			List<Integer> listIntSorted = SetZZZ.sortToInteger(setStrToBeSorted);
			
			//2. Gehe die sortierte Liste durch, hole den Wert und füge alles der neuen Hashmap hinzu.
			for(Integer intSorted : listIntSorted) {
				String sKey = intSorted.toString();
				String sValue = (String) map.get(sKey);
				hmReturn.put(sKey, sValue);				
			}						
		}//end main:
		return hmReturn;
	}
	
	
	/**
	 * Merke: Eine normale HashMap ist NIE sortierbar.
	 * @param map
	 * @return
	 * @author Fritz Lindhauer, 26.02.2020, 17:49:20
	 * @throws ExceptionZZZ 
	 */
	public static HashMapIterableKeyZZZ<Integer,Object> sortByKeyInteger(Map<Integer,Object> map) throws ExceptionZZZ {
		HashMapIterableKeyZZZ<Integer,Object>hmReturn=null;
		main:{
			if(map==null)break main;
			
			//1. Hole das KeySet, als Liste sortiert
			hmReturn = new HashMapIterableKeyZZZ<Integer,Object>();//new HashMap<String, ?>() funktioniert dagegen nicht. ? ist zu unspezifisch;
			
			Set<Integer> setIntToBeSorted = map.keySet();			
			if(setIntToBeSorted.size()==0) break main;
			
			List<Integer> listIntSorted = SetZZZ.sortToInteger(setIntToBeSorted);
					
			//2. Gehe die sortierte Liste durch, hole den Wert und füge alles der neuen Hashmap hinzu.
			for(Integer intSorted : listIntSorted) {
				Object objValue = map.get(intSorted);
				hmReturn.put(intSorted, objValue);				
			}						
		}//end main:
		return hmReturn;
	}
	
	/**Sortiere die Map. !!! Die Werte m�ssen vergleichbar sein!!!
	 *   http://stackoverflow.com/questions/109383/how-to-sort-a-mapkey-value-on-the-values-in-java
	* @param map
	* @return
	* 
	* lindhaueradmin; 22.05.2011 08:54:37
	 */
	public static void sortByKeyInteger_usingInnerComparator(Map<Integer,?> map) {
		
	     List<Integer> list = new LinkedList(map.keySet());
	     Collections.sort(list, new Comparator() {
	          public int compare(Object o1, Object o2) {
	        	  int iReturn = 0;
	        	  
	        	//  iReturn =( ((Map.Entry)o1).getValue()) .compareTo((Comparable)((Map.Entry)o2).getValue());
	        	Integer int1 = ((Integer) o1);
	        	Integer int2 = ((Integer) o2);
	        	iReturn = int1.compareTo(int2);
	      
	               return iReturn; 
	          }
	     });
	}

}
