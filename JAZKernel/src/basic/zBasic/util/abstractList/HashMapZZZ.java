package basic.zBasic.util.abstractList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import basic.zBasic.AbstractObjectZZZ;
import basic.zBasic.ExceptionZZZ;

public class HashMapZZZ<T,X> extends AbstractObjectZZZ {
	
	private HashMapZZZ(){
	}
	
	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	/** Erstelle eine echte Kopie der HashMap und nicht nur einen Clone, bei dem die Referencen gleich bleiben.
	 *  siehe: https://stackoverflow.com/questions/28288546/how-to-copy-hashmap-not-shallow-copy-in-java
	 */
//	public static HashMap<String,MySpecialClass> copy(HashMap<String,MySpecialClass> hmOriginal){
//	    HashMap<String,MySpecialClass> copy = new HashMap<String, MySpecialClass>();
//	    for (Map.Entry<String, MySpecialClass> entry : hmOriginal.entrySet())
//	    {
//	        copy.put((entry).getKey(), entry.getValue()));
//	    }
//	    return copy;
//	}
	
	/** Erstelle eine echte Kopie der HashMap und nicht nur einen Clone, bei dem die Referencen gleich bleiben.
	 *  siehe: https://stackoverflow.com/questions/28288546/how-to-copy-hashmap-not-shallow-copy-in-java
	 */
	public static HashMap<String,Boolean> copy(HashMap<String,Boolean> hmOriginal){
	    HashMap<String,Boolean> copy = new HashMap<String, Boolean>();
	    for (Map.Entry<String, Boolean> entry : hmOriginal.entrySet())
	    {
	        copy.put((entry).getKey(), entry.getValue());
	    }
	    return copy;
	}
	
//	/** Erstelle eine echte Kopie der HashMap und nicht nur einen Clone, bei dem die Referencen gleich bleiben.
//	 *  siehe: https://stackoverflow.com/questions/28288546/how-to-copy-hashmap-not-shallow-copy-in-java
//	 */
	
	//TODOGOON: Das ist nicht im Einsatz, testen. 
	public static HashMap<Integer, List<?>> copyWithList(HashMap<Integer, List<?>> original){
	    HashMap<Integer, List<?>> copy = new HashMap<Integer, List<?>>();
	    for (Map.Entry<Integer, List<?>> entry : original.entrySet())
	    {
	        copy.put(entry.getKey(),
	           // Or whatever List implementation you'd like here.
	           new ArrayList<Object>(entry.getValue()));
	    }
	    return copy;
	}

//	public static HashMap<String, List<?>> copyWithList(HashMap<String, List<?>> original){
//    HashMap<String, List<?>> copy = new HashMap<String, List<?>>();
//    for (Map.Entry<String, List<?>> entry : original.entrySet())
//    {
//        copy.put(entry.getKey(),
//           // Or whatever List implementation you'd like here.
//           new ArrayList<Object>(entry.getValue()));
//    }
//    return copy;
// }
	
	
	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	/** Gehe einfach das KeySet durch und gib den ersten Eintrag zurueck.
	 * @param map
	 * @return
	 * @author Fritz Lindhauer, 10.05.2024, 09:15:46
	 */
	public static Object getKeyByIndex(Map<?,?> map, int iIndex) {
		Object objReturn = null;
		main:{					
			if(map==null)break main;
			
			Set<?> setKey = map.keySet();
			objReturn = SetUtilZZZ.getByIndex(setKey, iIndex);
		}
		return objReturn;
	}
	
