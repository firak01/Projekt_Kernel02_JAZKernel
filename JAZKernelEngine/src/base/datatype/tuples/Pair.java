package base.datatype.tuples;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Map;

/**
 * Ein Paar von Objekten
 *
 * @author brummermann
 * @param <K> Schl&uuml;ssel
 * @param <V> Wert
 */
public class Pair<K, V> implements Serializable, Map.Entry<K, V> {

    private static final long serialVersionUID = -8005006032570760958L;

    private K key = null;

    private V value = null;


    /**
     * Comparator-Klasse, die eine Liste von Paaren nach deren Key vergleichbar macht. Kann nur 
verwendet werden, wenn die Keys 'Comparable' implementieren.
     *
     * @author martin
     */
    static class PairByKeyComparator<K extends Comparable<K>, V> implements Comparator<Pair<K, V>>, 
Serializable {
        private static final long serialVersionUID = -7006883537267525554L;

        @Override
        public int compare(Pair<K, V> o1, Pair<K, V> o2) {
            return ((Comparable<K>) o1.getKey()).compareTo(o2.getKey());
        }
    }

    /** Vergleicht Paare nur nach ihren Keys. */
    @SuppressWarnings("rawtypes")
    public static final Comparator COMPARE_BY_KEY = new PairByKeyComparator();

    public Pair(Map.Entry<K, V> e) {
        this.key = e.getKey();
        this.value = e.getValue();
    }

    public Pair(K key, V value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }


    @Override
    public V getValue() {
        return value;
    }


    @Override
    public V setValue(V value) {
        final V temp = this.value;
        this.value = value;
        return temp;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (key == null ? 0 : key.hashCode());
        result = prime * result + (value == null ? 0 : value.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Pair<?, ?>)) {
            return false;
        }
        final Pair<?, ?> other = (Pair<?, ?>) obj;
        if (key == null) {
            if (other.key != null) {
                return false;
            }
        } else if (!key.equals(other.key)) {
            return false;
        }
        if (value == null) {
            if (other.value != null) {
                return false;
            }
        } else if (!value.equals(other.value)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Pair [key=" + key + ", value=" + value + "]";
    }

    //Erst ab Java 1.7
//    public Pair<V, K> swap() {
//        return new Pair<>(value, key);
//    }
}

