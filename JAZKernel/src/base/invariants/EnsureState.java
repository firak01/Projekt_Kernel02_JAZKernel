package base.invariants;
//package de.his.core.base.invariants;


import java.io.File;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.lang.StringUtils;

/**
 * <p>Class which ensures application state.</p>
 *
 * <p>If the state is not as expected an {@code IllegalStateException} will be thrown.<p>
 *
 * @author mweyland
 * @version $Revision: 1.3 $
 */
public final class EnsureState {

    private EnsureState() {
        // prevent instantiation
    }

    /**
     * Ensure that the argument is true,
     * throwing {@code IllegalStateException} if not
     *
     * @param value    the object checked to be true
     * @throws IllegalStateException
     */
    public static void isTrue(final boolean value) {
        if (!value) {
            // throw new EnsureStateException("isTrue", null);
            throw new IllegalStateException("EnsureState.isTrue() failed");
        }
    }

    /**
     * Ensure that the argument is true,
     * throwing {@code IllegalStateException} if not
     *
     * @param value    the value checked to be true
     * @param message   the custom message to be used in case of a violation
     * @throws IllegalStateException
     */
    public static void isTrue(final boolean value, final String message) {
        if (!value) {
            throw new IllegalStateException(message);
        }
    }

    /**
     * Ensure that the argument is true,
     * throwing {@code IllegalStateException} if not
     *
     * @param object    the object checked to be true
     * @throws IllegalStateException
     */
    public static void isTrue(final Boolean object) {
        notNull(object, "EnsureState.isTrue() failed");
        if (!Boolean.TRUE.equals(object)) {
            throw new IllegalStateException("EnsureState.isTrue() failed");
        }
    }

    /**
     * Ensure that the argument is true,
     * throwing {@code IllegalStateException} if not
     *
     * @param object    the object checked to be true
     * @param message   the custom message to be used in case of a violation
     * @throws IllegalStateException
     */
    public static void isTrue(final Boolean object, final String message) {
        if (!Boolean.TRUE.equals(object)) {
            throw new IllegalStateException(message);
        }
    }

    /**
     * Ensure that the argument is false,
     * throwing {@code IllegalStateException} if not
     *
     * @param value    the value checked to be false
     * @throws IllegalStateException when {@code value != false}
     */
    public static void isFalse(final boolean value) {
        if (value) {
            throw new IllegalStateException("EnsureState.isFalse() failed");
        }
    }

    /**
     * Ensure that the argument is false,
     * throwing {@code IllegalStateException} if not
     *
     * @param value    the value checked to be false
     * @param message   the custom message to be used in case of a violation
     * @throws IllegalStateException
     */
    public static void isFalse(final boolean value, final String message) {
        if (value) {
            throw new IllegalStateException(message);
        }
    }

    /**
     * Ensure that the argument is False,
     * throwing {@code IllegalStateException} if not
     *
     * @param object    the object checked to be False
     * @throws IllegalStateException
     */
    public static void isFalse(final Boolean object) {
        notNull(object, "EnsureState.isFalse() failed");
        if (!Boolean.FALSE.equals(object)) {
            throw new IllegalStateException("EnsureState.isFalse() failed");
        }
    }

    /**
     * Ensure that the argument is False,
     * throwing {@code IllegalStateException} if not
     *
     * @param object    the object checked to be False
     * @param message   the custom message to be used in case of a violation
     * @throws IllegalStateException
     */
    public static void isFalse(final Boolean object, final String message) {
        if (!Boolean.FALSE.equals(object)) {
            throw new IllegalStateException(message);
        }
    }

    /**
     * Ensure that the argument is not null,
     * returning the non-null object if so, and
     * throwing {@code IllegalStateException} if not
     *
     * @param object    the object checked to be non-null
     * @throws IllegalStateException
     * @returns the object guaranteed to be non-null
     */
    public static <T> T notNull(final T object) {
        if (object == null) {
            throw new IllegalStateException("EnsureState.notNull() failed");
        }
        return object;
    }

    /**
     * Ensure that the argument is not null,
     * returning the non-null object if so, and
     * throwing {@code IllegalStateException} if not
     *
     * @param object    the object checked to be non-null
     * @param message   the custom message to be used in case of a violation
     * @throws IllegalStateException
     * @returns the object guaranteed to be non-null
     */
    public static <T> T notNull(final T object, String message) {
        if (object == null) {
            throw new IllegalStateException(message);
        }
        return object;
    }

    @Deprecated
    public static void isNotNull(final Object object, final String message) {
        notNull(object, message);
    }

    /**
     * Ensure that the argument is null,
     * throwing {@code IllegalStateException} if not
     *
     * @param object    the object checked to be null
     * @throws IllegalStateException
     */
    public static void isNull(final Object object) {
        if (object != null) {
            throw new IllegalStateException("EnsureState.isNull() failed");
        }
    }

    /**
     * Ensure that the argument is null,
     * throwing {@code IllegalStateException} if not
     *
     * @param object    the object checked to be non-null
     * @param message   the custom message to be used in case of a violation
     * @throws IllegalStateException
     */
    public static void isNull(final Object object, final String message) {
        if (object != null) {
            throw new IllegalStateException(message);
        }
    }

    /**
     * Ensure that two objects are equal, according to
     * their implementation of equals()
     *
     * @param a the first object
     * @param b the first object
     * @throws IllegalStateException
     */
    public static void isEqual(Object a, Object b) {
        if (!Objects.equals(a, b)) {
            throw new IllegalStateException("EnsureState.isEqual() failed: " + a + " does not equal " + b);
        }
    }

