package base.common.datetime;
//package de.his.core.common.datetime;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TimeZone;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;

import com.google.common.collect.Lists;



//import de.his.core.base.exceptions.HISException;
import base.exceptions.HISException;
//import de.his.core.base.exceptions.HISIllegalArgumentException;
import base.exceptions.HISIllegalArgumentException;
//import de.his.core.base.numeric.NumberUtil;
import base.numeric.NumberUtil;
//import de.his.core.base.reflection.ReflectionUtil;
import base.reflection.ReflectionUtil;
//import de.his.core.base.strings.StringUtil;
import base.strings.StringUtil;

/**
 *
 * Diese Klasse stellt verschiedene Funktionen zur Arbeit mit Datumsangaben zur Verf&uuml;gung.
 * Company: HIS
 * @version $Id: DateUtil.java,v 1.29 2017/03/30 11:16:46 siegel#his.de Exp $
 * @author <a href="mailto:jauer@his.de">Wilfried Jauer</a>, <a href="http://www.his.de">HIS eG</a>
 */
public final class DateUtil {
    private static Locale aLocale = new Locale("de", "DE");

    private static final Date maxDate = new Date(9999 - 1900, 11, 1);

    private static final List<String> PATTERNS = Arrays.asList("dd.MM.yy", "dd.MM.yyyy",

                    "d.M.yy", "d.M.yyyy",

                    "dd.M.yy", "dd.M.yyyy",

                    "d.MM.yy", "d.MM.yyyy",

                    "ddMMyy", "ddMMyyyy",

                    "MM/dd/yy", "MM/dd/yyyy",

                    "M/dd/yy", "M/dd/yyyy",

                    "MM/d/yy", "MM/d/yyyy",

                    "M/d/yy", "M/d/yyyy");

    // used by Velocity  as "DateUtil" via DateUtil.class
    /* XXX REFACTOR make a Velocity DateUtil with:
     * humanReadableSeconds
     * compareDates()
     *
     */
    private DateUtil() {
    }

    private static Logger logger = Logger.getLogger(DateUtil.class);

    /**
     * Liefert Tagesdatum im angegebenen Format.
     *
     * @param formatString Datumsformat
     * @return Tagesdatum
     */
    public static String getToday(String formatString) {
        return new SimpleDateFormat(formatString).format(new Date());
    }

    /**
     * Liefert Tagesdatum in der Form tt.mm.yyyy
     *
     * @return Tagesdatum
     */
    public static String getToday() {
        return getToday("dd.MM.yyyy");
    }

    /**
     * Prüft Datumsangabe.
     *
     * @param dateStr vermeintliches Datum
     * @return formatiertes Datum
     * @throws HISException wenn der Input-String keine gueltige Datumsangabe darstellt
     */
    public static String checkDate(String dateStr) throws HISException {
        return checkAndFormatDate(dateStr, "dd.MM.yyyy", ".");
    }

    /**
     * Prüft Datumsangabe.
     *
     * @param dateStr vermeintliches Datum
     * @param formatString nach SimpleDateFormat-Standards
     * @param sep Trennzeichen des Formatstrings
     * @return formatiertes Datum
     * @throws HISException wenn der Input-String keine gueltige Datumsangabe darstellt
     */
    public static String checkAndFormatDate(String dateStr, String formatString, String sep) throws HISException {
        final Date date = strToDate(dateStr, formatString);
        if (date == null) {
            return null;
        }
        final SimpleDateFormat simpleDateFormat0 = new SimpleDateFormat(formatString);
        String validDate = simpleDateFormat0.format(date);
        /* Eigene Plausis ***************/
        // 1. War das ein zweistelliges Jahr? (dann fängt es mit zwei Nullen an)
        final SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy");
        String yearStr = simpleDateFormat1.format(date);
        if (yearStr.startsWith("00")) {
            // 20. oder 21. Jahrhundert ?
            try {
                final int year = Integer.parseInt(yearStr);
                // aktuelles Jahr holen und vergleichen ...
                final Date actDate = new Date();
                yearStr = simpleDateFormat1.format(actDate);
                final String yearStrClipped = yearStr.substring(3);
                final int actYear = Integer.parseInt(yearStrClipped);
                final int diff = Math.abs(year - actYear);
                if (diff < 25) {
                    // dieses Jahrhundert ...
                    validDate = StringUtils.replaceOnce(validDate, "00", yearStr.substring(0, 2));
                } else {
                    // letztes Jahrhundert ...
                    validDate = StringUtils.replaceOnce(validDate, "00", "19");
                }

            } catch (final Exception e) {
                throw new HISException(e);
            }
        }
        // 2. Gab es ein Überlauf beim Tag oder Monat? (dann sind die Tage oder Monat ungleich)
        final String[] items = { "dd", "MM" };
        final String[] errors = { "wrongDay", "wrongMonth" };
        for (int i = 0; i < items.length; i++) {
            final String itemMatch = items[i];
            final String errorText = errors[i];
            final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(itemMatch);
            final String itemStr = simpleDateFormat.format(date);
            final String orgItem = getItem(dateStr, formatString, itemMatch, sep);
            if (!compareIntStrings(itemStr, orgItem)) {
                throw new HISException(errorText);
            }
        }
        return validDate;
    }

