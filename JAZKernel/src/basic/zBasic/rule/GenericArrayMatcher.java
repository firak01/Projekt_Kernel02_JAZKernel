package basic.zBasic.rule;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class GenericArrayMatcher {

    public static <K, V> V[] filter(
            K[] keys,
            V[] values,
            IMatchRuleZZZ<K, V> rule,
            Class<V> valueType) {

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