    /**
     * Ensure that two values are equal
     *
     * @param a the first value
     * @param b the first value
     * @throws IllegalStateException
     */
    public static void isEqual(int a, int b) {
        if (a != b) {
            throw new IllegalStateException("EnsureState.isEqual() failed: " + a + " == " + b + " does not hold");
        }
    }

    /**
     * Ensure that the string is not empty,
     * returning the non-empty string if so, and
     * throwing {@code IllegalStateException} if not
     *
     * @param string    the string checked to be non-empty
     * @throws IllegalStateException
     * @return the string guaranteed to be non-empty
     */
    public static String notEmpty(String string) {
        notNull(string, "EnsureState.notEmpty() failed, argument is null");
        if (string.isEmpty()) {
            throw new IllegalStateException("EnsureState.notEmpty() failed");
        }
        return string;
    }

    /**
     * Ensure that the string is not empty,
     * returning the non-empty string if so, and
     * throwing {@code IllegalStateException} if not
     *
     * @param string    the string checked to be non-empty
     * @param message   the custom message to be used in case of a violation
     * @throws IllegalStateException
     * @return the string guaranteed to be non-empty
     */
    public static String notEmpty(String string, String message) {
        notNull(string, message);
        if (string.isEmpty()) {
            throw new IllegalStateException(message);
        }
        return string;
    }

    /**
     * Ensure that the string is not blank,
     * returning the non-blank string if so, and
     * throwing {@code IllegalStateException} if not
     *
     * @param string    the string checked to be non-blank
     * @throws IllegalStateException
     * @return the string guaranteed to be non-empty
     */
    public static String notBlank(String string) {
        notNull(string, "EnsureState.isBlank() failed, argument is null");
        if (StringUtils.isBlank(string)) {
            throw new IllegalStateException("EnsureState.isBlank() failed");
        }
        return string;
    }

    /**
     * Ensure that the string is not blank,
     * returning the non-blank string if so, and
     * throwing {@code IllegalStateException} if not
     *
     * @param string    the string checked to be non-blank
     * @param message   the custom message to be used in case of a violation
     * @throws IllegalStateException
     * @return the string guaranteed to be non-empty
     */
    public static String notBlank(String string, String message) {
        notNull(string, message);
        if (StringUtils.isBlank(string)) {
            throw new IllegalStateException(message);
        }
        return string;

    }

    /**
     * Ensure that the collection is not empty,
     * returning the non-empty collection if so, and
     * throwing {@code IllegalStateException} if not
     *
     * @param collection the string checked to be non-empty
     * @throws IllegalStateException
     * @return the collection guaranteed to be non-empty
     */
    public static <T extends Collection<?>> T notEmpty(T collection) {
        notNull(collection, "EnsureState.isEmpty() failed, argument is null");
        if (collection.isEmpty()) {
            throw new IllegalStateException("EnsureState.isEmpty() failed");
        }
        return collection;
    }

    /**
     * Ensure that the collection is not empty,
     * returning the non-empty collection if so, and
     * throwing {@code IllegalStateException} if not
     *
     * @param collection the string checked to be non-empty
     * @param message    the custom message to be used in case of a violation
     * @throws IllegalStateException
     * @return the collection guaranteed to be non-empty
     */
    public static <T extends Collection<?>> T notEmpty(T collection, String message) {
        notNull(collection, message);
        if (collection.isEmpty()) {
            throw new IllegalStateException(message);
        }
        return collection;
    }

    /**
     * Ensure that the map is not empty,
     * returning the non-empty map if so, and
     * throwing {@code IllegalStateException} if not
     *
     * @param map the map checked to be non-empty
     * @throws IllegalStateException
     * @return the map guaranteed to be non-empty
     */
    public static <T extends Map<?, ?>> T notEmpty(T map) {
        notNull(map, "EnsureState.isEmpty() failed, argument is null");
        if (map.isEmpty()) {
            throw new IllegalStateException("EnsureState.isEmpty() failed");
        }
        return map;
    }

    /**
     * Ensure that the map is not empty,
     * returning the non-empty map if so, and
     * throwing {@code IllegalStateException} if not
     *
     * @param map the string checked to be non-empty
     * @param message    the custom message to be used in case of a violation
     * @throws IllegalStateException
     * @return the map guaranteed to be non-empty
     */
    public static <T extends Map<?, ?>> T notEmpty(T map, String message) {
        notNull(map, message);
        if (map.isEmpty()) {
            throw new IllegalStateException(message);
        }
        return map;
    }

    /**
     * Ensure that the array is not empty,
     * returning the non-empty array if so, and
     * throwing {@code IllegalStateException} if not
     *
     * @param array the array checked to be non-empty
     * @throws IllegalStateException
     * @return the array guaranteed to be non-empty
     */

    public static <T> T[] notEmpty(T[] array) {
        notNull(array, "EnsureState.isEmpty() failed, argument is null");
        if (array.length == 0) {
            throw new IllegalStateException("EnsureState.isEmpty() failed");
        }
        return array;
    }

    /**
     * Ensure that the array is not empty,
     * returning the non-empty array if so, and
     * throwing {@code IllegalStateException} if not
     *
     * @param array the string checked to be non-empty
     * @param message    the custom message to be used in case of a violation
     * @throws IllegalStateException
     * @return the array guaranteed to be non-empty
     */
    public static <T> T[] notEmpty(T[] array, String message) {
        notNull(array, message);
        if (array.length == 0) {
            throw new IllegalStateException(message);
        }
        return array;
    }

    public static void fileExists(final File file) {
        if (!file.exists()) {
            throw new IllegalStateException("File " + file.getName() + " exists not.");
        }
    }

    public static void isUnreachable() {
        isUnreachable("should not be reachable");
    }

    public static void isUnreachable(String message) {
        throw new IllegalStateException(message);
    }
}

