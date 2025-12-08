package basic.zBasic.util.abstractList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IOutputDebugNormedWithKeyZZZ;
import basic.zBasic.IOutputDebugNormedZZZ;
import basic.zBasic.util.datatype.string.StringArrayZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;

public class HashMapUtilZZZ {
	
	public static boolean isEmpty(HashMap hm) {
		boolean bReturn = false;
		main:{
			if(hm==null) {
				bReturn = true;
				break main;
			}
			if(hm.size()==0) {
				bReturn = true;
				break main;
			}
		}
		return bReturn;
	}
	
	//### fuer IOutputNormedZZ	
	
	//#############################################################################
	//### DEBUG HASHMAP-MULTI
	//#############################################################################	

	/** Aufbereitete Ausgabe der Daten als String, mit Zeilenumbruch fuer jeden neuen Eintrag.
	 * @param <T>
	 * @param <X>
	* @return
	* 
	* lindhauer; 08.08.2011 10:39:40
	 */				
	@SuppressWarnings("rawtypes")
	public static String computeDebugString(HashMapMultiZZZ hmDebug, String sKeyDelimiterIn, String sEntryDelimiterIn) throws ExceptionZZZ {
	    String sReturn = "";
	    main: {
	        // HashMapOuter durchgehen
	        if (hmDebug == null) break main;
	        if (hmDebug.size() == 0) break main;

	        String sEntryDelimiter;
	        if (sEntryDelimiterIn == null) {
	            sEntryDelimiter = IOutputDebugNormedZZZ.sDEBUG_ENTRY_DELIMITER_DEFAULT;
	        } else {
	            sEntryDelimiter = sEntryDelimiterIn;
	        }

	        String sKeyDelimiter;
	        if (sKeyDelimiterIn == null) {
	            sKeyDelimiter = IOutputDebugNormedWithKeyZZZ.sDEBUG_KEY_DELIMITER_DEFAULT;
	        } else {
	            sKeyDelimiter = sKeyDelimiterIn;
	        }

	        Set entrySetOuter = hmDebug.entrySet();
	        Iterator itOuter = entrySetOuter.iterator();
	        while (itOuter.hasNext()) {
	            if (!StringZZZ.isEmpty(sReturn)) {
	                sReturn = sReturn + sEntryDelimiter;
	            }

	            Map.Entry entryOuter = (Map.Entry) itOuter.next();
	            Object objOuterKey = entryOuter.getKey();
	            Object objOuterValue = entryOuter.getValue();

	            String sKeyOuter = String.valueOf(objOuterKey);
	            HashMap hmInner = (HashMap) objOuterValue;

	            // 20190801: HIER DEBUG FUNKTIONALITÄT VON HashMapExtendedZZZ verwenden.
	            String stemp = HashMapExtendedZZZ.computeDebugString(hmInner, sKeyDelimiter, sEntryDelimiter);
	            if (stemp != null) {
	                String[] saValue = StringZZZ.explode(stemp, sEntryDelimiter);
	                String[] saValueWithKey = StringArrayZZZ.plusString(saValue, sKeyOuter + sKeyDelimiter, "BEFORE");
	                sReturn = sReturn + StringArrayZZZ.implode(saValueWithKey, sEntryDelimiter);
	            } else {
	                sReturn = sReturn + sKeyOuter;
	            }
	        } // end while itOuter.hasNext()
	    } // end main
	    return sReturn;
	}
	
	
	/** Aufbereitete Ausgabe der Daten als String, mit Zeilenumbruch fuer jeden neuen Eintrag.
	 * @param <T>
	 * @param <X>
	* @return
	* 
	* lindhauer; 08.08.2011 10:39:40
	 */				
	@SuppressWarnings("rawtypes")
	public static String computeImplodeString(HashMapMultiZZZ hmDebug, String sKeyDelimiterIn, String sEntryDelimiterIn) throws ExceptionZZZ {
	    String sReturn = "";
	    main: {
	        // HashMapOuter durchgehen
	        if (hmDebug == null) break main;
	        if (hmDebug.size() == 0) break main;

	        String sEntryDelimiter;
	        if (sEntryDelimiterIn == null) {
	            sEntryDelimiter = IHashMapExtendedZZZ.sIMPLODE_ENTRY_DELIMITER_DEFAULT;
	        } else {
	            sEntryDelimiter = sEntryDelimiterIn;
	        }

	        String sKeyDelimiter;
	        if (sKeyDelimiterIn == null) {
	            sKeyDelimiter = IHashMapExtendedZZZ.sIMPLODE_KEY_DELIMITER_DEFAULT;
	        } else {
	            sKeyDelimiter = sKeyDelimiterIn;
	        }

	        Set entrySetOuter = hmDebug.entrySet();
	        Iterator itOuter = entrySetOuter.iterator();
	        while (itOuter.hasNext()) {
	            if (!StringZZZ.isEmpty(sReturn)) {
	                sReturn = sReturn + sEntryDelimiter;
	            }

	            Map.Entry entryOuter = (Map.Entry) itOuter.next();
	            Object objOuterKey = entryOuter.getKey();
	            Object objOuterValue = entryOuter.getValue();

	            String sKeyOuter = String.valueOf(objOuterKey);
	            HashMap hmInner = (HashMap) objOuterValue;

	            // 20190801: HIER DEBUG FUNKTIONALITÄT VON HashMapExtendedZZZ verwenden.
	            String stemp = HashMapExtendedZZZ.computeDebugString(hmInner, sKeyDelimiter, sEntryDelimiter);
	            if (stemp != null) {
	                String[] saValue = StringZZZ.explode(stemp, sEntryDelimiter);
	                String[] saValueWithKey = StringArrayZZZ.plusString(saValue, sKeyOuter + sKeyDelimiter, "BEFORE");
	                sReturn = sReturn + StringArrayZZZ.implode(saValueWithKey, sEntryDelimiter);
	            } else {
	                sReturn = sReturn + sKeyOuter;
	            }
	        } // end while itOuter.hasNext()
	    } // end main
	    return sReturn;
	}
	
