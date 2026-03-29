package base.common.datetime;
//package de.his.core.common.datetime;

import java.util.Calendar;

/**
 * All month names and the integer representation.
 *
 * <br />
 * Company: HIS
 * @author hoersch
 * @version $Revision: 1.1 $
 */
public enum MonthName {
    /**
     * Value of the month field indicating the
     * first month of the year in the Gregorian and Julian calendars.
     */
    JANUARY(Calendar.JANUARY + 1),

    /**
     * Value of the month field indicating the
     * second month of the year in the Gregorian and Julian calendars.
     */
    FEBRUARY(Calendar.FEBRUARY + 1),

    /**
     * Value of the month field indicating the
     * third month of the year in the Gregorian and Julian calendars.
     */
    MARCH(Calendar.MARCH + 1),

    /**
     * Value of the month field indicating the
     * fourth month of the year in the Gregorian and Julian calendars.
     */
    APRIL(Calendar.APRIL + 1),

    /**
     * Value of the month field indicating the
     * fifth month of the year in the Gregorian and Julian calendars.
     */
    MAY(Calendar.MAY + 1),

    /**
     * Value of the month field indicating the
     * sixth month of the year in the Gregorian and Julian calendars.
     */
    JUNE(Calendar.JUNE + 1),

    /**
     * Value of the month field indicating the
     * seventh month of the year in the Gregorian and Julian calendars.
     */
    JULY(Calendar.JULY + 1),

    /**
     * Value of the month field indicating the
     * eighth month of the year in the Gregorian and Julian calendars.
     */
    AUGUST(Calendar.AUGUST + 1),

    /**
     * Value of the month field indicating the
     * ninth month of the year in the Gregorian and Julian calendars.
     */
    SEPTEMBER(Calendar.SEPTEMBER + 1),

    /**
     * Value of the month field indicating the
     * tenth month of the year in the Gregorian and Julian calendars.
     */
    OCTOBER(Calendar.OCTOBER + 1),

    /**
     * Value of the month field indicating the
     * eleventh month of the year in the Gregorian and Julian calendars.
     */
    NOVEMBER(Calendar.NOVEMBER + 1),

    /**
     * Value of the month field indicating the
     * twelfth month of the year in the Gregorian and Julian calendars.
     */
    DECEMBER(Calendar.DECEMBER + 1);

    private final Integer _month;

    private MonthName(final int month) {
        _month = Integer.valueOf(month);
    }

    /**
     *
     * @param month
     * @return the month name enum according to the given integer value
     */
    public static MonthName valueOf(int month) {
        for (final MonthName monthName : values()) {
            if (monthName.asInt() == month) {
                return monthName;
            }
        }
        throw new IllegalArgumentException("Unknown integer value: " + month);
    }

    int asInt() {
        return asInteger().intValue();
    }

    /**
     * @return the integer value of this month
     */
    public Integer asInteger() {
        return _month;
    }
}
