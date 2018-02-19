package base.common.datetime;
//package de.his.core.common.datetime;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.time.DateUtils;

//import de.his.core.base.invariants.EnsureArgument;
import base.invariants.EnsureArgument;


//import de.his.core.base.invariants.EnsureState;
import base.invariants.EnsureState;

/**
 * Utility for dates with day accuracy.
 * Company: HIS
 * @author hoersch
 * @version $Revision: 1.3 $
 */
public class DateWithDayAccuracyUtil {

    private DateWithDayAccuracyUtil() {
        // static util class
    }

    /**
     * Equals.
     * @param first
     * @param second
     * @return true if first equals second
     */
    public static boolean eq(final Date first, final Date second) {
        return compare(first, second, true) == 0;
    }

    /**
     * Not equals.
     * @param first
     * @param second
     * @return true if first not equals second
     */
    public static boolean ne(final Date first, final Date second) {
        return compare(first, second, true) != 0;
    }

    /**
     * Less then.
     *
     * @param first
     * @param second
     * @param testForUpperBound true: if a given date is null it represents the positive infinite, false negative infinite
     * @return true if first is before second
     */
    public static boolean lt(final Date first, final Date second, final boolean testForUpperBound) {
        return compare(first, second, testForUpperBound) < 0;
    }

    /**
     * Less or equal.
     *
     * @param first
     * @param second
     * @param testForUpperBound true: if a given date is null it represents the positive infinite, false negative infinite
     * @return true if first is before or equals second
     */
    public static boolean le(final Date first, final Date second, final boolean testForUpperBound) {
        return compare(first, second, testForUpperBound) <= 0;
    }

    /**
     * Greater then.
     *
     * @param first
     * @param second
     * @param testForUpperBound true: if a given date is null it represents the positive infinite, false negative infinite
     * @return true if first is after second
     */
    public static boolean gt(final Date first, final Date second, final boolean testForUpperBound) {
        return compare(first, second, testForUpperBound) > 0;
    }

    /**
     * Greater or equals.
     *
     * @param first
     * @param second
     * @param testForUpperBound true: if a given date is null it represents the positive infinite, false negative infinite
     * @return true if first is after or equals second
     */
    public static boolean ge(final Date first, final Date second, final boolean testForUpperBound) {
        return compare(first, second, testForUpperBound) >= 0;
    }

    /**
     * Compares two dates.
     *
     * @param first
     * @param second
     * @param testForUpperBound true: if a given date is null it represents the positive infinite, false negative infinite
     * @return 0 if both are equal, less than 0 if first date is before second, greater 0 if first is after second
     */
    public static int compare(final Date first, final Date second, final boolean testForUpperBound) {
        if (first == null && second == null) {
            return 0;
        }
        if (first == null) {
            return testForUpperBound ? 1 : -1;
        }
        if (second == null) {
            return testForUpperBound ? -1 : 1;
        }
        return dayAccuracy(first).compareTo(dayAccuracy(second));
    }

    /**
     *
     * <pre>
     * [------ second ------]
     *     [-- first ---]
     * </pre>
     * @param firstValidFrom
     * @param firstValidTo
     * @param secondValidFrom
     * @param secondValidTo
     * @return true if this validity is within second (borders included)
     */
    public static boolean isWithin(final Date firstValidFrom, final Date firstValidTo, final Date secondValidFrom, final Date secondValidTo) {
        return (ge(firstValidFrom, secondValidFrom, false)) && (le(firstValidTo, secondValidTo, true));
    }

    /**
     * <pre>
     *                  [--- first ---]
     * [--- second ---]
     * or:
     * [--- first ---]
     *                  [--- second ---]
     * </pre>
     *
     * @param firstValidFrom
     * @param firstValidTo
     * @param secondValidFrom
     * @param secondValidTo
     * @return true if this validity is completely separate from the second
     */
    public static boolean isSeparate(final Date firstValidFrom, final Date firstValidTo, final Date secondValidFrom, final Date secondValidTo) {
        return gt(firstValidFrom, secondValidTo, false) || gt(secondValidFrom, firstValidTo, false);
    }

