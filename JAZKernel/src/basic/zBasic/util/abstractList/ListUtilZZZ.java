package basic.zBasic.util.abstractList;

import java.util.ArrayList;
import java.util.List;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IConstantZZZ;

public class ListUtilZZZ implements IConstantZZZ{

	//Private Konstruktor, zum Verbergen, damit die Klasse nicht instanziiert werden kann.
	//Ist hier protected, damit ListUtilZZZ erben kann, wg. Fehlermeldung:
	//"Implicit super constructor ListUtilZZZ() is not visible for default constructor. Must define an explicit constructor"
	//Zudem: <K,V> nicht auf Klassenebene definieren, sondern damit das für static Methoden möglich ist, nur auf Methodenebene. 
	//       oder <?,?> verwenden.
	
	protected ListUtilZZZ() {}
	
	
	/**
	 * @param lista
	 * @return
	 * 
	 * https://stackoverflow.com/questions/10766492/what-is-the-simplest-way-to-reverse-an-arraylist
	 */
//	public static <T> List<T> reverse(final List<T> list) {
//    final int size = list.size();
//    final int last = size - 1;
//
//    // create a new list, with exactly enough initial capacity to hold the (reversed) list
//    final List<T> result = new ArrayList<>(size);
//
//    // iterate through the list in reverse order and append to the result
//    for (int i = last; i >= 0; --i) {
//        final T element = list.get(i);
//        result.add(element);
//    }
//
//    // result now holds a reversed copy of the original list
//    return result;
//}
	public static <T> List<T> reverse(List<T> lista) throws ExceptionZZZ {
	    List<T> listaReturn = null; 
		main:{
			if(lista==null) break main;
			if(lista.size()==0) break main;
			
			
			
			// create a new list, with exactly enough initial capacity to hold the (reversed) list
			final int size = lista.size();
			final int last = size - 1;
			
		    listaReturn = new ArrayList<>(size);
		    
		    // iterate through the list in reverse order and append to the result
		    for (int i = last; i >= 0; --i) {
		        final T element = lista.get(i);
		        listaReturn.add(element);
		    }
		}//end main	
	    return listaReturn;
	}
}