    /**
     * Erzeugt Datum aus String.
     *
     * @param dateStr Datumsstring
     * @param format nach SimpleDateFormat-Standards
     * @return Datum
     * @throws HISException wenn der Input-String keine gueltige Datumsangabe darstellt
     */
    public static Date strToDate(String dateStr, String format) throws HISException {
        String formatString = format;
        Date date = null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatString);
        try {
            date = simpleDateFormat.parse(dateStr);
        } catch (final Exception e) {
            final int ix = formatString.indexOf(" ");
            if (ix <= -1) {
                throw new HISException(e);
            }
            formatString = formatString.substring(0, ix);
            simpleDateFormat = new SimpleDateFormat(formatString);
            try {
                date = simpleDateFormat.parse(dateStr);
            } catch (final Exception e1) {
                throw new HISException(e1);
            }
        }
        return date;
    }

    private static boolean compareIntStrings(String val1, String val2) {
        try {
            return Integer.parseInt(val1) == Integer.parseInt(val2);
        } catch (final Exception e) {
            logger.error(e);
            return false;
        }
    }

    private static String getItem(String str, String itemPattern, String itemMatch, String sep) {
        String item = null;
        final StringTokenizer strTok1 = new StringTokenizer(itemPattern, sep);
        final StringTokenizer strTok2 = new StringTokenizer(str, sep);
        while (strTok1.hasMoreTokens() && strTok1.hasMoreTokens()) {
            final String token1 = strTok1.nextToken();
            final String token2 = strTok2.nextToken();
            if (token1.equals(itemMatch)) {
                item = token2;
                break;
            }
        }
        return item;
    }

    /**
     * Gleicht Jahreslänge an.
     *
     * @param yearStr Jahr
     * @param centuryStr Jahrhundert
     * @param yearlength gewuenschte Laenge
     * @return Jahr in gewuenschter Laenge
     */
    public static String adjustYear(String yearStr, String centuryStr, int yearlength) {
        String century = centuryStr;
        String year = yearStr;
        if (century == null) {
            century = "20";
        }
        if (century.length() > 2) {
            century = century.substring(0, 2);
        }
        if (year.length() == 2 && yearlength == 4) {
            year = century + year;
        }
        if (year.length() == 4 && yearlength == 2) {
            year = year.substring(2);
        }

        return year;
    }

    public static String removeEmptyTimepart(String value) {
        String modifiedValue = value;
        if (modifiedValue.endsWith(" 00:00:00")) {
            modifiedValue = value.substring(0, value.length() - 9);
        }
        return modifiedValue;
    }

    /**
     *
     * @return aktuelle Zeit im Format HH:MM:SS
     */
    public static String getCurrentNormalisedTime() {
        return normaliseTime(getCurrentDate());
    }

    /**
     * @param date java.util.Date - Objekt
     * @return aktuelle Zeit im Format HH:MM:SS
     */
    public static String normaliseTime(Date date) {
        final DateFormat formater = DateFormat.getTimeInstance();
        return formater.format(date);
    }

    /**
     * @return java.util.Date - Objekt mit der aktuellen ECT-Zeit.
     */
    public static Date getCurrentDate() {
        final Calendar cal = new GregorianCalendar(TimeZone.getTimeZone("ECT"));
        return cal.getTime();
    }

    /**
     * Konvertiert ein Date-Objekt ins ISO-Format (ohne Millisekunden).
     *
     * @param date Date-Objekt
     * @return Datum als String im ISO-Format
     */
    public static String dateToIsoWithoutEmptyTimePart(Date date) {
        String res = new java.sql.Timestamp(date.getTime()).toString();
        final int pos = res.indexOf('.');
        if (pos > -1) {
            res = res.substring(0, pos);
        }
        return removeEmptyTimepart(res);
    }

    /**
     * Konvertiert ein Date-Objekt ins ISO-Format (ohne Millisekunden).
     *
     * @param date Date-Objekt
     * @return Datum als String im ISO-Format
     */
    public static String dateToISO(Date date) {
        String res = new java.sql.Timestamp(date.getTime()).toString();
        final int pos = res.indexOf('.');
        if (pos > -1) {
            res = res.substring(0, pos);
        }
        return res;
    }

    /**
     * Vergleicht zwei Strings im Timestamp-Format.
     *
     * @param time1 Timestamp-String
     * @param time2 Timestamp-String
     * @return -1 time1 ist vor time2
     *         +1 time1 ist nach time2
     *          0 sonst (auch im Fehlerfall)
     */
    public static int compareTimes(String time1, String time2) {
        if (time1 == null || time2 == null || time1.equals(time2)) {
            return 0;
        }
        final DateFormat formater = DateFormat.getDateTimeInstance();
        try {
            final Date date1 = formater.parse(time1);
            final Date date2 = formater.parse(time2);
            return date1.before(date2) ? -1 : 1;
        } catch (final ParseException e) {
            logger.error("Fehler im Datumsformat: ", e);
            return 0;
        }
    }

    /**
     * @param startDate 1. Parameter (1. Datum)
     * @param endDate  2. Parameter (2. Datum)
     * Format der Daten muss sein: DD.MM.YYYY - Tag und Monat kann auch nur jeweils eine Zahl sein
     * @return liefert True wenn der 1. Parameter zeitlich vor dem 2. Parameter liegt oder wenn beide gleich sind, anonsten false
     * @throws HISException wenn das Format der Daten nicht korrekt ist
     */
    public static boolean compareDates(String startDate, String endDate) throws HISException {
        if (!startDate.contains(".") || !endDate.contains(".") || !startDate.substring(startDate.indexOf(".") + 1, startDate.length()).contains(".")
            || !endDate.substring(endDate.indexOf(".") + 1, endDate.length()).contains(".")) {
            throw new HISException("Format der Daten ist nicht korrekt! (D(D).M(M).YYYY)");
        }

        String startDay = startDate.substring(0, startDate.indexOf("."));
        int startDayInt = NumberUtil.parseInt(startDay, 0);

        String startMonth = startDate.substring(startDate.indexOf(".") + 1, startDate.length()).substring(0, startDate.indexOf("."));
        int startMonthInt = NumberUtil.parseInt(startMonth, 0);

        String startYear = startDate.substring(startDate.lastIndexOf(".") + 1, startDate.length());
        int startYearInt = NumberUtil.parseInt(startYear, 0);

        String endDay = endDate.substring(0, endDate.indexOf("."));
        int endDayInt = NumberUtil.parseInt(endDay, 0);

        String endMonth = endDate.substring(endDate.indexOf(".") + 1, endDate.length()).substring(0, endDate.indexOf("."));
        int endMonthInt = NumberUtil.parseInt(endMonth, 0);

        String endYear = endDate.substring(endDate.lastIndexOf(".") + 1, endDate.length());
        int endYearInt = NumberUtil.parseInt(endYear, 0);

        if (startDayInt == 0 || startMonthInt == 0 || startYearInt < 100 || endDayInt == 0 || endMonthInt == 0 || endYearInt < 100) {
            logger.error("Format der Daten ist nicht korrekt, startDate='" + startDate + "'; endDate='" + endDate + "' ...");
            return false;
        }

        if (endYearInt > startYearInt) {
            return true;
        } else if (endYearInt < startYearInt) {
            return false;
        }

        if (endMonthInt > startMonthInt) {
            return true;
        } else if (endMonthInt < startMonthInt) {
            return false;
        }

        return endDayInt >= startDayInt;
    }

    /**
     * Methode um Zeiten zu vergleichen
     * @param startTime = Startzeit (Format: hh:mm)
     * @param endTime = Endzeit (Format: hh:mm)
     * @return true, falls Startzeit=Endzeit oder falls die Startzeit vor der Endzeit liegt, ansonsten false
     */

    public static boolean compareTimes2(String startTime, String endTime) {
        if (!startTime.contains(":") || !endTime.contains(":")) {
            return false;
        }
        if (startTime.equals(endTime)) {
            return true;
        }

        final int startHour = NumberUtil.parseInt(startTime.substring(0, startTime.indexOf(":")), 0);
        final int endHour = NumberUtil.parseInt(endTime.substring(0, endTime.indexOf(":")), 0);
        if (startHour != endHour) {
            return startHour < endHour;
        }

        final int startMinutes = NumberUtil.parseInt(startTime.substring(startTime.indexOf(":") + 1, startTime.length()), 0);
        final int endMinutes = NumberUtil.parseInt(endTime.substring(endTime.indexOf(":") + 1, endTime.length()), 0);
        return startMinutes < endMinutes;
    }

    /**
     * Zur Verwendung im Template. Erwartet zwei Datum Strings im Format dd.mm.yyyy
     * Wenn das erste Datum größer -> true sonst --> false
     *
     * Es gibt noch eine andere compareDates in DateUtil; keine Ahnung, was die unterscheidet...
     *
     * @param first dd.mm.yyyy
     * @param second dd.mm.yyyy
     * @return boolean
     */
    public static boolean compareDates2(String first, String second) {
        HISDate firstDate = parseDate(first);
        HISDate secDate = parseDate(second);
        return firstDate != null && secDate != null && firstDate.compare(secDate) > 0;
    }

    /**
     * @param date dd.mm.yyyy
     */
    private static HISDate parseDate(String date) {
        final String[] dmySec = date.split(Pattern.quote("."));
        if (dmySec == null || dmySec.length != 3) {
            return null;
        }
        return new HISDate(Integer.parseInt(dmySec[2]), Integer.parseInt(dmySec[1]), Integer.parseInt(dmySec[0]));
    }

    /**
     * Method to resolve the calendar-week of a given date.
     * @param cal
     * @return calendar-week
     */
    public static int getCalendarWeek(Calendar cal) {
        return cal.get(Calendar.WEEK_OF_YEAR);
    }

    /**
     * Get Date from year.
     * @param date
     * @return year
     */
    public static Integer getYearFromdate(Date date) {
        final Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return Integer.valueOf(cal.get(Calendar.YEAR));
    }

    /**
     * @param intWeek
     * @param intDayOfWeek (1 = Monday ... 7 = Siunday)
     * @return Date of the required Day in the given week
     */
    public static Date getDateFromWeek(int intWeek, int intDayOfWeek) {
        final GregorianCalendar calendar = new GregorianCalendar(Locale.GERMANY);
        final int CURRENT_YEAR = calendar.get(Calendar.YEAR);
        calendar.clear();
        calendar.set(Calendar.YEAR, CURRENT_YEAR);
        calendar.set(Calendar.WEEK_OF_YEAR, intWeek);
        calendar.add(Calendar.DAY_OF_MONTH, intDayOfWeek - 1);
        return calendar.getTime();
    }

    /**
     * Methode um Zeiten zu subtrahieren
     * @param time Die Zeit von der subtrahiert werden soll (Format: hh:mm)
     * @param timeToSubtract Die Zeit die subtrahiertt werden soll (Format: hh:mm)
     * z.B. time=10:00 timeToSubtract=1:30 return=08:30
     * oder time=10:00 timeToSubtract=90 return=08:30
     * @return Ergebnis der Subtraktion
     */
    public static String subtractTime(String time, String timeToSubtract) {
        String hour, minute;
        int minutes, hours, minutesToSub, hoursToSub;
        if (time.contains(":")) {
            minutes = NumberUtil.parseInt(time.substring(time.indexOf(":") + 1), 0);
            hours = NumberUtil.parseInt(time.substring(0, time.indexOf(":")), 0);
        } else {
            logger.error("Die Uhrzeit muss ein ':' zum trennen der Stunden von den Minuten enthalten");
            return "";
        }
        if (timeToSubtract.contains(":")) {
            minutesToSub = NumberUtil.parseInt(timeToSubtract.substring(timeToSubtract.indexOf(":") + 1), 0);
            hoursToSub = NumberUtil.parseInt(timeToSubtract.substring(0, timeToSubtract.indexOf(":")), 0);
        } else {
            minutesToSub = NumberUtil.parseInt(timeToSubtract, 0);
            hoursToSub = 0;
        }
        hours = hours - hoursToSub;
        minutes = minutes - minutesToSub;

        while (minutes < 0) {
            minutes = minutes + 60;
            hours = hours - 1;
        }
        if (hours < 0) {
            hours = hours + 24;
        }
        if (hours < 10) {
            hour = "0" + hours;
        } else {
            hour = "" + hours;
        }
        if (minutes < 10) {
            minute = "0" + minutes;
        } else {
            minute = "" + minutes;
        }
        return hour + ":" + minute;
    }

    /**
     * Wandelt Sekunden in "gewohnte" Angabe um.
     *
     * @param totalseconds Sekunden
     * @return "gewohnte" Angabe in Stunden, Minuten, Sekunden
     */
    public static String humanReadableSeconds(long totalseconds) {
        String duration = null;
        try {
            if (totalseconds < 60) {
                duration = totalseconds + " sec";
            } else if (totalseconds < 3600) {
                duration = totalseconds / 60 + " min " + totalseconds % 60 + " sec";
            } else {
                final long hours = totalseconds / (60 * 60);
                final long remaining = totalseconds - hours * 60 * 60;
                final long minutes = remaining / 60;
                final long seconds = remaining - minutes * 60;
                duration = hours + " h " + minutes + " min " + seconds + " sec";
            }
        } catch (final Exception e) {
            logger.error("transformSeconds: ", e);
        }
        return duration;
    }

    /**
     * Wandelt MilliSekunden in "gewohnte" Angabe um.
     *
     * @param milliseconds Sekunden
     * @return "gewohnte" Angabe in Stunden, Minuten, Sekunden
     */
    public static String humanReadableMilliSeconds(long milliseconds) {
        String duration = null;
        try {
            if (milliseconds < 3000) {
                duration = milliseconds + " msec";
            } else {
                duration = humanReadableSeconds(milliseconds / 1000);
            }
        } catch (final Exception e) {
            logger.error("transformSeconds: ", e);
        }
        return duration;
    }

    /**
     * @param time
     * @return ...
     */
    public static int dateTimeToInt(String time) {
        final String[] s = time.split(":");
        int res = Integer.parseInt(s[0]) * 60;
        if (s.length > 1) {
            res += Integer.parseInt(s[1]);
        }
        return res;
    }

    /**
     * @param time
     * @return ...
     */
    public static String intTodateTimeString(int time) {
        final int h = time / 60;
        final int m = time % 60;
        final String sh = h < 10 ? "0" + h : "" + h;
        final String mh = m < 10 ? "0" + m : "" + m;
        return sh + ":" + mh;
    }

    /**
     * Get the number of seconds between two dates.
     * @param from must not be null
     * @param to must not be null
     * @return number of seconds
     */
    public static long getSecondsBetween(Date from, Date to) {
        return (from.getTime() - to.getTime()) / 1000;
    }

    /**
     * Get the number of seconds between two dates as int.
     * @param from must not be null
     * @param to must not be null
     * @return number of seconds
     */
    public static int getSecondsBetweenInt(Date from, Date to) {
        return (int) getSecondsBetween(from, to);
    }

    /**
     * Checkt den Tagesteil einer Datums-Formatangabe
     * Dieser beschreibt, wie der Tagesteil aufgebaut ist.
     * Entweder als DBDATE-Format von Informix (z.B. dmy4.)
     * Oder als Java-DateFormatangabe (z.B. dd.MM.yyyy)
     *
     * @param format Formatstring
     * @return Reihenfolge von Jahr, Monat und Tag , z.B: "d", "m", "y"
     * @throws HISException wenn der Input-String keine gueltige Datumsformat-Angabe darstellt
     */
    public static String[] checkDateFormat(String format) throws HISException {
        // logger.debug("----------START----------- format=" + format);
        String myformat = format.toLowerCase();
        final int ix = myformat.indexOf(" ");
        if (ix > 0) {
            myformat = myformat.substring(0, ix);
        }

        
        //final List<String> formatList = new ArrayList<>();//FGL: Herausgenommen wg. Java 1.7
        final List<String> formatList = new ArrayList<String>();//Eclipse Lösungsvorschlag: Insert Inferred Type arguments
        //final Set<String> sep = new HashSet<>();//FGL: Herausgenommen wg. Java 1.7
        final Set<String> sep = new HashSet<String>();//Eclipse Lösungsvorschlag: Insert Inferred Type arguments
        String last = "?";
        StringBuilder strBld = new StringBuilder();
        int i = 0;
        while (i < myformat.length()) {
            final String current = myformat.substring(i, i + 1);
            if ("d".equals(current) || "m".equals(current) || "y".equals(current)) {
                if (!last.equals(current)) {
                    if (!"?".equals(last)) {
                        formatList.add(strBld.toString());
                    }
                    strBld = new StringBuilder();
                    last = current;
                }
                strBld.append(current);
            } else {
                try {
                    final int len = Integer.parseInt(current);
                    for (int j = 0; j < len - 1; j++) {
                        if (strBld.length() > 0) {
                            strBld.append(strBld.charAt(0));
                        }
                    }
                } catch (final NumberFormatException e) {
                    sep.add(current);
                }
            }
            i++;
        }
        if (!"?".equals(last)) {
            formatList.add(strBld.toString());
        }
        // logger.debug("----------Z-------- formatList=" + formatList + "; sep=" + sep);
        // Checks
        // 1. formatList contains exactly 3 entrys (d,m,y)
        if (formatList.size() != 3) {
            throw new HISIllegalArgumentException("Format '" + format + "' invalid: must have (only) 'd', 'm', 'y' entries, separated by a separator");
        }
        // 1. exactly one separator
        if (sep.size() != 1) {
            throw new HISIllegalArgumentException("Format '" + format + "' invalid: must have exactly 1 separator, found " + sep);
        }

        final String[] formatArray = new String[formatList.size()];
        i = 0;
        for (final String part : formatList) {
            if (part.length() > 1) {
                formatArray[i++] = part.substring(0, 1);
            } else {
                formatArray[i++] = part;
            }
        }
        return formatArray;
    }

    /**
     * Formats time string with format "hh:mm:ss"
     * if time is invalid "00:00:00" will be returned
     *
     * @return formatted time or "00:00:00"
     */
    public static String normalizeTime(String withHours) {
        String hour = "00";
        String minute = "00";
        String second = "00";

        final String sep = ":";
        int ix = withHours.indexOf(sep);
        String withMinutes;
        if (ix > -1) {
            hour = withHours.substring(0, ix);
            withMinutes = withHours.substring(ix + 1);
            ix = withMinutes.indexOf(sep);
            if (ix > -1) {
                minute = withMinutes.substring(0, ix);
                second = withMinutes.substring(ix + 1);
            } else {
                minute = withMinutes;
            }
        } else {
            hour = withHours;
            withMinutes = "";
        }
        if (hour.trim().length() == 0) {
            hour = "00";
        }
        if (minute.trim().length() == 0) {
            minute = "00";
        }
        if (second.trim().length() == 0) {
            second = "00";
        }
        try {
            final int item = Integer.parseInt(hour);
            if (item < 0 || item > 24) {
                hour = "00";
            } else {
                hour = Integer.toString(item);
                if (hour.length() < 2) {
                    hour = "0" + hour;
                }
            }
        } catch (final NumberFormatException e) {
            // logger.warn("Stundenangabe " + hour + " ist kein Integer, nehme default '00'", new Throwable());
        }
        try {
            final int item = Integer.parseInt(minute);
            if (item < 0 || item > 60) {
                minute = "00";
            } else {
                minute = Integer.toString(item);
                if (minute.length() < 2) {
                    minute = "0" + minute;
                }
            }
        } catch (final NumberFormatException e) {
            // logger.warn("Minutenangabe " + minute + " ist kein Integer, nehme default '00'");
        }
        try {
            final float item = Float.parseFloat(second);
            if (item < 0 || item > 60) {
                second = "00";
            } else {
                second = Float.toString(item);
            }
        } catch (final NumberFormatException e) {
            // logger.warn("Sekundenangabe " + second + " ist kein Float, nehme default '00'");
        }
        return hour + sep + minute + sep + second;
    }

    /**
     * Gleicht Jahreslänge an.
     *
     * @param year Jahr
     * @param yearlength gewuenschte Laenge
     * @return Jahr in gewuenschter Laenge
     * @throws HISDateException
     */
    public static String adjustYear(String year, int yearlength) throws HISDateException {
        String century = null;
        final int currentYear = HISDate.getToday().getYear();
        try {
            final int yy = Integer.parseInt(year);
            if (yy < 100) {
                if (yy - currentYear + 2000 > 14) {
                    century = "19";
                } else {
                    century = "20";
                }
            }
        } catch (final Exception e) {
            throw new HISDateException("Jahresangabe hat unerwarteten Wert: " + year, e);
        }
        return adjustYear(year, century, yearlength);
    }

    /**
     * @return TimeStamp String with day
     */
    public static String printTimeStamp() {
        final DateFormat df = DateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.DEFAULT, aLocale);
        return df.format(new Date());
    }

    /**
     * @return TimeStamp String without day
     */
    public static String getTimeWithoutDay() {
        String time = DateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.DEFAULT, aLocale).format(new Date());
        final int i = time.indexOf(" ");
        if (i > -1) {
            time = time.substring(i + 1);
        }
        return time;
    }

    /**
     * System.currentTimeMillis()
     *
     * @return Millisekunden
     */
    public static long getTimeStamp() {
        return System.currentTimeMillis();
    }

    /**
     * @param timeElapsed in msecs
     * @return TimeStamp String with day
     */
    public static String printTimeElapsed(long timeElapsed) {
        if (timeElapsed < 1000) {
            return timeElapsed + " msec";
        }
        if (timeElapsed < 60000) {
            final double duration = timeElapsed / 1000.0;
            return duration + " sec";
        }
        if (timeElapsed < 60000 * 60) {
            final double duration = Math.ceil(timeElapsed / 1000.0);
            return (int) Math.floor(duration / 60) + " min " + (int) Math.floor(duration % 60) + " sec";
        }
        // Stunden extra ausweisen
        final long hours = (long) Math.floor(timeElapsed / (60000 * 60));
        final long duration = (long) Math.ceil((timeElapsed - hours * 60000 * 60) / 1000);
        return hours + " h " + (int) Math.floor(duration / 60) + " min " + (int) Math.floor(duration % 60) + " sec";
    }

    /**
     * Combines the date of the first argument with the time of the second argument.
     *
     * @param date the date, may not be null
     * @param time the time or null
     * @return the combined time or date if time was null
     */
    public static Date combineDateWithTime(Date date, Date time) {
        if (time == null) {
            // fail gracefully and return unmodified source date if no time is given
            logger.debug("No time information supplied for source date: " + date);
            logger.debug("Returning unmodified start date.");
            return date;
        }

        // base returned value on source date
        final Calendar returnDate = Calendar.getInstance();
        returnDate.setTime(date);

        final Calendar timeDate = Calendar.getInstance();
        timeDate.setTime(time);

        logger.debug("Merging source date (" + date + ") with time values of this date: " + time);
        // set returned date's time values to the supplied date's time values
        returnDate.set(Calendar.HOUR_OF_DAY, timeDate.get(Calendar.HOUR_OF_DAY));
        returnDate.set(Calendar.MINUTE, timeDate.get(Calendar.MINUTE));
        returnDate.set(Calendar.SECOND, timeDate.get(Calendar.SECOND));

        final Date result = returnDate.getTime();
        logger.debug("Returning merged date: " + result);

        return result;
    }

    /**
     * Modifies the time components of a given date.
     * <p>The original date object is unchanged.</p>
     * <p>The milliseconds will be set to zero.</p>
     *
     * @param date      the date whose time components (hour, minute, second) should be modified
     * @param hour      the hour (of day) to set (0-23)
     * @param minute    the minute to set (0-59)
     * @param second    the second to set (0-59)
     *
     * @return a new date instance with the date fragment of the original date object and the given time components; never {@code null}
     */
    public static Date modifyTimeOfDate(Date date, int hour, int minute, int second) {
        Date result = new Date(date.getTime());
        result = DateUtils.setHours(result, hour);
        result = DateUtils.setMinutes(result, minute);
        result = DateUtils.setSeconds(result, second);
        return DateUtils.setMilliseconds(result, 0);
    }

    /**
     * @param from Früheres Datum, zum Beispiel Geburtsdatum
     * @param to Neueres Datum, zum Beispiel "heute"
     * @return Volle Jahre zwischen den beiden Datümern
     */
    public static int calculateYearsBetweenDates(Date from, Date to) {
        final Calendar calFrom = Calendar.getInstance();
        calFrom.setTime(from);
        final Calendar calTo = Calendar.getInstance();
        calTo.setTime(to);
        int age = calTo.get(Calendar.YEAR) - calFrom.get(Calendar.YEAR);
        if (calTo.get(Calendar.MONTH) < calFrom.get(Calendar.MONTH)) {
            age--;
        } else if (calTo.get(Calendar.MONTH) == calFrom.get(Calendar.MONTH) && calTo.get(Calendar.DAY_OF_MONTH) < calFrom.get(Calendar.DAY_OF_MONTH)) {
            age--;
        }
        return age;
    }

    /**
     * @param a
     * @param b
     * @return @return true if <code>a</code> is after <code>b</code>, ignoring time of both dates
     */
    public static boolean isAfterIgnoreTime(Date a, Date b) {
        return removeTime(a).after(removeTime(b));
    }

    /**
     * @param a
     * @param b
     * @return true if <code>a</code> is before <code>b</code>, ignoring time of both dates
     */
    public static boolean isBeforeIgnoreTime(Date a, Date b) {
        return removeTime(a).before(removeTime(b));
    }

    /**
     * @return today at 00:00:00.000
     */
    public static Date currentDateWithoutTime() {
        return removeTime(currentDate());
    }

    /**
     * Create a Date object from a given one which has same date but time is reset to 00:00:00.000
     * Used to simulate a date from database.
     * @param dateWithTime
     * @return date with time set to 00:00:00.000
     */
    public static Date removeTime(Date dateWithTime) {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateWithTime);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * Returns the current date.
     * @return the current date instance
     */
    public static Date currentDate() {
        return new Date();
    }

    /**
     * Returns the current date formatted in "yyyy-MM-dd".
     * @return the current ISO date
     */
    public static String currentISODate() {
        final DateFormat isoDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return isoDateFormat.format(currentDate());
    }

    /**
     * Returns the current date formatted in "yyyy-MM-dd HH:mm:ss".
     * @param date the date to format
     * @return the current date formatted in "yyyy-MM-dd HH:mm:ss".
     */
    public static String getDateTime(Date date) {
        final DateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateTimeFormat.format(date);
    }

    /**
     * @return the current year
     */
    public static int currentYear() {
        return getCalendarFor(currentDate()).get(Calendar.YEAR);
    }

    /**
     * @param year
     * @return the first day of the given year
     */
    public static Date firstDayOf(int year) {
        return new Month(MonthName.JANUARY, year).getStartDate();
    }

    /**
     * @param year
     * @return the last day of the given year
     */
    public static Date lastDayOf(int year) {
        return new Month(MonthName.DECEMBER, year).getEndDate();
    }

    /**
     * Gets the first day of the given month and year.
     * the first month of the year is JANUARY which is 1 ! (in the Gregorian and Julian calendars, the first month of the year is JANUARY which is 0 !)
     *
     * @param month
     * @param year
     * @return the first day of the given month and year
     */
    public static Date firstDayOf(int month, int year) {
        return new Month(month, year).getStartDate();
    }

    /**
     * Gets the last day of the given month and year.
     * the first month of the year is JANUARY which is 1 ! (in the Gregorian and Julian calendars, the first month of the year is JANUARY which is 0 !)
     *
     * @param month January == 1, December == 12
     * @param year
     * @return the last day of the given month and year
     */
    public static Date lastDayOf(int month, int year) {
        return new Month(month, year).getEndDate();
    }

    /**
     *
     * @return the first day of the current year
     */
    public static Date firstMonthOfCurrentYear() {
        return firstDayOf(currentYear());
    }

    /**
     *
     * @return the last day of the current year
     */
    public static Date lastMonthOfCurrentYear() {
        return lastDayOf(currentYear());
    }

    /**
     * Adds one hour to the given date.
     * @param date
     * @return a new date one hour after the given one
     */
    public static Date addOneHour(Date date) {
        return DateUtils.addHours(date, 1);
    }

    /**
     * Gets the year of the given date.
     * added because getYear() for the class Date is Deprecated.
     *
     * @param date
     * @return the year of the given date
     */
    public static int yearOf(Date date) {
        final Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.YEAR);
    }

    /**
     * Gets the month of the given date (range 0..11).
     * Has been added because getMonth() in class Date is deprecated.
     *
     * @param date
     * @return the month of the given date, in the range returned from Calendar: 0=January, 1=February, 2=March ...
     */
    public static int monthOf(Date date) {
        final Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.MONTH);
    }

    /**
     * Gets the day of the given date.
     * added because getDate() for the class Date is Deprecated.
     *
     * @param date
     * @return the day of the given date
     */
    public static int dayOf(Date date) {
        final Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * Returns the first day of the month of the given date.
     *
     * @param date
     * @return the first day of the month of the given date
     */
    public static Date firstDayOfMonth(Date date) {
        final Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.set(Calendar.MONTH, monthOf(date));
        cal.set(Calendar.YEAR, yearOf(date));
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
        return cal.getTime();
    }

    /**
     * Returns the last day of the month of the given date.
     *
     * @param date
     * @return the last day of the month of the given date
     */
    public static Date lastDayOfMonth(Date date) {
        final Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.set(Calendar.MONTH, monthOf(date));
        cal.set(Calendar.YEAR, yearOf(date));
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        return cal.getTime();
    }

    /**
     * Returns the first day of the week of the given date.
     *
     * @param currentDay A date within the week
     *
     * @return the first day of the week of the given date.
     */
    public static Date firstDayOfWeek(Date currentDay) {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDay);

        final int year = calendar.get(Calendar.YEAR);
        final int week = calendar.get(Calendar.WEEK_OF_YEAR);

        calendar.clear();

        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.WEEK_OF_YEAR, week);

        return calendar.getTime();
    }

    /**
     * @param aDate erstes Datum, kann auch null sein (Urknall)
     * @param anotherDate zweites Datum, kann auch null sein (Urknall)
     * @return größere/spätere Datum oder null, wenn beide Werte null (Urknall) sind
     */
    public static Date getMaxDate(Date aDate, Date anotherDate) {
        if (aDate == null) {
            return anotherDate;
        }
        if (anotherDate != null) {
            return aDate.compareTo(anotherDate) < 0 ? anotherDate : aDate;
        }
        return aDate;
    }

    /**
     * @param isoString String in ISO-Format (engl. with optional time-Part and optional millis)
     * @return java.util.Date from String in ISO-Format
     * @throws ParseException
     */
    public static Date parseISO(String isoString) throws ParseException {
        // Versuch, das passende Format zu ermitteln
        String format = "yyyy-MM-dd";
        if (isoString.contains(".")) {
            format = "yyyy-MM-dd HH:mm:ss.SSS";
        } else if (isoString.contains(":")) {
            format = "yyyy-MM-dd HH:mm:ss";
        }

        return new SimpleDateFormat(format).parse(isoString);
    }

    /**
     * Returns a {@link Calendar} without time-information.
     *
     * @param _date the {@link Date} to set
     *
     * @return a {@link Calendar} without time-information.
     */
    public static Calendar getCalendarNoTime(final Date _date) {
        final Calendar dateCalendar = Calendar.getInstance();
        if (_date != null) {
            dateCalendar.setTime(_date);
        }

        final Calendar newCalendar = Calendar.getInstance();
        newCalendar.clear();
        newCalendar.set(Calendar.YEAR, dateCalendar.get(Calendar.YEAR));
        newCalendar.set(Calendar.MONTH, dateCalendar.get(Calendar.MONTH));
        newCalendar.set(Calendar.DAY_OF_MONTH, dateCalendar.get(Calendar.DAY_OF_MONTH));
        return newCalendar;
    }

    /**
     * Returns {@code true} if both parameters are equal up to the second.
     *
     * <p>If both parameter are {@code null} the result is {@code true}. If only one of the parameter is {@code null}
     * the result is {@code false}. Otherwise the dates will be compared.</p>
     *
     * @param first     the first date
     * @param second    the second date
     *
     * @return {@code true} if the difference between both parameters is less than a second or both parameter are {@code null}
     */
    public static boolean equalToTheSecond(Date first, Date second) {
        return first == null && second == null || first != null && second != null && Math.abs(first.getTime() - second.getTime()) < 1000;
    }

    /**
     * @return 01.01.1900 - usual start date for H1 Calculations
     */
    public static Date getStartDate() {
        return parse("01.01.1900");
    }

    /**
     * @return 31.12.2100 - usual end date for H1 Calculations
     */
    public static Date getEndDate() {
        return parse("31.12.2100");
    }

    /**
     * Example:
     * addDays(01.02.1999, 3) ~> 04.02.1999
     *
     * @param date
     * @param numDays - may be negative
     * @return date plus numDays
     */
    public static Date addDays(Date date, int numDays) {
        final Calendar cal = getCalendarFor(date);
        cal.add(Calendar.DAY_OF_MONTH, numDays);
        return cal.getTime();
    }

    /**
     * @param date
     * @return day after date
     */
    public static Date dayAfter(Date date) {
        return addDays(date, 1);
    }

    /**
     * @param date
     * @return day before date
     */
    public static Date dayBefore(Date date) {
        return addDays(date, -1);
    }

    /**
     * @return Date for yesterday
     */
    public static Date yesterday() {
        return dayBefore(today());
    }

    /**
     * @return Date for tomorrow
     */
    public static Date tomorrow() {
        return dayAfter(today());
    }

    /**
     * @param date
     * @return Calendar instance initialized with 'date'
     */
    public static Calendar getCalendarFor(Date date) {
        final Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }

    /**
     * @return Date for today
     */
    public static Date today() {
        return new Date();
    }

    /**
     *.
     * @return the current year
     */
    public static int lastYear() {
        return currentYear() - 1;
    }

    /**
     *.
     * @return the current year
     */
    public static int nextYear() {
        return currentYear() + 1;
    }

    /**
     * Ist das Datum älter als die übergeben Tage?
     *
     * @param date
     * @param days
     * @return boolean
     */
    public static boolean isDateOlderThenDays(Date date, int days) {
        if (date == null || days < 0) {
            return false;
        }
        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, -days);
        return date.before(cal.getTime());
    }

    /**
     * Alias for parse().
     * @param dateString
     * @return a date or null
     */
    public static Date dateOf(String dateString) {
        return parse(dateString);
    }

    /**
     * Konvertiert einen Datums-String entsprechend der Form im Klassen-Kommentar zu einem Date-Objekt.
     * @param dateString
     * @return a date or null
     */
    public static Date parse(String dateString) {
        return doParse(dateString);
    }

    public static Date doParse(String dateString) {
        if (StringUtils.isBlank(dateString)) {
            return null;
        }
        final String value = dateString.trim();

        Date retVal = null;
        try {
            final SimpleDateFormat sdf = new SimpleDateFormat();
            final boolean containsDot = value.contains(".");
            final boolean containsSlash = value.contains("/");
            for (final String pattern : PATTERNS) {
                if (containsDot != pattern.contains(".") || containsSlash != pattern.contains("/")) {
                    continue;
                }
                logger.trace("Applying pattern \"" + pattern + "\"...");
                sdf.applyPattern(pattern);
                try {
                    retVal = sdf.parse(value);
                    // Kontrolle, ob Eingabe und Ausgabe übereinstimmen
                    if (!sdf.format(retVal).equals(value)) {
                        retVal = null;
                    }
                } catch (final Exception e) {
                    logger.debug(e);
                }
                if (retVal != null) {
                    logger.trace("Pattern matched!");
                    break;
                }
            }
        } catch (final Exception e) {
            // nix zu tun
        }

        if (retVal != null && retVal.getTime() > maxDate.getTime()) { // siehe #53527
            retVal = null;
        }

        return retVal;
    }

    /**
     * @param value
     * @return value as Date
     */
    public static Date asDate(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof Date) {
            return (Date) value;
        }
        if (value instanceof String && ((String) value).trim().length() == 0) {
            return null;
        }
        return parse(value.toString());
    }

    public static Date parseTimestamp(String value0) {
        String value = value0.trim();
        final List<String> patterns = Arrays.asList("d.M.yy HH:mm:ss", "d.MM.yy HH:mm:ss",

                        "dd.M.yy HH:mm:ss", "dd.MM.yy HH:mm:ss",

                        "d.M.yyyy HH:mm:ss", "d.MM.yyyy HH:mm:ss",

                        "dd.M.yyyy HH:mm:ss", "dd.MM.yyyy HH:mm:ss",

                        "ddMMyy HH:mm:ss", "ddMMyyyy HH:mm:ss",

                        "d.M.yy HH:mm", "d.MM.yy HH:mm",

                        "dd.M.yy HH:mm", "dd.MM.yy HH:mm",

                        "d.M.yyyy HH:mm", "d.MM.yyyy HH:mm",

                        "dd.M.yyyy HH:mm", "dd.MM.yyyy HH:mm",

                        "ddMMyy HH:mm", "ddMMyyyy HH:mm",

                        "yyyy-MM-dd HH:mm:ss");

        Date retVal = null;
        try {
            final SimpleDateFormat sdf = new SimpleDateFormat();
            for (final String pattern : patterns) {
                logger.debug("Applying pattern \"" + pattern + "\"...");
                sdf.applyPattern(pattern);
                try {
                    retVal = sdf.parse(value);
                    // Kontrolle, ob Eingabe und Ausgabe übereinstimmen
                    if (!sdf.format(retVal).equals(value)) {
                        retVal = null;
                    }
                } catch (final Exception e) {
                    logger.debug(e);
                }
                if (retVal != null) {
                    logger.debug("Pattern matched!");
                    break;
                }
            }
        } catch (final Exception e) {
            // nix zu tun
        }

        if (retVal != null && retVal.getTime() > maxDate.getTime()) { // siehe #53527
            retVal = null;
        }

        return retVal;
    }

    /**
     * @return the dates of the weekend days in the given range
     */
    // used by jsf
    public static List<Date> getWeekendDays(Date startDate, Date endDate) {
        final List<Date> result = Lists.newArrayList();
        for (final Calendar cal : DateRangeIterators.calendarRange(startDate, endDate)) {
            switch (cal.get(Calendar.DAY_OF_WEEK)) {
            case Calendar.SATURDAY:
            case Calendar.SUNDAY:
                result.add(cal.getTime());
                break;
            default:
            }
        }
        return result;
    }

    /**
     * Methode um Zeiten zu addieren
     * @param time Die Zeit zu der addiert werden soll (Format: hh:mm)
     * @param timeToAdd Die Zeit die addiert werden soll (Format: hh:mm)
     * z.B. time=10:00 timeToAdd=1:30 return=11:30
     * oder time=10:00 timeToAdd=90 return=11:30
     * @return timeAdded
     */
    public static String addTime(String time, String timeToAdd) {
        String hour, minute;
        int minutes, hours, minutesToAdd, hoursToAdd;
        if (!time.contains(":")) {
            StringUtil.logger.error("Die Uhrzeit muss ein ':' zum trennen der Stunden von den Minuten enthalten");
            return "";
        }
        minutes = NumberUtil.parseInt(time.substring(time.indexOf(":") + 1), 0);
        hours = NumberUtil.parseInt(time.substring(0, time.indexOf(":")), 0);
        if (timeToAdd.contains(":")) {
            final String zahl = timeToAdd.substring(timeToAdd.indexOf(":") + 1);
            minutesToAdd = NumberUtil.parseInt(zahl, 0);
            final String zahl1 = timeToAdd.substring(0, timeToAdd.indexOf(":"));
            hoursToAdd = NumberUtil.parseInt(zahl1, 0);
        } else {
            minutesToAdd = NumberUtil.parseInt(timeToAdd, 0);
            hoursToAdd = 0;
        }
        hours = hours + hoursToAdd;
        minutes = minutes + minutesToAdd;

        while (minutes > 59) {
            minutes = minutes - 60;
            hours = hours + 1;
        }
        if (hours > 23) {
            hours = hours - 24;
        }
        if (hours < 10) {
            hour = "0" + hours;
        } else {
            hour = "" + hours;
        }
        if (minutes < 10) {
            minute = "0" + minutes;
        } else {
            minute = "" + minutes;
        }
        return hour + ":" + minute;
    }

    /**
     * parses date as YYYY-MM-dd
     *
     * @return null on parse error
     */
    public static Object parseISO2014Date(String dateString) {
        String pattern = "yyyy-MM-dd";
        final SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        try {
            return dateFormat.parse(dateString);
        } catch (final ParseException e) {
            ReflectionUtil.logger.error("error parsing date '" + dateString + "' through pattern " + pattern, e);
            return null;
        }
    }

    /**
     * @param calendar
     * @return java.sql.Date
     */
    public static java.sql.Date calendarToSqlDate(Calendar calendar) {
        return new java.sql.Date(calendar.getTimeInMillis());
    }

    /**
     * @param calendar
     * @return java.sql.Timestamp
     */
    public static java.sql.Timestamp calendarToSqlTimestamp(Calendar calendar) {
        return new java.sql.Timestamp(calendar.getTimeInMillis());
    }

    /**
     * @param dateValue
     * @return calendar
     */
    public static GregorianCalendar dateToGregorianCalendar(java.util.Date dateValue) {
        long dateLong = dateValue.getTime();
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTimeInMillis(dateLong);
        return calendar;
    }
}