    /**
     * <pre>
     *                  [--- first ---]
     * [--- second ---]
     * or:
     * [--- first ---]
     *                  [--- second ---]
     * </pre>
     *
     * @param firstValidFrom
     * @param firstValidTo
     * @param secondValidFrom
     * @param secondValidTo
     * @return true if this validity is completely separate from the second
     */
    public static boolean isSeparateUncompletly(final Date firstValidFrom, final Date firstValidTo, final Date secondValidFrom, final Date secondValidTo) {
        return gt(firstValidFrom, secondValidTo, false) || gt(secondValidFrom, firstValidTo, false);
    }

    /**
     * <pre>
     *       [--- first ---]
     *              [--- second ---]
     * </pre>
     *
     * @param firstValidFrom
     * @param firstValidTo
     * @param secondValidFrom
     * @param secondValidTo
     * @return true if this validity intersects the second from the beginning at least one day
     */
    public static boolean intersectsBegin(final Date firstValidFrom, final Date firstValidTo, final Date secondValidFrom, final Date secondValidTo) {
        return ge(firstValidTo, secondValidFrom, false) && le(firstValidFrom, secondValidFrom, false) && le(firstValidTo, secondValidTo, true);
    }

    /**
     * <pre>
     * [--- second ---]
     *          [--- this ---]
     * </pre>
     *
     * @param firstValidFrom
     * @param firstValidTo
     * @param secondValidFrom
     * @param secondValidTo
     * @return true if this validity intersects the second validity from the end at least one day
     */
    public static boolean intersectsEnd(final Date firstValidFrom, final Date firstValidTo, final Date secondValidFrom, final Date secondValidTo) {
        return le(firstValidFrom, secondValidTo, false) && ge(firstValidFrom, secondValidFrom, false) && ge(firstValidTo, secondValidTo, true);
    }

    /**
     *
     * <pre>
     *     [------ second ------]
     *  [-- first ---]
     * </pre>
     * or:
     * <pre>
     *     [------ second ------]
     *              [-- first ---]
     * </pre>
     * @param firstValidFrom
     * @param firstValidTo
     * @param secondValidFrom
     * @param secondValidTo
     * @return true if this validity intersects the second (borders included)
     */
    public static boolean intersects(final Date firstValidFrom, final Date firstValidTo, final Date secondValidFrom, final Date secondValidTo) {
        return intersectsBegin(firstValidFrom, firstValidTo, secondValidFrom, secondValidTo) || intersectsEnd(firstValidFrom, firstValidTo, secondValidFrom, secondValidTo);
    }

    /**
     *
     * <pre>
     * [------ validity ------]
     *     [-- date ---]
     * </pre>
     * @param date
     * @param validFrom
     * @param validTo
     * @return true if the date is within the validity (borders included)
     */
    public static boolean isIn(final Date date, final Date validFrom, final Date validTo) {
        return (validFrom == null || ge(date, validFrom, false)) && (validTo == null || le(date, validTo, true));
    }

    /**
     * Computes the difference of the two dates in days.
     * @param first
     * @param second
     * @return the difference
     */
    public static long diff(final Date first, final Date second) {
        EnsureArgument.notNull(first, "first");
        EnsureArgument.notNull(second, "second");
        return TimeUnit.DAYS.convert(dayAccuracy(second).getTimeInMillis() - dayAccuracy(first).getTimeInMillis(), TimeUnit.MILLISECONDS);
    }

    /**
     * Computes the absolute difference of the two dates in days.
     * @param first
     * @param second
     * @return the absolute difference
     */
    public static long absdiff(final Date first, final Date second) {
        EnsureArgument.notNull(first, "first");
        EnsureArgument.notNull(second, "second");

        long difference = diff(first, second);
        return (difference < 0) ? -difference : difference;
    }

