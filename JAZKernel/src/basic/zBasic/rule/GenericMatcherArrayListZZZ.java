package basic.zBasic.rule;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractList.ArrayListZZZ;

/** Unschöner Zwilling von GenericMatcherArrayList. 
 *  
 * unschön, weil eine Notlösung, weil: In statischen Methoden sind die Rückgabwerte halt nur durch einen anderen Methodennamen (wie filterZZZ) zu unterscheiden.
 * s. ChatGPT vom 2026-01-23 "Vererbung bei Interface"
 * 
 * Vorschlag ist statt static eine Instanzvariabel von ArrayListZZZ zu verwenden und darin dann .filter aufzurufen.
 * Ich mache aber noch etwas anderes: Ich baue GenericMatcherArrayListZZZ.
 *   
 * @author Fritz Lindhauer, 23.01.2026, 12:03:10
 * 
 */
public class GenericMatcherArrayListZZZ {

    public static <V, K> ArrayListZZZ<V> filter(
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

