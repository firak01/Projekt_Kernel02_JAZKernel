package basic.zBasic.util.abstractList;

import java.io.File;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import basic.zBasic.ObjectZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;

public class HashMapZZZ<T,X> extends ObjectZZZ {

	/**
	 * Merke: Eine normale HashMap ist NIE sortierbar.
	 * @param map
	 * @return
	 * @author Fritz Lindhauer, 26.02.2020, 17:50:30
	 */
	public static HashMapIterableKeyZZZ<String, String> sortByKeyAsInteger(Map<String,String> map){
		HashMapIterableKeyZZZ<String, String>hmReturn=null;
		main:{					
			if(map==null)break main;
			
			//1. Hole das KeySet, als Liste sortiert
			hmReturn = new HashMapIterableKeyZZZ<String,String>();//new HashMap<String, ?>() funktioniert dagegen nicht. ? ist zu unspezifisch;
			
			Set<String> setStrToBeSorted = map.keySet();			
			if(setStrToBeSorted.size()==0) break main;
			
			Set<Integer> setIntToBeSorted = SetZZZ.toInteger(setStrToBeSorted);
			List<Integer> listIntSorted = SetZZZ.sortToIntegerReversed(setIntToBeSorted);
			
			//2. Gehe die sortierte Liste durch, hole den Wert und füge alles der neuen Hashmap hinzu.
			for(Integer intSorted : listIntSorted) {
				String sKey = intSorted.toString();
				String sValue = map.get(sKey);
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
	 */
	public static HashMapIterableKeyZZZ<Integer,String> sortByKeyInteger(Map<Integer,String> map) {
		HashMap<Integer,String>hmReturn=null;
		main:{
			if(map==null)break main;
			
			//1. Hole das KeySet, als Liste sortiert
			hmReturn = new HashMap<Integer,String>();//new HashMap<String, ?>() funktioniert dagegen nicht. ? ist zu unspezifisch;
			
			Set<Integer> setIntToBeSorted = map.keySet();			
			if(setIntToBeSorted.size()==0) break main;
			
			List<Integer> listIntSorted = SetZZZ.sortToInteger(setIntToBeSorted);
					
			//2. Gehe die sortierte Liste durch, hole den Wert und füge alles der neuen Hashmap hinzu.
			for(Integer intSorted : listIntSorted) {
				String sValue = map.get(intSorted);
				hmReturn.put(intSorted, sValue);				
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
