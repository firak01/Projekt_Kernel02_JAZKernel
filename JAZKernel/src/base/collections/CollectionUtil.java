package base.collections;
//package de.his.core.base.collections;

import static com.google.common.collect.Sets.newLinkedHashSet;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.log4j.Logger;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

//import de.his.core.base.invariants.EnsureArgument;
import base.invariants.EnsureArgument;

/**
 * Klasse mit Hilfsmethoden für Collections.
 * @author D.Scholz
 */
public class CollectionUtil {
    private static Logger logger = Logger.getLogger(CollectionUtil.class);

    /**
     * Converts an iterable of unknown objects to a typed list of the given type.
     *
     * @param <T>   the type of the expected typed list
     *
     * @param iterable      the iterable to be converted; can be {@code null}
     * @param type          the type the target list elements should have
     *
     * @return the converted collection of elements as list or {@code null} if iterable is {@code null}
     *
     * @throws ClassCastException if the element of the given list cannot be cast to the given type
     */
    // FIXME REFACTOR This should not be necessary. Fix the generic types of the callers!!
    public static <T> List<T> asTypedList(Iterable<?> iterable, Class<T> type) {
        if (iterable == null) {
            return null;
        }
        List<T> typedList = Lists.newArrayListWithCapacity(Iterables.size(iterable));
        for (Object element : iterable) {
            typedList.add(type.cast(element));
        }
        // FIXME nachdem die Elemente gecasted sind, braucht es keine checkedList mehr!
        return Collections.checkedList(typedList, type);
    }

    /**
     * Removes null entries from the given collection.
     * @param col
     */
    public static void removeNullObjs(Collection<?> col) {
        for (Iterator<?> it = col.iterator(); it.hasNext();) {
            if (it.next() == null) {
                it.remove();
            }
        }
    }

    /**
     * Merges the elements of collections in one result-list.
     *
     * <p>Caveat: if an element is contained in more than one collection it'll be several
     * times in the output list.</p>
     *
     * @param <K>                       the type of the elements
     * @param first           the first of the collections to merge
     * @param second      the other collection
     *
     * @return a list containing all elements from the input collections
     */
    public static <K> List<K> mergeCollections(Collection<K> first, Collection<K> second) {
        //FGL: Erst ab Java 1.7 List<K> result = new ArrayList<>();
    	List<K> result = new ArrayList<K>();//FGL: Eclipse Vorschlag: Insert Inferred Type
        if (CollectionUtils.isNotEmpty(first)) {
            result.addAll(first);
        }
        if (CollectionUtils.isNotEmpty(second)) {
            result.addAll(second);
        }
        return result;
    }

    /**
     * Adds all given elements to the passed collection.
     * @param <T>
     * @param collection
     * @param elements
     * @return the given collection
     */
    @SafeVarargs
    public static <T> Collection<T> addAll(Collection<T> collection, T... elements) {
        for (T elem : elements) {
            collection.add(elem);
        }
        return collection;
    }

    /**
     * Cast the iterable if possible, otherwise create a new collection.
     * @param collection
     * @return a collection
     */
    public static <T> Collection<T> asCollection(Iterable<T> collection) {
        if (collection instanceof Collection) {
            return (Collection<T>) collection;
        }
        return Lists.newArrayList(collection);
    }

    /**
     * Cast the iterable if possible, otherwise create a new list.
     * @param collection
     * @return a list
     */
    public static <T> List<T> asList(Iterable<T> collection) {
        if (collection instanceof List) {
            return (List<T>) collection;
        }
        return Lists.newArrayList(collection);
    }

    /**
     * Adds all given elements to the passed array.
     * @param <T>
     * @param array
     * @param elements
     * @return a new array with all elements
     */
    @SafeVarargs
    public static <T> T[] joinedArray(T[] array, T... elements) {
        EnsureArgument.notNull(array, "array");
        List<T> result = Lists.newArrayList(array);
        addAll(result, elements);
        return result.toArray(array);
    }

    /**
     * Create a copy of the given list sorted according to the given comparator.
     * @param list original list
     * @param cmp comparator
     * @return the copied and sorted list
     */
    public static <T> List<T> sort(List<T> list, Comparator<T> cmp) {
        List<T> result = Lists.newArrayList(list);
        Collections.sort(result, cmp);
        return result;
    }

    /**
     * Removes duplicates from an iterable.
     *
     * <p>The original of the elements order will be kept.</p>
     *
     * @param <T>       the type of the iterable
     * @param iterable  the iterable
     *
     * @return a list without duplicates
     */
    public static <T> List<T> removeDuplicateElements(Iterable<T> iterable) {
        return Lists.newArrayList(newLinkedHashSet(iterable));
    }

