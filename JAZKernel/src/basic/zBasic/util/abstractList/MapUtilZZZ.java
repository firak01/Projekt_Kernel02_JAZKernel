package basic.zBasic.util.abstractList;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractArray.ArrayUtilZZZ;

public class MapUtilZZZ {
	
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
	
	
	
	
	public static <K,V> Map<K,V[]> mergeMapsAndJoinArrayValues(Map<K,V[]> m1, Map<K,V[]> m2) throws ExceptionZZZ{
		LinkedHashMap<K,V[]> mReturn = null;
		main:{
			if(m1==null) {
				mReturn = (LinkedHashMap<K, V[]>) m2;
				break main;
			}
			if(m2==null || m2.isEmpty()) {
				mReturn = (LinkedHashMap<K, V[]>) m1;
				break main;
			}
			mReturn = (LinkedHashMap<K, V[]>) new LinkedHashMap<K,V>();
			
			
			//1. Baue eine Arraylist Aller Schlüssel auf, ohne Duplikate
			ArrayListZZZ<K> listaK = new ArrayListZZZ<K>();
			
			Set<K> set1 = m1.keySet();
			Iterator<K> it1 = set1.iterator();
			while(it1.hasNext()) {
				K objKey1 = it1.next();
				listaK.addUnique(objKey1);
			}
			
			Set<K> set2 = m2.keySet();
			Iterator<K> it2 = set2.iterator();
			while(it2.hasNext()) {
				K objKey2 = it2.next();
				listaK.addUnique(objKey2);
			}
			
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
}
