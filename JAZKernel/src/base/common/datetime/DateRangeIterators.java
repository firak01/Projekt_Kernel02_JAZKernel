package base.common.datetime;
//package de.his.core.common.datetime;

import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

import org.apache.commons.lang.time.DateUtils;

import com.google.common.collect.AbstractIterator;

//import de.his.core.base.invariants.EnsureArgument;
import base.invariants.EnsureArgument;

/**
 * Utility to iterate over a date range.
 *
 * <pre>
 * for (Calendar current : DateRange.ofDays(selectionFrom, selectionTo)) {
 *   // do something with the current day
 * }
 * </pre>
 * <br />
 * Company: HIS
 * @author hoersch
 * @version $Revision: 1.1 $
 */
public class DateRangeIterators {
    /**
     * Creates a new iterator to iterate over a range of days. If the end date is before the start date it iterates backwards
     * @param from (not null)
     * @param to (if null, from is used)
     * @return a new iterator iterating over the calendar dates in the given range (inclusive)
     */
    public static Iterable<Day> dayRange(final Date from, final Date to) {
        EnsureArgument.notNull(from, "from");
        final Date to_ = to != null ? to : from;
        return new Iterable<Day>() {
            @Override
            public Iterator<Day> iterator() {
                return new DayRangeIterator(from, to_);
            }
        };
    }

    /**
     * Creates a new iterator to iterate over a range of days. If the end date is before the start date it iterates backwards
     * @param from (not null)
     * @param to (if null, from is used)
     * @return a new iterator iterating over the calendar dates in the given range (inclusive)
     */
    public static Iterable<Calendar> calendarRange(final Date from, final Date to) {
        EnsureArgument.notNull(from, "from");
        final Date to_ = to != null ? to : from;
        return new Iterable<Calendar>() {
            @Override
            public Iterator<Calendar> iterator() {
                return new CalendarDayRangeIterator(from, to_);
            }
        };
    }

    private static class DayRangeIterator extends AbstractIterator<Day> {
        private Date _next;

        private final Date _end;

        private final int _direction;

        protected DayRangeIterator(final Date startDate, final Date endDate) {
            _next = DateWithDayAccuracyUtil.dayAccuracy(startDate).getTime();
            _end = DateWithDayAccuracyUtil.dayAccuracy(endDate).getTime();
            _direction = _next.before(_end) ? 1 : -1;
        }

        @Override
        protected Day computeNext() {
            if ((_direction == 1 && _next.after(_end)) || (_direction == -1 && _next.before(_end))) {
                return endOfData();
            }
            Date current = _next;
            _next = DateUtils.addDays(_next, _direction);
            return new Day(current);
        }

    }

    private static class CalendarDayRangeIterator extends AbstractIterator<Calendar> {
        private final Calendar _next;

        private final Calendar _end;

        private final int _direction;

        /**
         *
         * @param startDate
         * @param endDate
         */
        protected CalendarDayRangeIterator(final Date startDate, final Date endDate) {
            _next = DateWithDayAccuracyUtil.dayAccuracy(startDate);
            _end = DateWithDayAccuracyUtil.dayAccuracy(endDate);
            _direction = _next.before(_end) ? 1 : -1;
        }

        @Override
        protected Calendar computeNext() {
            if ((_direction == 1 && _next.after(_end)) || (_direction == -1 && _next.before(_end))) {
                return endOfData();
            }
            Calendar current = (Calendar) _next.clone();
            _next.add(Calendar.DAY_OF_YEAR, _direction);
            return current;
        }
    }
}