    /**
     * Add an element to a Collection, if the element is not null.
     * @param coll
     * @param elem
     * @return true if the Collection changed
     */
    public static <T> boolean addIfNotNull(Collection<T> coll, T elem) {
        return elem != null && coll.add(elem);
    }

    /**
     * Liefert eine Teil-Collection zurück, ähnlich wie die subString()-Funktion.
     * Ist effizienter für Listen als für allgemeine Collections.
     *
     *
     *
     * @param collection
     * @param start
     * @param count
     * @return Sub-Collection oder null
     */
    public static <T> Collection<T> subCollection(Collection<T> collection, int start, int count) {
        if (collection == null) {
            return collection;
        }
        if (start == 0 && collection.size() <= count) {
            // the whole list is requested
            return collection;
        }
        if (collection instanceof List) {
            //FGL Erst ab java 1.7: List<T> result = new ArrayList<>(count);
        	List<T> result = new ArrayList<T>(count); //Eclipse Vorschlag "insert inferred type argument"
            List<T> list = (List<T>) collection;
            // in Java 1.8, we can use list.sublist(start, end)
            for (int i = start; i < list.size() && i < start + count; i++) {
                result.add(list.get(i));
            }
            return result;
        }
        //FGL: Erst ab Java 1.7 List<T> result = new ArrayList<>(count);
        List<T> result = new ArrayList<T>(count);//Eclipse Vorschlag "insert inferred type argument"
        int i = 0;
        for (T o : collection) {
            if (i < start) {
                continue;
            }
            if (i >= start + count) {
                break;
            }
            result.add(o);
            i++;
        }
        return result;
    }

    /**
     * Creates a singleton list for a non-null parameter
     * or an empty list for a null parameter.
     *
     * @param object or null
     * @return List mit dem übergebenen object
     */
    public static <T> List<T> singletonList(T object) {
    	 //FGL: Erst ab Java 1.7  ArrayList<T> result = new ArrayList<>();        
        ArrayList<T> result = new ArrayList<T>();//Eclipse Vorschlag "insert inferred type argument"
        if (object != null) {
            result.add(object);
        }
        return result;
    }

    /**
     * Erzeugen eines String-Arrays aus einem Set mit Strings.
     *
     * @param set  HashSet mit Strings
     * @return    String-Array
     */
    public static String[] setToArray(Set<String> set) {
        if (set != null) {
            return set.toArray(new String[set.size()]);
        }
        return null;
    }

    /**
     * Returns the size of the biggest list in the collection.
     * @param lists
     * @return size of biggest list
     */
    public static <E, T extends Collection<E>> int maximumSize(Collection<T> lists) {
        int max = 0;
        for (final T list : lists) {
            final int size = list.size();
            if (size > max) {
                max = size;
            }
        }
        return max;
    }

    /**
     * Checks if a given collection or map has more than zero elements.
     *
     * @param anObject                     a collection or map
     * @return                             <code>true</code> if the collection or map is not empty. <code>false</code> otherwise.
     * @throws IllegalArgumentException    if the given object is no collection or map.<br />
     *                                     {@code ! (anObject instanceOf java.util.Collection) && ! (anObject instanceOf java.util.Map)}
     */
    public static boolean hasElements(Object anObject) {
        if (anObject == null) {
            logger.trace("Supplied parameter was null. Returning 0 as size");
            return false;
        }
        if (anObject instanceof Object[]) {
            return ((Object[]) anObject).length > 0;
        }
        if (anObject instanceof Collection) {
            return !((Collection<?>) anObject).isEmpty();
        }
        if (anObject instanceof Map) {
            return !((Map<?, ?>) anObject).isEmpty();
        }
        if (anObject instanceof Iterator) {
            return ((Iterator<?>) anObject).hasNext();
        }
        throw new IllegalArgumentException("The supplied argument is not of a supported type. Supplied type was '" + anObject.getClass().getCanonicalName() + "'");
    }

    /**
     * Checks if a collection contains a given element.
     *
     * @param collection   the collection to be checked
     * @param anObject     the object
     *
     * @return             <code>true</code> if the collection contains the object; <code>false</code> otherwise
     */
    public static boolean collectionContains(Object collection, Object anObject) {
        if (collection == null) {
            CollectionUtil.logger.trace("Supplied collection was null. Returning false");
            return false;
        }
        if (collection instanceof Collection<?>) {
            return ((Collection<?>) collection).contains(anObject);
        }
        if (collection instanceof Object[]) {
            return ArrayUtils.contains((Object[]) collection, anObject);
        }
        CollectionUtil.logger.trace("Supplied collection is not supported, must be either a collection or an array. Returning false");
        return false;
    }

