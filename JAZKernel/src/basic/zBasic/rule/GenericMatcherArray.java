package basic.zBasic.rule;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import basic.zBasic.ExceptionZZZ;

public class GenericMatcherArray {

    public static <V, K> V[] filter(
    		V[] values,
            K[] keys,            
            IMatchRuleZZZ<V, K> rule,
            Class<V> valueType) throws ExceptionZZZ {

        List<V> result = new ArrayList<V>();

        for (K key : keys) {
            for (V value : values) {
                if (rule.matches(value, key)) {
                    if (!result.contains(value)) {
                        result.add(value);
                    }
                }
            }
        }

        @SuppressWarnings("unchecked")
        V[] array = (V[]) Array.newInstance(valueType, result.size());
        return result.toArray(array);
    }
}

