package base.common.datetime;
//package de.his.core.common.datetime;

import static com.google.common.base.Preconditions.checkNotNull;
//import static de.his.core.base.invariants.EnsureArgument.checkRange;
import static base.invariants.EnsureArgument.checkRange;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.google.common.base.Predicate;
import com.google.common.collect.Lists;

//import de.his.core.base.invariants.EnsureArgument;
import base.invariants.EnsureArgument;

/**
 * Value class to hold a month/year combination. It is a date range.
 * <br />
 * Company: HIS
 * @author hoersch
 * @version $Revision: 1.4 $
 */
public class Month extends DateRange implements Serializable, Comparable<Month> {
    private static final long serialVersionUID = 1L;

    private final MonthName _month;

    private final Integer _year;

    private Date _endDate;

    private Date _startDate;

    private Month(final Date date) {
        this(toCalendar(date));
    }

    private Month(final Calendar calendar) {
        this(calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.YEAR));
    }

    /**
     *
     * @param month January == 1, December == 12
     * @param year
     */
    public Month(final Integer month, final Integer year) {
        this(checkNotNull(month).intValue(), year);
    }

    /**
     *
     * @param month January == 1, December == 12
     * @param year
     */
    public Month(final int month, final Integer year) {
        this(month, checkNotNull(year).intValue());
    }

    /**
     *
     * @param month January == 1, December == 12
     * @param year
     */
    public Month(final int month, final int year) {
        this(MonthName.valueOf(checkRange(month, 1, 12, "month")), year);
    }

    /**
     *
     * @param month
     * @param year
     */
    public Month(final MonthName month, final int year) {
        _month = month;
        _year = Integer.valueOf(year);
    }

    /**
     *
     * @param month
     * @param year
     */
    public Month(final MonthName month, final Integer year) {
        _month = month;
        _year = checkNotNull(year);
    }

    /**
     *
     * @param date
     * @return a new month or null if date is null
     */
    public static Month of(final Date date) {
        return date != null ? new Month(date) : null;
    }

    /**
     *
     * @param calendar
     * @return a new month or null if date is null
     */
    public static Month of(final Calendar calendar) {
        return calendar != null ? new Month(calendar) : null;
    }

    /**
     *
     * @param count
     * @return a new Month object with count months added
     */
    public Month addMonths(final int count) {
        if (count == 0) {
            return this;
        }

        final int totalMonth = _month.asInt() + count;
        int newYear = _year.intValue();
        int newMonth;
        if (totalMonth <= 0) {
            newMonth = totalMonth % 12;
            if (newMonth <= 0) {
                newMonth += 12;
            }
        } else {
            newMonth = (totalMonth - 1) % 12 + 1;
        }
        final int yearDiff = (totalMonth - newMonth + 12) / 12 - 1;
        newYear += yearDiff;

        return new Month(newMonth, newYear);
    }

    /**
     * @return the month
     */
    public MonthName getMonth() {
        return _month;
    }

    /**
     * @return the year
     */
    public Integer getYear() {
        return _year;
    }

    @Override
    public String toString() {
        return String.format("%02d-%d", _month.asInteger(), _year);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + _month.asInt();
        result = prime * result + _year.hashCode();
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
        if (!(obj instanceof Month)) {
            return false;
        }
        final Month other = (Month) obj;
        if (_month != other._month) {
            return false;
        }
        if (!_year.equals(other._year)) {
            return false;
        }
        return true;
    }

    /**
     *
     * @param other
     * @return true if this month is lesser or equal to the given
     */
    public boolean beforeOrEqual(Month other) {
        if (other == null) {
            return true;
        }
        final int year = _year.intValue();
        final int otherYear = other._year.intValue();
        final int month = _month.asInt();
        final int otherMonth = other._month.asInteger().intValue();
        return year != otherYear ? year <= otherYear : month <= otherMonth;
    }

    private static Calendar toCalendar(final Date date) {
        final Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }

    /**
     * @param year
     * @return all months in the given year
     */
    public static Iterable<Month> yearRange(int year) {
        return yearRange(year, year);
    }

    /**
     * @param startYear
     * @param endYear
     * @return all months between first month of the start and last month of the end year (inclusive)
     */
    public static Iterable<Month> yearRange(int startYear, int endYear) {
        if (startYear <= endYear) {
            return range(new Month(1, startYear), new Month(12, endYear));
        }
        return range(new Month(1, endYear), new Month(12, startYear));
    }

    /**
     *
     * @param start
     * @param end
     * @return all months between start and end month (inclusive)
     */
    public static Iterable<Month> range(Month start, Month end) {
        EnsureArgument.notNull(start, "start");
        EnsureArgument.notNull(start, "end");

        final Month start_ = start.beforeOrEqual(end) ? start : end;
        final Month end_ = !start.beforeOrEqual(end) ? start : end;

        final List<Month> result = Lists.newArrayList();
        Month current = start_;
        while (current.beforeOrEqual(end_)) {
            result.add(current);
            current = current.addMonths(1);
        }
        return result;
    }

    /**
     *
     * @param month
     * @return a predicate that checks if a given object is valid in the given month
     */
    public static <PV extends MutableMonthValidity> Predicate<PV> validInMonth(final Month month) {
        return new Predicate<PV>() {
            @Override
            public boolean apply(PV input) {
                return month.equals(input.getMonth());
            }
        };
    }

    /**
     *
     * @param months
     * @return a predicate that checks if a given object is valid in any of the given months
     */
    public static <PV extends MutableMonthValidity> Predicate<PV> validInAnyMonth(Iterable<Month> months) {
        final Collection<Month> months_ = Lists.newArrayList(months);
        return new Predicate<PV>() {
            @Override
            public boolean apply(PV input) {
                return months_.contains(input.getMonth());
            }
        };
    }

    /**
     * @return this month as {@link Date} representing the first day of the month
     */
    @Override
    public Date getStartDate() {
        if (_startDate == null) {
            final Calendar calendar = asStartDateIntern();
            _startDate = calendar.getTime();
        }
        return _startDate;
    }

    /**
     * @return this month as {@link Date} representing the last day of the month
     */
    @Override
    public Date getEndDate() {
        if (_endDate == null) {
            final Calendar calendar = asEndDateIntern();
            _endDate = calendar.getTime();
        }
        return _endDate;
    }

    private Calendar asStartDateIntern() {
        final Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.MONTH, _month.asInt() - 1);
        calendar.set(Calendar.YEAR, _year.intValue());
        return calendar;
    }

    private Calendar asEndDateIntern() {
        final Calendar calendar = asStartDateIntern();
        calendar.roll(Calendar.DAY_OF_MONTH, -1);
        return calendar;
    }

    /**
     *
     * @return the count of days of this month
     */
    @Override
    public long days() {
        return asEndDateIntern().get(Calendar.DAY_OF_MONTH);
    }

    @Override
    public Iterable<Month> months() {
        return Arrays.asList(this);
    }

    /**
     * @return the month in the prior year
     */
    public Month priorYear() {
        return new Month(_month.asInt(), _year.intValue() - 1);
    }

    /**
     * @return the prior month
     */
    public Month priorMonth() {
        return addMonths(-1);
    }

    /**
     *
     * @param collection
     * @return a new collection containing only elements valid for this month or null if the given collection is null
     */
    public <PV extends MutableMonthValidity> Collection<PV> filterByFiscalValidity(Collection<PV> collection) {
        if (collection == null) {
            return null;
        }
        final List<PV> result = Lists.newArrayList();
        final int year = getYear().intValue();
        final int month = getMonth().asInt();
        for (final PV pv : collection) {
            if (pv.getFiscalYear().intValue() == year && pv.getFiscalMonth().intValue() == month) {
                result.add(pv);
            }
        }
        return result;
    }

    @Override
    public int compareTo(Month o) {
        if (o == null) {
            return 1;
        }
        final int cmp = _year.compareTo(o._year);
        if (cmp != 0) {
            return cmp;
        }
        return _month.compareTo(o._month);
    }
}