	/** Gehe einfach das KeySet durch und gib den ersten Eintrag zurueck.
	 * @param map
	 * @return
	 * @author Fritz Lindhauer, 10.05.2024, 09:15:46
	 * @param <K>
	 * @param <V>
	 */
	public static <K, V> Object getEntryByIndex(Map<K, V> map, int iIndex) {
		Object objReturn = null;
		main:{					
			if(map==null)break main;
			if(iIndex < 0)break main;
			
			Set<Map.Entry<K, V>> setEntry = map.entrySet();
		    ////Nein, das wuerde eben StringKey=StringWert zurueckgeben   objReturn = SetZZZ.getByIndex(setEntry, iIndex);
			//Statt dessen: (siehe: https://stackoverflow.com/questions/46898/how-do-i-efficiently-iterate-over-each-entry-in-a-java-map
			int iCount = 0;			
			for(Map.Entry<K, V> entry : setEntry) {
				if(iCount == iIndex) {
					objReturn = entry.getValue();
					break;
				}
			}
			
		}
		return objReturn;
	}
	
	
	
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	
	/** Gehe einfach das KeySet durch und gib den ersten Eintrag zurueck.
	 * @param map
	 * @return
	 * @author Fritz Lindhauer, 10.05.2024, 09:15:46
	 */
	public static Object getKeyFirst(Map<?,?> map) {
		Object objReturn = null;
		main:{					
			if(map==null)break main;
			
			Set<?> setKey = map.keySet();
			objReturn = SetUtilZZZ.getFirst(setKey);
		}
		return objReturn;
	}
	
	/** Gehe einfach das KeySet durch und gib den ersten Eintrag zurueck.
	 * @param map
	 * @return
	 * @author Fritz Lindhauer, 10.05.2024, 09:15:46
	 */
	public static Object getKeyLast(Map<?,?> map) {
		Object objReturn = null;
		main:{					
			if(map==null)break main;
			
			Set<?> setKey = map.keySet();
			objReturn = SetUtilZZZ.getLast(setKey);
		}
		return objReturn;
	}
	
	//+++++++++++++++++++++++++++++++++++++++++++
	/** Gehe einfach das KeySet durch und gib den ersten Eintrag zurueck.
	 * @param map
	 * @return
	 * @author Fritz Lindhauer, 10.05.2024, 09:15:46
	 * @param <K>
	 * @param <V>
	 */
	public static <K, V> Object getEntryFirst(Map<K, V> map) {
		Object objReturn = null;
		main:{					
			if(map==null)break main;
			
			Set<Map.Entry<K, V>> setEntry = map.entrySet();
			////Nein, das wuerde eben StringKey=StringWert zurueckgeben objReturn = SetZZZ.getLast(setEntry); objReturn = SetZZZ.getFirst(setEntry);
			//Statt dessen: (siehe: https://stackoverflow.com/questions/46898/how-do-i-efficiently-iterate-over-each-entry-in-a-java-map
			for(Map.Entry<K, V> entry : setEntry) {
				objReturn = entry.getKey();
			}

			
		}
		return objReturn;
	}
	
	/** Gehe einfach das KeySet durch und gib den ersten Eintrag zurueck.
	 * @param map
	 * @return
	 * @author Fritz Lindhauer, 10.05.2024, 09:15:46
	 * @param <K>
	 * @param <V>
	 */
	public static <K, V> Object getEntryLast(Map<K,V> map) {
		Object objReturn = null;
		main:{					
			if(map==null)break main;
			
			Set<Map.Entry<K, V>> setEntry = map.entrySet();//!!! Also z.B. StringKey=StringWert
			//Nein, das wuerde eben StringKey=StringWert zurueckgeben objReturn = SetZZZ.getLast(setEntry);
			//Statt dessen: (siehe: https://stackoverflow.com/questions/46898/how-do-i-efficiently-iterate-over-each-entry-in-a-java-map
			for(Map.Entry<K, V> entry : setEntry) {
				objReturn = entry.getValue();
			}
		}
		return objReturn;
	}
	
	//+++++++++++++++++++++++++++++++++++++++++++
		
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
						
			List<Integer> listIntSorted = SetUtilZZZ.sortToInteger(setStrToBeSorted, iSortDirection);
			
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
			
			List<Integer> listIntSorted = SetUtilZZZ.sortToInteger(setStrToBeSorted);
			
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
			
			List<Integer> listIntSorted = SetUtilZZZ.sortToInteger(setIntToBeSorted);
					
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
