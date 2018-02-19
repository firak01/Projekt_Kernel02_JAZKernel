package base.common.datetime;
//package de.his.core.common.datetime;

/**
 * Mutable month / year holder.
 * <br />
 * Company: HIS
 * @author hoersch
 * @version $Revision: 1.1 $
 */
public interface MutableMonthValidity extends MonthValidity {

    /**
     *
     * @param month
     */
    public void setFiscalMonth(Integer month);

    /**
     *
     * @param year
     */
    public void setFiscalYear(Integer year);

}
