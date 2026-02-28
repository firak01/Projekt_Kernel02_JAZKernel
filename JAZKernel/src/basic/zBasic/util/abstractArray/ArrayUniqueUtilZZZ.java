package basic.zBasic.util.abstractArray;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

public class ArrayUniqueUtilZZZ<T>{
	protected ArrayUniqueUtilZZZ() {}//static methods only
	
	
    /**
     * Return true if number num is appeared only once in the
     * array num is unique.
     */
    public static <T> boolean isUnique(T[] array, T obj) {
    	int iCounterContains=0;
        for (int i = 0; i < array.length; i++) {
            if (array[i].equals(obj)) {
            	iCounterContains++;
            	if(iCounterContains>=2) {
            		return false;
            	}
            }
        }
        return true;
    }

    /**
     * Convert the given array to an array with unique values
     * without duplicates and returns it.
     * 
     * s ChatGPT vom 2026-02-28 fuer die Umstellung auf Generics
     */
    public static <T> T[] toUniqueArray(T[] array) {
    	//Merke: 
        //In Java 1.7 kannst du kein generisches Array direkt mit new T[...] erzeugen, weil der konkrete Typ von T zur Laufzeit durch Type Erasure nicht mehr bekannt ist.
        //T[] uniqueArray = new T[counter];
      
        T[] temp = Arrays.copyOf(array, array.length); // korrektes T[] erzeugt
        int counter = 0;

        for (int i = 0; i < array.length; i++) {
            if (isUnique(temp, array[i])) {
                temp[counter++] = array[i];
            }
        }

        return Arrays.copyOf(temp, counter); // Array auf richtige Größe kürzen
    }
    
    /** s ChatGPT vom 2026-02-28
     * @param array
     * @return
     * @author Fritz Lindhauer, 28.02.2026, 17:07:45
     */
    public static <T> T[] toUniqueArrayBySet(T[] array) {

        Set<T> set = new LinkedHashSet<T>();
        for (T element : array) {
            set.add(element);
        }

        return set.toArray(Arrays.copyOf(array, set.size()));
    }
    
    /**
     * Convert the given array to an array with unique values
     * without duplicates and returns it.
     */
    public static <T> String[] toUniqueArrayString(T[] array) {
        //Object[] temp = new Object[array.length];
        
    	//Merke: 
        //In Java 1.7 kannst du kein generisches Array direkt mit new T[...] erzeugen, weil der konkrete Typ von T zur Laufzeit durch Type Erasure nicht mehr bekannt ist.
        //T[] uniqueArray = new T[counter];
      
        T[] temp = Arrays.copyOf(array, array.length); // korrektes T[] erzeugt
        
        int counter = 0;
        for (int i = 0; i < array.length; i++) {
            if (isUnique(temp, array[i]))
                temp[counter++] = array[i];
        }

        String[] uniqueArray = new String[counter];
        System.arraycopy(temp, 0, uniqueArray, 0, uniqueArray.length);
        return uniqueArray;
    }

    /**
     * Println given array
     */
    public static <T> void printlnArray(T[] array) {
        for (int i = 0; i < array.length; i++) {
            System.out.println(array[i].toString() + " ");
        }        
    }
    
    /**
     * Print given array
     */
    public static <T> void printArray(T[] array, String sDelimiter) {
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i].toString() + sDelimiter);
        }        
    }
    
    
}
