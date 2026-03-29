package base.common.datetime;
//package de.his.core.common.datetime;

/**
 * Month / year holder.
 * @author hoersch
 */
public interface MonthValidity {

    /**
     *
     * @return the fiscal month
     */
    public Integer getFiscalMonth();

    /**
     *
     * @return the fiscal year
     */
    public Integer getFiscalYear();

    /**
     *
     * @return the month of this object
     */
    public Month getMonth();

}
