package base.datatype;
//package de.his.core.util;

import static org.apache.commons.lang.StringUtils.equalsIgnoreCase;

import java.util.HashSet;
import java.util.Set;

//import de.his.core.datatype.KeyEnum;


/**
 * Klasse, welche zu einem Key eines KeyEnums den entsprechenden Enum zurückliefert.
 * Falls der Enum nicht den angegebenen Key enthält wird eine Exception geworfen.
 */
public class KeyEnumHelper {
    /**
     * Returns all keys of an enumset of KeyEnums.
     * These can be easily defined with e.g. <code>EnumSet<ElementtypeValue> enumset = EnumSet.of(SomeKeyEnum.INSTANCE)</code>.
     *
     * @param <K> type of enums with HisKeyProvider functionality (required because class Enum can not be subclassed)
     * @param <T> type of key (usually Long or Integer)
     * @param enumset set of KeyEnum enums
     * @return set of his keys covered by the given set of enums
     */
    public static <K extends Enum<K> & KeyEnum<T>, T> Set<T> getKeySet(Set<? extends K> enumset) {
        Set<T> keys = new HashSet<T>();
        if (enumset != null) {
            for (KeyEnum<T> enumValue : enumset) {
                T key = enumValue != null ? enumValue.getKey() : null;
                if (key != null) {
                    keys.add(key);
                }
            }
        }
        return keys;
    }

    /**
     * Returns the enum for the given enum-key.
     *
     * @param <K> type of enums with HisKeyProvider functionality (required because class Enum can not be subclassed)
     * @param <T> type of key
     * @param enu the enum
     * @param key the enum-key
     *
     * @return the enum for the given enum-key.
     *
     * @throws IllegalArgumentException if the given enum has no instance with the given enum-key.
     */
    public static <K extends Enum<K> & KeyEnum<T>, T> K getKeyEnum(Class<K> enu, T key) {
        if (key == null) {
            return null;
        }

        for (K e : enu.getEnumConstants()) {
            if (e.getKey().equals(key)) {
                return e;
            }
        }
        throw new IllegalArgumentException("Supplied Enum type does not contain key ('" + key + "')!");
    }

    /**
     * Returns the enum for the given enum-key.
     *
     * <p>Unlike {@link #getKeyEnum(Class, Object)} this method will ignore the case
     * of the {@code key} provided.</p>
     *
     * @param <K> type of enums with HisKeyProvider functionality (required because class Enum can not be subclassed)
     * @param enu the enum
     * @param key the enum-key
     *
     * @return the enum for the given enum-key.
     *
     * @throws IllegalArgumentException if the given enum has no instance with the given enum-key.
     */
    public static <K extends Enum<K> & KeyEnum<String>> K getKeyEnumIgnoringCase(Class<K> enu, String key) {
        if (key == null) {
            return null;
        }

        for (K e : enu.getEnumConstants()) {
            if (equalsIgnoreCase(e.getKey(), key)) {
                return e;
            }
        }
        throw new IllegalArgumentException("Supplied Enum type does not contain key ('" + key + "')!");
    }
}

