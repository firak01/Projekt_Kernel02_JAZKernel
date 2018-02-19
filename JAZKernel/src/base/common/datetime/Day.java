package base.common.datetime;
//package de.his.core.common.datetime;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;


/**
 * Represents a date range of a single day.
 * <br />
 * Company: HIS
 * @author hoersch
 * @version $Revision: 1.1 $
 */
public class Day extends DateRange implements Serializable {
    private static final long serialVersionUID = 1L;

    private final Date _date;

    private final int _y;

    private final int _d;

    private final int _m;

    /**
     *
     * @param date
     */
    public Day(final Date date) {
        this(DateWithDayAccuracyUtil.dayAccuracy(date));
    }

    private Day(final Calendar cal) {
        this._d = cal.get(Calendar.DAY_OF_MONTH);
        this._m = cal.get(Calendar.MONTH) + 1;
        this._y = cal.get(Calendar.YEAR);
        this._date = cal.getTime();
    }

    /**
     *
     * @return today
     */
    public static Day today() {
        return new Day(new Date());
    }

    @Override
    public Date getStartDate() {
        return _date;
    }

    @Override
    public Date getEndDate() {
        return _date;
    }

    @Override
    public String toString() {
        return String.format("%2d.%2d.%d", Integer.valueOf(_d), Integer.valueOf(_m), Integer.valueOf(_y));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + _d;
        result = prime * result + _m;
        result = prime * result + _y;
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
        if (getClass() != obj.getClass()) {
            return false;
        }
        Day other = (Day) obj;
        if (_d != other._d) {
            return false;
        }
        if (_m != other._m) {
            return false;
        }
        if (_y != other._y) {
            return false;
        }
        return true;
    }
}