	//#############################################################################
	//### DEBUG
	//#############################################################################	
	//================== PUBLIC API: HashMap ==================
	public static String computeDebugString(HashMap hmImplode) throws ExceptionZZZ {
		return computeDebugStringInternal(hmImplode, null, null);
	}

	public static String computeDebugString(HashMap hmImplode, String sEntryDelimiterIn) throws ExceptionZZZ {
		return computeDebugStringInternal(hmImplode, sEntryDelimiterIn, null);
	}

	public static String computeDebugString(HashMap hmImplode, String sEntryDelimiterIn, String sKeyDelimiterIn) throws ExceptionZZZ {
		return computeDebugStringInternal(hmImplode, sEntryDelimiterIn, sKeyDelimiterIn);
	}
	
	
	//================== PUBLIC API: LinkedHashMap ==================
	//Merke: Linked HashMap soll die Reihenfolge erhalten
	public static String computeDebugString(LinkedHashMap hmImplode) throws ExceptionZZZ {
		return computeDebugStringInternal(hmImplode, null, null);
	}

	public static String computeDebugString(LinkedHashMap hmImplode, String sEntryDelimiterIn) throws ExceptionZZZ {
		return computeDebugStringInternal(hmImplode, sEntryDelimiterIn, null);
	}

	public static String computeDebugString(LinkedHashMap hmImplode, String sEntryDelimiterIn, String sKeyDelimiterIn) throws ExceptionZZZ {
		return computeDebugStringInternal(hmImplode, sEntryDelimiterIn, sKeyDelimiterIn);
	}
	
