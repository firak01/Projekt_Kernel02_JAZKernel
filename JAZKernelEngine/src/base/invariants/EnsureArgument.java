package base.invariants;

import java.io.File;
import java.util.Collection;
import java.util.Map;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;

/**
 * <p>Class which ensures method invariants for the arguments.</p>
 *
 * <p>If the invariant conditions are violated an {@code IllegalArgumentException} will be 
thrown.<p>
 */
public final class EnsureArgument {

    private EnsureArgument() {
        // prevent instantiation
    }

    /**
     * Ensure that the argument is true,
     * throwing {@code IllegalArgumentException} if not
     *
     * @param value    the object checked to be true
     * @throws IllegalArgumentException
     */
    public static void isTrue(final boolean value) {
        if (!value) {
            // throw new EnsureArgumentException("isTrue", null);
            throw new IllegalArgumentException("EnsureArgument.isTrue() failed");
        }
    }

    /**
     * Ensure that the argument is true,
     * throwing {@code IllegalArgumentException} if not
     *
     * @param value    the value checked to be true
     * @param message   the custom message to be used in case of a violation
     * @throws IllegalArgumentException
     */
    public static void isTrue(final boolean value, final String message) {
        if (!value) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Ensure that the argument is true,
     * throwing {@code IllegalArgumentException} if not
     *
     * @param object    the object checked to be true
     * @throws IllegalArgumentException
     */
    public static void isTrue(final Boolean object) {
        notNull(object, "EnsureArgument.isTrue() failed");
        if (!Boolean.TRUE.equals(object)) {
            throw new IllegalArgumentException("EnsureArgument.isTrue() failed");
        }
    }

    /**
     * Ensure that the argument is true,
     * throwing {@code IllegalArgumentException} if not
     *
     * @param object    the object checked to be true
     * @param message   the custom message to be used in case of a violation
     * @throws IllegalArgumentException
     */
    public static void isTrue(final Boolean object, final String message) {
        if (!Boolean.TRUE.equals(object)) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Ensure that the argument is false,
     * throwing {@code IllegalArgumentException} if not
     *
     * @param value    the value checked to be false
     * @throws IllegalArgumentException when {@code value != false}
     */
    public static void isFalse(final boolean value) {
        if (value) {
            throw new IllegalArgumentException("EnsureArgument.isFalse() failed");
        }
    }

    /**
     * Ensure that the argument is false,
     * throwing {@code IllegalArgumentException} if not
     *
     * @param value    the value checked to be false
     * @param message   the custom message to be used in case of a violation
     * @throws IllegalArgumentException
     */
    public static void isFalse(final boolean value, final String message) {
        if (value) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Ensure that the argument is False,
     * throwing {@code IllegalArgumentException} if not
     *
     * @param object    the object checked to be False
     * @throws IllegalArgumentException
     */
    public static void isFalse(final Boolean object) {
        notNull(object, "EnsureArgument.isFalse() failed");
        if (!Boolean.FALSE.equals(object)) {
            throw new IllegalArgumentException("EnsureArgument.isFalse() failed");
        }
    }

    /**
     * Ensure that the argument is False,
     * throwing {@code IllegalArgumentException} if not
     *
     * @param object    the object checked to be False
     * @param message   the custom message to be used in case of a violation
     * @throws IllegalArgumentException
     */
    public static void isFalse(final Boolean object, final String message) {
        if (!Boolean.FALSE.equals(object)) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Ensure that the argument is not null,
     * returning the non-null object if so, and
     * throwing {@code IllegalArgumentException} if not
     *
     * @param object    the object checked to be non-null
     * @throws IllegalArgumentException
     * @returns the object guaranteed to be non-null
     */
    public static <T> T notNull(final T object) {
        if (object == null) {
            throw new IllegalArgumentException("EnsureArgument.notNull() failed");
        }
        return object;
    }

    /**
     * Ensure that the argument is not null,
     * returning the non-null object if so, and
     * throwing {@code IllegalArgumentException} if not
     *
     * @param object    the object checked to be non-null
     * @param message   the custom message to be used in case of a violation
     * @throws IllegalArgumentException
     * @returns the object guaranteed to be non-null
     */
    public static <T> T notNull(final T object, String message) {
        if (object == null) {
            throw new IllegalArgumentException(message);
        }
        return object;
    }

    @Deprecated
    public static void isNotNull(final Object object, final String message) {
        notNull(object, message);
    }

    /**
     * Ensure that the argument is null,
     * throwing {@code IllegalArgumentException} if not
     *
     * @param object    the object checked to be null
     * @throws IllegalArgumentException
     */
    public static void isNull(final Object object) {
        if (object != null) {
            throw new IllegalArgumentException("EnsureArgument.isNull() failed");
        }
    }

    /**
     * Ensure that the argument is null,
     * throwing {@code IllegalArgumentException} if not
     *
     * @param object    the object checked to be non-null
     * @param message   the custom message to be used in case of a violation
     * @throws IllegalArgumentException
     */
    public static void isNull(final Object object, final String message) {
        if (object != null) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Ensure that the string is not empty,
     * returning the non-empty string if so, and
     * throwing {@code IllegalArgumentException} if not
     *
     * @param string    the string checked to be non-empty
     * @throws IllegalArgumentException
     * @return the string guaranteed to be non-empty
     */
    public static String notEmpty(String string) {
        notNull(string, "EnsureArgument.notEmpty() failed, argument is null");
        if (string.isEmpty()) {
            throw new IllegalArgumentException("EnsureArgument.notEmpty() failed");
        }
        return string;
    }

    /**
     * Ensure that the string is not empty,
     * returning the non-empty string if so, and
     * throwing {@code IllegalArgumentException} if not
     *
     * @param string    the string checked to be non-empty
     * @param message   the custom message to be used in case of a violation
     * @throws IllegalArgumentException
     * @return the string guaranteed to be non-empty
     */
    public static String notEmpty(String string, String message) {
        notNull(string, message);
        if (string.isEmpty()) {
            throw new IllegalArgumentException(message);
        }
        return string;
    }

    /**
     * Ensure that the string is not blank,
     * returning the non-blank string if so, and
     * throwing {@code IllegalArgumentException} if not
     *
     * @param string    the string checked to be non-blank
     * @throws IllegalArgumentException
     * @return the string guaranteed to be non-empty
     */
    public static String notBlank(String string) {
        notNull(string, "EnsureArgument.isBlank() failed, argument is null");
        if (StringUtils.isBlank(string)) {
            throw new IllegalArgumentException("EnsureArgument.isBlank() failed");
        }
        return string;
    }

    /**
     * Ensure that the string is not blank,
     * returning the non-blank string if so, and
     * throwing {@code IllegalArgumentException} if not
     *
     * @param string    the string checked to be non-blank
     * @param message   the custom message to be used in case of a violation
     * @throws IllegalArgumentException
     * @return the string guaranteed to be non-empty
     */
    public static String notBlank(String string, String message) {
        notNull(string, message);
        if (StringUtils.isBlank(string)) {
            throw new IllegalArgumentException(message);
        }
        return string;

    }

    /**
     * Ensure that the collection is not empty,
     * returning the non-empty collection if so, and
     * throwing {@code IllegalArgumentException} if not
     *
     * @param collection the string checked to be non-empty
     * @throws IllegalArgumentException
     * @return the collection guaranteed to be non-empty
     */
    public static <T extends Collection<?>> T notEmpty(T collection) {
        notNull(collection, "EnsureArgument.isEmpty() failed, argument is null");
        if (collection.isEmpty()) {
            throw new IllegalArgumentException("EnsureArgument.isEmpty() failed");
        }
        return collection;
    }

    /**
     * Ensure that the collection is not empty,
     * returning the non-empty collection if so, and
     * throwing {@code IllegalArgumentException} if not
     *
     * @param collection the string checked to be non-empty
     * @param message    the custom message to be used in case of a violation
     * @throws IllegalArgumentException
     * @return the collection guaranteed to be non-empty
     */
    public static <T extends Collection<?>> T notEmpty(T collection, String message) {
        notNull(collection, message);
        if (collection.isEmpty()) {
            throw new IllegalArgumentException(message);
        }
        return collection;
    }

    /**
     * Ensure that the map is not empty,
     * returning the non-empty map if so, and
     * throwing {@code IllegalArgumentException} if not
     *
     * @param map the map checked to be non-empty
     * @throws IllegalArgumentException
     * @return the map guaranteed to be non-empty
     */
    public static <T extends Map<?, ?>> T notEmpty(T map) {
        notNull(map, "EnsureArgument.isEmpty() failed, argument is null");
        if (map.isEmpty()) {
            throw new IllegalArgumentException("EnsureArgument.isEmpty() failed");
        }
        return map;
    }

    /**
     * Ensure that the map is not empty,
     * returning the non-empty map if so, and
     * throwing {@code IllegalArgumentException} if not
     *
     * @param map the string checked to be non-empty
     * @param message    the custom message to be used in case of a violation
     * @throws IllegalArgumentException
     * @return the map guaranteed to be non-empty
     */
    public static <T extends Map<?, ?>> T notEmpty(T map, String message) {
        notNull(map, message);
        if (map.isEmpty()) {
            throw new IllegalArgumentException(message);
        }
        return map;
    }

    /**
     * Ensure that the array is not empty,
     * returning the non-empty array if so, and
     * throwing {@code IllegalArgumentException} if not
     *
     * @param array the array checked to be non-empty
     * @throws IllegalArgumentException
     * @return the array guaranteed to be non-empty
     */

    public static <T> T[] notEmpty(T[] array) {
        notNull(array, "EnsureArgument.isEmpty() failed, argument is null");
        if (array.length == 0) {
            throw new IllegalArgumentException("EnsureArgument.isEmpty() failed");
        }
        return array;
    }

    /**
     * Ensure that the array is not empty,
     * returning the non-empty array if so, and
     * throwing {@code IllegalArgumentException} if not
     *
     * @param array the string checked to be non-empty
     * @param message    the custom message to be used in case of a violation
     * @throws IllegalArgumentException
     * @return the array guaranteed to be non-empty
     */
    public static <T> T[] notEmpty(T[] array, String message) {
        notNull(array, message);
        if (array.length == 0) {
            throw new IllegalArgumentException(message);
        }
        return array;
    }

    public static void fileExists(final File file) {
        if (!file.exists()) {
            throw new IllegalArgumentException("File " + file.getName() + " exists not.");
        }
    }


    // the following methods are not (yet) in EnsureState


    /**
     * Validate an argument, throwing {@code IllegalArgumentException} if the argument collection is 
null
     * or has elements that are not of type clazz or a subclass.
     *
     * @param collection    the collection to be checked
     * @param clazz         the clazz each element of the collection should be the type of
     *
     * @throws IllegalArgumentException if {@code (collection == null)} or any collection element of 
is not of type
     *                                  {@code clazz}.
     */
    public static void allElementsOfType(final Collection<?> collection, final Class<?> clazz) {
        Validate.allElementsOfType(collection, clazz);
    }

    /**
     * Validate an argument, throwing {@code IllegalArgumentException} if the argument collection is 
null
     * or has elements that are not of type clazz or a subclass and using a custom message if the
     * constraint has been violated.
     *
     * @param collection    the collection to be checked
     * @param clazz         the clazz each element of the collection should be the type of
     * @param message       the custom message to be used in case of a violation
     */
    public static void allElementsOfType(final Collection<?> collection, final Class<?> clazz, final 
String message) {
        Validate.allElementsOfType(collection, clazz, message);
    }

    /**
     * Validates the given object, throwing {@code IllegalArgumentException} if it is null
     * or not of type clazz or a subclass and using a custom message if the constraint has been 
violated.
     * @param object
     * @param clazz
     * @param message
     */
    public static void isOfType(final Object object, final Class<?> clazz, final String message) {
        Validate.isTrue(clazz.isInstance(object), message);
    }



    /**
     * Validate an argument, throwing {@code IllegalArgumentException} if the test result is false 
and using a
     * custom message if the constraint has been violated.
     *
     * @param expression    the expression to be checked
     * @param message       the custom message to be used in case of a violation
     * @param value         the offending value
     */
    public static void isTrue(final boolean expression, final String message, final double value) {
        Validate.isTrue(expression, message, value);
    }

    /**
     * Validate an argument, throwing {@code IllegalArgumentException} if the test result is false 
and using a
     * custom message if the constraint has been violated.
     *
     * @param expression    the expression to be checked
     * @param message       the custom message to be used in case of a violation
     * @param value         the offending value
     */
    public static void isTrue(final boolean expression, final String message, final long value) {
        Validate.isTrue(expression, message, value);
    }

    /**
     * Validate an argument, throwing {@code IllegalArgumentException} if the test result is false 
and using a
     * custom message if the constraint has been violated.
     *
     * @param expression    the expression to be checked
     * @param message       the custom message to be used in case of a violation
     * @param value         the offending object
     */
    public static void isTrue(final boolean expression, final String message, final Object value) {
        Validate.isTrue(expression, message, value);
    }

    /**
     * Validate an argument, throwing {@code IllegalArgumentException} if the argument Collection 
has null elements or is null
     *
     * @param collection    the collection to be checked
     */
    public static void noNullElements(final Iterable<?> collection) {
        Validate.notNull(collection);
        for (Object each : collection) {
            Validate.notNull(each);
        }
    }

    /**
     * Validate an argument, throwing {@code IllegalArgumentException} if the argument Collection 
has null elements or is null
     * and using a custom message if the constraint has been violated.
     *
     * @param collection    the collection to be checked
     * @param message       the custom message to be used in case of a violation
     */
    public static void noNullElements(final Iterable<?> collection, final String message) {
        Validate.notNull(collection);
        for (Object each : collection) {
            Validate.notNull(each, message);
        }
    }

    /**
     * Validate an argument, throwing {@code IllegalArgumentException} if the argument array has 
null elements or is null.
     *
     * @param array         the array to be checked
     */
    public static void noNullElements(final Object[] array) {
        Validate.noNullElements(array);
    }

    /**
     * Validate an argument, throwing {@code IllegalArgumentException} if the argument array has 
null elements or is null
     * and using a custom message if the constraint has been violated.
     *
     * @param array         the array to be checked
     * @param message       the custom message to be used in case of a violation
     */
    public static void noNullElements(final Object[] array, final String message) {
        Validate.noNullElements(array, message);
    }

    /**
     * Validate an argument, throwing {@code IllegalArgumentException} if the argument map has null 
keys or values or is null
     * and using a custom message if the constraint has been violated.
     *
     * @param map        the map to be checked
     * @param message    the custom message to be used in case of a violation
     */
    public static void noNullElements(final Map<?, ?> map, final String message) {
        Validate.notNull(map);
        for (Map.Entry<?, ?> entry : map.entrySet()) {
            Validate.notNull(entry.getKey(), message);
            Validate.notNull(entry.getValue(), message);
        }
    }

    /**
     * Validate an argument, throwing {@code IllegalArgumentException} if the argument map has null 
keys or is null
     * and using a custom message if the constraint has been violated.
     *
     * @param map        the map to be checked
     * @param message    the custom message to be used in case of a violation
     */
    public static void noNullKeys(final Map<?, ?> map, final String message) {
        Validate.notNull(map);
        for (Map.Entry<?, ?> entry : map.entrySet()) {
            Validate.notNull(entry.getKey(), message);
        }
    }


    /**
     * Validate an argument, throwing {@code IllegalArgumentException} if the argument Collection is 
not of the expected size (or null)
     * and using a custom message if the constraint has been violated.
     *
     * @param collection    the collection to be checked
     * @param size          expected size
     * @param message       the custom message to be used in case of a violation
     */
    public static void hasSize(final Collection<?> collection, final int size, final String message) 
{
        Validate.notNull(collection);
        Validate.isTrue(collection.size() == size, message);
    }



    /**
     * Validate an argument, throwing {@code IllegalArgumentException} if the argument {@code 
elements} has null or blank elements
     * and using a custom message if the constraint has been violated.
     *
     * @param elements      the elements to be checked
     * @param message       the custom message to be used in case of a violation
     */
    public static void noBlankElements(final Iterable<String> elements, final String message) {
        Validate.notNull(elements);
        for (String element : elements) {
            notBlank(element, message);
        }
    }

    /**
     * Validate an argument, throwing {@code IllegalArgumentException} if the argument String is 
empty after trimming (null or zero length).
     *
     * @param string    the string to be checked
     */
    public static void hasText(final String string) {
        Validate.notEmpty(StringUtils.trim(string));
    }

    /**
     * Validate an argument, throwing {@code IllegalArgumentException} if the argument String is 
empty after trimming (null or zero length)
     * and using a custom message if the constraint has been violated.
     *
     * @param string    the string to be checked
     * @param message   the custom message to be used in case of a violation
     */
    public static void hasText(final String string, final String message) {
        Validate.notEmpty(StringUtils.trim(string), message);
    }


    /**
     * Validate an argument, throwing {@code IllegalArgumentException} if the argument is
     * <strong>not</strong> in the given range {@code start <= i <= end}. objectName is used to 
build a meaningful message.
     *
     * @param object
     * @param start
     * @param end
     * @param objectName
     */
    public static void isInRange(final int object, final int start, final int end, final String 
objectName) {
        Validate.isTrue(start <= object && object <= end,
                        objectName + " must be in range from '" + start + "' to '" + end + "'!");
    }

    /**
     * Validate an argument, throwing {@code IllegalArgumentException} if the argument is
     * <strong>not</strong> in the given range {@code start <= i <= end}. objectName is used to 
build a meaningful message.
     *
     * @param object
     * @param start
     * @param end
     * @param objectName
     * @return the given object
     */
    public static int checkRange(final int object, final int start, final int end, final String 
objectName) {
        isInRange(object, start, end, objectName);
        return object;
    }

    /**
     * Validate an argument, throwing {@code IllegalArgumentException} if the argument is
     * <strong>not</strong> in the given range {@code start <= i <= end}. objectName is used to 
build a meaningful message.
     *
     * @param object
     * @param start
     * @param end
     * @param objectName
     */
    public static void isInRange(final double object, final double start, final double end, final 
String objectName) {
        Validate.isTrue(start <= object && object <= end,
                        objectName + " must be in range from '" + start + "' to '" + end + "'!");
    }

    /**
     * Validate an argument, throwing {@code IllegalArgumentException} if the argument is
     * <strong>not</strong> one of the specified expected values
     * @param obj Object to check. May be null.
     * @param expected Valid values.
     */
    public static <T> void equalsAny(final T obj, final Collection<T> expected) {
        notEmpty(expected, "no expected objects specified");
        for (Object other : expected) {
            if (ObjectUtils.equals(obj, other)) {
                return;
            }
        }
        throw new IllegalArgumentException(String.format("got object '%s'; expected one of: %s", obj, StringUtils.join(expected, ", ")));
    }

}
