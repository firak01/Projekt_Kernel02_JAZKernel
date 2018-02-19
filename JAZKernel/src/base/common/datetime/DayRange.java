package base.common.datetime;
//package de.his.core.common.datetime;

import java.util.Date;

//import de.his.core.base.invariants.EnsureArgument;
import base.invariants.EnsureArgument;

/**
 * A range of Dates, i.e. a fiscal year.
 * <br />
 * Company: HIS
 * @author hoersch
 * @version $Revision: 1.1 $
 */
public class DayRange extends DateRange {

    private final Day _from;

    private final Day _to;

    /**
     *
     * @param from
     * @param to
     */
    public DayRange(final Date from, final Date to) {
        this(new Day(from), new Day(to));
    }

    /**
     *
     * @param from
     * @param to
     */
    public DayRange(final Day from, final Day to) {
        EnsureArgument.notNull(from, "from");
        EnsureArgument.notNull(to, "to");
        this._from = from;
        this._to = to;

    }

    @Override
    public Date getStartDate() {
        return _from.getStartDate();
    }

    @Override
    public Date getEndDate() {
        return _to.getEndDate();
    }

    @Override
    public String toString() {
        return _from + " - " + _to;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((_from == null) ? 0 : _from.hashCode());
        result = prime * result + ((_to == null) ? 0 : _to.hashCode());
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
        DayRange other = (DayRange) obj;
        if (_from == null) {
            if (other._from != null) {
                return false;
            }
        } else if (!_from.equals(other._from)) {
            return false;
        }
        if (_to == null) {
            if (other._to != null) {
                return false;
            }
        } else if (!_to.equals(other._to)) {
            return false;
        }
        return true;
    }
}

