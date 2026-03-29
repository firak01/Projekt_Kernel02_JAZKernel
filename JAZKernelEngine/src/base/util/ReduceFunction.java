package base.util;
//package de.his.core.util;


/**
 * Simple function interface to be used to reduce lists.
 * Company: HIS
 * @author hoersch
 * @version $Revision: 1.2 $
 * @param <F>
 * @param <T>
 */
public interface ReduceFunction<F, T> {
    /**
     * Applies the value next to the accumulated value.
     * @param accum the accumulated value
     * @param next the actual value to be processed
     * @return the reduced value
     */
    public T apply(T accum, F next);
}

