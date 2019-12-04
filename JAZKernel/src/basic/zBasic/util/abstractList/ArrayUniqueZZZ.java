package basic.zBasic.util.abstractList;

public class ArrayUniqueZZZ {

    /**
     * Return true if number num is appeared only once in the
     * array num is unique.
     */
    public static boolean isUnique(Object[] array, Object obj) {
        for (int i = 0; i < array.length; i++) {
            if (array[i].equals(obj)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Convert the given array to an array with unique values
     * without duplicates and returns it.
     */
    public static Object[] toUniqueArray(Object[] array) {
        Object[] temp = new Object[array.length];
        for (int i = 0; i < temp.length; i++) {
            temp[i] = -1; // in case u have value of 0 in the array
        }

        int counter = 0;
        for (int i = 0; i < array.length; i++) {
            if (isUnique(temp, array[i]))
                temp[counter++] = array[i];
        }

        Object[] uniqueArray = new Object[counter];
        System.arraycopy(temp, 0, uniqueArray, 0, uniqueArray.length);
        return uniqueArray;
    }
    
    /**
     * Convert the given array to an array with unique values
     * without duplicates and returns it.
     */
    public static String[] toUniqueArrayString(Object[] array) {
        Object[] temp = new Object[array.length];
        for (int i = 0; i < temp.length; i++) {
            temp[i] = -1; // in case u have value of 0 in the array
        }

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
    public static void printlnArray(Object[] array) {
        for (int i = 0; i < array.length; i++) {
            System.out.println(array[i].toString() + " ");
        }        
    }
    
    /**
     * Print given array
     */
    public static void printArray(Object[] array, String sDelimiter) {
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i].toString() + sDelimiter);
        }        
    }
    
    
}
