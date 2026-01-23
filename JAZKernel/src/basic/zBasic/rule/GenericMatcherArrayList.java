package basic.zBasic.rule;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractList.ArrayListZZZ;

public class GenericMatcherArrayList {

    public static <V, K> ArrayList<V> filter(
    		List<V> values,
            K[] keys,            
            IMatchRuleZZZ<V, K> rule
            ) throws ExceptionZZZ {

        ArrayList<V> result = null;
        if (keys == null || values == null || rule == null) {
            return result;
        }        
        result = new ArrayList<V>();
        
        
        for (K key : keys) {
            for (V value : values) {
                if (rule.matches(value, key)) {
                    // Duplikate vermeiden
                    if (!result.contains(value)) {
                        result.add(value);
                    }
                }
            }
        }

       return result;
    }
    
    
    /*unschön, aber in statischen Methoden sind die Rückgabwerte halt nur so zu unterscheiden.
     * s. ChatGPT vom 2026-01-23 "Vererbung bei Interface"
     * 
     * Vorschlag ist statt static eine Instanzvariabel von ArrayListZZZ zu verwenden und darin dann .filter aufzurufen.
     * Ich mache aber noch etwas anderes: Ich baue GenericMatcherArrayListZZZ.
     */
    public static <V, K> ArrayListZZZ<V> filterZZZ(
    		ArrayListZZZ<V> values,
            K[] keys,            
            IMatchRuleZZZ<V, K> rule
            ) throws ExceptionZZZ {

        ArrayListZZZ<V> result = null;
        if (keys == null || values == null || rule == null) {
            return result;
        }        
        result = new ArrayListZZZ<V>();
        
        
        for (K key : keys) {
            for (V value : values) {
                if (rule.matches(value, key)) {
                    // Duplikate vermeiden
                    if (!result.contains(value)) {
                        result.add(value);
                    }
                }
            }
        }

       return result;
    }
}