    /**
     * Gibt die Anzahl der Einträge einer <code>Collection</code>, eines
     * Objekt-Arrays oder eines Iterators zurück. Falls der übergebene Parameter
     * nicht vom Typ <code>java.util.Collection</code>,
     * <code>java.lang.Object[]</code> oder <code>java.util.Iterator</code>
     * ist, wird eine <code>IllegalArgumentException</code> geworfen.
     *
     * @param anObject
     *            Objekt, dessen Größe ermittelt werden soll.
     * @return Größe des Objekts. <code>0</code> falls <code>null</code>
     *         übergeben wurde.
     * @throws IllegalArgumentException
     */
    public static int sizeOf(Object anObject) throws IllegalArgumentException {
        if (anObject == null) {
            CollectionUtil.logger.trace("Supplied parameter was null. Returning 0 as size");
            return 0;
        }
        if (anObject instanceof Object[]) {
            return ((Object[]) anObject).length;
        }
        if (anObject instanceof Collection) {
            return ((Collection<?>) anObject).size();
        }
        if (anObject instanceof Map) {
            return ((Map<?, ?>) anObject).size();
        }
        EnsureArgument.isFalse(anObject instanceof Iterator, "sizeof() cannot be implemented for iterators!");
        throw new IllegalArgumentException("The supplied argument is not of a supported type. Supplied type was '" + anObject.getClass().getCanonicalName() + "'");
    }

    /**
     * @param values List<object>
     * @return the original List with doubles removed
     */
    public static <T> List<T> removeDoubles(List<T> values) {
        if (values.size() <= 1) {
            return values;
        }
        // Entfernen von Dubletten über ein ordnungserhaltendes Set. 'contains' auf einem großen Array wäre teuer.
   	 //FGL: Erst ab Java 1.7  final LinkedHashSet<T> withoutDoubles = new LinkedHashSet<>(values);     
        final LinkedHashSet<T> withoutDoubles = new LinkedHashSet<T>(values);//Eclipse Vorschlag "insert inferred type argument"    
        //FGL: dito return new ArrayList<>(withoutDoubles);
        return new ArrayList<T>(withoutDoubles);//Eclipse Vorschlag "insert inferred type argument"  
    }

    /**
     * Concatenate the given arrays.
     * @param first
     * @param second
     * @return Array containing the elements of both lists
     */
    // XXX REFACTOR => ArrayUtils
    // XXX geht das nicht auch mit generischem Typ?
    public static Object[] concatArrays(final Object[] first, final Object[] second) {
        final Object[] objectsNew = new Object[first.length + second.length];
        int i = 0;
        for (final Object each : first) {
            objectsNew[i++] = each;
        }
        for (final Object each : second) {
            objectsNew[i++] = each;
        }
        return objectsNew;
    }

    /**
     * @param elements
     * @param toFind
     * @return true if the array contains the element toFind.
     */
    // XXX REFACTOR => ArrayUtils
    public static <T> boolean contains(T[] elements, final T toFind) {
        for (T element : elements) {
            if (toFind.equals(element)) {
                return true;
            }
        }
        return false;
    }

    public static <T> void filterDuplicates(final List<T> list) {
        // XXX Set benutzen?
        for (int i = list.size() - 1; i >= 0; i--) {
            final T str = list.get(i);
            for (int j = i - 1; j >= 0; j--) {
                if (str.equals(list.get(j))) {
                    list.remove(i);
                    break;
                }
            }
        }
    }

    /**
     * Erzeugt aus einer Spalte eines 2D-Arrays (z.B.: dbHandler.getData)
     * ein Set.
     *
     * @param table     2D-Array
     * @param columnIndex Spaltenindex
     * @return Set
     */
    // XXX das ist mehr eine Datenbank-Funktionalität
    public static <T> Set<T> columnToSet(T[][] table, int columnIndex) {    	
      	 //FGL: Erst ab Java 1.7  final Set<T> set = new HashSet<>();     
    	final Set<T> set = new HashSet<T>();//Eclipse Vorschlag "insert inferred type argument"    
        if (table != null) {
            for (final T[] column : table) {
                set.add(column[columnIndex]);
            }
        }
        return set;
    }

    public static <T> Set<T> toSet(T[] theArray) {
    	 //FGL: Erst ab Java 1.7  final Set<T> set = new HashSet<>();     
    	final Set<T> set = new HashSet<T>();//Eclipse Vorschlag "insert inferred type argument"    
        if (theArray != null) {
            for (final T word : theArray) {
                set.add(word);
            }
        }
        return set;
    }
}

