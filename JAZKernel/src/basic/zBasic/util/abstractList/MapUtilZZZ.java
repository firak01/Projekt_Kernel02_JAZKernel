package basic.zBasic.util.abstractList;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IConstantZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractArray.ArrayUtilZZZ;

public class MapUtilZZZ implements IConstantZZZ{
	
	//Private Konstruktor, zum Verbergen, damit die Klasse nicht instanziiert werden kann.
	//Ist hier protected, damit HashMapUtilZZZ erben kann, wg. Fehlermeldung:
	//"Implicit super constructor MapUtilZZZ() is not visible for default constructor. Must define an explicit constructor"
	//Zudem: <K,V> nicht auf Klassenebene definieren, sondern damit das für static Methoden möglich ist, nur auf Methodenebene. 
	//       oder <?,?> verwenden.
	
	protected MapUtilZZZ() {}
	
	public static boolean isEmpty(Map<?,?> m) {
		boolean bReturn = false;
		main:{
			if(m==null) {
				bReturn = true;
				break main;
			}
			if(m.size()==0) {
				bReturn = true;
				break main;
			}
		}
		return bReturn;
	}
	
	
	//###################################################################################################################
	
	public static <K,V> Map<K,V[]> mergeMapsAndJoinArrayValues(Map<K,V[]> m1, Map<K,V[]> m2) throws ExceptionZZZ{
		return MapUtilZZZ.mergeMapsAndJoinArrayValues_(null, m1, m2);
	}
	
	public static <K,V> Map<K,V[]> mergeMapsAndJoinArrayValues(ArrayListZZZ<K> listaK, Map<K,V[]> m1, Map<K,V[]> m2) throws ExceptionZZZ{
		return MapUtilZZZ.mergeMapsAndJoinArrayValues_(listaK, m1, m2);
	}
	
	private static <K,V> Map<K,V[]> mergeMapsAndJoinArrayValues_(ArrayListZZZ<K> listaKIn, Map<K,V[]> m1, Map<K,V[]> m2) throws ExceptionZZZ{
		LinkedHashMap<K,V[]> mReturn = null;
		main:{
//			if(listaKIn == null) {
//				ExceptionZZZ ez = new ExceptionZZZ("ArrayListZZZ<K>", iERROR_PARAMETER_MISSING, MapUtilZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
//				throw ez;				
//			}
			
			ArrayListZZZ<K> listaK = new ArrayListZZZ<K>();
			if(listaKIn==null || listaKIn.size()==0) {//wenn es keine Sortierspalten gibt...
				if(m1==null) {
					mReturn = (LinkedHashMap<K, V[]>) m2;
					break main;
				}
				if(m2==null || m2.isEmpty()) {
					mReturn = (LinkedHashMap<K, V[]>) m1;
					break main;
				}
								
				//1. Baue eine Arraylist aller Schlüssel auf, ohne Duplikate
				listaK = MapUtilZZZ.mergeKeys(m1, m2);
			}else {
				listaK = listaKIn;
			}
			
			
			mReturn = (LinkedHashMap<K, V[]>) new LinkedHashMap<K,V>();
			
			//2. Gehe alle Keys durch, hole die Werte und joine diese ggfs.
			for(K objKey : listaK) {
				
				//Wert erste Hashmap
				V[] objaValue1 = m1.get(objKey);
				if(objaValue1!=null) {
					
					//pruefe, ob die andere map den gleichen key hat.
					V[] objaValue2 = m2.get(objKey);
					if(objaValue2!=null) {
						
						V[]objaValueReturn = ArrayUtilZZZ.joinUnique(objaValue1, objaValue2);
						mReturn.put(objKey, objaValueReturn);
					}else {
						mReturn.put(objKey, objaValue1);
					}
				}else {
					//pruefe, ob die andere map den gleichen key hat.
					V[] objaValue2 = m2.get(objKey);
					if(objaValue2!=null) {
						mReturn.put(objKey, objaValue2);
					}	//da objaValue1 null ist, eruebrigt sich der Rest				
				}
			}
		}//end main:
		return mReturn;
	}
	
	
	//#################################################
	public static <K,V> ArrayListZZZ<K> mergeKeys(Map<K,V[]> m1) throws ExceptionZZZ {
		return MapUtilZZZ.mergeKeys(m1, null);
	}
	
	public static <K,V> ArrayListZZZ<K> mergeKeys(Map<K,V[]> m1, Map<K,V[]> m2) throws ExceptionZZZ {
		return MapUtilZZZ.mergeKeys(m1, m2);
	}
	
	
	private static <K,V> ArrayListZZZ<K> mergeKeys_(Map<K,V[]> m1, Map<K,V[]> m2) throws ExceptionZZZ {
		ArrayListZZZ<K> listaReturn = null;
		main:{
			//0. Falls nur 1x HashMap, dann ist das fuer jeden Fall deren KeySet
			if(m1==null) {
				Set<K> set2 = m2.keySet();
				Iterator<K> it2 = set2.iterator();
				while(it2.hasNext()) {
					K objKey2 = it2.next();
					listaReturn.addUnique(objKey2);
				}
				break main;
			}
			if(m2==null || m2.isEmpty()) {
				Set<K> set1 = m1.keySet();
				Iterator<K> it1 = set1.iterator();
				while(it1.hasNext()) {
					K objKey1 = it1.next();
					listaReturn.addUnique(objKey1);
				}
				break main;
			}
						
			//1. Baue eine Arraylist aller Schlüssel auf, ohne Duplikate
			Set<K> set1 = m1.keySet();
			Iterator<K> it1 = set1.iterator();
			while(it1.hasNext()) {
				K objKey1 = it1.next();
				listaReturn.addUnique(objKey1);
			}
			
			Set<K> set2 = m2.keySet();
			Iterator<K> it2 = set2.iterator();
			while(it2.hasNext()) {
				K objKey2 = it2.next();
				listaReturn.addUnique(objKey2);
			}
		}//end main:
		return listaReturn;
	}
}