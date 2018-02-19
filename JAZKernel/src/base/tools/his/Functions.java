package base.tools.his;

import static org.apache.commons.lang.StringUtils.lowerCase;
import static org.apache.commons.lang.StringUtils.upperCase;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
//import java.util.Objects; //erst ab Java 1.7

import org.apache.commons.lang.StringUtils;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import base.invariants.EnsureArgument;
import base.datatype.tuples.Pair;
import base.datatype.KeyEnum;

/**
 * Functions.
 * Functions with special arguments can be found in
 * appserver.service.model.common.EntityFunctions
 * appserver.service.dto.util.DtoFunctions
 * appserver.service.impl.common.message.MessageFunctions
 *
 * Company: HIS
 * @author hoersch
 * @version $Revision: 1.42 $
 */
public final class Functions {

    private Functions() {
        // utility class
    }

    /**
     * Returns a function which transforms a string to lower case. This function also
     * allows null string in which case null will be the result of the transformation.
     *
     * @return a function transforming a string to lower case
     */
    public static Function<String, String> stringToLowerCase() {
        return StringToLowerCaseFunction.INSTANCE;
    }

    private enum StringToLowerCaseFunction implements Function<String, String> {
        INSTANCE;
        @Override
        public String apply(String s) {
            return lowerCase(s);
        }
    
        @Override
        public String toString() {
            return "StringToLowerCase";
        }
    }

    /**
    * Returns a function which transforms a string to upper case. This function also
    * allows null string in which case null will be the result of the transformation.
    *
    * @return a function transforming a string to upper case
    */
    public static Function<String, String> stringToUpperCase() {
        return StringToUpperCaseFunction.INSTANCE;
    }

    private enum StringToUpperCaseFunction implements Function<String, String> {
        INSTANCE;
        @Override
        public String apply(String s) {
            return upperCase(s);
        }
    
        @Override
        public String toString() {
            return "StringToUpperCase";
        }
    }

    /**
     *
     * @return a function transforming a string to a Long
     */
    public static Function<String, Long> stringToLong() {
        return StringToLongFunction.INSTANCE;
    }

    private enum StringToLongFunction implements Function<String, Long> {
        INSTANCE;
        @Override
        public Long apply(String s) {
            return Long.valueOf(s);
        }
    
        @Override
        public String toString() {
            return "StringToLong";
        }
    }

    /**
     *
     * @return a function transforming a string to a Boolean
     */
    public static Function<String, Boolean> stringToBoolean() {
        return StringToBooleanFunction.INSTANCE;
    }

    private enum StringToBooleanFunction implements Function<String, Boolean> {
        INSTANCE;
        @Override
        public Boolean apply(String s) {
            EnsureArgument.notNull(s);
            return Boolean.valueOf(s);
        }
    
        @Override
        public String toString() {
            return "StringToBoolean";
        }
    }

    /**
     * Returns a function which extracts an element from an element array.
     * @param index     zero based index at which the element should be extracted; must be &gt;= 0
     *
     * @return the extraction function
     */       
    public static <T> Function<T[], T> elementAtIndex(final int index) {
    	//Erst ab Java 1.7 return new ElementAtIndexFunction<>(index);
    	return new ElementAtIndexFunction<T>(index);//FGL: Eclipse Vorschlag: Insert Inferred Type
    }

    private static final class ElementAtIndexFunction<T> implements Function<T[], T> {
        private final int index;
    
        ElementAtIndexFunction(int index) {
            this.index = index;
        }
    
        @Override
        public T apply(T[] input) {
            return input[this.index];
        }
    }

    /**
     * Returns a function applying {@link StringUtils#trimToNull(String)}
     * on the input.
     *
     * @return a function applying {@link StringUtils#trimToNull(String)}
     */
    public static Function<String, String> stringTrimToNull() {
        return StringTrimToNullFunction.INSTANCE;
    }

    private enum StringTrimToNullFunction implements Function<String, String> {
        INSTANCE;
    
        @Override
        public String apply(String input) {
            return StringUtils.trimToNull(input);
        }
    }

    /**
     * Returns a function applying {@link StringUtils#trimToEmpty(String)}
     * on the input.
     *
     * @return a function applying {@link StringUtils#trimToEmpty(String)}
     */
    public static Function<String, String> stringTrimToEmpty() {
        return StringTrimToEmptyFunction.INSTANCE;
    }

    private enum StringTrimToEmptyFunction implements Function<String, String> {
        INSTANCE;
    
        @Override
        public String apply(String input) {
            return StringUtils.trimToEmpty(input);
        }
    }

    /**
     *
     * @param <E>
     * @param enumClass
     * @return function to transform an enum constant to its name
     */
    public static <E extends Enum<E>> Function<E, String> toName(@SuppressWarnings("unused") 
Class<E> enumClass) {
        return new Function<E, String>() {
            @Override
            public String apply(E input) {
                return input.name();
            }
        };
    }

    /**
     * Transforms each entry of the given iterable with the given function.
     * <p>
     * Unlike to {@link Collections2#transform(java.util.Collection, Function)} this one creates a 
new {@link Serializable} list.
     * </p>
     * @param <T>
     * @param <F>
     * @param iterable
     * @param function
     * @return a new and filtered list
     */
    public static <T, F> List<T> transform(Iterable<F> iterable, Function<F, T> function) {
        return Lists.newArrayList(Iterables.transform(iterable, function));
    }


