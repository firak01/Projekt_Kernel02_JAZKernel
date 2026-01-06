package basic.zBasic.rule;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import basic.zBasic.ExceptionZZZ;

public class GenericArrayMatcher {

    public static <K, V> V[] filter(
            K[] keys,
            V[] values,
            IMatchRuleZZZ<K, V> rule,
            Class<V> valueType) throws ExceptionZZZ {

        List<V> result = new ArrayList<V>();

        for (K key : keys) {
            for (V value : values) {
                if (rule.matches(key, value)) {
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

