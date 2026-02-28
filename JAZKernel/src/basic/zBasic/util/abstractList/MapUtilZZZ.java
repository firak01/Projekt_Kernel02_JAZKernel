package basic.zBasic.util.abstractList;

import java.util.Map;

import basic.zBasic.ExceptionZZZ;

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
		Map<K,V[]> mReturn = null;
		main:{
			if(m1==null) {
				mReturn = m2;
				break main;
			}
			if(m2==null) {
				mReturn = m1;
				break main;
			}
			
			
			TODOGOON20260228;
			
		}//end main:
		return mReturn;
	}
}