	//================== PRIVATE GENERIC IMPLEMENTATION ==================
	@SuppressWarnings("rawtypes")
	private static String computeDebugStringInternal(Map hmImplode, String sEntryDelimiterIn, String sKeyDelimiterIn) throws ExceptionZZZ {	  
	    String sReturn = null;
	    main: {
	        if (hmImplode == null || hmImplode.size() == 0) break main;

	        String sKeyDelimiter = (sKeyDelimiterIn == null) 
		    		? IHashMapExtendedZZZ.sDEBUG_KEY_DELIMITER_DEFAULT 
		    		: sKeyDelimiterIn;

		    String sEntryDelimiter = (sEntryDelimiterIn == null) 
		    		? IHashMapExtendedZZZ.sDEBUG_ENTRY_DELIMITER_DEFAULT 
		    		: sEntryDelimiterIn;

	        Set setEntry = hmImplode.entrySet();   // raw usage beibehalten wg. Signaturen
	        Iterator it = setEntry.iterator();
	        while (it.hasNext()) {
	            Map.Entry entry = (Map.Entry) it.next();   // raw cast
	            Object objKey = entry.getKey();
	            Object objValue = entry.getValue();

	            String sPair = String.valueOf(objKey) + sKeyDelimiter + String.valueOf(objValue);

	            if (StringZZZ.isEmpty(sReturn)) {
	                sReturn = sPair;
	            } else {
	                sReturn = sReturn + sEntryDelimiter + sPair;
	            }
	        }
	    }
	    return sReturn;
	}
	
	
	//#############################################################################	
    //### IMPLODE
	//#############################################################################
	//================== PUBLIC API: HashMap ==================
	public static String computeImplodeString(HashMap hmImplode) throws ExceptionZZZ {
		return computeImplodeStringInternal(hmImplode, null, null);
	}

	public static String computeImplodeString(HashMap hmImplode, String sEntryDelimiterIn) throws ExceptionZZZ {
		return computeImplodeStringInternal(hmImplode, sEntryDelimiterIn, null);
	}

	public static String computeImplodeString(HashMap hmImplode, String sEntryDelimiterIn, String sKeyDelimiterIn) throws ExceptionZZZ {
		return computeImplodeStringInternal(hmImplode, sEntryDelimiterIn, sKeyDelimiterIn);
	}
	
	
	//================== PUBLIC API: LinkedHashMap ==================
	//Merke: Linked HashMap soll die Reihenfolge erhalten
	public static String computeImplodeString(LinkedHashMap hmImplode) throws ExceptionZZZ {
		return computeImplodeStringInternal(hmImplode, null, null);
	}

	public static String computeImplodeString(LinkedHashMap hmImplode, String sEntryDelimiterIn) throws ExceptionZZZ {
		return computeImplodeStringInternal(hmImplode, sEntryDelimiterIn, null);
	}

	public static String computeImplodeString(LinkedHashMap hmImplode, String sEntryDelimiterIn, String sKeyDelimiterIn) throws ExceptionZZZ {
		return computeImplodeStringInternal(hmImplode, sEntryDelimiterIn, sKeyDelimiterIn);
	}
	
	//================== PRIVATE GENERIC IMPLEMENTATION ==================
	@SuppressWarnings("rawtypes")
	private static String computeImplodeStringInternal(Map hmImplode, String sEntryDelimiterIn, String sKeyDelimiterIn) throws ExceptionZZZ {	    
	    String sReturn = null;
	    main: {
	        if (hmImplode == null || hmImplode.size() == 0) break main;

	        String sKeyDelimiter = (sKeyDelimiterIn == null) 
		    		? IHashMapExtendedZZZ.sIMPLODE_KEY_DELIMITER_DEFAULT 
		    		: sKeyDelimiterIn;

		    String sEntryDelimiter = (sEntryDelimiterIn == null) 
		    		? IHashMapExtendedZZZ.sIMPLODE_ENTRY_DELIMITER_DEFAULT 
		    		: sEntryDelimiterIn;

	        Set setEntry = hmImplode.entrySet();   // raw usage beibehalten wg. Signaturen
	        Iterator it = setEntry.iterator();
	        while (it.hasNext()) {
	            Map.Entry entry = (Map.Entry) it.next();   // raw cast
	            Object objKey = entry.getKey();
	            Object objValue = entry.getValue();

	            String sPair = String.valueOf(objKey) + sKeyDelimiter + String.valueOf(objValue);

	            if (StringZZZ.isEmpty(sReturn)) {
	                sReturn = sPair;
	            } else {
	                sReturn = sReturn + sEntryDelimiter + sPair;
	            }
	        }
	    }
	    return sReturn;
	}
	
	
	//#############################################################################
	public static String computeAsHashMapEntry(String sKey, String sValue) {
		String sReturn = null;
		main:{
			if(StringZZZ.isEmpty(sKey)) break main;
			
			sReturn = "{"+sKey+"="+sValue + "}";
		}//end main:
		return sReturn;
	}

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