    /**
     * @param iterable
     * @param function
     * @return a map with the content of the iterable as keys and the result of the function as 
value
     */
    public static <T, F> Map<F,T> transformToMap(Iterable<F> iterable, Function<F, T> function) {
        EnsureArgument.notNull(iterable, "iterable must not be null");
        EnsureArgument.notNull(function, "function must not be null");

        Map<F, T> result = Maps.newLinkedHashMap();
        for (F entry : iterable) {
            result.put(entry, function.apply(entry));
        }
        return result;
    }

    /**
     * Transforms the source {@code map} to the target-map utilizing the given
     * {@code transformer}.
     *
     * <p>The number of elements for the source- and target-map are equal. Each element
     * of the target-map is the result of applying the {@code transformer} to the
     * respective element of the source-map.</p>
     *
     * <p>If the source-map has a fixed ordering the target map will have the same
     * ordering.</p>
     *
     * @param <K_F> the key-type of the source map
     * @param <V_F> the value-type of the source map
     * @param <K>   the key-type of the target map
     * @param <V>   the value-type of the target map
     *
     * @param map           the source map
     * @param transformer   the function transforming an entry of the source
     *                      map to a key-value-pair for the target map
     *
     * @return a new transformed map
     *
     * @throws IllegalArgumentException <ul>
     *                                      <li>if {@code map === null}</li>
     *                                      <li>if {@code transformer == null}</li>
     *                                  </ul>
     */
    
    //Erst ab Java 1.7
//    public static <K_F, V_F, K, V> Map<K, V> transform(Map<K_F, V_F> map, Function<Map.Entry<K_F, 
//V_F>, Pair<K, V>> transformer) {
//        EnsureArgument.notNull(map, "map must not be null");
//        EnsureArgument.notNull(transformer, "transformer must not be null");
//
//        Map<K, V> result = Maps.newLinkedHashMap();
//        for (Map.Entry<K_F, V_F> entry : map.entrySet()) {
//            Pair<K, V> transformed = transformer.apply(entry);
//            result.put(transformed.getKey(), transformed.getValue());
//        }
//
//        return result;
//    }

    /**
     * Reduce the given iterable of values to a single value.
     *
     * @param <F> the element type over which to iterate
     * @param <T> type of the reduced value
     * @param values elements to reduce
     * @param reduceFunction function to reduce the elements
     * @param initialValue the value to start with
     * @return the reduced value
     */
    
    //Erst ab Java 1.7
//    public static <F, T> T reduce(final Iterable<F> values, final ReduceFunction<? super F, T> 
//reduceFunction, T initialValue) {
//        EnsureArgument.notNull(values, "data");
//        EnsureArgument.notNull(reduceFunction, "reduceFunction");
//
//        T reducedValue = initialValue;
//        for (F elem : values) {
//            reducedValue = reduceFunction.apply(reducedValue, elem);
//        }
//
//        return reducedValue;
//    }

    /**
     * Calls 'toString' on each object.
     * @param objects
     * @return array of strings
     */
    public static String[] toStringArray(Object[] objects) {
        if (objects == null || objects.length == 0) {
            return new String[0];
        }

        String[] params = new String[objects.length];
        for (int i = 0; i < params.length; i++) {
            params[i] = objects[i] != null ? objects[i].toString() : null;
        }
        return params;
    }

    /**
     * Returns a function which applies {@code toString} to any object allowing null objects.
     * @param defaultValue
     * @return a function transforming any object to string
     */
    
    //erst ab Java 1.7
//    public static Function<Object, String> toStringAllowNull(final String defaultValue) {
//        return new NullSafeToStringFunction(defaultValue);
//    }

    //Erst ab Java 1.7
//    private static class NullSafeToStringFunction implements Function<Object, String> {
//        private final String defaultValue;
//    
//        NullSafeToStringFunction(String defaultValue) {
//            this.defaultValue = defaultValue;
//        }
//    
//        @Override
//        public String apply(Object input) {
//            return Objects.toString(input, defaultValue);
//        }
//    }

    /**
     * Returns a function that transforms input values to {@link Optional}s.
     *
     * @return a function transforming to {@link Optional}
     */
    public static final <T> Function<T, Optional<T>> toOptional() {
        return new Function<T, Optional<T>>() {
            @Override
            public Optional<T> apply(T input) {
                return Optional.fromNullable(input);
            }
        };
    }

    /**
     * Returns a function that transforms input values to {@link Boolean}s.
     *
     * @param predicate The {@link Predicate} which will be used by the function
     *
     * @return a function transforming to {@link Boolean}
     */
    public static final <T> Function<T, Boolean> toBoolean(final Predicate<T> predicate) {
        return new Function<T, Boolean>() {
            @Override
            public Boolean apply(T input) {
                return Boolean.valueOf(predicate.apply(input));
            }
        };
    }

    /**
     * Returns a function extracting the key.
     *
     * @return a function extracting the key
     */
    public static final <K extends KeyEnum<V>, V> Function<K, V> toKey() {
        return new Function<K, V>() {
            @Override
            public V apply(K input) {
                return input.getKey();
            }
        };
    }

    /**
     * Returns a function appending the {@code appendix} to each string.
     *
     * @param appendix  the value to append
     *
     * @return a function
     */
    public static final Function<String, String> append(final String appendix) {
        return new Function<String, String>() {
            @Override
            public String apply(String input) {
                return input + appendix;
            }
        };
    }

    /**
     * Returns a function that cast the given class into the target class.
     *
     * @param targetClass the class in which the given class should be casted.
     *
     * @return a function that cast the given class into the target class.
     */
    public static <S, T> Function<S, T> cast(final Class<T> targetClass) {
        return new Function<S, T>() {
            @Override
            public T apply(S input) {
                return targetClass.cast(input);
            }
        };
    }
}