    /**
     * Creates a calendar object with the given date. Only day, month and year is used to get the day accuracy.
     * Also the daylight savings offset is reset to 0.
     * @param d
     * @return a new calendar with only day accuracy
     */
    public static Calendar dayAccuracy(Date d) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);

        cal.set(Calendar.HOUR, 0);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        cal.set(Calendar.DST_OFFSET, 0);
        return cal;
    }

    /**
     * @param date
     * @return the date one day before the given date
     */
    public static Date dayBefore(Date date) {
        return date != null ? DateUtils.addDays(date, -1) : null;
    }

    /**
     * @param date
     * @return the date one day after the given date
     */
    public static Date dayAfter(Date date) {
        return date != null ? DateUtils.addDays(date, 1) : null;
    }

    /**
     * @return date of today with day accuracy
     */
    public static Date today() {
        return dayAccuracy(new Date()).getTime();
    }

    /**
     * @param day
     * @param month
     * @param year
     * @return the date of given single fragments
     */
    public static Date dateOf(int day, Integer month, Integer year) {
        EnsureArgument.notNull(month, "month");
        EnsureArgument.notNull(year, "year");

        return dateOf(day, month.intValue(), year);
    }

    /**
     * @param day
     * @param month
     * @param year
     * @return the date of given single fragments
     */
    public static Date dateOf(int day, int month, Integer year) {
        EnsureArgument.notNull(year, "year");

        return dateOf(day, month, year.intValue());
    }

    /**
     * @param day
     * @param month
     * @param year
     * @return the date of given single fragments
     */
    public static Date dateOf(int day, int month, int year) {

        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month - 1);
        cal.set(Calendar.DATE, day);
        return cal.getTime();
    }

    /**
     *
     * @param date
     * @return true if the given date has time information
     */
    public static boolean isWithTime(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        //@formatter:off
        return cal.get(Calendar.HOUR) != 0
                        || cal.get(Calendar.HOUR_OF_DAY) != 0
                        || cal.get(Calendar.MINUTE) != 0
                        || cal.get(Calendar.SECOND) != 0
                        || cal.get(Calendar.MILLISECOND) != 0;
        //@formatter:on
    }

    /**
     * <pre>
     *     [------ second ------]
     *  [-- first ----]
     *     [-- days --]
     * </pre>
     * or:
     * <pre>
     *     [------ second ------]
     *              [--- first ---]
     *              [--- days --]
     * </pre>
     * or
     * <pre>
     *     [------ second ------]
     *                                   [--- first ---]
     *                             0
     * </pre>
     * @param firstValidFrom
     * @param firstValidTo
     * @param secondValidFrom
     * @param secondValidTo
     * @return the number of intersecting days
     */
    public static long countIntersectionDays(final Date firstValidFrom, final Date firstValidTo, final Date secondValidFrom, final Date secondValidTo) {
        Date start = le(firstValidFrom, secondValidFrom, false) ? secondValidFrom : firstValidFrom;
        Date end = ge(firstValidTo, secondValidTo, true) ? secondValidTo : firstValidTo;

        EnsureState.isTrue(start != null && end != null, "Only two of the four given dates can be infinite (null)!");

        return countDays(start, end);
    }

    /**
     *
     * @param from
     * @param to
     * @return count the days
     */
    public static long countDays(final Date from, final Date to) {
        EnsureArgument.notNull(from, "from");
        EnsureArgument.notNull(to, "to");

        long diff = diff(from, to);
        if (diff < 0) {
            return 0;
        }
        return diff + 1;
    }

    /**
     * @param first
     * @param second
     * @return the lower one
     */
    public static Date min(Date first, Date second) {
        return ge(first, second, true) ? second : first;
    }

    /**
     * @param first
     * @param second
     * @return the higher one
     */
    public static Date max(Date first, Date second) {
        return le(first, second, true) ? second : first;
    }
}
